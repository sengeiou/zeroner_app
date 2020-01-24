package com.iwown.device_module.common.Bluetooth.receiver.mtk.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.sport_data.gps.GpsUpData;
import com.iwown.data_link.sport_data.gps.HrUpData;
import com.iwown.data_link.sport_data.gps.HrUpData.Hr51;
import com.iwown.data_link.sport_data.gps.LongitudeAndLatitude;
import com.iwown.data_link.sport_data.gps.SwimUpData;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.sql.TB_62_data;
import com.iwown.device_module.common.sql.TB_blue_gps;
import com.iwown.device_module.common.sql.TB_fatigue_history;
import com.iwown.device_module.common.sql.TB_iv_temporary;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.TB_sport_ball;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.iwown.device_module.common.sql.TB_sport_other;
import com.iwown.device_module.common.sql.TB_sport_swim;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.device_module.common.sql.TB_v3_walk;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.device_module.common.sql.heart.TB_swimrate_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class DataUtil {
    public static void saveTBWalk(int year, int month, int day, String strCalorie, String strDistance, String strSteps) {
        try {
            float calorie = Float.parseFloat(strCalorie);
            float distance = Float.parseFloat(strDistance);
            int steps = Integer.parseInt(strSteps);
            long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            DateUtil dateUtil = new DateUtil(year, month, day, 8, 0, 0);
            String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
            TB_v3_walk walk = new TB_v3_walk();
            walk.setUid(uid);
            walk.setRecord_date(dateUtil.getUnixTimestamp());
            walk.setData_from(dataFrom);
            walk.setStep(steps);
            walk.setDate(dateUtil.getSyyyyMMddDate());
            walk.setCalorie(calorie);
            walk.setDistance(distance);
            walk.set_uploaded(0);
            walk.saveOrUpdate("uid=? and date=? and data_from=?", uid + "", dateUtil.getSyyyyMMddDate() + "", dataFrom + "");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static void saveTb53Heart(long uid, int year, int month, int day, int hour, List<Integer> data, String dataFrom) {
        if (data.size() < 60) {
            for (int i = data.size(); i < 60; i++) {
                data.add(Integer.valueOf(0));
            }
        } else if (data.size() > 60) {
            for (int i2 = data.size() - 1; i2 >= 59; i2--) {
                data.remove(i2);
            }
        }
        TB_v3_heartRate_data_hours dataHour = new TB_v3_heartRate_data_hours();
        dataHour.setUid(uid);
        dataHour.setYear(year);
        dataHour.setMonth(month);
        dataHour.setDay(day);
        dataHour.setHours(hour);
        dataHour.set_uploaded(0);
        dataHour.setRecord_date(Util.date2TimeStamp(year, month, day, hour, 0));
        dataHour.setData_from(dataFrom);
        dataHour.setDetail_data(JsonUtils.toJson(data));
        dataHour.saveOrUpdate("uid=? and record_date=? and data_from=?", uid + "", dataHour.getRecord_date() + "", dataFrom + "");
    }

    public static void saveFatiueData(long uid, long time, String dataFrom, String date, String detail) {
        TB_fatigue_history fatigue_history = new TB_fatigue_history();
        fatigue_history.setUid(uid);
        fatigue_history.setData_from(dataFrom);
        fatigue_history.setDate(date);
        fatigue_history.setDetail(detail);
        fatigue_history.setTime(time);
        fatigue_history.setUpload(0);
        fatigue_history.saveOrUpdate("uid=? and data_from=? and date=?", uid + "", dataFrom, date);
        HealthDataEventBus.updateHealthFatigueEvent();
    }

    public static TB_v3_sport_data getTbSport(long uid, int sportType, int year, int month, int day, int sTime, long sUTime, int eTime, long eUTime, float calorie, String detail, String dataFrom, int automatic) {
        TB_v3_sport_data sport = new TB_v3_sport_data();
        sport.setUid(uid);
        sport.setYear(year);
        sport.setMonth(month);
        sport.setDay(day);
        if (sTime - automatic < 0) {
            sport.setStart_time((sTime - automatic) + 1440);
        } else {
            sport.setStart_time(sTime - automatic);
        }
        sport.setStart_uxtime(sUTime - ((long) (automatic * 60)));
        if (sportType == 1) {
            sport.setEnd_time(eTime + 1);
            sport.setEnd_uxtime(60 + eUTime);
        } else {
            if (eUTime % 60 == 0) {
                sport.setEnd_time(eTime);
            } else {
                sport.setEnd_time(eTime + 1);
            }
            sport.setEnd_uxtime(eUTime);
        }
        sport.setCalorie((double) calorie);
        sport.setSport_type(sportType);
        sport.setDetail_data(detail);
        sport.setData_from(dataFrom);
        return sport;
    }

    public static void saveBlueToSwimSport(long uid, int sportType, long sUTime, long eUTime, float calorie, int step, int poolLength, int laps, String dataFrom, int automatic, int pauseTime, String hrUrl) {
        long activity = eUTime - (sUTime - ((long) (automatic * 60)));
        int distance = laps * poolLength;
        if (sportType == 1) {
            return;
        }
        if (activity >= 300 || distance >= 10) {
            long startTime = sUTime - ((long) (automatic * 60));
            TB_sport_swim oneGps = (TB_sport_swim) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_swim.class);
            String heartUrl = hrUrl;
            int upload = 0;
            if (oneGps != null) {
                heartUrl = oneGps.getHeart_url();
                upload = oneGps.getUpload_type();
            }
            if (upload != 1 || TextUtils.isEmpty(heartUrl)) {
                TB_sport_swim sport_swim = new TB_sport_swim();
                sport_swim.setUid(uid);
                sport_swim.setStart_time(startTime);
                sport_swim.setEnd_time(eUTime);
                sport_swim.setData_from(dataFrom);
                sport_swim.setSport_type(sportType);
                sport_swim.setCalorie(calorie);
                sport_swim.setDuration(((int) activity) - pauseTime);
                sport_swim.setHeart_url(heartUrl);
                sport_swim.setLaps(laps);
                sport_swim.setDone_times(step);
                sport_swim.setDistance((float) distance);
                sport_swim.setUpload_type(upload);
                sport_swim.saveOrUpdate("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom);
            }
        }
    }

    public static void saveBlueToGpsSport(long uid, int sportType, long sUTime, long eUTime, float calorie, int step, float distance, String dataFrom, int automatic, int pauseTime, int doneTimes, String hrUrl) {
        long activity = eUTime - (sUTime - ((long) (automatic * 60)));
        if (sportType == 1) {
            return;
        }
        if (activity >= 300 || distance >= 500.0f) {
            CopySportGps copySportGps = new CopySportGps();
            copySportGps.setUid(uid);
            copySportGps.setStart_time(sUTime - ((long) (automatic * 60)));
            copySportGps.setEnd_time(eUTime);
            copySportGps.setData_from(dataFrom);
            copySportGps.setSource_type(2);
            copySportGps.setStep(step);
            copySportGps.setDistance(distance);
            copySportGps.setCalorie(calorie);
            copySportGps.setDuration(((int) activity) - pauseTime);
            copySportGps.setPace(0.0f);
            copySportGps.setStride(80);
            copySportGps.setDone_times(doneTimes);
            copySportGps.setHeart_url(hrUrl);
            int mType = sportType;
            int tType = getSportDataTYpe(sportType);
            if (sportType == 7) {
                mType = 0;
            } else if (sportType == 136) {
                mType = 1;
            } else if (sportType == 5) {
                mType = 3;
            } else if (sportType == 147) {
                mType = 2;
            }
            copySportGps.setSport_type(mType);
            if (tType == 0) {
                saveGpsSport(copySportGps);
            } else if (tType == 1) {
                saveGpsBall(copySportGps);
            } else if (tType == 2) {
                saveGpsOther(copySportGps);
            }
        }
    }

    public static int getSportDataTYpe(int sportType) {
        switch (sportType) {
            case 1:
                return -1;
            case 5:
                return 0;
            case 7:
                return 0;
            case 128:
                return 1;
            case 129:
                return 1;
            case Opcodes.INT_TO_FLOAT /*130*/:
                return 1;
            case Opcodes.INT_TO_DOUBLE /*131*/:
                return 3;
            case Opcodes.LONG_TO_INT /*132*/:
                return 1;
            case Opcodes.LONG_TO_FLOAT /*133*/:
                return 1;
            case Opcodes.LONG_TO_DOUBLE /*134*/:
                return 1;
            case 135:
                return 1;
            case Opcodes.FLOAT_TO_LONG /*136*/:
                return 0;
            case Opcodes.DIV_INT /*147*/:
                return 0;
            case 255:
                return -1;
            case 256:
                return -1;
            default:
                return 2;
        }
    }

    public static int getSportTypeFromType(int type) {
        if (type == 0) {
            return 7;
        }
        if (type == 1) {
            return Opcodes.FLOAT_TO_LONG;
        }
        if (type == 2) {
            return Opcodes.DIV_INT;
        }
        if (type == 3) {
            return 5;
        }
        return type;
    }

    public static void saveGpsSport(CopySportGps copySportGps) {
        TB_sport_gps_segment oneGps = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getData_from()).findFirst(TB_sport_gps_segment.class);
        String heartUrl = copySportGps.getHeart_url();
        String r1Url = copySportGps.getR1_url();
        String gpsUrl = copySportGps.getGps_url();
        int upload = 0;
        if (oneGps != null) {
            if (!TextUtils.isEmpty(oneGps.getGps_url())) {
                gpsUrl = oneGps.getGps_url();
            }
            if (!TextUtils.isEmpty(oneGps.getHeart_url())) {
                heartUrl = oneGps.getHeart_url();
            }
            if (!TextUtils.isEmpty(oneGps.getR1_url())) {
                r1Url = oneGps.getR1_url();
            }
            upload = oneGps.getUpload();
        }
        if (copySportGps.getStart_time() != 0) {
            TB_sport_gps_segment gps_segment = new TB_sport_gps_segment();
            gps_segment.setUid(copySportGps.getUid());
            gps_segment.setStart_time(copySportGps.getStart_time());
            gps_segment.setEnd_time(copySportGps.getEnd_time());
            gps_segment.setData_from(copySportGps.getData_from());
            gps_segment.setSource_type(copySportGps.getSource_type());
            gps_segment.setSport_type(copySportGps.getSport_type());
            gps_segment.setStep(copySportGps.getStep());
            gps_segment.setDistance(copySportGps.getDistance());
            gps_segment.setCalorie(copySportGps.getCalorie());
            gps_segment.setDuration(copySportGps.getDuration());
            gps_segment.setGps_url(gpsUrl);
            gps_segment.setHeart_url(heartUrl);
            gps_segment.setR1_url(r1Url);
            gps_segment.setUpload(upload);
            gps_segment.setMtime(new DateUtil(gps_segment.getStart_time(), true).getY_M_D_H_M_S());
            gps_segment.saveOrUpdate("uid=? and start_time=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getData_from());
        }
    }

    public static void saveGpsBall(CopySportGps copySportGps) {
        TB_sport_ball oneGps = (TB_sport_ball) DataSupport.where("uid=? and start_time=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getData_from()).findFirst(TB_sport_ball.class);
        String heartUrl = copySportGps.getHeart_url();
        int upload = 0;
        if (oneGps != null) {
            heartUrl = oneGps.getHeart_url();
            upload = oneGps.getUpload_type();
        }
        if (upload != 1 || TextUtils.isEmpty(heartUrl)) {
            TB_sport_ball sport_ball = new TB_sport_ball();
            sport_ball.setUid(copySportGps.getUid());
            sport_ball.setStart_time(copySportGps.getStart_time());
            sport_ball.setEnd_time(copySportGps.getEnd_time());
            sport_ball.setData_from(copySportGps.getData_from());
            sport_ball.setSport_type(copySportGps.getSport_type());
            sport_ball.setCalorie(copySportGps.getCalorie());
            sport_ball.setDuration(copySportGps.getDuration());
            sport_ball.setHeart_url(heartUrl);
            sport_ball.setUpload_type(upload);
            sport_ball.saveOrUpdate("uid=? and start_time=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getData_from());
        }
    }

    public static void saveGpsOther(CopySportGps copySportGps) {
        TB_sport_other oneGps = (TB_sport_other) DataSupport.where("uid=? and start_time=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getData_from()).findFirst(TB_sport_other.class);
        String heartUrl = copySportGps.getHeart_url();
        int upload = 0;
        if (oneGps != null) {
            heartUrl = oneGps.getHeart_url();
            upload = oneGps.getUpload_type();
        }
        if (upload != 1 || TextUtils.isEmpty(heartUrl)) {
            TB_sport_other sport_other = new TB_sport_other();
            sport_other.setUid(copySportGps.getUid());
            sport_other.setStart_time(copySportGps.getStart_time());
            sport_other.setEnd_time(copySportGps.getEnd_time());
            sport_other.setData_from(copySportGps.getData_from());
            sport_other.setSport_type(copySportGps.getSport_type());
            sport_other.setCalorie(copySportGps.getCalorie());
            sport_other.setDuration(copySportGps.getDuration());
            sport_other.setDone_times(copySportGps.getDone_times());
            sport_other.setHeart_url(heartUrl);
            sport_other.setUpload_type(upload);
            sport_other.saveOrUpdate("uid=? and start_time=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getData_from());
        }
    }

    public static void upGpsSportOneUrl(String url, int upUrlType, long uid, long startTime, String dataFrom, int dataType) {
        KLog.e("no2855-> 心率上传的我去更新心率url " + url);
        if (dataType == 0) {
            KLog.d("no2855-> 心率上传的我去更新心率url00: " + upUrlType + " - " + uid + " - " + startTime + " - " + dataFrom + " - " + dataType + " - " + uid + " - ");
        }
        if (dataType == 0) {
            TB_sport_gps_segment oneGps = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and data_from like ?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_gps_segment.class);
            if (oneGps != null) {
                if (upUrlType == 0 && (TextUtils.isEmpty(oneGps.getGps_url()) || "1".equals(oneGps.getGps_url()))) {
                    int upType = oneGps.getUrl_type() | 1;
                    oneGps.setGps_url(url);
                    oneGps.setUrl_type(upType);
                    KLog.e("no2855-> 心率更新gps " + url + " == " + oneGps.getId());
                    oneGps.update(oneGps.getId());
                }
                if (upUrlType == 1 && (TextUtils.isEmpty(oneGps.getHeart_url()) || "1".equals(oneGps.getHeart_url()))) {
                    int upType2 = oneGps.getUrl_type() | 2;
                    oneGps.setHeart_url(url);
                    oneGps.setUrl_type(upType2);
                    oneGps.update(oneGps.getId());
                }
                if (upUrlType != 2) {
                    return;
                }
                if (TextUtils.isEmpty(oneGps.getR1_url()) || "1".equals(oneGps.getR1_url())) {
                    int upType3 = oneGps.getUrl_type() | 4;
                    oneGps.setR1_url(url);
                    oneGps.setUrl_type(upType3);
                    oneGps.update(oneGps.getId());
                    KLog.e("yyyxxx-> 上传url成功 " + startTime + "url" + url);
                }
            }
        } else if (dataType == 1) {
            TB_sport_ball oneGps2 = (TB_sport_ball) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_ball.class);
            if (oneGps2 == null) {
                return;
            }
            if (TextUtils.isEmpty(oneGps2.getHeart_url()) || "1".equals(oneGps2.getHeart_url())) {
                oneGps2.setHeart_url(url);
                oneGps2.update(oneGps2.getId());
            }
        } else if (dataType == 2) {
            TB_sport_other oneGps3 = (TB_sport_other) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_other.class);
            if (oneGps3 == null) {
                return;
            }
            if (TextUtils.isEmpty(oneGps3.getHeart_url()) || "1".equals(oneGps3.getHeart_url())) {
                oneGps3.setHeart_url(url);
                oneGps3.update(oneGps3.getId());
            }
        } else if (dataType == 3) {
            TB_sport_swim oneGps4 = (TB_sport_swim) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_swim.class);
            if (oneGps4 == null) {
                return;
            }
            if (TextUtils.isEmpty(oneGps4.getHeart_url()) || "1".equals(oneGps4.getHeart_url())) {
                oneGps4.setHeart_url(url);
                oneGps4.update(oneGps4.getId());
            }
        }
    }

    public static void saveSwim2File(TB_swimrate_data heart, LinkedList<Integer> steps, boolean isCheck) {
        if (heart != null) {
            String log_file = LogPath.SWIM_PATH + heart.getUid() + "_" + heart.getStart_time() + "_" + heart.getData_from() + ".txt";
            if (!isCheck || !FileUtils.checkFileExists(log_file)) {
                if (FileUtils.checkFileExists(log_file)) {
                    FileUtils.deleteFile(log_file);
                }
                SwimUpData swimUpData = new SwimUpData();
                swimUpData.setSwim(steps);
                if (FileIOUtils.writeFileFromString(Environment.getExternalStorageDirectory().getAbsolutePath() + log_file, JsonUtils.toJson(swimUpData))) {
                    SportDeviceNetWorkUtil.uploadHr(false, heart.getUid(), heart.getStart_time(), heart.getEnd_time(), heart.getData_from(), heart.getSport_type());
                }
            }
        }
    }

    public static void saveHr2File(TB_heartrate_data heart, int[] walks, boolean isUp, boolean isCheck) {
        if (heart != null) {
            String log_file = LogPath.HR_PATH + heart.getUid() + "_hr_" + heart.getStart_time() + "_" + heart.getData_from() + ".txt";
            if (!isCheck || !FileUtils.checkFileExists(log_file)) {
                HrUpData hrUpData = new HrUpData();
                Hr51 hr511 = new Hr51();
                HeartRateDetial heartRateDetial = (HeartRateDetial) JsonUtils.fromJson(heart.getDetail_data(), HeartRateDetial.class);
                if (heartRateDetial != null) {
                    hr511.setR0(heartRateDetial.getR0());
                    hr511.setR1(heartRateDetial.getR1());
                    hr511.setR2(heartRateDetial.getR2());
                    hr511.setR3(heartRateDetial.getR3());
                    hr511.setR4(heartRateDetial.getR4());
                    hr511.setR5(heartRateDetial.getR5());
                }
                List<Integer> hr53 = JsonUtils.getListJson(heart.getReserved(), Integer.class);
                hrUpData.setSf(walks);
                hrUpData.setH1(hr511);
                hrUpData.setH3(hr53);
                hrUpData.setAg(heart.getAge());
                String msg = JsonUtils.toJson(hrUpData);
                if (FileUtils.checkFileExists(log_file)) {
                    FileUtils.deleteFile(log_file);
                }
                if (FileIOUtils.writeFileFromString(Environment.getExternalStorageDirectory().getAbsolutePath() + log_file, msg) && isUp) {
                    SportDeviceNetWorkUtil.uploadHr(false, heart.getUid(), heart.getStart_time(), heart.getEnd_time(), heart.getData_from(), heart.getSport_type());
                }
            }
        }
    }

    public static void saveIVSportTem(TB_v3_sport_data entity) {
        if (entity != null && entity.getSport_type() != 1 && entity.getEnd_uxtime() - entity.getStart_uxtime() >= 300) {
            TB_iv_temporary iv_temporary = new TB_iv_temporary();
            iv_temporary.setUid(entity.getUid());
            iv_temporary.setHas_hr(0);
            iv_temporary.setData_from(entity.getData_from());
            iv_temporary.setSport_type(entity.getSport_type());
            iv_temporary.setStart_time(entity.getStart_uxtime());
            iv_temporary.setEnd_time(entity.getEnd_uxtime());
            iv_temporary.save();
        }
    }

    public static void writeBlueOneGps2SD(long uid, long startTime, long endTime, String dataFrom) {
        List<TB_blue_gps> zg_gps = DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, startTime + "", endTime + "").order("time asc").find(TB_blue_gps.class);
        if (zg_gps != null && zg_gps.size() > 0) {
            List<GpsUpData> gpsData = new LinkedList<>();
            for (TB_blue_gps zg_gp : zg_gps) {
                gpsData.add(new GpsUpData(zg_gp.getTime(), zg_gp.getLat(), zg_gp.getLon(), 0, 0));
            }
            String file1Path = uid + "_gps_" + startTime + "_" + dataFrom + ".txt";
            String log_file = LogPath.GPS_PATH + file1Path;
            if (FileUtils.checkFileExists(log_file)) {
                FileUtils.deleteFile(log_file);
            }
            KLog.e("no2855-->重新准备写入的gps文件: " + file1Path);
            FileUtils.write2SDFromString_1(LogPath.GPS_PATH, file1Path, JsonUtils.toJson(gpsData));
        }
    }

    public static void writeBlueOneGps2SD(TB_sport_gps_segment gps_segment) {
        writeBlueOneGps2SD(gps_segment.getUid(), gps_segment.getStart_time(), gps_segment.getEnd_time(), gps_segment.getData_from());
    }

    public static void writeBlueOneHr2SD(TB_sport_gps_segment gps_segment) {
        String dataFrom = gps_segment.getData_from();
        long uid = gps_segment.getUid();
        List<TB_blue_gps> zg_gps = DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, gps_segment.getStart_time() + "", gps_segment.getEnd_time() + "").order("time asc").find(TB_blue_gps.class);
        if (zg_gps != null && zg_gps.size() > 0) {
            List<GpsUpData> gpsData = new LinkedList<>();
            for (TB_blue_gps zg_gp : zg_gps) {
                gpsData.add(new GpsUpData(zg_gp.getTime(), zg_gp.getLat(), zg_gp.getLon(), 0, 0));
            }
            String file1Path = uid + "_gps_" + gps_segment.getStart_time() + "_" + dataFrom + ".txt";
            String log_file = LogPath.GPS_PATH + file1Path;
            if (FileUtils.checkFileExists(log_file)) {
                FileUtils.deleteFile(log_file);
            }
            KLog.e("no2855-->重新准备写入的gps文件: " + file1Path);
            FileUtils.write2SDFromString_1(LogPath.GPS_PATH, file1Path, JsonUtils.toJson(gpsData));
        }
    }

    public static void writeBlueGps2SD(long uid, String dataFrom, int year, int month, int day, boolean writeSD) {
        if (writeSD) {
            L.file("no2855 62入 write文件日期 " + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day, 3);
        }
        long stTime = new DateUtil(year, month, day, 0, 0, 0).getUnixTimestamp();
        List<TB_sport_gps_segment> gps_segments = DataSupport.where("uid=? and data_from=? and start_time>=? and start_time<?", uid + "", dataFrom, stTime + "", (stTime + 86400) + "").find(TB_sport_gps_segment.class);
        if (gps_segments != null && gps_segments.size() > 0) {
            for (TB_sport_gps_segment gps_segment : gps_segments) {
                List<TB_blue_gps> zg_gps = DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, gps_segment.getStart_time() + "", gps_segment.getEnd_time() + "").order("time asc").find(TB_blue_gps.class);
                if (zg_gps != null && zg_gps.size() > 0) {
                    if (TextUtils.isEmpty(gps_segment.getGps_url())) {
                        gps_segment.setGps_url("1");
                        gps_segment.update(gps_segment.getId());
                    }
                    if (writeSD) {
                        List<GpsUpData> gpsData = new LinkedList<>();
                        for (TB_blue_gps zg_gp : zg_gps) {
                            gpsData.add(new GpsUpData(zg_gp.getTime(), zg_gp.getLat(), zg_gp.getLon(), 0, 0));
                        }
                        String file1Path = uid + "_gps_" + gps_segment.getStart_time() + "_" + dataFrom + ".txt";
                        String log_file = LogPath.GPS_PATH + file1Path;
                        if (FileUtils.checkFileExists(log_file)) {
                            FileUtils.deleteFile(log_file);
                        }
                        KLog.e("no2855-->准备写入的gps文件: " + file1Path);
                        L.file("no2855 62入 write文件写入statue " + file1Path + " == " + FileUtils.write2SDFromString_1(LogPath.GPS_PATH, file1Path, JsonUtils.toJson(gpsData)), 3);
                    }
                }
            }
        }
    }

    public static void writeMtkGps2TB(long uid, String dataFrom, int year, int month, int day, boolean isP1MIni) {
        KLog.e("no2855--> mtk_statues。时间: " + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day);
        List<TB_62_data> list = DataSupport.where("uid=? and data_from=? and year=? and month=? and day=?", String.valueOf(uid), dataFrom, year + "", month + "", day + "").order("time asc").find(TB_62_data.class);
        if (list != null && list.size() > 0) {
            KLog.e("no2855--> list.size: " + list.size());
            for (TB_62_data data : list) {
                List<LongitudeAndLatitude> latitudes = JsonTool.getListJson(data.getGnssData(), LongitudeAndLatitude.class);
                if (latitudes != null && latitudes.size() > 0) {
                    KLog.e("no2855--> latitudes解析成功 " + latitudes.size());
                    long startTime = data.getTime() / 1000;
                    int num = 0;
                    int value = isP1MIni ? data.getFreq() < 1 ? 1 : data.getFreq() : 60 / latitudes.size();
                    for (LongitudeAndLatitude latitude : latitudes) {
                        TB_blue_gps blue_gps = new TB_blue_gps();
                        blue_gps.setUid(uid);
                        blue_gps.setData_from(dataFrom);
                        long mTime = startTime + ((long) (num * value));
                        blue_gps.setTime(mTime);
                        blue_gps.setLat(latitude.getLatitude());
                        blue_gps.setLon(latitude.getLongitude());
                        blue_gps.saveOrUpdate("uid=? and data_from=? and time=?", uid + "", dataFrom, mTime + "");
                        num++;
                    }
                }
            }
            TB_mtk_statue mtk_statue = (TB_mtk_statue) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=?", String.valueOf(uid), dataFrom, year + "", month + "", day + "").findFirst(TB_mtk_statue.class);
            if (mtk_statue != null) {
                mtk_statue.setHas_tb(1);
                mtk_statue.update(mtk_statue.getId());
            }
            writeBlueGps2SD(uid, dataFrom, year, month, day, false);
        }
    }

    public static boolean hasHeart(String dataFrom) {
        boolean noHr;
        if (TextUtils.isEmpty(dataFrom)) {
            return false;
        }
        if (dataFrom.contains("Bracel04") || dataFrom.contains("Braceli5") || dataFrom.contains("Bracel02") || dataFrom.contains("Bracel15") || dataFrom.contains("I6Dk")) {
            noHr = true;
        } else {
            noHr = false;
        }
        if (!noHr) {
            return true;
        }
        return false;
    }

    public static void deleteBleGps() {
        DateUtil dateUtil = new DateUtil();
        dateUtil.addDay(-25);
        DataSupport.deleteAll(TB_blue_gps.class, "time<?", dateUtil.getUnixTimestamp() + "");
    }
}
