package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.dto.FriendDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;

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
    @SerializedName("i")
    @UnsignedNumber
    int requestId;

    /**
     * To whom was it sent
     */
    @SerializedName("t")
    @org.jetbrains.annotations.NotNull
    @NotNull
    String to;

    /**
     * Reply status
     */
    @SerializedName("s")
    @org.jetbrains.annotations.NotNull
    @NotNull
    Status status;

    /**
     * Will not be null if the status is {@link S2COutgoingFriendRequest#status} {@link Status#ACCEPTED}
     */
    @SerializedName("f")
    @Nullable
    FriendDto friend;

    public enum Status {
        /**
         * Friend request accepted
         */
        @SerializedName("a")
        ACCEPTED,

        /**
         * Friend request rejected
         */
        @SerializedName("c")
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