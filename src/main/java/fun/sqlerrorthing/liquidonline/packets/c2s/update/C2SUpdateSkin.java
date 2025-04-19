package fun.sqlerrorthing.liquidonline.packets.c2s.update;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Sent when a player's current head skin is updated.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SUpdateSkin implements Packet {
    /**
     * Player head skin image 16x16.
     * In png format encoded in base64.
     */
    @NotNull
    @org.jetbrains.annotations.NotNull
    String skin;

    @Override
    public byte id() {
        return 6;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
