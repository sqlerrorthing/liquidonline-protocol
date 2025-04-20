package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent from the server to all party members when the party ownership has been transferred to a new member.
 * <p>
 *     This packet notifies all current party members about the {@code newOwnerId},
 *     indicating the member who is now the owner of the party.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyOwnerTransferred implements Packet {
    /**
     * The ID of the new party owner.
     */
    int newOwnerId;

    @Override
    public byte id() {
        return 39;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
