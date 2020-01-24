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
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class ConParams {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_ConParamsUpdate_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_ConParamsUpdate_fieldAccessorTable = new FieldAccessorTable(internal_static_ConParamsUpdate_descriptor, new String[]{"Mtu", "IntervalMs", "TimeoutMs", "MaxSize"});

    public static final class ConParamsUpdate extends GeneratedMessageV3 implements ConParamsUpdateOrBuilder {
        private static final ConParamsUpdate DEFAULT_INSTANCE = new ConParamsUpdate();
        public static final int INTERVAL_MS_FIELD_NUMBER = 2;
        public static final int MAX_SIZE_FIELD_NUMBER = 4;
        public static final int MTU_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<ConParamsUpdate> PARSER = new AbstractParser<ConParamsUpdate>() {
            public ConParamsUpdate parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ConParamsUpdate(input, extensionRegistry);
            }
        };
        public static final int TIMEOUT_MS_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int intervalMs_;
        /* access modifiers changed from: private */
        public int maxSize_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int mtu_;
        /* access modifiers changed from: private */
        public int timeoutMs_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements ConParamsUpdateOrBuilder {
            private int bitField0_;
            private int intervalMs_;
            private int maxSize_;
            private int mtu_;
            private int timeoutMs_;

            public static final Descriptor getDescriptor() {
                return ConParams.internal_static_ConParamsUpdate_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return ConParams.internal_static_ConParamsUpdate_fieldAccessorTable.ensureFieldAccessorsInitialized(ConParamsUpdate.class, Builder.class);
            }

            private Builder() {
                this.maxSize_ = 512;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.maxSize_ = 512;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (ConParamsUpdate.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.mtu_ = 0;
                this.bitField0_ &= -2;
                this.intervalMs_ = 0;
                this.bitField0_ &= -3;
                this.timeoutMs_ = 0;
                this.bitField0_ &= -5;
                this.maxSize_ = 512;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return ConParams.internal_static_ConParamsUpdate_descriptor;
            }

            public ConParamsUpdate getDefaultInstanceForType() {
                return ConParamsUpdate.getDefaultInstance();
            }

            public ConParamsUpdate build() {
                ConParamsUpdate result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public ConParamsUpdate buildPartial() {
                ConParamsUpdate result = new ConParamsUpdate((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.mtu_ = this.mtu_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.intervalMs_ = this.intervalMs_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.timeoutMs_ = this.timeoutMs_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.maxSize_ = this.maxSize_;
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
                if (other instanceof ConParamsUpdate) {
                    return mergeFrom((ConParamsUpdate) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ConParamsUpdate other) {
                if (other != ConParamsUpdate.getDefaultInstance()) {
                    if (other.hasMtu()) {
                        setMtu(other.getMtu());
                    }
                    if (other.hasIntervalMs()) {
                        setIntervalMs(other.getIntervalMs());
                    }
                    if (other.hasTimeoutMs()) {
                        setTimeoutMs(other.getTimeoutMs());
                    }
                    if (other.hasMaxSize()) {
                        setMaxSize(other.getMaxSize());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ConParamsUpdate parsedMessage = null;
                try {
                    ConParamsUpdate parsedMessage2 = (ConParamsUpdate) ConParamsUpdate.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    ConParamsUpdate parsedMessage3 = (ConParamsUpdate) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasMtu() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getMtu() {
                return this.mtu_;
            }

            public Builder setMtu(int value) {
                this.bitField0_ |= 1;
                this.mtu_ = value;
                onChanged();
                return this;
            }

            public Builder clearMtu() {
                this.bitField0_ &= -2;
                this.mtu_ = 0;
                onChanged();
                return this;
            }

            public boolean hasIntervalMs() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getIntervalMs() {
                return this.intervalMs_;
            }

            public Builder setIntervalMs(int value) {
                this.bitField0_ |= 2;
                this.intervalMs_ = value;
                onChanged();
                return this;
            }

            public Builder clearIntervalMs() {
                this.bitField0_ &= -3;
                this.intervalMs_ = 0;
                onChanged();
                return this;
            }

            public boolean hasTimeoutMs() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getTimeoutMs() {
                return this.timeoutMs_;
            }

            public Builder setTimeoutMs(int value) {
                this.bitField0_ |= 4;
                this.timeoutMs_ = value;
                onChanged();
                return this;
            }

            public Builder clearTimeoutMs() {
                this.bitField0_ &= -5;
                this.timeoutMs_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMaxSize() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getMaxSize() {
                return this.maxSize_;
            }

            public Builder setMaxSize(int value) {
                this.bitField0_ |= 8;
                this.maxSize_ = value;
                onChanged();
                return this;
            }

            public Builder clearMaxSize() {
                this.bitField0_ &= -9;
                this.maxSize_ = 512;
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

        private ConParamsUpdate(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private ConParamsUpdate() {
            this.memoizedIsInitialized = -1;
            this.mtu_ = 0;
            this.intervalMs_ = 0;
            this.timeoutMs_ = 0;
            this.maxSize_ = 512;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ConParamsUpdate(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.mtu_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.intervalMs_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.timeoutMs_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.maxSize_ = input.readFixed32();
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
            return ConParams.internal_static_ConParamsUpdate_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ConParams.internal_static_ConParamsUpdate_fieldAccessorTable.ensureFieldAccessorsInitialized(ConParamsUpdate.class, Builder.class);
        }

        public boolean hasMtu() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMtu() {
            return this.mtu_;
        }

        public boolean hasIntervalMs() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getIntervalMs() {
            return this.intervalMs_;
        }

        public boolean hasTimeoutMs() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getTimeoutMs() {
            return this.timeoutMs_;
        }

        public boolean hasMaxSize() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getMaxSize() {
            return this.maxSize_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.mtu_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.intervalMs_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.timeoutMs_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.maxSize_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.mtu_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.intervalMs_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.timeoutMs_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.maxSize_);
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
            if (!(obj instanceof ConParamsUpdate)) {
                return super.equals(obj);
            }
            ConParamsUpdate other = (ConParamsUpdate) obj;
            boolean result5 = 1 != 0 && hasMtu() == other.hasMtu();
            if (hasMtu()) {
                result5 = result5 && getMtu() == other.getMtu();
            }
            if (!result5 || hasIntervalMs() != other.hasIntervalMs()) {
                result = false;
            } else {
                result = true;
            }
            if (hasIntervalMs()) {
                if (!result || getIntervalMs() != other.getIntervalMs()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasTimeoutMs() != other.hasTimeoutMs()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasTimeoutMs()) {
                if (!result2 || getTimeoutMs() != other.getTimeoutMs()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasMaxSize() != other.hasMaxSize()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasMaxSize()) {
                if (!result3 || getMaxSize() != other.getMaxSize()) {
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
            if (hasMtu()) {
                hash = (((hash * 37) + 1) * 53) + getMtu();
            }
            if (hasIntervalMs()) {
                hash = (((hash * 37) + 2) * 53) + getIntervalMs();
            }
            if (hasTimeoutMs()) {
                hash = (((hash * 37) + 3) * 53) + getTimeoutMs();
            }
            if (hasMaxSize()) {
                hash = (((hash * 37) + 4) * 53) + getMaxSize();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static ConParamsUpdate parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ConParamsUpdate) PARSER.parseFrom(data);
        }

        public static ConParamsUpdate parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ConParamsUpdate) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ConParamsUpdate parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ConParamsUpdate) PARSER.parseFrom(data);
        }

        public static ConParamsUpdate parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ConParamsUpdate) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ConParamsUpdate parseFrom(InputStream input) throws IOException {
            return (ConParamsUpdate) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ConParamsUpdate parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ConParamsUpdate) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ConParamsUpdate parseDelimitedFrom(InputStream input) throws IOException {
            return (ConParamsUpdate) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ConParamsUpdate parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ConParamsUpdate) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ConParamsUpdate parseFrom(CodedInputStream input) throws IOException {
            return (ConParamsUpdate) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ConParamsUpdate parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ConParamsUpdate) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ConParamsUpdate prototype) {
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

        public static ConParamsUpdate getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ConParamsUpdate> parser() {
            return PARSER;
        }

        public Parser<ConParamsUpdate> getParserForType() {
            return PARSER;
        }

        public ConParamsUpdate getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface ConParamsUpdateOrBuilder extends MessageOrBuilder {
        int getIntervalMs();

        int getMaxSize();

        int getMtu();

        int getTimeoutMs();

        boolean hasIntervalMs();

        boolean hasMaxSize();

        boolean hasMtu();

        boolean hasTimeoutMs();
    }

    private ConParams() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0010con_params.proto\"^\n\u000fConParamsUpdate\u0012\u000b\n\u0003mtu\u0018\u0001 \u0001(\u0007\u0012\u0013\n\u000binterval_ms\u0018\u0002 \u0001(\u0007\u0012\u0012\n\ntimeout_ms\u0018\u0003 \u0001(\u0007\u0012\u0015\n\bmax_size\u0018\u0004 \u0001(\u0007:\u0003512"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                ConParams.descriptor = root;
                return null;
            }
        });
    }
}
