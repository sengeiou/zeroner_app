package com.iwown.device_module.common.Bluetooth.receiver.mtk.utils;

import android.content.ContentValues;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FilePath;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.MyDay;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.resp.F1SleepData;
import com.iwown.device_module.common.network.data.resp.UpSDFileCode;
import com.iwown.device_module.common.sql.RawData68;
import com.iwown.device_module.common.sql.TB_61_data;
import com.iwown.device_module.common.sql.TB_62_data;
import com.iwown.device_module.common.sql.TB_f1_index;
import com.iwown.device_module.common.sql.TB_http_manage;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.TB_sport_ball;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.iwown.device_module.common.sql.TB_sport_other;
import com.iwown.device_module.common.sql.headset.DataIndex_68;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel.HttpType;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class MtkDataToServer {
    /* access modifiers changed from: private */
    public static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingDeque());
    private static boolean isTwo;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void syncUpCmdToServer() {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                MtkDataToServer.upCmdToServer();
            }
        });
    }

    public static void upCmdToServer() {
        isTwo = true;
        final long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        final String deviceName = ContextUtil.getDeviceNameNoClear() + "";
        List<TB_f1_index> fileList = DataSupport.where("uid=? and data_from=? and has_file=1", uid + "", deviceName).order("time desc").find(TB_f1_index.class);
        if (fileList != null && fileList.size() > 0) {
            for (TB_f1_index tb_f1_index : fileList) {
                String date = tb_f1_index.getDate();
                String log_file = "/Zeroner/iwownfit_5_0/blelog/61_data/" + uid + "_" + date + "_" + deviceName + ".txt";
                FileUtils.clearInfoForFile(log_file, "/Zeroner/iwownfit_5_0/blelog/61_data/");
                if (FileUtils.checkFileExists(log_file)) {
                    FileUtils.deleteFile(log_file);
                }
                String sd1 = FilePath.Mtk_Ble_61_Sleep_Dir + date + "/";
                String fileName = "uid-" + uid + "-date-" + date + "-source-" + deviceName;
                L.file("no2855 61数据写入文件:日期" + date, 3);
                FileUtils.clearInfoForFile(sd1 + fileName, FilePath.Mtk_Ble_61_Sleep_Dir);
                if (FileUtils.checkFileExists(sd1 + fileName)) {
                    FileUtils.deleteFile(sd1 + fileName);
                }
                DateUtil dateUtil = new DateUtil(tb_f1_index.getTime(), true);
                KLog.e("no2855--> 写入文件的日期: " + dateUtil.getSyyyyMMddDate());
                List<TB_61_data> list61 = DataSupport.where("uid=? and data_from=? and year=? and month=? and day=?", String.valueOf(uid), deviceName, dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "").order("time asc").find(TB_61_data.class);
                for (int j = 0; j < list61.size(); j++) {
                    isTwo = true;
                    sleepCmdSaveToFile((TB_61_data) list61.get(j));
                }
                tb_f1_index.setHas_file(2);
                tb_f1_index.update(tb_f1_index.getId());
                MtkToIvHandler.save61FileSleep(date);
            }
        }
        EventBus.getDefault().post(new ViewRefresh(false, 97));
        List<TB_f1_index> httpList = DataSupport.where("uid=? and data_from=? and has_up=1", uid + "", deviceName).order("time desc").find(TB_f1_index.class);
        if (httpList != null && httpList.size() > 0) {
            L.file("上传61文件大小: " + httpList.size(), 3);
            for (TB_f1_index tb_f1_index2 : httpList) {
                final TB_f1_index tB_f1_index = tb_f1_index2;
                NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                    public void onSuccess(UpSDFileCode upSDFileCode) {
                        try {
                            KLog.e("61dataUp", "http上传11111161文件成功--" + tB_f1_index.getDate());
                            if (upSDFileCode != null && upSDFileCode.getRetCode() == 0) {
                                tB_f1_index.setHas_up(2);
                                tB_f1_index.update(tB_f1_index.getId());
                                KLog.e("61dataUp", "httpno2855上传11111161文件成功--" + tB_f1_index.getDate() + JsonUtils.toJson(upSDFileCode));
                                ContentValues values = new ContentValues();
                                values.put("upload", Integer.valueOf(1));
                                values.put("up_time", Long.valueOf(System.currentTimeMillis() / 1000));
                                DataSupport.updateAll(TB_http_manage.class, values, "uid=? and data_from=? and type=? and date=?", uid + "", deviceName, HttpType.F1_61_UP, tB_f1_index.getDate());
                                MtkDataToServer.downLoad(true, tB_f1_index.getDate());
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }

                    public void onFail(Throwable e) {
                    }
                }).upSd_61_File(uid, tb_f1_index2.getDate(), deviceName, new File(Environment.getExternalStorageDirectory() + "/Zeroner/iwownfit_5_0/blelog/61_data/" + uid + "_" + tb_f1_index2.getDate() + "_" + deviceName + ".txt"));
            }
        }
    }

    public static void upload68ToServer() {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        List<RawData68> rawDataList = DataSupport.where("uid = ? and data_from = ?", String.valueOf(uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name)).order("year,month,day,hour,min,second desc").find(RawData68.class);
        RawData68 prevData = null;
        List<RawData68> availableList = new ArrayList<>();
        for (RawData68 data : rawDataList) {
            if (prevData == null) {
                availableList.add(data);
            } else if (prevData.getYear() == data.getYear() && prevData.getMonth() == data.getMonth() && prevData.getDay() == data.getDay()) {
                availableList.add(data);
            } else {
                generate68FileAndUpload(availableList);
                availableList.clear();
                availableList.add(data);
            }
            prevData = data;
        }
        if (availableList != null && availableList.size() > 0) {
            generate68FileAndUpload(availableList);
        }
    }

    private static void generate68FileAndUpload(List<RawData68> rawDataList) {
        if (rawDataList == null || rawDataList.size() == 0) {
            KLog.e("no 68 raw data to upload");
            return;
        }
        String data_from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        final RawData68 data = (RawData68) rawDataList.get(0);
        final String date = String.format("%04d%02d%02d", new Object[]{Integer.valueOf(data.getYear()), Integer.valueOf(data.getMonth()), Integer.valueOf(data.getDay())});
        String directory = FilePath.Mtk_Ble_68_Data_Dir + date + "/";
        String fileName = "uid-" + ContextUtil.getUID() + "-date-" + date + "-source-" + data_from;
        if (FileUtils.checkFileExists(directory + fileName)) {
            FileUtils.deleteFile(directory + fileName);
        }
        for (int i = 0; i < rawDataList.size(); i++) {
            write2SDFromString_1(directory, fileName, ((RawData68) rawDataList.get(i)).getRaw_data());
        }
        final File file = new File(Environment.getExternalStorageDirectory() + "/" + directory + fileName);
        NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
            public void onSuccess(UpSDFileCode upSDFileCode) {
                try {
                    KLog.e("上传68文件--" + date);
                    if (upSDFileCode != null) {
                        KLog.e("retcode:" + String.valueOf(upSDFileCode.getRetCode()));
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(1);
                        int month = cal.get(2) + 1;
                        int day = cal.get(5);
                        if (year != data.getYear() || month != data.getMonth() || day != data.getDay()) {
                            long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                            DataSupport.deleteAll(DataIndex_68.class, "uid=? and device_name=? and year=? and month=? and day=?", String.valueOf(uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name), String.valueOf(data.getYear()), String.valueOf(data.getMonth()), String.valueOf(data.getDay()));
                            file.delete();
                        }
                    }
                } catch (Exception e) {
                    KLog.e(String.format("68 upload failed : %s", new Object[]{e.getMessage()}));
                    ThrowableExtension.printStackTrace(e);
                }
            }

            public void onFail(Throwable e) {
                KLog.e(String.format("68 upload failed : %s", new Object[]{e.getLocalizedMessage()}));
            }
        }).upSd_68_File(UserConfig.getInstance().getNewUID(), date, data_from, file);
    }

    private static synchronized void sleepCmdSaveToFile(TB_61_data data) {
        synchronized (MtkDataToServer.class) {
            String date = new DateUtil(data.getYear(), data.getMonth(), data.getDay()).getSyyyyMMddDate();
            String fileName = "uid-" + data.getUid() + "-date-" + date + "-source-" + data.getData_from();
            String path = FilePath.Mtk_Ble_61_Sleep_Dir + date + "/";
            String fileName_1 = data.getUid() + "_" + date + "_" + data.getData_from() + ".txt";
            write2SDFromString_1(path, fileName, data.getCmd());
            write2SDFromString_1("/Zeroner/iwownfit_5_0/blelog/61_data/", fileName_1, data.getCmd());
        }
    }

    public static File creatSDDir(String dirName) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f A[SYNTHETIC, Splitter:B:16:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b A[SYNTHETIC, Splitter:B:22:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString_1(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r1 = 0
            r2 = 0
            creatSDDir(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r4.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = createSDFile(r4)     // Catch:{ Exception -> 0x0039 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0039 }
            r4 = 1
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0039 }
            r3.write(r7)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            r3.flush()     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            if (r3 == 0) goto L_0x0031
            r3.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0031:
            r2 = r3
        L_0x0032:
            return r1
        L_0x0033:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x0032
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x0032
        L_0x0043:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0032
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004e:
            throw r4
        L_0x004f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004e
        L_0x0054:
            r4 = move-exception
            r2 = r3
            goto L_0x0049
        L_0x0057:
            r0 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkDataToServer.write2SDFromString_1(java.lang.String, java.lang.String, java.lang.String):java.io.File");
    }

    public static File createSDFile(String fileName) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
            KLog.e("---create file : " + Environment.getExternalStorageDirectory() + "/" + fileName);
        }
        return file;
    }

    public static void downLoad(boolean isWait, final String date) {
        if (date != null) {
            if (!DataSupport.isExist(TB_http_manage.class, "uid=? and date=? and data_from=? and type=? and upload=?", UserConfig.getInstance().getNewUID() + "", date, ContextUtil.getDeviceNameNoClear() + "", HttpType.F1_SLEEP_DOWN, "1")) {
                if (!DataSupport.isExist(TB_http_manage.class, "uid=? and date=? and data_from=? and type=? and upload=?", UserConfig.getInstance().getNewUID() + "", date, ContextUtil.getDeviceNameNoClear() + "", HttpType.F1_SLEEP_DOWN, "0")) {
                    TB_http_manage manage = new TB_http_manage();
                    manage.setDate(date);
                    manage.setUid(UserConfig.getInstance().getNewUID());
                    manage.setType(HttpType.F1_SLEEP_DOWN);
                    manage.setUpload(0);
                    manage.setData_from(ContextUtil.getDeviceNameNoClear() + "");
                    manage.save();
                }
                if (isWait) {
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            MtkDataToServer.downLoadSleepP1(date);
                        }
                    }, 20000);
                } else {
                    downLoadSleepP1(date);
                }
            }
        }
    }

    public static void downLoadSleepP1(final String date) {
        NetFactory.getInstance().getClient(new MyCallback<F1SleepData>() {
            public void onSuccess(F1SleepData f1SleepData) {
                if (f1SleepData != null && f1SleepData.getRetCode() == 0) {
                    MtkToIvHandler.mtkServerSleepDataToSleepFinal(f1SleepData, date);
                    if (date.equals(new DateUtil().getSyyyyMMddDate())) {
                        EventBus.getDefault().post(new ViewRefresh(false, 40));
                    } else {
                        EventBus.getDefault().post(new ViewRefresh(false, 17));
                    }
                }
            }

            public void onFail(Throwable e) {
            }
        }).downSleepData(date, DeviceUtils.autoHeartStatue().isHeart_switch());
    }

    public static void syncSaveTodayCmd() {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                MtkDataToServer.saveTodayCmd();
            }
        });
    }

    public static void saveTodayCmd() {
        isTwo = false;
        String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + "";
        DateUtil dateUtil = new DateUtil();
        List<MyDay> days = new ArrayList<>();
        days.add(new MyDay(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), dateUtil.getSyyyyMMddDate()));
        dateUtil.addDay(-1);
        days.add(new MyDay(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), dateUtil.getSyyyyMMddDate()));
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        for (int i = 0; i < days.size(); i++) {
            List<TB_61_data> list61 = DataSupport.where("uid=? and data_from=? and year=? and month=? and day=?", String.valueOf(uid), deviceName, ((MyDay) days.get(i)).getYear() + "", ((MyDay) days.get(i)).getMonth() + "", ((MyDay) days.get(i)).getDay() + "").order("time asc").find(TB_61_data.class);
            if (list61.size() > 0) {
                if (isTwo) {
                    break;
                }
                String date = ((MyDay) days.get(i)).getDate();
                L.file("no2855 61数据写入今天文件:" + date, 3);
                String sd1 = FilePath.Mtk_Ble_61_Sleep_Dir + date + "/";
                String path1 = sd1 + ("uid-" + uid + "-date-" + date + "-source-" + deviceName);
                FileUtils.clearInfoForFile(path1, FilePath.Mtk_Ble_61_Sleep_Dir);
                if (FileUtils.checkFileExists(path1)) {
                    FileUtils.deleteFile(path1);
                }
                for (int j = 0; j < list61.size() && !isTwo; j++) {
                    sleepCmdSaveToFile((TB_61_data) list61.get(j));
                }
            }
        }
        MtkToIvHandler.save61FileSleep(new DateUtil().getSyyyyMMddDate());
    }

    public static void upOldCmd62ToServer() {
        List<TB_62_data> list = DataSupport.where("uid=? and data_from =? ", String.valueOf(UserConfig.getInstance().getNewUID()), ContextUtil.getDeviceNameNoClear() + "").order("time asc").find(TB_62_data.class);
        Set<String> set = new HashSet<>();
        KLog.i("62dataUp" + list.size());
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TB_62_data data = (TB_62_data) list.get(i);
                String date = new DateUtil(data.getYear(), data.getMonth(), data.getDay()).getSyyyyMMddDate();
                String path = "/Zeroner/iwownfit_5_0/blelog/62_data/" + ContextUtil.getUID() + "_" + date + "_" + ContextUtil.getDeviceNameNoClear() + ".txt";
                FileUtils.clearInfoForFile(path, "/Zeroner/iwownfit_5_0/blelog/62_data/");
                try {
                    if (FileUtils.checkFileExists(path)) {
                        FileUtils.deleteFile(path);
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                set.add(date);
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                gnssCmdSaveToFile((TB_62_data) list.get(i2));
            }
            for (final String date2 : set) {
                final String deviceName = ContextUtil.getDeviceNameNoClear() + "";
                NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                    public void onSuccess(UpSDFileCode upSDFileCode) {
                        if (upSDFileCode != null && upSDFileCode.getRetCode() == 0) {
                            KLog.e("no2855上传62文件成功--" + UserConfig.getInstance().getNewUID() + "_" + date2 + "_" + deviceName + ".txt");
                        }
                    }

                    public void onFail(Throwable e) {
                        KLog.e("no2855上传62文件失败--" + UserConfig.getInstance().getNewUID() + "_" + date2 + "_" + deviceName + ".txt");
                        KLog.i("===========" + e.toString());
                    }
                }).upGnssDataToServer(date2, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zeroner/iwownfit_5_0/blelog/62_data/" + ContextUtil.getUID() + "_" + date2 + "_" + deviceName + ".txt"));
            }
        }
    }

    public static void upCmd62ToServer() {
        final long uid = ContextUtil.getLUID();
        String dataFrom = ContextUtil.getDeviceNameNoClear() + "";
        DateUtil dateUtil12 = new DateUtil();
        if (((TB_mtk_statue) DataSupport.where("uid=? and date>? and type=?", uid + "", ((System.currentTimeMillis() / 1000) - 345600) + "", "1").findFirst(TB_mtk_statue.class)) == null) {
            for (int i = 0; i < 5; i++) {
                DataUtil.writeMtkGps2TB(uid, dataFrom, dateUtil12.getYear(), dateUtil12.getMonth(), dateUtil12.getDay(), false);
                dateUtil12.addDay(-1);
            }
        }
        List<TB_mtk_statue> mtk_statues = DataSupport.where("uid=? and data_from=? and type=? and has_file!=?", uid + "", dataFrom, "62", "1").order("date desc").find(TB_mtk_statue.class);
        KLog.e("no2855--> 62入文件mtk_statues。查询条件 " + uid + " - " + dataFrom);
        if (mtk_statues != null && mtk_statues.size() > 0) {
            L.file("no2855 62入文件:要写入的个数 " + mtk_statues.size(), 3);
            for (TB_mtk_statue mtk_statue : mtk_statues) {
                long site = System.currentTimeMillis();
                KLog.e("no2855--> 62入文件:mtk_statues。size: " + mtk_statue.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + mtk_statue.getDay());
                if (mtk_statue.getHas_tb() != 1) {
                    DataUtil.writeMtkGps2TB(uid, dataFrom, mtk_statue.getYear(), mtk_statue.getMonth(), mtk_statue.getDay(), false);
                }
                DataUtil.writeBlueGps2SD(uid, dataFrom, mtk_statue.getYear(), mtk_statue.getMonth(), mtk_statue.getDay(), true);
                mtk_statue.setHas_file(1);
                mtk_statue.update(mtk_statue.getId());
                KLog.e("no2855--> 62入文件:耗时: " + (System.currentTimeMillis() - site));
            }
        }
        DateUtil dateUtil = new DateUtil();
        dateUtil.addDay(-25);
        DataSupport.deleteAll(TB_mtk_statue.class, "date<?", dateUtil.getUnixTimestamp() + "");
        mHandler.postDelayed(new Runnable() {
            public void run() {
                MtkDataToServer.fixedThreadPool.execute(new Runnable() {
                    public void run() {
                        KLog.e("no2855--> 62入文件:耗时:开始上传数据 ");
                        MtkDataToServer.uploadMtkData(uid);
                    }
                });
            }
        }, BootloaderScanner.TIMEOUT);
    }

    public static void uploadMtkData(long uid) {
        List<TB_sport_gps_segment> sportGpsSegments = DataSupport.where("uid=? and upload=?", uid + "", "0").find(TB_sport_gps_segment.class);
        if (sportGpsSegments != null && sportGpsSegments.size() > 0) {
            for (TB_sport_gps_segment sportGpsSegment : sportGpsSegments) {
                if ("1".equals(sportGpsSegment.getGps_url())) {
                    boolean hasHr = "1".equals(sportGpsSegment.getHeart_url());
                    KLog.e("no2855--> 心率存在: " + hasHr);
                    uploadGps(uid, sportGpsSegment.getStart_time(), sportGpsSegment.getEnd_time(), sportGpsSegment.getData_from(), hasHr, sportGpsSegment.getSport_type());
                } else {
                    SportDeviceNetWorkUtil.uploadHr(true, uid, sportGpsSegment.getStart_time(), sportGpsSegment.getEnd_time(), sportGpsSegment.getData_from(), sportGpsSegment.getSport_type());
                }
            }
        }
        List<TB_sport_ball> sport_balls = DataSupport.where("uid=? and upload_type=?", uid + "", "0").find(TB_sport_ball.class);
        if (sport_balls != null && sport_balls.size() > 0) {
            for (TB_sport_ball sportGpsSegment2 : sport_balls) {
                SportDeviceNetWorkUtil.uploadHr(false, uid, sportGpsSegment2.getStart_time(), sportGpsSegment2.getEnd_time(), sportGpsSegment2.getData_from(), sportGpsSegment2.getSport_type());
            }
        }
        List<TB_sport_other> sport_others = DataSupport.where("uid=? and upload_type=?", uid + "", "0").find(TB_sport_other.class);
        if (sport_others != null && sport_others.size() > 0) {
            for (TB_sport_other sportGpsSegment3 : sport_others) {
                SportDeviceNetWorkUtil.uploadHr(false, uid, sportGpsSegment3.getStart_time(), sportGpsSegment3.getEnd_time(), sportGpsSegment3.getData_from(), sportGpsSegment3.getSport_type());
            }
        }
    }

    private static void uploadGps(long uid, long startTime, long endTime, String dataFrom, boolean hasHr, int sportType) {
        String path1 = Environment.getExternalStorageDirectory() + LogPath.GPS_PATH + uid + "_gps_" + startTime + "_" + dataFrom;
        String txtFile = path1 + ".txt";
        final String zipFile = path1 + ".zip";
        if (!new File(txtFile).exists()) {
            KLog.e("no2855想上传又没得上传:??? " + txtFile);
            return;
        }
        String st = new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate();
        String ed = new DateUtil(endTime, true).getYyyyMMdd_HHmmssDate();
        if (ZipUtil.zip(txtFile, zipFile)) {
            final long j = uid;
            final long j2 = startTime;
            final String str = dataFrom;
            final boolean z = hasHr;
            final long j3 = endTime;
            final int i = sportType;
            NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                public void onSuccess(UpSDFileCode upSDFileCode) {
                    FileUtils.deleteFile(new File(zipFile));
                    if (upSDFileCode != null) {
                        try {
                            if (upSDFileCode.getRetCode() == 0) {
                                DataUtil.upGpsSportOneUrl(upSDFileCode.getUrl(), 0, j, j2, str, 0);
                                if (z) {
                                    SportDeviceNetWorkUtil.uploadHr(true, j, j2, j3, str, i);
                                }
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                    FileUtils.deleteFile(new File(zipFile));
                }
            }).upSportGpsFile(uid, st, ed, dataFrom, new File(zipFile));
        }
    }

    private static void gnssCmdSaveToFile(TB_62_data data) {
        String date = new DateUtil(data.getYear(), data.getMonth(), data.getDay()).getSyyyyMMddDate();
        L.writeFileToSd("/Zeroner/iwownfit_5_0/blelog/62_data/", ContextUtil.getLUID() + "_" + date + "_" + PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + ".txt", data.getCmd());
    }
}
