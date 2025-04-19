package fun.sqlerrorthing.liquidonline.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Representing a friend request in the system.
 * <p>
 *     This DTO contains information about a friend request, including the username of the other user
 *     involved in the request and the current status of the request.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    /**
     * The username of the user associated with this friend request.
     * <p>Must not be {@code null}.</p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    String username;

    /**
     * The status of the friend request.
     * <p>Must not be {@code null}.</p>
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Status status;

    public enum Status {
        /**
         * Indicates that this is an incoming friend request sent by another user.
         */
        INCOMING,
        
        /**
         * Indicates that this is an outgoing friend request sent by the current user.
         */
        OUTGOING,
    }
}
