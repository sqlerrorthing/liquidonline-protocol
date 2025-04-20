package fun.sqlerrorthing.liquidonline.packets.c2s.update;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.SharedConstants;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sent when a player's current playing server changes.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SUpdatePlayingServer implements Packet {
    /**
     * The current server the client is playing on.
     * Use <b>singleplayer</b> if the player is in the local world.
     */
    @Nullable
    @Pattern(
            regexp = SharedConstants.SERVER_IP_PATTERN,
            message = "The server ip does not validate against this regular expression: " + SharedConstants.SERVER_IP_PATTERN
    ) String server;

    @Override
    public byte id() {
        return 5;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
