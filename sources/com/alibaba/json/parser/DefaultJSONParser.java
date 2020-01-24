package com.alibaba.json.parser;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONArray;
import com.alibaba.json.JSONException;
import com.alibaba.json.JSONObject;
import com.alibaba.json.parser.deserializer.ExtraProcessor;
import com.alibaba.json.parser.deserializer.ExtraTypeProvider;
import com.alibaba.json.parser.deserializer.FieldDeserializer;
import com.alibaba.json.parser.deserializer.FieldTypeResolver;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import com.alibaba.json.serializer.IntegerCodec;
import com.alibaba.json.serializer.StringCodec;
import com.alibaba.json.util.TypeUtils;
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

    /* JADX WARNING: type inference failed for: r41v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r46v3 */
    /* JADX WARNING: type inference failed for: r46v4 */
    /* JADX WARNING: type inference failed for: r2v16, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v17, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v75, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r46v5 */
    /* JADX WARNING: type inference failed for: r46v6, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r46v7 */
    /* JADX WARNING: type inference failed for: r46v8 */
    /* JADX INFO: used method not loaded: com.alibaba.json.parser.JavaBeanDeserializer.createInstance(com.alibaba.json.parser.DefaultJSONParser, java.lang.reflect.Type):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.alibaba.json.parser.deserializer.FieldDeserializer.setValue(java.lang.Object, java.lang.Object):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.alibaba.json.util.TypeUtils.cast(java.lang.Object, java.lang.Class, com.alibaba.json.parser.ParserConfig):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x03b3, code lost:
        r29.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x03c8, code lost:
        if (r29.token != 13) goto L_0x0469;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x03ca, code lost:
        r29.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x03d3, code lost:
        r22 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:?, code lost:
        r10 = r51.config.getDeserializer(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x03e5, code lost:
        if ((r10 instanceof com.alibaba.json.parser.JavaBeanDeserializer) == false) goto L_0x043b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x03e7, code lost:
        r26 = (com.alibaba.json.parser.JavaBeanDeserializer) r10;
        r22 = r26.createInstance(r51, (java.lang.reflect.Type) r6);
        r47 = r52.entrySet().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0400, code lost:
        if (r47.hasNext() == false) goto L_0x043b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0402, code lost:
        r13 = (java.util.Map.Entry) r47.next();
        r14 = r13.getKey();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0413, code lost:
        if ((r14 instanceof java.lang.String) == false) goto L_0x03fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0415, code lost:
        r17 = r26.getFieldDeserializer((java.lang.String) r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x041d, code lost:
        if (r17 == null) goto L_0x03fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x041f, code lost:
        r17.setValue(r22, r13.getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x043b, code lost:
        if (r22 != null) goto L_0x0448;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0441, code lost:
        if (r6 != java.lang.Cloneable.class) goto L_0x0452;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:?, code lost:
        r22 = new java.util.HashMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0448, code lost:
        if (r11 != false) goto L_0x044e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x044a, code lost:
        r51.contex = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x045d, code lost:
        if ("java.util.Collections$EmptyMap".equals(r45) == false) goto L_0x0464;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x045f, code lost:
        r22 = java.util.Collections.emptyMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0464, code lost:
        r22 = r6.newInstance();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:?, code lost:
        r51.resolveStatus = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0477, code lost:
        if (r51.contex == null) goto L_0x0484;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x047f, code lost:
        if ((r53 instanceof java.lang.Integer) != false) goto L_0x0484;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0481, code lost:
        popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0488, code lost:
        if (r52.size() <= 0) goto L_0x04a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x048a, code lost:
        r31 = com.alibaba.json.util.TypeUtils.cast((java.lang.Object) r52, (java.lang.Class) r6, r51.config);
        parseObject(r31);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x049f, code lost:
        if (r11 != false) goto L_0x04a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x04a1, code lost:
        r51.contex = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:?, code lost:
        r52 = r51.config.getDeserializer(r6).deserialze(r51, r6, r53);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x04bd, code lost:
        if (r11 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x04bf, code lost:
        r51.contex = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x04dc, code lost:
        r29.nextToken(4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x04f1, code lost:
        if (r29.token != 4) goto L_0x061d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x04f3, code lost:
        r36 = r29.stringVal();
        r29.nextToken(13);
        r37 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x050d, code lost:
        if ("@".equals(r36) == false) goto L_0x0570;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x050f, code lost:
        r42 = r51.contex;
        r43 = r42.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x0521, code lost:
        if ((r43 instanceof java.lang.Object[]) != false) goto L_0x052b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x0529, code lost:
        if ((r43 instanceof java.util.Collection) == false) goto L_0x055b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x052b, code lost:
        r37 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x052d, code lost:
        r52 = r37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x053b, code lost:
        if (r29.token == 13) goto L_0x060c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x055a, code lost:
        throw new com.alibaba.json.JSONException("syntax error, " + r29.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x0561, code lost:
        if (r42.parent == null) goto L_0x052d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x0563, code lost:
        r37 = r42.parent.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x057b, code lost:
        if ("..".equals(r36) == false) goto L_0x05a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x0581, code lost:
        if (r7.object == null) goto L_0x058a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x0583, code lost:
        r52 = r7.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x058a, code lost:
        r0 = new com.alibaba.json.parser.DefaultJSONParser.ResolveTask(r7, r36);
        addResolveTask(r0);
        r51.resolveStatus = 1;
        r52 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x05b0, code lost:
        if ("$".equals(r36) == false) goto L_0x05f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x05b2, code lost:
        r39 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x05ba, code lost:
        if (r39.parent == null) goto L_0x05c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x05bc, code lost:
        r39 = r39.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x05c9, code lost:
        if (r39.object == null) goto L_0x05d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x05cb, code lost:
        r37 = r39.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x05d1, code lost:
        r52 = r37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x05d5, code lost:
        r0 = new com.alibaba.json.parser.DefaultJSONParser.ResolveTask(r39, r36);
        addResolveTask(r0);
        r51.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x05f0, code lost:
        r0 = new com.alibaba.json.parser.DefaultJSONParser.ResolveTask(r7, r36);
        addResolveTask(r0);
        r51.resolveStatus = 1;
        r52 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x060c, code lost:
        r29.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x0615, code lost:
        if (r11 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x0617, code lost:
        r51.contex = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0640, code lost:
        throw new com.alibaba.json.JSONException("illegal ref, " + com.alibaba.json.parser.JSONToken.name(r29.token));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:374:0x0a21, code lost:
        if (r5 != '}') goto L_0x0b3a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:375:0x0a23, code lost:
        r19 = r29.bp + 1;
        r29.bp = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:376:0x0a3b, code lost:
        if (r19 < r29.len) goto L_0x0a96;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:377:0x0a3d, code lost:
        r5 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:378:0x0a3f, code lost:
        r29.ch = r5;
        r29.sp = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:379:0x0a4f, code lost:
        if (r5 != ',') goto L_0x0ab4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:0x0a51, code lost:
        r19 = r29.bp + 1;
        r29.bp = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:381:0x0a69, code lost:
        if (r19 < r29.len) goto L_0x0aa5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:382:0x0a6b, code lost:
        r47 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:383:0x0a6d, code lost:
        r29.ch = r47;
        r29.token = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:384:0x0a7b, code lost:
        if (r11 != false) goto L_0x0a8e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:385:0x0a7d, code lost:
        setContext(r51.contex, r52, r53);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:386:0x0a8e, code lost:
        if (r11 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:387:0x0a90, code lost:
        r51.contex = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:390:?, code lost:
        r5 = r29.text.charAt(r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:391:0x0aa5, code lost:
        r47 = r29.text.charAt(r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:393:0x0ab8, code lost:
        if (r5 != '}') goto L_0x0af4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:394:0x0aba, code lost:
        r19 = r29.bp + 1;
        r29.bp = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:395:0x0ad2, code lost:
        if (r19 < r29.len) goto L_0x0ae5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:396:0x0ad4, code lost:
        r47 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:397:0x0ad6, code lost:
        r29.ch = r47;
        r29.token = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:398:0x0ae5, code lost:
        r47 = r29.text.charAt(r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:400:0x0af8, code lost:
        if (r5 != ']') goto L_0x0b35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:401:0x0afa, code lost:
        r19 = r29.bp + 1;
        r29.bp = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:402:0x0b12, code lost:
        if (r19 < r29.len) goto L_0x0b26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:403:0x0b14, code lost:
        r47 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:404:0x0b16, code lost:
        r29.ch = r47;
        r29.token = 15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:405:0x0b26, code lost:
        r47 = r29.text.charAt(r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:406:0x0b35, code lost:
        r29.nextToken();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:408:0x0b57, code lost:
        throw new com.alibaba.json.JSONException("syntax error, " + r29.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r19 = r29.bp + 1;
        r29.bp = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:450:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:451:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:452:?, code lost:
        return r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:453:?, code lost:
        return r31;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:454:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:455:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:456:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:457:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x013b, code lost:
        if (r19 < r29.len) goto L_0x015e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:464:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:465:?, code lost:
        return r52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x013d, code lost:
        r47 = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x013f, code lost:
        r29.ch = r47;
        r29.sp = 0;
        r29.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0156, code lost:
        if (r11 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0158, code lost:
        r51.contex = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r47 = r29.text.charAt(r19);
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r46v5
      assigns: []
      uses: []
      mth insns count: 1103
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
    public final java.lang.Object parseObject(java.util.Map r52, java.lang.Object r53) {
        /*
            r51 = this;
            r0 = r51
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer
            r29 = r0
            r0 = r29
            int r0 = r0.token
            r44 = r0
            r47 = 8
            r0 = r44
            r1 = r47
            if (r0 != r1) goto L_0x001a
            r29.nextToken()
            r52 = 0
        L_0x0019:
            return r52
        L_0x001a:
            r47 = 12
            r0 = r44
            r1 = r47
            if (r0 == r1) goto L_0x0057
            r47 = 16
            r0 = r44
            r1 = r47
            if (r0 == r1) goto L_0x0057
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r48 = new java.lang.StringBuilder
            r48.<init>()
            java.lang.String r49 = "syntax error, expect {, actual "
            java.lang.StringBuilder r48 = r48.append(r49)
            java.lang.String r49 = com.alibaba.json.parser.JSONToken.name(r44)
            java.lang.StringBuilder r48 = r48.append(r49)
            java.lang.String r49 = ", "
            java.lang.StringBuilder r48 = r48.append(r49)
            java.lang.String r49 = r29.info()
            java.lang.StringBuilder r48 = r48.append(r49)
            java.lang.String r48 = r48.toString()
            r47.<init>(r48)
            throw r47
        L_0x0057:
            r0 = r52
            boolean r0 = r0 instanceof com.alibaba.json.JSONObject
            r47 = r0
            if (r47 == 0) goto L_0x00ae
            r27 = r52
            com.alibaba.json.JSONObject r27 = (com.alibaba.json.JSONObject) r27
            java.util.Map r20 = r27.getInnerMap()
            r23 = 1
        L_0x0069:
            r0 = r29
            int r0 = r0.features
            r47 = r0
            com.alibaba.json.parser.Feature r48 = com.alibaba.json.parser.Feature.AllowISO8601DateFormat
            r0 = r48
            int r0 = r0.mask
            r48 = r0
            r47 = r47 & r48
            if (r47 == 0) goto L_0x00b3
            r4 = 1
        L_0x007c:
            r0 = r29
            boolean r11 = r0.disableCircularReferenceDetect
            r0 = r51
            com.alibaba.json.parser.ParseContext r7 = r0.contex
            r40 = 0
        L_0x0086:
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 34
            r0 = r47
            if (r5 == r0) goto L_0x009d
            r47 = 125(0x7d, float:1.75E-43)
            r0 = r47
            if (r5 == r0) goto L_0x009d
            r29.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
        L_0x009d:
            r47 = 44
            r0 = r47
            if (r5 != r0) goto L_0x00b5
            r29.next()     // Catch:{ all -> 0x0115 }
            r29.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            goto L_0x009d
        L_0x00ae:
            r23 = 0
            r20 = r52
            goto L_0x0069
        L_0x00b3:
            r4 = 0
            goto L_0x007c
        L_0x00b5:
            r24 = 0
            r47 = 34
            r0 = r47
            if (r5 != r0) goto L_0x011d
            r0 = r51
            com.alibaba.json.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 34
            r0 = r29
            r1 = r47
            r2 = r48
            java.lang.String r28 = r0.scanSymbol(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 58
            r0 = r47
            if (r5 == r0) goto L_0x0b58
            r29.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 58
            r0 = r47
            if (r5 == r0) goto L_0x0b58
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "expect ':' at "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.pos     // Catch:{ all -> 0x0115 }
            r49 = r0
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = ", name "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            r0 = r48
            r1 = r28
            java.lang.StringBuilder r48 = r0.append(r1)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0115:
            r47 = move-exception
            if (r11 != 0) goto L_0x011c
            r0 = r51
            r0.contex = r7
        L_0x011c:
            throw r47
        L_0x011d:
            r47 = 125(0x7d, float:1.75E-43)
            r0 = r47
            if (r5 != r0) goto L_0x016d
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r47 = r0
            int r19 = r47 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r19
            r1 = r47
            if (r0 < r1) goto L_0x015e
            r47 = 26
        L_0x013f:
            r0 = r47
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r47 = 0
            r0 = r47
            r1 = r29
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x015e:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            r1 = r19
            char r47 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x013f
        L_0x016d:
            r47 = 39
            r0 = r47
            if (r5 != r0) goto L_0x01c0
            r0 = r51
            com.alibaba.json.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 39
            r0 = r29
            r1 = r47
            r2 = r48
            java.lang.String r28 = r0.scanSymbol(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r0 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 58
            r0 = r47
            r1 = r48
            if (r0 == r1) goto L_0x0196
            r29.skipWhitespace()     // Catch:{ all -> 0x0115 }
        L_0x0196:
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 58
            r0 = r47
            if (r5 == r0) goto L_0x0b58
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "expect ':' at "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.pos     // Catch:{ all -> 0x0115 }
            r49 = r0
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x01c0:
            r47 = 26
            r0 = r47
            if (r5 != r0) goto L_0x01e4
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x01e4:
            r47 = 44
            r0 = r47
            if (r5 != r0) goto L_0x0208
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0208:
            r47 = 48
            r0 = r47
            if (r5 < r0) goto L_0x0214
            r47 = 57
            r0 = r47
            if (r5 <= r0) goto L_0x021a
        L_0x0214:
            r47 = 45
            r0 = r47
            if (r5 != r0) goto L_0x028f
        L_0x021a:
            r47 = 0
            r0 = r47
            r1 = r29
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            r29.scanNumber()     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.token     // Catch:{ NumberFormatException -> 0x0270 }
            r47 = r0
            r48 = 2
            r0 = r47
            r1 = r48
            if (r0 != r1) goto L_0x0265
            java.lang.Number r28 = r29.integerValue()     // Catch:{ NumberFormatException -> 0x0270 }
        L_0x0237:
            if (r23 == 0) goto L_0x023d
            java.lang.String r28 = r28.toString()     // Catch:{ NumberFormatException -> 0x0270 }
        L_0x023d:
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 58
            r0 = r47
            if (r5 == r0) goto L_0x0b5c
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "parse number key error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0265:
            r47 = 1
            r0 = r29
            r1 = r47
            java.lang.Number r28 = r0.decimalValue(r1)     // Catch:{ NumberFormatException -> 0x0270 }
            goto L_0x0237
        L_0x0270:
            r15 = move-exception
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "parse number key error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x028f:
            r47 = 123(0x7b, float:1.72E-43)
            r0 = r47
            if (r5 == r0) goto L_0x029b
            r47 = 91
            r0 = r47
            if (r5 != r0) goto L_0x02fa
        L_0x029b:
            r29.nextToken()     // Catch:{ all -> 0x0115 }
            java.lang.Object r28 = r51.parse()     // Catch:{ all -> 0x0115 }
            r24 = 1
            r47 = r28
        L_0x02a6:
            if (r24 != 0) goto L_0x035c
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r48 = r0
            int r19 = r48 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r19
            r1 = r48
            if (r0 < r1) goto L_0x034c
            r5 = 26
        L_0x02c4:
            r0 = r29
            r0.ch = r5     // Catch:{ all -> 0x0115 }
        L_0x02c8:
            r48 = 32
            r0 = r48
            if (r5 > r0) goto L_0x0360
            r48 = 32
            r0 = r48
            if (r5 == r0) goto L_0x02f2
            r48 = 10
            r0 = r48
            if (r5 == r0) goto L_0x02f2
            r48 = 13
            r0 = r48
            if (r5 == r0) goto L_0x02f2
            r48 = 9
            r0 = r48
            if (r5 == r0) goto L_0x02f2
            r48 = 12
            r0 = r48
            if (r5 == r0) goto L_0x02f2
            r48 = 8
            r0 = r48
            if (r5 != r0) goto L_0x0360
        L_0x02f2:
            r29.next()     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            goto L_0x02c8
        L_0x02fa:
            r0 = r51
            com.alibaba.json.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r29
            r1 = r47
            java.lang.String r28 = r0.scanSymbolUnQuoted(r1)     // Catch:{ all -> 0x0115 }
            r29.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 58
            r0 = r47
            if (r5 == r0) goto L_0x0342
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "expect ':' at "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r49 = r0
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = ", actual "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            r0 = r48
            java.lang.StringBuilder r48 = r0.append(r5)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0342:
            if (r23 == 0) goto L_0x0b58
            java.lang.String r28 = r28.toString()     // Catch:{ all -> 0x0115 }
            r47 = r28
            goto L_0x02a6
        L_0x034c:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r48
            r1 = r19
            char r5 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x02c4
        L_0x035c:
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
        L_0x0360:
            r48 = 0
            r0 = r48
            r1 = r29
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = "@type"
            r0 = r47
            r1 = r48
            if (r0 != r1) goto L_0x04c5
            com.alibaba.json.parser.Feature r48 = com.alibaba.json.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0115 }
            r0 = r29
            r1 = r48
            boolean r48 = r0.isEnabled(r1)     // Catch:{ all -> 0x0115 }
            if (r48 != 0) goto L_0x04c5
            r0 = r51
            com.alibaba.json.parser.SymbolTable r0 = r0.symbolTable     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 34
            r0 = r29
            r1 = r47
            r2 = r48
            java.lang.String r45 = r0.scanSymbol(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r51
            com.alibaba.json.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            java.lang.ClassLoader r0 = r0.defaultClassLoader     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r45
            r1 = r47
            java.lang.Class r6 = com.alibaba.json.util.TypeUtils.loadClass(r0, r1)     // Catch:{ all -> 0x0115 }
            if (r6 != 0) goto L_0x03b3
            java.lang.String r47 = "@type"
            r0 = r52
            r1 = r47
            r2 = r45
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x0086
        L_0x03b3:
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 13
            r0 = r47
            r1 = r48
            if (r0 != r1) goto L_0x0469
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r22 = 0
            r0 = r51
            com.alibaba.json.parser.ParserConfig r0 = r0.config     // Catch:{ Exception -> 0x042d }
            r47 = r0
            r0 = r47
            com.alibaba.json.parser.deserializer.ObjectDeserializer r10 = r0.getDeserializer(r6)     // Catch:{ Exception -> 0x042d }
            boolean r0 = r10 instanceof com.alibaba.json.parser.JavaBeanDeserializer     // Catch:{ Exception -> 0x042d }
            r47 = r0
            if (r47 == 0) goto L_0x043b
            r0 = r10
            com.alibaba.json.parser.JavaBeanDeserializer r0 = (com.alibaba.json.parser.JavaBeanDeserializer) r0     // Catch:{ Exception -> 0x042d }
            r26 = r0
            r0 = r26
            r1 = r51
            java.lang.Object r22 = r0.createInstance(r1, r6)     // Catch:{ Exception -> 0x042d }
            java.util.Set r47 = r52.entrySet()     // Catch:{ Exception -> 0x042d }
            java.util.Iterator r47 = r47.iterator()     // Catch:{ Exception -> 0x042d }
        L_0x03fc:
            boolean r48 = r47.hasNext()     // Catch:{ Exception -> 0x042d }
            if (r48 == 0) goto L_0x043b
            java.lang.Object r32 = r47.next()     // Catch:{ Exception -> 0x042d }
            r0 = r32
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Exception -> 0x042d }
            r13 = r0
            java.lang.Object r14 = r13.getKey()     // Catch:{ Exception -> 0x042d }
            boolean r0 = r14 instanceof java.lang.String     // Catch:{ Exception -> 0x042d }
            r48 = r0
            if (r48 == 0) goto L_0x03fc
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ Exception -> 0x042d }
            r0 = r26
            com.alibaba.json.parser.deserializer.FieldDeserializer r17 = r0.getFieldDeserializer(r14)     // Catch:{ Exception -> 0x042d }
            if (r17 == 0) goto L_0x03fc
            java.lang.Object r48 = r13.getValue()     // Catch:{ Exception -> 0x042d }
            r0 = r17
            r1 = r22
            r2 = r48
            r0.setValue(r1, r2)     // Catch:{ Exception -> 0x042d }
            goto L_0x03fc
        L_0x042d:
            r12 = move-exception
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = "create instance error"
            r0 = r47
            r1 = r48
            r0.<init>(r1, r12)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x043b:
            if (r22 != 0) goto L_0x0448
            java.lang.Class<java.lang.Cloneable> r47 = java.lang.Cloneable.class
            r0 = r47
            if (r6 != r0) goto L_0x0452
            java.util.HashMap r22 = new java.util.HashMap     // Catch:{ Exception -> 0x042d }
            r22.<init>()     // Catch:{ Exception -> 0x042d }
        L_0x0448:
            if (r11 != 0) goto L_0x044e
            r0 = r51
            r0.contex = r7
        L_0x044e:
            r52 = r22
            goto L_0x0019
        L_0x0452:
            java.lang.String r47 = "java.util.Collections$EmptyMap"
            r0 = r47
            r1 = r45
            boolean r47 = r0.equals(r1)     // Catch:{ Exception -> 0x042d }
            if (r47 == 0) goto L_0x0464
            java.util.Map r22 = java.util.Collections.emptyMap()     // Catch:{ Exception -> 0x042d }
            goto L_0x0448
        L_0x0464:
            java.lang.Object r22 = r6.newInstance()     // Catch:{ Exception -> 0x042d }
            goto L_0x0448
        L_0x0469:
            r47 = 2
            r0 = r47
            r1 = r51
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            r0 = r51
            com.alibaba.json.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 == 0) goto L_0x0484
            r0 = r53
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 != 0) goto L_0x0484
            r51.popContext()     // Catch:{ all -> 0x0115 }
        L_0x0484:
            int r47 = r52.size()     // Catch:{ all -> 0x0115 }
            if (r47 <= 0) goto L_0x04a9
            r0 = r51
            com.alibaba.json.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r52
            r1 = r47
            java.lang.Object r31 = com.alibaba.json.util.TypeUtils.cast(r0, r6, r1)     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r31
            r0.parseObject(r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x04a5
            r0 = r51
            r0.contex = r7
        L_0x04a5:
            r52 = r31
            goto L_0x0019
        L_0x04a9:
            r0 = r51
            com.alibaba.json.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            com.alibaba.json.parser.deserializer.ObjectDeserializer r10 = r0.getDeserializer(r6)     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r53
            java.lang.Object r52 = r10.deserialze(r0, r6, r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x04c5:
            java.lang.String r48 = "$ref"
            r0 = r47
            r1 = r48
            if (r0 != r1) goto L_0x0641
            if (r7 == 0) goto L_0x0641
            com.alibaba.json.parser.Feature r48 = com.alibaba.json.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0115 }
            r0 = r29
            r1 = r48
            boolean r48 = r0.isEnabled(r1)     // Catch:{ all -> 0x0115 }
            if (r48 != 0) goto L_0x0641
            r47 = 4
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 4
            r0 = r47
            r1 = r48
            if (r0 != r1) goto L_0x061d
            java.lang.String r36 = r29.stringVal()     // Catch:{ all -> 0x0115 }
            r47 = 13
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            r37 = 0
            java.lang.String r47 = "@"
            r0 = r47
            r1 = r36
            boolean r47 = r0.equals(r1)     // Catch:{ all -> 0x0115 }
            if (r47 == 0) goto L_0x0570
            r0 = r51
            com.alibaba.json.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r42 = r0
            r0 = r42
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r43 = r0
            r0 = r43
            boolean r0 = r0 instanceof java.lang.Object[]     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 != 0) goto L_0x052b
            r0 = r43
            boolean r0 = r0 instanceof java.util.Collection     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 == 0) goto L_0x055b
        L_0x052b:
            r37 = r43
        L_0x052d:
            r52 = r37
        L_0x052f:
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 13
            r0 = r47
            r1 = r48
            if (r0 == r1) goto L_0x060c
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x055b:
            r0 = r42
            com.alibaba.json.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 == 0) goto L_0x052d
            r0 = r42
            com.alibaba.json.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r37 = r0
            goto L_0x052d
        L_0x0570:
            java.lang.String r47 = ".."
            r0 = r47
            r1 = r36
            boolean r47 = r0.equals(r1)     // Catch:{ all -> 0x0115 }
            if (r47 == 0) goto L_0x05a5
            java.lang.Object r0 = r7.object     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 == 0) goto L_0x058a
            java.lang.Object r0 = r7.object     // Catch:{ all -> 0x0115 }
            r37 = r0
            r52 = r37
            goto L_0x052f
        L_0x058a:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r47 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0115 }
            r0 = r47
            r1 = r36
            r0.<init>(r7, r1)     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r47
            r0.addResolveTask(r1)     // Catch:{ all -> 0x0115 }
            r47 = 1
            r0 = r47
            r1 = r51
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            r52 = r37
            goto L_0x052f
        L_0x05a5:
            java.lang.String r47 = "$"
            r0 = r47
            r1 = r36
            boolean r47 = r0.equals(r1)     // Catch:{ all -> 0x0115 }
            if (r47 == 0) goto L_0x05f0
            r39 = r7
        L_0x05b4:
            r0 = r39
            com.alibaba.json.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 == 0) goto L_0x05c3
            r0 = r39
            com.alibaba.json.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x0115 }
            r39 = r0
            goto L_0x05b4
        L_0x05c3:
            r0 = r39
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r47 = r0
            if (r47 == 0) goto L_0x05d5
            r0 = r39
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x0115 }
            r37 = r0
        L_0x05d1:
            r52 = r37
            goto L_0x052f
        L_0x05d5:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r47 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0115 }
            r0 = r47
            r1 = r39
            r2 = r36
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r47
            r0.addResolveTask(r1)     // Catch:{ all -> 0x0115 }
            r47 = 1
            r0 = r47
            r1 = r51
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            goto L_0x05d1
        L_0x05f0:
            com.alibaba.json.parser.DefaultJSONParser$ResolveTask r47 = new com.alibaba.json.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0115 }
            r0 = r47
            r1 = r36
            r0.<init>(r7, r1)     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r47
            r0.addResolveTask(r1)     // Catch:{ all -> 0x0115 }
            r47 = 1
            r0 = r47
            r1 = r51
            r1.resolveStatus = r0     // Catch:{ all -> 0x0115 }
            r52 = r37
            goto L_0x052f
        L_0x060c:
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x061d:
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "illegal ref, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r49 = r0
            java.lang.String r49 = com.alibaba.json.parser.JSONToken.name(r49)     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0641:
            if (r11 != 0) goto L_0x065c
            if (r40 != 0) goto L_0x065c
            r0 = r51
            com.alibaba.json.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r51
            r1 = r48
            r2 = r52
            r3 = r53
            com.alibaba.json.parser.ParseContext r8 = r0.setContext(r1, r2, r3)     // Catch:{ all -> 0x0115 }
            if (r7 != 0) goto L_0x065a
            r7 = r8
        L_0x065a:
            r40 = 1
        L_0x065c:
            r48 = 34
            r0 = r48
            if (r5 != r0) goto L_0x06e8
            r48 = 34
            r0 = r29
            r1 = r48
            java.lang.String r41 = r0.scanStringValue(r1)     // Catch:{ all -> 0x0115 }
            r46 = r41
            if (r4 == 0) goto L_0x0692
            com.alibaba.json.parser.JSONLexer r25 = new com.alibaba.json.parser.JSONLexer     // Catch:{ all -> 0x0115 }
            r0 = r25
            r1 = r41
            r0.<init>(r1)     // Catch:{ all -> 0x0115 }
            r48 = 1
            r0 = r25
            r1 = r48
            boolean r48 = r0.scanISO8601DateIfMatch(r1)     // Catch:{ all -> 0x0115 }
            if (r48 == 0) goto L_0x068f
            r0 = r25
            java.util.Calendar r0 = r0.calendar     // Catch:{ all -> 0x0115 }
            r48 = r0
            java.util.Date r46 = r48.getTime()     // Catch:{ all -> 0x0115 }
        L_0x068f:
            r25.close()     // Catch:{ all -> 0x0115 }
        L_0x0692:
            if (r20 == 0) goto L_0x06de
            r0 = r20
            r1 = r47
            r2 = r46
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x069d:
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
            r47 = 44
            r0 = r47
            if (r5 == r0) goto L_0x06b4
            r47 = 125(0x7d, float:1.75E-43)
            r0 = r47
            if (r5 == r0) goto L_0x06b4
            r29.skipWhitespace()     // Catch:{ all -> 0x0115 }
            r0 = r29
            char r5 = r0.ch     // Catch:{ all -> 0x0115 }
        L_0x06b4:
            r47 = 44
            r0 = r47
            if (r5 != r0) goto L_0x0a1d
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r47 = r0
            int r19 = r47 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r19
            r1 = r47
            if (r0 < r1) goto L_0x0a0d
            r47 = 26
        L_0x06d6:
            r0 = r47
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0086
        L_0x06de:
            r0 = r52
            r1 = r47
            r2 = r46
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x069d
        L_0x06e8:
            r48 = 48
            r0 = r48
            if (r5 < r0) goto L_0x06f4
            r48 = 57
            r0 = r48
            if (r5 <= r0) goto L_0x06fa
        L_0x06f4:
            r48 = 45
            r0 = r48
            if (r5 != r0) goto L_0x0708
        L_0x06fa:
            java.lang.Number r46 = r29.scanNumberValue()     // Catch:{ all -> 0x0115 }
            r0 = r20
            r1 = r47
            r2 = r46
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x069d
        L_0x0708:
            r48 = 91
            r0 = r48
            if (r5 != r0) goto L_0x07d2
            r48 = 14
            r0 = r48
            r1 = r29
            r1.token = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r48 = r0
            int r19 = r48 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r19
            r1 = r48
            if (r0 < r1) goto L_0x0790
            r48 = 26
        L_0x0732:
            r0 = r48
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            java.util.ArrayList r30 = new java.util.ArrayList     // Catch:{ all -> 0x0115 }
            r30.<init>()     // Catch:{ all -> 0x0115 }
            if (r53 == 0) goto L_0x079f
            java.lang.Class r48 = r53.getClass()     // Catch:{ all -> 0x0115 }
            java.lang.Class<java.lang.Integer> r49 = java.lang.Integer.class
            r0 = r48
            r1 = r49
            if (r0 != r1) goto L_0x079f
            r35 = 1
        L_0x074d:
            if (r35 != 0) goto L_0x0754
            r0 = r51
            r0.setContext(r7)     // Catch:{ all -> 0x0115 }
        L_0x0754:
            r0 = r51
            r1 = r30
            r2 = r47
            r0.parseArray(r1, r2)     // Catch:{ all -> 0x0115 }
            com.alibaba.json.JSONArray r46 = new com.alibaba.json.JSONArray     // Catch:{ all -> 0x0115 }
            r0 = r46
            r1 = r30
            r0.<init>(r1)     // Catch:{ all -> 0x0115 }
            if (r20 == 0) goto L_0x07a2
            r0 = r20
            r1 = r47
            r2 = r46
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x0771:
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r44 = r0
            r47 = 13
            r0 = r44
            r1 = r47
            if (r0 != r1) goto L_0x07ac
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x0790:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r48
            r1 = r19
            char r48 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0732
        L_0x079f:
            r35 = 0
            goto L_0x074d
        L_0x07a2:
            r0 = r52
            r1 = r47
            r2 = r46
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x0771
        L_0x07ac:
            r47 = 16
            r0 = r44
            r1 = r47
            if (r0 == r1) goto L_0x0086
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x07d2:
            r48 = 123(0x7b, float:1.72E-43)
            r0 = r48
            if (r5 != r0) goto L_0x0930
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r48 = r0
            int r19 = r48 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r19
            r1 = r48
            if (r0 < r1) goto L_0x08e5
            r48 = 26
        L_0x07f4:
            r0 = r48
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r48 = 12
            r0 = r48
            r1 = r29
            r1.token = r0     // Catch:{ all -> 0x0115 }
            r0 = r53
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ all -> 0x0115 }
            r35 = r0
            r0 = r29
            int r0 = r0.features     // Catch:{ all -> 0x0115 }
            r48 = r0
            com.alibaba.json.parser.Feature r49 = com.alibaba.json.parser.Feature.OrderedField     // Catch:{ all -> 0x0115 }
            r0 = r49
            int r0 = r0.mask     // Catch:{ all -> 0x0115 }
            r49 = r0
            r48 = r48 & r49
            if (r48 == 0) goto L_0x08f5
            com.alibaba.json.JSONObject r21 = new com.alibaba.json.JSONObject     // Catch:{ all -> 0x0115 }
            java.util.LinkedHashMap r48 = new java.util.LinkedHashMap     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            r0 = r21
            r1 = r48
            r0.<init>(r1)     // Catch:{ all -> 0x0115 }
        L_0x0828:
            r9 = 0
            if (r11 != 0) goto L_0x0837
            if (r35 != 0) goto L_0x0837
            r0 = r51
            r1 = r21
            r2 = r47
            com.alibaba.json.parser.ParseContext r9 = r0.setContext(r7, r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x0837:
            r33 = 0
            r34 = 0
            r0 = r51
            com.alibaba.json.parser.deserializer.FieldTypeResolver r0 = r0.fieldTypeResolver     // Catch:{ all -> 0x0115 }
            r48 = r0
            if (r48 == 0) goto L_0x0877
            if (r47 == 0) goto L_0x08fc
            java.lang.String r38 = r47.toString()     // Catch:{ all -> 0x0115 }
        L_0x0849:
            r0 = r51
            com.alibaba.json.parser.deserializer.FieldTypeResolver r0 = r0.fieldTypeResolver     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r48
            r1 = r52
            r2 = r38
            java.lang.reflect.Type r18 = r0.resolve(r1, r2)     // Catch:{ all -> 0x0115 }
            if (r18 == 0) goto L_0x0877
            r0 = r51
            com.alibaba.json.parser.ParserConfig r0 = r0.config     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r48
            r1 = r18
            com.alibaba.json.parser.deserializer.ObjectDeserializer r16 = r0.getDeserializer(r1)     // Catch:{ all -> 0x0115 }
            r0 = r16
            r1 = r51
            r2 = r18
            r3 = r47
            java.lang.Object r33 = r0.deserialze(r1, r2, r3)     // Catch:{ all -> 0x0115 }
            r34 = 1
        L_0x0877:
            if (r34 != 0) goto L_0x0883
            r0 = r51
            r1 = r21
            r2 = r47
            java.lang.Object r33 = r0.parseObject(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x0883:
            if (r9 == 0) goto L_0x088f
            r0 = r21
            r1 = r33
            if (r0 == r1) goto L_0x088f
            r0 = r52
            r9.object = r0     // Catch:{ all -> 0x0115 }
        L_0x088f:
            r0 = r51
            int r0 = r0.resolveStatus     // Catch:{ all -> 0x0115 }
            r48 = r0
            r49 = 1
            r0 = r48
            r1 = r49
            if (r0 != r1) goto L_0x08aa
            java.lang.String r48 = r47.toString()     // Catch:{ all -> 0x0115 }
            r0 = r51
            r1 = r52
            r2 = r48
            r0.checkMapResolve(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x08aa:
            if (r20 == 0) goto L_0x0900
            r0 = r20
            r1 = r47
            r2 = r33
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x08b5:
            if (r35 == 0) goto L_0x08c0
            r0 = r51
            r1 = r33
            r2 = r47
            r0.setContext(r7, r1, r2)     // Catch:{ all -> 0x0115 }
        L_0x08c0:
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r44 = r0
            r47 = 13
            r0 = r44
            r1 = r47
            if (r0 != r1) goto L_0x090a
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x08dd
            r0 = r51
            r0.contex = r7     // Catch:{ all -> 0x0115 }
        L_0x08dd:
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x08e5:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r48 = r0
            r0 = r48
            r1 = r19
            char r48 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x07f4
        L_0x08f5:
            com.alibaba.json.JSONObject r21 = new com.alibaba.json.JSONObject     // Catch:{ all -> 0x0115 }
            r21.<init>()     // Catch:{ all -> 0x0115 }
            goto L_0x0828
        L_0x08fc:
            r38 = 0
            goto L_0x0849
        L_0x0900:
            r0 = r52
            r1 = r47
            r2 = r33
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x08b5
        L_0x090a:
            r47 = 16
            r0 = r44
            r1 = r47
            if (r0 == r1) goto L_0x0086
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0930:
            r48 = 116(0x74, float:1.63E-43)
            r0 = r48
            if (r5 != r0) goto L_0x0969
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r48 = r0
            java.lang.String r49 = "true"
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r50 = r0
            boolean r48 = r48.startsWith(r49, r50)     // Catch:{ all -> 0x0115 }
            if (r48 == 0) goto L_0x069d
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r48 = r0
            int r48 = r48 + 3
            r0 = r48
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r29.next()     // Catch:{ all -> 0x0115 }
            java.lang.Boolean r48 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0115 }
            r0 = r52
            r1 = r47
            r2 = r48
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x069d
        L_0x0969:
            r48 = 102(0x66, float:1.43E-43)
            r0 = r48
            if (r5 != r0) goto L_0x09a2
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r48 = r0
            java.lang.String r49 = "false"
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r50 = r0
            boolean r48 = r48.startsWith(r49, r50)     // Catch:{ all -> 0x0115 }
            if (r48 == 0) goto L_0x069d
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r48 = r0
            int r48 = r48 + 4
            r0 = r48
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r29.next()     // Catch:{ all -> 0x0115 }
            java.lang.Boolean r48 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0115 }
            r0 = r52
            r1 = r47
            r2 = r48
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            goto L_0x069d
        L_0x09a2:
            r29.nextToken()     // Catch:{ all -> 0x0115 }
            java.lang.Object r46 = r51.parse()     // Catch:{ all -> 0x0115 }
            java.lang.Class r48 = r52.getClass()     // Catch:{ all -> 0x0115 }
            java.lang.Class<com.alibaba.json.JSONObject> r49 = com.alibaba.json.JSONObject.class
            r0 = r48
            r1 = r49
            if (r0 != r1) goto L_0x09b9
            java.lang.String r47 = r47.toString()     // Catch:{ all -> 0x0115 }
        L_0x09b9:
            r0 = r52
            r1 = r47
            r2 = r46
            r0.put(r1, r2)     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 13
            r0 = r47
            r1 = r48
            if (r0 != r1) goto L_0x09e1
            r47 = 16
            r0 = r29
            r1 = r47
            r0.nextToken(r1)     // Catch:{ all -> 0x0115 }
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x09e1:
            r0 = r29
            int r0 = r0.token     // Catch:{ all -> 0x0115 }
            r47 = r0
            r48 = 16
            r0 = r47
            r1 = r48
            if (r0 == r1) goto L_0x0086
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0a0d:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            r1 = r19
            char r47 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x06d6
        L_0x0a1d:
            r47 = 125(0x7d, float:1.75E-43)
            r0 = r47
            if (r5 != r0) goto L_0x0b3a
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r47 = r0
            int r19 = r47 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r19
            r1 = r47
            if (r0 < r1) goto L_0x0a96
            r5 = 26
        L_0x0a3f:
            r0 = r29
            r0.ch = r5     // Catch:{ all -> 0x0115 }
            r47 = 0
            r0 = r47
            r1 = r29
            r1.sp = r0     // Catch:{ all -> 0x0115 }
            r47 = 44
            r0 = r47
            if (r5 != r0) goto L_0x0ab4
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r47 = r0
            int r19 = r47 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r19
            r1 = r47
            if (r0 < r1) goto L_0x0aa5
            r47 = 26
        L_0x0a6d:
            r0 = r47
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r47 = 16
            r0 = r47
            r1 = r29
            r1.token = r0     // Catch:{ all -> 0x0115 }
        L_0x0a7b:
            if (r11 != 0) goto L_0x0a8e
            r0 = r51
            com.alibaba.json.parser.ParseContext r0 = r0.contex     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r51
            r1 = r47
            r2 = r52
            r3 = r53
            r0.setContext(r1, r2, r3)     // Catch:{ all -> 0x0115 }
        L_0x0a8e:
            if (r11 != 0) goto L_0x0019
            r0 = r51
            r0.contex = r7
            goto L_0x0019
        L_0x0a96:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            r1 = r19
            char r5 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0a3f
        L_0x0aa5:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            r1 = r19
            char r47 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0a6d
        L_0x0ab4:
            r47 = 125(0x7d, float:1.75E-43)
            r0 = r47
            if (r5 != r0) goto L_0x0af4
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r47 = r0
            int r19 = r47 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r19
            r1 = r47
            if (r0 < r1) goto L_0x0ae5
            r47 = 26
        L_0x0ad6:
            r0 = r47
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r47 = 13
            r0 = r47
            r1 = r29
            r1.token = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0a7b
        L_0x0ae5:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            r1 = r19
            char r47 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0ad6
        L_0x0af4:
            r47 = 93
            r0 = r47
            if (r5 != r0) goto L_0x0b35
            r0 = r29
            int r0 = r0.bp     // Catch:{ all -> 0x0115 }
            r47 = r0
            int r19 = r47 + 1
            r0 = r19
            r1 = r29
            r1.bp = r0     // Catch:{ all -> 0x0115 }
            r0 = r29
            int r0 = r0.len     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r19
            r1 = r47
            if (r0 < r1) goto L_0x0b26
            r47 = 26
        L_0x0b16:
            r0 = r47
            r1 = r29
            r1.ch = r0     // Catch:{ all -> 0x0115 }
            r47 = 15
            r0 = r47
            r1 = r29
            r1.token = r0     // Catch:{ all -> 0x0115 }
            goto L_0x0a7b
        L_0x0b26:
            r0 = r29
            java.lang.String r0 = r0.text     // Catch:{ all -> 0x0115 }
            r47 = r0
            r0 = r47
            r1 = r19
            char r47 = r0.charAt(r1)     // Catch:{ all -> 0x0115 }
            goto L_0x0b16
        L_0x0b35:
            r29.nextToken()     // Catch:{ all -> 0x0115 }
            goto L_0x0a7b
        L_0x0b3a:
            com.alibaba.json.JSONException r47 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = new java.lang.StringBuilder     // Catch:{ all -> 0x0115 }
            r48.<init>()     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = "syntax error, "
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r49 = r29.info()     // Catch:{ all -> 0x0115 }
            java.lang.StringBuilder r48 = r48.append(r49)     // Catch:{ all -> 0x0115 }
            java.lang.String r48 = r48.toString()     // Catch:{ all -> 0x0115 }
            r47.<init>(r48)     // Catch:{ all -> 0x0115 }
            throw r47     // Catch:{ all -> 0x0115 }
        L_0x0b58:
            r47 = r28
            goto L_0x02a6
        L_0x0b5c:
            r47 = r28
            goto L_0x02a6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
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
            com.alibaba.json.parser.ParserConfig r10 = r13.config
            com.alibaba.json.parser.deserializer.ObjectDeserializer r2 = r10.getDeserializer(r1)
            boolean r10 = r2 instanceof com.alibaba.json.parser.JavaBeanDeserializer
            if (r10 == 0) goto L_0x0012
            r0 = r2
            com.alibaba.json.parser.JavaBeanDeserializer r0 = (com.alibaba.json.parser.JavaBeanDeserializer) r0
        L_0x0012:
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r9 = r10.token
            r10 = 12
            if (r9 == r10) goto L_0x0044
            r10 = 16
            if (r9 == r10) goto L_0x0044
            com.alibaba.json.JSONException r10 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "syntax error, expect {, actual "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = com.alibaba.json.parser.JSONToken.name(r9)
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x003c:
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 16
            if (r10 != r11) goto L_0x005e
        L_0x0044:
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            com.alibaba.json.parser.SymbolTable r11 = r13.symbolTable
            java.lang.String r8 = r10.scanSymbol(r11)
            if (r8 != 0) goto L_0x005e
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 13
            if (r10 != r11) goto L_0x003c
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 16
            r10.nextToken(r11)
        L_0x005d:
            return
        L_0x005e:
            r4 = 0
            if (r0 == 0) goto L_0x0065
            com.alibaba.json.parser.deserializer.FieldDeserializer r4 = r0.getFieldDeserializer(r8)
        L_0x0065:
            if (r4 != 0) goto L_0x00b3
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.features
            com.alibaba.json.parser.Feature r11 = com.alibaba.json.parser.Feature.IgnoreNotMatch
            int r11 = r11.mask
            r10 = r10 & r11
            if (r10 != 0) goto L_0x009b
            com.alibaba.json.JSONException r10 = new com.alibaba.json.JSONException
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
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            r13.parse()
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 13
            if (r10 != r11) goto L_0x0044
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r10.nextToken()
            goto L_0x005d
        L_0x00b3:
            com.alibaba.json.util.FieldInfo r10 = r4.fieldInfo
            java.lang.Class<?> r3 = r10.fieldClass
            com.alibaba.json.util.FieldInfo r10 = r4.fieldInfo
            java.lang.reflect.Type r5 = r10.fieldType
            java.lang.Class r10 = java.lang.Integer.TYPE
            if (r3 != r10) goto L_0x00e9
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            com.alibaba.json.serializer.IntegerCodec r10 = com.alibaba.json.serializer.IntegerCodec.instance
            r11 = 0
            java.lang.Object r6 = r10.deserialze(r13, r5, r11)
        L_0x00cd:
            r4.setValue(r14, r6)
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 16
            if (r10 == r11) goto L_0x0044
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            int r10 = r10.token
            r11 = 13
            if (r10 != r11) goto L_0x0044
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 16
            r10.nextToken(r11)
            goto L_0x005d
        L_0x00e9:
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            if (r3 != r10) goto L_0x00f9
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            java.lang.String r6 = r13.parseString()
            goto L_0x00cd
        L_0x00f9:
            java.lang.Class r10 = java.lang.Long.TYPE
            if (r3 != r10) goto L_0x010c
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            com.alibaba.json.serializer.IntegerCodec r10 = com.alibaba.json.serializer.IntegerCodec.instance
            r11 = 0
            java.lang.Object r6 = r10.deserialze(r13, r5, r11)
            goto L_0x00cd
        L_0x010c:
            com.alibaba.json.parser.ParserConfig r10 = r13.config
            com.alibaba.json.parser.deserializer.ObjectDeserializer r7 = r10.getDeserializer(r3, r5)
            com.alibaba.json.parser.JSONLexer r10 = r13.lexer
            r11 = 58
            r10.nextTokenWithChar(r11)
            r10 = 0
            java.lang.Object r6 = r7.deserialze(r13, r5, r10)
            goto L_0x00cd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.DefaultJSONParser.parseObject(java.lang.Object):void");
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
    /* JADX WARNING: type inference failed for: r10v0, types: [java.util.Collection, com.alibaba.json.JSONArray] */
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer
            int r13 = r15.token
            r15 = 21
            if (r13 == r15) goto L_0x000e
            r15 = 22
            if (r13 != r15) goto L_0x001b
        L_0x000e:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer
            r15.nextToken()
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer
            int r13 = r15.token
        L_0x001b:
            r15 = 14
            if (r13 == r15) goto L_0x0054
            com.alibaba.json.JSONException r15 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "syntax error, expect [, actual "
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r17 = com.alibaba.json.parser.JSONToken.name(r13)
            java.lang.StringBuilder r16 = r16.append(r17)
            java.lang.String r17 = ", pos "
            java.lang.StringBuilder r16 = r16.append(r17)
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer
            boolean r5 = r15.disableCircularReferenceDetect
            r0 = r18
            com.alibaba.json.parser.ParseContext r4 = r0.contex
            if (r5 != 0) goto L_0x006d
            r0 = r18
            com.alibaba.json.parser.ParseContext r15 = r0.contex
            r0 = r18
            r1 = r19
            r2 = r20
            r0.setContext(r15, r1, r2)
        L_0x006d:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r3 = r15.ch     // Catch:{ all -> 0x0136 }
            r15 = 34
            if (r3 == r15) goto L_0x013e
            r15 = 93
            if (r3 != r15) goto L_0x0092
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.next()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.len     // Catch:{ all -> 0x0136 }
            if (r8 < r15) goto L_0x0121
            r15 = 26
        L_0x00b2:
            r0 = r16
            r0.ch = r15     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r15 = r15.ch     // Catch:{ all -> 0x0136 }
            r16 = 34
            r0 = r16
            if (r15 != r0) goto L_0x017a
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 34
            java.lang.String r14 = r15.scanStringValue(r16)     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r3 = r15.ch     // Catch:{ all -> 0x0136 }
            r15 = 44
            if (r3 != r15) goto L_0x0192
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r15 = r15.text     // Catch:{ all -> 0x0136 }
            char r15 = r15.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x00b2
        L_0x012c:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.json.parser.Feature r16 = com.alibaba.json.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 != 0) goto L_0x0153
            r6 = 1
            goto L_0x00c1
        L_0x0153:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 4
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            r6 = 0
            goto L_0x00c1
        L_0x015f:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
        L_0x017a:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r13 = r15.token     // Catch:{ all -> 0x0136 }
        L_0x0180:
            r15 = 16
            if (r13 != r15) goto L_0x01ec
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r13 = r15.token     // Catch:{ all -> 0x0136 }
            goto L_0x0180
        L_0x0192:
            r15 = 93
            if (r3 != r15) goto L_0x01e4
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            if (r5 != 0) goto L_0x0091
            r0 = r18
            r0.contex = r4
            goto L_0x0091
        L_0x01d9:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r15 = r15.text     // Catch:{ all -> 0x0136 }
            char r15 = r15.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x01b2
        L_0x01e4:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.token     // Catch:{ all -> 0x0136 }
            r16 = 16
            r0 = r16
            if (r15 != r0) goto L_0x011e
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            char r3 = r15.ch     // Catch:{ all -> 0x0136 }
            r15 = 34
            if (r3 != r15) goto L_0x0345
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            int r0 = r0.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            r15.pos = r0     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.scanString()     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        L_0x0238:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.Number r14 = r15.integerValue()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x024a:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.json.parser.Feature r16 = com.alibaba.json.parser.Feature.UseBigDecimal     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 == 0) goto L_0x0270
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 1
            java.lang.Number r14 = r15.decimalValue(r16)     // Catch:{ all -> 0x0136 }
        L_0x0266:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x0270:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 0
            java.lang.Number r14 = r15.decimalValue(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x0266
        L_0x027b:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r12 = r15.stringVal()     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.json.parser.Feature r16 = com.alibaba.json.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 == 0) goto L_0x02b7
            com.alibaba.json.parser.JSONLexer r9 = new com.alibaba.json.parser.JSONLexer     // Catch:{ all -> 0x0136 }
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
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02c7:
            java.lang.Boolean r14 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02d4:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.features     // Catch:{ all -> 0x0136 }
            com.alibaba.json.parser.Feature r16 = com.alibaba.json.parser.Feature.OrderedField     // Catch:{ all -> 0x0136 }
            r0 = r16
            int r0 = r0.mask     // Catch:{ all -> 0x0136 }
            r16 = r0
            r15 = r15 & r16
            if (r15 == 0) goto L_0x02fc
            com.alibaba.json.JSONObject r11 = new com.alibaba.json.JSONObject     // Catch:{ all -> 0x0136 }
            java.util.LinkedHashMap r15 = new java.util.LinkedHashMap     // Catch:{ all -> 0x0136 }
            r15.<init>()     // Catch:{ all -> 0x0136 }
            r11.<init>(r15)     // Catch:{ all -> 0x0136 }
        L_0x02f0:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0136 }
            r0 = r18
            java.lang.Object r14 = r0.parseObject(r11, r15)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x02fc:
            com.alibaba.json.JSONObject r11 = new com.alibaba.json.JSONObject     // Catch:{ all -> 0x0136 }
            r11.<init>()     // Catch:{ all -> 0x0136 }
            goto L_0x02f0
        L_0x0302:
            com.alibaba.json.JSONArray r10 = new com.alibaba.json.JSONArray     // Catch:{ all -> 0x0136 }
            r10.<init>()     // Catch:{ all -> 0x0136 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0136 }
            r0 = r18
            r0.parseArray(r10, r15)     // Catch:{ all -> 0x0136 }
            r14 = r10
            goto L_0x01f3
        L_0x0313:
            r14 = 0
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 4
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x031f:
            r14 = 0
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 4
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            goto L_0x01f3
        L_0x032b:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 16
            r15.nextToken(r16)     // Catch:{ all -> 0x0136 }
            if (r5 != 0) goto L_0x0091
            r0 = r18
            r0.contex = r4
            goto L_0x0091
        L_0x033c:
            com.alibaba.json.JSONException r15 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "unclosed jsonArray"
            r15.<init>(r16)     // Catch:{ all -> 0x0136 }
            throw r15     // Catch:{ all -> 0x0136 }
        L_0x0345:
            r15 = 48
            if (r3 < r15) goto L_0x036a
            r15 = 57
            if (r3 > r15) goto L_0x036a
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            int r0 = r0.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r16
            r15.pos = r0     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.scanNumber()     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        L_0x036a:
            r15 = 123(0x7b, float:1.72E-43)
            if (r3 != r15) goto L_0x03a5
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = 12
            r0 = r16
            r15.token = r0     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r0 = r15.bp     // Catch:{ all -> 0x0136 }
            r16 = r0
            int r8 = r16 + 1
            r15.bp = r8     // Catch:{ all -> 0x0136 }
            r0 = r18
            com.alibaba.json.parser.JSONLexer r0 = r0.lexer     // Catch:{ all -> 0x0136 }
            r16 = r0
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            int r15 = r15.len     // Catch:{ all -> 0x0136 }
            if (r8 < r15) goto L_0x039a
            r15 = 26
        L_0x0394:
            r0 = r16
            r0.ch = r15     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        L_0x039a:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            java.lang.String r15 = r15.text     // Catch:{ all -> 0x0136 }
            char r15 = r15.charAt(r8)     // Catch:{ all -> 0x0136 }
            goto L_0x0394
        L_0x03a5:
            r0 = r18
            com.alibaba.json.parser.JSONLexer r15 = r0.lexer     // Catch:{ all -> 0x0136 }
            r15.nextToken()     // Catch:{ all -> 0x0136 }
            goto L_0x011e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.parser.DefaultJSONParser.parseArray(java.util.Collection, java.lang.Object):void");
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
