package fun.sqlerrorthing.liquidonline.packets.c2s.login;

import fun.sqlerrorthing.liquidonline.SharedConstants;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;

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
public final class C2SLogin implements Packet {
    /**
     * The authentication token used to verify the client's account.
     * This token is sent from the client to the server for user authentication.
     */
    @SerializedName("t")
    @org.jetbrains.annotations.NotNull
    @NotNull
    String token;

    /**
     * Minecraft account username
     */
    @SerializedName("m")
    @org.jetbrains.annotations.NotNull
    @NotNull
    @Pattern(
            regexp = SharedConstants.MINECRAFT_USERNAME_PATTERN,
            message = "The username does not validate against this regular expression: " + SharedConstants.MINECRAFT_USERNAME_PATTERN
    ) String minecraftUsername;

    /**
     * The current server the client is playing on.
     * Use <b>singleplayer</b> if the player is in the local world.
     */
    @SerializedName("c")
    @Nullable
    @Pattern(
            regexp = SharedConstants.SERVER_IP_PATTERN,
            message = "The server ip does not validate against this regular expression: " + SharedConstants.SERVER_IP_PATTERN
    ) String server;

    /**
     * Player head skin image 16x16.
     * In png format encoded in base64.
     */
    @SerializedName("d")
    @NotNull
    @org.jetbrains.annotations.NotNull
    String skin;

    @Override
    public byte id() {
        return 0;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
