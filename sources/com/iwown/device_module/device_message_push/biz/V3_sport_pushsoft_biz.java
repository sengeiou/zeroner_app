package com.iwown.device_module.device_message_push.biz;

import android.os.Process;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.sql.TB_PUSH_SOFT;
import com.socks.library.KLog;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class V3_sport_pushsoft_biz {
    private String TAG = getClass().getSimpleName();

    private void saveAndDelMessage(String uid, String message, int msgId, String packageName, long time) {
        KLog.e("saveAndDelMessage", Long.valueOf(time));
        KLog.e("saveAndDelMessage", String.valueOf(time - 3600));
        TB_PUSH_SOFT entity = new TB_PUSH_SOFT();
        entity.setUid(uid);
        entity.setMessage(message.trim());
        entity.setAppName(packageName + ".com");
        entity.setMsgid(msgId);
        entity.setTimes(time);
        entity.save();
        DataSupport.deleteAll(TB_PUSH_SOFT.class, "uid=? and times<?", String.valueOf(uid), String.valueOf(time - 3600));
        KLog.e("licl", "走了这里");
    }

    public synchronized int queryMessagePush(String uid, String message, int mesgId, String packageName) {
        int i;
        try {
            long time = System.currentTimeMillis() / 1000;
            KLog.e("time", Long.valueOf(time));
            long oldTime = time - 3600;
            KLog.e("queryMessagePush--", Process.myPid() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + uid + HelpFormatter.DEFAULT_OPT_PREFIX + message + HelpFormatter.DEFAULT_OPT_PREFIX + packageName + HelpFormatter.DEFAULT_OPT_PREFIX + oldTime + HelpFormatter.DEFAULT_OPT_PREFIX + mesgId);
            List<TB_PUSH_SOFT> newsList = DataSupport.where("uid=? and (msgid=? or appName=?) and message=? and times>?", uid, String.valueOf(mesgId), packageName + ".com", message, String.valueOf(oldTime)).find(TB_PUSH_SOFT.class);
            if (newsList.size() > 0) {
                KLog.e("NotificationBiz", "数据库最新的内容-->: " + ((TB_PUSH_SOFT) newsList.get(0)).getMessage() + "<->ID: " + ((TB_PUSH_SOFT) newsList.get(0)).getMsgid() + "<-内容->: " + message + "<===" + newsList.size());
                if (((TB_PUSH_SOFT) newsList.get(0)).getMessage().trim().equals(message.trim())) {
                    KLog.e(this.TAG, "firstMst与message相等");
                    i = 1;
                } else {
                    saveAndDelMessage(uid, message, mesgId, packageName, time);
                    i = 0;
                }
            } else {
                saveAndDelMessage(uid, message, mesgId, packageName, time);
                i = 0;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            i = 0;
        }
        return i;
    }

    public int deleteDatabyDate(String uid, int hour) {
        boolean z = false;
        try {
            return DataSupport.deleteAll(TB_PUSH_SOFT.class, "uid=? and hour!= ?", uid + "", hour + "");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }
}
