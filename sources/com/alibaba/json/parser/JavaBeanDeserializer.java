package com.alibaba.json.parser;

import com.alibaba.json.JSONException;
import com.alibaba.json.JSONObject;
import com.alibaba.json.parser.deserializer.ExtraProcessable;
import com.alibaba.json.parser.deserializer.ExtraProcessor;
import com.alibaba.json.parser.deserializer.ExtraTypeProvider;
import com.alibaba.json.parser.deserializer.FieldDeserializer;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import com.alibaba.json.util.FieldInfo;
import com.alibaba.json.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import kotlin.text.Typography;

public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] smartMatchHashArray;
    private transient int[] smartMatchHashArrayMapping;
    private final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2, Type type) {
        this(config, clazz2, type, JavaBeanInfo.build(clazz2, clazz2.getModifiers(), type, false, true, true, true, config.propertyNamingStrategy));
    }

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2, Type type, JavaBeanInfo beanInfo2) {
        String[] strArr;
        this.clazz = clazz2;
        this.beanInfo = beanInfo2;
        Map<String, FieldDeserializer> alterNameFieldDeserializers2 = null;
        this.sortedFieldDeserializers = new FieldDeserializer[beanInfo2.sortedFields.length];
        int size = beanInfo2.sortedFields.length;
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo = beanInfo2.sortedFields[i];
            FieldDeserializer fieldDeserializer = config.createFieldDeserializer(config, clazz2, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializer;
            for (String name : fieldInfo.alternateNames) {
                if (alterNameFieldDeserializers2 == null) {
                    alterNameFieldDeserializers2 = new HashMap<>();
                }
                alterNameFieldDeserializers2.put(name, fieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = alterNameFieldDeserializers2;
        this.fieldDeserializers = new FieldDeserializer[beanInfo2.fields.length];
        int size2 = beanInfo2.fields.length;
        for (int i2 = 0; i2 < size2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(beanInfo2.fields[i2].name);
        }
    }

    /* access modifiers changed from: protected */
    public Object createInstance(DefaultJSONParser parser, Type type) {
        Object object;
        FieldInfo[] fieldInfoArr;
        boolean ordered;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            Class<?> clazz2 = (Class) type;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if ((parser.lexer.features & Feature.OrderedField.mask) != 0) {
                ordered = true;
            } else {
                ordered = false;
            }
            return Proxy.newProxyInstance(loader, new Class[]{clazz2}, new JSONObject(ordered));
        } else if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        } else {
            if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
                return null;
            }
            try {
                Constructor<?> constructor = this.beanInfo.defaultConstructor;
                if (this.beanInfo.defaultConstructorParameterSize != 0) {
                    object = constructor.newInstance(new Object[]{parser.contex.object});
                } else if (constructor != null) {
                    object = constructor.newInstance(new Object[0]);
                } else {
                    object = this.beanInfo.factoryMethod.invoke(null, new Object[0]);
                }
                if (!(parser == null || (parser.lexer.features & Feature.InitStringFieldAsEmpty.mask) == 0)) {
                    for (FieldInfo fieldInfo : this.beanInfo.fields) {
                        if (fieldInfo.fieldClass == String.class) {
                            fieldInfo.set(object, "");
                        }
                    }
                }
                return object;
            } catch (Exception e) {
                throw new JSONException("create instance error, class " + this.clazz.getName(), e);
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return deserialze(parser, type, fieldName, null);
    }

    private <T> T deserialzeArrayMapping(DefaultJSONParser parser, Type type, Object fieldName, Object object) {
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        char charAt5;
        Enum value;
        char charAt6;
        char charAt7;
        char charAt8;
        char charAt9;
        char charAt10;
        char charAt11;
        char charAt12;
        String strVal;
        char charAt13;
        char charAt14;
        char charAt15;
        char charAt16;
        JSONLexer lexer = parser.lexer;
        T createInstance = createInstance(parser, type);
        int size = this.sortedFieldDeserializers.length;
        int i = 0;
        while (i < size) {
            char seperator = i == size + -1 ? ']' : ',';
            FieldDeserializer fieldDeser = this.sortedFieldDeserializers[i];
            FieldInfo fieldInfo = fieldDeser.fieldInfo;
            Class<?> fieldClass = fieldInfo.fieldClass;
            try {
                if (fieldClass == Integer.TYPE) {
                    int intValue = (int) lexer.scanLongValue();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setInt(createInstance, intValue);
                    } else {
                        Integer num = new Integer(intValue);
                        fieldDeser.setValue((Object) createInstance, (Object) num);
                    }
                    if (lexer.ch == ',') {
                        int index = lexer.bp + 1;
                        lexer.bp = index;
                        if (index >= lexer.len) {
                            charAt16 = 26;
                        } else {
                            charAt16 = lexer.text.charAt(index);
                        }
                        lexer.ch = charAt16;
                        lexer.token = 16;
                    } else if (lexer.ch == ']') {
                        int index2 = lexer.bp + 1;
                        lexer.bp = index2;
                        if (index2 >= lexer.len) {
                            charAt15 = 26;
                        } else {
                            charAt15 = lexer.text.charAt(index2);
                        }
                        lexer.ch = charAt15;
                        lexer.token = 15;
                    } else {
                        lexer.nextToken();
                    }
                } else if (fieldClass == String.class) {
                    if (lexer.ch == '\"') {
                        strVal = lexer.scanStringValue(Typography.quote);
                    } else if (lexer.ch != 'n' || !lexer.text.startsWith("null", lexer.bp)) {
                        throw new JSONException("not match string. feild : " + fieldName);
                    } else {
                        lexer.bp += 4;
                        int index3 = lexer.bp;
                        if (lexer.bp >= lexer.len) {
                            charAt12 = 26;
                        } else {
                            charAt12 = lexer.text.charAt(index3);
                        }
                        lexer.ch = charAt12;
                        strVal = null;
                    }
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.set(createInstance, strVal);
                    } else {
                        fieldDeser.setValue((Object) createInstance, (Object) strVal);
                    }
                    if (lexer.ch == ',') {
                        int index4 = lexer.bp + 1;
                        lexer.bp = index4;
                        if (index4 >= lexer.len) {
                            charAt14 = 26;
                        } else {
                            charAt14 = lexer.text.charAt(index4);
                        }
                        lexer.ch = charAt14;
                        lexer.token = 16;
                    } else if (lexer.ch == ']') {
                        int index5 = lexer.bp + 1;
                        lexer.bp = index5;
                        if (index5 >= lexer.len) {
                            charAt13 = 26;
                        } else {
                            charAt13 = lexer.text.charAt(index5);
                        }
                        lexer.ch = charAt13;
                        lexer.token = 15;
                    } else {
                        lexer.nextToken();
                    }
                } else if (fieldClass == Long.TYPE) {
                    long longValue = lexer.scanLongValue();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setLong(createInstance, longValue);
                    } else {
                        Long l = new Long(longValue);
                        fieldDeser.setValue((Object) createInstance, (Object) l);
                    }
                    if (lexer.ch == ',') {
                        int index6 = lexer.bp + 1;
                        lexer.bp = index6;
                        if (index6 >= lexer.len) {
                            charAt11 = 26;
                        } else {
                            charAt11 = lexer.text.charAt(index6);
                        }
                        lexer.ch = charAt11;
                        lexer.token = 16;
                    } else if (lexer.ch == ']') {
                        int index7 = lexer.bp + 1;
                        lexer.bp = index7;
                        if (index7 >= lexer.len) {
                            charAt10 = 26;
                        } else {
                            charAt10 = lexer.text.charAt(index7);
                        }
                        lexer.ch = charAt10;
                        lexer.token = 15;
                    } else {
                        lexer.nextToken();
                    }
                } else if (fieldClass == Boolean.TYPE) {
                    boolean booleanValue = lexer.scanBoolean();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setBoolean(createInstance, booleanValue);
                    } else {
                        fieldDeser.setValue((Object) createInstance, (Object) Boolean.valueOf(booleanValue));
                    }
                    if (lexer.ch == ',') {
                        int index8 = lexer.bp + 1;
                        lexer.bp = index8;
                        if (index8 >= lexer.len) {
                            charAt9 = 26;
                        } else {
                            charAt9 = lexer.text.charAt(index8);
                        }
                        lexer.ch = charAt9;
                        lexer.token = 16;
                    } else if (lexer.ch == ']') {
                        int index9 = lexer.bp + 1;
                        lexer.bp = index9;
                        if (index9 >= lexer.len) {
                            charAt8 = 26;
                        } else {
                            charAt8 = lexer.text.charAt(index9);
                        }
                        lexer.ch = charAt8;
                        lexer.token = 15;
                    } else {
                        lexer.nextToken();
                    }
                } else if (fieldClass.isEnum()) {
                    char ch = lexer.ch;
                    if (ch == '\"') {
                        String enumName = lexer.scanSymbol(parser.symbolTable);
                        if (enumName == null) {
                            value = null;
                        } else {
                            value = Enum.valueOf(fieldClass, enumName);
                        }
                    } else if (ch < '0' || ch > '9') {
                        throw new JSONException("illegal enum." + lexer.info());
                    } else {
                        value = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeser).getFieldValueDeserilizer(parser.config)).values[(int) lexer.scanLongValue()];
                    }
                    fieldDeser.setValue((Object) createInstance, (Object) value);
                    if (lexer.ch == ',') {
                        int index10 = lexer.bp + 1;
                        lexer.bp = index10;
                        if (index10 >= lexer.len) {
                            charAt7 = 26;
                        } else {
                            charAt7 = lexer.text.charAt(index10);
                        }
                        lexer.ch = charAt7;
                        lexer.token = 16;
                    } else if (lexer.ch == ']') {
                        int index11 = lexer.bp + 1;
                        lexer.bp = index11;
                        if (index11 >= lexer.len) {
                            charAt6 = 26;
                        } else {
                            charAt6 = lexer.text.charAt(index11);
                        }
                        lexer.ch = charAt6;
                        lexer.token = 15;
                    } else {
                        lexer.nextToken();
                    }
                } else if (fieldClass == Date.class && lexer.ch == '1') {
                    Date date = new Date(lexer.scanLongValue());
                    fieldDeser.setValue((Object) createInstance, (Object) date);
                    if (lexer.ch == ',') {
                        int index12 = lexer.bp + 1;
                        lexer.bp = index12;
                        if (index12 >= lexer.len) {
                            charAt5 = 26;
                        } else {
                            charAt5 = lexer.text.charAt(index12);
                        }
                        lexer.ch = charAt5;
                        lexer.token = 16;
                    } else if (lexer.ch == ']') {
                        int index13 = lexer.bp + 1;
                        lexer.bp = index13;
                        if (index13 >= lexer.len) {
                            charAt4 = 26;
                        } else {
                            charAt4 = lexer.text.charAt(index13);
                        }
                        lexer.ch = charAt4;
                        lexer.token = 15;
                    } else {
                        lexer.nextToken();
                    }
                } else {
                    if (lexer.ch == '[') {
                        int index14 = lexer.bp + 1;
                        lexer.bp = index14;
                        if (index14 >= lexer.len) {
                            charAt3 = 26;
                        } else {
                            charAt3 = lexer.text.charAt(index14);
                        }
                        lexer.ch = charAt3;
                        lexer.token = 14;
                    } else if (lexer.ch == '{') {
                        int index15 = lexer.bp + 1;
                        lexer.bp = index15;
                        if (index15 >= lexer.len) {
                            charAt2 = 26;
                        } else {
                            charAt2 = lexer.text.charAt(index15);
                        }
                        lexer.ch = charAt2;
                        lexer.token = 12;
                    } else {
                        lexer.nextToken();
                    }
                    fieldDeser.parseField(parser, createInstance, fieldInfo.fieldType, null);
                    if (seperator == ']') {
                        if (lexer.token != 15) {
                            throw new JSONException("syntax error");
                        }
                    } else if (seperator == ',' && lexer.token != 16) {
                        throw new JSONException("syntax error");
                    }
                }
                i++;
            } catch (IllegalAccessException e) {
                JSONException jSONException = new JSONException("set " + fieldInfo.name + "error", e);
                throw jSONException;
            }
        }
        if (lexer.ch == ',') {
            int index16 = lexer.bp + 1;
            lexer.bp = index16;
            if (index16 >= lexer.len) {
                charAt = 26;
            } else {
                charAt = lexer.text.charAt(index16);
            }
            lexer.ch = charAt;
            lexer.token = 16;
        } else {
            lexer.nextToken();
        }
        return createInstance;
    }

    /* JADX WARNING: type inference failed for: r28v0 */
    /* JADX WARNING: type inference failed for: r28v1 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v2 */
    /* JADX WARNING: type inference failed for: r0v60, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v3, types: [java.lang.Integer] */
    /* JADX WARNING: type inference failed for: r28v4, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r28v5, types: [java.lang.Float] */
    /* JADX WARNING: type inference failed for: r0v67, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r28v7, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r28v8, types: [double[][]] */
    /* JADX WARNING: type inference failed for: r28v9, types: [float[][]] */
    /* JADX WARNING: type inference failed for: r28v10, types: [double[]] */
    /* JADX WARNING: type inference failed for: r28v11, types: [float[]] */
    /* JADX WARNING: type inference failed for: r28v12, types: [int[]] */
    /* JADX WARNING: type inference failed for: r28v13, types: [java.lang.Enum] */
    /* JADX WARNING: type inference failed for: r28v14, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r28v15, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r28v16 */
    /* JADX WARNING: type inference failed for: r28v17 */
    /* JADX WARNING: type inference failed for: r28v18 */
    /* JADX WARNING: type inference failed for: r0v284, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r28v19 */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01c6, code lost:
        if (r63 != null) goto L_0x08e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01c8, code lost:
        if (r9 != null) goto L_0x0818;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
        r63 = createInstance(r60, r61);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01ce, code lost:
        if (r11 != null) goto L_0x01da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01d0, code lost:
        r11 = r60.setContext(r12, r63, r62);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01da, code lost:
        if (r11 == null) goto L_0x01e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01dc, code lost:
        r11.object = r63;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01e0, code lost:
        r60.setContext(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x053b, code lost:
        if (r53.equals(r6) == false) goto L_0x053d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:0x0540, code lost:
        if ("@type" == r6) goto L_0x0542;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:274:0x0542, code lost:
        r39.nextTokenWithChar(':');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:0x054e, code lost:
        if (r39.token != 4) goto L_0x0607;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:276:0x0550, code lost:
        r55 = r39.stringVal();
        r39.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:277:0x055f, code lost:
        if ((r61 instanceof java.lang.Class) == false) goto L_0x0581;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:279:0x0570, code lost:
        if (r55.equals(((java.lang.Class) r61).getName()) == false) goto L_0x0581;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x0578, code lost:
        if (r39.token != 13) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:282:0x057a, code lost:
        r39.nextToken();
        r9 = r33;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x0581, code lost:
        r13 = getSeeAlso(r60.config, r59.beanInfo, r55);
        r57 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:284:0x0593, code lost:
        if (r13 != null) goto L_0x05bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:285:0x0595, code lost:
        r57 = com.alibaba.json.util.TypeUtils.loadClass(r55, r60.config.defaultClassLoader);
        r20 = com.alibaba.json.util.TypeUtils.getClass(r61);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:286:0x05a5, code lost:
        if (r20 == null) goto L_0x05b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:0x05a7, code lost:
        if (r57 == null) goto L_0x05f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x05b1, code lost:
        if (r20.isAssignableFrom(r57) == false) goto L_0x05f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:290:0x05b3, code lost:
        r13 = r60.config.getDeserializer(r57);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x05bf, code lost:
        if ((r13 instanceof com.alibaba.json.parser.JavaBeanDeserializer) == false) goto L_0x05fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x05c1, code lost:
        r38 = (com.alibaba.json.parser.JavaBeanDeserializer) r13;
        r56 = r38.deserialze(r60, r57, r62, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:294:0x05d3, code lost:
        if (r53 == null) goto L_0x05e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:295:0x05d5, code lost:
        r38.getFieldDeserializer(r53).setValue((java.lang.Object) r56, (java.lang.Object) r55);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:296:0x05e6, code lost:
        if (r11 == null) goto L_0x05ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:297:0x05e8, code lost:
        r11.object = r63;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x05ec, code lost:
        r60.setContext(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:301:0x05fb, code lost:
        throw new com.alibaba.json.JSONException("type not match");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:302:0x05fc, code lost:
        r56 = r13.deserialze(r60, r57, r62);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:304:0x060f, code lost:
        throw new com.alibaba.json.JSONException("syntax error");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:356:0x06cc, code lost:
        r17 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:359:0x06ec, code lost:
        throw new com.alibaba.json.JSONException("set property error, " + r27.name, r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:415:0x0818, code lost:
        r45 = r59.beanInfo.creatorConstructorParameters;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:416:0x0820, code lost:
        if (r45 == null) goto L_0x0858;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:417:0x0822, code lost:
        r50 = r45.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:418:0x0827, code lost:
        r46 = new java.lang.Object[r50];
        r36 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:420:0x0833, code lost:
        if (r36 >= r50) goto L_0x0869;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:421:0x0835, code lost:
        r27 = r59.fieldDeserializers[r36].fieldInfo;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:422:0x083f, code lost:
        if (r45 == null) goto L_0x0860;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:423:0x0841, code lost:
        r44 = r9.remove(r27.name);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:424:0x0849, code lost:
        if (r44 != null) goto L_0x0853;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:425:0x084b, code lost:
        r44 = com.alibaba.json.util.TypeUtils.defaultValue(r27.fieldClass);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:426:0x0853, code lost:
        r46[r36] = r44;
        r36 = r36 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:427:0x0858, code lost:
        r50 = r59.fieldDeserializers.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:428:0x0860, code lost:
        r44 = r9.get(r27.name);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:430:0x086f, code lost:
        if (r59.beanInfo.creatorConstructor == null) goto L_0x08d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:433:?, code lost:
        r63 = r59.beanInfo.creatorConstructor.newInstance(r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:434:0x087d, code lost:
        if (r45 == null) goto L_0x08e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:436:?, code lost:
        r5 = r9.entrySet().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:438:0x088b, code lost:
        if (r5.hasNext() == false) goto L_0x08e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:439:0x088d, code lost:
        r16 = (java.util.Map.Entry) r5.next();
        r23 = getFieldDeserializer((java.lang.String) r16.getKey());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:440:0x089f, code lost:
        if (r23 == null) goto L_0x0887;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:441:0x08a1, code lost:
        r23.setValue(r63, r16.getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:442:0x08ad, code lost:
        r15 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:444:0x08d1, code lost:
        throw new com.alibaba.json.JSONException("create instance error, " + r59.beanInfo.creatorConstructor.toGenericString(), r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:446:0x08d8, code lost:
        if (r59.beanInfo.factoryMethod == null) goto L_0x08e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:449:?, code lost:
        r63 = r59.beanInfo.factoryMethod.invoke(null, r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:450:0x08e7, code lost:
        if (r11 == null) goto L_0x08ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:451:0x08e9, code lost:
        r11.object = r63;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:452:0x08ed, code lost:
        r60.setContext(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:453:0x08f6, code lost:
        r15 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:456:0x091a, code lost:
        throw new com.alibaba.json.JSONException("create factory method error, " + r59.beanInfo.factoryMethod.toString(), r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:506:?, code lost:
        return r63;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:508:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:509:?, code lost:
        return r63;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0100, code lost:
        r4 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:15:0x0047, B:346:0x06aa, B:432:0x0873, B:448:0x08dc] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r28v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.lang.Double, java.lang.Boolean, double[][], float[][], double[], float[], int[], java.util.Date, java.lang.String]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], ?[OBJECT, ARRAY], java.lang.Double]
      mth insns count: 1001
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T deserialze(com.alibaba.json.parser.DefaultJSONParser r60, java.lang.reflect.Type r61, java.lang.Object r62, java.lang.Object r63) {
        /*
            r59 = this;
            java.lang.Class<com.alibaba.json.JSON> r4 = com.alibaba.json.JSON.class
            r0 = r61
            if (r0 == r4) goto L_0x000c
            java.lang.Class<com.alibaba.json.JSONObject> r4 = com.alibaba.json.JSONObject.class
            r0 = r61
            if (r0 != r4) goto L_0x0011
        L_0x000c:
            java.lang.Object r56 = r60.parse()
        L_0x0010:
            return r56
        L_0x0011:
            r0 = r60
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer
            r39 = r0
            r0 = r39
            int r0 = r0.token
            r52 = r0
            r4 = 8
            r0 = r52
            if (r0 != r4) goto L_0x002d
            r4 = 16
            r0 = r39
            r0.nextToken(r4)
            r56 = 0
            goto L_0x0010
        L_0x002d:
            r0 = r39
            boolean r14 = r0.disableCircularReferenceDetect
            r0 = r60
            com.alibaba.json.parser.ParseContext r12 = r0.contex
            if (r63 == 0) goto L_0x003b
            if (r12 == 0) goto L_0x003b
            com.alibaba.json.parser.ParseContext r12 = r12.parent
        L_0x003b:
            r11 = 0
            r9 = 0
            r4 = 13
            r0 = r52
            if (r0 != r4) goto L_0x005e
            r4 = 16
            r0 = r39
            r0.nextToken(r4)     // Catch:{ all -> 0x0100 }
            if (r63 != 0) goto L_0x0050
            java.lang.Object r63 = r59.createInstance(r60, r61)     // Catch:{ all -> 0x0100 }
        L_0x0050:
            if (r11 == 0) goto L_0x0056
            r0 = r63
            r11.object = r0
        L_0x0056:
            r0 = r60
            r0.setContext(r12)
            r56 = r63
            goto L_0x0010
        L_0x005e:
            r4 = 14
            r0 = r52
            if (r0 != r4) goto L_0x008e
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            boolean r4 = r4.supportBeanToArray     // Catch:{ all -> 0x0100 }
            if (r4 != 0) goto L_0x0077
            r0 = r39
            int r4 = r0.features     // Catch:{ all -> 0x0100 }
            com.alibaba.json.parser.Feature r5 = com.alibaba.json.parser.Feature.SupportArrayToBean     // Catch:{ all -> 0x0100 }
            int r5 = r5.mask     // Catch:{ all -> 0x0100 }
            r4 = r4 & r5
            if (r4 == 0) goto L_0x008b
        L_0x0077:
            r37 = 1
        L_0x0079:
            if (r37 == 0) goto L_0x008e
            java.lang.Object r56 = r59.deserialzeArrayMapping(r60, r61, r62, r63)     // Catch:{ all -> 0x0100 }
            if (r11 == 0) goto L_0x0085
            r0 = r63
            r11.object = r0
        L_0x0085:
            r0 = r60
            r0.setContext(r12)
            goto L_0x0010
        L_0x008b:
            r37 = 0
            goto L_0x0079
        L_0x008e:
            r4 = 12
            r0 = r52
            if (r0 == r4) goto L_0x010d
            r4 = 16
            r0 = r52
            if (r0 == r4) goto L_0x010d
            boolean r4 = r39.isBlankInput()     // Catch:{ all -> 0x0100 }
            if (r4 == 0) goto L_0x00af
            r56 = 0
            if (r11 == 0) goto L_0x00a8
            r0 = r63
            r11.object = r0
        L_0x00a8:
            r0 = r60
            r0.setContext(r12)
            goto L_0x0010
        L_0x00af:
            r4 = 4
            r0 = r52
            if (r0 != r4) goto L_0x00d0
            java.lang.String r51 = r39.stringVal()     // Catch:{ all -> 0x0100 }
            int r4 = r51.length()     // Catch:{ all -> 0x0100 }
            if (r4 != 0) goto L_0x00d0
            r39.nextToken()     // Catch:{ all -> 0x0100 }
            r56 = 0
            if (r11 == 0) goto L_0x00c9
            r0 = r63
            r11.object = r0
        L_0x00c9:
            r0 = r60
            r0.setContext(r12)
            goto L_0x0010
        L_0x00d0:
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ all -> 0x0100 }
            r4.<init>()     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = "syntax error, expect {, actual "
            java.lang.StringBuffer r4 = r4.append(r5)     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = r39.info()     // Catch:{ all -> 0x0100 }
            java.lang.StringBuffer r10 = r4.append(r5)     // Catch:{ all -> 0x0100 }
            r0 = r62
            boolean r4 = r0 instanceof java.lang.String     // Catch:{ all -> 0x0100 }
            if (r4 == 0) goto L_0x00f6
            java.lang.String r4 = ", fieldName "
            java.lang.StringBuffer r4 = r10.append(r4)     // Catch:{ all -> 0x0100 }
            r0 = r62
            r4.append(r0)     // Catch:{ all -> 0x0100 }
        L_0x00f6:
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = r10.toString()     // Catch:{ all -> 0x0100 }
            r4.<init>(r5)     // Catch:{ all -> 0x0100 }
            throw r4     // Catch:{ all -> 0x0100 }
        L_0x0100:
            r4 = move-exception
        L_0x0101:
            if (r11 == 0) goto L_0x0107
            r0 = r63
            r11.object = r0
        L_0x0107:
            r0 = r60
            r0.setContext(r12)
            throw r4
        L_0x010d:
            r0 = r60
            int r4 = r0.resolveStatus     // Catch:{ all -> 0x0100 }
            r5 = 2
            if (r4 != r5) goto L_0x0119
            r4 = 0
            r0 = r60
            r0.resolveStatus = r4     // Catch:{ all -> 0x0100 }
        L_0x0119:
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            java.lang.String r0 = r4.typeKey     // Catch:{ all -> 0x0100 }
            r53 = r0
            r42 = 0
            r26 = 0
            r0 = r59
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r4 = r0.sortedFieldDeserializers     // Catch:{ all -> 0x0100 }
            int r0 = r4.length     // Catch:{ all -> 0x0100 }
            r50 = r0
            r33 = r9
        L_0x012e:
            r6 = 0
            r22 = 0
            r27 = 0
            r21 = 0
            r4 = 0
            int r4 = (r42 > r4 ? 1 : (r42 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x0153
            r0 = r59
            r1 = r42
            com.alibaba.json.parser.deserializer.FieldDeserializer r22 = r0.getFieldDeserializerByHash(r1)     // Catch:{ all -> 0x0472 }
            if (r22 == 0) goto L_0x0151
            r0 = r22
            com.alibaba.json.util.FieldInfo r0 = r0.fieldInfo     // Catch:{ all -> 0x0472 }
            r27 = r0
            r0 = r27
            java.lang.Class<?> r0 = r0.fieldClass     // Catch:{ all -> 0x0472 }
            r21 = r0
        L_0x0151:
            r42 = 0
        L_0x0153:
            if (r22 != 0) goto L_0x016f
            r0 = r26
            r1 = r50
            if (r0 >= r1) goto L_0x01e9
            r0 = r59
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r4 = r0.sortedFieldDeserializers     // Catch:{ all -> 0x0472 }
            r22 = r4[r26]     // Catch:{ all -> 0x0472 }
            r0 = r22
            com.alibaba.json.util.FieldInfo r0 = r0.fieldInfo     // Catch:{ all -> 0x0472 }
            r27 = r0
            r0 = r27
            java.lang.Class<?> r0 = r0.fieldClass     // Catch:{ all -> 0x0472 }
            r21 = r0
            int r26 = r26 + 1
        L_0x016f:
            r41 = 0
            r58 = 0
            r28 = 0
            r32 = 0
            r34 = 0
            r29 = 0
            r30 = 0
            if (r22 == 0) goto L_0x01a3
            r0 = r27
            long r0 = r0.nameHashCode     // Catch:{ all -> 0x0472 }
            r24 = r0
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0472 }
            r0 = r21
            if (r0 == r4) goto L_0x0191
            java.lang.Class<java.lang.Integer> r4 = java.lang.Integer.class
            r0 = r21
            if (r0 != r4) goto L_0x01fb
        L_0x0191:
            r0 = r39
            r1 = r24
            int r32 = r0.scanFieldInt(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x01ec
            r41 = 1
            r58 = 1
        L_0x01a3:
            if (r41 != 0) goto L_0x0610
            r0 = r60
            com.alibaba.json.parser.SymbolTable r4 = r0.symbolTable     // Catch:{ all -> 0x0472 }
            r0 = r39
            java.lang.String r6 = r0.scanSymbol(r4)     // Catch:{ all -> 0x0472 }
            if (r6 != 0) goto L_0x042e
            r0 = r39
            int r0 = r0.token     // Catch:{ all -> 0x0472 }
            r52 = r0
            r4 = 13
            r0 = r52
            if (r0 != r4) goto L_0x0428
            r4 = 16
            r0 = r39
            r0.nextToken(r4)     // Catch:{ all -> 0x0472 }
            r9 = r33
        L_0x01c6:
            if (r63 != 0) goto L_0x08e7
            if (r9 != 0) goto L_0x0818
            java.lang.Object r63 = r59.createInstance(r60, r61)     // Catch:{ all -> 0x0100 }
            if (r11 != 0) goto L_0x01da
            r0 = r60
            r1 = r63
            r2 = r62
            com.alibaba.json.parser.ParseContext r11 = r0.setContext(r12, r1, r2)     // Catch:{ all -> 0x0100 }
        L_0x01da:
            if (r11 == 0) goto L_0x01e0
            r0 = r63
            r11.object = r0
        L_0x01e0:
            r0 = r60
            r0.setContext(r12)
            r56 = r63
            goto L_0x0010
        L_0x01e9:
            int r26 = r26 + 1
            goto L_0x016f
        L_0x01ec:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x01fb:
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ all -> 0x0472 }
            r0 = r21
            if (r0 == r4) goto L_0x0207
            java.lang.Class<java.lang.Long> r4 = java.lang.Long.class
            r0 = r21
            if (r0 != r4) goto L_0x0229
        L_0x0207:
            r0 = r39
            r1 = r24
            long r34 = r0.scanFieldLong(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x021a
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x021a:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x0229:
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r0 = r21
            if (r0 != r4) goto L_0x0252
            r0 = r39
            r1 = r24
            java.lang.String r28 = r0.scanFieldString(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x0243
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x0243:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x0252:
            java.lang.Class<java.util.Date> r4 = java.util.Date.class
            r0 = r21
            if (r0 != r4) goto L_0x027b
            r0 = r39
            r1 = r24
            java.util.Date r28 = r0.scanFieldDate(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x026c
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x026c:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x027b:
            java.lang.Class r4 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0472 }
            r0 = r21
            if (r0 == r4) goto L_0x0287
            java.lang.Class<java.lang.Boolean> r4 = java.lang.Boolean.class
            r0 = r21
            if (r0 != r4) goto L_0x02ae
        L_0x0287:
            r0 = r39
            r1 = r24
            boolean r4 = r0.scanFieldBoolean(r1)     // Catch:{ all -> 0x0472 }
            java.lang.Boolean r28 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x029f
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x029f:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x02ae:
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ all -> 0x0472 }
            r0 = r21
            if (r0 == r4) goto L_0x02ba
            java.lang.Class<java.lang.Float> r4 = java.lang.Float.class
            r0 = r21
            if (r0 != r4) goto L_0x02dd
        L_0x02ba:
            r0 = r39
            r1 = r24
            float r29 = r0.scanFieldFloat(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x02ce
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x02ce:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x02dd:
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ all -> 0x0472 }
            r0 = r21
            if (r0 == r4) goto L_0x02e9
            java.lang.Class<java.lang.Double> r4 = java.lang.Double.class
            r0 = r21
            if (r0 != r4) goto L_0x030c
        L_0x02e9:
            r0 = r39
            r1 = r24
            double r30 = r0.scanFieldDouble(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x02fd
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x02fd:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x030c:
            r0 = r27
            boolean r4 = r0.isEnum     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x034b
            r0 = r60
            com.alibaba.json.parser.ParserConfig r4 = r0.config     // Catch:{ all -> 0x0472 }
            r0 = r21
            com.alibaba.json.parser.deserializer.ObjectDeserializer r4 = r4.getDeserializer(r0)     // Catch:{ all -> 0x0472 }
            boolean r4 = r4 instanceof com.alibaba.json.parser.EnumDeserializer     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x034b
            r0 = r39
            r1 = r24
            long r18 = r0.scanFieldSymbol(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x033c
            r41 = 1
            r58 = 1
            r0 = r22
            r1 = r18
            java.lang.Enum r28 = r0.getEnumByHashCode(r1)     // Catch:{ all -> 0x0472 }
            goto L_0x01a3
        L_0x033c:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x034b:
            java.lang.Class<int[]> r4 = int[].class
            r0 = r21
            if (r0 != r4) goto L_0x0374
            r0 = r39
            r1 = r24
            int[] r28 = r0.scanFieldIntArray(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x0365
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x0365:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x0374:
            java.lang.Class<float[]> r4 = float[].class
            r0 = r21
            if (r0 != r4) goto L_0x039d
            r0 = r39
            r1 = r24
            float[] r28 = r0.scanFieldFloatArray(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x038e
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x038e:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x039d:
            java.lang.Class<double[]> r4 = double[].class
            r0 = r21
            if (r0 != r4) goto L_0x03c6
            r0 = r39
            r1 = r24
            double[] r28 = r0.scanFieldDoubleArray(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x03b7
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x03b7:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x03c6:
            java.lang.Class<float[][]> r4 = float[][].class
            r0 = r21
            if (r0 != r4) goto L_0x03ef
            r0 = r39
            r1 = r24
            float[][] r28 = r0.scanFieldFloatArray2(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x03e0
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x03e0:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x03ef:
            java.lang.Class<double[][]> r4 = double[][].class
            r0 = r21
            if (r0 != r4) goto L_0x0418
            r0 = r39
            r1 = r24
            double[][] r28 = r0.scanFieldDoubleArray2(r1)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            if (r4 <= 0) goto L_0x0409
            r41 = 1
            r58 = 1
            goto L_0x01a3
        L_0x0409:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0472 }
            r5 = -2
            if (r4 != r5) goto L_0x01a3
            r0 = r39
            long r0 = r0.fieldHash     // Catch:{ all -> 0x0472 }
            r42 = r0
            goto L_0x012e
        L_0x0418:
            r0 = r27
            long r4 = r0.nameHashCode     // Catch:{ all -> 0x0472 }
            r0 = r39
            boolean r4 = r0.matchField(r4)     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x012e
            r41 = 1
            goto L_0x01a3
        L_0x0428:
            r4 = 16
            r0 = r52
            if (r0 == r4) goto L_0x012e
        L_0x042e:
            java.lang.String r4 = "$ref"
            if (r4 != r6) goto L_0x0533
            if (r12 == 0) goto L_0x0533
            r4 = 58
            r0 = r39
            r0.nextTokenWithChar(r4)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r0 = r0.token     // Catch:{ all -> 0x0472 }
            r52 = r0
            r4 = 4
            r0 = r52
            if (r0 != r4) goto L_0x04f6
            java.lang.String r48 = r39.stringVal()     // Catch:{ all -> 0x0472 }
            java.lang.String r4 = "@"
            r0 = r48
            boolean r4 = r4.equals(r0)     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x0477
            java.lang.Object r0 = r12.object     // Catch:{ all -> 0x0472 }
            r63 = r0
        L_0x045a:
            r4 = 13
            r0 = r39
            r0.nextToken(r4)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0472 }
            r5 = 13
            if (r4 == r5) goto L_0x0514
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0472 }
            java.lang.String r5 = "illegal ref"
            r4.<init>(r5)     // Catch:{ all -> 0x0472 }
            throw r4     // Catch:{ all -> 0x0472 }
        L_0x0472:
            r4 = move-exception
            r9 = r33
            goto L_0x0101
        L_0x0477:
            java.lang.String r4 = ".."
            r0 = r48
            boolean r4 = r4.equals(r0)     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x04a7
            com.alibaba.json.parser.ParseContext r0 = r12.parent     // Catch:{ all -> 0x0472 }
            r47 = r0
            r0 = r47
            java.lang.Object r4 = r0.object     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x0493
            r0 = r47
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0472 }
            r63 = r0
            goto L_0x045a
        L_0x0493:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0472 }
            r0 = r47
            r1 = r48
            r4.<init>(r0, r1)     // Catch:{ all -> 0x0472 }
            r0 = r60
            r0.addResolveTask(r4)     // Catch:{ all -> 0x0472 }
            r4 = 1
            r0 = r60
            r0.resolveStatus = r4     // Catch:{ all -> 0x0472 }
            goto L_0x045a
        L_0x04a7:
            java.lang.String r4 = "$"
            r0 = r48
            boolean r4 = r4.equals(r0)     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x04e3
            r49 = r12
        L_0x04b4:
            r0 = r49
            com.alibaba.json.parser.ParseContext r4 = r0.parent     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x04c1
            r0 = r49
            com.alibaba.json.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0472 }
            r49 = r0
            goto L_0x04b4
        L_0x04c1:
            r0 = r49
            java.lang.Object r4 = r0.object     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x04ce
            r0 = r49
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0472 }
            r63 = r0
            goto L_0x045a
        L_0x04ce:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0472 }
            r0 = r49
            r1 = r48
            r4.<init>(r0, r1)     // Catch:{ all -> 0x0472 }
            r0 = r60
            r0.addResolveTask(r4)     // Catch:{ all -> 0x0472 }
            r4 = 1
            r0 = r60
            r0.resolveStatus = r4     // Catch:{ all -> 0x0472 }
            goto L_0x045a
        L_0x04e3:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0472 }
            r0 = r48
            r4.<init>(r12, r0)     // Catch:{ all -> 0x0472 }
            r0 = r60
            r0.addResolveTask(r4)     // Catch:{ all -> 0x0472 }
            r4 = 1
            r0 = r60
            r0.resolveStatus = r4     // Catch:{ all -> 0x0472 }
            goto L_0x045a
        L_0x04f6:
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0472 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0472 }
            r5.<init>()     // Catch:{ all -> 0x0472 }
            java.lang.String r7 = "illegal ref, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0472 }
            java.lang.String r7 = com.alibaba.json.parser.JSONToken.name(r52)     // Catch:{ all -> 0x0472 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0472 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0472 }
            r4.<init>(r5)     // Catch:{ all -> 0x0472 }
            throw r4     // Catch:{ all -> 0x0472 }
        L_0x0514:
            r4 = 16
            r0 = r39
            r0.nextToken(r4)     // Catch:{ all -> 0x0472 }
            r0 = r60
            r1 = r63
            r2 = r62
            r0.setContext(r12, r1, r2)     // Catch:{ all -> 0x0472 }
            if (r11 == 0) goto L_0x052a
            r0 = r63
            r11.object = r0
        L_0x052a:
            r0 = r60
            r0.setContext(r12)
            r56 = r63
            goto L_0x0010
        L_0x0533:
            if (r53 == 0) goto L_0x053d
            r0 = r53
            boolean r4 = r0.equals(r6)     // Catch:{ all -> 0x0472 }
            if (r4 != 0) goto L_0x0542
        L_0x053d:
            java.lang.String r4 = "@type"
            if (r4 != r6) goto L_0x0610
        L_0x0542:
            r4 = 58
            r0 = r39
            r0.nextTokenWithChar(r4)     // Catch:{ all -> 0x0472 }
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0472 }
            r5 = 4
            if (r4 != r5) goto L_0x0607
            java.lang.String r55 = r39.stringVal()     // Catch:{ all -> 0x0472 }
            r4 = 16
            r0 = r39
            r0.nextToken(r4)     // Catch:{ all -> 0x0472 }
            r0 = r61
            boolean r4 = r0 instanceof java.lang.Class     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x0581
            r0 = r61
            java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ all -> 0x0472 }
            r4 = r0
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x0472 }
            r0 = r55
            boolean r4 = r0.equals(r4)     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x0581
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0472 }
            r5 = 13
            if (r4 != r5) goto L_0x012e
            r39.nextToken()     // Catch:{ all -> 0x0472 }
            r9 = r33
            goto L_0x01c6
        L_0x0581:
            r0 = r60
            com.alibaba.json.parser.ParserConfig r4 = r0.config     // Catch:{ all -> 0x0472 }
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r5 = r0.beanInfo     // Catch:{ all -> 0x0472 }
            r0 = r59
            r1 = r55
            com.alibaba.json.parser.JavaBeanDeserializer r13 = r0.getSeeAlso(r4, r5, r1)     // Catch:{ all -> 0x0472 }
            r57 = 0
            if (r13 != 0) goto L_0x05bd
            r0 = r60
            com.alibaba.json.parser.ParserConfig r4 = r0.config     // Catch:{ all -> 0x0472 }
            java.lang.ClassLoader r4 = r4.defaultClassLoader     // Catch:{ all -> 0x0472 }
            r0 = r55
            java.lang.Class r57 = com.alibaba.json.util.TypeUtils.loadClass(r0, r4)     // Catch:{ all -> 0x0472 }
            java.lang.Class r20 = com.alibaba.json.util.TypeUtils.getClass(r61)     // Catch:{ all -> 0x0472 }
            if (r20 == 0) goto L_0x05b3
            if (r57 == 0) goto L_0x05f3
            r0 = r20
            r1 = r57
            boolean r4 = r0.isAssignableFrom(r1)     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x05f3
        L_0x05b3:
            r0 = r60
            com.alibaba.json.parser.ParserConfig r4 = r0.config     // Catch:{ all -> 0x0472 }
            r0 = r57
            com.alibaba.json.parser.deserializer.ObjectDeserializer r13 = r4.getDeserializer(r0)     // Catch:{ all -> 0x0472 }
        L_0x05bd:
            boolean r4 = r13 instanceof com.alibaba.json.parser.JavaBeanDeserializer     // Catch:{ all -> 0x0472 }
            if (r4 == 0) goto L_0x05fc
            r0 = r13
            com.alibaba.json.parser.JavaBeanDeserializer r0 = (com.alibaba.json.parser.JavaBeanDeserializer) r0     // Catch:{ all -> 0x0472 }
            r38 = r0
            r4 = 0
            r0 = r38
            r1 = r60
            r2 = r57
            r3 = r62
            java.lang.Object r56 = r0.deserialze(r1, r2, r3, r4)     // Catch:{ all -> 0x0472 }
            if (r53 == 0) goto L_0x05e6
            r0 = r38
            r1 = r53
            com.alibaba.json.parser.deserializer.FieldDeserializer r54 = r0.getFieldDeserializer(r1)     // Catch:{ all -> 0x0472 }
            r0 = r54
            r1 = r56
            r2 = r55
            r0.setValue(r1, r2)     // Catch:{ all -> 0x0472 }
        L_0x05e6:
            if (r11 == 0) goto L_0x05ec
            r0 = r63
            r11.object = r0
        L_0x05ec:
            r0 = r60
            r0.setContext(r12)
            goto L_0x0010
        L_0x05f3:
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0472 }
            java.lang.String r5 = "type not match"
            r4.<init>(r5)     // Catch:{ all -> 0x0472 }
            throw r4     // Catch:{ all -> 0x0472 }
        L_0x05fc:
            r0 = r60
            r1 = r57
            r2 = r62
            java.lang.Object r56 = r13.deserialze(r0, r1, r2)     // Catch:{ all -> 0x0472 }
            goto L_0x05e6
        L_0x0607:
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0472 }
            java.lang.String r5 = "syntax error"
            r4.<init>(r5)     // Catch:{ all -> 0x0472 }
            throw r4     // Catch:{ all -> 0x0472 }
        L_0x0610:
            if (r63 != 0) goto L_0x0923
            if (r33 != 0) goto L_0x0923
            java.lang.Object r63 = r59.createInstance(r60, r61)     // Catch:{ all -> 0x0472 }
            if (r63 != 0) goto L_0x091f
            java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x0472 }
            r0 = r59
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r4 = r0.fieldDeserializers     // Catch:{ all -> 0x0472 }
            int r4 = r4.length     // Catch:{ all -> 0x0472 }
            r9.<init>(r4)     // Catch:{ all -> 0x0472 }
        L_0x0624:
            if (r14 != 0) goto L_0x0630
            r0 = r60
            r1 = r63
            r2 = r62
            com.alibaba.json.parser.ParseContext r11 = r0.setContext(r12, r1, r2)     // Catch:{ all -> 0x0100 }
        L_0x0630:
            if (r41 == 0) goto L_0x07a6
            if (r58 != 0) goto L_0x064b
            r0 = r22
            r1 = r60
            r2 = r63
            r3 = r61
            r0.parseField(r1, r2, r3, r9)     // Catch:{ all -> 0x0100 }
        L_0x063f:
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0100 }
            r5 = 16
            if (r4 != r5) goto L_0x07d2
            r33 = r9
            goto L_0x012e
        L_0x064b:
            if (r63 != 0) goto L_0x06a8
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0100 }
            r0 = r21
            if (r0 == r4) goto L_0x0659
            java.lang.Class<java.lang.Integer> r4 = java.lang.Integer.class
            r0 = r21
            if (r0 != r4) goto L_0x066f
        L_0x0659:
            java.lang.Integer r28 = java.lang.Integer.valueOf(r32)     // Catch:{ all -> 0x0100 }
        L_0x065d:
            r0 = r27
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x0100 }
            r0 = r28
            r9.put(r4, r0)     // Catch:{ all -> 0x0100 }
        L_0x0666:
            r0 = r39
            int r4 = r0.matchStat     // Catch:{ all -> 0x0100 }
            r5 = 4
            if (r4 != r5) goto L_0x063f
            goto L_0x01c6
        L_0x066f:
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ all -> 0x0100 }
            r0 = r21
            if (r0 == r4) goto L_0x067b
            java.lang.Class<java.lang.Long> r4 = java.lang.Long.class
            r0 = r21
            if (r0 != r4) goto L_0x0680
        L_0x067b:
            java.lang.Long r28 = java.lang.Long.valueOf(r34)     // Catch:{ all -> 0x0100 }
            goto L_0x065d
        L_0x0680:
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ all -> 0x0100 }
            r0 = r21
            if (r0 == r4) goto L_0x068c
            java.lang.Class<java.lang.Float> r4 = java.lang.Float.class
            r0 = r21
            if (r0 != r4) goto L_0x0692
        L_0x068c:
            java.lang.Float r28 = new java.lang.Float     // Catch:{ all -> 0x0100 }
            r28.<init>(r29)     // Catch:{ all -> 0x0100 }
            goto L_0x065d
        L_0x0692:
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ all -> 0x0100 }
            r0 = r21
            if (r0 == r4) goto L_0x069e
            java.lang.Class<java.lang.Double> r4 = java.lang.Double.class
            r0 = r21
            if (r0 != r4) goto L_0x065d
        L_0x069e:
            java.lang.Double r28 = new java.lang.Double     // Catch:{ all -> 0x0100 }
            r0 = r28
            r1 = r30
            r0.<init>(r1)     // Catch:{ all -> 0x0100 }
            goto L_0x065d
        L_0x06a8:
            if (r28 != 0) goto L_0x079b
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 == r4) goto L_0x06b6
            java.lang.Class<java.lang.Integer> r4 = java.lang.Integer.class
            r0 = r21
            if (r0 != r4) goto L_0x06fa
        L_0x06b6:
            r0 = r27
            boolean r4 = r0.fieldAccess     // Catch:{ IllegalAccessException -> 0x06cc }
            if (r4 == 0) goto L_0x06ed
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 != r4) goto L_0x06ed
            r0 = r22
            r1 = r63
            r2 = r32
            r0.setValue(r1, r2)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x06cc:
            r17 = move-exception
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0100 }
            r5.<init>()     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = "set property error, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            r0 = r27
            java.lang.String r7 = r0.name     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0100 }
            r0 = r17
            r4.<init>(r5, r0)     // Catch:{ all -> 0x0100 }
            throw r4     // Catch:{ all -> 0x0100 }
        L_0x06ed:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r32)     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r22
            r1 = r63
            r0.setValue(r1, r4)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x06fa:
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 == r4) goto L_0x0706
            java.lang.Class<java.lang.Long> r4 = java.lang.Long.class
            r0 = r21
            if (r0 != r4) goto L_0x072a
        L_0x0706:
            r0 = r27
            boolean r4 = r0.fieldAccess     // Catch:{ IllegalAccessException -> 0x06cc }
            if (r4 == 0) goto L_0x071d
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 != r4) goto L_0x071d
            r0 = r22
            r1 = r63
            r2 = r34
            r0.setValue(r1, r2)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x071d:
            java.lang.Long r4 = java.lang.Long.valueOf(r34)     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r22
            r1 = r63
            r0.setValue(r1, r4)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x072a:
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 == r4) goto L_0x0736
            java.lang.Class<java.lang.Float> r4 = java.lang.Float.class
            r0 = r21
            if (r0 != r4) goto L_0x075d
        L_0x0736:
            r0 = r27
            boolean r4 = r0.fieldAccess     // Catch:{ IllegalAccessException -> 0x06cc }
            if (r4 == 0) goto L_0x074d
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 != r4) goto L_0x074d
            r0 = r22
            r1 = r63
            r2 = r29
            r0.setValue(r1, r2)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x074d:
            java.lang.Float r4 = new java.lang.Float     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r29
            r4.<init>(r0)     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r22
            r1 = r63
            r0.setValue(r1, r4)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x075d:
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 == r4) goto L_0x0769
            java.lang.Class<java.lang.Double> r4 = java.lang.Double.class
            r0 = r21
            if (r0 != r4) goto L_0x0790
        L_0x0769:
            r0 = r27
            boolean r4 = r0.fieldAccess     // Catch:{ IllegalAccessException -> 0x06cc }
            if (r4 == 0) goto L_0x0780
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r21
            if (r0 != r4) goto L_0x0780
            r0 = r22
            r1 = r63
            r2 = r30
            r0.setValue(r1, r2)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x0780:
            java.lang.Double r4 = new java.lang.Double     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r30
            r4.<init>(r0)     // Catch:{ IllegalAccessException -> 0x06cc }
            r0 = r22
            r1 = r63
            r0.setValue(r1, r4)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x0790:
            r0 = r22
            r1 = r63
            r2 = r28
            r0.setValue(r1, r2)     // Catch:{ IllegalAccessException -> 0x06cc }
            goto L_0x0666
        L_0x079b:
            r0 = r22
            r1 = r63
            r2 = r28
            r0.setValue(r1, r2)     // Catch:{ all -> 0x0100 }
            goto L_0x0666
        L_0x07a6:
            r4 = r59
            r5 = r60
            r7 = r63
            r8 = r61
            boolean r40 = r4.parseField(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0100 }
            if (r40 != 0) goto L_0x07c1
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0100 }
            r5 = 13
            if (r4 != r5) goto L_0x091b
            r39.nextToken()     // Catch:{ all -> 0x0100 }
            goto L_0x01c6
        L_0x07c1:
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0100 }
            r5 = 17
            if (r4 != r5) goto L_0x063f
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = "syntax error, unexpect token ':'"
            r4.<init>(r5)     // Catch:{ all -> 0x0100 }
            throw r4     // Catch:{ all -> 0x0100 }
        L_0x07d2:
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0100 }
            r5 = 13
            if (r4 != r5) goto L_0x07e3
            r4 = 16
            r0 = r39
            r0.nextToken(r4)     // Catch:{ all -> 0x0100 }
            goto L_0x01c6
        L_0x07e3:
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0100 }
            r5 = 18
            if (r4 == r5) goto L_0x07f2
            r0 = r39
            int r4 = r0.token     // Catch:{ all -> 0x0100 }
            r5 = 1
            if (r4 != r5) goto L_0x0814
        L_0x07f2:
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0100 }
            r5.<init>()     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = "syntax error, unexpect token "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            r0 = r39
            int r7 = r0.token     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = com.alibaba.json.parser.JSONToken.name(r7)     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0100 }
            r4.<init>(r5)     // Catch:{ all -> 0x0100 }
            throw r4     // Catch:{ all -> 0x0100 }
        L_0x0814:
            r33 = r9
            goto L_0x012e
        L_0x0818:
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            java.lang.String[] r0 = r4.creatorConstructorParameters     // Catch:{ all -> 0x0100 }
            r45 = r0
            if (r45 == 0) goto L_0x0858
            r0 = r45
            int r0 = r0.length     // Catch:{ all -> 0x0100 }
            r50 = r0
        L_0x0827:
            r0 = r50
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0100 }
            r46 = r0
            r36 = 0
        L_0x082f:
            r0 = r36
            r1 = r50
            if (r0 >= r1) goto L_0x0869
            r0 = r59
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r4 = r0.fieldDeserializers     // Catch:{ all -> 0x0100 }
            r4 = r4[r36]     // Catch:{ all -> 0x0100 }
            com.alibaba.json.util.FieldInfo r0 = r4.fieldInfo     // Catch:{ all -> 0x0100 }
            r27 = r0
            if (r45 == 0) goto L_0x0860
            r0 = r27
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x0100 }
            java.lang.Object r44 = r9.remove(r4)     // Catch:{ all -> 0x0100 }
        L_0x0849:
            if (r44 != 0) goto L_0x0853
            r0 = r27
            java.lang.Class<?> r4 = r0.fieldClass     // Catch:{ all -> 0x0100 }
            java.lang.Object r44 = com.alibaba.json.util.TypeUtils.defaultValue(r4)     // Catch:{ all -> 0x0100 }
        L_0x0853:
            r46[r36] = r44     // Catch:{ all -> 0x0100 }
            int r36 = r36 + 1
            goto L_0x082f
        L_0x0858:
            r0 = r59
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r4 = r0.fieldDeserializers     // Catch:{ all -> 0x0100 }
            int r0 = r4.length     // Catch:{ all -> 0x0100 }
            r50 = r0
            goto L_0x0827
        L_0x0860:
            r0 = r27
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x0100 }
            java.lang.Object r44 = r9.get(r4)     // Catch:{ all -> 0x0100 }
            goto L_0x0849
        L_0x0869:
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            java.lang.reflect.Constructor<?> r4 = r4.creatorConstructor     // Catch:{ all -> 0x0100 }
            if (r4 == 0) goto L_0x08d2
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ Exception -> 0x08ad }
            java.lang.reflect.Constructor<?> r4 = r4.creatorConstructor     // Catch:{ Exception -> 0x08ad }
            r0 = r46
            java.lang.Object r63 = r4.newInstance(r0)     // Catch:{ Exception -> 0x08ad }
            if (r45 == 0) goto L_0x08e7
            java.util.Set r4 = r9.entrySet()     // Catch:{ all -> 0x0100 }
            java.util.Iterator r5 = r4.iterator()     // Catch:{ all -> 0x0100 }
        L_0x0887:
            boolean r4 = r5.hasNext()     // Catch:{ all -> 0x0100 }
            if (r4 == 0) goto L_0x08e7
            java.lang.Object r16 = r5.next()     // Catch:{ all -> 0x0100 }
            java.util.Map$Entry r16 = (java.util.Map.Entry) r16     // Catch:{ all -> 0x0100 }
            java.lang.Object r4 = r16.getKey()     // Catch:{ all -> 0x0100 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x0100 }
            r0 = r59
            com.alibaba.json.parser.deserializer.FieldDeserializer r23 = r0.getFieldDeserializer(r4)     // Catch:{ all -> 0x0100 }
            if (r23 == 0) goto L_0x0887
            java.lang.Object r4 = r16.getValue()     // Catch:{ all -> 0x0100 }
            r0 = r23
            r1 = r63
            r0.setValue(r1, r4)     // Catch:{ all -> 0x0100 }
            goto L_0x0887
        L_0x08ad:
            r15 = move-exception
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0100 }
            r5.<init>()     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = "create instance error, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r7 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            java.lang.reflect.Constructor<?> r7 = r7.creatorConstructor     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = r7.toGenericString()     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0100 }
            r4.<init>(r5, r15)     // Catch:{ all -> 0x0100 }
            throw r4     // Catch:{ all -> 0x0100 }
        L_0x08d2:
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            java.lang.reflect.Method r4 = r4.factoryMethod     // Catch:{ all -> 0x0100 }
            if (r4 == 0) goto L_0x08e7
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ Exception -> 0x08f6 }
            java.lang.reflect.Method r4 = r4.factoryMethod     // Catch:{ Exception -> 0x08f6 }
            r5 = 0
            r0 = r46
            java.lang.Object r63 = r4.invoke(r5, r0)     // Catch:{ Exception -> 0x08f6 }
        L_0x08e7:
            if (r11 == 0) goto L_0x08ed
            r0 = r63
            r11.object = r0
        L_0x08ed:
            r0 = r60
            r0.setContext(r12)
            r56 = r63
            goto L_0x0010
        L_0x08f6:
            r15 = move-exception
            com.alibaba.json.JSONException r4 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0100 }
            r5.<init>()     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = "create factory method error, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            r0 = r59
            com.alibaba.json.parser.JavaBeanInfo r7 = r0.beanInfo     // Catch:{ all -> 0x0100 }
            java.lang.reflect.Method r7 = r7.factoryMethod     // Catch:{ all -> 0x0100 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0100 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0100 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0100 }
            r4.<init>(r5, r15)     // Catch:{ all -> 0x0100 }
            throw r4     // Catch:{ all -> 0x0100 }
        L_0x091b:
            r33 = r9
            goto L_0x012e
        L_0x091f:
            r9 = r33
            goto L_0x0624
        L_0x0923:
            r9 = r33
            goto L_0x0630
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JavaBeanDeserializer.deserialze(com.alibaba.json.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public FieldDeserializer getFieldDeserializerByHash(long fieldHash) {
        for (FieldDeserializer fieldDeserializer : this.sortedFieldDeserializers) {
            if (fieldDeserializer.fieldInfo.nameHashCode == fieldHash) {
                return fieldDeserializer;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public FieldDeserializer getFieldDeserializer(String key) {
        if (key == null) {
            return null;
        }
        if (this.beanInfo.ordered) {
            for (FieldDeserializer fieldDeserializer : this.sortedFieldDeserializers) {
                if (fieldDeserializer.fieldInfo.name.equalsIgnoreCase(key)) {
                    return fieldDeserializer;
                }
            }
            return null;
        }
        int low = 0;
        int high = this.sortedFieldDeserializers.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = this.sortedFieldDeserializers[mid].fieldInfo.name.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp <= 0) {
                return this.sortedFieldDeserializers[mid];
            } else {
                high = mid - 1;
            }
        }
        if (this.alterNameFieldDeserializers != null) {
            return (FieldDeserializer) this.alterNameFieldDeserializers.get(key);
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r18v0, types: [com.alibaba.json.parser.deserializer.FieldDeserializer] */
    /* JADX WARNING: type inference failed for: r18v1 */
    /* JADX WARNING: type inference failed for: r18v2 */
    /* JADX WARNING: type inference failed for: r0v5, types: [com.alibaba.json.parser.deserializer.FieldDeserializer] */
    /* JADX WARNING: type inference failed for: r0v14, types: [com.alibaba.json.parser.DefaultFieldDeserializer] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r18v5, types: [com.alibaba.json.parser.deserializer.FieldDeserializer] */
    /* JADX WARNING: type inference failed for: r7v27, types: [com.alibaba.json.parser.deserializer.FieldDeserializer[]] */
    /* JADX WARNING: type inference failed for: r18v6 */
    /* JADX WARNING: type inference failed for: r0v48, types: [com.alibaba.json.parser.DefaultFieldDeserializer] */
    /* JADX WARNING: type inference failed for: r18v7 */
    /* JADX WARNING: type inference failed for: r18v8 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r18v0, names: [fieldDeserializer], types: [com.alibaba.json.parser.deserializer.FieldDeserializer]
      assigns: [com.alibaba.json.parser.deserializer.FieldDeserializer, com.alibaba.json.parser.DefaultFieldDeserializer]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], ?[OBJECT, ARRAY], com.alibaba.json.parser.DefaultFieldDeserializer]
      mth insns count: 183
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean parseField(com.alibaba.json.parser.DefaultJSONParser r33, java.lang.String r34, java.lang.Object r35, java.lang.reflect.Type r36, java.util.Map<java.lang.String, java.lang.Object> r37) {
        /*
            r32 = this;
            r0 = r33
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer
            r24 = r0
            r0 = r32
            r1 = r34
            com.alibaba.json.parser.deserializer.FieldDeserializer r18 = r0.getFieldDeserializer(r1)
            if (r18 != 0) goto L_0x00ca
            long r30 = com.alibaba.json.util.TypeUtils.fnv_64_lower(r34)
            r0 = r32
            long[] r7 = r0.smartMatchHashArray
            if (r7 != 0) goto L_0x004a
            r0 = r32
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r7 = r0.sortedFieldDeserializers
            int r7 = r7.length
            long[] r0 = new long[r7]
            r22 = r0
            r23 = 0
        L_0x0025:
            r0 = r32
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r7 = r0.sortedFieldDeserializers
            int r7 = r7.length
            r0 = r23
            if (r0 >= r7) goto L_0x0041
            r0 = r32
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r7 = r0.sortedFieldDeserializers
            r7 = r7[r23]
            com.alibaba.json.util.FieldInfo r7 = r7.fieldInfo
            java.lang.String r7 = r7.name
            long r8 = com.alibaba.json.util.TypeUtils.fnv_64_lower(r7)
            r22[r23] = r8
            int r23 = r23 + 1
            goto L_0x0025
        L_0x0041:
            java.util.Arrays.sort(r22)
            r0 = r22
            r1 = r32
            r1.smartMatchHashArray = r0
        L_0x004a:
            r0 = r32
            long[] r7 = r0.smartMatchHashArray
            r0 = r30
            int r28 = java.util.Arrays.binarySearch(r7, r0)
            if (r28 >= 0) goto L_0x0076
            java.lang.String r7 = "is"
            r0 = r34
            boolean r7 = r0.startsWith(r7)
            if (r7 == 0) goto L_0x0076
            r7 = 2
            r0 = r34
            java.lang.String r7 = r0.substring(r7)
            long r30 = com.alibaba.json.util.TypeUtils.fnv_64_lower(r7)
            r0 = r32
            long[] r7 = r0.smartMatchHashArray
            r0 = r30
            int r28 = java.util.Arrays.binarySearch(r7, r0)
        L_0x0076:
            if (r28 < 0) goto L_0x00ca
            r0 = r32
            int[] r7 = r0.smartMatchHashArrayMapping
            if (r7 != 0) goto L_0x00bb
            r0 = r32
            long[] r7 = r0.smartMatchHashArray
            int r7 = r7.length
            int[] r0 = new int[r7]
            r25 = r0
            r7 = -1
            r0 = r25
            java.util.Arrays.fill(r0, r7)
            r23 = 0
        L_0x008f:
            r0 = r32
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r7 = r0.sortedFieldDeserializers
            int r7 = r7.length
            r0 = r23
            if (r0 >= r7) goto L_0x00b5
            r0 = r32
            long[] r7 = r0.smartMatchHashArray
            r0 = r32
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r8 = r0.sortedFieldDeserializers
            r8 = r8[r23]
            com.alibaba.json.util.FieldInfo r8 = r8.fieldInfo
            java.lang.String r8 = r8.name
            long r8 = com.alibaba.json.util.TypeUtils.fnv_64_lower(r8)
            int r27 = java.util.Arrays.binarySearch(r7, r8)
            if (r27 < 0) goto L_0x00b2
            r25[r27] = r23
        L_0x00b2:
            int r23 = r23 + 1
            goto L_0x008f
        L_0x00b5:
            r0 = r25
            r1 = r32
            r1.smartMatchHashArrayMapping = r0
        L_0x00bb:
            r0 = r32
            int[] r7 = r0.smartMatchHashArrayMapping
            r15 = r7[r28]
            r7 = -1
            if (r15 == r7) goto L_0x00ca
            r0 = r32
            com.alibaba.json.parser.deserializer.FieldDeserializer[] r7 = r0.sortedFieldDeserializers
            r18 = r7[r15]
        L_0x00ca:
            com.alibaba.json.parser.Feature r7 = com.alibaba.json.parser.Feature.SupportNonPublicField
            int r0 = r7.mask
            r26 = r0
            if (r18 != 0) goto L_0x0153
            r0 = r33
            com.alibaba.json.parser.JSONLexer r7 = r0.lexer
            int r7 = r7.features
            r7 = r7 & r26
            if (r7 != 0) goto L_0x00e6
            r0 = r32
            com.alibaba.json.parser.JavaBeanInfo r7 = r0.beanInfo
            int r7 = r7.parserFeatures
            r7 = r7 & r26
            if (r7 == 0) goto L_0x0153
        L_0x00e6:
            r0 = r32
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> r7 = r0.extraFieldDeserializers
            if (r7 != 0) goto L_0x013d
            java.util.concurrent.ConcurrentHashMap r17 = new java.util.concurrent.ConcurrentHashMap
            r7 = 1
            r8 = 1061158912(0x3f400000, float:0.75)
            r9 = 1
            r0 = r17
            r0.<init>(r7, r8, r9)
            r0 = r32
            java.lang.Class<?> r14 = r0.clazz
        L_0x00fb:
            if (r14 == 0) goto L_0x0137
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            if (r14 == r7) goto L_0x0137
            java.lang.reflect.Field[] r21 = r14.getDeclaredFields()
            r0 = r21
            int r8 = r0.length
            r7 = 0
        L_0x0109:
            if (r7 >= r8) goto L_0x0132
            r11 = r21[r7]
            java.lang.String r20 = r11.getName()
            r0 = r32
            r1 = r20
            com.alibaba.json.parser.deserializer.FieldDeserializer r9 = r0.getFieldDeserializer(r1)
            if (r9 == 0) goto L_0x011e
        L_0x011b:
            int r7 = r7 + 1
            goto L_0x0109
        L_0x011e:
            int r19 = r11.getModifiers()
            r9 = r19 & 16
            if (r9 != 0) goto L_0x011b
            r9 = r19 & 8
            if (r9 != 0) goto L_0x011b
            r0 = r17
            r1 = r20
            r0.put(r1, r11)
            goto L_0x011b
        L_0x0132:
            java.lang.Class r14 = r14.getSuperclass()
            goto L_0x00fb
        L_0x0137:
            r0 = r17
            r1 = r32
            r1.extraFieldDeserializers = r0
        L_0x013d:
            r0 = r32
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> r7 = r0.extraFieldDeserializers
            r0 = r34
            java.lang.Object r16 = r7.get(r0)
            if (r16 == 0) goto L_0x0153
            r0 = r16
            boolean r7 = r0 instanceof com.alibaba.json.parser.deserializer.FieldDeserializer
            if (r7 == 0) goto L_0x0162
            r18 = r16
            com.alibaba.json.parser.deserializer.FieldDeserializer r18 = (com.alibaba.json.parser.deserializer.FieldDeserializer) r18
        L_0x0153:
            if (r18 != 0) goto L_0x019a
            r0 = r32
            r1 = r33
            r2 = r35
            r3 = r34
            r0.parseExtra(r1, r2, r3)
            r7 = 0
        L_0x0161:
            return r7
        L_0x0162:
            r11 = r16
            java.lang.reflect.Field r11 = (java.lang.reflect.Field) r11
            r7 = 1
            r11.setAccessible(r7)
            com.alibaba.json.util.FieldInfo r6 = new com.alibaba.json.util.FieldInfo
            java.lang.Class r8 = r11.getDeclaringClass()
            java.lang.Class r9 = r11.getType()
            java.lang.reflect.Type r10 = r11.getGenericType()
            r12 = 0
            r13 = 0
            r7 = r34
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)
            com.alibaba.json.parser.DefaultFieldDeserializer r18 = new com.alibaba.json.parser.DefaultFieldDeserializer
            r0 = r33
            com.alibaba.json.parser.ParserConfig r7 = r0.config
            r0 = r32
            java.lang.Class<?> r8 = r0.clazz
            r0 = r18
            r0.<init>(r7, r8, r6)
            r0 = r32
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> r7 = r0.extraFieldDeserializers
            r0 = r34
            r1 = r18
            r7.put(r0, r1)
            goto L_0x0153
        L_0x019a:
            r7 = 58
            r0 = r24
            r0.nextTokenWithChar(r7)
            r0 = r18
            r1 = r33
            r2 = r35
            r3 = r36
            r4 = r37
            r0.parseField(r1, r2, r3, r4)
            r7 = 1
            goto L_0x0161
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.JavaBeanDeserializer.parseField(com.alibaba.json.parser.DefaultJSONParser, java.lang.String, java.lang.Object, java.lang.reflect.Type, java.util.Map):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void parseExtra(DefaultJSONParser parser, Object object, String key) {
        Object value;
        JSONLexer lexer = parser.lexer;
        if ((parser.lexer.features & Feature.IgnoreNotMatch.mask) == 0) {
            throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + key);
        }
        lexer.nextTokenWithChar(':');
        Type type = null;
        List<ExtraTypeProvider> extraTypeProviders = parser.extraTypeProviders;
        if (extraTypeProviders != null) {
            for (ExtraTypeProvider extraProvider : extraTypeProviders) {
                type = extraProvider.getExtraType(object, key);
            }
        }
        if (type == null) {
            value = parser.parse();
        } else {
            value = parser.parseObject(type);
        }
        if (object instanceof ExtraProcessable) {
            ((ExtraProcessable) object).processExtra(key, value);
            return;
        }
        List<ExtraProcessor> extraProcessors = parser.extraProcessors;
        if (extraProcessors != null) {
            for (ExtraProcessor process : extraProcessors) {
                process.processExtra(object, key, value);
            }
        }
    }

    public Object createInstance(Map<String, Object> map, ParserConfig config) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.beanInfo.creatorConstructor == null) {
            Object object = createInstance((DefaultJSONParser) null, (Type) this.clazz);
            for (Entry<String, Object> entry : map.entrySet()) {
                FieldDeserializer fieldDeser = getFieldDeserializer((String) entry.getKey());
                if (fieldDeser != null) {
                    Object value = entry.getValue();
                    Method method = fieldDeser.fieldInfo.method;
                    if (method != null) {
                        method.invoke(object, new Object[]{TypeUtils.cast(value, method.getGenericParameterTypes()[0], config)});
                    } else {
                        fieldDeser.fieldInfo.field.set(object, TypeUtils.cast(value, fieldDeser.fieldInfo.fieldType, config));
                    }
                }
            }
            return object;
        }
        FieldInfo[] fieldInfoList = this.beanInfo.fields;
        int size = fieldInfoList.length;
        Object[] params = new Object[size];
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo = fieldInfoList[i];
            Object param = map.get(fieldInfo.name);
            if (param == null) {
                param = TypeUtils.defaultValue(fieldInfo.fieldClass);
            }
            params[i] = param;
        }
        if (this.beanInfo.creatorConstructor == null) {
            return null;
        }
        try {
            return this.beanInfo.creatorConstructor.newInstance(params);
        } catch (Exception e) {
            JSONException jSONException = new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e);
            throw jSONException;
        }
    }

    /* access modifiers changed from: protected */
    public JavaBeanDeserializer getSeeAlso(ParserConfig config, JavaBeanInfo beanInfo2, String typeName) {
        if (beanInfo2.jsonType == null) {
            return null;
        }
        for (Class<?> seeAlsoClass : beanInfo2.jsonType.seeAlso()) {
            ObjectDeserializer seeAlsoDeser = config.getDeserializer(seeAlsoClass);
            if (seeAlsoDeser instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer seeAlsoJavaBeanDeser = (JavaBeanDeserializer) seeAlsoDeser;
                JavaBeanInfo subBeanInfo = seeAlsoJavaBeanDeser.beanInfo;
                if (subBeanInfo.typeName.equals(typeName)) {
                    return seeAlsoJavaBeanDeser;
                }
                JavaBeanDeserializer subSeeAlso = getSeeAlso(config, subBeanInfo, typeName);
                if (subSeeAlso != null) {
                    return subSeeAlso;
                }
            }
        }
        return null;
    }
}
