package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.adapters.ByteArrayBase64Adapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.adapters.ColorAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.adapters.InstantTimeAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.strategy.SerializedFieldName;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * A packet serialization strategy using Gson for JSON-based serialization and deserialization.
 * <p>
 *     This implementation leverages the Gson library.
 *     The strategy includes custom serializers and deserializers for specific types, such as `Color`, `Instant`.
 * </p>
 * <p>
 *     <b>Note</b>: By default, Gson's module for JSON serialization is not included in this library.
 *     Before using this class, ensure the following dependencies are present in your classpath:
 *     <ul>
 *         <li>gson</li>
 * </ul>
 * </p>
 *
 * @see GsonBuilder
 * @see ColorAdapter
 * @see InstantTimeAdapter
 * @see PacketSerializationStrategy
 * @see Packet
 * @see Packets
 */
public class GsonJsonPacketSerializationStrategy implements PacketSerializationStrategy {
    @NotNull
    private final Gson gson;

    public GsonJsonPacketSerializationStrategy() {
        this(new GsonBuilder());
    }

    public GsonJsonPacketSerializationStrategy(GsonBuilder builder) {
        this.gson = builder
                .serializeNulls()
                .setFieldNamingStrategy(new SerializedFieldName())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .registerTypeAdapter(Instant.class, new InstantTimeAdapter())
                .registerTypeAdapter(byte[].class, new ByteArrayBase64Adapter())
                .create();
    }


    @Override
    public @NotNull String serializePacketToString(@NotNull Packet packet) {
        var json = new JsonObject();
        json.addProperty("i", packet.id());
        json.add("p", gson.toJsonTree(packet));

        return gson.toJson(json);
    }

    @Override
    public @NotNull Packet deserializePacketFromString(@NotNull String serializedPacket) throws IOException {
        var root = JsonParser.parseString(serializedPacket).getAsJsonObject();

        var packetClass = Packets.PACKETS_WITH_ID.get(root.get("i").getAsByte());
        if (packetClass == null) {
            throw new IOException("Unknown packet id");
        }

        return gson.fromJson(root.get("p"), packetClass);
    }

    @Override
    public byte @NotNull [] serializePacket(@NotNull Packet packet) {
        return serializePacketToString(packet).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public @NotNull Packet deserializePacket(byte @NotNull [] serializedPacket) throws IOException {
        return deserializePacketFromString(new String(serializedPacket));
    }
}
