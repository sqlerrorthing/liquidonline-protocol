package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.messagepack;

import com.google.gson.*;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

class MessagePackToJson {
    public static String msgPackToJson(byte[] msgpackBytes) throws IOException {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(msgpackBytes)) {
            JsonElement jsonElement = deserializeToJsonElement(unpacker);
            return new Gson().toJson(jsonElement);
        }
    }

    private static JsonElement deserializeToJsonElement(MessageUnpacker unpacker) throws IOException {
        switch (unpacker.getNextFormat().getValueType()) {
            case STRING:
                return new JsonPrimitive(unpacker.unpackString());
            case BOOLEAN:
                return new JsonPrimitive(unpacker.unpackBoolean());
            case INTEGER:
                return new JsonPrimitive(unpacker.unpackLong());
            case FLOAT:
                return new JsonPrimitive(unpacker.unpackDouble());
            case MAP:
                JsonObject obj = new JsonObject();
                int mapSize = unpacker.unpackMapHeader();
                for (int i = 0; i < mapSize; i++) {
                    String key = unpacker.unpackString();
                    JsonElement value = deserializeToJsonElement(unpacker);
                    obj.add(key, value);
                }
                return obj;
            case ARRAY:
                JsonArray array = new JsonArray();
                int arraySize = unpacker.unpackArrayHeader();
                for (int i = 0; i < arraySize; i++) {
                    array.add(deserializeToJsonElement(unpacker));
                }
                return array;
            case NIL:
                unpacker.unpackNil();
                return JsonNull.INSTANCE;
            default:
                throw new IOException("Unknown type");
        }
    }
}
