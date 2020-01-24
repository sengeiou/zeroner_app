package com.facebook.stetho.inspector.database;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.protocol.module.BaseDatabaseDriver.ExecuteResultHandler;
import com.facebook.stetho.inspector.protocol.module.DatabaseDescriptor;
import com.facebook.stetho.inspector.protocol.module.DatabaseDriver2;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class SqliteDatabaseDriver extends DatabaseDriver2<SqliteDatabaseDescriptor> {
    private static final String[] UNINTERESTING_FILENAME_SUFFIXES = {"-journal", "-shm", "-uid", "-wal"};
    private final DatabaseConnectionProvider mDatabaseConnectionProvider;
    private final DatabaseFilesProvider mDatabaseFilesProvider;

    static class SqliteDatabaseDescriptor implements DatabaseDescriptor {
        public final File file;

        public SqliteDatabaseDescriptor(File file2) {
            this.file = file2;
        }

        public String name() {
            return this.file.getName();
        }
    }

    @Deprecated
    public SqliteDatabaseDriver(Context context) {
        this(context, new DefaultDatabaseFilesProvider(context), new DefaultDatabaseConnectionProvider());
    }

    @Deprecated
    public SqliteDatabaseDriver(Context context, DatabaseFilesProvider databaseFilesProvider) {
        this(context, databaseFilesProvider, new DefaultDatabaseConnectionProvider());
    }

    public SqliteDatabaseDriver(Context context, DatabaseFilesProvider databaseFilesProvider, DatabaseConnectionProvider databaseConnectionProvider) {
        super(context);
        this.mDatabaseFilesProvider = databaseFilesProvider;
        this.mDatabaseConnectionProvider = databaseConnectionProvider;
    }

    public List<SqliteDatabaseDescriptor> getDatabaseNames() {
        ArrayList<SqliteDatabaseDescriptor> databases = new ArrayList<>();
        List<File> potentialDatabaseFiles = this.mDatabaseFilesProvider.getDatabaseFiles();
        Collections.sort(potentialDatabaseFiles);
        for (File database : tidyDatabaseList(potentialDatabaseFiles)) {
            databases.add(new SqliteDatabaseDescriptor(database));
        }
        return databases;
    }

    static List<File> tidyDatabaseList(List<File> databaseFiles) {
        Set<File> originalAsSet = new HashSet<>(databaseFiles);
        List<File> tidiedList = new ArrayList<>();
        for (File databaseFile : databaseFiles) {
            String databaseFilename = databaseFile.getPath();
            String sansSuffix = removeSuffix(databaseFilename, UNINTERESTING_FILENAME_SUFFIXES);
            if (sansSuffix.equals(databaseFilename) || !originalAsSet.contains(new File(sansSuffix))) {
                tidiedList.add(databaseFile);
            }
        }
        return tidiedList;
    }

    private static String removeSuffix(String str, String[] suffixesToRemove) {
        for (String suffix : suffixesToRemove) {
            if (str.endsWith(suffix)) {
                return str.substring(0, str.length() - suffix.length());
            }
        }
        return str;
    }

    public List<String> getTableNames(SqliteDatabaseDescriptor databaseDesc) throws SQLiteException {
        Cursor cursor;
        SQLiteDatabase database = openDatabase(databaseDesc);
        try {
            cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type IN (?, ?)", new String[]{"table", "view"});
            List<String> tableNames = new ArrayList<>();
            while (cursor.moveToNext()) {
                tableNames.add(cursor.getString(0));
            }
            cursor.close();
            database.close();
            return tableNames;
        } catch (Throwable th) {
            database.close();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0069 A[Catch:{ all -> 0x0087 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0073 A[SYNTHETIC, Splitter:B:30:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007d A[SYNTHETIC, Splitter:B:33:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x001d A[Catch:{ all -> 0x0087 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse executeSQL(com.facebook.stetho.inspector.database.SqliteDatabaseDriver.SqliteDatabaseDescriptor r5, java.lang.String r6, com.facebook.stetho.inspector.protocol.module.BaseDatabaseDriver.ExecuteResultHandler<com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse> r7) throws android.database.sqlite.SQLiteException {
        /*
            r4 = this;
            com.facebook.stetho.common.Util.throwIfNull(r6)
            com.facebook.stetho.common.Util.throwIfNull(r7)
            android.database.sqlite.SQLiteDatabase r0 = r4.openDatabase(r5)
            java.lang.String r2 = getFirstWord(r6)     // Catch:{ all -> 0x0087 }
            java.lang.String r1 = r2.toUpperCase()     // Catch:{ all -> 0x0087 }
            r2 = -1
            int r3 = r1.hashCode()     // Catch:{ all -> 0x0087 }
            switch(r3) {
                case -2130463047: goto L_0x003d;
                case -1926899396: goto L_0x0053;
                case -1852692228: goto L_0x0048;
                case -1785516855: goto L_0x0027;
                case -591179561: goto L_0x005e;
                case 2012838315: goto L_0x0032;
                default: goto L_0x001a;
            }     // Catch:{ all -> 0x0087 }
        L_0x001a:
            switch(r2) {
                case 0: goto L_0x0069;
                case 1: goto L_0x0069;
                case 2: goto L_0x0073;
                case 3: goto L_0x007d;
                case 4: goto L_0x007d;
                case 5: goto L_0x007d;
                default: goto L_0x001d;
            }     // Catch:{ all -> 0x0087 }
        L_0x001d:
            java.lang.Object r2 = r4.executeRawQuery(r0, r6, r7)     // Catch:{ all -> 0x0087 }
            com.facebook.stetho.inspector.protocol.module.Database$ExecuteSQLResponse r2 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r2     // Catch:{ all -> 0x0087 }
            r0.close()
        L_0x0026:
            return r2
        L_0x0027:
            java.lang.String r3 = "UPDATE"
            boolean r3 = r1.equals(r3)     // Catch:{ all -> 0x0087 }
            if (r3 == 0) goto L_0x001a
            r2 = 0
            goto L_0x001a
        L_0x0032:
            java.lang.String r3 = "DELETE"
            boolean r3 = r1.equals(r3)     // Catch:{ all -> 0x0087 }
            if (r3 == 0) goto L_0x001a
            r2 = 1
            goto L_0x001a
        L_0x003d:
            java.lang.String r3 = "INSERT"
            boolean r3 = r1.equals(r3)     // Catch:{ all -> 0x0087 }
            if (r3 == 0) goto L_0x001a
            r2 = 2
            goto L_0x001a
        L_0x0048:
            java.lang.String r3 = "SELECT"
            boolean r3 = r1.equals(r3)     // Catch:{ all -> 0x0087 }
            if (r3 == 0) goto L_0x001a
            r2 = 3
            goto L_0x001a
        L_0x0053:
            java.lang.String r3 = "PRAGMA"
            boolean r3 = r1.equals(r3)     // Catch:{ all -> 0x0087 }
            if (r3 == 0) goto L_0x001a
            r2 = 4
            goto L_0x001a
        L_0x005e:
            java.lang.String r3 = "EXPLAIN"
            boolean r3 = r1.equals(r3)     // Catch:{ all -> 0x0087 }
            if (r3 == 0) goto L_0x001a
            r2 = 5
            goto L_0x001a
        L_0x0069:
            java.lang.Object r2 = r4.executeUpdateDelete(r0, r6, r7)     // Catch:{ all -> 0x0087 }
            com.facebook.stetho.inspector.protocol.module.Database$ExecuteSQLResponse r2 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r2     // Catch:{ all -> 0x0087 }
            r0.close()
            goto L_0x0026
        L_0x0073:
            java.lang.Object r2 = r4.executeInsert(r0, r6, r7)     // Catch:{ all -> 0x0087 }
            com.facebook.stetho.inspector.protocol.module.Database$ExecuteSQLResponse r2 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r2     // Catch:{ all -> 0x0087 }
            r0.close()
            goto L_0x0026
        L_0x007d:
            java.lang.Object r2 = r4.executeSelect(r0, r6, r7)     // Catch:{ all -> 0x0087 }
            com.facebook.stetho.inspector.protocol.module.Database$ExecuteSQLResponse r2 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r2     // Catch:{ all -> 0x0087 }
            r0.close()
            goto L_0x0026
        L_0x0087:
            r2 = move-exception
            r0.close()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.stetho.inspector.database.SqliteDatabaseDriver.executeSQL(com.facebook.stetho.inspector.database.SqliteDatabaseDriver$SqliteDatabaseDescriptor, java.lang.String, com.facebook.stetho.inspector.protocol.module.BaseDatabaseDriver$ExecuteResultHandler):com.facebook.stetho.inspector.protocol.module.Database$ExecuteSQLResponse");
    }

    private static String getFirstWord(String s) {
        String s2 = s.trim();
        int firstSpace = s2.indexOf(32);
        return firstSpace >= 0 ? s2.substring(0, firstSpace) : s2;
    }

    @TargetApi(11)
    private <T> T executeUpdateDelete(SQLiteDatabase database, String query, ExecuteResultHandler<T> handler) {
        return handler.handleUpdateDelete(database.compileStatement(query).executeUpdateDelete());
    }

    private <T> T executeInsert(SQLiteDatabase database, String query, ExecuteResultHandler<T> handler) {
        return handler.handleInsert(database.compileStatement(query).executeInsert());
    }

    private <T> T executeSelect(SQLiteDatabase database, String query, ExecuteResultHandler<T> handler) {
        Cursor cursor = database.rawQuery(query, null);
        try {
            return handler.handleSelect(cursor);
        } finally {
            cursor.close();
        }
    }

    private <T> T executeRawQuery(SQLiteDatabase database, String query, ExecuteResultHandler<T> handler) {
        database.execSQL(query);
        return handler.handleRawQuery();
    }

    private SQLiteDatabase openDatabase(SqliteDatabaseDescriptor databaseDesc) throws SQLiteException {
        Util.throwIfNull(databaseDesc);
        return this.mDatabaseConnectionProvider.openDatabase(databaseDesc.file);
    }
}
