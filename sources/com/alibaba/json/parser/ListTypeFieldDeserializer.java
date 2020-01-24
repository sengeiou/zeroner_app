package com.alibaba.json.parser;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.deserializer.FieldDeserializer;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import com.alibaba.json.util.FieldInfo;
import com.alibaba.json.util.ParameterizedTypeImpl;
import com.alibaba.json.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

class ListTypeFieldDeserializer extends FieldDeserializer {
    private final boolean array;
    private ObjectDeserializer deserializer;
    private final Type itemType;

    public ListTypeFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo, 14);
        Type fieldType = fieldInfo.fieldType;
        Class<?> fieldClass = fieldInfo.fieldClass;
        if (fieldClass.isArray()) {
            this.itemType = fieldClass.getComponentType();
            this.array = true;
            return;
        }
        this.itemType = TypeUtils.getCollectionItemType(fieldType);
        this.array = false;
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r5v0, types: [java.util.ArrayList] */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.util.List, java.util.Collection] */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.lang.Object[], java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseField(com.alibaba.json.parser.DefaultJSONParser r10, java.lang.Object r11, java.lang.reflect.Type r12, java.util.Map<java.lang.String, java.lang.Object> r13) {
        /*
            r9 = this;
            com.alibaba.json.parser.JSONLexer r4 = r10.lexer
            int r6 = r4.token()
            r7 = 8
            if (r6 == r7) goto L_0x0017
            r7 = 4
            if (r6 != r7) goto L_0x0021
            java.lang.String r7 = r4.stringVal()
            int r7 = r7.length()
            if (r7 != 0) goto L_0x0021
        L_0x0017:
            r7 = 0
            r9.setValue(r11, r7)
            com.alibaba.json.parser.JSONLexer r7 = r10.lexer
            r7.nextToken()
        L_0x0020:
            return
        L_0x0021:
            r3 = 0
            boolean r7 = r9.array
            if (r7 == 0) goto L_0x0066
            com.alibaba.json.JSONArray r3 = new com.alibaba.json.JSONArray
            r3.<init>()
            r5 = r3
            java.lang.reflect.Type r7 = r9.itemType
            r3.setComponentType(r7)
        L_0x0031:
            com.alibaba.json.parser.ParseContext r1 = r10.contex
            com.alibaba.json.util.FieldInfo r7 = r9.fieldInfo
            java.lang.String r7 = r7.name
            r10.setContext(r1, r11, r7)
            r9.parseArray(r10, r12, r5)
            r10.setContext(r1)
            boolean r7 = r9.array
            if (r7 == 0) goto L_0x006c
            java.lang.reflect.Type r7 = r9.itemType
            java.lang.Class r7 = (java.lang.Class) r7
            int r8 = r5.size()
            java.lang.Object r7 = java.lang.reflect.Array.newInstance(r7, r8)
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            r0 = r7
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            java.lang.Object[] r2 = r5.toArray(r0)
            r3.setRelatedArray(r2)
        L_0x005c:
            if (r11 != 0) goto L_0x006e
            com.alibaba.json.util.FieldInfo r7 = r9.fieldInfo
            java.lang.String r7 = r7.name
            r13.put(r7, r2)
            goto L_0x0020
        L_0x0066:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            goto L_0x0031
        L_0x006c:
            r2 = r5
            goto L_0x005c
        L_0x006e:
            r9.setValue(r11, r2)
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.ListTypeFieldDeserializer.parseField(com.alibaba.json.parser.DefaultJSONParser, java.lang.Object, java.lang.reflect.Type, java.util.Map):void");
    }

    /* access modifiers changed from: 0000 */
    public final void parseArray(DefaultJSONParser parser, Type objectType, Collection array2) {
        char charAt;
        char charAt2;
        char charAt3;
        char charAt4;
        char charAt5;
        char charAt6;
        Type itemType2 = this.itemType;
        ObjectDeserializer itemTypeDeser = this.deserializer;
        if (objectType instanceof ParameterizedType) {
            if (itemType2 instanceof TypeVariable) {
                TypeVariable typeVar = (TypeVariable) itemType2;
                ParameterizedType paramType = (ParameterizedType) objectType;
                Class<?> objectClass = null;
                if (paramType.getRawType() instanceof Class) {
                    objectClass = (Class) paramType.getRawType();
                }
                int paramIndex = -1;
                if (objectClass != null) {
                    int i = 0;
                    int size = objectClass.getTypeParameters().length;
                    while (true) {
                        if (i >= size) {
                            break;
                        } else if (objectClass.getTypeParameters()[i].getName().equals(typeVar.getName())) {
                            paramIndex = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (paramIndex != -1) {
                    itemType2 = paramType.getActualTypeArguments()[paramIndex];
                    if (!itemType2.equals(this.itemType)) {
                        itemTypeDeser = parser.config.getDeserializer(itemType2);
                    }
                }
            } else if (itemType2 instanceof ParameterizedType) {
                ParameterizedType parameterizedItemType = (ParameterizedType) itemType2;
                Type[] itemActualTypeArgs = parameterizedItemType.getActualTypeArguments();
                if (itemActualTypeArgs.length == 1 && (itemActualTypeArgs[0] instanceof TypeVariable)) {
                    TypeVariable typeVar2 = (TypeVariable) itemActualTypeArgs[0];
                    ParameterizedType paramType2 = (ParameterizedType) objectType;
                    Class<?> objectClass2 = null;
                    if (paramType2.getRawType() instanceof Class) {
                        objectClass2 = (Class) paramType2.getRawType();
                    }
                    int paramIndex2 = -1;
                    if (objectClass2 != null) {
                        int i2 = 0;
                        int size2 = objectClass2.getTypeParameters().length;
                        while (true) {
                            if (i2 >= size2) {
                                break;
                            } else if (objectClass2.getTypeParameters()[i2].getName().equals(typeVar2.getName())) {
                                paramIndex2 = i2;
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                    if (paramIndex2 != -1) {
                        itemActualTypeArgs[0] = paramType2.getActualTypeArguments()[paramIndex2];
                        itemType2 = new ParameterizedTypeImpl(itemActualTypeArgs, parameterizedItemType.getOwnerType(), parameterizedItemType.getRawType());
                    }
                }
            }
        }
        JSONLexer lexer = parser.lexer;
        if (itemTypeDeser == null) {
            itemTypeDeser = parser.config.getDeserializer(itemType2);
            this.deserializer = itemTypeDeser;
        }
        if (lexer.token == 14) {
            int ch = lexer.ch;
            if (ch == 91) {
                int index = lexer.bp + 1;
                lexer.bp = index;
                if (index >= lexer.len) {
                    charAt6 = 26;
                } else {
                    charAt6 = lexer.text.charAt(index);
                }
                lexer.ch = charAt6;
                lexer.token = 14;
            } else if (ch == 123) {
                int index2 = lexer.bp + 1;
                lexer.bp = index2;
                if (index2 >= lexer.len) {
                    charAt2 = 26;
                } else {
                    charAt2 = lexer.text.charAt(index2);
                }
                lexer.ch = charAt2;
                lexer.token = 12;
            } else if (ch == 34) {
                lexer.scanString();
            } else if (ch == 93) {
                int index3 = lexer.bp + 1;
                lexer.bp = index3;
                if (index3 >= lexer.len) {
                    charAt = 26;
                } else {
                    charAt = lexer.text.charAt(index3);
                }
                lexer.ch = charAt;
                lexer.token = 15;
            } else {
                lexer.nextToken();
            }
            int i3 = 0;
            while (true) {
                if (lexer.token == 16) {
                    lexer.nextToken();
                } else if (lexer.token == 15) {
                    break;
                } else {
                    array2.add(itemTypeDeser.deserialze(parser, itemType2, Integer.valueOf(i3)));
                    if (parser.resolveStatus == 1) {
                        parser.checkListResolve(array2);
                    }
                    if (lexer.token == 16) {
                        int ch2 = lexer.ch;
                        if (ch2 == 91) {
                            int index4 = lexer.bp + 1;
                            lexer.bp = index4;
                            if (index4 >= lexer.len) {
                                charAt5 = 26;
                            } else {
                                charAt5 = lexer.text.charAt(index4);
                            }
                            lexer.ch = charAt5;
                            lexer.token = 14;
                        } else if (ch2 == 123) {
                            int index5 = lexer.bp + 1;
                            lexer.bp = index5;
                            if (index5 >= lexer.len) {
                                charAt4 = 26;
                            } else {
                                charAt4 = lexer.text.charAt(index5);
                            }
                            lexer.ch = charAt4;
                            lexer.token = 12;
                        } else if (ch2 == 34) {
                            lexer.scanString();
                        } else {
                            lexer.nextToken();
                        }
                    }
                    i3++;
                }
            }
            if (lexer.ch == ',') {
                int index6 = lexer.bp + 1;
                lexer.bp = index6;
                if (index6 >= lexer.len) {
                    charAt3 = 26;
                } else {
                    charAt3 = lexer.text.charAt(index6);
                }
                lexer.ch = charAt3;
                lexer.token = 16;
                return;
            }
            lexer.nextToken();
        } else if (lexer.token == 12) {
            array2.add(itemTypeDeser.deserialze(parser, itemType2, Integer.valueOf(0)));
        } else {
            String errorMessage = "exepct '[', but " + JSONToken.name(lexer.token);
            if (objectType != null) {
                errorMessage = errorMessage + ", type : " + objectType;
            }
            JSONException jSONException = new JSONException(errorMessage);
            throw jSONException;
        }
    }
}
