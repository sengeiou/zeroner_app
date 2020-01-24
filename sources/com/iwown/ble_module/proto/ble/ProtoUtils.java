package com.iwown.ble_module.proto.ble;

import com.google.common.primitives.UnsignedBytes;
import com.iwown.ble_module.model.ProtoBufLogBean;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.Arrays;

public class ProtoUtils {
    private static boolean isDataOver = false;
    private static int length = 0;
    private static byte[] newByte = new byte[0];

    public static void writeLog(byte[] data, int downOrUp) {
        ProtoBufLogBean bean = new ProtoBufLogBean();
        if (downOrUp == 1) {
            byte[] log = getLog(data);
            if (log != null && log.length > 0) {
                int id = Util.bytesToInt(Arrays.copyOfRange(log, 6, 8));
                byte[] dat = Arrays.copyOfRange(log, 8, log.length);
                bean.setId(id + "");
                bean.setName("down");
                bean.setSentence(Util.bytesToString(dat, false));
                String s = JsonTool.toJson(bean);
                L.writeLog("ProtoBufLog", s);
                KLog.d(s);
                return;
            }
            return;
        }
        int id2 = Util.bytesToInt(Arrays.copyOfRange(data, 6, 8));
        byte[] dat2 = Arrays.copyOfRange(data, 8, data.length);
        bean.setId(id2 + "");
        bean.setName("up");
        bean.setSentence(Util.bytesToString(dat2, false));
        String s2 = JsonTool.toJson(bean);
        L.writeLog("ProtoBufLog", s2);
        KLog.d(s2);
    }

    private static byte[] getLog(byte[] receiverData) {
        if (receiverData[0] == 68 && receiverData[1] == 84) {
            length = ((receiverData[3] & UnsignedBytes.MAX_VALUE) << 8) | (receiverData[2] & UnsignedBytes.MAX_VALUE);
            if (receiverData.length - 8 >= length) {
                isDataOver = true;
                newByte = Arrays.copyOfRange(receiverData, 0, receiverData.length);
                return newByte;
            }
            isDataOver = false;
            newByte = Arrays.copyOfRange(receiverData, 0, receiverData.length);
        } else if (!isDataOver) {
            newByte = Util.concat(newByte, receiverData);
            if (newByte.length - 8 == length) {
                return newByte;
            }
        } else {
            newByte = new byte[0];
        }
        return new byte[0];
    }
}
