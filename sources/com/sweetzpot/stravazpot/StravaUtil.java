package com.sweetzpot.stravazpot;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.consts.StravaCredential;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.eventbus.StravaTokenGetEvent;
import com.iwown.data_link.sport_data.P1_62_data;
import com.iwown.data_link.sport_data.gps.GPX;
import com.iwown.data_link.sport_data.gps.Gps;
import com.iwown.data_link.sport_data.gps.GpsUpData;
import com.iwown.data_link.sport_data.gps.LongitudeAndLatitude;
import com.iwown.data_link.sport_data.gps.Track;
import com.iwown.data_link.sport_data.gps.Waypoint;
import com.iwown.data_link.utils.GPXParser;
import com.iwown.data_link.utils.JsonUtility;
import com.iwown.data_link.utils.PositionUtility;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.utility.Constants;
import com.socks.library.KLog;
import com.sweetzpot.stravazpot.authenticaton.api.AuthenticationAPI;
import com.sweetzpot.stravazpot.authenticaton.model.AppCredentials;
import com.sweetzpot.stravazpot.authenticaton.model.LoginResult;
import com.sweetzpot.stravazpot.common.api.AuthenticationConfig;
import com.sweetzpot.stravazpot.common.api.StravaConfig;
import com.sweetzpot.stravazpot.common.api.exception.StravaAPIException;
import com.sweetzpot.stravazpot.common.api.exception.StravaUnauthorizedException;
import com.sweetzpot.stravazpot.upload.api.UploadAPI;
import com.sweetzpot.stravazpot.upload.model.DataType;
import com.sweetzpot.stravazpot.upload.model.UploadActivityType;
import com.sweetzpot.stravazpot.upload.model.UploadStatus;
import com.sweetzpot.stravazpot.upload.request.UploadFileRequest;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;

public class StravaUtil {
    public static final int FILE_NOT_FOUND_WHEN_WRITE_GPX = 2;
    public static final int GET_GPX_DATA_FAIL = 1;
    public static final int GET_GPX_DATA_SUCCESS = 0;
    public static final int GET_TOKEN_FAIL = 15;
    public static final int GET_TOKEN_SUCCESS = 14;
    public static final int OtherException_WHEN_GET_TOKEN = 11;
    public static final int OtherException_WHEN_UPLOAD_GPX = 8;
    public static final int OtherException_WHEN_WRITE_GPX = 5;
    public static final int ParserConfigurationException_WHEN_WRITE_GPX = 3;
    public static final int StravaAPIException_WHEN_GET_TOKEN = 13;
    public static final int StravaAPIException_WHEN_UPLOAD_GPX = 7;
    public static final int StravaUnauthorizedException = 6;
    private static final String TAG = "StravaUtil";
    public static final int TransformerException_WHEN_WRITE_GPX = 4;
    public static final int UPLOAD_GPX_FAIL = 10;
    public static final int UPLOAD_GPX_SUCCESS = 9;
    /* access modifiers changed from: private */
    public static final ThreadLocal<SimpleDateFormat> dFyyyyMMddHHmmss = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    public static StravaUtil instance = null;
    private static Executor mExecutor = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public long end_time;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public MyHandler mHandler = new MyHandler();
    private MyStravaCallback mStravaCallback;
    public StravaConfig mStravaConfig = null;
    /* access modifiers changed from: private */
    public int sport_type;
    /* access modifiers changed from: private */
    public Runnable startUpLoadRunnable = new Runnable() {
        public void run() {
            StravaUtil.this.upLoadGPXFile(StravaUtil.this.sport_type, StravaUtil.this.start_time, StravaUtil.this.end_time);
        }
    };
    /* access modifiers changed from: private */
    public long start_time;
    private List<P1_62_data> tb62Datas;

    private class MyHandler extends Handler {
        MyStravaCallback mStravaCallback;

        public void setStravaCallback(MyStravaCallback stravaCallback) {
            this.mStravaCallback = stravaCallback;
        }

        public MyHandler(MyStravaCallback myStravaCallback) {
            this.mStravaCallback = myStravaCallback;
        }

        public MyHandler() {
        }

        public void handleMessage(Message msg) {
            if (this.mStravaCallback != null) {
                this.mStravaCallback.onResult(msg.what);
            }
            switch (msg.what) {
                case 0:
                    StravaUtil.this.saveGPX2SD(StravaUtil.this.sport_type, StravaUtil.this.start_time, StravaUtil.this.end_time, (GPX) msg.obj);
                    return;
                case 14:
                    LoginResult result = (LoginResult) msg.obj;
                    GlobalDataUpdater.setStravaToken(StravaUtil.this.mContext, result.getToken().toString());
                    StravaUtil.this.mStravaConfig = StravaConfig.withToken(result.getToken()).debug().build();
                    return;
                default:
                    return;
            }
        }
    }

    public interface MyStravaCallback {
        void onResult(int i);
    }

    private class SaveGPXRunnable implements Runnable {
        private GPX mGPX;
        private FileOutputStream mOutputStream;

        public SaveGPXRunnable(GPX gpx, FileOutputStream out) {
            this.mGPX = gpx;
            this.mOutputStream = out;
        }

        /* JADX INFO: finally extract failed */
        public void run() {
            try {
                new GPXParser().writeGPX(this.mGPX, this.mOutputStream);
                StravaUtil.this.mHandler.postDelayed(StravaUtil.this.startUpLoadRunnable, 500);
                if (this.mOutputStream != null) {
                    try {
                        this.mOutputStream.close();
                    } catch (IOException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            } catch (ParserConfigurationException e2) {
                ThrowableExtension.printStackTrace(e2);
                StravaUtil.this.sendMsg(3, e2);
                StravaUtil.this.mHandler.removeCallbacks(StravaUtil.this.startUpLoadRunnable);
                if (this.mOutputStream != null) {
                    try {
                        this.mOutputStream.close();
                    } catch (IOException e3) {
                        ThrowableExtension.printStackTrace(e3);
                    }
                }
            } catch (TransformerException e4) {
                StravaUtil.this.sendMsg(4, e4);
                ThrowableExtension.printStackTrace(e4);
                StravaUtil.this.mHandler.removeCallbacks(StravaUtil.this.startUpLoadRunnable);
                if (this.mOutputStream != null) {
                    try {
                        this.mOutputStream.close();
                    } catch (IOException e5) {
                        ThrowableExtension.printStackTrace(e5);
                    }
                }
            } catch (Exception e6) {
                StravaUtil.this.sendMsg(5, e6);
                StravaUtil.this.mHandler.removeCallbacks(StravaUtil.this.startUpLoadRunnable);
                if (this.mOutputStream != null) {
                    try {
                        this.mOutputStream.close();
                    } catch (IOException e7) {
                        ThrowableExtension.printStackTrace(e7);
                    }
                }
            } catch (Throwable th) {
                if (this.mOutputStream != null) {
                    try {
                        this.mOutputStream.close();
                    } catch (IOException e8) {
                        ThrowableExtension.printStackTrace(e8);
                    }
                }
                throw th;
            }
        }
    }

    public MyStravaCallback getStravaCallback() {
        return this.mStravaCallback;
    }

    public void setStravaCallback(MyStravaCallback stravaCallback) {
        this.mStravaCallback = stravaCallback;
        this.mHandler.setStravaCallback(stravaCallback);
    }

    public StravaConfig getStravaConfig() {
        return this.mStravaConfig;
    }

    public void setStravaConfig(StravaConfig stravaConfig) {
        this.mStravaConfig = stravaConfig;
    }

    private StravaUtil(Context context) {
        this.mStravaConfig = StravaConfig.withToken(GlobalUserDataFetcher.getStravaToken(context)).debug().build();
        this.mContext = context;
    }

    public static StravaUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (StravaUtil.class) {
                if (instance == null) {
                    instance = new StravaUtil(context);
                }
            }
        }
        return instance;
    }

    public void sendMsg(int what, Object object) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = object;
        this.mHandler.sendMessage(message);
    }

    public void startNewStrava(long uid, int sport_type2, long startTime, long endTime, String dataFrom) {
        if (TextUtils.isEmpty(GlobalUserDataFetcher.getStravaToken(this.mContext))) {
            Log.i(TAG, "-------not open strava feature");
            return;
        }
        String newDataFrom = dataFrom;
        if (dataFrom != null) {
            if (dataFrom.contains("Android")) {
                newDataFrom = "Android";
                final String mFrom = newDataFrom;
                this.sport_type = sport_type2;
                this.start_time = 1000 * startTime;
                this.end_time = 1000 * endTime;
                final long j = uid;
                final long j2 = startTime;
                mExecutor.execute(new Runnable() {
                    public void run() {
                        GPX gpx = StravaUtil.this.getGpxDataNew(j, j2, mFrom);
                        if (gpx != null) {
                            Log.i(StravaUtil.TAG, "----------strava send msg GET_GPX_DATA_SUCCESS----------");
                            StravaUtil.this.sendMsg(0, gpx);
                            return;
                        }
                        Log.i(StravaUtil.TAG, "----------strava send msg GET_GPX_DATA_FAIL, file is null");
                        StravaUtil.this.sendMsg(1, null);
                    }
                });
            }
        }
        if (dataFrom != null) {
            if (dataFrom.contains("iPhone")) {
                newDataFrom = "iPhone";
            }
        }
        final String mFrom2 = newDataFrom;
        this.sport_type = sport_type2;
        this.start_time = 1000 * startTime;
        this.end_time = 1000 * endTime;
        final long j3 = uid;
        final long j22 = startTime;
        mExecutor.execute(new Runnable() {
            public void run() {
                GPX gpx = StravaUtil.this.getGpxDataNew(j3, j22, mFrom2);
                if (gpx != null) {
                    Log.i(StravaUtil.TAG, "----------strava send msg GET_GPX_DATA_SUCCESS----------");
                    StravaUtil.this.sendMsg(0, gpx);
                    return;
                }
                Log.i(StravaUtil.TAG, "----------strava send msg GET_GPX_DATA_FAIL, file is null");
                StravaUtil.this.sendMsg(1, null);
            }
        });
    }

    public void startStrava(int sport_type2, long startTime, long endTime, final List<P1_62_data> tb62Datas2) {
        if (!TextUtils.isEmpty(GlobalUserDataFetcher.getStravaToken(this.mContext))) {
            this.sport_type = sport_type2;
            this.start_time = startTime;
            this.end_time = endTime;
            this.tb62Datas = tb62Datas2;
            mExecutor.execute(new Runnable() {
                public void run() {
                    GPX gpx = StravaUtil.this.getGpxData(tb62Datas2);
                    if (gpx != null) {
                        StravaUtil.this.sendMsg(0, gpx);
                    } else {
                        StravaUtil.this.sendMsg(1, null);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void saveGPX2SD(int sport_type2, long startTime, long endTime, GPX gpx) {
        String st = ((SimpleDateFormat) dFyyyyMMddHHmmss.get()).format(new Date(startTime));
        String et = ((SimpleDateFormat) dFyyyyMMddHHmmss.get()).format(new Date(endTime));
        String dir_path = Environment.getExternalStorageDirectory() + "/Zeroner/zeroner_5_0/blelog/gpx_data";
        File dir = new File(dir_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String file_name = GlobalUserDataFetcher.getCurrentUid(this.mContext) + HelpFormatter.DEFAULT_OPT_PREFIX + sport_type2 + HelpFormatter.DEFAULT_OPT_PREFIX + st + HelpFormatter.DEFAULT_OPT_PREFIX + et + ".gpx";
        File out_file = new File(dir_path + "/" + file_name);
        Log.e("licl", dir_path + "/" + file_name);
        if (out_file.exists()) {
            out_file.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(out_file, true);
        } catch (FileNotFoundException e) {
            sendMsg(2, e);
            ThrowableExtension.printStackTrace(e);
        }
        mExecutor.execute(new SaveGPXRunnable(gpx, fileOutputStream));
    }

    /* access modifiers changed from: private */
    public GPX getGpxDataNew(long uid, long startTime, String dataFrom) {
        String newDataFrom = dataFrom;
        if (dataFrom.toLowerCase(Locale.US).contains(Constants.APPSYSTEM)) {
            newDataFrom = "Android";
        } else {
            if (dataFrom.toLowerCase(Locale.US).contains("iphone")) {
                newDataFrom = "iPhone";
            }
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH + uid + "_gps_" + startTime + "_" + newDataFrom + ".txt";
        String zipPath = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH + uid + "_gps_" + startTime + "_" + newDataFrom + ".zip";
        File file = new File(path);
        List<GpsUpData> gpsUpDataList = null;
        if (file.exists()) {
            KLog.e("no2855--> starve : ");
            gpsUpDataList = getGpsData(path);
        } else {
            File zipFile = new File(zipPath);
            if (zipFile.exists() && ZipUtil.unZip(zipFile, file)) {
                KLog.e("no2855 starve解压成功了吧--> ");
                gpsUpDataList = getGpsData(path);
            }
        }
        if (gpsUpDataList == null || gpsUpDataList.size() == 0) {
            return null;
        }
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        HashSet<Track> tracks = new HashSet<>();
        Track track = new Track();
        GPX gpx = new GPX();
        for (GpsUpData gpsUpData : gpsUpDataList) {
            if (gpsUpData.getY() != Utils.DOUBLE_EPSILON || gpsUpData.getX() != Utils.DOUBLE_EPSILON) {
                Waypoint waypoint = new Waypoint();
                waypoint.setLatitude(Double.valueOf(gpsUpData.getY()));
                waypoint.setLongitude(Double.valueOf(gpsUpData.getX()));
                waypoint.setElevation(Double.valueOf((double) gpsUpData.getV()));
                waypoint.setTime(new Date(gpsUpData.getT() * 1000));
                waypoints.add(waypoint);
            }
        }
        track.setTrackPoints(waypoints);
        tracks.add(track);
        gpx.setTracks(tracks);
        return gpx;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0097 A[SYNTHETIC, Splitter:B:24:0x0097] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009c A[Catch:{ IOException -> 0x00a0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a8 A[SYNTHETIC, Splitter:B:32:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ad A[Catch:{ IOException -> 0x00b1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.iwown.data_link.sport_data.gps.GpsUpData> getGpsData(java.lang.String r13) {
        /*
            r12 = this;
            r8 = 0
            java.io.File r5 = new java.io.File
            r5.<init>(r13)
            java.lang.String r10 = "no2855--> location开始将手机地理文件内容写入数据库。。。"
            com.socks.library.KLog.e(r10)
            r6 = 0
            r2 = 0
            java.io.FileReader r7 = new java.io.FileReader     // Catch:{ Exception -> 0x0079 }
            r7.<init>(r5)     // Catch:{ Exception -> 0x0079 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00bd, all -> 0x00b6 }
            r3.<init>(r7)     // Catch:{ Exception -> 0x00bd, all -> 0x00b6 }
            r9 = 0
            java.lang.String r1 = ""
        L_0x001c:
            java.lang.String r9 = r3.readLine()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            if (r9 == 0) goto L_0x004f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            r10.<init>()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.String r11 = "读取gps文件-->"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.StringBuilder r10 = r10.append(r9)     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            com.socks.library.KLog.e(r10)     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            r10.<init>()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.String r11 = r9.trim()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.String r1 = r10.toString()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            goto L_0x001c
        L_0x004f:
            com.google.gson.Gson r10 = new com.google.gson.Gson     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            r10.<init>()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            com.sweetzpot.stravazpot.StravaUtil$4 r11 = new com.sweetzpot.stravazpot.StravaUtil$4     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            r11.<init>()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.reflect.Type r11 = r11.getType()     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            java.lang.Object r10 = r10.fromJson(r1, r11)     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            r0 = r10
            java.util.List r0 = (java.util.List) r0     // Catch:{ Exception -> 0x00c0, all -> 0x00b9 }
            r8 = r0
            if (r7 == 0) goto L_0x006a
            r7.close()     // Catch:{ IOException -> 0x0072 }
        L_0x006a:
            if (r3 == 0) goto L_0x006f
            r3.close()     // Catch:{ IOException -> 0x0072 }
        L_0x006f:
            r2 = r3
            r6 = r7
        L_0x0071:
            return r8
        L_0x0072:
            r4 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r4)
            r2 = r3
            r6 = r7
            goto L_0x0071
        L_0x0079:
            r4 = move-exception
        L_0x007a:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a5 }
            r10.<init>()     // Catch:{ all -> 0x00a5 }
            java.lang.String r11 = "gps文件-->写入strava 异常"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ all -> 0x00a5 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00a5 }
            r11 = 3
            com.iwown.lib_common.log.L.file(r10, r11)     // Catch:{ all -> 0x00a5 }
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r4)     // Catch:{ all -> 0x00a5 }
            if (r6 == 0) goto L_0x009a
            r6.close()     // Catch:{ IOException -> 0x00a0 }
        L_0x009a:
            if (r2 == 0) goto L_0x0071
            r2.close()     // Catch:{ IOException -> 0x00a0 }
            goto L_0x0071
        L_0x00a0:
            r4 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r4)
            goto L_0x0071
        L_0x00a5:
            r10 = move-exception
        L_0x00a6:
            if (r6 == 0) goto L_0x00ab
            r6.close()     // Catch:{ IOException -> 0x00b1 }
        L_0x00ab:
            if (r2 == 0) goto L_0x00b0
            r2.close()     // Catch:{ IOException -> 0x00b1 }
        L_0x00b0:
            throw r10
        L_0x00b1:
            r4 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r4)
            goto L_0x00b0
        L_0x00b6:
            r10 = move-exception
            r6 = r7
            goto L_0x00a6
        L_0x00b9:
            r10 = move-exception
            r2 = r3
            r6 = r7
            goto L_0x00a6
        L_0x00bd:
            r4 = move-exception
            r6 = r7
            goto L_0x007a
        L_0x00c0:
            r4 = move-exception
            r2 = r3
            r6 = r7
            goto L_0x007a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sweetzpot.stravazpot.StravaUtil.getGpsData(java.lang.String):java.util.List");
    }

    /* access modifiers changed from: private */
    public GPX getGpxData(List<P1_62_data> tb62Datas2) {
        long handled_time;
        if (tb62Datas2 == null || tb62Datas2.size() == 0) {
            return null;
        }
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        HashSet<Track> tracks = new HashSet<>();
        Track track = new Track();
        GPX gpx = new GPX();
        for (int i = 0; i < tb62Datas2.size(); i++) {
            P1_62_data data = (P1_62_data) tb62Datas2.get(i);
            List<LongitudeAndLatitude> latitudes = JsonUtility.jsonToList(data.getGnssData(), LongitudeAndLatitude[].class);
            int num = data.getNum();
            int interval = 60 / num;
            boolean isInChina = false;
            for (int i1 = 0; i1 < num; i1++) {
                LongitudeAndLatitude longitudeAndLatitude = (LongitudeAndLatitude) latitudes.get(i1);
                if (i1 == num - 1) {
                    handled_time = data.getTime() + ((long) (((i1 * interval) - 1) * 1000));
                } else {
                    handled_time = data.getTime() + ((long) (i1 * interval * 1000));
                }
                if (!isInChina) {
                    isInChina = PositionUtility.isInChina();
                    if (isInChina) {
                        L.file("2855--> location is  paster location", 3);
                    }
                }
                Gps gps = new Gps(longitudeAndLatitude.getLatitude(), longitudeAndLatitude.getLongitude());
                double lat = gps.getWgLat();
                double lon = gps.getWgLon();
                double alt = (double) longitudeAndLatitude.getAltitude();
                Waypoint waypoint = new Waypoint();
                waypoint.setLatitude(Double.valueOf(lat));
                waypoint.setLongitude(Double.valueOf(lon));
                waypoint.setElevation(Double.valueOf(alt));
                Date date = new Date(handled_time);
                waypoint.setTime(date);
                waypoints.add(waypoint);
            }
        }
        track.setTrackPoints(waypoints);
        tracks.add(track);
        gpx.setTracks(tracks);
        return gpx;
    }

    public void upLoadGPXFile(int sport_type2, long startTime, long endTime) {
        final long j = startTime;
        final long j2 = endTime;
        final int i = sport_type2;
        mExecutor.execute(new Runnable() {
            public void run() {
                String st = ((SimpleDateFormat) StravaUtil.dFyyyyMMddHHmmss.get()).format(new Date(j));
                String et = ((SimpleDateFormat) StravaUtil.dFyyyyMMddHHmmss.get()).format(new Date(j2));
                String dir_path = Environment.getExternalStorageDirectory() + "/Zeroner/zeroner_5_0/blelog/gpx_data";
                String file_name = GlobalUserDataFetcher.getCurrentUid(StravaUtil.this.mContext) + HelpFormatter.DEFAULT_OPT_PREFIX + i + HelpFormatter.DEFAULT_OPT_PREFIX + st + HelpFormatter.DEFAULT_OPT_PREFIX + et + ".gpx";
                File dir = new File(dir_path, file_name);
                if (dir.exists()) {
                    Log.e("strava", dir.getAbsolutePath());
                }
                UploadFileRequest uploadFileRequest = new UploadAPI(StravaUtil.this.mStravaConfig).uploadFile(dir);
                if (i != 5 && i != 149) {
                    switch (i) {
                        case 5:
                            uploadFileRequest.withActivityType(UploadActivityType.UNKNOWN);
                            break;
                        case 7:
                            uploadFileRequest.withActivityType(UploadActivityType.RUN);
                            break;
                        case Opcodes.FLOAT_TO_LONG /*136*/:
                            uploadFileRequest.withActivityType(UploadActivityType.RIDE);
                            break;
                        case Opcodes.FLOAT_TO_DOUBLE /*137*/:
                            uploadFileRequest.withActivityType(UploadActivityType.BACKCOUNTRY_SKI);
                            break;
                        case Opcodes.DIV_INT /*147*/:
                            uploadFileRequest.withActivityType(UploadActivityType.WALK);
                            break;
                        case Opcodes.AND_INT /*149*/:
                            uploadFileRequest.withActivityType(UploadActivityType.UNKNOWN);
                            break;
                    }
                    try {
                        UploadStatus uploadStatus = uploadFileRequest.withDataType(DataType.GPX).withName("").withDescription(file_name).isPrivate(false).hasTrainer(false).isCommute(false).withExternalID(file_name).execute();
                        if (uploadStatus != null) {
                            StravaUtil.this.sendMsg(9, uploadStatus);
                        } else {
                            StravaUtil.this.sendMsg(10, null);
                        }
                    } catch (StravaUnauthorizedException e) {
                        StravaUtil.this.sendMsg(6, e);
                        ThrowableExtension.printStackTrace(e);
                    } catch (StravaAPIException e2) {
                        StravaUtil.this.sendMsg(7, e2);
                        ThrowableExtension.printStackTrace(e2);
                    } catch (Exception e3) {
                        StravaUtil.this.sendMsg(8, e3);
                        ThrowableExtension.printStackTrace(e3);
                    }
                }
            }
        });
    }

    public void getToken() {
        mExecutor.execute(new Runnable() {
            public void run() {
                LoginResult result = null;
                try {
                    result = new AuthenticationAPI(AuthenticationConfig.create().debug().build()).getTokenForApp(AppCredentials.with(StravaCredential.getClientId(), StravaCredential.getClientSecret())).withCode(GlobalUserDataFetcher.getStravaCode(StravaUtil.this.mContext)).execute();
                    if (result != null) {
                        StravaUtil.this.sendMsg(14, result);
                        Log.e("strava", result.getToken().toString());
                    } else {
                        StravaUtil.this.sendMsg(15, null);
                    }
                } catch (StravaUnauthorizedException e) {
                    StravaUtil.this.sendMsg(6, e);
                    ThrowableExtension.printStackTrace(e);
                } catch (StravaAPIException e2) {
                    StravaUtil.this.sendMsg(13, e2);
                    ThrowableExtension.printStackTrace(e2);
                } catch (Exception e3) {
                    StravaUtil.this.sendMsg(11, e3);
                    ThrowableExtension.printStackTrace(e3);
                }
                GlobalDataUpdater.setStravaToken(StravaUtil.this.mContext, result.getToken().toString());
                StravaUtil.this.mStravaConfig = StravaConfig.withToken(result.getToken()).debug().build();
                Log.i("strava token", GlobalUserDataFetcher.getStravaToken(StravaUtil.this.mContext));
                StravaTokenGetEvent tokenEvent = new StravaTokenGetEvent();
                tokenEvent.setStatus(1);
                EventBus.getDefault().post(tokenEvent);
            }
        });
    }
}
