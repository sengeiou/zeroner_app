package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Beta
public final class FileBackedOutputStream extends OutputStream {
    private File file;
    private final int fileThreshold;
    private MemoryOutput memory;
    private OutputStream out;
    private final boolean resetOnFinalize;
    private final ByteSource source;

    private static class MemoryOutput extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        /* access modifiers changed from: 0000 */
        public byte[] getBuffer() {
            return this.buf;
        }

        /* access modifiers changed from: 0000 */
        public int getCount() {
            return this.count;
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public synchronized File getFile() {
        return this.file;
    }

    public FileBackedOutputStream(int fileThreshold2) {
        this(fileThreshold2, false);
    }

    public FileBackedOutputStream(int fileThreshold2, boolean resetOnFinalize2) {
        this.fileThreshold = fileThreshold2;
        this.resetOnFinalize = resetOnFinalize2;
        this.memory = new MemoryOutput();
        this.out = this.memory;
        if (resetOnFinalize2) {
            this.source = new ByteSource() {
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }

                /* access modifiers changed from: protected */
                public void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                    } catch (Throwable t) {
                        ThrowableExtension.printStackTrace(t, System.err);
                    }
                }
            };
        } else {
            this.source = new ByteSource() {
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
        }
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    /* access modifiers changed from: private */
    public synchronized InputStream openInputStream() throws IOException {
        InputStream byteArrayInputStream;
        if (this.file != null) {
            byteArrayInputStream = new FileInputStream(this.file);
        } else {
            byteArrayInputStream = new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
        }
        return byteArrayInputStream;
    }

    public synchronized void reset() throws IOException {
        try {
            close();
            if (this.memory == null) {
                this.memory = new MemoryOutput();
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            if (this.file != null) {
                File deleteMe = this.file;
                this.file = null;
                if (!deleteMe.delete()) {
                    throw new IOException("Could not delete: " + deleteMe);
                }
            }
        } catch (Throwable th) {
            if (this.memory == null) {
                this.memory = new MemoryOutput();
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            if (this.file != null) {
                File deleteMe2 = this.file;
                this.file = null;
                if (!deleteMe2.delete()) {
                    throw new IOException("Could not delete: " + deleteMe2);
                }
            }
            throw th;
        }
    }

    public synchronized void write(int b) throws IOException {
        update(1);
        this.out.write(b);
    }

    public synchronized void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public synchronized void write(byte[] b, int off, int len) throws IOException {
        update(len);
        this.out.write(b, off, len);
    }

    public synchronized void close() throws IOException {
        this.out.close();
    }

    public synchronized void flush() throws IOException {
        this.out.flush();
    }

    private void update(int len) throws IOException {
        if (this.file == null && this.memory.getCount() + len > this.fileThreshold) {
            File temp = File.createTempFile("FileBackedOutputStream", null);
            if (this.resetOnFinalize) {
                temp.deleteOnExit();
            }
            FileOutputStream transfer = new FileOutputStream(temp);
            transfer.write(this.memory.getBuffer(), 0, this.memory.getCount());
            transfer.flush();
            this.out = transfer;
            this.file = temp;
            this.memory = null;
        }
    }
}
