package org.litepal.crud;

import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

class QueryHandler extends DataHandler {
    QueryHandler(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    /* access modifiers changed from: 0000 */
    public <T> T onFind(Class<T> modelClass, long id, boolean isEager) {
        Class<T> cls = modelClass;
        List<T> dataList = query(cls, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null, getForeignKeyAssociations(modelClass.getName(), isEager));
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public <T> T onFindFirst(Class<T> modelClass, boolean isEager) {
        Class<T> cls = modelClass;
        List<T> dataList = query(cls, null, null, null, null, null, "id", "1", getForeignKeyAssociations(modelClass.getName(), isEager));
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public <T> T onFindLast(Class<T> modelClass, boolean isEager) {
        Class<T> cls = modelClass;
        List<T> dataList = query(cls, null, null, null, null, null, "id desc", "1", getForeignKeyAssociations(modelClass.getName(), isEager));
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public <T> List<T> onFindAll(Class<T> modelClass, boolean isEager, long... ids) {
        if (isAffectAllLines(ids)) {
            return query(modelClass, null, null, null, null, null, "id", null, getForeignKeyAssociations(modelClass.getName(), isEager));
        }
        return query(modelClass, null, getWhereOfIdsWithOr(ids), null, null, null, "id", null, getForeignKeyAssociations(modelClass.getName(), isEager));
    }

    /* access modifiers changed from: 0000 */
    public <T> List<T> onFind(Class<T> modelClass, String[] columns, String[] conditions, String orderBy, String limit, boolean isEager) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        String orderBy2 = DBUtility.convertOrderByClauseToValidName(orderBy);
        return query(modelClass, columns, getWhereClause(conditions), getWhereArgs(conditions), null, null, orderBy2, limit, getForeignKeyAssociations(modelClass.getName(), isEager));
    }

    /* access modifiers changed from: 0000 */
    public int onCount(String tableName, String[] conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return ((Integer) mathQuery(tableName, new String[]{"count(1)"}, conditions, Integer.TYPE)).intValue();
    }

    /* access modifiers changed from: 0000 */
    public double onAverage(String tableName, String column, String[] conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return ((Double) mathQuery(tableName, new String[]{"avg(" + column + ")"}, conditions, Double.TYPE)).doubleValue();
    }

    /* access modifiers changed from: 0000 */
    public <T> T onMax(String tableName, String column, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return mathQuery(tableName, new String[]{"max(" + column + ")"}, conditions, type);
    }

    /* access modifiers changed from: 0000 */
    public <T> T onMin(String tableName, String column, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return mathQuery(tableName, new String[]{"min(" + column + ")"}, conditions, type);
    }

    /* access modifiers changed from: 0000 */
    public <T> T onSum(String tableName, String column, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return mathQuery(tableName, new String[]{"sum(" + column + ")"}, conditions, type);
    }
}
