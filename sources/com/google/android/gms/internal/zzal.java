package com.google.android.gms.internal;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzal implements zzb {
    private final Map<String, zzam> zzbx;
    private long zzby;
    private final File zzbz;
    private final int zzca;

    public zzal(File file) {
        this(file, 5242880);
    }

    private zzal(File file, int i) {
        this.zzbx = new LinkedHashMap(16, 0.75f, true);
        this.zzby = 0;
        this.zzbz = file;
        this.zzca = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean delete = zze(str).delete();
        removeEntry(str);
        if (!delete) {
            zzae.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private final void removeEntry(String str) {
        zzam zzam = (zzam) this.zzbx.remove(str);
        if (zzam != null) {
            this.zzby -= zzam.zzcb;
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    private static InputStream zza(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    static String zza(zzan zzan) throws IOException {
        return new String(zza(zzan, zzc(zzan)), "UTF-8");
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzam zzam) {
        if (!this.zzbx.containsKey(str)) {
            this.zzby += zzam.zzcb;
        } else {
            zzam zzam2 = (zzam) this.zzbx.get(str);
            this.zzby = (zzam.zzcb - zzam2.zzcb) + this.zzby;
        }
        this.zzbx.put(str, zzam);
    }

    private static byte[] zza(zzan zzan, long j) throws IOException {
        long zzn = zzan.zzn();
        if (j < 0 || j > zzn || ((long) ((int) j)) != j) {
            throw new IOException("streamToBytes length=" + j + ", maxLength=" + zzn);
        }
        byte[] bArr = new byte[((int) j)];
        new DataInputStream(zzan).readFully(bArr);
        return bArr;
    }

    static int zzb(InputStream inputStream) throws IOException {
        return zza(inputStream) | 0 | (zza(inputStream) << 8) | (zza(inputStream) << 16) | (zza(inputStream) << 24);
    }

    static List<zzl> zzb(zzan zzan) throws IOException {
        int zzb = zzb((InputStream) zzan);
        List<zzl> arrayList = zzb == 0 ? Collections.emptyList() : new ArrayList<>(zzb);
        for (int i = 0; i < zzb; i++) {
            arrayList.add(new zzl(zza(zzan).intern(), zza(zzan).intern()));
        }
        return arrayList;
    }

    static long zzc(InputStream inputStream) throws IOException {
        return 0 | (((long) zza(inputStream)) & 255) | ((((long) zza(inputStream)) & 255) << 8) | ((((long) zza(inputStream)) & 255) << 16) | ((((long) zza(inputStream)) & 255) << 24) | ((((long) zza(inputStream)) & 255) << 32) | ((((long) zza(inputStream)) & 255) << 40) | ((((long) zza(inputStream)) & 255) << 48) | ((((long) zza(inputStream)) & 255) << 56);
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final File zze(String str) {
        return new File(this.zzbz, zzd(str));
    }

    public final synchronized void initialize() {
        zzan zzan;
        if (this.zzbz.exists()) {
            File[] listFiles = this.zzbz.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    try {
                        long length = file.length();
                        zzan = new zzan(new BufferedInputStream(zza(file)), length);
                        zzam zzc = zzam.zzc(zzan);
                        zzc.zzcb = length;
                        zza(zzc.key, zzc);
                        zzan.close();
                    } catch (IOException e) {
                        file.delete();
                    } catch (Throwable th) {
                        zzan.close();
                        throw th;
                    }
                }
            }
        } else if (!this.zzbz.mkdirs()) {
            zzae.zzc("Unable to create cache dir %s", this.zzbz.getAbsolutePath());
        }
    }

    public final synchronized zzc zza(String str) {
        zzc zzc;
        zzan zzan;
        zzam zzam = (zzam) this.zzbx.get(str);
        if (zzam == null) {
            zzc = null;
        } else {
            File zze = zze(str);
            try {
                zzan = new zzan(new BufferedInputStream(zza(zze)), zze.length());
                zzam zzc2 = zzam.zzc(zzan);
                if (!TextUtils.equals(str, zzc2.key)) {
                    zzae.zzb("%s: key=%s, found=%s", zze.getAbsolutePath(), str, zzc2.key);
                    removeEntry(str);
                    zzan.close();
                    zzc = null;
                } else {
                    byte[] zza = zza(zzan, zzan.zzn());
                    zzc zzc3 = new zzc();
                    zzc3.data = zza;
                    zzc3.zza = zzam.zza;
                    zzc3.zzb = zzam.zzb;
                    zzc3.zzc = zzam.zzc;
                    zzc3.zzd = zzam.zzd;
                    zzc3.zze = zzam.zze;
                    zzc3.zzf = zzao.zza(zzam.zzg);
                    zzc3.zzg = Collections.unmodifiableList(zzam.zzg);
                    zzan.close();
                    zzc = zzc3;
                }
            } catch (IOException e) {
                zzae.zzb("%s: %s", zze.getAbsolutePath(), e.toString());
                remove(str);
                zzc = null;
            } catch (Throwable th) {
                zzan.close();
                throw th;
            }
        }
        return zzc;
    }

    public final synchronized void zza(String str, zzc zzc) {
        int i;
        int i2 = 0;
        synchronized (this) {
            int length = zzc.data.length;
            if (this.zzby + ((long) length) >= ((long) this.zzca)) {
                if (zzae.DEBUG) {
                    zzae.zza("Pruning old cache entries.", new Object[0]);
                }
                long j = this.zzby;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator it = this.zzbx.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        i = i2;
                        break;
                    }
                    zzam zzam = (zzam) ((Entry) it.next()).getValue();
                    if (zze(zzam.key).delete()) {
                        this.zzby -= zzam.zzcb;
                    } else {
                        zzae.zzb("Could not delete cache entry for key=%s, filename=%s", zzam.key, zzd(zzam.key));
                    }
                    it.remove();
                    i = i2 + 1;
                    if (((float) (this.zzby + ((long) length))) < ((float) this.zzca) * 0.9f) {
                        break;
                    }
                    i2 = i;
                }
                if (zzae.DEBUG) {
                    zzae.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i), Long.valueOf(this.zzby - j), Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
                }
            }
            File zze = zze(str);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zze));
                zzam zzam2 = new zzam(str, zzc);
                if (!zzam2.zza(bufferedOutputStream)) {
                    bufferedOutputStream.close();
                    zzae.zzb("Failed to write header for %s", zze.getAbsolutePath());
                    throw new IOException();
                }
                bufferedOutputStream.write(zzc.data);
                bufferedOutputStream.close();
                zza(str, zzam2);
            } catch (IOException e) {
                if (!zze.delete()) {
                    zzae.zzb("Could not clean up file %s", zze.getAbsolutePath());
                }
            }
        }
    }
}
