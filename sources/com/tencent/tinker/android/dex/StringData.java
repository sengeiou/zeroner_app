package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import java.io.UTFDataFormatException;

public class StringData extends Item<StringData> {
    public String value;

    public StringData(int offset, String value2) {
        super(offset);
        this.value = value2;
    }

    public int compareTo(StringData other) {
        return this.value.compareTo(other.value);
    }

    public int byteCountInDex() {
        try {
            return Leb128.unsignedLeb128Size(this.value.length()) + ((int) Mutf8.countBytes(this.value, true)) + 1;
        } catch (UTFDataFormatException e) {
            throw new DexException((Throwable) e);
        }
    }
}
