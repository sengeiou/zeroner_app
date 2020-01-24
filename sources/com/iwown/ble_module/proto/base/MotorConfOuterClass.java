package com.iwown.ble_module.proto.base;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.Internal;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MotorConfOuterClass {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MotorConfNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MotorConfNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_MotorConfNotification_descriptor, new String[]{"Operation", "Conf", "Vibrate", "Data"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MotorConfSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MotorConfSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_MotorConfSubscriber_descriptor, new String[]{"Hash", "Params", "Confirm", "Data"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MotorConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MotorConf_fieldAccessorTable = new FieldAccessorTable(internal_static_MotorConf_descriptor, new String[]{"Hash", "Conf"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MotorConfirm_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MotorConfirm_fieldAccessorTable = new FieldAccessorTable(internal_static_MotorConfirm_descriptor, new String[]{"Operation", "Ret"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MotorParams_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MotorParams_fieldAccessorTable = new FieldAccessorTable(internal_static_MotorParams_descriptor, new String[]{"ModeNum", "TypeNum"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MotorVibrate_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MotorVibrate_fieldAccessorTable = new FieldAccessorTable(internal_static_MotorVibrate_descriptor, new String[]{"Mode", "Round"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_VibrateCnf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_VibrateCnf_fieldAccessorTable = new FieldAccessorTable(internal_static_VibrateCnf_descriptor, new String[]{"Type", "Mode", "Round"});

    public static final class MotorConf extends GeneratedMessageV3 implements MotorConfOrBuilder {
        public static final int CONF_FIELD_NUMBER = 2;
        private static final MotorConf DEFAULT_INSTANCE = new MotorConf();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<MotorConf> PARSER = new AbstractParser<MotorConf>() {
            public MotorConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MotorConf(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public List<VibrateCnf> conf_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MotorConfOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilderV3<VibrateCnf, Builder, VibrateCnfOrBuilder> confBuilder_;
            private List<VibrateCnf> conf_;
            private int hash_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_MotorConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_MotorConf_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConf.class, Builder.class);
            }

            private Builder() {
                this.conf_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.conf_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MotorConf.alwaysUseFieldBuilders) {
                    getConfFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.confBuilder_ == null) {
                    this.conf_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.confBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_MotorConf_descriptor;
            }

            public MotorConf getDefaultInstanceForType() {
                return MotorConf.getDefaultInstance();
            }

            public MotorConf build() {
                MotorConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MotorConf buildPartial() {
                MotorConf result = new MotorConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.confBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.conf_ = Collections.unmodifiableList(this.conf_);
                        this.bitField0_ &= -3;
                    }
                    result.conf_ = this.conf_;
                } else {
                    result.conf_ = this.confBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof MotorConf) {
                    return mergeFrom((MotorConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MotorConf other) {
                RepeatedFieldBuilderV3<VibrateCnf, Builder, VibrateCnfOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != MotorConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (this.confBuilder_ == null) {
                        if (!other.conf_.isEmpty()) {
                            if (this.conf_.isEmpty()) {
                                this.conf_ = other.conf_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureConfIsMutable();
                                this.conf_.addAll(other.conf_);
                            }
                            onChanged();
                        }
                    } else if (!other.conf_.isEmpty()) {
                        if (this.confBuilder_.isEmpty()) {
                            this.confBuilder_.dispose();
                            this.confBuilder_ = null;
                            this.conf_ = other.conf_;
                            this.bitField0_ &= -3;
                            if (MotorConf.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getConfFieldBuilder();
                            }
                            this.confBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.confBuilder_.addAllMessages(other.conf_);
                        }
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasHash()) {
                    return false;
                }
                for (int i = 0; i < getConfCount(); i++) {
                    if (!getConf(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MotorConf parsedMessage = null;
                try {
                    MotorConf parsedMessage2 = (MotorConf) MotorConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MotorConf parsedMessage3 = (MotorConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            private void ensureConfIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.conf_ = new ArrayList(this.conf_);
                    this.bitField0_ |= 2;
                }
            }

            public List<VibrateCnf> getConfList() {
                if (this.confBuilder_ == null) {
                    return Collections.unmodifiableList(this.conf_);
                }
                return this.confBuilder_.getMessageList();
            }

            public int getConfCount() {
                if (this.confBuilder_ == null) {
                    return this.conf_.size();
                }
                return this.confBuilder_.getCount();
            }

            public VibrateCnf getConf(int index) {
                if (this.confBuilder_ == null) {
                    return (VibrateCnf) this.conf_.get(index);
                }
                return (VibrateCnf) this.confBuilder_.getMessage(index);
            }

            public Builder setConf(int index, VibrateCnf value) {
                if (this.confBuilder_ != null) {
                    this.confBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureConfIsMutable();
                    this.conf_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setConf(int index, Builder builderForValue) {
                if (this.confBuilder_ == null) {
                    ensureConfIsMutable();
                    this.conf_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.confBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addConf(VibrateCnf value) {
                if (this.confBuilder_ != null) {
                    this.confBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureConfIsMutable();
                    this.conf_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addConf(int index, VibrateCnf value) {
                if (this.confBuilder_ != null) {
                    this.confBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureConfIsMutable();
                    this.conf_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addConf(Builder builderForValue) {
                if (this.confBuilder_ == null) {
                    ensureConfIsMutable();
                    this.conf_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.confBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addConf(int index, Builder builderForValue) {
                if (this.confBuilder_ == null) {
                    ensureConfIsMutable();
                    this.conf_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.confBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllConf(Iterable<? extends VibrateCnf> values) {
                if (this.confBuilder_ == null) {
                    ensureConfIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.conf_);
                    onChanged();
                } else {
                    this.confBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearConf() {
                if (this.confBuilder_ == null) {
                    this.conf_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.confBuilder_.clear();
                }
                return this;
            }

            public Builder removeConf(int index) {
                if (this.confBuilder_ == null) {
                    ensureConfIsMutable();
                    this.conf_.remove(index);
                    onChanged();
                } else {
                    this.confBuilder_.remove(index);
                }
                return this;
            }

            public Builder getConfBuilder(int index) {
                return (Builder) getConfFieldBuilder().getBuilder(index);
            }

            public VibrateCnfOrBuilder getConfOrBuilder(int index) {
                if (this.confBuilder_ == null) {
                    return (VibrateCnfOrBuilder) this.conf_.get(index);
                }
                return (VibrateCnfOrBuilder) this.confBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends VibrateCnfOrBuilder> getConfOrBuilderList() {
                if (this.confBuilder_ != null) {
                    return this.confBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.conf_);
            }

            public Builder addConfBuilder() {
                return (Builder) getConfFieldBuilder().addBuilder(VibrateCnf.getDefaultInstance());
            }

            public Builder addConfBuilder(int index) {
                return (Builder) getConfFieldBuilder().addBuilder(index, VibrateCnf.getDefaultInstance());
            }

            public List<Builder> getConfBuilderList() {
                return getConfFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<VibrateCnf, Builder, VibrateCnfOrBuilder> getConfFieldBuilder() {
                if (this.confBuilder_ == null) {
                    this.confBuilder_ = new RepeatedFieldBuilderV3<>(this.conf_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.conf_ = null;
                }
                return this.confBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MotorConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MotorConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.conf_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MotorConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 13:
                            this.bitField0_ |= 1;
                            this.hash_ = input.readFixed32();
                            break;
                        case 18:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.conf_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.conf_.add(input.readMessage(VibrateCnf.PARSER, extensionRegistry));
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.conf_ = Collections.unmodifiableList(this.conf_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.conf_ = Collections.unmodifiableList(this.conf_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_MotorConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_MotorConf_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<VibrateCnf> getConfList() {
            return this.conf_;
        }

        public List<? extends VibrateCnfOrBuilder> getConfOrBuilderList() {
            return this.conf_;
        }

        public int getConfCount() {
            return this.conf_.size();
        }

        public VibrateCnf getConf(int index) {
            return (VibrateCnf) this.conf_.get(index);
        }

        public VibrateCnfOrBuilder getConfOrBuilder(int index) {
            return (VibrateCnfOrBuilder) this.conf_.get(index);
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < getConfCount(); i++) {
                if (!getConf(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            for (int i = 0; i < this.conf_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.conf_.get(i));
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            for (int i = 0; i < this.conf_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.conf_.get(i));
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MotorConf)) {
                return super.equals(obj);
            }
            MotorConf other = (MotorConf) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getConfList().equals(other.getConfList())) {
                result = false;
            } else {
                result = true;
            }
            if (!result || !this.unknownFields.equals(other.unknownFields)) {
                result2 = false;
            } else {
                result2 = true;
            }
            return result2;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (getConfCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getConfList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MotorConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MotorConf) PARSER.parseFrom(data);
        }

        public static MotorConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MotorConf) PARSER.parseFrom(data);
        }

        public static MotorConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConf parseFrom(InputStream input) throws IOException {
            return (MotorConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConf parseDelimitedFrom(InputStream input) throws IOException {
            return (MotorConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MotorConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConf parseFrom(CodedInputStream input) throws IOException {
            return (MotorConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MotorConf prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static MotorConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MotorConf> parser() {
            return PARSER;
        }

        public Parser<MotorConf> getParserForType() {
            return PARSER;
        }

        public MotorConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MotorConfNotification extends GeneratedMessageV3 implements MotorConfNotificationOrBuilder {
        public static final int CONF_FIELD_NUMBER = 2;
        private static final MotorConfNotification DEFAULT_INSTANCE = new MotorConfNotification();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<MotorConfNotification> PARSER = new AbstractParser<MotorConfNotification>() {
            public MotorConfNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MotorConfNotification(input, extensionRegistry);
            }
        };
        public static final int VIBRATE_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;

        public enum DataCase implements EnumLite {
            CONF(2),
            VIBRATE(3),
            DATA_NOT_SET(0);
            
            private final int value;

            private DataCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static DataCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static DataCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DATA_NOT_SET;
                    case 2:
                        return CONF;
                    case 3:
                        return VIBRATE;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MotorConfNotificationOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<MotorConf, Builder, MotorConfOrBuilder> confBuilder_;
            private int dataCase_;
            private Object data_;
            private int operation_;
            private SingleFieldBuilderV3<MotorVibrate, Builder, MotorVibrateOrBuilder> vibrateBuilder_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_MotorConfNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_MotorConfNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConfNotification.class, Builder.class);
            }

            private Builder() {
                this.dataCase_ = 0;
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dataCase_ = 0;
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MotorConfNotification.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.operation_ = 0;
                this.bitField0_ &= -2;
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_MotorConfNotification_descriptor;
            }

            public MotorConfNotification getDefaultInstanceForType() {
                return MotorConfNotification.getDefaultInstance();
            }

            public MotorConfNotification build() {
                MotorConfNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MotorConfNotification buildPartial() {
                MotorConfNotification result = new MotorConfNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.operation_ = this.operation_;
                if (this.dataCase_ == 2) {
                    if (this.confBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.confBuilder_.build();
                    }
                }
                if (this.dataCase_ == 3) {
                    if (this.vibrateBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.vibrateBuilder_.build();
                    }
                }
                result.bitField0_ = to_bitField0_;
                result.dataCase_ = this.dataCase_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof MotorConfNotification) {
                    return mergeFrom((MotorConfNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MotorConfNotification other) {
                if (other != MotorConfNotification.getDefaultInstance()) {
                    if (other.hasOperation()) {
                        setOperation(other.getOperation());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfNotification$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeConf(other.getConf());
                            break;
                        case 2:
                            mergeVibrate(other.getVibrate());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasOperation()) {
                    return false;
                }
                if (hasConf() && !getConf().isInitialized()) {
                    return false;
                }
                if (!hasVibrate() || getVibrate().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MotorConfNotification parsedMessage = null;
                try {
                    MotorConfNotification parsedMessage2 = (MotorConfNotification) MotorConfNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MotorConfNotification parsedMessage3 = (MotorConfNotification) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public DataCase getDataCase() {
                return DataCase.forNumber(this.dataCase_);
            }

            public Builder clearData() {
                this.dataCase_ = 0;
                this.data_ = null;
                onChanged();
                return this;
            }

            public boolean hasOperation() {
                return (this.bitField0_ & 1) == 1;
            }

            public MotorOperation getOperation() {
                MotorOperation result = MotorOperation.valueOf(this.operation_);
                return result == null ? MotorOperation.CONFIG : result;
            }

            public Builder setOperation(MotorOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.operation_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearOperation() {
                this.bitField0_ &= -2;
                this.operation_ = 0;
                onChanged();
                return this;
            }

            public boolean hasConf() {
                return this.dataCase_ == 2;
            }

            public MotorConf getConf() {
                if (this.confBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (MotorConf) this.data_;
                    }
                    return MotorConf.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (MotorConf) this.confBuilder_.getMessage();
                } else {
                    return MotorConf.getDefaultInstance();
                }
            }

            public Builder setConf(MotorConf value) {
                if (this.confBuilder_ != null) {
                    this.confBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setConf(Builder builderForValue) {
                if (this.confBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.confBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeConf(MotorConf value) {
                if (this.confBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == MotorConf.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = MotorConf.newBuilder((MotorConf) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.confBuilder_.mergeFrom(value);
                    }
                    this.confBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearConf() {
                if (this.confBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.confBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getConfBuilder() {
                return (Builder) getConfFieldBuilder().getBuilder();
            }

            public MotorConfOrBuilder getConfOrBuilder() {
                if (this.dataCase_ == 2 && this.confBuilder_ != null) {
                    return (MotorConfOrBuilder) this.confBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (MotorConf) this.data_;
                }
                return MotorConf.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MotorConf, Builder, MotorConfOrBuilder> getConfFieldBuilder() {
                if (this.confBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = MotorConf.getDefaultInstance();
                    }
                    this.confBuilder_ = new SingleFieldBuilderV3<>((MotorConf) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.confBuilder_;
            }

            public boolean hasVibrate() {
                return this.dataCase_ == 3;
            }

            public MotorVibrate getVibrate() {
                if (this.vibrateBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (MotorVibrate) this.data_;
                    }
                    return MotorVibrate.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (MotorVibrate) this.vibrateBuilder_.getMessage();
                } else {
                    return MotorVibrate.getDefaultInstance();
                }
            }

            public Builder setVibrate(MotorVibrate value) {
                if (this.vibrateBuilder_ != null) {
                    this.vibrateBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder setVibrate(Builder builderForValue) {
                if (this.vibrateBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.vibrateBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder mergeVibrate(MotorVibrate value) {
                if (this.vibrateBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == MotorVibrate.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = MotorVibrate.newBuilder((MotorVibrate) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 3) {
                        this.vibrateBuilder_.mergeFrom(value);
                    }
                    this.vibrateBuilder_.setMessage(value);
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder clearVibrate() {
                if (this.vibrateBuilder_ != null) {
                    if (this.dataCase_ == 3) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.vibrateBuilder_.clear();
                } else if (this.dataCase_ == 3) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getVibrateBuilder() {
                return (Builder) getVibrateFieldBuilder().getBuilder();
            }

            public MotorVibrateOrBuilder getVibrateOrBuilder() {
                if (this.dataCase_ == 3 && this.vibrateBuilder_ != null) {
                    return (MotorVibrateOrBuilder) this.vibrateBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (MotorVibrate) this.data_;
                }
                return MotorVibrate.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MotorVibrate, Builder, MotorVibrateOrBuilder> getVibrateFieldBuilder() {
                if (this.vibrateBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = MotorVibrate.getDefaultInstance();
                    }
                    this.vibrateBuilder_ = new SingleFieldBuilderV3<>((MotorVibrate) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 3;
                onChanged();
                return this.vibrateBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MotorConfNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private MotorConfNotification() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MotorConfNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            int rawValue = input.readEnum();
                            if (MotorOperation.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.operation_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            Builder subBuilder = null;
                            if (this.dataCase_ == 2) {
                                subBuilder = ((MotorConf) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(MotorConf.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((MotorConf) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 3) {
                                subBuilder2 = ((MotorVibrate) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(MotorVibrate.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((MotorVibrate) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 3;
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_MotorConfNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_MotorConfNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConfNotification.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public MotorOperation getOperation() {
            MotorOperation result = MotorOperation.valueOf(this.operation_);
            return result == null ? MotorOperation.CONFIG : result;
        }

        public boolean hasConf() {
            return this.dataCase_ == 2;
        }

        public MotorConf getConf() {
            if (this.dataCase_ == 2) {
                return (MotorConf) this.data_;
            }
            return MotorConf.getDefaultInstance();
        }

        public MotorConfOrBuilder getConfOrBuilder() {
            if (this.dataCase_ == 2) {
                return (MotorConf) this.data_;
            }
            return MotorConf.getDefaultInstance();
        }

        public boolean hasVibrate() {
            return this.dataCase_ == 3;
        }

        public MotorVibrate getVibrate() {
            if (this.dataCase_ == 3) {
                return (MotorVibrate) this.data_;
            }
            return MotorVibrate.getDefaultInstance();
        }

        public MotorVibrateOrBuilder getVibrateOrBuilder() {
            if (this.dataCase_ == 3) {
                return (MotorVibrate) this.data_;
            }
            return MotorVibrate.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasOperation()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasConf() && !getConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasVibrate() || getVibrate().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.operation_);
            }
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (MotorConf) this.data_);
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (MotorVibrate) this.data_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.operation_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (MotorConf) this.data_);
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (MotorVibrate) this.data_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MotorConfNotification)) {
                return super.equals(obj);
            }
            MotorConfNotification other = (MotorConfNotification) obj;
            boolean result3 = 1 != 0 && hasOperation() == other.hasOperation();
            if (hasOperation()) {
                result3 = result3 && this.operation_ == other.operation_;
            }
            if (!result3 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 2:
                    if (result && getConf().equals(other.getConf())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getVibrate().equals(other.getVibrate())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
            }
            if (!result || !this.unknownFields.equals(other.unknownFields)) {
                result2 = false;
            } else {
                result2 = true;
            }
            return result2;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasOperation()) {
                hash = (((hash * 37) + 1) * 53) + this.operation_;
            }
            switch (this.dataCase_) {
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getConf().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getVibrate().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MotorConfNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MotorConfNotification) PARSER.parseFrom(data);
        }

        public static MotorConfNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConfNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConfNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MotorConfNotification) PARSER.parseFrom(data);
        }

        public static MotorConfNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConfNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConfNotification parseFrom(InputStream input) throws IOException {
            return (MotorConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConfNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConfNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (MotorConfNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MotorConfNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConfNotification parseFrom(CodedInputStream input) throws IOException {
            return (MotorConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConfNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MotorConfNotification prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static MotorConfNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MotorConfNotification> parser() {
            return PARSER;
        }

        public Parser<MotorConfNotification> getParserForType() {
            return PARSER;
        }

        public MotorConfNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MotorConfSubscriber extends GeneratedMessageV3 implements MotorConfSubscriberOrBuilder {
        public static final int CONFIRM_FIELD_NUMBER = 3;
        private static final MotorConfSubscriber DEFAULT_INSTANCE = new MotorConfSubscriber();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int PARAMS_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<MotorConfSubscriber> PARSER = new AbstractParser<MotorConfSubscriber>() {
            public MotorConfSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MotorConfSubscriber(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public enum DataCase implements EnumLite {
            PARAMS(2),
            CONFIRM(3),
            DATA_NOT_SET(0);
            
            private final int value;

            private DataCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static DataCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static DataCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DATA_NOT_SET;
                    case 2:
                        return PARAMS;
                    case 3:
                        return CONFIRM;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MotorConfSubscriberOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<MotorConfirm, Builder, MotorConfirmOrBuilder> confirmBuilder_;
            private int dataCase_;
            private Object data_;
            private int hash_;
            private SingleFieldBuilderV3<MotorParams, Builder, MotorParamsOrBuilder> paramsBuilder_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_MotorConfSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_MotorConfSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConfSubscriber.class, Builder.class);
            }

            private Builder() {
                this.dataCase_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dataCase_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MotorConfSubscriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_MotorConfSubscriber_descriptor;
            }

            public MotorConfSubscriber getDefaultInstanceForType() {
                return MotorConfSubscriber.getDefaultInstance();
            }

            public MotorConfSubscriber build() {
                MotorConfSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MotorConfSubscriber buildPartial() {
                MotorConfSubscriber result = new MotorConfSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.dataCase_ == 2) {
                    if (this.paramsBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.paramsBuilder_.build();
                    }
                }
                if (this.dataCase_ == 3) {
                    if (this.confirmBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.confirmBuilder_.build();
                    }
                }
                result.bitField0_ = to_bitField0_;
                result.dataCase_ = this.dataCase_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof MotorConfSubscriber) {
                    return mergeFrom((MotorConfSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MotorConfSubscriber other) {
                if (other != MotorConfSubscriber.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfSubscriber$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeParams(other.getParams());
                            break;
                        case 2:
                            mergeConfirm(other.getConfirm());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasHash()) {
                    return false;
                }
                if (hasParams() && !getParams().isInitialized()) {
                    return false;
                }
                if (!hasConfirm() || getConfirm().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MotorConfSubscriber parsedMessage = null;
                try {
                    MotorConfSubscriber parsedMessage2 = (MotorConfSubscriber) MotorConfSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MotorConfSubscriber parsedMessage3 = (MotorConfSubscriber) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public DataCase getDataCase() {
                return DataCase.forNumber(this.dataCase_);
            }

            public Builder clearData() {
                this.dataCase_ = 0;
                this.data_ = null;
                onChanged();
                return this;
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasParams() {
                return this.dataCase_ == 2;
            }

            public MotorParams getParams() {
                if (this.paramsBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (MotorParams) this.data_;
                    }
                    return MotorParams.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (MotorParams) this.paramsBuilder_.getMessage();
                } else {
                    return MotorParams.getDefaultInstance();
                }
            }

            public Builder setParams(MotorParams value) {
                if (this.paramsBuilder_ != null) {
                    this.paramsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setParams(Builder builderForValue) {
                if (this.paramsBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.paramsBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeParams(MotorParams value) {
                if (this.paramsBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == MotorParams.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = MotorParams.newBuilder((MotorParams) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.paramsBuilder_.mergeFrom(value);
                    }
                    this.paramsBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearParams() {
                if (this.paramsBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.paramsBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getParamsBuilder() {
                return (Builder) getParamsFieldBuilder().getBuilder();
            }

            public MotorParamsOrBuilder getParamsOrBuilder() {
                if (this.dataCase_ == 2 && this.paramsBuilder_ != null) {
                    return (MotorParamsOrBuilder) this.paramsBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (MotorParams) this.data_;
                }
                return MotorParams.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MotorParams, Builder, MotorParamsOrBuilder> getParamsFieldBuilder() {
                if (this.paramsBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = MotorParams.getDefaultInstance();
                    }
                    this.paramsBuilder_ = new SingleFieldBuilderV3<>((MotorParams) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.paramsBuilder_;
            }

            public boolean hasConfirm() {
                return this.dataCase_ == 3;
            }

            public MotorConfirm getConfirm() {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (MotorConfirm) this.data_;
                    }
                    return MotorConfirm.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (MotorConfirm) this.confirmBuilder_.getMessage();
                } else {
                    return MotorConfirm.getDefaultInstance();
                }
            }

            public Builder setConfirm(MotorConfirm value) {
                if (this.confirmBuilder_ != null) {
                    this.confirmBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder setConfirm(Builder builderForValue) {
                if (this.confirmBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.confirmBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder mergeConfirm(MotorConfirm value) {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == MotorConfirm.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = MotorConfirm.newBuilder((MotorConfirm) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 3) {
                        this.confirmBuilder_.mergeFrom(value);
                    }
                    this.confirmBuilder_.setMessage(value);
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder clearConfirm() {
                if (this.confirmBuilder_ != null) {
                    if (this.dataCase_ == 3) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.confirmBuilder_.clear();
                } else if (this.dataCase_ == 3) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getConfirmBuilder() {
                return (Builder) getConfirmFieldBuilder().getBuilder();
            }

            public MotorConfirmOrBuilder getConfirmOrBuilder() {
                if (this.dataCase_ == 3 && this.confirmBuilder_ != null) {
                    return (MotorConfirmOrBuilder) this.confirmBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (MotorConfirm) this.data_;
                }
                return MotorConfirm.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MotorConfirm, Builder, MotorConfirmOrBuilder> getConfirmFieldBuilder() {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = MotorConfirm.getDefaultInstance();
                    }
                    this.confirmBuilder_ = new SingleFieldBuilderV3<>((MotorConfirm) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 3;
                onChanged();
                return this.confirmBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MotorConfSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private MotorConfSubscriber() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MotorConfSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 13:
                            this.bitField0_ |= 1;
                            this.hash_ = input.readFixed32();
                            break;
                        case 18:
                            Builder subBuilder = null;
                            if (this.dataCase_ == 2) {
                                subBuilder = ((MotorParams) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(MotorParams.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((MotorParams) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 3) {
                                subBuilder2 = ((MotorConfirm) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(MotorConfirm.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((MotorConfirm) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 3;
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_MotorConfSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_MotorConfSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConfSubscriber.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasParams() {
            return this.dataCase_ == 2;
        }

        public MotorParams getParams() {
            if (this.dataCase_ == 2) {
                return (MotorParams) this.data_;
            }
            return MotorParams.getDefaultInstance();
        }

        public MotorParamsOrBuilder getParamsOrBuilder() {
            if (this.dataCase_ == 2) {
                return (MotorParams) this.data_;
            }
            return MotorParams.getDefaultInstance();
        }

        public boolean hasConfirm() {
            return this.dataCase_ == 3;
        }

        public MotorConfirm getConfirm() {
            if (this.dataCase_ == 3) {
                return (MotorConfirm) this.data_;
            }
            return MotorConfirm.getDefaultInstance();
        }

        public MotorConfirmOrBuilder getConfirmOrBuilder() {
            if (this.dataCase_ == 3) {
                return (MotorConfirm) this.data_;
            }
            return MotorConfirm.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasParams() && !getParams().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasConfirm() || getConfirm().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (MotorParams) this.data_);
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (MotorConfirm) this.data_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (MotorParams) this.data_);
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (MotorConfirm) this.data_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MotorConfSubscriber)) {
                return super.equals(obj);
            }
            MotorConfSubscriber other = (MotorConfSubscriber) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 2:
                    if (result && getParams().equals(other.getParams())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getConfirm().equals(other.getConfirm())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
            }
            if (!result || !this.unknownFields.equals(other.unknownFields)) {
                result2 = false;
            } else {
                result2 = true;
            }
            return result2;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            switch (this.dataCase_) {
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getParams().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getConfirm().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MotorConfSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MotorConfSubscriber) PARSER.parseFrom(data);
        }

        public static MotorConfSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConfSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConfSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MotorConfSubscriber) PARSER.parseFrom(data);
        }

        public static MotorConfSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConfSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConfSubscriber parseFrom(InputStream input) throws IOException {
            return (MotorConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConfSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConfSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (MotorConfSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MotorConfSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConfSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (MotorConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConfSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MotorConfSubscriber prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static MotorConfSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MotorConfSubscriber> parser() {
            return PARSER;
        }

        public Parser<MotorConfSubscriber> getParserForType() {
            return PARSER;
        }

        public MotorConfSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MotorConfirm extends GeneratedMessageV3 implements MotorConfirmOrBuilder {
        private static final MotorConfirm DEFAULT_INSTANCE = new MotorConfirm();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<MotorConfirm> PARSER = new AbstractParser<MotorConfirm>() {
            public MotorConfirm parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MotorConfirm(input, extensionRegistry);
            }
        };
        public static final int RET_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;
        /* access modifiers changed from: private */
        public boolean ret_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MotorConfirmOrBuilder {
            private int bitField0_;
            private int operation_;
            private boolean ret_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_MotorConfirm_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_MotorConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConfirm.class, Builder.class);
            }

            private Builder() {
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MotorConfirm.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.operation_ = 0;
                this.bitField0_ &= -2;
                this.ret_ = false;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_MotorConfirm_descriptor;
            }

            public MotorConfirm getDefaultInstanceForType() {
                return MotorConfirm.getDefaultInstance();
            }

            public MotorConfirm build() {
                MotorConfirm result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MotorConfirm buildPartial() {
                MotorConfirm result = new MotorConfirm((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.operation_ = this.operation_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.ret_ = this.ret_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof MotorConfirm) {
                    return mergeFrom((MotorConfirm) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MotorConfirm other) {
                if (other != MotorConfirm.getDefaultInstance()) {
                    if (other.hasOperation()) {
                        setOperation(other.getOperation());
                    }
                    if (other.hasRet()) {
                        setRet(other.getRet());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasOperation() && hasRet()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MotorConfirm parsedMessage = null;
                try {
                    MotorConfirm parsedMessage2 = (MotorConfirm) MotorConfirm.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MotorConfirm parsedMessage3 = (MotorConfirm) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasOperation() {
                return (this.bitField0_ & 1) == 1;
            }

            public MotorOperation getOperation() {
                MotorOperation result = MotorOperation.valueOf(this.operation_);
                return result == null ? MotorOperation.CONFIG : result;
            }

            public Builder setOperation(MotorOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.operation_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearOperation() {
                this.bitField0_ &= -2;
                this.operation_ = 0;
                onChanged();
                return this;
            }

            public boolean hasRet() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getRet() {
                return this.ret_;
            }

            public Builder setRet(boolean value) {
                this.bitField0_ |= 2;
                this.ret_ = value;
                onChanged();
                return this;
            }

            public Builder clearRet() {
                this.bitField0_ &= -3;
                this.ret_ = false;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MotorConfirm(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MotorConfirm() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
            this.ret_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MotorConfirm(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            int rawValue = input.readEnum();
                            if (MotorOperation.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.operation_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            this.bitField0_ |= 2;
                            this.ret_ = input.readBool();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_MotorConfirm_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_MotorConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorConfirm.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public MotorOperation getOperation() {
            MotorOperation result = MotorOperation.valueOf(this.operation_);
            return result == null ? MotorOperation.CONFIG : result;
        }

        public boolean hasRet() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getRet() {
            return this.ret_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasOperation()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRet()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.operation_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.ret_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.operation_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.ret_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MotorConfirm)) {
                return super.equals(obj);
            }
            MotorConfirm other = (MotorConfirm) obj;
            boolean result3 = 1 != 0 && hasOperation() == other.hasOperation();
            if (hasOperation()) {
                result3 = result3 && this.operation_ == other.operation_;
            }
            if (!result3 || hasRet() != other.hasRet()) {
                result = false;
            } else {
                result = true;
            }
            if (hasRet()) {
                if (!result || getRet() != other.getRet()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || !this.unknownFields.equals(other.unknownFields)) {
                result2 = false;
            } else {
                result2 = true;
            }
            return result2;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasOperation()) {
                hash = (((hash * 37) + 1) * 53) + this.operation_;
            }
            if (hasRet()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getRet());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MotorConfirm parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MotorConfirm) PARSER.parseFrom(data);
        }

        public static MotorConfirm parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConfirm parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MotorConfirm) PARSER.parseFrom(data);
        }

        public static MotorConfirm parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorConfirm parseFrom(InputStream input) throws IOException {
            return (MotorConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConfirm parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConfirm parseDelimitedFrom(InputStream input) throws IOException {
            return (MotorConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MotorConfirm parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorConfirm parseFrom(CodedInputStream input) throws IOException {
            return (MotorConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorConfirm parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MotorConfirm prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static MotorConfirm getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MotorConfirm> parser() {
            return PARSER;
        }

        public Parser<MotorConfirm> getParserForType() {
            return PARSER;
        }

        public MotorConfirm getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum MotorOperation implements ProtocolMessageEnum {
        CONFIG(0),
        VIBRATE(1);
        
        public static final int CONFIG_VALUE = 0;
        private static final MotorOperation[] VALUES = null;
        public static final int VIBRATE_VALUE = 1;
        private static final EnumLiteMap<MotorOperation> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<MotorOperation>() {
                public MotorOperation findValueByNumber(int number) {
                    return MotorOperation.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static MotorOperation valueOf(int value2) {
            return forNumber(value2);
        }

        public static MotorOperation forNumber(int value2) {
            switch (value2) {
                case 0:
                    return CONFIG;
                case 1:
                    return VIBRATE;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<MotorOperation> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) MotorConfOuterClass.getDescriptor().getEnumTypes().get(1);
        }

        public static MotorOperation valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private MotorOperation(int value2) {
            this.value = value2;
        }
    }

    public static final class MotorParams extends GeneratedMessageV3 implements MotorParamsOrBuilder {
        private static final MotorParams DEFAULT_INSTANCE = new MotorParams();
        public static final int MODE_NUM_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<MotorParams> PARSER = new AbstractParser<MotorParams>() {
            public MotorParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MotorParams(input, extensionRegistry);
            }
        };
        public static final int TYPE_NUM_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int modeNum_;
        /* access modifiers changed from: private */
        public int typeNum_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MotorParamsOrBuilder {
            private int bitField0_;
            private int modeNum_;
            private int typeNum_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_MotorParams_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_MotorParams_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorParams.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MotorParams.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.modeNum_ = 0;
                this.bitField0_ &= -2;
                this.typeNum_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_MotorParams_descriptor;
            }

            public MotorParams getDefaultInstanceForType() {
                return MotorParams.getDefaultInstance();
            }

            public MotorParams build() {
                MotorParams result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MotorParams buildPartial() {
                MotorParams result = new MotorParams((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.modeNum_ = this.modeNum_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.typeNum_ = this.typeNum_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof MotorParams) {
                    return mergeFrom((MotorParams) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MotorParams other) {
                if (other != MotorParams.getDefaultInstance()) {
                    if (other.hasModeNum()) {
                        setModeNum(other.getModeNum());
                    }
                    if (other.hasTypeNum()) {
                        setTypeNum(other.getTypeNum());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasModeNum() && hasTypeNum()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MotorParams parsedMessage = null;
                try {
                    MotorParams parsedMessage2 = (MotorParams) MotorParams.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MotorParams parsedMessage3 = (MotorParams) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasModeNum() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getModeNum() {
                return this.modeNum_;
            }

            public Builder setModeNum(int value) {
                this.bitField0_ |= 1;
                this.modeNum_ = value;
                onChanged();
                return this;
            }

            public Builder clearModeNum() {
                this.bitField0_ &= -2;
                this.modeNum_ = 0;
                onChanged();
                return this;
            }

            public boolean hasTypeNum() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getTypeNum() {
                return this.typeNum_;
            }

            public Builder setTypeNum(int value) {
                this.bitField0_ |= 2;
                this.typeNum_ = value;
                onChanged();
                return this;
            }

            public Builder clearTypeNum() {
                this.bitField0_ &= -3;
                this.typeNum_ = 0;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MotorParams(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MotorParams() {
            this.memoizedIsInitialized = -1;
            this.modeNum_ = 0;
            this.typeNum_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MotorParams(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 13:
                            this.bitField0_ |= 1;
                            this.modeNum_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.typeNum_ = input.readFixed32();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_MotorParams_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_MotorParams_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorParams.class, Builder.class);
        }

        public boolean hasModeNum() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getModeNum() {
            return this.modeNum_;
        }

        public boolean hasTypeNum() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getTypeNum() {
            return this.typeNum_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasModeNum()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasTypeNum()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.modeNum_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.typeNum_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.modeNum_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.typeNum_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MotorParams)) {
                return super.equals(obj);
            }
            MotorParams other = (MotorParams) obj;
            boolean result3 = 1 != 0 && hasModeNum() == other.hasModeNum();
            if (hasModeNum()) {
                result3 = result3 && getModeNum() == other.getModeNum();
            }
            if (!result3 || hasTypeNum() != other.hasTypeNum()) {
                result = false;
            } else {
                result = true;
            }
            if (hasTypeNum()) {
                if (!result || getTypeNum() != other.getTypeNum()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || !this.unknownFields.equals(other.unknownFields)) {
                result2 = false;
            } else {
                result2 = true;
            }
            return result2;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasModeNum()) {
                hash = (((hash * 37) + 1) * 53) + getModeNum();
            }
            if (hasTypeNum()) {
                hash = (((hash * 37) + 2) * 53) + getTypeNum();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MotorParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MotorParams) PARSER.parseFrom(data);
        }

        public static MotorParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorParams) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MotorParams) PARSER.parseFrom(data);
        }

        public static MotorParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorParams) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorParams parseFrom(InputStream input) throws IOException {
            return (MotorParams) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorParams) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorParams parseDelimitedFrom(InputStream input) throws IOException {
            return (MotorParams) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MotorParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorParams) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorParams parseFrom(CodedInputStream input) throws IOException {
            return (MotorParams) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorParams) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MotorParams prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static MotorParams getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MotorParams> parser() {
            return PARSER;
        }

        public Parser<MotorParams> getParserForType() {
            return PARSER;
        }

        public MotorParams getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MotorVibrate extends GeneratedMessageV3 implements MotorVibrateOrBuilder {
        private static final MotorVibrate DEFAULT_INSTANCE = new MotorVibrate();
        public static final int MODE_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<MotorVibrate> PARSER = new AbstractParser<MotorVibrate>() {
            public MotorVibrate parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MotorVibrate(input, extensionRegistry);
            }
        };
        public static final int ROUND_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int mode_;
        /* access modifiers changed from: private */
        public int round_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MotorVibrateOrBuilder {
            private int bitField0_;
            private int mode_;
            private int round_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_MotorVibrate_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_MotorVibrate_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorVibrate.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MotorVibrate.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.mode_ = 0;
                this.bitField0_ &= -2;
                this.round_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_MotorVibrate_descriptor;
            }

            public MotorVibrate getDefaultInstanceForType() {
                return MotorVibrate.getDefaultInstance();
            }

            public MotorVibrate build() {
                MotorVibrate result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MotorVibrate buildPartial() {
                MotorVibrate result = new MotorVibrate((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.mode_ = this.mode_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.round_ = this.round_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof MotorVibrate) {
                    return mergeFrom((MotorVibrate) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MotorVibrate other) {
                if (other != MotorVibrate.getDefaultInstance()) {
                    if (other.hasMode()) {
                        setMode(other.getMode());
                    }
                    if (other.hasRound()) {
                        setRound(other.getRound());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasMode() && hasRound()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MotorVibrate parsedMessage = null;
                try {
                    MotorVibrate parsedMessage2 = (MotorVibrate) MotorVibrate.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MotorVibrate parsedMessage3 = (MotorVibrate) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasMode() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getMode() {
                return this.mode_;
            }

            public Builder setMode(int value) {
                this.bitField0_ |= 1;
                this.mode_ = value;
                onChanged();
                return this;
            }

            public Builder clearMode() {
                this.bitField0_ &= -2;
                this.mode_ = 0;
                onChanged();
                return this;
            }

            public boolean hasRound() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getRound() {
                return this.round_;
            }

            public Builder setRound(int value) {
                this.bitField0_ |= 2;
                this.round_ = value;
                onChanged();
                return this;
            }

            public Builder clearRound() {
                this.bitField0_ &= -3;
                this.round_ = 0;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MotorVibrate(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MotorVibrate() {
            this.memoizedIsInitialized = -1;
            this.mode_ = 0;
            this.round_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MotorVibrate(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 13:
                            this.bitField0_ |= 1;
                            this.mode_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.round_ = input.readFixed32();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_MotorVibrate_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_MotorVibrate_fieldAccessorTable.ensureFieldAccessorsInitialized(MotorVibrate.class, Builder.class);
        }

        public boolean hasMode() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMode() {
            return this.mode_;
        }

        public boolean hasRound() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getRound() {
            return this.round_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasMode()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRound()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.mode_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.round_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.mode_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.round_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MotorVibrate)) {
                return super.equals(obj);
            }
            MotorVibrate other = (MotorVibrate) obj;
            boolean result3 = 1 != 0 && hasMode() == other.hasMode();
            if (hasMode()) {
                result3 = result3 && getMode() == other.getMode();
            }
            if (!result3 || hasRound() != other.hasRound()) {
                result = false;
            } else {
                result = true;
            }
            if (hasRound()) {
                if (!result || getRound() != other.getRound()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || !this.unknownFields.equals(other.unknownFields)) {
                result2 = false;
            } else {
                result2 = true;
            }
            return result2;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasMode()) {
                hash = (((hash * 37) + 1) * 53) + getMode();
            }
            if (hasRound()) {
                hash = (((hash * 37) + 2) * 53) + getRound();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MotorVibrate parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MotorVibrate) PARSER.parseFrom(data);
        }

        public static MotorVibrate parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorVibrate) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorVibrate parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MotorVibrate) PARSER.parseFrom(data);
        }

        public static MotorVibrate parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MotorVibrate) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MotorVibrate parseFrom(InputStream input) throws IOException {
            return (MotorVibrate) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorVibrate parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorVibrate) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorVibrate parseDelimitedFrom(InputStream input) throws IOException {
            return (MotorVibrate) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MotorVibrate parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorVibrate) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MotorVibrate parseFrom(CodedInputStream input) throws IOException {
            return (MotorVibrate) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MotorVibrate parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MotorVibrate) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MotorVibrate prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static MotorVibrate getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MotorVibrate> parser() {
            return PARSER;
        }

        public Parser<MotorVibrate> getParserForType() {
            return PARSER;
        }

        public MotorVibrate getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class VibrateCnf extends GeneratedMessageV3 implements VibrateCnfOrBuilder {
        private static final VibrateCnf DEFAULT_INSTANCE = new VibrateCnf();
        public static final int MODE_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<VibrateCnf> PARSER = new AbstractParser<VibrateCnf>() {
            public VibrateCnf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new VibrateCnf(input, extensionRegistry);
            }
        };
        public static final int ROUND_FIELD_NUMBER = 3;
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int mode_;
        /* access modifiers changed from: private */
        public int round_;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements VibrateCnfOrBuilder {
            private int bitField0_;
            private int mode_;
            private int round_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return MotorConfOuterClass.internal_static_VibrateCnf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MotorConfOuterClass.internal_static_VibrateCnf_fieldAccessorTable.ensureFieldAccessorsInitialized(VibrateCnf.class, Builder.class);
            }

            private Builder() {
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (VibrateCnf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= -2;
                this.mode_ = 0;
                this.bitField0_ &= -3;
                this.round_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MotorConfOuterClass.internal_static_VibrateCnf_descriptor;
            }

            public VibrateCnf getDefaultInstanceForType() {
                return VibrateCnf.getDefaultInstance();
            }

            public VibrateCnf build() {
                VibrateCnf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public VibrateCnf buildPartial() {
                VibrateCnf result = new VibrateCnf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.mode_ = this.mode_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.round_ = this.round_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* Debug info: failed to restart local var, previous not found, register: 1 */
            public Builder mergeFrom(Message other) {
                if (other instanceof VibrateCnf) {
                    return mergeFrom((VibrateCnf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(VibrateCnf other) {
                if (other != VibrateCnf.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (other.hasMode()) {
                        setMode(other.getMode());
                    }
                    if (other.hasRound()) {
                        setRound(other.getRound());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasType() && hasMode() && hasRound()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                VibrateCnf parsedMessage = null;
                try {
                    VibrateCnf parsedMessage2 = (VibrateCnf) VibrateCnf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    VibrateCnf parsedMessage3 = (VibrateCnf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            public VibrateType getType() {
                VibrateType result = VibrateType.valueOf(this.type_);
                return result == null ? VibrateType.ALARM_CLOCK : result;
            }

            public Builder setType(VibrateType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMode() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getMode() {
                return this.mode_;
            }

            public Builder setMode(int value) {
                this.bitField0_ |= 2;
                this.mode_ = value;
                onChanged();
                return this;
            }

            public Builder clearMode() {
                this.bitField0_ &= -3;
                this.mode_ = 0;
                onChanged();
                return this;
            }

            public boolean hasRound() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getRound() {
                return this.round_;
            }

            public Builder setRound(int value) {
                this.bitField0_ |= 4;
                this.round_ = value;
                onChanged();
                return this;
            }

            public Builder clearRound() {
                this.bitField0_ &= -5;
                this.round_ = 0;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private VibrateCnf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private VibrateCnf() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
            this.mode_ = 0;
            this.round_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private VibrateCnf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            int rawValue = input.readEnum();
                            if (VibrateType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 21:
                            this.bitField0_ |= 2;
                            this.mode_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.round_ = input.readFixed32();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                break;
                            } else {
                                done = true;
                                break;
                            }
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MotorConfOuterClass.internal_static_VibrateCnf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MotorConfOuterClass.internal_static_VibrateCnf_fieldAccessorTable.ensureFieldAccessorsInitialized(VibrateCnf.class, Builder.class);
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        public VibrateType getType() {
            VibrateType result = VibrateType.valueOf(this.type_);
            return result == null ? VibrateType.ALARM_CLOCK : result;
        }

        public boolean hasMode() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getMode() {
            return this.mode_;
        }

        public boolean hasRound() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getRound() {
            return this.round_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMode()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRound()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.type_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.mode_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.round_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.type_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.mode_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.round_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            boolean result3;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof VibrateCnf)) {
                return super.equals(obj);
            }
            VibrateCnf other = (VibrateCnf) obj;
            boolean result4 = 1 != 0 && hasType() == other.hasType();
            if (hasType()) {
                result4 = result4 && this.type_ == other.type_;
            }
            if (!result4 || hasMode() != other.hasMode()) {
                result = false;
            } else {
                result = true;
            }
            if (hasMode()) {
                if (!result || getMode() != other.getMode()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasRound() != other.hasRound()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasRound()) {
                if (!result2 || getRound() != other.getRound()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || !this.unknownFields.equals(other.unknownFields)) {
                result3 = false;
            } else {
                result3 = true;
            }
            return result3;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasType()) {
                hash = (((hash * 37) + 1) * 53) + this.type_;
            }
            if (hasMode()) {
                hash = (((hash * 37) + 2) * 53) + getMode();
            }
            if (hasRound()) {
                hash = (((hash * 37) + 3) * 53) + getRound();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static VibrateCnf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (VibrateCnf) PARSER.parseFrom(data);
        }

        public static VibrateCnf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VibrateCnf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static VibrateCnf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (VibrateCnf) PARSER.parseFrom(data);
        }

        public static VibrateCnf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VibrateCnf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static VibrateCnf parseFrom(InputStream input) throws IOException {
            return (VibrateCnf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static VibrateCnf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VibrateCnf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static VibrateCnf parseDelimitedFrom(InputStream input) throws IOException {
            return (VibrateCnf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static VibrateCnf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VibrateCnf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static VibrateCnf parseFrom(CodedInputStream input) throws IOException {
            return (VibrateCnf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static VibrateCnf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VibrateCnf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(VibrateCnf prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            if (this == DEFAULT_INSTANCE) {
                return new Builder();
            }
            return new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }

        public static VibrateCnf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VibrateCnf> parser() {
            return PARSER;
        }

        public Parser<VibrateCnf> getParserForType() {
            return PARSER;
        }

        public VibrateCnf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum VibrateType implements ProtocolMessageEnum {
        ALARM_CLOCK(0),
        INCOMING_CALL(1),
        SMS(2),
        SEDENTARINESS(3),
        CHARGING(4),
        CALENDAR(5),
        DISTANCE_ALARM(6),
        HEARTRATE_ALARM(7),
        OTHERS(8);
        
        public static final int ALARM_CLOCK_VALUE = 0;
        public static final int CALENDAR_VALUE = 5;
        public static final int CHARGING_VALUE = 4;
        public static final int DISTANCE_ALARM_VALUE = 6;
        public static final int HEARTRATE_ALARM_VALUE = 7;
        public static final int INCOMING_CALL_VALUE = 1;
        public static final int OTHERS_VALUE = 8;
        public static final int SEDENTARINESS_VALUE = 3;
        public static final int SMS_VALUE = 2;
        private static final VibrateType[] VALUES = null;
        private static final EnumLiteMap<VibrateType> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<VibrateType>() {
                public VibrateType findValueByNumber(int number) {
                    return VibrateType.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static VibrateType valueOf(int value2) {
            return forNumber(value2);
        }

        public static VibrateType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return ALARM_CLOCK;
                case 1:
                    return INCOMING_CALL;
                case 2:
                    return SMS;
                case 3:
                    return SEDENTARINESS;
                case 4:
                    return CHARGING;
                case 5:
                    return CALENDAR;
                case 6:
                    return DISTANCE_ALARM;
                case 7:
                    return HEARTRATE_ALARM;
                case 8:
                    return OTHERS;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<VibrateType> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) MotorConfOuterClass.getDescriptor().getEnumTypes().get(0);
        }

        public static VibrateType valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private VibrateType(int value2) {
            this.value = value2;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.MotorConfOuterClass$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfNotification$DataCase = new int[DataCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfSubscriber$DataCase = new int[DataCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfNotification$DataCase[DataCase.CONF.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfNotification$DataCase[DataCase.VIBRATE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfNotification$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfSubscriber$DataCase[DataCase.PARAMS.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfSubscriber$DataCase[DataCase.CONFIRM.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MotorConfOuterClass$MotorConfSubscriber$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public interface MotorConfNotificationOrBuilder extends MessageOrBuilder {
        MotorConf getConf();

        MotorConfOrBuilder getConfOrBuilder();

        DataCase getDataCase();

        MotorOperation getOperation();

        MotorVibrate getVibrate();

        MotorVibrateOrBuilder getVibrateOrBuilder();

        boolean hasConf();

        boolean hasOperation();

        boolean hasVibrate();
    }

    public interface MotorConfOrBuilder extends MessageOrBuilder {
        VibrateCnf getConf(int i);

        int getConfCount();

        List<VibrateCnf> getConfList();

        VibrateCnfOrBuilder getConfOrBuilder(int i);

        List<? extends VibrateCnfOrBuilder> getConfOrBuilderList();

        int getHash();

        boolean hasHash();
    }

    public interface MotorConfSubscriberOrBuilder extends MessageOrBuilder {
        MotorConfirm getConfirm();

        MotorConfirmOrBuilder getConfirmOrBuilder();

        DataCase getDataCase();

        int getHash();

        MotorParams getParams();

        MotorParamsOrBuilder getParamsOrBuilder();

        boolean hasConfirm();

        boolean hasHash();

        boolean hasParams();
    }

    public interface MotorConfirmOrBuilder extends MessageOrBuilder {
        MotorOperation getOperation();

        boolean getRet();

        boolean hasOperation();

        boolean hasRet();
    }

    public interface MotorParamsOrBuilder extends MessageOrBuilder {
        int getModeNum();

        int getTypeNum();

        boolean hasModeNum();

        boolean hasTypeNum();
    }

    public interface MotorVibrateOrBuilder extends MessageOrBuilder {
        int getMode();

        int getRound();

        boolean hasMode();

        boolean hasRound();
    }

    public interface VibrateCnfOrBuilder extends MessageOrBuilder {
        int getMode();

        int getRound();

        VibrateType getType();

        boolean hasMode();

        boolean hasRound();

        boolean hasType();
    }

    private MotorConfOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite) registry);
    }

    public static FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0010motor_conf.proto\"?\n\fMotorConfirm\u0012\"\n\toperation\u0018\u0001 \u0002(\u000e2\u000f.MotorOperation\u0012\u000b\n\u0003ret\u0018\u0002 \u0002(\b\"1\n\u000bMotorParams\u0012\u0010\n\bmode_num\u0018\u0001 \u0002(\u0007\u0012\u0010\n\btype_num\u0018\u0002 \u0002(\u0007\"m\n\u0013MotorConfSubscriber\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u001e\n\u0006params\u0018\u0002 \u0001(\u000b2\f.MotorParamsH\u0000\u0012 \n\u0007confirm\u0018\u0003 \u0001(\u000b2\r.MotorConfirmH\u0000B\u0006\n\u0004data\"E\n\nVibrateCnf\u0012\u001a\n\u0004type\u0018\u0001 \u0002(\u000e2\f.VibrateType\u0012\f\n\u0004mode\u0018\u0002 \u0002(\u0007\u0012\r\n\u0005round\u0018\u0003 \u0002(\u0007\"4\n\tMotorConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0019\n\u0004conf\u0018\u0002 \u0003(\u000b2\u000b.VibrateCnf\"+\n\fMotorVibrate\u0012\f\n\u0004mode\u0018\u0001 \u0002(\u0007", "\u0012\r\n\u0005round\u0018\u0002 \u0002(\u0007\"\u0001\n\u0015MotorConfNotification\u0012\"\n\toperation\u0018\u0001 \u0002(\u000e2\u000f.MotorOperation\u0012\u001a\n\u0004conf\u0018\u0002 \u0001(\u000b2\n.MotorConfH\u0000\u0012 \n\u0007vibrate\u0018\u0003 \u0001(\u000b2\r.MotorVibrateH\u0000B\u0006\n\u0004data*\u0001\n\u000bVibrateType\u0012\u000f\n\u000bALARM_CLOCK\u0010\u0000\u0012\u0011\n\rINCOMING_CALL\u0010\u0001\u0012\u0007\n\u0003SMS\u0010\u0002\u0012\u0011\n\rSEDENTARINESS\u0010\u0003\u0012\f\n\bCHARGING\u0010\u0004\u0012\f\n\bCALENDAR\u0010\u0005\u0012\u0012\n\u000eDISTANCE_ALARM\u0010\u0006\u0012\u0013\n\u000fHEARTRATE_ALARM\u0010\u0007\u0012\n\n\u0006OTHERS\u0010\b*)\n\u000eMotorOperation\u0012\n\n\u0006CONFIG\u0010\u0000\u0012\u000b\n\u0007VIBRATE\u0010\u0001"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                MotorConfOuterClass.descriptor = root;
                return null;
            }
        });
    }
}
