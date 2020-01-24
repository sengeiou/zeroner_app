package com.iwown.my_module.healthy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
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
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.my_module.R;
import com.iwown.my_module.data.TB_data_import;
import com.iwown.my_module.healthy.activity.PhoneOrEmailActivity;
import com.iwown.my_module.healthy.contract.LoginContract.LoginView;
import com.iwown.my_module.healthy.event.LoginEvent;
import com.iwown.my_module.healthy.presenter.LoginPresenter;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.settingactivity.CustomWebViewActivity;
import com.iwown.my_module.settingactivity.GoalSettingActivity;
import com.iwown.my_module.useractivity.GetbackPwdActivity;
import com.iwown.my_module.useractivity.profile.NewProfileGenderActivity;
import com.iwown.my_module.utility.ScreenUtility;
import com.iwown.my_module.utility.StatusbarHelper;
import com.iwown.my_module.utility.TextValidator;
import com.iwown.my_module.widget.ErrorTipTextView;
import com.iwown.my_module.widget.ErrorTipTextView.OnDisplayEndListener;
import com.kunekt.healthy.wxapi.WxQqAPI;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Req;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import retrofit2.Retrofit;

public class HealthyLoginActivity extends Activity implements OnClickListener, LoginView {
    private static final String TAG = "HealthyLoginActivity";
    private LoadingDialog loadDialog;
    IUiListener loginListener = new BaseUiListener() {
        /* access modifiers changed from: protected */
        public void doComplete(JSONObject values) {
            KLog.d("no2855QQ的asstoken:" + values.toString());
            if (values.has("openid")) {
                try {
                    String openId = values.getString("openid");
                    String qqToken = values.getString(Constants.PARAM_ACCESS_TOKEN);
                    HealthyLoginActivity.this.showLoadDialog();
                    HealthyLoginActivity.this.initOpenidAndToken(values);
                    HealthyLoginActivity.this.loginPresenter.getQqOrWxUid(4, qqToken, openId);
                } catch (JSONException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public LoginPresenter loginPresenter;
    ImageView loginQQ;
    ImageView loginWx;
    TextView mBtnSignin;
    ImageView mDisclosePwdBtn;
    EditText mEmailEdt;
    ErrorTipTextView mErrorTipTv;
    TextView mForgetPwdBtn;
    RelativeLayout mLayout;
    EditText mPwdEdt;
    private Retrofit mRetrofit;
    TextView mToSignUpBtn;
    private UserService mUserService;
    TextView privacyTxt;

    private class BaseUiListener implements IUiListener {
        private BaseUiListener() {
        }

        public void onComplete(Object response) {
            if (response != null) {
                JSONObject jsonResponse = (JSONObject) response;
                try {
                    KLog.e("no2855 QQ登录信息: " + jsonResponse.toString());
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (jsonResponse == null || jsonResponse.length() != 0) {
                    doComplete((JSONObject) response);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void doComplete(JSONObject values) {
        }

        public void onError(UiError e) {
        }

        public void onCancel() {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        StatusbarHelper.hideStatusBar(this);
        setContentView(R.layout.my_module_healthy_login);
        AppManger.getAppManager().finishAllActivity(this);
        EventBus.getDefault().register(this);
        initView();
        initEvent();
        this.loginPresenter = new LoginPresenter(this, this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        dismissLoadDialog();
        super.onDestroy();
    }

    private void initQQWx() {
        WxQqAPI.initQQAndWxAPI(this);
    }

    private void initView() {
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
        this.loginQQ = (ImageView) findViewById(R.id.login_qq);
        this.loginWx = (ImageView) findViewById(R.id.login_weichat);
        this.privacyTxt = (TextView) findViewById(R.id.privacy_statement);
        this.privacyTxt.getPaint().setFlags(8);
        this.privacyTxt.getPaint().setAntiAlias(true);
        this.loginQQ.setOnClickListener(this);
        this.loginWx.setOnClickListener(this);
        this.privacyTxt.setOnClickListener(this);
        int statusBarHeight = StatusbarHelper.getStatusBarHeight();
        this.mErrorTipTv.setInitialHeightAndWidth(statusBarHeight + 20, ScreenUtility.getScreenWidth(this) - 120);
        this.mErrorTipTv.setTranslationY((float) (-statusBarHeight));
        this.mErrorTipTv.setViewBackground(R.drawable.dark_theme_round_corner);
        this.mPwdEdt.setInputType(129);
    }

    private void initEvent() {
        GlobalDataUpdater.setMeasureUnit(this, EnumMeasureUnit.Metric);
        GlobalDataUpdater.setRegisterTime(this, 0);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        this.mErrorTipTv.setDisplayEndListener(new OnDisplayEndListener() {
            public void onDisplayEnd() {
                HealthyLoginActivity.this.mLayout.setSystemUiVisibility(0);
            }
        });
        this.mPwdEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    HealthyLoginActivity.this.mDisclosePwdBtn.setVisibility(0);
                    HealthyLoginActivity.this.mDisclosePwdBtn.setOnClickListener(HealthyLoginActivity.this);
                    return;
                }
                HealthyLoginActivity.this.mDisclosePwdBtn.setVisibility(8);
            }
        });
    }

    /* access modifiers changed from: private */
    public void showLoadDialog() {
        if (!isDestroyed()) {
            if (this.loadDialog == null) {
                this.loadDialog = new LoadingDialog(this);
            }
            this.loadDialog.setCancelable(false);
            this.loadDialog.show();
            this.loadDialog.setMessage("登录中...");
        }
    }

    private void dismissLoadDialog() {
        if (!isDestroyed() && this.loadDialog != null) {
            this.loadDialog.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent loginEvent) {
        KLog.e("no2855微信返回");
        if (loginEvent.getOpenid() == null) {
            raiseErrorNotice(R.string.wechat_auth_error, this.mEmailEdt);
            return;
        }
        showLoadDialog();
        this.loginPresenter.getQqOrWxUid(3, loginEvent.getToken(), loginEvent.getOpenid());
    }

    /* access modifiers changed from: 0000 */
    public void login() {
        if (checkLoginInfo()) {
            String user = this.mEmailEdt.getText().toString().trim();
            String pwd = this.mPwdEdt.getText().toString().trim();
            showLoadDialog();
            this.loginPresenter.getPhoneUid(user, pwd);
        }
    }

    /* access modifiers changed from: 0000 */
    public void openSignUpView() {
        Intent intent = new Intent(this, PhoneOrEmailActivity.class);
        intent.putExtra("enter", 2);
        startActivity(intent);
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
            setClickState(false);
            login();
        } else if (i == R.id.to_sign_up_btn) {
            openSignUpView();
        } else if (i == R.id.login_img_disclose_pwd) {
            disclosePwd();
        } else if (i == R.id.forget_pwd_tv) {
            openForgetPwdView();
        } else if (i == R.id.login_qq) {
            setClickState(false);
            qqLogin();
        } else if (i == R.id.login_weichat) {
            setClickState(false);
            wxLogin();
        } else if (i == R.id.privacy_statement) {
            Intent intent = new Intent(this, CustomWebViewActivity.class);
            intent.putExtra("url", AppConfigUtil.getPrivacy_Link());
            intent.putExtra("title", getString(R.string.iwown_privacy));
            startActivity(intent);
        }
    }

    public boolean checkLoginInfo() {
        String user = this.mEmailEdt.getText().toString().trim();
        String pwd = this.mPwdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            raiseErrorNotice(R.string.empty_username, this.mEmailEdt);
            return false;
        } else if (TextValidator.isEmail(user) || TextValidator.isPhoneNumber(user)) {
            if (!TextUtils.isEmpty(pwd)) {
                return true;
            }
            raiseErrorNotice(R.string.empty_password, this.mPwdEdt);
            return false;
        } else if (AppConfigUtil.isHealthy(this)) {
            raiseErrorNotice(R.string.healthy_phone_error, this.mEmailEdt);
            return false;
        } else {
            raiseErrorNotice(R.string.email_error, this.mEmailEdt);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void raiseErrorNotice(int resId, EditText focusEditView) {
        setClickState(true);
        this.mLayout.setSystemUiVisibility(4);
        focusEditView.requestFocus();
        this.mErrorTipTv.setText(getResources().getString(resId));
        this.mErrorTipTv.startAnim();
    }

    /* access modifiers changed from: 0000 */
    public void raiseErrorNotice(int resId) {
        setClickState(true);
        this.mLayout.setSystemUiVisibility(4);
        this.mErrorTipTv.setText(getResources().getString(resId));
        this.mErrorTipTv.startAnim();
    }

    /* access modifiers changed from: 0000 */
    public void raiseErrorNotice(String errMsg) {
        setClickState(true);
        int statusBarHeight = StatusbarHelper.getStatusBarHeight();
        int screenWidth = ScreenUtility.getScreenWidth(this);
        this.mLayout.setSystemUiVisibility(4);
        this.mErrorTipTv.setText(errMsg);
        this.mErrorTipTv.startAnim();
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
                RouteUtils.startAPPMainActivitry();
                finish();
                return;
            default:
                return;
        }
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

    private void wxLogin() {
        if (WxQqAPI.getIwxapi(this).isWXAppInstalled()) {
            new HealthySharedUtil(this).setWxLoginOrBind(1);
            Req req = new Req();
            req.scope = "snsapi_userinfo";
            req.state = "com.kunekt.healthy.zeroner";
            WxQqAPI.getIwxapi(this).sendReq(req);
            return;
        }
        setClickState(true);
        raiseErrorNotice("请先安装微信");
    }

    private void qqLogin() {
        WxQqAPI.getTencent(this).login((Activity) this, "all", this.loginListener);
    }

    public void loginOk(int type) {
        if (type == 3) {
            long reTime = GlobalUserDataFetcher.getRegisterTime(this);
            if (reTime <= 100 || reTime >= 1543161600000L) {
                RouteUtils.startAPPMainActivitry();
                return;
            }
            TB_data_import dataImport = (TB_data_import) DataSupport.where("uid=?", GlobalUserDataFetcher.getCurrentUid(this).longValue() + "").findFirst(TB_data_import.class);
            if (dataImport == null || dataImport.getCode() == 0) {
                RouteUtils.startDataImportActivity();
                return;
            }
            KLog.d("no2855,跳转主页面--->");
            RouteUtils.startAPPMainActivitry();
            return;
        }
        Intent intent = new Intent(this, NewProfileGenderActivity.class);
        intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
        startActivity(intent);
    }

    public void netError(int type, int errorCode) {
        dismissLoadDialog();
        setClickState(true);
        if (type == 1) {
            if (errorCode == 50012) {
                raiseErrorNotice(R.string.no_user_error);
                return;
            } else if (errorCode == 50003) {
                raiseErrorNotice(R.string.message_login_error);
                return;
            }
        }
        raiseErrorNotice(getString(R.string.unkown_error, new Object[]{type + HelpFormatter.DEFAULT_OPT_PREFIX + errorCode}));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, this.loginListener);
        if (requestCode == 10100 && resultCode == 0) {
            Tencent.handleResultData(data, this.loginListener);
        }
    }

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString("openid");
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                WxQqAPI.getTencent(this).setAccessToken(token, expires);
                WxQqAPI.getTencent(this).setOpenId(openId);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void setClickState(boolean state) {
        if (state) {
            if (this.loginWx != null && this.loginQQ != null && this.mBtnSignin != null) {
                this.loginWx.setClickable(true);
                this.loginWx.setFocusable(true);
                this.loginQQ.setClickable(true);
                this.loginQQ.setFocusable(false);
                this.mBtnSignin.setClickable(true);
                this.mBtnSignin.setFocusable(true);
            }
        } else if (this.loginWx != null && this.loginQQ != null && this.mBtnSignin != null) {
            this.loginWx.setClickable(false);
            this.loginWx.setFocusable(false);
            this.loginQQ.setClickable(false);
            this.loginQQ.setFocusable(false);
            this.mBtnSignin.setClickable(false);
            this.mBtnSignin.setFocusable(false);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        setClickState(true);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            setClickState(true);
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
