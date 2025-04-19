package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.c2s.friends.C2SRespondFriendRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Sent when a user has accepted or rejected a request from this user.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2COutgoingFriendRequest implements Packet {
    /**
     * To whom was it sent
     */
    @org.jetbrains.annotations.NotNull
    @NotNull
    String to;

    /**
     * Reply status
     */
    @org.jetbrains.annotations.NotNull
    @NotNull
    Status status;

    public enum Status {
        /**
         * Friend request accepted
         */
        ACCEPTED,

        /**
         * Friend request rejected
         */
        REJECT,
    }

    @Override
    public byte id() {
        return 14;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}