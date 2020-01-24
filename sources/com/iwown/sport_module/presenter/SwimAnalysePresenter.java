package com.iwown.sport_module.presenter;

import android.os.Environment;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.gps.SwimUpData;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.sport_module.contract.SwimAnalyseContract.Presenter;
import com.iwown.sport_module.contract.SwimAnalyseContract.View;
import com.iwown.sport_module.model.FilePathData;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.pojo.SwimRateData;
import com.iwown.sport_module.pojo.data.SwimHealthyData;
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

public class SwimAnalysePresenter implements Presenter {
    private boolean isMetric;
    /* access modifiers changed from: private */
    public View swimView;

    public SwimAnalysePresenter(View swimView2, boolean isMetric2) {
        this.swimView = swimView2;
        this.isMetric = isMetric2;
    }

    public void getSwimBaseData(long uid, String dataFrom, long startTime) {
        CopySportGps copySportGps = ModuleRouteSportService.getInstance().getOneTbSport(uid, startTime, 3, Opcodes.INT_TO_DOUBLE, dataFrom);
        KLog.e("no2855-游泳 " + JsonUtils.toJson(copySportGps));
        SwimHealthyData swimHealthyData = new SwimHealthyData();
        if (copySportGps != null) {
            float avgdps = 0.0f;
            int pool = 0;
            if (copySportGps.getDone_times() > 0) {
                avgdps = Util.doubleToFloat(1, (double) (copySportGps.getDistance() / ((float) copySportGps.getStep())));
                if (!this.isMetric) {
                    avgdps = Util.doubleToFloat(1, (double) Util.m2ft((double) (copySportGps.getDistance() / ((float) copySportGps.getStep()))));
                }
                pool = (int) (copySportGps.getDistance() / ((float) copySportGps.getDone_times()));
            }
            swimHealthyData.setAvgDps(avgdps);
            int avgPace = 0;
            if (copySportGps.getDistance() > 0.0f) {
                avgPace = (int) ((((float) copySportGps.getDuration()) / copySportGps.getDistance()) * 100.0f);
            }
            swimHealthyData.setAvgPace(avgPace);
            int avgRate = 0;
            if (copySportGps.getDuration() > 0) {
                avgRate = (int) ((((float) copySportGps.getStep()) / (((float) copySportGps.getDuration()) * 1.0f)) * 60.0f);
            }
            swimHealthyData.setAvgRate(avgRate);
            float avgStroke = 0.0f;
            int avgSwolf = 0;
            if (copySportGps.getDone_times() > 0) {
                avgStroke = Util.doubleToFloat(1, (double) (((float) copySportGps.getStep()) / (((float) copySportGps.getDone_times()) * 1.0f)));
                avgSwolf = (int) (avgStroke + (((float) copySportGps.getDuration()) / (((float) copySportGps.getDone_times()) * 1.0f)));
            }
            swimHealthyData.setAvgStroke(avgStroke);
            swimHealthyData.setAvgSwolf(avgSwolf);
            swimHealthyData.setCalories(Util.doubleToFloat(1, (double) copySportGps.getCalorie()));
            swimHealthyData.setDistance((float) ((int) copySportGps.getDistance()));
            swimHealthyData.setDuration(copySportGps.getDuration());
            swimHealthyData.setLaps(copySportGps.getDone_times());
            if (!this.isMetric) {
                swimHealthyData.setDistance((float) ((int) Util.m2ft((double) copySportGps.getDistance())));
            }
            swimHealthyData.setMaxRate(100);
            swimHealthyData.setMetric(this.isMetric);
            if (pool % 5 != 0) {
                if (pool % 5 < 3) {
                    pool = (pool / 5) * 5;
                } else {
                    pool = ((pool / 5) + 1) * 5;
                }
            }
            if (!this.isMetric) {
                pool = (int) Util.m2ft((double) pool);
            }
            swimHealthyData.setPoolLength(pool);
            DateUtil dateUtil = new DateUtil(copySportGps.getStart_time(), true);
            swimHealthyData.setStartTime(dateUtil.getYyyyMMdd_HHmmDate());
            swimHealthyData.setTotalStroke(copySportGps.getStep());
            swimHealthyData.setUrl(copySportGps.getHeart_url());
            if (this.swimView != null) {
                this.swimView.onBaseDataArrive(swimHealthyData);
            }
        } else if (this.swimView != null) {
            this.swimView.onBaseDataArrive(swimHealthyData);
        }
    }

    public void getSwimRateData(long uid, String dataFrom, long startTime, long endTime, String url) {
        HeartData heartData = ModuleRouteHeartService.getInstance().getSwimDataByTime(uid, dataFrom, startTime * 1000, endTime * 1000, 25);
        SwimRateData swimRateData = new SwimRateData();
        if (heartData == null || heartData.getHeInt() == null) {
            getRateFromFileOrNet(uid, dataFrom, startTime, url);
        } else {
            swimRateData = getSwimRate(heartData.getHeInt());
            if (this.swimView != null) {
                this.swimView.onRateDataArrive(swimRateData);
            }
        }
        KLog.e("no2855--> " + JsonUtils.toJson(swimRateData));
    }

    /* access modifiers changed from: private */
    public SwimRateData getSwimRate(List<Integer> dataList) {
        SwimRateData swimRateData = new SwimRateData();
        if (dataList != null) {
            int i = 0;
            int maxRate = 0;
            int sum = 0;
            int num = 0;
            List<DlineDataBean> dlineDataBeanList = new ArrayList<>();
            for (Integer integer : dataList) {
                dlineDataBeanList.add(new DlineDataBean((long) ((i * 60) + 1000), (float) integer.intValue()));
                if (integer.intValue() > maxRate) {
                    maxRate = integer.intValue();
                }
                i++;
                if (integer.intValue() > 0) {
                    sum += integer.intValue();
                    num++;
                }
            }
            int avgRate = num == 0 ? 0 : sum / num;
            swimRateData.setMaxY_rate(maxRate);
            swimRateData.setAvg_rate(avgRate);
            swimRateData.setRateDataBeans(dlineDataBeanList);
        }
        return swimRateData;
    }

    public void getRateFromFileOrNet(long uid, String dataFrom, long startTime, String url) {
        String txtName = uid + "_" + startTime + "_" + dataFrom + ".txt";
        String zipName = uid + "_" + startTime + "_" + dataFrom + ".zip";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.SWIM_PATH + txtName;
        String zipFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.SWIM_PATH + zipName;
        if (FileUtils.checkFileExistsTwo(filePath) || FileUtils.checkFileExistsTwo(zipFilePath)) {
            getRateFromFile(filePath, zipFilePath);
        } else {
            getRateFromNet(url, zipName, txtName);
        }
    }

    /* access modifiers changed from: private */
    public void getRateFromFile(String filePath, String zipFilePath) {
        Observable.fromArray(new FilePathData(filePath, zipFilePath)).observeOn(Schedulers.io()).map(new Function<FilePathData, SwimRateData>() {
            public SwimRateData apply(FilePathData filePathData) throws Exception {
                File outPut = new File(filePathData.txtPath);
                if (FileUtils.checkFileExistsTwo(filePathData.txtPath)) {
                    KLog.d("no2855-->swim直接有txt文件");
                } else if (!FileUtils.checkFileExistsTwo(filePathData.zipPath)) {
                    return null;
                } else {
                    ZipUtil.unZip(new File(filePathData.zipPath), outPut);
                }
                if (!outPut.exists()) {
                    return null;
                }
                SwimRateData swimRateData = new SwimRateData();
                BufferedReader buffereader = new BufferedReader(new FileReader(outPut));
                String allMsg = "";
                while (true) {
                    String line = buffereader.readLine();
                    if (line == null) {
                        break;
                    }
                    allMsg = allMsg + line.trim();
                }
                SwimUpData data = (SwimUpData) JsonUtils.fromJson(allMsg, SwimUpData.class);
                if (data == null || data.getSwim() == null) {
                    return swimRateData;
                }
                return SwimAnalysePresenter.this.getSwimRate(data.getSwim());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<SwimRateData>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(SwimRateData swimRateData) {
                if (SwimAnalysePresenter.this.swimView != null) {
                    SwimAnalysePresenter.this.swimView.onRateDataArrive(swimRateData);
                }
            }

            public void onError(Throwable e) {
            }

            public void onComplete() {
            }
        });
    }

    public void getRateFromNet(String url, final String zipName, final String txtName) {
        KLog.e("no2855--> 下载的游泳 连接: " + url);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SwimAnalysePresenter.this.getRateFromFile(Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.SWIM_PATH + txtName, Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.SWIM_PATH + zipName);
            }

            public void onFail(Throwable e) {
                if (SwimAnalysePresenter.this.swimView != null) {
                    SwimAnalysePresenter.this.swimView.onRateDataArrive(null);
                }
            }
        }).downAndSaveFile(url, LogPath.SWIM_PATH, zipName);
    }
}
