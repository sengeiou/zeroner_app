package com.fasterxml.jackson.core.util;

import com.iwown.my_module.utility.Constants.ServiceErrorCode;

public class BufferRecycler {
    public static final int DEFAULT_WRITE_CONCAT_BUFFER_LEN = 2000;
    protected final byte[][] _byteBuffers = new byte[ByteBufferType.values().length][];
    protected final char[][] _charBuffers = new char[CharBufferType.values().length][];

    public enum ByteBufferType {
        READ_IO_BUFFER(ServiceErrorCode.UPLOAD_FILE_SO_BIG),
        WRITE_ENCODING_BUFFER(ServiceErrorCode.UPLOAD_FILE_SO_BIG),
        WRITE_CONCAT_BUFFER(2000),
        BASE64_CODEC_BUFFER(2000);
        
        protected final int size;

        private ByteBufferType(int size2) {
            this.size = size2;
        }
    }

    public enum CharBufferType {
        TOKEN_BUFFER(2000),
        CONCAT_BUFFER(2000),
        TEXT_BUFFER(200),
        NAME_COPY_BUFFER(200);
        
        protected final int size;

        private CharBufferType(int size2) {
            this.size = size2;
        }
    }

    public final byte[] allocByteBuffer(ByteBufferType type) {
        int ix = type.ordinal();
        byte[] buffer = this._byteBuffers[ix];
        if (buffer == null) {
            return balloc(type.size);
        }
        this._byteBuffers[ix] = null;
        return buffer;
    }

    public final void releaseByteBuffer(ByteBufferType type, byte[] buffer) {
        this._byteBuffers[type.ordinal()] = buffer;
    }

    public final char[] allocCharBuffer(CharBufferType type) {
        return allocCharBuffer(type, 0);
    }

    public final char[] allocCharBuffer(CharBufferType type, int minSize) {
        if (type.size > minSize) {
            minSize = type.size;
        }
        int ix = type.ordinal();
        char[] buffer = this._charBuffers[ix];
        if (buffer == null || buffer.length < minSize) {
            return calloc(minSize);
        }
        this._charBuffers[ix] = null;
        return buffer;
    }

    public final void releaseCharBuffer(CharBufferType type, char[] buffer) {
        this._charBuffers[type.ordinal()] = buffer;
    }

    private byte[] balloc(int size) {
        return new byte[size];
    }

    private char[] calloc(int size) {
        return new char[size];
    }
}
