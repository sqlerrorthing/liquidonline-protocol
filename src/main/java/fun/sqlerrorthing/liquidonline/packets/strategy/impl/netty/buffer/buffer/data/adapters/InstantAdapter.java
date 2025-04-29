package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.adapters;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.context.BufferDeserializationContext;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.context.BufferSerializationContext;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufReader;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;

public class InstantAdapter implements BufferAdapter<Instant> {
    @Override
    public @NotNull Instant deserialize(@NotNull ByteBufReader reader, @NotNull Type type, @NotNull BufferDeserializationContext context) throws IOException {
        return Instant.ofEpochMilli(reader.readLong());
    }

    @Override
    public void serialize(@NotNull Instant src, @NotNull Type typeOfSrc, @NotNull ByteBufWriter writer, @NotNull BufferSerializationContext context) throws IOException {
        writer.writeLong(src.toEpochMilli());
    }
}
