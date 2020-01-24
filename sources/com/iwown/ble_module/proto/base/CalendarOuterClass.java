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
import com.iwown.ble_module.proto.base.RealtimeData.RtTime;
import com.iwown.ble_module.proto.base.RealtimeData.RtTimeOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CalendarOuterClass {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_CalendarConfirm_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_CalendarConfirm_fieldAccessorTable = new FieldAccessorTable(internal_static_CalendarConfirm_descriptor, new String[]{"Operation", "Ret"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_CalendarGroup_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_CalendarGroup_fieldAccessorTable = new FieldAccessorTable(internal_static_CalendarGroup_descriptor, new String[]{"Hash", "Calendar"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_CalendarNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_CalendarNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_CalendarNotification_descriptor, new String[]{"Operation", "Group"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_CalendarSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_CalendarSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_CalendarSubscriber_descriptor, new String[]{"Hash", "MaxCount", "Confirm"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_Calendar_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_Calendar_fieldAccessorTable = new FieldAccessorTable(internal_static_Calendar_descriptor, new String[]{"Time", "Text"});

    public static final class Calendar extends GeneratedMessageV3 implements CalendarOrBuilder {
        private static final Calendar DEFAULT_INSTANCE = new Calendar();
        @Deprecated
        public static final Parser<Calendar> PARSER = new AbstractParser<Calendar>() {
            public Calendar parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Calendar(input, extensionRegistry);
            }
        };
        public static final int TEXT_FIELD_NUMBER = 2;
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public volatile Object text_;
        /* access modifiers changed from: private */
        public RtTime time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CalendarOrBuilder {
            private int bitField0_;
            private Object text_;
            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> timeBuilder_;
            private RtTime time_;

            public static final Descriptor getDescriptor() {
                return CalendarOuterClass.internal_static_Calendar_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return CalendarOuterClass.internal_static_Calendar_fieldAccessorTable.ensureFieldAccessorsInitialized(Calendar.class, Builder.class);
            }

            private Builder() {
                this.time_ = null;
                this.text_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = null;
                this.text_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (Calendar.alwaysUseFieldBuilders) {
                    getTimeFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.timeBuilder_ == null) {
                    this.time_ = null;
                } else {
                    this.timeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.text_ = "";
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return CalendarOuterClass.internal_static_Calendar_descriptor;
            }

            public Calendar getDefaultInstanceForType() {
                return Calendar.getDefaultInstance();
            }

            public Calendar build() {
                Calendar result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public Calendar buildPartial() {
                Calendar result = new Calendar((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeBuilder_ == null) {
                    result.time_ = this.time_;
                } else {
                    result.time_ = (RtTime) this.timeBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.text_ = this.text_;
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
                if (other instanceof Calendar) {
                    return mergeFrom((Calendar) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Calendar other) {
                if (other != Calendar.getDefaultInstance()) {
                    if (other.hasTime()) {
                        mergeTime(other.getTime());
                    }
                    if (other.hasText()) {
                        this.bitField0_ |= 2;
                        this.text_ = other.text_;
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasTime() && getTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Calendar parsedMessage = null;
                try {
                    Calendar parsedMessage2 = (Calendar) Calendar.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    Calendar parsedMessage3 = (Calendar) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasTime() {
                return (this.bitField0_ & 1) == 1;
            }

            public RtTime getTime() {
                if (this.timeBuilder_ == null) {
                    return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
                }
                return (RtTime) this.timeBuilder_.getMessage();
            }

            public Builder setTime(RtTime value) {
                if (this.timeBuilder_ != null) {
                    this.timeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.time_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTime(com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder builderForValue) {
                if (this.timeBuilder_ == null) {
                    this.time_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTime(RtTime value) {
                if (this.timeBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.time_ == null || this.time_ == RtTime.getDefaultInstance()) {
                        this.time_ = value;
                    } else {
                        this.time_ = RtTime.newBuilder(this.time_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.timeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTime() {
                if (this.timeBuilder_ == null) {
                    this.time_ = null;
                    onChanged();
                } else {
                    this.timeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder getTimeBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder) getTimeFieldBuilder().getBuilder();
            }

            public RtTimeOrBuilder getTimeOrBuilder() {
                if (this.timeBuilder_ != null) {
                    return (RtTimeOrBuilder) this.timeBuilder_.getMessageOrBuilder();
                }
                return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
            }

            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> getTimeFieldBuilder() {
                if (this.timeBuilder_ == null) {
                    this.timeBuilder_ = new SingleFieldBuilderV3<>(getTime(), getParentForChildren(), isClean());
                    this.time_ = null;
                }
                return this.timeBuilder_;
            }

            public boolean hasText() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getText() {
                Object ref = this.text_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.text_ = s;
                return s;
            }

            public ByteString getTextBytes() {
                Object ref = this.text_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.text_ = b;
                return b;
            }

            public Builder setText(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.text_ = value;
                onChanged();
                return this;
            }

            public Builder clearText() {
                this.bitField0_ &= -3;
                this.text_ = Calendar.getDefaultInstance().getText();
                onChanged();
                return this;
            }

            public Builder setTextBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.text_ = value;
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

        private Calendar(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private Calendar() {
            this.memoizedIsInitialized = -1;
            this.text_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Calendar(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.time_.toBuilder();
                            }
                            this.time_ = (RtTime) input.readMessage(RtTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.time_);
                                this.time_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.text_ = bs;
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
            return CalendarOuterClass.internal_static_Calendar_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return CalendarOuterClass.internal_static_Calendar_fieldAccessorTable.ensureFieldAccessorsInitialized(Calendar.class, Builder.class);
        }

        public boolean hasTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtTime getTime() {
            return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
        }

        public RtTimeOrBuilder getTimeOrBuilder() {
            return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
        }

        public boolean hasText() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getText() {
            Object ref = this.text_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.text_ = s;
            }
            return s;
        }

        public ByteString getTextBytes() {
            Object ref = this.text_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.text_ = b;
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
            if (!hasTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getTime().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                GeneratedMessageV3.writeString(output, 2, this.text_);
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += GeneratedMessageV3.computeStringSize(2, this.text_);
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
            if (!(obj instanceof Calendar)) {
                return super.equals(obj);
            }
            Calendar other = (Calendar) obj;
            boolean result3 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result3 = result3 && getTime().equals(other.getTime());
            }
            if (!result3 || hasText() != other.hasText()) {
                result = false;
            } else {
                result = true;
            }
            if (hasText()) {
                if (!result || !getText().equals(other.getText())) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + getTime().hashCode();
            }
            if (hasText()) {
                hash = (((hash * 37) + 2) * 53) + getText().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static Calendar parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Calendar) PARSER.parseFrom(data);
        }

        public static Calendar parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Calendar) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Calendar parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Calendar) PARSER.parseFrom(data);
        }

        public static Calendar parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Calendar) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Calendar parseFrom(InputStream input) throws IOException {
            return (Calendar) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Calendar parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Calendar) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Calendar parseDelimitedFrom(InputStream input) throws IOException {
            return (Calendar) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Calendar parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Calendar) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Calendar parseFrom(CodedInputStream input) throws IOException {
            return (Calendar) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Calendar parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Calendar) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Calendar prototype) {
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

        public static Calendar getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Calendar> parser() {
            return PARSER;
        }

        public Parser<Calendar> getParserForType() {
            return PARSER;
        }

        public Calendar getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class CalendarConfirm extends GeneratedMessageV3 implements CalendarConfirmOrBuilder {
        private static final CalendarConfirm DEFAULT_INSTANCE = new CalendarConfirm();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<CalendarConfirm> PARSER = new AbstractParser<CalendarConfirm>() {
            public CalendarConfirm parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CalendarConfirm(input, extensionRegistry);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CalendarConfirmOrBuilder {
            private int bitField0_;
            private int operation_;
            private boolean ret_;

            public static final Descriptor getDescriptor() {
                return CalendarOuterClass.internal_static_CalendarConfirm_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return CalendarOuterClass.internal_static_CalendarConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarConfirm.class, Builder.class);
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
                if (CalendarConfirm.alwaysUseFieldBuilders) {
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
                return CalendarOuterClass.internal_static_CalendarConfirm_descriptor;
            }

            public CalendarConfirm getDefaultInstanceForType() {
                return CalendarConfirm.getDefaultInstance();
            }

            public CalendarConfirm build() {
                CalendarConfirm result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CalendarConfirm buildPartial() {
                CalendarConfirm result = new CalendarConfirm((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (other instanceof CalendarConfirm) {
                    return mergeFrom((CalendarConfirm) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CalendarConfirm other) {
                if (other != CalendarConfirm.getDefaultInstance()) {
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
                CalendarConfirm parsedMessage = null;
                try {
                    CalendarConfirm parsedMessage2 = (CalendarConfirm) CalendarConfirm.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CalendarConfirm parsedMessage3 = (CalendarConfirm) e.getUnfinishedMessage();
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

            public CalendarOperation getOperation() {
                CalendarOperation result = CalendarOperation.valueOf(this.operation_);
                return result == null ? CalendarOperation.ADD : result;
            }

            public Builder setOperation(CalendarOperation value) {
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

        private CalendarConfirm(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CalendarConfirm() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
            this.ret_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CalendarConfirm(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (CalendarOperation.valueOf(rawValue) != null) {
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
            return CalendarOuterClass.internal_static_CalendarConfirm_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return CalendarOuterClass.internal_static_CalendarConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarConfirm.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public CalendarOperation getOperation() {
            CalendarOperation result = CalendarOperation.valueOf(this.operation_);
            return result == null ? CalendarOperation.ADD : result;
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
            if (!(obj instanceof CalendarConfirm)) {
                return super.equals(obj);
            }
            CalendarConfirm other = (CalendarConfirm) obj;
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

        public static CalendarConfirm parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CalendarConfirm) PARSER.parseFrom(data);
        }

        public static CalendarConfirm parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarConfirm parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CalendarConfirm) PARSER.parseFrom(data);
        }

        public static CalendarConfirm parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarConfirm parseFrom(InputStream input) throws IOException {
            return (CalendarConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarConfirm parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarConfirm parseDelimitedFrom(InputStream input) throws IOException {
            return (CalendarConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CalendarConfirm parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarConfirm parseFrom(CodedInputStream input) throws IOException {
            return (CalendarConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarConfirm parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CalendarConfirm prototype) {
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

        public static CalendarConfirm getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CalendarConfirm> parser() {
            return PARSER;
        }

        public Parser<CalendarConfirm> getParserForType() {
            return PARSER;
        }

        public CalendarConfirm getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class CalendarGroup extends GeneratedMessageV3 implements CalendarGroupOrBuilder {
        public static final int CALENDAR_FIELD_NUMBER = 2;
        private static final CalendarGroup DEFAULT_INSTANCE = new CalendarGroup();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<CalendarGroup> PARSER = new AbstractParser<CalendarGroup>() {
            public CalendarGroup parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CalendarGroup(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public List<Calendar> calendar_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CalendarGroupOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilderV3<Calendar, Builder, CalendarOrBuilder> calendarBuilder_;
            private List<Calendar> calendar_;
            private int hash_;

            public static final Descriptor getDescriptor() {
                return CalendarOuterClass.internal_static_CalendarGroup_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return CalendarOuterClass.internal_static_CalendarGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarGroup.class, Builder.class);
            }

            private Builder() {
                this.calendar_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.calendar_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CalendarGroup.alwaysUseFieldBuilders) {
                    getCalendarFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.calendarBuilder_ == null) {
                    this.calendar_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.calendarBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return CalendarOuterClass.internal_static_CalendarGroup_descriptor;
            }

            public CalendarGroup getDefaultInstanceForType() {
                return CalendarGroup.getDefaultInstance();
            }

            public CalendarGroup build() {
                CalendarGroup result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CalendarGroup buildPartial() {
                CalendarGroup result = new CalendarGroup((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.calendarBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.calendar_ = Collections.unmodifiableList(this.calendar_);
                        this.bitField0_ &= -3;
                    }
                    result.calendar_ = this.calendar_;
                } else {
                    result.calendar_ = this.calendarBuilder_.build();
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
                if (other instanceof CalendarGroup) {
                    return mergeFrom((CalendarGroup) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CalendarGroup other) {
                RepeatedFieldBuilderV3<Calendar, Builder, CalendarOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != CalendarGroup.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (this.calendarBuilder_ == null) {
                        if (!other.calendar_.isEmpty()) {
                            if (this.calendar_.isEmpty()) {
                                this.calendar_ = other.calendar_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureCalendarIsMutable();
                                this.calendar_.addAll(other.calendar_);
                            }
                            onChanged();
                        }
                    } else if (!other.calendar_.isEmpty()) {
                        if (this.calendarBuilder_.isEmpty()) {
                            this.calendarBuilder_.dispose();
                            this.calendarBuilder_ = null;
                            this.calendar_ = other.calendar_;
                            this.bitField0_ &= -3;
                            if (CalendarGroup.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getCalendarFieldBuilder();
                            }
                            this.calendarBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.calendarBuilder_.addAllMessages(other.calendar_);
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
                for (int i = 0; i < getCalendarCount(); i++) {
                    if (!getCalendar(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                CalendarGroup parsedMessage = null;
                try {
                    CalendarGroup parsedMessage2 = (CalendarGroup) CalendarGroup.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CalendarGroup parsedMessage3 = (CalendarGroup) e.getUnfinishedMessage();
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

            private void ensureCalendarIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.calendar_ = new ArrayList(this.calendar_);
                    this.bitField0_ |= 2;
                }
            }

            public List<Calendar> getCalendarList() {
                if (this.calendarBuilder_ == null) {
                    return Collections.unmodifiableList(this.calendar_);
                }
                return this.calendarBuilder_.getMessageList();
            }

            public int getCalendarCount() {
                if (this.calendarBuilder_ == null) {
                    return this.calendar_.size();
                }
                return this.calendarBuilder_.getCount();
            }

            public Calendar getCalendar(int index) {
                if (this.calendarBuilder_ == null) {
                    return (Calendar) this.calendar_.get(index);
                }
                return (Calendar) this.calendarBuilder_.getMessage(index);
            }

            public Builder setCalendar(int index, Calendar value) {
                if (this.calendarBuilder_ != null) {
                    this.calendarBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureCalendarIsMutable();
                    this.calendar_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setCalendar(int index, Builder builderForValue) {
                if (this.calendarBuilder_ == null) {
                    ensureCalendarIsMutable();
                    this.calendar_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.calendarBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addCalendar(Calendar value) {
                if (this.calendarBuilder_ != null) {
                    this.calendarBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureCalendarIsMutable();
                    this.calendar_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addCalendar(int index, Calendar value) {
                if (this.calendarBuilder_ != null) {
                    this.calendarBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureCalendarIsMutable();
                    this.calendar_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addCalendar(Builder builderForValue) {
                if (this.calendarBuilder_ == null) {
                    ensureCalendarIsMutable();
                    this.calendar_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.calendarBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addCalendar(int index, Builder builderForValue) {
                if (this.calendarBuilder_ == null) {
                    ensureCalendarIsMutable();
                    this.calendar_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.calendarBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllCalendar(Iterable<? extends Calendar> values) {
                if (this.calendarBuilder_ == null) {
                    ensureCalendarIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.calendar_);
                    onChanged();
                } else {
                    this.calendarBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearCalendar() {
                if (this.calendarBuilder_ == null) {
                    this.calendar_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.calendarBuilder_.clear();
                }
                return this;
            }

            public Builder removeCalendar(int index) {
                if (this.calendarBuilder_ == null) {
                    ensureCalendarIsMutable();
                    this.calendar_.remove(index);
                    onChanged();
                } else {
                    this.calendarBuilder_.remove(index);
                }
                return this;
            }

            public Builder getCalendarBuilder(int index) {
                return (Builder) getCalendarFieldBuilder().getBuilder(index);
            }

            public CalendarOrBuilder getCalendarOrBuilder(int index) {
                if (this.calendarBuilder_ == null) {
                    return (CalendarOrBuilder) this.calendar_.get(index);
                }
                return (CalendarOrBuilder) this.calendarBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends CalendarOrBuilder> getCalendarOrBuilderList() {
                if (this.calendarBuilder_ != null) {
                    return this.calendarBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.calendar_);
            }

            public Builder addCalendarBuilder() {
                return (Builder) getCalendarFieldBuilder().addBuilder(Calendar.getDefaultInstance());
            }

            public Builder addCalendarBuilder(int index) {
                return (Builder) getCalendarFieldBuilder().addBuilder(index, Calendar.getDefaultInstance());
            }

            public List<Builder> getCalendarBuilderList() {
                return getCalendarFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Calendar, Builder, CalendarOrBuilder> getCalendarFieldBuilder() {
                if (this.calendarBuilder_ == null) {
                    this.calendarBuilder_ = new RepeatedFieldBuilderV3<>(this.calendar_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.calendar_ = null;
                }
                return this.calendarBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private CalendarGroup(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CalendarGroup() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.calendar_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CalendarGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.calendar_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.calendar_.add(input.readMessage(Calendar.PARSER, extensionRegistry));
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
                        this.calendar_ = Collections.unmodifiableList(this.calendar_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.calendar_ = Collections.unmodifiableList(this.calendar_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return CalendarOuterClass.internal_static_CalendarGroup_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return CalendarOuterClass.internal_static_CalendarGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarGroup.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<Calendar> getCalendarList() {
            return this.calendar_;
        }

        public List<? extends CalendarOrBuilder> getCalendarOrBuilderList() {
            return this.calendar_;
        }

        public int getCalendarCount() {
            return this.calendar_.size();
        }

        public Calendar getCalendar(int index) {
            return (Calendar) this.calendar_.get(index);
        }

        public CalendarOrBuilder getCalendarOrBuilder(int index) {
            return (CalendarOrBuilder) this.calendar_.get(index);
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
            for (int i = 0; i < getCalendarCount(); i++) {
                if (!getCalendar(i).isInitialized()) {
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
            for (int i = 0; i < this.calendar_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.calendar_.get(i));
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
            for (int i = 0; i < this.calendar_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.calendar_.get(i));
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
            if (!(obj instanceof CalendarGroup)) {
                return super.equals(obj);
            }
            CalendarGroup other = (CalendarGroup) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getCalendarList().equals(other.getCalendarList())) {
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
            if (getCalendarCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getCalendarList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CalendarGroup parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CalendarGroup) PARSER.parseFrom(data);
        }

        public static CalendarGroup parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarGroup parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CalendarGroup) PARSER.parseFrom(data);
        }

        public static CalendarGroup parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarGroup parseFrom(InputStream input) throws IOException {
            return (CalendarGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarGroup parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarGroup parseDelimitedFrom(InputStream input) throws IOException {
            return (CalendarGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CalendarGroup parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarGroup parseFrom(CodedInputStream input) throws IOException {
            return (CalendarGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarGroup parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CalendarGroup prototype) {
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

        public static CalendarGroup getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CalendarGroup> parser() {
            return PARSER;
        }

        public Parser<CalendarGroup> getParserForType() {
            return PARSER;
        }

        public CalendarGroup getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class CalendarNotification extends GeneratedMessageV3 implements CalendarNotificationOrBuilder {
        private static final CalendarNotification DEFAULT_INSTANCE = new CalendarNotification();
        public static final int GROUP_FIELD_NUMBER = 2;
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<CalendarNotification> PARSER = new AbstractParser<CalendarNotification>() {
            public CalendarNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CalendarNotification(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public CalendarGroup group_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CalendarNotificationOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<CalendarGroup, Builder, CalendarGroupOrBuilder> groupBuilder_;
            private CalendarGroup group_;
            private int operation_;

            public static final Descriptor getDescriptor() {
                return CalendarOuterClass.internal_static_CalendarNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return CalendarOuterClass.internal_static_CalendarNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarNotification.class, Builder.class);
            }

            private Builder() {
                this.operation_ = 0;
                this.group_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.operation_ = 0;
                this.group_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CalendarNotification.alwaysUseFieldBuilders) {
                    getGroupFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.operation_ = 0;
                this.bitField0_ &= -2;
                if (this.groupBuilder_ == null) {
                    this.group_ = null;
                } else {
                    this.groupBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return CalendarOuterClass.internal_static_CalendarNotification_descriptor;
            }

            public CalendarNotification getDefaultInstanceForType() {
                return CalendarNotification.getDefaultInstance();
            }

            public CalendarNotification build() {
                CalendarNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CalendarNotification buildPartial() {
                CalendarNotification result = new CalendarNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.operation_ = this.operation_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.groupBuilder_ == null) {
                    result.group_ = this.group_;
                } else {
                    result.group_ = (CalendarGroup) this.groupBuilder_.build();
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
                if (other instanceof CalendarNotification) {
                    return mergeFrom((CalendarNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CalendarNotification other) {
                if (other != CalendarNotification.getDefaultInstance()) {
                    if (other.hasOperation()) {
                        setOperation(other.getOperation());
                    }
                    if (other.hasGroup()) {
                        mergeGroup(other.getGroup());
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
                if (!hasGroup() || getGroup().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                CalendarNotification parsedMessage = null;
                try {
                    CalendarNotification parsedMessage2 = (CalendarNotification) CalendarNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CalendarNotification parsedMessage3 = (CalendarNotification) e.getUnfinishedMessage();
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

            public CalendarOperation getOperation() {
                CalendarOperation result = CalendarOperation.valueOf(this.operation_);
                return result == null ? CalendarOperation.ADD : result;
            }

            public Builder setOperation(CalendarOperation value) {
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

            public boolean hasGroup() {
                return (this.bitField0_ & 2) == 2;
            }

            public CalendarGroup getGroup() {
                if (this.groupBuilder_ == null) {
                    return this.group_ == null ? CalendarGroup.getDefaultInstance() : this.group_;
                }
                return (CalendarGroup) this.groupBuilder_.getMessage();
            }

            public Builder setGroup(CalendarGroup value) {
                if (this.groupBuilder_ != null) {
                    this.groupBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.group_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setGroup(Builder builderForValue) {
                if (this.groupBuilder_ == null) {
                    this.group_ = builderForValue.build();
                    onChanged();
                } else {
                    this.groupBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeGroup(CalendarGroup value) {
                if (this.groupBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.group_ == null || this.group_ == CalendarGroup.getDefaultInstance()) {
                        this.group_ = value;
                    } else {
                        this.group_ = CalendarGroup.newBuilder(this.group_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.groupBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearGroup() {
                if (this.groupBuilder_ == null) {
                    this.group_ = null;
                    onChanged();
                } else {
                    this.groupBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getGroupBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getGroupFieldBuilder().getBuilder();
            }

            public CalendarGroupOrBuilder getGroupOrBuilder() {
                if (this.groupBuilder_ != null) {
                    return (CalendarGroupOrBuilder) this.groupBuilder_.getMessageOrBuilder();
                }
                return this.group_ == null ? CalendarGroup.getDefaultInstance() : this.group_;
            }

            private SingleFieldBuilderV3<CalendarGroup, Builder, CalendarGroupOrBuilder> getGroupFieldBuilder() {
                if (this.groupBuilder_ == null) {
                    this.groupBuilder_ = new SingleFieldBuilderV3<>(getGroup(), getParentForChildren(), isClean());
                    this.group_ = null;
                }
                return this.groupBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private CalendarNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CalendarNotification() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CalendarNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (CalendarOperation.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.operation_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.group_.toBuilder();
                            }
                            this.group_ = (CalendarGroup) input.readMessage(CalendarGroup.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.group_);
                                this.group_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
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
            return CalendarOuterClass.internal_static_CalendarNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return CalendarOuterClass.internal_static_CalendarNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarNotification.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public CalendarOperation getOperation() {
            CalendarOperation result = CalendarOperation.valueOf(this.operation_);
            return result == null ? CalendarOperation.ADD : result;
        }

        public boolean hasGroup() {
            return (this.bitField0_ & 2) == 2;
        }

        public CalendarGroup getGroup() {
            return this.group_ == null ? CalendarGroup.getDefaultInstance() : this.group_;
        }

        public CalendarGroupOrBuilder getGroupOrBuilder() {
            return this.group_ == null ? CalendarGroup.getDefaultInstance() : this.group_;
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
            } else if (!hasGroup() || getGroup().isInitialized()) {
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
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getGroup());
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
                size2 += CodedOutputStream.computeMessageSize(2, getGroup());
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
            if (!(obj instanceof CalendarNotification)) {
                return super.equals(obj);
            }
            CalendarNotification other = (CalendarNotification) obj;
            boolean result3 = 1 != 0 && hasOperation() == other.hasOperation();
            if (hasOperation()) {
                result3 = result3 && this.operation_ == other.operation_;
            }
            if (!result3 || hasGroup() != other.hasGroup()) {
                result = false;
            } else {
                result = true;
            }
            if (hasGroup()) {
                if (!result || !getGroup().equals(other.getGroup())) {
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
            if (hasGroup()) {
                hash = (((hash * 37) + 2) * 53) + getGroup().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CalendarNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CalendarNotification) PARSER.parseFrom(data);
        }

        public static CalendarNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CalendarNotification) PARSER.parseFrom(data);
        }

        public static CalendarNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarNotification parseFrom(InputStream input) throws IOException {
            return (CalendarNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (CalendarNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CalendarNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarNotification parseFrom(CodedInputStream input) throws IOException {
            return (CalendarNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CalendarNotification prototype) {
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

        public static CalendarNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CalendarNotification> parser() {
            return PARSER;
        }

        public Parser<CalendarNotification> getParserForType() {
            return PARSER;
        }

        public CalendarNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum CalendarOperation implements ProtocolMessageEnum {
        ADD(0),
        REMOVE(1),
        CLEAR(2);
        
        public static final int ADD_VALUE = 0;
        public static final int CLEAR_VALUE = 2;
        public static final int REMOVE_VALUE = 1;
        private static final CalendarOperation[] VALUES = null;
        private static final EnumLiteMap<CalendarOperation> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<CalendarOperation>() {
                public CalendarOperation findValueByNumber(int number) {
                    return CalendarOperation.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static CalendarOperation valueOf(int value2) {
            return forNumber(value2);
        }

        public static CalendarOperation forNumber(int value2) {
            switch (value2) {
                case 0:
                    return ADD;
                case 1:
                    return REMOVE;
                case 2:
                    return CLEAR;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<CalendarOperation> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) CalendarOuterClass.getDescriptor().getEnumTypes().get(0);
        }

        public static CalendarOperation valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private CalendarOperation(int value2) {
            this.value = value2;
        }
    }

    public static final class CalendarSubscriber extends GeneratedMessageV3 implements CalendarSubscriberOrBuilder {
        public static final int CONFIRM_FIELD_NUMBER = 3;
        private static final CalendarSubscriber DEFAULT_INSTANCE = new CalendarSubscriber();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int MAX_COUNT_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<CalendarSubscriber> PARSER = new AbstractParser<CalendarSubscriber>() {
            public CalendarSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CalendarSubscriber(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public CalendarConfirm confirm_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public int maxCount_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CalendarSubscriberOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<CalendarConfirm, Builder, CalendarConfirmOrBuilder> confirmBuilder_;
            private CalendarConfirm confirm_;
            private int hash_;
            private int maxCount_;

            public static final Descriptor getDescriptor() {
                return CalendarOuterClass.internal_static_CalendarSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return CalendarOuterClass.internal_static_CalendarSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarSubscriber.class, Builder.class);
            }

            private Builder() {
                this.confirm_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.confirm_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CalendarSubscriber.alwaysUseFieldBuilders) {
                    getConfirmFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.maxCount_ = 0;
                this.bitField0_ &= -3;
                if (this.confirmBuilder_ == null) {
                    this.confirm_ = null;
                } else {
                    this.confirmBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return CalendarOuterClass.internal_static_CalendarSubscriber_descriptor;
            }

            public CalendarSubscriber getDefaultInstanceForType() {
                return CalendarSubscriber.getDefaultInstance();
            }

            public CalendarSubscriber build() {
                CalendarSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CalendarSubscriber buildPartial() {
                CalendarSubscriber result = new CalendarSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.maxCount_ = this.maxCount_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.confirmBuilder_ == null) {
                    result.confirm_ = this.confirm_;
                } else {
                    result.confirm_ = (CalendarConfirm) this.confirmBuilder_.build();
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
                if (other instanceof CalendarSubscriber) {
                    return mergeFrom((CalendarSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CalendarSubscriber other) {
                if (other != CalendarSubscriber.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasMaxCount()) {
                        setMaxCount(other.getMaxCount());
                    }
                    if (other.hasConfirm()) {
                        mergeConfirm(other.getConfirm());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasHash() || !hasMaxCount()) {
                    return false;
                }
                if (!hasConfirm() || getConfirm().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                CalendarSubscriber parsedMessage = null;
                try {
                    CalendarSubscriber parsedMessage2 = (CalendarSubscriber) CalendarSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CalendarSubscriber parsedMessage3 = (CalendarSubscriber) e.getUnfinishedMessage();
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

            public boolean hasMaxCount() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getMaxCount() {
                return this.maxCount_;
            }

            public Builder setMaxCount(int value) {
                this.bitField0_ |= 2;
                this.maxCount_ = value;
                onChanged();
                return this;
            }

            public Builder clearMaxCount() {
                this.bitField0_ &= -3;
                this.maxCount_ = 0;
                onChanged();
                return this;
            }

            public boolean hasConfirm() {
                return (this.bitField0_ & 4) == 4;
            }

            public CalendarConfirm getConfirm() {
                if (this.confirmBuilder_ == null) {
                    return this.confirm_ == null ? CalendarConfirm.getDefaultInstance() : this.confirm_;
                }
                return (CalendarConfirm) this.confirmBuilder_.getMessage();
            }

            public Builder setConfirm(CalendarConfirm value) {
                if (this.confirmBuilder_ != null) {
                    this.confirmBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.confirm_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setConfirm(Builder builderForValue) {
                if (this.confirmBuilder_ == null) {
                    this.confirm_ = builderForValue.build();
                    onChanged();
                } else {
                    this.confirmBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeConfirm(CalendarConfirm value) {
                if (this.confirmBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.confirm_ == null || this.confirm_ == CalendarConfirm.getDefaultInstance()) {
                        this.confirm_ = value;
                    } else {
                        this.confirm_ = CalendarConfirm.newBuilder(this.confirm_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.confirmBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearConfirm() {
                if (this.confirmBuilder_ == null) {
                    this.confirm_ = null;
                    onChanged();
                } else {
                    this.confirmBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getConfirmBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getConfirmFieldBuilder().getBuilder();
            }

            public CalendarConfirmOrBuilder getConfirmOrBuilder() {
                if (this.confirmBuilder_ != null) {
                    return (CalendarConfirmOrBuilder) this.confirmBuilder_.getMessageOrBuilder();
                }
                return this.confirm_ == null ? CalendarConfirm.getDefaultInstance() : this.confirm_;
            }

            private SingleFieldBuilderV3<CalendarConfirm, Builder, CalendarConfirmOrBuilder> getConfirmFieldBuilder() {
                if (this.confirmBuilder_ == null) {
                    this.confirmBuilder_ = new SingleFieldBuilderV3<>(getConfirm(), getParentForChildren(), isClean());
                    this.confirm_ = null;
                }
                return this.confirmBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private CalendarSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CalendarSubscriber() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.maxCount_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CalendarSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 21:
                            this.bitField0_ |= 2;
                            this.maxCount_ = input.readFixed32();
                            break;
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.confirm_.toBuilder();
                            }
                            this.confirm_ = (CalendarConfirm) input.readMessage(CalendarConfirm.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.confirm_);
                                this.confirm_ = subBuilder.buildPartial();
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
            return CalendarOuterClass.internal_static_CalendarSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return CalendarOuterClass.internal_static_CalendarSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(CalendarSubscriber.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasMaxCount() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getMaxCount() {
            return this.maxCount_;
        }

        public boolean hasConfirm() {
            return (this.bitField0_ & 4) == 4;
        }

        public CalendarConfirm getConfirm() {
            return this.confirm_ == null ? CalendarConfirm.getDefaultInstance() : this.confirm_;
        }

        public CalendarConfirmOrBuilder getConfirmOrBuilder() {
            return this.confirm_ == null ? CalendarConfirm.getDefaultInstance() : this.confirm_;
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
            } else if (!hasMaxCount()) {
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
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.maxCount_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getConfirm());
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
                size2 += CodedOutputStream.computeFixed32Size(2, this.maxCount_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getConfirm());
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
            if (!(obj instanceof CalendarSubscriber)) {
                return super.equals(obj);
            }
            CalendarSubscriber other = (CalendarSubscriber) obj;
            boolean result4 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result4 = result4 && getHash() == other.getHash();
            }
            if (!result4 || hasMaxCount() != other.hasMaxCount()) {
                result = false;
            } else {
                result = true;
            }
            if (hasMaxCount()) {
                if (!result || getMaxCount() != other.getMaxCount()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasConfirm() != other.hasConfirm()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasConfirm()) {
                if (!result2 || !getConfirm().equals(other.getConfirm())) {
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
            if (hasMaxCount()) {
                hash = (((hash * 37) + 2) * 53) + getMaxCount();
            }
            if (hasConfirm()) {
                hash = (((hash * 37) + 3) * 53) + getConfirm().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CalendarSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CalendarSubscriber) PARSER.parseFrom(data);
        }

        public static CalendarSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CalendarSubscriber) PARSER.parseFrom(data);
        }

        public static CalendarSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CalendarSubscriber parseFrom(InputStream input) throws IOException {
            return (CalendarSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (CalendarSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CalendarSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CalendarSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (CalendarSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CalendarSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CalendarSubscriber prototype) {
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

        public static CalendarSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CalendarSubscriber> parser() {
            return PARSER;
        }

        public Parser<CalendarSubscriber> getParserForType() {
            return PARSER;
        }

        public CalendarSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface CalendarConfirmOrBuilder extends MessageOrBuilder {
        CalendarOperation getOperation();

        boolean getRet();

        boolean hasOperation();

        boolean hasRet();
    }

    public interface CalendarGroupOrBuilder extends MessageOrBuilder {
        Calendar getCalendar(int i);

        int getCalendarCount();

        List<Calendar> getCalendarList();

        CalendarOrBuilder getCalendarOrBuilder(int i);

        List<? extends CalendarOrBuilder> getCalendarOrBuilderList();

        int getHash();

        boolean hasHash();
    }

    public interface CalendarNotificationOrBuilder extends MessageOrBuilder {
        CalendarGroup getGroup();

        CalendarGroupOrBuilder getGroupOrBuilder();

        CalendarOperation getOperation();

        boolean hasGroup();

        boolean hasOperation();
    }

    public interface CalendarOrBuilder extends MessageOrBuilder {
        String getText();

        ByteString getTextBytes();

        RtTime getTime();

        RtTimeOrBuilder getTimeOrBuilder();

        boolean hasText();

        boolean hasTime();
    }

    public interface CalendarSubscriberOrBuilder extends MessageOrBuilder {
        CalendarConfirm getConfirm();

        CalendarConfirmOrBuilder getConfirmOrBuilder();

        int getHash();

        int getMaxCount();

        boolean hasConfirm();

        boolean hasHash();

        boolean hasMaxCount();
    }

    private CalendarOuterClass() {
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
        FileDescriptor[] fileDescriptorArr = {RealtimeData.getDescriptor()};
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u000ecalendar.proto\u001a\u0013realtime_data.proto\"/\n\bCalendar\u0012\u0015\n\u0004time\u0018\u0001 \u0002(\u000b2\u0007.RtTime\u0012\f\n\u0004text\u0018\u0002 \u0001(\t\"E\n\u000fCalendarConfirm\u0012%\n\toperation\u0018\u0001 \u0002(\u000e2\u0012.CalendarOperation\u0012\u000b\n\u0003ret\u0018\u0002 \u0002(\b\":\n\rCalendarGroup\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u001b\n\bcalendar\u0018\u0002 \u0003(\u000b2\t.Calendar\"\\\n\u0014CalendarNotification\u0012%\n\toperation\u0018\u0001 \u0002(\u000e2\u0012.CalendarOperation\u0012\u001d\n\u0005group\u0018\u0002 \u0001(\u000b2\u000e.CalendarGroup\"X\n\u0012CalendarSubscriber\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0011\n\tmax_count\u0018\u0002 \u0002(\u0007\u0012!\n\u0007confirm\u0018\u0003 \u0001(\u000b2\u0010.CalendarConfir", "m*3\n\u0011CalendarOperation\u0012\u0007\n\u0003ADD\u0010\u0000\u0012\n\n\u0006REMOVE\u0010\u0001\u0012\t\n\u0005CLEAR\u0010\u0002"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                CalendarOuterClass.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
