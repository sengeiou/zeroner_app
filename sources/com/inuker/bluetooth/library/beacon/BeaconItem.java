package com.inuker.bluetooth.library.beacon;

import com.google.common.primitives.UnsignedBytes;
import com.inuker.bluetooth.library.utils.ByteUtils;

public class BeaconItem {
    public byte[] bytes;
    public int len;
    public int type;

    public String toString() {
        String format;
        String str = "";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("@Len = %02X, @Type = 0x%02X", new Object[]{Integer.valueOf(this.len), Integer.valueOf(this.type)}));
        switch (this.type) {
            case 8:
            case 9:
                format = "%c";
                break;
            default:
                format = "%02X ";
                break;
        }
        sb.append(" -> ");
        StringBuilder sbSub = new StringBuilder();
        try {
            for (byte b : this.bytes) {
                sbSub.append(String.format(format, new Object[]{Integer.valueOf(b & UnsignedBytes.MAX_VALUE)}));
            }
            sb.append(sbSub.toString());
        } catch (Exception e) {
            sb.append(ByteUtils.byteToString(this.bytes));
        }
        return sb.toString();
    }
}
