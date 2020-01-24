package com.tencent.tinker.android.dex;

import com.iwown.device_module.device_camera.exif.ExifInterface.ColorSpace;
import com.tencent.tinker.android.dex.ClassData.Method;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.android.dex.util.FileUtils;
import com.tencent.tinker.android.dx.util.Hex;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.zip.Adler32;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public final class Dex {
    private static final int CHECKSUM_OFFSET = 8;
    static final short[] EMPTY_SHORT_ARRAY = new short[0];
    private static final int SIGNATURE_OFFSET = 12;
    private final ClassDefTable classDefs;
    private ByteBuffer data;
    private final FieldIdTable fieldIds;
    private final MethodIdTable methodIds;
    private int nextSectionStart;
    private final ProtoIdTable protoIds;
    private byte[] signature;
    /* access modifiers changed from: private */
    public final StringTable strings;
    /* access modifiers changed from: private */
    public final TableOfContents tableOfContents;
    private final TypeIndexToDescriptorIndexTable typeIds;
    private final TypeIndexToDescriptorTable typeNames;

    private final class ClassDefIterable implements Iterable<ClassDef> {
        private ClassDefIterable() {
        }

        public Iterator<ClassDef> iterator() {
            return !Dex.this.tableOfContents.classDefs.exists() ? Collections.emptySet().iterator() : new ClassDefIterator();
        }
    }

    private final class ClassDefIterator implements Iterator<ClassDef> {
        private int count;
        private final Section in;

        private ClassDefIterator() {
            this.in = Dex.this.openSection(Dex.this.tableOfContents.classDefs);
            this.count = 0;
        }

        public boolean hasNext() {
            return this.count < Dex.this.tableOfContents.classDefs.size;
        }

        public ClassDef next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.count++;
            return this.in.readClassDef();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class ClassDefTable extends AbstractList<ClassDef> implements RandomAccess {
        private ClassDefTable() {
        }

        public ClassDef get(int index) {
            Dex.checkBounds(index, Dex.this.tableOfContents.classDefs.size);
            return Dex.this.openSection(Dex.this.tableOfContents.classDefs.off + (index * 32)).readClassDef();
        }

        public int size() {
            return Dex.this.tableOfContents.classDefs.size;
        }
    }

    private final class FieldIdTable extends AbstractList<FieldId> implements RandomAccess {
        private FieldIdTable() {
        }

        public FieldId get(int index) {
            Dex.checkBounds(index, Dex.this.tableOfContents.fieldIds.size);
            return Dex.this.openSection(Dex.this.tableOfContents.fieldIds.off + (index * 8)).readFieldId();
        }

        public int size() {
            return Dex.this.tableOfContents.fieldIds.size;
        }
    }

    private final class MethodIdTable extends AbstractList<MethodId> implements RandomAccess {
        private MethodIdTable() {
        }

        public MethodId get(int index) {
            Dex.checkBounds(index, Dex.this.tableOfContents.methodIds.size);
            return Dex.this.openSection(Dex.this.tableOfContents.methodIds.off + (index * 8)).readMethodId();
        }

        public int size() {
            return Dex.this.tableOfContents.methodIds.size;
        }
    }

    private final class ProtoIdTable extends AbstractList<ProtoId> implements RandomAccess {
        private ProtoIdTable() {
        }

        public ProtoId get(int index) {
            Dex.checkBounds(index, Dex.this.tableOfContents.protoIds.size);
            return Dex.this.openSection(Dex.this.tableOfContents.protoIds.off + (index * 12)).readProtoId();
        }

        public int size() {
            return Dex.this.tableOfContents.protoIds.size;
        }
    }

    public final class Section extends DexDataBuffer {
        private final String name;

        private Section(String name2, ByteBuffer data) {
            super(data);
            this.name = name2;
        }

        public StringData readStringData() {
            ensureFourBytesAligned(Dex.this.tableOfContents.stringDatas, false);
            return super.readStringData();
        }

        public TypeList readTypeList() {
            ensureFourBytesAligned(Dex.this.tableOfContents.typeLists, false);
            return super.readTypeList();
        }

        public FieldId readFieldId() {
            ensureFourBytesAligned(Dex.this.tableOfContents.fieldIds, false);
            return super.readFieldId();
        }

        public MethodId readMethodId() {
            ensureFourBytesAligned(Dex.this.tableOfContents.methodIds, false);
            return super.readMethodId();
        }

        public ProtoId readProtoId() {
            ensureFourBytesAligned(Dex.this.tableOfContents.protoIds, false);
            return super.readProtoId();
        }

        public ClassDef readClassDef() {
            ensureFourBytesAligned(Dex.this.tableOfContents.classDefs, false);
            return super.readClassDef();
        }

        public Code readCode() {
            ensureFourBytesAligned(Dex.this.tableOfContents.codes, false);
            return super.readCode();
        }

        public DebugInfoItem readDebugInfoItem() {
            ensureFourBytesAligned(Dex.this.tableOfContents.debugInfos, false);
            return super.readDebugInfoItem();
        }

        public ClassData readClassData() {
            ensureFourBytesAligned(Dex.this.tableOfContents.classDatas, false);
            return super.readClassData();
        }

        public Annotation readAnnotation() {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotations, false);
            return super.readAnnotation();
        }

        public AnnotationSet readAnnotationSet() {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotationSets, false);
            return super.readAnnotationSet();
        }

        public AnnotationSetRefList readAnnotationSetRefList() {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotationSetRefLists, false);
            return super.readAnnotationSetRefList();
        }

        public AnnotationsDirectory readAnnotationsDirectory() {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotationsDirectories, false);
            return super.readAnnotationsDirectory();
        }

        public EncodedValue readEncodedArray() {
            ensureFourBytesAligned(Dex.this.tableOfContents.encodedArrays, false);
            return super.readEncodedArray();
        }

        private void ensureFourBytesAligned(com.tencent.tinker.android.dex.TableOfContents.Section tocSec, boolean isFillWithZero) {
            if (!tocSec.isElementFourByteAligned) {
                return;
            }
            if (isFillWithZero) {
                alignToFourBytesWithZeroFill();
            } else {
                alignToFourBytes();
            }
        }

        public int writeStringData(StringData stringData) {
            ensureFourBytesAligned(Dex.this.tableOfContents.stringDatas, true);
            return super.writeStringData(stringData);
        }

        public int writeTypeList(TypeList typeList) {
            ensureFourBytesAligned(Dex.this.tableOfContents.typeLists, true);
            return super.writeTypeList(typeList);
        }

        public int writeFieldId(FieldId fieldId) {
            ensureFourBytesAligned(Dex.this.tableOfContents.fieldIds, true);
            return super.writeFieldId(fieldId);
        }

        public int writeMethodId(MethodId methodId) {
            ensureFourBytesAligned(Dex.this.tableOfContents.methodIds, true);
            return super.writeMethodId(methodId);
        }

        public int writeProtoId(ProtoId protoId) {
            ensureFourBytesAligned(Dex.this.tableOfContents.protoIds, true);
            return super.writeProtoId(protoId);
        }

        public int writeClassDef(ClassDef classDef) {
            ensureFourBytesAligned(Dex.this.tableOfContents.classDefs, true);
            return super.writeClassDef(classDef);
        }

        public int writeCode(Code code) {
            ensureFourBytesAligned(Dex.this.tableOfContents.codes, true);
            return super.writeCode(code);
        }

        public int writeDebugInfoItem(DebugInfoItem debugInfoItem) {
            ensureFourBytesAligned(Dex.this.tableOfContents.debugInfos, true);
            return super.writeDebugInfoItem(debugInfoItem);
        }

        public int writeClassData(ClassData classData) {
            ensureFourBytesAligned(Dex.this.tableOfContents.classDatas, true);
            return super.writeClassData(classData);
        }

        public int writeAnnotation(Annotation annotation) {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotations, true);
            return super.writeAnnotation(annotation);
        }

        public int writeAnnotationSet(AnnotationSet annotationSet) {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotationSets, true);
            return super.writeAnnotationSet(annotationSet);
        }

        public int writeAnnotationSetRefList(AnnotationSetRefList annotationSetRefList) {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotationSetRefLists, true);
            return super.writeAnnotationSetRefList(annotationSetRefList);
        }

        public int writeAnnotationsDirectory(AnnotationsDirectory annotationsDirectory) {
            ensureFourBytesAligned(Dex.this.tableOfContents.annotationsDirectories, true);
            return super.writeAnnotationsDirectory(annotationsDirectory);
        }

        public int writeEncodedArray(EncodedValue encodedValue) {
            ensureFourBytesAligned(Dex.this.tableOfContents.encodedArrays, true);
            return super.writeEncodedArray(encodedValue);
        }
    }

    private final class StringTable extends AbstractList<String> implements RandomAccess {
        private StringTable() {
        }

        public String get(int index) {
            Dex.checkBounds(index, Dex.this.tableOfContents.stringIds.size);
            return Dex.this.openSection(Dex.this.openSection(Dex.this.tableOfContents.stringIds.off + (index * 4)).readInt()).readStringData().value;
        }

        public int size() {
            return Dex.this.tableOfContents.stringIds.size;
        }
    }

    private final class TypeIndexToDescriptorIndexTable extends AbstractList<Integer> implements RandomAccess {
        private TypeIndexToDescriptorIndexTable() {
        }

        public Integer get(int index) {
            return Integer.valueOf(Dex.this.descriptorIndexFromTypeIndex(index));
        }

        public int size() {
            return Dex.this.tableOfContents.typeIds.size;
        }
    }

    private final class TypeIndexToDescriptorTable extends AbstractList<String> implements RandomAccess {
        private TypeIndexToDescriptorTable() {
        }

        public String get(int index) {
            return Dex.this.strings.get(Dex.this.descriptorIndexFromTypeIndex(index));
        }

        public int size() {
            return Dex.this.tableOfContents.typeIds.size;
        }
    }

    public Dex(byte[] data2) throws IOException {
        this(ByteBuffer.wrap(data2));
    }

    private Dex(ByteBuffer data2) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.strings = new StringTable();
        this.typeIds = new TypeIndexToDescriptorIndexTable();
        this.typeNames = new TypeIndexToDescriptorTable();
        this.protoIds = new ProtoIdTable();
        this.fieldIds = new FieldIdTable();
        this.methodIds = new MethodIdTable();
        this.classDefs = new ClassDefTable();
        this.nextSectionStart = 0;
        this.signature = null;
        this.data = data2;
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        this.tableOfContents.readFrom(this);
    }

    public Dex(int byteCount) {
        this.tableOfContents = new TableOfContents();
        this.strings = new StringTable();
        this.typeIds = new TypeIndexToDescriptorIndexTable();
        this.typeNames = new TypeIndexToDescriptorTable();
        this.protoIds = new ProtoIdTable();
        this.fieldIds = new FieldIdTable();
        this.methodIds = new MethodIdTable();
        this.classDefs = new ClassDefTable();
        this.nextSectionStart = 0;
        this.signature = null;
        this.data = ByteBuffer.wrap(new byte[byteCount]);
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        this.tableOfContents.fileSize = byteCount;
    }

    public Dex(InputStream in) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.strings = new StringTable();
        this.typeIds = new TypeIndexToDescriptorIndexTable();
        this.typeNames = new TypeIndexToDescriptorTable();
        this.protoIds = new ProtoIdTable();
        this.fieldIds = new FieldIdTable();
        this.methodIds = new MethodIdTable();
        this.classDefs = new ClassDefTable();
        this.nextSectionStart = 0;
        this.signature = null;
        loadFrom(in);
    }

    public Dex(InputStream in, int initSize) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.strings = new StringTable();
        this.typeIds = new TypeIndexToDescriptorIndexTable();
        this.typeNames = new TypeIndexToDescriptorTable();
        this.protoIds = new ProtoIdTable();
        this.fieldIds = new FieldIdTable();
        this.methodIds = new MethodIdTable();
        this.classDefs = new ClassDefTable();
        this.nextSectionStart = 0;
        this.signature = null;
        loadFrom(in, initSize);
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00d8 A[SYNTHETIC, Splitter:B:53:0x00d8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Dex(java.io.File r11) throws java.io.IOException {
        /*
            r10 = this;
            r8 = 0
            r10.<init>()
            com.tencent.tinker.android.dex.TableOfContents r7 = new com.tencent.tinker.android.dex.TableOfContents
            r7.<init>()
            r10.tableOfContents = r7
            com.tencent.tinker.android.dex.Dex$StringTable r7 = new com.tencent.tinker.android.dex.Dex$StringTable
            r7.<init>()
            r10.strings = r7
            com.tencent.tinker.android.dex.Dex$TypeIndexToDescriptorIndexTable r7 = new com.tencent.tinker.android.dex.Dex$TypeIndexToDescriptorIndexTable
            r7.<init>()
            r10.typeIds = r7
            com.tencent.tinker.android.dex.Dex$TypeIndexToDescriptorTable r7 = new com.tencent.tinker.android.dex.Dex$TypeIndexToDescriptorTable
            r7.<init>()
            r10.typeNames = r7
            com.tencent.tinker.android.dex.Dex$ProtoIdTable r7 = new com.tencent.tinker.android.dex.Dex$ProtoIdTable
            r7.<init>()
            r10.protoIds = r7
            com.tencent.tinker.android.dex.Dex$FieldIdTable r7 = new com.tencent.tinker.android.dex.Dex$FieldIdTable
            r7.<init>()
            r10.fieldIds = r7
            com.tencent.tinker.android.dex.Dex$MethodIdTable r7 = new com.tencent.tinker.android.dex.Dex$MethodIdTable
            r7.<init>()
            r10.methodIds = r7
            com.tencent.tinker.android.dex.Dex$ClassDefTable r7 = new com.tencent.tinker.android.dex.Dex$ClassDefTable
            r7.<init>()
            r10.classDefs = r7
            r7 = 0
            r10.nextSectionStart = r7
            r10.signature = r8
            if (r11 != 0) goto L_0x004c
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "file is null."
            r7.<init>(r8)
            throw r7
        L_0x004c:
            java.lang.String r7 = r11.getName()
            boolean r7 = com.tencent.tinker.android.dex.util.FileUtils.hasArchiveSuffix(r7)
            if (r7 == 0) goto L_0x00a6
            r5 = 0
            java.util.zip.ZipFile r6 = new java.util.zip.ZipFile     // Catch:{ all -> 0x0102 }
            r6.<init>(r11)     // Catch:{ all -> 0x0102 }
            java.lang.String r7 = "classes.dex"
            java.util.zip.ZipEntry r1 = r6.getEntry(r7)     // Catch:{ all -> 0x0084 }
            if (r1 == 0) goto L_0x008c
            r4 = 0
            java.io.InputStream r4 = r6.getInputStream(r1)     // Catch:{ all -> 0x007d }
            long r8 = r1.getSize()     // Catch:{ all -> 0x007d }
            int r7 = (int) r8     // Catch:{ all -> 0x007d }
            r10.loadFrom(r4, r7)     // Catch:{ all -> 0x007d }
            if (r4 == 0) goto L_0x0077
            r4.close()     // Catch:{ all -> 0x0084 }
        L_0x0077:
            if (r6 == 0) goto L_0x007c
            r6.close()     // Catch:{ Exception -> 0x00f6 }
        L_0x007c:
            return
        L_0x007d:
            r7 = move-exception
            if (r4 == 0) goto L_0x0083
            r4.close()     // Catch:{ all -> 0x0084 }
        L_0x0083:
            throw r7     // Catch:{ all -> 0x0084 }
        L_0x0084:
            r7 = move-exception
            r5 = r6
        L_0x0086:
            if (r5 == 0) goto L_0x008b
            r5.close()     // Catch:{ Exception -> 0x00f8 }
        L_0x008b:
            throw r7
        L_0x008c:
            com.tencent.tinker.android.dex.DexException r7 = new com.tencent.tinker.android.dex.DexException     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r8.<init>()     // Catch:{ all -> 0x0084 }
            java.lang.String r9 = "Expected classes.dex in "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ all -> 0x0084 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0084 }
            r7.<init>(r8)     // Catch:{ all -> 0x0084 }
            throw r7     // Catch:{ all -> 0x0084 }
        L_0x00a6:
            java.lang.String r7 = r11.getName()
            java.lang.String r8 = ".dex"
            boolean r7 = r7.endsWith(r8)
            if (r7 == 0) goto L_0x00dc
            r2 = 0
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00ce }
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00ce }
            r7.<init>(r11)     // Catch:{ Exception -> 0x00ce }
            r3.<init>(r7)     // Catch:{ Exception -> 0x00ce }
            long r8 = r11.length()     // Catch:{ Exception -> 0x00ff, all -> 0x00fc }
            int r7 = (int) r8     // Catch:{ Exception -> 0x00ff, all -> 0x00fc }
            r10.loadFrom(r3, r7)     // Catch:{ Exception -> 0x00ff, all -> 0x00fc }
            if (r3 == 0) goto L_0x007c
            r3.close()     // Catch:{ Exception -> 0x00cc }
            goto L_0x007c
        L_0x00cc:
            r7 = move-exception
            goto L_0x007c
        L_0x00ce:
            r0 = move-exception
        L_0x00cf:
            com.tencent.tinker.android.dex.DexException r7 = new com.tencent.tinker.android.dex.DexException     // Catch:{ all -> 0x00d5 }
            r7.<init>(r0)     // Catch:{ all -> 0x00d5 }
            throw r7     // Catch:{ all -> 0x00d5 }
        L_0x00d5:
            r7 = move-exception
        L_0x00d6:
            if (r2 == 0) goto L_0x00db
            r2.close()     // Catch:{ Exception -> 0x00fa }
        L_0x00db:
            throw r7
        L_0x00dc:
            com.tencent.tinker.android.dex.DexException r7 = new com.tencent.tinker.android.dex.DexException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "unknown output extension: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x00f6:
            r7 = move-exception
            goto L_0x007c
        L_0x00f8:
            r8 = move-exception
            goto L_0x008b
        L_0x00fa:
            r8 = move-exception
            goto L_0x00db
        L_0x00fc:
            r7 = move-exception
            r2 = r3
            goto L_0x00d6
        L_0x00ff:
            r0 = move-exception
            r2 = r3
            goto L_0x00cf
        L_0x0102:
            r7 = move-exception
            goto L_0x0086
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.android.dex.Dex.<init>(java.io.File):void");
    }

    /* access modifiers changed from: private */
    public static void checkBounds(int index, int length) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("index:" + index + ", length=" + length);
        }
    }

    private void loadFrom(InputStream in) throws IOException {
        loadFrom(in, 0);
    }

    private void loadFrom(InputStream in, int initSize) throws IOException {
        this.data = ByteBuffer.wrap(FileUtils.readStream(in, initSize));
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        this.tableOfContents.readFrom(this);
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.data.array());
        out.flush();
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x001e A[SYNTHETIC, Splitter:B:14:0x001e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeTo(java.io.File r6) throws java.io.IOException {
        /*
            r5 = this;
            r1 = 0
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0014 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0014 }
            r3.<init>(r6)     // Catch:{ Exception -> 0x0014 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0014 }
            r5.writeTo(r2)     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            if (r2 == 0) goto L_0x0013
            r2.close()     // Catch:{ Exception -> 0x0022 }
        L_0x0013:
            return
        L_0x0014:
            r0 = move-exception
        L_0x0015:
            com.tencent.tinker.android.dex.DexException r3 = new com.tencent.tinker.android.dex.DexException     // Catch:{ all -> 0x001b }
            r3.<init>(r0)     // Catch:{ all -> 0x001b }
            throw r3     // Catch:{ all -> 0x001b }
        L_0x001b:
            r3 = move-exception
        L_0x001c:
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ Exception -> 0x0024 }
        L_0x0021:
            throw r3
        L_0x0022:
            r3 = move-exception
            goto L_0x0013
        L_0x0024:
            r4 = move-exception
            goto L_0x0021
        L_0x0026:
            r3 = move-exception
            r1 = r2
            goto L_0x001c
        L_0x0029:
            r0 = move-exception
            r1 = r2
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.android.dex.Dex.writeTo(java.io.File):void");
    }

    public TableOfContents getTableOfContents() {
        return this.tableOfContents;
    }

    public Section openSection(int position) {
        if (position < 0 || position >= this.data.capacity()) {
            throw new IllegalArgumentException("position=" + position + " length=" + this.data.capacity());
        }
        ByteBuffer sectionData = this.data.duplicate();
        sectionData.order(ByteOrder.LITTLE_ENDIAN);
        sectionData.position(position);
        sectionData.limit(this.data.capacity());
        return new Section("temp-section", sectionData);
    }

    public Section openSection(com.tencent.tinker.android.dex.TableOfContents.Section tocSec) {
        int position = tocSec.off;
        if (position < 0 || position >= this.data.capacity()) {
            throw new IllegalArgumentException("position=" + position + " length=" + this.data.capacity());
        }
        ByteBuffer sectionData = this.data.duplicate();
        sectionData.order(ByteOrder.LITTLE_ENDIAN);
        sectionData.position(position);
        sectionData.limit(tocSec.byteCount + position);
        return new Section("section", sectionData);
    }

    public Section appendSection(int maxByteCount, String name) {
        int limit = this.nextSectionStart + maxByteCount;
        ByteBuffer sectionData = this.data.duplicate();
        sectionData.order(ByteOrder.LITTLE_ENDIAN);
        sectionData.position(this.nextSectionStart);
        sectionData.limit(limit);
        Section result = new Section(name, sectionData);
        this.nextSectionStart = limit;
        return result;
    }

    public int getLength() {
        return this.data.capacity();
    }

    public int getNextSectionStart() {
        return this.nextSectionStart;
    }

    public byte[] getBytes() {
        ByteBuffer data2 = this.data.duplicate();
        byte[] result = new byte[data2.capacity()];
        data2.position(0);
        data2.get(result);
        return result;
    }

    public List<String> strings() {
        return this.strings;
    }

    public List<Integer> typeIds() {
        return this.typeIds;
    }

    public List<String> typeNames() {
        return this.typeNames;
    }

    public List<ProtoId> protoIds() {
        return this.protoIds;
    }

    public List<FieldId> fieldIds() {
        return this.fieldIds;
    }

    public List<MethodId> methodIds() {
        return this.methodIds;
    }

    public List<ClassDef> classDefs() {
        return this.classDefs;
    }

    public Iterable<ClassDef> classDefIterable() {
        return new ClassDefIterable();
    }

    public ClassData readClassData(ClassDef classDef) {
        int offset = classDef.classDataOffset;
        if (offset != 0) {
            return openSection(offset).readClassData();
        }
        throw new IllegalArgumentException("offset == 0");
    }

    public Code readCode(Method method) {
        int offset = method.codeOffset;
        if (offset != 0) {
            return openSection(offset).readCode();
        }
        throw new IllegalArgumentException("offset == 0");
    }

    public byte[] computeSignature(boolean forceRecompute) {
        if (this.signature != null && !forceRecompute) {
            return this.signature;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
            byte[] buffer = new byte[8192];
            ByteBuffer data2 = this.data.duplicate();
            data2.limit(data2.capacity());
            data2.position(32);
            while (data2.hasRemaining()) {
                int count = Math.min(buffer.length, data2.remaining());
                data2.get(buffer, 0, count);
                digest.update(buffer, 0, count);
            }
            byte[] digest2 = digest.digest();
            this.signature = digest2;
            return digest2;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder strBuilder = new StringBuilder(bytes.length << 1);
        for (byte b : bytes) {
            strBuilder.append(Hex.u1(b));
        }
        return strBuilder.toString();
    }

    public int computeChecksum() throws IOException {
        Adler32 adler32 = new Adler32();
        byte[] buffer = new byte[8192];
        ByteBuffer data2 = this.data.duplicate();
        data2.limit(data2.capacity());
        data2.position(12);
        while (data2.hasRemaining()) {
            int count = Math.min(buffer.length, data2.remaining());
            data2.get(buffer, 0, count);
            adler32.update(buffer, 0, count);
        }
        return (int) adler32.getValue();
    }

    public void writeHashes() throws IOException {
        openSection(12).write(computeSignature(true));
        openSection(8).writeInt(computeChecksum());
    }

    public int nameIndexFromFieldIndex(int fieldIndex) {
        checkBounds(fieldIndex, this.tableOfContents.fieldIds.size);
        return this.data.getInt(this.tableOfContents.fieldIds.off + (fieldIndex * 8) + 2 + 2);
    }

    public int findStringIndex(String s) {
        return Collections.binarySearch(this.strings, s);
    }

    public int findTypeIndex(String descriptor) {
        return Collections.binarySearch(this.typeNames, descriptor);
    }

    public int findFieldIndex(FieldId fieldId) {
        return Collections.binarySearch(this.fieldIds, fieldId);
    }

    public int findMethodIndex(MethodId methodId) {
        return Collections.binarySearch(this.methodIds, methodId);
    }

    public int findClassDefIndexFromTypeIndex(int typeIndex) {
        checkBounds(typeIndex, this.tableOfContents.typeIds.size);
        if (!this.tableOfContents.classDefs.exists()) {
            return -1;
        }
        for (int i = 0; i < this.tableOfContents.classDefs.size; i++) {
            if (typeIndexFromClassDefIndex(i) == typeIndex) {
                return i;
            }
        }
        return -1;
    }

    public int typeIndexFromFieldIndex(int fieldIndex) {
        checkBounds(fieldIndex, this.tableOfContents.fieldIds.size);
        return this.data.getShort(this.tableOfContents.fieldIds.off + (fieldIndex * 8) + 2) & ColorSpace.UNCALIBRATED;
    }

    public int declaringClassIndexFromMethodIndex(int methodIndex) {
        checkBounds(methodIndex, this.tableOfContents.methodIds.size);
        return this.data.getShort(this.tableOfContents.methodIds.off + (methodIndex * 8)) & ColorSpace.UNCALIBRATED;
    }

    public int nameIndexFromMethodIndex(int methodIndex) {
        checkBounds(methodIndex, this.tableOfContents.methodIds.size);
        return this.data.getInt(this.tableOfContents.methodIds.off + (methodIndex * 8) + 2 + 2);
    }

    public short[] parameterTypeIndicesFromMethodIndex(int methodIndex) {
        checkBounds(methodIndex, this.tableOfContents.methodIds.size);
        int protoIndex = this.data.getShort(this.tableOfContents.methodIds.off + (methodIndex * 8) + 2) & 65535;
        checkBounds(protoIndex, this.tableOfContents.protoIds.size);
        int parametersOffset = this.data.getInt(this.tableOfContents.protoIds.off + (protoIndex * 12) + 4 + 4);
        if (parametersOffset == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int position = parametersOffset;
        int size = this.data.getInt(position);
        if (size <= 0) {
            throw new AssertionError("Unexpected parameter type list size: " + size);
        }
        int position2 = position + 4;
        short[] types = new short[size];
        for (int i = 0; i < size; i++) {
            types[i] = this.data.getShort(position2);
            position2 += 2;
        }
        return types;
    }

    public short[] parameterTypeIndicesFromMethodId(MethodId methodId) {
        int protoIndex = methodId.protoIndex & 65535;
        checkBounds(protoIndex, this.tableOfContents.protoIds.size);
        int parametersOffset = this.data.getInt(this.tableOfContents.protoIds.off + (protoIndex * 12) + 4 + 4);
        if (parametersOffset == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int position = parametersOffset;
        int size = this.data.getInt(position);
        if (size <= 0) {
            throw new AssertionError("Unexpected parameter type list size: " + size);
        }
        int position2 = position + 4;
        short[] types = new short[size];
        for (int i = 0; i < size; i++) {
            types[i] = this.data.getShort(position2);
            position2 += 2;
        }
        return types;
    }

    public int returnTypeIndexFromMethodIndex(int methodIndex) {
        checkBounds(methodIndex, this.tableOfContents.methodIds.size);
        int protoIndex = this.data.getShort(this.tableOfContents.methodIds.off + (methodIndex * 8) + 2) & 65535;
        checkBounds(protoIndex, this.tableOfContents.protoIds.size);
        return this.data.getInt(this.tableOfContents.protoIds.off + (protoIndex * 12) + 4);
    }

    public int descriptorIndexFromTypeIndex(int typeIndex) {
        checkBounds(typeIndex, this.tableOfContents.typeIds.size);
        return this.data.getInt(this.tableOfContents.typeIds.off + (typeIndex * 4));
    }

    public int typeIndexFromClassDefIndex(int classDefIndex) {
        checkBounds(classDefIndex, this.tableOfContents.classDefs.size);
        return this.data.getInt(this.tableOfContents.classDefs.off + (classDefIndex * 32));
    }

    public int annotationDirectoryOffsetFromClassDefIndex(int classDefIndex) {
        checkBounds(classDefIndex, this.tableOfContents.classDefs.size);
        return this.data.getInt(this.tableOfContents.classDefs.off + (classDefIndex * 32) + 4 + 4 + 4 + 4 + 4);
    }

    public short[] interfaceTypeIndicesFromClassDefIndex(int classDefIndex) {
        checkBounds(classDefIndex, this.tableOfContents.classDefs.size);
        int interfacesOffset = this.data.getInt(this.tableOfContents.classDefs.off + (classDefIndex * 32) + 4 + 4 + 4);
        if (interfacesOffset == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int position = interfacesOffset;
        int size = this.data.getInt(position);
        if (size <= 0) {
            throw new AssertionError("Unexpected interfaces list size: " + size);
        }
        int position2 = position + 4;
        short[] types = new short[size];
        for (int i = 0; i < size; i++) {
            types[i] = this.data.getShort(position2);
            position2 += 2;
        }
        return types;
    }

    public short[] interfaceTypeIndicesFromClassDef(ClassDef classDef) {
        int interfacesOffset = this.data.getInt(classDef.off + 4 + 4 + 4);
        if (interfacesOffset == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int position = interfacesOffset;
        int size = this.data.getInt(position);
        if (size <= 0) {
            throw new AssertionError("Unexpected interfaces list size: " + size);
        }
        int position2 = position + 4;
        short[] types = new short[size];
        for (int i = 0; i < size; i++) {
            types[i] = this.data.getShort(position2);
            position2 += 2;
        }
        return types;
    }
}
