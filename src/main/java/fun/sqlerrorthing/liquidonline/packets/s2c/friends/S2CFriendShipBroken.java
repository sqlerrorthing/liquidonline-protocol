package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Notifies that friendship with this user has been broken.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CFriendShipBroken implements Packet {
    /**
     * With whom friendship was destroyed
     * Contains the id of a friend (former).
     */
    @SerializedName("i")
    @UnsignedNumber
    int with;

    @Override
    public byte id() {
        return 13;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}