package com.iwown.device_module.common.Bluetooth.receiver.proto.dao;

import android.os.Environment;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.app.nativeinvoke.NativeInvoker;
import com.iwown.app.nativeinvoke.SA_SleepBufInfo;
import com.iwown.app.nativeinvoke.SA_SleepDataInfo;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.sleep_data.SleepScoreHandler;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FilePath;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.SleepSegment;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.sync.proto.ComplexPropertyPreFilter;
import com.iwown.device_module.common.sql.File_protobuf_80data;
import com.iwown.device_module.common.sql.File_protobuf_80data.HRV;
import com.iwown.device_module.common.sql.File_protobuf_80data.HeartRate;
import com.iwown.device_module.common.sql.File_protobuf_80data.Pedo;
import com.iwown.device_module.common.sql.File_protobuf_80data.Sleep;
import com.iwown.device_module.common.sql.ProtoBuf_80_data;
import com.iwown.device_module.common.sql.ProtoBuf_index_80;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.SingleThreadUtil;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsLongitudeRef;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsStatus;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsTrackRef;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.iwown.lib_common.file.FileUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

public class ProtoBufSleepSqlUtils {
    public static void dispSleepData(List<ProtoBuf_index_80> indexTables) {
        String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<Integer> days = new ArrayList<>();
        if (indexTables != null) {
            for (ProtoBuf_index_80 index80 : sort(indexTables)) {
                if ((!arrayList.contains(Integer.valueOf(index80.getYear())) || !arrayList2.contains(Integer.valueOf(index80.getMonth())) || !days.contains(Integer.valueOf(index80.getDay()))) && index80.getIndexType() == 0) {
                    arrayList.add(Integer.valueOf(index80.getYear()));
                    arrayList2.add(Integer.valueOf(index80.getMonth()));
                    days.add(Integer.valueOf(index80.getDay()));
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                Log.e("yanxi---msg", "yanxi--" + arrayList.get(i) + HelpFormatter.DEFAULT_OPT_PREFIX + arrayList2.get(i) + HelpFormatter.DEFAULT_OPT_PREFIX + days.get(i));
                List<ProtoBuf_80_data> index80s = DataSupport.where("uid= ? and year=? and month=? and day=? and data_from=?", ContextUtil.getLUID() + "", arrayList.get(i) + "", arrayList2.get(i) + "", days.get(i) + "", dataFrom).order("seq asc").find(ProtoBuf_80_data.class);
                if (index80s != null && index80s.size() > 0) {
                    ArrayList arrayList3 = new ArrayList();
                    for (ProtoBuf_80_data index : index80s) {
                        File_protobuf_80data file_protobuf_80data = new File_protobuf_80data();
                        Sleep sleep = new Sleep();
                        sleep.setA((int[]) JsonTool.fromJson(index.getSleepData(), int[].class));
                        if (index.isShutdown()) {
                            sleep.setS(1);
                        } else {
                            sleep.setS(0);
                        }
                        if (index.isCharge()) {
                            sleep.setC(1);
                        } else {
                            sleep.setC(0);
                        }
                        HeartRate heartRate = new HeartRate();
                        heartRate.setX(index.getMax_bpm());
                        heartRate.setN(index.getMin_bpm());
                        heartRate.setA(index.getAvg_bpm());
                        HRV hrv = new HRV();
                        hrv.setS(index.getSDNN());
                        hrv.setR(index.getRMSSD());
                        hrv.setP(index.getPNN50());
                        hrv.setM(index.getMEAN());
                        hrv.setF(index.getFatigue());
                        Pedo pedo = new Pedo();
                        pedo.setS(index.getStep());
                        pedo.setD((int) index.getDistance());
                        pedo.setC(index.getCalorie());
                        pedo.setT(index.getType());
                        pedo.setA(index.getState());
                        file_protobuf_80data.setQ(index.getSeq());
                        file_protobuf_80data.setT(file_protobuf_80data.parseTime(index.getHour(), index.getMinute()));
                        file_protobuf_80data.setE(sleep);
                        file_protobuf_80data.setP(pedo);
                        file_protobuf_80data.setH(heartRate);
                        file_protobuf_80data.setV(hrv);
                        arrayList3.add(file_protobuf_80data);
                    }
                    DateUtil dateUtil = new DateUtil(((Integer) arrayList.get(i)).intValue(), ((Integer) arrayList2.get(i)).intValue(), ((Integer) days.get(i)).intValue());
                    String data = dateUtil.getSyyyyMMddDate();
                    ComplexPropertyPreFilter filter = new ComplexPropertyPreFilter();
                    Map<Class<?>, String[]> includes = new HashMap<>();
                    Map<Class<?>, String[]> excludes = new HashMap<>();
                    excludes.put(Pedo.class, new String[]{"t", "a", "c", "s", "d"});
                    excludes.put(HeartRate.class, new String[]{"n", "x", "a"});
                    excludes.put(HRV.class, new String[]{"s", "r", "p", "m", "f"});
                    excludes.put(Sleep.class, new String[]{"a", "c", "s"});
                    includes.put(File_protobuf_80data.class, new String[]{"Q", GpsTrackRef.TRUE_DIRECTION, GpsLongitudeRef.EAST, "H", "P", GpsStatus.INTEROPERABILITY});
                    filter.setExcludes(excludes);
                    filter.setIncludes(includes);
                    writeSleep(data, JsonTool.toJson(arrayList3, filter), dataFrom);
                }
            }
        }
    }

    public static void dispSleepData(int year, int month, int day) {
        String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        Log.e("yanxi---msg", "yanxi--" + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day);
        List<ProtoBuf_80_data> index80s = DataSupport.where("uid= ? and year=? and month=? and day=? and data_from=?", ContextUtil.getLUID() + "", year + "", month + "", day + "", dataFrom).order("seq asc").find(ProtoBuf_80_data.class);
        if (index80s != null && index80s.size() > 0) {
            List<File_protobuf_80data> protobufLists = new ArrayList<>();
            for (ProtoBuf_80_data index : index80s) {
                File_protobuf_80data file_protobuf_80data = new File_protobuf_80data();
                Sleep sleep = new Sleep();
                sleep.setA((int[]) JsonTool.fromJson(index.getSleepData(), int[].class));
                if (index.isShutdown()) {
                    sleep.setS(1);
                } else {
                    sleep.setS(0);
                }
                if (index.isCharge()) {
                    sleep.setC(1);
                } else {
                    sleep.setC(0);
                }
                HeartRate heartRate = new HeartRate();
                heartRate.setX(index.getMax_bpm());
                heartRate.setN(index.getMin_bpm());
                heartRate.setA(index.getAvg_bpm());
                HRV hrv = new HRV();
                hrv.setS(index.getSDNN());
                hrv.setR(index.getRMSSD());
                hrv.setP(index.getPNN50());
                hrv.setM(index.getMEAN());
                hrv.setF(index.getFatigue());
                Pedo pedo = new Pedo();
                pedo.setS(index.getStep());
                pedo.setD((int) index.getDistance());
                pedo.setC(index.getCalorie());
                pedo.setT(index.getType());
                pedo.setA(index.getState());
                file_protobuf_80data.setQ(index.getSeq());
                file_protobuf_80data.setT(file_protobuf_80data.parseTime(index.getHour(), index.getMinute()));
                file_protobuf_80data.setE(sleep);
                file_protobuf_80data.setP(pedo);
                file_protobuf_80data.setH(heartRate);
                file_protobuf_80data.setV(hrv);
                protobufLists.add(file_protobuf_80data);
            }
            DateUtil dateUtil = new DateUtil(year, month, day);
            String data = dateUtil.getSyyyyMMddDate();
            ComplexPropertyPreFilter filter = new ComplexPropertyPreFilter();
            Map<Class<?>, String[]> includes = new HashMap<>();
            Map<Class<?>, String[]> excludes = new HashMap<>();
            excludes.put(Pedo.class, new String[]{"t", "a", "c", "s", "d"});
            excludes.put(HeartRate.class, new String[]{"n", "x", "a"});
            excludes.put(HRV.class, new String[]{"s", "r", "p", "m", "f"});
            excludes.put(Sleep.class, new String[]{"a", "c", "s"});
            includes.put(File_protobuf_80data.class, new String[]{"Q", GpsTrackRef.TRUE_DIRECTION, GpsLongitudeRef.EAST, "H", "P", GpsStatus.INTEROPERABILITY});
            filter.setExcludes(excludes);
            filter.setIncludes(includes);
            writeSleep(data, JsonTool.toJson(protobufLists, filter), dataFrom);
        }
    }

    public static void localSleepDataToSleepFinal(SA_SleepBufInfo f1SleepData, String date) {
        int activity;
        KLog.i("==========mtkLocalSleepDataToSleepFinal==========" + JsonTool.toJson(f1SleepData));
        if (f1SleepData.sleepdata == null) {
            return;
        }
        if (f1SleepData.datastatus == 0 || f1SleepData.datastatus == 1) {
            List<SleepSegment> segList = new ArrayList<>();
            SA_SleepDataInfo[] sleepData = f1SleepData.sleepdata;
            if (sleepData.length > 0) {
                int totalDeep = 0;
                int totalLight = 0;
                int totalWakeUp = 0;
                SleepSegment tampSeg = new SleepSegment();
                for (int i = 0; i < sleepData.length; i++) {
                    SA_SleepDataInfo bean = sleepData[i];
                    SleepSegment segment = new SleepSegment();
                    int start = (bean.startTime.hour * 60) + bean.startTime.minute;
                    int end = (bean.stopTime.hour * 60) + bean.stopTime.minute;
                    if (start <= end) {
                        activity = end - start;
                    } else {
                        activity = (end + 1440) - start;
                    }
                    KLog.e(start + "===no2855-->=start" + end + "end" + activity + "activity=====");
                    if (i == 0) {
                        segment.setSt(0);
                        segment.setEt(activity);
                        segment.setType(bean.sleepMode);
                        tampSeg = segment;
                        segList.add(0, segment);
                    } else if (i > 0) {
                        segment.setSt(tampSeg.getEt());
                        segment.setEt(tampSeg.getEt() + activity);
                        segment.setType(bean.sleepMode);
                        segList.add(segment);
                        tampSeg = segment;
                    }
                    int sleepType = bean.sleepMode;
                    if (sleepType == 3) {
                        totalDeep += activity;
                    } else if (sleepType == 4) {
                        totalLight += activity;
                    } else if (sleepType == 6) {
                        totalWakeUp += activity;
                    }
                }
                DateUtil startDateUtil = new DateUtil(f1SleepData.inSleepTime.year + 2000, f1SleepData.inSleepTime.month, f1SleepData.inSleepTime.day, f1SleepData.inSleepTime.hour, f1SleepData.inSleepTime.minute);
                DateUtil endDateUtil = new DateUtil(f1SleepData.outSleepTime.year + 2000, f1SleepData.outSleepTime.month, f1SleepData.outSleepTime.day, f1SleepData.outSleepTime.hour, f1SleepData.outSleepTime.minute);
                TB_SLEEP_Final_DATA sleepDataByDate1 = Mtk_DeviceBaseInfoSqlUtil.getSleepDataByDate1(ContextUtil.app, date);
                if (sleepDataByDate1 == null) {
                    sleepDataByDate1 = new TB_SLEEP_Final_DATA();
                    sleepDataByDate1.setToDefault("_uploaded");
                    sleepDataByDate1.setUid(ContextUtil.getLUID());
                    sleepDataByDate1.setData_from(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
                    sleepDataByDate1.setDate(date);
                }
                try {
                    int score = SleepScoreHandler.calSleepScore(totalDeep + totalLight + totalWakeUp, totalDeep, startDateUtil.getUnixTimestamp());
                    sleepDataByDate1.setYear(startDateUtil.getYear());
                    sleepDataByDate1.setMonth(startDateUtil.getMonth());
                    sleepDataByDate1.setStart_time(startDateUtil.getUnixTimestamp());
                    sleepDataByDate1.setEnd_time(endDateUtil.getUnixTimestamp());
                    sleepDataByDate1.setDeepSleepTime((float) totalDeep);
                    sleepDataByDate1.setLightSleepTime((float) totalLight);
                    sleepDataByDate1.setScore(score);
                    sleepDataByDate1.setSleep_segment(JsonUtils.toJson(segList));
                    sleepDataByDate1.saveAsync().listen(new SaveCallback() {
                        public void onFinish(boolean success) {
                            HealthDataEventBus.updateHealthSleepEvent();
                        }
                    });
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }
    }

    public static TB_SLEEP_Final_DATA getSleepDataByDate(String date) {
        return (TB_SLEEP_Final_DATA) DataSupport.where("date =? and data_from=?", date, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name)).findFirst(TB_SLEEP_Final_DATA.class);
    }

    public static SA_SleepBufInfo getSleep(String date, String data_from) {
        try {
            SA_SleepBufInfo retData = new SA_SleepBufInfo();
            String path = FilePath.ProtoBuf_Ble_80_Sleep_Dir + date + "/uid-" + ContextUtil.getLUID() + "-date-" + date + "-source-" + data_from + ".json";
            KLog.d("testSleep", "testSleep睡眠：" + path + "   存在？" + FileUtils.checkFileExists(path));
            if (FileUtils.checkFileExists(path)) {
                retData.datastatus = new NativeInvoker().calculateSleep(Environment.getExternalStorageDirectory().getAbsolutePath() + FilePath.ProtoBuf_Ble_80_Sleep_Dir, ContextUtil.getLUID(), date, data_from, 1, retData);
                KLog.d("testSleep", "testSleep: " + JsonTool.toJson(retData));
                localSleepDataToSleepFinal(retData, date);
                return retData;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return null;
    }

    public static void writeSleep(final String data, final String msg, final String data_from) {
        try {
            if (((String) SingleThreadUtil.getLogSingleThread().submit(new Runnable() {
                public void run() {
                    try {
                        FileIOUtils.write2SDFromString(FilePath.ProtoBuf_Ble_80_Sleep_Dir + data + "/", "uid-" + ContextUtil.getLUID() + "-date-" + data + "-source-" + data_from + ".json", msg, false);
                    } catch (Exception e1) {
                        ThrowableExtension.printStackTrace(e1);
                    }
                }
            }, "OK").get()).equals("OK")) {
                KLog.e("testSleep", JsonTool.toJson(getSleep(data, data_from)));
            }
        } catch (InterruptedException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (ExecutionException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    private static List<ProtoBuf_index_80> sort(List<ProtoBuf_index_80> index80s) {
        Collections.sort(index80s, new Comparator<ProtoBuf_index_80>() {
            public int compare(ProtoBuf_index_80 index1, ProtoBuf_index_80 index2) {
                int i = (index1.getYear() * 380) + (index1.getMonth() * 31) + index1.getDay();
                int i2 = (index2.getYear() * 380) + (index2.getMonth() * 31) + index2.getDay();
                if (i > i2) {
                    return 1;
                }
                if (i == i2) {
                    return 0;
                }
                return -1;
            }
        });
        return index80s;
    }
}
