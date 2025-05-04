package fun.sqlerrorthing.liquidonline;

import java.time.Duration;

/**
 * A container for constants that are shared between both client and server.
 * <p>
 *     These constants are designed to ensure consistency in validation and protocol handling
 *     across both sides of the networked application, avoiding duplication and mismatches.
 * </p>
 *
 * <p><b>Note:</b> Any change to this class should maintain backward compatibility
 * across client and server implementations to avoid desync issues.</p>
 */
public class SharedConstants {
    /**
     * Regular expression for validating a username.
     */
    public static final String USERNAME_PATTERN = "^[a-zA-Zа-яА-ЯёЁ0-9_]{2,16}$";

    /**
     * Regular expression for validating Minecraft-style usernames.
     * <p>
     *     Functionally identical to <a href="https://regex101.com/r/jM0zH5/1">https://regex101.com/r/jM0zH5/1</a>.
     *     Included for semantic clarity when specifically validating Minecraft usernames.
     * </p>
     */
    public static final String MINECRAFT_USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,16}$";

    /**
     * Regular expression for validating server IP addresses or hostnames.
     */
    public static final String SERVER_IP_PATTERN = "^(singleplayer|((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}|(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(?:\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})(:(6553[0-5]|655[0-2]\\d|65[0-4]\\d{2}|6[0-4]\\d{3}|[1-5]?\\d{1,4}|0))?)$";

    /**
     * Regular expression for validating party names.
     */
    public static final String PARTY_NAME_PATTERN = "^(?!.* {2,})(?=.{3,24}$)[a-zA-Zа-яА-ЯёЁ0-9$'.,:;!?%&()\\[\\]{}\"\\- _]+$";

    /**
     * Maximum lifetime for temporary markers in the world.
     * <p>
     *     Markers exceeding this duration for visual pings
     *     will automatically expire and be removed.
     * </p>
     *
     * @see fun.sqlerrorthing.liquidonline.packets.s2c.party.S2CPartyNewMarker
     */
    public static final Duration MAX_MARKER_LIFETIME = Duration.ofSeconds(3);

    /**
     * Maximum duration for which an attack remains valid.
     *
     * @see fun.sqlerrorthing.liquidonline.packets.s2c.friends.S2CPartyMemberEntityAttack
     * @see fun.sqlerrorthing.liquidonline.packets.c2s.party.C2SPartyAttackEntity
     */
    public static final Duration MAX_ATTACK_LIFE = Duration.ofSeconds(3);

    /**
     * Special byte value used to represent a null or uninitialized marker.
     *
     * @see fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.NettyBuffer
     * @see fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.NettyBufferPacketSerializationStrategy
     * @see fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.CompilerTimeByteBufPacketSerializationStrategy
     */
    public static final byte NULL_MARKER_BYTE = (byte) 0xC0;
}
