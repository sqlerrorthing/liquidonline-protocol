package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl.ByteBufReaderImpl;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl.ByteBufWriterImpl;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CompilerTimeByteBufPacketSerializationStrategy implements PacketSerializationStrategy {
    @Override
    public byte @NotNull [] deserializePacket(@NotNull Packet packet) throws IOException {
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
                throw new UnsupportedOperationException("packet is not serializable");
            }
        } catch (Exception e) {
            throw new IOException("Failed to deserialize packet", e);
        }
    }

    @Override
    public @NotNull Packet serializePacket(byte @NotNull [] deserializedPacket) throws IOException {
        var buffer = Unpooled.wrappedBuffer(deserializedPacket);
        var reader = new ByteBufReaderImpl(buffer);

        byte id = reader.readByte();
        var packetClazz = Packets.PACKETS_WITH_ID.get(id);

        try {
            if (PacketSerializable.class.isAssignableFrom(packetClazz)) {
                PacketSerializable packet = (PacketSerializable) packetClazz.getDeclaredConstructor().newInstance();

                packet.readSelf(reader);

                return (Packet) packet;
            } else {
                throw new UnsupportedOperationException("Packet class does not implement PacketSerialization");
            }
        } catch (Exception e) {
            throw new IOException("Failed to deserialize packet", e);
        }
    }
}
