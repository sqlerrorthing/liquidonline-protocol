package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter;

/**
 * Marker interface for packets that support custom byte-level serialization and deserialization.
 * <p>
 *     Implementing this interface allows a {@link fun.sqlerrorthing.liquidonline.packets.Packet} to define how it writes its own state
 *     into a {@link ByteBufWriter} and how it reconstructs itself from a {@link ByteBufReader}.
 *     This is used during high-performance serialization where reflection is avoided.
 * </p>
 */
public interface PacketSerializable {
    /**
     * Writes the current packet's state into the given {@link ByteBufWriter}.
     * <p>
     *     This method is called during packet serialization to encode the packet's contents
     *     into a byte buffer.
     * </p>
     *
     * @param output the writer used to write the packet's data
     */
    void writeSelf(ByteBufWriter output);

    /**
     * Reads the packet's state from the given {@link ByteBufReader}.
     * <p>
     *     This method is called during packet deserialization to reconstruct the packet
     *     from a byte buffer.
     * </p>
     *
     * @param input the reader used to read the packet's data
     */
    void readSelf(ByteBufReader input);
}
