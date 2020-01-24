package cn.smssdk.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import cn.smssdk.contact.a.a;
import cn.smssdk.contact.a.c;
import cn.smssdk.contact.a.e;
import cn.smssdk.contact.a.g;
import cn.smssdk.contact.a.h;
import cn.smssdk.contact.a.i;
import cn.smssdk.contact.a.j;
import cn.smssdk.contact.a.k;
import cn.smssdk.contact.a.l;
import cn.smssdk.contact.a.m;
import cn.smssdk.contact.a.n;
import cn.smssdk.contact.a.o;
import cn.smssdk.contact.a.q;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import com.iwown.data_link.consts.UserConst;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.UIHandler;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class d {
    private static d a;
    private static ContentObserver b;
    /* access modifiers changed from: private */
    public Context c;
    private ContentResolver d;
    private l e;
    /* access modifiers changed from: private */
    public OnContactChangeListener f;
    /* access modifiers changed from: private */
    public m g;
    private String h;

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    private d(Context context) {
        this.c = context.getApplicationContext();
        this.d = context.getContentResolver();
        this.e = new l(context, this.d);
        c();
        this.g = new m(context, this);
        this.h = new File(context.getFilesDir(), ".cb").getAbsolutePath();
    }

    private void c() {
        if (b == null) {
            b = new e(this, new Handler());
        }
        try {
            this.d.unregisterContentObserver(b);
            this.d.registerContentObserver(Contacts.CONTENT_URI, true, b);
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
    }

    public void a() {
        a((Runnable) new f(this), (Runnable) null);
    }

    public void a(Runnable runnable, Runnable runnable2) {
        try {
            if (!DeviceHelper.getInstance(this.c).checkPermission("android.permission.READ_CONTACTS")) {
                e();
                if (runnable2 != null) {
                    runnable2.run();
                }
            } else if (!SPHelper.getInstance(this.c).isWarnWhenReadContact()) {
                SPHelper.getInstance(this.c).setAllowReadContact();
                if (runnable != null) {
                    runnable.run();
                }
            } else if (SPHelper.getInstance(this.c).isAllowReadContact()) {
                if (runnable != null) {
                    runnable.run();
                }
            } else if (a.a()) {
                a.a(runnable, runnable2);
            } else {
                a aVar = new a();
                a.a(runnable, runnable2);
                aVar.showForResult(this.c, null, new g(this));
            }
        } catch (Throwable th) {
            e();
            SMSLog.getInstance().w(th);
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }

    public void b(Runnable runnable, Runnable runnable2) {
        a((Runnable) new h(this, runnable), (Runnable) new j(this, runnable2));
    }

    /* access modifiers changed from: private */
    public synchronized void b(boolean z) {
        SMSLog.getInstance().d(">>>>>>>>> ContactHelper.onRebuild", new Object[0]);
        if (z || !new File(this.h).exists()) {
            SMSLog.getInstance().d(">>>>>>>>> ContactHelper.onRebuild.start", new Object[0]);
            ArrayList arrayList = new ArrayList();
            String str = VERSION.SDK_INT <= 10 ? "_id" : "name_raw_contact_id";
            Uri uri = VERSION.SDK_INT <= 9 ? RawContacts.CONTENT_URI : Contacts.CONTENT_URI;
            String[] strArr = {str};
            SMSLog.getInstance().d(">>>>>>>>> query: " + uri, new Object[0]);
            ArrayList a2 = this.e.a(uri, strArr, null, null, "sort_key asc");
            if (a2 != null) {
                SMSLog.getInstance().d(">>>>>>>>> found: " + a2.size() + " ids", new Object[0]);
                Iterator it = a2.iterator();
                while (it.hasNext()) {
                    String str2 = (String) ((HashMap) it.next()).get(str);
                    if (str2 != null) {
                        arrayList.add(new a(this.e, str2));
                    }
                }
            }
            SMSLog.getInstance().d(">>>>>>>>> ContactHelper.onRebuild.buffercontacts", new Object[0]);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(this.h)));
            objectOutputStream.writeInt(arrayList.size());
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                byte[] bytes = ((a) it2.next()).toString().getBytes("utf-8");
                objectOutputStream.writeInt(bytes.length);
                objectOutputStream.write(bytes);
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            SMSLog.getInstance().d(">>>>>>>>> ContactHelper.onRebuild.buffercontacts.finish", new Object[0]);
        } else {
            SMSLog.getInstance().d(">>>>>>>>> ContactHelper.onRebuild.quickfinish", new Object[0]);
        }
    }

    private ArrayList<a> d() {
        SMSLog.getInstance().d(">>>>>>>>> ContactHelper.getContacts", new Object[0]);
        if (this.h == null) {
            return new ArrayList<>();
        }
        File file = new File(this.h);
        try {
            if (!file.exists()) {
                b(false);
            } else if (file.length() <= 28) {
                b(true);
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(new GZIPInputStream(new FileInputStream(this.h)));
            int readInt = objectInputStream.readInt();
            SMSLog.getInstance().d(">>>>>>>>> found: " + readInt + " contacts", new Object[0]);
            ArrayList<a> arrayList = new ArrayList<>(readInt);
            for (int i = 0; i < readInt; i++) {
                byte[] bArr = new byte[objectInputStream.readInt()];
                objectInputStream.readFully(bArr);
                arrayList.add(new a(new String(bArr, "utf-8")));
            }
            objectInputStream.close();
            SMSLog.getInstance().d(">>>>>>>>> ContactHelper.getContacts.finish", new Object[0]);
            return arrayList;
        } catch (Throwable th) {
            if (file.exists()) {
                file.delete();
            }
            SMSLog.getInstance().w(th);
            return new ArrayList<>();
        }
    }

    public ArrayList<HashMap<String, Object>> a(boolean z) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (!DeviceHelper.getInstance(this.c).checkPermission("android.permission.READ_CONTACTS")) {
            return null;
        }
        ArrayList d2 = d();
        if (d2 == null) {
            return null;
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = d2.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            HashMap hashMap = new HashMap();
            h a2 = aVar.a();
            if (a2 != null) {
                String b2 = a2.b();
                if (!TextUtils.isEmpty(b2)) {
                    hashMap.put("prefixname", b2);
                }
                String c2 = a2.c();
                if (!TextUtils.isEmpty(c2)) {
                    hashMap.put("suffixname", c2);
                }
                String d3 = a2.d();
                if (!TextUtils.isEmpty(d3)) {
                    hashMap.put("lastname", d3);
                }
                String e2 = a2.e();
                if (!TextUtils.isEmpty(e2)) {
                    hashMap.put("firstname", e2);
                }
                String f2 = a2.f();
                if (!TextUtils.isEmpty(f2)) {
                    hashMap.put("displayname", f2);
                }
                String i = a2.i();
                if (!TextUtils.isEmpty(i)) {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("key", "phonetic");
                    ArrayList arrayList4 = new ArrayList();
                    arrayList4.add(i);
                    hashMap2.put("vals", arrayList4);
                    if (0 == 0) {
                        arrayList2 = new ArrayList();
                        hashMap.put("others", arrayList2);
                    } else {
                        arrayList2 = null;
                    }
                    arrayList2.add(hashMap2);
                } else {
                    arrayList2 = null;
                }
                String g2 = a2.g();
                if (!TextUtils.isEmpty(g2)) {
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put("key", "phoneticfirstname");
                    ArrayList arrayList5 = new ArrayList();
                    arrayList5.add(g2);
                    hashMap3.put("vals", arrayList5);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                        hashMap.put("others", arrayList2);
                    }
                    arrayList2.add(hashMap3);
                }
                String h2 = a2.h();
                if (!TextUtils.isEmpty(h2)) {
                    HashMap hashMap4 = new HashMap();
                    hashMap4.put("key", "phoneticlastname");
                    ArrayList arrayList6 = new ArrayList();
                    arrayList6.add(h2);
                    hashMap4.put("vals", arrayList6);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                        hashMap.put("others", arrayList2);
                    }
                    arrayList2.add(hashMap4);
                }
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
            i b3 = aVar.b();
            if (b3 != null) {
                String b4 = b3.b();
                if (!TextUtils.isEmpty(b4)) {
                    hashMap.put("nickname", b4);
                }
            }
            k d4 = aVar.d();
            if (d4 != null) {
                String b5 = d4.b();
                if (!TextUtils.isEmpty(b5)) {
                    hashMap.put("company", b5);
                }
                String c3 = d4.c();
                if (!TextUtils.isEmpty(c3)) {
                    hashMap.put("position", c3);
                }
            }
            ArrayList j = aVar.j();
            if (j != null) {
                Iterator it2 = j.iterator();
                ArrayList arrayList7 = null;
                while (it2.hasNext()) {
                    l lVar = (l) it2.next();
                    String b6 = lVar.b();
                    if (!TextUtils.isEmpty(b6)) {
                        if (arrayList7 == null) {
                            arrayList7 = new ArrayList();
                            hashMap.put("phones", arrayList7);
                        }
                        HashMap hashMap5 = new HashMap();
                        hashMap5.put(UserConst.PHONE, b6);
                        hashMap5.put("type", Integer.valueOf(lVar.c()));
                        hashMap5.put(SocialConstants.PARAM_APP_DESC, lVar.d());
                        arrayList7.add(hashMap5);
                    }
                }
            }
            ArrayList i2 = aVar.i();
            if (i2 != null) {
                Iterator it3 = i2.iterator();
                ArrayList arrayList8 = null;
                while (it3.hasNext()) {
                    c cVar = (c) it3.next();
                    String b7 = cVar.b();
                    if (!TextUtils.isEmpty(b7)) {
                        if (arrayList8 == null) {
                            arrayList8 = new ArrayList();
                            hashMap.put("mails", arrayList8);
                        }
                        HashMap hashMap6 = new HashMap();
                        hashMap6.put("email", b7);
                        hashMap6.put("type", Integer.valueOf(cVar.c()));
                        hashMap6.put(SocialConstants.PARAM_APP_DESC, cVar.d());
                        arrayList8.add(hashMap6);
                    }
                }
            }
            ArrayList k = aVar.k();
            if (k != null) {
                Iterator it4 = k.iterator();
                ArrayList arrayList9 = null;
                while (it4.hasNext()) {
                    n nVar = (n) it4.next();
                    String b8 = nVar.b();
                    if (!TextUtils.isEmpty(b8)) {
                        if (arrayList9 == null) {
                            arrayList9 = new ArrayList();
                            hashMap.put("addresses", arrayList9);
                        }
                        HashMap hashMap7 = new HashMap();
                        hashMap7.put("address", b8);
                        hashMap7.put("type", Integer.valueOf(nVar.c()));
                        hashMap7.put(SocialConstants.PARAM_APP_DESC, nVar.d());
                        arrayList9.add(hashMap7);
                    }
                }
            }
            ArrayList l = aVar.l();
            if (l != null) {
                Iterator it5 = l.iterator();
                ArrayList arrayList10 = null;
                while (it5.hasNext()) {
                    cn.smssdk.contact.a.d dVar = (cn.smssdk.contact.a.d) it5.next();
                    String b9 = dVar.b();
                    if (!TextUtils.isEmpty(b9)) {
                        if (arrayList10 == null) {
                            arrayList10 = new ArrayList();
                            hashMap.put("specialdate", arrayList10);
                        }
                        HashMap hashMap8 = new HashMap();
                        hashMap8.put("date", b9);
                        hashMap8.put("type", Integer.valueOf(dVar.c()));
                        hashMap8.put(SocialConstants.PARAM_APP_DESC, dVar.d());
                        arrayList10.add(hashMap8);
                    }
                }
            }
            ArrayList h3 = aVar.h();
            if (h3 != null) {
                Iterator it6 = h3.iterator();
                ArrayList arrayList11 = null;
                while (it6.hasNext()) {
                    g gVar = (g) it6.next();
                    String b10 = gVar.b();
                    if (!TextUtils.isEmpty(b10)) {
                        if (arrayList11 == null) {
                            arrayList11 = new ArrayList();
                            hashMap.put("ims", arrayList11);
                        }
                        HashMap hashMap9 = new HashMap();
                        hashMap9.put("val", b10);
                        hashMap9.put("type", Integer.valueOf(gVar.c()));
                        hashMap9.put(SocialConstants.PARAM_APP_DESC, gVar.d());
                        arrayList11.add(hashMap9);
                    }
                }
            }
            e c4 = aVar.c();
            if (c4 != null) {
                String b11 = c4.b();
                if (!TextUtils.isEmpty(b11)) {
                    hashMap.put("group", b11);
                }
            }
            j f3 = aVar.f();
            if (f3 != null) {
                String b12 = f3.b();
                if (!TextUtils.isEmpty(b12)) {
                    hashMap.put("remarks", b12);
                }
            }
            ArrayList g3 = aVar.g();
            if (g3 != null) {
                Iterator it7 = g3.iterator();
                ArrayList arrayList12 = null;
                while (it7.hasNext()) {
                    q qVar = (q) it7.next();
                    String b13 = qVar.b();
                    if (!TextUtils.isEmpty(b13)) {
                        if (arrayList12 == null) {
                            arrayList12 = new ArrayList();
                            hashMap.put("websites", arrayList12);
                        }
                        HashMap hashMap10 = new HashMap();
                        hashMap10.put("val", b13);
                        hashMap10.put("type", Integer.valueOf(qVar.c()));
                        hashMap10.put(SocialConstants.PARAM_APP_DESC, qVar.d());
                        arrayList12.add(hashMap10);
                    }
                }
            }
            ArrayList m = aVar.m();
            if (m != null) {
                Iterator it8 = m.iterator();
                ArrayList arrayList13 = null;
                while (it8.hasNext()) {
                    o oVar = (o) it8.next();
                    String b14 = oVar.b();
                    if (!TextUtils.isEmpty(b14)) {
                        if (arrayList13 == null) {
                            arrayList13 = new ArrayList();
                            hashMap.put("relations", arrayList13);
                        }
                        HashMap hashMap11 = new HashMap();
                        hashMap11.put("val", b14);
                        hashMap11.put("type", Integer.valueOf(oVar.c()));
                        hashMap11.put(SocialConstants.PARAM_APP_DESC, oVar.d());
                        arrayList13.add(hashMap11);
                    }
                }
            }
            if (z) {
                m e3 = aVar.e();
                if (e3 != null) {
                    String b15 = e3.b();
                    if (!TextUtils.isEmpty(b15)) {
                        HashMap hashMap12 = new HashMap();
                        hashMap12.put("key", "avatar");
                        ArrayList arrayList14 = new ArrayList();
                        arrayList14.add(b15);
                        hashMap12.put("vals", arrayList14);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            hashMap.put("others", arrayList);
                        }
                        arrayList.add(hashMap12);
                    }
                }
            }
            arrayList3.add(hashMap);
        }
        return arrayList3;
    }

    public String[] b() {
        ArrayList d2 = d();
        if (d2 == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Iterator it = d2.iterator();
        while (it.hasNext()) {
            ArrayList j = ((a) it.next()).j();
            if (j != null) {
                Iterator it2 = j.iterator();
                while (it2.hasNext()) {
                    String b2 = ((l) it2.next()).b();
                    if (!TextUtils.isEmpty(b2)) {
                        hashSet.add(b2);
                    }
                }
            }
        }
        String[] strArr = new String[hashSet.size()];
        int i = 0;
        Iterator it3 = hashSet.iterator();
        while (true) {
            int i2 = i;
            if (!it3.hasNext()) {
                break;
            }
            strArr[i2] = (String) it3.next();
            i = i2 + 1;
        }
        return strArr.length > 0 ? strArr : null;
    }

    public void a(OnContactChangeListener onContactChangeListener) {
        this.f = onContactChangeListener;
    }

    private void e() {
        if (SPHelper.getInstance(this.c).isWarnWhenReadContact()) {
            UIHandler.sendEmptyMessage(1, new k(this));
        }
    }
}
