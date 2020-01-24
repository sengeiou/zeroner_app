package org.aspectj.runtime.reflect;

import com.alibaba.android.arouter.utils.Consts;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.lang.reflect.Field;
import org.aspectj.lang.reflect.FieldSignature;

public class FieldSignatureImpl extends MemberSignatureImpl implements FieldSignature {
    private Field field;
    Class fieldType;

    FieldSignatureImpl(int modifiers, String name, Class declaringType, Class fieldType2) {
        super(modifiers, name, declaringType);
        this.fieldType = fieldType2;
    }

    FieldSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public Class getFieldType() {
        if (this.fieldType == null) {
            this.fieldType = extractType(3);
        }
        return this.fieldType;
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        StringBuffer buf = new StringBuffer();
        buf.append(sm.makeModifiersString(getModifiers()));
        if (sm.includeArgs) {
            buf.append(sm.makeTypeName(getFieldType()));
        }
        if (sm.includeArgs) {
            buf.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        }
        buf.append(sm.makePrimaryTypeName(getDeclaringType(), getDeclaringTypeName()));
        buf.append(Consts.DOT);
        buf.append(getName());
        return buf.toString();
    }

    public Field getField() {
        if (this.field == null) {
            try {
                this.field = getDeclaringType().getDeclaredField(getName());
            } catch (Exception e) {
            }
        }
        return this.field;
    }
}
