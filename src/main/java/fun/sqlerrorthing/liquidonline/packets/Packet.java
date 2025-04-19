package fun.sqlerrorthing.liquidonline.packets;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a network packet with a unique positive identifier.
 * <p>
 * Each packet has a unique positive {@code id} that distinguishes it from other packets.
 * The {@code id} is used to identify the type and format of the packet, and to determine
 * how its payload should be interpreted.
 * </p>
 *
 * <p>
 * The general format of a packet is:
 * </p>
 * <pre>
 * {
 *     "id": byte,
 *     "payload": { ... }
 * }
 * </pre>
 * <p>
 * The meaning and structure of the {@code payload} is determined based on the {@code id}.
 * </p>
 */
public interface Packet {
    /**
     * Returns the unique positive identifier of this packet.
     *
     * @return the unique positive {@code id} of the packet
     */
    byte getId();

    /**
     * Returns the bound of the packet.
     *
     * @return the bound of the packet.
     * @see PacketBound
     */
    @NotNull
    PacketBound getPacketBound();
}
