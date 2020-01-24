package com.tencent.tinker.ziputils.ziputil;

import java.nio.ByteOrder;

public final class HeapBufferIterator extends BufferIterator {
    private final byte[] buffer;
    private final int byteCount;
    private final int offset;
    private final ByteOrder order;
    private int position;

    HeapBufferIterator(byte[] buffer2, int offset2, int byteCount2, ByteOrder order2) {
        this.buffer = buffer2;
        this.offset = offset2;
        this.byteCount = byteCount2;
        this.order = order2;
    }

    public static BufferIterator iterator(byte[] buffer2, int offset2, int byteCount2, ByteOrder order2) {
        return new HeapBufferIterator(buffer2, offset2, byteCount2, order2);
    }

    public void seek(int offset2) {
        this.position = offset2;
    }

    public void skip(int byteCount2) {
        this.position += byteCount2;
    }

    public void readByteArray(byte[] dst, int dstOffset, int byteCount2) {
        System.arraycopy(this.buffer, this.offset + this.position, dst, dstOffset, byteCount2);
        this.position += byteCount2;
    }

    public byte readByte() {
        byte result = this.buffer[this.offset + this.position];
        this.position++;
        return result;
    }

    public int readInt() {
        int result = Memory.peekInt(this.buffer, this.offset + this.position, this.order);
        this.position += 4;
        return result;
    }

    public short readShort() {
        short result = Memory.peekShort(this.buffer, this.offset + this.position, this.order);
        this.position += 2;
        return result;
    }
}
