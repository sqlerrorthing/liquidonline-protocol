package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.context;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufReader;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface BufferDeserializationContext {
    <T> T deserialize(ByteBufReader buffer, Class<T> clazz) throws IOException;
}
