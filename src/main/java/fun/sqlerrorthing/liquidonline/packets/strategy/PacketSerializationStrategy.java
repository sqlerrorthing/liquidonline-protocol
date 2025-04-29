package fun.sqlerrorthing.liquidonline.packets.strategy;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Interface representing a strategy for serializing and deserializing packets.
 * <p>
 * This interface provides methods for serializing a packet into a byte array
 * and deserializing a byte array back into a packet. It is important to note that
 * the serialization and deserialization must be performed using the same strategy.
 * Using different strategies for serialization and deserialization will not work.
 * </p>
 * <p>
 * Implementations of this interface should ensure that they use consistent formats
 * for both serializing and deserializing the packet, whether it's JSON, MessagePack,
 * or any other serialization format.
 * </p>
 */
public interface PacketSerializationStrategy {
    /**
     * Serializes the given packet into a byte array.
     *
     * @param packet the packet to serialize.
     * @return the byte array representing the serialized packet.
     * @throws IOException if the packet cannot be serialized.
     */
    byte @NotNull [] serializePacket(@NotNull Packet packet) throws IOException;

    /**
     * Converts the serialized packet into a string.
     * This method uses the {@link #serializePacket(Packet)} method to
     * convert the packet into a byte array and then encodes it as a UTF-8 string.
     *
     * @param packet the packet to serialize.
     * @return a string representation of the serialized packet.
     * @throws IOException if the packet cannot be serialized.
     */
    @NotNull
    default String serializePacketToString(@NotNull Packet packet) throws IOException {
        return new String(serializePacket(packet));
    }

    /**
     * Deserializes the given byte array back into a packet.
     *
     * @param serializedPacket the byte array to serialize into a packet.
     * @return the serialized packet.
     * @throws IOException if the byte array cannot be serialized.
     */
    @NotNull
    Packet deserializePacket(byte @NotNull [] serializedPacket) throws IOException;

    /**
     * Deserializes a string representation of the serialized packet into a packet.
     * This method first converts the string into a byte array using UTF-8 encoding
     * and then calls {@link #deserializePacket(byte[])} to serialize it into a packet.
     *
     * @param serializedPacket the string representation of the serialized packet.
     * @return the serialized packet.
     * @throws IOException if the string cannot be serialized.
     */
    @NotNull
    default Packet deserializePacketFromString(@NotNull String serializedPacket) throws IOException {
        return deserializePacket(serializedPacket.getBytes(StandardCharsets.UTF_8));
    }
}
