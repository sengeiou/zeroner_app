package com.iwown.device_module.device_camera.camera;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build.VERSION;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.Closeable;
import java.io.IOException;

public class ImageCleanupTask extends Thread {
    private int PHOTO_SIZE = 2000;
    private boolean applyMatrix = true;
    private int cameraId;
    private byte[] data;
    private PictureTransaction xact = null;

    ImageCleanupTask(Context ctxt, byte[] data2, int cameraId2, PictureTransaction xact2) {
        boolean z = true;
        this.data = data2;
        this.cameraId = cameraId2;
        this.xact = xact2;
        if (((float) data2.length) / ((float) calculateHeapSize(ctxt)) >= xact2.host.maxPictureCleanupHeapUsage()) {
            z = false;
        }
        this.applyMatrix = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00d7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r17 = this;
            android.hardware.Camera$CameraInfo r14 = new android.hardware.Camera$CameraInfo
            r14.<init>()
            r0 = r17
            int r2 = r0.cameraId
            android.hardware.Camera.getCameraInfo(r2, r14)
            r6 = 0
            r8 = 0
            r10 = 0
            r0 = r17
            boolean r2 = r0.applyMatrix
            if (r2 == 0) goto L_0x00aa
            int r2 = r14.facing
            r3 = 1
            if (r2 != r3) goto L_0x0049
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            com.iwown.device_module.device_camera.camera.CameraHost r2 = r2.host
            com.iwown.device_module.device_camera.camera.DeviceProfile r2 = r2.getDeviceProfile()
            boolean r2 = r2.portraitFFCFlipped()
            if (r2 == 0) goto L_0x0107
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            int r2 = r2.displayOrientation
            r3 = 90
            if (r2 == r3) goto L_0x003e
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            int r2 = r2.displayOrientation
            r3 = 270(0x10e, float:3.78E-43)
            if (r2 != r3) goto L_0x0107
        L_0x003e:
            android.graphics.Matrix r2 = new android.graphics.Matrix
            r2.<init>()
            r0 = r17
            android.graphics.Matrix r6 = r0.flip(r2)
        L_0x0049:
            r13 = 0
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact     // Catch:{ IOException -> 0x0165 }
            com.iwown.device_module.device_camera.camera.CameraHost r2 = r2.host     // Catch:{ IOException -> 0x0165 }
            com.iwown.device_module.device_camera.camera.DeviceProfile r2 = r2.getDeviceProfile()     // Catch:{ IOException -> 0x0165 }
            boolean r2 = r2.useDeviceOrientation()     // Catch:{ IOException -> 0x0165 }
            if (r2 == 0) goto L_0x011e
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact     // Catch:{ IOException -> 0x0165 }
            int r13 = r2.displayOrientation     // Catch:{ IOException -> 0x0165 }
        L_0x0060:
            if (r13 == 0) goto L_0x006f
            if (r6 != 0) goto L_0x0162
            android.graphics.Matrix r2 = new android.graphics.Matrix     // Catch:{ IOException -> 0x0165 }
            r2.<init>()     // Catch:{ IOException -> 0x0165 }
        L_0x0069:
            r0 = r17
            android.graphics.Matrix r6 = r0.rotate(r2, r13)     // Catch:{ IOException -> 0x0165 }
        L_0x006f:
            if (r6 == 0) goto L_0x00aa
            android.graphics.BitmapFactory$Options r15 = new android.graphics.BitmapFactory$Options
            r15.<init>()
            r2 = 102400(0x19000, float:1.43493E-40)
            byte[] r2 = new byte[r2]
            r15.inTempStorage = r2
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
            r15.inPreferredConfig = r2
            r2 = 1
            r15.inPurgeable = r2
            r2 = 4
            r15.inSampleSize = r2
            r2 = 1
            r15.inInputShareable = r2
            r0 = r17
            byte[] r2 = r0.data
            r3 = 0
            r0 = r17
            byte[] r4 = r0.data
            int r4 = r4.length
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeByteArray(r2, r3, r4, r15)
            r2 = 0
            r3 = 0
            int r4 = r1.getWidth()
            int r5 = r1.getHeight()
            r7 = 1
            android.graphics.Bitmap r8 = android.graphics.Bitmap.createBitmap(r1, r2, r3, r4, r5, r6, r7)
            r1.recycle()
        L_0x00aa:
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            boolean r2 = r2.needBitmap
            if (r2 == 0) goto L_0x00cf
            if (r8 != 0) goto L_0x00c2
            r0 = r17
            byte[] r2 = r0.data
            r3 = 0
            r0 = r17
            byte[] r4 = r0.data
            int r4 = r4.length
            android.graphics.Bitmap r8 = android.graphics.BitmapFactory.decodeByteArray(r2, r3, r4)
        L_0x00c2:
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            com.iwown.device_module.device_camera.camera.CameraHost r2 = r2.host
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r3 = r0.xact
            r2.saveImage(r3, r8)
        L_0x00cf:
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            boolean r2 = r2.needByteArray
            if (r2 == 0) goto L_0x0103
            if (r6 == 0) goto L_0x00f2
            java.io.ByteArrayOutputStream r16 = new java.io.ByteArrayOutputStream
            r16.<init>()
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
            r3 = 100
            r0 = r16
            r8.compress(r2, r3, r0)
            byte[] r2 = r16.toByteArray()
            r0 = r17
            r0.data = r2
            r16.close()     // Catch:{ IOException -> 0x0171 }
        L_0x00f2:
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            com.iwown.device_module.device_camera.camera.CameraHost r2 = r2.host
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r3 = r0.xact
            r0 = r17
            byte[] r4 = r0.data
            r2.saveImage(r3, r4)
        L_0x0103:
            java.lang.System.gc()
            return
        L_0x0107:
            r0 = r17
            com.iwown.device_module.device_camera.camera.PictureTransaction r2 = r0.xact
            boolean r2 = r2.mirrorFFC()
            if (r2 == 0) goto L_0x0049
            android.graphics.Matrix r2 = new android.graphics.Matrix
            r2.<init>()
            r0 = r17
            android.graphics.Matrix r6 = r0.mirror(r2)
            goto L_0x0049
        L_0x011e:
            com.iwown.device_module.device_camera.exif.ExifInterface r11 = new com.iwown.device_module.device_camera.exif.ExifInterface     // Catch:{ IOException -> 0x0165 }
            r11.<init>()     // Catch:{ IOException -> 0x0165 }
            r0 = r17
            byte[] r2 = r0.data     // Catch:{ IOException -> 0x017d }
            r11.readExif(r2)     // Catch:{ IOException -> 0x017d }
            int r2 = com.iwown.device_module.device_camera.exif.ExifInterface.TAG_ORIENTATION     // Catch:{ IOException -> 0x017d }
            java.lang.Integer r12 = r11.getTagIntValue(r2)     // Catch:{ IOException -> 0x017d }
            if (r12 == 0) goto L_0x0180
            int r2 = r12.intValue()     // Catch:{ IOException -> 0x017d }
            r3 = 6
            if (r2 != r3) goto L_0x013e
            r13 = 90
            r10 = r11
            goto L_0x0060
        L_0x013e:
            int r2 = r12.intValue()     // Catch:{ IOException -> 0x017d }
            r3 = 8
            if (r2 != r3) goto L_0x014b
            r13 = 270(0x10e, float:3.78E-43)
            r10 = r11
            goto L_0x0060
        L_0x014b:
            int r2 = r12.intValue()     // Catch:{ IOException -> 0x017d }
            r3 = 3
            if (r2 != r3) goto L_0x0157
            r13 = 180(0xb4, float:2.52E-43)
            r10 = r11
            goto L_0x0060
        L_0x0157:
            int r2 = r12.intValue()     // Catch:{ IOException -> 0x017d }
            r3 = 1
            if (r2 != r3) goto L_0x0180
            r13 = 0
            r10 = r11
            goto L_0x0060
        L_0x0162:
            r2 = r6
            goto L_0x0069
        L_0x0165:
            r9 = move-exception
        L_0x0166:
            java.lang.String r2 = "CWAC-Camera"
            java.lang.String r3 = "Exception parsing JPEG"
            android.util.Log.e(r2, r3, r9)
            goto L_0x006f
        L_0x0171:
            r9 = move-exception
            java.lang.String r2 = "CWAC-Camera"
            java.lang.String r3 = "Exception in closing a BAOS???"
            android.util.Log.e(r2, r3, r9)
            goto L_0x00f2
        L_0x017d:
            r9 = move-exception
            r10 = r11
            goto L_0x0166
        L_0x0180:
            r10 = r11
            goto L_0x0060
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_camera.camera.ImageCleanupTask.run():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Bitmap decodeRegionCrop(byte[] r13, android.graphics.Rect r14, android.graphics.Matrix r15) {
        /*
            r12 = this;
            r1 = 0
            r9 = 0
            java.lang.System.gc()
            r0 = 0
            java.io.ByteArrayInputStream r10 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x002e }
            r10.<init>(r13)     // Catch:{ Throwable -> 0x002e }
            r2 = 0
            android.graphics.BitmapRegionDecoder r7 = android.graphics.BitmapRegionDecoder.newInstance(r10, r2)     // Catch:{ Throwable -> 0x003e, all -> 0x003b }
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch:{ IllegalArgumentException -> 0x0041 }
            r2.<init>()     // Catch:{ IllegalArgumentException -> 0x0041 }
            android.graphics.Bitmap r0 = r7.decodeRegion(r14, r2)     // Catch:{ IllegalArgumentException -> 0x0041 }
        L_0x0019:
            r12.closeStream(r10)
            r9 = r10
        L_0x001d:
            int r3 = r12.PHOTO_SIZE
            int r4 = r12.PHOTO_SIZE
            r6 = 1
            r2 = r1
            r5 = r15
            android.graphics.Bitmap r11 = android.graphics.Bitmap.createBitmap(r0, r1, r2, r3, r4, r5, r6)
            if (r11 == r0) goto L_0x002d
            r0.recycle()
        L_0x002d:
            return r11
        L_0x002e:
            r8 = move-exception
        L_0x002f:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r8)     // Catch:{ all -> 0x0036 }
            r12.closeStream(r9)
            goto L_0x001d
        L_0x0036:
            r1 = move-exception
        L_0x0037:
            r12.closeStream(r9)
            throw r1
        L_0x003b:
            r1 = move-exception
            r9 = r10
            goto L_0x0037
        L_0x003e:
            r8 = move-exception
            r9 = r10
            goto L_0x002f
        L_0x0041:
            r2 = move-exception
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_camera.camera.ImageCleanupTask.decodeRegionCrop(byte[], android.graphics.Rect, android.graphics.Matrix):android.graphics.Bitmap");
    }

    public void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    private Matrix mirror(Matrix input) {
        float[] mirrorY = {-1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        Matrix matrixMirrorY = new Matrix();
        matrixMirrorY.setValues(mirrorY);
        input.postConcat(matrixMirrorY);
        return input;
    }

    private Matrix flip(Matrix input) {
        float[] mirrorY = {-1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        Matrix matrixMirrorY = new Matrix();
        matrixMirrorY.setValues(mirrorY);
        input.preScale(1.0f, -1.0f);
        input.postConcat(matrixMirrorY);
        return input;
    }

    private Matrix rotate(Matrix input, int degree) {
        input.setRotate((float) degree);
        return input;
    }

    @TargetApi(11)
    private static int calculateHeapSize(Context ctxt) {
        ActivityManager am = (ActivityManager) ctxt.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
        int memoryClass = am.getMemoryClass();
        if (VERSION.SDK_INT >= 11 && (ctxt.getApplicationInfo().flags & 1048576) != 0) {
            memoryClass = am.getLargeMemoryClass();
        }
        return memoryClass * 1048576;
    }
}
