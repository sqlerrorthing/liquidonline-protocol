package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
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
    @NotNull
    @jakarta.validation.constraints.NotNull
    Status status;

    @Nullable
    Integer requestId;

    public enum Status {
        /**
         * The user sent a friend request to someone who already has this friend request pending.
         * Then we consider that he accepted this friend request.
         */
        ACCEPTED,

        /**
         * The user not found
         */
        NOT_FOUND,

        /**
         * Friend request successfully sent
         */
        REQUESTED,

        /**
         * Friend request already sent
         */
        ALREADY_REQUESTED,

        /**
         * This player is already friends with this player
         */
        ALREADY_FRIENDS,

        /**
         * The request sent to self
         */
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
