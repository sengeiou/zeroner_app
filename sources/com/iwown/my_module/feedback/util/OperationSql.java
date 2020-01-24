package com.iwown.my_module.feedback.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.my_module.feedback.data.TB_feedback;
import com.iwown.my_module.feedback.network.request.UserSend;
import java.util.List;
import org.litepal.crud.DataSupport;

public class OperationSql {
    public TB_feedback getNewTalkSql(String msg, int msgType, Long chatRecordId) {
        TB_feedback tbFeedback = new TB_feedback();
        tbFeedback.setUid(UserConfig.getInstance().getNewUID());
        long times = System.currentTimeMillis();
        tbFeedback.setDate(times);
        tbFeedback.setMessage(msg);
        tbFeedback.setMsg_type(msgType);
        if (chatRecordId == null) {
            tbFeedback.setRecord_id(0);
        } else {
            tbFeedback.setRecord_id(chatRecordId.longValue());
        }
        if (msgType == 5) {
            tbFeedback.setUrl(msg);
        }
        if (!tbFeedback.save()) {
            return null;
        }
        return (TB_feedback) DataSupport.where("uid=? and date=?", UserConfig.getInstance().getNewUID() + "", times + "").findLast(TB_feedback.class);
    }

    public List<TB_feedback> getHistory() {
        return DataSupport.where("uid=?", UserConfig.getInstance().getNewUID() + "").order("date asc").find(TB_feedback.class);
    }

    public void updateTbFeedback(int id, int talkType) {
        ContentValues values = new ContentValues();
        values.put("msg_type", Integer.valueOf(talkType));
        DataSupport.update(TB_feedback.class, values, (long) id);
    }

    public void deleteTbFeedback() {
        TB_feedback sd = (TB_feedback) DataSupport.findLast(TB_feedback.class);
        if (sd != null) {
            DataSupport.deleteAll(TB_feedback.class, "uid=? and id<?", UserConfig.getInstance().getNewUID() + "", (sd.getId() - 30) + "");
        }
    }

    public UserSend getUserSend(Context context) throws NameNotFoundException {
        UserSend send = new UserSend();
        PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        String model = Build.MODEL;
        String models = Build.BRAND;
        String release = VERSION.RELEASE;
        String system = VERSION.BASE_OS;
        send.setUid(String.valueOf(UserConfig.getInstance().getNewUID()));
        send.setApp(pi.packageName);
        send.setAppVersion(pi.versionName);
        send.setPhone(model + "/" + models);
        send.setPhoneVersion(release);
        send.setPhoneSystem(system);
        String deviceModel = ModuleRouteDeviceInfoService.getInstance().getDevicemodel();
        String deviceVersion = ModuleRouteDeviceInfoService.getInstance().getDeviceVersion();
        send.setDevice(deviceModel);
        send.setDeviceVersion(deviceVersion);
        send.setCountry(UserConfig.getInstance().getCountry());
        send.setCity(UserConfig.getInstance().getLocality());
        return send;
    }
}
