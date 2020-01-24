package com.iwown.sport_module.presenter;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.activity.BaseRunActivity;
import com.iwown.sport_module.base.BasePresenterImpl2;
import com.iwown.sport_module.contract.RunActivityContract.Presenter;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.model.RunActivityHealthyModeImpl;
import com.iwown.sport_module.model.RunActivityModelImpl;
import com.iwown.sport_module.model.RunActivityModelImpl.LoadCallback;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.pojo.R1DataBean;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.util.List;

public class BaseRunActivityPresenterImpl extends BasePresenterImpl2<BaseRunActivity> implements Presenter, LoadCallback, RunActivityHealthyModeImpl.LoadCallback {
    private static final int DEV_TYPE_EARPHONE = 3;
    private static final int DEV_TYPE_PHONE = 2;
    private static final int DEV_TYPE_WATCH = 1;
    private static final int DEV_TYPE_WRIST = 0;
    private CopySportGps copySportGps;
    private int dev_type = 0;
    private boolean isLoad6162 = false;
    private boolean isMertric;
    private RunActivityHealthyModeImpl mHealthyMode;
    private RunActivityModelImpl mRunActivityModel;

    public BaseRunActivityPresenterImpl(BaseRunActivity view) {
        super(view);
    }

    public BaseRunActivityPresenterImpl(BaseRunActivity view, int dev_type2) {
        super(view);
        this.dev_type = dev_type2;
    }

    public void getPhoneTrackData(long uid, long start_time, int sport_type, String fileName, String save_dir_path, String gpsDownUrl) {
        KLog.e("getPhoneTrackData");
        initRunActivityModel();
        this.mRunActivityModel.getTrackDataAboutPhone(uid, start_time, sport_type, fileName, save_dir_path, gpsDownUrl);
    }

    public void getWatchTrackData(long uid, long start_time, long end_time, String data_from, String fileName, String save_dir_path) {
        KLog.e("no2855getWatchTrackData获取地图");
        initRunActivityModel();
        this.mRunActivityModel.getTrackDataAboutWatch(uid, start_time, end_time, data_from, fileName, save_dir_path);
    }

    public void getHealthyDataAboutPhone(long uid, long start, long end, float user_height, boolean is_Mertric, int sport_type, String dataFrom) {
        KLog.e("getHealthyDataAboutPhone");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).controlLoading(true);
        }
        initHealthyMode();
        this.mHealthyMode.getHealthyDataAboutPhone(uid, start, end, user_height, is_Mertric, sport_type, dataFrom);
    }

    public void getHealthyDataAboutWatch(long uid, long start_time, long end_time, String data_from, String file_name, String save_dir_path, int sport_type, boolean is_Mertric, int age, String dev_model) {
        KLog.e("no2855getHealthyDataAboutWatch");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).controlLoading(true);
        }
        initHealthyMode();
        this.mHealthyMode.getHealthyDataAboutWatch(uid, start_time, end_time, data_from, file_name, save_dir_path, sport_type, is_Mertric, age, dev_model, true);
    }

    private void initHealthyMode() {
        if (this.mHealthyMode == null) {
            this.mHealthyMode = new RunActivityHealthyModeImpl();
            this.mHealthyMode.setLoadCallback(this);
        }
    }

    private void initRunActivityModel() {
        if (this.mRunActivityModel == null) {
            this.mRunActivityModel = new RunActivityModelImpl();
            this.mRunActivityModel.setLoadCallback(this);
        }
    }

    public void getHealthyDataAboutWrist(long uid, long start_time, long end_time, String data_from, int age, String dev_model) {
        KLog.e("getHealthyDataAboutWrist");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).controlLoading(true);
        }
        initHealthyMode();
        this.mHealthyMode.getHealthyDataAboutWrist(uid, start_time, end_time, data_from, age, dev_model);
    }

    private void getHeartDataAbutWatch() {
        if (this.mHealthyMode != null) {
            this.mHealthyMode.getHeartDataWatch();
        }
    }

    private void getHeartDataAboutWrist() {
        if (this.mHealthyMode != null) {
            this.mHealthyMode.getHeartDataWrist();
        }
    }

    private void getPaceChartDataAboutWatch() {
        if (this.mHealthyMode != null) {
            this.mHealthyMode.getPaceChartBeanListWatch();
        }
    }

    private void getPaceChartDataAboutPhone() {
        if (this.mHealthyMode != null) {
            this.mHealthyMode.getPaceChartBeanListPhone();
        }
    }

    private void getDiagramsAboutWatch() {
        if (this.mHealthyMode != null) {
            this.mHealthyMode.getDiagramsDataWatch();
        }
    }

    private void getDiagramsAboutPhone() {
        if (this.mHealthyMode != null) {
            this.mHealthyMode.getDiagramsDataPhone();
        }
    }

    public void onTrackDataSuccess(List<LongitudeAndLatitude> longitudeAndLatitudes) {
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).refreshMapView(longitudeAndLatitudes);
        }
    }

    public void onTrackDataFail() {
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).refreshMapView(null);
        }
    }

    public void onWatch_Healty_Success(MapHealthyData mapHealthyData) {
        KLog.e("no2855onWatch_Healty_Success");
        MapHealthyData mMapHealthyData = mapHealthyData;
        if (isViewNotNull()) {
            KLog.e("no2855onWatch_Healty_Success11111");
            if (this.copySportGps != null) {
                KLog.e("no2855onWatch_Healty_Success22222");
                if (mMapHealthyData == null) {
                    mMapHealthyData = new MapHealthyData();
                }
                mMapHealthyData.setCal((int) this.copySportGps.getCalorie());
                mMapHealthyData.setActive_time(this.copySportGps.getDuration());
                mMapHealthyData.setMertric(this.isMertric);
                mMapHealthyData.setDistance(this.copySportGps.getDistance() / 1000.0f);
                if (this.copySportGps.getDistance() > 0.0f) {
                    mMapHealthyData.setPace(getPace(this.copySportGps.getDuration(), this.copySportGps.getDistance()));
                }
                mMapHealthyData.setTotal_step(this.copySportGps.getStep());
                if (mMapHealthyData.getSpeed() == 0.0f) {
                    mMapHealthyData.setStride(getStride(this.copySportGps.getStep(), this.copySportGps.getDistance()));
                    if (this.copySportGps.getDuration() > 0) {
                        mMapHealthyData.setSpeed(getSpeed(this.copySportGps.getDistance(), this.copySportGps.getDuration()));
                        mMapHealthyData.setRate((int) ((((float) this.copySportGps.getStep()) / (((float) this.copySportGps.getDuration()) * 1.0f)) * 60.0f));
                    }
                }
            }
            ((BaseRunActivity) getView()).onMapHealthDataArrive(mMapHealthyData);
            if (this.isLoad6162) {
                getHeartDataAbutWatch();
            }
        }
    }

    private int getPace(int duration, float distance) {
        if (distance <= 0.0f) {
            return 0;
        }
        if (this.isMertric) {
            return (int) ((((float) duration) / distance) * 1000.0f);
        }
        return (int) (((double) duration) / Util.kmToMile((double) (distance / 1000.0f)));
    }

    private int getStride(int mStep, float distance) {
        if (mStep == 0) {
            return 0;
        }
        if (this.isMertric) {
            return (int) ((100.0f * distance) / ((float) mStep));
        }
        return (int) ((((double) distance) * 63.36d) / ((double) mStep));
    }

    private float getSpeed(float distance, int duration) {
        if (duration <= 0) {
            return 0.0f;
        }
        if (this.isMertric) {
            return Util.doubleToFloat(1, (double) (((distance / 1000.0f) / ((float) duration)) * 3600.0f));
        }
        return Util.doubleToFloat(1, (Util.kmToMile((double) (distance / 1000.0f)) / ((double) duration)) * 3600.0d);
    }

    public void onWatch_Health_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onWatch_Health_Fail");
        if (isViewNotNull()) {
            MapHealthyData mMapHealthyData = new MapHealthyData();
            if (isViewNotNull() && this.copySportGps != null) {
                mMapHealthyData.setCal((int) this.copySportGps.getCalorie());
                mMapHealthyData.setActive_time(this.copySportGps.getDuration());
                mMapHealthyData.setMertric(this.isMertric);
                mMapHealthyData.setDistance(this.copySportGps.getDistance() / 1000.0f);
                if (this.copySportGps.getDistance() > 0.0f) {
                    mMapHealthyData.setPace(getPace(this.copySportGps.getDuration(), this.copySportGps.getDistance()));
                }
                mMapHealthyData.setTotal_step(this.copySportGps.getStep());
                mMapHealthyData.setStride(getStride(this.copySportGps.getStep(), this.copySportGps.getDistance()));
                if (this.copySportGps.getDuration() > 0) {
                    mMapHealthyData.setSpeed(getSpeed(this.copySportGps.getDistance(), this.copySportGps.getDuration()));
                    mMapHealthyData.setRate((int) ((((float) this.copySportGps.getStep()) / (((float) this.copySportGps.getDuration()) * 1.0f)) * 60.0f));
                }
            }
            ((BaseRunActivity) getView()).onMapHealthDataArrive(mMapHealthyData);
        }
    }

    public void onWatch_Heart_Success(HeartData heartData) {
        KLog.e("onWatch_Heart_Success");
        if (this.isLoad6162 && isViewNotNull()) {
            ((BaseRunActivity) getView()).onHeartDataArrive(heartData);
        }
        getPaceChartDataAboutWatch();
    }

    public void onWatch_Heart_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onWatch_Heart_Fail");
        getPaceChartDataAboutWatch();
    }

    public void onWatch_PaceBeanList_Success(List<DataFragmentBean> dataFragmentBeans) {
        KLog.e("onWatch_PaceBeanList_Success");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onPaceChartBeansArrive(dataFragmentBeans);
        }
        getDiagramsAboutWatch();
    }

    public void onWatch_PaceBeanList_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onWatch_PaceBeanList_Fail");
        getDiagramsAboutWatch();
    }

    public void onWatch_Diagrams_Success(DiagramsData diagramsData) {
        KLog.e("onWatch_Diagrams_Success");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onDiagramArrive(diagramsData);
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onWatch_Diagrams_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onWatch_Diagrams_Fail");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onPhone_Healty_Success(MapHealthyData mapHealthyData) {
        KLog.e("no2855onPhone_Healty_Success" + JsonUtils.toJson(mapHealthyData));
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onMapHealthDataArrive(mapHealthyData);
        }
        if (this.copySportGps == null) {
            getPaceChartDataAboutPhone();
        } else if (this.copySportGps == null) {
        } else {
            if (this.copySportGps.getData_from().contains("Android") || this.copySportGps.getData_from().contains("iPhone")) {
                getPaceChartDataAboutPhone();
            }
        }
    }

    public void onPhone_Health_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onPhone_Health_Fail");
        MapHealthyData mMapHealthyData = new MapHealthyData();
        if (isViewNotNull()) {
            if (this.copySportGps != null) {
                mMapHealthyData.setCal((int) this.copySportGps.getCalorie());
                mMapHealthyData.setActive_time(this.copySportGps.getDuration());
                if (this.isMertric) {
                    mMapHealthyData.setDistance(this.copySportGps.getDistance() / 1000.0f);
                } else {
                    mMapHealthyData.setDistance((float) Util.meterToMile((double) (this.copySportGps.getDistance() / 1000.0f)));
                }
                if (this.copySportGps.getDistance() > 0.0f) {
                    mMapHealthyData.setPace(getPace(this.copySportGps.getDuration(), this.copySportGps.getDistance()));
                }
                mMapHealthyData.setTotal_step(this.copySportGps.getStep());
                mMapHealthyData.setStride(getStride(this.copySportGps.getStep(), this.copySportGps.getDistance()));
                if (this.copySportGps.getDuration() > 0) {
                    mMapHealthyData.setSpeed(getSpeed(this.copySportGps.getDistance(), this.copySportGps.getDuration()));
                    mMapHealthyData.setRate((int) ((((float) this.copySportGps.getStep()) / (((float) this.copySportGps.getDuration()) * 1.0f)) * 60.0f));
                }
            }
            ((BaseRunActivity) getView()).onMapHealthDataArrive(mMapHealthyData);
        }
        getPaceChartDataAboutPhone();
    }

    public void onPhone_PaceBeanList_Success(List<DataFragmentBean> dataFragmentBeans) {
        KLog.e("onPhone_PaceBeanList_Success");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onPaceChartBeansArrive(dataFragmentBeans);
        }
        if (this.copySportGps == null) {
            getDiagramsAboutPhone();
        } else if (this.copySportGps == null) {
        } else {
            if (this.copySportGps.getData_from().contains("Android") || this.copySportGps.getData_from().contains("iPhone")) {
                getDiagramsAboutPhone();
            }
        }
    }

    public void onPhone_PaceBeanList_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onPhone_PaceBeanList_Fail");
        if (this.copySportGps == null) {
            getDiagramsAboutPhone();
        } else if (this.copySportGps == null) {
        } else {
            if (this.copySportGps.getData_from().contains("Android") || this.copySportGps.getData_from().contains("iPhone")) {
                getDiagramsAboutPhone();
            }
        }
    }

    public void onPhone_Diagrams_Success(DiagramsData diagramsData) {
        KLog.e("onPhone_Diagrams_Success");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onDiagramArrive(diagramsData);
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onPhone_Diagrams_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onPhone_Diagrams_Fail");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onWrist_Heart_Success(HeartData heartData) {
        KLog.e("onWrist_Heart_Success");
        if (isViewNotNull() && this.isLoad6162) {
            ((BaseRunActivity) getView()).onHeartDataArrive(heartData);
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onWrist_Heart_Fail(Throwable e, long startTime, long uid, String dataFrom) {
        KLog.e("onWrist_Heart_Fail");
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onHeartDataArrive(null);
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onSportHeartSuccess(HeartData heartData) {
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onHeartDataArrive(heartData);
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onSportHeartFail() {
    }

    public void onR1DataSuccess(R1DataBean bean) {
        if (isViewNotNull()) {
            ((BaseRunActivity) getView()).onR1Data(bean);
            ((BaseRunActivity) getView()).controlLoading(false);
        }
    }

    public void onR1Fail() {
    }

    public void on61Success() {
        getPaceChartDataAboutWatch();
    }

    public void getSportHeart(long uid, String dataFrom, long startTime, int age, String url) {
        KLog.d("no2855--> 开始获取心率");
        if (this.mHealthyMode == null) {
            this.mHealthyMode = new RunActivityHealthyModeImpl();
            this.mHealthyMode.setLoadCallback(this);
        }
        this.mHealthyMode.getSportHeartData(uid, dataFrom, startTime / 1000, age, url);
    }

    public void getPhoneLocation(long uid, int sportType, String dataFrom, long startTime, long endTime, String url) {
        KLog.e("no28855--> 开始获取手机gps数据");
        initRunActivityModel();
        this.mRunActivityModel.getPhoneLocation(uid, sportType, startTime, dataFrom, url);
    }

    public void getDeviceLocation(long uid, String dataFrom, long startTime, long endTime, String url) {
        KLog.e("no28855--> 开始获取设备gps数据");
        initRunActivityModel();
        this.mRunActivityModel.getDeviceLocation(uid, startTime, endTime, dataFrom, url);
    }

    public void initModel(CopySportGps copySportGps2, long uid, long start_time, long end_time, String data_from, String file_name, String save_dir_path, int sport_type, boolean is_Metric) {
        initHealthyMode();
        this.copySportGps = copySportGps2;
        this.isMertric = is_Metric;
        KLog.e("no2855--> 文件: 我去: " + data_from + " ==== " + file_name + "  === " + save_dir_path + " uid:" + uid);
        this.mHealthyMode.initModel(uid, start_time, end_time, data_from, file_name, save_dir_path, sport_type, is_Metric);
        getPaceChartDataAboutWatch();
    }

    public void getR1Data(long uid, String dataFrom, long startTime, String url) {
        initHealthyMode();
        this.mHealthyMode.getR1Data(uid, dataFrom, startTime, url);
    }

    public void initCopySportGps(CopySportGps copySportGps2, boolean is_Metric, boolean isLoad61622) {
        this.copySportGps = copySportGps2;
        this.isMertric = is_Metric;
        this.isLoad6162 = isLoad61622;
    }

    public void getWristGpsPace(long uid, String dataFrom, long startTime, long endTime, boolean isNewPace) {
        initHealthyMode();
        this.mHealthyMode.getPaceChartBeanListWristGps(startTime, endTime, dataFrom);
        KLog.d("no2855:-> 配速的地图准备开始了啊");
        this.mHealthyMode.getDiagramsDataWristGps(startTime, endTime, dataFrom, isNewPace);
    }
}
