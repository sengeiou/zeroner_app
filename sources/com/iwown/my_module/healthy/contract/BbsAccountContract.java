package com.iwown.my_module.healthy.contract;

import com.iwown.my_module.healthy.data.DiscuzUser;
import com.iwown.my_module.healthy.network.request.BBSAccount;

public class BbsAccountContract {

    public interface BbsAccountPresenter {
        void bindBBs(BBSAccount bBSAccount);

        void loginBBs(BBSAccount bBSAccount);

        void registerBBs(DiscuzUser discuzUser);
    }

    public interface BbsAccountView {
        void bindAccountResult(int i, String str);

        void loginResult(int i);

        void registerResult(int i);
    }
}
