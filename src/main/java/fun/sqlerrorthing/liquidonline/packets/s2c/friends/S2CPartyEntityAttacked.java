package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent to all party members when member attacked an entity.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyEntityAttacked implements Packet {
    /**
     * The party member.
     */
    @UnsignedNumber
    int memberId;

    /**
     * The attacked entity in the world.
     */
    int attackedId;

    @Override
    public byte id() {
        return 80;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
