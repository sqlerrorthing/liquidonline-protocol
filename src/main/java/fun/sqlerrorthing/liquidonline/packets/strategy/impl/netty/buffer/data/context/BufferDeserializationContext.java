package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.data.context;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader;

import java.io.IOException;

public interface BufferDeserializationContext {
    <T> T deserialize(ByteBufReader buffer, Class<T> clazz) throws IOException;
}
