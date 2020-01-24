package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public abstract class CharSource implements InputSupplier<Reader> {

    private static class CharSequenceCharSource extends CharSource {
        /* access modifiers changed from: private */
        public static final Splitter LINE_SPLITTER = Splitter.on(Pattern.compile("\r\n|\n|\r"));
        /* access modifiers changed from: private */
        public final CharSequence seq;

        public /* bridge */ /* synthetic */ Object getInput() throws IOException {
            return CharSource.super.getInput();
        }

        protected CharSequenceCharSource(CharSequence seq2) {
            this.seq = (CharSequence) Preconditions.checkNotNull(seq2);
        }

        public Reader openStream() {
            return new CharSequenceReader(this.seq);
        }

        public String read() {
            return this.seq.toString();
        }

        public boolean isEmpty() {
            return this.seq.length() == 0;
        }

        private Iterable<String> lines() {
            return new Iterable<String>() {
                public Iterator<String> iterator() {
                    return new AbstractIterator<String>() {
                        Iterator<String> lines = CharSequenceCharSource.LINE_SPLITTER.split(CharSequenceCharSource.this.seq).iterator();

                        /* access modifiers changed from: protected */
                        public String computeNext() {
                            if (this.lines.hasNext()) {
                                String next = (String) this.lines.next();
                                if (this.lines.hasNext() || next.length() != 0) {
                                    return next;
                                }
                            }
                            return (String) endOfData();
                        }
                    };
                }
            };
        }

        public String readFirstLine() {
            Iterator<String> lines = lines().iterator();
            if (lines.hasNext()) {
                return (String) lines.next();
            }
            return null;
        }

        public ImmutableList<String> readLines() {
            return ImmutableList.copyOf(lines());
        }

        public <T> T readLines(LineProcessor<T> processor) throws IOException {
            for (String line : lines()) {
                if (!processor.processLine(line)) {
                    break;
                }
            }
            return processor.getResult();
        }

        public String toString() {
            return "CharSource.wrap(" + Ascii.truncate(this.seq, 30, "...") + ")";
        }
    }

    private static final class ConcatenatedCharSource extends CharSource {
        private final Iterable<? extends CharSource> sources;

        public /* bridge */ /* synthetic */ Object getInput() throws IOException {
            return CharSource.super.getInput();
        }

        ConcatenatedCharSource(Iterable<? extends CharSource> sources2) {
            this.sources = (Iterable) Preconditions.checkNotNull(sources2);
        }

        public Reader openStream() throws IOException {
            return new MultiReader(this.sources.iterator());
        }

        public boolean isEmpty() throws IOException {
            for (CharSource source : this.sources) {
                if (!source.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return "CharSource.concat(" + this.sources + ")";
        }
    }

    private static final class EmptyCharSource extends CharSequenceCharSource {
        /* access modifiers changed from: private */
        public static final EmptyCharSource INSTANCE = new EmptyCharSource();

        private EmptyCharSource() {
            super("");
        }

        public String toString() {
            return "CharSource.empty()";
        }
    }

    public abstract Reader openStream() throws IOException;

    protected CharSource() {
    }

    @Deprecated
    public final Reader getInput() throws IOException {
        return openStream();
    }

    public BufferedReader openBufferedStream() throws IOException {
        Reader reader = openStream();
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public long copyTo(Appendable appendable) throws IOException {
        Preconditions.checkNotNull(appendable);
        Closer closer = Closer.create();
        try {
            long copy = CharStreams.copy((Readable) (Reader) closer.register(openStream()), appendable);
            closer.close();
            return copy;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public long copyTo(CharSink sink) throws IOException {
        Preconditions.checkNotNull(sink);
        Closer closer = Closer.create();
        try {
            long copy = CharStreams.copy((Readable) (Reader) closer.register(openStream()), (Appendable) (Writer) closer.register(sink.openStream()));
            closer.close();
            return copy;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public String read() throws IOException {
        Closer closer = Closer.create();
        try {
            String charStreams = CharStreams.toString((Readable) (Reader) closer.register(openStream()));
            closer.close();
            return charStreams;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    @Nullable
    public String readFirstLine() throws IOException {
        Closer closer = Closer.create();
        try {
            String readLine = ((BufferedReader) closer.register(openBufferedStream())).readLine();
            closer.close();
            return readLine;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public ImmutableList<String> readLines() throws IOException {
        Closer closer = Closer.create();
        try {
            BufferedReader reader = (BufferedReader) closer.register(openBufferedStream());
            List<String> result = Lists.newArrayList();
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    result.add(line);
                } else {
                    ImmutableList<String> copyOf = ImmutableList.copyOf((Collection<? extends E>) result);
                    closer.close();
                    return copyOf;
                }
            }
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    @Beta
    public <T> T readLines(LineProcessor<T> processor) throws IOException {
        Preconditions.checkNotNull(processor);
        Closer closer = Closer.create();
        try {
            T readLines = CharStreams.readLines((Readable) (Reader) closer.register(openStream()), processor);
            closer.close();
            return readLines;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public boolean isEmpty() throws IOException {
        Closer closer = Closer.create();
        try {
            boolean z = ((Reader) closer.register(openStream())).read() == -1;
            closer.close();
            return z;
        } catch (Throwable th) {
            closer.close();
            throw th;
        }
    }

    public static CharSource concat(Iterable<? extends CharSource> sources) {
        return new ConcatenatedCharSource(sources);
    }

    public static CharSource concat(Iterator<? extends CharSource> sources) {
        return concat((Iterable<? extends CharSource>) ImmutableList.copyOf(sources));
    }

    public static CharSource concat(CharSource... sources) {
        return concat((Iterable<? extends CharSource>) ImmutableList.copyOf((E[]) sources));
    }

    public static CharSource wrap(CharSequence charSequence) {
        return new CharSequenceCharSource(charSequence);
    }

    public static CharSource empty() {
        return EmptyCharSource.INSTANCE;
    }
}
