package com.iwown.my_module.useractivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.network.LoginService;
import com.iwown.my_module.model.request.ChangePasswordRequest;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.utility.CommonUtility;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePwdActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = "ChangePwdActivity";
    private boolean flag = false;
    private LoginService loginService;
    private Context mContext;
    private Retrofit mRetrofit;
    private UserService mUserService;
    private Vibrator mVibrator;
    EditText newPassword;
    EditText oldPassword;
    Button resetPassword;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_changepwd);
        this.oldPassword = (EditText) findViewById(R.id.old_password);
        this.newPassword = (EditText) findViewById(R.id.new_password);
        this.resetPassword = (Button) findViewById(R.id.reset_password);
        this.resetPassword.setOnClickListener(this);
        this.mContext = this;
        setTitleText(R.string.activity_title_resetpass);
        setLeftBackTo();
        this.newPassword.setInputType(Opcodes.ADD_INT);
        this.mVibrator = (Vibrator) getApplication().getSystemService("vibrator");
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        initView();
    }

    private void initView() {
    }

    /* access modifiers changed from: 0000 */
    public boolean checkInput() {
        String oldpwd = this.oldPassword.getText().toString();
        String newpwd = this.newPassword.getText().toString();
        if (TextUtils.isEmpty(oldpwd)) {
            raiseErrorNotice(R.string.person_oldpassword_null);
            this.oldPassword.requestFocus();
            this.mVibrator.vibrate(300);
            return false;
        } else if (TextUtils.isEmpty(newpwd)) {
            raiseErrorNotice(R.string.person_newpassword_null);
            this.newPassword.requestFocus();
            this.mVibrator.vibrate(300);
            return false;
        } else if (CommonUtility.hasChinese(newpwd)) {
            raiseErrorNotice(R.string.password_no_chinese);
            this.newPassword.requestFocus();
            this.mVibrator.vibrate(300);
            return false;
        } else if (newpwd.length() < 6) {
            raiseErrorNotice(R.string.change_password_minlength);
            this.newPassword.requestFocus();
            this.mVibrator.vibrate(300);
            return false;
        } else if (newpwd.length() <= 18) {
            return true;
        } else {
            raiseErrorNotice(R.string.change_password_maxlength);
            this.newPassword.requestFocus();
            this.mVibrator.vibrate(300);
            return false;
        }
    }

    public void onClick(View view) {
        Call<ReturnCode> call;
        if (view.getId() == R.id.reset_password && checkInput()) {
            if (this.mUserService == null) {
                this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
            }
            ChangePasswordRequest request = new ChangePasswordRequest();
            long uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
            if (uid >= 0) {
                String oldpwd = this.oldPassword.getText().toString();
                String newpwd = this.newPassword.getText().toString();
                request.setUid(uid);
                request.setOld_password(oldpwd);
                request.setNew_password(newpwd);
                AppConfigUtil.getInstance(this);
                if (AppConfigUtil.isHealthy()) {
                    if (this.loginService == null) {
                        this.loginService = (LoginService) this.mRetrofit.create(LoginService.class);
                    }
                    call = this.loginService.changePwd(request);
                } else {
                    call = this.mUserService.changePwd(request);
                }
                showLoadingDialog();
                call.enqueue(new Callback<ReturnCode>() {
                    public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                        ChangePwdActivity.this.hideLoadingDialog();
                        if (response == null || response.body() == null) {
                            ChangePwdActivity.this.raiseErrorNotice(ChangePwdActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                            return;
                        }
                        switch (((ReturnCode) response.body()).getRetCode()) {
                            case 0:
                                ChangePwdActivity.this.finish();
                                return;
                            case 10001:
                                ChangePwdActivity.this.raiseErrorNotice(R.string.sql_error);
                                return;
                            case ApiConst.PasswordNotMatch /*50003*/:
                                ChangePwdActivity.this.raiseErrorNotice(R.string.oldpwd_not_match);
                                return;
                            default:
                                ChangePwdActivity.this.raiseErrorNotice(ChangePwdActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((ReturnCode) response.body()).getRetCode())}));
                                return;
                        }
                    }

                    public void onFailure(Call<ReturnCode> call, Throwable t) {
                        ChangePwdActivity.this.hideLoadingDialog();
                        ChangePwdActivity.this.raiseErrorNotice(ChangePwdActivity.this.getString(R.string.network_error));
                    }
                });
            }
        }
    }
}
