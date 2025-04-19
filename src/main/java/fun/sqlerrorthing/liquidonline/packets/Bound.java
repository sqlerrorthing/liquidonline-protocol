package fun.sqlerrorthing.liquidonline.packets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to specify the direction of a packet (whether it's sent from the client to the server
 * or from the server to the client).
 *
 * <p>This annotation is applied to packet classes to associate them with a specific direction
 * of communication, indicated by {@link PacketBound#CLIENT} or {@link PacketBound#SERVER}.</p>
 *
 * <p>This annotation is useful for maintaining consistency and clarity when handling different types
 * of packets in the communication between client and server.</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bound {
    /**
     * Specifies the direction of the packet.
     *
     * @return the direction of the packet {@link PacketBound#CLIENT} or {@link PacketBound#SERVER}
     */
    PacketBound value();
}
