package fun.sqlerrorthing.liquidonline.dto;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
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
     * The id of the request.
     * <p>Must not be {@code null}.</p>
     */
    @SerializedName("i")
    int requestId;

    /**
     * The username of the user associated with this friend request.
     * <p>Must not be {@code null}.</p>
     */
    @SerializedName("u")
    @NotNull
    @jakarta.validation.constraints.NotNull
    String username;
}
