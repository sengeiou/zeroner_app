package cn.smssdk.contact.a;

import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;
import cn.smssdk.contact.l;
import cn.smssdk.utils.SMSLog;
import com.mob.tools.utils.Hashon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.litepal.util.Const.TableSchema;

public class a implements Serializable {
    private String a;
    private h b;
    private i c;
    private e d;
    private k e;
    private ArrayList<d> f;
    private m g;
    private j h;
    private ArrayList<q> i;
    private ArrayList<g> j;
    private ArrayList<c> k;
    private ArrayList<l> l;
    private ArrayList<n> m;
    private ArrayList<p> n;
    private ArrayList<o> o;
    private f p;

    public a(String str) {
        try {
            HashMap fromJson = new Hashon().fromJson(str);
            if (fromJson != null) {
                HashMap hashMap = (HashMap) fromJson.get(TableSchema.COLUMN_NAME);
                if (hashMap != null) {
                    this.b = (h) b.a(hashMap);
                }
                HashMap hashMap2 = (HashMap) fromJson.get("nickname");
                if (hashMap2 != null) {
                    this.c = (i) b.a(hashMap2);
                }
                HashMap hashMap3 = (HashMap) fromJson.get("group");
                if (hashMap3 != null) {
                    this.d = (e) b.a(hashMap3);
                }
                HashMap hashMap4 = (HashMap) fromJson.get("organization");
                if (hashMap4 != null) {
                    this.e = (k) b.a(hashMap4);
                }
                ArrayList arrayList = (ArrayList) fromJson.get("event");
                if (arrayList != null) {
                    if (this.f == null) {
                        this.f = new ArrayList<>();
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        this.f.add((d) b.a((HashMap) it.next()));
                    }
                }
                HashMap hashMap5 = (HashMap) fromJson.get("photo");
                if (hashMap5 != null) {
                    this.g = (m) b.a(hashMap5);
                }
                HashMap hashMap6 = (HashMap) fromJson.get("note");
                if (hashMap6 != null) {
                    this.h = (j) b.a(hashMap6);
                }
                ArrayList arrayList2 = (ArrayList) fromJson.get("websites");
                if (arrayList2 != null) {
                    if (this.i == null) {
                        this.i = new ArrayList<>();
                    }
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        this.i.add((q) b.a((HashMap) it2.next()));
                    }
                }
                ArrayList arrayList3 = (ArrayList) fromJson.get("ims");
                if (arrayList3 != null) {
                    if (this.j == null) {
                        this.j = new ArrayList<>();
                    }
                    Iterator it3 = arrayList3.iterator();
                    while (it3.hasNext()) {
                        this.j.add((g) b.a((HashMap) it3.next()));
                    }
                }
                ArrayList arrayList4 = (ArrayList) fromJson.get("emails");
                if (arrayList4 != null) {
                    if (this.k == null) {
                        this.k = new ArrayList<>();
                    }
                    Iterator it4 = arrayList4.iterator();
                    while (it4.hasNext()) {
                        this.k.add((c) b.a((HashMap) it4.next()));
                    }
                }
                ArrayList arrayList5 = (ArrayList) fromJson.get("phones");
                if (arrayList5 != null) {
                    if (this.l == null) {
                        this.l = new ArrayList<>();
                    }
                    Iterator it5 = arrayList5.iterator();
                    while (it5.hasNext()) {
                        this.l.add((l) b.a((HashMap) it5.next()));
                    }
                }
                ArrayList arrayList6 = (ArrayList) fromJson.get("postals");
                if (arrayList6 != null) {
                    if (this.m == null) {
                        this.m = new ArrayList<>();
                    }
                    Iterator it6 = arrayList6.iterator();
                    while (it6.hasNext()) {
                        this.m.add((n) b.a((HashMap) it6.next()));
                    }
                }
                ArrayList arrayList7 = (ArrayList) fromJson.get("sipAddresses");
                if (arrayList7 != null) {
                    if (this.n == null) {
                        this.n = new ArrayList<>();
                    }
                    Iterator it7 = arrayList7.iterator();
                    while (it7.hasNext()) {
                        this.n.add((p) b.a((HashMap) it7.next()));
                    }
                }
                ArrayList arrayList8 = (ArrayList) fromJson.get("relations");
                if (arrayList8 != null) {
                    if (this.o == null) {
                        this.o = new ArrayList<>();
                    }
                    Iterator it8 = arrayList8.iterator();
                    while (it8.hasNext()) {
                        this.o.add((o) b.a((HashMap) it8.next()));
                    }
                }
                HashMap hashMap7 = (HashMap) fromJson.get("identity");
                if (hashMap7 != null) {
                    this.p = (f) b.a(hashMap7);
                }
            }
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
    }

    public a(l lVar, String str) {
        this.a = str;
        a(lVar);
    }

    private void a(l lVar) {
        if (this.a != null) {
            String str = "raw_contact_id=" + this.a;
            ArrayList a2 = lVar.a(Data.CONTENT_URI, null, str, null, null);
            if (a2 != null) {
                Iterator it = a2.iterator();
                while (it.hasNext()) {
                    HashMap hashMap = (HashMap) it.next();
                    b a3 = b.a(hashMap);
                    if (a3 != null) {
                        if (a3 instanceof h) {
                            this.b = (h) a3;
                        } else if (a3 instanceof i) {
                            this.c = (i) a3;
                        } else if (a3 instanceof e) {
                            String str2 = "_id=" + hashMap.get("data1");
                            ArrayList a4 = lVar.a(Groups.CONTENT_URI, null, str2, null, null);
                            if (a4 != null && a4.size() > 0) {
                                HashMap hashMap2 = (HashMap) a4.get(0);
                                hashMap2.put("mimetype", "vnd.android.cursor.item/group_membership");
                                this.d = (e) b.a(hashMap2);
                            }
                        } else if (a3 instanceof k) {
                            this.e = (k) a3;
                        } else if (a3 instanceof d) {
                            if (this.f == null) {
                                this.f = new ArrayList<>();
                            }
                            this.f.add((d) a3);
                        } else if (a3 instanceof m) {
                            this.g = (m) a3;
                        } else if (a3 instanceof j) {
                            this.h = (j) a3;
                        } else if (a3 instanceof q) {
                            if (this.i == null) {
                                this.i = new ArrayList<>();
                            }
                            this.i.add((q) a3);
                        } else if (a3 instanceof g) {
                            if (this.j == null) {
                                this.j = new ArrayList<>();
                            }
                            this.j.add((g) a3);
                        } else if (a3 instanceof c) {
                            if (this.k == null) {
                                this.k = new ArrayList<>();
                            }
                            this.k.add((c) a3);
                        } else if (a3 instanceof l) {
                            if (this.l == null) {
                                this.l = new ArrayList<>();
                            }
                            this.l.add((l) a3);
                        } else if (a3 instanceof n) {
                            if (this.m == null) {
                                this.m = new ArrayList<>();
                            }
                            this.m.add((n) a3);
                        } else if (a3 instanceof o) {
                            if (this.m == null) {
                                this.o = new ArrayList<>();
                            }
                            this.o.add((o) a3);
                        } else if (a3 instanceof p) {
                            if (this.n == null) {
                                this.n = new ArrayList<>();
                            }
                            this.n.add((p) a3);
                        } else if (a3 instanceof f) {
                            this.p = (f) a3;
                        }
                    }
                }
            }
        }
    }

    public h a() {
        return this.b;
    }

    public i b() {
        return this.c;
    }

    public e c() {
        return this.d;
    }

    public k d() {
        return this.e;
    }

    public m e() {
        return this.g;
    }

    public j f() {
        return this.h;
    }

    public ArrayList<q> g() {
        return this.i;
    }

    public ArrayList<g> h() {
        return this.j;
    }

    public ArrayList<c> i() {
        return this.k;
    }

    public ArrayList<l> j() {
        return this.l;
    }

    public ArrayList<n> k() {
        return this.m;
    }

    public ArrayList<d> l() {
        return this.f;
    }

    public ArrayList<o> m() {
        return this.o;
    }

    public String toString() {
        return new Hashon().fromHashMap(n());
    }

    public HashMap<String, Object> n() {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (this.b != null) {
            hashMap.put(TableSchema.COLUMN_NAME, this.b.a());
        }
        if (this.c != null) {
            hashMap.put("nickname", this.c.a());
        }
        if (this.d != null) {
            hashMap.put("group", this.d.a());
        }
        if (this.e != null) {
            hashMap.put("organization", this.e.a());
        }
        if (this.f != null) {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.f.iterator();
            while (it.hasNext()) {
                arrayList.add(((d) it.next()).a());
            }
            hashMap.put("events", arrayList);
        }
        if (this.g != null) {
            hashMap.put("photo", this.g.a());
        }
        if (this.h != null) {
            hashMap.put("note", this.h.a());
        }
        if (this.i != null) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = this.i.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((q) it2.next()).a());
            }
            hashMap.put("websites", arrayList2);
        }
        if (this.j != null) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it3 = this.j.iterator();
            while (it3.hasNext()) {
                arrayList3.add(((g) it3.next()).a());
            }
            hashMap.put("ims", arrayList3);
        }
        if (this.k != null) {
            ArrayList arrayList4 = new ArrayList();
            Iterator it4 = this.k.iterator();
            while (it4.hasNext()) {
                arrayList4.add(((c) it4.next()).a());
            }
            hashMap.put("emails", arrayList4);
        }
        if (this.l != null) {
            ArrayList arrayList5 = new ArrayList();
            Iterator it5 = this.l.iterator();
            while (it5.hasNext()) {
                arrayList5.add(((l) it5.next()).a());
            }
            hashMap.put("phones", arrayList5);
        }
        if (this.m != null) {
            ArrayList arrayList6 = new ArrayList();
            Iterator it6 = this.m.iterator();
            while (it6.hasNext()) {
                arrayList6.add(((n) it6.next()).a());
            }
            hashMap.put("postals", arrayList6);
        }
        if (this.n != null) {
            ArrayList arrayList7 = new ArrayList();
            Iterator it7 = this.n.iterator();
            while (it7.hasNext()) {
                arrayList7.add(((p) it7.next()).a());
            }
            hashMap.put("sipAddresses", arrayList7);
        }
        if (this.o != null) {
            ArrayList arrayList8 = new ArrayList();
            Iterator it8 = this.o.iterator();
            while (it8.hasNext()) {
                arrayList8.add(((o) it8.next()).a());
            }
            hashMap.put("relations", arrayList8);
        }
        if (this.p != null) {
            hashMap.put("identity", this.p.a());
        }
        return hashMap;
    }
}
