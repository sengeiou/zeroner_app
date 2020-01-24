package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.TableOfContents.Section;
import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;
import java.lang.Comparable;
import java.util.Arrays;

public abstract class DexSectionPatchAlgorithm<T extends Comparable<T>> {
    protected final Dex oldDex;
    private final SparseIndexMap oldToPatchedIndexMap;
    protected final DexPatchFile patchFile;

    /* access modifiers changed from: protected */
    public abstract int getItemSize(T t);

    /* access modifiers changed from: protected */
    public abstract Section getTocSection(Dex dex);

    /* access modifiers changed from: protected */
    public abstract T nextItem(DexDataBuffer dexDataBuffer);

    /* access modifiers changed from: protected */
    public abstract int writePatchedItem(T t);

    public DexSectionPatchAlgorithm(DexPatchFile patchFile2, Dex oldDex2, SparseIndexMap oldToPatchedIndexMap2) {
        this.patchFile = patchFile2;
        this.oldDex = oldDex2;
        this.oldToPatchedIndexMap = oldToPatchedIndexMap2;
    }

    /* access modifiers changed from: protected */
    public T adjustItem(AbstractIndexMap indexMap, T item) {
        return item;
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
    }

    private int[] readDeltaIndiciesOrOffsets(int count) {
        int[] result = new int[count];
        int lastVal = 0;
        for (int i = 0; i < count; i++) {
            lastVal += this.patchFile.getBuffer().readSleb128();
            result[i] = lastVal;
        }
        return result;
    }

    private int getItemOffsetOrIndex(int index, T item) {
        if (item instanceof Item) {
            return ((Item) item).off;
        }
        return index;
    }

    public void execute() {
        int[] deletedIndices = readDeltaIndiciesOrOffsets(this.patchFile.getBuffer().readUleb128());
        int[] addedIndices = readDeltaIndiciesOrOffsets(this.patchFile.getBuffer().readUleb128());
        int[] replacedIndices = readDeltaIndiciesOrOffsets(this.patchFile.getBuffer().readUleb128());
        Section tocSec = getTocSection(this.oldDex);
        Dex.Section oldSection = null;
        int oldItemCount = 0;
        if (tocSec.exists()) {
            oldSection = this.oldDex.openSection(tocSec);
            oldItemCount = tocSec.size;
        }
        doFullPatch(oldSection, oldItemCount, deletedIndices, addedIndices, replacedIndices);
    }

    private void doFullPatch(Dex.Section oldSection, int oldItemCount, int[] deletedIndices, int[] addedIndices, int[] replacedIndices) {
        int deletedItemCount = deletedIndices.length;
        int addedItemCount = addedIndices.length;
        int replacedItemCount = replacedIndices.length;
        int newItemCount = (oldItemCount + addedItemCount) - deletedItemCount;
        int deletedItemCounter = 0;
        int addActionCursor = 0;
        int replaceActionCursor = 0;
        int oldIndex = 0;
        int patchedIndex = 0;
        while (true) {
            if (oldIndex >= oldItemCount && patchedIndex >= newItemCount) {
                break;
            } else if (addActionCursor < addedItemCount && addedIndices[addActionCursor] == patchedIndex) {
                int writePatchedItem = writePatchedItem(nextItem(this.patchFile.getBuffer()));
                addActionCursor++;
                patchedIndex++;
            } else if (replaceActionCursor < replacedItemCount && replacedIndices[replaceActionCursor] == patchedIndex) {
                int writePatchedItem2 = writePatchedItem(nextItem(this.patchFile.getBuffer()));
                replaceActionCursor++;
                patchedIndex++;
            } else if (Arrays.binarySearch(deletedIndices, oldIndex) >= 0) {
                T skippedOldItem = nextItem(oldSection);
                markDeletedIndexOrOffset(this.oldToPatchedIndexMap, oldIndex, getItemOffsetOrIndex(oldIndex, skippedOldItem));
                oldIndex++;
                deletedItemCounter++;
            } else if (Arrays.binarySearch(replacedIndices, oldIndex) >= 0) {
                T skippedOldItem2 = nextItem(oldSection);
                markDeletedIndexOrOffset(this.oldToPatchedIndexMap, oldIndex, getItemOffsetOrIndex(oldIndex, skippedOldItem2));
                oldIndex++;
            } else if (oldIndex < oldItemCount) {
                T oldItem = adjustItem(this.oldToPatchedIndexMap, nextItem(oldSection));
                updateIndexOrOffset(this.oldToPatchedIndexMap, oldIndex, getItemOffsetOrIndex(oldIndex, oldItem), patchedIndex, writePatchedItem(oldItem));
                oldIndex++;
                patchedIndex++;
            }
        }
        if (addActionCursor != addedItemCount || deletedItemCounter != deletedItemCount || replaceActionCursor != replacedItemCount) {
            throw new IllegalStateException(String.format("bad patch operation sequence. addCounter: %d, addCount: %d, delCounter: %d, delCount: %d, replaceCounter: %d, replaceCount:%d", new Object[]{Integer.valueOf(addActionCursor), Integer.valueOf(addedItemCount), Integer.valueOf(deletedItemCounter), Integer.valueOf(deletedItemCount), Integer.valueOf(replaceActionCursor), Integer.valueOf(replacedItemCount)}));
        }
    }
}
