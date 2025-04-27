package fun.sqlerrorthing.liquidonline.packets.s2c.login;

import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.PacketBound;
import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The packet is sent when the error {@link jakarta.validation.ConstraintViolationException} appears
 *
 * @see jakarta.validation.ConstraintViolationException
 */
@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public final class S2CValidationFailure implements Packet {
    /**
     * It contains the path and the message of why this path was not completed.
     */
    @NotNull
    @jakarta.validation.constraints.NotNull
    @SerializedName("d")
    List<FailureDetail> details;

    @Data
    @SuperBuilder
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    public final static class FailureDetail {
        @SerializedName("p")
        @Nullable
        String path;

        @SerializedName("m")
        @NotNull
        String message;
    }

    @Override
    public byte id() {
        return 3;
    }

    @Override
    public @NotNull PacketBound packetBound() {
        return PacketBound.SERVER;
    }
}
