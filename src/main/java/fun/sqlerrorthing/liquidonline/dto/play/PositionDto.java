package fun.sqlerrorthing.liquidonline.dto.play;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The position of a player in the game world.
 * <p>
 *     Contains the X, Y, and Z coordinates indicating the player's current location
 *     within their dimension.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    /**
     * The player's X coordinate in the world.
     */
    double x;

    /**
     * The player's Y coordinate in the world.
     */
    double y;

    /**
     * The player's Z coordinate in the world.
     */
    double z;
}
