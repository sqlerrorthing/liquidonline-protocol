package fun.sqlerrorthing.liquidonline.packets.c2s.update;

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
 * Sent when a player's nickname changes.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SUpdateMinecraftUsername implements Packet {
    /**
     * New minecraft username.
     */
    @SerializedName("u")
    @org.jetbrains.annotations.NotNull
    @NotNull
    @Pattern(
            regexp = SharedConstants.MINECRAFT_USERNAME_PATTERN,
            message = "The username does not validate against this regular expression: " + SharedConstants.MINECRAFT_USERNAME_PATTERN
    ) String username;

    @Override
    public byte id() {
        return 4;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
