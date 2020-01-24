package com.iwown.ble_module.zg_ble.task;

import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.ArrayList;
import java.util.List;

public class BleMessage {
    public List<byte[]> bytes;

    public BleMessage() {
    }

    public BleMessage(List<byte[]> bytes2) {
        this.bytes = bytes2;
    }

    public BleMessage(byte[] bytes2) {
        this.bytes = new ArrayList();
        this.bytes.add(bytes2);
    }

    public String toString() {
        if (this.bytes == null) {
            return "bytes is null waitTask";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.bytes.size(); i++) {
            builder.append(ByteUtil.bytesToString1((byte[]) this.bytes.get(i)));
            builder.append("\n");
        }
        return "" + builder.toString();
    }
}
