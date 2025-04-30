package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers;

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

    void writeBoolean(boolean b);

    void writeSignedVarInt(int i);

    void writeUnsignedVarInt(int i);

    default void writeByte(Byte b) {
        writeByte(b.byteValue());
    }

    default void writeShort(Short s) {
        writeShort(s.shortValue());
    }

    default void writeFloat(Float f) {
        writeFloat(f.floatValue());
    }

    default void writeDouble(Double d) {
        writeDouble(d.doubleValue());
    }

    default void writeLong(Long l) {
        writeLong(l.longValue());
    }

    default void writeInt(Integer i) {
        writeInt(i.intValue());
    }

    default void writeBoolean(Boolean b) {
        writeBoolean(b.booleanValue());
    }

    default void writeUnsignedVarInt(Integer i) {
        writeUnsignedVarInt(i.intValue());
    }

    default void writeSignedVarInt(Integer i) {
        writeSignedVarInt(i.intValue());
    }
}
