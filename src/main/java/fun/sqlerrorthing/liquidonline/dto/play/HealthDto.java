package fun.sqlerrorthing.liquidonline.dto.play;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Data Transfer Object (DTO) representing the health status of a player.
 * <p>
 *     Contains information about the player's current health, maximum possible health,
 *     and recent damage state.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto {
    /**
     * The player's current health value.
     * <p>
     *     Represents the current number of health points the player has.
     *     Typically ranges from {@code 0.0f} (dead) to {@code maxHealth}.
     * </p>
     */
    @SerializedName("a")
    float health;

    /**
     * The maximum possible health value for the player.
     * <p>
     *     Indicates the highest number of health points the player can have.
     * </p>
     */
    @SerializedName("b")
    float maxHealth;

    /**
     * The time since the player last took damage.
     * <p>
     *     Represents the number of ticks since the last time the player was hurt.
     *     This can be used for invulnerability frames or damage animations.
     * </p>
     */
    @SerializedName("c")
    float hurtTime;
}
