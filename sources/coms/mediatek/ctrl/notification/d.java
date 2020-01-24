package coms.mediatek.ctrl.notification;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.my_module.utility.Constants;
import com.tencent.tauth.AuthActivity;
import coms.mediatek.ctrl.notification.NotificationMessageBody.Action;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class d {
    private c a;
    private b b;

    d() {
    }

    private void a(XmlPullParser xmlPullParser, String str, d dVar) throws XmlPullParserException, IOException {
        c a2 = dVar.a();
        b b2 = dVar.b();
        if (str.equals("category")) {
            a2.a(xmlPullParser.nextText());
        } else if (str.equals("subType")) {
            a2.b(xmlPullParser.nextText());
        } else if (str.equals("msgId")) {
            a2.a(Integer.parseInt(xmlPullParser.nextText()));
        } else if (str.equals(AuthActivity.ACTION_KEY)) {
            a2.c(xmlPullParser.nextText());
        } else if (str.equals(Constants.NEW_MAP_KEY)) {
            if (a2.b().equals("text")) {
                b2 = new NotificationMessageBody();
            } else if (a2.b().equals("action_operate")) {
                b2 = new NotificationMessageBody();
            } else if (a2.b().equals("sms")) {
                b2 = new f();
            } else if (a2.b().equals("block_sender")) {
                b2 = new NotificationMessageBody();
            } else if (a2.b().equals("missed_call")) {
                b2 = new a();
            }
            dVar.a(b2);
        } else if (b2 != null) {
            b(xmlPullParser, str, dVar);
        } else {
            Log.d("App/MessageObj", "parseHeader()");
        }
    }

    private void b(XmlPullParser xmlPullParser, String str, d dVar) throws XmlPullParserException, IOException {
        b b2 = dVar.b();
        if (str.equals("content")) {
            b2.c(xmlPullParser.nextText());
        } else if (str.equals("timestamp")) {
            b2.b(Integer.parseInt(xmlPullParser.nextText()));
        } else if (str.equals("sender")) {
            b2.b(xmlPullParser.nextText());
        } else if (str.equals("appId")) {
            ((NotificationMessageBody) b2).g(xmlPullParser.nextText());
        } else if (str.equals("icon")) {
            NotificationMessageBody notificationMessageBody = (NotificationMessageBody) b2;
            byte[] decode = Base64.decode(xmlPullParser.nextText(), 0);
            notificationMessageBody.a(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        } else if (str.equals("number")) {
            ((f) b2).a(xmlPullParser.nextText());
        } else if (str.equals("action_id")) {
            ((NotificationMessageBody) b2).a(new Action(xmlPullParser.nextText(), ""));
        } else {
            Log.d("App/MessageObj", "parseBody()");
        }
    }

    public c a() {
        return this.a;
    }

    public d a(byte[] bArr) throws XmlPullParserException, IOException {
        String str = new String(bArr);
        Charset.forName("UTF-8").encode(str);
        StringReader stringReader = new StringReader(str);
        d dVar = new d();
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(stringReader);
        c cVar = null;
        for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
            String name = newPullParser.getName();
            switch (eventType) {
                case 2:
                    if (!name.equals("header")) {
                        if (cVar == null) {
                            Log.d("App/MessageObj", "parseXml()");
                            break;
                        } else {
                            a(newPullParser, name, dVar);
                            break;
                        }
                    } else {
                        cVar = new c();
                        dVar.a(cVar);
                        break;
                    }
            }
        }
        return dVar;
    }

    public void a(b bVar) {
        this.b = bVar;
    }

    public void a(c cVar) {
        this.a = cVar;
    }

    public b b() {
        return this.b;
    }

    public byte[] c() {
        byte[] bArr = null;
        Log.d("App/MessageObj", "genXmlBuff start");
        StringWriter stringWriter = new StringWriter();
        c a2 = a();
        b b2 = b();
        XmlSerializer newSerializer = Xml.newSerializer();
        boolean z = false;
        try {
            newSerializer.setOutput(stringWriter);
            newSerializer.startDocument("UTF-8", Boolean.valueOf(true));
            newSerializer.startTag(null, "event_report");
            if (a2 != null) {
                a2.a(newSerializer);
            }
            if (b2 != null) {
                b2.a(newSerializer);
            }
            if (a2 == null || b2 == null) {
                Log.d("App/MessageObj", "genXmlBuff, header or body is null");
            }
            newSerializer.endTag(null, "event_report");
            newSerializer.endDocument();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            z = true;
        }
        if (z) {
            Log.e("App/MessageObj", "genXmlBuff, construct xml failed.");
            return bArr;
        }
        try {
            return stringWriter.toString().getBytes("UTF-8");
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            return bArr;
        }
    }
}
