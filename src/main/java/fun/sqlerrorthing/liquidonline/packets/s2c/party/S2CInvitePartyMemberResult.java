package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The response packet after an attempt to invite a new member to a party.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CInvitePartyMemberResult implements Packet {
    /**
     * The result of the invitation attempt.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Result result;

    /**
     * The details of the invited user.
     * <p>
     *     This field is populated only if the result is {@link Result#INVITED}. It contains the invitation details of
     *     the user who has been invited to the party.
     * </p>
     */
    @Nullable
    InvitedMemberDto invite;

    public enum Result {
        /**
         * The invitation was successfully sent to the user.
         */
        INVITED,

        /**
         * The user attempting to send the invite is not part of any party.
         */
        NOT_IN_A_PARTY,

        /**
         * The user attempting to send the invite to person who is already in the same party.
         */
        ALREADY_IN_A_PARTY,

        /**
         * The user attempting to send the invite does not have the necessary permissions.
         */
        NOT_ENOUGH_RIGHTS,

        /**
         * The receiver doesn't online or not sender's friend
         */
        NOT_FOUND
    }

    @Override
    public byte id() {
        return 25;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
