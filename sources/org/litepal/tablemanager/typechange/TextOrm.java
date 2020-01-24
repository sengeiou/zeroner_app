package org.litepal.tablemanager.typechange;

public class TextOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType != null) {
            if (fieldType.equals("char") || fieldType.equals("java.lang.Character")) {
                return "text";
            }
            if (fieldType.equals("java.lang.String")) {
                return "text";
            }
        }
        return null;
    }
}
