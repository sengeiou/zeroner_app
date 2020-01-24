package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.MethodId;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class MethodIdSectionPatchAlgorithm extends DexSectionPatchAlgorithm<MethodId> {
    private Section patchedMethodIdSec = null;
    private TableOfContents.Section patchedMethodIdTocSec = null;

    public MethodIdSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedMethodIdTocSec = patchedDex.getTableOfContents().methodIds;
            this.patchedMethodIdSec = patchedDex.openSection(this.patchedMethodIdTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().methodIds;
    }

    /* access modifiers changed from: protected */
    public MethodId nextItem(DexDataBuffer section) {
        return section.readMethodId();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(MethodId item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public MethodId adjustItem(AbstractIndexMap indexMap, MethodId item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(MethodId patchedItem) {
        this.patchedMethodIdTocSec.size++;
        return this.patchedMethodIdSec.writeMethodId(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldIndex != newIndex) {
            sparseIndexMap.mapMethodIds(oldIndex, newIndex);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markMethodIdDeleted(deletedIndex);
    }
}
