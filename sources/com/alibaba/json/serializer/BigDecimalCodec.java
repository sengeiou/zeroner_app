package com.alibaba.json.serializer;

import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.JSONLexer;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import com.alibaba.json.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalCodec implements ObjectSerializer, ObjectDeserializer {
    public static final BigDecimalCodec instance = new BigDecimalCodec();

    private BigDecimalCodec() {
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            if ((out.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0) {
                out.write(48);
            } else {
                out.writeNull();
            }
        } else if (object instanceof BigInteger) {
            out.write(((BigInteger) object).toString());
        } else {
            BigDecimal val = (BigDecimal) object;
            out.write(val.toString());
            if ((out.features & SerializerFeature.WriteClassName.mask) != 0 && fieldType != BigDecimal.class && val.scale() == 0) {
                out.write(46);
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 2) {
            if (clazz == BigInteger.class) {
                String val = lexer.numberString();
                lexer.nextToken(16);
                return new BigInteger(val, 10);
            }
            T decimalValue = lexer.decimalValue();
            lexer.nextToken(16);
            return decimalValue;
        } else if (token == 3) {
            BigDecimal val2 = lexer.decimalValue();
            lexer.nextToken(16);
            return clazz == BigInteger.class ? val2.toBigInteger() : val2;
        } else {
            Object value = parser.parse();
            if (value == null) {
                return null;
            }
            if (clazz == BigInteger.class) {
                return TypeUtils.castToBigInteger(value);
            }
            return TypeUtils.castToBigDecimal(value);
        }
    }
}
