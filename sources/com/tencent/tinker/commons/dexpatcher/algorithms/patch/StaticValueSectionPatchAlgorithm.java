package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.EncodedValue;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class StaticValueSectionPatchAlgorithm extends DexSectionPatchAlgorithm<EncodedValue> {
    private Section patchedEncodedValueSec = null;
    private TableOfContents.Section patchedEncodedValueTocSec = null;

    public StaticValueSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedEncodedValueTocSec = patchedDex.getTableOfContents().encodedArrays;
            this.patchedEncodedValueSec = patchedDex.openSection(this.patchedEncodedValueTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().encodedArrays;
    }

    /* access modifiers changed from: protected */
    public EncodedValue nextItem(DexDataBuffer section) {
        return section.readEncodedArray();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(EncodedValue item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public EncodedValue adjustItem(AbstractIndexMap indexMap, EncodedValue item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(EncodedValue patchedItem) {
        this.patchedEncodedValueTocSec.size++;
        return this.patchedEncodedValueSec.writeEncodedArray(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapStaticValuesOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markStaticValuesDeleted(deletedOffset);
    }
}
