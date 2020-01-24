package coms.mediatek.ctrl.notification;

import android.util.Log;
import com.tencent.tauth.AuthActivity;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

class c {
    private String a = null;
    private String b = null;
    private int c = 0;
    private String d = null;

    c() {
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return this.a;
    }

    public void a(int i) {
        this.c = i;
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(XmlSerializer xmlSerializer) throws IllegalArgumentException, IllegalStateException, IOException, e {
        if (a() == null || b() == null || c() == 0 || d() == null) {
            Log.d("App/MessageHeader", "genXmlBuff,throw NoDataException");
            throw new e();
        }
        xmlSerializer.startTag(null, "header");
        xmlSerializer.startTag(null, "category");
        xmlSerializer.text(a());
        xmlSerializer.endTag(null, "category");
        xmlSerializer.startTag(null, "subType");
        xmlSerializer.text(b());
        xmlSerializer.endTag(null, "subType");
        xmlSerializer.startTag(null, "msgId");
        xmlSerializer.text(String.valueOf(c()));
        xmlSerializer.endTag(null, "msgId");
        xmlSerializer.startTag(null, AuthActivity.ACTION_KEY);
        xmlSerializer.text(d());
        xmlSerializer.endTag(null, AuthActivity.ACTION_KEY);
        xmlSerializer.endTag(null, "header");
    }

    public String b() {
        return this.b;
    }

    public void b(String str) {
        this.b = str;
    }

    public int c() {
        return this.c;
    }

    public void c(String str) {
        this.d = str;
    }

    /* access modifiers changed from: 0000 */
    public String d() {
        return this.d;
    }

    public String toString() {
        return a() + b() + c() + d();
    }
}
