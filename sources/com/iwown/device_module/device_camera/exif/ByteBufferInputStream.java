package com.iwown.device_module.device_camera.exif;

import com.google.common.primitives.UnsignedBytes;
import java.io.InputStream;
import java.nio.ByteBuffer;

class ByteBufferInputStream extends InputStream {
    private final ByteBuffer mBuf;

    public ByteBufferInputStream(ByteBuffer buf) {
        this.mBuf = buf;
    }

    public int read() {
        if (!this.mBuf.hasRemaining()) {
            return -1;
        }
        return this.mBuf.get() & UnsignedBytes.MAX_VALUE;
    }

    public int read(byte[] bytes, int off, int len) {
        if (!this.mBuf.hasRemaining()) {
            return -1;
        }
        int len2 = Math.min(len, this.mBuf.remaining());
        this.mBuf.get(bytes, off, len2);
        return len2;
    }
}
