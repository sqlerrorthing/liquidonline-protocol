package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent from the client to the server when the current party owner wants to transfer party ownership to another member.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2STransferPartyOwnership implements Packet {
    /**
     * The unique ID of the member who should become the new party owner.
     */
    @SerializedName("i")
    int memberId;

    @Override
    public byte id() {
        return 38;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
