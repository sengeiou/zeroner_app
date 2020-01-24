package org.litepal.tablemanager.typechange;

public class DecimalOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType != null) {
            if (fieldType.equals("float") || fieldType.equals("java.lang.Float")) {
                return "real";
            }
            if (fieldType.equals("double") || fieldType.equals("java.lang.Double")) {
                return "real";
            }
        }
        return null;
    }
}
