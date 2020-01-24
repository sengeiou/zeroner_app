package com.iwown.my_module.useractivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.activity.PhoneOrEmailActivity;
import com.iwown.my_module.widget.TextImageButton;

public class GetbackPwdActivity extends BaseActivity implements OnClickListener {
    private String email;
    TextImageButton mFindPwdEmail;
    TextImageButton mFindPwdPhone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_find_pwd);
        this.mFindPwdPhone = (TextImageButton) findViewById(R.id.find_pwd_phone);
        this.mFindPwdPhone.setOnClickListener(this);
        this.mFindPwdEmail = (TextImageButton) findViewById(R.id.find_pwd_email);
        this.mFindPwdEmail.setOnClickListener(this);
        this.email = getIntent().getStringExtra("email");
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.findpwdactiviey_title));
        setLeftBackTo();
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.find_pwd_phone) {
            AppConfigUtil.getInstance(this);
            if (AppConfigUtil.isHealthy()) {
                Intent intent = new Intent(this, PhoneOrEmailActivity.class);
                intent.putExtra("enter", 1);
                startActivity(intent);
                return;
            }
            startActivity(new Intent(this, ValidatePhoneEmailActivity.class));
        } else if (i == R.id.find_pwd_email) {
            AppConfigUtil.getInstance(this);
            if (AppConfigUtil.isHealthy()) {
                Intent intent2 = new Intent(this, PhoneOrEmailActivity.class);
                intent2.putExtra("enter", 3);
                startActivity(intent2);
                return;
            }
            startActivity(new Intent(this, GetbackPwdByEmailActivity.class));
        }
    }
}
