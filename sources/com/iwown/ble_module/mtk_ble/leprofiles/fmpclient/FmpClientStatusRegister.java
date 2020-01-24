package com.iwown.ble_module.mtk_ble.leprofiles.fmpclient;

import java.util.ArrayList;
import java.util.Iterator;

public class FmpClientStatusRegister {
    public static final int FIND_ME_STATUS_DISABLED = 0;
    public static final int FIND_ME_STATUS_NORMAL = 1;
    public static final int FIND_ME_STATUS_USING = 2;
    private static FmpClientStatusRegister sInstance = null;
    private int mFindMeStatus = 0;
    private ArrayList<FmpClientStatusChangeListener> mFmStatusChangeListeners = new ArrayList<>();

    public static FmpClientStatusRegister getInstance() {
        if (sInstance == null) {
            sInstance = new FmpClientStatusRegister();
        }
        return sInstance;
    }

    private FmpClientStatusRegister() {
    }

    public void registerFmListener(FmpClientStatusChangeListener listener) {
        if (!this.mFmStatusChangeListeners.contains(listener)) {
            this.mFmStatusChangeListeners.add(listener);
        }
    }

    public void unregisterFmListener(FmpClientStatusChangeListener listener) {
        this.mFmStatusChangeListeners.remove(listener);
    }

    public void setFindMeStatus(int status) {
        this.mFindMeStatus = status;
        notifyFmChange();
    }

    public int getFindMeStatus() {
        return this.mFindMeStatus;
    }

    private void notifyFmChange() {
        Iterator it = this.mFmStatusChangeListeners.iterator();
        while (it.hasNext()) {
            ((FmpClientStatusChangeListener) it.next()).onStatusChange();
        }
    }
}
