package com.iwown.device_module.device_blood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;

public class BloodTwoSetActivity extends DeviceModuleBaseActivity {
    public static int Twidbp_lb;
    public static int Twosbp_lb;
    public static BloodTwoSetActivity bloodTwoSetActivity = null;
    /* access modifiers changed from: private */
    public EditText blood_Twohight;
    /* access modifiers changed from: private */
    public EditText blood_Twolow;
    private Button blood_bt_Twonext;
    private TextView toastMsg;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_blood_twoset);
        initView();
        bloodTwoSetActivity = this;
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_blood_DetailTwo);
        this.blood_Twohight = (EditText) findViewById(R.id.et_blood_Twoheigh);
        this.blood_Twolow = (EditText) findViewById(R.id.et_blood_Twolow);
        this.blood_bt_Twonext = (Button) findViewById(R.id.btn_blood_Twonext);
        this.blood_bt_Twonext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(BloodTwoSetActivity.this.blood_Twohight.getText().toString()) || TextUtils.isEmpty(BloodTwoSetActivity.this.blood_Twolow.getText().toString())) {
                    Toast.makeText(BloodTwoSetActivity.this, R.string.Blood_pressure_last_textnull, 1).show();
                    return;
                }
                BloodTwoSetActivity.Twosbp_lb = Integer.parseInt(BloodTwoSetActivity.this.blood_Twohight.getText().toString());
                BloodTwoSetActivity.Twidbp_lb = Integer.parseInt(BloodTwoSetActivity.this.blood_Twolow.getText().toString());
                if (Math.abs(BloodTwoSetActivity.Twosbp_lb - BloodSettingActivity.Onesbp_lb) > 10 || Math.abs(BloodTwoSetActivity.Twidbp_lb - BloodSettingActivity.Onedbp_lb) > 10) {
                    Toast.makeText(BloodTwoSetActivity.this, R.string.Blood_pressure_two_text, 1).show();
                } else if (BloodTwoSetActivity.Twosbp_lb < 60 || BloodTwoSetActivity.Twosbp_lb > 200 || BloodTwoSetActivity.Twidbp_lb < 30 || BloodTwoSetActivity.Twidbp_lb > 150) {
                    Toast.makeText(BloodTwoSetActivity.this, R.string.Blood_pressure_last_text, 1).show();
                } else {
                    BloodTwoSetActivity.this.startActivity(new Intent(BloodTwoSetActivity.this, BloodLastSetActivity.class));
                }
            }
        });
    }
}
