package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.Syntax;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class GeneratedMessage extends AbstractMessage implements Serializable {
    protected static boolean alwaysUseFieldBuilders = false;
    private static final long serialVersionUID = 1;
    protected UnknownFieldSet unknownFields;

    public static abstract class Builder<BuilderType extends Builder<BuilderType>> extends com.google.protobuf.AbstractMessage.Builder<BuilderType> {
        private BuilderParent builderParent;
        private boolean isClean;
        private BuilderParentImpl meAsParent;
        private UnknownFieldSet unknownFields;

        private class BuilderParentImpl implements BuilderParent {
            private BuilderParentImpl() {
            }

            public void markDirty() {
                Builder.this.onChanged();
            }
        }

        /* access modifiers changed from: protected */
        public abstract FieldAccessorTable internalGetFieldAccessorTable();

        protected Builder() {
            this(null);
        }

        protected Builder(BuilderParent builderParent2) {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.builderParent = builderParent2;
        }

        /* access modifiers changed from: 0000 */
        public void dispose() {
            this.builderParent = null;
        }

        /* access modifiers changed from: protected */
        public void onBuilt() {
            if (this.builderParent != null) {
                markClean();
            }
        }

        /* access modifiers changed from: protected */
        public void markClean() {
            this.isClean = true;
        }

        /* access modifiers changed from: protected */
        public boolean isClean() {
            return this.isClean;
        }

        public BuilderType clone() {
            BuilderType builder = (Builder) getDefaultInstanceForType().newBuilderForType();
            builder.mergeFrom(buildPartial());
            return builder;
        }

        public BuilderType clear() {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            onChanged();
            return this;
        }

        public Descriptor getDescriptorForType() {
            return internalGetFieldAccessorTable().descriptor;
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            return Collections.unmodifiableMap(getAllFieldsMutable());
        }

        /* access modifiers changed from: private */
        public Map<FieldDescriptor, Object> getAllFieldsMutable() {
            TreeMap<FieldDescriptor, Object> result = new TreeMap<>();
            List<FieldDescriptor> fields = internalGetFieldAccessorTable().descriptor.getFields();
            int i = 0;
            while (i < fields.size()) {
                FieldDescriptor field = (FieldDescriptor) fields.get(i);
                OneofDescriptor oneofDescriptor = field.getContainingOneof();
                if (oneofDescriptor != null) {
                    i += oneofDescriptor.getFieldCount() - 1;
                    if (hasOneof(oneofDescriptor)) {
                        field = getOneofFieldDescriptor(oneofDescriptor);
                        result.put(field, getField(field));
                    }
                } else if (field.isRepeated()) {
                    List<?> value = (List) getField(field);
                    if (!value.isEmpty()) {
                        result.put(field, value);
                    }
                } else {
                    if (!hasField(field)) {
                    }
                    result.put(field, getField(field));
                }
                i++;
            }
            return result;
        }

        public com.google.protobuf.Message.Builder newBuilderForField(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).newBuilder();
        }

        public com.google.protobuf.Message.Builder getFieldBuilder(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).getBuilder(this);
        }

        public com.google.protobuf.Message.Builder getRepeatedFieldBuilder(FieldDescriptor field, int index) {
            return internalGetFieldAccessorTable().getField(field).getRepeatedBuilder(this, index);
        }

        public boolean hasOneof(OneofDescriptor oneof) {
            return internalGetFieldAccessorTable().getOneof(oneof).has(this);
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            return internalGetFieldAccessorTable().getOneof(oneof).get(this);
        }

        public boolean hasField(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).has(this);
        }

        public Object getField(FieldDescriptor field) {
            Object object = internalGetFieldAccessorTable().getField(field).get(this);
            if (field.isRepeated()) {
                return Collections.unmodifiableList((List) object);
            }
            return object;
        }

        public BuilderType setField(FieldDescriptor field, Object value) {
            internalGetFieldAccessorTable().getField(field).set(this, value);
            return this;
        }

        public BuilderType clearField(FieldDescriptor field) {
            internalGetFieldAccessorTable().getField(field).clear(this);
            return this;
        }

        public BuilderType clearOneof(OneofDescriptor oneof) {
            internalGetFieldAccessorTable().getOneof(oneof).clear(this);
            return this;
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            return internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
        }

        public BuilderType setRepeatedField(FieldDescriptor field, int index, Object value) {
            internalGetFieldAccessorTable().getField(field).setRepeated(this, index, value);
            return this;
        }

        public BuilderType addRepeatedField(FieldDescriptor field, Object value) {
            internalGetFieldAccessorTable().getField(field).addRepeated(this, value);
            return this;
        }

        public BuilderType setUnknownFields(UnknownFieldSet unknownFields2) {
            this.unknownFields = unknownFields2;
            onChanged();
            return this;
        }

        public BuilderType mergeUnknownFields(UnknownFieldSet unknownFields2) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields2).build();
            onChanged();
            return this;
        }

        public boolean isInitialized() {
            for (FieldDescriptor field : getDescriptorForType().getFields()) {
                if (field.isRequired() && !hasField(field)) {
                    return false;
                }
                if (field.getJavaType() == JavaType.MESSAGE) {
                    if (field.isRepeated()) {
                        for (Message element : (List) getField(field)) {
                            if (!element.isInitialized()) {
                                return false;
                            }
                        }
                        continue;
                    } else if (hasField(field) && !((Message) getField(field)).isInitialized()) {
                        return false;
                    }
                }
            }
            return true;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        /* access modifiers changed from: protected */
        public boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields2, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return unknownFields2.mergeFieldFrom(tag, input);
        }

        /* access modifiers changed from: protected */
        public BuilderParent getParentForChildren() {
            if (this.meAsParent == null) {
                this.meAsParent = new BuilderParentImpl<>();
            }
            return this.meAsParent;
        }

        /* access modifiers changed from: protected */
        public final void onChanged() {
            if (this.isClean && this.builderParent != null) {
                this.builderParent.markDirty();
                this.isClean = false;
            }
        }

        /* access modifiers changed from: protected */
        public MapField internalGetMapField(int fieldNumber) {
            throw new RuntimeException("No map fields found in " + getClass().getName());
        }

        /* access modifiers changed from: protected */
        public MapField internalGetMutableMapField(int fieldNumber) {
            throw new RuntimeException("No map fields found in " + getClass().getName());
        }
    }

    protected interface BuilderParent extends BuilderParent {
    }

    private static abstract class CachedDescriptorRetriever implements ExtensionDescriptorRetriever {
        private volatile FieldDescriptor descriptor;

        /* access modifiers changed from: protected */
        public abstract FieldDescriptor loadDescriptor();

        private CachedDescriptorRetriever() {
        }

        public FieldDescriptor getDescriptor() {
            if (this.descriptor == null) {
                synchronized (this) {
                    if (this.descriptor == null) {
                        this.descriptor = loadDescriptor();
                    }
                }
            }
            return this.descriptor;
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<BuilderType> implements ExtendableMessageOrBuilder<MessageType> {
        private FieldSet<FieldDescriptor> extensions = FieldSet.emptySet();

        protected ExtendableBuilder() {
        }

        protected ExtendableBuilder(BuilderParent parent) {
            super(parent);
        }

        /* access modifiers changed from: 0000 */
        public void internalSetExtensionSet(FieldSet<FieldDescriptor> extensions2) {
            this.extensions = extensions2;
        }

        public BuilderType clear() {
            this.extensions = FieldSet.emptySet();
            return (ExtendableBuilder) super.clear();
        }

        public BuilderType clone() {
            return (ExtendableBuilder) super.clone();
        }

        private void ensureExtensionsIsMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
            }
        }

        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, Type> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }

        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            Extension<MessageType, List<Type>> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.getDescriptor());
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, Type> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            FieldDescriptor descriptor = extension.getDescriptor();
            Object value = this.extensions.getField(descriptor);
            if (value != null) {
                return extension.fromReflectionType(value);
            }
            if (descriptor.isRepeated()) {
                return Collections.emptyList();
            }
            if (descriptor.getJavaType() == JavaType.MESSAGE) {
                return extension.getMessageDefaultInstance();
            }
            return extension.fromReflectionType(descriptor.getDefaultValue());
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int index) {
            Extension<MessageType, List<Type>> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            return extension.singularFromReflectionType(this.extensions.getRepeatedField(extension.getDescriptor(), index));
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extensionLite, Type value) {
            Extension<MessageType, Type> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.setField(extension.getDescriptor(), extension.toReflectionType(value));
            onChanged();
            return this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int index, Type value) {
            Extension<MessageType, List<Type>> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(extension.getDescriptor(), index, extension.singularToReflectionType(value));
            onChanged();
            return this;
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extensionLite, Type value) {
            Extension<MessageType, List<Type>> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(extension.getDescriptor(), extension.singularToReflectionType(value));
            onChanged();
            return this;
        }

        public final <Type> BuilderType clearExtension(ExtensionLite<MessageType, ?> extensionLite) {
            Extension<MessageType, ?> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.clearField(extension.getDescriptor());
            onChanged();
            return this;
        }

        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            return hasExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> extension) {
            return hasExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            return getExtensionCount((ExtensionLite<MessageType, List<Type>>) extension);
        }

        public final <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> extension) {
            return getExtensionCount((ExtensionLite<MessageType, List<Type>>) extension);
        }

        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            return getExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> Type getExtension(GeneratedExtension<MessageType, Type> extension) {
            return getExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int index) {
            return getExtension((ExtensionLite<MessageType, List<Type>>) extension, index);
        }

        public final <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> extension, int index) {
            return getExtension((ExtensionLite<MessageType, List<Type>>) extension, index);
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, Type> extension, Type value) {
            return setExtension((ExtensionLite<MessageType, Type>) extension, value);
        }

        public <Type> BuilderType setExtension(GeneratedExtension<MessageType, Type> extension, Type value) {
            return setExtension((ExtensionLite<MessageType, Type>) extension, value);
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, List<Type>> extension, int index, Type value) {
            return setExtension((ExtensionLite<MessageType, List<Type>>) extension, index, value);
        }

        public <Type> BuilderType setExtension(GeneratedExtension<MessageType, List<Type>> extension, int index, Type value) {
            return setExtension((ExtensionLite<MessageType, List<Type>>) extension, index, value);
        }

        public final <Type> BuilderType addExtension(Extension<MessageType, List<Type>> extension, Type value) {
            return addExtension((ExtensionLite<MessageType, List<Type>>) extension, value);
        }

        public <Type> BuilderType addExtension(GeneratedExtension<MessageType, List<Type>> extension, Type value) {
            return addExtension((ExtensionLite<MessageType, List<Type>>) extension, value);
        }

        public final <Type> BuilderType clearExtension(Extension<MessageType, ?> extension) {
            return clearExtension((ExtensionLite<MessageType, ?>) extension);
        }

        public <Type> BuilderType clearExtension(GeneratedExtension<MessageType, ?> extension) {
            return clearExtension((ExtensionLite<MessageType, ?>) extension);
        }

        /* access modifiers changed from: protected */
        public boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        /* access modifiers changed from: private */
        public FieldSet<FieldDescriptor> buildExtensions() {
            this.extensions.makeImmutable();
            return this.extensions;
        }

        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        /* access modifiers changed from: protected */
        public boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, getDescriptorForType(), new BuilderAdapter(this), tag);
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            Map<FieldDescriptor, Object> result = getAllFieldsMutable();
            result.putAll(this.extensions.getAllFields());
            return Collections.unmodifiableMap(result);
        }

        public Object getField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getField(field);
            }
            verifyContainingType(field);
            Object value = this.extensions.getField(field);
            if (value != null) {
                return value;
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getRepeatedFieldCount(field);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedFieldCount(field);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            if (!field.isExtension()) {
                return super.getRepeatedField(field, index);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedField(field, index);
        }

        public boolean hasField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.hasField(field);
            }
            verifyContainingType(field);
            return this.extensions.hasField(field);
        }

        public BuilderType setField(FieldDescriptor field, Object value) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.setField(field, value);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.setField(field, value);
            onChanged();
            return this;
        }

        public BuilderType clearField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.clearField(field);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.clearField(field);
            onChanged();
            return this;
        }

        public BuilderType setRepeatedField(FieldDescriptor field, int index, Object value) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.setRepeatedField(field, index, value);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(field, index, value);
            onChanged();
            return this;
        }

        public BuilderType addRepeatedField(FieldDescriptor field, Object value) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.addRepeatedField(field, value);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(field, value);
            onChanged();
            return this;
        }

        /* access modifiers changed from: protected */
        public final void mergeExtensionFields(ExtendableMessage other) {
            ensureExtensionsIsMutable();
            this.extensions.mergeFrom(other.extensions);
            onChanged();
        }

        private void verifyContainingType(FieldDescriptor field) {
            if (field.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage> extends GeneratedMessage implements ExtendableMessageOrBuilder<MessageType> {
        private static final long serialVersionUID = 1;
        /* access modifiers changed from: private */
        public final FieldSet<FieldDescriptor> extensions;

        protected class ExtensionWriter {
            private final Iterator<Entry<FieldDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Entry<FieldDescriptor, Object> next;

            private ExtensionWriter(boolean messageSetWireFormat2) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = (Entry) this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat2;
            }

            public void writeUntil(int end, CodedOutputStream output) throws IOException {
                while (this.next != null && ((FieldDescriptor) this.next.getKey()).getNumber() < end) {
                    FieldDescriptor descriptor = (FieldDescriptor) this.next.getKey();
                    if (!this.messageSetWireFormat || descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated()) {
                        FieldSet.writeField(descriptor, this.next.getValue(), output);
                    } else if (this.next instanceof LazyEntry) {
                        output.writeRawMessageSetExtension(descriptor.getNumber(), ((LazyEntry) this.next).getField().toByteString());
                    } else {
                        output.writeMessageSetExtension(descriptor.getNumber(), (Message) this.next.getValue());
                    }
                    if (this.iter.hasNext()) {
                        this.next = (Entry) this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }

        protected ExtendableMessage(ExtendableBuilder<MessageType, ?> builder) {
            super(builder);
            this.extensions = builder.buildExtensions();
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
            }
        }

        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, Type> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }

        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            Extension<MessageType, List<Type>> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.getDescriptor());
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, Type> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            FieldDescriptor descriptor = extension.getDescriptor();
            Object value = this.extensions.getField(descriptor);
            if (value != null) {
                return extension.fromReflectionType(value);
            }
            if (descriptor.isRepeated()) {
                return Collections.emptyList();
            }
            if (descriptor.getJavaType() == JavaType.MESSAGE) {
                return extension.getMessageDefaultInstance();
            }
            return extension.fromReflectionType(descriptor.getDefaultValue());
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int index) {
            Extension<MessageType, List<Type>> extension = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extension);
            return extension.singularFromReflectionType(this.extensions.getRepeatedField(extension.getDescriptor(), index));
        }

        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            return hasExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> extension) {
            return hasExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            return getExtensionCount((ExtensionLite<MessageType, List<Type>>) extension);
        }

        public final <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> extension) {
            return getExtensionCount((ExtensionLite<MessageType, List<Type>>) extension);
        }

        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            return getExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> Type getExtension(GeneratedExtension<MessageType, Type> extension) {
            return getExtension((ExtensionLite<MessageType, Type>) extension);
        }

        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int index) {
            return getExtension((ExtensionLite<MessageType, List<Type>>) extension, index);
        }

        public final <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> extension, int index) {
            return getExtension((ExtensionLite<MessageType, List<Type>>) extension, index);
        }

        /* access modifiers changed from: protected */
        public boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        public boolean isInitialized() {
            return GeneratedMessage.super.isInitialized() && extensionsAreInitialized();
        }

        /* access modifiers changed from: protected */
        public boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, getDescriptorForType(), new ExtensionAdapter(this.extensions), tag);
        }

        /* access modifiers changed from: protected */
        public void makeExtensionsImmutable() {
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

        /* access modifiers changed from: protected */
        public Map<FieldDescriptor, Object> getExtensionFields() {
            return this.extensions.getAllFields();
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            Map<FieldDescriptor, Object> result = getAllFieldsMutable(false);
            result.putAll(getExtensionFields());
            return Collections.unmodifiableMap(result);
        }

        public Map<FieldDescriptor, Object> getAllFieldsRaw() {
            Map<FieldDescriptor, Object> result = getAllFieldsMutable(false);
            result.putAll(getExtensionFields());
            return Collections.unmodifiableMap(result);
        }

        public boolean hasField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return GeneratedMessage.super.hasField(field);
            }
            verifyContainingType(field);
            return this.extensions.hasField(field);
        }

        public Object getField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return GeneratedMessage.super.getField(field);
            }
            verifyContainingType(field);
            Object value = this.extensions.getField(field);
            if (value != null) {
                return value;
            }
            if (field.isRepeated()) {
                return Collections.emptyList();
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            if (!field.isExtension()) {
                return GeneratedMessage.super.getRepeatedFieldCount(field);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedFieldCount(field);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            if (!field.isExtension()) {
                return GeneratedMessage.super.getRepeatedField(field, index);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedField(field, index);
        }

        private void verifyContainingType(FieldDescriptor field) {
            if (field.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage> extends MessageOrBuilder {
        Message getDefaultInstanceForType();

        <Type> Type getExtension(Extension<MessageType, Type> extension);

        <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i);

        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i);

        <Type> Type getExtension(GeneratedExtension<MessageType, Type> generatedExtension);

        <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> generatedExtension, int i);

        <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> generatedExtension);

        <Type> boolean hasExtension(Extension<MessageType, Type> extension);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> generatedExtension);
    }

    interface ExtensionDescriptorRetriever {
        FieldDescriptor getDescriptor();
    }

    public static final class FieldAccessorTable {
        private String[] camelCaseNames;
        /* access modifiers changed from: private */
        public final Descriptor descriptor;
        private final FieldAccessor[] fields;
        private volatile boolean initialized;
        private final OneofAccessor[] oneofs;

        private interface FieldAccessor {
            void addRepeated(Builder builder, Object obj);

            void clear(Builder builder);

            Object get(Builder builder);

            Object get(GeneratedMessage generatedMessage);

            com.google.protobuf.Message.Builder getBuilder(Builder builder);

            Object getRaw(Builder builder);

            Object getRaw(GeneratedMessage generatedMessage);

            Object getRepeated(Builder builder, int i);

            Object getRepeated(GeneratedMessage generatedMessage, int i);

            com.google.protobuf.Message.Builder getRepeatedBuilder(Builder builder, int i);

            int getRepeatedCount(Builder builder);

            int getRepeatedCount(GeneratedMessage generatedMessage);

            Object getRepeatedRaw(Builder builder, int i);

            Object getRepeatedRaw(GeneratedMessage generatedMessage, int i);

            boolean has(Builder builder);

            boolean has(GeneratedMessage generatedMessage);

            com.google.protobuf.Message.Builder newBuilder();

            void set(Builder builder, Object obj);

            void setRepeated(Builder builder, int i, Object obj);
        }

        private static class MapFieldAccessor implements FieldAccessor {
            private final FieldDescriptor field;
            private final Message mapEntryMessageDefaultInstance;

            MapFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> cls) {
                this.field = descriptor;
                this.mapEntryMessageDefaultInstance = getMapField((GeneratedMessage) GeneratedMessage.invokeOrDie(GeneratedMessage.getMethodOrDie(messageClass, "getDefaultInstance", new Class[0]), null, new Object[0])).getMapEntryMessageDefaultInstance();
            }

            private MapField<?, ?> getMapField(GeneratedMessage message) {
                return message.internalGetMapField(this.field.getNumber());
            }

            private MapField<?, ?> getMapField(Builder builder) {
                return builder.internalGetMapField(this.field.getNumber());
            }

            private MapField<?, ?> getMutableMapField(Builder builder) {
                return builder.internalGetMutableMapField(this.field.getNumber());
            }

            public Object get(GeneratedMessage message) {
                List result = new ArrayList();
                for (int i = 0; i < getRepeatedCount(message); i++) {
                    result.add(getRepeated(message, i));
                }
                return Collections.unmodifiableList(result);
            }

            public Object get(Builder builder) {
                List result = new ArrayList();
                for (int i = 0; i < getRepeatedCount(builder); i++) {
                    result.add(getRepeated(builder, i));
                }
                return Collections.unmodifiableList(result);
            }

            public Object getRaw(GeneratedMessage message) {
                return get(message);
            }

            public Object getRaw(Builder builder) {
                return get(builder);
            }

            public void set(Builder builder, Object value) {
                clear(builder);
                for (Object entry : (List) value) {
                    addRepeated(builder, entry);
                }
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                return getMapField(message).getList().get(index);
            }

            public Object getRepeated(Builder builder, int index) {
                return getMapField(builder).getList().get(index);
            }

            public Object getRepeatedRaw(GeneratedMessage message, int index) {
                return getRepeated(message, index);
            }

            public Object getRepeatedRaw(Builder builder, int index) {
                return getRepeated(builder, index);
            }

            public void setRepeated(Builder builder, int index, Object value) {
                getMutableMapField(builder).getMutableList().set(index, (Message) value);
            }

            public void addRepeated(Builder builder, Object value) {
                getMutableMapField(builder).getMutableList().add((Message) value);
            }

            public boolean has(GeneratedMessage message) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }

            public boolean has(Builder builder) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }

            public int getRepeatedCount(GeneratedMessage message) {
                return getMapField(message).getList().size();
            }

            public int getRepeatedCount(Builder builder) {
                return getMapField(builder).getList().size();
            }

            public void clear(Builder builder) {
                getMutableMapField(builder).getMutableList().clear();
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                return this.mapEntryMessageDefaultInstance.newBuilderForType();
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }

            public com.google.protobuf.Message.Builder getRepeatedBuilder(Builder builder, int index) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }
        }

        private static class OneofAccessor {
            private final Method caseMethod;
            private final Method caseMethodBuilder;
            private final Method clearMethod;
            private final Descriptor descriptor;

            OneofAccessor(Descriptor descriptor2, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                this.descriptor = descriptor2;
                this.caseMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Case", new Class[0]);
                this.caseMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Case", new Class[0]);
                this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
            }

            public boolean has(GeneratedMessage message) {
                if (((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber() == 0) {
                    return false;
                }
                return true;
            }

            public boolean has(Builder builder) {
                if (((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() == 0) {
                    return false;
                }
                return true;
            }

            public FieldDescriptor get(GeneratedMessage message) {
                int fieldNumber = ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
                if (fieldNumber > 0) {
                    return this.descriptor.findFieldByNumber(fieldNumber);
                }
                return null;
            }

            public FieldDescriptor get(Builder builder) {
                int fieldNumber = ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                if (fieldNumber > 0) {
                    return this.descriptor.findFieldByNumber(fieldNumber);
                }
                return null;
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }
        }

        private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor {
            private Method addRepeatedValueMethod;
            private EnumDescriptor enumDescriptor;
            private Method getRepeatedValueMethod;
            private Method getRepeatedValueMethodBuilder;
            private final Method getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
            private Method setRepeatedValueMethod;
            private boolean supportUnknownEnumValue;
            private final Method valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", EnumValueDescriptor.class);

            RepeatedEnumFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.enumDescriptor = descriptor.getEnumType();
                this.supportUnknownEnumValue = descriptor.getFile().supportsUnknownEnumValue();
                if (this.supportUnknownEnumValue) {
                    this.getRepeatedValueMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Value", Integer.TYPE);
                    this.getRepeatedValueMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Value", Integer.TYPE);
                    this.setRepeatedValueMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName + "Value", Integer.TYPE, Integer.TYPE);
                    this.addRepeatedValueMethod = GeneratedMessage.getMethodOrDie(builderClass, "add" + camelCaseName + "Value", Integer.TYPE);
                }
            }

            public Object get(GeneratedMessage message) {
                List newList = new ArrayList();
                int size = getRepeatedCount(message);
                for (int i = 0; i < size; i++) {
                    newList.add(getRepeated(message, i));
                }
                return Collections.unmodifiableList(newList);
            }

            public Object get(Builder builder) {
                List newList = new ArrayList();
                int size = getRepeatedCount(builder);
                for (int i = 0; i < size; i++) {
                    newList.add(getRepeated(builder, i));
                }
                return Collections.unmodifiableList(newList);
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                if (!this.supportUnknownEnumValue) {
                    return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(message, index), new Object[0]);
                }
                return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getRepeatedValueMethod, message, Integer.valueOf(index))).intValue());
            }

            public Object getRepeated(Builder builder, int index) {
                if (!this.supportUnknownEnumValue) {
                    return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, index), new Object[0]);
                }
                return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getRepeatedValueMethodBuilder, builder, Integer.valueOf(index))).intValue());
            }

            public void setRepeated(Builder builder, int index, Object value) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessage.invokeOrDie(this.setRepeatedValueMethod, builder, Integer.valueOf(index), Integer.valueOf(((EnumValueDescriptor) value).getNumber()));
                    return;
                }
                super.setRepeated(builder, index, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, value));
            }

            public void addRepeated(Builder builder, Object value) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessage.invokeOrDie(this.addRepeatedValueMethod, builder, Integer.valueOf(((EnumValueDescriptor) value).getNumber()));
                    return;
                }
                super.addRepeated(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, value));
            }
        }

        private static class RepeatedFieldAccessor implements FieldAccessor {
            protected final Method addRepeatedMethod;
            protected final Method clearMethod;
            protected final Method getCountMethod;
            protected final Method getCountMethodBuilder;
            protected final Method getMethod;
            protected final Method getMethodBuilder;
            protected final Method getRepeatedMethod;
            protected final Method getRepeatedMethodBuilder;
            protected final Method setRepeatedMethod;
            protected final Class type = this.getRepeatedMethod.getReturnType();

            RepeatedFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                this.getMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "List", new Class[0]);
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "List", new Class[0]);
                this.getRepeatedMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName, Integer.TYPE);
                this.getRepeatedMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName, Integer.TYPE);
                this.setRepeatedMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName, Integer.TYPE, this.type);
                this.addRepeatedMethod = GeneratedMessage.getMethodOrDie(builderClass, "add" + camelCaseName, this.type);
                this.getCountMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Count", new Class[0]);
                this.getCountMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Count", new Class[0]);
                this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
            }

            public Object get(GeneratedMessage message) {
                return GeneratedMessage.invokeOrDie(this.getMethod, message, new Object[0]);
            }

            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            public Object getRaw(GeneratedMessage message) {
                return get(message);
            }

            public Object getRaw(Builder builder) {
                return get(builder);
            }

            public void set(Builder builder, Object value) {
                clear(builder);
                for (Object element : (List) value) {
                    addRepeated(builder, element);
                }
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethod, message, Integer.valueOf(index));
            }

            public Object getRepeated(Builder builder, int index) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethodBuilder, builder, Integer.valueOf(index));
            }

            public Object getRepeatedRaw(GeneratedMessage message, int index) {
                return getRepeated(message, index);
            }

            public Object getRepeatedRaw(Builder builder, int index) {
                return getRepeated(builder, index);
            }

            public void setRepeated(Builder builder, int index, Object value) {
                GeneratedMessage.invokeOrDie(this.setRepeatedMethod, builder, Integer.valueOf(index), value);
            }

            public void addRepeated(Builder builder, Object value) {
                GeneratedMessage.invokeOrDie(this.addRepeatedMethod, builder, value);
            }

            public boolean has(GeneratedMessage message) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            public boolean has(Builder builder) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            public int getRepeatedCount(GeneratedMessage message) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethod, message, new Object[0])).intValue();
            }

            public int getRepeatedCount(Builder builder) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethodBuilder, builder, new Object[0])).intValue();
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }

            public com.google.protobuf.Message.Builder getRepeatedBuilder(Builder builder, int index) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
        }

        private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor {
            private final Method getBuilderMethodBuilder;
            private final Method newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);

            RepeatedMessageFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", Integer.TYPE);
            }

            private Object coerceType(Object value) {
                if (this.type.isInstance(value)) {
                    return value;
                }
                return ((com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) value).build();
            }

            public void setRepeated(Builder builder, int index, Object value) {
                super.setRepeated(builder, index, coerceType(value));
            }

            public void addRepeated(Builder builder, Object value) {
                super.addRepeated(builder, coerceType(value));
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            public com.google.protobuf.Message.Builder getRepeatedBuilder(Builder builder, int index) {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, Integer.valueOf(index));
            }
        }

        private static final class SingularEnumFieldAccessor extends SingularFieldAccessor {
            private EnumDescriptor enumDescriptor;
            private Method getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
            private Method getValueMethod;
            private Method getValueMethodBuilder;
            private Method setValueMethod;
            private boolean supportUnknownEnumValue;
            private Method valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", EnumValueDescriptor.class);

            SingularEnumFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.enumDescriptor = descriptor.getEnumType();
                this.supportUnknownEnumValue = descriptor.getFile().supportsUnknownEnumValue();
                if (this.supportUnknownEnumValue) {
                    this.getValueMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Value", new Class[0]);
                    this.getValueMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Value", new Class[0]);
                    this.setValueMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName + "Value", Integer.TYPE);
                }
            }

            public Object get(GeneratedMessage message) {
                if (!this.supportUnknownEnumValue) {
                    return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(message), new Object[0]);
                }
                return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getValueMethod, message, new Object[0])).intValue());
            }

            public Object get(Builder builder) {
                if (!this.supportUnknownEnumValue) {
                    return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
                }
                return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getValueMethodBuilder, builder, new Object[0])).intValue());
            }

            public void set(Builder builder, Object value) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessage.invokeOrDie(this.setValueMethod, builder, Integer.valueOf(((EnumValueDescriptor) value).getNumber()));
                    return;
                }
                super.set(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, value));
            }
        }

        private static class SingularFieldAccessor implements FieldAccessor {
            protected final Method caseMethod;
            protected final Method caseMethodBuilder;
            protected final Method clearMethod;
            protected final FieldDescriptor field;
            protected final Method getMethod;
            protected final Method getMethodBuilder;
            protected final boolean hasHasMethod;
            protected final Method hasMethod;
            protected final Method hasMethodBuilder;
            protected final boolean isOneofField;
            protected final Method setMethod;
            protected final Class<?> type;

            SingularFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                boolean z;
                Method method;
                Method method2;
                Method method3;
                Method method4 = null;
                this.field = descriptor;
                this.isOneofField = descriptor.getContainingOneof() != null;
                if (FieldAccessorTable.supportFieldPresence(descriptor.getFile()) || (!this.isOneofField && descriptor.getJavaType() == JavaType.MESSAGE)) {
                    z = true;
                } else {
                    z = false;
                }
                this.hasHasMethod = z;
                this.getMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName, new Class[0]);
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName, new Class[0]);
                this.type = this.getMethod.getReturnType();
                this.setMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName, this.type);
                if (this.hasHasMethod) {
                    method = GeneratedMessage.getMethodOrDie(messageClass, "has" + camelCaseName, new Class[0]);
                } else {
                    method = null;
                }
                this.hasMethod = method;
                if (this.hasHasMethod) {
                    method2 = GeneratedMessage.getMethodOrDie(builderClass, "has" + camelCaseName, new Class[0]);
                } else {
                    method2 = null;
                }
                this.hasMethodBuilder = method2;
                this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
                if (this.isOneofField) {
                    method3 = GeneratedMessage.getMethodOrDie(messageClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]);
                } else {
                    method3 = null;
                }
                this.caseMethod = method3;
                if (this.isOneofField) {
                    method4 = GeneratedMessage.getMethodOrDie(builderClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]);
                }
                this.caseMethodBuilder = method4;
            }

            private int getOneofFieldNumber(GeneratedMessage message) {
                return ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
            }

            private int getOneofFieldNumber(Builder builder) {
                return ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
            }

            public Object get(GeneratedMessage message) {
                return GeneratedMessage.invokeOrDie(this.getMethod, message, new Object[0]);
            }

            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            public Object getRaw(GeneratedMessage message) {
                return get(message);
            }

            public Object getRaw(Builder builder) {
                return get(builder);
            }

            public void set(Builder builder, Object value) {
                GeneratedMessage.invokeOrDie(this.setMethod, builder, value);
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            public Object getRepeatedRaw(GeneratedMessage message, int index) {
                throw new UnsupportedOperationException("getRepeatedFieldRaw() called on a singular field.");
            }

            public Object getRepeated(Builder builder, int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            public Object getRepeatedRaw(Builder builder, int index) {
                throw new UnsupportedOperationException("getRepeatedFieldRaw() called on a singular field.");
            }

            public void setRepeated(Builder builder, int index, Object value) {
                throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
            }

            public void addRepeated(Builder builder, Object value) {
                throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
            }

            public boolean has(GeneratedMessage message) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethod, message, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    if (getOneofFieldNumber(message) == this.field.getNumber()) {
                        return true;
                    }
                    return false;
                } else if (get(message).equals(this.field.getDefaultValue())) {
                    return false;
                } else {
                    return true;
                }
            }

            public boolean has(Builder builder) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethodBuilder, builder, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    if (getOneofFieldNumber(builder) == this.field.getNumber()) {
                        return true;
                    }
                    return false;
                } else if (get(builder).equals(this.field.getDefaultValue())) {
                    return false;
                } else {
                    return true;
                }
            }

            public int getRepeatedCount(GeneratedMessage message) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            public int getRepeatedCount(Builder builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }

            public com.google.protobuf.Message.Builder getRepeatedBuilder(Builder builder, int index) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
        }

        private static final class SingularMessageFieldAccessor extends SingularFieldAccessor {
            private final Method getBuilderMethodBuilder;
            private final Method newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);

            SingularMessageFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", new Class[0]);
            }

            private Object coerceType(Object value) {
                if (this.type.isInstance(value)) {
                    return value;
                }
                return ((com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) value).buildPartial();
            }

            public void set(Builder builder, Object value) {
                super.set(builder, coerceType(value));
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
            }
        }

        private static final class SingularStringFieldAccessor extends SingularFieldAccessor {
            private final Method getBytesMethod;
            private final Method getBytesMethodBuilder;
            private final Method setBytesMethodBuilder;

            SingularStringFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.getBytesMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Bytes", new Class[0]);
                this.getBytesMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Bytes", new Class[0]);
                this.setBytesMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName + "Bytes", ByteString.class);
            }

            public Object getRaw(GeneratedMessage message) {
                return GeneratedMessage.invokeOrDie(this.getBytesMethod, message, new Object[0]);
            }

            public Object getRaw(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getBytesMethodBuilder, builder, new Object[0]);
            }

            public void set(Builder builder, Object value) {
                if (value instanceof ByteString) {
                    GeneratedMessage.invokeOrDie(this.setBytesMethodBuilder, builder, value);
                    return;
                }
                super.set(builder, value);
            }
        }

        public FieldAccessorTable(Descriptor descriptor2, String[] camelCaseNames2, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
            this(descriptor2, camelCaseNames2);
            ensureFieldAccessorsInitialized(messageClass, builderClass);
        }

        public FieldAccessorTable(Descriptor descriptor2, String[] camelCaseNames2) {
            this.descriptor = descriptor2;
            this.camelCaseNames = camelCaseNames2;
            this.fields = new FieldAccessor[descriptor2.getFields().size()];
            this.oneofs = new OneofAccessor[descriptor2.getOneofs().size()];
            this.initialized = false;
        }

        private boolean isMapFieldEnabled(FieldDescriptor field) {
            return true;
        }

        public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        int fieldsSize = this.fields.length;
                        for (int i = 0; i < fieldsSize; i++) {
                            FieldDescriptor field = (FieldDescriptor) this.descriptor.getFields().get(i);
                            String containingOneofCamelCaseName = null;
                            if (field.getContainingOneof() != null) {
                                containingOneofCamelCaseName = this.camelCaseNames[field.getContainingOneof().getIndex() + fieldsSize];
                            }
                            if (field.isRepeated()) {
                                if (field.getJavaType() == JavaType.MESSAGE) {
                                    if (!field.isMapField() || !isMapFieldEnabled(field)) {
                                        this.fields[i] = new RepeatedMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                    } else {
                                        this.fields[i] = new MapFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                    }
                                } else if (field.getJavaType() == JavaType.ENUM) {
                                    this.fields[i] = new RepeatedEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                } else {
                                    this.fields[i] = new RepeatedFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                }
                            } else if (field.getJavaType() == JavaType.MESSAGE) {
                                this.fields[i] = new SingularMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            } else if (field.getJavaType() == JavaType.ENUM) {
                                this.fields[i] = new SingularEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            } else if (field.getJavaType() == JavaType.STRING) {
                                this.fields[i] = new SingularStringFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            } else {
                                this.fields[i] = new SingularFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            }
                        }
                        int oneofsSize = this.oneofs.length;
                        for (int i2 = 0; i2 < oneofsSize; i2++) {
                            this.oneofs[i2] = new OneofAccessor(this.descriptor, this.camelCaseNames[i2 + fieldsSize], messageClass, builderClass);
                        }
                        this.initialized = true;
                        this.camelCaseNames = null;
                    }
                }
            }
            return this;
        }

        /* access modifiers changed from: private */
        public FieldAccessor getField(FieldDescriptor field) {
            if (field.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            } else if (!field.isExtension()) {
                return this.fields[field.getIndex()];
            } else {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
        }

        /* access modifiers changed from: private */
        public OneofAccessor getOneof(OneofDescriptor oneof) {
            if (oneof.getContainingType() == this.descriptor) {
                return this.oneofs[oneof.getIndex()];
            }
            throw new IllegalArgumentException("OneofDescriptor does not match message type.");
        }

        /* access modifiers changed from: private */
        public static boolean supportFieldPresence(FileDescriptor file) {
            return file.getSyntax() == Syntax.PROTO2;
        }
    }

    public static class GeneratedExtension<ContainingType extends Message, Type> extends Extension<ContainingType, Type> {
        private ExtensionDescriptorRetriever descriptorRetriever;
        private final Method enumGetValueDescriptor;
        private final Method enumValueOf;
        private final ExtensionType extensionType;
        private final Message messageDefaultInstance;
        private final Class singularType;

        GeneratedExtension(ExtensionDescriptorRetriever descriptorRetriever2, Class singularType2, Message messageDefaultInstance2, ExtensionType extensionType2) {
            if (!Message.class.isAssignableFrom(singularType2) || singularType2.isInstance(messageDefaultInstance2)) {
                this.descriptorRetriever = descriptorRetriever2;
                this.singularType = singularType2;
                this.messageDefaultInstance = messageDefaultInstance2;
                if (ProtocolMessageEnum.class.isAssignableFrom(singularType2)) {
                    this.enumValueOf = GeneratedMessage.getMethodOrDie(singularType2, "valueOf", EnumValueDescriptor.class);
                    this.enumGetValueDescriptor = GeneratedMessage.getMethodOrDie(singularType2, "getValueDescriptor", new Class[0]);
                } else {
                    this.enumValueOf = null;
                    this.enumGetValueDescriptor = null;
                }
                this.extensionType = extensionType2;
                return;
            }
            throw new IllegalArgumentException("Bad messageDefaultInstance for " + singularType2.getName());
        }

        public void internalInit(final FieldDescriptor descriptor) {
            if (this.descriptorRetriever != null) {
                throw new IllegalStateException("Already initialized.");
            }
            this.descriptorRetriever = new ExtensionDescriptorRetriever() {
                public FieldDescriptor getDescriptor() {
                    return descriptor;
                }
            };
        }

        public FieldDescriptor getDescriptor() {
            if (this.descriptorRetriever != null) {
                return this.descriptorRetriever.getDescriptor();
            }
            throw new IllegalStateException("getDescriptor() called before internalInit()");
        }

        public Message getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        /* access modifiers changed from: protected */
        public ExtensionType getExtensionType() {
            return this.extensionType;
        }

        /* access modifiers changed from: protected */
        public Object fromReflectionType(Object value) {
            FieldDescriptor descriptor = getDescriptor();
            if (!descriptor.isRepeated()) {
                return singularFromReflectionType(value);
            }
            if (descriptor.getJavaType() != JavaType.MESSAGE && descriptor.getJavaType() != JavaType.ENUM) {
                return value;
            }
            ArrayList arrayList = new ArrayList();
            for (Object element : (List) value) {
                arrayList.add(singularFromReflectionType(element));
            }
            return arrayList;
        }

        /* access modifiers changed from: protected */
        public Object singularFromReflectionType(Object value) {
            FieldDescriptor descriptor = getDescriptor();
            switch (descriptor.getJavaType()) {
                case MESSAGE:
                    if (!this.singularType.isInstance(value)) {
                        return this.messageDefaultInstance.newBuilderForType().mergeFrom((Message) value).build();
                    }
                    return value;
                case ENUM:
                    return GeneratedMessage.invokeOrDie(this.enumValueOf, null, (EnumValueDescriptor) value);
                default:
                    return value;
            }
        }

        /* access modifiers changed from: protected */
        public Object toReflectionType(Object value) {
            FieldDescriptor descriptor = getDescriptor();
            if (!descriptor.isRepeated()) {
                return singularToReflectionType(value);
            }
            if (descriptor.getJavaType() != JavaType.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(singularToReflectionType(element));
            }
            return result;
        }

        /* access modifiers changed from: protected */
        public Object singularToReflectionType(Object value) {
            FieldDescriptor descriptor = getDescriptor();
            switch (descriptor.getJavaType()) {
                case ENUM:
                    return GeneratedMessage.invokeOrDie(this.enumGetValueDescriptor, value, new Object[0]);
                default:
                    return value;
            }
        }

        public int getNumber() {
            return getDescriptor().getNumber();
        }

        public FieldType getLiteType() {
            return getDescriptor().getLiteType();
        }

        public boolean isRepeated() {
            return getDescriptor().isRepeated();
        }

        public Type getDefaultValue() {
            if (isRepeated()) {
                return Collections.emptyList();
            }
            if (getDescriptor().getJavaType() == JavaType.MESSAGE) {
                return this.messageDefaultInstance;
            }
            return singularFromReflectionType(getDescriptor().getDefaultValue());
        }
    }

    /* access modifiers changed from: protected */
    public abstract FieldAccessorTable internalGetFieldAccessorTable();

    /* access modifiers changed from: protected */
    public abstract com.google.protobuf.Message.Builder newBuilderForType(BuilderParent builderParent);

    protected GeneratedMessage() {
        this.unknownFields = UnknownFieldSet.getDefaultInstance();
    }

    protected GeneratedMessage(Builder<?> builder) {
        this.unknownFields = builder.getUnknownFields();
    }

    public Parser<? extends GeneratedMessage> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    static void enableAlwaysUseFieldBuildersForTesting() {
        alwaysUseFieldBuilders = true;
    }

    public Descriptor getDescriptorForType() {
        return internalGetFieldAccessorTable().descriptor;
    }

    /* access modifiers changed from: private */
    public Map<FieldDescriptor, Object> getAllFieldsMutable(boolean getBytesForString) {
        TreeMap<FieldDescriptor, Object> result = new TreeMap<>();
        List<FieldDescriptor> fields = internalGetFieldAccessorTable().descriptor.getFields();
        int i = 0;
        while (i < fields.size()) {
            FieldDescriptor field = (FieldDescriptor) fields.get(i);
            OneofDescriptor oneofDescriptor = field.getContainingOneof();
            if (oneofDescriptor != null) {
                i += oneofDescriptor.getFieldCount() - 1;
                if (hasOneof(oneofDescriptor)) {
                    field = getOneofFieldDescriptor(oneofDescriptor);
                    if (getBytesForString || field.getJavaType() != JavaType.STRING) {
                        result.put(field, getField(field));
                    } else {
                        result.put(field, getFieldRaw(field));
                    }
                }
            } else if (field.isRepeated()) {
                List<?> value = (List) getField(field);
                if (!value.isEmpty()) {
                    result.put(field, value);
                }
            } else {
                if (!hasField(field)) {
                }
                if (getBytesForString) {
                }
                result.put(field, getField(field));
            }
            i++;
        }
        return result;
    }

    public boolean isInitialized() {
        for (FieldDescriptor field : getDescriptorForType().getFields()) {
            if (field.isRequired() && !hasField(field)) {
                return false;
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                if (field.isRepeated()) {
                    for (Message element : (List) getField(field)) {
                        if (!element.isInitialized()) {
                            return false;
                        }
                    }
                    continue;
                } else if (hasField(field) && !((Message) getField(field)).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map<FieldDescriptor, Object> getAllFields() {
        return Collections.unmodifiableMap(getAllFieldsMutable(false));
    }

    /* access modifiers changed from: 0000 */
    public Map<FieldDescriptor, Object> getAllFieldsRaw() {
        return Collections.unmodifiableMap(getAllFieldsMutable(true));
    }

    public boolean hasOneof(OneofDescriptor oneof) {
        return internalGetFieldAccessorTable().getOneof(oneof).has(this);
    }

    public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
        return internalGetFieldAccessorTable().getOneof(oneof).get(this);
    }

    public boolean hasField(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).has(this);
    }

    public Object getField(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).get(this);
    }

    /* access modifiers changed from: 0000 */
    public Object getFieldRaw(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).getRaw(this);
    }

    public int getRepeatedFieldCount(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
    }

    public Object getRepeatedField(FieldDescriptor field, int index) {
        return internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
    }

    public UnknownFieldSet getUnknownFields() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    /* access modifiers changed from: protected */
    public boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields2, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
        return unknownFields2.mergeFieldFrom(tag, input);
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream input) throws IOException {
        try {
            return (Message) parser.parseFrom(input);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream input, ExtensionRegistryLite extensions) throws IOException {
        try {
            return (Message) parser.parseFrom(input, extensions);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream input) throws IOException {
        try {
            return (Message) parser.parseFrom(input);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream input, ExtensionRegistryLite extensions) throws IOException {
        try {
            return (Message) parser.parseFrom(input, extensions);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream input) throws IOException {
        try {
            return (Message) parser.parseDelimitedFrom(input);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream input, ExtensionRegistryLite extensions) throws IOException {
        try {
            return (Message) parser.parseDelimitedFrom(input, extensions);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        MessageReflection.writeMessageTo(this, getAllFieldsRaw(), output, false);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        this.memoizedSize = MessageReflection.getSerializedSize(this, getAllFieldsRaw());
        return this.memoizedSize;
    }

    /* access modifiers changed from: protected */
    public void makeExtensionsImmutable() {
    }

    /* access modifiers changed from: protected */
    public com.google.protobuf.Message.Builder newBuilderForType(final BuilderParent parent) {
        return newBuilderForType((BuilderParent) new BuilderParent() {
            public void markDirty() {
                parent.markDirty();
            }
        });
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message scope, final int descriptorIndex, Class singularType, Message defaultInstance) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() {
            public FieldDescriptor loadDescriptor() {
                return (FieldDescriptor) scope.getDescriptorForType().getExtensions().get(descriptorIndex);
            }
        }, singularType, defaultInstance, ExtensionType.IMMUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(Class singularType, Message defaultInstance) {
        return new GeneratedExtension<>(null, singularType, defaultInstance, ExtensionType.IMMUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message scope, final String name, Class singularType, Message defaultInstance) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() {
            /* access modifiers changed from: protected */
            public FieldDescriptor loadDescriptor() {
                return scope.getDescriptorForType().findFieldByName(name);
            }
        }, singularType, defaultInstance, ExtensionType.MUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(final Class singularType, Message defaultInstance, final String descriptorOuterClass, final String extensionName) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() {
            /* access modifiers changed from: protected */
            public FieldDescriptor loadDescriptor() {
                try {
                    return ((FileDescriptor) singularType.getClassLoader().loadClass(descriptorOuterClass).getField("descriptor").get(null)).findExtensionByName(extensionName);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot load descriptors: " + descriptorOuterClass + " is not a valid descriptor class name", e);
                }
            }
        }, singularType, defaultInstance, ExtensionType.MUTABLE);
    }

    /* access modifiers changed from: private */
    public static Method getMethodOrDie(Class clazz, String name, Class... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e);
        }
    }

    /* access modifiers changed from: private */
    public static Object invokeOrDie(Method method, Object object, Object... params) {
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

    /* access modifiers changed from: protected */
    public MapField internalGetMapField(int fieldNumber) {
        throw new RuntimeException("No map fields found in " + getClass().getName());
    }

    /* access modifiers changed from: protected */
    public Object writeReplace() throws ObjectStreamException {
        return new SerializedForm(this);
    }

    /* access modifiers changed from: private */
    public static <MessageType extends ExtendableMessage<MessageType>, T> Extension<MessageType, T> checkNotLite(ExtensionLite<MessageType, T> extension) {
        if (!extension.isLite()) {
            return (Extension) extension;
        }
        throw new IllegalArgumentException("Expected non-lite extension.");
    }

    protected static int computeStringSize(int fieldNumber, Object value) {
        if (value instanceof String) {
            return CodedOutputStream.computeStringSize(fieldNumber, (String) value);
        }
        return CodedOutputStream.computeBytesSize(fieldNumber, (ByteString) value);
    }

    protected static int computeStringSizeNoTag(Object value) {
        if (value instanceof String) {
            return CodedOutputStream.computeStringSizeNoTag((String) value);
        }
        return CodedOutputStream.computeBytesSizeNoTag((ByteString) value);
    }

    protected static void writeString(CodedOutputStream output, int fieldNumber, Object value) throws IOException {
        if (value instanceof String) {
            output.writeString(fieldNumber, (String) value);
        } else {
            output.writeBytes(fieldNumber, (ByteString) value);
        }
    }

    protected static void writeStringNoTag(CodedOutputStream output, Object value) throws IOException {
        if (value instanceof String) {
            output.writeStringNoTag((String) value);
        } else {
            output.writeBytesNoTag((ByteString) value);
        }
    }
}
