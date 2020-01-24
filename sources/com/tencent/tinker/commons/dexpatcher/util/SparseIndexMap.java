package com.tencent.tinker.commons.dexpatcher.util;

import com.tencent.tinker.android.utils.SparseBoolArray;
import com.tencent.tinker.android.utils.SparseIntArray;

public class SparseIndexMap extends AbstractIndexMap {
    private final SparseIntArray annotationOffsetsMap = new SparseIntArray();
    private final SparseIntArray annotationSetOffsetsMap = new SparseIntArray();
    private final SparseIntArray annotationSetRefListOffsetsMap = new SparseIntArray();
    private final SparseIntArray annotationsDirectoryOffsetsMap = new SparseIntArray();
    private final SparseIntArray classDataOffsetsMap = new SparseIntArray();
    private final SparseIntArray codeOffsetsMap = new SparseIntArray();
    private final SparseIntArray debugInfoItemOffsetsMap = new SparseIntArray();
    private final SparseBoolArray deletedAnnotationOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedAnnotationSetOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedAnnotationSetRefListOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedAnnotationsDirectoryOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedClassDataOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedCodeOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedDebugInfoItemOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedFieldIds = new SparseBoolArray();
    private final SparseBoolArray deletedMethodIds = new SparseBoolArray();
    private final SparseBoolArray deletedProtoIds = new SparseBoolArray();
    private final SparseBoolArray deletedStaticValuesOffsets = new SparseBoolArray();
    private final SparseBoolArray deletedStringIds = new SparseBoolArray();
    private final SparseBoolArray deletedTypeIds = new SparseBoolArray();
    private final SparseBoolArray deletedTypeListOffsets = new SparseBoolArray();
    private final SparseIntArray fieldIdsMap = new SparseIntArray();
    private final SparseIntArray methodIdsMap = new SparseIntArray();
    private final SparseIntArray protoIdsMap = new SparseIntArray();
    private final SparseIntArray staticValuesOffsetsMap = new SparseIntArray();
    private final SparseIntArray stringIdsMap = new SparseIntArray();
    private final SparseIntArray typeIdsMap = new SparseIntArray();
    private final SparseIntArray typeListOffsetsMap = new SparseIntArray();

    public void mapStringIds(int oldIndex, int newIndex) {
        this.stringIdsMap.put(oldIndex, newIndex);
    }

    public void markStringIdDeleted(int index) {
        if (index >= 0) {
            this.deletedStringIds.put(index, true);
        }
    }

    public void mapTypeIds(int oldIndex, int newIndex) {
        this.typeIdsMap.put(oldIndex, newIndex);
    }

    public void markTypeIdDeleted(int index) {
        if (index >= 0) {
            this.deletedTypeIds.put(index, true);
        }
    }

    public void mapProtoIds(int oldIndex, int newIndex) {
        this.protoIdsMap.put(oldIndex, newIndex);
    }

    public void markProtoIdDeleted(int index) {
        if (index >= 0) {
            this.deletedProtoIds.put(index, true);
        }
    }

    public void mapFieldIds(int oldIndex, int newIndex) {
        this.fieldIdsMap.put(oldIndex, newIndex);
    }

    public void markFieldIdDeleted(int index) {
        if (index >= 0) {
            this.deletedFieldIds.put(index, true);
        }
    }

    public void mapMethodIds(int oldIndex, int newIndex) {
        this.methodIdsMap.put(oldIndex, newIndex);
    }

    public void markMethodIdDeleted(int index) {
        if (index >= 0) {
            this.deletedMethodIds.put(index, true);
        }
    }

    public void mapTypeListOffset(int oldOffset, int newOffset) {
        this.typeListOffsetsMap.put(oldOffset, newOffset);
    }

    public void markTypeListDeleted(int offset) {
        if (offset >= 0) {
            this.deletedTypeListOffsets.put(offset, true);
        }
    }

    public void mapAnnotationOffset(int oldOffset, int newOffset) {
        this.annotationOffsetsMap.put(oldOffset, newOffset);
    }

    public void markAnnotationDeleted(int offset) {
        if (offset >= 0) {
            this.deletedAnnotationOffsets.put(offset, true);
        }
    }

    public void mapAnnotationSetOffset(int oldOffset, int newOffset) {
        this.annotationSetOffsetsMap.put(oldOffset, newOffset);
    }

    public void markAnnotationSetDeleted(int offset) {
        if (offset >= 0) {
            this.deletedAnnotationSetOffsets.put(offset, true);
        }
    }

    public void mapAnnotationSetRefListOffset(int oldOffset, int newOffset) {
        this.annotationSetRefListOffsetsMap.put(oldOffset, newOffset);
    }

    public void markAnnotationSetRefListDeleted(int offset) {
        if (offset >= 0) {
            this.deletedAnnotationSetRefListOffsets.put(offset, true);
        }
    }

    public void mapAnnotationsDirectoryOffset(int oldOffset, int newOffset) {
        this.annotationsDirectoryOffsetsMap.put(oldOffset, newOffset);
    }

    public void markAnnotationsDirectoryDeleted(int offset) {
        if (offset >= 0) {
            this.deletedAnnotationsDirectoryOffsets.put(offset, true);
        }
    }

    public void mapStaticValuesOffset(int oldOffset, int newOffset) {
        this.staticValuesOffsetsMap.put(oldOffset, newOffset);
    }

    public void markStaticValuesDeleted(int offset) {
        if (offset >= 0) {
            this.deletedStaticValuesOffsets.put(offset, true);
        }
    }

    public void mapClassDataOffset(int oldOffset, int newOffset) {
        this.classDataOffsetsMap.put(oldOffset, newOffset);
    }

    public void markClassDataDeleted(int offset) {
        if (offset >= 0) {
            this.deletedClassDataOffsets.put(offset, true);
        }
    }

    public void mapDebugInfoItemOffset(int oldOffset, int newOffset) {
        this.debugInfoItemOffsetsMap.put(oldOffset, newOffset);
    }

    public void markDebugInfoItemDeleted(int offset) {
        if (offset >= 0) {
            this.deletedDebugInfoItemOffsets.put(offset, true);
        }
    }

    public void mapCodeOffset(int oldOffset, int newOffset) {
        this.codeOffsetsMap.put(oldOffset, newOffset);
    }

    public void markCodeDeleted(int offset) {
        if (offset >= 0) {
            this.deletedCodeOffsets.put(offset, true);
        }
    }

    public int adjustStringIndex(int stringIndex) {
        int index = this.stringIdsMap.indexOfKey(stringIndex);
        if (index >= 0) {
            return this.stringIdsMap.valueAt(index);
        }
        if (stringIndex < 0 || !this.deletedStringIds.containsKey(stringIndex)) {
            return stringIndex;
        }
        return -1;
    }

    public int adjustTypeIdIndex(int typeIdIndex) {
        int index = this.typeIdsMap.indexOfKey(typeIdIndex);
        if (index >= 0) {
            return this.typeIdsMap.valueAt(index);
        }
        if (typeIdIndex < 0 || !this.deletedTypeIds.containsKey(typeIdIndex)) {
            return typeIdIndex;
        }
        return -1;
    }

    public int adjustProtoIdIndex(int protoIndex) {
        int index = this.protoIdsMap.indexOfKey(protoIndex);
        if (index >= 0) {
            return this.protoIdsMap.valueAt(index);
        }
        if (protoIndex < 0 || !this.deletedProtoIds.containsKey(protoIndex)) {
            return protoIndex;
        }
        return -1;
    }

    public int adjustFieldIdIndex(int fieldIndex) {
        int index = this.fieldIdsMap.indexOfKey(fieldIndex);
        if (index >= 0) {
            return this.fieldIdsMap.valueAt(index);
        }
        if (fieldIndex < 0 || !this.deletedFieldIds.containsKey(fieldIndex)) {
            return fieldIndex;
        }
        return -1;
    }

    public int adjustMethodIdIndex(int methodIndex) {
        int index = this.methodIdsMap.indexOfKey(methodIndex);
        if (index >= 0) {
            return this.methodIdsMap.valueAt(index);
        }
        if (methodIndex < 0 || !this.deletedMethodIds.containsKey(methodIndex)) {
            return methodIndex;
        }
        return -1;
    }

    public int adjustTypeListOffset(int typeListOffset) {
        int index = this.typeListOffsetsMap.indexOfKey(typeListOffset);
        if (index >= 0) {
            return this.typeListOffsetsMap.valueAt(index);
        }
        if (typeListOffset < 0 || !this.deletedTypeListOffsets.containsKey(typeListOffset)) {
            return typeListOffset;
        }
        return -1;
    }

    public int adjustAnnotationOffset(int annotationOffset) {
        int index = this.annotationOffsetsMap.indexOfKey(annotationOffset);
        if (index >= 0) {
            return this.annotationOffsetsMap.valueAt(index);
        }
        if (annotationOffset < 0 || !this.deletedAnnotationOffsets.containsKey(annotationOffset)) {
            return annotationOffset;
        }
        return -1;
    }

    public int adjustAnnotationSetOffset(int annotationSetOffset) {
        int index = this.annotationSetOffsetsMap.indexOfKey(annotationSetOffset);
        if (index >= 0) {
            return this.annotationSetOffsetsMap.valueAt(index);
        }
        if (annotationSetOffset < 0 || !this.deletedAnnotationSetOffsets.containsKey(annotationSetOffset)) {
            return annotationSetOffset;
        }
        return -1;
    }

    public int adjustAnnotationSetRefListOffset(int annotationSetRefListOffset) {
        int index = this.annotationSetRefListOffsetsMap.indexOfKey(annotationSetRefListOffset);
        if (index >= 0) {
            return this.annotationSetRefListOffsetsMap.valueAt(index);
        }
        if (annotationSetRefListOffset < 0 || !this.deletedAnnotationSetRefListOffsets.containsKey(annotationSetRefListOffset)) {
            return annotationSetRefListOffset;
        }
        return -1;
    }

    public int adjustAnnotationsDirectoryOffset(int annotationsDirectoryOffset) {
        int index = this.annotationsDirectoryOffsetsMap.indexOfKey(annotationsDirectoryOffset);
        if (index >= 0) {
            return this.annotationsDirectoryOffsetsMap.valueAt(index);
        }
        if (annotationsDirectoryOffset < 0 || !this.deletedAnnotationsDirectoryOffsets.containsKey(annotationsDirectoryOffset)) {
            return annotationsDirectoryOffset;
        }
        return -1;
    }

    public int adjustStaticValuesOffset(int staticValuesOffset) {
        int index = this.staticValuesOffsetsMap.indexOfKey(staticValuesOffset);
        if (index >= 0) {
            return this.staticValuesOffsetsMap.valueAt(index);
        }
        if (staticValuesOffset < 0 || !this.deletedStaticValuesOffsets.containsKey(staticValuesOffset)) {
            return staticValuesOffset;
        }
        return -1;
    }

    public int adjustClassDataOffset(int classDataOffset) {
        int index = this.classDataOffsetsMap.indexOfKey(classDataOffset);
        if (index >= 0) {
            return this.classDataOffsetsMap.valueAt(index);
        }
        if (classDataOffset < 0 || !this.deletedClassDataOffsets.containsKey(classDataOffset)) {
            return classDataOffset;
        }
        return -1;
    }

    public int adjustDebugInfoItemOffset(int debugInfoItemOffset) {
        int index = this.debugInfoItemOffsetsMap.indexOfKey(debugInfoItemOffset);
        if (index >= 0) {
            return this.debugInfoItemOffsetsMap.valueAt(index);
        }
        if (debugInfoItemOffset < 0 || !this.deletedDebugInfoItemOffsets.containsKey(debugInfoItemOffset)) {
            return debugInfoItemOffset;
        }
        return -1;
    }

    public int adjustCodeOffset(int codeOffset) {
        int index = this.codeOffsetsMap.indexOfKey(codeOffset);
        if (index >= 0) {
            return this.codeOffsetsMap.valueAt(index);
        }
        if (codeOffset < 0 || !this.deletedCodeOffsets.containsKey(codeOffset)) {
            return codeOffset;
        }
        return -1;
    }
}
