package com.iwown.my_module.utility;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ImageUtility {
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 4;
    public static final String SDCARD = "/sdcard";
    public static final String SDCARD_MNT = "/mnt/sdcard";

    public static void saveImage(Context context, String fileName, Bitmap bitmap) throws IOException {
        saveImage(context, fileName, bitmap, 100);
    }

    public static void saveImage(Context context, String fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null && fileName != null && context != null) {
            FileOutputStream fos = context.openFileOutput(fileName, 0);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, quality, stream);
            fos.write(stream.toByteArray());
            fos.close();
        }
    }

    public static void saveImageToSD(Context ctx, String filePath, Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bitmap.compress(CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            if (ctx != null) {
                scanPhoto(ctx, filePath);
            }
        }
    }

    public static void saveBackgroundImage(Context ctx, String filePath, Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bitmap.compress(CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();
            if (ctx != null) {
                scanPhoto(ctx, filePath);
            }
        }
    }

    private static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        mediaScanIntent.setData(Uri.fromFile(new File(imgFileName)));
        ctx.sendBroadcast(mediaScanIntent);
    }

    public static Bitmap getBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (OutOfMemoryError e2) {
            ThrowableExtension.printStackTrace(e2);
        } finally {
            try {
                fis.close();
            } catch (Exception e3) {
            }
        }
        return bitmap;
    }

    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:19:0x0024=Splitter:B:19:0x0024, B:13:0x001a=Splitter:B:13:0x001a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap getBitmapByPath(java.lang.String r7, android.graphics.BitmapFactory.Options r8) {
        /*
            r3 = 0
            r0 = 0
            java.io.File r2 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0019, OutOfMemoryError -> 0x0023 }
            r2.<init>(r7)     // Catch:{ FileNotFoundException -> 0x0019, OutOfMemoryError -> 0x0023 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0019, OutOfMemoryError -> 0x0023 }
            r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0019, OutOfMemoryError -> 0x0023 }
            r5 = 0
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r4, r5, r8)     // Catch:{ FileNotFoundException -> 0x003a, OutOfMemoryError -> 0x0037, all -> 0x0034 }
            r4.close()     // Catch:{ Exception -> 0x0016 }
            r3 = r4
        L_0x0015:
            return r0
        L_0x0016:
            r5 = move-exception
            r3 = r4
            goto L_0x0015
        L_0x0019:
            r1 = move-exception
        L_0x001a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x002d }
            r3.close()     // Catch:{ Exception -> 0x0021 }
            goto L_0x0015
        L_0x0021:
            r5 = move-exception
            goto L_0x0015
        L_0x0023:
            r1 = move-exception
        L_0x0024:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x002d }
            r3.close()     // Catch:{ Exception -> 0x002b }
            goto L_0x0015
        L_0x002b:
            r5 = move-exception
            goto L_0x0015
        L_0x002d:
            r5 = move-exception
        L_0x002e:
            r3.close()     // Catch:{ Exception -> 0x0032 }
        L_0x0031:
            throw r5
        L_0x0032:
            r6 = move-exception
            goto L_0x0031
        L_0x0034:
            r5 = move-exception
            r3 = r4
            goto L_0x002e
        L_0x0037:
            r1 = move-exception
            r3 = r4
            goto L_0x0024
        L_0x003a:
            r1 = move-exception
            r3 = r4
            goto L_0x001a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.my_module.utility.ImageUtility.getBitmapByPath(java.lang.String, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:12:0x0014=Splitter:B:12:0x0014, B:18:0x001e=Splitter:B:18:0x001e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap getBitmapByFile(java.io.File r6) {
        /*
            r2 = 0
            r0 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0013, OutOfMemoryError -> 0x001d }
            r3.<init>(r6)     // Catch:{ FileNotFoundException -> 0x0013, OutOfMemoryError -> 0x001d }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r3)     // Catch:{ FileNotFoundException -> 0x0034, OutOfMemoryError -> 0x0031, all -> 0x002e }
            r3.close()     // Catch:{ Exception -> 0x0010 }
            r2 = r3
        L_0x000f:
            return r0
        L_0x0010:
            r4 = move-exception
            r2 = r3
            goto L_0x000f
        L_0x0013:
            r1 = move-exception
        L_0x0014:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0027 }
            r2.close()     // Catch:{ Exception -> 0x001b }
            goto L_0x000f
        L_0x001b:
            r4 = move-exception
            goto L_0x000f
        L_0x001d:
            r1 = move-exception
        L_0x001e:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0027 }
            r2.close()     // Catch:{ Exception -> 0x0025 }
            goto L_0x000f
        L_0x0025:
            r4 = move-exception
            goto L_0x000f
        L_0x0027:
            r4 = move-exception
        L_0x0028:
            r2.close()     // Catch:{ Exception -> 0x002c }
        L_0x002b:
            throw r4
        L_0x002c:
            r5 = move-exception
            goto L_0x002b
        L_0x002e:
            r4 = move-exception
            r2 = r3
            goto L_0x0028
        L_0x0031:
            r1 = move-exception
            r2 = r3
            goto L_0x001e
        L_0x0034:
            r1 = move-exception
            r2 = r3
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.my_module.utility.ImageUtility.getBitmapByFile(java.io.File):android.graphics.Bitmap");
    }

    public static String getTempFileName() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS").format(new Timestamp(System.currentTimeMillis()));
    }

    public static String getCamerPath() {
        return Environment.getExternalStorageDirectory() + File.separator + "FounderNews" + File.separator;
    }

    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String mUriString = Uri.decode(mUri.toString());
        String pre1 = "file:///sdcard" + File.separator;
        String pre2 = "file:///mnt/sdcard" + File.separator;
        if (mUriString.startsWith(pre1)) {
            return Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring(pre1.length());
        }
        if (mUriString.startsWith(pre2)) {
            return Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring(pre2.length());
        }
        return null;
    }

    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        Cursor cursor = context.managedQuery(uri, new String[]{"_data"}, null, null, null);
        if (cursor == null) {
            return imagePath;
        }
        int column_index = cursor.getColumnIndexOrThrow("_data");
        if (cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            return imagePath;
        }
        return cursor.getString(column_index);
    }

    public static Bitmap loadImgThumbnail(Activity context, String imgName, int kind) {
        String[] proj = {"_id", "_display_name"};
        Cursor cursor = context.managedQuery(Media.EXTERNAL_CONTENT_URI, proj, "_display_name='" + imgName + "'", null, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            return null;
        }
        ContentResolver crThumb = context.getContentResolver();
        Options options = new Options();
        options.inSampleSize = 1;
        return Thumbnails.getThumbnail(crThumb, (long) cursor.getInt(0), kind, options);
    }

    public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
        return zoomBitmap(getBitmapByPath(filePath), w, h);
    }

    public static String getLatestImage(Activity context) {
        Activity activity = context;
        Cursor cursor = activity.managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data"}, null, null, "_id desc");
        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            return cursor.getString(1);
        }
        return null;
    }

    public static int[] scaleImageSize(int[] img_size, int square_size) {
        if (img_size[0] <= square_size && img_size[1] <= square_size) {
            return img_size;
        }
        double ratio = ((double) square_size) / ((double) Math.max(img_size[0], img_size[1]));
        return new int[]{(int) (((double) img_size[0]) * ratio), (int) (((double) img_size[1]) * ratio)};
    }

    public static void createImageThumbnail(Context context, String largeImagePath, String thumbfilePath, int square_size, int quality) throws IOException {
        Options opts = new Options();
        opts.inSampleSize = 1;
        Bitmap cur_bitmap = getBitmapByPath(largeImagePath, opts);
        if (cur_bitmap != null) {
            int[] new_img_size = scaleImageSize(new int[]{cur_bitmap.getWidth(), cur_bitmap.getHeight()}, square_size);
            saveImageToSD(null, thumbfilePath, zoomBitmap(cur_bitmap, new_img_size[0], new_img_size[1]), quality);
        }
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) w) / ((float) width), ((float) h) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) 200) / ((float) width);
        float scaleHeight = ((float) 200) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap reDrawBitMap(Activity context, Bitmap bitmap) {
        float zoomScale;
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int i = dm.heightPixels;
        int rWidth = dm.widthPixels;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (width >= rWidth) {
            zoomScale = ((float) rWidth) / ((float) width);
        } else {
            zoomScale = 1.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(zoomScale, zoomScale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height / 2) + height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawRect(0.0f, (float) height, (float) width, (float) (height + 4), new Paint());
        canvas.drawBitmap(reflectionImage, 0.0f, (float) (height + 4), null);
        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0.0f, (float) bitmap.getHeight(), 0.0f, (float) (bitmapWithReflection.getHeight() + 4), 1895825407, ViewCompat.MEASURED_SIZE_MASK, TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0.0f, (float) height, (float) width, (float) (bitmapWithReflection.getHeight() + 4), paint);
        return bitmapWithReflection;
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0028 A[SYNTHETIC, Splitter:B:21:0x0028] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getImageType(java.io.File r6) {
        /*
            r3 = 0
            if (r6 == 0) goto L_0x0009
            boolean r4 = r6.exists()
            if (r4 != 0) goto L_0x000a
        L_0x0009:
            return r3
        L_0x000a:
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x001c, all -> 0x0025 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x001c, all -> 0x0025 }
            java.lang.String r3 = getImageType(r2)     // Catch:{ IOException -> 0x0031, all -> 0x002e }
            if (r2 == 0) goto L_0x0009
            r2.close()     // Catch:{ IOException -> 0x001a }
            goto L_0x0009
        L_0x001a:
            r4 = move-exception
            goto L_0x0009
        L_0x001c:
            r0 = move-exception
        L_0x001d:
            if (r1 == 0) goto L_0x0009
            r1.close()     // Catch:{ IOException -> 0x0023 }
            goto L_0x0009
        L_0x0023:
            r4 = move-exception
            goto L_0x0009
        L_0x0025:
            r4 = move-exception
        L_0x0026:
            if (r1 == 0) goto L_0x002b
            r1.close()     // Catch:{ IOException -> 0x002c }
        L_0x002b:
            throw r4
        L_0x002c:
            r5 = move-exception
            goto L_0x002b
        L_0x002e:
            r4 = move-exception
            r1 = r2
            goto L_0x0026
        L_0x0031:
            r0 = move-exception
            r1 = r2
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.my_module.utility.ImageUtility.getImageType(java.io.File):java.lang.String");
    }

    public static String getImageType(InputStream in) {
        String str = null;
        if (in == null) {
            return str;
        }
        try {
            byte[] bytes = new byte[8];
            in.read(bytes);
            return getImageType(bytes);
        } catch (IOException e) {
            return str;
        }
    }

    public Bitmap toRoundBitmap(Bitmap bitmap) {
        float roundPx;
        float left;
        float right;
        float top;
        float bottom;
        float dst_left;
        float dst_top;
        float dst_right;
        float dst_bottom;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= height) {
            roundPx = (float) (width / 2);
            top = 0.0f;
            bottom = (float) width;
            left = 0.0f;
            right = (float) width;
            height = width;
            dst_left = 0.0f;
            dst_top = 0.0f;
            dst_right = (float) width;
            dst_bottom = (float) width;
        } else {
            roundPx = (float) (height / 2);
            float clip = (float) ((width - height) / 2);
            left = clip;
            right = ((float) width) - clip;
            top = 0.0f;
            bottom = (float) height;
            width = height;
            dst_left = 0.0f;
            dst_top = 0.0f;
            dst_right = (float) height;
            dst_bottom = (float) height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
        Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, dst, paint);
        return output;
    }

    public static int readPictureDegree(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return Constants.LANDSCAPE_270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);
        System.out.println("angle2=" + angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) {
            return "image/jpeg";
        }
        if (isGIF(bytes)) {
            return "image/gif";
        }
        if (isPNG(bytes)) {
            return "image/png";
        }
        if (isBMP(bytes)) {
            return "application/x-bmp";
        }
        return null;
    }

    private static boolean isJPEG(byte[] b) {
        boolean z = true;
        if (b.length < 2) {
            return false;
        }
        if (!(b[0] == -1 && b[1] == -40)) {
            z = false;
        }
        return z;
    }

    private static boolean isGIF(byte[] b) {
        boolean z = true;
        if (b.length < 6) {
            return false;
        }
        if (!(b[0] == 71 && b[1] == 73 && b[2] == 70 && b[3] == 56 && ((b[4] == 55 || b[4] == 57) && b[5] == 97))) {
            z = false;
        }
        return z;
    }

    private static boolean isPNG(byte[] b) {
        boolean z = true;
        if (b.length < 8) {
            return false;
        }
        if (!(b[0] == -119 && b[1] == 80 && b[2] == 78 && b[3] == 71 && b[4] == 13 && b[5] == 10 && b[6] == 26 && b[7] == 10)) {
            z = false;
        }
        return z;
    }

    private static boolean isBMP(byte[] b) {
        boolean z = true;
        if (b.length < 2) {
            return false;
        }
        if (!(b[0] == 66 && b[1] == 77)) {
            z = false;
        }
        return z;
    }

    public static Drawable ControlBitMap(Context context, int id) {
        Options opt = new Options();
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        try {
            InputStream is = context.getResources().openRawResource(id);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
            is.close();
            return new BitmapDrawable(context.getResources(), bitmap);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64Utility.decode(string);
            return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return bitmap;
        }
    }

    public static String bitmaptoString(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        return Base64.encode(bStream.toByteArray());
    }
}
