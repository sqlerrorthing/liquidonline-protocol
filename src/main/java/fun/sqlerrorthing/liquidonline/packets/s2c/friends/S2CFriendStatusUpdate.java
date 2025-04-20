package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Packet sent from the server to the client when a friend's information has been updated.
 *
 * <p>
 *     Contains updated fields for a specific friend identified by {@code friendId}.
 * </p>
 *
 * The {@code friendId} is always present.
 * Other fields will be non-null only if their corresponding value was updated.
 * If a particular field was not updated, it will be {@code null}.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CFriendStatusUpdate implements Packet {
    /**
     * The unique identifier of the friend whose information was updated.
     * This value is always present.
     */
    int friendId;

    /**
     * The updated username of the friend.
     * Will be non-null if the username was changed; otherwise {@code null}.
     */
    @Nullable
    String username;

    /**
     * The updated Minecraft username of the friend.
     * Will be non-null if the Minecraft username was changed; otherwise {@code null}.
     */
    @Nullable
    String minecraftUsername;

    /**
     * The updated skin image of the friend, represented as a base64-encoded PNG image.
     * The image is a 16x16 pixel head texture.
     * Will be non-null if the skin was changed; otherwise {@code null}.
     */
    @Nullable
    String skin;

    /**
     * The updated server name where the friend is currently playing.
     * <p>
     *     <b>singleplayer</b> if in the local world.
     * </p>
     * Will be non-null if the server information was changed; otherwise {@code null}.
     */
    @Nullable
    String server;

    @Override
    public byte id() {
        return 20;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
