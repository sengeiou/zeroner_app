package com.iwown.device_module.device_firmware_upgrade.bean;

import android.content.SharedPreferences.Editor;
import com.iwown.device_module.common.utils.ContextUtil;

public class FwUpdateInfo {
    private static final String KEY_IS_ERROR = "key_is_error";
    private static final String KEY_LAST_TIME = "key_last_time";
    private static final String KEY_PROGRESS = "key_progress";
    private static final String KEY_STATE = "key_state";
    private static final String SP_NAME = "FwUpdateInfo";
    public static final int STATE_CONNECT = 2;
    public static final int STATE_ERROR = 0;
    public static final int STATE_NORMAL = 1;
    public static final int UPDATE_TIME_OUT = 180000;
    private int mLastProgress;
    private long mLastProgressTime;
    private int state;

    private static class Single {
        /* access modifiers changed from: private */
        public static FwUpdateInfo sFwUpdateInfo = new FwUpdateInfo();

        private Single() {
        }
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }

    public static FwUpdateInfo getInstance() {
        return Single.sFwUpdateInfo;
    }

    private FwUpdateInfo() {
        this.state = 0;
        initData();
    }

    private void initData() {
    }

    public long getLastProgressTime() {
        return this.mLastProgressTime;
    }

    public void setLastProgressTime(long lastProgressTime) {
        this.mLastProgressTime = lastProgressTime;
    }

    public int getLastProgress() {
        return this.mLastProgress;
    }

    public void setLastProgress(int lastProgress) {
        this.mLastProgress = lastProgress;
    }

    public void save() {
    }

    public void clear() {
        Editor edit = ContextUtil.app.getSharedPreferences(SP_NAME, 0).edit();
        edit.clear();
        edit.apply();
        initData();
    }

    public String toString() {
        return "FwUpdateInfo{mLastProgressTime=" + this.mLastProgressTime + ", state=" + this.state + ", mLastProgress=" + this.mLastProgress + '}';
    }
}
