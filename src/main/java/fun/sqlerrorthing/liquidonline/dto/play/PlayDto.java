package fun.sqlerrorthing.liquidonline.dto.play;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PlayDto {
    int entityId;

    @NotNull
    @jakarta.validation.constraints.NotNull
    String dimension;

    @NotNull
    @jakarta.validation.constraints.NotNull
    String server;

    @NotNull
    @jakarta.validation.constraints.NotNull
    HealthDto health;

    @NotNull
    @jakarta.validation.constraints.NotNull
    PositionDto position;

    @NotNull
    @jakarta.validation.constraints.NotNull
    RotationDto rotation;
}
