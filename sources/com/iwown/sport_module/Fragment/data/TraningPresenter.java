package com.iwown.sport_module.Fragment.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.eventbus.GpsTotalEvent;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.SportTotalData;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.Fragment.data.mvp.HomeTraningContract.HTPresenter1;
import com.iwown.sport_module.Fragment.data.mvp.HomeTraningContract.HTView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.data.TB_sport_all_ball;
import com.iwown.sport_module.gps.data.TB_sport_all_gps;
import com.iwown.sport_module.gps.data.TB_sport_all_other;
import com.iwown.sport_module.gps.data.TB_sport_all_swim;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.pojo.data.BaseTraningItem;
import com.iwown.sport_module.pojo.data.HistoryRideTraningItem;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class TraningPresenter implements HTPresenter1 {
    List<MultiItemEntity> historyRunWalkTraningItems = new ArrayList();
    private final HTView htView;
    private int mType = 0;

    public TraningPresenter(HTView htView2) {
        this.htView = htView2;
    }

    public void loadData(DateUtil dateUtil, boolean isNet) {
        KLog.e(" ----  loadData");
        if (isNet) {
            long uid = UserConfig.getInstance().getNewUID();
            getGpsAllFromNet(uid);
            getBallAllFromNet(uid);
            getOtherFromNet(uid);
            getSwimFromNet(uid);
            return;
        }
        updateDatas();
    }

    public void reshCard(GpsTotalEvent event) {
        if (event.getCount() >= 20) {
            updateDatas();
        }
    }

    private void reshOneCard(GpsTotalEvent event) {
        if (event.getCardType() != 0 && event.getCardType() == 0) {
        }
    }

    /* access modifiers changed from: private */
    public void reshHome() {
        this.mType++;
        if (this.mType >= 3) {
            updateDatas();
            this.mType = 0;
        }
    }

    private void getGpsAllFromNet(long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                TraningPresenter.this.reshHome();
            }

            public void onFail(Throwable e) {
                TraningPresenter.this.reshHome();
            }
        }).downloadAllSportGps(uid);
    }

    private void getBallAllFromNet(long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                TraningPresenter.this.reshHome();
            }

            public void onFail(Throwable e) {
                TraningPresenter.this.reshHome();
            }
        }).downloadAllSportBall(uid);
    }

    private void getOtherFromNet(long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                TraningPresenter.this.reshHome();
            }

            public void onFail(Throwable e) {
                TraningPresenter.this.reshHome();
            }
        }).downloadAllSportOther(uid);
    }

    private void getSwimFromNet(long uid) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                TraningPresenter.this.reshHome();
            }

            public void onFail(Throwable e) {
                TraningPresenter.this.reshHome();
            }
        }).downloadAllSportSwim(uid);
    }

    public List<MultiItemEntity> getHistoryItems(boolean isCalculateData) {
        boolean isMetric;
        KLog.e("no2855-->card");
        if (UserConfig.getInstance().isMertric()) {
            isMetric = true;
        } else {
            isMetric = false;
        }
        HistoryRideTraningItem historyRun = null;
        HistoryRideTraningItem historyWalk = null;
        HistoryRideTraningItem historyBike = null;
        HistoryRideTraningItem historyMountain = null;
        HistoryRideTraningItem historyBall = null;
        HistoryRideTraningItem historySwim = null;
        HistoryRideTraningItem historyOther = null;
        if (0 == 0) {
            historyRun = new HistoryRideTraningItem(BaseTraningItem.SPORT_TYPE_RUN_GPS, R.mipmap.run, R.color.sport_module_F34736);
            historyRun.setBgImgResources(R.mipmap.running);
            historyRun.setTitleMsgResources(R.string.sport_module_running);
            historyRun.setUnitResources(isMetric ? R.string.sport_module_dis_km : R.string.sport_module_dis_mile);
            historyRun.setGoImgResources(R.mipmap.go_red);
            historyRun.setShowGoImg(true);
            historyRun.setSport_type(0);
            historyRun.setShowType(0);
        }
        if (0 == 0) {
            historyWalk = new HistoryRideTraningItem(BaseTraningItem.SPORT_TYPE_SPEED_WALK_GPS, R.drawable.sport_speed_walk, R.color.sport_module_29A905);
            historyWalk.setBgImgResources(R.drawable.walking_bg);
            historyWalk.setTitleMsgResources(R.string.sport_module_sport_plan_speed_walking);
            historyWalk.setUnitResources(isMetric ? R.string.sport_module_dis_km : R.string.sport_module_dis_mile);
            historyWalk.setGoImgResources(R.mipmap.go_green);
            historyWalk.setShowGoImg(true);
            historyWalk.setSport_type(2);
            historyWalk.setShowType(0);
        }
        if (0 == 0) {
            historyBike = new HistoryRideTraningItem(BaseTraningItem.SPORT_TYPE_RIDE_GPS, R.drawable.sport_ride_icon, R.color.sport_module_ride_base_color);
            historyBike.setBgImgResources(R.drawable.riding_bg);
            historyBike.setTitleMsgResources(R.string.sport_module_riding);
            historyBike.setUnitResources(isMetric ? R.string.sport_module_dis_km : R.string.sport_module_dis_mile);
            historyBike.setGoImgResources(R.mipmap.go_blue);
            historyBike.setShowGoImg(true);
            historyBike.setSport_type(1);
            historyBike.setShowType(0);
        }
        if (0 == 0) {
            historyMountain = new HistoryRideTraningItem(5, R.mipmap.mountain_on, R.color.sport_module_186DDB);
            historyMountain.setBgImgResources(R.mipmap.mountaineering_bg);
            historyMountain.setTitleMsgResources(R.string.sport_module_mountaineering);
            historyMountain.setUnitResources(isMetric ? R.string.sport_module_dis_km : R.string.sport_module_dis_mile);
            historyMountain.setGoImgResources(R.mipmap.go_blue);
            historyMountain.setShowGoImg(false);
            historyMountain.setSport_type(3);
            historyMountain.setShowType(0);
        }
        if (0 == 0) {
            historyBall = new HistoryRideTraningItem(4, R.mipmap.ball_games, R.color.sport_module_29A905);
            historyBall.setBgImgResources(R.mipmap.ball_games_bg);
            historyBall.setTitleMsgResources(R.string.sport_module_balling);
            historyBall.setUnitResources(R.string.sport_module_times_min);
            historyBall.setGoImgResources(R.mipmap.go_blue);
            historyBall.setShowGoImg(false);
            historyBall.setSport_type(4);
            historyBall.setShowType(1);
        }
        if (0 == 0) {
            historySwim = new HistoryRideTraningItem(6, R.mipmap.swimming_ti, R.color.sport_module_ride_base_color);
            historySwim.setBgImgResources(R.mipmap.swimming_bg);
            historySwim.setTitleMsgResources(R.string.sport_swimming_title);
            historySwim.setUnitResources(R.string.sport_module_times_min);
            historySwim.setGoImgResources(R.mipmap.go_blue);
            historySwim.setShowGoImg(false);
            historySwim.setSport_type(6);
            historySwim.setShowType(1);
        }
        if (0 == 0) {
            historyOther = new HistoryRideTraningItem(BaseTraningItem.SPORT_TYPE_R1_OTHER, R.drawable.r1_other_icon, R.color.sport_module_ride_base_color);
            historyOther.setBgImgResources(R.drawable.other_sports_bg);
            historyOther.setTitleMsgResources(R.string.sport_module_sport_r1_other);
            historyOther.setUnitResources(R.string.sport_module_times_min);
            historyOther.setGoImgResources(R.mipmap.go_blue);
            historyOther.setShowGoImg(false);
            historyOther.setSport_type(5);
            historyOther.setShowType(1);
        }
        this.historyRunWalkTraningItems.clear();
        this.historyRunWalkTraningItems.add(historyRun);
        this.historyRunWalkTraningItems.add(historyBike);
        this.historyRunWalkTraningItems.add(historyWalk);
        this.historyRunWalkTraningItems.add(historyBall);
        this.historyRunWalkTraningItems.add(historyMountain);
        this.historyRunWalkTraningItems.add(historySwim);
        this.historyRunWalkTraningItems.add(historyOther);
        if (isCalculateData) {
            long uid = UserConfig.getInstance().getNewUID();
            SportTotalData sportTotalData = ModuleRouteSportService.getInstance().getSportTotalData(uid);
            TB_sport_all_gps sport_all_gps = (TB_sport_all_gps) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_gps.class);
            TB_sport_all_ball sport_all_ball = (TB_sport_all_ball) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_ball.class);
            TB_sport_all_other sport_all_other = (TB_sport_all_other) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_other.class);
            TB_sport_all_swim sport_all_swim = (TB_sport_all_swim) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_swim.class);
            if (sport_all_gps == null) {
                sport_all_gps = new TB_sport_all_gps();
            }
            if (sport_all_ball == null) {
                sport_all_ball = new TB_sport_all_ball();
            }
            if (sport_all_other == null) {
                sport_all_other = new TB_sport_all_other();
            }
            if (sport_all_swim == null) {
                sport_all_swim = new TB_sport_all_swim();
            }
            historyRun.setCount(sport_all_gps.getRun_times() + sportTotalData.getRunTimes());
            historyRun.setDistance((sport_all_gps.getRun_distance() + sportTotalData.getRunDistance()) / 1000.0f);
            historyRun.setTime((sport_all_gps.getRun_duration() + sportTotalData.getRunDuration()) / 60);
            historyBike.setCount(sport_all_gps.getCycle_times() + sportTotalData.getBikeTimes());
            historyBike.setDistance((sport_all_gps.getCycle_distance() + sportTotalData.getBikeDistance()) / 1000.0f);
            historyBike.setTime((sport_all_gps.getCycle_duration() + sportTotalData.getBikeDuration()) / 60);
            historyWalk.setCount(sport_all_gps.getWalk_times() + sportTotalData.getWalkTimes());
            historyWalk.setDistance((sport_all_gps.getWalk_distance() + sportTotalData.getWalkDistance()) / 1000.0f);
            historyWalk.setTime((sport_all_gps.getWalk_duration() + sportTotalData.getWalkDuration()) / 60);
            historyMountain.setCount(sport_all_gps.getClimb_times() + sportTotalData.getClimbTimes());
            historyMountain.setDistance((sport_all_gps.getClimb_distance() + sportTotalData.getClimbDistance()) / 1000.0f);
            historyMountain.setTime((sport_all_gps.getClimb_duration() + sportTotalData.getClimbDuration()) / 60);
            historyBall.setCount(sport_all_ball.getTimes() + sportTotalData.getBallTimes());
            historyBall.setDistance(0.0f);
            historyBall.setTime((sport_all_ball.getDuration() + sportTotalData.getBallDuiation()) / 60);
            historyBall.setCal(sport_all_ball.getCalorie() + sportTotalData.getBallCaliores());
            historySwim.setCount(sport_all_swim.getTimes() + sportTotalData.getSwimTimes());
            historySwim.setTime((sport_all_swim.getDuration() + sportTotalData.getSwimDuiation()) / 60);
            historySwim.setCal(sport_all_swim.getCalorie() + sportTotalData.getSwimCaliores());
            historyOther.setCount(sport_all_other.getTimes() + sportTotalData.getOtherTimes());
            historyOther.setDistance(0.0f);
            historyOther.setTime((sport_all_other.getDuration() + sportTotalData.getOtherDuiation()) / 60);
            historyOther.setCal(sport_all_other.getCalorie() + sportTotalData.getOtherCaliores());
        }
        KLog.e("----- " + this.historyRunWalkTraningItems.size());
        return this.historyRunWalkTraningItems;
    }

    private void updateDatas() {
        this.htView.showTrandingDatas(getHistoryItems(true));
    }

    public void start(boolean b) {
    }

    public void onDestroy() {
    }

    public void downloadGpsSegment(long uid) {
        long times = System.currentTimeMillis() / 1000;
        for (int i = 0; i < 4; i++) {
            getSportGpsNet(uid, times, 20, i);
        }
    }

    private void getSportGpsNet(long uid, long startTime, int count, int sportType) {
        long j = uid;
        int i = sportType;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.e("no2855 00--> 加载成功0000");
            }

            public void onFail(Throwable e) {
                KLog.e("no2855--> 获取gps主页网络数据 失败？？？");
            }
        }).downloadPageSportGps(j, i, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }

    private void getSportBallNet(long uid, long startTime, int count) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
            }

            public void onFail(Throwable e) {
            }
        }).downloadPageSportBall(uid, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }

    private void getSportOtherNet(long uid, long startTime, int count) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.e("no2855 00--> 加载成功");
            }

            public void onFail(Throwable e) {
            }
        }).downloadPageSportOther(uid, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), count);
    }
}
