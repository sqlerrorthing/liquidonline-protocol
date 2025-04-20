package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Sent from the client to the server when the user responds to a party invite.
 * Or when the party owner or requester wants to decline the request
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SPartyInviteResponse implements Packet {
    /**
     * The unique identifier of the invite.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    UUID inviteUuid;

    /**
     * The response to the party invite.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Response response;

    /**
     * If the invite was accepted, you need to provide up-to-date information about yourself.
     * Or leave null if there is none.
     */
    @Nullable
    PlayDto play;

    public enum Response {
        /**
         * The invite was accepted
         */
        ACCEPTED,

        /**
         * The invite was declined
         */
        DECLINED,
    }

    @Override
    public byte id() {
        return 33;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
