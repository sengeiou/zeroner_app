package coms.mediatek.ctrl.notification;

import com.iwown.my_module.utility.Constants;
import coms.mediatek.wearable.WearableManager;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

class f extends b {
    private String a = null;

    f() {
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(XmlSerializer xmlSerializer) throws IllegalArgumentException, IllegalStateException, IOException {
        xmlSerializer.startTag(null, Constants.NEW_MAP_KEY);
        if (a() != null) {
            xmlSerializer.startTag(null, "sender");
            xmlSerializer.text(a());
            xmlSerializer.endTag(null, "sender");
        }
        if (d() != null) {
            xmlSerializer.startTag(null, "number");
            xmlSerializer.text(d());
            xmlSerializer.endTag(null, "number");
        }
        if (c() != null) {
            if (WearableManager.getInstance().getRemoteDeviceVersion() >= 340) {
                xmlSerializer.startTag(null, "page_num");
                xmlSerializer.text("1");
                xmlSerializer.endTag(null, "page_num");
                xmlSerializer.startTag(null, "page");
                xmlSerializer.attribute("", "index", "1");
                xmlSerializer.startTag(null, "content");
                xmlSerializer.cdsect(c());
                xmlSerializer.endTag(null, "content");
                xmlSerializer.endTag(null, "page");
            } else {
                xmlSerializer.startTag(null, "content");
                xmlSerializer.text(c());
                xmlSerializer.endTag(null, "content");
            }
        }
        if (b() != 0) {
            xmlSerializer.startTag(null, "timestamp");
            xmlSerializer.text(String.valueOf(b()));
            xmlSerializer.endTag(null, "timestamp");
        }
        xmlSerializer.endTag(null, Constants.NEW_MAP_KEY);
    }

    public String d() {
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
        if (d() != null) {
            sb.append(d());
        }
        sb.append(", ");
        if (c() != null) {
            sb.append(c());
        }
        sb.append(", ");
        if (b() != 0) {
            sb.append(String.valueOf(b()));
        }
        sb.append("]");
        return sb.toString();
    }
}
