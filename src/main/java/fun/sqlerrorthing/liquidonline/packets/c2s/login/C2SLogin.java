package fun.sqlerrorthing.liquidonline.packets.c2s.login;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Represents the login packet sent from the client to the server upon initial connection.
 * This packet contains the authentication token for the account.
 *
 * <p>The packet is sent by the client when establishing a connection with the server
 * to authenticate the user using the provided token.</p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SLogin implements Packet {
    /**
     * The authentication token used to verify the client's account.
     * This token is sent from the client to the server for user authentication.
     */
    @org.jetbrains.annotations.NotNull
    @NotNull
    String token;

    @Override
    public byte getId() {
        return 0;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound getPacketBound() {
        return PacketBound.CLIENT;
    }
}
