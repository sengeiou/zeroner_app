package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.io.IOException;

final class zzf implements zzj<Void> {
    private /* synthetic */ Bundle val$extras;
    private /* synthetic */ String zzecf;

    zzf(String str, Bundle bundle) {
        this.zzecf = str;
        this.val$extras = bundle;
    }

    public final /* synthetic */ Object zzac(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundle = (Bundle) 
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0010: CHECK_CAST  (r0v3 'bundle' android.os.Bundle) = (android.os.Bundle) (wrap: java.lang.Object
              0x000c: INVOKE  (r0v2 java.lang.Object) = (wrap: android.os.Bundle
              0x0008: INVOKE  (r0v1 android.os.Bundle) = (wrap: com.google.android.gms.internal.zzex
              0x0000: INVOKE  (r0v0 com.google.android.gms.internal.zzex) = (r4v0 'iBinder' android.os.IBinder) com.google.android.gms.internal.zzey.zza(android.os.IBinder):com.google.android.gms.internal.zzex type: STATIC), (wrap: java.lang.String
              0x0004: IGET  (r1v0 java.lang.String) = (r3v0 'this' com.google.android.gms.auth.zzf A[THIS]) com.google.android.gms.auth.zzf.zzecf java.lang.String), (wrap: android.os.Bundle
              0x0006: IGET  (r2v0 android.os.Bundle) = (r3v0 'this' com.google.android.gms.auth.zzf A[THIS]) com.google.android.gms.auth.zzf.val$extras android.os.Bundle) com.google.android.gms.internal.zzex.zza(java.lang.String, android.os.Bundle):android.os.Bundle type: INTERFACE) com.google.android.gms.auth.zzd.zzq(java.lang.Object):java.lang.Object type: STATIC) in method: com.google.android.gms.auth.zzf.zzac(android.os.IBinder):java.lang.Object, dex: classes2.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
            Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000c: INVOKE  (r0v2 java.lang.Object) = (wrap: android.os.Bundle
              0x0008: INVOKE  (r0v1 android.os.Bundle) = (wrap: com.google.android.gms.internal.zzex
              0x0000: INVOKE  (r0v0 com.google.android.gms.internal.zzex) = (r4v0 'iBinder' android.os.IBinder) com.google.android.gms.internal.zzey.zza(android.os.IBinder):com.google.android.gms.internal.zzex type: STATIC), (wrap: java.lang.String
              0x0004: IGET  (r1v0 java.lang.String) = (r3v0 'this' com.google.android.gms.auth.zzf A[THIS]) com.google.android.gms.auth.zzf.zzecf java.lang.String), (wrap: android.os.Bundle
              0x0006: IGET  (r2v0 android.os.Bundle) = (r3v0 'this' com.google.android.gms.auth.zzf A[THIS]) com.google.android.gms.auth.zzf.val$extras android.os.Bundle) com.google.android.gms.internal.zzex.zza(java.lang.String, android.os.Bundle):android.os.Bundle type: INTERFACE) com.google.android.gms.auth.zzd.zzq(java.lang.Object):java.lang.Object type: STATIC in method: com.google.android.gms.auth.zzf.zzac(android.os.IBinder):java.lang.Object, dex: classes2.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:280)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
            	... 19 more
            Caused by: java.util.ConcurrentModificationException
            	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1681)
            	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1656)
            	at jadx.core.dex.instructions.args.SSAVar.removeUse(SSAVar.java:86)
            	at jadx.core.dex.instructions.args.SSAVar.use(SSAVar.java:79)
            	at jadx.core.dex.nodes.InsnNode.attachArg(InsnNode.java:87)
            	at jadx.core.dex.nodes.InsnNode.addArg(InsnNode.java:73)
            	at jadx.core.dex.nodes.InsnNode.copyCommonParams(InsnNode.java:335)
            	at jadx.core.dex.instructions.InvokeNode.copy(InvokeNode.java:56)
            	at jadx.core.dex.nodes.InsnNode.copyCommonParams(InsnNode.java:333)
            	at jadx.core.dex.nodes.InsnNode.copy(InsnNode.java:350)
            	at jadx.core.codegen.InsnGen.inlineMethod(InsnGen.java:880)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:669)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
            	... 22 more
            */
        /*
            this = this;
            com.google.android.gms.internal.zzex r0 = com.google.android.gms.internal.zzey.zza(r4)
            java.lang.String r1 = r3.zzecf
            android.os.Bundle r2 = r3.val$extras
            android.os.Bundle r0 = r0.zza(r1, r2)
            java.lang.Object r0 = com.google.android.gms.auth.zzd.zzp(r0)
            android.os.Bundle r0 = (android.os.Bundle) r0
            java.lang.String r1 = "Error"
            java.lang.String r1 = r0.getString(r1)
            java.lang.String r2 = "booleanResult"
            boolean r0 = r0.getBoolean(r2)
            if (r0 != 0) goto L_0x0028
            com.google.android.gms.auth.GoogleAuthException r0 = new com.google.android.gms.auth.GoogleAuthException
            r0.<init>(r1)
            throw r0
        L_0x0028:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.zzf.zzac(android.os.IBinder):java.lang.Object");
    }
}
