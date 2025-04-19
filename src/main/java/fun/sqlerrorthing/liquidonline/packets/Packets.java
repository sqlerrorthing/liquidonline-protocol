package fun.sqlerrorthing.liquidonline.packets;

import fun.sqlerrorthing.liquidonline.packets.c2s.login.C2SLogin;
import fun.sqlerrorthing.liquidonline.packets.c2s.update.C2SUpdateMinecraftUsername;
import fun.sqlerrorthing.liquidonline.packets.c2s.update.C2SUpdatePlayingServer;
import fun.sqlerrorthing.liquidonline.packets.c2s.update.C2SUpdateSkin;
import fun.sqlerrorthing.liquidonline.packets.s2c.login.S2CConnected;
import fun.sqlerrorthing.liquidonline.packets.s2c.login.S2CDisconnected;
import fun.sqlerrorthing.liquidonline.packets.s2c.login.S2CValidationFailure;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class that holds metadata about available network packets.
 * <p>
 * This class defines and registers all available {@link Packet} implementations,
 * along with their associated IDs, obtained via their {@link Packet#id()} method.
 * </p>
 *
 * <p>
 * It provides two public static collections:
 * <ul>
 *     <li>{@link #AVAILABLE_PACKETS} — an array containing all available packet classes.</li>
 *     <li>{@link #PACKETS_WITH_ID} — an unmodifiable map associating each packet class with its unique byte ID.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each packet class must have:
 * <ul>
 *     <li>A no-argument constructor (used via reflection)</li>
 *     <li>Implementation of the {@link Packet#id()} method to provide a unique packet identifier</li>
 * </ul>
 * </p>
 *
 * <p>
 * The {@code PACKETS_WITH_ID} map is immutable and cannot be modified at runtime.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>{@code
 * byte id = Packets.PACKETS_WITH_ID.get(S2CDisconnected.class);
 * }</pre>
 */
public class Packets {
    /**
     * An array containing all available packet classes.
     */
    @SuppressWarnings("unchecked")
    public static final Class<? extends Packet>[] AVAILABLE_PACKETS = new Class[] {
            // Client-to-Server
            C2SLogin.class,
            C2SUpdateMinecraftUsername.class,
            C2SUpdatePlayingServer.class,
            C2SUpdateSkin.class,

            // Server-to-Client
            S2CDisconnected.class,
            S2CConnected.class,
            S2CValidationFailure.class
    };

    /**
     * An unmodifiable map associating each available packet class with its unique byte ID.
     * <p>
     * The IDs are obtained by instantiating each packet via its no-argument constructor
     * and calling {@link Packet#id()}.
     * </p>
     */
    public static final Map<Byte, Class<? extends Packet>> PACKETS_WITH_ID;

    static {
        Map<Byte, Class<? extends Packet>> packets = new HashMap<>();
        Map<Byte, List<Class<? extends Packet>>> idsToPackets = new HashMap<>();

        for (var packet : AVAILABLE_PACKETS) {
            try {
                Packet instance = packet.getDeclaredConstructor().newInstance();
                byte id = instance.id();

                packets.put(id, packet);
                idsToPackets.computeIfAbsent(id, k -> new ArrayList<>()).add(packet);
            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Failed to instantiate packet: " + packet.getName(), e);
            }
        }

        List<String> duplicates = new ArrayList<>();
        for (var entry : idsToPackets.entrySet()) {
            if (entry.getValue().size() > 1) {
                String duplicateInfo = "Duplicate id " + entry.getKey() + " for packets: " +
                        entry.getValue().stream()
                                .map(Class::getName)
                                .collect(Collectors.joining(", "));
                duplicates.add(duplicateInfo);
            }
        }

        if (!duplicates.isEmpty()) {
            throw new RuntimeException("Packet id conflicts found:\n" + String.join("\n", duplicates));
        }

        PACKETS_WITH_ID = Collections.unmodifiableMap(packets);
    }
}
