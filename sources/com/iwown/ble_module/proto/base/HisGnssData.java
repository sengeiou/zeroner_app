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
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.iwown.ble_module.proto.base.RealtimeData.DateTime;
import com.iwown.ble_module.proto.base.RealtimeData.DateTimeOrBuilder;
import com.iwown.ble_module.proto.base.RealtimeData.RtGNSS;
import com.iwown.ble_module.proto.base.RealtimeData.RtGNSSOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HisGnssData {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisDataGNSS_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisDataGNSS_fieldAccessorTable = new FieldAccessorTable(internal_static_HisDataGNSS_descriptor, new String[]{"TimeStamp", "Frequency", "Gnss"});

    public static final class HisDataGNSS extends GeneratedMessageV3 implements HisDataGNSSOrBuilder {
        private static final HisDataGNSS DEFAULT_INSTANCE = new HisDataGNSS();
        public static final int FREQUENCY_FIELD_NUMBER = 2;
        public static final int GNSS_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<HisDataGNSS> PARSER = new AbstractParser<HisDataGNSS>() {
            public HisDataGNSS parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisDataGNSS(input, extensionRegistry);
            }
        };
        public static final int TIME_STAMP_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int frequency_;
        /* access modifiers changed from: private */
        public List<RtGNSS> gnss_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public DateTime timeStamp_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisDataGNSSOrBuilder {
            private int bitField0_;
            private int frequency_;
            private RepeatedFieldBuilderV3<RtGNSS, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder, RtGNSSOrBuilder> gnssBuilder_;
            private List<RtGNSS> gnss_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> timeStampBuilder_;
            private DateTime timeStamp_;

            public static final Descriptor getDescriptor() {
                return HisGnssData.internal_static_HisDataGNSS_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisGnssData.internal_static_HisDataGNSS_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataGNSS.class, Builder.class);
            }

            private Builder() {
                this.timeStamp_ = null;
                this.gnss_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.timeStamp_ = null;
                this.gnss_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisDataGNSS.alwaysUseFieldBuilders) {
                    getTimeStampFieldBuilder();
                    getGnssFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.timeStampBuilder_ == null) {
                    this.timeStamp_ = null;
                } else {
                    this.timeStampBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.frequency_ = 0;
                this.bitField0_ &= -3;
                if (this.gnssBuilder_ == null) {
                    this.gnss_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.gnssBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisGnssData.internal_static_HisDataGNSS_descriptor;
            }

            public HisDataGNSS getDefaultInstanceForType() {
                return HisDataGNSS.getDefaultInstance();
            }

            public HisDataGNSS build() {
                HisDataGNSS result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisDataGNSS buildPartial() {
                HisDataGNSS result = new HisDataGNSS((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeStampBuilder_ == null) {
                    result.timeStamp_ = this.timeStamp_;
                } else {
                    result.timeStamp_ = (DateTime) this.timeStampBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.frequency_ = this.frequency_;
                if (this.gnssBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.gnss_ = Collections.unmodifiableList(this.gnss_);
                        this.bitField0_ &= -5;
                    }
                    result.gnss_ = this.gnss_;
                } else {
                    result.gnss_ = this.gnssBuilder_.build();
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
                if (other instanceof HisDataGNSS) {
                    return mergeFrom((HisDataGNSS) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisDataGNSS other) {
                RepeatedFieldBuilderV3<RtGNSS, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder, RtGNSSOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != HisDataGNSS.getDefaultInstance()) {
                    if (other.hasTimeStamp()) {
                        mergeTimeStamp(other.getTimeStamp());
                    }
                    if (other.hasFrequency()) {
                        setFrequency(other.getFrequency());
                    }
                    if (this.gnssBuilder_ == null) {
                        if (!other.gnss_.isEmpty()) {
                            if (this.gnss_.isEmpty()) {
                                this.gnss_ = other.gnss_;
                                this.bitField0_ &= -5;
                            } else {
                                ensureGnssIsMutable();
                                this.gnss_.addAll(other.gnss_);
                            }
                            onChanged();
                        }
                    } else if (!other.gnss_.isEmpty()) {
                        if (this.gnssBuilder_.isEmpty()) {
                            this.gnssBuilder_.dispose();
                            this.gnssBuilder_ = null;
                            this.gnss_ = other.gnss_;
                            this.bitField0_ &= -5;
                            if (HisDataGNSS.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getGnssFieldBuilder();
                            }
                            this.gnssBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.gnssBuilder_.addAllMessages(other.gnss_);
                        }
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasTimeStamp() || !hasFrequency() || !getTimeStamp().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < getGnssCount(); i++) {
                    if (!getGnss(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisDataGNSS parsedMessage = null;
                try {
                    HisDataGNSS parsedMessage2 = (HisDataGNSS) HisDataGNSS.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisDataGNSS parsedMessage3 = (HisDataGNSS) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasTimeStamp() {
                return (this.bitField0_ & 1) == 1;
            }

            public DateTime getTimeStamp() {
                if (this.timeStampBuilder_ == null) {
                    return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
                }
                return (DateTime) this.timeStampBuilder_.getMessage();
            }

            public Builder setTimeStamp(DateTime value) {
                if (this.timeStampBuilder_ != null) {
                    this.timeStampBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.timeStamp_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTimeStamp(com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder builderForValue) {
                if (this.timeStampBuilder_ == null) {
                    this.timeStamp_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timeStampBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTimeStamp(DateTime value) {
                if (this.timeStampBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.timeStamp_ == null || this.timeStamp_ == DateTime.getDefaultInstance()) {
                        this.timeStamp_ = value;
                    } else {
                        this.timeStamp_ = DateTime.newBuilder(this.timeStamp_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.timeStampBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTimeStamp() {
                if (this.timeStampBuilder_ == null) {
                    this.timeStamp_ = null;
                    onChanged();
                } else {
                    this.timeStampBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder getTimeStampBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder) getTimeStampFieldBuilder().getBuilder();
            }

            public DateTimeOrBuilder getTimeStampOrBuilder() {
                if (this.timeStampBuilder_ != null) {
                    return (DateTimeOrBuilder) this.timeStampBuilder_.getMessageOrBuilder();
                }
                return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
            }

            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> getTimeStampFieldBuilder() {
                if (this.timeStampBuilder_ == null) {
                    this.timeStampBuilder_ = new SingleFieldBuilderV3<>(getTimeStamp(), getParentForChildren(), isClean());
                    this.timeStamp_ = null;
                }
                return this.timeStampBuilder_;
            }

            public boolean hasFrequency() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getFrequency() {
                return this.frequency_;
            }

            public Builder setFrequency(int value) {
                this.bitField0_ |= 2;
                this.frequency_ = value;
                onChanged();
                return this;
            }

            public Builder clearFrequency() {
                this.bitField0_ &= -3;
                this.frequency_ = 0;
                onChanged();
                return this;
            }

            private void ensureGnssIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.gnss_ = new ArrayList(this.gnss_);
                    this.bitField0_ |= 4;
                }
            }

            public List<RtGNSS> getGnssList() {
                if (this.gnssBuilder_ == null) {
                    return Collections.unmodifiableList(this.gnss_);
                }
                return this.gnssBuilder_.getMessageList();
            }

            public int getGnssCount() {
                if (this.gnssBuilder_ == null) {
                    return this.gnss_.size();
                }
                return this.gnssBuilder_.getCount();
            }

            public RtGNSS getGnss(int index) {
                if (this.gnssBuilder_ == null) {
                    return (RtGNSS) this.gnss_.get(index);
                }
                return (RtGNSS) this.gnssBuilder_.getMessage(index);
            }

            public Builder setGnss(int index, RtGNSS value) {
                if (this.gnssBuilder_ != null) {
                    this.gnssBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureGnssIsMutable();
                    this.gnss_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setGnss(int index, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder builderForValue) {
                if (this.gnssBuilder_ == null) {
                    ensureGnssIsMutable();
                    this.gnss_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.gnssBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addGnss(RtGNSS value) {
                if (this.gnssBuilder_ != null) {
                    this.gnssBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureGnssIsMutable();
                    this.gnss_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addGnss(int index, RtGNSS value) {
                if (this.gnssBuilder_ != null) {
                    this.gnssBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureGnssIsMutable();
                    this.gnss_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addGnss(com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder builderForValue) {
                if (this.gnssBuilder_ == null) {
                    ensureGnssIsMutable();
                    this.gnss_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.gnssBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addGnss(int index, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder builderForValue) {
                if (this.gnssBuilder_ == null) {
                    ensureGnssIsMutable();
                    this.gnss_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.gnssBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllGnss(Iterable<? extends RtGNSS> values) {
                if (this.gnssBuilder_ == null) {
                    ensureGnssIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.gnss_);
                    onChanged();
                } else {
                    this.gnssBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearGnss() {
                if (this.gnssBuilder_ == null) {
                    this.gnss_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.gnssBuilder_.clear();
                }
                return this;
            }

            public Builder removeGnss(int index) {
                if (this.gnssBuilder_ == null) {
                    ensureGnssIsMutable();
                    this.gnss_.remove(index);
                    onChanged();
                } else {
                    this.gnssBuilder_.remove(index);
                }
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder getGnssBuilder(int index) {
                return (com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder) getGnssFieldBuilder().getBuilder(index);
            }

            public RtGNSSOrBuilder getGnssOrBuilder(int index) {
                if (this.gnssBuilder_ == null) {
                    return (RtGNSSOrBuilder) this.gnss_.get(index);
                }
                return (RtGNSSOrBuilder) this.gnssBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends RtGNSSOrBuilder> getGnssOrBuilderList() {
                if (this.gnssBuilder_ != null) {
                    return this.gnssBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.gnss_);
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder addGnssBuilder() {
                return (com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder) getGnssFieldBuilder().addBuilder(RtGNSS.getDefaultInstance());
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder addGnssBuilder(int index) {
                return (com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder) getGnssFieldBuilder().addBuilder(index, RtGNSS.getDefaultInstance());
            }

            public List<com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder> getGnssBuilderList() {
                return getGnssFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<RtGNSS, com.iwown.ble_module.proto.base.RealtimeData.RtGNSS.Builder, RtGNSSOrBuilder> getGnssFieldBuilder() {
                if (this.gnssBuilder_ == null) {
                    this.gnssBuilder_ = new RepeatedFieldBuilderV3<>(this.gnss_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.gnss_ = null;
                }
                return this.gnssBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisDataGNSS(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisDataGNSS() {
            this.memoizedIsInitialized = -1;
            this.frequency_ = 0;
            this.gnss_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisDataGNSS(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 10:
                            com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.timeStamp_.toBuilder();
                            }
                            this.timeStamp_ = (DateTime) input.readMessage(DateTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.timeStamp_);
                                this.timeStamp_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.frequency_ = input.readFixed32();
                            break;
                        case 26:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.gnss_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.gnss_.add(input.readMessage(RtGNSS.PARSER, extensionRegistry));
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
                        this.gnss_ = Collections.unmodifiableList(this.gnss_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.gnss_ = Collections.unmodifiableList(this.gnss_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return HisGnssData.internal_static_HisDataGNSS_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisGnssData.internal_static_HisDataGNSS_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataGNSS.class, Builder.class);
        }

        public boolean hasTimeStamp() {
            return (this.bitField0_ & 1) == 1;
        }

        public DateTime getTimeStamp() {
            return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
        }

        public DateTimeOrBuilder getTimeStampOrBuilder() {
            return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
        }

        public boolean hasFrequency() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getFrequency() {
            return this.frequency_;
        }

        public List<RtGNSS> getGnssList() {
            return this.gnss_;
        }

        public List<? extends RtGNSSOrBuilder> getGnssOrBuilderList() {
            return this.gnss_;
        }

        public int getGnssCount() {
            return this.gnss_.size();
        }

        public RtGNSS getGnss(int index) {
            return (RtGNSS) this.gnss_.get(index);
        }

        public RtGNSSOrBuilder getGnssOrBuilder(int index) {
            return (RtGNSSOrBuilder) this.gnss_.get(index);
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasTimeStamp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFrequency()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getTimeStamp().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                for (int i = 0; i < getGnssCount(); i++) {
                    if (!getGnss(i).isInitialized()) {
                        this.memoizedIsInitialized = 0;
                        return false;
                    }
                }
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTimeStamp());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.frequency_);
            }
            for (int i = 0; i < this.gnss_.size(); i++) {
                output.writeMessage(3, (MessageLite) this.gnss_.get(i));
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getTimeStamp());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.frequency_);
            }
            for (int i = 0; i < this.gnss_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(3, (MessageLite) this.gnss_.get(i));
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
            if (!(obj instanceof HisDataGNSS)) {
                return super.equals(obj);
            }
            HisDataGNSS other = (HisDataGNSS) obj;
            boolean result4 = 1 != 0 && hasTimeStamp() == other.hasTimeStamp();
            if (hasTimeStamp()) {
                result4 = result4 && getTimeStamp().equals(other.getTimeStamp());
            }
            if (!result4 || hasFrequency() != other.hasFrequency()) {
                result = false;
            } else {
                result = true;
            }
            if (hasFrequency()) {
                if (!result || getFrequency() != other.getFrequency()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || !getGnssList().equals(other.getGnssList())) {
                result2 = false;
            } else {
                result2 = true;
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
            if (hasTimeStamp()) {
                hash = (((hash * 37) + 1) * 53) + getTimeStamp().hashCode();
            }
            if (hasFrequency()) {
                hash = (((hash * 37) + 2) * 53) + getFrequency();
            }
            if (getGnssCount() > 0) {
                hash = (((hash * 37) + 3) * 53) + getGnssList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisDataGNSS parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisDataGNSS) PARSER.parseFrom(data);
        }

        public static HisDataGNSS parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataGNSS) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataGNSS parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisDataGNSS) PARSER.parseFrom(data);
        }

        public static HisDataGNSS parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataGNSS) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataGNSS parseFrom(InputStream input) throws IOException {
            return (HisDataGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataGNSS parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataGNSS parseDelimitedFrom(InputStream input) throws IOException {
            return (HisDataGNSS) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisDataGNSS parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataGNSS) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataGNSS parseFrom(CodedInputStream input) throws IOException {
            return (HisDataGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataGNSS parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisDataGNSS prototype) {
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

        public static HisDataGNSS getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisDataGNSS> parser() {
            return PARSER;
        }

        public Parser<HisDataGNSS> getParserForType() {
            return PARSER;
        }

        public HisDataGNSS getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface HisDataGNSSOrBuilder extends MessageOrBuilder {
        int getFrequency();

        RtGNSS getGnss(int i);

        int getGnssCount();

        List<RtGNSS> getGnssList();

        RtGNSSOrBuilder getGnssOrBuilder(int i);

        List<? extends RtGNSSOrBuilder> getGnssOrBuilderList();

        DateTime getTimeStamp();

        DateTimeOrBuilder getTimeStampOrBuilder();

        boolean hasFrequency();

        boolean hasTimeStamp();
    }

    private HisGnssData() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0013his_gnss_data.proto\u001a\u0013realtime_data.proto\"V\n\u000bHisDataGNSS\u0012\u001d\n\ntime_stamp\u0018\u0001 \u0002(\u000b2\t.DateTime\u0012\u0011\n\tfrequency\u0018\u0002 \u0002(\u0007\u0012\u0015\n\u0004gnss\u0018\u0003 \u0003(\u000b2\u0007.RtGNSS"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                HisGnssData.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
