package com.iwown.sport_module.ui.heart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.HeartStatusData.ContentBean;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.network.utils.BaseUtils;
import com.iwown.lib_common.views.TitleBar.ImageAction;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.service.IntentSendUtils;
import com.iwown.sport_module.ui.heart.HeartAdapter.DateClickListener;
import com.iwown.sport_module.ui.heart.HeartContract.HeartPresenter1;
import com.iwown.sport_module.ui.heart.HeartContract.HeartView;
import com.iwown.sport_module.ui.repository.DataRepositoryHelper;
import com.iwown.sport_module.ui.sleep.RecyclerOnPagerListener;
import com.iwown.sport_module.ui.sleep.adapter.SleepAdapter;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder.CallBack;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.LinkedHashMap;
import java.util.Map;

public class HeartActivity extends BaseActivity implements HeartView, DateClickListener {
    private ConstraintLayout cl_content;
    private CoordinatorLayout cl_main;
    private long currentTime = System.currentTimeMillis();
    /* access modifiers changed from: private */
    public HeartAdapter heartAdapter;
    /* access modifiers changed from: private */
    public HeartPresnter heartPresnter;
    /* access modifiers changed from: private */
    public boolean isShort;
    private LoadingDialog loadingDialog;
    /* access modifiers changed from: private */
    public RecyclerViewPager rvp_hearts;
    private DateUtil toDay;
    private TextView tv_calendar_show;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_heart);
        setLeftBackTo();
        getTitleBar().addAction(new ImageAction(R.drawable.share_icon) {
            public void performAction(View view) {
                if (!HeartActivity.this.isShort) {
                    HeartActivity.this.isShort = true;
                    ScreenLongShareUtils.shareScreenView(HeartActivity.this, HeartActivity.this.heartAdapter.getScroll_view());
                    HeartActivity.this.isShort = false;
                }
            }
        });
        setTitleBarBG(getResources().getColor(R.color.heart_top));
        setTitleTextID(R.string.sport_module_heart_rate);
        initView();
    }

    private void initView() {
        this.rvp_hearts = (RecyclerViewPager) findViewById(R.id.rvp_hearts);
        this.tv_calendar_show = (TextView) findViewById(R.id.tv_calendar_show);
        this.heartAdapter = new HeartAdapter(this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), 0, false);
        this.rvp_hearts.setLayoutManager(layout);
        this.rvp_hearts.setAdapter(this.heartAdapter);
        this.heartPresnter = new HeartPresnter(this, DataRepositoryHelper.getHeartDataRepository(this));
        this.rvp_hearts.addOnScrollListener(new RecyclerOnPagerListener(layout) {
            public void callBack(int firstCompletelyVisibleItemPosition) {
                long preOrNextTimeByDay = DateUtil.getPreOrNextTimeByDay((long) (SleepAdapter.getLastPosition() - firstCompletelyVisibleItemPosition));
                DateUtil dateUtil = new DateUtil(preOrNextTimeByDay, false);
                if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
                    HeartActivity.this.initCalendar();
                }
                CalendarShowHanlder.getCalendarShowHanlder().updateSelectDate(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());
                HeartShowData heartShowData = new HeartShowData();
                heartShowData.dateUtil = dateUtil;
                HeartActivity.this.heartAdapter.setRecyclerViewAndData(HeartActivity.this.rvp_hearts, heartShowData);
                if (HeartActivity.this.heartPresnter != null) {
                    HeartActivity.this.heartPresnter.loadData(new DateUtil(preOrNextTimeByDay, false));
                }
            }
        });
        this.rvp_hearts.scrollToPosition(SleepAdapter.getLastPosition());
        this.toDay = new DateUtil();
        initCalendar();
    }

    /* access modifiers changed from: private */
    public void initCalendar() {
        CalendarShowHanlder.init(this);
        CalendarShowHanlder.getCalendarShowHanlder().setRoundColor(Color.parseColor("#D4572E"));
        CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
        CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
            public void onResult(int year, int month, int day) {
                HeartActivity.this.rvp_hearts.scrollToPosition(BaseUtils.getPositionByTime(new DateUtil(year, month, day).getTimestamp(), SleepAdapter.getLastPosition()));
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            calendarShowHanlder.destory();
            this.heartPresnter.onDestroy();
            IntentSendUtils.sendUploadHeart(this);
        }
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public Context getContext() {
        return getApplicationContext();
    }

    public void showDatas(HeartShowData heartShowData) {
        this.heartAdapter.setRecyclerViewAndData(this.rvp_hearts, heartShowData);
    }

    public void dismissLoading() {
        if (this.loadingDialog != null && !isFinishing()) {
            this.loadingDialog.dismiss();
        }
    }

    public void showLoading() {
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
        this.loadingDialog.show();
    }

    public void updateCalendar(Map<String, ContentBean> statusDatas) {
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            int color_good = getResources().getColor(R.color.base_text_color_black_1);
            Map<String, ShowLeveTag> showLeveTagList = new LinkedHashMap<>();
            for (String key : statusDatas.keySet()) {
                ContentBean contentBean = (ContentBean) statusDatas.get(key);
                ShowLeveTag showLeveTag = new ShowLeveTag();
                showLeveTag.color = color_good;
                showLeveTag.unix_time = (long) contentBean.getUnix_time();
                showLeveTagList.put(key, showLeveTag);
            }
            calendarShowHanlder.updateSleepStatus(this, showLeveTagList);
        }
    }

    public void setPresenter(HeartPresenter1 presenter) {
    }

    public void onDateClickListener() {
        if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
            initCalendar();
        }
        CalendarShowHanlder.getCalendarShowHanlder().showCalendar(this.tv_calendar_show);
    }
}
