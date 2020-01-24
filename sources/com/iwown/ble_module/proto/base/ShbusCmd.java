package com.iwown.ble_module.proto.base;

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
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.iwown.ble_module.proto.base.RealtimeData.RtTime;
import com.iwown.ble_module.proto.base.RealtimeData.RtTimeOrBuilder;
import java.io.IOException;
import java.io.InputStream;

public final class ShbusCmd {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static Descriptor internal_static_SBCommand_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_SBCommand_fieldAccessorTable = new FieldAccessorTable(internal_static_SBCommand_descriptor, new String[]{"SleepData"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_SBDLSleepData_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_SBDLSleepData_fieldAccessorTable = new FieldAccessorTable(internal_static_SBDLSleepData_descriptor, new String[]{"Score", "Duration", "Deepsleep", "Somnolence", "Wakefulness", "Time"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_SBReport_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_SBReport_fieldAccessorTable = new FieldAccessorTable(internal_static_SBReport_descriptor, new String[]{"SleepData"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_SBUPSleepData_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_SBUPSleepData_fieldAccessorTable = new FieldAccessorTable(internal_static_SBUPSleepData_descriptor, new String[]{"Time"});

    public static final class SBCommand extends GeneratedMessageV3 implements SBCommandOrBuilder {
        private static final SBCommand DEFAULT_INSTANCE = new SBCommand();
        @Deprecated
        public static final Parser<SBCommand> PARSER = new AbstractParser<SBCommand>() {
            public SBCommand parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SBCommand(input, extensionRegistry);
            }
        };
        public static final int SLEEP_DATA_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public SBDLSleepData sleepData_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SBCommandOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<SBDLSleepData, Builder, SBDLSleepDataOrBuilder> sleepDataBuilder_;
            private SBDLSleepData sleepData_;

            public static final Descriptor getDescriptor() {
                return ShbusCmd.internal_static_SBCommand_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return ShbusCmd.internal_static_SBCommand_fieldAccessorTable.ensureFieldAccessorsInitialized(SBCommand.class, Builder.class);
            }

            private Builder() {
                this.sleepData_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.sleepData_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (SBCommand.alwaysUseFieldBuilders) {
                    getSleepDataFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return ShbusCmd.internal_static_SBCommand_descriptor;
            }

            public SBCommand getDefaultInstanceForType() {
                return SBCommand.getDefaultInstance();
            }

            public SBCommand build() {
                SBCommand result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SBCommand buildPartial() {
                SBCommand result = new SBCommand((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.sleepDataBuilder_ == null) {
                    result.sleepData_ = this.sleepData_;
                } else {
                    result.sleepData_ = (SBDLSleepData) this.sleepDataBuilder_.build();
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
                if (other instanceof SBCommand) {
                    return mergeFrom((SBCommand) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SBCommand other) {
                if (other != SBCommand.getDefaultInstance()) {
                    if (other.hasSleepData()) {
                        mergeSleepData(other.getSleepData());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasSleepData() || getSleepData().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                SBCommand parsedMessage = null;
                try {
                    SBCommand parsedMessage2 = (SBCommand) SBCommand.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SBCommand parsedMessage3 = (SBCommand) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSleepData() {
                return (this.bitField0_ & 1) == 1;
            }

            public SBDLSleepData getSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    return this.sleepData_ == null ? SBDLSleepData.getDefaultInstance() : this.sleepData_;
                }
                return (SBDLSleepData) this.sleepDataBuilder_.getMessage();
            }

            public Builder setSleepData(SBDLSleepData value) {
                if (this.sleepDataBuilder_ != null) {
                    this.sleepDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.sleepData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setSleepData(Builder builderForValue) {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.sleepDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeSleepData(SBDLSleepData value) {
                if (this.sleepDataBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.sleepData_ == null || this.sleepData_ == SBDLSleepData.getDefaultInstance()) {
                        this.sleepData_ = value;
                    } else {
                        this.sleepData_ = SBDLSleepData.newBuilder(this.sleepData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.sleepDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                    onChanged();
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Builder getSleepDataBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getSleepDataFieldBuilder().getBuilder();
            }

            public SBDLSleepDataOrBuilder getSleepDataOrBuilder() {
                if (this.sleepDataBuilder_ != null) {
                    return (SBDLSleepDataOrBuilder) this.sleepDataBuilder_.getMessageOrBuilder();
                }
                return this.sleepData_ == null ? SBDLSleepData.getDefaultInstance() : this.sleepData_;
            }

            private SingleFieldBuilderV3<SBDLSleepData, Builder, SBDLSleepDataOrBuilder> getSleepDataFieldBuilder() {
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

        private SBCommand(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SBCommand() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SBCommand(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = this.sleepData_.toBuilder();
                            }
                            this.sleepData_ = (SBDLSleepData) input.readMessage(SBDLSleepData.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.sleepData_);
                                this.sleepData_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
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
            return ShbusCmd.internal_static_SBCommand_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ShbusCmd.internal_static_SBCommand_fieldAccessorTable.ensureFieldAccessorsInitialized(SBCommand.class, Builder.class);
        }

        public boolean hasSleepData() {
            return (this.bitField0_ & 1) == 1;
        }

        public SBDLSleepData getSleepData() {
            return this.sleepData_ == null ? SBDLSleepData.getDefaultInstance() : this.sleepData_;
        }

        public SBDLSleepDataOrBuilder getSleepDataOrBuilder() {
            return this.sleepData_ == null ? SBDLSleepData.getDefaultInstance() : this.sleepData_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSleepData() || getSleepData().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            }
            this.memoizedIsInitialized = 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getSleepData());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getSleepData());
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
            if (!(obj instanceof SBCommand)) {
                return super.equals(obj);
            }
            SBCommand other = (SBCommand) obj;
            boolean result2 = 1 != 0 && hasSleepData() == other.hasSleepData();
            if (hasSleepData()) {
                result2 = result2 && getSleepData().equals(other.getSleepData());
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
            if (hasSleepData()) {
                hash = (((hash * 37) + 1) * 53) + getSleepData().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static SBCommand parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SBCommand) PARSER.parseFrom(data);
        }

        public static SBCommand parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBCommand) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBCommand parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SBCommand) PARSER.parseFrom(data);
        }

        public static SBCommand parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBCommand) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBCommand parseFrom(InputStream input) throws IOException {
            return (SBCommand) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBCommand parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBCommand) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBCommand parseDelimitedFrom(InputStream input) throws IOException {
            return (SBCommand) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SBCommand parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBCommand) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBCommand parseFrom(CodedInputStream input) throws IOException {
            return (SBCommand) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBCommand parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBCommand) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SBCommand prototype) {
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

        public static SBCommand getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SBCommand> parser() {
            return PARSER;
        }

        public Parser<SBCommand> getParserForType() {
            return PARSER;
        }

        public SBCommand getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class SBDLSleepData extends GeneratedMessageV3 implements SBDLSleepDataOrBuilder {
        public static final int DEEPSLEEP_FIELD_NUMBER = 3;
        private static final SBDLSleepData DEFAULT_INSTANCE = new SBDLSleepData();
        public static final int DURATION_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<SBDLSleepData> PARSER = new AbstractParser<SBDLSleepData>() {
            public SBDLSleepData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SBDLSleepData(input, extensionRegistry);
            }
        };
        public static final int SCORE_FIELD_NUMBER = 1;
        public static final int SOMNOLENCE_FIELD_NUMBER = 4;
        public static final int TIME_FIELD_NUMBER = 6;
        public static final int WAKEFULNESS_FIELD_NUMBER = 5;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
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
        public RtTime time_;
        /* access modifiers changed from: private */
        public int wakefulness_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SBDLSleepDataOrBuilder {
            private int bitField0_;
            private int deepsleep_;
            private int duration_;
            private int score_;
            private int somnolence_;
            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> timeBuilder_;
            private RtTime time_;
            private int wakefulness_;

            public static final Descriptor getDescriptor() {
                return ShbusCmd.internal_static_SBDLSleepData_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return ShbusCmd.internal_static_SBDLSleepData_fieldAccessorTable.ensureFieldAccessorsInitialized(SBDLSleepData.class, Builder.class);
            }

            private Builder() {
                this.time_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (SBDLSleepData.alwaysUseFieldBuilders) {
                    getTimeFieldBuilder();
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
                if (this.timeBuilder_ == null) {
                    this.time_ = null;
                } else {
                    this.timeBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return ShbusCmd.internal_static_SBDLSleepData_descriptor;
            }

            public SBDLSleepData getDefaultInstanceForType() {
                return SBDLSleepData.getDefaultInstance();
            }

            public SBDLSleepData build() {
                SBDLSleepData result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SBDLSleepData buildPartial() {
                SBDLSleepData result = new SBDLSleepData((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (this.timeBuilder_ == null) {
                    result.time_ = this.time_;
                } else {
                    result.time_ = (RtTime) this.timeBuilder_.build();
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
                if (other instanceof SBDLSleepData) {
                    return mergeFrom((SBDLSleepData) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SBDLSleepData other) {
                if (other != SBDLSleepData.getDefaultInstance()) {
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
                    if (other.hasTime()) {
                        mergeTime(other.getTime());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasScore() && hasDuration() && hasDeepsleep() && hasSomnolence() && hasWakefulness() && hasTime() && getTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                SBDLSleepData parsedMessage = null;
                try {
                    SBDLSleepData parsedMessage2 = (SBDLSleepData) SBDLSleepData.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SBDLSleepData parsedMessage3 = (SBDLSleepData) e.getUnfinishedMessage();
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

            public boolean hasTime() {
                return (this.bitField0_ & 32) == 32;
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
                this.bitField0_ |= 32;
                return this;
            }

            public Builder setTime(com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder builderForValue) {
                if (this.timeBuilder_ == null) {
                    this.time_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeTime(RtTime value) {
                if (this.timeBuilder_ == null) {
                    if ((this.bitField0_ & 32) != 32 || this.time_ == null || this.time_ == RtTime.getDefaultInstance()) {
                        this.time_ = value;
                    } else {
                        this.time_ = RtTime.newBuilder(this.time_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.timeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder clearTime() {
                if (this.timeBuilder_ == null) {
                    this.time_ = null;
                    onChanged();
                } else {
                    this.timeBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder getTimeBuilder() {
                this.bitField0_ |= 32;
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

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private SBDLSleepData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SBDLSleepData() {
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

        private SBDLSleepData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 32) == 32) {
                                subBuilder = this.time_.toBuilder();
                            }
                            this.time_ = (RtTime) input.readMessage(RtTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.time_);
                                this.time_ = subBuilder.buildPartial();
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
            return ShbusCmd.internal_static_SBDLSleepData_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ShbusCmd.internal_static_SBDLSleepData_fieldAccessorTable.ensureFieldAccessorsInitialized(SBDLSleepData.class, Builder.class);
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

        public boolean hasTime() {
            return (this.bitField0_ & 32) == 32;
        }

        public RtTime getTime() {
            return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
        }

        public RtTimeOrBuilder getTimeOrBuilder() {
            return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
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
            } else if (!hasTime()) {
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
                output.writeMessage(6, getTime());
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
                size2 += CodedOutputStream.computeMessageSize(6, getTime());
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
            if (!(obj instanceof SBDLSleepData)) {
                return super.equals(obj);
            }
            SBDLSleepData other = (SBDLSleepData) obj;
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
            if (!result4 || hasTime() != other.hasTime()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasTime()) {
                if (!result5 || !getTime().equals(other.getTime())) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 6) * 53) + getTime().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static SBDLSleepData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SBDLSleepData) PARSER.parseFrom(data);
        }

        public static SBDLSleepData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBDLSleepData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBDLSleepData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SBDLSleepData) PARSER.parseFrom(data);
        }

        public static SBDLSleepData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBDLSleepData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBDLSleepData parseFrom(InputStream input) throws IOException {
            return (SBDLSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBDLSleepData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBDLSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBDLSleepData parseDelimitedFrom(InputStream input) throws IOException {
            return (SBDLSleepData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SBDLSleepData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBDLSleepData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBDLSleepData parseFrom(CodedInputStream input) throws IOException {
            return (SBDLSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBDLSleepData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBDLSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SBDLSleepData prototype) {
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

        public static SBDLSleepData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SBDLSleepData> parser() {
            return PARSER;
        }

        public Parser<SBDLSleepData> getParserForType() {
            return PARSER;
        }

        public SBDLSleepData getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class SBReport extends GeneratedMessageV3 implements SBReportOrBuilder {
        private static final SBReport DEFAULT_INSTANCE = new SBReport();
        @Deprecated
        public static final Parser<SBReport> PARSER = new AbstractParser<SBReport>() {
            public SBReport parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SBReport(input, extensionRegistry);
            }
        };
        public static final int SLEEP_DATA_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public SBUPSleepData sleepData_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SBReportOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<SBUPSleepData, Builder, SBUPSleepDataOrBuilder> sleepDataBuilder_;
            private SBUPSleepData sleepData_;

            public static final Descriptor getDescriptor() {
                return ShbusCmd.internal_static_SBReport_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return ShbusCmd.internal_static_SBReport_fieldAccessorTable.ensureFieldAccessorsInitialized(SBReport.class, Builder.class);
            }

            private Builder() {
                this.sleepData_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.sleepData_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (SBReport.alwaysUseFieldBuilders) {
                    getSleepDataFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return ShbusCmd.internal_static_SBReport_descriptor;
            }

            public SBReport getDefaultInstanceForType() {
                return SBReport.getDefaultInstance();
            }

            public SBReport build() {
                SBReport result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SBReport buildPartial() {
                SBReport result = new SBReport((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.sleepDataBuilder_ == null) {
                    result.sleepData_ = this.sleepData_;
                } else {
                    result.sleepData_ = (SBUPSleepData) this.sleepDataBuilder_.build();
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
                if (other instanceof SBReport) {
                    return mergeFrom((SBReport) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SBReport other) {
                if (other != SBReport.getDefaultInstance()) {
                    if (other.hasSleepData()) {
                        mergeSleepData(other.getSleepData());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasSleepData() || getSleepData().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                SBReport parsedMessage = null;
                try {
                    SBReport parsedMessage2 = (SBReport) SBReport.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SBReport parsedMessage3 = (SBReport) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSleepData() {
                return (this.bitField0_ & 1) == 1;
            }

            public SBUPSleepData getSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    return this.sleepData_ == null ? SBUPSleepData.getDefaultInstance() : this.sleepData_;
                }
                return (SBUPSleepData) this.sleepDataBuilder_.getMessage();
            }

            public Builder setSleepData(SBUPSleepData value) {
                if (this.sleepDataBuilder_ != null) {
                    this.sleepDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.sleepData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setSleepData(Builder builderForValue) {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.sleepDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeSleepData(SBUPSleepData value) {
                if (this.sleepDataBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.sleepData_ == null || this.sleepData_ == SBUPSleepData.getDefaultInstance()) {
                        this.sleepData_ = value;
                    } else {
                        this.sleepData_ = SBUPSleepData.newBuilder(this.sleepData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.sleepDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                    onChanged();
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Builder getSleepDataBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getSleepDataFieldBuilder().getBuilder();
            }

            public SBUPSleepDataOrBuilder getSleepDataOrBuilder() {
                if (this.sleepDataBuilder_ != null) {
                    return (SBUPSleepDataOrBuilder) this.sleepDataBuilder_.getMessageOrBuilder();
                }
                return this.sleepData_ == null ? SBUPSleepData.getDefaultInstance() : this.sleepData_;
            }

            private SingleFieldBuilderV3<SBUPSleepData, Builder, SBUPSleepDataOrBuilder> getSleepDataFieldBuilder() {
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

        private SBReport(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SBReport() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SBReport(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = this.sleepData_.toBuilder();
                            }
                            this.sleepData_ = (SBUPSleepData) input.readMessage(SBUPSleepData.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.sleepData_);
                                this.sleepData_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
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
            return ShbusCmd.internal_static_SBReport_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ShbusCmd.internal_static_SBReport_fieldAccessorTable.ensureFieldAccessorsInitialized(SBReport.class, Builder.class);
        }

        public boolean hasSleepData() {
            return (this.bitField0_ & 1) == 1;
        }

        public SBUPSleepData getSleepData() {
            return this.sleepData_ == null ? SBUPSleepData.getDefaultInstance() : this.sleepData_;
        }

        public SBUPSleepDataOrBuilder getSleepDataOrBuilder() {
            return this.sleepData_ == null ? SBUPSleepData.getDefaultInstance() : this.sleepData_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSleepData() || getSleepData().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            }
            this.memoizedIsInitialized = 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getSleepData());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getSleepData());
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
            if (!(obj instanceof SBReport)) {
                return super.equals(obj);
            }
            SBReport other = (SBReport) obj;
            boolean result2 = 1 != 0 && hasSleepData() == other.hasSleepData();
            if (hasSleepData()) {
                result2 = result2 && getSleepData().equals(other.getSleepData());
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
            if (hasSleepData()) {
                hash = (((hash * 37) + 1) * 53) + getSleepData().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static SBReport parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SBReport) PARSER.parseFrom(data);
        }

        public static SBReport parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBReport) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBReport parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SBReport) PARSER.parseFrom(data);
        }

        public static SBReport parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBReport) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBReport parseFrom(InputStream input) throws IOException {
            return (SBReport) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBReport parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBReport) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBReport parseDelimitedFrom(InputStream input) throws IOException {
            return (SBReport) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SBReport parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBReport) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBReport parseFrom(CodedInputStream input) throws IOException {
            return (SBReport) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBReport parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBReport) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SBReport prototype) {
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

        public static SBReport getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SBReport> parser() {
            return PARSER;
        }

        public Parser<SBReport> getParserForType() {
            return PARSER;
        }

        public SBReport getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class SBUPSleepData extends GeneratedMessageV3 implements SBUPSleepDataOrBuilder {
        private static final SBUPSleepData DEFAULT_INSTANCE = new SBUPSleepData();
        @Deprecated
        public static final Parser<SBUPSleepData> PARSER = new AbstractParser<SBUPSleepData>() {
            public SBUPSleepData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SBUPSleepData(input, extensionRegistry);
            }
        };
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public RtTime time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SBUPSleepDataOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> timeBuilder_;
            private RtTime time_;

            public static final Descriptor getDescriptor() {
                return ShbusCmd.internal_static_SBUPSleepData_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return ShbusCmd.internal_static_SBUPSleepData_fieldAccessorTable.ensureFieldAccessorsInitialized(SBUPSleepData.class, Builder.class);
            }

            private Builder() {
                this.time_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (SBUPSleepData.alwaysUseFieldBuilders) {
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
                return this;
            }

            public Descriptor getDescriptorForType() {
                return ShbusCmd.internal_static_SBUPSleepData_descriptor;
            }

            public SBUPSleepData getDefaultInstanceForType() {
                return SBUPSleepData.getDefaultInstance();
            }

            public SBUPSleepData build() {
                SBUPSleepData result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SBUPSleepData buildPartial() {
                SBUPSleepData result = new SBUPSleepData((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeBuilder_ == null) {
                    result.time_ = this.time_;
                } else {
                    result.time_ = (RtTime) this.timeBuilder_.build();
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
                if (other instanceof SBUPSleepData) {
                    return mergeFrom((SBUPSleepData) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SBUPSleepData other) {
                if (other != SBUPSleepData.getDefaultInstance()) {
                    if (other.hasTime()) {
                        mergeTime(other.getTime());
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
                SBUPSleepData parsedMessage = null;
                try {
                    SBUPSleepData parsedMessage2 = (SBUPSleepData) SBUPSleepData.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SBUPSleepData parsedMessage3 = (SBUPSleepData) e.getUnfinishedMessage();
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

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private SBUPSleepData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SBUPSleepData() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SBUPSleepData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return ShbusCmd.internal_static_SBUPSleepData_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ShbusCmd.internal_static_SBUPSleepData_fieldAccessorTable.ensureFieldAccessorsInitialized(SBUPSleepData.class, Builder.class);
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
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SBUPSleepData)) {
                return super.equals(obj);
            }
            SBUPSleepData other = (SBUPSleepData) obj;
            boolean result2 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result2 = result2 && getTime().equals(other.getTime());
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + getTime().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static SBUPSleepData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SBUPSleepData) PARSER.parseFrom(data);
        }

        public static SBUPSleepData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBUPSleepData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBUPSleepData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SBUPSleepData) PARSER.parseFrom(data);
        }

        public static SBUPSleepData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SBUPSleepData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SBUPSleepData parseFrom(InputStream input) throws IOException {
            return (SBUPSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBUPSleepData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBUPSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBUPSleepData parseDelimitedFrom(InputStream input) throws IOException {
            return (SBUPSleepData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SBUPSleepData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBUPSleepData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SBUPSleepData parseFrom(CodedInputStream input) throws IOException {
            return (SBUPSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SBUPSleepData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SBUPSleepData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SBUPSleepData prototype) {
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

        public static SBUPSleepData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SBUPSleepData> parser() {
            return PARSER;
        }

        public Parser<SBUPSleepData> getParserForType() {
            return PARSER;
        }

        public SBUPSleepData getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface SBCommandOrBuilder extends MessageOrBuilder {
        SBDLSleepData getSleepData();

        SBDLSleepDataOrBuilder getSleepDataOrBuilder();

        boolean hasSleepData();
    }

    public interface SBDLSleepDataOrBuilder extends MessageOrBuilder {
        int getDeepsleep();

        int getDuration();

        int getScore();

        int getSomnolence();

        RtTime getTime();

        RtTimeOrBuilder getTimeOrBuilder();

        int getWakefulness();

        boolean hasDeepsleep();

        boolean hasDuration();

        boolean hasScore();

        boolean hasSomnolence();

        boolean hasTime();

        boolean hasWakefulness();
    }

    public interface SBReportOrBuilder extends MessageOrBuilder {
        SBUPSleepData getSleepData();

        SBUPSleepDataOrBuilder getSleepDataOrBuilder();

        boolean hasSleepData();
    }

    public interface SBUPSleepDataOrBuilder extends MessageOrBuilder {
        RtTime getTime();

        RtTimeOrBuilder getTimeOrBuilder();

        boolean hasTime();
    }

    private ShbusCmd() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u000fshbus_cmd.proto\u001a\u0013realtime_data.proto\"\u0001\n\rSBDLSleepData\u0012\r\n\u0005score\u0018\u0001 \u0002(\u0007\u0012\u0010\n\bduration\u0018\u0002 \u0002(\u0007\u0012\u0011\n\tdeepsleep\u0018\u0003 \u0002(\u0007\u0012\u0012\n\nsomnolence\u0018\u0004 \u0002(\u0007\u0012\u0013\n\u000bwakefulness\u0018\u0005 \u0002(\u0007\u0012\u0015\n\u0004time\u0018\u0006 \u0002(\u000b2\u0007.RtTime\"&\n\rSBUPSleepData\u0012\u0015\n\u0004time\u0018\u0001 \u0002(\u000b2\u0007.RtTime\"/\n\tSBCommand\u0012\"\n\nsleep_data\u0018\u0001 \u0001(\u000b2\u000e.SBDLSleepData\".\n\bSBReport\u0012\"\n\nsleep_data\u0018\u0001 \u0001(\u000b2\u000e.SBUPSleepData"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                ShbusCmd.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
