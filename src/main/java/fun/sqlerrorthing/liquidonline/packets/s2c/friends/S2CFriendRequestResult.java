package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Response when friend request was sent
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CFriendRequestResult implements Packet {
    @SerializedName("s")
    @NotNull
    @jakarta.validation.constraints.NotNull
    Status status;

    @SerializedName("9")
    @Nullable
    @UnsignedNumber
    Integer requestId;

    public enum Status {
        /**
         * The user sent a friend request to someone who already has this friend request pending.
         * Then we consider that he accepted this friend request.
         */
        @SerializedName("a")
        ACCEPTED,

        /**
         * The user not found
         */
        @SerializedName("b")
        NOT_FOUND,

        /**
         * Friend request successfully sent
         */
        @SerializedName("c")
        REQUESTED,

        /**
         * Friend request already sent
         */
        @SerializedName("d")
        ALREADY_REQUESTED,

        /**
         * This player is already friends with this player
         */
        @SerializedName("e")
        ALREADY_FRIENDS,

        /**
         * The request sent to self
         */
        @SerializedName("f")
        SENT_TO_SELF
    }

    @Override
    public byte id() {
        return 8;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
