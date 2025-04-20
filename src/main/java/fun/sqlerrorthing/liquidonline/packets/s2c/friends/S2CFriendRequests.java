package fun.sqlerrorthing.liquidonline.packets.s2c.friends;

import fun.sqlerrorthing.liquidonline.dto.FriendRequestDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CFriendRequests implements Packet {
    /**
     * All incoming friend request sent by another users.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    @Builder.Default
    List<FriendRequestDto> incoming = new ArrayList<>();

    /**
     * All outgoing friend requests sent by the current user.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    @Builder.Default
    List<FriendRequestDto> outgoing = new ArrayList<>();

    @Override
    public byte id() {
        return 16;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
