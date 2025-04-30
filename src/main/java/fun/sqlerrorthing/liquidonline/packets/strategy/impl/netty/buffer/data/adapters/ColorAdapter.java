package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.adapters;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.context.BufferDeserializationContext;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.context.BufferSerializationContext;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class ColorAdapter implements BufferAdapter<Color> {
    @Override
    public @NotNull Color deserialize(@NotNull ByteBufReader reader, @NotNull Type type, @NotNull BufferDeserializationContext context) throws IOException {
        int red = reader.readUnsignedByte();
        int green = reader.readUnsignedByte();
        int blue = reader.readUnsignedByte();

        return new Color(red, green, blue);
    }

    @Override
    public void serialize(@NotNull Color src, @NotNull Type typeOfSrc, @NotNull ByteBufWriter writer, @NotNull BufferSerializationContext context) throws IOException {
        writer.writeByte((byte) src.getRed());
        writer.writeByte((byte) src.getGreen());
        writer.writeByte((byte) src.getBlue());
    }
}
