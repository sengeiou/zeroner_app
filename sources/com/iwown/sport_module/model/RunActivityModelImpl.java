package com.iwown.sport_module.model;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.P1_62_data;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.my_module.utility.Constants;
import com.iwown.sport_module.contract.RunActivityContract.Model;
import com.iwown.sport_module.gps.data.GpsUpData;
import com.iwown.sport_module.gps.data.TB_location;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
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
import java.util.concurrent.Executors;
import org.litepal.crud.DataSupport;

public class RunActivityModelImpl implements Model {
    private String TAG = getClass().getSimpleName();
    /* access modifiers changed from: private */
    public long UID;
    /* access modifiers changed from: private */
    public String dataFrom = "";
    /* access modifiers changed from: private */
    public String downFileUrl = "";
    /* access modifiers changed from: private */
    public long endTime;
    /* access modifiers changed from: private */
    public String fileName;
    /* access modifiers changed from: private */
    public boolean isOk = false;
    /* access modifiers changed from: private */
    public LoadCallback mLoadCallback = null;
    /* access modifiers changed from: private */
    public List<LongitudeAndLatitude> mLocationList;
    /* access modifiers changed from: private */
    public String saveDirPath;
    /* access modifiers changed from: private */
    public int sportType = -1;
    /* access modifiers changed from: private */
    public long startTime;

    public interface LoadCallback {
        void onTrackDataFail();

        void onTrackDataSuccess(List<LongitudeAndLatitude> list);
    }

    public void setLoadCallback(LoadCallback loadCallback) {
        this.mLoadCallback = loadCallback;
    }

    private List<P1_62_data> get62FromSql() {
        return ModuleRouteSportService.getInstance().get62Data(this.startTime, this.endTime, this.dataFrom, this.UID);
    }

    public List<LongitudeAndLatitude> getTrackDataAboutWatch(long uid, long start_time, long end_time, String data_from, String file_name, String save_dir_path) {
        this.UID = uid;
        this.startTime = start_time;
        this.endTime = end_time;
        this.dataFrom = data_from;
        this.fileName = file_name;
        this.saveDirPath = save_dir_path;
        List<P1_62_data> p1_62_dataList = get62FromSql();
        if (p1_62_dataList.size() != 0) {
            KLog.e(this.TAG, "no2855-->本地查到手表62数据");
            KLog.e(JsonTool.toJson(p1_62_dataList));
            if (this.mLoadCallback != null) {
                this.mLoadCallback.onTrackDataSuccess(transformWatchData(p1_62_dataList));
            }
        } else {
            KLog.e(this.TAG, "no2855-->本地没有查到手表62数据-->去文件夹查找文件");
            if (!this.isOk) {
                get62FromSD();
            } else if (this.mLoadCallback != null) {
                this.mLoadCallback.onTrackDataFail();
            }
        }
        return null;
    }

    private List<LongitudeAndLatitude> transformWatchData(List<P1_62_data> tb_62_datas) {
        List<String> stringList = new ArrayList<>();
        for (P1_62_data tb_62_data : tb_62_datas) {
            stringList.add(tb_62_data.getGnssData());
            Log.e("licl", tb_62_data.getGnssData().toString());
        }
        List<LongitudeAndLatitude> list = new ArrayList<>();
        for (String s : stringList) {
            List<LongitudeAndLatitude> tudes = JsonTool.getListJson(s, LongitudeAndLatitude.class);
            if (tudes != null) {
                for (LongitudeAndLatitude tude : tudes) {
                    if (tude.getLongitude() != Utils.DOUBLE_EPSILON || tude.getLatitude() != Utils.DOUBLE_EPSILON) {
                        list.add(tude);
                    }
                }
            }
        }
        return list;
    }

    private void get62FromSD() {
        if (FileUtils.checkFileExists(this.saveDirPath + this.fileName)) {
            KLog.e(this.TAG, "本地存在对应62文件，入库");
            save62File2Sql();
            return;
        }
        KLog.e(this.TAG, "本地不存在对应62文件-->去网络请求吧");
        get62DataFromNet();
    }

    /* access modifiers changed from: private */
    public void get62DataFromNet() {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                RunActivityModelImpl.this.save62File2Sql();
            }

            public void onFail(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityModelImpl.this.mLoadCallback != null) {
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataFail();
                }
            }
        }).get62FileDown(this.UID, this.dataFrom, new DateUtil(this.startTime, false).getSyyyyMMddDate(), this.saveDirPath, this.fileName);
    }

    /* access modifiers changed from: private */
    public void save62File2Sql() {
        Observable.fromArray(Environment.getExternalStorageDirectory() + this.saveDirPath + this.fileName).observeOn(Schedulers.io()).map(new Function<String, Boolean>() {
            public Boolean apply(String s) throws Exception {
                File file = new File(s);
                KLog.e("本地没有查开始将62文件内容写入数据库。。。");
                BufferedReader buffereader = new BufferedReader(new FileReader(file));
                int count = 0;
                while (true) {
                    String line = buffereader.readLine();
                    if (line == null) {
                        break;
                    }
                    KLog.e("读取62文件-->" + line);
                    count++;
                    ModuleRouteSportService.getInstance().save62DataFromFile(RunActivityModelImpl.this.UID, RunActivityModelImpl.this.dataFrom, Util.hexToBytes(line));
                }
                if (count != 0) {
                    return Boolean.valueOf(true);
                }
                KLog.e("此本地62文件为空:还是要去网络请求" + file.getPath());
                return Boolean.valueOf(false);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<Boolean>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(Boolean value) {
                if (value.booleanValue()) {
                    RunActivityModelImpl.this.isOk = true;
                    RunActivityModelImpl.this.getTrackDataAboutWatch(RunActivityModelImpl.this.UID, RunActivityModelImpl.this.startTime, RunActivityModelImpl.this.endTime, RunActivityModelImpl.this.dataFrom, RunActivityModelImpl.this.fileName, RunActivityModelImpl.this.saveDirPath);
                    return;
                }
                RunActivityModelImpl.this.get62DataFromNet();
            }

            public void onError(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityModelImpl.this.mLoadCallback != null) {
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataFail();
                }
            }

            public void onComplete() {
            }
        });
    }

    public List<LongitudeAndLatitude> getTrackDataAboutPhone(long uid, long start_time, int sport_type, String fileName2, String save_dir_pat, String downUrl) {
        this.UID = uid;
        this.startTime = start_time;
        this.fileName = fileName2;
        this.saveDirPath = save_dir_pat;
        this.downFileUrl = downUrl;
        this.sportType = sport_type;
        List<TB_location> locations = getPhoneLocationDataFromSql();
        KLog.e(JsonTool.toJson(locations));
        if (locations == null || locations.size() == 0) {
            KLog.e(this.TAG, "本地没有TB_location数据");
            if (FileUtils.checkFileExists(this.saveDirPath + fileName2)) {
                KLog.e(this.TAG, "本地存在手机Location文件，入库");
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + this.saveDirPath + fileName2);
                if (FileUtils.getFileSize2(file) <= 0) {
                    file.delete();
                    KLog.e(this.TAG, "发现手机Location文件为空， 删掉这个文件，去网络下载");
                    getPhonLocationFileFromNet();
                } else {
                    savePhoneLocationFile2Sql();
                }
            } else {
                KLog.e(this.TAG, "本地不存在手机Location文件-->去网络请求吧");
                getPhonLocationFileFromNet();
            }
            return null;
        }
        KLog.e(this.TAG, "本地有TB_location数据");
        if (this.mLoadCallback != null) {
            this.mLoadCallback.onTrackDataSuccess(transformPhoneLocationData(locations));
        }
        return null;
    }

    public void getPhoneLocation(long uid, int sportType2, long startTime2, String dataFrom2, String url) {
        this.UID = uid;
        this.startTime = startTime2;
        this.fileName = "";
        this.dataFrom = dataFrom2;
        this.sportType = sportType2;
        List<TB_location> locations = getPhoneLocationDataFromSql();
        if (locations == null || locations.size() == 0) {
            getLocationFromFileOrNet(uid, startTime2 / 1000, dataFrom2, url);
            return;
        }
        KLog.e("no2855--> 本地有TB_location数据");
        if (this.mLoadCallback != null) {
            this.mLoadCallback.onTrackDataSuccess(transformPhoneLocationData(locations));
        }
    }

    public void getDeviceLocation(long uid, long startTime2, long endTime2, String dataFrom2, String url) {
        this.UID = uid;
        this.startTime = startTime2;
        this.fileName = "";
        this.dataFrom = dataFrom2;
        this.endTime = endTime2;
        if (this.mLoadCallback != null) {
            List<com.iwown.data_link.sport_data.gps.LongitudeAndLatitude> latitudes = ModuleRouteSportService.getInstance().getDeviceLocation(uid, startTime2, endTime2, dataFrom2);
            if (latitudes == null || latitudes.size() <= 0) {
                getLocationFromFileOrNet(uid, startTime2, dataFrom2, url);
                return;
            }
            List<LongitudeAndLatitude> mLocations = new ArrayList<>(latitudes.size());
            for (com.iwown.data_link.sport_data.gps.LongitudeAndLatitude latitude : latitudes) {
                if (latitude.getAltitude() != 0 || latitude.getLongitude() != Utils.DOUBLE_EPSILON) {
                    LongitudeAndLatitude location = new LongitudeAndLatitude();
                    location.setLatitude(latitude.getLatitude());
                    location.setLongitude(latitude.getLongitude());
                    mLocations.add(location);
                }
            }
            KLog.d("no2855-->  gps数据显示成功");
            this.mLoadCallback.onTrackDataSuccess(mLocations);
            if ("1".equals(url)) {
                writeGpsSdAgain(uid, startTime2, endTime2, dataFrom2);
            }
        }
    }

    private void writeGpsSdAgain(long uid, long startTime2, long endTime2, String dataFrom2) {
        final long j = uid;
        final long j2 = startTime2;
        final long j3 = endTime2;
        final String str = dataFrom2;
        Executors.newFixedThreadPool(1).submit(new Runnable() {
            public void run() {
                DataUtil.writeBlueOneGps2SD(j, j2, j3, str);
            }
        });
    }

    private void getLocationFromFileOrNet(long uid, long startTime2, String dataFrom2, String url) {
        if (dataFrom2 == null) {
            this.mLoadCallback.onTrackDataSuccess(null);
            return;
        }
        String str = "";
        String newDataFrom = dataFrom2;
        if (dataFrom2.toLowerCase(Locale.US).contains(Constants.APPSYSTEM)) {
            newDataFrom = "Android";
        } else {
            if (dataFrom2.toLowerCase(Locale.US).contains("iphone")) {
                newDataFrom = "iPhone";
            }
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH + uid + "_gps_" + startTime2 + "_" + newDataFrom + ".txt";
        String zipPath = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH + uid + "_gps_" + startTime2 + "_" + newDataFrom + ".zip";
        File file = new File(path);
        if (file.exists()) {
            getAllLocationFromFile(path);
            return;
        }
        File zipFile = new File(zipPath);
        if (!zipFile.exists()) {
            KLog.e("no2855--> 下载的gps网络请求 ");
            if (!TextUtils.isEmpty(url)) {
                getLocationFromNet(url, uid, dataFrom2, startTime2);
            } else if (this.mLoadCallback != null) {
                this.mLoadCallback.onTrackDataFail();
            }
        } else if (ZipUtil.unZip(zipFile, file)) {
            KLog.e("no2855 解压成功了吧--> ");
            getAllLocationFromFile(path);
        }
    }

    /* access modifiers changed from: private */
    public void getAllLocationFromFile(String path) {
        Observable.fromArray(path).observeOn(Schedulers.io()).map(new Function<String, Boolean>() {
            public Boolean apply(String s) throws Exception {
                File file = new File(s);
                KLog.e("no2855--> location开始将手机地理文件内容写入数据库。。。");
                BufferedReader buffereader = new BufferedReader(new FileReader(file));
                String allMsg = "";
                while (true) {
                    String line = buffereader.readLine();
                    if (line == null) {
                        break;
                    }
                    KLog.e("读取gps文件-->" + line);
                    allMsg = allMsg + line.trim();
                }
                boolean isPhone = false;
                if (RunActivityModelImpl.this.dataFrom.toLowerCase(Locale.US).contains(Constants.APPSYSTEM) || RunActivityModelImpl.this.dataFrom.toLowerCase(Locale.US).contains("iphone")) {
                    isPhone = true;
                }
                List<GpsUpData> gpsData = (List) new Gson().fromJson(allMsg, new TypeToken<List<GpsUpData>>() {
                }.getType());
                if (gpsData != null && gpsData.size() > 0) {
                    RunActivityModelImpl.this.mLocationList = new ArrayList();
                    if (isPhone) {
                        DataSupport.deleteAll(TB_location.class, "uid=? and time_id=?", RunActivityModelImpl.this.UID + "", RunActivityModelImpl.this.startTime + "");
                    }
                    for (GpsUpData gpsDatum : gpsData) {
                        LongitudeAndLatitude latitude = new LongitudeAndLatitude();
                        if (gpsDatum.getY() != Utils.DOUBLE_EPSILON || gpsDatum.getX() != Utils.DOUBLE_EPSILON) {
                            if (isPhone) {
                                TB_location tB_location = new TB_location(gpsDatum.getY(), gpsDatum.getX());
                                tB_location.setPause_type(gpsDatum.getR());
                                tB_location.setTime_id(RunActivityModelImpl.this.startTime / 1000);
                                tB_location.setTime(gpsDatum.getT());
                                tB_location.setUid(RunActivityModelImpl.this.UID);
                                tB_location.setSport_type(RunActivityModelImpl.this.sportType);
                                tB_location.setStep(gpsDatum.getV());
                                tB_location.save();
                            } else {
                                ModuleRouteSportService.getInstance().saveBleGps(RunActivityModelImpl.this.UID, gpsDatum.getT(), RunActivityModelImpl.this.dataFrom, gpsDatum.getY(), gpsDatum.getX());
                            }
                            latitude.setLatitude(gpsDatum.getY());
                            latitude.setLongitude(gpsDatum.getX());
                            RunActivityModelImpl.this.mLocationList.add(latitude);
                        }
                    }
                }
                return Boolean.valueOf(true);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<Boolean>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(Boolean value) {
                if (value.booleanValue()) {
                    RunActivityModelImpl.this.isOk = true;
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataSuccess(RunActivityModelImpl.this.mLocationList);
                }
            }

            public void onError(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityModelImpl.this.mLoadCallback != null) {
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataFail();
                }
            }

            public void onComplete() {
            }
        });
    }

    /* access modifiers changed from: private */
    public void savePhoneLocationFile2Sql() {
        Observable.fromArray(Environment.getExternalStorageDirectory() + this.saveDirPath + this.fileName).observeOn(Schedulers.io()).map(new Function<String, Boolean>() {
            public Boolean apply(String s) throws Exception {
                File file = new File(s);
                KLog.e("location开始将手机地理文件内容写入数据库。。。");
                BufferedReader buffereader = new BufferedReader(new FileReader(file));
                String allMsg = "";
                while (true) {
                    String line = buffereader.readLine();
                    if (line == null) {
                        break;
                    }
                    KLog.e("读取gps文件-->" + line);
                    allMsg = allMsg + line.trim();
                }
                List<GpsUpData> gpsData = (List) new Gson().fromJson(allMsg, new TypeToken<List<GpsUpData>>() {
                }.getType());
                if (gpsData != null && gpsData.size() > 0) {
                    DataSupport.deleteAll(TB_location.class, "uid=? and time_id=?", RunActivityModelImpl.this.UID + "", (RunActivityModelImpl.this.startTime / 1000) + "");
                    for (GpsUpData gpsDatum : gpsData) {
                        TB_location location = new TB_location(gpsDatum.getY(), gpsDatum.getX());
                        location.setPause_type(gpsDatum.getR());
                        location.setTime_id(RunActivityModelImpl.this.startTime / 1000);
                        location.setTime(gpsDatum.getT());
                        location.setUid(RunActivityModelImpl.this.UID);
                        location.setSport_type(RunActivityModelImpl.this.sportType);
                        location.setStep(gpsDatum.getV());
                        location.save();
                    }
                }
                return Boolean.valueOf(true);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new Observer<Boolean>() {
            public void onSubscribe(Disposable d) {
            }

            public void onNext(Boolean value) {
                if (value.booleanValue()) {
                    RunActivityModelImpl.this.isOk = true;
                    RunActivityModelImpl.this.getTrackDataAboutPhone(RunActivityModelImpl.this.UID, RunActivityModelImpl.this.startTime, RunActivityModelImpl.this.sportType, RunActivityModelImpl.this.fileName, RunActivityModelImpl.this.saveDirPath, RunActivityModelImpl.this.downFileUrl);
                }
            }

            public void onError(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                if (RunActivityModelImpl.this.mLoadCallback != null) {
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataFail();
                }
            }

            public void onComplete() {
            }
        });
    }

    private void getLocationFromNet(String url, long uid, String dataFrom2, long startTime2) {
        KLog.e("no2855--> 下载的gps 连接: " + url);
        String savePath = LogPath.GPS_PATH;
        final String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + savePath;
        final String fileName2 = uid + "_gps_" + startTime2 + "_" + dataFrom2 + ".txt";
        String newName = uid + "_gps_" + startTime2 + "_" + dataFrom2 + ".zip";
        if (url != null && url.endsWith(".txt")) {
            newName = fileName2;
        }
        final String zipName = newName;
        final String str = url;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (str.endsWith(".txt")) {
                    KLog.d("no2855--> 这是老的gps数据: ");
                    RunActivityModelImpl.this.getAllLocationFromFile(path2 + fileName2);
                    return;
                }
                File input = new File(path2 + zipName);
                File output = new File(path2 + fileName2);
                KLog.e("no2855解压路径: " + path2 + zipName);
                KLog.e("no2855解压路径111: " + path2 + fileName2);
                if (ZipUtil.unZip(input, output)) {
                    RunActivityModelImpl.this.getAllLocationFromFile(path2 + fileName2);
                }
            }

            public void onFail(Throwable e) {
                if (RunActivityModelImpl.this.mLoadCallback != null) {
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataFail();
                }
            }
        }).downAndSaveFile(url, savePath, zipName);
    }

    private void getPhonLocationFileFromNet() {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.e("no2855location: gps 数据下载成功: ");
                RunActivityModelImpl.this.savePhoneLocationFile2Sql();
            }

            public void onFail(Throwable e) {
                ThrowableExtension.printStackTrace(e);
                KLog.e("no2855location: gps 数据下载失败: ");
                if (RunActivityModelImpl.this.mLoadCallback != null) {
                    RunActivityModelImpl.this.mLoadCallback.onTrackDataFail();
                }
            }
        }).downAndSaveFile(this.downFileUrl, this.saveDirPath, this.fileName);
    }

    private List<LongitudeAndLatitude> transformPhoneLocationData(List<TB_location> locations) {
        List<LongitudeAndLatitude> list = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            if (((TB_location) locations.get(i)).getLat() != Utils.DOUBLE_EPSILON || ((TB_location) locations.get(i)).getLon() != Utils.DOUBLE_EPSILON) {
                LongitudeAndLatitude latitude = new LongitudeAndLatitude();
                latitude.setLatitude(((TB_location) locations.get(i)).getLat());
                latitude.setLongitude(((TB_location) locations.get(i)).getLon());
                list.add(latitude);
                if (i != 0 && ((TB_location) locations.get(i)).getPause_type() == ((TB_location) locations.get(i - 1)).getPause_type()) {
                }
                if (i == locations.size() - 1) {
                }
            }
        }
        return list;
    }

    private List<TB_location> getPhoneLocationDataFromSql() {
        List<TB_location> locations = DataSupport.where("uid=? and time_id=?", this.UID + "", (this.startTime / 1000) + "").order("time asc").find(TB_location.class);
        if (locations != null && locations.size() > 0) {
            KLog.d("no2855 location_down: " + ((TB_location) locations.get(0)).getTime() + " endtime: " + ((TB_location) locations.get(locations.size() - 1)).getTime());
        }
        return locations;
    }
}
