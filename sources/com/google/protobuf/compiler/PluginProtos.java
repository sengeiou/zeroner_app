package com.google.protobuf.compiler;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder;
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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PluginProtos {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_google_protobuf_compiler_CodeGeneratorRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor, new String[]{"FileToGenerate", "Parameter", "ProtoFile", "CompilerVersion"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor = ((Descriptor) internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor.getNestedTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor, new String[]{"Name", "InsertionPoint", "Content"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_google_protobuf_compiler_CodeGeneratorResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor, new String[]{"Error", "File"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_google_protobuf_compiler_Version_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_google_protobuf_compiler_Version_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_compiler_Version_descriptor, new String[]{"Major", "Minor", "Patch", "Suffix"});

    public static final class CodeGeneratorRequest extends GeneratedMessageV3 implements CodeGeneratorRequestOrBuilder {
        public static final int COMPILER_VERSION_FIELD_NUMBER = 3;
        private static final CodeGeneratorRequest DEFAULT_INSTANCE = new CodeGeneratorRequest();
        public static final int FILE_TO_GENERATE_FIELD_NUMBER = 1;
        public static final int PARAMETER_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<CodeGeneratorRequest> PARSER = new AbstractParser<CodeGeneratorRequest>() {
            public CodeGeneratorRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CodeGeneratorRequest(input, extensionRegistry);
            }
        };
        public static final int PROTO_FILE_FIELD_NUMBER = 15;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public Version compilerVersion_;
        /* access modifiers changed from: private */
        public LazyStringList fileToGenerate_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public volatile Object parameter_;
        /* access modifiers changed from: private */
        public List<FileDescriptorProto> protoFile_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CodeGeneratorRequestOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<Version, Builder, VersionOrBuilder> compilerVersionBuilder_;
            private Version compilerVersion_;
            private LazyStringList fileToGenerate_;
            private Object parameter_;
            private RepeatedFieldBuilderV3<FileDescriptorProto, com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> protoFileBuilder_;
            private List<FileDescriptorProto> protoFile_;

            public static final Descriptor getDescriptor() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorRequest.class, Builder.class);
            }

            private Builder() {
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.parameter_ = "";
                this.protoFile_ = Collections.emptyList();
                this.compilerVersion_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.parameter_ = "";
                this.protoFile_ = Collections.emptyList();
                this.compilerVersion_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CodeGeneratorRequest.alwaysUseFieldBuilders) {
                    getProtoFileFieldBuilder();
                    getCompilerVersionFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -2;
                this.parameter_ = "";
                this.bitField0_ &= -3;
                if (this.protoFileBuilder_ == null) {
                    this.protoFile_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.protoFileBuilder_.clear();
                }
                if (this.compilerVersionBuilder_ == null) {
                    this.compilerVersion_ = null;
                } else {
                    this.compilerVersionBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor;
            }

            public CodeGeneratorRequest getDefaultInstanceForType() {
                return CodeGeneratorRequest.getDefaultInstance();
            }

            public CodeGeneratorRequest build() {
                CodeGeneratorRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CodeGeneratorRequest buildPartial() {
                CodeGeneratorRequest result = new CodeGeneratorRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.fileToGenerate_ = this.fileToGenerate_.getUnmodifiableView();
                    this.bitField0_ &= -2;
                }
                result.fileToGenerate_ = this.fileToGenerate_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ = 0 | 1;
                }
                result.parameter_ = this.parameter_;
                if (this.protoFileBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.protoFile_ = Collections.unmodifiableList(this.protoFile_);
                        this.bitField0_ &= -5;
                    }
                    result.protoFile_ = this.protoFile_;
                } else {
                    result.protoFile_ = this.protoFileBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 2;
                }
                if (this.compilerVersionBuilder_ == null) {
                    result.compilerVersion_ = this.compilerVersion_;
                } else {
                    result.compilerVersion_ = (Version) this.compilerVersionBuilder_.build();
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
                if (other instanceof CodeGeneratorRequest) {
                    return mergeFrom((CodeGeneratorRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CodeGeneratorRequest other) {
                RepeatedFieldBuilderV3<FileDescriptorProto, com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != CodeGeneratorRequest.getDefaultInstance()) {
                    if (!other.fileToGenerate_.isEmpty()) {
                        if (this.fileToGenerate_.isEmpty()) {
                            this.fileToGenerate_ = other.fileToGenerate_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureFileToGenerateIsMutable();
                            this.fileToGenerate_.addAll(other.fileToGenerate_);
                        }
                        onChanged();
                    }
                    if (other.hasParameter()) {
                        this.bitField0_ |= 2;
                        this.parameter_ = other.parameter_;
                        onChanged();
                    }
                    if (this.protoFileBuilder_ == null) {
                        if (!other.protoFile_.isEmpty()) {
                            if (this.protoFile_.isEmpty()) {
                                this.protoFile_ = other.protoFile_;
                                this.bitField0_ &= -5;
                            } else {
                                ensureProtoFileIsMutable();
                                this.protoFile_.addAll(other.protoFile_);
                            }
                            onChanged();
                        }
                    } else if (!other.protoFile_.isEmpty()) {
                        if (this.protoFileBuilder_.isEmpty()) {
                            this.protoFileBuilder_.dispose();
                            this.protoFileBuilder_ = null;
                            this.protoFile_ = other.protoFile_;
                            this.bitField0_ &= -5;
                            if (CodeGeneratorRequest.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getProtoFileFieldBuilder();
                            }
                            this.protoFileBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.protoFileBuilder_.addAllMessages(other.protoFile_);
                        }
                    }
                    if (other.hasCompilerVersion()) {
                        mergeCompilerVersion(other.getCompilerVersion());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getProtoFileCount(); i++) {
                    if (!getProtoFile(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                CodeGeneratorRequest parsedMessage = null;
                try {
                    CodeGeneratorRequest parsedMessage2 = (CodeGeneratorRequest) CodeGeneratorRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CodeGeneratorRequest parsedMessage3 = (CodeGeneratorRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            private void ensureFileToGenerateIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.fileToGenerate_ = new LazyStringArrayList(this.fileToGenerate_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getFileToGenerateList() {
                return this.fileToGenerate_.getUnmodifiableView();
            }

            public int getFileToGenerateCount() {
                return this.fileToGenerate_.size();
            }

            public String getFileToGenerate(int index) {
                return (String) this.fileToGenerate_.get(index);
            }

            public ByteString getFileToGenerateBytes(int index) {
                return this.fileToGenerate_.getByteString(index);
            }

            public Builder setFileToGenerate(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureFileToGenerateIsMutable();
                this.fileToGenerate_.set(index, value);
                onChanged();
                return this;
            }

            public Builder addFileToGenerate(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureFileToGenerateIsMutable();
                this.fileToGenerate_.add(value);
                onChanged();
                return this;
            }

            public Builder addAllFileToGenerate(Iterable<String> values) {
                ensureFileToGenerateIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, (List<? super T>) this.fileToGenerate_);
                onChanged();
                return this;
            }

            public Builder clearFileToGenerate() {
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -2;
                onChanged();
                return this;
            }

            public Builder addFileToGenerateBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureFileToGenerateIsMutable();
                this.fileToGenerate_.add(value);
                onChanged();
                return this;
            }

            public boolean hasParameter() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getParameter() {
                Object ref = this.parameter_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.parameter_ = s;
                return s;
            }

            public ByteString getParameterBytes() {
                Object ref = this.parameter_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.parameter_ = b;
                return b;
            }

            public Builder setParameter(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.parameter_ = value;
                onChanged();
                return this;
            }

            public Builder clearParameter() {
                this.bitField0_ &= -3;
                this.parameter_ = CodeGeneratorRequest.getDefaultInstance().getParameter();
                onChanged();
                return this;
            }

            public Builder setParameterBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.parameter_ = value;
                onChanged();
                return this;
            }

            private void ensureProtoFileIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.protoFile_ = new ArrayList(this.protoFile_);
                    this.bitField0_ |= 4;
                }
            }

            public List<FileDescriptorProto> getProtoFileList() {
                if (this.protoFileBuilder_ == null) {
                    return Collections.unmodifiableList(this.protoFile_);
                }
                return this.protoFileBuilder_.getMessageList();
            }

            public int getProtoFileCount() {
                if (this.protoFileBuilder_ == null) {
                    return this.protoFile_.size();
                }
                return this.protoFileBuilder_.getCount();
            }

            public FileDescriptorProto getProtoFile(int index) {
                if (this.protoFileBuilder_ == null) {
                    return (FileDescriptorProto) this.protoFile_.get(index);
                }
                return (FileDescriptorProto) this.protoFileBuilder_.getMessage(index);
            }

            public Builder setProtoFile(int index, FileDescriptorProto value) {
                if (this.protoFileBuilder_ != null) {
                    this.protoFileBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureProtoFileIsMutable();
                    this.protoFile_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setProtoFile(int index, com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder builderForValue) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.protoFileBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addProtoFile(FileDescriptorProto value) {
                if (this.protoFileBuilder_ != null) {
                    this.protoFileBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addProtoFile(int index, FileDescriptorProto value) {
                if (this.protoFileBuilder_ != null) {
                    this.protoFileBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addProtoFile(com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder builderForValue) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.protoFileBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addProtoFile(int index, com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder builderForValue) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.protoFileBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllProtoFile(Iterable<? extends FileDescriptorProto> values) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.protoFile_);
                    onChanged();
                } else {
                    this.protoFileBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearProtoFile() {
                if (this.protoFileBuilder_ == null) {
                    this.protoFile_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.protoFileBuilder_.clear();
                }
                return this;
            }

            public Builder removeProtoFile(int index) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.remove(index);
                    onChanged();
                } else {
                    this.protoFileBuilder_.remove(index);
                }
                return this;
            }

            public com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder getProtoFileBuilder(int index) {
                return (com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder) getProtoFileFieldBuilder().getBuilder(index);
            }

            public FileDescriptorProtoOrBuilder getProtoFileOrBuilder(int index) {
                if (this.protoFileBuilder_ == null) {
                    return (FileDescriptorProtoOrBuilder) this.protoFile_.get(index);
                }
                return (FileDescriptorProtoOrBuilder) this.protoFileBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FileDescriptorProtoOrBuilder> getProtoFileOrBuilderList() {
                if (this.protoFileBuilder_ != null) {
                    return this.protoFileBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.protoFile_);
            }

            public com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder addProtoFileBuilder() {
                return (com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder) getProtoFileFieldBuilder().addBuilder(FileDescriptorProto.getDefaultInstance());
            }

            public com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder addProtoFileBuilder(int index) {
                return (com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder) getProtoFileFieldBuilder().addBuilder(index, FileDescriptorProto.getDefaultInstance());
            }

            public List<com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder> getProtoFileBuilderList() {
                return getProtoFileFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<FileDescriptorProto, com.google.protobuf.DescriptorProtos.FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> getProtoFileFieldBuilder() {
                if (this.protoFileBuilder_ == null) {
                    this.protoFileBuilder_ = new RepeatedFieldBuilderV3<>(this.protoFile_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.protoFile_ = null;
                }
                return this.protoFileBuilder_;
            }

            public boolean hasCompilerVersion() {
                return (this.bitField0_ & 8) == 8;
            }

            public Version getCompilerVersion() {
                if (this.compilerVersionBuilder_ == null) {
                    return this.compilerVersion_ == null ? Version.getDefaultInstance() : this.compilerVersion_;
                }
                return (Version) this.compilerVersionBuilder_.getMessage();
            }

            public Builder setCompilerVersion(Version value) {
                if (this.compilerVersionBuilder_ != null) {
                    this.compilerVersionBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.compilerVersion_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setCompilerVersion(Builder builderForValue) {
                if (this.compilerVersionBuilder_ == null) {
                    this.compilerVersion_ = builderForValue.build();
                    onChanged();
                } else {
                    this.compilerVersionBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeCompilerVersion(Version value) {
                if (this.compilerVersionBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.compilerVersion_ == null || this.compilerVersion_ == Version.getDefaultInstance()) {
                        this.compilerVersion_ = value;
                    } else {
                        this.compilerVersion_ = Version.newBuilder(this.compilerVersion_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.compilerVersionBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearCompilerVersion() {
                if (this.compilerVersionBuilder_ == null) {
                    this.compilerVersion_ = null;
                    onChanged();
                } else {
                    this.compilerVersionBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getCompilerVersionBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getCompilerVersionFieldBuilder().getBuilder();
            }

            public VersionOrBuilder getCompilerVersionOrBuilder() {
                if (this.compilerVersionBuilder_ != null) {
                    return (VersionOrBuilder) this.compilerVersionBuilder_.getMessageOrBuilder();
                }
                return this.compilerVersion_ == null ? Version.getDefaultInstance() : this.compilerVersion_;
            }

            private SingleFieldBuilderV3<Version, Builder, VersionOrBuilder> getCompilerVersionFieldBuilder() {
                if (this.compilerVersionBuilder_ == null) {
                    this.compilerVersionBuilder_ = new SingleFieldBuilderV3<>(getCompilerVersion(), getParentForChildren(), isClean());
                    this.compilerVersion_ = null;
                }
                return this.compilerVersionBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private CodeGeneratorRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CodeGeneratorRequest() {
            this.memoizedIsInitialized = -1;
            this.fileToGenerate_ = LazyStringArrayList.EMPTY;
            this.parameter_ = "";
            this.protoFile_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CodeGeneratorRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
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
                            ByteString bs = input.readBytes();
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.fileToGenerate_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.fileToGenerate_.add(bs);
                            break;
                        case 18:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 1;
                            this.parameter_ = bs2;
                            break;
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.compilerVersion_.toBuilder();
                            }
                            this.compilerVersion_ = (Version) input.readMessage(Version.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.compilerVersion_);
                                this.compilerVersion_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 122:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.protoFile_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.protoFile_.add(input.readMessage(FileDescriptorProto.PARSER, extensionRegistry));
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
                    if ((mutable_bitField0_ & 1) == 1) {
                        this.fileToGenerate_ = this.fileToGenerate_.getUnmodifiableView();
                    }
                    if ((mutable_bitField0_ & 4) == 4) {
                        this.protoFile_ = Collections.unmodifiableList(this.protoFile_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.fileToGenerate_ = this.fileToGenerate_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.protoFile_ = Collections.unmodifiableList(this.protoFile_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorRequest.class, Builder.class);
        }

        public ProtocolStringList getFileToGenerateList() {
            return this.fileToGenerate_;
        }

        public int getFileToGenerateCount() {
            return this.fileToGenerate_.size();
        }

        public String getFileToGenerate(int index) {
            return (String) this.fileToGenerate_.get(index);
        }

        public ByteString getFileToGenerateBytes(int index) {
            return this.fileToGenerate_.getByteString(index);
        }

        public boolean hasParameter() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getParameter() {
            Object ref = this.parameter_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.parameter_ = s;
            }
            return s;
        }

        public ByteString getParameterBytes() {
            Object ref = this.parameter_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.parameter_ = b;
            return b;
        }

        public List<FileDescriptorProto> getProtoFileList() {
            return this.protoFile_;
        }

        public List<? extends FileDescriptorProtoOrBuilder> getProtoFileOrBuilderList() {
            return this.protoFile_;
        }

        public int getProtoFileCount() {
            return this.protoFile_.size();
        }

        public FileDescriptorProto getProtoFile(int index) {
            return (FileDescriptorProto) this.protoFile_.get(index);
        }

        public FileDescriptorProtoOrBuilder getProtoFileOrBuilder(int index) {
            return (FileDescriptorProtoOrBuilder) this.protoFile_.get(index);
        }

        public boolean hasCompilerVersion() {
            return (this.bitField0_ & 2) == 2;
        }

        public Version getCompilerVersion() {
            return this.compilerVersion_ == null ? Version.getDefaultInstance() : this.compilerVersion_;
        }

        public VersionOrBuilder getCompilerVersionOrBuilder() {
            return this.compilerVersion_ == null ? Version.getDefaultInstance() : this.compilerVersion_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < getProtoFileCount(); i++) {
                if (!getProtoFile(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.fileToGenerate_.size(); i++) {
                GeneratedMessageV3.writeString(output, 1, this.fileToGenerate_.getRaw(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 2, this.parameter_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(3, getCompilerVersion());
            }
            for (int i2 = 0; i2 < this.protoFile_.size(); i2++) {
                output.writeMessage(15, (MessageLite) this.protoFile_.get(i2));
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int dataSize = 0;
            for (int i = 0; i < this.fileToGenerate_.size(); i++) {
                dataSize += computeStringSizeNoTag(this.fileToGenerate_.getRaw(i));
            }
            int size2 = 0 + dataSize + (getFileToGenerateList().size() * 1);
            if ((this.bitField0_ & 1) == 1) {
                size2 += GeneratedMessageV3.computeStringSize(2, this.parameter_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeMessageSize(3, getCompilerVersion());
            }
            for (int i2 = 0; i2 < this.protoFile_.size(); i2++) {
                size2 += CodedOutputStream.computeMessageSize(15, (MessageLite) this.protoFile_.get(i2));
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
            if (!(obj instanceof CodeGeneratorRequest)) {
                return super.equals(obj);
            }
            CodeGeneratorRequest other = (CodeGeneratorRequest) obj;
            if (!(1 != 0 && getFileToGenerateList().equals(other.getFileToGenerateList())) || hasParameter() != other.hasParameter()) {
                result = false;
            } else {
                result = true;
            }
            if (hasParameter()) {
                if (!result || !getParameter().equals(other.getParameter())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || !getProtoFileList().equals(other.getProtoFileList())) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (!result2 || hasCompilerVersion() != other.hasCompilerVersion()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasCompilerVersion()) {
                if (!result3 || !getCompilerVersion().equals(other.getCompilerVersion())) {
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
            int hash = getDescriptor().hashCode() + 779;
            if (getFileToGenerateCount() > 0) {
                hash = (((hash * 37) + 1) * 53) + getFileToGenerateList().hashCode();
            }
            if (hasParameter()) {
                hash = (((hash * 37) + 2) * 53) + getParameter().hashCode();
            }
            if (getProtoFileCount() > 0) {
                hash = (((hash * 37) + 15) * 53) + getProtoFileList().hashCode();
            }
            if (hasCompilerVersion()) {
                hash = (((hash * 37) + 3) * 53) + getCompilerVersion().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CodeGeneratorRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CodeGeneratorRequest) PARSER.parseFrom(data);
        }

        public static CodeGeneratorRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CodeGeneratorRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CodeGeneratorRequest) PARSER.parseFrom(data);
        }

        public static CodeGeneratorRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CodeGeneratorRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CodeGeneratorRequest) PARSER.parseFrom(data);
        }

        public static CodeGeneratorRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CodeGeneratorRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(InputStream input) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CodeGeneratorRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(CodedInputStream input) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CodeGeneratorRequest prototype) {
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

        public static CodeGeneratorRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CodeGeneratorRequest> parser() {
            return PARSER;
        }

        public Parser<CodeGeneratorRequest> getParserForType() {
            return PARSER;
        }

        public CodeGeneratorRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class CodeGeneratorResponse extends GeneratedMessageV3 implements CodeGeneratorResponseOrBuilder {
        private static final CodeGeneratorResponse DEFAULT_INSTANCE = new CodeGeneratorResponse();
        public static final int ERROR_FIELD_NUMBER = 1;
        public static final int FILE_FIELD_NUMBER = 15;
        @Deprecated
        public static final Parser<CodeGeneratorResponse> PARSER = new AbstractParser<CodeGeneratorResponse>() {
            public CodeGeneratorResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CodeGeneratorResponse(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public volatile Object error_;
        /* access modifiers changed from: private */
        public List<File> file_;
        private byte memoizedIsInitialized;

        public static final class File extends GeneratedMessageV3 implements FileOrBuilder {
            public static final int CONTENT_FIELD_NUMBER = 15;
            private static final File DEFAULT_INSTANCE = new File();
            public static final int INSERTION_POINT_FIELD_NUMBER = 2;
            public static final int NAME_FIELD_NUMBER = 1;
            @Deprecated
            public static final Parser<File> PARSER = new AbstractParser<File>() {
                public File parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new File(input, extensionRegistry);
                }
            };
            private static final long serialVersionUID = 0;
            /* access modifiers changed from: private */
            public int bitField0_;
            /* access modifiers changed from: private */
            public volatile Object content_;
            /* access modifiers changed from: private */
            public volatile Object insertionPoint_;
            private byte memoizedIsInitialized;
            /* access modifiers changed from: private */
            public volatile Object name_;

            public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FileOrBuilder {
                private int bitField0_;
                private Object content_;
                private Object insertionPoint_;
                private Object name_;

                public static final Descriptor getDescriptor() {
                    return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor;
                }

                /* access modifiers changed from: protected */
                public FieldAccessorTable internalGetFieldAccessorTable() {
                    return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_fieldAccessorTable.ensureFieldAccessorsInitialized(File.class, Builder.class);
                }

                private Builder() {
                    this.name_ = "";
                    this.insertionPoint_ = "";
                    this.content_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    this.name_ = "";
                    this.insertionPoint_ = "";
                    this.content_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (File.alwaysUseFieldBuilders) {
                    }
                }

                public Builder clear() {
                    super.clear();
                    this.name_ = "";
                    this.bitField0_ &= -2;
                    this.insertionPoint_ = "";
                    this.bitField0_ &= -3;
                    this.content_ = "";
                    this.bitField0_ &= -5;
                    return this;
                }

                public Descriptor getDescriptorForType() {
                    return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor;
                }

                public File getDefaultInstanceForType() {
                    return File.getDefaultInstance();
                }

                public File build() {
                    File result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw newUninitializedMessageException(result);
                }

                public File buildPartial() {
                    File result = new File((com.google.protobuf.GeneratedMessageV3.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.name_ = this.name_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result.insertionPoint_ = this.insertionPoint_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    result.content_ = this.content_;
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
                    if (other instanceof File) {
                        return mergeFrom((File) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(File other) {
                    if (other != File.getDefaultInstance()) {
                        if (other.hasName()) {
                            this.bitField0_ |= 1;
                            this.name_ = other.name_;
                            onChanged();
                        }
                        if (other.hasInsertionPoint()) {
                            this.bitField0_ |= 2;
                            this.insertionPoint_ = other.insertionPoint_;
                            onChanged();
                        }
                        if (other.hasContent()) {
                            this.bitField0_ |= 4;
                            this.content_ = other.content_;
                            onChanged();
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
                    File parsedMessage = null;
                    try {
                        File parsedMessage2 = (File) File.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage2 != null) {
                            mergeFrom(parsedMessage2);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        File parsedMessage3 = (File) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        throw th;
                    }
                }

                public boolean hasName() {
                    return (this.bitField0_ & 1) == 1;
                }

                public String getName() {
                    Object ref = this.name_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.name_ = s;
                    return s;
                }

                public ByteString getNameBytes() {
                    Object ref = this.name_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.name_ = b;
                    return b;
                }

                public Builder setName(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.name_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearName() {
                    this.bitField0_ &= -2;
                    this.name_ = File.getDefaultInstance().getName();
                    onChanged();
                    return this;
                }

                public Builder setNameBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.name_ = value;
                    onChanged();
                    return this;
                }

                public boolean hasInsertionPoint() {
                    return (this.bitField0_ & 2) == 2;
                }

                public String getInsertionPoint() {
                    Object ref = this.insertionPoint_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.insertionPoint_ = s;
                    return s;
                }

                public ByteString getInsertionPointBytes() {
                    Object ref = this.insertionPoint_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.insertionPoint_ = b;
                    return b;
                }

                public Builder setInsertionPoint(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 2;
                    this.insertionPoint_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearInsertionPoint() {
                    this.bitField0_ &= -3;
                    this.insertionPoint_ = File.getDefaultInstance().getInsertionPoint();
                    onChanged();
                    return this;
                }

                public Builder setInsertionPointBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 2;
                    this.insertionPoint_ = value;
                    onChanged();
                    return this;
                }

                public boolean hasContent() {
                    return (this.bitField0_ & 4) == 4;
                }

                public String getContent() {
                    Object ref = this.content_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.content_ = s;
                    return s;
                }

                public ByteString getContentBytes() {
                    Object ref = this.content_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.content_ = b;
                    return b;
                }

                public Builder setContent(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 4;
                    this.content_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearContent() {
                    this.bitField0_ &= -5;
                    this.content_ = File.getDefaultInstance().getContent();
                    onChanged();
                    return this;
                }

                public Builder setContentBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 4;
                    this.content_ = value;
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

            private File(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }

            private File() {
                this.memoizedIsInitialized = -1;
                this.name_ = "";
                this.insertionPoint_ = "";
                this.content_ = "";
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private File(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
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
                                this.name_ = bs;
                                break;
                            case 18:
                                ByteString bs2 = input.readBytes();
                                this.bitField0_ |= 2;
                                this.insertionPoint_ = bs2;
                                break;
                            case 122:
                                ByteString bs3 = input.readBytes();
                                this.bitField0_ |= 4;
                                this.content_ = bs3;
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
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_fieldAccessorTable.ensureFieldAccessorsInitialized(File.class, Builder.class);
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                Object ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.name_ = s;
                }
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public boolean hasInsertionPoint() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getInsertionPoint() {
                Object ref = this.insertionPoint_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.insertionPoint_ = s;
                }
                return s;
            }

            public ByteString getInsertionPointBytes() {
                Object ref = this.insertionPoint_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.insertionPoint_ = b;
                return b;
            }

            public boolean hasContent() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getContent() {
                Object ref = this.content_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.content_ = s;
                }
                return s;
            }

            public ByteString getContentBytes() {
                Object ref = this.content_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.content_ = b;
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
                this.memoizedIsInitialized = 1;
                return true;
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 1) == 1) {
                    GeneratedMessageV3.writeString(output, 1, this.name_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    GeneratedMessageV3.writeString(output, 2, this.insertionPoint_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    GeneratedMessageV3.writeString(output, 15, this.content_);
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
                    size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.name_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size2 += GeneratedMessageV3.computeStringSize(2, this.insertionPoint_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size2 += GeneratedMessageV3.computeStringSize(15, this.content_);
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
                if (!(obj instanceof File)) {
                    return super.equals(obj);
                }
                File other = (File) obj;
                boolean result4 = 1 != 0 && hasName() == other.hasName();
                if (hasName()) {
                    result4 = result4 && getName().equals(other.getName());
                }
                if (!result4 || hasInsertionPoint() != other.hasInsertionPoint()) {
                    result = false;
                } else {
                    result = true;
                }
                if (hasInsertionPoint()) {
                    if (!result || !getInsertionPoint().equals(other.getInsertionPoint())) {
                        result = false;
                    } else {
                        result = true;
                    }
                }
                if (!result || hasContent() != other.hasContent()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
                if (hasContent()) {
                    if (!result2 || !getContent().equals(other.getContent())) {
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
                int hash = getDescriptor().hashCode() + 779;
                if (hasName()) {
                    hash = (((hash * 37) + 1) * 53) + getName().hashCode();
                }
                if (hasInsertionPoint()) {
                    hash = (((hash * 37) + 2) * 53) + getInsertionPoint().hashCode();
                }
                if (hasContent()) {
                    hash = (((hash * 37) + 15) * 53) + getContent().hashCode();
                }
                int hash2 = (hash * 29) + this.unknownFields.hashCode();
                this.memoizedHashCode = hash2;
                return hash2;
            }

            public static File parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (File) PARSER.parseFrom(data);
            }

            public static File parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (File) PARSER.parseFrom(data, extensionRegistry);
            }

            public static File parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (File) PARSER.parseFrom(data);
            }

            public static File parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (File) PARSER.parseFrom(data, extensionRegistry);
            }

            public static File parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (File) PARSER.parseFrom(data);
            }

            public static File parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (File) PARSER.parseFrom(data, extensionRegistry);
            }

            public static File parseFrom(InputStream input) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static File parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static File parseDelimitedFrom(InputStream input) throws IOException {
                return (File) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static File parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (File) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static File parseFrom(CodedInputStream input) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static File parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(File prototype) {
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

            public static File getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<File> parser() {
                return PARSER;
            }

            public Parser<File> getParserForType() {
                return PARSER;
            }

            public File getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements CodeGeneratorResponseOrBuilder {
            private int bitField0_;
            private Object error_;
            private RepeatedFieldBuilderV3<File, Builder, FileOrBuilder> fileBuilder_;
            private List<File> file_;

            public static final Descriptor getDescriptor() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorResponse.class, Builder.class);
            }

            private Builder() {
                this.error_ = "";
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.error_ = "";
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CodeGeneratorResponse.alwaysUseFieldBuilders) {
                    getFileFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.error_ = "";
                this.bitField0_ &= -2;
                if (this.fileBuilder_ == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.fileBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor;
            }

            public CodeGeneratorResponse getDefaultInstanceForType() {
                return CodeGeneratorResponse.getDefaultInstance();
            }

            public CodeGeneratorResponse build() {
                CodeGeneratorResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CodeGeneratorResponse buildPartial() {
                CodeGeneratorResponse result = new CodeGeneratorResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.error_ = this.error_;
                if (this.fileBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                        this.bitField0_ &= -3;
                    }
                    result.file_ = this.file_;
                } else {
                    result.file_ = this.fileBuilder_.build();
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
                if (other instanceof CodeGeneratorResponse) {
                    return mergeFrom((CodeGeneratorResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CodeGeneratorResponse other) {
                RepeatedFieldBuilderV3<File, Builder, FileOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != CodeGeneratorResponse.getDefaultInstance()) {
                    if (other.hasError()) {
                        this.bitField0_ |= 1;
                        this.error_ = other.error_;
                        onChanged();
                    }
                    if (this.fileBuilder_ == null) {
                        if (!other.file_.isEmpty()) {
                            if (this.file_.isEmpty()) {
                                this.file_ = other.file_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureFileIsMutable();
                                this.file_.addAll(other.file_);
                            }
                            onChanged();
                        }
                    } else if (!other.file_.isEmpty()) {
                        if (this.fileBuilder_.isEmpty()) {
                            this.fileBuilder_.dispose();
                            this.fileBuilder_ = null;
                            this.file_ = other.file_;
                            this.bitField0_ &= -3;
                            if (CodeGeneratorResponse.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getFileFieldBuilder();
                            }
                            this.fileBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.fileBuilder_.addAllMessages(other.file_);
                        }
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
                CodeGeneratorResponse parsedMessage = null;
                try {
                    CodeGeneratorResponse parsedMessage2 = (CodeGeneratorResponse) CodeGeneratorResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CodeGeneratorResponse parsedMessage3 = (CodeGeneratorResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasError() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getError() {
                Object ref = this.error_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.error_ = s;
                return s;
            }

            public ByteString getErrorBytes() {
                Object ref = this.error_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.error_ = b;
                return b;
            }

            public Builder setError(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.error_ = value;
                onChanged();
                return this;
            }

            public Builder clearError() {
                this.bitField0_ &= -2;
                this.error_ = CodeGeneratorResponse.getDefaultInstance().getError();
                onChanged();
                return this;
            }

            public Builder setErrorBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.error_ = value;
                onChanged();
                return this;
            }

            private void ensureFileIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.file_ = new ArrayList(this.file_);
                    this.bitField0_ |= 2;
                }
            }

            public List<File> getFileList() {
                if (this.fileBuilder_ == null) {
                    return Collections.unmodifiableList(this.file_);
                }
                return this.fileBuilder_.getMessageList();
            }

            public int getFileCount() {
                if (this.fileBuilder_ == null) {
                    return this.file_.size();
                }
                return this.fileBuilder_.getCount();
            }

            public File getFile(int index) {
                if (this.fileBuilder_ == null) {
                    return (File) this.file_.get(index);
                }
                return (File) this.fileBuilder_.getMessage(index);
            }

            public Builder setFile(int index, File value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFileIsMutable();
                    this.file_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setFile(int index, Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addFile(File value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFileIsMutable();
                    this.file_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addFile(int index, File value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFileIsMutable();
                    this.file_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addFile(Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addFile(int index, Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllFile(Iterable<? extends File> values) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.file_);
                    onChanged();
                } else {
                    this.fileBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearFile() {
                if (this.fileBuilder_ == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.fileBuilder_.clear();
                }
                return this;
            }

            public Builder removeFile(int index) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.remove(index);
                    onChanged();
                } else {
                    this.fileBuilder_.remove(index);
                }
                return this;
            }

            public Builder getFileBuilder(int index) {
                return (Builder) getFileFieldBuilder().getBuilder(index);
            }

            public FileOrBuilder getFileOrBuilder(int index) {
                if (this.fileBuilder_ == null) {
                    return (FileOrBuilder) this.file_.get(index);
                }
                return (FileOrBuilder) this.fileBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FileOrBuilder> getFileOrBuilderList() {
                if (this.fileBuilder_ != null) {
                    return this.fileBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.file_);
            }

            public Builder addFileBuilder() {
                return (Builder) getFileFieldBuilder().addBuilder(File.getDefaultInstance());
            }

            public Builder addFileBuilder(int index) {
                return (Builder) getFileFieldBuilder().addBuilder(index, File.getDefaultInstance());
            }

            public List<Builder> getFileBuilderList() {
                return getFileFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<File, Builder, FileOrBuilder> getFileFieldBuilder() {
                if (this.fileBuilder_ == null) {
                    this.fileBuilder_ = new RepeatedFieldBuilderV3<>(this.file_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.file_ = null;
                }
                return this.fileBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public interface FileOrBuilder extends MessageOrBuilder {
            String getContent();

            ByteString getContentBytes();

            String getInsertionPoint();

            ByteString getInsertionPointBytes();

            String getName();

            ByteString getNameBytes();

            boolean hasContent();

            boolean hasInsertionPoint();

            boolean hasName();
        }

        private CodeGeneratorResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CodeGeneratorResponse() {
            this.memoizedIsInitialized = -1;
            this.error_ = "";
            this.file_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CodeGeneratorResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
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
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.error_ = bs;
                            break;
                        case 122:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.file_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.file_.add(input.readMessage(File.PARSER, extensionRegistry));
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
                        this.file_ = Collections.unmodifiableList(this.file_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.file_ = Collections.unmodifiableList(this.file_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorResponse.class, Builder.class);
        }

        public boolean hasError() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getError() {
            Object ref = this.error_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.error_ = s;
            }
            return s;
        }

        public ByteString getErrorBytes() {
            Object ref = this.error_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.error_ = b;
            return b;
        }

        public List<File> getFileList() {
            return this.file_;
        }

        public List<? extends FileOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        public int getFileCount() {
            return this.file_.size();
        }

        public File getFile(int index) {
            return (File) this.file_.get(index);
        }

        public FileOrBuilder getFileOrBuilder(int index) {
            return (FileOrBuilder) this.file_.get(index);
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
                GeneratedMessageV3.writeString(output, 1, this.error_);
            }
            for (int i = 0; i < this.file_.size(); i++) {
                output.writeMessage(15, (MessageLite) this.file_.get(i));
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
                size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.error_);
            }
            for (int i = 0; i < this.file_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(15, (MessageLite) this.file_.get(i));
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
            if (!(obj instanceof CodeGeneratorResponse)) {
                return super.equals(obj);
            }
            CodeGeneratorResponse other = (CodeGeneratorResponse) obj;
            boolean result3 = 1 != 0 && hasError() == other.hasError();
            if (hasError()) {
                result3 = result3 && getError().equals(other.getError());
            }
            if (!result3 || !getFileList().equals(other.getFileList())) {
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
            int hash = getDescriptor().hashCode() + 779;
            if (hasError()) {
                hash = (((hash * 37) + 1) * 53) + getError().hashCode();
            }
            if (getFileCount() > 0) {
                hash = (((hash * 37) + 15) * 53) + getFileList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CodeGeneratorResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CodeGeneratorResponse) PARSER.parseFrom(data);
        }

        public static CodeGeneratorResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CodeGeneratorResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CodeGeneratorResponse) PARSER.parseFrom(data);
        }

        public static CodeGeneratorResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CodeGeneratorResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CodeGeneratorResponse) PARSER.parseFrom(data);
        }

        public static CodeGeneratorResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CodeGeneratorResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(InputStream input) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CodeGeneratorResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(CodedInputStream input) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CodeGeneratorResponse prototype) {
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

        public static CodeGeneratorResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CodeGeneratorResponse> parser() {
            return PARSER;
        }

        public Parser<CodeGeneratorResponse> getParserForType() {
            return PARSER;
        }

        public CodeGeneratorResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class Version extends GeneratedMessageV3 implements VersionOrBuilder {
        private static final Version DEFAULT_INSTANCE = new Version();
        public static final int MAJOR_FIELD_NUMBER = 1;
        public static final int MINOR_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<Version> PARSER = new AbstractParser<Version>() {
            public Version parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Version(input, extensionRegistry);
            }
        };
        public static final int PATCH_FIELD_NUMBER = 3;
        public static final int SUFFIX_FIELD_NUMBER = 4;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int major_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int minor_;
        /* access modifiers changed from: private */
        public int patch_;
        /* access modifiers changed from: private */
        public volatile Object suffix_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements VersionOrBuilder {
            private int bitField0_;
            private int major_;
            private int minor_;
            private int patch_;
            private Object suffix_;

            public static final Descriptor getDescriptor() {
                return PluginProtos.internal_static_google_protobuf_compiler_Version_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_Version_fieldAccessorTable.ensureFieldAccessorsInitialized(Version.class, Builder.class);
            }

            private Builder() {
                this.suffix_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.suffix_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (Version.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.major_ = 0;
                this.bitField0_ &= -2;
                this.minor_ = 0;
                this.bitField0_ &= -3;
                this.patch_ = 0;
                this.bitField0_ &= -5;
                this.suffix_ = "";
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PluginProtos.internal_static_google_protobuf_compiler_Version_descriptor;
            }

            public Version getDefaultInstanceForType() {
                return Version.getDefaultInstance();
            }

            public Version build() {
                Version result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public Version buildPartial() {
                Version result = new Version((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.major_ = this.major_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.minor_ = this.minor_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.patch_ = this.patch_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.suffix_ = this.suffix_;
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
                if (other instanceof Version) {
                    return mergeFrom((Version) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Version other) {
                if (other != Version.getDefaultInstance()) {
                    if (other.hasMajor()) {
                        setMajor(other.getMajor());
                    }
                    if (other.hasMinor()) {
                        setMinor(other.getMinor());
                    }
                    if (other.hasPatch()) {
                        setPatch(other.getPatch());
                    }
                    if (other.hasSuffix()) {
                        this.bitField0_ |= 8;
                        this.suffix_ = other.suffix_;
                        onChanged();
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
                Version parsedMessage = null;
                try {
                    Version parsedMessage2 = (Version) Version.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    Version parsedMessage3 = (Version) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasMajor() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getMajor() {
                return this.major_;
            }

            public Builder setMajor(int value) {
                this.bitField0_ |= 1;
                this.major_ = value;
                onChanged();
                return this;
            }

            public Builder clearMajor() {
                this.bitField0_ &= -2;
                this.major_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMinor() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getMinor() {
                return this.minor_;
            }

            public Builder setMinor(int value) {
                this.bitField0_ |= 2;
                this.minor_ = value;
                onChanged();
                return this;
            }

            public Builder clearMinor() {
                this.bitField0_ &= -3;
                this.minor_ = 0;
                onChanged();
                return this;
            }

            public boolean hasPatch() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getPatch() {
                return this.patch_;
            }

            public Builder setPatch(int value) {
                this.bitField0_ |= 4;
                this.patch_ = value;
                onChanged();
                return this;
            }

            public Builder clearPatch() {
                this.bitField0_ &= -5;
                this.patch_ = 0;
                onChanged();
                return this;
            }

            public boolean hasSuffix() {
                return (this.bitField0_ & 8) == 8;
            }

            public String getSuffix() {
                Object ref = this.suffix_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.suffix_ = s;
                return s;
            }

            public ByteString getSuffixBytes() {
                Object ref = this.suffix_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.suffix_ = b;
                return b;
            }

            public Builder setSuffix(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.suffix_ = value;
                onChanged();
                return this;
            }

            public Builder clearSuffix() {
                this.bitField0_ &= -9;
                this.suffix_ = Version.getDefaultInstance().getSuffix();
                onChanged();
                return this;
            }

            public Builder setSuffixBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.suffix_ = value;
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

        private Version(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private Version() {
            this.memoizedIsInitialized = -1;
            this.major_ = 0;
            this.minor_ = 0;
            this.patch_ = 0;
            this.suffix_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Version(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
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
                            this.major_ = input.readInt32();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.minor_ = input.readInt32();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.patch_ = input.readInt32();
                            break;
                        case 34:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 8;
                            this.suffix_ = bs;
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
            return PluginProtos.internal_static_google_protobuf_compiler_Version_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PluginProtos.internal_static_google_protobuf_compiler_Version_fieldAccessorTable.ensureFieldAccessorsInitialized(Version.class, Builder.class);
        }

        public boolean hasMajor() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMajor() {
            return this.major_;
        }

        public boolean hasMinor() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getMinor() {
            return this.minor_;
        }

        public boolean hasPatch() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getPatch() {
            return this.patch_;
        }

        public boolean hasSuffix() {
            return (this.bitField0_ & 8) == 8;
        }

        public String getSuffix() {
            Object ref = this.suffix_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.suffix_ = s;
            }
            return s;
        }

        public ByteString getSuffixBytes() {
            Object ref = this.suffix_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.suffix_ = b;
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
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.major_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.minor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeInt32(3, this.patch_);
            }
            if ((this.bitField0_ & 8) == 8) {
                GeneratedMessageV3.writeString(output, 4, this.suffix_);
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
                size2 = 0 + CodedOutputStream.computeInt32Size(1, this.major_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeInt32Size(2, this.minor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeInt32Size(3, this.patch_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += GeneratedMessageV3.computeStringSize(4, this.suffix_);
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
            if (!(obj instanceof Version)) {
                return super.equals(obj);
            }
            Version other = (Version) obj;
            boolean result5 = 1 != 0 && hasMajor() == other.hasMajor();
            if (hasMajor()) {
                result5 = result5 && getMajor() == other.getMajor();
            }
            if (!result5 || hasMinor() != other.hasMinor()) {
                result = false;
            } else {
                result = true;
            }
            if (hasMinor()) {
                if (!result || getMinor() != other.getMinor()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasPatch() != other.hasPatch()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasPatch()) {
                if (!result2 || getPatch() != other.getPatch()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasSuffix() != other.hasSuffix()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasSuffix()) {
                if (!result3 || !getSuffix().equals(other.getSuffix())) {
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
            int hash = getDescriptor().hashCode() + 779;
            if (hasMajor()) {
                hash = (((hash * 37) + 1) * 53) + getMajor();
            }
            if (hasMinor()) {
                hash = (((hash * 37) + 2) * 53) + getMinor();
            }
            if (hasPatch()) {
                hash = (((hash * 37) + 3) * 53) + getPatch();
            }
            if (hasSuffix()) {
                hash = (((hash * 37) + 4) * 53) + getSuffix().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static Version parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Version) PARSER.parseFrom(data);
        }

        public static Version parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Version) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Version parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Version) PARSER.parseFrom(data);
        }

        public static Version parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Version) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Version parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Version) PARSER.parseFrom(data);
        }

        public static Version parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Version) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Version parseFrom(InputStream input) throws IOException {
            return (Version) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Version parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Version) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Version parseDelimitedFrom(InputStream input) throws IOException {
            return (Version) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Version parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Version) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Version parseFrom(CodedInputStream input) throws IOException {
            return (Version) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Version parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Version) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Version prototype) {
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

        public static Version getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Version> parser() {
            return PARSER;
        }

        public Parser<Version> getParserForType() {
            return PARSER;
        }

        public Version getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface CodeGeneratorRequestOrBuilder extends MessageOrBuilder {
        Version getCompilerVersion();

        VersionOrBuilder getCompilerVersionOrBuilder();

        String getFileToGenerate(int i);

        ByteString getFileToGenerateBytes(int i);

        int getFileToGenerateCount();

        List<String> getFileToGenerateList();

        String getParameter();

        ByteString getParameterBytes();

        FileDescriptorProto getProtoFile(int i);

        int getProtoFileCount();

        List<FileDescriptorProto> getProtoFileList();

        FileDescriptorProtoOrBuilder getProtoFileOrBuilder(int i);

        List<? extends FileDescriptorProtoOrBuilder> getProtoFileOrBuilderList();

        boolean hasCompilerVersion();

        boolean hasParameter();
    }

    public interface CodeGeneratorResponseOrBuilder extends MessageOrBuilder {
        String getError();

        ByteString getErrorBytes();

        File getFile(int i);

        int getFileCount();

        List<File> getFileList();

        FileOrBuilder getFileOrBuilder(int i);

        List<? extends FileOrBuilder> getFileOrBuilderList();

        boolean hasError();
    }

    public interface VersionOrBuilder extends MessageOrBuilder {
        int getMajor();

        int getMinor();

        int getPatch();

        String getSuffix();

        ByteString getSuffixBytes();

        boolean hasMajor();

        boolean hasMinor();

        boolean hasPatch();

        boolean hasSuffix();
    }

    private PluginProtos() {
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
        FileDescriptor[] fileDescriptorArr = {DescriptorProtos.getDescriptor()};
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n%google/protobuf/compiler/plugin.proto\u0012\u0018google.protobuf.compiler\u001a google/protobuf/descriptor.proto\"F\n\u0007Version\u0012\r\n\u0005major\u0018\u0001 \u0001(\u0005\u0012\r\n\u0005minor\u0018\u0002 \u0001(\u0005\u0012\r\n\u0005patch\u0018\u0003 \u0001(\u0005\u0012\u000e\n\u0006suffix\u0018\u0004 \u0001(\t\"º\u0001\n\u0014CodeGeneratorRequest\u0012\u0018\n\u0010file_to_generate\u0018\u0001 \u0003(\t\u0012\u0011\n\tparameter\u0018\u0002 \u0001(\t\u00128\n\nproto_file\u0018\u000f \u0003(\u000b2$.google.protobuf.FileDescriptorProto\u0012;\n\u0010compiler_version\u0018\u0003 \u0001(\u000b2!.google.protobuf.compiler.Version\"ª\u0001\n\u0015CodeGeneratorResponse\u0012\r\n\u0005error\u0018\u0001 \u0001(\t\u0012B\n\u0004file\u0018\u000f \u0003(\u000b24.google.protobuf.compiler.CodeGeneratorResponse.File\u001a>\n\u0004File\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0017\n\u000finsertion_point\u0018\u0002 \u0001(\t\u0012\u000f\n\u0007content\u0018\u000f \u0001(\tBg\n\u001ccom.google.protobuf.compilerB\fPluginProtosZ9github.com/golang/protobuf/protoc-gen-go/plugin;plugin_go"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                PluginProtos.descriptor = root;
                return null;
            }
        });
        DescriptorProtos.getDescriptor();
    }
}
