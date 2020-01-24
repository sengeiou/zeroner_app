package coms.mediatek.wearable.leprofiles;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.List;

public class LeServerManager {
    private static a a = null;

    public static void addLeServers(Context context, List<LeServer> list) {
        a = a.a(context);
        a.a(list);
    }

    public static void onBluetoothStatusChange(boolean z) {
        if (VERSION.SDK_INT >= 18) {
            if (z) {
                if (a != null) {
                    a.a();
                }
            } else if (a != null) {
                a.b();
            }
        }
    }
}
