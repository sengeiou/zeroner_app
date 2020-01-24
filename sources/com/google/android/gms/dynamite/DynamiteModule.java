package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.alibaba.android.arouter.utils.Consts;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class DynamiteModule {
    private static Boolean zzgwq;
    private static zzk zzgwr;
    private static zzm zzgws;
    private static String zzgwt;
    private static final ThreadLocal<zza> zzgwu = new ThreadLocal<>();
    private static final zzi zzgwv = new zza();
    public static final zzd zzgww = new zzb();
    private static zzd zzgwx = new zzc();
    public static final zzd zzgwy = new zzd();
    public static final zzd zzgwz = new zze();
    public static final zzd zzgxa = new zzf();
    public static final zzd zzgxb = new zzg();
    private final Context zzgxc;

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static class zza {
        public Cursor zzgxd;

        private zza() {
        }

        /* synthetic */ zza(zza zza) {
            this();
        }
    }

    static class zzb implements zzi {
        private final int zzgxe;
        private final int zzgxf = 0;

        public zzb(int i, int i2) {
            this.zzgxe = i;
        }

        public final int zzab(Context context, String str) {
            return this.zzgxe;
        }

        public final int zzc(Context context, String str, boolean z) {
            return 0;
        }
    }

    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        /* synthetic */ zzc(String str, zza zza) {
            this(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }

        /* synthetic */ zzc(String str, Throwable th, zza zza) {
            this(str, th);
        }
    }

    public interface zzd {
        zzj zza(Context context, String str, zzi zzi) throws zzc;
    }

    private DynamiteModule(Context context) {
        this.zzgxc = (Context) zzbq.checkNotNull(context);
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzm zzm) {
        try {
            return (Context) zzn.zzx(zzm.zza(zzn.zzz(context), str, i, zzn.zzz(cursor)));
        } catch (Exception e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load DynamiteLoader: ";
            String valueOf = String.valueOf(e.toString());
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
    }

    public static DynamiteModule zza(Context context, zzd zzd2, String str) throws zzc {
        zzj zza2;
        zza zza3 = (zza) zzgwu.get();
        zza zza4 = new zza(null);
        zzgwu.set(zza4);
        try {
            zza2 = zzd2.zza(context, str, zzgwv);
            Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length()).append("Considering local module ").append(str).append(":").append(zza2.zzgxg).append(" and remote module ").append(str).append(":").append(zza2.zzgxh).toString());
            if (zza2.zzgxi == 0 || ((zza2.zzgxi == -1 && zza2.zzgxg == 0) || (zza2.zzgxi == 1 && zza2.zzgxh == 0))) {
                throw new zzc("No acceptable module found. Local version is " + zza2.zzgxg + " and remote version is " + zza2.zzgxh + Consts.DOT, (zza) null);
            } else if (zza2.zzgxi == -1) {
                DynamiteModule zzad = zzad(context, str);
                if (zza4.zzgxd != null) {
                    zza4.zzgxd.close();
                }
                zzgwu.set(zza3);
                return zzad;
            } else if (zza2.zzgxi == 1) {
                DynamiteModule zza5 = zza(context, str, zza2.zzgxh);
                if (zza4.zzgxd != null) {
                    zza4.zzgxd.close();
                }
                zzgwu.set(zza3);
                return zza5;
            } else {
                throw new zzc("VersionPolicy returned invalid code:" + zza2.zzgxi, (zza) null);
            }
        } catch (zzc e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load remote module: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            if (zza2.zzgxg == 0 || zzd2.zza(context, str, new zzb(zza2.zzgxg, 0)).zzgxi != -1) {
                throw new zzc("Remote load failed. No local fallback found.", e, null);
            }
            DynamiteModule zzad2 = zzad(context, str);
            if (zza4.zzgxd != null) {
                zza4.zzgxd.close();
            }
            zzgwu.set(zza3);
            return zzad2;
        } catch (Throwable th) {
            if (zza4.zzgxd != null) {
                zza4.zzgxd.close();
            }
            zzgwu.set(zza3);
            throw th;
        }
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        Boolean bool;
        synchronized (DynamiteModule.class) {
            bool = zzgwq;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        }
        throw new zzc("Failed to determine which loading route to use.", (zza) null);
    }

    private static void zza(ClassLoader classLoader) throws zzc {
        zzm zzn;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzn = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                zzn = queryLocalInterface instanceof zzm ? (zzm) queryLocalInterface : new zzn(iBinder);
            }
            zzgws = zzn;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new zzc("Failed to instantiate dynamite loader", e, null);
        }
    }

    public static int zzab(Context context, String str) {
        try {
            String str2 = "com.google.android.gms.dynamite.descriptors.";
            String str3 = "ModuleDescriptor";
            Class loadClass = context.getApplicationContext().getClassLoader().loadClass(new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(str).length() + String.valueOf(str3).length()).append(str2).append(str).append(Consts.DOT).append(str3).toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            String valueOf = String.valueOf(declaredField.get(null));
            Log.e("DynamiteModule", new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(str).length()).append("Module descriptor id '").append(valueOf).append("' didn't match expected id '").append(str).append("'").toString());
            return 0;
        } catch (ClassNotFoundException e) {
            Log.w("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 45).append("Local module descriptor class for ").append(str).append(" not found.").toString());
            return 0;
        } catch (Exception e2) {
            String str4 = "DynamiteModule";
            String str5 = "Failed to load module descriptor class: ";
            String valueOf2 = String.valueOf(e2.getMessage());
            Log.e(str4, valueOf2.length() != 0 ? str5.concat(valueOf2) : new String(str5));
            return 0;
        }
    }

    public static int zzac(Context context, String str) {
        return zzc(context, str, false);
    }

    private static DynamiteModule zzad(Context context, String str) {
        String str2 = "DynamiteModule";
        String str3 = "Selected local version of ";
        String valueOf = String.valueOf(str);
        Log.i(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        zzk zzdf = zzdf(context);
        if (zzdf == null) {
            throw new zzc("Failed to create IDynamiteLoader.", (zza) null);
        }
        try {
            IObjectWrapper zza2 = zzdf.zza(zzn.zzz(context), str, i);
            if (zzn.zzx(zza2) != null) {
                return new DynamiteModule((Context) zzn.zzx(zza2));
            }
            throw new zzc("Failed to load remote module.", (zza) null);
        } catch (RemoteException e) {
            throw new zzc("Failed to load remote module.", e, null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003c A[SYNTHETIC, Splitter:B:21:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f3  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:35:0x0073=Splitter:B:35:0x0073, B:25:0x0044=Splitter:B:25:0x0044} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int zzc(android.content.Context r7, java.lang.String r8, boolean r9) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r1 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r1)
            java.lang.Boolean r0 = zzgwq     // Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x0035
            android.content.Context r0 = r7.getApplicationContext()     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader> r2 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class
            java.lang.String r2 = r2.getName()     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
            java.lang.Class r2 = r0.loadClass(r2)     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
            java.lang.String r0 = "sClassLoader"
            java.lang.reflect.Field r3 = r2.getDeclaredField(r0)     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
            monitor-enter(r2)     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
            r0 = 0
            java.lang.Object r0 = r3.get(r0)     // Catch:{ all -> 0x009e }
            java.lang.ClassLoader r0 = (java.lang.ClassLoader) r0     // Catch:{ all -> 0x009e }
            if (r0 == 0) goto L_0x0047
            java.lang.ClassLoader r3 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x009e }
            if (r0 != r3) goto L_0x0041
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x009e }
        L_0x0032:
            monitor-exit(r2)     // Catch:{ all -> 0x009e }
        L_0x0033:
            zzgwq = r0     // Catch:{ all -> 0x0076 }
        L_0x0035:
            monitor-exit(r1)     // Catch:{ all -> 0x0076 }
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x00f3
            int r0 = zze(r7, r8, r9)     // Catch:{ zzc -> 0x00ce }
        L_0x0040:
            return r0
        L_0x0041:
            zza(r0)     // Catch:{ zzc -> 0x00f9 }
        L_0x0044:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x009e }
            goto L_0x0032
        L_0x0047:
            java.lang.String r0 = "com.google.android.gms"
            android.content.Context r4 = r7.getApplicationContext()     // Catch:{ all -> 0x009e }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ all -> 0x009e }
            boolean r0 = r0.equals(r4)     // Catch:{ all -> 0x009e }
            if (r0 == 0) goto L_0x0063
            r0 = 0
            java.lang.ClassLoader r4 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x009e }
            r3.set(r0, r4)     // Catch:{ all -> 0x009e }
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x009e }
            goto L_0x0032
        L_0x0063:
            int r0 = zze(r7, r8, r9)     // Catch:{ zzc -> 0x0092 }
            java.lang.String r4 = zzgwt     // Catch:{ zzc -> 0x0092 }
            if (r4 == 0) goto L_0x0073
            java.lang.String r4 = zzgwt     // Catch:{ zzc -> 0x0092 }
            boolean r4 = r4.isEmpty()     // Catch:{ zzc -> 0x0092 }
            if (r4 == 0) goto L_0x0079
        L_0x0073:
            monitor-exit(r2)     // Catch:{ all -> 0x009e }
            monitor-exit(r1)     // Catch:{ all -> 0x0076 }
            goto L_0x0040
        L_0x0076:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0076 }
            throw r0
        L_0x0079:
            com.google.android.gms.dynamite.zzh r4 = new com.google.android.gms.dynamite.zzh     // Catch:{ zzc -> 0x0092 }
            java.lang.String r5 = zzgwt     // Catch:{ zzc -> 0x0092 }
            java.lang.ClassLoader r6 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ zzc -> 0x0092 }
            r4.<init>(r5, r6)     // Catch:{ zzc -> 0x0092 }
            zza(r4)     // Catch:{ zzc -> 0x0092 }
            r5 = 0
            r3.set(r5, r4)     // Catch:{ zzc -> 0x0092 }
            java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch:{ zzc -> 0x0092 }
            zzgwq = r4     // Catch:{ zzc -> 0x0092 }
            monitor-exit(r2)     // Catch:{ all -> 0x009e }
            monitor-exit(r1)     // Catch:{ all -> 0x0076 }
            goto L_0x0040
        L_0x0092:
            r0 = move-exception
            r0 = 0
            java.lang.ClassLoader r4 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x009e }
            r3.set(r0, r4)     // Catch:{ all -> 0x009e }
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x009e }
            goto L_0x0032
        L_0x009e:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x009e }
            throw r0     // Catch:{ ClassNotFoundException -> 0x00a1, IllegalAccessException -> 0x00fe, NoSuchFieldException -> 0x00fc }
        L_0x00a1:
            r0 = move-exception
        L_0x00a2:
            java.lang.String r2 = "DynamiteModule"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0076 }
            int r3 = r3.length()     // Catch:{ all -> 0x0076 }
            int r3 = r3 + 30
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0076 }
            r4.<init>(r3)     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = "Failed to load module via V2: "
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x0076 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0076 }
            android.util.Log.w(r2, r0)     // Catch:{ all -> 0x0076 }
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0076 }
            goto L_0x0033
        L_0x00ce:
            r0 = move-exception
            java.lang.String r1 = "DynamiteModule"
            java.lang.String r2 = "Failed to retrieve remote module version: "
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r3 = r0.length()
            if (r3 == 0) goto L_0x00ed
            java.lang.String r0 = r2.concat(r0)
        L_0x00e7:
            android.util.Log.w(r1, r0)
            r0 = 0
            goto L_0x0040
        L_0x00ed:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
            goto L_0x00e7
        L_0x00f3:
            int r0 = zzd(r7, r8, r9)
            goto L_0x0040
        L_0x00f9:
            r0 = move-exception
            goto L_0x0044
        L_0x00fc:
            r0 = move-exception
            goto L_0x00a2
        L_0x00fe:
            r0 = move-exception
            goto L_0x00a2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzc(android.content.Context, java.lang.String, boolean):int");
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        zzm zzm;
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        synchronized (DynamiteModule.class) {
            zzm = zzgws;
        }
        if (zzm == null) {
            throw new zzc("DynamiteLoaderV2 was not cached.", (zza) null);
        }
        zza zza2 = (zza) zzgwu.get();
        if (zza2 == null || zza2.zzgxd == null) {
            throw new zzc("No result cursor", (zza) null);
        }
        Context zza3 = zza(context.getApplicationContext(), str, i, zza2.zzgxd, zzm);
        if (zza3 != null) {
            return new DynamiteModule(zza3);
        }
        throw new zzc("Failed to get module context", (zza) null);
    }

    private static int zzd(Context context, String str, boolean z) {
        zzk zzdf = zzdf(context);
        if (zzdf == null) {
            return 0;
        }
        try {
            return zzdf.zza(zzn.zzz(context), str, z);
        } catch (RemoteException e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to retrieve remote module version: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return 0;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.gms.dynamite.zzk zzdf(android.content.Context r7) {
        /*
            r3 = 0
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r4 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r4)
            com.google.android.gms.dynamite.zzk r1 = zzgwr     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x000c
            com.google.android.gms.dynamite.zzk r1 = zzgwr     // Catch:{ all -> 0x003b }
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
        L_0x000b:
            return r1
        L_0x000c:
            com.google.android.gms.common.zzf r1 = com.google.android.gms.common.zzf.zzafy()     // Catch:{ all -> 0x003b }
            int r1 = r1.isGooglePlayServicesAvailable(r7)     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0019
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
            r1 = r3
            goto L_0x000b
        L_0x0019:
            java.lang.String r1 = "com.google.android.gms"
            r2 = 3
            android.content.Context r1 = r7.createPackageContext(r1, r2)     // Catch:{ Exception -> 0x0055 }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ Exception -> 0x0055 }
            java.lang.String r2 = "com.google.android.gms.chimera.container.DynamiteLoaderImpl"
            java.lang.Class r1 = r1.loadClass(r2)     // Catch:{ Exception -> 0x0055 }
            java.lang.Object r1 = r1.newInstance()     // Catch:{ Exception -> 0x0055 }
            android.os.IBinder r1 = (android.os.IBinder) r1     // Catch:{ Exception -> 0x0055 }
            if (r1 != 0) goto L_0x003e
            r1 = r3
        L_0x0035:
            if (r1 == 0) goto L_0x0071
            zzgwr = r1     // Catch:{ Exception -> 0x0055 }
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
            goto L_0x000b
        L_0x003b:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
            throw r1
        L_0x003e:
            java.lang.String r2 = "com.google.android.gms.dynamite.IDynamiteLoader"
            android.os.IInterface r2 = r1.queryLocalInterface(r2)     // Catch:{ Exception -> 0x0055 }
            boolean r5 = r2 instanceof com.google.android.gms.dynamite.zzk     // Catch:{ Exception -> 0x0055 }
            if (r5 == 0) goto L_0x004e
            r0 = r2
            com.google.android.gms.dynamite.zzk r0 = (com.google.android.gms.dynamite.zzk) r0     // Catch:{ Exception -> 0x0055 }
            r1 = r0
            goto L_0x0035
        L_0x004e:
            com.google.android.gms.dynamite.zzl r2 = new com.google.android.gms.dynamite.zzl     // Catch:{ Exception -> 0x0055 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0055 }
            r1 = r2
            goto L_0x0035
        L_0x0055:
            r1 = move-exception
            java.lang.String r2 = "DynamiteModule"
            java.lang.String r5 = "Failed to load IDynamiteLoader from GmsCore: "
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x003b }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x003b }
            int r6 = r1.length()     // Catch:{ all -> 0x003b }
            if (r6 == 0) goto L_0x0074
            java.lang.String r1 = r5.concat(r1)     // Catch:{ all -> 0x003b }
        L_0x006e:
            android.util.Log.e(r2, r1)     // Catch:{ all -> 0x003b }
        L_0x0071:
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
            r1 = r3
            goto L_0x000b
        L_0x0074:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x003b }
            r1.<init>(r5)     // Catch:{ all -> 0x003b }
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzdf(android.content.Context):com.google.android.gms.dynamite.zzk");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0075  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zze(android.content.Context r7, java.lang.String r8, boolean r9) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            r6 = 0
            android.content.ContentResolver r0 = r7.getContentResolver()     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            if (r9 == 0) goto L_0x0079
            java.lang.String r1 = "api_force_staging"
        L_0x000a:
            java.lang.String r2 = "content://com.google.android.gms.chimera/"
            java.lang.String r3 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            int r3 = r3.length()     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            int r3 = r3 + 1
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            int r4 = r4.length()     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            int r3 = r3 + r4
            java.lang.String r4 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            int r4 = r4.length()     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            int r3 = r3 + r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            java.lang.String r2 = "/"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x00b6, all -> 0x00b3 }
            if (r1 == 0) goto L_0x0059
            boolean r0 = r1.moveToFirst()     // Catch:{ Exception -> 0x006c }
            if (r0 != 0) goto L_0x007d
        L_0x0059:
            java.lang.String r0 = "DynamiteModule"
            java.lang.String r2 = "Failed to retrieve remote module version."
            android.util.Log.w(r0, r2)     // Catch:{ Exception -> 0x006c }
            com.google.android.gms.dynamite.DynamiteModule$zzc r0 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "Failed to connect to dynamite module ContentResolver."
            r3 = 0
            r0.<init>(r2, r3)     // Catch:{ Exception -> 0x006c }
            throw r0     // Catch:{ Exception -> 0x006c }
        L_0x006c:
            r0 = move-exception
        L_0x006d:
            boolean r2 = r0 instanceof com.google.android.gms.dynamite.DynamiteModule.zzc     // Catch:{ all -> 0x0072 }
            if (r2 == 0) goto L_0x00a9
            throw r0     // Catch:{ all -> 0x0072 }
        L_0x0072:
            r0 = move-exception
        L_0x0073:
            if (r1 == 0) goto L_0x0078
            r1.close()
        L_0x0078:
            throw r0
        L_0x0079:
            java.lang.String r1 = "api"
            goto L_0x000a
        L_0x007d:
            r0 = 0
            int r2 = r1.getInt(r0)     // Catch:{ Exception -> 0x006c }
            if (r2 <= 0) goto L_0x00a0
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r3 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r3)     // Catch:{ Exception -> 0x006c }
            r0 = 2
            java.lang.String r0 = r1.getString(r0)     // Catch:{ all -> 0x00a6 }
            zzgwt = r0     // Catch:{ all -> 0x00a6 }
            monitor-exit(r3)     // Catch:{ all -> 0x00a6 }
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r0 = zzgwu     // Catch:{ Exception -> 0x006c }
            java.lang.Object r0 = r0.get()     // Catch:{ Exception -> 0x006c }
            com.google.android.gms.dynamite.DynamiteModule$zza r0 = (com.google.android.gms.dynamite.DynamiteModule.zza) r0     // Catch:{ Exception -> 0x006c }
            if (r0 == 0) goto L_0x00a0
            android.database.Cursor r3 = r0.zzgxd     // Catch:{ Exception -> 0x006c }
            if (r3 != 0) goto L_0x00a0
            r0.zzgxd = r1     // Catch:{ Exception -> 0x006c }
            r1 = r6
        L_0x00a0:
            if (r1 == 0) goto L_0x00a5
            r1.close()
        L_0x00a5:
            return r2
        L_0x00a6:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00a6 }
            throw r0     // Catch:{ Exception -> 0x006c }
        L_0x00a9:
            com.google.android.gms.dynamite.DynamiteModule$zzc r2 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ all -> 0x0072 }
            java.lang.String r3 = "V2 version check failed"
            r4 = 0
            r2.<init>(r3, r0, r4)     // Catch:{ all -> 0x0072 }
            throw r2     // Catch:{ all -> 0x0072 }
        L_0x00b3:
            r0 = move-exception
            r1 = r6
            goto L_0x0073
        L_0x00b6:
            r0 = move-exception
            r1 = r6
            goto L_0x006d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zze(android.content.Context, java.lang.String, boolean):int");
    }

    public final Context zzaqb() {
        return this.zzgxc;
    }

    public final IBinder zzhb(String str) throws zzc {
        try {
            return (IBinder) this.zzgxc.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String str2 = "Failed to instantiate module class: ";
            String valueOf = String.valueOf(str);
            throw new zzc(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e, null);
        }
    }
}
