package com.tencent.bugly.proguard;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.a;
import com.tencent.bugly.crashreport.common.info.b;
import java.io.File;
import java.util.List;

/* compiled from: BUGLY */
public class af extends SQLiteOpenHelper {
    public static String a = "bugly_db";
    public static int b = 14;
    protected Context c;
    private List<a> d;

    public af(Context context, List<a> list) {
        StringBuilder append = new StringBuilder().append(a).append("_");
        com.tencent.bugly.crashreport.common.info.a.a(context).getClass();
        super(context, append.append("").toString(), null, b);
        this.c = context;
        this.d = list;
    }

    public synchronized void onCreate(SQLiteDatabase db) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS ").append("t_ui").append(" ( ").append("_id").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("INTEGER PRIMARY KEY").append(" , ").append("_tm").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_ut").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_tp").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_dt").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("blob").append(" , ").append("_pc").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("text").append(" ) ");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS ").append("t_lr").append(" ( ").append("_id").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("INTEGER PRIMARY KEY").append(" , ").append("_tp").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_tm").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_pc").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("text").append(" , ").append("_th").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("text").append(" , ").append("_dt").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("blob").append(" ) ");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS ").append("t_pf").append(" ( ").append("_id").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("integer").append(" , ").append("_tp").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("text").append(" , ").append("_tm").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_dt").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("blob").append(",primary key(").append("_id").append(",").append("_tp").append(" )) ");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS ").append("t_cr").append(" ( ").append("_id").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("INTEGER PRIMARY KEY").append(" , ").append("_tm").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_s1").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("text").append(" , ").append("_up").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_me").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_uc").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_dt").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("blob").append(" ) ");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS ").append("dl_1002").append(" (").append("_id").append(" integer primary key autoincrement, ").append("_dUrl").append(" varchar(100), ").append("_sFile").append(" varchar(100), ").append("_sLen").append(" INTEGER, ").append("_tLen").append(" INTEGER, ").append("_MD5").append(" varchar(100), ").append("_DLTIME").append(" INTEGER)");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append("CREATE TABLE IF NOT EXISTS ").append("ge_1002").append(" (").append("_id").append(" integer primary key autoincrement, ").append("_time").append(" INTEGER, ").append("_datas").append(" blob)");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS ").append("st_1002").append(" ( ").append("_id").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("integer").append(" , ").append("_tp").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("text").append(" , ").append("_tm").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("int").append(" , ").append("_dt").append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append("blob").append(",primary key(").append("_id").append(",").append("_tp").append(" )) ");
            an.c(sb.toString(), new Object[0]);
            db.execSQL(sb.toString(), new String[0]);
        } catch (Throwable th) {
            if (!an.b(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
        if (this.d != null) {
            for (a onDbCreate : this.d) {
                try {
                    onDbCreate.onDbCreate(db);
                } catch (Throwable th2) {
                    if (!an.b(th2)) {
                        ThrowableExtension.printStackTrace(th2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public synchronized boolean a(SQLiteDatabase sQLiteDatabase) {
        boolean z = true;
        synchronized (this) {
            try {
                String[] strArr = {"t_lr", "t_ui", "t_pf"};
                int length = strArr.length;
                for (int i = 0; i < length; i++) {
                    sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + strArr[i], new String[0]);
                }
            } catch (Throwable th) {
                if (!an.b(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
                z = false;
            }
        }
        return z;
    }

    public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        an.d("[Database] Upgrade %d to %d , drop tables!", Integer.valueOf(oldVersion), Integer.valueOf(newVersion));
        if (this.d != null) {
            for (a onDbUpgrade : this.d) {
                try {
                    onDbUpgrade.onDbUpgrade(db, oldVersion, newVersion);
                } catch (Throwable th) {
                    if (!an.b(th)) {
                        ThrowableExtension.printStackTrace(th);
                    }
                }
            }
        }
        if (a(db)) {
            onCreate(db);
        } else {
            an.d("[Database] Failed to drop, delete db.", new Object[0]);
            File databasePath = this.c.getDatabasePath(a);
            if (databasePath != null && databasePath.canWrite()) {
                databasePath.delete();
            }
        }
    }

    @TargetApi(11)
    public synchronized void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (b.c() >= 11) {
            an.d("[Database] Downgrade %d to %d drop tables.", Integer.valueOf(oldVersion), Integer.valueOf(newVersion));
            if (this.d != null) {
                for (a onDbDowngrade : this.d) {
                    try {
                        onDbDowngrade.onDbDowngrade(db, oldVersion, newVersion);
                    } catch (Throwable th) {
                        if (!an.b(th)) {
                            ThrowableExtension.printStackTrace(th);
                        }
                    }
                }
            }
            if (a(db)) {
                onCreate(db);
            } else {
                an.d("[Database] Failed to drop, delete db.", new Object[0]);
                File databasePath = this.c.getDatabasePath(a);
                if (databasePath != null && databasePath.canWrite()) {
                    databasePath.delete();
                }
            }
        }
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase sQLiteDatabase;
        int i = 0;
        synchronized (this) {
            sQLiteDatabase = null;
            while (sQLiteDatabase == null && i < 5) {
                i++;
                try {
                    sQLiteDatabase = super.getReadableDatabase();
                } catch (Throwable th) {
                    an.d("[Database] Try to get db(count: %d).", Integer.valueOf(i));
                    if (i == 5) {
                        an.e("[Database] Failed to get db.", new Object[0]);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        }
        return sQLiteDatabase;
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase sQLiteDatabase;
        int i = 0;
        synchronized (this) {
            sQLiteDatabase = null;
            while (sQLiteDatabase == null && i < 5) {
                i++;
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (Throwable th) {
                    an.d("[Database] Try to get db(count: %d).", Integer.valueOf(i));
                    if (i == 5) {
                        an.e("[Database] Failed to get db.", new Object[0]);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
            if (sQLiteDatabase == null) {
                an.d("[Database] db error delay error record 1min.", new Object[0]);
            }
        }
        return sQLiteDatabase;
    }
}
