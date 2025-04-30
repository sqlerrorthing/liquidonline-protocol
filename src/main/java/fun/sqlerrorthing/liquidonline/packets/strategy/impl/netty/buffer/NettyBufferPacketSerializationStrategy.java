package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * A strategy for serializing and deserializing packets using Netty's {@link Unpooled} buffer and {@link NettyBuffer}.
 * This class provides an efficient implementation of packet serialization and deserialization with minimal overhead.
 * It is highly optimized for performance and uses Netty's buffer mechanism for fast byte array manipulation.
 * <p>
 *     The default Netty buffer library is not included in the classpath,
 *     so it needs to be added manually for this strategy to work.
 * </p>
 *
 * @see PacketSerializationStrategy
 * @see NettyBuffer
 * @see Unpooled
 */
public class NettyBufferPacketSerializationStrategy implements PacketSerializationStrategy {
    @NotNull
    private final NettyBuffer nettyBuffer;

    public NettyBufferPacketSerializationStrategy() {
        this.nettyBuffer = NettyBuffer.builder().build();
    }

    @Override
    public byte @NotNull [] serializePacket(@NotNull Packet packet) throws IOException {
        var packetBuffer = Unpooled.buffer();

        packetBuffer.writeByte(packet.id());
        nettyBuffer.serialize(packetBuffer, packet);

        byte[] bytes = new byte[packetBuffer.writerIndex()];
        packetBuffer.getBytes(0, bytes);

        return bytes;
    }

    @Override
    public @NotNull Packet deserializePacket(byte @NotNull [] serializedPacket) throws IOException {
        var packetBuffer = Unpooled.wrappedBuffer(serializedPacket);

        byte id = packetBuffer.readByte();
        var packetClazz = Packets.PACKETS_WITH_ID.get(id);

        return nettyBuffer.deserialize(packetBuffer, packetClazz);
    }
}
