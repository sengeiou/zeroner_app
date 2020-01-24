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
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class DebugConf {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_dbgConfNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_dbgConfNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_dbgConfNotification_descriptor, new String[]{"SyslogLevel", "WatcherInterval"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_dbgConfSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_dbgConfSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_dbgConfSubscriber_descriptor, new String[]{"SupportSyslogLevel", "SupportSysWatcher"});

    public static final class dbgConfNotification extends GeneratedMessageV3 implements dbgConfNotificationOrBuilder {
        private static final dbgConfNotification DEFAULT_INSTANCE = new dbgConfNotification();
        @Deprecated
        public static final Parser<dbgConfNotification> PARSER = new AbstractParser<dbgConfNotification>() {
            public dbgConfNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new dbgConfNotification(input, extensionRegistry);
            }
        };
        public static final int SYSLOG_LEVEL_FIELD_NUMBER = 1;
        public static final int WATCHER_INTERVAL_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int syslogLevel_;
        /* access modifiers changed from: private */
        public int watcherInterval_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements dbgConfNotificationOrBuilder {
            private int bitField0_;
            private int syslogLevel_;
            private int watcherInterval_;

            public static final Descriptor getDescriptor() {
                return DebugConf.internal_static_dbgConfNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DebugConf.internal_static_dbgConfNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(dbgConfNotification.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (dbgConfNotification.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.syslogLevel_ = 0;
                this.bitField0_ &= -2;
                this.watcherInterval_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DebugConf.internal_static_dbgConfNotification_descriptor;
            }

            public dbgConfNotification getDefaultInstanceForType() {
                return dbgConfNotification.getDefaultInstance();
            }

            public dbgConfNotification build() {
                dbgConfNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public dbgConfNotification buildPartial() {
                dbgConfNotification result = new dbgConfNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.syslogLevel_ = this.syslogLevel_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.watcherInterval_ = this.watcherInterval_;
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
                if (other instanceof dbgConfNotification) {
                    return mergeFrom((dbgConfNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(dbgConfNotification other) {
                if (other != dbgConfNotification.getDefaultInstance()) {
                    if (other.hasSyslogLevel()) {
                        setSyslogLevel(other.getSyslogLevel());
                    }
                    if (other.hasWatcherInterval()) {
                        setWatcherInterval(other.getWatcherInterval());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSyslogLevel() && hasWatcherInterval()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                dbgConfNotification parsedMessage = null;
                try {
                    dbgConfNotification parsedMessage2 = (dbgConfNotification) dbgConfNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    dbgConfNotification parsedMessage3 = (dbgConfNotification) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSyslogLevel() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getSyslogLevel() {
                return this.syslogLevel_;
            }

            public Builder setSyslogLevel(int value) {
                this.bitField0_ |= 1;
                this.syslogLevel_ = value;
                onChanged();
                return this;
            }

            public Builder clearSyslogLevel() {
                this.bitField0_ &= -2;
                this.syslogLevel_ = 0;
                onChanged();
                return this;
            }

            public boolean hasWatcherInterval() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getWatcherInterval() {
                return this.watcherInterval_;
            }

            public Builder setWatcherInterval(int value) {
                this.bitField0_ |= 2;
                this.watcherInterval_ = value;
                onChanged();
                return this;
            }

            public Builder clearWatcherInterval() {
                this.bitField0_ &= -3;
                this.watcherInterval_ = 0;
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

        private dbgConfNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private dbgConfNotification() {
            this.memoizedIsInitialized = -1;
            this.syslogLevel_ = 0;
            this.watcherInterval_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private dbgConfNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.syslogLevel_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.watcherInterval_ = input.readFixed32();
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
            return DebugConf.internal_static_dbgConfNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DebugConf.internal_static_dbgConfNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(dbgConfNotification.class, Builder.class);
        }

        public boolean hasSyslogLevel() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getSyslogLevel() {
            return this.syslogLevel_;
        }

        public boolean hasWatcherInterval() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getWatcherInterval() {
            return this.watcherInterval_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSyslogLevel()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasWatcherInterval()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.syslogLevel_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.watcherInterval_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.syslogLevel_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.watcherInterval_);
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
            if (!(obj instanceof dbgConfNotification)) {
                return super.equals(obj);
            }
            dbgConfNotification other = (dbgConfNotification) obj;
            boolean result3 = 1 != 0 && hasSyslogLevel() == other.hasSyslogLevel();
            if (hasSyslogLevel()) {
                result3 = result3 && getSyslogLevel() == other.getSyslogLevel();
            }
            if (!result3 || hasWatcherInterval() != other.hasWatcherInterval()) {
                result = false;
            } else {
                result = true;
            }
            if (hasWatcherInterval()) {
                if (!result || getWatcherInterval() != other.getWatcherInterval()) {
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
            if (hasSyslogLevel()) {
                hash = (((hash * 37) + 1) * 53) + getSyslogLevel();
            }
            if (hasWatcherInterval()) {
                hash = (((hash * 37) + 2) * 53) + getWatcherInterval();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static dbgConfNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (dbgConfNotification) PARSER.parseFrom(data);
        }

        public static dbgConfNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (dbgConfNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static dbgConfNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (dbgConfNotification) PARSER.parseFrom(data);
        }

        public static dbgConfNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (dbgConfNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static dbgConfNotification parseFrom(InputStream input) throws IOException {
            return (dbgConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static dbgConfNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (dbgConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static dbgConfNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (dbgConfNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static dbgConfNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (dbgConfNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static dbgConfNotification parseFrom(CodedInputStream input) throws IOException {
            return (dbgConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static dbgConfNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (dbgConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(dbgConfNotification prototype) {
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

        public static dbgConfNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<dbgConfNotification> parser() {
            return PARSER;
        }

        public Parser<dbgConfNotification> getParserForType() {
            return PARSER;
        }

        public dbgConfNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class dbgConfSubscriber extends GeneratedMessageV3 implements dbgConfSubscriberOrBuilder {
        private static final dbgConfSubscriber DEFAULT_INSTANCE = new dbgConfSubscriber();
        @Deprecated
        public static final Parser<dbgConfSubscriber> PARSER = new AbstractParser<dbgConfSubscriber>() {
            public dbgConfSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new dbgConfSubscriber(input, extensionRegistry);
            }
        };
        public static final int SUPPORT_SYSLOG_LEVEL_FIELD_NUMBER = 1;
        public static final int SUPPORT_SYS_WATCHER_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean supportSysWatcher_;
        /* access modifiers changed from: private */
        public boolean supportSyslogLevel_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements dbgConfSubscriberOrBuilder {
            private int bitField0_;
            private boolean supportSysWatcher_;
            private boolean supportSyslogLevel_;

            public static final Descriptor getDescriptor() {
                return DebugConf.internal_static_dbgConfSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DebugConf.internal_static_dbgConfSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(dbgConfSubscriber.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (dbgConfSubscriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.supportSyslogLevel_ = false;
                this.bitField0_ &= -2;
                this.supportSysWatcher_ = false;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DebugConf.internal_static_dbgConfSubscriber_descriptor;
            }

            public dbgConfSubscriber getDefaultInstanceForType() {
                return dbgConfSubscriber.getDefaultInstance();
            }

            public dbgConfSubscriber build() {
                dbgConfSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public dbgConfSubscriber buildPartial() {
                dbgConfSubscriber result = new dbgConfSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.supportSyslogLevel_ = this.supportSyslogLevel_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.supportSysWatcher_ = this.supportSysWatcher_;
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
                if (other instanceof dbgConfSubscriber) {
                    return mergeFrom((dbgConfSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(dbgConfSubscriber other) {
                if (other != dbgConfSubscriber.getDefaultInstance()) {
                    if (other.hasSupportSyslogLevel()) {
                        setSupportSyslogLevel(other.getSupportSyslogLevel());
                    }
                    if (other.hasSupportSysWatcher()) {
                        setSupportSysWatcher(other.getSupportSysWatcher());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSupportSyslogLevel() && hasSupportSysWatcher()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                dbgConfSubscriber parsedMessage = null;
                try {
                    dbgConfSubscriber parsedMessage2 = (dbgConfSubscriber) dbgConfSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    dbgConfSubscriber parsedMessage3 = (dbgConfSubscriber) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSupportSyslogLevel() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getSupportSyslogLevel() {
                return this.supportSyslogLevel_;
            }

            public Builder setSupportSyslogLevel(boolean value) {
                this.bitField0_ |= 1;
                this.supportSyslogLevel_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportSyslogLevel() {
                this.bitField0_ &= -2;
                this.supportSyslogLevel_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportSysWatcher() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getSupportSysWatcher() {
                return this.supportSysWatcher_;
            }

            public Builder setSupportSysWatcher(boolean value) {
                this.bitField0_ |= 2;
                this.supportSysWatcher_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportSysWatcher() {
                this.bitField0_ &= -3;
                this.supportSysWatcher_ = false;
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

        private dbgConfSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private dbgConfSubscriber() {
            this.memoizedIsInitialized = -1;
            this.supportSyslogLevel_ = false;
            this.supportSysWatcher_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private dbgConfSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.supportSyslogLevel_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.supportSysWatcher_ = input.readBool();
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
            return DebugConf.internal_static_dbgConfSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DebugConf.internal_static_dbgConfSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(dbgConfSubscriber.class, Builder.class);
        }

        public boolean hasSupportSyslogLevel() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getSupportSyslogLevel() {
            return this.supportSyslogLevel_;
        }

        public boolean hasSupportSysWatcher() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getSupportSysWatcher() {
            return this.supportSysWatcher_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSupportSyslogLevel()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportSysWatcher()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.supportSyslogLevel_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.supportSysWatcher_);
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.supportSyslogLevel_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.supportSysWatcher_);
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
            if (!(obj instanceof dbgConfSubscriber)) {
                return super.equals(obj);
            }
            dbgConfSubscriber other = (dbgConfSubscriber) obj;
            boolean result3 = 1 != 0 && hasSupportSyslogLevel() == other.hasSupportSyslogLevel();
            if (hasSupportSyslogLevel()) {
                result3 = result3 && getSupportSyslogLevel() == other.getSupportSyslogLevel();
            }
            if (!result3 || hasSupportSysWatcher() != other.hasSupportSysWatcher()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSupportSysWatcher()) {
                if (!result || getSupportSysWatcher() != other.getSupportSysWatcher()) {
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
            if (hasSupportSyslogLevel()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getSupportSyslogLevel());
            }
            if (hasSupportSysWatcher()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getSupportSysWatcher());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static dbgConfSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (dbgConfSubscriber) PARSER.parseFrom(data);
        }

        public static dbgConfSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (dbgConfSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static dbgConfSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (dbgConfSubscriber) PARSER.parseFrom(data);
        }

        public static dbgConfSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (dbgConfSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static dbgConfSubscriber parseFrom(InputStream input) throws IOException {
            return (dbgConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static dbgConfSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (dbgConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static dbgConfSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (dbgConfSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static dbgConfSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (dbgConfSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static dbgConfSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (dbgConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static dbgConfSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (dbgConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(dbgConfSubscriber prototype) {
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

        public static dbgConfSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<dbgConfSubscriber> parser() {
            return PARSER;
        }

        public Parser<dbgConfSubscriber> getParserForType() {
            return PARSER;
        }

        public dbgConfSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface dbgConfNotificationOrBuilder extends MessageOrBuilder {
        int getSyslogLevel();

        int getWatcherInterval();

        boolean hasSyslogLevel();

        boolean hasWatcherInterval();
    }

    public interface dbgConfSubscriberOrBuilder extends MessageOrBuilder {
        boolean getSupportSysWatcher();

        boolean getSupportSyslogLevel();

        boolean hasSupportSysWatcher();

        boolean hasSupportSyslogLevel();
    }

    private DebugConf() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0010debug_conf.proto\"E\n\u0013dbgConfNotification\u0012\u0014\n\fsyslog_level\u0018\u0001 \u0002(\u0007\u0012\u0018\n\u0010watcher_interval\u0018\u0002 \u0002(\u0007\"N\n\u0011dbgConfSubscriber\u0012\u001c\n\u0014support_syslog_level\u0018\u0001 \u0002(\b\u0012\u001b\n\u0013support_sys_watcher\u0018\u0002 \u0002(\b"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                DebugConf.descriptor = root;
                return null;
            }
        });
    }
}
