package com.iwown.sport_module.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.V3_sport_data;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsTrackRef;
import com.iwown.lib_common.BaseActivity2;
import com.iwown.lib_common.BaseActivity2.ActionOnclickListener;
import com.iwown.lib_common.WeakHandler;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.iwown.sport_module.Fragment.active.ActiveCaculateFragment;
import com.iwown.sport_module.Fragment.active.ActiveTodayFragment;
import com.iwown.sport_module.Fragment.active.ActiveTodayPagerAdapter;
import com.iwown.sport_module.R;
import com.iwown.sport_module.contract.ActiveTodayContract.ActivityTodayView;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.exception.ServerException;
import com.iwown.sport_module.net.response.MonthHas28DateCode.RspInfoModel;
import com.iwown.sport_module.net.response.Sport28MonthCode;
import com.iwown.sport_module.pojo.active.SportAllData;
import com.iwown.sport_module.presenter.ActiveTodayPresentImpl;
import com.iwown.sport_module.sql.TB_has28Days_monthly;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.NoScrollViewPager;
import com.iwown.sport_module.view.calendar.CalendarDayClickData;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder.CallBack;
import com.iwown.sport_module.view.calendar.HistoryCalendar;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import com.iwown.sport_module.view.calendar.OnCalendarDayClick;
import com.iwown.sport_module.view.calendar.SportPopupWindow;
import com.iwown.sport_module.view.checkbar.AChecKBarAdapter;
import com.iwown.sport_module.view.checkbar.ALinearCheckBar;
import com.iwown.sport_module.view.checkbar.ALinearCheckBar.OnCheckedChangeListener;
import com.socks.library.KLog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

@Route(path = "/sport/activeactivity")
public class ActiveActivity extends BaseActivity2 implements ActivityTodayView {
    /* access modifiers changed from: private */
    public CalendarDayClickData calendarDayClickData = new CalendarDayClickData();
    private boolean check = false;
    /* access modifiers changed from: private */
    public HashMap<String, Sport28MonthCode> daysHas28map = new HashMap<>();
    /* access modifiers changed from: private */
    public ALinearCheckBar mALinearCheckBar;
    private ActiveTodayPresentImpl mActiveTodayPresent = null;
    private List<Bean> mBeans;
    /* access modifiers changed from: private */
    public FrameLayout mCaculateContainer;
    /* access modifiers changed from: private */
    public ActiveCaculateFragment mCaculateFragment;
    /* access modifiers changed from: private */
    public View mCalendar;
    private View mCalendarLeft;
    /* access modifiers changed from: private */
    public ImageView mCalendarRight;
    private TextView mCalendarTitle;
    /* access modifiers changed from: private */
    public HistoryCalendar mCalendarView;
    private HashMap<String, Integer> mDataTypeMap = new HashMap<>();
    private FragmentManager mFragmentManager;
    /* access modifiers changed from: private */
    public View mGuideView;
    Animation mHiddenAction;
    private LoadingDialog mLoadingDialog = null;
    /* access modifiers changed from: private */
    public ImageView mMonthImg;
    private OnCalendarDayClick mOnCalendarDayClick = new OnCalendarDayClick() {
        public void onDayClick(CalendarDayClickData item) {
            ActiveActivity.this.calendarDayClickData.set(item);
            ActiveActivity.this.selectedYear = item.getYear();
            ActiveActivity.this.selectedMonth = item.getMonth();
            ActiveActivity.this.selectedDay = item.getDay();
            ActiveActivity.this.myDay = ActiveActivity.this.selectedDay;
            ActiveActivity.this.myYear = ActiveActivity.this.selectedYear;
            ActiveActivity.this.myMonth = ActiveActivity.this.selectedMonth;
            ActiveActivity.this.updateTitle();
            KLog.e("licl", "日历选择日期后的查询日期->" + ActiveActivity.this.selectedYear + "/" + ActiveActivity.this.selectedMonth + "/" + ActiveActivity.this.selectedDay);
            ActiveActivity.this.hiddenCalendar();
            DateUtil dateUtil = new DateUtil(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
            ActiveActivity.this.mTodayFragment.setThisFragmentTime(dateUtil.getTimestamp());
            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mTodayFragment);
            dateUtil.addDay(-1);
            ActiveActivity.this.mYesterdayFragment.setThisFragmentTime(dateUtil.getTimestamp());
            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mYesterdayFragment);
            dateUtil.addDay(2);
            ActiveActivity.this.mTomorrowFragment.setThisFragmentTime(dateUtil.getTimestamp());
            ActiveActivity.this.mTodayFragVp.setCurrentItem(1, true);
            ActiveActivity.this.mTodayFragVp.openNoLimitScroll();
            ActiveActivity.this.controlVpScroll();
        }
    };
    Animation mShowAction;
    private SportPopupWindow mSportPopup;
    /* access modifiers changed from: private */
    public TextView mTitle;
    /* access modifiers changed from: private */
    public View mTitleCenterView;
    /* access modifiers changed from: private */
    public NoScrollViewPager mTodayFragVp;
    /* access modifiers changed from: private */
    public ActiveTodayFragment mTodayFragment;
    /* access modifiers changed from: private */
    public ActiveTodayFragment mTomorrowFragment;
    WeakHandler mWeakHandler = new WeakHandler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public ActiveTodayFragment mYesterdayFragment;
    /* access modifiers changed from: private */
    public int minYear = 2012;
    private String[] months;
    /* access modifiers changed from: private */
    public int myDay;
    /* access modifiers changed from: private */
    public int myMonth;
    /* access modifiers changed from: private */
    public int myYear;
    boolean onceIn = true;
    /* access modifiers changed from: private */
    public int selectedDay;
    /* access modifiers changed from: private */
    public int selectedMonth;
    /* access modifiers changed from: private */
    public int selectedYear;
    Map<String, ShowLeveTag> showLeveTagList = new LinkedHashMap();
    List<Fragment> todayDataFrags = new ArrayList();
    /* access modifiers changed from: private */
    public int todayMonth;
    /* access modifiers changed from: private */
    public int todayYear;

    private class Bean {
        public int img_selector;
        public String text;
        public int text_selector;

        public Bean(int img_selector2, int text_selector2, String text2) {
            this.img_selector = img_selector2;
            this.text_selector = text_selector2;
            this.text = text2;
        }

        public Bean(int img_selector2, String text2) {
            this.img_selector = img_selector2;
            this.text = text2;
        }
    }

    public ActiveTodayPresentImpl getActiveTodayPresent() {
        return this.mActiveTodayPresent;
    }

    public HashMap<String, Integer> getDataTypeMap() {
        return this.mDataTypeMap;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_active_activity_layout);
        UserConfig.getInstance().initInfoFromOtherModule();
        CalendarShowHanlder.init(this);
        KLog.e(TAG, "2xxx" + CalendarShowHanlder.getCalendarShowHanlder() + "");
        CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
        CalendarShowHanlder.getCalendarShowHanlder().setRoundColor(-15374891);
        CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
            public void onResult(int year, int month, int day) {
                List<RspInfoModel> list;
                ActiveActivity.this.selectedYear = year;
                ActiveActivity.this.selectedMonth = month;
                ActiveActivity.this.selectedDay = day;
                ActiveActivity.this.myDay = ActiveActivity.this.selectedDay;
                ActiveActivity.this.myYear = ActiveActivity.this.selectedYear;
                ActiveActivity.this.myMonth = ActiveActivity.this.selectedMonth;
                ActiveActivity.this.updateTitle();
                KLog.e("licl", "日历选择日期后的查询日期->" + ActiveActivity.this.selectedYear + "/" + ActiveActivity.this.selectedMonth + "/" + ActiveActivity.this.selectedDay);
                TB_has28Days_monthly tb_has28Days_monthly = (TB_has28Days_monthly) DataSupport.where("uid=? and month=?", UserConfig.getInstance().getNewUID() + "", ActiveActivity.this.selectedMonth + "").findFirst(TB_has28Days_monthly.class);
                if (tb_has28Days_monthly != null) {
                    if (!TextUtils.isEmpty(tb_has28Days_monthly.getInfo())) {
                        list = JsonTool.getListJson(tb_has28Days_monthly.getInfo(), RspInfoModel.class);
                    } else {
                        list = new ArrayList<>();
                    }
                    if (list != null && list.size() > 0) {
                        for (RspInfoModel model : list) {
                            try {
                                ActiveActivity.this.showLeveTagList.put(model.getDate(), new ShowLeveTag(-16248796, new SimpleDateFormat("yyyy-MM-dd").parse(model.getDate()).getTime() / 1000));
                                if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                                    CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(ActiveActivity.this, ActiveActivity.this.showLeveTagList);
                                }
                            } catch (ParseException e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }
                }
                DateUtil dateUtil = new DateUtil(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                ActiveActivity.this.mTodayFragment.setThisFragmentTime(dateUtil.getTimestamp());
                ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mTodayFragment);
                dateUtil.addDay(-1);
                ActiveActivity.this.mYesterdayFragment.setThisFragmentTime(dateUtil.getTimestamp());
                ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mYesterdayFragment);
                dateUtil.addDay(2);
                ActiveActivity.this.mTomorrowFragment.setThisFragmentTime(dateUtil.getTimestamp());
                ActiveActivity.this.mTodayFragVp.setCurrentItem(1, true);
                ActiveActivity.this.mTodayFragVp.openNoLimitScroll();
                ActiveActivity.this.controlVpScroll();
            }
        });
        initData();
        initView();
        initEvent();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        KLog.e(TAG, "onResume...");
        if (this.onceIn) {
            DateUtil dateUtil = new DateUtil(this.selectedYear, this.selectedMonth, this.selectedDay);
            this.mTodayFragment.setThisFragmentTime(dateUtil.getTimestamp());
            this.mTodayFragment.setSportAllData(hasSportData(dateUtil));
            this.onceIn = false;
        }
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        if (this.mCaculateFragment == null) {
            this.mCaculateFragment = new ActiveCaculateFragment();
            transaction.add(R.id.calculate_frag_container, (Fragment) this.mCaculateFragment);
        }
        transaction.show(this.mCaculateFragment);
        transaction.commitAllowingStateLoss();
    }

    private void initData() {
        this.months = getResources().getStringArray(R.array.sport_module_months_items);
        DateUtil dateUtil = new DateUtil();
        int year = dateUtil.getYear();
        this.selectedYear = year;
        this.todayYear = year;
        int month = dateUtil.getMonth();
        this.selectedMonth = month;
        this.todayMonth = month;
        this.selectedDay = dateUtil.getDay();
        this.myYear = this.todayYear;
        this.myMonth = this.todayMonth;
        this.myDay = this.selectedDay;
        this.mActiveTodayPresent = new ActiveTodayPresentImpl(this);
        getDaysInThisMonthHas28(dateUtil.getYear(), dateUtil.getMonth());
        dateUtil.addMonth(-1);
        getDaysInThisMonthHas28(dateUtil.getYear(), dateUtil.getMonth());
        this.mActiveTodayPresent.getAllData(UserConfig.getInstance().getNewUID(), this.todayYear, this.todayMonth, this.selectedDay, UserConfig.getInstance().getDevice(), true);
    }

    /* access modifiers changed from: private */
    public void getDaysInThisMonthHas28(final int year, final int month) {
        if (this.daysHas28map.get(year + "" + month) != null) {
            KLog.e(TAG, year + "/" + month + "getDaysInThisMonthHas28--111");
            return;
        }
        KLog.e(TAG, year + "/" + month + "getDaysInThisMonthHas28--222");
        NetFactory.getInstance().getClient(new MyCallback<Sport28MonthCode>() {
            public void onSuccess(Sport28MonthCode o) {
                ActiveActivity.this.daysHas28map.put(year + "" + month, o);
                TB_has28Days_monthly has28Days_monthly = (TB_has28Days_monthly) DataSupport.where("uid=? and year=? and month=?", UserConfig.getInstance().getNewUID() + "", year + "", month + "").findFirst(TB_has28Days_monthly.class);
                if (has28Days_monthly != null) {
                    for (RspInfoModel s : JsonTool.getListJson(has28Days_monthly.getInfo(), RspInfoModel.class)) {
                        try {
                            long unix_time = new SimpleDateFormat("yyyy-MM-dd").parse(s.getDate()).getTime() / 1000;
                            KLog.e(BaseActivity2.TAG, Long.valueOf(unix_time));
                            ActiveActivity.this.showLeveTagList.put(s.getDate(), new ShowLeveTag(-16248796, unix_time));
                        } catch (ParseException e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                    ActiveActivity.this.hasDataPreDays(year, month);
                    if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                        CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(ActiveActivity.this, ActiveActivity.this.showLeveTagList);
                        CalendarShowHanlder.getCalendarShowHanlder().updateSelectDate(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                    }
                }
            }

            public void onFail(Throwable e) {
                if ((e instanceof ServerException) && ((ServerException) e).code() == 10404) {
                    ActiveActivity.this.daysHas28map.put(year + "" + month, new Sport28MonthCode());
                }
                ActiveActivity.this.hasDataPreDays(year, month);
            }
        }).hasSport28DataNet(UserConfig.getInstance().getNewUID(), year, month);
    }

    /* access modifiers changed from: private */
    public void hasDataPreDays(int year, int month) {
        KLog.e(TAG, "has28PreDays111");
        DateUtil dateUtil = new DateUtil(year, month, new DateUtil().getDay(), 0, 0, 0);
        if (dateUtil.isSameMonth(month, year)) {
            KLog.e(TAG, "has28PreDays222");
            for (int i = 1; i <= 20; i++) {
                dateUtil.addDay(-1);
                V3_walk v3_walk = ModuleRouteWalkService.getInstance().get29Walk(UserConfig.getInstance().getNewUID(), dateUtil.getUnixTimestamp());
                List<V3_sport_data> v3_sport_data = ModuleRouteSportService.getInstance().get28SportNoDataFrom(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());
                if (v3_sport_data != null && v3_sport_data.size() != 0) {
                    this.showLeveTagList.put(dateUtil.getY_M_D(), new ShowLeveTag(-16248796, dateUtil.getUnixTimestamp()));
                } else if (!(v3_walk == null || v3_walk.getStep() == 0 || !dateUtil.isToday())) {
                    this.showLeveTagList.put(dateUtil.getY_M_D(), new ShowLeveTag(-16248796, dateUtil.getUnixTimestamp()));
                }
            }
            if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(this, this.showLeveTagList);
            }
        }
    }

    private boolean has28Today() {
        DateUtil dateUtil = new DateUtil();
        List<V3_sport_data> v3_sport_data = ModuleRouteSportService.getInstance().getSport(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice());
        return ((v3_sport_data == null || v3_sport_data.size() == 0) && ModuleRouteWalkService.getInstance().get29Walk(UserConfig.getInstance().getNewUID(), dateUtil.getUnixTimestamp()) == null) ? false : true;
    }

    private void initEvent() {
        this.mGuideView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserConfig.getInstance().setHasGuideSport(true);
                UserConfig.getInstance().save();
                ActiveActivity.this.mGuideView.setVisibility(8);
            }
        });
        this.mTitleCenterView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                KLog.e(BaseActivity2.TAG, CalendarShowHanlder.getCalendarShowHanlder() + "xxx");
                if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
                    CalendarShowHanlder.init(ActiveActivity.this);
                    KLog.e(BaseActivity2.TAG, "2xxx" + CalendarShowHanlder.getCalendarShowHanlder() + "");
                    CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
                    CalendarShowHanlder.getCalendarShowHanlder().setRoundColor(-15374891);
                    CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
                        public void onResult(int year, int month, int day) {
                            ActiveActivity.this.selectedYear = year;
                            ActiveActivity.this.selectedMonth = month;
                            ActiveActivity.this.selectedDay = day;
                            ActiveActivity.this.myDay = ActiveActivity.this.selectedDay;
                            ActiveActivity.this.myYear = ActiveActivity.this.selectedYear;
                            ActiveActivity.this.myMonth = ActiveActivity.this.selectedMonth;
                            ActiveActivity.this.updateTitle();
                            KLog.e("licl", "日历选择日期后的查询日期->" + ActiveActivity.this.selectedYear + "/" + ActiveActivity.this.selectedMonth + "/" + ActiveActivity.this.selectedDay);
                            TB_has28Days_monthly tb_has28Days_monthly = (TB_has28Days_monthly) DataSupport.where("uid=? and month=?", UserConfig.getInstance().getNewUID() + "", ActiveActivity.this.selectedMonth + "").findFirst(TB_has28Days_monthly.class);
                            if (tb_has28Days_monthly != null && !TextUtils.isEmpty(tb_has28Days_monthly.getInfo())) {
                                List<RspInfoModel> list = JsonTool.getListJson(tb_has28Days_monthly.getInfo(), RspInfoModel.class);
                                if (list != null && list.size() > 0) {
                                    for (RspInfoModel model : list) {
                                        try {
                                            ActiveActivity.this.showLeveTagList.put(model.getDate(), new ShowLeveTag(-16248796, new SimpleDateFormat("yyyy-MM-dd").parse(model.getDate()).getTime() / 1000));
                                            if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                                                CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(ActiveActivity.this, ActiveActivity.this.showLeveTagList);
                                            }
                                        } catch (ParseException e) {
                                            ThrowableExtension.printStackTrace(e);
                                        }
                                    }
                                }
                            }
                            DateUtil dateUtil = new DateUtil(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                            ActiveActivity.this.mTodayFragment.setThisFragmentTime(dateUtil.getTimestamp());
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mTodayFragment);
                            dateUtil.addDay(-1);
                            ActiveActivity.this.mYesterdayFragment.setThisFragmentTime(dateUtil.getTimestamp());
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mYesterdayFragment);
                            dateUtil.addDay(2);
                            ActiveActivity.this.mTomorrowFragment.setThisFragmentTime(dateUtil.getTimestamp());
                            ActiveActivity.this.mTodayFragVp.setCurrentItem(1, true);
                            ActiveActivity.this.mTodayFragVp.openNoLimitScroll();
                            ActiveActivity.this.controlVpScroll();
                        }
                    });
                }
                CalendarShowHanlder.getCalendarShowHanlder().showCalendar(ActiveActivity.this.mTitleCenterView);
                ActiveActivity.this.getDaysInThisMonthHas28(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth);
                CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(ActiveActivity.this, ActiveActivity.this.showLeveTagList);
                CalendarShowHanlder.getCalendarShowHanlder().updateSelectDate(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
            }
        });
        this.mTodayFragVp.addOnPageChangeListener(new OnPageChangeListener() {
            private int selected = -1;

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                this.selected = position;
            }

            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    switch (this.selected) {
                        case 0:
                            ActiveActivity.this.mTodayFragment.addDays(-1);
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mTodayFragment);
                            ActiveActivity.this.mTodayFragVp.setCurrentItem(1, false);
                            ActiveActivity.this.mYesterdayFragment.addDays(-1);
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mYesterdayFragment);
                            ActiveActivity.this.mTomorrowFragment.addDays(-1);
                            ActiveActivity.this.mTodayFragVp.openNoLimitScroll();
                            break;
                        case 2:
                            ActiveActivity.this.mTodayFragment.addDays(1);
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mTodayFragment);
                            ActiveActivity.this.mTodayFragVp.setCurrentItem(1, false);
                            ActiveActivity.this.mYesterdayFragment.addDays(1);
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mYesterdayFragment);
                            ActiveActivity.this.mTomorrowFragment.addDays(1);
                            ActiveActivity.this.setAllData2Frag(ActiveActivity.this.mTomorrowFragment);
                            break;
                    }
                    long now_time = ActiveActivity.this.controlVpScroll();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(now_time);
                    ActiveActivity.this.myYear = ActiveActivity.this.selectedYear = calendar.get(1);
                    ActiveActivity.this.myMonth = ActiveActivity.this.selectedMonth = calendar.get(2) + 1;
                    ActiveActivity.this.myDay = ActiveActivity.this.selectedDay = calendar.get(5);
                    ActiveActivity.this.getDaysInThisMonthHas28(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth);
                    if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                        CalendarShowHanlder.getCalendarShowHanlder().updateSelectDate(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                    }
                    KLog.e(BaseActivity2.TAG, ActiveActivity.this.selectedYear + "/" + ActiveActivity.this.selectedMonth + "/" + ActiveActivity.this.selectedDay);
                    ActiveActivity.this.updateCanTitle();
                    ActiveActivity.this.updateTitle();
                }
            }
        });
        this.mCalendarRight.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActiveActivity.this.selectedMonth = ActiveActivity.this.selectedMonth + 1;
                if (ActiveActivity.this.selectedMonth > 12) {
                    ActiveActivity.this.selectedMonth = 1;
                    ActiveActivity.this.selectedYear = ActiveActivity.this.selectedYear + 1;
                }
                if ((ActiveActivity.this.selectedMonth == ActiveActivity.this.todayMonth && ActiveActivity.this.selectedYear == ActiveActivity.this.todayYear) || ActiveActivity.this.selectedYear > ActiveActivity.this.todayYear) {
                    ActiveActivity.this.mCalendarRight.setVisibility(4);
                }
                if ((ActiveActivity.this.selectedMonth <= ActiveActivity.this.todayMonth || ActiveActivity.this.selectedYear != ActiveActivity.this.todayYear) && ActiveActivity.this.selectedYear <= ActiveActivity.this.todayYear) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(1, ActiveActivity.this.selectedYear);
                    cal.set(2, ActiveActivity.this.selectedMonth - 1);
                    int maxDay = cal.getActualMaximum(5);
                    if (ActiveActivity.this.selectedDay > maxDay) {
                        ActiveActivity.this.selectedDay = maxDay;
                    }
                    cal.set(5, ActiveActivity.this.selectedDay);
                    ActiveActivity.this.getDaysInThisMonthHas28(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth);
                    ActiveActivity.this.mCalendarView.update(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                    ActiveActivity.this.updateCanTitle();
                    return;
                }
                ActiveActivity.this.selectedMonth = ActiveActivity.this.todayMonth;
                ActiveActivity.this.selectedYear = ActiveActivity.this.todayYear;
            }
        });
        this.mCalendarLeft.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActiveActivity.this.selectedMonth = ActiveActivity.this.selectedMonth - 1;
                if (ActiveActivity.this.selectedMonth == 0) {
                    ActiveActivity.this.selectedMonth = 12;
                    ActiveActivity.this.selectedYear = ActiveActivity.this.selectedYear - 1;
                    if (ActiveActivity.this.selectedYear < ActiveActivity.this.minYear) {
                        ActiveActivity.this.selectedMonth = 1;
                        ActiveActivity.this.selectedYear = ActiveActivity.this.minYear;
                        return;
                    }
                }
                Calendar cal = Calendar.getInstance();
                cal.set(1, ActiveActivity.this.selectedYear);
                cal.set(2, ActiveActivity.this.selectedMonth - 1);
                int maxDay = cal.getActualMaximum(5);
                if (ActiveActivity.this.selectedDay > maxDay) {
                    ActiveActivity.this.selectedDay = maxDay;
                }
                cal.set(5, ActiveActivity.this.selectedDay);
                ActiveActivity.this.getDaysInThisMonthHas28(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth);
                ActiveActivity.this.mCalendarView.update(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                ActiveActivity.this.mCalendarRight.setVisibility(0);
                ActiveActivity.this.updateCanTitle();
            }
        });
        this.mCalendarView.setOnCalendarDayClick(this.mOnCalendarDayClick);
    }

    /* access modifiers changed from: private */
    public long controlVpScroll() {
        long now_time = ((ActiveTodayFragment) this.todayDataFrags.get(this.mTodayFragVp.getCurrentItem())).getThisFragmentTime();
        if (new DateUtil(now_time, false).isToday()) {
            this.mTodayFragVp.openJustRightScroll();
        } else {
            setAllData2Frag(this.mTomorrowFragment);
        }
        return now_time;
    }

    private void showCalendar() {
        this.check = true;
        this.mCalendar.setVisibility(0);
        this.mMonthImg.setImageResource(R.mipmap.sport_calendar_up);
        if (this.mShowAction == null) {
            this.mShowAction = AnimationUtils.loadAnimation(this, R.anim.sport_module_show_canlendar);
            this.mShowAction.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    ActiveActivity.this.selectedYear = ActiveActivity.this.myYear;
                    ActiveActivity.this.selectedMonth = ActiveActivity.this.myMonth;
                    ActiveActivity.this.selectedDay = ActiveActivity.this.myDay;
                    if (ActiveActivity.this.selectedMonth == ActiveActivity.this.todayMonth && ActiveActivity.this.selectedYear == ActiveActivity.this.todayYear) {
                        ActiveActivity.this.mCalendarRight.setVisibility(4);
                    }
                    ActiveActivity.this.updateCanTitle();
                    ActiveActivity.this.getDaysInThisMonthHas28(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth);
                    ActiveActivity.this.mCalendarView.update(ActiveActivity.this.selectedYear, ActiveActivity.this.selectedMonth, ActiveActivity.this.selectedDay);
                }

                public void onAnimationEnd(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.mSportPopup.startAnimation(this.mShowAction);
    }

    /* access modifiers changed from: private */
    public void updateCanTitle() {
        this.mCalendarTitle.setText(this.months[this.selectedMonth - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.selectedYear);
    }

    /* access modifiers changed from: private */
    public void hiddenCalendar() {
        this.check = false;
        this.mMonthImg.setImageResource(R.mipmap.sport_calendar);
        if (this.mHiddenAction == null) {
            this.mHiddenAction = AnimationUtils.loadAnimation(this, R.anim.sport_module_hidden_canlendar);
            this.mHiddenAction.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ActiveActivity.this.mCalendar.setVisibility(8);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.mSportPopup.startAnimation(this.mHiddenAction);
    }

    private void initView() {
        this.mGuideView = View.inflate(this, R.layout.sport_module_active_guide_layout, null);
        this.mTitleCenterView = View.inflate(this, R.layout.sport_module_date_change_title_view, null);
        this.mTitle = (TextView) this.mTitleCenterView.findViewById(R.id.title);
        this.mMonthImg = (ImageView) this.mTitleCenterView.findViewById(R.id.month_img);
        setLeftBackTo();
        setRightImag(R.mipmap.share, new ActionOnclickListener() {
            public void onclick() {
                PermissionsUtils.handleSTORAGE(ActiveActivity.this, new PermissinCallBack() {
                    public void callBackOk() {
                        if (ActiveActivity.this.mTodayFragVp.getVisibility() == 0) {
                            ScreenLongShareUtils.shareScreenByMultiViews(ActiveActivity.this, new View[]{ActiveActivity.this.titleBar, ActiveActivity.this.mTodayFragment.mRcyHead, ActiveActivity.this.mTodayFragment.mActiveTodayRcy}, new int[]{0, 0, 0});
                        } else if (ActiveActivity.this.mCaculateContainer.getVisibility() == 0) {
                            ScreenLongShareUtils.shareScreenByMultiViews(ActiveActivity.this, new View[]{ActiveActivity.this.titleBar, ActiveActivity.this.mCaculateFragment.mScl}, new int[]{0, 0});
                        }
                    }

                    public void callBackFial() {
                    }
                });
            }
        });
        setCustomTitleView(this.mTitleCenterView);
        getTitleBar().setBackgroundColor(Color.parseColor("#186DDB"));
        this.mCalendar = findViewById(R.id.calendar);
        this.mSportPopup = (SportPopupWindow) this.mCalendar.findViewById(R.id.sport_popup);
        this.mCalendarRight = (ImageView) this.mCalendar.findViewById(R.id.calendar_right);
        this.mCalendarLeft = this.mCalendar.findViewById(R.id.calendar_left);
        this.mCalendarTitle = (TextView) this.mCalendar.findViewById(R.id.calendar_title);
        this.mCalendarView = (HistoryCalendar) this.mCalendar.findViewById(R.id.calendar_view);
        this.mCalendarView.setShowLeveTag(true);
        if (has28Today()) {
            DateUtil dateUtil = new DateUtil();
            KLog.e(TAG, dateUtil.getY_M_D());
            this.showLeveTagList.put(dateUtil.getY_M_D(), new ShowLeveTag(-16248796, dateUtil.getUnixTimestamp()));
            if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
                CalendarShowHanlder.getCalendarShowHanlder().updateSleepStatus(this, this.showLeveTagList);
            }
        }
        if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
            CalendarShowHanlder.getCalendarShowHanlder().updateSelectDate(this.selectedYear, this.selectedMonth, this.selectedDay);
        }
        this.mCalendarRight.setVisibility(4);
        updateCanTitle();
        updateTitle();
        this.mBeans = new ArrayList();
        this.mBeans.add(new Bean(R.drawable.sport_module_active_chage_time_type_selector, GpsTrackRef.TRUE_DIRECTION));
        this.mBeans.add(new Bean(R.drawable.sport_module_active_chage_time_type_selector, "D"));
        this.mALinearCheckBar = (ALinearCheckBar) findViewById(R.id.check_bar);
        this.mALinearCheckBar.setAdapter(new AChecKBarAdapter<Bean>(this, R.layout.sport_module_active_time_type_check_item, this.mBeans, 0) {
            public void bindCheckRes(View itemView, Bean bean) {
                TextView textView = (TextView) itemView.findViewById(R.id.time_type_text);
                textView.setBackground(ActiveActivity.this.getResources().getDrawable(bean.img_selector));
                textView.setText(bean.text);
            }
        });
        this.mALinearCheckBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckChanged(int position, boolean isChecked) {
                if (isChecked) {
                    if (position != 0 || !isChecked) {
                        ActiveActivity.this.mTodayFragVp.setVisibility(8);
                        ActiveActivity.this.mCaculateContainer.setVisibility(0);
                        ActiveActivity.this.hiddenCalendar();
                        ActiveActivity.this.mMonthImg.setVisibility(8);
                        ActiveActivity.this.mTitle.setText(R.string.sport_module_History);
                    } else {
                        ActiveActivity.this.mTodayFragVp.setVisibility(0);
                        ActiveActivity.this.mMonthImg.setVisibility(0);
                        ActiveActivity.this.updateTitle();
                        ActiveActivity.this.mCaculateContainer.setVisibility(8);
                    }
                    UserConfig.getInstance().setHasGuideSport(true);
                    UserConfig.getInstance().save();
                    ActiveActivity.this.mGuideView.setVisibility(8);
                }
            }
        });
        this.mTodayFragVp = (NoScrollViewPager) findViewById(R.id.today_frag_vp);
        this.mTodayFragVp.setOffscreenPageLimit(2);
        this.mTodayFragment = new ActiveTodayFragment();
        this.mYesterdayFragment = new ActiveTodayFragment();
        this.mTomorrowFragment = new ActiveTodayFragment();
        this.todayDataFrags.add(this.mYesterdayFragment);
        this.todayDataFrags.add(this.mTodayFragment);
        this.todayDataFrags.add(this.mTomorrowFragment);
        this.mTodayFragVp.setAdapter(new ActiveTodayPagerAdapter(getSupportFragmentManager(), this.todayDataFrags));
        this.mTodayFragVp.setCurrentItem(1);
        this.mTodayFragVp.openJustRightScroll();
        this.mCaculateContainer = (FrameLayout) findViewById(R.id.calculate_frag_container);
        this.mFragmentManager = getSupportFragmentManager();
        this.mCaculateContainer.setVisibility(8);
        Calendar todayCal = Calendar.getInstance();
        todayCal.set(this.selectedYear, this.selectedMonth - 1, this.selectedDay);
        this.mTodayFragment.setThisFragmentTime(todayCal.getTimeInMillis());
        todayCal.add(5, -1);
        this.mYesterdayFragment.setThisFragmentTime(todayCal.getTimeInMillis());
        todayCal.add(5, 2);
        this.mTomorrowFragment.setThisFragmentTime(todayCal.getTimeInMillis());
        this.mALinearCheckBar.post(new Runnable() {
            public void run() {
                if (!UserConfig.getInstance().isHasGuideSport()) {
                    ActiveActivity.this.getRootView().addView(ActiveActivity.this.mGuideView);
                    MarginLayoutParams params = (MarginLayoutParams) ActiveActivity.this.mGuideView.getLayoutParams();
                    params.bottomMargin = ActiveActivity.this.mALinearCheckBar.getHeight();
                    ActiveActivity.this.mGuideView.setLayoutParams(params);
                }
            }
        });
    }

    public void refreshUI(SportAllData allData) {
        for (Fragment frag : this.todayDataFrags) {
            DateUtil dateUtil = new DateUtil(((ActiveTodayFragment) frag).getThisFragmentTime(), false);
            if (dateUtil.getYear() == allData.getYear() && dateUtil.getMonth() == allData.getMonth() && dateUtil.getDay() == allData.getDay()) {
                ((ActiveTodayFragment) frag).setSportAllData(allData);
            }
        }
    }

    public void refreshDFrgUI(int year, int month) {
        if (this.mCaculateFragment != null) {
            this.mCaculateFragment.refreshUi(year, month);
        }
    }

    public void netReqLoading(boolean isLoading) {
        if (isLoading) {
            if (this.mLoadingDialog == null) {
                this.mLoadingDialog = new LoadingDialog(this);
            }
            this.mLoadingDialog.show();
        } else if (this.mLoadingDialog != null) {
            this.mLoadingDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void updateTitle() {
        if (!new DateUtil(this.selectedYear, this.selectedMonth, this.selectedDay).isToday()) {
            this.mTitle.setText(this.selectedYear + HelpFormatter.DEFAULT_OPT_PREFIX + Util.get02dStr(this.selectedMonth) + HelpFormatter.DEFAULT_OPT_PREFIX + Util.get02dStr(this.selectedDay));
        } else {
            this.mTitle.setText(R.string.sport_module_Today);
        }
    }

    private SportAllData hasSportData(DateUtil dateUtil) {
        return this.mActiveTodayPresent.getAllData(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice(), true);
    }

    /* access modifiers changed from: private */
    public void setAllData2Frag(ActiveTodayFragment fragment) {
        int i = 0;
        DateUtil dateUtil = new DateUtil(fragment.getThisFragmentTime(), false);
        Integer data_type = (Integer) this.mDataTypeMap.get(dateUtil.getSyyyyMMddDate());
        if (data_type != null) {
            i = data_type.intValue();
        }
        fragment.setNow_data_type(i);
        fragment.setSportAllData(hasSportData(dateUtil));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (CalendarShowHanlder.getCalendarShowHanlder() != null) {
            CalendarShowHanlder.getCalendarShowHanlder().destory();
        }
        super.onDestroy();
    }

    /* access modifiers changed from: 0000 */
    public void tryUploadGoogleFit() {
    }
}
