package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.litepal.util.Const.TableSchema;

public final class ReaderBasedJsonParser extends ParserBase {
    protected final int _hashSeed;
    protected char[] _inputBuffer;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete = false;

    public ReaderBasedJsonParser(IOContext ctxt, int features, Reader r, ObjectCodec codec, CharsToNameCanonicalizer st) {
        super(ctxt, features);
        this._reader = r;
        this._inputBuffer = ctxt.allocTokenBuffer();
        this._objectCodec = codec;
        this._symbols = st;
        this._hashSeed = st.hashSeed();
    }

    public Version version() {
        return CoreVersion.instance.version();
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public void setCodec(ObjectCodec c) {
        this._objectCodec = c;
    }

    public int releaseBuffered(Writer w) throws IOException {
        int count = this._inputEnd - this._inputPtr;
        if (count < 1) {
            return 0;
        }
        w.write(this._inputBuffer, this._inputPtr, count);
        return count;
    }

    public Object getInputSource() {
        return this._reader;
    }

    /* access modifiers changed from: protected */
    public boolean loadMore() throws IOException {
        this._currInputProcessed += (long) this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        if (this._reader == null) {
            return false;
        }
        int count = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (count > 0) {
            this._inputPtr = 0;
            this._inputEnd = count;
            return true;
        }
        _closeInput();
        if (count != 0) {
            return false;
        }
        throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
    }

    /* access modifiers changed from: protected */
    public char getNextChar(String eofMsg) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            _reportInvalidEOF(eofMsg);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return cArr[i];
    }

    /* access modifiers changed from: protected */
    public void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }

    /* access modifiers changed from: protected */
    public void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        char[] buf = this._inputBuffer;
        if (buf != null) {
            this._inputBuffer = null;
            this._ioContext.releaseTokenBuffer(buf);
        }
    }

    public String getText() throws IOException, JsonParseException {
        JsonToken t = this._currToken;
        if (t != JsonToken.VALUE_STRING) {
            return _getText2(t);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    public String getValueAsString() throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return super.getValueAsString(null);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    public String getValueAsString(String defValue) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return super.getValueAsString(defValue);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    /* access modifiers changed from: protected */
    public String _getText2(JsonToken t) {
        if (t == null) {
            return null;
        }
        switch (t) {
            case FIELD_NAME:
                return this._parsingContext.getCurrentName();
            case VALUE_STRING:
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                return this._textBuffer.contentsAsString();
            default:
                return t.asString();
        }
    }

    public char[] getTextCharacters() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken) {
            case FIELD_NAME:
                if (!this._nameCopied) {
                    String name = this._parsingContext.getCurrentName();
                    int nameLen = name.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(nameLen);
                    } else if (this._nameCopyBuffer.length < nameLen) {
                        this._nameCopyBuffer = new char[nameLen];
                    }
                    name.getChars(0, nameLen, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            case VALUE_STRING:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                break;
            default:
                return this._currToken.asCharArray();
        }
        return this._textBuffer.getTextBuffer();
    }

    public int getTextLength() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken) {
            case FIELD_NAME:
                return this._parsingContext.getCurrentName().length();
            case VALUE_STRING:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                break;
            default:
                return this._currToken.asCharArray().length;
        }
        return this._textBuffer.size();
    }

    public int getTextOffset() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken) {
            case VALUE_STRING:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                break;
            default:
                return 0;
        }
        return this._textBuffer.getTextOffset();
    }

    public Object getEmbeddedObject() throws IOException, JsonParseException {
        return null;
    }

    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
            _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = _decodeBase64(b64variant);
                this._tokenIncomplete = false;
            } catch (IllegalArgumentException iae) {
                throw _constructError("Failed to decode VALUE_STRING as base64 (" + b64variant + "): " + iae.getMessage());
            }
        } else if (this._binaryValue == null) {
            ByteArrayBuilder builder = _getByteArrayBuilder();
            _decodeBase64(getText(), builder, b64variant);
            this._binaryValue = builder.toByteArray();
        }
        return this._binaryValue;
    }

    public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException, JsonParseException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            byte[] b = getBinaryValue(b64variant);
            out.write(b);
            return b.length;
        }
        byte[] buf = this._ioContext.allocBase64Buffer();
        try {
            return _readBinary(b64variant, out, buf);
        } finally {
            this._ioContext.releaseBase64Buffer(buf);
        }
    }

    /* access modifiers changed from: protected */
    public int _readBinary(Base64Variant b64variant, OutputStream out, byte[] buffer) throws IOException, JsonParseException {
        int outputPtr = 0;
        int outputEnd = buffer.length - 3;
        int outputCount = 0;
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                loadMoreGuaranteed();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char ch = cArr[i];
            if (ch > ' ') {
                int bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (ch == '\"') {
                        break;
                    }
                    bits = _decodeBase64Escape(b64variant, ch, 0);
                    if (bits < 0) {
                        continue;
                    }
                }
                if (outputPtr > outputEnd) {
                    outputCount += outputPtr;
                    out.write(buffer, 0, outputPtr);
                    outputPtr = 0;
                }
                int decodedData = bits;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char ch2 = cArr2[i2];
                int bits2 = b64variant.decodeBase64Char(ch2);
                if (bits2 < 0) {
                    bits2 = _decodeBase64Escape(b64variant, ch2, 1);
                }
                int decodedData2 = (decodedData << 6) | bits2;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr3 = this._inputBuffer;
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                char ch3 = cArr3[i3];
                int bits3 = b64variant.decodeBase64Char(ch3);
                if (bits3 < 0) {
                    if (bits3 != -2) {
                        if (ch3 == '\"' && !b64variant.usesPadding()) {
                            int outputPtr2 = outputPtr + 1;
                            buffer[outputPtr] = (byte) (decodedData2 >> 4);
                            outputPtr = outputPtr2;
                            break;
                        }
                        bits3 = _decodeBase64Escape(b64variant, ch3, 2);
                    }
                    if (bits3 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            loadMoreGuaranteed();
                        }
                        char[] cArr4 = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        char ch4 = cArr4[i4];
                        if (!b64variant.usesPaddingChar(ch4)) {
                            throw reportInvalidBase64Char(b64variant, ch4, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                        }
                        int outputPtr3 = outputPtr + 1;
                        buffer[outputPtr] = (byte) (decodedData2 >> 4);
                        outputPtr = outputPtr3;
                    }
                }
                int decodedData3 = (decodedData2 << 6) | bits3;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr5 = this._inputBuffer;
                int i5 = this._inputPtr;
                this._inputPtr = i5 + 1;
                char ch5 = cArr5[i5];
                int bits4 = b64variant.decodeBase64Char(ch5);
                if (bits4 < 0) {
                    if (bits4 != -2) {
                        if (ch5 == '\"' && !b64variant.usesPadding()) {
                            int decodedData4 = decodedData3 >> 2;
                            int outputPtr4 = outputPtr + 1;
                            buffer[outputPtr] = (byte) (decodedData4 >> 8);
                            outputPtr = outputPtr4 + 1;
                            buffer[outputPtr4] = (byte) decodedData4;
                            break;
                        }
                        bits4 = _decodeBase64Escape(b64variant, ch5, 3);
                    }
                    if (bits4 == -2) {
                        int decodedData5 = decodedData3 >> 2;
                        int outputPtr5 = outputPtr + 1;
                        buffer[outputPtr] = (byte) (decodedData5 >> 8);
                        outputPtr = outputPtr5 + 1;
                        buffer[outputPtr5] = (byte) decodedData5;
                    }
                }
                int decodedData6 = (decodedData3 << 6) | bits4;
                int outputPtr6 = outputPtr + 1;
                buffer[outputPtr] = (byte) (decodedData6 >> 16);
                int outputPtr7 = outputPtr6 + 1;
                buffer[outputPtr6] = (byte) (decodedData6 >> 8);
                int outputPtr8 = outputPtr7 + 1;
                buffer[outputPtr7] = (byte) decodedData6;
                outputPtr = outputPtr8;
            }
        }
        this._tokenIncomplete = false;
        if (outputPtr <= 0) {
            return outputCount;
        }
        int outputCount2 = outputCount + outputPtr;
        out.write(buffer, 0, outputPtr);
        return outputCount2;
    }

    public JsonToken nextToken() throws IOException, JsonParseException {
        JsonToken t;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return _nextAfterName();
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int i = _skipWSOrEnd();
        if (i < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._tokenInputTotal = (this._currInputProcessed + ((long) this._inputPtr)) - 1;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = (this._inputPtr - this._currInputRowStart) - 1;
        this._binaryValue = null;
        if (i == 93) {
            if (!this._parsingContext.inArray()) {
                _reportMismatchedEndMarker(i, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            JsonToken jsonToken = JsonToken.END_ARRAY;
            this._currToken = jsonToken;
            return jsonToken;
        } else if (i == 125) {
            if (!this._parsingContext.inObject()) {
                _reportMismatchedEndMarker(i, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            JsonToken jsonToken2 = JsonToken.END_OBJECT;
            this._currToken = jsonToken2;
            return jsonToken2;
        } else {
            if (this._parsingContext.expectComma()) {
                if (i != 44) {
                    _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                }
                i = _skipWS();
            }
            boolean inObject = this._parsingContext.inObject();
            if (inObject) {
                this._parsingContext.setCurrentName(_parseFieldName(i));
                this._currToken = JsonToken.FIELD_NAME;
                int i2 = _skipWS();
                if (i2 != 58) {
                    _reportUnexpectedChar(i2, "was expecting a colon to separate field name and value");
                }
                i = _skipWS();
            }
            switch (i) {
                case 34:
                    this._tokenIncomplete = true;
                    t = JsonToken.VALUE_STRING;
                    break;
                case 45:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    t = parseNumberText(i);
                    break;
                case 91:
                    if (!inObject) {
                        this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                    }
                    t = JsonToken.START_ARRAY;
                    break;
                case 93:
                case Opcodes.NEG_LONG /*125*/:
                    _reportUnexpectedChar(i, "expected a value");
                    break;
                case 102:
                    _matchToken("false", 1);
                    t = JsonToken.VALUE_FALSE;
                    break;
                case 110:
                    _matchToken("null", 1);
                    t = JsonToken.VALUE_NULL;
                    break;
                case 116:
                    break;
                case 123:
                    if (!inObject) {
                        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
                    t = JsonToken.START_OBJECT;
                    break;
                default:
                    t = _handleUnexpectedValue(i);
                    break;
            }
            _matchToken("true", 1);
            t = JsonToken.VALUE_TRUE;
            if (inObject) {
                this._nextToken = t;
                return this._currToken;
            }
            this._currToken = t;
            return t;
        }
    }

    private JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken t = this._nextToken;
        this._nextToken = null;
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (t == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = t;
        return t;
    }

    public String nextTextValue() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken t = this._nextToken;
            this._nextToken = null;
            this._currToken = t;
            if (t == JsonToken.VALUE_STRING) {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                }
                return this._textBuffer.contentsAsString();
            } else if (t == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            } else if (t != JsonToken.START_OBJECT) {
                return null;
            } else {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
        } else if (nextToken() == JsonToken.VALUE_STRING) {
            return getText();
        } else {
            return null;
        }
    }

    public int nextIntValue(int defaultValue) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : defaultValue;
        }
        this._nameCopied = false;
        JsonToken t = this._nextToken;
        this._nextToken = null;
        this._currToken = t;
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return getIntValue();
        }
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return defaultValue;
        } else if (t != JsonToken.START_OBJECT) {
            return defaultValue;
        } else {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return defaultValue;
        }
    }

    public long nextLongValue(long defaultValue) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : defaultValue;
        }
        this._nameCopied = false;
        JsonToken t = this._nextToken;
        this._nextToken = null;
        this._currToken = t;
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return getLongValue();
        }
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return defaultValue;
        } else if (t != JsonToken.START_OBJECT) {
            return defaultValue;
        } else {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return defaultValue;
        }
    }

    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken t = this._nextToken;
            this._nextToken = null;
            this._currToken = t;
            if (t == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (t == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (t == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            } else if (t != JsonToken.START_OBJECT) {
                return null;
            } else {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
        } else {
            switch (nextToken()) {
                case VALUE_TRUE:
                    return Boolean.TRUE;
                case VALUE_FALSE:
                    return Boolean.FALSE;
                default:
                    return null;
            }
        }
    }

    public void close() throws IOException {
        super.close();
        this._symbols.release();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004a, code lost:
        if (r15 != '.') goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004c, code lost:
        if (r7 < r2) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004e, code lost:
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0054, code lost:
        r6 = r7 + 1;
        r15 = r14._inputBuffer[r7];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        if (r15 < '0') goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005c, code lost:
        if (r15 <= '9') goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005e, code lost:
        if (r1 != 0) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0060, code lost:
        reportUnexpectedNumberChar(r15, "Decimal point not followed by a digit");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0066, code lost:
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0067, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006a, code lost:
        if (r15 == 101) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006e, code lost:
        if (r15 != 69) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0070, code lost:
        if (r7 < r2) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0072, code lost:
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0074, code lost:
        r1 = r1 + 1;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0078, code lost:
        r6 = r7 + 1;
        r15 = r14._inputBuffer[r7];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007e, code lost:
        if (r15 == '-') goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0082, code lost:
        if (r15 != '+') goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0084, code lost:
        if (r6 >= r2) goto L_0x0016;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0086, code lost:
        r7 = r6 + 1;
        r15 = r14._inputBuffer[r6];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x008c, code lost:
        if (r15 > 57) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x008e, code lost:
        if (r15 < 48) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0090, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0092, code lost:
        if (r7 < r2) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0094, code lost:
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0096, code lost:
        r6 = r7 + 1;
        r15 = r14._inputBuffer[r7];
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x009e, code lost:
        if (r0 != 0) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a0, code lost:
        reportUnexpectedNumberChar(r15, "Exponent indicator not followed by a digit");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a6, code lost:
        r6 = r7 - 1;
        r14._inputPtr = r6;
        r14._textBuffer.resetWithShared(r14._inputBuffer, r8, r6 - r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ba, code lost:
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        return reset(r5, r3, r1, r0);
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r15v2, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r15v4, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r15v7, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r15v8, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r15v9, types: [int, char] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.core.JsonToken parseNumberText(int r15) throws java.io.IOException, com.fasterxml.jackson.core.JsonParseException {
        /*
            r14 = this;
            r13 = 45
            r9 = 1
            r12 = 57
            r11 = 48
            if (r15 != r13) goto L_0x0021
            r5 = r9
        L_0x000a:
            int r6 = r14._inputPtr
            int r8 = r6 + -1
            int r2 = r14._inputEnd
            if (r5 == 0) goto L_0x0036
            int r10 = r14._inputEnd
            if (r6 < r10) goto L_0x0023
        L_0x0016:
            if (r5 == 0) goto L_0x001a
            int r8 = r8 + 1
        L_0x001a:
            r14._inputPtr = r8
            com.fasterxml.jackson.core.JsonToken r9 = r14.parseNumberText2(r5)
        L_0x0020:
            return r9
        L_0x0021:
            r5 = 0
            goto L_0x000a
        L_0x0023:
            char[] r10 = r14._inputBuffer
            int r7 = r6 + 1
            char r15 = r10[r6]
            if (r15 > r12) goto L_0x002d
            if (r15 >= r11) goto L_0x0035
        L_0x002d:
            r14._inputPtr = r7
            com.fasterxml.jackson.core.JsonToken r9 = r14._handleInvalidNumberStart(r15, r9)
            r6 = r7
            goto L_0x0020
        L_0x0035:
            r6 = r7
        L_0x0036:
            if (r15 == r11) goto L_0x0016
            r3 = 1
        L_0x0039:
            int r9 = r14._inputEnd
            if (r6 >= r9) goto L_0x0016
            char[] r9 = r14._inputBuffer
            int r7 = r6 + 1
            char r15 = r9[r6]
            if (r15 < r11) goto L_0x0047
            if (r15 <= r12) goto L_0x0050
        L_0x0047:
            r1 = 0
            r9 = 46
            if (r15 != r9) goto L_0x0067
        L_0x004c:
            if (r7 < r2) goto L_0x0054
            r6 = r7
            goto L_0x0016
        L_0x0050:
            int r3 = r3 + 1
            r6 = r7
            goto L_0x0039
        L_0x0054:
            char[] r9 = r14._inputBuffer
            int r6 = r7 + 1
            char r15 = r9[r7]
            if (r15 < r11) goto L_0x005e
            if (r15 <= r12) goto L_0x0074
        L_0x005e:
            if (r1 != 0) goto L_0x0066
            java.lang.String r9 = "Decimal point not followed by a digit"
            r14.reportUnexpectedNumberChar(r15, r9)
        L_0x0066:
            r7 = r6
        L_0x0067:
            r0 = 0
            r9 = 101(0x65, float:1.42E-43)
            if (r15 == r9) goto L_0x0070
            r9 = 69
            if (r15 != r9) goto L_0x00a6
        L_0x0070:
            if (r7 < r2) goto L_0x0078
            r6 = r7
            goto L_0x0016
        L_0x0074:
            int r1 = r1 + 1
            r7 = r6
            goto L_0x004c
        L_0x0078:
            char[] r9 = r14._inputBuffer
            int r6 = r7 + 1
            char r15 = r9[r7]
            if (r15 == r13) goto L_0x0084
            r9 = 43
            if (r15 != r9) goto L_0x00ba
        L_0x0084:
            if (r6 >= r2) goto L_0x0016
            char[] r9 = r14._inputBuffer
            int r7 = r6 + 1
            char r15 = r9[r6]
        L_0x008c:
            if (r15 > r12) goto L_0x009e
            if (r15 < r11) goto L_0x009e
            int r0 = r0 + 1
            if (r7 < r2) goto L_0x0096
            r6 = r7
            goto L_0x0016
        L_0x0096:
            char[] r9 = r14._inputBuffer
            int r6 = r7 + 1
            char r15 = r9[r7]
            r7 = r6
            goto L_0x008c
        L_0x009e:
            if (r0 != 0) goto L_0x00a6
            java.lang.String r9 = "Exponent indicator not followed by a digit"
            r14.reportUnexpectedNumberChar(r15, r9)
        L_0x00a6:
            r6 = r7
            int r6 = r6 + -1
            r14._inputPtr = r6
            int r4 = r6 - r8
            com.fasterxml.jackson.core.util.TextBuffer r9 = r14._textBuffer
            char[] r10 = r14._inputBuffer
            r9.resetWithShared(r10, r8, r4)
            com.fasterxml.jackson.core.JsonToken r9 = r14.reset(r5, r3, r1, r0)
            goto L_0x0020
        L_0x00ba:
            r7 = r6
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser.parseNumberText(int):com.fasterxml.jackson.core.JsonToken");
    }

    private JsonToken parseNumberText2(boolean negative) throws IOException, JsonParseException {
        char c;
        int outPtr;
        int outPtr2;
        char c2;
        char c3;
        int outPtr3;
        int outPtr4;
        char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
        int outPtr5 = 0;
        if (negative) {
            int outPtr6 = 0 + 1;
            outBuf[0] = '-';
            outPtr5 = outPtr6;
        }
        int intLen = 0;
        if (this._inputPtr < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            c = cArr[i];
        } else {
            c = getNextChar("No digit following minus sign");
        }
        if (c == '0') {
            c = _verifyNoLeadingZeroes();
        }
        boolean eof = false;
        while (true) {
            if (c >= '0' && c <= '9') {
                intLen++;
                if (outPtr5 >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr5 = 0;
                }
                outPtr = outPtr5 + 1;
                outBuf[outPtr5] = c;
                if (this._inputPtr >= this._inputEnd && !loadMore()) {
                    c = 0;
                    eof = true;
                    break;
                }
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                c = cArr2[i2];
                outPtr5 = outPtr;
            } else {
                outPtr = outPtr5;
            }
        }
        outPtr = outPtr5;
        if (intLen == 0) {
            reportInvalidNumber("Missing integer part (next char " + _getCharDesc(c) + ")");
        }
        int fractLen = 0;
        if (c == '.') {
            outPtr2 = outPtr + 1;
            outBuf[outPtr] = c;
            while (true) {
                if (this._inputPtr >= this._inputEnd && !loadMore()) {
                    eof = true;
                    break;
                }
                char[] cArr3 = this._inputBuffer;
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                c = cArr3[i3];
                if (c < '0' || c > '9') {
                    break;
                }
                fractLen++;
                if (outPtr2 >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr2 = 0;
                }
                int outPtr7 = outPtr2 + 1;
                outBuf[outPtr2] = c;
                outPtr2 = outPtr7;
            }
            if (fractLen == 0) {
                reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
            }
        } else {
            outPtr2 = outPtr;
        }
        int expLen = 0;
        if (c == 'e' || c == 'E') {
            if (outPtr2 >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr2 = 0;
            }
            int outPtr8 = outPtr2 + 1;
            outBuf[outPtr2] = c;
            if (this._inputPtr < this._inputEnd) {
                char[] cArr4 = this._inputBuffer;
                int i4 = this._inputPtr;
                this._inputPtr = i4 + 1;
                c2 = cArr4[i4];
            } else {
                c2 = getNextChar("expected a digit for number exponent");
            }
            if (c3 == '-' || c3 == '+') {
                if (outPtr8 >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr4 = 0;
                } else {
                    outPtr4 = outPtr8;
                }
                int outPtr9 = outPtr4 + 1;
                outBuf[outPtr4] = c3;
                if (this._inputPtr < this._inputEnd) {
                    char[] cArr5 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    c3 = cArr5[i5];
                } else {
                    c3 = getNextChar("expected a digit for number exponent");
                }
                outPtr3 = outPtr9;
            } else {
                outPtr3 = outPtr8;
            }
            while (true) {
                if (c3 > '9' || c3 < '0') {
                    break;
                }
                expLen++;
                if (outPtr3 >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr3 = 0;
                }
                int outPtr10 = outPtr3 + 1;
                outBuf[outPtr3] = c3;
                if (this._inputPtr >= this._inputEnd && !loadMore()) {
                    eof = true;
                    outPtr3 = outPtr10;
                    break;
                }
                char[] cArr6 = this._inputBuffer;
                int i6 = this._inputPtr;
                this._inputPtr = i6 + 1;
                c3 = cArr6[i6];
                outPtr3 = outPtr10;
            }
            if (expLen == 0) {
                reportUnexpectedNumberChar(c3, "Exponent indicator not followed by a digit");
            }
        }
        if (!eof) {
            this._inputPtr--;
        }
        this._textBuffer.setCurrentLength(outPtr2);
        return reset(negative, intLen, fractLen, expLen);
    }

    private char _verifyNoLeadingZeroes() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            return '0';
        }
        char ch = this._inputBuffer[this._inputPtr];
        if (ch < '0' || ch > '9') {
            return '0';
        }
        if (!isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr++;
        if (ch != '0') {
            return ch;
        }
        do {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                return ch;
            }
            ch = this._inputBuffer[this._inputPtr];
            if (ch < '0' || ch > '9') {
                return '0';
            }
            this._inputPtr++;
        } while (ch == '0');
        return ch;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=char, for r10v0, types: [int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.core.JsonToken _handleInvalidNumberStart(char r10, boolean r11) throws java.io.IOException, com.fasterxml.jackson.core.JsonParseException {
        /*
            r9 = this;
            r8 = 3
            r4 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            r2 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            r1 = 73
            if (r10 != r1) goto L_0x0061
            int r1 = r9._inputPtr
            int r6 = r9._inputEnd
            if (r1 < r6) goto L_0x0018
            boolean r1 = r9.loadMore()
            if (r1 != 0) goto L_0x0018
            r9._reportInvalidEOFInValue()
        L_0x0018:
            char[] r1 = r9._inputBuffer
            int r6 = r9._inputPtr
            int r7 = r6 + 1
            r9._inputPtr = r7
            char r10 = r1[r6]
            r1 = 78
            if (r10 != r1) goto L_0x0069
            if (r11 == 0) goto L_0x003d
            java.lang.String r0 = "-INF"
        L_0x002b:
            r9._matchToken(r0, r8)
            com.fasterxml.jackson.core.JsonParser$Feature r1 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r1 = r9.isEnabled(r1)
            if (r1 == 0) goto L_0x0043
            if (r11 == 0) goto L_0x0041
        L_0x0038:
            com.fasterxml.jackson.core.JsonToken r1 = r9.resetAsNaN(r0, r2)
        L_0x003c:
            return r1
        L_0x003d:
            java.lang.String r0 = "+INF"
            goto L_0x002b
        L_0x0041:
            r2 = r4
            goto L_0x0038
        L_0x0043:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Non-standard token '"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r2 = "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r9._reportError(r1)
        L_0x0061:
            java.lang.String r1 = "expected digit (0-9) to follow minus sign, for valid numeric value"
            r9.reportUnexpectedNumberChar(r10, r1)
            r1 = 0
            goto L_0x003c
        L_0x0069:
            r1 = 110(0x6e, float:1.54E-43)
            if (r10 != r1) goto L_0x0061
            if (r11 == 0) goto L_0x0084
            java.lang.String r0 = "-Infinity"
        L_0x0072:
            r9._matchToken(r0, r8)
            com.fasterxml.jackson.core.JsonParser$Feature r1 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r1 = r9.isEnabled(r1)
            if (r1 == 0) goto L_0x008a
            if (r11 == 0) goto L_0x0088
        L_0x007f:
            com.fasterxml.jackson.core.JsonToken r1 = r9.resetAsNaN(r0, r2)
            goto L_0x003c
        L_0x0084:
            java.lang.String r0 = "+Infinity"
            goto L_0x0072
        L_0x0088:
            r2 = r4
            goto L_0x007f
        L_0x008a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Non-standard token '"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r2 = "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r9._reportError(r1)
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._handleInvalidNumberStart(int, boolean):com.fasterxml.jackson.core.JsonToken");
    }

    /* access modifiers changed from: protected */
    public String _parseFieldName(int i) throws IOException, JsonParseException {
        if (i != 34) {
            return _handleUnusualFieldName(i);
        }
        int ptr = this._inputPtr;
        int hash = this._hashSeed;
        int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            int[] codes = CharTypes.getInputCodeLatin1();
            int maxCode = codes.length;
            while (true) {
                char ch = this._inputBuffer[ptr];
                if (ch >= maxCode || codes[ch] == 0) {
                    hash = (hash * 33) + ch;
                    ptr++;
                    if (ptr >= inputLen) {
                        break;
                    }
                } else if (ch == '\"') {
                    int start = this._inputPtr;
                    this._inputPtr = ptr + 1;
                    return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                }
            }
        }
        int start2 = this._inputPtr;
        this._inputPtr = ptr;
        return _parseFieldName2(start2, hash, 34);
    }

    private String _parseFieldName2(int startPtr, int hash, int endChar) throws IOException, JsonParseException {
        this._textBuffer.resetWithShared(this._inputBuffer, startPtr, this._inputPtr - startPtr);
        char[] outBuf = this._textBuffer.getCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOF(": was expecting closing '" + ((char) endChar) + "' for name");
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            char c2 = c;
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c = _decodeEscaped();
                } else if (c2 <= endChar) {
                    if (c2 == endChar) {
                        this._textBuffer.setCurrentLength(outPtr);
                        TextBuffer tb = this._textBuffer;
                        return this._symbols.findSymbol(tb.getTextBuffer(), tb.getTextOffset(), tb.size(), hash);
                    } else if (c2 < ' ') {
                        _throwUnquotedSpace(c2, TableSchema.COLUMN_NAME);
                    }
                }
            }
            hash = (hash * 33) + c2;
            int outPtr2 = outPtr + 1;
            outBuf[outPtr] = c;
            if (outPtr2 >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            } else {
                outPtr = outPtr2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public String _handleUnusualFieldName(int i) throws IOException, JsonParseException {
        if (i == 39 && isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return _parseApostropheFieldName();
        }
        if (!isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar(i, "was expecting double-quote to start field name");
        }
        int[] codes = CharTypes.getInputCodeLatin1JsNames();
        int maxCode = codes.length;
        boolean firstOk = i < maxCode ? codes[i] == 0 && (i < 48 || i > 57) : Character.isJavaIdentifierPart((char) i);
        if (!firstOk) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int ptr = this._inputPtr;
        int hash = this._hashSeed;
        int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            do {
                char ch = this._inputBuffer[ptr];
                if (ch < maxCode) {
                    if (codes[ch] != 0) {
                        int start = this._inputPtr - 1;
                        this._inputPtr = ptr;
                        return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                    }
                } else if (!Character.isJavaIdentifierPart((char) ch)) {
                    int start2 = this._inputPtr - 1;
                    this._inputPtr = ptr;
                    return this._symbols.findSymbol(this._inputBuffer, start2, ptr - start2, hash);
                }
                hash = (hash * 33) + ch;
                ptr++;
            } while (ptr < inputLen);
        }
        int start3 = this._inputPtr - 1;
        this._inputPtr = ptr;
        return _parseUnusualFieldName2(start3, hash, codes);
    }

    /* access modifiers changed from: protected */
    public String _parseApostropheFieldName() throws IOException, JsonParseException {
        int ptr = this._inputPtr;
        int hash = this._hashSeed;
        int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            int[] codes = CharTypes.getInputCodeLatin1();
            int maxCode = codes.length;
            do {
                char ch = this._inputBuffer[ptr];
                if (ch != '\'') {
                    if (ch < maxCode && codes[ch] != 0) {
                        break;
                    }
                    hash = (hash * 33) + ch;
                    ptr++;
                } else {
                    int start = this._inputPtr;
                    this._inputPtr = ptr + 1;
                    return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                }
            } while (ptr < inputLen);
        }
        int start2 = this._inputPtr;
        this._inputPtr = ptr;
        return _parseFieldName2(start2, hash, 39);
    }

    /* access modifiers changed from: protected */
    public JsonToken _handleUnexpectedValue(int i) throws IOException, JsonParseException {
        switch (i) {
            case 39:
                if (isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return _handleApostropheValue();
                }
                break;
            case 43:
                if (this._inputPtr >= this._inputEnd && !loadMore()) {
                    _reportInvalidEOFInValue();
                }
                char[] cArr = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                return _handleInvalidNumberStart(cArr[i2], false);
            case 78:
                _matchToken("NaN", 1);
                if (!isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    break;
                } else {
                    return resetAsNaN("NaN", Double.NaN);
                }
        }
        _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }

    /* access modifiers changed from: protected */
    public JsonToken _handleApostropheValue() throws IOException, JsonParseException {
        char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            char c2 = c;
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c = _decodeEscaped();
                } else if (c2 <= '\'') {
                    if (c2 == '\'') {
                        this._textBuffer.setCurrentLength(outPtr);
                        return JsonToken.VALUE_STRING;
                    } else if (c2 < ' ') {
                        _throwUnquotedSpace(c2, "string value");
                    }
                }
            }
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
            int outPtr2 = outPtr + 1;
            outBuf[outPtr] = c;
            outPtr = outPtr2;
        }
    }

    private String _parseUnusualFieldName2(int startPtr, int hash, int[] codes) throws IOException, JsonParseException {
        this._textBuffer.resetWithShared(this._inputBuffer, startPtr, this._inputPtr - startPtr);
        char[] outBuf = this._textBuffer.getCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        int maxCode = codes.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            char c2 = c;
            if (c2 > maxCode) {
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
            } else if (codes[c2] != 0) {
                break;
            }
            this._inputPtr++;
            hash = (hash * 33) + c2;
            int outPtr2 = outPtr + 1;
            outBuf[outPtr] = c;
            if (outPtr2 >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            } else {
                outPtr = outPtr2;
            }
        }
        this._textBuffer.setCurrentLength(outPtr);
        TextBuffer tb = this._textBuffer;
        return this._symbols.findSymbol(tb.getTextBuffer(), tb.getTextOffset(), tb.size(), hash);
    }

    /* access modifiers changed from: protected */
    public void _finishString() throws IOException, JsonParseException {
        int ptr = this._inputPtr;
        int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            int[] codes = CharTypes.getInputCodeLatin1();
            int maxCode = codes.length;
            while (true) {
                char ch = this._inputBuffer[ptr];
                if (ch >= maxCode || codes[ch] == 0) {
                    ptr++;
                    if (ptr >= inputLen) {
                        break;
                    }
                } else if (ch == '\"') {
                    this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, ptr - this._inputPtr);
                    this._inputPtr = ptr + 1;
                    return;
                }
            }
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, ptr - this._inputPtr);
        this._inputPtr = ptr;
        _finishString2();
    }

    /* access modifiers changed from: protected */
    public void _finishString2() throws IOException, JsonParseException {
        char[] outBuf = this._textBuffer.getCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            char c2 = c;
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c = _decodeEscaped();
                } else if (c2 <= '\"') {
                    if (c2 == '\"') {
                        this._textBuffer.setCurrentLength(outPtr);
                        return;
                    } else if (c2 < ' ') {
                        _throwUnquotedSpace(c2, "string value");
                    }
                }
            }
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
            int outPtr2 = outPtr + 1;
            outBuf[outPtr] = c;
            outPtr = outPtr2;
        }
    }

    /* access modifiers changed from: protected */
    public void _skipString() throws IOException, JsonParseException {
        this._tokenIncomplete = false;
        int inputPtr = this._inputPtr;
        int inputLen = this._inputEnd;
        char[] inputBuffer = this._inputBuffer;
        while (true) {
            if (inputPtr >= inputLen) {
                this._inputPtr = inputPtr;
                if (!loadMore()) {
                    _reportInvalidEOF(": was expecting closing quote for a string value");
                }
                inputPtr = this._inputPtr;
                inputLen = this._inputEnd;
            }
            int inputPtr2 = inputPtr + 1;
            int i = inputBuffer[inputPtr];
            if (i <= 92) {
                if (i == 92) {
                    this._inputPtr = inputPtr2;
                    char c = _decodeEscaped();
                    inputPtr = this._inputPtr;
                    inputLen = this._inputEnd;
                } else if (i <= 34) {
                    if (i == 34) {
                        this._inputPtr = inputPtr2;
                        return;
                    } else if (i < 32) {
                        this._inputPtr = inputPtr2;
                        _throwUnquotedSpace(i, "string value");
                    }
                }
            }
            inputPtr = inputPtr2;
        }
    }

    /* access modifiers changed from: protected */
    public void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            this._inputPtr++;
        }
        this._currInputRow++;
        this._currInputRowStart = this._inputPtr;
    }

    /* access modifiers changed from: protected */
    public void _skipLF() throws IOException {
        this._currInputRow++;
        this._currInputRowStart = this._inputPtr;
    }

    private int _skipWS() throws IOException, JsonParseException {
        while (true) {
            if (this._inputPtr < this._inputEnd || loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char i2 = cArr[i];
                if (i2 > ' ') {
                    if (i2 != '/') {
                        return i2;
                    }
                    _skipComment();
                } else if (i2 != ' ') {
                    if (i2 == 10) {
                        _skipLF();
                    } else if (i2 == 13) {
                        _skipCR();
                    } else if (i2 != 9) {
                        _throwInvalidSpace(i2);
                    }
                }
            } else {
                throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
            }
        }
    }

    private int _skipWSOrEnd() throws IOException, JsonParseException {
        while (true) {
            if (this._inputPtr < this._inputEnd || loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char i2 = cArr[i];
                if (i2 > ' ') {
                    if (i2 != '/') {
                        return i2;
                    }
                    _skipComment();
                } else if (i2 != ' ') {
                    if (i2 == 10) {
                        _skipLF();
                    } else if (i2 == 13) {
                        _skipCR();
                    } else if (i2 != 9) {
                        _throwInvalidSpace(i2);
                    }
                }
            } else {
                _handleEOF();
                return -1;
            }
        }
    }

    private void _skipComment() throws IOException, JsonParseException {
        if (!isEnabled(Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            _reportInvalidEOF(" in a comment");
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c == '/') {
            _skipCppComment();
        } else if (c == '*') {
            _skipCComment();
        } else {
            _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
        }
    }

    private void _skipCComment() throws IOException, JsonParseException {
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                break;
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char i2 = cArr[i];
            if (i2 <= '*') {
                if (i2 == '*') {
                    if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        break;
                    } else if (this._inputBuffer[this._inputPtr] == '/') {
                        this._inputPtr++;
                        return;
                    }
                } else if (i2 < ' ') {
                    if (i2 == 10) {
                        _skipLF();
                    } else if (i2 == 13) {
                        _skipCR();
                    } else if (i2 != 9) {
                        _throwInvalidSpace(i2);
                    }
                }
            }
        }
        _reportInvalidEOF(" in a comment");
    }

    private void _skipCppComment() throws IOException, JsonParseException {
        while (true) {
            if (this._inputPtr < this._inputEnd || loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char i2 = cArr[i];
                if (i2 < ' ') {
                    if (i2 == 10) {
                        _skipLF();
                        return;
                    } else if (i2 == 13) {
                        _skipCR();
                        return;
                    } else if (i2 != 9) {
                        _throwInvalidSpace(i2);
                    }
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public char _decodeEscaped() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !loadMore()) {
            _reportInvalidEOF(" in character escape sequence");
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        switch (c) {
            case '\"':
            case '/':
            case '\\':
                return c;
            case 'b':
                return 8;
            case 'f':
                return 12;
            case 'n':
                return 10;
            case 'r':
                return 13;
            case 't':
                return 9;
            case 'u':
                int value = 0;
                for (int i2 = 0; i2 < 4; i2++) {
                    if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        _reportInvalidEOF(" in character escape sequence");
                    }
                    char[] cArr2 = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    char ch = cArr2[i3];
                    int digit = CharTypes.charToHex(ch);
                    if (digit < 0) {
                        _reportUnexpectedChar(ch, "expected a hex-digit for character escape sequence");
                    }
                    value = (value << 4) | digit;
                }
                return (char) value;
            default:
                return _handleUnrecognizedCharacterEscape(c);
        }
    }

    /* access modifiers changed from: protected */
    public void _matchToken(String matchStr, int i) throws IOException, JsonParseException {
        int len = matchStr.length();
        do {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                _reportInvalidEOFInValue();
            }
            if (this._inputBuffer[this._inputPtr] != matchStr.charAt(i)) {
                _reportInvalidToken(matchStr.substring(0, i), "'null', 'true', 'false' or NaN");
            }
            this._inputPtr++;
            i++;
        } while (i < len);
        if (this._inputPtr < this._inputEnd || loadMore()) {
            char c = this._inputBuffer[this._inputPtr];
            if (c >= '0' && c != ']' && c != '}' && Character.isJavaIdentifierPart(c)) {
                _reportInvalidToken(matchStr.substring(0, i), "'null', 'true', 'false' or NaN");
            }
        }
    }

    /* access modifiers changed from: protected */
    public byte[] _decodeBase64(Base64Variant b64variant) throws IOException, JsonParseException {
        ByteArrayBuilder builder = _getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                loadMoreGuaranteed();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char ch = cArr[i];
            if (ch > ' ') {
                int bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (ch == '\"') {
                        return builder.toByteArray();
                    }
                    bits = _decodeBase64Escape(b64variant, ch, 0);
                    if (bits < 0) {
                        continue;
                    }
                }
                int decodedData = bits;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char ch2 = cArr2[i2];
                int bits2 = b64variant.decodeBase64Char(ch2);
                if (bits2 < 0) {
                    bits2 = _decodeBase64Escape(b64variant, ch2, 1);
                }
                int decodedData2 = (decodedData << 6) | bits2;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr3 = this._inputBuffer;
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                char ch3 = cArr3[i3];
                int bits3 = b64variant.decodeBase64Char(ch3);
                if (bits3 < 0) {
                    if (bits3 != -2) {
                        if (ch3 != '\"' || b64variant.usesPadding()) {
                            bits3 = _decodeBase64Escape(b64variant, ch3, 2);
                        } else {
                            builder.append(decodedData2 >> 4);
                            return builder.toByteArray();
                        }
                    }
                    if (bits3 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            loadMoreGuaranteed();
                        }
                        char[] cArr4 = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        char ch4 = cArr4[i4];
                        if (!b64variant.usesPaddingChar(ch4)) {
                            throw reportInvalidBase64Char(b64variant, ch4, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                        }
                        builder.append(decodedData2 >> 4);
                    }
                }
                int decodedData3 = (decodedData2 << 6) | bits3;
                if (this._inputPtr >= this._inputEnd) {
                    loadMoreGuaranteed();
                }
                char[] cArr5 = this._inputBuffer;
                int i5 = this._inputPtr;
                this._inputPtr = i5 + 1;
                char ch5 = cArr5[i5];
                int bits4 = b64variant.decodeBase64Char(ch5);
                if (bits4 < 0) {
                    if (bits4 != -2) {
                        if (ch5 != '\"' || b64variant.usesPadding()) {
                            bits4 = _decodeBase64Escape(b64variant, ch5, 3);
                        } else {
                            builder.appendTwoBytes(decodedData3 >> 2);
                            return builder.toByteArray();
                        }
                    }
                    if (bits4 == -2) {
                        builder.appendTwoBytes(decodedData3 >> 2);
                    }
                }
                builder.appendThreeBytes((decodedData3 << 6) | bits4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidToken(String matchedPart, String msg) throws IOException, JsonParseException {
        StringBuilder sb = new StringBuilder(matchedPart);
        while (true) {
            if (this._inputPtr >= this._inputEnd && !loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            if (!Character.isJavaIdentifierPart(c)) {
                break;
            }
            this._inputPtr++;
            sb.append(c);
        }
        _reportError("Unrecognized token '" + sb.toString() + "': was expecting ");
    }
}
