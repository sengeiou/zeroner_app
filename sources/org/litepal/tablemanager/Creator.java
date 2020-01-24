package org.litepal.tablemanager;

import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.DBUtility;

class Creator extends AssociationCreator {
    public static final String TAG = "Creator";

    Creator() {
    }

    /* access modifiers changed from: protected */
    public void createOrUpgradeTable(SQLiteDatabase db, boolean force) {
        for (TableModel tableModel : getAllTableModels()) {
            createOrUpgradeTable(tableModel, db, force);
        }
    }

    /* access modifiers changed from: protected */
    public void createOrUpgradeTable(TableModel tableModel, SQLiteDatabase db, boolean force) {
        execute(getCreateTableSQLs(tableModel, db, force), db);
        giveTableSchemaACopy(tableModel.getTableName(), 0, db);
    }

    /* access modifiers changed from: protected */
    public List<String> getCreateTableSQLs(TableModel tableModel, SQLiteDatabase db, boolean force) {
        List<String> sqls = new ArrayList<>();
        if (force) {
            sqls.add(generateDropTableSQL(tableModel));
            sqls.add(generateCreateTableSQL(tableModel));
            return sqls;
        } else if (DBUtility.isTableExists(tableModel.getTableName(), db)) {
            return null;
        } else {
            sqls.add(generateCreateTableSQL(tableModel));
            return sqls;
        }
    }

    private String generateDropTableSQL(TableModel tableModel) {
        return generateDropTableSQL(tableModel.getTableName());
    }

    /* access modifiers changed from: 0000 */
    public String generateCreateTableSQL(TableModel tableModel) {
        return generateCreateTableSQL(tableModel.getTableName(), tableModel.getColumnModels(), true);
    }
}
