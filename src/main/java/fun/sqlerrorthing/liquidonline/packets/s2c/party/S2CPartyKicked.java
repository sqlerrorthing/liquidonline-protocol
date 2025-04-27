package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent to the client when they are kicked from the party.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyKicked implements Packet {
    /**
     * The reason for being kicked from the party.
     */
    @SerializedName("r")
    Reason reason;

    public enum Reason {
        /**
         * The party was disbanded.
         */
        @SerializedName("a")
        DISBANDED,

        /**
         * The user was kicked by the party owner
         */
        @SerializedName("b")
        KICKED
    }

    @Override
    public byte id() {
        return 30;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
