package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

public interface BufferSerializer<T> {
    void serialize(@NotNull T src, @NotNull ByteBufWriter writer) throws IOException;
}
