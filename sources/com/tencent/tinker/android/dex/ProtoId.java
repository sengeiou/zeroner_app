package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public final class ProtoId extends Item<ProtoId> {
    public int parametersOffset;
    public int returnTypeIndex;
    public int shortyIndex;

    public ProtoId(int off, int shortyIndex2, int returnTypeIndex2, int parametersOffset2) {
        super(off);
        this.shortyIndex = shortyIndex2;
        this.returnTypeIndex = returnTypeIndex2;
        this.parametersOffset = parametersOffset2;
    }

    public int compareTo(ProtoId other) {
        int res = CompareUtils.uCompare(this.shortyIndex, other.shortyIndex);
        if (res != 0) {
            return res;
        }
        int res2 = CompareUtils.uCompare(this.returnTypeIndex, other.returnTypeIndex);
        if (res2 != 0) {
            return res2;
        }
        return CompareUtils.sCompare(this.parametersOffset, other.parametersOffset);
    }

    public int byteCountInDex() {
        return 12;
    }
}
