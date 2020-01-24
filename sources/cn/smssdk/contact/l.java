package cn.smssdk.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.mob.tools.utils.DeviceHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class l {
    private ContentResolver a;
    private Context b;

    public l(Context context, ContentResolver contentResolver) {
        this.a = contentResolver;
        this.b = context;
    }

    public ArrayList<HashMap<String, Object>> a(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        ArrayList<HashMap<String, Object>> arrayList;
        Object blob;
        try {
            if (!DeviceHelper.getInstance(this.b).checkPermission("android.permission.READ_CONTACTS")) {
                return null;
            }
        } catch (Throwable th) {
        }
        Cursor query = this.a.query(uri, strArr, str, strArr2, str2);
        if (query == null) {
            arrayList = null;
        } else if (query.getCount() == 0) {
            return null;
        } else {
            if (query.moveToFirst()) {
                ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>();
                do {
                    HashMap hashMap = new HashMap();
                    int columnCount = query.getColumnCount();
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = query.getColumnName(i);
                        try {
                            blob = query.getString(i);
                        } catch (Throwable th2) {
                            blob = query.getBlob(i);
                        }
                        hashMap.put(columnName, blob);
                    }
                    arrayList2.add(hashMap);
                } while (query.moveToNext());
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
            query.close();
        }
        return arrayList;
    }
}
