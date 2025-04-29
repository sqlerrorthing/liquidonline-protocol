package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.impl;

import fun.sqlerrorthing.liquidonline.SharedConstants;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByteBufReaderImpl implements ByteBufReader {
    private final ByteBuf byteBuf;

    @Override
    public short readUnsignedByte() {
        return byteBuf.readUnsignedByte();
    }

    @Override
    public long readLong() {
        return byteBuf.readLong();
    }

    @Override
    public byte peekByte() {
        return byteBuf.getByte(byteBuf.readerIndex());
    }

    @Override
    public Boolean peekIsNullMarker() {
        return peekByte() == SharedConstants.NULL_MARKER_BYTE;
    }

    @Override
    public byte readByte() {
        return byteBuf.readByte();
    }

    @Override
    public void readBytes(byte[] dst) {
        byteBuf.readBytes(dst);
    }

    @Override
    public void skipBytes(int bytes) {
        byteBuf.readerIndex(byteBuf.readerIndex() + bytes);
    }

    @Override
    public int readInt() {
        return byteBuf.readInt();
    }

    @Override
    public double readDouble() {
        return byteBuf.readDouble();
    }

    @Override
    public boolean readBoolean() {
        return byteBuf.readBoolean();
    }

    @Override
    public short readShort() {
        return byteBuf.readShort();
    }

    @Override
    public float readFloat() {
        return byteBuf.readFloat();
    }

    @Override
    public String readString() {
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        return new String(bytes);
    }
}
