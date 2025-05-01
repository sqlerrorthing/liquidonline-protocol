package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.play.MarkerDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * This packet includes the {@code memberId} of the player who set the marker and the details of the marker itself.
 * <p>This packet is broadcast to all participants in the player's party to inform them about the new marker.</p>
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CPartyNewMarker implements Packet {
    /**
     * The ID of the player who set the marker.
     */
    @SerializedName("i")
    @UnsignedNumber
    int memberId;

    /**
     * The marker information.
     */
    @SerializedName("m")
    @NotNull
    @jakarta.validation.constraints.NotNull
    MarkerDto marker;

    @Override
    public byte id() {
        return 74;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
