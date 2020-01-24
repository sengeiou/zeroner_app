package com.hiflying.smartlink.v3;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.hiflying.smartlink.ISmartLinker;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SnifferSmartLinkerSendAction implements Runnable {
    private int CONTENT_CHECKSUM_BEFORE_DELAY_TIME = 100;
    private int CONTENT_COUNT = 5;
    private int CONTENT_GROUP_DELAY_TIME = 500;
    private int CONTENT_PACKAGE_DELAY_TIME = 50;
    private int HEADER_CAPACITY = 76;
    private int HEADER_COUNT = 200;
    private int HEADER_PACKAGE_DELAY_TIME = 10;
    private InetAddress mBroadcastInetAddress;
    private Context mContext;
    private String mPassword;
    private ISmartLinker mSmartLinker;
    private DatagramSocket mSocket;
    private String mSsid;
    private int port = 49999;

    public SnifferSmartLinkerSendAction(Context context, DatagramSocket socket, ISmartLinker smartLinker, String ssid, String password) throws Exception {
        this.mContext = context;
        this.mSocket = socket;
        this.mSmartLinker = smartLinker;
        this.mSsid = ssid;
        this.mPassword = password;
        this.mBroadcastInetAddress = InetAddress.getByName(getBroadcastAddress(this.mContext));
        if (context == null) {
            throw new NullPointerException("params context is null");
        } else if (socket == null) {
            throw new NullPointerException("params socket is null");
        } else if (smartLinker == null) {
            throw new NullPointerException("params smartLinker is null");
        } else if (password == null) {
            throw new NullPointerException("params password is null");
        }
    }

    public void run() {
        while (this.mSmartLinker.isSmartLinking()) {
            sendContents();
        }
    }

    private void sleep(int millseconds) {
        try {
            Thread.sleep((long) millseconds);
        } catch (InterruptedException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private String getBroadcastAddress(Context ctx) {
        DhcpInfo myDhcpInfo = ((WifiManager) ctx.getSystemService("wifi")).getDhcpInfo();
        if (myDhcpInfo == null) {
            return "255.255.255.255";
        }
        int broadcast = (myDhcpInfo.ipAddress & myDhcpInfo.netmask) | (myDhcpInfo.netmask ^ -1);
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++) {
            quads[k] = (byte) ((broadcast >> (k * 8)) & 255);
        }
        try {
            return InetAddress.getByAddress(quads).getHostAddress();
        } catch (Exception e) {
            return "255.255.255.255";
        }
    }

    private void sendContents() {
        byte[] header = genPackageContents(this.HEADER_CAPACITY);
        int i = 0;
        while (this.mSmartLinker.isSmartLinking() && i < this.HEADER_COUNT) {
            send(header);
            sleep(this.HEADER_PACKAGE_DELAY_TIME);
            i++;
        }
        int[] content = new int[(this.mPassword.length() + 2)];
        content[0] = 89;
        for (int i2 = 0; i2 < this.mPassword.length(); i2++) {
            content[i2 + 1] = this.mPassword.charAt(i2) + 'L';
        }
        content[content.length - 1] = 86;
        int count = 1;
        while (this.mSmartLinker.isSmartLinking() && count <= this.CONTENT_COUNT) {
            int i3 = 0;
            while (i3 < content.length) {
                int _count = (i3 == 0 || i3 == content.length + -1) ? 3 : 1;
                int j = 0;
                while (this.mSmartLinker.isSmartLinking() && j < _count) {
                    send(genPackageContents(content[i3]));
                    if (i3 != content.length - 1) {
                        sleep(this.CONTENT_PACKAGE_DELAY_TIME);
                    }
                    j++;
                }
                if (i3 != content.length - 1) {
                    sleep(this.CONTENT_PACKAGE_DELAY_TIME);
                }
                i3++;
            }
            sleep(this.CONTENT_CHECKSUM_BEFORE_DELAY_TIME);
            int checkLength = this.mPassword.length() + 256 + 76;
            int i4 = 0;
            while (this.mSmartLinker.isSmartLinking() && i4 < 3) {
                send(genPackageContents(checkLength));
                if (i4 < 2) {
                    sleep(this.CONTENT_PACKAGE_DELAY_TIME);
                }
                i4++;
            }
            sleep(this.CONTENT_GROUP_DELAY_TIME);
            count++;
        }
    }

    private byte[] genPackageContents(int capacity) {
        byte[] data = new byte[capacity];
        for (int i = 0; i < capacity; i++) {
            data[i] = 5;
        }
        return data;
    }

    private void send(byte[] data) {
        try {
            this.mSocket.send(new DatagramPacket(data, data.length, this.mBroadcastInetAddress, this.port));
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
