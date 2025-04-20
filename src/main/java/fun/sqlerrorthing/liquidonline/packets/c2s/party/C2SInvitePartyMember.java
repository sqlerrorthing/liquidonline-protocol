package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.SharedConstants;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SInvitePartyMember implements Packet {
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
