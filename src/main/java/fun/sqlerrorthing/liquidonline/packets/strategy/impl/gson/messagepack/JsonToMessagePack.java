package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.messagepack;

import com.google.gson.*;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class JsonToMessagePack {
    public static byte[] jsonToMsgPack(byte[] json) throws IOException {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(new String(json, StandardCharsets.UTF_8), JsonElement.class);

        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            serializeJsonElement(packer, jsonElement);
            return packer.toByteArray();
        }
    }

    private static void serializeJsonElement(MessageBufferPacker packer, JsonElement element) throws IOException {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            packer.packMapHeader(obj.size());
            for (var entry : obj.entrySet()) {
                packer.packString(entry.getKey());
                serializeJsonElement(packer, entry.getValue());
            }
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            packer.packArrayHeader(array.size());
            for (JsonElement item : array) {
                serializeJsonElement(packer, item);
            }
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                packer.packString(primitive.getAsString());
            } else if (primitive.isBoolean()) {
                packer.packBoolean(primitive.getAsBoolean());
            } else if (primitive.isNumber()) {
                double num = primitive.getAsDouble();
                if (num == Math.floor(num)) {
                    packer.packLong((long) num);
                } else {
                    packer.packDouble(num);
                }
            }
        } else if (element.isJsonNull()) {
            packer.packNil();
        }
    }
}
