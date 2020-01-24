package com.iwown.data_link.sport_data;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.sport_data.gps.BleGpsData;
import com.iwown.data_link.sport_data.gps.LongitudeAndLatitude;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;

public interface ISportService extends IProvider {
    void changeUpFlag(long j);

    void deleteOthers(long j, String str, int i, int i2, int i3);

    void downloadTBSport(Sport28Code sport28Code);

    List<Detail_data> get28DetailAsTimePeriod(long j, String str, long j2, long j3, int i);

    List<String> get28Sport(long j, int i, int i2, int i3, String str);

    List<String> get28SportAsTimePeriod(long j, String str, long j2, long j3, int i);

    List<V3_sport_data> get28SportNoDataFrom(long j, int i, int i2, int i3);

    List<P1_61_data> get61Data(long j, String str, long j2, long j3);

    List<P1_61_data> get61DataAsSportTypeAndSortBySeq(long j, String str, long j2, long j3, int i);

    int get61SportPauseTime(long j, String str, long j2, long j3, int i);

    List<P1_62_data> get62Data(long j, long j2, String str, long j3);

    List<R1_68_data> get68Data(long j, String str, int i, int i2, int i3);

    List<R1_68_data> get68Data(long j, String str, long j2, long j3);

    List<Bp_data_sport> getAllDataBlood(long j, long j2, long j3);

    List<BleGpsData> getBleGps(long j, long j2, long j3, String str);

    List<Bp_data_sport> getDataBlood(long j);

    List<LongitudeAndLatitude> getDeviceLocation(long j, long j2, long j3, String str);

    List<String> getNeedUpLoadSport(long j);

    List<CopySportGps> getR1AndGpsSegment(long j, String str);

    List<P1_61_data> getSport61Data(long j, String str, long j2, long j3, int i, boolean z);

    List<CopySportGps> getSportHistory(long j, long j2, int i, int i2, int i3);

    CopySportGps getSportOne(long j, long j2, int i, int i2, String str);

    SportTotalData getSportTotalData(long j);

    List<HomeTrandingTodayData> getTodaySports(long j, String str, DateUtil dateUtil);

    boolean isP1(String str);

    void let61To28(long j, int i, int i2, int i3, String str);

    void save61DataFromFile(long j, String str, byte[] bArr);

    void save62DataFromFile(long j, String str, byte[] bArr);

    void saveBleGps(long j, long j2, String str, double d, double d2);

    void saveBloodData(long j, String str, long j2, int i, int i2);

    void saveGps2SportTB(CopySportGps copySportGps);

    void saveSportBallTBFromNet(SportBallCode sportBallCode);

    void saveSportGpsTBFromNet(SportGpsCode sportGpsCode);

    void saveSportOtherTBFromNet(SportOtherCode sportOtherCode);

    void saveSportSwimTBFromNet(SportSwimCode sportSwimCode);

    void update68StateType(long j, long j2, int i);

    void updateDataBlood(long j, String str);

    void updateOthers(V3_sport_data v3_sport_data);

    void updateR1AndGpsSegment(long j, long j2, String str);

    void updateTBGpsSegmentUrl(String str, int i, long j, long j2, String str2, int i2, boolean z);

    void uploadHrFile(boolean z, long j, long j2, long j3, String str, int i);

    void uploadNoUpGps(long j);

    void uploadR1File(boolean z, long j, long j2, long j3, String str, int i);
}
