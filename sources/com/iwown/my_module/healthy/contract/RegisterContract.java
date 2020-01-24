package com.iwown.my_module.healthy.contract;

public class RegisterContract {

    public interface RegisterPresenter {
        void onTaskEnded(int i, int i2);
    }

    public interface RegisterView {
        void hideProgress();

        void navigateToNext();

        void registerError(int i);

        void sendMessage(boolean z, String str);

        void showProgress();
    }
}
