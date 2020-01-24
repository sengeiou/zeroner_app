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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Alarmclock {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AlarmClock_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AlarmClock_fieldAccessorTable = new FieldAccessorTable(internal_static_AlarmClock_descriptor, new String[]{"Id", "Repeat", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Hour", "Minutes", "Text"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AlarmConfirm_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AlarmConfirm_fieldAccessorTable = new FieldAccessorTable(internal_static_AlarmConfirm_descriptor, new String[]{"Operation", "Ret"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AlarmGroup_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AlarmGroup_fieldAccessorTable = new FieldAccessorTable(internal_static_AlarmGroup_descriptor, new String[]{"Hash", "Alarmclock"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AlarmIDList_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AlarmIDList_fieldAccessorTable = new FieldAccessorTable(internal_static_AlarmIDList_descriptor, new String[]{"IdList"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AlarmNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AlarmNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_AlarmNotification_descriptor, new String[]{"Operation", "Group", "IdList", "Reserved", "Data"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AlarmSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AlarmSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_AlarmSubscriber_descriptor, new String[]{"Hash", "Confirm"});

    public static final class AlarmClock extends GeneratedMessageV3 implements AlarmClockOrBuilder {
        private static final AlarmClock DEFAULT_INSTANCE = new AlarmClock();
        public static final int FRIDAY_FIELD_NUMBER = 7;
        public static final int HOUR_FIELD_NUMBER = 10;
        public static final int ID_FIELD_NUMBER = 1;
        public static final int MINUTES_FIELD_NUMBER = 11;
        public static final int MONDAY_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<AlarmClock> PARSER = new AbstractParser<AlarmClock>() {
            public AlarmClock parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AlarmClock(input, extensionRegistry);
            }
        };
        public static final int REPEAT_FIELD_NUMBER = 2;
        public static final int SATURDAY_FIELD_NUMBER = 8;
        public static final int SUNDAY_FIELD_NUMBER = 9;
        public static final int TEXT_FIELD_NUMBER = 12;
        public static final int THURSDAY_FIELD_NUMBER = 6;
        public static final int TUESDAY_FIELD_NUMBER = 4;
        public static final int WEDNESDAY_FIELD_NUMBER = 5;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public boolean friday_;
        /* access modifiers changed from: private */
        public int hour_;
        /* access modifiers changed from: private */
        public int id_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int minutes_;
        /* access modifiers changed from: private */
        public boolean monday_;
        /* access modifiers changed from: private */
        public boolean repeat_;
        /* access modifiers changed from: private */
        public boolean saturday_;
        /* access modifiers changed from: private */
        public boolean sunday_;
        /* access modifiers changed from: private */
        public volatile Object text_;
        /* access modifiers changed from: private */
        public boolean thursday_;
        /* access modifiers changed from: private */
        public boolean tuesday_;
        /* access modifiers changed from: private */
        public boolean wednesday_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AlarmClockOrBuilder {
            private int bitField0_;
            private boolean friday_;
            private int hour_;
            private int id_;
            private int minutes_;
            private boolean monday_;
            private boolean repeat_;
            private boolean saturday_;
            private boolean sunday_;
            private Object text_;
            private boolean thursday_;
            private boolean tuesday_;
            private boolean wednesday_;

            public static final Descriptor getDescriptor() {
                return Alarmclock.internal_static_AlarmClock_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Alarmclock.internal_static_AlarmClock_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmClock.class, Builder.class);
            }

            private Builder() {
                this.text_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.text_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (AlarmClock.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.id_ = 0;
                this.bitField0_ &= -2;
                this.repeat_ = false;
                this.bitField0_ &= -3;
                this.monday_ = false;
                this.bitField0_ &= -5;
                this.tuesday_ = false;
                this.bitField0_ &= -9;
                this.wednesday_ = false;
                this.bitField0_ &= -17;
                this.thursday_ = false;
                this.bitField0_ &= -33;
                this.friday_ = false;
                this.bitField0_ &= -65;
                this.saturday_ = false;
                this.bitField0_ &= -129;
                this.sunday_ = false;
                this.bitField0_ &= -257;
                this.hour_ = 0;
                this.bitField0_ &= -513;
                this.minutes_ = 0;
                this.bitField0_ &= -1025;
                this.text_ = "";
                this.bitField0_ &= -2049;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Alarmclock.internal_static_AlarmClock_descriptor;
            }

            public AlarmClock getDefaultInstanceForType() {
                return AlarmClock.getDefaultInstance();
            }

            public AlarmClock build() {
                AlarmClock result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AlarmClock buildPartial() {
                AlarmClock result = new AlarmClock((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.id_ = this.id_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.repeat_ = this.repeat_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.monday_ = this.monday_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.tuesday_ = this.tuesday_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.wednesday_ = this.wednesday_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.thursday_ = this.thursday_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.friday_ = this.friday_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.saturday_ = this.saturday_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.sunday_ = this.sunday_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.hour_ = this.hour_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                result.minutes_ = this.minutes_;
                if ((from_bitField0_ & 2048) == 2048) {
                    to_bitField0_ |= 2048;
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
                if (other instanceof AlarmClock) {
                    return mergeFrom((AlarmClock) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AlarmClock other) {
                if (other != AlarmClock.getDefaultInstance()) {
                    if (other.hasId()) {
                        setId(other.getId());
                    }
                    if (other.hasRepeat()) {
                        setRepeat(other.getRepeat());
                    }
                    if (other.hasMonday()) {
                        setMonday(other.getMonday());
                    }
                    if (other.hasTuesday()) {
                        setTuesday(other.getTuesday());
                    }
                    if (other.hasWednesday()) {
                        setWednesday(other.getWednesday());
                    }
                    if (other.hasThursday()) {
                        setThursday(other.getThursday());
                    }
                    if (other.hasFriday()) {
                        setFriday(other.getFriday());
                    }
                    if (other.hasSaturday()) {
                        setSaturday(other.getSaturday());
                    }
                    if (other.hasSunday()) {
                        setSunday(other.getSunday());
                    }
                    if (other.hasHour()) {
                        setHour(other.getHour());
                    }
                    if (other.hasMinutes()) {
                        setMinutes(other.getMinutes());
                    }
                    if (other.hasText()) {
                        this.bitField0_ |= 2048;
                        this.text_ = other.text_;
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasId() && hasRepeat() && hasMonday() && hasTuesday() && hasWednesday() && hasThursday() && hasFriday() && hasSaturday() && hasSunday() && hasHour() && hasMinutes() && hasText()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                AlarmClock parsedMessage = null;
                try {
                    AlarmClock parsedMessage2 = (AlarmClock) AlarmClock.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AlarmClock parsedMessage3 = (AlarmClock) e.getUnfinishedMessage();
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

            public boolean hasRepeat() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getRepeat() {
                return this.repeat_;
            }

            public Builder setRepeat(boolean value) {
                this.bitField0_ |= 2;
                this.repeat_ = value;
                onChanged();
                return this;
            }

            public Builder clearRepeat() {
                this.bitField0_ &= -3;
                this.repeat_ = false;
                onChanged();
                return this;
            }

            public boolean hasMonday() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getMonday() {
                return this.monday_;
            }

            public Builder setMonday(boolean value) {
                this.bitField0_ |= 4;
                this.monday_ = value;
                onChanged();
                return this;
            }

            public Builder clearMonday() {
                this.bitField0_ &= -5;
                this.monday_ = false;
                onChanged();
                return this;
            }

            public boolean hasTuesday() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getTuesday() {
                return this.tuesday_;
            }

            public Builder setTuesday(boolean value) {
                this.bitField0_ |= 8;
                this.tuesday_ = value;
                onChanged();
                return this;
            }

            public Builder clearTuesday() {
                this.bitField0_ &= -9;
                this.tuesday_ = false;
                onChanged();
                return this;
            }

            public boolean hasWednesday() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getWednesday() {
                return this.wednesday_;
            }

            public Builder setWednesday(boolean value) {
                this.bitField0_ |= 16;
                this.wednesday_ = value;
                onChanged();
                return this;
            }

            public Builder clearWednesday() {
                this.bitField0_ &= -17;
                this.wednesday_ = false;
                onChanged();
                return this;
            }

            public boolean hasThursday() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean getThursday() {
                return this.thursday_;
            }

            public Builder setThursday(boolean value) {
                this.bitField0_ |= 32;
                this.thursday_ = value;
                onChanged();
                return this;
            }

            public Builder clearThursday() {
                this.bitField0_ &= -33;
                this.thursday_ = false;
                onChanged();
                return this;
            }

            public boolean hasFriday() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean getFriday() {
                return this.friday_;
            }

            public Builder setFriday(boolean value) {
                this.bitField0_ |= 64;
                this.friday_ = value;
                onChanged();
                return this;
            }

            public Builder clearFriday() {
                this.bitField0_ &= -65;
                this.friday_ = false;
                onChanged();
                return this;
            }

            public boolean hasSaturday() {
                return (this.bitField0_ & 128) == 128;
            }

            public boolean getSaturday() {
                return this.saturday_;
            }

            public Builder setSaturday(boolean value) {
                this.bitField0_ |= 128;
                this.saturday_ = value;
                onChanged();
                return this;
            }

            public Builder clearSaturday() {
                this.bitField0_ &= -129;
                this.saturday_ = false;
                onChanged();
                return this;
            }

            public boolean hasSunday() {
                return (this.bitField0_ & 256) == 256;
            }

            public boolean getSunday() {
                return this.sunday_;
            }

            public Builder setSunday(boolean value) {
                this.bitField0_ |= 256;
                this.sunday_ = value;
                onChanged();
                return this;
            }

            public Builder clearSunday() {
                this.bitField0_ &= -257;
                this.sunday_ = false;
                onChanged();
                return this;
            }

            public boolean hasHour() {
                return (this.bitField0_ & 512) == 512;
            }

            public int getHour() {
                return this.hour_;
            }

            public Builder setHour(int value) {
                this.bitField0_ |= 512;
                this.hour_ = value;
                onChanged();
                return this;
            }

            public Builder clearHour() {
                this.bitField0_ &= -513;
                this.hour_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMinutes() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public int getMinutes() {
                return this.minutes_;
            }

            public Builder setMinutes(int value) {
                this.bitField0_ |= 1024;
                this.minutes_ = value;
                onChanged();
                return this;
            }

            public Builder clearMinutes() {
                this.bitField0_ &= -1025;
                this.minutes_ = 0;
                onChanged();
                return this;
            }

            public boolean hasText() {
                return (this.bitField0_ & 2048) == 2048;
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
                this.bitField0_ |= 2048;
                this.text_ = value;
                onChanged();
                return this;
            }

            public Builder clearText() {
                this.bitField0_ &= -2049;
                this.text_ = AlarmClock.getDefaultInstance().getText();
                onChanged();
                return this;
            }

            public Builder setTextBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2048;
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

        private AlarmClock(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private AlarmClock() {
            this.memoizedIsInitialized = -1;
            this.id_ = 0;
            this.repeat_ = false;
            this.monday_ = false;
            this.tuesday_ = false;
            this.wednesday_ = false;
            this.thursday_ = false;
            this.friday_ = false;
            this.saturday_ = false;
            this.sunday_ = false;
            this.hour_ = 0;
            this.minutes_ = 0;
            this.text_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AlarmClock(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 2;
                            this.repeat_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.monday_ = input.readBool();
                            break;
                        case 32:
                            this.bitField0_ |= 8;
                            this.tuesday_ = input.readBool();
                            break;
                        case 40:
                            this.bitField0_ |= 16;
                            this.wednesday_ = input.readBool();
                            break;
                        case 48:
                            this.bitField0_ |= 32;
                            this.thursday_ = input.readBool();
                            break;
                        case 56:
                            this.bitField0_ |= 64;
                            this.friday_ = input.readBool();
                            break;
                        case 64:
                            this.bitField0_ |= 128;
                            this.saturday_ = input.readBool();
                            break;
                        case 72:
                            this.bitField0_ |= 256;
                            this.sunday_ = input.readBool();
                            break;
                        case 85:
                            this.bitField0_ |= 512;
                            this.hour_ = input.readFixed32();
                            break;
                        case 93:
                            this.bitField0_ |= 1024;
                            this.minutes_ = input.readFixed32();
                            break;
                        case 98:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2048;
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
            return Alarmclock.internal_static_AlarmClock_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Alarmclock.internal_static_AlarmClock_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmClock.class, Builder.class);
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getId() {
            return this.id_;
        }

        public boolean hasRepeat() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getRepeat() {
            return this.repeat_;
        }

        public boolean hasMonday() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getMonday() {
            return this.monday_;
        }

        public boolean hasTuesday() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getTuesday() {
            return this.tuesday_;
        }

        public boolean hasWednesday() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getWednesday() {
            return this.wednesday_;
        }

        public boolean hasThursday() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getThursday() {
            return this.thursday_;
        }

        public boolean hasFriday() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean getFriday() {
            return this.friday_;
        }

        public boolean hasSaturday() {
            return (this.bitField0_ & 128) == 128;
        }

        public boolean getSaturday() {
            return this.saturday_;
        }

        public boolean hasSunday() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean getSunday() {
            return this.sunday_;
        }

        public boolean hasHour() {
            return (this.bitField0_ & 512) == 512;
        }

        public int getHour() {
            return this.hour_;
        }

        public boolean hasMinutes() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public int getMinutes() {
            return this.minutes_;
        }

        public boolean hasText() {
            return (this.bitField0_ & 2048) == 2048;
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
            if (!hasId()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRepeat()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMonday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasTuesday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasWednesday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasThursday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFriday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSaturday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSunday()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasHour()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMinutes()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasText()) {
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
                output.writeBool(2, this.repeat_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.monday_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(4, this.tuesday_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(5, this.wednesday_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(6, this.thursday_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBool(7, this.friday_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeBool(8, this.saturday_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeBool(9, this.sunday_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeFixed32(10, this.hour_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeFixed32(11, this.minutes_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                GeneratedMessageV3.writeString(output, 12, this.text_);
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
                size2 += CodedOutputStream.computeBoolSize(2, this.repeat_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.monday_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBoolSize(4, this.tuesday_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBoolSize(5, this.wednesday_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeBoolSize(6, this.thursday_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeBoolSize(7, this.friday_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeBoolSize(8, this.saturday_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size2 += CodedOutputStream.computeBoolSize(9, this.sunday_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size2 += CodedOutputStream.computeFixed32Size(10, this.hour_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size2 += CodedOutputStream.computeFixed32Size(11, this.minutes_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                size2 += GeneratedMessageV3.computeStringSize(12, this.text_);
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
            boolean result9;
            boolean result10;
            boolean result11;
            boolean result12;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AlarmClock)) {
                return super.equals(obj);
            }
            AlarmClock other = (AlarmClock) obj;
            boolean result13 = 1 != 0 && hasId() == other.hasId();
            if (hasId()) {
                result13 = result13 && getId() == other.getId();
            }
            if (!result13 || hasRepeat() != other.hasRepeat()) {
                result = false;
            } else {
                result = true;
            }
            if (hasRepeat()) {
                if (!result || getRepeat() != other.getRepeat()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasMonday() != other.hasMonday()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasMonday()) {
                if (!result2 || getMonday() != other.getMonday()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasTuesday() != other.hasTuesday()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasTuesday()) {
                if (!result3 || getTuesday() != other.getTuesday()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasWednesday() != other.hasWednesday()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasWednesday()) {
                if (!result4 || getWednesday() != other.getWednesday()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasThursday() != other.hasThursday()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasThursday()) {
                if (!result5 || getThursday() != other.getThursday()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasFriday() != other.hasFriday()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasFriday()) {
                if (!result6 || getFriday() != other.getFriday()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasSaturday() != other.hasSaturday()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasSaturday()) {
                if (!result7 || getSaturday() != other.getSaturday()) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || hasSunday() != other.hasSunday()) {
                result8 = false;
            } else {
                result8 = true;
            }
            if (hasSunday()) {
                if (!result8 || getSunday() != other.getSunday()) {
                    result8 = false;
                } else {
                    result8 = true;
                }
            }
            if (!result8 || hasHour() != other.hasHour()) {
                result9 = false;
            } else {
                result9 = true;
            }
            if (hasHour()) {
                if (!result9 || getHour() != other.getHour()) {
                    result9 = false;
                } else {
                    result9 = true;
                }
            }
            if (!result9 || hasMinutes() != other.hasMinutes()) {
                result10 = false;
            } else {
                result10 = true;
            }
            if (hasMinutes()) {
                if (!result10 || getMinutes() != other.getMinutes()) {
                    result10 = false;
                } else {
                    result10 = true;
                }
            }
            if (!result10 || hasText() != other.hasText()) {
                result11 = false;
            } else {
                result11 = true;
            }
            if (hasText()) {
                if (!result11 || !getText().equals(other.getText())) {
                    result11 = false;
                } else {
                    result11 = true;
                }
            }
            if (!result11 || !this.unknownFields.equals(other.unknownFields)) {
                result12 = false;
            } else {
                result12 = true;
            }
            return result12;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasId()) {
                hash = (((hash * 37) + 1) * 53) + getId();
            }
            if (hasRepeat()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getRepeat());
            }
            if (hasMonday()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getMonday());
            }
            if (hasTuesday()) {
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getTuesday());
            }
            if (hasWednesday()) {
                hash = (((hash * 37) + 5) * 53) + Internal.hashBoolean(getWednesday());
            }
            if (hasThursday()) {
                hash = (((hash * 37) + 6) * 53) + Internal.hashBoolean(getThursday());
            }
            if (hasFriday()) {
                hash = (((hash * 37) + 7) * 53) + Internal.hashBoolean(getFriday());
            }
            if (hasSaturday()) {
                hash = (((hash * 37) + 8) * 53) + Internal.hashBoolean(getSaturday());
            }
            if (hasSunday()) {
                hash = (((hash * 37) + 9) * 53) + Internal.hashBoolean(getSunday());
            }
            if (hasHour()) {
                hash = (((hash * 37) + 10) * 53) + getHour();
            }
            if (hasMinutes()) {
                hash = (((hash * 37) + 11) * 53) + getMinutes();
            }
            if (hasText()) {
                hash = (((hash * 37) + 12) * 53) + getText().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static AlarmClock parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AlarmClock) PARSER.parseFrom(data);
        }

        public static AlarmClock parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmClock) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmClock parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AlarmClock) PARSER.parseFrom(data);
        }

        public static AlarmClock parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmClock) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmClock parseFrom(InputStream input) throws IOException {
            return (AlarmClock) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmClock parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmClock) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmClock parseDelimitedFrom(InputStream input) throws IOException {
            return (AlarmClock) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AlarmClock parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmClock) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmClock parseFrom(CodedInputStream input) throws IOException {
            return (AlarmClock) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmClock parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmClock) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AlarmClock prototype) {
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

        public static AlarmClock getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AlarmClock> parser() {
            return PARSER;
        }

        public Parser<AlarmClock> getParserForType() {
            return PARSER;
        }

        public AlarmClock getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class AlarmConfirm extends GeneratedMessageV3 implements AlarmConfirmOrBuilder {
        private static final AlarmConfirm DEFAULT_INSTANCE = new AlarmConfirm();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<AlarmConfirm> PARSER = new AbstractParser<AlarmConfirm>() {
            public AlarmConfirm parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AlarmConfirm(input, extensionRegistry);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AlarmConfirmOrBuilder {
            private int bitField0_;
            private int operation_;
            private boolean ret_;

            public static final Descriptor getDescriptor() {
                return Alarmclock.internal_static_AlarmConfirm_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Alarmclock.internal_static_AlarmConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmConfirm.class, Builder.class);
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
                if (AlarmConfirm.alwaysUseFieldBuilders) {
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
                return Alarmclock.internal_static_AlarmConfirm_descriptor;
            }

            public AlarmConfirm getDefaultInstanceForType() {
                return AlarmConfirm.getDefaultInstance();
            }

            public AlarmConfirm build() {
                AlarmConfirm result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AlarmConfirm buildPartial() {
                AlarmConfirm result = new AlarmConfirm((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (other instanceof AlarmConfirm) {
                    return mergeFrom((AlarmConfirm) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AlarmConfirm other) {
                if (other != AlarmConfirm.getDefaultInstance()) {
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
                AlarmConfirm parsedMessage = null;
                try {
                    AlarmConfirm parsedMessage2 = (AlarmConfirm) AlarmConfirm.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AlarmConfirm parsedMessage3 = (AlarmConfirm) e.getUnfinishedMessage();
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

            public AlarmOperation getOperation() {
                AlarmOperation result = AlarmOperation.valueOf(this.operation_);
                return result == null ? AlarmOperation.ADD : result;
            }

            public Builder setOperation(AlarmOperation value) {
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

        private AlarmConfirm(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private AlarmConfirm() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
            this.ret_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AlarmConfirm(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (AlarmOperation.valueOf(rawValue) != null) {
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
            return Alarmclock.internal_static_AlarmConfirm_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Alarmclock.internal_static_AlarmConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmConfirm.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public AlarmOperation getOperation() {
            AlarmOperation result = AlarmOperation.valueOf(this.operation_);
            return result == null ? AlarmOperation.ADD : result;
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
            if (!(obj instanceof AlarmConfirm)) {
                return super.equals(obj);
            }
            AlarmConfirm other = (AlarmConfirm) obj;
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

        public static AlarmConfirm parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AlarmConfirm) PARSER.parseFrom(data);
        }

        public static AlarmConfirm parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmConfirm parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AlarmConfirm) PARSER.parseFrom(data);
        }

        public static AlarmConfirm parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmConfirm parseFrom(InputStream input) throws IOException {
            return (AlarmConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmConfirm parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmConfirm parseDelimitedFrom(InputStream input) throws IOException {
            return (AlarmConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AlarmConfirm parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmConfirm parseFrom(CodedInputStream input) throws IOException {
            return (AlarmConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmConfirm parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AlarmConfirm prototype) {
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

        public static AlarmConfirm getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AlarmConfirm> parser() {
            return PARSER;
        }

        public Parser<AlarmConfirm> getParserForType() {
            return PARSER;
        }

        public AlarmConfirm getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class AlarmGroup extends GeneratedMessageV3 implements AlarmGroupOrBuilder {
        public static final int ALARMCLOCK_FIELD_NUMBER = 2;
        private static final AlarmGroup DEFAULT_INSTANCE = new AlarmGroup();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<AlarmGroup> PARSER = new AbstractParser<AlarmGroup>() {
            public AlarmGroup parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AlarmGroup(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public List<AlarmClock> alarmclock_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AlarmGroupOrBuilder {
            private RepeatedFieldBuilderV3<AlarmClock, Builder, AlarmClockOrBuilder> alarmclockBuilder_;
            private List<AlarmClock> alarmclock_;
            private int bitField0_;
            private int hash_;

            public static final Descriptor getDescriptor() {
                return Alarmclock.internal_static_AlarmGroup_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Alarmclock.internal_static_AlarmGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmGroup.class, Builder.class);
            }

            private Builder() {
                this.alarmclock_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.alarmclock_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (AlarmGroup.alwaysUseFieldBuilders) {
                    getAlarmclockFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.alarmclockBuilder_ == null) {
                    this.alarmclock_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.alarmclockBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Alarmclock.internal_static_AlarmGroup_descriptor;
            }

            public AlarmGroup getDefaultInstanceForType() {
                return AlarmGroup.getDefaultInstance();
            }

            public AlarmGroup build() {
                AlarmGroup result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AlarmGroup buildPartial() {
                AlarmGroup result = new AlarmGroup((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.alarmclockBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alarmclock_ = Collections.unmodifiableList(this.alarmclock_);
                        this.bitField0_ &= -3;
                    }
                    result.alarmclock_ = this.alarmclock_;
                } else {
                    result.alarmclock_ = this.alarmclockBuilder_.build();
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
                if (other instanceof AlarmGroup) {
                    return mergeFrom((AlarmGroup) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AlarmGroup other) {
                RepeatedFieldBuilderV3<AlarmClock, Builder, AlarmClockOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != AlarmGroup.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (this.alarmclockBuilder_ == null) {
                        if (!other.alarmclock_.isEmpty()) {
                            if (this.alarmclock_.isEmpty()) {
                                this.alarmclock_ = other.alarmclock_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureAlarmclockIsMutable();
                                this.alarmclock_.addAll(other.alarmclock_);
                            }
                            onChanged();
                        }
                    } else if (!other.alarmclock_.isEmpty()) {
                        if (this.alarmclockBuilder_.isEmpty()) {
                            this.alarmclockBuilder_.dispose();
                            this.alarmclockBuilder_ = null;
                            this.alarmclock_ = other.alarmclock_;
                            this.bitField0_ &= -3;
                            if (AlarmGroup.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getAlarmclockFieldBuilder();
                            }
                            this.alarmclockBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.alarmclockBuilder_.addAllMessages(other.alarmclock_);
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
                for (int i = 0; i < getAlarmclockCount(); i++) {
                    if (!getAlarmclock(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                AlarmGroup parsedMessage = null;
                try {
                    AlarmGroup parsedMessage2 = (AlarmGroup) AlarmGroup.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AlarmGroup parsedMessage3 = (AlarmGroup) e.getUnfinishedMessage();
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

            private void ensureAlarmclockIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alarmclock_ = new ArrayList(this.alarmclock_);
                    this.bitField0_ |= 2;
                }
            }

            public List<AlarmClock> getAlarmclockList() {
                if (this.alarmclockBuilder_ == null) {
                    return Collections.unmodifiableList(this.alarmclock_);
                }
                return this.alarmclockBuilder_.getMessageList();
            }

            public int getAlarmclockCount() {
                if (this.alarmclockBuilder_ == null) {
                    return this.alarmclock_.size();
                }
                return this.alarmclockBuilder_.getCount();
            }

            public AlarmClock getAlarmclock(int index) {
                if (this.alarmclockBuilder_ == null) {
                    return (AlarmClock) this.alarmclock_.get(index);
                }
                return (AlarmClock) this.alarmclockBuilder_.getMessage(index);
            }

            public Builder setAlarmclock(int index, AlarmClock value) {
                if (this.alarmclockBuilder_ != null) {
                    this.alarmclockBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setAlarmclock(int index, Builder builderForValue) {
                if (this.alarmclockBuilder_ == null) {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.alarmclockBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlarmclock(AlarmClock value) {
                if (this.alarmclockBuilder_ != null) {
                    this.alarmclockBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addAlarmclock(int index, AlarmClock value) {
                if (this.alarmclockBuilder_ != null) {
                    this.alarmclockBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addAlarmclock(Builder builderForValue) {
                if (this.alarmclockBuilder_ == null) {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.alarmclockBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlarmclock(int index, Builder builderForValue) {
                if (this.alarmclockBuilder_ == null) {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.alarmclockBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlarmclock(Iterable<? extends AlarmClock> values) {
                if (this.alarmclockBuilder_ == null) {
                    ensureAlarmclockIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.alarmclock_);
                    onChanged();
                } else {
                    this.alarmclockBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearAlarmclock() {
                if (this.alarmclockBuilder_ == null) {
                    this.alarmclock_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.alarmclockBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlarmclock(int index) {
                if (this.alarmclockBuilder_ == null) {
                    ensureAlarmclockIsMutable();
                    this.alarmclock_.remove(index);
                    onChanged();
                } else {
                    this.alarmclockBuilder_.remove(index);
                }
                return this;
            }

            public Builder getAlarmclockBuilder(int index) {
                return (Builder) getAlarmclockFieldBuilder().getBuilder(index);
            }

            public AlarmClockOrBuilder getAlarmclockOrBuilder(int index) {
                if (this.alarmclockBuilder_ == null) {
                    return (AlarmClockOrBuilder) this.alarmclock_.get(index);
                }
                return (AlarmClockOrBuilder) this.alarmclockBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends AlarmClockOrBuilder> getAlarmclockOrBuilderList() {
                if (this.alarmclockBuilder_ != null) {
                    return this.alarmclockBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alarmclock_);
            }

            public Builder addAlarmclockBuilder() {
                return (Builder) getAlarmclockFieldBuilder().addBuilder(AlarmClock.getDefaultInstance());
            }

            public Builder addAlarmclockBuilder(int index) {
                return (Builder) getAlarmclockFieldBuilder().addBuilder(index, AlarmClock.getDefaultInstance());
            }

            public List<Builder> getAlarmclockBuilderList() {
                return getAlarmclockFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<AlarmClock, Builder, AlarmClockOrBuilder> getAlarmclockFieldBuilder() {
                if (this.alarmclockBuilder_ == null) {
                    this.alarmclockBuilder_ = new RepeatedFieldBuilderV3<>(this.alarmclock_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.alarmclock_ = null;
                }
                return this.alarmclockBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private AlarmGroup(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private AlarmGroup() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.alarmclock_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AlarmGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.alarmclock_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.alarmclock_.add(input.readMessage(AlarmClock.PARSER, extensionRegistry));
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
                        this.alarmclock_ = Collections.unmodifiableList(this.alarmclock_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.alarmclock_ = Collections.unmodifiableList(this.alarmclock_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return Alarmclock.internal_static_AlarmGroup_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Alarmclock.internal_static_AlarmGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmGroup.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<AlarmClock> getAlarmclockList() {
            return this.alarmclock_;
        }

        public List<? extends AlarmClockOrBuilder> getAlarmclockOrBuilderList() {
            return this.alarmclock_;
        }

        public int getAlarmclockCount() {
            return this.alarmclock_.size();
        }

        public AlarmClock getAlarmclock(int index) {
            return (AlarmClock) this.alarmclock_.get(index);
        }

        public AlarmClockOrBuilder getAlarmclockOrBuilder(int index) {
            return (AlarmClockOrBuilder) this.alarmclock_.get(index);
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
            for (int i = 0; i < getAlarmclockCount(); i++) {
                if (!getAlarmclock(i).isInitialized()) {
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
            for (int i = 0; i < this.alarmclock_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.alarmclock_.get(i));
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
            for (int i = 0; i < this.alarmclock_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.alarmclock_.get(i));
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
            if (!(obj instanceof AlarmGroup)) {
                return super.equals(obj);
            }
            AlarmGroup other = (AlarmGroup) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getAlarmclockList().equals(other.getAlarmclockList())) {
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
            if (getAlarmclockCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getAlarmclockList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static AlarmGroup parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AlarmGroup) PARSER.parseFrom(data);
        }

        public static AlarmGroup parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmGroup parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AlarmGroup) PARSER.parseFrom(data);
        }

        public static AlarmGroup parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmGroup parseFrom(InputStream input) throws IOException {
            return (AlarmGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmGroup parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmGroup parseDelimitedFrom(InputStream input) throws IOException {
            return (AlarmGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AlarmGroup parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmGroup parseFrom(CodedInputStream input) throws IOException {
            return (AlarmGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmGroup parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AlarmGroup prototype) {
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

        public static AlarmGroup getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AlarmGroup> parser() {
            return PARSER;
        }

        public Parser<AlarmGroup> getParserForType() {
            return PARSER;
        }

        public AlarmGroup getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class AlarmIDList extends GeneratedMessageV3 implements AlarmIDListOrBuilder {
        private static final AlarmIDList DEFAULT_INSTANCE = new AlarmIDList();
        public static final int ID_LIST_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<AlarmIDList> PARSER = new AbstractParser<AlarmIDList>() {
            public AlarmIDList parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AlarmIDList(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public List<Integer> idList_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AlarmIDListOrBuilder {
            private int bitField0_;
            private List<Integer> idList_;

            public static final Descriptor getDescriptor() {
                return Alarmclock.internal_static_AlarmIDList_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Alarmclock.internal_static_AlarmIDList_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmIDList.class, Builder.class);
            }

            private Builder() {
                this.idList_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.idList_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (AlarmIDList.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.idList_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Alarmclock.internal_static_AlarmIDList_descriptor;
            }

            public AlarmIDList getDefaultInstanceForType() {
                return AlarmIDList.getDefaultInstance();
            }

            public AlarmIDList build() {
                AlarmIDList result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AlarmIDList buildPartial() {
                AlarmIDList result = new AlarmIDList((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.idList_ = Collections.unmodifiableList(this.idList_);
                    this.bitField0_ &= -2;
                }
                result.idList_ = this.idList_;
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
                if (other instanceof AlarmIDList) {
                    return mergeFrom((AlarmIDList) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AlarmIDList other) {
                if (other != AlarmIDList.getDefaultInstance()) {
                    if (!other.idList_.isEmpty()) {
                        if (this.idList_.isEmpty()) {
                            this.idList_ = other.idList_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureIdListIsMutable();
                            this.idList_.addAll(other.idList_);
                        }
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
                AlarmIDList parsedMessage = null;
                try {
                    AlarmIDList parsedMessage2 = (AlarmIDList) AlarmIDList.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AlarmIDList parsedMessage3 = (AlarmIDList) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            private void ensureIdListIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.idList_ = new ArrayList(this.idList_);
                    this.bitField0_ |= 1;
                }
            }

            public List<Integer> getIdListList() {
                return Collections.unmodifiableList(this.idList_);
            }

            public int getIdListCount() {
                return this.idList_.size();
            }

            public int getIdList(int index) {
                return ((Integer) this.idList_.get(index)).intValue();
            }

            public Builder setIdList(int index, int value) {
                ensureIdListIsMutable();
                this.idList_.set(index, Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addIdList(int value) {
                ensureIdListIsMutable();
                this.idList_.add(Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addAllIdList(Iterable<? extends Integer> values) {
                ensureIdListIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.idList_);
                onChanged();
                return this;
            }

            public Builder clearIdList() {
                this.idList_ = Collections.emptyList();
                this.bitField0_ &= -2;
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

        private AlarmIDList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private AlarmIDList() {
            this.memoizedIsInitialized = -1;
            this.idList_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AlarmIDList(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int limit = input.pushLimit(input.readRawVarint32());
                            if ((mutable_bitField0_ & 1) != 1 && input.getBytesUntilLimit() > 0) {
                                this.idList_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.idList_.add(Integer.valueOf(input.readFixed32()));
                            }
                            input.popLimit(limit);
                            break;
                        case 13:
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.idList_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.idList_.add(Integer.valueOf(input.readFixed32()));
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
                        this.idList_ = Collections.unmodifiableList(this.idList_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.idList_ = Collections.unmodifiableList(this.idList_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return Alarmclock.internal_static_AlarmIDList_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Alarmclock.internal_static_AlarmIDList_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmIDList.class, Builder.class);
        }

        public List<Integer> getIdListList() {
            return this.idList_;
        }

        public int getIdListCount() {
            return this.idList_.size();
        }

        public int getIdList(int index) {
            return ((Integer) this.idList_.get(index)).intValue();
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
            for (int i = 0; i < this.idList_.size(); i++) {
                output.writeFixed32(1, ((Integer) this.idList_.get(i)).intValue());
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0 + (getIdListList().size() * 4) + (getIdListList().size() * 1) + this.unknownFields.getSerializedSize();
            this.memoizedSize = size2;
            return size2;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AlarmIDList)) {
                return super.equals(obj);
            }
            AlarmIDList other = (AlarmIDList) obj;
            if (1 == 0 || !getIdListList().equals(other.getIdListList())) {
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
            if (getIdListCount() > 0) {
                hash = (((hash * 37) + 1) * 53) + getIdListList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static AlarmIDList parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AlarmIDList) PARSER.parseFrom(data);
        }

        public static AlarmIDList parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmIDList) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmIDList parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AlarmIDList) PARSER.parseFrom(data);
        }

        public static AlarmIDList parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmIDList) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmIDList parseFrom(InputStream input) throws IOException {
            return (AlarmIDList) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmIDList parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmIDList) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmIDList parseDelimitedFrom(InputStream input) throws IOException {
            return (AlarmIDList) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AlarmIDList parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmIDList) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmIDList parseFrom(CodedInputStream input) throws IOException {
            return (AlarmIDList) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmIDList parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmIDList) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AlarmIDList prototype) {
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

        public static AlarmIDList getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AlarmIDList> parser() {
            return PARSER;
        }

        public Parser<AlarmIDList> getParserForType() {
            return PARSER;
        }

        public AlarmIDList getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class AlarmNotification extends GeneratedMessageV3 implements AlarmNotificationOrBuilder {
        private static final AlarmNotification DEFAULT_INSTANCE = new AlarmNotification();
        public static final int GROUP_FIELD_NUMBER = 2;
        public static final int ID_LIST_FIELD_NUMBER = 3;
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<AlarmNotification> PARSER = new AbstractParser<AlarmNotification>() {
            public AlarmNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AlarmNotification(input, extensionRegistry);
            }
        };
        public static final int RESERVED_FIELD_NUMBER = 4;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;

        public enum DataCase implements EnumLite {
            GROUP(2),
            ID_LIST(3),
            RESERVED(4),
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
                        return GROUP;
                    case 3:
                        return ID_LIST;
                    case 4:
                        return RESERVED;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AlarmNotificationOrBuilder {
            private int bitField0_;
            private int dataCase_;
            private Object data_;
            private SingleFieldBuilderV3<AlarmGroup, Builder, AlarmGroupOrBuilder> groupBuilder_;
            private SingleFieldBuilderV3<AlarmIDList, Builder, AlarmIDListOrBuilder> idListBuilder_;
            private int operation_;

            public static final Descriptor getDescriptor() {
                return Alarmclock.internal_static_AlarmNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Alarmclock.internal_static_AlarmNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmNotification.class, Builder.class);
            }

            private Builder() {
                this.dataCase_ = 0;
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dataCase_ = 0;
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (AlarmNotification.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.operation_ = 0;
                this.bitField0_ &= -2;
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Alarmclock.internal_static_AlarmNotification_descriptor;
            }

            public AlarmNotification getDefaultInstanceForType() {
                return AlarmNotification.getDefaultInstance();
            }

            public AlarmNotification build() {
                AlarmNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AlarmNotification buildPartial() {
                AlarmNotification result = new AlarmNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.operation_ = this.operation_;
                if (this.dataCase_ == 2) {
                    if (this.groupBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.groupBuilder_.build();
                    }
                }
                if (this.dataCase_ == 3) {
                    if (this.idListBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.idListBuilder_.build();
                    }
                }
                if (this.dataCase_ == 4) {
                    result.data_ = this.data_;
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
                if (other instanceof AlarmNotification) {
                    return mergeFrom((AlarmNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AlarmNotification other) {
                if (other != AlarmNotification.getDefaultInstance()) {
                    if (other.hasOperation()) {
                        setOperation(other.getOperation());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$Alarmclock$AlarmNotification$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeGroup(other.getGroup());
                            break;
                        case 2:
                            mergeIdList(other.getIdList());
                            break;
                        case 3:
                            setReserved(other.getReserved());
                            break;
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
                AlarmNotification parsedMessage = null;
                try {
                    AlarmNotification parsedMessage2 = (AlarmNotification) AlarmNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AlarmNotification parsedMessage3 = (AlarmNotification) e.getUnfinishedMessage();
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

            public boolean hasOperation() {
                return (this.bitField0_ & 1) == 1;
            }

            public AlarmOperation getOperation() {
                AlarmOperation result = AlarmOperation.valueOf(this.operation_);
                return result == null ? AlarmOperation.ADD : result;
            }

            public Builder setOperation(AlarmOperation value) {
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
                return this.dataCase_ == 2;
            }

            public AlarmGroup getGroup() {
                if (this.groupBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (AlarmGroup) this.data_;
                    }
                    return AlarmGroup.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (AlarmGroup) this.groupBuilder_.getMessage();
                } else {
                    return AlarmGroup.getDefaultInstance();
                }
            }

            public Builder setGroup(AlarmGroup value) {
                if (this.groupBuilder_ != null) {
                    this.groupBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setGroup(Builder builderForValue) {
                if (this.groupBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.groupBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeGroup(AlarmGroup value) {
                if (this.groupBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == AlarmGroup.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = AlarmGroup.newBuilder((AlarmGroup) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.groupBuilder_.mergeFrom(value);
                    }
                    this.groupBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearGroup() {
                if (this.groupBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.groupBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getGroupBuilder() {
                return (Builder) getGroupFieldBuilder().getBuilder();
            }

            public AlarmGroupOrBuilder getGroupOrBuilder() {
                if (this.dataCase_ == 2 && this.groupBuilder_ != null) {
                    return (AlarmGroupOrBuilder) this.groupBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (AlarmGroup) this.data_;
                }
                return AlarmGroup.getDefaultInstance();
            }

            private SingleFieldBuilderV3<AlarmGroup, Builder, AlarmGroupOrBuilder> getGroupFieldBuilder() {
                if (this.groupBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = AlarmGroup.getDefaultInstance();
                    }
                    this.groupBuilder_ = new SingleFieldBuilderV3<>((AlarmGroup) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.groupBuilder_;
            }

            public boolean hasIdList() {
                return this.dataCase_ == 3;
            }

            public AlarmIDList getIdList() {
                if (this.idListBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (AlarmIDList) this.data_;
                    }
                    return AlarmIDList.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (AlarmIDList) this.idListBuilder_.getMessage();
                } else {
                    return AlarmIDList.getDefaultInstance();
                }
            }

            public Builder setIdList(AlarmIDList value) {
                if (this.idListBuilder_ != null) {
                    this.idListBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder setIdList(Builder builderForValue) {
                if (this.idListBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.idListBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder mergeIdList(AlarmIDList value) {
                if (this.idListBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == AlarmIDList.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = AlarmIDList.newBuilder((AlarmIDList) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 3) {
                        this.idListBuilder_.mergeFrom(value);
                    }
                    this.idListBuilder_.setMessage(value);
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder clearIdList() {
                if (this.idListBuilder_ != null) {
                    if (this.dataCase_ == 3) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.idListBuilder_.clear();
                } else if (this.dataCase_ == 3) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getIdListBuilder() {
                return (Builder) getIdListFieldBuilder().getBuilder();
            }

            public AlarmIDListOrBuilder getIdListOrBuilder() {
                if (this.dataCase_ == 3 && this.idListBuilder_ != null) {
                    return (AlarmIDListOrBuilder) this.idListBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (AlarmIDList) this.data_;
                }
                return AlarmIDList.getDefaultInstance();
            }

            private SingleFieldBuilderV3<AlarmIDList, Builder, AlarmIDListOrBuilder> getIdListFieldBuilder() {
                if (this.idListBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = AlarmIDList.getDefaultInstance();
                    }
                    this.idListBuilder_ = new SingleFieldBuilderV3<>((AlarmIDList) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 3;
                onChanged();
                return this.idListBuilder_;
            }

            public boolean hasReserved() {
                return this.dataCase_ == 4;
            }

            public int getReserved() {
                if (this.dataCase_ == 4) {
                    return ((Integer) this.data_).intValue();
                }
                return 0;
            }

            public Builder setReserved(int value) {
                this.dataCase_ = 4;
                this.data_ = Integer.valueOf(value);
                onChanged();
                return this;
            }

            public Builder clearReserved() {
                if (this.dataCase_ == 4) {
                    this.dataCase_ = 0;
                    this.data_ = null;
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

        private AlarmNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private AlarmNotification() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AlarmNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (AlarmOperation.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.operation_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            Builder subBuilder = null;
                            if (this.dataCase_ == 2) {
                                subBuilder = ((AlarmGroup) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(AlarmGroup.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((AlarmGroup) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 3) {
                                subBuilder2 = ((AlarmIDList) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(AlarmIDList.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((AlarmIDList) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 3;
                            break;
                        case 37:
                            this.dataCase_ = 4;
                            this.data_ = Integer.valueOf(input.readFixed32());
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
            return Alarmclock.internal_static_AlarmNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Alarmclock.internal_static_AlarmNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmNotification.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public AlarmOperation getOperation() {
            AlarmOperation result = AlarmOperation.valueOf(this.operation_);
            return result == null ? AlarmOperation.ADD : result;
        }

        public boolean hasGroup() {
            return this.dataCase_ == 2;
        }

        public AlarmGroup getGroup() {
            if (this.dataCase_ == 2) {
                return (AlarmGroup) this.data_;
            }
            return AlarmGroup.getDefaultInstance();
        }

        public AlarmGroupOrBuilder getGroupOrBuilder() {
            if (this.dataCase_ == 2) {
                return (AlarmGroup) this.data_;
            }
            return AlarmGroup.getDefaultInstance();
        }

        public boolean hasIdList() {
            return this.dataCase_ == 3;
        }

        public AlarmIDList getIdList() {
            if (this.dataCase_ == 3) {
                return (AlarmIDList) this.data_;
            }
            return AlarmIDList.getDefaultInstance();
        }

        public AlarmIDListOrBuilder getIdListOrBuilder() {
            if (this.dataCase_ == 3) {
                return (AlarmIDList) this.data_;
            }
            return AlarmIDList.getDefaultInstance();
        }

        public boolean hasReserved() {
            return this.dataCase_ == 4;
        }

        public int getReserved() {
            if (this.dataCase_ == 4) {
                return ((Integer) this.data_).intValue();
            }
            return 0;
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
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (AlarmGroup) this.data_);
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (AlarmIDList) this.data_);
            }
            if (this.dataCase_ == 4) {
                output.writeFixed32(4, ((Integer) this.data_).intValue());
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
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (AlarmGroup) this.data_);
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (AlarmIDList) this.data_);
            }
            if (this.dataCase_ == 4) {
                size2 += CodedOutputStream.computeFixed32Size(4, ((Integer) this.data_).intValue());
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
            if (!(obj instanceof AlarmNotification)) {
                return super.equals(obj);
            }
            AlarmNotification other = (AlarmNotification) obj;
            boolean result3 = 1 != 0 && hasOperation() == other.hasOperation();
            if (hasOperation()) {
                result3 = result3 && this.operation_ == other.operation_;
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
                    if (result && getGroup().equals(other.getGroup())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getIdList().equals(other.getIdList())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 4:
                    if (result && getReserved() == other.getReserved()) {
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
            if (hasOperation()) {
                hash = (((hash * 37) + 1) * 53) + this.operation_;
            }
            switch (this.dataCase_) {
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getGroup().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getIdList().hashCode();
                    break;
                case 4:
                    hash = (((hash * 37) + 4) * 53) + getReserved();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static AlarmNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AlarmNotification) PARSER.parseFrom(data);
        }

        public static AlarmNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AlarmNotification) PARSER.parseFrom(data);
        }

        public static AlarmNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmNotification parseFrom(InputStream input) throws IOException {
            return (AlarmNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (AlarmNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AlarmNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmNotification parseFrom(CodedInputStream input) throws IOException {
            return (AlarmNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AlarmNotification prototype) {
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

        public static AlarmNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AlarmNotification> parser() {
            return PARSER;
        }

        public Parser<AlarmNotification> getParserForType() {
            return PARSER;
        }

        public AlarmNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum AlarmOperation implements ProtocolMessageEnum {
        ADD(0),
        REMOVE(1),
        CLEAR(2);
        
        public static final int ADD_VALUE = 0;
        public static final int CLEAR_VALUE = 2;
        public static final int REMOVE_VALUE = 1;
        private static final AlarmOperation[] VALUES = null;
        private static final EnumLiteMap<AlarmOperation> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<AlarmOperation>() {
                public AlarmOperation findValueByNumber(int number) {
                    return AlarmOperation.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static AlarmOperation valueOf(int value2) {
            return forNumber(value2);
        }

        public static AlarmOperation forNumber(int value2) {
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

        public static EnumLiteMap<AlarmOperation> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) Alarmclock.getDescriptor().getEnumTypes().get(0);
        }

        public static AlarmOperation valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private AlarmOperation(int value2) {
            this.value = value2;
        }
    }

    public static final class AlarmSubscriber extends GeneratedMessageV3 implements AlarmSubscriberOrBuilder {
        public static final int CONFIRM_FIELD_NUMBER = 2;
        private static final AlarmSubscriber DEFAULT_INSTANCE = new AlarmSubscriber();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<AlarmSubscriber> PARSER = new AbstractParser<AlarmSubscriber>() {
            public AlarmSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AlarmSubscriber(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public AlarmConfirm confirm_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AlarmSubscriberOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<AlarmConfirm, Builder, AlarmConfirmOrBuilder> confirmBuilder_;
            private AlarmConfirm confirm_;
            private int hash_;

            public static final Descriptor getDescriptor() {
                return Alarmclock.internal_static_AlarmSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return Alarmclock.internal_static_AlarmSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmSubscriber.class, Builder.class);
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
                if (AlarmSubscriber.alwaysUseFieldBuilders) {
                    getConfirmFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.confirmBuilder_ == null) {
                    this.confirm_ = null;
                } else {
                    this.confirmBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return Alarmclock.internal_static_AlarmSubscriber_descriptor;
            }

            public AlarmSubscriber getDefaultInstanceForType() {
                return AlarmSubscriber.getDefaultInstance();
            }

            public AlarmSubscriber build() {
                AlarmSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AlarmSubscriber buildPartial() {
                AlarmSubscriber result = new AlarmSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.confirmBuilder_ == null) {
                    result.confirm_ = this.confirm_;
                } else {
                    result.confirm_ = (AlarmConfirm) this.confirmBuilder_.build();
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
                if (other instanceof AlarmSubscriber) {
                    return mergeFrom((AlarmSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AlarmSubscriber other) {
                if (other != AlarmSubscriber.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
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
                if (!hasHash()) {
                    return false;
                }
                if (!hasConfirm() || getConfirm().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                AlarmSubscriber parsedMessage = null;
                try {
                    AlarmSubscriber parsedMessage2 = (AlarmSubscriber) AlarmSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AlarmSubscriber parsedMessage3 = (AlarmSubscriber) e.getUnfinishedMessage();
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

            public boolean hasConfirm() {
                return (this.bitField0_ & 2) == 2;
            }

            public AlarmConfirm getConfirm() {
                if (this.confirmBuilder_ == null) {
                    return this.confirm_ == null ? AlarmConfirm.getDefaultInstance() : this.confirm_;
                }
                return (AlarmConfirm) this.confirmBuilder_.getMessage();
            }

            public Builder setConfirm(AlarmConfirm value) {
                if (this.confirmBuilder_ != null) {
                    this.confirmBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.confirm_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setConfirm(Builder builderForValue) {
                if (this.confirmBuilder_ == null) {
                    this.confirm_ = builderForValue.build();
                    onChanged();
                } else {
                    this.confirmBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeConfirm(AlarmConfirm value) {
                if (this.confirmBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.confirm_ == null || this.confirm_ == AlarmConfirm.getDefaultInstance()) {
                        this.confirm_ = value;
                    } else {
                        this.confirm_ = AlarmConfirm.newBuilder(this.confirm_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.confirmBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearConfirm() {
                if (this.confirmBuilder_ == null) {
                    this.confirm_ = null;
                    onChanged();
                } else {
                    this.confirmBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getConfirmBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getConfirmFieldBuilder().getBuilder();
            }

            public AlarmConfirmOrBuilder getConfirmOrBuilder() {
                if (this.confirmBuilder_ != null) {
                    return (AlarmConfirmOrBuilder) this.confirmBuilder_.getMessageOrBuilder();
                }
                return this.confirm_ == null ? AlarmConfirm.getDefaultInstance() : this.confirm_;
            }

            private SingleFieldBuilderV3<AlarmConfirm, Builder, AlarmConfirmOrBuilder> getConfirmFieldBuilder() {
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

        private AlarmSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private AlarmSubscriber() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AlarmSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.confirm_.toBuilder();
                            }
                            this.confirm_ = (AlarmConfirm) input.readMessage(AlarmConfirm.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.confirm_);
                                this.confirm_ = subBuilder.buildPartial();
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
            return Alarmclock.internal_static_AlarmSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return Alarmclock.internal_static_AlarmSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(AlarmSubscriber.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasConfirm() {
            return (this.bitField0_ & 2) == 2;
        }

        public AlarmConfirm getConfirm() {
            return this.confirm_ == null ? AlarmConfirm.getDefaultInstance() : this.confirm_;
        }

        public AlarmConfirmOrBuilder getConfirmOrBuilder() {
            return this.confirm_ == null ? AlarmConfirm.getDefaultInstance() : this.confirm_;
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
                output.writeMessage(2, getConfirm());
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
                size2 += CodedOutputStream.computeMessageSize(2, getConfirm());
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
            if (!(obj instanceof AlarmSubscriber)) {
                return super.equals(obj);
            }
            AlarmSubscriber other = (AlarmSubscriber) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || hasConfirm() != other.hasConfirm()) {
                result = false;
            } else {
                result = true;
            }
            if (hasConfirm()) {
                if (!result || !getConfirm().equals(other.getConfirm())) {
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
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasConfirm()) {
                hash = (((hash * 37) + 2) * 53) + getConfirm().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static AlarmSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AlarmSubscriber) PARSER.parseFrom(data);
        }

        public static AlarmSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AlarmSubscriber) PARSER.parseFrom(data);
        }

        public static AlarmSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AlarmSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AlarmSubscriber parseFrom(InputStream input) throws IOException {
            return (AlarmSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (AlarmSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AlarmSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AlarmSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (AlarmSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AlarmSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AlarmSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AlarmSubscriber prototype) {
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

        public static AlarmSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AlarmSubscriber> parser() {
            return PARSER;
        }

        public Parser<AlarmSubscriber> getParserForType() {
            return PARSER;
        }

        public AlarmSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.Alarmclock$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$Alarmclock$AlarmNotification$DataCase = new int[DataCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$Alarmclock$AlarmNotification$DataCase[DataCase.GROUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$Alarmclock$AlarmNotification$DataCase[DataCase.ID_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$Alarmclock$AlarmNotification$DataCase[DataCase.RESERVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$Alarmclock$AlarmNotification$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public interface AlarmClockOrBuilder extends MessageOrBuilder {
        boolean getFriday();

        int getHour();

        int getId();

        int getMinutes();

        boolean getMonday();

        boolean getRepeat();

        boolean getSaturday();

        boolean getSunday();

        String getText();

        ByteString getTextBytes();

        boolean getThursday();

        boolean getTuesday();

        boolean getWednesday();

        boolean hasFriday();

        boolean hasHour();

        boolean hasId();

        boolean hasMinutes();

        boolean hasMonday();

        boolean hasRepeat();

        boolean hasSaturday();

        boolean hasSunday();

        boolean hasText();

        boolean hasThursday();

        boolean hasTuesday();

        boolean hasWednesday();
    }

    public interface AlarmConfirmOrBuilder extends MessageOrBuilder {
        AlarmOperation getOperation();

        boolean getRet();

        boolean hasOperation();

        boolean hasRet();
    }

    public interface AlarmGroupOrBuilder extends MessageOrBuilder {
        AlarmClock getAlarmclock(int i);

        int getAlarmclockCount();

        List<AlarmClock> getAlarmclockList();

        AlarmClockOrBuilder getAlarmclockOrBuilder(int i);

        List<? extends AlarmClockOrBuilder> getAlarmclockOrBuilderList();

        int getHash();

        boolean hasHash();
    }

    public interface AlarmIDListOrBuilder extends MessageOrBuilder {
        int getIdList(int i);

        int getIdListCount();

        List<Integer> getIdListList();
    }

    public interface AlarmNotificationOrBuilder extends MessageOrBuilder {
        DataCase getDataCase();

        AlarmGroup getGroup();

        AlarmGroupOrBuilder getGroupOrBuilder();

        AlarmIDList getIdList();

        AlarmIDListOrBuilder getIdListOrBuilder();

        AlarmOperation getOperation();

        int getReserved();

        boolean hasGroup();

        boolean hasIdList();

        boolean hasOperation();

        boolean hasReserved();
    }

    public interface AlarmSubscriberOrBuilder extends MessageOrBuilder {
        AlarmConfirm getConfirm();

        AlarmConfirmOrBuilder getConfirmOrBuilder();

        int getHash();

        boolean hasConfirm();

        boolean hasHash();
    }

    private Alarmclock() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0010alarmclock.proto\"Í\u0001\n\nAlarmClock\u0012\n\n\u0002id\u0018\u0001 \u0002(\u0007\u0012\u000e\n\u0006repeat\u0018\u0002 \u0002(\b\u0012\u000e\n\u0006monday\u0018\u0003 \u0002(\b\u0012\u000f\n\u0007tuesday\u0018\u0004 \u0002(\b\u0012\u0011\n\twednesday\u0018\u0005 \u0002(\b\u0012\u0010\n\bthursday\u0018\u0006 \u0002(\b\u0012\u000e\n\u0006friday\u0018\u0007 \u0002(\b\u0012\u0010\n\bsaturday\u0018\b \u0002(\b\u0012\u000e\n\u0006sunday\u0018\t \u0002(\b\u0012\f\n\u0004hour\u0018\n \u0002(\u0007\u0012\u000f\n\u0007minutes\u0018\u000b \u0002(\u0007\u0012\f\n\u0004text\u0018\f \u0002(\t\";\n\nAlarmGroup\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u001f\n\nalarmclock\u0018\u0002 \u0003(\u000b2\u000b.AlarmClock\"\u001e\n\u000bAlarmIDList\u0012\u000f\n\u0007id_list\u0018\u0001 \u0003(\u0007\"\u0001\n\u0011AlarmNotification\u0012\"\n\toperation\u0018\u0001 \u0002(\u000e2\u000f.AlarmOperation\u0012\u001c\n\u0005group\u0018\u0002 \u0001(\u000b2\u000b.Alarm", "GroupH\u0000\u0012\u001f\n\u0007id_list\u0018\u0003 \u0001(\u000b2\f.AlarmIDListH\u0000\u0012\u0012\n\breserved\u0018\u0004 \u0001(\u0007H\u0000B\u0006\n\u0004data\"?\n\fAlarmConfirm\u0012\"\n\toperation\u0018\u0001 \u0002(\u000e2\u000f.AlarmOperation\u0012\u000b\n\u0003ret\u0018\u0002 \u0002(\b\"?\n\u000fAlarmSubscriber\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u001e\n\u0007confirm\u0018\u0002 \u0001(\u000b2\r.AlarmConfirm*0\n\u000eAlarmOperation\u0012\u0007\n\u0003ADD\u0010\u0000\u0012\n\n\u0006REMOVE\u0010\u0001\u0012\t\n\u0005CLEAR\u0010\u0002"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                Alarmclock.descriptor = root;
                return null;
            }
        });
    }
}
