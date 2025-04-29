package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.data.context;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;

import java.io.IOException;

public interface BufferSerializationContext {
    void serialize(ByteBufWriter writer, Object object) throws IOException;
}
