package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.FieldId;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class FieldIdSectionPatchAlgorithm extends DexSectionPatchAlgorithm<FieldId> {
    private Section patchedFieldIdSec = null;
    private TableOfContents.Section patchedFieldIdTocSec = null;

    public FieldIdSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedFieldIdTocSec = patchedDex.getTableOfContents().fieldIds;
            this.patchedFieldIdSec = patchedDex.openSection(this.patchedFieldIdTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().fieldIds;
    }

    /* access modifiers changed from: protected */
    public FieldId nextItem(DexDataBuffer section) {
        return section.readFieldId();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(FieldId item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public FieldId adjustItem(AbstractIndexMap indexMap, FieldId item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(FieldId patchedItem) {
        this.patchedFieldIdTocSec.size++;
        return this.patchedFieldIdSec.writeFieldId(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldIndex != newIndex) {
            sparseIndexMap.mapFieldIds(oldIndex, newIndex);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markFieldIdDeleted(deletedIndex);
    }
}
