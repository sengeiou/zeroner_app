package com.tencent.tinker.android.dex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public final class TableOfContents {
    public static final short SECTION_TYPE_ANNOTATIONS = 8196;
    public static final short SECTION_TYPE_ANNOTATIONSDIRECTORIES = 8198;
    public static final short SECTION_TYPE_ANNOTATIONSETREFLISTS = 4098;
    public static final short SECTION_TYPE_ANNOTATIONSETS = 4099;
    public static final short SECTION_TYPE_CLASSDATA = 8192;
    public static final short SECTION_TYPE_CLASSDEFS = 6;
    public static final short SECTION_TYPE_CODES = 8193;
    public static final short SECTION_TYPE_DEBUGINFOS = 8195;
    public static final short SECTION_TYPE_ENCODEDARRAYS = 8197;
    public static final short SECTION_TYPE_FIELDIDS = 4;
    public static final short SECTION_TYPE_HEADER = 0;
    public static final short SECTION_TYPE_MAPLIST = 4096;
    public static final short SECTION_TYPE_METHODIDS = 5;
    public static final short SECTION_TYPE_PROTOIDS = 3;
    public static final short SECTION_TYPE_STRINGDATAS = 8194;
    public static final short SECTION_TYPE_STRINGIDS = 1;
    public static final short SECTION_TYPE_TYPEIDS = 2;
    public static final short SECTION_TYPE_TYPELISTS = 4097;
    public final Section annotationSetRefLists = new Section(4098, true);
    public final Section annotationSets = new Section(4099, true);
    public final Section annotations = new Section(8196, false);
    public final Section annotationsDirectories = new Section(8198, true);
    public int checksum;
    public final Section classDatas = new Section(8192, false);
    public final Section classDefs = new Section(6, true);
    public final Section codes = new Section(8193, true);
    public int dataOff;
    public int dataSize;
    public final Section debugInfos = new Section(8195, false);
    public final Section encodedArrays = new Section(8197, false);
    public final Section fieldIds = new Section(4, true);
    public int fileSize;
    public final Section header = new Section(0, true);
    public int linkOff;
    public int linkSize;
    public final Section mapList = new Section(4096, true);
    public final Section methodIds = new Section(5, true);
    public final Section protoIds = new Section(3, true);
    public final Section[] sections = {this.header, this.stringIds, this.typeIds, this.protoIds, this.fieldIds, this.methodIds, this.classDefs, this.mapList, this.typeLists, this.annotationSetRefLists, this.annotationSets, this.classDatas, this.codes, this.stringDatas, this.debugInfos, this.annotations, this.encodedArrays, this.annotationsDirectories};
    public byte[] signature = new byte[20];
    public final Section stringDatas = new Section(8194, false);
    public final Section stringIds = new Section(1, true);
    public final Section typeIds = new Section(2, true);
    public final Section typeLists = new Section(4097, true);

    public static class Section implements Comparable<Section> {
        public static final int UNDEF_INDEX = -1;
        public static final int UNDEF_OFFSET = -1;
        public int byteCount = 0;
        public boolean isElementFourByteAligned;
        public int off = -1;
        public int size = 0;
        public final short type;

        public static abstract class Item<T> implements Comparable<T> {
            public int off;

            public abstract int byteCountInDex();

            public Item(int off2) {
                this.off = off2;
            }

            public boolean equals(Object obj) {
                return compareTo(obj) == 0;
            }
        }

        public Section(int type2, boolean isElementFourByteAligned2) {
            this.type = (short) type2;
            this.isElementFourByteAligned = isElementFourByteAligned2;
            if (type2 == 0) {
                this.off = 0;
                this.size = 1;
                this.byteCount = 112;
            } else if (type2 == 4096) {
                this.size = 1;
            }
        }

        public boolean exists() {
            return this.size > 0;
        }

        private int remapTypeOrderId(int type2) {
            switch (type2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                    return 6;
                case 4096:
                    return 17;
                case 4097:
                    return 8;
                case 4098:
                    return 11;
                case 4099:
                    return 10;
                case 8192:
                    return 15;
                case 8193:
                    return 14;
                case 8194:
                    return 7;
                case 8195:
                    return 13;
                case 8196:
                    return 9;
                case 8197:
                    return 16;
                case 8198:
                    return 12;
                default:
                    throw new IllegalArgumentException("unknown section type: " + type2);
            }
        }

        public int compareTo(Section section) {
            if (this.off == section.off) {
                int remappedType = remapTypeOrderId(this.type);
                int otherRemappedType = remapTypeOrderId(section.type);
                if (remappedType == otherRemappedType) {
                    return 0;
                }
                if (remappedType >= otherRemappedType) {
                    return 1;
                }
                return -1;
            } else if (this.off < section.off) {
                return -1;
            } else {
                return 1;
            }
        }

        public String toString() {
            return String.format("Section[type=%#x,off=%#x,size=%#x]", new Object[]{Short.valueOf(this.type), Integer.valueOf(this.off), Integer.valueOf(this.size)});
        }
    }

    public Section getSectionByType(int type) {
        switch (type) {
            case 0:
                return this.header;
            case 1:
                return this.stringIds;
            case 2:
                return this.typeIds;
            case 3:
                return this.protoIds;
            case 4:
                return this.fieldIds;
            case 5:
                return this.methodIds;
            case 6:
                return this.classDefs;
            case 4096:
                return this.mapList;
            case 4097:
                return this.typeLists;
            case 4098:
                return this.annotationSetRefLists;
            case 4099:
                return this.annotationSets;
            case 8192:
                return this.classDatas;
            case 8193:
                return this.codes;
            case 8194:
                return this.stringDatas;
            case 8195:
                return this.debugInfos;
            case 8196:
                return this.annotations;
            case 8197:
                return this.encodedArrays;
            case 8198:
                return this.annotationsDirectories;
            default:
                throw new IllegalArgumentException("unknown section type: " + type);
        }
    }

    public void readFrom(Dex dex) throws IOException {
        readHeader(dex.openSection(this.header));
        readMap(dex.openSection(this.mapList.off));
        computeSizesFromOffsets();
    }

    private void readHeader(com.tencent.tinker.android.dex.Dex.Section headerIn) throws UnsupportedEncodingException {
        byte[] magic = headerIn.readByteArray(8);
        if (DexFormat.magicToApi(magic) != 13) {
            throw new DexException("Unexpected magic: " + Arrays.toString(magic));
        }
        this.checksum = headerIn.readInt();
        this.signature = headerIn.readByteArray(20);
        this.fileSize = headerIn.readInt();
        int headerSize = headerIn.readInt();
        if (headerSize != 112) {
            throw new DexException("Unexpected header: 0x" + Integer.toHexString(headerSize));
        }
        int endianTag = headerIn.readInt();
        if (endianTag != 305419896) {
            throw new DexException("Unexpected endian tag: 0x" + Integer.toHexString(endianTag));
        }
        this.linkSize = headerIn.readInt();
        this.linkOff = headerIn.readInt();
        this.mapList.off = headerIn.readInt();
        if (this.mapList.off == 0) {
            throw new DexException("Cannot merge dex files that do not contain a map");
        }
        this.stringIds.size = headerIn.readInt();
        this.stringIds.off = headerIn.readInt();
        this.typeIds.size = headerIn.readInt();
        this.typeIds.off = headerIn.readInt();
        this.protoIds.size = headerIn.readInt();
        this.protoIds.off = headerIn.readInt();
        this.fieldIds.size = headerIn.readInt();
        this.fieldIds.off = headerIn.readInt();
        this.methodIds.size = headerIn.readInt();
        this.methodIds.off = headerIn.readInt();
        this.classDefs.size = headerIn.readInt();
        this.classDefs.off = headerIn.readInt();
        this.dataSize = headerIn.readInt();
        this.dataOff = headerIn.readInt();
    }

    private void readMap(com.tencent.tinker.android.dex.Dex.Section in) throws IOException {
        int mapSize = in.readInt();
        Section previous = null;
        int i = 0;
        while (i < mapSize) {
            short type = in.readShort();
            in.readShort();
            Section section = getSection(type);
            int size = in.readInt();
            int offset = in.readInt();
            if ((section.size == 0 || section.size == size) && (section.off == -1 || section.off == offset)) {
                section.size = size;
                section.off = offset;
                if (previous == null || previous.off <= section.off) {
                    previous = section;
                    i++;
                } else {
                    throw new DexException("Map is unsorted at " + previous + ", " + section);
                }
            } else {
                throw new DexException("Unexpected map value for 0x" + Integer.toHexString(type));
            }
        }
        this.header.off = 0;
        Arrays.sort(this.sections);
        for (int i2 = 1; i2 < this.sections.length; i2++) {
            if (this.sections[i2].off == -1) {
                this.sections[i2].off = this.sections[i2 - 1].off;
            }
        }
    }

    public void computeSizesFromOffsets() {
        int end = this.fileSize;
        for (int i = this.sections.length - 1; i >= 0; i--) {
            Section section = this.sections[i];
            if (section.off != -1) {
                if (section.off > end) {
                    throw new DexException("Map is unsorted at " + section);
                }
                section.byteCount = end - section.off;
                end = section.off;
            }
        }
        this.dataOff = this.header.byteCount + this.stringIds.byteCount + this.typeIds.byteCount + this.protoIds.byteCount + this.fieldIds.byteCount + this.methodIds.byteCount + this.classDefs.byteCount;
        this.dataSize = this.fileSize - this.dataOff;
    }

    private Section getSection(short type) {
        Section[] sectionArr;
        for (Section section : this.sections) {
            if (section.type == type) {
                return section;
            }
        }
        throw new IllegalArgumentException("No such map item: " + type);
    }

    public void writeHeader(com.tencent.tinker.android.dex.Dex.Section out) throws IOException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        out.write(DexFormat.apiToMagic(13).getBytes("UTF-8"));
        out.writeInt(this.checksum);
        out.write(this.signature);
        out.writeInt(this.fileSize);
        out.writeInt(112);
        out.writeInt(DexFormat.ENDIAN_TAG);
        out.writeInt(this.linkSize);
        out.writeInt(this.linkOff);
        out.writeInt(this.mapList.off);
        out.writeInt(this.stringIds.size);
        out.writeInt(this.stringIds.exists() ? this.stringIds.off : 0);
        out.writeInt(this.typeIds.size);
        if (this.typeIds.exists()) {
            i = this.typeIds.off;
        } else {
            i = 0;
        }
        out.writeInt(i);
        out.writeInt(this.protoIds.size);
        if (this.protoIds.exists()) {
            i2 = this.protoIds.off;
        } else {
            i2 = 0;
        }
        out.writeInt(i2);
        out.writeInt(this.fieldIds.size);
        if (this.fieldIds.exists()) {
            i3 = this.fieldIds.off;
        } else {
            i3 = 0;
        }
        out.writeInt(i3);
        out.writeInt(this.methodIds.size);
        if (this.methodIds.exists()) {
            i4 = this.methodIds.off;
        } else {
            i4 = 0;
        }
        out.writeInt(i4);
        out.writeInt(this.classDefs.size);
        if (this.classDefs.exists()) {
            i5 = this.classDefs.off;
        }
        out.writeInt(i5);
        out.writeInt(this.dataSize);
        out.writeInt(this.dataOff);
    }

    public void writeMap(com.tencent.tinker.android.dex.Dex.Section out) throws IOException {
        Section[] sectionArr;
        int count = 0;
        for (Section section : this.sections) {
            if (section.exists()) {
                count++;
            }
        }
        out.writeInt(count);
        for (Section section2 : this.sections) {
            if (section2.exists()) {
                out.writeShort(section2.type);
                out.writeShort(0);
                out.writeInt(section2.size);
                out.writeInt(section2.off);
            }
        }
    }
}
