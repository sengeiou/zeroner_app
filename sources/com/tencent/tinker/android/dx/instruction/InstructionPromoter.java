package com.tencent.tinker.android.dx.instruction;

import com.tencent.tinker.android.dex.DexException;
import com.tencent.tinker.android.dx.util.Hex;
import com.tencent.tinker.android.utils.SparseIntArray;

public final class InstructionPromoter extends InstructionVisitor {
    private final SparseIntArray addressMap = new SparseIntArray();
    private int currentPromotedAddress = 0;

    public InstructionPromoter() {
        super(null);
    }

    private void mapAddressIfNeeded(int currentAddress) {
        if (currentAddress != this.currentPromotedAddress) {
            this.addressMap.append(currentAddress, this.currentPromotedAddress);
        }
    }

    public int getPromotedAddress(int currentAddress) {
        int index = this.addressMap.indexOfKey(currentAddress);
        return index < 0 ? currentAddress : this.addressMap.valueAt(index);
    }

    public int getPromotedAddressCount() {
        return this.addressMap.size();
    }

    public void visitZeroRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case -1:
            case 0:
            case 14:
                this.currentPromotedAddress++;
                return;
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.currentPromotedAddress += 3;
                return;
            case 40:
                int relativeTarget = InstructionCodec.getTarget(target, this.currentPromotedAddress);
                if (relativeTarget == ((byte) relativeTarget)) {
                    this.currentPromotedAddress++;
                    return;
                } else if (relativeTarget != ((short) relativeTarget)) {
                    this.currentPromotedAddress += 3;
                    return;
                } else {
                    this.currentPromotedAddress += 2;
                    return;
                }
            case 41:
                int relativeTarget2 = InstructionCodec.getTarget(target, this.currentPromotedAddress);
                if (relativeTarget2 != ((short) relativeTarget2)) {
                    this.currentPromotedAddress += 3;
                    return;
                } else {
                    this.currentPromotedAddress += 2;
                    return;
                }
            case 42:
                this.currentPromotedAddress += 3;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitOneRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 29:
            case 30:
            case 39:
                this.currentPromotedAddress++;
                return;
            case 19:
            case 21:
            case 22:
            case 25:
            case 28:
            case 31:
            case 34:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
                this.currentPromotedAddress += 2;
                return;
            case 20:
            case 23:
            case 36:
            case 38:
            case 43:
            case 44:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.currentPromotedAddress += 3;
                return;
            case 24:
                this.currentPromotedAddress += 5;
                return;
            case 26:
                if (index > 65535) {
                    this.currentPromotedAddress += 3;
                    return;
                } else {
                    this.currentPromotedAddress += 2;
                    return;
                }
            case 27:
                this.currentPromotedAddress += 3;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitTwoRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case 1:
            case 4:
            case 7:
            case 33:
            case 123:
            case 124:
            case Opcodes.NEG_LONG /*125*/:
            case Opcodes.NOT_LONG /*126*/:
            case Opcodes.NEG_FLOAT /*127*/:
            case 128:
            case 129:
            case Opcodes.INT_TO_FLOAT /*130*/:
            case Opcodes.INT_TO_DOUBLE /*131*/:
            case Opcodes.LONG_TO_INT /*132*/:
            case Opcodes.LONG_TO_FLOAT /*133*/:
            case Opcodes.LONG_TO_DOUBLE /*134*/:
            case 135:
            case Opcodes.FLOAT_TO_LONG /*136*/:
            case Opcodes.FLOAT_TO_DOUBLE /*137*/:
            case Opcodes.DOUBLE_TO_INT /*138*/:
            case Opcodes.DOUBLE_TO_LONG /*139*/:
            case Opcodes.DOUBLE_TO_FLOAT /*140*/:
            case Opcodes.INT_TO_BYTE /*141*/:
            case Opcodes.INT_TO_CHAR /*142*/:
            case Opcodes.INT_TO_SHORT /*143*/:
            case Opcodes.ADD_INT_2ADDR /*176*/:
            case Opcodes.SUB_INT_2ADDR /*177*/:
            case Opcodes.MUL_INT_2ADDR /*178*/:
            case Opcodes.DIV_INT_2ADDR /*179*/:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case Opcodes.SHR_INT_2ADDR /*185*/:
            case Opcodes.USHR_INT_2ADDR /*186*/:
            case Opcodes.ADD_LONG_2ADDR /*187*/:
            case Opcodes.SUB_LONG_2ADDR /*188*/:
            case Opcodes.MUL_LONG_2ADDR /*189*/:
            case Opcodes.DIV_LONG_2ADDR /*190*/:
            case Opcodes.REM_LONG_2ADDR /*191*/:
            case Opcodes.AND_LONG_2ADDR /*192*/:
            case Opcodes.OR_LONG_2ADDR /*193*/:
            case Opcodes.XOR_LONG_2ADDR /*194*/:
            case Opcodes.SHL_LONG_2ADDR /*195*/:
            case Opcodes.SHR_LONG_2ADDR /*196*/:
            case Opcodes.USHR_LONG_2ADDR /*197*/:
            case Opcodes.ADD_FLOAT_2ADDR /*198*/:
            case Opcodes.SUB_FLOAT_2ADDR /*199*/:
            case 200:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
                this.currentPromotedAddress++;
                return;
            case 2:
            case 5:
            case 8:
                this.currentPromotedAddress += 2;
                return;
            case 3:
            case 6:
            case 9:
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.currentPromotedAddress += 3;
                return;
            case 32:
            case 35:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 208:
            case 209:
            case 210:
            case Opcodes.DIV_INT_LIT16 /*211*/:
            case Opcodes.REM_INT_LIT16 /*212*/:
            case Opcodes.AND_INT_LIT16 /*213*/:
            case Opcodes.OR_INT_LIT16 /*214*/:
            case Opcodes.XOR_INT_LIT16 /*215*/:
            case Opcodes.ADD_INT_LIT8 /*216*/:
            case Opcodes.RSUB_INT_LIT8 /*217*/:
            case Opcodes.MUL_INT_LIT8 /*218*/:
            case Opcodes.DIV_INT_LIT8 /*219*/:
            case Opcodes.REM_INT_LIT8 /*220*/:
            case Opcodes.AND_INT_LIT8 /*221*/:
            case Opcodes.OR_INT_LIT8 /*222*/:
            case Opcodes.XOR_INT_LIT8 /*223*/:
            case Opcodes.SHL_INT_LIT8 /*224*/:
            case Opcodes.SHR_INT_LIT8 /*225*/:
            case Opcodes.USHR_INT_LIT8 /*226*/:
                this.currentPromotedAddress += 2;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitThreeRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b, int c) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.currentPromotedAddress += 3;
                return;
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case Opcodes.ADD_INT /*144*/:
            case Opcodes.SUB_INT /*145*/:
            case Opcodes.MUL_INT /*146*/:
            case Opcodes.DIV_INT /*147*/:
            case Opcodes.REM_INT /*148*/:
            case Opcodes.AND_INT /*149*/:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case Opcodes.REM_LONG /*159*/:
            case Opcodes.AND_LONG /*160*/:
            case Opcodes.OR_LONG /*161*/:
            case Opcodes.XOR_LONG /*162*/:
            case Opcodes.SHL_LONG /*163*/:
            case Opcodes.SHR_LONG /*164*/:
            case Opcodes.USHR_LONG /*165*/:
            case Opcodes.ADD_FLOAT /*166*/:
            case Opcodes.SUB_FLOAT /*167*/:
            case Opcodes.MUL_FLOAT /*168*/:
            case Opcodes.DIV_FLOAT /*169*/:
            case 170:
            case Opcodes.ADD_DOUBLE /*171*/:
            case Opcodes.SUB_DOUBLE /*172*/:
            case Opcodes.MUL_DOUBLE /*173*/:
            case Opcodes.DIV_DOUBLE /*174*/:
            case Opcodes.REM_DOUBLE /*175*/:
                this.currentPromotedAddress += 2;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitFourRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b, int c, int d) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.currentPromotedAddress += 3;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitFiveRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b, int c, int d, int e) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.currentPromotedAddress += 3;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitRegisterRangeInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int registerCount) {
        mapAddressIfNeeded(currentAddress);
        switch (opcode) {
            case 37:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
                this.currentPromotedAddress += 3;
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitSparseSwitchPayloadInsn(int currentAddress, int opcode, int[] keys, int[] targets) {
        mapAddressIfNeeded(currentAddress);
        this.currentPromotedAddress += 2;
        this.currentPromotedAddress += keys.length * 2;
        this.currentPromotedAddress += targets.length * 2;
    }

    public void visitPackedSwitchPayloadInsn(int currentAddress, int opcode, int firstKey, int[] targets) {
        mapAddressIfNeeded(currentAddress);
        this.currentPromotedAddress += 4;
        this.currentPromotedAddress += targets.length * 2;
    }

    public void visitFillArrayDataPayloadInsn(int currentAddress, int opcode, Object data, int size, int elementWidth) {
        mapAddressIfNeeded(currentAddress);
        this.currentPromotedAddress += 4;
        switch (elementWidth) {
            case 1:
                int length = ((byte[]) data).length;
                this.currentPromotedAddress += (length >> 1) + (length & 1);
                return;
            case 2:
                this.currentPromotedAddress += ((short[]) data).length * 1;
                return;
            case 4:
                this.currentPromotedAddress += ((int[]) data).length * 2;
                return;
            case 8:
                this.currentPromotedAddress += ((long[]) data).length * 4;
                return;
            default:
                throw new DexException("bogus element_width: " + Hex.u2(elementWidth));
        }
    }
}
