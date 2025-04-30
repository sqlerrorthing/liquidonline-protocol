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
    public boolean peekIsNullMarker() {
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
    public int readSignedVarInt() {
        int zigZag = readUnsignedVarInt();
        return (zigZag >>> 1) ^ - (zigZag & 1);
    }

    @Override
    public int readUnsignedVarInt() {
        int numRead = 0;
        int result = 0;
        byte read;

        do {
            if (numRead >= 5) {
                throw new RuntimeException("VarInt too big (overflow)");
            }

            read = readByte();
            int value = read & 0x7F;
            result |= (value << (7 * numRead));

            numRead++;
        } while ((read & 0x80) != 0);

        return result;
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
        int length = readUnsignedVarInt();
        byte[] bytes = new byte[length];
        readBytes(bytes);
        return new String(bytes);
    }
}
