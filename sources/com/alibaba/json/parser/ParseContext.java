package com.alibaba.json.parser;

import com.alibaba.android.arouter.utils.Consts;
import java.lang.reflect.Type;

public class ParseContext {
    public final Object fieldName;
    public Object object;
    public final ParseContext parent;
    private transient String path;
    public Type type;

    public ParseContext(ParseContext parent2, Object object2, Object fieldName2) {
        this.parent = parent2;
        this.object = object2;
        this.fieldName = fieldName2;
    }

    public String toString() {
        if (this.path == null) {
            if (this.parent == null) {
                this.path = "$";
            } else if (this.fieldName instanceof Integer) {
                this.path = this.parent.toString() + "[" + this.fieldName + "]";
            } else {
                this.path = this.parent.toString() + Consts.DOT + this.fieldName;
            }
        }
        return this.path;
    }
}
