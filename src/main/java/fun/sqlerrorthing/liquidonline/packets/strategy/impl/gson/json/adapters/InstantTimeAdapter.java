package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantTimeAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(json.getAsString()));
    }

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
    }
}
