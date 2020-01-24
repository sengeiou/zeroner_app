package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@Beta
public abstract class BaseEncoding {
    private static final BaseEncoding BASE16 = new StandardBaseEncoding("base16()", "0123456789ABCDEF", null);
    private static final BaseEncoding BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", Character.valueOf('='));
    private static final BaseEncoding BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", Character.valueOf('='));
    private static final BaseEncoding BASE64 = new StandardBaseEncoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", Character.valueOf('='));
    private static final BaseEncoding BASE64_URL = new StandardBaseEncoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", Character.valueOf('='));

    static final class StandardBaseEncoding extends BaseEncoding {
        /* access modifiers changed from: private */
        public final Alphabet alphabet;
        private transient BaseEncoding lowerCase;
        /* access modifiers changed from: private */
        @Nullable
        public final Character paddingChar;
        private transient BaseEncoding upperCase;

        StandardBaseEncoding(String name, String alphabetChars, @Nullable Character paddingChar2) {
            this(new Alphabet(name, alphabetChars.toCharArray()), paddingChar2);
        }

        StandardBaseEncoding(Alphabet alphabet2, @Nullable Character paddingChar2) {
            boolean z;
            this.alphabet = (Alphabet) Preconditions.checkNotNull(alphabet2);
            if (paddingChar2 == null || !alphabet2.matches(paddingChar2.charValue())) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "Padding character %s was already in alphabet", paddingChar2);
            this.paddingChar = paddingChar2;
        }

        /* access modifiers changed from: 0000 */
        public CharMatcher padding() {
            return this.paddingChar == null ? CharMatcher.NONE : CharMatcher.is(this.paddingChar.charValue());
        }

        /* access modifiers changed from: 0000 */
        public int maxEncodedSize(int bytes) {
            return this.alphabet.charsPerChunk * IntMath.divide(bytes, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
        }

        /* access modifiers changed from: 0000 */
        public ByteOutput encodingStream(final CharOutput out) {
            Preconditions.checkNotNull(out);
            return new ByteOutput() {
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int writtenChars = 0;

                public void write(byte b) throws IOException {
                    this.bitBuffer <<= 8;
                    this.bitBuffer |= b & UnsignedBytes.MAX_VALUE;
                    this.bitBufferLength += 8;
                    while (this.bitBufferLength >= StandardBaseEncoding.this.alphabet.bitsPerChar) {
                        out.write(StandardBaseEncoding.this.alphabet.encode((this.bitBuffer >> (this.bitBufferLength - StandardBaseEncoding.this.alphabet.bitsPerChar)) & StandardBaseEncoding.this.alphabet.mask));
                        this.writtenChars++;
                        this.bitBufferLength -= StandardBaseEncoding.this.alphabet.bitsPerChar;
                    }
                }

                public void flush() throws IOException {
                    out.flush();
                }

                public void close() throws IOException {
                    if (this.bitBufferLength > 0) {
                        out.write(StandardBaseEncoding.this.alphabet.encode((this.bitBuffer << (StandardBaseEncoding.this.alphabet.bitsPerChar - this.bitBufferLength)) & StandardBaseEncoding.this.alphabet.mask));
                        this.writtenChars++;
                        if (StandardBaseEncoding.this.paddingChar != null) {
                            while (this.writtenChars % StandardBaseEncoding.this.alphabet.charsPerChunk != 0) {
                                out.write(StandardBaseEncoding.this.paddingChar.charValue());
                                this.writtenChars++;
                            }
                        }
                    }
                    out.close();
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public int maxDecodedSize(int chars) {
            return (int) (((((long) this.alphabet.bitsPerChar) * ((long) chars)) + 7) / 8);
        }

        /* access modifiers changed from: 0000 */
        public ByteInput decodingStream(final CharInput reader) {
            Preconditions.checkNotNull(reader);
            return new ByteInput() {
                int bitBuffer = 0;
                int bitBufferLength = 0;
                boolean hitPadding = false;
                final CharMatcher paddingMatcher = StandardBaseEncoding.this.padding();
                int readChars = 0;

                public int read() throws IOException {
                    while (true) {
                        int readChar = reader.read();
                        if (readChar != -1) {
                            this.readChars++;
                            char ch = (char) readChar;
                            if (this.paddingMatcher.matches(ch)) {
                                if (this.hitPadding || (this.readChars != 1 && StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars - 1))) {
                                    this.hitPadding = true;
                                }
                            } else if (this.hitPadding) {
                                throw new DecodingException("Expected padding character but found '" + ch + "' at index " + this.readChars);
                            } else {
                                this.bitBuffer <<= StandardBaseEncoding.this.alphabet.bitsPerChar;
                                this.bitBuffer |= StandardBaseEncoding.this.alphabet.decode(ch);
                                this.bitBufferLength += StandardBaseEncoding.this.alphabet.bitsPerChar;
                                if (this.bitBufferLength >= 8) {
                                    this.bitBufferLength -= 8;
                                    return (this.bitBuffer >> this.bitBufferLength) & 255;
                                }
                            }
                        } else if (this.hitPadding || StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars)) {
                            return -1;
                        } else {
                            throw new DecodingException("Invalid input length " + this.readChars);
                        }
                    }
                    throw new DecodingException("Padding cannot start at index " + this.readChars);
                }

                public void close() throws IOException {
                    reader.close();
                }
            };
        }

        /* Debug info: failed to restart local var, previous not found, register: 3 */
        public BaseEncoding omitPadding() {
            return this.paddingChar == null ? this : new StandardBaseEncoding(this.alphabet, null);
        }

        /* Debug info: failed to restart local var, previous not found, register: 3 */
        public BaseEncoding withPadChar(char padChar) {
            if (8 % this.alphabet.bitsPerChar != 0) {
                return (this.paddingChar == null || this.paddingChar.charValue() != padChar) ? new StandardBaseEncoding(this.alphabet, Character.valueOf(padChar)) : this;
            }
            return this;
        }

        public BaseEncoding withSeparator(String separator, int afterEveryChars) {
            Preconditions.checkNotNull(separator);
            Preconditions.checkArgument(padding().or(this.alphabet).matchesNoneOf(separator), "Separator cannot contain alphabet or padding characters");
            return new SeparatedBaseEncoding(this, separator, afterEveryChars);
        }

        public BaseEncoding upperCase() {
            BaseEncoding result = this.upperCase;
            if (result == 0) {
                Alphabet upper = this.alphabet.upperCase();
                BaseEncoding result2 = upper == this.alphabet ? this : new StandardBaseEncoding(upper, this.paddingChar);
                this.upperCase = result2;
                result = result2;
            }
            return result;
        }

        public BaseEncoding lowerCase() {
            BaseEncoding result = this.lowerCase;
            if (result == 0) {
                Alphabet lower = this.alphabet.lowerCase();
                BaseEncoding result2 = lower == this.alphabet ? this : new StandardBaseEncoding(lower, this.paddingChar);
                this.lowerCase = result2;
                result = result2;
            }
            return result;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder("BaseEncoding.");
            builder.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    builder.append(".omitPadding()");
                } else {
                    builder.append(".withPadChar(").append(this.paddingChar).append(')');
                }
            }
            return builder.toString();
        }
    }

    private static final class Alphabet extends CharMatcher {
        final int bitsPerChar;
        final int bytesPerChunk;
        private final char[] chars;
        final int charsPerChunk;
        private final byte[] decodabet;
        final int mask;
        private final String name;
        private final boolean[] validPadding;

        Alphabet(String name2, char[] chars2) {
            boolean z;
            this.name = (String) Preconditions.checkNotNull(name2);
            this.chars = (char[]) Preconditions.checkNotNull(chars2);
            try {
                this.bitsPerChar = IntMath.log2(chars2.length, RoundingMode.UNNECESSARY);
                int gcd = Math.min(8, Integer.lowestOneBit(this.bitsPerChar));
                this.charsPerChunk = 8 / gcd;
                this.bytesPerChunk = this.bitsPerChar / gcd;
                this.mask = chars2.length - 1;
                byte[] decodabet2 = new byte[128];
                Arrays.fill(decodabet2, -1);
                for (int i = 0; i < chars2.length; i++) {
                    char c = chars2[i];
                    Preconditions.checkArgument(CharMatcher.ASCII.matches(c), "Non-ASCII character: %s", Character.valueOf(c));
                    if (decodabet2[c] == -1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Preconditions.checkArgument(z, "Duplicate character: %s", Character.valueOf(c));
                    decodabet2[c] = (byte) i;
                }
                this.decodabet = decodabet2;
                boolean[] validPadding2 = new boolean[this.charsPerChunk];
                for (int i2 = 0; i2 < this.bytesPerChunk; i2++) {
                    validPadding2[IntMath.divide(i2 * 8, this.bitsPerChar, RoundingMode.CEILING)] = true;
                }
                this.validPadding = validPadding2;
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("Illegal alphabet length " + chars2.length, e);
            }
        }

        /* access modifiers changed from: 0000 */
        public char encode(int bits) {
            return this.chars[bits];
        }

        /* access modifiers changed from: 0000 */
        public boolean isValidPaddingStartPosition(int index) {
            return this.validPadding[index % this.charsPerChunk];
        }

        /* access modifiers changed from: 0000 */
        public int decode(char ch) throws IOException {
            if (ch <= 127 && this.decodabet[ch] != -1) {
                return this.decodabet[ch];
            }
            throw new DecodingException("Unrecognized character: " + ch);
        }

        private boolean hasLowerCase() {
            for (char c : this.chars) {
                if (Ascii.isLowerCase(c)) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasUpperCase() {
            for (char c : this.chars) {
                if (Ascii.isUpperCase(c)) {
                    return true;
                }
            }
            return false;
        }

        /* Debug info: failed to restart local var, previous not found, register: 5 */
        /* access modifiers changed from: 0000 */
        public Alphabet upperCase() {
            if (!hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(!hasUpperCase(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] upperCased = new char[this.chars.length];
            for (int i = 0; i < this.chars.length; i++) {
                upperCased[i] = Ascii.toUpperCase(this.chars[i]);
            }
            return new Alphabet(this.name + ".upperCase()", upperCased);
        }

        /* Debug info: failed to restart local var, previous not found, register: 5 */
        /* access modifiers changed from: 0000 */
        public Alphabet lowerCase() {
            if (!hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(!hasLowerCase(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] lowerCased = new char[this.chars.length];
            for (int i = 0; i < this.chars.length; i++) {
                lowerCased[i] = Ascii.toLowerCase(this.chars[i]);
            }
            return new Alphabet(this.name + ".lowerCase()", lowerCased);
        }

        public boolean matches(char c) {
            return CharMatcher.ASCII.matches(c) && this.decodabet[c] != -1;
        }

        public String toString() {
            return this.name;
        }
    }

    public static final class DecodingException extends IOException {
        DecodingException(String message) {
            super(message);
        }

        DecodingException(Throwable cause) {
            initCause(cause);
        }
    }

    static final class SeparatedBaseEncoding extends BaseEncoding {
        private final int afterEveryChars;
        private final BaseEncoding delegate;
        private final String separator;
        private final CharMatcher separatorChars;

        SeparatedBaseEncoding(BaseEncoding delegate2, String separator2, int afterEveryChars2) {
            boolean z;
            this.delegate = (BaseEncoding) Preconditions.checkNotNull(delegate2);
            this.separator = (String) Preconditions.checkNotNull(separator2);
            this.afterEveryChars = afterEveryChars2;
            if (afterEveryChars2 > 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "Cannot add a separator after every %s chars", Integer.valueOf(afterEveryChars2));
            this.separatorChars = CharMatcher.anyOf(separator2).precomputed();
        }

        /* access modifiers changed from: 0000 */
        public CharMatcher padding() {
            return this.delegate.padding();
        }

        /* access modifiers changed from: 0000 */
        public int maxEncodedSize(int bytes) {
            int unseparatedSize = this.delegate.maxEncodedSize(bytes);
            return (this.separator.length() * IntMath.divide(Math.max(0, unseparatedSize - 1), this.afterEveryChars, RoundingMode.FLOOR)) + unseparatedSize;
        }

        /* access modifiers changed from: 0000 */
        public ByteOutput encodingStream(CharOutput output) {
            return this.delegate.encodingStream(separatingOutput(output, this.separator, this.afterEveryChars));
        }

        /* access modifiers changed from: 0000 */
        public int maxDecodedSize(int chars) {
            return this.delegate.maxDecodedSize(chars);
        }

        /* access modifiers changed from: 0000 */
        public ByteInput decodingStream(CharInput input) {
            return this.delegate.decodingStream(ignoringInput(input, this.separatorChars));
        }

        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        public BaseEncoding withPadChar(char padChar) {
            return this.delegate.withPadChar(padChar).withSeparator(this.separator, this.afterEveryChars);
        }

        public BaseEncoding withSeparator(String separator2, int afterEveryChars2) {
            throw new UnsupportedOperationException("Already have a separator");
        }

        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            return this.delegate.toString() + ".withSeparator(\"" + this.separator + "\", " + this.afterEveryChars + ")";
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract ByteInput decodingStream(CharInput charInput);

    /* access modifiers changed from: 0000 */
    public abstract ByteOutput encodingStream(CharOutput charOutput);

    @CheckReturnValue
    public abstract BaseEncoding lowerCase();

    /* access modifiers changed from: 0000 */
    public abstract int maxDecodedSize(int i);

    /* access modifiers changed from: 0000 */
    public abstract int maxEncodedSize(int i);

    @CheckReturnValue
    public abstract BaseEncoding omitPadding();

    /* access modifiers changed from: 0000 */
    public abstract CharMatcher padding();

    @CheckReturnValue
    public abstract BaseEncoding upperCase();

    @CheckReturnValue
    public abstract BaseEncoding withPadChar(char c);

    @CheckReturnValue
    public abstract BaseEncoding withSeparator(String str, int i);

    BaseEncoding() {
    }

    public String encode(byte[] bytes) {
        return encode((byte[]) Preconditions.checkNotNull(bytes), 0, bytes.length);
    }

    public final String encode(byte[] bytes, int off, int len) {
        Preconditions.checkNotNull(bytes);
        Preconditions.checkPositionIndexes(off, off + len, bytes.length);
        CharOutput result = GwtWorkarounds.stringBuilderOutput(maxEncodedSize(len));
        ByteOutput byteOutput = encodingStream(result);
        int i = 0;
        while (i < len) {
            try {
                byteOutput.write(bytes[off + i]);
                i++;
            } catch (IOException e) {
                throw new AssertionError("impossible");
            }
        }
        byteOutput.close();
        return result.toString();
    }

    @GwtIncompatible("Writer,OutputStream")
    public final OutputStream encodingStream(Writer writer) {
        return GwtWorkarounds.asOutputStream(encodingStream(GwtWorkarounds.asCharOutput(writer)));
    }

    @GwtIncompatible("ByteSink,CharSink")
    public final ByteSink encodingSink(final CharSink encodedSink) {
        Preconditions.checkNotNull(encodedSink);
        return new ByteSink() {
            public OutputStream openStream() throws IOException {
                return BaseEncoding.this.encodingStream(encodedSink.openStream());
            }
        };
    }

    private static byte[] extract(byte[] result, int length) {
        if (length == result.length) {
            return result;
        }
        byte[] trunc = new byte[length];
        System.arraycopy(result, 0, trunc, 0, length);
        return trunc;
    }

    public final byte[] decode(CharSequence chars) {
        try {
            return decodeChecked(chars);
        } catch (DecodingException badInput) {
            throw new IllegalArgumentException(badInput);
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] decodeChecked(CharSequence chars) throws DecodingException {
        CharSequence chars2 = padding().trimTrailingFrom(chars);
        ByteInput decodedInput = decodingStream(GwtWorkarounds.asCharInput(chars2));
        byte[] tmp = new byte[maxDecodedSize(chars2.length())];
        int index = 0;
        try {
            int i = decodedInput.read();
            while (true) {
                int index2 = index;
                if (i == -1) {
                    return extract(tmp, index2);
                }
                index = index2 + 1;
                tmp[index2] = (byte) i;
                i = decodedInput.read();
            }
        } catch (DecodingException badInput) {
            throw badInput;
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @GwtIncompatible("Reader,InputStream")
    public final InputStream decodingStream(Reader reader) {
        return GwtWorkarounds.asInputStream(decodingStream(GwtWorkarounds.asCharInput(reader)));
    }

    @GwtIncompatible("ByteSource,CharSource")
    public final ByteSource decodingSource(final CharSource encodedSource) {
        Preconditions.checkNotNull(encodedSource);
        return new ByteSource() {
            public InputStream openStream() throws IOException {
                return BaseEncoding.this.decodingStream(encodedSource.openStream());
            }
        };
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    static CharInput ignoringInput(final CharInput delegate, final CharMatcher toIgnore) {
        Preconditions.checkNotNull(delegate);
        Preconditions.checkNotNull(toIgnore);
        return new CharInput() {
            public int read() throws IOException {
                int readChar;
                do {
                    readChar = delegate.read();
                    if (readChar == -1) {
                        break;
                    }
                } while (toIgnore.matches((char) readChar));
                return readChar;
            }

            public void close() throws IOException {
                delegate.close();
            }
        };
    }

    static CharOutput separatingOutput(final CharOutput delegate, final String separator, final int afterEveryChars) {
        Preconditions.checkNotNull(delegate);
        Preconditions.checkNotNull(separator);
        Preconditions.checkArgument(afterEveryChars > 0);
        return new CharOutput() {
            int charsUntilSeparator = afterEveryChars;

            public void write(char c) throws IOException {
                if (this.charsUntilSeparator == 0) {
                    for (int i = 0; i < separator.length(); i++) {
                        delegate.write(separator.charAt(i));
                    }
                    this.charsUntilSeparator = afterEveryChars;
                }
                delegate.write(c);
                this.charsUntilSeparator--;
            }

            public void flush() throws IOException {
                delegate.flush();
            }

            public void close() throws IOException {
                delegate.close();
            }
        };
    }
}
