package com.alibaba.json.parser;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;

public class EnumDeserializer implements ObjectDeserializer {
    private final Class<?> enumClass;
    protected final Enum[] values;

    public EnumDeserializer(Class<?> enumClass2) {
        this.enumClass = enumClass2;
        this.values = (Enum[]) enumClass2.getEnumConstants();
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        try {
            JSONLexer lexer = parser.lexer;
            int token = lexer.token;
            if (token == 2) {
                int intValue = lexer.intValue();
                lexer.nextToken(16);
                if (intValue >= 0 && intValue <= this.values.length) {
                    return this.values[intValue];
                }
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + intValue);
            } else if (token == 4) {
                String strVal = lexer.stringVal();
                lexer.nextToken(16);
                if (strVal.length() != 0) {
                    return Enum.valueOf(this.enumClass, strVal);
                }
                return null;
            } else if (token == 8) {
                lexer.nextToken(16);
                return null;
            } else {
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + parser.parse());
            }
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }
}
