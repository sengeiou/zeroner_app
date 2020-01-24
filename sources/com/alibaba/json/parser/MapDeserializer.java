package com.alibaba.json.parser;

import com.alibaba.json.JSONException;
import com.alibaba.json.JSONObject;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class MapDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    MapDeserializer() {
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == JSONObject.class && parser.fieldTypeResolver == null) {
            return parser.parseObject();
        }
        JSONLexer lexer = parser.lexer;
        if (lexer.token == 8) {
            lexer.nextToken(16);
            return null;
        }
        Map<?, ?> map = createMap(type);
        ParseContext context = parser.contex;
        try {
            parser.setContext(context, map, fieldName);
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type keyType = parameterizedType.getActualTypeArguments()[0];
                Type valueType = parameterizedType.getActualTypeArguments()[1];
                if (String.class == keyType) {
                    return parseMap(parser, map, valueType, fieldName);
                }
                T parseMap = parseMap(parser, map, keyType, valueType, fieldName);
                parser.setContext(context);
                return parseMap;
            }
            T parseObject = parser.parseObject((Map) map, fieldName);
            parser.setContext(context);
            return parseObject;
        } finally {
            parser.setContext(context);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r3 = r12.config.getDeserializer(r1);
        r5.nextToken(16);
        r12.resolveStatus = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x013d, code lost:
        if (r2 == null) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0141, code lost:
        if ((r15 instanceof java.lang.Integer) != false) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0143, code lost:
        r12.popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0146, code lost:
        r9 = (java.util.Map) r3.deserialze(r12, r1, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x014c, code lost:
        r12.setContext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        return r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map parseMap(com.alibaba.json.parser.DefaultJSONParser r12, java.util.Map<java.lang.String, java.lang.Object> r13, java.lang.reflect.Type r14, java.lang.Object r15) {
        /*
            com.alibaba.json.parser.JSONLexer r5 = r12.lexer
            int r9 = r5.token
            r10 = 12
            if (r9 == r10) goto L_0x0024
            com.alibaba.json.JSONException r9 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "syntax error, expect {, actual "
            java.lang.StringBuilder r10 = r10.append(r11)
            int r11 = r5.token
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x0024:
            com.alibaba.json.parser.ParseContext r2 = r12.contex
        L_0x0026:
            r5.skipWhitespace()     // Catch:{ all -> 0x006b }
            char r0 = r5.ch     // Catch:{ all -> 0x006b }
        L_0x002b:
            r9 = 44
            if (r0 != r9) goto L_0x0038
            r5.next()     // Catch:{ all -> 0x006b }
            r5.skipWhitespace()     // Catch:{ all -> 0x006b }
            char r0 = r5.ch     // Catch:{ all -> 0x006b }
            goto L_0x002b
        L_0x0038:
            r9 = 34
            if (r0 != r9) goto L_0x0070
            com.alibaba.json.parser.SymbolTable r9 = r12.symbolTable     // Catch:{ all -> 0x006b }
            r10 = 34
            java.lang.String r4 = r5.scanSymbol(r9, r10)     // Catch:{ all -> 0x006b }
            r5.skipWhitespace()     // Catch:{ all -> 0x006b }
            char r0 = r5.ch     // Catch:{ all -> 0x006b }
            r9 = 58
            if (r0 == r9) goto L_0x00ec
            com.alibaba.json.JSONException r9 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r10.<init>()     // Catch:{ all -> 0x006b }
            java.lang.String r11 = "syntax error, "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            java.lang.String r11 = r5.info()     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x006b }
            r9.<init>(r10)     // Catch:{ all -> 0x006b }
            throw r9     // Catch:{ all -> 0x006b }
        L_0x006b:
            r9 = move-exception
            r12.setContext(r2)
            throw r9
        L_0x0070:
            r9 = 125(0x7d, float:1.75E-43)
            if (r0 != r9) goto L_0x0083
            r5.next()     // Catch:{ all -> 0x006b }
            r9 = 0
            r5.sp = r9     // Catch:{ all -> 0x006b }
            r9 = 16
            r5.nextToken(r9)     // Catch:{ all -> 0x006b }
            r12.setContext(r2)
        L_0x0082:
            return r13
        L_0x0083:
            r9 = 39
            if (r0 != r9) goto L_0x00b6
            com.alibaba.json.parser.SymbolTable r9 = r12.symbolTable     // Catch:{ all -> 0x006b }
            r10 = 39
            java.lang.String r4 = r5.scanSymbol(r9, r10)     // Catch:{ all -> 0x006b }
            r5.skipWhitespace()     // Catch:{ all -> 0x006b }
            char r0 = r5.ch     // Catch:{ all -> 0x006b }
            r9 = 58
            if (r0 == r9) goto L_0x00ec
            com.alibaba.json.JSONException r9 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r10.<init>()     // Catch:{ all -> 0x006b }
            java.lang.String r11 = "syntax error, "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            java.lang.String r11 = r5.info()     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x006b }
            r9.<init>(r10)     // Catch:{ all -> 0x006b }
            throw r9     // Catch:{ all -> 0x006b }
        L_0x00b6:
            com.alibaba.json.parser.SymbolTable r9 = r12.symbolTable     // Catch:{ all -> 0x006b }
            java.lang.String r4 = r5.scanSymbolUnQuoted(r9)     // Catch:{ all -> 0x006b }
            r5.skipWhitespace()     // Catch:{ all -> 0x006b }
            char r0 = r5.ch     // Catch:{ all -> 0x006b }
            r9 = 58
            if (r0 == r9) goto L_0x00ec
            com.alibaba.json.JSONException r9 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r10.<init>()     // Catch:{ all -> 0x006b }
            java.lang.String r11 = "expect ':' at "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            int r11 = r5.pos     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            java.lang.String r11 = ", actual "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ all -> 0x006b }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x006b }
            r9.<init>(r10)     // Catch:{ all -> 0x006b }
            throw r9     // Catch:{ all -> 0x006b }
        L_0x00ec:
            r5.next()     // Catch:{ all -> 0x006b }
            r5.skipWhitespace()     // Catch:{ all -> 0x006b }
            char r0 = r5.ch     // Catch:{ all -> 0x006b }
            r9 = 0
            r5.sp = r9     // Catch:{ all -> 0x006b }
            java.lang.String r9 = "@type"
            if (r4 != r9) goto L_0x0152
            com.alibaba.json.parser.Feature r9 = com.alibaba.json.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x006b }
            boolean r9 = r5.isEnabled(r9)     // Catch:{ all -> 0x006b }
            if (r9 != 0) goto L_0x0152
            com.alibaba.json.parser.SymbolTable r9 = r12.symbolTable     // Catch:{ all -> 0x006b }
            r10 = 34
            java.lang.String r7 = r5.scanSymbol(r9, r10)     // Catch:{ all -> 0x006b }
            com.alibaba.json.parser.ParserConfig r9 = r12.config     // Catch:{ all -> 0x006b }
            java.lang.ClassLoader r9 = r9.defaultClassLoader     // Catch:{ all -> 0x006b }
            java.lang.Class r1 = com.alibaba.json.util.TypeUtils.loadClass(r7, r9)     // Catch:{ all -> 0x006b }
            java.lang.Class r9 = r13.getClass()     // Catch:{ all -> 0x006b }
            if (r1 != r9) goto L_0x012f
            r9 = 16
            r5.nextToken(r9)     // Catch:{ all -> 0x006b }
            int r9 = r5.token     // Catch:{ all -> 0x006b }
            r10 = 13
            if (r9 != r10) goto L_0x0026
            r9 = 16
            r5.nextToken(r9)     // Catch:{ all -> 0x006b }
            r12.setContext(r2)
            goto L_0x0082
        L_0x012f:
            com.alibaba.json.parser.ParserConfig r9 = r12.config     // Catch:{ all -> 0x006b }
            com.alibaba.json.parser.deserializer.ObjectDeserializer r3 = r9.getDeserializer(r1)     // Catch:{ all -> 0x006b }
            r9 = 16
            r5.nextToken(r9)     // Catch:{ all -> 0x006b }
            r9 = 2
            r12.resolveStatus = r9     // Catch:{ all -> 0x006b }
            if (r2 == 0) goto L_0x0146
            boolean r9 = r15 instanceof java.lang.Integer     // Catch:{ all -> 0x006b }
            if (r9 != 0) goto L_0x0146
            r12.popContext()     // Catch:{ all -> 0x006b }
        L_0x0146:
            java.lang.Object r9 = r3.deserialze(r12, r1, r15)     // Catch:{ all -> 0x006b }
            java.util.Map r9 = (java.util.Map) r9     // Catch:{ all -> 0x006b }
            r12.setContext(r2)
            r13 = r9
            goto L_0x0082
        L_0x0152:
            r5.nextToken()     // Catch:{ all -> 0x006b }
            r12.setContext(r2)     // Catch:{ all -> 0x006b }
            int r9 = r5.token     // Catch:{ all -> 0x006b }
            r10 = 8
            if (r9 != r10) goto L_0x017f
            r8 = 0
            r5.nextToken()     // Catch:{ all -> 0x006b }
        L_0x0162:
            r13.put(r4, r8)     // Catch:{ all -> 0x006b }
            int r9 = r12.resolveStatus     // Catch:{ all -> 0x006b }
            r10 = 1
            if (r9 != r10) goto L_0x016d
            r12.checkMapResolve(r13, r4)     // Catch:{ all -> 0x006b }
        L_0x016d:
            r12.setContext(r2, r8, r4)     // Catch:{ all -> 0x006b }
            int r6 = r5.token     // Catch:{ all -> 0x006b }
            r9 = 20
            if (r6 == r9) goto L_0x017a
            r9 = 15
            if (r6 != r9) goto L_0x0184
        L_0x017a:
            r12.setContext(r2)
            goto L_0x0082
        L_0x017f:
            java.lang.Object r8 = r12.parseObject(r14, r4)     // Catch:{ all -> 0x006b }
            goto L_0x0162
        L_0x0184:
            r9 = 13
            if (r6 != r9) goto L_0x0026
            r5.nextToken()     // Catch:{ all -> 0x006b }
            r12.setContext(r2)
            goto L_0x0082
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.MapDeserializer.parseMap(com.alibaba.json.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.Object):java.util.Map");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006d, code lost:
        r5 = null;
        r4.nextTokenWithChar(':');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0076, code lost:
        if (r4.token != 4) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0078, code lost:
        r7 = r4.stringVal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0083, code lost:
        if ("..".equals(r7) == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0085, code lost:
        r5 = r1.parent.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0089, code lost:
        r4.nextToken(13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0092, code lost:
        if (r4.token == 13) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009c, code lost:
        throw new com.alibaba.json.JSONException("illegal ref");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a9, code lost:
        if ("$".equals(r7) == false) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ab, code lost:
        r8 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ae, code lost:
        if (r8.parent == null) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b0, code lost:
        r8 = r8.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b3, code lost:
        r5 = r8.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b6, code lost:
        r15.addResolveTask(new com.alibaba.json.parser.DefaultJSONParser.ResolveTask(r1, r7));
        r15.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00df, code lost:
        throw new com.alibaba.json.JSONException("illegal ref, " + com.alibaba.json.parser.JSONToken.name(r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e0, code lost:
        r4.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e5, code lost:
        r15.setContext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object parseMap(com.alibaba.json.parser.DefaultJSONParser r15, java.util.Map<java.lang.Object, java.lang.Object> r16, java.lang.reflect.Type r17, java.lang.reflect.Type r18, java.lang.Object r19) {
        /*
            com.alibaba.json.parser.JSONLexer r4 = r15.lexer
            int r9 = r4.token
            r12 = 12
            if (r9 == r12) goto L_0x002a
            r12 = 16
            if (r9 == r12) goto L_0x002a
            com.alibaba.json.JSONException r12 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "syntax error, expect {, actual "
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r14 = com.alibaba.json.parser.JSONToken.name(r9)
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x002a:
            com.alibaba.json.parser.ParserConfig r12 = r15.config
            r0 = r17
            com.alibaba.json.parser.deserializer.ObjectDeserializer r3 = r12.getDeserializer(r0)
            com.alibaba.json.parser.ParserConfig r12 = r15.config
            r0 = r18
            com.alibaba.json.parser.deserializer.ObjectDeserializer r11 = r12.getDeserializer(r0)
            r4.nextToken()
            com.alibaba.json.parser.ParseContext r1 = r15.contex
        L_0x003f:
            int r9 = r4.token     // Catch:{ all -> 0x009d }
            r12 = 13
            if (r9 != r12) goto L_0x004e
            r12 = 16
            r4.nextToken(r12)     // Catch:{ all -> 0x009d }
            r15.setContext(r1)
        L_0x004d:
            return r16
        L_0x004e:
            r12 = 4
            if (r9 != r12) goto L_0x00ec
            int r12 = r4.sp     // Catch:{ all -> 0x009d }
            r13 = 4
            if (r12 != r13) goto L_0x00ec
            java.lang.String r12 = r4.text     // Catch:{ all -> 0x009d }
            java.lang.String r13 = "$ref"
            int r14 = r4.np     // Catch:{ all -> 0x009d }
            int r14 = r14 + 1
            boolean r12 = r12.startsWith(r13, r14)     // Catch:{ all -> 0x009d }
            if (r12 == 0) goto L_0x00ec
            com.alibaba.json.parser.Feature r12 = com.alibaba.json.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x009d }
            boolean r12 = r4.isEnabled(r12)     // Catch:{ all -> 0x009d }
            if (r12 != 0) goto L_0x00ec
            r5 = 0
            r12 = 58
            r4.nextTokenWithChar(r12)     // Catch:{ all -> 0x009d }
            int r12 = r4.token     // Catch:{ all -> 0x009d }
            r13 = 4
            if (r12 != r13) goto L_0x00c2
            java.lang.String r7 = r4.stringVal()     // Catch:{ all -> 0x009d }
            java.lang.String r12 = ".."
            boolean r12 = r12.equals(r7)     // Catch:{ all -> 0x009d }
            if (r12 == 0) goto L_0x00a2
            com.alibaba.json.parser.ParseContext r6 = r1.parent     // Catch:{ all -> 0x009d }
            java.lang.Object r5 = r6.object     // Catch:{ all -> 0x009d }
        L_0x0089:
            r12 = 13
            r4.nextToken(r12)     // Catch:{ all -> 0x009d }
            int r12 = r4.token     // Catch:{ all -> 0x009d }
            r13 = 13
            if (r12 == r13) goto L_0x00e0
            com.alibaba.json.JSONException r12 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x009d }
            java.lang.String r13 = "illegal ref"
            r12.<init>(r13)     // Catch:{ all -> 0x009d }
            throw r12     // Catch:{ all -> 0x009d }
        L_0x009d:
            r12 = move-exception
            r15.setContext(r1)
            throw r12
        L_0x00a2:
            java.lang.String r12 = "$"
            boolean r12 = r12.equals(r7)     // Catch:{ all -> 0x009d }
            if (r12 == 0) goto L_0x00b6
            r8 = r1
        L_0x00ac:
            com.alibaba.json.parser.ParseContext r12 = r8.parent     // Catch:{ all -> 0x009d }
            if (r12 == 0) goto L_0x00b3
            com.alibaba.json.parser.ParseContext r8 = r8.parent     // Catch:{ all -> 0x009d }
            goto L_0x00ac
        L_0x00b3:
            java.lang.Object r5 = r8.object     // Catch:{ all -> 0x009d }
            goto L_0x0089
        L_0x00b6:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r12 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x009d }
            r12.<init>(r1, r7)     // Catch:{ all -> 0x009d }
            r15.addResolveTask(r12)     // Catch:{ all -> 0x009d }
            r12 = 1
            r15.resolveStatus = r12     // Catch:{ all -> 0x009d }
            goto L_0x0089
        L_0x00c2:
            com.alibaba.json.JSONException r12 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x009d }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x009d }
            r13.<init>()     // Catch:{ all -> 0x009d }
            java.lang.String r14 = "illegal ref, "
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x009d }
            java.lang.String r14 = com.alibaba.json.parser.JSONToken.name(r9)     // Catch:{ all -> 0x009d }
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x009d }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x009d }
            r12.<init>(r13)     // Catch:{ all -> 0x009d }
            throw r12     // Catch:{ all -> 0x009d }
        L_0x00e0:
            r12 = 16
            r4.nextToken(r12)     // Catch:{ all -> 0x009d }
            r15.setContext(r1)
            r16 = r5
            goto L_0x004d
        L_0x00ec:
            int r12 = r16.size()     // Catch:{ all -> 0x009d }
            if (r12 != 0) goto L_0x0125
            r12 = 4
            if (r9 != r12) goto L_0x0125
            java.lang.String r12 = "@type"
            java.lang.String r13 = r4.stringVal()     // Catch:{ all -> 0x009d }
            boolean r12 = r12.equals(r13)     // Catch:{ all -> 0x009d }
            if (r12 == 0) goto L_0x0125
            com.alibaba.json.parser.Feature r12 = com.alibaba.json.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x009d }
            boolean r12 = r4.isEnabled(r12)     // Catch:{ all -> 0x009d }
            if (r12 != 0) goto L_0x0125
            r12 = 58
            r4.nextTokenWithChar(r12)     // Catch:{ all -> 0x009d }
            r12 = 16
            r4.nextToken(r12)     // Catch:{ all -> 0x009d }
            int r12 = r4.token     // Catch:{ all -> 0x009d }
            r13 = 13
            if (r12 != r13) goto L_0x0122
            r4.nextToken()     // Catch:{ all -> 0x009d }
            r15.setContext(r1)
            goto L_0x004d
        L_0x0122:
            r4.nextToken()     // Catch:{ all -> 0x009d }
        L_0x0125:
            r12 = 0
            r0 = r17
            java.lang.Object r2 = r3.deserialze(r15, r0, r12)     // Catch:{ all -> 0x009d }
            int r12 = r4.token     // Catch:{ all -> 0x009d }
            r13 = 17
            if (r12 == r13) goto L_0x014e
            com.alibaba.json.JSONException r12 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x009d }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x009d }
            r13.<init>()     // Catch:{ all -> 0x009d }
            java.lang.String r14 = "syntax error, expect :, actual "
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x009d }
            int r14 = r4.token     // Catch:{ all -> 0x009d }
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x009d }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x009d }
            r12.<init>(r13)     // Catch:{ all -> 0x009d }
            throw r12     // Catch:{ all -> 0x009d }
        L_0x014e:
            r4.nextToken()     // Catch:{ all -> 0x009d }
            r0 = r18
            java.lang.Object r10 = r11.deserialze(r15, r0, r2)     // Catch:{ all -> 0x009d }
            int r12 = r15.resolveStatus     // Catch:{ all -> 0x009d }
            r13 = 1
            if (r12 != r13) goto L_0x0161
            r0 = r16
            r15.checkMapResolve(r0, r2)     // Catch:{ all -> 0x009d }
        L_0x0161:
            r0 = r16
            r0.put(r2, r10)     // Catch:{ all -> 0x009d }
            int r12 = r4.token     // Catch:{ all -> 0x009d }
            r13 = 16
            if (r12 != r13) goto L_0x003f
            r4.nextToken()     // Catch:{ all -> 0x009d }
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.MapDeserializer.parseMap(com.alibaba.json.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public Map<?, ?> createMap(Type type) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class || type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type == JSONObject.class) {
            return new JSONObject();
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (EnumMap.class.equals(rawType)) {
                return new EnumMap((Class) parameterizedType.getActualTypeArguments()[0]);
            }
            return createMap(rawType);
        }
        Class<?> clazz = (Class) type;
        if (clazz.isInterface()) {
            throw new JSONException("unsupport type " + type);
        }
        try {
            return (Map) clazz.newInstance();
        } catch (Exception e) {
            throw new JSONException("unsupport type " + type, e);
        }
    }
}
