package com.tencent.tinker.commons.dexpatcher.struct;

public final class PatchOperation<T> {
    public static final int OP_ADD = 1;
    public static final int OP_DEL = 0;
    public static final int OP_REPLACE = 2;
    public int index;
    public T newItem;
    public int op;

    public PatchOperation(int op2, int index2) {
        this(op2, index2, null);
    }

    public PatchOperation(int op2, int index2, T newItem2) {
        this.op = op2;
        this.index = index2;
        this.newItem = newItem2;
    }

    public static String translateOpToString(int op2) {
        switch (op2) {
            case 0:
                return "OP_DEL";
            case 1:
                return "OP_ADD";
            case 2:
                return "OP_REPLACE";
            default:
                return "OP_UNKNOWN";
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String opDesc = translateOpToString(this.op);
        sb.append('{');
        sb.append("op: ").append(opDesc).append(", index: ").append(this.index).append(", newItem: ").append(this.newItem);
        sb.append('}');
        return sb.toString();
    }
}
