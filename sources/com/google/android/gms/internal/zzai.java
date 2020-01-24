package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

public class zzai implements zzm {
    private static boolean DEBUG = zzae.DEBUG;
    @Deprecated
    private zzaq zzbp;
    private final zzah zzbq;
    private zzaj zzbr;

    public zzai(zzah zzah) {
        this(zzah, new zzaj(4096));
    }

    private zzai(zzah zzah, zzaj zzaj) {
        this.zzbq = zzah;
        this.zzbp = zzah;
        this.zzbr = zzaj;
    }

    @Deprecated
    public zzai(zzaq zzaq) {
        this(zzaq, new zzaj(4096));
    }

    @Deprecated
    private zzai(zzaq zzaq, zzaj zzaj) {
        this.zzbp = zzaq;
        this.zzbq = new zzag(zzaq);
        this.zzbr = zzaj;
    }

    private static List<zzl> zza(List<zzl> list, zzc zzc) {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        if (!list.isEmpty()) {
            for (zzl name : list) {
                treeSet.add(name.getName());
            }
        }
        ArrayList arrayList = new ArrayList(list);
        if (zzc.zzg != null) {
            if (!zzc.zzg.isEmpty()) {
                for (zzl zzl : zzc.zzg) {
                    if (!treeSet.contains(zzl.getName())) {
                        arrayList.add(zzl);
                    }
                }
            }
        } else if (!zzc.zzf.isEmpty()) {
            for (Entry entry : zzc.zzf.entrySet()) {
                if (!treeSet.contains(entry.getKey())) {
                    arrayList.add(new zzl((String) entry.getKey(), (String) entry.getValue()));
                }
            }
        }
        return arrayList;
    }

    private static void zza(String str, zzr<?> zzr, zzad zzad) throws zzad {
        zzaa zzi = zzr.zzi();
        int zzh = zzr.zzh();
        try {
            zzi.zza(zzad);
            zzr.zzb(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(zzh)}));
        } catch (zzad e) {
            zzr.zzb(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(zzh)}));
            throw e;
        }
    }

    private final byte[] zza(InputStream inputStream, int i) throws IOException, zzab {
        zzat zzat = new zzat(this.zzbr, i);
        if (inputStream == null) {
            try {
                throw new zzab();
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        zzae.zza("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                this.zzbr.zza(null);
                zzat.close();
                throw th;
            }
        } else {
            byte[] zzb = this.zzbr.zzb(1024);
            while (true) {
                int read = inputStream.read(zzb);
                if (read == -1) {
                    break;
                }
                zzat.write(zzb, 0, read);
            }
            byte[] byteArray = zzat.toByteArray();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    zzae.zza("Error occurred when closing InputStream", new Object[0]);
                }
            }
            this.zzbr.zza(zzb);
            zzat.close();
            return byteArray;
        }
    }

    /* JADX INFO: used method not loaded: com.google.android.gms.internal.zzp.<init>(int, byte[], boolean, long, java.util.List):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0067, code lost:
        zza("socket", r21, new com.google.android.gms.internal.zzac());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ec, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ed, code lost:
        r3 = r2;
        r5 = "Bad URL ";
        r2 = java.lang.String.valueOf(r21.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ff, code lost:
        if (r2.length() != 0) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0101, code lost:
        r2 = r5.concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0108, code lost:
        throw new java.lang.RuntimeException(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x011f, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0120, code lost:
        r3 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0124, code lost:
        r3 = r3.getStatusCode();
        com.google.android.gms.internal.zzae.zzc("Unexpected response code %d for %s", java.lang.Integer.valueOf(r3), r21.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x013f, code lost:
        if (r4 != null) goto L_0x0141;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0141, code lost:
        r2 = new com.google.android.gms.internal.zzp(r3, r4, false, android.os.SystemClock.elapsedRealtime() - r18, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x014f, code lost:
        if (r3 == 401) goto L_0x0155;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0155, code lost:
        zza("auth", r21, new com.google.android.gms.internal.zza(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0164, code lost:
        r2 = new java.lang.String(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x016f, code lost:
        throw new com.google.android.gms.internal.zzq(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0172, code lost:
        if (r3 < 400) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x017d, code lost:
        throw new com.google.android.gms.internal.zzg(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0180, code lost:
        if (r3 < 500) goto L_0x018c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x018b, code lost:
        throw new com.google.android.gms.internal.zzab(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0191, code lost:
        throw new com.google.android.gms.internal.zzab(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0192, code lost:
        zza("network", r21, new com.google.android.gms.internal.zzo());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01a5, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01a6, code lost:
        r4 = null;
        r3 = r17;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0066 A[ExcHandler: SocketTimeoutException (e java.net.SocketTimeoutException), Splitter:B:2:0x000a] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ec A[ExcHandler: MalformedURLException (r2v5 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:2:0x000a] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x016a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzp zzc(com.google.android.gms.internal.zzr<?> r21) throws com.google.android.gms.internal.zzad {
        /*
            r20 = this;
            long r18 = android.os.SystemClock.elapsedRealtime()
        L_0x0004:
            r3 = 0
            r9 = 0
            java.util.List r8 = java.util.Collections.emptyList()
            com.google.android.gms.internal.zzc r4 = r21.zze()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            if (r4 != 0) goto L_0x0040
            java.util.Map r2 = java.util.Collections.emptyMap()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
        L_0x0014:
            r0 = r20
            com.google.android.gms.internal.zzah r4 = r0.zzbq     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            r0 = r21
            com.google.android.gms.internal.zzap r17 = r4.zza(r0, r2)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            int r3 = r17.getStatusCode()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            java.util.List r8 = r17.zzp()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            r2 = 304(0x130, float:4.26E-43)
            if (r3 != r2) goto L_0x008b
            com.google.android.gms.internal.zzc r2 = r21.zze()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            if (r2 != 0) goto L_0x0075
            com.google.android.gms.internal.zzp r2 = new com.google.android.gms.internal.zzp     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            r3 = 304(0x130, float:4.26E-43)
            r4 = 0
            r5 = 1
            long r6 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            long r6 = r6 - r18
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
        L_0x003f:
            return r2
        L_0x0040:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            r2.<init>()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            java.lang.String r5 = r4.zza     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            if (r5 == 0) goto L_0x0051
            java.lang.String r5 = "If-None-Match"
            java.lang.String r6 = r4.zza     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            r2.put(r5, r6)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
        L_0x0051:
            long r6 = r4.zzc     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            r10 = 0
            int r5 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r5 <= 0) goto L_0x0014
            java.lang.String r5 = "If-Modified-Since"
            long r6 = r4.zzc     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            java.lang.String r4 = com.google.android.gms.internal.zzao.zzb(r6)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            r2.put(r5, r4)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a1 }
            goto L_0x0014
        L_0x0066:
            r2 = move-exception
            java.lang.String r2 = "socket"
            com.google.android.gms.internal.zzac r3 = new com.google.android.gms.internal.zzac
            r3.<init>()
            r0 = r21
            zza(r2, r0, r3)
            goto L_0x0004
        L_0x0075:
            java.util.List r16 = zza(r8, r2)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            com.google.android.gms.internal.zzp r10 = new com.google.android.gms.internal.zzp     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            r11 = 304(0x130, float:4.26E-43)
            byte[] r12 = r2.data     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            r13 = 1
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            long r14 = r2 - r18
            r10.<init>(r11, r12, r13, r14, r16)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            r2 = r10
            goto L_0x003f
        L_0x008b:
            java.io.InputStream r2 = r17.getContent()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            if (r2 == 0) goto L_0x0109
            int r4 = r17.getContentLength()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            r0 = r20
            byte[] r4 = r0.zza(r2, r4)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
        L_0x009b:
            long r6 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            long r6 = r6 - r18
            boolean r2 = DEBUG     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            if (r2 != 0) goto L_0x00ab
            r10 = 3000(0xbb8, double:1.482E-320)
            int r2 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r2 <= 0) goto L_0x00de
        L_0x00ab:
            java.lang.String r5 = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]"
            r2 = 5
            java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r2 = 0
            r9[r2] = r21     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r2 = 1
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r9[r2] = r6     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r6 = 2
            if (r4 == 0) goto L_0x010d
            int r2 = r4.length     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
        L_0x00c3:
            r9[r6] = r2     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r2 = 3
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r9[r2] = r6     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r2 = 4
            com.google.android.gms.internal.zzaa r6 = r21.zzi()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            int r6 = r6.zzc()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r9[r2] = r6     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            com.google.android.gms.internal.zzae.zzb(r5, r9)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
        L_0x00de:
            r2 = 200(0xc8, float:2.8E-43)
            if (r3 < r2) goto L_0x00e6
            r2 = 299(0x12b, float:4.19E-43)
            if (r3 <= r2) goto L_0x0111
        L_0x00e6:
            java.io.IOException r2 = new java.io.IOException     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r2.<init>()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            throw r2     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
        L_0x00ec:
            r2 = move-exception
            r3 = r2
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.String r5 = "Bad URL "
            java.lang.String r2 = r21.getUrl()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r6 = r2.length()
            if (r6 == 0) goto L_0x0164
            java.lang.String r2 = r5.concat(r2)
        L_0x0105:
            r4.<init>(r2, r3)
            throw r4
        L_0x0109:
            r2 = 0
            byte[] r4 = new byte[r2]     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x01a5 }
            goto L_0x009b
        L_0x010d:
            java.lang.String r2 = "null"
            goto L_0x00c3
        L_0x0111:
            com.google.android.gms.internal.zzp r2 = new com.google.android.gms.internal.zzp     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            r5 = 0
            long r6 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            long r6 = r6 - r18
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ SocketTimeoutException -> 0x0066, MalformedURLException -> 0x00ec, IOException -> 0x011f }
            goto L_0x003f
        L_0x011f:
            r2 = move-exception
            r3 = r17
        L_0x0122:
            if (r3 == 0) goto L_0x016a
            int r3 = r3.getStatusCode()
            java.lang.String r2 = "Unexpected response code %d for %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r6 = 0
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            r5[r6] = r7
            r6 = 1
            java.lang.String r7 = r21.getUrl()
            r5[r6] = r7
            com.google.android.gms.internal.zzae.zzc(r2, r5)
            if (r4 == 0) goto L_0x0192
            com.google.android.gms.internal.zzp r2 = new com.google.android.gms.internal.zzp
            r5 = 0
            long r6 = android.os.SystemClock.elapsedRealtime()
            long r6 = r6 - r18
            r2.<init>(r3, r4, r5, r6, r8)
            r4 = 401(0x191, float:5.62E-43)
            if (r3 == r4) goto L_0x0155
            r4 = 403(0x193, float:5.65E-43)
            if (r3 != r4) goto L_0x0170
        L_0x0155:
            java.lang.String r3 = "auth"
            com.google.android.gms.internal.zza r4 = new com.google.android.gms.internal.zza
            r4.<init>(r2)
            r0 = r21
            zza(r3, r0, r4)
            goto L_0x0004
        L_0x0164:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r5)
            goto L_0x0105
        L_0x016a:
            com.google.android.gms.internal.zzq r3 = new com.google.android.gms.internal.zzq
            r3.<init>(r2)
            throw r3
        L_0x0170:
            r4 = 400(0x190, float:5.6E-43)
            if (r3 < r4) goto L_0x017e
            r4 = 499(0x1f3, float:6.99E-43)
            if (r3 > r4) goto L_0x017e
            com.google.android.gms.internal.zzg r3 = new com.google.android.gms.internal.zzg
            r3.<init>(r2)
            throw r3
        L_0x017e:
            r4 = 500(0x1f4, float:7.0E-43)
            if (r3 < r4) goto L_0x018c
            r4 = 599(0x257, float:8.4E-43)
            if (r3 > r4) goto L_0x018c
            com.google.android.gms.internal.zzab r3 = new com.google.android.gms.internal.zzab
            r3.<init>(r2)
            throw r3
        L_0x018c:
            com.google.android.gms.internal.zzab r3 = new com.google.android.gms.internal.zzab
            r3.<init>(r2)
            throw r3
        L_0x0192:
            java.lang.String r2 = "network"
            com.google.android.gms.internal.zzo r3 = new com.google.android.gms.internal.zzo
            r3.<init>()
            r0 = r21
            zza(r2, r0, r3)
            goto L_0x0004
        L_0x01a1:
            r2 = move-exception
            r4 = r9
            goto L_0x0122
        L_0x01a5:
            r2 = move-exception
            r4 = r9
            r3 = r17
            goto L_0x0122
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzai.zzc(com.google.android.gms.internal.zzr):com.google.android.gms.internal.zzp");
    }
}
