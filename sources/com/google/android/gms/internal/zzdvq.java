package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;

final class zzdvq extends zzdvm {
    zzdvq() {
    }

    public final void zza(Throwable th, PrintStream printStream) {
        ThrowableExtension.printStackTrace(th, printStream);
    }

    public final void zza(Throwable th, PrintWriter printWriter) {
        ThrowableExtension.printStackTrace(th, printWriter);
    }
}
