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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SedentarinessOuterClass {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_Sedentariness_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_Sedentariness_fieldAccessorTable = new FieldAccessorTable(internal_static_Sedentariness_descriptor, new String[]{"Repeat", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "StartHour", "EndHour", "Duration", "Threshold"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_SedtConfirm_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_SedtConfirm_fieldAccessorTable = new FieldAccessorTable(internal_static_SedtConfirm_descriptor, new String[]{"Operation", "Ret"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_SedtGroup_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_SedtGroup_fieldAccessorTable = new FieldAccessorTable(internal_static_SedtGroup_descriptor, new String[]{"Hash", "Sedentariness"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_SedtNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_SedtNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_SedtNotification_descriptor, new String[]{"Operation", "Group"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_SedtSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_SedtSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_SedtSubscriber_descriptor, new String[]{"Hash", "Confirm"});

    public static final class Sedentariness extends GeneratedMessageV3 implements SedentarinessOrBuilder {
        private static final Sedentariness DEFAULT_INSTANCE = new Sedentariness();
        public static final int DURATION_FIELD_NUMBER = 11;
        public static final int END_HOUR_FIELD_NUMBER = 10;
        public static final int FRIDAY_FIELD_NUMBER = 6;
        public static final int MONDAY_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<Sedentariness> PARSER = new AbstractParser<Sedentariness>() {
            public Sedentariness parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Sedentariness(input, extensionRegistry);
            }
        };
        public static final int REPEAT_FIELD_NUMBER = 1;
        public static final int SATURDAY_FIELD_NUMBER = 7;
        public static final int START_HOUR_FIELD_NUMBER = 9;
        public static final int SUNDAY_FIELD_NUMBER = 8;
        public static final int THRESHOLD_FIELD_NUMBER = 12;
        public static final int THURSDAY_FIELD_NUMBER = 5;
        public static final int TUESDAY_FIELD_NUMBER = 3;
        public static final int WEDNESDAY_FIELD_NUMBER = 4;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int duration_;
        /* access modifiers changed from: private */
        public int endHour_;
        /* access modifiers changed from: private */
        public boolean friday_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean monday_;
        /* access modifiers changed from: private */
        public boolean repeat_;
        /* access modifiers changed from: private */
        public boolean saturday_;
        /* access modifiers changed from: private */
        public int startHour_;
        /* access modifiers changed from: private */
        public boolean sunday_;
        /* access modifiers changed from: private */
        public int threshold_;
        /* access modifiers changed from: private */
        public boolean thursday_;
        /* access modifiers changed from: private */
        public boolean tuesday_;
        /* access modifiers changed from: private */
        public boolean wednesday_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SedentarinessOrBuilder {
            private int bitField0_;
            private int duration_;
            private int endHour_;
            private boolean friday_;
            private boolean monday_;
            private boolean repeat_;
            private boolean saturday_;
            private int startHour_;
            private boolean sunday_;
            private int threshold_;
            private boolean thursday_;
            private boolean tuesday_;
            private boolean wednesday_;

            public static final Descriptor getDescriptor() {
                return SedentarinessOuterClass.internal_static_Sedentariness_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return SedentarinessOuterClass.internal_static_Sedentariness_fieldAccessorTable.ensureFieldAccessorsInitialized(Sedentariness.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (Sedentariness.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.repeat_ = false;
                this.bitField0_ &= -2;
                this.monday_ = false;
                this.bitField0_ &= -3;
                this.tuesday_ = false;
                this.bitField0_ &= -5;
                this.wednesday_ = false;
                this.bitField0_ &= -9;
                this.thursday_ = false;
                this.bitField0_ &= -17;
                this.friday_ = false;
                this.bitField0_ &= -33;
                this.saturday_ = false;
                this.bitField0_ &= -65;
                this.sunday_ = false;
                this.bitField0_ &= -129;
                this.startHour_ = 0;
                this.bitField0_ &= -257;
                this.endHour_ = 0;
                this.bitField0_ &= -513;
                this.duration_ = 0;
                this.bitField0_ &= -1025;
                this.threshold_ = 0;
                this.bitField0_ &= -2049;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return SedentarinessOuterClass.internal_static_Sedentariness_descriptor;
            }

            public Sedentariness getDefaultInstanceForType() {
                return Sedentariness.getDefaultInstance();
            }

            public Sedentariness build() {
                Sedentariness result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public Sedentariness buildPartial() {
                Sedentariness result = new Sedentariness((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.repeat_ = this.repeat_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.monday_ = this.monday_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.tuesday_ = this.tuesday_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.wednesday_ = this.wednesday_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.thursday_ = this.thursday_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.friday_ = this.friday_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.saturday_ = this.saturday_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.sunday_ = this.sunday_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.startHour_ = this.startHour_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.endHour_ = this.endHour_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                result.duration_ = this.duration_;
                if ((from_bitField0_ & 2048) == 2048) {
                    to_bitField0_ |= 2048;
                }
                result.threshold_ = this.threshold_;
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
                if (other instanceof Sedentariness) {
                    return mergeFrom((Sedentariness) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Sedentariness other) {
                if (other != Sedentariness.getDefaultInstance()) {
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
                    if (other.hasStartHour()) {
                        setStartHour(other.getStartHour());
                    }
                    if (other.hasEndHour()) {
                        setEndHour(other.getEndHour());
                    }
                    if (other.hasDuration()) {
                        setDuration(other.getDuration());
                    }
                    if (other.hasThreshold()) {
                        setThreshold(other.getThreshold());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasRepeat() && hasMonday() && hasTuesday() && hasWednesday() && hasThursday() && hasFriday() && hasSaturday() && hasSunday() && hasStartHour() && hasEndHour() && hasDuration() && hasThreshold()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Sedentariness parsedMessage = null;
                try {
                    Sedentariness parsedMessage2 = (Sedentariness) Sedentariness.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    Sedentariness parsedMessage3 = (Sedentariness) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasRepeat() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getRepeat() {
                return this.repeat_;
            }

            public Builder setRepeat(boolean value) {
                this.bitField0_ |= 1;
                this.repeat_ = value;
                onChanged();
                return this;
            }

            public Builder clearRepeat() {
                this.bitField0_ &= -2;
                this.repeat_ = false;
                onChanged();
                return this;
            }

            public boolean hasMonday() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getMonday() {
                return this.monday_;
            }

            public Builder setMonday(boolean value) {
                this.bitField0_ |= 2;
                this.monday_ = value;
                onChanged();
                return this;
            }

            public Builder clearMonday() {
                this.bitField0_ &= -3;
                this.monday_ = false;
                onChanged();
                return this;
            }

            public boolean hasTuesday() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getTuesday() {
                return this.tuesday_;
            }

            public Builder setTuesday(boolean value) {
                this.bitField0_ |= 4;
                this.tuesday_ = value;
                onChanged();
                return this;
            }

            public Builder clearTuesday() {
                this.bitField0_ &= -5;
                this.tuesday_ = false;
                onChanged();
                return this;
            }

            public boolean hasWednesday() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getWednesday() {
                return this.wednesday_;
            }

            public Builder setWednesday(boolean value) {
                this.bitField0_ |= 8;
                this.wednesday_ = value;
                onChanged();
                return this;
            }

            public Builder clearWednesday() {
                this.bitField0_ &= -9;
                this.wednesday_ = false;
                onChanged();
                return this;
            }

            public boolean hasThursday() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getThursday() {
                return this.thursday_;
            }

            public Builder setThursday(boolean value) {
                this.bitField0_ |= 16;
                this.thursday_ = value;
                onChanged();
                return this;
            }

            public Builder clearThursday() {
                this.bitField0_ &= -17;
                this.thursday_ = false;
                onChanged();
                return this;
            }

            public boolean hasFriday() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean getFriday() {
                return this.friday_;
            }

            public Builder setFriday(boolean value) {
                this.bitField0_ |= 32;
                this.friday_ = value;
                onChanged();
                return this;
            }

            public Builder clearFriday() {
                this.bitField0_ &= -33;
                this.friday_ = false;
                onChanged();
                return this;
            }

            public boolean hasSaturday() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean getSaturday() {
                return this.saturday_;
            }

            public Builder setSaturday(boolean value) {
                this.bitField0_ |= 64;
                this.saturday_ = value;
                onChanged();
                return this;
            }

            public Builder clearSaturday() {
                this.bitField0_ &= -65;
                this.saturday_ = false;
                onChanged();
                return this;
            }

            public boolean hasSunday() {
                return (this.bitField0_ & 128) == 128;
            }

            public boolean getSunday() {
                return this.sunday_;
            }

            public Builder setSunday(boolean value) {
                this.bitField0_ |= 128;
                this.sunday_ = value;
                onChanged();
                return this;
            }

            public Builder clearSunday() {
                this.bitField0_ &= -129;
                this.sunday_ = false;
                onChanged();
                return this;
            }

            public boolean hasStartHour() {
                return (this.bitField0_ & 256) == 256;
            }

            public int getStartHour() {
                return this.startHour_;
            }

            public Builder setStartHour(int value) {
                this.bitField0_ |= 256;
                this.startHour_ = value;
                onChanged();
                return this;
            }

            public Builder clearStartHour() {
                this.bitField0_ &= -257;
                this.startHour_ = 0;
                onChanged();
                return this;
            }

            public boolean hasEndHour() {
                return (this.bitField0_ & 512) == 512;
            }

            public int getEndHour() {
                return this.endHour_;
            }

            public Builder setEndHour(int value) {
                this.bitField0_ |= 512;
                this.endHour_ = value;
                onChanged();
                return this;
            }

            public Builder clearEndHour() {
                this.bitField0_ &= -513;
                this.endHour_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDuration() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public int getDuration() {
                return this.duration_;
            }

            public Builder setDuration(int value) {
                this.bitField0_ |= 1024;
                this.duration_ = value;
                onChanged();
                return this;
            }

            public Builder clearDuration() {
                this.bitField0_ &= -1025;
                this.duration_ = 0;
                onChanged();
                return this;
            }

            public boolean hasThreshold() {
                return (this.bitField0_ & 2048) == 2048;
            }

            public int getThreshold() {
                return this.threshold_;
            }

            public Builder setThreshold(int value) {
                this.bitField0_ |= 2048;
                this.threshold_ = value;
                onChanged();
                return this;
            }

            public Builder clearThreshold() {
                this.bitField0_ &= -2049;
                this.threshold_ = 0;
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

        private Sedentariness(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private Sedentariness() {
            this.memoizedIsInitialized = -1;
            this.repeat_ = false;
            this.monday_ = false;
            this.tuesday_ = false;
            this.wednesday_ = false;
            this.thursday_ = false;
            this.friday_ = false;
            this.saturday_ = false;
            this.sunday_ = false;
            this.startHour_ = 0;
            this.endHour_ = 0;
            this.duration_ = 0;
            this.threshold_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Sedentariness(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.repeat_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.monday_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.tuesday_ = input.readBool();
                            break;
                        case 32:
                            this.bitField0_ |= 8;
                            this.wednesday_ = input.readBool();
                            break;
                        case 40:
                            this.bitField0_ |= 16;
                            this.thursday_ = input.readBool();
                            break;
                        case 48:
                            this.bitField0_ |= 32;
                            this.friday_ = input.readBool();
                            break;
                        case 56:
                            this.bitField0_ |= 64;
                            this.saturday_ = input.readBool();
                            break;
                        case 64:
                            this.bitField0_ |= 128;
                            this.sunday_ = input.readBool();
                            break;
                        case 77:
                            this.bitField0_ |= 256;
                            this.startHour_ = input.readFixed32();
                            break;
                        case 85:
                            this.bitField0_ |= 512;
                            this.endHour_ = input.readFixed32();
                            break;
                        case 93:
                            this.bitField0_ |= 1024;
                            this.duration_ = input.readFixed32();
                            break;
                        case 101:
                            this.bitField0_ |= 2048;
                            this.threshold_ = input.readFixed32();
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
            return SedentarinessOuterClass.internal_static_Sedentariness_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return SedentarinessOuterClass.internal_static_Sedentariness_fieldAccessorTable.ensureFieldAccessorsInitialized(Sedentariness.class, Builder.class);
        }

        public boolean hasRepeat() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getRepeat() {
            return this.repeat_;
        }

        public boolean hasMonday() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getMonday() {
            return this.monday_;
        }

        public boolean hasTuesday() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getTuesday() {
            return this.tuesday_;
        }

        public boolean hasWednesday() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getWednesday() {
            return this.wednesday_;
        }

        public boolean hasThursday() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getThursday() {
            return this.thursday_;
        }

        public boolean hasFriday() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getFriday() {
            return this.friday_;
        }

        public boolean hasSaturday() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean getSaturday() {
            return this.saturday_;
        }

        public boolean hasSunday() {
            return (this.bitField0_ & 128) == 128;
        }

        public boolean getSunday() {
            return this.sunday_;
        }

        public boolean hasStartHour() {
            return (this.bitField0_ & 256) == 256;
        }

        public int getStartHour() {
            return this.startHour_;
        }

        public boolean hasEndHour() {
            return (this.bitField0_ & 512) == 512;
        }

        public int getEndHour() {
            return this.endHour_;
        }

        public boolean hasDuration() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public int getDuration() {
            return this.duration_;
        }

        public boolean hasThreshold() {
            return (this.bitField0_ & 2048) == 2048;
        }

        public int getThreshold() {
            return this.threshold_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasRepeat()) {
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
            } else if (!hasStartHour()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasEndHour()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDuration()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasThreshold()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.repeat_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.monday_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.tuesday_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(4, this.wednesday_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(5, this.thursday_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(6, this.friday_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBool(7, this.saturday_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeBool(8, this.sunday_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeFixed32(9, this.startHour_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeFixed32(10, this.endHour_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeFixed32(11, this.duration_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                output.writeFixed32(12, this.threshold_);
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.repeat_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.monday_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.tuesday_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBoolSize(4, this.wednesday_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBoolSize(5, this.thursday_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeBoolSize(6, this.friday_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeBoolSize(7, this.saturday_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeBoolSize(8, this.sunday_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size2 += CodedOutputStream.computeFixed32Size(9, this.startHour_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size2 += CodedOutputStream.computeFixed32Size(10, this.endHour_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size2 += CodedOutputStream.computeFixed32Size(11, this.duration_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                size2 += CodedOutputStream.computeFixed32Size(12, this.threshold_);
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
            if (!(obj instanceof Sedentariness)) {
                return super.equals(obj);
            }
            Sedentariness other = (Sedentariness) obj;
            boolean result13 = 1 != 0 && hasRepeat() == other.hasRepeat();
            if (hasRepeat()) {
                result13 = result13 && getRepeat() == other.getRepeat();
            }
            if (!result13 || hasMonday() != other.hasMonday()) {
                result = false;
            } else {
                result = true;
            }
            if (hasMonday()) {
                if (!result || getMonday() != other.getMonday()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasTuesday() != other.hasTuesday()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasTuesday()) {
                if (!result2 || getTuesday() != other.getTuesday()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasWednesday() != other.hasWednesday()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasWednesday()) {
                if (!result3 || getWednesday() != other.getWednesday()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasThursday() != other.hasThursday()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasThursday()) {
                if (!result4 || getThursday() != other.getThursday()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasFriday() != other.hasFriday()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasFriday()) {
                if (!result5 || getFriday() != other.getFriday()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasSaturday() != other.hasSaturday()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasSaturday()) {
                if (!result6 || getSaturday() != other.getSaturday()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasSunday() != other.hasSunday()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasSunday()) {
                if (!result7 || getSunday() != other.getSunday()) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || hasStartHour() != other.hasStartHour()) {
                result8 = false;
            } else {
                result8 = true;
            }
            if (hasStartHour()) {
                if (!result8 || getStartHour() != other.getStartHour()) {
                    result8 = false;
                } else {
                    result8 = true;
                }
            }
            if (!result8 || hasEndHour() != other.hasEndHour()) {
                result9 = false;
            } else {
                result9 = true;
            }
            if (hasEndHour()) {
                if (!result9 || getEndHour() != other.getEndHour()) {
                    result9 = false;
                } else {
                    result9 = true;
                }
            }
            if (!result9 || hasDuration() != other.hasDuration()) {
                result10 = false;
            } else {
                result10 = true;
            }
            if (hasDuration()) {
                if (!result10 || getDuration() != other.getDuration()) {
                    result10 = false;
                } else {
                    result10 = true;
                }
            }
            if (!result10 || hasThreshold() != other.hasThreshold()) {
                result11 = false;
            } else {
                result11 = true;
            }
            if (hasThreshold()) {
                if (!result11 || getThreshold() != other.getThreshold()) {
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
            if (hasRepeat()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getRepeat());
            }
            if (hasMonday()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getMonday());
            }
            if (hasTuesday()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getTuesday());
            }
            if (hasWednesday()) {
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getWednesday());
            }
            if (hasThursday()) {
                hash = (((hash * 37) + 5) * 53) + Internal.hashBoolean(getThursday());
            }
            if (hasFriday()) {
                hash = (((hash * 37) + 6) * 53) + Internal.hashBoolean(getFriday());
            }
            if (hasSaturday()) {
                hash = (((hash * 37) + 7) * 53) + Internal.hashBoolean(getSaturday());
            }
            if (hasSunday()) {
                hash = (((hash * 37) + 8) * 53) + Internal.hashBoolean(getSunday());
            }
            if (hasStartHour()) {
                hash = (((hash * 37) + 9) * 53) + getStartHour();
            }
            if (hasEndHour()) {
                hash = (((hash * 37) + 10) * 53) + getEndHour();
            }
            if (hasDuration()) {
                hash = (((hash * 37) + 11) * 53) + getDuration();
            }
            if (hasThreshold()) {
                hash = (((hash * 37) + 12) * 53) + getThreshold();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static Sedentariness parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Sedentariness) PARSER.parseFrom(data);
        }

        public static Sedentariness parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Sedentariness) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sedentariness parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Sedentariness) PARSER.parseFrom(data);
        }

        public static Sedentariness parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Sedentariness) PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sedentariness parseFrom(InputStream input) throws IOException {
            return (Sedentariness) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Sedentariness parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Sedentariness) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Sedentariness parseDelimitedFrom(InputStream input) throws IOException {
            return (Sedentariness) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Sedentariness parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Sedentariness) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Sedentariness parseFrom(CodedInputStream input) throws IOException {
            return (Sedentariness) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Sedentariness parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Sedentariness) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Sedentariness prototype) {
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

        public static Sedentariness getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Sedentariness> parser() {
            return PARSER;
        }

        public Parser<Sedentariness> getParserForType() {
            return PARSER;
        }

        public Sedentariness getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class SedtConfirm extends GeneratedMessageV3 implements SedtConfirmOrBuilder {
        private static final SedtConfirm DEFAULT_INSTANCE = new SedtConfirm();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<SedtConfirm> PARSER = new AbstractParser<SedtConfirm>() {
            public SedtConfirm parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SedtConfirm(input, extensionRegistry);
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

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SedtConfirmOrBuilder {
            private int bitField0_;
            private int operation_;
            private boolean ret_;

            public static final Descriptor getDescriptor() {
                return SedentarinessOuterClass.internal_static_SedtConfirm_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return SedentarinessOuterClass.internal_static_SedtConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtConfirm.class, Builder.class);
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
                if (SedtConfirm.alwaysUseFieldBuilders) {
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
                return SedentarinessOuterClass.internal_static_SedtConfirm_descriptor;
            }

            public SedtConfirm getDefaultInstanceForType() {
                return SedtConfirm.getDefaultInstance();
            }

            public SedtConfirm build() {
                SedtConfirm result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SedtConfirm buildPartial() {
                SedtConfirm result = new SedtConfirm((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (other instanceof SedtConfirm) {
                    return mergeFrom((SedtConfirm) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SedtConfirm other) {
                if (other != SedtConfirm.getDefaultInstance()) {
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
                SedtConfirm parsedMessage = null;
                try {
                    SedtConfirm parsedMessage2 = (SedtConfirm) SedtConfirm.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SedtConfirm parsedMessage3 = (SedtConfirm) e.getUnfinishedMessage();
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

            public SedtOperation getOperation() {
                SedtOperation result = SedtOperation.valueOf(this.operation_);
                return result == null ? SedtOperation.SET : result;
            }

            public Builder setOperation(SedtOperation value) {
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

        private SedtConfirm(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SedtConfirm() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
            this.ret_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SedtConfirm(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (SedtOperation.valueOf(rawValue) != null) {
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
            return SedentarinessOuterClass.internal_static_SedtConfirm_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return SedentarinessOuterClass.internal_static_SedtConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtConfirm.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public SedtOperation getOperation() {
            SedtOperation result = SedtOperation.valueOf(this.operation_);
            return result == null ? SedtOperation.SET : result;
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
            if (!(obj instanceof SedtConfirm)) {
                return super.equals(obj);
            }
            SedtConfirm other = (SedtConfirm) obj;
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

        public static SedtConfirm parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SedtConfirm) PARSER.parseFrom(data);
        }

        public static SedtConfirm parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtConfirm parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SedtConfirm) PARSER.parseFrom(data);
        }

        public static SedtConfirm parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtConfirm parseFrom(InputStream input) throws IOException {
            return (SedtConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtConfirm parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtConfirm parseDelimitedFrom(InputStream input) throws IOException {
            return (SedtConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SedtConfirm parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtConfirm parseFrom(CodedInputStream input) throws IOException {
            return (SedtConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtConfirm parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SedtConfirm prototype) {
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

        public static SedtConfirm getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SedtConfirm> parser() {
            return PARSER;
        }

        public Parser<SedtConfirm> getParserForType() {
            return PARSER;
        }

        public SedtConfirm getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class SedtGroup extends GeneratedMessageV3 implements SedtGroupOrBuilder {
        private static final SedtGroup DEFAULT_INSTANCE = new SedtGroup();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<SedtGroup> PARSER = new AbstractParser<SedtGroup>() {
            public SedtGroup parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SedtGroup(input, extensionRegistry);
            }
        };
        public static final int SEDENTARINESS_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public List<Sedentariness> sedentariness_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SedtGroupOrBuilder {
            private int bitField0_;
            private int hash_;
            private RepeatedFieldBuilderV3<Sedentariness, Builder, SedentarinessOrBuilder> sedentarinessBuilder_;
            private List<Sedentariness> sedentariness_;

            public static final Descriptor getDescriptor() {
                return SedentarinessOuterClass.internal_static_SedtGroup_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return SedentarinessOuterClass.internal_static_SedtGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtGroup.class, Builder.class);
            }

            private Builder() {
                this.sedentariness_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.sedentariness_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (SedtGroup.alwaysUseFieldBuilders) {
                    getSedentarinessFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                if (this.sedentarinessBuilder_ == null) {
                    this.sedentariness_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.sedentarinessBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return SedentarinessOuterClass.internal_static_SedtGroup_descriptor;
            }

            public SedtGroup getDefaultInstanceForType() {
                return SedtGroup.getDefaultInstance();
            }

            public SedtGroup build() {
                SedtGroup result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SedtGroup buildPartial() {
                SedtGroup result = new SedtGroup((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if (this.sedentarinessBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.sedentariness_ = Collections.unmodifiableList(this.sedentariness_);
                        this.bitField0_ &= -3;
                    }
                    result.sedentariness_ = this.sedentariness_;
                } else {
                    result.sedentariness_ = this.sedentarinessBuilder_.build();
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
                if (other instanceof SedtGroup) {
                    return mergeFrom((SedtGroup) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SedtGroup other) {
                RepeatedFieldBuilderV3<Sedentariness, Builder, SedentarinessOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != SedtGroup.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (this.sedentarinessBuilder_ == null) {
                        if (!other.sedentariness_.isEmpty()) {
                            if (this.sedentariness_.isEmpty()) {
                                this.sedentariness_ = other.sedentariness_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureSedentarinessIsMutable();
                                this.sedentariness_.addAll(other.sedentariness_);
                            }
                            onChanged();
                        }
                    } else if (!other.sedentariness_.isEmpty()) {
                        if (this.sedentarinessBuilder_.isEmpty()) {
                            this.sedentarinessBuilder_.dispose();
                            this.sedentarinessBuilder_ = null;
                            this.sedentariness_ = other.sedentariness_;
                            this.bitField0_ &= -3;
                            if (SedtGroup.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getSedentarinessFieldBuilder();
                            }
                            this.sedentarinessBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.sedentarinessBuilder_.addAllMessages(other.sedentariness_);
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
                for (int i = 0; i < getSedentarinessCount(); i++) {
                    if (!getSedentariness(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                SedtGroup parsedMessage = null;
                try {
                    SedtGroup parsedMessage2 = (SedtGroup) SedtGroup.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SedtGroup parsedMessage3 = (SedtGroup) e.getUnfinishedMessage();
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

            private void ensureSedentarinessIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.sedentariness_ = new ArrayList(this.sedentariness_);
                    this.bitField0_ |= 2;
                }
            }

            public List<Sedentariness> getSedentarinessList() {
                if (this.sedentarinessBuilder_ == null) {
                    return Collections.unmodifiableList(this.sedentariness_);
                }
                return this.sedentarinessBuilder_.getMessageList();
            }

            public int getSedentarinessCount() {
                if (this.sedentarinessBuilder_ == null) {
                    return this.sedentariness_.size();
                }
                return this.sedentarinessBuilder_.getCount();
            }

            public Sedentariness getSedentariness(int index) {
                if (this.sedentarinessBuilder_ == null) {
                    return (Sedentariness) this.sedentariness_.get(index);
                }
                return (Sedentariness) this.sedentarinessBuilder_.getMessage(index);
            }

            public Builder setSedentariness(int index, Sedentariness value) {
                if (this.sedentarinessBuilder_ != null) {
                    this.sedentarinessBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setSedentariness(int index, Builder builderForValue) {
                if (this.sedentarinessBuilder_ == null) {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.sedentarinessBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addSedentariness(Sedentariness value) {
                if (this.sedentarinessBuilder_ != null) {
                    this.sedentarinessBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addSedentariness(int index, Sedentariness value) {
                if (this.sedentarinessBuilder_ != null) {
                    this.sedentarinessBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addSedentariness(Builder builderForValue) {
                if (this.sedentarinessBuilder_ == null) {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.sedentarinessBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addSedentariness(int index, Builder builderForValue) {
                if (this.sedentarinessBuilder_ == null) {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.sedentarinessBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllSedentariness(Iterable<? extends Sedentariness> values) {
                if (this.sedentarinessBuilder_ == null) {
                    ensureSedentarinessIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.sedentariness_);
                    onChanged();
                } else {
                    this.sedentarinessBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearSedentariness() {
                if (this.sedentarinessBuilder_ == null) {
                    this.sedentariness_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.sedentarinessBuilder_.clear();
                }
                return this;
            }

            public Builder removeSedentariness(int index) {
                if (this.sedentarinessBuilder_ == null) {
                    ensureSedentarinessIsMutable();
                    this.sedentariness_.remove(index);
                    onChanged();
                } else {
                    this.sedentarinessBuilder_.remove(index);
                }
                return this;
            }

            public Builder getSedentarinessBuilder(int index) {
                return (Builder) getSedentarinessFieldBuilder().getBuilder(index);
            }

            public SedentarinessOrBuilder getSedentarinessOrBuilder(int index) {
                if (this.sedentarinessBuilder_ == null) {
                    return (SedentarinessOrBuilder) this.sedentariness_.get(index);
                }
                return (SedentarinessOrBuilder) this.sedentarinessBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends SedentarinessOrBuilder> getSedentarinessOrBuilderList() {
                if (this.sedentarinessBuilder_ != null) {
                    return this.sedentarinessBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.sedentariness_);
            }

            public Builder addSedentarinessBuilder() {
                return (Builder) getSedentarinessFieldBuilder().addBuilder(Sedentariness.getDefaultInstance());
            }

            public Builder addSedentarinessBuilder(int index) {
                return (Builder) getSedentarinessFieldBuilder().addBuilder(index, Sedentariness.getDefaultInstance());
            }

            public List<Builder> getSedentarinessBuilderList() {
                return getSedentarinessFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Sedentariness, Builder, SedentarinessOrBuilder> getSedentarinessFieldBuilder() {
                if (this.sedentarinessBuilder_ == null) {
                    this.sedentarinessBuilder_ = new RepeatedFieldBuilderV3<>(this.sedentariness_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.sedentariness_ = null;
                }
                return this.sedentarinessBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private SedtGroup(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SedtGroup() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.sedentariness_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SedtGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.sedentariness_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.sedentariness_.add(input.readMessage(Sedentariness.PARSER, extensionRegistry));
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
                        this.sedentariness_ = Collections.unmodifiableList(this.sedentariness_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.sedentariness_ = Collections.unmodifiableList(this.sedentariness_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return SedentarinessOuterClass.internal_static_SedtGroup_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return SedentarinessOuterClass.internal_static_SedtGroup_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtGroup.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<Sedentariness> getSedentarinessList() {
            return this.sedentariness_;
        }

        public List<? extends SedentarinessOrBuilder> getSedentarinessOrBuilderList() {
            return this.sedentariness_;
        }

        public int getSedentarinessCount() {
            return this.sedentariness_.size();
        }

        public Sedentariness getSedentariness(int index) {
            return (Sedentariness) this.sedentariness_.get(index);
        }

        public SedentarinessOrBuilder getSedentarinessOrBuilder(int index) {
            return (SedentarinessOrBuilder) this.sedentariness_.get(index);
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
            for (int i = 0; i < getSedentarinessCount(); i++) {
                if (!getSedentariness(i).isInitialized()) {
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
            for (int i = 0; i < this.sedentariness_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.sedentariness_.get(i));
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
            for (int i = 0; i < this.sedentariness_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.sedentariness_.get(i));
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
            if (!(obj instanceof SedtGroup)) {
                return super.equals(obj);
            }
            SedtGroup other = (SedtGroup) obj;
            boolean result3 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result3 = result3 && getHash() == other.getHash();
            }
            if (!result3 || !getSedentarinessList().equals(other.getSedentarinessList())) {
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
            if (getSedentarinessCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getSedentarinessList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static SedtGroup parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SedtGroup) PARSER.parseFrom(data);
        }

        public static SedtGroup parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtGroup parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SedtGroup) PARSER.parseFrom(data);
        }

        public static SedtGroup parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtGroup) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtGroup parseFrom(InputStream input) throws IOException {
            return (SedtGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtGroup parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtGroup parseDelimitedFrom(InputStream input) throws IOException {
            return (SedtGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SedtGroup parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtGroup) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtGroup parseFrom(CodedInputStream input) throws IOException {
            return (SedtGroup) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtGroup parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtGroup) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SedtGroup prototype) {
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

        public static SedtGroup getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SedtGroup> parser() {
            return PARSER;
        }

        public Parser<SedtGroup> getParserForType() {
            return PARSER;
        }

        public SedtGroup getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class SedtNotification extends GeneratedMessageV3 implements SedtNotificationOrBuilder {
        private static final SedtNotification DEFAULT_INSTANCE = new SedtNotification();
        public static final int GROUP_FIELD_NUMBER = 2;
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<SedtNotification> PARSER = new AbstractParser<SedtNotification>() {
            public SedtNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SedtNotification(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public SedtGroup group_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SedtNotificationOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<SedtGroup, Builder, SedtGroupOrBuilder> groupBuilder_;
            private SedtGroup group_;
            private int operation_;

            public static final Descriptor getDescriptor() {
                return SedentarinessOuterClass.internal_static_SedtNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return SedentarinessOuterClass.internal_static_SedtNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtNotification.class, Builder.class);
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
                if (SedtNotification.alwaysUseFieldBuilders) {
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
                return SedentarinessOuterClass.internal_static_SedtNotification_descriptor;
            }

            public SedtNotification getDefaultInstanceForType() {
                return SedtNotification.getDefaultInstance();
            }

            public SedtNotification build() {
                SedtNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SedtNotification buildPartial() {
                SedtNotification result = new SedtNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                    result.group_ = (SedtGroup) this.groupBuilder_.build();
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
                if (other instanceof SedtNotification) {
                    return mergeFrom((SedtNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SedtNotification other) {
                if (other != SedtNotification.getDefaultInstance()) {
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
                SedtNotification parsedMessage = null;
                try {
                    SedtNotification parsedMessage2 = (SedtNotification) SedtNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SedtNotification parsedMessage3 = (SedtNotification) e.getUnfinishedMessage();
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

            public SedtOperation getOperation() {
                SedtOperation result = SedtOperation.valueOf(this.operation_);
                return result == null ? SedtOperation.SET : result;
            }

            public Builder setOperation(SedtOperation value) {
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

            public SedtGroup getGroup() {
                if (this.groupBuilder_ == null) {
                    return this.group_ == null ? SedtGroup.getDefaultInstance() : this.group_;
                }
                return (SedtGroup) this.groupBuilder_.getMessage();
            }

            public Builder setGroup(SedtGroup value) {
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

            public Builder mergeGroup(SedtGroup value) {
                if (this.groupBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.group_ == null || this.group_ == SedtGroup.getDefaultInstance()) {
                        this.group_ = value;
                    } else {
                        this.group_ = SedtGroup.newBuilder(this.group_).mergeFrom(value).buildPartial();
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

            public SedtGroupOrBuilder getGroupOrBuilder() {
                if (this.groupBuilder_ != null) {
                    return (SedtGroupOrBuilder) this.groupBuilder_.getMessageOrBuilder();
                }
                return this.group_ == null ? SedtGroup.getDefaultInstance() : this.group_;
            }

            private SingleFieldBuilderV3<SedtGroup, Builder, SedtGroupOrBuilder> getGroupFieldBuilder() {
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

        private SedtNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SedtNotification() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SedtNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (SedtOperation.valueOf(rawValue) != null) {
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
                            this.group_ = (SedtGroup) input.readMessage(SedtGroup.PARSER, extensionRegistry);
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
            return SedentarinessOuterClass.internal_static_SedtNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return SedentarinessOuterClass.internal_static_SedtNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtNotification.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public SedtOperation getOperation() {
            SedtOperation result = SedtOperation.valueOf(this.operation_);
            return result == null ? SedtOperation.SET : result;
        }

        public boolean hasGroup() {
            return (this.bitField0_ & 2) == 2;
        }

        public SedtGroup getGroup() {
            return this.group_ == null ? SedtGroup.getDefaultInstance() : this.group_;
        }

        public SedtGroupOrBuilder getGroupOrBuilder() {
            return this.group_ == null ? SedtGroup.getDefaultInstance() : this.group_;
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
            if (!(obj instanceof SedtNotification)) {
                return super.equals(obj);
            }
            SedtNotification other = (SedtNotification) obj;
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

        public static SedtNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SedtNotification) PARSER.parseFrom(data);
        }

        public static SedtNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SedtNotification) PARSER.parseFrom(data);
        }

        public static SedtNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtNotification parseFrom(InputStream input) throws IOException {
            return (SedtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (SedtNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SedtNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtNotification parseFrom(CodedInputStream input) throws IOException {
            return (SedtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SedtNotification prototype) {
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

        public static SedtNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SedtNotification> parser() {
            return PARSER;
        }

        public Parser<SedtNotification> getParserForType() {
            return PARSER;
        }

        public SedtNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum SedtOperation implements ProtocolMessageEnum {
        SET(0),
        CLEAR(1);
        
        public static final int CLEAR_VALUE = 1;
        public static final int SET_VALUE = 0;
        private static final SedtOperation[] VALUES = null;
        private static final EnumLiteMap<SedtOperation> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<SedtOperation>() {
                public SedtOperation findValueByNumber(int number) {
                    return SedtOperation.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static SedtOperation valueOf(int value2) {
            return forNumber(value2);
        }

        public static SedtOperation forNumber(int value2) {
            switch (value2) {
                case 0:
                    return SET;
                case 1:
                    return CLEAR;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<SedtOperation> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) SedentarinessOuterClass.getDescriptor().getEnumTypes().get(0);
        }

        public static SedtOperation valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private SedtOperation(int value2) {
            this.value = value2;
        }
    }

    public static final class SedtSubscriber extends GeneratedMessageV3 implements SedtSubscriberOrBuilder {
        public static final int CONFIRM_FIELD_NUMBER = 2;
        private static final SedtSubscriber DEFAULT_INSTANCE = new SedtSubscriber();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<SedtSubscriber> PARSER = new AbstractParser<SedtSubscriber>() {
            public SedtSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SedtSubscriber(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public SedtConfirm confirm_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SedtSubscriberOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<SedtConfirm, Builder, SedtConfirmOrBuilder> confirmBuilder_;
            private SedtConfirm confirm_;
            private int hash_;

            public static final Descriptor getDescriptor() {
                return SedentarinessOuterClass.internal_static_SedtSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return SedentarinessOuterClass.internal_static_SedtSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtSubscriber.class, Builder.class);
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
                if (SedtSubscriber.alwaysUseFieldBuilders) {
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
                return SedentarinessOuterClass.internal_static_SedtSubscriber_descriptor;
            }

            public SedtSubscriber getDefaultInstanceForType() {
                return SedtSubscriber.getDefaultInstance();
            }

            public SedtSubscriber build() {
                SedtSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public SedtSubscriber buildPartial() {
                SedtSubscriber result = new SedtSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                    result.confirm_ = (SedtConfirm) this.confirmBuilder_.build();
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
                if (other instanceof SedtSubscriber) {
                    return mergeFrom((SedtSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SedtSubscriber other) {
                if (other != SedtSubscriber.getDefaultInstance()) {
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
                SedtSubscriber parsedMessage = null;
                try {
                    SedtSubscriber parsedMessage2 = (SedtSubscriber) SedtSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    SedtSubscriber parsedMessage3 = (SedtSubscriber) e.getUnfinishedMessage();
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

            public SedtConfirm getConfirm() {
                if (this.confirmBuilder_ == null) {
                    return this.confirm_ == null ? SedtConfirm.getDefaultInstance() : this.confirm_;
                }
                return (SedtConfirm) this.confirmBuilder_.getMessage();
            }

            public Builder setConfirm(SedtConfirm value) {
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

            public Builder mergeConfirm(SedtConfirm value) {
                if (this.confirmBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.confirm_ == null || this.confirm_ == SedtConfirm.getDefaultInstance()) {
                        this.confirm_ = value;
                    } else {
                        this.confirm_ = SedtConfirm.newBuilder(this.confirm_).mergeFrom(value).buildPartial();
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

            public SedtConfirmOrBuilder getConfirmOrBuilder() {
                if (this.confirmBuilder_ != null) {
                    return (SedtConfirmOrBuilder) this.confirmBuilder_.getMessageOrBuilder();
                }
                return this.confirm_ == null ? SedtConfirm.getDefaultInstance() : this.confirm_;
            }

            private SingleFieldBuilderV3<SedtConfirm, Builder, SedtConfirmOrBuilder> getConfirmFieldBuilder() {
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

        private SedtSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private SedtSubscriber() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SedtSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.confirm_ = (SedtConfirm) input.readMessage(SedtConfirm.PARSER, extensionRegistry);
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
            return SedentarinessOuterClass.internal_static_SedtSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return SedentarinessOuterClass.internal_static_SedtSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(SedtSubscriber.class, Builder.class);
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

        public SedtConfirm getConfirm() {
            return this.confirm_ == null ? SedtConfirm.getDefaultInstance() : this.confirm_;
        }

        public SedtConfirmOrBuilder getConfirmOrBuilder() {
            return this.confirm_ == null ? SedtConfirm.getDefaultInstance() : this.confirm_;
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
            if (!(obj instanceof SedtSubscriber)) {
                return super.equals(obj);
            }
            SedtSubscriber other = (SedtSubscriber) obj;
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

        public static SedtSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SedtSubscriber) PARSER.parseFrom(data);
        }

        public static SedtSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SedtSubscriber) PARSER.parseFrom(data);
        }

        public static SedtSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SedtSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SedtSubscriber parseFrom(InputStream input) throws IOException {
            return (SedtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (SedtSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SedtSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SedtSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (SedtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SedtSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SedtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SedtSubscriber prototype) {
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

        public static SedtSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SedtSubscriber> parser() {
            return PARSER;
        }

        public Parser<SedtSubscriber> getParserForType() {
            return PARSER;
        }

        public SedtSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface SedentarinessOrBuilder extends MessageOrBuilder {
        int getDuration();

        int getEndHour();

        boolean getFriday();

        boolean getMonday();

        boolean getRepeat();

        boolean getSaturday();

        int getStartHour();

        boolean getSunday();

        int getThreshold();

        boolean getThursday();

        boolean getTuesday();

        boolean getWednesday();

        boolean hasDuration();

        boolean hasEndHour();

        boolean hasFriday();

        boolean hasMonday();

        boolean hasRepeat();

        boolean hasSaturday();

        boolean hasStartHour();

        boolean hasSunday();

        boolean hasThreshold();

        boolean hasThursday();

        boolean hasTuesday();

        boolean hasWednesday();
    }

    public interface SedtConfirmOrBuilder extends MessageOrBuilder {
        SedtOperation getOperation();

        boolean getRet();

        boolean hasOperation();

        boolean hasRet();
    }

    public interface SedtGroupOrBuilder extends MessageOrBuilder {
        int getHash();

        Sedentariness getSedentariness(int i);

        int getSedentarinessCount();

        List<Sedentariness> getSedentarinessList();

        SedentarinessOrBuilder getSedentarinessOrBuilder(int i);

        List<? extends SedentarinessOrBuilder> getSedentarinessOrBuilderList();

        boolean hasHash();
    }

    public interface SedtNotificationOrBuilder extends MessageOrBuilder {
        SedtGroup getGroup();

        SedtGroupOrBuilder getGroupOrBuilder();

        SedtOperation getOperation();

        boolean hasGroup();

        boolean hasOperation();
    }

    public interface SedtSubscriberOrBuilder extends MessageOrBuilder {
        SedtConfirm getConfirm();

        SedtConfirmOrBuilder getConfirmOrBuilder();

        int getHash();

        boolean hasConfirm();

        boolean hasHash();
    }

    private SedentarinessOuterClass() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0013sedentariness.proto\"â\u0001\n\rSedentariness\u0012\u000e\n\u0006repeat\u0018\u0001 \u0002(\b\u0012\u000e\n\u0006monday\u0018\u0002 \u0002(\b\u0012\u000f\n\u0007tuesday\u0018\u0003 \u0002(\b\u0012\u0011\n\twednesday\u0018\u0004 \u0002(\b\u0012\u0010\n\bthursday\u0018\u0005 \u0002(\b\u0012\u000e\n\u0006friday\u0018\u0006 \u0002(\b\u0012\u0010\n\bsaturday\u0018\u0007 \u0002(\b\u0012\u000e\n\u0006sunday\u0018\b \u0002(\b\u0012\u0012\n\nstart_hour\u0018\t \u0002(\u0007\u0012\u0010\n\bend_hour\u0018\n \u0002(\u0007\u0012\u0010\n\bduration\u0018\u000b \u0002(\u0007\u0012\u0011\n\tthreshold\u0018\f \u0002(\u0007\"@\n\tSedtGroup\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012%\n\rsedentariness\u0018\u0002 \u0003(\u000b2\u000e.Sedentariness\"P\n\u0010SedtNotification\u0012!\n\toperation\u0018\u0001 \u0002(\u000e2\u000e.SedtOperation\u0012\u0019\n\u0005group\u0018\u0002 \u0001(\u000b2\n.SedtGroup\"=", "\n\u000bSedtConfirm\u0012!\n\toperation\u0018\u0001 \u0002(\u000e2\u000e.SedtOperation\u0012\u000b\n\u0003ret\u0018\u0002 \u0002(\b\"=\n\u000eSedtSubscriber\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u001d\n\u0007confirm\u0018\u0002 \u0001(\u000b2\f.SedtConfirm*#\n\rSedtOperation\u0012\u0007\n\u0003SET\u0010\u0000\u0012\t\n\u0005CLEAR\u0010\u0001"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                SedentarinessOuterClass.descriptor = root;
                return null;
            }
        });
    }
}
