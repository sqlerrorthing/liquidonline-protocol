package fun.sqlerrorthing.liquidonline.packets.s2c.login;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
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
public final class S2CDisconnected implements Packet {
    @SerializedName("r")
    @NotNull
    @org.jetbrains.annotations.NotNull
    Reason reason;

    public enum Reason {
        /**
         * The provided auth token is invalid.
         */
        @SerializedName("a")
        INVALID_TOKEN,

        /**
         * Minecraft Username or head skin did not pass validation.
         */
        @SerializedName("b")
        INVALID_INITIAL_PLAYER_DATA,

        /**
         * An active session with the same token already exists.
         */
        @SerializedName("c")
        ALREADY_CONNECTED
    }

    @Override
    public byte id() {
        return 1;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
