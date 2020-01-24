package coms.mediatek.wearable;

import android.util.Log;
import java.util.ArrayList;

class i {
    private final String a;
    private final boolean b;
    private final boolean c;
    private final ArrayList<byte[]> d = new ArrayList<>();
    private int e;
    private boolean f;

    i(String str, boolean z, boolean z2) {
        Log.d("[wearable]Session", "tag=" + str + " isProgress=" + z2 + " isResponse=" + z);
        this.a = str;
        this.b = z;
        this.c = z2;
        this.f = false;
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        this.f = z;
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            Log.e("[wearable]Session", "addRequest return, error data");
        } else {
            this.d.add(bArr);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public byte[] a(int i) {
        return (byte[]) this.d.get(i);
    }

    /* access modifiers changed from: 0000 */
    public void b(int i) {
        this.e = i;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public String d() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public int e() {
        return this.d.size();
    }

    /* access modifiers changed from: 0000 */
    public int f() {
        return this.e;
    }

    public String toString() {
        return "Session[Tag=" + this.a + ", mIsResponse=" + this.b + ", mIsProgress=" + this.c + ", RequestSize=" + e() + "]";
    }
}
