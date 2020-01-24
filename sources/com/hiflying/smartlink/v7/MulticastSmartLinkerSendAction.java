package com.hiflying.smartlink.v7;

import android.content.Context;
import android.text.TextUtils;
import com.hiflying.smartlink.ISmartLinker;
import java.nio.ByteBuffer;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MulticastSmartLinkerSendAction implements Runnable {
    private static final int DEFAULT_DATA_LENGTH = 30;
    private static final int UDP_PORT = 6000;
    private String mPassword;
    private ISmartLinker mSmartLinker;
    private String mSsid;

    public MulticastSmartLinkerSendAction(Context context, ISmartLinker smartLinker, String ssid, String password) throws Exception {
        this.mSmartLinker = smartLinker;
        this.mSsid = ssid;
        this.mPassword = password;
        if (context == null) {
            throw new NullPointerException("params context is null");
        } else if (smartLinker == null) {
            throw new NullPointerException("params smartLinker is null");
        } else if (ssid == null) {
            throw new NullPointerException("params ssid is null");
        } else if (password == null) {
            throw new NullPointerException("params password is null");
        }
    }

    private void sleep(long millseconds) {
        try {
            Thread.sleep(millseconds);
        } catch (InterruptedException e) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            r0 = 0
            java.lang.String r7 = r13.mSsid     // Catch:{ Exception -> 0x0021 }
            java.lang.String r8 = r13.mPassword     // Catch:{ Exception -> 0x0021 }
            byte[] r0 = r13.generateData2Send(r7, r8)     // Catch:{ Exception -> 0x0021 }
            r4 = 0
            java.net.MulticastSocket r5 = new java.net.MulticastSocket     // Catch:{ IOException -> 0x00cb }
            r5.<init>()     // Catch:{ IOException -> 0x00cb }
        L_0x000f:
            com.hiflying.smartlink.ISmartLinker r7 = r13.mSmartLinker     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            boolean r7 = r7.isSmartLinking()     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            if (r7 != 0) goto L_0x003f
            if (r5 == 0) goto L_0x001f
            r5.close()
            r5.disconnect()
        L_0x001f:
            r4 = r5
        L_0x0020:
            return
        L_0x0021:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            java.lang.RuntimeException r7 = new java.lang.RuntimeException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "generateData2Send error: "
            r8.<init>(r9)
            java.lang.String r9 = r1.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x003f:
            java.net.DatagramPacket r6 = new java.net.DatagramPacket     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r7 = 30
            byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r8 = 30
            java.lang.String r9 = "239.48.0.0"
            java.net.InetAddress r9 = java.net.InetAddress.getByName(r9)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r10 = 6000(0x1770, float:8.408E-42)
            r6.<init>(r7, r8, r9, r10)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r2 = 0
        L_0x0054:
            r7 = 5
            if (r2 < r7) goto L_0x0071
            int r7 = r0.length     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            int r3 = r7 / 2
            r2 = 0
        L_0x005b:
            if (r2 < r3) goto L_0x007c
            r8 = 100
            r13.sleep(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            goto L_0x000f
        L_0x0063:
            r1 = move-exception
            r4 = r5
        L_0x0065:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x00be }
            if (r4 == 0) goto L_0x0020
            r4.close()
            r4.disconnect()
            goto L_0x0020
        L_0x0071:
            r5.send(r6)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r8 = 10
            r13.sleep(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            int r2 = r2 + 1
            goto L_0x0054
        L_0x007c:
            java.net.DatagramPacket r6 = new java.net.DatagramPacket     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            int r7 = r2 + 30
            byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            int r8 = r2 + 30
            java.lang.String r9 = "239.46.%s.%s"
            r10 = 2
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r11 = 0
            int r12 = r2 * 2
            byte r12 = r0[r12]     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r12 = r12 & 255(0xff, float:3.57E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r10[r11] = r12     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r11 = 1
            int r12 = r2 * 2
            int r12 = r12 + 1
            byte r12 = r0[r12]     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r12 = r12 & 255(0xff, float:3.57E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r10[r11] = r12     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            java.lang.String r9 = java.lang.String.format(r9, r10)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            java.net.InetAddress r9 = java.net.InetAddress.getByName(r9)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r10 = 6000(0x1770, float:8.408E-42)
            r6.<init>(r7, r8, r9, r10)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r5.send(r6)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            r8 = 10
            r13.sleep(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00c8 }
            int r2 = r2 + 1
            goto L_0x005b
        L_0x00be:
            r7 = move-exception
        L_0x00bf:
            if (r4 == 0) goto L_0x00c7
            r4.close()
            r4.disconnect()
        L_0x00c7:
            throw r7
        L_0x00c8:
            r7 = move-exception
            r4 = r5
            goto L_0x00bf
        L_0x00cb:
            r1 = move-exception
            goto L_0x0065
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiflying.smartlink.v7.MulticastSmartLinkerSendAction.run():void");
    }

    private byte[] encodedPMK(String ssid, String password) throws Exception {
        if (TextUtils.isEmpty(password)) {
            return new byte[0];
        }
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(TextUtils.isEmpty(password) ? new char[0] : password.toCharArray(), ssid.getBytes(), 4096, 256)).getEncoded();
    }

    private byte[] generateData2Send(String ssid, String password) throws Exception {
        boolean odd;
        byte[] ssidDataArray = ssid.getBytes();
        byte[] passwordArray = TextUtils.isEmpty(password) ? new byte[0] : password.getBytes();
        byte[] pmkDataArray = encodedPMK(ssid, password);
        byte[] userDataArray = new byte[0];
        int bufferSize = ssidDataArray.length + 4 + passwordArray.length + pmkDataArray.length + userDataArray.length;
        if (bufferSize % 2 != 0) {
            odd = true;
        } else {
            odd = false;
        }
        if (odd) {
            bufferSize++;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.put((byte) ssidDataArray.length);
        buffer.put((byte) passwordArray.length);
        buffer.put((byte) pmkDataArray.length);
        buffer.put((byte) userDataArray.length);
        buffer.put(ssidDataArray);
        buffer.put(passwordArray);
        buffer.put(pmkDataArray);
        buffer.put(userDataArray);
        if (odd) {
            buffer.put(0);
        }
        return buffer.array();
    }
}
