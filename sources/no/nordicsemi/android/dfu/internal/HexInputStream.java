package no.nordicsemi.android.dfu.internal;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import no.nordicsemi.android.dfu.internal.exception.HexFileValidationException;

public class HexInputStream extends FilterInputStream {
    private final int LINE_LENGTH = 128;
    private final int MBRSize;
    private int available;
    private int bytesRead;
    private int lastAddress = 0;
    private final byte[] localBuf = new byte[128];
    private int localPos = 128;
    private int pos;
    private int size = this.localBuf.length;

    public HexInputStream(InputStream in, int mbrSize) throws HexFileValidationException, IOException {
        super(new BufferedInputStream(in));
        this.MBRSize = mbrSize;
        this.available = calculateBinSize(mbrSize);
    }

    public HexInputStream(byte[] data, int mbrSize) throws HexFileValidationException, IOException {
        super(new ByteArrayInputStream(data));
        this.MBRSize = mbrSize;
        this.available = calculateBinSize(mbrSize);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0033, code lost:
        if (r0 == 13) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0021, code lost:
        skip(r2, (long) ((r5 * 2) + 2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0029, code lost:
        r0 = r2.read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002f, code lost:
        if (r0 == 10) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int calculateBinSize(int r15) throws java.io.IOException {
        /*
            r14 = this;
            r1 = 0
            java.io.InputStream r2 = r14.in
            int r12 = r2.available()
            r2.mark(r12)
            r4 = 0
            int r0 = r2.read()     // Catch:{ all -> 0x0052 }
        L_0x000f:
            r14.checkComma(r0)     // Catch:{ all -> 0x0052 }
            int r5 = r14.readByte(r2)     // Catch:{ all -> 0x0052 }
            int r8 = r14.readAddress(r2)     // Catch:{ all -> 0x0052 }
            int r9 = r14.readByte(r2)     // Catch:{ all -> 0x0052 }
            switch(r9) {
                case 0: goto L_0x0072;
                case 1: goto L_0x0036;
                case 2: goto L_0x0057;
                case 3: goto L_0x0021;
                case 4: goto L_0x003a;
                default: goto L_0x0021;
            }     // Catch:{ all -> 0x0052 }
        L_0x0021:
            int r12 = r5 * 2
            int r12 = r12 + 2
            long r10 = (long) r12     // Catch:{ all -> 0x0052 }
            r14.skip(r2, r10)     // Catch:{ all -> 0x0052 }
        L_0x0029:
            int r0 = r2.read()     // Catch:{ all -> 0x0052 }
            r12 = 10
            if (r0 == r12) goto L_0x0029
            r12 = 13
            if (r0 == r12) goto L_0x0029
            goto L_0x000f
        L_0x0036:
            r2.reset()
        L_0x0039:
            return r1
        L_0x003a:
            int r7 = r14.readAddress(r2)     // Catch:{ all -> 0x0052 }
            if (r1 <= 0) goto L_0x004a
            int r12 = r4 >> 16
            int r12 = r12 + 1
            if (r7 == r12) goto L_0x004a
            r2.reset()
            goto L_0x0039
        L_0x004a:
            int r4 = r7 << 16
            r12 = 2
            r14.skip(r2, r12)     // Catch:{ all -> 0x0052 }
            goto L_0x0029
        L_0x0052:
            r12 = move-exception
            r2.reset()
            throw r12
        L_0x0057:
            int r12 = r14.readAddress(r2)     // Catch:{ all -> 0x0052 }
            int r6 = r12 << 4
            if (r1 <= 0) goto L_0x006b
            int r12 = r6 >> 16
            int r13 = r4 >> 16
            int r13 = r13 + 1
            if (r12 == r13) goto L_0x006b
            r2.reset()
            goto L_0x0039
        L_0x006b:
            r4 = r6
            r12 = 2
            r14.skip(r2, r12)     // Catch:{ all -> 0x0052 }
            goto L_0x0029
        L_0x0072:
            int r3 = r4 + r8
            if (r3 < r15) goto L_0x0021
            int r1 = r1 + r5
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.internal.HexInputStream.calculateBinSize(int):int");
    }

    public int available() {
        return this.available - this.bytesRead;
    }

    public int readPacket(byte[] buffer) throws HexFileValidationException, IOException {
        int i = 0;
        while (i < buffer.length) {
            if (this.localPos < this.size) {
                int i2 = i + 1;
                byte[] bArr = this.localBuf;
                int i3 = this.localPos;
                this.localPos = i3 + 1;
                buffer[i] = bArr[i3];
                i = i2;
            } else {
                int i4 = this.bytesRead;
                int readLine = readLine();
                this.size = readLine;
                this.bytesRead = i4 + readLine;
                if (this.size == 0) {
                    break;
                }
            }
        }
        return i;
    }

    public int read() throws IOException {
        throw new UnsupportedOperationException("Please, use readPacket() method instead");
    }

    public int read(byte[] buffer) throws IOException {
        return readPacket(buffer);
    }

    public int read(byte[] buffer, int offset, int count) throws IOException {
        throw new UnsupportedOperationException("Please, use readPacket() method instead");
    }

    public int sizeInBytes() {
        return this.available;
    }

    public int sizeInPackets(int packetSize) throws IOException {
        int sizeInBytes = sizeInBytes();
        return (sizeInBytes % packetSize > 0 ? 1 : 0) + (sizeInBytes / packetSize);
    }

    private int readLine() throws IOException {
        if (this.pos == -1) {
            return 0;
        }
        InputStream in = this.in;
        while (true) {
            int b = in.read();
            this.pos++;
            if (!(b == 10 || b == 13)) {
                checkComma(b);
                int lineSize = readByte(in);
                this.pos += 2;
                int offset = readAddress(in);
                this.pos += 4;
                int type = readByte(in);
                this.pos += 2;
                switch (type) {
                    case 0:
                        if (this.lastAddress + offset < this.MBRSize) {
                            type = -1;
                            this.pos = (int) (((long) this.pos) + skip(in, (long) ((lineSize * 2) + 2)));
                            break;
                        }
                        break;
                    case 1:
                        this.pos = -1;
                        return 0;
                    case 2:
                        int address = readAddress(in) << 4;
                        this.pos += 4;
                        if (this.bytesRead <= 0 || (address >> 16) == (this.lastAddress >> 16) + 1) {
                            this.lastAddress = address;
                            this.pos = (int) (((long) this.pos) + skip(in, 2));
                            break;
                        } else {
                            return 0;
                        }
                    case 4:
                        int address2 = readAddress(in);
                        this.pos += 4;
                        if (this.bytesRead <= 0 || address2 == (this.lastAddress >> 16) + 1) {
                            this.lastAddress = address2 << 16;
                            this.pos = (int) (((long) this.pos) + skip(in, 2));
                            break;
                        } else {
                            return 0;
                        }
                    default:
                        this.pos = (int) (((long) this.pos) + skip(in, (long) ((lineSize * 2) + 2)));
                        break;
                }
                if (type == 0) {
                    int i = 0;
                    while (i < this.localBuf.length && i < lineSize) {
                        int b2 = readByte(in);
                        this.pos += 2;
                        this.localBuf[i] = (byte) b2;
                        i++;
                    }
                    this.pos = (int) (((long) this.pos) + skip(in, 2));
                    this.localPos = 0;
                    return lineSize;
                }
            }
        }
    }

    public synchronized void reset() throws IOException {
        super.reset();
        this.pos = 0;
        this.bytesRead = 0;
        this.localPos = 0;
    }

    private void checkComma(int comma) throws HexFileValidationException {
        if (comma != 58) {
            throw new HexFileValidationException("Not a HEX file");
        }
    }

    private long skip(InputStream in, long offset) throws IOException {
        long skipped = in.skip(offset);
        if (skipped < offset) {
            return skipped + in.skip(offset - skipped);
        }
        return skipped;
    }

    private int readByte(InputStream in) throws IOException {
        int first = asciiToInt(in.read());
        return (first << 4) | asciiToInt(in.read());
    }

    private int readAddress(InputStream in) throws IOException {
        return (readByte(in) << 8) | readByte(in);
    }

    private int asciiToInt(int ascii) {
        if (ascii >= 65) {
            return ascii - 55;
        }
        if (ascii >= 48) {
            return ascii - 48;
        }
        return -1;
    }
}
