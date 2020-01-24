package coms.mediatek.wearable;

import android.util.Log;

class k {
    static void a(String str, String str2) {
        if (WearableConfig.f()) {
            Log.d(str, str2);
        }
    }

    static void b(String str, String str2) {
        if (WearableConfig.g()) {
            Log.d(str, str2);
        }
    }

    static void c(String str, String str2) {
        if (WearableConfig.f()) {
            Log.e(str, str2);
        }
    }
}
