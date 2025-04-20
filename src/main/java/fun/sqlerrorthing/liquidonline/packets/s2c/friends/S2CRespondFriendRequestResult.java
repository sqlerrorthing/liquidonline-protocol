package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.dto.FriendDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Packet sent from the server to the client in response to a {@code C2SRespondFriendRequest}.
 *
 * <p>
 *     If the {@code status} is {@code ACCEPTED}, the {@code friend} field will contain the accepted friend's data.
 * </p>
 * <p>
 *     If the {@code status} is {@code REQUEST_NOT_FOUND}, the {@code friend} field will be {@code null}.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CRespondFriendRequestResult implements Packet {
    /**
     * The result status of the friend request response operation.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Status status;

    /**
     * Information about the accepted friend.
     * <p>
     * This field is non-null only when {@code status} is {@link Status#SUCCESS} and the friend request was {@link fun.sqlerrorthing.liquidonline.packets.c2s.friends.C2SRespondFriendRequest.Status#ACCEPTED}.
     * Otherwise, it will be {@code null}.
     */
    @Nullable
    FriendDto friend;

    public enum Status {
        SUCCESS,

        REQUEST_NOT_FOUND,
    }

    @Override
    public byte id() {
        return 19;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}