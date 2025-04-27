package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Response when friendship deletion request was sent
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CStopBeingFriendsResult implements Packet {
    @SerializedName("s")
    @NotNull
    @jakarta.validation.constraints.NotNull
    Status status;

    public enum Status {
        /**
         * Friendship is over
         */
        @SerializedName("a")
        SUCCESS,

        /**
         * There was no friendship between the said player
         */
        @SerializedName("b")
        WAS_NO_FRIENDSHIP
    }

    @Override
    public byte id() {
        return 12;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
