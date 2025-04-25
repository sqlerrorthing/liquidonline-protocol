package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.dto.party.PartyMemberDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents the packet sent from the server to all party members, notifying them
 * that a new player has joined the party.
 * <p>
 *     This packet contains the details of the newly joined player. It is sent when a player successfully
 *     accepts an invitation to the party and becomes part of it.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyMemberJoined implements Packet {
    @NotNull
    @jakarta.validation.constraints.NotNull
    PartyMemberDto member;

    /**
     * Not null if the member joined by invite
     */
    @Nullable
    UUID inviteUuid;

    @Override
    public byte id() {
        return 28;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
