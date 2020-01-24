package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

class JavaObjectDeserializer implements ObjectDeserializer {
    public static final JavaObjectDeserializer instance = new JavaObjectDeserializer();

    JavaObjectDeserializer() {
    }

    /* JADX WARNING: type inference failed for: r0v3, types: [T, java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v3, names: [array], types: [T, java.lang.Object[]]
      assigns: [java.lang.Object[]]
      uses: [java.lang.Object[], T]
      mth insns count: 27
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r8, java.lang.reflect.Type r9, java.lang.Object r10) {
        /*
            r7 = this;
            boolean r5 = r9 instanceof java.lang.reflect.GenericArrayType
            if (r5 == 0) goto L_0x003d
            java.lang.reflect.GenericArrayType r9 = (java.lang.reflect.GenericArrayType) r9
            java.lang.reflect.Type r2 = r9.getGenericComponentType()
            boolean r5 = r2 instanceof java.lang.reflect.TypeVariable
            if (r5 == 0) goto L_0x0018
            r3 = r2
            java.lang.reflect.TypeVariable r3 = (java.lang.reflect.TypeVariable) r3
            java.lang.reflect.Type[] r5 = r3.getBounds()
            r6 = 0
            r2 = r5[r6]
        L_0x0018:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r8.parseArray(r2, r4)
            boolean r5 = r2 instanceof java.lang.Class
            if (r5 == 0) goto L_0x0038
            r1 = r2
            java.lang.Class r1 = (java.lang.Class) r1
            int r5 = r4.size()
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r1, r5)
            java.lang.Object[] r5 = (java.lang.Object[]) r5
            r0 = r5
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r4.toArray(r0)
        L_0x0037:
            return r0
        L_0x0038:
            java.lang.Object[] r0 = r4.toArray()
            goto L_0x0037
        L_0x003d:
            java.lang.Object r0 = r8.parse(r10)
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JavaObjectDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
