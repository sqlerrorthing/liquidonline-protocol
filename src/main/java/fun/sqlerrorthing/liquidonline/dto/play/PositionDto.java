package fun.sqlerrorthing.liquidonline.dto.play;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
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
    @SerializedName("x")
    float x;

    /**
     * The player's Y coordinate in the world.
     */
    @SerializedName("y")
    float y;

    /**
     * The player's Z coordinate in the world.
     */
    @SerializedName("z")
    float z;
}
