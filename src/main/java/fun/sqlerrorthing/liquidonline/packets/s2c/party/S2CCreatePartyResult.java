package fun.sqlerrorthing.liquidonline.packets.s2c.party;

import fun.sqlerrorthing.liquidonline.dto.party.PartyDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The response packet sent from the server to the client in response to a party creation request.
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class S2CCreatePartyResult implements Packet {
    /**
     * The result of the party creation request.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    Result result;

    /**
     * The party information, populated if the creation was successful.
     * <p>
     *     If the result is {@link Result#CREATED}, this field will contain the details of the newly created party.
     *     If the result is {@link Result#ALREADY_IN_PARTY}, this field will be {@code null}.
     * </p>
     */
    @Nullable
    PartyDto party;

    public enum Result {
        /**
         * The party was successfully created.
         */
        CREATED,

        /**
         * The user is already part of a party and cannot create a new one.
         */
        ALREADY_IN_PARTY
    }

    @Override
    public byte id() {
        return 22;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
