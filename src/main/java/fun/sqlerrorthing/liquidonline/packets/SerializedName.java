package fun.sqlerrorthing.liquidonline.packets;

import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies a custom serialized name for a field when working with JSON.
 * <p>
 * To take effect, {@code SerializedName} must be explicitly registered within your
 * library's JSON serialization context. This ensures consistent behavior across all annotated fields.
 * </p>
 * <p>
 * The {@code value} must not be {@code null}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializedName {
    @NotNull
    String value();
}
