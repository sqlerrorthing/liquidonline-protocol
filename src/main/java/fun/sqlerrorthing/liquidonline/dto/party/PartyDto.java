package fun.sqlerrorthing.liquidonline.dto.party;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Representing a Party.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto {
    /**
     * The unique identifier for the party.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    UUID id;

    /**
     * The name of the party.
     * <p>
     *     This is the display name of the party, which can be seen by its members and invited members
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    String name;

    /**
     * Indicates whether the party is public or private.
     * <p>
     *     If the party is public ({@code true}), any member can invite new people to join. If it is private ({@code false}),
     *     only the owner can invite new members.
     * </p>
     */
    boolean partyPublic;

    /**
     * The ID of the party owner.
     * <p>
     *     This represents the unique identifier of the person who owns the party. The owner has special privileges,
     *     such as the ability to control the invitation of new members if the party is private.
     * </p>
     */
    int ownerId;

    /**
     * A list of party members.
     * <p>
     *     This list contains all the people who are part of the party.
     *     <br><br>
     *     Only the owner of the party has the right to:
     *     <ul>
     *         <li>Kick members from the party.</li>
     *         <li>Dissolve (disband) the party entirely.</li>
     *     </ul>
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    List<PartyMember> members;

    /**
     * A list of invited members.
     * <p>
     *     This list contains users who have been invited to join the party but have not yet accepted the invitation.
     *     <br><br>
     *     Invitations can be managed as follows:
     *     <ul>
     *         <li>If the party is <b>private</b>, only the owner can send invitations.</li>
     *         <li>If the party is <b>public</b>, any current member can send invitations.</li>
     *         <li>Only the owner of the party can reject (decline) pending invitations.</li>
     *         <li>The person who sent an invitation has the right to delete (cancel) their own invitation.</li>
     *     </ul>
     * </p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    List<InvitedMember> invitedMembers;
}
