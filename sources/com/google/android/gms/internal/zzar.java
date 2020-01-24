package com.google.android.gms.internal;

import com.google.api.client.http.HttpMethods;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLSocketFactory;

public final class zzar extends zzah {
    private final zzas zzch;
    private final SSLSocketFactory zzci;

    public zzar() {
        this(null);
    }

    private zzar(zzas zzas) {
        this(null, null);
    }

    private zzar(zzas zzas, SSLSocketFactory sSLSocketFactory) {
        this.zzch = null;
        this.zzci = null;
    }

    private static InputStream zza(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            return httpURLConnection.getErrorStream();
        }
    }

    private static void zza(HttpURLConnection httpURLConnection, zzr<?> zzr) throws IOException, zza {
        byte[] zzf = zzr.zzf();
        if (zzf != null) {
            httpURLConnection.setDoOutput(true);
            String str = "Content-Type";
            String str2 = "application/x-www-form-urlencoded; charset=";
            String valueOf = String.valueOf("UTF-8");
            httpURLConnection.addRequestProperty(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(zzf);
            dataOutputStream.close();
        }
    }

    private static List<zzl> zzc(Map<String, List<String>> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            if (entry.getKey() != null) {
                for (String zzl : (List) entry.getValue()) {
                    arrayList.add(new zzl((String) entry.getKey(), zzl));
                }
            }
        }
        return arrayList;
    }

    public final zzap zza(zzr<?> zzr, Map<String, String> map) throws IOException, zza {
        String str;
        String url = zzr.getUrl();
        HashMap hashMap = new HashMap();
        hashMap.putAll(zzr.getHeaders());
        hashMap.putAll(map);
        if (this.zzch != null) {
            str = this.zzch.zzg(url);
            if (str == null) {
                String str2 = "URL blocked by rewriter: ";
                String valueOf = String.valueOf(url);
                throw new IOException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        } else {
            str = url;
        }
        URL url2 = new URL(str);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        int zzh = zzr.zzh();
        httpURLConnection.setConnectTimeout(zzh);
        httpURLConnection.setReadTimeout(zzh);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        "https".equals(url2.getProtocol());
        for (String str3 : hashMap.keySet()) {
            httpURLConnection.addRequestProperty(str3, (String) hashMap.get(str3));
        }
        switch (zzr.getMethod()) {
            case -1:
                break;
            case 0:
                httpURLConnection.setRequestMethod("GET");
                break;
            case 1:
                httpURLConnection.setRequestMethod("POST");
                zza(httpURLConnection, zzr);
                break;
            case 2:
                httpURLConnection.setRequestMethod(HttpMethods.PUT);
                zza(httpURLConnection, zzr);
                break;
            case 3:
                httpURLConnection.setRequestMethod(HttpMethods.DELETE);
                break;
            case 4:
                httpURLConnection.setRequestMethod(HttpMethods.HEAD);
                break;
            case 5:
                httpURLConnection.setRequestMethod(HttpMethods.OPTIONS);
                break;
            case 6:
                httpURLConnection.setRequestMethod(HttpMethods.TRACE);
                break;
            case 7:
                httpURLConnection.setRequestMethod("PATCH");
                zza(httpURLConnection, zzr);
                break;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        return !(zzr.getMethod() != 4 && ((100 > responseCode || responseCode >= 200) && responseCode != 204 && responseCode != 304)) ? new zzap(responseCode, zzc(httpURLConnection.getHeaderFields())) : new zzap(responseCode, zzc(httpURLConnection.getHeaderFields()), httpURLConnection.getContentLength(), zza(httpURLConnection));
    }
}
