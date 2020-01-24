package com.google.protobuf;

import com.google.protobuf.FieldSet.FieldDescriptorLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal.BooleanList;
import com.google.protobuf.Internal.DoubleList;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.Internal.FloatList;
import com.google.protobuf.Internal.IntList;
import com.google.protobuf.Internal.LongList;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.WireFormat.FieldType;
import com.google.protobuf.WireFormat.JavaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite<MessageType, BuilderType> {
    static final boolean ENABLE_EXPERIMENTAL_RUNTIME_AT_BUILD_TIME = false;
    protected int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();

    protected static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final byte[] asBytes;
        private final String messageClassName;

        public static SerializedForm of(MessageLite message) {
            return new SerializedForm(message);
        }

        SerializedForm(MessageLite regularForm) {
            this.messageClassName = regularForm.getClass().getName();
            this.asBytes = regularForm.toByteArray();
        }

        /* access modifiers changed from: protected */
        public Object readResolve() throws ObjectStreamException {
            try {
                Field defaultInstanceField = Class.forName(this.messageClassName).getDeclaredField("DEFAULT_INSTANCE");
                defaultInstanceField.setAccessible(true);
                return ((MessageLite) defaultInstanceField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e);
            } catch (NoSuchFieldException e2) {
                return readResolveFallback();
            } catch (SecurityException e3) {
                throw new RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e3);
            } catch (IllegalAccessException e4) {
                throw new RuntimeException("Unable to call parsePartialFrom", e4);
            } catch (InvalidProtocolBufferException e5) {
                throw new RuntimeException("Unable to understand proto buffer", e5);
            }
        }

        @Deprecated
        private Object readResolveFallback() throws ObjectStreamException {
            try {
                Field defaultInstanceField = Class.forName(this.messageClassName).getDeclaredField("defaultInstance");
                defaultInstanceField.setAccessible(true);
                return ((MessageLite) defaultInstanceField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e);
            } catch (NoSuchFieldException e2) {
                throw new RuntimeException("Unable to find defaultInstance in " + this.messageClassName, e2);
            } catch (SecurityException e3) {
                throw new RuntimeException("Unable to call defaultInstance in " + this.messageClassName, e3);
            } catch (IllegalAccessException e4) {
                throw new RuntimeException("Unable to call parsePartialFrom", e4);
            } catch (InvalidProtocolBufferException e5) {
                throw new RuntimeException("Unable to understand proto buffer", e5);
            }
        }
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends com.google.protobuf.AbstractMessageLite.Builder<MessageType, BuilderType> {
        private final MessageType defaultInstance;
        protected MessageType instance;
        protected boolean isBuilt = false;

        protected Builder(MessageType defaultInstance2) {
            this.defaultInstance = defaultInstance2;
            this.instance = (GeneratedMessageLite) defaultInstance2.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        }

        /* access modifiers changed from: protected */
        public void copyOnWrite() {
            if (this.isBuilt) {
                MessageType newInstance = (GeneratedMessageLite) this.instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
                mergeFromInstance(newInstance, this.instance);
                this.instance = newInstance;
                this.isBuilt = false;
            }
        }

        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }

        public final BuilderType clear() {
            this.instance = (GeneratedMessageLite) this.instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            return this;
        }

        public BuilderType clone() {
            BuilderType builder = getDefaultInstanceForType().newBuilderForType();
            builder.mergeFrom(buildPartial());
            return builder;
        }

        public MessageType buildPartial() {
            if (this.isBuilt) {
                return this.instance;
            }
            this.instance.makeImmutable();
            this.isBuilt = true;
            return this.instance;
        }

        public final MessageType build() {
            MessageType result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        /* access modifiers changed from: protected */
        public BuilderType internalMergeFrom(MessageType message) {
            return mergeFrom(message);
        }

        public BuilderType mergeFrom(MessageType message) {
            copyOnWrite();
            mergeFromInstance(this.instance, message);
            return this;
        }

        private void mergeFromInstance(MessageType dest, MessageType src) {
            dest.visit(MergeFromVisitor.INSTANCE, src);
        }

        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        public BuilderType mergeFrom(byte[] input, int offset, int length) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(input, offset, length);
        }

        public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            copyOnWrite();
            try {
                this.instance.dynamicMethod(MethodToInvoke.MERGE_FROM_STREAM, input, extensionRegistry);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }
    }

    protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
        private T defaultInstance;

        public DefaultInstanceBasedParser(T defaultInstance2) {
            this.defaultInstance = defaultInstance2;
        }

        public T parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, extensionRegistry);
        }

        public T parsePartialFrom(byte[] input) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input);
        }
    }

    static class EqualsVisitor implements Visitor {
        static final EqualsVisitor INSTANCE = new EqualsVisitor();
        static final NotEqualsException NOT_EQUALS = new NotEqualsException();

        static final class NotEqualsException extends RuntimeException {
            NotEqualsException() {
            }
        }

        private EqualsVisitor() {
        }

        public boolean visitBoolean(boolean minePresent, boolean mine, boolean otherPresent, boolean other) {
            if (minePresent == otherPresent && mine == other) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public int visitInt(boolean minePresent, int mine, boolean otherPresent, int other) {
            if (minePresent == otherPresent && mine == other) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public double visitDouble(boolean minePresent, double mine, boolean otherPresent, double other) {
            if (minePresent == otherPresent && mine == other) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public float visitFloat(boolean minePresent, float mine, boolean otherPresent, float other) {
            if (minePresent == otherPresent && mine == other) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public long visitLong(boolean minePresent, long mine, boolean otherPresent, long other) {
            if (minePresent == otherPresent && mine == other) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public String visitString(boolean minePresent, String mine, boolean otherPresent, String other) {
            if (minePresent == otherPresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public ByteString visitByteString(boolean minePresent, ByteString mine, boolean otherPresent, ByteString other) {
            if (minePresent == otherPresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofBoolean(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofInt(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofDouble(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofFloat(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofLong(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofString(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofByteString(boolean minePresent, Object mine, Object other) {
            if (minePresent && mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public Object visitOneofMessage(boolean minePresent, Object mine, Object other) {
            if (minePresent && ((GeneratedMessageLite) mine).equals(this, (MessageLite) other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public void visitOneofNotSet(boolean minePresent) {
            if (minePresent) {
                throw NOT_EQUALS;
            }
        }

        public <T extends MessageLite> T visitMessage(T mine, T other) {
            if (mine == null && other == null) {
                return null;
            }
            if (mine == null || other == null) {
                throw NOT_EQUALS;
            }
            ((GeneratedMessageLite) mine).equals(this, other);
            return mine;
        }

        public <T> ProtobufList<T> visitList(ProtobufList<T> mine, ProtobufList<T> other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public BooleanList visitBooleanList(BooleanList mine, BooleanList other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public IntList visitIntList(IntList mine, IntList other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public DoubleList visitDoubleList(DoubleList mine, DoubleList other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public FloatList visitFloatList(FloatList mine, FloatList other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public LongList visitLongList(LongList mine, LongList other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> mine, FieldSet<ExtensionDescriptor> other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite mine, UnknownFieldSetLite other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }

        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mine, MapFieldLite<K, V> other) {
            if (mine.equals(other)) {
                return mine;
            }
            throw NOT_EQUALS;
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected ExtendableBuilder(MessageType defaultInstance) {
            super(defaultInstance);
        }

        /* access modifiers changed from: 0000 */
        public void internalSetExtensionSet(FieldSet<ExtensionDescriptor> extensions) {
            copyOnWrite();
            ((ExtendableMessage) this.instance).extensions = extensions;
        }

        /* access modifiers changed from: protected */
        public void copyOnWrite() {
            if (this.isBuilt) {
                super.copyOnWrite();
                ((ExtendableMessage) this.instance).extensions = ((ExtendableMessage) this.instance).extensions.clone();
            }
        }

        private FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            FieldSet<ExtensionDescriptor> extensions = ((ExtendableMessage) this.instance).extensions;
            if (!extensions.isImmutable()) {
                return extensions;
            }
            FieldSet<ExtensionDescriptor> extensions2 = extensions.clone();
            ((ExtendableMessage) this.instance).extensions = extensions2;
            return extensions2;
        }

        public final MessageType buildPartial() {
            if (this.isBuilt) {
                return (ExtendableMessage) this.instance;
            }
            ((ExtendableMessage) this.instance).extensions.makeImmutable();
            return (ExtendableMessage) super.buildPartial();
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage) this.instance).hasExtension(extension);
        }

        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
            return ((ExtendableMessage) this.instance).getExtensionCount(extension);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage) this.instance).getExtension(extension);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extension, int index) {
            return ((ExtendableMessage) this.instance).getExtension(extension, index);
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extension, Type value) {
            GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setField(extensionLite.descriptor, extensionLite.toFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extension, int index, Type value) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setRepeatedField(extensionLite.descriptor, index, extensionLite.singularToFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extension, Type value) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().addRepeatedField(extensionLite.descriptor, extensionLite.singularToFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType clearExtension(ExtensionLite<MessageType, ?> extension) {
            GeneratedExtension<MessageType, ?> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().clearField(extensionLite.descriptor);
            return this;
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends GeneratedMessageLite<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected FieldSet<ExtensionDescriptor> extensions = FieldSet.emptySet();

        protected class ExtensionWriter {
            private final Iterator<Entry<ExtensionDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Entry<ExtensionDescriptor, Object> next;

            private ExtensionWriter(boolean messageSetWireFormat2) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = (Entry) this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat2;
            }

            public void writeUntil(int end, CodedOutputStream output) throws IOException {
                while (this.next != null && ((ExtensionDescriptor) this.next.getKey()).getNumber() < end) {
                    ExtensionDescriptor extension = (ExtensionDescriptor) this.next.getKey();
                    if (!this.messageSetWireFormat || extension.getLiteJavaType() != JavaType.MESSAGE || extension.isRepeated()) {
                        FieldSet.writeField(extension, this.next.getValue(), output);
                    } else {
                        output.writeMessageSetExtension(extension.getNumber(), (MessageLite) this.next.getValue());
                    }
                    if (this.iter.hasNext()) {
                        this.next = (Entry) this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        public /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType() {
            return GeneratedMessageLite.super.getDefaultInstanceForType();
        }

        public /* bridge */ /* synthetic */ com.google.protobuf.MessageLite.Builder newBuilderForType() {
            return GeneratedMessageLite.super.newBuilderForType();
        }

        public /* bridge */ /* synthetic */ com.google.protobuf.MessageLite.Builder toBuilder() {
            return GeneratedMessageLite.super.toBuilder();
        }

        /* access modifiers changed from: protected */
        public final void mergeExtensionFields(MessageType other) {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            this.extensions.mergeFrom(other.extensions);
        }

        /* access modifiers changed from: 0000 */
        public final void visit(Visitor visitor, MessageType other) {
            GeneratedMessageLite.super.visit(visitor, other);
            this.extensions = visitor.visitExtensions(this.extensions, other.extensions);
        }

        /* access modifiers changed from: protected */
        public <MessageType extends MessageLite> boolean parseUnknownField(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            return parseExtension(input, extensionRegistry, extensionRegistry.findLiteExtensionByNumber(defaultInstance, fieldNumber), tag, fieldNumber);
        }

        private boolean parseExtension(CodedInputStream input, ExtensionRegistryLite extensionRegistry, GeneratedExtension<?, ?> extension, int tag, int fieldNumber) throws IOException {
            Object findValueByNumber;
            int wireType = WireFormat.getTagWireType(tag);
            boolean unknown = false;
            boolean packed = false;
            if (extension == null) {
                unknown = true;
            } else if (wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), false)) {
                packed = false;
            } else if (!extension.descriptor.isRepeated || !extension.descriptor.type.isPackable() || wireType != FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), true)) {
                unknown = true;
            } else {
                packed = true;
            }
            if (unknown) {
                return parseUnknownField(tag, input);
            }
            ensureExtensionsAreMutable();
            if (packed) {
                int limit = input.pushLimit(input.readRawVarint32());
                if (extension.descriptor.getLiteType() == FieldType.ENUM) {
                    while (input.getBytesUntilLimit() > 0) {
                        EnumLite value = extension.descriptor.getEnumType().findValueByNumber(input.readEnum());
                        if (value == null) {
                            return true;
                        }
                        this.extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value));
                    }
                } else {
                    while (input.getBytesUntilLimit() > 0) {
                        this.extensions.addRepeatedField(extension.descriptor, FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false));
                    }
                }
                input.popLimit(limit);
            } else {
                switch (extension.descriptor.getLiteJavaType()) {
                    case MESSAGE:
                        com.google.protobuf.MessageLite.Builder subBuilder = null;
                        if (!extension.descriptor.isRepeated()) {
                            MessageLite existingValue = (MessageLite) this.extensions.getField(extension.descriptor);
                            if (existingValue != null) {
                                subBuilder = existingValue.toBuilder();
                            }
                        }
                        if (subBuilder == null) {
                            subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
                        }
                        if (extension.descriptor.getLiteType() == FieldType.GROUP) {
                            input.readGroup(extension.getNumber(), subBuilder, extensionRegistry);
                        } else {
                            input.readMessage(subBuilder, extensionRegistry);
                        }
                        findValueByNumber = subBuilder.build();
                        break;
                    case ENUM:
                        int rawValue = input.readEnum();
                        findValueByNumber = extension.descriptor.getEnumType().findValueByNumber(rawValue);
                        if (findValueByNumber == null) {
                            mergeVarintField(fieldNumber, rawValue);
                            return true;
                        }
                        break;
                    default:
                        findValueByNumber = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                        break;
                }
                if (extension.descriptor.isRepeated()) {
                    this.extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(findValueByNumber));
                } else {
                    this.extensions.setField(extension.descriptor, extension.singularToFieldSetType(findValueByNumber));
                }
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public <MessageType extends MessageLite> boolean parseUnknownFieldAsMessageSet(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            if (tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
                mergeMessageSetExtensionFromCodedStream(defaultInstance, input, extensionRegistry);
                return true;
            } else if (WireFormat.getTagWireType(tag) == 2) {
                return parseUnknownField(defaultInstance, input, extensionRegistry, tag);
            } else {
                return input.skipField(tag);
            }
        }

        private <MessageType extends MessageLite> void mergeMessageSetExtensionFromCodedStream(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            int typeId = 0;
            ByteString rawBytes = null;
            GeneratedExtension<?, ?> extension = null;
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    break;
                } else if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = input.readUInt32();
                    if (typeId != 0) {
                        extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, typeId);
                    }
                } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (typeId == 0 || extension == null) {
                        rawBytes = input.readBytes();
                    } else {
                        eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, typeId);
                        rawBytes = null;
                    }
                } else if (!input.skipField(tag)) {
                    break;
                }
            }
            input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
            if (rawBytes != null && typeId != 0) {
                if (extension != null) {
                    mergeMessageSetExtensionFromBytes(rawBytes, extensionRegistry, extension);
                } else if (rawBytes != null) {
                    mergeLengthDelimitedField(typeId, rawBytes);
                }
            }
        }

        private void eagerlyMergeMessageSetExtension(CodedInputStream input, GeneratedExtension<?, ?> extension, ExtensionRegistryLite extensionRegistry, int typeId) throws IOException {
            CodedInputStream codedInputStream = input;
            ExtensionRegistryLite extensionRegistryLite = extensionRegistry;
            GeneratedExtension<?, ?> generatedExtension = extension;
            parseExtension(codedInputStream, extensionRegistryLite, generatedExtension, WireFormat.makeTag(typeId, 2), typeId);
        }

        private void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistryLite extensionRegistry, GeneratedExtension<?, ?> extension) throws IOException {
            com.google.protobuf.MessageLite.Builder subBuilder = null;
            MessageLite existingValue = (MessageLite) this.extensions.getField(extension.descriptor);
            if (existingValue != null) {
                subBuilder = existingValue.toBuilder();
            }
            if (subBuilder == null) {
                subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
            }
            subBuilder.mergeFrom(rawBytes, extensionRegistry);
            ensureExtensionsAreMutable().setField(extension.descriptor, extension.singularToFieldSetType(subBuilder.build()));
        }

        private FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            return this.extensions;
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
            GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return this.extensions.hasField(extensionLite.descriptor);
        }

        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return this.extensions.getRepeatedFieldCount(extensionLite.descriptor);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extension) {
            GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            Object value = this.extensions.getField(extensionLite.descriptor);
            if (value == null) {
                return extensionLite.defaultValue;
            }
            return extensionLite.fromFieldSetType(value);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extension, int index) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return extensionLite.singularFromFieldSetType(this.extensions.getRepeatedField(extensionLite.descriptor, index));
        }

        /* access modifiers changed from: protected */
        public boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        /* access modifiers changed from: protected */
        public final void makeImmutable() {
            GeneratedMessageLite.super.makeImmutable();
            this.extensions.makeImmutable();
        }

        /* access modifiers changed from: protected */
        public ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter<>(false);
        }

        /* access modifiers changed from: protected */
        public ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter<>(true);
        }

        /* access modifiers changed from: protected */
        public int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        /* access modifiers changed from: protected */
        public int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder {
        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);
    }

    static final class ExtensionDescriptor implements FieldDescriptorLite<ExtensionDescriptor> {
        final EnumLiteMap<?> enumTypeMap;
        final boolean isPacked;
        final boolean isRepeated;
        final int number;
        final FieldType type;

        ExtensionDescriptor(EnumLiteMap<?> enumTypeMap2, int number2, FieldType type2, boolean isRepeated2, boolean isPacked2) {
            this.enumTypeMap = enumTypeMap2;
            this.number = number2;
            this.type = type2;
            this.isRepeated = isRepeated2;
            this.isPacked = isPacked2;
        }

        public int getNumber() {
            return this.number;
        }

        public FieldType getLiteType() {
            return this.type;
        }

        public JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }

        public boolean isRepeated() {
            return this.isRepeated;
        }

        public boolean isPacked() {
            return this.isPacked;
        }

        public EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }

        public com.google.protobuf.MessageLite.Builder internalMergeFrom(com.google.protobuf.MessageLite.Builder to, MessageLite from) {
            return ((Builder) to).mergeFrom((GeneratedMessageLite) from);
        }

        public int compareTo(ExtensionDescriptor other) {
            return this.number - other.number;
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final ExtensionDescriptor descriptor;
        final MessageLite messageDefaultInstance;

        GeneratedExtension(ContainingType containingTypeDefaultInstance2, Type defaultValue2, MessageLite messageDefaultInstance2, ExtensionDescriptor descriptor2, Class singularType) {
            if (containingTypeDefaultInstance2 == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (descriptor2.getLiteType() == FieldType.MESSAGE && messageDefaultInstance2 == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.containingTypeDefaultInstance = containingTypeDefaultInstance2;
                this.defaultValue = defaultValue2;
                this.messageDefaultInstance = messageDefaultInstance2;
                this.descriptor = descriptor2;
            }
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }

        public int getNumber() {
            return this.descriptor.getNumber();
        }

        public MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        /* access modifiers changed from: 0000 */
        public Object fromFieldSetType(Object value) {
            if (!this.descriptor.isRepeated()) {
                return singularFromFieldSetType(value);
            }
            if (this.descriptor.getLiteJavaType() != JavaType.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(singularFromFieldSetType(element));
            }
            return result;
        }

        /* access modifiers changed from: 0000 */
        public Object singularFromFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == JavaType.ENUM) {
                return this.descriptor.enumTypeMap.findValueByNumber(((Integer) value).intValue());
            }
            return value;
        }

        /* access modifiers changed from: 0000 */
        public Object toFieldSetType(Object value) {
            if (!this.descriptor.isRepeated()) {
                return singularToFieldSetType(value);
            }
            if (this.descriptor.getLiteJavaType() != JavaType.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(singularToFieldSetType(element));
            }
            return result;
        }

        /* access modifiers changed from: 0000 */
        public Object singularToFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == JavaType.ENUM) {
                return Integer.valueOf(((EnumLite) value).getNumber());
            }
            return value;
        }

        public FieldType getLiteType() {
            return this.descriptor.getLiteType();
        }

        public boolean isRepeated() {
            return this.descriptor.isRepeated;
        }

        public Type getDefaultValue() {
            return this.defaultValue;
        }
    }

    static class HashCodeVisitor implements Visitor {
        int hashCode = 0;

        HashCodeVisitor() {
        }

        public boolean visitBoolean(boolean minePresent, boolean mine, boolean otherPresent, boolean other) {
            this.hashCode = (this.hashCode * 53) + Internal.hashBoolean(mine);
            return mine;
        }

        public int visitInt(boolean minePresent, int mine, boolean otherPresent, int other) {
            this.hashCode = (this.hashCode * 53) + mine;
            return mine;
        }

        public double visitDouble(boolean minePresent, double mine, boolean otherPresent, double other) {
            this.hashCode = (this.hashCode * 53) + Internal.hashLong(Double.doubleToLongBits(mine));
            return mine;
        }

        public float visitFloat(boolean minePresent, float mine, boolean otherPresent, float other) {
            this.hashCode = (this.hashCode * 53) + Float.floatToIntBits(mine);
            return mine;
        }

        public long visitLong(boolean minePresent, long mine, boolean otherPresent, long other) {
            this.hashCode = (this.hashCode * 53) + Internal.hashLong(mine);
            return mine;
        }

        public String visitString(boolean minePresent, String mine, boolean otherPresent, String other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public ByteString visitByteString(boolean minePresent, ByteString mine, boolean otherPresent, ByteString other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public Object visitOneofBoolean(boolean minePresent, Object mine, Object other) {
            this.hashCode = Internal.hashBoolean(((Boolean) mine).booleanValue()) + (this.hashCode * 53);
            return mine;
        }

        public Object visitOneofInt(boolean minePresent, Object mine, Object other) {
            this.hashCode = ((Integer) mine).intValue() + (this.hashCode * 53);
            return mine;
        }

        public Object visitOneofDouble(boolean minePresent, Object mine, Object other) {
            this.hashCode = Internal.hashLong(Double.doubleToLongBits(((Double) mine).doubleValue())) + (this.hashCode * 53);
            return mine;
        }

        public Object visitOneofFloat(boolean minePresent, Object mine, Object other) {
            this.hashCode = Float.floatToIntBits(((Float) mine).floatValue()) + (this.hashCode * 53);
            return mine;
        }

        public Object visitOneofLong(boolean minePresent, Object mine, Object other) {
            this.hashCode = Internal.hashLong(((Long) mine).longValue()) + (this.hashCode * 53);
            return mine;
        }

        public Object visitOneofString(boolean minePresent, Object mine, Object other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public Object visitOneofByteString(boolean minePresent, Object mine, Object other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public Object visitOneofMessage(boolean minePresent, Object mine, Object other) {
            return visitMessage((MessageLite) mine, (MessageLite) other);
        }

        public void visitOneofNotSet(boolean minePresent) {
            if (minePresent) {
                throw new IllegalStateException();
            }
        }

        public <T extends MessageLite> T visitMessage(T mine, T t) {
            int protoHash;
            if (mine == null) {
                protoHash = 37;
            } else if (mine instanceof GeneratedMessageLite) {
                protoHash = ((GeneratedMessageLite) mine).hashCode(this);
            } else {
                protoHash = mine.hashCode();
            }
            this.hashCode = (this.hashCode * 53) + protoHash;
            return mine;
        }

        public <T> ProtobufList<T> visitList(ProtobufList<T> mine, ProtobufList<T> protobufList) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public BooleanList visitBooleanList(BooleanList mine, BooleanList other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public IntList visitIntList(IntList mine, IntList other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public DoubleList visitDoubleList(DoubleList mine, DoubleList other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public FloatList visitFloatList(FloatList mine, FloatList other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public LongList visitLongList(LongList mine, LongList other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> mine, FieldSet<ExtensionDescriptor> fieldSet) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite mine, UnknownFieldSetLite other) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }

        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mine, MapFieldLite<K, V> mapFieldLite) {
            this.hashCode = (this.hashCode * 53) + mine.hashCode();
            return mine;
        }
    }

    protected static class MergeFromVisitor implements Visitor {
        public static final MergeFromVisitor INSTANCE = new MergeFromVisitor();

        private MergeFromVisitor() {
        }

        public boolean visitBoolean(boolean minePresent, boolean mine, boolean otherPresent, boolean other) {
            return otherPresent ? other : mine;
        }

        public int visitInt(boolean minePresent, int mine, boolean otherPresent, int other) {
            return otherPresent ? other : mine;
        }

        public double visitDouble(boolean minePresent, double mine, boolean otherPresent, double other) {
            return otherPresent ? other : mine;
        }

        public float visitFloat(boolean minePresent, float mine, boolean otherPresent, float other) {
            return otherPresent ? other : mine;
        }

        public long visitLong(boolean minePresent, long mine, boolean otherPresent, long other) {
            return otherPresent ? other : mine;
        }

        public String visitString(boolean minePresent, String mine, boolean otherPresent, String other) {
            return otherPresent ? other : mine;
        }

        public ByteString visitByteString(boolean minePresent, ByteString mine, boolean otherPresent, ByteString other) {
            return otherPresent ? other : mine;
        }

        public Object visitOneofBoolean(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofInt(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofDouble(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofFloat(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofLong(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofString(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofByteString(boolean minePresent, Object mine, Object other) {
            return other;
        }

        public Object visitOneofMessage(boolean minePresent, Object mine, Object other) {
            if (minePresent) {
                return visitMessage((MessageLite) mine, (MessageLite) other);
            }
            return other;
        }

        public void visitOneofNotSet(boolean minePresent) {
        }

        public <T extends MessageLite> T visitMessage(T mine, T other) {
            if (mine == null || other == null) {
                return mine == null ? other : mine;
            }
            return mine.toBuilder().mergeFrom((MessageLite) other).build();
        }

        public <T> ProtobufList<T> visitList(ProtobufList<T> mine, ProtobufList<T> other) {
            int size = mine.size();
            int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll(other);
            }
            return size > 0 ? mine : other;
        }

        public BooleanList visitBooleanList(BooleanList mine, BooleanList other) {
            int size = mine.size();
            int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll(other);
            }
            return size > 0 ? mine : other;
        }

        public IntList visitIntList(IntList mine, IntList other) {
            int size = mine.size();
            int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll(other);
            }
            return size > 0 ? mine : other;
        }

        public DoubleList visitDoubleList(DoubleList mine, DoubleList other) {
            int size = mine.size();
            int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll(other);
            }
            return size > 0 ? mine : other;
        }

        public FloatList visitFloatList(FloatList mine, FloatList other) {
            int size = mine.size();
            int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll(other);
            }
            return size > 0 ? mine : other;
        }

        public LongList visitLongList(LongList mine, LongList other) {
            int size = mine.size();
            int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll(other);
            }
            return size > 0 ? mine : other;
        }

        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> mine, FieldSet<ExtensionDescriptor> other) {
            if (mine.isImmutable()) {
                mine = mine.clone();
            }
            mine.mergeFrom(other);
            return mine;
        }

        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite mine, UnknownFieldSetLite other) {
            if (other == UnknownFieldSetLite.getDefaultInstance()) {
                return mine;
            }
            return UnknownFieldSetLite.mutableCopyOf(mine, other);
        }

        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mine, MapFieldLite<K, V> other) {
            if (!other.isEmpty()) {
                if (!mine.isMutable()) {
                    mine = mine.mutableCopy();
                }
                mine.mergeFrom(other);
            }
            return mine;
        }
    }

    public enum MethodToInvoke {
        IS_INITIALIZED,
        VISIT,
        MERGE_FROM_STREAM,
        MAKE_IMMUTABLE,
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    protected interface Visitor {
        boolean visitBoolean(boolean z, boolean z2, boolean z3, boolean z4);

        BooleanList visitBooleanList(BooleanList booleanList, BooleanList booleanList2);

        ByteString visitByteString(boolean z, ByteString byteString, boolean z2, ByteString byteString2);

        double visitDouble(boolean z, double d, boolean z2, double d2);

        DoubleList visitDoubleList(DoubleList doubleList, DoubleList doubleList2);

        FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> fieldSet, FieldSet<ExtensionDescriptor> fieldSet2);

        float visitFloat(boolean z, float f, boolean z2, float f2);

        FloatList visitFloatList(FloatList floatList, FloatList floatList2);

        int visitInt(boolean z, int i, boolean z2, int i2);

        IntList visitIntList(IntList intList, IntList intList2);

        <T> ProtobufList<T> visitList(ProtobufList<T> protobufList, ProtobufList<T> protobufList2);

        long visitLong(boolean z, long j, boolean z2, long j2);

        LongList visitLongList(LongList longList, LongList longList2);

        <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mapFieldLite, MapFieldLite<K, V> mapFieldLite2);

        <T extends MessageLite> T visitMessage(T t, T t2);

        Object visitOneofBoolean(boolean z, Object obj, Object obj2);

        Object visitOneofByteString(boolean z, Object obj, Object obj2);

        Object visitOneofDouble(boolean z, Object obj, Object obj2);

        Object visitOneofFloat(boolean z, Object obj, Object obj2);

        Object visitOneofInt(boolean z, Object obj, Object obj2);

        Object visitOneofLong(boolean z, Object obj, Object obj2);

        Object visitOneofMessage(boolean z, Object obj, Object obj2);

        void visitOneofNotSet(boolean z);

        Object visitOneofString(boolean z, Object obj, Object obj2);

        String visitString(boolean z, String str, boolean z2, String str2);

        UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2);
    }

    /* access modifiers changed from: protected */
    public abstract Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2);

    public final Parser<MessageType> getParserForType() {
        return (Parser) dynamicMethod(MethodToInvoke.GET_PARSER);
    }

    public final MessageType getDefaultInstanceForType() {
        return (GeneratedMessageLite) dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    public final BuilderType newBuilderForType() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    public String toString() {
        return MessageLiteToString.toString(this, super.toString());
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        HashCodeVisitor visitor = new HashCodeVisitor();
        visit(visitor, this);
        this.memoizedHashCode = visitor.hashCode;
        return this.memoizedHashCode;
    }

    /* access modifiers changed from: 0000 */
    public int hashCode(HashCodeVisitor visitor) {
        if (this.memoizedHashCode == 0) {
            int inProgressHashCode = visitor.hashCode;
            visitor.hashCode = 0;
            visit(visitor, this);
            this.memoizedHashCode = visitor.hashCode;
            visitor.hashCode = inProgressHashCode;
        }
        return this.memoizedHashCode;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!getDefaultInstanceForType().getClass().isInstance(other)) {
            return false;
        }
        try {
            visit(EqualsVisitor.INSTANCE, (GeneratedMessageLite) other);
            return true;
        } catch (NotEqualsException e) {
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean equals(EqualsVisitor visitor, MessageLite other) {
        if (this == other) {
            return true;
        }
        if (!getDefaultInstanceForType().getClass().isInstance(other)) {
            return false;
        }
        visit(visitor, (GeneratedMessageLite) other);
        return true;
    }

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.newInstance();
        }
    }

    /* access modifiers changed from: protected */
    public boolean parseUnknownField(int tag, CodedInputStream input) throws IOException {
        if (WireFormat.getTagWireType(tag) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.mergeFieldFrom(tag, input);
    }

    /* access modifiers changed from: protected */
    public void mergeVarintField(int tag, int value) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeVarintField(tag, value);
    }

    /* access modifiers changed from: protected */
    public void mergeLengthDelimitedField(int fieldNumber, ByteString value) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeLengthDelimitedField(fieldNumber, value);
    }

    /* access modifiers changed from: protected */
    public void makeImmutable() {
        dynamicMethod(MethodToInvoke.MAKE_IMMUTABLE);
        this.unknownFields.makeImmutable();
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder(MessageType prototype) {
        return createBuilder().mergeFrom(prototype);
    }

    public final boolean isInitialized() {
        return isInitialized(this, Boolean.TRUE.booleanValue());
    }

    public final BuilderType toBuilder() {
        BuilderType builder = (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
        builder.mergeFrom(this);
        return builder;
    }

    /* access modifiers changed from: protected */
    public Object dynamicMethod(MethodToInvoke method, Object arg0) {
        return dynamicMethod(method, arg0, null);
    }

    /* access modifiers changed from: protected */
    public Object dynamicMethod(MethodToInvoke method) {
        return dynamicMethod(method, null, null);
    }

    /* access modifiers changed from: 0000 */
    public void visit(Visitor visitor, MessageType other) {
        dynamicMethod(MethodToInvoke.VISIT, visitor, other);
        this.unknownFields = visitor.visitUnknownFields(this.unknownFields, other.unknownFields);
    }

    /* access modifiers changed from: 0000 */
    public int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize;
    }

    /* access modifiers changed from: 0000 */
    public void setMemoizedSerializedSize(int size) {
        this.memoizedSerializedSize = size;
    }

    /* access modifiers changed from: protected */
    public final void mergeUnknownFields(UnknownFieldSetLite unknownFields2) {
        this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFields2);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, EnumLiteMap<?> enumTypeMap, int number, FieldType type, Class singularType) {
        GeneratedExtension generatedExtension = new GeneratedExtension(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, false, false), singularType);
        return generatedExtension;
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingTypeDefaultInstance, MessageLite messageDefaultInstance, EnumLiteMap<?> enumTypeMap, int number, FieldType type, boolean isPacked, Class singularType) {
        GeneratedExtension generatedExtension = new GeneratedExtension(containingTypeDefaultInstance, Collections.emptyList(), messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, true, isPacked), singularType);
        return generatedExtension;
    }

    static Method getMethodOrDie(Class clazz, String name, Class... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e);
        }
    }

    static Object invokeOrDie(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    /* access modifiers changed from: private */
    public static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(ExtensionLite<MessageType, T> extension) {
        if (extension.isLite()) {
            return (GeneratedExtension) extension;
        }
        throw new IllegalArgumentException("Expected a lite extension.");
    }

    protected static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(T message, boolean shouldMemoize) {
        boolean isInitialized = true;
        byte memoizedIsInitialized = ((Byte) message.dynamicMethod(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (memoizedIsInitialized == 1) {
            return true;
        }
        if (memoizedIsInitialized == 0) {
            return false;
        }
        if (message.dynamicMethod(MethodToInvoke.IS_INITIALIZED, Boolean.FALSE) == null) {
            isInitialized = false;
        }
        if (!shouldMemoize) {
            return isInitialized;
        }
        message.dynamicMethod(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, isInitialized ? message : null);
        return isInitialized;
    }

    protected static IntList emptyIntList() {
        return IntArrayList.emptyList();
    }

    protected static IntList mutableCopy(IntList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static LongList emptyLongList() {
        return LongArrayList.emptyList();
    }

    protected static LongList mutableCopy(LongList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }

    protected static FloatList mutableCopy(FloatList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }

    protected static DoubleList mutableCopy(DoubleList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }

    protected static BooleanList mutableCopy(BooleanList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static <E> ProtobufList<E> emptyProtobufList() {
        return ProtobufArrayList.emptyList();
    }

    protected static <E> ProtobufList<E> mutableCopy(ProtobufList<E> list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T instance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        T result = (GeneratedMessageLite) instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            result.dynamicMethod(MethodToInvoke.MERGE_FROM_STREAM, input, extensionRegistry);
            result.makeImmutable();
            return result;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e.getCause());
            }
            throw e;
        }
    }

    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T instance, byte[] input) throws InvalidProtocolBufferException {
        return parsePartialFrom(instance, input, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, CodedInputStream input) throws InvalidProtocolBufferException {
        return parsePartialFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
    }

    private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T message) throws InvalidProtocolBufferException {
        if (message == null || message.isInitialized()) {
            return message;
        }
        throw message.newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(message);
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parseFrom(defaultInstance, CodedInputStream.newInstance(data), extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteBuffer data) throws InvalidProtocolBufferException {
        return parseFrom(defaultInstance, data, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteString data) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parseFrom(defaultInstance, data, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(defaultInstance, data, extensionRegistry));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        T message;
        try {
            CodedInputStream input = data.newCodedInput();
            message = parsePartialFrom(defaultInstance, input, extensionRegistry);
            input.checkLastTagWas(0);
            return message;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(message);
        } catch (InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        T message;
        try {
            CodedInputStream input = CodedInputStream.newInstance(data);
            message = parsePartialFrom(defaultInstance, input, extensionRegistry);
            input.checkLastTagWas(0);
            return message;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(message);
        } catch (InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, byte[] data) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(defaultInstance, data));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(defaultInstance, data, extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, InputStream input) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(defaultInstance, CodedInputStream.newInstance(input), ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(defaultInstance, CodedInputStream.newInstance(input), extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, CodedInputStream input) throws InvalidProtocolBufferException {
        return parseFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(defaultInstance, input, extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T defaultInstance, InputStream input) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialDelimitedFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialDelimitedFrom(defaultInstance, input, extensionRegistry));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        try {
            int firstByte = input.read();
            if (firstByte == -1) {
                return null;
            }
            CodedInputStream codedInput = CodedInputStream.newInstance(new LimitedInputStream(input, CodedInputStream.readRawVarint32(firstByte, input)));
            T message = parsePartialFrom(defaultInstance, codedInput, extensionRegistry);
            try {
                codedInput.checkLastTagWas(0);
                return message;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(message);
            }
        } catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2.getMessage());
        }
    }
}
