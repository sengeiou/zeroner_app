package com.google.android.gms.ads.identifier;

import java.util.Map;

final class zza extends Thread {
    private /* synthetic */ Map zzanb;

    zza(AdvertisingIdClient advertisingIdClient, Map map) {
        this.zzanb = map;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r6 = this;
            com.google.android.gms.ads.identifier.zzc r0 = new com.google.android.gms.ads.identifier.zzc
            r0.<init>()
            java.util.Map r2 = r6.zzanb
            java.lang.String r0 = "https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps"
            android.net.Uri r0 = android.net.Uri.parse(r0)
            android.net.Uri$Builder r3 = r0.buildUpon()
            java.util.Set r0 = r2.keySet()
            java.util.Iterator r4 = r0.iterator()
        L_0x001a:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x0030
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r1 = r2.get(r0)
            java.lang.String r1 = (java.lang.String) r1
            r3.appendQueryParameter(r0, r1)
            goto L_0x001a
        L_0x0030:
            android.net.Uri r0 = r3.build()
            java.lang.String r2 = r0.toString()
            java.net.URL r0 = new java.net.URL     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
            r0.<init>(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
            int r1 = r0.getResponseCode()     // Catch:{ all -> 0x0082 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 < r3) goto L_0x004f
            r3 = 300(0x12c, float:4.2E-43)
            if (r1 < r3) goto L_0x007e
        L_0x004f:
            java.lang.String r3 = "HttpUrlPinger"
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0082 }
            int r4 = r4.length()     // Catch:{ all -> 0x0082 }
            int r4 = r4 + 65
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0082 }
            r5.<init>(r4)     // Catch:{ all -> 0x0082 }
            java.lang.String r4 = "Received non-success response code "
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ all -> 0x0082 }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ all -> 0x0082 }
            java.lang.String r4 = " from pinging URL: "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ all -> 0x0082 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0082 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0082 }
            android.util.Log.w(r3, r1)     // Catch:{ all -> 0x0082 }
        L_0x007e:
            r0.disconnect()     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
        L_0x0081:
            return
        L_0x0082:
            r1 = move-exception
            r0.disconnect()     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
            throw r1     // Catch:{ IndexOutOfBoundsException -> 0x0087, IOException -> 0x0104, RuntimeException -> 0x00c5 }
        L_0x0087:
            r0 = move-exception
            java.lang.String r1 = "HttpUrlPinger"
            java.lang.String r3 = r0.getMessage()
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 32
            java.lang.String r5 = java.lang.String.valueOf(r3)
            int r5 = r5.length()
            int r4 = r4 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Error while parsing ping URL: "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r4 = ". "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.w(r1, r2, r0)
            goto L_0x0081
        L_0x00c5:
            r0 = move-exception
        L_0x00c6:
            java.lang.String r1 = "HttpUrlPinger"
            java.lang.String r3 = r0.getMessage()
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 27
            java.lang.String r5 = java.lang.String.valueOf(r3)
            int r5 = r5.length()
            int r4 = r4 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Error while pinging URL: "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r4 = ". "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.w(r1, r2, r0)
            goto L_0x0081
        L_0x0104:
            r0 = move-exception
            goto L_0x00c6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.zza.run():void");
    }
}
