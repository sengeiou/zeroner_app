package com.iwown.sport_module.zxing.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Images.Media;
import java.io.FileDescriptor;
import java.io.FileInputStream;

public class ImageUtils {
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int requiredWidth, int requiredHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromDisk(FileDescriptor descriptor, int requiredWidth, int requiredHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Rect outPadding = new Rect(0, 0, requiredWidth, requiredHeight);
        BitmapFactory.decodeFileDescriptor(descriptor, outPadding, options);
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(descriptor, outPadding, options);
    }

    public static Bitmap decodeSampledBitmap(FileInputStream is, int requiredWidth, int requiredHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static Bitmap decodeSampledBitmapFromDisk(String imagePath, int requiredWidth, int requiredHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    private static int calculateInSampleSize(Options options, int requiredWidth, int requiredHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        if (width <= requiredWidth && height <= requiredHeight) {
            return 1;
        }
        int widthRatio = Math.round(((float) width) / ((float) requiredWidth));
        int heightRatio = Math.round(((float) height) / ((float) requiredHeight));
        if (widthRatio < heightRatio) {
            return widthRatio;
        }
        return heightRatio;
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        if (VERSION.SDK_INT >= 19) {
            return getRealPathFromUriAboveApi19(context, uri);
        }
        return getRealPathFromUriBelowAPI19(context, uri);
    }

    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    @SuppressLint({"NewApi"})
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) {
                String[] selectionArgs = {documentId.split(":")[1]};
                return getDataColumn(context, Media.EXTERNAL_CONTENT_URI, "_id=?", selectionArgs);
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null, null);
            } else {
                return null;
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else {
            if ("file".equals(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String[] projection = {"_data"};
        Cursor cursor = null;
        try {
            Cursor cursor2 = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor2 == null || !cursor2.moveToFirst()) {
                return null;
            }
            return cursor2.getString(cursor2.getColumnIndexOrThrow(projection[0]));
        } catch (Exception e) {
            if (cursor == null) {
                return null;
            }
            cursor.close();
            return null;
        }
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static Bitmap getSmallerBitmap(Bitmap bitmap) {
        int size = (bitmap.getWidth() * bitmap.getHeight()) / 160000;
        if (size <= 1) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postScale((float) (1.0d / Math.sqrt((double) size)), (float) (1.0d / Math.sqrt((double) size)));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
