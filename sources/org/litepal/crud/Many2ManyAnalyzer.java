package org.litepal.crud;

import android.database.Cursor;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import org.litepal.crud.model.AssociationsInfo;
import org.litepal.tablemanager.Connector;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

public class Many2ManyAnalyzer extends AssociationsAnalyzer {
    /* access modifiers changed from: 0000 */
    public void analyze(DataSupport baseObj, AssociationsInfo associationInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Collection<DataSupport> associatedModels = getAssociatedModels(baseObj, associationInfo);
        declareAssociations(baseObj, associationInfo);
        if (associatedModels != null) {
            for (DataSupport associatedModel : associatedModels) {
                Collection<DataSupport> reverseAssociatedModels = checkAssociatedModelCollection(getReverseAssociatedModels(associatedModel, associationInfo), associationInfo.getAssociateSelfFromOtherModel());
                addNewModelForAssociatedModel(reverseAssociatedModels, baseObj);
                setReverseAssociatedModels(associatedModel, associationInfo, reverseAssociatedModels);
                dealAssociatedModel(baseObj, associatedModel);
            }
        }
    }

    private void declareAssociations(DataSupport baseObj, AssociationsInfo associationInfo) {
        baseObj.addEmptyModelForJoinTable(getAssociatedTableName(associationInfo));
    }

    private void addNewModelForAssociatedModel(Collection<DataSupport> associatedModelCollection, DataSupport baseObj) {
        if (!associatedModelCollection.contains(baseObj)) {
            associatedModelCollection.add(baseObj);
        }
    }

    private void dealAssociatedModel(DataSupport baseObj, DataSupport associatedModel) {
        if (associatedModel.isSaved()) {
            baseObj.addAssociatedModelForJoinTable(associatedModel.getTableName(), associatedModel.getBaseObjId());
        }
    }

    private String getAssociatedTableName(AssociationsInfo associationInfo) {
        return BaseUtility.changeCase(DBUtility.getTableNameByClassName(associationInfo.getAssociatedClassName()));
    }

    @Deprecated
    private boolean isDataExists(DataSupport baseObj, DataSupport associatedModel) {
        Cursor cursor = null;
        try {
            cursor = Connector.getDatabase().query(getJoinTableName(baseObj, associatedModel), null, getSelection(baseObj, associatedModel), getSelectionArgs(baseObj, associatedModel), null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            cursor.close();
            return true;
        } catch (Throwable th) {
            cursor.close();
            throw th;
        }
    }

    private String getSelection(DataSupport baseObj, DataSupport associatedModel) {
        StringBuilder where = new StringBuilder();
        where.append(getForeignKeyColumnName(baseObj.getTableName()));
        where.append(" = ? and ");
        where.append(getForeignKeyColumnName(associatedModel.getTableName()));
        where.append(" = ?");
        return where.toString();
    }

    private String[] getSelectionArgs(DataSupport baseObj, DataSupport associatedModel) {
        return new String[]{String.valueOf(baseObj.getBaseObjId()), String.valueOf(associatedModel.getBaseObjId())};
    }

    private String getJoinTableName(DataSupport baseObj, DataSupport associatedModel) {
        return getIntermediateTableName(baseObj, associatedModel.getTableName());
    }
}
