package coms.mediatek.ctrl.notification;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import android.util.Base64;
import com.iwown.my_module.utility.Constants;
import coms.mediatek.wearable.WearableManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlSerializer;

class NotificationMessageBody extends b {
    private String a = null;
    private String b = "";
    private String c = "";
    private String d = "";
    private ArrayList<Action> e;
    private ArrayList<Page> f;
    private String g;
    private String h;

    public static class Action {
        private String a;
        private String b;

        Action(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public String getId() {
            return this.a;
        }

        public String getName() {
            return this.b;
        }

        public void setId(String str) {
            this.a = str;
        }

        public void setName(String str) {
            this.b = str;
        }
    }

    public static class Page {
        private String a;
        private String b;

        Page(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public String getContent() {
            return this.b;
        }

        public String getTitle() {
            return this.a;
        }

        public void setContent(String str) {
            this.b = str;
        }

        public void setId(String str) {
            this.a = str;
        }
    }

    NotificationMessageBody() {
    }

    public void a(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 30, byteArrayOutputStream);
        this.a = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
    }

    public void a(Action action) {
        if (this.e == null) {
            this.e = new ArrayList<>();
        }
        this.e.add(action);
    }

    public void a(Page page) {
        if (this.f == null) {
            this.f = new ArrayList<>();
        }
        this.f.add(page);
    }

    public void a(String str) {
        this.g = str;
    }

    public void a(XmlSerializer xmlSerializer) throws IllegalArgumentException, IllegalStateException, IOException {
        xmlSerializer.startTag(null, Constants.NEW_MAP_KEY);
        if (a() != null) {
            xmlSerializer.startTag(null, "sender");
            xmlSerializer.text(a());
            xmlSerializer.endTag(null, "sender");
        }
        if (!TextUtils.isEmpty(j())) {
            xmlSerializer.startTag(null, "appId");
            xmlSerializer.text(j());
            xmlSerializer.endTag(null, "appId");
        }
        if (WearableManager.getInstance().getRemoteDeviceVersion() < 340 || e() == null) {
            if (!TextUtils.isEmpty(h())) {
                xmlSerializer.startTag(null, "title");
                xmlSerializer.cdsect(h());
                xmlSerializer.endTag(null, "title");
            }
            if (!TextUtils.isEmpty(c())) {
                xmlSerializer.startTag(null, "content");
                xmlSerializer.cdsect(c());
                xmlSerializer.endTag(null, "content");
            }
        } else {
            xmlSerializer.startTag(null, "page_num");
            xmlSerializer.text(String.valueOf(e().size()));
            xmlSerializer.endTag(null, "page_num");
            for (int i = 0; i < e().size(); i++) {
                Page page = (Page) e().get(i);
                xmlSerializer.startTag(null, "page");
                xmlSerializer.attribute("", "index", String.valueOf(i));
                if (!TextUtils.isEmpty(page.getTitle())) {
                    xmlSerializer.startTag(null, "title");
                    xmlSerializer.cdsect(page.getTitle());
                    xmlSerializer.endTag(null, "title");
                }
                if (!TextUtils.isEmpty(page.getContent())) {
                    xmlSerializer.startTag(null, "content");
                    xmlSerializer.cdsect(page.getContent());
                    xmlSerializer.endTag(null, "content");
                }
                xmlSerializer.endTag(null, "page");
            }
        }
        if (b() != 0) {
            xmlSerializer.startTag(null, "timestamp");
            xmlSerializer.text(String.valueOf(b()));
            xmlSerializer.endTag(null, "timestamp");
        }
        if (d() != null) {
            xmlSerializer.startTag(null, "action_num");
            xmlSerializer.text(String.valueOf(d().size()));
            xmlSerializer.endTag(null, "action_num");
            for (int i2 = 0; i2 < d().size(); i2++) {
                Action action = (Action) d().get(i2);
                if (!TextUtils.isEmpty(action.getId())) {
                    xmlSerializer.startTag(null, "action_id");
                    xmlSerializer.text(action.getId());
                    xmlSerializer.endTag(null, "action_id");
                }
                if (!TextUtils.isEmpty(action.getName())) {
                    xmlSerializer.startTag(null, "action_name");
                    xmlSerializer.text(action.getName());
                    xmlSerializer.endTag(null, "action_name");
                }
            }
        }
        if (f() != null && !TextUtils.isEmpty(f())) {
            xmlSerializer.startTag(null, "group_id");
            xmlSerializer.cdsect(f());
            xmlSerializer.endTag(null, "group_id");
        }
        if (!TextUtils.isEmpty(g())) {
            xmlSerializer.startTag(null, "result");
            xmlSerializer.text(g());
            xmlSerializer.endTag(null, "result");
        }
        xmlSerializer.endTag(null, Constants.NEW_MAP_KEY);
    }

    public ArrayList<Action> d() {
        return this.e;
    }

    public void d(String str) {
        this.h = str;
    }

    public ArrayList<Page> e() {
        return this.f;
    }

    public void e(String str) {
        this.b = str;
    }

    public String f() {
        return this.g;
    }

    public void f(String str) {
        this.c = str;
    }

    public String g() {
        return this.h;
    }

    public void g(String str) {
        this.d = str;
    }

    public String h() {
        return this.b;
    }

    public String i() {
        return this.c;
    }

    public String j() {
        return this.d;
    }

    public String k() {
        return this.a;
    }

    public String toString() {
        String str = ", ";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (a() != null) {
            sb.append(a());
        }
        sb.append(", ");
        if (k() != null) {
            sb.append(k());
        }
        sb.append(", ");
        if (k() != null) {
            sb.append(j());
        }
        if (WearableManager.getInstance().getRemoteDeviceVersion() < 340 || e() == null) {
            sb.append(", ");
            if (h() != null) {
                sb.append(h());
            }
            sb.append(", ");
            if (c() != null) {
                sb.append(c());
            }
        } else {
            sb.append(", ");
            if (e().size() != 0) {
                sb.append(e().size());
            }
            for (int i = 0; i < e().size(); i++) {
                Page page = (Page) e().get(i);
                sb.append(", ");
                if (page.getTitle() != null) {
                    sb.append(page.getTitle());
                }
                sb.append(", ");
                if (page.getContent() != null) {
                    sb.append(page.getContent());
                }
            }
        }
        sb.append(", ");
        if (i() != null) {
            sb.append(i());
        }
        sb.append(", ");
        if (b() != 0) {
            sb.append(String.valueOf(b()));
        }
        if (d() != null) {
            sb.append(", ");
            if (d().size() != 0) {
                sb.append(String.valueOf(d().size()));
            }
            for (int i2 = 0; i2 < d().size(); i2++) {
                Action action = (Action) d().get(i2);
                sb.append(", ");
                if (action.getId() != null) {
                    sb.append(action.getId());
                }
                sb.append(", ");
                if (action.getName() != null) {
                    sb.append(action.getName());
                }
            }
        }
        if (f() != null) {
            sb.append(", ");
            sb.append(f());
        }
        sb.append("]");
        return sb.toString();
    }
}
