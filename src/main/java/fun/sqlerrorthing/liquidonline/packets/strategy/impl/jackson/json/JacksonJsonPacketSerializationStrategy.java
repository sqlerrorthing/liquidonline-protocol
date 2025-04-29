package fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.deserializers.ColorDeserializer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.introspectors.AnnotationIntrospector;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.serializers.ColorSerializer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

/**
 * A packet serialization strategy using Jackson for JSON-based serialization and deserialization.
 * <p>
 *     This implementation leverages the Jackson library, along with its `jackson-datatype-jsr310` module,
 *     to handle JSON serialization of various objects, including Java 8 Date/Time API (JSR-310) types.
 *     The strategy includes custom serializers and deserializers for specific types, such as `Color`.
 * </p>
 * <p>
 *     <b>Note</b>: By default, Jackson's module for JSON serialization and JSR-310 support is not included in this library.
 *     Before using this class, ensure the following dependencies are present in your classpath:
 * <ul>
 *   <li>jackson-databind</li>
 *   <li>jackson-datatype-jsr310</li>
 * </ul>
 * </p>
 *
 * <p>
 *     The class uses the following Jackson settings:
 * <ul>
 *     <li>Serialization of `null` values is excluded by default (`JsonInclude.Include.NON_NULL`).</li>
 *     <li>Jackson's Java 8 Date/Time module (`JavaTimeModule`) is registered to handle Java 8 Date/Time types.</li>
 *     <li>Disables writing dates as timestamps (`SerializationFeature.WRITE_DATES_AS_TIMESTAMPS`), using ISO-8601 format instead.</li>
 *     <li>Deserialization does not fail on unknown properties (`DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES` is disabled).</li>
 *     <li>Unknown enum values are treated as `null` (`DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL` is enabled).</li>
 *     <li>Empty strings are accepted as `null` objects during deserialization (`DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT` is enabled).</li>
 * </ul>
 * </p>
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 * @see ColorDeserializer
 * @see ColorSerializer
 * @see PacketSerializationStrategy
 * @see Packet
 * @see Packets
 */
public class JacksonJsonPacketSerializationStrategy implements PacketSerializationStrategy {
    @NotNull
    private final ObjectMapper mapper;

    public JacksonJsonPacketSerializationStrategy() {
        this(new ObjectMapper());
    }

    public JacksonJsonPacketSerializationStrategy(ObjectMapper baseMapper) {
        var simpleModule = new SimpleModule()
                .addDeserializer(Color.class, new ColorDeserializer())
                .addSerializer(Color.class, new ColorSerializer());

        this.mapper = baseMapper
                .setAnnotationIntrospector(new AnnotationIntrospector())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JavaTimeModule())
                .registerModule(simpleModule)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    @Override
    public byte @NotNull [] serializePacket(@NotNull Packet packet) throws IOException {
        var json = mapper.createObjectNode();

        json.put("i", packet.id());
        json.set("p", mapper.convertValue(packet, JsonNode.class));

        return mapper.writeValueAsBytes(json);
    }

    @Override
    public @NotNull Packet deserializePacket(byte @NotNull [] serializedPacket) throws IOException {
        var root = mapper.readTree(serializedPacket);
        var packetClass = Packets.PACKETS_WITH_ID.get((byte) root.get("i").asInt());

        if (packetClass == null) {
            throw new IOException("Unknown packet id");
        }

        return mapper.treeToValue(root.get("p"), packetClass);
    }
}
