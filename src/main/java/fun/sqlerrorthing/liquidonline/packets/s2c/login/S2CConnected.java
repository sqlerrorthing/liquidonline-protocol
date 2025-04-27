package fun.sqlerrorthing.liquidonline.packets.s2c.login;

import fun.sqlerrorthing.liquidonline.dto.UserAccountDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The packet is sent after successful authorization by token.
 *
 * <p>
 *     Provides a user account
 * </p>
 *
 * @see UserAccountDto
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public final class S2CConnected implements Packet {
    /**
     * The user account
     * Cannot be {@code null}.
     */
    @SerializedName("a")
    @NotNull
    @org.jetbrains.annotations.Nullable
    UserAccountDto account;

    @Override
    public byte id() {
        return 2;
    }

    @Override
    public @org.jetbrains.annotations.NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
