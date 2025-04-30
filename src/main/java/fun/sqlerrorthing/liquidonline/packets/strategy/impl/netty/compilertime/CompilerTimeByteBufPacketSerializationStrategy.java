package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.NettyBuffer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.NettyBufferPacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl.ByteBufReaderImpl;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl.ByteBufWriterImpl;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * A {@link PacketSerializationStrategy} that uses compile-time knowledge to serialize and deserialize packets
 * directly to and from Netty's {@link io.netty.buffer.ByteBuf}, avoiding the overhead of reflection.
 * <p>
 *     During compilation, all classes extending {@link Packet} are automatically enhanced to also implement
 *     the {@link PacketSerializable} interface. This enables efficient serialization and deserialization
 *     by calling {@link PacketSerializable#writeSelf(fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter)} and
 *     {@link PacketSerializable#readSelf(fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader)} directly.
 * </p>
 *
 * <p>
 *     If a packet does not implement {@link PacketSerializable} (e.g., not compiled with the enhancement),
 *     and {@code useRuntimeInFallback} is {@code true}, the fallback runtime strategy
 *     {@link NettyBufferPacketSerializationStrategy} is used. This fallback uses reflection
 *     to serialize and deserialize packet data into a {@link io.netty.buffer.ByteBuf}.
 * </p>
 *
 * <p>
 *     This strategy significantly improves performance for packets compiled with the compile-time enhancement
 *     by avoiding reflective access, which is especially beneficial in high-throughput network systems.
 * </p>
 *
 * <p>
 *     The default Netty buffer library is not included in the classpath,
 *     so it needs to be added manually for this strategy to work.
 * </p>
 *
 * @see NettyBufferPacketSerializationStrategy
 * @see NettyBuffer
 * @see Unpooled
 */
public class CompilerTimeByteBufPacketSerializationStrategy implements PacketSerializationStrategy {
    @Nullable
    private final NettyBufferPacketSerializationStrategy runtimeSerializationStrategy;

    public CompilerTimeByteBufPacketSerializationStrategy() {
        this(true);
    }

    public CompilerTimeByteBufPacketSerializationStrategy(boolean useRuntimeInFallback) {
        if (useRuntimeInFallback) {
            this.runtimeSerializationStrategy = new NettyBufferPacketSerializationStrategy();
        } else {
            this.runtimeSerializationStrategy = null;
        }
    }

    @Override
    public byte @NotNull [] serializePacket(@NotNull Packet packet) throws IOException {
        try {
            if (packet instanceof PacketSerializable serialization) {
                var byteBuffer = Unpooled.buffer();
                var writer = new ByteBufWriterImpl(byteBuffer);

                writer.writeByte(packet.id());
                serialization.writeSelf(writer);

                byte[] bytes = new byte[byteBuffer.writerIndex()];
                byteBuffer.getBytes(0, bytes);
                return bytes;
            } else {
                var ex = new UnsupportedOperationException("Packet class does not implement PacketSerialization");

                if (runtimeSerializationStrategy == null) {
                    throw ex;
                }

                return runtimeSerializationStrategy.serializePacket(packet);
            }
        } catch (Exception e) {
            throw new IOException("Failed to deserialize packet", e);
        }
    }

    @Override
    public @NotNull Packet deserializePacket(byte @NotNull [] serializedPacket) throws IOException {
        var buffer = Unpooled.wrappedBuffer(serializedPacket);
        var reader = new ByteBufReaderImpl(buffer);

        byte id = reader.readByte();
        var packetClazz = Packets.PACKETS_WITH_ID.get(id);

        try {
            if (PacketSerializable.class.isAssignableFrom(packetClazz)) {
                PacketSerializable packet = (PacketSerializable) packetClazz.getDeclaredConstructor().newInstance();

                packet.readSelf(reader);

                return (Packet) packet;
            } else {
                var ex = new UnsupportedOperationException("Packet class does not implement PacketSerialization");

                if (runtimeSerializationStrategy == null) {
                    throw ex;
                }

                return runtimeSerializationStrategy.deserializePacket(serializedPacket);
            }
        } catch (Exception e) {
            throw new IOException("Failed to deserialize packet", e);
        }
    }
}
