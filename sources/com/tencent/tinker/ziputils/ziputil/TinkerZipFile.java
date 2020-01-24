package com.tencent.tinker.ziputils.ziputil;

import android.support.v4.media.session.PlaybackStateCompat;
import com.iwown.device_module.device_camera.exif.ExifInterface.ColorSpace;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.zip.ZipException;

public class TinkerZipFile implements Closeable, ZipConstants {
    static final int GPBF_DATA_DESCRIPTOR_FLAG = 8;
    static final int GPBF_ENCRYPTED_FLAG = 1;
    static final int GPBF_UNSUPPORTED_MASK = 1;
    static final int GPBF_UTF8_FLAG = 2048;
    public static final int OPEN_DELETE = 4;
    public static final int OPEN_READ = 1;
    private String comment;
    private final LinkedHashMap<String, TinkerZipEntry> entries;
    private File fileToDeleteOnClose;
    private final String filename;
    private RandomAccessFile raf;

    static class EocdRecord {
        final long centralDirOffset;
        final int commentLength;
        final long numEntries;

        EocdRecord(long numEntries2, long centralDirOffset2, int commentLength2) {
            this.numEntries = numEntries2;
            this.centralDirOffset = centralDirOffset2;
            this.commentLength = commentLength2;
        }
    }

    public static class RAFStream extends InputStream {
        /* access modifiers changed from: private */
        public long endOffset;
        /* access modifiers changed from: private */
        public long offset;
        private final RandomAccessFile sharedRaf;

        public RAFStream(RandomAccessFile raf, long initialOffset, long endOffset2) {
            this.sharedRaf = raf;
            this.offset = initialOffset;
            this.endOffset = endOffset2;
        }

        public RAFStream(RandomAccessFile raf, long initialOffset) throws IOException {
            this(raf, initialOffset, raf.length());
        }

        public int available() throws IOException {
            return this.offset < this.endOffset ? 1 : 0;
        }

        public int read() throws IOException {
            return Streams.readSingleByte(this);
        }

        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            int count;
            synchronized (this.sharedRaf) {
                long length = this.endOffset - this.offset;
                if (((long) byteCount) > length) {
                    byteCount = (int) length;
                }
                this.sharedRaf.seek(this.offset);
                count = this.sharedRaf.read(buffer, byteOffset, byteCount);
                if (count > 0) {
                    this.offset += (long) count;
                } else {
                    count = -1;
                }
            }
            return count;
        }

        public long skip(long byteCount) throws IOException {
            if (byteCount > this.endOffset - this.offset) {
                byteCount = this.endOffset - this.offset;
            }
            this.offset += byteCount;
            return byteCount;
        }
    }

    public TinkerZipFile(File file) throws ZipException, IOException {
        this(file, 1);
    }

    public TinkerZipFile(String name) throws IOException {
        this(new File(name), 1);
    }

    public TinkerZipFile(File file, int mode) throws IOException {
        this.entries = new LinkedHashMap<>();
        this.filename = file.getPath();
        if (mode == 1 || mode == 5) {
            if ((mode & 4) != 0) {
                this.fileToDeleteOnClose = file;
                this.fileToDeleteOnClose.deleteOnExit();
            } else {
                this.fileToDeleteOnClose = null;
            }
            this.raf = new RandomAccessFile(this.filename, "r");
            readCentralDir();
            return;
        }
        throw new IllegalArgumentException("Bad mode: " + mode);
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        if (!(a == null || b == null)) {
            int length = a.length();
            if (length == b.length()) {
                if ((a instanceof String) && (b instanceof String)) {
                    return a.equals(b);
                }
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static EocdRecord parseEocdRecord(RandomAccessFile raf2, long offset, boolean isZip64) throws IOException {
        long numEntries;
        long centralDirOffset;
        raf2.seek(offset);
        byte[] eocd = new byte[18];
        raf2.readFully(eocd);
        BufferIterator it = HeapBufferIterator.iterator(eocd, 0, eocd.length, ByteOrder.LITTLE_ENDIAN);
        if (isZip64) {
            numEntries = -1;
            centralDirOffset = -1;
            it.skip(16);
        } else {
            int diskNumber = it.readShort() & 65535;
            int diskWithCentralDir = it.readShort() & 65535;
            numEntries = (long) (it.readShort() & ColorSpace.UNCALIBRATED);
            int totalNumEntries = it.readShort() & 65535;
            it.skip(4);
            centralDirOffset = ((long) it.readInt()) & 4294967295L;
            if (!(numEntries == ((long) totalNumEntries) && diskNumber == 0 && diskWithCentralDir == 0)) {
                throw new ZipException("Spanned archives not supported");
            }
        }
        return new EocdRecord(numEntries, centralDirOffset, it.readShort() & 65535);
    }

    static void throwZipException(String filename2, long fileSize, String entryName, long localHeaderRelOffset, String msg, int magic) throws ZipException {
        throw new ZipException("file name:" + filename2 + ", file size" + fileSize + ", entry name:" + entryName + ", entry localHeaderRelOffset:" + localHeaderRelOffset + ", " + msg + " signature not found; was " + Integer.toHexString(magic));
    }

    public void close() throws IOException {
        RandomAccessFile localRaf = this.raf;
        if (localRaf != null) {
            synchronized (localRaf) {
                this.raf = null;
                localRaf.close();
            }
            if (this.fileToDeleteOnClose != null) {
                this.fileToDeleteOnClose.delete();
                this.fileToDeleteOnClose = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public void checkNotClosed() {
        if (this.raf == null) {
            throw new IllegalStateException("Zip file closed");
        }
    }

    public Enumeration<? extends TinkerZipEntry> entries() {
        checkNotClosed();
        final Iterator<TinkerZipEntry> iterator = this.entries.values().iterator();
        return new Enumeration<TinkerZipEntry>() {
            public boolean hasMoreElements() {
                TinkerZipFile.this.checkNotClosed();
                return iterator.hasNext();
            }

            public TinkerZipEntry nextElement() {
                TinkerZipFile.this.checkNotClosed();
                return (TinkerZipEntry) iterator.next();
            }
        };
    }

    public String getComment() {
        checkNotClosed();
        return this.comment;
    }

    public TinkerZipEntry getEntry(String entryName) {
        checkNotClosed();
        if (entryName == null) {
            throw new NullPointerException("entryName == null");
        }
        TinkerZipEntry ze = (TinkerZipEntry) this.entries.get(entryName);
        if (ze == null) {
            return (TinkerZipEntry) this.entries.get(entryName + "/");
        }
        return ze;
    }

    public InputStream getInputStream(TinkerZipEntry entry) throws IOException {
        RAFStream rafStream;
        TinkerZipEntry entry2 = getEntry(entry.getName());
        if (entry2 == null) {
            return null;
        }
        RandomAccessFile localRaf = this.raf;
        synchronized (localRaf) {
            rafStream = new RAFStream(localRaf, entry2.localHeaderRelOffset);
            DataInputStream is = new DataInputStream(rafStream);
            int localMagic = Integer.reverseBytes(is.readInt());
            if (((long) localMagic) != ZipConstants.LOCSIG) {
                throwZipException(this.filename, localRaf.length(), entry2.getName(), entry2.localHeaderRelOffset, "Local File Header", localMagic);
            }
            is.skipBytes(2);
            int gpbf = Short.reverseBytes(is.readShort()) & 65535;
            if ((gpbf & 1) != 0) {
                throw new ZipException("Invalid General Purpose Bit Flag: " + gpbf);
            }
            is.skipBytes(18);
            int fileNameLength = Short.reverseBytes(is.readShort()) & 65535;
            int extraFieldLength = Short.reverseBytes(is.readShort()) & 65535;
            is.close();
            rafStream.skip((long) (fileNameLength + extraFieldLength));
            if (entry2.compressionMethod == 0) {
                rafStream.endOffset = rafStream.offset + entry2.size;
            } else {
                rafStream.endOffset = rafStream.offset + entry2.compressedSize;
            }
        }
        return rafStream;
    }

    public String getName() {
        return this.filename;
    }

    public int size() {
        checkNotClosed();
        return this.entries.size();
    }

    private void readCentralDir() throws IOException {
        long scanOffset = this.raf.length() - 22;
        if (scanOffset < 0) {
            throw new ZipException("File too short to be a zip file: " + this.raf.length());
        }
        this.raf.seek(0);
        if (((long) Integer.reverseBytes(this.raf.readInt())) != ZipConstants.LOCSIG) {
            throw new ZipException("Not a zip archive");
        }
        long stopOffset = scanOffset - PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
        if (stopOffset < 0) {
            stopOffset = 0;
        }
        do {
            this.raf.seek(scanOffset);
            if (((long) Integer.reverseBytes(this.raf.readInt())) == ZipConstants.ENDSIG) {
                byte[] eocd = new byte[18];
                this.raf.readFully(eocd);
                BufferIterator it = HeapBufferIterator.iterator(eocd, 0, eocd.length, ByteOrder.LITTLE_ENDIAN);
                int diskNumber = it.readShort() & 65535;
                int diskWithCentralDir = it.readShort() & 65535;
                short readShort = it.readShort() & ColorSpace.UNCALIBRATED;
                short readShort2 = it.readShort() & ColorSpace.UNCALIBRATED;
                it.skip(4);
                long centralDirOffset = ((long) it.readInt()) & 4294967295L;
                short commentLength = it.readShort() & ColorSpace.UNCALIBRATED;
                if (readShort == readShort2 && diskNumber == 0 && diskWithCentralDir == 0) {
                    if (commentLength > 0) {
                        byte[] commentBytes = new byte[commentLength];
                        this.raf.readFully(commentBytes);
                        String str = new String(commentBytes, 0, commentBytes.length, StandardCharsets.UTF_8);
                        this.comment = str;
                    }
                    RAFStream rAFStream = new RAFStream(this.raf, centralDirOffset);
                    BufferedInputStream bufferedStream = new BufferedInputStream(rAFStream, 4096);
                    byte[] hdrBuf = new byte[46];
                    for (int i = 0; i < readShort; i++) {
                        TinkerZipEntry tinkerZipEntry = new TinkerZipEntry(hdrBuf, bufferedStream, StandardCharsets.UTF_8, false);
                        if (tinkerZipEntry.localHeaderRelOffset >= centralDirOffset) {
                            throw new ZipException("Local file header offset is after central directory");
                        }
                        String entryName = tinkerZipEntry.getName();
                        if (this.entries.put(entryName, tinkerZipEntry) != null) {
                            throw new ZipException("Duplicate entry name: " + entryName);
                        }
                    }
                    return;
                }
                throw new ZipException("Spanned archives not supported");
            }
            scanOffset--;
        } while (scanOffset >= stopOffset);
        throw new ZipException("End Of Central Directory signature not found");
    }
}
