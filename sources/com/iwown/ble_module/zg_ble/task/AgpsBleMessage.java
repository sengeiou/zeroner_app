package com.iwown.ble_module.zg_ble.task;

import java.util.ArrayList;
import java.util.List;

public class AgpsBleMessage extends BleMessage {
    public AgpsBleMessage(List<byte[]> bytes) {
        this.bytes = bytes;
    }

    public AgpsBleMessage(byte[] bytes) {
        this.bytes = new ArrayList();
        this.bytes.add(bytes);
    }
}
