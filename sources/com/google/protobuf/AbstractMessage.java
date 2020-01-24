package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Descriptors.FileDescriptor.Syntax;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Internal.EnumLite;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractMessage extends AbstractMessageLite implements Message {
    protected int memoizedSize = -1;

    public static abstract class Builder<BuilderType extends Builder<BuilderType>> extends com.google.protobuf.AbstractMessageLite.Builder implements com.google.protobuf.Message.Builder {
        public BuilderType clone() {
            throw new UnsupportedOperationException("clone() should be implemented in subclasses.");
        }

        public boolean hasOneof(OneofDescriptor oneof) {
            throw new UnsupportedOperationException("hasOneof() is not implemented.");
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
        }

        public BuilderType clearOneof(OneofDescriptor oneof) {
            throw new UnsupportedOperationException("clearOneof() is not implemented.");
        }

        public BuilderType clear() {
            for (Entry<FieldDescriptor, Object> entry : getAllFields().entrySet()) {
                clearField((FieldDescriptor) entry.getKey());
            }
            return this;
        }

        public List<String> findInitializationErrors() {
            return MessageReflection.findMissingFields(this);
        }

        public String getInitializationErrorString() {
            return MessageReflection.delimitWithCommas(findInitializationErrors());
        }

        /* access modifiers changed from: protected */
        public BuilderType internalMergeFrom(AbstractMessageLite other) {
            return mergeFrom((Message) other);
        }

        public BuilderType mergeFrom(Message other) {
            return mergeFrom(other, other.getAllFields());
        }

        /* access modifiers changed from: 0000 */
        public BuilderType mergeFrom(Message other, Map<FieldDescriptor, Object> allFields) {
            if (other.getDescriptorForType() != getDescriptorForType()) {
                throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
            }
            for (Entry<FieldDescriptor, Object> entry : allFields.entrySet()) {
                FieldDescriptor field = (FieldDescriptor) entry.getKey();
                if (field.isRepeated()) {
                    for (Object element : (List) entry.getValue()) {
                        addRepeatedField(field, element);
                    }
                } else if (field.getJavaType() == JavaType.MESSAGE) {
                    Message existingValue = (Message) getField(field);
                    if (existingValue == existingValue.getDefaultInstanceForType()) {
                        setField(field, entry.getValue());
                    } else {
                        setField(field, existingValue.newBuilderForType().mergeFrom(existingValue).mergeFrom((Message) entry.getValue()).build());
                    }
                } else {
                    setField(field, entry.getValue());
                }
            }
            mergeUnknownFields(other.getUnknownFields());
            return this;
        }

        public BuilderType mergeFrom(CodedInputStream input) throws IOException {
            return mergeFrom(input, (ExtensionRegistryLite) ExtensionRegistry.getEmptyRegistry());
        }

        public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            boolean discardUnknown;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields;
            int tag;
            if (getDescriptorForType().getFile().getSyntax() == Syntax.PROTO3) {
                discardUnknown = input.shouldDiscardUnknownFieldsProto3();
            } else {
                discardUnknown = input.shouldDiscardUnknownFields();
            }
            if (discardUnknown) {
                unknownFields = null;
            } else {
                unknownFields = UnknownFieldSet.newBuilder(getUnknownFields());
            }
            do {
                tag = input.readTag();
                if (tag == 0) {
                    break;
                }
            } while (MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, getDescriptorForType(), new BuilderAdapter(this), tag));
            if (unknownFields != null) {
                setUnknownFields(unknownFields.build());
            }
            return this;
        }

        public BuilderType mergeUnknownFields(UnknownFieldSet unknownFields) {
            setUnknownFields(UnknownFieldSet.newBuilder(getUnknownFields()).mergeFrom(unknownFields).build());
            return this;
        }

        public com.google.protobuf.Message.Builder getFieldBuilder(FieldDescriptor field) {
            throw new UnsupportedOperationException("getFieldBuilder() called on an unsupported message type.");
        }

        public com.google.protobuf.Message.Builder getRepeatedFieldBuilder(FieldDescriptor field, int index) {
            throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on an unsupported message type.");
        }

        public String toString() {
            return TextFormat.printToString((MessageOrBuilder) this);
        }

        protected static UninitializedMessageException newUninitializedMessageException(Message message) {
            return new UninitializedMessageException(MessageReflection.findMissingFields(message));
        }

        /* access modifiers changed from: 0000 */
        public void markClean() {
            throw new IllegalStateException("Should be overridden by subclasses.");
        }

        /* access modifiers changed from: 0000 */
        public void dispose() {
            throw new IllegalStateException("Should be overridden by subclasses.");
        }

        public BuilderType mergeFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data);
        }

        public BuilderType mergeFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, extensionRegistry);
        }

        public BuilderType mergeFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data);
        }

        public BuilderType mergeFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, off, len);
        }

        public BuilderType mergeFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, extensionRegistry);
        }

        public BuilderType mergeFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, off, len, extensionRegistry);
        }

        public BuilderType mergeFrom(InputStream input) throws IOException {
            return (Builder) super.mergeFrom(input);
        }

        public BuilderType mergeFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Builder) super.mergeFrom(input, extensionRegistry);
        }

        public boolean mergeDelimitedFrom(InputStream input) throws IOException {
            return super.mergeDelimitedFrom(input);
        }

        public boolean mergeDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return super.mergeDelimitedFrom(input, extensionRegistry);
        }
    }

    protected interface BuilderParent {
        void markDirty();
    }

    public boolean isInitialized() {
        return MessageReflection.isInitialized(this);
    }

    /* access modifiers changed from: protected */
    public com.google.protobuf.Message.Builder newBuilderForType(BuilderParent parent) {
        throw new UnsupportedOperationException("Nested builder is not supported for this type.");
    }

    public List<String> findInitializationErrors() {
        return MessageReflection.findMissingFields(this);
    }

    public String getInitializationErrorString() {
        return MessageReflection.delimitWithCommas(findInitializationErrors());
    }

    public boolean hasOneof(OneofDescriptor oneof) {
        throw new UnsupportedOperationException("hasOneof() is not implemented.");
    }

    public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
        throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
    }

    public final String toString() {
        return TextFormat.printToString((MessageOrBuilder) this);
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        MessageReflection.writeMessageTo(this, getAllFields(), output, false);
    }

    /* access modifiers changed from: 0000 */
    public int getMemoizedSerializedSize() {
        return this.memoizedSize;
    }

    /* access modifiers changed from: 0000 */
    public void setMemoizedSerializedSize(int size) {
        this.memoizedSize = size;
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        this.memoizedSize = MessageReflection.getSerializedSize(this, getAllFields());
        return this.memoizedSize;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Message)) {
            return false;
        }
        Message otherMessage = (Message) other;
        if (getDescriptorForType() != otherMessage.getDescriptorForType()) {
            return false;
        }
        if (!compareFields(getAllFields(), otherMessage.getAllFields()) || !getUnknownFields().equals(otherMessage.getUnknownFields())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = this.memoizedHashCode;
        if (hash != 0) {
            return hash;
        }
        int hash2 = (hashFields(getDescriptorForType().hashCode() + 779, getAllFields()) * 29) + getUnknownFields().hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    private static ByteString toByteString(Object value) {
        if (value instanceof byte[]) {
            return ByteString.copyFrom((byte[]) (byte[]) value);
        }
        return (ByteString) value;
    }

    private static boolean compareBytes(Object a, Object b) {
        if (!(a instanceof byte[]) || !(b instanceof byte[])) {
            return toByteString(a).equals(toByteString(b));
        }
        return Arrays.equals((byte[]) a, (byte[]) b);
    }

    private static Map convertMapEntryListToMap(List list) {
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map result = new HashMap();
        Iterator iterator = list.iterator();
        Message entry = (Message) iterator.next();
        Descriptor descriptor = entry.getDescriptorForType();
        FieldDescriptor key = descriptor.findFieldByName("key");
        FieldDescriptor value = descriptor.findFieldByName("value");
        Object fieldValue = entry.getField(value);
        if (fieldValue instanceof EnumValueDescriptor) {
            fieldValue = Integer.valueOf(((EnumValueDescriptor) fieldValue).getNumber());
        }
        result.put(entry.getField(key), fieldValue);
        while (iterator.hasNext()) {
            Message entry2 = (Message) iterator.next();
            Object fieldValue2 = entry2.getField(value);
            if (fieldValue2 instanceof EnumValueDescriptor) {
                fieldValue2 = Integer.valueOf(((EnumValueDescriptor) fieldValue2).getNumber());
            }
            result.put(entry2.getField(key), fieldValue2);
        }
        return result;
    }

    private static boolean compareMapField(Object a, Object b) {
        return MapFieldLite.equals(convertMapEntryListToMap((List) a), convertMapEntryListToMap((List) b));
    }

    static boolean compareFields(Map<FieldDescriptor, Object> a, Map<FieldDescriptor, Object> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (FieldDescriptor descriptor : a.keySet()) {
            if (!b.containsKey(descriptor)) {
                return false;
            }
            Object value1 = a.get(descriptor);
            Object value2 = b.get(descriptor);
            if (descriptor.getType() == Type.BYTES) {
                if (descriptor.isRepeated()) {
                    List list1 = (List) value1;
                    List list2 = (List) value2;
                    if (list1.size() != list2.size()) {
                        return false;
                    }
                    for (int i = 0; i < list1.size(); i++) {
                        if (!compareBytes(list1.get(i), list2.get(i))) {
                            return false;
                        }
                    }
                    continue;
                } else if (!compareBytes(value1, value2)) {
                    return false;
                }
            } else if (descriptor.isMapField()) {
                if (!compareMapField(value1, value2)) {
                    return false;
                }
            } else if (!value1.equals(value2)) {
                return false;
            }
        }
        return true;
    }

    private static int hashMapField(Object value) {
        return MapFieldLite.calculateHashCodeForMap(convertMapEntryListToMap((List) value));
    }

    protected static int hashFields(int hash, Map<FieldDescriptor, Object> map) {
        for (Entry<FieldDescriptor, Object> entry : map.entrySet()) {
            FieldDescriptor field = (FieldDescriptor) entry.getKey();
            Object value = entry.getValue();
            int hash2 = (hash * 37) + field.getNumber();
            if (field.isMapField()) {
                hash = (hash2 * 53) + hashMapField(value);
            } else if (field.getType() != Type.ENUM) {
                hash = (hash2 * 53) + value.hashCode();
            } else if (field.isRepeated()) {
                hash = (hash2 * 53) + Internal.hashEnumList((List) value);
            } else {
                hash = (hash2 * 53) + Internal.hashEnum((EnumLite) value);
            }
        }
        return hash;
    }

    /* access modifiers changed from: 0000 */
    public UninitializedMessageException newUninitializedMessageException() {
        return Builder.newUninitializedMessageException(this);
    }

    @Deprecated
    protected static int hashLong(long n) {
        return (int) ((n >>> 32) ^ n);
    }

    @Deprecated
    protected static int hashBoolean(boolean b) {
        return b ? 1231 : 1237;
    }

    @Deprecated
    protected static int hashEnum(EnumLite e) {
        return e.getNumber();
    }

    @Deprecated
    protected static int hashEnumList(List<? extends EnumLite> list) {
        int hash = 1;
        for (EnumLite e : list) {
            hash = (hash * 31) + hashEnum(e);
        }
        return hash;
    }
}
