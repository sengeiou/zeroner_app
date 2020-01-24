package com.iwown.sport_module.model;

import android.text.TextUtils;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.Sport28Code;
import com.iwown.data_link.sport_data.V3_sport_data;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.R;
import com.iwown.sport_module.contract.ActiveTodayContract.ActiveTodayModel;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.exception.ServerException;
import com.iwown.sport_module.net.response.MonthHas28DateCode.RspInfoModel;
import com.iwown.sport_module.net.response.Sport28MonthCode;
import com.iwown.sport_module.pojo.active.SportAllData;
import com.iwown.sport_module.pojo.active.SportDetailsData;
import com.iwown.sport_module.sql.TB_has28Days_Monthly_item;
import com.iwown.sport_module.sql.TB_has28Days_monthly;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class ActiveTodayModelImpl implements ActiveTodayModel {
    private static int STAND_JUDGE_CALORIES = 7;
    private static int STAND_JUDGE_STEPS = 250;
    /* access modifiers changed from: private */
    public int DEFAULT_DAY = 1;
    /* access modifiers changed from: private */
    public int DEFAULT_MON = 10;
    /* access modifiers changed from: private */
    public int DEFAULT_YEAR = 1949;
    private String TAG = getClass().getSimpleName();
    DecimalFormat decimalFormat2 = new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.US));
    public DateUtil hasLoadDateFromNet28 = new DateUtil(Calendar.getInstance().getTimeInMillis(), false);
    public DateUtil hasLoadDateFromNet29 = new DateUtil(Calendar.getInstance().getTimeInMillis(), false);
    /* access modifiers changed from: private */
    public HashMap<String, SportAllData> mSportAllDataHashMap = new HashMap<>();
    /* access modifiers changed from: private */
    public LoadCallback<SportAllData> myCallback;
    public DateUtil nowLoadDate28 = new DateUtil(this.DEFAULT_YEAR, this.DEFAULT_MON, this.DEFAULT_DAY);
    public DateUtil nowLoadDate29 = new DateUtil(this.DEFAULT_YEAR, this.DEFAULT_MON, this.DEFAULT_DAY);

    public interface LoadCallback<T> {
        void month28DataArrive(int i, int i2);

        void onFail(Throwable th, int i, int i2, int i3, SportAllData sportAllData);

        void onSuccess(T t);

        void startProcess();
    }

    public void setMyCallback(LoadCallback<SportAllData> myCallback2) {
        this.myCallback = myCallback2;
    }

    public SportAllData getAllData(long uid, int year, int month, int day, String dataFrom, boolean needNet) {
        return get28HistoryMonthly(uid, year, month, day, dataFrom, needNet);
    }

    public SportAllData get28HistoryMonthly(long uid, int year, int month, int day, String dataFrom, boolean needNetData) {
        List<TB_has28Days_Monthly_item> contentList;
        SportAllData allData;
        DateUtil dateUtil = new DateUtil(year, month, day);
        long unix_time = dateUtil.getUnixTimestamp();
        if (dateUtil.isToday()) {
            return getDataForShow(uid, year, month, day, dataFrom, needNetData);
        }
        TB_has28Days_monthly data_monthly = (TB_has28Days_monthly) DataSupport.where("uid=? and year=? and month=?", uid + "", year + "", month + "").findFirst(TB_has28Days_monthly.class);
        KLog.e(JsonTool.toJson(data_monthly));
        if (data_monthly == null) {
            KLog.e(this.TAG, "TB_has28Days_monthly没查到数据，去网络请求");
            get28HistoryMonthlyFromNet(uid, year, month, day, dataFrom, needNetData);
            if (this.myCallback != null) {
                this.myCallback.startProcess();
            }
        } else {
            List<V3_sport_data> sport = ModuleRouteSportService.getInstance().getSport(uid, year, month, day, dataFrom);
            if (sport == null || sport.size() <= 0) {
                if (TextUtils.isEmpty(data_monthly.getInfo())) {
                    contentList = new ArrayList<>();
                } else {
                    contentList = JsonTool.getListJson(data_monthly.getInfo(), TB_has28Days_Monthly_item.class);
                }
                for (TB_has28Days_Monthly_item content : contentList) {
                    String[] strs = content.getDate().split(HelpFormatter.DEFAULT_OPT_PREFIX);
                    if (dateUtil.isSameDay(new DateUtil(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2])).getTimestamp(), false)) {
                        KLog.e(this.TAG, "TB_has28Days_monthly有今天的28-->去获取28具体数据吧" + year + "/" + month + "/" + day);
                        SportAllData allData2 = getDataForShow(uid, year, month, day, content.getFrom() + "", needNetData);
                        if (allData2 == null || !allData2.isReal_has_data() || this.myCallback == null) {
                            return allData2;
                        }
                        this.myCallback.onSuccess(allData2);
                        return allData2;
                    }
                }
                List<V3_sport_data> v3_sport_datas = ModuleRouteSportService.getInstance().get28SportNoDataFrom(uid, year, month, day);
                V3_walk v3_walk = ModuleRouteWalkService.getInstance().get29Walk(uid, unix_time);
                if (dateUtil.daysBetweenMe(new DateUtil()) <= 20 && (v3_sport_datas != null || v3_walk != null)) {
                    KLog.e(this.TAG, "有这天的28数据-->去获取28吧" + year + "/" + month + "/" + day);
                    if (v3_sport_datas != null) {
                        allData = getDataForShow(uid, year, month, day, ((V3_sport_data) v3_sport_datas.get(0)).getData_from() + "", needNetData);
                    } else {
                        allData = new SportAllData(year, month, day, "", true);
                    }
                    if (allData == null || !allData.isReal_has_data() || this.myCallback == null) {
                        return allData;
                    }
                    this.myCallback.onSuccess(allData);
                    return allData;
                } else if (0 == 0) {
                    KLog.e(this.TAG, "没有这天的28-->" + year + "/" + month + "/" + day);
                    SportAllData allData3 = new SportAllData(year, month, day, "----", true);
                    if (this.myCallback != null) {
                        this.myCallback.onSuccess(allData3);
                    }
                    this.mSportAllDataHashMap.put(uid + allData3.getDateStr() + dataFrom, allData3);
                    return allData3;
                }
            } else {
                SportAllData allData4 = getDataForShow(uid, year, month, day, dataFrom, needNetData);
                if (allData4 == null || !allData4.isReal_has_data() || this.myCallback == null) {
                    return allData4;
                }
                this.myCallback.onSuccess(allData4);
                return allData4;
            }
        }
        return new SportAllData(year, month, day, dataFrom, false);
    }

    private void get28HistoryMonthlyFromNet(long uid, int year, int month, int day, String dataFrom, boolean needNetData) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int actualMaximum = cal.getActualMaximum(5);
        DateUtil dateUtil = new DateUtil(year, month, 1);
        if (this.nowLoadDate29.isSameMonth(month, year)) {
            KLog.e(this.TAG, "服务器请求28月数据被拦截-->" + year + "/" + month + "/" + day);
            return;
        }
        this.nowLoadDate29.setYear(year);
        this.nowLoadDate29.setMonth(month);
        this.nowLoadDate29.setDay(day);
        if (dateUtil.getTimestamp() < this.hasLoadDateFromNet29.getTimestamp()) {
            KLog.e(this.TAG, "去服务器请求28月数据-->" + year + "/" + month + "/" + day);
            final int i = year;
            final int i2 = month;
            final long j = uid;
            final int i3 = day;
            final String str = dataFrom;
            final boolean z = needNetData;
            NetFactory.getInstance().getClient(new MyCallback<Sport28MonthCode>() {
                public void onSuccess(Sport28MonthCode code) {
                    ActiveTodayModelImpl.this.hasLoadDateFromNet29 = new DateUtil(i, i2, 1);
                    Observable.just(code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Sport28MonthCode>() {
                        public void accept(Sport28MonthCode code) throws Exception {
                            ActiveTodayModelImpl.this.get28HistoryMonthly(j, i, i2, i3, str, z);
                        }
                    }, new Consumer<Throwable>() {
                        public void accept(Throwable throwable) throws Exception {
                            ThrowableExtension.printStackTrace(throwable);
                        }
                    });
                }

                public void onFail(Throwable e) {
                    ActiveTodayModelImpl.this.nowLoadDate29 = new DateUtil(ActiveTodayModelImpl.this.DEFAULT_YEAR, ActiveTodayModelImpl.this.DEFAULT_MON, ActiveTodayModelImpl.this.DEFAULT_DAY);
                    if ((e instanceof ServerException) && ((ServerException) e).code() == 10404) {
                        Observable.just(e).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Consumer<? super T>) new Consumer<Throwable>() {
                            public void accept(Throwable throwable) throws Exception {
                                ThrowableExtension.printStackTrace(throwable);
                                ActiveTodayModelImpl.this.get28HistoryMonthly(j, i, i2, i3, str, z);
                            }
                        });
                        ActiveTodayModelImpl.this.hasLoadDateFromNet29 = new DateUtil(i, i2, 1);
                    } else if (ActiveTodayModelImpl.this.myCallback != null) {
                        ActiveTodayModelImpl.this.myCallback.onFail(e, i, i2, i3, new SportAllData(i, i2, i3, str, false));
                    }
                }
            }).hasSport28DataNet(uid, year, month);
            return;
        }
        KLog.e(this.TAG, dateUtil.getSyyyyMMddDate() + "所在月的28数据已经请求过了--getDateInMonthHas28");
        KLog.e(this.TAG, "当前已经请求到-->" + this.hasLoadDateFromNet29.getSyyyyMMddDate());
    }

    public SportAllData getDataForShow(long uid, int year, int month, int day, String oDataFrom, boolean needNetData) {
        int mStep;
        long j;
        float cal;
        float f;
        Detail_data detail_data1;
        boolean has28 = true;
        boolean has29 = true;
        String dataFrom = oDataFrom;
        if (TextUtils.isEmpty(oDataFrom)) {
            TB_has28Days_monthly has28Days_monthly = (TB_has28Days_monthly) DataSupport.where("uid=? and year=? and month=?", uid + "", year + "", month + "").findFirst(TB_has28Days_monthly.class);
            if (has28Days_monthly != null && !TextUtils.isEmpty(has28Days_monthly.getInfo())) {
                List<RspInfoModel> list = JsonTool.getListJson(has28Days_monthly.getInfo(), RspInfoModel.class);
                if (list != null && list.size() > 0) {
                    String dates = year + HelpFormatter.DEFAULT_OPT_PREFIX + String.format("%02d", new Object[]{Integer.valueOf(month)}) + HelpFormatter.DEFAULT_OPT_PREFIX + String.format("%02d", new Object[]{Integer.valueOf(day)});
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        RspInfoModel rspInfoModel = (RspInfoModel) it.next();
                        if (dates.equals(rspInfoModel.getDate())) {
                            dataFrom = rspInfoModel.getFrom();
                            break;
                        }
                    }
                }
            }
        }
        KLog.e("no29444-： " + year + "--/" + month + "--/" + day + " == " + oDataFrom + " == " + dataFrom);
        SportAllData allData1 = (SportAllData) this.mSportAllDataHashMap.get(uid + new DateUtil(year, month, day).getSyyyyMMddDate() + dataFrom);
        if (allData1 == null || !allData1.isReal_has_data()) {
            SportAllData allData = new SportAllData(year, month, day, dataFrom, false);
            List<V3_sport_data> sportData = ModuleRouteSportService.getInstance().getSport(uid, year, month, day, dataFrom);
            KLog.e("no29444-： " + JsonTool.toJson(sportData));
            V3_walk walk = ModuleRouteWalkService.getInstance().getWalk(uid, new DateUtil(year, month, day).getSyyyyMMddDate(), dataFrom);
            DateUtil dateUtil = new DateUtil(year, month, day);
            if (sportData == null || sportData.size() == 0) {
                KLog.e("没查到28数据--是否是今天：" + dateUtil.isToday());
                has28 = false;
            }
            if (walk.getUid() == 0) {
                KLog.e("没查到29数据--是否是今天：" + dateUtil.isToday());
                has29 = false;
            }
            if (dateUtil.isToday()) {
                if (!has28 && !has29) {
                    return allData;
                }
            } else if (!has28) {
                if (needNetData) {
                    get28DataFromServer(uid, year, month, day, dataFrom);
                }
                this.mSportAllDataHashMap.put(uid + allData.getDateStr() + dataFrom, allData);
                return allData;
            }
            allData.setSteps(walk.getStep());
            allData.setCalorie((int) walk.getCalorie());
            allData.setDistance(((double) walk.getDistance()) / 1000.0d);
            float totalCal = 0.0f;
            int totalWalk = 0;
            float totalDistance = 0.0f;
            int totalActive = 0;
            int mStep2 = 0;
            float mDis = 0.0f;
            int mAct = 0;
            double mCals = Utils.DOUBLE_EPSILON;
            int mNum = 0;
            int count = sportData != null ? sportData.size() : 0;
            long stUTime = 0;
            long edUTime = 0;
            Gson gson = new Gson();
            for (int i = 0; i < count; i++) {
                if (!dateUtil.isSameDay(System.currentTimeMillis(), false) || ((V3_sport_data) sportData.get(i)).getSport_type() != 255) {
                    Detail_data detail_data = (Detail_data) gson.fromJson(((V3_sport_data) sportData.get(i)).getDetail_data(), Detail_data.class);
                    if (((V3_sport_data) sportData.get(i)).getSport_type() != 1) {
                        totalActive += detail_data.getActivity();
                    }
                    if (i < count - 1) {
                        detail_data1 = (Detail_data) gson.fromJson(((V3_sport_data) sportData.get(i + 1)).getDetail_data(), Detail_data.class);
                    } else {
                        detail_data1 = new Detail_data();
                    }
                    KLog.e(this.TAG, JsonTool.toJson(sportData.get(i)));
                    if (i < count - 1 && ((V3_sport_data) sportData.get(i)).getSport_type() == 1) {
                        if (((V3_sport_data) sportData.get(i)).getSport_type() == ((V3_sport_data) sportData.get(i + 1)).getSport_type() && detail_data.getActivity() <= 5 && detail_data1.getActivity() <= 5) {
                            if (((V3_sport_data) sportData.get(i)).getStart_uxtime() - ((V3_sport_data) sportData.get(i + 1)).getEnd_uxtime() <= 1800) {
                                mStep2 += detail_data.getStep();
                                mDis += detail_data.getDistance();
                                mAct += detail_data.getActivity();
                                int mSTime = ((V3_sport_data) sportData.get(i)).getStart_time();
                                stUTime = ((V3_sport_data) sportData.get(i)).getStart_uxtime();
                                if (mNum == 0) {
                                    int mETime = ((V3_sport_data) sportData.get(i)).getEnd_time();
                                    edUTime = ((V3_sport_data) sportData.get(i)).getEnd_uxtime();
                                }
                                mNum++;
                                mCals += ((V3_sport_data) sportData.get(i)).getCalorie();
                            }
                        }
                    }
                    int sportType = ((V3_sport_data) sportData.get(i)).getSport_type();
                    if (!(sportType == 4 || sportType == 2 || sportType == 131)) {
                        mStep2 += detail_data.getStep();
                    }
                    float mDis2 = mDis + detail_data.getDistance();
                    int mSTime2 = ((V3_sport_data) sportData.get(i)).getStart_time();
                    int mAct2 = mAct + detail_data.getActivity();
                    stUTime = ((V3_sport_data) sportData.get(i)).getStart_uxtime();
                    if (mNum == 0) {
                        int mETime2 = ((V3_sport_data) sportData.get(i)).getEnd_time();
                        edUTime = ((V3_sport_data) sportData.get(i)).getEnd_uxtime();
                    }
                    double mCals2 = mCals + ((V3_sport_data) sportData.get(i)).getCalorie();
                    SportDetailsData data = new SportDetailsData();
                    data.setTime(getTime(stUTime) + HelpFormatter.DEFAULT_OPT_PREFIX + getTime(edUTime));
                    data.setActivity((int) ((edUTime - stUTime) % 60 == 0 ? (edUTime - stUTime) / 60 : ((edUTime - stUTime) / 60) + 1));
                    if (Util.getSporyImgOrName(0, ((V3_sport_data) sportData.get(i)).getSport_type()) != -1) {
                        data.setStr_res(Util.getSporyImgOrName(0, ((V3_sport_data) sportData.get(i)).getSport_type()));
                    }
                    if (((V3_sport_data) sportData.get(i)).getSport_type() == 7) {
                        data.setImgType(R.mipmap.run_yel3x);
                    } else if (((V3_sport_data) sportData.get(i)).getSport_type() == 136) {
                        data.setImgType(R.mipmap.bike_yel3x);
                    } else {
                        data.setImgType(Util.getSporyImgOrName(2, ((V3_sport_data) sportData.get(i)).getSport_type()));
                    }
                    data.setType(((V3_sport_data) sportData.get(i)).getSport_type());
                    data.setStrCalories(this.decimalFormat2.format(mCals2));
                    data.setStartTime(stUTime);
                    data.setEndTime(edUTime);
                    totalCal = (float) (((double) totalCal) + mCals2);
                    totalDistance += mDis2;
                    totalWalk += mStep2;
                    data.setStep(mStep2);
                    data.setDistance(((double) mDis2) / 1000.0d);
                    data.setActivity(mAct2);
                    if (data.getType() != 7 && data.getType() != 147) {
                        allData.getDetailsDatas().add(data);
                    } else if (data.getStep() != 0) {
                        allData.getDetailsDatas().add(data);
                    }
                    mStep2 = 0;
                    mDis = 0.0f;
                    mAct = 0;
                    mCals = Utils.DOUBLE_EPSILON;
                    mNum = 0;
                } else {
                    KLog.e(this.TAG, "统计时，忽略今天的others数据");
                }
            }
            for (int i2 = 0; i2 < count; i2++) {
                KLog.e("no2855数据-> " + JsonTool.toJson(sportData.get(i2)));
                long tedUTime = ((V3_sport_data) sportData.get(i2)).getEnd_uxtime();
                long tstUTime = ((V3_sport_data) sportData.get(i2)).getStart_uxtime();
                DateUtil dateUtil2 = new DateUtil(tstUTime, true);
                DateUtil dateUtil3 = new DateUtil(tedUTime, true);
                int sHour = dateUtil2.getHour();
                int eHour = dateUtil3.getHour();
                int cha = eHour - sHour;
                Detail_data detailData = (Detail_data) new Gson().fromJson(((V3_sport_data) sportData.get(i2)).getDetail_data(), Detail_data.class);
                int mSportType = ((V3_sport_data) sportData.get(i2)).getSport_type();
                if (mSportType == 4 || mSportType == 2 || mSportType == 131) {
                    mStep = 0;
                } else {
                    mStep = detailData.getStep();
                }
                double mCals3 = ((V3_sport_data) sportData.get(i2)).getCalorie();
                if (((V3_sport_data) sportData.get(i2)).getSport_type() != 255) {
                    KLog.e(this.TAG, "sHour:" + sHour + "eHour:" + eHour);
                    if (cha != 0) {
                        if ((tedUTime - tstUTime) % 60 == 0) {
                            j = (tedUTime - tstUTime) / 60;
                        } else {
                            j = ((tedUTime - tstUTime) / 60) + 1;
                        }
                        int totalMin = (int) j;
                        if (totalMin < 1) {
                            totalMin = 1;
                        }
                        int sMin = dateUtil2.getMinute();
                        int eMin = dateUtil3.getMinute();
                        if (eHour >= 24) {
                            eHour = 23;
                        }
                        if (sHour >= 24) {
                            sHour = 23;
                        }
                        if (cha > 0) {
                            float minCal = (float) (mCals3 / ((double) (((float) totalMin) * 1.0f)));
                            float minStep = ((float) mStep) / (((float) totalMin) * 1.0f);
                            KLog.d("no2855数据:mStep " + mStep + " == " + mCals3 + " == " + totalMin + " == " + minCal + " == " + minStep);
                            for (int j2 = sHour; j2 <= eHour; j2++) {
                                if (j2 == sHour) {
                                    cal = Util.doubleToFloat(2, (double) (((float) (60 - sMin)) * minCal));
                                    f = (float) (60 - sMin);
                                } else if (j2 == eHour) {
                                    cal = Util.doubleToFloat(2, (double) (((float) eMin) * minCal));
                                    f = (float) eMin;
                                } else {
                                    cal = Util.doubleToFloat(2, (double) (60.0f * minCal));
                                    f = 60.0f;
                                }
                                int step = (int) (f * minStep);
                                float[] caloreis = allData.getCaloreis();
                                caloreis[j2] = caloreis[j2] + cal;
                                float[] step_value_every_h = allData.getStep_value_every_h();
                                step_value_every_h[j2] = step_value_every_h[j2] + ((float) step);
                                KLog.e("no2855数据: " + cal + "/" + step + "//" + mCals3 + "/" + totalMin);
                            }
                        }
                    } else {
                        float[] caloreis2 = allData.getCaloreis();
                        caloreis2[sHour] = (float) (((double) caloreis2[sHour]) + mCals3);
                        float[] step_value_every_h2 = allData.getStep_value_every_h();
                        step_value_every_h2[sHour] = step_value_every_h2[sHour] + ((float) mStep);
                    }
                }
            }
            if (allData.getSteps() > totalWalk) {
                V3_sport_data tb_v3_sport_data = new V3_sport_data();
                tb_v3_sport_data.setYear(year);
                tb_v3_sport_data.setMonth(month);
                tb_v3_sport_data.setDay(day);
                tb_v3_sport_data.setWeek(dateUtil.getWeekOfYear());
                tb_v3_sport_data.setData_from(UserConfig.getInstance().getDevice());
                tb_v3_sport_data.setUid(UserConfig.getInstance().getNewUID());
                tb_v3_sport_data.set_uploaded(0);
                tb_v3_sport_data.setSport_type(255);
                if (has28) {
                    tb_v3_sport_data.setEnd_time(((V3_sport_data) sportData.get(0)).getEnd_time());
                    tb_v3_sport_data.setStart_time(((V3_sport_data) sportData.get(sportData.size() - 1)).getStart_time());
                    tb_v3_sport_data.setEnd_uxtime(((V3_sport_data) sportData.get(0)).getEnd_uxtime());
                    tb_v3_sport_data.setStart_uxtime(((V3_sport_data) sportData.get(sportData.size() - 1)).getStart_uxtime());
                } else if (has29) {
                    tb_v3_sport_data.setStart_uxtime(walk.getRecord_date());
                    tb_v3_sport_data.setEnd_uxtime(walk.getRecord_date());
                    DateUtil dateUtil4 = new DateUtil(walk.getRecord_date(), true);
                    int minute = (dateUtil4.getHour() * 60) + dateUtil4.getMinute();
                    tb_v3_sport_data.setStart_time(minute);
                    tb_v3_sport_data.setEnd_time(minute);
                }
                tb_v3_sport_data.setCalorie((double) Math.max(0.0f, ((float) allData.getCalorie()) - totalCal));
                Detail_data d = new Detail_data();
                d.setStep(allData.getSteps() - totalWalk);
                d.setDistance((float) Math.max(Utils.DOUBLE_EPSILON, (allData.getDistance() * 1000.0d) - ((double) totalDistance)));
                tb_v3_sport_data.setDetail_data(d.toString());
                ModuleRouteSportService.getInstance().upDateOthers(tb_v3_sport_data);
                SportDetailsData data2 = new SportDetailsData();
                data2.setTime(getTime(stUTime) + HelpFormatter.DEFAULT_OPT_PREFIX + getTime(edUTime));
                data2.setActivity((int) ((edUTime - stUTime) % 60 == 0 ? (edUTime - stUTime) / 60 : ((edUTime - stUTime) / 60) + 1));
                if (Util.getSporyImgOrName(0, 255) != -1) {
                    data2.setStr_res(Util.getSporyImgOrName(0, 255));
                }
                data2.setType(255);
                data2.setStrCalories(this.decimalFormat2.format(tb_v3_sport_data.getCalorie()));
                data2.setStartTime(tb_v3_sport_data.getStart_uxtime());
                data2.setEndTime(tb_v3_sport_data.getEnd_uxtime());
                data2.setStep(d.getStep());
                data2.setImgType(R.mipmap.others_on3x);
                data2.setDistance(((double) d.getDistance()) / 1000.0d);
                allData.getDetailsDatas().add(data2);
            } else {
                ModuleRouteSportService.getInstance().deleteOthers(uid, dataFrom, year, month, day);
            }
            int step2add = 0;
            float cal2add = 0.0f;
            double dis2add = Utils.DOUBLE_EPSILON;
            boolean hasP1Others2Add = false;
            Iterator<SportDetailsData> detailsDataIterator = allData.getDetailsDatas().iterator();
            if (!(allData.getData_from().indexOf("watch-P1") == -1 && allData.getData_from().indexOf("NX4399") == -1)) {
                KLog.e(this.TAG, "isP1");
                while (detailsDataIterator.hasNext()) {
                    SportDetailsData detailsData = (SportDetailsData) detailsDataIterator.next();
                    KLog.e(this.TAG, detailsData.getStartTime() + "/" + detailsData.getEndTime());
                    if (detailsData.getActivity() < 1 && detailsData.getType() != 1) {
                        hasP1Others2Add = true;
                        step2add += detailsData.getStep();
                        cal2add += Float.parseFloat(detailsData.getStrCalories());
                        dis2add += detailsData.getDistance();
                        KLog.e(this.TAG, "发现P1手表有小于1min的运动-->" + JsonTool.toJson(detailsData));
                        detailsDataIterator.remove();
                    }
                }
            }
            if (hasP1Others2Add) {
                SportDetailsData last_item = null;
                if (allData.getDetailsDatas().size() > 0) {
                    last_item = (SportDetailsData) allData.getDetailsDatas().get(allData.getDetailsDatas().size() - 1);
                }
                if (last_item == null || last_item.getType() != 255) {
                    SportDetailsData data3 = new SportDetailsData();
                    if (sportData == null || sportData.size() == 0) {
                        sportData = new ArrayList<>();
                        V3_sport_data tb_v3_sport_data2 = new V3_sport_data();
                        tb_v3_sport_data2.setYear(year);
                        tb_v3_sport_data2.setMonth(month);
                        tb_v3_sport_data2.setDay(day);
                        tb_v3_sport_data2.setWeek(dateUtil.getWeekOfYear());
                        tb_v3_sport_data2.setData_from(UserConfig.getInstance().getDevice());
                        tb_v3_sport_data2.setUid(UserConfig.getInstance().getNewUID());
                        tb_v3_sport_data2.set_uploaded(0);
                        tb_v3_sport_data2.setSport_type(255);
                        sportData.add(tb_v3_sport_data2);
                    }
                    KLog.d("no29444-sportData:" + sportData.size());
                    data3.setTime(getTime(stUTime) + HelpFormatter.DEFAULT_OPT_PREFIX + getTime(edUTime));
                    data3.setActivity((int) ((edUTime - stUTime) % 60 == 0 ? (edUTime - stUTime) / 60 : ((edUTime - stUTime) / 60) + 1));
                    if (Util.getSporyImgOrName(0, 255) != -1) {
                        data3.setStr_res(Util.getSporyImgOrName(0, 255));
                    }
                    data3.setType(255);
                    data3.setStrCalories(this.decimalFormat2.format((double) cal2add));
                    data3.setStartTime(0);
                    data3.setEndTime(0);
                    data3.setStep(step2add);
                    data3.setImgType(R.mipmap.others_on3x);
                    data3.setDistance(dis2add);
                    allData.getDetailsDatas().add(data3);
                } else {
                    last_item.setStep(last_item.getStep() + step2add);
                    last_item.setDistance(last_item.getDistance() + dis2add);
                    last_item.setStrCalories(this.decimalFormat2.format((double) (Float.parseFloat(last_item.getStrCalories()) + cal2add)));
                }
            }
            allData.setSteps(Math.max(totalWalk, allData.getSteps()));
            allData.setCalorie((int) Math.max(totalCal, (float) allData.getCalorie()));
            allData.setDistance(Math.max(((double) totalDistance) / 1000.0d, allData.getDistance()));
            allData.setActive_time(totalActive);
            int stand_count = 0;
            for (int i3 = 0; i3 < allData.getCaloreis().length; i3++) {
                if (allData.getCaloreis()[i3] >= ((float) STAND_JUDGE_CALORIES) || allData.getStep_value_every_h()[i3] >= ((float) STAND_JUDGE_STEPS)) {
                    stand_count++;
                }
            }
            allData.setStand_hours(stand_count);
            allData.setReal_has_data(true);
            if (!dateUtil.isToday()) {
                this.mSportAllDataHashMap.put(uid + allData.getDateStr() + dataFrom, allData);
            }
            KLog.e(this.TAG, year + "/" + month + "/" + day + ": " + JsonTool.toJson(allData));
            if (allData.getDetailsDatas() == null || allData.getDetailsDatas().size() <= 1) {
                return allData;
            }
            float cal2 = 0.0f;
            int step2 = 0;
            int i4 = allData.getDetailsDatas().size() - 1;
            while (i4 > 0 && ((SportDetailsData) allData.getDetailsDatas().get(i4 - 1)).getType() == 255 && ((SportDetailsData) allData.getDetailsDatas().get(i4)).getType() == 255) {
                cal2 += Float.parseFloat(((SportDetailsData) allData.getDetailsDatas().get(i4)).getStrCalories());
                step2 += ((SportDetailsData) allData.getDetailsDatas().get(i4)).getStep();
                allData.getDetailsDatas().remove(i4);
                i4--;
            }
            if (cal2 <= 0.0f && step2 <= 0) {
                return allData;
            }
            float newCal = Float.parseFloat(((SportDetailsData) allData.getDetailsDatas().get(allData.getDetailsDatas().size() - 1)).getStrCalories()) + cal2;
            ((SportDetailsData) allData.getDetailsDatas().get(allData.getDetailsDatas().size() - 1)).setStep(((SportDetailsData) allData.getDetailsDatas().get(allData.getDetailsDatas().size() - 1)).getStep() + step2);
            ((SportDetailsData) allData.getDetailsDatas().get(allData.getDetailsDatas().size() - 1)).setStrCalories(this.decimalFormat2.format((double) newCal));
            return allData;
        }
        KLog.e(this.TAG, "内存中查到结果" + JsonTool.toJson(allData1));
        return allData1;
    }

    private void get28DataFromServer(long uid, int year, int month, int day, String dataFrom) {
        if (this.nowLoadDate28.isSameMonth(month, year)) {
            KLog.e(this.TAG, "服务器请求28月数据被拦截-->" + year + "/" + month + "/" + day);
            return;
        }
        this.nowLoadDate28.setYear(year);
        this.nowLoadDate28.setMonth(month);
        this.nowLoadDate28.setDay(day);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int actualMaximum = cal.getActualMaximum(5);
        DateUtil dateUtil = new DateUtil(year, month, 1);
        if (dateUtil.getTimestamp() < this.hasLoadDateFromNet28.getTimestamp()) {
            KLog.e(this.TAG, "去服务器请求28-->" + year + "/" + month + "/" + day);
            if (this.myCallback != null) {
                this.myCallback.startProcess();
            }
            final int i = year;
            final int i2 = month;
            final long j = uid;
            final int i3 = day;
            final String str = dataFrom;
            NetFactory.getInstance().getClient(new MyCallback<Sport28Code>() {
                public void onSuccess(Sport28Code code) {
                    ActiveTodayModelImpl.this.hasLoadDateFromNet28 = new DateUtil(i, i2, 1);
                    SportAllData allData = ActiveTodayModelImpl.this.getDataForShow(j, i, i2, i3, str, false);
                    if (ActiveTodayModelImpl.this.myCallback != null) {
                        ActiveTodayModelImpl.this.myCallback.onSuccess(allData);
                        ActiveTodayModelImpl.this.myCallback.month28DataArrive(i, i2);
                    }
                }

                public void onFail(Throwable e) {
                    ActiveTodayModelImpl.this.nowLoadDate28 = new DateUtil(ActiveTodayModelImpl.this.DEFAULT_YEAR, ActiveTodayModelImpl.this.DEFAULT_MON, ActiveTodayModelImpl.this.DEFAULT_DAY);
                    if (!(e instanceof ServerException)) {
                        SportAllData sportAllData = new SportAllData(i, i2, i3, str, false);
                    } else if (((ServerException) e).code() == 10404) {
                        DateUtil dateUtil1 = new DateUtil(i, i2, 1);
                        for (int i = 1; i <= i3; i++) {
                            dateUtil1.setDay(i);
                            SportAllData sportAllData2 = new SportAllData(i, i2, i, str, true);
                            if (!dateUtil1.isToday()) {
                                ActiveTodayModelImpl.this.mSportAllDataHashMap.put(j + sportAllData2.getDateStr() + str, sportAllData2);
                            }
                        }
                        ActiveTodayModelImpl.this.hasLoadDateFromNet28 = new DateUtil(i, i2, 1);
                    } else {
                        SportAllData sportAllData3 = new SportAllData(i, i2, i3, str, false);
                        ActiveTodayModelImpl.this.mSportAllDataHashMap.put(j + sportAllData3.getDateStr() + str, sportAllData3);
                    }
                    if (ActiveTodayModelImpl.this.myCallback != null) {
                        Throwable th = e;
                        ActiveTodayModelImpl.this.myCallback.onFail(th, i, i2, i3, ActiveTodayModelImpl.this.getDataForShow(j, i, i2, i3, str, false));
                    }
                }
            }).downloadSport28(uid, dateUtil);
            return;
        }
        KLog.e(this.TAG, dateUtil.getSyyyyMMddDate() + "所在月的28数据已经请求过了。。。。");
        KLog.e(this.TAG, "当前已经请求到-->" + this.hasLoadDateFromNet28.getSyyyyMMddDate());
    }

    private String getTime(long time) {
        return new DateUtil(time, true).getHHmmDate();
    }
}
