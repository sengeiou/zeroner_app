package com.google.protobuf;

import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Internal.EnumLiteMap;

public enum Syntax implements ProtocolMessageEnum {
    SYNTAX_PROTO2(0),
    SYNTAX_PROTO3(1),
    UNRECOGNIZED(-1);
    
    public static final int SYNTAX_PROTO2_VALUE = 0;
    public static final int SYNTAX_PROTO3_VALUE = 1;
    private static final Syntax[] VALUES = null;
    private static final EnumLiteMap<Syntax> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new EnumLiteMap<Syntax>() {
            public Syntax findValueByNumber(int number) {
                return Syntax.forNumber(number);
            }
        };
        VALUES = values();
    }

    public final int getNumber() {
        if (this != UNRECOGNIZED) {
            return this.value;
        }
        throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
    }

    @Deprecated
    public static Syntax valueOf(int value2) {
        return forNumber(value2);
    }

    public static Syntax forNumber(int value2) {
        switch (value2) {
            case 0:
                return SYNTAX_PROTO2;
            case 1:
                return SYNTAX_PROTO3;
            default:
                return null;
        }
    }

    public static EnumLiteMap<Syntax> internalGetValueMap() {
        return internalValueMap;
    }

    public final EnumValueDescriptor getValueDescriptor() {
        return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
    }

    public final EnumDescriptor getDescriptorForType() {
        return getDescriptor();
    }

    public static final EnumDescriptor getDescriptor() {
        return (EnumDescriptor) TypeProto.getDescriptor().getEnumTypes().get(0);
    }

    public static Syntax valueOf(EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        } else if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        } else {
            return VALUES[desc.getIndex()];
        }
    }

    private Syntax(int value2) {
        this.value = value2;
    }
}
