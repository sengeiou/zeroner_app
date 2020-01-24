package com.iwown.data_link.user_pre;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

public class ModuleRouteUserInfoService {
    @Autowired
    IUserInfoService mUserInfoService;

    static class ModuleRouteUserInfoServiceHolder {
        static ModuleRouteUserInfoService moduleRouteUserInfoService = new ModuleRouteUserInfoService();

        ModuleRouteUserInfoServiceHolder() {
        }
    }

    private ModuleRouteUserInfoService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteUserInfoService getInstance() {
        return ModuleRouteUserInfoServiceHolder.moduleRouteUserInfoService;
    }

    public UserInfo getUserInfo(Context context) {
        if (this.mUserInfoService != null) {
            return this.mUserInfoService.getUserInfo(context);
        }
        return new UserInfo();
    }

    public String getRegisterDate(Context context) {
        if (this.mUserInfoService != null) {
            return this.mUserInfoService.registerDate(context);
        }
        return "1970-01-01";
    }

    public void uploadWxQQStep(Context context) {
        if (this.mUserInfoService != null) {
            this.mUserInfoService.uploadWxQQStep(context);
        }
    }

    public void uploadQQSleep(Context context) {
        if (this.mUserInfoService != null) {
            this.mUserInfoService.uploadQQSleep(context);
        }
    }

    public void changeTBImport(long uid) {
        if (this.mUserInfoService != null) {
            this.mUserInfoService.changeTBImport(uid);
        }
    }
}
