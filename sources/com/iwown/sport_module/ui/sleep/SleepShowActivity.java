package com.iwown.sport_module.ui.sleep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.network.utils.BaseUtils;
import com.iwown.lib_common.views.TitleBar.ImageAction;
import com.iwown.lib_common.views.sleepview.DSleepChartView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.service.IntentSendUtils;
import com.iwown.sport_module.ui.repository.DataRepositoryHelper;
import com.iwown.sport_module.ui.skin_loader.SleepSkinHandler;
import com.iwown.sport_module.ui.sleep.adapter.SleepAdapter;
import com.iwown.sport_module.ui.sleep.fragment.SleepHistoryFragment;
import com.iwown.sport_module.ui.sleep.fragment.TSleepViewPagerFragment;
import com.iwown.sport_module.ui.sleep.fragment.TSleepViewPagerFragment.OnFragmentInteractionListener;
import com.iwown.sport_module.ui.sleep.mvp.SleepPresenter;
import com.iwown.sport_module.ui.utils.FontUtils;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder.CallBack;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import com.socks.library.KLog;
import java.util.LinkedHashMap;
import java.util.Map;

@Route(path = "/sport/sleepactivity")
public class SleepShowActivity extends BaseActivity implements OnFragmentInteractionListener, OnClickListener, SleepHistoryFragment.OnFragmentInteractionListener {
    public static long currentTime = System.currentTimeMillis();
    private String Tag0 = TSleepViewPagerFragment.class.getSimpleName();
    private String Tag1 = SleepHistoryFragment.class.getSimpleName();
    private Button btD;
    private Button btT;
    private DSleepChartView dcvSleep;
    private FragmentManager fragmentManager;
    private AntGridView gvTimes;
    private FrameLayout idContent;
    /* access modifiers changed from: private */
    public boolean isShort;
    private LoadingDialog loadingDialog;
    private View mCalendar;
    private Fragment mContent;
    private SleepPresenter mSleepPresenter;
    /* access modifiers changed from: private */
    public int showFragmentType;
    /* access modifiers changed from: private */
    public SleepHistoryFragment sleep_history_day;
    private SleepHistoryFragment sleep_history_month;
    private SleepHistoryFragment sleep_history_week;
    /* access modifiers changed from: private */
    public TSleepViewPagerFragment tSleepViewPagerFragment;
    private TextView tv_calendar_show;
    private View vTop;

    public /* bridge */ /* synthetic */ View getTitleBar() {
        return super.getTitleBar();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_sleep_show);
        setLeftBackTo();
        getTitleBar().addAction(new ImageAction(R.drawable.share_icon) {
            public void performAction(View view) {
                if (!SleepShowActivity.this.isShort) {
                    SleepShowActivity.this.isShort = true;
                    if (SleepShowActivity.this.showFragmentType == 0 && SleepShowActivity.this.tSleepViewPagerFragment != null) {
                        SleepShowActivity.this.tSleepViewPagerFragment.shareScreen();
                    } else if (SleepShowActivity.this.showFragmentType == 1 && SleepShowActivity.this.sleep_history_day != null) {
                        SleepShowActivity.this.sleep_history_day.shareScreen();
                    }
                    SleepShowActivity.this.isShort = false;
                }
            }
        });
        setTitleTextID(R.string.sport_module_sleep_activity);
        this.fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            this.tSleepViewPagerFragment = (TSleepViewPagerFragment) getSupportFragmentManager().findFragmentByTag(this.Tag0);
            this.sleep_history_day = (SleepHistoryFragment) getSupportFragmentManager().findFragmentByTag(this.Tag1);
            this.fragmentManager.beginTransaction().addToBackStack(null);
            this.fragmentManager.beginTransaction().show(this.tSleepViewPagerFragment).hide(this.sleep_history_day).commit();
            showBottomColorBG(this.Tag0);
        }
        initView();
    }

    private void initView() {
        SleepSkinHandler.getInstance().init();
        SleepDataCacheHandler.init(this);
        FontUtils.initFonts(getApplication());
        if (this.tSleepViewPagerFragment == null) {
            this.tSleepViewPagerFragment = TSleepViewPagerFragment.newInstance("", "");
        }
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
        if (this.mSleepPresenter == null) {
            this.mSleepPresenter = new SleepPresenter(this.tSleepViewPagerFragment, DataRepositoryHelper.getSleepDataRepository(this));
        }
        this.fragmentManager.beginTransaction().addToBackStack(null);
        this.fragmentManager.beginTransaction().add(R.id.id_content, this.tSleepViewPagerFragment, this.Tag0).commit();
        this.mContent = this.tSleepViewPagerFragment;
        this.showFragmentType = 0;
        updateBG();
        this.btT = (Button) findViewById(R.id.bt_t);
        this.btD = (Button) findViewById(R.id.bt_d);
        this.tv_calendar_show = (TextView) findViewById(R.id.tv_calendar_show);
        initCalendar();
        this.btT.setOnClickListener(this);
        this.btD.setOnClickListener(this);
    }

    public void initCalendar() {
        CalendarShowHanlder.init(this);
        CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
        CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
            public void onResult(int year, int month, int day) {
                KLog.e("-----");
                if (SleepShowActivity.this.tSleepViewPagerFragment != null) {
                    DateUtil dateUtil = new DateUtil(year, month, day);
                    SleepShowActivity.currentTime = dateUtil.getTimestamp();
                    SleepShowActivity.this.tSleepViewPagerFragment.scrollToPosition(BaseUtils.getPositionByTime(dateUtil.getTimestamp(), SleepAdapter.getLastPosition()), dateUtil);
                }
            }
        });
    }

    public void updateBG() {
        setTitleBarBG(SleepSkinHandler.getInstance().getSkinTitleBarBG());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        IntentSendUtils.sendUploadSleep(this);
        this.mSleepPresenter.onDestroy();
        SleepDataCacheHandler.destory();
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            calendarShowHanlder.destory();
        }
        FontUtils.destory();
    }

    public void onClick(View view) {
        this.btT.setBackgroundResource(R.drawable.sport_module_sleep_fragment_bt_gradient0);
        this.btD.setBackgroundResource(R.drawable.sport_module_sleep_fragment_bt_gradient0);
        if (view.getId() == R.id.bt_t) {
            if (this.tSleepViewPagerFragment == null) {
                this.tSleepViewPagerFragment = TSleepViewPagerFragment.newInstance("", "");
            }
            showFragment(this.tSleepViewPagerFragment, this.Tag0);
            this.showFragmentType = 0;
        } else if (view.getId() == R.id.bt_d) {
            if (this.sleep_history_day == null) {
                this.sleep_history_day = SleepHistoryFragment.newInstance("", "");
            }
            showFragment(this.sleep_history_day, this.Tag1);
            this.showFragmentType = 1;
        }
    }

    public void showFragment(Fragment to, String tag) {
        if (this.mContent != to) {
            FragmentTransaction transaction = this.fragmentManager.beginTransaction();
            if (!to.isAdded()) {
                this.fragmentManager.beginTransaction().addToBackStack(null);
                transaction.hide(this.mContent).add(R.id.id_content, to, tag).show(to).commit();
            } else {
                this.fragmentManager.beginTransaction().addToBackStack(null);
                transaction.hide(this.mContent).show(to).commit();
            }
            this.mContent = to;
        }
        showBottomColorBG(tag);
    }

    private void showBottomColorBG(String tag) {
        KLog.e("  " + tag);
        if (this.btD != null && this.btT != null) {
            if (TextUtils.equals(tag, this.Tag0)) {
                this.btT.setBackgroundResource(R.drawable.sport_module_sleep_fragment_bt_gradient);
                this.btD.setBackgroundResource(R.drawable.sport_module_sleep_fragment_bt_gradient0);
                return;
            }
            this.btT.setBackgroundResource(R.drawable.sport_module_sleep_fragment_bt_gradient0);
            this.btD.setBackgroundResource(R.drawable.sport_module_sleep_fragment_bt_gradient);
        }
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

    public void updateCalendar(Map<String, ContentBean> statusDatas) {
        int i;
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            int color_good = getResources().getColor(R.color.level_circle_color_good);
            int color_bad = getResources().getColor(R.color.level_circle_color_bad);
            Map<String, ShowLeveTag> showLeveTagList = new LinkedHashMap<>();
            for (String key : statusDatas.keySet()) {
                ContentBean contentBean = (ContentBean) statusDatas.get(key);
                if (contentBean != null) {
                    ShowLeveTag showLeveTag = new ShowLeveTag();
                    if (contentBean.getScore() >= 100) {
                        i = color_good;
                    } else {
                        i = color_bad;
                    }
                    showLeveTag.color = i;
                    showLeveTag.unix_time = (long) contentBean.getTime();
                    showLeveTagList.put(key, showLeveTag);
                }
            }
            calendarShowHanlder.updateSleepStatus(this, showLeveTagList);
        }
    }

    public void showCalendarPop() {
        if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
            initCalendar();
        }
        if (this.showFragmentType == 0) {
            CalendarShowHanlder.getCalendarShowHanlder().showCalendar(this.tv_calendar_show);
        }
    }
}
