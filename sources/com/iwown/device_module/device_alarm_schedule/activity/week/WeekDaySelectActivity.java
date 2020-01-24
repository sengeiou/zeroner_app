package com.iwown.device_module.device_alarm_schedule.activity.week;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.view.checkbox.WeekRepeatCheck;
import com.iwown.device_module.common.view.checkbox.WeekRepeatCheck.SetOnCheckChange;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.OnItemClickListener;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.OnItemMultiSelectListener;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.Operation;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.SelectMode;
import com.iwown.device_module.device_alarm_schedule.bean.WeekRepeat;
import java.util.ArrayList;
import java.util.List;

public class WeekDaySelectActivity extends DeviceModuleBaseActivity implements View {
    public static final int Activity_Type_AddClock = 1;
    public static final int Activity_Type_LongSit = 2;
    /* access modifiers changed from: private */
    public List<WeekRepeat> data = new ArrayList();
    private WeekRepeatCheck everyDay;
    RecyclerView mBloodRecycler;
    /* access modifiers changed from: private */
    public byte mWeekRept = -1;
    /* access modifiers changed from: private */
    public ModeAdapter modeAdapter;
    /* access modifiers changed from: private */
    public WeekDaySelectPresenter presenter;
    private WeekRepeatCheck weekEnd;
    private WeekRepeatCheck workDay;

    class ModeAdapter extends EasyAdapter<MyViewHolder> {
        private LayoutInflater mInflater;

        public ModeAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(this.mInflater.inflate(R.layout.device_module_shake_mode_item, null));
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getItemCount() {
            return WeekDaySelectActivity.this.data.size();
        }

        public void whenBindViewHolder(MyViewHolder holder, int position) {
            holder.tvName.setText(((WeekRepeat) WeekDaySelectActivity.this.data.get(position)).getDay());
            holder.check.setVisibility(((WeekRepeat) WeekDaySelectActivity.this.data.get(position)).isCheck() ? 0 : 4);
        }
    }

    static class MyViewHolder extends ViewHolder {
        public ImageView check;
        public TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.blood_item_text);
            this.check = (ImageView) itemView.findViewById(R.id.blood_item_img);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_weak_day_select);
        this.mWeekRept = getIntent().getByteExtra("day_of_week", -1);
        this.presenter = new WeekDaySelectPresenter(this);
        initView();
        initEvent();
        initData();
    }

    private void initEvent() {
    }

    private void initView() {
        setLeftBackTo();
        this.mBloodRecycler = (RecyclerView) findViewById(R.id.blood_recycler);
        this.everyDay = (WeekRepeatCheck) findViewById(R.id.repeat_every_day);
        this.workDay = (WeekRepeatCheck) findViewById(R.id.repeat_workday);
        this.weekEnd = (WeekRepeatCheck) findViewById(R.id.repeat_weekend);
        this.everyDay.setLeftTitle(getString(R.string.device_module_every_day));
        this.everyDay.setCheckChange(new SetOnCheckChange() {
            public void onCheckChange(boolean isCheck) {
                if (isCheck) {
                    WeekDaySelectActivity.this.mWeekRept = -1;
                }
                WeekDaySelectActivity.this.initData();
            }
        });
        this.weekEnd.setLeftTitle(getString(R.string.device_module_every_weekend));
        this.weekEnd.setCheckChange(new SetOnCheckChange() {
            public void onCheckChange(boolean isCheck) {
                if (isCheck) {
                    WeekDaySelectActivity.this.mWeekRept = -125;
                }
                WeekDaySelectActivity.this.initData();
            }
        });
        this.workDay.setLeftTitle(getString(R.string.device_module_every_weekdays));
        this.workDay.setCheckChange(new SetOnCheckChange() {
            public void onCheckChange(boolean isCheck) {
                if (isCheck) {
                    WeekDaySelectActivity.this.mWeekRept = -4;
                }
                WeekDaySelectActivity.this.initData();
            }
        });
        switch (getIntent().getIntExtra("what_activity", -1)) {
            case 1:
                setTitleText(R.string.device_module_add_clock);
                break;
            case 2:
                setTitleText(R.string.device_module_sedentary_reminder);
                break;
        }
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                if (WeekDaySelectActivity.this.mWeekRept == Byte.MIN_VALUE) {
                    Toast.makeText(WeekDaySelectActivity.this, WeekDaySelectActivity.this.getString(R.string.device_module_select_least_one), 0).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("week_repeat", WeekDaySelectActivity.this.mWeekRept);
                WeekDaySelectActivity.this.setResult(-1, intent);
                WeekDaySelectActivity.this.finish();
            }
        });
        this.modeAdapter = new ModeAdapter(this);
        this.modeAdapter.setSelectMode(SelectMode.MULTI_SELECT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(1);
        this.mBloodRecycler.setLayoutManager(linearLayoutManager);
        this.mBloodRecycler.setAdapter(this.modeAdapter);
        this.modeAdapter.notifyDataSetChanged();
        this.modeAdapter.setOnItemMultiSelectListener(new OnItemMultiSelectListener() {
            public void onSelected(Operation operation, int itemPosition, boolean isSelected) {
                WeekRepeat repeat = (WeekRepeat) WeekDaySelectActivity.this.data.get(itemPosition);
                if (repeat.isCheck()) {
                    repeat.setCheck(false);
                } else {
                    repeat.setCheck(true);
                }
                WeekDaySelectActivity.this.data.set(itemPosition, repeat);
                WeekDaySelectActivity.this.mWeekRept = WeekDaySelectActivity.this.presenter.getWeekRepeat(WeekDaySelectActivity.this.data);
                WeekDaySelectActivity.this.initSelect(WeekDaySelectActivity.this.mWeekRept);
                WeekDaySelectActivity.this.modeAdapter.notifyDataSetChanged();
            }
        });
        this.modeAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onClicked(int itemPosition) {
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void initSelect(int repeat) {
        this.workDay.setChecked(false);
        this.weekEnd.setChecked(false);
        this.everyDay.setChecked(false);
        if (repeat == -1) {
            this.everyDay.setChecked(true);
        } else if (repeat == -4) {
            this.workDay.setChecked(true);
        } else if (repeat == -125) {
            this.weekEnd.setChecked(true);
        }
    }

    /* access modifiers changed from: private */
    public void initData() {
        this.data.clear();
        this.data.addAll(this.presenter.getData(this, this.mWeekRept));
        this.modeAdapter.notifyDataSetChanged();
        this.mWeekRept = this.presenter.getWeekRepeat(this.data);
        initSelect(this.mWeekRept);
    }

    public void setPresenter(Presenter presenter2) {
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
