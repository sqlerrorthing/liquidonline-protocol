package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sent from the client to the server to update the current play information of the member in the party.
 * <p>
 * If the player is not currently in the game (e.g., not connected to a server), the {@code play} field can be {@code null}.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SPartyPlayUpdate implements Packet {
    /**
     * The current play data of the player in the party. Can be {@code null} if the player is not actively playing.
     */
    @Nullable
    PlayDto data;

    @Override
    public byte id() {
        return 42;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
