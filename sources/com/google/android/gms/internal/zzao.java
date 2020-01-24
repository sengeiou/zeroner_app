package com.google.android.gms.internal;

import com.google.common.net.HttpHeaders;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import org.apache.commons.codec.CharEncoding;

public final class zzao {
    static List<zzl> zza(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            arrayList.add(new zzl((String) entry.getKey(), (String) entry.getValue()));
        }
        return arrayList;
    }

    static Map<String, String> zza(List<zzl> list) {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (zzl zzl : list) {
            treeMap.put(zzl.getName(), zzl.getValue());
        }
        return treeMap;
    }

    public static zzc zzb(zzp zzp) {
        boolean z;
        boolean z2;
        long j;
        long j2;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zzp.zzac;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        String str = (String) map.get(HttpHeaders.DATE);
        if (str != null) {
            j3 = zzf(str);
        }
        String str2 = (String) map.get(HttpHeaders.CACHE_CONTROL);
        if (str2 != null) {
            String[] split = str2.split(",");
            z = false;
            long j6 = 0;
            long j7 = 0;
            for (String trim : split) {
                String trim2 = trim.trim();
                if (trim2.equals("no-cache") || trim2.equals("no-store")) {
                    return null;
                }
                if (trim2.startsWith("max-age=")) {
                    try {
                        j7 = Long.parseLong(trim2.substring(8));
                    } catch (Exception e) {
                    }
                } else if (trim2.startsWith("stale-while-revalidate=")) {
                    try {
                        j6 = Long.parseLong(trim2.substring(23));
                    } catch (Exception e2) {
                    }
                } else if (trim2.equals("must-revalidate") || trim2.equals("proxy-revalidate")) {
                    z = true;
                }
            }
            j4 = j7;
            j5 = j6;
            z2 = true;
        } else {
            z = false;
            z2 = false;
        }
        String str3 = (String) map.get(HttpHeaders.EXPIRES);
        long j8 = str3 != null ? zzf(str3) : 0;
        String str4 = (String) map.get(HttpHeaders.LAST_MODIFIED);
        long j9 = str4 != null ? zzf(str4) : 0;
        String str5 = (String) map.get(HttpHeaders.ETAG);
        if (z2) {
            j2 = currentTimeMillis + (1000 * j4);
            j = z ? j2 : (1000 * j5) + j2;
        } else if (j3 <= 0 || j8 < j3) {
            j = 0;
            j2 = 0;
        } else {
            j = (j8 - j3) + currentTimeMillis;
            j2 = j;
        }
        zzc zzc = new zzc();
        zzc.data = zzp.data;
        zzc.zza = str5;
        zzc.zze = j2;
        zzc.zzd = j;
        zzc.zzb = j3;
        zzc.zzc = j9;
        zzc.zzf = map;
        zzc.zzg = zzp.allHeaders;
        return zzc;
    }

    static String zzb(long j) {
        return zzo().format(new Date(j));
    }

    public static String zzb(Map<String, String> map) {
        String str = CharEncoding.ISO_8859_1;
        String str2 = (String) map.get("Content-Type");
        if (str2 != null) {
            String[] split = str2.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals("charset")) {
                    return split2[1];
                }
            }
        }
        return str;
    }

    private static long zzf(String str) {
        try {
            return zzo().parse(str).getTime();
        } catch (ParseException e) {
            zzae.zza(e, "Unable to parse dateStr: %s, falling back to 0", str);
            return 0;
        }
    }

    private static SimpleDateFormat zzo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }
}
