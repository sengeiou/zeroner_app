package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.DebugInfoItem;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class DebugInfoItemSectionPatchAlgorithm extends DexSectionPatchAlgorithm<DebugInfoItem> {
    private Section patchedDebugInfoItemSec = null;
    private TableOfContents.Section patchedDebugInfoItemTocSec = null;

    public DebugInfoItemSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedDebugInfoItemTocSec = patchedDex.getTableOfContents().debugInfos;
            this.patchedDebugInfoItemSec = patchedDex.openSection(this.patchedDebugInfoItemTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().debugInfos;
    }

    /* access modifiers changed from: protected */
    public DebugInfoItem nextItem(DexDataBuffer section) {
        return section.readDebugInfoItem();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(DebugInfoItem item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public DebugInfoItem adjustItem(AbstractIndexMap indexMap, DebugInfoItem item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(DebugInfoItem patchedItem) {
        this.patchedDebugInfoItemTocSec.size++;
        return this.patchedDebugInfoItemSec.writeDebugInfoItem(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapDebugInfoItemOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markDebugInfoItemDeleted(deletedOffset);
    }
}
