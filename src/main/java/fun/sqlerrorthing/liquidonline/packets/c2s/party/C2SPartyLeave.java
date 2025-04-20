package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import org.jetbrains.annotations.NotNull;

/**
 * Sent from the client to the server when a player wants to leave their current party.
 * <p>
 *     If the leaving player is the party owner, the server must handle either transferring ownership
 * </p>
 */
public class C2SPartyLeave implements Packet {
    @Override
    public byte id() {
        return 40;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
