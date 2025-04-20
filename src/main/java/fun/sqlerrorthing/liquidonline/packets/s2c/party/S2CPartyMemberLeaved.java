package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.PartyMemberDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the packet sent from the server to all party members, notifying them
 * that a party member has left the party or was removed.
 * <p>
 *     This packet contains the details of the player who has left the party, either voluntarily
 *     or due to being removed by the party owner or another player with the necessary permissions.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyMemberLeaved implements Packet {
    int memberId;

    @Override
    public byte id() {
        return 29;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
