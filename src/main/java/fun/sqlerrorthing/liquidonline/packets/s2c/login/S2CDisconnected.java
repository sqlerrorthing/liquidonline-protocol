package fun.sqlerrorthing.liquidonline.packets.s2c.login;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Packet sent immediately before closing a WebSocket connection.
 * <p>
 *     This packet provides a reason for the disconnection, allowing the client to understand why
 *     the server is terminating the connection.
 * </p>
 *
 * @see Reason
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CDisconnected implements Packet {
    /**
     * The reason for disconnection sent by the server.
     * Cannot be {@code null}.
     */
    @NotNull
    @org.jetbrains.annotations.NotNull
    Reason reason;

    public enum Reason {
        /**
         * The provided auth token is invalid.
         */
        INVALID_TOKEN,

        /**
         * An active session with the same token already exists.
         */
        ALREADY_CONNECTED
    }

    @Override
    public byte getId() {
        return 1;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound getPacketBound() {
        return PacketBound.SERVER;
    }
}
