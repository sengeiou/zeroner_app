package com.loc;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: DynamicFileDBCreator */
public final class ay implements an {
    private static ay a;

    private ay() {
    }

    public static synchronized ay b() {
        ay ayVar;
        synchronized (ay.class) {
            if (a == null) {
                a = new ay();
            }
            ayVar = a;
        }
        return ayVar;
    }

    public final String a() {
        return "dafile.db";
    }

    public final void a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS file (_id integer primary key autoincrement, sname  varchar(20), fname varchar(100),md varchar(20),version varchar(20),dversion varchar(20),status varchar(20),reservedfield varchar(20));");
        } catch (Throwable th) {
            ag.a(th, "DynamicFileDBCreator", "onCreate");
        }
    }
}
