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
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class FilesUpdate {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUDataRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(8));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUDataRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_FUDataRequest_descriptor, new String[]{"Fd", "FileOffset", "Crc32AtOffset", "Buf"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUDataResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUDataResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_FUDataResponse_descriptor, new String[]{"Fd", "Status", "FileOffset", "Crc32AtOffset"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUDescResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUDescResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_FUDescResponse_descriptor, new String[]{"Mtu", "Gps", "Font", "Mgaonline"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUExitRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(9));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUExitRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_FUExitRequest_descriptor, new String[]{"Fd"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUExitResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUExitResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_FUExitResponse_descriptor, new String[]{"Fd", "Status", "Desc"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUFileDesc_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUFileDesc_fieldAccessorTable = new FieldAccessorTable(internal_static_FUFileDesc_descriptor, new String[]{"MaxSize", "Valid", "Info"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUFileInfo_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUFileInfo_fieldAccessorTable = new FieldAccessorTable(internal_static_FUFileInfo_descriptor, new String[]{"Fd", "FileName", "FileSize", "FileCrc32", "FileOffset", "Crc32AtOffset"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUInitRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(7));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUInitRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_FUInitRequest_descriptor, new String[]{"InitInfo"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FUInitResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FUInitResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_FUInitResponse_descriptor, new String[]{"Fd", "Status"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FilesUpdateRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(10));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FilesUpdateRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_FilesUpdateRequest_descriptor, new String[]{"Init", "Data", "Exit", "Desc", "Params"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_FilesUpdateResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_FilesUpdateResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_FilesUpdateResponse_descriptor, new String[]{"Init", "Data", "Exit", "Desc", "Params"});

    public static final class FUDataRequest extends GeneratedMessageV3 implements FUDataRequestOrBuilder {
        public static final int BUF_FIELD_NUMBER = 4;
        public static final int CRC32_AT_OFFSET_FIELD_NUMBER = 3;
        private static final FUDataRequest DEFAULT_INSTANCE = new FUDataRequest();
        public static final int FD_FIELD_NUMBER = 1;
        public static final int FILE_OFFSET_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<FUDataRequest> PARSER = new AbstractParser<FUDataRequest>() {
            public FUDataRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUDataRequest(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public ByteString buf_;
        /* access modifiers changed from: private */
        public int crc32AtOffset_;
        /* access modifiers changed from: private */
        public int fd_;
        /* access modifiers changed from: private */
        public int fileOffset_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUDataRequestOrBuilder {
            private int bitField0_;
            private ByteString buf_;
            private int crc32AtOffset_;
            private int fd_;
            private int fileOffset_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUDataRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUDataRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FUDataRequest.class, Builder.class);
            }

            private Builder() {
                this.fd_ = 0;
                this.buf_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fd_ = 0;
                this.buf_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUDataRequest.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.fd_ = 0;
                this.bitField0_ &= -2;
                this.fileOffset_ = 0;
                this.bitField0_ &= -3;
                this.crc32AtOffset_ = 0;
                this.bitField0_ &= -5;
                this.buf_ = ByteString.EMPTY;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUDataRequest_descriptor;
            }

            public FUDataRequest getDefaultInstanceForType() {
                return FUDataRequest.getDefaultInstance();
            }

            public FUDataRequest build() {
                FUDataRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUDataRequest buildPartial() {
                FUDataRequest result = new FUDataRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.fd_ = this.fd_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.fileOffset_ = this.fileOffset_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.crc32AtOffset_ = this.crc32AtOffset_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.buf_ = this.buf_;
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
                if (other instanceof FUDataRequest) {
                    return mergeFrom((FUDataRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUDataRequest other) {
                if (other != FUDataRequest.getDefaultInstance()) {
                    if (other.hasFd()) {
                        setFd(other.getFd());
                    }
                    if (other.hasFileOffset()) {
                        setFileOffset(other.getFileOffset());
                    }
                    if (other.hasCrc32AtOffset()) {
                        setCrc32AtOffset(other.getCrc32AtOffset());
                    }
                    if (other.hasBuf()) {
                        setBuf(other.getBuf());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasFd() && hasFileOffset() && hasCrc32AtOffset() && hasBuf()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUDataRequest parsedMessage = null;
                try {
                    FUDataRequest parsedMessage2 = (FUDataRequest) FUDataRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUDataRequest parsedMessage3 = (FUDataRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasFd() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUType getFd() {
                FUType result = FUType.valueOf(this.fd_);
                return result == null ? FUType.GPS : result;
            }

            public Builder setFd(FUType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.fd_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFd() {
                this.bitField0_ &= -2;
                this.fd_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFileOffset() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getFileOffset() {
                return this.fileOffset_;
            }

            public Builder setFileOffset(int value) {
                this.bitField0_ |= 2;
                this.fileOffset_ = value;
                onChanged();
                return this;
            }

            public Builder clearFileOffset() {
                this.bitField0_ &= -3;
                this.fileOffset_ = 0;
                onChanged();
                return this;
            }

            public boolean hasCrc32AtOffset() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getCrc32AtOffset() {
                return this.crc32AtOffset_;
            }

            public Builder setCrc32AtOffset(int value) {
                this.bitField0_ |= 4;
                this.crc32AtOffset_ = value;
                onChanged();
                return this;
            }

            public Builder clearCrc32AtOffset() {
                this.bitField0_ &= -5;
                this.crc32AtOffset_ = 0;
                onChanged();
                return this;
            }

            public boolean hasBuf() {
                return (this.bitField0_ & 8) == 8;
            }

            public ByteString getBuf() {
                return this.buf_;
            }

            public Builder setBuf(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.buf_ = value;
                onChanged();
                return this;
            }

            public Builder clearBuf() {
                this.bitField0_ &= -9;
                this.buf_ = FUDataRequest.getDefaultInstance().getBuf();
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

        private FUDataRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUDataRequest() {
            this.memoizedIsInitialized = -1;
            this.fd_ = 0;
            this.fileOffset_ = 0;
            this.crc32AtOffset_ = 0;
            this.buf_ = ByteString.EMPTY;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUDataRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (FUType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.fd_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 21:
                            this.bitField0_ |= 2;
                            this.fileOffset_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.crc32AtOffset_ = input.readFixed32();
                            break;
                        case 34:
                            this.bitField0_ |= 8;
                            this.buf_ = input.readBytes();
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
            return FilesUpdate.internal_static_FUDataRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUDataRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FUDataRequest.class, Builder.class);
        }

        public boolean hasFd() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUType getFd() {
            FUType result = FUType.valueOf(this.fd_);
            return result == null ? FUType.GPS : result;
        }

        public boolean hasFileOffset() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getFileOffset() {
            return this.fileOffset_;
        }

        public boolean hasCrc32AtOffset() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getCrc32AtOffset() {
            return this.crc32AtOffset_;
        }

        public boolean hasBuf() {
            return (this.bitField0_ & 8) == 8;
        }

        public ByteString getBuf() {
            return this.buf_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasFd()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFileOffset()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCrc32AtOffset()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasBuf()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.fileOffset_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.crc32AtOffset_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBytes(4, this.buf_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.fileOffset_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.crc32AtOffset_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBytesSize(4, this.buf_);
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
            if (!(obj instanceof FUDataRequest)) {
                return super.equals(obj);
            }
            FUDataRequest other = (FUDataRequest) obj;
            boolean result5 = 1 != 0 && hasFd() == other.hasFd();
            if (hasFd()) {
                result5 = result5 && this.fd_ == other.fd_;
            }
            if (!result5 || hasFileOffset() != other.hasFileOffset()) {
                result = false;
            } else {
                result = true;
            }
            if (hasFileOffset()) {
                if (!result || getFileOffset() != other.getFileOffset()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasCrc32AtOffset() != other.hasCrc32AtOffset()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasCrc32AtOffset()) {
                if (!result2 || getCrc32AtOffset() != other.getCrc32AtOffset()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasBuf() != other.hasBuf()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasBuf()) {
                if (!result3 || !getBuf().equals(other.getBuf())) {
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
            if (hasFd()) {
                hash = (((hash * 37) + 1) * 53) + this.fd_;
            }
            if (hasFileOffset()) {
                hash = (((hash * 37) + 2) * 53) + getFileOffset();
            }
            if (hasCrc32AtOffset()) {
                hash = (((hash * 37) + 3) * 53) + getCrc32AtOffset();
            }
            if (hasBuf()) {
                hash = (((hash * 37) + 4) * 53) + getBuf().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUDataRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUDataRequest) PARSER.parseFrom(data);
        }

        public static FUDataRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUDataRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUDataRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUDataRequest) PARSER.parseFrom(data);
        }

        public static FUDataRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUDataRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUDataRequest parseFrom(InputStream input) throws IOException {
            return (FUDataRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUDataRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDataRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUDataRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (FUDataRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUDataRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDataRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUDataRequest parseFrom(CodedInputStream input) throws IOException {
            return (FUDataRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUDataRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDataRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUDataRequest prototype) {
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

        public static FUDataRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUDataRequest> parser() {
            return PARSER;
        }

        public Parser<FUDataRequest> getParserForType() {
            return PARSER;
        }

        public FUDataRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUDataResponse extends GeneratedMessageV3 implements FUDataResponseOrBuilder {
        public static final int CRC32_AT_OFFSET_FIELD_NUMBER = 4;
        private static final FUDataResponse DEFAULT_INSTANCE = new FUDataResponse();
        public static final int FD_FIELD_NUMBER = 1;
        public static final int FILE_OFFSET_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<FUDataResponse> PARSER = new AbstractParser<FUDataResponse>() {
            public FUDataResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUDataResponse(input, extensionRegistry);
            }
        };
        public static final int STATUS_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int crc32AtOffset_;
        /* access modifiers changed from: private */
        public int fd_;
        /* access modifiers changed from: private */
        public int fileOffset_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int status_;

        public enum Status implements ProtocolMessageEnum {
            OK(0),
            ERROR_PARAMS(1),
            ERROR_OFFSET_MISMATCH(2),
            ERROR_CRC32_MISMATCH(3),
            ERROR_INNER(4);
            
            public static final int ERROR_CRC32_MISMATCH_VALUE = 3;
            public static final int ERROR_INNER_VALUE = 4;
            public static final int ERROR_OFFSET_MISMATCH_VALUE = 2;
            public static final int ERROR_PARAMS_VALUE = 1;
            public static final int OK_VALUE = 0;
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
                        return OK;
                    case 1:
                        return ERROR_PARAMS;
                    case 2:
                        return ERROR_OFFSET_MISMATCH;
                    case 3:
                        return ERROR_CRC32_MISMATCH;
                    case 4:
                        return ERROR_INNER;
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
                return (EnumDescriptor) FUDataResponse.getDescriptor().getEnumTypes().get(0);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUDataResponseOrBuilder {
            private int bitField0_;
            private int crc32AtOffset_;
            private int fd_;
            private int fileOffset_;
            private int status_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUDataResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUDataResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUDataResponse.class, Builder.class);
            }

            private Builder() {
                this.fd_ = 0;
                this.status_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fd_ = 0;
                this.status_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUDataResponse.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.fd_ = 0;
                this.bitField0_ &= -2;
                this.status_ = 0;
                this.bitField0_ &= -3;
                this.fileOffset_ = 0;
                this.bitField0_ &= -5;
                this.crc32AtOffset_ = 0;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUDataResponse_descriptor;
            }

            public FUDataResponse getDefaultInstanceForType() {
                return FUDataResponse.getDefaultInstance();
            }

            public FUDataResponse build() {
                FUDataResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUDataResponse buildPartial() {
                FUDataResponse result = new FUDataResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.fd_ = this.fd_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.status_ = this.status_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.fileOffset_ = this.fileOffset_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.crc32AtOffset_ = this.crc32AtOffset_;
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
                if (other instanceof FUDataResponse) {
                    return mergeFrom((FUDataResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUDataResponse other) {
                if (other != FUDataResponse.getDefaultInstance()) {
                    if (other.hasFd()) {
                        setFd(other.getFd());
                    }
                    if (other.hasStatus()) {
                        setStatus(other.getStatus());
                    }
                    if (other.hasFileOffset()) {
                        setFileOffset(other.getFileOffset());
                    }
                    if (other.hasCrc32AtOffset()) {
                        setCrc32AtOffset(other.getCrc32AtOffset());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasFd() && hasStatus() && hasFileOffset() && hasCrc32AtOffset()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUDataResponse parsedMessage = null;
                try {
                    FUDataResponse parsedMessage2 = (FUDataResponse) FUDataResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUDataResponse parsedMessage3 = (FUDataResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasFd() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUType getFd() {
                FUType result = FUType.valueOf(this.fd_);
                return result == null ? FUType.GPS : result;
            }

            public Builder setFd(FUType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.fd_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFd() {
                this.bitField0_ &= -2;
                this.fd_ = 0;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            public Status getStatus() {
                Status result = Status.valueOf(this.status_);
                return result == null ? Status.OK : result;
            }

            public Builder setStatus(Status value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.status_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -3;
                this.status_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFileOffset() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getFileOffset() {
                return this.fileOffset_;
            }

            public Builder setFileOffset(int value) {
                this.bitField0_ |= 4;
                this.fileOffset_ = value;
                onChanged();
                return this;
            }

            public Builder clearFileOffset() {
                this.bitField0_ &= -5;
                this.fileOffset_ = 0;
                onChanged();
                return this;
            }

            public boolean hasCrc32AtOffset() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getCrc32AtOffset() {
                return this.crc32AtOffset_;
            }

            public Builder setCrc32AtOffset(int value) {
                this.bitField0_ |= 8;
                this.crc32AtOffset_ = value;
                onChanged();
                return this;
            }

            public Builder clearCrc32AtOffset() {
                this.bitField0_ &= -9;
                this.crc32AtOffset_ = 0;
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

        private FUDataResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUDataResponse() {
            this.memoizedIsInitialized = -1;
            this.fd_ = 0;
            this.status_ = 0;
            this.fileOffset_ = 0;
            this.crc32AtOffset_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUDataResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (FUType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.fd_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            int rawValue2 = input.readEnum();
                            if (Status.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 2;
                                this.status_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue2);
                                break;
                            }
                        case 29:
                            this.bitField0_ |= 4;
                            this.fileOffset_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.crc32AtOffset_ = input.readFixed32();
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
            return FilesUpdate.internal_static_FUDataResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUDataResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUDataResponse.class, Builder.class);
        }

        public boolean hasFd() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUType getFd() {
            FUType result = FUType.valueOf(this.fd_);
            return result == null ? FUType.GPS : result;
        }

        public boolean hasStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        public Status getStatus() {
            Status result = Status.valueOf(this.status_);
            return result == null ? Status.OK : result;
        }

        public boolean hasFileOffset() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getFileOffset() {
            return this.fileOffset_;
        }

        public boolean hasCrc32AtOffset() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getCrc32AtOffset() {
            return this.crc32AtOffset_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasFd()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStatus()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFileOffset()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCrc32AtOffset()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.fileOffset_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.crc32AtOffset_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.fileOffset_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.crc32AtOffset_);
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
            if (!(obj instanceof FUDataResponse)) {
                return super.equals(obj);
            }
            FUDataResponse other = (FUDataResponse) obj;
            boolean result5 = 1 != 0 && hasFd() == other.hasFd();
            if (hasFd()) {
                result5 = result5 && this.fd_ == other.fd_;
            }
            if (!result5 || hasStatus() != other.hasStatus()) {
                result = false;
            } else {
                result = true;
            }
            if (hasStatus()) {
                if (!result || this.status_ != other.status_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasFileOffset() != other.hasFileOffset()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasFileOffset()) {
                if (!result2 || getFileOffset() != other.getFileOffset()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasCrc32AtOffset() != other.hasCrc32AtOffset()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasCrc32AtOffset()) {
                if (!result3 || getCrc32AtOffset() != other.getCrc32AtOffset()) {
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
            if (hasFd()) {
                hash = (((hash * 37) + 1) * 53) + this.fd_;
            }
            if (hasStatus()) {
                hash = (((hash * 37) + 2) * 53) + this.status_;
            }
            if (hasFileOffset()) {
                hash = (((hash * 37) + 3) * 53) + getFileOffset();
            }
            if (hasCrc32AtOffset()) {
                hash = (((hash * 37) + 4) * 53) + getCrc32AtOffset();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUDataResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUDataResponse) PARSER.parseFrom(data);
        }

        public static FUDataResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUDataResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUDataResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUDataResponse) PARSER.parseFrom(data);
        }

        public static FUDataResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUDataResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUDataResponse parseFrom(InputStream input) throws IOException {
            return (FUDataResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUDataResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDataResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUDataResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (FUDataResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUDataResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDataResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUDataResponse parseFrom(CodedInputStream input) throws IOException {
            return (FUDataResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUDataResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDataResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUDataResponse prototype) {
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

        public static FUDataResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUDataResponse> parser() {
            return PARSER;
        }

        public Parser<FUDataResponse> getParserForType() {
            return PARSER;
        }

        public FUDataResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUDescResponse extends GeneratedMessageV3 implements FUDescResponseOrBuilder {
        private static final FUDescResponse DEFAULT_INSTANCE = new FUDescResponse();
        public static final int FONT_FIELD_NUMBER = 3;
        public static final int GPS_FIELD_NUMBER = 2;
        public static final int MGAONLINE_FIELD_NUMBER = 4;
        public static final int MTU_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FUDescResponse> PARSER = new AbstractParser<FUDescResponse>() {
            public FUDescResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUDescResponse(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public FUFileDesc font_;
        /* access modifiers changed from: private */
        public FUFileDesc gps_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public FUFileDesc mgaonline_;
        /* access modifiers changed from: private */
        public int mtu_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUDescResponseOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> fontBuilder_;
            private FUFileDesc font_;
            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> gpsBuilder_;
            private FUFileDesc gps_;
            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> mgaonlineBuilder_;
            private FUFileDesc mgaonline_;
            private int mtu_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUDescResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUDescResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUDescResponse.class, Builder.class);
            }

            private Builder() {
                this.gps_ = null;
                this.font_ = null;
                this.mgaonline_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.gps_ = null;
                this.font_ = null;
                this.mgaonline_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUDescResponse.alwaysUseFieldBuilders) {
                    getGpsFieldBuilder();
                    getFontFieldBuilder();
                    getMgaonlineFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.mtu_ = 0;
                this.bitField0_ &= -2;
                if (this.gpsBuilder_ == null) {
                    this.gps_ = null;
                } else {
                    this.gpsBuilder_.clear();
                }
                this.bitField0_ &= -3;
                if (this.fontBuilder_ == null) {
                    this.font_ = null;
                } else {
                    this.fontBuilder_.clear();
                }
                this.bitField0_ &= -5;
                if (this.mgaonlineBuilder_ == null) {
                    this.mgaonline_ = null;
                } else {
                    this.mgaonlineBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUDescResponse_descriptor;
            }

            public FUDescResponse getDefaultInstanceForType() {
                return FUDescResponse.getDefaultInstance();
            }

            public FUDescResponse build() {
                FUDescResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUDescResponse buildPartial() {
                FUDescResponse result = new FUDescResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.mtu_ = this.mtu_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.gpsBuilder_ == null) {
                    result.gps_ = this.gps_;
                } else {
                    result.gps_ = (FUFileDesc) this.gpsBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.fontBuilder_ == null) {
                    result.font_ = this.font_;
                } else {
                    result.font_ = (FUFileDesc) this.fontBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.mgaonlineBuilder_ == null) {
                    result.mgaonline_ = this.mgaonline_;
                } else {
                    result.mgaonline_ = (FUFileDesc) this.mgaonlineBuilder_.build();
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
                if (other instanceof FUDescResponse) {
                    return mergeFrom((FUDescResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUDescResponse other) {
                if (other != FUDescResponse.getDefaultInstance()) {
                    if (other.hasMtu()) {
                        setMtu(other.getMtu());
                    }
                    if (other.hasGps()) {
                        mergeGps(other.getGps());
                    }
                    if (other.hasFont()) {
                        mergeFont(other.getFont());
                    }
                    if (other.hasMgaonline()) {
                        mergeMgaonline(other.getMgaonline());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasMtu()) {
                    return false;
                }
                if (hasGps() && !getGps().isInitialized()) {
                    return false;
                }
                if (hasFont() && !getFont().isInitialized()) {
                    return false;
                }
                if (!hasMgaonline() || getMgaonline().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUDescResponse parsedMessage = null;
                try {
                    FUDescResponse parsedMessage2 = (FUDescResponse) FUDescResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUDescResponse parsedMessage3 = (FUDescResponse) e.getUnfinishedMessage();
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

            public boolean hasGps() {
                return (this.bitField0_ & 2) == 2;
            }

            public FUFileDesc getGps() {
                if (this.gpsBuilder_ == null) {
                    return this.gps_ == null ? FUFileDesc.getDefaultInstance() : this.gps_;
                }
                return (FUFileDesc) this.gpsBuilder_.getMessage();
            }

            public Builder setGps(FUFileDesc value) {
                if (this.gpsBuilder_ != null) {
                    this.gpsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.gps_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setGps(Builder builderForValue) {
                if (this.gpsBuilder_ == null) {
                    this.gps_ = builderForValue.build();
                    onChanged();
                } else {
                    this.gpsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeGps(FUFileDesc value) {
                if (this.gpsBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.gps_ == null || this.gps_ == FUFileDesc.getDefaultInstance()) {
                        this.gps_ = value;
                    } else {
                        this.gps_ = FUFileDesc.newBuilder(this.gps_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.gpsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearGps() {
                if (this.gpsBuilder_ == null) {
                    this.gps_ = null;
                    onChanged();
                } else {
                    this.gpsBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getGpsBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getGpsFieldBuilder().getBuilder();
            }

            public FUFileDescOrBuilder getGpsOrBuilder() {
                if (this.gpsBuilder_ != null) {
                    return (FUFileDescOrBuilder) this.gpsBuilder_.getMessageOrBuilder();
                }
                return this.gps_ == null ? FUFileDesc.getDefaultInstance() : this.gps_;
            }

            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> getGpsFieldBuilder() {
                if (this.gpsBuilder_ == null) {
                    this.gpsBuilder_ = new SingleFieldBuilderV3<>(getGps(), getParentForChildren(), isClean());
                    this.gps_ = null;
                }
                return this.gpsBuilder_;
            }

            public boolean hasFont() {
                return (this.bitField0_ & 4) == 4;
            }

            public FUFileDesc getFont() {
                if (this.fontBuilder_ == null) {
                    return this.font_ == null ? FUFileDesc.getDefaultInstance() : this.font_;
                }
                return (FUFileDesc) this.fontBuilder_.getMessage();
            }

            public Builder setFont(FUFileDesc value) {
                if (this.fontBuilder_ != null) {
                    this.fontBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.font_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setFont(Builder builderForValue) {
                if (this.fontBuilder_ == null) {
                    this.font_ = builderForValue.build();
                    onChanged();
                } else {
                    this.fontBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeFont(FUFileDesc value) {
                if (this.fontBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.font_ == null || this.font_ == FUFileDesc.getDefaultInstance()) {
                        this.font_ = value;
                    } else {
                        this.font_ = FUFileDesc.newBuilder(this.font_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.fontBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearFont() {
                if (this.fontBuilder_ == null) {
                    this.font_ = null;
                    onChanged();
                } else {
                    this.fontBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getFontBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getFontFieldBuilder().getBuilder();
            }

            public FUFileDescOrBuilder getFontOrBuilder() {
                if (this.fontBuilder_ != null) {
                    return (FUFileDescOrBuilder) this.fontBuilder_.getMessageOrBuilder();
                }
                return this.font_ == null ? FUFileDesc.getDefaultInstance() : this.font_;
            }

            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> getFontFieldBuilder() {
                if (this.fontBuilder_ == null) {
                    this.fontBuilder_ = new SingleFieldBuilderV3<>(getFont(), getParentForChildren(), isClean());
                    this.font_ = null;
                }
                return this.fontBuilder_;
            }

            public boolean hasMgaonline() {
                return (this.bitField0_ & 8) == 8;
            }

            public FUFileDesc getMgaonline() {
                if (this.mgaonlineBuilder_ == null) {
                    return this.mgaonline_ == null ? FUFileDesc.getDefaultInstance() : this.mgaonline_;
                }
                return (FUFileDesc) this.mgaonlineBuilder_.getMessage();
            }

            public Builder setMgaonline(FUFileDesc value) {
                if (this.mgaonlineBuilder_ != null) {
                    this.mgaonlineBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.mgaonline_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setMgaonline(Builder builderForValue) {
                if (this.mgaonlineBuilder_ == null) {
                    this.mgaonline_ = builderForValue.build();
                    onChanged();
                } else {
                    this.mgaonlineBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeMgaonline(FUFileDesc value) {
                if (this.mgaonlineBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.mgaonline_ == null || this.mgaonline_ == FUFileDesc.getDefaultInstance()) {
                        this.mgaonline_ = value;
                    } else {
                        this.mgaonline_ = FUFileDesc.newBuilder(this.mgaonline_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.mgaonlineBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearMgaonline() {
                if (this.mgaonlineBuilder_ == null) {
                    this.mgaonline_ = null;
                    onChanged();
                } else {
                    this.mgaonlineBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getMgaonlineBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getMgaonlineFieldBuilder().getBuilder();
            }

            public FUFileDescOrBuilder getMgaonlineOrBuilder() {
                if (this.mgaonlineBuilder_ != null) {
                    return (FUFileDescOrBuilder) this.mgaonlineBuilder_.getMessageOrBuilder();
                }
                return this.mgaonline_ == null ? FUFileDesc.getDefaultInstance() : this.mgaonline_;
            }

            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> getMgaonlineFieldBuilder() {
                if (this.mgaonlineBuilder_ == null) {
                    this.mgaonlineBuilder_ = new SingleFieldBuilderV3<>(getMgaonline(), getParentForChildren(), isClean());
                    this.mgaonline_ = null;
                }
                return this.mgaonlineBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private FUDescResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUDescResponse() {
            this.memoizedIsInitialized = -1;
            this.mtu_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUDescResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 18:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.gps_.toBuilder();
                            }
                            this.gps_ = (FUFileDesc) input.readMessage(FUFileDesc.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.gps_);
                                this.gps_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder2 = this.font_.toBuilder();
                            }
                            this.font_ = (FUFileDesc) input.readMessage(FUFileDesc.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.font_);
                                this.font_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 34:
                            Builder subBuilder3 = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder3 = this.mgaonline_.toBuilder();
                            }
                            this.mgaonline_ = (FUFileDesc) input.readMessage(FUFileDesc.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.mgaonline_);
                                this.mgaonline_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 8;
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
            return FilesUpdate.internal_static_FUDescResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUDescResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUDescResponse.class, Builder.class);
        }

        public boolean hasMtu() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMtu() {
            return this.mtu_;
        }

        public boolean hasGps() {
            return (this.bitField0_ & 2) == 2;
        }

        public FUFileDesc getGps() {
            return this.gps_ == null ? FUFileDesc.getDefaultInstance() : this.gps_;
        }

        public FUFileDescOrBuilder getGpsOrBuilder() {
            return this.gps_ == null ? FUFileDesc.getDefaultInstance() : this.gps_;
        }

        public boolean hasFont() {
            return (this.bitField0_ & 4) == 4;
        }

        public FUFileDesc getFont() {
            return this.font_ == null ? FUFileDesc.getDefaultInstance() : this.font_;
        }

        public FUFileDescOrBuilder getFontOrBuilder() {
            return this.font_ == null ? FUFileDesc.getDefaultInstance() : this.font_;
        }

        public boolean hasMgaonline() {
            return (this.bitField0_ & 8) == 8;
        }

        public FUFileDesc getMgaonline() {
            return this.mgaonline_ == null ? FUFileDesc.getDefaultInstance() : this.mgaonline_;
        }

        public FUFileDescOrBuilder getMgaonlineOrBuilder() {
            return this.mgaonline_ == null ? FUFileDesc.getDefaultInstance() : this.mgaonline_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasMtu()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasGps() && !getGps().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasFont() && !getFont().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMgaonline() || getMgaonline().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.mtu_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getGps());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getFont());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, getMgaonline());
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
                size2 += CodedOutputStream.computeMessageSize(2, getGps());
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getFont());
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeMessageSize(4, getMgaonline());
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
            if (!(obj instanceof FUDescResponse)) {
                return super.equals(obj);
            }
            FUDescResponse other = (FUDescResponse) obj;
            boolean result5 = 1 != 0 && hasMtu() == other.hasMtu();
            if (hasMtu()) {
                result5 = result5 && getMtu() == other.getMtu();
            }
            if (!result5 || hasGps() != other.hasGps()) {
                result = false;
            } else {
                result = true;
            }
            if (hasGps()) {
                if (!result || !getGps().equals(other.getGps())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasFont() != other.hasFont()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasFont()) {
                if (!result2 || !getFont().equals(other.getFont())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasMgaonline() != other.hasMgaonline()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasMgaonline()) {
                if (!result3 || !getMgaonline().equals(other.getMgaonline())) {
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
            if (hasGps()) {
                hash = (((hash * 37) + 2) * 53) + getGps().hashCode();
            }
            if (hasFont()) {
                hash = (((hash * 37) + 3) * 53) + getFont().hashCode();
            }
            if (hasMgaonline()) {
                hash = (((hash * 37) + 4) * 53) + getMgaonline().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUDescResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUDescResponse) PARSER.parseFrom(data);
        }

        public static FUDescResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUDescResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUDescResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUDescResponse) PARSER.parseFrom(data);
        }

        public static FUDescResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUDescResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUDescResponse parseFrom(InputStream input) throws IOException {
            return (FUDescResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUDescResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDescResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUDescResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (FUDescResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUDescResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDescResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUDescResponse parseFrom(CodedInputStream input) throws IOException {
            return (FUDescResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUDescResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUDescResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUDescResponse prototype) {
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

        public static FUDescResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUDescResponse> parser() {
            return PARSER;
        }

        public Parser<FUDescResponse> getParserForType() {
            return PARSER;
        }

        public FUDescResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUExitRequest extends GeneratedMessageV3 implements FUExitRequestOrBuilder {
        private static final FUExitRequest DEFAULT_INSTANCE = new FUExitRequest();
        public static final int FD_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FUExitRequest> PARSER = new AbstractParser<FUExitRequest>() {
            public FUExitRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUExitRequest(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int fd_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUExitRequestOrBuilder {
            private int bitField0_;
            private int fd_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUExitRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUExitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FUExitRequest.class, Builder.class);
            }

            private Builder() {
                this.fd_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fd_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUExitRequest.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.fd_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUExitRequest_descriptor;
            }

            public FUExitRequest getDefaultInstanceForType() {
                return FUExitRequest.getDefaultInstance();
            }

            public FUExitRequest build() {
                FUExitRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUExitRequest buildPartial() {
                FUExitRequest result = new FUExitRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.fd_ = this.fd_;
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
                if (other instanceof FUExitRequest) {
                    return mergeFrom((FUExitRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUExitRequest other) {
                if (other != FUExitRequest.getDefaultInstance()) {
                    if (other.hasFd()) {
                        setFd(other.getFd());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasFd()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUExitRequest parsedMessage = null;
                try {
                    FUExitRequest parsedMessage2 = (FUExitRequest) FUExitRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUExitRequest parsedMessage3 = (FUExitRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasFd() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUType getFd() {
                FUType result = FUType.valueOf(this.fd_);
                return result == null ? FUType.GPS : result;
            }

            public Builder setFd(FUType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.fd_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFd() {
                this.bitField0_ &= -2;
                this.fd_ = 0;
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

        private FUExitRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUExitRequest() {
            this.memoizedIsInitialized = -1;
            this.fd_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUExitRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (FUType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.fd_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
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
            return FilesUpdate.internal_static_FUExitRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUExitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FUExitRequest.class, Builder.class);
        }

        public boolean hasFd() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUType getFd() {
            FUType result = FUType.valueOf(this.fd_);
            return result == null ? FUType.GPS : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasFd()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.fd_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.fd_);
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
            if (!(obj instanceof FUExitRequest)) {
                return super.equals(obj);
            }
            FUExitRequest other = (FUExitRequest) obj;
            boolean result2 = 1 != 0 && hasFd() == other.hasFd();
            if (hasFd()) {
                result2 = result2 && this.fd_ == other.fd_;
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
            if (hasFd()) {
                hash = (((hash * 37) + 1) * 53) + this.fd_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUExitRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUExitRequest) PARSER.parseFrom(data);
        }

        public static FUExitRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUExitRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUExitRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUExitRequest) PARSER.parseFrom(data);
        }

        public static FUExitRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUExitRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUExitRequest parseFrom(InputStream input) throws IOException {
            return (FUExitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUExitRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUExitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUExitRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (FUExitRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUExitRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUExitRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUExitRequest parseFrom(CodedInputStream input) throws IOException {
            return (FUExitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUExitRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUExitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUExitRequest prototype) {
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

        public static FUExitRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUExitRequest> parser() {
            return PARSER;
        }

        public Parser<FUExitRequest> getParserForType() {
            return PARSER;
        }

        public FUExitRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUExitResponse extends GeneratedMessageV3 implements FUExitResponseOrBuilder {
        private static final FUExitResponse DEFAULT_INSTANCE = new FUExitResponse();
        public static final int DESC_FIELD_NUMBER = 3;
        public static final int FD_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FUExitResponse> PARSER = new AbstractParser<FUExitResponse>() {
            public FUExitResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUExitResponse(input, extensionRegistry);
            }
        };
        public static final int STATUS_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public FUFileDesc desc_;
        /* access modifiers changed from: private */
        public int fd_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int status_;

        public enum Status implements ProtocolMessageEnum {
            OK(0),
            ERROR_PARAMS(1),
            ERROR_CRC_MISMATCH(2),
            ERROR_SIZE_MISMATCH(3),
            ERROR_INNER(4);
            
            public static final int ERROR_CRC_MISMATCH_VALUE = 2;
            public static final int ERROR_INNER_VALUE = 4;
            public static final int ERROR_PARAMS_VALUE = 1;
            public static final int ERROR_SIZE_MISMATCH_VALUE = 3;
            public static final int OK_VALUE = 0;
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
                        return OK;
                    case 1:
                        return ERROR_PARAMS;
                    case 2:
                        return ERROR_CRC_MISMATCH;
                    case 3:
                        return ERROR_SIZE_MISMATCH;
                    case 4:
                        return ERROR_INNER;
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
                return (EnumDescriptor) FUExitResponse.getDescriptor().getEnumTypes().get(0);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUExitResponseOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> descBuilder_;
            private FUFileDesc desc_;
            private int fd_;
            private int status_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUExitResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUExitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUExitResponse.class, Builder.class);
            }

            private Builder() {
                this.fd_ = 0;
                this.status_ = 0;
                this.desc_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fd_ = 0;
                this.status_ = 0;
                this.desc_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUExitResponse.alwaysUseFieldBuilders) {
                    getDescFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.fd_ = 0;
                this.bitField0_ &= -2;
                this.status_ = 0;
                this.bitField0_ &= -3;
                if (this.descBuilder_ == null) {
                    this.desc_ = null;
                } else {
                    this.descBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUExitResponse_descriptor;
            }

            public FUExitResponse getDefaultInstanceForType() {
                return FUExitResponse.getDefaultInstance();
            }

            public FUExitResponse build() {
                FUExitResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUExitResponse buildPartial() {
                FUExitResponse result = new FUExitResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.fd_ = this.fd_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.status_ = this.status_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.descBuilder_ == null) {
                    result.desc_ = this.desc_;
                } else {
                    result.desc_ = (FUFileDesc) this.descBuilder_.build();
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
                if (other instanceof FUExitResponse) {
                    return mergeFrom((FUExitResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUExitResponse other) {
                if (other != FUExitResponse.getDefaultInstance()) {
                    if (other.hasFd()) {
                        setFd(other.getFd());
                    }
                    if (other.hasStatus()) {
                        setStatus(other.getStatus());
                    }
                    if (other.hasDesc()) {
                        mergeDesc(other.getDesc());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasFd() && hasStatus() && hasDesc() && getDesc().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUExitResponse parsedMessage = null;
                try {
                    FUExitResponse parsedMessage2 = (FUExitResponse) FUExitResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUExitResponse parsedMessage3 = (FUExitResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasFd() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUType getFd() {
                FUType result = FUType.valueOf(this.fd_);
                return result == null ? FUType.GPS : result;
            }

            public Builder setFd(FUType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.fd_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFd() {
                this.bitField0_ &= -2;
                this.fd_ = 0;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            public Status getStatus() {
                Status result = Status.valueOf(this.status_);
                return result == null ? Status.OK : result;
            }

            public Builder setStatus(Status value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.status_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -3;
                this.status_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDesc() {
                return (this.bitField0_ & 4) == 4;
            }

            public FUFileDesc getDesc() {
                if (this.descBuilder_ == null) {
                    return this.desc_ == null ? FUFileDesc.getDefaultInstance() : this.desc_;
                }
                return (FUFileDesc) this.descBuilder_.getMessage();
            }

            public Builder setDesc(FUFileDesc value) {
                if (this.descBuilder_ != null) {
                    this.descBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.desc_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setDesc(Builder builderForValue) {
                if (this.descBuilder_ == null) {
                    this.desc_ = builderForValue.build();
                    onChanged();
                } else {
                    this.descBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeDesc(FUFileDesc value) {
                if (this.descBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.desc_ == null || this.desc_ == FUFileDesc.getDefaultInstance()) {
                        this.desc_ = value;
                    } else {
                        this.desc_ = FUFileDesc.newBuilder(this.desc_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.descBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearDesc() {
                if (this.descBuilder_ == null) {
                    this.desc_ = null;
                    onChanged();
                } else {
                    this.descBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getDescBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getDescFieldBuilder().getBuilder();
            }

            public FUFileDescOrBuilder getDescOrBuilder() {
                if (this.descBuilder_ != null) {
                    return (FUFileDescOrBuilder) this.descBuilder_.getMessageOrBuilder();
                }
                return this.desc_ == null ? FUFileDesc.getDefaultInstance() : this.desc_;
            }

            private SingleFieldBuilderV3<FUFileDesc, Builder, FUFileDescOrBuilder> getDescFieldBuilder() {
                if (this.descBuilder_ == null) {
                    this.descBuilder_ = new SingleFieldBuilderV3<>(getDesc(), getParentForChildren(), isClean());
                    this.desc_ = null;
                }
                return this.descBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private FUExitResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUExitResponse() {
            this.memoizedIsInitialized = -1;
            this.fd_ = 0;
            this.status_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUExitResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (FUType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.fd_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            int rawValue2 = input.readEnum();
                            if (Status.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 2;
                                this.status_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue2);
                                break;
                            }
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.desc_.toBuilder();
                            }
                            this.desc_ = (FUFileDesc) input.readMessage(FUFileDesc.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.desc_);
                                this.desc_ = subBuilder.buildPartial();
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
            return FilesUpdate.internal_static_FUExitResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUExitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUExitResponse.class, Builder.class);
        }

        public boolean hasFd() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUType getFd() {
            FUType result = FUType.valueOf(this.fd_);
            return result == null ? FUType.GPS : result;
        }

        public boolean hasStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        public Status getStatus() {
            Status result = Status.valueOf(this.status_);
            return result == null ? Status.OK : result;
        }

        public boolean hasDesc() {
            return (this.bitField0_ & 4) == 4;
        }

        public FUFileDesc getDesc() {
            return this.desc_ == null ? FUFileDesc.getDefaultInstance() : this.desc_;
        }

        public FUFileDescOrBuilder getDescOrBuilder() {
            return this.desc_ == null ? FUFileDesc.getDefaultInstance() : this.desc_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasFd()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStatus()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDesc()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getDesc().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getDesc());
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getDesc());
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
            if (!(obj instanceof FUExitResponse)) {
                return super.equals(obj);
            }
            FUExitResponse other = (FUExitResponse) obj;
            boolean result4 = 1 != 0 && hasFd() == other.hasFd();
            if (hasFd()) {
                result4 = result4 && this.fd_ == other.fd_;
            }
            if (!result4 || hasStatus() != other.hasStatus()) {
                result = false;
            } else {
                result = true;
            }
            if (hasStatus()) {
                if (!result || this.status_ != other.status_) {
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
                if (!result2 || !getDesc().equals(other.getDesc())) {
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
            if (hasFd()) {
                hash = (((hash * 37) + 1) * 53) + this.fd_;
            }
            if (hasStatus()) {
                hash = (((hash * 37) + 2) * 53) + this.status_;
            }
            if (hasDesc()) {
                hash = (((hash * 37) + 3) * 53) + getDesc().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUExitResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUExitResponse) PARSER.parseFrom(data);
        }

        public static FUExitResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUExitResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUExitResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUExitResponse) PARSER.parseFrom(data);
        }

        public static FUExitResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUExitResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUExitResponse parseFrom(InputStream input) throws IOException {
            return (FUExitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUExitResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUExitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUExitResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (FUExitResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUExitResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUExitResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUExitResponse parseFrom(CodedInputStream input) throws IOException {
            return (FUExitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUExitResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUExitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUExitResponse prototype) {
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

        public static FUExitResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUExitResponse> parser() {
            return PARSER;
        }

        public Parser<FUExitResponse> getParserForType() {
            return PARSER;
        }

        public FUExitResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUFileDesc extends GeneratedMessageV3 implements FUFileDescOrBuilder {
        private static final FUFileDesc DEFAULT_INSTANCE = new FUFileDesc();
        public static final int INFO_FIELD_NUMBER = 3;
        public static final int MAX_SIZE_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FUFileDesc> PARSER = new AbstractParser<FUFileDesc>() {
            public FUFileDesc parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUFileDesc(input, extensionRegistry);
            }
        };
        public static final int VALID_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public FUFileInfo info_;
        /* access modifiers changed from: private */
        public int maxSize_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean valid_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUFileDescOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<FUFileInfo, Builder, FUFileInfoOrBuilder> infoBuilder_;
            private FUFileInfo info_;
            private int maxSize_;
            private boolean valid_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUFileDesc_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUFileDesc_fieldAccessorTable.ensureFieldAccessorsInitialized(FUFileDesc.class, Builder.class);
            }

            private Builder() {
                this.info_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.info_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUFileDesc.alwaysUseFieldBuilders) {
                    getInfoFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.maxSize_ = 0;
                this.bitField0_ &= -2;
                this.valid_ = false;
                this.bitField0_ &= -3;
                if (this.infoBuilder_ == null) {
                    this.info_ = null;
                } else {
                    this.infoBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUFileDesc_descriptor;
            }

            public FUFileDesc getDefaultInstanceForType() {
                return FUFileDesc.getDefaultInstance();
            }

            public FUFileDesc build() {
                FUFileDesc result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUFileDesc buildPartial() {
                FUFileDesc result = new FUFileDesc((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.maxSize_ = this.maxSize_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.valid_ = this.valid_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.infoBuilder_ == null) {
                    result.info_ = this.info_;
                } else {
                    result.info_ = (FUFileInfo) this.infoBuilder_.build();
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
                if (other instanceof FUFileDesc) {
                    return mergeFrom((FUFileDesc) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUFileDesc other) {
                if (other != FUFileDesc.getDefaultInstance()) {
                    if (other.hasMaxSize()) {
                        setMaxSize(other.getMaxSize());
                    }
                    if (other.hasValid()) {
                        setValid(other.getValid());
                    }
                    if (other.hasInfo()) {
                        mergeInfo(other.getInfo());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasMaxSize() && hasValid() && hasInfo() && getInfo().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUFileDesc parsedMessage = null;
                try {
                    FUFileDesc parsedMessage2 = (FUFileDesc) FUFileDesc.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUFileDesc parsedMessage3 = (FUFileDesc) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasMaxSize() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getMaxSize() {
                return this.maxSize_;
            }

            public Builder setMaxSize(int value) {
                this.bitField0_ |= 1;
                this.maxSize_ = value;
                onChanged();
                return this;
            }

            public Builder clearMaxSize() {
                this.bitField0_ &= -2;
                this.maxSize_ = 0;
                onChanged();
                return this;
            }

            public boolean hasValid() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getValid() {
                return this.valid_;
            }

            public Builder setValid(boolean value) {
                this.bitField0_ |= 2;
                this.valid_ = value;
                onChanged();
                return this;
            }

            public Builder clearValid() {
                this.bitField0_ &= -3;
                this.valid_ = false;
                onChanged();
                return this;
            }

            public boolean hasInfo() {
                return (this.bitField0_ & 4) == 4;
            }

            public FUFileInfo getInfo() {
                if (this.infoBuilder_ == null) {
                    return this.info_ == null ? FUFileInfo.getDefaultInstance() : this.info_;
                }
                return (FUFileInfo) this.infoBuilder_.getMessage();
            }

            public Builder setInfo(FUFileInfo value) {
                if (this.infoBuilder_ != null) {
                    this.infoBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.info_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setInfo(Builder builderForValue) {
                if (this.infoBuilder_ == null) {
                    this.info_ = builderForValue.build();
                    onChanged();
                } else {
                    this.infoBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeInfo(FUFileInfo value) {
                if (this.infoBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.info_ == null || this.info_ == FUFileInfo.getDefaultInstance()) {
                        this.info_ = value;
                    } else {
                        this.info_ = FUFileInfo.newBuilder(this.info_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.infoBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearInfo() {
                if (this.infoBuilder_ == null) {
                    this.info_ = null;
                    onChanged();
                } else {
                    this.infoBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getInfoBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getInfoFieldBuilder().getBuilder();
            }

            public FUFileInfoOrBuilder getInfoOrBuilder() {
                if (this.infoBuilder_ != null) {
                    return (FUFileInfoOrBuilder) this.infoBuilder_.getMessageOrBuilder();
                }
                return this.info_ == null ? FUFileInfo.getDefaultInstance() : this.info_;
            }

            private SingleFieldBuilderV3<FUFileInfo, Builder, FUFileInfoOrBuilder> getInfoFieldBuilder() {
                if (this.infoBuilder_ == null) {
                    this.infoBuilder_ = new SingleFieldBuilderV3<>(getInfo(), getParentForChildren(), isClean());
                    this.info_ = null;
                }
                return this.infoBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private FUFileDesc(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUFileDesc() {
            this.memoizedIsInitialized = -1;
            this.maxSize_ = 0;
            this.valid_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUFileDesc(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.maxSize_ = input.readFixed32();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.valid_ = input.readBool();
                            break;
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.info_.toBuilder();
                            }
                            this.info_ = (FUFileInfo) input.readMessage(FUFileInfo.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.info_);
                                this.info_ = subBuilder.buildPartial();
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
            return FilesUpdate.internal_static_FUFileDesc_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUFileDesc_fieldAccessorTable.ensureFieldAccessorsInitialized(FUFileDesc.class, Builder.class);
        }

        public boolean hasMaxSize() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMaxSize() {
            return this.maxSize_;
        }

        public boolean hasValid() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getValid() {
            return this.valid_;
        }

        public boolean hasInfo() {
            return (this.bitField0_ & 4) == 4;
        }

        public FUFileInfo getInfo() {
            return this.info_ == null ? FUFileInfo.getDefaultInstance() : this.info_;
        }

        public FUFileInfoOrBuilder getInfoOrBuilder() {
            return this.info_ == null ? FUFileInfo.getDefaultInstance() : this.info_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasMaxSize()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasValid()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasInfo()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getInfo().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.maxSize_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.valid_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getInfo());
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.maxSize_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.valid_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getInfo());
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
            if (!(obj instanceof FUFileDesc)) {
                return super.equals(obj);
            }
            FUFileDesc other = (FUFileDesc) obj;
            boolean result4 = 1 != 0 && hasMaxSize() == other.hasMaxSize();
            if (hasMaxSize()) {
                result4 = result4 && getMaxSize() == other.getMaxSize();
            }
            if (!result4 || hasValid() != other.hasValid()) {
                result = false;
            } else {
                result = true;
            }
            if (hasValid()) {
                if (!result || getValid() != other.getValid()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasInfo() != other.hasInfo()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasInfo()) {
                if (!result2 || !getInfo().equals(other.getInfo())) {
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
            if (hasMaxSize()) {
                hash = (((hash * 37) + 1) * 53) + getMaxSize();
            }
            if (hasValid()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getValid());
            }
            if (hasInfo()) {
                hash = (((hash * 37) + 3) * 53) + getInfo().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUFileDesc parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUFileDesc) PARSER.parseFrom(data);
        }

        public static FUFileDesc parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUFileDesc) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUFileDesc parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUFileDesc) PARSER.parseFrom(data);
        }

        public static FUFileDesc parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUFileDesc) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUFileDesc parseFrom(InputStream input) throws IOException {
            return (FUFileDesc) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUFileDesc parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUFileDesc) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUFileDesc parseDelimitedFrom(InputStream input) throws IOException {
            return (FUFileDesc) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUFileDesc parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUFileDesc) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUFileDesc parseFrom(CodedInputStream input) throws IOException {
            return (FUFileDesc) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUFileDesc parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUFileDesc) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUFileDesc prototype) {
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

        public static FUFileDesc getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUFileDesc> parser() {
            return PARSER;
        }

        public Parser<FUFileDesc> getParserForType() {
            return PARSER;
        }

        public FUFileDesc getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUFileInfo extends GeneratedMessageV3 implements FUFileInfoOrBuilder {
        public static final int CRC32_AT_OFFSET_FIELD_NUMBER = 6;
        private static final FUFileInfo DEFAULT_INSTANCE = new FUFileInfo();
        public static final int FD_FIELD_NUMBER = 1;
        public static final int FILE_CRC32_FIELD_NUMBER = 4;
        public static final int FILE_NAME_FIELD_NUMBER = 2;
        public static final int FILE_OFFSET_FIELD_NUMBER = 5;
        public static final int FILE_SIZE_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<FUFileInfo> PARSER = new AbstractParser<FUFileInfo>() {
            public FUFileInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUFileInfo(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int crc32AtOffset_;
        /* access modifiers changed from: private */
        public int fd_;
        /* access modifiers changed from: private */
        public int fileCrc32_;
        /* access modifiers changed from: private */
        public volatile Object fileName_;
        /* access modifiers changed from: private */
        public int fileOffset_;
        /* access modifiers changed from: private */
        public int fileSize_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUFileInfoOrBuilder {
            private int bitField0_;
            private int crc32AtOffset_;
            private int fd_;
            private int fileCrc32_;
            private Object fileName_;
            private int fileOffset_;
            private int fileSize_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUFileInfo_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUFileInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(FUFileInfo.class, Builder.class);
            }

            private Builder() {
                this.fd_ = 0;
                this.fileName_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fd_ = 0;
                this.fileName_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUFileInfo.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.fd_ = 0;
                this.bitField0_ &= -2;
                this.fileName_ = "";
                this.bitField0_ &= -3;
                this.fileSize_ = 0;
                this.bitField0_ &= -5;
                this.fileCrc32_ = 0;
                this.bitField0_ &= -9;
                this.fileOffset_ = 0;
                this.bitField0_ &= -17;
                this.crc32AtOffset_ = 0;
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUFileInfo_descriptor;
            }

            public FUFileInfo getDefaultInstanceForType() {
                return FUFileInfo.getDefaultInstance();
            }

            public FUFileInfo build() {
                FUFileInfo result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUFileInfo buildPartial() {
                FUFileInfo result = new FUFileInfo((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.fd_ = this.fd_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.fileName_ = this.fileName_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.fileSize_ = this.fileSize_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.fileCrc32_ = this.fileCrc32_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.fileOffset_ = this.fileOffset_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.crc32AtOffset_ = this.crc32AtOffset_;
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
                if (other instanceof FUFileInfo) {
                    return mergeFrom((FUFileInfo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUFileInfo other) {
                if (other != FUFileInfo.getDefaultInstance()) {
                    if (other.hasFd()) {
                        setFd(other.getFd());
                    }
                    if (other.hasFileName()) {
                        this.bitField0_ |= 2;
                        this.fileName_ = other.fileName_;
                        onChanged();
                    }
                    if (other.hasFileSize()) {
                        setFileSize(other.getFileSize());
                    }
                    if (other.hasFileCrc32()) {
                        setFileCrc32(other.getFileCrc32());
                    }
                    if (other.hasFileOffset()) {
                        setFileOffset(other.getFileOffset());
                    }
                    if (other.hasCrc32AtOffset()) {
                        setCrc32AtOffset(other.getCrc32AtOffset());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasFd() && hasFileName() && hasFileSize() && hasFileCrc32() && hasFileOffset() && hasCrc32AtOffset()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUFileInfo parsedMessage = null;
                try {
                    FUFileInfo parsedMessage2 = (FUFileInfo) FUFileInfo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUFileInfo parsedMessage3 = (FUFileInfo) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasFd() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUType getFd() {
                FUType result = FUType.valueOf(this.fd_);
                return result == null ? FUType.GPS : result;
            }

            public Builder setFd(FUType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.fd_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFd() {
                this.bitField0_ &= -2;
                this.fd_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFileName() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getFileName() {
                Object ref = this.fileName_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.fileName_ = s;
                return s;
            }

            public ByteString getFileNameBytes() {
                Object ref = this.fileName_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.fileName_ = b;
                return b;
            }

            public Builder setFileName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.fileName_ = value;
                onChanged();
                return this;
            }

            public Builder clearFileName() {
                this.bitField0_ &= -3;
                this.fileName_ = FUFileInfo.getDefaultInstance().getFileName();
                onChanged();
                return this;
            }

            public Builder setFileNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.fileName_ = value;
                onChanged();
                return this;
            }

            public boolean hasFileSize() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getFileSize() {
                return this.fileSize_;
            }

            public Builder setFileSize(int value) {
                this.bitField0_ |= 4;
                this.fileSize_ = value;
                onChanged();
                return this;
            }

            public Builder clearFileSize() {
                this.bitField0_ &= -5;
                this.fileSize_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFileCrc32() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getFileCrc32() {
                return this.fileCrc32_;
            }

            public Builder setFileCrc32(int value) {
                this.bitField0_ |= 8;
                this.fileCrc32_ = value;
                onChanged();
                return this;
            }

            public Builder clearFileCrc32() {
                this.bitField0_ &= -9;
                this.fileCrc32_ = 0;
                onChanged();
                return this;
            }

            public boolean hasFileOffset() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getFileOffset() {
                return this.fileOffset_;
            }

            public Builder setFileOffset(int value) {
                this.bitField0_ |= 16;
                this.fileOffset_ = value;
                onChanged();
                return this;
            }

            public Builder clearFileOffset() {
                this.bitField0_ &= -17;
                this.fileOffset_ = 0;
                onChanged();
                return this;
            }

            public boolean hasCrc32AtOffset() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getCrc32AtOffset() {
                return this.crc32AtOffset_;
            }

            public Builder setCrc32AtOffset(int value) {
                this.bitField0_ |= 32;
                this.crc32AtOffset_ = value;
                onChanged();
                return this;
            }

            public Builder clearCrc32AtOffset() {
                this.bitField0_ &= -33;
                this.crc32AtOffset_ = 0;
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

        private FUFileInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUFileInfo() {
            this.memoizedIsInitialized = -1;
            this.fd_ = 0;
            this.fileName_ = "";
            this.fileSize_ = 0;
            this.fileCrc32_ = 0;
            this.fileOffset_ = 0;
            this.crc32AtOffset_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUFileInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (FUType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.fd_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.fileName_ = bs;
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.fileSize_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.fileCrc32_ = input.readFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.fileOffset_ = input.readFixed32();
                            break;
                        case 53:
                            this.bitField0_ |= 32;
                            this.crc32AtOffset_ = input.readFixed32();
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
            return FilesUpdate.internal_static_FUFileInfo_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUFileInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(FUFileInfo.class, Builder.class);
        }

        public boolean hasFd() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUType getFd() {
            FUType result = FUType.valueOf(this.fd_);
            return result == null ? FUType.GPS : result;
        }

        public boolean hasFileName() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getFileName() {
            Object ref = this.fileName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.fileName_ = s;
            }
            return s;
        }

        public ByteString getFileNameBytes() {
            Object ref = this.fileName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.fileName_ = b;
            return b;
        }

        public boolean hasFileSize() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getFileSize() {
            return this.fileSize_;
        }

        public boolean hasFileCrc32() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getFileCrc32() {
            return this.fileCrc32_;
        }

        public boolean hasFileOffset() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getFileOffset() {
            return this.fileOffset_;
        }

        public boolean hasCrc32AtOffset() {
            return (this.bitField0_ & 32) == 32;
        }

        public int getCrc32AtOffset() {
            return this.crc32AtOffset_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasFd()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFileName()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFileSize()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFileCrc32()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFileOffset()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCrc32AtOffset()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                GeneratedMessageV3.writeString(output, 2, this.fileName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.fileSize_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.fileCrc32_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.fileOffset_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeFixed32(6, this.crc32AtOffset_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += GeneratedMessageV3.computeStringSize(2, this.fileName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.fileSize_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.fileCrc32_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.fileOffset_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeFixed32Size(6, this.crc32AtOffset_);
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
            if (!(obj instanceof FUFileInfo)) {
                return super.equals(obj);
            }
            FUFileInfo other = (FUFileInfo) obj;
            boolean result7 = 1 != 0 && hasFd() == other.hasFd();
            if (hasFd()) {
                result7 = result7 && this.fd_ == other.fd_;
            }
            if (!result7 || hasFileName() != other.hasFileName()) {
                result = false;
            } else {
                result = true;
            }
            if (hasFileName()) {
                if (!result || !getFileName().equals(other.getFileName())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasFileSize() != other.hasFileSize()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasFileSize()) {
                if (!result2 || getFileSize() != other.getFileSize()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasFileCrc32() != other.hasFileCrc32()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasFileCrc32()) {
                if (!result3 || getFileCrc32() != other.getFileCrc32()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasFileOffset() != other.hasFileOffset()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasFileOffset()) {
                if (!result4 || getFileOffset() != other.getFileOffset()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasCrc32AtOffset() != other.hasCrc32AtOffset()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasCrc32AtOffset()) {
                if (!result5 || getCrc32AtOffset() != other.getCrc32AtOffset()) {
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
            if (hasFd()) {
                hash = (((hash * 37) + 1) * 53) + this.fd_;
            }
            if (hasFileName()) {
                hash = (((hash * 37) + 2) * 53) + getFileName().hashCode();
            }
            if (hasFileSize()) {
                hash = (((hash * 37) + 3) * 53) + getFileSize();
            }
            if (hasFileCrc32()) {
                hash = (((hash * 37) + 4) * 53) + getFileCrc32();
            }
            if (hasFileOffset()) {
                hash = (((hash * 37) + 5) * 53) + getFileOffset();
            }
            if (hasCrc32AtOffset()) {
                hash = (((hash * 37) + 6) * 53) + getCrc32AtOffset();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUFileInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUFileInfo) PARSER.parseFrom(data);
        }

        public static FUFileInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUFileInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUFileInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUFileInfo) PARSER.parseFrom(data);
        }

        public static FUFileInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUFileInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUFileInfo parseFrom(InputStream input) throws IOException {
            return (FUFileInfo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUFileInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUFileInfo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUFileInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (FUFileInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUFileInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUFileInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUFileInfo parseFrom(CodedInputStream input) throws IOException {
            return (FUFileInfo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUFileInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUFileInfo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUFileInfo prototype) {
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

        public static FUFileInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUFileInfo> parser() {
            return PARSER;
        }

        public Parser<FUFileInfo> getParserForType() {
            return PARSER;
        }

        public FUFileInfo getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUInitRequest extends GeneratedMessageV3 implements FUInitRequestOrBuilder {
        private static final FUInitRequest DEFAULT_INSTANCE = new FUInitRequest();
        public static final int INIT_INFO_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FUInitRequest> PARSER = new AbstractParser<FUInitRequest>() {
            public FUInitRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUInitRequest(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public FUFileInfo initInfo_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUInitRequestOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<FUFileInfo, Builder, FUFileInfoOrBuilder> initInfoBuilder_;
            private FUFileInfo initInfo_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUInitRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUInitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FUInitRequest.class, Builder.class);
            }

            private Builder() {
                this.initInfo_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.initInfo_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUInitRequest.alwaysUseFieldBuilders) {
                    getInitInfoFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.initInfoBuilder_ == null) {
                    this.initInfo_ = null;
                } else {
                    this.initInfoBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUInitRequest_descriptor;
            }

            public FUInitRequest getDefaultInstanceForType() {
                return FUInitRequest.getDefaultInstance();
            }

            public FUInitRequest build() {
                FUInitRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUInitRequest buildPartial() {
                FUInitRequest result = new FUInitRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.initInfoBuilder_ == null) {
                    result.initInfo_ = this.initInfo_;
                } else {
                    result.initInfo_ = (FUFileInfo) this.initInfoBuilder_.build();
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
                if (other instanceof FUInitRequest) {
                    return mergeFrom((FUInitRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUInitRequest other) {
                if (other != FUInitRequest.getDefaultInstance()) {
                    if (other.hasInitInfo()) {
                        mergeInitInfo(other.getInitInfo());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasInitInfo() && getInitInfo().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUInitRequest parsedMessage = null;
                try {
                    FUInitRequest parsedMessage2 = (FUInitRequest) FUInitRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUInitRequest parsedMessage3 = (FUInitRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasInitInfo() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUFileInfo getInitInfo() {
                if (this.initInfoBuilder_ == null) {
                    return this.initInfo_ == null ? FUFileInfo.getDefaultInstance() : this.initInfo_;
                }
                return (FUFileInfo) this.initInfoBuilder_.getMessage();
            }

            public Builder setInitInfo(FUFileInfo value) {
                if (this.initInfoBuilder_ != null) {
                    this.initInfoBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.initInfo_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setInitInfo(Builder builderForValue) {
                if (this.initInfoBuilder_ == null) {
                    this.initInfo_ = builderForValue.build();
                    onChanged();
                } else {
                    this.initInfoBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeInitInfo(FUFileInfo value) {
                if (this.initInfoBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.initInfo_ == null || this.initInfo_ == FUFileInfo.getDefaultInstance()) {
                        this.initInfo_ = value;
                    } else {
                        this.initInfo_ = FUFileInfo.newBuilder(this.initInfo_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.initInfoBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearInitInfo() {
                if (this.initInfoBuilder_ == null) {
                    this.initInfo_ = null;
                    onChanged();
                } else {
                    this.initInfoBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Builder getInitInfoBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getInitInfoFieldBuilder().getBuilder();
            }

            public FUFileInfoOrBuilder getInitInfoOrBuilder() {
                if (this.initInfoBuilder_ != null) {
                    return (FUFileInfoOrBuilder) this.initInfoBuilder_.getMessageOrBuilder();
                }
                return this.initInfo_ == null ? FUFileInfo.getDefaultInstance() : this.initInfo_;
            }

            private SingleFieldBuilderV3<FUFileInfo, Builder, FUFileInfoOrBuilder> getInitInfoFieldBuilder() {
                if (this.initInfoBuilder_ == null) {
                    this.initInfoBuilder_ = new SingleFieldBuilderV3<>(getInitInfo(), getParentForChildren(), isClean());
                    this.initInfo_ = null;
                }
                return this.initInfoBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private FUInitRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUInitRequest() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUInitRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = this.initInfo_.toBuilder();
                            }
                            this.initInfo_ = (FUFileInfo) input.readMessage(FUFileInfo.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.initInfo_);
                                this.initInfo_ = subBuilder.buildPartial();
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
            return FilesUpdate.internal_static_FUInitRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUInitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FUInitRequest.class, Builder.class);
        }

        public boolean hasInitInfo() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUFileInfo getInitInfo() {
            return this.initInfo_ == null ? FUFileInfo.getDefaultInstance() : this.initInfo_;
        }

        public FUFileInfoOrBuilder getInitInfoOrBuilder() {
            return this.initInfo_ == null ? FUFileInfo.getDefaultInstance() : this.initInfo_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasInitInfo()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getInitInfo().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getInitInfo());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getInitInfo());
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
            if (!(obj instanceof FUInitRequest)) {
                return super.equals(obj);
            }
            FUInitRequest other = (FUInitRequest) obj;
            boolean result2 = 1 != 0 && hasInitInfo() == other.hasInitInfo();
            if (hasInitInfo()) {
                result2 = result2 && getInitInfo().equals(other.getInitInfo());
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
            if (hasInitInfo()) {
                hash = (((hash * 37) + 1) * 53) + getInitInfo().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUInitRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUInitRequest) PARSER.parseFrom(data);
        }

        public static FUInitRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUInitRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUInitRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUInitRequest) PARSER.parseFrom(data);
        }

        public static FUInitRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUInitRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUInitRequest parseFrom(InputStream input) throws IOException {
            return (FUInitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUInitRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUInitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUInitRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (FUInitRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUInitRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUInitRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUInitRequest parseFrom(CodedInputStream input) throws IOException {
            return (FUInitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUInitRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUInitRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUInitRequest prototype) {
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

        public static FUInitRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUInitRequest> parser() {
            return PARSER;
        }

        public Parser<FUInitRequest> getParserForType() {
            return PARSER;
        }

        public FUInitRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FUInitResponse extends GeneratedMessageV3 implements FUInitResponseOrBuilder {
        private static final FUInitResponse DEFAULT_INSTANCE = new FUInitResponse();
        public static final int FD_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FUInitResponse> PARSER = new AbstractParser<FUInitResponse>() {
            public FUInitResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FUInitResponse(input, extensionRegistry);
            }
        };
        public static final int STATUS_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int fd_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int status_;

        public enum Status implements ProtocolMessageEnum {
            OK(0),
            ERROR_PARAMS(1),
            ERROR_INNER(2);
            
            public static final int ERROR_INNER_VALUE = 2;
            public static final int ERROR_PARAMS_VALUE = 1;
            public static final int OK_VALUE = 0;
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
                        return OK;
                    case 1:
                        return ERROR_PARAMS;
                    case 2:
                        return ERROR_INNER;
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
                return (EnumDescriptor) FUInitResponse.getDescriptor().getEnumTypes().get(0);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FUInitResponseOrBuilder {
            private int bitField0_;
            private int fd_;
            private int status_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FUInitResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FUInitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUInitResponse.class, Builder.class);
            }

            private Builder() {
                this.fd_ = 0;
                this.status_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fd_ = 0;
                this.status_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (FUInitResponse.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.fd_ = 0;
                this.bitField0_ &= -2;
                this.status_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FUInitResponse_descriptor;
            }

            public FUInitResponse getDefaultInstanceForType() {
                return FUInitResponse.getDefaultInstance();
            }

            public FUInitResponse build() {
                FUInitResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FUInitResponse buildPartial() {
                FUInitResponse result = new FUInitResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.fd_ = this.fd_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.status_ = this.status_;
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
                if (other instanceof FUInitResponse) {
                    return mergeFrom((FUInitResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FUInitResponse other) {
                if (other != FUInitResponse.getDefaultInstance()) {
                    if (other.hasFd()) {
                        setFd(other.getFd());
                    }
                    if (other.hasStatus()) {
                        setStatus(other.getStatus());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasFd() && hasStatus()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FUInitResponse parsedMessage = null;
                try {
                    FUInitResponse parsedMessage2 = (FUInitResponse) FUInitResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FUInitResponse parsedMessage3 = (FUInitResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasFd() {
                return (this.bitField0_ & 1) == 1;
            }

            public FUType getFd() {
                FUType result = FUType.valueOf(this.fd_);
                return result == null ? FUType.GPS : result;
            }

            public Builder setFd(FUType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.fd_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFd() {
                this.bitField0_ &= -2;
                this.fd_ = 0;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            public Status getStatus() {
                Status result = Status.valueOf(this.status_);
                return result == null ? Status.OK : result;
            }

            public Builder setStatus(Status value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.status_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -3;
                this.status_ = 0;
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

        private FUInitResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private FUInitResponse() {
            this.memoizedIsInitialized = -1;
            this.fd_ = 0;
            this.status_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FUInitResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (FUType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.fd_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            int rawValue2 = input.readEnum();
                            if (Status.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 2;
                                this.status_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue2);
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
            return FilesUpdate.internal_static_FUInitResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FUInitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FUInitResponse.class, Builder.class);
        }

        public boolean hasFd() {
            return (this.bitField0_ & 1) == 1;
        }

        public FUType getFd() {
            FUType result = FUType.valueOf(this.fd_);
            return result == null ? FUType.GPS : result;
        }

        public boolean hasStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        public Status getStatus() {
            Status result = Status.valueOf(this.status_);
            return result == null ? Status.OK : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasFd()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStatus()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.status_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.fd_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.status_);
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
            if (!(obj instanceof FUInitResponse)) {
                return super.equals(obj);
            }
            FUInitResponse other = (FUInitResponse) obj;
            boolean result3 = 1 != 0 && hasFd() == other.hasFd();
            if (hasFd()) {
                result3 = result3 && this.fd_ == other.fd_;
            }
            if (!result3 || hasStatus() != other.hasStatus()) {
                result = false;
            } else {
                result = true;
            }
            if (hasStatus()) {
                if (!result || this.status_ != other.status_) {
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
            if (hasFd()) {
                hash = (((hash * 37) + 1) * 53) + this.fd_;
            }
            if (hasStatus()) {
                hash = (((hash * 37) + 2) * 53) + this.status_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FUInitResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FUInitResponse) PARSER.parseFrom(data);
        }

        public static FUInitResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUInitResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUInitResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FUInitResponse) PARSER.parseFrom(data);
        }

        public static FUInitResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FUInitResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FUInitResponse parseFrom(InputStream input) throws IOException {
            return (FUInitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUInitResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUInitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUInitResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (FUInitResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FUInitResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUInitResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FUInitResponse parseFrom(CodedInputStream input) throws IOException {
            return (FUInitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FUInitResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FUInitResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FUInitResponse prototype) {
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

        public static FUInitResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FUInitResponse> parser() {
            return PARSER;
        }

        public Parser<FUInitResponse> getParserForType() {
            return PARSER;
        }

        public FUInitResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum FUType implements ProtocolMessageEnum {
        GPS(0),
        FONT(1),
        MGAONLINE(2);
        
        public static final int FONT_VALUE = 1;
        public static final int GPS_VALUE = 0;
        public static final int MGAONLINE_VALUE = 2;
        private static final FUType[] VALUES = null;
        private static final EnumLiteMap<FUType> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<FUType>() {
                public FUType findValueByNumber(int number) {
                    return FUType.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static FUType valueOf(int value2) {
            return forNumber(value2);
        }

        public static FUType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return GPS;
                case 1:
                    return FONT;
                case 2:
                    return MGAONLINE;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<FUType> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) FilesUpdate.getDescriptor().getEnumTypes().get(0);
        }

        public static FUType valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private FUType(int value2) {
            this.value = value2;
        }
    }

    public static final class FilesUpdateRequest extends GeneratedMessageV3 implements FilesUpdateRequestOrBuilder {
        public static final int DATA_FIELD_NUMBER = 2;
        private static final FilesUpdateRequest DEFAULT_INSTANCE = new FilesUpdateRequest();
        public static final int DESC_FIELD_NUMBER = 4;
        public static final int EXIT_FIELD_NUMBER = 3;
        public static final int INIT_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FilesUpdateRequest> PARSER = new AbstractParser<FilesUpdateRequest>() {
            public FilesUpdateRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FilesUpdateRequest(input, extensionRegistry);
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
            INIT(1),
            DATA(2),
            EXIT(3),
            DESC(4),
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
                        return INIT;
                    case 2:
                        return DATA;
                    case 3:
                        return EXIT;
                    case 4:
                        return DESC;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FilesUpdateRequestOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<FUDataRequest, Builder, FUDataRequestOrBuilder> dataBuilder_;
            private SingleFieldBuilderV3<FUExitRequest, Builder, FUExitRequestOrBuilder> exitBuilder_;
            private SingleFieldBuilderV3<FUInitRequest, Builder, FUInitRequestOrBuilder> initBuilder_;
            private int paramsCase_;
            private Object params_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FilesUpdateRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FilesUpdateRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FilesUpdateRequest.class, Builder.class);
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
                if (FilesUpdateRequest.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.paramsCase_ = 0;
                this.params_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FilesUpdateRequest_descriptor;
            }

            public FilesUpdateRequest getDefaultInstanceForType() {
                return FilesUpdateRequest.getDefaultInstance();
            }

            public FilesUpdateRequest build() {
                FilesUpdateRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FilesUpdateRequest buildPartial() {
                FilesUpdateRequest result = new FilesUpdateRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if (this.paramsCase_ == 1) {
                    if (this.initBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.initBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 2) {
                    if (this.dataBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.dataBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 3) {
                    if (this.exitBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.exitBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 4) {
                    result.params_ = this.params_;
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
                if (other instanceof FilesUpdateRequest) {
                    return mergeFrom((FilesUpdateRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FilesUpdateRequest other) {
                if (other != FilesUpdateRequest.getDefaultInstance()) {
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase[other.getParamsCase().ordinal()]) {
                        case 1:
                            mergeInit(other.getInit());
                            break;
                        case 2:
                            mergeData(other.getData());
                            break;
                        case 3:
                            mergeExit(other.getExit());
                            break;
                        case 4:
                            setDesc(other.getDesc());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasInit() && !getInit().isInitialized()) {
                    return false;
                }
                if (hasData() && !getData().isInitialized()) {
                    return false;
                }
                if (!hasExit() || getExit().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FilesUpdateRequest parsedMessage = null;
                try {
                    FilesUpdateRequest parsedMessage2 = (FilesUpdateRequest) FilesUpdateRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FilesUpdateRequest parsedMessage3 = (FilesUpdateRequest) e.getUnfinishedMessage();
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

            public boolean hasInit() {
                return this.paramsCase_ == 1;
            }

            public FUInitRequest getInit() {
                if (this.initBuilder_ == null) {
                    if (this.paramsCase_ == 1) {
                        return (FUInitRequest) this.params_;
                    }
                    return FUInitRequest.getDefaultInstance();
                } else if (this.paramsCase_ == 1) {
                    return (FUInitRequest) this.initBuilder_.getMessage();
                } else {
                    return FUInitRequest.getDefaultInstance();
                }
            }

            public Builder setInit(FUInitRequest value) {
                if (this.initBuilder_ != null) {
                    this.initBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder setInit(Builder builderForValue) {
                if (this.initBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.initBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder mergeInit(FUInitRequest value) {
                if (this.initBuilder_ == null) {
                    if (this.paramsCase_ != 1 || this.params_ == FUInitRequest.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUInitRequest.newBuilder((FUInitRequest) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 1) {
                        this.initBuilder_.mergeFrom(value);
                    }
                    this.initBuilder_.setMessage(value);
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder clearInit() {
                if (this.initBuilder_ != null) {
                    if (this.paramsCase_ == 1) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.initBuilder_.clear();
                } else if (this.paramsCase_ == 1) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getInitBuilder() {
                return (Builder) getInitFieldBuilder().getBuilder();
            }

            public FUInitRequestOrBuilder getInitOrBuilder() {
                if (this.paramsCase_ == 1 && this.initBuilder_ != null) {
                    return (FUInitRequestOrBuilder) this.initBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 1) {
                    return (FUInitRequest) this.params_;
                }
                return FUInitRequest.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUInitRequest, Builder, FUInitRequestOrBuilder> getInitFieldBuilder() {
                if (this.initBuilder_ == null) {
                    if (this.paramsCase_ != 1) {
                        this.params_ = FUInitRequest.getDefaultInstance();
                    }
                    this.initBuilder_ = new SingleFieldBuilderV3<>((FUInitRequest) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 1;
                onChanged();
                return this.initBuilder_;
            }

            public boolean hasData() {
                return this.paramsCase_ == 2;
            }

            public FUDataRequest getData() {
                if (this.dataBuilder_ == null) {
                    if (this.paramsCase_ == 2) {
                        return (FUDataRequest) this.params_;
                    }
                    return FUDataRequest.getDefaultInstance();
                } else if (this.paramsCase_ == 2) {
                    return (FUDataRequest) this.dataBuilder_.getMessage();
                } else {
                    return FUDataRequest.getDefaultInstance();
                }
            }

            public Builder setData(FUDataRequest value) {
                if (this.dataBuilder_ != null) {
                    this.dataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder setData(Builder builderForValue) {
                if (this.dataBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dataBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder mergeData(FUDataRequest value) {
                if (this.dataBuilder_ == null) {
                    if (this.paramsCase_ != 2 || this.params_ == FUDataRequest.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUDataRequest.newBuilder((FUDataRequest) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 2) {
                        this.dataBuilder_.mergeFrom(value);
                    }
                    this.dataBuilder_.setMessage(value);
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder clearData() {
                if (this.dataBuilder_ != null) {
                    if (this.paramsCase_ == 2) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.dataBuilder_.clear();
                } else if (this.paramsCase_ == 2) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getDataBuilder() {
                return (Builder) getDataFieldBuilder().getBuilder();
            }

            public FUDataRequestOrBuilder getDataOrBuilder() {
                if (this.paramsCase_ == 2 && this.dataBuilder_ != null) {
                    return (FUDataRequestOrBuilder) this.dataBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 2) {
                    return (FUDataRequest) this.params_;
                }
                return FUDataRequest.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUDataRequest, Builder, FUDataRequestOrBuilder> getDataFieldBuilder() {
                if (this.dataBuilder_ == null) {
                    if (this.paramsCase_ != 2) {
                        this.params_ = FUDataRequest.getDefaultInstance();
                    }
                    this.dataBuilder_ = new SingleFieldBuilderV3<>((FUDataRequest) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 2;
                onChanged();
                return this.dataBuilder_;
            }

            public boolean hasExit() {
                return this.paramsCase_ == 3;
            }

            public FUExitRequest getExit() {
                if (this.exitBuilder_ == null) {
                    if (this.paramsCase_ == 3) {
                        return (FUExitRequest) this.params_;
                    }
                    return FUExitRequest.getDefaultInstance();
                } else if (this.paramsCase_ == 3) {
                    return (FUExitRequest) this.exitBuilder_.getMessage();
                } else {
                    return FUExitRequest.getDefaultInstance();
                }
            }

            public Builder setExit(FUExitRequest value) {
                if (this.exitBuilder_ != null) {
                    this.exitBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder setExit(Builder builderForValue) {
                if (this.exitBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.exitBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder mergeExit(FUExitRequest value) {
                if (this.exitBuilder_ == null) {
                    if (this.paramsCase_ != 3 || this.params_ == FUExitRequest.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUExitRequest.newBuilder((FUExitRequest) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 3) {
                        this.exitBuilder_.mergeFrom(value);
                    }
                    this.exitBuilder_.setMessage(value);
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder clearExit() {
                if (this.exitBuilder_ != null) {
                    if (this.paramsCase_ == 3) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.exitBuilder_.clear();
                } else if (this.paramsCase_ == 3) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getExitBuilder() {
                return (Builder) getExitFieldBuilder().getBuilder();
            }

            public FUExitRequestOrBuilder getExitOrBuilder() {
                if (this.paramsCase_ == 3 && this.exitBuilder_ != null) {
                    return (FUExitRequestOrBuilder) this.exitBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 3) {
                    return (FUExitRequest) this.params_;
                }
                return FUExitRequest.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUExitRequest, Builder, FUExitRequestOrBuilder> getExitFieldBuilder() {
                if (this.exitBuilder_ == null) {
                    if (this.paramsCase_ != 3) {
                        this.params_ = FUExitRequest.getDefaultInstance();
                    }
                    this.exitBuilder_ = new SingleFieldBuilderV3<>((FUExitRequest) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 3;
                onChanged();
                return this.exitBuilder_;
            }

            public boolean hasDesc() {
                return this.paramsCase_ == 4;
            }

            public boolean getDesc() {
                if (this.paramsCase_ == 4) {
                    return ((Boolean) this.params_).booleanValue();
                }
                return false;
            }

            public Builder setDesc(boolean value) {
                this.paramsCase_ = 4;
                this.params_ = Boolean.valueOf(value);
                onChanged();
                return this;
            }

            public Builder clearDesc() {
                if (this.paramsCase_ == 4) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private FilesUpdateRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.paramsCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private FilesUpdateRequest() {
            this.paramsCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FilesUpdateRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = ((FUInitRequest) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUInitRequest.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((FUInitRequest) this.params_);
                                this.params_ = subBuilder.buildPartial();
                            }
                            this.paramsCase_ = 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if (this.paramsCase_ == 2) {
                                subBuilder2 = ((FUDataRequest) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUDataRequest.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((FUDataRequest) this.params_);
                                this.params_ = subBuilder2.buildPartial();
                            }
                            this.paramsCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder3 = null;
                            if (this.paramsCase_ == 3) {
                                subBuilder3 = ((FUExitRequest) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUExitRequest.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom((FUExitRequest) this.params_);
                                this.params_ = subBuilder3.buildPartial();
                            }
                            this.paramsCase_ = 3;
                            break;
                        case 32:
                            this.paramsCase_ = 4;
                            this.params_ = Boolean.valueOf(input.readBool());
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
            return FilesUpdate.internal_static_FilesUpdateRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FilesUpdateRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(FilesUpdateRequest.class, Builder.class);
        }

        public ParamsCase getParamsCase() {
            return ParamsCase.forNumber(this.paramsCase_);
        }

        public boolean hasInit() {
            return this.paramsCase_ == 1;
        }

        public FUInitRequest getInit() {
            if (this.paramsCase_ == 1) {
                return (FUInitRequest) this.params_;
            }
            return FUInitRequest.getDefaultInstance();
        }

        public FUInitRequestOrBuilder getInitOrBuilder() {
            if (this.paramsCase_ == 1) {
                return (FUInitRequest) this.params_;
            }
            return FUInitRequest.getDefaultInstance();
        }

        public boolean hasData() {
            return this.paramsCase_ == 2;
        }

        public FUDataRequest getData() {
            if (this.paramsCase_ == 2) {
                return (FUDataRequest) this.params_;
            }
            return FUDataRequest.getDefaultInstance();
        }

        public FUDataRequestOrBuilder getDataOrBuilder() {
            if (this.paramsCase_ == 2) {
                return (FUDataRequest) this.params_;
            }
            return FUDataRequest.getDefaultInstance();
        }

        public boolean hasExit() {
            return this.paramsCase_ == 3;
        }

        public FUExitRequest getExit() {
            if (this.paramsCase_ == 3) {
                return (FUExitRequest) this.params_;
            }
            return FUExitRequest.getDefaultInstance();
        }

        public FUExitRequestOrBuilder getExitOrBuilder() {
            if (this.paramsCase_ == 3) {
                return (FUExitRequest) this.params_;
            }
            return FUExitRequest.getDefaultInstance();
        }

        public boolean hasDesc() {
            return this.paramsCase_ == 4;
        }

        public boolean getDesc() {
            if (this.paramsCase_ == 4) {
                return ((Boolean) this.params_).booleanValue();
            }
            return false;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasInit() && !getInit().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasData() && !getData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasExit() || getExit().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.paramsCase_ == 1) {
                output.writeMessage(1, (FUInitRequest) this.params_);
            }
            if (this.paramsCase_ == 2) {
                output.writeMessage(2, (FUDataRequest) this.params_);
            }
            if (this.paramsCase_ == 3) {
                output.writeMessage(3, (FUExitRequest) this.params_);
            }
            if (this.paramsCase_ == 4) {
                output.writeBool(4, ((Boolean) this.params_).booleanValue());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, (FUInitRequest) this.params_);
            }
            if (this.paramsCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (FUDataRequest) this.params_);
            }
            if (this.paramsCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (FUExitRequest) this.params_);
            }
            if (this.paramsCase_ == 4) {
                size2 += CodedOutputStream.computeBoolSize(4, ((Boolean) this.params_).booleanValue());
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
            if (!(obj instanceof FilesUpdateRequest)) {
                return super.equals(obj);
            }
            FilesUpdateRequest other = (FilesUpdateRequest) obj;
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
                    if (result && getInit().equals(other.getInit())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 2:
                    if (result && getData().equals(other.getData())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getExit().equals(other.getExit())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 4:
                    if (result && getDesc() == other.getDesc()) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
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
            switch (this.paramsCase_) {
                case 1:
                    hash = (((hash * 37) + 1) * 53) + getInit().hashCode();
                    break;
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getData().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getExit().hashCode();
                    break;
                case 4:
                    hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getDesc());
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FilesUpdateRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FilesUpdateRequest) PARSER.parseFrom(data);
        }

        public static FilesUpdateRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FilesUpdateRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FilesUpdateRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FilesUpdateRequest) PARSER.parseFrom(data);
        }

        public static FilesUpdateRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FilesUpdateRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FilesUpdateRequest parseFrom(InputStream input) throws IOException {
            return (FilesUpdateRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FilesUpdateRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FilesUpdateRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FilesUpdateRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (FilesUpdateRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FilesUpdateRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FilesUpdateRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FilesUpdateRequest parseFrom(CodedInputStream input) throws IOException {
            return (FilesUpdateRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FilesUpdateRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FilesUpdateRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FilesUpdateRequest prototype) {
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

        public static FilesUpdateRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FilesUpdateRequest> parser() {
            return PARSER;
        }

        public Parser<FilesUpdateRequest> getParserForType() {
            return PARSER;
        }

        public FilesUpdateRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class FilesUpdateResponse extends GeneratedMessageV3 implements FilesUpdateResponseOrBuilder {
        public static final int DATA_FIELD_NUMBER = 2;
        private static final FilesUpdateResponse DEFAULT_INSTANCE = new FilesUpdateResponse();
        public static final int DESC_FIELD_NUMBER = 4;
        public static final int EXIT_FIELD_NUMBER = 3;
        public static final int INIT_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<FilesUpdateResponse> PARSER = new AbstractParser<FilesUpdateResponse>() {
            public FilesUpdateResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FilesUpdateResponse(input, extensionRegistry);
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
            INIT(1),
            DATA(2),
            EXIT(3),
            DESC(4),
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
                        return INIT;
                    case 2:
                        return DATA;
                    case 3:
                        return EXIT;
                    case 4:
                        return DESC;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FilesUpdateResponseOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<FUDataResponse, Builder, FUDataResponseOrBuilder> dataBuilder_;
            private SingleFieldBuilderV3<FUDescResponse, Builder, FUDescResponseOrBuilder> descBuilder_;
            private SingleFieldBuilderV3<FUExitResponse, Builder, FUExitResponseOrBuilder> exitBuilder_;
            private SingleFieldBuilderV3<FUInitResponse, Builder, FUInitResponseOrBuilder> initBuilder_;
            private int paramsCase_;
            private Object params_;

            public static final Descriptor getDescriptor() {
                return FilesUpdate.internal_static_FilesUpdateResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return FilesUpdate.internal_static_FilesUpdateResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FilesUpdateResponse.class, Builder.class);
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
                if (FilesUpdateResponse.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.paramsCase_ = 0;
                this.params_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return FilesUpdate.internal_static_FilesUpdateResponse_descriptor;
            }

            public FilesUpdateResponse getDefaultInstanceForType() {
                return FilesUpdateResponse.getDefaultInstance();
            }

            public FilesUpdateResponse build() {
                FilesUpdateResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public FilesUpdateResponse buildPartial() {
                FilesUpdateResponse result = new FilesUpdateResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if (this.paramsCase_ == 1) {
                    if (this.initBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.initBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 2) {
                    if (this.dataBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.dataBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 3) {
                    if (this.exitBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.exitBuilder_.build();
                    }
                }
                if (this.paramsCase_ == 4) {
                    if (this.descBuilder_ == null) {
                        result.params_ = this.params_;
                    } else {
                        result.params_ = this.descBuilder_.build();
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
                if (other instanceof FilesUpdateResponse) {
                    return mergeFrom((FilesUpdateResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FilesUpdateResponse other) {
                if (other != FilesUpdateResponse.getDefaultInstance()) {
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase[other.getParamsCase().ordinal()]) {
                        case 1:
                            mergeInit(other.getInit());
                            break;
                        case 2:
                            mergeData(other.getData());
                            break;
                        case 3:
                            mergeExit(other.getExit());
                            break;
                        case 4:
                            mergeDesc(other.getDesc());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasInit() && !getInit().isInitialized()) {
                    return false;
                }
                if (hasData() && !getData().isInitialized()) {
                    return false;
                }
                if (hasExit() && !getExit().isInitialized()) {
                    return false;
                }
                if (!hasDesc() || getDesc().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FilesUpdateResponse parsedMessage = null;
                try {
                    FilesUpdateResponse parsedMessage2 = (FilesUpdateResponse) FilesUpdateResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FilesUpdateResponse parsedMessage3 = (FilesUpdateResponse) e.getUnfinishedMessage();
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

            public boolean hasInit() {
                return this.paramsCase_ == 1;
            }

            public FUInitResponse getInit() {
                if (this.initBuilder_ == null) {
                    if (this.paramsCase_ == 1) {
                        return (FUInitResponse) this.params_;
                    }
                    return FUInitResponse.getDefaultInstance();
                } else if (this.paramsCase_ == 1) {
                    return (FUInitResponse) this.initBuilder_.getMessage();
                } else {
                    return FUInitResponse.getDefaultInstance();
                }
            }

            public Builder setInit(FUInitResponse value) {
                if (this.initBuilder_ != null) {
                    this.initBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder setInit(Builder builderForValue) {
                if (this.initBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.initBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder mergeInit(FUInitResponse value) {
                if (this.initBuilder_ == null) {
                    if (this.paramsCase_ != 1 || this.params_ == FUInitResponse.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUInitResponse.newBuilder((FUInitResponse) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 1) {
                        this.initBuilder_.mergeFrom(value);
                    }
                    this.initBuilder_.setMessage(value);
                }
                this.paramsCase_ = 1;
                return this;
            }

            public Builder clearInit() {
                if (this.initBuilder_ != null) {
                    if (this.paramsCase_ == 1) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.initBuilder_.clear();
                } else if (this.paramsCase_ == 1) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getInitBuilder() {
                return (Builder) getInitFieldBuilder().getBuilder();
            }

            public FUInitResponseOrBuilder getInitOrBuilder() {
                if (this.paramsCase_ == 1 && this.initBuilder_ != null) {
                    return (FUInitResponseOrBuilder) this.initBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 1) {
                    return (FUInitResponse) this.params_;
                }
                return FUInitResponse.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUInitResponse, Builder, FUInitResponseOrBuilder> getInitFieldBuilder() {
                if (this.initBuilder_ == null) {
                    if (this.paramsCase_ != 1) {
                        this.params_ = FUInitResponse.getDefaultInstance();
                    }
                    this.initBuilder_ = new SingleFieldBuilderV3<>((FUInitResponse) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 1;
                onChanged();
                return this.initBuilder_;
            }

            public boolean hasData() {
                return this.paramsCase_ == 2;
            }

            public FUDataResponse getData() {
                if (this.dataBuilder_ == null) {
                    if (this.paramsCase_ == 2) {
                        return (FUDataResponse) this.params_;
                    }
                    return FUDataResponse.getDefaultInstance();
                } else if (this.paramsCase_ == 2) {
                    return (FUDataResponse) this.dataBuilder_.getMessage();
                } else {
                    return FUDataResponse.getDefaultInstance();
                }
            }

            public Builder setData(FUDataResponse value) {
                if (this.dataBuilder_ != null) {
                    this.dataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder setData(Builder builderForValue) {
                if (this.dataBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dataBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder mergeData(FUDataResponse value) {
                if (this.dataBuilder_ == null) {
                    if (this.paramsCase_ != 2 || this.params_ == FUDataResponse.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUDataResponse.newBuilder((FUDataResponse) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 2) {
                        this.dataBuilder_.mergeFrom(value);
                    }
                    this.dataBuilder_.setMessage(value);
                }
                this.paramsCase_ = 2;
                return this;
            }

            public Builder clearData() {
                if (this.dataBuilder_ != null) {
                    if (this.paramsCase_ == 2) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.dataBuilder_.clear();
                } else if (this.paramsCase_ == 2) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getDataBuilder() {
                return (Builder) getDataFieldBuilder().getBuilder();
            }

            public FUDataResponseOrBuilder getDataOrBuilder() {
                if (this.paramsCase_ == 2 && this.dataBuilder_ != null) {
                    return (FUDataResponseOrBuilder) this.dataBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 2) {
                    return (FUDataResponse) this.params_;
                }
                return FUDataResponse.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUDataResponse, Builder, FUDataResponseOrBuilder> getDataFieldBuilder() {
                if (this.dataBuilder_ == null) {
                    if (this.paramsCase_ != 2) {
                        this.params_ = FUDataResponse.getDefaultInstance();
                    }
                    this.dataBuilder_ = new SingleFieldBuilderV3<>((FUDataResponse) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 2;
                onChanged();
                return this.dataBuilder_;
            }

            public boolean hasExit() {
                return this.paramsCase_ == 3;
            }

            public FUExitResponse getExit() {
                if (this.exitBuilder_ == null) {
                    if (this.paramsCase_ == 3) {
                        return (FUExitResponse) this.params_;
                    }
                    return FUExitResponse.getDefaultInstance();
                } else if (this.paramsCase_ == 3) {
                    return (FUExitResponse) this.exitBuilder_.getMessage();
                } else {
                    return FUExitResponse.getDefaultInstance();
                }
            }

            public Builder setExit(FUExitResponse value) {
                if (this.exitBuilder_ != null) {
                    this.exitBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder setExit(Builder builderForValue) {
                if (this.exitBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.exitBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder mergeExit(FUExitResponse value) {
                if (this.exitBuilder_ == null) {
                    if (this.paramsCase_ != 3 || this.params_ == FUExitResponse.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUExitResponse.newBuilder((FUExitResponse) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 3) {
                        this.exitBuilder_.mergeFrom(value);
                    }
                    this.exitBuilder_.setMessage(value);
                }
                this.paramsCase_ = 3;
                return this;
            }

            public Builder clearExit() {
                if (this.exitBuilder_ != null) {
                    if (this.paramsCase_ == 3) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.exitBuilder_.clear();
                } else if (this.paramsCase_ == 3) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getExitBuilder() {
                return (Builder) getExitFieldBuilder().getBuilder();
            }

            public FUExitResponseOrBuilder getExitOrBuilder() {
                if (this.paramsCase_ == 3 && this.exitBuilder_ != null) {
                    return (FUExitResponseOrBuilder) this.exitBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 3) {
                    return (FUExitResponse) this.params_;
                }
                return FUExitResponse.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUExitResponse, Builder, FUExitResponseOrBuilder> getExitFieldBuilder() {
                if (this.exitBuilder_ == null) {
                    if (this.paramsCase_ != 3) {
                        this.params_ = FUExitResponse.getDefaultInstance();
                    }
                    this.exitBuilder_ = new SingleFieldBuilderV3<>((FUExitResponse) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 3;
                onChanged();
                return this.exitBuilder_;
            }

            public boolean hasDesc() {
                return this.paramsCase_ == 4;
            }

            public FUDescResponse getDesc() {
                if (this.descBuilder_ == null) {
                    if (this.paramsCase_ == 4) {
                        return (FUDescResponse) this.params_;
                    }
                    return FUDescResponse.getDefaultInstance();
                } else if (this.paramsCase_ == 4) {
                    return (FUDescResponse) this.descBuilder_.getMessage();
                } else {
                    return FUDescResponse.getDefaultInstance();
                }
            }

            public Builder setDesc(FUDescResponse value) {
                if (this.descBuilder_ != null) {
                    this.descBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.params_ = value;
                    onChanged();
                }
                this.paramsCase_ = 4;
                return this;
            }

            public Builder setDesc(Builder builderForValue) {
                if (this.descBuilder_ == null) {
                    this.params_ = builderForValue.build();
                    onChanged();
                } else {
                    this.descBuilder_.setMessage(builderForValue.build());
                }
                this.paramsCase_ = 4;
                return this;
            }

            public Builder mergeDesc(FUDescResponse value) {
                if (this.descBuilder_ == null) {
                    if (this.paramsCase_ != 4 || this.params_ == FUDescResponse.getDefaultInstance()) {
                        this.params_ = value;
                    } else {
                        this.params_ = FUDescResponse.newBuilder((FUDescResponse) this.params_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.paramsCase_ == 4) {
                        this.descBuilder_.mergeFrom(value);
                    }
                    this.descBuilder_.setMessage(value);
                }
                this.paramsCase_ = 4;
                return this;
            }

            public Builder clearDesc() {
                if (this.descBuilder_ != null) {
                    if (this.paramsCase_ == 4) {
                        this.paramsCase_ = 0;
                        this.params_ = null;
                    }
                    this.descBuilder_.clear();
                } else if (this.paramsCase_ == 4) {
                    this.paramsCase_ = 0;
                    this.params_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getDescBuilder() {
                return (Builder) getDescFieldBuilder().getBuilder();
            }

            public FUDescResponseOrBuilder getDescOrBuilder() {
                if (this.paramsCase_ == 4 && this.descBuilder_ != null) {
                    return (FUDescResponseOrBuilder) this.descBuilder_.getMessageOrBuilder();
                }
                if (this.paramsCase_ == 4) {
                    return (FUDescResponse) this.params_;
                }
                return FUDescResponse.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FUDescResponse, Builder, FUDescResponseOrBuilder> getDescFieldBuilder() {
                if (this.descBuilder_ == null) {
                    if (this.paramsCase_ != 4) {
                        this.params_ = FUDescResponse.getDefaultInstance();
                    }
                    this.descBuilder_ = new SingleFieldBuilderV3<>((FUDescResponse) this.params_, getParentForChildren(), isClean());
                    this.params_ = null;
                }
                this.paramsCase_ = 4;
                onChanged();
                return this.descBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private FilesUpdateResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.paramsCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private FilesUpdateResponse() {
            this.paramsCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FilesUpdateResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = ((FUInitResponse) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUInitResponse.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((FUInitResponse) this.params_);
                                this.params_ = subBuilder.buildPartial();
                            }
                            this.paramsCase_ = 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if (this.paramsCase_ == 2) {
                                subBuilder2 = ((FUDataResponse) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUDataResponse.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((FUDataResponse) this.params_);
                                this.params_ = subBuilder2.buildPartial();
                            }
                            this.paramsCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder3 = null;
                            if (this.paramsCase_ == 3) {
                                subBuilder3 = ((FUExitResponse) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUExitResponse.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom((FUExitResponse) this.params_);
                                this.params_ = subBuilder3.buildPartial();
                            }
                            this.paramsCase_ = 3;
                            break;
                        case 34:
                            Builder subBuilder4 = null;
                            if (this.paramsCase_ == 4) {
                                subBuilder4 = ((FUDescResponse) this.params_).toBuilder();
                            }
                            this.params_ = input.readMessage(FUDescResponse.PARSER, extensionRegistry);
                            if (subBuilder4 != null) {
                                subBuilder4.mergeFrom((FUDescResponse) this.params_);
                                this.params_ = subBuilder4.buildPartial();
                            }
                            this.paramsCase_ = 4;
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
            return FilesUpdate.internal_static_FilesUpdateResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return FilesUpdate.internal_static_FilesUpdateResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(FilesUpdateResponse.class, Builder.class);
        }

        public ParamsCase getParamsCase() {
            return ParamsCase.forNumber(this.paramsCase_);
        }

        public boolean hasInit() {
            return this.paramsCase_ == 1;
        }

        public FUInitResponse getInit() {
            if (this.paramsCase_ == 1) {
                return (FUInitResponse) this.params_;
            }
            return FUInitResponse.getDefaultInstance();
        }

        public FUInitResponseOrBuilder getInitOrBuilder() {
            if (this.paramsCase_ == 1) {
                return (FUInitResponse) this.params_;
            }
            return FUInitResponse.getDefaultInstance();
        }

        public boolean hasData() {
            return this.paramsCase_ == 2;
        }

        public FUDataResponse getData() {
            if (this.paramsCase_ == 2) {
                return (FUDataResponse) this.params_;
            }
            return FUDataResponse.getDefaultInstance();
        }

        public FUDataResponseOrBuilder getDataOrBuilder() {
            if (this.paramsCase_ == 2) {
                return (FUDataResponse) this.params_;
            }
            return FUDataResponse.getDefaultInstance();
        }

        public boolean hasExit() {
            return this.paramsCase_ == 3;
        }

        public FUExitResponse getExit() {
            if (this.paramsCase_ == 3) {
                return (FUExitResponse) this.params_;
            }
            return FUExitResponse.getDefaultInstance();
        }

        public FUExitResponseOrBuilder getExitOrBuilder() {
            if (this.paramsCase_ == 3) {
                return (FUExitResponse) this.params_;
            }
            return FUExitResponse.getDefaultInstance();
        }

        public boolean hasDesc() {
            return this.paramsCase_ == 4;
        }

        public FUDescResponse getDesc() {
            if (this.paramsCase_ == 4) {
                return (FUDescResponse) this.params_;
            }
            return FUDescResponse.getDefaultInstance();
        }

        public FUDescResponseOrBuilder getDescOrBuilder() {
            if (this.paramsCase_ == 4) {
                return (FUDescResponse) this.params_;
            }
            return FUDescResponse.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasInit() && !getInit().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasData() && !getData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasExit() && !getExit().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDesc() || getDesc().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.paramsCase_ == 1) {
                output.writeMessage(1, (FUInitResponse) this.params_);
            }
            if (this.paramsCase_ == 2) {
                output.writeMessage(2, (FUDataResponse) this.params_);
            }
            if (this.paramsCase_ == 3) {
                output.writeMessage(3, (FUExitResponse) this.params_);
            }
            if (this.paramsCase_ == 4) {
                output.writeMessage(4, (FUDescResponse) this.params_);
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, (FUInitResponse) this.params_);
            }
            if (this.paramsCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (FUDataResponse) this.params_);
            }
            if (this.paramsCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (FUExitResponse) this.params_);
            }
            if (this.paramsCase_ == 4) {
                size2 += CodedOutputStream.computeMessageSize(4, (FUDescResponse) this.params_);
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
            if (!(obj instanceof FilesUpdateResponse)) {
                return super.equals(obj);
            }
            FilesUpdateResponse other = (FilesUpdateResponse) obj;
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
                    if (result && getInit().equals(other.getInit())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 2:
                    if (result && getData().equals(other.getData())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getExit().equals(other.getExit())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 4:
                    if (result && getDesc().equals(other.getDesc())) {
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
                    hash = (((hash * 37) + 1) * 53) + getInit().hashCode();
                    break;
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getData().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getExit().hashCode();
                    break;
                case 4:
                    hash = (((hash * 37) + 4) * 53) + getDesc().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static FilesUpdateResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FilesUpdateResponse) PARSER.parseFrom(data);
        }

        public static FilesUpdateResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FilesUpdateResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FilesUpdateResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FilesUpdateResponse) PARSER.parseFrom(data);
        }

        public static FilesUpdateResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FilesUpdateResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FilesUpdateResponse parseFrom(InputStream input) throws IOException {
            return (FilesUpdateResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FilesUpdateResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FilesUpdateResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FilesUpdateResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (FilesUpdateResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FilesUpdateResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FilesUpdateResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FilesUpdateResponse parseFrom(CodedInputStream input) throws IOException {
            return (FilesUpdateResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FilesUpdateResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FilesUpdateResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FilesUpdateResponse prototype) {
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

        public static FilesUpdateResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FilesUpdateResponse> parser() {
            return PARSER;
        }

        public Parser<FilesUpdateResponse> getParserForType() {
            return PARSER;
        }

        public FilesUpdateResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.FilesUpdate$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase = new int[ParamsCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase = new int[ParamsCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase[ParamsCase.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase[ParamsCase.DATA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase[ParamsCase.EXIT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase[ParamsCase.DESC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateRequest$ParamsCase[ParamsCase.PARAMS_NOT_SET.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase[ParamsCase.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase[ParamsCase.DATA.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase[ParamsCase.EXIT.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase[ParamsCase.DESC.ordinal()] = 4;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$FilesUpdate$FilesUpdateResponse$ParamsCase[ParamsCase.PARAMS_NOT_SET.ordinal()] = 5;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    public interface FUDataRequestOrBuilder extends MessageOrBuilder {
        ByteString getBuf();

        int getCrc32AtOffset();

        FUType getFd();

        int getFileOffset();

        boolean hasBuf();

        boolean hasCrc32AtOffset();

        boolean hasFd();

        boolean hasFileOffset();
    }

    public interface FUDataResponseOrBuilder extends MessageOrBuilder {
        int getCrc32AtOffset();

        FUType getFd();

        int getFileOffset();

        Status getStatus();

        boolean hasCrc32AtOffset();

        boolean hasFd();

        boolean hasFileOffset();

        boolean hasStatus();
    }

    public interface FUDescResponseOrBuilder extends MessageOrBuilder {
        FUFileDesc getFont();

        FUFileDescOrBuilder getFontOrBuilder();

        FUFileDesc getGps();

        FUFileDescOrBuilder getGpsOrBuilder();

        FUFileDesc getMgaonline();

        FUFileDescOrBuilder getMgaonlineOrBuilder();

        int getMtu();

        boolean hasFont();

        boolean hasGps();

        boolean hasMgaonline();

        boolean hasMtu();
    }

    public interface FUExitRequestOrBuilder extends MessageOrBuilder {
        FUType getFd();

        boolean hasFd();
    }

    public interface FUExitResponseOrBuilder extends MessageOrBuilder {
        FUFileDesc getDesc();

        FUFileDescOrBuilder getDescOrBuilder();

        FUType getFd();

        Status getStatus();

        boolean hasDesc();

        boolean hasFd();

        boolean hasStatus();
    }

    public interface FUFileDescOrBuilder extends MessageOrBuilder {
        FUFileInfo getInfo();

        FUFileInfoOrBuilder getInfoOrBuilder();

        int getMaxSize();

        boolean getValid();

        boolean hasInfo();

        boolean hasMaxSize();

        boolean hasValid();
    }

    public interface FUFileInfoOrBuilder extends MessageOrBuilder {
        int getCrc32AtOffset();

        FUType getFd();

        int getFileCrc32();

        String getFileName();

        ByteString getFileNameBytes();

        int getFileOffset();

        int getFileSize();

        boolean hasCrc32AtOffset();

        boolean hasFd();

        boolean hasFileCrc32();

        boolean hasFileName();

        boolean hasFileOffset();

        boolean hasFileSize();
    }

    public interface FUInitRequestOrBuilder extends MessageOrBuilder {
        FUFileInfo getInitInfo();

        FUFileInfoOrBuilder getInitInfoOrBuilder();

        boolean hasInitInfo();
    }

    public interface FUInitResponseOrBuilder extends MessageOrBuilder {
        FUType getFd();

        Status getStatus();

        boolean hasFd();

        boolean hasStatus();
    }

    public interface FilesUpdateRequestOrBuilder extends MessageOrBuilder {
        FUDataRequest getData();

        FUDataRequestOrBuilder getDataOrBuilder();

        boolean getDesc();

        FUExitRequest getExit();

        FUExitRequestOrBuilder getExitOrBuilder();

        FUInitRequest getInit();

        FUInitRequestOrBuilder getInitOrBuilder();

        ParamsCase getParamsCase();

        boolean hasData();

        boolean hasDesc();

        boolean hasExit();

        boolean hasInit();
    }

    public interface FilesUpdateResponseOrBuilder extends MessageOrBuilder {
        FUDataResponse getData();

        FUDataResponseOrBuilder getDataOrBuilder();

        FUDescResponse getDesc();

        FUDescResponseOrBuilder getDescOrBuilder();

        FUExitResponse getExit();

        FUExitResponseOrBuilder getExitOrBuilder();

        FUInitResponse getInit();

        FUInitResponseOrBuilder getInitOrBuilder();

        ParamsCase getParamsCase();

        boolean hasData();

        boolean hasDesc();

        boolean hasExit();

        boolean hasInit();
    }

    private FilesUpdate() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0012files_update.proto\"\u0001\n\u000eFUInitResponse\u0012\u0013\n\u0002fd\u0018\u0001 \u0002(\u000e2\u0007.FUType\u0012&\n\u0006status\u0018\u0002 \u0002(\u000e2\u0016.FUInitResponse.Status\"3\n\u0006Status\u0012\u0006\n\u0002OK\u0010\u0000\u0012\u0010\n\fERROR_PARAMS\u0010\u0001\u0012\u000f\n\u000bERROR_INNER\u0010\u0002\"å\u0001\n\u000eFUDataResponse\u0012\u0013\n\u0002fd\u0018\u0001 \u0002(\u000e2\u0007.FUType\u0012&\n\u0006status\u0018\u0002 \u0002(\u000e2\u0016.FUDataResponse.Status\u0012\u0013\n\u000bfile_offset\u0018\u0003 \u0002(\u0007\u0012\u0017\n\u000fcrc32_at_offset\u0018\u0004 \u0002(\u0007\"h\n\u0006Status\u0012\u0006\n\u0002OK\u0010\u0000\u0012\u0010\n\fERROR_PARAMS\u0010\u0001\u0012\u0019\n\u0015ERROR_OFFSET_MISMATCH\u0010\u0002\u0012\u0018\n\u0014ERROR_CRC32_MISMATCH\u0010\u0003\u0012\u000f\n\u000bERROR_INNER\u0010\u0004\"\u0001\n\nFUFileInfo", "\u0012\u0013\n\u0002fd\u0018\u0001 \u0002(\u000e2\u0007.FUType\u0012\u0011\n\tfile_name\u0018\u0002 \u0002(\t\u0012\u0011\n\tfile_size\u0018\u0003 \u0002(\u0007\u0012\u0012\n\nfile_crc32\u0018\u0004 \u0002(\u0007\u0012\u0013\n\u000bfile_offset\u0018\u0005 \u0002(\u0007\u0012\u0017\n\u000fcrc32_at_offset\u0018\u0006 \u0002(\u0007\"H\n\nFUFileDesc\u0012\u0010\n\bmax_size\u0018\u0001 \u0002(\u0007\u0012\r\n\u0005valid\u0018\u0002 \u0002(\b\u0012\u0019\n\u0004info\u0018\u0003 \u0002(\u000b2\u000b.FUFileInfo\"Î\u0001\n\u000eFUExitResponse\u0012\u0013\n\u0002fd\u0018\u0001 \u0002(\u000e2\u0007.FUType\u0012&\n\u0006status\u0018\u0002 \u0002(\u000e2\u0016.FUExitResponse.Status\u0012\u0019\n\u0004desc\u0018\u0003 \u0002(\u000b2\u000b.FUFileDesc\"d\n\u0006Status\u0012\u0006\n\u0002OK\u0010\u0000\u0012\u0010\n\fERROR_PARAMS\u0010\u0001\u0012\u0016\n\u0012ERROR_CRC_MISMATCH\u0010\u0002\u0012\u0017\n\u0013ERROR_SIZE_MISMATCH\u0010\u0003\u0012\u000f\n\u000bERROR", "_INNER\u0010\u0004\"r\n\u000eFUDescResponse\u0012\u000b\n\u0003mtu\u0018\u0001 \u0002(\u0007\u0012\u0018\n\u0003gps\u0018\u0002 \u0001(\u000b2\u000b.FUFileDesc\u0012\u0019\n\u0004font\u0018\u0003 \u0001(\u000b2\u000b.FUFileDesc\u0012\u001e\n\tmgaonline\u0018\u0004 \u0001(\u000b2\u000b.FUFileDesc\"£\u0001\n\u0013FilesUpdateResponse\u0012\u001f\n\u0004init\u0018\u0001 \u0001(\u000b2\u000f.FUInitResponseH\u0000\u0012\u001f\n\u0004data\u0018\u0002 \u0001(\u000b2\u000f.FUDataResponseH\u0000\u0012\u001f\n\u0004exit\u0018\u0003 \u0001(\u000b2\u000f.FUExitResponseH\u0000\u0012\u001f\n\u0004desc\u0018\u0004 \u0001(\u000b2\u000f.FUDescResponseH\u0000B\b\n\u0006params\"/\n\rFUInitRequest\u0012\u001e\n\tinit_info\u0018\u0001 \u0002(\u000b2\u000b.FUFileInfo\"_\n\rFUDataRequest\u0012\u0013\n\u0002fd\u0018\u0001 \u0002(\u000e2\u0007.FUType\u0012\u0013\n\u000bfile_offset\u0018\u0002 \u0002(\u0007\u0012\u0017", "\n\u000fcrc32_at_offset\u0018\u0003 \u0002(\u0007\u0012\u000b\n\u0003buf\u0018\u0004 \u0002(\f\"$\n\rFUExitRequest\u0012\u0013\n\u0002fd\u0018\u0001 \u0002(\u000e2\u0007.FUType\"\u0001\n\u0012FilesUpdateRequest\u0012\u001e\n\u0004init\u0018\u0001 \u0001(\u000b2\u000e.FUInitRequestH\u0000\u0012\u001e\n\u0004data\u0018\u0002 \u0001(\u000b2\u000e.FUDataRequestH\u0000\u0012\u001e\n\u0004exit\u0018\u0003 \u0001(\u000b2\u000e.FUExitRequestH\u0000\u0012\u000e\n\u0004desc\u0018\u0004 \u0001(\bH\u0000B\b\n\u0006params**\n\u0006FUType\u0012\u0007\n\u0003GPS\u0010\u0000\u0012\b\n\u0004FONT\u0010\u0001\u0012\r\n\tMGAONLINE\u0010\u0002"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                FilesUpdate.descriptor = root;
                return null;
            }
        });
    }
}
