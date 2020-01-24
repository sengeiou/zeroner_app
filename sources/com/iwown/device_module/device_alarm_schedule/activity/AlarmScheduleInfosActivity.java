package com.iwown.device_module.device_alarm_schedule.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.adapter.CommonAdapter;
import com.iwown.device_module.common.adapter.ViewHolder;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.view.iosStyle.SwipeMenu;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuCreator;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuItem;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView.OnMenuItemClickListener;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockActivity;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockPresenter;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockView;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockPresenter;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddScheduleActivity;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddSchedulePresenter;
import com.iwown.device_module.device_alarm_schedule.common.ScheduleManager;
import com.iwown.device_module.device_alarm_schedule.eventbus.ScheduleEvent;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_alarm_schedule.view.widgets.SwitchItme;
import com.iwown.device_module.device_alarm_schedule.view.widgets.SwitchItme.OnSwitchChangedListener;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

public class AlarmScheduleInfosActivity extends DeviceModuleBaseActivity implements OnClickListener, ClockView {
    /* access modifiers changed from: private */
    public static int STATE_CLOCK = 0;
    private static int STATE_SCHEDULE = 1;
    /* access modifiers changed from: private */
    public CommonAdapter<TB_Alarmstatue> alarmAdapter;
    /* access modifiers changed from: private */
    public CommonAdapter<TB_schedulestatue> mAdapter;
    /* access modifiers changed from: private */
    public List<TB_Alarmstatue> mAlarmstatues = new ArrayList();
    private TextView mClock;
    /* access modifiers changed from: private */
    public String[] mDayArr;
    SwipeMenuListView mListViewSwipeMenu;
    /* access modifiers changed from: private */
    public String[] mMinArr;
    /* access modifiers changed from: private */
    public String[] mMonthArr;
    private TextView mSchedule;
    /* access modifiers changed from: private */
    public List<TB_schedulestatue> mSchedules;
    /* access modifiers changed from: private */
    public int now_state;
    /* access modifiers changed from: private */
    public AddClockPresenter presenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_schedule_infos);
        EventBus.getDefault().register(this);
        initView();
        initSwipeMenuList();
        initEvent();
    }

    private void initEvent() {
        this.mListViewSwipeMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (AlarmScheduleInfosActivity.this.now_state == AlarmScheduleInfosActivity.STATE_CLOCK) {
                            TB_Alarmstatue alarm = (TB_Alarmstatue) AlarmScheduleInfosActivity.this.mAlarmstatues.get(position);
                            AlarmScheduleInfosActivity.this.presenter.deleteAlarm(alarm.getAc_Idx());
                            KLog.e(DeviceModuleBaseActivity.TAG, "delete_alarm-->" + alarm.getAc_Idx());
                            AlarmScheduleInfosActivity.this.mAlarmstatues.remove(alarm);
                            AlarmScheduleInfosActivity.this.alarmAdapter.notifyDataSetChanged();
                            return;
                        }
                        AddScheduleUtil.tbScheduleStatue = (TB_schedulestatue) AlarmScheduleInfosActivity.this.mSchedules.get(position);
                        AddScheduleUtil.tbScheduleStatue.setUID(String.valueOf(ContextUtil.getUID()));
                        ScheduleManager.getInstance().deleteSchedule(AddScheduleUtil.tbScheduleStatue);
                        AlarmScheduleInfosActivity.this.mSchedules.remove(AlarmScheduleInfosActivity.this.mSchedules.get(position));
                        AlarmScheduleInfosActivity.this.mAdapter.notifyDataSetChanged();
                        return;
                    default:
                        return;
                }
            }
        });
        this.mListViewSwipeMenu.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int index = position - AlarmScheduleInfosActivity.this.mListViewSwipeMenu.getHeaderViewsCount();
                KLog.i("================index==================" + index);
                if (index >= 0) {
                    if (AlarmScheduleInfosActivity.this.now_state == AlarmScheduleInfosActivity.STATE_CLOCK) {
                        TB_Alarmstatue item = (TB_Alarmstatue) AlarmScheduleInfosActivity.this.mAlarmstatues.get(index);
                        AddScheduleUtil.packAlarmData(item.getAc_Idx(), (byte) item.getAc_Conf(), item.getAc_Hour(), item.getAc_Minute(), item.getAc_String(), "", item.getOpenState() == 1, item.getZg_mode(), item.getZg_number());
                        AlarmScheduleInfosActivity.this.toEditClock();
                        return;
                    }
                    TB_schedulestatue schedulestatue = (TB_schedulestatue) AlarmScheduleInfosActivity.this.mSchedules.get(index);
                    AddScheduleUtil.packScheduleData(schedulestatue.getId(), schedulestatue.getYear(), schedulestatue.getMonth(), schedulestatue.getDay(), schedulestatue.getHour(), schedulestatue.getMinute(), schedulestatue.getText(), "", schedulestatue.getZg_mode(), schedulestatue.getZg_number());
                    AlarmScheduleInfosActivity.this.toEditSchedule();
                }
            }
        });
    }

    private void initView() {
        this.now_state = STATE_CLOCK;
        setLeftBtn(new ActionOnclickListener() {
            public void onclick() {
                AlarmScheduleInfosActivity.this.setResult(2000);
                AlarmScheduleInfosActivity.this.finish();
            }
        });
        this.presenter = new AddClockPresenter(this);
        this.mListViewSwipeMenu = (SwipeMenuListView) findViewById(R.id.listView_swipeMenu);
        setTitleText(R.string.device_module_schedule_alarm_list);
        View mHeader = View.inflate(this, R.layout.device_module_alarm_schedule_list_title, null);
        this.mClock = (TextView) mHeader.findViewById(R.id.clock);
        this.mSchedule = (TextView) mHeader.findViewById(R.id.schedule);
        this.mListViewSwipeMenu.addHeaderView(mHeader);
        this.mClock.setOnClickListener(this);
        this.mSchedule.setOnClickListener(this);
    }

    private void initSwipeMenuList() {
        this.mListViewSwipeMenu.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ContextUtil.app);
                deleteItem.setBackground((Drawable) new ColorDrawable(Color.rgb(Opcodes.DIV_INT_LIT16, 19, 0)));
                deleteItem.setWidth(WindowsUtil.dip2px(ContextUtil.app, 75.0f));
                deleteItem.setTitle(AlarmScheduleInfosActivity.this.getString(R.string.device_module_delete));
                deleteItem.setTitleSize(17);
                deleteItem.setTitleColor(-1);
                menu.addMenuItem(deleteItem);
            }
        });
        this.mAlarmstatues = DataSupport.where("uid=?", String.valueOf(ContextUtil.getUID())).find(TB_Alarmstatue.class);
        this.alarmAdapter = new CommonAdapter<TB_Alarmstatue>(this, this.mAlarmstatues, R.layout.device_module_alarm_item_layout) {
            public void convert(ViewHolder helper, int position, final TB_Alarmstatue item) {
                boolean z = true;
                SwitchItme switchItem = (SwitchItme) helper.getConvertView().findViewById(R.id.swtich_item);
                switchItem.setCotentVisible(true);
                switchItem.setContent(Utils.getRepeatString(ContextUtil.app, (byte) item.getAc_Conf()));
                switchItem.setTitle(AlarmScheduleInfosActivity.this.getString(R.string.sport_module_time_hh_mm, new Object[]{String.valueOf(item.getAc_Hour()), AlarmScheduleInfosActivity.this.mMinArr[item.getAc_Minute()]}));
                if (item.getOpenState() != 1) {
                    z = false;
                }
                switchItem.setOn(z);
                switchItem.setTitleSize(40.0f);
                switchItem.setOnSwitchChangedListener(new OnSwitchChangedListener() {
                    public void onSwitchChanged(boolean isOn) {
                        AlarmScheduleInfosActivity.this.presenter.editAlarm(item.getAc_Idx(), item.getAc_Conf(), item.getAc_Hour(), item.getAc_Minute(), item.getAc_String(), "", isOn, item.getZg_mode(), item.getZg_number());
                        item.setOpenState(isOn ? 1 : 0);
                    }
                });
            }
        };
        this.mSchedules = DataSupport.where("uid=?", String.valueOf(ContextUtil.getUID())).find(TB_schedulestatue.class);
        this.mMonthArr = getArray(R.array.device_module_months_items_complete);
        this.mDayArr = getArray(R.array.device_module_day_with_th);
        this.mMinArr = getArray(R.array.device_module_min_has_zero);
        this.mAdapter = new CommonAdapter<TB_schedulestatue>(this, this.mSchedules, R.layout.device_module_schedule_info_item) {
            public void convert(ViewHolder helper, int position, TB_schedulestatue item) {
                helper.setText(R.id.time, AlarmScheduleInfosActivity.this.mMonthArr[item.getMonth() - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + AlarmScheduleInfosActivity.this.mDayArr[item.getDay() - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + AlarmScheduleInfosActivity.this.getString(R.string.sport_module_time_hh_mm, new Object[]{String.valueOf(item.getHour()), AlarmScheduleInfosActivity.this.mMinArr[item.getMinute()]}));
                helper.setText(R.id.title, item.getText());
            }
        };
        this.mListViewSwipeMenu.setAdapter((ListAdapter) this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        if (this.now_state == STATE_CLOCK) {
            this.mListViewSwipeMenu.setAdapter((ListAdapter) this.alarmAdapter);
            this.alarmAdapter.notifyDataSetChanged();
            this.mClock.performClick();
            return;
        }
        this.mListViewSwipeMenu.setAdapter((ListAdapter) this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        this.mSchedule.performClick();
    }

    /* access modifiers changed from: private */
    public void toEditSchedule() {
        Intent intent = new Intent(this, AddScheduleActivity.class);
        intent.putExtra(AddSchedulePresenter.KEY_ADD_OR_EDIT, AddSchedulePresenter.TO_EDIT);
        startActivityForResult(intent, AddSchedulePresenter.REQUEST_EDIT_SCH);
    }

    /* access modifiers changed from: private */
    public void toEditClock() {
        Intent intent = new Intent(this, AddClockActivity.class);
        intent.putExtra(AddClockPresenter.KEY_ADD_OR_EDIT, AddClockPresenter.TO_EDIT);
        startActivityForResult(intent, AddClockPresenter.REQUEST_EDIT_CLOCK);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ScheduleEvent event) {
    }

    public void updateUI() {
        initSwipeMenuList();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddSchedulePresenter.REQUEST_EDIT_SCH) {
            if (resultCode == -1) {
                KLog.e(TAG, "REQUEST_EDIT_SCH");
                ScheduleManager.getInstance().editSchedule(AddScheduleUtil.dataID, AddScheduleUtil.dataYear, AddScheduleUtil.dataMonth, AddScheduleUtil.dataDay, AddScheduleUtil.dataHour, AddScheduleUtil.dataMinute, AddScheduleUtil.dataItem, AddScheduleUtil.dataRemind, AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
                this.mSchedules.clear();
                this.mSchedules.addAll(DataSupport.where("uid=?", String.valueOf(ContextUtil.app)).find(TB_schedulestatue.class));
                this.mAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new ScheduleEvent(null, ScheduleEvent.TO_EDIT));
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
                AddScheduleUtil.tbScheduleStatue.setUID(String.valueOf(ContextUtil.getUID()));
                ScheduleManager.getInstance().deleteSchedule(AddScheduleUtil.tbScheduleStatue);
                Iterator<TB_schedulestatue> schedulestatues = this.mSchedules.iterator();
                while (schedulestatues.hasNext()) {
                    TB_schedulestatue schedule = (TB_schedulestatue) schedulestatues.next();
                    if (schedule.getId() == AddScheduleUtil.tbScheduleStatue.getId() && schedule.getUID().equalsIgnoreCase(AddScheduleUtil.tbScheduleStatue.getUID())) {
                        schedulestatues.remove();
                        this.mAdapter.notifyDataSetChanged();
                    }
                }
                EventBus.getDefault().post(new ScheduleEvent(AddScheduleUtil.tbScheduleStatue, ScheduleEvent.TO_DELETE));
            }
            updateUI();
        } else if (requestCode == AddClockPresenter.REQUEST_EDIT_CLOCK) {
            if (resultCode == -1) {
                KLog.e(TAG, "REQUEST_EDIT_CLOCK");
                this.presenter.editAlarm(AddScheduleUtil.dataID, AddScheduleUtil.dataByteWeek, AddScheduleUtil.dataHour, AddScheduleUtil.dataMinute, AddScheduleUtil.dataItem, AddScheduleUtil.dataRemind, AddScheduleUtil.dataIsOpen, AddScheduleUtil.shakeMode, AddScheduleUtil.shakeNum);
            } else if (resultCode == AddClockPresenter.RESULT_CLOCK_DELETE) {
                KLog.e(TAG, "REQUEST_EDIT_CLOCK_DELETE");
                this.presenter.deleteAlarm(AddScheduleUtil.dataID);
            }
            updateUI();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.clock) {
            this.mClock.setSelected(true);
            this.mSchedule.setSelected(false);
            this.mClock.setTextColor(getResources().getColor(R.color.device_module_long_seat_text_1));
            this.mSchedule.setTextColor(getResources().getColor(R.color.device_module_schedule_list_uncheck));
            this.now_state = STATE_CLOCK;
            this.mListViewSwipeMenu.setAdapter((ListAdapter) this.alarmAdapter);
            this.alarmAdapter.notifyDataSetChanged();
        } else if (i == R.id.schedule) {
            this.mClock.setSelected(false);
            this.mSchedule.setSelected(true);
            this.mSchedule.setTextColor(getResources().getColor(R.color.device_module_long_seat_text_1));
            this.mClock.setTextColor(getResources().getColor(R.color.device_module_schedule_list_uncheck));
            this.now_state = STATE_SCHEDULE;
            this.mListViewSwipeMenu.setAdapter((ListAdapter) this.mAdapter);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void back() {
        super.back();
        setResult(2000);
        finish();
    }

    public void setPresenter(ClockPresenter presenter2) {
    }
}
