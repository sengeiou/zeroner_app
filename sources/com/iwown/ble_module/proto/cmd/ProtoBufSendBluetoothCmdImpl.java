package com.iwown.ble_module.proto.cmd;

import android.content.Context;
import com.google.protobuf.ByteString;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmClock;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmGroup;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmIDList;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmNotification;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmOperation;
import com.iwown.ble_module.proto.base.CalendarOuterClass.Calendar;
import com.iwown.ble_module.proto.base.CalendarOuterClass.CalendarGroup;
import com.iwown.ble_module.proto.base.CalendarOuterClass.CalendarNotification;
import com.iwown.ble_module.proto.base.CalendarOuterClass.CalendarOperation;
import com.iwown.ble_module.proto.base.DataInfo.DataInfoRequest;
import com.iwown.ble_module.proto.base.DevInfo.DeviceInfoRequest;
import com.iwown.ble_module.proto.base.DevInfo.DeviceInfoRequest.Builder;
import com.iwown.ble_module.proto.base.DeviceConf.DeviceConfNotification;
import com.iwown.ble_module.proto.base.DeviceConf.DeviceLanuage;
import com.iwown.ble_module.proto.base.DeviceConf.DeviceLcdGs;
import com.iwown.ble_module.proto.base.FilesUpdate.FUDataRequest;
import com.iwown.ble_module.proto.base.FilesUpdate.FUExitRequest;
import com.iwown.ble_module.proto.base.FilesUpdate.FUFileInfo;
import com.iwown.ble_module.proto.base.FilesUpdate.FUInitRequest;
import com.iwown.ble_module.proto.base.FilesUpdate.FUType;
import com.iwown.ble_module.proto.base.FilesUpdate.FilesUpdateRequest;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisBlock;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisDataType;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisITSync;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisOperation;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisStartSync;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisStopSync;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisSubscriber;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorConf;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorConfNotification;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorOperation;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorVibrate;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.VibrateCnf;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.VibrateType;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgFilter;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgFilter.ID;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgHandler;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgHandler.Policy;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgHandler.Timing;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgNotification;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgNotify;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgNotify.Option;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgNotify.Status;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgNotify.Type;
import com.iwown.ble_module.proto.base.PeerInfo.AfConf;
import com.iwown.ble_module.proto.base.PeerInfo.BpCaliConf;
import com.iwown.ble_module.proto.base.PeerInfo.GnssConf;
import com.iwown.ble_module.proto.base.PeerInfo.GoalConf;
import com.iwown.ble_module.proto.base.PeerInfo.HrAlarmConf;
import com.iwown.ble_module.proto.base.PeerInfo.PeerInfoNotification;
import com.iwown.ble_module.proto.base.PeerInfo.PeerInfoNotification.PeerStatus;
import com.iwown.ble_module.proto.base.PeerInfo.PeerInfoNotification.PeerType;
import com.iwown.ble_module.proto.base.PeerInfo.UserConf;
import com.iwown.ble_module.proto.base.RealtimeData.DateTime;
import com.iwown.ble_module.proto.base.RealtimeData.RtMode;
import com.iwown.ble_module.proto.base.RealtimeData.RtSubscriber;
import com.iwown.ble_module.proto.base.RealtimeData.RtSync;
import com.iwown.ble_module.proto.base.RealtimeData.RtTime;
import com.iwown.ble_module.proto.base.SedentarinessOuterClass.Sedentariness;
import com.iwown.ble_module.proto.base.SedentarinessOuterClass.SedtGroup;
import com.iwown.ble_module.proto.base.SedentarinessOuterClass.SedtNotification;
import com.iwown.ble_module.proto.base.SedentarinessOuterClass.SedtOperation;
import com.iwown.ble_module.proto.base.WeatherOuterClass.Weather;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherDesc;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherGroup;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherNotification;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherOperation;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherType;
import com.iwown.ble_module.proto.ble.Constants.ProtoBufUUID;
import com.iwown.ble_module.proto.ble.ProtoBle;
import com.iwown.ble_module.proto.model.WeatherEvent;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.utils.Util;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.Date;
import java.util.List;

public class ProtoBufSendBluetoothCmdImpl {
    private static volatile ProtoBufSendBluetoothCmdImpl instance;

    public static ProtoBufSendBluetoothCmdImpl getInstance() {
        if (instance == null) {
            synchronized (ProtoBufSendBluetoothCmdImpl.class) {
                if (instance == null) {
                    instance = new ProtoBufSendBluetoothCmdImpl();
                }
            }
        }
        return instance;
    }

    public byte[] getHardwareFeatures() {
        Builder builder = DeviceInfoRequest.newBuilder();
        builder.setReserved(1);
        return Util.getMergeHeadLenBytes(0, 0, builder.build().toByteArray());
    }

    public byte[] setTime() {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        DateTime.Builder builder1 = DateTime.newBuilder();
        RtTime.Builder builder2 = RtTime.newBuilder();
        builder2.setSeconds((int) ((System.currentTimeMillis() / 1000) + ((long) (Util.getTimeZone() * 3600))));
        builder1.setDateTime(builder2).setTimeZone(Util.getTimeZone());
        return Util.getMergeHeadLenBytes(0, 1, builder.setDateTime(builder1).build().toByteArray());
    }

    public byte[] setPeerTypeAndStatus() {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        builder.setPeerType(PeerType.APP_ANDROID).setPeerStatus(PeerStatus.APP_FOREGROUND);
        return Util.getMergeHeadLenBytes(0, 1, builder.build().toByteArray());
    }

    public byte[] setHeartAlarm(boolean enable, int hrH, int hrL, int second, int interval) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        HrAlarmConf.Builder builder1 = HrAlarmConf.newBuilder();
        builder1.setHash(new Date().hashCode()).setEnable(enable).setThsHigh(hrH).setThsLow(hrL).setTimeout(second).setInterval(interval).build();
        builder.setHrAlarmConf(builder1);
        return Util.getMergeHeadLenBytes(0, 1, builder.build().toByteArray());
    }

    public byte[] setUserConf(int height, int weight, boolean gender, int age, int walk, int run) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        UserConf.Builder builder1 = UserConf.newBuilder();
        builder1.setHash(new Date().hashCode()).setHeight(height).setWeight(weight).setGender(gender).setAge(age).setCalibWalk(walk).setCalibRun(run).build();
        builder.setUserConf(builder1);
        return Util.getMergeHeadLenBytes(0, 1, builder.build().toByteArray());
    }

    public byte[] setGoalConf(int calorie, int step, int distance) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        GoalConf.Builder builder1 = GoalConf.newBuilder();
        builder1.setHash(new Date().hashCode()).setCalorie(calorie).setDistance(distance).setStep(step).build();
        builder.setGoalConf(builder1);
        return Util.getMergeHeadLenBytes(0, 1, builder.build().toByteArray());
    }

    public byte[] setPeerInfo(boolean enable, int hrH, int hrL, int second, int interval, int height, int weight, boolean gender, int age, int walk, int run) {
        int hash = new Date().hashCode();
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        DateTime.Builder builder1 = DateTime.newBuilder();
        RtTime.Builder b1 = RtTime.newBuilder();
        b1.setSeconds((int) ((System.currentTimeMillis() / 1000) + ((long) (Util.getTimeZone() * 3600))));
        builder1.setDateTime(b1).setTimeZone(Util.getTimeZone());
        HrAlarmConf.Builder builder3 = HrAlarmConf.newBuilder();
        builder3.setHash(hash).setEnable(enable).setThsHigh(hrH).setThsLow(hrL).setTimeout(second).setInterval(interval);
        UserConf.Builder builder4 = UserConf.newBuilder();
        builder4.setHash(hash).setHeight(height).setWeight(weight).setGender(gender).setAge(age).setCalibWalk(walk).setCalibRun(run);
        return Util.getMergeHeadLenBytes(0, 1, builder.setDateTime(builder1).setHrAlarmConf(builder3).setUserConf(builder4).build().toByteArray());
    }

    public byte[] setGnssConf(int altitude, String latitude, String longitude) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        GnssConf.Builder builder1 = GnssConf.newBuilder();
        builder1.setAltitude((float) altitude).setLatitude(Float.parseFloat(latitude)).setLongitude(Float.parseFloat(longitude)).build();
        builder.setGnssConf(builder1);
        return Util.getMergeHeadLenBytes(0, 1, builder.build().toByteArray());
    }

    public byte[] setBpCaliConf(int[] blood) {
        if (blood.length != 6) {
            KLog.d("参数有误");
            return new byte[0];
        }
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        BpCaliConf.Builder builder1 = BpCaliConf.newBuilder();
        builder1.setHash(new Date().hashCode()).setSrcSbp(blood[0]).setSrcDbp(blood[1]).setDstSbp(blood[2]).setDstDbp(blood[3]).setDifSbp(blood[4]).setDifDbp(blood[5]).build();
        return Util.getMergeHeadLenBytes(0, 1, builder.setBpcaliConf(builder1).build().toByteArray());
    }

    public byte[] setBpCaliConf(int src_sbp, int src_dbp, int dst_sbp, int dst_dbp, int dif_sbp, int dif_dbp) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        BpCaliConf.Builder builder1 = BpCaliConf.newBuilder();
        builder1.setHash(new Date().hashCode()).setSrcSbp(src_sbp).setSrcDbp(src_dbp).setDstSbp(dst_sbp).setDstDbp(dst_dbp).setDifSbp(dif_sbp).setDifDbp(dif_dbp).build();
        return Util.getMergeHeadLenBytes(0, 1, builder.setBpcaliConf(builder1).build().toByteArray());
    }

    public byte[] set24af(boolean autoRun) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        AfConf.Builder afConf = AfConf.newBuilder();
        afConf.setHash(new Date().hashCode()).setAutoRun(autoRun).setInterval(0);
        return Util.getMergeHeadLenBytes(0, 1, builder.setAfConf(afConf).build().toByteArray());
    }

    public byte[] set24af(boolean autoRun, int minutes) {
        PeerInfoNotification.Builder builder = PeerInfoNotification.newBuilder();
        AfConf.Builder afConf = AfConf.newBuilder();
        afConf.setHash(new Date().hashCode()).setAutoRun(autoRun).setInterval(minutes);
        return Util.getMergeHeadLenBytes(0, 1, builder.setAfConf(afConf).build().toByteArray());
    }

    public byte[] setMsgNotificationTime(boolean policy, int startHour, int endHour, int startMin, int endMin) {
        int i;
        MsgNotification.Builder builder = MsgNotification.newBuilder();
        MsgHandler.Builder builder1 = MsgHandler.newBuilder();
        Timing.Builder builder2 = Timing.newBuilder();
        builder2.setStartHour(startHour).setEndHour(endHour).setStartMinute(startMin).setEndMinute(endMin).build();
        MsgHandler.Builder hash = builder1.setHash(new Date().hashCode());
        if (policy) {
            i = 1;
        } else {
            i = 0;
        }
        hash.setPolicy(Policy.forNumber(i)).setTiming(builder2).build();
        builder.setHandler(builder1);
        return Util.getMergeHeadLenBytes(0, 2, builder.build().toByteArray());
    }

    public byte[] setMsgNotificationFilter(String id) {
        MsgNotification.Builder builder = MsgNotification.newBuilder();
        MsgFilter.Builder builder1 = MsgFilter.newBuilder();
        ID.Builder builder2 = ID.newBuilder();
        builder2.setId(id);
        builder1.setHash(new Date().hashCode());
        builder1.addId(builder2);
        builder.setFilter(builder1);
        return Util.getMergeHeadLenBytes(0, 2, builder.build().toByteArray());
    }

    public byte[] setMsgNotificationNotifyByCall(int id, int type, boolean accept, boolean reject, boolean mute, String title, String detail) {
        MsgNotification.Builder builder = MsgNotification.newBuilder();
        MsgNotify.Builder builder1 = MsgNotify.newBuilder();
        Option.Builder builder2 = Option.newBuilder();
        builder2.setAccept(accept).setReject(reject).setMute(mute).build();
        builder1.setId(id).setType(Type.CALL).setStatus(Status.forNumber(type)).setOption(builder2).setTitle(title).setDetail(detail).build();
        builder.setNotify(builder1);
        return Util.getMergeHeadLenBytes(0, 2, builder.build().toByteArray());
    }

    public byte[] setMsgNotificationNotifyBySMS(int id, String title, String detail) {
        MsgNotification.Builder builder = MsgNotification.newBuilder();
        MsgNotify.Builder builder1 = MsgNotify.newBuilder();
        Option.Builder builder2 = Option.newBuilder();
        builder2.setAccept(false).setReject(false).setMute(false).build();
        builder1.setId(id).setType(Type.SMS).setStatus(Status.ADDED).setOption(builder2).setTitle(title).setDetail(detail).build();
        builder.setNotify(builder1);
        return Util.getMergeHeadLenBytes(0, 2, builder.build().toByteArray());
    }

    public byte[] setWeather(int timeMills, int weatherDesc, int weatherType, int degreeMax, int degreeMin, int pm2p5) {
        WeatherNotification.Builder builder = WeatherNotification.newBuilder();
        WeatherGroup.Builder builder1 = WeatherGroup.newBuilder();
        Weather.Builder builder2 = Weather.newBuilder();
        RtTime.Builder builder3 = RtTime.newBuilder();
        builder3.setSeconds((Util.getTimeZone() * 3600) + timeMills);
        builder2.setDateTime(builder3).setType(WeatherType.forNumber(weatherType)).setDesc(WeatherDesc.forNumber(weatherDesc)).setDegreeMax(degreeMax).setDegreeMin(degreeMin).setPm2P5(pm2p5);
        builder1.setHash(new Date().hashCode()).addData(builder2);
        return Util.getMergeHeadLenBytes(0, 3, builder.setOperation(WeatherOperation.ADD).setGroup(builder1).build().toByteArray());
    }

    public byte[] setWeather(List<WeatherEvent> weatherEvents) {
        if (weatherEvents == null || weatherEvents.size() == 0) {
            return new byte[0];
        }
        WeatherNotification.Builder builder = WeatherNotification.newBuilder();
        WeatherGroup.Builder builder1 = WeatherGroup.newBuilder();
        Weather.Builder builder2 = Weather.newBuilder();
        RtTime.Builder builder3 = RtTime.newBuilder();
        for (WeatherEvent weather : weatherEvents) {
            builder3.setSeconds(weather.getTimeMills() + (Util.getTimeZone() * 3600));
            builder2.setDateTime(builder3).setType(WeatherType.forNumber(weather.getWeatherType())).setDesc(WeatherDesc.forNumber(weather.getWeatherDesc())).setDegreeMax(weather.getDegreeMax()).setDegreeMin(weather.getDegreeMin()).setPm2P5(weather.getPm2p5());
            builder1.setHash(new Date().hashCode()).addData(builder2);
        }
        return Util.getMergeHeadLenBytes(0, 3, builder.setOperation(WeatherOperation.ADD).setGroup(builder1).build().toByteArray());
    }

    public byte[] clearWeather() {
        return Util.getMergeHeadLenBytes(0, 3, WeatherNotification.newBuilder().setOperation(WeatherOperation.CLEAR).build().toByteArray());
    }

    public byte[] addAlarm(int id, boolean repeat, int week, int hour, int min, String text) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7 = true;
        AlarmNotification.Builder builder = AlarmNotification.newBuilder();
        AlarmGroup.Builder builder1 = AlarmGroup.newBuilder();
        AlarmClock.Builder builder2 = AlarmClock.newBuilder();
        AlarmClock.Builder repeat2 = builder2.setId(id).setRepeat(repeat);
        if ((week & 1) == 1) {
            z = true;
        } else {
            z = false;
        }
        AlarmClock.Builder sunday = repeat2.setSunday(z);
        if (((week & 2) >> 1) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        AlarmClock.Builder saturday = sunday.setSaturday(z2);
        if (((week & 4) >> 2) == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        AlarmClock.Builder friday = saturday.setFriday(z3);
        if (((week & 8) >> 3) == 1) {
            z4 = true;
        } else {
            z4 = false;
        }
        AlarmClock.Builder thursday = friday.setThursday(z4);
        if (((week & 16) >> 4) == 1) {
            z5 = true;
        } else {
            z5 = false;
        }
        AlarmClock.Builder wednesday = thursday.setWednesday(z5);
        if (((week & 32) >> 5) == 1) {
            z6 = true;
        } else {
            z6 = false;
        }
        AlarmClock.Builder tuesday = wednesday.setTuesday(z6);
        if (((week & 64) >> 6) != 1) {
            z7 = false;
        }
        AlarmClock.Builder minutes = tuesday.setMonday(z7).setHour(hour).setMinutes(min);
        if (text.getBytes().length > 30) {
            text = text.substring(0, 10);
        }
        minutes.setTextBytes(ByteString.copyFromUtf8(text)).build();
        builder1.setHash(new Date().hashCode()).addAlarmclock(builder2);
        return Util.getMergeHeadLenBytes(0, 4, builder.setGroup(builder1).setOperation(AlarmOperation.ADD).build().toByteArray());
    }

    public byte[] removeAlarm(int id) {
        AlarmNotification.Builder builder = AlarmNotification.newBuilder();
        AlarmIDList.Builder builder3 = AlarmIDList.newBuilder();
        builder3.addIdList(id).build();
        return Util.getMergeHeadLenBytes(0, 4, builder.setIdList(builder3).setOperation(AlarmOperation.REMOVE).build().toByteArray());
    }

    public byte[] clearAlarm() {
        return Util.getMergeHeadLenBytes(0, 4, AlarmNotification.newBuilder().setReserved(1).setOperation(AlarmOperation.CLEAR).build().toByteArray());
    }

    public byte[] setSedentariness(boolean repeat, int hash, int week, int startHour, int endHour, int duration, int threshold) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7 = true;
        SedtNotification.Builder builder = SedtNotification.newBuilder();
        SedtGroup.Builder builder1 = SedtGroup.newBuilder();
        Sedentariness.Builder builder2 = Sedentariness.newBuilder();
        Sedentariness.Builder repeat2 = builder2.setRepeat(repeat);
        if ((week & 1) == 1) {
            z = true;
        } else {
            z = false;
        }
        Sedentariness.Builder sunday = repeat2.setSunday(z);
        if (((week & 2) >> 1) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        Sedentariness.Builder saturday = sunday.setSaturday(z2);
        if (((week & 4) >> 2) == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        Sedentariness.Builder friday = saturday.setFriday(z3);
        if (((week & 8) >> 3) == 1) {
            z4 = true;
        } else {
            z4 = false;
        }
        Sedentariness.Builder thursday = friday.setThursday(z4);
        if (((week & 16) >> 4) == 1) {
            z5 = true;
        } else {
            z5 = false;
        }
        Sedentariness.Builder wednesday = thursday.setWednesday(z5);
        if (((week & 32) >> 5) == 1) {
            z6 = true;
        } else {
            z6 = false;
        }
        Sedentariness.Builder tuesday = wednesday.setTuesday(z6);
        if (((week & 64) >> 6) != 1) {
            z7 = false;
        }
        tuesday.setMonday(z7).setStartHour(startHour).setEndHour(endHour).setDuration(duration).setThreshold(threshold);
        builder1.setHash(hash).addSedentariness(builder2);
        return Util.getMergeHeadLenBytes(0, 5, builder.setOperation(SedtOperation.SET).setGroup(builder1).build().toByteArray());
    }

    public byte[] clearSedentariness() {
        return Util.getMergeHeadLenBytes(0, 5, SedtNotification.newBuilder().setOperation(SedtOperation.SET).build().toByteArray());
    }

    public void setLanguage(Context context, int language) {
        BackgroundThreadManager.getInstance().addWriteData(context, Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setLanguageId(DeviceLanuage.forNumber(language)).build().toByteArray()));
    }

    public byte[] setLcdGsTime(boolean lcdGsswitch, int startHour, int endHour) {
        DeviceConfNotification.Builder builder = DeviceConfNotification.newBuilder();
        DeviceLcdGs.Builder builder1 = DeviceLcdGs.newBuilder();
        builder1.setLcdGsStartHour(startHour).setLcdGsEndHour(endHour).build();
        return Util.getMergeHeadLenBytes(0, 6, builder.setHash(new Date().hashCode()).setLcdGsSwitch(lcdGsswitch).setLcdGsTime(builder1).build().toByteArray());
    }

    public byte[] setDistenceUnit(boolean distance_unit) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setDistanceUnit(distance_unit).build().toByteArray());
    }

    public byte[] setTemperatureUnit(boolean temperature_unit) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setTemperatureUnit(temperature_unit).build().toByteArray());
    }

    public byte[] setHourFormat(boolean hour_format) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setHourFormat(hour_format).build().toByteArray());
    }

    public byte[] setDateFormat(boolean date_format) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setDateFormat(date_format).build().toByteArray());
    }

    public byte[] setAutoHeartrate(boolean autoHeartrate) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setAutoHeartrate(autoHeartrate).build().toByteArray());
    }

    public byte[] setAutoSport(boolean auto_sport) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setAutoSport(auto_sport).build().toByteArray());
    }

    public byte[] setHabitualHand(boolean habitual_hand) {
        return Util.getMergeHeadLenBytes(0, 6, DeviceConfNotification.newBuilder().setHash(new Date().hashCode()).setHabitualHand(habitual_hand).build().toByteArray());
    }

    public byte[] setDeviceInfo(int language, boolean lcdGsswitch, int startHour, int endHour, boolean distance_unit, boolean temperature_unit, boolean hour_format, boolean date_format, boolean auto_heartrate, boolean auto_sport, boolean habitual_hand) {
        DeviceConfNotification.Builder builder = DeviceConfNotification.newBuilder();
        DeviceLcdGs.Builder builder1 = DeviceLcdGs.newBuilder();
        builder1.setLcdGsStartHour(startHour).setLcdGsEndHour(endHour);
        builder.setHash(new Date().hashCode()).setLanguageId(DeviceLanuage.forNumber(language)).setLcdGsSwitch(lcdGsswitch).setLcdGsTime(builder1).setDistanceUnit(distance_unit).setTemperatureUnit(temperature_unit).setHourFormat(hour_format).setDateFormat(date_format).setAutoHeartrate(auto_heartrate).setAutoSport(auto_sport).setHabitualHand(habitual_hand);
        return Util.getMergeHeadLenBytes(0, 6, builder.build().toByteArray());
    }

    public byte[] clearCalendar() {
        return Util.getMergeHeadLenBytes(0, 7, CalendarNotification.newBuilder().setOperation(CalendarOperation.CLEAR).build().toByteArray());
    }

    public byte[] addCalendar(int id, int timeMillis, String text) {
        CalendarNotification.Builder builder = CalendarNotification.newBuilder();
        CalendarGroup.Builder builder1 = CalendarGroup.newBuilder();
        Calendar.Builder builder2 = Calendar.newBuilder();
        RtTime.Builder builder3 = RtTime.newBuilder();
        builder3.setSeconds((Util.getTimeZone() * 3600) + timeMillis);
        if (text.getBytes().length > 30) {
            builder2.setTime(builder3).setTextBytes(ByteString.copyFromUtf8(text.substring(0, 10)));
        } else {
            builder2.setTime(builder3).setTextBytes(ByteString.copyFromUtf8(text));
        }
        builder1.setHash(new Date((long) timeMillis).hashCode()).addCalendar(builder2);
        return Util.getMergeHeadLenBytes(0, 7, builder.setOperation(CalendarOperation.ADD).setGroup(builder1).build().toByteArray());
    }

    public byte[] removeCalendar(int hash, int second) {
        CalendarNotification.Builder builder = CalendarNotification.newBuilder();
        CalendarGroup.Builder builder1 = CalendarGroup.newBuilder();
        Calendar.Builder builder2 = Calendar.newBuilder();
        RtTime.Builder builder3 = RtTime.newBuilder();
        builder3.setSeconds((Util.getTimeZone() * 3600) + second).build();
        builder2.setTime(builder3).build();
        builder1.setHash(new Date().hashCode()).addCalendar(builder2).build();
        return Util.getMergeHeadLenBytes(0, 7, builder.setOperation(CalendarOperation.REMOVE).setGroup(builder1).build().toByteArray());
    }

    public byte[] setMototConf(int mode, int round, int type) {
        MotorConfNotification.Builder builder = MotorConfNotification.newBuilder();
        MotorConf.Builder builder1 = MotorConf.newBuilder();
        VibrateType vibType = VibrateType.forNumber(type);
        VibrateCnf.Builder builder2 = VibrateCnf.newBuilder();
        builder2.setMode(mode).setRound(round).setType(vibType);
        builder1.setHash(new Date().hashCode()).addConf(builder2);
        return Util.getMergeHeadLenBytes(0, 8, builder.setOperation(MotorOperation.CONFIG).setConf(builder1).build().toByteArray());
    }

    public byte[] setMotorVibrate(int mode, int round) {
        MotorConfNotification.Builder builder = MotorConfNotification.newBuilder();
        MotorVibrate.Builder builder3 = MotorVibrate.newBuilder();
        builder3.setMode(mode).setRound(round).build();
        return Util.getMergeHeadLenBytes(0, 8, builder.setOperation(MotorOperation.VIBRATE).setVibrate(builder3).build().toByteArray());
    }

    public byte[] getDataInfo() {
        return Util.getMergeHeadLenBytes(0, 9, DataInfoRequest.newBuilder().setReserved(1).build().toByteArray());
    }

    public byte[] getRealTimeData() {
        return Util.getMergeHeadLenBytes(0, 112, RtSubscriber.newBuilder().setTime(RtSync.ON_MINUTE_CHANGE).build().toByteArray());
    }

    public byte[] getBattery() {
        return Util.getMergeHeadLenBytes(0, 112, RtSubscriber.newBuilder().setBattery(RtSync.ONLY_ONCE).build().toByteArray());
    }

    public byte[] getRealHealthData() {
        return Util.getMergeHeadLenBytes(0, 112, RtSubscriber.newBuilder().setHealth(RtSync.ONLY_ONCE).build().toByteArray());
    }

    public byte[] setSmartShotData(int mode) {
        return Util.getMergeHeadLenBytes(0, 112, RtSubscriber.newBuilder().setMode(RtMode.forNumber(mode)).build().toByteArray());
    }

    public byte[] itHisData(int type) {
        HisSubscriber.Builder builder = HisSubscriber.newBuilder();
        HisITSync.Builder builder1 = HisITSync.newBuilder();
        builder1.setType(HisDataType.forNumber(type)).build();
        return Util.getMergeHeadLenBytes(0, 128, builder.setOperation(HisOperation.IT_SYNC).setItSync(builder1).build().toByteArray());
    }

    public byte[] stopHisData(int type) {
        HisSubscriber.Builder builder = HisSubscriber.newBuilder();
        HisStopSync.Builder builder2 = HisStopSync.newBuilder();
        builder2.setType(HisDataType.forNumber(type)).build();
        return Util.getMergeHeadLenBytes(0, 128, builder.setOperation(HisOperation.STOP_SYNC).setStopSync(builder2).build().toByteArray());
    }

    public byte[] startHisData(int type, int startSeq, int endSeq) {
        HisSubscriber.Builder builder = HisSubscriber.newBuilder();
        HisStartSync.Builder builder3 = HisStartSync.newBuilder();
        HisBlock.Builder builder4 = HisBlock.newBuilder();
        builder4.setStartSeq(startSeq).setEndSeq(endSeq);
        builder3.setType(HisDataType.forNumber(type)).addBlock(builder4);
        return Util.getMergeHeadLenBytes(0, 128, builder.setOperation(HisOperation.START_SYNC).setStartSync(builder3).build().toByteArray());
    }

    public byte[] setFileDescUpdate(boolean isDesc) {
        return Util.getMergeHeadLenBytes(0, Opcodes.ADD_INT, FilesUpdateRequest.newBuilder().setDesc(isDesc).build().toByteArray());
    }

    public byte[] setFileInitUpdate(int fuType, int fileSize, int fileCyc, String fileName, int fileOffset, int crc32AtOffset) {
        FilesUpdateRequest.Builder builder = FilesUpdateRequest.newBuilder();
        FUInitRequest.Builder builder1 = FUInitRequest.newBuilder();
        FUFileInfo.Builder builder2 = FUFileInfo.newBuilder();
        builder2.setFd(FUType.forNumber(fuType)).setFileSize(fileSize).setFileCrc32(fileCyc).setFileName(fileName).setFileOffset(fileOffset).setCrc32AtOffset(crc32AtOffset);
        builder1.setInitInfo(builder2);
        return Util.getMergeHeadLenBytes(0, Opcodes.ADD_INT, builder.setInit(builder1).build().toByteArray());
    }

    public byte[] setFileDataUpdate(int fuType, int fileCyc, int crc32AtOffset, ByteString buf) {
        FilesUpdateRequest.Builder builder = FilesUpdateRequest.newBuilder();
        FUDataRequest.Builder builder1 = FUDataRequest.newBuilder();
        builder1.setFd(FUType.forNumber(fuType)).setFileOffset(fileCyc).setCrc32AtOffset(crc32AtOffset).setBuf(buf);
        return Util.getMergeHeadLenBytes(0, Opcodes.ADD_INT, builder.setData(builder1).build().toByteArray());
    }

    public byte[] setFileDataExit(int fuType) {
        FilesUpdateRequest.Builder builder = FilesUpdateRequest.newBuilder();
        FUExitRequest.Builder builder1 = FUExitRequest.newBuilder();
        builder1.setFd(FUType.forNumber(fuType));
        return Util.getMergeHeadLenBytes(0, Opcodes.ADD_INT, builder.setExit(builder1).build().toByteArray());
    }

    public boolean setUpgradeNotification() {
        return ProtoBle.getInstance().setCharacteristicNotification(ProtoBle.getInstance().getCharacteristic(ProtoBufUUID.BAND_PROTO_DFU_CHARACT_UUID), true);
    }

    public void setUpgradeCmd() {
        ProtoBle.getInstance().writeCharacteristicNewAPI(ProtoBufUUID.BAND_PROTO_DFU_CHARACT_UUID, new byte[]{1});
    }
}
