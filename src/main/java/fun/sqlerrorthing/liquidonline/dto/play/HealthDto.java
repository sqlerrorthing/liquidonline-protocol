package fun.sqlerrorthing.liquidonline.dto.play;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto {
    float health;

    float maxHealth;

    float hurtTime;
}
