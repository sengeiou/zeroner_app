package com.tencent.tinker.android.dx.instruction;

import com.tencent.tinker.android.dex.DexException;
import com.tencent.tinker.android.dx.util.Hex;

public final class InstructionWriter extends InstructionVisitor {
    private final ShortArrayCodeOutput codeOut;
    private final boolean hasPromoter;
    private final InstructionPromoter insnPromoter;

    public InstructionWriter(ShortArrayCodeOutput codeOut2, InstructionPromoter ipmo) {
        super(null);
        this.codeOut = codeOut2;
        this.insnPromoter = ipmo;
        this.hasPromoter = ipmo != null;
    }

    public void visitZeroRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal) {
        if (this.hasPromoter) {
            target = this.insnPromoter.getPromotedAddress(target);
        }
        switch (opcode) {
            case -1:
            case 0:
            case 14:
                this.codeOut.write((short) opcode);
                return;
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(0, 0)), (short) index, InstructionCodec.codeUnit(0, 0, 0, 0));
                return;
            case 40:
                if (this.hasPromoter) {
                    int relativeTarget = InstructionCodec.getTarget(target, this.codeOut.cursor());
                    if (relativeTarget == ((byte) relativeTarget)) {
                        this.codeOut.write(InstructionCodec.codeUnit(opcode, relativeTarget & 255));
                        return;
                    } else if (relativeTarget != ((short) relativeTarget)) {
                        this.codeOut.write(42, InstructionCodec.unit0(relativeTarget), InstructionCodec.unit1(relativeTarget));
                        return;
                    } else {
                        this.codeOut.write(41, (short) relativeTarget);
                        return;
                    }
                } else {
                    this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.getTargetByte(target, this.codeOut.cursor())));
                    return;
                }
            case 41:
                if (this.hasPromoter) {
                    int relativeTarget2 = InstructionCodec.getTarget(target, this.codeOut.cursor());
                    if (relativeTarget2 != ((short) relativeTarget2)) {
                        this.codeOut.write(42, InstructionCodec.unit0(relativeTarget2), InstructionCodec.unit1(relativeTarget2));
                        return;
                    }
                    short opcodeUnit = (short) opcode;
                    this.codeOut.write(opcodeUnit, (short) relativeTarget2);
                    return;
                }
                short opcodeUnit2 = (short) opcode;
                this.codeOut.write(opcodeUnit2, InstructionCodec.getTargetUnit(target, this.codeOut.cursor()));
                return;
            case 42:
                int relativeTarget3 = InstructionCodec.getTarget(target, this.codeOut.cursor());
                this.codeOut.write((short) opcode, InstructionCodec.unit0(relativeTarget3), InstructionCodec.unit1(relativeTarget3));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitOneRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a) {
        if (this.hasPromoter) {
            target = this.insnPromoter.getPromotedAddress(target);
        }
        switch (opcode) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 29:
            case 30:
            case 39:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a));
                return;
            case 18:
                this.codeOut.write(InstructionCodec.codeUnit((short) opcode, InstructionCodec.makeByte(a, InstructionCodec.getLiteralNibble(literal))));
                return;
            case 19:
            case 22:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.getLiteralUnit(literal));
                return;
            case 20:
            case 23:
                int literalInt = InstructionCodec.getLiteralInt(literal);
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.unit0(literalInt), InstructionCodec.unit1(literalInt));
                return;
            case 21:
            case 25:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), (short) ((int) (literal >> (opcode == 21 ? 16 : 48))));
                return;
            case 24:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.unit0(literal), InstructionCodec.unit1(literal), InstructionCodec.unit2(literal), InstructionCodec.unit3(literal));
                return;
            case 26:
                if (this.hasPromoter) {
                    if (index > 65535) {
                        this.codeOut.write(InstructionCodec.codeUnit(27, a), InstructionCodec.unit0(index), InstructionCodec.unit1(index));
                        return;
                    } else {
                        this.codeOut.write(InstructionCodec.codeUnit(opcode, a), (short) index);
                        return;
                    }
                } else if (index > 65535) {
                    throw new DexException("string index out of bound: " + Hex.u4(index) + ", perhaps you need to enable force jumbo mode.");
                } else {
                    this.codeOut.write(InstructionCodec.codeUnit(opcode, a), (short) index);
                    return;
                }
            case 27:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.unit0(index), InstructionCodec.unit1(index));
                return;
            case 28:
            case 31:
            case 34:
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
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), (short) index);
                return;
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(0, 1)), (short) index, InstructionCodec.codeUnit(a, 0, 0, 0));
                return;
            case 38:
            case 43:
            case 44:
                switch (opcode) {
                    case 43:
                    case 44:
                        this.codeOut.setBaseAddress(target, this.codeOut.cursor());
                        break;
                }
                int relativeTarget = InstructionCodec.getTarget(target, this.codeOut.cursor());
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.unit0(relativeTarget), InstructionCodec.unit1(relativeTarget));
                return;
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.getTargetUnit(target, this.codeOut.cursor()));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitTwoRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b) {
        if (this.hasPromoter) {
            target = this.insnPromoter.getPromotedAddress(target);
        }
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
                this.codeOut.write(InstructionCodec.codeUnit((short) opcode, InstructionCodec.makeByte(a, b)));
                return;
            case 2:
            case 5:
            case 8:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.getBUnit(b));
                return;
            case 3:
            case 6:
            case 9:
                this.codeOut.write((short) opcode, InstructionCodec.getAUnit(a), InstructionCodec.getBUnit(b));
                return;
            case 32:
            case 35:
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
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(a, b)), (short) index);
                return;
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(0, 2)), (short) index, InstructionCodec.codeUnit(a, b, 0, 0));
                return;
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(a, b)), InstructionCodec.getTargetUnit(target, this.codeOut.cursor()));
                return;
            case 208:
            case 209:
            case 210:
            case Opcodes.DIV_INT_LIT16 /*211*/:
            case Opcodes.REM_INT_LIT16 /*212*/:
            case Opcodes.AND_INT_LIT16 /*213*/:
            case Opcodes.OR_INT_LIT16 /*214*/:
            case Opcodes.XOR_INT_LIT16 /*215*/:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(a, b)), InstructionCodec.getLiteralUnit(literal));
                return;
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
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.codeUnit(b, InstructionCodec.getLiteralByte(literal)));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitThreeRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b, int c) {
        switch (opcode) {
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(0, 3)), (short) index, InstructionCodec.codeUnit(a, b, c, 0));
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
                this.codeOut.write(InstructionCodec.codeUnit(opcode, a), InstructionCodec.codeUnit(b, c));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitFourRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b, int c, int d) {
        switch (opcode) {
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(0, 4)), (short) index, InstructionCodec.codeUnit(a, b, c, d));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitFiveRegisterInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int b, int c, int d, int e) {
        switch (opcode) {
            case 36:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, InstructionCodec.makeByte(e, 5)), (short) index, InstructionCodec.codeUnit(a, b, c, d));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitRegisterRangeInsn(int currentAddress, int opcode, int index, int indexType, int target, long literal, int a, int registerCount) {
        switch (opcode) {
            case 37:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
                this.codeOut.write(InstructionCodec.codeUnit(opcode, registerCount), (short) index, InstructionCodec.getAUnit(a));
                return;
            default:
                throw new IllegalStateException("unexpected opcode: " + Hex.u2or4(opcode));
        }
    }

    public void visitSparseSwitchPayloadInsn(int currentAddress, int opcode, int[] keys, int[] targets) {
        int i = 0;
        int baseAddress = this.codeOut.baseAddressForCursor();
        this.codeOut.write((short) opcode);
        this.codeOut.write(InstructionCodec.asUnsignedUnit(targets.length));
        for (int key : keys) {
            this.codeOut.writeInt(key);
        }
        if (this.hasPromoter) {
            int length = targets.length;
            while (i < length) {
                this.codeOut.writeInt(this.insnPromoter.getPromotedAddress(targets[i]) - baseAddress);
                i++;
            }
            return;
        }
        int length2 = targets.length;
        while (i < length2) {
            this.codeOut.writeInt(targets[i] - baseAddress);
            i++;
        }
    }

    public void visitPackedSwitchPayloadInsn(int currentAddress, int opcode, int firstKey, int[] targets) {
        int i = 0;
        int baseAddress = this.codeOut.baseAddressForCursor();
        this.codeOut.write((short) opcode);
        this.codeOut.write(InstructionCodec.asUnsignedUnit(targets.length));
        this.codeOut.writeInt(firstKey);
        if (this.hasPromoter) {
            int length = targets.length;
            while (i < length) {
                this.codeOut.writeInt(this.insnPromoter.getPromotedAddress(targets[i]) - baseAddress);
                i++;
            }
            return;
        }
        int length2 = targets.length;
        while (i < length2) {
            this.codeOut.writeInt(targets[i] - baseAddress);
            i++;
        }
    }

    public void visitFillArrayDataPayloadInsn(int currentAddress, int opcode, Object data, int size, int elementWidth) {
        this.codeOut.write((short) opcode);
        this.codeOut.write((short) elementWidth);
        this.codeOut.writeInt(size);
        switch (elementWidth) {
            case 1:
                this.codeOut.write((byte[]) (byte[]) data);
                return;
            case 2:
                this.codeOut.write((short[]) (short[]) data);
                return;
            case 4:
                this.codeOut.write((int[]) (int[]) data);
                return;
            case 8:
                this.codeOut.write((long[]) (long[]) data);
                return;
            default:
                throw new DexException("bogus element_width: " + Hex.u2(elementWidth));
        }
    }
}
