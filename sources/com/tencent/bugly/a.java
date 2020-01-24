package com.tencent.bugly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.an;

/* compiled from: BUGLY */
public abstract class a {
    public int id;
    public String moduleName;
    public String version;
    public String versionKey;

    public abstract String[] getTables();

    public abstract void init(Context context, boolean z, BuglyStrategy buglyStrategy);

    public void onDbCreate(SQLiteDatabase db) {
    }

    public void onDbUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (getTables() != null) {
                String[] tables = getTables();
                int length = tables.length;
                for (int i = 0; i < length; i++) {
                    db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
                }
                onDbCreate(db);
            }
        } catch (Throwable th) {
            if (!an.b(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    public void onDbDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (getTables() != null) {
                String[] tables = getTables();
                int length = tables.length;
                for (int i = 0; i < length; i++) {
                    db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
                }
                onDbCreate(db);
            }
        } catch (Throwable th) {
            if (!an.b(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    public void onServerStrategyChanged(StrategyBean strategy) {
    }
}
