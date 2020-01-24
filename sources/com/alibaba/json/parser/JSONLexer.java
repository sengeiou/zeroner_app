package com.alibaba.json.parser;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.github.mikephil.charting.utils.Utils;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.text.Typography;

public final class JSONLexer {
    public static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    public static final int END = 4;
    public static final char EOI = '\u001a';
    static final int[] IA = new int[256];
    public static final int NOT_MATCH = -1;
    public static final int NOT_MATCH_NAME = -2;
    public static final int UNKNOWN = 0;
    private static boolean V6 = false;
    public static final int VALUE = 3;
    protected static final int[] digits = new int[103];
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];
    private static final ThreadLocal<char[]> sbufLocal = new ThreadLocal<>();
    protected int bp;
    public Calendar calendar;
    protected char ch;
    public boolean disableCircularReferenceDetect;
    protected int eofPos;
    protected boolean exp;
    public int features;
    protected long fieldHash;
    protected boolean hasSpecial;
    protected boolean isDouble;
    protected final int len;
    public Locale locale;
    public int matchStat;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected final String text;
    public TimeZone timeZone;
    protected int token;

    static {
        boolean z;
        int version = -1;
        try {
            version = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt(null);
        } catch (Exception e) {
        }
        if (version >= 23) {
            z = true;
        } else {
            z = false;
        }
        V6 = z;
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
        Arrays.fill(IA, -1);
        int iS = CA.length;
        for (int i4 = 0; i4 < iS; i4++) {
            IA[CA[i4]] = i4;
        }
        IA[61] = 0;
        for (char c = 0; c < firstIdentifierFlags.length; c = (char) (c + 1)) {
            if (c >= 'A' && c <= 'Z') {
                firstIdentifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                firstIdentifierFlags[c] = true;
            } else if (c == '_') {
                firstIdentifierFlags[c] = true;
            }
        }
        for (char c2 = 0; c2 < identifierFlags.length; c2 = (char) (c2 + 1)) {
            if (c2 >= 'A' && c2 <= 'Z') {
                identifierFlags[c2] = true;
            } else if (c2 >= 'a' && c2 <= 'z') {
                identifierFlags[c2] = true;
            } else if (c2 == '_') {
                identifierFlags[c2] = true;
            } else if (c2 >= '0' && c2 <= '9') {
                identifierFlags[c2] = true;
            }
        }
    }

    public JSONLexer(String input) {
        this(input, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONLexer(char[] input, int inputLength) {
        this(input, inputLength, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONLexer(char[] input, int inputLength, int features2) {
        this(new String(input, 0, inputLength), features2);
    }

    public JSONLexer(String input, int features2) {
        char charAt;
        String str;
        boolean z;
        this.features = JSON.DEFAULT_PARSER_FEATURE;
        this.exp = false;
        this.isDouble = false;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        this.calendar = null;
        this.matchStat = 0;
        this.sbuf = (char[]) sbufLocal.get();
        if (this.sbuf == null) {
            this.sbuf = new char[512];
        }
        this.features = features2;
        this.text = input;
        this.len = this.text.length();
        this.bp = -1;
        int index = this.bp + 1;
        this.bp = index;
        if (index >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index);
        }
        this.ch = charAt;
        if (this.ch == 65279) {
            next();
        }
        if ((Feature.InitStringFieldAsEmpty.mask & features2) != 0) {
            str = "";
        } else {
            str = null;
        }
        this.stringDefaultValue = str;
        if ((Feature.DisableCircularReferenceDetect.mask & features2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.disableCircularReferenceDetect = z;
    }

    public final int token() {
        return this.token;
    }

    public void close() {
        if (this.sbuf.length <= 8196) {
            sbufLocal.set(this.sbuf);
        }
        this.sbuf = null;
    }

    public char next() {
        char charAt;
        int index = this.bp + 1;
        this.bp = index;
        if (index >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index);
        }
        this.ch = charAt;
        return charAt;
    }

    public final void config(Feature feature, boolean state) {
        if (state) {
            this.features |= feature.mask;
        } else {
            this.features &= feature.mask ^ -1;
        }
        if (feature == Feature.InitStringFieldAsEmpty) {
            this.stringDefaultValue = state ? "" : null;
        }
        this.disableCircularReferenceDetect = (this.features & Feature.DisableCircularReferenceDetect.mask) != 0;
    }

    public final boolean isEnabled(Feature feature) {
        return (this.features & feature.mask) != 0;
    }

    public final void nextTokenWithChar(char expect) {
        char charAt;
        this.sp = 0;
        while (this.ch != expect) {
            if (this.ch == ' ' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
                next();
            } else {
                throw new JSONException("not match " + expect + " - " + this.ch);
            }
        }
        int index = this.bp + 1;
        this.bp = index;
        if (index >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index);
        }
        this.ch = charAt;
        nextToken();
    }

    public final String numberString() {
        char chLocal = this.text.charAt((this.np + this.sp) - 1);
        int sp2 = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp2--;
        }
        return subString(this.np, sp2);
    }

    /* access modifiers changed from: protected */
    public char charAt(int index) {
        if (index >= this.len) {
            return 26;
        }
        return this.text.charAt(index);
    }

    public final void nextToken() {
        char c = 26;
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            if (this.ch == '/') {
                skipComment();
            } else if (this.ch == '\"') {
                scanString();
                return;
            } else if ((this.ch < '0' || this.ch > '9') && this.ch != '-') {
                if (this.ch == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                switch (this.ch) {
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        next();
                        break;
                    case '\'':
                        scanString();
                        return;
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        int index = this.bp + 1;
                        this.bp = index;
                        if (index < this.len) {
                            c = this.text.charAt(index);
                        }
                        this.ch = c;
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        if (this.text.startsWith("false", this.bp)) {
                            this.bp += 5;
                            this.ch = charAt(this.bp);
                            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8 || this.ch == ':') {
                                this.token = 7;
                                return;
                            }
                        }
                        throw new JSONException("scan false error");
                    case 'n':
                        int token2 = 0;
                        if (this.text.startsWith("null", this.bp)) {
                            this.bp += 4;
                            token2 = 8;
                        } else if (this.text.startsWith("new", this.bp)) {
                            this.bp += 3;
                            token2 = 9;
                        }
                        if (token2 != 0) {
                            this.ch = charAt(this.bp);
                            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8) {
                                this.token = token2;
                                return;
                            }
                        }
                        throw new JSONException("scan null/new error");
                    case 't':
                        if (this.text.startsWith("true", this.bp)) {
                            this.bp += 4;
                            this.ch = charAt(this.bp);
                            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8 || this.ch == ':') {
                                this.token = 6;
                                return;
                            }
                        }
                        throw new JSONException("scan true error");
                    case '{':
                        int index2 = this.bp + 1;
                        this.bp = index2;
                        if (index2 < this.len) {
                            c = this.text.charAt(index2);
                        }
                        this.ch = c;
                        this.token = 12;
                        return;
                    case Opcodes.NEG_LONG /*125*/:
                        int index3 = this.bp + 1;
                        this.bp = index3;
                        if (index3 < this.len) {
                            c = this.text.charAt(index3);
                        }
                        this.ch = c;
                        this.token = 13;
                        return;
                    default:
                        if (this.bp == this.len || (this.ch == 26 && this.bp + 1 == this.len)) {
                            if (this.token == 20) {
                                throw new JSONException("EOF error");
                            }
                            this.token = 20;
                            int i = this.eofPos;
                            this.bp = i;
                            this.pos = i;
                            return;
                        } else if (this.ch <= 31 || this.ch == 127) {
                            next();
                            break;
                        } else {
                            this.token = 1;
                            next();
                            return;
                        }
                }
            }
        }
        scanNumber();
    }

    public final void nextToken(int expect) {
        char c = 26;
        this.sp = 0;
        while (true) {
            switch (expect) {
                case 2:
                    if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        return;
                    } else if (this.ch == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (this.ch == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (this.ch == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                    break;
                case 4:
                    if (this.ch == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        return;
                    } else if (this.ch == '{') {
                        this.token = 12;
                        int index = this.bp + 1;
                        this.bp = index;
                        if (index < this.len) {
                            c = this.text.charAt(index);
                        }
                        this.ch = c;
                        return;
                    }
                    break;
                case 12:
                    if (this.ch == '{') {
                        this.token = 12;
                        int index2 = this.bp + 1;
                        this.bp = index2;
                        if (index2 < this.len) {
                            c = this.text.charAt(index2);
                        }
                        this.ch = c;
                        return;
                    } else if (this.ch == '[') {
                        this.token = 14;
                        int index3 = this.bp + 1;
                        this.bp = index3;
                        if (index3 < this.len) {
                            c = this.text.charAt(index3);
                        }
                        this.ch = c;
                        return;
                    }
                    break;
                case 14:
                    if (this.ch == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (this.ch == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                    break;
                case 15:
                    if (this.ch == ']') {
                        this.token = 15;
                        next();
                        return;
                    }
                    break;
                case 16:
                    if (this.ch == ',') {
                        this.token = 16;
                        int index4 = this.bp + 1;
                        this.bp = index4;
                        if (index4 < this.len) {
                            c = this.text.charAt(index4);
                        }
                        this.ch = c;
                        return;
                    } else if (this.ch == '}') {
                        this.token = 13;
                        int index5 = this.bp + 1;
                        this.bp = index5;
                        if (index5 < this.len) {
                            c = this.text.charAt(index5);
                        }
                        this.ch = c;
                        return;
                    } else if (this.ch == ']') {
                        this.token = 15;
                        int index6 = this.bp + 1;
                        this.bp = index6;
                        if (index6 < this.len) {
                            c = this.text.charAt(index6);
                        }
                        this.ch = c;
                        return;
                    } else if (this.ch == 26) {
                        this.token = 20;
                        return;
                    }
                    break;
                case 18:
                    nextIdent();
                    return;
                case 20:
                    break;
            }
            if (this.ch == 26) {
                this.token = 20;
                return;
            }
            if (this.ch == ' ' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
                next();
            } else {
                nextToken();
                return;
            }
        }
    }

    public final void nextIdent() {
        while (true) {
            if (!(this.ch <= ' ' && (this.ch == ' ' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8))) {
                break;
            }
            next();
        }
        if (this.ch == '_' || Character.isLetter(this.ch)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    public final Number integerValue() throws NumberFormatException {
        char chLocal;
        char chLocal2;
        long limit;
        int i;
        char chLocal3;
        char chLocal4;
        long result = 0;
        boolean negative = false;
        int i2 = this.np;
        int max = this.np + this.sp;
        char type = ' ';
        int charIndex = max - 1;
        if (charIndex >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex);
        }
        switch (chLocal) {
            case 'B':
                max--;
                type = 'B';
                break;
            case 'L':
                max--;
                type = 'L';
                break;
            case 'S':
                max--;
                type = 'S';
                break;
        }
        if (this.np >= this.len) {
            chLocal2 = 26;
        } else {
            chLocal2 = this.text.charAt(this.np);
        }
        if (chLocal2 == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i = i2 + 1;
        } else {
            limit = -9223372036854775807L;
            i = i2;
        }
        if (i < max) {
            int i3 = i + 1;
            int charIndex2 = i;
            if (charIndex2 >= this.len) {
                chLocal4 = 26;
            } else {
                chLocal4 = this.text.charAt(charIndex2);
            }
            result = (long) (-(chLocal4 - '0'));
            i = i3;
        }
        while (i < max) {
            int i4 = i + 1;
            int charIndex3 = i;
            if (charIndex3 >= this.len) {
                chLocal3 = 26;
            } else {
                chLocal3 = this.text.charAt(charIndex3);
            }
            int digit = chLocal3 - '0';
            if (result < -922337203685477580L) {
                return new BigInteger(numberString());
            }
            long result2 = result * 10;
            if (result2 < ((long) digit) + limit) {
                return new BigInteger(numberString());
            }
            result = result2 - ((long) digit);
            i = i4;
        }
        if (!negative) {
            long result3 = -result;
            if (result3 > 2147483647L || type == 'L') {
                int i5 = i;
                return Long.valueOf(result3);
            } else if (type == 'S') {
                int i6 = i;
                return Short.valueOf((short) ((int) result3));
            } else if (type == 'B') {
                int i7 = i;
                return Byte.valueOf((byte) ((int) result3));
            } else {
                int i8 = i;
                return Integer.valueOf((int) result3);
            }
        } else if (i <= this.np + 1) {
            throw new NumberFormatException(numberString());
        } else if (result < -2147483648L || type == 'L') {
            int i9 = i;
            return Long.valueOf(result);
        } else if (type == 'S') {
            int i10 = i;
            return Short.valueOf((short) ((int) result));
        } else if (type == 'B') {
            int i11 = i;
            return Byte.valueOf((byte) ((int) result));
        } else {
            int i12 = i;
            return Integer.valueOf((int) result);
        }
    }

    public final String scanSymbol(SymbolTable symbolTable) {
        while (true) {
            if (this.ch != ' ' && this.ch != 10 && this.ch != 13 && this.ch != 9 && this.ch != 12 && this.ch != 8) {
                break;
            }
            next();
        }
        if (this.ch == '\"') {
            return scanSymbol(symbolTable, Typography.quote);
        }
        if (this.ch == '\'') {
            return scanSymbol(symbolTable, '\'');
        }
        if (this.ch == '}') {
            next();
            this.token = 13;
            return null;
        } else if (this.ch == ',') {
            next();
            this.token = 16;
            return null;
        } else if (this.ch != 26) {
            return scanSymbolUnQuoted(symbolTable);
        } else {
            this.token = 20;
            return null;
        }
    }

    public String scanSymbol(SymbolTable symbolTable, char quoteChar) {
        String strVal;
        char charAt;
        int hash = 0;
        boolean hasSpecial2 = false;
        int startIndex = this.bp + 1;
        int endIndex = this.text.indexOf(quoteChar, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        int chars_len = endIndex - startIndex;
        char[] chars = sub_chars(this.bp + 1, chars_len);
        while (chars_len > 0 && chars[chars_len - 1] == '\\') {
            int slashCount = 1;
            int i = chars_len - 2;
            while (i >= 0 && chars[i] == '\\') {
                slashCount++;
                i--;
            }
            if (slashCount % 2 == 0) {
                break;
            }
            int nextIndex = this.text.indexOf(quoteChar, endIndex + 1);
            int next_chars_len = chars_len + (nextIndex - endIndex);
            if (next_chars_len >= chars.length) {
                int newLen = (chars.length * 3) / 2;
                if (newLen < next_chars_len) {
                    newLen = next_chars_len;
                }
                char[] newChars = new char[newLen];
                System.arraycopy(chars, 0, newChars, 0, chars.length);
                chars = newChars;
            }
            this.text.getChars(endIndex, nextIndex, chars, chars_len);
            chars_len = next_chars_len;
            endIndex = nextIndex;
            hasSpecial2 = true;
        }
        if (!hasSpecial2) {
            for (int i2 = 0; i2 < chars_len; i2++) {
                char ch2 = chars[i2];
                hash = (hash * 31) + ch2;
                if (ch2 == '\\') {
                    hasSpecial2 = true;
                }
            }
            strVal = hasSpecial2 ? readString(chars, chars_len) : chars_len < 20 ? symbolTable.addSymbol(chars, 0, chars_len, hash) : new String(chars, 0, chars_len);
        } else {
            strVal = readString(chars, chars_len);
        }
        this.bp = endIndex + 1;
        int index = this.bp;
        if (index >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index);
        }
        this.ch = charAt;
        return strVal;
    }

    private static String readString(char[] chars, int chars_len) {
        int len2;
        char[] sbuf2 = new char[chars_len];
        int i = 0;
        int len3 = 0;
        while (i < chars_len) {
            char ch2 = chars[i];
            if (ch2 != '\\') {
                len2 = len3 + 1;
                sbuf2[len3] = ch2;
            } else {
                i++;
                switch (chars[i]) {
                    case '\"':
                        len2 = len3 + 1;
                        sbuf2[len3] = Typography.quote;
                        break;
                    case '\'':
                        len2 = len3 + 1;
                        sbuf2[len3] = '\'';
                        break;
                    case '/':
                        len2 = len3 + 1;
                        sbuf2[len3] = '/';
                        break;
                    case '0':
                        len2 = len3 + 1;
                        sbuf2[len3] = 0;
                        break;
                    case '1':
                        len2 = len3 + 1;
                        sbuf2[len3] = 1;
                        break;
                    case '2':
                        len2 = len3 + 1;
                        sbuf2[len3] = 2;
                        break;
                    case '3':
                        len2 = len3 + 1;
                        sbuf2[len3] = 3;
                        break;
                    case '4':
                        len2 = len3 + 1;
                        sbuf2[len3] = 4;
                        break;
                    case '5':
                        len2 = len3 + 1;
                        sbuf2[len3] = 5;
                        break;
                    case '6':
                        len2 = len3 + 1;
                        sbuf2[len3] = 6;
                        break;
                    case '7':
                        len2 = len3 + 1;
                        sbuf2[len3] = 7;
                        break;
                    case 'F':
                    case 'f':
                        len2 = len3 + 1;
                        sbuf2[len3] = 12;
                        break;
                    case '\\':
                        len2 = len3 + 1;
                        sbuf2[len3] = '\\';
                        break;
                    case 'b':
                        len2 = len3 + 1;
                        sbuf2[len3] = 8;
                        break;
                    case 'n':
                        len2 = len3 + 1;
                        sbuf2[len3] = 10;
                        break;
                    case 'r':
                        len2 = len3 + 1;
                        sbuf2[len3] = 13;
                        break;
                    case 't':
                        len2 = len3 + 1;
                        sbuf2[len3] = 9;
                        break;
                    case 'u':
                        len2 = len3 + 1;
                        int i2 = i + 1;
                        int i3 = i2 + 1;
                        int i4 = i3 + 1;
                        i = i4 + 1;
                        sbuf2[len3] = (char) Integer.parseInt(new String(new char[]{chars[i2], chars[i3], chars[i4], chars[i]}), 16);
                        break;
                    case 'v':
                        len2 = len3 + 1;
                        sbuf2[len3] = 11;
                        break;
                    case 'x':
                        len2 = len3 + 1;
                        int i5 = i + 1;
                        i = i5 + 1;
                        sbuf2[len3] = (char) ((digits[chars[i5]] * 16) + digits[chars[i]]);
                        break;
                    default:
                        throw new JSONException("unclosed.str.lit");
                }
            }
            i++;
            len3 = len2;
        }
        return new String(sbuf2, 0, len3);
    }

    public String info() {
        String substring;
        StringBuilder append = new StringBuilder().append("pos ").append(this.bp).append(", json : ");
        if (this.len < 65536) {
            substring = this.text;
        } else {
            substring = this.text.substring(0, 65536);
        }
        return append.append(substring).toString();
    }

    /* access modifiers changed from: protected */
    public void skipComment() {
        next();
        if (this.ch == '/') {
            do {
                next();
            } while (this.ch != 10);
            next();
        } else if (this.ch == '*') {
            next();
            while (this.ch != 26) {
                if (this.ch == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
        } else {
            throw new JSONException("invalid comment");
        }
    }

    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        int i = this.ch;
        if (!(this.ch >= firstIdentifierFlags.length || firstIdentifierFlags[i])) {
            throw new JSONException("illegal identifier : " + this.ch + ", " + info());
        }
        int hash = i;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char ch2 = next();
            if (ch2 < identifierFlags.length && !identifierFlags[ch2]) {
                break;
            }
            hash = (hash * 31) + ch2;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp != 4 || !this.text.startsWith("null", this.np)) {
            return symbolTable.addSymbol(this.text, this.np, this.sp, hash);
        }
        return null;
    }

    public final void scanString() {
        char charAt;
        char quoteChar = this.ch;
        boolean hasSpecial2 = false;
        int startIndex = this.bp + 1;
        int endIndex = this.text.indexOf(quoteChar, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        int chars_len = endIndex - startIndex;
        char[] chars = sub_chars(this.bp + 1, chars_len);
        while (chars_len > 0 && chars[chars_len - 1] == '\\') {
            int slashCount = 1;
            int i = chars_len - 2;
            while (i >= 0 && chars[i] == '\\') {
                slashCount++;
                i--;
            }
            if (slashCount % 2 == 0) {
                break;
            }
            int nextIndex = this.text.indexOf(quoteChar, endIndex + 1);
            int next_chars_len = chars_len + (nextIndex - endIndex);
            if (next_chars_len >= chars.length) {
                int newLen = (chars.length * 3) / 2;
                if (newLen < next_chars_len) {
                    newLen = next_chars_len;
                }
                char[] newChars = new char[newLen];
                System.arraycopy(chars, 0, newChars, 0, chars.length);
                chars = newChars;
            }
            this.text.getChars(endIndex, nextIndex, chars, chars_len);
            chars_len = next_chars_len;
            endIndex = nextIndex;
            hasSpecial2 = true;
        }
        if (!hasSpecial2) {
            for (int i2 = 0; i2 < chars_len; i2++) {
                if (chars[i2] == '\\') {
                    hasSpecial2 = true;
                }
            }
        }
        this.sbuf = chars;
        this.sp = chars_len;
        this.np = this.bp;
        this.hasSpecial = hasSpecial2;
        this.bp = endIndex + 1;
        int index = this.bp;
        if (index >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index);
        }
        this.ch = charAt;
        this.token = 4;
    }

    public String scanStringValue(char quoteChar) {
        String strVal;
        char charAt;
        int startIndex = this.bp + 1;
        int endIndex = this.text.indexOf(quoteChar, startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        if (V6) {
            strVal = this.text.substring(startIndex, endIndex);
        } else {
            int chars_len = endIndex - startIndex;
            strVal = new String(sub_chars(this.bp + 1, chars_len), 0, chars_len);
        }
        if (strVal.indexOf(92) != -1) {
            while (true) {
                int slashCount = 0;
                int i = endIndex - 1;
                while (i >= 0 && this.text.charAt(i) == '\\') {
                    slashCount++;
                    i--;
                }
                if (slashCount % 2 == 0) {
                    break;
                }
                endIndex = this.text.indexOf(quoteChar, endIndex + 1);
            }
            int chars_len2 = endIndex - startIndex;
            strVal = readString(sub_chars(this.bp + 1, chars_len2), chars_len2);
        }
        this.bp = endIndex + 1;
        int index = this.bp;
        if (index >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index);
        }
        this.ch = charAt;
        return strVal;
    }

    public final int intValue() {
        char chLocal;
        int limit;
        int i;
        int i2;
        int charAt;
        int charAt2;
        int result = 0;
        boolean negative = false;
        int i3 = this.np;
        int max = this.np + this.sp;
        if (this.np >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(this.np);
        }
        if (chLocal == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i = i3 + 1;
        } else {
            limit = -2147483647;
            i = i3;
        }
        if (i < max) {
            int i4 = i + 1;
            int charIndex = i;
            if (charIndex >= this.len) {
                charAt2 = 26;
            } else {
                charAt2 = this.text.charAt(charIndex);
            }
            result = -(charAt2 - 48);
            i = i4;
        }
        while (true) {
            if (i >= max) {
                i2 = i;
                break;
            }
            i2 = i + 1;
            int charIndex2 = i;
            if (charIndex2 >= this.len) {
                charAt = 26;
            } else {
                charAt = this.text.charAt(charIndex2);
            }
            if (charAt == 76 || charAt == 83 || charAt == 66) {
                break;
            }
            int digit = charAt - 48;
            if (result < -214748364) {
                throw new NumberFormatException(numberString());
            }
            int result2 = result * 10;
            if (result2 < limit + digit) {
                throw new NumberFormatException(numberString());
            }
            result = result2 - digit;
            i = i2;
        }
        if (!negative) {
            return -result;
        }
        if (i2 > this.np + 1) {
            return result;
        }
        throw new NumberFormatException(numberString());
    }

    public byte[] bytesValue() {
        return decodeFast(this.text, this.np + 1, this.sp);
    }

    private void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String ident = stringVal();
        if (ident.equals("null")) {
            this.token = 8;
        } else if (ident.equals("true")) {
            this.token = 6;
        } else if (ident.equals("false")) {
            this.token = 7;
        } else if (ident.equals("new")) {
            this.token = 9;
        } else if (ident.equals("undefined")) {
            this.token = 23;
        } else if (ident.equals("Set")) {
            this.token = 21;
        } else if (ident.equals("TreeSet")) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public final String stringVal() {
        if (this.hasSpecial) {
            return readString(this.sbuf, this.sp);
        }
        return subString(this.np + 1, this.sp);
    }

    private final String subString(int offset, int count) {
        if (count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return new String(this.sbuf, 0, count);
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return new String(chars);
    }

    /* access modifiers changed from: 0000 */
    public final char[] sub_chars(int offset, int count) {
        if (count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return this.sbuf;
        }
        char[] chars = new char[count];
        this.sbuf = chars;
        this.text.getChars(offset, offset + count, chars, 0);
        return chars;
    }

    public final boolean isBlankInput() {
        boolean whitespace;
        int i = 0;
        while (true) {
            char ch2 = charAt(i);
            if (ch2 == 26) {
                return true;
            }
            if (ch2 > ' ' || !(ch2 == ' ' || ch2 == 10 || ch2 == 13 || ch2 == 9 || ch2 == 12 || ch2 == 8)) {
                whitespace = false;
            } else {
                whitespace = true;
            }
            if (!whitespace) {
                return false;
            }
            i++;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void skipWhitespace() {
        while (this.ch <= '/') {
            if (this.ch == ' ' || this.ch == 13 || this.ch == 10 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
                next();
            } else if (this.ch == '/') {
                skipComment();
            } else {
                return;
            }
        }
    }

    public final void scanNumber() {
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        char charAt5;
        char charAt6;
        char charAt7;
        this.np = this.bp;
        this.exp = false;
        if (this.ch == '-') {
            this.sp++;
            int index = this.bp + 1;
            this.bp = index;
            if (index >= this.len) {
                charAt7 = 26;
            } else {
                charAt7 = this.text.charAt(index);
            }
            this.ch = charAt7;
        }
        while (this.ch >= '0' && this.ch <= '9') {
            this.sp++;
            int index2 = this.bp + 1;
            this.bp = index2;
            if (index2 >= this.len) {
                charAt6 = 26;
            } else {
                charAt6 = this.text.charAt(index2);
            }
            this.ch = charAt6;
        }
        this.isDouble = false;
        if (this.ch == '.') {
            this.sp++;
            int index3 = this.bp + 1;
            this.bp = index3;
            if (index3 >= this.len) {
                charAt4 = 26;
            } else {
                charAt4 = this.text.charAt(index3);
            }
            this.ch = charAt4;
            this.isDouble = true;
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                int index4 = this.bp + 1;
                this.bp = index4;
                if (index4 >= this.len) {
                    charAt5 = 26;
                } else {
                    charAt5 = this.text.charAt(index4);
                }
                this.ch = charAt5;
            }
        }
        if (this.ch == 'L') {
            this.sp++;
            next();
        } else if (this.ch == 'S') {
            this.sp++;
            next();
        } else if (this.ch == 'B') {
            this.sp++;
            next();
        } else if (this.ch == 'F') {
            this.sp++;
            next();
            this.isDouble = true;
        } else if (this.ch == 'D') {
            this.sp++;
            next();
            this.isDouble = true;
        } else if (this.ch == 'e' || this.ch == 'E') {
            this.sp++;
            int index5 = this.bp + 1;
            this.bp = index5;
            if (index5 >= this.len) {
                charAt = 26;
            } else {
                charAt = this.text.charAt(index5);
            }
            this.ch = charAt;
            if (this.ch == '+' || this.ch == '-') {
                this.sp++;
                int index6 = this.bp + 1;
                this.bp = index6;
                if (index6 >= this.len) {
                    charAt3 = 26;
                } else {
                    charAt3 = this.text.charAt(index6);
                }
                this.ch = charAt3;
            }
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                int index7 = this.bp + 1;
                this.bp = index7;
                if (index7 >= this.len) {
                    charAt2 = 26;
                } else {
                    charAt2 = this.text.charAt(index7);
                }
                this.ch = charAt2;
            }
            if (this.ch == 'D' || this.ch == 'F') {
                this.sp++;
                next();
            }
            this.exp = true;
            this.isDouble = true;
        }
        if (this.isDouble) {
            this.token = 3;
        } else {
            this.token = 2;
        }
    }

    public boolean scanBoolean() {
        int offset;
        boolean value;
        if (this.text.startsWith("false", this.bp)) {
            offset = 5;
            value = false;
        } else if (this.text.startsWith("true", this.bp)) {
            offset = 4;
            value = true;
        } else if (this.ch == '1') {
            offset = 1;
            value = true;
        } else if (this.ch == '0') {
            offset = 1;
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        this.bp += offset;
        this.ch = charAt(this.bp);
        return value;
    }

    /* JADX WARNING: type inference failed for: r25v1, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r25v2, types: [java.lang.Float] */
    /* JADX WARNING: type inference failed for: r25v3, types: [java.lang.Byte] */
    /* JADX WARNING: type inference failed for: r25v4, types: [java.lang.Short] */
    /* JADX WARNING: type inference failed for: r25v6, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r25v7, types: [java.lang.Float] */
    /* JADX WARNING: type inference failed for: r25v8 */
    /* JADX WARNING: type inference failed for: r31v39, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r25v10 */
    /* JADX WARNING: type inference failed for: r25v11 */
    /* JADX WARNING: type inference failed for: r31v44, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r25v12, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r25v13, types: [java.lang.Integer] */
    /* JADX WARNING: type inference failed for: r25v15, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r25v22 */
    /* JADX WARNING: type inference failed for: r25v23 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Number scanNumberValue() {
        /*
            r36 = this;
            r0 = r36
            int r0 = r0.bp
            r28 = r0
            r26 = 0
            r25 = 0
            r31 = 0
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 45
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x00da
            r24 = 1
            r20 = -9223372036854775808
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x00ca
            r31 = 26
        L_0x004e:
            r0 = r31
            r1 = r36
            r1.ch = r0
        L_0x0054:
            r22 = 0
        L_0x0056:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 48
            r0 = r31
            r1 = r32
            if (r0 < r1) goto L_0x00f2
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 57
            r0 = r31
            r1 = r32
            if (r0 > r1) goto L_0x00f2
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            int r8 = r31 + -48
            r32 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r31 = (r22 > r32 ? 1 : (r22 == r32 ? 0 : -1))
            if (r31 >= 0) goto L_0x0085
            r26 = 1
        L_0x0085:
            r32 = 10
            long r22 = r22 * r32
            long r0 = (long) r8
            r32 = r0
            long r32 = r32 + r20
            int r31 = (r22 > r32 ? 1 : (r22 == r32 ? 0 : -1))
            if (r31 >= 0) goto L_0x0094
            r26 = 1
        L_0x0094:
            long r0 = (long) r8
            r32 = r0
            long r22 = r22 - r32
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x00e3
            r31 = 26
        L_0x00c3:
            r0 = r31
            r1 = r36
            r1.ch = r0
            goto L_0x0056
        L_0x00ca:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x004e
        L_0x00da:
            r24 = 0
            r20 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x0054
        L_0x00e3:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x00c3
        L_0x00f2:
            if (r24 != 0) goto L_0x00f9
            r0 = r22
            long r0 = -r0
            r22 = r0
        L_0x00f9:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 76
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x01ac
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r36.next()
            java.lang.Long r25 = java.lang.Long.valueOf(r22)
        L_0x011c:
            r18 = 0
            r12 = 0
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 46
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x027e
            r18 = 1
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x025e
            r31 = 26
        L_0x0159:
            r0 = r31
            r1 = r36
            r1.ch = r0
        L_0x015f:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 48
            r0 = r31
            r1 = r32
            if (r0 < r1) goto L_0x027e
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 57
            r0 = r31
            r1 = r32
            if (r0 > r1) goto L_0x027e
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x026e
            r31 = 26
        L_0x01a5:
            r0 = r31
            r1 = r36
            r1.ch = r0
            goto L_0x015f
        L_0x01ac:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 83
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x01db
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r36.next()
            r0 = r22
            int r0 = (int) r0
            r31 = r0
            r0 = r31
            short r0 = (short) r0
            r31 = r0
            java.lang.Short r25 = java.lang.Short.valueOf(r31)
            goto L_0x011c
        L_0x01db:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 66
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x020a
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r36.next()
            r0 = r22
            int r0 = (int) r0
            r31 = r0
            r0 = r31
            byte r0 = (byte) r0
            r31 = r0
            java.lang.Byte r25 = java.lang.Byte.valueOf(r31)
            goto L_0x011c
        L_0x020a:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 70
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0234
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r36.next()
            r0 = r22
            float r0 = (float) r0
            r31 = r0
            java.lang.Float r25 = java.lang.Float.valueOf(r31)
            goto L_0x011c
        L_0x0234:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 68
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x011c
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r36.next()
            r0 = r22
            double r0 = (double) r0
            r32 = r0
            java.lang.Double r25 = java.lang.Double.valueOf(r32)
            goto L_0x011c
        L_0x025e:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x0159
        L_0x026e:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x01a5
        L_0x027e:
            r30 = 0
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 101(0x65, float:1.42E-43)
            r0 = r31
            r1 = r32
            if (r0 == r1) goto L_0x029c
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 69
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x03c7
        L_0x029c:
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x0365
            r31 = 26
        L_0x02c6:
            r0 = r31
            r1 = r36
            r1.ch = r0
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 43
            r0 = r31
            r1 = r32
            if (r0 == r1) goto L_0x02e8
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 45
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0318
        L_0x02e8:
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x0375
            r31 = 26
        L_0x0312:
            r0 = r31
            r1 = r36
            r1.ch = r0
        L_0x0318:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 48
            r0 = r31
            r1 = r32
            if (r0 < r1) goto L_0x0393
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 57
            r0 = r31
            r1 = r32
            if (r0 > r1) goto L_0x0393
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r16 = r31 + 1
            r0 = r16
            r1 = r36
            r1.bp = r0
            r0 = r36
            int r0 = r0.len
            r31 = r0
            r0 = r16
            r1 = r31
            if (r0 < r1) goto L_0x0384
            r31 = 26
        L_0x035e:
            r0 = r31
            r1 = r36
            r1.ch = r0
            goto L_0x0318
        L_0x0365:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x02c6
        L_0x0375:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x0312
        L_0x0384:
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r31
            r1 = r16
            char r31 = r0.charAt(r1)
            goto L_0x035e
        L_0x0393:
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 68
            r0 = r31
            r1 = r32
            if (r0 == r1) goto L_0x03af
            r0 = r36
            char r0 = r0.ch
            r31 = r0
            r32 = 70
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x03c6
        L_0x03af:
            r0 = r36
            int r0 = r0.np
            r31 = r0
            int r31 = r31 + 1
            r0 = r31
            r1 = r36
            r1.np = r0
            r0 = r36
            char r0 = r0.ch
            r30 = r0
            r36.next()
        L_0x03c6:
            r12 = 1
        L_0x03c7:
            if (r18 != 0) goto L_0x0423
            if (r12 != 0) goto L_0x0423
            if (r26 == 0) goto L_0x0402
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r19 = r31 - r28
            r0 = r19
            char[] r7 = new char[r0]
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            r0 = r36
            int r0 = r0.bp
            r32 = r0
            r33 = 0
            r0 = r31
            r1 = r28
            r2 = r32
            r3 = r33
            r0.getChars(r1, r2, r7, r3)
            java.lang.String r29 = new java.lang.String
            r0 = r29
            r0.<init>(r7)
            java.math.BigInteger r25 = new java.math.BigInteger
            r0 = r25
            r1 = r29
            r0.<init>(r1)
        L_0x0402:
            if (r25 != 0) goto L_0x041b
            r32 = -2147483648(0xffffffff80000000, double:NaN)
            int r31 = (r22 > r32 ? 1 : (r22 == r32 ? 0 : -1))
            if (r31 <= 0) goto L_0x041e
            r32 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r31 = (r22 > r32 ? 1 : (r22 == r32 ? 0 : -1))
            if (r31 >= 0) goto L_0x041e
            r0 = r22
            int r0 = (int) r0
            r31 = r0
            java.lang.Integer r25 = java.lang.Integer.valueOf(r31)
        L_0x041b:
            r31 = r25
        L_0x041d:
            return r31
        L_0x041e:
            java.lang.Long r25 = java.lang.Long.valueOf(r22)
            goto L_0x041b
        L_0x0423:
            r0 = r36
            int r0 = r0.bp
            r31 = r0
            int r19 = r31 - r28
            if (r30 == 0) goto L_0x042f
            int r19 = r19 + -1
        L_0x042f:
            r0 = r36
            char[] r0 = r0.sbuf
            r31 = r0
            r0 = r31
            int r0 = r0.length
            r31 = r0
            r0 = r19
            r1 = r31
            if (r0 >= r1) goto L_0x0485
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            int r32 = r28 + r19
            r0 = r36
            char[] r0 = r0.sbuf
            r33 = r0
            r34 = 0
            r0 = r31
            r1 = r28
            r2 = r32
            r3 = r33
            r4 = r34
            r0.getChars(r1, r2, r3, r4)
            r0 = r36
            char[] r7 = r0.sbuf
        L_0x0461:
            if (r12 != 0) goto L_0x049f
            r0 = r36
            int r0 = r0.features
            r31 = r0
            com.alibaba.json.parser.Feature r32 = com.alibaba.json.parser.Feature.UseBigDecimal
            r0 = r32
            int r0 = r0.mask
            r32 = r0
            r31 = r31 & r32
            if (r31 == 0) goto L_0x049f
            java.math.BigDecimal r25 = new java.math.BigDecimal
            r31 = 0
            r0 = r25
            r1 = r31
            r2 = r19
            r0.<init>(r7, r1, r2)
        L_0x0482:
            r31 = r25
            goto L_0x041d
        L_0x0485:
            r0 = r19
            char[] r7 = new char[r0]
            r0 = r36
            java.lang.String r0 = r0.text
            r31 = r0
            int r32 = r28 + r19
            r33 = 0
            r0 = r31
            r1 = r28
            r2 = r32
            r3 = r33
            r0.getChars(r1, r2, r7, r3)
            goto L_0x0461
        L_0x049f:
            r31 = 9
            r0 = r19
            r1 = r31
            if (r0 > r1) goto L_0x0510
            if (r12 != 0) goto L_0x0510
            r14 = 0
            int r15 = r14 + 1
            char r6 = r7[r14]     // Catch:{ NumberFormatException -> 0x0535 }
            r31 = 45
            r0 = r31
            if (r6 == r0) goto L_0x04ba
            r31 = 43
            r0 = r31
            if (r6 != r0) goto L_0x0560
        L_0x04ba:
            int r14 = r15 + 1
            char r6 = r7[r15]     // Catch:{ NumberFormatException -> 0x0535 }
        L_0x04be:
            int r17 = r6 + -48
            r27 = 0
        L_0x04c2:
            r0 = r19
            if (r14 >= r0) goto L_0x04de
            char r6 = r7[r14]     // Catch:{ NumberFormatException -> 0x0535 }
            r31 = 46
            r0 = r31
            if (r6 != r0) goto L_0x04d3
            r27 = 1
        L_0x04d0:
            int r14 = r14 + 1
            goto L_0x04c2
        L_0x04d3:
            int r8 = r6 + -48
            int r31 = r17 * 10
            int r17 = r31 + r8
            if (r27 == 0) goto L_0x04d0
            int r27 = r27 * 10
            goto L_0x04d0
        L_0x04de:
            r31 = 70
            r0 = r30
            r1 = r31
            if (r0 != r1) goto L_0x04fb
            r0 = r17
            float r0 = (float) r0     // Catch:{ NumberFormatException -> 0x0535 }
            r31 = r0
            r0 = r27
            float r0 = (float) r0     // Catch:{ NumberFormatException -> 0x0535 }
            r32 = r0
            float r13 = r31 / r32
            if (r24 == 0) goto L_0x04f5
            float r13 = -r13
        L_0x04f5:
            java.lang.Float r31 = java.lang.Float.valueOf(r13)     // Catch:{ NumberFormatException -> 0x0535 }
            goto L_0x041d
        L_0x04fb:
            r0 = r17
            double r0 = (double) r0     // Catch:{ NumberFormatException -> 0x0535 }
            r32 = r0
            r0 = r27
            double r0 = (double) r0     // Catch:{ NumberFormatException -> 0x0535 }
            r34 = r0
            double r10 = r32 / r34
            if (r24 == 0) goto L_0x050a
            double r10 = -r10
        L_0x050a:
            java.lang.Double r31 = java.lang.Double.valueOf(r10)     // Catch:{ NumberFormatException -> 0x0535 }
            goto L_0x041d
        L_0x0510:
            java.lang.String r29 = new java.lang.String     // Catch:{ NumberFormatException -> 0x0535 }
            r31 = 0
            r0 = r29
            r1 = r31
            r2 = r19
            r0.<init>(r7, r1, r2)     // Catch:{ NumberFormatException -> 0x0535 }
            r31 = 70
            r0 = r30
            r1 = r31
            if (r0 != r1) goto L_0x052b
            java.lang.Float r25 = java.lang.Float.valueOf(r29)     // Catch:{ NumberFormatException -> 0x0535 }
            goto L_0x0482
        L_0x052b:
            double r32 = java.lang.Double.parseDouble(r29)     // Catch:{ NumberFormatException -> 0x0535 }
            java.lang.Double r25 = java.lang.Double.valueOf(r32)     // Catch:{ NumberFormatException -> 0x0535 }
            goto L_0x0482
        L_0x0535:
            r9 = move-exception
            com.alibaba.json.JSONException r31 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r32 = new java.lang.StringBuilder
            r32.<init>()
            java.lang.String r33 = r9.getMessage()
            java.lang.StringBuilder r32 = r32.append(r33)
            java.lang.String r33 = ", "
            java.lang.StringBuilder r32 = r32.append(r33)
            java.lang.String r33 = r36.info()
            java.lang.StringBuilder r32 = r32.append(r33)
            java.lang.String r32 = r32.toString()
            r0 = r31
            r1 = r32
            r0.<init>(r1, r9)
            throw r31
        L_0x0560:
            r14 = r15
            goto L_0x04be
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.scanNumberValue():java.lang.Number");
    }

    public final long scanLongValue() {
        boolean negative;
        long limit;
        char charAt;
        this.np = 0;
        if (this.ch == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            this.np++;
            int index = this.bp + 1;
            this.bp = index;
            if (index >= this.len) {
                throw new JSONException("syntax error, " + info());
            }
            this.ch = this.text.charAt(index);
        } else {
            negative = false;
            limit = -9223372036854775807L;
        }
        long longValue = 0;
        while (this.ch >= '0' && this.ch <= '9') {
            int digit = this.ch - '0';
            if (longValue < -922337203685477580L) {
                throw new JSONException("error long value, " + longValue + ", " + info());
            }
            long longValue2 = longValue * 10;
            if (longValue2 < ((long) digit) + limit) {
                throw new JSONException("error long value, " + longValue2 + ", " + info());
            }
            longValue = longValue2 - ((long) digit);
            this.np++;
            int index2 = this.bp + 1;
            this.bp = index2;
            if (index2 >= this.len) {
                charAt = 26;
            } else {
                charAt = this.text.charAt(index2);
            }
            this.ch = charAt;
        }
        if (!negative) {
            return -longValue;
        }
        return longValue;
    }

    public final long longValue() throws NumberFormatException {
        long limit;
        int i;
        int i2;
        char chLocal;
        long result = 0;
        boolean negative = false;
        int i3 = this.np;
        int max = this.np + this.sp;
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i = i3 + 1;
        } else {
            limit = -9223372036854775807L;
            i = i3;
        }
        if (i < max) {
            result = (long) (-(charAt(i) - '0'));
            i++;
        }
        while (true) {
            if (i >= max) {
                i2 = i;
                break;
            }
            i2 = i + 1;
            int index = i;
            if (index >= this.len) {
                chLocal = 26;
            } else {
                chLocal = this.text.charAt(index);
            }
            if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B') {
                break;
            }
            int digit = chLocal - '0';
            if (result < -922337203685477580L) {
                throw new NumberFormatException(numberString());
            }
            long result2 = result * 10;
            if (result2 < ((long) digit) + limit) {
                throw new NumberFormatException(numberString());
            }
            result = result2 - ((long) digit);
            i = i2;
        }
        if (!negative) {
            return -result;
        }
        if (i2 > this.np + 1) {
            return result;
        }
        throw new NumberFormatException(numberString());
    }

    public final Number decimalValue(boolean decimal) {
        char chLocal;
        char[] chars;
        int i;
        int charIndex = (this.np + this.sp) - 1;
        if (charIndex >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex);
        }
        if (chLocal == 'F') {
            try {
                return Float.valueOf(Float.parseFloat(numberString()));
            } catch (NumberFormatException ex) {
                throw new JSONException(ex.getMessage() + ", " + info());
            }
        } else if (chLocal == 'D') {
            return Double.valueOf(Double.parseDouble(numberString()));
        } else {
            if (decimal) {
                return decimalValue();
            }
            char chLocal2 = this.text.charAt((this.np + this.sp) - 1);
            int sp2 = this.sp;
            if (chLocal2 == 'L' || chLocal2 == 'S' || chLocal2 == 'B' || chLocal2 == 'F' || chLocal2 == 'D') {
                sp2--;
            }
            int offset = this.np;
            int count = sp2;
            if (count < this.sbuf.length) {
                this.text.getChars(offset, offset + count, this.sbuf, 0);
                chars = this.sbuf;
            } else {
                chars = new char[count];
                this.text.getChars(offset, offset + count, chars, 0);
            }
            if (count > 9 || this.exp) {
                String str = new String(chars, 0, count);
                return Double.valueOf(Double.parseDouble(str));
            }
            boolean negative = false;
            int i2 = 0 + 1;
            char c = chars[0];
            if (c == '-') {
                negative = true;
                i = i2 + 1;
                c = chars[i2];
            } else if (c == '+') {
                i = i2 + 1;
                c = chars[i2];
            } else {
                i = i2;
            }
            int intVal = c - '0';
            int power = 0;
            while (i < count) {
                char c2 = chars[i];
                if (c2 == '.') {
                    power = 1;
                } else {
                    intVal = (intVal * 10) + (c2 - '0');
                    if (power != 0) {
                        power *= 10;
                    }
                }
                i++;
            }
            double doubleVal = ((double) intVal) / ((double) power);
            if (negative) {
                doubleVal = -doubleVal;
            }
            return Double.valueOf(doubleVal);
        }
    }

    public final BigDecimal decimalValue() {
        char chLocal = this.text.charAt((this.np + this.sp) - 1);
        int sp2 = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp2--;
        }
        int offset = this.np;
        int count = sp2;
        if (count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, count);
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return new BigDecimal(chars);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0027, code lost:
        if (r6 == r14) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0029, code lost:
        r13.matchStat = -2;
        r13.fieldHash = r6;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0070, code lost:
        r6 = (r6 ^ ((long) r0)) * 1099511628211L;
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x007b, code lost:
        r8 = r9 + 1;
        r2 = r13.bp + r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0083, code lost:
        if (r2 < r13.len) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0085, code lost:
        r1 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0088, code lost:
        r9 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008a, code lost:
        if (r1 != ':') goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r6 = -3750763034362895579L;
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008c, code lost:
        r8 = r9 + 1;
        r2 = r13.bp + r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0094, code lost:
        if (r2 < r13.len) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0096, code lost:
        r1 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x009a, code lost:
        if (r1 != '{') goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009c, code lost:
        r13.bp = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a4, code lost:
        if (r13.bp < r13.len) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a6, code lost:
        r10 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a8, code lost:
        r13.ch = r10;
        r13.token = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b0, code lost:
        r1 = r13.text.charAt(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b7, code lost:
        r1 = r13.text.charAt(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c0, code lost:
        if (r1 > ' ') goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00c4, code lost:
        if (r1 == ' ') goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c8, code lost:
        if (r1 == 10) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00cc, code lost:
        if (r1 == 13) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0014, code lost:
        if (r5 >= r13.len) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00d0, code lost:
        if (r1 == 9) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00d4, code lost:
        if (r1 == 12) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00d8, code lost:
        if (r1 != 8) goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00da, code lost:
        r8 = r9 + 1;
        r2 = r13.bp + r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00e2, code lost:
        if (r2 < r13.len) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00e4, code lost:
        r1 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
        r0 = r13.text.charAt(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00e8, code lost:
        r1 = r13.text.charAt(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00f7, code lost:
        throw new com.alibaba.json.JSONException("match feild error expect ':'");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00f8, code lost:
        r10 = r13.text.charAt(r13.bp);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0103, code lost:
        if (r1 != '[') goto L_0x0121;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0105, code lost:
        r13.bp = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x010d, code lost:
        if (r13.bp < r13.len) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x010f, code lost:
        r10 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0111, code lost:
        r13.ch = r10;
        r13.token = 14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r0 != r3) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0118, code lost:
        r10 = r13.text.charAt(r13.bp);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0121, code lost:
        r13.bp = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0127, code lost:
        if (r13.bp < r13.len) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0129, code lost:
        r10 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x012b, code lost:
        r13.ch = r10;
        nextToken();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0132, code lost:
        r10 = r13.text.charAt(r13.bp);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        r9 = r9 + ((r5 - r4) + 1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean matchField(long r14) {
        /*
            r13 = this;
            r8 = 1
            char r3 = r13.ch
            int r10 = r13.bp
            int r4 = r10 + 1
            r9 = r8
        L_0x0008:
            r10 = 34
            if (r3 != r10) goto L_0x0031
        L_0x000c:
            r6 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            r5 = r4
        L_0x0012:
            int r10 = r13.len
            if (r5 >= r10) goto L_0x0025
            java.lang.String r10 = r13.text
            char r0 = r10.charAt(r5)
            if (r0 != r3) goto L_0x0070
            int r10 = r5 - r4
            int r10 = r10 + 1
            int r8 = r9 + r10
            r9 = r8
        L_0x0025:
            int r10 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r10 == 0) goto L_0x007b
            r10 = -2
            r13.matchStat = r10
            r13.fieldHash = r6
            r10 = 0
            r8 = r9
        L_0x0030:
            return r10
        L_0x0031:
            r10 = 39
            if (r3 == r10) goto L_0x000c
            r10 = 32
            if (r3 > r10) goto L_0x0066
            r10 = 32
            if (r3 == r10) goto L_0x0051
            r10 = 10
            if (r3 == r10) goto L_0x0051
            r10 = 13
            if (r3 == r10) goto L_0x0051
            r10 = 9
            if (r3 == r10) goto L_0x0051
            r10 = 12
            if (r3 == r10) goto L_0x0051
            r10 = 8
            if (r3 != r10) goto L_0x0066
        L_0x0051:
            int r10 = r13.bp
            int r8 = r9 + 1
            int r2 = r10 + r9
            int r10 = r13.len
            if (r2 < r10) goto L_0x005f
            r3 = 26
        L_0x005d:
            r9 = r8
            goto L_0x0008
        L_0x005f:
            java.lang.String r10 = r13.text
            char r3 = r10.charAt(r2)
            goto L_0x005d
        L_0x0066:
            r10 = 0
            r13.fieldHash = r10
            r10 = -2
            r13.matchStat = r10
            r10 = 0
            r8 = r9
            goto L_0x0030
        L_0x0070:
            long r10 = (long) r0
            long r6 = r6 ^ r10
            r10 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r6 = r6 * r10
            int r5 = r5 + 1
            goto L_0x0012
        L_0x007b:
            int r10 = r13.bp
            int r8 = r9 + 1
            int r2 = r10 + r9
            int r10 = r13.len
            if (r2 < r10) goto L_0x00b0
            r1 = 26
        L_0x0087:
            r9 = r8
        L_0x0088:
            r10 = 58
            if (r1 != r10) goto L_0x00be
            int r10 = r13.bp
            int r8 = r9 + 1
            int r2 = r10 + r9
            int r10 = r13.len
            if (r2 < r10) goto L_0x00b7
            r1 = 26
        L_0x0098:
            r10 = 123(0x7b, float:1.72E-43)
            if (r1 != r10) goto L_0x0101
            int r10 = r2 + 1
            r13.bp = r10
            int r10 = r13.bp
            int r11 = r13.len
            if (r10 < r11) goto L_0x00f8
            r10 = 26
        L_0x00a8:
            r13.ch = r10
            r10 = 12
            r13.token = r10
        L_0x00ae:
            r10 = 1
            goto L_0x0030
        L_0x00b0:
            java.lang.String r10 = r13.text
            char r1 = r10.charAt(r2)
            goto L_0x0087
        L_0x00b7:
            java.lang.String r10 = r13.text
            char r1 = r10.charAt(r2)
            goto L_0x0098
        L_0x00be:
            r10 = 32
            if (r1 > r10) goto L_0x00ef
            r10 = 32
            if (r1 == r10) goto L_0x00da
            r10 = 10
            if (r1 == r10) goto L_0x00da
            r10 = 13
            if (r1 == r10) goto L_0x00da
            r10 = 9
            if (r1 == r10) goto L_0x00da
            r10 = 12
            if (r1 == r10) goto L_0x00da
            r10 = 8
            if (r1 != r10) goto L_0x00ef
        L_0x00da:
            int r10 = r13.bp
            int r8 = r9 + 1
            int r2 = r10 + r9
            int r10 = r13.len
            if (r2 < r10) goto L_0x00e8
            r1 = 26
        L_0x00e6:
            r9 = r8
            goto L_0x0088
        L_0x00e8:
            java.lang.String r10 = r13.text
            char r1 = r10.charAt(r2)
            goto L_0x00e6
        L_0x00ef:
            com.alibaba.json.JSONException r10 = new com.alibaba.json.JSONException
            java.lang.String r11 = "match feild error expect ':'"
            r10.<init>(r11)
            throw r10
        L_0x00f8:
            java.lang.String r10 = r13.text
            int r11 = r13.bp
            char r10 = r10.charAt(r11)
            goto L_0x00a8
        L_0x0101:
            r10 = 91
            if (r1 != r10) goto L_0x0121
            int r10 = r2 + 1
            r13.bp = r10
            int r10 = r13.bp
            int r11 = r13.len
            if (r10 < r11) goto L_0x0118
            r10 = 26
        L_0x0111:
            r13.ch = r10
            r10 = 14
            r13.token = r10
            goto L_0x00ae
        L_0x0118:
            java.lang.String r10 = r13.text
            int r11 = r13.bp
            char r10 = r10.charAt(r11)
            goto L_0x0111
        L_0x0121:
            r13.bp = r2
            int r10 = r13.bp
            int r11 = r13.len
            if (r10 < r11) goto L_0x0132
            r10 = 26
        L_0x012b:
            r13.ch = r10
            r13.nextToken()
            goto L_0x00ae
        L_0x0132:
            java.lang.String r10 = r13.text
            int r11 = r13.bp
            char r10 = r10.charAt(r11)
            goto L_0x012b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.matchField(long):boolean");
    }

    private int matchFieldHash(long fieldHashCode) {
        int offset;
        int offset2;
        char chLocal;
        char chLocal2;
        int offset3 = 1;
        char fieldQuote = this.ch;
        int i = this.bp + 1;
        while (true) {
            offset = offset3;
            if (!(fieldQuote == '\"' || fieldQuote == '\'')) {
                if (fieldQuote == ' ' || fieldQuote == 10 || fieldQuote == 13 || fieldQuote == 9 || fieldQuote == 12 || fieldQuote == 8) {
                    offset3 = offset + 1;
                    int charIndex = this.bp + offset;
                    if (charIndex >= this.len) {
                        fieldQuote = 26;
                    } else {
                        fieldQuote = this.text.charAt(charIndex);
                    }
                } else {
                    this.fieldHash = 0;
                    this.matchStat = -2;
                    int i2 = offset;
                    return 0;
                }
            }
        }
        long hash = -3750763034362895579L;
        int i3 = this.bp + offset;
        while (true) {
            if (i3 >= this.len) {
                offset2 = offset;
                break;
            }
            char ch2 = this.text.charAt(i3);
            if (ch2 == fieldQuote) {
                offset2 = offset + ((i3 - this.bp) - offset);
                break;
            }
            hash = (hash ^ ((long) ch2)) * 1099511628211L;
            i3++;
        }
        if (hash != fieldHashCode) {
            this.fieldHash = hash;
            this.matchStat = -2;
            return 0;
        }
        int offset4 = offset2 + 1;
        int charIndex2 = this.bp + offset4;
        if (charIndex2 >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex2);
        }
        while (true) {
            int offset5 = offset4;
            if (chLocal == ':') {
                return offset5 + 1;
            }
            if (chLocal <= ' ' && (chLocal == ' ' || chLocal == 10 || chLocal == 13 || chLocal == 9 || chLocal == 12 || chLocal == 8)) {
                offset4 = offset5 + 1;
                int charIndex3 = this.bp + offset5;
                if (charIndex3 >= this.len) {
                    chLocal2 = 26;
                } else {
                    chLocal2 = this.text.charAt(charIndex3);
                }
            }
        }
        throw new JSONException("match feild error expect ':'");
    }

    public int scanFieldInt(long fieldHashCode) {
        char chLocal;
        char chLocal2;
        int offset;
        int offset2;
        char chLocal3;
        int offset3;
        char chLocal4;
        int offset4;
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        this.matchStat = 0;
        int offset5 = matchFieldHash(fieldHashCode);
        if (offset5 == 0) {
            return 0;
        }
        int offset6 = offset5 + 1;
        int charIndex = this.bp + offset5;
        if (charIndex >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex);
        }
        boolean quote = chLocal2 == '\"';
        if (quote) {
            quote = true;
            int offset7 = offset6 + 1;
            int charIndex2 = this.bp + offset6;
            if (charIndex2 >= this.len) {
                chLocal2 = 26;
            } else {
                chLocal2 = this.text.charAt(charIndex2);
            }
            offset6 = offset7;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset6 + 1;
            int charIndex3 = this.bp + offset6;
            if (charIndex3 >= this.len) {
                chLocal2 = 26;
            } else {
                chLocal2 = this.text.charAt(charIndex3);
            }
        } else {
            offset = offset6;
        }
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int value = chLocal2 - '0';
        while (true) {
            offset2 = offset + 1;
            int charIndex4 = this.bp + offset;
            if (charIndex4 >= this.len) {
                chLocal3 = 26;
            } else {
                chLocal3 = this.text.charAt(charIndex4);
            }
            if (chLocal3 >= '0' && chLocal3 <= '9') {
                value = (value * 10) + (chLocal3 - '0');
                offset = offset2;
            }
        }
        if (chLocal3 == '.') {
            this.matchStat = -1;
            int i = offset2;
            return 0;
        }
        if (chLocal3 != '\"') {
            offset3 = offset2;
        } else if (!quote) {
            this.matchStat = -1;
            int i2 = offset2;
            return 0;
        } else {
            offset3 = offset2 + 1;
            int index = this.bp + offset2;
            if (index >= this.len) {
                chLocal3 = 26;
            } else {
                chLocal3 = this.text.charAt(index);
            }
        }
        if (value < 0) {
            this.matchStat = -1;
            return 0;
        }
        while (true) {
            offset4 = offset3;
            if (chLocal4 == ',') {
                this.bp += offset4 - 1;
                int index2 = this.bp + 1;
                this.bp = index2;
                if (index2 >= this.len) {
                    charAt = 26;
                } else {
                    charAt = this.text.charAt(index2);
                }
                this.ch = charAt;
                this.matchStat = 3;
                this.token = 16;
                if (negative) {
                    value = -value;
                }
                int i3 = offset4;
                return value;
            } else if (chLocal4 <= ' ' && (chLocal4 == ' ' || chLocal4 == 10 || chLocal4 == 13 || chLocal4 == 9 || chLocal4 == 12 || chLocal4 == 8)) {
                offset3 = offset4 + 1;
                int charIndex5 = this.bp + offset4;
                if (charIndex5 >= this.len) {
                    chLocal4 = 26;
                } else {
                    chLocal4 = this.text.charAt(charIndex5);
                }
            }
        }
        if (chLocal4 == '}') {
            int offset8 = offset4 + 1;
            char chLocal5 = charAt(this.bp + offset4);
            if (chLocal5 == ',') {
                this.token = 16;
                this.bp += offset8 - 1;
                int index3 = this.bp + 1;
                this.bp = index3;
                if (index3 >= this.len) {
                    charAt4 = 26;
                } else {
                    charAt4 = this.text.charAt(index3);
                }
                this.ch = charAt4;
            } else if (chLocal5 == ']') {
                this.token = 15;
                this.bp += offset8 - 1;
                int index4 = this.bp + 1;
                this.bp = index4;
                if (index4 >= this.len) {
                    charAt3 = 26;
                } else {
                    charAt3 = this.text.charAt(index4);
                }
                this.ch = charAt3;
            } else if (chLocal5 == '}') {
                this.token = 13;
                this.bp += offset8 - 1;
                int index5 = this.bp + 1;
                this.bp = index5;
                if (index5 >= this.len) {
                    charAt2 = 26;
                } else {
                    charAt2 = this.text.charAt(index5);
                }
                this.ch = charAt2;
            } else if (chLocal5 == 26) {
                this.token = 20;
                this.bp += offset8 - 1;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            if (negative) {
                return -value;
            }
            return value;
        }
        this.matchStat = -1;
        int i4 = offset4;
        return 0;
    }

    public final int[] scanFieldIntArray(long fieldHashCode) {
        char chLocal;
        char chLocal2;
        char chLocal3;
        char chLocal4;
        int offset;
        int offset2;
        int offset3;
        char chLocal5;
        this.matchStat = 0;
        int offset4 = matchFieldHash(fieldHashCode);
        if (offset4 == 0) {
            return null;
        }
        int offset5 = offset4 + 1;
        int charIndex = this.bp + offset4;
        if (charIndex >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex);
        }
        if (chLocal != '[') {
            this.matchStat = -1;
            int i = offset5;
            return null;
        }
        int offset6 = offset5 + 1;
        int charIndex2 = this.bp + offset5;
        if (charIndex2 >= this.len) {
            chLocal2 = 26;
        } else {
            chLocal2 = this.text.charAt(charIndex2);
        }
        int[] array = new int[16];
        int arrayIndex = 0;
        if (chLocal3 == ']') {
            int offset7 = offset6 + 1;
            int charIndex3 = this.bp + offset6;
            if (charIndex3 >= this.len) {
                chLocal5 = 26;
            } else {
                chLocal5 = this.text.charAt(charIndex3);
            }
            offset3 = offset7;
        } else {
            while (true) {
                int arrayIndex2 = arrayIndex;
                int offset8 = offset6;
                boolean nagative = false;
                if (chLocal4 == '-') {
                    offset = offset8 + 1;
                    int charIndex4 = this.bp + offset8;
                    if (charIndex4 >= this.len) {
                        chLocal4 = 26;
                    } else {
                        chLocal4 = this.text.charAt(charIndex4);
                    }
                    nagative = true;
                } else {
                    offset = offset8;
                }
                if (chLocal4 < '0' || chLocal4 > '9') {
                    this.matchStat = -1;
                } else {
                    int value = chLocal4 - '0';
                    while (true) {
                        offset2 = offset + 1;
                        int charIndex5 = this.bp + offset;
                        if (charIndex5 >= this.len) {
                            chLocal3 = 26;
                        } else {
                            chLocal3 = this.text.charAt(charIndex5);
                        }
                        if (chLocal3 >= '0' && chLocal3 <= '9') {
                            value = (value * 10) + (chLocal3 - '0');
                            offset = offset2;
                        }
                    }
                    if (arrayIndex2 >= array.length) {
                        int[] tmp = new int[((array.length * 3) / 2)];
                        System.arraycopy(array, 0, tmp, 0, arrayIndex2);
                        array = tmp;
                    }
                    arrayIndex = arrayIndex2 + 1;
                    if (nagative) {
                        value = -value;
                    }
                    array[arrayIndex2] = value;
                    if (chLocal3 == ',') {
                        offset6 = offset2 + 1;
                        int charIndex6 = this.bp + offset2;
                        if (charIndex6 >= this.len) {
                            chLocal3 = 26;
                        } else {
                            chLocal3 = this.text.charAt(charIndex6);
                        }
                    } else if (chLocal3 == ']') {
                        offset3 = offset2 + 1;
                        int charIndex7 = this.bp + offset2;
                        if (charIndex7 >= this.len) {
                            chLocal5 = 26;
                        } else {
                            chLocal5 = this.text.charAt(charIndex7);
                        }
                    } else {
                        offset6 = offset2;
                    }
                }
            }
            this.matchStat = -1;
            return null;
        }
        if (arrayIndex != array.length) {
            int[] tmp2 = new int[arrayIndex];
            System.arraycopy(array, 0, tmp2, 0, arrayIndex);
            array = tmp2;
        }
        if (chLocal5 == ',') {
            this.bp += offset3 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return array;
        } else if (chLocal5 == '}') {
            int offset9 = offset3 + 1;
            char chLocal6 = charAt(this.bp + offset3);
            if (chLocal6 == ',') {
                this.token = 16;
                this.bp += offset9 - 1;
                next();
            } else if (chLocal6 == ']') {
                this.token = 15;
                this.bp += offset9 - 1;
                next();
            } else if (chLocal6 == '}') {
                this.token = 13;
                this.bp += offset9 - 1;
                next();
            } else if (chLocal6 == 26) {
                this.bp += offset9 - 1;
                this.token = 20;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                int i2 = offset9;
                return null;
            }
            this.matchStat = 4;
            int i3 = offset9;
            return array;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public long scanFieldLong(long fieldHashCode) {
        char chLocal;
        char chLocal2;
        int offset;
        int offset2;
        char chLocal3;
        char chLocal4;
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        this.matchStat = 0;
        int offset3 = matchFieldHash(fieldHashCode);
        if (offset3 == 0) {
            return 0;
        }
        int offset4 = offset3 + 1;
        int index = this.bp + offset3;
        if (index >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(index);
        }
        boolean quote = chLocal2 == '\"';
        if (quote) {
            int offset5 = offset4 + 1;
            int index2 = this.bp + offset4;
            if (index2 >= this.len) {
                chLocal2 = 26;
            } else {
                chLocal2 = this.text.charAt(index2);
            }
            offset4 = offset5;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            int index3 = this.bp + offset4;
            if (index3 >= this.len) {
                chLocal2 = 26;
            } else {
                chLocal2 = this.text.charAt(index3);
            }
        } else {
            offset = offset4;
        }
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        long value = (long) (chLocal2 - '0');
        while (true) {
            offset2 = offset + 1;
            int index4 = this.bp + offset;
            if (index4 >= this.len) {
                chLocal3 = 26;
            } else {
                chLocal3 = this.text.charAt(index4);
            }
            if (chLocal4 >= '0' && chLocal4 <= '9') {
                value = (10 * value) + ((long) (chLocal4 - '0'));
                offset = offset2;
            }
        }
        if (chLocal4 == '.') {
            this.matchStat = -1;
            int i = offset2;
            return 0;
        }
        if (chLocal4 == '\"') {
            if (!quote) {
                this.matchStat = -1;
                int i2 = offset2;
                return 0;
            }
            int offset6 = offset2 + 1;
            int index5 = this.bp + offset2;
            if (index5 >= this.len) {
                chLocal4 = 26;
            } else {
                chLocal4 = this.text.charAt(index5);
            }
            offset2 = offset6;
        }
        if (value < 0) {
            this.matchStat = -1;
            int i3 = offset2;
            return 0;
        } else if (chLocal4 == ',') {
            this.bp += offset2 - 1;
            int index6 = this.bp + 1;
            this.bp = index6;
            if (index6 >= this.len) {
                charAt4 = 26;
            } else {
                charAt4 = this.text.charAt(index6);
            }
            this.ch = charAt4;
            this.matchStat = 3;
            this.token = 16;
            if (negative) {
                value = -value;
            }
            int i4 = offset2;
            return value;
        } else if (chLocal4 == '}') {
            int offset7 = offset2 + 1;
            char chLocal5 = charAt(this.bp + offset2);
            if (chLocal5 == ',') {
                this.token = 16;
                this.bp += offset7 - 1;
                int index7 = this.bp + 1;
                this.bp = index7;
                if (index7 >= this.len) {
                    charAt3 = 26;
                } else {
                    charAt3 = this.text.charAt(index7);
                }
                this.ch = charAt3;
            } else if (chLocal5 == ']') {
                this.token = 15;
                this.bp += offset7 - 1;
                int index8 = this.bp + 1;
                this.bp = index8;
                if (index8 >= this.len) {
                    charAt2 = 26;
                } else {
                    charAt2 = this.text.charAt(index8);
                }
                this.ch = charAt2;
            } else if (chLocal5 == '}') {
                this.token = 13;
                this.bp += offset7 - 1;
                int index9 = this.bp + 1;
                this.bp = index9;
                if (index9 >= this.len) {
                    charAt = 26;
                } else {
                    charAt = this.text.charAt(index9);
                }
                this.ch = charAt;
            } else if (chLocal5 == 26) {
                this.token = 20;
                this.bp += offset7 - 1;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            if (negative) {
                return -value;
            }
            return value;
        } else {
            this.matchStat = -1;
            int i5 = offset2;
            return 0;
        }
    }

    public String scanFieldString(long fieldHashCode) {
        String strVal;
        char chLocal;
        char chLocal2;
        char charAt;
        this.matchStat = 0;
        int offset = matchFieldHash(fieldHashCode);
        if (offset == 0) {
            return null;
        }
        int offset2 = offset + 1;
        int index = this.bp + offset;
        if (index >= this.len) {
            throw new JSONException("unclosed str, " + info());
        } else if (this.text.charAt(index) != '\"') {
            this.matchStat = -1;
            int i = offset2;
            return this.stringDefaultValue;
        } else {
            boolean hasSpecial2 = false;
            int startIndex = this.bp + offset2;
            int endIndex = this.text.indexOf(34, startIndex);
            if (endIndex == -1) {
                throw new JSONException("unclosed str, " + info());
            }
            if (V6) {
                strVal = this.text.substring(startIndex, endIndex);
            } else {
                int chars_len = endIndex - startIndex;
                strVal = new String(sub_chars(this.bp + offset2, chars_len), 0, chars_len);
            }
            if (strVal.indexOf(92) != -1) {
                while (true) {
                    int slashCount = 0;
                    int i2 = endIndex - 1;
                    while (i2 >= 0 && this.text.charAt(i2) == '\\') {
                        hasSpecial2 = true;
                        slashCount++;
                        i2--;
                    }
                    if (slashCount % 2 == 0) {
                        break;
                    }
                    endIndex = this.text.indexOf(34, endIndex + 1);
                }
                int chars_len2 = endIndex - startIndex;
                char[] chars = sub_chars(this.bp + offset2, chars_len2);
                if (hasSpecial2) {
                    strVal = readString(chars, chars_len2);
                } else {
                    strVal = new String(chars, 0, chars_len2);
                    if (strVal.indexOf(92) != -1) {
                        strVal = readString(chars, chars_len2);
                    }
                }
            }
            int endIndex2 = endIndex + 1;
            int index2 = endIndex2;
            if (index2 >= this.len) {
                chLocal = 26;
            } else {
                chLocal = this.text.charAt(index2);
            }
            if (chLocal == ',') {
                this.bp = endIndex2;
                int index3 = this.bp + 1;
                this.bp = index3;
                if (index3 >= this.len) {
                    charAt = 26;
                } else {
                    charAt = this.text.charAt(index3);
                }
                this.ch = charAt;
                this.matchStat = 3;
                this.token = 16;
                int i3 = offset2;
                return strVal;
            } else if (chLocal == '}') {
                int endIndex3 = endIndex2 + 1;
                int charIndex = endIndex3;
                if (charIndex >= this.len) {
                    chLocal2 = 26;
                } else {
                    chLocal2 = this.text.charAt(charIndex);
                }
                if (chLocal2 == ',') {
                    this.token = 16;
                    this.bp = endIndex3;
                    next();
                } else if (chLocal2 == ']') {
                    this.token = 15;
                    this.bp = endIndex3;
                    next();
                } else if (chLocal2 == '}') {
                    this.token = 13;
                    this.bp = endIndex3;
                    next();
                } else if (chLocal2 == 26) {
                    this.token = 20;
                    this.bp = endIndex3;
                    this.ch = 26;
                } else {
                    this.matchStat = -1;
                    int i4 = offset2;
                    return this.stringDefaultValue;
                }
                this.matchStat = 4;
                int i5 = offset2;
                return strVal;
            } else {
                this.matchStat = -1;
                int i6 = offset2;
                return this.stringDefaultValue;
            }
        }
    }

    public Date scanFieldDate(long fieldHashCode) {
        char chLocal;
        char chLocal2;
        int offset;
        Date dateVal;
        int offset2;
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        this.matchStat = 0;
        int offset3 = matchFieldHash(fieldHashCode);
        if (offset3 == 0) {
            return null;
        }
        int startPos = this.bp;
        char c = this.ch;
        int offset4 = offset3 + 1;
        int index = this.bp + offset3;
        if (index >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(index);
        }
        if (chLocal == '\"') {
            int startIndex = this.bp + offset4;
            int offset5 = offset4 + 1;
            int index2 = this.bp + offset4;
            if (index2 < this.len) {
                char chLocal3 = this.text.charAt(index2);
            }
            int endIndex = this.text.indexOf(34, this.bp + offset5);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int rest = endIndex - startIndex;
            this.bp = startIndex;
            if (scanISO8601DateIfMatch(false, rest)) {
                dateVal = this.calendar.getTime();
                int offset6 = offset5 + rest;
                offset2 = offset6 + 1;
                chLocal2 = charAt(startPos + offset6);
                this.bp = startPos;
            } else {
                this.bp = startPos;
                this.matchStat = -1;
                return null;
            }
        } else if (chLocal < '0' || chLocal > '9') {
            this.matchStat = -1;
            int i = offset4;
            return null;
        } else {
            long millis = (long) (chLocal - '0');
            while (true) {
                int offset7 = offset4;
                offset4 = offset7 + 1;
                int index3 = this.bp + offset7;
                if (index3 >= this.len) {
                    chLocal2 = 26;
                } else {
                    chLocal2 = this.text.charAt(index3);
                }
                if (chLocal2 >= '0' && chLocal2 <= '9') {
                    millis = (10 * millis) + ((long) (chLocal2 - '0'));
                }
            }
            if (chLocal2 == '.') {
                this.matchStat = -1;
                int i2 = offset4;
                return null;
            }
            if (chLocal2 == '\"') {
                offset = offset4 + 1;
                int index4 = this.bp + offset4;
                if (index4 >= this.len) {
                    chLocal2 = 26;
                } else {
                    chLocal2 = this.text.charAt(index4);
                }
            } else {
                offset = offset4;
            }
            if (millis < 0) {
                this.matchStat = -1;
                return null;
            }
            dateVal = new Date(millis);
            offset2 = offset;
        }
        if (chLocal2 == ',') {
            this.bp += offset2 - 1;
            int index5 = this.bp + 1;
            this.bp = index5;
            if (index5 >= this.len) {
                charAt4 = 26;
            } else {
                charAt4 = this.text.charAt(index5);
            }
            this.ch = charAt4;
            this.matchStat = 3;
            this.token = 16;
            int i3 = offset2;
            return dateVal;
        } else if (chLocal2 == '}') {
            int offset8 = offset2 + 1;
            char chLocal4 = charAt(this.bp + offset2);
            if (chLocal4 == ',') {
                this.token = 16;
                this.bp += offset8 - 1;
                int index6 = this.bp + 1;
                this.bp = index6;
                if (index6 >= this.len) {
                    charAt3 = 26;
                } else {
                    charAt3 = this.text.charAt(index6);
                }
                this.ch = charAt3;
            } else if (chLocal4 == ']') {
                this.token = 15;
                this.bp += offset8 - 1;
                int index7 = this.bp + 1;
                this.bp = index7;
                if (index7 >= this.len) {
                    charAt2 = 26;
                } else {
                    charAt2 = this.text.charAt(index7);
                }
                this.ch = charAt2;
            } else if (chLocal4 == '}') {
                this.token = 13;
                this.bp += offset8 - 1;
                int index8 = this.bp + 1;
                this.bp = index8;
                if (index8 >= this.len) {
                    charAt = 26;
                } else {
                    charAt = this.text.charAt(index8);
                }
                this.ch = charAt;
            } else if (chLocal4 == 26) {
                this.token = 20;
                this.bp += offset8 - 1;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return dateVal;
        } else {
            this.matchStat = -1;
            int i4 = offset2;
            return null;
        }
    }

    public boolean scanFieldBoolean(long fieldHashCode) {
        int offset;
        boolean value;
        char chLocal;
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        char chLocal2;
        this.matchStat = 0;
        int offset2 = matchFieldHash(fieldHashCode);
        if (offset2 == 0) {
            return false;
        }
        if (this.text.startsWith("false", this.bp + offset2)) {
            offset = offset2 + 5;
            value = false;
        } else if (this.text.startsWith("true", this.bp + offset2)) {
            offset = offset2 + 4;
            value = true;
        } else if (this.text.startsWith("\"false\"", this.bp + offset2)) {
            offset = offset2 + 7;
            value = false;
        } else if (this.text.startsWith("\"true\"", this.bp + offset2)) {
            offset = offset2 + 6;
            value = true;
        } else if (this.text.charAt(this.bp + offset2) == '1') {
            offset = offset2 + 1;
            value = true;
        } else if (this.text.charAt(this.bp + offset2) == '0') {
            offset = offset2 + 1;
            value = false;
        } else if (this.text.startsWith("\"1\"", this.bp + offset2)) {
            offset = offset2 + 3;
            value = true;
        } else if (this.text.startsWith("\"0\"", this.bp + offset2)) {
            offset = offset2 + 3;
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        int offset3 = offset + 1;
        int charIndex = this.bp + offset;
        if (charIndex >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex);
        }
        while (chLocal != ',') {
            if (chLocal != '}' && (chLocal == ' ' || chLocal == 10 || chLocal == 13 || chLocal == 9 || chLocal == 12 || chLocal == 8)) {
                int offset4 = offset3 + 1;
                int charIndex2 = this.bp + offset3;
                if (charIndex2 >= this.len) {
                    chLocal2 = 26;
                } else {
                    chLocal2 = this.text.charAt(charIndex2);
                }
                offset3 = offset4;
            } else if (chLocal == '}') {
                int offset5 = offset3 + 1;
                char chLocal3 = charAt(this.bp + offset3);
                if (chLocal3 == ',') {
                    this.token = 16;
                    this.bp += offset5 - 1;
                    int index = this.bp + 1;
                    this.bp = index;
                    if (index >= this.len) {
                        charAt4 = 26;
                    } else {
                        charAt4 = this.text.charAt(index);
                    }
                    this.ch = charAt4;
                } else if (chLocal3 == ']') {
                    this.token = 15;
                    this.bp += offset5 - 1;
                    int index2 = this.bp + 1;
                    this.bp = index2;
                    if (index2 >= this.len) {
                        charAt3 = 26;
                    } else {
                        charAt3 = this.text.charAt(index2);
                    }
                    this.ch = charAt3;
                } else if (chLocal3 == '}') {
                    this.token = 13;
                    this.bp += offset5 - 1;
                    int index3 = this.bp + 1;
                    this.bp = index3;
                    if (index3 >= this.len) {
                        charAt2 = 26;
                    } else {
                        charAt2 = this.text.charAt(index3);
                    }
                    this.ch = charAt2;
                } else if (chLocal3 == 26) {
                    this.token = 20;
                    this.bp += offset5 - 1;
                    this.ch = 26;
                } else {
                    this.matchStat = -1;
                    return false;
                }
                this.matchStat = 4;
                return value;
            } else {
                this.matchStat = -1;
                int i = offset3;
                return false;
            }
        }
        this.bp += offset3 - 1;
        int index4 = this.bp + 1;
        this.bp = index4;
        if (index4 >= this.len) {
            charAt = 26;
        } else {
            charAt = this.text.charAt(index4);
        }
        this.ch = charAt;
        this.matchStat = 3;
        this.token = 16;
        int i2 = offset3;
        return value;
    }

    public final float scanFieldFloat(long fieldHashCode) {
        int offset;
        int intVal;
        int offset2;
        char chLocal;
        int offset3;
        char chLocal2;
        float value;
        this.matchStat = 0;
        int offset4 = matchFieldHash(fieldHashCode);
        if (offset4 == 0) {
            return 0.0f;
        }
        int offset5 = offset4 + 1;
        char chLocal3 = charAt(this.bp + offset4);
        int start = (this.bp + offset5) - 1;
        boolean negative = chLocal3 == '-';
        if (negative) {
            offset = offset5 + 1;
            chLocal3 = charAt(this.bp + offset5);
        } else {
            offset = offset5;
        }
        if (chLocal3 < '0' || chLocal3 > '9') {
            this.matchStat = -1;
            return 0.0f;
        }
        int intVal2 = chLocal3 - '0';
        while (true) {
            offset2 = offset + 1;
            chLocal = charAt(this.bp + offset);
            if (chLocal < '0' || chLocal > '9') {
                int power = 1;
            } else {
                intVal2 = (intVal * 10) + (chLocal - '0');
                offset = offset2;
            }
        }
        int power2 = 1;
        if (chLocal == '.') {
            int offset6 = offset2 + 1;
            char chLocal4 = charAt(this.bp + offset2);
            if (chLocal4 >= '0' && chLocal4 <= '9') {
                intVal = (intVal * 10) + (chLocal4 - '0');
                power2 = 10;
                while (true) {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (intVal * 10) + (chLocal - '0');
                    power2 *= 10;
                    offset6 = offset2;
                }
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
        }
        boolean exp2 = chLocal2 == 'e' || chLocal2 == 'E';
        if (exp2) {
            int offset7 = offset3 + 1;
            chLocal2 = charAt(this.bp + offset3);
            if (chLocal2 == '+' || chLocal2 == '-') {
                offset3 = offset7 + 1;
                chLocal2 = charAt(this.bp + offset7);
            } else {
                offset3 = offset7;
            }
            while (chLocal2 >= '0' && chLocal2 <= '9') {
                int offset8 = offset3 + 1;
                chLocal2 = charAt(this.bp + offset3);
                offset3 = offset8;
            }
        }
        int offset9 = offset3;
        int count = ((this.bp + offset9) - start) - 1;
        if (exp2 || count >= 10) {
            value = Float.parseFloat(subString(start, count));
        } else {
            value = ((float) intVal) / ((float) power2);
            if (negative) {
                value = -value;
            }
        }
        if (chLocal2 == ',') {
            this.bp += offset9 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal2 == '}') {
            int offset10 = offset9 + 1;
            char chLocal5 = charAt(this.bp + offset9);
            if (chLocal5 == ',') {
                this.token = 16;
                this.bp += offset10 - 1;
                next();
            } else if (chLocal5 == ']') {
                this.token = 15;
                this.bp += offset10 - 1;
                next();
            } else if (chLocal5 == '}') {
                this.token = 13;
                this.bp += offset10 - 1;
                next();
            } else if (chLocal5 == 26) {
                this.bp += offset10 - 1;
                this.token = 20;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                int i = offset10;
                return 0.0f;
            }
            this.matchStat = 4;
            int i2 = offset10;
            return value;
        } else {
            this.matchStat = -1;
            return 0.0f;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float[] scanFieldFloatArray(long r24) {
        /*
            r23 = this;
            r20 = 0
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            int r12 = r23.matchFieldHash(r24)
            if (r12 != 0) goto L_0x0010
            r3 = 0
        L_0x000f:
            return r3
        L_0x0010:
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x0037
            r6 = 26
        L_0x0026:
            r20 = 91
            r0 = r20
            if (r6 == r0) goto L_0x0044
            r20 = -1
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            r3 = 0
            r12 = r13
            goto L_0x000f
        L_0x0037:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x0026
        L_0x0044:
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r12 = r13 + 1
            int r7 = r20 + r13
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x00c3
            r6 = 26
        L_0x005a:
            r20 = 16
            r0 = r20
            float[] r3 = new float[r0]
            r4 = 0
        L_0x0061:
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r20 = r20 + r12
            int r16 = r20 + -1
            r20 = 45
            r0 = r20
            if (r6 != r0) goto L_0x00d0
            r11 = 1
        L_0x0072:
            if (r11 == 0) goto L_0x008b
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x00d2
            r6 = 26
        L_0x008a:
            r12 = r13
        L_0x008b:
            r20 = 48
            r0 = r20
            if (r6 < r0) goto L_0x02fb
            r20 = 57
            r0 = r20
            if (r6 > r0) goto L_0x02fb
            int r10 = r6 + -48
        L_0x0099:
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x00df
            r6 = 26
        L_0x00af:
            r20 = 48
            r0 = r20
            if (r6 < r0) goto L_0x00ec
            r20 = 57
            r0 = r20
            if (r6 > r0) goto L_0x00ec
            int r20 = r10 * 10
            int r21 = r6 + -48
            int r10 = r20 + r21
            r12 = r13
            goto L_0x0099
        L_0x00c3:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x005a
        L_0x00d0:
            r11 = 0
            goto L_0x0072
        L_0x00d2:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x008a
        L_0x00df:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x00af
        L_0x00ec:
            r14 = 1
            r20 = 46
            r0 = r20
            if (r6 != r0) goto L_0x014c
            r15 = 1
        L_0x00f4:
            if (r15 == 0) goto L_0x0173
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r12 = r13 + 1
            int r7 = r20 + r13
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x014e
            r6 = 26
        L_0x010c:
            int r14 = r14 * 10
            r20 = 48
            r0 = r20
            if (r6 < r0) goto L_0x0168
            r20 = 57
            r0 = r20
            if (r6 > r0) goto L_0x0168
            int r20 = r10 * 10
            int r21 = r6 + -48
            int r10 = r20 + r21
        L_0x0120:
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x015b
            r6 = 26
        L_0x0136:
            r20 = 48
            r0 = r20
            if (r6 < r0) goto L_0x0173
            r20 = 57
            r0 = r20
            if (r6 > r0) goto L_0x0173
            int r20 = r10 * 10
            int r21 = r6 + -48
            int r10 = r20 + r21
            int r14 = r14 * 10
            r12 = r13
            goto L_0x0120
        L_0x014c:
            r15 = 0
            goto L_0x00f4
        L_0x014e:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x010c
        L_0x015b:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x0136
        L_0x0168:
            r20 = -1
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            r3 = 0
            goto L_0x000f
        L_0x0173:
            r20 = 101(0x65, float:1.42E-43)
            r0 = r20
            if (r6 == r0) goto L_0x017f
            r20 = 69
            r0 = r20
            if (r6 != r0) goto L_0x01de
        L_0x017f:
            r9 = 1
        L_0x0180:
            if (r9 == 0) goto L_0x0207
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r12 = r13 + 1
            int r7 = r20 + r13
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x01e0
            r6 = 26
        L_0x0198:
            r20 = 43
            r0 = r20
            if (r6 == r0) goto L_0x01a4
            r20 = 45
            r0 = r20
            if (r6 != r0) goto L_0x03dd
        L_0x01a4:
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x01ed
            r6 = 26
        L_0x01ba:
            r20 = 48
            r0 = r20
            if (r6 < r0) goto L_0x0207
            r20 = 57
            r0 = r20
            if (r6 > r0) goto L_0x0207
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r12 = r13 + 1
            int r7 = r20 + r13
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x01fa
            r6 = 26
        L_0x01dc:
            r13 = r12
            goto L_0x01ba
        L_0x01de:
            r9 = 0
            goto L_0x0180
        L_0x01e0:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x0198
        L_0x01ed:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x01ba
        L_0x01fa:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x01dc
        L_0x0207:
            r12 = r13
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r20 = r20 + r12
            int r20 = r20 - r16
            int r8 = r20 + -1
            if (r9 != 0) goto L_0x0272
            r20 = 10
            r0 = r20
            if (r8 >= r0) goto L_0x0272
            float r0 = (float) r10
            r20 = r0
            float r0 = (float) r14
            r21 = r0
            float r19 = r20 / r21
            if (r11 == 0) goto L_0x022b
            r0 = r19
            float r0 = -r0
            r19 = r0
        L_0x022b:
            int r0 = r3.length
            r20 = r0
            r0 = r20
            if (r4 < r0) goto L_0x024e
            int r0 = r3.length
            r20 = r0
            int r20 = r20 * 3
            int r20 = r20 / 2
            r0 = r20
            float[] r0 = new float[r0]
            r18 = r0
            r20 = 0
            r21 = 0
            r0 = r20
            r1 = r18
            r2 = r21
            java.lang.System.arraycopy(r3, r0, r1, r2, r4)
            r3 = r18
        L_0x024e:
            int r5 = r4 + 1
            r3[r4] = r19
            r20 = 44
            r0 = r20
            if (r6 != r0) goto L_0x028c
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x027f
            r6 = 26
        L_0x026e:
            r12 = r13
        L_0x026f:
            r4 = r5
            goto L_0x0061
        L_0x0272:
            r0 = r23
            r1 = r16
            java.lang.String r17 = r0.subString(r1, r8)
            float r19 = java.lang.Float.parseFloat(r17)
            goto L_0x022b
        L_0x027f:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x026e
        L_0x028c:
            r20 = 93
            r0 = r20
            if (r6 != r0) goto L_0x026f
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r13 = r12 + 1
            int r7 = r20 + r12
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x02ee
            r6 = 26
        L_0x02a8:
            int r0 = r3.length
            r20 = r0
            r0 = r20
            if (r5 == r0) goto L_0x02c2
            float[] r0 = new float[r5]
            r18 = r0
            r20 = 0
            r21 = 0
            r0 = r20
            r1 = r18
            r2 = r21
            java.lang.System.arraycopy(r3, r0, r1, r2, r5)
            r3 = r18
        L_0x02c2:
            r20 = 44
            r0 = r20
            if (r6 != r0) goto L_0x0306
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r21 = r13 + -1
            int r20 = r20 + r21
            r0 = r20
            r1 = r23
            r1.bp = r0
            r23.next()
            r20 = 3
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            r20 = 16
            r0 = r20
            r1 = r23
            r1.token = r0
            r12 = r13
            goto L_0x000f
        L_0x02ee:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x02a8
        L_0x02fb:
            r20 = -1
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            r3 = 0
            goto L_0x000f
        L_0x0306:
            r20 = 125(0x7d, float:1.75E-43)
            r0 = r20
            if (r6 != r0) goto L_0x03d1
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r12 = r13 + 1
            int r7 = r20 + r13
            r0 = r23
            int r0 = r0.len
            r20 = r0
            r0 = r20
            if (r7 < r0) goto L_0x034d
            r6 = 26
        L_0x0322:
            r20 = 44
            r0 = r20
            if (r6 != r0) goto L_0x035a
            r20 = 16
            r0 = r20
            r1 = r23
            r1.token = r0
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r21 = r12 + -1
            int r20 = r20 + r21
            r0 = r20
            r1 = r23
            r1.bp = r0
            r23.next()
        L_0x0343:
            r20 = 4
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            goto L_0x000f
        L_0x034d:
            r0 = r23
            java.lang.String r0 = r0.text
            r20 = r0
            r0 = r20
            char r6 = r0.charAt(r7)
            goto L_0x0322
        L_0x035a:
            r20 = 93
            r0 = r20
            if (r6 != r0) goto L_0x037c
            r20 = 15
            r0 = r20
            r1 = r23
            r1.token = r0
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r21 = r12 + -1
            int r20 = r20 + r21
            r0 = r20
            r1 = r23
            r1.bp = r0
            r23.next()
            goto L_0x0343
        L_0x037c:
            r20 = 125(0x7d, float:1.75E-43)
            r0 = r20
            if (r6 != r0) goto L_0x039e
            r20 = 13
            r0 = r20
            r1 = r23
            r1.token = r0
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r21 = r12 + -1
            int r20 = r20 + r21
            r0 = r20
            r1 = r23
            r1.bp = r0
            r23.next()
            goto L_0x0343
        L_0x039e:
            r20 = 26
            r0 = r20
            if (r6 != r0) goto L_0x03c6
            r0 = r23
            int r0 = r0.bp
            r20 = r0
            int r21 = r12 + -1
            int r20 = r20 + r21
            r0 = r20
            r1 = r23
            r1.bp = r0
            r20 = 20
            r0 = r20
            r1 = r23
            r1.token = r0
            r20 = 26
            r0 = r20
            r1 = r23
            r1.ch = r0
            goto L_0x0343
        L_0x03c6:
            r20 = -1
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            r3 = 0
            goto L_0x000f
        L_0x03d1:
            r20 = -1
            r0 = r20
            r1 = r23
            r1.matchStat = r0
            r3 = 0
            r12 = r13
            goto L_0x000f
        L_0x03dd:
            r13 = r12
            goto L_0x01ba
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.scanFieldFloatArray(long):float[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:187:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float[][] scanFieldFloatArray2(long r26) {
        /*
            r25 = this;
            r22 = 0
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            int r15 = r25.matchFieldHash(r26)
            if (r15 != 0) goto L_0x0013
            r22 = 0
            float[][] r22 = (float[][]) r22
        L_0x0012:
            return r22
        L_0x0013:
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x003e
            r9 = 26
        L_0x0029:
            r22 = 91
            r0 = r22
            if (r9 == r0) goto L_0x004b
            r22 = -1
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r22 = 0
            float[][] r22 = (float[][]) r22
            r15 = r16
            goto L_0x0012
        L_0x003e:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0029
        L_0x004b:
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x00f2
            r9 = 26
        L_0x0061:
            r22 = 16
            r0 = r22
            float[][] r6 = new float[r0][]
            r7 = 0
            r8 = r7
            r16 = r15
        L_0x006b:
            r22 = 91
            r0 = r22
            if (r9 != r0) goto L_0x006b
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x0100
            r9 = 26
        L_0x0087:
            r22 = 16
            r0 = r22
            float[] r3 = new float[r0]
            r4 = 0
        L_0x008e:
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r22 = r22 + r15
            int r18 = r22 + -1
            r22 = 45
            r0 = r22
            if (r9 != r0) goto L_0x010e
            r14 = 1
        L_0x009f:
            if (r14 == 0) goto L_0x00b9
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x0110
            r9 = 26
        L_0x00b7:
            r15 = r16
        L_0x00b9:
            r22 = 48
            r0 = r22
            if (r9 < r0) goto L_0x035a
            r22 = 57
            r0 = r22
            if (r9 > r0) goto L_0x035a
            int r13 = r9 + -48
        L_0x00c7:
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x011d
            r9 = 26
        L_0x00dd:
            r22 = 48
            r0 = r22
            if (r9 < r0) goto L_0x012a
            r22 = 57
            r0 = r22
            if (r9 > r0) goto L_0x012a
            int r22 = r13 * 10
            int r23 = r9 + -48
            int r13 = r22 + r23
            r15 = r16
            goto L_0x00c7
        L_0x00f2:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0061
        L_0x0100:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0087
        L_0x010e:
            r14 = 0
            goto L_0x009f
        L_0x0110:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x00b7
        L_0x011d:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x00dd
        L_0x012a:
            r17 = 1
            r22 = 46
            r0 = r22
            if (r9 != r0) goto L_0x01b1
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x0189
            r9 = 26
        L_0x0148:
            r22 = 48
            r0 = r22
            if (r9 < r0) goto L_0x01a3
            r22 = 57
            r0 = r22
            if (r9 > r0) goto L_0x01a3
            int r22 = r13 * 10
            int r23 = r9 + -48
            int r13 = r22 + r23
            int r17 = r17 * 10
        L_0x015c:
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x0196
            r9 = 26
        L_0x0172:
            r22 = 48
            r0 = r22
            if (r9 < r0) goto L_0x01b1
            r22 = 57
            r0 = r22
            if (r9 > r0) goto L_0x01b1
            int r22 = r13 * 10
            int r23 = r9 + -48
            int r13 = r22 + r23
            int r17 = r17 * 10
            r15 = r16
            goto L_0x015c
        L_0x0189:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0148
        L_0x0196:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0172
        L_0x01a3:
            r22 = -1
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r22 = 0
            float[][] r22 = (float[][]) r22
            goto L_0x0012
        L_0x01b1:
            r22 = 101(0x65, float:1.42E-43)
            r0 = r22
            if (r9 == r0) goto L_0x01bd
            r22 = 69
            r0 = r22
            if (r9 != r0) goto L_0x021d
        L_0x01bd:
            r12 = 1
        L_0x01be:
            if (r12 == 0) goto L_0x0246
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x021f
            r9 = 26
        L_0x01d6:
            r22 = 43
            r0 = r22
            if (r9 == r0) goto L_0x01e2
            r22 = 45
            r0 = r22
            if (r9 != r0) goto L_0x04b9
        L_0x01e2:
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x022c
            r9 = 26
        L_0x01f8:
            r22 = 48
            r0 = r22
            if (r9 < r0) goto L_0x0246
            r22 = 57
            r0 = r22
            if (r9 > r0) goto L_0x0246
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x0239
            r9 = 26
        L_0x021a:
            r16 = r15
            goto L_0x01f8
        L_0x021d:
            r12 = 0
            goto L_0x01be
        L_0x021f:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x01d6
        L_0x022c:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x01f8
        L_0x0239:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x021a
        L_0x0246:
            r15 = r16
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r22 = r22 + r15
            int r22 = r22 - r18
            int r11 = r22 + -1
            if (r12 != 0) goto L_0x02b5
            r22 = 10
            r0 = r22
            if (r11 >= r0) goto L_0x02b5
            float r0 = (float) r13
            r22 = r0
            r0 = r17
            float r0 = (float) r0
            r23 = r0
            float r21 = r22 / r23
            if (r14 == 0) goto L_0x026d
            r0 = r21
            float r0 = -r0
            r21 = r0
        L_0x026d:
            int r0 = r3.length
            r22 = r0
            r0 = r22
            if (r4 < r0) goto L_0x0290
            int r0 = r3.length
            r22 = r0
            int r22 = r22 * 3
            int r22 = r22 / 2
            r0 = r22
            float[] r0 = new float[r0]
            r20 = r0
            r22 = 0
            r23 = 0
            r0 = r22
            r1 = r20
            r2 = r23
            java.lang.System.arraycopy(r3, r0, r1, r2, r4)
            r3 = r20
        L_0x0290:
            int r5 = r4 + 1
            r3[r4] = r21
            r22 = 44
            r0 = r22
            if (r9 != r0) goto L_0x02cf
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x02c2
            r9 = 26
        L_0x02b0:
            r15 = r16
        L_0x02b2:
            r4 = r5
            goto L_0x008e
        L_0x02b5:
            r0 = r25
            r1 = r18
            java.lang.String r19 = r0.subString(r1, r11)
            float r21 = java.lang.Float.parseFloat(r19)
            goto L_0x026d
        L_0x02c2:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x02b0
        L_0x02cf:
            r22 = 93
            r0 = r22
            if (r9 != r0) goto L_0x02b2
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r10 = r22 + r15
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x034d
            r9 = 26
        L_0x02eb:
            int r0 = r3.length
            r22 = r0
            r0 = r22
            if (r5 == r0) goto L_0x0305
            float[] r0 = new float[r5]
            r20 = r0
            r22 = 0
            r23 = 0
            r0 = r22
            r1 = r20
            r2 = r23
            java.lang.System.arraycopy(r3, r0, r1, r2, r5)
            r3 = r20
        L_0x0305:
            int r0 = r6.length
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x0328
            int r0 = r6.length
            r22 = r0
            int r22 = r22 * 3
            int r22 = r22 / 2
            r0 = r22
            float[][] r0 = new float[r0][]
            r20 = r0
            r22 = 0
            r23 = 0
            r0 = r22
            r1 = r20
            r2 = r23
            java.lang.System.arraycopy(r3, r0, r1, r2, r5)
            r6 = r20
        L_0x0328:
            int r7 = r8 + 1
            r6[r8] = r3
            r22 = 44
            r0 = r22
            if (r9 != r0) goto L_0x0375
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x0368
            r9 = 26
        L_0x0348:
            r8 = r7
            r16 = r15
            goto L_0x006b
        L_0x034d:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x02eb
        L_0x035a:
            r22 = -1
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r22 = 0
            float[][] r22 = (float[][]) r22
            goto L_0x0012
        L_0x0368:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0348
        L_0x0375:
            r22 = 93
            r0 = r22
            if (r9 != r0) goto L_0x04b5
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r15 = r16 + 1
            int r10 = r22 + r16
            r0 = r25
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r10 < r0) goto L_0x03d8
            r9 = 26
        L_0x0391:
            int r0 = r6.length
            r22 = r0
            r0 = r22
            if (r7 == r0) goto L_0x03ab
            float[][] r0 = new float[r7][]
            r20 = r0
            r22 = 0
            r23 = 0
            r0 = r22
            r1 = r20
            r2 = r23
            java.lang.System.arraycopy(r6, r0, r1, r2, r7)
            r6 = r20
        L_0x03ab:
            r22 = 44
            r0 = r22
            if (r9 != r0) goto L_0x03e5
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r23 = r15 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r25
            r1.bp = r0
            r25.next()
            r22 = 3
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r22 = 16
            r0 = r22
            r1 = r25
            r1.token = r0
            r22 = r6
            goto L_0x0012
        L_0x03d8:
            r0 = r25
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r9 = r0.charAt(r10)
            goto L_0x0391
        L_0x03e5:
            r22 = 125(0x7d, float:1.75E-43)
            r0 = r22
            if (r9 != r0) goto L_0x04a7
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r16 = r15 + 1
            int r22 = r22 + r15
            r0 = r25
            r1 = r22
            char r9 = r0.charAt(r1)
            r22 = 44
            r0 = r22
            if (r9 != r0) goto L_0x042c
            r22 = 16
            r0 = r22
            r1 = r25
            r1.token = r0
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r23 = r16 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r25
            r1.bp = r0
            r25.next()
        L_0x041e:
            r22 = 4
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r15 = r16
            r22 = r6
            goto L_0x0012
        L_0x042c:
            r22 = 93
            r0 = r22
            if (r9 != r0) goto L_0x044e
            r22 = 15
            r0 = r22
            r1 = r25
            r1.token = r0
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r23 = r16 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r25
            r1.bp = r0
            r25.next()
            goto L_0x041e
        L_0x044e:
            r22 = 125(0x7d, float:1.75E-43)
            r0 = r22
            if (r9 != r0) goto L_0x0470
            r22 = 13
            r0 = r22
            r1 = r25
            r1.token = r0
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r23 = r16 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r25
            r1.bp = r0
            r25.next()
            goto L_0x041e
        L_0x0470:
            r22 = 26
            r0 = r22
            if (r9 != r0) goto L_0x0497
            r0 = r25
            int r0 = r0.bp
            r22 = r0
            int r23 = r16 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r25
            r1.bp = r0
            r22 = 20
            r0 = r22
            r1 = r25
            r1.token = r0
            r22 = 26
            r0 = r22
            r1 = r25
            r1.ch = r0
            goto L_0x041e
        L_0x0497:
            r22 = -1
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r22 = 0
            float[][] r22 = (float[][]) r22
            r15 = r16
            goto L_0x0012
        L_0x04a7:
            r22 = -1
            r0 = r22
            r1 = r25
            r1.matchStat = r0
            r22 = 0
            float[][] r22 = (float[][]) r22
            goto L_0x0012
        L_0x04b5:
            r15 = r16
            goto L_0x0348
        L_0x04b9:
            r16 = r15
            goto L_0x01f8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.scanFieldFloatArray2(long):float[][]");
    }

    public final double scanFieldDouble(long fieldHashCode) {
        int offset;
        int intVal;
        int offset2;
        char chLocal;
        int offset3;
        char chLocal2;
        double value;
        this.matchStat = 0;
        int offset4 = matchFieldHash(fieldHashCode);
        if (offset4 == 0) {
            return Utils.DOUBLE_EPSILON;
        }
        int offset5 = offset4 + 1;
        char chLocal3 = charAt(this.bp + offset4);
        int start = (this.bp + offset5) - 1;
        boolean negative = chLocal3 == '-';
        if (negative) {
            offset = offset5 + 1;
            chLocal3 = charAt(this.bp + offset5);
        } else {
            offset = offset5;
        }
        if (chLocal3 < '0' || chLocal3 > '9') {
            this.matchStat = -1;
            return Utils.DOUBLE_EPSILON;
        }
        int intVal2 = chLocal3 - '0';
        while (true) {
            offset2 = offset + 1;
            chLocal = charAt(this.bp + offset);
            if (chLocal < '0' || chLocal > '9') {
                int power = 1;
            } else {
                intVal2 = (intVal * 10) + (chLocal - '0');
                offset = offset2;
            }
        }
        int power2 = 1;
        if (chLocal == '.') {
            int offset6 = offset2 + 1;
            char chLocal4 = charAt(this.bp + offset2);
            if (chLocal4 >= '0' && chLocal4 <= '9') {
                intVal = (intVal * 10) + (chLocal4 - '0');
                power2 = 1 * 10;
                while (true) {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    intVal = (intVal * 10) + (chLocal - '0');
                    power2 *= 10;
                    offset6 = offset2;
                }
            } else {
                this.matchStat = -1;
                return Utils.DOUBLE_EPSILON;
            }
        }
        boolean exp2 = chLocal2 == 'e' || chLocal2 == 'E';
        if (exp2) {
            int offset7 = offset3 + 1;
            chLocal2 = charAt(this.bp + offset3);
            if (chLocal2 == '+' || chLocal2 == '-') {
                offset3 = offset7 + 1;
                chLocal2 = charAt(this.bp + offset7);
            } else {
                offset3 = offset7;
            }
            while (chLocal2 >= '0' && chLocal2 <= '9') {
                int offset8 = offset3 + 1;
                chLocal2 = charAt(this.bp + offset3);
                offset3 = offset8;
            }
        }
        int offset9 = offset3;
        int count = ((this.bp + offset9) - start) - 1;
        if (exp2 || count >= 10) {
            value = Double.parseDouble(subString(start, count));
        } else {
            value = ((double) intVal) / ((double) power2);
            if (negative) {
                value = -value;
            }
        }
        if (chLocal2 == ',') {
            this.bp += offset9 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal2 == '}') {
            int offset10 = offset9 + 1;
            char chLocal5 = charAt(this.bp + offset9);
            if (chLocal5 == ',') {
                this.token = 16;
                this.bp += offset10 - 1;
                next();
            } else if (chLocal5 == ']') {
                this.token = 15;
                this.bp += offset10 - 1;
                next();
            } else if (chLocal5 == '}') {
                this.token = 13;
                this.bp += offset10 - 1;
                next();
            } else if (chLocal5 == 26) {
                this.bp += offset10 - 1;
                this.token = 20;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                int i = offset10;
                return Utils.DOUBLE_EPSILON;
            }
            this.matchStat = 4;
            int i2 = offset10;
            return value;
        } else {
            this.matchStat = -1;
            return Utils.DOUBLE_EPSILON;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final double[] scanFieldDoubleArray(long r28) {
        /*
            r27 = this;
            r22 = 0
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            int r13 = r27.matchFieldHash(r28)
            if (r13 != 0) goto L_0x0010
            r4 = 0
        L_0x000f:
            return r4
        L_0x0010:
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x0037
            r7 = 26
        L_0x0026:
            r22 = 91
            r0 = r22
            if (r7 == r0) goto L_0x0044
            r22 = -1
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            r4 = 0
            r13 = r14
            goto L_0x000f
        L_0x0037:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x0026
        L_0x0044:
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r13 = r14 + 1
            int r8 = r22 + r14
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x00c3
            r7 = 26
        L_0x005a:
            r22 = 16
            r0 = r22
            double[] r4 = new double[r0]
            r5 = 0
        L_0x0061:
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r22 = r22 + r13
            int r17 = r22 + -1
            r22 = 45
            r0 = r22
            if (r7 != r0) goto L_0x00d0
            r12 = 1
        L_0x0072:
            if (r12 == 0) goto L_0x008b
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x00d2
            r7 = 26
        L_0x008a:
            r13 = r14
        L_0x008b:
            r22 = 48
            r0 = r22
            if (r7 < r0) goto L_0x02fd
            r22 = 57
            r0 = r22
            if (r7 > r0) goto L_0x02fd
            int r11 = r7 + -48
        L_0x0099:
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x00df
            r7 = 26
        L_0x00af:
            r22 = 48
            r0 = r22
            if (r7 < r0) goto L_0x00ec
            r22 = 57
            r0 = r22
            if (r7 > r0) goto L_0x00ec
            int r22 = r11 * 10
            int r23 = r7 + -48
            int r11 = r22 + r23
            r13 = r14
            goto L_0x0099
        L_0x00c3:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x005a
        L_0x00d0:
            r12 = 0
            goto L_0x0072
        L_0x00d2:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x008a
        L_0x00df:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x00af
        L_0x00ec:
            r15 = 1
            r22 = 46
            r0 = r22
            if (r7 != r0) goto L_0x014d
            r16 = 1
        L_0x00f5:
            if (r16 == 0) goto L_0x0175
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r13 = r14 + 1
            int r8 = r22 + r14
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x0150
            r7 = 26
        L_0x010d:
            int r15 = r15 * 10
            r22 = 48
            r0 = r22
            if (r7 < r0) goto L_0x016a
            r22 = 57
            r0 = r22
            if (r7 > r0) goto L_0x016a
            int r22 = r11 * 10
            int r23 = r7 + -48
            int r11 = r22 + r23
        L_0x0121:
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x015d
            r7 = 26
        L_0x0137:
            r22 = 48
            r0 = r22
            if (r7 < r0) goto L_0x0175
            r22 = 57
            r0 = r22
            if (r7 > r0) goto L_0x0175
            int r22 = r11 * 10
            int r23 = r7 + -48
            int r11 = r22 + r23
            int r15 = r15 * 10
            r13 = r14
            goto L_0x0121
        L_0x014d:
            r16 = 0
            goto L_0x00f5
        L_0x0150:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x010d
        L_0x015d:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x0137
        L_0x016a:
            r22 = -1
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            r4 = 0
            goto L_0x000f
        L_0x0175:
            r22 = 101(0x65, float:1.42E-43)
            r0 = r22
            if (r7 == r0) goto L_0x0181
            r22 = 69
            r0 = r22
            if (r7 != r0) goto L_0x01e0
        L_0x0181:
            r10 = 1
        L_0x0182:
            if (r10 == 0) goto L_0x0209
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r13 = r14 + 1
            int r8 = r22 + r14
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x01e2
            r7 = 26
        L_0x019a:
            r22 = 43
            r0 = r22
            if (r7 == r0) goto L_0x01a6
            r22 = 45
            r0 = r22
            if (r7 != r0) goto L_0x03df
        L_0x01a6:
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x01ef
            r7 = 26
        L_0x01bc:
            r22 = 48
            r0 = r22
            if (r7 < r0) goto L_0x0209
            r22 = 57
            r0 = r22
            if (r7 > r0) goto L_0x0209
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r13 = r14 + 1
            int r8 = r22 + r14
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x01fc
            r7 = 26
        L_0x01de:
            r14 = r13
            goto L_0x01bc
        L_0x01e0:
            r10 = 0
            goto L_0x0182
        L_0x01e2:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x019a
        L_0x01ef:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x01bc
        L_0x01fc:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x01de
        L_0x0209:
            r13 = r14
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r22 = r22 + r13
            int r22 = r22 - r17
            int r9 = r22 + -1
            if (r10 != 0) goto L_0x0274
            r22 = 10
            r0 = r22
            if (r9 >= r0) goto L_0x0274
            double r0 = (double) r11
            r22 = r0
            double r0 = (double) r15
            r24 = r0
            double r20 = r22 / r24
            if (r12 == 0) goto L_0x022d
            r0 = r20
            double r0 = -r0
            r20 = r0
        L_0x022d:
            int r0 = r4.length
            r22 = r0
            r0 = r22
            if (r5 < r0) goto L_0x0250
            int r0 = r4.length
            r22 = r0
            int r22 = r22 * 3
            int r22 = r22 / 2
            r0 = r22
            double[] r0 = new double[r0]
            r19 = r0
            r22 = 0
            r23 = 0
            r0 = r22
            r1 = r19
            r2 = r23
            java.lang.System.arraycopy(r4, r0, r1, r2, r5)
            r4 = r19
        L_0x0250:
            int r6 = r5 + 1
            r4[r5] = r20
            r22 = 44
            r0 = r22
            if (r7 != r0) goto L_0x028e
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x0281
            r7 = 26
        L_0x0270:
            r13 = r14
        L_0x0271:
            r5 = r6
            goto L_0x0061
        L_0x0274:
            r0 = r27
            r1 = r17
            java.lang.String r18 = r0.subString(r1, r9)
            double r20 = java.lang.Double.parseDouble(r18)
            goto L_0x022d
        L_0x0281:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x0270
        L_0x028e:
            r22 = 93
            r0 = r22
            if (r7 != r0) goto L_0x0271
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r14 = r13 + 1
            int r8 = r22 + r13
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x02f0
            r7 = 26
        L_0x02aa:
            int r0 = r4.length
            r22 = r0
            r0 = r22
            if (r6 == r0) goto L_0x02c4
            double[] r0 = new double[r6]
            r19 = r0
            r22 = 0
            r23 = 0
            r0 = r22
            r1 = r19
            r2 = r23
            java.lang.System.arraycopy(r4, r0, r1, r2, r6)
            r4 = r19
        L_0x02c4:
            r22 = 44
            r0 = r22
            if (r7 != r0) goto L_0x0308
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r23 = r14 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r27
            r1.bp = r0
            r27.next()
            r22 = 3
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            r22 = 16
            r0 = r22
            r1 = r27
            r1.token = r0
            r13 = r14
            goto L_0x000f
        L_0x02f0:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x02aa
        L_0x02fd:
            r22 = -1
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            r4 = 0
            goto L_0x000f
        L_0x0308:
            r22 = 125(0x7d, float:1.75E-43)
            r0 = r22
            if (r7 != r0) goto L_0x03d3
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r13 = r14 + 1
            int r8 = r22 + r14
            r0 = r27
            int r0 = r0.len
            r22 = r0
            r0 = r22
            if (r8 < r0) goto L_0x034f
            r7 = 26
        L_0x0324:
            r22 = 44
            r0 = r22
            if (r7 != r0) goto L_0x035c
            r22 = 16
            r0 = r22
            r1 = r27
            r1.token = r0
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r23 = r13 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r27
            r1.bp = r0
            r27.next()
        L_0x0345:
            r22 = 4
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            goto L_0x000f
        L_0x034f:
            r0 = r27
            java.lang.String r0 = r0.text
            r22 = r0
            r0 = r22
            char r7 = r0.charAt(r8)
            goto L_0x0324
        L_0x035c:
            r22 = 93
            r0 = r22
            if (r7 != r0) goto L_0x037e
            r22 = 15
            r0 = r22
            r1 = r27
            r1.token = r0
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r23 = r13 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r27
            r1.bp = r0
            r27.next()
            goto L_0x0345
        L_0x037e:
            r22 = 125(0x7d, float:1.75E-43)
            r0 = r22
            if (r7 != r0) goto L_0x03a0
            r22 = 13
            r0 = r22
            r1 = r27
            r1.token = r0
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r23 = r13 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r27
            r1.bp = r0
            r27.next()
            goto L_0x0345
        L_0x03a0:
            r22 = 26
            r0 = r22
            if (r7 != r0) goto L_0x03c8
            r0 = r27
            int r0 = r0.bp
            r22 = r0
            int r23 = r13 + -1
            int r22 = r22 + r23
            r0 = r22
            r1 = r27
            r1.bp = r0
            r22 = 20
            r0 = r22
            r1 = r27
            r1.token = r0
            r22 = 26
            r0 = r22
            r1 = r27
            r1.ch = r0
            goto L_0x0345
        L_0x03c8:
            r22 = -1
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            r4 = 0
            goto L_0x000f
        L_0x03d3:
            r22 = -1
            r0 = r22
            r1 = r27
            r1.matchStat = r0
            r4 = 0
            r13 = r14
            goto L_0x000f
        L_0x03df:
            r14 = r13
            goto L_0x01bc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.scanFieldDoubleArray(long):double[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:187:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final double[][] scanFieldDoubleArray2(long r30) {
        /*
            r29 = this;
            r24 = 0
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            int r16 = r29.matchFieldHash(r30)
            if (r16 != 0) goto L_0x0013
            r24 = 0
            double[][] r24 = (double[][]) r24
        L_0x0012:
            return r24
        L_0x0013:
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x003e
            r10 = 26
        L_0x0029:
            r24 = 91
            r0 = r24
            if (r10 == r0) goto L_0x004b
            r24 = -1
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r24 = 0
            double[][] r24 = (double[][]) r24
            r16 = r17
            goto L_0x0012
        L_0x003e:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0029
        L_0x004b:
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x00f2
            r10 = 26
        L_0x0061:
            r24 = 16
            r0 = r24
            double[][] r7 = new double[r0][]
            r8 = 0
            r9 = r8
            r17 = r16
        L_0x006b:
            r24 = 91
            r0 = r24
            if (r10 != r0) goto L_0x006b
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x0100
            r10 = 26
        L_0x0087:
            r24 = 16
            r0 = r24
            double[] r4 = new double[r0]
            r5 = 0
        L_0x008e:
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r24 = r24 + r16
            int r19 = r24 + -1
            r24 = 45
            r0 = r24
            if (r10 != r0) goto L_0x010e
            r15 = 1
        L_0x009f:
            if (r15 == 0) goto L_0x00b9
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x0110
            r10 = 26
        L_0x00b7:
            r16 = r17
        L_0x00b9:
            r24 = 48
            r0 = r24
            if (r10 < r0) goto L_0x035a
            r24 = 57
            r0 = r24
            if (r10 > r0) goto L_0x035a
            int r14 = r10 + -48
        L_0x00c7:
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x011d
            r10 = 26
        L_0x00dd:
            r24 = 48
            r0 = r24
            if (r10 < r0) goto L_0x012a
            r24 = 57
            r0 = r24
            if (r10 > r0) goto L_0x012a
            int r24 = r14 * 10
            int r25 = r10 + -48
            int r14 = r24 + r25
            r16 = r17
            goto L_0x00c7
        L_0x00f2:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0061
        L_0x0100:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0087
        L_0x010e:
            r15 = 0
            goto L_0x009f
        L_0x0110:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x00b7
        L_0x011d:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x00dd
        L_0x012a:
            r18 = 1
            r24 = 46
            r0 = r24
            if (r10 != r0) goto L_0x01b1
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x0189
            r10 = 26
        L_0x0148:
            r24 = 48
            r0 = r24
            if (r10 < r0) goto L_0x01a3
            r24 = 57
            r0 = r24
            if (r10 > r0) goto L_0x01a3
            int r24 = r14 * 10
            int r25 = r10 + -48
            int r14 = r24 + r25
            int r18 = r18 * 10
        L_0x015c:
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x0196
            r10 = 26
        L_0x0172:
            r24 = 48
            r0 = r24
            if (r10 < r0) goto L_0x01b1
            r24 = 57
            r0 = r24
            if (r10 > r0) goto L_0x01b1
            int r24 = r14 * 10
            int r25 = r10 + -48
            int r14 = r24 + r25
            int r18 = r18 * 10
            r16 = r17
            goto L_0x015c
        L_0x0189:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0148
        L_0x0196:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0172
        L_0x01a3:
            r24 = -1
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r24 = 0
            double[][] r24 = (double[][]) r24
            goto L_0x0012
        L_0x01b1:
            r24 = 101(0x65, float:1.42E-43)
            r0 = r24
            if (r10 == r0) goto L_0x01bd
            r24 = 69
            r0 = r24
            if (r10 != r0) goto L_0x021d
        L_0x01bd:
            r13 = 1
        L_0x01be:
            if (r13 == 0) goto L_0x0246
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x021f
            r10 = 26
        L_0x01d6:
            r24 = 43
            r0 = r24
            if (r10 == r0) goto L_0x01e2
            r24 = 45
            r0 = r24
            if (r10 != r0) goto L_0x04b9
        L_0x01e2:
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x022c
            r10 = 26
        L_0x01f8:
            r24 = 48
            r0 = r24
            if (r10 < r0) goto L_0x0246
            r24 = 57
            r0 = r24
            if (r10 > r0) goto L_0x0246
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x0239
            r10 = 26
        L_0x021a:
            r17 = r16
            goto L_0x01f8
        L_0x021d:
            r13 = 0
            goto L_0x01be
        L_0x021f:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x01d6
        L_0x022c:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x01f8
        L_0x0239:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x021a
        L_0x0246:
            r16 = r17
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r24 = r24 + r16
            int r24 = r24 - r19
            int r12 = r24 + -1
            if (r13 != 0) goto L_0x02b5
            r24 = 10
            r0 = r24
            if (r12 >= r0) goto L_0x02b5
            double r0 = (double) r14
            r24 = r0
            r0 = r18
            double r0 = (double) r0
            r26 = r0
            double r22 = r24 / r26
            if (r15 == 0) goto L_0x026d
            r0 = r22
            double r0 = -r0
            r22 = r0
        L_0x026d:
            int r0 = r4.length
            r24 = r0
            r0 = r24
            if (r5 < r0) goto L_0x0290
            int r0 = r4.length
            r24 = r0
            int r24 = r24 * 3
            int r24 = r24 / 2
            r0 = r24
            double[] r0 = new double[r0]
            r21 = r0
            r24 = 0
            r25 = 0
            r0 = r24
            r1 = r21
            r2 = r25
            java.lang.System.arraycopy(r4, r0, r1, r2, r5)
            r4 = r21
        L_0x0290:
            int r6 = r5 + 1
            r4[r5] = r22
            r24 = 44
            r0 = r24
            if (r10 != r0) goto L_0x02cf
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x02c2
            r10 = 26
        L_0x02b0:
            r16 = r17
        L_0x02b2:
            r5 = r6
            goto L_0x008e
        L_0x02b5:
            r0 = r29
            r1 = r19
            java.lang.String r20 = r0.subString(r1, r12)
            double r22 = java.lang.Double.parseDouble(r20)
            goto L_0x026d
        L_0x02c2:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x02b0
        L_0x02cf:
            r24 = 93
            r0 = r24
            if (r10 != r0) goto L_0x02b2
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r11 = r24 + r16
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x034d
            r10 = 26
        L_0x02eb:
            int r0 = r4.length
            r24 = r0
            r0 = r24
            if (r6 == r0) goto L_0x0305
            double[] r0 = new double[r6]
            r21 = r0
            r24 = 0
            r25 = 0
            r0 = r24
            r1 = r21
            r2 = r25
            java.lang.System.arraycopy(r4, r0, r1, r2, r6)
            r4 = r21
        L_0x0305:
            int r0 = r7.length
            r24 = r0
            r0 = r24
            if (r9 < r0) goto L_0x0328
            int r0 = r7.length
            r24 = r0
            int r24 = r24 * 3
            int r24 = r24 / 2
            r0 = r24
            double[][] r0 = new double[r0][]
            r21 = r0
            r24 = 0
            r25 = 0
            r0 = r24
            r1 = r21
            r2 = r25
            java.lang.System.arraycopy(r4, r0, r1, r2, r6)
            r7 = r21
        L_0x0328:
            int r8 = r9 + 1
            r7[r9] = r4
            r24 = 44
            r0 = r24
            if (r10 != r0) goto L_0x0375
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x0368
            r10 = 26
        L_0x0348:
            r9 = r8
            r17 = r16
            goto L_0x006b
        L_0x034d:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x02eb
        L_0x035a:
            r24 = -1
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r24 = 0
            double[][] r24 = (double[][]) r24
            goto L_0x0012
        L_0x0368:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0348
        L_0x0375:
            r24 = 93
            r0 = r24
            if (r10 != r0) goto L_0x04b5
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r16 = r17 + 1
            int r11 = r24 + r17
            r0 = r29
            int r0 = r0.len
            r24 = r0
            r0 = r24
            if (r11 < r0) goto L_0x03d8
            r10 = 26
        L_0x0391:
            int r0 = r7.length
            r24 = r0
            r0 = r24
            if (r8 == r0) goto L_0x03ab
            double[][] r0 = new double[r8][]
            r21 = r0
            r24 = 0
            r25 = 0
            r0 = r24
            r1 = r21
            r2 = r25
            java.lang.System.arraycopy(r7, r0, r1, r2, r8)
            r7 = r21
        L_0x03ab:
            r24 = 44
            r0 = r24
            if (r10 != r0) goto L_0x03e5
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r25 = r16 + -1
            int r24 = r24 + r25
            r0 = r24
            r1 = r29
            r1.bp = r0
            r29.next()
            r24 = 3
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r24 = 16
            r0 = r24
            r1 = r29
            r1.token = r0
            r24 = r7
            goto L_0x0012
        L_0x03d8:
            r0 = r29
            java.lang.String r0 = r0.text
            r24 = r0
            r0 = r24
            char r10 = r0.charAt(r11)
            goto L_0x0391
        L_0x03e5:
            r24 = 125(0x7d, float:1.75E-43)
            r0 = r24
            if (r10 != r0) goto L_0x04a7
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r17 = r16 + 1
            int r24 = r24 + r16
            r0 = r29
            r1 = r24
            char r10 = r0.charAt(r1)
            r24 = 44
            r0 = r24
            if (r10 != r0) goto L_0x042c
            r24 = 16
            r0 = r24
            r1 = r29
            r1.token = r0
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r25 = r17 + -1
            int r24 = r24 + r25
            r0 = r24
            r1 = r29
            r1.bp = r0
            r29.next()
        L_0x041e:
            r24 = 4
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r16 = r17
            r24 = r7
            goto L_0x0012
        L_0x042c:
            r24 = 93
            r0 = r24
            if (r10 != r0) goto L_0x044e
            r24 = 15
            r0 = r24
            r1 = r29
            r1.token = r0
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r25 = r17 + -1
            int r24 = r24 + r25
            r0 = r24
            r1 = r29
            r1.bp = r0
            r29.next()
            goto L_0x041e
        L_0x044e:
            r24 = 125(0x7d, float:1.75E-43)
            r0 = r24
            if (r10 != r0) goto L_0x0470
            r24 = 13
            r0 = r24
            r1 = r29
            r1.token = r0
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r25 = r17 + -1
            int r24 = r24 + r25
            r0 = r24
            r1 = r29
            r1.bp = r0
            r29.next()
            goto L_0x041e
        L_0x0470:
            r24 = 26
            r0 = r24
            if (r10 != r0) goto L_0x0497
            r0 = r29
            int r0 = r0.bp
            r24 = r0
            int r25 = r17 + -1
            int r24 = r24 + r25
            r0 = r24
            r1 = r29
            r1.bp = r0
            r24 = 20
            r0 = r24
            r1 = r29
            r1.token = r0
            r24 = 26
            r0 = r24
            r1 = r29
            r1.ch = r0
            goto L_0x041e
        L_0x0497:
            r24 = -1
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r24 = 0
            double[][] r24 = (double[][]) r24
            r16 = r17
            goto L_0x0012
        L_0x04a7:
            r24 = -1
            r0 = r24
            r1 = r29
            r1.matchStat = r0
            r24 = 0
            double[][] r24 = (double[][]) r24
            goto L_0x0012
        L_0x04b5:
            r16 = r17
            goto L_0x0348
        L_0x04b9:
            r17 = r16
            goto L_0x01f8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.scanFieldDoubleArray2(long):double[][]");
    }

    public long scanFieldSymbol(long fieldHashCode) {
        char chLocal;
        char chLocal2;
        char chLocal3;
        char chLocal4;
        char charAt;
        this.matchStat = 0;
        int offset = matchFieldHash(fieldHashCode);
        if (offset == 0) {
            return 0;
        }
        int offset2 = offset + 1;
        int charIndex = this.bp + offset;
        if (charIndex >= this.len) {
            chLocal = 26;
        } else {
            chLocal = this.text.charAt(charIndex);
        }
        if (chLocal != '\"') {
            this.matchStat = -1;
            int i = offset2;
            return 0;
        }
        long hash = -3750763034362895579L;
        int i2 = this.bp + offset2;
        while (true) {
            int offset3 = offset2;
            offset2 = offset3 + 1;
            int charIndex2 = this.bp + offset3;
            if (charIndex2 >= this.len) {
                chLocal2 = 26;
            } else {
                chLocal2 = this.text.charAt(charIndex2);
            }
            if (chLocal2 == '\"') {
                int offset4 = offset2 + 1;
                int charIndex3 = this.bp + offset2;
                if (charIndex3 >= this.len) {
                    chLocal3 = 26;
                } else {
                    chLocal3 = this.text.charAt(charIndex3);
                }
                if (chLocal3 == ',') {
                    this.bp += offset4 - 1;
                    int index = this.bp + 1;
                    this.bp = index;
                    if (index >= this.len) {
                        charAt = 26;
                    } else {
                        charAt = this.text.charAt(index);
                    }
                    this.ch = charAt;
                    this.matchStat = 3;
                    return hash;
                } else if (chLocal3 == '}') {
                    int offset5 = offset4 + 1;
                    int charIndex4 = this.bp + offset4;
                    if (charIndex4 >= this.len) {
                        chLocal4 = 26;
                    } else {
                        chLocal4 = this.text.charAt(charIndex4);
                    }
                    if (chLocal4 == ',') {
                        this.token = 16;
                        this.bp += offset5 - 1;
                        next();
                    } else if (chLocal4 == ']') {
                        this.token = 15;
                        this.bp += offset5 - 1;
                        next();
                    } else if (chLocal4 == '}') {
                        this.token = 13;
                        this.bp += offset5 - 1;
                        next();
                    } else if (chLocal4 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = 26;
                    } else {
                        this.matchStat = -1;
                        int i3 = offset5;
                        return 0;
                    }
                    this.matchStat = 4;
                    int i4 = offset5;
                    return hash;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            } else {
                hash = (hash ^ ((long) chLocal2)) * 1099511628211L;
                if (chLocal2 == '\\') {
                    this.matchStat = -1;
                    int i5 = offset2;
                    return 0;
                }
            }
        }
    }

    public boolean scanISO8601DateIfMatch(boolean strict) {
        return scanISO8601DateIfMatch(strict, this.len - this.bp);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x018a, code lost:
        if (r33 != ' ') goto L_0x018c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scanISO8601DateIfMatch(boolean r74, int r75) {
        /*
            r73 = this;
            if (r74 != 0) goto L_0x0154
            r18 = 13
            r0 = r75
            r1 = r18
            if (r0 <= r1) goto L_0x0154
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            r0 = r73
            r1 = r18
            char r31 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 1
            r0 = r73
            r1 = r18
            char r32 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 2
            r0 = r73
            r1 = r18
            char r37 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 3
            r0 = r73
            r1 = r18
            char r38 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r39 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 5
            r0 = r73
            r1 = r18
            char r40 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r75
            int r18 = r18 + -1
            r0 = r73
            r1 = r18
            char r46 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r75
            int r18 = r18 + -2
            r0 = r73
            r1 = r18
            char r47 = r0.charAt(r1)
            r18 = 47
            r0 = r31
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 68
            r0 = r32
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 97
            r0 = r37
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 116(0x74, float:1.63E-43)
            r0 = r38
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 101(0x65, float:1.42E-43)
            r0 = r39
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 40
            r0 = r40
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 47
            r0 = r46
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r18 = 41
            r0 = r47
            r1 = r18
            if (r0 != r1) goto L_0x0154
            r59 = -1
            r52 = 6
        L_0x00d0:
            r0 = r52
            r1 = r75
            if (r0 >= r1) goto L_0x0103
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r52
            r0 = r73
            r1 = r18
            char r30 = r0.charAt(r1)
            r18 = 43
            r0 = r30
            r1 = r18
            if (r0 != r1) goto L_0x00f3
            r59 = r52
        L_0x00f0:
            int r52 = r52 + 1
            goto L_0x00d0
        L_0x00f3:
            r18 = 48
            r0 = r30
            r1 = r18
            if (r0 < r1) goto L_0x0103
            r18 = 57
            r0 = r30
            r1 = r18
            if (r0 <= r1) goto L_0x00f0
        L_0x0103:
            r18 = -1
            r0 = r59
            r1 = r18
            if (r0 != r1) goto L_0x010e
            r18 = 0
        L_0x010d:
            return r18
        L_0x010e:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r58 = r18 + 6
            int r18 = r59 - r58
            r0 = r73
            r1 = r58
            r2 = r18
            java.lang.String r57 = r0.subString(r1, r2)
            long r54 = java.lang.Long.parseLong(r57)
            r0 = r73
            java.util.TimeZone r0 = r0.timeZone
            r18 = r0
            r0 = r73
            java.util.Locale r0 = r0.locale
            r19 = r0
            java.util.Calendar r18 = java.util.Calendar.getInstance(r18, r19)
            r0 = r18
            r1 = r73
            r1.calendar = r0
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r0 = r18
            r1 = r54
            r0.setTimeInMillis(r1)
            r18 = 5
            r0 = r18
            r1 = r73
            r1.token = r0
            r18 = 1
            goto L_0x010d
        L_0x0154:
            r18 = 8
            r0 = r75
            r1 = r18
            if (r0 == r1) goto L_0x01ac
            r18 = 14
            r0 = r75
            r1 = r18
            if (r0 == r1) goto L_0x01ac
            r18 = 16
            r0 = r75
            r1 = r18
            if (r0 != r1) goto L_0x018c
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 10
            r0 = r73
            r1 = r18
            char r33 = r0.charAt(r1)
            r18 = 84
            r0 = r33
            r1 = r18
            if (r0 == r1) goto L_0x01ac
            r18 = 32
            r0 = r33
            r1 = r18
            if (r0 == r1) goto L_0x01ac
        L_0x018c:
            r18 = 17
            r0 = r75
            r1 = r18
            if (r0 != r1) goto L_0x0495
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 6
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 45
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x0495
        L_0x01ac:
            if (r74 == 0) goto L_0x01b2
            r18 = 0
            goto L_0x010d
        L_0x01b2:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            r0 = r73
            r1 = r18
            char r31 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 1
            r0 = r73
            r1 = r18
            char r32 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 2
            r0 = r73
            r1 = r18
            char r37 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 3
            r0 = r73
            r1 = r18
            char r38 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r39 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 5
            r0 = r73
            r1 = r18
            char r40 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 6
            r0 = r73
            r1 = r18
            char r41 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 7
            r0 = r73
            r1 = r18
            char r42 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 8
            r0 = r73
            r1 = r18
            char r43 = r0.charAt(r1)
            r18 = 45
            r0 = r39
            r1 = r18
            if (r0 != r1) goto L_0x0296
            r18 = 45
            r0 = r42
            r1 = r18
            if (r0 != r1) goto L_0x0296
            r45 = 1
        L_0x0252:
            if (r45 == 0) goto L_0x0299
            r18 = 16
            r0 = r75
            r1 = r18
            if (r0 != r1) goto L_0x0299
            r61 = 1
        L_0x025e:
            if (r45 == 0) goto L_0x029c
            r18 = 17
            r0 = r75
            r1 = r18
            if (r0 != r1) goto L_0x029c
            r62 = 1
        L_0x026a:
            if (r62 != 0) goto L_0x026e
            if (r61 == 0) goto L_0x029f
        L_0x026e:
            r4 = r31
            r5 = r32
            r6 = r37
            r7 = r38
            r8 = r40
            r9 = r41
            r10 = r43
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 9
            r0 = r73
            r1 = r18
            char r11 = r0.charAt(r1)
        L_0x028c:
            boolean r18 = checkDate(r4, r5, r6, r7, r8, r9, r10, r11)
            if (r18 != 0) goto L_0x02b0
            r18 = 0
            goto L_0x010d
        L_0x0296:
            r45 = 0
            goto L_0x0252
        L_0x0299:
            r61 = 0
            goto L_0x025e
        L_0x029c:
            r62 = 0
            goto L_0x026a
        L_0x029f:
            r4 = r31
            r5 = r32
            r6 = r37
            r7 = r38
            r8 = r39
            r9 = r40
            r10 = r41
            r11 = r42
            goto L_0x028c
        L_0x02b0:
            r12 = r73
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            r17 = r8
            r18 = r9
            r19 = r10
            r20 = r11
            r12.setCalendar(r13, r14, r15, r16, r17, r18, r19, r20)
            r18 = 8
            r0 = r75
            r1 = r18
            if (r0 == r1) goto L_0x048c
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 9
            r0 = r73
            r1 = r18
            char r44 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 10
            r0 = r73
            r1 = r18
            char r33 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 11
            r0 = r73
            r1 = r18
            char r34 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 12
            r0 = r73
            r1 = r18
            char r35 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 13
            r0 = r73
            r1 = r18
            char r36 = r0.charAt(r1)
            if (r62 == 0) goto L_0x0344
            r18 = 84
            r0 = r33
            r1 = r18
            if (r0 != r1) goto L_0x0344
            r18 = 58
            r0 = r36
            r1 = r18
            if (r0 != r1) goto L_0x0344
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 16
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 90
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x035e
        L_0x0344:
            if (r61 == 0) goto L_0x0390
            r18 = 32
            r0 = r33
            r1 = r18
            if (r0 == r1) goto L_0x0356
            r18 = 84
            r0 = r33
            r1 = r18
            if (r0 != r1) goto L_0x0390
        L_0x0356:
            r18 = 58
            r0 = r36
            r1 = r18
            if (r0 != r1) goto L_0x0390
        L_0x035e:
            r12 = r34
            r13 = r35
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 14
            r0 = r73
            r1 = r18
            char r14 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 15
            r0 = r73
            r1 = r18
            char r15 = r0.charAt(r1)
            r16 = 48
            r17 = 48
        L_0x0386:
            boolean r18 = checkTime(r12, r13, r14, r15, r16, r17)
            if (r18 != 0) goto L_0x039d
            r18 = 0
            goto L_0x010d
        L_0x0390:
            r12 = r43
            r13 = r44
            r14 = r33
            r15 = r34
            r16 = r35
            r17 = r36
            goto L_0x0386
        L_0x039d:
            r18 = 17
            r0 = r75
            r1 = r18
            if (r0 != r1) goto L_0x0489
            if (r62 != 0) goto L_0x0489
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 14
            r0 = r73
            r1 = r18
            char r27 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 15
            r0 = r73
            r1 = r18
            char r28 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 16
            r0 = r73
            r1 = r18
            char r29 = r0.charAt(r1)
            r18 = 48
            r0 = r27
            r1 = r18
            if (r0 < r1) goto L_0x03e7
            r18 = 57
            r0 = r27
            r1 = r18
            if (r0 <= r1) goto L_0x03eb
        L_0x03e7:
            r18 = 0
            goto L_0x010d
        L_0x03eb:
            r18 = 48
            r0 = r28
            r1 = r18
            if (r0 < r1) goto L_0x03fb
            r18 = 57
            r0 = r28
            r1 = r18
            if (r0 <= r1) goto L_0x03ff
        L_0x03fb:
            r18 = 0
            goto L_0x010d
        L_0x03ff:
            r18 = 48
            r0 = r29
            r1 = r18
            if (r0 < r1) goto L_0x040f
            r18 = 57
            r0 = r29
            r1 = r18
            if (r0 <= r1) goto L_0x0413
        L_0x040f:
            r18 = 0
            goto L_0x010d
        L_0x0413:
            int r18 = r27 + -48
            int r18 = r18 * 100
            int r19 = r28 + -48
            int r19 = r19 * 10
            int r18 = r18 + r19
            int r19 = r29 + -48
            int r54 = r18 + r19
        L_0x0421:
            int r18 = r12 + -48
            int r18 = r18 * 10
            int r19 = r13 + -48
            int r51 = r18 + r19
            int r18 = r14 + -48
            int r18 = r18 * 10
            int r19 = r15 + -48
            int r56 = r18 + r19
            int r18 = r16 + -48
            int r18 = r18 * 10
            int r19 = r17 + -48
            int r60 = r18 + r19
        L_0x0439:
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 11
            r0 = r18
            r1 = r19
            r2 = r51
            r0.set(r1, r2)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 12
            r0 = r18
            r1 = r19
            r2 = r56
            r0.set(r1, r2)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 13
            r0 = r18
            r1 = r19
            r2 = r60
            r0.set(r1, r2)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 14
            r0 = r18
            r1 = r19
            r2 = r54
            r0.set(r1, r2)
            r18 = 5
            r0 = r18
            r1 = r73
            r1.token = r0
            r18 = 1
            goto L_0x010d
        L_0x0489:
            r54 = 0
            goto L_0x0421
        L_0x048c:
            r51 = 0
            r56 = 0
            r60 = 0
            r54 = 0
            goto L_0x0439
        L_0x0495:
            r18 = 9
            r0 = r75
            r1 = r18
            if (r0 >= r1) goto L_0x04a1
            r18 = 0
            goto L_0x010d
        L_0x04a1:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            r0 = r73
            r1 = r18
            char r31 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 1
            r0 = r73
            r1 = r18
            char r32 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 2
            r0 = r73
            r1 = r18
            char r37 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 3
            r0 = r73
            r1 = r18
            char r38 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r39 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 5
            r0 = r73
            r1 = r18
            char r40 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 6
            r0 = r73
            r1 = r18
            char r41 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 7
            r0 = r73
            r1 = r18
            char r42 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 8
            r0 = r73
            r1 = r18
            char r43 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 9
            r0 = r73
            r1 = r18
            char r44 = r0.charAt(r1)
            r48 = 10
            r18 = 45
            r0 = r39
            r1 = r18
            if (r0 != r1) goto L_0x0551
            r18 = 45
            r0 = r42
            r1 = r18
            if (r0 == r1) goto L_0x0561
        L_0x0551:
            r18 = 47
            r0 = r39
            r1 = r18
            if (r0 != r1) goto L_0x057b
            r18 = 47
            r0 = r42
            r1 = r18
            if (r0 != r1) goto L_0x057b
        L_0x0561:
            r4 = r31
            r5 = r32
            r6 = r37
            r7 = r38
            r8 = r40
            r9 = r41
            r10 = r43
            r11 = r44
        L_0x0571:
            boolean r18 = checkDate(r4, r5, r6, r7, r8, r9, r10, r11)
            if (r18 != 0) goto L_0x06af
            r18 = 0
            goto L_0x010d
        L_0x057b:
            r18 = 45
            r0 = r39
            r1 = r18
            if (r0 != r1) goto L_0x05ad
            r18 = 45
            r0 = r41
            r1 = r18
            if (r0 != r1) goto L_0x05ad
            r4 = r31
            r5 = r32
            r6 = r37
            r7 = r38
            r8 = 48
            r9 = r40
            r18 = 32
            r0 = r43
            r1 = r18
            if (r0 != r1) goto L_0x05a6
            r10 = 48
            r11 = r42
            r48 = 8
            goto L_0x0571
        L_0x05a6:
            r10 = r42
            r11 = r43
            r48 = 9
            goto L_0x0571
        L_0x05ad:
            r18 = 46
            r0 = r37
            r1 = r18
            if (r0 != r1) goto L_0x05bd
            r18 = 46
            r0 = r40
            r1 = r18
            if (r0 == r1) goto L_0x05cd
        L_0x05bd:
            r18 = 45
            r0 = r37
            r1 = r18
            if (r0 != r1) goto L_0x05de
            r18 = 45
            r0 = r40
            r1 = r18
            if (r0 != r1) goto L_0x05de
        L_0x05cd:
            r10 = r31
            r11 = r32
            r8 = r38
            r9 = r39
            r4 = r41
            r5 = r42
            r6 = r43
            r7 = r44
            goto L_0x0571
        L_0x05de:
            r18 = 24180(0x5e74, float:3.3883E-41)
            r0 = r39
            r1 = r18
            if (r0 == r1) goto L_0x05ef
            r18 = 45380(0xb144, float:6.3591E-41)
            r0 = r39
            r1 = r18
            if (r0 != r1) goto L_0x06ab
        L_0x05ef:
            r4 = r31
            r5 = r32
            r6 = r37
            r7 = r38
            r18 = 26376(0x6708, float:3.696E-41)
            r0 = r42
            r1 = r18
            if (r0 == r1) goto L_0x0608
            r18 = 50900(0xc6d4, float:7.1326E-41)
            r0 = r42
            r1 = r18
            if (r0 != r1) goto L_0x0660
        L_0x0608:
            r8 = r40
            r9 = r41
            r18 = 26085(0x65e5, float:3.6553E-41)
            r0 = r44
            r1 = r18
            if (r0 == r1) goto L_0x061d
            r18 = 51068(0xc77c, float:7.1562E-41)
            r0 = r44
            r1 = r18
            if (r0 != r1) goto L_0x0623
        L_0x061d:
            r10 = 48
            r11 = r43
            goto L_0x0571
        L_0x0623:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 10
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 26085(0x65e5, float:3.6553E-41)
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x0654
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + 10
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 51068(0xc77c, float:7.1562E-41)
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x065c
        L_0x0654:
            r10 = r43
            r11 = r44
            r48 = 11
            goto L_0x0571
        L_0x065c:
            r18 = 0
            goto L_0x010d
        L_0x0660:
            r18 = 26376(0x6708, float:3.696E-41)
            r0 = r41
            r1 = r18
            if (r0 == r1) goto L_0x0671
            r18 = 50900(0xc6d4, float:7.1326E-41)
            r0 = r41
            r1 = r18
            if (r0 != r1) goto L_0x06a7
        L_0x0671:
            r8 = 48
            r9 = r40
            r18 = 26085(0x65e5, float:3.6553E-41)
            r0 = r43
            r1 = r18
            if (r0 == r1) goto L_0x0686
            r18 = 51068(0xc77c, float:7.1562E-41)
            r0 = r43
            r1 = r18
            if (r0 != r1) goto L_0x068c
        L_0x0686:
            r10 = 48
            r11 = r42
            goto L_0x0571
        L_0x068c:
            r18 = 26085(0x65e5, float:3.6553E-41)
            r0 = r44
            r1 = r18
            if (r0 == r1) goto L_0x069d
            r18 = 51068(0xc77c, float:7.1562E-41)
            r0 = r44
            r1 = r18
            if (r0 != r1) goto L_0x06a3
        L_0x069d:
            r10 = r42
            r11 = r43
            goto L_0x0571
        L_0x06a3:
            r18 = 0
            goto L_0x010d
        L_0x06a7:
            r18 = 0
            goto L_0x010d
        L_0x06ab:
            r18 = 0
            goto L_0x010d
        L_0x06af:
            r18 = r73
            r19 = r4
            r20 = r5
            r21 = r6
            r22 = r7
            r23 = r8
            r24 = r9
            r25 = r10
            r26 = r11
            r18.setCalendar(r19, r20, r21, r22, r23, r24, r25, r26)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            r0 = r73
            r1 = r18
            char r63 = r0.charAt(r1)
            r18 = 84
            r0 = r63
            r1 = r18
            if (r0 == r1) goto L_0x06e6
            r18 = 32
            r0 = r63
            r1 = r18
            if (r0 != r1) goto L_0x06f2
            if (r74 != 0) goto L_0x06f2
        L_0x06e6:
            int r18 = r48 + 9
            r0 = r75
            r1 = r18
            if (r0 >= r1) goto L_0x0838
            r18 = 0
            goto L_0x010d
        L_0x06f2:
            r18 = 34
            r0 = r63
            r1 = r18
            if (r0 == r1) goto L_0x0713
            r18 = 26
            r0 = r63
            r1 = r18
            if (r0 == r1) goto L_0x0713
            r18 = 26085(0x65e5, float:3.6553E-41)
            r0 = r63
            r1 = r18
            if (r0 == r1) goto L_0x0713
            r18 = 51068(0xc77c, float:7.1562E-41)
            r0 = r63
            r1 = r18
            if (r0 != r1) goto L_0x076f
        L_0x0713:
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 11
            r20 = 0
            r18.set(r19, r20)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 12
            r20 = 0
            r18.set(r19, r20)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 13
            r20 = 0
            r18.set(r19, r20)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 14
            r20 = 0
            r18.set(r19, r20)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            r0 = r18
            r1 = r73
            r1.bp = r0
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r0 = r18
            r1 = r73
            r1.ch = r0
            r18 = 5
            r0 = r18
            r1 = r73
            r1.token = r0
            r18 = 1
            goto L_0x010d
        L_0x076f:
            r18 = 43
            r0 = r63
            r1 = r18
            if (r0 == r1) goto L_0x077f
            r18 = 45
            r0 = r63
            r1 = r18
            if (r0 != r1) goto L_0x0834
        L_0x077f:
            r0 = r73
            int r0 = r0.len
            r18 = r0
            int r19 = r48 + 6
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x0830
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 3
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 58
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x07db
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 48
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x07db
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 5
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 48
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x07df
        L_0x07db:
            r18 = 0
            goto L_0x010d
        L_0x07df:
            r19 = 48
            r20 = 48
            r21 = 48
            r22 = 48
            r23 = 48
            r24 = 48
            r18 = r73
            r18.setTime(r19, r20, r21, r22, r23, r24)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 14
            r20 = 0
            r18.set(r19, r20)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 1
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r19 = r0
            int r19 = r19 + r48
            int r19 = r19 + 2
            r0 = r73
            r1 = r19
            char r19 = r0.charAt(r1)
            r0 = r73
            r1 = r63
            r2 = r18
            r3 = r19
            r0.setTimeZone(r1, r2, r3)
            r18 = 1
            goto L_0x010d
        L_0x0830:
            r18 = 0
            goto L_0x010d
        L_0x0834:
            r18 = 0
            goto L_0x010d
        L_0x0838:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 3
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 58
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x0856
            r18 = 0
            goto L_0x010d
        L_0x0856:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 6
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r19 = 58
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x0874
            r18 = 0
            goto L_0x010d
        L_0x0874:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 1
            r0 = r73
            r1 = r18
            char r12 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 2
            r0 = r73
            r1 = r18
            char r13 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r14 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 5
            r0 = r73
            r1 = r18
            char r15 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 7
            r0 = r73
            r1 = r18
            char r16 = r0.charAt(r1)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 8
            r0 = r73
            r1 = r18
            char r17 = r0.charAt(r1)
            boolean r18 = checkTime(r12, r13, r14, r15, r16, r17)
            if (r18 != 0) goto L_0x08ea
            r18 = 0
            goto L_0x010d
        L_0x08ea:
            r18 = r73
            r19 = r12
            r20 = r13
            r21 = r14
            r22 = r15
            r23 = r16
            r24 = r17
            r18.setTime(r19, r20, r21, r22, r23, r24)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 9
            r0 = r73
            r1 = r18
            char r49 = r0.charAt(r1)
            r18 = 46
            r0 = r49
            r1 = r18
            if (r0 != r1) goto L_0x0921
            int r18 = r48 + 11
            r0 = r75
            r1 = r18
            if (r0 >= r1) goto L_0x0992
            r18 = 0
            goto L_0x010d
        L_0x0921:
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 14
            r20 = 0
            r18.set(r19, r20)
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r19 = r48 + 9
            int r18 = r18 + r19
            r0 = r18
            r1 = r73
            r1.bp = r0
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r0 = r18
            r1 = r73
            r1.ch = r0
            r18 = 5
            r0 = r18
            r1 = r73
            r1.token = r0
            r18 = 90
            r0 = r49
            r1 = r18
            if (r0 != r1) goto L_0x098e
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            java.util.TimeZone r18 = r18.getTimeZone()
            int r18 = r18.getRawOffset()
            if (r18 == 0) goto L_0x098e
            r18 = 0
            java.lang.String[] r71 = java.util.TimeZone.getAvailableIDs(r18)
            r0 = r71
            int r0 = r0.length
            r18 = r0
            if (r18 <= 0) goto L_0x098e
            r18 = 0
            r18 = r71[r18]
            java.util.TimeZone r69 = java.util.TimeZone.getTimeZone(r18)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r0 = r18
            r1 = r69
            r0.setTimeZone(r1)
        L_0x098e:
            r18 = 1
            goto L_0x010d
        L_0x0992:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            r0 = r73
            r1 = r18
            char r27 = r0.charAt(r1)
            r18 = 48
            r0 = r27
            r1 = r18
            if (r0 < r1) goto L_0x09b4
            r18 = 57
            r0 = r27
            r1 = r18
            if (r0 <= r1) goto L_0x09b8
        L_0x09b4:
            r18 = 0
            goto L_0x010d
        L_0x09b8:
            int r54 = r27 + -48
            r53 = 1
            int r18 = r48 + 11
            r0 = r75
            r1 = r18
            if (r0 <= r1) goto L_0x09ee
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 11
            r0 = r73
            r1 = r18
            char r28 = r0.charAt(r1)
            r18 = 48
            r0 = r28
            r1 = r18
            if (r0 < r1) goto L_0x09ee
            r18 = 57
            r0 = r28
            r1 = r18
            if (r0 > r1) goto L_0x09ee
            int r18 = r54 * 10
            int r19 = r28 + -48
            int r54 = r18 + r19
            r53 = 2
        L_0x09ee:
            r18 = 2
            r0 = r53
            r1 = r18
            if (r0 != r1) goto L_0x0a20
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 12
            r0 = r73
            r1 = r18
            char r29 = r0.charAt(r1)
            r18 = 48
            r0 = r29
            r1 = r18
            if (r0 < r1) goto L_0x0a20
            r18 = 57
            r0 = r29
            r1 = r18
            if (r0 > r1) goto L_0x0a20
            int r18 = r54 * 10
            int r19 = r29 + -48
            int r54 = r18 + r19
            r53 = 3
        L_0x0a20:
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r19 = 14
            r0 = r18
            r1 = r19
            r2 = r54
            r0.set(r1, r2)
            r72 = 0
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            r0 = r73
            r1 = r18
            char r70 = r0.charAt(r1)
            r18 = 43
            r0 = r70
            r1 = r18
            if (r0 == r1) goto L_0x0a57
            r18 = 45
            r0 = r70
            r1 = r18
            if (r0 != r1) goto L_0x0b74
        L_0x0a57:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            int r18 = r18 + 1
            r0 = r73
            r1 = r18
            char r64 = r0.charAt(r1)
            r18 = 48
            r0 = r64
            r1 = r18
            if (r0 < r1) goto L_0x0a7d
            r18 = 49
            r0 = r64
            r1 = r18
            if (r0 <= r1) goto L_0x0a81
        L_0x0a7d:
            r18 = 0
            goto L_0x010d
        L_0x0a81:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            int r18 = r18 + 2
            r0 = r73
            r1 = r18
            char r65 = r0.charAt(r1)
            r18 = 48
            r0 = r65
            r1 = r18
            if (r0 < r1) goto L_0x0aa7
            r18 = 57
            r0 = r65
            r1 = r18
            if (r0 <= r1) goto L_0x0aab
        L_0x0aa7:
            r18 = 0
            goto L_0x010d
        L_0x0aab:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            int r18 = r18 + 3
            r0 = r73
            r1 = r18
            char r66 = r0.charAt(r1)
            r18 = 58
            r0 = r66
            r1 = r18
            if (r0 != r1) goto L_0x0b44
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r67 = r0.charAt(r1)
            r18 = 48
            r0 = r67
            r1 = r18
            if (r0 == r1) goto L_0x0aeb
            r18 = 0
            goto L_0x010d
        L_0x0aeb:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            int r18 = r18 + 5
            r0 = r73
            r1 = r18
            char r68 = r0.charAt(r1)
            r18 = 48
            r0 = r68
            r1 = r18
            if (r0 == r1) goto L_0x0b0d
            r18 = 0
            goto L_0x010d
        L_0x0b0d:
            r72 = 6
        L_0x0b0f:
            r0 = r73
            r1 = r70
            r2 = r64
            r3 = r65
            r0.setTimeZone(r1, r2, r3)
        L_0x0b1a:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r19 = r48 + 10
            int r19 = r19 + r53
            int r19 = r19 + r72
            int r18 = r18 + r19
            r0 = r73
            r1 = r18
            char r50 = r0.charAt(r1)
            r18 = 26
            r0 = r50
            r1 = r18
            if (r0 == r1) goto L_0x0bb2
            r18 = 34
            r0 = r50
            r1 = r18
            if (r0 == r1) goto L_0x0bb2
            r18 = 0
            goto L_0x010d
        L_0x0b44:
            r18 = 48
            r0 = r66
            r1 = r18
            if (r0 != r1) goto L_0x0b71
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r18 = r18 + r48
            int r18 = r18 + 10
            int r18 = r18 + r53
            int r18 = r18 + 4
            r0 = r73
            r1 = r18
            char r67 = r0.charAt(r1)
            r18 = 48
            r0 = r67
            r1 = r18
            if (r0 == r1) goto L_0x0b6e
            r18 = 0
            goto L_0x010d
        L_0x0b6e:
            r72 = 5
            goto L_0x0b0f
        L_0x0b71:
            r72 = 3
            goto L_0x0b0f
        L_0x0b74:
            r18 = 90
            r0 = r70
            r1 = r18
            if (r0 != r1) goto L_0x0b1a
            r72 = 1
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            java.util.TimeZone r18 = r18.getTimeZone()
            int r18 = r18.getRawOffset()
            if (r18 == 0) goto L_0x0b1a
            r18 = 0
            java.lang.String[] r71 = java.util.TimeZone.getAvailableIDs(r18)
            r0 = r71
            int r0 = r0.length
            r18 = r0
            if (r18 <= 0) goto L_0x0b1a
            r18 = 0
            r18 = r71[r18]
            java.util.TimeZone r69 = java.util.TimeZone.getTimeZone(r18)
            r0 = r73
            java.util.Calendar r0 = r0.calendar
            r18 = r0
            r0 = r18
            r1 = r69
            r0.setTimeZone(r1)
            goto L_0x0b1a
        L_0x0bb2:
            r0 = r73
            int r0 = r0.bp
            r18 = r0
            int r19 = r48 + 10
            int r19 = r19 + r53
            int r19 = r19 + r72
            int r18 = r18 + r19
            r0 = r18
            r1 = r73
            r1.bp = r0
            r0 = r73
            r1 = r18
            char r18 = r0.charAt(r1)
            r0 = r18
            r1 = r73
            r1.ch = r0
            r18 = 5
            r0 = r18
            r1 = r73
            r1.token = r0
            r18 = 1
            goto L_0x010d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JSONLexer.scanISO8601DateIfMatch(boolean, int):boolean");
    }

    /* access modifiers changed from: protected */
    public void setTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        int minute = ((m0 - '0') * 10) + (m1 - '0');
        int seconds = ((s0 - '0') * 10) + (s1 - '0');
        this.calendar.set(11, ((h0 - '0') * 10) + (h1 - '0'));
        this.calendar.set(12, minute);
        this.calendar.set(13, seconds);
    }

    /* access modifiers changed from: protected */
    public void setTimeZone(char timeZoneFlag, char t0, char t1) {
        int timeZoneOffset = (((t0 - '0') * 10) + (t1 - '0')) * 3600 * 1000;
        if (timeZoneFlag == '-') {
            timeZoneOffset = -timeZoneOffset;
        }
        if (this.calendar.getTimeZone().getRawOffset() != timeZoneOffset) {
            String[] timeZoneIDs = TimeZone.getAvailableIDs(timeZoneOffset);
            if (timeZoneIDs.length > 0) {
                this.calendar.setTimeZone(TimeZone.getTimeZone(timeZoneIDs[0]));
            }
        }
    }

    static boolean checkTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        if (h0 == '0') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 == '1') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 != '2' || h1 < '0' || h1 > '4') {
            return false;
        }
        if (m0 < '0' || m0 > '5') {
            if (!(m0 == '6' && m1 == '0')) {
                return false;
            }
        } else if (m1 < '0' || m1 > '9') {
            return false;
        }
        if (s0 < '0' || s0 > '5') {
            if (!(s0 == '6' && s1 == '0')) {
                return false;
            }
        } else if (s1 < '0' || s1 > '9') {
            return false;
        }
        return true;
    }

    private void setCalendar(char y0, char y1, char y2, char y3, char M0, char M1, char d0, char d1) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        int month = (((M0 - '0') * 10) + (M1 - '0')) - 1;
        int day = ((d0 - '0') * 10) + (d1 - '0');
        this.calendar.set(1, ((y0 - '0') * 1000) + ((y1 - '0') * 100) + ((y2 - '0') * 10) + (y3 - '0'));
        this.calendar.set(2, month);
        this.calendar.set(5, day);
    }

    static boolean checkDate(char y0, char y1, char y2, char y3, char M0, char M1, int d0, int d1) {
        if (y0 < '1' || y0 > '3' || y1 < '0' || y1 > '9' || y2 < '0' || y2 > '9' || y3 < '0' || y3 > '9') {
            return false;
        }
        if (M0 == '0') {
            if (M1 < '1' || M1 > '9') {
                return false;
            }
        } else if (M0 != '1') {
            return false;
        } else {
            if (!(M1 == '0' || M1 == '1' || M1 == '2')) {
                return false;
            }
        }
        if (d0 == 48) {
            if (d1 < 49 || d1 > 57) {
                return false;
            }
        } else if (d0 == 49 || d0 == 50) {
            if (d1 < 48 || d1 > 57) {
                return false;
            }
        } else if (d0 != 51) {
            return false;
        } else {
            if (!(d1 == 48 || d1 == 49)) {
                return false;
            }
        }
        return true;
    }

    public static final byte[] decodeFast(String chars, int offset, int charsLen) {
        int sepCnt;
        int sIx;
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx2 = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx2 < eIx && IA[chars.charAt(sIx2)] < 0) {
            sIx2++;
        }
        while (eIx > 0 && IA[chars.charAt(eIx)] < 0) {
            eIx--;
        }
        int pad = chars.charAt(eIx) == '=' ? chars.charAt(eIx + -1) == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx2) + 1;
        if (charsLen > 76) {
            sepCnt = (chars.charAt(76) == 13 ? cCnt / 78 : 0) << 1;
        } else {
            sepCnt = 0;
        }
        int len2 = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len2];
        int cc = 0;
        int eLen = (len2 / 3) * 3;
        int d = 0;
        int sIx3 = sIx2;
        while (d < eLen) {
            int sIx4 = sIx3 + 1;
            int sIx5 = sIx4 + 1;
            int sIx6 = sIx5 + 1;
            int sIx7 = sIx6 + 1;
            int i = (IA[chars.charAt(sIx3)] << 18) | (IA[chars.charAt(sIx4)] << 12) | (IA[chars.charAt(sIx5)] << 6) | IA[chars.charAt(sIx6)];
            int d2 = d + 1;
            bytes[d] = (byte) (i >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i;
            if (sepCnt > 0) {
                cc++;
                if (cc == 19) {
                    sIx = sIx7 + 2;
                    cc = 0;
                    d = d4;
                    sIx3 = sIx;
                }
            }
            sIx = sIx7;
            d = d4;
            sIx3 = sIx;
        }
        if (d < len2) {
            int i2 = 0;
            int j = 0;
            while (sIx3 <= eIx - pad) {
                i2 |= IA[chars.charAt(sIx3)] << (18 - (j * 6));
                j++;
                sIx3++;
            }
            int r = 16;
            while (d < len2) {
                int d5 = d + 1;
                bytes[d] = (byte) (i2 >> r);
                r -= 8;
                d = d5;
            }
        }
        int i3 = d;
        int i4 = sIx3;
        return bytes;
    }
}
