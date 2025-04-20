package fun.sqlerrorthing.liquidonline.packets.c2s.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SharedConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Responds to an incoming friend request
 * <p>
 *     The requester can send this packet, but only with {@link Status#REJECT} to revoke their own friend request.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SRespondFriendRequest implements Packet {
    int requestId;

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
        return 9;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}