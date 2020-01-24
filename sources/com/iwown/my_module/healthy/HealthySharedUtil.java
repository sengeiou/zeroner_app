package com.iwown.my_module.healthy;

import android.content.Context;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.consts.UserConst.HealthyConst;
import com.iwown.data_link.utils.HealthyShareUtility;
import com.iwown.data_link.utils.PreferenceUtility;

public class HealthySharedUtil {
    private Context mContext;
    private HealthyShareUtility prefUtil;

    public HealthySharedUtil(Context context) {
        this.mContext = context;
        this.prefUtil = new HealthyShareUtility(context);
    }

    public void setUid(long uid) {
        new PreferenceUtility(this.mContext).updateLongValueWithKey(UserConst.UID, uid);
    }

    public long getUid() {
        return new PreferenceUtility(this.mContext).fetchLongValueWithKey(UserConst.UID);
    }

    public void setLoginType(int type) {
        if (this.prefUtil != null) {
            this.prefUtil.updateNumberValueWithKey(HealthyConst.LOGIN_TYPE, type);
        }
    }

    public int getLoginType() {
        if (this.prefUtil != null) {
            return this.prefUtil.fetchNumberValueWithKey(HealthyConst.LOGIN_TYPE);
        }
        return 1;
    }

    public String getPhotoUrl() {
        return this.prefUtil != null ? this.prefUtil.fetchStrValueWithKey(HealthyConst.PHOTO_URL) : "";
    }

    public void setPhotoUrl(String photoUrl) {
        if (this.prefUtil != null) {
            this.prefUtil.updateStrValueWithKey(HealthyConst.PHOTO_URL, photoUrl);
        }
    }

    public boolean isUserExist() {
        if (this.prefUtil != null) {
            return this.prefUtil.fetchBooleanValueWithKey(HealthyConst.USER_EXIST);
        }
        return false;
    }

    public void setUserExist(boolean userExist) {
        if (this.prefUtil != null) {
            this.prefUtil.updateBooleanValueWithKey(HealthyConst.USER_EXIST, userExist);
        }
    }

    public void setWxOpenId(String openId) {
        if (this.prefUtil != null) {
            this.prefUtil.updateStrValueWithKey(HealthyConst.WX_OPENID, openId);
        }
    }

    public String getWxOpenId() {
        return this.prefUtil != null ? this.prefUtil.fetchStrValueWithKey(HealthyConst.WX_OPENID) : "";
    }

    public void setWxLoginOrBind(int type) {
        if (this.prefUtil != null) {
            this.prefUtil.updateNumberValueWithKey(HealthyConst.WX_LOGIN_BIND, type);
        }
    }

    public int getWxLoginOrBind() {
        if (this.prefUtil != null) {
            return this.prefUtil.fetchNumberValueWithKey(HealthyConst.WX_LOGIN_BIND);
        }
        return 1;
    }

    public void setWxStepMsg(int step) {
        if (this.prefUtil != null) {
            this.prefUtil.updateNumberValueWithKey(HealthyConst.WX_STEP, step);
        }
    }

    public String getWxStepMsg() {
        if (this.prefUtil != null) {
            return this.prefUtil.fetchStrValueWithKey(HealthyConst.WX_STEP);
        }
        return null;
    }

    public String getQQOpenId() {
        return this.prefUtil != null ? this.prefUtil.fetchStrValueWithKey(HealthyConst.QQ_OPENID) : "";
    }

    public void setQQOpenId(String openId) {
        if (this.prefUtil != null) {
            this.prefUtil.updateStrValueWithKey(HealthyConst.QQ_OPENID, openId);
        }
    }

    public String getQQToken() {
        return this.prefUtil != null ? this.prefUtil.fetchStrValueWithKey(HealthyConst.QQ_TOKEN) : "";
    }

    public void setQQToken(String openId) {
        if (this.prefUtil != null) {
            this.prefUtil.updateStrValueWithKey(HealthyConst.QQ_TOKEN, openId);
        }
    }

    public void setBBsBind(int type) {
        if (this.prefUtil != null) {
            this.prefUtil.updateNumberValueWithKey(HealthyConst.BBS_BIND, type);
        }
    }

    public int getBBsBind() {
        if (this.prefUtil != null) {
            return this.prefUtil.fetchNumberValueWithKey(HealthyConst.BBS_BIND);
        }
        return 2;
    }

    public void setBBsAccount(String account) {
        if (this.prefUtil != null) {
            this.prefUtil.updateStrValueWithKey(HealthyConst.BBS_USER, account);
        }
    }

    public String getBBsAccount() {
        return this.prefUtil != null ? this.prefUtil.fetchStrValueWithKey(HealthyConst.BBS_USER) : "";
    }
}
