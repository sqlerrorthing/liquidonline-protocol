package fun.sqlerrorthing.liquidonline.dto.party;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * An invited member to a party.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class InvitedMemberDto {
    /**
     * The unique identifier for the invitation.
     * <p>
     *     This ID uniquely identifies the invitation request, not the party itself.
     *     It is used to track and manage individual invitations.
     * </p>
     */
    @SerializedName("a")
    @NotNull
    @jakarta.validation.constraints.NotNull
    UUID inviteUuid;

    /**
     * The username of the invited user.
     */
    @SerializedName("b")
    @NotNull
    @jakarta.validation.constraints.NotNull
    String username;

    /**
     * The ID of the user who sent the invitation.
     * <p>
     *     Only the owner or members (in a public party) can send invitations, and this ID helps track
     *     who invited the member.
     * </p>
     */
    @SerializedName("c")
    int senderId;
}
