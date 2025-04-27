package fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.adapters;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String colorStr = json.getAsString();

        if (colorStr.length() != 6) {
            return null;
        }

        var rgb = Integer.parseInt(colorStr, 16);

        return new Color(
                (rgb >> 16) & 0xFF,
                (rgb >> 8) & 0xFF,
                rgb & 0xFF
        );
    }

    @Override
    public JsonElement serialize(Color color, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
