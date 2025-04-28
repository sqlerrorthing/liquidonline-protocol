package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sent from the server to all party members when a player's in-game data has been updated.
 * <p>
 * If the player is not currently playing or their data is not available, the {@code data} field
 * can be {@code null}.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyMemberPlayUpdate implements Packet {
    /**
     * The ID of the party member whose play data was updated.
     */
    @SerializedName("i")
    int memberId;

    /**
     * The updated play data of the party member. Can be {@code null} if the player is not currently playing.
     */
    @SerializedName("d")
    @Nullable
    PlayDto data;

    @Override
    public byte id() {
        return 43;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
