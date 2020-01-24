package com.iwown.sport_module.net.client;

import com.iwown.data_link.blood.BloodDataUpload;
import com.iwown.data_link.ecg.EcgDataAiResult;
import com.iwown.data_link.ecg.EcgDataNoteNet;
import com.iwown.data_link.ecg.EcgUploadNet;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.fatigue.FatigueSend;
import com.iwown.data_link.heart.HeartHoursData;
import com.iwown.data_link.heart.heart_sport.HeartUpSend;
import com.iwown.data_link.sleep_data.SleepUpNewSend;
import com.iwown.data_link.sport_data.SportData;
import com.iwown.data_link.weight.MacBandS2Bean;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.net.request.IRequest;
import com.iwown.sport_module.net.send.Sport28Send;
import com.iwown.sport_module.pojo.active.WalkData;
import java.io.File;
import java.util.List;
import okhttp3.RequestBody;

public class BaseClient implements IRequest {
    private int api_type;

    public int getApi_type() {
        return this.api_type;
    }

    public void setApi_type(int api_type2) {
        this.api_type = api_type2;
    }

    public void getCity(String lat, String lng, String language) {
    }

    public void getWeather(RequestBody body) {
    }

    public void get28Data(long uid, int ds, String st, DateUtil date) {
    }

    public void up28Data(long uid, List<SportData> list) {
    }

    public void up29Data(long uid, List<WalkData> list) {
    }

    public void get62FileDown(long uid, String data_from, String recordDate, String save_dir_path, String file_name) {
    }

    public void downloadSleepByDate(long uid, int ds, String st) {
    }

    public void uploadSleepData(SleepUpNewSend sleepUpNewSend) {
    }

    public void downloadSleepStatusByDate(long uid, int ds, String st) {
    }

    public void downloadHeartStatusByDate(long uid, int ds, String st) {
    }

    public void heartDownRepo(long uid, int ds, String st, DateUtil dateUtil) {
    }

    public void getWifiScaleData(long uid, int dt, String st) {
    }

    public void getWifiScaleRWData(long uid, long weightDate, String scaleid) {
    }

    public void bindWifiScale(MacBandS2Bean macBandS2Bean) {
    }

    public void downAndSaveFile(String url, String save_dir_path, String file_name) {
    }

    public void get61FileDown(long uid, String data_from, String recordDate, String save_dir_path, String file_name) {
    }

    public void downloadGPSStatDatas(long uid) {
    }

    public void uploadHeartSportData(HeartUpSend unUploadHeartSportsDatas) {
    }

    public void uploadEcgSportData(List<EcgUploadNet> list) {
    }

    public void updateEcgNote(EcgDataNoteNet net) {
    }

    public void downLoadEcgData(long uid, String year, String month, String day) {
    }

    public void downLoadEcgFile(String url, long uid, String dataFrom, String date, EcgViewDataBean bean) {
    }

    public void hasEcgDataNet(long uid, String year, String month) {
    }

    public void updateEcgAiResult(EcgDataAiResult result) {
    }

    public void uploadGpsFile(long uid, String st, String ed, String source, File file) {
    }

    public void downloadPageSportGps(long uid, int sportType, String startTime, int size) {
    }

    public void downloadPageSportBall(long uid, String startTime, int size) {
    }

    public void downloadPageSportOther(long uid, String startTime, int size) {
    }

    public void downloadPageSportSwim(long uid, String startTime, int size) {
    }

    public void uploadSport28(Sport28Send sport28Send) {
    }

    public void downloadSport28(long uid, DateUtil dateUtil) {
    }

    public void downloadAllSportGps(long uid) {
    }

    public void downloadAllSportBall(long uid) {
    }

    public void downloadAllSportOther(long uid) {
    }

    public void downloadAllSportSwim(long uid) {
    }

    public void uploadR1Data(long uid, String st, String et, String data_from, File file) {
    }

    public void getFatigueData(long uid, int pageSize, String benchmarkDate) {
    }

    public void sendFatigue(FatigueSend send) {
    }

    public void downHeartHoursData(long uid, int dt, String st, DateUtil dateUtil) {
    }

    public void UpHeartHoursData(long uid, List<HeartHoursData> list) {
    }

    public void upPhoneGpsFileAndUpGpsDetailData(long uid, File file, long time) {
    }

    public void getGpsPageData(long uid, int pageIndex, int sportType) {
    }

    public void getDateInMonthHas28(long uid, int year, int month, int zone) {
    }

    public void down29Data(long uid, int ds, String st) {
    }

    public void getAdvertise(String lat, String lng) {
    }

    public void getWeather_24h(long uid, Object lat, Object lon, String city, String key, String timestamp, String appversion, String country) {
    }

    public void getSupportsByName() {
    }

    public void hasSport28DataNet(long uid, int year, int month) {
    }

    public void downloadAllBlood(long uid, String st, String et) {
    }

    public void uploadBlooddata(BloodDataUpload bloodDataUpload) {
    }

    public void downloadBPcoverage(long uid, int year, int mouth) {
    }
}
