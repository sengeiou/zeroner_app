package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public final class FieldId extends Item<FieldId> {
    public int declaringClassIndex;
    public int nameIndex;
    public int typeIndex;

    public FieldId(int off, int declaringClassIndex2, int typeIndex2, int nameIndex2) {
        super(off);
        this.declaringClassIndex = declaringClassIndex2;
        this.typeIndex = typeIndex2;
        this.nameIndex = nameIndex2;
    }

    public int compareTo(FieldId other) {
        if (this.declaringClassIndex != other.declaringClassIndex) {
            return CompareUtils.uCompare(this.declaringClassIndex, other.declaringClassIndex);
        }
        if (this.nameIndex != other.nameIndex) {
            return CompareUtils.uCompare(this.nameIndex, other.nameIndex);
        }
        return CompareUtils.uCompare(this.typeIndex, other.typeIndex);
    }

    public int byteCountInDex() {
        return 8;
    }
}
