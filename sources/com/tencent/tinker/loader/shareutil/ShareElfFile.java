package com.tencent.tinker.loader.shareutil;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ShareElfFile implements Closeable {
    public static final int FILE_TYPE_ELF = 1;
    public static final int FILE_TYPE_ODEX = 0;
    public static final int FILE_TYPE_OTHERS = -1;
    public ElfHeader elfHeader = null;
    private final FileInputStream fis;
    public ProgramHeader[] programHeaders = null;
    public SectionHeader[] sectionHeaders = null;
    private final Map<String, SectionHeader> sectionNameToHeaderMap = new HashMap();

    public static class ElfHeader {
        public static final int EI_CLASS = 4;
        public static final int EI_DATA = 5;
        private static final int EI_NINDENT = 16;
        public static final int EI_VERSION = 6;
        public static final int ELFCLASS32 = 1;
        public static final int ELFCLASS64 = 2;
        public static final int ELFDATA2LSB = 1;
        public static final int ELFDATA2MSB = 2;
        public static final int ET_CORE = 4;
        public static final int ET_DYN = 3;
        public static final int ET_EXEC = 2;
        public static final int ET_HIPROC = 65535;
        public static final int ET_LOPROC = 65280;
        public static final int ET_NONE = 0;
        public static final int ET_REL = 1;
        public static final int EV_CURRENT = 1;
        public final short eEhSize;
        public final long eEntry;
        public final int eFlags;
        public final byte[] eIndent;
        public final short eMachine;
        public final short ePhEntSize;
        public final short ePhNum;
        public final long ePhOff;
        public final short eShEntSize;
        public final short eShNum;
        public final long eShOff;
        public final short eShStrNdx;
        public final short eType;
        public final int eVersion;

        private ElfHeader(FileChannel channel) throws IOException {
            this.eIndent = new byte[16];
            channel.position(0);
            channel.read(ByteBuffer.wrap(this.eIndent));
            if (this.eIndent[0] == Byte.MAX_VALUE && this.eIndent[1] == 69 && this.eIndent[2] == 76 && this.eIndent[3] == 70) {
                ShareElfFile.assertInRange(this.eIndent[4], 1, 2, "bad elf class: " + this.eIndent[4]);
                ShareElfFile.assertInRange(this.eIndent[5], 1, 2, "bad elf data encoding: " + this.eIndent[5]);
                ByteBuffer restBuffer = ByteBuffer.allocate(this.eIndent[4] == 1 ? 36 : 48);
                restBuffer.order(this.eIndent[5] == 1 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
                ShareElfFile.readUntilLimit(channel, restBuffer, "failed to read rest part of ehdr.");
                this.eType = restBuffer.getShort();
                this.eMachine = restBuffer.getShort();
                this.eVersion = restBuffer.getInt();
                ShareElfFile.assertInRange(this.eVersion, 1, 1, "bad elf version: " + this.eVersion);
                switch (this.eIndent[4]) {
                    case 1:
                        this.eEntry = (long) restBuffer.getInt();
                        this.ePhOff = (long) restBuffer.getInt();
                        this.eShOff = (long) restBuffer.getInt();
                        break;
                    case 2:
                        this.eEntry = restBuffer.getLong();
                        this.ePhOff = restBuffer.getLong();
                        this.eShOff = restBuffer.getLong();
                        break;
                    default:
                        throw new IOException("Unexpected elf class: " + this.eIndent[4]);
                }
                this.eFlags = restBuffer.getInt();
                this.eEhSize = restBuffer.getShort();
                this.ePhEntSize = restBuffer.getShort();
                this.ePhNum = restBuffer.getShort();
                this.eShEntSize = restBuffer.getShort();
                this.eShNum = restBuffer.getShort();
                this.eShStrNdx = restBuffer.getShort();
                return;
            }
            throw new IOException(String.format("bad elf magic: %x %x %x %x.", new Object[]{Byte.valueOf(this.eIndent[0]), Byte.valueOf(this.eIndent[1]), Byte.valueOf(this.eIndent[2]), Byte.valueOf(this.eIndent[3])}));
        }
    }

    public static class ProgramHeader {
        public static final int PF_R = 4;
        public static final int PF_W = 2;
        public static final int PF_X = 1;
        public static final int PT_DYNAMIC = 2;
        public static final int PT_HIPROC = Integer.MAX_VALUE;
        public static final int PT_INTERP = 3;
        public static final int PT_LOAD = 1;
        public static final int PT_LOPROC = 1879048192;
        public static final int PT_NOTE = 4;
        public static final int PT_NULL = 0;
        public static final int PT_PHDR = 6;
        public static final int PT_SHLIB = 5;
        public final long pAlign;
        public final long pFileSize;
        public final int pFlags;
        public final long pMemSize;
        public final long pOffset;
        public final long pPddr;
        public final int pType;
        public final long pVddr;

        private ProgramHeader(ByteBuffer buffer, int elfClass) throws IOException {
            switch (elfClass) {
                case 1:
                    this.pType = buffer.getInt();
                    this.pOffset = (long) buffer.getInt();
                    this.pVddr = (long) buffer.getInt();
                    this.pPddr = (long) buffer.getInt();
                    this.pFileSize = (long) buffer.getInt();
                    this.pMemSize = (long) buffer.getInt();
                    this.pFlags = buffer.getInt();
                    this.pAlign = (long) buffer.getInt();
                    return;
                case 2:
                    this.pType = buffer.getInt();
                    this.pFlags = buffer.getInt();
                    this.pOffset = buffer.getLong();
                    this.pVddr = buffer.getLong();
                    this.pPddr = buffer.getLong();
                    this.pFileSize = buffer.getLong();
                    this.pMemSize = buffer.getLong();
                    this.pAlign = buffer.getLong();
                    return;
                default:
                    throw new IOException("Unexpected elf class: " + elfClass);
            }
        }
    }

    public static class SectionHeader {
        public static final int SHF_ALLOC = 2;
        public static final int SHF_EXECINSTR = 4;
        public static final int SHF_MASKPROC = -268435456;
        public static final int SHF_WRITE = 1;
        public static final int SHN_ABS = 65521;
        public static final int SHN_COMMON = 65522;
        public static final int SHN_HIPROC = 65311;
        public static final int SHN_HIRESERVE = 65535;
        public static final int SHN_LOPROC = 65280;
        public static final int SHN_LORESERVE = 65280;
        public static final int SHN_UNDEF = 0;
        public static final int SHT_DYNAMIC = 6;
        public static final int SHT_DYNSYM = 11;
        public static final int SHT_HASH = 5;
        public static final int SHT_HIPROC = Integer.MAX_VALUE;
        public static final int SHT_HIUSER = -1;
        public static final int SHT_LOPROC = 1879048192;
        public static final int SHT_LOUSER = Integer.MIN_VALUE;
        public static final int SHT_NOBITS = 8;
        public static final int SHT_NOTE = 7;
        public static final int SHT_NULL = 0;
        public static final int SHT_PROGBITS = 1;
        public static final int SHT_REL = 9;
        public static final int SHT_RELA = 4;
        public static final int SHT_SHLIB = 10;
        public static final int SHT_STRTAB = 3;
        public static final int SHT_SYMTAB = 2;
        public final long shAddr;
        public final long shAddrAlign;
        public final long shEntSize;
        public final long shFlags;
        public final int shInfo;
        public final int shLink;
        public final int shName;
        public String shNameStr;
        public final long shOffset;
        public final long shSize;
        public final int shType;

        private SectionHeader(ByteBuffer buffer, int elfClass) throws IOException {
            switch (elfClass) {
                case 1:
                    this.shName = buffer.getInt();
                    this.shType = buffer.getInt();
                    this.shFlags = (long) buffer.getInt();
                    this.shAddr = (long) buffer.getInt();
                    this.shOffset = (long) buffer.getInt();
                    this.shSize = (long) buffer.getInt();
                    this.shLink = buffer.getInt();
                    this.shInfo = buffer.getInt();
                    this.shAddrAlign = (long) buffer.getInt();
                    this.shEntSize = (long) buffer.getInt();
                    break;
                case 2:
                    this.shName = buffer.getInt();
                    this.shType = buffer.getInt();
                    this.shFlags = buffer.getLong();
                    this.shAddr = buffer.getLong();
                    this.shOffset = buffer.getLong();
                    this.shSize = buffer.getLong();
                    this.shLink = buffer.getInt();
                    this.shInfo = buffer.getInt();
                    this.shAddrAlign = buffer.getLong();
                    this.shEntSize = buffer.getLong();
                    break;
                default:
                    throw new IOException("Unexpected elf class: " + elfClass);
            }
            this.shNameStr = null;
        }
    }

    public ShareElfFile(File file) throws IOException {
        SectionHeader[] sectionHeaderArr;
        this.fis = new FileInputStream(file);
        FileChannel channel = this.fis.getChannel();
        this.elfHeader = new ElfHeader(channel);
        ByteBuffer headerBuffer = ByteBuffer.allocate(128);
        headerBuffer.limit(this.elfHeader.ePhEntSize);
        headerBuffer.order(this.elfHeader.eIndent[5] == 1 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        channel.position(this.elfHeader.ePhOff);
        this.programHeaders = new ProgramHeader[this.elfHeader.ePhNum];
        for (int i = 0; i < this.programHeaders.length; i++) {
            readUntilLimit(channel, headerBuffer, "failed to read phdr.");
            this.programHeaders[i] = new ProgramHeader(headerBuffer, this.elfHeader.eIndent[4]);
        }
        channel.position(this.elfHeader.eShOff);
        headerBuffer.limit(this.elfHeader.eShEntSize);
        this.sectionHeaders = new SectionHeader[this.elfHeader.eShNum];
        for (int i2 = 0; i2 < this.sectionHeaders.length; i2++) {
            readUntilLimit(channel, headerBuffer, "failed to read shdr.");
            this.sectionHeaders[i2] = new SectionHeader(headerBuffer, this.elfHeader.eIndent[4]);
        }
        if (this.elfHeader.eShStrNdx > 0) {
            ByteBuffer shStrTab = getSection(this.sectionHeaders[this.elfHeader.eShStrNdx]);
            for (SectionHeader shdr : this.sectionHeaders) {
                shStrTab.position(shdr.shName);
                shdr.shNameStr = readCString(shStrTab);
                this.sectionNameToHeaderMap.put(shdr.shNameStr, shdr);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void assertInRange(int b, int lb, int ub, String errMsg) throws IOException {
        if (b < lb || b > ub) {
            throw new IOException(errMsg);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x005f A[SYNTHETIC, Splitter:B:38:0x005f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getFileTypeByMagic(java.io.File r7) throws java.io.IOException {
        /*
            r4 = 1
            r3 = 0
            r0 = 0
            r5 = 4
            byte[] r2 = new byte[r5]     // Catch:{ all -> 0x005c }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x005c }
            r1.<init>(r7)     // Catch:{ all -> 0x005c }
            r1.read(r2)     // Catch:{ all -> 0x0069 }
            r5 = 0
            byte r5 = r2[r5]     // Catch:{ all -> 0x0069 }
            r6 = 100
            if (r5 != r6) goto L_0x0030
            r5 = 1
            byte r5 = r2[r5]     // Catch:{ all -> 0x0069 }
            r6 = 101(0x65, float:1.42E-43)
            if (r5 != r6) goto L_0x0030
            r5 = 2
            byte r5 = r2[r5]     // Catch:{ all -> 0x0069 }
            r6 = 121(0x79, float:1.7E-43)
            if (r5 != r6) goto L_0x0030
            r5 = 3
            byte r5 = r2[r5]     // Catch:{ all -> 0x0069 }
            r6 = 10
            if (r5 != r6) goto L_0x0030
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ Throwable -> 0x0063 }
        L_0x002f:
            return r3
        L_0x0030:
            r3 = 0
            byte r3 = r2[r3]     // Catch:{ all -> 0x0069 }
            r5 = 127(0x7f, float:1.78E-43)
            if (r3 != r5) goto L_0x0053
            r3 = 1
            byte r3 = r2[r3]     // Catch:{ all -> 0x0069 }
            r5 = 69
            if (r3 != r5) goto L_0x0053
            r3 = 2
            byte r3 = r2[r3]     // Catch:{ all -> 0x0069 }
            r5 = 76
            if (r3 != r5) goto L_0x0053
            r3 = 3
            byte r3 = r2[r3]     // Catch:{ all -> 0x0069 }
            r5 = 70
            if (r3 != r5) goto L_0x0053
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ Throwable -> 0x0065 }
        L_0x0051:
            r3 = r4
            goto L_0x002f
        L_0x0053:
            r3 = -1
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ Throwable -> 0x005a }
            goto L_0x002f
        L_0x005a:
            r4 = move-exception
            goto L_0x002f
        L_0x005c:
            r3 = move-exception
        L_0x005d:
            if (r0 == 0) goto L_0x0062
            r0.close()     // Catch:{ Throwable -> 0x0067 }
        L_0x0062:
            throw r3
        L_0x0063:
            r4 = move-exception
            goto L_0x002f
        L_0x0065:
            r3 = move-exception
            goto L_0x0051
        L_0x0067:
            r4 = move-exception
            goto L_0x0062
        L_0x0069:
            r3 = move-exception
            r0 = r1
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.loader.shareutil.ShareElfFile.getFileTypeByMagic(java.io.File):int");
    }

    public static void readUntilLimit(FileChannel channel, ByteBuffer bufferOut, String errMsg) throws IOException {
        bufferOut.rewind();
        int bytesRead = channel.read(bufferOut);
        if (bytesRead != bufferOut.limit()) {
            throw new IOException(errMsg + " Rest bytes insufficient, expect to read " + bufferOut.limit() + " bytes but only " + bytesRead + " bytes were read.");
        }
        bufferOut.flip();
    }

    public static String readCString(ByteBuffer buffer) {
        byte[] rawBuffer = buffer.array();
        int begin = buffer.position();
        while (buffer.hasRemaining() && rawBuffer[buffer.position()] != 0) {
            buffer.position(buffer.position() + 1);
        }
        buffer.position(buffer.position() + 1);
        return new String(rawBuffer, begin, (buffer.position() - begin) - 1, Charset.forName("ASCII"));
    }

    public FileChannel getChannel() {
        return this.fis.getChannel();
    }

    public boolean is32BitElf() {
        return this.elfHeader.eIndent[4] == 1;
    }

    public ByteOrder getDataOrder() {
        return this.elfHeader.eIndent[5] == 1 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
    }

    public SectionHeader getSectionHeaderByName(String name) {
        return (SectionHeader) this.sectionNameToHeaderMap.get(name);
    }

    public ByteBuffer getSection(SectionHeader sectionHeader) throws IOException {
        ByteBuffer result = ByteBuffer.allocate((int) sectionHeader.shSize);
        this.fis.getChannel().position(sectionHeader.shOffset);
        readUntilLimit(this.fis.getChannel(), result, "failed to read section: " + sectionHeader.shNameStr);
        return result;
    }

    public ByteBuffer getSegment(ProgramHeader programHeader) throws IOException {
        ByteBuffer result = ByteBuffer.allocate((int) programHeader.pFileSize);
        this.fis.getChannel().position(programHeader.pOffset);
        readUntilLimit(this.fis.getChannel(), result, "failed to read segment (type: " + programHeader.pType + ").");
        return result;
    }

    public void close() throws IOException {
        this.fis.close();
        this.sectionNameToHeaderMap.clear();
        this.programHeaders = null;
        this.sectionHeaders = null;
    }
}
