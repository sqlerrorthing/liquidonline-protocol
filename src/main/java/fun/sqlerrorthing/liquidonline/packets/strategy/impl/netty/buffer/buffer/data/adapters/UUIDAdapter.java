package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.adapters;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.BufferAdapter;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufReader;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDAdapter implements BufferAdapter<UUID> {
    @Override
    public @NotNull UUID deserialize(@NotNull ByteBufReader reader, @NotNull Type type) throws IOException {
        var mostSigBits = reader.readLong();
        var leastSigBits = reader.readLong();
        return new UUID(mostSigBits, leastSigBits);
    }

    @Override
    public void serialize(@NotNull UUID src, @NotNull Type typeOfSrc, @NotNull ByteBufWriter writer) throws IOException {
        writer.writeLong(src.getMostSignificantBits());
        writer.writeLong(src.getLeastSignificantBits());
    }
}
