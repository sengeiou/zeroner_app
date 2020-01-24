package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.ClassDef;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class ClassDefSectionPatchAlgorithm extends DexSectionPatchAlgorithm<ClassDef> {
    private Section patchedClassDefSec = null;
    private TableOfContents.Section patchedClassDefTocSec = null;

    public ClassDefSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedClassDefTocSec = patchedDex.getTableOfContents().classDefs;
            this.patchedClassDefSec = patchedDex.openSection(this.patchedClassDefTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().classDefs;
    }

    /* access modifiers changed from: protected */
    public ClassDef nextItem(DexDataBuffer section) {
        return section.readClassDef();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(ClassDef item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public ClassDef adjustItem(AbstractIndexMap indexMap, ClassDef item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(ClassDef patchedItem) {
        this.patchedClassDefTocSec.size++;
        return this.patchedClassDefSec.writeClassDef(patchedItem);
    }
}
