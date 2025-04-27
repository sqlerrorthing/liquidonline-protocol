package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.SharedConstants;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Packet sent by a party member to invite another player to the party.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SInvitePartyMember implements Packet {
    @SerializedName("u")
    @org.jetbrains.annotations.NotNull
    @NotNull
    @Pattern(
            regexp = SharedConstants.USERNAME_PATTERN,
            message = "The username does not validate against this regular expression: " + SharedConstants.USERNAME_PATTERN
    ) String username;

    @Override
    public byte id() {
        return 23;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
