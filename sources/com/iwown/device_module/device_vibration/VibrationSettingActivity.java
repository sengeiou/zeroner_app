package com.iwown.device_module.device_vibration;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.device_alarm_schedule.view.dialog.ShakeNumDialog;
import com.iwown.device_module.device_alarm_schedule.view.dialog.ShakeNumDialog.OnConfirmListener;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_vibration.bean.VibrationIvMtk;
import com.iwown.device_module.device_vibration.bean.VibrationPb;
import com.iwown.device_module.device_vibration.bean.VibrationZg;
import com.socks.library.KLog;
import java.util.List;
import org.litepal.util.Const.TableSchema;

public class VibrationSettingActivity extends DeviceModuleBaseActivity implements OnConfirmListener, OnClickListener, View {
    public static final String ID = "id";
    public static final String POSITION = "position";
    public static final String POSITION_NAME = "position_name";
    private static final int REQUEST_MODE = 123;
    public static final String SHAKE_NUM = "shake_num";
    public static final String TYPE = "type";
    int clockShakeMode = 0;
    int clockShakeNumber = 0;
    int heartGuideShakeMode = 0;
    int heartGuideShakeNumber = 0;
    LinearLayout mCallLl;
    LinearLayout mClockLl;
    private int mCurrType;
    private ItemView mCurrView;
    LinearLayout mHeartGuideLl;
    ItemView mHeartGuideShakeMode;
    ItemView mHeartGuideShakeNum;
    private SparseArray<String> mModeMap;
    LinearLayout mMsgPushLl;
    LinearLayout mScheduleLl;
    LinearLayout mSedentaryLl;
    /* access modifiers changed from: private */
    public ShakeNumDialog mShakeNumDialog;
    int phoneShakeMode = 0;
    int phoneShakeNumber = 0;
    VibrationSettingPresenter presenter;
    int scheduleShakeMode = 0;
    int scheduleShakeNumber = 0;
    int sedentaryShakeMode = 0;
    int sedentaryShakeNumber = 0;
    ItemView settingClockShakeMode;
    ItemView settingClockShakeNum;
    ItemView settingPhoneShakeMode;
    ItemView settingPhoneShakeNum;
    ItemView settingScheduleShakeMode;
    ItemView settingScheduleShakeNum;
    ItemView settingSedentaryShakeMode;
    ItemView settingSedentaryShakeNum;
    ItemView settingSmsShakeMode;
    ItemView settingSmsShakeNum;
    int smsShakeNumber = 0;
    int smsShakemode = 0;
    /* access modifiers changed from: private */
    public int type = -1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_vibration_setting);
        initView();
        initEvent();
    }

    private void initEvent() {
        this.mShakeNumDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {
                switch (VibrationSettingActivity.this.type) {
                    case 1:
                        VibrationSettingActivity.this.mShakeNumDialog.setTitle(R.string.device_module_activity_come_phone);
                        return;
                    case 2:
                        VibrationSettingActivity.this.mShakeNumDialog.setTitle(R.string.device_module_clock);
                        return;
                    case 3:
                        VibrationSettingActivity.this.mShakeNumDialog.setTitle(R.string.device_module_schedule);
                        return;
                    case 4:
                        VibrationSettingActivity.this.mShakeNumDialog.setTitle(R.string.device_module_activity_msg_push);
                        return;
                    case 5:
                        VibrationSettingActivity.this.mShakeNumDialog.setTitle(R.string.device_module_sedentary_reminder);
                        return;
                    case 6:
                        VibrationSettingActivity.this.mShakeNumDialog.setTitle(R.string.device_module_activity_heart_guide);
                        return;
                    default:
                        return;
                }
            }
        });
        this.mShakeNumDialog.setOnConfirmListener(this);
    }

    private void initView() {
        String setting;
        VibrationSettingPresenter vibrationSettingPresenter = new VibrationSettingPresenter(this);
        this.presenter = vibrationSettingPresenter;
        this.settingPhoneShakeMode = (ItemView) findViewById(R.id.setting_phone_shake_mode);
        this.settingPhoneShakeNum = (ItemView) findViewById(R.id.setting_phone_shake_num);
        this.settingClockShakeMode = (ItemView) findViewById(R.id.setting_clock_shake_mode);
        this.settingClockShakeNum = (ItemView) findViewById(R.id.setting_clock_shake_num);
        this.settingScheduleShakeMode = (ItemView) findViewById(R.id.setting_schedule_shake_mode);
        this.settingScheduleShakeNum = (ItemView) findViewById(R.id.setting_schedule_shake_num);
        this.settingSmsShakeMode = (ItemView) findViewById(R.id.setting_sms_shake_mode);
        this.settingSmsShakeNum = (ItemView) findViewById(R.id.setting_sms_shake_num);
        this.settingSedentaryShakeMode = (ItemView) findViewById(R.id.setting_sedentary_shake_mode);
        this.settingSedentaryShakeNum = (ItemView) findViewById(R.id.setting_sedentary_shake_num);
        this.mCallLl = (LinearLayout) findViewById(R.id.call_ll);
        this.mClockLl = (LinearLayout) findViewById(R.id.clock_ll);
        this.mScheduleLl = (LinearLayout) findViewById(R.id.schedule_ll);
        this.mSedentaryLl = (LinearLayout) findViewById(R.id.sedentary_ll);
        this.mMsgPushLl = (LinearLayout) findViewById(R.id.msg_push_ll);
        this.mHeartGuideShakeMode = (ItemView) findViewById(R.id.heart_guide_shake_mode);
        this.mHeartGuideShakeNum = (ItemView) findViewById(R.id.heart_guide_shake_num);
        this.mHeartGuideLl = (LinearLayout) findViewById(R.id.heart_guide_ll);
        this.settingPhoneShakeMode.setOnClickListener(this);
        this.settingPhoneShakeNum.setOnClickListener(this);
        this.settingClockShakeMode.setOnClickListener(this);
        this.settingClockShakeNum.setOnClickListener(this);
        this.settingScheduleShakeMode.setOnClickListener(this);
        this.settingScheduleShakeNum.setOnClickListener(this);
        this.settingSmsShakeMode.setOnClickListener(this);
        this.settingSmsShakeNum.setOnClickListener(this);
        this.settingSedentaryShakeMode.setOnClickListener(this);
        this.settingSedentaryShakeNum.setOnClickListener(this);
        this.mHeartGuideShakeMode.setOnClickListener(this);
        this.mHeartGuideShakeNum.setOnClickListener(this);
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_vibration);
        if (BluetoothOperation.isZg()) {
            ShakeNumDialog shakeNumDialog = new ShakeNumDialog(this, 1, 15);
            this.mShakeNumDialog = shakeNumDialog;
        } else if (BluetoothOperation.isProtoBuf()) {
            ShakeNumDialog shakeNumDialog2 = new ShakeNumDialog(this, 1, 15);
            this.mShakeNumDialog = shakeNumDialog2;
        } else {
            ShakeNumDialog shakeNumDialog3 = new ShakeNumDialog(this);
            this.mShakeNumDialog = shakeNumDialog3;
        }
        this.mModeMap = new SparseArray();
        String[] shakeModeName = getResources().getStringArray(R.array.device_module_shake_mode_name);
        try {
            new VibrationIvMtk();
            new VibrationIvMtk();
            new VibrationZg();
            new VibrationPb();
            if (BluetoothOperation.isZg()) {
                VibrationZg zg = this.presenter.zgVibration();
                int[] shakeMode = getResources().getIntArray(R.array.device_module_shake_mode_zg);
                for (int index = 0; index < shakeModeName.length; index++) {
                    this.mModeMap.put(shakeMode[index], shakeModeName[index]);
                }
                this.phoneShakeMode = zg.getZg_phoneShakeMode();
                this.phoneShakeNumber = zg.getZg_phoneShakeNum();
                this.scheduleShakeMode = zg.getZg_scheduleShakeMode();
                this.scheduleShakeNumber = zg.getZg_scheduleShakeNum();
                this.smsShakemode = zg.getZg_smsShakeMode();
                this.smsShakeNumber = zg.getZg_smsShakeNum();
                this.sedentaryShakeMode = zg.getZg_sedentaryShakeMode();
                this.sedentaryShakeNumber = zg.getZg_sedentaryShakeNum();
                this.clockShakeMode = zg.getZg_clockShakeMode();
                this.clockShakeNumber = zg.getZg_clockShakeNum();
                this.heartGuideShakeMode = zg.getZg_heartGuideShakeMode();
                this.heartGuideShakeNumber = zg.getZg_heartGuideShakeNum();
            } else if (BluetoothOperation.isIv()) {
                int[] shakeMode2 = getResources().getIntArray(R.array.device_module_shake_mode);
                for (int index2 = 0; index2 < shakeModeName.length; index2++) {
                    this.mModeMap.put(shakeMode2[index2], shakeModeName[index2]);
                }
                VibrationIvMtk iv = this.presenter.ivVibration();
                this.phoneShakeMode = iv.getPhoneShakeMode();
                this.phoneShakeNumber = iv.getPhoneShakeNum();
                this.scheduleShakeMode = iv.getScheduleShakeMode();
                this.scheduleShakeNumber = iv.getScheduleShakeNum();
                this.smsShakemode = iv.getSmsShakeMode();
                this.smsShakeNumber = iv.getSmsShakeNum();
                this.sedentaryShakeMode = iv.getSedentaryShakeMode();
                this.sedentaryShakeNumber = iv.getSedentaryShakeNum();
                this.clockShakeMode = iv.getClockShakeMode();
                this.clockShakeNumber = iv.getClockShakeNum();
                this.heartGuideShakeMode = iv.getHeartGuideShakeMode();
                this.heartGuideShakeNumber = iv.getHeartGuideShakeNum();
            } else if (BluetoothOperation.isMtk()) {
                int[] shakeMode3 = getResources().getIntArray(R.array.device_module_shake_mode);
                for (int index3 = 0; index3 < shakeModeName.length; index3++) {
                    this.mModeMap.put(shakeMode3[index3], shakeModeName[index3]);
                }
                VibrationIvMtk mtk = this.presenter.mtkVibration();
                this.phoneShakeMode = mtk.getPhoneShakeMode();
                this.phoneShakeNumber = mtk.getPhoneShakeNum();
                this.scheduleShakeMode = mtk.getScheduleShakeMode();
                this.scheduleShakeNumber = mtk.getScheduleShakeNum();
                this.smsShakemode = mtk.getSmsShakeMode();
                this.smsShakeNumber = mtk.getSmsShakeNum();
                this.sedentaryShakeMode = mtk.getSedentaryShakeMode();
                this.sedentaryShakeNumber = mtk.getSedentaryShakeNum();
                this.clockShakeMode = mtk.getClockShakeMode();
                this.clockShakeNumber = mtk.getClockShakeNum();
                this.heartGuideShakeMode = mtk.getHeartGuideShakeMode();
                this.heartGuideShakeNumber = mtk.getHeartGuideShakeNum();
            } else if (BluetoothOperation.isProtoBuf()) {
                int[] shakeMode4 = getResources().getIntArray(R.array.device_module_shake_mode);
                for (int index4 = 0; index4 < shakeModeName.length; index4++) {
                    this.mModeMap.put(shakeMode4[index4], shakeModeName[index4]);
                }
                VibrationPb pb = this.presenter.pbVibration();
                this.phoneShakeMode = pb.getPb_phoneShakeMode();
                this.phoneShakeNumber = pb.getPb_phoneShakeNum();
                this.scheduleShakeMode = pb.getPb_scheduleShakeMode();
                this.scheduleShakeNumber = pb.getPb_scheduleShakeNum();
                this.smsShakemode = pb.getPb_smsShakeMode();
                this.smsShakeNumber = pb.getPb_smsShakeNum();
                this.sedentaryShakeMode = pb.getPb_sedentaryShakeMode();
                this.sedentaryShakeNumber = pb.getPb_sedentaryShakeNum();
                this.clockShakeMode = pb.getPb_clockShakeMode();
                this.clockShakeNumber = pb.getPb_clockShakeNum();
                this.heartGuideShakeMode = pb.getPb_heartGuideShakeMode();
                this.heartGuideShakeNumber = pb.getPb_heartGuideShakeNum();
            }
            this.settingPhoneShakeMode.setMessageText((String) this.mModeMap.get(this.phoneShakeMode));
            this.settingClockShakeMode.setMessageText((String) this.mModeMap.get(this.clockShakeMode));
            this.settingScheduleShakeMode.setMessageText((String) this.mModeMap.get(this.scheduleShakeMode));
            this.settingSmsShakeMode.setMessageText((String) this.mModeMap.get(this.smsShakemode));
            this.settingSedentaryShakeMode.setMessageText((String) this.mModeMap.get(this.sedentaryShakeMode));
            this.settingPhoneShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.phoneShakeNumber)}));
            this.settingClockShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.clockShakeNumber)}));
            this.settingSmsShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.smsShakeNumber)}));
            this.settingScheduleShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.scheduleShakeNumber)}));
            this.settingSedentaryShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.sedentaryShakeNumber)}));
            this.mHeartGuideShakeMode.setMessageText((String) this.mModeMap.get(this.heartGuideShakeMode));
            this.mHeartGuideShakeNum.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(this.heartGuideShakeNumber)}));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        boolean support_alarm = false;
        boolean support_schedule = false;
        boolean support_sedentary = false;
        boolean support_heart_guide = false;
        TB_DeviceSettings settings = DeviceSettingsBiz.getInstance().queryDevSettings();
        Gson gson = new Gson();
        if (settings == null) {
            setting = "";
        } else {
            setting = settings.getSetting();
        }
        AnonymousClass2 r0 = new TypeToken<List<SettingBean>>() {
        };
        List<SettingBean> dev_settings = (List) gson.fromJson(setting, r0.getType());
        if (dev_settings != null && dev_settings.size() > 0) {
            for (SettingBean setting2 : dev_settings) {
                switch (setting2.getType()) {
                    case 13:
                        support_heart_guide = true;
                        break;
                    case 15:
                        support_alarm = true;
                        break;
                    case 16:
                        support_schedule = true;
                        break;
                    case 19:
                        support_sedentary = true;
                        break;
                }
            }
        }
        if (BluetoothOperation.isZg()) {
            support_alarm = false;
            support_schedule = false;
        }
        if (!support_alarm) {
            this.mClockLl.setVisibility(8);
        }
        if (!support_heart_guide) {
            this.mHeartGuideLl.setVisibility(8);
        }
        if (!support_schedule) {
            this.mScheduleLl.setVisibility(8);
        }
        if (!support_sedentary) {
            this.mSedentaryLl.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.presenter.writeVibrationToDevice();
        super.onDestroy();
    }

    public void back() {
        super.back();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == -1) {
            processIntent(data);
        }
    }

    private void processIntent(Intent data) {
        String stringExtra = data.getStringExtra("position_name");
        int id = data.getIntExtra("id", -1);
        int type2 = data.getIntExtra("type", -1);
        int position = data.getIntExtra("position", 0);
        if (id != -1) {
            ((ItemView) findViewById(id)).setMessageText((String) this.mModeMap.get(position));
        }
        this.presenter.saveVibration(position, 99, type2);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.setting_phone_shake_mode) {
            selectMode(view.getId(), 1, this.phoneShakeMode, this.phoneShakeNumber, getString(R.string.device_module_activity_come_phone));
        } else if (view.getId() == R.id.setting_clock_shake_mode) {
            selectMode(view.getId(), 2, this.clockShakeMode, this.clockShakeNumber, getString(R.string.device_module_clock));
        } else if (view.getId() == R.id.setting_schedule_shake_mode) {
            selectMode(view.getId(), 3, this.scheduleShakeMode, this.scheduleShakeNumber, getString(R.string.device_module_schedule));
        } else if (view.getId() == R.id.setting_sms_shake_mode) {
            selectMode(view.getId(), 4, this.smsShakemode, this.smsShakeNumber, getString(R.string.device_module_activity_msg_push));
        } else if (view.getId() == R.id.setting_sedentary_shake_mode) {
            selectMode(view.getId(), 5, this.sedentaryShakeMode, this.sedentaryShakeNumber, getString(R.string.device_module_sedentary_reminder));
        } else if (view.getId() == R.id.heart_guide_shake_mode) {
            selectMode(view.getId(), 6, this.heartGuideShakeMode, this.heartGuideShakeNumber, getString(R.string.device_module_activity_heart_guide));
        } else if (view.getId() == R.id.setting_phone_shake_num) {
            showNumDialog(view, 1, this.phoneShakeNumber);
        } else if (view.getId() == R.id.setting_clock_shake_num) {
            showNumDialog(view, 2, this.clockShakeNumber);
        } else if (view.getId() == R.id.setting_schedule_shake_num) {
            showNumDialog(view, 3, this.scheduleShakeNumber);
        } else if (view.getId() == R.id.setting_sms_shake_num) {
            showNumDialog(view, 4, this.smsShakeNumber);
        } else if (view.getId() == R.id.setting_sedentary_shake_num) {
            showNumDialog(view, 5, this.sedentaryShakeNumber);
        } else if (view.getId() == R.id.heart_guide_shake_num) {
            showNumDialog(view, 6, this.heartGuideShakeNumber);
        }
    }

    private void selectMode(int resId, int type2, int position, int num, String modeName) {
        KLog.d("type = " + type2 + " num = " + num + " position = " + position);
        Intent intent = new Intent(this, VibrationModeActivity.class);
        intent.putExtra(TableSchema.COLUMN_NAME, modeName);
        intent.putExtra("id", resId);
        intent.putExtra("type", type2);
        intent.putExtra("position", position);
        intent.putExtra("shake_num", num);
        startActivityForResult(intent, 123);
    }

    private void showNumDialog(View view, int type2, int num) {
        KLog.d("type = " + type2 + " num = " + num);
        this.type = type2;
        this.mCurrView = (ItemView) view;
        this.mCurrType = type2;
        if (this.mShakeNumDialog == null) {
            if (BluetoothOperation.isZg()) {
                this.mShakeNumDialog = new ShakeNumDialog(this, 1, 15);
            } else if (BluetoothOperation.isProtoBuf()) {
                this.mShakeNumDialog = new ShakeNumDialog(this, 1, 15);
            } else {
                this.mShakeNumDialog = new ShakeNumDialog(this);
            }
            this.mShakeNumDialog.setOnConfirmListener(this);
        }
        this.mShakeNumDialog.setCurrPosition(num);
        this.mShakeNumDialog.show();
    }

    public void onConfirm(int num) {
        this.mShakeNumDialog.dismiss();
        this.mCurrView.setMessageText(getString(R.string.device_module_activity_times, new Object[]{Integer.valueOf(num)}));
        this.presenter.saveVibration(99, num, this.mCurrType);
    }

    public void setPresenter(VibrationPresenter presenter2) {
    }
}
