package fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.*;
import java.io.IOException;

public class ColorSerializer extends JsonSerializer<Color> {
    @Override
    public void serialize(Color color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        var hex = String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        jsonGenerator.writeString(hex);
    }
}
