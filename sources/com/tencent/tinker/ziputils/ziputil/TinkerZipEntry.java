package com.tencent.tinker.ziputils.ziputil;

import com.iwown.device_module.device_camera.exif.ExifInterface.ColorSpace;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipException;

public class TinkerZipEntry implements ZipConstants, Cloneable {
    public static final int DEFLATED = 8;
    public static final int STORED = 0;
    String comment;
    long compressedSize = -1;
    int compressionMethod = -1;
    long crc = -1;
    long dataOffset = -1;
    byte[] extra;
    long localHeaderRelOffset = -1;
    int modDate = -1;
    String name;
    long size = -1;
    int time = -1;

    public TinkerZipEntry(String name2, String comment2, long crc2, long compressedSize2, long size2, int compressionMethod2, int time2, int modDate2, byte[] extra2, long localHeaderRelOffset2, long dataOffset2) {
        this.name = name2;
        this.comment = comment2;
        this.crc = crc2;
        this.compressedSize = compressedSize2;
        this.size = size2;
        this.compressionMethod = compressionMethod2;
        this.time = time2;
        this.modDate = modDate2;
        this.extra = extra2;
        this.localHeaderRelOffset = localHeaderRelOffset2;
        this.dataOffset = dataOffset2;
    }

    public TinkerZipEntry(String name2) {
        if (name2 == null) {
            throw new NullPointerException("name == null");
        }
        validateStringLength("Name", name2);
        this.name = name2;
    }

    public TinkerZipEntry(TinkerZipEntry ze) {
        this.name = ze.name;
        this.comment = ze.comment;
        this.time = ze.time;
        this.size = ze.size;
        this.compressedSize = ze.compressedSize;
        this.crc = ze.crc;
        this.compressionMethod = ze.compressionMethod;
        this.modDate = ze.modDate;
        this.extra = ze.extra;
        this.localHeaderRelOffset = ze.localHeaderRelOffset;
        this.dataOffset = ze.dataOffset;
    }

    public TinkerZipEntry(TinkerZipEntry ze, String name2) {
        this.name = name2;
        this.comment = ze.comment;
        this.time = ze.time;
        this.size = ze.size;
        this.compressedSize = ze.compressedSize;
        this.crc = ze.crc;
        this.compressionMethod = ze.compressionMethod;
        this.modDate = ze.modDate;
        this.extra = ze.extra;
        this.localHeaderRelOffset = ze.localHeaderRelOffset;
        this.dataOffset = ze.dataOffset;
    }

    TinkerZipEntry(byte[] cdeHdrBuf, InputStream cdStream, Charset defaultCharset, boolean isZip64) throws IOException {
        Streams.readFully(cdStream, cdeHdrBuf, 0, cdeHdrBuf.length);
        BufferIterator it = HeapBufferIterator.iterator(cdeHdrBuf, 0, cdeHdrBuf.length, ByteOrder.LITTLE_ENDIAN);
        int sig = it.readInt();
        if (((long) sig) != ZipConstants.CENSIG) {
            TinkerZipFile.throwZipException("unknown", (long) cdStream.available(), "unknown", 0, "Central Directory Entry", sig);
        }
        it.seek(8);
        int gpbf = it.readShort() & 65535;
        if ((gpbf & 1) != 0) {
            throw new ZipException("Invalid General Purpose Bit Flag: " + gpbf);
        }
        Charset charset = defaultCharset;
        if ((gpbf & 2048) != 0) {
            charset = Charset.forName("UTF-8");
        }
        this.compressionMethod = it.readShort() & ColorSpace.UNCALIBRATED;
        this.time = it.readShort() & ColorSpace.UNCALIBRATED;
        this.modDate = it.readShort() & ColorSpace.UNCALIBRATED;
        this.crc = ((long) it.readInt()) & 4294967295L;
        this.compressedSize = ((long) it.readInt()) & 4294967295L;
        this.size = ((long) it.readInt()) & 4294967295L;
        short readShort = it.readShort() & ColorSpace.UNCALIBRATED;
        short extraLength = it.readShort() & ColorSpace.UNCALIBRATED;
        short commentByteCount = it.readShort() & ColorSpace.UNCALIBRATED;
        it.seek(42);
        this.localHeaderRelOffset = ((long) it.readInt()) & 4294967295L;
        byte[] nameBytes = new byte[readShort];
        Streams.readFully(cdStream, nameBytes, 0, nameBytes.length);
        if (containsNulByte(nameBytes)) {
            throw new ZipException("Filename contains NUL byte: " + Arrays.toString(nameBytes));
        }
        this.name = new String(nameBytes, 0, nameBytes.length, charset);
        if (extraLength > 0) {
            this.extra = new byte[extraLength];
            Streams.readFully(cdStream, this.extra, 0, extraLength);
        }
        if (commentByteCount > 0) {
            byte[] commentBytes = new byte[commentByteCount];
            Streams.readFully(cdStream, commentBytes, 0, commentByteCount);
            this.comment = new String(commentBytes, 0, commentBytes.length, charset);
        }
    }

    private static boolean containsNulByte(byte[] bytes) {
        for (byte b : bytes) {
            if (b == 0) {
                return true;
            }
        }
        return false;
    }

    private static void validateStringLength(String argument, String string) {
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        if (bytes.length > 65535) {
            throw new IllegalArgumentException(argument + " too long: " + bytes.length);
        }
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment2) {
        if (comment2 == null) {
            this.comment = null;
            return;
        }
        validateStringLength("Comment", comment2);
        this.comment = comment2;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(long value) {
        this.compressedSize = value;
    }

    public long getCrc() {
        return this.crc;
    }

    public void setCrc(long value) {
        if (value < 0 || value > 4294967295L) {
            throw new IllegalArgumentException("Bad CRC32: " + value);
        }
        this.crc = value;
    }

    public byte[] getExtra() {
        return this.extra;
    }

    public void setExtra(byte[] data) {
        if (data == null || data.length <= 65535) {
            this.extra = data;
            return;
        }
        throw new IllegalArgumentException("Extra data too long: " + data.length);
    }

    public int getMethod() {
        return this.compressionMethod;
    }

    public void setMethod(int value) {
        if (value == 0 || value == 8) {
            this.compressionMethod = value;
            return;
        }
        throw new IllegalArgumentException("Bad method: " + value);
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Bad size: " + value);
        }
        this.size = value;
    }

    public long getTime() {
        if (this.time == -1) {
            return -1;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(14, 0);
        cal.set(((this.modDate >> 9) & Opcodes.NEG_FLOAT) + 1980, ((this.modDate >> 5) & 15) - 1, this.modDate & 31, (this.time >> 11) & 31, (this.time >> 5) & 63, (this.time & 31) << 1);
        return cal.getTime().getTime();
    }

    public void setTime(long value) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(value));
        if (cal.get(1) < 1980) {
            this.modDate = 33;
            this.time = 0;
            return;
        }
        this.modDate = cal.get(5);
        this.modDate = ((cal.get(2) + 1) << 5) | this.modDate;
        this.modDate = ((cal.get(1) - 1980) << 9) | this.modDate;
        this.time = cal.get(13) >> 1;
        this.time = (cal.get(12) << 5) | this.time;
        this.time = (cal.get(11) << 11) | this.time;
    }

    public boolean isDirectory() {
        return this.name.charAt(this.name.length() + -1) == '/';
    }

    public long getDataOffset() {
        return this.dataOffset;
    }

    public void setDataOffset(long value) {
        this.dataOffset = value;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name:" + this.name);
        sb.append("\ncomment:" + this.comment);
        sb.append("\ntime:" + this.time);
        sb.append("\nsize:" + this.size);
        sb.append("\ncompressedSize:" + this.compressedSize);
        sb.append("\ncrc:" + this.crc);
        sb.append("\ncompressionMethod:" + this.compressionMethod);
        sb.append("\nmodDate:" + this.modDate);
        sb.append("\nextra length:" + this.extra.length);
        sb.append("\nlocalHeaderRelOffset:" + this.localHeaderRelOffset);
        sb.append("\ndataOffset:" + this.dataOffset);
        return sb.toString();
    }

    public Object clone() {
        try {
            TinkerZipEntry result = (TinkerZipEntry) super.clone();
            result.extra = this.extra != null ? (byte[]) this.extra.clone() : null;
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
