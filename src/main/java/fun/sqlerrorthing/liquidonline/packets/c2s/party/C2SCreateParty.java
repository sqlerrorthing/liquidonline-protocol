package fun.sqlerrorthing.liquidonline.packets.c2s.party;

import fun.sqlerrorthing.liquidonline.SharedConstants;
import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Request the creation of a new party.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class C2SCreateParty implements Packet {
    /**
     * The name of the party to be created.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    @Pattern(
            regexp = SharedConstants.PARTY_NAME_PATTERN,
            message = "The party name does not validate against this regular expression: " + SharedConstants.PARTY_NAME_PATTERN
    ) String name;

    /**
     * Immediate information about the party owner
     */
    @Nullable
    PlayDto playData;

    @Override
    public byte id() {
        return 22;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.CLIENT;
    }
}
