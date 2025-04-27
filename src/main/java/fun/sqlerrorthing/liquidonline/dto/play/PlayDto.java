package fun.sqlerrorthing.liquidonline.dto.play;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Representing the in-game session state of a party member.
 * <p>
 *     This class contains information about the player's current location, health,
 *     and orientation within the game world.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PlayDto {
    /**
     * The unique entity identifier of the player in the game world.
     * <p>
     *     This ID represents the player entity within the current world instance.
     * </p>
     */
    @SerializedName("a")
    int entityId;

    /**
     * The dimension in which the player is currently located.
     * <p>
     *     This value represents the name of the Minecraft dimension.
     * </p>
     */
    @SerializedName("b")
    @NotNull
    @jakarta.validation.constraints.NotNull
    String dimension;

    /**
     * The server address the player is currently connected to.
     * <p>
     *     If the player is in a single-player local world, this value is set to {@code "singleplayer"}.
     *     Otherwise, it holds the IP address of the multiplayer server.
     * </p>
     */
    @SerializedName("c")
    @NotNull
    @jakarta.validation.constraints.NotNull
    String server;

    /**
     * The player's current health status.
     */
    @SerializedName("d")
    @NotNull
    @jakarta.validation.constraints.NotNull
    HealthDto health;

    /**
     * The player's current position in the game world.
     */
    @SerializedName("e")
    @NotNull
    @jakarta.validation.constraints.NotNull
    PositionDto position;

    /**
     * The player's current rotation in the game world.
     */
    @SerializedName("f")
    @NotNull
    @jakarta.validation.constraints.NotNull
    RotationDto rotation;
}
