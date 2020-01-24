package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public class DebugInfoItem extends Item<DebugInfoItem> {
    public static final byte DBG_ADVANCE_LINE = 2;
    public static final byte DBG_ADVANCE_PC = 1;
    public static final byte DBG_END_LOCAL = 5;
    public static final byte DBG_END_SEQUENCE = 0;
    public static final byte DBG_RESTART_LOCAL = 6;
    public static final byte DBG_SET_EPILOGUE_BEGIN = 8;
    public static final byte DBG_SET_FILE = 9;
    public static final byte DBG_SET_PROLOGUE_END = 7;
    public static final byte DBG_START_LOCAL = 3;
    public static final byte DBG_START_LOCAL_EXTENDED = 4;
    public byte[] infoSTM;
    public int lineStart;
    public int[] parameterNames;

    public DebugInfoItem(int off, int lineStart2, int[] parameterNames2, byte[] infoSTM2) {
        super(off);
        this.lineStart = lineStart2;
        this.parameterNames = parameterNames2;
        this.infoSTM = infoSTM2;
    }

    public int compareTo(DebugInfoItem o) {
        int origLineStart = this.lineStart;
        int destLineStart = o.lineStart;
        if (origLineStart != destLineStart) {
            return origLineStart - destLineStart;
        }
        int cmpRes = CompareUtils.uArrCompare(this.parameterNames, o.parameterNames);
        if (cmpRes == 0) {
            return CompareUtils.uArrCompare(this.infoSTM, o.infoSTM);
        }
        return cmpRes;
    }

    public int byteCountInDex() {
        int byteCount = Leb128.unsignedLeb128Size(this.lineStart) + Leb128.unsignedLeb128Size(this.parameterNames.length);
        for (int pn : this.parameterNames) {
            byteCount += Leb128.unsignedLeb128p1Size(pn);
        }
        return byteCount + (this.infoSTM.length * 1);
    }
}
