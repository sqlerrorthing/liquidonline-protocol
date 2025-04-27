package fun.sqlerrorthing.liquidonline.packets.c2s.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import jakarta.validation.constraints.NotNull;
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
    @SerializedName("r")
    int requestId;

    /**
     * Reply status
     */
    @SerializedName("s")
    @org.jetbrains.annotations.NotNull
    @NotNull
    Status status;

    public enum Status {
        /**
         * Friend request accepted
         */
        @SerializedName("a")
        ACCEPTED,

        /**
         * Friend request rejected
         */
        @SerializedName("r")
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