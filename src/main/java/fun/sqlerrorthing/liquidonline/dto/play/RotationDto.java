package fun.sqlerrorthing.liquidonline.dto.play;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The rotation of a player in the game world.
 * <p>
 *     Contains the yaw and pitch values that describe the direction the player is currently facing.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RotationDto {
    /**
     * The yaw (horizontal rotation) of the player in degrees.
     * <br><br>
     * Constraints:
     * {@code -180 <= yaw <= 180}
     */
    @SerializedName("y")
    float yaw;

    /**
     * The pitch (vertical rotation) of the player in degrees.
     * <br><br>
     * Constraints:
     * {@code -90 <= pitch <= 90}
     */
    @SerializedName("p")
    float pitch;
}
