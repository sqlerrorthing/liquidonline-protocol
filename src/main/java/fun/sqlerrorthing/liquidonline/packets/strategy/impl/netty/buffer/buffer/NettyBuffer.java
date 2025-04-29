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
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NettyBuffer {
    @NotNull
    private final Map<Class<?>, BufferDeserializer<?>> deserializers = new HashMap<>();

    @NotNull
    private final Map<Class<?>, BufferSerializer<?>> serializers = new HashMap<>();

    @NotNull
    private final ByteBufDeserializer deserializer;

    @NotNull
    private final ByteBufSerializer serializer;

    public NettyBuffer() {
        registerAdapter(Color.class, new ColorAdapter());
        registerAdapter(UUID.class, new UUIDAdapter());
        registerAdapter(Instant.class, new InstantAdapter());

        this.deserializer = new ByteBufDeserializer(deserializers);
        this.serializer = new ByteBufSerializer(serializers);
    }

    private <T> void registerAdapter(@NotNull Class<T> clazz, @NotNull BufferAdapter<T> adapter) {
        this.registerDeserializer(clazz, adapter);
        this.registerSerializer(clazz, adapter);
    }

    private <T> void registerDeserializer(@NotNull Class<T> clazz, @NotNull BufferDeserializer<T> deserializer) {
        deserializers.put(clazz, deserializer);
    }

    private <T> void registerSerializer(@NotNull Class<T> clazz, @NotNull BufferSerializer<T> serializer) {
        serializers.put(clazz, serializer);
    }

    public void serialize(@NotNull ByteBuf buf, @NotNull Object obj) throws IOException {
        serializer.serialize(new ByteBufWriterImpl(buf), obj);
    }

    @NotNull
    public <T> T deserialize(@NotNull ByteBuf byteBuf, @NotNull Class<T> clazz) throws IOException {
        return deserializer.deserialize(new ByteBufReaderImpl(byteBuf), clazz);
    }
}
