package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public final class MethodId extends Item<MethodId> {
    public int declaringClassIndex;
    public int nameIndex;
    public int protoIndex;

    public MethodId(int off, int declaringClassIndex2, int protoIndex2, int nameIndex2) {
        super(off);
        this.declaringClassIndex = declaringClassIndex2;
        this.protoIndex = protoIndex2;
        this.nameIndex = nameIndex2;
    }

    public int compareTo(MethodId other) {
        if (this.declaringClassIndex != other.declaringClassIndex) {
            return CompareUtils.uCompare(this.declaringClassIndex, other.declaringClassIndex);
        }
        if (this.nameIndex != other.nameIndex) {
            return CompareUtils.uCompare(this.nameIndex, other.nameIndex);
        }
        return CompareUtils.uCompare(this.protoIndex, other.protoIndex);
    }

    public int byteCountInDex() {
        return 8;
    }
}
