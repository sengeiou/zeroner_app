package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public class AnnotationsDirectory extends Item<AnnotationsDirectory> {
    public int classAnnotationsOffset;
    public int[][] fieldAnnotations;
    public int[][] methodAnnotations;
    public int[][] parameterAnnotations;

    public AnnotationsDirectory(int off, int classAnnotationsOffset2, int[][] fieldAnnotations2, int[][] methodAnnotations2, int[][] parameterAnnotations2) {
        super(off);
        this.classAnnotationsOffset = classAnnotationsOffset2;
        this.fieldAnnotations = fieldAnnotations2;
        this.methodAnnotations = methodAnnotations2;
        this.parameterAnnotations = parameterAnnotations2;
    }

    public int compareTo(AnnotationsDirectory other) {
        if (this.classAnnotationsOffset != other.classAnnotationsOffset) {
            return CompareUtils.uCompare(this.classAnnotationsOffset, other.classAnnotationsOffset);
        }
        int fieldsSize = this.fieldAnnotations.length;
        int methodsSize = this.methodAnnotations.length;
        int parameterListSize = this.parameterAnnotations.length;
        int oFieldsSize = other.fieldAnnotations.length;
        int oMethodsSize = other.methodAnnotations.length;
        int oParameterListSize = other.parameterAnnotations.length;
        if (fieldsSize != oFieldsSize) {
            return CompareUtils.sCompare(fieldsSize, oFieldsSize);
        }
        if (methodsSize != oMethodsSize) {
            return CompareUtils.sCompare(methodsSize, oMethodsSize);
        }
        if (parameterListSize != oParameterListSize) {
            return CompareUtils.sCompare(parameterListSize, oParameterListSize);
        }
        for (int i = 0; i < fieldsSize; i++) {
            int fieldIdx = this.fieldAnnotations[i][0];
            int annotationOffset = this.fieldAnnotations[i][1];
            int othFieldIdx = other.fieldAnnotations[i][0];
            int othAnnotationOffset = other.fieldAnnotations[i][1];
            if (fieldIdx != othFieldIdx) {
                return CompareUtils.uCompare(fieldIdx, othFieldIdx);
            }
            if (annotationOffset != othAnnotationOffset) {
                return CompareUtils.sCompare(annotationOffset, othAnnotationOffset);
            }
        }
        for (int i2 = 0; i2 < methodsSize; i2++) {
            int methodIdx = this.methodAnnotations[i2][0];
            int annotationOffset2 = this.methodAnnotations[i2][1];
            int othMethodIdx = other.methodAnnotations[i2][0];
            int othAnnotationOffset2 = other.methodAnnotations[i2][1];
            if (methodIdx != othMethodIdx) {
                return CompareUtils.uCompare(methodIdx, othMethodIdx);
            }
            if (annotationOffset2 != othAnnotationOffset2) {
                return CompareUtils.sCompare(annotationOffset2, othAnnotationOffset2);
            }
        }
        for (int i3 = 0; i3 < parameterListSize; i3++) {
            int methodIdx2 = this.parameterAnnotations[i3][0];
            int annotationOffset3 = this.parameterAnnotations[i3][1];
            int othMethodIdx2 = other.parameterAnnotations[i3][0];
            int othAnnotationOffset3 = other.parameterAnnotations[i3][1];
            if (methodIdx2 != othMethodIdx2) {
                return CompareUtils.uCompare(methodIdx2, othMethodIdx2);
            }
            if (annotationOffset3 != othAnnotationOffset3) {
                return CompareUtils.sCompare(annotationOffset3, othAnnotationOffset3);
            }
        }
        return 0;
    }

    public int byteCountInDex() {
        return (((this.fieldAnnotations.length + this.methodAnnotations.length + this.parameterAnnotations.length) * 2) + 4) * 4;
    }
}
