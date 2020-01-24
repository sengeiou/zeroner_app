package com.iwown.sport_module.model;

import android.location.Location;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.P1_61_data;
import com.iwown.data_link.sport_data.V3_sport_data;
import com.iwown.data_link.sport_data.gps.BleGpsData;
import com.iwown.data_link.sport_data.gps.HrUpData;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.contract.RunActivityContract.MapHealthyDataModel;
import com.iwown.sport_module.gps.data.TB_location;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.pojo.R1DataBean;
import com.iwown.sport_module.pojo.R1UpdateBean;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.run.DlineDataBean;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.litepal.crud.DataSupport;

public class RunActivityHealthyModeImpl implements MapHealthyDataModel {
    public static final int PHONE = 2;
    private static float PHONE_STEP = 0.0f;
    private static float PHONE_STRIDE = 0.8f;
    public static final int WATCH = 1;
    public static final int WRIST = 0;
    /* access modifiers changed from: private */
    public String TAG;
    /* access modifiers changed from: private */
    public long UID;
    /* access modifiers changed from: private */
    public int age;
    /* access modifiers changed from: private */
    public String dataFrom;
    /* access modifiers changed from: private */
    public String devModel;
    /* access modifiers changed from: private */
    public long endTime;
    /* access modifiers changed from: private */
    public String fileName;
    /* access modifiers changed from: private */
    public boolean isMertric;
    /* access modifiers changed from: private */
    public LoadCallback mLoadCallback;
    private TB_location_history mLocationHistory;
    private List<P1_61_data> mP1_61_data_not_all;
    private int mSportTime;
    private List<P1_61_data> mTb_61_datas;
    /* access modifiers changed from: private */
    public String saveDirPath;
    /* access modifiers changed from: private */
    public int sportType;
    /* access modifiers changed from: private */
    public long startTime;
    private float userHeight;

    public interface LoadCallback {
        void on61Success();

        void onPhone_Diagrams_Fail(Throwable th, long j, long j2, String str);

        void onPhone_Diagrams_Success(DiagramsData diagramsData);

        void onPhone_Health_Fail(Throwable th, long j, long j2, String str);

        void onPhone_Healty_Success(MapHealthyData mapHealthyData);

        void onPhone_PaceBeanList_Fail(Throwable th, long j, long j2, String str);

        void onPhone_PaceBeanList_Success(List<DataFragmentBean> list);

        void onR1DataSuccess(R1DataBean r1DataBean);

        void onR1Fail();

        void onSportHeartFail();

        void onSportHeartSuccess(HeartData heartData);

        void onWatch_Diagrams_Fail(Throwable th, long j, long j2, String str);

        void onWatch_Diagrams_Success(DiagramsData diagramsData);

        void onWatch_Health_Fail(Throwable th, long j, long j2, String str);

        void onWatch_Healty_Success(MapHealthyData mapHealthyData);

        void onWatch_Heart_Fail(Throwable th, long j, long j2, String str);

        void onWatch_Heart_Success(HeartData heartData);

        void onWatch_PaceBeanList_Fail(Throwable th, long j, long j2, String str);

        void onWatch_PaceBeanList_Success(List<DataFragmentBean> list);

        void onWrist_Heart_Fail(Throwable th, long j, long j2, String str);

        void onWrist_Heart_Success(HeartData heartData);
    }

    static class PaceBean {
        List<Float> list_m_count;
        float time_s_last;
        float time_s_one;

        PaceBean() {
        }

        public String toString() {
            return "PaceBean{time_s_one=" + this.time_s_one + ", time_s_last=" + this.time_s_last + '}';
        }
    }

    private class Seg_61 {
        public int end_index;
        public long end_tiem;
        public int start_index;
        public long start_time;

        private Seg_61() {
        }
    }

    public RunActivityHealthyModeImpl() {
        this.devModel = "";
        this.sportType = -1;
        this.age = -1;
        this.isMertric = true;
        this.userHeight = 0.0f;
        this.TAG = getClass().getSimpleName();
        this.mLoadCallback = null;
        this.mSportTime = 0;
        this.UID = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.dataFrom = "";
        this.mSportTime = 0;
    }

    public void setLoadCallback(LoadCallback loadCallback) {
        this.mLoadCallback = loadCallback;
    }

    public void getHealthyDataAboutWatch(long uid, long start_time, long end_time, String data_from, String file_name, String save_dir_path, int sport_type, boolean is_Mertric, int age2, String dev_model, boolean check) {
        this.UID = uid;
        this.startTime = start_time;
        this.endTime = end_time;
        this.dataFrom = data_from;
        this.fileName = file_name;
        this.saveDirPath = save_dir_path;
        this.sportType = sport_type;
        this.isMertric = is_Mertric;
        this.age = age2;
        this.devModel = dev_model;
        new MapHealthyData();
        List<P1_61_data> p1_61_dataList = get61FromSql();
        if (check) {
            if (p1_61_dataList.size() != 0) {
                KLog.e("no2855-->本地查到手表61数据");
                if (this.mLoadCallback != null) {
                    this.mLoadCallback.onWatch_Healty_Success(getHealthyData());
                    return;
                }
                return;
            }
            KLog.e(this.TAG, "本地没有查到手表61数据-->去文件夹查找文件");
            get61FromSD();
        } else if (p1_61_dataList.size() != 0) {
            KLog.e("no2855-->本地查到手表61数据");
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onWatch_Healty_Success(getHealthyData());
            }
        } else if (this.mLoadCallback != null) {
            this.mLoadCallback.onWatch_Health_Fail(null, this.startTime, uid, this.dataFrom);
        }
    }

    private MapHealthyData getHealthyData() {
        int avg_bpm;
        List<V3_sport_data> v3_sport_dataList = ModuleRouteSportService.getInstance().get28SportAsTimePeriod(this.UID, this.dataFrom, this.startTime, this.endTime, this.sportType);
        KLog.e("no2855-->licl: " + this.UID + "/" + this.dataFrom + "/" + this.startTime + "/" + this.endTime + "/" + this.sportType);
        MapHealthyData healthyData = new MapHealthyData();
        float distance = 0.0f;
        int activity = 0;
        int steps = 0;
        float cal = 0.0f;
        if (this.mSportTime == 0) {
            this.mSportTime = getSportTime() / 1000;
        }
        int sportTime = this.mSportTime;
        KLog.e("no2855--> 运动时长: " + sportTime);
        if (!(v3_sport_dataList == null || v3_sport_dataList.size() == 0)) {
            healthyData = new MapHealthyData();
            healthyData.setMertric(this.isMertric);
            for (V3_sport_data v3_sport_data : v3_sport_dataList) {
                cal = (float) (((double) cal) + v3_sport_data.getCalorie());
                Detail_data detail_data = (Detail_data) JsonTool.fromJson(v3_sport_data.getDetail_data(), Detail_data.class);
                distance += detail_data.getDistance();
                activity += detail_data.getActivity();
                steps += detail_data.getStep();
            }
            healthyData.setCal((int) cal);
            healthyData.setDistance(Util.doubleToFloat(2, (double) (distance / 1000.0f)));
            healthyData.setTotal_step(steps);
            healthyData.setActive_time(sportTime);
            healthyData.setPace(getSecPace(sportTime, distance));
            healthyData.setStride(getStride(healthyData.getTotal_step(), healthyData.getDistance()));
        }
        this.mTb_61_datas = ModuleRouteSportService.getInstance().getsport61Data(this.UID, this.dataFrom, this.startTime, this.endTime, this.sportType, true);
        int avg_bpm2 = 0;
        int totalNum = 0;
        if (this.mTb_61_datas != null && this.mTb_61_datas.size() > 0) {
            if (healthyData == null) {
                healthyData = new MapHealthyData();
            }
            healthyData.setActive_time(sportTime);
            for (P1_61_data data : this.mTb_61_datas) {
                if (data.getAvg_bpm() > 0) {
                    avg_bpm2 += data.getAvg_bpm();
                    totalNum++;
                }
            }
            KLog.e(this.TAG, "avg/bpm: " + avg_bpm2 + "/" + totalNum);
            if (totalNum > 0) {
                avg_bpm = avg_bpm2 / totalNum;
            } else {
                avg_bpm = 0;
            }
            healthyData.setHr(avg_bpm);
        }
        if (this.isMertric) {
            healthyData.setSpeed(getSpeed(distance, healthyData.getActive_time()));
        } else {
            healthyData.setSpeed(getSpeed(((float) Util.meterToMile((double) (distance / 1000.0f))) * 1000.0f, healthyData.getActive_time()));
        }
        healthyData.setRate(getRate(healthyData.getTotal_step(), healthyData.getActive_time()));
        return healthyData;
    }

    private int getRate(int mStep, int active) {
        if (this.sportType == 136) {
            return getCadence(mStep);
        }
        if (active == 0) {
            return 0;
        }
        KLog.e("licl", "getRate" + mStep + "/" + active);
        return (int) (((double) mStep) / ((((double) active) * 1.0d) / 60.0d));
    }

    private int getCadence(int mStep) {
        if (this.endTime <= this.startTime) {
            return 0;
        }
        if (this.mSportTime == 0) {
            this.mSportTime = getSportTime() / 1000;
        }
        KLog.e("no2855--> mSportTime: " + this.mSportTime);
        return (int) (((float) mStep) / (((float) this.mSportTime) / 60.0f));
    }

    private int getStride(int mStep, float distance) {
        if (mStep == 0) {
            return 0;
        }
        if (this.isMertric) {
            return (int) (((1000.0f * distance) * 100.0f) / ((float) mStep));
        }
        return (int) ((63360.0f * distance) / ((float) mStep));
    }

    private float getPace(float distances) {
        if (distances == 0.0f) {
            return 0.0f;
        }
        if (this.mSportTime == 0) {
            this.mSportTime = getSportTime() / 1000;
        }
        return Util.doubleToFloat(2, (double) ((((float) this.mSportTime) / distances) / 60.0f));
    }

    private int getSecPace(int sportTime, float distances) {
        if (distances <= 0.0f) {
            return 0;
        }
        if (this.isMertric) {
            return (int) ((((float) sportTime) / distances) * 1000.0f);
        }
        return (int) (((double) sportTime) / Util.meterToMile(((double) distances) / 1000.0d));
    }

    private float getSpeed(float distances, int active_time) {
        if (active_time >= 0) {
            return Util.doubleToFloat(2, (double) (((float) (((int) (1000.0d * ((double) (((distances / (((float) active_time) * 1.0f)) * 3600.0f) / 1000.0f)))) / 10)) / 100.0f));
        }
        return 0.0f;
    }

    public int getSportTime() {
        List<P1_61_data> list = ModuleRouteSportService.getInstance().get61DataAsSportTypeAndSortBySeq(this.UID, this.dataFrom, this.startTime, this.endTime, this.sportType);
        if (list == null || list.size() == 0) {
            return (int) (this.endTime - this.startTime);
        }
        if (list.size() == 1) {
            return (int) ((this.endTime - this.startTime) - ((long) (getPauseTime() * 1000)));
        }
        ArrayList<Seg_61> arrayList = new ArrayList<>();
        int startindex = 0;
        long start_time = 0;
        int isOk = 0;
        for (int i = 0; i < list.size(); i++) {
            P1_61_data p1_61_data = (P1_61_data) list.get(i);
            if (p1_61_data.getState_type() == 1) {
                if (isOk == 1) {
                    Seg_61 seg_61 = new Seg_61();
                    seg_61.start_index = startindex;
                    seg_61.start_time = start_time;
                    seg_61.end_index = i;
                    seg_61.end_tiem = p1_61_data.getTime();
                    arrayList.add(seg_61);
                }
                isOk = 1;
                KLog.e(this.TAG, "找到一个运动段起点：" + JsonTool.toJson(p1_61_data));
                startindex = i;
                start_time = p1_61_data.getTime() - ((long) ((p1_61_data.getAutomatic() * 60) * 1000));
            } else if (isOk == 1 && p1_61_data.getState_type() == 2) {
                isOk = 2;
                KLog.e(this.TAG, "找到一个运动段终点：" + JsonTool.toJson(p1_61_data));
                Seg_61 seg_612 = new Seg_61();
                seg_612.start_index = startindex;
                seg_612.start_time = start_time;
                seg_612.end_index = i;
                seg_612.end_tiem = p1_61_data.getTime();
                arrayList.add(seg_612);
            }
        }
        if (isOk == 1 && list != null && list.size() > 0) {
            KLog.e("no2855时长: li 特殊结束：" + JsonTool.toJson(list.get(list.size() - 1)));
            Seg_61 seg_613 = new Seg_61();
            seg_613.start_index = startindex;
            seg_613.start_time = start_time;
            seg_613.end_index = list.size() - 1;
            seg_613.end_tiem = ((P1_61_data) list.get(list.size() - 1)).getTime();
            arrayList.add(seg_613);
        }
        KLog.e(this.TAG, "计算61暂停时间的Segs：" + JsonTool.toJson(arrayList));
        int pause_time = 0;
        int true_active = 0;
        for (Seg_61 seg_614 : arrayList) {
            pause_time += ModuleRouteSportService.getInstance().get61SportPauseTime(this.UID, this.dataFrom, seg_614.start_time, seg_614.end_tiem, this.sportType);
            true_active = (int) (((long) true_active) + (((seg_614.end_tiem / 1000) * 1000) - ((seg_614.start_time / 1000) * 1000)));
        }
        KLog.e(this.TAG, "pause_time: " + pause_time);
        KLog.e(this.TAG, "true_active: " + true_active);
        return true_active - (pause_time * 1000);
    }

    public int getPauseTime() {
        return ModuleRouteSportService.getInstance().get61SportPauseTime(this.UID, this.dataFrom, this.startTime, this.endTime, this.sportType);
    }

    private void get61FromSD() {
        if (FileUtils.checkFileExists(this.saveDirPath + this.fileName)) {
            KLog.e(this.TAG, "no2855本地存在对应61文件，入库");
            save61File2Sql();
            return;
        }
        KLog.e(this.TAG, "no2855本地不存在对应61文件-->去网络请求吧");
        get61DataFromNet();
    }

    /* access modifiers changed from: private */
    public void get61DataFromNet() {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                RunActivityHealthyModeImpl.this.save61File2Sql();
            }

            public void onFail(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onWatch_Health_Fail(e, RunActivityHealthyModeImpl.this.startTime, RunActivityHealthyModeImpl.this.UID, RunActivityHealthyModeImpl.this.dataFrom);
                }
            }
        }).get61FileDown(this.UID, this.dataFrom, new DateUtil(this.startTime, false).getSyyyyMMddDate(), this.saveDirPath, this.fileName);
    }

    /* access modifiers changed from: private */
    public void save61File2Sql() {
        Observable.fromArray(Environment.getExternalStorageDirectory() + this.saveDirPath + this.fileName).observeOn(Schedulers.io()).map(new Function<String, Boolean>() {
            public Boolean apply(String s) throws Exception {
                File file = new File(s);
                KLog.e("no2855开始将61文件内容写入数据库。。。");
                BufferedReader buffereader = new BufferedReader(new FileReader(file));
                int count = 0;
                while (true) {
                    String line = buffereader.readLine();
                    if (line == null) {
                        break;
                    }
                    count++;
                    ModuleRouteSportService.getInstance().save61DataFromFile(RunActivityHealthyModeImpl.this.UID, RunActivityHealthyModeImpl.this.dataFrom, Util.hexToBytes(line));
                }
                if (count == 0) {
                    KLog.e("此本地61文件为空:还是要去网络请求" + file.getPath());
                    return Boolean.valueOf(false);
                }
                DateUtil dateUtil = new DateUtil(RunActivityHealthyModeImpl.this.startTime, false);
                ModuleRouteSportService.getInstance().let61To28(RunActivityHealthyModeImpl.this.UID, dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), RunActivityHealthyModeImpl.this.dataFrom);
                return Boolean.valueOf(true);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<Boolean>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(Boolean value) {
                if (value.booleanValue()) {
                    RunActivityHealthyModeImpl.this.getHealthyDataAboutWatch(RunActivityHealthyModeImpl.this.UID, RunActivityHealthyModeImpl.this.startTime, RunActivityHealthyModeImpl.this.endTime, RunActivityHealthyModeImpl.this.dataFrom, RunActivityHealthyModeImpl.this.fileName, RunActivityHealthyModeImpl.this.saveDirPath, RunActivityHealthyModeImpl.this.sportType, RunActivityHealthyModeImpl.this.isMertric, RunActivityHealthyModeImpl.this.age, RunActivityHealthyModeImpl.this.devModel, false);
                    RunActivityHealthyModeImpl.this.mLoadCallback.on61Success();
                    return;
                }
                RunActivityHealthyModeImpl.this.get61DataFromNet();
            }

            public void onError(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onWatch_Health_Fail(e, RunActivityHealthyModeImpl.this.startTime, RunActivityHealthyModeImpl.this.UID, RunActivityHealthyModeImpl.this.dataFrom);
                }
            }

            public void onComplete() {
            }
        });
    }

    private List<P1_61_data> get61FromSql() {
        return ModuleRouteSportService.getInstance().get61Data(this.startTime, this.endTime, this.dataFrom, this.UID);
    }

    public void getHeartDataWatch() {
        getHeartData(1, true);
    }

    private int getHeartLev(int maxHeart, int heart) {
        if (((double) heart) <= ((double) maxHeart) * 0.5d) {
            return 0;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.6d) {
            return 1;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.7d) {
            return 2;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.8d) {
            return 3;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.9d) {
            return 4;
        }
        return 5;
    }

    public void getPaceChartBeanListWatch() {
        List<DataFragmentBean> dataFragmentBeans = new ArrayList<>();
        this.mP1_61_data_not_all = ModuleRouteSportService.getInstance().getsport61Data(this.UID, this.dataFrom, this.startTime, this.endTime, this.sportType, false);
        if (this.mP1_61_data_not_all == null || this.mP1_61_data_not_all.size() == 0) {
            KLog.d("no2855onDiag111 mP1_61_data_not_all is null");
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onWatch_PaceBeanList_Fail(null, this.startTime, this.UID, this.dataFrom);
            }
        } else {
            KLog.d("no2855onDiag111 mP1_61_data_not_all is not null");
            ArrayList<PaceBean> arrayList = new ArrayList<>();
            float totlDS = 0.0f;
            for (P1_61_data data : this.mP1_61_data_not_all) {
                if (data.getState_type() != 1) {
                    PaceBean paceBean = new PaceBean();
                    if (data.getDistance() == 0.0f) {
                        paceBean.time_s_one = 0.0f;
                    } else {
                        paceBean.time_s_one = 60.0f / data.getDistance();
                    }
                    paceBean.list_m_count = new ArrayList();
                    int int_distance = (int) data.getDistance();
                    float last_distance = data.getDistance() - ((float) int_distance);
                    for (int i = 0; i < int_distance; i++) {
                        paceBean.list_m_count.add(Float.valueOf(1.0f));
                    }
                    if (last_distance != 0.0f) {
                        paceBean.list_m_count.add(Float.valueOf(last_distance));
                        paceBean.time_s_last = last_distance;
                    }
                    totlDS += data.getDistance();
                    arrayList.add(paceBean);
                    if (!this.isMertric) {
                        data.setDistance((float) Util.kmToMile((double) (data.getDistance() / 1000.0f)));
                    } else {
                        data.setDistance(data.getDistance() / 1000.0f);
                    }
                }
            }
            KLog.e("size " + this.mP1_61_data_not_all.size() + "  sum_m " + totlDS + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            float sum = 0.0f;
            float km_total_times = 0.0f;
            for (PaceBean paceBean2 : arrayList) {
                if (paceBean2.time_s_one == 0.0f) {
                    km_total_times += 60.0f;
                }
                for (int i2 = 0; i2 < paceBean2.list_m_count.size(); i2++) {
                    if (i2 != paceBean2.list_m_count.size() - 1 || paceBean2.time_s_last == 0.0f) {
                        km_total_times += paceBean2.time_s_one;
                    } else {
                        km_total_times += paceBean2.time_s_last * paceBean2.time_s_one;
                    }
                    if (this.isMertric) {
                        sum += ((Float) paceBean2.list_m_count.get(i2)).floatValue();
                    } else {
                        sum = (float) (((double) sum) + (((double) ((Float) paceBean2.list_m_count.get(i2)).floatValue()) * 3.28d));
                    }
                    if (((int) sum) >= 1000 && this.isMertric) {
                        KLog.e("1km " + sum + " time_s " + km_total_times);
                        dataFragmentBeans.add(new DataFragmentBean(km_total_times, sum / 1000.0f));
                        sum = 0.0f;
                        km_total_times = 0.0f;
                    }
                    if (((int) sum) >= 5280 && !this.isMertric) {
                        KLog.e("1mile " + sum + " time_s " + km_total_times);
                        dataFragmentBeans.add(new DataFragmentBean(km_total_times, sum / 5280.0f));
                        sum = 0.0f;
                        km_total_times = 0.0f;
                    }
                }
            }
            KLog.e("最后剩下的距离 " + sum + "  " + km_total_times);
            dataFragmentBeans.add(new DataFragmentBean(km_total_times, sum / 1000.0f));
        }
        KLog.e(JsonTool.toJson(dataFragmentBeans));
        if (this.mLoadCallback != null) {
            this.mLoadCallback.onWatch_PaceBeanList_Success(dataFragmentBeans);
        }
    }

    public void getDiagramsDataWatch() {
        DiagramsData diagramsData = new DiagramsData();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        float maxY_pace = 5000.0f;
        float minY_pace = 0.0f;
        int maxY_rate = 0;
        float max_pace_sum = 0.0f;
        float max_rate_sum = 0.0f;
        float sum_distance = 0.0f;
        float sum_steps = 0.0f;
        if (this.mP1_61_data_not_all == null || this.mP1_61_data_not_all.size() == 0) {
            KLog.d("no2855onDiag mP1_61_data_not_all is not null");
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onWatch_Diagrams_Fail(null, this.startTime, this.UID, this.dataFrom);
                return;
            }
            return;
        }
        for (P1_61_data data : this.mP1_61_data_not_all) {
            KLog.d("no2855onDiag 数据: " + data.getSeq() + " =step= " + data.getStep() + " == " + data.getState_type() + " == " + data.getMin());
            float i = 0.0f;
            float f1 = 0.0f;
            if (data.getDistance() != 0.0f) {
                i = 1.0f / data.getDistance();
                f1 = Util.doubleToFloat(2, (double) i);
            }
            max_pace_sum += f1;
            if (i != 0.0f && i < maxY_pace) {
                maxY_pace = f1;
                diagramsData.setMaxY_pace(maxY_pace);
            }
            if (i > minY_pace) {
                minY_pace = f1;
                diagramsData.setMinY_pace(minY_pace);
            }
            if (data.getStep() > maxY_rate) {
                maxY_rate = data.getStep();
                diagramsData.setMaxY_rate(maxY_rate);
            }
            arrayList.add(new DlineDataBean(data.getTime() / 1000, f1));
            max_rate_sum += (float) data.getStep();
            arrayList2.add(new DlineDataBean(data.getTime() / 1000, (float) data.getStep()));
            sum_distance += data.getDistance();
            sum_steps += (float) data.getStep();
        }
        diagramsData.setPaceDataBeans(arrayList);
        if (this.mSportTime == 0) {
            this.mSportTime = getSportTime() / 1000;
        }
        if (this.sportType == 136) {
            if (maxY_pace == 5000.0f) {
                diagramsData.setMaxY_pace(0.0f);
            }
            diagramsData.setPace(getPace(sum_distance));
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onWatch_Diagrams_Success(diagramsData);
                return;
            }
            return;
        }
        KLog.e("no2855onDiag : 算出的步幅图: " + JsonTool.toJson(arrayList2));
        diagramsData.setRateDataBeans(arrayList2);
        if (maxY_pace == 5000.0f) {
            maxY_pace = 0.0f;
        }
        diagramsData.setMaxY_pace(maxY_pace);
        diagramsData.setPace(getPace(sum_distance));
        diagramsData.setAvg_rate(getCadence((int) sum_steps));
        diagramsData.setMax_rate(maxY_rate);
        diagramsData.setMaxY_rate(maxY_rate);
        diagramsData.setMaxY_pace(maxY_pace);
        diagramsData.setMinY_pace(minY_pace);
        if (this.mLoadCallback != null) {
            this.mLoadCallback.onWatch_Diagrams_Success(diagramsData);
        }
    }

    public void getHealthyDataAboutPhone(long uid, long start, long end, float user_height, boolean is_Mertric, int sport_type, String dataFrom2) {
        this.UID = uid;
        this.startTime = start;
        this.endTime = end;
        this.userHeight = user_height;
        this.isMertric = is_Mertric;
        this.sportType = sport_type;
        this.dataFrom = dataFrom2;
        KLog.e(this.TAG, "user_height: " + user_height);
        MapHealthyData healthyData = new MapHealthyData();
        healthyData.setMertric(is_Mertric);
        CopySportGps copySportGps = ModuleRouteSportService.getInstance().getOneTbSport(uid, this.startTime / 1000, 0, this.sportType, dataFrom2);
        if (copySportGps != null && copySportGps.getDistance() != 0.0f) {
            int bufu = (int) (this.userHeight / 0.45f);
            if (bufu < 35 || bufu > 135) {
                bufu = 72;
            }
            healthyData.setDistance(Util.doubleToFloat(2, (double) (copySportGps.getDistance() / 1000.0f)));
            int mActivity_time = copySportGps.getDuration();
            healthyData.setActive_time(mActivity_time);
            healthyData.setCal((int) copySportGps.getCalorie());
            healthyData.setPace(getPacePhone(healthyData.getDistance(), mActivity_time));
            healthyData.setSpeed(getSpeedPhone(healthyData.getDistance(), mActivity_time));
            if (sport_type == 0 || sport_type == 2) {
                if (sport_type == 0) {
                    bufu += 13;
                }
                if (this.isMertric) {
                    healthyData.setStride(bufu);
                    PHONE_STRIDE = ((float) bufu) / 100.0f;
                } else {
                    healthyData.setStride((int) Util.cm2in((double) bufu));
                    PHONE_STRIDE = (float) ((int) Util.cm2in((double) bufu));
                }
                KLog.e(this.TAG, "bufu: " + bufu);
                int bushu = (int) ((copySportGps.getDistance() * 100.0f) / ((float) bufu));
                KLog.e(this.TAG, "bushu: " + bushu);
                KLog.e(this.TAG, Float.valueOf(copySportGps.getDistance()));
                KLog.e(this.TAG, Float.valueOf(healthyData.getDistance()));
                healthyData.setTotal_step(bushu);
                PHONE_STEP = (float) bushu;
                healthyData.setRate((int) ((((double) bushu) / (((double) mActivity_time) * 1.0d)) * 60.0d));
            }
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onPhone_Healty_Success(healthyData);
            }
        } else if (this.mLoadCallback != null) {
            this.mLoadCallback.onPhone_Health_Fail(null, this.startTime, uid, dataFrom2);
        }
    }

    private int getPacePhone(float totalDis, int mActivity_time) {
        if (totalDis > 0.0f) {
            return (int) (((float) mActivity_time) / totalDis);
        }
        return 0;
    }

    private float getSpeedPhone(float totalDis, int mActivity_time) {
        if (mActivity_time > 0) {
            return Util.doubleToFloat(1, (double) (totalDis / (((float) mActivity_time) / 3600.0f)));
        }
        return 0.0f;
    }

    public void getPaceChartBeanListPhone() {
        getPacePhoneWrist(DataSupport.where("uid=? and time_id=?", UserConfig.getInstance().getNewUID() + "", (this.startTime / 1000) + "").order("time asc").find(TB_location.class));
    }

    public void getPaceChartBeanListWristGps(long startTime2, long endTime2, String dataFrom2) {
        List<BleGpsData> bleGpsDataList = ModuleRouteSportService.getInstance().getBleGps(UserConfig.getInstance().getNewUID(), startTime2 / 1000, endTime2 / 1000, dataFrom2);
        List<TB_location> locations = new ArrayList<>(bleGpsDataList.size());
        for (BleGpsData bleGpsData : bleGpsDataList) {
            TB_location location = new TB_location();
            location.setTime_id(startTime2 / 1000);
            location.setTime(bleGpsData.getTime());
            location.setLat(bleGpsData.getLat());
            location.setLon(bleGpsData.getLon());
            location.setUid(bleGpsData.getUid());
            locations.add(location);
        }
        getPacePhoneWrist(locations);
    }

    private void getPacePhoneWrist(List<TB_location> locations) {
        List<DataFragmentBean> dataFragmentBeans = new ArrayList<>();
        if (locations.size() > 1) {
            float dist = 0.0f;
            long times = ((TB_location) locations.get(0)).getTime_id();
            for (int i = 0; i < locations.size(); i++) {
                if (i >= 1) {
                    float[] dis = new float[1];
                    Location.distanceBetween(((TB_location) locations.get(i - 1)).getLat(), ((TB_location) locations.get(i - 1)).getLon(), ((TB_location) locations.get(i)).getLat(), ((TB_location) locations.get(i)).getLon(), dis);
                    if (!this.isMertric) {
                        dis[0] = Util.m2ft((double) dis[0]);
                    }
                    Log.d("jisuangs", "计算出gpstime:距离: " + dis[0]);
                    int factor = 1000;
                    if (!this.isMertric) {
                        factor = 5280;
                    }
                    if (dis[0] + dist >= ((float) factor)) {
                        float dis2 = (dis[0] + dist) - ((float) factor);
                        int time2 = (int) ((((float) ((int) (((TB_location) locations.get(i)).getTime() - ((TB_location) locations.get(i - 1)).getTime()))) * dis2) / dis[0]);
                        dataFragmentBeans.add(new DataFragmentBean((float) Math.abs((((TB_location) locations.get(i)).getTime() - times) - ((long) time2)), 1.0f));
                        Log.d("jisuangs", "计算出gpstime:" + times + " - " + dist + " - " + ((TB_location) locations.get(i)).getTime() + " - " + time2 + " - " + ((((TB_location) locations.get(i)).getTime() - times) - ((long) time2)));
                        times = ((TB_location) locations.get(i)).getTime() - ((long) time2);
                        dist = dis2;
                    } else {
                        dist += dis[0];
                    }
                    if (i == locations.size() - 1) {
                        dataFragmentBeans.add(new DataFragmentBean((float) (((TB_location) locations.get(i)).getTime() - times), dist / ((float) factor)));
                    }
                }
            }
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onPhone_PaceBeanList_Success(dataFragmentBeans);
            }
        } else if (this.mLoadCallback != null) {
            this.mLoadCallback.onPhone_PaceBeanList_Fail(null, this.startTime, this.UID, this.dataFrom);
        }
    }

    private void getDiagramsData(List<TB_location> locations, long uid, String dataFrom2, long startTime2, boolean newPace) {
        int maxY_rate;
        DlineDataBean dlineDataBean_rate;
        DlineDataBean dlineDataBean_rate2;
        float disAll2GetRate;
        DlineDataBean dlineDataBean_rate3;
        DiagramsData diagramsData = new DiagramsData();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        float maxY_pace = 5000.0f;
        float minY_pace = 0.0f;
        int maxY_rate2 = 0;
        float factor = 1000.0f;
        float allDistance = 0.0f;
        float disAll = 0.0f;
        int timeNum = 0;
        if (locations.size() > 1) {
            long time = ((TB_location) locations.get(0)).getTime();
            for (int i = 0; i < locations.size(); i++) {
                if (i > 0) {
                    float[] dis = new float[1];
                    Location.distanceBetween(((TB_location) locations.get(i - 1)).getLat(), ((TB_location) locations.get(i - 1)).getLon(), ((TB_location) locations.get(i)).getLat(), ((TB_location) locations.get(i)).getLon(), dis);
                    if (!this.isMertric) {
                        dis[0] = Util.m2ft((double) dis[0]);
                        factor = 5280.0f;
                    }
                    disAll += dis[0];
                    KLog.e("no2855licl: " + dis[0]);
                    allDistance += dis[0];
                    if (((TB_location) locations.get(i)).getTime() - time >= 60) {
                        int num = (int) ((((TB_location) locations.get(i)).getTime() - time) / 60);
                        KLog.d("no285511: " + num);
                        if (num > 1) {
                            if (disAll > 0.0f) {
                                float avgDis = disAll / ((float) num);
                                float dis2 = factor / avgDis;
                                float f1 = Util.doubleToFloat(2, (double) dis2);
                                for (int j = 0; j < num; j++) {
                                    timeNum++;
                                    DlineDataBean dlineDataBean = new DlineDataBean(60 + time, f1);
                                    arrayList.add(dlineDataBean);
                                    if (this.isMertric) {
                                        dlineDataBean_rate3 = new DlineDataBean(60 + time, (float) ((int) (avgDis / PHONE_STRIDE)));
                                        maxY_rate2 = (int) Math.max(avgDis / PHONE_STRIDE, (float) maxY_rate2);
                                    } else {
                                        dlineDataBean_rate3 = new DlineDataBean(60 + time, (float) ((int) (Util.ft2in((double) avgDis) / ((double) PHONE_STRIDE))));
                                        maxY_rate2 = (int) Math.max(Util.ft2in((double) avgDis) / ((double) PHONE_STRIDE), (double) maxY_rate2);
                                    }
                                    arrayList2.add(dlineDataBean_rate3);
                                    KLog.e(this.TAG, "licl:" + dis2 + "/" + maxY_rate2);
                                    minY_pace = Math.max(f1, minY_pace);
                                    if (f1 != 0.0f) {
                                        maxY_pace = Math.min(f1, maxY_pace);
                                    }
                                }
                            } else {
                                timeNum++;
                                DlineDataBean dlineDataBean2 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), 0.0f);
                                arrayList.add(dlineDataBean2);
                                DlineDataBean dlineDataBean3 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), 0.0f);
                                arrayList2.add(dlineDataBean3);
                            }
                            time += (long) (num * 60);
                        } else {
                            timeNum++;
                            time = ((TB_location) locations.get(i)).getTime();
                            KLog.e(this.TAG, "disAll: " + disAll);
                            if (disAll > 0.0f) {
                                float dis22 = factor / disAll;
                                float f12 = Util.doubleToFloat(2, (double) dis22);
                                DlineDataBean dlineDataBean4 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), f12);
                                arrayList.add(dlineDataBean4);
                                if (this.isMertric) {
                                    dlineDataBean_rate2 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), (float) ((int) (disAll / PHONE_STRIDE)));
                                } else {
                                    dlineDataBean_rate2 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), (float) ((int) (Util.ft2in((double) disAll) / ((double) PHONE_STRIDE))));
                                }
                                if (this.isMertric) {
                                    disAll2GetRate = disAll;
                                } else {
                                    disAll2GetRate = (float) Util.ft2in((double) disAll);
                                }
                                arrayList2.add(dlineDataBean_rate2);
                                if (disAll2GetRate / PHONE_STRIDE > ((float) maxY_rate2)) {
                                    maxY_rate2 = (int) (disAll2GetRate / PHONE_STRIDE);
                                }
                                KLog.e(this.TAG, "licl2:" + dis22 + "/" + maxY_rate2);
                                minY_pace = Math.max(f12, minY_pace);
                                if (f12 != 0.0f) {
                                    maxY_pace = Math.min(f12, maxY_pace);
                                }
                            } else {
                                DlineDataBean dlineDataBean5 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), 0.0f);
                                arrayList.add(dlineDataBean5);
                                DlineDataBean dlineDataBean6 = new DlineDataBean(((TB_location) locations.get(i)).getTime(), 0.0f);
                                arrayList2.add(dlineDataBean6);
                            }
                        }
                        disAll = 0.0f;
                    }
                }
                if (i == locations.size() - 1 && ((TB_location) locations.get(i)).getTime() - time < 60 && disAll != 0.0f) {
                    disAll = (60.0f * disAll) / ((float) (((TB_location) locations.get(i)).getTime() - time));
                    DlineDataBean dlineDataBean7 = new DlineDataBean(60 + time, Util.doubleToFloat(2, (double) (factor / disAll)));
                    arrayList.add(dlineDataBean7);
                    if (this.isMertric) {
                        dlineDataBean_rate = new DlineDataBean(60 + time, (float) ((int) (disAll / PHONE_STRIDE)));
                    } else {
                        dlineDataBean_rate = new DlineDataBean(60 + time, (float) ((int) (Util.ft2in((double) disAll) / ((double) PHONE_STRIDE))));
                    }
                    KLog.e(this.TAG, "disAll: " + JsonUtils.toJson(dlineDataBean_rate));
                    arrayList2.add(dlineDataBean_rate);
                }
            }
        }
        KLog.e(this.TAG, "diagram_pace: " + JsonTool.toJson(arrayList));
        if (timeNum == 0 && disAll > 0.0f) {
            if (this.isMertric) {
                maxY_rate = (int) (disAll / PHONE_STRIDE);
            } else {
                maxY_rate = (int) (Util.ft2in((double) disAll) / ((double) PHONE_STRIDE));
            }
            maxY_pace = Util.doubleToFloat(2, (double) (factor / disAll));
            KLog.e(this.TAG, "maxY_pace3: " + maxY_pace);
        }
        if (allDistance > 0.0f) {
            diagramsData.setMinY_pace(minY_pace);
            diagramsData.setPaceDataBeans(arrayList);
            if (this.sportType == 1) {
                if (maxY_pace == 5000.0f) {
                    KLog.e(this.TAG, "maxY_pace1: " + maxY_pace);
                    maxY_pace = 0.0f;
                }
                diagramsData.setMaxY_pace(maxY_pace);
                KLog.e(this.TAG, "maxY_pace4: " + maxY_pace);
                float sd = ((float) ((((TB_location) locations.get(locations.size() - 1)).getTime() / 60) - (((TB_location) locations.get(0)).getTime_id() / 60))) / (allDistance / factor);
                KLog.e(this.TAG, "sd: " + sd);
                if (sd > 0.0f) {
                    diagramsData.setPace(sd);
                }
                if (this.mLoadCallback != null) {
                    this.mLoadCallback.onPhone_Diagrams_Success(diagramsData);
                    return;
                }
                return;
            }
            Log.d("jisuangs", new Gson().toJson((Object) arrayList2));
            if (!newPace) {
                diagramsData.setRateDataBeans(arrayList2);
            }
            if (maxY_pace == 5000.0f) {
                KLog.e(this.TAG, "maxY_pace2: " + maxY_pace);
                maxY_pace = 0.0f;
            }
            diagramsData.setMaxY_pace(maxY_pace);
            KLog.e(this.TAG, "maxY_pace: " + maxY_pace);
            float sd2 = ((float) ((((TB_location) locations.get(locations.size() - 1)).getTime() / 60) - (((TB_location) locations.get(0)).getTime_id() / 60))) / (allDistance / factor);
            if (sd2 > 0.0f) {
                diagramsData.setPace(sd2);
            }
            diagramsData.setMaxY_rate(maxY_rate2);
            if (timeNum == 0) {
                if (this.isMertric) {
                    diagramsData.setAvg_rate((int) (allDistance / PHONE_STRIDE));
                } else {
                    diagramsData.setAvg_rate((int) (Util.ft2in((double) allDistance) / ((double) PHONE_STRIDE)));
                }
            } else if (this.isMertric) {
                diagramsData.setAvg_rate((int) ((allDistance / PHONE_STRIDE) / ((float) timeNum)));
            } else {
                diagramsData.setAvg_rate((int) ((Util.ft2in((double) allDistance) / ((double) PHONE_STRIDE)) / ((double) timeNum)));
            }
            if (newPace) {
                arrayList2.clear();
                diagramsData.setRateDataBeans(arrayList2);
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.HR_PATH + uid + "_hr_" + startTime2 + "_" + dataFrom2 + ".txt";
                File file = new File(path);
                if (file.exists()) {
                    int[] steps = getListsRate(path);
                    if (steps != null) {
                        int maxRate = 0;
                        int sumRate = 0;
                        int num2 = 0;
                        for (int i2 = 0; i2 < steps.length; i2++) {
                            arrayList2.add(new DlineDataBean(startTime2 + ((long) (i2 * 60)), (float) steps[i2]));
                            if (steps[i2] > maxRate) {
                                maxRate = steps[i2];
                            }
                            if (steps[i2] > 0) {
                                sumRate += steps[i2];
                                num2++;
                            }
                        }
                        diagramsData.setMax_rate(maxRate);
                        diagramsData.setMaxY_rate(maxRate);
                        if (num2 == 0) {
                            diagramsData.setAvg_rate(0);
                        } else {
                            diagramsData.setAvg_rate(sumRate / num2);
                        }
                    }
                }
            }
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onPhone_Diagrams_Success(diagramsData);
            }
        } else if (this.mLoadCallback != null) {
            this.mLoadCallback.onPhone_Diagrams_Fail(null, startTime2, this.UID, dataFrom2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0086 A[SYNTHETIC, Splitter:B:39:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0092 A[SYNTHETIC, Splitter:B:45:0x0092] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:10:0x0040=Splitter:B:10:0x0040, B:36:0x0081=Splitter:B:36:0x0081} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int[] getListsRate(java.lang.String r12) {
        /*
            r11 = this;
            r8 = 0
            java.io.File r2 = new java.io.File
            r2.<init>(r12)
            java.lang.String r9 = "no2855-->开始获取heart文件"
            com.socks.library.KLog.e(r9)
            r3 = 0
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x0080 }
            r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x0080 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            r0.<init>(r4)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            r6 = 0
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            r7.<init>()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
        L_0x001d:
            java.lang.String r6 = r0.readLine()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            if (r6 == 0) goto L_0x0049
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            r9.<init>()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            java.lang.String r10 = "no2855-->读取心率文件-->"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            java.lang.StringBuilder r9 = r9.append(r6)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            java.lang.String r9 = r9.toString()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            com.socks.library.KLog.e(r9)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            r7.append(r6)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            goto L_0x001d
        L_0x003e:
            r1 = move-exception
            r3 = r4
        L_0x0040:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x008f }
            if (r3 == 0) goto L_0x0048
            r3.close()     // Catch:{ IOException -> 0x007b }
        L_0x0048:
            return r8
        L_0x0049:
            java.lang.String r9 = r7.toString()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            boolean r9 = android.text.TextUtils.isEmpty(r9)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            if (r9 == 0) goto L_0x005f
            if (r4 == 0) goto L_0x0058
            r4.close()     // Catch:{ IOException -> 0x005a }
        L_0x0058:
            r3 = r4
            goto L_0x0048
        L_0x005a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0058
        L_0x005f:
            java.lang.String r9 = r7.toString()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            java.lang.Class<com.iwown.data_link.sport_data.gps.HrUpData> r10 = com.iwown.data_link.sport_data.gps.HrUpData.class
            java.lang.Object r5 = com.iwown.lib_common.network.utils.JsonUtils.fromJson(r9, r10)     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            com.iwown.data_link.sport_data.gps.HrUpData r5 = (com.iwown.data_link.sport_data.gps.HrUpData) r5     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            int[] r8 = r5.getSf()     // Catch:{ FileNotFoundException -> 0x003e, IOException -> 0x009e, all -> 0x009b }
            if (r4 == 0) goto L_0x0074
            r4.close()     // Catch:{ IOException -> 0x0076 }
        L_0x0074:
            r3 = r4
            goto L_0x0048
        L_0x0076:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0074
        L_0x007b:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0048
        L_0x0080:
            r1 = move-exception
        L_0x0081:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x008f }
            if (r3 == 0) goto L_0x0048
            r3.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x0048
        L_0x008a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0048
        L_0x008f:
            r8 = move-exception
        L_0x0090:
            if (r3 == 0) goto L_0x0095
            r3.close()     // Catch:{ IOException -> 0x0096 }
        L_0x0095:
            throw r8
        L_0x0096:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0095
        L_0x009b:
            r8 = move-exception
            r3 = r4
            goto L_0x0090
        L_0x009e:
            r1 = move-exception
            r3 = r4
            goto L_0x0081
        L_0x00a1:
            r1 = move-exception
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.sport_module.model.RunActivityHealthyModeImpl.getListsRate(java.lang.String):int[]");
    }

    public void getDiagramsDataWristGps(long startTime2, long endTime2, String dataFrom2, boolean isNewPace) {
        long uid = UserConfig.getInstance().getNewUID();
        List<BleGpsData> bleGpsDataList = ModuleRouteSportService.getInstance().getBleGps(uid, startTime2 / 1000, endTime2 / 1000, dataFrom2);
        if (bleGpsDataList == null || bleGpsDataList.size() == 0) {
        }
        List<TB_location> locations = new ArrayList<>(bleGpsDataList.size());
        for (BleGpsData bleGpsData : bleGpsDataList) {
            TB_location location = new TB_location();
            location.setTime_id(startTime2 / 1000);
            location.setTime(bleGpsData.getTime());
            location.setLat(bleGpsData.getLat());
            location.setLon(bleGpsData.getLon());
            location.setUid(bleGpsData.getUid());
            locations.add(location);
        }
        if (dataFrom2 != null) {
            if (!dataFrom2.toUpperCase(Locale.US).contains("ANDROID")) {
                if (!dataFrom2.toUpperCase(Locale.US).contains("IPHONE")) {
                    getDiagramsData(locations, uid, dataFrom2, startTime2 / 1000, isNewPace);
                    return;
                }
            }
            getDiagramsData(locations, uid, dataFrom2, startTime2 / 1000, false);
        }
    }

    public void getDiagramsDataPhone() {
        long uid = UserConfig.getInstance().getNewUID();
        getDiagramsData(DataSupport.where("uid=? and time_id=?", uid + "", (this.startTime / 1000) + "").order("time asc").find(TB_location.class), uid, "", this.startTime / 1000, false);
    }

    public void getHealthyDataAboutWrist(long uid, long start_time, long end_time, String data_from, int age2, String dev_model) {
        this.UID = uid;
        this.startTime = start_time;
        this.endTime = end_time;
        this.dataFrom = data_from;
        this.age = age2;
        this.devModel = dev_model;
        KLog.e(this.TAG, data_from);
        getHeartDataWrist();
    }

    public void getHeartDataWrist() {
        getHeartData(0, true);
    }

    public void getSportHeartData(long uid, String dataFrom1, long startTime2, int age2, String url) {
        if (dataFrom1 != null) {
            String[] dataFroms = dataFrom1.split("&");
            String dataFrom2 = dataFrom1;
            if (dataFroms.length > 1) {
                dataFrom2 = dataFroms[1];
            }
            KLog.d("no2855--> 心率数据 获取心率 dataFrom-> " + dataFrom2);
            if (this.mLoadCallback != null) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.HR_PATH + uid + "_hr_" + startTime2 + "_" + dataFrom2 + ".txt";
                if (new File(path).exists()) {
                    getSportHeartFromFile(path);
                    return;
                }
                HeartData heartData = ModuleRouteHeartService.getInstance().getHeartDataByTime(uid, dataFrom2, 1000 * startTime2, 1000 * startTime2, age2);
                if (heartData != null) {
                    KLog.d("no2855--> 心率数据" + JsonUtils.toJson(heartData));
                    this.mLoadCallback.onSportHeartSuccess(heartData);
                    return;
                }
                getSportHeartFromNetWork(uid, dataFrom2, startTime2, url);
            }
        }
    }

    public void getR1Data(long uid, String dataFrom2, long startTime2, String url) {
        String[] split = dataFrom2.split("&");
        String data_form = "";
        if (split.length == 2) {
            data_form = split[1];
        } else if (split.length == 1) {
            data_form = split[0];
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.R1_PATH + uid + "_r1_" + (startTime2 / 1000) + "_" + data_form + ".txt";
        String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.R1_PATH + uid + "_r1_" + (startTime2 / 1000) + "_" + dataFrom2 + ".txt";
        File file = new File(path);
        File file1 = new File(path1);
        if (file.exists()) {
            getR1FromFile(path);
        } else if (!file1.exists()) {
            getR1DataByNetWork(uid, startTime2, dataFrom2, url);
        } else {
            getR1FromFile(path1);
        }
    }

    private void getR1DataByNetWork(long uid, long startTime2, String dataFrom2, String url) {
        String rootPath = LogPath.R1_PATH;
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + rootPath;
        final String fileName2 = uid + "_r1_" + (startTime2 / 1000) + "_" + dataFrom2 + ".txt";
        final String zipName = uid + "_r1_" + (startTime2 / 1000) + "_" + dataFrom2 + ".zip";
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.d("yanxi...耳机数据网络获取成功...");
                if (ZipUtil.unZip(new File(path + zipName), new File(path + fileName2))) {
                    RunActivityHealthyModeImpl.this.getR1FromFile(path + fileName2);
                } else {
                    KLog.d("yanxi... 耳机数据解压失败");
                }
            }

            public void onFail(Throwable e) {
                KLog.d(" 耳机数据请求失败");
            }
        }).downAndSaveFile(url, rootPath, zipName);
    }

    /* access modifiers changed from: private */
    public void getR1FromFile(String path) {
        Observable.fromArray(path).observeOn(Schedulers.io()).map(new Function<String, R1DataBean>() {
            public R1DataBean apply(String s) throws Exception {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(s)));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    KLog.e("no2855-->读取心率文件-->" + line);
                    stringBuffer.append(line);
                }
                if (TextUtils.isEmpty(stringBuffer.toString())) {
                    return null;
                }
                R1UpdateBean bean = (R1UpdateBean) JsonTool.fromJson(stringBuffer.toString(), R1UpdateBean.class);
                R1DataBean r1DataBean = new R1DataBean();
                r1DataBean.setRate_avg(bean.getAvgRate());
                r1DataBean.setEarth_balance(bean.getBanlance());
                r1DataBean.setEarth_time_avg(bean.getAvgGround());
                r1DataBean.setSky_time_avg(bean.getAvgFlight());
                r1DataBean.setVertical_avg(bean.getAvgVert());
                r1DataBean.setVerticalLists(r1DataBean.parse1(bean.getVert()));
                r1DataBean.setEarthTimeLists(r1DataBean.parse1(bean.getGround()));
                r1DataBean.setStepRateLists(r1DataBean.parse1(bean.getRate()));
                r1DataBean.setSpeedLists(r1DataBean.parse1(bean.getPace()));
                r1DataBean.setMaxRate((int) r1DataBean.parseMax(bean.getRate()));
                r1DataBean.setSpeed_min(r1DataBean.parseMin(bean.getPace()));
                r1DataBean.setSpeed_max(r1DataBean.parseMax(bean.getPace()));
                r1DataBean.setSpeed_avg(r1DataBean.parseAvg(bean.getPace()));
                return r1DataBean;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<R1DataBean>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(R1DataBean bean) {
                if (bean != null && RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onR1DataSuccess(bean);
                }
            }

            public void onError(Throwable e) {
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onR1Fail();
                }
            }

            public void onComplete() {
            }
        });
    }

    /* access modifiers changed from: private */
    public void getSportHeartFromFile(String path) {
        Observable.fromArray(path).observeOn(Schedulers.io()).map(new Function<String, HeartData>() {
            public HeartData apply(String s) throws Exception {
                File file = new File(s);
                KLog.e("no2855-->开始获取heart文件");
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    KLog.e("no2855-->读取心率文件-->" + line);
                    stringBuffer.append(line);
                }
                if (TextUtils.isEmpty(stringBuffer.toString())) {
                    return null;
                }
                HrUpData hrUpData = (HrUpData) JsonUtils.fromJson(stringBuffer.toString(), HrUpData.class);
                HeartData heartData = new HeartData();
                int count = 0;
                int sum = 0;
                int maxHr = 0;
                int minHr = 0;
                for (Integer integer : hrUpData.getH3()) {
                    if (integer != null && integer.intValue() > 0) {
                        sum += integer.intValue();
                        count++;
                        if (integer.intValue() > maxHr) {
                            maxHr = integer.intValue();
                        }
                        if (minHr == 0 || integer.intValue() < minHr) {
                            minHr = integer.intValue();
                        }
                    }
                }
                heartData.setHeInt(hrUpData.getH3());
                if (count > 0) {
                    heartData.setAvg(sum / count);
                }
                int total = hrUpData.getH1().getR0() + hrUpData.getH1().getR1() + hrUpData.getH1().getR2() + hrUpData.getH1().getR3() + hrUpData.getH1().getR4() + hrUpData.getH1().getR5();
                heartData.setMins(new int[]{hrUpData.getH1().getR0(), hrUpData.getH1().getR1(), hrUpData.getH1().getR2(), hrUpData.getH1().getR3(), hrUpData.getH1().getR4(), hrUpData.getH1().getR5()});
                heartData.setMax_bpm(maxHr);
                heartData.setMin_bpm(minHr);
                heartData.setTotal51(total);
                heartData.setMaxHeart(220 - hrUpData.getAg());
                return heartData;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<HeartData>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(HeartData value) {
                if (value == null) {
                    KLog.d("no2855--> file get heart is empty,go service");
                } else if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onSportHeartSuccess(value);
                }
            }

            public void onError(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onSportHeartFail();
                }
            }

            public void onComplete() {
            }
        });
    }

    private void getSportHeartFromNetWork(long uid, String dataFrom2, long startTime2, String url) {
        String savePath = LogPath.HR_PATH;
        final String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + savePath;
        final String fileName2 = uid + "_hr_" + startTime2 + "_" + dataFrom2 + ".txt";
        final String zipName = uid + "_hr_" + startTime2 + "_" + dataFrom2 + ".zip";
        KLog.d("no2855--> 心率数据 网络获取心率");
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.d("no2855--> 心率数据 网络获取心率 成功");
                if (ZipUtil.unZip(new File(path2 + zipName), new File(path2 + fileName2))) {
                    RunActivityHealthyModeImpl.this.getSportHeartFromFile(path2 + fileName2);
                } else {
                    KLog.d("no2855--> 心率数据 网络获取心率解压失败");
                }
            }

            public void onFail(Throwable e) {
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.mLoadCallback.onSportHeartFail();
                }
            }
        }).downAndSaveFile(url, savePath, zipName);
    }

    public void getHeartData(int dev_type, boolean need51) {
        boolean isOk = need51 ? ModuleRouteHeartService.getInstance().isExist53SomeDay(this.UID, this.dataFrom, new DateUtil(this.startTime, false)) && ModuleRouteHeartService.getInstance().isExist51SomeSegment(this.UID, this.dataFrom, this.startTime, this.endTime) : ModuleRouteHeartService.getInstance().isExist53SomeDay(this.UID, this.dataFrom, new DateUtil(this.startTime, false));
        KLog.d("no2855onWatch_Healty 准备获取心率??? --> ");
        if (isOk) {
            KLog.e(this.TAG, "no2855本地有手环的53数据");
            HeartData heartData = ModuleRouteHeartService.getInstance().getHeartOldDataByTime(this.UID, this.dataFrom, this.startTime, this.endTime, this.age);
            if (heartData != null) {
                if (this.mLoadCallback != null) {
                    backHeartResult(dev_type, true, heartData);
                }
            } else if (this.mLoadCallback != null) {
                backHeartResult(dev_type, false, null);
            }
        } else {
            KLog.e(this.TAG, "no2855本地没有51 or 53-->去下载");
            getHeartDataFromNet(dev_type);
        }
    }

    /* access modifiers changed from: private */
    public void backHeartResult(int dev_type, boolean isSuccess, HeartData heartData) {
        if (!isSuccess) {
            switch (dev_type) {
                case 0:
                    this.mLoadCallback.onWrist_Heart_Fail(null, this.startTime, this.UID, this.dataFrom);
                    return;
                case 1:
                    this.mLoadCallback.onWatch_Heart_Fail(null, this.startTime, this.UID, this.dataFrom);
                    return;
                default:
                    return;
            }
        } else {
            switch (dev_type) {
                case 0:
                    this.mLoadCallback.onWrist_Heart_Success(heartData);
                    return;
                case 1:
                    this.mLoadCallback.onWatch_Heart_Success(heartData);
                    return;
                default:
                    return;
            }
        }
    }

    public void getHeartDataFromNet(final int dev_type) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (!ModuleRouteHeartService.getInstance().isExist53SomeDay(RunActivityHealthyModeImpl.this.UID, RunActivityHealthyModeImpl.this.dataFrom, new DateUtil(RunActivityHealthyModeImpl.this.startTime, false))) {
                    KLog.e("no2855心率网络下载后仍查不到53值，真的没有" + RunActivityHealthyModeImpl.this.dataFrom + "的53数据");
                    if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                        RunActivityHealthyModeImpl.this.backHeartResult(dev_type, false, null);
                        return;
                    }
                    return;
                }
                RunActivityHealthyModeImpl.this.get51FromNet(dev_type);
            }

            public void onFail(Throwable e) {
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.backHeartResult(dev_type, false, null);
                }
            }
        }).downHeartHoursData(this.UID, 0, new DateUtil(this.startTime, false).getSyyyyMMddDate(), new DateUtil(this.startTime, false));
    }

    public void get51FromNet(final int dev_type) {
        DateUtil da = new DateUtil(this.startTime, false);
        da.addDay(-3);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (!ModuleRouteHeartService.getInstance().isExist51SomeSegment(RunActivityHealthyModeImpl.this.UID, RunActivityHealthyModeImpl.this.dataFrom, RunActivityHealthyModeImpl.this.startTime, RunActivityHealthyModeImpl.this.endTime)) {
                    KLog.e(RunActivityHealthyModeImpl.this.TAG, "no2855心率网络下载后仍查不到51值，真的没有" + RunActivityHealthyModeImpl.this.dataFrom + "的51数据");
                    RunActivityHealthyModeImpl.this.getHeartData(dev_type, false);
                    return;
                }
                RunActivityHealthyModeImpl.this.getHeartData(dev_type, false);
            }

            public void onFail(Throwable e) {
                if (RunActivityHealthyModeImpl.this.mLoadCallback != null) {
                    RunActivityHealthyModeImpl.this.backHeartResult(dev_type, false, null);
                }
            }
        }).heartDownRepo(this.UID, 3, da.getSyyyyMMddDate(), new DateUtil(this.startTime, false));
    }

    public void initModel(long uid, long start_time, long end_time, String data_from, String file_name, String save_dir_path, int sport_type, boolean is_Metric) {
        this.UID = uid;
        this.startTime = start_time;
        this.endTime = end_time;
        this.dataFrom = data_from;
        this.fileName = file_name;
        this.saveDirPath = save_dir_path;
        if (sport_type == 0) {
            this.sportType = 7;
        } else if (sport_type == 1) {
            this.sportType = Opcodes.FLOAT_TO_LONG;
        } else if (sport_type == 2) {
            this.sportType = Opcodes.DIV_INT;
        } else if (sport_type == 3) {
            this.sportType = 5;
        } else {
            this.sportType = sport_type;
        }
        this.isMertric = is_Metric;
        getWatchBaseData();
    }

    private void getWatchBaseData() {
        if (get61FromSql().size() != 0) {
            KLog.e("no2855--> 本地查到手表61数据");
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onWatch_Healty_Success(getHealthyData());
                return;
            }
            return;
        }
        KLog.e(this.TAG, "no2855本地没有查到手表61数据-->去文件夹查找文件");
        get61FromSD();
    }
}
