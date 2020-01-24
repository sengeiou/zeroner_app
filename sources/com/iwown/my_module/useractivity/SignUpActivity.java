package com.iwown.my_module.useractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.model.request.LoginRequest;
import com.iwown.my_module.model.request.RegisterRequest;
import com.iwown.my_module.model.request.UploadAccountInfoRequest;
import com.iwown.my_module.model.response.LoginResponse;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.useractivity.profile.NewProfileGenderActivity;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.utility.TextValidator;
import com.iwown.my_module.widget.MyEditTex;
import com.iwown.my_module.widget.MyEditTex.OnFocusChangedListener;
import com.iwown.my_module.widget.MyEditTex.OnTextChangedListener;
import com.socks.library.KLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends BaseActivity {
    private static final String TAG = "SignUpActivity";
    MyEditTex mEmailEdt;
    TextView mEmailInputHint;
    LinearLayout mLayout;
    /* access modifiers changed from: private */
    public PreferenceUtility mPrefUtil = new PreferenceUtility(this);
    MyEditTex mPwdEdt;
    private Retrofit mRetrofit;
    TextView mSignUpBtn;
    private UserService mUserService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_register);
        this.mEmailEdt = (MyEditTex) findViewById(R.id.email_edt);
        this.mPwdEdt = (MyEditTex) findViewById(R.id.pwd_edt);
        this.mSignUpBtn = (TextView) findViewById(R.id.sign_up_btn);
        this.mSignUpBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SignUpActivity.this.checkRegisterInfo()) {
                    SignUpActivity.this.register();
                }
            }
        });
        this.mEmailInputHint = (TextView) findViewById(R.id.register_email_input_tip);
        this.mLayout = (LinearLayout) findViewById(R.id.register_main);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        initView();
        initEvent();
    }

    private void initEvent() {
        this.mEmailEdt.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    SignUpActivity.this.mSignUpBtn.setSelected(false);
                } else if (SignUpActivity.this.checkOkBtnEnable()) {
                    SignUpActivity.this.mSignUpBtn.setSelected(true);
                }
            }
        });
        this.mEmailEdt.setOnFocusChangedListener(new OnFocusChangedListener() {
            public void onFocusChanged(View v, boolean hasFocus) {
                if (hasFocus) {
                    SignUpActivity.this.mEmailInputHint.setVisibility(0);
                } else {
                    SignUpActivity.this.mEmailInputHint.setVisibility(8);
                }
            }
        });
        this.mPwdEdt.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    SignUpActivity.this.mSignUpBtn.setSelected(false);
                } else if (SignUpActivity.this.checkOkBtnEnable()) {
                    SignUpActivity.this.mSignUpBtn.setSelected(true);
                }
            }
        });
        this.mPwdEdt.setOnFocusChangedListener(new OnFocusChangedListener() {
            public void onFocusChanged(View v, boolean hasFocus) {
                if (hasFocus) {
                    SignUpActivity.this.mPwdEdt.setRightImgVisible(true);
                } else {
                    SignUpActivity.this.mPwdEdt.setRightImgVisible(false);
                }
            }
        });
    }

    private void initView() {
        setTitleText(getString(R.string.signup_activiey_title));
        setLeftBackTo();
        this.mSignUpBtn.setSelected(false);
        this.mEmailEdt.setEdtHint(R.string.email_address);
        this.mEmailEdt.setEdtHintColor(Color.parseColor("#B5B7BD"));
        this.mEmailEdt.setUderLineColorWhenChg(R.color.find_pwd_edt_underline_color_yes, R.color.find_pwd_edt_underline_color_no);
        this.mPwdEdt.setEdtHint(R.string.password_register_input_hint);
        this.mPwdEdt.setEdtHintColor(Color.parseColor("#B5B7BD"));
        this.mPwdEdt.setUderLineColorWhenChg(R.color.find_pwd_edt_underline_color_yes, R.color.find_pwd_edt_underline_color_no);
        this.mPwdEdt.setEdtInputType(129);
    }

    public boolean checkOkBtnEnable() {
        if (!this.mEmailEdt.isHasContent() || !this.mPwdEdt.isHasContent()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public boolean checkRegisterInfo() {
        String user = this.mEmailEdt.getText().toString().trim();
        String pwd = this.mPwdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            raiseErrorNotice(R.string.empty_username);
            this.mEmailEdt.requestFocus();
            return false;
        } else if (CommonUtility.hasChinese(user)) {
            raiseErrorNotice(R.string.email_no_chinese);
            this.mEmailEdt.requestFocus();
            return false;
        } else if (!TextValidator.isEmail(user)) {
            raiseErrorNotice(R.string.email_error);
            this.mEmailEdt.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            raiseErrorNotice(R.string.empty_password);
            this.mPwdEdt.requestFocus();
            return false;
        } else if (CommonUtility.hasChinese(pwd)) {
            raiseErrorNotice(R.string.password_no_chinese);
            this.mPwdEdt.requestFocus();
            return false;
        } else if (pwd.length() < 6) {
            raiseErrorNotice(R.string.email_password_length);
            this.mPwdEdt.requestFocus();
            return false;
        } else if (pwd.length() <= 18) {
            return true;
        } else {
            raiseErrorNotice(R.string.email_password_length_18_limit);
            this.mPwdEdt.requestFocus();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void register() {
        final String user = this.mEmailEdt.getText().toString().trim();
        final String pwd = this.mPwdEdt.getText().toString().trim();
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        RegisterRequest request = new RegisterRequest();
        request.setAccount(user);
        request.setPassword(pwd);
        request.setType(2);
        Call<LoginResponse> call = this.mUserService.register(request);
        showLoadingDialog();
        call.enqueue(new Callback<LoginResponse>() {
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                SignUpActivity.this.hideLoadingDialog();
                if (response == null || response.body() == null) {
                    SignUpActivity.this.raiseErrorNotice(SignUpActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                switch (((LoginResponse) response.body()).getRetCode()) {
                    case 0:
                        UserConfig.getInstance().setHasGuideSport(false);
                        UserConfig.getInstance().setHasGuideHome(false);
                        UserConfig.getInstance().setHasGuideHomeRefresh(false);
                        UserConfig.getInstance().setHasGuideI7A(false);
                        UserConfig.getInstance().save();
                        GlobalDataUpdater.setMineGuideStatus(SignUpActivity.this, 0);
                        SignUpActivity.this.mPrefUtil.updateLongValueWithKey(UserConst.UID, ((LoginResponse) response.body()).getUid());
                        SignUpActivity.this.mPrefUtil.updateStrValueWithKey("email", user);
                        GlobalDataUpdater.setLoginStatus(SignUpActivity.this, 1);
                        SignUpActivity.this.uploadAccountInfo();
                        KLog.i("====" + new DateUtil().getY_M_D());
                        GlobalDataUpdater.setRegisterDate(SignUpActivity.this, new DateUtil().getY_M_D());
                        SignUpActivity.this.autoLogin(user, pwd);
                        return;
                    case 10001:
                        SignUpActivity.this.raiseErrorNotice(R.string.sql_error);
                        return;
                    case ApiConst.UserAlreadyExist /*50004*/:
                    case ApiConst.UserAlreadyExistMoreThanOne /*80001*/:
                        SignUpActivity.this.raiseErrorNotice(R.string.activity_user_exist);
                        return;
                    default:
                        SignUpActivity.this.raiseErrorNotice(SignUpActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((LoginResponse) response.body()).getRetCode())}));
                        return;
                }
            }

            public void onFailure(Call<LoginResponse> call, Throwable t) {
                SignUpActivity.this.hideLoadingDialog();
                SignUpActivity.this.raiseErrorNotice(SignUpActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
    }

    /* access modifiers changed from: private */
    public void autoLogin(String user, String pwd) {
        LoginRequest request = new LoginRequest();
        request.setType(2);
        request.setAccount(user);
        request.setPassword(pwd);
        this.mUserService.login(request).enqueue(new Callback<LoginResponse>() {
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response == null || response.body() == null) {
                    SignUpActivity.this.raiseErrorNotice(SignUpActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                switch (((LoginResponse) response.body()).getRetCode()) {
                    case 0:
                        KLog.i("===autoLogin==" + ((LoginResponse) response.body()).getRegister_date());
                        if (!TextUtils.isEmpty(((LoginResponse) response.body()).getRegister_date())) {
                            GlobalDataUpdater.setRegisterDate(SignUpActivity.this, ((LoginResponse) response.body()).getRegister_date());
                            return;
                        }
                        return;
                    default:
                        SignUpActivity.this.raiseErrorNotice(SignUpActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((LoginResponse) response.body()).getRetCode())}));
                        return;
                }
            }

            public void onFailure(Call<LoginResponse> call, Throwable t) {
                SignUpActivity.this.raiseErrorNotice(SignUpActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void uploadAccountInfo() {
        UploadAccountInfoRequest request = new UploadAccountInfoRequest();
        String locale = UserConfig.getInstance(this).getCountry();
        if (TextUtils.isEmpty(locale)) {
            locale = ((TelephonyManager) getSystemService(UserConst.PHONE)).getSimCountryIso();
        }
        try {
            request.setApp_version(getPackageManager().getPackageInfo(getPackageName(), 16384).versionCode);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
        request.setCountry(locale);
        request.setModel(Build.MODEL);
        request.setBrand(Build.BRAND);
        request.setSdk_version(VERSION.RELEASE);
        request.setUid(GlobalUserDataFetcher.getCurrentUid(this).longValue());
        request.setApp(7);
        this.mUserService.updateAccountInfo(request).enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                if (!(response == null || response.body() == null)) {
                    Log.i(SignUpActivity.TAG, String.format("upload account info, retCode:%d", new Object[]{Integer.valueOf(((ReturnCode) response.body()).getRetCode())}));
                }
                Intent intent = new Intent(SignUpActivity.this, NewProfileGenderActivity.class);
                intent.setFlags(268468224);
                SignUpActivity.this.startActivity(intent);
                SignUpActivity.this.finish();
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                Log.i(SignUpActivity.TAG, "upload account info - failure");
                Intent intent = new Intent(SignUpActivity.this, NewProfileGenderActivity.class);
                intent.setFlags(268468224);
                SignUpActivity.this.startActivity(intent);
                SignUpActivity.this.finish();
            }
        });
    }
}
