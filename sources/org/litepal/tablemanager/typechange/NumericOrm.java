package org.litepal.tablemanager.typechange;

public class NumericOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType != null) {
            if (fieldType.equals("int") || fieldType.equals("java.lang.Integer")) {
                return "integer";
            }
            if (fieldType.equals("long") || fieldType.equals("java.lang.Long")) {
                return "integer";
            }
            if (fieldType.equals("short") || fieldType.equals("java.lang.Short")) {
                return "integer";
            }
        }
        return null;
    }
}
