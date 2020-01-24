package com.google.zxing.maxicode.decoder;

import com.google.common.base.Ascii;
import com.google.zxing.common.DecoderResult;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import com.tencent.tinker.loader.shareutil.ShareElfFile.SectionHeader;
import java.text.DecimalFormat;
import java.text.NumberFormat;

final class DecodedBitStreamParser {
    private static final char ECI = '￺';
    private static final char FS = '\u001c';
    private static final char GS = '\u001d';
    private static final char LATCHA = '￷';
    private static final char LATCHB = '￸';
    private static final char LOCK = '￹';
    private static final NumberFormat NINE_DIGITS = new DecimalFormat("000000000");
    private static final char NS = '￻';
    private static final char PAD = '￼';
    private static final char RS = '\u001e';
    private static final String[] SETS = {"\nABCDEFGHIJKLMNOPQRSTUVWXYZ￺\u001c\u001d\u001e￻ ￼\"#$%&'()*+,-./0123456789:￱￲￳￴￸", "`abcdefghijklmnopqrstuvwxyz￺\u001c\u001d\u001e￻{￼}~;<=>?[\\]^_ ,./:@!|￼￵￶￼￰￲￳￴￷", "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚ￺\u001c\u001d\u001eÛÜÝÞßª¬±²³µ¹º¼½¾￷ ￹￳￴￸", "àáâãäåæçèéêëìíîïðñòóôõö÷øùú￺\u001c\u001d\u001e￻ûüýþÿ¡¨«¯°´·¸»¿￷ ￲￹￴￸", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a￺￼￼\u001b￻\u001c\u001d\u001e\u001f ¢£¤¥¦§©­®¶￷ ￲￳￹￸", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?"};
    private static final char SHIFTA = '￰';
    private static final char SHIFTB = '￱';
    private static final char SHIFTC = '￲';
    private static final char SHIFTD = '￳';
    private static final char SHIFTE = '￴';
    private static final char THREESHIFTA = '￶';
    private static final NumberFormat THREE_DIGITS = new DecimalFormat("000");
    private static final char TWOSHIFTA = '￵';

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] bytes, int mode) {
        String postcode;
        StringBuilder result = new StringBuilder(Opcodes.ADD_INT);
        switch (mode) {
            case 2:
            case 3:
                if (mode == 2) {
                    postcode = new DecimalFormat("0000000000".substring(0, getPostCode2Length(bytes))).format((long) getPostCode2(bytes));
                } else {
                    postcode = getPostCode3(bytes);
                }
                String country = THREE_DIGITS.format((long) getCountry(bytes));
                String service = THREE_DIGITS.format((long) getServiceClass(bytes));
                result.append(getMessage(bytes, 10, 84));
                if (!result.toString().startsWith("[)>\u001e01\u001d")) {
                    result.insert(0, postcode + GS + country + GS + service + GS);
                    break;
                } else {
                    result.insert(9, postcode + GS + country + GS + service + GS);
                    break;
                }
            case 4:
                result.append(getMessage(bytes, 1, 93));
                break;
            case 5:
                result.append(getMessage(bytes, 1, 77));
                break;
        }
        return new DecoderResult(bytes, result.toString(), null, String.valueOf(mode));
    }

    private static int getBit(int bit, byte[] bytes) {
        int bit2 = bit - 1;
        if ((bytes[bit2 / 6] & (1 << (5 - (bit2 % 6)))) == 0) {
            return 0;
        }
        return 1;
    }

    private static int getInt(byte[] bytes, byte[] x) {
        if (x.length == 0) {
            throw new IllegalArgumentException();
        }
        int val = 0;
        for (int i = 0; i < x.length; i++) {
            val += getBit(x[i], bytes) << ((x.length - i) - 1);
        }
        return val;
    }

    private static int getCountry(byte[] bytes) {
        return getInt(bytes, new byte[]{53, 54, 43, 44, Framer.STDIN_FRAME_PREFIX, 46, 47, 48, 37, 38});
    }

    private static int getServiceClass(byte[] bytes) {
        return getInt(bytes, new byte[]{55, 56, 57, 58, 59, 60, Framer.STDOUT_FRAME_PREFIX, Framer.STDERR_FRAME_PREFIX, 51, 52});
    }

    private static int getPostCode2Length(byte[] bytes) {
        return getInt(bytes, new byte[]{39, 40, 41, 42, Ascii.US, 32});
    }

    private static int getPostCode2(byte[] bytes) {
        return getInt(bytes, new byte[]{Framer.ENTER_FRAME_PREFIX, 34, 35, 36, Ascii.EM, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.CR, Ascii.SO, Ascii.SI, 16, 17, Ascii.DC2, 7, 8, 9, 10, Ascii.VT, Ascii.FF, 1, 2});
    }

    private static String getPostCode3(byte[] bytes) {
        return String.valueOf(new char[]{SETS[0].charAt(getInt(bytes, new byte[]{39, 40, 41, 42, Ascii.US, 32})), SETS[0].charAt(getInt(bytes, new byte[]{Framer.ENTER_FRAME_PREFIX, 34, 35, 36, Ascii.EM, Ascii.SUB})), SETS[0].charAt(getInt(bytes, new byte[]{Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, 19, Ascii.DC4})), SETS[0].charAt(getInt(bytes, new byte[]{Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.CR, Ascii.SO})), SETS[0].charAt(getInt(bytes, new byte[]{Ascii.SI, 16, 17, Ascii.DC2, 7, 8})), SETS[0].charAt(getInt(bytes, new byte[]{9, 10, Ascii.VT, Ascii.FF, 1, 2}))});
    }

    private static String getMessage(byte[] bytes, int start, int len) {
        int shift;
        StringBuilder sb = new StringBuilder();
        int shift2 = -1;
        int set = 0;
        int lastset = 0;
        int i = start;
        while (i < start + len) {
            char c = SETS[set].charAt(bytes[i]);
            switch (c) {
                case 65520:
                case SectionHeader.SHN_ABS /*65521*/:
                case SectionHeader.SHN_COMMON /*65522*/:
                case 65523:
                case 65524:
                    lastset = set;
                    set = c - SHIFTA;
                    shift = 1;
                    break;
                case 65525:
                    lastset = set;
                    set = 0;
                    shift = 2;
                    break;
                case 65526:
                    lastset = set;
                    set = 0;
                    shift = 3;
                    break;
                case 65527:
                    set = 0;
                    shift = -1;
                    break;
                case 65528:
                    set = 1;
                    shift = -1;
                    break;
                case 65529:
                    shift = -1;
                    break;
                case 65531:
                    int i2 = i + 1;
                    int i3 = i2 + 1;
                    int i4 = i3 + 1;
                    int i5 = i4 + 1;
                    i = i5 + 1;
                    sb.append(NINE_DIGITS.format((long) ((bytes[i2] << Ascii.CAN) + (bytes[i3] << Ascii.DC2) + (bytes[i4] << Ascii.FF) + (bytes[i5] << 6) + bytes[i])));
                    shift = shift2;
                    break;
                default:
                    sb.append(c);
                    shift = shift2;
                    break;
            }
            shift2 = shift - 1;
            if (shift == 0) {
                set = lastset;
            }
            i++;
        }
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == 65532) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
