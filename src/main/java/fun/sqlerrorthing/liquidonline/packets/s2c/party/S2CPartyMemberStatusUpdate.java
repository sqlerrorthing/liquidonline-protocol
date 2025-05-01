package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sent from the server to all party members when one of the members updates their personal status.
 * <p>
 *     This packet notifies all participants in the party about changes to a specific member's status.
 *     Only the fields that have changed will contain non-{@code null} values.
 *     Fields that remain unchanged will be {@code null}.
 * </p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyMemberStatusUpdate implements Packet {
    /**
     * The ID of the party member whose status was updated.
     */
    @SerializedName("i")
    @UnsignedNumber
    int memberId;

    /**
     * The updated username of the member, or {@code null} if it hasn't changed.
     */
    @SerializedName("a")
    @Nullable
    String username;

    /**
     * The updated Minecraft username of the member, or {@code null} if it hasn't changed.
     */
    @SerializedName("b")
    @Nullable
    String minecraftUsername;

    /**
     * The updated PNG of the member's head skin (16x16), or {@code null} if it hasn't changed.
     */
    @SerializedName("c")
    byte @Nullable [] skin;

    @Override
    public byte id() {
        return 44;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
