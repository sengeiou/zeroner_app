package com.dmcbig.mediapicker.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.alibaba.android.arouter.utils.Consts;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsSpeedRef;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

public class FileUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final long MB = 1048576;

    public static File createTmpFile(Context context) throws IOException {
        File dir;
        if (TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists()) {
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
                if (!dir.exists()) {
                    dir = getCacheDirectory(context, true);
                }
            }
        } else {
            dir = getCacheDirectory(context, true);
        }
        return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir);
    }

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        }
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return result;
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        String externalStorageState;
        File appCacheDir = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e2) {
            externalStorageState = "";
        }
        if (preferExternal && "mounted".equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            return new File("/data/data/" + context.getPackageName() + "/cache/");
        }
        return appCacheDir;
    }

    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (individualCacheDir.exists() || individualCacheDir.mkdir()) {
            return individualCacheDir;
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File appCacheDir = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), context.getPackageName()), "cache");
        if (appCacheDir.exists()) {
            return appCacheDir;
        }
        if (!appCacheDir.mkdirs()) {
            return null;
        }
        try {
            new File(appCacheDir, ".nomedia").createNewFile();
            return appCacheDir;
        } catch (IOException e) {
            return appCacheDir;
        }
    }

    public String getSizeByUnit(double size) {
        if (size == Utils.DOUBLE_EPSILON) {
            return "0K";
        }
        if (size >= 1048576.0d) {
            double sizeInM = size / 1048576.0d;
            return String.format(Locale.getDefault(), "%.1f", new Object[]{Double.valueOf(sizeInM)}) + "M";
        }
        double sizeInK = size / 1024.0d;
        return String.format(Locale.getDefault(), "%.1f", new Object[]{Double.valueOf(sizeInK)}) + GpsSpeedRef.KILOMETERS;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        return context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION) == 0;
    }

    public static String fileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        int digitGroups = (int) (Math.log10((double) size) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.#").format(((double) size) / Math.pow(1024.0d, (double) digitGroups)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + new String[]{"B", "kB", "MB", "GB", "TB"}[digitGroups];
    }

    public static String getMimeType(Context context, Uri uri) {
        if ("content".equals(uri.getScheme())) {
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri));
            return TextUtils.isEmpty(extension) ? MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString()) : extension;
        }
        String extension2 = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        if (TextUtils.isEmpty(extension2)) {
            return MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri));
        }
        return extension2;
    }

    public static String getMimeTypeByFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(Consts.DOT), fileName.length());
    }
}
