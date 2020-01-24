package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public final class ClassDef extends Item<ClassDef> {
    public static final int NO_INDEX = -1;
    public static final int NO_OFFSET = 0;
    public int accessFlags;
    public int annotationsOffset;
    public int classDataOffset;
    public int interfacesOffset;
    public int sourceFileIndex;
    public int staticValuesOffset;
    public int supertypeIndex;
    public int typeIndex;

    public ClassDef(int off, int typeIndex2, int accessFlags2, int supertypeIndex2, int interfacesOffset2, int sourceFileIndex2, int annotationsOffset2, int classDataOffset2, int staticValuesOffset2) {
        super(off);
        this.typeIndex = typeIndex2;
        this.accessFlags = accessFlags2;
        this.supertypeIndex = supertypeIndex2;
        this.interfacesOffset = interfacesOffset2;
        this.sourceFileIndex = sourceFileIndex2;
        this.annotationsOffset = annotationsOffset2;
        this.classDataOffset = classDataOffset2;
        this.staticValuesOffset = staticValuesOffset2;
    }

    public int compareTo(ClassDef other) {
        int res = CompareUtils.uCompare(this.typeIndex, other.typeIndex);
        if (res != 0) {
            return res;
        }
        int res2 = CompareUtils.sCompare(this.accessFlags, other.accessFlags);
        if (res2 != 0) {
            return res2;
        }
        int res3 = CompareUtils.uCompare(this.supertypeIndex, other.supertypeIndex);
        if (res3 != 0) {
            return res3;
        }
        int res4 = CompareUtils.sCompare(this.interfacesOffset, other.interfacesOffset);
        if (res4 != 0) {
            return res4;
        }
        int res5 = CompareUtils.uCompare(this.sourceFileIndex, other.sourceFileIndex);
        if (res5 != 0) {
            return res5;
        }
        int res6 = CompareUtils.sCompare(this.annotationsOffset, other.annotationsOffset);
        if (res6 != 0) {
            return res6;
        }
        int res7 = CompareUtils.sCompare(this.classDataOffset, other.classDataOffset);
        if (res7 != 0) {
            return res7;
        }
        return CompareUtils.sCompare(this.staticValuesOffset, other.staticValuesOffset);
    }

    public int byteCountInDex() {
        return 32;
    }
}
