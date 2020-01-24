package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;
import com.tencent.tinker.android.dex.util.CompareUtils;

public final class Code extends Item<Code> {
    public CatchHandler[] catchHandlers;
    public int debugInfoOffset;
    public int insSize;
    public short[] instructions;
    public int outsSize;
    public int registersSize;
    public Try[] tries;

    public static class CatchHandler implements Comparable<CatchHandler> {
        public int[] addresses;
        public int catchAllAddress;
        public int offset;
        public int[] typeIndexes;

        public CatchHandler(int[] typeIndexes2, int[] addresses2, int catchAllAddress2, int offset2) {
            this.typeIndexes = typeIndexes2;
            this.addresses = addresses2;
            this.catchAllAddress = catchAllAddress2;
            this.offset = offset2;
        }

        public int compareTo(CatchHandler other) {
            int res = CompareUtils.sArrCompare(this.typeIndexes, other.typeIndexes);
            if (res != 0) {
                return res;
            }
            int res2 = CompareUtils.sArrCompare(this.addresses, other.addresses);
            if (res2 != 0) {
                return res2;
            }
            return CompareUtils.sCompare(this.catchAllAddress, other.catchAllAddress);
        }
    }

    public static class Try implements Comparable<Try> {
        public int catchHandlerIndex;
        public int instructionCount;
        public int startAddress;

        public Try(int startAddress2, int instructionCount2, int catchHandlerIndex2) {
            this.startAddress = startAddress2;
            this.instructionCount = instructionCount2;
            this.catchHandlerIndex = catchHandlerIndex2;
        }

        public int compareTo(Try other) {
            int res = CompareUtils.sCompare(this.startAddress, other.startAddress);
            if (res != 0) {
                return res;
            }
            int res2 = CompareUtils.sCompare(this.instructionCount, other.instructionCount);
            if (res2 != 0) {
                return res2;
            }
            return CompareUtils.sCompare(this.catchHandlerIndex, other.catchHandlerIndex);
        }
    }

    public Code(int off, int registersSize2, int insSize2, int outsSize2, int debugInfoOffset2, short[] instructions2, Try[] tries2, CatchHandler[] catchHandlers2) {
        super(off);
        this.registersSize = registersSize2;
        this.insSize = insSize2;
        this.outsSize = outsSize2;
        this.debugInfoOffset = debugInfoOffset2;
        this.instructions = instructions2;
        this.tries = tries2;
        this.catchHandlers = catchHandlers2;
    }

    public int compareTo(Code other) {
        int res = CompareUtils.sCompare(this.registersSize, other.registersSize);
        if (res != 0) {
            return res;
        }
        int res2 = CompareUtils.sCompare(this.insSize, other.insSize);
        if (res2 != 0) {
            return res2;
        }
        int res3 = CompareUtils.sCompare(this.outsSize, other.outsSize);
        if (res3 != 0) {
            return res3;
        }
        int res4 = CompareUtils.sCompare(this.debugInfoOffset, other.debugInfoOffset);
        if (res4 != 0) {
            return res4;
        }
        int res5 = CompareUtils.uArrCompare(this.instructions, other.instructions);
        if (res5 != 0) {
            return res5;
        }
        int res6 = CompareUtils.aArrCompare(this.tries, other.tries);
        if (res6 != 0) {
            return res6;
        }
        return CompareUtils.aArrCompare(this.catchHandlers, other.catchHandlers);
    }

    public int byteCountInDex() {
        CatchHandler[] catchHandlerArr;
        int insnsSize = this.instructions.length;
        int res = (insnsSize * 2) + 16;
        if (this.tries.length > 0) {
            if ((insnsSize & 1) == 1) {
                res += 2;
            }
            res = res + (this.tries.length * 8) + Leb128.unsignedLeb128Size(this.catchHandlers.length);
            for (CatchHandler catchHandler : this.catchHandlers) {
                int typeIdxAddrPairCount = catchHandler.typeIndexes.length;
                if (catchHandler.catchAllAddress != -1) {
                    res += Leb128.signedLeb128Size(-typeIdxAddrPairCount) + Leb128.unsignedLeb128Size(catchHandler.catchAllAddress);
                } else {
                    res += Leb128.signedLeb128Size(typeIdxAddrPairCount);
                }
                for (int i = 0; i < typeIdxAddrPairCount; i++) {
                    res += Leb128.unsignedLeb128Size(catchHandler.typeIndexes[i]) + Leb128.unsignedLeb128Size(catchHandler.addresses[i]);
                }
            }
        }
        return res;
    }
}
