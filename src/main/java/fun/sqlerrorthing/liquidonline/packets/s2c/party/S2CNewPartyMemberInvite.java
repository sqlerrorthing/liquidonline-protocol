package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the packet sent from the server to all party members, notifying them
 * that a new user has been invited to the party.
 * <p>
 *     This packet contains the details of the invited user, informing all participants of the invitation.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CNewPartyMemberInvite implements Packet {
    /**
     * The details of the invited member.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    InvitedMemberDto invitedMember;

    @Override
    public byte id() {
        return 26;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
