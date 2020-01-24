package com.iwown.device_module.device_camera.camera;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import com.iwown.device_module.device_camera.camera.CameraHost.RecordingHint;
import java.util.Locale;

public abstract class DeviceProfile {
    private static volatile DeviceProfile SINGLETON = null;

    public abstract boolean doesZoomActuallyWork(boolean z);

    public abstract int getDefaultOrientation();

    public abstract RecordingHint getDefaultRecordingHint();

    public abstract int getMaxPictureHeight();

    public abstract int getMinPictureHeight();

    public abstract int getPictureDelay();

    public abstract boolean portraitFFCFlipped();

    public abstract boolean useDeviceOrientation();

    public abstract boolean useTextureView();

    public static synchronized DeviceProfile getInstance(Context ctxt) {
        DeviceProfile deviceProfile;
        synchronized (DeviceProfile.class) {
            if (SINGLETON == null) {
                if (!"motorola".equalsIgnoreCase(Build.MANUFACTURER) || !"XT890_rtgb".equals(Build.PRODUCT)) {
                    int resource = findResource(ctxt);
                    if (resource != 0) {
                        SINGLETON = new SimpleDeviceProfile().load(ctxt.getResources().getXml(resource));
                    } else {
                        SINGLETON = new SimpleDeviceProfile();
                    }
                } else {
                    SINGLETON = new MotorolaRazrI();
                }
            }
            deviceProfile = SINGLETON;
        }
        return deviceProfile;
    }

    private static int findResource(Context ctxt) {
        Resources res = ctxt.getResources();
        StringBuilder buf = new StringBuilder("cwac_camera_profile_");
        buf.append(clean(Build.MANUFACTURER));
        int mfrResult = res.getIdentifier(buf.toString(), "xml", ctxt.getPackageName());
        buf.append("_");
        buf.append(clean(Build.PRODUCT));
        int result = res.getIdentifier(buf.toString(), "xml", ctxt.getPackageName());
        return result == 0 ? mfrResult : result;
    }

    private static String clean(String input) {
        return input.replaceAll("[\\W]", "_").toLowerCase(Locale.US);
    }

    private boolean isCyanogenMod() {
        return System.getProperty("os.version").contains("cyanogenmod") || Build.HOST.contains("cyanogenmod");
    }
}
