package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Code;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class CodeSectionPatchAlgorithm extends DexSectionPatchAlgorithm<Code> {
    private Section patchedCodeSec = null;
    private TableOfContents.Section patchedCodeTocSec = null;

    public CodeSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedCodeTocSec = patchedDex.getTableOfContents().codes;
            this.patchedCodeSec = patchedDex.openSection(this.patchedCodeTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().codes;
    }

    /* access modifiers changed from: protected */
    public Code nextItem(DexDataBuffer section) {
        return section.readCode();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(Code item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public Code adjustItem(AbstractIndexMap indexMap, Code item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(Code patchedItem) {
        this.patchedCodeTocSec.size++;
        return this.patchedCodeSec.writeCode(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapCodeOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markCodeDeleted(deletedOffset);
    }
}
