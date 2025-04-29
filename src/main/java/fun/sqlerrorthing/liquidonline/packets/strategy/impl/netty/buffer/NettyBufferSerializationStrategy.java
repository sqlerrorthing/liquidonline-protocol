package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.NettyBuffer;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class NettyBufferSerializationStrategy implements PacketSerializationStrategy {
    @NotNull
    private final NettyBuffer nettyBuffer;

    public NettyBufferSerializationStrategy() {
        this.nettyBuffer = new NettyBuffer();
    }

    @Override
    public byte @NotNull [] deserializePacket(@NotNull Packet packet) throws IOException {
        var packetBuffer = Unpooled.buffer();

        packetBuffer.writeByte(packet.id());
        nettyBuffer.serialize(packetBuffer, packet);

        return packetBuffer.array();
    }

    @Override
    public @NotNull Packet serializePacket(byte @NotNull [] deserializedPacket) throws IOException {
        var packetBuffer = Unpooled.wrappedBuffer(deserializedPacket);

        byte id = packetBuffer.readByte();
        var packetClazz = Packets.PACKETS_WITH_ID.get(id);

        return nettyBuffer.deserialize(packetBuffer, packetClazz);
    }
}
