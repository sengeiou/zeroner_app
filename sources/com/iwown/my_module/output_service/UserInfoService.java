package com.iwown.my_module.output_service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.user_pre.IUserInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.my_module.data.HealthGoalEntity;
import com.iwown.my_module.data.TB_data_import;
import com.iwown.my_module.data.UserInfoEntity;
import com.kunekt.healthy.wxapi.WxQqUpload;
import org.litepal.crud.DataSupport;

@Route(path = "/user/info_service")
public class UserInfoService implements IUserInfoService {
    private String TAG = getClass().getSimpleName();

    public UserInfo getUserInfo(Context context) {
        UserInfo userInfo = new UserInfo();
        userInfo.uid = GlobalUserDataFetcher.getCurrentUid(context).longValue();
        HealthGoalEntity entity = (HealthGoalEntity) DataSupport.where("uid=?", String.valueOf(userInfo.uid)).findFirst(HealthGoalEntity.class);
        if (entity != null) {
            userInfo.target_step = entity.getTarget_step();
            userInfo.target_weight = entity.getTarget_weight();
        } else {
            Log.e(this.TAG, "HealthGoalEntity没查到目标信息-—>" + userInfo.uid);
        }
        userInfo.isMertric = GlobalUserDataFetcher.getUnitType(context) != 0;
        UserInfoEntity userInfoEntity = (UserInfoEntity) DataSupport.where("uid=?", String.valueOf(userInfo.uid)).findFirst(UserInfoEntity.class);
        if (userInfoEntity != null) {
            if (userInfoEntity.getGender() == 1 || userInfoEntity.getGender() == 0) {
                userInfo.isMale = true;
            } else {
                userInfo.isMale = false;
            }
            if (userInfo.isMale) {
                if (userInfoEntity.getWeight() == 0.0f) {
                    userInfo.weight = 70.0d;
                } else {
                    userInfo.weight = (double) userInfoEntity.getWeight();
                }
                if (userInfoEntity.getHeight() == 0.0f) {
                    userInfo.height = 175.0d;
                } else {
                    userInfo.height = (double) userInfoEntity.getHeight();
                }
            } else {
                if (userInfoEntity.getWeight() == 0.0f) {
                    userInfo.weight = 50.0d;
                } else {
                    userInfo.weight = (double) userInfoEntity.getWeight();
                }
                if (userInfoEntity.getHeight() == 0.0f) {
                    userInfo.height = 165.0d;
                } else {
                    userInfo.height = (double) userInfoEntity.getHeight();
                }
            }
            userInfo.setBirthday(userInfoEntity.getBirthday());
            userInfo.getGoalCaloria();
            if (TextUtils.isEmpty(userInfoEntity.getNickname())) {
                userInfo.nickName = GlobalUserDataFetcher.getEmail(context);
            } else {
                userInfo.nickName = userInfoEntity.getNickname();
            }
        } else {
            userInfo.isMale = true;
            userInfo.weight = 70.0d;
            userInfo.height = 175.0d;
            userInfo.age = 25;
            Log.e(this.TAG, "UserInfoEntity没查到个人信息-—>" + userInfo.uid);
        }
        userInfo.isCentigrade = GlobalUserDataFetcher.getTemperatureUnit(context) == 0;
        return userInfo;
    }

    public void uploadWxQQStep(Context context) {
        WxQqUpload.uploadStepWX(context, true, false);
        WxQqUpload.uploadStepQQ(context, true, false);
    }

    public void uploadQQSleep(Context context) {
        WxQqUpload.responseSleepTime(context);
    }

    public void changeTBImport(long uid) {
        TB_data_import dataImport = (TB_data_import) DataSupport.where("uid=?", uid + "").findFirst(TB_data_import.class);
        if (dataImport != null) {
            dataImport.setCode(1);
            dataImport.updateAll("uid=?", uid + "");
            return;
        }
        TB_data_import dataImport2 = new TB_data_import();
        dataImport2.setUid(uid);
        dataImport2.setCode(1);
        dataImport2.save();
    }

    public String registerDate(Context context) {
        return GlobalUserDataFetcher.getRegisterDate(context);
    }

    public void init(Context context) {
    }
}
