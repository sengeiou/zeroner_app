package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Map;

final class zzac implements OnCompleteListener<Map<zzh<?>, String>> {
    private /* synthetic */ zzaa zzfqm;

    private zzac(zzaa zzaa) {
        this.zzfqm = zzaa;
    }

    public final void onComplete(@NonNull Task<Map<zzh<?>, String>> task) {
        this.zzfqm.zzfps.lock();
        try {
            if (this.zzfqm.zzfqh) {
                if (task.isSuccessful()) {
                    this.zzfqm.zzfqi = new ArrayMap(this.zzfqm.zzfpy.size());
                    for (zzz zzagn : this.zzfqm.zzfpy.values()) {
                        this.zzfqm.zzfqi.put(zzagn.zzagn(), ConnectionResult.zzfkr);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x007a: INVOKE  (r1v13 boolean) = (wrap: com.google.android.gms.common.api.internal.zzaa
                          0x0078: IGET  (r1v12 com.google.android.gms.common.api.internal.zzaa) = (r6v0 'this' com.google.android.gms.common.api.internal.zzac A[THIS]) com.google.android.gms.common.api.internal.zzac.zzfqm com.google.android.gms.common.api.internal.zzaa) com.google.android.gms.common.api.internal.zzaa.zze(com.google.android.gms.common.api.internal.zzaa):boolean type: STATIC in method: com.google.android.gms.common.api.internal.zzac.onComplete(com.google.android.gms.tasks.Task):void, dex: classes2.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                        	at jadx.core.codegen.ConditionGen.addCompare(ConditionGen.java:115)
                        	at jadx.core.codegen.ConditionGen.add(ConditionGen.java:57)
                        	at jadx.core.codegen.ConditionGen.add(ConditionGen.java:46)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                        	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:163)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:144)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:299)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:68)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                        	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:76)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
                        	at jadx.core.ProcessClass.process(ProcessClass.java:36)
                        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                        Caused by: java.lang.ArrayIndexOutOfBoundsException: Index -2 out of bounds for length 2
                        	at java.base/java.util.ArrayList.shiftTailOverGap(ArrayList.java:746)
                        	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1687)
                        	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1656)
                        	at jadx.core.dex.instructions.args.SSAVar.removeUse(SSAVar.java:86)
                        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:90)
                        	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:130)
                        	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:134)
                        	at jadx.core.codegen.InsnGen.inlineMethod(InsnGen.java:892)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:669)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                        	... 41 more
                        */
                    /*
                        this = this;
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm
                        java.util.concurrent.locks.Lock r0 = r0.zzfps
                        r0.lock()
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        boolean r0 = r0.zzfqh     // Catch:{ all -> 0x005f }
                        if (r0 != 0) goto L_0x001b
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm
                        java.util.concurrent.locks.Lock r0 = r0.zzfps
                        r0.unlock()
                    L_0x001a:
                        return
                    L_0x001b:
                        boolean r0 = r7.isSuccessful()     // Catch:{ all -> 0x005f }
                        if (r0 == 0) goto L_0x006a     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        android.support.v4.util.ArrayMap r1 = new android.support.v4.util.ArrayMap     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r2 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r2 = r2.zzfpy     // Catch:{ all -> 0x005f }
                        int r2 = r2.size()     // Catch:{ all -> 0x005f }
                        r1.<init>(r2)     // Catch:{ all -> 0x005f }
                        r0.zzfqi = r1     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r0 = r0.zzfpy     // Catch:{ all -> 0x005f }
                        java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x005f }
                        java.util.Iterator r1 = r0.iterator()     // Catch:{ all -> 0x005f }
                    L_0x0043:
                        boolean r0 = r1.hasNext()     // Catch:{ all -> 0x005f }
                        if (r0 == 0) goto L_0x00ed     // Catch:{ all -> 0x005f }
                        java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzz r0 = (com.google.android.gms.common.api.internal.zzz) r0     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r2 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r2 = r2.zzfqi     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzh r0 = r0.zzagn()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r3 = com.google.android.gms.common.ConnectionResult.zzfkr     // Catch:{ all -> 0x005f }
                        r2.put(r0, r3)     // Catch:{ all -> 0x005f }
                        goto L_0x0043
                    L_0x005f:
                        r0 = move-exception
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm
                        java.util.concurrent.locks.Lock r1 = r1.zzfps
                        r1.unlock()
                        throw r0
                    L_0x006a:
                        java.lang.Exception r0 = r7.getException()     // Catch:{ all -> 0x005f }
                        boolean r0 = r0 instanceof com.google.android.gms.common.api.AvailabilityException     // Catch:{ all -> 0x005f }
                        if (r0 == 0) goto L_0x0135     // Catch:{ all -> 0x005f }
                        java.lang.Exception r0 = r7.getException()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.AvailabilityException r0 = (com.google.android.gms.common.api.AvailabilityException) r0     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        boolean r1 = 
                        // error: 0x007a: INVOKE  (r1 I:boolean) = (r1 I:com.google.android.gms.common.api.internal.zzaa) com.google.android.gms.common.api.internal.zzaa.zze(com.google.android.gms.common.api.internal.zzaa):boolean type: STATIC
                        if (r1 == 0) goto L_0x00d9     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        android.support.v4.util.ArrayMap r2 = new android.support.v4.util.ArrayMap     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r3 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r3 = r3.zzfpy     // Catch:{ all -> 0x005f }
                        int r3 = r3.size()     // Catch:{ all -> 0x005f }
                        r2.<init>(r3)     // Catch:{ all -> 0x005f }
                        r1.zzfqi = r2     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r1 = r1.zzfpy     // Catch:{ all -> 0x005f }
                        java.util.Collection r1 = r1.values()     // Catch:{ all -> 0x005f }
                        java.util.Iterator r2 = r1.iterator()     // Catch:{ all -> 0x005f }
                    L_0x00a2:
                        boolean r1 = r2.hasNext()     // Catch:{ all -> 0x005f }
                        if (r1 == 0) goto L_0x00e2     // Catch:{ all -> 0x005f }
                        java.lang.Object r1 = r2.next()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzz r1 = (com.google.android.gms.common.api.internal.zzz) r1     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzh r3 = r1.zzagn()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r4 = r0.getConnectionResult(r1)     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r5 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        boolean r1 = r5.zza(r1, r4)     // Catch:{ all -> 0x005f }
                        if (r1 == 0) goto L_0x00cf     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r1 = r1.zzfqi     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult     // Catch:{ all -> 0x005f }
                        r5 = 16     // Catch:{ all -> 0x005f }
                        r4.<init>(r5)     // Catch:{ all -> 0x005f }
                        r1.put(r3, r4)     // Catch:{ all -> 0x005f }
                        goto L_0x00a2     // Catch:{ all -> 0x005f }
                    L_0x00cf:
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r1 = r1.zzfqi     // Catch:{ all -> 0x005f }
                        r1.put(r3, r4)     // Catch:{ all -> 0x005f }
                        goto L_0x00a2     // Catch:{ all -> 0x005f }
                    L_0x00d9:
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        android.support.v4.util.ArrayMap r0 = r0.zzagj()     // Catch:{ all -> 0x005f }
                        r1.zzfqi = r0     // Catch:{ all -> 0x005f }
                    L_0x00e2:
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r1 = r1.zzaht()     // Catch:{ all -> 0x005f }
                        r0.zzfql = r1     // Catch:{ all -> 0x005f }
                    L_0x00ed:
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r0 = r0.zzfqj     // Catch:{ all -> 0x005f }
                        if (r0 == 0) goto L_0x010f     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r0 = r0.zzfqi     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r1 = r1.zzfqj     // Catch:{ all -> 0x005f }
                        r0.putAll(r1)     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r1 = r1.zzaht()     // Catch:{ all -> 0x005f }
                        r0.zzfql = r1     // Catch:{ all -> 0x005f }
                    L_0x010f:
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r0 = r0.zzfql     // Catch:{ all -> 0x005f }
                        if (r0 != 0) goto L_0x0158     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        r0.zzahr()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        r0.zzahs()     // Catch:{ all -> 0x005f }
                    L_0x0121:
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.concurrent.locks.Condition r0 = r0.zzfqd     // Catch:{ all -> 0x005f }
                        r0.signalAll()     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm
                        java.util.concurrent.locks.Lock r0 = r0.zzfps
                        r0.unlock()
                        goto L_0x001a
                    L_0x0135:
                        java.lang.String r0 = "ConnectionlessGAC"
                        java.lang.String r1 = "Unexpected availability exception"
                        java.lang.Exception r2 = r7.getException()     // Catch:{ all -> 0x005f }
                        android.util.Log.e(r0, r1, r2)     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        java.util.Map r1 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x005f }
                        r0.zzfqi = r1     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r1 = new com.google.android.gms.common.ConnectionResult     // Catch:{ all -> 0x005f }
                        r2 = 8     // Catch:{ all -> 0x005f }
                        r1.<init>(r2)     // Catch:{ all -> 0x005f }
                        r0.zzfql = r1     // Catch:{ all -> 0x005f }
                        goto L_0x00ed     // Catch:{ all -> 0x005f }
                    L_0x0158:
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        r1 = 0     // Catch:{ all -> 0x005f }
                        r0.zzfqh = false     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r0 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzba r0 = r0.zzfqb     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.api.internal.zzaa r1 = r6.zzfqm     // Catch:{ all -> 0x005f }
                        com.google.android.gms.common.ConnectionResult r1 = r1.zzfql     // Catch:{ all -> 0x005f }
                        r0.zzc(r1)     // Catch:{ all -> 0x005f }
                        goto L_0x0121
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzac.onComplete(com.google.android.gms.tasks.Task):void");
                }
            }
