package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers;

public interface ByteBufWriter {
    void writeByte(byte b);

    void writeLong(long l);

    void writeInt(int i);

    void writeShort(short s);

    void writeFloat(float f);

    void writeDouble(double d);

    void writeString(String s);

    void writeBytes(byte[] bytes);

    void writeNull();
}
