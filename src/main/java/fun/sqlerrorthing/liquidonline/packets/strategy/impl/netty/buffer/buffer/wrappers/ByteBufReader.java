package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers;

public interface ByteBufReader {
    short readUnsignedByte();

    long readLong();

    int readInt();

    double readDouble();

    boolean readBoolean();

    short readShort();

    byte readByte();

    byte peekByte();

    Boolean peekIsNullMarker();

    void skipBytes(int bytes);

    default void skipByte() {
        skipBytes(1);
    }

    void readBytes(byte[] dst);

    String readString();
}
