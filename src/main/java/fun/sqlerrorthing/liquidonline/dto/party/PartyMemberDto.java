package fun.sqlerrorthing.liquidonline.dto.party;

import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * Represents a member of a party.
 * <p>
 * This class stores information about an individual user participating in a party,
 * including account details, in-game appearance, and online status.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PartyMemberDto {
    /**
     * Unique identifier of the member in the account system.
     * <p>
     *     This ID is the global identifier for the user in the account management system.
     *     A separate party-specific member ID is not allocated.
     * </p>
     */
    int memberId;

    /**
     * The display username of the member within the party.
     * <p>
     *     This is the name visible to other members inside the party.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    String username;

    /**
     * The Minecraft in-game username of the member.
     * <p>
     *     This is the name used by the player in the Minecraft environment.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    String minecraftUsername;

    /**
     * The player’s head skin image.
     * <p>
     *     This field contains a 16x16 pixel PNG image of the player's head skin,
     *     encoded as a Base64 string.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    String skin;

    /**
     * The color assigned to this party member.
     * <p>
     *     This color can be used to visually distinguish the player in the party UI or in-game.
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Color color;

    /**
     * Data about the player's current game session.
     * <p>
     *     This field is non-null when the player is currently connected to a server or local world.
     *     If the player is offline or not in-game, this value is {@code null}.
     * </p>
     */
    @Nullable
    PlayDto playData;
}
