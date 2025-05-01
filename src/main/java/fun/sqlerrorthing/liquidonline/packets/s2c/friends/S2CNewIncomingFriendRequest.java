package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Notifies when the user receive a new friend request
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CNewIncomingFriendRequest implements Packet {
    /**
     * Who did this request come from
     */
    @SerializedName("f")
    @org.jetbrains.annotations.NotNull
    @NotNull
    String from;

    @SerializedName("i")
    @UnsignedNumber
    int requestId;

    @Override
    public byte id() {
        return 10;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
