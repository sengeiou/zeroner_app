package com.tencent.tinker.commons.dexpatcher.util;

import com.tencent.tinker.android.dex.Annotation;
import com.tencent.tinker.android.dex.AnnotationSet;
import com.tencent.tinker.android.dex.AnnotationSetRefList;
import com.tencent.tinker.android.dex.AnnotationsDirectory;
import com.tencent.tinker.android.dex.ClassData;
import com.tencent.tinker.android.dex.ClassData.Field;
import com.tencent.tinker.android.dex.ClassData.Method;
import com.tencent.tinker.android.dex.ClassDef;
import com.tencent.tinker.android.dex.Code;
import com.tencent.tinker.android.dex.Code.CatchHandler;
import com.tencent.tinker.android.dex.DebugInfoItem;
import com.tencent.tinker.android.dex.DexException;
import com.tencent.tinker.android.dex.EncodedValue;
import com.tencent.tinker.android.dex.EncodedValueCodec;
import com.tencent.tinker.android.dex.EncodedValueReader;
import com.tencent.tinker.android.dex.FieldId;
import com.tencent.tinker.android.dex.Leb128;
import com.tencent.tinker.android.dex.MethodId;
import com.tencent.tinker.android.dex.ProtoId;
import com.tencent.tinker.android.dex.TypeList;
import com.tencent.tinker.android.dex.util.ByteInput;
import com.tencent.tinker.android.dex.util.ByteOutput;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;

public abstract class AbstractIndexMap {

    private final class EncodedValueTransformer {
        private final ByteOutput out;

        EncodedValueTransformer(ByteOutput out2) {
            this.out = out2;
        }

        public void transform(EncodedValueReader reader) {
            int i = 0;
            switch (reader.peek()) {
                case 0:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 0, (long) reader.readByte());
                    return;
                case 2:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 2, (long) reader.readShort());
                    return;
                case 3:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 3, (long) reader.readChar());
                    return;
                case 4:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 4, (long) reader.readInt());
                    return;
                case 6:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 6, reader.readLong());
                    return;
                case 16:
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, 16, ((long) Float.floatToIntBits(reader.readFloat())) << 32);
                    return;
                case 17:
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, 17, Double.doubleToLongBits(reader.readDouble()));
                    return;
                case 23:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 23, (long) AbstractIndexMap.this.adjustStringIndex(reader.readString()));
                    return;
                case 24:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 24, (long) AbstractIndexMap.this.adjustTypeIdIndex(reader.readType()));
                    return;
                case 25:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 25, (long) AbstractIndexMap.this.adjustFieldIdIndex(reader.readField()));
                    return;
                case 26:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 26, (long) AbstractIndexMap.this.adjustMethodIdIndex(reader.readMethod()));
                    return;
                case 27:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 27, (long) AbstractIndexMap.this.adjustFieldIdIndex(reader.readEnum()));
                    return;
                case 28:
                    writeTypeAndArg(28, 0);
                    transformArray(reader);
                    return;
                case 29:
                    writeTypeAndArg(29, 0);
                    transformAnnotation(reader);
                    return;
                case 30:
                    reader.readNull();
                    writeTypeAndArg(30, 0);
                    return;
                case 31:
                    if (reader.readBoolean()) {
                        i = 1;
                    }
                    writeTypeAndArg(31, i);
                    return;
                default:
                    throw new DexException("Unexpected type: " + Integer.toHexString(reader.peek()));
            }
        }

        /* access modifiers changed from: private */
        public void transformAnnotation(EncodedValueReader reader) {
            int fieldCount = reader.readAnnotation();
            Leb128.writeUnsignedLeb128(this.out, AbstractIndexMap.this.adjustTypeIdIndex(reader.getAnnotationType()));
            Leb128.writeUnsignedLeb128(this.out, fieldCount);
            for (int i = 0; i < fieldCount; i++) {
                Leb128.writeUnsignedLeb128(this.out, AbstractIndexMap.this.adjustStringIndex(reader.readAnnotationName()));
                transform(reader);
            }
        }

        /* access modifiers changed from: private */
        public void transformArray(EncodedValueReader reader) {
            int size = reader.readArray();
            Leb128.writeUnsignedLeb128(this.out, size);
            for (int i = 0; i < size; i++) {
                transform(reader);
            }
        }

        private void writeTypeAndArg(int type, int arg) {
            this.out.writeByte((arg << 5) | type);
        }
    }

    public abstract int adjustAnnotationOffset(int i);

    public abstract int adjustAnnotationSetOffset(int i);

    public abstract int adjustAnnotationSetRefListOffset(int i);

    public abstract int adjustAnnotationsDirectoryOffset(int i);

    public abstract int adjustClassDataOffset(int i);

    public abstract int adjustCodeOffset(int i);

    public abstract int adjustDebugInfoItemOffset(int i);

    public abstract int adjustFieldIdIndex(int i);

    public abstract int adjustMethodIdIndex(int i);

    public abstract int adjustProtoIdIndex(int i);

    public abstract int adjustStaticValuesOffset(int i);

    public abstract int adjustStringIndex(int i);

    public abstract int adjustTypeIdIndex(int i);

    public abstract int adjustTypeListOffset(int i);

    public TypeList adjust(TypeList typeList) {
        if (typeList == TypeList.EMPTY) {
            return typeList;
        }
        short[] types = new short[typeList.types.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = (short) adjustTypeIdIndex(typeList.types[i]);
        }
        return new TypeList(typeList.off, types);
    }

    public MethodId adjust(MethodId methodId) {
        return new MethodId(methodId.off, adjustTypeIdIndex(methodId.declaringClassIndex), adjustProtoIdIndex(methodId.protoIndex), adjustStringIndex(methodId.nameIndex));
    }

    public FieldId adjust(FieldId fieldId) {
        return new FieldId(fieldId.off, adjustTypeIdIndex(fieldId.declaringClassIndex), adjustTypeIdIndex(fieldId.typeIndex), adjustStringIndex(fieldId.nameIndex));
    }

    public ProtoId adjust(ProtoId protoId) {
        return new ProtoId(protoId.off, adjustStringIndex(protoId.shortyIndex), adjustTypeIdIndex(protoId.returnTypeIndex), adjustTypeListOffset(protoId.parametersOffset));
    }

    public ClassDef adjust(ClassDef classDef) {
        return new ClassDef(classDef.off, adjustTypeIdIndex(classDef.typeIndex), classDef.accessFlags, adjustTypeIdIndex(classDef.supertypeIndex), adjustTypeListOffset(classDef.interfacesOffset), adjustStringIndex(classDef.sourceFileIndex), adjustAnnotationsDirectoryOffset(classDef.annotationsOffset), adjustClassDataOffset(classDef.classDataOffset), adjustStaticValuesOffset(classDef.staticValuesOffset));
    }

    public ClassData adjust(ClassData classData) {
        return new ClassData(classData.off, adjustFields(classData.staticFields), adjustFields(classData.instanceFields), adjustMethods(classData.directMethods), adjustMethods(classData.virtualMethods));
    }

    public Code adjust(Code code) {
        return new Code(code.off, code.registersSize, code.insSize, code.outsSize, adjustDebugInfoItemOffset(code.debugInfoOffset), adjustInstructions(code.instructions), code.tries, adjustCatchHandlers(code.catchHandlers));
    }

    private short[] adjustInstructions(short[] instructions) {
        return (instructions == null || instructions.length == 0) ? instructions : new InstructionTransformer(this).transform(instructions);
    }

    private CatchHandler[] adjustCatchHandlers(CatchHandler[] catchHandlers) {
        if (catchHandlers == null || catchHandlers.length == 0) {
            return catchHandlers;
        }
        CatchHandler[] adjustedCatchHandlers = new CatchHandler[catchHandlers.length];
        for (int i = 0; i < catchHandlers.length; i++) {
            CatchHandler catchHandler = catchHandlers[i];
            int typeIndexesCount = catchHandler.typeIndexes.length;
            int[] adjustedTypeIndexes = new int[typeIndexesCount];
            for (int j = 0; j < typeIndexesCount; j++) {
                adjustedTypeIndexes[j] = adjustTypeIdIndex(catchHandler.typeIndexes[j]);
            }
            adjustedCatchHandlers[i] = new CatchHandler(adjustedTypeIndexes, catchHandler.addresses, catchHandler.catchAllAddress, catchHandler.offset);
        }
        return adjustedCatchHandlers;
    }

    private Field[] adjustFields(Field[] fields) {
        Field[] adjustedFields = new Field[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            adjustedFields[i] = new Field(adjustFieldIdIndex(field.fieldIndex), field.accessFlags);
        }
        return adjustedFields;
    }

    private Method[] adjustMethods(Method[] methods) {
        Method[] adjustedMethods = new Method[methods.length];
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            adjustedMethods[i] = new Method(adjustMethodIdIndex(method.methodIndex), method.accessFlags, adjustCodeOffset(method.codeOffset));
        }
        return adjustedMethods;
    }

    public DebugInfoItem adjust(DebugInfoItem debugInfoItem) {
        return new DebugInfoItem(debugInfoItem.off, debugInfoItem.lineStart, adjustParameterNames(debugInfoItem.parameterNames), adjustDebugInfoItemSTM(debugInfoItem.infoSTM));
    }

    private int[] adjustParameterNames(int[] parameterNames) {
        int size = parameterNames.length;
        int[] adjustedParameterNames = new int[size];
        for (int i = 0; i < size; i++) {
            adjustedParameterNames[i] = adjustStringIndex(parameterNames[i]);
        }
        return adjustedParameterNames;
    }

    private byte[] adjustDebugInfoItemSTM(byte[] infoSTM) {
        ByteArrayInputStream bais = new ByteArrayInputStream(infoSTM);
        final ByteArrayInputStream baisRef = bais;
        ByteInput inAdapter = new ByteInput() {
            public byte readByte() {
                return (byte) (baisRef.read() & 255);
            }
        };
        ByteArrayOutputStream baos = new ByteArrayOutputStream(infoSTM.length + 512);
        final ByteArrayOutputStream baosRef = baos;
        ByteOutput outAdapter = new ByteOutput() {
            public void writeByte(int i) {
                baosRef.write(i);
            }
        };
        while (true) {
            int opcode = bais.read() & 255;
            baos.write(opcode);
            switch (opcode) {
                case 0:
                    return baos.toByteArray();
                case 1:
                    Leb128.writeUnsignedLeb128(outAdapter, Leb128.readUnsignedLeb128(inAdapter));
                    break;
                case 2:
                    Leb128.writeSignedLeb128(outAdapter, Leb128.readSignedLeb128(inAdapter));
                    break;
                case 3:
                case 4:
                    Leb128.writeUnsignedLeb128(outAdapter, Leb128.readUnsignedLeb128(inAdapter));
                    Leb128.writeUnsignedLeb128p1(outAdapter, adjustStringIndex(Leb128.readUnsignedLeb128p1(inAdapter)));
                    Leb128.writeUnsignedLeb128p1(outAdapter, adjustTypeIdIndex(Leb128.readUnsignedLeb128p1(inAdapter)));
                    if (opcode != 4) {
                        break;
                    } else {
                        Leb128.writeUnsignedLeb128p1(outAdapter, adjustStringIndex(Leb128.readUnsignedLeb128p1(inAdapter)));
                        break;
                    }
                case 5:
                case 6:
                    Leb128.writeUnsignedLeb128(outAdapter, Leb128.readUnsignedLeb128(inAdapter));
                    break;
                case 9:
                    Leb128.writeUnsignedLeb128p1(outAdapter, adjustStringIndex(Leb128.readUnsignedLeb128p1(inAdapter)));
                    break;
            }
        }
    }

    public EncodedValue adjust(EncodedValue encodedArray) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(encodedArray.data.length);
        new EncodedValueTransformer(new ByteOutput() {
            public void writeByte(int i) {
                baos.write(i);
            }
        }).transformArray(new EncodedValueReader(encodedArray, 28));
        return new EncodedValue(encodedArray.off, baos.toByteArray());
    }

    public Annotation adjust(Annotation annotation) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(annotation.encodedAnnotation.data.length);
        new EncodedValueTransformer(new ByteOutput() {
            public void writeByte(int i) {
                baos.write(i);
            }
        }).transformAnnotation(annotation.getReader());
        return new Annotation(annotation.off, annotation.visibility, new EncodedValue(annotation.encodedAnnotation.off, baos.toByteArray()));
    }

    public AnnotationSet adjust(AnnotationSet annotationSet) {
        int size = annotationSet.annotationOffsets.length;
        int[] adjustedAnnotationOffsets = new int[size];
        for (int i = 0; i < size; i++) {
            adjustedAnnotationOffsets[i] = adjustAnnotationOffset(annotationSet.annotationOffsets[i]);
        }
        return new AnnotationSet(annotationSet.off, adjustedAnnotationOffsets);
    }

    public AnnotationSetRefList adjust(AnnotationSetRefList annotationSetRefList) {
        int size = annotationSetRefList.annotationSetRefItems.length;
        int[] adjustedAnnotationSetRefItems = new int[size];
        for (int i = 0; i < size; i++) {
            adjustedAnnotationSetRefItems[i] = adjustAnnotationSetOffset(annotationSetRefList.annotationSetRefItems[i]);
        }
        return new AnnotationSetRefList(annotationSetRefList.off, adjustedAnnotationSetRefItems);
    }

    public AnnotationsDirectory adjust(AnnotationsDirectory annotationsDirectory) {
        int adjustedClassAnnotationsOffset = adjustAnnotationSetOffset(annotationsDirectory.classAnnotationsOffset);
        int[][] adjustedFieldAnnotations = (int[][]) Array.newInstance(Integer.TYPE, new int[]{annotationsDirectory.fieldAnnotations.length, 2});
        for (int i = 0; i < adjustedFieldAnnotations.length; i++) {
            adjustedFieldAnnotations[i][0] = adjustFieldIdIndex(annotationsDirectory.fieldAnnotations[i][0]);
            adjustedFieldAnnotations[i][1] = adjustAnnotationSetOffset(annotationsDirectory.fieldAnnotations[i][1]);
        }
        int[][] adjustedMethodAnnotations = (int[][]) Array.newInstance(Integer.TYPE, new int[]{annotationsDirectory.methodAnnotations.length, 2});
        for (int i2 = 0; i2 < adjustedMethodAnnotations.length; i2++) {
            adjustedMethodAnnotations[i2][0] = adjustMethodIdIndex(annotationsDirectory.methodAnnotations[i2][0]);
            adjustedMethodAnnotations[i2][1] = adjustAnnotationSetOffset(annotationsDirectory.methodAnnotations[i2][1]);
        }
        int[][] adjustedParameterAnnotations = (int[][]) Array.newInstance(Integer.TYPE, new int[]{annotationsDirectory.parameterAnnotations.length, 2});
        for (int i3 = 0; i3 < adjustedParameterAnnotations.length; i3++) {
            adjustedParameterAnnotations[i3][0] = adjustMethodIdIndex(annotationsDirectory.parameterAnnotations[i3][0]);
            adjustedParameterAnnotations[i3][1] = adjustAnnotationSetRefListOffset(annotationsDirectory.parameterAnnotations[i3][1]);
        }
        return new AnnotationsDirectory(annotationsDirectory.off, adjustedClassAnnotationsOffset, adjustedFieldAnnotations, adjustedMethodAnnotations, adjustedParameterAnnotations);
    }
}
