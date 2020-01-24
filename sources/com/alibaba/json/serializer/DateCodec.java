package com.alibaba.json.serializer;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.JSONLexer;
import com.alibaba.json.parser.deserializer.ObjectDeserializer;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateCodec implements ObjectSerializer, ObjectDeserializer {
    public static final DateCodec instance = new DateCodec();

    private DateCodec() {
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
        Date date;
        char[] buf;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else if ((out.features & SerializerFeature.WriteClassName.mask) == 0 || object.getClass() == fieldType) {
            if (object instanceof Calendar) {
                date = ((Calendar) object).getTime();
            } else {
                date = (Date) object;
            }
            if ((out.features & SerializerFeature.WriteDateUseDateFormat.mask) != 0) {
                DateFormat format = serializer.getDateFormat();
                if (format == null) {
                    format = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, serializer.locale);
                    format.setTimeZone(serializer.timeZone);
                }
                out.writeString(format.format(date));
                return;
            }
            long time = date.getTime();
            if ((out.features & SerializerFeature.UseISO8601DateFormat.mask) != 0) {
                if ((out.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                    out.write(39);
                } else {
                    out.write(34);
                }
                Calendar calendar = Calendar.getInstance(serializer.timeZone, serializer.locale);
                calendar.setTimeInMillis(time);
                int year = calendar.get(1);
                int month = calendar.get(2) + 1;
                int day = calendar.get(5);
                int hour = calendar.get(11);
                int minute = calendar.get(12);
                int second = calendar.get(13);
                int millis = calendar.get(14);
                if (millis != 0) {
                    buf = "0000-00-00T00:00:00.000".toCharArray();
                    SerializeWriter.getChars((long) millis, 23, buf);
                    SerializeWriter.getChars((long) second, 19, buf);
                    SerializeWriter.getChars((long) minute, 16, buf);
                    SerializeWriter.getChars((long) hour, 13, buf);
                    SerializeWriter.getChars((long) day, 10, buf);
                    SerializeWriter.getChars((long) month, 7, buf);
                    SerializeWriter.getChars((long) year, 4, buf);
                } else if (second == 0 && minute == 0 && hour == 0) {
                    buf = "0000-00-00".toCharArray();
                    SerializeWriter.getChars((long) day, 10, buf);
                    SerializeWriter.getChars((long) month, 7, buf);
                    SerializeWriter.getChars((long) year, 4, buf);
                } else {
                    buf = "0000-00-00T00:00:00".toCharArray();
                    SerializeWriter.getChars((long) second, 19, buf);
                    SerializeWriter.getChars((long) minute, 16, buf);
                    SerializeWriter.getChars((long) hour, 13, buf);
                    SerializeWriter.getChars((long) day, 10, buf);
                    SerializeWriter.getChars((long) month, 7, buf);
                    SerializeWriter.getChars((long) year, 4, buf);
                }
                out.write(buf);
                if ((out.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                    out.write(39);
                } else {
                    out.write(34);
                }
            } else {
                out.writeLong(time);
            }
        } else if (object.getClass() == Date.class) {
            out.write("new Date(");
            out.writeLong(((Date) object).getTime());
            out.write(41);
        } else {
            out.write(123);
            out.writeFieldName("@type", false);
            serializer.write(object.getClass().getName());
            out.write(44);
            out.writeFieldName("val", false);
            out.writeLong(((Date) object).getTime());
            out.write((int) Opcodes.NEG_LONG);
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        return deserialze(parser, clazz, fieldName, null);
    }

    /* JADX WARNING: type inference failed for: r20v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v0 */
    /* JADX WARNING: type inference failed for: r20v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v1 */
    /* JADX WARNING: type inference failed for: r20v2, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r6v3 */
    /* JADX WARNING: type inference failed for: r14v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r20v4 */
    /* JADX WARNING: type inference failed for: r20v5 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r20v6 */
    /* JADX WARNING: type inference failed for: r20v7, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r6v5, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r20v8, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r20v9 */
    /* JADX WARNING: type inference failed for: r20v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r20v6
      assigns: []
      uses: []
      mth insns count: 129
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
    public <T> T deserialze(com.alibaba.json.parser.DefaultJSONParser r22, java.lang.reflect.Type r23, java.lang.Object r24, java.lang.String r25) {
        /*
            r21 = this;
            r0 = r22
            com.alibaba.json.parser.JSONLexer r12 = r0.lexer
            int r15 = r12.token()
            r2 = 2
            if (r15 != r2) goto L_0x0034
            long r2 = r12.longValue()
            java.lang.Long r20 = java.lang.Long.valueOf(r2)
            r2 = 16
            r12.nextToken(r2)
            r6 = r20
        L_0x001a:
            r2 = r21
            r3 = r22
            r4 = r23
            r5 = r24
            r7 = r25
            java.lang.Object r13 = r2.cast(r3, r4, r5, r6, r7)
            java.lang.Class<java.util.Calendar> r2 = java.util.Calendar.class
            r0 = r23
            if (r0 != r2) goto L_0x0177
            boolean r2 = r13 instanceof java.util.Calendar
            if (r2 == 0) goto L_0x0162
            r8 = r13
        L_0x0033:
            return r8
        L_0x0034:
            r2 = 4
            if (r15 != r2) goto L_0x006d
            java.lang.String r14 = r12.stringVal()
            r20 = r14
            r2 = 16
            r12.nextToken(r2)
            int r2 = r12.features
            com.alibaba.json.parser.Feature r3 = com.alibaba.json.parser.Feature.AllowISO8601DateFormat
            int r3 = r3.mask
            r2 = r2 & r3
            if (r2 == 0) goto L_0x006a
            com.alibaba.json.parser.JSONLexer r10 = new com.alibaba.json.parser.JSONLexer
            r10.<init>(r14)
            r2 = 1
            boolean r2 = r10.scanISO8601DateIfMatch(r2)
            if (r2 == 0) goto L_0x0067
            java.util.Calendar r8 = r10.calendar
            java.lang.Class<java.util.Calendar> r2 = java.util.Calendar.class
            r0 = r23
            if (r0 != r2) goto L_0x0063
            r10.close()
            goto L_0x0033
        L_0x0063:
            java.util.Date r20 = r8.getTime()
        L_0x0067:
            r10.close()
        L_0x006a:
            r6 = r20
            goto L_0x001a
        L_0x006d:
            r2 = 8
            if (r15 != r2) goto L_0x0079
            r12.nextToken()
            r20 = 0
            r6 = r20
            goto L_0x001a
        L_0x0079:
            r2 = 12
            if (r15 != r2) goto L_0x0108
            r12.nextToken()
            int r2 = r12.token()
            r3 = 4
            if (r2 != r3) goto L_0x00e1
            java.lang.String r11 = r12.stringVal()
            java.lang.String r2 = "@type"
            boolean r2 = r2.equals(r11)
            if (r2 == 0) goto L_0x00bf
            r12.nextToken()
            r2 = 17
            r0 = r22
            r0.accept(r2)
            java.lang.String r19 = r12.stringVal()
            r0 = r22
            com.alibaba.json.parser.ParserConfig r2 = r0.config
            java.lang.ClassLoader r2 = r2.defaultClassLoader
            r0 = r19
            java.lang.Class r18 = com.alibaba.json.util.TypeUtils.loadClass(r0, r2)
            if (r18 == 0) goto L_0x00b2
            r23 = r18
        L_0x00b2:
            r2 = 4
            r0 = r22
            r0.accept(r2)
            r2 = 16
            r0 = r22
            r0.accept(r2)
        L_0x00bf:
            r2 = 58
            r12.nextTokenWithChar(r2)
            int r15 = r12.token()
            r2 = 2
            if (r15 != r2) goto L_0x00ea
            long r16 = r12.longValue()
            r12.nextToken()
            java.lang.Long r20 = java.lang.Long.valueOf(r16)
            r2 = 13
            r0 = r22
            r0.accept(r2)
            r6 = r20
            goto L_0x001a
        L_0x00e1:
            com.alibaba.json.JSONException r2 = new com.alibaba.json.JSONException
            java.lang.String r3 = "syntax error"
            r2.<init>(r3)
            throw r2
        L_0x00ea:
            com.alibaba.json.JSONException r2 = new com.alibaba.json.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "syntax error : "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = com.alibaba.json.parser.JSONToken.name(r15)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0108:
            r0 = r22
            int r2 = r0.resolveStatus
            r3 = 2
            if (r2 != r3) goto L_0x015a
            r2 = 0
            r0 = r22
            r0.resolveStatus = r2
            r2 = 16
            r0 = r22
            r0.accept(r2)
            int r2 = r12.token()
            r3 = 4
            if (r2 != r3) goto L_0x0151
            java.lang.String r2 = "val"
            java.lang.String r3 = r12.stringVal()
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0138
            com.alibaba.json.JSONException r2 = new com.alibaba.json.JSONException
            java.lang.String r3 = "syntax error"
            r2.<init>(r3)
            throw r2
        L_0x0138:
            r12.nextToken()
            r2 = 17
            r0 = r22
            r0.accept(r2)
            java.lang.Object r20 = r22.parse()
            r2 = 13
            r0 = r22
            r0.accept(r2)
            r6 = r20
            goto L_0x001a
        L_0x0151:
            com.alibaba.json.JSONException r2 = new com.alibaba.json.JSONException
            java.lang.String r3 = "syntax error"
            r2.<init>(r3)
            throw r2
        L_0x015a:
            java.lang.Object r20 = r22.parse()
            r6 = r20
            goto L_0x001a
        L_0x0162:
            r9 = r13
            java.util.Date r9 = (java.util.Date) r9
            if (r9 != 0) goto L_0x016a
            r8 = 0
            goto L_0x0033
        L_0x016a:
            java.util.TimeZone r2 = r12.timeZone
            java.util.Locale r3 = r12.locale
            java.util.Calendar r8 = java.util.Calendar.getInstance(r2, r3)
            r8.setTime(r9)
            goto L_0x0033
        L_0x0177:
            r8 = r13
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.serializer.DateCodec.deserialze(com.alibaba.json.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.String):java.lang.Object");
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val, String format) {
        DateFormat dateFormat;
        if (val == null) {
            return null;
        }
        if (val instanceof Date) {
            return val;
        }
        if (val instanceof Number) {
            return new Date(((Number) val).longValue());
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            JSONLexer dateLexer = new JSONLexer(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.calendar;
                    if (clazz == Calendar.class) {
                        dateLexer.close();
                        return calendar;
                    }
                    Object val2 = calendar.getTime();
                    dateLexer.close();
                    return val2;
                }
                dateLexer.close();
                if ("0000-00-00".equals(strVal) || "0000-00-00T00:00:00".equalsIgnoreCase(strVal) || "0001-01-01T00:00:00+08:00".equalsIgnoreCase(strVal)) {
                    return null;
                }
                if (format != null) {
                    dateFormat = new SimpleDateFormat(format);
                } else {
                    dateFormat = parser.getDateFormat();
                }
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    return new Date(Long.parseLong(strVal));
                }
            } catch (Throwable th) {
                dateLexer.close();
                throw th;
            }
        } else {
            throw new JSONException("parse error");
        }
    }
}
