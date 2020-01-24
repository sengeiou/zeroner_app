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
import com.iwown.ble_module.proto.base.RealtimeData.RtTime;
import com.iwown.ble_module.proto.base.RealtimeData.RtTimeOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WeatherOuterClass {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_WeatherConfirm_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_WeatherConfirm_fieldAccessorTable = new FieldAccessorTable(internal_static_WeatherConfirm_descriptor, new String[]{"Operation", "Ret"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_WeatherGroup_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_WeatherGroup_fieldAccessorTable = new FieldAccessorTable(internal_static_WeatherGroup_descriptor, new String[]{"Hash", "Data"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_WeatherNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_WeatherNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_WeatherNotification_descriptor, new String[]{"Operation", "Group"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_WeatherParams_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_WeatherParams_fieldAccessorTable = new FieldAccessorTable(internal_static_WeatherParams_descriptor, new String[]{"MaxCount"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_WeatherSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_WeatherSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_WeatherSubscriber_descriptor, new String[]{"Hash", "Params", "Confirm", "Data"});
    /* access modifiers changed from: private */
    public static Descriptor internal_static_Weather_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_Weather_fieldAccessorTable = new FieldAccessorTable(internal_static_Weather_descriptor, new String[]{"DateTime", "Type", "Desc", "DegreeMax", "DegreeMin", "Pm2P5"});

    public static final class Weather extends GeneratedMessageV3 implements WeatherOrBuilder {
        public static final int DATE_TIME_FIELD_NUMBER = 1;
        private static final Weather DEFAULT_INSTANCE = new Weather();
        public static final int DEGREE_MAX_FIELD_NUMBER = 4;
        public static final int DEGREE_MIN_FIELD_NUMBER = 5;
        public static final int DESC_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<Weather> PARSER = new AbstractParser<Weather>() {
            public Weather parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Weather(input, extensionRegistry);
            }
        };
        public static final int PM2P5_FIELD_NUMBER = 6;
        public static final int TYPE_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public RtTime dateTime_;
        /* access modifiers changed from: private */
        public int degreeMax_;
        /* access modifiers changed from: private */
        public int degreeMin_;
        /* access modifiers changed from: private */
        public int desc_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int pm2P5_;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements WeatherOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> dateTimeBuilder_;
            private RtTime dateTime_;
            private int degreeMax_;
            private int degreeMin_;
            private int desc_;
            private int pm2P5_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return WeatherOuterClass.internal_static_Weather_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return WeatherOuterClass.internal_static_Weather_fieldAccessorTable.ensureFieldAccessorsInitialized(Weather.class, Builder.class);
            }

            private Builder() {
                this.dateTime_ = null;
                this.type_ = 0;
                this.desc_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dateTime_ = null;
                this.type_ = 0;
                this.desc_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (Weather.alwaysUseFieldBuilders) {
                    getDateTimeFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.type_ = 0;
                this.bitField0_ &= -3;
                this.desc_ = 0;
                this.bitField0_ &= -5;
                this.degreeMax_ = 0;
                this.bitField0_ &= -9;
                this.degreeMin_ = 0;
                this.bitField0_ &= -17;
                this.pm2P5_ = 0;
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return WeatherOuterClass.internal_static_Weather_descriptor;
            }

            public Weather getDefaultInstanceForType() {
                return Weather.getDefaultInstance();
            }

            public Weather build() {
                Weather result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public Weather buildPartial() {
                Weather result = new Weather((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.dateTimeBuilder_ == null) {
                    result.dateTime_ = this.dateTime_;
                } else {
                    result.dateTime_ = (RtTime) this.dateTimeBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.desc_ = this.desc_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.degreeMax_ = this.degreeMax_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.degreeMin_ = this.degreeMin_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.pm2P5_ = this.pm2P5_;
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
                if (other instanceof Weather) {
                    return mergeFrom((Weather) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Weather other) {
                if (other != Weather.getDefaultInstance()) {
                    if (other.hasDateTime()) {
                        mergeDateTime(other.getDateTime());
                    }
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (other.hasDesc()) {
                        setDesc(other.getDesc());
                    }
                    if (other.hasDegreeMax()) {
                        setDegreeMax(other.getDegreeMax());
                    }
                    if (other.hasDegreeMin()) {
                        setDegreeMin(other.getDegreeMin());
                    }
                    if (other.hasPm2P5()) {
                        setPm2P5(other.getPm2P5());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasDateTime() && hasType() && hasDesc() && hasDegreeMax() && hasDegreeMin() && hasPm2P5() && getDateTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Weather parsedMessage = null;
                try {
                    Weather parsedMessage2 = (Weather) Weather.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    Weather parsedMessage3 = (Weather) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasDateTime() {
                return (this.bitField0_ & 1) == 1;
            }

            public RtTime getDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
                }
                return (RtTime) this.dateTimeBuilder_.getMessage();
            }

            public Builder setDateTime(RtTime value) {
                if (this.dateTimeBuilder_ != null) {
                    this.dateTimeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.dateTime_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setDateTime(com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder builderForValue) {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dateTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeDateTime(RtTime value) {
                if (this.dateTimeBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.dateTime_ == null || this.dateTime_ == RtTime.getDefaultInstance()) {
                        this.dateTime_ = value;
                    } else {
                        this.dateTime_ = RtTime.newBuilder(this.dateTime_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.dateTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                    onChanged();
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder getDateTimeBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder) getDateTimeFieldBuilder().getBuilder();
            }

            public RtTimeOrBuilder getDateTimeOrBuilder() {
                if (this.dateTimeBuilder_ != null) {
                    return (RtTimeOrBuilder) this.dateTimeBuilder_.getMessageOrBuilder();
                }
                return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
            }

            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> getDateTimeFieldBuilder() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTimeBuilder_ = new SingleFieldBuilderV3<>(getDateTime(), getParentForChildren(), isClean());
                    this.dateTime_ = null;
                }
                return this.dateTimeBuilder_;
            }

            public boolean hasType() {
                return (this.bitField0_ & 2) == 2;
            }

            public WeatherType getType() {
                WeatherType result = WeatherType.valueOf(this.type_);
                return result == null ? WeatherType.EACH_HOUR : result;
            }

            public Builder setType(WeatherType value) {
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
                this.type_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDesc() {
                return (this.bitField0_ & 4) == 4;
            }

            public WeatherDesc getDesc() {
                WeatherDesc result = WeatherDesc.valueOf(this.desc_);
                return result == null ? WeatherDesc.Sunny : result;
            }

            public Builder setDesc(WeatherDesc value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.desc_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearDesc() {
                this.bitField0_ &= -5;
                this.desc_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDegreeMax() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getDegreeMax() {
                return this.degreeMax_;
            }

            public Builder setDegreeMax(int value) {
                this.bitField0_ |= 8;
                this.degreeMax_ = value;
                onChanged();
                return this;
            }

            public Builder clearDegreeMax() {
                this.bitField0_ &= -9;
                this.degreeMax_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDegreeMin() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getDegreeMin() {
                return this.degreeMin_;
            }

            public Builder setDegreeMin(int value) {
                this.bitField0_ |= 16;
                this.degreeMin_ = value;
                onChanged();
                return this;
            }

            public Builder clearDegreeMin() {
                this.bitField0_ &= -17;
                this.degreeMin_ = 0;
                onChanged();
                return this;
            }

            public boolean hasPm2P5() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getPm2P5() {
                return this.pm2P5_;
            }

            public Builder setPm2P5(int value) {
                this.bitField0_ |= 32;
                this.pm2P5_ = value;
                onChanged();
                return this;
            }

            public Builder clearPm2P5() {
                this.bitField0_ &= -33;
                this.pm2P5_ = 0;
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

        private Weather(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private Weather() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
            this.desc_ = 0;
            this.degreeMax_ = 0;
            this.degreeMin_ = 0;
            this.pm2P5_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Weather(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = this.dateTime_.toBuilder();
                            }
                            this.dateTime_ = (RtTime) input.readMessage(RtTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.dateTime_);
                                this.dateTime_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 16:
                            int rawValue = input.readEnum();
                            if (WeatherType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 2;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue);
                                break;
                            }
                        case 24:
                            int rawValue2 = input.readEnum();
                            if (WeatherDesc.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 4;
                                this.desc_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(3, rawValue2);
                                break;
                            }
                        case 37:
                            this.bitField0_ |= 8;
                            this.degreeMax_ = input.readSFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.degreeMin_ = input.readSFixed32();
                            break;
                        case 53:
                            this.bitField0_ |= 32;
                            this.pm2P5_ = input.readFixed32();
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
            return WeatherOuterClass.internal_static_Weather_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WeatherOuterClass.internal_static_Weather_fieldAccessorTable.ensureFieldAccessorsInitialized(Weather.class, Builder.class);
        }

        public boolean hasDateTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtTime getDateTime() {
            return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
        }

        public RtTimeOrBuilder getDateTimeOrBuilder() {
            return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
        }

        public boolean hasType() {
            return (this.bitField0_ & 2) == 2;
        }

        public WeatherType getType() {
            WeatherType result = WeatherType.valueOf(this.type_);
            return result == null ? WeatherType.EACH_HOUR : result;
        }

        public boolean hasDesc() {
            return (this.bitField0_ & 4) == 4;
        }

        public WeatherDesc getDesc() {
            WeatherDesc result = WeatherDesc.valueOf(this.desc_);
            return result == null ? WeatherDesc.Sunny : result;
        }

        public boolean hasDegreeMax() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getDegreeMax() {
            return this.degreeMax_;
        }

        public boolean hasDegreeMin() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getDegreeMin() {
            return this.degreeMin_;
        }

        public boolean hasPm2P5() {
            return (this.bitField0_ & 32) == 32;
        }

        public int getPm2P5() {
            return this.pm2P5_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasDateTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDesc()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDegreeMax()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDegreeMin()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasPm2P5()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getDateTime().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getDateTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.type_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(3, this.desc_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeSFixed32(4, this.degreeMax_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeSFixed32(5, this.degreeMin_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeFixed32(6, this.pm2P5_);
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getDateTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.type_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeEnumSize(3, this.desc_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeSFixed32Size(4, this.degreeMax_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeSFixed32Size(5, this.degreeMin_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeFixed32Size(6, this.pm2P5_);
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
            if (!(obj instanceof Weather)) {
                return super.equals(obj);
            }
            Weather other = (Weather) obj;
            boolean result7 = 1 != 0 && hasDateTime() == other.hasDateTime();
            if (hasDateTime()) {
                result7 = result7 && getDateTime().equals(other.getDateTime());
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
            if (!result || hasDesc() != other.hasDesc()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasDesc()) {
                if (!result2 || this.desc_ != other.desc_) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasDegreeMax() != other.hasDegreeMax()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasDegreeMax()) {
                if (!result3 || getDegreeMax() != other.getDegreeMax()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasDegreeMin() != other.hasDegreeMin()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasDegreeMin()) {
                if (!result4 || getDegreeMin() != other.getDegreeMin()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasPm2P5() != other.hasPm2P5()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasPm2P5()) {
                if (!result5 || getPm2P5() != other.getPm2P5()) {
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
            if (hasDateTime()) {
                hash = (((hash * 37) + 1) * 53) + getDateTime().hashCode();
            }
            if (hasType()) {
                hash = (((hash * 37) + 2) * 53) + this.type_;
            }
            if (hasDesc()) {
                hash = (((hash * 37) + 3) * 53) + this.desc_;
            }
            if (hasDegreeMax()) {
                hash = (((hash * 37) + 4) * 53) + getDegreeMax();
            }
            if (hasDegreeMin()) {
                hash = (((hash * 37) + 5) * 53) + getDegreeMin();
            }
            if (hasPm2P5()) {
                hash = (((hash * 37) + 6) * 53) + getPm2P5();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static Weather parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Weather) PARSER.parseFrom(data);
        }

        public static Weather parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Weather) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Weather parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Weather) PARSER.parseFrom(data);
        }

        public static Weather parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Weather) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Weather parseFrom(InputStream input) throws IOException {
            return (Weather) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Weather parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Weather) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Weather parseDelimitedFrom(InputStream input) throws IOException {
            return (Weather) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Weather parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Weather) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Weather parseFrom(CodedInputStream input) throws IOException {
            return (Weather) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Weather parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Weather) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Weather prototype) {
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

        public static Weather getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Weather> parser() {
            return PARSER;
        }

        public Parser<Weather> getParserForType() {
            return PARSER;
        }

        public Weather getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class WeatherConfirm extends GeneratedMessageV3 implements WeatherConfirmOrBuilder {
        private static final WeatherConfirm DEFAULT_INSTANCE = new WeatherConfirm();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<WeatherConfirm> PARSER = new AbstractParser<WeatherConfirm>() {
            public WeatherConfirm parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new WeatherConfirm(input, extensionRegistry);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements WeatherConfirmOrBuilder {
            private int bitField0_;
            private int operation_;
            private boolean ret_;

            public static final Descriptor getDescriptor() {
                return WeatherOuterClass.internal_static_WeatherConfirm_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return WeatherOuterClass.internal_static_WeatherConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherConfirm.class, Builder.class);
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
                if (WeatherConfirm.alwaysUseFieldBuilders) {
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
                return WeatherOuterClass.internal_static_WeatherConfirm_descriptor;
            }

            public WeatherConfirm getDefaultInstanceForType() {
                return WeatherConfirm.getDefaultInstance();
            }

            public WeatherConfirm build() {
                WeatherConfirm result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public WeatherConfirm buildPartial() {
                WeatherConfirm result = new WeatherConfirm((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (other instanceof WeatherConfirm) {
                    return mergeFrom((WeatherConfirm) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WeatherConfirm other) {
                if (other != WeatherConfirm.getDefaultInstance()) {
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
                WeatherConfirm parsedMessage = null;
                try {
                    WeatherConfirm parsedMessage2 = (WeatherConfirm) WeatherConfirm.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    WeatherConfirm parsedMessage3 = (WeatherConfirm) e.getUnfinishedMessage();
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

            public WeatherOperation getOperation() {
                WeatherOperation result = WeatherOperation.valueOf(this.operation_);
                return result == null ? WeatherOperation.ADD : result;
            }

            public Builder setOperation(WeatherOperation value) {
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

        private WeatherConfirm(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private WeatherConfirm() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
            this.ret_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private WeatherConfirm(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (WeatherOperation.valueOf(rawValue) != null) {
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
            return WeatherOuterClass.internal_static_WeatherConfirm_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WeatherOuterClass.internal_static_WeatherConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherConfirm.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public WeatherOperation getOperation() {
            WeatherOperation result = WeatherOperation.valueOf(this.operation_);
            return result == null ? WeatherOperation.ADD : result;
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
            if (!(obj instanceof WeatherConfirm)) {
                return super.equals(obj);
            }
            WeatherConfirm other = (WeatherConfirm) obj;
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

        public static WeatherConfirm parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WeatherConfirm) PARSER.parseFrom(data);
        }

        public static WeatherConfirm parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherConfirm parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WeatherConfirm) PARSER.parseFrom(data);
        }

        public static WeatherConfirm parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherConfirm parseFrom(InputStream input) throws IOException {
            return (WeatherConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherConfirm parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherConfirm parseDelimitedFrom(InputStream input) throws IOException {
            return (WeatherConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WeatherConfirm parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherConfirm parseFrom(CodedInputStream input) throws IOException {
            return (WeatherConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherConfirm parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WeatherConfirm prototype) {
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

        public static WeatherConfirm getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WeatherConfirm> parser() {
            return PARSER;
        }

        public Parser<WeatherConfirm> getParserForType() {
            return PARSER;
        }

        public WeatherConfirm getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum WeatherDesc implements ProtocolMessageEnum {
        Sunny(0),
        Cloudy(1),
        Overcast(2),
        LightRain(3),
        ModerateRain(4),
        HeavyRain(5),
        Rainstorm(6),
        Snow(7),
        Haze(8),
        SandStorm(9),
        CloudyTurnToFine(10),
        Thunderstorm(11);
        
        public static final int CloudyTurnToFine_VALUE = 10;
        public static final int Cloudy_VALUE = 1;
        public static final int Haze_VALUE = 8;
        public static final int HeavyRain_VALUE = 5;
        public static final int LightRain_VALUE = 3;
        public static final int ModerateRain_VALUE = 4;
        public static final int Overcast_VALUE = 2;
        public static final int Rainstorm_VALUE = 6;
        public static final int SandStorm_VALUE = 9;
        public static final int Snow_VALUE = 7;
        public static final int Sunny_VALUE = 0;
        public static final int Thunderstorm_VALUE = 11;
        private static final WeatherDesc[] VALUES = null;
        private static final EnumLiteMap<WeatherDesc> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<WeatherDesc>() {
                public WeatherDesc findValueByNumber(int number) {
                    return WeatherDesc.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static WeatherDesc valueOf(int value2) {
            return forNumber(value2);
        }

        public static WeatherDesc forNumber(int value2) {
            switch (value2) {
                case 0:
                    return Sunny;
                case 1:
                    return Cloudy;
                case 2:
                    return Overcast;
                case 3:
                    return LightRain;
                case 4:
                    return ModerateRain;
                case 5:
                    return HeavyRain;
                case 6:
                    return Rainstorm;
                case 7:
                    return Snow;
                case 8:
                    return Haze;
                case 9:
                    return SandStorm;
                case 10:
                    return CloudyTurnToFine;
                case 11:
                    return Thunderstorm;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<WeatherDesc> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) WeatherOuterClass.getDescriptor().getEnumTypes().get(1);
        }

        public static WeatherDesc valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private WeatherDesc(int value2) {
            this.value = value2;
        }
    }

    public static final class WeatherGroup extends GeneratedMessageV3 implements WeatherGroupOrBuilder {
        public static final int DATA_FIELD_NUMBER = 2;
        private static final WeatherGroup DEFAULT_INSTANCE = new WeatherGroup();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<WeatherGroup> PARSER = new AbstractParser<WeatherGroup>() {
            public WeatherGroup parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new WeatherGroup(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public List<Weather> data_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements WeatherGroupOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilderV3<Weather, Builder, WeatherOrBuilder> dataBuilder_;
            private List<Weather> data_;
            private int hash_;

            public static final Descriptor getDescriptor() {
                return WeatherOuterClass.internal_static_WeatherGroup_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return WeatherOuterClass.internal_static_WeatherGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherGroup.class, Builder.class);
            }

            private Builder() {
                this.data_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.data_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (WeatherGroup.alwaysUseFieldBuilders) {
                    getDataFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.dataBuilder_ == null) {
                    this.data_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.dataBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return WeatherOuterClass.internal_static_WeatherGroup_descriptor;
            }

            public WeatherGroup getDefaultInstanceForType() {
                return WeatherGroup.getDefaultInstance();
            }

            public WeatherGroup build() {
                WeatherGroup result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public WeatherGroup buildPartial() {
                WeatherGroup result = new WeatherGroup((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.dataBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.data_ = Collections.unmodifiableList(this.data_);
                        this.bitField0_ &= -3;
                    }
                    result.data_ = this.data_;
                } else {
                    result.data_ = this.dataBuilder_.build();
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
                if (other instanceof WeatherGroup) {
                    return mergeFrom((WeatherGroup) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WeatherGroup other) {
                RepeatedFieldBuilderV3<Weather, Builder, WeatherOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != WeatherGroup.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (this.dataBuilder_ == null) {
                        if (!other.data_.isEmpty()) {
                            if (this.data_.isEmpty()) {
                                this.data_ = other.data_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureDataIsMutable();
                                this.data_.addAll(other.data_);
                            }
                            onChanged();
                        }
                    } else if (!other.data_.isEmpty()) {
                        if (this.dataBuilder_.isEmpty()) {
                            this.dataBuilder_.dispose();
                            this.dataBuilder_ = null;
                            this.data_ = other.data_;
                            this.bitField0_ &= -3;
                            if (WeatherGroup.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getDataFieldBuilder();
                            }
                            this.dataBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.dataBuilder_.addAllMessages(other.data_);
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
                for (int i = 0; i < getDataCount(); i++) {
                    if (!getData(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                WeatherGroup parsedMessage = null;
                try {
                    WeatherGroup parsedMessage2 = (WeatherGroup) WeatherGroup.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    WeatherGroup parsedMessage3 = (WeatherGroup) e.getUnfinishedMessage();
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

            private void ensureDataIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.data_ = new ArrayList(this.data_);
                    this.bitField0_ |= 2;
                }
            }

            public List<Weather> getDataList() {
                if (this.dataBuilder_ == null) {
                    return Collections.unmodifiableList(this.data_);
                }
                return this.dataBuilder_.getMessageList();
            }

            public int getDataCount() {
                if (this.dataBuilder_ == null) {
                    return this.data_.size();
                }
                return this.dataBuilder_.getCount();
            }

            public Weather getData(int index) {
                if (this.dataBuilder_ == null) {
                    return (Weather) this.data_.get(index);
                }
                return (Weather) this.dataBuilder_.getMessage(index);
            }

            public Builder setData(int index, Weather value) {
                if (this.dataBuilder_ != null) {
                    this.dataBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureDataIsMutable();
                    this.data_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setData(int index, Builder builderForValue) {
                if (this.dataBuilder_ == null) {
                    ensureDataIsMutable();
                    this.data_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.dataBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addData(Weather value) {
                if (this.dataBuilder_ != null) {
                    this.dataBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureDataIsMutable();
                    this.data_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addData(int index, Weather value) {
                if (this.dataBuilder_ != null) {
                    this.dataBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureDataIsMutable();
                    this.data_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addData(Builder builderForValue) {
                if (this.dataBuilder_ == null) {
                    ensureDataIsMutable();
                    this.data_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.dataBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addData(int index, Builder builderForValue) {
                if (this.dataBuilder_ == null) {
                    ensureDataIsMutable();
                    this.data_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.dataBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllData(Iterable<? extends Weather> values) {
                if (this.dataBuilder_ == null) {
                    ensureDataIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.data_);
                    onChanged();
                } else {
                    this.dataBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearData() {
                if (this.dataBuilder_ == null) {
                    this.data_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.dataBuilder_.clear();
                }
                return this;
            }

            public Builder removeData(int index) {
                if (this.dataBuilder_ == null) {
                    ensureDataIsMutable();
                    this.data_.remove(index);
                    onChanged();
                } else {
                    this.dataBuilder_.remove(index);
                }
                return this;
            }

            public Builder getDataBuilder(int index) {
                return (Builder) getDataFieldBuilder().getBuilder(index);
            }

            public WeatherOrBuilder getDataOrBuilder(int index) {
                if (this.dataBuilder_ == null) {
                    return (WeatherOrBuilder) this.data_.get(index);
                }
                return (WeatherOrBuilder) this.dataBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends WeatherOrBuilder> getDataOrBuilderList() {
                if (this.dataBuilder_ != null) {
                    return this.dataBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.data_);
            }

            public Builder addDataBuilder() {
                return (Builder) getDataFieldBuilder().addBuilder(Weather.getDefaultInstance());
            }

            public Builder addDataBuilder(int index) {
                return (Builder) getDataFieldBuilder().addBuilder(index, Weather.getDefaultInstance());
            }

            public List<Builder> getDataBuilderList() {
                return getDataFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Weather, Builder, WeatherOrBuilder> getDataFieldBuilder() {
                if (this.dataBuilder_ == null) {
                    this.dataBuilder_ = new RepeatedFieldBuilderV3<>(this.data_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                return this.dataBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private WeatherGroup(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private WeatherGroup() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.data_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private WeatherGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.data_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.data_.add(input.readMessage(Weather.PARSER, extensionRegistry));
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
                        this.data_ = Collections.unmodifiableList(this.data_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.data_ = Collections.unmodifiableList(this.data_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return WeatherOuterClass.internal_static_WeatherGroup_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WeatherOuterClass.internal_static_WeatherGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherGroup.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<Weather> getDataList() {
            return this.data_;
        }

        public List<? extends WeatherOrBuilder> getDataOrBuilderList() {
            return this.data_;
        }

        public int getDataCount() {
            return this.data_.size();
        }

        public Weather getData(int index) {
            return (Weather) this.data_.get(index);
        }

        public WeatherOrBuilder getDataOrBuilder(int index) {
            return (WeatherOrBuilder) this.data_.get(index);
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
            for (int i = 0; i < getDataCount(); i++) {
                if (!getData(i).isInitialized()) {
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
            for (int i = 0; i < this.data_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.data_.get(i));
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
            for (int i = 0; i < this.data_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.data_.get(i));
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
            if (!(obj instanceof WeatherGroup)) {
                return super.equals(obj);
            }
            WeatherGroup other = (WeatherGroup) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getDataList().equals(other.getDataList())) {
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
            if (getDataCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getDataList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static WeatherGroup parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WeatherGroup) PARSER.parseFrom(data);
        }

        public static WeatherGroup parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherGroup parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WeatherGroup) PARSER.parseFrom(data);
        }

        public static WeatherGroup parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherGroup parseFrom(InputStream input) throws IOException {
            return (WeatherGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherGroup parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherGroup parseDelimitedFrom(InputStream input) throws IOException {
            return (WeatherGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WeatherGroup parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherGroup parseFrom(CodedInputStream input) throws IOException {
            return (WeatherGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherGroup parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WeatherGroup prototype) {
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

        public static WeatherGroup getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WeatherGroup> parser() {
            return PARSER;
        }

        public Parser<WeatherGroup> getParserForType() {
            return PARSER;
        }

        public WeatherGroup getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class WeatherNotification extends GeneratedMessageV3 implements WeatherNotificationOrBuilder {
        private static final WeatherNotification DEFAULT_INSTANCE = new WeatherNotification();
        public static final int GROUP_FIELD_NUMBER = 2;
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<WeatherNotification> PARSER = new AbstractParser<WeatherNotification>() {
            public WeatherNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new WeatherNotification(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public WeatherGroup group_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements WeatherNotificationOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<WeatherGroup, Builder, WeatherGroupOrBuilder> groupBuilder_;
            private WeatherGroup group_;
            private int operation_;

            public static final Descriptor getDescriptor() {
                return WeatherOuterClass.internal_static_WeatherNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return WeatherOuterClass.internal_static_WeatherNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherNotification.class, Builder.class);
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
                if (WeatherNotification.alwaysUseFieldBuilders) {
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
                return WeatherOuterClass.internal_static_WeatherNotification_descriptor;
            }

            public WeatherNotification getDefaultInstanceForType() {
                return WeatherNotification.getDefaultInstance();
            }

            public WeatherNotification build() {
                WeatherNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public WeatherNotification buildPartial() {
                WeatherNotification result = new WeatherNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                    result.group_ = (WeatherGroup) this.groupBuilder_.build();
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
                if (other instanceof WeatherNotification) {
                    return mergeFrom((WeatherNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WeatherNotification other) {
                if (other != WeatherNotification.getDefaultInstance()) {
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
                WeatherNotification parsedMessage = null;
                try {
                    WeatherNotification parsedMessage2 = (WeatherNotification) WeatherNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    WeatherNotification parsedMessage3 = (WeatherNotification) e.getUnfinishedMessage();
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

            public WeatherOperation getOperation() {
                WeatherOperation result = WeatherOperation.valueOf(this.operation_);
                return result == null ? WeatherOperation.ADD : result;
            }

            public Builder setOperation(WeatherOperation value) {
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

            public WeatherGroup getGroup() {
                if (this.groupBuilder_ == null) {
                    return this.group_ == null ? WeatherGroup.getDefaultInstance() : this.group_;
                }
                return (WeatherGroup) this.groupBuilder_.getMessage();
            }

            public Builder setGroup(WeatherGroup value) {
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

            public Builder mergeGroup(WeatherGroup value) {
                if (this.groupBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.group_ == null || this.group_ == WeatherGroup.getDefaultInstance()) {
                        this.group_ = value;
                    } else {
                        this.group_ = WeatherGroup.newBuilder(this.group_).mergeFrom(value).buildPartial();
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

            public WeatherGroupOrBuilder getGroupOrBuilder() {
                if (this.groupBuilder_ != null) {
                    return (WeatherGroupOrBuilder) this.groupBuilder_.getMessageOrBuilder();
                }
                return this.group_ == null ? WeatherGroup.getDefaultInstance() : this.group_;
            }

            private SingleFieldBuilderV3<WeatherGroup, Builder, WeatherGroupOrBuilder> getGroupFieldBuilder() {
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

        private WeatherNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private WeatherNotification() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private WeatherNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (WeatherOperation.valueOf(rawValue) != null) {
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
                            this.group_ = (WeatherGroup) input.readMessage(WeatherGroup.PARSER, extensionRegistry);
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
            return WeatherOuterClass.internal_static_WeatherNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WeatherOuterClass.internal_static_WeatherNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherNotification.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public WeatherOperation getOperation() {
            WeatherOperation result = WeatherOperation.valueOf(this.operation_);
            return result == null ? WeatherOperation.ADD : result;
        }

        public boolean hasGroup() {
            return (this.bitField0_ & 2) == 2;
        }

        public WeatherGroup getGroup() {
            return this.group_ == null ? WeatherGroup.getDefaultInstance() : this.group_;
        }

        public WeatherGroupOrBuilder getGroupOrBuilder() {
            return this.group_ == null ? WeatherGroup.getDefaultInstance() : this.group_;
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
            if (!(obj instanceof WeatherNotification)) {
                return super.equals(obj);
            }
            WeatherNotification other = (WeatherNotification) obj;
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

        public static WeatherNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WeatherNotification) PARSER.parseFrom(data);
        }

        public static WeatherNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WeatherNotification) PARSER.parseFrom(data);
        }

        public static WeatherNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherNotification parseFrom(InputStream input) throws IOException {
            return (WeatherNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (WeatherNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WeatherNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherNotification parseFrom(CodedInputStream input) throws IOException {
            return (WeatherNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WeatherNotification prototype) {
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

        public static WeatherNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WeatherNotification> parser() {
            return PARSER;
        }

        public Parser<WeatherNotification> getParserForType() {
            return PARSER;
        }

        public WeatherNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum WeatherOperation implements ProtocolMessageEnum {
        ADD(0),
        CLEAR(1);
        
        public static final int ADD_VALUE = 0;
        public static final int CLEAR_VALUE = 1;
        private static final WeatherOperation[] VALUES = null;
        private static final EnumLiteMap<WeatherOperation> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<WeatherOperation>() {
                public WeatherOperation findValueByNumber(int number) {
                    return WeatherOperation.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static WeatherOperation valueOf(int value2) {
            return forNumber(value2);
        }

        public static WeatherOperation forNumber(int value2) {
            switch (value2) {
                case 0:
                    return ADD;
                case 1:
                    return CLEAR;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<WeatherOperation> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) WeatherOuterClass.getDescriptor().getEnumTypes().get(0);
        }

        public static WeatherOperation valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private WeatherOperation(int value2) {
            this.value = value2;
        }
    }

    public static final class WeatherParams extends GeneratedMessageV3 implements WeatherParamsOrBuilder {
        private static final WeatherParams DEFAULT_INSTANCE = new WeatherParams();
        public static final int MAX_COUNT_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<WeatherParams> PARSER = new AbstractParser<WeatherParams>() {
            public WeatherParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new WeatherParams(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int maxCount_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements WeatherParamsOrBuilder {
            private int bitField0_;
            private int maxCount_;

            public static final Descriptor getDescriptor() {
                return WeatherOuterClass.internal_static_WeatherParams_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return WeatherOuterClass.internal_static_WeatherParams_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherParams.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (WeatherParams.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.maxCount_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return WeatherOuterClass.internal_static_WeatherParams_descriptor;
            }

            public WeatherParams getDefaultInstanceForType() {
                return WeatherParams.getDefaultInstance();
            }

            public WeatherParams build() {
                WeatherParams result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public WeatherParams buildPartial() {
                WeatherParams result = new WeatherParams((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.maxCount_ = this.maxCount_;
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
                if (other instanceof WeatherParams) {
                    return mergeFrom((WeatherParams) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WeatherParams other) {
                if (other != WeatherParams.getDefaultInstance()) {
                    if (other.hasMaxCount()) {
                        setMaxCount(other.getMaxCount());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasMaxCount()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                WeatherParams parsedMessage = null;
                try {
                    WeatherParams parsedMessage2 = (WeatherParams) WeatherParams.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    WeatherParams parsedMessage3 = (WeatherParams) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasMaxCount() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getMaxCount() {
                return this.maxCount_;
            }

            public Builder setMaxCount(int value) {
                this.bitField0_ |= 1;
                this.maxCount_ = value;
                onChanged();
                return this;
            }

            public Builder clearMaxCount() {
                this.bitField0_ &= -2;
                this.maxCount_ = 0;
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

        private WeatherParams(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private WeatherParams() {
            this.memoizedIsInitialized = -1;
            this.maxCount_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private WeatherParams(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.maxCount_ = input.readFixed32();
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
            return WeatherOuterClass.internal_static_WeatherParams_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WeatherOuterClass.internal_static_WeatherParams_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherParams.class, Builder.class);
        }

        public boolean hasMaxCount() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMaxCount() {
            return this.maxCount_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasMaxCount()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.maxCount_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.maxCount_);
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
            if (!(obj instanceof WeatherParams)) {
                return super.equals(obj);
            }
            WeatherParams other = (WeatherParams) obj;
            boolean result2 = 1 != 0 && hasMaxCount() == other.hasMaxCount();
            if (hasMaxCount()) {
                result2 = result2 && getMaxCount() == other.getMaxCount();
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
            if (hasMaxCount()) {
                hash = (((hash * 37) + 1) * 53) + getMaxCount();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static WeatherParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WeatherParams) PARSER.parseFrom(data);
        }

        public static WeatherParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherParams) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WeatherParams) PARSER.parseFrom(data);
        }

        public static WeatherParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherParams) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherParams parseFrom(InputStream input) throws IOException {
            return (WeatherParams) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherParams) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherParams parseDelimitedFrom(InputStream input) throws IOException {
            return (WeatherParams) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WeatherParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherParams) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherParams parseFrom(CodedInputStream input) throws IOException {
            return (WeatherParams) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherParams) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WeatherParams prototype) {
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

        public static WeatherParams getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WeatherParams> parser() {
            return PARSER;
        }

        public Parser<WeatherParams> getParserForType() {
            return PARSER;
        }

        public WeatherParams getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class WeatherSubscriber extends GeneratedMessageV3 implements WeatherSubscriberOrBuilder {
        public static final int CONFIRM_FIELD_NUMBER = 3;
        private static final WeatherSubscriber DEFAULT_INSTANCE = new WeatherSubscriber();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int PARAMS_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<WeatherSubscriber> PARSER = new AbstractParser<WeatherSubscriber>() {
            public WeatherSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new WeatherSubscriber(input, extensionRegistry);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements WeatherSubscriberOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<WeatherConfirm, Builder, WeatherConfirmOrBuilder> confirmBuilder_;
            private int dataCase_;
            private Object data_;
            private int hash_;
            private SingleFieldBuilderV3<WeatherParams, Builder, WeatherParamsOrBuilder> paramsBuilder_;

            public static final Descriptor getDescriptor() {
                return WeatherOuterClass.internal_static_WeatherSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return WeatherOuterClass.internal_static_WeatherSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherSubscriber.class, Builder.class);
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
                if (WeatherSubscriber.alwaysUseFieldBuilders) {
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
                return WeatherOuterClass.internal_static_WeatherSubscriber_descriptor;
            }

            public WeatherSubscriber getDefaultInstanceForType() {
                return WeatherSubscriber.getDefaultInstance();
            }

            public WeatherSubscriber build() {
                WeatherSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public WeatherSubscriber buildPartial() {
                WeatherSubscriber result = new WeatherSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (other instanceof WeatherSubscriber) {
                    return mergeFrom((WeatherSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WeatherSubscriber other) {
                if (other != WeatherSubscriber.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$WeatherOuterClass$WeatherSubscriber$DataCase[other.getDataCase().ordinal()]) {
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
                WeatherSubscriber parsedMessage = null;
                try {
                    WeatherSubscriber parsedMessage2 = (WeatherSubscriber) WeatherSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    WeatherSubscriber parsedMessage3 = (WeatherSubscriber) e.getUnfinishedMessage();
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

            public WeatherParams getParams() {
                if (this.paramsBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (WeatherParams) this.data_;
                    }
                    return WeatherParams.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (WeatherParams) this.paramsBuilder_.getMessage();
                } else {
                    return WeatherParams.getDefaultInstance();
                }
            }

            public Builder setParams(WeatherParams value) {
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

            public Builder mergeParams(WeatherParams value) {
                if (this.paramsBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == WeatherParams.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = WeatherParams.newBuilder((WeatherParams) this.data_).mergeFrom(value).buildPartial();
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

            public WeatherParamsOrBuilder getParamsOrBuilder() {
                if (this.dataCase_ == 2 && this.paramsBuilder_ != null) {
                    return (WeatherParamsOrBuilder) this.paramsBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (WeatherParams) this.data_;
                }
                return WeatherParams.getDefaultInstance();
            }

            private SingleFieldBuilderV3<WeatherParams, Builder, WeatherParamsOrBuilder> getParamsFieldBuilder() {
                if (this.paramsBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = WeatherParams.getDefaultInstance();
                    }
                    this.paramsBuilder_ = new SingleFieldBuilderV3<>((WeatherParams) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.paramsBuilder_;
            }

            public boolean hasConfirm() {
                return this.dataCase_ == 3;
            }

            public WeatherConfirm getConfirm() {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (WeatherConfirm) this.data_;
                    }
                    return WeatherConfirm.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (WeatherConfirm) this.confirmBuilder_.getMessage();
                } else {
                    return WeatherConfirm.getDefaultInstance();
                }
            }

            public Builder setConfirm(WeatherConfirm value) {
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

            public Builder mergeConfirm(WeatherConfirm value) {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == WeatherConfirm.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = WeatherConfirm.newBuilder((WeatherConfirm) this.data_).mergeFrom(value).buildPartial();
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

            public WeatherConfirmOrBuilder getConfirmOrBuilder() {
                if (this.dataCase_ == 3 && this.confirmBuilder_ != null) {
                    return (WeatherConfirmOrBuilder) this.confirmBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (WeatherConfirm) this.data_;
                }
                return WeatherConfirm.getDefaultInstance();
            }

            private SingleFieldBuilderV3<WeatherConfirm, Builder, WeatherConfirmOrBuilder> getConfirmFieldBuilder() {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = WeatherConfirm.getDefaultInstance();
                    }
                    this.confirmBuilder_ = new SingleFieldBuilderV3<>((WeatherConfirm) this.data_, getParentForChildren(), isClean());
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

        private WeatherSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private WeatherSubscriber() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private WeatherSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = ((WeatherParams) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(WeatherParams.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((WeatherParams) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 3) {
                                subBuilder2 = ((WeatherConfirm) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(WeatherConfirm.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((WeatherConfirm) this.data_);
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
            return WeatherOuterClass.internal_static_WeatherSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WeatherOuterClass.internal_static_WeatherSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(WeatherSubscriber.class, Builder.class);
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

        public WeatherParams getParams() {
            if (this.dataCase_ == 2) {
                return (WeatherParams) this.data_;
            }
            return WeatherParams.getDefaultInstance();
        }

        public WeatherParamsOrBuilder getParamsOrBuilder() {
            if (this.dataCase_ == 2) {
                return (WeatherParams) this.data_;
            }
            return WeatherParams.getDefaultInstance();
        }

        public boolean hasConfirm() {
            return this.dataCase_ == 3;
        }

        public WeatherConfirm getConfirm() {
            if (this.dataCase_ == 3) {
                return (WeatherConfirm) this.data_;
            }
            return WeatherConfirm.getDefaultInstance();
        }

        public WeatherConfirmOrBuilder getConfirmOrBuilder() {
            if (this.dataCase_ == 3) {
                return (WeatherConfirm) this.data_;
            }
            return WeatherConfirm.getDefaultInstance();
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
                output.writeMessage(2, (WeatherParams) this.data_);
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (WeatherConfirm) this.data_);
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
                size2 += CodedOutputStream.computeMessageSize(2, (WeatherParams) this.data_);
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (WeatherConfirm) this.data_);
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
            if (!(obj instanceof WeatherSubscriber)) {
                return super.equals(obj);
            }
            WeatherSubscriber other = (WeatherSubscriber) obj;
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

        public static WeatherSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WeatherSubscriber) PARSER.parseFrom(data);
        }

        public static WeatherSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WeatherSubscriber) PARSER.parseFrom(data);
        }

        public static WeatherSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WeatherSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static WeatherSubscriber parseFrom(InputStream input) throws IOException {
            return (WeatherSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (WeatherSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WeatherSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WeatherSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (WeatherSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WeatherSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WeatherSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WeatherSubscriber prototype) {
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

        public static WeatherSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WeatherSubscriber> parser() {
            return PARSER;
        }

        public Parser<WeatherSubscriber> getParserForType() {
            return PARSER;
        }

        public WeatherSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum WeatherType implements ProtocolMessageEnum {
        EACH_HOUR(0),
        EACH_DAY(1);
        
        public static final int EACH_DAY_VALUE = 1;
        public static final int EACH_HOUR_VALUE = 0;
        private static final WeatherType[] VALUES = null;
        private static final EnumLiteMap<WeatherType> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<WeatherType>() {
                public WeatherType findValueByNumber(int number) {
                    return WeatherType.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static WeatherType valueOf(int value2) {
            return forNumber(value2);
        }

        public static WeatherType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return EACH_HOUR;
                case 1:
                    return EACH_DAY;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<WeatherType> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) WeatherOuterClass.getDescriptor().getEnumTypes().get(2);
        }

        public static WeatherType valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private WeatherType(int value2) {
            this.value = value2;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.WeatherOuterClass$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$WeatherOuterClass$WeatherSubscriber$DataCase = new int[DataCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$WeatherOuterClass$WeatherSubscriber$DataCase[DataCase.PARAMS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$WeatherOuterClass$WeatherSubscriber$DataCase[DataCase.CONFIRM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$WeatherOuterClass$WeatherSubscriber$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public interface WeatherConfirmOrBuilder extends MessageOrBuilder {
        WeatherOperation getOperation();

        boolean getRet();

        boolean hasOperation();

        boolean hasRet();
    }

    public interface WeatherGroupOrBuilder extends MessageOrBuilder {
        Weather getData(int i);

        int getDataCount();

        List<Weather> getDataList();

        WeatherOrBuilder getDataOrBuilder(int i);

        List<? extends WeatherOrBuilder> getDataOrBuilderList();

        int getHash();

        boolean hasHash();
    }

    public interface WeatherNotificationOrBuilder extends MessageOrBuilder {
        WeatherGroup getGroup();

        WeatherGroupOrBuilder getGroupOrBuilder();

        WeatherOperation getOperation();

        boolean hasGroup();

        boolean hasOperation();
    }

    public interface WeatherOrBuilder extends MessageOrBuilder {
        RtTime getDateTime();

        RtTimeOrBuilder getDateTimeOrBuilder();

        int getDegreeMax();

        int getDegreeMin();

        WeatherDesc getDesc();

        int getPm2P5();

        WeatherType getType();

        boolean hasDateTime();

        boolean hasDegreeMax();

        boolean hasDegreeMin();

        boolean hasDesc();

        boolean hasPm2P5();

        boolean hasType();
    }

    public interface WeatherParamsOrBuilder extends MessageOrBuilder {
        int getMaxCount();

        boolean hasMaxCount();
    }

    public interface WeatherSubscriberOrBuilder extends MessageOrBuilder {
        WeatherConfirm getConfirm();

        WeatherConfirmOrBuilder getConfirmOrBuilder();

        DataCase getDataCase();

        int getHash();

        WeatherParams getParams();

        WeatherParamsOrBuilder getParamsOrBuilder();

        boolean hasConfirm();

        boolean hasHash();

        boolean hasParams();
    }

    private WeatherOuterClass() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\rweather.proto\u001a\u0013realtime_data.proto\"\u0001\n\u0007Weather\u0012\u001a\n\tdate_time\u0018\u0001 \u0002(\u000b2\u0007.RtTime\u0012\u001a\n\u0004type\u0018\u0002 \u0002(\u000e2\f.WeatherType\u0012\u001a\n\u0004desc\u0018\u0003 \u0002(\u000e2\f.WeatherDesc\u0012\u0012\n\ndegree_max\u0018\u0004 \u0002(\u000f\u0012\u0012\n\ndegree_min\u0018\u0005 \u0002(\u000f\u0012\r\n\u0005pm2p5\u0018\u0006 \u0002(\u0007\"4\n\fWeatherGroup\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0016\n\u0004data\u0018\u0002 \u0003(\u000b2\b.Weather\"Y\n\u0013WeatherNotification\u0012$\n\toperation\u0018\u0001 \u0002(\u000e2\u0011.WeatherOperation\u0012\u001c\n\u0005group\u0018\u0002 \u0001(\u000b2\r.WeatherGroup\"C\n\u000eWeatherConfirm\u0012$\n\toperation\u0018\u0001 \u0002(\u000e2\u0011.WeatherOperation\u0012\u000b\n\u0003ret\u0018\u0002 \u0002(", "\b\"\"\n\rWeatherParams\u0012\u0011\n\tmax_count\u0018\u0001 \u0002(\u0007\"o\n\u0011WeatherSubscriber\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012 \n\u0006params\u0018\u0002 \u0001(\u000b2\u000e.WeatherParamsH\u0000\u0012\"\n\u0007confirm\u0018\u0003 \u0001(\u000b2\u000f.WeatherConfirmH\u0000B\u0006\n\u0004data*&\n\u0010WeatherOperation\u0012\u0007\n\u0003ADD\u0010\u0000\u0012\t\n\u0005CLEAR\u0010\u0001*¼\u0001\n\u000bWeatherDesc\u0012\t\n\u0005Sunny\u0010\u0000\u0012\n\n\u0006Cloudy\u0010\u0001\u0012\f\n\bOvercast\u0010\u0002\u0012\r\n\tLightRain\u0010\u0003\u0012\u0010\n\fModerateRain\u0010\u0004\u0012\r\n\tHeavyRain\u0010\u0005\u0012\r\n\tRainstorm\u0010\u0006\u0012\b\n\u0004Snow\u0010\u0007\u0012\b\n\u0004Haze\u0010\b\u0012\r\n\tSandStorm\u0010\t\u0012\u0014\n\u0010CloudyTurnToFine\u0010\n\u0012\u0010\n\fThunderstorm\u0010\u000b**\n\u000bWeatherType\u0012\r\n\t", "EACH_HOUR\u0010\u0000\u0012\f\n\bEACH_DAY\u0010\u0001"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                WeatherOuterClass.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
