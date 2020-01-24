package com.iwown.device_module.device_blood;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.data_link.blood.BpPreUpload;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.device_setting.configure.BloodUtil;
import com.iwown.device_module.device_setting.configure.DeviceUtils;

public class BloodLastSetActivity extends DeviceModuleBaseActivity {
    public static int avg_dbp;
    public static int avg_sbp;
    public static int[] bloodInt;
    public static int src_dbp;
    public static int src_sbp;
    private Button blood_bt_last;
    /* access modifiers changed from: private */
    public EditText blood_lasthight;
    /* access modifiers changed from: private */
    public EditText blood_lastlow;
    /* access modifiers changed from: private */
    public int dstdbp_hb;
    /* access modifiers changed from: private */
    public int dstdbp_lb;
    /* access modifiers changed from: private */
    public int dstsbp_hb;
    /* access modifiers changed from: private */
    public int dstsbp_lb;
    /* access modifiers changed from: private */
    public int srcdbp_hb;
    /* access modifiers changed from: private */
    public int srcdbp_lb;
    /* access modifiers changed from: private */
    public int srcsbp_hb;
    /* access modifiers changed from: private */
    public int srcsbp_lb;
    private TextView toastMsg;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_blood_lastset);
        initView();
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_blood_DetailFinal);
        this.blood_lasthight = (EditText) findViewById(R.id.et_blood_lastheigh);
        this.blood_lastlow = (EditText) findViewById(R.id.et_blood_lastlow);
        this.blood_bt_last = (Button) findViewById(R.id.btn_blood_finish);
        this.blood_bt_last.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(BloodLastSetActivity.this.blood_lasthight.getText()) || TextUtils.isEmpty(BloodLastSetActivity.this.blood_lastlow.getText())) {
                    Toast.makeText(BloodLastSetActivity.this, R.string.Blood_pressure_last_textnull, 1).show();
                    return;
                }
                BloodLastSetActivity.src_sbp = Integer.parseInt(BloodLastSetActivity.this.blood_lasthight.getText().toString());
                BloodLastSetActivity.src_dbp = Integer.parseInt(BloodLastSetActivity.this.blood_lastlow.getText().toString());
                if (BloodLastSetActivity.src_sbp < 60 || BloodLastSetActivity.src_sbp > 200 || BloodLastSetActivity.src_dbp < 30 || BloodLastSetActivity.src_dbp > 150) {
                    Toast.makeText(BloodLastSetActivity.this, R.string.Blood_pressure_last_text, 1).show();
                    return;
                }
                BloodLastSetActivity.avg_sbp = (BloodSettingActivity.Onesbp_lb + BloodTwoSetActivity.Twosbp_lb) / 2;
                BloodLastSetActivity.avg_dbp = (BloodSettingActivity.Onedbp_lb + BloodTwoSetActivity.Twidbp_lb) / 2;
                BloodLastSetActivity.this.srcsbp_lb = ByteUtil.loword(BloodLastSetActivity.src_sbp);
                BloodLastSetActivity.this.srcsbp_hb = ByteUtil.hiword(BloodLastSetActivity.src_sbp);
                BloodLastSetActivity.this.srcdbp_lb = ByteUtil.loword(BloodLastSetActivity.src_dbp);
                BloodLastSetActivity.this.srcdbp_hb = ByteUtil.hiword(BloodLastSetActivity.src_dbp);
                BloodLastSetActivity.this.dstsbp_lb = ByteUtil.loword(BloodLastSetActivity.avg_sbp);
                BloodLastSetActivity.this.dstsbp_hb = ByteUtil.hiword(BloodLastSetActivity.avg_sbp);
                BloodLastSetActivity.this.dstdbp_lb = ByteUtil.loword(BloodLastSetActivity.avg_dbp);
                BloodLastSetActivity.this.dstdbp_hb = ByteUtil.hiword(BloodLastSetActivity.avg_dbp);
                BloodLastSetActivity.bloodInt = new int[]{BloodLastSetActivity.this.srcsbp_lb, BloodLastSetActivity.this.srcsbp_hb, BloodLastSetActivity.this.srcdbp_lb, BloodLastSetActivity.this.srcdbp_hb, BloodLastSetActivity.this.dstsbp_lb, BloodLastSetActivity.this.dstsbp_hb, BloodLastSetActivity.this.dstdbp_lb, BloodLastSetActivity.this.dstdbp_hb};
                BloodUtil.upBloodTb(BloodLastSetActivity.bloodInt);
                Editor editor = BloodLastSetActivity.this.getSharedPreferences("bloodhistory", 0).edit();
                editor.putInt("Onesbp_lb", BloodSettingActivity.Onesbp_lb);
                editor.putInt("Onedbp_lb", BloodSettingActivity.Onedbp_lb);
                editor.putInt("Twosbp_lb", BloodTwoSetActivity.Twosbp_lb);
                editor.putInt("Twodbp_lb", BloodTwoSetActivity.Twidbp_lb);
                editor.putInt("src_sbp", BloodLastSetActivity.src_sbp);
                editor.putInt("src_dbp", BloodLastSetActivity.src_dbp);
                editor.commit();
                DeviceUtils.writeCommandToDevice(38);
                Toast.makeText(BloodLastSetActivity.this, R.string.sport_module_bloodpressure_successful, 1).show();
                CommandOperation.setBoodSettingData(BloodLastSetActivity.src_sbp, BloodLastSetActivity.src_dbp, BloodLastSetActivity.avg_sbp, BloodLastSetActivity.avg_dbp);
                BpPreUpload prebloodData = new BpPreUpload();
                prebloodData.setStandard_sbp_1st(BloodSettingActivity.Onesbp_lb);
                prebloodData.setStandard_dbp_1st(BloodSettingActivity.Onedbp_lb);
                prebloodData.setStandard_sbp_2nd(BloodTwoSetActivity.Twosbp_lb);
                prebloodData.setStandard_dbp_2nd(BloodTwoSetActivity.Twidbp_lb);
                prebloodData.setMeasured_sbp(BloodLastSetActivity.src_sbp);
                prebloodData.setMeasured_dbp(BloodLastSetActivity.src_dbp);
                prebloodData.setUid(UserConfig.getInstance().getNewUID());
                NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                    public void onSuccess(Integer integer) {
                    }

                    public void onFail(Throwable e) {
                    }
                }).uploadBloodpressure(prebloodData);
                BloodSettingActivity.bloodSettingActivity.finish();
                BloodTwoSetActivity.bloodTwoSetActivity.finish();
                BloodLastSetActivity.this.finish();
            }
        });
    }
}
