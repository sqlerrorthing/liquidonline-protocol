package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers;

public interface ByteBufReader {
    short readUnsignedByte();

    long readLong();

    int readInt();

    int readSignedVarInt();

    int readUnsignedVarInt();

    short readUnsignedVarShort();

    long readUnsignedVarLong();

    double readDouble();

    boolean readBoolean();

    short readShort();

    byte readByte();

    byte peekByte();

    boolean peekIsNullMarker();

    void skipBytes(int bytes);

    default void skipByte() {
        skipBytes(1);
    }

    void readBytes(byte[] dst);

    String readString();

    float readFloat();
}
