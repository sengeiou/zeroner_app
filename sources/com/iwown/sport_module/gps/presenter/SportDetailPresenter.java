package com.iwown.sport_module.gps.presenter;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.data.CopySportAll;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.SportTotalData;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.contract.SportDetailContract.SportDetailView;
import com.iwown.sport_module.gps.data.SportDetailItem;
import com.iwown.sport_module.gps.data.SportItemData;
import com.iwown.sport_module.gps.data.TB_sport_all_ball;
import com.iwown.sport_module.gps.data.TB_sport_all_gps;
import com.iwown.sport_module.gps.data.TB_sport_all_other;
import com.iwown.sport_module.gps.data.TB_sport_all_swim;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.litepal.crud.DataSupport;

public class SportDetailPresenter implements com.iwown.sport_module.gps.contract.SportDetailContract.SportDetailPresenter {
    /* access modifiers changed from: private */
    public SportDetailView detailView;
    private boolean isMetric;

    public SportDetailPresenter(SportDetailView detailView2, boolean isMetric2) {
        this.detailView = detailView2;
        this.isMetric = isMetric2;
    }

    public void getTotalGps(long uid, int cardType, int sportType) {
        if (cardType == 0) {
            this.detailView.loadAllSportSuccess(getGpsAllData(uid, sportType));
            getGpsAllFromNet(uid, sportType);
        } else if (cardType == 1) {
            this.detailView.loadAllSportSuccess(getBallAllData(uid));
            getBallAllFromNet(uid);
        } else if (cardType == 2) {
            this.detailView.loadAllSportSuccess(getOtherAllData(uid));
            getOtherAllFromNet(uid);
        } else if (cardType == 3) {
            this.detailView.loadAllSportSuccess(getSwimAllData(uid));
            getSwimAllFromNet(uid);
        }
    }

    public void getDetailGpsServer(long uid, long startTime, int count, int cardType, int sportType) {
        if (cardType == 0) {
            getSportGpsNet(uid, startTime, count, cardType, sportType);
        } else if (cardType == 1) {
            getSportBallNet(uid, startTime, count, cardType, sportType);
        } else if (cardType == 3) {
            getSportSwimNet(uid, startTime, count, cardType, sportType);
        } else if (cardType == 2) {
            getSportOtherNet(uid, startTime, count, cardType, sportType);
        }
    }

    public void getDetailGpsLocal(long uid, int cardType, int sportType) {
        getSportGpsLocal(uid, System.currentTimeMillis() / 1000, 20, cardType, sportType);
    }

    public void getSportGpsLocal(long uid, long startTime, int count, int cardType, int sportType) {
        this.detailView.loadPageDataSuccess(getDetailItems(uid, startTime, count, cardType, sportType));
    }

    /* access modifiers changed from: private */
    public List<SportDetailItem> getDetailItems(long uid, long startTime, int count, int cardType, int sportType) {
        List<CopySportGps> copySportGpsList = ModuleRouteSportService.getInstance().getSportHistory(uid, startTime, count, cardType, sportType);
        List<SportDetailItem> detailItems = new ArrayList<>();
        if (copySportGpsList != null && copySportGpsList.size() > 0) {
            KLog.d("no2855--> 获取到的新数据: " + copySportGpsList.size());
            for (CopySportGps copySportGps : copySportGpsList) {
                if (copySportGps.getStart_time() > 0) {
                    SportDetailItem detailItem = new SportDetailItem();
                    detailItem.setCalorie(copySportGps.getCalorie());
                    detailItem.setCanClick(true);
                    if (copySportGps.getSport_type() == 131 && cardType == 5) {
                        detailItem.setCanClick(false);
                    }
                    detailItem.setDataFrom(copySportGps.getData_from());
                    if (cardType == 3) {
                        detailItem.setDistanceKm((float) ((int) copySportGps.getDistance()));
                    } else {
                        detailItem.setDistanceKm(Util.doubleToFloat(2, (double) (copySportGps.getDistance() / 1000.0f)));
                    }
                    detailItem.setDistanceMile(Util.doubleToFloat(2, Util.meterToMile((double) (copySportGps.getDistance() / 1000.0f))));
                    detailItem.setDoneTimes(copySportGps.getDone_times());
                    detailItem.setDuration(copySportGps.getDuration());
                    detailItem.setDurationStr(secondToHms(copySportGps.getDuration()));
                    detailItem.setEndTime(copySportGps.getEnd_time());
                    detailItem.setSourceType(copySportGps.getSource_type());
                    detailItem.setSportType(copySportGps.getSport_type());
                    detailItem.setStartTime(copySportGps.getStart_time());
                    detailItem.setStep(copySportGps.getStep());
                    detailItem.setTimeTitle(new DateUtil(copySportGps.getStart_time(), true).getMMdd_HHmmDate());
                    detailItem.setDataType(getTypeImg(copySportGps.getData_from()));
                    detailItem.setUpload(copySportGps.getUpload());
                    if (cardType == 0) {
                        detailItem.setSportImg(0);
                    } else {
                        int choseType = copySportGps.getSport_type();
                        if (cardType == 3) {
                            if (copySportGps.getDistance() < 10.0f) {
                                choseType = 2098;
                            } else {
                                choseType = 2097;
                            }
                            detailItem.setSportType(choseType);
                        }
                        detailItem.setSportImg(getSportTypeImg(choseType));
                    }
                    detailItems.add(detailItem);
                }
            }
        }
        return detailItems;
    }

    private int getSportTypeImg(int sportType) {
        switch (sportType) {
            case 2:
                return R.mipmap.sport_sit_ups3x;
            case 3:
                return R.mipmap.sport_push_ups3x;
            case 4:
                return R.mipmap.sport_rope_skipping3x;
            case 5:
                return R.mipmap.sport_climbing3x;
            case 6:
                return R.mipmap.sport_pull_ups3x;
            case 7:
                return R.mipmap.sport_run3x;
            case 8:
                return R.mipmap.sport_aerobics3x;
            case 128:
                return R.mipmap.sport_badminton3x;
            case 129:
                return R.mipmap.sport_basketball3x;
            case Opcodes.INT_TO_FLOAT /*130*/:
                return R.mipmap.sport_football3x;
            case Opcodes.INT_TO_DOUBLE /*131*/:
                return R.mipmap.sport_swimming3x;
            case Opcodes.LONG_TO_INT /*132*/:
                return R.mipmap.sport_volleyball3x;
            case Opcodes.LONG_TO_FLOAT /*133*/:
                return R.mipmap.sport_table_tennis3x;
            case Opcodes.LONG_TO_DOUBLE /*134*/:
                return R.mipmap.sport_bowling3x;
            case 135:
                return R.mipmap.sport_tennis3x;
            case Opcodes.FLOAT_TO_LONG /*136*/:
                return R.mipmap.sport_bike3x;
            case Opcodes.FLOAT_TO_DOUBLE /*137*/:
                return R.mipmap.sport_ski3x;
            case Opcodes.DOUBLE_TO_INT /*138*/:
                return R.mipmap.sport_skate3x;
            case Opcodes.DOUBLE_TO_LONG /*139*/:
                return R.mipmap.sport_rock_climbing3x;
            case Opcodes.DOUBLE_TO_FLOAT /*140*/:
                return R.mipmap.sport_fitness3x;
            case Opcodes.INT_TO_BYTE /*141*/:
                return R.mipmap.sport_dance3x;
            case Opcodes.INT_TO_CHAR /*142*/:
                return R.mipmap.sport_plank3x;
            case Opcodes.INT_TO_SHORT /*143*/:
                return R.mipmap.sport_sports3x;
            case Opcodes.ADD_INT /*144*/:
                return R.mipmap.sport_yoga3x;
            case Opcodes.SUB_INT /*145*/:
                return R.mipmap.sport_shuttlecock3x;
            case Opcodes.MUL_INT /*146*/:
                return R.mipmap.sport_ball_games3x;
            case Opcodes.DIV_INT /*147*/:
                return R.mipmap.sport_walk3x;
            case Opcodes.REM_INT /*148*/:
                return R.mipmap.sport_golf3x;
            case Opcodes.AND_INT /*149*/:
                return R.mipmap.sport_canoeing3x;
            case 150:
                return R.mipmap.sport_sports3x;
            case 2097:
                return R.mipmap.sport_swim_laps;
            case 2098:
                return R.mipmap.sport_swimming3x;
            case 4096:
                return R.mipmap.sport_treadmill3x;
            case 4097:
                return R.mipmap.sport_spinning3x;
            default:
                return R.mipmap.sport_others3x;
        }
    }

    private String secondToHms(int second) {
        int hour = second / 3600;
        int minute = (second / 60) % 60;
        return String.format("%02d", new Object[]{Integer.valueOf(hour)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(minute)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(second % 60)});
    }

    private void getSportGpsNet(long uid, long startTime, int count, int cardType, int sportType) {
        final long j = uid;
        final long j2 = startTime;
        final int i = count;
        final int i2 = cardType;
        final int i3 = sportType;
        long j3 = uid;
        int i4 = sportType;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object ob) {
                int num = SportDetailPresenter.this.getNetNum(ob);
                KLog.e("no2855 00--> 加载成功0000-- " + num);
                SportDetailPresenter.this.detailView.loadPageServiceDataSuccess(num, SportDetailPresenter.this.getDetailItems(j, j2, i, i2, i3));
            }

            public void onFail(Throwable e) {
                KLog.e("no2855--> 获取gps网络数据 失败？？？");
                SportDetailPresenter.this.detailView.loadPageDataFail();
            }
        }).downloadPageSportGps(j3, i4, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }

    /* access modifiers changed from: private */
    public int getNetNum(Object ob) {
        try {
            return ((Integer) ob).intValue();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    private void getSportBallNet(long uid, long startTime, int count, int cardType, int sportType) {
        final long j = uid;
        final long j2 = startTime;
        final int i = count;
        final int i2 = cardType;
        final int i3 = sportType;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object ob) {
                SportDetailPresenter.this.detailView.loadPageServiceDataSuccess(SportDetailPresenter.this.getNetNum(ob), SportDetailPresenter.this.getDetailItems(j, j2, i, i2, i3));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadPageDataFail();
            }
        }).downloadPageSportBall(uid, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }

    private void getSportOtherNet(long uid, long startTime, int count, int cardType, int sportType) {
        final long j = uid;
        final long j2 = startTime;
        final int i = count;
        final int i2 = cardType;
        final int i3 = sportType;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object ob) {
                int num = SportDetailPresenter.this.getNetNum(ob);
                KLog.e("no2855 00--> 加载成功");
                SportDetailPresenter.this.detailView.loadPageServiceDataSuccess(num, SportDetailPresenter.this.getDetailItems(j, j2, i, i2, i3));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadPageDataFail();
            }
        }).downloadPageSportOther(uid, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }

    private void getSportSwimNet(long uid, long startTime, int count, int cardType, int sportType) {
        final long j = uid;
        final long j2 = startTime;
        final int i = count;
        final int i2 = cardType;
        final int i3 = sportType;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object ob) {
                int num = SportDetailPresenter.this.getNetNum(ob);
                KLog.e("no2855 00--> 加载成功");
                SportDetailPresenter.this.detailView.loadPageServiceDataSuccess(num, SportDetailPresenter.this.getDetailItems(j, j2, i, i2, i3));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadPageDataFail();
            }
        }).downloadPageSportSwim(uid, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }

    private int getTypeImg(String dataFrom) {
        if (dataFrom == null) {
            return 0;
        }
        String dataFrom2 = dataFrom.toUpperCase(Locale.US);
        if (dataFrom2.contains("ANDROID") || dataFrom2.contains("IPHONE")) {
            if (dataFrom2.contains("VOICE")) {
                return 3;
            }
            return 0;
        } else if (ModuleRouteSportService.getInstance().isP1(dataFrom)) {
            return 1;
        } else {
            if (dataFrom2.contains("VOICE")) {
                return 2;
            }
            return 4;
        }
    }

    private int isR1OrGps(String r1_url, String gps_url) {
        if (!TextUtils.isEmpty(r1_url) && !TextUtils.isEmpty(gps_url)) {
            return 3;
        }
        if (!TextUtils.isEmpty(r1_url)) {
            return 2;
        }
        if (!TextUtils.isEmpty(gps_url)) {
        }
        return 0;
    }

    private void getGpsAllFromNet(final long uid, final int sportType) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SportDetailPresenter.this.detailView.loadServiceAllSportSuccess(SportDetailPresenter.this.getGpsAllData(uid, sportType));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadAllSportFail();
            }
        }).downloadAllSportGps(uid);
    }

    private void getBallAllFromNet(final long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SportDetailPresenter.this.detailView.loadServiceAllSportSuccess(SportDetailPresenter.this.getBallAllData(uid));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadAllSportFail();
            }
        }).downloadAllSportBall(uid);
    }

    private void getOtherAllFromNet(final long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SportDetailPresenter.this.detailView.loadServiceAllSportSuccess(SportDetailPresenter.this.getOtherAllData(uid));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadAllSportFail();
            }
        }).downloadAllSportOther(uid);
    }

    private void getSwimAllFromNet(final long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SportDetailPresenter.this.detailView.loadServiceAllSportSuccess(SportDetailPresenter.this.getSwimAllData(uid));
            }

            public void onFail(Throwable e) {
                SportDetailPresenter.this.detailView.loadAllSportFail();
            }
        }).downloadAllSportSwim(uid);
    }

    /* access modifiers changed from: private */
    public CopySportAll getGpsAllData(long uid, int sportType) {
        TB_sport_all_gps allGps = (TB_sport_all_gps) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_gps.class);
        CopySportAll copySportAll = new CopySportAll();
        if (allGps == null) {
            allGps = new TB_sport_all_gps();
        }
        copySportAll.setUid(allGps.getUid());
        SportTotalData sportTotalData = ModuleRouteSportService.getInstance().getSportTotalData(uid);
        if (sportType == 0) {
            copySportAll.setDistance(allGps.getRun_distance() + sportTotalData.getRunDistance());
            copySportAll.setDuration(allGps.getRun_duration() + sportTotalData.getRunDuration());
            copySportAll.setDoneTimes(allGps.getRun_times() + sportTotalData.getRunTimes());
        } else if (sportType == 1) {
            copySportAll.setDistance(allGps.getCycle_distance() + sportTotalData.getBikeDistance());
            copySportAll.setDuration(allGps.getCycle_duration() + sportTotalData.getBikeDuration());
            copySportAll.setDoneTimes(allGps.getCycle_times() + sportTotalData.getBikeTimes());
        } else if (sportType == 3) {
            copySportAll.setDistance(allGps.getClimb_distance() + sportTotalData.getClimbDistance());
            copySportAll.setDuration(allGps.getClimb_duration() + sportTotalData.getClimbDuration());
            copySportAll.setDoneTimes(allGps.getClimb_times() + sportTotalData.getClimbTimes());
        } else if (sportType == 2) {
            copySportAll.setDistance(allGps.getWalk_distance() + sportTotalData.getWalkDistance());
            copySportAll.setDuration(allGps.getWalk_duration() + sportTotalData.getWalkTimes());
            copySportAll.setDoneTimes(allGps.getWalk_times() + sportTotalData.getWalkTimes());
        }
        return copySportAll;
    }

    /* access modifiers changed from: private */
    public CopySportAll getBallAllData(long uid) {
        TB_sport_all_ball allGps = (TB_sport_all_ball) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_ball.class);
        CopySportAll copySportAll = new CopySportAll();
        if (allGps == null) {
            allGps = new TB_sport_all_ball();
        }
        SportTotalData sportTotalData = ModuleRouteSportService.getInstance().getSportTotalData(uid);
        copySportAll.setDoneTimes(allGps.getTimes() + sportTotalData.getBallTimes());
        copySportAll.setDuration(allGps.getDuration() + sportTotalData.getBallDuiation());
        copySportAll.setCalorie(allGps.getCalorie() + sportTotalData.getBallCaliores());
        copySportAll.setUid(allGps.getUid());
        return copySportAll;
    }

    /* access modifiers changed from: private */
    public CopySportAll getSwimAllData(long uid) {
        TB_sport_all_swim allGps = (TB_sport_all_swim) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_swim.class);
        CopySportAll copySportAll = new CopySportAll();
        if (allGps == null) {
            allGps = new TB_sport_all_swim();
        }
        SportTotalData sportTotalData = ModuleRouteSportService.getInstance().getSportTotalData(uid);
        copySportAll.setDoneTimes(allGps.getTimes() + sportTotalData.getSwimTimes());
        copySportAll.setDuration(allGps.getDuration() + sportTotalData.getSwimDuiation());
        copySportAll.setCalorie(allGps.getCalorie() + sportTotalData.getSwimCaliores());
        copySportAll.setUid(allGps.getUid());
        return copySportAll;
    }

    /* access modifiers changed from: private */
    public CopySportAll getOtherAllData(long uid) {
        TB_sport_all_other allGps = (TB_sport_all_other) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_other.class);
        CopySportAll copySportAll = new CopySportAll();
        if (allGps == null) {
            allGps = new TB_sport_all_other();
        }
        SportTotalData sportTotalData = ModuleRouteSportService.getInstance().getSportTotalData(uid);
        copySportAll.setDoneTimes(allGps.getTimes() + sportTotalData.getOtherTimes());
        copySportAll.setDuration(allGps.getDuration() + sportTotalData.getOtherDuiation());
        copySportAll.setCalorie(allGps.getCalorie() + sportTotalData.getOtherCaliores());
        copySportAll.setUid(allGps.getUid());
        return copySportAll;
    }

    public void uploadNoUpSegment(long uid) {
        ModuleRouteSportService.getInstance().uploadNoUpGps(uid);
    }

    public void updataAllSportTb(long uid, int cardType, int sportType, SportItemData sportItemData) {
        KLog.d("no28855---> +bug来了哦 - " + sportType);
        if (cardType == 0) {
            TB_sport_all_gps allGps = (TB_sport_all_gps) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_gps.class);
            if (allGps != null) {
                if (sportType == 0) {
                    allGps.setRun_distance(sportItemData.getDistance() * 1000.0f);
                    allGps.setRun_times(sportItemData.getCount());
                    allGps.setRun_duration(sportItemData.getTime());
                } else if (sportType == 1) {
                    allGps.setCycle_distance(sportItemData.getDistance() * 1000.0f);
                    allGps.setCycle_times(sportItemData.getCount());
                    allGps.setCycle_duration(sportItemData.getTime());
                } else if (sportType == 2) {
                    allGps.setWalk_distance(sportItemData.getDistance() * 1000.0f);
                    allGps.setWalk_times(sportItemData.getCount());
                    allGps.setWalk_duration(sportItemData.getTime());
                } else if (sportType == 3) {
                    allGps.setClimb_distance(sportItemData.getDistance() * 1000.0f);
                    allGps.setClimb_times(sportItemData.getCount());
                    allGps.setClimb_duration(sportItemData.getTime());
                }
                allGps.updateAll("uid=?", uid + "");
            }
        } else if (cardType == 1) {
            TB_sport_all_ball allGps2 = (TB_sport_all_ball) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_ball.class);
            if (allGps2 != null) {
                allGps2.setTimes(sportItemData.getCount());
                allGps2.setDuration(sportItemData.getTime());
                allGps2.setCalorie(sportItemData.getCalorie());
                allGps2.updateAll("uid=?", uid + "");
            }
        } else if (cardType == 3) {
            TB_sport_all_swim allGps3 = (TB_sport_all_swim) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_swim.class);
            if (allGps3 != null) {
                allGps3.setTimes(sportItemData.getCount());
                allGps3.setDuration(sportItemData.getTime());
                allGps3.setCalorie(sportItemData.getCalorie());
                allGps3.updateAll("uid=?", uid + "");
            }
        } else if (cardType == 2) {
            TB_sport_all_other allGps4 = (TB_sport_all_other) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_other.class);
            if (allGps4 != null) {
                allGps4.setTimes(sportItemData.getCount());
                allGps4.setDuration(sportItemData.getTime());
                allGps4.setCalorie(sportItemData.getCalorie());
                allGps4.updateAll("uid=?", uid + "");
            }
        }
    }
}
