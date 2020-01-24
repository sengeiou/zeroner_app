package com.iwown.ble_module.proto.base;

import com.google.common.net.HttpHeaders;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.iwown.ble_module.proto.base.RealtimeData.DateTime;
import com.iwown.ble_module.proto.base.RealtimeData.DateTimeOrBuilder;
import com.iwown.ble_module.proto.base.RealtimeData.RtBattery;
import com.iwown.ble_module.proto.base.RealtimeData.RtBatteryOrBuilder;
import com.iwown.ble_module.proto.base.RealtimeData.RtGNSS;
import com.iwown.ble_module.proto.base.RealtimeData.RtGNSSOrBuilder;
import com.iwown.ble_module.proto.base.RealtimeData.RtHealth;
import com.iwown.ble_module.proto.base.RealtimeData.RtHealthOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Om0Command {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_OM0Command_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_OM0Command_fieldAccessorTable = new FieldAccessorTable(internal_static_OM0Command_descriptor, new String[]{"TrackConf", "SleepData"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_OM0Report_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_OM0Report_fieldAccessorTable = new FieldAccessorTable(internal_static_OM0Report_descriptor, new String[]{"SosSignal", "WakeUp", "TrackData", "Battery", "Health", "DateTime"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_OM0SleepData_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_OM0SleepData_fieldAccessorTable = new FieldAccessorTable(internal_static_OM0SleepData_descriptor, new String[]{"Score", "Duration", "Deepsleep", "Somnolence", "Wakefulness", HttpHeaders.DATE});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_OM0TrackConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_OM0TrackConf_fieldAccessorTable = new FieldAccessorTable(internal_static_OM0TrackConf_descriptor, new String[]{"Hash", "AutoRun", "Interval"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_OM0TrackData_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_OM0TrackData_fieldAccessorTable = new FieldAccessorTable(internal_static_OM0TrackData_descriptor, new String[]{"Time", "Gnss", "Hr"});

    public static final class OM0Command extends GeneratedMessageV3 implements OM0CommandOrBuilder {
        private static final OM0Command DEFAULT_INSTANCE = new OM0Command();
        @Deprecated
        public static final Parser<OM0Command> PARSER = new AbstractParser<OM0Command>() {
            public OM0Command parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OM0Command(input, extensionRegistry);
            }
        };
        public static final int SLEEP_DATA_FIELD_NUMBER = 2;
        public static final int TRACK_CONF_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public OM0SleepData sleepData_;
        /* access modifiers changed from: private */
        public OM0TrackConf trackConf_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OM0CommandOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<OM0SleepData, Builder, OM0SleepDataOrBuilder> sleepDataBuilder_;
            private OM0SleepData sleepData_;
            private SingleFieldBuilderV3<OM0TrackConf, Builder, OM0TrackConfOrBuilder> trackConfBuilder_;
            private OM0TrackConf trackConf_;

            public static final Descriptor getDescriptor() {
                return Om0Command.internal_static_OM0Command_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Om0Command.internal_static_OM0Command_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0Command.class, Builder.class);
            }

            private Builder() {
                this.trackConf_ = null;
                this.sleepData_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.trackConf_ = null;
                this.sleepData_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (OM0Command.alwaysUseFieldBuilders) {
                    getTrackConfFieldBuilder();
                    getSleepDataFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.trackConfBuilder_ == null) {
                    this.trackConf_ = null;
                } else {
                    this.trackConfBuilder_.clear();
                }
                this.bitField0_ &= -2;
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Om0Command.internal_static_OM0Command_descriptor;
            }

            public OM0Command getDefaultInstanceForType() {
                return OM0Command.getDefaultInstance();
            }

            public OM0Command build() {
                OM0Command result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public OM0Command buildPartial() {
                OM0Command result = new OM0Command((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.trackConfBuilder_ == null) {
                    result.trackConf_ = this.trackConf_;
                } else {
                    result.trackConf_ = (OM0TrackConf) this.trackConfBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.sleepDataBuilder_ == null) {
                    result.sleepData_ = this.sleepData_;
                } else {
                    result.sleepData_ = (OM0SleepData) this.sleepDataBuilder_.build();
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
                if (other instanceof OM0Command) {
                    return mergeFrom((OM0Command) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OM0Command other) {
                if (other != OM0Command.getDefaultInstance()) {
                    if (other.hasTrackConf()) {
                        mergeTrackConf(other.getTrackConf());
                    }
                    if (other.hasSleepData()) {
                        mergeSleepData(other.getSleepData());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasTrackConf() && !getTrackConf().isInitialized()) {
                    return false;
                }
                if (!hasSleepData() || getSleepData().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                OM0Command parsedMessage = null;
                try {
                    OM0Command parsedMessage2 = (OM0Command) OM0Command.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    OM0Command parsedMessage3 = (OM0Command) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasTrackConf() {
                return (this.bitField0_ & 1) == 1;
            }

            public OM0TrackConf getTrackConf() {
                if (this.trackConfBuilder_ == null) {
                    return this.trackConf_ == null ? OM0TrackConf.getDefaultInstance() : this.trackConf_;
                }
                return (OM0TrackConf) this.trackConfBuilder_.getMessage();
            }

            public Builder setTrackConf(OM0TrackConf value) {
                if (this.trackConfBuilder_ != null) {
                    this.trackConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.trackConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTrackConf(Builder builderForValue) {
                if (this.trackConfBuilder_ == null) {
                    this.trackConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.trackConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTrackConf(OM0TrackConf value) {
                if (this.trackConfBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.trackConf_ == null || this.trackConf_ == OM0TrackConf.getDefaultInstance()) {
                        this.trackConf_ = value;
                    } else {
                        this.trackConf_ = OM0TrackConf.newBuilder(this.trackConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.trackConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTrackConf() {
                if (this.trackConfBuilder_ == null) {
                    this.trackConf_ = null;
                    onChanged();
                } else {
                    this.trackConfBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Builder getTrackConfBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getTrackConfFieldBuilder().getBuilder();
            }

            public OM0TrackConfOrBuilder getTrackConfOrBuilder() {
                if (this.trackConfBuilder_ != null) {
                    return (OM0TrackConfOrBuilder) this.trackConfBuilder_.getMessageOrBuilder();
                }
                return this.trackConf_ == null ? OM0TrackConf.getDefaultInstance() : this.trackConf_;
            }

            private SingleFieldBuilderV3<OM0TrackConf, Builder, OM0TrackConfOrBuilder> getTrackConfFieldBuilder() {
                if (this.trackConfBuilder_ == null) {
                    this.trackConfBuilder_ = new SingleFieldBuilderV3<>(getTrackConf(), getParentForChildren(), isClean());
                    this.trackConf_ = null;
                }
                return this.trackConfBuilder_;
            }

            public boolean hasSleepData() {
                return (this.bitField0_ & 2) == 2;
            }

            public OM0SleepData getSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    return this.sleepData_ == null ? OM0SleepData.getDefaultInstance() : this.sleepData_;
                }
                return (OM0SleepData) this.sleepDataBuilder_.getMessage();
            }

            public Builder setSleepData(OM0SleepData value) {
                if (this.sleepDataBuilder_ != null) {
                    this.sleepDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.sleepData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setSleepData(Builder builderForValue) {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.sleepDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeSleepData(OM0SleepData value) {
                if (this.sleepDataBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.sleepData_ == null || this.sleepData_ == OM0SleepData.getDefaultInstance()) {
                        this.sleepData_ = value;
                    } else {
                        this.sleepData_ = OM0SleepData.newBuilder(this.sleepData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.sleepDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                    onChanged();
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getSleepDataBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getSleepDataFieldBuilder().getBuilder();
            }

            public OM0SleepDataOrBuilder getSleepDataOrBuilder() {
                if (this.sleepDataBuilder_ != null) {
                    return (OM0SleepDataOrBuilder) this.sleepDataBuilder_.getMessageOrBuilder();
                }
                return this.sleepData_ == null ? OM0SleepData.getDefaultInstance() : this.sleepData_;
            }

            private SingleFieldBuilderV3<OM0SleepData, Builder, OM0SleepDataOrBuilder> getSleepDataFieldBuilder() {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepDataBuilder_ = new SingleFieldBuilderV3<>(getSleepData(), getParentForChildren(), isClean());
                    this.sleepData_ = null;
                }
                return this.sleepDataBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private OM0Command(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private OM0Command() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OM0Command(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.trackConf_.toBuilder();
                            }
                            this.trackConf_ = (OM0TrackConf) input.readMessage(OM0TrackConf.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.trackConf_);
                                this.trackConf_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder2 = this.sleepData_.toBuilder();
                            }
                            this.sleepData_ = (OM0SleepData) input.readMessage(OM0SleepData.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.sleepData_);
                                this.sleepData_ = subBuilder2.buildPartial();
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
            return Om0Command.internal_static_OM0Command_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Om0Command.internal_static_OM0Command_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0Command.class, Builder.class);
        }

        public boolean hasTrackConf() {
            return (this.bitField0_ & 1) == 1;
        }

        public OM0TrackConf getTrackConf() {
            return this.trackConf_ == null ? OM0TrackConf.getDefaultInstance() : this.trackConf_;
        }

        public OM0TrackConfOrBuilder getTrackConfOrBuilder() {
            return this.trackConf_ == null ? OM0TrackConf.getDefaultInstance() : this.trackConf_;
        }

        public boolean hasSleepData() {
            return (this.bitField0_ & 2) == 2;
        }

        public OM0SleepData getSleepData() {
            return this.sleepData_ == null ? OM0SleepData.getDefaultInstance() : this.sleepData_;
        }

        public OM0SleepDataOrBuilder getSleepDataOrBuilder() {
            return this.sleepData_ == null ? OM0SleepData.getDefaultInstance() : this.sleepData_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasTrackConf() && !getTrackConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSleepData() || getSleepData().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTrackConf());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getSleepData());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getTrackConf());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, getSleepData());
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
            if (!(obj instanceof OM0Command)) {
                return super.equals(obj);
            }
            OM0Command other = (OM0Command) obj;
            boolean result3 = 1 != 0 && hasTrackConf() == other.hasTrackConf();
            if (hasTrackConf()) {
                result3 = result3 && getTrackConf().equals(other.getTrackConf());
            }
            if (!result3 || hasSleepData() != other.hasSleepData()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSleepData()) {
                if (!result || !getSleepData().equals(other.getSleepData())) {
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
            if (hasTrackConf()) {
                hash = (((hash * 37) + 1) * 53) + getTrackConf().hashCode();
            }
            if (hasSleepData()) {
                hash = (((hash * 37) + 2) * 53) + getSleepData().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static OM0Command parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OM0Command) PARSER.parseFrom(data);
        }

        public static OM0Command parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0Command) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0Command parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OM0Command) PARSER.parseFrom(data);
        }

        public static OM0Command parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0Command) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0Command parseFrom(InputStream input) throws IOException {
            return (OM0Command) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0Command parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0Command) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0Command parseDelimitedFrom(InputStream input) throws IOException {
            return (OM0Command) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static OM0Command parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0Command) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0Command parseFrom(CodedInputStream input) throws IOException {
            return (OM0Command) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0Command parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0Command) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OM0Command prototype) {
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

        public static OM0Command getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OM0Command> parser() {
            return PARSER;
        }

        public Parser<OM0Command> getParserForType() {
            return PARSER;
        }

        public OM0Command getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class OM0Report extends GeneratedMessageV3 implements OM0ReportOrBuilder {
        public static final int BATTERY_FIELD_NUMBER = 4;
        public static final int DATE_TIME_FIELD_NUMBER = 6;
        private static final OM0Report DEFAULT_INSTANCE = new OM0Report();
        public static final int HEALTH_FIELD_NUMBER = 5;
        @Deprecated
        public static final Parser<OM0Report> PARSER = new AbstractParser<OM0Report>() {
            public OM0Report parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OM0Report(input, extensionRegistry);
            }
        };
        public static final int SOS_SIGNAL_FIELD_NUMBER = 1;
        public static final int TRACK_DATA_FIELD_NUMBER = 3;
        public static final int WAKE_UP_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public RtBattery battery_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public DateTime dateTime_;
        /* access modifiers changed from: private */
        public RtHealth health_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean sosSignal_;
        /* access modifiers changed from: private */
        public List<OM0TrackData> trackData_;
        /* access modifiers changed from: private */
        public boolean wakeUp_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OM0ReportOrBuilder {
            private SingleFieldBuilderV3<RtBattery, com.iwown.ble_module.proto.base.RealtimeData.RtBattery.Builder, RtBatteryOrBuilder> batteryBuilder_;
            private RtBattery battery_;
            private int bitField0_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> dateTimeBuilder_;
            private DateTime dateTime_;
            private SingleFieldBuilderV3<RtHealth, com.iwown.ble_module.proto.base.RealtimeData.RtHealth.Builder, RtHealthOrBuilder> healthBuilder_;
            private RtHealth health_;
            private boolean sosSignal_;
            private RepeatedFieldBuilderV3<OM0TrackData, Builder, OM0TrackDataOrBuilder> trackDataBuilder_;
            private List<OM0TrackData> trackData_;
            private boolean wakeUp_;

            public static final Descriptor getDescriptor() {
                return Om0Command.internal_static_OM0Report_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Om0Command.internal_static_OM0Report_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0Report.class, Builder.class);
            }

            private Builder() {
                this.trackData_ = Collections.emptyList();
                this.battery_ = null;
                this.health_ = null;
                this.dateTime_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.trackData_ = Collections.emptyList();
                this.battery_ = null;
                this.health_ = null;
                this.dateTime_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (OM0Report.alwaysUseFieldBuilders) {
                    getTrackDataFieldBuilder();
                    getBatteryFieldBuilder();
                    getHealthFieldBuilder();
                    getDateTimeFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.sosSignal_ = false;
                this.bitField0_ &= -2;
                this.wakeUp_ = false;
                this.bitField0_ &= -3;
                if (this.trackDataBuilder_ == null) {
                    this.trackData_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.trackDataBuilder_.clear();
                }
                if (this.batteryBuilder_ == null) {
                    this.battery_ = null;
                } else {
                    this.batteryBuilder_.clear();
                }
                this.bitField0_ &= -9;
                if (this.healthBuilder_ == null) {
                    this.health_ = null;
                } else {
                    this.healthBuilder_.clear();
                }
                this.bitField0_ &= -17;
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Om0Command.internal_static_OM0Report_descriptor;
            }

            public OM0Report getDefaultInstanceForType() {
                return OM0Report.getDefaultInstance();
            }

            public OM0Report build() {
                OM0Report result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public OM0Report buildPartial() {
                OM0Report result = new OM0Report((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.sosSignal_ = this.sosSignal_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.wakeUp_ = this.wakeUp_;
                if (this.trackDataBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.trackData_ = Collections.unmodifiableList(this.trackData_);
                        this.bitField0_ &= -5;
                    }
                    result.trackData_ = this.trackData_;
                } else {
                    result.trackData_ = this.trackDataBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                if (this.batteryBuilder_ == null) {
                    result.battery_ = this.battery_;
                } else {
                    result.battery_ = (RtBattery) this.batteryBuilder_.build();
                }
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 8;
                }
                if (this.healthBuilder_ == null) {
                    result.health_ = this.health_;
                } else {
                    result.health_ = (RtHealth) this.healthBuilder_.build();
                }
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 16;
                }
                if (this.dateTimeBuilder_ == null) {
                    result.dateTime_ = this.dateTime_;
                } else {
                    result.dateTime_ = (DateTime) this.dateTimeBuilder_.build();
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
                if (other instanceof OM0Report) {
                    return mergeFrom((OM0Report) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OM0Report other) {
                RepeatedFieldBuilderV3<OM0TrackData, Builder, OM0TrackDataOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != OM0Report.getDefaultInstance()) {
                    if (other.hasSosSignal()) {
                        setSosSignal(other.getSosSignal());
                    }
                    if (other.hasWakeUp()) {
                        setWakeUp(other.getWakeUp());
                    }
                    if (this.trackDataBuilder_ == null) {
                        if (!other.trackData_.isEmpty()) {
                            if (this.trackData_.isEmpty()) {
                                this.trackData_ = other.trackData_;
                                this.bitField0_ &= -5;
                            } else {
                                ensureTrackDataIsMutable();
                                this.trackData_.addAll(other.trackData_);
                            }
                            onChanged();
                        }
                    } else if (!other.trackData_.isEmpty()) {
                        if (this.trackDataBuilder_.isEmpty()) {
                            this.trackDataBuilder_.dispose();
                            this.trackDataBuilder_ = null;
                            this.trackData_ = other.trackData_;
                            this.bitField0_ &= -5;
                            if (OM0Report.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getTrackDataFieldBuilder();
                            }
                            this.trackDataBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.trackDataBuilder_.addAllMessages(other.trackData_);
                        }
                    }
                    if (other.hasBattery()) {
                        mergeBattery(other.getBattery());
                    }
                    if (other.hasHealth()) {
                        mergeHealth(other.getHealth());
                    }
                    if (other.hasDateTime()) {
                        mergeDateTime(other.getDateTime());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getTrackDataCount(); i++) {
                    if (!getTrackData(i).isInitialized()) {
                        return false;
                    }
                }
                if (hasBattery() && !getBattery().isInitialized()) {
                    return false;
                }
                if (hasHealth() && !getHealth().isInitialized()) {
                    return false;
                }
                if (!hasDateTime() || getDateTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                OM0Report parsedMessage = null;
                try {
                    OM0Report parsedMessage2 = (OM0Report) OM0Report.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    OM0Report parsedMessage3 = (OM0Report) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSosSignal() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getSosSignal() {
                return this.sosSignal_;
            }

            public Builder setSosSignal(boolean value) {
                this.bitField0_ |= 1;
                this.sosSignal_ = value;
                onChanged();
                return this;
            }

            public Builder clearSosSignal() {
                this.bitField0_ &= -2;
                this.sosSignal_ = false;
                onChanged();
                return this;
            }

            public boolean hasWakeUp() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getWakeUp() {
                return this.wakeUp_;
            }

            public Builder setWakeUp(boolean value) {
                this.bitField0_ |= 2;
                this.wakeUp_ = value;
                onChanged();
                return this;
            }

            public Builder clearWakeUp() {
                this.bitField0_ &= -3;
                this.wakeUp_ = false;
                onChanged();
                return this;
            }

            private void ensureTrackDataIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.trackData_ = new ArrayList(this.trackData_);
                    this.bitField0_ |= 4;
                }
            }

            public List<OM0TrackData> getTrackDataList() {
                if (this.trackDataBuilder_ == null) {
                    return Collections.unmodifiableList(this.trackData_);
                }
                return this.trackDataBuilder_.getMessageList();
            }

            public int getTrackDataCount() {
                if (this.trackDataBuilder_ == null) {
                    return this.trackData_.size();
                }
                return this.trackDataBuilder_.getCount();
            }

            public OM0TrackData getTrackData(int index) {
                if (this.trackDataBuilder_ == null) {
                    return (OM0TrackData) this.trackData_.get(index);
                }
                return (OM0TrackData) this.trackDataBuilder_.getMessage(index);
            }

            public Builder setTrackData(int index, OM0TrackData value) {
                if (this.trackDataBuilder_ != null) {
                    this.trackDataBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureTrackDataIsMutable();
                    this.trackData_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setTrackData(int index, Builder builderForValue) {
                if (this.trackDataBuilder_ == null) {
                    ensureTrackDataIsMutable();
                    this.trackData_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.trackDataBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addTrackData(OM0TrackData value) {
                if (this.trackDataBuilder_ != null) {
                    this.trackDataBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureTrackDataIsMutable();
                    this.trackData_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addTrackData(int index, OM0TrackData value) {
                if (this.trackDataBuilder_ != null) {
                    this.trackDataBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureTrackDataIsMutable();
                    this.trackData_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addTrackData(Builder builderForValue) {
                if (this.trackDataBuilder_ == null) {
                    ensureTrackDataIsMutable();
                    this.trackData_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.trackDataBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addTrackData(int index, Builder builderForValue) {
                if (this.trackDataBuilder_ == null) {
                    ensureTrackDataIsMutable();
                    this.trackData_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.trackDataBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllTrackData(Iterable<? extends OM0TrackData> values) {
                if (this.trackDataBuilder_ == null) {
                    ensureTrackDataIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.trackData_);
                    onChanged();
                } else {
                    this.trackDataBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearTrackData() {
                if (this.trackDataBuilder_ == null) {
                    this.trackData_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.trackDataBuilder_.clear();
                }
                return this;
            }

            public Builder removeTrackData(int index) {
                if (this.trackDataBuilder_ == null) {
                    ensureTrackDataIsMutable();
                    this.trackData_.remove(index);
                    onChanged();
                } else {
                    this.trackDataBuilder_.remove(index);
                }
                return this;
            }

            public Builder getTrackDataBuilder(int index) {
                return (Builder) getTrackDataFieldBuilder().getBuilder(index);
            }

            public OM0TrackDataOrBuilder getTrackDataOrBuilder(int index) {
                if (this.trackDataBuilder_ == null) {
                    return (OM0TrackDataOrBuilder) this.trackData_.get(index);
                }
                return (OM0TrackDataOrBuilder) this.trackDataBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends OM0TrackDataOrBuilder> getTrackDataOrBuilderList() {
                if (this.trackDataBuilder_ != null) {
                    return this.trackDataBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.trackData_);
            }

            public Builder addTrackDataBuilder() {
                return (Builder) getTrackDataFieldBuilder().addBuilder(OM0TrackData.getDefaultInstance());
            }

            public Builder addTrackDataBuilder(int index) {
                return (Builder) getTrackDataFieldBuilder().addBuilder(index, OM0TrackData.getDefaultInstance());
            }

            public List<Builder> getTrackDataBuilderList() {
                return getTrackDataFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<OM0TrackData, Builder, OM0TrackDataOrBuilder> getTrackDataFieldBuilder() {
                if (this.trackDataBuilder_ == null) {
                    this.trackDataBuilder_ = new RepeatedFieldBuilderV3<>(this.trackData_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.trackData_ = null;
                }
                return this.trackDataBuilder_;
            }

            public boolean hasBattery() {
                return (this.bitField0_ & 8) == 8;
            }

            public RtBattery getBattery() {
                if (this.batteryBuilder_ == null) {
                    return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
                }
                return (RtBattery) this.batteryBuilder_.getMessage();
            }

            public Builder setBattery(RtBattery value) {
                if (this.batteryBuilder_ != null) {
                    this.batteryBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.battery_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setBattery(com.iwown.ble_module.proto.base.RealtimeData.RtBattery.Builder builderForValue) {
                if (this.batteryBuilder_ == null) {
                    this.battery_ = builderForValue.build();
                    onChanged();
                } else {
                    this.batteryBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeBattery(RtBattery value) {
                if (this.batteryBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.battery_ == null || this.battery_ == RtBattery.getDefaultInstance()) {
                        this.battery_ = value;
                    } else {
                        this.battery_ = RtBattery.newBuilder(this.battery_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.batteryBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearBattery() {
                if (this.batteryBuilder_ == null) {
                    this.battery_ = null;
                    onChanged();
                } else {
                    this.batteryBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtBattery.Builder getBatteryBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.RtBattery.Builder) getBatteryFieldBuilder().getBuilder();
            }

            public RtBatteryOrBuilder getBatteryOrBuilder() {
                if (this.batteryBuilder_ != null) {
                    return (RtBatteryOrBuilder) this.batteryBuilder_.getMessageOrBuilder();
                }
                return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
            }

            private SingleFieldBuilderV3<RtBattery, com.iwown.ble_module.proto.base.RealtimeData.RtBattery.Builder, RtBatteryOrBuilder> getBatteryFieldBuilder() {
                if (this.batteryBuilder_ == null) {
                    this.batteryBuilder_ = new SingleFieldBuilderV3<>(getBattery(), getParentForChildren(), isClean());
                    this.battery_ = null;
                }
                return this.batteryBuilder_;
            }

            public boolean hasHealth() {
                return (this.bitField0_ & 16) == 16;
            }

            public RtHealth getHealth() {
                if (this.healthBuilder_ == null) {
                    return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
                }
                return (RtHealth) this.healthBuilder_.getMessage();
            }

            public Builder setHealth(RtHealth value) {
                if (this.healthBuilder_ != null) {
                    this.healthBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.health_ = value;
                    onChanged();
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setHealth(com.iwown.ble_module.proto.base.RealtimeData.RtHealth.Builder builderForValue) {
                if (this.healthBuilder_ == null) {
                    this.health_ = builderForValue.build();
                    onChanged();
                } else {
                    this.healthBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder mergeHealth(RtHealth value) {
                if (this.healthBuilder_ == null) {
                    if ((this.bitField0_ & 16) != 16 || this.health_ == null || this.health_ == RtHealth.getDefaultInstance()) {
                        this.health_ = value;
                    } else {
                        this.health_ = RtHealth.newBuilder(this.health_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.healthBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder clearHealth() {
                if (this.healthBuilder_ == null) {
                    this.health_ = null;
                    onChanged();
                } else {
                    this.healthBuilder_.clear();
                }
                this.bitField0_ &= -17;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtHealth.Builder getHealthBuilder() {
                this.bitField0_ |= 16;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.RtHealth.Builder) getHealthFieldBuilder().getBuilder();
            }

            public RtHealthOrBuilder getHealthOrBuilder() {
                if (this.healthBuilder_ != null) {
                    return (RtHealthOrBuilder) this.healthBuilder_.getMessageOrBuilder();
                }
                return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
            }

            private SingleFieldBuilderV3<RtHealth, com.iwown.ble_module.proto.base.RealtimeData.RtHealth.Builder, RtHealthOrBuilder> getHealthFieldBuilder() {
                if (this.healthBuilder_ == null) {
                    this.healthBuilder_ = new SingleFieldBuilderV3<>(getHealth(), getParentForChildren(), isClean());
                    this.health_ = null;
                }
                return this.healthBuilder_;
            }

            public boolean hasDateTime() {
                return (this.bitField0_ & 32) == 32;
            }

            public DateTime getDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
                }
                return (DateTime) this.dateTimeBuilder_.getMessage();
            }

            public Builder setDateTime(DateTime value) {
                if (this.dateTimeBuilder_ != null) {
                    this.dateTimeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.dateTime_ = value;
                    onChanged();
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder setDateTime(com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder builderForValue) {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dateTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeDateTime(DateTime value) {
                if (this.dateTimeBuilder_ == null) {
                    if ((this.bitField0_ & 32) != 32 || this.dateTime_ == null || this.dateTime_ == DateTime.getDefaultInstance()) {
                        this.dateTime_ = value;
                    } else {
                        this.dateTime_ = DateTime.newBuilder(this.dateTime_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.dateTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder clearDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                    onChanged();
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder getDateTimeBuilder() {
                this.bitField0_ |= 32;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder) getDateTimeFieldBuilder().getBuilder();
            }

            public DateTimeOrBuilder getDateTimeOrBuilder() {
                if (this.dateTimeBuilder_ != null) {
                    return (DateTimeOrBuilder) this.dateTimeBuilder_.getMessageOrBuilder();
                }
                return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
            }

            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> getDateTimeFieldBuilder() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTimeBuilder_ = new SingleFieldBuilderV3<>(getDateTime(), getParentForChildren(), isClean());
                    this.dateTime_ = null;
                }
                return this.dateTimeBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private OM0Report(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private OM0Report() {
            this.memoizedIsInitialized = -1;
            this.sosSignal_ = false;
            this.wakeUp_ = false;
            this.trackData_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OM0Report(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8:
                            this.bitField0_ |= 1;
                            this.sosSignal_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.wakeUp_ = input.readBool();
                            break;
                        case 26:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.trackData_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.trackData_.add(input.readMessage(OM0TrackData.PARSER, extensionRegistry));
                            break;
                        case 34:
                            com.iwown.ble_module.proto.base.RealtimeData.RtBattery.Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.battery_.toBuilder();
                            }
                            this.battery_ = (RtBattery) input.readMessage(RtBattery.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.battery_);
                                this.battery_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 42:
                            com.iwown.ble_module.proto.base.RealtimeData.RtHealth.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder2 = this.health_.toBuilder();
                            }
                            this.health_ = (RtHealth) input.readMessage(RtHealth.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.health_);
                                this.health_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        case 50:
                            com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder subBuilder3 = null;
                            if ((this.bitField0_ & 16) == 16) {
                                subBuilder3 = this.dateTime_.toBuilder();
                            }
                            this.dateTime_ = (DateTime) input.readMessage(DateTime.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.dateTime_);
                                this.dateTime_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 16;
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
                    if ((mutable_bitField0_ & 4) == 4) {
                        this.trackData_ = Collections.unmodifiableList(this.trackData_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.trackData_ = Collections.unmodifiableList(this.trackData_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return Om0Command.internal_static_OM0Report_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Om0Command.internal_static_OM0Report_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0Report.class, Builder.class);
        }

        public boolean hasSosSignal() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getSosSignal() {
            return this.sosSignal_;
        }

        public boolean hasWakeUp() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getWakeUp() {
            return this.wakeUp_;
        }

        public List<OM0TrackData> getTrackDataList() {
            return this.trackData_;
        }

        public List<? extends OM0TrackDataOrBuilder> getTrackDataOrBuilderList() {
            return this.trackData_;
        }

        public int getTrackDataCount() {
            return this.trackData_.size();
        }

        public OM0TrackData getTrackData(int index) {
            return (OM0TrackData) this.trackData_.get(index);
        }

        public OM0TrackDataOrBuilder getTrackDataOrBuilder(int index) {
            return (OM0TrackDataOrBuilder) this.trackData_.get(index);
        }

        public boolean hasBattery() {
            return (this.bitField0_ & 4) == 4;
        }

        public RtBattery getBattery() {
            return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
        }

        public RtBatteryOrBuilder getBatteryOrBuilder() {
            return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
        }

        public boolean hasHealth() {
            return (this.bitField0_ & 8) == 8;
        }

        public RtHealth getHealth() {
            return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
        }

        public RtHealthOrBuilder getHealthOrBuilder() {
            return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
        }

        public boolean hasDateTime() {
            return (this.bitField0_ & 16) == 16;
        }

        public DateTime getDateTime() {
            return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
        }

        public DateTimeOrBuilder getDateTimeOrBuilder() {
            return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < getTrackDataCount(); i++) {
                if (!getTrackData(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (hasBattery() && !getBattery().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasHealth() && !getHealth().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDateTime() || getDateTime().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.sosSignal_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.wakeUp_);
            }
            for (int i = 0; i < this.trackData_.size(); i++) {
                output.writeMessage(3, (MessageLite) this.trackData_.get(i));
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(4, getBattery());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(5, getHealth());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeMessage(6, getDateTime());
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.sosSignal_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.wakeUp_);
            }
            for (int i = 0; i < this.trackData_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(3, (MessageLite) this.trackData_.get(i));
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(4, getBattery());
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeMessageSize(5, getHealth());
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeMessageSize(6, getDateTime());
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
            if (!(obj instanceof OM0Report)) {
                return super.equals(obj);
            }
            OM0Report other = (OM0Report) obj;
            boolean result7 = 1 != 0 && hasSosSignal() == other.hasSosSignal();
            if (hasSosSignal()) {
                result7 = result7 && getSosSignal() == other.getSosSignal();
            }
            if (!result7 || hasWakeUp() != other.hasWakeUp()) {
                result = false;
            } else {
                result = true;
            }
            if (hasWakeUp()) {
                if (!result || getWakeUp() != other.getWakeUp()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || !getTrackDataList().equals(other.getTrackDataList())) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (!result2 || hasBattery() != other.hasBattery()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasBattery()) {
                if (!result3 || !getBattery().equals(other.getBattery())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasHealth() != other.hasHealth()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasHealth()) {
                if (!result4 || !getHealth().equals(other.getHealth())) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasDateTime() != other.hasDateTime()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasDateTime()) {
                if (!result5 || !getDateTime().equals(other.getDateTime())) {
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
            if (hasSosSignal()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getSosSignal());
            }
            if (hasWakeUp()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getWakeUp());
            }
            if (getTrackDataCount() > 0) {
                hash = (((hash * 37) + 3) * 53) + getTrackDataList().hashCode();
            }
            if (hasBattery()) {
                hash = (((hash * 37) + 4) * 53) + getBattery().hashCode();
            }
            if (hasHealth()) {
                hash = (((hash * 37) + 5) * 53) + getHealth().hashCode();
            }
            if (hasDateTime()) {
                hash = (((hash * 37) + 6) * 53) + getDateTime().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static OM0Report parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OM0Report) PARSER.parseFrom(data);
        }

        public static OM0Report parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0Report) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0Report parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OM0Report) PARSER.parseFrom(data);
        }

        public static OM0Report parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0Report) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0Report parseFrom(InputStream input) throws IOException {
            return (OM0Report) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0Report parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0Report) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0Report parseDelimitedFrom(InputStream input) throws IOException {
            return (OM0Report) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static OM0Report parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0Report) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0Report parseFrom(CodedInputStream input) throws IOException {
            return (OM0Report) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0Report parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0Report) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OM0Report prototype) {
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

        public static OM0Report getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OM0Report> parser() {
            return PARSER;
        }

        public Parser<OM0Report> getParserForType() {
            return PARSER;
        }

        public OM0Report getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class OM0SleepData extends GeneratedMessageV3 implements OM0SleepDataOrBuilder {
        public static final int DATE_FIELD_NUMBER = 6;
        public static final int DEEPSLEEP_FIELD_NUMBER = 3;
        private static final OM0SleepData DEFAULT_INSTANCE = new OM0SleepData();
        public static final int DURATION_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<OM0SleepData> PARSER = new AbstractParser<OM0SleepData>() {
            public OM0SleepData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OM0SleepData(input, extensionRegistry);
            }
        };
        public static final int SCORE_FIELD_NUMBER = 1;
        public static final int SOMNOLENCE_FIELD_NUMBER = 4;
        public static final int WAKEFULNESS_FIELD_NUMBER = 5;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public DateTime date_;
        /* access modifiers changed from: private */
        public int deepsleep_;
        /* access modifiers changed from: private */
        public int duration_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int score_;
        /* access modifiers changed from: private */
        public int somnolence_;
        /* access modifiers changed from: private */
        public int wakefulness_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OM0SleepDataOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> dateBuilder_;
            private DateTime date_;
            private int deepsleep_;
            private int duration_;
            private int score_;
            private int somnolence_;
            private int wakefulness_;

            public static final Descriptor getDescriptor() {
                return Om0Command.internal_static_OM0SleepData_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Om0Command.internal_static_OM0SleepData_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0SleepData.class, Builder.class);
            }

            private Builder() {
                this.date_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.date_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (OM0SleepData.alwaysUseFieldBuilders) {
                    getDateFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.score_ = 0;
                this.bitField0_ &= -2;
                this.duration_ = 0;
                this.bitField0_ &= -3;
                this.deepsleep_ = 0;
                this.bitField0_ &= -5;
                this.somnolence_ = 0;
                this.bitField0_ &= -9;
                this.wakefulness_ = 0;
                this.bitField0_ &= -17;
                if (this.dateBuilder_ == null) {
                    this.date_ = null;
                } else {
                    this.dateBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Om0Command.internal_static_OM0SleepData_descriptor;
            }

            public OM0SleepData getDefaultInstanceForType() {
                return OM0SleepData.getDefaultInstance();
            }

            public OM0SleepData build() {
                OM0SleepData result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public OM0SleepData buildPartial() {
                OM0SleepData result = new OM0SleepData((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.score_ = this.score_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.duration_ = this.duration_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.deepsleep_ = this.deepsleep_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.somnolence_ = this.somnolence_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.wakefulness_ = this.wakefulness_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                if (this.dateBuilder_ == null) {
                    result.date_ = this.date_;
                } else {
                    result.date_ = (DateTime) this.dateBuilder_.build();
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
                if (other instanceof OM0SleepData) {
                    return mergeFrom((OM0SleepData) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OM0SleepData other) {
                if (other != OM0SleepData.getDefaultInstance()) {
                    if (other.hasScore()) {
                        setScore(other.getScore());
                    }
                    if (other.hasDuration()) {
                        setDuration(other.getDuration());
                    }
                    if (other.hasDeepsleep()) {
                        setDeepsleep(other.getDeepsleep());
                    }
                    if (other.hasSomnolence()) {
                        setSomnolence(other.getSomnolence());
                    }
                    if (other.hasWakefulness()) {
                        setWakefulness(other.getWakefulness());
                    }
                    if (other.hasDate()) {
                        mergeDate(other.getDate());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasScore() && hasDuration() && hasDeepsleep() && hasSomnolence() && hasWakefulness() && hasDate() && getDate().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                OM0SleepData parsedMessage = null;
                try {
                    OM0SleepData parsedMessage2 = (OM0SleepData) OM0SleepData.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    OM0SleepData parsedMessage3 = (OM0SleepData) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasScore() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getScore() {
                return this.score_;
            }

            public Builder setScore(int value) {
                this.bitField0_ |= 1;
                this.score_ = value;
                onChanged();
                return this;
            }

            public Builder clearScore() {
                this.bitField0_ &= -2;
                this.score_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDuration() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getDuration() {
                return this.duration_;
            }

            public Builder setDuration(int value) {
                this.bitField0_ |= 2;
                this.duration_ = value;
                onChanged();
                return this;
            }

            public Builder clearDuration() {
                this.bitField0_ &= -3;
                this.duration_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDeepsleep() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getDeepsleep() {
                return this.deepsleep_;
            }

            public Builder setDeepsleep(int value) {
                this.bitField0_ |= 4;
                this.deepsleep_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeepsleep() {
                this.bitField0_ &= -5;
                this.deepsleep_ = 0;
                onChanged();
                return this;
            }

            public boolean hasSomnolence() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getSomnolence() {
                return this.somnolence_;
            }

            public Builder setSomnolence(int value) {
                this.bitField0_ |= 8;
                this.somnolence_ = value;
                onChanged();
                return this;
            }

            public Builder clearSomnolence() {
                this.bitField0_ &= -9;
                this.somnolence_ = 0;
                onChanged();
                return this;
            }

            public boolean hasWakefulness() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getWakefulness() {
                return this.wakefulness_;
            }

            public Builder setWakefulness(int value) {
                this.bitField0_ |= 16;
                this.wakefulness_ = value;
                onChanged();
                return this;
            }

            public Builder clearWakefulness() {
                this.bitField0_ &= -17;
                this.wakefulness_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDate() {
                return (this.bitField0_ & 32) == 32;
            }

            public DateTime getDate() {
                if (this.dateBuilder_ == null) {
                    return this.date_ == null ? DateTime.getDefaultInstance() : this.date_;
                }
                return (DateTime) this.dateBuilder_.getMessage();
            }

            public Builder setDate(DateTime value) {
                if (this.dateBuilder_ != null) {
                    this.dateBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.date_ = value;
                    onChanged();
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder setDate(com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder builderForValue) {
                if (this.dateBuilder_ == null) {
                    this.date_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dateBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeDate(DateTime value) {
                if (this.dateBuilder_ == null) {
                    if ((this.bitField0_ & 32) != 32 || this.date_ == null || this.date_ == DateTime.getDefaultInstance()) {
                        this.date_ = value;
                    } else {
                        this.date_ = DateTime.newBuilder(this.date_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.dateBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder clearDate() {
                if (this.dateBuilder_ == null) {
                    this.date_ = null;
                    onChanged();
                } else {
                    this.dateBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder getDateBuilder() {
                this.bitField0_ |= 32;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder) getDateFieldBuilder().getBuilder();
            }

            public DateTimeOrBuilder getDateOrBuilder() {
                if (this.dateBuilder_ != null) {
                    return (DateTimeOrBuilder) this.dateBuilder_.getMessageOrBuilder();
                }
                return this.date_ == null ? DateTime.getDefaultInstance() : this.date_;
            }

            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> getDateFieldBuilder() {
                if (this.dateBuilder_ == null) {
                    this.dateBuilder_ = new SingleFieldBuilderV3<>(getDate(), getParentForChildren(), isClean());
                    this.date_ = null;
                }
                return this.dateBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private OM0SleepData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private OM0SleepData() {
            this.memoizedIsInitialized = -1;
            this.score_ = 0;
            this.duration_ = 0;
            this.deepsleep_ = 0;
            this.somnolence_ = 0;
            this.wakefulness_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OM0SleepData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.score_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.duration_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.deepsleep_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.somnolence_ = input.readFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.wakefulness_ = input.readFixed32();
                            break;
                        case 50:
                            com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 32) == 32) {
                                subBuilder = this.date_.toBuilder();
                            }
                            this.date_ = (DateTime) input.readMessage(DateTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.date_);
                                this.date_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 32;
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
            return Om0Command.internal_static_OM0SleepData_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Om0Command.internal_static_OM0SleepData_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0SleepData.class, Builder.class);
        }

        public boolean hasScore() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getScore() {
            return this.score_;
        }

        public boolean hasDuration() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getDuration() {
            return this.duration_;
        }

        public boolean hasDeepsleep() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getDeepsleep() {
            return this.deepsleep_;
        }

        public boolean hasSomnolence() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getSomnolence() {
            return this.somnolence_;
        }

        public boolean hasWakefulness() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getWakefulness() {
            return this.wakefulness_;
        }

        public boolean hasDate() {
            return (this.bitField0_ & 32) == 32;
        }

        public DateTime getDate() {
            return this.date_ == null ? DateTime.getDefaultInstance() : this.date_;
        }

        public DateTimeOrBuilder getDateOrBuilder() {
            return this.date_ == null ? DateTime.getDefaultInstance() : this.date_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasScore()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDuration()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDeepsleep()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSomnolence()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasWakefulness()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDate()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getDate().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.score_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.duration_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.deepsleep_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.somnolence_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.wakefulness_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeMessage(6, getDate());
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.score_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.duration_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.deepsleep_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.somnolence_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.wakefulness_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeMessageSize(6, getDate());
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
            if (!(obj instanceof OM0SleepData)) {
                return super.equals(obj);
            }
            OM0SleepData other = (OM0SleepData) obj;
            boolean result7 = 1 != 0 && hasScore() == other.hasScore();
            if (hasScore()) {
                result7 = result7 && getScore() == other.getScore();
            }
            if (!result7 || hasDuration() != other.hasDuration()) {
                result = false;
            } else {
                result = true;
            }
            if (hasDuration()) {
                if (!result || getDuration() != other.getDuration()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasDeepsleep() != other.hasDeepsleep()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasDeepsleep()) {
                if (!result2 || getDeepsleep() != other.getDeepsleep()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasSomnolence() != other.hasSomnolence()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasSomnolence()) {
                if (!result3 || getSomnolence() != other.getSomnolence()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasWakefulness() != other.hasWakefulness()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasWakefulness()) {
                if (!result4 || getWakefulness() != other.getWakefulness()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasDate() != other.hasDate()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasDate()) {
                if (!result5 || !getDate().equals(other.getDate())) {
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
            if (hasScore()) {
                hash = (((hash * 37) + 1) * 53) + getScore();
            }
            if (hasDuration()) {
                hash = (((hash * 37) + 2) * 53) + getDuration();
            }
            if (hasDeepsleep()) {
                hash = (((hash * 37) + 3) * 53) + getDeepsleep();
            }
            if (hasSomnolence()) {
                hash = (((hash * 37) + 4) * 53) + getSomnolence();
            }
            if (hasWakefulness()) {
                hash = (((hash * 37) + 5) * 53) + getWakefulness();
            }
            if (hasDate()) {
                hash = (((hash * 37) + 6) * 53) + getDate().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static OM0SleepData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OM0SleepData) PARSER.parseFrom(data);
        }

        public static OM0SleepData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0SleepData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0SleepData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OM0SleepData) PARSER.parseFrom(data);
        }

        public static OM0SleepData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0SleepData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0SleepData parseFrom(InputStream input) throws IOException {
            return (OM0SleepData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0SleepData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0SleepData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0SleepData parseDelimitedFrom(InputStream input) throws IOException {
            return (OM0SleepData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static OM0SleepData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0SleepData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0SleepData parseFrom(CodedInputStream input) throws IOException {
            return (OM0SleepData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0SleepData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0SleepData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OM0SleepData prototype) {
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

        public static OM0SleepData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OM0SleepData> parser() {
            return PARSER;
        }

        public Parser<OM0SleepData> getParserForType() {
            return PARSER;
        }

        public OM0SleepData getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class OM0TrackConf extends GeneratedMessageV3 implements OM0TrackConfOrBuilder {
        public static final int AUTO_RUN_FIELD_NUMBER = 2;
        private static final OM0TrackConf DEFAULT_INSTANCE = new OM0TrackConf();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int INTERVAL_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<OM0TrackConf> PARSER = new AbstractParser<OM0TrackConf>() {
            public OM0TrackConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OM0TrackConf(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public boolean autoRun_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public int interval_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OM0TrackConfOrBuilder {
            private boolean autoRun_;
            private int bitField0_;
            private int hash_;
            private int interval_;

            public static final Descriptor getDescriptor() {
                return Om0Command.internal_static_OM0TrackConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Om0Command.internal_static_OM0TrackConf_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0TrackConf.class, Builder.class);
            }

            private Builder() {
                this.interval_ = 30;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.interval_ = 30;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (OM0TrackConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.autoRun_ = false;
                this.bitField0_ &= -3;
                this.interval_ = 30;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Om0Command.internal_static_OM0TrackConf_descriptor;
            }

            public OM0TrackConf getDefaultInstanceForType() {
                return OM0TrackConf.getDefaultInstance();
            }

            public OM0TrackConf build() {
                OM0TrackConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public OM0TrackConf buildPartial() {
                OM0TrackConf result = new OM0TrackConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.autoRun_ = this.autoRun_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.interval_ = this.interval_;
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
                if (other instanceof OM0TrackConf) {
                    return mergeFrom((OM0TrackConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OM0TrackConf other) {
                if (other != OM0TrackConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasAutoRun()) {
                        setAutoRun(other.getAutoRun());
                    }
                    if (other.hasInterval()) {
                        setInterval(other.getInterval());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasAutoRun() && hasInterval()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                OM0TrackConf parsedMessage = null;
                try {
                    OM0TrackConf parsedMessage2 = (OM0TrackConf) OM0TrackConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    OM0TrackConf parsedMessage3 = (OM0TrackConf) e.getUnfinishedMessage();
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

            public boolean hasAutoRun() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getAutoRun() {
                return this.autoRun_;
            }

            public Builder setAutoRun(boolean value) {
                this.bitField0_ |= 2;
                this.autoRun_ = value;
                onChanged();
                return this;
            }

            public Builder clearAutoRun() {
                this.bitField0_ &= -3;
                this.autoRun_ = false;
                onChanged();
                return this;
            }

            public boolean hasInterval() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getInterval() {
                return this.interval_;
            }

            public Builder setInterval(int value) {
                this.bitField0_ |= 4;
                this.interval_ = value;
                onChanged();
                return this;
            }

            public Builder clearInterval() {
                this.bitField0_ &= -5;
                this.interval_ = 30;
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

        private OM0TrackConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private OM0TrackConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.autoRun_ = false;
            this.interval_ = 30;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OM0TrackConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 2;
                            this.autoRun_ = input.readBool();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.interval_ = input.readFixed32();
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
            return Om0Command.internal_static_OM0TrackConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Om0Command.internal_static_OM0TrackConf_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0TrackConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasAutoRun() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getAutoRun() {
            return this.autoRun_;
        }

        public boolean hasInterval() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getInterval() {
            return this.interval_;
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
            } else if (!hasAutoRun()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasInterval()) {
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
                output.writeBool(2, this.autoRun_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.interval_);
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
                size2 += CodedOutputStream.computeBoolSize(2, this.autoRun_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.interval_);
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
            if (!(obj instanceof OM0TrackConf)) {
                return super.equals(obj);
            }
            OM0TrackConf other = (OM0TrackConf) obj;
            boolean result4 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result4 = result4 && getHash() == other.getHash();
            }
            if (!result4 || hasAutoRun() != other.hasAutoRun()) {
                result = false;
            } else {
                result = true;
            }
            if (hasAutoRun()) {
                if (!result || getAutoRun() != other.getAutoRun()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasInterval() != other.hasInterval()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasInterval()) {
                if (!result2 || getInterval() != other.getInterval()) {
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
            if (hasAutoRun()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getAutoRun());
            }
            if (hasInterval()) {
                hash = (((hash * 37) + 3) * 53) + getInterval();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static OM0TrackConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OM0TrackConf) PARSER.parseFrom(data);
        }

        public static OM0TrackConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0TrackConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0TrackConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OM0TrackConf) PARSER.parseFrom(data);
        }

        public static OM0TrackConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0TrackConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0TrackConf parseFrom(InputStream input) throws IOException {
            return (OM0TrackConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0TrackConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0TrackConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0TrackConf parseDelimitedFrom(InputStream input) throws IOException {
            return (OM0TrackConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static OM0TrackConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0TrackConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0TrackConf parseFrom(CodedInputStream input) throws IOException {
            return (OM0TrackConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0TrackConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0TrackConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OM0TrackConf prototype) {
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

        public static OM0TrackConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OM0TrackConf> parser() {
            return PARSER;
        }

        public Parser<OM0TrackConf> getParserForType() {
            return PARSER;
        }

        public OM0TrackConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class OM0TrackData extends GeneratedMessageV3 implements OM0TrackDataOrBuilder {
        private static final OM0TrackData DEFAULT_INSTANCE = new OM0TrackData();
        public static final int GNSS_FIELD_NUMBER = 2;
        public static final int HR_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<OM0TrackData> PARSER = new AbstractParser<OM0TrackData>() {
            public OM0TrackData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OM0TrackData(input, extensionRegistry);
            }
        };
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public RtGNSS gnss_;
        /* access modifiers changed from: private */
        public int hr_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public DateTime time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OM0TrackDataOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<RtGNSS, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder, RtGNSSOrBuilder> gnssBuilder_;
            private RtGNSS gnss_;
            private int hr_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> timeBuilder_;
            private DateTime time_;

            public static final Descriptor getDescriptor() {
                return Om0Command.internal_static_OM0TrackData_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Om0Command.internal_static_OM0TrackData_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0TrackData.class, Builder.class);
            }

            private Builder() {
                this.time_ = null;
                this.gnss_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = null;
                this.gnss_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (OM0TrackData.alwaysUseFieldBuilders) {
                    getTimeFieldBuilder();
                    getGnssFieldBuilder();
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
                if (this.gnssBuilder_ == null) {
                    this.gnss_ = null;
                } else {
                    this.gnssBuilder_.clear();
                }
                this.bitField0_ &= -3;
                this.hr_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Om0Command.internal_static_OM0TrackData_descriptor;
            }

            public OM0TrackData getDefaultInstanceForType() {
                return OM0TrackData.getDefaultInstance();
            }

            public OM0TrackData build() {
                OM0TrackData result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public OM0TrackData buildPartial() {
                OM0TrackData result = new OM0TrackData((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeBuilder_ == null) {
                    result.time_ = this.time_;
                } else {
                    result.time_ = (DateTime) this.timeBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.gnssBuilder_ == null) {
                    result.gnss_ = this.gnss_;
                } else {
                    result.gnss_ = (RtGNSS) this.gnssBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.hr_ = this.hr_;
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
                if (other instanceof OM0TrackData) {
                    return mergeFrom((OM0TrackData) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OM0TrackData other) {
                if (other != OM0TrackData.getDefaultInstance()) {
                    if (other.hasTime()) {
                        mergeTime(other.getTime());
                    }
                    if (other.hasGnss()) {
                        mergeGnss(other.getGnss());
                    }
                    if (other.hasHr()) {
                        setHr(other.getHr());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasTime() || !getTime().isInitialized()) {
                    return false;
                }
                if (!hasGnss() || getGnss().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                OM0TrackData parsedMessage = null;
                try {
                    OM0TrackData parsedMessage2 = (OM0TrackData) OM0TrackData.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    OM0TrackData parsedMessage3 = (OM0TrackData) e.getUnfinishedMessage();
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

            public DateTime getTime() {
                if (this.timeBuilder_ == null) {
                    return this.time_ == null ? DateTime.getDefaultInstance() : this.time_;
                }
                return (DateTime) this.timeBuilder_.getMessage();
            }

            public Builder setTime(DateTime value) {
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

            public Builder setTime(com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder builderForValue) {
                if (this.timeBuilder_ == null) {
                    this.time_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTime(DateTime value) {
                if (this.timeBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.time_ == null || this.time_ == DateTime.getDefaultInstance()) {
                        this.time_ = value;
                    } else {
                        this.time_ = DateTime.newBuilder(this.time_).mergeFrom(value).buildPartial();
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

            public com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder getTimeBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder) getTimeFieldBuilder().getBuilder();
            }

            public DateTimeOrBuilder getTimeOrBuilder() {
                if (this.timeBuilder_ != null) {
                    return (DateTimeOrBuilder) this.timeBuilder_.getMessageOrBuilder();
                }
                return this.time_ == null ? DateTime.getDefaultInstance() : this.time_;
            }

            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> getTimeFieldBuilder() {
                if (this.timeBuilder_ == null) {
                    this.timeBuilder_ = new SingleFieldBuilderV3<>(getTime(), getParentForChildren(), isClean());
                    this.time_ = null;
                }
                return this.timeBuilder_;
            }

            public boolean hasGnss() {
                return (this.bitField0_ & 2) == 2;
            }

            public RtGNSS getGnss() {
                if (this.gnssBuilder_ == null) {
                    return this.gnss_ == null ? RtGNSS.getDefaultInstance() : this.gnss_;
                }
                return (RtGNSS) this.gnssBuilder_.getMessage();
            }

            public Builder setGnss(RtGNSS value) {
                if (this.gnssBuilder_ != null) {
                    this.gnssBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.gnss_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setGnss(com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder builderForValue) {
                if (this.gnssBuilder_ == null) {
                    this.gnss_ = builderForValue.build();
                    onChanged();
                } else {
                    this.gnssBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeGnss(RtGNSS value) {
                if (this.gnssBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.gnss_ == null || this.gnss_ == RtGNSS.getDefaultInstance()) {
                        this.gnss_ = value;
                    } else {
                        this.gnss_ = RtGNSS.newBuilder(this.gnss_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.gnssBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearGnss() {
                if (this.gnssBuilder_ == null) {
                    this.gnss_ = null;
                    onChanged();
                } else {
                    this.gnssBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder getGnssBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder) getGnssFieldBuilder().getBuilder();
            }

            public RtGNSSOrBuilder getGnssOrBuilder() {
                if (this.gnssBuilder_ != null) {
                    return (RtGNSSOrBuilder) this.gnssBuilder_.getMessageOrBuilder();
                }
                return this.gnss_ == null ? RtGNSS.getDefaultInstance() : this.gnss_;
            }

            private SingleFieldBuilderV3<RtGNSS, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder, RtGNSSOrBuilder> getGnssFieldBuilder() {
                if (this.gnssBuilder_ == null) {
                    this.gnssBuilder_ = new SingleFieldBuilderV3<>(getGnss(), getParentForChildren(), isClean());
                    this.gnss_ = null;
                }
                return this.gnssBuilder_;
            }

            public boolean hasHr() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getHr() {
                return this.hr_;
            }

            public Builder setHr(int value) {
                this.bitField0_ |= 4;
                this.hr_ = value;
                onChanged();
                return this;
            }

            public Builder clearHr() {
                this.bitField0_ &= -5;
                this.hr_ = 0;
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

        private OM0TrackData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private OM0TrackData() {
            this.memoizedIsInitialized = -1;
            this.hr_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OM0TrackData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.time_.toBuilder();
                            }
                            this.time_ = (DateTime) input.readMessage(DateTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.time_);
                                this.time_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder2 = this.gnss_.toBuilder();
                            }
                            this.gnss_ = (RtGNSS) input.readMessage(RtGNSS.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.gnss_);
                                this.gnss_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.hr_ = input.readFixed32();
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
            return Om0Command.internal_static_OM0TrackData_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Om0Command.internal_static_OM0TrackData_fieldAccessorTable.ensureFieldAccessorsInitialized(OM0TrackData.class, Builder.class);
        }

        public boolean hasTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public DateTime getTime() {
            return this.time_ == null ? DateTime.getDefaultInstance() : this.time_;
        }

        public DateTimeOrBuilder getTimeOrBuilder() {
            return this.time_ == null ? DateTime.getDefaultInstance() : this.time_;
        }

        public boolean hasGnss() {
            return (this.bitField0_ & 2) == 2;
        }

        public RtGNSS getGnss() {
            return this.gnss_ == null ? RtGNSS.getDefaultInstance() : this.gnss_;
        }

        public RtGNSSOrBuilder getGnssOrBuilder() {
            return this.gnss_ == null ? RtGNSS.getDefaultInstance() : this.gnss_;
        }

        public boolean hasHr() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getHr() {
            return this.hr_;
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
            } else if (!hasGnss() || getGnss().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getGnss());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.hr_);
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
                size2 += CodedOutputStream.computeMessageSize(2, getGnss());
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.hr_);
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
            if (!(obj instanceof OM0TrackData)) {
                return super.equals(obj);
            }
            OM0TrackData other = (OM0TrackData) obj;
            boolean result4 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result4 = result4 && getTime().equals(other.getTime());
            }
            if (!result4 || hasGnss() != other.hasGnss()) {
                result = false;
            } else {
                result = true;
            }
            if (hasGnss()) {
                if (!result || !getGnss().equals(other.getGnss())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasHr() != other.hasHr()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasHr()) {
                if (!result2 || getHr() != other.getHr()) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + getTime().hashCode();
            }
            if (hasGnss()) {
                hash = (((hash * 37) + 2) * 53) + getGnss().hashCode();
            }
            if (hasHr()) {
                hash = (((hash * 37) + 3) * 53) + getHr();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static OM0TrackData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OM0TrackData) PARSER.parseFrom(data);
        }

        public static OM0TrackData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0TrackData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0TrackData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OM0TrackData) PARSER.parseFrom(data);
        }

        public static OM0TrackData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OM0TrackData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OM0TrackData parseFrom(InputStream input) throws IOException {
            return (OM0TrackData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0TrackData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0TrackData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0TrackData parseDelimitedFrom(InputStream input) throws IOException {
            return (OM0TrackData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static OM0TrackData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0TrackData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OM0TrackData parseFrom(CodedInputStream input) throws IOException {
            return (OM0TrackData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OM0TrackData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OM0TrackData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OM0TrackData prototype) {
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

        public static OM0TrackData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OM0TrackData> parser() {
            return PARSER;
        }

        public Parser<OM0TrackData> getParserForType() {
            return PARSER;
        }

        public OM0TrackData getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface OM0CommandOrBuilder extends MessageOrBuilder {
        OM0SleepData getSleepData();

        OM0SleepDataOrBuilder getSleepDataOrBuilder();

        OM0TrackConf getTrackConf();

        OM0TrackConfOrBuilder getTrackConfOrBuilder();

        boolean hasSleepData();

        boolean hasTrackConf();
    }

    public interface OM0ReportOrBuilder extends MessageOrBuilder {
        RtBattery getBattery();

        RtBatteryOrBuilder getBatteryOrBuilder();

        DateTime getDateTime();

        DateTimeOrBuilder getDateTimeOrBuilder();

        RtHealth getHealth();

        RtHealthOrBuilder getHealthOrBuilder();

        boolean getSosSignal();

        OM0TrackData getTrackData(int i);

        int getTrackDataCount();

        List<OM0TrackData> getTrackDataList();

        OM0TrackDataOrBuilder getTrackDataOrBuilder(int i);

        List<? extends OM0TrackDataOrBuilder> getTrackDataOrBuilderList();

        boolean getWakeUp();

        boolean hasBattery();

        boolean hasDateTime();

        boolean hasHealth();

        boolean hasSosSignal();

        boolean hasWakeUp();
    }

    public interface OM0SleepDataOrBuilder extends MessageOrBuilder {
        DateTime getDate();

        DateTimeOrBuilder getDateOrBuilder();

        int getDeepsleep();

        int getDuration();

        int getScore();

        int getSomnolence();

        int getWakefulness();

        boolean hasDate();

        boolean hasDeepsleep();

        boolean hasDuration();

        boolean hasScore();

        boolean hasSomnolence();

        boolean hasWakefulness();
    }

    public interface OM0TrackConfOrBuilder extends MessageOrBuilder {
        boolean getAutoRun();

        int getHash();

        int getInterval();

        boolean hasAutoRun();

        boolean hasHash();

        boolean hasInterval();
    }

    public interface OM0TrackDataOrBuilder extends MessageOrBuilder {
        RtGNSS getGnss();

        RtGNSSOrBuilder getGnssOrBuilder();

        int getHr();

        DateTime getTime();

        DateTimeOrBuilder getTimeOrBuilder();

        boolean hasGnss();

        boolean hasHr();

        boolean hasTime();
    }

    private Om0Command() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0011om0_command.proto\u001a\u0013realtime_data.proto\"D\n\fOM0TrackConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0010\n\bauto_run\u0018\u0002 \u0002(\b\u0012\u0014\n\binterval\u0018\u0003 \u0002(\u0007:\u000230\"\u0001\n\fOM0SleepData\u0012\r\n\u0005score\u0018\u0001 \u0002(\u0007\u0012\u0010\n\bduration\u0018\u0002 \u0002(\u0007\u0012\u0011\n\tdeepsleep\u0018\u0003 \u0002(\u0007\u0012\u0012\n\nsomnolence\u0018\u0004 \u0002(\u0007\u0012\u0013\n\u000bwakefulness\u0018\u0005 \u0002(\u0007\u0012\u0017\n\u0004date\u0018\u0006 \u0002(\u000b2\t.DateTime\"R\n\nOM0Command\u0012!\n\ntrack_conf\u0018\u0001 \u0001(\u000b2\r.OM0TrackConf\u0012!\n\nsleep_data\u0018\u0002 \u0001(\u000b2\r.OM0SleepData\"J\n\fOM0TrackData\u0012\u0017\n\u0004time\u0018\u0001 \u0002(\u000b2\t.DateTime\u0012\u0015\n\u0004gnss\u0018\u0002 \u0001(\u000b2\u0007.RtGNSS\u0012\n\n\u0002hr\u0018", "\u0003 \u0001(\u0007\"©\u0001\n\tOM0Report\u0012\u0012\n\nsos_signal\u0018\u0001 \u0001(\b\u0012\u000f\n\u0007wake_up\u0018\u0002 \u0001(\b\u0012!\n\ntrack_data\u0018\u0003 \u0003(\u000b2\r.OM0TrackData\u0012\u001b\n\u0007battery\u0018\u0004 \u0001(\u000b2\n.RtBattery\u0012\u0019\n\u0006health\u0018\u0005 \u0001(\u000b2\t.RtHealth\u0012\u001c\n\tdate_time\u0018\u0006 \u0001(\u000b2\t.DateTime"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                Om0Command.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
