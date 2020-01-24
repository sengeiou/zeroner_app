package com.iwown.data_link.sport_data;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.sport_data.gps.BleGpsData;
import com.iwown.data_link.sport_data.gps.LongitudeAndLatitude;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModuleRouteSportService {
    @Autowired(name = "/device/sport_service")
    ISportService mSportService;

    static class ModuleRouteSleepServiceHolder {
        static ModuleRouteSportService moduleRouteSportService = new ModuleRouteSportService();

        ModuleRouteSleepServiceHolder() {
        }
    }

    private ModuleRouteSportService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteSportService getInstance() {
        return ModuleRouteSleepServiceHolder.moduleRouteSportService;
    }

    public int getSportTimes(long uid, int year, int month, int day, String dataFrom) {
        List<V3_sport_data> sports = getSport(uid, year, month, day, dataFrom);
        int time = 0;
        if (sports == null) {
            return 0;
        }
        for (V3_sport_data sport_data : sports) {
            if (sport_data.getSport_type() != 1) {
                time = (int) (((long) time) + ((sport_data.getEnd_uxtime() - sport_data.getStart_uxtime()) / 60));
            }
        }
        return time;
    }

    public List<V3_sport_data> getSport(long uid, int year, int month, int day, String dataFrom) {
        if (this.mSportService != null) {
            List<String> sport_data_strings = this.mSportService.get28Sport(uid, year, month, day, dataFrom);
            String str = "";
            if (!(sport_data_strings == null || sport_data_strings.size() == 0)) {
                List<V3_sport_data> v3_sport_data = new ArrayList<>();
                for (String data_string : sport_data_strings) {
                    V3_sport_data sport_data = (V3_sport_data) JsonTool.fromJson(data_string, V3_sport_data.class);
                    if (!v3_sport_data.contains(sport_data)) {
                        v3_sport_data.add(sport_data);
                    }
                }
                return v3_sport_data;
            }
        }
        return null;
    }

    public List<V3_sport_data> get28SportNoDataFrom(long uid, int year, int month, int day) {
        if (this.mSportService != null) {
            return this.mSportService.get28SportNoDataFrom(uid, year, month, day);
        }
        return null;
    }

    public List<Detail_data> get28DetailAsTimePeriod(long uid, String dataFrom, long start, long end, int sportType) {
        if (this.mSportService != null) {
            return this.mSportService.get28DetailAsTimePeriod(uid, dataFrom, start, end, sportType);
        }
        return null;
    }

    public List<V3_sport_data> get28SportAsTimePeriod(long uid, String dataFrom, long start, long end, int sportType) {
        if (this.mSportService != null) {
            List<String> sport_data_strings = this.mSportService.get28SportAsTimePeriod(uid, dataFrom, start, end, sportType);
            String str = "";
            if (!(sport_data_strings == null || sport_data_strings.size() == 0)) {
                List<V3_sport_data> v3_sport_data = new ArrayList<>();
                for (String data_string : sport_data_strings) {
                    V3_sport_data sport_data = (V3_sport_data) JsonTool.fromJson(data_string, V3_sport_data.class);
                    if (!v3_sport_data.contains(sport_data)) {
                        v3_sport_data.add(sport_data);
                    }
                }
                return v3_sport_data;
            }
        }
        return null;
    }

    public void downloadTBSport(Sport28Code code) {
        if (this.mSportService != null) {
            this.mSportService.downloadTBSport(code);
            return;
        }
        KLog.e("licl", "mSportService==null");
    }

    public void changeUpFlag(long uid) {
        if (this.mSportService != null) {
            this.mSportService.changeUpFlag(uid);
        }
    }

    public void upDateOthers(V3_sport_data v3_sport_data) {
        if (this.mSportService != null) {
            this.mSportService.updateOthers(v3_sport_data);
        }
    }

    public void deleteOthers(long uid, String data_from, int year, int month, int day) {
        if (this.mSportService != null) {
            this.mSportService.deleteOthers(uid, data_from, year, month, day);
        }
    }

    public List<V3_sport_data> querySportUpLoadData(long uid) {
        List<V3_sport_data> v3_sport_data = null;
        if (this.mSportService != null) {
            List<String> strings = this.mSportService.getNeedUpLoadSport(uid);
            if (!(strings == null || strings.size() == 0)) {
                v3_sport_data = new ArrayList<>();
                for (String string : strings) {
                    V3_sport_data sport_data = (V3_sport_data) JsonTool.fromJson(string, V3_sport_data.class);
                    if (!v3_sport_data.contains(sport_data)) {
                        v3_sport_data.add(sport_data);
                    }
                }
            }
        }
        return v3_sport_data;
    }

    public List<P1_62_data> get62Data(long start, long end, String data_from, long uid) {
        List<P1_62_data> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.get62Data(start, end, data_from, uid);
        }
        return list;
    }

    public void save62DataFromFile(long uid, String data_from, byte[] datas) {
        if (this.mSportService != null) {
            this.mSportService.save62DataFromFile(uid, data_from, datas);
        }
    }

    public List<P1_61_data> get61Data(long start, long end, String data_from, long uid) {
        List<P1_61_data> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.get61Data(uid, data_from, start, end);
        }
        return list;
    }

    public List<P1_61_data> get61DataAsSportTypeAndSortBySeq(long uid, String data_from, long start, long end, int sportType) {
        List<P1_61_data> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.get61DataAsSportTypeAndSortBySeq(uid, data_from, start, end, sportType);
        }
        return list;
    }

    public List<R1_68_data> get68Data(int year, int month, int day, String data_from, long uid) {
        List<R1_68_data> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.get68Data(uid, data_from, year, month, day);
        }
        return list;
    }

    public List<R1_68_data> get68Data(String data_from, long uid, long startTime, long endTime) {
        List<R1_68_data> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.get68Data(uid, data_from, startTime, endTime);
        }
        return list;
    }

    public void update68Data(long uid, long time, int state_type) {
        if (this.mSportService != null) {
            this.mSportService.update68StateType(uid, time, state_type);
        }
    }

    public List<P1_61_data> getsport61Data(long uid, String data_from, long start, long end, int sportType, boolean isAll) {
        List<P1_61_data> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.getSport61Data(uid, data_from, start, end, sportType, isAll);
        }
        return list;
    }

    public int get61SportPauseTime(long uid, String data_from, long start, long endTime, int sportType) {
        if (this.mSportService != null) {
            return this.mSportService.get61SportPauseTime(uid, data_from, start, endTime, sportType);
        }
        return -1;
    }

    public void save61DataFromFile(long uid, String data_from, byte[] datas) {
        if (this.mSportService != null) {
            this.mSportService.save61DataFromFile(uid, data_from, datas);
        }
    }

    public List<HomeTrandingTodayData> getTodaySports(long newUID, String device, DateUtil dateUtil) {
        if (this.mSportService != null) {
            return this.mSportService.getTodaySports(newUID, device, dateUtil);
        }
        return null;
    }

    public void let61To28(long uid, int year, int month, int day, String data_from) {
        if (this.mSportService != null) {
            this.mSportService.let61To28(uid, year, month, day, data_from);
        }
    }

    public void saveGps2SportTB(CopySportGps copySportGps) {
        if (this.mSportService != null) {
            this.mSportService.saveGps2SportTB(copySportGps);
        }
    }

    public List<CopySportGps> getSportHistory(long uid, long startTime, int count, int cardType, int sportType) {
        List<CopySportGps> copySportGps = new LinkedList<>();
        if (this.mSportService != null) {
            List<CopySportGps> copySportGpsTwo = this.mSportService.getSportHistory(uid, startTime, count, cardType, sportType);
            if (copySportGpsTwo != null && copySportGpsTwo.size() > 0) {
                copySportGps.addAll(copySportGpsTwo);
            }
        }
        return copySportGps;
    }

    public CopySportGps getOneTbSport(long uid, long startTime, int type, int sportType, String dataFrom) {
        if (this.mSportService != null) {
            return this.mSportService.getSportOne(uid, startTime, type, sportType, dataFrom);
        }
        return null;
    }

    public List<LongitudeAndLatitude> getDeviceLocation(long uid, long startTime, long endTime, String dataFrom) {
        if (this.mSportService != null) {
            return this.mSportService.getDeviceLocation(uid, startTime, endTime, dataFrom);
        }
        return null;
    }

    public void updateTBGpsSegmentUrl(String url, int upUrlType, long uid, long startTime, String dataFrom, int dataType, boolean isCheck) {
        if (this.mSportService != null) {
            this.mSportService.updateTBGpsSegmentUrl(url, upUrlType, uid, startTime, dataFrom, dataType, isCheck);
        }
    }

    public void saveSportGpsTBFromNet(SportGpsCode sportGpsCode) {
        if (this.mSportService != null) {
            this.mSportService.saveSportGpsTBFromNet(sportGpsCode);
        }
    }

    public void saveSportBallTBFromNet(SportBallCode sportGpsCode) {
        if (this.mSportService != null) {
            this.mSportService.saveSportBallTBFromNet(sportGpsCode);
        }
    }

    public void saveSportOtherTBFromNet(SportOtherCode sportGpsCode) {
        if (this.mSportService != null) {
            this.mSportService.saveSportOtherTBFromNet(sportGpsCode);
        }
    }

    public void saveSportSwimTBFromNet(SportSwimCode sportGpsCode) {
        if (this.mSportService != null) {
            this.mSportService.saveSportSwimTBFromNet(sportGpsCode);
        }
    }

    public SportTotalData getSportTotalData(long uid) {
        if (this.mSportService != null) {
            return this.mSportService.getSportTotalData(uid);
        }
        return new SportTotalData();
    }

    public boolean isP1(String dataFrom) {
        if (this.mSportService != null) {
            return this.mSportService.isP1(dataFrom);
        }
        return false;
    }

    public void uploadNoUpGps(long uid) {
        if (this.mSportService != null) {
            this.mSportService.uploadNoUpGps(uid);
        }
    }

    public List<BleGpsData> getBleGps(long uid, long startTime, long endTime, String dataFrom) {
        if (this.mSportService != null) {
            return this.mSportService.getBleGps(uid, startTime, endTime, dataFrom);
        }
        return new ArrayList();
    }

    public void saveBleGps(long uid, long time, String dataFrom, double lat, double lon) {
        if (this.mSportService != null) {
            this.mSportService.saveBleGps(uid, time, dataFrom, lat, lon);
        }
    }

    public void uploadHrFile(boolean isGpsSeg, long uid, long startTime, long endTime, String dataFrom, int sportType) {
        if (this.mSportService != null) {
            this.mSportService.uploadHrFile(isGpsSeg, uid, startTime, endTime, dataFrom, sportType);
        }
    }

    public void uploadR1File(boolean isGpsSeg, long uid, long startTime, long endTime, String dataFrom, int sportType) {
        if (this.mSportService != null) {
            this.mSportService.uploadR1File(isGpsSeg, uid, startTime, endTime, dataFrom, sportType);
        }
    }

    public void updateR1AndGpsSegment(long uid, long startTime, String dataFrom) {
        if (this.mSportService != null) {
            this.mSportService.updateR1AndGpsSegment(uid, startTime, dataFrom);
        }
    }

    public List<CopySportGps> getR1AndGpsSegment(long uid, String dataFrom) {
        List<CopySportGps> r1AndGpsSegment = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.getR1AndGpsSegment(uid, dataFrom);
        }
        return r1AndGpsSegment;
    }

    public List<Bp_data_sport> getAllDataBlood(long uid, long start, long end) {
        List<Bp_data_sport> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.getAllDataBlood(uid, start, end);
        }
        return list;
    }

    public List<Bp_data_sport> getDataBlood(long uid) {
        List<Bp_data_sport> list = new ArrayList<>();
        if (this.mSportService != null) {
            return this.mSportService.getDataBlood(uid);
        }
        return list;
    }

    public void updateDataBlood(long uid, String bptime) {
        if (this.mSportService != null) {
            this.mSportService.updateDataBlood(uid, bptime);
        }
    }

    public void saveBloodData(long uid, String datafrom, long bptime, int dbp, int sbp) {
        if (this.mSportService != null) {
            this.mSportService.saveBloodData(uid, datafrom, bptime, dbp, sbp);
        }
    }
}
