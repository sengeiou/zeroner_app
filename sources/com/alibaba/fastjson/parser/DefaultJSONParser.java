package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    public ParserConfig config;
    protected ParseContext contex;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    protected List<ExtraProcessor> extraProcessors;
    protected List<ExtraTypeProvider> extraTypeProviders;
    public FieldTypeResolver fieldTypeResolver;
    public final JSONLexer lexer;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    public static class ResolveTask {
        /* access modifiers changed from: private */
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        /* access modifiers changed from: private */
        public final String referenceValue;

        public ResolveTask(ParseContext context2, String referenceValue2) {
            this.context = context2;
            this.referenceValue = referenceValue2;
        }
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.locale);
            this.dateFormat.setTimeZone(this.lexer.timeZone);
        }
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat2) {
        this.dateFormatPattern = dateFormat2;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat2) {
        this.dateFormat = dateFormat2;
    }

    public DefaultJSONParser(String input) {
        this(input, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String input, ParserConfig config2) {
        this(new JSONLexer(input, JSON.DEFAULT_PARSER_FEATURE), config2);
    }

    public DefaultJSONParser(String input, ParserConfig config2, int features) {
        this(new JSONLexer(input, features), config2);
    }

    public DefaultJSONParser(char[] input, int length, ParserConfig config2, int features) {
        this(new JSONLexer(input, length, features), config2);
    }

    public DefaultJSONParser(JSONLexer lexer2) {
        this(lexer2, ParserConfig.global);
    }

    public DefaultJSONParser(JSONLexer lexer2, ParserConfig config2) {
        char c = 26;
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.lexer = lexer2;
        this.config = config2;
        this.symbolTable = config2.symbolTable;
        if (lexer2.ch == '{') {
            int index = lexer2.bp + 1;
            lexer2.bp = index;
            if (index < lexer2.len) {
                c = lexer2.text.charAt(index);
            }
            lexer2.ch = c;
            lexer2.token = 12;
        } else if (lexer2.ch == '[') {
            int index2 = lexer2.bp + 1;
            lexer2.bp = index2;
            if (index2 < lexer2.len) {
                c = lexer2.text.charAt(index2);
            }
            lexer2.ch = c;
            lexer2.token = 14;
        } else {
            lexer2.nextToken();
        }
    }

    /* JADX WARNING: type inference failed for: r45v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r50v3 */
    /* JADX WARNING: type inference failed for: r50v4 */
    /* JADX WARNING: type inference failed for: r2v16, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v17, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v75, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r50v5 */
    /* JADX WARNING: type inference failed for: r50v6, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r50v7 */
    /* JADX WARNING: type inference failed for: r50v8 */
    /* JADX INFO: used method not loaded: com.alibaba.fastjson.parser.JavaBeanDeserializer.createInstance(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.alibaba.fastjson.parser.deserializer.FieldDeserializer.setValue(java.lang.Object, java.lang.Object):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x03e1, code lost:
        r33.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x03f6, code lost:
        if (r33.token != 13) goto L_0x049c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x03f8, code lost:
        r33.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0401, code lost:
        r26 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:?, code lost:
        r12 = r55.config.getDeserializer(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0413, code lost:
        if ((r12 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer) == false) goto L_0x046e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0415, code lost:
        r30 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r12;
        r26 = r30.createInstance(r55, (java.lang.reflect.Type) r8);
        r51 = r56.entrySet().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x042e, code lost:
        if (r51.hasNext() == false) goto L_0x046e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0430, code lost:
        r16 = (java.util.Map.Entry) r51.next();
        r17 = r16.getKey();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0444, code lost:
        if ((r17 instanceof java.lang.String) == false) goto L_0x042a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0446, code lost:
        r20 = r30.getFieldDeserializer((java.lang.String) r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0450, code lost:
        if (r20 == null) goto L_0x042a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0452, code lost:
        r20.setValue(r26, r16.getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x046e, code lost:
        if (r26 != null) goto L_0x047b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0474, code lost:
        if (r8 != java.lang.Cloneable.class) goto L_0x0485;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:?, code lost:
        r26 = new java.util.HashMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x047b, code lost:
        if (r14 != false) goto L_0x0481;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x047d, code lost:
        r55.contex = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0490, code lost:
        if ("java.util.Collections$EmptyMap".equals(r49) == false) goto L_0x0497;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0492, code lost:
        r26 = java.util.Collections.emptyMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0497, code lost:
        r26 = r8.newInstance();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:?, code lost:
        r55.resolveStatus = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x04aa, code lost:
        if (r55.contex == null) goto L_0x04b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x04b2, code lost:
        if ((r57 instanceof java.lang.Integer) != false) goto L_0x04b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x04b4, code lost:
        popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x04bb, code lost:
        if (r56.size() <= 0) goto L_0x04dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x04bd, code lost:
        r35 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r56, r8, r55.config);
        parseObject(r35);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x04d2, code lost:
        if (r14 != false) goto L_0x04d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x04d4, code lost:
        r55.contex = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:?, code lost:
        r12 = r55.config.getDeserializer(r8);
        r13 = r12.deserialze(r55, r8, r57);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x04f4, code lost:
        if ((r12 instanceof com.alibaba.fastjson.parser.MapDeserializer) == false) goto L_0x04fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x04f6, code lost:
        r55.resolveStatus = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x04fe, code lost:
        if (r14 != false) goto L_0x0504;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0500, code lost:
        r55.contex = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x051f, code lost:
        r33.nextToken(4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x0534, code lost:
        if (r33.token != 4) goto L_0x0660;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x0536, code lost:
        r40 = r33.stringVal();
        r33.nextToken(13);
        r41 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x0550, code lost:
        if ("@".equals(r40) == false) goto L_0x05b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x0552, code lost:
        r46 = r55.contex;
        r47 = r46.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:213:0x0564, code lost:
        if ((r47 instanceof java.lang.Object[]) != false) goto L_0x056e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x056c, code lost:
        if ((r47 instanceof java.util.Collection) == false) goto L_0x059e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x056e, code lost:
        r41 = r47;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x0570, code lost:
        r56 = r41;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x057e, code lost:
        if (r33.token == 13) goto L_0x064f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x059d, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, " + r33.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x05a4, code lost:
        if (r46.parent == null) goto L_0x0570;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x05a6, code lost:
        r41 = r46.parent.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x05be, code lost:
        if ("..".equals(r40) == false) goto L_0x05e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x05c4, code lost:
        if (r9.object == null) goto L_0x05cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x05c6, code lost:
        r56 = r9.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x05cd, code lost:
        r0 = new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r9, r40);
        addResolveTask(r0);
        r55.resolveStatus = 1;
        r56 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x05f3, code lost:
        if ("$".equals(r40) == false) goto L_0x0633;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x05f5, code lost:
        r43 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x05fd, code lost:
        if (r43.parent == null) goto L_0x0606;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:0x05ff, code lost:
        r43 = r43.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x060c, code lost:
        if (r43.object == null) goto L_0x0618;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x060e, code lost:
        r41 = r43.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x0614, code lost:
        r56 = r41;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x0618, code lost:
        r0 = new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r43, r40);
        addResolveTask(r0);
        r55.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x0633, code lost:
        r0 = new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r9, r40);
        addResolveTask(r0);
        r55.resolveStatus = 1;
        r56 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x064f, code lost:
        r33.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x0658, code lost:
        if (r14 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x065a, code lost:
        r55.contex = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x0683, code lost:
        throw new com.alibaba.fastjson.JSONException("illegal ref, " + com.alibaba.fastjson.parser.JSONToken.name(r33.token));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:388:0x0a64, code lost:
        if (r7 != '}') goto L_0x0b7d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:389:0x0a66, code lost:
        r23 = r33.bp + 1;
        r33.bp = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:390:0x0a7e, code lost:
        if (r23 < r33.len) goto L_0x0ad9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:391:0x0a80, code lost:
        r7 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:392:0x0a82, code lost:
        r33.ch = r7;
        r33.sp = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:393:0x0a92, code lost:
        if (r7 != ',') goto L_0x0af7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:394:0x0a94, code lost:
        r23 = r33.bp + 1;
        r33.bp = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:395:0x0aac, code lost:
        if (r23 < r33.len) goto L_0x0ae8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:396:0x0aae, code lost:
        r51 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:397:0x0ab0, code lost:
        r33.ch = r51;
        r33.token = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:398:0x0abe, code lost:
        if (r14 != false) goto L_0x0ad1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:399:0x0ac0, code lost:
        setContext(r55.contex, r56, r57);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:400:0x0ad1, code lost:
        if (r14 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:401:0x0ad3, code lost:
        r55.contex = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:404:?, code lost:
        r7 = r33.text.charAt(r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:405:0x0ae8, code lost:
        r51 = r33.text.charAt(r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:407:0x0afb, code lost:
        if (r7 != '}') goto L_0x0b37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:408:0x0afd, code lost:
        r23 = r33.bp + 1;
        r33.bp = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:409:0x0b15, code lost:
        if (r23 < r33.len) goto L_0x0b28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:410:0x0b17, code lost:
        r51 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:411:0x0b19, code lost:
        r33.ch = r51;
        r33.token = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:412:0x0b28, code lost:
        r51 = r33.text.charAt(r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:414:0x0b3b, code lost:
        if (r7 != ']') goto L_0x0b78;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:415:0x0b3d, code lost:
        r23 = r33.bp + 1;
        r33.bp = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:416:0x0b55, code lost:
        if (r23 < r33.len) goto L_0x0b69;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:417:0x0b57, code lost:
        r51 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:418:0x0b59, code lost:
        r33.ch = r51;
        r33.token = 15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:419:0x0b69, code lost:
        r51 = r33.text.charAt(r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:420:0x0b78, code lost:
        r33.nextToken();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:422:0x0b9a, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, " + r33.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r23 = r33.bp + 1;
        r33.bp = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x013b, code lost:
        if (r23 < r33.len) goto L_0x015e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:467:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:468:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:469:?, code lost:
        return r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x013d, code lost:
        r51 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:470:?, code lost:
        return r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:471:?, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:472:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:473:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x013f, code lost:
        r33.ch = r51;
        r33.sp = 0;
        r33.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:480:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:481:?, code lost:
        return r56;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0156, code lost:
        if (r14 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0158, code lost:
        r55.contex = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r51 = r33.text.charAt(r23);
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r50v5
      assigns: []
      uses: []
      mth insns count: 1127
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
    public final java.lang.Object parseObject(java.util.Map r56, java.lang.Object r57) {
        /*
            r55 = this;
            r0 = r55
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer
            r33 = r0
            r0 = r33
            int r0 = r0.token
            r48 = r0
            r51 = 8
            r0 = r48
            r1 = r51
            if (r0 != r1) goto L_0x001a
            r33.nextToken()
            r56 = 0
        L_0x0019:
            return r56
        L_0x001a:
            r51 = 12
            r0 = r48
            r1 = r51
            if (r0 == r1) goto L_0x0057
            r51 = 16
            r0 = r48
            r1 = r51
            if (r0 == r1) goto L_0x0057
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r52 = new java.lang.StringBuilder
            r52.<init>()
            java.lang.String r53 = "syntax error, expect {, actual "
            java.lang.StringBuilder r52 = r52.append(r53)
            java.lang.String r53 = com.alibaba.fastjson.parser.JSONToken.name(r48)
            java.lang.StringBuilder r52 = r52.append(r53)
            java.lang.String r53 = ", "
            java.lang.StringBuilder r52 = r52.append(r53)
            java.lang.String r53 = r33.info()
            java.lang.StringBuilder r52 = r52.append(r53)
            java.lang.String r52 = r52.toString()
            r51.<init>(r52)
            throw r51
        L_0x0057:
            r0 = r56
            boolean r0 = r0 instanceof com.alibaba.fastjson.JSONObject
            r51 = r0
            if (r51 == 0) goto L_0x00ae
            r31 = r56
            com.alibaba.fastjson.JSONObject r31 = (com.alibaba.fastjson.JSONObject) r31
            java.util.Map r24 = r31.getInnerMap()
            r27 = 1
        L_0x0069:
            r0 = r33
            int r0 = r0.features
            r51 = r0
            com.alibaba.fastjson.parser.Feature r52 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat
            r0 = r52
            int r0 = r0.mask
            r52 = r0
            r51 = r51 & r52
            if (r51 == 0) goto L_0x00b3
            r5 = 1
        L_0x007c:
            r0 = r33
            boolean r14 = r0.disableCircularReferenceDetect
            r0 = r55
            com.alibaba.fastjson.parser.ParseContext r9 = r0.contex
            r44 = 0
        L_0x0086:
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 34
            r0 = r51
            if (r7 == r0) goto L_0x009d
            r51 = 125(0x7d, float:1.75E-43)
            r0 = r51
            if (r7 == r0) goto L_0x009d
            r33.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
        L_0x009d:
            r51 = 44
            r0 = r51
            if (r7 != r0) goto L_0x00b5
            r33.next()     // Catch:{ all -> 0x0115 }
            r33.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            goto L_0x009d
        L_0x00ae:
            r27 = 0
            r24 = r56
            goto L_0x0069
        L_0x00b3:
            r5 = 0
            goto L_0x007c
        L_0x00b5:
            r28 = 0
            r51 = 34
            r0 = r51
            if (r7 != r0) goto L_0x011d
            r0 = r55
            com.alibaba.fastjson.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 34
            r0 = r33
            r1 = r51
            r2 = r52
            java.lang.String r32 = r0.scanSymbol(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 58
            r0 = r51
            if (r7 == r0) goto L_0x0b9b
            r33.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 58
            r0 = r51
            if (r7 == r0) goto L_0x0b9b
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "expect ':' at "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.pos     // Catch:{ all -> 0x0115 }
            r53 = r0
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = ", name "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            r0 = r52
            r1 = r32
            java.lang.StringBuilder r52 = r0.append(r1)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0115:
            r51 = move-exception
            if (r14 != 0) goto L_0x011c
            r0 = r55
            r0.contex = r9
        L_0x011c:
            throw r51
        L_0x011d:
            r51 = 125(0x7d, float:1.75E-43)
            r0 = r51
            if (r7 != r0) goto L_0x016d
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r51 = r0
            int r23 = r51 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r23
            r1 = r51
            if (r0 < r1) goto L_0x015e
            r51 = 26
        L_0x013f:
            r0 = r51
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r51 = 0
            r0 = r51
            r1 = r33
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r14 != 0) goto L_0x0019
            r0 = r55
            r0.contex = r9
            goto L_0x0019
        L_0x015e:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            r1 = r23
            char r51 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x013f
        L_0x016d:
            r51 = 39
            r0 = r51
            if (r7 != r0) goto L_0x01c0
            r0 = r55
            com.alibaba.fastjson.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 39
            r0 = r33
            r1 = r51
            r2 = r52
            java.lang.String r32 = r0.scanSymbol(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r0 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 58
            r0 = r51
            r1 = r52
            if (r0 == r1) goto L_0x0196
            r33.skipWhitespace()     // Catch:{ all -> 0x0115 }
        L_0x0196:
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 58
            r0 = r51
            if (r7 == r0) goto L_0x0b9b
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "expect ':' at "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.pos     // Catch:{ all -> 0x0115 }
            r53 = r0
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x01c0:
            r51 = 26
            r0 = r51
            if (r7 != r0) goto L_0x01e4
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x01e4:
            r51 = 44
            r0 = r51
            if (r7 != r0) goto L_0x0208
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0208:
            r51 = 48
            r0 = r51
            if (r7 < r0) goto L_0x0214
            r51 = 57
            r0 = r51
            if (r7 <= r0) goto L_0x021a
        L_0x0214:
            r51 = 45
            r0 = r51
            if (r7 != r0) goto L_0x028f
        L_0x021a:
            r51 = 0
            r0 = r51
            r1 = r33
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            r33.scanNumber()     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.token     // Catch:{ NumberFormatException -> 0x0270 }
            r51 = r0
            r52 = 2
            r0 = r51
            r1 = r52
            if (r0 != r1) goto L_0x0265
            java.lang.Number r32 = r33.integerValue()     // Catch:{ NumberFormatException -> 0x0270 }
        L_0x0237:
            if (r27 == 0) goto L_0x023d
            java.lang.String r32 = r32.toString()     // Catch:{ NumberFormatException -> 0x0270 }
        L_0x023d:
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 58
            r0 = r51
            if (r7 == r0) goto L_0x0b9f
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "parse number key error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0265:
            r51 = 1
            r0 = r33
            r1 = r51
            java.lang.Number r32 = r0.decimalValue(r1)     // Catch:{ NumberFormatException -> 0x0270 }
            goto L_0x0237
        L_0x0270:
            r18 = move-exception
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "parse number key error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x028f:
            r51 = 123(0x7b, float:1.72E-43)
            r0 = r51
            if (r7 == r0) goto L_0x029b
            r51 = 91
            r0 = r51
            if (r7 != r0) goto L_0x02fa
        L_0x029b:
            r33.nextToken()     // Catch:{ all -> 0x0115 }
            java.lang.Object r32 = r55.parse()     // Catch:{ all -> 0x0115 }
            r28 = 1
            r51 = r32
        L_0x02a6:
            if (r28 != 0) goto L_0x035c
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r52 = r0
            int r23 = r52 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r23
            r1 = r52
            if (r0 < r1) goto L_0x034c
            r7 = 26
        L_0x02c4:
            r0 = r33
            r0.ch = r7     // Catch:{ all -> 0x0115 }
        L_0x02c8:
            r52 = 32
            r0 = r52
            if (r7 > r0) goto L_0x0360
            r52 = 32
            r0 = r52
            if (r7 == r0) goto L_0x02f2
            r52 = 10
            r0 = r52
            if (r7 == r0) goto L_0x02f2
            r52 = 13
            r0 = r52
            if (r7 == r0) goto L_0x02f2
            r52 = 9
            r0 = r52
            if (r7 == r0) goto L_0x02f2
            r52 = 12
            r0 = r52
            if (r7 == r0) goto L_0x02f2
            r52 = 8
            r0 = r52
            if (r7 != r0) goto L_0x0360
        L_0x02f2:
            r33.next()     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            goto L_0x02c8
        L_0x02fa:
            r0 = r55
            com.alibaba.fastjson.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r33
            r1 = r51
            java.lang.String r32 = r0.scanSymbolUnQuoted(r1)     // Catch:{ all -> 0x0115 }
            r33.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 58
            r0 = r51
            if (r7 == r0) goto L_0x0342
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "expect ':' at "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r53 = r0
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = ", actual "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            r0 = r52
            java.lang.StringBuilder r52 = r0.append(r7)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0342:
            if (r27 == 0) goto L_0x0b9b
            java.lang.String r32 = r32.toString()     // Catch:{ all -> 0x0115 }
            r51 = r32
            goto L_0x02a6
        L_0x034c:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r52
            r1 = r23
            char r7 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x02c4
        L_0x035c:
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
        L_0x0360:
            r52 = 0
            r0 = r52
            r1 = r33
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = "@type"
            r0 = r51
            r1 = r52
            if (r0 != r1) goto L_0x0508
            com.alibaba.fastjson.parser.Feature r52 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0115 }
            r0 = r33
            r1 = r52
            boolean r52 = r0.isEnabled(r1)     // Catch:{ all -> 0x0115 }
            if (r52 != 0) goto L_0x0508
            r0 = r55
            com.alibaba.fastjson.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 34
            r0 = r33
            r1 = r51
            r2 = r52
            java.lang.String r49 = r0.scanSymbol(r1, r2)     // Catch:{ all -> 0x0115 }
            r4 = 1
            r22 = 0
        L_0x0392:
            int r51 = r49.length()     // Catch:{ all -> 0x0115 }
            r0 = r22
            r1 = r51
            if (r0 >= r1) goto L_0x03b1
            r0 = r49
            r1 = r22
            char r6 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            r51 = 48
            r0 = r51
            if (r6 < r0) goto L_0x03b0
            r51 = 57
            r0 = r51
            if (r6 <= r0) goto L_0x03de
        L_0x03b0:
            r4 = 0
        L_0x03b1:
            r8 = 0
            if (r4 != 0) goto L_0x03ce
            r0 = r55
            com.alibaba.fastjson.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 0
            r0 = r33
            int r0 = r0.features     // Catch:{ all -> 0x0115 }
            r53 = r0
            r0 = r51
            r1 = r49
            r2 = r52
            r3 = r53
            java.lang.Class r8 = r0.checkAutoType(r1, r2, r3)     // Catch:{ all -> 0x0115 }
        L_0x03ce:
            if (r8 != 0) goto L_0x03e1
            java.lang.String r51 = "@type"
            r0 = r56
            r1 = r51
            r2 = r49
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x0086
        L_0x03de:
            int r22 = r22 + 1
            goto L_0x0392
        L_0x03e1:
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 13
            r0 = r51
            r1 = r52
            if (r0 != r1) goto L_0x049c
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r26 = 0
            r0 = r55
            com.alibaba.fastjson.parser.ParserConfig r0 = r0.config     // Catch:{ Exception -> 0x0460 }
            r51 = r0
            r0 = r51
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r12 = r0.getDeserializer(r8)     // Catch:{ Exception -> 0x0460 }
            boolean r0 = r12 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer     // Catch:{ Exception -> 0x0460 }
            r51 = r0
            if (r51 == 0) goto L_0x046e
            r0 = r12
            com.alibaba.fastjson.parser.JavaBeanDeserializer r0 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r0     // Catch:{ Exception -> 0x0460 }
            r30 = r0
            r0 = r30
            r1 = r55
            java.lang.Object r26 = r0.createInstance(r1, r8)     // Catch:{ Exception -> 0x0460 }
            java.util.Set r51 = r56.entrySet()     // Catch:{ Exception -> 0x0460 }
            java.util.Iterator r51 = r51.iterator()     // Catch:{ Exception -> 0x0460 }
        L_0x042a:
            boolean r52 = r51.hasNext()     // Catch:{ Exception -> 0x0460 }
            if (r52 == 0) goto L_0x046e
            java.lang.Object r36 = r51.next()     // Catch:{ Exception -> 0x0460 }
            r0 = r36
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Exception -> 0x0460 }
            r16 = r0
            java.lang.Object r17 = r16.getKey()     // Catch:{ Exception -> 0x0460 }
            r0 = r17
            boolean r0 = r0 instanceof java.lang.String     // Catch:{ Exception -> 0x0460 }
            r52 = r0
            if (r52 == 0) goto L_0x042a
            java.lang.String r17 = (java.lang.String) r17     // Catch:{ Exception -> 0x0460 }
            r0 = r30
            r1 = r17
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r20 = r0.getFieldDeserializer(r1)     // Catch:{ Exception -> 0x0460 }
            if (r20 == 0) goto L_0x042a
            java.lang.Object r52 = r16.getValue()     // Catch:{ Exception -> 0x0460 }
            r0 = r20
            r1 = r26
            r2 = r52
            r0.setValue(r1, r2)     // Catch:{ Exception -> 0x0460 }
            goto L_0x042a
        L_0x0460:
            r15 = move-exception
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = "create instance error"
            r0 = r51
            r1 = r52
            r0.<init>(r1, r15)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x046e:
            if (r26 != 0) goto L_0x047b
            java.lang.Class<java.lang.Cloneable> r51 = java.lang.Cloneable.class
            r0 = r51
            if (r8 != r0) goto L_0x0485
            java.util.HashMap r26 = new java.util.HashMap     // Catch:{ Exception -> 0x0460 }
            r26.<init>()     // Catch:{ Exception -> 0x0460 }
        L_0x047b:
            if (r14 != 0) goto L_0x0481
            r0 = r55
            r0.contex = r9
        L_0x0481:
            r56 = r26
            goto L_0x0019
        L_0x0485:
            java.lang.String r51 = "java.util.Collections$EmptyMap"
            r0 = r51
            r1 = r49
            boolean r51 = r0.equals(r1)     // Catch:{ Exception -> 0x0460 }
            if (r51 == 0) goto L_0x0497
            java.util.Map r26 = java.util.Collections.emptyMap()     // Catch:{ Exception -> 0x0460 }
            goto L_0x047b
        L_0x0497:
            java.lang.Object r26 = r8.newInstance()     // Catch:{ Exception -> 0x0460 }
            goto L_0x047b
        L_0x049c:
            r51 = 2
            r0 = r51
            r1 = r55
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            r0 = r55
            com.alibaba.fastjson.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x04b7
            r0 = r57
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 != 0) goto L_0x04b7
            r55.popContext()     // Catch:{ all -> 0x0115 }
        L_0x04b7:
            int r51 = r56.size()     // Catch:{ all -> 0x0115 }
            if (r51 <= 0) goto L_0x04dc
            r0 = r55
            com.alibaba.fastjson.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r56
            r1 = r51
            java.lang.Object r35 = com.alibaba.fastjson.util.TypeUtils.cast(r0, r8, r1)     // Catch:{ all -> 0x0115 }
            r0 = r55
            r1 = r35
            r0.parseObject(r1)     // Catch:{ all -> 0x0115 }
            if (r14 != 0) goto L_0x04d8
            r0 = r55
            r0.contex = r9
        L_0x04d8:
            r56 = r35
            goto L_0x0019
        L_0x04dc:
            r0 = r55
            com.alibaba.fastjson.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r12 = r0.getDeserializer(r8)     // Catch:{ all -> 0x0115 }
            r0 = r55
            r1 = r57
            java.lang.Object r13 = r12.deserialze(r0, r8, r1)     // Catch:{ all -> 0x0115 }
            boolean r0 = r12 instanceof com.alibaba.fastjson.parser.MapDeserializer     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x04fe
            r51 = 0
            r0 = r51
            r1 = r55
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
        L_0x04fe:
            if (r14 != 0) goto L_0x0504
            r0 = r55
            r0.contex = r9
        L_0x0504:
            r56 = r13
            goto L_0x0019
        L_0x0508:
            java.lang.String r52 = "$ref"
            r0 = r51
            r1 = r52
            if (r0 != r1) goto L_0x0684
            if (r9 == 0) goto L_0x0684
            com.alibaba.fastjson.parser.Feature r52 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0115 }
            r0 = r33
            r1 = r52
            boolean r52 = r0.isEnabled(r1)     // Catch:{ all -> 0x0115 }
            if (r52 != 0) goto L_0x0684
            r51 = 4
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 4
            r0 = r51
            r1 = r52
            if (r0 != r1) goto L_0x0660
            java.lang.String r40 = r33.stringVal()     // Catch:{ all -> 0x0115 }
            r51 = 13
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r41 = 0
            java.lang.String r51 = "@"
            r0 = r51
            r1 = r40
            boolean r51 = r0.equals(r1)     // Catch:{ all -> 0x0115 }
            if (r51 == 0) goto L_0x05b3
            r0 = r55
            com.alibaba.fastjson.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r46 = r0
            r0 = r46
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            boolean r0 = r0 instanceof java.lang.Object[]     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 != 0) goto L_0x056e
            r0 = r47
            boolean r0 = r0 instanceof java.util.Collection     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x059e
        L_0x056e:
            r41 = r47
        L_0x0570:
            r56 = r41
        L_0x0572:
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 13
            r0 = r51
            r1 = r52
            if (r0 == r1) goto L_0x064f
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x059e:
            r0 = r46
            com.alibaba.fastjson.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x0570
            r0 = r46
            com.alibaba.fastjson.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r41 = r0
            goto L_0x0570
        L_0x05b3:
            java.lang.String r51 = ".."
            r0 = r51
            r1 = r40
            boolean r51 = r0.equals(r1)     // Catch:{ all -> 0x0115 }
            if (r51 == 0) goto L_0x05e8
            java.lang.Object r0 = r9.object     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x05cd
            java.lang.Object r0 = r9.object     // Catch:{ all -> 0x0115 }
            r41 = r0
            r56 = r41
            goto L_0x0572
        L_0x05cd:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r51 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r40
            r0.<init>(r9, r1)     // Catch:{ all -> 0x0115 }
            r0 = r55
            r1 = r51
            r0.addResolveTask(r1)     // Catch:{ all -> 0x0115 }
            r51 = 1
            r0 = r51
            r1 = r55
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            r56 = r41
            goto L_0x0572
        L_0x05e8:
            java.lang.String r51 = "$"
            r0 = r51
            r1 = r40
            boolean r51 = r0.equals(r1)     // Catch:{ all -> 0x0115 }
            if (r51 == 0) goto L_0x0633
            r43 = r9
        L_0x05f7:
            r0 = r43
            com.alibaba.fastjson.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x0606
            r0 = r43
            com.alibaba.fastjson.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r43 = r0
            goto L_0x05f7
        L_0x0606:
            r0 = r43
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r51 = r0
            if (r51 == 0) goto L_0x0618
            r0 = r43
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r41 = r0
        L_0x0614:
            r56 = r41
            goto L_0x0572
        L_0x0618:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r51 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r43
            r2 = r40
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r55
            r1 = r51
            r0.addResolveTask(r1)     // Catch:{ all -> 0x0115 }
            r51 = 1
            r0 = r51
            r1 = r55
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0614
        L_0x0633:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r51 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r40
            r0.<init>(r9, r1)     // Catch:{ all -> 0x0115 }
            r0 = r55
            r1 = r51
            r0.addResolveTask(r1)     // Catch:{ all -> 0x0115 }
            r51 = 1
            r0 = r51
            r1 = r55
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            r56 = r41
            goto L_0x0572
        L_0x064f:
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r14 != 0) goto L_0x0019
            r0 = r55
            r0.contex = r9
            goto L_0x0019
        L_0x0660:
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "illegal ref, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r53 = r0
            java.lang.String r53 = com.alibaba.fastjson.parser.JSONToken.name(r53)     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0684:
            if (r14 != 0) goto L_0x069f
            if (r44 != 0) goto L_0x069f
            r0 = r55
            com.alibaba.fastjson.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r55
            r1 = r52
            r2 = r56
            r3 = r57
            com.alibaba.fastjson.parser.ParseContext r10 = r0.setContext(r1, r2, r3)     // Catch:{ all -> 0x0115 }
            if (r9 != 0) goto L_0x069d
            r9 = r10
        L_0x069d:
            r44 = 1
        L_0x069f:
            r52 = 34
            r0 = r52
            if (r7 != r0) goto L_0x072b
            r52 = 34
            r0 = r33
            r1 = r52
            java.lang.String r45 = r0.scanStringValue(r1)     // Catch:{ all -> 0x0115 }
            r50 = r45
            if (r5 == 0) goto L_0x06d5
            com.alibaba.fastjson.parser.JSONLexer r29 = new com.alibaba.fastjson.parser.JSONLexer     // Catch:{ all -> 0x0115 }
            r0 = r29
            r1 = r45
            r0.<init>(r1)     // Catch:{ all -> 0x0115 }
            r52 = 1
            r0 = r29
            r1 = r52
            boolean r52 = r0.scanISO8601DateIfMatch(r1)     // Catch:{ all -> 0x0115 }
            if (r52 == 0) goto L_0x06d2
            r0 = r29
            java.util.Calendar r0 = r0.calendar     // Catch:{ all -> 0x0115 }
            r52 = r0
            java.util.Date r50 = r52.getTime()     // Catch:{ all -> 0x0115 }
        L_0x06d2:
            r29.close()     // Catch:{ all -> 0x0115 }
        L_0x06d5:
            if (r24 == 0) goto L_0x0721
            r0 = r24
            r1 = r51
            r2 = r50
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x06e0:
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
            r51 = 44
            r0 = r51
            if (r7 == r0) goto L_0x06f7
            r51 = 125(0x7d, float:1.75E-43)
            r0 = r51
            if (r7 == r0) goto L_0x06f7
            r33.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r33
            char r7 = r0.ch     // Catch:{ all -> 0x0115 }
        L_0x06f7:
            r51 = 44
            r0 = r51
            if (r7 != r0) goto L_0x0a60
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r51 = r0
            int r23 = r51 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r23
            r1 = r51
            if (r0 < r1) goto L_0x0a50
            r51 = 26
        L_0x0719:
            r0 = r51
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0086
        L_0x0721:
            r0 = r56
            r1 = r51
            r2 = r50
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x06e0
        L_0x072b:
            r52 = 48
            r0 = r52
            if (r7 < r0) goto L_0x0737
            r52 = 57
            r0 = r52
            if (r7 <= r0) goto L_0x073d
        L_0x0737:
            r52 = 45
            r0 = r52
            if (r7 != r0) goto L_0x074b
        L_0x073d:
            java.lang.Number r50 = r33.scanNumberValue()     // Catch:{ all -> 0x0115 }
            r0 = r24
            r1 = r51
            r2 = r50
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x06e0
        L_0x074b:
            r52 = 91
            r0 = r52
            if (r7 != r0) goto L_0x0815
            r52 = 14
            r0 = r52
            r1 = r33
            r1.token = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r52 = r0
            int r23 = r52 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r23
            r1 = r52
            if (r0 < r1) goto L_0x07d3
            r52 = 26
        L_0x0775:
            r0 = r52
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            java.util.ArrayList r34 = new java.util.ArrayList     // Catch:{ all -> 0x0115 }
            r34.<init>()     // Catch:{ all -> 0x0115 }
            if (r57 == 0) goto L_0x07e2
            java.lang.Class r52 = r57.getClass()     // Catch:{ all -> 0x0115 }
            java.lang.Class<java.lang.Integer> r53 = java.lang.Integer.class
            r0 = r52
            r1 = r53
            if (r0 != r1) goto L_0x07e2
            r39 = 1
        L_0x0790:
            if (r39 != 0) goto L_0x0797
            r0 = r55
            r0.setContext(r9)     // Catch:{ all -> 0x0115 }
        L_0x0797:
            r0 = r55
            r1 = r34
            r2 = r51
            r0.parseArray(r1, r2)     // Catch:{ all -> 0x0115 }
            com.alibaba.fastjson.JSONArray r50 = new com.alibaba.fastjson.JSONArray     // Catch:{ all -> 0x0115 }
            r0 = r50
            r1 = r34
            r0.<init>(r1)     // Catch:{ all -> 0x0115 }
            if (r24 == 0) goto L_0x07e5
            r0 = r24
            r1 = r51
            r2 = r50
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x07b4:
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r48 = r0
            r51 = 13
            r0 = r48
            r1 = r51
            if (r0 != r1) goto L_0x07ef
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r14 != 0) goto L_0x0019
            r0 = r55
            r0.contex = r9
            goto L_0x0019
        L_0x07d3:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r52
            r1 = r23
            char r52 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0775
        L_0x07e2:
            r39 = 0
            goto L_0x0790
        L_0x07e5:
            r0 = r56
            r1 = r51
            r2 = r50
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x07b4
        L_0x07ef:
            r51 = 16
            r0 = r48
            r1 = r51
            if (r0 == r1) goto L_0x0086
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0815:
            r52 = 123(0x7b, float:1.72E-43)
            r0 = r52
            if (r7 != r0) goto L_0x0973
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r52 = r0
            int r23 = r52 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r23
            r1 = r52
            if (r0 < r1) goto L_0x0928
            r52 = 26
        L_0x0837:
            r0 = r52
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r52 = 12
            r0 = r52
            r1 = r33
            r1.token = r0     // Catch:{ all -> 0x0115 }
            r0 = r57
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ all -> 0x0115 }
            r39 = r0
            r0 = r33
            int r0 = r0.features     // Catch:{ all -> 0x0115 }
            r52 = r0
            com.alibaba.fastjson.parser.Feature r53 = com.alibaba.fastjson.parser.Feature.OrderedField     // Catch:{ all -> 0x0115 }
            r0 = r53
            int r0 = r0.mask     // Catch:{ all -> 0x0115 }
            r53 = r0
            r52 = r52 & r53
            if (r52 == 0) goto L_0x0938
            com.alibaba.fastjson.JSONObject r25 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0115 }
            java.util.LinkedHashMap r52 = new java.util.LinkedHashMap     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            r0 = r25
            r1 = r52
            r0.<init>(r1)     // Catch:{ all -> 0x0115 }
        L_0x086b:
            r11 = 0
            if (r14 != 0) goto L_0x087a
            if (r39 != 0) goto L_0x087a
            r0 = r55
            r1 = r25
            r2 = r51
            com.alibaba.fastjson.parser.ParseContext r11 = r0.setContext(r9, r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x087a:
            r37 = 0
            r38 = 0
            r0 = r55
            com.alibaba.fastjson.parser.deserializer.FieldTypeResolver r0 = r0.fieldTypeResolver     // Catch:{ all -> 0x0115 }
            r52 = r0
            if (r52 == 0) goto L_0x08ba
            if (r51 == 0) goto L_0x093f
            java.lang.String r42 = r51.toString()     // Catch:{ all -> 0x0115 }
        L_0x088c:
            r0 = r55
            com.alibaba.fastjson.parser.deserializer.FieldTypeResolver r0 = r0.fieldTypeResolver     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r52
            r1 = r56
            r2 = r42
            java.lang.reflect.Type r21 = r0.resolve(r1, r2)     // Catch:{ all -> 0x0115 }
            if (r21 == 0) goto L_0x08ba
            r0 = r55
            com.alibaba.fastjson.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r52
            r1 = r21
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r19 = r0.getDeserializer(r1)     // Catch:{ all -> 0x0115 }
            r0 = r19
            r1 = r55
            r2 = r21
            r3 = r51
            java.lang.Object r37 = r0.deserialze(r1, r2, r3)     // Catch:{ all -> 0x0115 }
            r38 = 1
        L_0x08ba:
            if (r38 != 0) goto L_0x08c6
            r0 = r55
            r1 = r25
            r2 = r51
            java.lang.Object r37 = r0.parseObject(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x08c6:
            if (r11 == 0) goto L_0x08d2
            r0 = r25
            r1 = r37
            if (r0 == r1) goto L_0x08d2
            r0 = r56
            r11.object = r0     // Catch:{ all -> 0x0115 }
        L_0x08d2:
            r0 = r55
            int r0 = r0.resolveStatus     // Catch:{ all -> 0x0115 }
            r52 = r0
            r53 = 1
            r0 = r52
            r1 = r53
            if (r0 != r1) goto L_0x08ed
            java.lang.String r52 = r51.toString()     // Catch:{ all -> 0x0115 }
            r0 = r55
            r1 = r56
            r2 = r52
            r0.checkMapResolve(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x08ed:
            if (r24 == 0) goto L_0x0943
            r0 = r24
            r1 = r51
            r2 = r37
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x08f8:
            if (r39 == 0) goto L_0x0903
            r0 = r55
            r1 = r37
            r2 = r51
            r0.setContext(r9, r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x0903:
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r48 = r0
            r51 = 13
            r0 = r48
            r1 = r51
            if (r0 != r1) goto L_0x094d
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r14 != 0) goto L_0x0920
            r0 = r55
            r0.contex = r9     // Catch:{ all -> 0x0115 }
        L_0x0920:
            if (r14 != 0) goto L_0x0019
            r0 = r55
            r0.contex = r9
            goto L_0x0019
        L_0x0928:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r52 = r0
            r0 = r52
            r1 = r23
            char r52 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0837
        L_0x0938:
            com.alibaba.fastjson.JSONObject r25 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0115 }
            r25.<init>()     // Catch:{ all -> 0x0115 }
            goto L_0x086b
        L_0x093f:
            r42 = 0
            goto L_0x088c
        L_0x0943:
            r0 = r56
            r1 = r51
            r2 = r37
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x08f8
        L_0x094d:
            r51 = 16
            r0 = r48
            r1 = r51
            if (r0 == r1) goto L_0x0086
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0973:
            r52 = 116(0x74, float:1.63E-43)
            r0 = r52
            if (r7 != r0) goto L_0x09ac
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r52 = r0
            java.lang.String r53 = "true"
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r54 = r0
            boolean r52 = r52.startsWith(r53, r54)     // Catch:{ all -> 0x0115 }
            if (r52 == 0) goto L_0x06e0
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r52 = r0
            int r52 = r52 + 3
            r0 = r52
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r33.next()     // Catch:{ all -> 0x0115 }
            java.lang.Boolean r52 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0115 }
            r0 = r56
            r1 = r51
            r2 = r52
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x06e0
        L_0x09ac:
            r52 = 102(0x66, float:1.43E-43)
            r0 = r52
            if (r7 != r0) goto L_0x09e5
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r52 = r0
            java.lang.String r53 = "false"
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r54 = r0
            boolean r52 = r52.startsWith(r53, r54)     // Catch:{ all -> 0x0115 }
            if (r52 == 0) goto L_0x06e0
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r52 = r0
            int r52 = r52 + 4
            r0 = r52
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r33.next()     // Catch:{ all -> 0x0115 }
            java.lang.Boolean r52 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0115 }
            r0 = r56
            r1 = r51
            r2 = r52
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x06e0
        L_0x09e5:
            r33.nextToken()     // Catch:{ all -> 0x0115 }
            java.lang.Object r50 = r55.parse()     // Catch:{ all -> 0x0115 }
            java.lang.Class r52 = r56.getClass()     // Catch:{ all -> 0x0115 }
            java.lang.Class<com.alibaba.fastjson.JSONObject> r53 = com.alibaba.fastjson.JSONObject.class
            r0 = r52
            r1 = r53
            if (r0 != r1) goto L_0x09fc
            java.lang.String r51 = r51.toString()     // Catch:{ all -> 0x0115 }
        L_0x09fc:
            r0 = r56
            r1 = r51
            r2 = r50
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 13
            r0 = r51
            r1 = r52
            if (r0 != r1) goto L_0x0a24
            r51 = 16
            r0 = r33
            r1 = r51
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r14 != 0) goto L_0x0019
            r0 = r55
            r0.contex = r9
            goto L_0x0019
        L_0x0a24:
            r0 = r33
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r51 = r0
            r52 = 16
            r0 = r51
            r1 = r52
            if (r0 == r1) goto L_0x0086
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0a50:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            r1 = r23
            char r51 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0719
        L_0x0a60:
            r51 = 125(0x7d, float:1.75E-43)
            r0 = r51
            if (r7 != r0) goto L_0x0b7d
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r51 = r0
            int r23 = r51 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r23
            r1 = r51
            if (r0 < r1) goto L_0x0ad9
            r7 = 26
        L_0x0a82:
            r0 = r33
            r0.ch = r7     // Catch:{ all -> 0x0115 }
            r51 = 0
            r0 = r51
            r1 = r33
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            r51 = 44
            r0 = r51
            if (r7 != r0) goto L_0x0af7
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r51 = r0
            int r23 = r51 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r23
            r1 = r51
            if (r0 < r1) goto L_0x0ae8
            r51 = 26
        L_0x0ab0:
            r0 = r51
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r51 = 16
            r0 = r51
            r1 = r33
            r1.token = r0     // Catch:{ all -> 0x0115 }
        L_0x0abe:
            if (r14 != 0) goto L_0x0ad1
            r0 = r55
            com.alibaba.fastjson.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r55
            r1 = r51
            r2 = r56
            r3 = r57
            r0.setContext(r1, r2, r3)     // Catch:{ all -> 0x0115 }
        L_0x0ad1:
            if (r14 != 0) goto L_0x0019
            r0 = r55
            r0.contex = r9
            goto L_0x0019
        L_0x0ad9:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            r1 = r23
            char r7 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0a82
        L_0x0ae8:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            r1 = r23
            char r51 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0ab0
        L_0x0af7:
            r51 = 125(0x7d, float:1.75E-43)
            r0 = r51
            if (r7 != r0) goto L_0x0b37
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r51 = r0
            int r23 = r51 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r23
            r1 = r51
            if (r0 < r1) goto L_0x0b28
            r51 = 26
        L_0x0b19:
            r0 = r51
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r51 = 13
            r0 = r51
            r1 = r33
            r1.token = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0abe
        L_0x0b28:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            r1 = r23
            char r51 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0b19
        L_0x0b37:
            r51 = 93
            r0 = r51
            if (r7 != r0) goto L_0x0b78
            r0 = r33
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r51 = r0
            int r23 = r51 + 1
            r0 = r23
            r1 = r33
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r33
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r23
            r1 = r51
            if (r0 < r1) goto L_0x0b69
            r51 = 26
        L_0x0b59:
            r0 = r51
            r1 = r33
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r51 = 15
            r0 = r51
            r1 = r33
            r1.token = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0abe
        L_0x0b69:
            r0 = r33
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r51 = r0
            r0 = r51
            r1 = r23
            char r51 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0b59
        L_0x0b78:
            r33.nextToken()     // Catch:{ all -> 0x0115 }
            goto L_0x0abe
        L_0x0b7d:
            com.alibaba.fastjson.JSONException r51 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r52.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = "syntax error, "
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r53 = r33.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r52 = r52.append(r53)     // Catch:{ all -> 0x0115 }
            java.lang.String r52 = r52.toString()     // Catch:{ all -> 0x0115 }
            r51.<init>(r52)     // Catch:{ all -> 0x0115 }
            throw r51     // Catch:{ all -> 0x0115 }
        L_0x0b9b:
            r51 = r32
            goto L_0x02a6
        L_0x0b9f:
            r51 = r32
            goto L_0x02a6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public <T> T parseObject(Class<T> clazz) {
        return parseObject((Type) clazz, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return parseObject(type, (Object) null);
    }

    public <T> T parseObject(Type type, Object fieldName) {
        if (this.lexer.token == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (this.lexer.token == 4) {
            if (type == byte[].class) {
                Object bytesValue = this.lexer.bytesValue();
                this.lexer.nextToken();
                return bytesValue;
            } else if (type == char[].class) {
                String strVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return strVal.toCharArray();
            }
        }
        try {
            return this.config.getDeserializer(type).deserialze(this, type, fieldName);
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    public <T> List<T> parseArray(Class<T> clazz) {
        List<T> array = new ArrayList<>();
        parseArray(clazz, (Collection) array);
        return array;
    }

    public void parseArray(Class<?> clazz, Collection array) {
        parseArray((Type) clazz, array);
    }

    public void parseArray(Type type, Collection array) {
        parseArray(type, array, null);
    }

    /* JADX INFO: finally extract failed */
    public void parseArray(Type type, Collection array, Object fieldName) {
        ObjectDeserializer deserializer;
        Object deserialze;
        String value;
        if (this.lexer.token == 21 || this.lexer.token == 22) {
            this.lexer.nextToken();
        }
        if (this.lexer.token != 14) {
            throw new JSONException("exepct '[', but " + JSONToken.name(this.lexer.token) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            deserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(12);
        }
        ParseContext context = this.contex;
        if (!this.lexer.disableCircularReferenceDetect) {
            setContext(this.contex, array, fieldName);
        }
        int i = 0;
        while (true) {
            try {
                if (this.lexer.token == 16) {
                    this.lexer.nextToken();
                } else if (this.lexer.token == 15) {
                    this.contex = context;
                    this.lexer.nextToken(16);
                    return;
                } else {
                    if (Integer.TYPE == type) {
                        array.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token == 4) {
                            value = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj = parse();
                            if (obj == null) {
                                value = null;
                            } else {
                                value = obj.toString();
                            }
                        }
                        array.add(value);
                    } else {
                        if (this.lexer.token == 8) {
                            this.lexer.nextToken();
                            deserialze = null;
                        } else {
                            deserialze = deserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        array.add(deserialze);
                        if (this.resolveStatus == 1) {
                            checkListResolve(array);
                        }
                    }
                    if (this.lexer.token == 16) {
                        this.lexer.nextToken();
                    }
                    i++;
                }
            } catch (Throwable th) {
                this.contex = context;
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] types) {
        Object value;
        if (this.lexer.token == 8) {
            this.lexer.nextToken(16);
            return null;
        } else if (this.lexer.token != 14) {
            throw new JSONException("syntax error, " + this.lexer.info());
        } else {
            Object[] list = new Object[types.length];
            if (types.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token != 15) {
                    throw new JSONException("syntax error, " + this.lexer.info());
                }
                this.lexer.nextToken(16);
                return new Object[0];
            }
            this.lexer.nextToken(2);
            int i = 0;
            while (i < types.length) {
                if (this.lexer.token == 8) {
                    value = null;
                    this.lexer.nextToken(16);
                } else {
                    Class<String> cls = types[i];
                    if (cls == Integer.TYPE || cls == Integer.class) {
                        if (this.lexer.token == 2) {
                            value = Integer.valueOf(this.lexer.intValue());
                            this.lexer.nextToken(16);
                        } else {
                            value = TypeUtils.cast(parse(), (Type) cls, this.config);
                        }
                    } else if (cls != String.class) {
                        boolean isArray = false;
                        Class<?> componentType = null;
                        if (i == types.length - 1 && (cls instanceof Class)) {
                            Class<?> clazz = cls;
                            isArray = clazz.isArray();
                            componentType = clazz.getComponentType();
                        }
                        if (!isArray || this.lexer.token == 14) {
                            value = this.config.getDeserializer(cls).deserialze(this, cls, null);
                        } else {
                            List<Object> varList = new ArrayList<>();
                            ObjectDeserializer derializer = this.config.getDeserializer(componentType);
                            if (this.lexer.token != 15) {
                                while (true) {
                                    varList.add(derializer.deserialze(this, cls, null));
                                    if (this.lexer.token != 16) {
                                        break;
                                    }
                                    this.lexer.nextToken(12);
                                }
                                if (this.lexer.token != 15) {
                                    throw new JSONException("syntax error, " + this.lexer.info());
                                }
                            }
                            value = TypeUtils.cast((Object) varList, (Type) cls, this.config);
                        }
                    } else if (this.lexer.token == 4) {
                        value = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        value = TypeUtils.cast(parse(), (Type) cls, this.config);
                    }
                }
                list[i] = value;
                if (this.lexer.token == 15) {
                    break;
                } else if (this.lexer.token != 16) {
                    throw new JSONException("syntax error, " + this.lexer.info());
                } else {
                    if (i == types.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                    i++;
                }
            }
            if (this.lexer.token != 15) {
                throw new JSONException("syntax error, " + this.lexer.info());
            }
            this.lexer.nextToken(16);
            return list;
        }
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 147 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseObject(java.lang.Object r14) {
        /*
            r13 = this;
            java.lang.Class r1 = r14.getClass()
            r0 = 0
            com.alibaba.fastjson.parser.ParserConfig r10 = r13.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r2 = r10.getDeserializer(r1)
            boolean r10 = r2 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer
            if (r10 == 0) goto L_0x0012
            r0 = r2
            com.alibaba.fastjson.parser.JavaBeanDeserializer r0 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r0
        L_0x0012:
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r9 = r10.token
            r10 = 12
            if (r9 == r10) goto L_0x0044
            r10 = 16
            if (r9 == r10) goto L_0x0044
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "syntax error, expect {, actual "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = com.alibaba.fastjson.parser.JSONToken.name(r9)
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x003c:
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 16
            if (r10 != r11) goto L_0x005e
        L_0x0044:
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            com.alibaba.fastjson.parser.SymbolTable r11 = r13.symbolTable
            java.lang.String r8 = r10.scanSymbol(r11)
            if (r8 != 0) goto L_0x005e
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 13
            if (r10 != r11) goto L_0x003c
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 16
            r10.nextToken(r11)
        L_0x005d:
            return
        L_0x005e:
            r4 = 0
            if (r0 == 0) goto L_0x0065
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r4 = r0.getFieldDeserializer(r8)
        L_0x0065:
            if (r4 != 0) goto L_0x00b3
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.features
            com.alibaba.fastjson.parser.Feature r11 = com.alibaba.fastjson.parser.Feature.IgnoreNotMatch
            int r11 = r11.mask
            r10 = r10 & r11
            if (r10 != 0) goto L_0x009b
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "setter not found, class "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = r1.getName()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = ", property "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r8)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x009b:
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            r13.parse()
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 13
            if (r10 != r11) goto L_0x0044
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r10.nextToken()
            goto L_0x005d
        L_0x00b3:
            com.alibaba.fastjson.util.FieldInfo r10 = r4.fieldInfo
            java.lang.Class<?> r3 = r10.fieldClass
            com.alibaba.fastjson.util.FieldInfo r10 = r4.fieldInfo
            java.lang.reflect.Type r5 = r10.fieldType
            java.lang.Class r10 = java.lang.Integer.TYPE
            if (r3 != r10) goto L_0x00e9
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            com.alibaba.fastjson.serializer.IntegerCodec r10 = com.alibaba.fastjson.serializer.IntegerCodec.instance
            r11 = 0
            java.lang.Object r6 = r10.deserialze(r13, r5, r11)
        L_0x00cd:
            r4.setValue(r14, r6)
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 16
            if (r10 == r11) goto L_0x0044
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 13
            if (r10 != r11) goto L_0x0044
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 16
            r10.nextToken(r11)
            goto L_0x005d
        L_0x00e9:
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            if (r3 != r10) goto L_0x00f9
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            java.lang.String r6 = r13.parseString()
            goto L_0x00cd
        L_0x00f9:
            java.lang.Class r10 = java.lang.Long.TYPE
            if (r3 != r10) goto L_0x010c
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            com.alibaba.fastjson.serializer.IntegerCodec r10 = com.alibaba.fastjson.serializer.IntegerCodec.instance
            r11 = 0
            java.lang.Object r6 = r10.deserialze(r13, r5, r11)
            goto L_0x00cd
        L_0x010c:
            com.alibaba.fastjson.parser.ParserConfig r10 = r13.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r7 = r10.getDeserializer(r3, r5)
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            r10 = 0
            java.lang.Object r6 = r7.deserialze(r13, r5, r10)
            goto L_0x00cd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.lang.Object):void");
    }

    public Object parseArrayWithType(Type collectionType) {
        if (this.lexer.token == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypes = ((ParameterizedType) collectionType).getActualTypeArguments();
        if (actualTypes.length != 1) {
            throw new JSONException("not support type " + collectionType);
        }
        Type actualTypeArgument = actualTypes[0];
        if (actualTypeArgument instanceof Class) {
            List<Object> array = new ArrayList<>();
            parseArray((Class) actualTypeArgument, (Collection) array);
            return array;
        } else if (actualTypeArgument instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) actualTypeArgument;
            Type upperBoundType = wildcardType.getUpperBounds()[0];
            if (!Object.class.equals(upperBoundType)) {
                List<Object> array2 = new ArrayList<>();
                parseArray((Class) upperBoundType, (Collection) array2);
                return array2;
            } else if (wildcardType.getLowerBounds().length == 0) {
                return parse();
            } else {
                throw new JSONException("not support type : " + collectionType);
            }
        } else {
            if (actualTypeArgument instanceof TypeVariable) {
                TypeVariable<?> typeVariable = (TypeVariable) actualTypeArgument;
                Type[] bounds = typeVariable.getBounds();
                if (bounds.length != 1) {
                    throw new JSONException("not support : " + typeVariable);
                }
                Type boundType = bounds[0];
                if (boundType instanceof Class) {
                    List<Object> array3 = new ArrayList<>();
                    parseArray((Class) boundType, (Collection) array3);
                    return array3;
                }
            }
            if (actualTypeArgument instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) actualTypeArgument;
                List<Object> array4 = new ArrayList<>();
                parseArray((Type) parameterizedType, (Collection) array4);
                return array4;
            }
            throw new JSONException("TODO : " + collectionType);
        }
    }

    /* access modifiers changed from: protected */
    public void checkListResolve(Collection array) {
        if (array instanceof List) {
            ResolveTask task = getLastResolveTask();
            task.fieldDeserializer = new ResolveFieldDeserializer(this, (List) array, array.size() - 1);
            task.ownerContext = this.contex;
            this.resolveStatus = 0;
            return;
        }
        ResolveTask task2 = getLastResolveTask();
        task2.fieldDeserializer = new ResolveFieldDeserializer(array);
        task2.ownerContext = this.contex;
        this.resolveStatus = 0;
    }

    /* access modifiers changed from: protected */
    public void checkMapResolve(Map object, Object fieldName) {
        ResolveFieldDeserializer fieldResolver = new ResolveFieldDeserializer(object, fieldName);
        ResolveTask task = getLastResolveTask();
        task.fieldDeserializer = fieldResolver;
        task.ownerContext = this.contex;
        this.resolveStatus = 0;
    }

    public Object parseObject(Map object) {
        return parseObject(object, (Object) null);
    }

    public JSONObject parseObject() {
        return (JSONObject) parseObject((Map) (this.lexer.features & Feature.OrderedField.mask) != 0 ? new JSONObject((Map<String, Object>) new LinkedHashMap<String,Object>()) : new JSONObject(), (Object) null);
    }

    public final void parseArray(Collection array) {
        parseArray(array, (Object) null);
    }

    /* JADX WARNING: type inference failed for: r14v0 */
    /* JADX WARNING: type inference failed for: r14v1 */
    /* JADX WARNING: type inference failed for: r10v0, types: [com.alibaba.fastjson.JSONArray, java.util.Collection] */
    /* JADX WARNING: type inference failed for: r14v2 */
    /* JADX WARNING: type inference failed for: r14v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r14v4, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r14v5, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r12v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r14v6 */
    /* JADX WARNING: type inference failed for: r14v7 */
    /* JADX WARNING: type inference failed for: r14v8 */
    /* JADX WARNING: type inference failed for: r14v9, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r14v10, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r14v11 */
    /* JADX WARNING: type inference failed for: r14v12, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r14v13, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r14v14, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r14v15, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r14v17 */
    /* JADX WARNING: type inference failed for: r14v18 */
    /* JADX WARNING: type inference failed for: r14v19 */
    /* JADX WARNING: type inference failed for: r14v20 */
    /* JADX WARNING: type inference failed for: r14v21 */
    /* JADX WARNING: type inference failed for: r14v22 */
    /* JADX WARNING: type inference failed for: r14v23 */
    /* JADX WARNING: type inference failed for: r14v24 */
    /* JADX WARNING: type inference failed for: r14v25 */
    /* JADX WARNING: type inference failed for: r14v26 */
    /* JADX WARNING: type inference failed for: r14v27 */
    /* JADX WARNING: type inference failed for: r14v28 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r14v8
      assigns: []
      uses: []
      mth insns count: 405
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
    /* JADX WARNING: Unknown variable types count: 14 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void parseArray(java.util.Collection r19, java.lang.Object r20) {
        /*
            r18 = this;
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer
            int r13 = r15.token
            r15 = 21
            if (r13 == r15) goto L_0x000e
            r15 = 22
            if (r13 != r15) goto L_0x001b
        L_0x000e:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer
            r15.nextToken()
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer
            int r13 = r15.token
        L_0x001b:
            r15 = 14
            if (r13 == r15) goto L_0x0054
            com.alibaba.fastjson.JSONException r15 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "syntax error, expect [, actual "
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r17 = com.alibaba.fastjson.parser.JSONToken.name(r13)
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r17 = ", pos "
            java.lang.StringBuilder r16 = r16.append(r17)
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer
            r17 = r0
            r0 = r17
            int r0 = r0.pos
            r17 = r0
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r16 = r16.toString()
            r15.<init>(r16)
            throw r15
        L_0x0054:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer
            boolean r5 = r15.disableCircularReferenceDetect
            r0 = r18
            com.alibaba.fastjson.parser.ParseContext r4 = r0.contex
            if (r5 != 0) goto L_0x006d
            r0 = r18
            com.alibaba.fastjson.parser.ParseContext r15 = r0.contex
            r0 = r18
            r1 = r19
            r2 = r20
            r0.setContext(r15, r1, r2)
        L_0x006d:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r3 = r15.ch     // Catch:{ all -> 0x0136 }
            r15 = 34
            if (r3 == r15) goto L_0x013e
            r15 = 93
            if (r3 != r15) goto L_0x0092
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.next()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            if (r5 != 0) goto L_0x0091
            r0 = r18
            r0.contex = r4
        L_0x0091:
            return
        L_0x0092:
            r15 = 123(0x7b, float:1.72E-43)
            if (r3 != r15) goto L_0x012c
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.len     // Catch:{ all -> 0x0136 }
            if (r8 < r15) goto L_0x0121
            r15 = 26
        L_0x00b2:
            r0 = r16
            r0.ch = r15     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 12
            r0 = r16
            r15.token = r0     // Catch:{ all -> 0x0136 }
        L_0x00c0:
            r6 = 0
        L_0x00c1:
            r7 = 0
        L_0x00c2:
            if (r6 == 0) goto L_0x017a
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r15 = r15.ch     // Catch:{ all -> 0x0136 }
            r16 = 34
            r0 = r16
            if (r15 != r0) goto L_0x017a
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 34
            java.lang.String r14 = r15.scanStringValue(r16)     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r3 = r15.ch     // Catch:{ all -> 0x0136 }
            r15 = 44
            if (r3 != r15) goto L_0x0192
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            int r0 = r0.len     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            if (r8 < r0) goto L_0x015f
            r3 = 26
        L_0x0106:
            r15.ch = r3     // Catch:{ all -> 0x0136 }
            r0 = r19
            r0.add(r14)     // Catch:{ all -> 0x0136 }
            r0 = r18
            int r15 = r0.resolveStatus     // Catch:{ all -> 0x0136 }
            r16 = 1
            r0 = r16
            if (r15 != r0) goto L_0x011a
            r18.checkListResolve(r19)     // Catch:{ all -> 0x0136 }
        L_0x011a:
            r15 = 34
            if (r3 != r15) goto L_0x0172
        L_0x011e:
            int r7 = r7 + 1
            goto L_0x00c2
        L_0x0121:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r15 = r15.text     // Catch:{ all -> 0x0136 }
            char r15 = r15.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x00b2
        L_0x012c:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 12
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x00c0
        L_0x0136:
            r15 = move-exception
            if (r5 != 0) goto L_0x013d
            r0 = r18
            r0.contex = r4
        L_0x013d:
            throw r15
        L_0x013e:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.fastjson.parser.Feature r16 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 != 0) goto L_0x0153
            r6 = 1
            goto L_0x00c1
        L_0x0153:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 4
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            r6 = 0
            goto L_0x00c1
        L_0x015f:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            char r3 = r0.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x0106
        L_0x0172:
            r6 = 0
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
        L_0x017a:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r13 = r15.token     // Catch:{ all -> 0x0136 }
        L_0x0180:
            r15 = 16
            if (r13 != r15) goto L_0x01ec
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r13 = r15.token     // Catch:{ all -> 0x0136 }
            goto L_0x0180
        L_0x0192:
            r15 = 93
            if (r3 != r15) goto L_0x01e4
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.len     // Catch:{ all -> 0x0136 }
            if (r8 < r15) goto L_0x01d9
            r15 = 26
        L_0x01b2:
            r0 = r16
            r0.ch = r15     // Catch:{ all -> 0x0136 }
            r0 = r19
            r0.add(r14)     // Catch:{ all -> 0x0136 }
            r0 = r18
            int r15 = r0.resolveStatus     // Catch:{ all -> 0x0136 }
            r16 = 1
            r0 = r16
            if (r15 != r0) goto L_0x01c8
            r18.checkListResolve(r19)     // Catch:{ all -> 0x0136 }
        L_0x01c8:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            if (r5 != 0) goto L_0x0091
            r0 = r18
            r0.contex = r4
            goto L_0x0091
        L_0x01d9:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r15 = r15.text     // Catch:{ all -> 0x0136 }
            char r15 = r15.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x01b2
        L_0x01e4:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
            goto L_0x017a
        L_0x01ec:
            switch(r13) {
                case 2: goto L_0x0238;
                case 3: goto L_0x024a;
                case 4: goto L_0x027b;
                case 5: goto L_0x01ef;
                case 6: goto L_0x02ba;
                case 7: goto L_0x02c7;
                case 8: goto L_0x0313;
                case 9: goto L_0x01ef;
                case 10: goto L_0x01ef;
                case 11: goto L_0x01ef;
                case 12: goto L_0x02d4;
                case 13: goto L_0x01ef;
                case 14: goto L_0x0302;
                case 15: goto L_0x032b;
                case 16: goto L_0x01ef;
                case 17: goto L_0x01ef;
                case 18: goto L_0x01ef;
                case 19: goto L_0x01ef;
                case 20: goto L_0x033c;
                case 21: goto L_0x01ef;
                case 22: goto L_0x01ef;
                case 23: goto L_0x031f;
                default: goto L_0x01ef;
            }     // Catch:{ all -> 0x0136 }
        L_0x01ef:
            java.lang.Object r14 = r18.parse()     // Catch:{ all -> 0x0136 }
        L_0x01f3:
            r0 = r19
            r0.add(r14)     // Catch:{ all -> 0x0136 }
            r0 = r18
            int r15 = r0.resolveStatus     // Catch:{ all -> 0x0136 }
            r16 = 1
            r0 = r16
            if (r15 != r0) goto L_0x0205
            r18.checkListResolve(r19)     // Catch:{ all -> 0x0136 }
        L_0x0205:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.token     // Catch:{ all -> 0x0136 }
            r16 = 16
            r0 = r16
            if (r15 != r0) goto L_0x011e
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r3 = r15.ch     // Catch:{ all -> 0x0136 }
            r15 = 34
            if (r3 != r15) goto L_0x0345
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            int r0 = r0.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            r15.pos = r0     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.scanString()     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        L_0x0238:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.Number r14 = r15.integerValue()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x024a:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.fastjson.parser.Feature r16 = com.alibaba.fastjson.parser.Feature.UseBigDecimal     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 == 0) goto L_0x0270
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 1
            java.lang.Number r14 = r15.decimalValue(r16)     // Catch:{ all -> 0x0136 }
        L_0x0266:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x0270:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 0
            java.lang.Number r14 = r15.decimalValue(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x0266
        L_0x027b:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r12 = r15.stringVal()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.fastjson.parser.Feature r16 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 == 0) goto L_0x02b7
            com.alibaba.fastjson.parser.JSONLexer r9 = new com.alibaba.fastjson.parser.JSONLexer     // Catch:{ all -> 0x0136 }
            r9.<init>(r12)     // Catch:{ all -> 0x0136 }
            r15 = 1
            boolean r15 = r9.scanISO8601DateIfMatch(r15)     // Catch:{ all -> 0x0136 }
            if (r15 == 0) goto L_0x02b5
            java.util.Calendar r15 = r9.calendar     // Catch:{ all -> 0x0136 }
            java.util.Date r14 = r15.getTime()     // Catch:{ all -> 0x0136 }
        L_0x02b0:
            r9.close()     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02b5:
            r14 = r12
            goto L_0x02b0
        L_0x02b7:
            r14 = r12
            goto L_0x01f3
        L_0x02ba:
            java.lang.Boolean r14 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02c7:
            java.lang.Boolean r14 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02d4:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.fastjson.parser.Feature r16 = com.alibaba.fastjson.parser.Feature.OrderedField     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 == 0) goto L_0x02fc
            com.alibaba.fastjson.JSONObject r11 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0136 }
            java.util.LinkedHashMap r15 = new java.util.LinkedHashMap     // Catch:{ all -> 0x0136 }
            r15.<init>()     // Catch:{ all -> 0x0136 }
            r11.<init>(r15)     // Catch:{ all -> 0x0136 }
        L_0x02f0:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0136 }
            r0 = r18
            java.lang.Object r14 = r0.parseObject(r11, r15)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02fc:
            com.alibaba.fastjson.JSONObject r11 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0136 }
            r11.<init>()     // Catch:{ all -> 0x0136 }
            goto L_0x02f0
        L_0x0302:
            com.alibaba.fastjson.JSONArray r10 = new com.alibaba.fastjson.JSONArray     // Catch:{ all -> 0x0136 }
            r10.<init>()     // Catch:{ all -> 0x0136 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0136 }
            r0 = r18
            r0.parseArray(r10, r15)     // Catch:{ all -> 0x0136 }
            r14 = r10
            goto L_0x01f3
        L_0x0313:
            r14 = 0
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 4
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x031f:
            r14 = 0
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 4
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x032b:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            if (r5 != 0) goto L_0x0091
            r0 = r18
            r0.contex = r4
            goto L_0x0091
        L_0x033c:
            com.alibaba.fastjson.JSONException r15 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "unclosed jsonArray"
            r15.<init>(r16)     // Catch:{ all -> 0x0136 }
            throw r15     // Catch:{ all -> 0x0136 }
        L_0x0345:
            r15 = 48
            if (r3 < r15) goto L_0x036a
            r15 = 57
            if (r3 > r15) goto L_0x036a
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            int r0 = r0.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            r15.pos = r0     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.scanNumber()     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        L_0x036a:
            r15 = 123(0x7b, float:1.72E-43)
            if (r3 != r15) goto L_0x03a5
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 12
            r0 = r16
            r15.token = r0     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.len     // Catch:{ all -> 0x0136 }
            if (r8 < r15) goto L_0x039a
            r15 = 26
        L_0x0394:
            r0 = r16
            r0.ch = r15     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        L_0x039a:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r15 = r15.text     // Catch:{ all -> 0x0136 }
            char r15 = r15.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x0394
        L_0x03a5:
            r0 = r18
            com.alibaba.fastjson.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseArray(java.util.Collection, java.lang.Object):void");
    }

    /* access modifiers changed from: protected */
    public void addResolveTask(ResolveTask task) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(task);
    }

    /* access modifiers changed from: protected */
    public ResolveTask getLastResolveTask() {
        return (ResolveTask) this.resolveTaskList.get(this.resolveTaskList.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public void setContext(ParseContext context) {
        if (!this.lexer.disableCircularReferenceDetect) {
            this.contex = context;
        }
    }

    /* access modifiers changed from: protected */
    public void popContext() {
        this.contex = this.contex.parent;
        this.contextArray[this.contextArrayIndex - 1] = null;
        this.contextArrayIndex--;
    }

    /* access modifiers changed from: protected */
    public ParseContext setContext(ParseContext parent, Object object, Object fieldName) {
        if (this.lexer.disableCircularReferenceDetect) {
            return null;
        }
        this.contex = new ParseContext(parent, object, fieldName);
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        if (this.contextArray == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= this.contextArray.length) {
            ParseContext[] newArray = new ParseContext[((this.contextArray.length * 3) / 2)];
            System.arraycopy(this.contextArray, 0, newArray, 0, this.contextArray.length);
            this.contextArray = newArray;
        }
        this.contextArray[i] = this.contex;
        return this.contex;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parse(Object fieldName) {
        switch (this.lexer.token) {
            case 2:
                Number intValue = this.lexer.integerValue();
                this.lexer.nextToken();
                return intValue;
            case 3:
                Number value = this.lexer.decimalValue((this.lexer.features & Feature.UseBigDecimal.mask) != 0);
                this.lexer.nextToken();
                return value;
            case 4:
                String stringLiteral = this.lexer.stringVal();
                this.lexer.nextToken(16);
                if ((this.lexer.features & Feature.AllowISO8601DateFormat.mask) != 0) {
                    JSONLexer iso8601Lexer = new JSONLexer(stringLiteral);
                    try {
                        if (iso8601Lexer.scanISO8601DateIfMatch(true)) {
                            return iso8601Lexer.calendar.getTime();
                        }
                        iso8601Lexer.close();
                    } finally {
                        iso8601Lexer.close();
                    }
                }
                return stringLiteral;
            case 6:
                this.lexer.nextToken(16);
                return Boolean.TRUE;
            case 7:
                this.lexer.nextToken(16);
                return Boolean.FALSE;
            case 8:
            case 23:
                this.lexer.nextToken();
                return null;
            case 9:
                this.lexer.nextToken(18);
                if (this.lexer.token != 18) {
                    throw new JSONException("syntax error, " + this.lexer.info());
                }
                this.lexer.nextToken(10);
                accept(10);
                long time = this.lexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(time);
            case 12:
                return parseObject((Map) (this.lexer.features & Feature.OrderedField.mask) != 0 ? new JSONObject((Map<String, Object>) new LinkedHashMap<String,Object>()) : new JSONObject(), fieldName);
            case 14:
                JSONArray array = new JSONArray();
                parseArray((Collection) array, fieldName);
                return array;
            case 20:
                if (this.lexer.isBlankInput()) {
                    return null;
                }
                throw new JSONException("syntax error, " + this.lexer.info());
            case 21:
                this.lexer.nextToken();
                HashSet<Object> set = new HashSet<>();
                parseArray((Collection) set, fieldName);
                return set;
            case 22:
                this.lexer.nextToken();
                TreeSet<Object> treeSet = new TreeSet<>();
                parseArray((Collection) treeSet, fieldName);
                return treeSet;
            default:
                throw new JSONException("syntax error, " + this.lexer.info());
        }
    }

    public void config(Feature feature, boolean state) {
        this.lexer.config(feature, state);
    }

    public final void accept(int token) {
        if (this.lexer.token == token) {
            this.lexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(this.lexer.token));
    }

    public void close() {
        try {
            if (this.lexer.token != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(this.lexer.token));
            }
        } finally {
            this.lexer.close();
        }
    }

    public void handleResovleTask(Object value) {
        if (this.resolveTaskList != null) {
            int size = this.resolveTaskList.size();
            for (int i = 0; i < size; i++) {
                ResolveTask task = (ResolveTask) this.resolveTaskList.get(i);
                FieldDeserializer fieldDeser = task.fieldDeserializer;
                if (fieldDeser != null) {
                    Object object = null;
                    if (task.ownerContext != null) {
                        object = task.ownerContext.object;
                    }
                    String ref = task.referenceValue;
                    Object refValue = null;
                    if (ref.startsWith("$")) {
                        for (int j = 0; j < this.contextArrayIndex; j++) {
                            if (ref.equals(this.contextArray[j].toString())) {
                                refValue = this.contextArray[j].object;
                            }
                        }
                    } else {
                        refValue = task.context.object;
                    }
                    fieldDeser.setValue(object, refValue);
                }
            }
        }
    }

    public String parseString() {
        char c = 26;
        int token = this.lexer.token;
        if (token == 4) {
            String val = this.lexer.stringVal();
            if (this.lexer.ch == ',') {
                JSONLexer jSONLexer = this.lexer;
                int index = jSONLexer.bp + 1;
                jSONLexer.bp = index;
                JSONLexer jSONLexer2 = this.lexer;
                if (index < this.lexer.len) {
                    c = this.lexer.text.charAt(index);
                }
                jSONLexer2.ch = c;
                this.lexer.token = 16;
                return val;
            } else if (this.lexer.ch == ']') {
                JSONLexer jSONLexer3 = this.lexer;
                int index2 = jSONLexer3.bp + 1;
                jSONLexer3.bp = index2;
                JSONLexer jSONLexer4 = this.lexer;
                if (index2 < this.lexer.len) {
                    c = this.lexer.text.charAt(index2);
                }
                jSONLexer4.ch = c;
                this.lexer.token = 15;
                return val;
            } else if (this.lexer.ch == '}') {
                JSONLexer jSONLexer5 = this.lexer;
                int index3 = jSONLexer5.bp + 1;
                jSONLexer5.bp = index3;
                JSONLexer jSONLexer6 = this.lexer;
                if (index3 < this.lexer.len) {
                    c = this.lexer.text.charAt(index3);
                }
                jSONLexer6.ch = c;
                this.lexer.token = 13;
                return val;
            } else {
                this.lexer.nextToken();
                return val;
            }
        } else if (token == 2) {
            String val2 = this.lexer.numberString();
            this.lexer.nextToken(16);
            return val2;
        } else {
            Object value = parse();
            if (value == null) {
                return null;
            }
            return value.toString();
        }
    }
}
