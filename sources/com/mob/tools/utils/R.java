package com.mob.tools.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.github.mikephil.charting.utils.Utils;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import kotlin.text.Typography;

public class R {
    private static float density;
    private static int deviceWidth;
    private static Object rp;

    public static int dipToPx(Context context, int dip) {
        if (density <= 0.0f) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((((float) dip) * density) + 0.5f);
    }

    public static int pxToDip(Context context, int px) {
        if (density <= 0.0f) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((((float) px) / density) + 0.5f);
    }

    public static int designToDevice(Context context, int designScreenWidth, int designPx) {
        if (deviceWidth == 0) {
            int[] scrSize = getScreenSize(context);
            deviceWidth = scrSize[0] < scrSize[1] ? scrSize[0] : scrSize[1];
        }
        return (int) (((((float) designPx) * ((float) deviceWidth)) / ((float) designScreenWidth)) + 0.5f);
    }

    public static int designToDevice(Context context, float designScreenDensity, int designPx) {
        if (density <= 0.0f) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return (int) (((((float) designPx) * density) / designScreenDensity) + 0.5f);
    }

    public static int[] getScreenSize(Context context) {
        WindowManager windowManager;
        try {
            windowManager = (WindowManager) context.getSystemService("window");
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            windowManager = null;
        }
        if (windowManager == null) {
            return new int[]{0, 0};
        }
        Display display = windowManager.getDefaultDisplay();
        if (VERSION.SDK_INT < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            return new int[]{dm.widthPixels, dm.heightPixels};
        }
        try {
            Point size = new Point();
            Method method = display.getClass().getMethod("getRealSize", new Class[]{Point.class});
            method.setAccessible(true);
            method.invoke(display, new Object[]{size});
            return new int[]{size.x, size.y};
        } catch (Throwable t2) {
            MobLog.getInstance().w(t2);
            return new int[]{0, 0};
        }
    }

    public static int getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    public static void setResourceProvider(Object rp2) {
        try {
            if (rp2.getClass().getMethod("getResId", new Class[]{Context.class, String.class, String.class}) != null) {
                rp = rp2;
            }
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
        }
    }

    public static int getResId(Context context, String resType, String resName) {
        int resId = 0;
        if (context == null || TextUtils.isEmpty(resType) || TextUtils.isEmpty(resName)) {
            return resId;
        }
        if (rp != null) {
            try {
                Method mth = rp.getClass().getMethod("getResId", new Class[]{Context.class, String.class, String.class});
                mth.setAccessible(true);
                resId = ((Integer) mth.invoke(rp, new Object[]{context, resType, resName})).intValue();
            } catch (Throwable t) {
                MobLog.getInstance().d(t);
            }
        }
        if (resId <= 0) {
            String pck = context.getPackageName();
            if (TextUtils.isEmpty(pck)) {
                return resId;
            }
            if (resId <= 0) {
                resId = context.getResources().getIdentifier(resName, resType, pck);
                if (resId <= 0) {
                    resId = context.getResources().getIdentifier(resName.toLowerCase(), resType, pck);
                }
            }
            if (resId <= 0) {
                System.err.println("failed to parse " + resType + " resource \"" + resName + "\"");
            }
        }
        return resId;
    }

    public static int getBitmapRes(Context context, String resName) {
        return getResId(context, "drawable", resName);
    }

    public static int getStringRes(Context context, String resName) {
        return getResId(context, "string", resName);
    }

    public static int getStringArrayRes(Context context, String resName) {
        return getResId(context, "array", resName);
    }

    public static int getLayoutRes(Context context, String resName) {
        return getResId(context, "layout", resName);
    }

    public static int getStyleRes(Context context, String resName) {
        return getResId(context, "style", resName);
    }

    public static int getIdRes(Context context, String resName) {
        return getResId(context, "id", resName);
    }

    public static int getColorRes(Context context, String resName) {
        return getResId(context, "color", resName);
    }

    public static int getRawRes(Context context, String resName) {
        return getResId(context, ShareConstants.DEXMODE_RAW, resName);
    }

    public static int getPluralsRes(Context context, String resName) {
        return getResId(context, "plurals", resName);
    }

    public static int getAnimRes(Context context, String resName) {
        return getResId(context, "anim", resName);
    }

    public static String getCacheRoot(Context context) {
        String appDir = context.getFilesDir().getAbsolutePath() + "/Mob/";
        DeviceHelper helper = DeviceHelper.getInstance(context);
        if (helper.getSdcardState()) {
            appDir = helper.getSdcardPath() + "/Mob/";
        }
        File file = new File(appDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return appDir;
    }

    public static String getCachePath(Context context, String category) {
        String appDir = context.getFilesDir().getAbsolutePath() + "/Mob/cache/";
        DeviceHelper helper = DeviceHelper.getInstance(context);
        try {
            if (helper.getSdcardState()) {
                appDir = helper.getSdcardPath() + "/Mob/" + helper.getPackageName() + "/cache/";
            }
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
        }
        if (!TextUtils.isEmpty(category)) {
            appDir = appDir + category + "/";
        }
        File file = new File(appDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return appDir;
    }

    public static String getImageCachePath(Context context) {
        return getCachePath(context, "images");
    }

    public static void clearCache(Context context) throws Throwable {
        deleteFileAndFolder(new File(getCachePath(context, null)));
    }

    public static void deleteFilesInFolder(File folder) throws Throwable {
        if (folder != null && folder.exists()) {
            if (folder.isFile()) {
                folder.delete();
                return;
            }
            String[] names = folder.list();
            if (names != null && names.length > 0) {
                for (String name : names) {
                    File f = new File(folder, name);
                    if (f.isDirectory()) {
                        deleteFilesInFolder(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
    }

    public static void deleteFileAndFolder(File folder) throws Throwable {
        if (folder != null && folder.exists()) {
            if (folder.isFile()) {
                folder.delete();
                return;
            }
            String[] names = folder.list();
            if (names == null || names.length <= 0) {
                folder.delete();
                return;
            }
            for (String name : names) {
                File f = new File(folder, name);
                if (f.isDirectory()) {
                    deleteFileAndFolder(f);
                } else {
                    f.delete();
                }
            }
            folder.delete();
        }
    }

    public static String toWordText(String text, int lenInWord) {
        char[] cText = text.toCharArray();
        int count = lenInWord * 2;
        StringBuilder sb = new StringBuilder();
        char[] arr$ = cText;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            char ch = arr$[i$];
            count -= ch < 256 ? 1 : 2;
            if (count < 0) {
                return sb.toString();
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    public static int getTextLengthInWord(String text) {
        char[] cText = text == null ? new char[0] : text.toCharArray();
        int count = 0;
        for (int i = 0; i < cText.length; i++) {
            count += cText[i] < 256 ? 1 : 2;
        }
        return count;
    }

    public static long strToDate(String strDate) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate, new ParsePosition(0)).getTime();
    }

    public static long dateStrToLong(String strDate) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(strDate, new ParsePosition(0)).getTime();
    }

    public static Date longToDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    public static String longToTime(long time, int level) {
        String format = "yyyy-MM-dd kk:mm:ss";
        switch (level) {
            case 1:
                format = "yyyy";
                break;
            case 2:
                format = "yyyy-MM";
                break;
            case 5:
                format = "yyyy-MM-dd";
                break;
            case 10:
                format = "yyyy-MM-dd kk";
                break;
            case 12:
                format = "yyyy-MM-dd kk:mm";
                break;
        }
        return new SimpleDateFormat(format).format(Long.valueOf(time));
    }

    public static long dateToLong(String date) {
        try {
            Date d = new Date(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            return cal.getTimeInMillis();
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return 0;
        }
    }

    public static int[] covertTimeInYears(long time) {
        long delta = System.currentTimeMillis() - time;
        if (delta <= 0) {
            return new int[]{0, 0};
        }
        long delta2 = delta / 1000;
        if (delta2 < 60) {
            return new int[]{(int) delta2, 0};
        }
        long delta3 = delta2 / 60;
        if (delta3 < 60) {
            return new int[]{(int) delta3, 1};
        }
        long delta4 = delta3 / 60;
        if (delta4 < 24) {
            return new int[]{(int) delta4, 2};
        }
        long delta5 = delta4 / 24;
        if (delta5 < 30) {
            return new int[]{(int) delta5, 3};
        }
        long delta6 = delta5 / 30;
        if (delta6 < 12) {
            return new int[]{(int) delta6, 4};
        }
        return new int[]{(int) (delta6 / 12), 5};
    }

    public static Uri pathToContentUri(Context context, String imagePath) {
        Cursor cursor = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{imagePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            return Uri.withAppendedPath(Uri.parse("content://media/external/images/media"), "" + cursor.getInt(cursor.getColumnIndex("_id")));
        } else if (!new File(imagePath).exists()) {
            return null;
        } else {
            ContentValues values = new ContentValues();
            values.put("_data", imagePath);
            return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    public static String contentUriToPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if (new File(uri.getPath()).exists()) {
            return uri.getPath();
        }
        String path = null;
        Cursor c = null;
        try {
            if (VERSION.SDK_INT >= 19) {
                Class<?> DocumentsContract = Class.forName("android.provider.DocumentsContract");
                Method isDocumentUri = DocumentsContract.getMethod("isDocumentUri", new Class[]{Context.class, Uri.class});
                isDocumentUri.setAccessible(true);
                if (Boolean.TRUE.equals(isDocumentUri.invoke(null, new Object[]{context, uri}))) {
                    Method getDocumentId = DocumentsContract.getMethod("getDocumentId", new Class[]{Uri.class});
                    getDocumentId.setAccessible(true);
                    String[] column = {"_data"};
                    String[] args = {String.valueOf(getDocumentId.invoke(null, new Object[]{uri})).split(":")[1]};
                    c = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, column, "_id=?", args, null);
                }
            }
            if (c == null) {
                c = context.getContentResolver().query(uri, null, null, null, null);
            }
            if (c == null) {
                return null;
            }
            if (c.moveToFirst()) {
                path = c.getString(c.getColumnIndex("_data"));
            }
            c.close();
            return path;
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return null;
        }
    }

    public static String encodeUrl(Bundle parameters) {
        if (parameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            Object value = parameters.get(key);
            if (value == null) {
                value = "";
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(Data.urlEncode(key) + "=" + Data.urlEncode(String.valueOf(value)));
        }
        return sb.toString();
    }

    public static String encodeUrl(ArrayList<KVPair<String>> values) {
        if (values == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Iterator i$ = values.iterator();
        while (i$.hasNext()) {
            KVPair<String> pair = (KVPair) i$.next();
            if (i > 0) {
                sb.append(Typography.amp);
            }
            String key = pair.name;
            String value = (String) pair.value;
            if (key != null) {
                if (value == null) {
                    value = "";
                }
                sb.append(Data.urlEncode(key) + "=" + Data.urlEncode(value));
                i++;
            }
        }
        return sb.toString();
    }

    public static Bundle urlToBundle(String url) {
        String url2;
        int index = url.indexOf("://");
        if (index >= 0) {
            url2 = "http://" + url.substring(index + 1);
        } else {
            url2 = "http://" + url;
        }
        try {
            URL u = new URL(url2);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (Throwable e) {
            MobLog.getInstance().w(e);
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            for (String parameter : s.split("&")) {
                String[] v = parameter.split("=");
                if (v.length < 2 || v[1] == null) {
                    params.putString(URLDecoder.decode(v[0]), "");
                } else {
                    params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
                }
            }
        }
        return params;
    }

    public static int parseInt(String string) throws Throwable {
        return parseInt(string, 10);
    }

    public static int parseInt(String string, int radix) throws Throwable {
        if (radix < 2 || radix > 36) {
            throw new Throwable("Invalid radix: " + radix);
        } else if (string == null) {
            throw invalidInt(string);
        } else {
            int length = string.length();
            int i = 0;
            if (length == 0) {
                throw invalidInt(string);
            }
            boolean negative = string.charAt(0) == '-';
            if (negative) {
                i = 0 + 1;
                if (i == length) {
                    throw invalidInt(string);
                }
            }
            return parseInt(string, i, radix, negative);
        }
    }

    private static int parseInt(String string, int offset, int radix, boolean negative) throws Throwable {
        int max = Integer.MIN_VALUE / radix;
        int result = 0;
        int length = string.length();
        int offset2 = offset;
        while (offset2 < length) {
            int offset3 = offset2 + 1;
            int digit = digit(string.charAt(offset2), radix);
            if (digit == -1) {
                throw invalidInt(string);
            } else if (max > result) {
                throw invalidInt(string);
            } else {
                int next = (result * radix) - digit;
                if (next > result) {
                    throw invalidInt(string);
                }
                result = next;
                offset2 = offset3;
            }
        }
        if (!negative) {
            result = -result;
            if (result < 0) {
                throw invalidInt(string);
            }
        }
        return result;
    }

    private static int digit(int codePoint, int radix) {
        if (radix < 2 || radix > 36) {
            return -1;
        }
        int result = -1;
        if (48 <= codePoint && codePoint <= 57) {
            result = codePoint - 48;
        } else if (97 <= codePoint && codePoint <= 122) {
            result = (codePoint - 97) + 10;
        } else if (65 <= codePoint && codePoint <= 90) {
            result = (codePoint - 65) + 10;
        }
        if (result >= radix) {
            return -1;
        }
        return result;
    }

    private static Throwable invalidInt(String s) throws Throwable {
        throw new Throwable("Invalid int: \"" + s + "\"");
    }

    public static long parseLong(String string) throws Throwable {
        return parseLong(string, 10);
    }

    public static long parseLong(String string, int radix) throws Throwable {
        if (radix < 2 || radix > 36) {
            throw new Throwable("Invalid radix: " + radix);
        } else if (string == null) {
            throw new Throwable("Invalid long: \"" + string + "\"");
        } else {
            int length = string.length();
            int i = 0;
            if (length == 0) {
                throw new Throwable("Invalid long: \"" + string + "\"");
            }
            boolean negative = string.charAt(0) == '-';
            if (negative) {
                i = 0 + 1;
                if (i == length) {
                    throw new Throwable("Invalid long: \"" + string + "\"");
                }
            }
            return parseLong(string, i, radix, negative);
        }
    }

    private static long parseLong(String string, int offset, int radix, boolean negative) throws Throwable {
        long max = Long.MIN_VALUE / ((long) radix);
        long result = 0;
        long length = (long) string.length();
        while (true) {
            int offset2 = offset;
            if (((long) offset2) < length) {
                offset = offset2 + 1;
                int digit = digit(string.charAt(offset2), radix);
                if (digit == -1) {
                    throw new Throwable("Invalid long: \"" + string + "\"");
                } else if (max > result) {
                    throw new Throwable("Invalid long: \"" + string + "\"");
                } else {
                    long next = (((long) radix) * result) - ((long) digit);
                    if (next > result) {
                        throw new Throwable("Invalid long: \"" + string + "\"");
                    }
                    result = next;
                }
            } else {
                if (!negative) {
                    result = -result;
                    if (result < 0) {
                        throw new Throwable("Invalid long: \"" + string + "\"");
                    }
                }
                return result;
            }
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static <T> T forceCast(Object obj) {
        return forceCast(obj, null);
    }

    public static <T> T forceCast(Object obj, T defValue) {
        boolean z = true;
        if (obj == null) {
            return defValue;
        }
        if (obj instanceof Byte) {
            byte value = ((Byte) obj).byteValue();
            if (defValue instanceof Boolean) {
                return Boolean.valueOf(value != 0);
            } else if (defValue instanceof Short) {
                return Short.valueOf((short) value);
            } else {
                if (defValue instanceof Character) {
                    return Character.valueOf((char) value);
                }
                if (defValue instanceof Integer) {
                    return Integer.valueOf(value);
                }
                if (defValue instanceof Float) {
                    return Float.valueOf((float) value);
                }
                if (defValue instanceof Long) {
                    return Long.valueOf((long) value);
                }
                if (defValue instanceof Double) {
                    return Double.valueOf((double) value);
                }
            }
        } else if (obj instanceof Character) {
            char value2 = ((Character) obj).charValue();
            if (defValue instanceof Byte) {
                return Byte.valueOf((byte) value2);
            }
            if (defValue instanceof Boolean) {
                if (value2 == 0) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (defValue instanceof Short) {
                return Short.valueOf((short) value2);
            } else {
                if (defValue instanceof Integer) {
                    return Integer.valueOf(value2);
                }
                if (defValue instanceof Float) {
                    return Float.valueOf((float) value2);
                }
                if (defValue instanceof Long) {
                    return Long.valueOf((long) value2);
                }
                if (defValue instanceof Double) {
                    return Double.valueOf((double) value2);
                }
            }
        } else if (obj instanceof Short) {
            short value3 = ((Short) obj).shortValue();
            if (defValue instanceof Byte) {
                return Byte.valueOf((byte) value3);
            }
            if (defValue instanceof Boolean) {
                if (value3 == 0) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (defValue instanceof Character) {
                return Character.valueOf((char) value3);
            } else {
                if (defValue instanceof Integer) {
                    return Integer.valueOf(value3);
                }
                if (defValue instanceof Float) {
                    return Float.valueOf((float) value3);
                }
                if (defValue instanceof Long) {
                    return Long.valueOf((long) value3);
                }
                if (defValue instanceof Double) {
                    return Double.valueOf((double) value3);
                }
            }
        } else if (obj instanceof Integer) {
            int value4 = ((Integer) obj).intValue();
            if (defValue instanceof Byte) {
                return Byte.valueOf((byte) value4);
            }
            if (defValue instanceof Boolean) {
                if (value4 == 0) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (defValue instanceof Character) {
                return Character.valueOf((char) value4);
            } else {
                if (defValue instanceof Short) {
                    return Short.valueOf((short) value4);
                }
                if (defValue instanceof Float) {
                    return Float.valueOf((float) value4);
                }
                if (defValue instanceof Long) {
                    return Long.valueOf((long) value4);
                }
                if (defValue instanceof Double) {
                    return Double.valueOf((double) value4);
                }
            }
        } else if (obj instanceof Float) {
            float value5 = ((Float) obj).floatValue();
            if (defValue instanceof Byte) {
                return Byte.valueOf((byte) ((int) value5));
            }
            if (defValue instanceof Boolean) {
                if (value5 == 0.0f) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (defValue instanceof Character) {
                return Character.valueOf((char) ((int) value5));
            } else {
                if (defValue instanceof Short) {
                    return Short.valueOf((short) ((int) value5));
                }
                if (defValue instanceof Integer) {
                    return Integer.valueOf((int) value5);
                }
                if (defValue instanceof Long) {
                    return Long.valueOf((long) value5);
                }
                if (defValue instanceof Double) {
                    return Double.valueOf((double) value5);
                }
            }
        } else if (obj instanceof Long) {
            long value6 = ((Long) obj).longValue();
            if (defValue instanceof Byte) {
                return Byte.valueOf((byte) ((int) value6));
            }
            if (defValue instanceof Boolean) {
                if (value6 == 0) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (defValue instanceof Character) {
                return Character.valueOf((char) ((int) value6));
            } else {
                if (defValue instanceof Short) {
                    return Short.valueOf((short) ((int) value6));
                }
                if (defValue instanceof Integer) {
                    return Integer.valueOf((int) value6);
                }
                if (defValue instanceof Float) {
                    return Float.valueOf((float) value6);
                }
                if (defValue instanceof Double) {
                    return Double.valueOf((double) value6);
                }
            }
        } else if (obj instanceof Double) {
            double value7 = ((Double) obj).doubleValue();
            if (defValue instanceof Byte) {
                return Byte.valueOf((byte) ((int) value7));
            }
            if (defValue instanceof Boolean) {
                if (value7 == Utils.DOUBLE_EPSILON) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } else if (defValue instanceof Character) {
                return Character.valueOf((char) ((int) value7));
            } else {
                if (defValue instanceof Short) {
                    return Short.valueOf((short) ((int) value7));
                }
                if (defValue instanceof Integer) {
                    return Integer.valueOf((int) value7);
                }
                if (defValue instanceof Float) {
                    return Float.valueOf((float) value7);
                }
                if (defValue instanceof Long) {
                    return Long.valueOf((long) value7);
                }
            }
        }
        return obj;
    }

    public static boolean copyFile(String fromFilePath, String toFilePath) {
        if (TextUtils.isEmpty(fromFilePath) || TextUtils.isEmpty(toFilePath) || !new File(fromFilePath).exists()) {
            return false;
        }
        try {
            copyFile(new FileInputStream(fromFilePath), new FileOutputStream(toFilePath));
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static void copyFile(FileInputStream src, FileOutputStream dst) throws Throwable {
        byte[] buf = new byte[65536];
        int len = src.read(buf);
        while (len > 0) {
            dst.write(buf, 0, len);
            len = src.read(buf);
        }
        src.close();
        dst.close();
    }

    public static long getFileSize(String path) throws Throwable {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        return getFileSize(new File(path));
    }

    public static long getFileSize(File file) throws Throwable {
        if (!file.exists()) {
            return 0;
        }
        if (!file.isDirectory()) {
            return file.length();
        }
        int size = 0;
        for (String file2 : file.list()) {
            size = (int) (((long) size) + getFileSize(new File(file, file2)));
        }
        return (long) size;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c A[SYNTHETIC, Splitter:B:15:0x002c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean saveObjectToFile(java.lang.String r7, java.lang.Object r8) {
        /*
            boolean r6 = android.text.TextUtils.isEmpty(r7)
            if (r6 != 0) goto L_0x0050
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0046 }
            r1.<init>(r7)     // Catch:{ Throwable -> 0x0046 }
            boolean r6 = r1.exists()     // Catch:{ Throwable -> 0x0052 }
            if (r6 == 0) goto L_0x0015
            r1.delete()     // Catch:{ Throwable -> 0x0052 }
        L_0x0015:
            java.io.File r6 = r1.getParentFile()     // Catch:{ Throwable -> 0x0052 }
            boolean r6 = r6.exists()     // Catch:{ Throwable -> 0x0052 }
            if (r6 != 0) goto L_0x0026
            java.io.File r6 = r1.getParentFile()     // Catch:{ Throwable -> 0x0052 }
            r6.mkdirs()     // Catch:{ Throwable -> 0x0052 }
        L_0x0026:
            r1.createNewFile()     // Catch:{ Throwable -> 0x0052 }
            r0 = r1
        L_0x002a:
            if (r0 == 0) goto L_0x0050
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x004c }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x004c }
            java.util.zip.GZIPOutputStream r3 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x004c }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x004c }
            java.io.ObjectOutputStream r4 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x004c }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x004c }
            r4.writeObject(r8)     // Catch:{ Throwable -> 0x004c }
            r4.flush()     // Catch:{ Throwable -> 0x004c }
            r4.close()     // Catch:{ Throwable -> 0x004c }
            r6 = 1
        L_0x0045:
            return r6
        L_0x0046:
            r5 = move-exception
        L_0x0047:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r5)
            r0 = 0
            goto L_0x002a
        L_0x004c:
            r5 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r5)
        L_0x0050:
            r6 = 0
            goto L_0x0045
        L_0x0052:
            r5 = move-exception
            r0 = r1
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.utils.R.saveObjectToFile(java.lang.String, java.lang.Object):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0015 A[SYNTHETIC, Splitter:B:10:0x0015] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object readObjectFromFile(java.lang.String r8) {
        /*
            boolean r7 = android.text.TextUtils.isEmpty(r8)
            if (r7 != 0) goto L_0x0036
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x002c }
            r1.<init>(r8)     // Catch:{ Throwable -> 0x002c }
            boolean r7 = r1.exists()     // Catch:{ Throwable -> 0x0038 }
            if (r7 != 0) goto L_0x003b
            r0 = 0
        L_0x0013:
            if (r0 == 0) goto L_0x0036
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0032 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0032 }
            java.util.zip.GZIPInputStream r3 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x0032 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0032 }
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x0032 }
            r5.<init>(r3)     // Catch:{ Throwable -> 0x0032 }
            java.lang.Object r4 = r5.readObject()     // Catch:{ Throwable -> 0x0032 }
            r5.close()     // Catch:{ Throwable -> 0x0032 }
        L_0x002b:
            return r4
        L_0x002c:
            r6 = move-exception
        L_0x002d:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r6)
            r0 = 0
            goto L_0x0013
        L_0x0032:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r6)
        L_0x0036:
            r4 = 0
            goto L_0x002b
        L_0x0038:
            r6 = move-exception
            r0 = r1
            goto L_0x002d
        L_0x003b:
            r0 = r1
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.utils.R.readObjectFromFile(java.lang.String):java.lang.Object");
    }
}
