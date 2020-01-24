package com.mob.commons;

import android.content.Context;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.mob.tools.MobLog;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ReflectHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;
import org.apache.commons.cli.HelpFormatter;

/* compiled from: CommonNetworkHelper */
public class b extends NetworkHelper {
    private static final String[] a = {"SHARESDK", "SMSSDK", "SHAREREC", "MOBAPI"};
    private static b b;
    private DeviceHelper c;
    private HashMap<String, MobProduct> d = new HashMap<>();

    private b(Context context) {
        this.c = DeviceHelper.getInstance(context);
    }

    public static b a(Context context) {
        if (b == null) {
            b = new b(context);
        }
        return b;
    }

    public ArrayList<MobProduct> a() {
        try {
            ReflectHelper.importClass("com.mob.commons.*");
            for (String newInstance : a) {
                try {
                    MobProduct mobProduct = (MobProduct) ReflectHelper.newInstance(newInstance, new Object[0]);
                    if (mobProduct != null) {
                        this.d.put(mobProduct.getProductTag(), mobProduct);
                    }
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
            MobLog.getInstance().w(th2);
        }
        ArrayList<MobProduct> arrayList = new ArrayList<>();
        for (Entry value : this.d.entrySet()) {
            arrayList.add(value.getValue());
        }
        return arrayList;
    }

    public String a(ArrayList<MobProduct> arrayList) {
        String str;
        String str2 = this.c.getPackageName() + "/" + this.c.getAppVersionName();
        int size = arrayList.size();
        String str3 = "";
        for (int i = 0; i < size; i++) {
            try {
                if (str3.length() > 0) {
                    str = str3 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                } else {
                    str = str3;
                }
                try {
                    MobProduct mobProduct = (MobProduct) arrayList.get(i);
                    str3 = str + mobProduct.getProductTag() + "/" + mobProduct.getSdkver();
                } catch (Throwable th) {
                    str3 = str;
                }
            } catch (Throwable th2) {
            }
        }
        String str4 = "Android/" + this.c.getOSVersionInt();
        return str2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str3 + (str3.length() > 0 ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : "") + str4 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + TimeZone.getDefault().getID() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ("Lang/" + Locale.getDefault().toString().replace("-r", HelpFormatter.DEFAULT_OPT_PREFIX));
    }

    public void a(MobProduct mobProduct) {
        if (mobProduct != null && !this.d.containsKey(mobProduct.getProductTag())) {
            this.d.put(mobProduct.getProductTag(), mobProduct);
        }
    }
}
