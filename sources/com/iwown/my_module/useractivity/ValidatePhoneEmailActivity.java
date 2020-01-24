package com.iwown.my_module.useractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.consts.UserConst;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.utility.TextValidator;
import com.iwown.my_module.widget.MyEditTex;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ValidatePhoneEmailActivity extends BaseActivity {
    MyEditTex mEmailEdt;
    RelativeLayout mLayout;
    TextView mOkBtn;
    MyEditTex mPhoneEdt;
    private Retrofit mRetrofit;
    private UserService mUserService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_validate_phone_email);
        this.mEmailEdt = (MyEditTex) findViewById(R.id.email_edt);
        this.mPhoneEdt = (MyEditTex) findViewById(R.id.phone_edt);
        this.mOkBtn = (TextView) findViewById(R.id.ok_btn);
        this.mOkBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ValidatePhoneEmailActivity.this.checkInput()) {
                    ValidatePhoneEmailActivity.this.findPwdByPhone();
                }
            }
        });
        this.mLayout = (RelativeLayout) findViewById(R.id.validate_phone_email_layout);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        setTitleText(getString(R.string.findpwdactiviey_title));
        setLeftBackTo();
        this.mOkBtn.setSelected(true);
        this.mEmailEdt.setEdtHint(R.string.email_address);
        this.mEmailEdt.setEdtHintColor(Color.parseColor("#B5B7BD"));
        this.mEmailEdt.setUderLineColorWhenChg(R.color.find_pwd_edt_underline_color_yes, R.color.find_pwd_edt_underline_color_no);
        this.mPhoneEdt.setEdtHint(R.string.phone_number_hint);
        this.mPhoneEdt.setEdtHintColor(Color.parseColor("#B5B7BD"));
        this.mPhoneEdt.setUderLineColorWhenChg(R.color.find_pwd_edt_underline_color_yes, R.color.find_pwd_edt_underline_color_no);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    /* access modifiers changed from: private */
    public void findPwdByPhone() {
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        final String email = this.mEmailEdt.getText().toString().trim();
        final String phone = this.mPhoneEdt.getText().toString().trim();
        Call<ReturnCode> call = this.mUserService.validatePhoneAndEmail(email, phone);
        showLoadingDialog();
        call.enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                ValidatePhoneEmailActivity.this.hideLoadingDialog();
                if (response == null || response.body() == null) {
                    ValidatePhoneEmailActivity.this.raiseErrorNotice(ValidatePhoneEmailActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                switch (((ReturnCode) response.body()).getRetCode()) {
                    case 0:
                        Intent intent = new Intent(ValidatePhoneEmailActivity.this, RetrievePwdByPhoneActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra(UserConst.PHONE, phone);
                        ValidatePhoneEmailActivity.this.startActivity(intent);
                        return;
                    case 10001:
                        ValidatePhoneEmailActivity.this.raiseErrorNotice(R.string.sql_error);
                        return;
                    case ApiConst.UserNotExist /*50012*/:
                        ValidatePhoneEmailActivity.this.raiseErrorNotice(R.string.user_phone_validate_error);
                        return;
                    default:
                        ValidatePhoneEmailActivity.this.raiseErrorNotice(ValidatePhoneEmailActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((ReturnCode) response.body()).getRetCode())}));
                        return;
                }
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                ValidatePhoneEmailActivity.this.hideLoadingDialog();
                ValidatePhoneEmailActivity.this.raiseErrorNotice(ValidatePhoneEmailActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkInput() {
        String email = this.mEmailEdt.getText().toString().trim();
        String phone = this.mPhoneEdt.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            this.mEmailEdt.requestFocus();
            raiseErrorNotice(R.string.empty_username);
            return false;
        } else if (CommonUtility.hasChinese(email)) {
            this.mEmailEdt.requestFocus();
            raiseErrorNotice(R.string.email_no_chinese);
            return false;
        } else if (!TextValidator.isEmail(email)) {
            this.mEmailEdt.requestFocus();
            raiseErrorNotice(R.string.email_error);
            return false;
        } else if (!TextUtils.isEmpty(phone)) {
            return true;
        } else {
            this.mPhoneEdt.requestFocus();
            raiseErrorNotice(R.string.empty_phone);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
