package cn.smssdk;

import java.util.Iterator;

class c extends Thread {
    final /* synthetic */ int a;
    final /* synthetic */ Object b;
    final /* synthetic */ a c;

    c(a aVar, int i, Object obj) {
        this.c = aVar;
        this.a = i;
        this.b = obj;
    }

    public void run() {
        synchronized (this.c.b) {
            Iterator it = this.c.b.iterator();
            while (it.hasNext()) {
                ((EventHandler) it.next()).beforeEvent(this.a, this.b);
            }
        }
        this.c.b(this.a, this.b);
    }
}
