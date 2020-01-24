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
import com.iwown.my_module.model.request.RetrievePwdRequest;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.widget.MyEditTex;
import com.iwown.my_module.widget.MyEditTex.OnFocusChangedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrievePwdByPhoneActivity extends BaseActivity implements OnClickListener {
    private String mEmail;
    RelativeLayout mLayout;
    MyEditTex mNewPwdEdt;
    TextView mOkBtn;
    private String mPhone;
    private Retrofit mRetrofit;
    private UserService mUserService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_retrieve_password);
        this.mNewPwdEdt = (MyEditTex) findViewById(R.id.new_pwd_edt);
        this.mOkBtn = (TextView) findViewById(R.id.ok_btn);
        this.mOkBtn.setOnClickListener(this);
        this.mLayout = (RelativeLayout) findViewById(R.id.validate_phone_email_layout);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        Intent intent = getIntent();
        this.mEmail = intent.getStringExtra("email");
        this.mPhone = intent.getStringExtra(UserConst.PHONE);
        initView();
        initEvent();
    }

    private void initEvent() {
        this.mNewPwdEdt.setOnFocusChangedListener(new OnFocusChangedListener() {
            public void onFocusChanged(View v, boolean hasFocus) {
                if (hasFocus) {
                    RetrievePwdByPhoneActivity.this.mNewPwdEdt.setRightImgVisible(true);
                } else {
                    RetrievePwdByPhoneActivity.this.mNewPwdEdt.setRightImgVisible(false);
                }
            }
        });
    }

    private void initView() {
        setTitleText(getString(R.string.findpwdactiviey_title));
        setLeftBackTo();
        this.mOkBtn.setSelected(true);
        this.mNewPwdEdt.setEdtHint(R.string.new_password_input_hint);
        this.mNewPwdEdt.setEdtHintColor(Color.parseColor("#B5B7BD"));
        this.mNewPwdEdt.setUderLineColorWhenChg(R.color.find_pwd_edt_underline_color_yes, R.color.find_pwd_edt_underline_color_no);
        this.mNewPwdEdt.setEdtInputType(129);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void retrievePwd() {
        if (checkInput()) {
            findPwdByPhone();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ok_btn) {
            retrievePwd();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    private void findPwdByPhone() {
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        String pwd = this.mNewPwdEdt.getText().toString().trim();
        RetrievePwdRequest request = new RetrievePwdRequest();
        request.setEmail(this.mEmail);
        request.setPhone(this.mPhone);
        request.setPassword(pwd);
        Call<ReturnCode> call = this.mUserService.retrievePwd(request);
        showLoadingDialog();
        call.enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                RetrievePwdByPhoneActivity.this.hideLoadingDialog();
                if (response == null || response.body() == null) {
                    RetrievePwdByPhoneActivity.this.raiseErrorNotice(RetrievePwdByPhoneActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                switch (((ReturnCode) response.body()).getRetCode()) {
                    case 0:
                        Intent intent = new Intent(RetrievePwdByPhoneActivity.this, LoginActivity.class);
                        intent.setFlags(67108864);
                        RetrievePwdByPhoneActivity.this.startActivity(intent);
                        return;
                    case 10001:
                        RetrievePwdByPhoneActivity.this.raiseErrorNotice(R.string.sql_error);
                        return;
                    case ApiConst.UserNotExist /*50012*/:
                        RetrievePwdByPhoneActivity.this.raiseErrorNotice(R.string.no_user_error);
                        return;
                    default:
                        RetrievePwdByPhoneActivity.this.raiseErrorNotice(RetrievePwdByPhoneActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((ReturnCode) response.body()).getRetCode())}));
                        return;
                }
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                RetrievePwdByPhoneActivity.this.hideLoadingDialog();
                RetrievePwdByPhoneActivity.this.raiseErrorNotice(RetrievePwdByPhoneActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
    }

    private boolean checkInput() {
        String pwd = this.mNewPwdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            this.mNewPwdEdt.requestFocus();
            raiseErrorNotice(R.string.empty_password);
            return false;
        } else if (CommonUtility.hasChinese(pwd)) {
            this.mNewPwdEdt.requestFocus();
            raiseErrorNotice(R.string.password_no_chinese);
            return false;
        } else if (pwd.length() < 6) {
            this.mNewPwdEdt.requestFocus();
            raiseErrorNotice(R.string.email_password_length);
            return false;
        } else if (pwd.length() <= 18) {
            return true;
        } else {
            this.mNewPwdEdt.requestFocus();
            raiseErrorNotice(R.string.email_password_length_18_limit);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
