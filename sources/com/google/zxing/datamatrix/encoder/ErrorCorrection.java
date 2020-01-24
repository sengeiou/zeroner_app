package com.google.zxing.datamatrix.encoder;

import com.tencent.bugly.beta.tinker.TinkerReport;
import com.tencent.tinker.android.dx.instruction.Opcodes;

public final class ErrorCorrection {
    private static final int[] ALOG = new int[255];
    private static final int[][] FACTORS = {new int[]{228, 48, 15, 111, 62}, new int[]{23, 68, Opcodes.ADD_INT, Opcodes.LONG_TO_DOUBLE, 240, 92, TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE}, new int[]{28, 24, Opcodes.SHR_INT_2ADDR, Opcodes.ADD_FLOAT, Opcodes.XOR_INT_LIT8, 248, 116, 255, 110, 61}, new int[]{Opcodes.REM_DOUBLE, Opcodes.DOUBLE_TO_INT, 205, 12, Opcodes.XOR_LONG_2ADDR, Opcodes.MUL_FLOAT, 39, 245, 60, 97, 120}, new int[]{41, 153, 158, 91, 61, 42, Opcodes.INT_TO_CHAR, Opcodes.AND_INT_LIT16, 97, Opcodes.MUL_INT_2ADDR, 100, 242}, new int[]{156, 97, Opcodes.AND_LONG_2ADDR, TinkerReport.KEY_LOADED_EXCEPTION_DEX, 95, 9, 157, 119, Opcodes.DOUBLE_TO_INT, 45, 18, Opcodes.USHR_INT_2ADDR, 83, Opcodes.SHR_INT_2ADDR}, new int[]{83, Opcodes.SHL_LONG_2ADDR, 100, 39, Opcodes.SUB_LONG_2ADDR, 75, 66, 61, 241, Opcodes.AND_INT_LIT16, 109, 129, 94, TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE, Opcodes.SHR_INT_LIT8, 48, 90, Opcodes.SUB_LONG_2ADDR}, new int[]{15, Opcodes.SHL_LONG_2ADDR, 244, 9, 233, 71, Opcodes.MUL_FLOAT, 2, Opcodes.SUB_LONG_2ADDR, Opcodes.AND_LONG, 153, Opcodes.SUB_INT, TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK, 79, 108, 82, 27, Opcodes.DIV_DOUBLE, Opcodes.USHR_INT_2ADDR, Opcodes.SUB_DOUBLE}, new int[]{52, Opcodes.DIV_LONG_2ADDR, 88, 205, 109, 39, Opcodes.ADD_INT_2ADDR, 21, 155, Opcodes.USHR_LONG_2ADDR, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, Opcodes.XOR_INT_LIT8, 155, 21, 5, Opcodes.SUB_DOUBLE, TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE, 124, 12, 181, 184, 96, 50, Opcodes.OR_LONG_2ADDR}, new int[]{Opcodes.DIV_INT_LIT16, 231, 43, 97, 71, 96, 103, Opcodes.DIV_DOUBLE, 37, 151, 170, 53, 75, 34, 249, TinkerReport.KEY_APPLIED_DEXOPT_OTHER, 17, Opcodes.DOUBLE_TO_INT, 110, Opcodes.AND_INT_LIT16, Opcodes.INT_TO_BYTE, Opcodes.FLOAT_TO_LONG, 120, 151, 233, Opcodes.MUL_FLOAT, 93, 255}, new int[]{245, Opcodes.NEG_FLOAT, 242, Opcodes.MUL_INT_LIT8, Opcodes.INT_TO_FLOAT, 250, Opcodes.XOR_LONG, 181, 102, 120, 84, Opcodes.DIV_INT_2ADDR, Opcodes.REM_INT_LIT8, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, 80, 182, 229, 18, 2, 4, 68, 33, 101, Opcodes.FLOAT_TO_DOUBLE, 95, 119, 115, 44, Opcodes.REM_DOUBLE, 184, 59, 25, Opcodes.SHR_INT_LIT8, 98, 81, 112}, new int[]{77, Opcodes.OR_LONG_2ADDR, Opcodes.FLOAT_TO_DOUBLE, 31, 19, 38, 22, 153, 247, 105, 122, 2, 245, Opcodes.LONG_TO_FLOAT, 242, 8, Opcodes.REM_DOUBLE, 95, 100, 9, Opcodes.SUB_FLOAT, 105, Opcodes.OR_INT_LIT16, 111, 57, TinkerReport.KEY_APPLIED_DEXOPT_OTHER, 21, 1, TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK, 57, 54, 101, 248, 202, 69, 50, 150, Opcodes.SUB_INT_2ADDR, Opcodes.USHR_INT_LIT8, 5, 9, 5}, new int[]{245, Opcodes.LONG_TO_INT, Opcodes.SUB_DOUBLE, Opcodes.XOR_INT_LIT8, 96, 32, 117, 22, 238, Opcodes.LONG_TO_FLOAT, 238, 231, 205, Opcodes.SUB_LONG_2ADDR, 237, 87, Opcodes.REM_LONG_2ADDR, 106, 16, Opcodes.DIV_INT, 118, 23, 37, 90, 170, 205, Opcodes.INT_TO_DOUBLE, 88, 120, 100, 66, Opcodes.DOUBLE_TO_INT, Opcodes.USHR_INT_2ADDR, 240, 82, 44, Opcodes.ADD_INT_2ADDR, 87, Opcodes.ADD_LONG_2ADDR, Opcodes.DIV_INT, Opcodes.AND_LONG, Opcodes.REM_DOUBLE, 69, Opcodes.AND_INT_LIT16, 92, TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK, Opcodes.SHR_INT_LIT8, 19}, new int[]{Opcodes.REM_DOUBLE, 9, Opcodes.XOR_INT_LIT8, 238, 12, 17, Opcodes.REM_INT_LIT8, 208, 100, 29, Opcodes.REM_DOUBLE, 170, 230, Opcodes.AND_LONG_2ADDR, Opcodes.XOR_INT_LIT16, 235, 150, Opcodes.REM_LONG, 36, Opcodes.XOR_INT_LIT8, 38, 200, Opcodes.LONG_TO_INT, 54, 228, Opcodes.MUL_INT, Opcodes.MUL_INT_LIT8, 234, 117, 203, 29, 232, Opcodes.ADD_INT, 238, 22, 150, 201, 117, 62, 207, Opcodes.SHR_LONG, 13, Opcodes.FLOAT_TO_DOUBLE, 245, Opcodes.NEG_FLOAT, 67, 247, 28, 155, 43, 203, 107, 233, 53, Opcodes.INT_TO_SHORT, 46}, new int[]{242, 93, Opcodes.DIV_FLOAT, 50, Opcodes.ADD_INT, 210, 39, 118, 202, Opcodes.SUB_LONG_2ADDR, 201, Opcodes.MUL_LONG_2ADDR, Opcodes.INT_TO_SHORT, 108, Opcodes.SHR_LONG_2ADDR, 37, Opcodes.SHR_INT_2ADDR, 112, Opcodes.LONG_TO_DOUBLE, 230, 245, 63, Opcodes.USHR_LONG_2ADDR, Opcodes.DIV_LONG_2ADDR, 250, 106, Opcodes.SHR_INT_2ADDR, Opcodes.AND_INT_LIT8, Opcodes.REM_DOUBLE, 64, 114, 71, Opcodes.OR_LONG, 44, Opcodes.DIV_INT, 6, 27, Opcodes.MUL_INT_LIT8, 51, 63, 87, 10, 40, Opcodes.INT_TO_FLOAT, Opcodes.SUB_LONG_2ADDR, 17, Opcodes.SHL_LONG, 31, Opcodes.ADD_INT_2ADDR, 170, 4, 107, 232, 7, 94, Opcodes.ADD_FLOAT, Opcodes.SHL_INT_LIT8, 124, 86, 47, 11, 204}, new int[]{Opcodes.REM_INT_LIT8, 228, Opcodes.MUL_DOUBLE, 89, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, Opcodes.AND_INT, Opcodes.REM_LONG, 56, 89, 33, Opcodes.DIV_INT, 244, 154, 36, 73, Opcodes.NEG_FLOAT, Opcodes.AND_INT_LIT16, Opcodes.FLOAT_TO_LONG, 248, 180, 234, Opcodes.USHR_LONG_2ADDR, 158, Opcodes.SUB_INT_2ADDR, 68, 122, 93, Opcodes.AND_INT_LIT16, 15, Opcodes.AND_LONG, 227, 236, 66, Opcodes.DOUBLE_TO_LONG, 153, Opcodes.SHR_INT_2ADDR, 202, Opcodes.SUB_FLOAT, Opcodes.DIV_INT_2ADDR, 25, Opcodes.REM_INT_LIT8, 232, 96, 210, 231, Opcodes.FLOAT_TO_LONG, Opcodes.XOR_INT_LIT8, 239, 181, 241, 59, 52, Opcodes.SUB_DOUBLE, 25, 49, 232, Opcodes.DIV_INT_LIT16, Opcodes.MUL_LONG_2ADDR, 64, 54, 108, 153, Opcodes.LONG_TO_INT, 63, 96, 103, 82, Opcodes.USHR_INT_2ADDR}};
    private static final int[] FACTOR_SETS = {5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
    private static final int[] LOG = new int[256];
    private static final int MODULO_VALUE = 301;

    static {
        int p = 1;
        for (int i = 0; i < 255; i++) {
            ALOG[i] = p;
            LOG[p] = i;
            p *= 2;
            if (p >= 256) {
                p ^= 301;
            }
        }
    }

    private ErrorCorrection() {
    }

    public static String encodeECC200(String codewords, SymbolInfo symbolInfo) {
        if (codewords.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder sb = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        sb.append(codewords);
        int blockCount = symbolInfo.getInterleavedBlockCount();
        if (blockCount == 1) {
            sb.append(createECCBlock(codewords, symbolInfo.getErrorCodewords()));
        } else {
            sb.setLength(sb.capacity());
            int[] dataSizes = new int[blockCount];
            int[] errorSizes = new int[blockCount];
            int[] startPos = new int[blockCount];
            for (int i = 0; i < blockCount; i++) {
                dataSizes[i] = symbolInfo.getDataLengthForInterleavedBlock(i + 1);
                errorSizes[i] = symbolInfo.getErrorLengthForInterleavedBlock(i + 1);
                startPos[i] = 0;
                if (i > 0) {
                    startPos[i] = startPos[i - 1] + dataSizes[i];
                }
            }
            for (int block = 0; block < blockCount; block++) {
                StringBuilder temp = new StringBuilder(dataSizes[block]);
                for (int d = block; d < symbolInfo.getDataCapacity(); d += blockCount) {
                    temp.append(codewords.charAt(d));
                }
                String ecc = createECCBlock(temp.toString(), errorSizes[block]);
                int pos = 0;
                int e = block;
                while (e < errorSizes[block] * blockCount) {
                    int pos2 = pos + 1;
                    sb.setCharAt(symbolInfo.getDataCapacity() + e, ecc.charAt(pos));
                    e += blockCount;
                    pos = pos2;
                }
            }
        }
        return sb.toString();
    }

    private static String createECCBlock(CharSequence codewords, int numECWords) {
        return createECCBlock(codewords, 0, codewords.length(), numECWords);
    }

    private static String createECCBlock(CharSequence codewords, int start, int len, int numECWords) {
        int table = -1;
        int i = 0;
        while (true) {
            if (i >= FACTOR_SETS.length) {
                break;
            } else if (FACTOR_SETS[i] == numECWords) {
                table = i;
                break;
            } else {
                i++;
            }
        }
        if (table < 0) {
            throw new IllegalArgumentException("Illegal number of error correction codewords specified: " + numECWords);
        }
        int[] poly = FACTORS[table];
        char[] ecc = new char[numECWords];
        for (int i2 = 0; i2 < numECWords; i2++) {
            ecc[i2] = 0;
        }
        for (int i3 = start; i3 < start + len; i3++) {
            int m = ecc[numECWords - 1] ^ codewords.charAt(i3);
            for (int k = numECWords - 1; k > 0; k--) {
                if (m == 0 || poly[k] == 0) {
                    ecc[k] = ecc[k - 1];
                } else {
                    ecc[k] = (char) (ecc[k - 1] ^ ALOG[(LOG[m] + LOG[poly[k]]) % 255]);
                }
            }
            if (m == 0 || poly[0] == 0) {
                ecc[0] = 0;
            } else {
                ecc[0] = (char) ALOG[(LOG[m] + LOG[poly[0]]) % 255];
            }
        }
        char[] eccReversed = new char[numECWords];
        for (int i4 = 0; i4 < numECWords; i4++) {
            eccReversed[i4] = ecc[(numECWords - i4) - 1];
        }
        return String.valueOf(eccReversed);
    }
}
