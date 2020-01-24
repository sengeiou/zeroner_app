package coms.mediatek.wearable;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.HashSet;

public abstract class Controller {
    protected static final int CMD_1 = 1;
    protected static final int CMD_2 = 2;
    protected static final int CMD_3 = 3;
    protected static final int CMD_4 = 4;
    protected static final int CMD_5 = 5;
    protected static final int CMD_6 = 6;
    protected static final int CMD_7 = 7;
    protected static final int CMD_8 = 8;
    protected static final int CMD_9 = 9;
    protected static final int DATA_TYPE_BUFFER = 0;
    protected static final int DATA_TYPE_FILE = 1;
    protected static final int MSG_ON_RECEIVE = 9002;
    protected static final int MSG_ON_RESPONSE = 9001;
    protected static final int MSG_ON_TIME_OUT = 9003;
    protected static final int PRIORITY_HIGH = 2;
    protected static final int PRIORITY_LOW = 1;
    protected static final int PRIORITY_NORMAL = 0;
    private String a = null;
    private int b = 0;
    private HashSet<String> c = new HashSet<>();
    protected final Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case Controller.MSG_ON_RECEIVE /*9002*/:
                    Controller.this.onReceive((byte[]) message.obj);
                    return;
                default:
                    return;
            }
        }
    };

    protected Controller(String str, int i) {
        this.a = str;
        this.b = i;
    }

    private byte[] a(int i, String str) {
        return LoadJniFunction.a().a(i, str);
    }

    public int getCmdType() {
        return this.b;
    }

    public String getControllerTag() {
        return this.a;
    }

    public HashSet<String> getReceiverTags() {
        return this.c;
    }

    public void init() {
    }

    /* access modifiers changed from: protected */
    public void onConnectionStateChange(int i) {
    }

    /* access modifiers changed from: protected */
    public void onProgress(float f) {
    }

    /* access modifiers changed from: protected */
    public void onReceive(byte[] bArr) {
    }

    public void send(String str, String str2, int i, byte[] bArr, boolean z, int i2) throws Exception {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new Exception("Controller sender/receiver/action invalid.");
        }
        Log.d("[wearable]Controller", "send, senderCMD=" + str + ", receiverCMD=" + str2 + ", action=" + i);
        int i3 = bArr != null ? bArr.length : 0;
        StringBuilder sb = new StringBuilder();
        sb.append(str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        sb.append(str2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        sb.append(i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        sb.append("0 ");
        sb.append(i3 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        send(sb.toString(), bArr, false, z, i2);
    }

    public void send(String str, byte[] bArr, boolean z, boolean z2, int i) throws Exception {
        if (this.b < 1 || this.b > 9) {
            throw new Exception("invalid mCmdType, please set valid mCmdType(1-9) via setCmdType(int)");
        }
        Log.d("[wearable]Controller", "send, mCmdType=" + this.b + ", cmd=" + str + ", response=" + z);
        i iVar = new i(this.a, z, z2);
        iVar.b(i);
        if (str != null) {
            iVar.a(a(this.b, str));
        }
        if (bArr != null) {
            iVar.a(bArr);
        }
        j.a().a(iVar);
    }

    public void setCmdType(int i) {
        this.b = i;
    }

    public void setControllerTag(String str) {
        this.a = str;
    }

    public void setReceiverTags(HashSet<String> hashSet) {
        this.c = hashSet;
    }

    public void tearDown() {
    }
}
