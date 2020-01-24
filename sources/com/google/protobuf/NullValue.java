package com.google.protobuf;

import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Internal.EnumLiteMap;

public enum NullValue implements ProtocolMessageEnum {
    NULL_VALUE(0),
    UNRECOGNIZED(-1);
    
    public static final int NULL_VALUE_VALUE = 0;
    private static final NullValue[] VALUES = null;
    private static final EnumLiteMap<NullValue> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new EnumLiteMap<NullValue>() {
            public NullValue findValueByNumber(int number) {
                return NullValue.forNumber(number);
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
    public static NullValue valueOf(int value2) {
        return forNumber(value2);
    }

    public static NullValue forNumber(int value2) {
        switch (value2) {
            case 0:
                return NULL_VALUE;
            default:
                return null;
        }
    }

    public static EnumLiteMap<NullValue> internalGetValueMap() {
        return internalValueMap;
    }

    public final EnumValueDescriptor getValueDescriptor() {
        return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
    }

    public final EnumDescriptor getDescriptorForType() {
        return getDescriptor();
    }

    public static final EnumDescriptor getDescriptor() {
        return (EnumDescriptor) StructProto.getDescriptor().getEnumTypes().get(0);
    }

    public static NullValue valueOf(EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        } else if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        } else {
            return VALUES[desc.getIndex()];
        }
    }

    private NullValue(int value2) {
        this.value = value2;
    }
}
