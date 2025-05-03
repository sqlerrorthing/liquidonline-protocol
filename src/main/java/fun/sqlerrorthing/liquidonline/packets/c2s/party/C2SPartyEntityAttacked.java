package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent when a party member attacked an entity in the world.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SPartyEntityAttacked implements Packet {
    /**
     * Attacked entity id.
     */
    int entityId;

    @Override
    public byte id() {
        return 83;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
