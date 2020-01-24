package com.iwown.device_module.device_alarm_schedule.activity.schedule;

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
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.wheelView.TimePointView;
import com.iwown.device_module.common.view.wheelView.YMDPicerView;
import com.iwown.device_module.device_alarm_schedule.bean.ScheduleInfo;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_alarm_schedule.view.dialog.ShakeNumDialog;
import com.iwown.device_module.device_alarm_schedule.view.dialog.ShakeNumDialog.OnConfirmListener;
import com.iwown.device_module.device_vibration.VibrationModeActivity;
import com.iwown.device_module.device_vibration.bean.VibrationZg;
import com.iwown.lib_common.toast.CustomToast;
import com.socks.library.KLog;
import com.tencent.bugly.beta.tinker.TinkerReport;
import com.tencent.connect.common.Constants;
import java.util.Calendar;
import org.litepal.util.Const.TableSchema;

public class AddScheduleActivity extends DeviceModuleBaseActivity implements OnClickListener {
    private static final int REQUEST_MODE = 123;
    public static int RESULT_SCH_DELETE = TinkerReport.KEY_LOADED_INFO_CORRUPTED;
    public static int STATE_ADD = 0;
    public static int STATE_EDIT = 1;
    /* access modifiers changed from: private */
    public static int mState = -1;
    /* access modifiers changed from: private */
    public ScheduleInfo info = new ScheduleInfo();
    TextView mDeleteScheduleBtn;
    ImageView mImag;
    private String[] mMinArr;
    private SparseArray<String> mModeMap;
    private String[] mMonthArr;
    ItemView mSettingShakeMode;
    ItemView mSettingShakeNum;
    /* access modifiers changed from: private */
    public ShakeNumDialog mShakeNumDialog;
    ItemView mTime;
    TimePointView mTimePointPicker;
    EditText mTitleEdit;
    ItemView mYmd;
    YMDPicerView mYmdPicker;
    /* access modifiers changed from: private */
    public int thisSchShakeMode = 1;
    /* access modifiers changed from: private */
    public int thisSchShakeNum = 1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_edit_schedule2);
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {
        this.mShakeNumDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface anInterface) {
                AddScheduleActivity.this.mShakeNumDialog.setTitle(R.string.device_module_schedule);
            }
        });
        this.mShakeNumDialog.setOnConfirmListener(new OnConfirmListener() {
            public void onConfirm(int num) {
                AddScheduleActivity.this.mSettingShakeNum.setMessageText(AddScheduleActivity.this.getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(num)}));
                AddScheduleActivity.this.thisSchShakeNum = num;
            }
        });
    }

    private void initData() {
        this.mMinArr = getArray(R.array.device_module_min_has_zero);
        this.mMonthArr = getArray(R.array.device_module_months_items);
        this.mModeMap = new SparseArray<>();
        String[] shakeModeName = getResources().getStringArray(R.array.device_module_shake_mode_name);
        int[] shakeMode = getResources().getIntArray(R.array.device_module_shake_mode_zg);
        for (int index = 0; index < shakeModeName.length; index++) {
            this.mModeMap.put(shakeMode[index], shakeModeName[index]);
        }
    }

    private void initView() {
        VibrationZg zg;
        this.mImag = (ImageView) findViewById(R.id.imag);
        this.mTitleEdit = (EditText) findViewById(R.id.title_edit);
        this.mYmd = (ItemView) findViewById(R.id.ymd);
        this.mYmdPicker = (YMDPicerView) findViewById(R.id.ymd_picker);
        this.mTime = (ItemView) findViewById(R.id.time);
        this.mTimePointPicker = (TimePointView) findViewById(R.id.time_point_picker);
        this.mDeleteScheduleBtn = (TextView) findViewById(R.id.delete_schedule_btn);
        this.mSettingShakeMode = (ItemView) findViewById(R.id.setting_shake_mode);
        this.mSettingShakeNum = (ItemView) findViewById(R.id.setting_shake_num);
        this.mShakeNumDialog = new ShakeNumDialog(this, 1, 15);
        this.mTime.setOnClickListener(this);
        this.mYmd.setOnClickListener(this);
        this.mDeleteScheduleBtn.setOnClickListener(this);
        this.mSettingShakeNum.setOnClickListener(this);
        this.mSettingShakeMode.setOnClickListener(this);
        this.mTime.setRightImagVisible(true);
        this.mYmd.setRightImagVisible(true);
        if (BluetoothOperation.isZg()) {
            this.mSettingShakeMode.setVisibility(0);
            this.mSettingShakeNum.setVisibility(0);
        }
        setLeftBackTo();
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                AddScheduleActivity.this.setScheduleInfo();
                if (AddScheduleActivity.this.checkBeforeSave()) {
                    if (TextUtils.isEmpty(AddScheduleActivity.this.info.getTitle())) {
                        CustomToast.makeText(AddScheduleActivity.this, AddScheduleActivity.this.getString(R.string.device_module_schedule_add_title_error));
                        return;
                    }
                    if (AddScheduleActivity.mState == AddScheduleActivity.STATE_ADD) {
                        AddScheduleUtil.packScheduleData(0, AddScheduleActivity.this.info.getYear(), AddScheduleActivity.this.info.getMonth(), AddScheduleActivity.this.info.getDay(), AddScheduleActivity.this.info.getHour(), AddScheduleActivity.this.info.getMin(), AddScheduleActivity.this.info.getTitle(), "", AddScheduleActivity.this.thisSchShakeMode, AddScheduleActivity.this.thisSchShakeNum);
                    } else {
                        AddScheduleUtil.packScheduleData(AddScheduleActivity.this.info.getId(), AddScheduleActivity.this.info.getYear(), AddScheduleActivity.this.info.getMonth(), AddScheduleActivity.this.info.getDay(), AddScheduleActivity.this.info.getHour(), AddScheduleActivity.this.info.getMin(), AddScheduleActivity.this.info.getTitle(), "", AddScheduleActivity.this.thisSchShakeMode, AddScheduleActivity.this.thisSchShakeNum);
                    }
                    AddScheduleActivity.this.setResult(-1);
                    AddScheduleActivity.this.finish();
                }
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getIntExtra("add_or_edit", -1) == AddSchedulePresenter.TO_ADD) {
                setTitleText(R.string.device_module_add_schedule);
                mState = STATE_ADD;
                if (BluetoothOperation.isZg()) {
                    new VibrationZg();
                    ZG_BaseInfo zgBase = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                    if (zgBase == null || TextUtils.isEmpty(zgBase.getContent())) {
                        zg = new VibrationZg();
                    } else {
                        zg = (VibrationZg) JsonUtils.fromJson(zgBase.getContent(), VibrationZg.class);
                    }
                    this.thisSchShakeMode = zg.getZg_scheduleShakeMode();
                    this.thisSchShakeNum = zg.getZg_scheduleShakeNum();
                }
            } else if (intent.getIntExtra("add_or_edit", -1) == AddSchedulePresenter.TO_EDIT) {
                setTitleText(R.string.device_module_edit_schedule);
                mState = STATE_EDIT;
                this.thisSchShakeMode = AddScheduleUtil.shakeMode;
                this.thisSchShakeNum = AddScheduleUtil.shakeNum;
            }
        }
        initUi(mState);
    }

    private void initUi(int state) {
        this.mTitleEdit.setText(AddScheduleUtil.dataItem);
        this.mYmd.setLeftImagVisible(true);
        this.mYmd.setRightImagVisible(true);
        int rightMargin = WindowsUtil.dip2px(this, 16.0f);
        this.mYmd.setTitleMsgRightMargin(rightMargin);
        this.mYmd.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time));
        this.mTime.setLeftImagVisible(true);
        this.mTime.setRightImagVisible(true);
        this.mTime.setTitleMsgRightMargin(rightMargin);
        this.mTime.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time));
        if (Utils.shouldUseY_M_D()) {
            this.mYmd.setMessageText(getString(R.string.sport_module_time, new Object[]{String.valueOf(AddScheduleUtil.dataYear), String.valueOf(AddScheduleUtil.dataMonth), String.valueOf(AddScheduleUtil.dataDay)}));
        } else {
            this.mYmd.setMessageText(getString(R.string.sport_module_time_west, new Object[]{this.mMonthArr[AddScheduleUtil.dataMonth - 1], String.valueOf(AddScheduleUtil.dataDay), String.valueOf(AddScheduleUtil.dataYear)}));
        }
        YMDPicerView yMDPicerView = this.mYmdPicker;
        int i = AddScheduleUtil.dataYear;
        YMDPicerView yMDPicerView2 = this.mYmdPicker;
        yMDPicerView.setYearCurrentPosition(i - YMDPicerView.START_YEAR);
        this.mYmdPicker.setMonthCurrentPosition(AddScheduleUtil.dataMonth - 1);
        this.mYmdPicker.setDayCurrentPosition(AddScheduleUtil.dataDay - 1);
        this.mYmdPicker.setVisibility(8);
        this.mTimePointPicker.setVisibility(8);
        if (state == STATE_ADD) {
            this.mTime.setMessageText(getString(R.string.sport_module_time_hh_mm, new Object[]{Constants.VIA_REPORT_TYPE_SET_AVATAR, "00"}));
            this.mTimePointPicker.setStartCurrPosition(12);
            this.mTimePointPicker.setEndCurrPosition(0);
            this.mDeleteScheduleBtn.setVisibility(8);
        } else {
            this.mTime.setMessageText(getString(R.string.sport_module_time_hh_mm, new Object[]{String.valueOf(AddScheduleUtil.dataHour), this.mMinArr[AddScheduleUtil.dataMinute]}));
            this.mTimePointPicker.setStartCurrPosition(AddScheduleUtil.dataHour);
            this.mTimePointPicker.setEndCurrPosition(AddScheduleUtil.dataMinute);
            this.mDeleteScheduleBtn.setVisibility(0);
        }
        this.mSettingShakeMode.setMessageText((String) this.mModeMap.get(this.thisSchShakeMode));
        this.mSettingShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.thisSchShakeNum)}));
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ymd) {
            changeViewVisible(this.mYmdPicker);
            setYMDTime();
        } else if (i == R.id.time) {
            changeViewVisible(this.mTimePointPicker);
            setStartTime();
        } else if (i == R.id.delete_schedule_btn) {
            setScheduleInfo();
            AddScheduleUtil.packScheduleData(this.info.getId(), this.info.getYear(), this.info.getMonth(), this.info.getDay(), this.info.getHour(), this.info.getMin(), this.info.getTitle(), "", this.thisSchShakeMode, this.thisSchShakeNum);
            setResult(RESULT_SCH_DELETE);
            finish();
        } else if (i == R.id.setting_shake_mode) {
            selectMode(view.getId(), 3, this.thisSchShakeMode, this.thisSchShakeNum, getString(R.string.device_module_schedule));
        } else if (i == R.id.setting_shake_num) {
            this.mShakeNumDialog.show();
        }
    }

    private void setStartTime() {
        if (this.mTimePointPicker.getVisibility() == 0) {
            this.mTime.setMsgColor(getResources().getColor(R.color.device_module_schedule_text_1));
        } else {
            this.mTime.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time));
        }
        String hour = String.valueOf(this.mTimePointPicker.getStartTimeCurrPosition());
        String min = this.mMinArr[this.mTimePointPicker.getEndTimeCurrentPosition()];
        this.mTime.setMessageText(getString(R.string.sport_module_time_hh_mm, new Object[]{hour, min}));
    }

    private void setYMDTime() {
        if (this.mYmdPicker.getVisibility() == 0) {
            this.mYmd.setMsgColor(getResources().getColor(R.color.device_module_schedule_text_1));
        } else {
            this.mYmd.setMsgColor(getResources().getColor(R.color.device_module_alarm_start_time));
        }
        int yearCurrentPosition = this.mYmdPicker.getYearCurrentPosition();
        YMDPicerView yMDPicerView = this.mYmdPicker;
        String year = String.valueOf(yearCurrentPosition + YMDPicerView.START_YEAR);
        String month = String.valueOf(this.mYmdPicker.getMonthCurrentPosition() + 1);
        String day = String.valueOf(this.mYmdPicker.getDayCurrentPosition() + 1);
        if (Utils.shouldUseY_M_D()) {
            this.mYmd.setMessageText(getString(R.string.sport_module_time, new Object[]{year, month, day}));
            return;
        }
        this.mYmd.setMessageText(getString(R.string.sport_module_time_west, new Object[]{this.mMonthArr[this.mYmdPicker.getMonthCurrentPosition()], day, year}));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 123) {
            String stringExtra = data.getStringExtra("position_name");
            int id = data.getIntExtra("id", -1);
            int intExtra = data.getIntExtra("type", -1);
            int position = data.getIntExtra("position", 0);
            if (id != -1) {
                ((ItemView) findViewById(id)).setMessageText((String) this.mModeMap.get(position));
            }
            this.thisSchShakeMode = position;
        }
    }

    /* access modifiers changed from: private */
    public boolean checkBeforeSave() {
        if (!checkWithUi()) {
            return false;
        }
        setScheduleInfo();
        Calendar curCal = Calendar.getInstance();
        if (AddScheduleUtil.isBeforeToday(this.info.getYear(), this.info.getMonth(), this.info.getDay(), curCal)) {
            Toast.makeText(this, R.string.device_module_schedule_msg_schedule_before_today, 0).show();
            return false;
        }
        curCal.setTimeInMillis(System.currentTimeMillis());
        if (AddScheduleUtil.isToday(this.info.getYear(), this.info.getMonth(), this.info.getDay(), curCal) && AddScheduleUtil.isBeforeCurrentTime(this.info.getHour(), this.info.getMin(), curCal)) {
            Toast.makeText(this, R.string.device_module_schedule_msg_schedule_before_time, 0).show();
            return false;
        } else if ((this.info.getMin() == AddScheduleUtil.dataMinute && this.info.getHour() == AddScheduleUtil.dataHour && this.info.getDay() == AddScheduleUtil.dataDay && this.info.getMonth() == AddScheduleUtil.dataMonth && this.info.getYear() == AddScheduleUtil.dataYear) || AddScheduleUtil.isChangSchedule(String.valueOf(ContextUtil.getUID()), this.info.getYear(), this.info.getMonth(), this.info.getDay(), this.info.getHour(), this.info.getMin())) {
            return true;
        } else {
            Toast.makeText(this, R.string.device_module_schedule_msg_schedule_has_same, 0).show();
            return false;
        }
    }

    public void setScheduleInfo() {
        this.info.setId(AddScheduleUtil.dataID);
        ScheduleInfo scheduleInfo = this.info;
        int yearCurrentPosition = this.mYmdPicker.getYearCurrentPosition();
        YMDPicerView yMDPicerView = this.mYmdPicker;
        scheduleInfo.setYear(yearCurrentPosition + YMDPicerView.START_YEAR);
        this.info.setMonth(this.mYmdPicker.getMonthCurrentPosition() + 1);
        this.info.setDay(this.mYmdPicker.getDayCurrentPosition() + 1);
        this.info.setHour(this.mTimePointPicker.getStartTimeCurrPosition());
        this.info.setMin(this.mTimePointPicker.getEndTimeCurrentPosition());
        this.info.setTitle(this.mTitleEdit.getText().toString().trim());
        String title = this.mTitleEdit.getText().toString().trim();
        ScheduleInfo scheduleInfo2 = this.info;
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.device_module_schedule);
        }
        scheduleInfo2.setTitle(title);
        KLog.i(JsonUtils.toJson(this.info));
    }

    public boolean checkWithUi() {
        if (BluetoothOperation.isConnected()) {
            return true;
        }
        Toast.makeText(this, R.string.device_module_schedule_msg_no_connect, 0).show();
        return false;
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
}
