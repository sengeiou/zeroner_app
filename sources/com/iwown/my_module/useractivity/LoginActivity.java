package com.iwown.my_module.useractivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.AppManger;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.my_module.R;
import com.iwown.my_module.data.HealthGoalEntity;
import com.iwown.my_module.data.TB_data_import;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.dialog.DeleteContactsDialog;
import com.iwown.my_module.dialog.DeleteContactsDialog.OnConfirmButtonClickListener;
import com.iwown.my_module.dialog.DeleteContactsDialog.OnLinkClickedListener;
import com.iwown.my_module.model.request.LoginRequest;
import com.iwown.my_module.model.request.UploadAccountInfoRequest;
import com.iwown.my_module.model.response.Goal;
import com.iwown.my_module.model.response.LoginResponse;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.model.response.UserInfo;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.settingactivity.CustomWebViewActivity;
import com.iwown.my_module.settingactivity.GoalSettingActivity;
import com.iwown.my_module.useractivity.profile.NewProfileGenderActivity;
import com.iwown.my_module.utility.ScreenUtility;
import com.iwown.my_module.utility.StatusbarHelper;
import com.iwown.my_module.utility.TextValidator;
import com.iwown.my_module.widget.ErrorTipTextView;
import com.iwown.my_module.widget.ErrorTipTextView.OnDisplayEndListener;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import org.litepal.crud.DataSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity implements OnClickListener {
    private static final String TAG = "LoginActivity";
    /* access modifiers changed from: private */
    public LoadingDialog loadDialog;
    TextView mBtnSignin;
    ImageView mDisclosePwdBtn;
    EditText mEmailEdt;
    ErrorTipTextView mErrorTipTv;
    TextView mForgetPwdBtn;
    RelativeLayout mLayout;
    /* access modifiers changed from: private */
    public PreferenceUtility mPrefUtil = new PreferenceUtility(this);
    private TextView mPrivacyBtn;
    EditText mPwdEdt;
    private Retrofit mRetrofit;
    TextView mToSignUpBtn;
    private UserService mUserService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        StatusbarHelper.hideStatusBar(this);
        setContentView(R.layout.my_module_activity_login);
        this.mErrorTipTv = (ErrorTipTextView) findViewById(R.id.error_tip_tv);
        this.mEmailEdt = (EditText) findViewById(R.id.email_edt);
        this.mPwdEdt = (EditText) findViewById(R.id.pwd_edt);
        this.mBtnSignin = (TextView) findViewById(R.id.login_btn);
        this.mBtnSignin.setOnClickListener(this);
        this.mToSignUpBtn = (TextView) findViewById(R.id.to_sign_up_btn);
        this.mToSignUpBtn.setOnClickListener(this);
        this.mForgetPwdBtn = (TextView) findViewById(R.id.forget_pwd_tv);
        this.mForgetPwdBtn.setOnClickListener(this);
        this.mDisclosePwdBtn = (ImageView) findViewById(R.id.login_img_disclose_pwd);
        this.mDisclosePwdBtn.setOnClickListener(this);
        this.mDisclosePwdBtn.setVisibility(8);
        this.mLayout = (RelativeLayout) findViewById(R.id.login_main);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        this.mPrefUtil = new PreferenceUtility(this);
        this.mPrivacyBtn = (TextView) findViewById(R.id.privacy_btn);
        this.mPrivacyBtn.setText(getString(R.string.the_privacy_policy, new Object[]{AppConfigUtil.app_name}));
        AppManger.getAppManager().finishAllActivity(this);
        String pre_email = null;
        if (!TextUtils.isEmpty(GlobalUserDataFetcher.getEmail(this))) {
            pre_email = GlobalUserDataFetcher.getEmail(this);
        } else if (!TextUtils.isEmpty(GlobalUserDataFetcher.getEmailOldApp(this))) {
            pre_email = GlobalUserDataFetcher.getEmailOldApp(this);
        }
        if (!TextUtils.isEmpty(pre_email)) {
            this.mEmailEdt.setText(pre_email);
        }
        initView();
        initEvent();
    }

    private void initView() {
        int statusBarHeight = StatusbarHelper.getStatusBarHeight();
        this.mErrorTipTv.setInitialHeightAndWidth(statusBarHeight + 20, ScreenUtility.getScreenWidth(this) - 120);
        this.mErrorTipTv.setTranslationY((float) (-statusBarHeight));
        this.mErrorTipTv.setViewBackground(R.drawable.dark_theme_round_corner);
        this.mPwdEdt.setInputType(129);
        DeleteContactsDialog mDeleteContactsDialog = new DeleteContactsDialog(this, false);
        mDeleteContactsDialog.setOnConfirmButtonClickListener(new OnConfirmButtonClickListener() {
            public void onConfirmButtonClick(boolean isConfirm) {
                if (isConfirm) {
                    new PreferenceUtility(LoginActivity.this).updateNumberValueWithKey(UserConst.AGREE_PRIVACY_TERM, 1);
                } else {
                    System.exit(1);
                }
            }
        });
        mDeleteContactsDialog.show();
        mDeleteContactsDialog.setOnLinkClickedListener(new OnLinkClickedListener() {
            public void onLinkClicked() {
                Intent intent = new Intent(LoginActivity.this, CustomWebViewActivity.class);
                intent.putExtra("url", AppConfigUtil.Privacy_Link);
                intent.putExtra("title", "Privacy");
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    private void initEvent() {
        GlobalDataUpdater.setRegisterTime(this, 0);
        this.mErrorTipTv.setDisplayEndListener(new OnDisplayEndListener() {
            public void onDisplayEnd() {
                LoginActivity.this.mLayout.setSystemUiVisibility(0);
            }
        });
        this.mPwdEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    LoginActivity.this.mDisclosePwdBtn.setVisibility(0);
                    LoginActivity.this.mDisclosePwdBtn.setOnClickListener(LoginActivity.this);
                    return;
                }
                LoginActivity.this.mDisclosePwdBtn.setVisibility(8);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void login() {
        if (checkLoginInfo()) {
            if (this.mUserService == null) {
                this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
            }
            final String user = this.mEmailEdt.getText().toString().trim();
            String pwd = this.mPwdEdt.getText().toString().trim();
            LoginRequest request = new LoginRequest();
            request.setType(2);
            request.setAccount(user);
            request.setPassword(pwd);
            Call<LoginResponse> call = this.mUserService.login(request);
            if (this.loadDialog == null) {
                this.loadDialog = new LoadingDialog(this);
            }
            this.loadDialog.show();
            call.enqueue(new Callback<LoginResponse>() {
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginActivity.this.loadDialog.dismiss();
                    if (response == null || response.body() == null) {
                        LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                        return;
                    }
                    switch (((LoginResponse) response.body()).getRetCode()) {
                        case 0:
                            LoginActivity.this.mPrefUtil.updateLongValueWithKey(UserConst.UID, ((LoginResponse) response.body()).getUid());
                            GlobalDataUpdater.setLoginStatus(LoginActivity.this, 3);
                            GlobalDataUpdater.setEmail(LoginActivity.this, user);
                            GlobalDataUpdater.setRegisterTime(LoginActivity.this, DateUtil.dateY_M_D2Stamp(((LoginResponse) response.body()).getRegister_date()));
                            LoginActivity.this.downloadUserInfo(((LoginResponse) response.body()).getUid());
                            return;
                        case ApiConst.PasswordNotMatch /*50003*/:
                            LoginActivity.this.raiseErrorNotice(R.string.message_login_error);
                            return;
                        case ApiConst.UserNotExist /*50012*/:
                            LoginActivity.this.raiseErrorNotice(R.string.no_user_error);
                            return;
                        default:
                            LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((LoginResponse) response.body()).getRetCode())}));
                            return;
                    }
                }

                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (!LoginActivity.this.isFinishing()) {
                        LoginActivity.this.loadDialog.dismiss();
                        LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: 0000 */
    public void openSignUpView() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    /* access modifiers changed from: 0000 */
    public void disclosePwd() {
        if (this.mPwdEdt.getInputType() == 129) {
            this.mPwdEdt.setInputType(Opcodes.SUB_INT);
        } else {
            this.mPwdEdt.setInputType(129);
        }
    }

    /* access modifiers changed from: 0000 */
    public void openForgetPwdView() {
        startActivity(new Intent(this, GetbackPwdActivity.class));
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.login_btn) {
            login();
        } else if (i == R.id.to_sign_up_btn) {
            openSignUpView();
        } else if (i == R.id.login_img_disclose_pwd) {
            disclosePwd();
        } else if (i == R.id.forget_pwd_tv) {
            openForgetPwdView();
        }
    }

    public boolean checkLoginInfo() {
        String user = this.mEmailEdt.getText().toString().trim();
        String pwd = this.mPwdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            raiseErrorNotice(R.string.empty_username, this.mEmailEdt);
            return false;
        } else if (!TextValidator.isEmail(user) && !TextValidator.isPhoneNumber(user)) {
            raiseErrorNotice(R.string.email_error, this.mEmailEdt);
            return false;
        } else if (!TextUtils.isEmpty(pwd)) {
            return true;
        } else {
            raiseErrorNotice(R.string.empty_password, this.mPwdEdt);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void raiseErrorNotice(int resId, EditText focusEditView) {
        this.mLayout.setSystemUiVisibility(4);
        focusEditView.requestFocus();
        this.mErrorTipTv.setText(getResources().getString(resId));
        this.mErrorTipTv.startAnim();
    }

    /* access modifiers changed from: 0000 */
    public void raiseErrorNotice(int resId) {
        this.mLayout.setSystemUiVisibility(4);
        this.mErrorTipTv.setText(getResources().getString(resId));
        this.mErrorTipTv.startAnim();
    }

    /* access modifiers changed from: 0000 */
    public void raiseErrorNotice(String errMsg) {
        int statusBarHeight = StatusbarHelper.getStatusBarHeight();
        int screenWidth = ScreenUtility.getScreenWidth(this);
        this.mLayout.setSystemUiVisibility(4);
        this.mErrorTipTv.setText(errMsg);
        this.mErrorTipTv.startAnim();
    }

    /* access modifiers changed from: 0000 */
    public void downloadUserInfo(long uid) {
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        this.mUserService.userInfo(uid).enqueue(new Callback<UserInfo>() {
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response == null || response.body() == null) {
                    LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                UserInfo userInfo = (UserInfo) response.body();
                if (userInfo.getRetCode() == 0) {
                    GlobalDataUpdater.setLoginStatus(LoginActivity.this, 2);
                    UserInfoEntity baseInfo = new UserInfoEntity();
                    LoginActivity.this.copyUserInfo(baseInfo, userInfo);
                    baseInfo.saveOrUpdate("uid=?", String.valueOf(baseInfo.getUid()));
                    Log.i(LoginActivity.TAG, "download user info succeed");
                    LoginActivity.this.downloadGoal(userInfo.getUid());
                    return;
                }
                LoginActivity.this.enter(1);
            }

            public void onFailure(Call<UserInfo> call, Throwable t) {
                LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
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
        if (!TextUtils.isEmpty(locale)) {
            request.setCountry(locale.toUpperCase());
        }
        request.setModel(Build.MODEL);
        request.setBrand(Build.BRAND);
        request.setSdk_version(VERSION.RELEASE);
        request.setUid(GlobalUserDataFetcher.getCurrentUid(this).longValue());
        if (AppConfigUtil.isIwownFitPro()) {
            request.setApp(7);
        } else if (AppConfigUtil.isZeronerHealthPro()) {
            request.setApp(6);
        } else if (AppConfigUtil.isNanfei_TRAX_GPS()) {
            request.setApp(12);
        } else if (AppConfigUtil.isUpfit()) {
            request.setApp(13);
        } else if (AppConfigUtil.isNewfit()) {
            request.setApp(14);
        } else if (AppConfigUtil.isDrviva()) {
            request.setApp(22);
        }
        this.mUserService.updateAccountInfo(request).enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                if (response != null && response.body() != null) {
                    Log.i(LoginActivity.TAG, String.format("upload account info, retCode:%d", new Object[]{Integer.valueOf(((ReturnCode) response.body()).getRetCode())}));
                }
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                Log.i(LoginActivity.TAG, "upload account info - failure");
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void downloadGoal(long uid) {
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        this.mUserService.getGoal(uid).enqueue(new Callback<Goal>() {
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                if (response == null || response.body() == null) {
                    LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                Goal goal_server = (Goal) response.body();
                if (goal_server.getRetCode() == 0) {
                    HealthGoalEntity mGoal = new HealthGoalEntity();
                    mGoal.setUid(goal_server.getUid());
                    mGoal.setTarget_step(goal_server.getTarget_step());
                    mGoal.setTarget_weight(goal_server.getTarget_weight());
                    mGoal.saveOrUpdate("uid=?", String.valueOf(goal_server.getUid()));
                    GlobalDataUpdater.setLoginStatus(LoginActivity.this, 3);
                    try {
                        ModuleRouteDeviceInfoService.getInstance().updateTargetStep(mGoal.getTarget_step(), new com.iwown.data_link.user_pre.UserInfo().getGoalCaloria());
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    Log.i(LoginActivity.TAG, "download goal succeed");
                    LoginActivity.this.enter(3);
                    return;
                }
                LoginActivity.this.enter(2);
            }

            public void onFailure(Call<Goal> call, Throwable t) {
                LoginActivity.this.raiseErrorNotice(LoginActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void enter(int status) {
        Log.i(TAG, String.format("login status:%d", new Object[]{Integer.valueOf(status)}));
        switch (status) {
            case 1:
                Intent intent = new Intent(this, NewProfileGenderActivity.class);
                intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
                startActivity(intent);
                finish();
                return;
            case 2:
                Intent intent2 = new Intent(this, GoalSettingActivity.class);
                intent2.putExtra(GoalSettingActivity.GOALVIEWSTATUS, 1);
                startActivity(intent2);
                finish();
                return;
            case 3:
                finish();
                long reTime = GlobalUserDataFetcher.getRegisterTime(this);
                if (reTime <= 100 || reTime >= 1540483200000L) {
                    RouteUtils.startAPPMainActivitry();
                    return;
                }
                TB_data_import dataImport = (TB_data_import) DataSupport.where("uid=?", GlobalUserDataFetcher.getCurrentUid(this).longValue() + "").findFirst(TB_data_import.class);
                if (dataImport == null || dataImport.getCode() == 0) {
                    RouteUtils.startDataImportActivity();
                    return;
                }
                RouteUtils.startAPPMainActivitry();
                Log.i(TAG, "navigate to main");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: 0000 */
    public void copyUserInfo(UserInfoEntity target, UserInfo src) {
        target.setUid(src.getUid());
        target.setGender(src.getGender());
        target.setHeight(src.getHeight());
        target.setWeight(src.getWeight());
        target.setBirthday(src.getBirthday());
        target.setNickname(src.getNickname());
        target.setPortrait_url(src.getPortrait_url());
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v == null || !(v instanceof EditText)) {
            return false;
        }
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0];
        int top = l[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();
        if (event.getX() <= ((float) left) || event.getX() >= ((float) right) || event.getY() <= ((float) top) || event.getY() >= ((float) bottom)) {
            return true;
        }
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(token, 2);
        }
    }
}
