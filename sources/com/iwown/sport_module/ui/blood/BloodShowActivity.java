package com.iwown.sport_module.ui.blood;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.blood.BloodData;
import com.iwown.data_link.blood.BloodDataUpload;
import com.iwown.data_link.blood.bpCoverageDown;
import com.iwown.data_link.blood.bpCoverageDown2;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.data_link.sport_data.Bp_data_sport;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.views.bloodview.DBloodChartView;
import com.iwown.lib_common.views.bloodview.DBloodChartView.BpCllback;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.sleep.fragment.TSleepViewPagerFragment.OnFragmentInteractionListener;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder.CallBack;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import com.socks.library.KLog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.LitePal;

public class BloodShowActivity extends BaseActivity implements OnFragmentInteractionListener, OnClickListener {
    private static final String TAG = "";
    public static long currentTime = System.currentTimeMillis();
    BPchartBean[] bPchartBean;
    /* access modifiers changed from: private */
    public DBloodChartView bpChart;
    private int[] data1 = new int[25];
    private int[] data2 = new int[25];
    private int[] data3 = new int[25];
    private int[] data4 = new int[25];
    Handler handler = new Handler();
    private LoadingDialog loadingDialog;
    /* access modifiers changed from: private */
    public int mouthblood;
    private ListView rvp_blood;
    /* access modifiers changed from: private */
    public LinearLayout sport_module_bptop;
    private ScrollView sv_blood_main;
    public long todayendT;
    /* access modifiers changed from: private */
    public TextView tv_blood_hight;
    /* access modifiers changed from: private */
    public TextView tv_blood_lost;
    /* access modifiers changed from: private */
    public TextView tv_blood_time;
    /* access modifiers changed from: private */
    public TextView tv_date_choose;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentLayout(R.layout.sport_module_fragment_blood);
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.heart_top));
        setTitleTextID(R.string.sport_module_blood_rate);
        initData();
        uploadBPData();
    }

    private void initData() {
        this.sv_blood_main = (ScrollView) findViewById(R.id.sv_blood_main);
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        this.mouthblood = dateUtil1.getMonth();
        initCalendar();
        Downcoverage(dateUtil1.getYear(), dateUtil1.getMonth());
        this.rvp_blood = (ListView) findViewById(R.id.sport_module_bloodlist);
        this.bpChart = (DBloodChartView) findViewById(R.id.hcv_bloodchart);
        this.tv_blood_lost = (TextView) findViewById(R.id.tv_blood_lostBP);
        this.tv_blood_hight = (TextView) findViewById(R.id.tv_blood_hight);
        this.tv_blood_time = (TextView) findViewById(R.id.tv_blood_BPtime);
        this.tv_date_choose = (TextView) findViewById(R.id.tv_BPdate_center);
        this.sport_module_bptop = (LinearLayout) findViewById(R.id.sport_module_bptop);
        currentTime = dateUtil1.getZeroTime();
        this.todayendT = currentTime + 86400;
        showBloodlist(currentTime, this.todayendT);
        this.tv_date_choose.setText(R.string.sport_module_Today);
        this.tv_date_choose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
                    BloodShowActivity.this.initCalendar();
                }
                CalendarShowHanlder.getCalendarShowHanlder().showCalendar(BloodShowActivity.this.tv_date_choose);
            }
        });
        this.sv_blood_main.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        this.bpChart.setCallback(new BpCllback() {
            public void bpData(int a, int b, int c, int d, int e) {
                if (a == 0 && b == 0 && c == 0 && d == 0) {
                    BloodShowActivity.this.sport_module_bptop.setVisibility(8);
                    return;
                }
                BloodShowActivity.this.sport_module_bptop.setVisibility(0);
                if (b == a) {
                    BloodShowActivity.this.tv_blood_lost.setText(b + "  ");
                } else {
                    BloodShowActivity.this.tv_blood_lost.setText(b + HelpFormatter.DEFAULT_OPT_PREFIX + a + "  ");
                }
                if (d == c) {
                    BloodShowActivity.this.tv_blood_hight.setText(d + "  ");
                } else {
                    BloodShowActivity.this.tv_blood_hight.setText(d + HelpFormatter.DEFAULT_OPT_PREFIX + c + "  ");
                }
                BloodShowActivity.this.tv_blood_time.setText(" || " + e + ":00-" + (e + 1) + ":00");
            }
        });
    }

    /* access modifiers changed from: private */
    public void showBloodlist(long tbp_time, long todayendT2) {
        List<Bp_data_sport> todayBlood = ModuleRouteSportService.getInstance().getAllDataBlood(UserConfig.getInstance().getNewUID(), tbp_time, todayendT2);
        List<BloodBeanData> bloodDataListList = new ArrayList<>();
        BloodBeanData[] A = new BloodBeanData[todayBlood.size()];
        if (todayBlood.size() > 0) {
            this.rvp_blood.setVisibility(0);
            this.bPchartBean = new BPchartBean[25];
            for (int i = 0; i < 25; i++) {
                this.bPchartBean[i] = new BPchartBean();
                this.bPchartBean[i].setBPhour(i);
                this.bPchartBean[i].setTimeh_sbp(0);
                this.bPchartBean[i].setTimel_sbp(0);
                this.bPchartBean[i].setTimeh_dbp(0);
                this.bPchartBean[i].setTimel_dbp(0);
            }
            String lasthour = "";
            int time_hsbp = 0;
            int time_lsbp = 0;
            int time_hdbp = 0;
            int time_ldbp = 0;
            for (int i2 = 0; i2 < todayBlood.size(); i2++) {
                A[i2] = new BloodBeanData();
                A[i2].setBP_grade(JudgeBlood(((Bp_data_sport) todayBlood.get(i2)).getSbp(), ((Bp_data_sport) todayBlood.get(i2)).getDbp()));
                A[i2].setBP_num(((Bp_data_sport) todayBlood.get(i2)).getSbp() + "/" + ((Bp_data_sport) todayBlood.get(i2)).getDbp());
                A[i2].setBP_time(DataTimeUtils.getHM(((Bp_data_sport) todayBlood.get(i2)).getBpTime() * 1000));
                bloodDataListList.add(A[i2]);
                String Timehour = DataTimeUtils.getH(((Bp_data_sport) todayBlood.get(i2)).getBpTime() * 1000);
                String str = DataTimeUtils.getyyyyMMddHHmm(((Bp_data_sport) todayBlood.get(i2)).getBpTime() * 1000);
                int chartXid = Integer.parseInt(Timehour);
                if (Timehour.equals(lasthour)) {
                    this.bPchartBean[chartXid].setTimeh_sbp(getTimeMax(time_hsbp, ((Bp_data_sport) todayBlood.get(i2)).getSbp()));
                    this.bPchartBean[chartXid].setTimel_sbp(getTimeMin(time_lsbp, ((Bp_data_sport) todayBlood.get(i2)).getSbp()));
                    this.bPchartBean[chartXid].setTimeh_dbp(getTimeMax(time_hdbp, ((Bp_data_sport) todayBlood.get(i2)).getDbp()));
                    this.bPchartBean[chartXid].setTimel_dbp(getTimeMin(time_ldbp, ((Bp_data_sport) todayBlood.get(i2)).getDbp()));
                    time_hsbp = getTimeMax(time_hsbp, ((Bp_data_sport) todayBlood.get(i2)).getSbp());
                    time_lsbp = getTimeMin(time_lsbp, ((Bp_data_sport) todayBlood.get(i2)).getSbp());
                    time_hdbp = getTimeMax(time_hdbp, ((Bp_data_sport) todayBlood.get(i2)).getDbp());
                    time_ldbp = getTimeMin(time_ldbp, ((Bp_data_sport) todayBlood.get(i2)).getDbp());
                } else {
                    this.bPchartBean[chartXid].setBPhour(Integer.parseInt(Timehour));
                    this.bPchartBean[chartXid].setTimeh_sbp(((Bp_data_sport) todayBlood.get(i2)).getSbp());
                    this.bPchartBean[chartXid].setTimel_sbp(((Bp_data_sport) todayBlood.get(i2)).getSbp());
                    this.bPchartBean[chartXid].setTimeh_dbp(((Bp_data_sport) todayBlood.get(i2)).getDbp());
                    this.bPchartBean[chartXid].setTimel_dbp(((Bp_data_sport) todayBlood.get(i2)).getDbp());
                    lasthour = Timehour;
                    time_hsbp = ((Bp_data_sport) todayBlood.get(i2)).getSbp();
                    time_lsbp = ((Bp_data_sport) todayBlood.get(i2)).getSbp();
                    time_hdbp = ((Bp_data_sport) todayBlood.get(i2)).getDbp();
                    time_ldbp = ((Bp_data_sport) todayBlood.get(i2)).getDbp();
                }
                KLog.e("", "showBloodlist: -------------" + ((Bp_data_sport) todayBlood.get(i2)).getSbp() + "     " + ((Bp_data_sport) todayBlood.get(i2)).getDbp());
            }
            Collections.sort(bloodDataListList, new Comparator<BloodBeanData>() {
                public int compare(BloodBeanData o1, BloodBeanData o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    long a = 0;
                    long b = 0;
                    try {
                        a = sdf.parse(o1.getBP_time()).getTime();
                        b = sdf.parse(o2.getBP_time()).getTime();
                    } catch (ParseException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    if (a < b) {
                        return 1;
                    }
                    if (o1.getBP_time() == o2.getBP_time()) {
                        return 0;
                    }
                    return -1;
                }
            });
            this.rvp_blood.setAdapter(new BloodAdapter(this, R.layout.sport_module_active_blood_list, bloodDataListList));
            setListViewHeightBasedOnChildren(this.rvp_blood);
            this.rvp_blood.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            initCurveChart2();
            return;
        }
        this.sport_module_bptop.setVisibility(8);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                BloodShowActivity.this.bpChart.setDate(null);
            }
        }, 50);
        this.rvp_blood.setVisibility(8);
    }

    public String JudgeBlood(int sbp, int dbp) {
        if (sbp < 90 || dbp < 60) {
            return getString(R.string.blood_grade_low);
        }
        if (sbp > 89 && sbp < 120 && dbp > 59 && dbp < 80) {
            return getString(R.string.blood_grade_normal);
        }
        if (sbp >= 180 || dbp >= 110) {
            return getString(R.string.blood_grade_Severelyhigh);
        }
        if ((sbp > 159 && sbp < 180) || (dbp > 99 && dbp < 110)) {
            return getString(R.string.blood_grade_Moderatelyhigh);
        }
        if ((sbp > 139 && sbp < 160) || (dbp > 89 && dbp < 100)) {
            return getString(R.string.blood_grade_Mildlyhigh);
        }
        if ((sbp <= 119 || sbp >= 140) && (dbp <= 79 || dbp >= 90)) {
            return getString(R.string.blood_grade_Severelyhigh);
        }
        return getString(R.string.blood_grade_Normalhigh_value);
    }

    public int getTimeMax(int a, int b) {
        return a > b ? a : b;
    }

    public int getTimeMin(int a, int b) {
        return a < b ? a : b;
    }

    private void initCurveChart2() {
        for (int i = 0; i < 25; i++) {
            this.data1[i] = this.bPchartBean[i].getTimeh_sbp();
            this.data2[i] = this.bPchartBean[i].getTimel_sbp();
            this.data3[i] = this.bPchartBean[i].getTimeh_dbp();
            this.data4[i] = this.bPchartBean[i].getTimel_dbp();
        }
        List<int[]> data = new ArrayList<>();
        data.add(this.data1);
        data.add(this.data2);
        data.add(this.data3);
        data.add(this.data4);
        if (data == null) {
            data = new ArrayList<>();
        }
        final List<int[]> finalData = data;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                BloodShowActivity.this.bpChart.setDate(finalData);
            }
        }, 50);
        Log.e("", "initCurveChart2: 更新数据");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            calendarShowHanlder.destory();
        }
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void initCalendar() {
        CalendarShowHanlder.init(this);
        CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
        CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
            public void onResult(int year, int month, int day) {
                if (month != BloodShowActivity.this.mouthblood) {
                    BloodShowActivity.this.Downcoverage(year, month);
                    DateUtil dateUtil = new DateUtil(year, month, 1);
                    dateUtil.setHour(0);
                    dateUtil.setMinute(0);
                    dateUtil.setSecond(0);
                    DateUtil dateUtilend = new DateUtil(year, month + 1, 1);
                    dateUtilend.setHour(0);
                    dateUtilend.setMinute(0);
                    dateUtilend.setSecond(0);
                    KLog.e(dateUtil.getY_M_D_H_M_S().toString() + "   kong  " + dateUtilend.getY_M_D_H_M_S().toString());
                    NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                        public void onSuccess(Integer integer) {
                            KLog.e("88808  downloadAllBlood ---------111   :  ");
                        }

                        public void onFail(Throwable e) {
                            KLog.e("88808  downloadAllBlood data fai1");
                        }
                    }).downloadAllBlood(UserConfig.getInstance().getNewUID(), dateUtil.getY_M_D_H_M_S().toString(), dateUtilend.getY_M_D_H_M_S().toString());
                    BloodShowActivity.this.mouthblood = month;
                }
                BloodShowActivity.currentTime = new DateUtil(year, month, day).getZeroTime();
                BloodShowActivity.this.todayendT = BloodShowActivity.currentTime + 86400;
                BloodShowActivity.this.tv_date_choose.setText(year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day);
                BloodShowActivity.this.showBloodlist(BloodShowActivity.currentTime, BloodShowActivity.this.todayendT);
            }
        });
    }

    public void showLoading() {
        KLog.e("showLoading " + this.loadingDialog);
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
        this.loadingDialog.show();
    }

    public void dismissLoading() {
        KLog.e("   dismissLoading " + this.loadingDialog);
        if (this.loadingDialog != null) {
            this.loadingDialog.dismiss();
        }
    }

    public void onClick(View v) {
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            LayoutParams params = listView.getLayoutParams();
            params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalHeight;
            listView.setLayoutParams(params);
        }
    }

    public void uploadBPData() {
        final ArrayList<BloodData> bloodData = new ArrayList<>();
        List<Bp_data_sport> AllBlood = ModuleRouteSportService.getInstance().getDataBlood(UserConfig.getInstance().getNewUID());
        if (AllBlood != null && AllBlood.size() > 0) {
            for (int i = 0; i < AllBlood.size(); i++) {
                if (((Bp_data_sport) AllBlood.get(i)).getIsupload() != 1) {
                    BloodData a = new BloodData();
                    a.setRecord_date(new DateUtil(((Bp_data_sport) AllBlood.get(i)).getBpTime(), true).getY_M_D_H_M_S());
                    a.setData_from(((Bp_data_sport) AllBlood.get(i)).getDataFrom());
                    a.setDbp(((Bp_data_sport) AllBlood.get(i)).getDbp());
                    a.setSbp(((Bp_data_sport) AllBlood.get(i)).getSbp());
                    KLog.e("l808  上传血压数据  " + DataTimeUtils.getyyyyMMddHHmmss(((Bp_data_sport) AllBlood.get(i)).getBpTime() * 1000) + "   " + ((Bp_data_sport) AllBlood.get(i)).getDataFrom() + "   " + ((Bp_data_sport) AllBlood.get(i)).getDbp());
                    bloodData.add(a);
                }
            }
        }
        BloodDataUpload bul = new BloodDataUpload();
        bul.uid = UserConfig.getInstance().getNewUID();
        bul.Data = bloodData;
        NetFactory.getInstance().getClient(new MyCallback<ResponseBody>() {
            public void onSuccess(ResponseBody responseBody) {
                if (responseBody != null && bloodData != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0; i < bloodData.size(); i++) {
                        long stTime = 0;
                        try {
                            stTime = simpleDateFormat.parse(((BloodData) bloodData.get(i)).getRecord_date()).getTime() / 1000;
                        } catch (ParseException e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                        ModuleRouteSportService.getInstance().updateDataBlood(UserConfig.getInstance().getNewUID(), stTime + "");
                    }
                }
            }

            public void onFail(Throwable e) {
                KLog.e("l808 uploadBlooddata data fai1");
            }
        }).uploadBlooddata(bul);
    }

    public void Downcoverage(int year, int mouth) {
        DateUtil dateUtil = new DateUtil(year, mouth, 1);
        List<Bp_data_sport> todayBlood = ModuleRouteSportService.getInstance().getAllDataBlood(UserConfig.getInstance().getNewUID(), dateUtil.getUnixTimestamp(), (DateUtil.getLastDayMonth(new Date(dateUtil.getTimestamp())) / 1000) + 86400);
        if (todayBlood != null) {
            Map<String, ShowLeveTag> showLeveTagList = new HashMap<>();
            for (int i = 0; i < todayBlood.size(); i++) {
                bpCoverageDown2 bpCoverageDown2 = new bpCoverageDown2();
                bpCoverageDown2.setData_from(((Bp_data_sport) todayBlood.get(i)).getDataFrom());
                bpCoverageDown2.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(((Bp_data_sport) todayBlood.get(i)).getBpTime() * 1000)) + "");
                showLeveTagList.put(bpCoverageDown2.getDate(), new ShowLeveTag(-16248796, ((Bp_data_sport) todayBlood.get(i)).getBpTime()));
            }
            if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(this, showLeveTagList);
                return;
            }
            return;
        }
        NetFactory.getInstance().getClient(new MyCallback<bpCoverageDown>() {
            public void onSuccess(bpCoverageDown bpCoverageDown) {
                if (bpCoverageDown != null) {
                    List<bpCoverageDown2> coverageDown2List = bpCoverageDown.getData().getBPDataIndex();
                    Map<String, ShowLeveTag> showLeveTagList = new HashMap<>();
                    for (bpCoverageDown2 bpCoverageDown2 : coverageDown2List) {
                        long unix_time = 0;
                        try {
                            unix_time = new SimpleDateFormat("yyyy-MM-dd").parse(bpCoverageDown2.getDate()).getTime() / 1000;
                        } catch (ParseException e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                        showLeveTagList.put(bpCoverageDown2.getDate(), new ShowLeveTag(-16248796, unix_time));
                    }
                    if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                        CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(BloodShowActivity.this, showLeveTagList);
                    }
                }
            }

            public void onFail(Throwable e) {
                KLog.e("downloadBlood data fai1");
            }
        }).downloadBPcoverage(UserConfig.getInstance().getNewUID(), year, mouth);
    }

    public void updateCalendar(Map<String, ContentBean> map) {
    }
}
