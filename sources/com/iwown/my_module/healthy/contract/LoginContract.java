package com.iwown.my_module.healthy.contract;

public class LoginContract {
    public static final int go_goal = 2;
    public static final int go_main = 3;
    public static final int go_user = 1;
    public static final int login_error = 1;
    public static final int qq_user_error = 3;
    public static final int user_error = 2;

    public interface LoginPresenter {
        void getPhoneUid(String str, String str2);

        void getQqOrWxUid(int i, String str, String str2);

        void getUserInfo(long j);
    }

    public interface LoginView {
        void loginOk(int i);

        void netError(int i, int i2);
    }
}
