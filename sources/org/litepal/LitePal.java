package org.litepal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.io.File;
import org.litepal.parser.LitePalAttr;
import org.litepal.parser.LitePalParser;
import org.litepal.tablemanager.Connector;
import org.litepal.util.BaseUtility;
import org.litepal.util.Const.Config;
import org.litepal.util.SharedUtil;

public class LitePal {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void initialize(Context context) {
        LitePalApplication.sContext = context;
    }

    public static SQLiteDatabase getDatabase() {
        return Connector.getDatabase();
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void use(LitePalDB litePalDB) {
        LitePalAttr litePalAttr = LitePalAttr.getInstance();
        litePalAttr.setDbName(litePalDB.getDbName());
        litePalAttr.setVersion(litePalDB.getVersion());
        litePalAttr.setStorage(litePalDB.isExternalStorage() ? "external" : "internal");
        litePalAttr.setClassNames(litePalDB.getClassNames());
        if (!isDefaultDatabase(litePalDB.getDbName())) {
            litePalAttr.setExtraKeyName(litePalDB.getDbName());
            litePalAttr.setCases(Config.CASES_LOWER);
        }
        Connector.clearLitePalOpenHelperInstance();
    }

    public static void useDefault() {
        LitePalAttr.clearInstance();
        Connector.clearLitePalOpenHelperInstance();
    }

    public static boolean deleteDatabase(String dbName) {
        if (TextUtils.isEmpty(dbName)) {
            return false;
        }
        if (!dbName.endsWith(Config.DB_NAME_SUFFIX)) {
            dbName = dbName + Config.DB_NAME_SUFFIX;
        }
        File dbFile = LitePalApplication.getContext().getDatabasePath(dbName);
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                return result;
            }
            removeVersionInSharedPreferences(dbName);
            Connector.clearLitePalOpenHelperInstance();
            return result;
        }
        boolean result2 = new File((LitePalApplication.getContext().getExternalFilesDir("") + "/databases/") + dbName).delete();
        if (!result2) {
            return result2;
        }
        removeVersionInSharedPreferences(dbName);
        Connector.clearLitePalOpenHelperInstance();
        return result2;
    }

    private static void removeVersionInSharedPreferences(String dbName) {
        if (isDefaultDatabase(dbName)) {
            SharedUtil.removeVersion(null);
        } else {
            SharedUtil.removeVersion(dbName);
        }
    }

    private static boolean isDefaultDatabase(String dbName) {
        if (!BaseUtility.isLitePalXMLExists()) {
            return false;
        }
        if (!dbName.endsWith(Config.DB_NAME_SUFFIX)) {
            dbName = dbName + Config.DB_NAME_SUFFIX;
        }
        String defaultDbName = LitePalParser.parseLitePalConfiguration().getDbName();
        if (!defaultDbName.endsWith(Config.DB_NAME_SUFFIX)) {
            defaultDbName = defaultDbName + Config.DB_NAME_SUFFIX;
        }
        return dbName.equalsIgnoreCase(defaultDbName);
    }
}
