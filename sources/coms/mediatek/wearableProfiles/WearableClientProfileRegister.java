package coms.mediatek.wearableProfiles;

import android.os.Looper;

public class WearableClientProfileRegister {
    public static final void registerWearableClientProfile(WearableClientProfile wearableClientProfile, Looper looper) {
        WearableClientProfileManager.getWearableClientProfileManager().registerWearableClientProfile(wearableClientProfile, looper);
    }

    public static final void unRegisterWearableClientProfile(WearableClientProfile wearableClientProfile) {
        WearableClientProfileManager.getWearableClientProfileManager().unRegisterWearableClientProfile(wearableClientProfile);
    }
}
