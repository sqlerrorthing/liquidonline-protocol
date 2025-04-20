package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Sent to the user when they receive a new party invite.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyInviteReceived implements Packet {
    /**
     * The unique identifier of the invite.
     * <p>
     *     This UUID is used to identify the invite, and is necessary for accepting or declining the invite.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    UUID inviteUuid;

    /**
     * The name of the party that has invited the player.
     * <p>
     *     This is the name of the party to which the player is invited.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    String partyName;

    /**
     * The ID of the player who sent the invite.
     */
    int senderId;

    @Override
    public byte id() {
        return 31;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
