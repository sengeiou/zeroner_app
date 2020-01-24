package com.iwown.ble_module.proto.base;

import com.google.common.net.HttpHeaders;
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

public final class MsgNotifyOuterClass {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgFilter_ID_descriptor = ((Descriptor) internal_static_MsgFilter_descriptor.getNestedTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgFilter_ID_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgFilter_ID_descriptor, new String[]{"Id"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgFilter_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgFilter_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgFilter_descriptor, new String[]{"Hash", "Id"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgHandler_Timing_descriptor = ((Descriptor) internal_static_MsgHandler_descriptor.getNestedTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgHandler_Timing_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgHandler_Timing_descriptor, new String[]{"StartHour", "StartMinute", "EndHour", "EndMinute"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgHandler_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgHandler_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgHandler_descriptor, new String[]{"Hash", "Policy", "Timing"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgNotification_descriptor, new String[]{"Handler", "Filter", "Notify", "Params"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgNotify_Option_descriptor = ((Descriptor) internal_static_MsgNotify_descriptor.getNestedTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgNotify_Option_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgNotify_Option_descriptor, new String[]{HttpHeaders.ACCEPT, "Reject", "Mute"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgNotify_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgNotify_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgNotify_descriptor, new String[]{"Id", "Type", "Status", "Option", "Title", "Detail"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgOperation_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgOperation_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgOperation_descriptor, new String[]{"Id", "Option"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgRequest_descriptor, new String[]{"SupportHandler", "SupportFilter", "SupportNotify", "HandlerHash", "FilterHash", "FilterIdCount", "NotifyTitleLen", "NotifyDetailLen"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_MsgSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_MsgSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_MsgSubscriber_descriptor, new String[]{"Request", "Operation", "Data"});

    public static final class MsgFilter extends GeneratedMessageV3 implements MsgFilterOrBuilder {
        private static final MsgFilter DEFAULT_INSTANCE = new MsgFilter();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int ID_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<MsgFilter> PARSER = new AbstractParser<MsgFilter>() {
            public MsgFilter parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgFilter(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public List<ID> id_;
        private byte memoizedIsInitialized;

        public static final class ID extends GeneratedMessageV3 implements IDOrBuilder {
            private static final ID DEFAULT_INSTANCE = new ID();
            public static final int ID_FIELD_NUMBER = 1;
            @Deprecated
            public static final Parser<ID> PARSER = new AbstractParser<ID>() {
                public ID parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ID(input, extensionRegistry);
                }
            };
            private static final long serialVersionUID = 0;
            /* access modifiers changed from: private */
            public int bitField0_;
            /* access modifiers changed from: private */
            public volatile Object id_;
            private byte memoizedIsInitialized;

            public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements IDOrBuilder {
                private int bitField0_;
                private Object id_;

                public static final Descriptor getDescriptor() {
                    return MsgNotifyOuterClass.internal_static_MsgFilter_ID_descriptor;
                }

                /* access modifiers changed from: protected */
                public FieldAccessorTable internalGetFieldAccessorTable() {
                    return MsgNotifyOuterClass.internal_static_MsgFilter_ID_fieldAccessorTable.ensureFieldAccessorsInitialized(ID.class, Builder.class);
                }

                private Builder() {
                    this.id_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    this.id_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (ID.alwaysUseFieldBuilders) {
                    }
                }

                public Builder clear() {
                    super.clear();
                    this.id_ = "";
                    this.bitField0_ &= -2;
                    return this;
                }

                public Descriptor getDescriptorForType() {
                    return MsgNotifyOuterClass.internal_static_MsgFilter_ID_descriptor;
                }

                public ID getDefaultInstanceForType() {
                    return ID.getDefaultInstance();
                }

                public ID build() {
                    ID result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw newUninitializedMessageException(result);
                }

                public ID buildPartial() {
                    ID result = new ID((com.google.protobuf.GeneratedMessageV3.Builder) this);
                    int to_bitField0_ = 0;
                    if ((this.bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.id_ = this.id_;
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
                    if (other instanceof ID) {
                        return mergeFrom((ID) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(ID other) {
                    if (other != ID.getDefaultInstance()) {
                        if (other.hasId()) {
                            this.bitField0_ |= 1;
                            this.id_ = other.id_;
                            onChanged();
                        }
                        mergeUnknownFields(other.unknownFields);
                        onChanged();
                    }
                    return this;
                }

                public final boolean isInitialized() {
                    if (!hasId()) {
                        return false;
                    }
                    return true;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    ID parsedMessage = null;
                    try {
                        ID parsedMessage2 = (ID) ID.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage2 != null) {
                            mergeFrom(parsedMessage2);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        ID parsedMessage3 = (ID) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        throw th;
                    }
                }

                public boolean hasId() {
                    return (this.bitField0_ & 1) == 1;
                }

                public String getId() {
                    Object ref = this.id_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.id_ = s;
                    return s;
                }

                public ByteString getIdBytes() {
                    Object ref = this.id_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.id_ = b;
                    return b;
                }

                public Builder setId(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.id_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearId() {
                    this.bitField0_ &= -2;
                    this.id_ = ID.getDefaultInstance().getId();
                    onChanged();
                    return this;
                }

                public Builder setIdBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.id_ = value;
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

            private ID(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }

            private ID() {
                this.memoizedIsInitialized = -1;
                this.id_ = "";
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private ID(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            case 10:
                                ByteString bs = input.readBytes();
                                this.bitField0_ |= 1;
                                this.id_ = bs;
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
                return MsgNotifyOuterClass.internal_static_MsgFilter_ID_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgFilter_ID_fieldAccessorTable.ensureFieldAccessorsInitialized(ID.class, Builder.class);
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getId() {
                Object ref = this.id_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.id_ = s;
                }
                return s;
            }

            public ByteString getIdBytes() {
                Object ref = this.id_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.id_ = b;
                return b;
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!hasId()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 1) == 1) {
                    GeneratedMessageV3.writeString(output, 1, this.id_);
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
                    size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.id_);
                }
                int size3 = size2 + this.unknownFields.getSerializedSize();
                this.memoizedSize = size3;
                return size3;
            }

            public boolean equals(Object obj) {
                boolean result;
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof ID)) {
                    return super.equals(obj);
                }
                ID other = (ID) obj;
                boolean result2 = 1 != 0 && hasId() == other.hasId();
                if (hasId()) {
                    result2 = result2 && getId().equals(other.getId());
                }
                if (!result2 || !this.unknownFields.equals(other.unknownFields)) {
                    result = false;
                } else {
                    result = true;
                }
                return result;
            }

            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = getDescriptorForType().hashCode() + 779;
                if (hasId()) {
                    hash = (((hash * 37) + 1) * 53) + getId().hashCode();
                }
                int hash2 = (hash * 29) + this.unknownFields.hashCode();
                this.memoizedHashCode = hash2;
                return hash2;
            }

            public static ID parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ID) PARSER.parseFrom(data);
            }

            public static ID parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ID) PARSER.parseFrom(data, extensionRegistry);
            }

            public static ID parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ID) PARSER.parseFrom(data);
            }

            public static ID parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ID) PARSER.parseFrom(data, extensionRegistry);
            }

            public static ID parseFrom(InputStream input) throws IOException {
                return (ID) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static ID parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ID) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static ID parseDelimitedFrom(InputStream input) throws IOException {
                return (ID) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static ID parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ID) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static ID parseFrom(CodedInputStream input) throws IOException {
                return (ID) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static ID parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ID) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(ID prototype) {
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

            public static ID getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<ID> parser() {
                return PARSER;
            }

            public Parser<ID> getParserForType() {
                return PARSER;
            }

            public ID getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgFilterOrBuilder {
            private int bitField0_;
            private int hash_;
            private RepeatedFieldBuilderV3<ID, Builder, IDOrBuilder> idBuilder_;
            private List<ID> id_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgFilter_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgFilter_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgFilter.class, Builder.class);
            }

            private Builder() {
                this.id_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.id_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MsgFilter.alwaysUseFieldBuilders) {
                    getIdFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.idBuilder_ == null) {
                    this.id_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.idBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgFilter_descriptor;
            }

            public MsgFilter getDefaultInstanceForType() {
                return MsgFilter.getDefaultInstance();
            }

            public MsgFilter build() {
                MsgFilter result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgFilter buildPartial() {
                MsgFilter result = new MsgFilter((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.idBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.id_ = Collections.unmodifiableList(this.id_);
                        this.bitField0_ &= -3;
                    }
                    result.id_ = this.id_;
                } else {
                    result.id_ = this.idBuilder_.build();
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
                if (other instanceof MsgFilter) {
                    return mergeFrom((MsgFilter) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgFilter other) {
                RepeatedFieldBuilderV3<ID, Builder, IDOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != MsgFilter.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (this.idBuilder_ == null) {
                        if (!other.id_.isEmpty()) {
                            if (this.id_.isEmpty()) {
                                this.id_ = other.id_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureIdIsMutable();
                                this.id_.addAll(other.id_);
                            }
                            onChanged();
                        }
                    } else if (!other.id_.isEmpty()) {
                        if (this.idBuilder_.isEmpty()) {
                            this.idBuilder_.dispose();
                            this.idBuilder_ = null;
                            this.id_ = other.id_;
                            this.bitField0_ &= -3;
                            if (MsgFilter.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getIdFieldBuilder();
                            }
                            this.idBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.idBuilder_.addAllMessages(other.id_);
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
                for (int i = 0; i < getIdCount(); i++) {
                    if (!getId(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgFilter parsedMessage = null;
                try {
                    MsgFilter parsedMessage2 = (MsgFilter) MsgFilter.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgFilter parsedMessage3 = (MsgFilter) e.getUnfinishedMessage();
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

            private void ensureIdIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.id_ = new ArrayList(this.id_);
                    this.bitField0_ |= 2;
                }
            }

            public List<ID> getIdList() {
                if (this.idBuilder_ == null) {
                    return Collections.unmodifiableList(this.id_);
                }
                return this.idBuilder_.getMessageList();
            }

            public int getIdCount() {
                if (this.idBuilder_ == null) {
                    return this.id_.size();
                }
                return this.idBuilder_.getCount();
            }

            public ID getId(int index) {
                if (this.idBuilder_ == null) {
                    return (ID) this.id_.get(index);
                }
                return (ID) this.idBuilder_.getMessage(index);
            }

            public Builder setId(int index, ID value) {
                if (this.idBuilder_ != null) {
                    this.idBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureIdIsMutable();
                    this.id_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setId(int index, Builder builderForValue) {
                if (this.idBuilder_ == null) {
                    ensureIdIsMutable();
                    this.id_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.idBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addId(ID value) {
                if (this.idBuilder_ != null) {
                    this.idBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureIdIsMutable();
                    this.id_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addId(int index, ID value) {
                if (this.idBuilder_ != null) {
                    this.idBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureIdIsMutable();
                    this.id_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addId(Builder builderForValue) {
                if (this.idBuilder_ == null) {
                    ensureIdIsMutable();
                    this.id_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.idBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addId(int index, Builder builderForValue) {
                if (this.idBuilder_ == null) {
                    ensureIdIsMutable();
                    this.id_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.idBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllId(Iterable<? extends ID> values) {
                if (this.idBuilder_ == null) {
                    ensureIdIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.id_);
                    onChanged();
                } else {
                    this.idBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearId() {
                if (this.idBuilder_ == null) {
                    this.id_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.idBuilder_.clear();
                }
                return this;
            }

            public Builder removeId(int index) {
                if (this.idBuilder_ == null) {
                    ensureIdIsMutable();
                    this.id_.remove(index);
                    onChanged();
                } else {
                    this.idBuilder_.remove(index);
                }
                return this;
            }

            public Builder getIdBuilder(int index) {
                return (Builder) getIdFieldBuilder().getBuilder(index);
            }

            public IDOrBuilder getIdOrBuilder(int index) {
                if (this.idBuilder_ == null) {
                    return (IDOrBuilder) this.id_.get(index);
                }
                return (IDOrBuilder) this.idBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends IDOrBuilder> getIdOrBuilderList() {
                if (this.idBuilder_ != null) {
                    return this.idBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.id_);
            }

            public Builder addIdBuilder() {
                return (Builder) getIdFieldBuilder().addBuilder(ID.getDefaultInstance());
            }

            public Builder addIdBuilder(int index) {
                return (Builder) getIdFieldBuilder().addBuilder(index, ID.getDefaultInstance());
            }

            public List<Builder> getIdBuilderList() {
                return getIdFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ID, Builder, IDOrBuilder> getIdFieldBuilder() {
                if (this.idBuilder_ == null) {
                    this.idBuilder_ = new RepeatedFieldBuilderV3<>(this.id_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.id_ = null;
                }
                return this.idBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public interface IDOrBuilder extends MessageOrBuilder {
            String getId();

            ByteString getIdBytes();

            boolean hasId();
        }

        private MsgFilter(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MsgFilter() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.id_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgFilter(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.id_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.id_.add(input.readMessage(ID.PARSER, extensionRegistry));
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
                        this.id_ = Collections.unmodifiableList(this.id_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.id_ = Collections.unmodifiableList(this.id_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return MsgNotifyOuterClass.internal_static_MsgFilter_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgFilter_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgFilter.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<ID> getIdList() {
            return this.id_;
        }

        public List<? extends IDOrBuilder> getIdOrBuilderList() {
            return this.id_;
        }

        public int getIdCount() {
            return this.id_.size();
        }

        public ID getId(int index) {
            return (ID) this.id_.get(index);
        }

        public IDOrBuilder getIdOrBuilder(int index) {
            return (IDOrBuilder) this.id_.get(index);
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
            for (int i = 0; i < getIdCount(); i++) {
                if (!getId(i).isInitialized()) {
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
            for (int i = 0; i < this.id_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.id_.get(i));
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
            for (int i = 0; i < this.id_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.id_.get(i));
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
            if (!(obj instanceof MsgFilter)) {
                return super.equals(obj);
            }
            MsgFilter other = (MsgFilter) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getIdList().equals(other.getIdList())) {
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
            if (getIdCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getIdList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgFilter parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgFilter) PARSER.parseFrom(data);
        }

        public static MsgFilter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgFilter) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgFilter parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgFilter) PARSER.parseFrom(data);
        }

        public static MsgFilter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgFilter) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgFilter parseFrom(InputStream input) throws IOException {
            return (MsgFilter) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgFilter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgFilter) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgFilter parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgFilter) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgFilter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgFilter) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgFilter parseFrom(CodedInputStream input) throws IOException {
            return (MsgFilter) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgFilter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgFilter) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgFilter prototype) {
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

        public static MsgFilter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgFilter> parser() {
            return PARSER;
        }

        public Parser<MsgFilter> getParserForType() {
            return PARSER;
        }

        public MsgFilter getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MsgHandler extends GeneratedMessageV3 implements MsgHandlerOrBuilder {
        private static final MsgHandler DEFAULT_INSTANCE = new MsgHandler();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<MsgHandler> PARSER = new AbstractParser<MsgHandler>() {
            public MsgHandler parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgHandler(input, extensionRegistry);
            }
        };
        public static final int POLICY_FIELD_NUMBER = 2;
        public static final int TIMING_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int policy_;
        /* access modifiers changed from: private */
        public Timing timing_;

        public enum Policy implements ProtocolMessageEnum {
            STORE_AND_PROMPT(0),
            STORE_NO_PROMPT(1),
            NO_STORE_NO_PROMPT(2);
            
            public static final int NO_STORE_NO_PROMPT_VALUE = 2;
            public static final int STORE_AND_PROMPT_VALUE = 0;
            public static final int STORE_NO_PROMPT_VALUE = 1;
            private static final Policy[] VALUES = null;
            private static final EnumLiteMap<Policy> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Policy>() {
                    public Policy findValueByNumber(int number) {
                        return Policy.forNumber(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Policy valueOf(int value2) {
                return forNumber(value2);
            }

            public static Policy forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return STORE_AND_PROMPT;
                    case 1:
                        return STORE_NO_PROMPT;
                    case 2:
                        return NO_STORE_NO_PROMPT;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Policy> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) MsgHandler.getDescriptor().getEnumTypes().get(0);
            }

            public static Policy valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private Policy(int value2) {
                this.value = value2;
            }
        }

        public static final class Timing extends GeneratedMessageV3 implements TimingOrBuilder {
            private static final Timing DEFAULT_INSTANCE = new Timing();
            public static final int END_HOUR_FIELD_NUMBER = 3;
            public static final int END_MINUTE_FIELD_NUMBER = 4;
            @Deprecated
            public static final Parser<Timing> PARSER = new AbstractParser<Timing>() {
                public Timing parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Timing(input, extensionRegistry);
                }
            };
            public static final int START_HOUR_FIELD_NUMBER = 1;
            public static final int START_MINUTE_FIELD_NUMBER = 2;
            private static final long serialVersionUID = 0;
            /* access modifiers changed from: private */
            public int bitField0_;
            /* access modifiers changed from: private */
            public int endHour_;
            /* access modifiers changed from: private */
            public int endMinute_;
            private byte memoizedIsInitialized;
            /* access modifiers changed from: private */
            public int startHour_;
            /* access modifiers changed from: private */
            public int startMinute_;

            public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements TimingOrBuilder {
                private int bitField0_;
                private int endHour_;
                private int endMinute_;
                private int startHour_;
                private int startMinute_;

                public static final Descriptor getDescriptor() {
                    return MsgNotifyOuterClass.internal_static_MsgHandler_Timing_descriptor;
                }

                /* access modifiers changed from: protected */
                public FieldAccessorTable internalGetFieldAccessorTable() {
                    return MsgNotifyOuterClass.internal_static_MsgHandler_Timing_fieldAccessorTable.ensureFieldAccessorsInitialized(Timing.class, Builder.class);
                }

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (Timing.alwaysUseFieldBuilders) {
                    }
                }

                public Builder clear() {
                    super.clear();
                    this.startHour_ = 0;
                    this.bitField0_ &= -2;
                    this.startMinute_ = 0;
                    this.bitField0_ &= -3;
                    this.endHour_ = 0;
                    this.bitField0_ &= -5;
                    this.endMinute_ = 0;
                    this.bitField0_ &= -9;
                    return this;
                }

                public Descriptor getDescriptorForType() {
                    return MsgNotifyOuterClass.internal_static_MsgHandler_Timing_descriptor;
                }

                public Timing getDefaultInstanceForType() {
                    return Timing.getDefaultInstance();
                }

                public Timing build() {
                    Timing result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw newUninitializedMessageException(result);
                }

                public Timing buildPartial() {
                    Timing result = new Timing((com.google.protobuf.GeneratedMessageV3.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.startHour_ = this.startHour_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result.startMinute_ = this.startMinute_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    result.endHour_ = this.endHour_;
                    if ((from_bitField0_ & 8) == 8) {
                        to_bitField0_ |= 8;
                    }
                    result.endMinute_ = this.endMinute_;
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
                    if (other instanceof Timing) {
                        return mergeFrom((Timing) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Timing other) {
                    if (other != Timing.getDefaultInstance()) {
                        if (other.hasStartHour()) {
                            setStartHour(other.getStartHour());
                        }
                        if (other.hasStartMinute()) {
                            setStartMinute(other.getStartMinute());
                        }
                        if (other.hasEndHour()) {
                            setEndHour(other.getEndHour());
                        }
                        if (other.hasEndMinute()) {
                            setEndMinute(other.getEndMinute());
                        }
                        mergeUnknownFields(other.unknownFields);
                        onChanged();
                    }
                    return this;
                }

                public final boolean isInitialized() {
                    if (hasStartHour() && hasStartMinute() && hasEndHour() && hasEndMinute()) {
                        return true;
                    }
                    return false;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    Timing parsedMessage = null;
                    try {
                        Timing parsedMessage2 = (Timing) Timing.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage2 != null) {
                            mergeFrom(parsedMessage2);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        Timing parsedMessage3 = (Timing) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        throw th;
                    }
                }

                public boolean hasStartHour() {
                    return (this.bitField0_ & 1) == 1;
                }

                public int getStartHour() {
                    return this.startHour_;
                }

                public Builder setStartHour(int value) {
                    this.bitField0_ |= 1;
                    this.startHour_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearStartHour() {
                    this.bitField0_ &= -2;
                    this.startHour_ = 0;
                    onChanged();
                    return this;
                }

                public boolean hasStartMinute() {
                    return (this.bitField0_ & 2) == 2;
                }

                public int getStartMinute() {
                    return this.startMinute_;
                }

                public Builder setStartMinute(int value) {
                    this.bitField0_ |= 2;
                    this.startMinute_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearStartMinute() {
                    this.bitField0_ &= -3;
                    this.startMinute_ = 0;
                    onChanged();
                    return this;
                }

                public boolean hasEndHour() {
                    return (this.bitField0_ & 4) == 4;
                }

                public int getEndHour() {
                    return this.endHour_;
                }

                public Builder setEndHour(int value) {
                    this.bitField0_ |= 4;
                    this.endHour_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearEndHour() {
                    this.bitField0_ &= -5;
                    this.endHour_ = 0;
                    onChanged();
                    return this;
                }

                public boolean hasEndMinute() {
                    return (this.bitField0_ & 8) == 8;
                }

                public int getEndMinute() {
                    return this.endMinute_;
                }

                public Builder setEndMinute(int value) {
                    this.bitField0_ |= 8;
                    this.endMinute_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearEndMinute() {
                    this.bitField0_ &= -9;
                    this.endMinute_ = 0;
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

            private Timing(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }

            private Timing() {
                this.memoizedIsInitialized = -1;
                this.startHour_ = 0;
                this.startMinute_ = 0;
                this.endHour_ = 0;
                this.endMinute_ = 0;
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private Timing(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.startHour_ = input.readFixed32();
                                break;
                            case 21:
                                this.bitField0_ |= 2;
                                this.startMinute_ = input.readFixed32();
                                break;
                            case 29:
                                this.bitField0_ |= 4;
                                this.endHour_ = input.readFixed32();
                                break;
                            case 37:
                                this.bitField0_ |= 8;
                                this.endMinute_ = input.readFixed32();
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
                return MsgNotifyOuterClass.internal_static_MsgHandler_Timing_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgHandler_Timing_fieldAccessorTable.ensureFieldAccessorsInitialized(Timing.class, Builder.class);
            }

            public boolean hasStartHour() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getStartHour() {
                return this.startHour_;
            }

            public boolean hasStartMinute() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getStartMinute() {
                return this.startMinute_;
            }

            public boolean hasEndHour() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getEndHour() {
                return this.endHour_;
            }

            public boolean hasEndMinute() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getEndMinute() {
                return this.endMinute_;
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!hasStartHour()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else if (!hasStartMinute()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else if (!hasEndHour()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else if (!hasEndMinute()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else {
                    this.memoizedIsInitialized = 1;
                    return true;
                }
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 1) == 1) {
                    output.writeFixed32(1, this.startHour_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeFixed32(2, this.startMinute_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    output.writeFixed32(3, this.endHour_);
                }
                if ((this.bitField0_ & 8) == 8) {
                    output.writeFixed32(4, this.endMinute_);
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
                    size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.startHour_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size2 += CodedOutputStream.computeFixed32Size(2, this.startMinute_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size2 += CodedOutputStream.computeFixed32Size(3, this.endHour_);
                }
                if ((this.bitField0_ & 8) == 8) {
                    size2 += CodedOutputStream.computeFixed32Size(4, this.endMinute_);
                }
                int size3 = size2 + this.unknownFields.getSerializedSize();
                this.memoizedSize = size3;
                return size3;
            }

            public boolean equals(Object obj) {
                boolean result;
                boolean result2;
                boolean result3;
                boolean result4;
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Timing)) {
                    return super.equals(obj);
                }
                Timing other = (Timing) obj;
                boolean result5 = 1 != 0 && hasStartHour() == other.hasStartHour();
                if (hasStartHour()) {
                    result5 = result5 && getStartHour() == other.getStartHour();
                }
                if (!result5 || hasStartMinute() != other.hasStartMinute()) {
                    result = false;
                } else {
                    result = true;
                }
                if (hasStartMinute()) {
                    if (!result || getStartMinute() != other.getStartMinute()) {
                        result = false;
                    } else {
                        result = true;
                    }
                }
                if (!result || hasEndHour() != other.hasEndHour()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
                if (hasEndHour()) {
                    if (!result2 || getEndHour() != other.getEndHour()) {
                        result2 = false;
                    } else {
                        result2 = true;
                    }
                }
                if (!result2 || hasEndMinute() != other.hasEndMinute()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
                if (hasEndMinute()) {
                    if (!result3 || getEndMinute() != other.getEndMinute()) {
                        result3 = false;
                    } else {
                        result3 = true;
                    }
                }
                if (!result3 || !this.unknownFields.equals(other.unknownFields)) {
                    result4 = false;
                } else {
                    result4 = true;
                }
                return result4;
            }

            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = getDescriptorForType().hashCode() + 779;
                if (hasStartHour()) {
                    hash = (((hash * 37) + 1) * 53) + getStartHour();
                }
                if (hasStartMinute()) {
                    hash = (((hash * 37) + 2) * 53) + getStartMinute();
                }
                if (hasEndHour()) {
                    hash = (((hash * 37) + 3) * 53) + getEndHour();
                }
                if (hasEndMinute()) {
                    hash = (((hash * 37) + 4) * 53) + getEndMinute();
                }
                int hash2 = (hash * 29) + this.unknownFields.hashCode();
                this.memoizedHashCode = hash2;
                return hash2;
            }

            public static Timing parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Timing) PARSER.parseFrom(data);
            }

            public static Timing parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Timing) PARSER.parseFrom(data, extensionRegistry);
            }

            public static Timing parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Timing) PARSER.parseFrom(data);
            }

            public static Timing parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Timing) PARSER.parseFrom(data, extensionRegistry);
            }

            public static Timing parseFrom(InputStream input) throws IOException {
                return (Timing) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Timing parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Timing) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Timing parseDelimitedFrom(InputStream input) throws IOException {
                return (Timing) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Timing parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Timing) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Timing parseFrom(CodedInputStream input) throws IOException {
                return (Timing) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Timing parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Timing) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Timing prototype) {
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

            public static Timing getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Timing> parser() {
                return PARSER;
            }

            public Parser<Timing> getParserForType() {
                return PARSER;
            }

            public Timing getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgHandlerOrBuilder {
            private int bitField0_;
            private int hash_;
            private int policy_;
            private SingleFieldBuilderV3<Timing, Builder, TimingOrBuilder> timingBuilder_;
            private Timing timing_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgHandler_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgHandler_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgHandler.class, Builder.class);
            }

            private Builder() {
                this.policy_ = 0;
                this.timing_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.policy_ = 0;
                this.timing_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MsgHandler.alwaysUseFieldBuilders) {
                    getTimingFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.policy_ = 0;
                this.bitField0_ &= -3;
                if (this.timingBuilder_ == null) {
                    this.timing_ = null;
                } else {
                    this.timingBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgHandler_descriptor;
            }

            public MsgHandler getDefaultInstanceForType() {
                return MsgHandler.getDefaultInstance();
            }

            public MsgHandler build() {
                MsgHandler result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgHandler buildPartial() {
                MsgHandler result = new MsgHandler((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.policy_ = this.policy_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.timingBuilder_ == null) {
                    result.timing_ = this.timing_;
                } else {
                    result.timing_ = (Timing) this.timingBuilder_.build();
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
                if (other instanceof MsgHandler) {
                    return mergeFrom((MsgHandler) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgHandler other) {
                if (other != MsgHandler.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasPolicy()) {
                        setPolicy(other.getPolicy());
                    }
                    if (other.hasTiming()) {
                        mergeTiming(other.getTiming());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasPolicy() && hasTiming() && getTiming().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgHandler parsedMessage = null;
                try {
                    MsgHandler parsedMessage2 = (MsgHandler) MsgHandler.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgHandler parsedMessage3 = (MsgHandler) e.getUnfinishedMessage();
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

            public boolean hasPolicy() {
                return (this.bitField0_ & 2) == 2;
            }

            public Policy getPolicy() {
                Policy result = Policy.valueOf(this.policy_);
                return result == null ? Policy.STORE_AND_PROMPT : result;
            }

            public Builder setPolicy(Policy value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.policy_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearPolicy() {
                this.bitField0_ &= -3;
                this.policy_ = 0;
                onChanged();
                return this;
            }

            public boolean hasTiming() {
                return (this.bitField0_ & 4) == 4;
            }

            public Timing getTiming() {
                if (this.timingBuilder_ == null) {
                    return this.timing_ == null ? Timing.getDefaultInstance() : this.timing_;
                }
                return (Timing) this.timingBuilder_.getMessage();
            }

            public Builder setTiming(Timing value) {
                if (this.timingBuilder_ != null) {
                    this.timingBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.timing_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setTiming(Builder builderForValue) {
                if (this.timingBuilder_ == null) {
                    this.timing_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timingBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeTiming(Timing value) {
                if (this.timingBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.timing_ == null || this.timing_ == Timing.getDefaultInstance()) {
                        this.timing_ = value;
                    } else {
                        this.timing_ = Timing.newBuilder(this.timing_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.timingBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearTiming() {
                if (this.timingBuilder_ == null) {
                    this.timing_ = null;
                    onChanged();
                } else {
                    this.timingBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getTimingBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getTimingFieldBuilder().getBuilder();
            }

            public TimingOrBuilder getTimingOrBuilder() {
                if (this.timingBuilder_ != null) {
                    return (TimingOrBuilder) this.timingBuilder_.getMessageOrBuilder();
                }
                return this.timing_ == null ? Timing.getDefaultInstance() : this.timing_;
            }

            private SingleFieldBuilderV3<Timing, Builder, TimingOrBuilder> getTimingFieldBuilder() {
                if (this.timingBuilder_ == null) {
                    this.timingBuilder_ = new SingleFieldBuilderV3<>(getTiming(), getParentForChildren(), isClean());
                    this.timing_ = null;
                }
                return this.timingBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public interface TimingOrBuilder extends MessageOrBuilder {
            int getEndHour();

            int getEndMinute();

            int getStartHour();

            int getStartMinute();

            boolean hasEndHour();

            boolean hasEndMinute();

            boolean hasStartHour();

            boolean hasStartMinute();
        }

        private MsgHandler(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MsgHandler() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.policy_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgHandler(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 16:
                            int rawValue = input.readEnum();
                            if (Policy.valueOf(rawValue) != null) {
                                this.bitField0_ |= 2;
                                this.policy_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue);
                                break;
                            }
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.timing_.toBuilder();
                            }
                            this.timing_ = (Timing) input.readMessage(Timing.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.timing_);
                                this.timing_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
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
            return MsgNotifyOuterClass.internal_static_MsgHandler_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgHandler_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgHandler.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasPolicy() {
            return (this.bitField0_ & 2) == 2;
        }

        public Policy getPolicy() {
            Policy result = Policy.valueOf(this.policy_);
            return result == null ? Policy.STORE_AND_PROMPT : result;
        }

        public boolean hasTiming() {
            return (this.bitField0_ & 4) == 4;
        }

        public Timing getTiming() {
            return this.timing_ == null ? Timing.getDefaultInstance() : this.timing_;
        }

        public TimingOrBuilder getTimingOrBuilder() {
            return this.timing_ == null ? Timing.getDefaultInstance() : this.timing_;
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
            } else if (!hasPolicy()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasTiming()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getTiming().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.policy_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getTiming());
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
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.policy_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getTiming());
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
            if (!(obj instanceof MsgHandler)) {
                return super.equals(obj);
            }
            MsgHandler other = (MsgHandler) obj;
            boolean result4 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result4 = result4 && getHash() == other.getHash();
            }
            if (!result4 || hasPolicy() != other.hasPolicy()) {
                result = false;
            } else {
                result = true;
            }
            if (hasPolicy()) {
                if (!result || this.policy_ != other.policy_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasTiming() != other.hasTiming()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasTiming()) {
                if (!result2 || !getTiming().equals(other.getTiming())) {
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
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasPolicy()) {
                hash = (((hash * 37) + 2) * 53) + this.policy_;
            }
            if (hasTiming()) {
                hash = (((hash * 37) + 3) * 53) + getTiming().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgHandler parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgHandler) PARSER.parseFrom(data);
        }

        public static MsgHandler parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgHandler) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgHandler parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgHandler) PARSER.parseFrom(data);
        }

        public static MsgHandler parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgHandler) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgHandler parseFrom(InputStream input) throws IOException {
            return (MsgHandler) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgHandler parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgHandler) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgHandler parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgHandler) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgHandler parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgHandler) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgHandler parseFrom(CodedInputStream input) throws IOException {
            return (MsgHandler) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgHandler parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgHandler) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgHandler prototype) {
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

        public static MsgHandler getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgHandler> parser() {
            return PARSER;
        }

        public Parser<MsgHandler> getParserForType() {
            return PARSER;
        }

        public MsgHandler getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MsgNotification extends GeneratedMessageV3 implements MsgNotificationOrBuilder {
        private static final MsgNotification DEFAULT_INSTANCE = new MsgNotification();
        public static final int FILTER_FIELD_NUMBER = 2;
        public static final int HANDLER_FIELD_NUMBER = 1;
        public static final int NOTIFY_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<MsgNotification> PARSER = new AbstractParser<MsgNotification>() {
            public MsgNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgNotification(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int paramsCase_;
        /* access modifiers changed from: private */
        public Object params_;

        public enum ParamsCase implements EnumLite {
            HANDLER(1),
            FILTER(2),
            NOTIFY(3),
            PARAMS_NOT_SET(0);
            
            private final int value;

            private ParamsCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static ParamsCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static ParamsCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return PARAMS_NOT_SET;
                    case 1:
                        return HANDLER;
                    case 2:
                        return FILTER;
                    case 3:
                        return NOTIFY;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgNotificationOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<MsgFilter, Builder, MsgFilterOrBuilder> filterBuilder_;
            private SingleFieldBuilderV3<MsgHandler, Builder, MsgHandlerOrBuilder> handlerBuilder_;
            private SingleFieldBuilderV3<MsgNotify, Builder, MsgNotifyOrBuilder> notifyBuilder_;
            private int paramsCase_;
            private Object params_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgNotification.class, Builder.class);
            }

            private Builder() {
                this.paramsCase_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.paramsCase_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MsgNotification.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.paramsCase_ = 0;
                this.params_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgNotification_descriptor;
            }

            public MsgNotification getDefaultInstanceForType() {
                return MsgNotification.getDefaultInstance();
            }

            public MsgNotification build() {
                MsgNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgNotification buildPartial() {
                MsgNotification result = new MsgNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if (this.paramsCase_ == 1) {
                    if (this.handlerBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.handlerBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 2) {
                    if (this.filterBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.filterBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 3) {
                    if (this.notifyBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.notifyBuilder_.build();
                    }
                }
                result.bitField0_ = 0;
                result.paramsCase_ = this.paramsCase_;
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
                if (other instanceof MsgNotification) {
                    return mergeFrom((MsgNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgNotification other) {
                if (other != MsgNotification.getDefaultInstance()) {
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgNotification$ParamsCase[other.getParamsCase().ordinal()]) {
                        case 1:
                            mergeHandler(other.getHandler());
                            break;
                        case 2:
                            mergeFilter(other.getFilter());
                            break;
                        case 3:
                            mergeNotify(other.getNotify());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHandler() && !getHandler().isInitialized()) {
                    return false;
                }
                if (hasFilter() && !getFilter().isInitialized()) {
                    return false;
                }
                if (!hasNotify() || getNotify().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgNotification parsedMessage = null;
                try {
                    MsgNotification parsedMessage2 = (MsgNotification) MsgNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgNotification parsedMessage3 = (MsgNotification) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public ParamsCase getParamsCase() {
                return ParamsCase.forNumber(this.paramsCase_);
            }

            public Builder clearParams() {
                this.paramsCase_ = 0;
                this.params_ = null;
                onChanged();
                return this;
            }

            public boolean hasHandler() {
                return this.paramsCase_ == 1;
            }

            public MsgHandler getHandler() {
                if (this.handlerBuilder_ == null) {
                    if (this.paramsCase_ == 1) {
                        return (MsgHandler) this.params_;
                    }
                    return MsgHandler.getDefaultInstance();
                } else if (this.paramsCase_ == 1) {
                    return (MsgHandler) this.handlerBuilder_.getMessage();
                } else {
                    return MsgHandler.getDefaultInstance();
                }
            }

            public Builder setHandler(MsgHandler value) {
                if (this.handlerBuilder_ != null) {
                    this.handlerBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder setHandler(Builder builderForValue) {
                if (this.handlerBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.handlerBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder mergeHandler(MsgHandler value) {
                if (this.handlerBuilder_ == null) {
                    if (this.paramsCase_ != 1 || this.params_ == MsgHandler.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = MsgHandler.newBuilder((MsgHandler) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 1) {
                        this.handlerBuilder_.mergeFrom(value);
                    }
                    this.handlerBuilder_.setMessage(value);
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder clearHandler() {
                if (this.handlerBuilder_ != null) {
                    if (this.paramsCase_ == 1) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.handlerBuilder_.clear();
                } else if (this.paramsCase_ == 1) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getHandlerBuilder() {
                return (Builder) getHandlerFieldBuilder().getBuilder();
            }

            public MsgHandlerOrBuilder getHandlerOrBuilder() {
                if (this.paramsCase_ == 1 && this.handlerBuilder_ != null) {
                    return (MsgHandlerOrBuilder) this.handlerBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 1) {
                    return (MsgHandler) this.params_;
                }
                return MsgHandler.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MsgHandler, Builder, MsgHandlerOrBuilder> getHandlerFieldBuilder() {
                if (this.handlerBuilder_ == null) {
                    if (this.paramsCase_ != 1) {
                        this.params_ = MsgHandler.getDefaultInstance();
                    }
                    this.handlerBuilder_ = new SingleFieldBuilderV3<>((MsgHandler) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 1;
                onChanged();
                return this.handlerBuilder_;
            }

            public boolean hasFilter() {
                return this.paramsCase_ == 2;
            }

            public MsgFilter getFilter() {
                if (this.filterBuilder_ == null) {
                    if (this.paramsCase_ == 2) {
                        return (MsgFilter) this.params_;
                    }
                    return MsgFilter.getDefaultInstance();
                } else if (this.paramsCase_ == 2) {
                    return (MsgFilter) this.filterBuilder_.getMessage();
                } else {
                    return MsgFilter.getDefaultInstance();
                }
            }

            public Builder setFilter(MsgFilter value) {
                if (this.filterBuilder_ != null) {
                    this.filterBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder setFilter(Builder builderForValue) {
                if (this.filterBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.filterBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder mergeFilter(MsgFilter value) {
                if (this.filterBuilder_ == null) {
                    if (this.paramsCase_ != 2 || this.params_ == MsgFilter.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = MsgFilter.newBuilder((MsgFilter) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 2) {
                        this.filterBuilder_.mergeFrom(value);
                    }
                    this.filterBuilder_.setMessage(value);
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder clearFilter() {
                if (this.filterBuilder_ != null) {
                    if (this.paramsCase_ == 2) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.filterBuilder_.clear();
                } else if (this.paramsCase_ == 2) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getFilterBuilder() {
                return (Builder) getFilterFieldBuilder().getBuilder();
            }

            public MsgFilterOrBuilder getFilterOrBuilder() {
                if (this.paramsCase_ == 2 && this.filterBuilder_ != null) {
                    return (MsgFilterOrBuilder) this.filterBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 2) {
                    return (MsgFilter) this.params_;
                }
                return MsgFilter.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MsgFilter, Builder, MsgFilterOrBuilder> getFilterFieldBuilder() {
                if (this.filterBuilder_ == null) {
                    if (this.paramsCase_ != 2) {
                        this.params_ = MsgFilter.getDefaultInstance();
                    }
                    this.filterBuilder_ = new SingleFieldBuilderV3<>((MsgFilter) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 2;
                onChanged();
                return this.filterBuilder_;
            }

            public boolean hasNotify() {
                return this.paramsCase_ == 3;
            }

            public MsgNotify getNotify() {
                if (this.notifyBuilder_ == null) {
                    if (this.paramsCase_ == 3) {
                        return (MsgNotify) this.params_;
                    }
                    return MsgNotify.getDefaultInstance();
                } else if (this.paramsCase_ == 3) {
                    return (MsgNotify) this.notifyBuilder_.getMessage();
                } else {
                    return MsgNotify.getDefaultInstance();
                }
            }

            public Builder setNotify(MsgNotify value) {
                if (this.notifyBuilder_ != null) {
                    this.notifyBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder setNotify(Builder builderForValue) {
                if (this.notifyBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.notifyBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder mergeNotify(MsgNotify value) {
                if (this.notifyBuilder_ == null) {
                    if (this.paramsCase_ != 3 || this.params_ == MsgNotify.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = MsgNotify.newBuilder((MsgNotify) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 3) {
                        this.notifyBuilder_.mergeFrom(value);
                    }
                    this.notifyBuilder_.setMessage(value);
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder clearNotify() {
                if (this.notifyBuilder_ != null) {
                    if (this.paramsCase_ == 3) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.notifyBuilder_.clear();
                } else if (this.paramsCase_ == 3) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getNotifyBuilder() {
                return (Builder) getNotifyFieldBuilder().getBuilder();
            }

            public MsgNotifyOrBuilder getNotifyOrBuilder() {
                if (this.paramsCase_ == 3 && this.notifyBuilder_ != null) {
                    return (MsgNotifyOrBuilder) this.notifyBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 3) {
                    return (MsgNotify) this.params_;
                }
                return MsgNotify.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MsgNotify, Builder, MsgNotifyOrBuilder> getNotifyFieldBuilder() {
                if (this.notifyBuilder_ == null) {
                    if (this.paramsCase_ != 3) {
                        this.params_ = MsgNotify.getDefaultInstance();
                    }
                    this.notifyBuilder_ = new SingleFieldBuilderV3<>((MsgNotify) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 3;
                onChanged();
                return this.notifyBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MsgNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.paramsCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private MsgNotification() {
            this.paramsCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 10:
                            Builder subBuilder = null;
                            if (this.paramsCase_ == 1) {
                                subBuilder = ((MsgHandler) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(MsgHandler.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((MsgHandler) this.params_);
                                this.params_ = subBuilder.buildPartial();
                            }
                            this.paramsCase_ = 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if (this.paramsCase_ == 2) {
                                subBuilder2 = ((MsgFilter) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(MsgFilter.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((MsgFilter) this.params_);
                                this.params_ = subBuilder2.buildPartial();
                            }
                            this.paramsCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder3 = null;
                            if (this.paramsCase_ == 3) {
                                subBuilder3 = ((MsgNotify) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(MsgNotify.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom((MsgNotify) this.params_);
                                this.params_ = subBuilder3.buildPartial();
                            }
                            this.paramsCase_ = 3;
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
            return MsgNotifyOuterClass.internal_static_MsgNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgNotification.class, Builder.class);
        }

        public ParamsCase getParamsCase() {
            return ParamsCase.forNumber(this.paramsCase_);
        }

        public boolean hasHandler() {
            return this.paramsCase_ == 1;
        }

        public MsgHandler getHandler() {
            if (this.paramsCase_ == 1) {
                return (MsgHandler) this.params_;
            }
            return MsgHandler.getDefaultInstance();
        }

        public MsgHandlerOrBuilder getHandlerOrBuilder() {
            if (this.paramsCase_ == 1) {
                return (MsgHandler) this.params_;
            }
            return MsgHandler.getDefaultInstance();
        }

        public boolean hasFilter() {
            return this.paramsCase_ == 2;
        }

        public MsgFilter getFilter() {
            if (this.paramsCase_ == 2) {
                return (MsgFilter) this.params_;
            }
            return MsgFilter.getDefaultInstance();
        }

        public MsgFilterOrBuilder getFilterOrBuilder() {
            if (this.paramsCase_ == 2) {
                return (MsgFilter) this.params_;
            }
            return MsgFilter.getDefaultInstance();
        }

        public boolean hasNotify() {
            return this.paramsCase_ == 3;
        }

        public MsgNotify getNotify() {
            if (this.paramsCase_ == 3) {
                return (MsgNotify) this.params_;
            }
            return MsgNotify.getDefaultInstance();
        }

        public MsgNotifyOrBuilder getNotifyOrBuilder() {
            if (this.paramsCase_ == 3) {
                return (MsgNotify) this.params_;
            }
            return MsgNotify.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasHandler() && !getHandler().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasFilter() && !getFilter().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasNotify() || getNotify().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.paramsCase_ == 1) {
                output.writeMessage(1, (MsgHandler) this.params_);
            }
            if (this.paramsCase_ == 2) {
                output.writeMessage(2, (MsgFilter) this.params_);
            }
            if (this.paramsCase_ == 3) {
                output.writeMessage(3, (MsgNotify) this.params_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if (this.paramsCase_ == 1) {
                size2 = 0 + CodedOutputStream.computeMessageSize(1, (MsgHandler) this.params_);
            }
            if (this.paramsCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (MsgFilter) this.params_);
            }
            if (this.paramsCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (MsgNotify) this.params_);
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
            if (!(obj instanceof MsgNotification)) {
                return super.equals(obj);
            }
            MsgNotification other = (MsgNotification) obj;
            if (1 == 0 || !getParamsCase().equals(other.getParamsCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.paramsCase_) {
                case 1:
                    if (result && getHandler().equals(other.getHandler())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 2:
                    if (result && getFilter().equals(other.getFilter())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getNotify().equals(other.getNotify())) {
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
            switch (this.paramsCase_) {
                case 1:
                    hash = (((hash * 37) + 1) * 53) + getHandler().hashCode();
                    break;
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getFilter().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getNotify().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgNotification) PARSER.parseFrom(data);
        }

        public static MsgNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgNotification) PARSER.parseFrom(data);
        }

        public static MsgNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgNotification parseFrom(InputStream input) throws IOException {
            return (MsgNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgNotification parseFrom(CodedInputStream input) throws IOException {
            return (MsgNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgNotification prototype) {
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

        public static MsgNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgNotification> parser() {
            return PARSER;
        }

        public Parser<MsgNotification> getParserForType() {
            return PARSER;
        }

        public MsgNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MsgNotify extends GeneratedMessageV3 implements MsgNotifyOrBuilder {
        private static final MsgNotify DEFAULT_INSTANCE = new MsgNotify();
        public static final int DETAIL_FIELD_NUMBER = 6;
        public static final int ID_FIELD_NUMBER = 1;
        public static final int OPTION_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<MsgNotify> PARSER = new AbstractParser<MsgNotify>() {
            public MsgNotify parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgNotify(input, extensionRegistry);
            }
        };
        public static final int STATUS_FIELD_NUMBER = 3;
        public static final int TITLE_FIELD_NUMBER = 5;
        public static final int TYPE_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public volatile Object detail_;
        /* access modifiers changed from: private */
        public int id_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public Option option_;
        /* access modifiers changed from: private */
        public int status_;
        /* access modifiers changed from: private */
        public volatile Object title_;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Option extends GeneratedMessageV3 implements OptionOrBuilder {
            public static final int ACCEPT_FIELD_NUMBER = 1;
            private static final Option DEFAULT_INSTANCE = new Option();
            public static final int MUTE_FIELD_NUMBER = 3;
            @Deprecated
            public static final Parser<Option> PARSER = new AbstractParser<Option>() {
                public Option parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Option(input, extensionRegistry);
                }
            };
            public static final int REJECT_FIELD_NUMBER = 2;
            private static final long serialVersionUID = 0;
            /* access modifiers changed from: private */
            public boolean accept_;
            /* access modifiers changed from: private */
            public int bitField0_;
            private byte memoizedIsInitialized;
            /* access modifiers changed from: private */
            public boolean mute_;
            /* access modifiers changed from: private */
            public boolean reject_;

            public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OptionOrBuilder {
                private boolean accept_;
                private int bitField0_;
                private boolean mute_;
                private boolean reject_;

                public static final Descriptor getDescriptor() {
                    return MsgNotifyOuterClass.internal_static_MsgNotify_Option_descriptor;
                }

                /* access modifiers changed from: protected */
                public FieldAccessorTable internalGetFieldAccessorTable() {
                    return MsgNotifyOuterClass.internal_static_MsgNotify_Option_fieldAccessorTable.ensureFieldAccessorsInitialized(Option.class, Builder.class);
                }

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (Option.alwaysUseFieldBuilders) {
                    }
                }

                public Builder clear() {
                    super.clear();
                    this.accept_ = false;
                    this.bitField0_ &= -2;
                    this.reject_ = false;
                    this.bitField0_ &= -3;
                    this.mute_ = false;
                    this.bitField0_ &= -5;
                    return this;
                }

                public Descriptor getDescriptorForType() {
                    return MsgNotifyOuterClass.internal_static_MsgNotify_Option_descriptor;
                }

                public Option getDefaultInstanceForType() {
                    return Option.getDefaultInstance();
                }

                public Option build() {
                    Option result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw newUninitializedMessageException(result);
                }

                public Option buildPartial() {
                    Option result = new Option((com.google.protobuf.GeneratedMessageV3.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.accept_ = this.accept_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result.reject_ = this.reject_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    result.mute_ = this.mute_;
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
                    if (other instanceof Option) {
                        return mergeFrom((Option) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Option other) {
                    if (other != Option.getDefaultInstance()) {
                        if (other.hasAccept()) {
                            setAccept(other.getAccept());
                        }
                        if (other.hasReject()) {
                            setReject(other.getReject());
                        }
                        if (other.hasMute()) {
                            setMute(other.getMute());
                        }
                        mergeUnknownFields(other.unknownFields);
                        onChanged();
                    }
                    return this;
                }

                public final boolean isInitialized() {
                    if (hasAccept() && hasReject() && hasMute()) {
                        return true;
                    }
                    return false;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    Option parsedMessage = null;
                    try {
                        Option parsedMessage2 = (Option) Option.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage2 != null) {
                            mergeFrom(parsedMessage2);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        Option parsedMessage3 = (Option) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        throw th;
                    }
                }

                public boolean hasAccept() {
                    return (this.bitField0_ & 1) == 1;
                }

                public boolean getAccept() {
                    return this.accept_;
                }

                public Builder setAccept(boolean value) {
                    this.bitField0_ |= 1;
                    this.accept_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearAccept() {
                    this.bitField0_ &= -2;
                    this.accept_ = false;
                    onChanged();
                    return this;
                }

                public boolean hasReject() {
                    return (this.bitField0_ & 2) == 2;
                }

                public boolean getReject() {
                    return this.reject_;
                }

                public Builder setReject(boolean value) {
                    this.bitField0_ |= 2;
                    this.reject_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearReject() {
                    this.bitField0_ &= -3;
                    this.reject_ = false;
                    onChanged();
                    return this;
                }

                public boolean hasMute() {
                    return (this.bitField0_ & 4) == 4;
                }

                public boolean getMute() {
                    return this.mute_;
                }

                public Builder setMute(boolean value) {
                    this.bitField0_ |= 4;
                    this.mute_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearMute() {
                    this.bitField0_ &= -5;
                    this.mute_ = false;
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

            private Option(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }

            private Option() {
                this.memoizedIsInitialized = -1;
                this.accept_ = false;
                this.reject_ = false;
                this.mute_ = false;
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private Option(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.bitField0_ |= 1;
                                this.accept_ = input.readBool();
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.reject_ = input.readBool();
                                break;
                            case 24:
                                this.bitField0_ |= 4;
                                this.mute_ = input.readBool();
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
                return MsgNotifyOuterClass.internal_static_MsgNotify_Option_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgNotify_Option_fieldAccessorTable.ensureFieldAccessorsInitialized(Option.class, Builder.class);
            }

            public boolean hasAccept() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getAccept() {
                return this.accept_;
            }

            public boolean hasReject() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getReject() {
                return this.reject_;
            }

            public boolean hasMute() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getMute() {
                return this.mute_;
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!hasAccept()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else if (!hasReject()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else if (!hasMute()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else {
                    this.memoizedIsInitialized = 1;
                    return true;
                }
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 1) == 1) {
                    output.writeBool(1, this.accept_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeBool(2, this.reject_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    output.writeBool(3, this.mute_);
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
                    size2 = 0 + CodedOutputStream.computeBoolSize(1, this.accept_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size2 += CodedOutputStream.computeBoolSize(2, this.reject_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size2 += CodedOutputStream.computeBoolSize(3, this.mute_);
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
                if (!(obj instanceof Option)) {
                    return super.equals(obj);
                }
                Option other = (Option) obj;
                boolean result4 = 1 != 0 && hasAccept() == other.hasAccept();
                if (hasAccept()) {
                    result4 = result4 && getAccept() == other.getAccept();
                }
                if (!result4 || hasReject() != other.hasReject()) {
                    result = false;
                } else {
                    result = true;
                }
                if (hasReject()) {
                    if (!result || getReject() != other.getReject()) {
                        result = false;
                    } else {
                        result = true;
                    }
                }
                if (!result || hasMute() != other.hasMute()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
                if (hasMute()) {
                    if (!result2 || getMute() != other.getMute()) {
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
                if (hasAccept()) {
                    hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getAccept());
                }
                if (hasReject()) {
                    hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getReject());
                }
                if (hasMute()) {
                    hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getMute());
                }
                int hash2 = (hash * 29) + this.unknownFields.hashCode();
                this.memoizedHashCode = hash2;
                return hash2;
            }

            public static Option parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Option) PARSER.parseFrom(data);
            }

            public static Option parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Option) PARSER.parseFrom(data, extensionRegistry);
            }

            public static Option parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Option) PARSER.parseFrom(data);
            }

            public static Option parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Option) PARSER.parseFrom(data, extensionRegistry);
            }

            public static Option parseFrom(InputStream input) throws IOException {
                return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Option parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Option parseDelimitedFrom(InputStream input) throws IOException {
                return (Option) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Option parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Option) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Option parseFrom(CodedInputStream input) throws IOException {
                return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Option parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Option prototype) {
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

            public static Option getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Option> parser() {
                return PARSER;
            }

            public Parser<Option> getParserForType() {
                return PARSER;
            }

            public Option getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }
        }

        public enum Status implements ProtocolMessageEnum {
            ADDED(0),
            REMOVED(1),
            UPDATED(2);
            
            public static final int ADDED_VALUE = 0;
            public static final int REMOVED_VALUE = 1;
            public static final int UPDATED_VALUE = 2;
            private static final Status[] VALUES = null;
            private static final EnumLiteMap<Status> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Status>() {
                    public Status findValueByNumber(int number) {
                        return Status.forNumber(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Status valueOf(int value2) {
                return forNumber(value2);
            }

            public static Status forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return ADDED;
                    case 1:
                        return REMOVED;
                    case 2:
                        return UPDATED;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Status> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) MsgNotify.getDescriptor().getEnumTypes().get(1);
            }

            public static Status valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private Status(int value2) {
                this.value = value2;
            }
        }

        public enum Type implements ProtocolMessageEnum {
            CALL(128),
            SMS(129);
            
            public static final int CALL_VALUE = 128;
            public static final int SMS_VALUE = 129;
            private static final Type[] VALUES = null;
            private static final EnumLiteMap<Type> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Type>() {
                    public Type findValueByNumber(int number) {
                        return Type.forNumber(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Type valueOf(int value2) {
                return forNumber(value2);
            }

            public static Type forNumber(int value2) {
                switch (value2) {
                    case 128:
                        return CALL;
                    case 129:
                        return SMS;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) MsgNotify.getDescriptor().getEnumTypes().get(0);
            }

            public static Type valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private Type(int value2) {
                this.value = value2;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgNotifyOrBuilder {
            private int bitField0_;
            private Object detail_;
            private int id_;
            private SingleFieldBuilderV3<Option, Builder, OptionOrBuilder> optionBuilder_;
            private Option option_;
            private int status_;
            private Object title_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgNotify_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgNotify_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgNotify.class, Builder.class);
            }

            private Builder() {
                this.type_ = 128;
                this.status_ = 0;
                this.option_ = null;
                this.title_ = "";
                this.detail_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.type_ = 128;
                this.status_ = 0;
                this.option_ = null;
                this.title_ = "";
                this.detail_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MsgNotify.alwaysUseFieldBuilders) {
                    getOptionFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.id_ = 0;
                this.bitField0_ &= -2;
                this.type_ = 128;
                this.bitField0_ &= -3;
                this.status_ = 0;
                this.bitField0_ &= -5;
                if (this.optionBuilder_ == null) {
                    this.option_ = null;
                } else {
                    this.optionBuilder_.clear();
                }
                this.bitField0_ &= -9;
                this.title_ = "";
                this.bitField0_ &= -17;
                this.detail_ = "";
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgNotify_descriptor;
            }

            public MsgNotify getDefaultInstanceForType() {
                return MsgNotify.getDefaultInstance();
            }

            public MsgNotify build() {
                MsgNotify result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgNotify buildPartial() {
                MsgNotify result = new MsgNotify((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.id_ = this.id_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.status_ = this.status_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.optionBuilder_ == null) {
                    result.option_ = this.option_;
                } else {
                    result.option_ = (Option) this.optionBuilder_.build();
                }
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.title_ = this.title_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.detail_ = this.detail_;
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
                if (other instanceof MsgNotify) {
                    return mergeFrom((MsgNotify) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgNotify other) {
                if (other != MsgNotify.getDefaultInstance()) {
                    if (other.hasId()) {
                        setId(other.getId());
                    }
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (other.hasStatus()) {
                        setStatus(other.getStatus());
                    }
                    if (other.hasOption()) {
                        mergeOption(other.getOption());
                    }
                    if (other.hasTitle()) {
                        this.bitField0_ |= 16;
                        this.title_ = other.title_;
                        onChanged();
                    }
                    if (other.hasDetail()) {
                        this.bitField0_ |= 32;
                        this.detail_ = other.detail_;
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasId() && hasType() && hasStatus() && hasOption() && hasTitle() && hasDetail() && getOption().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgNotify parsedMessage = null;
                try {
                    MsgNotify parsedMessage2 = (MsgNotify) MsgNotify.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgNotify parsedMessage3 = (MsgNotify) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getId() {
                return this.id_;
            }

            public Builder setId(int value) {
                this.bitField0_ |= 1;
                this.id_ = value;
                onChanged();
                return this;
            }

            public Builder clearId() {
                this.bitField0_ &= -2;
                this.id_ = 0;
                onChanged();
                return this;
            }

            public boolean hasType() {
                return (this.bitField0_ & 2) == 2;
            }

            public Type getType() {
                Type result = Type.valueOf(this.type_);
                return result == null ? Type.CALL : result;
            }

            public Builder setType(Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.type_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -3;
                this.type_ = 128;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return (this.bitField0_ & 4) == 4;
            }

            public Status getStatus() {
                Status result = Status.valueOf(this.status_);
                return result == null ? Status.ADDED : result;
            }

            public Builder setStatus(Status value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.status_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -5;
                this.status_ = 0;
                onChanged();
                return this;
            }

            public boolean hasOption() {
                return (this.bitField0_ & 8) == 8;
            }

            public Option getOption() {
                if (this.optionBuilder_ == null) {
                    return this.option_ == null ? Option.getDefaultInstance() : this.option_;
                }
                return (Option) this.optionBuilder_.getMessage();
            }

            public Builder setOption(Option value) {
                if (this.optionBuilder_ != null) {
                    this.optionBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.option_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setOption(Builder builderForValue) {
                if (this.optionBuilder_ == null) {
                    this.option_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeOption(Option value) {
                if (this.optionBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.option_ == null || this.option_ == Option.getDefaultInstance()) {
                        this.option_ = value;
                    } else {
                        this.option_ = Option.newBuilder(this.option_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearOption() {
                if (this.optionBuilder_ == null) {
                    this.option_ = null;
                    onChanged();
                } else {
                    this.optionBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getOptionBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getOptionFieldBuilder().getBuilder();
            }

            public OptionOrBuilder getOptionOrBuilder() {
                if (this.optionBuilder_ != null) {
                    return (OptionOrBuilder) this.optionBuilder_.getMessageOrBuilder();
                }
                return this.option_ == null ? Option.getDefaultInstance() : this.option_;
            }

            private SingleFieldBuilderV3<Option, Builder, OptionOrBuilder> getOptionFieldBuilder() {
                if (this.optionBuilder_ == null) {
                    this.optionBuilder_ = new SingleFieldBuilderV3<>(getOption(), getParentForChildren(), isClean());
                    this.option_ = null;
                }
                return this.optionBuilder_;
            }

            public boolean hasTitle() {
                return (this.bitField0_ & 16) == 16;
            }

            public String getTitle() {
                Object ref = this.title_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.title_ = s;
                return s;
            }

            public ByteString getTitleBytes() {
                Object ref = this.title_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.title_ = b;
                return b;
            }

            public Builder setTitle(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.title_ = value;
                onChanged();
                return this;
            }

            public Builder clearTitle() {
                this.bitField0_ &= -17;
                this.title_ = MsgNotify.getDefaultInstance().getTitle();
                onChanged();
                return this;
            }

            public Builder setTitleBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.title_ = value;
                onChanged();
                return this;
            }

            public boolean hasDetail() {
                return (this.bitField0_ & 32) == 32;
            }

            public String getDetail() {
                Object ref = this.detail_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.detail_ = s;
                return s;
            }

            public ByteString getDetailBytes() {
                Object ref = this.detail_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.detail_ = b;
                return b;
            }

            public Builder setDetail(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.detail_ = value;
                onChanged();
                return this;
            }

            public Builder clearDetail() {
                this.bitField0_ &= -33;
                this.detail_ = MsgNotify.getDefaultInstance().getDetail();
                onChanged();
                return this;
            }

            public Builder setDetailBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.detail_ = value;
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

        public interface OptionOrBuilder extends MessageOrBuilder {
            boolean getAccept();

            boolean getMute();

            boolean getReject();

            boolean hasAccept();

            boolean hasMute();

            boolean hasReject();
        }

        private MsgNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MsgNotify() {
            this.memoizedIsInitialized = -1;
            this.id_ = 0;
            this.type_ = 128;
            this.status_ = 0;
            this.title_ = "";
            this.detail_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgNotify(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.id_ = input.readFixed32();
                            break;
                        case 16:
                            int rawValue = input.readEnum();
                            if (Type.valueOf(rawValue) != null) {
                                this.bitField0_ |= 2;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue);
                                break;
                            }
                        case 24:
                            int rawValue2 = input.readEnum();
                            if (Status.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 4;
                                this.status_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(3, rawValue2);
                                break;
                            }
                        case 34:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.option_.toBuilder();
                            }
                            this.option_ = (Option) input.readMessage(Option.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.option_);
                                this.option_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        case 42:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 16;
                            this.title_ = bs;
                            break;
                        case 50:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 32;
                            this.detail_ = bs2;
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
            return MsgNotifyOuterClass.internal_static_MsgNotify_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgNotify_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgNotify.class, Builder.class);
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getId() {
            return this.id_;
        }

        public boolean hasType() {
            return (this.bitField0_ & 2) == 2;
        }

        public Type getType() {
            Type result = Type.valueOf(this.type_);
            return result == null ? Type.CALL : result;
        }

        public boolean hasStatus() {
            return (this.bitField0_ & 4) == 4;
        }

        public Status getStatus() {
            Status result = Status.valueOf(this.status_);
            return result == null ? Status.ADDED : result;
        }

        public boolean hasOption() {
            return (this.bitField0_ & 8) == 8;
        }

        public Option getOption() {
            return this.option_ == null ? Option.getDefaultInstance() : this.option_;
        }

        public OptionOrBuilder getOptionOrBuilder() {
            return this.option_ == null ? Option.getDefaultInstance() : this.option_;
        }

        public boolean hasTitle() {
            return (this.bitField0_ & 16) == 16;
        }

        public String getTitle() {
            Object ref = this.title_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.title_ = s;
            }
            return s;
        }

        public ByteString getTitleBytes() {
            Object ref = this.title_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.title_ = b;
            return b;
        }

        public boolean hasDetail() {
            return (this.bitField0_ & 32) == 32;
        }

        public String getDetail() {
            Object ref = this.detail_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.detail_ = s;
            }
            return s;
        }

        public ByteString getDetailBytes() {
            Object ref = this.detail_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.detail_ = b;
            return b;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasId()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStatus()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasOption()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasTitle()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDetail()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getOption().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.type_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(3, this.status_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, getOption());
            }
            if ((this.bitField0_ & 16) == 16) {
                GeneratedMessageV3.writeString(output, 5, this.title_);
            }
            if ((this.bitField0_ & 32) == 32) {
                GeneratedMessageV3.writeString(output, 6, this.detail_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.type_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeEnumSize(3, this.status_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeMessageSize(4, getOption());
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += GeneratedMessageV3.computeStringSize(5, this.title_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += GeneratedMessageV3.computeStringSize(6, this.detail_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            boolean result3;
            boolean result4;
            boolean result5;
            boolean result6;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MsgNotify)) {
                return super.equals(obj);
            }
            MsgNotify other = (MsgNotify) obj;
            boolean result7 = 1 != 0 && hasId() == other.hasId();
            if (hasId()) {
                result7 = result7 && getId() == other.getId();
            }
            if (!result7 || hasType() != other.hasType()) {
                result = false;
            } else {
                result = true;
            }
            if (hasType()) {
                if (!result || this.type_ != other.type_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasStatus() != other.hasStatus()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasStatus()) {
                if (!result2 || this.status_ != other.status_) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasOption() != other.hasOption()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasOption()) {
                if (!result3 || !getOption().equals(other.getOption())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasTitle() != other.hasTitle()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasTitle()) {
                if (!result4 || !getTitle().equals(other.getTitle())) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasDetail() != other.hasDetail()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasDetail()) {
                if (!result5 || !getDetail().equals(other.getDetail())) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || !this.unknownFields.equals(other.unknownFields)) {
                result6 = false;
            } else {
                result6 = true;
            }
            return result6;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasId()) {
                hash = (((hash * 37) + 1) * 53) + getId();
            }
            if (hasType()) {
                hash = (((hash * 37) + 2) * 53) + this.type_;
            }
            if (hasStatus()) {
                hash = (((hash * 37) + 3) * 53) + this.status_;
            }
            if (hasOption()) {
                hash = (((hash * 37) + 4) * 53) + getOption().hashCode();
            }
            if (hasTitle()) {
                hash = (((hash * 37) + 5) * 53) + getTitle().hashCode();
            }
            if (hasDetail()) {
                hash = (((hash * 37) + 6) * 53) + getDetail().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgNotify parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgNotify) PARSER.parseFrom(data);
        }

        public static MsgNotify parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgNotify) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgNotify parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgNotify) PARSER.parseFrom(data);
        }

        public static MsgNotify parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgNotify) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgNotify parseFrom(InputStream input) throws IOException {
            return (MsgNotify) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgNotify parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgNotify) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgNotify parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgNotify) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgNotify parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgNotify) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgNotify parseFrom(CodedInputStream input) throws IOException {
            return (MsgNotify) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgNotify parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgNotify) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgNotify prototype) {
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

        public static MsgNotify getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgNotify> parser() {
            return PARSER;
        }

        public Parser<MsgNotify> getParserForType() {
            return PARSER;
        }

        public MsgNotify getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MsgOperation extends GeneratedMessageV3 implements MsgOperationOrBuilder {
        private static final MsgOperation DEFAULT_INSTANCE = new MsgOperation();
        public static final int ID_FIELD_NUMBER = 1;
        public static final int OPTION_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<MsgOperation> PARSER = new AbstractParser<MsgOperation>() {
            public MsgOperation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgOperation(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int id_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int option_;

        public enum Option implements ProtocolMessageEnum {
            ACCEPT(0),
            REJECT(1),
            MUTE(2);
            
            public static final int ACCEPT_VALUE = 0;
            public static final int MUTE_VALUE = 2;
            public static final int REJECT_VALUE = 1;
            private static final Option[] VALUES = null;
            private static final EnumLiteMap<Option> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Option>() {
                    public Option findValueByNumber(int number) {
                        return Option.forNumber(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Option valueOf(int value2) {
                return forNumber(value2);
            }

            public static Option forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return ACCEPT;
                    case 1:
                        return REJECT;
                    case 2:
                        return MUTE;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Option> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) MsgOperation.getDescriptor().getEnumTypes().get(0);
            }

            public static Option valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private Option(int value2) {
                this.value = value2;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgOperationOrBuilder {
            private int bitField0_;
            private int id_;
            private int option_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgOperation_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgOperation_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgOperation.class, Builder.class);
            }

            private Builder() {
                this.option_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.option_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MsgOperation.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.id_ = 0;
                this.bitField0_ &= -2;
                this.option_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgOperation_descriptor;
            }

            public MsgOperation getDefaultInstanceForType() {
                return MsgOperation.getDefaultInstance();
            }

            public MsgOperation build() {
                MsgOperation result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgOperation buildPartial() {
                MsgOperation result = new MsgOperation((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.id_ = this.id_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.option_ = this.option_;
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
                if (other instanceof MsgOperation) {
                    return mergeFrom((MsgOperation) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgOperation other) {
                if (other != MsgOperation.getDefaultInstance()) {
                    if (other.hasId()) {
                        setId(other.getId());
                    }
                    if (other.hasOption()) {
                        setOption(other.getOption());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasId() && hasOption()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgOperation parsedMessage = null;
                try {
                    MsgOperation parsedMessage2 = (MsgOperation) MsgOperation.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgOperation parsedMessage3 = (MsgOperation) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getId() {
                return this.id_;
            }

            public Builder setId(int value) {
                this.bitField0_ |= 1;
                this.id_ = value;
                onChanged();
                return this;
            }

            public Builder clearId() {
                this.bitField0_ &= -2;
                this.id_ = 0;
                onChanged();
                return this;
            }

            public boolean hasOption() {
                return (this.bitField0_ & 2) == 2;
            }

            public Option getOption() {
                Option result = Option.valueOf(this.option_);
                return result == null ? Option.ACCEPT : result;
            }

            public Builder setOption(Option value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.option_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearOption() {
                this.bitField0_ &= -3;
                this.option_ = 0;
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

        private MsgOperation(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MsgOperation() {
            this.memoizedIsInitialized = -1;
            this.id_ = 0;
            this.option_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgOperation(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.id_ = input.readFixed32();
                            break;
                        case 16:
                            int rawValue = input.readEnum();
                            if (Option.valueOf(rawValue) != null) {
                                this.bitField0_ |= 2;
                                this.option_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue);
                                break;
                            }
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
            return MsgNotifyOuterClass.internal_static_MsgOperation_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgOperation_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgOperation.class, Builder.class);
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getId() {
            return this.id_;
        }

        public boolean hasOption() {
            return (this.bitField0_ & 2) == 2;
        }

        public Option getOption() {
            Option result = Option.valueOf(this.option_);
            return result == null ? Option.ACCEPT : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasId()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasOption()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.option_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.option_);
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
            if (!(obj instanceof MsgOperation)) {
                return super.equals(obj);
            }
            MsgOperation other = (MsgOperation) obj;
            boolean result3 = 1 != 0 && hasId() == other.hasId();
            if (hasId()) {
                result3 = result3 && getId() == other.getId();
            }
            if (!result3 || hasOption() != other.hasOption()) {
                result = false;
            } else {
                result = true;
            }
            if (hasOption()) {
                if (!result || this.option_ != other.option_) {
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
            if (hasId()) {
                hash = (((hash * 37) + 1) * 53) + getId();
            }
            if (hasOption()) {
                hash = (((hash * 37) + 2) * 53) + this.option_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgOperation parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgOperation) PARSER.parseFrom(data);
        }

        public static MsgOperation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgOperation) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgOperation parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgOperation) PARSER.parseFrom(data);
        }

        public static MsgOperation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgOperation) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgOperation parseFrom(InputStream input) throws IOException {
            return (MsgOperation) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgOperation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgOperation) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgOperation parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgOperation) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgOperation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgOperation) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgOperation parseFrom(CodedInputStream input) throws IOException {
            return (MsgOperation) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgOperation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgOperation) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgOperation prototype) {
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

        public static MsgOperation getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgOperation> parser() {
            return PARSER;
        }

        public Parser<MsgOperation> getParserForType() {
            return PARSER;
        }

        public MsgOperation getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MsgRequest extends GeneratedMessageV3 implements MsgRequestOrBuilder {
        private static final MsgRequest DEFAULT_INSTANCE = new MsgRequest();
        public static final int FILTER_HASH_FIELD_NUMBER = 5;
        public static final int FILTER_ID_COUNT_FIELD_NUMBER = 6;
        public static final int HANDLER_HASH_FIELD_NUMBER = 4;
        public static final int NOTIFY_DETAIL_LEN_FIELD_NUMBER = 8;
        public static final int NOTIFY_TITLE_LEN_FIELD_NUMBER = 7;
        @Deprecated
        public static final Parser<MsgRequest> PARSER = new AbstractParser<MsgRequest>() {
            public MsgRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgRequest(input, extensionRegistry);
            }
        };
        public static final int SUPPORT_FILTER_FIELD_NUMBER = 2;
        public static final int SUPPORT_HANDLER_FIELD_NUMBER = 1;
        public static final int SUPPORT_NOTIFY_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int filterHash_;
        /* access modifiers changed from: private */
        public int filterIdCount_;
        /* access modifiers changed from: private */
        public int handlerHash_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int notifyDetailLen_;
        /* access modifiers changed from: private */
        public int notifyTitleLen_;
        /* access modifiers changed from: private */
        public boolean supportFilter_;
        /* access modifiers changed from: private */
        public boolean supportHandler_;
        /* access modifiers changed from: private */
        public boolean supportNotify_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgRequestOrBuilder {
            private int bitField0_;
            private int filterHash_;
            private int filterIdCount_;
            private int handlerHash_;
            private int notifyDetailLen_;
            private int notifyTitleLen_;
            private boolean supportFilter_;
            private boolean supportHandler_;
            private boolean supportNotify_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgRequest.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (MsgRequest.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.supportHandler_ = false;
                this.bitField0_ &= -2;
                this.supportFilter_ = false;
                this.bitField0_ &= -3;
                this.supportNotify_ = false;
                this.bitField0_ &= -5;
                this.handlerHash_ = 0;
                this.bitField0_ &= -9;
                this.filterHash_ = 0;
                this.bitField0_ &= -17;
                this.filterIdCount_ = 0;
                this.bitField0_ &= -33;
                this.notifyTitleLen_ = 0;
                this.bitField0_ &= -65;
                this.notifyDetailLen_ = 0;
                this.bitField0_ &= -129;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgRequest_descriptor;
            }

            public MsgRequest getDefaultInstanceForType() {
                return MsgRequest.getDefaultInstance();
            }

            public MsgRequest build() {
                MsgRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgRequest buildPartial() {
                MsgRequest result = new MsgRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.supportHandler_ = this.supportHandler_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.supportFilter_ = this.supportFilter_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.supportNotify_ = this.supportNotify_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.handlerHash_ = this.handlerHash_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.filterHash_ = this.filterHash_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.filterIdCount_ = this.filterIdCount_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.notifyTitleLen_ = this.notifyTitleLen_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.notifyDetailLen_ = this.notifyDetailLen_;
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
                if (other instanceof MsgRequest) {
                    return mergeFrom((MsgRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgRequest other) {
                if (other != MsgRequest.getDefaultInstance()) {
                    if (other.hasSupportHandler()) {
                        setSupportHandler(other.getSupportHandler());
                    }
                    if (other.hasSupportFilter()) {
                        setSupportFilter(other.getSupportFilter());
                    }
                    if (other.hasSupportNotify()) {
                        setSupportNotify(other.getSupportNotify());
                    }
                    if (other.hasHandlerHash()) {
                        setHandlerHash(other.getHandlerHash());
                    }
                    if (other.hasFilterHash()) {
                        setFilterHash(other.getFilterHash());
                    }
                    if (other.hasFilterIdCount()) {
                        setFilterIdCount(other.getFilterIdCount());
                    }
                    if (other.hasNotifyTitleLen()) {
                        setNotifyTitleLen(other.getNotifyTitleLen());
                    }
                    if (other.hasNotifyDetailLen()) {
                        setNotifyDetailLen(other.getNotifyDetailLen());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSupportHandler() && hasSupportFilter() && hasSupportNotify()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgRequest parsedMessage = null;
                try {
                    MsgRequest parsedMessage2 = (MsgRequest) MsgRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgRequest parsedMessage3 = (MsgRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSupportHandler() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getSupportHandler() {
                return this.supportHandler_;
            }

            public Builder setSupportHandler(boolean value) {
                this.bitField0_ |= 1;
                this.supportHandler_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportHandler() {
                this.bitField0_ &= -2;
                this.supportHandler_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportFilter() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getSupportFilter() {
                return this.supportFilter_;
            }

            public Builder setSupportFilter(boolean value) {
                this.bitField0_ |= 2;
                this.supportFilter_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportFilter() {
                this.bitField0_ &= -3;
                this.supportFilter_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportNotify() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getSupportNotify() {
                return this.supportNotify_;
            }

            public Builder setSupportNotify(boolean value) {
                this.bitField0_ |= 4;
                this.supportNotify_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportNotify() {
                this.bitField0_ &= -5;
                this.supportNotify_ = false;
                onChanged();
                return this;
            }

            public boolean hasHandlerHash() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getHandlerHash() {
                return this.handlerHash_;
            }

            public Builder setHandlerHash(int value) {
                this.bitField0_ |= 8;
                this.handlerHash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHandlerHash() {
                this.bitField0_ &= -9;
                this.handlerHash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFilterHash() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getFilterHash() {
                return this.filterHash_;
            }

            public Builder setFilterHash(int value) {
                this.bitField0_ |= 16;
                this.filterHash_ = value;
                onChanged();
                return this;
            }

            public Builder clearFilterHash() {
                this.bitField0_ &= -17;
                this.filterHash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFilterIdCount() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getFilterIdCount() {
                return this.filterIdCount_;
            }

            public Builder setFilterIdCount(int value) {
                this.bitField0_ |= 32;
                this.filterIdCount_ = value;
                onChanged();
                return this;
            }

            public Builder clearFilterIdCount() {
                this.bitField0_ &= -33;
                this.filterIdCount_ = 0;
                onChanged();
                return this;
            }

            public boolean hasNotifyTitleLen() {
                return (this.bitField0_ & 64) == 64;
            }

            public int getNotifyTitleLen() {
                return this.notifyTitleLen_;
            }

            public Builder setNotifyTitleLen(int value) {
                this.bitField0_ |= 64;
                this.notifyTitleLen_ = value;
                onChanged();
                return this;
            }

            public Builder clearNotifyTitleLen() {
                this.bitField0_ &= -65;
                this.notifyTitleLen_ = 0;
                onChanged();
                return this;
            }

            public boolean hasNotifyDetailLen() {
                return (this.bitField0_ & 128) == 128;
            }

            public int getNotifyDetailLen() {
                return this.notifyDetailLen_;
            }

            public Builder setNotifyDetailLen(int value) {
                this.bitField0_ |= 128;
                this.notifyDetailLen_ = value;
                onChanged();
                return this;
            }

            public Builder clearNotifyDetailLen() {
                this.bitField0_ &= -129;
                this.notifyDetailLen_ = 0;
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

        private MsgRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private MsgRequest() {
            this.memoizedIsInitialized = -1;
            this.supportHandler_ = false;
            this.supportFilter_ = false;
            this.supportNotify_ = false;
            this.handlerHash_ = 0;
            this.filterHash_ = 0;
            this.filterIdCount_ = 0;
            this.notifyTitleLen_ = 0;
            this.notifyDetailLen_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 1;
                            this.supportHandler_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.supportFilter_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.supportNotify_ = input.readBool();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.handlerHash_ = input.readFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.filterHash_ = input.readFixed32();
                            break;
                        case 53:
                            this.bitField0_ |= 32;
                            this.filterIdCount_ = input.readFixed32();
                            break;
                        case 61:
                            this.bitField0_ |= 64;
                            this.notifyTitleLen_ = input.readFixed32();
                            break;
                        case 69:
                            this.bitField0_ |= 128;
                            this.notifyDetailLen_ = input.readFixed32();
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
            return MsgNotifyOuterClass.internal_static_MsgRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgRequest.class, Builder.class);
        }

        public boolean hasSupportHandler() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getSupportHandler() {
            return this.supportHandler_;
        }

        public boolean hasSupportFilter() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getSupportFilter() {
            return this.supportFilter_;
        }

        public boolean hasSupportNotify() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getSupportNotify() {
            return this.supportNotify_;
        }

        public boolean hasHandlerHash() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getHandlerHash() {
            return this.handlerHash_;
        }

        public boolean hasFilterHash() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getFilterHash() {
            return this.filterHash_;
        }

        public boolean hasFilterIdCount() {
            return (this.bitField0_ & 32) == 32;
        }

        public int getFilterIdCount() {
            return this.filterIdCount_;
        }

        public boolean hasNotifyTitleLen() {
            return (this.bitField0_ & 64) == 64;
        }

        public int getNotifyTitleLen() {
            return this.notifyTitleLen_;
        }

        public boolean hasNotifyDetailLen() {
            return (this.bitField0_ & 128) == 128;
        }

        public int getNotifyDetailLen() {
            return this.notifyDetailLen_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSupportHandler()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportFilter()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportNotify()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.supportHandler_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.supportFilter_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.supportNotify_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.handlerHash_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.filterHash_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeFixed32(6, this.filterIdCount_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeFixed32(7, this.notifyTitleLen_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeFixed32(8, this.notifyDetailLen_);
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.supportHandler_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.supportFilter_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.supportNotify_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.handlerHash_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.filterHash_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeFixed32Size(6, this.filterIdCount_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeFixed32Size(7, this.notifyTitleLen_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeFixed32Size(8, this.notifyDetailLen_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            boolean result3;
            boolean result4;
            boolean result5;
            boolean result6;
            boolean result7;
            boolean result8;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MsgRequest)) {
                return super.equals(obj);
            }
            MsgRequest other = (MsgRequest) obj;
            boolean result9 = 1 != 0 && hasSupportHandler() == other.hasSupportHandler();
            if (hasSupportHandler()) {
                result9 = result9 && getSupportHandler() == other.getSupportHandler();
            }
            if (!result9 || hasSupportFilter() != other.hasSupportFilter()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSupportFilter()) {
                if (!result || getSupportFilter() != other.getSupportFilter()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasSupportNotify() != other.hasSupportNotify()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSupportNotify()) {
                if (!result2 || getSupportNotify() != other.getSupportNotify()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasHandlerHash() != other.hasHandlerHash()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasHandlerHash()) {
                if (!result3 || getHandlerHash() != other.getHandlerHash()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasFilterHash() != other.hasFilterHash()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasFilterHash()) {
                if (!result4 || getFilterHash() != other.getFilterHash()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasFilterIdCount() != other.hasFilterIdCount()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasFilterIdCount()) {
                if (!result5 || getFilterIdCount() != other.getFilterIdCount()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasNotifyTitleLen() != other.hasNotifyTitleLen()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasNotifyTitleLen()) {
                if (!result6 || getNotifyTitleLen() != other.getNotifyTitleLen()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasNotifyDetailLen() != other.hasNotifyDetailLen()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasNotifyDetailLen()) {
                if (!result7 || getNotifyDetailLen() != other.getNotifyDetailLen()) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || !this.unknownFields.equals(other.unknownFields)) {
                result8 = false;
            } else {
                result8 = true;
            }
            return result8;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasSupportHandler()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getSupportHandler());
            }
            if (hasSupportFilter()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getSupportFilter());
            }
            if (hasSupportNotify()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getSupportNotify());
            }
            if (hasHandlerHash()) {
                hash = (((hash * 37) + 4) * 53) + getHandlerHash();
            }
            if (hasFilterHash()) {
                hash = (((hash * 37) + 5) * 53) + getFilterHash();
            }
            if (hasFilterIdCount()) {
                hash = (((hash * 37) + 6) * 53) + getFilterIdCount();
            }
            if (hasNotifyTitleLen()) {
                hash = (((hash * 37) + 7) * 53) + getNotifyTitleLen();
            }
            if (hasNotifyDetailLen()) {
                hash = (((hash * 37) + 8) * 53) + getNotifyDetailLen();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgRequest) PARSER.parseFrom(data);
        }

        public static MsgRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgRequest) PARSER.parseFrom(data);
        }

        public static MsgRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgRequest parseFrom(InputStream input) throws IOException {
            return (MsgRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgRequest parseFrom(CodedInputStream input) throws IOException {
            return (MsgRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgRequest prototype) {
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

        public static MsgRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgRequest> parser() {
            return PARSER;
        }

        public Parser<MsgRequest> getParserForType() {
            return PARSER;
        }

        public MsgRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class MsgSubscriber extends GeneratedMessageV3 implements MsgSubscriberOrBuilder {
        private static final MsgSubscriber DEFAULT_INSTANCE = new MsgSubscriber();
        public static final int OPERATION_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<MsgSubscriber> PARSER = new AbstractParser<MsgSubscriber>() {
            public MsgSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MsgSubscriber(input, extensionRegistry);
            }
        };
        public static final int REQUEST_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;

        public enum DataCase implements EnumLite {
            REQUEST(1),
            OPERATION(2),
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
                    case 1:
                        return REQUEST;
                    case 2:
                        return OPERATION;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MsgSubscriberOrBuilder {
            private int bitField0_;
            private int dataCase_;
            private Object data_;
            private SingleFieldBuilderV3<MsgOperation, Builder, MsgOperationOrBuilder> operationBuilder_;
            private SingleFieldBuilderV3<MsgRequest, Builder, MsgRequestOrBuilder> requestBuilder_;

            public static final Descriptor getDescriptor() {
                return MsgNotifyOuterClass.internal_static_MsgSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return MsgNotifyOuterClass.internal_static_MsgSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgSubscriber.class, Builder.class);
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
                if (MsgSubscriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return MsgNotifyOuterClass.internal_static_MsgSubscriber_descriptor;
            }

            public MsgSubscriber getDefaultInstanceForType() {
                return MsgSubscriber.getDefaultInstance();
            }

            public MsgSubscriber build() {
                MsgSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public MsgSubscriber buildPartial() {
                MsgSubscriber result = new MsgSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if (this.dataCase_ == 1) {
                    if (this.requestBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.requestBuilder_.build();
                    }
                }
                if (this.dataCase_ == 2) {
                    if (this.operationBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.operationBuilder_.build();
                    }
                }
                result.bitField0_ = 0;
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
                if (other instanceof MsgSubscriber) {
                    return mergeFrom((MsgSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MsgSubscriber other) {
                if (other != MsgSubscriber.getDefaultInstance()) {
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgSubscriber$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeRequest(other.getRequest());
                            break;
                        case 2:
                            mergeOperation(other.getOperation());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasRequest() && !getRequest().isInitialized()) {
                    return false;
                }
                if (!hasOperation() || getOperation().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MsgSubscriber parsedMessage = null;
                try {
                    MsgSubscriber parsedMessage2 = (MsgSubscriber) MsgSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MsgSubscriber parsedMessage3 = (MsgSubscriber) e.getUnfinishedMessage();
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

            public boolean hasRequest() {
                return this.dataCase_ == 1;
            }

            public MsgRequest getRequest() {
                if (this.requestBuilder_ == null) {
                    if (this.dataCase_ == 1) {
                        return (MsgRequest) this.data_;
                    }
                    return MsgRequest.getDefaultInstance();
                } else if (this.dataCase_ == 1) {
                    return (MsgRequest) this.requestBuilder_.getMessage();
                } else {
                    return MsgRequest.getDefaultInstance();
                }
            }

            public Builder setRequest(MsgRequest value) {
                if (this.requestBuilder_ != null) {
                    this.requestBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 1;
                return this;
            }

            public Builder setRequest(Builder builderForValue) {
                if (this.requestBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.requestBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 1;
                return this;
            }

            public Builder mergeRequest(MsgRequest value) {
                if (this.requestBuilder_ == null) {
                    if (this.dataCase_ != 1 || this.data_ == MsgRequest.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = MsgRequest.newBuilder((MsgRequest) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 1) {
                        this.requestBuilder_.mergeFrom(value);
                    }
                    this.requestBuilder_.setMessage(value);
                }
                this.dataCase_ = 1;
                return this;
            }

            public Builder clearRequest() {
                if (this.requestBuilder_ != null) {
                    if (this.dataCase_ == 1) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.requestBuilder_.clear();
                } else if (this.dataCase_ == 1) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getRequestBuilder() {
                return (Builder) getRequestFieldBuilder().getBuilder();
            }

            public MsgRequestOrBuilder getRequestOrBuilder() {
                if (this.dataCase_ == 1 && this.requestBuilder_ != null) {
                    return (MsgRequestOrBuilder) this.requestBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 1) {
                    return (MsgRequest) this.data_;
                }
                return MsgRequest.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MsgRequest, Builder, MsgRequestOrBuilder> getRequestFieldBuilder() {
                if (this.requestBuilder_ == null) {
                    if (this.dataCase_ != 1) {
                        this.data_ = MsgRequest.getDefaultInstance();
                    }
                    this.requestBuilder_ = new SingleFieldBuilderV3<>((MsgRequest) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 1;
                onChanged();
                return this.requestBuilder_;
            }

            public boolean hasOperation() {
                return this.dataCase_ == 2;
            }

            public MsgOperation getOperation() {
                if (this.operationBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (MsgOperation) this.data_;
                    }
                    return MsgOperation.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (MsgOperation) this.operationBuilder_.getMessage();
                } else {
                    return MsgOperation.getDefaultInstance();
                }
            }

            public Builder setOperation(MsgOperation value) {
                if (this.operationBuilder_ != null) {
                    this.operationBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setOperation(Builder builderForValue) {
                if (this.operationBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.operationBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeOperation(MsgOperation value) {
                if (this.operationBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == MsgOperation.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = MsgOperation.newBuilder((MsgOperation) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.operationBuilder_.mergeFrom(value);
                    }
                    this.operationBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearOperation() {
                if (this.operationBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.operationBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getOperationBuilder() {
                return (Builder) getOperationFieldBuilder().getBuilder();
            }

            public MsgOperationOrBuilder getOperationOrBuilder() {
                if (this.dataCase_ == 2 && this.operationBuilder_ != null) {
                    return (MsgOperationOrBuilder) this.operationBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (MsgOperation) this.data_;
                }
                return MsgOperation.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MsgOperation, Builder, MsgOperationOrBuilder> getOperationFieldBuilder() {
                if (this.operationBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = MsgOperation.getDefaultInstance();
                    }
                    this.operationBuilder_ = new SingleFieldBuilderV3<>((MsgOperation) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.operationBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private MsgSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private MsgSubscriber() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MsgSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 10:
                            Builder subBuilder = null;
                            if (this.dataCase_ == 1) {
                                subBuilder = ((MsgRequest) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(MsgRequest.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((MsgRequest) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 2) {
                                subBuilder2 = ((MsgOperation) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(MsgOperation.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((MsgOperation) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 2;
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
            return MsgNotifyOuterClass.internal_static_MsgSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return MsgNotifyOuterClass.internal_static_MsgSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(MsgSubscriber.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasRequest() {
            return this.dataCase_ == 1;
        }

        public MsgRequest getRequest() {
            if (this.dataCase_ == 1) {
                return (MsgRequest) this.data_;
            }
            return MsgRequest.getDefaultInstance();
        }

        public MsgRequestOrBuilder getRequestOrBuilder() {
            if (this.dataCase_ == 1) {
                return (MsgRequest) this.data_;
            }
            return MsgRequest.getDefaultInstance();
        }

        public boolean hasOperation() {
            return this.dataCase_ == 2;
        }

        public MsgOperation getOperation() {
            if (this.dataCase_ == 2) {
                return (MsgOperation) this.data_;
            }
            return MsgOperation.getDefaultInstance();
        }

        public MsgOperationOrBuilder getOperationOrBuilder() {
            if (this.dataCase_ == 2) {
                return (MsgOperation) this.data_;
            }
            return MsgOperation.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasRequest() && !getRequest().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasOperation() || getOperation().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.dataCase_ == 1) {
                output.writeMessage(1, (MsgRequest) this.data_);
            }
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (MsgOperation) this.data_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if (this.dataCase_ == 1) {
                size2 = 0 + CodedOutputStream.computeMessageSize(1, (MsgRequest) this.data_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (MsgOperation) this.data_);
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
            if (!(obj instanceof MsgSubscriber)) {
                return super.equals(obj);
            }
            MsgSubscriber other = (MsgSubscriber) obj;
            if (1 == 0 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 1:
                    if (result && getRequest().equals(other.getRequest())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 2:
                    if (result && getOperation().equals(other.getOperation())) {
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
            switch (this.dataCase_) {
                case 1:
                    hash = (((hash * 37) + 1) * 53) + getRequest().hashCode();
                    break;
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getOperation().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static MsgSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MsgSubscriber) PARSER.parseFrom(data);
        }

        public static MsgSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MsgSubscriber) PARSER.parseFrom(data);
        }

        public static MsgSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MsgSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MsgSubscriber parseFrom(InputStream input) throws IOException {
            return (MsgSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (MsgSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MsgSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MsgSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (MsgSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MsgSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MsgSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MsgSubscriber prototype) {
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

        public static MsgSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MsgSubscriber> parser() {
            return PARSER;
        }

        public Parser<MsgSubscriber> getParserForType() {
            return PARSER;
        }

        public MsgSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.MsgNotifyOuterClass$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgNotification$ParamsCase = new int[ParamsCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgSubscriber$DataCase = new int[DataCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgSubscriber$DataCase[DataCase.REQUEST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgSubscriber$DataCase[DataCase.OPERATION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgSubscriber$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgNotification$ParamsCase[ParamsCase.HANDLER.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgNotification$ParamsCase[ParamsCase.FILTER.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgNotification$ParamsCase[ParamsCase.NOTIFY.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$MsgNotifyOuterClass$MsgNotification$ParamsCase[ParamsCase.PARAMS_NOT_SET.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public interface MsgFilterOrBuilder extends MessageOrBuilder {
        int getHash();

        ID getId(int i);

        int getIdCount();

        List<ID> getIdList();

        IDOrBuilder getIdOrBuilder(int i);

        List<? extends IDOrBuilder> getIdOrBuilderList();

        boolean hasHash();
    }

    public interface MsgHandlerOrBuilder extends MessageOrBuilder {
        int getHash();

        Policy getPolicy();

        Timing getTiming();

        TimingOrBuilder getTimingOrBuilder();

        boolean hasHash();

        boolean hasPolicy();

        boolean hasTiming();
    }

    public interface MsgNotificationOrBuilder extends MessageOrBuilder {
        MsgFilter getFilter();

        MsgFilterOrBuilder getFilterOrBuilder();

        MsgHandler getHandler();

        MsgHandlerOrBuilder getHandlerOrBuilder();

        MsgNotify getNotify();

        MsgNotifyOrBuilder getNotifyOrBuilder();

        ParamsCase getParamsCase();

        boolean hasFilter();

        boolean hasHandler();

        boolean hasNotify();
    }

    public interface MsgNotifyOrBuilder extends MessageOrBuilder {
        String getDetail();

        ByteString getDetailBytes();

        int getId();

        Option getOption();

        OptionOrBuilder getOptionOrBuilder();

        Status getStatus();

        String getTitle();

        ByteString getTitleBytes();

        Type getType();

        boolean hasDetail();

        boolean hasId();

        boolean hasOption();

        boolean hasStatus();

        boolean hasTitle();

        boolean hasType();
    }

    public interface MsgOperationOrBuilder extends MessageOrBuilder {
        int getId();

        Option getOption();

        boolean hasId();

        boolean hasOption();
    }

    public interface MsgRequestOrBuilder extends MessageOrBuilder {
        int getFilterHash();

        int getFilterIdCount();

        int getHandlerHash();

        int getNotifyDetailLen();

        int getNotifyTitleLen();

        boolean getSupportFilter();

        boolean getSupportHandler();

        boolean getSupportNotify();

        boolean hasFilterHash();

        boolean hasFilterIdCount();

        boolean hasHandlerHash();

        boolean hasNotifyDetailLen();

        boolean hasNotifyTitleLen();

        boolean hasSupportFilter();

        boolean hasSupportHandler();

        boolean hasSupportNotify();
    }

    public interface MsgSubscriberOrBuilder extends MessageOrBuilder {
        DataCase getDataCase();

        MsgOperation getOperation();

        MsgOperationOrBuilder getOperationOrBuilder();

        MsgRequest getRequest();

        MsgRequestOrBuilder getRequestOrBuilder();

        boolean hasOperation();

        boolean hasRequest();
    }

    private MsgNotifyOuterClass() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0010msg_notify.proto\"\u0002\n\nMsgHandler\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\"\n\u0006policy\u0018\u0002 \u0002(\u000e2\u0012.MsgHandler.Policy\u0012\"\n\u0006timing\u0018\u0003 \u0002(\u000b2\u0012.MsgHandler.Timing\u001aX\n\u0006Timing\u0012\u0012\n\nstart_hour\u0018\u0001 \u0002(\u0007\u0012\u0014\n\fstart_minute\u0018\u0002 \u0002(\u0007\u0012\u0010\n\bend_hour\u0018\u0003 \u0002(\u0007\u0012\u0012\n\nend_minute\u0018\u0004 \u0002(\u0007\"K\n\u0006Policy\u0012\u0014\n\u0010STORE_AND_PROMPT\u0010\u0000\u0012\u0013\n\u000fSTORE_NO_PROMPT\u0010\u0001\u0012\u0016\n\u0012NO_STORE_NO_PROMPT\u0010\u0002\"F\n\tMsgFilter\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0019\n\u0002id\u0018\u0002 \u0003(\u000b2\r.MsgFilter.ID\u001a\u0010\n\u0002ID\u0012\n\n\u0002id\u0018\u0001 \u0002(\t\"\u0002\n\tMsgNotify\u0012\n\n\u0002id\u0018\u0001 \u0002(\u0007\u0012\u001d\n\u0004type\u0018\u0002 \u0002(\u000e2\u000f", ".MsgNotify.Type\u0012!\n\u0006status\u0018\u0003 \u0002(\u000e2\u0011.MsgNotify.Status\u0012!\n\u0006option\u0018\u0004 \u0002(\u000b2\u0011.MsgNotify.Option\u0012\r\n\u0005title\u0018\u0005 \u0002(\t\u0012\u000e\n\u0006detail\u0018\u0006 \u0002(\t\u001a6\n\u0006Option\u0012\u000e\n\u0006accept\u0018\u0001 \u0002(\b\u0012\u000e\n\u0006reject\u0018\u0002 \u0002(\b\u0012\f\n\u0004mute\u0018\u0003 \u0002(\b\"\u001b\n\u0004Type\u0012\t\n\u0004CALL\u0010\u0001\u0012\b\n\u0003SMS\u0010\u0001\"-\n\u0006Status\u0012\t\n\u0005ADDED\u0010\u0000\u0012\u000b\n\u0007REMOVED\u0010\u0001\u0012\u000b\n\u0007UPDATED\u0010\u0002\"w\n\u000fMsgNotification\u0012\u001e\n\u0007handler\u0018\u0001 \u0001(\u000b2\u000b.MsgHandlerH\u0000\u0012\u001c\n\u0006filter\u0018\u0002 \u0001(\u000b2\n.MsgFilterH\u0000\u0012\u001c\n\u0006notify\u0018\u0003 \u0001(\u000b2\n.MsgNotifyH\u0000B\b\n\u0006params\"Î\u0001\n\nMsgRequest\u0012\u0017\n\u000fsupport_hand", "ler\u0018\u0001 \u0002(\b\u0012\u0016\n\u000esupport_filter\u0018\u0002 \u0002(\b\u0012\u0016\n\u000esupport_notify\u0018\u0003 \u0002(\b\u0012\u0014\n\fhandler_hash\u0018\u0004 \u0001(\u0007\u0012\u0013\n\u000bfilter_hash\u0018\u0005 \u0001(\u0007\u0012\u0017\n\u000ffilter_id_count\u0018\u0006 \u0001(\u0007\u0012\u0018\n\u0010notify_title_len\u0018\u0007 \u0001(\u0007\u0012\u0019\n\u0011notify_detail_len\u0018\b \u0001(\u0007\"l\n\fMsgOperation\u0012\n\n\u0002id\u0018\u0001 \u0002(\u0007\u0012$\n\u0006option\u0018\u0002 \u0002(\u000e2\u0014.MsgOperation.Option\"*\n\u0006Option\u0012\n\n\u0006ACCEPT\u0010\u0000\u0012\n\n\u0006REJECT\u0010\u0001\u0012\b\n\u0004MUTE\u0010\u0002\"[\n\rMsgSubscriber\u0012\u001e\n\u0007request\u0018\u0001 \u0001(\u000b2\u000b.MsgRequestH\u0000\u0012\"\n\toperation\u0018\u0002 \u0001(\u000b2\r.MsgOperationH\u0000B\u0006\n\u0004data"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                MsgNotifyOuterClass.descriptor = root;
                return null;
            }
        });
    }
}
