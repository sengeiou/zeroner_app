package com.iwown.device_module.device_alarm_schedule.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.dinuscxj.refresh.IDragDistanceConverter;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.dinuscxj.refresh.RecyclerRefreshLayout.OnRefreshListener;
import com.dinuscxj.refresh.RecyclerRefreshLayout.RefreshStyle;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.adapter.CommonAdapter;
import com.iwown.device_module.common.adapter.ViewHolder;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.view.iosStyle.SwipeMenu;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuCreator;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuItem;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuLayout.OnMenuStateChangeListener;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView.OnMenuItemClickListener;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockActivity;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockPresenter;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockView;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockPresenter;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddPhoneScheduleActivity;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddScheduleActivity;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddScheduleContract.AddScheduleView;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddSchedulePresenter;
import com.iwown.device_module.device_alarm_schedule.bean.ScheduleInfo;
import com.iwown.device_module.device_alarm_schedule.common.SimpleHead;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.Month;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.MonthDay;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.ZCalenderView;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.ZCalenderView.MonthCalendarDismissListener;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.ZCalenderView.OnDateSelectListener;
import com.iwown.device_module.device_alarm_schedule.view.widgets.SwitchItme;
import com.iwown.device_module.device_alarm_schedule.view.widgets.SwitchItme.OnSwitchChangedListener;
import com.iwown.lib_common.toast.CustomToast;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmScheduleActivity extends DeviceModuleBaseActivity implements AddScheduleView, OnClickListener, ClockView {
    /* access modifiers changed from: private */
    public static int STATE_CLOCK = 0;
    private static int STATE_SCHEDULE = 1;
    private TextView AddPhoneSchedule;
    /* access modifiers changed from: private */
    public CommonAdapter<TB_Alarmstatue> alarmAdapter = null;
    ImageView controlCalendarIv;
    LinearLayout cotrolCalendarLL;
    private TextView mAddBtn;
    /* access modifiers changed from: private */
    public List<TB_Alarmstatue> mAlarmstatues = new ArrayList();
    ZCalenderView mCalendarView;
    TextView mClock;
    private SimpleHead mHead;
    /* access modifiers changed from: private */
    public SwipeMenuListView mListViewSwipeMenu;
    /* access modifiers changed from: private */
    public String[] mMinArr;
    /* access modifiers changed from: private */
    public String[] mMonthArr;
    /* access modifiers changed from: private */
    public String[] mMonthArrSimple;
    TextView mSchedule;
    /* access modifiers changed from: private */
    public List<TB_schedulestatue> mSchedules = new ArrayList();
    /* access modifiers changed from: private */
    public RecyclerRefreshLayout mSimpleRefresh;
    TextView mTimeTv;
    private Calendar mTodayCal;
    /* access modifiers changed from: private */
    public int now_state = -1;
    LinearLayout phoneLayout;
    /* access modifiers changed from: private */
    public AddClockPresenter presenter;
    /* access modifiers changed from: private */
    public CommonAdapter<TB_schedulestatue> scheduleAdapter = null;
    protected AddSchedulePresenter schedulePresenter;
    /* access modifiers changed from: private */
    public int selectedDay;
    /* access modifiers changed from: private */
    public int selectedMonth;
    /* access modifiers changed from: private */
    public int selectedYear;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_alarm_schedule);
        initData();
        initView();
    }

    private void initData() {
        this.mMinArr = getArray(R.array.device_module_min_has_zero);
        this.presenter = new AddClockPresenter(this);
        this.schedulePresenter = new AddSchedulePresenter(this);
        this.schedulePresenter.initSdk();
        this.schedulePresenter.readDeviceInfoFromTB();
        this.schedulePresenter.newScheduleBluetoothDataParseBiz();
        this.schedulePresenter.registerReceiver();
    }

    private void initView() {
        this.mSimpleRefresh = (RecyclerRefreshLayout) findViewById(R.id.simple_refresh);
        this.mListViewSwipeMenu = (SwipeMenuListView) findViewById(R.id.listView_swipeMenu);
        this.AddPhoneSchedule = (TextView) findViewById(R.id.add_phone_schedule);
        this.mAddBtn = (TextView) findViewById(R.id.add_btn);
        this.phoneLayout = (LinearLayout) findViewById(R.id.phone_sch_layout);
        this.mTodayCal = Calendar.getInstance();
        this.mTodayCal.setTimeInMillis(System.currentTimeMillis());
        this.mMonthArr = getResources().getStringArray(R.array.device_module_months_items_complete);
        this.mMonthArrSimple = getArray(R.array.device_module_months_items);
        setLeftBackTo();
        setTitleText(this.mMonthArr[this.mTodayCal.get(2)]);
        setRightImag(R.mipmap.to_list_chedule, new ActionOnclickListener() {
            public void onclick() {
                AlarmScheduleActivity.this.startActivityForResult(new Intent(AlarmScheduleActivity.this, AlarmScheduleInfosActivity.class), 2000);
            }
        });
        setRightVisible(true);
        this.mAddBtn.setSelected(true);
        this.now_state = STATE_CLOCK;
        initSwipeMenuList();
        if (AppConfigUtil.isHealthy()) {
            this.phoneLayout.setVisibility(4);
        }
        String year = String.valueOf(this.mTodayCal.get(1));
        String month = String.valueOf(this.mTodayCal.get(2) + 1);
        String day = String.valueOf(this.mTodayCal.get(5));
        if (Utils.shouldUseY_M_D()) {
            this.mTimeTv.setText(getString(R.string.sport_module_time, new Object[]{year, month, day}));
        } else {
            this.mTimeTv.setText(getString(R.string.sport_module_time_west, new Object[]{this.mMonthArrSimple[Integer.parseInt(month) - 1], day, year}));
        }
        this.mClock.setSelected(true);
        this.mSchedule.setSelected(false);
        this.mClock.setCompoundDrawablePadding(WindowsUtil.dip2px(this, 10.0f));
        this.mSchedule.setCompoundDrawablePadding(WindowsUtil.dip2px(this, 10.0f));
        this.mSimpleRefresh.setDragDistanceConverter(new IDragDistanceConverter() {
            public float convert(float scrollDistance, float refreshDistance) {
                return 0.3f * scrollDistance;
            }
        });
        this.mSimpleRefresh.setEnabled(false);
        this.mHead = new SimpleHead(this);
        this.mHead.setWidth(WindowsUtil.getScreenWidth(this));
        this.mHead.setHeight(WindowsUtil.dip2px(this, 200.0f));
        this.mHead.setBackgroundColor(getResources().getColor(R.color.device_module_device_set_acitvites_bg));
        this.mSimpleRefresh.setRefreshView(this.mHead, new LayoutParams(-1, -2));
        this.mSimpleRefresh.setRefreshStyle(RefreshStyle.NORMAL);
        this.mSimpleRefresh.setAnimateToRefreshDuration(30);
        this.mSimpleRefresh.setRefreshTargetOffset((float) WindowsUtil.dip2px(this, 20.0f));
        if (this.schedulePresenter.isChangeDeviceSchedule(true) && BluetoothOperation.isConnected()) {
            KLog.e("更换手环 同步一次闹钟日程");
            this.schedulePresenter.updateIsChangeDevice();
        }
        initEvent();
    }

    private void initSwipeMenuList() {
        View mHeader = View.inflate(this, R.layout.device_module_schedule_head_layout, null);
        this.mCalendarView = (ZCalenderView) mHeader.findViewById(R.id.calendar_view);
        this.mTimeTv = (TextView) mHeader.findViewById(R.id.time_tv);
        this.mClock = (TextView) mHeader.findViewById(R.id.clock);
        this.mSchedule = (TextView) mHeader.findViewById(R.id.schedule);
        this.controlCalendarIv = (ImageView) mHeader.findViewById(R.id.control_calendar_iv);
        this.cotrolCalendarLL = (LinearLayout) mHeader.findViewById(R.id.control_calendar_ll);
        this.mListViewSwipeMenu.addHeaderView(mHeader);
        this.mListViewSwipeMenu.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ContextUtil.app);
                deleteItem.setBackground((Drawable) new ColorDrawable(Color.rgb(Opcodes.DIV_INT_LIT16, 19, 0)));
                deleteItem.setWidth(WindowsUtil.dip2px(ContextUtil.app, 75.0f));
                deleteItem.setTitle(AlarmScheduleActivity.this.getString(R.string.device_module_delete));
                deleteItem.setTitleSize(17);
                deleteItem.setTitleColor(-1);
                menu.addMenuItem(deleteItem);
            }
        });
        this.alarmAdapter = new CommonAdapter<TB_Alarmstatue>(this, this.mAlarmstatues, R.layout.device_module_alarm_item_layout) {
            public void convert(ViewHolder helper, int position, final TB_Alarmstatue item) {
                boolean z = true;
                KLog.i("------" + JsonUtils.toJson(item));
                SwitchItme switchItem = (SwitchItme) helper.getConvertView().findViewById(R.id.swtich_item);
                switchItem.setCotentVisible(true);
                switchItem.setContent(Utils.getRepeatString(ContextUtil.app, (byte) item.getAc_Conf()));
                switchItem.setTitle(AlarmScheduleActivity.this.getString(R.string.sport_module_time_hh_mm, new Object[]{String.valueOf(item.getAc_Hour()), AlarmScheduleActivity.this.mMinArr[item.getAc_Minute()]}));
                if (item.getOpenState() != 1) {
                    z = false;
                }
                switchItem.setOn(z);
                switchItem.setTitleSize(40.0f);
                switchItem.setOnSwitchChangedListener(new OnSwitchChangedListener() {
                    public void onSwitchChanged(boolean isOn) {
                        AlarmScheduleActivity.this.presenter.editAlarm(item.getAc_Idx(), item.getAc_Conf(), item.getAc_Hour(), item.getAc_Minute(), item.getAc_String(), "", isOn, item.getZg_mode(), item.getZg_number());
                        item.setOpenState(isOn ? 1 : 0);
                    }
                });
            }
        };
        this.scheduleAdapter = new CommonAdapter<TB_schedulestatue>(this, this.mSchedules, R.layout.device_module_schedule_item_layout) {
            public void convert(ViewHolder helper, int position, TB_schedulestatue item) {
                TextView title = (TextView) helper.getConvertView().findViewById(R.id.title);
                ((TextView) helper.getConvertView().findViewById(R.id.left_tv)).setText(AlarmScheduleActivity.this.getString(R.string.sport_module_time_hh_mm, new Object[]{AlarmScheduleActivity.this.mMinArr[item.getHour()], AlarmScheduleActivity.this.mMinArr[item.getMinute()]}));
                title.setText(item.getText());
            }
        };
        this.selectedYear = this.mTodayCal.get(1);
        this.selectedMonth = this.mTodayCal.get(2) + 1;
        this.selectedDay = this.mTodayCal.get(5);
        updateSchList();
        if (this.now_state == STATE_CLOCK) {
            this.mListViewSwipeMenu.setAdapter((ListAdapter) this.alarmAdapter);
            this.alarmAdapter.notifyDataSetChanged();
            return;
        }
        this.mListViewSwipeMenu.setAdapter((ListAdapter) this.scheduleAdapter);
        this.scheduleAdapter.notifyDataSetChanged();
    }

    private void initEvent() {
        this.mCalendarView.setMonthCalendarDismissListener(new MonthCalendarDismissListener() {
            public void whenMonthCalendarDismiss(View view) {
                AlarmScheduleActivity.this.controlCalendarIv.setImageResource(R.mipmap.open_calendar3x);
            }
        });
        this.mCalendarView.setOnDateSelectListener(new OnDateSelectListener() {
            public void onDateSelect(View view, Month month, int selectIndex) {
                AlarmScheduleActivity.this.setTitleText(AlarmScheduleActivity.this.mMonthArr[month.getMonth()]);
                MonthDay monthDay = month.getMonthDay(selectIndex);
                String year = String.valueOf(monthDay.getmYear());
                String mMonth = String.valueOf(monthDay.getmMonth() + 1);
                String day = String.valueOf(monthDay.getmDay());
                AlarmScheduleActivity.this.selectedYear = monthDay.getmYear();
                AlarmScheduleActivity.this.selectedMonth = monthDay.getmMonth() + 1;
                AlarmScheduleActivity.this.selectedDay = monthDay.getmDay();
                AlarmScheduleActivity.this.updateSchList();
                AlarmScheduleActivity.this.updateAlarmList();
                KLog.e(DeviceModuleBaseActivity.TAG, year + "/" + mMonth + "/" + day);
                if (Utils.shouldUseY_M_D()) {
                    AlarmScheduleActivity.this.mTimeTv.setText(AlarmScheduleActivity.this.getString(R.string.sport_module_time, new Object[]{year, mMonth, day}));
                    return;
                }
                AlarmScheduleActivity.this.mTimeTv.setText(AlarmScheduleActivity.this.getString(R.string.sport_module_time_west, new Object[]{AlarmScheduleActivity.this.mMonthArrSimple[Integer.parseInt(mMonth) - 1], day, year}));
            }
        });
        this.mSimpleRefresh.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                AlarmScheduleActivity.this.mCalendarView.setViewPagerVisible(true);
                AlarmScheduleActivity.this.mCalendarView.setWeekViewVisible(false);
                AlarmScheduleActivity.this.mSimpleRefresh.setRefreshing(false);
                AlarmScheduleActivity.this.controlCalendarIv.setImageResource(R.mipmap.close_calendar3x);
            }
        });
        this.mListViewSwipeMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (AlarmScheduleActivity.this.now_state == AlarmScheduleActivity.STATE_CLOCK) {
                            TB_Alarmstatue alarm = (TB_Alarmstatue) AlarmScheduleActivity.this.mAlarmstatues.get(position);
                            AlarmScheduleActivity.this.presenter.deleteAlarm(alarm.getAc_Idx());
                            KLog.e(DeviceModuleBaseActivity.TAG, "delete_alarm-->" + alarm.getAc_Idx() + "----" + JsonUtils.toJson(alarm));
                            AlarmScheduleActivity.this.updateAlarmList();
                            KLog.i("-----list---" + JsonUtils.toJson(AlarmScheduleActivity.this.mAlarmstatues));
                            AlarmScheduleActivity.this.alarmAdapter.notifyDataSetChanged();
                            return;
                        }
                        try {
                            TB_schedulestatue schedule = (TB_schedulestatue) AlarmScheduleActivity.this.mSchedules.get(position);
                            AddScheduleUtil.tbScheduleStatue = schedule;
                            AddScheduleUtil.tbScheduleStatue.setUID(String.valueOf(ContextUtil.getUID()));
                            AlarmScheduleActivity.this.schedulePresenter.deleteSchedule(AddScheduleUtil.tbScheduleStatue);
                            AlarmScheduleActivity.this.mSchedules.remove(schedule);
                            AlarmScheduleActivity.this.scheduleAdapter.notifyDataSetChanged();
                            return;
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                            return;
                        }
                    default:
                        return;
                }
            }
        });
        this.mListViewSwipeMenu.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int index = position - AlarmScheduleActivity.this.mListViewSwipeMenu.getHeaderViewsCount();
                if (index >= 0) {
                    if (AlarmScheduleActivity.this.now_state == AlarmScheduleActivity.STATE_CLOCK) {
                        TB_Alarmstatue item = (TB_Alarmstatue) AlarmScheduleActivity.this.mAlarmstatues.get(index);
                        AddScheduleUtil.packAlarmData(item.getAc_Idx(), (byte) item.getAc_Conf(), item.getAc_Hour(), item.getAc_Minute(), item.getAc_String(), "", item.getOpenState() == 1, item.getZg_mode(), item.getZg_number());
                        AlarmScheduleActivity.this.process(AddClockPresenter.STEP_CLOCK_EDIT);
                        return;
                    }
                    TB_schedulestatue schedulestatue = (TB_schedulestatue) AlarmScheduleActivity.this.mSchedules.get(index);
                    AddScheduleUtil.packScheduleData(schedulestatue.getId(), schedulestatue.getYear(), schedulestatue.getMonth(), schedulestatue.getDay(), schedulestatue.getHour(), schedulestatue.getMinute(), schedulestatue.getText(), "", schedulestatue.getZg_mode(), schedulestatue.getZg_number());
                    AlarmScheduleActivity.this.process(AddSchedulePresenter.STEP_SCHEDULE_EDIT);
                }
            }
        });
        this.mListViewSwipeMenu.setOnMenuStateChangeListener(new OnMenuStateChangeListener() {
            public void onMenuStateChange(boolean isMenuOn) {
            }
        });
        this.mClock.setOnClickListener(this);
        this.mSchedule.setOnClickListener(this);
        this.mAddBtn.setOnClickListener(this);
        this.AddPhoneSchedule.setOnClickListener(this);
        this.cotrolCalendarLL.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AlarmScheduleActivity.this.changeCalendarState();
            }
        });
        updateSchList();
        updateAlarmList();
    }

    /* access modifiers changed from: private */
    public void changeCalendarState() {
        this.mCalendarView.changeViewPagerVisible();
        this.mCalendarView.changeWeekViewVisible();
        this.mCalendarView.resetWeekView();
        if (this.mCalendarView.isViewPagerVisible()) {
            this.controlCalendarIv.setImageResource(R.mipmap.close_calendar3x);
        } else {
            this.controlCalendarIv.setImageResource(R.mipmap.open_calendar3x);
        }
    }

    public void process(int stepNow) {
        if (stepNow == AddClockPresenter.STEP_CLOCK_ADD) {
            toAddClock();
        } else if (stepNow == AddClockPresenter.STEP_CLOCK_EDIT) {
            toEditClock();
        } else if (stepNow == AddSchedulePresenter.STEP_SCHEDULE_ADD) {
            toAddSchedule();
        } else if (stepNow == AddSchedulePresenter.STEP_SCHEDULE_EDIT) {
            toEditSchedule();
        }
    }

    private void toAddClock() {
        int max;
        if (BluetoothOperation.isZg()) {
            max = 4;
        } else {
            max = 6;
        }
        int alarmID = this.presenter.isAddAlarm(max);
        if (alarmID != -1) {
            Intent intent = new Intent();
            intent.setClass(this, AddClockActivity.class);
            intent.putExtra(AddClockPresenter.KEY_ADD_OR_EDIT, AddClockPresenter.TO_ADD);
            intent.putExtra(AddClockPresenter.KEY_REPEAT, -1);
            intent.putExtra(AddClockPresenter.KEY_CLOCK_ID, alarmID);
            startActivityForResult(intent, AddClockPresenter.REQUEST_ADD_CLOCK);
        } else if (BluetoothOperation.isZg()) {
            CustomToast.makeText(this, getString(R.string.device_module_zg_schedule_msg_alarm_out));
        } else {
            CustomToast.makeText(this, getString(R.string.device_module_schedule_msg_alarm_out));
        }
    }

    private void toEditClock() {
        Intent intent = new Intent(this, AddClockActivity.class);
        intent.putExtra(AddClockPresenter.KEY_ADD_OR_EDIT, AddClockPresenter.TO_EDIT);
        startActivityForResult(intent, AddClockPresenter.REQUEST_EDIT_CLOCK);
    }

    private void toAddSchedule() {
        if (checkBeforeAddOrEdit(this.now_state, AddSchedulePresenter.TO_ADD)) {
            Intent intent = new Intent();
            intent.setClass(this, AddScheduleActivity.class);
            AddScheduleUtil.packScheduleData(-1, this.selectedYear, this.selectedMonth, this.selectedDay, -1, -1, "", "", 1, 1);
            intent.putExtra(AddSchedulePresenter.KEY_SCH_YEAR, this.selectedYear);
            intent.putExtra(AddSchedulePresenter.KEY_SCH_MONTH, this.selectedMonth);
            intent.putExtra(AddSchedulePresenter.KEY_SCH_DAY, this.selectedDay);
            intent.putExtra(AddSchedulePresenter.KEY_ADD_OR_EDIT, AddSchedulePresenter.TO_ADD);
            startActivityForResult(intent, AddSchedulePresenter.REQUEST_ADD_SCH);
        }
    }

    private void toEditSchedule() {
        Intent intent = new Intent(this, AddScheduleActivity.class);
        intent.putExtra(AddSchedulePresenter.KEY_ADD_OR_EDIT, AddSchedulePresenter.TO_EDIT);
        startActivityForResult(intent, AddSchedulePresenter.REQUEST_EDIT_SCH);
    }

    public boolean checkBeforeAddOrEdit(int setType, int operateType) {
        if (!BluetoothOperation.isConnected()) {
            CustomToast.makeText(this, getString(R.string.device_module_schedule_msg_no_connect));
            return false;
        }
        if (setType != STATE_CLOCK) {
            if (!isSupportSchedule(true)) {
                return false;
            }
            Calendar curCal = Calendar.getInstance();
            curCal.setTimeInMillis(System.currentTimeMillis());
            if (AddScheduleUtil.isBeforeToday(this.selectedYear, this.selectedMonth, this.selectedDay, curCal)) {
                KLog.e(TAG, Integer.valueOf(this.selectedYear + this.selectedMonth + this.selectedDay));
                CustomToast.makeText(this, getString(R.string.device_module_schedule_msg_schedule_before_today));
                return false;
            }
            int isadd = this.schedulePresenter.isAddSchedule(this.selectedYear, this.selectedMonth, this.selectedDay);
            KLog.e("====================" + isadd);
            if (1 == isadd || 3 == isadd) {
                CustomToast.makeText(this, getString(R.string.device_module_schedule_msg_effect_max1) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.schedulePresenter.getMaxSetableNum() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.device_module_schedule_msg_effect_max2));
                return false;
            } else if (2 == isadd) {
                CustomToast.makeText(this, getString(R.string.device_module_schedule_msg_perday_max1) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.schedulePresenter.getPerdaySetableNum() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.device_module_schedule) + "!");
                return false;
            } else if (isadd == 0) {
                return true;
            }
        }
        return true;
    }

    private boolean isSupportSchedule(boolean isShowMsg) {
        if (this.schedulePresenter.getIsSupportSchedule()) {
            return true;
        }
        if (isShowMsg) {
            CustomToast.makeText(this, getString(R.string.device_module_schedule_msg_no_support));
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.schedulePresenter.unRegisterReceiver();
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.clock) {
            this.mClock.setSelected(true);
            this.mSchedule.setSelected(false);
            this.now_state = STATE_CLOCK;
            this.phoneLayout.setVisibility(4);
            this.mListViewSwipeMenu.setAdapter((ListAdapter) this.alarmAdapter);
            this.alarmAdapter.notifyDataSetChanged();
        } else if (i == R.id.schedule) {
            this.mClock.setSelected(false);
            this.mSchedule.setSelected(true);
            this.now_state = STATE_SCHEDULE;
            if (!AppConfigUtil.isHealthy(this)) {
                this.phoneLayout.setVisibility(0);
            }
            this.mListViewSwipeMenu.setAdapter((ListAdapter) this.scheduleAdapter);
            this.scheduleAdapter.notifyDataSetChanged();
        } else if (i == R.id.add_btn) {
            if (this.now_state == STATE_CLOCK) {
                process(AddClockPresenter.STEP_CLOCK_ADD);
            } else {
                process(AddSchedulePresenter.STEP_SCHEDULE_ADD);
            }
        } else if (i == R.id.add_phone_schedule && checkBeforeAddOrEdit(this.now_state, AddSchedulePresenter.TO_ADD)) {
            Intent intent = new Intent();
            intent.setClass(this, AddPhoneScheduleActivity.class);
            AddScheduleUtil.packScheduleData(-1, this.selectedYear, this.selectedMonth, this.selectedDay, -1, -1, "", "", 1, 1);
            intent.putExtra(AddSchedulePresenter.KEY_SCH_YEAR, this.selectedYear);
            intent.putExtra(AddSchedulePresenter.KEY_SCH_MONTH, this.selectedMonth);
            intent.putExtra(AddSchedulePresenter.KEY_SCH_DAY, this.selectedDay);
            intent.putExtra(AddSchedulePresenter.KEY_ADD_OR_EDIT, AddSchedulePresenter.TO_ADD);
            startActivityForResult(intent, AddSchedulePresenter.REQUEST_ADD_SCH);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddClockPresenter.REQUEST_ADD_CLOCK) {
            KLog.e(TAG, "REQUEST_ADD_CLOCK");
            if (resultCode == -1) {
                this.presenter.addAlarm(AddScheduleUtil.dataID, AddScheduleUtil.dataByteWeek, AddScheduleUtil.dataHour, AddScheduleUtil.dataMinute, AddScheduleUtil.dataItem, AddScheduleUtil.dataRemind, AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
                updateAlarmList();
            }
        } else if (requestCode == AddClockPresenter.REQUEST_EDIT_CLOCK) {
            if (resultCode == -1) {
                KLog.e(TAG, "REQUEST_EDIT_CLOCK");
                this.presenter.editAlarm(AddScheduleUtil.dataID, AddScheduleUtil.dataByteWeek, AddScheduleUtil.dataHour, AddScheduleUtil.dataMinute, AddScheduleUtil.dataItem, AddScheduleUtil.dataRemind, AddScheduleUtil.dataIsOpen, AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
            } else if (resultCode == AddClockPresenter.RESULT_CLOCK_DELETE) {
                KLog.e(TAG, "REQUEST_EDIT_CLOCK_DELETE");
                this.presenter.deleteAlarm(AddScheduleUtil.dataID);
            }
            updateAlarmList();
        } else if (requestCode == AddSchedulePresenter.REQUEST_ADD_SCH) {
            if (resultCode == -1) {
                this.schedulePresenter.addSchedule(AddScheduleUtil.dataYear, AddScheduleUtil.dataMonth, AddScheduleUtil.dataDay, AddScheduleUtil.dataHour, AddScheduleUtil.dataMinute, AddScheduleUtil.dataItem, AddScheduleUtil.dataRemind, AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
            } else if (resultCode == AddSchedulePresenter.PHONE_SCHEDULE) {
                for (int i = 0; i < AddScheduleUtil.list.size(); i++) {
                    this.schedulePresenter.addSchedule(((ScheduleInfo) AddScheduleUtil.list.get(i)).getYear(), ((ScheduleInfo) AddScheduleUtil.list.get(i)).getMonth(), ((ScheduleInfo) AddScheduleUtil.list.get(i)).getDay(), ((ScheduleInfo) AddScheduleUtil.list.get(i)).getHour(), ((ScheduleInfo) AddScheduleUtil.list.get(i)).getMin(), ((ScheduleInfo) AddScheduleUtil.list.get(i)).getTitle(), ((ScheduleInfo) AddScheduleUtil.list.get(i)).getTitle(), AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
                }
            }
            updateSchList();
        } else if (requestCode == AddSchedulePresenter.REQUEST_EDIT_SCH) {
            if (resultCode == -1) {
                KLog.e(TAG, "REQUEST_EDIT_SCH");
                this.schedulePresenter.editSchedule(AddScheduleUtil.dataID, AddScheduleUtil.dataYear, AddScheduleUtil.dataMonth, AddScheduleUtil.dataDay, AddScheduleUtil.dataHour, AddScheduleUtil.dataMinute, AddScheduleUtil.dataItem, AddScheduleUtil.dataRemind, AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
            } else if (resultCode == AddSchedulePresenter.RESULT_SCH_DELETE) {
                KLog.e(TAG, "REQUEST_EDIT_SCH_DELETE");
                AddScheduleUtil.tbScheduleStatue.setId(AddScheduleUtil.dataID);
                AddScheduleUtil.tbScheduleStatue.setYear(AddScheduleUtil.dataYear);
                AddScheduleUtil.tbScheduleStatue.setMonth(AddScheduleUtil.dataMonth);
                AddScheduleUtil.tbScheduleStatue.setDay(AddScheduleUtil.dataDay);
                AddScheduleUtil.tbScheduleStatue.setHour(AddScheduleUtil.dataHour);
                AddScheduleUtil.tbScheduleStatue.setMinute(AddScheduleUtil.dataMinute);
                AddScheduleUtil.tbScheduleStatue.setText(AddScheduleUtil.dataItem);
                AddScheduleUtil.tbScheduleStatue.setRemind("");
                AddScheduleUtil.tbScheduleStatue.setUID(String.valueOf(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid)));
                this.schedulePresenter.deleteSchedule(AddScheduleUtil.tbScheduleStatue);
            }
            updateSchList();
        } else if (requestCode == 2000) {
            updateAlarmList();
            updateSchList();
        }
    }

    /* access modifiers changed from: private */
    public void updateAlarmList() {
        this.mAlarmstatues.clear();
        this.mAlarmstatues.addAll(this.presenter.getAllAlarmData());
        this.mListViewSwipeMenu.setAdapter((ListAdapter) this.alarmAdapter);
        this.alarmAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void updateSchList() {
        this.mSchedules.clear();
        this.mSchedules.addAll(this.schedulePresenter.getSelectedDayScheduleData(this.selectedYear, this.selectedMonth, this.selectedDay));
        this.scheduleAdapter.notifyDataSetChanged();
    }

    public void setPresenter(ClockPresenter presenter2) {
    }

    public void readScheduleError(int errorCode, String action) {
        if (errorCode == 4) {
            if (action.equalsIgnoreCase(AddSchedulePresenter.queue_state_bluetooth_connect)) {
                KLog.d(TAG, "蓝牙连接");
                updateSchList();
                updateAlarmList();
            }
        } else if (errorCode == 0) {
            KLog.d(TAG, "QueueManagerResult 成功");
            this.schedulePresenter.updateIsChangeDevice();
        }
    }

    public void showDialog(boolean show) {
    }
}
