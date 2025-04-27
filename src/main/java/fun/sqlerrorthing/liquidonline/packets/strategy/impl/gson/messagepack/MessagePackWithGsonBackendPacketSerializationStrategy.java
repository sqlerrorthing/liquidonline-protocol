package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.messagepack;

import com.google.gson.GsonBuilder;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.GsonJsonPacketSerializationStrategy;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * A {@link PacketSerializationStrategy} implementation that serializes and deserializes packets
 * using <b>MessagePack</b>, with <b>JSON</b> as the intermediate format for serialization and deserialization.
 * <p>
 *     This strategy uses <b>Gson</b> as the backend for handling JSON serialization and deserialization.
 *     The class essentially acts as a wrapper around JSON serialization (via Gson) and converts the
 *     serialized JSON data into MessagePack format and vice versa.
 * </p>
 *
 * <p><b>Note</b>: By default, this class does not include the necessary dependencies for
 * MessagePack or Gson. You need to ensure that the following dependencies are
 * available in your classpath:</p>
 * <ul>
 *     <li>gson</li>
 *     <li>msgpack-core</li>
 * </ul>
 */
public class MessagePackWithGsonBackendPacketSerializationStrategy implements PacketSerializationStrategy {
    @NotNull
    private final GsonJsonPacketSerializationStrategy backend;

    public MessagePackWithGsonBackendPacketSerializationStrategy() {
        this.backend = new GsonJsonPacketSerializationStrategy();
    }

    public MessagePackWithGsonBackendPacketSerializationStrategy(GsonBuilder gsonBuilder) {
        this.backend = new GsonJsonPacketSerializationStrategy(gsonBuilder);
    }

    @Override
    public byte @NotNull [] deserializePacket(@NotNull Packet packet) throws IOException {
        return JsonToMessagePack.jsonToMsgPack(backend.deserializePacket(packet));
    }

    @Override
    public @NotNull Packet serializePacket(byte @NotNull [] deserializedPacket) throws IOException {
        return backend.serializePacketFromString(MessagePackToJson.msgPackToJson(deserializedPacket));
    }
}
