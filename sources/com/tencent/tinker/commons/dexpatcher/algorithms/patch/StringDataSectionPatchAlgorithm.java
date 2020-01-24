package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.StringData;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class StringDataSectionPatchAlgorithm extends DexSectionPatchAlgorithm<StringData> {
    private Section patchedStringDataSec = null;
    private TableOfContents.Section patchedStringDataTocSec = null;
    private Section patchedStringIdSec = null;
    private TableOfContents.Section patchedStringIdTocSec = null;

    public StringDataSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedStringDataTocSec = patchedDex.getTableOfContents().stringDatas;
            this.patchedStringIdTocSec = patchedDex.getTableOfContents().stringIds;
            this.patchedStringDataSec = patchedDex.openSection(this.patchedStringDataTocSec);
            this.patchedStringIdSec = patchedDex.openSection(this.patchedStringIdTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().stringDatas;
    }

    /* access modifiers changed from: protected */
    public StringData nextItem(DexDataBuffer section) {
        return section.readStringData();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(StringData item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(StringData patchedItem) {
        int off = this.patchedStringDataSec.writeStringData(patchedItem);
        this.patchedStringIdSec.writeInt(off);
        this.patchedStringDataTocSec.size++;
        this.patchedStringIdTocSec.size++;
        return off;
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldIndex != newIndex) {
            sparseIndexMap.mapStringIds(oldIndex, newIndex);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markStringIdDeleted(deletedIndex);
    }
}
