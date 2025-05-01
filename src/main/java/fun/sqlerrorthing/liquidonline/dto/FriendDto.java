package fun.sqlerrorthing.liquidonline.dto;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

/**
 * Data Transfer Object (DTO) representing a friend in the system.
 * <p>
 * This DTO holds various information about a friend, including their username,
 * online status, and Minecraft-related data.
 *
 * <p><b>Validation rules:</b></p>
 * <ul>
 *     <li>If {@code online} is {@code true}, then {@code minecraftUsername} and {@code skin} must not be {@code null}.</li>
 *     <li>If {@code online} is {@code false}, then {@code lastOnline} must not be {@code null}.</li>
 *     <li>If {@code server} is not {@code null} or equals {@code "singleplayer"},
 *         this indicates that the friend is playing in a local (singleplayer) world.</li>
 * </ul>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    /**
     * The friend's id.
     */
    @SerializedName("a")
    @UnsignedNumber
    int id;

    /**
     * The friend's username.
     * <p>Must not be {@code null}.</p>
     */
    @SerializedName("b")
    @NotNull
    @jakarta.validation.constraints.NotNull
    String username;

    /**
     * The friend's online status.
     * <p>
     *     If {@code true}, {@code minecraftUsername} and {@code skin} must be non-null.
     *     If {@code false}, {@code lastOnline} must be non-null.
     * </p>
     */
    @SerializedName("c")
    boolean online;

    /**
     * The friend's Minecraft username.
     * <p>Must not be {@code null} if {@code online} is {@code true}.</p>
     */
    @SerializedName("d")
    @Nullable
    String minecraftUsername;

    /**
     * The friend's Minecraft skin.
     * <p>
     *     Must not be {@code null} if {@code online} is {@code true}.
     *     A 16x16 pixel PNG image of the player's head skin.
     * </p>
     */
    @SerializedName("e")
    byte @NotNull [] skin;

    /**
     * The server the friend is currently connected to.
     * <p>
     *     If not {@code null} or equals {@code "singleplayer"},
     *     the friend is playing in a local singleplayer world or server.
     * </p>
     */
    @SerializedName("f")
    @Nullable
    String server;

    /**
     * The timestamp of the last time the friend was online.
     * <p>Must not be {@code null} if {@code online} is {@code false}.</p>
     */
    @SerializedName("g")
    @Nullable
    Instant lastOnline;
}