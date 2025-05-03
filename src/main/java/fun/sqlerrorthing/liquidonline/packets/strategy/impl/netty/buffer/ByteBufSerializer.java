package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.BufferSerializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.context.BufferSerializationContext;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ByteBufSerializer {
    @NotNull
    private final Map<Class<?>, BufferSerializer<?>> serializers;

    @NotNull
    private final BufferSerializationContext context;

    public ByteBufSerializer(@NotNull Map<Class<?>, BufferSerializer<?>> serializers) {
        this.serializers = serializers;
        this.context = this::serialize;
    }

    void serialize(@NotNull ByteBufWriter writer, @NotNull Object obj) throws IOException {
        for (var field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                writeValue(field.getDeclaredAnnotations(), writer, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void writeValue(@NotNull Annotation[] fieldAnnotations, @NotNull ByteBufWriter writer, @Nullable Object value) throws IOException {
        if (value == null) {
            writer.writeNull();
        } else if (value instanceof String s) {
            writer.writeString(s);
        } else if (value instanceof Boolean b) {
            writer.writeBoolean(b);
        } else if (value.getClass().isEnum()) {
            writer.writeUnsignedVarInt(((Enum<?>) value).ordinal());
        } else if (value instanceof Long l) {
            writeAsUnsignedIfUnsignedAnnotationPreset(
                    () -> writer.writeUnsignedVarLong(l),
                    () -> writer.writeLong(l),
                    fieldAnnotations
            );
        } else if (value instanceof Integer i) {
            writeAsUnsignedIfUnsignedAnnotationPreset(
                    () -> writer.writeUnsignedVarInt(i),
                    () -> writer.writeInt(i),
                    fieldAnnotations
            );
        } else if (value instanceof Short s) {
            writeAsUnsignedIfUnsignedAnnotationPreset(
                    () -> writer.writeUnsignedVarShort(s),
                    () -> writer.writeShort(s),
                    fieldAnnotations
            );
        } else if (value instanceof Byte b) {
            writer.writeByte(b);
        } else if (value instanceof Float f) {
            writer.writeFloat(f);
        } else if (value instanceof Double d) {
            writer.writeDouble(d);
        } else if (value instanceof byte[] arr) {
            writer.writeUnsignedVarInt(arr.length);
            writer.writeBytes(arr);
        }
        else if (value.getClass().isArray()) {
            throw new UnsupportedOperationException("Not implemented yet.");
        } else if (value instanceof List<?> l) {
            writeList(writer, l);
        } else {
            var clazz = value.getClass();

            BufferSerializer<?> adapter = getSerializer(clazz);
            if (adapter != null) {
                ((BufferSerializer<Object>) adapter).serialize(value, clazz, writer, context);
                return;
            }

            serialize(writer, value);
        }
    }

    private void writeAsUnsignedIfUnsignedAnnotationPreset(
            Runnable writeUnsigned,
            Runnable writeSigned,
            Annotation[] annotations
    ) {
        Optional<UnsignedNumber> unsigned = Arrays.stream(annotations)
                .filter(a -> a.annotationType().equals(UnsignedNumber.class))
                .map(a -> (UnsignedNumber) a)
                .findFirst();

        if (unsigned.isPresent()) {
            writeUnsigned.run();
        } else {
            writeSigned.run();
        }
    }

    private void writeList(@NotNull ByteBufWriter writer, @NotNull List<?> list) throws IOException {
        writer.writeUnsignedVarInt(list.size());
        for (var o : list) {
            writeValue(new Annotation[0], writer, o);
        }
    }

    @SuppressWarnings("unchecked")
    private BufferSerializer<?> getSerializer(Class<?> clazz) {
        BufferSerializer<?> adapter = serializers.get(clazz);
        if (adapter != null) {
            return adapter;
        }

        for (Class<?> superClass = clazz.getSuperclass(); superClass != null; superClass = superClass.getSuperclass()) {
            adapter = serializers.get(superClass);
            if (adapter != null) {
                return adapter;
            }
        }

        for (Class<?> iface : clazz.getInterfaces()) {
            adapter = serializers.get(iface);
            if (adapter != null) {
                return adapter;
            }
        }

        return null;
    }
}
