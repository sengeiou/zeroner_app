package com.iwown.device_module.interactive_service;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.sleep_data.ISleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepDownCode;
import com.iwown.data_link.sleep_data.SleepDownData1;
import com.iwown.data_link.sleep_data.SleepDownData2;
import com.iwown.data_link.sleep_data.SleepHistoryData;
import com.iwown.data_link.sleep_data.SleepScoreHandler;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.data_link.sleep_data.SleepUpNewSend;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.sql.sleep.TB_Sleep_Status;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.litepal.crud.DataSupport;

@Route(path = "/device/sleep_service")
public class DeviceSleepDataService implements ISleepService {
    public void init(Context context) {
    }

    public void getSleepData(SleepDataDay sleepDataToday) {
        DateUtil dateUtil = new DateUtil(sleepDataToday.time_unix, true);
        TB_SLEEP_Final_DATA tb_sleep_final_data = null;
        KLog.e("   " + sleepDataToday + "  isBind  " + ModuleRouteDeviceInfoService.getInstance().isBind());
        Date date = new Date();
        Date date2 = new Date(sleepDataToday.time_unix * 1000);
        if (DateUtil.isSameDay(date, date2)) {
            KLog.e(" getSleepData  " + sleepDataToday.data_from);
            tb_sleep_final_data = (TB_SLEEP_Final_DATA) DataSupport.where("date=? and uid=? and data_from=?", dateUtil.getSyyyyMMddDate(), sleepDataToday.uid + "", sleepDataToday.data_from + "").findFirst(TB_SLEEP_Final_DATA.class);
        } else {
            if (ModuleRouteDeviceInfoService.getInstance().isBind()) {
                tb_sleep_final_data = (TB_SLEEP_Final_DATA) DataSupport.where("date=? and uid=? and data_from=?", dateUtil.getSyyyyMMddDate(), sleepDataToday.uid + "", sleepDataToday.data_from + "").findFirst(TB_SLEEP_Final_DATA.class);
            }
            if (tb_sleep_final_data == null) {
                List<TB_SLEEP_Final_DATA> lists = DataSupport.where("date=? and uid=? and data_from not in(?,?)", dateUtil.getSyyyyMMddDate(), sleepDataToday.uid + "", "Android", "iOS").find(TB_SLEEP_Final_DATA.class);
                KLog.e("  lists " + lists);
                if (lists.size() != 0) {
                    int max_time = 0;
                    boolean isShowContains = false;
                    Iterator it = lists.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (((TB_SLEEP_Final_DATA) it.next()).getData_from().equals(sleepDataToday.data_from)) {
                                isShowContains = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (!isShowContains) {
                        tb_sleep_final_data = (TB_SLEEP_Final_DATA) lists.get(0);
                        for (TB_SLEEP_Final_DATA tb_sleep_final_data1 : lists) {
                            int temp_max_time = (int) ((tb_sleep_final_data1.getEnd_time() - tb_sleep_final_data1.getStart_time()) / 60);
                            if (temp_max_time > max_time) {
                                tb_sleep_final_data = tb_sleep_final_data1;
                                max_time = temp_max_time;
                                sleepDataToday.data_from = tb_sleep_final_data1.getData_from();
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
        if (tb_sleep_final_data == null) {
            KLog.e("tb_sleep_final_data " + tb_sleep_final_data);
            return;
        }
        try {
            SleepDownData1 sleepDownData1 = new SleepDownData1();
            if (tb_sleep_final_data.getScore() == 0) {
                int i = SleepScoreHandler.calSleepScore((int) ((tb_sleep_final_data.getEnd_time() - tb_sleep_final_data.getStart_time()) / 60), (int) tb_sleep_final_data.getDeepSleepTime(), tb_sleep_final_data.getStart_time());
                if (i != 0) {
                    tb_sleep_final_data.setScore(i);
                    tb_sleep_final_data.setUpload(0);
                    tb_sleep_final_data.save();
                    KLog.e("  " + tb_sleep_final_data.getScore());
                }
            }
            sleepDownData1.setUid(sleepDataToday.uid);
            sleepDownData1.setEnd_time(tb_sleep_final_data.getEnd_time());
            sleepDownData1.setStart_time(tb_sleep_final_data.getStart_time());
            sleepDownData1.setData_from(tb_sleep_final_data.getData_from());
            sleepDownData1.setDeep_time(tb_sleep_final_data.getDeepSleepTime());
            sleepDownData1.setLight_time(tb_sleep_final_data.getLightSleepTime());
            sleepDownData1.setFeel_type(tb_sleep_final_data.getFeel_type());
            sleepDownData1.setScore(tb_sleep_final_data.getScore());
            sleepDownData1.setAction(tb_sleep_final_data.getAction());
            sleepDataToday.db_id = tb_sleep_final_data.getId();
            try {
                sleepDownData1.setSleep_segment(JsonTool.getListJson(tb_sleep_final_data.getSleep_segment(), SleepDownData2.class));
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            sleepDataToday.sleepDownData1 = sleepDownData1;
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    public void saveSleep(SleepDownCode sleepDownCode, boolean isNet) {
        List<SleepDownData1> list = sleepDownCode.getContent();
        if (list.size() > 0) {
            for (SleepDownData1 sleepDown : list) {
                if (sleepDown.getEnd_time() < sleepDown.getStart_time()) {
                    sleepDown.setEnd_time(sleepDown.getEnd_time() + 86400);
                }
                DateUtil endTime = new DateUtil(sleepDown.getEnd_time(), true);
                if (TextUtils.equals(sleepDown.getData_from(), "Android")) {
                    DataSupport.deleteAll(TB_SLEEP_Final_DATA.class, "uid=? and date=? and data_from=?", sleepDown.getUid() + "", endTime.getSyyyyMMddDate(), sleepDown.getData_from());
                    TB_SLEEP_Final_DATA entity = new TB_SLEEP_Final_DATA();
                    entity.setStart_time(sleepDown.getStart_time());
                    entity.setEnd_time(sleepDown.getEnd_time());
                    entity.setDate(endTime.getSyyyyMMddDate());
                    entity.setYear(endTime.getYear());
                    entity.setMonth(endTime.getMonth());
                    entity.setFeel_type(sleepDown.getFeel_type());
                    entity.setLightSleepTime(sleepDown.getLight_time());
                    entity.setDeepSleepTime(sleepDown.getDeep_time());
                    entity.setUid(sleepDown.getUid());
                    entity.setSleep_segment(new Gson().toJson((Object) sleepDown.getSleep_segment()));
                    entity.setData_from(sleepDown.getData_from());
                    entity.setUpload(isNet ? 1 : 0);
                    entity.saveOrUpdate("uid=? and start_time=?", sleepDown.getUid() + "", sleepDown.getStart_time() + "");
                } else {
                    if (!queryDatabyDate(endTime.getSyyyyMMddDate(), sleepDown.getUid(), sleepDown.getLight_time() + sleepDown.getDeep_time(), sleepDown.getData_from())) {
                        TB_SLEEP_Final_DATA entity2 = new TB_SLEEP_Final_DATA();
                        entity2.setStart_time(sleepDown.getStart_time());
                        entity2.setEnd_time(sleepDown.getEnd_time());
                        entity2.setDate(endTime.getSyyyyMMddDate());
                        entity2.setYear(endTime.getYear());
                        entity2.setMonth(endTime.getMonth());
                        entity2.setFeel_type(sleepDown.getFeel_type());
                        entity2.setLightSleepTime(sleepDown.getLight_time());
                        entity2.setDeepSleepTime(sleepDown.getDeep_time());
                        entity2.setUid(sleepDown.getUid());
                        entity2.setSleep_segment(new Gson().toJson((Object) sleepDown.getSleep_segment()));
                        entity2.setData_from(sleepDown.getData_from());
                        entity2.setUpload(isNet ? 1 : 0);
                        entity2.saveOrUpdate("uid=? and start_time=?", sleepDown.getUid() + "", sleepDown.getStart_time() + "");
                    }
                }
            }
        }
    }

    public void updateFinalSleepUploadStatus(SleepUpNewSend sleepUpNewSend, boolean upload) {
        List<SleepDownData1> list = sleepUpNewSend.getContent();
        if (list.size() > 0) {
            for (SleepDownData1 sleepDown : list) {
                DateUtil endTime = new DateUtil(sleepDown.getEnd_time(), true);
                TB_SLEEP_Final_DATA data = new TB_SLEEP_Final_DATA();
                data.setUpload(1);
                data.updateAll("date=? and uid=? and data_from=?", endTime.getSyyyyMMddDate(), sleepDown.getUid() + "", sleepDown.getData_from() + "");
            }
        }
    }

    public void updateSleepStatusData(List<ContentBean> content) {
        for (ContentBean contentBean : content) {
            DateUtil today = new DateUtil((long) contentBean.getTime(), true);
            today.setHour(0);
            today.setMinute(0);
            today.setSecond(0);
            DateUtil tomorDay = new DateUtil(today.getTimestamp(), false);
            tomorDay.setHour(23);
            tomorDay.setMinute(59);
            tomorDay.setSecond(59);
            TB_Sleep_Status data = (TB_Sleep_Status) DataSupport.where("time>=? and time<=? and uid=? and data_from=?", today.getUnixTimestamp() + "", tomorDay.getUnixTimestamp() + "", contentBean.getUid() + "", contentBean.getData_from() + "").findFirst(TB_Sleep_Status.class);
            if (data == null) {
                data = new TB_Sleep_Status();
                data.setUid(contentBean.getUid());
                data.setData_from(contentBean.getData_from());
            }
            data.setFeel_type(contentBean.getFeel_type());
            data.setTime(contentBean.getTime());
            data.setScore(contentBean.getScore());
            data.setDate(today.getSyyyyMMddDate());
            data.save();
        }
    }

    public Map<String, ContentBean> getStatusDatas(long newUID, DateUtil dateUtil, String device) {
        long firstDayMonth = DateUtil.getFirstDayMonth(new Date(dateUtil.getTimestamp()));
        long lastDayMonth = DateUtil.getLastDayMonth(new Date(dateUtil.getTimestamp()));
        DateUtil firstDate = new DateUtil(firstDayMonth, false);
        firstDate.setHour(0);
        firstDate.setMinute(0);
        firstDate.setSecond(0);
        DateUtil lastDate = new DateUtil(lastDayMonth, false);
        lastDate.setHour(23);
        lastDate.setMinute(59);
        lastDate.setSecond(59);
        Map<String, ContentBean> contentBeans = getSleepRules(newUID, device, firstDate, lastDate);
        KLog.e(contentBeans);
        return contentBeans;
    }

    private Map<String, ContentBean> getSleepRules(long newUID, String device, DateUtil firstDate, DateUtil lastDate) {
        Map<String, ContentBean> contentBeans = new LinkedHashMap<>();
        if (ModuleRouteDeviceInfoService.getInstance().isBind()) {
            for (TB_SLEEP_Final_DATA tb_sleep_status : DataSupport.where("end_time>=? and end_time<=? and uid=? and data_from=?", firstDate.getUnixTimestamp() + "", lastDate.getUnixTimestamp() + "", newUID + "", device).order("end_time asc").find(TB_SLEEP_Final_DATA.class)) {
                ContentBean contentBean = new ContentBean();
                contentBean.setData_from(tb_sleep_status.getData_from());
                contentBean.setFeel_type(tb_sleep_status.getFeel_type());
                contentBean.setScore(tb_sleep_status.getScore());
                contentBean.setTime((int) tb_sleep_status.getEnd_time());
                contentBean.setUid(tb_sleep_status.getUid());
                contentBeans.put(new DateUtil((long) contentBean.getTime(), true).getY_M_D(), contentBean);
            }
        }
        for (TB_SLEEP_Final_DATA tb_sleep_status2 : DataSupport.where("end_time>=? and end_time<=? and uid=? and data_from not in(?,?)", firstDate.getUnixTimestamp() + "", lastDate.getUnixTimestamp() + "", newUID + "", "Android", "iOS").order("end_time asc").find(TB_SLEEP_Final_DATA.class)) {
            DateUtil dateUtil1 = new DateUtil(tb_sleep_status2.getEnd_time(), true);
            ContentBean contentBean1 = (ContentBean) contentBeans.get(dateUtil1.getY_M_D());
            if (contentBean1 == null || ((!ModuleRouteDeviceInfoService.getInstance().isBind() || !TextUtils.equals(contentBean1.getData_from(), device)) && ((int) ((tb_sleep_status2.getEnd_time() - tb_sleep_status2.getStart_time()) / 60)) > contentBean1.getSleep_time_min())) {
                ContentBean contentBean2 = new ContentBean();
                contentBean2.setData_from(tb_sleep_status2.getData_from());
                contentBean2.setFeel_type(tb_sleep_status2.getFeel_type());
                contentBean2.setScore(tb_sleep_status2.getScore());
                contentBean2.setTime((int) tb_sleep_status2.getEnd_time());
                contentBean2.setUid(tb_sleep_status2.getUid());
                contentBean2.setSleep_time_min((int) ((tb_sleep_status2.getEnd_time() - tb_sleep_status2.getStart_time()) / 60));
                contentBeans.put(dateUtil1.getY_M_D(), contentBean2);
            }
        }
        DateUtil dateUtil2 = new DateUtil();
        List<TB_SLEEP_Final_DATA> datas1 = DataSupport.where("date=?  and uid=? and data_from=?", dateUtil2.getSyyyyMMddDate() + "", newUID + "", device).find(TB_SLEEP_Final_DATA.class);
        if (datas1.size() == 0) {
            contentBeans.put(dateUtil2.getY_M_D(), null);
        } else {
            for (TB_SLEEP_Final_DATA tb_sleep_status3 : datas1) {
                ContentBean contentBean3 = new ContentBean();
                contentBean3.setData_from(tb_sleep_status3.getData_from());
                contentBean3.setFeel_type(tb_sleep_status3.getFeel_type());
                contentBean3.setScore(tb_sleep_status3.getScore());
                contentBean3.setTime((int) tb_sleep_status3.getEnd_time());
                contentBean3.setUid(tb_sleep_status3.getUid());
                contentBeans.put(new DateUtil((long) contentBean3.getTime(), true).getY_M_D(), contentBean3);
            }
        }
        return contentBeans;
    }

    public void updateSleepAction(int db_id, List<Integer> actions) {
        TB_SLEEP_Final_DATA first = (TB_SLEEP_Final_DATA) DataSupport.where("id=?", db_id + "").findFirst(TB_SLEEP_Final_DATA.class);
        if (first != null) {
            String s = JsonUtils.toJson(actions);
            if (!TextUtils.equals(s, first.getAction())) {
                KLog.e("updateSleepAction");
                first.setAction(s);
                first.setUpload(0);
                first.save();
            }
        }
    }

    public void updateSleepFeelType(int db_id, int feel_type) {
        TB_SLEEP_Final_DATA first = (TB_SLEEP_Final_DATA) DataSupport.where("id=?", db_id + "").findFirst(TB_SLEEP_Final_DATA.class);
        if (first != null && feel_type != first.getFeel_type()) {
            KLog.e("updateSleepFeelType");
            first.setFeel_type(feel_type);
            first.setUpload(0);
            first.save();
        }
    }

    public void getDaySleepByDataFrom(SleepDataDay sleepDataDay) {
        DateUtil toDay = new DateUtil(sleepDataDay.time_unix, true);
        toDay.setHour(18);
        toDay.setMinute(0);
        toDay.setSecond(0);
        DateUtil yesDay = new DateUtil(toDay.getUnixTimestamp(), true);
        yesDay.addDay(-1);
        yesDay.setHour(18);
        yesDay.setMinute(0);
        yesDay.setSecond(0);
        KLog.e(sleepDataDay + "  " + yesDay.getUnixTimestamp() + "  " + toDay.getUnixTimestamp());
        TB_SLEEP_Final_DATA tb_sleep_final_data = (TB_SLEEP_Final_DATA) DataSupport.where("start_time>=? and end_time <? and uid=? and data_from=?", yesDay.getUnixTimestamp() + "", toDay.getUnixTimestamp() + "", sleepDataDay.uid + "", sleepDataDay.data_from + "").findFirst(TB_SLEEP_Final_DATA.class);
        KLog.e(tb_sleep_final_data);
        if (tb_sleep_final_data != null) {
            try {
                SleepDownData1 sleepDownData1 = new SleepDownData1();
                if (tb_sleep_final_data.getScore() == 0) {
                    int i = SleepScoreHandler.calSleepScore((int) ((tb_sleep_final_data.getEnd_time() - tb_sleep_final_data.getStart_time()) / 60), (int) tb_sleep_final_data.getDeepSleepTime(), tb_sleep_final_data.getStart_time());
                    if (i != 0) {
                        tb_sleep_final_data.setScore(i);
                        tb_sleep_final_data.setUpload(0);
                        tb_sleep_final_data.save();
                        KLog.e("  " + tb_sleep_final_data.getScore());
                    }
                }
                sleepDownData1.setUid(sleepDataDay.uid);
                sleepDownData1.setEnd_time(tb_sleep_final_data.getEnd_time());
                sleepDownData1.setStart_time(tb_sleep_final_data.getStart_time());
                sleepDownData1.setData_from(tb_sleep_final_data.getData_from());
                sleepDownData1.setDeep_time(tb_sleep_final_data.getDeepSleepTime());
                sleepDownData1.setLight_time(tb_sleep_final_data.getLightSleepTime());
                sleepDownData1.setFeel_type(tb_sleep_final_data.getFeel_type());
                sleepDownData1.setScore(tb_sleep_final_data.getScore());
                sleepDownData1.setAction(tb_sleep_final_data.getAction());
                sleepDataDay.db_id = tb_sleep_final_data.getId();
                try {
                    sleepDownData1.setSleep_segment(JsonTool.getListJson(tb_sleep_final_data.getSleep_segment(), SleepDownData2.class));
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                sleepDataDay.sleepDownData1 = sleepDownData1;
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
    }

    public boolean isExitSleepData(long uid) {
        if (DataSupport.where("uid=?", uid + "").count(TB_SLEEP_Final_DATA.class) == 0) {
            return false;
        }
        return true;
    }

    public SleepUpNewSend getUnUploadSleepDatas(long newUID) {
        List<TB_SLEEP_Final_DATA> datas = DataSupport.where("uid=?  and upload=0", newUID + "").find(TB_SLEEP_Final_DATA.class);
        SleepUpNewSend sleepUpNewSend = new SleepUpNewSend();
        sleepUpNewSend.setUid(newUID);
        List<SleepDownData1> sleepDownData1List = new ArrayList<>();
        for (TB_SLEEP_Final_DATA tb_sleep_final_data : datas) {
            SleepDownData1 sleepDownData1 = new SleepDownData1();
            sleepDownData1.setUid(newUID);
            sleepDownData1.setScore(tb_sleep_final_data.getScore());
            sleepDownData1.setLight_time(tb_sleep_final_data.getLightSleepTime());
            sleepDownData1.setDeep_time(tb_sleep_final_data.getDeepSleepTime());
            sleepDownData1.setData_from(tb_sleep_final_data.getData_from());
            sleepDownData1.setStart_time(tb_sleep_final_data.getStart_time());
            sleepDownData1.setEnd_time(tb_sleep_final_data.getEnd_time());
            sleepDownData1.setAction(tb_sleep_final_data.getAction());
            sleepDownData1.setFeel_type(tb_sleep_final_data.getFeel_type());
            try {
                sleepDownData1.setSleep_segment(JsonUtils.getListJson(tb_sleep_final_data.getSleep_segment(), SleepDownData2.class));
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            sleepDownData1List.add(sleepDownData1);
        }
        sleepUpNewSend.setContent(sleepDownData1List);
        return sleepUpNewSend;
    }

    public List<SleepHistoryData> getStartEndSleeps(long uid, String data_from, DateUtil startDate, DateUtil endDate) {
        DateUtil dateUtil = new DateUtil(startDate.getTimestamp(), false);
        DateUtil endDateUtil = new DateUtil(endDate.getTimestamp(), false);
        KLog.e(dateUtil.getY_M_D_H_M() + " -> " + endDateUtil.getY_M_D_H_M());
        dateUtil.setHour(18);
        dateUtil.setMinute(0);
        dateUtil.setSecond(0);
        long unixTimestamp = dateUtil.getUnixTimestamp();
        endDateUtil.setHour(18);
        endDateUtil.setMinute(0);
        endDateUtil.setSecond(0);
        List<TB_SLEEP_Final_DATA> datas = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        Map<String, TB_SLEEP_Final_DATA> contentBeans = new LinkedHashMap<>();
        for (TB_SLEEP_Final_DATA tb_sleep_status : DataSupport.where("end_time>=? and end_time<=? and uid=? and data_from not in(?,?)", dateUtil.getZeroTime() + "", endDateUtil.getUnixTimestamp() + "", uid + "", "Android", "iOS").order("end_time asc").find(TB_SLEEP_Final_DATA.class)) {
            DateUtil dateUtil1 = new DateUtil(tb_sleep_status.getEnd_time(), true);
            TB_SLEEP_Final_DATA tb_sleep_final_data = (TB_SLEEP_Final_DATA) contentBeans.get(dateUtil1.getY_M_D());
            if (tb_sleep_final_data == null || ((int) ((tb_sleep_status.getEnd_time() - tb_sleep_status.getStart_time()) / 60)) > ((int) ((tb_sleep_final_data.getEnd_time() - tb_sleep_final_data.getStart_time()) / 60))) {
                contentBeans.put(dateUtil1.getY_M_D(), tb_sleep_status);
            }
        }
        for (TB_SLEEP_Final_DATA tb_sleep_status2 : DataSupport.where("date=?  and uid=? and data_from=?", new DateUtil().getSyyyyMMddDate() + "", uid + "", data_from).find(TB_SLEEP_Final_DATA.class)) {
            contentBeans.put(new DateUtil(tb_sleep_status2.getEnd_time(), true).getY_M_D(), tb_sleep_status2);
        }
        for (String key : contentBeans.keySet()) {
            datas.add(contentBeans.get(key));
        }
        KLog.e(uid + "  " + data_from + "    " + datas.size());
        for (TB_SLEEP_Final_DATA data : datas) {
            if (data == null || TextUtils.isEmpty(data.getSleep_segment())) {
                KLog.e(" data sleep segmnt is null");
            } else {
                SleepHistoryData sleepHistoryData = new SleepHistoryData();
                sleepHistoryData.deepTime = (int) data.getDeepSleepTime();
                sleepHistoryData.lightTime = (int) data.getLightSleepTime();
                sleepHistoryData.startTime = data.getStart_time();
                sleepHistoryData.endTime = data.getEnd_time();
                sleepHistoryData.totalTime = (int) ((data.getEnd_time() - data.getStart_time()) / 60);
                if ((sleepHistoryData.totalTime - sleepHistoryData.deepTime) - sleepHistoryData.lightTime < 0) {
                    KLog.e("total time < deep+light");
                } else {
                    DateUtil dateUtil2 = new DateUtil(data.getEnd_time(), true);
                    sleepHistoryData.time_str = dateUtil2.getMonth() + "/" + dateUtil2.getDay();
                    sleepHistoryData.score = data.getScore();
                    if (data.getScore() == 0) {
                        int i = SleepScoreHandler.calSleepScore((int) ((data.getEnd_time() - data.getStart_time()) / 60), (int) data.getDeepSleepTime(), data.getStart_time());
                        if (i != 0) {
                            data.setScore(i);
                            data.setUpload(0);
                            data.save();
                            KLog.e(" 更新score " + data.getScore());
                        }
                    }
                    arrayList.add(sleepHistoryData);
                }
            }
        }
        return arrayList;
    }

    public boolean queryDatabyDate(String date, long uid, float totalTime, String dataFrom) {
        try {
            List<TB_SLEEP_Final_DATA> data = DataSupport.where("date=? and uid=? and data_from=?", date, uid + "", dataFrom).find(TB_SLEEP_Final_DATA.class);
            if (data.size() <= 0) {
                return false;
            }
            if (totalTime <= ((TB_SLEEP_Final_DATA) data.get(0)).getDeepSleepTime() + ((TB_SLEEP_Final_DATA) data.get(0)).getLightSleepTime()) {
                return true;
            }
            DataSupport.deleteAll(TB_SLEEP_Final_DATA.class, "uid=? and date=? and data_from=?", uid + "", date, dataFrom);
            return false;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return true;
        }
    }
}
