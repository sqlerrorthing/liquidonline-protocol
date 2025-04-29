package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.context.BufferDeserializationContext;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufReader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

public interface BufferDeserializer<T> {
    @NotNull
    T deserialize(@NotNull ByteBufReader reader, @NotNull Type type, @NotNull BufferDeserializationContext context) throws IOException;
}
