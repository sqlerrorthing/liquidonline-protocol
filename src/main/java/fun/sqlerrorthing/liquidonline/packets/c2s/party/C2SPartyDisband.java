package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import org.jetbrains.annotations.NotNull;

/**
 * Sent from the client to the server when the party owner wants to disband the party.
 * <p>
 *     Only the current party owner has the permission to send this request.
 *     2Upon successful disbanding, the server will notify all party members and remove the party.
 * </p>
 */
public class C2SPartyDisband implements Packet {
    @Override
    public byte id() {
        return 41;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
