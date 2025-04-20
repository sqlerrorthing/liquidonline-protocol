package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the packet sent from the server to all party members, notifying them
 * that an invitation to a player has been declined.
 * <p>
 *     This packet contains the details of the invited player who has declined the invitation,
 *     either by themselves or by another member with the appropriate permissions.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyInviteDeclined implements Packet {
    /**
     * The details of the invited player who has declined the invitation.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    InvitedMemberDto invitedMember;

    @Override
    public byte id() {
        return 27;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
