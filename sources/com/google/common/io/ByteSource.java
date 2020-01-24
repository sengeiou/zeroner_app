package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;

public abstract class ByteSource implements InputSupplier<InputStream> {
    private static final int BUF_SIZE = 4096;
    private static final byte[] countBuffer = new byte[4096];

    private final class AsCharSource extends CharSource {
        private final Charset charset;

        private AsCharSource(Charset charset2) {
            this.charset = (Charset) Preconditions.checkNotNull(charset2);
        }

        public Reader openStream() throws IOException {
            return new InputStreamReader(ByteSource.this.openStream(), this.charset);
        }

        public String toString() {
            return ByteSource.this.toString() + ".asCharSource(" + this.charset + ")";
        }
    }

    private static class ByteArrayByteSource extends ByteSource {
        protected final byte[] bytes;

        public /* bridge */ /* synthetic */ Object getInput() throws IOException {
            return ByteSource.super.getInput();
        }

        protected ByteArrayByteSource(byte[] bytes2) {
            this.bytes = (byte[]) Preconditions.checkNotNull(bytes2);
        }

        public InputStream openStream() {
            return new ByteArrayInputStream(this.bytes);
        }

        public InputStream openBufferedStream() throws IOException {
            return openStream();
        }

        public boolean isEmpty() {
            return this.bytes.length == 0;
        }

        public long size() {
            return (long) this.bytes.length;
        }

        public byte[] read() {
            return (byte[]) this.bytes.clone();
        }

        public long copyTo(OutputStream output) throws IOException {
            output.write(this.bytes);
            return (long) this.bytes.length;
        }

        public <T> T read(ByteProcessor<T> processor) throws IOException {
            processor.processBytes(this.bytes, 0, this.bytes.length);
            return processor.getResult();
        }

        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.bytes);
        }

        public String toString() {
            return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.bytes), 30, "...") + ")";
        }
    }

    private static final class ConcatenatedByteSource extends ByteSource {
        private final Iterable<? extends ByteSource> sources;

        public /* bridge */ /* synthetic */ Object getInput() throws IOException {
            return ByteSource.super.getInput();
        }

        ConcatenatedByteSource(Iterable<? extends ByteSource> sources2) {
            this.sources = (Iterable) Preconditions.checkNotNull(sources2);
        }

        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.sources.iterator());
        }

        public boolean isEmpty() throws IOException {
            for (ByteSource source : this.sources) {
                if (!source.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public long size() throws IOException {
            long result = 0;
            for (ByteSource source : this.sources) {
                result += source.size();
            }
            return result;
        }

        public String toString() {
            return "ByteSource.concat(" + this.sources + ")";
        }
    }

    private static final class EmptyByteSource extends ByteArrayByteSource {
        /* access modifiers changed from: private */
        public static final EmptyByteSource INSTANCE = new EmptyByteSource();

        private EmptyByteSource() {
            super(new byte[0]);
        }

        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        public byte[] read() {
            return this.bytes;
        }

        public String toString() {
            return "ByteSource.empty()";
        }
    }

    private final class SlicedByteSource extends ByteSource {
        private final long length;
        private final long offset;

        public /* bridge */ /* synthetic */ Object getInput() throws IOException {
            return ByteSource.super.getInput();
        }

        private SlicedByteSource(long offset2, long length2) {
            boolean z;
            boolean z2;
            if (offset2 >= 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "offset (%s) may not be negative", Long.valueOf(offset2));
            if (length2 >= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "length (%s) may not be negative", Long.valueOf(length2));
            this.offset = offset2;
            this.length = length2;
        }

        public InputStream openStream() throws IOException {
            return sliceStream(ByteSource.this.openStream());
        }

        public InputStream openBufferedStream() throws IOException {
            return sliceStream(ByteSource.this.openBufferedStream());
        }

        private InputStream sliceStream(InputStream in) throws IOException {
            Closer closer;
            if (this.offset > 0) {
                try {
                    ByteStreams.skipFully(in, this.offset);
                } catch (Throwable th) {
                    closer.close();
                    throw th;
                }
            }
            return ByteStreams.limit(in, this.length);
        }

        public ByteSource slice(long offset2, long length2) {
            boolean z;
            boolean z2;
            if (offset2 >= 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "offset (%s) may not be negative", Long.valueOf(offset2));
            if (length2 >= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "length (%s) may not be negative", Long.valueOf(length2));
            return ByteSource.this.slice(this.offset + offset2, Math.min(length2, this.length - offset2));
        }

        public boolean isEmpty() throws IOException {
            return this.length == 0 || ByteSource.super.isEmpty();
        }

        public String toString() {
            return ByteSource.this.toString() + ".slice(" + this.offset + ", " + this.length + ")";
        }
    }

    public abstract InputStream openStream() throws IOException;

    protected ByteSource() {
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(charset);
    }

    @Deprecated
    public final InputStream getInput() throws IOException {
        return openStream();
    }

    public InputStream openBufferedStream() throws IOException {
        InputStream in = openStream();
        return in instanceof BufferedInputStream ? (BufferedInputStream) in : new BufferedInputStream(in);
    }

    public ByteSource slice(long offset, long length) {
        return new SlicedByteSource(offset, length);
    }

    public boolean isEmpty() throws IOException {
        Closer closer = Closer.create();
        try {
            boolean z = ((InputStream) closer.register(openStream())).read() == -1;
            closer.close();
            return z;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public long size() throws IOException {
        Closer closer;
        long countByReading;
        Closer closer2 = Closer.create();
        try {
            countByReading = countBySkipping((InputStream) closer2.register(openStream()));
            closer2.close();
        } catch (IOException e) {
            closer2.close();
            closer = Closer.create();
            countByReading = countByReading((InputStream) closer.register(openStream()));
            closer.close();
        } catch (Throwable e2) {
            try {
                throw closer.rethrow(e2);
            } catch (Throwable th) {
                closer.close();
                throw th;
            }
        }
        return countByReading;
    }

    private long countBySkipping(InputStream in) throws IOException {
        long count = 0;
        while (true) {
            long skipped = in.skip((long) Math.min(in.available(), Integer.MAX_VALUE));
            if (skipped > 0) {
                count += skipped;
            } else if (in.read() == -1) {
                return count;
            } else {
                if (count == 0 && in.available() == 0) {
                    throw new IOException();
                }
                count++;
            }
        }
    }

    private long countByReading(InputStream in) throws IOException {
        long count = 0;
        while (true) {
            long read = (long) in.read(countBuffer);
            if (read == -1) {
                return count;
            }
            count += read;
        }
    }

    public long copyTo(OutputStream output) throws IOException {
        Preconditions.checkNotNull(output);
        Closer closer = Closer.create();
        try {
            long copy = ByteStreams.copy((InputStream) closer.register(openStream()), output);
            closer.close();
            return copy;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public long copyTo(ByteSink sink) throws IOException {
        Preconditions.checkNotNull(sink);
        Closer closer = Closer.create();
        try {
            long copy = ByteStreams.copy((InputStream) closer.register(openStream()), (OutputStream) closer.register(sink.openStream()));
            closer.close();
            return copy;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public byte[] read() throws IOException {
        Closer closer = Closer.create();
        try {
            byte[] byteArray = ByteStreams.toByteArray((InputStream) closer.register(openStream()));
            closer.close();
            return byteArray;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    @Beta
    public <T> T read(ByteProcessor<T> processor) throws IOException {
        Preconditions.checkNotNull(processor);
        Closer closer = Closer.create();
        try {
            T readBytes = ByteStreams.readBytes((InputStream) closer.register(openStream()), processor);
            closer.close();
            return readBytes;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public HashCode hash(HashFunction hashFunction) throws IOException {
        Hasher hasher = hashFunction.newHasher();
        copyTo(Funnels.asOutputStream(hasher));
        return hasher.hash();
    }

    public boolean contentEquals(ByteSource other) throws IOException {
        int read1;
        Preconditions.checkNotNull(other);
        byte[] buf1 = new byte[4096];
        byte[] buf2 = new byte[4096];
        Closer closer = Closer.create();
        try {
            InputStream in1 = (InputStream) closer.register(openStream());
            InputStream in2 = (InputStream) closer.register(other.openStream());
            do {
                read1 = ByteStreams.read(in1, buf1, 0, 4096);
                if (read1 != ByteStreams.read(in2, buf2, 0, 4096) || !Arrays.equals(buf1, buf2)) {
                    closer.close();
                    return false;
                }
            } while (read1 == 4096);
            closer.close();
            return true;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public static ByteSource concat(Iterable<? extends ByteSource> sources) {
        return new ConcatenatedByteSource(sources);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> sources) {
        return concat((Iterable<? extends ByteSource>) ImmutableList.copyOf(sources));
    }

    public static ByteSource concat(ByteSource... sources) {
        return concat((Iterable<? extends ByteSource>) ImmutableList.copyOf((E[]) sources));
    }

    public static ByteSource wrap(byte[] b) {
        return new ByteArrayByteSource(b);
    }

    public static ByteSource empty() {
        return EmptyByteSource.INSTANCE;
    }
}
