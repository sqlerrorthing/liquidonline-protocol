package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent by the server in response to an attempt to set a marker.
 * This packet contains the result of the marker installation attempt, indicating whether
 * the attempt was successful or if it encountered any issues.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartySetMarkerResult implements Packet {
    /**
     * The result of the marker installation attempt.
     */
    @SerializedName("r")
    @NotNull
    @jakarta.validation.constraints.NotNull
    Result result;

    enum Result {
        /**
         * The player is not at a party and cannot set a marker.
         */
        @SerializedName("a")
        NOT_IN_A_PARTY,

        /**
         * The marker installation attempt exceeded some predefined limit (e.g., too many markers).
         */
        @SerializedName("b")
        LIMIT,

        /**
         * The marker was successfully installed.
         */
        @SerializedName("c")
        INSTALLED
    }

    @Override
    public byte id() {
        return 67;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
