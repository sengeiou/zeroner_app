package coms.mediatek.ctrl.notification;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.Calendar;

final class g {
    private static int a = 256;

    public static int a() {
        Log.d("App/Util", "genMessageId(), messageId=" + a);
        int i = a;
        a = i + 1;
        return i;
    }

    public static int a(long j) {
        Log.d("App/Util", "getUTCTime(), local time=" + j);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        int timeInMillis = (int) (instance.getTimeInMillis() / 1000);
        Log.d("App/Util", "getUTCTime(), UTC time=" + timeInMillis);
        return timeInMillis;
    }

    public static ApplicationInfo a(Context context, CharSequence charSequence) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(charSequence.toString(), 0);
        } catch (NameNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        }
        Log.d("App/Util", "getAppInfo(), appInfo=" + applicationInfo);
        return applicationInfo;
    }

    private static Bitmap a(Context context, ApplicationInfo applicationInfo, boolean z) {
        Bitmap bitmap;
        if (context == null || applicationInfo == null) {
            bitmap = null;
        } else {
            Drawable applicationIcon = context.getPackageManager().getApplicationIcon(applicationInfo);
            bitmap = z ? Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Config.ARGB_8888) : b();
            Canvas canvas = new Canvas(bitmap);
            applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            applicationIcon.draw(canvas);
        }
        Log.d("App/Util", "createIcon(), icon width=" + bitmap.getWidth());
        return bitmap;
    }

    public static String a(Context context, ApplicationInfo applicationInfo) {
        String charSequence = (context == null || applicationInfo == null) ? "(unknown)" : context.getPackageManager().getApplicationLabel(applicationInfo).toString();
        Log.d("App/Util", "getAppName(), appName=" + charSequence);
        return charSequence;
    }

    public static String a(Context context, String str) {
        String str2 = null;
        if (str != null && !str.equals("")) {
            try {
                Cursor query = context.getContentResolver().query(Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{"display_name"}, null, null, null);
                str2 = (query == null || !query.moveToFirst()) ? str : query.getString(0);
                try {
                    query.close();
                    Log.d("App/Util", "getContactName(), contactName=" + str2);
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                str2 = str;
                Log.d("App/Util", "getContactName Exception");
                return str2;
            }
        }
        return str2;
    }

    private static Bitmap b() {
        Bitmap createBitmap = Bitmap.createBitmap(40, 40, Config.RGB_565);
        int[] iArr = new int[1600];
        for (int i = 0; i < 40; i++) {
            for (int i2 = 0; i2 < 40; i2++) {
                int i3 = (i * 40) + i2;
                iArr[i3] = ((((iArr[i3] >> 16) & 255) | 255) << 16) | ViewCompat.MEASURED_STATE_MASK | ((((iArr[i3] >> 8) & 255) | 255) << 8) | (iArr[i3] & 255) | 255;
            }
        }
        Log.d("App/Util", "createWhiteBitmap(), pixels num=" + iArr.length);
        createBitmap.setPixels(iArr, 0, 40, 0, 0, 40, 40);
        return createBitmap;
    }

    public static Bitmap b(Context context, ApplicationInfo applicationInfo) {
        Log.d("App/Util", "getMessageIcon()");
        return a(context, applicationInfo, false);
    }
}
