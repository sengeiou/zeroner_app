package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.TypeList;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class TypeListSectionPatchAlgorithm extends DexSectionPatchAlgorithm<TypeList> {
    private Section patchedTypeListSec = null;
    private TableOfContents.Section patchedTypeListTocSec = null;

    public TypeListSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedTypeListTocSec = patchedDex.getTableOfContents().typeLists;
            this.patchedTypeListSec = patchedDex.openSection(this.patchedTypeListTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().typeLists;
    }

    /* access modifiers changed from: protected */
    public TypeList nextItem(DexDataBuffer section) {
        return section.readTypeList();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(TypeList item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public TypeList adjustItem(AbstractIndexMap indexMap, TypeList item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(TypeList patchedItem) {
        this.patchedTypeListTocSec.size++;
        return this.patchedTypeListSec.writeTypeList(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapTypeListOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markTypeListDeleted(deletedOffset);
    }
}
