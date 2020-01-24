package com.iwown.device_module.device_setting.configure;

import com.iwown.ble_module.zg_ble.data.model.WelcomeBloodData;
import com.iwown.ble_module.zg_ble.data.model.WelcomeBloodData.BloodPressure;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.data_link.data.ZgWelcomeBlood;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import org.litepal.crud.DataSupport;

public class BloodUtil {
    public static void upBloodTb(int[] blood) {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        ZG_BaseInfo baseInfo = (ZG_BaseInfo) DataSupport.where("uid=? and key=?", uid + "", ZG_BaseInfo.key_welcome_blood).findFirst(ZG_BaseInfo.class);
        if (baseInfo == null) {
            WelcomeBloodData bloodData = new WelcomeBloodData();
            UserInfo user = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
            int male = 0;
            if (!user.isMale) {
                male = 1;
            }
            bloodData.setHeight((int) user.height);
            bloodData.setGender(male);
            bloodData.setTimeZone(DeviceUtils.getTimeZoneInt());
            bloodData.setWelcome("there");
            bloodData.setAllBloodPressure(blood);
            ZG_BaseInfo baseInfo2 = new ZG_BaseInfo();
            baseInfo2.setKey(ZG_BaseInfo.key_welcome_blood);
            baseInfo2.setUid(uid + "");
            baseInfo2.setContent(JsonTool.toJson(bloodData));
            baseInfo2.save();
            return;
        }
        WelcomeBloodData bloodData2 = (WelcomeBloodData) JsonTool.fromJson(baseInfo.getContent(), WelcomeBloodData.class);
        bloodData2.setAllBloodPressure(blood);
        baseInfo.setContent(JsonTool.toJson(bloodData2));
        baseInfo.update(baseInfo.getId());
    }

    public static void saveBlood(int[] oldbp) {
        if (oldbp != null && oldbp.length >= 6) {
            int avg_sbp = (oldbp[0] + oldbp[2]) / 2;
            int avg_dbp = (oldbp[1] + oldbp[3]) / 2;
            upBloodTb(new int[]{ByteUtil.loword(oldbp[4]), ByteUtil.hiword(oldbp[4]), ByteUtil.loword(oldbp[5]), ByteUtil.hiword(oldbp[5]), ByteUtil.loword(avg_sbp), ByteUtil.hiword(avg_sbp), ByteUtil.loword(avg_dbp), ByteUtil.hiword(avg_dbp)});
        }
    }

    private static ZG_BaseInfo saveWelcome(String welcome) {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        ZG_BaseInfo zg_baseInfo = getTBWelcomeBlood(uid);
        if (zg_baseInfo == null) {
            WelcomeBloodData welcomeBloodData = new WelcomeBloodData();
            welcomeBloodData.setBlood(new BloodPressure());
            welcomeBloodData.setUserTimeZone(getHeightTimeZone());
            welcomeBloodData.setWelcome(welcome);
            ZG_BaseInfo zg_baseInfo2 = new ZG_BaseInfo();
            zg_baseInfo2.setContent(JsonTool.toJson(welcomeBloodData));
            zg_baseInfo2.setUid(String.valueOf(uid));
            zg_baseInfo2.setKey(ZG_BaseInfo.key_welcome_blood);
            zg_baseInfo2.save();
            return zg_baseInfo2;
        }
        WelcomeBloodData welcomeBloodData2 = (WelcomeBloodData) JsonTool.fromJson(zg_baseInfo.getContent(), WelcomeBloodData.class);
        if (welcomeBloodData2 != null) {
            welcomeBloodData2.setWelcome(welcome);
        } else {
            welcomeBloodData2 = new WelcomeBloodData();
            welcomeBloodData2.setBlood(new BloodPressure());
            welcomeBloodData2.setUserTimeZone(getHeightTimeZone());
            welcomeBloodData2.setWelcome(welcome);
        }
        zg_baseInfo.setContent(JsonTool.toJson(welcomeBloodData2));
        updateTBWelcome(uid, zg_baseInfo);
        return zg_baseInfo;
    }

    public static ZgWelcomeBlood getWelBloodBle(String welcome) {
        ZG_BaseInfo zg_baseInfo = saveWelcome(welcome);
        if (zg_baseInfo != null) {
            return getZgWelcomeBlood((WelcomeBloodData) JsonTool.fromJson(zg_baseInfo.getContent(), WelcomeBloodData.class));
        }
        ZgWelcomeBlood zgWelcomeBlood = new ZgWelcomeBlood();
        zgWelcomeBlood.setBlood(new int[8]);
        return zgWelcomeBlood;
    }

    public static ZgWelcomeBlood getWelBloodBle(int[] oldBd) {
        saveBlood(oldBd);
        return getWelBloodBle();
    }

    public static ZgWelcomeBlood getWelBloodBle() {
        ZG_BaseInfo zg_baseInfo = getTBWelcomeBlood(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
        if (zg_baseInfo != null) {
            return getZgWelcomeBlood((WelcomeBloodData) JsonTool.fromJson(zg_baseInfo.getContent(), WelcomeBloodData.class));
        }
        ZgWelcomeBlood zgWelcomeBlood = new ZgWelcomeBlood();
        zgWelcomeBlood.setBlood(new int[8]);
        return zgWelcomeBlood;
    }

    private static ZgWelcomeBlood getZgWelcomeBlood(WelcomeBloodData welcomeBloodData) {
        boolean z;
        ZgWelcomeBlood welcomeBlood = new ZgWelcomeBlood();
        if (welcomeBloodData != null) {
            welcomeBlood.setGender(welcomeBloodData.getGender());
            welcomeBlood.setHeight(welcomeBloodData.getHeight());
            if (welcomeBloodData.getOld8D() == 1) {
                z = true;
            } else {
                z = false;
            }
            welcomeBlood.setOld(z);
            welcomeBlood.setWelcome(welcomeBloodData.getWelcome());
            int[] blood = new int[8];
            if (welcomeBloodData.getBlood() != null) {
                blood[0] = welcomeBloodData.getBlood().getSrcSbp_LB();
                blood[1] = welcomeBloodData.getBlood().getSrcSbp_HB();
                blood[2] = welcomeBloodData.getBlood().getSrcDbp_LB();
                blood[3] = welcomeBloodData.getBlood().getSrcDbp_HB();
                blood[4] = welcomeBloodData.getBlood().getDstSbp_LB();
                blood[5] = welcomeBloodData.getBlood().getDstSbp_HB();
                blood[6] = welcomeBloodData.getBlood().getDstDbp_LB();
                blood[7] = welcomeBloodData.getBlood().getDstDbp_HB();
            }
            welcomeBlood.setBlood(blood);
        }
        return welcomeBlood;
    }

    private static void updateTBWelcome(long uid, ZG_BaseInfo baseInfo) {
        baseInfo.updateAll("uid=? and key=?", uid + "", ZG_BaseInfo.key_welcome_blood);
    }

    public static ZG_BaseInfo getTBWelcomeBlood(long uid) {
        return (ZG_BaseInfo) DataSupport.where("uid=? and key=?", uid + "", ZG_BaseInfo.key_welcome_blood).findFirst(ZG_BaseInfo.class);
    }

    public static boolean isSend0D(long uid, String data8D) {
        if (data8D == null) {
            data8D = "";
        }
        ZG_BaseInfo baseInfo = (ZG_BaseInfo) DataSupport.where("uid=? and key=?", uid + "", ZG_BaseInfo.key_welcome_blood).findFirst(ZG_BaseInfo.class);
        WelcomeBloodData bloodData = (WelcomeBloodData) JsonTool.fromJson(data8D, WelcomeBloodData.class);
        if (bloodData == null) {
            bloodData = new WelcomeBloodData();
            L.file("欢迎页不应该进这里的", 3);
            bloodData.setWelcome("there");
            bloodData.setBlood(new BloodPressure());
        }
        if (baseInfo == null) {
            WelcomeBloodData bloodData1 = new WelcomeBloodData();
            bloodData1.setBlood(bloodData.getBlood());
            bloodData1.setWelcome(bloodData.getWelcome());
            int[] userTime = getHeightTimeZone();
            bloodData1.setGender(userTime[0]);
            bloodData1.setHeight(userTime[1]);
            bloodData1.setTimeZone(userTime[2]);
            bloodData1.setOld8D(bloodData.getOld8D());
            ZG_BaseInfo baseInfo2 = new ZG_BaseInfo();
            baseInfo2.setUid(uid + "");
            baseInfo2.setContent(JsonTool.toJson(bloodData1));
            baseInfo2.setKey(ZG_BaseInfo.key_welcome_blood);
            baseInfo2.save();
            return true;
        }
        WelcomeBloodData bloodData12 = (WelcomeBloodData) JsonTool.fromJson(baseInfo.getContent(), WelcomeBloodData.class);
        if (data8D.equals(baseInfo.getContent()) && bloodData.getTimeZone() == DeviceUtils.getTimeZoneInt()) {
            return false;
        }
        if (bloodData12 == null) {
            bloodData12 = new WelcomeBloodData();
            bloodData12.setUserTimeZone(getHeightTimeZone());
            bloodData12.setWelcome("there");
            bloodData12.setBlood(new BloodPressure());
        }
        if (bloodData12.getBlood() == null) {
            bloodData12.setBlood(new BloodPressure());
        }
        if (bloodData12.getBlood().getDstSbp_LB() != 0) {
            return true;
        }
        bloodData12.setBlood(bloodData.getBlood());
        baseInfo.setContent(JsonTool.toJson(bloodData));
        baseInfo.updateAll("uid=? and key=?", uid + "", ZG_BaseInfo.key_welcome_blood);
        return true;
    }

    public static int[] getHeightTimeZone() {
        int[] data = new int[3];
        UserInfo user = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
        int male = 0;
        if (!user.isMale) {
            male = 1;
        }
        data[0] = male;
        data[1] = (int) user.height;
        data[2] = DeviceUtils.getTimeZoneInt();
        return data;
    }
}
