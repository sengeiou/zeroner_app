package com.iwown.sport_module.gps.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.activity.MapActivity;
import com.iwown.sport_module.gps.data.Gps;
import com.iwown.sport_module.gps.data.GpsMsgData;
import com.iwown.sport_module.gps.data.GpsTimeEvent;
import com.iwown.sport_module.gps.data.GpsUpData;
import com.iwown.sport_module.gps.data.TB_location;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class BaseLocationManger {
    Builder builder;
    protected Gps[] checkGps = new Gps[3];
    protected int checkNum = 0;
    protected long[] checkTime = new long[3];
    private String device = "";
    private long endTime = 0;
    protected boolean firstCheck;
    private String gpsChannelId = "gps_notification";
    protected GpsMsgData gpsMsgData;
    protected boolean isOk;
    private boolean isR1 = false;
    private boolean isR1First = true;
    protected boolean isRunning = false;
    protected boolean isViewStop = false;
    protected int lastDistance = 0;
    protected Context mContext;
    protected int mPauseTime;
    protected int mStep = 0;
    protected Thread mThread;
    NotificationManager manager;
    private final double maxBike = 16.7d;
    private final double maxRun = 8.5d;
    protected double maxSpeed;
    protected double min2Avg = Utils.DOUBLE_EPSILON;
    private String msgUt = "km";
    protected Notification notify2;
    protected Gps nowGps = new Gps();
    protected int nums;
    protected int pauseStep = 0;
    protected int pauseType = 0;
    private SensorManager sensorManager;
    protected float speed = 0.0f;
    protected int sportTime = -1;
    protected int sportType;
    protected int stateNums;
    TodayStepCounter stepCounter;
    protected List<Long> stopTime = new ArrayList();
    protected float target = 0.0f;
    protected int targetType = 0;
    protected long timeId;
    protected float totalCal = 0.0f;
    protected float totalDis = 0.0f;
    protected long uid;
    private float weight = 60.0f;

    public void setContext(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public Context getContext() {
        return this.mContext;
    }

    public void initLocation(Context context) {
        this.mContext = context.getApplicationContext();
        this.msgUt = "km";
        this.uid = UserConfig.getInstance().getNewUID();
        this.sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        addStepCounterListener();
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this.mContext, 0, new Intent(this.mContext, MapActivity.class), 0);
        this.builder = getGpsNotificationBuilder();
        this.builder.setSmallIcon(R.mipmap.ic_launcher).setTicker(this.mContext.getString(R.string.sport_module_gps_news)).setContentTitle(this.mContext.getString(R.string.sport_module_gps_distance_pop) + Util.doubleToFloat(1, (double) (this.totalDis / 1000.0f)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.msgUt).setContentText(this.mContext.getString(R.string.our_app_name) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.mContext.getString(R.string.sport_module_gps_pop_msg)).setContentIntent(pendingIntent2);
        this.notify2 = this.builder.build();
        this.notify2.flags = 16;
        this.manager = (NotificationManager) this.mContext.getSystemService("notification");
        createNotificationChannel();
        this.gpsMsgData = new GpsMsgData();
    }

    public Notification getNotify2() {
        return this.notify2;
    }

    /* access modifiers changed from: protected */
    public void reshNotification() {
        if (this.builder != null && this.mContext != null) {
            this.builder.setContentTitle(this.mContext.getString(R.string.sport_module_gps_distance_pop) + Util.doubleToFloat(1, ((double) this.totalDis) / 1000.0d) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.msgUt);
            this.notify2 = this.builder.build();
            this.manager.notify(237, this.notify2);
        }
    }

    public void startLocation() {
        clear();
    }

    public void start() {
        clear();
        this.timeId = System.currentTimeMillis() / 1000;
        this.mPauseTime = 0;
        this.sportTime = -1;
        this.isRunning = true;
        this.min2Avg = Utils.DOUBLE_EPSILON;
        this.isOk = false;
        this.firstCheck = true;
        if (this.sportType == 1) {
            this.maxSpeed = 16.7d;
        } else {
            this.maxSpeed = 8.5d;
        }
        startThread();
    }

    private void startThread() {
        if (this.mThread == null || !this.mThread.isAlive()) {
            this.mThread = new Thread(new Runnable() {
                public void run() {
                    while (BaseLocationManger.this.isRunning) {
                        try {
                            BaseLocationManger.this.sportTime++;
                            EventBus.getDefault().post(new GpsTimeEvent(BaseLocationManger.this.sportTime));
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                            return;
                        }
                    }
                }
            });
            this.mThread.start();
        }
    }

    public void pauseLocation() {
        this.isRunning = false;
        this.stopTime.add(Long.valueOf(System.currentTimeMillis() / 1000));
        if (this.stepCounter != null) {
            this.mStep = this.stepCounter.getmStep() - this.pauseStep;
        }
    }

    public void restartLocation() {
        this.isRunning = true;
        this.stopTime.add(Long.valueOf(System.currentTimeMillis() / 1000));
        if (this.stopTime.size() >= 2) {
            this.mPauseTime = ((int) (((Long) this.stopTime.get(1)).longValue() - ((Long) this.stopTime.get(0)).longValue())) + this.mPauseTime;
        }
        this.sportTime = ((int) ((System.currentTimeMillis() / 1000) - this.timeId)) - this.mPauseTime;
        this.stopTime.clear();
        if (this.stepCounter != null) {
            this.pauseStep = this.stepCounter.getmStep() - this.mStep;
        }
        startThread();
    }

    public void stopLocation() {
        this.isRunning = false;
        if (isCanSave()) {
            saveHisTB();
            saveTBGpsSport();
            List<TB_location> tb_locations = DataSupport.where("uid=? and time_id=?", this.uid + "", this.timeId + "").order("time asc").find(TB_location.class);
            List<GpsUpData> gpsData = new ArrayList<>();
            for (TB_location location : tb_locations) {
                gpsData.add(new GpsUpData(location.getTime(), location.getLat(), location.getLon(), location.getPause_type(), location.getStep()));
            }
            String str = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH;
            Util.writeGpsSD(UserConfig.getInstance().getNewUID() + "_gps_" + this.timeId + "_Android.txt", new Gson().toJson((Object) gpsData));
            L.file("writeGpsSD/" + new File(Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH + UserConfig.getInstance().getNewUID() + "_gps_" + this.timeId + "_Android.txt").getAbsolutePath(), 3);
            HealthDataEventBus.updateTraningData();
            KLog.e(this.timeId + "/" + UserConfig.getInstance().getNewUID(), new Gson().toJson((Object) gpsData));
            ModuleRouteDeviceInfoService.getInstance().setGpsSportTime(0);
            if (this.isR1) {
                ModuleRouteDeviceInfoService.getInstance().syncDataInfo(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void saveHisTB() {
        ModuleRouteDeviceInfoService.getInstance().setGpsSportTime(System.currentTimeMillis());
        TB_location_history history = new TB_location_history();
        history.setUid(this.uid);
        history.setTime_id(this.timeId);
        this.endTime = System.currentTimeMillis() / 1000;
        history.setEnd_time(this.endTime);
        history.setTime((int) ((this.endTime - this.timeId) - ((long) this.mPauseTime)));
        history.setCalorie(this.totalCal);
        history.setDistance(this.totalDis);
        if (this.isR1First && this.sportTime < 150) {
            this.device = ModuleRouteDeviceInfoService.getInstance().getDeviceClient();
            if (this.device != null && this.device.toUpperCase(Locale.US).contains("VOICE")) {
                this.isR1First = false;
                this.isR1 = true;
            }
        }
        if (this.isR1) {
            history.setData_type(3);
        } else {
            history.setData_type(0);
        }
        history.setYear_month_day(getTime(this.timeId));
        history.setSport_type(this.sportType);
        history.saveOrUpdate("uid=? and time_id=?", this.uid + "", this.timeId + "");
    }

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1000 * time);
        int mCurrentYear = calendar.get(1);
        int mCurrentMonth = calendar.get(2) + 1;
        int mCurrentDay = calendar.get(5);
        StringBuilder currentSb = new StringBuilder();
        currentSb.append(mCurrentYear).append(mCurrentMonth).append(mCurrentDay);
        return currentSb.toString();
    }

    public int getStateNums() {
        KLog.e(getTimeId() + "", "界面获取到的卫星数： " + this.stateNums);
        return this.stateNums;
    }

    public Gps getNowGps() {
        return this.nowGps;
    }

    /* access modifiers changed from: protected */
    public float getLatDistance() {
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public GpsMsgData changeMain() {
        if (this.stepCounter != null) {
            this.mStep = this.stepCounter.getmStep() - this.pauseStep;
        }
        if (this.sportTime < 0) {
            this.sportTime = 0;
            this.mPauseTime = 0;
            this.stopTime.clear();
        }
        float distance = getLatDistance();
        this.totalDis += distance;
        if (this.totalDis > 0.0f) {
            this.speed = ((float) this.sportTime) / (this.totalDis / 1000.0f);
        } else {
            this.speed = 0.0f;
        }
        int times = (int) this.speed;
        String timeStr = (times / 60) + "'" + (times % 60) + "''";
        this.totalCal = (float) (((double) ((this.weight * this.totalDis) / 1000.0f)) * 1.036d);
        KLog.d("no285511距离： " + distance + "总距离: " + this.totalDis + " 速度: " + timeStr + " 卡路里: " + this.totalCal);
        this.gpsMsgData.setType(0);
        if (distance > 0.0f) {
            this.gpsMsgData.setPace(timeStr);
        } else {
            this.gpsMsgData.setPace(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        }
        this.gpsMsgData.setDistance(this.totalDis / 1000.0f);
        this.gpsMsgData.setCalorie(this.totalCal);
        this.gpsMsgData.setTime(this.sportTime);
        return this.gpsMsgData;
    }

    public void setSportType(int sportType2) {
        this.sportType = sportType2;
    }

    public int getSportType() {
        return this.sportType;
    }

    public void clear() {
        Log.d("testamap", "开始清除数据");
        this.totalDis = 0.0f;
        this.totalCal = 0.0f;
        this.speed = 0.0f;
        this.timeId = 0;
        this.endTime = 0;
        this.mPauseTime = 0;
        this.pauseType = 0;
        this.mStep = 0;
        this.pauseStep = 0;
        this.lastDistance = 0;
        this.isR1First = true;
        this.isR1 = false;
        this.device = "";
        if (this.stopTime != null) {
            this.stopTime.clear();
        }
        if (this.gpsMsgData != null) {
            this.gpsMsgData.setCalorie(0.0f);
            this.gpsMsgData.setPace(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
            this.gpsMsgData.setTime(0);
            this.gpsMsgData.setDistance(0.0f);
        }
        this.checkNum = 0;
        for (int i = 0; i < 3; i++) {
            this.checkTime[i] = 0;
            if (this.checkGps[i] == null) {
                this.checkGps[i] = new Gps(Utils.DOUBLE_EPSILON, Utils.DOUBLE_EPSILON);
            } else {
                this.checkGps[i].setWgLat(Utils.DOUBLE_EPSILON);
                this.checkGps[i].setWgLon(Utils.DOUBLE_EPSILON);
            }
        }
        if (this.stepCounter != null) {
            this.stepCounter.clear();
        }
    }

    public void setViewStop() {
        this.isViewStop = true;
    }

    public GpsMsgData getGpsMsgData() {
        return this.gpsMsgData;
    }

    public long getTimeId() {
        return this.timeId;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public int getPauseType() {
        return this.pauseType;
    }

    public boolean isCanSave() {
        L.file("gps--" + this.totalDis + "/" + this.sportTime, 3);
        if (this.timeId > 0 && this.totalDis >= 500.0f) {
            return true;
        }
        DataSupport.deleteAll(TB_location_history.class, "uid=? and time_id=?", this.uid + "", this.timeId + "");
        DataSupport.deleteAll(TB_location.class, "uid=? and time_id=?", this.uid + "", this.timeId + "");
        return false;
    }

    public int getTargetOk() {
        Log.d("testMain", "targetType is " + this.targetType + " target: " + this.target + " totalDis: " + this.totalDis + " sportTime:" + this.sportTime + " totalCal:" + this.totalCal);
        if (this.targetType == 0) {
            return 0;
        }
        if (this.targetType == 1) {
            if (((double) this.target) <= ((double) this.totalDis) / 1000.0d) {
                return 1;
            }
            return 2;
        } else if (this.targetType == 2) {
            if (this.target <= ((float) this.sportTime)) {
                return 1;
            }
            return 2;
        } else if (this.targetType != 3) {
            return 0;
        } else {
            if (this.target <= this.totalCal) {
                return 1;
            }
            return 2;
        }
    }

    public void setTarget(int targetType2, float target2) {
        KLog.e("no2855 目标获取: " + targetType2 + " - " + target2);
        this.targetType = targetType2;
        if (targetType2 != 1 || !UserConfig.getInstance().isMertric()) {
            this.target = target2;
        } else {
            this.target = (float) Util.mileToKm(target2);
        }
    }

    public void playEnd() {
    }

    private void saveTBGpsSport() {
        KLog.e("no2855--> 准备入表-->");
        CopySportGps copySportGps = new CopySportGps();
        copySportGps.setUid(this.uid);
        copySportGps.setStart_time(this.timeId);
        copySportGps.setEnd_time(this.endTime);
        if (this.isR1) {
            copySportGps.setR1_url("1");
            copySportGps.setHeart_url("1");
            copySportGps.setData_from("Android&" + this.device);
        } else {
            copySportGps.setData_from("Android");
        }
        copySportGps.setGps_url("1");
        copySportGps.setSource_type(2);
        if (this.sportType > 2 || this.sportType < 0) {
            this.sportType = 0;
        }
        copySportGps.setSport_type(this.sportType);
        copySportGps.setStep(this.mStep);
        copySportGps.setDistance(this.totalDis);
        copySportGps.setCalorie(this.totalCal);
        copySportGps.setDuration((int) ((this.endTime - this.timeId) - ((long) this.mPauseTime)));
        ModuleRouteSportService.getInstance().saveGps2SportTB(copySportGps);
    }

    private void addStepCounterListener() {
        try {
            this.sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
            Sensor countSensor = this.sensorManager.getDefaultSensor(19);
            if (countSensor != null) {
                this.stepCounter = new TodayStepCounter(this.mContext.getApplicationContext());
                this.sensorManager.registerListener(this.stepCounter, countSensor, 0);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: protected */
    public void setMin2Avg() {
        if (this.min2Avg == Utils.DOUBLE_EPSILON && this.sportTime >= 120) {
            this.min2Avg = (double) (this.totalDis / ((float) this.sportTime));
            if (this.min2Avg <= 13.0d) {
                this.isOk = true;
            }
        }
    }

    public void createNotificationChannel() {
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(this.gpsChannelId, "GpsNotification", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            if (this.manager != null) {
                this.manager.createNotificationChannel(channel);
            }
        }
    }

    @NonNull
    private Builder getGpsNotificationBuilder() {
        if (VERSION.SDK_INT >= 26) {
            return new Builder(this.mContext, this.gpsChannelId);
        }
        return new Builder(this.mContext);
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }
}
