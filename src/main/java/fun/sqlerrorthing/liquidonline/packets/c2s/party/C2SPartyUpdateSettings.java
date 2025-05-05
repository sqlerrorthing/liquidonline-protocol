package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Sent by the party owner for update party settings
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SPartyUpdateSettings implements Packet {
    boolean isPubic;

    public static C2SPartyUpdateSettings isPublic(boolean isPublic) {
        return C2SPartyUpdateSettings.builder()
                .isPubic(isPublic)
                .build();
    }

    public static C2SPartyUpdateSettings nowPublic() {
        return isPublic(true);
    }

    public static C2SPartyUpdateSettings nowPrivate() {
        return isPublic(false);
    }

    @Override
    public byte id() {
        return 68;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
