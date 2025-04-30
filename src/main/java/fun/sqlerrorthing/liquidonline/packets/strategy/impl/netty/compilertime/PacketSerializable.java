package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime;

import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter;

public interface PacketSerializable {
    void writeSelf(ByteBufWriter output);
    void readSelf(ByteBufReader input);
}
