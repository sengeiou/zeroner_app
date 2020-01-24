package com.iwown.my_module.healthy.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.healthy.contract.RegisterContract.RegisterView;
import com.iwown.my_module.healthy.register.RegisterManager;
import com.iwown.my_module.utility.TextValidator;
import com.socks.library.KLog;

public class RegisterPresenter implements com.iwown.my_module.healthy.contract.RegisterContract.RegisterPresenter {
    public static final int ERROR_ID_EMAILORPHONENUMBER_ERROR = 2;
    public static final int ERROR_ID_LENGTH_SHORT = 3;
    public static final int ERROR_ID_PASSWORD_NULL = 4;
    public static final int ERROR_ID_USERNAME_NULL = 1;
    public static final int ERROR_ID_VERIFICATIONCODE_NULL = 5;
    public static final int TYPE_MESG = 1;
    public static final int TYPE_REGISTER = 2;
    private Context context;
    private RegisterManager manager = new RegisterManager();
    private RegisterView registerView;

    public RegisterPresenter(Context context2, RegisterView registerView2) {
        this.registerView = registerView2;
        this.context = context2;
    }

    public void onTaskEnded(int result, int type) {
        if (type == 1) {
            if (result == 50004) {
                this.registerView.sendMessage(true, this.context.getString(R.string.register_phone_exist));
                KLog.d("Reqister_activity", "手机号码已经存在");
            } else if (result == 11000) {
                this.registerView.sendMessage(false, this.context.getString(R.string.register_no_network));
            } else {
                this.registerView.sendMessage(true, null);
            }
        } else if (result == 0) {
            this.registerView.hideProgress();
            this.registerView.navigateToNext();
        } else {
            this.registerView.registerError(result);
            this.registerView.hideProgress();
        }
    }

    public void validateCredentials(String user, String password) {
        if (TextUtils.isEmpty(user)) {
            this.registerView.registerError(1);
        } else if (!TextValidator.isEmail(user) && !TextValidator.isPhoneNumber(user)) {
            this.registerView.registerError(2);
        } else if (password.length() < 6) {
            this.registerView.registerError(3);
        } else if (TextUtils.isEmpty(password)) {
            this.registerView.registerError(5);
        } else {
            this.registerView.showProgress();
        }
    }
}
