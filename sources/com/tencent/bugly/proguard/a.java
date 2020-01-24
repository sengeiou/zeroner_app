package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: BUGLY */
public class a {
    public static String a(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, a((String) arrayList.get(i)));
        }
        Collections.reverse(arrayList);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String str = (String) arrayList.get(i2);
            if (str.equals("list")) {
                arrayList.set(i2 - 1, "<" + ((String) arrayList.get(i2 - 1)));
                arrayList.set(0, ((String) arrayList.get(0)) + ">");
            } else if (str.equals("map")) {
                arrayList.set(i2 - 1, "<" + ((String) arrayList.get(i2 - 1)) + ",");
                arrayList.set(0, ((String) arrayList.get(0)) + ">");
            } else if (str.equals("Array")) {
                arrayList.set(i2 - 1, "<" + ((String) arrayList.get(i2 - 1)));
                arrayList.set(0, ((String) arrayList.get(0)) + ">");
            }
        }
        Collections.reverse(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append((String) it.next());
        }
        return stringBuffer.toString();
    }

    public static String a(String str) {
        if (str.equals("java.lang.Integer") || str.equals("int")) {
            return "int32";
        }
        if (str.equals("java.lang.Boolean") || str.equals("boolean")) {
            return "bool";
        }
        if (str.equals("java.lang.Byte") || str.equals("byte")) {
            return "char";
        }
        if (str.equals("java.lang.Double") || str.equals("double")) {
            return "double";
        }
        if (str.equals("java.lang.Float") || str.equals("float")) {
            return "float";
        }
        if (str.equals("java.lang.Long") || str.equals("long")) {
            return "int64";
        }
        if (str.equals("java.lang.Short") || str.equals("short")) {
            return "short";
        }
        if (str.equals("java.lang.Character")) {
            throw new IllegalArgumentException("can not support java.lang.Character");
        } else if (str.equals("java.lang.String")) {
            return "string";
        } else {
            if (str.equals("java.util.List")) {
                return "list";
            }
            if (str.equals("java.util.Map")) {
                return "map";
            }
            return str;
        }
    }
}
