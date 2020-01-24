package com.loc;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.Iterator;
import java.util.List;

/* compiled from: SDKDBOperation */
public final class at {
    private ao a;
    private Context b;

    public at(Context context, boolean z) {
        this.b = context;
        this.a = a(this.b, z);
    }

    private static ao a(Context context, boolean z) {
        try {
            return new ao(context, ao.a(as.class));
        } catch (Throwable th) {
            if (!z) {
                aj.b(th, "sd", "gdb");
            }
            return null;
        }
    }

    public final List<v> a() {
        boolean z = false;
        try {
            return this.a.a(v.g(), v.class, true);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return z;
        }
    }

    public final void a(v vVar) {
        boolean z;
        if (vVar != null) {
            try {
                if (this.a == null) {
                    this.a = a(this.b, false);
                }
                String a2 = v.a(vVar.a());
                List b2 = this.a.b(a2, v.class);
                if (b2 == null || b2.size() == 0) {
                    this.a.a(vVar);
                    return;
                }
                Iterator it = b2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((v) it.next()).equals(vVar)) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    this.a.a(a2, (Object) vVar);
                }
            } catch (Throwable th) {
                aj.b(th, "sd", "it");
            }
        }
    }
}
