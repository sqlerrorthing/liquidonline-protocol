package fun.sqlerrorthing.liquidonline.dto.play;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A marker in the world, including its position and optionally an associated entity.
 * The {@code MarkerDto} provides information about the marker's location and, if applicable,
 * the entity to which the marker is attached.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MarkerDto {
    /**
     * The position of the marker. This field must not be {@code null}.
     */
    @SerializedName("a")
    @NotNull
    @jakarta.validation.constraints.NotNull
    PositionDto position;

    /**
     * The entity ID associated with the marker, or {@code null} if the marker is not associated with an entity.
     * If provided, this is used to "lock" the marker to the specified entity.
     */
    @SerializedName("b")
    @Nullable
    Integer entityId;
}
