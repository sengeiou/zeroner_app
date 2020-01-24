package com.iwown.device_module.device_alarm_schedule.activity.alarm;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.wheelView.TimePointView;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockPresenter;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockView;
import com.iwown.device_module.device_alarm_schedule.activity.week.WeekDaySelectActivity;
import com.iwown.device_module.device_alarm_schedule.bean.ClockInfo;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_alarm_schedule.view.dialog.ShakeNumDialog;
import com.iwown.device_module.device_alarm_schedule.view.dialog.ShakeNumDialog.OnConfirmListener;
import com.iwown.device_module.device_vibration.VibrationModeActivity;
import com.iwown.device_module.device_vibration.VibrationSettingUtils;
import com.iwown.device_module.device_vibration.bean.VibrationZg;
import com.socks.library.KLog;
import java.util.Calendar;
import org.litepal.crud.DataSupport;
import org.litepal.util.Const.TableSchema;

public class AddClockActivity extends DeviceModuleBaseActivity implements OnClickListener, ClockView {
    private static int ADD_CLOCK_REQUEST = 838;
    public static final String ID = "id";
    public static final String POSITION = "position";
    public static final String POSITION_NAME = "position_name";
    private static final int REQUEST_MODE = 123;
    public static int RESULT_CLOCK_DELETE = 88;
    public static final String SHAKE_NUM = "shake_num";
    public static int STATE_ADD = 0;
    public static int STATE_EDIT = 1;
    public static final String TYPE = "type";
    private static int mState = -1;
    private byte bb = -1;
    private int clock_id = -1;
    /* access modifiers changed from: private */
    public ClockInfo mClockInfo = new ClockInfo();
    TextView mDeleteClockBtn;
    ImageView mImag;
    private String[] mMinArr;
    private SparseArray<String> mModeMap;
    private AddClockPresenter mPresenter;
    ItemView mRepeat;
    ItemView mSettingShakeMode;
    ItemView mSettingShakeNum;
    /* access modifiers changed from: private */
    public ShakeNumDialog mShakeNumDialog;
    ItemView mStartTime;
    TimePointView mTimePointPicker;
    EditText mTitleEdit;
    /* access modifiers changed from: private */
    public int thisClockShakeMode = 1;
    /* access modifiers changed from: private */
    public int thisClockShakeNum = 1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_add_clock);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        this.mPresenter = new AddClockPresenter(this);
        this.mMinArr = getResources().getStringArray(R.array.device_module_min_has_zero);
        this.mModeMap = new SparseArray<>();
        String[] shakeModeName = getResources().getStringArray(R.array.device_module_shake_mode_name);
        int[] shakeMode = getResources().getIntArray(R.array.device_module_shake_mode_zg);
        for (int index = 0; index < shakeModeName.length; index++) {
            this.mModeMap.put(shakeMode[index], shakeModeName[index]);
        }
    }

    private void initEvent() {
        this.mShakeNumDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface anInterface) {
                AddClockActivity.this.mShakeNumDialog.setTitle(R.string.device_module_clock);
            }
        });
        this.mShakeNumDialog.setOnConfirmListener(new OnConfirmListener() {
            public void onConfirm(int num) {
                VibrationSettingUtils.saveVibration(99, num, 2);
                AddClockActivity.this.mSettingShakeNum.setMessageText(AddClockActivity.this.getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(num)}));
                AddClockActivity.this.thisClockShakeNum = num;
            }
        });
    }

    private void initView() {
        this.mImag = (ImageView) findViewById(R.id.imag);
        this.mTitleEdit = (EditText) findViewById(R.id.title_edit);
        this.mStartTime = (ItemView) findViewById(R.id.start_time);
        this.mTimePointPicker = (TimePointView) findViewById(R.id.time_point_picker);
        this.mRepeat = (ItemView) findViewById(R.id.repeat);
        this.mDeleteClockBtn = (TextView) findViewById(R.id.delete_clock_btn);
        this.mSettingShakeMode = (ItemView) findViewById(R.id.setting_shake_mode);
        this.mSettingShakeNum = (ItemView) findViewById(R.id.setting_shake_num);
        this.mRepeat.setOnClickListener(this);
        this.mStartTime.setOnClickListener(this);
        this.mSettingShakeMode.setOnClickListener(this);
        this.mSettingShakeNum.setOnClickListener(this);
        this.mDeleteClockBtn.setOnClickListener(this);
        this.bb = AddScheduleUtil.dataByteWeek;
        KLog.e(TAG, Byte.valueOf(this.bb));
        Intent intent = getIntent();
        setLeftBackTo();
        if (intent != null) {
            if (intent.getIntExtra(AddClockPresenter.KEY_ADD_OR_EDIT, -1) == AddClockPresenter.TO_ADD) {
                setTitleText(R.string.device_module_add_clock);
                mState = STATE_ADD;
                this.bb = getIntent().getByteExtra(AddClockPresenter.KEY_REPEAT, -1);
                if (this.bb == Byte.MIN_VALUE) {
                    this.bb = -1;
                }
                this.clock_id = getIntent().getIntExtra(AddClockPresenter.KEY_CLOCK_ID, -1);
                this.thisClockShakeMode = VibrationSettingUtils.zgVibration().getZg_clockShakeMode();
                this.thisClockShakeNum = VibrationSettingUtils.zgVibration().getZg_clockShakeNum();
                initUi(mState);
            } else if (intent.getIntExtra(AddClockPresenter.KEY_ADD_OR_EDIT, -1) == AddClockPresenter.TO_EDIT) {
                setTitleText(R.string.device_module_edit_clock);
                mState = STATE_EDIT;
                this.clock_id = AddScheduleUtil.dataID;
                TB_Alarmstatue tb_alarmstatue = (TB_Alarmstatue) DataSupport.where("uid=? and Ac_Idx=?", ContextUtil.getUID() + "", this.clock_id + "").findFirst(TB_Alarmstatue.class);
                if (tb_alarmstatue != null) {
                    this.thisClockShakeMode = tb_alarmstatue.getZg_mode();
                    this.thisClockShakeNum = tb_alarmstatue.getZg_number();
                }
                initUi(mState);
            }
        }
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                if (AddClockActivity.this.checkWithUi()) {
                    AddClockActivity.this.setClockInfo();
                    AddScheduleUtil.packAlarmData(AddClockActivity.this.mClockInfo.getId(), AddClockActivity.this.mClockInfo.getRepeat(), AddClockActivity.this.mClockInfo.getHour(), AddClockActivity.this.mClockInfo.getMin(), AddClockActivity.this.mClockInfo.getTitle(), "", AddClockActivity.this.mClockInfo.isOpen(), AddClockActivity.this.thisClockShakeMode, AddClockActivity.this.thisClockShakeNum);
                    KLog.e("licl", AddClockActivity.this.thisClockShakeMode + "/" + AddClockActivity.this.thisClockShakeNum + "alarm:" + AddClockActivity.this.mClockInfo);
                    AddClockActivity.this.setResult(-1);
                    AddClockActivity.this.finish();
                }
            }
        });
        this.mShakeNumDialog = new ShakeNumDialog(this, 1, 15);
        if (BluetoothOperation.isZg()) {
            this.mSettingShakeMode.setVisibility(0);
            this.mSettingShakeNum.setVisibility(0);
        }
    }

    private void initUi(int state) {
        VibrationZg zg;
        this.mStartTime.setLeftImagVisible(true);
        this.mStartTime.setRightImagVisible(true);
        int rightMargin = WindowsUtil.dip2px(this, 16.0f);
        this.mStartTime.setTitleMsgRightMargin(rightMargin);
        this.mStartTime.setMsgColor(R.color.device_module_alarm_start_time);
        this.mStartTime.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time));
        this.mRepeat.setLeftImagVisible(true);
        this.mRepeat.setTitleMsgRightMargin(rightMargin);
        if (state == STATE_ADD) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            this.mStartTime.setMessageText(getString(R.string.sport_module_time_hh_mm, new Object[]{String.valueOf(cal.get(11)), this.mMinArr[cal.get(12)]}));
            this.mTimePointPicker.setStartCurrPosition(cal.get(11));
            this.mTimePointPicker.setEndCurrPosition(cal.get(12));
            this.mTimePointPicker.setVisibility(8);
            this.mDeleteClockBtn.setVisibility(8);
            if (BluetoothOperation.isZg()) {
                new VibrationZg();
                ZG_BaseInfo zgBase = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                if (zgBase == null || TextUtils.isEmpty(zgBase.getContent())) {
                    zg = new VibrationZg();
                } else {
                    zg = (VibrationZg) JsonUtils.fromJson(zgBase.getContent(), VibrationZg.class);
                }
                this.mSettingShakeMode.setMessageText((String) this.mModeMap.get(zg.getZg_clockShakeMode()));
                this.mSettingShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(zg.getZg_clockShakeNum())}));
            }
        } else {
            this.mStartTime.setMessageText(getString(R.string.sport_module_time_hh_mm, new Object[]{String.valueOf(AddScheduleUtil.dataHour), this.mMinArr[AddScheduleUtil.dataMinute]}));
            this.mTimePointPicker.setStartCurrPosition(AddScheduleUtil.dataHour);
            this.mTimePointPicker.setEndCurrPosition(AddScheduleUtil.dataMinute);
            this.mTimePointPicker.setVisibility(8);
            this.mDeleteClockBtn.setVisibility(0);
            this.mSettingShakeMode.setMessageText((String) this.mModeMap.get(this.thisClockShakeMode));
            this.mSettingShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.thisClockShakeNum)}));
            this.mTitleEdit.setText(AddScheduleUtil.dataItem);
        }
        setRepeat();
        setClockInfo();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == ADD_CLOCK_REQUEST) {
            this.bb = data.getByteExtra("week_repeat", 0);
            setRepeat();
            setClockInfo();
        } else if (requestCode == 123) {
            String stringExtra = data.getStringExtra("position_name");
            int id = data.getIntExtra("id", -1);
            int type = data.getIntExtra("type", -1);
            int position = data.getIntExtra("position", 0);
            VibrationSettingUtils.saveVibration(position, 99, type);
            if (id != -1) {
                ((ItemView) findViewById(id)).setMessageText((String) this.mModeMap.get(position));
            }
            this.thisClockShakeMode = position;
        }
    }

    private void setStartTime() {
        if (this.mTimePointPicker.getVisibility() == 0) {
            this.mStartTime.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time_1));
        } else {
            this.mStartTime.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time));
        }
        String hour = String.valueOf(this.mTimePointPicker.getStartTimeCurrPosition());
        String min = this.mMinArr[this.mTimePointPicker.getEndTimeCurrentPosition()];
        this.mStartTime.setMessageText(getString(R.string.sport_module_time_hh_mm, new Object[]{hour, min}));
    }

    public void setRepeat() {
        if (this.mRepeat.getVisibility() == 0) {
            this.mRepeat.setMessageText(this.mPresenter.getWeekRepeatStr(this.bb));
        }
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.delete_clock_btn) {
            AddScheduleUtil.packIDData(this.clock_id);
            setResult(RESULT_CLOCK_DELETE);
            finish();
        } else if (i == R.id.setting_shake_mode) {
            selectMode(view.getId(), 2, this.thisClockShakeMode, this.thisClockShakeNum, getString(R.string.device_module_clock));
        } else if (i == R.id.setting_shake_num) {
            this.mShakeNumDialog.show();
        } else if (i == R.id.start_time) {
            changeViewVisible(this.mTimePointPicker);
            setStartTime();
        } else if (i == R.id.repeat) {
            Intent intent = new Intent(this, WeekDaySelectActivity.class);
            intent.putExtra("what_activity", 1);
            intent.putExtra("day_of_week", this.bb);
            startActivityForResult(intent, ADD_CLOCK_REQUEST);
        }
    }

    private void selectMode(int resId, int type, int position, int num, String modeName) {
        KLog.d("type = " + type + " num = " + num + " position = " + position);
        Intent intent = new Intent(this, VibrationModeActivity.class);
        intent.putExtra(TableSchema.COLUMN_NAME, modeName);
        intent.putExtra("id", resId);
        intent.putExtra("type", type);
        intent.putExtra("position", position);
        intent.putExtra("shake_num", num);
        startActivityForResult(intent, 123);
    }

    public void setClockInfo() {
        this.mClockInfo.setId(this.clock_id);
        this.mClockInfo.setRepeat(this.bb);
        this.mClockInfo.setHour(this.mTimePointPicker.getStartTimeCurrPosition());
        this.mClockInfo.setMin(this.mTimePointPicker.getEndTimeCurrentPosition());
        this.mClockInfo.setTitle(this.mTitleEdit.getText().toString().trim());
        if (mState == STATE_EDIT) {
            this.mClockInfo.setOpen(AddScheduleUtil.dataIsOpen);
        }
    }

    public boolean checkWithUi() {
        if (BluetoothOperation.isConnected()) {
            return true;
        }
        Toast.makeText(this, R.string.device_module_schedule_msg_no_connect, 0).show();
        return false;
    }

    public void setPresenter(ClockPresenter presenter) {
    }
}
