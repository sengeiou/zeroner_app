package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class TypeIdSectionPatchAlgorithm extends DexSectionPatchAlgorithm<Integer> {
    private Section patchedTypeIdSec = null;
    private TableOfContents.Section patchedTypeIdTocSec = null;

    public TypeIdSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedTypeIdTocSec = patchedDex.getTableOfContents().typeIds;
            this.patchedTypeIdSec = patchedDex.openSection(this.patchedTypeIdTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().typeIds;
    }

    /* access modifiers changed from: protected */
    public Integer nextItem(DexDataBuffer section) {
        return Integer.valueOf(section.readInt());
    }

    /* access modifiers changed from: protected */
    public int getItemSize(Integer item) {
        return 4;
    }

    /* access modifiers changed from: protected */
    public Integer adjustItem(AbstractIndexMap indexMap, Integer item) {
        return Integer.valueOf(indexMap.adjustStringIndex(item.intValue()));
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(Integer patchedItem) {
        int off = this.patchedTypeIdSec.position();
        this.patchedTypeIdSec.writeInt(patchedItem.intValue());
        this.patchedTypeIdTocSec.size++;
        return off;
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldIndex != newIndex) {
            sparseIndexMap.mapTypeIds(oldIndex, newIndex);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markTypeIdDeleted(deletedIndex);
    }
}
