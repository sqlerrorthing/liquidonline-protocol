package fun.sqlerrorthing.liquidonline.packets.c2s.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SharedConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Sends a friend request to a player
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SSendFriendRequest implements Packet {
    /**
     * Player's nickname IN THE SYSTEM, to whom to send a friend request
     */
    @org.jetbrains.annotations.NotNull
    @NotNull
    @Pattern(
            regexp = SharedConstants.USERNAME_PATTERN,
            message = "The username does not validate against this regular expression: " + SharedConstants.USERNAME_PATTERN
    ) String username;

    @Override
    public byte id() {
        return 7;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
