package cn.smssdk.net;

import com.mob.commons.iosbridge.UDPServer;

class e implements Runnable {
    final /* synthetic */ b a;

    e(b bVar) {
        this.a = bVar;
    }

    public void run() {
        UDPServer.start(this.a.d);
    }
}
