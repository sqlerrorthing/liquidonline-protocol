package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.BufferDeserializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.BufferSerializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.adapters.ColorAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.adapters.InstantAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.adapters.UUIDAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl.ByteBufReaderImpl;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl.ByteBufWriterImpl;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utility for high-performance serialization and deserialization of objects into Netty {@link io.netty.buffer.ByteBuf}.
 * <p>
 *     This class uses user-provided {@link BufferSerializer} and {@link BufferDeserializer} implementations
 *     to encode and decode objects in a compact binary form. All field names and type metadata are stripped,
 *     meaning the serialized form contains only raw values.
 * </p>
 *
 * <p><b>Important:</b> Objects must be deserialized using the exact same class structure as they were serialized with.
 * Any field order, type, or class mismatches will lead to deserialization errors or corrupted data.</p>
 *
 * <p>
 *     Designed for minimal overhead and maximal efficiency when working with Netty's {@code ByteBuf},
 *     this utility is ideal for performance-critical scenarios like custom network protocols.
 * </p>
 */
public class NettyBuffer {
    @NotNull
    private final ByteBufDeserializer deserializer;

    @NotNull
    private final ByteBufSerializer serializer;

    private NettyBuffer(
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

    /**
     * Serializes the given object into the provided {@link io.netty.buffer.ByteBuf}.
     *
     * @param buf the target buffer to write into
     * @param obj the object to serialize
     * @throws IOException if serialization fails
     */
    public void serialize(@NotNull ByteBuf buf, @NotNull Object obj) throws IOException {
        serializer.serialize(new ByteBufWriterImpl(buf), obj);
    }

    /**
     * Deserializes an object of the given class from the provided {@link io.netty.buffer.ByteBuf}.
     * The class must match exactly the structure used during serialization.
     *
     * @param byteBuf the buffer to read from
     * @param clazz the class of the object to deserialize
     * @param <T> the type of the object
     * @return the deserialized object
     * @throws IOException if deserialization fails
     */
    @NotNull
    public <T> T deserialize(@NotNull ByteBuf byteBuf, @NotNull Class<T> clazz) throws IOException {
        return deserializer.deserialize(new ByteBufReaderImpl(byteBuf), clazz);
    }

    /**
     * Builder for {@link NettyBuffer}, allowing registration of custom (de)serializers and adapters.
     */
    public static class Builder {
        @NotNull
        private final Map<Class<?>, BufferDeserializer<?>> deserializers = new HashMap<>();

        @NotNull
        private final Map<Class<?>, BufferSerializer<?>> serializers = new HashMap<>();

        /**
         * Registers a symmetric {@link BufferAdapter} for both serialization and deserialization of the given type.
         *
         * @param clazz the class type
         * @param adapter the adapter to use
         * @param <T> the type
         * @return this builder
         */
        public <T> Builder registerAdapter(@NotNull Class<T> clazz, @NotNull BufferAdapter<T> adapter) {
            return this.registerDeserializer(clazz, adapter).registerSerializer(clazz, adapter);
        }

        /**
         * Registers a custom {@link BufferDeserializer} for the given type.
         *
         * @param clazz the class type
         * @param deserializer the deserializer to use
         * @param <T> the type
         * @return this builder
         */
        public <T> Builder registerDeserializer(@NotNull Class<T> clazz, @NotNull BufferDeserializer<T> deserializer) {
            deserializers.put(clazz, deserializer);
            return this;
        }

        /**
         * Registers a custom {@link BufferSerializer} for the given type.
         *
         * @param clazz the class type
         * @param serializer the serializer to use
         * @param <T> the type
         * @return this builder
         */
        public <T> Builder registerSerializer(@NotNull Class<T> clazz, @NotNull BufferSerializer<T> serializer) {
            serializers.put(clazz, serializer);
            return this;
        }

        /**
         * Builds the {@link NettyBuffer} instance with all registered serializers and deserializers.
         *
         * @return the configured {@link NettyBuffer}
         */
        public NettyBuffer build() {
            return new NettyBuffer(deserializers, serializers);
        }
    }
}
