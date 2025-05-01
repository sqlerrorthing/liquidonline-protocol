package fun.sqlerrorthing.liquidonline.dto;

import fun.sqlerrorthing.liquidonline.packets.SerializedName;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Data
@SuperBuilder
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {
    @SerializedName("i")
    @UnsignedNumber
    int id;

    @SerializedName("u")
    @NotNull
    String username;
}
