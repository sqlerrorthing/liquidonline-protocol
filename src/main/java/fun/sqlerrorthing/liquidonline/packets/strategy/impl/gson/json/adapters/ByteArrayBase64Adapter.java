package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.adapters;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Base64;

public class ByteArrayBase64Adapter extends TypeAdapter<byte[]> {
    @Override
    public void write(JsonWriter jsonWriter, byte[] bytes) throws IOException {
        if (bytes == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(Base64.getEncoder().encodeToString(bytes));
        }
    }

    @Override
    public byte[] read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String base64 = in.nextString();
            return Base64.getDecoder().decode(base64);
        }
    }
}
