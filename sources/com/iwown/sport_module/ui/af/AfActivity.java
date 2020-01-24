package com.iwown.sport_module.ui.af;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.github.mikephil.charting.charts.ScatterChart;
import com.iwown.data_link.af.AfContenBean;
import com.iwown.data_link.af.ModuleRouterRRIService;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.utils.UI;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.af.presenter.AfPresenter;
import com.iwown.sport_module.ui.af.presenter.IAFListener;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder.CallBack;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.cli.HelpFormatter;

public class AfActivity extends BaseActivity implements OnClickListener, IAFListener {
    private String dataFrom;
    private String date;
    /* access modifiers changed from: private */
    public DateUtil dateUtil;
    /* access modifiers changed from: private */
    public AfPresenter presenter;
    /* access modifiers changed from: private */
    public TextView tv_calendar_show;
    private TextView tv_chat_name;
    /* access modifiers changed from: private */
    public TextView tv_chat_status;
    private long uid;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_af);
        this.uid = UserConfig.getInstance().getNewUID();
        this.dataFrom = UserConfig.getInstance().getDevice();
        initView();
        initCalendar();
        initData();
    }

    private void initData() {
        DateUtil dateUtil2 = new DateUtil();
        this.tv_calendar_show.setText(String.format(getResources().getString(R.string.sport_module_sleep_sync_time), new Object[]{""}));
        this.presenter.showData(dateUtil2.getSyyyyMMddDate());
        updatePoint();
    }

    private void initView() {
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.heart_top));
        setTitleTextID(R.string.sport_module_af);
        ScatterChart scatterChart = (ScatterChart) findViewById(R.id.mScatterChart);
        this.tv_calendar_show = (TextView) findViewById(R.id.tv_calendar_show);
        this.tv_chat_status = (TextView) findViewById(R.id.tv_chat_status);
        this.tv_chat_name = (TextView) findViewById(R.id.tv_chat_name);
        this.tv_calendar_show.setOnClickListener(this);
        this.tv_chat_name.setOnClickListener(this);
        this.presenter = new AfPresenter(this, this.uid, this.dataFrom, scatterChart, this);
        this.tv_chat_status.setText(getResources().getString(R.string.af_result, new Object[]{HelpFormatter.DEFAULT_LONG_OPT_PREFIX}));
    }

    private void initCalendar() {
        if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
            CalendarShowHanlder.init(this);
        }
        CalendarShowHanlder.getCalendarShowHanlder().setRoundColor(Color.parseColor("#D4572E"));
        CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
        CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
            public void onResult(int year, int month, int day) {
                AfActivity.this.tv_chat_status.setText(AfActivity.this.getResources().getString(R.string.af_result, new Object[]{HelpFormatter.DEFAULT_OPT_PREFIX}));
                DateUtil dateUtil = new DateUtil(year, month, day);
                AfActivity.this.dateUtil = dateUtil;
                if (dateUtil.isToday()) {
                    AfActivity.this.tv_calendar_show.setText(String.format(AfActivity.this.getResources().getString(R.string.sport_module_sleep_sync_time), new Object[]{""}));
                } else {
                    AfActivity.this.tv_calendar_show.setText(dateUtil.getY_M_D());
                }
                AfActivity.this.presenter.showData(dateUtil.getSyyyyMMddDate());
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.tv_calendar_show) {
            if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
                initCalendar();
            }
            CalendarShowHanlder.getCalendarShowHanlder().showCalendar(this.tv_calendar_show);
        } else if (v.getId() == R.id.tv_chat_name) {
            UI.startActivity(this, AfDescriptionActivity.class);
        }
    }

    public void showAfData(List<Integer> afData) {
        if (this.dateUtil == null) {
            this.dateUtil = new DateUtil();
        }
        this.presenter.getAfResult(this.dateUtil.getSyyyyMMddDate(), 0, afData);
    }

    public void showAfDataByHour(SparseArray<List<Integer>> sparseArray) {
    }

    private void updatePoint() {
        Map<String, AfContenBean> calendarMap = ModuleRouterRRIService.getInstance().getCalendarMap(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            int color_good = getResources().getColor(R.color.base_text_color_black_1);
            Map<String, ShowLeveTag> showLeveTagList = new LinkedHashMap<>();
            for (String key : calendarMap.keySet()) {
                AfContenBean bean = (AfContenBean) calendarMap.get(key);
                ShowLeveTag showLeveTag = new ShowLeveTag();
                showLeveTag.color = color_good;
                showLeveTag.unix_time = (long) bean.getUnix_time();
                showLeveTagList.put(key, showLeveTag);
            }
            calendarShowHanlder.updateSleepStatus(this, showLeveTagList);
        }
    }

    public void showAfResult(int result) {
        String string = getResources().getString(result);
        this.tv_chat_status.setText(getResources().getString(R.string.af_result, new Object[]{string}));
        if (this.dateUtil == null || this.dateUtil.isToday()) {
            HealthDataEventBus.updateAfEvent();
        }
    }
}
