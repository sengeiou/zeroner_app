package com.iwown.ble_module.proto.cmd;

import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.protobuf.InvalidProtocolBufferException;
import com.iwown.ble_module.model.KeyModel;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmConfirm;
import com.iwown.ble_module.proto.base.Alarmclock.AlarmSubscriber;
import com.iwown.ble_module.proto.base.CalendarOuterClass.CalendarConfirm;
import com.iwown.ble_module.proto.base.CalendarOuterClass.CalendarSubscriber;
import com.iwown.ble_module.proto.base.ConParams.ConParamsUpdate;
import com.iwown.ble_module.proto.base.DataInfo.DataInfoResponse;
import com.iwown.ble_module.proto.base.DataInfo.HisDataInfo;
import com.iwown.ble_module.proto.base.DevInfo.DevInfoManu;
import com.iwown.ble_module.proto.base.DevInfo.DeviceInfoResponse;
import com.iwown.ble_module.proto.base.DeviceConf.DeviceConfSubscriber;
import com.iwown.ble_module.proto.base.FilesUpdate.FUDataResponse;
import com.iwown.ble_module.proto.base.FilesUpdate.FUDescResponse;
import com.iwown.ble_module.proto.base.FilesUpdate.FUExitResponse;
import com.iwown.ble_module.proto.base.FilesUpdate.FUFileDesc;
import com.iwown.ble_module.proto.base.FilesUpdate.FUFileInfo;
import com.iwown.ble_module.proto.base.FilesUpdate.FUInitResponse;
import com.iwown.ble_module.proto.base.FilesUpdate.FilesUpdateResponse;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisConfirm;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisData;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisDataType;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisIndex;
import com.iwown.ble_module.proto.base.HisDataOuterClass.HisNotification;
import com.iwown.ble_module.proto.base.HisEcgData.HisDataECG;
import com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS;
import com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth;
import com.iwown.ble_module.proto.base.HisHealthData.HisHealthBp;
import com.iwown.ble_module.proto.base.HisHealthData.HisHealthHr;
import com.iwown.ble_module.proto.base.HisHealthData.HisHealthHrv;
import com.iwown.ble_module.proto.base.HisHealthData.HisHealthPedo;
import com.iwown.ble_module.proto.base.HisHealthData.HisHealthSleep;
import com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG;
import com.iwown.ble_module.proto.base.HisRriData.HisDataRRI;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorConfSubscriber;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorConfSubscriber.DataCase;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorConfirm;
import com.iwown.ble_module.proto.base.MotorConfOuterClass.MotorParams;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgOperation;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgRequest;
import com.iwown.ble_module.proto.base.MsgNotifyOuterClass.MsgSubscriber;
import com.iwown.ble_module.proto.base.PeerInfo.PeerInfoSubsriber;
import com.iwown.ble_module.proto.base.RealtimeData.RtBattery;
import com.iwown.ble_module.proto.base.RealtimeData.RtData;
import com.iwown.ble_module.proto.base.RealtimeData.RtGNSS;
import com.iwown.ble_module.proto.base.RealtimeData.RtHealth;
import com.iwown.ble_module.proto.base.RealtimeData.RtMode;
import com.iwown.ble_module.proto.base.RealtimeData.RtNotification;
import com.iwown.ble_module.proto.base.RealtimeData.RtState;
import com.iwown.ble_module.proto.base.RealtimeData.RtSync;
import com.iwown.ble_module.proto.base.SedentarinessOuterClass.SedtConfirm;
import com.iwown.ble_module.proto.base.SedentarinessOuterClass.SedtSubscriber;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherConfirm;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherOperation;
import com.iwown.ble_module.proto.base.WeatherOuterClass.WeatherSubscriber;
import com.iwown.ble_module.proto.model.ProtoBufConnectParmas;
import com.iwown.ble_module.proto.model.ProtoBufFileUpdateInfo;
import com.iwown.ble_module.proto.model.ProtoBufHardwareInfo;
import com.iwown.ble_module.proto.model.ProtoBufHisEPGData;
import com.iwown.ble_module.proto.model.ProtoBufHisGnssData;
import com.iwown.ble_module.proto.model.ProtoBufHisGnssData.Gnss;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData.Bp;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData.HRV;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData.HeartRate;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData.Pedo;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData.Sleep;
import com.iwown.ble_module.proto.model.ProtoBufHisIndexTable;
import com.iwown.ble_module.proto.model.ProtoBufHisIndexTable.Index;
import com.iwown.ble_module.proto.model.ProtoBufHisRriData;
import com.iwown.ble_module.proto.model.ProtoBufPeerInfo;
import com.iwown.ble_module.proto.model.ProtoBufRealTimeData;
import com.iwown.ble_module.proto.model.ProtoBufResult;
import com.iwown.ble_module.proto.model.ProtoBufSupportInfo;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProtoBufReceiverCmd {
    public static ProtoBufHardwareInfo parse00DeviceInfo(byte[] bytes) {
        try {
            DeviceInfoResponse deviceInfoResponse = DeviceInfoResponse.parseFrom(bytes);
            String version = deviceInfoResponse.getVersion();
            String model = deviceInfoResponse.getModel();
            int fota = deviceInfoResponse.getFota().getNumber();
            byte[] mac = deviceInfoResponse.getMac().toByteArray();
            DevInfoManu manu = deviceInfoResponse.getManu();
            String date = manu.getDate();
            String factory = manu.getFactory();
            ProtoBufHardwareInfo info = new ProtoBufHardwareInfo();
            info.setVersion(version);
            info.setModel(model);
            info.setFotaType(fota);
            info.setFota(ProtoBufHardwareInfo.fotas[fota]);
            info.setMac(Util.bytesToString(mac));
            info.setDeviceTime(date);
            info.setFactory(factory);
            return info;
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static String parse01PeerInfo(byte[] bytes) {
        String result = null;
        try {
            PeerInfoSubsriber peerInfoSubsriber = PeerInfoSubsriber.parseFrom(bytes);
            boolean supportPeerType = peerInfoSubsriber.getSupportPeerType();
            boolean supportPeerStatus = peerInfoSubsriber.getSupportPeerStatus();
            boolean supportDateTime = peerInfoSubsriber.getSupportDateTime();
            boolean supportGnssConf = peerInfoSubsriber.getSupportGnssConf();
            boolean supportHrAlarmConf = peerInfoSubsriber.getSupportHrAlarmConf();
            boolean supportUserConf = peerInfoSubsriber.getSupportUserConf();
            boolean supportGoalConf = peerInfoSubsriber.getSupportGoalConf();
            int hashGnssConf = peerInfoSubsriber.getHashGnssConf();
            int hashHrAlarmConf = peerInfoSubsriber.getHashHrAlarmConf();
            int hashUserConf = peerInfoSubsriber.getHashUserConf();
            int hashGoalConf = peerInfoSubsriber.getHashGoalConf();
            ProtoBufPeerInfo protoBufPeerInfo = new ProtoBufPeerInfo();
            protoBufPeerInfo.setSupport_peer_status(supportPeerType);
            protoBufPeerInfo.setSupport_peer_status(supportPeerStatus);
            protoBufPeerInfo.setSupport_date_time(supportDateTime);
            protoBufPeerInfo.setSupport_gnss_conf(supportGnssConf);
            protoBufPeerInfo.setSupport_hr_alarm_conf(supportHrAlarmConf);
            protoBufPeerInfo.setSupport_user_conf(supportUserConf);
            protoBufPeerInfo.setSupport_goal_conf(supportGoalConf);
            return JsonTool.toJson(protoBufPeerInfo);
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
    }

    public static String parse02MsgNotify(byte[] bytes) {
        String result = null;
        try {
            MsgSubscriber msgSubscriber = MsgSubscriber.parseFrom(bytes);
            switch (msgSubscriber.getDataCase()) {
                case REQUEST:
                    MsgRequest request = msgSubscriber.getRequest();
                    boolean supportHandler = request.getSupportHandler();
                    boolean supportFilter = request.getSupportFilter();
                    boolean supportNotify = request.getSupportNotify();
                    int handlerHash = request.getHandlerHash();
                    int filterHash = request.getFilterHash();
                    int filterIdCount = request.getFilterIdCount();
                    int notifyTitleLen = request.getNotifyTitleLen();
                    int notifyDetailLen = request.getNotifyDetailLen();
                    Log.e("yanxiparse", "   supportHandler   " + supportHandler);
                    return result;
                case OPERATION:
                    MsgOperation operation = msgSubscriber.getOperation();
                    int number = operation.getOption().getNumber();
                    int id = operation.getId();
                    KeyModel keyModel = new KeyModel();
                    keyModel.setKeyCode(number);
                    return JsonTool.toJson(keyModel);
                default:
                    return result;
            }
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
        ThrowableExtension.printStackTrace(e);
        return result;
    }

    public static String parse03Weather(byte[] bytes) {
        try {
            WeatherSubscriber weatherSubscriber = WeatherSubscriber.parseFrom(bytes);
            switch (weatherSubscriber.getDataCase()) {
                case CONFIRM:
                    WeatherConfirm confirm = weatherSubscriber.getConfirm();
                    WeatherOperation operation = confirm.getOperation();
                    boolean ret = confirm.getRet();
                    ProtoBufResult i7BResult = new ProtoBufResult();
                    i7BResult.setRet(ret);
                    i7BResult.setComfirm(operation.getNumber());
                    return JsonTool.toJson(i7BResult);
                case PARAMS:
                    int maxCount = weatherSubscriber.getParams().getMaxCount();
                    return null;
                default:
                    return null;
            }
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static String parse04Alarm(byte[] bytes) {
        String result = null;
        try {
            AlarmSubscriber alarmSubscriber = AlarmSubscriber.parseFrom(bytes);
            int hash = alarmSubscriber.getHash();
            AlarmConfirm confirm = alarmSubscriber.getConfirm();
            int number = confirm.getOperation().getNumber();
            boolean ret = confirm.getRet();
            ProtoBufResult i7BResult = new ProtoBufResult();
            i7BResult.setComfirm(number);
            i7BResult.setRet(ret);
            return JsonTool.toJson(i7BResult);
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
    }

    public static String parse05Sedentariness(byte[] bytes) {
        String result = null;
        try {
            SedtSubscriber sedtSubscriber = SedtSubscriber.parseFrom(bytes);
            int hash = sedtSubscriber.getHash();
            SedtConfirm confirm = sedtSubscriber.getConfirm();
            int format = confirm.getOperation().getNumber();
            boolean ret = confirm.getRet();
            ProtoBufResult i7BResult = new ProtoBufResult();
            i7BResult.setComfirm(format);
            i7BResult.setRet(ret);
            return JsonTool.toJson(i7BResult);
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
    }

    public static void parse06DeviceConf(byte[] bytes) {
        try {
            DeviceConfSubscriber deviceConfSubscriber = DeviceConfSubscriber.parseFrom(bytes);
            int hash = deviceConfSubscriber.getHash();
            int supportLanguageMaskCount = deviceConfSubscriber.getSupportLanguageMaskCount();
            boolean supportLcdGestureSwitch = deviceConfSubscriber.getSupportLcdGestureSwitch();
            boolean supportLcdGestureTime = deviceConfSubscriber.getSupportLcdGestureTime();
            boolean supportDistanceUnit = deviceConfSubscriber.getSupportDistanceUnit();
            boolean supportTemperatureUnit = deviceConfSubscriber.getSupportTemperatureUnit();
            boolean supportHourFormat = deviceConfSubscriber.getSupportHourFormat();
            boolean supportDateFormat = deviceConfSubscriber.getSupportDateFormat();
            boolean supportAutoHeartrate = deviceConfSubscriber.getSupportAutoHeartrate();
            boolean supportAutoSport = deviceConfSubscriber.getSupportAutoSport();
            deviceConfSubscriber.getSupportHabitualHand();
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static String parse07Calendar(byte[] bytes) {
        String result = null;
        try {
            CalendarSubscriber calendarSubscriber = CalendarSubscriber.parseFrom(bytes);
            int hash = calendarSubscriber.getHash();
            int maxCount = calendarSubscriber.getMaxCount();
            CalendarConfirm confirm = calendarSubscriber.getConfirm();
            int number = confirm.getOperation().getNumber();
            boolean ret = confirm.getRet();
            ProtoBufResult i7BResult = new ProtoBufResult();
            i7BResult.setComfirm(number);
            i7BResult.setRet(ret);
            return JsonTool.toJson(i7BResult);
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
    }

    public static String parse08MotorConf(byte[] bytes) {
        try {
            MotorConfSubscriber motorConfSubscriber = MotorConfSubscriber.parseFrom(bytes);
            DataCase dataCase = motorConfSubscriber.getDataCase();
            int hash = motorConfSubscriber.getHash();
            switch (dataCase) {
                case CONFIRM:
                    MotorConfirm confirm = motorConfSubscriber.getConfirm();
                    int number = confirm.getOperation().getNumber();
                    boolean ret = confirm.getRet();
                    ProtoBufResult i7BResult = new ProtoBufResult();
                    i7BResult.setComfirm(number);
                    i7BResult.setRet(ret);
                    return JsonTool.toJson(i7BResult);
                case PARAMS:
                    MotorParams motorParams = motorConfSubscriber.getParams();
                    int modeNum = motorParams.getModeNum();
                    motorParams.getTypeNum();
                    return null;
                default:
                    return null;
            }
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static String parse09DataInfo(byte[] bytes) {
        String result = null;
        try {
            HisDataInfo hisDataInfo = DataInfoResponse.parseFrom(bytes).getHisDataInfo();
            boolean supportHealth = hisDataInfo.getSupportHealth();
            boolean supportGnss = hisDataInfo.getSupportGnss();
            boolean supportEcg = hisDataInfo.getSupportEcg();
            boolean supportRri = hisDataInfo.getSupportRri();
            ProtoBufSupportInfo info = new ProtoBufSupportInfo();
            info.setSupport_health(supportHealth);
            info.setSupport_gnss(supportGnss);
            info.setSupport_ecg(supportEcg);
            info.setSupport_rri(supportRri);
            return JsonTool.toJson(info);
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
    }

    public static ProtoBufRealTimeData parse70RealInfo(byte[] bytes) {
        try {
            RtNotification rtNotification = RtNotification.parseFrom(bytes);
            RtNotification.DataCase dataCase = rtNotification.getDataCase();
            ProtoBufRealTimeData data = new ProtoBufRealTimeData();
            switch (dataCase) {
                case RT_DATA:
                    RtData rtData = rtNotification.getRtData();
                    if (rtData.hasBattery()) {
                        RtBattery battery = rtData.getBattery();
                        int level = battery.getLevel();
                        boolean charging = battery.getCharging();
                        Log.e("yanxiparse", "level" + level + "charging" + charging);
                        data.setBattery(true);
                        data.setLevel(level);
                        data.setCharging(charging);
                    }
                    if (rtData.hasTime()) {
                        int seconds = rtData.getTime().getSeconds();
                        data.setTime(true);
                        data.setSeconds(seconds);
                    }
                    if (rtData.hasHealth()) {
                        RtHealth health = rtData.getHealth();
                        float calorie = (((float) health.getCalorie()) * 1.0f) / 10.0f;
                        float distance = ((float) health.getDistance()) / 10.0f;
                        int steps = health.getSteps();
                        Log.e("yanxiparse", "calorie" + calorie + "distance" + distance + "steps" + steps);
                        data.setHearth(true);
                        data.setCalorie(calorie);
                        data.setDistance(distance);
                        data.setSteps(steps);
                    }
                    if (!rtData.hasKey()) {
                        return data;
                    }
                    int number = rtData.getKey().getNumber();
                    data.setKey(true);
                    data.setKeyMode(number);
                    return data;
                case RT_STATE:
                    RtState rtState = rtNotification.getRtState();
                    RtSync battery_status = rtState.getBattery();
                    RtSync health_status = rtState.getHealth();
                    RtSync time_status = rtState.getTime();
                    RtMode mode_status = rtState.getMode();
                    data.setBattery_status(battery_status.getNumber());
                    data.setHealth_status(health_status.getNumber());
                    data.setTime_status(time_status.getNumber());
                    data.setMode_status(mode_status.getNumber());
                    return data;
                default:
                    return null;
            }
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
        ThrowableExtension.printStackTrace(e);
        return null;
    }

    public static String parse80HisData(byte[] bytes) {
        try {
            HisNotification hisNotification = HisNotification.parseFrom(bytes);
            HisDataType hisDataType = hisNotification.getType();
            HisNotification.DataCase dataCase = hisNotification.getDataCase();
            switch (dataCase) {
                case CONFIRM:
                    HisConfirm confirm = hisNotification.getConfirm();
                    int number = confirm.getOperation().getNumber();
                    boolean ret = confirm.getRet();
                    ProtoBufResult i7BResult = new ProtoBufResult();
                    i7BResult.setComfirm(number);
                    i7BResult.setRet(ret);
                    return JsonTool.toJson(i7BResult);
                case HIS_DATA:
                    HisData hisData = hisNotification.getHisData();
                    int seq = hisData.getSeq();
                    HisData.DataCase dataCase1 = hisData.getDataCase();
                    switch (dataCase1) {
                        case RRI:
                            HisDataRRI rri = hisData.getRri();
                            int rriSecond = rri.getTimeStamp().getDateTime().getSeconds();
                            int timeZone1 = rri.getTimeStamp().getTimeZone();
                            ProtoBufHisRriData protoBufHisRriData = new ProtoBufHisRriData();
                            protoBufHisRriData.setYear(protoBufHisRriData.parseTime((long) rriSecond, timeZone1)[0]);
                            protoBufHisRriData.setMonth(protoBufHisRriData.parseTime((long) rriSecond, timeZone1)[1]);
                            protoBufHisRriData.setDay(protoBufHisRriData.parseTime((long) rriSecond, timeZone1)[2]);
                            protoBufHisRriData.setHour(protoBufHisRriData.parseTime((long) rriSecond, timeZone1)[3]);
                            protoBufHisRriData.setMinute(protoBufHisRriData.parseTime((long) rriSecond, timeZone1)[4]);
                            protoBufHisRriData.setSecond(protoBufHisRriData.parseTime((long) rriSecond, timeZone1)[5]);
                            protoBufHisRriData.setRaw_data(rri.getRawDataList());
                            protoBufHisRriData.setTimestamp(rriSecond - (timeZone1 * 3600));
                            protoBufHisRriData.setHisDataType(dataCase1.getNumber());
                            protoBufHisRriData.setHisDataCase(dataCase.getNumber());
                            protoBufHisRriData.setSeq(seq);
                            return JsonTool.toJson(protoBufHisRriData);
                        case ECG:
                            HisDataECG ecg = hisData.getEcg();
                            int seconds1 = ecg.getTimeStamp().getDateTime().getSeconds();
                            List<Integer> ecgDataList = ecg.getRawDataList();
                            int timeZone = ecg.getTimeStamp().getTimeZone();
                            ProtoBufHisEPGData i7BHisECGData = new ProtoBufHisEPGData();
                            i7BHisECGData.setEcg_data(ecgDataList);
                            i7BHisECGData.setYear(i7BHisECGData.parseTime((long) seconds1, timeZone)[0]);
                            i7BHisECGData.setMonth(i7BHisECGData.parseTime((long) seconds1, timeZone)[1]);
                            i7BHisECGData.setDay(i7BHisECGData.parseTime((long) seconds1, timeZone)[2]);
                            i7BHisECGData.setHour(i7BHisECGData.parseTime((long) seconds1, timeZone)[3]);
                            i7BHisECGData.setMinute(i7BHisECGData.parseTime((long) seconds1, timeZone)[4]);
                            i7BHisECGData.setSecond(i7BHisECGData.parseTime((long) seconds1, timeZone)[5]);
                            i7BHisECGData.setHisDataType(dataCase1.getNumber());
                            i7BHisECGData.setHisDataCase(dataCase.getNumber());
                            i7BHisECGData.setSeq(seq);
                            return JsonTool.toJson(i7BHisECGData);
                        case PPG:
                            HisDataPPG ppg = hisData.getPpg();
                            int seconds3 = ppg.getTimeStamp().getDateTime().getSeconds();
                            int timeZone4 = ppg.getTimeStamp().getTimeZone();
                            List<Integer> rawDataList = ppg.getRawDataList();
                            ProtoBufHisEPGData bufHisEPGData = new ProtoBufHisEPGData();
                            bufHisEPGData.setPpg_data(rawDataList);
                            bufHisEPGData.setYear(bufHisEPGData.parseTime((long) seconds3, timeZone4)[0]);
                            bufHisEPGData.setMonth(bufHisEPGData.parseTime((long) seconds3, timeZone4)[1]);
                            bufHisEPGData.setDay(bufHisEPGData.parseTime((long) seconds3, timeZone4)[2]);
                            bufHisEPGData.setHour(bufHisEPGData.parseTime((long) seconds3, timeZone4)[3]);
                            bufHisEPGData.setMinute(bufHisEPGData.parseTime((long) seconds3, timeZone4)[4]);
                            bufHisEPGData.setSecond(bufHisEPGData.parseTime((long) seconds3, timeZone4)[5]);
                            bufHisEPGData.setHisDataType(dataCase1.getNumber());
                            bufHisEPGData.setHisDataCase(dataCase.getNumber());
                            bufHisEPGData.setSeq(seq);
                            return JsonTool.toJson(bufHisEPGData);
                        case GNSS:
                            HisDataGNSS gnss = hisData.getGnss();
                            ArrayList arrayList = new ArrayList();
                            int frequency = gnss.getFrequency();
                            int seconds2 = gnss.getTimeStamp().getDateTime().getSeconds();
                            int timeZone2 = gnss.getTimeStamp().getTimeZone();
                            for (RtGNSS rtGNSS : gnss.getGnssList()) {
                                float altitude = rtGNSS.getAltitude();
                                float latitude = rtGNSS.getLatitude();
                                float longitude = rtGNSS.getLongitude();
                                float speed = rtGNSS.getSpeed();
                                Gnss gnss1 = new Gnss();
                                gnss1.setAltitude(altitude);
                                gnss1.setLatitude(latitude);
                                gnss1.setLongitude(longitude);
                                gnss1.setSpeed(speed);
                                arrayList.add(gnss1);
                            }
                            ProtoBufHisGnssData i7BHisGnssData = new ProtoBufHisGnssData();
                            i7BHisGnssData.setSeq(seq);
                            i7BHisGnssData.setFrequency(frequency);
                            i7BHisGnssData.setTime_stamp(seconds2 - (timeZone2 * 3600));
                            i7BHisGnssData.setGnssList(arrayList);
                            i7BHisGnssData.setHisDataType(dataCase1.getNumber());
                            i7BHisGnssData.setHisDataCase(dataCase.getNumber());
                            return JsonTool.toJson(i7BHisGnssData);
                        case HEALTH:
                            ProtoBufHisHealthData i7BHisData = new ProtoBufHisHealthData();
                            HisDataHealth health = hisData.getHealth();
                            int seconds = health.getTimeStamp().getDateTime().getSeconds();
                            int timeZone3 = health.getTimeStamp().getTimeZone();
                            HisHealthSleep sleepData = health.getSleepData();
                            List<Integer> sleepDataList = sleepData.getSleepDataList();
                            boolean shutDown = sleepData.getShutDown();
                            boolean charge = sleepData.getCharge();
                            Sleep sleep = new Sleep();
                            sleep.setCharge(charge);
                            sleep.setShutdown(shutDown);
                            sleep.setSleepData(sleep.parseData(sleepDataList));
                            HisHealthPedo pedoData = health.getPedoData();
                            int type = pedoData.getType();
                            int state = pedoData.getState();
                            int calorie = pedoData.getCalorie();
                            int step = pedoData.getStep();
                            int distance = pedoData.getDistance();
                            Pedo pedo = new Pedo();
                            pedo.setType(type);
                            pedo.setState(state);
                            pedo.setCalorie(calorie);
                            pedo.setStep(step);
                            pedo.setDistance(distance);
                            HisHealthHr hrData = health.getHrData();
                            int maxBpm = hrData.getMaxBpm();
                            int minBpm = hrData.getMinBpm();
                            int avgBpm = hrData.getAvgBpm();
                            HeartRate heartRate = new HeartRate();
                            heartRate.setAvg_bpm(avgBpm);
                            heartRate.setMax_bpm(maxBpm);
                            heartRate.setMin_bpm(minBpm);
                            HisHealthHrv hrvData = health.getHrvData();
                            float sdnn = hrvData.getSDNN();
                            float rmssd = hrvData.getRMSSD();
                            float pnn50 = hrvData.getPNN50();
                            float mean = hrvData.getMEAN();
                            float fatigue = hrvData.getFatigue();
                            HRV hrv = new HRV();
                            hrv.setSDNN(sdnn);
                            hrv.setRMSSD(rmssd);
                            hrv.setPNN50(pnn50);
                            hrv.setMEAN(mean);
                            hrv.setFatigue(fatigue);
                            HisHealthBp bpData = health.getBpData();
                            Bp bp = new Bp();
                            bp.setDbp(bpData.getDbp());
                            bp.setSbp(bpData.getSbp());
                            bp.setTime(bpData.getTime());
                            i7BHisData.setYear(i7BHisData.parseTime((long) seconds, timeZone3)[0]);
                            i7BHisData.setMonth(i7BHisData.parseTime((long) seconds, timeZone3)[1]);
                            i7BHisData.setDay(i7BHisData.parseTime((long) seconds, timeZone3)[2]);
                            i7BHisData.setHour(i7BHisData.parseTime((long) seconds, timeZone3)[3]);
                            i7BHisData.setMinute(i7BHisData.parseTime((long) seconds, timeZone3)[4]);
                            i7BHisData.setSecond(i7BHisData.parseTime((long) seconds, timeZone3)[5]);
                            i7BHisData.setTime(seconds - (timeZone3 * 3600));
                            i7BHisData.setSeq(seq);
                            i7BHisData.setSleep(sleep);
                            i7BHisData.setHeartRate(heartRate);
                            i7BHisData.setPedo(pedo);
                            i7BHisData.setHrv(hrv);
                            i7BHisData.setBp(bp);
                            i7BHisData.setHisDataType(dataCase1.getNumber());
                            i7BHisData.setHisDataCase(dataCase.getNumber());
                            return JsonTool.toJson(i7BHisData);
                        case STATUS:
                            Log.e("yanxi", hisData.getStatus().getNumber() + "");
                            return null;
                        default:
                            return null;
                    }
                case INDEX_TABLE:
                    List<HisIndex> indexList = hisNotification.getIndexTable().getIndexList();
                    ProtoBufHisIndexTable i7BHisIndexTable = new ProtoBufHisIndexTable();
                    ArrayList arrayList2 = new ArrayList();
                    for (HisIndex index : indexList) {
                        Index index1 = new Index();
                        index1.setStartSeq(index.getStartSeq());
                        index1.setEndSeq(index.getEndSeq());
                        index1.setSecond(index.getTime().getSeconds());
                        arrayList2.add(index1);
                    }
                    i7BHisIndexTable.setIndexList(arrayList2);
                    i7BHisIndexTable.setHisDataType(hisDataType.getNumber());
                    i7BHisIndexTable.setHisDataCase(dataCase.getNumber());
                    return JsonTool.toJson(i7BHisIndexTable);
                default:
                    return null;
            }
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
        ThrowableExtension.printStackTrace(e);
        return null;
    }

    public static String parse90FileDescUpdate(byte[] bytes) {
        try {
            FilesUpdateResponse filesUpdateResponse = FilesUpdateResponse.parseFrom(bytes);
            switch (filesUpdateResponse.getParamsCase()) {
                case DESC:
                    FUDescResponse desc = filesUpdateResponse.getDesc();
                    LinkedList linkedList = new LinkedList();
                    int mtu = desc.getMtu();
                    if (desc.hasFont()) {
                        FUFileDesc font = desc.getFont();
                        int maxSize = font.getMaxSize();
                        boolean valid = font.getValid();
                        FUFileInfo info = font.getInfo();
                        int number = info.getFd().getNumber();
                        String fileName = info.getFileName();
                        int fileSize = info.getFileSize();
                        int fileCrc32 = info.getFileCrc32();
                        int fileOffset = info.getFileOffset();
                        int crc32AtOffset = info.getCrc32AtOffset();
                        ProtoBufFileUpdateInfo updateDesc = new ProtoBufFileUpdateInfo();
                        updateDesc.setType(filesUpdateResponse.getParamsCase().getNumber());
                        updateDesc.setMtu(mtu);
                        updateDesc.setMaxSize(maxSize);
                        updateDesc.setValid(valid);
                        updateDesc.setFuType(number);
                        updateDesc.setFileName(fileName);
                        updateDesc.setFileSize(fileSize);
                        updateDesc.setFileCrc32(fileCrc32);
                        updateDesc.setFileOffset(fileOffset);
                        updateDesc.setCrc32AtOffset(crc32AtOffset);
                        linkedList.add(updateDesc);
                    }
                    if (desc.hasGps()) {
                        FUFileDesc gps = desc.getGps();
                        int maxSize2 = gps.getMaxSize();
                        boolean valid2 = gps.getValid();
                        FUFileInfo info2 = gps.getInfo();
                        int number2 = info2.getFd().getNumber();
                        String fileName2 = info2.getFileName();
                        int fileSize2 = info2.getFileSize();
                        int fileCrc322 = info2.getFileCrc32();
                        int fileOffset2 = info2.getFileOffset();
                        int crc32AtOffset2 = info2.getCrc32AtOffset();
                        ProtoBufFileUpdateInfo updateDesc2 = new ProtoBufFileUpdateInfo();
                        updateDesc2.setType(filesUpdateResponse.getParamsCase().getNumber());
                        updateDesc2.setMtu(mtu);
                        updateDesc2.setMaxSize(maxSize2);
                        updateDesc2.setValid(valid2);
                        updateDesc2.setFuType(number2);
                        updateDesc2.setFileName(fileName2);
                        updateDesc2.setFileSize(fileSize2);
                        updateDesc2.setFileCrc32(fileCrc322);
                        updateDesc2.setFileOffset(fileOffset2);
                        updateDesc2.setCrc32AtOffset(crc32AtOffset2);
                        linkedList.add(updateDesc2);
                    }
                    if (desc.hasMgaonline()) {
                        FUFileDesc mgaonline = desc.getMgaonline();
                        int maxSize3 = mgaonline.getMaxSize();
                        boolean valid3 = mgaonline.getValid();
                        FUFileInfo info3 = mgaonline.getInfo();
                        int number3 = info3.getFd().getNumber();
                        String fileName3 = info3.getFileName();
                        int fileSize3 = info3.getFileSize();
                        int fileCrc323 = info3.getFileCrc32();
                        int fileOffset3 = info3.getFileOffset();
                        int crc32AtOffset3 = info3.getCrc32AtOffset();
                        ProtoBufFileUpdateInfo updateDesc3 = new ProtoBufFileUpdateInfo();
                        updateDesc3.setType(filesUpdateResponse.getParamsCase().getNumber());
                        updateDesc3.setMtu(mtu);
                        updateDesc3.setMaxSize(maxSize3);
                        updateDesc3.setValid(valid3);
                        updateDesc3.setFuType(number3);
                        updateDesc3.setFileName(fileName3);
                        updateDesc3.setFileSize(fileSize3);
                        updateDesc3.setFileCrc32(fileCrc323);
                        updateDesc3.setFileOffset(fileOffset3);
                        updateDesc3.setCrc32AtOffset(crc32AtOffset3);
                        linkedList.add(updateDesc3);
                    }
                    return JsonTool.toJson(linkedList);
                case INIT:
                    FUInitResponse init = filesUpdateResponse.getInit();
                    int fd = init.getFd().getNumber();
                    int status = init.getStatus().getNumber();
                    LinkedList linkedList2 = new LinkedList();
                    ProtoBufFileUpdateInfo updateInit = new ProtoBufFileUpdateInfo();
                    updateInit.setType(filesUpdateResponse.getParamsCase().getNumber());
                    updateInit.setFuType(fd);
                    updateInit.setStatus(status);
                    linkedList2.add(updateInit);
                    return JsonTool.toJson(linkedList2);
                case DATA:
                    FUDataResponse data = filesUpdateResponse.getData();
                    int fd1 = data.getFd().getNumber();
                    int status1 = data.getStatus().getNumber();
                    int fileOffset1 = data.getFileOffset();
                    int crc32AtOffset1 = data.getCrc32AtOffset();
                    ProtoBufFileUpdateInfo updateData = new ProtoBufFileUpdateInfo();
                    List<ProtoBufFileUpdateInfo> datas = new LinkedList<>();
                    updateData.setType(filesUpdateResponse.getParamsCase().getNumber());
                    updateData.setFuType(fd1);
                    updateData.setStatus(status1);
                    updateData.setFileOffset(fileOffset1);
                    updateData.setCrc32AtOffset(crc32AtOffset1);
                    datas.add(updateData);
                    return JsonTool.toJson(datas);
                case EXIT:
                    FUExitResponse exit = filesUpdateResponse.getExit();
                    int fd2 = exit.getFd().getNumber();
                    int status2 = exit.getStatus().getNumber();
                    FUFileDesc desc1 = exit.getDesc();
                    int maxSize22 = desc1.getMaxSize();
                    boolean valid1 = desc1.getValid();
                    FUFileInfo info1 = desc1.getInfo();
                    String fileName22 = info1.getFileName();
                    int fileSize22 = info1.getFileSize();
                    int fileCrc3222 = info1.getFileCrc32();
                    int fileOffset22 = info1.getFileOffset();
                    int crc32AtOffset22 = info1.getCrc32AtOffset();
                    List<ProtoBufFileUpdateInfo> exits = new LinkedList<>();
                    ProtoBufFileUpdateInfo updateExit = new ProtoBufFileUpdateInfo();
                    updateExit.setType(filesUpdateResponse.getParamsCase().getNumber());
                    updateExit.setFuType(fd2);
                    updateExit.setStatus(status2);
                    updateExit.setMaxSize(maxSize22);
                    updateExit.setValid(valid1);
                    updateExit.setFileName(fileName22);
                    updateExit.setFileSize(fileSize22);
                    updateExit.setFileCrc32(fileCrc3222);
                    updateExit.setFileOffset(fileOffset22);
                    updateExit.setCrc32AtOffset(crc32AtOffset22);
                    exits.add(updateExit);
                    return JsonTool.toJson(exits);
            }
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
        }
        return null;
    }

    public static String parseFFConnectParmas(byte[] bytes) {
        String result = "";
        try {
            ConParamsUpdate conParamsUpdate = ConParamsUpdate.parseFrom(bytes);
            int mtu = conParamsUpdate.getMtu();
            int intervalMs = conParamsUpdate.getIntervalMs();
            int maxSize = conParamsUpdate.getMaxSize();
            int timeoutMs = conParamsUpdate.getTimeoutMs();
            ProtoBufConnectParmas connectParmas = new ProtoBufConnectParmas();
            connectParmas.setIntervalMs(intervalMs);
            connectParmas.setMaxSize(maxSize);
            connectParmas.setTimeoutMs(timeoutMs);
            connectParmas.setMtu(mtu);
            return JsonTool.toJson(connectParmas);
        } catch (InvalidProtocolBufferException e) {
            ThrowableExtension.printStackTrace(e);
            return result;
        }
    }
}
