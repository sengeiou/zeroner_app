package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;

public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig mapping, Class<?> clazz) {
        super(mapping, clazz, clazz);
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Throwable ex;
        Throwable ex2;
        JSONLexer lexer = parser.lexer;
        if (lexer.token == 8) {
            lexer.nextToken();
            return null;
        }
        if (parser.resolveStatus == 2) {
            parser.resolveStatus = 0;
        } else if (lexer.token != 12) {
            throw new JSONException("syntax error");
        }
        Throwable cause = null;
        Class<?> exClass = null;
        if (type != null && (type instanceof Class)) {
            Class<?> clazz = (Class) type;
            if (Throwable.class.isAssignableFrom(clazz)) {
                exClass = clazz;
            }
        }
        String message = null;
        StackTraceElement[] stackTrace = null;
        HashMap hashMap = null;
        while (true) {
            String key = lexer.scanSymbol(parser.symbolTable);
            if (key == null) {
                if (lexer.token == 13) {
                    lexer.nextToken(16);
                    break;
                } else if (lexer.token == 16) {
                    continue;
                }
            }
            lexer.nextTokenWithChar(':');
            if ("@type".equals(key)) {
                if (lexer.token == 4) {
                    exClass = TypeUtils.loadClass(lexer.stringVal(), parser.config.defaultClassLoader, false);
                    lexer.nextToken(16);
                } else {
                    throw new JSONException("syntax error");
                }
            } else if ("message".equals(key)) {
                if (lexer.token == 8) {
                    message = null;
                } else if (lexer.token == 4) {
                    message = lexer.stringVal();
                } else {
                    throw new JSONException("syntax error");
                }
                lexer.nextToken();
            } else if ("cause".equals(key)) {
                cause = (Throwable) deserialze(parser, null, "cause");
            } else if ("stackTrace".equals(key)) {
                stackTrace = (StackTraceElement[]) parser.parseObject(StackTraceElement[].class);
            } else {
                if (hashMap == null) {
                    hashMap = new HashMap();
                }
                hashMap.put(key, parser.parse());
            }
            if (lexer.token == 13) {
                lexer.nextToken(16);
                break;
            }
        }
        if (exClass == null) {
            ex2 = new Exception(message, cause);
        } else {
            Constructor<?> defaultConstructor = null;
            Constructor<?> messageConstructor = null;
            Constructor<?> causeConstructor = null;
            try {
                Constructor<?>[] constructors = exClass.getConstructors();
                int length = constructors.length;
                for (int i = 0; i < length; i++) {
                    Constructor<?> constructor = constructors[i];
                    if (constructor.getParameterTypes().length == 0) {
                        defaultConstructor = constructor;
                    } else if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == String.class) {
                        messageConstructor = constructor;
                    } else if (constructor.getParameterTypes().length == 2 && constructor.getParameterTypes()[0] == String.class && constructor.getParameterTypes()[1] == Throwable.class) {
                        causeConstructor = constructor;
                    }
                }
                if (causeConstructor != null) {
                    ex = (Throwable) causeConstructor.newInstance(new Object[]{message, cause});
                } else if (messageConstructor != null) {
                    ex = (Throwable) messageConstructor.newInstance(new Object[]{message});
                } else if (defaultConstructor != null) {
                    ex = (Throwable) defaultConstructor.newInstance(new Object[0]);
                } else {
                    ex = null;
                }
                if (ex == null) {
                    try {
                        ex2 = new Exception(message, cause);
                    } catch (Exception e) {
                        e = e;
                        Throwable th = ex;
                    }
                } else {
                    ex2 = ex;
                }
            } catch (Exception e2) {
                e = e2;
                JSONException jSONException = new JSONException("create instance error", e);
                throw jSONException;
            }
        }
        if (stackTrace != null) {
            ex2.setStackTrace(stackTrace);
        }
        if (hashMap == null) {
            return ex2;
        }
        JavaBeanDeserializer exBeanDeser = null;
        if (exClass != null) {
            if (exClass == this.clazz) {
                exBeanDeser = this;
            } else {
                ObjectDeserializer exDeser = parser.config.getDeserializer(exClass);
                if (exDeser instanceof JavaBeanDeserializer) {
                    exBeanDeser = (JavaBeanDeserializer) exDeser;
                }
            }
        }
        if (exBeanDeser == null) {
            return ex2;
        }
        for (Entry<String, Object> entry : hashMap.entrySet()) {
            String key2 = (String) entry.getKey();
            Object value = entry.getValue();
            FieldDeserializer fieldDeserializer = exBeanDeser.getFieldDeserializer(key2);
            if (fieldDeserializer != null) {
                fieldDeserializer.setValue((Object) ex2, value);
            }
        }
        return ex2;
    }
}
