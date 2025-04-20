package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent from the server to the client in response to a kick request for a party member.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyMemberKickResult implements Packet {
    /**
     * The result of the kick action.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Result result;

    public enum Result {
        /**
         * The player was successfully kicked from the party
         */
        KICKED,

        /**
         * The requester lacked the rights to kick the player
         */
        NO_ENOUGH_RIGHTS,

        /**
         * The player was not found or is not in the party
         */
        NOT_FOUND
    }

    @Override
    public byte id() {
        return 37;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
