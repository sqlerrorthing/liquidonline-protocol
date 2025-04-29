package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferSerializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class ByteBufSerializer {
    @NotNull
    private final Map<Class<?>, BufferSerializer<?>> serializers;

    public ByteBufSerializer(@NotNull Map<Class<?>, BufferSerializer<?>> serializers) {
        this.serializers = serializers;
    }

    void serialize(@NotNull ByteBufWriter writer, @NotNull Object obj) throws IOException {
        for (var field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                writeValue(writer, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void writeValue(@NotNull ByteBufWriter writer, @Nullable Object value) throws IOException {
        if (value == null) {
            writer.writeNull();
        } else if (value instanceof String s) {
            writer.writeString(s);
        } else if (value instanceof Long l) {
            writer.writeLong(l);
        } else if (value instanceof Integer i) {
            writer.writeInt(i);
        } else if (value instanceof Short s) {
            writer.writeShort(s);
        } else if (value instanceof Byte b) {
            writer.writeByte(b);
        } else if (value instanceof Float f) {
            writer.writeFloat(f);
        } else if (value instanceof Double d) {
            writer.writeDouble(d);
        }

        else if (value.getClass().isArray()) {
            writeArray(writer, value);
        } else if (value instanceof List<?> l) {
            writeList(writer, l);
        } else {
            BufferSerializer<?> adapter = serializers.get(value.getClass());
            if (adapter != null) {
                ((BufferSerializer<Object>) adapter).serialize(value, writer);
                return;
            }

            serialize(writer, value);
        }
    }

    private void writeArray(@NotNull ByteBufWriter writer, @NotNull Object value) throws IOException {
        int length = Array.getLength(value);
        writer.writeInt(length);

        for (int i = 0; i < length; i++) {
            writeValue(writer, Array.get(value, i));
        }
    }

    private void writeList(@NotNull ByteBufWriter writer, @NotNull List<?> list) throws IOException {
        writer.writeInt(list.size());
        for (var o : list) {
            writeValue(writer, o);
        }
    }
}
