package com.iwown.device_module.device_camera.camera;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import com.iwown.device_module.device_camera.camera.CameraHost.RecordingHint;

public class SimpleDeviceProfile extends DeviceProfile {
    private int defaultOrientation;
    private boolean doesZoomActuallyWork;
    private int maxPictureHeight;
    private int minPictureHeight;
    private int pictureDelay;
    private boolean portraitFFCFlipped;
    private RecordingHint recordingHint;
    private boolean useDeviceOrientation;
    private boolean useTextureView;

    static class MotorolaRazrI extends SimpleDeviceProfile {
        MotorolaRazrI() {
        }

        public boolean doesZoomActuallyWork(boolean isFFC) {
            return !isFFC;
        }
    }

    public SimpleDeviceProfile() {
        this.useTextureView = VERSION.SDK_INT >= 16 && !isCyanogenMod();
        this.portraitFFCFlipped = false;
        this.minPictureHeight = 0;
        this.maxPictureHeight = Integer.MAX_VALUE;
        this.doesZoomActuallyWork = true;
        this.defaultOrientation = -1;
        this.useDeviceOrientation = false;
        this.pictureDelay = 0;
        this.recordingHint = RecordingHint.NONE;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0057, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0010, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r10.next();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.iwown.device_module.device_camera.camera.SimpleDeviceProfile load(org.xmlpull.v1.XmlPullParser r10) {
        /*
            r9 = this;
            r8 = 1
            r0 = 0
            r1 = r0
        L_0x0003:
            int r3 = r10.getEventType()     // Catch:{ Exception -> 0x0039 }
            if (r3 == r8) goto L_0x0055
            int r3 = r10.getEventType()     // Catch:{ Exception -> 0x0039 }
            switch(r3) {
                case 2: goto L_0x0016;
                case 3: goto L_0x0027;
                case 4: goto L_0x001c;
                default: goto L_0x0010;
            }
        L_0x0010:
            r0 = r1
        L_0x0011:
            r10.next()     // Catch:{ Exception -> 0x0057 }
            r1 = r0
            goto L_0x0003
        L_0x0016:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r0.<init>()     // Catch:{ Exception -> 0x0039 }
            goto L_0x0011
        L_0x001c:
            if (r1 == 0) goto L_0x0010
            java.lang.String r3 = r10.getText()     // Catch:{ Exception -> 0x0039 }
            r1.append(r3)     // Catch:{ Exception -> 0x0039 }
            r0 = r1
            goto L_0x0011
        L_0x0027:
            if (r1 == 0) goto L_0x0010
            java.lang.String r3 = r10.getName()     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r1.toString()     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.trim()     // Catch:{ Exception -> 0x0039 }
            r9.set(r3, r4)     // Catch:{ Exception -> 0x0039 }
            goto L_0x0010
        L_0x0039:
            r2 = move-exception
            r0 = r1
        L_0x003b:
            java.lang.String r3 = "CWAC-Camera"
            java.lang.String r4 = "Exception parsing device profile for %s %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r6 = 0
            java.lang.String r7 = android.os.Build.MANUFACTURER
            r5[r6] = r7
            java.lang.String r6 = android.os.Build.MODEL
            r5[r8] = r6
            java.lang.String r4 = java.lang.String.format(r4, r5)
            android.util.Log.e(r3, r4, r2)
        L_0x0054:
            return r9
        L_0x0055:
            r0 = r1
            goto L_0x0054
        L_0x0057:
            r2 = move-exception
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_camera.camera.SimpleDeviceProfile.load(org.xmlpull.v1.XmlPullParser):com.iwown.device_module.device_camera.camera.SimpleDeviceProfile");
    }

    @SuppressLint({"DefaultLocale"})
    private void set(String name, String value) {
        if ("useTextureView".equals(name)) {
            this.useTextureView = Boolean.parseBoolean(value);
        } else if ("portraitFFCFlipped".equals(name)) {
            this.portraitFFCFlipped = Boolean.parseBoolean(value);
        } else if ("doesZoomActuallyWork".equals(name)) {
            this.doesZoomActuallyWork = Boolean.parseBoolean(value);
        } else if ("useDeviceOrientation".equals(name)) {
            this.useDeviceOrientation = Boolean.parseBoolean(value);
        } else if ("minPictureHeight".equals(name)) {
            this.minPictureHeight = Integer.parseInt(value);
        } else if ("maxPictureHeight".equals(name)) {
            this.maxPictureHeight = Integer.parseInt(value);
        } else if ("pictureDelay".equals(name)) {
            this.pictureDelay = Integer.parseInt(value);
        } else if ("recordingHint".equals(name)) {
            String hint = value.toUpperCase();
            if ("ANY".equals(hint)) {
                this.recordingHint = RecordingHint.ANY;
            } else if ("STILL_ONLY".equals(hint)) {
                this.recordingHint = RecordingHint.STILL_ONLY;
            } else if ("VIDEO_ONLY".equals(hint)) {
                this.recordingHint = RecordingHint.VIDEO_ONLY;
            }
        }
    }

    public boolean useTextureView() {
        return this.useTextureView;
    }

    public boolean portraitFFCFlipped() {
        return this.portraitFFCFlipped;
    }

    public int getMinPictureHeight() {
        return this.minPictureHeight;
    }

    public int getMaxPictureHeight() {
        return this.maxPictureHeight;
    }

    public boolean doesZoomActuallyWork(boolean isFFC) {
        return this.doesZoomActuallyWork;
    }

    public int getDefaultOrientation() {
        return this.defaultOrientation;
    }

    public boolean useDeviceOrientation() {
        return this.useDeviceOrientation;
    }

    public int getPictureDelay() {
        return this.pictureDelay;
    }

    public RecordingHint getDefaultRecordingHint() {
        return this.recordingHint;
    }

    private boolean isCyanogenMod() {
        return System.getProperty("os.version").contains("cyanogenmod") || Build.HOST.contains("cyanogenmod");
    }
}
