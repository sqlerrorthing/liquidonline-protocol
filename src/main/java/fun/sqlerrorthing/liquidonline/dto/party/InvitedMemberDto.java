package fun.sqlerrorthing.liquidonline.dto.party;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class InvitedMemberDto {
    @NotNull
    @jakarta.validation.constraints.NotNull
    UUID inviteUuid;

    @NotNull
    @jakarta.validation.constraints.NotNull
    String username;

    int senderId;
}
