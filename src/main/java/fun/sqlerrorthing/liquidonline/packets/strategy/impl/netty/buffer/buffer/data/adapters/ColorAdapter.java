package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.adapters;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufReader;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class ColorAdapter implements BufferAdapter<Color> {
    @Override
    public @NotNull Color deserialize(@NotNull ByteBufReader reader, @NotNull Type type) throws IOException {
        int red = reader.readUnsignedByte();
        int green = reader.readUnsignedByte();
        int blue = reader.readUnsignedByte();

        return new Color(red, green, blue);
    }

    @Override
    public void serialize(@NotNull Color src, @NotNull Type typeOfSrc, @NotNull ByteBufWriter writer) throws IOException {
        writer.writeByte((byte) src.getRed());
        writer.writeByte((byte) src.getGreen());
        writer.writeByte((byte) src.getBlue());
    }
}
