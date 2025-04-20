package fun.sqlerrorthing.liquidonline.dto.party;

import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PartyMember {
    int memberId;

    @NotNull
    @jakarta.validation.constraints.NotNull
    String minecraftUsername;

    @NotNull
    @jakarta.validation.constraints.NotNull
    String skin;

    @NotNull
    @jakarta.validation.constraints.NotNull
    Color color;

    @Nullable
    PlayDto playData;
}
