package com.iwown.device_module.interactive_service;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.sport_data.Bp_data_sport;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.sport_data.HomeTrandingTodayData;
import com.iwown.data_link.sport_data.ISportService;
import com.iwown.data_link.sport_data.P1_61_data;
import com.iwown.data_link.sport_data.P1_62_data;
import com.iwown.data_link.sport_data.R1_68_data;
import com.iwown.data_link.sport_data.Sport28Code;
import com.iwown.data_link.sport_data.Sport28Code.SportCode;
import com.iwown.data_link.sport_data.SportBallCode;
import com.iwown.data_link.sport_data.SportBallCode.BallCode;
import com.iwown.data_link.sport_data.SportGpsCode;
import com.iwown.data_link.sport_data.SportGpsCode.GpsCode;
import com.iwown.data_link.sport_data.SportOtherCode;
import com.iwown.data_link.sport_data.SportOtherCode.OtherCode;
import com.iwown.data_link.sport_data.SportSwimCode;
import com.iwown.data_link.sport_data.SportSwimCode.SwimCode;
import com.iwown.data_link.sport_data.SportTotalData;
import com.iwown.data_link.sport_data.V3_sport_data;
import com.iwown.data_link.sport_data.gps.BleGpsData;
import com.iwown.data_link.sport_data.gps.LongitudeAndLatitude;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.Ble61DataParse;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.Ble62DataParse;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkToIvHandler;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.SportDeviceNetWorkUtil;
import com.iwown.device_module.common.sql.TB_61_data;
import com.iwown.device_module.common.sql.TB_62_data;
import com.iwown.device_module.common.sql.TB_68_data;
import com.iwown.device_module.common.sql.TB_BP_data;
import com.iwown.device_module.common.sql.TB_blue_gps;
import com.iwown.device_module.common.sql.TB_sport_ball;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.iwown.device_module.common.sql.TB_sport_other;
import com.iwown.device_module.common.sql.TB_sport_swim;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_gps.factory.listsport.SportListFactory;
import com.iwown.device_module.device_gps.factory.onesport.SportFactory;
import com.iwown.device_module.device_operation.bean.ModeItems;
import com.iwown.device_module.device_operation.bean.ModeItems.DataBean;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.litepal.crud.DataSupport;

@Route(path = "/device/sport_service")
public class SportService implements ISportService {
    public List<String> get28Sport(long uid, int year, int month, int day, String dataFrom) {
        List<TB_v3_sport_data> tb_v3_sport_datas = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", String.valueOf(uid), String.valueOf(year), String.valueOf(month), String.valueOf(day), dataFrom).order("start_uxtime desc").find(TB_v3_sport_data.class);
        if (tb_v3_sport_datas == null || tb_v3_sport_datas.size() == 0) {
            return null;
        }
        List<String> strings = new ArrayList<>();
        for (TB_v3_sport_data sport_data : tb_v3_sport_datas) {
            strings.add(JsonTool.toJson(sport_data));
        }
        return strings;
    }

    public List<V3_sport_data> get28SportNoDataFrom(long uid, int year, int month, int day) {
        List<TB_v3_sport_data> tb_v3_sport_datas = DataSupport.where("uid=? and year=? and month=? and day=?", String.valueOf(uid), String.valueOf(year), String.valueOf(month), String.valueOf(day)).find(TB_v3_sport_data.class);
        if (tb_v3_sport_datas == null || tb_v3_sport_datas.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (TB_v3_sport_data sport_data : tb_v3_sport_datas) {
            V3_sport_data sport_dataz = (V3_sport_data) JsonTool.fromJson(JsonTool.toJson(sport_data), V3_sport_data.class);
            if (!arrayList.contains(sport_dataz)) {
                arrayList.add(sport_dataz);
            }
        }
        return arrayList;
    }

    public List<String> get28SportAsTimePeriod(long uid, String dataFrom, long start, long end, int sportType) {
        Log.e("licl", "start_time" + start + " end_time" + end);
        List<TB_v3_sport_data> tb_v3_sport_datas = DataSupport.where("uid=? and data_from=? and start_uxtime>=? and end_uxtime<=? and sport_type=?", uid + "", dataFrom + "", (start / 1000) + "", (end / 1000) + "", sportType + "").find(TB_v3_sport_data.class);
        KLog.e(JsonTool.toJson(tb_v3_sport_datas));
        if (tb_v3_sport_datas == null || tb_v3_sport_datas.size() == 0) {
            return null;
        }
        List<String> strings = new ArrayList<>();
        for (TB_v3_sport_data sport_data : tb_v3_sport_datas) {
            strings.add(JsonTool.toJson(sport_data));
        }
        return strings;
    }

    public List<Detail_data> get28DetailAsTimePeriod(long uid, String dataFrom, long start, long end, int sportType) {
        Log.e("licl", "start_time" + start + " end_time" + end);
        List<TB_v3_sport_data> dataList = DataSupport.where("uid=? and data_from=? and start_uxtime>=? and end_uxtime<=? and sport_type=?", uid + "", dataFrom + "", (start / 1000) + "", (end / 1000) + "", sportType + "").find(TB_v3_sport_data.class);
        List<Detail_data> list = new ArrayList<>();
        if (!(dataList == null || dataList.size() == 0)) {
            for (TB_v3_sport_data data : dataList) {
                list.add((Detail_data) JsonTool.fromJson(data.getDetail_data(), Detail_data.class));
            }
        }
        return list;
    }

    public void downloadTBSport(Sport28Code resultCode) {
        if (resultCode != null && resultCode.getData() != null && resultCode.getData().size() > 0) {
            List<SportCode> content = resultCode.getData();
            KLog.e(JsonTool.toJson(resultCode), "下载的28数据入表");
            if (content.size() > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (SportCode sportData : content) {
                    long stTime = 0;
                    try {
                        stTime = simpleDateFormat.parse(sportData.getStart_time()).getTime() / 1000;
                    } catch (ParseException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    if (DataSupport.where("uid=? and start_uxtime=? and data_from=? and sport_type=?", sportData.getUid() + "", stTime + "", sportData.getData_from() + "", sportData.getSport_type() + "").find(TB_v3_sport_data.class).size() <= 0) {
                        Detail_data detail_data = new Detail_data();
                        detail_data.setActivity(sportData.getDuration());
                        detail_data.setCount(sportData.getDone_times());
                        detail_data.setDistance(sportData.getDistance());
                        detail_data.setStep(sportData.getStep());
                        if (sportData.getDistance() <= 0.0f || ((float) sportData.getStep()) / sportData.getDistance() < 1000.0f) {
                            detail_data.setDistance(sportData.getDistance());
                        } else {
                            detail_data.setDistance(sportData.getDistance() * 1000.0f);
                        }
                        String deStr = JsonTool.toJson(detail_data);
                        TB_v3_sport_data tbData = new TB_v3_sport_data();
                        tbData.set_uploaded(1);
                        long edTime = 0;
                        try {
                            stTime = simpleDateFormat.parse(sportData.getStart_time()).getTime() / 1000;
                            edTime = simpleDateFormat.parse(sportData.getEnd_time()).getTime() / 1000;
                        } catch (ParseException e2) {
                            ThrowableExtension.printStackTrace(e2);
                        }
                        tbData.setStart_uxtime(stTime);
                        tbData.setEnd_uxtime(edTime);
                        tbData.setCalorie((double) sportData.getCalorie());
                        tbData.setData_from(sportData.getData_from());
                        tbData.setUid(sportData.getUid());
                        if (sportData.getSport_type() != 256) {
                            tbData.setSport_type(sportData.getSport_type());
                        } else {
                            tbData.setSport_type(1);
                        }
                        tbData.setDetail_data(deStr);
                        DateUtil dateUtil = new DateUtil(stTime, true);
                        tbData.setYear(dateUtil.getYear());
                        tbData.setMonth(dateUtil.getMonth());
                        tbData.setDay(dateUtil.getDay());
                        tbData.setWeek(dateUtil.getWeekOfYear());
                        tbData.setStart_time((dateUtil.getHour() * 60) + dateUtil.getMinute());
                        DateUtil dateUtil1End = new DateUtil(edTime, true);
                        tbData.setEnd_time((dateUtil1End.getHour() * 60) + dateUtil1End.getMinute());
                        tbData.save();
                    }
                }
            }
        }
    }

    public void changeUpFlag(long uid) {
        TB_v3_sport_data sport_data = new TB_v3_sport_data();
        sport_data.set_uploaded(1);
        sport_data.updateAll("uid=? and _uploaded=?", uid + "", "0");
    }

    public void updateOthers(V3_sport_data v3_sport_data) {
        TB_v3_sport_data sport_data = new TB_v3_sport_data();
        sport_data.setYear(v3_sport_data.getYear());
        sport_data.setMonth(v3_sport_data.getMonth());
        sport_data.setDay(v3_sport_data.getDay());
        sport_data.setWeek(v3_sport_data.getWeek());
        sport_data.setData_from(v3_sport_data.getData_from());
        sport_data.setUid(v3_sport_data.getUid());
        sport_data.set_uploaded(0);
        sport_data.setSport_type(255);
        sport_data.setStart_time(v3_sport_data.getStart_time());
        sport_data.setEnd_time(v3_sport_data.getEnd_time());
        sport_data.setStart_uxtime(v3_sport_data.getStart_uxtime());
        sport_data.setEnd_uxtime(v3_sport_data.getEnd_uxtime());
        sport_data.setCalorie(v3_sport_data.getCalorie());
        sport_data.setDetail_data(v3_sport_data.getDetail_data());
        sport_data.saveOrUpdate("uid=? and year=? and month=? and day=? and data_from=? and sport_type=?", sport_data.getUid() + "", sport_data.getYear() + "", sport_data.getMonth() + "", sport_data.getDay() + "", sport_data.getData_from() + "", sport_data.getSport_type() + "");
    }

    public void deleteOthers(long uid, String data_from, int year, int month, int day) {
        DataSupport.deleteAll(TB_v3_sport_data.class, "uid=? and data_from=? and year=? and month=? and day=? and sport_type=?", uid + "", data_from + "", year + "", month + "", day + "", "255");
    }

    public List<String> getNeedUpLoadSport(long uid) {
        List<TB_v3_sport_data> dataList = DataSupport.where("uid=? and _uploaded=?", uid + "", "0").find(TB_v3_sport_data.class);
        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        List<String> jsonList = new ArrayList<>();
        for (TB_v3_sport_data data : dataList) {
            jsonList.add(JsonTool.toJson(data));
        }
        return jsonList;
    }

    public List<Bp_data_sport> getAllDataBlood(long uid, long start, long end) {
        Log.e("blood pressure", "start_time" + start + " end_time" + end);
        List<TB_BP_data> dataList = DataSupport.where("bptime>? and bptime<? and uid=?", start + "", end + "", uid + "").find(TB_BP_data.class);
        List<Bp_data_sport> list = new ArrayList<>();
        if (!(dataList == null || dataList.size() == 0)) {
            for (TB_BP_data data : dataList) {
                Bp_data_sport bp_data_sport = new Bp_data_sport();
                bp_data_sport.setUid(data.getUid());
                bp_data_sport.setBpTime(data.getBpTime());
                bp_data_sport.setDataFrom(data.getDataFrom());
                bp_data_sport.setDbp(data.getDbp());
                bp_data_sport.setSbp(data.getSbp());
                bp_data_sport.setIsupload(data.getIsupload());
                list.add(bp_data_sport);
            }
        }
        return list;
    }

    public List<Bp_data_sport> getDataBlood(long uid) {
        List<TB_BP_data> dataList = DataSupport.where("uid=? ", uid + "").find(TB_BP_data.class);
        List<Bp_data_sport> list = new ArrayList<>();
        if (!(dataList == null || dataList.size() == 0)) {
            for (TB_BP_data data : dataList) {
                Bp_data_sport bp_data_sport = new Bp_data_sport();
                bp_data_sport.setUid(data.getUid());
                bp_data_sport.setBpTime(data.getBpTime());
                bp_data_sport.setDataFrom(data.getDataFrom());
                bp_data_sport.setDbp(data.getDbp());
                bp_data_sport.setSbp(data.getSbp());
                bp_data_sport.setIsupload(data.getIsupload());
                list.add(bp_data_sport);
            }
        }
        return list;
    }

    public void saveBloodData(long uid, String datafrom, long bptime, int dbp, int sbp) {
        TB_BP_data driveruser_2 = (TB_BP_data) DataSupport.where("uid=? and datafrom =? and bptime =?", uid + "", datafrom, bptime + "").findFirst(TB_BP_data.class);
        if (driveruser_2 == null) {
            driveruser_2 = new TB_BP_data();
        }
        driveruser_2.setBpTime(bptime);
        driveruser_2.setDataFrom(datafrom);
        driveruser_2.setDbp(dbp);
        driveruser_2.setSbp(sbp);
        driveruser_2.setIsupload(1);
        driveruser_2.setUid(UserConfig.getInstance().getNewUID());
        driveruser_2.saveOrUpdate("uid=? and datafrom =? and bptime =?", uid + "", datafrom, bptime + "");
    }

    public void updateDataBlood(long uid, String bptime) {
        ContentValues values = new ContentValues();
        values.put("isupload", Integer.valueOf(1));
        DataSupport.updateAll(TB_BP_data.class, values, "bptime = ? and uid = ?", bptime, uid + "");
    }

    public List<P1_62_data> get62Data(long start, long end, String data_from, long uid) {
        Log.e("licl", "start_time" + start + " end_time" + end);
        List<TB_62_data> dataList = DataSupport.where("time>=? and time<=? and data_from=? and uid=?", start + "", end + "", data_from + "", uid + "").order("time").find(TB_62_data.class);
        List<P1_62_data> list = new ArrayList<>();
        if (!(dataList == null || dataList.size() == 0)) {
            for (TB_62_data data : dataList) {
                P1_62_data p1_62_data = new P1_62_data();
                p1_62_data.setCmd(data.getCmd());
                p1_62_data.setCtrl(data.getCtrl());
                p1_62_data.setData_from(data.getData_from());
                p1_62_data.setDay(data.getDay());
                p1_62_data.setFreq(data.getFreq());
                p1_62_data.setGnssData(data.getGnssData());
                p1_62_data.setHour(data.getHour());
                p1_62_data.setMin(data.getMin());
                p1_62_data.setMonth(data.getMonth());
                p1_62_data.setNum(data.getNum());
                p1_62_data.setSeq(data.getSeq());
                p1_62_data.setTime(data.getTime());
                p1_62_data.setUid(data.getUid());
                p1_62_data.setYear(data.getYear());
                list.add(p1_62_data);
            }
        }
        return list;
    }

    public void save62DataFromFile(long uid, String data_from, byte[] datas) {
        if (datas[2] != 98) {
            KLog.e("非62数据，不能入62表...");
            return;
        }
        Ble62DataParse ble62DataParse = Ble62DataParse.parse(datas);
        String str = "2_" + new DateUtil(ble62DataParse.getYear(), ble62DataParse.getMonth(), ble62DataParse.getDay()).getYyyyMMddDate();
        TB_62_data cmd62 = new TB_62_data();
        cmd62.setUid(uid);
        cmd62.setData_from(data_from);
        cmd62.setCtrl(ble62DataParse.getCtrl());
        cmd62.setSeq(ble62DataParse.getSeq());
        cmd62.setYear(ble62DataParse.getYear());
        cmd62.setMonth(ble62DataParse.getMonth());
        cmd62.setDay(ble62DataParse.getDay());
        cmd62.setHour(ble62DataParse.getHour());
        cmd62.setMin(ble62DataParse.getMin());
        cmd62.setFreq(ble62DataParse.getFreq());
        cmd62.setNum(ble62DataParse.getNum());
        cmd62.setCmd(Utils.bytesToHexString(datas));
        cmd62.setTime(new DateUtil(cmd62.getYear(), cmd62.getMonth(), cmd62.getDay(), cmd62.getHour(), cmd62.getMin(), 0).getTimestamp());
        cmd62.setGnssData(ble62DataParse.getDetail() + "");
        if (cmd62.getYear() - 2000 != 255 || cmd62.getMonth() - 1 != 255 || cmd62.getDay() - 1 != 255 || cmd62.getHour() != 255 || cmd62.getMin() != 255) {
            cmd62.saveOrUpdate("uid=? and seq=? and year =? and month=? and day=? and hour=? and min=? and data_from=?", String.valueOf(uid), String.valueOf(cmd62.getSeq()), String.valueOf(cmd62.getYear()), String.valueOf(cmd62.getMonth()), String.valueOf(cmd62.getDay()), String.valueOf(cmd62.getHour()), String.valueOf(cmd62.getMin()), String.valueOf(data_from));
        }
    }

    public List<R1_68_data> get68Data(long uid, String data_from, int year, int month, int day) {
        List<TB_68_data> dataList = DataSupport.where("data_from=? and uid=?", data_from + "", uid + "").order("time asc").find(TB_68_data.class);
        List<R1_68_data> list = new ArrayList<>();
        if (!(dataList == null || dataList.size() == 0)) {
            for (TB_68_data data : dataList) {
                R1_68_data r1_68_data = new R1_68_data();
                r1_68_data.setUid(uid);
                r1_68_data.setData_from(data_from);
                r1_68_data.setCtrl(data.getCtrl());
                r1_68_data.setSeq(data.getSeq());
                r1_68_data.setYear(data.getYear());
                r1_68_data.setMonth(data.getMonth());
                r1_68_data.setDay(data.getDay());
                r1_68_data.setHour(data.getHour());
                r1_68_data.setMin(data.getMin());
                r1_68_data.setSeconds(data.getSeconds());
                r1_68_data.setData_type(data.getData_type());
                r1_68_data.setSport_type(data.getSport_type());
                r1_68_data.setState_type(data.getState_type());
                r1_68_data.setStep(data.getStep());
                r1_68_data.setDistance(data.getDistance());
                r1_68_data.setCalorie(data.getCalorie());
                r1_68_data.setRateOfStride_min(data.getRateOfStride_min());
                r1_68_data.setRateOfStride_max(data.getRateOfStride_max());
                r1_68_data.setRateOfStride_avg(data.getRateOfStride_avg());
                r1_68_data.setFlight_min(data.getFlight_min());
                r1_68_data.setFlight_max(data.getFlight_max());
                r1_68_data.setFlight_avg(data.getFlight_avg());
                r1_68_data.setTouchDown_min(data.getTouchDown_min());
                r1_68_data.setTouchDown_max(data.getTouchDown_max());
                r1_68_data.setTouchDown_avg(data.getTouchDown_avg());
                r1_68_data.setTouchDownPower_balance(data.getTouchDownPower_balance());
                r1_68_data.setTouchDownPower_stop(data.getTouchDownPower_stop());
                r1_68_data.setMin_hr(data.getMin_hr());
                r1_68_data.setMax_hr(data.getMax_hr());
                r1_68_data.setAvg_hr(data.getAvg_hr());
                r1_68_data.setTime(data.getTime());
                r1_68_data.setCmd(data.getCmd());
                list.add(r1_68_data);
            }
        }
        return list;
    }

    public List<R1_68_data> get68Data(long uid, String data_from, long startTime, long endTime) {
        List<TB_68_data> dataList = DataSupport.where("data_from=? and time>=? and time<=? ", data_from, startTime + "", endTime + "").order("time asc").find(TB_68_data.class);
        List<R1_68_data> list = new ArrayList<>();
        for (TB_68_data data : dataList) {
            R1_68_data r1_68_data = new R1_68_data();
            r1_68_data.setUid(uid);
            r1_68_data.setData_from(data_from);
            r1_68_data.setCtrl(data.getCtrl());
            r1_68_data.setSeq(data.getSeq());
            r1_68_data.setYear(data.getYear());
            r1_68_data.setMonth(data.getMonth());
            r1_68_data.setDay(data.getDay());
            r1_68_data.setHour(data.getHour());
            r1_68_data.setMin(data.getMin());
            r1_68_data.setSeconds(data.getSeconds());
            r1_68_data.setData_type(data.getData_type());
            r1_68_data.setSport_type(data.getSport_type());
            r1_68_data.setState_type(data.getState_type());
            r1_68_data.setStep(data.getStep());
            r1_68_data.setDistance(data.getDistance());
            r1_68_data.setCalorie(data.getCalorie());
            r1_68_data.setRateOfStride_min(data.getRateOfStride_min());
            r1_68_data.setRateOfStride_max(data.getRateOfStride_max());
            r1_68_data.setRateOfStride_avg(data.getRateOfStride_avg());
            r1_68_data.setFlight_min(data.getFlight_min());
            r1_68_data.setFlight_max(data.getFlight_max());
            r1_68_data.setFlight_avg(data.getFlight_avg());
            r1_68_data.setTouchDown_min(data.getTouchDown_min());
            r1_68_data.setTouchDown_max(data.getTouchDown_max());
            r1_68_data.setTouchDown_avg(data.getTouchDown_avg());
            r1_68_data.setTouchDownPower_balance(data.getTouchDownPower_balance());
            r1_68_data.setTouchDownPower_stop(data.getTouchDownPower_stop());
            r1_68_data.setMin_hr(data.getMin_hr());
            r1_68_data.setMax_hr(data.getMax_hr());
            r1_68_data.setAvg_hr(data.getAvg_hr());
            r1_68_data.setTime(data.getTime());
            r1_68_data.setCmd(data.getCmd());
            list.add(r1_68_data);
        }
        return list;
    }

    public void update68StateType(long uid, long time, int state_type) {
        TB_68_data tb_68_data = new TB_68_data();
        tb_68_data.setState_type(state_type);
        tb_68_data.updateAll("time=? and uid=?", time + "", uid + "");
    }

    public List<P1_61_data> get61Data(long uid, String data_from, long start, long end) {
        List<TB_61_data> dataList = DataSupport.where("time>=? and time<=? and data_from=? and uid=?", start + "", end + "", data_from + "", uid + "").order("time").find(TB_61_data.class);
        List<P1_61_data> list = new ArrayList<>();
        if (!(dataList == null || dataList.size() == 0)) {
            for (TB_61_data data : dataList) {
                P1_61_data p1_61_data = new P1_61_data();
                p1_61_data.setUid(uid);
                p1_61_data.setData_from(data_from);
                p1_61_data.setCtrl(data.getCtrl());
                p1_61_data.setSeq(data.getSeq());
                p1_61_data.setYear(data.getYear());
                p1_61_data.setMonth(data.getMonth());
                p1_61_data.setDay(data.getDay());
                p1_61_data.setHour(data.getHour());
                p1_61_data.setMin(data.getMin());
                p1_61_data.setData_type(data.getData_type());
                p1_61_data.setSport_type(data.getSport_type());
                p1_61_data.setCalorie(data.getCalorie());
                p1_61_data.setStep(data.getStep());
                p1_61_data.setDistance(data.getDistance());
                p1_61_data.setState_type(data.getState_type());
                p1_61_data.setReserve(data.getReserve());
                p1_61_data.setAutomatic(data.getAutomatic());
                p1_61_data.setMin_bpm(data.getMin_bpm());
                p1_61_data.setMax_bpm(data.getMax_bpm());
                p1_61_data.setAvg_bpm(data.getAvg_bpm());
                p1_61_data.setLevel(data.getLevel());
                p1_61_data.setSdnn(data.getSdnn());
                p1_61_data.setLf(data.getLf());
                p1_61_data.setHf(data.getHf());
                p1_61_data.setLf_hf(data.getLf_hf());
                p1_61_data.setBpm_hr(data.getBpm_hr());
                p1_61_data.setSbp(data.getSbp());
                p1_61_data.setDbp(data.getDbp());
                p1_61_data.setBpm(data.getBpm());
                p1_61_data.setTime(data.getTime());
                p1_61_data.setCmd(data.getCmd());
                list.add(p1_61_data);
            }
        }
        return list;
    }

    public List<P1_61_data> get61DataAsSportTypeAndSortBySeq(long uid, String data_from, long start, long end, int sport_type) {
        List<TB_61_data> allDatas = DataSupport.where("time>=? and time<=? and data_from=? and uid=? and sport_type=?", ((afreshStartTime(uid, data_from, start / 1000) * 1000) - 60000) + "", (999 + end) + "", data_from + "", uid + "", sport_type + "").order("seq asc").find(TB_61_data.class);
        List<P1_61_data> list = new ArrayList<>();
        List<TB_61_data> datas = new ArrayList<>();
        if (!(allDatas == null || allDatas.size() == 0)) {
            if (allDatas.size() <= 1) {
                datas.addAll(allDatas);
            } else if (((TB_61_data) allDatas.get(allDatas.size() - 1)).getSeq() - ((TB_61_data) allDatas.get(0)).getSeq() >= 4000) {
                int cut = 0;
                int i = 1;
                while (true) {
                    if (i >= allDatas.size()) {
                        break;
                    } else if (((TB_61_data) allDatas.get(i)).getSeq() - ((TB_61_data) allDatas.get(i - 1)).getSeq() >= 2000) {
                        cut = i;
                        break;
                    } else {
                        i++;
                    }
                }
                List<TB_61_data> firstDatas = new ArrayList<>();
                List<TB_61_data> twoDatas = new ArrayList<>();
                for (int i2 = 0; i2 < allDatas.size(); i2++) {
                    if (i2 < cut) {
                        firstDatas.add(allDatas.get(i2));
                    } else {
                        twoDatas.add(allDatas.get(i2));
                    }
                }
                twoDatas.addAll(firstDatas);
                datas.addAll(twoDatas);
            } else {
                datas.addAll(allDatas);
            }
            for (TB_61_data data : datas) {
                P1_61_data p1_61_data = new P1_61_data();
                p1_61_data.setUid(uid);
                p1_61_data.setData_from(data_from);
                p1_61_data.setCtrl(data.getCtrl());
                p1_61_data.setSeq(data.getSeq());
                p1_61_data.setYear(data.getYear());
                p1_61_data.setMonth(data.getMonth());
                p1_61_data.setDay(data.getDay());
                p1_61_data.setHour(data.getHour());
                p1_61_data.setMin(data.getMin());
                p1_61_data.setData_type(data.getData_type());
                p1_61_data.setSport_type(data.getSport_type());
                p1_61_data.setCalorie(data.getCalorie());
                p1_61_data.setStep(data.getStep());
                p1_61_data.setDistance(data.getDistance());
                p1_61_data.setState_type(data.getState_type());
                p1_61_data.setReserve(data.getReserve());
                p1_61_data.setAutomatic(data.getAutomatic());
                p1_61_data.setMin_bpm(data.getMin_bpm());
                p1_61_data.setMax_bpm(data.getMax_bpm());
                p1_61_data.setAvg_bpm(data.getAvg_bpm());
                p1_61_data.setLevel(data.getLevel());
                p1_61_data.setSdnn(data.getSdnn());
                p1_61_data.setLf(data.getLf());
                p1_61_data.setHf(data.getHf());
                p1_61_data.setLf_hf(data.getLf_hf());
                p1_61_data.setBpm_hr(data.getBpm_hr());
                p1_61_data.setSbp(data.getSbp());
                p1_61_data.setDbp(data.getDbp());
                p1_61_data.setBpm(data.getBpm());
                p1_61_data.setTime(data.getTime());
                p1_61_data.setCmd(data.getCmd());
                list.add(p1_61_data);
            }
        }
        return list;
    }

    public List<P1_61_data> getSport61Data(long uid, String dataFrom, long start, long endTime, int sportType, boolean isAll) {
        KLog.e(Long.valueOf(start));
        try {
            start = afreshStartTime(uid, dataFrom, start / 1000) * 1000;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        ArrayList<TB_61_data> arrayList = new ArrayList<>();
        DateUtil date1 = new DateUtil(start, false);
        KLog.e(uid + "/" + dataFrom + "/" + start + "/" + endTime + "/" + sportType + "/" + isAll);
        TB_61_data start61 = (TB_61_data) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and hour=? and min=? and reserve=?", uid + "", dataFrom, date1.getYear() + "", date1.getMonth() + "", date1.getDay() + "", date1.getHour() + "", date1.getMinute() + "", date1.getSecond() + "").findFirst(TB_61_data.class);
        DateUtil date2 = new DateUtil(endTime, false);
        TB_61_data end61 = (TB_61_data) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and hour=? and min=? and reserve=?", uid + "", dataFrom, date2.getYear() + "", date2.getMonth() + "", date2.getDay() + "", date2.getHour() + "", date2.getMinute() + "", date2.getSecond() + "").findFirst(TB_61_data.class);
        KLog.e("start61" + JsonTool.toJson(start61));
        KLog.e("end61" + JsonTool.toJson(end61));
        List<TB_61_data> myDatas = new ArrayList<>();
        if (start61 == null || end61 == null) {
            myDatas.addAll(DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, (1000 * start) + "", ((1000 * endTime) + 999) + "").order("time asc").find(TB_61_data.class));
            DateUtil date = new DateUtil((start / 60) * 60, true);
            TB_61_data data = (TB_61_data) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and hour=? and min=? and state_type=? and sport_type=?", uid + "", dataFrom, date.getYear() + "", date.getMonth() + "", date.getDay() + "", date.getHour() + "", date.getMinute() + "", "4", sportType + "").findFirst(TB_61_data.class);
            if (!(arrayList == null || data == null)) {
                if (arrayList.size() > 1) {
                    arrayList.add(1, data);
                } else {
                    arrayList.add(data);
                }
            }
        } else {
            if (end61.getSeq() < start61.getSeq()) {
                myDatas.addAll(DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and seq>=?", uid + "", dataFrom, date1.getYear() + "", date1.getMonth() + "", date1.getDay() + "", start61.getSeq() + "").order("seq asc").find(TB_61_data.class));
                myDatas.addAll(DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and seq<=?", uid + "", dataFrom, date2.getYear() + "", date2.getMonth() + "", date2.getDay() + "", end61.getSeq() + "").order("seq asc").find(TB_61_data.class));
            } else {
                myDatas.addAll(DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and seq>=? and seq<=?", uid + "", dataFrom, date2.getYear() + "", date2.getMonth() + "", date2.getDay() + "", start61.getSeq() + "", end61.getSeq() + "").order("seq asc").find(TB_61_data.class));
            }
        }
        if (isAll) {
            arrayList.addAll(myDatas);
        } else {
            for (int i = 0; i < myDatas.size(); i++) {
                if (((TB_61_data) myDatas.get(i)).getState_type() != 3) {
                    arrayList.add(myDatas.get(i));
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (TB_61_data data2 : arrayList) {
            arrayList2.add(JsonTool.fromJson(JsonTool.toJson(data2), P1_61_data.class));
        }
        return arrayList2;
    }

    public int get61SportPauseTime(long uid, String dataFrom, long start, long endTime, int sportType) {
        List<TB_61_data> allDatas = DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, start + "", (((endTime / 1000) * 1000) + 999) + "").order("seq asc").find(TB_61_data.class);
        List<TB_61_data> tb_61_datas = new ArrayList<>();
        if (allDatas.size() <= 1) {
            tb_61_datas.addAll(allDatas);
        } else if (((TB_61_data) allDatas.get(allDatas.size() - 1)).getSeq() - ((TB_61_data) allDatas.get(0)).getSeq() >= 4000) {
            int cut = 0;
            int i = 1;
            while (true) {
                if (i >= allDatas.size()) {
                    break;
                } else if (((TB_61_data) allDatas.get(i)).getSeq() - ((TB_61_data) allDatas.get(i - 1)).getSeq() >= 2000) {
                    cut = i;
                    break;
                } else {
                    i++;
                }
            }
            List<TB_61_data> firstDatas = new ArrayList<>();
            List<TB_61_data> twoDatas = new ArrayList<>();
            for (int i2 = 0; i2 < allDatas.size(); i2++) {
                if (i2 < cut) {
                    firstDatas.add(allDatas.get(i2));
                } else {
                    twoDatas.add(allDatas.get(i2));
                }
            }
            twoDatas.addAll(firstDatas);
            tb_61_datas.addAll(twoDatas);
        } else {
            tb_61_datas.addAll(allDatas);
        }
        boolean pause = false;
        int pauseTime = 0;
        long pauseUt = 0;
        for (int i3 = 0; i3 < tb_61_datas.size(); i3++) {
            if (((TB_61_data) tb_61_datas.get(i3)).getState_type() == 3 && !pause && sportType == ((TB_61_data) tb_61_datas.get(i3)).getSport_type()) {
                pauseUt = ((TB_61_data) tb_61_datas.get(i3)).getTime() / 1000;
                pause = true;
            }
            if (!(!pause || ((TB_61_data) tb_61_datas.get(i3)).getState_type() == 3 || ((TB_61_data) tb_61_datas.get(i3)).getState_type() == 0)) {
                pauseTime += (int) ((((TB_61_data) tb_61_datas.get(i3)).getTime() / 1000) - pauseUt);
                pause = false;
            }
        }
        KLog.e("licl", "pauseTime-->" + pauseTime);
        return pauseTime;
    }

    public static long afreshStartTime(long uid, String dataFrom, long oldStart) {
        DateUtil dateUtil = new DateUtil(oldStart, true);
        long start = oldStart;
        if (DataSupport.isExist(TB_61_data.class, "uid=? and data_from=? and year=? and month=? and day=? and hour=? and min=? and state_type=?", uid + "", dataFrom + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", dateUtil.getHour() + "", dateUtil.getMinute() + "", "1")) {
            return start;
        }
        for (int i = 1; i <= 10; i++) {
            DateUtil newDate = new DateUtil(((long) (i * 60)) + start, true);
            if (DataSupport.isExist(TB_61_data.class, "uid=? and data_from=? and year=? and month=? and day=? and hour=? and min=? and state_type=? and automatic>?", uid + "", dataFrom, newDate.getYear() + "", newDate.getMonth() + "", newDate.getDay() + "", newDate.getHour() + "", newDate.getMinute() + "", "1", "0")) {
                return ((TB_61_data) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and hour=? and min=? and state_type=? and automatic>?", uid + "", dataFrom, newDate.getYear() + "", newDate.getMonth() + "", newDate.getDay() + "", newDate.getHour() + "", newDate.getMinute() + "", "1", "0").findFirst(TB_61_data.class)).getTime() / 1000;
            }
        }
        return start;
    }

    public void save61DataFromFile(long uid, String data_from, byte[] data) {
        if (data[2] != 97) {
            KLog.e("非61数据，不能入61表...");
            return;
        }
        Ble61DataParse ble61DataParse = Ble61DataParse.parse(data);
        new DateUtil(ble61DataParse.getYear(), ble61DataParse.getMonth(), ble61DataParse.getDay());
        TB_61_data cmd_61 = new TB_61_data();
        cmd_61.setUid(uid);
        cmd_61.setData_from(data_from);
        cmd_61.setCtrl(ble61DataParse.getCtrl());
        cmd_61.setSeq(ble61DataParse.getSeq());
        cmd_61.setYear(ble61DataParse.getYear());
        cmd_61.setMonth(ble61DataParse.getMonth());
        cmd_61.setDay(ble61DataParse.getDay());
        cmd_61.setHour(ble61DataParse.getHour());
        cmd_61.setMin(ble61DataParse.getMin());
        if (cmd_61.getYear() - 2000 != 255 || cmd_61.getMonth() - 1 != 255 || cmd_61.getDay() - 1 != 255 || cmd_61.getHour() != 255 || cmd_61.getMin() != 255) {
            cmd_61.setTime(new DateUtil(cmd_61.getYear(), cmd_61.getMonth(), cmd_61.getDay(), cmd_61.getHour(), cmd_61.getMin(), ble61DataParse.getReserve()).getTimestamp());
            cmd_61.setData_type(ble61DataParse.getData_type());
            cmd_61.setSport_type(ble61DataParse.getSport_type());
            cmd_61.setCalorie(ble61DataParse.getCalorie());
            cmd_61.setStep(ble61DataParse.getStep());
            cmd_61.setDistance(ble61DataParse.getDistance());
            cmd_61.setState_type(ble61DataParse.getState_type());
            cmd_61.setReserve(ble61DataParse.getReserve());
            cmd_61.setAutomatic(ble61DataParse.getAutomaticMin());
            cmd_61.setMin_bpm(ble61DataParse.getMin_bpm());
            cmd_61.setMax_bpm(ble61DataParse.getMax_bpm());
            cmd_61.setAvg_bpm(ble61DataParse.getAvg_bpm());
            cmd_61.setLevel(ble61DataParse.getLevel());
            cmd_61.setSdnn(ble61DataParse.getSdnn());
            cmd_61.setLf_hf(ble61DataParse.getLf());
            cmd_61.setHf(ble61DataParse.getHf());
            cmd_61.setLf_hf(ble61DataParse.getLf_hf());
            cmd_61.setBpm_hr(ble61DataParse.getBpm_hr());
            cmd_61.setSbp(ble61DataParse.getSbp());
            cmd_61.setDbp(ble61DataParse.getDbp());
            cmd_61.setBpm(ble61DataParse.getBpm());
            cmd_61.setCmd(Utils.bytesToHexString(data));
            KLog.i("ble61DataParse" + ble61DataParse.toString());
            cmd_61.saveOrUpdate("uid=? and data_from=? and seq=? and cmd=?", uid + "", data_from + "", ble61DataParse.getSeq() + "", cmd_61.getCmd() + "");
        }
    }

    public List<HomeTrandingTodayData> getTodaySports(long newUID, String device, DateUtil dateUtil) {
        List<TB_v3_sport_data> tb_v3_sport_datas = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=? ", newUID + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", device + "").find(TB_v3_sport_data.class);
        Iterator<TB_v3_sport_data> iterator = tb_v3_sport_datas.iterator();
        while (iterator.hasNext()) {
            TB_v3_sport_data next = (TB_v3_sport_data) iterator.next();
            if (((long) ((next.getEnd_time() - next.getStart_time()) / 60000)) < 5) {
                iterator.remove();
            }
        }
        KLog.e(tb_v3_sport_datas);
        List<HomeTrandingTodayData> homeTrandingTodayDataList = new ArrayList<>();
        for (TB_v3_sport_data data : tb_v3_sport_datas) {
            HomeTrandingTodayData homeTrandingTodayData = new HomeTrandingTodayData();
            homeTrandingTodayData.startTime = (long) data.getStart_time();
            homeTrandingTodayData.endTime = (long) data.getEnd_time();
            homeTrandingTodayData.type = data.getSport_type();
            homeTrandingTodayDataList.add(homeTrandingTodayData);
        }
        return homeTrandingTodayDataList;
    }

    public void let61To28(long uid, int year, int month, int day, String data_from) {
        MtkToIvHandler.mtk61DataToHeart(uid, year, month, day, data_from, MtkToIvHandler.sort61DataBySeq(uid, year, month, day, data_from));
    }

    public void saveGps2SportTB(CopySportGps copySportGps) {
        TB_sport_gps_segment oneGps = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and source_type=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getSource_type() + "", copySportGps.getData_from()).findFirst(TB_sport_gps_segment.class);
        String heartUrl = copySportGps.getHeart_url();
        String r1Url = copySportGps.getR1_url();
        String gpsUrl = copySportGps.getGps_url();
        int upload = 0;
        int urlType = 0;
        if (oneGps != null) {
            if (!TextUtils.isEmpty(oneGps.getHeart_url())) {
                heartUrl = oneGps.getHeart_url();
            } else if (copySportGps.getData_from().toUpperCase(Locale.US).contains("VOICE")) {
                heartUrl = "1";
            }
            if (!TextUtils.isEmpty(oneGps.getR1_url())) {
                r1Url = oneGps.getR1_url();
            } else if (copySportGps.getData_from().toUpperCase(Locale.US).contains("VOICE")) {
                r1Url = "1";
            }
            if (!TextUtils.isEmpty(oneGps.getGps_url())) {
                gpsUrl = oneGps.getGps_url();
            }
            upload = oneGps.getUpload();
            urlType = oneGps.getUrl_type();
        }
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
        gps_segment.setUrl_type(urlType);
        gps_segment.setMtime(new DateUtil(gps_segment.getStart_time(), true).getY_M_D_H_M_S());
        gps_segment.saveOrUpdate("uid=? and start_time=? and source_type=? and data_from=?", copySportGps.getUid() + "", copySportGps.getStart_time() + "", copySportGps.getSource_type() + "", copySportGps.getData_from());
        KLog.e("no2855--> 入表失败 或 成功???-->");
    }

    public List<CopySportGps> getSportHistory(long uid, long startTime, int count, int cardType, int sportType) {
        return new SportListFactory().getExecutor(cardType).getCopySportGpsList(uid, startTime, count, sportType);
    }

    public CopySportGps getSportOne(long uid, long startTime, int cardType, int sportType, String dataFrom) {
        return new SportFactory().getExecutor(cardType).getCopySportGps(uid, startTime, dataFrom, sportType);
    }

    public List<LongitudeAndLatitude> getDeviceLocation(long uid, long startTime, long endTime, String dataFrom) {
        List<TB_blue_gps> blueGpsList = DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, startTime + "", endTime + "").order("time asc").find(TB_blue_gps.class);
        List<LongitudeAndLatitude> latitudes = new LinkedList<>();
        if (blueGpsList != null && blueGpsList.size() > 0) {
            KLog.e("no2855--> 获取到blue的size: " + blueGpsList.size());
            for (TB_blue_gps blueGps : blueGpsList) {
                LongitudeAndLatitude andLatitude = new LongitudeAndLatitude();
                andLatitude.setLatitude(blueGps.getLat());
                andLatitude.setLongitude(blueGps.getLon());
                latitudes.add(andLatitude);
            }
        }
        return latitudes;
    }

    public void updateTBGpsSegmentUrl(String url, int upUrlType, long uid, long startTime, String dataFrom, int dataType, boolean isCheck) {
        DataUtil.upGpsSportOneUrl(url, upUrlType, uid, startTime, dataFrom, dataType);
        SportDeviceNetWorkUtil.uploadTbGpsSegment(uid, startTime, dataFrom, isCheck);
    }

    public void saveSportGpsTBFromNet(SportGpsCode sportGpsCode) {
        if (sportGpsCode != null && sportGpsCode.getData() != null && sportGpsCode.getData().size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (GpsCode gpsCode : sportGpsCode.getData()) {
                long time2 = 0;
                long edTime = 0;
                try {
                    time2 = sdf.parse(gpsCode.getStart_time()).getTime() / 1000;
                    edTime = sdf.parse(gpsCode.getEnd_time()).getTime() / 1000;
                } catch (ParseException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (time2 != 0) {
                    if (UserConst.PHONE.equals(gpsCode.getData_from())) {
                        if (gpsCode.getSource_type() == 1) {
                            gpsCode.setData_from("iPhone");
                        } else {
                            gpsCode.setData_from("Android");
                        }
                    }
                    if (time2 != 0) {
                        TB_sport_gps_segment mSegment = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and data_from=?", gpsCode.getUid() + "", time2 + "", gpsCode.getData_from()).findFirst(TB_sport_gps_segment.class);
                        if (mSegment == null) {
                            TB_sport_gps_segment segment = new TB_sport_gps_segment();
                            segment.setUpload(1);
                            segment.setGps_url(gpsCode.getGps_data_url());
                            segment.setR1_url(gpsCode.getHeadset_data_url());
                            segment.setHeart_url(gpsCode.getHr_data_url());
                            segment.setUrl_type(0);
                            segment.setSport_type(gpsCode.getSport_type());
                            segment.setStep(gpsCode.getStep());
                            segment.setEnd_time(edTime);
                            segment.setStart_time(time2);
                            segment.setDistance(gpsCode.getDistance());
                            segment.setDuration(gpsCode.getDuration());
                            segment.setData_from(gpsCode.getData_from());
                            segment.setCalorie(gpsCode.getCalorie());
                            segment.setUid(gpsCode.getUid());
                            segment.setSource_type(gpsCode.getSource_type());
                            segment.setMtime(new DateUtil(segment.getStart_time(), true).getY_M_D_H_M_S());
                            segment.save();
                        } else if (mSegment.getUpload() == 0) {
                            mSegment.setUpload(1);
                            mSegment.setUrl_type(0);
                            if (!TextUtils.isEmpty(gpsCode.getGps_data_url())) {
                                mSegment.setGps_url(gpsCode.getGps_data_url());
                            } else if ("1".equals(mSegment.getGps_url())) {
                                mSegment.setUrl_type(1);
                            }
                            if (!TextUtils.isEmpty(gpsCode.getHr_data_url())) {
                                mSegment.setHeart_url(gpsCode.getHr_data_url());
                            } else if ("1".equals(mSegment.getHeart_url())) {
                                mSegment.setUrl_type(mSegment.getUrl_type() | 2);
                            }
                            if (!TextUtils.isEmpty(gpsCode.getHeadset_data_url())) {
                                mSegment.setR1_url(gpsCode.getHeadset_data_url());
                            } else if ("1".equals(mSegment.getR1_url())) {
                                mSegment.setUrl_type(mSegment.getUrl_type() | 4);
                            }
                            if (UserConst.PHONE.equals(gpsCode.getData_from())) {
                                if (gpsCode.getSource_type() == 1) {
                                    mSegment.setData_from("iPhone");
                                } else {
                                    mSegment.setData_from("Android");
                                }
                            }
                            mSegment.update(mSegment.getId());
                        }
                    }
                }
            }
        }
    }

    public void saveSportBallTBFromNet(SportBallCode sportGpsCode) {
        if (sportGpsCode != null && sportGpsCode.getData() != null && sportGpsCode.getData().size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BallCode gpsCode : sportGpsCode.getData()) {
                long time2 = 0;
                long edTime = 0;
                try {
                    time2 = sdf.parse(gpsCode.getStart_time()).getTime() / 1000;
                    edTime = sdf.parse(gpsCode.getEnd_time()).getTime() / 1000;
                } catch (ParseException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (time2 != 0) {
                    TB_sport_ball ball = (TB_sport_ball) DataSupport.where("uid=? and start_time=? and data_from=?", gpsCode.getUid() + "", time2 + "", gpsCode.getData_from()).findFirst(TB_sport_ball.class);
                    if (ball == null) {
                        TB_sport_ball segment = new TB_sport_ball();
                        segment.setUpload_type(1);
                        segment.setHeart_url(gpsCode.getHr_data_url());
                        segment.setSport_type(gpsCode.getSport_type());
                        segment.setEnd_time(edTime);
                        segment.setStart_time(time2);
                        segment.setDuration(gpsCode.getDuration());
                        segment.setData_from(gpsCode.getData_from());
                        segment.setCalorie(gpsCode.getCalorie());
                        segment.setUid(gpsCode.getUid());
                        segment.save();
                    } else if (ball.getUpload_type() == 0) {
                        ball.setUpload_type(1);
                        if (!TextUtils.isEmpty(gpsCode.getHr_data_url())) {
                            ball.setHeart_url(gpsCode.getHr_data_url());
                        } else if ("1".equals(ball.getHeart_url())) {
                            ball.setUpload_type(0);
                        }
                        ball.update(ball.getId());
                    }
                }
            }
        }
    }

    public void saveSportOtherTBFromNet(SportOtherCode sportGpsCode) {
        if (sportGpsCode != null && sportGpsCode.getData() != null && sportGpsCode.getData().size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (OtherCode gpsCode : sportGpsCode.getData()) {
                long time2 = 0;
                long edTime = 0;
                try {
                    time2 = sdf.parse(gpsCode.getStart_time()).getTime() / 1000;
                    edTime = sdf.parse(gpsCode.getEnd_time()).getTime() / 1000;
                } catch (ParseException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (time2 != 0) {
                    TB_sport_other other = (TB_sport_other) DataSupport.where("uid=? and start_time=? and data_from=?", gpsCode.getUid() + "", time2 + "", gpsCode.getData_from()).findFirst(TB_sport_other.class);
                    if (other == null) {
                        TB_sport_other segment = new TB_sport_other();
                        segment.setUpload_type(1);
                        segment.setHeart_url(gpsCode.getHr_data_url());
                        segment.setSport_type(gpsCode.getSport_type());
                        segment.setEnd_time(edTime);
                        segment.setStart_time(time2);
                        segment.setDuration(gpsCode.getDuration());
                        segment.setData_from(gpsCode.getData_from());
                        segment.setCalorie(gpsCode.getCalorie());
                        segment.setUid(gpsCode.getUid());
                        segment.setDone_times(gpsCode.getDone_times());
                        segment.save();
                    } else if (other.getUpload_type() == 0) {
                        other.setUpload_type(1);
                        if (!TextUtils.isEmpty(gpsCode.getHr_data_url())) {
                            other.setHeart_url(gpsCode.getHr_data_url());
                        } else if ("1".equals(other.getHeart_url())) {
                            other.setUpload_type(0);
                        }
                        other.update(other.getId());
                    }
                }
            }
        }
    }

    public void saveSportSwimTBFromNet(SportSwimCode sportGpsCode) {
        if (sportGpsCode != null && sportGpsCode.getData() != null && sportGpsCode.getData().size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (SwimCode gpsCode : sportGpsCode.getData()) {
                long time2 = 0;
                long edTime = 0;
                try {
                    time2 = sdf.parse(gpsCode.getStart_time()).getTime() / 1000;
                    edTime = sdf.parse(gpsCode.getEnd_time()).getTime() / 1000;
                } catch (ParseException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (time2 != 0) {
                    TB_sport_swim swim = (TB_sport_swim) DataSupport.where("uid=? and start_time=? and data_from=?", gpsCode.getUid() + "", time2 + "", gpsCode.getData_from()).findFirst(TB_sport_swim.class);
                    if (swim == null) {
                        TB_sport_swim segment = new TB_sport_swim();
                        segment.setUpload_type(1);
                        segment.setHeart_url(gpsCode.getSwim_detail_data());
                        segment.setSport_type(Opcodes.INT_TO_DOUBLE);
                        segment.setEnd_time(edTime);
                        segment.setStart_time(time2);
                        segment.setDuration(gpsCode.getDuration());
                        segment.setData_from(gpsCode.getData_from());
                        segment.setCalorie(gpsCode.getCalorie());
                        segment.setUid(gpsCode.getUid());
                        segment.setLaps(gpsCode.getSwim_laps());
                        segment.setDone_times(gpsCode.getStroke_times());
                        segment.setDistance(gpsCode.getDistance());
                        segment.save();
                    } else if (swim.getUpload_type() == 0) {
                        swim.setUpload_type(1);
                        if (!TextUtils.isEmpty(gpsCode.getSwim_detail_data())) {
                            swim.setHeart_url(gpsCode.getSwim_detail_data());
                        } else if ("1".equals(swim.getHeart_url())) {
                            swim.setUpload_type(0);
                        }
                        swim.update(swim.getId());
                    }
                }
            }
        }
    }

    public SportTotalData getSportTotalData(long uid) {
        SportTotalData totalData = new SportTotalData();
        List<TB_sport_gps_segment> gpsList = DataSupport.where("uid=? and upload=?", uid + "", "0").find(TB_sport_gps_segment.class);
        List<TB_sport_ball> ballList = DataSupport.where("uid=? and upload_type=?", uid + "", "0").find(TB_sport_ball.class);
        List<TB_sport_other> otherList = DataSupport.where("uid=? and upload_type=?", uid + "", "0").find(TB_sport_other.class);
        List<TB_sport_swim> swimList = DataSupport.where("uid=? and upload_type=?", uid + "", "0").find(TB_sport_swim.class);
        if (gpsList != null && gpsList.size() > 0) {
            for (TB_sport_gps_segment gps_segment : gpsList) {
                if (gps_segment.getSport_type() == 0) {
                    totalData.setRunDistance(totalData.getRunDistance() + gps_segment.getDistance());
                    totalData.setRunDuration(totalData.getRunDuration() + gps_segment.getDuration());
                    totalData.setRunTimes(totalData.getRunTimes() + 1);
                } else if (gps_segment.getSport_type() == 1) {
                    totalData.setBikeDistance(totalData.getBikeDistance() + gps_segment.getDistance());
                    totalData.setBikeDuration(totalData.getBikeDuration() + gps_segment.getDuration());
                    totalData.setBikeTimes(totalData.getBikeTimes() + 1);
                } else if (gps_segment.getSport_type() == 2) {
                    totalData.setWalkDistance(totalData.getWalkDistance() + gps_segment.getDistance());
                    totalData.setWalkDuration(totalData.getWalkDuration() + gps_segment.getDuration());
                    totalData.setWalkTimes(totalData.getWalkTimes() + 1);
                } else if (gps_segment.getSport_type() == 3) {
                    totalData.setClimbDistance(totalData.getClimbDistance() + gps_segment.getDistance());
                    totalData.setClimbDuration(totalData.getClimbDuration() + gps_segment.getDuration());
                    totalData.setClimbTimes(totalData.getClimbTimes() + 1);
                }
            }
        }
        if (ballList != null && ballList.size() > 0) {
            for (TB_sport_ball sport_ball : ballList) {
                totalData.setBallCaliores(totalData.getBallCaliores() + sport_ball.getCalorie());
                totalData.setBallDuiation(totalData.getBallDuiation() + sport_ball.getDuration());
                totalData.setBallTimes(totalData.getBallTimes() + 1);
            }
        }
        if (otherList != null && otherList.size() > 0) {
            for (TB_sport_other tb_sport_other : otherList) {
                totalData.setOtherCaliores(totalData.getOtherCaliores() + tb_sport_other.getCalorie());
                totalData.setOtherDuiation(totalData.getOtherDuiation() + tb_sport_other.getDuration());
                totalData.setOtherTimes(totalData.getOtherTimes() + 1);
            }
        }
        if (swimList != null && swimList.size() > 0) {
            for (TB_sport_swim tb_sport_swim : swimList) {
                totalData.setSwimCaliores(totalData.getSwimCaliores() + tb_sport_swim.getCalorie());
                totalData.setSwimDuiation(totalData.getSwimDuiation() + tb_sport_swim.getDuration());
                totalData.setSwimTimes(totalData.getSwimTimes() + 1);
            }
        }
        return totalData;
    }

    public boolean isP1(String dataFrom) {
        String modelClass = PrefUtil.getString(ContextUtil.app, SharedPreferencesAction.APP_SDK_UPDATE_Content);
        if (TextUtils.isEmpty(modelClass)) {
            modelClass = com.iwown.device_module.common.utils.Utils.getFromAssets(ContextUtil.app, "modesdklist2default.txt");
        }
        List<DataBean> data = ((ModeItems) new Gson().fromJson(modelClass, ModeItems.class)).getData();
        if (data != null && data.size() > 0) {
            for (DataBean dataBean : data) {
                if (dataBean.getClassid() == 2 && BluetoothOperation.filterOk(dataBean.getKeyword(), dataFrom)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void uploadNoUpGps(long uid) {
        SportDeviceNetWorkUtil.uploadNggService(uid);
    }

    public List<BleGpsData> getBleGps(long uid, long startTime, long endTime, String dataFrom) {
        List<TB_blue_gps> blueGpsList = DataSupport.where("uid=? and data_from=? and time>=? and time<=?", uid + "", dataFrom, startTime + "", endTime + "").order("time asc").find(TB_blue_gps.class);
        int size = 0;
        if (blueGpsList != null) {
            size = blueGpsList.size();
        }
        List<BleGpsData> bleGpsDataList = new ArrayList<>(size);
        for (TB_blue_gps blue_gps : blueGpsList) {
            BleGpsData bleGpsData = new BleGpsData();
            bleGpsData.setData_from(blue_gps.getData_from());
            bleGpsData.setTime(blue_gps.getTime());
            bleGpsData.setLat(blue_gps.getLat());
            bleGpsData.setLon(blue_gps.getLon());
            bleGpsData.setUid(blue_gps.getUid());
            bleGpsDataList.add(bleGpsData);
        }
        return bleGpsDataList;
    }

    public void uploadHrFile(boolean isGpsSeg, long uid, long startTime, long endTime, String dataFrom, int sportType) {
        SportDeviceNetWorkUtil.uploadHr(isGpsSeg, uid, startTime, endTime, dataFrom, sportType);
    }

    public void uploadR1File(boolean isGpsSeg, long uid, long startTime, long endTime, String dataFrom, int sportType) {
        SportDeviceNetWorkUtil.updateR1(isGpsSeg, uid, startTime, endTime, dataFrom, sportType);
    }

    public void updateR1AndGpsSegment(long uid, long starTime, String dateFrom) {
        TB_sport_gps_segment tbSportGpsSegment = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and data_from like ?", uid + "", starTime + "", "%" + dateFrom).findFirst(TB_sport_gps_segment.class);
        if (tbSportGpsSegment != null) {
            tbSportGpsSegment.setUrl_type(1);
            tbSportGpsSegment.update(tbSportGpsSegment.getId());
        }
    }

    public List<CopySportGps> getR1AndGpsSegment(long uid, String dateFrom) {
        List<CopySportGps> copySportGpsList = new ArrayList<>();
        List<TB_sport_gps_segment> tbSportGpsSegments = DataSupport.where("uid=? and data_from=?", uid + "", dateFrom).find(TB_sport_gps_segment.class);
        if (tbSportGpsSegments != null) {
            for (TB_sport_gps_segment sportGpsSegment : tbSportGpsSegments) {
                CopySportGps copySportGps = new CopySportGps();
                copySportGps.setStart_time(sportGpsSegment.getStart_time());
                copySportGps.setEnd_time(sportGpsSegment.getEnd_time());
                copySportGps.setData_from(sportGpsSegment.getData_from());
                copySportGpsList.add(copySportGps);
            }
        }
        return copySportGpsList;
    }

    public void saveBleGps(long uid, long time, String dataFrom, double lat, double lon) {
        if (!DataSupport.isExist(TB_blue_gps.class, "uid=? and time=? and data_from=?", uid + "", time + "", dataFrom + "")) {
            TB_blue_gps blue_gps = new TB_blue_gps();
            blue_gps.setLon(lon);
            blue_gps.setLat(lat);
            blue_gps.setTime(time);
            blue_gps.setUid(uid);
            blue_gps.setData_from(dataFrom);
            blue_gps.save();
        }
    }

    public void init(Context context) {
    }
}
