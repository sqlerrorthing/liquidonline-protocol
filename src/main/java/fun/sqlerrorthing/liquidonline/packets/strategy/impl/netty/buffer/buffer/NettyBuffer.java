package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferDeserializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferSerializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.adapters.ColorAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.adapters.InstantAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.adapters.UUIDAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.impl.ByteBufReaderImpl;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.impl.ByteBufWriterImpl;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NettyBuffer {
    @NotNull
    private final ByteBufDeserializer deserializer;

    @NotNull
    private final ByteBufSerializer serializer;

    public NettyBuffer(
        @NotNull final Map<Class<?>, BufferDeserializer<?>> deserializers,
        @NotNull final Map<Class<?>, BufferSerializer<?>> serializers
    ) {
        this.deserializer = new ByteBufDeserializer(deserializers);
        this.serializer = new ByteBufSerializer(serializers);
    }

    public static Builder builder() {
        return new Builder()
                .registerAdapter(Instant.class, new InstantAdapter())
                .registerAdapter(UUID.class, new UUIDAdapter())
                .registerAdapter(Color.class, new ColorAdapter());
    }

    public void serialize(@NotNull ByteBuf buf, @NotNull Object obj) throws IOException {
        serializer.serialize(new ByteBufWriterImpl(buf), obj);
    }

    @NotNull
    public <T> T deserialize(@NotNull ByteBuf byteBuf, @NotNull Class<T> clazz) throws IOException {
        return deserializer.deserialize(new ByteBufReaderImpl(byteBuf), clazz);
    }

    public static class Builder {
        @NotNull
        private final Map<Class<?>, BufferDeserializer<?>> deserializers = new HashMap<>();

        @NotNull
        private final Map<Class<?>, BufferSerializer<?>> serializers = new HashMap<>();

        public <T> Builder registerAdapter(@NotNull Class<T> clazz, @NotNull BufferAdapter<T> adapter) {
            return this.registerDeserializer(clazz, adapter).registerSerializer(clazz, adapter);
        }

        public <T> Builder registerDeserializer(@NotNull Class<T> clazz, @NotNull BufferDeserializer<T> deserializer) {
            deserializers.put(clazz, deserializer);
            return this;
        }

        public <T> Builder registerSerializer(@NotNull Class<T> clazz, @NotNull BufferSerializer<T> serializer) {
            serializers.put(clazz, serializer);
            return this;
        }

        public NettyBuffer build() {
            return new NettyBuffer(deserializers, serializers);
        }
    }
}
