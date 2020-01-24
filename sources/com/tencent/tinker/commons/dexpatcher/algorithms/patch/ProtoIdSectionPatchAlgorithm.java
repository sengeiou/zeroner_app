package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.ProtoId;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class ProtoIdSectionPatchAlgorithm extends DexSectionPatchAlgorithm<ProtoId> {
    private Section patchedProtoIdSec = null;
    private TableOfContents.Section patchedProtoIdTocSec = null;

    public ProtoIdSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedProtoIdTocSec = patchedDex.getTableOfContents().protoIds;
            this.patchedProtoIdSec = patchedDex.openSection(this.patchedProtoIdTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().protoIds;
    }

    /* access modifiers changed from: protected */
    public ProtoId nextItem(DexDataBuffer section) {
        return section.readProtoId();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(ProtoId item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public ProtoId adjustItem(AbstractIndexMap indexMap, ProtoId item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(ProtoId patchedItem) {
        this.patchedProtoIdTocSec.size++;
        return this.patchedProtoIdSec.writeProtoId(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldIndex != newIndex) {
            sparseIndexMap.mapProtoIds(oldIndex, newIndex);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markProtoIdDeleted(deletedIndex);
    }
}
