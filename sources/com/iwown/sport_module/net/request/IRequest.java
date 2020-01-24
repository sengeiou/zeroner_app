package com.iwown.sport_module.net.request;

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
import com.iwown.sport_module.net.send.Sport28Send;
import com.iwown.sport_module.pojo.active.WalkData;
import java.io.File;
import java.util.List;
import okhttp3.RequestBody;

public interface IRequest {
    void UpHeartHoursData(long j, List<HeartHoursData> list);

    void bindWifiScale(MacBandS2Bean macBandS2Bean);

    void downAndSaveFile(String str, String str2, String str3);

    void downHeartHoursData(long j, int i, String str, DateUtil dateUtil);

    void downLoadEcgData(long j, String str, String str2, String str3);

    void downLoadEcgFile(String str, long j, String str2, String str3, EcgViewDataBean ecgViewDataBean);

    void downloadAllBlood(long j, String str, String str2);

    void downloadAllSportBall(long j);

    void downloadAllSportGps(long j);

    void downloadAllSportOther(long j);

    void downloadAllSportSwim(long j);

    void downloadBPcoverage(long j, int i, int i2);

    void downloadGPSStatDatas(long j);

    void downloadHeartStatusByDate(long j, int i, String str);

    void downloadPageSportBall(long j, String str, int i);

    void downloadPageSportGps(long j, int i, String str, int i2);

    void downloadPageSportOther(long j, String str, int i);

    void downloadPageSportSwim(long j, String str, int i);

    void downloadSleepByDate(long j, int i, String str);

    void downloadSleepStatusByDate(long j, int i, String str);

    void downloadSport28(long j, DateUtil dateUtil);

    void get28Data(long j, int i, String str, DateUtil dateUtil);

    void get61FileDown(long j, String str, String str2, String str3, String str4);

    void get62FileDown(long j, String str, String str2, String str3, String str4);

    void getCity(String str, String str2, String str3);

    void getFatigueData(long j, int i, String str);

    void getWeather(RequestBody requestBody);

    void getWifiScaleData(long j, int i, String str);

    void getWifiScaleRWData(long j, long j2, String str);

    void hasEcgDataNet(long j, String str, String str2);

    void hasSport28DataNet(long j, int i, int i2);

    void heartDownRepo(long j, int i, String str, DateUtil dateUtil);

    void sendFatigue(FatigueSend fatigueSend);

    void up28Data(long j, List<SportData> list);

    void up29Data(long j, List<WalkData> list);

    void updateEcgAiResult(EcgDataAiResult ecgDataAiResult);

    void updateEcgNote(EcgDataNoteNet ecgDataNoteNet);

    void uploadBlooddata(BloodDataUpload bloodDataUpload);

    void uploadEcgSportData(List<EcgUploadNet> list);

    void uploadGpsFile(long j, String str, String str2, String str3, File file);

    void uploadHeartSportData(HeartUpSend heartUpSend);

    void uploadR1Data(long j, String str, String str2, String str3, File file);

    void uploadSleepData(SleepUpNewSend sleepUpNewSend);

    void uploadSport28(Sport28Send sport28Send);
}
