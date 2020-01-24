package com.iwown.device_module.device_blood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;

public class BloodSettingActivity extends DeviceModuleBaseActivity {
    public static int Onedbp_lb;
    public static int Onesbp_lb;
    public static BloodSettingActivity bloodSettingActivity = null;
    private Button blood_bt_next;
    /* access modifiers changed from: private */
    public EditText blood_hight;
    /* access modifiers changed from: private */
    public EditText blood_low;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_blood_setting);
        initView();
        bloodSettingActivity = this;
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_blood_DetailOne);
        this.blood_hight = (EditText) findViewById(R.id.et_blood_heigh);
        this.blood_low = (EditText) findViewById(R.id.et_blood_low);
        this.blood_bt_next = (Button) findViewById(R.id.btn_blood_next_1);
        this.blood_bt_next.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(BloodSettingActivity.this.blood_hight.getText().toString()) || TextUtils.isEmpty(BloodSettingActivity.this.blood_low.getText().toString())) {
                    Toast.makeText(BloodSettingActivity.this, R.string.Blood_pressure_last_textnull, 1).show();
                    return;
                }
                BloodSettingActivity.Onesbp_lb = Integer.parseInt(BloodSettingActivity.this.blood_hight.getText().toString());
                BloodSettingActivity.Onedbp_lb = Integer.parseInt(BloodSettingActivity.this.blood_low.getText().toString());
                if (BloodSettingActivity.Onesbp_lb < 60 || BloodSettingActivity.Onesbp_lb > 200 || BloodSettingActivity.Onedbp_lb < 30 || BloodSettingActivity.Onedbp_lb > 150) {
                    Toast.makeText(BloodSettingActivity.this, R.string.Blood_pressure_last_text, 1).show();
                    return;
                }
                BloodSettingActivity.this.startActivity(new Intent(BloodSettingActivity.this, BloodTwoSetActivity.class));
            }
        });
    }
}
