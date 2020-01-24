package coms.mediatek.ctrl.notification;

import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

abstract class b {
    private String a = null;
    private int b = 0;
    private String c = null;

    b() {
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return this.a;
    }

    public abstract void a(XmlSerializer xmlSerializer) throws IllegalArgumentException, IllegalStateException, IOException;

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.b;
    }

    public void b(int i) {
        this.b = i;
    }

    public void b(String str) {
        this.a = str;
    }

    public String c() {
        return this.c;
    }

    public void c(String str) {
        this.c = str;
    }
}
