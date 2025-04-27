package fun.sqlerrorthing.liquidonline.packets.s2c.party;


import fun.sqlerrorthing.liquidonline.dto.party.PartyDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sent from the server to the client in response to a party invite response (accepted or declined).
 * <p>
 *     This packet indicates the result of the player's response to the invite, and if the response was successful,
 *     it includes the details of the party the player was invited to. If the invite was not found or the player is already
 *     in another party, the appropriate result is returned instead.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyInviteResponseStatus implements Packet {
    /**
     * The result of the invite response.
     */
    @SerializedName("r")
    @NotNull
    @jakarta.validation.constraints.NotNull
    Result result;

    /**
     * The details of the party, only included if the response was successful.
     * <p>
     * If the {@code result} is {@code SUCCESS} and invite was accepted, this field contains the details of the party the player was invited to.
     * </p>
     */
    @SerializedName("p")
    @Nullable
    PartyDto party;

    public enum Result {
        /**
         * The player successfully accepted the invite and party details are included
         */
        @SerializedName("a")
        SUCCESS,

        /**
         * The invite was not found
         */
        @SerializedName("b")
        INVITE_NOT_FOUND,

        /**
         * The player is already in another party
         */
        @SerializedName("c")
        ALREADY_IN_ANOTHER_PARTY,

        /**
         * The number of members in the party exceeds the limit
         */
        @SerializedName("d")
        PARTY_MEMBERS_LIMIT
    }

    @Override
    public byte id() {
        return 34;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
