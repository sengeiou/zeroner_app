package com.iwown.lib_common.log;

import android.os.Environment;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.iwown.lib_common.file.FileUtils;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;

public class L {
    public static final String Bluetooth_App_Path = "app";
    public static final String Bluetooth_Firmware_Upgrade = "firmware";
    public static final String Bluetooth_Notify_Path = "notify";
    public static final String Bluetooth_Operate_Path = "operate";
    public static final String Bluetooth_P1_Path = "p1";
    public static final String Bluetooth_Write_Path = "write";
    public static final String Root_Path = "Zeroner/zeroner/log/";
    public static String SDPATH = (Environment.getExternalStorageDirectory() + "/");
    public static final String TAG = "iwown_healthy";
    public static final int Type_A_TEST = 7;
    public static final int Type_App = 3;
    public static final int Type_Firmware_Upgrade = 6;
    public static final int Type_Notify = 2;
    public static final int Type_Operate = 4;
    public static final int Type_P1 = 5;
    public static final int Type_Write = 1;
    public static final int Type_gps = 8;
    public static final String gps_path = "gps_t";
    public static final boolean isShowLog = true;

    public static void init() {
        KLog.init(true, TAG);
        deleteOverdueFile("Zeroner/zeroner/log/");
    }

    public static void file(final String msg, final int type) {
        final String lineMethod = getLineMethod();
        SingleThreadUtil.getLogSingleThread().execute(new Runnable() {
            public void run() {
                String fileName = L.Bluetooth_App_Path;
                if (type == 1) {
                    fileName = "write";
                } else if (type == 2) {
                    fileName = "notify";
                } else if (type == 3) {
                    fileName = L.Bluetooth_App_Path;
                } else if (type == 8) {
                    fileName = L.gps_path;
                } else if (type == 4) {
                    fileName = "operate";
                } else if (type == 5) {
                    fileName = L.Bluetooth_P1_Path;
                } else if (type == 6) {
                    fileName = L.Bluetooth_Firmware_Upgrade;
                } else if (type == 7) {
                    fileName = "a_test";
                }
                L.write2Sd("[" + lineMethod + "]" + msg, fileName);
                FileUtils.createOrExistsDir(L.SDPATH + "Zeroner/zeroner/log/");
            }
        });
    }

    public static String getLineMethod() {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[4];
        return "[ (" + traceElement.getFileName() + ":" + traceElement.getLineNumber() + ")#" + traceElement.getMethodName() + " ] ";
    }

    public static void write2Sd(final String msg, final String fileName) {
        SingleThreadUtil.getLogSingleThread().execute(new Runnable() {
            public void run() {
                try {
                    FileIOUtils.writeFileFromString(L.SDPATH + "Zeroner/zeroner/log/" + new DateUtil().getYyyyMMddDate() + fileName + ".txt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ":" + msg);
                } catch (Exception e1) {
                    ThrowableExtension.printStackTrace(e1);
                }
            }
        });
    }

    public static void writeFileToSd(String path, String fileName, String msg) {
        try {
            FileUtils.write2SDFromString_1(path, fileName, msg);
        } catch (Exception e1) {
            ThrowableExtension.printStackTrace(e1);
        }
    }

    public static void deleteOverdueFile(String path) {
        try {
            DateUtil date = new DateUtil();
            date.addDay(-7);
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].lastModified() < date.getTimestamp()) {
                            FileUtils.deleteFile(files[i]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static void deleteLog(final int day) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    L.deleteFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zeroner"), day);
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void deleteFile(File file, int day) throws IOException {
        int deleteDay = day;
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length <= 0) {
                file.delete();
                return;
            }
            if ("sport_gps".equals(file.getName()) || FitnessActivities.SLEEP.equals(file.getName()) || "61_data".equals(file.getName()) || "62_data".equals(file.getName())) {
                deleteDay = 15;
            }
            for (File deleteFile : files) {
                deleteFile(deleteFile, deleteDay);
            }
            return;
        }
        if (System.currentTimeMillis() - file.lastModified() > ((long) (deleteDay * 24 * 60 * 60 * 1000))) {
            file.delete();
        }
    }

    public static void writeLog(final String path, final String msg) {
        SingleThreadUtil.getLogSingleThread().execute(new Runnable() {
            public void run() {
                try {
                    FileIOUtils.write2SDFromString("Zeroner/zeroner_3/" + path + "/", new DateUtil().getYyyyMMddDate() + "_ble.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ":" + msg);
                } catch (Exception e1) {
                    ThrowableExtension.printStackTrace(e1);
                }
            }
        });
    }
}
