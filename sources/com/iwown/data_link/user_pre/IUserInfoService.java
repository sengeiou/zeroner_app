package com.iwown.data_link.user_pre;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.IProvider;

public interface IUserInfoService extends IProvider {
    void changeTBImport(long j);

    UserInfo getUserInfo(Context context);

    String registerDate(Context context);

    void uploadQQSleep(Context context);

    void uploadWxQQStep(Context context);
}
