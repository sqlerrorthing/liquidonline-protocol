package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated field must contain a non-negative integer value (unsigned).
 * <p>
 *     This annotation is intended for use on integer types (e.g., {@code int}, {@code long}, {@code short}),
 *     where negative values are disallowed. Enforcing unsigned values can improve data compression,
 *     as positive numbers are often encoded more efficiently.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UnsignedNumber {
}
