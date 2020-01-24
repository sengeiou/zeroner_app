package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayStreamingContent implements StreamingContent {
    private final byte[] byteArray;
    private final int length;
    private final int offset;

    public ByteArrayStreamingContent(byte[] byteArray2) {
        this(byteArray2, 0, byteArray2.length);
    }

    public ByteArrayStreamingContent(byte[] byteArray2, int offset2, int length2) {
        this.byteArray = (byte[]) Preconditions.checkNotNull(byteArray2);
        Preconditions.checkArgument(offset2 >= 0 && length2 >= 0 && offset2 + length2 <= byteArray2.length);
        this.offset = offset2;
        this.length = length2;
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.byteArray, this.offset, this.length);
        out.flush();
    }
}
