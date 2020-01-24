package coms.mediatek.wearable;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.util.UUID;
import org.litepal.util.Const.TableSchema;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class WearableConfig {
    private static String a = "0000FF01-0000-1000-8000-00805F9B34FF";
    private static int b = 20;
    private static int c = 509;
    private static String d = "";
    private static int e = 60;
    private static String f = "";
    private static boolean g = true;
    private static boolean h = false;
    private static boolean i = false;
    private static boolean j = true;
    private static boolean k = true;

    static String a() {
        return a;
    }

    private static void a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                return;
            }
        } while (next != 1);
    }

    private static void a(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        } else if (!xmlPullParser.getName().equals(str)) {
            throw new XmlPullParserException("Unexpected start tag: found " + xmlPullParser.getName() + ", expected " + str);
        }
    }

    static int b() {
        return b;
    }

    static int c() {
        return c;
    }

    static int d() {
        return e;
    }

    static String e() {
        return f;
    }

    static boolean f() {
        return g;
    }

    static boolean g() {
        return h;
    }

    public static String getEpoUrl() {
        return new String(LoadJniFunction.a().b());
    }

    static boolean h() {
        return i;
    }

    static boolean i() {
        return j;
    }

    public static void init(Context context, int i2) {
        Log.d("[wearable]WearableConfig", "init resID =" + i2);
        if (i2 == 0) {
            a = "0000FF01-0000-1000-8000-00805F9B34FF";
            d.a = UUID.fromString("000018A0-0000-1000-8000-00805F9B34FB");
            d.b = UUID.fromString("00002AA0-0000-1000-8000-00805F9B34FB");
            d.c = UUID.fromString("00002AA1-0000-1000-8000-00805F9B34FB");
            b = 20;
            c = 509;
            d = "";
            e = 60;
            f = "";
            g = true;
            h = false;
            i = false;
            j = true;
            k = true;
            return;
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            xmlResourceParser = context.getResources().getXml(i2);
            a(xmlResourceParser, "wearable_config");
            while (true) {
                a(xmlResourceParser);
                String name = xmlResourceParser.getName();
                if (name == null) {
                    break;
                }
                String attributeName = xmlResourceParser.getAttributeName(0);
                String attributeValue = xmlResourceParser.getAttributeValue(0);
                String str = null;
                if (xmlResourceParser.next() == 4) {
                    str = xmlResourceParser.getText();
                }
                Log.d("[wearable]WearableConfig", "tag: " + name + " value: " + attributeValue + " - " + str);
                if (TableSchema.COLUMN_NAME.equalsIgnoreCase(attributeName)) {
                    if ("bool".equals(name)) {
                        if ("wearable_debug_log".equalsIgnoreCase(attributeValue)) {
                            g = "true".equalsIgnoreCase(str);
                        } else if ("wearable_detail_debug_log".equalsIgnoreCase(attributeValue)) {
                            h = "true".equalsIgnoreCase(str);
                        } else if ("spp_a2dp_hfp_reconnect".equalsIgnoreCase(attributeValue)) {
                            i = "true".equalsIgnoreCase(str);
                        } else if ("enable_auto_reconnect".equalsIgnoreCase(attributeValue)) {
                            j = "true".equalsIgnoreCase(str);
                        } else if ("enable_spp_ack".equalsIgnoreCase(attributeValue)) {
                            k = "true".equalsIgnoreCase(str);
                        }
                    } else if ("int".equals(name)) {
                        if ("gatt_value_size_for_KK".equalsIgnoreCase(attributeValue)) {
                            b = Integer.parseInt(str);
                            if (b > 509) {
                                b = 509;
                            } else if (b < 20) {
                                b = 20;
                            }
                        } else if ("gatt_value_size_for_LMN".equalsIgnoreCase(attributeValue)) {
                            c = Integer.parseInt(str);
                            if (c > 509) {
                                c = 509;
                            } else if (c < 20) {
                                c = 20;
                            }
                        } else if ("gatt_reconnect_time".equalsIgnoreCase(attributeValue)) {
                            e = Integer.parseInt(str);
                            if (e > 3600) {
                                e = 3600;
                            } else if (e < 30) {
                                e = 30;
                            }
                        }
                    } else if ("string".equals(name)) {
                        if ("spp_uuid".equalsIgnoreCase(attributeValue)) {
                            a = str;
                        } else if ("dogp_uuid".equalsIgnoreCase(attributeValue)) {
                            d.a = UUID.fromString(str);
                        } else if ("dogp_read_uuid".equalsIgnoreCase(attributeValue)) {
                            d.b = UUID.fromString(str);
                        } else if ("dogp_write_uuid".equalsIgnoreCase(attributeValue)) {
                            d.c = UUID.fromString(str);
                        } else if ("Device_Manufacturer".equalsIgnoreCase(attributeValue)) {
                            f = str;
                        } else if ("GATT_RequestMTU_BlackList".equalsIgnoreCase(attributeValue)) {
                            d = str;
                        }
                    }
                }
            }
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        } catch (Exception e2) {
            Log.e("[wearable]WearableConfig", "loadMmsSettings caught ", e2);
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        } catch (Throwable th) {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    static boolean j() {
        return k;
    }

    static boolean k() {
        String str = Build.MODEL;
        Log.d("[wearable]WearableConfig", "isRequestMTUBlackList, model=" + str + " Manufacturer=" + Build.MANUFACTURER + " product=" + Build.PRODUCT);
        if (d == null || d.isEmpty() || str == null || str.isEmpty()) {
            return false;
        }
        String[] split = d.split(",");
        if (split.length <= 0) {
            return false;
        }
        Log.d("[wearable]WearableConfig", "isRequestMTUBlackList, list length=" + split.length);
        for (String str2 : split) {
            if (str2 != null) {
                Log.d("[wearable]WearableConfig", "isRequestMTUBlackList, item=" + str2);
                if (str2.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}
