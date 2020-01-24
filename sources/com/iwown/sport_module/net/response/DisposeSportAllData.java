package com.iwown.sport_module.net.response;

import com.iwown.sport_module.gps.data.TB_sport_all_ball;
import com.iwown.sport_module.gps.data.TB_sport_all_gps;
import com.iwown.sport_module.gps.data.TB_sport_all_other;
import com.iwown.sport_module.gps.data.TB_sport_all_swim;
import com.iwown.sport_module.gps.data.TB_sport_correct_gps;
import com.socks.library.KLog;
import org.litepal.crud.DataSupport;

public class DisposeSportAllData {
    public void saveTBAllGps(AllSportGpsCode retCode) {
        if (retCode != null && retCode.getData() != null) {
            long uid = retCode.getData().getUid();
            TB_sport_correct_gps correctGps = (TB_sport_correct_gps) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_correct_gps.class);
            boolean isSave = true;
            if (correctGps != null) {
                boolean runSame = correctGps.getRun_duration() == retCode.getData().getRunDuration() && correctGps.getRun_times() == retCode.getData().getRunTimes();
                boolean bikeSame = correctGps.getCycle_duration() == retCode.getData().getCycleDuration() && correctGps.getCycle_times() == retCode.getData().getCycleTimes();
                boolean walkSame = correctGps.getWalk_duration() == retCode.getData().getWalkDuration() && correctGps.getWalk_times() == retCode.getData().getWalkTimes();
                boolean climbSame = correctGps.getClimb_duration() == retCode.getData().getClimbDuration() && correctGps.getClimb_times() == retCode.getData().getClimbTimes();
                if (runSame && bikeSame && walkSame && climbSame) {
                    isSave = false;
                }
            }
            TB_sport_all_gps allGpss = (TB_sport_all_gps) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_gps.class);
            if (correctGps != null && allGpss == null) {
                isSave = true;
            }
            if (isSave) {
                saveCorrectGps(uid, retCode);
                KLog.e("no28855--->去你大爷");
                TB_sport_all_gps allGps = new TB_sport_all_gps();
                allGps.setUid(uid);
                allGps.setRun_distance(retCode.getData().getRunDistance());
                allGps.setRun_duration(retCode.getData().getRunDuration());
                allGps.setRun_times(retCode.getData().getRunTimes());
                allGps.setCycle_distance(retCode.getData().getCycleDistance());
                allGps.setCycle_duration(retCode.getData().getCycleDuration());
                allGps.setCycle_times(retCode.getData().getCycleTimes());
                allGps.setWalk_distance(retCode.getData().getWalkDistance());
                allGps.setWalk_duration(retCode.getData().getWalkDuration());
                allGps.setWalk_times(retCode.getData().getWalkTimes());
                allGps.setClimb_distance(retCode.getData().getClimbDistance());
                allGps.setClimb_duration(retCode.getData().getClimbDuration());
                allGps.setClimb_times(retCode.getData().getClimbTimes());
                allGps.saveOrUpdate("uid=?", uid + "");
            }
        }
    }

    private void saveCorrectGps(long uid, AllSportGpsCode retCode) {
        TB_sport_correct_gps allGps = new TB_sport_correct_gps();
        allGps.setUid(uid);
        allGps.setRun_distance(retCode.getData().getRunDistance());
        allGps.setRun_duration(retCode.getData().getRunDuration());
        allGps.setRun_times(retCode.getData().getRunTimes());
        allGps.setCycle_distance(retCode.getData().getCycleDistance());
        allGps.setCycle_duration(retCode.getData().getCycleDuration());
        allGps.setCycle_times(retCode.getData().getCycleTimes());
        allGps.setWalk_distance(retCode.getData().getWalkDistance());
        allGps.setWalk_duration(retCode.getData().getWalkDuration());
        allGps.setWalk_times(retCode.getData().getWalkTimes());
        allGps.setClimb_distance(retCode.getData().getClimbDistance());
        allGps.setClimb_duration(retCode.getData().getClimbDuration());
        allGps.setClimb_times(retCode.getData().getClimbTimes());
        allGps.saveOrUpdate("uid=?", uid + "");
    }

    public void saveTBAllBall(AllSportBallCode retCode) {
        boolean isSame;
        if (retCode != null && retCode.getData() != null) {
            long uid = retCode.getData().getUid();
            TB_sport_all_ball allBall = (TB_sport_all_ball) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_ball.class);
            boolean isSave = true;
            if (allBall == null) {
                allBall = new TB_sport_all_ball();
            } else {
                if (allBall.getDuration_net() == retCode.getData().getDuration() && allBall.getTimes_net() == retCode.getData().getTimes()) {
                    isSame = true;
                } else {
                    isSame = false;
                }
                if (isSame) {
                    isSave = false;
                }
            }
            if (isSave) {
                allBall.setCalorie(retCode.getData().getCalorie());
                allBall.setDuration(retCode.getData().getDuration());
                allBall.setTimes(retCode.getData().getTimes());
                allBall.setCalorie_net(retCode.getData().getCalorie());
                allBall.setDuration_net(retCode.getData().getDuration());
                allBall.setTimes_net(retCode.getData().getTimes());
                allBall.setUid(retCode.getData().getUid());
                allBall.saveOrUpdate("uid=?", uid + "");
            }
        }
    }

    public void saveTBAllOther(AllSportOtherCode retCode) {
        boolean isSame;
        if (retCode != null && retCode.getData() != null) {
            long uid = retCode.getData().getUid();
            TB_sport_all_other allOther = (TB_sport_all_other) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_other.class);
            boolean isSave = true;
            if (allOther == null) {
                allOther = new TB_sport_all_other();
            } else {
                if (allOther.getDuration_net() == retCode.getData().getDuration() && allOther.getTimes_net() == retCode.getData().getTimes()) {
                    isSame = true;
                } else {
                    isSame = false;
                }
                if (isSame) {
                    isSave = false;
                }
            }
            if (isSave) {
                allOther.setCalorie(retCode.getData().getCalorie());
                allOther.setDuration(retCode.getData().getDuration());
                allOther.setTimes(retCode.getData().getTimes());
                allOther.setCalorie_net(retCode.getData().getCalorie());
                allOther.setDuration_net(retCode.getData().getDuration());
                allOther.setTimes_net(retCode.getData().getTimes());
                allOther.setUid(retCode.getData().getUid());
                allOther.saveOrUpdate("uid=?", uid + "");
            }
        }
    }

    public void saveTBAllSwim(AllSportSwimCode retCode) {
        boolean isSame;
        if (retCode != null && retCode.getData() != null) {
            long uid = retCode.getData().getUid();
            TB_sport_all_swim allSwim = (TB_sport_all_swim) DataSupport.where("uid=?", uid + "").findFirst(TB_sport_all_swim.class);
            boolean isSave = true;
            if (allSwim == null) {
                allSwim = new TB_sport_all_swim();
            } else {
                if (allSwim.getDuration_net() == retCode.getData().getDuration() && allSwim.getTimes_net() == retCode.getData().getTimes()) {
                    isSame = true;
                } else {
                    isSame = false;
                }
                if (isSame) {
                    isSave = false;
                }
            }
            if (isSave) {
                allSwim.setDuration(retCode.getData().getDuration());
                allSwim.setTimes(retCode.getData().getTimes());
                allSwim.setDuration_net(retCode.getData().getDuration());
                allSwim.setTimes_net(retCode.getData().getTimes());
                allSwim.setCalorie(retCode.getData().getCalorie());
                allSwim.setCalorie_net(retCode.getData().getCalorie());
                allSwim.setUid(retCode.getData().getUid());
                allSwim.saveOrUpdate("uid=?", uid + "");
            }
        }
    }
}
