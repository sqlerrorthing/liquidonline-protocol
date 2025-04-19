package fun.sqlerrorthing.liquidonline.packets.c2s;

import fun.sqlerrorthing.liquidonline.packets.Bound;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Represents the login packet sent from the client to the server upon initial connection.
 * This packet contains the authentication token for the account.
 *
 * <p>The packet is sent by the client when establishing a connection with the server
 * to authenticate the user using the provided token.</p>
 *
 * <p>The {@link #token} field holds the authentication token which is used by the
 * server to verify the client's identity and authorize access to the system.</p>
 */
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Bound(PacketBound.CLIENT)
public class C2SLogin implements Packet {
    /**
     * The authentication token used to verify the client's account.
     * This token is sent from the client to the server for user authentication.
     */
    String token;

    @Override
    public byte getId() {
        return 0;
    }
}
