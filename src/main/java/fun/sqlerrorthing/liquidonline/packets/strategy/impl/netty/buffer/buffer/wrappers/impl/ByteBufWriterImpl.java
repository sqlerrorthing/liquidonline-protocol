package fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.impl;

import fun.sqlerrorthing.liquidonline.SharedConstants;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.wrappers.ByteBufWriter;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class ByteBufWriterImpl implements ByteBufWriter {
    private final ByteBuf byteBuf;

    @Override
    public void writeByte(byte b) {
        byteBuf.writeByte(b);
    }

    @Override
    public void writeLong(long l) {
        byteBuf.writeLong(l);
    }

    @Override
    public void writeBoolean(boolean b) {
        byteBuf.writeBoolean(b);
    }

    @Override
    public void writeNull() {
        writeByte(SharedConstants.NULL_MARKER_BYTE);
    }

    @Override
    public void writeInt(int i) {
        byteBuf.writeInt(i);
    }

    @Override
    public void writeShort(short s) {
        byteBuf.writeShort(s);
    }

    @Override
    public void writeFloat(float f) {
        byteBuf.writeFloat(f);
    }

    @Override
    public void writeDouble(double d) {
        byteBuf.writeDouble(d);
    }

    @Override
    public void writeString(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        writeInt(bytes.length);
        writeBytes(bytes);
    }

    @Override
    public void writeBytes(byte[] bytes) {
        byteBuf.writeBytes(bytes);
    }
}
