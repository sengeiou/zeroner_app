package com.iwown.ble_module.proto.ble;

import java.util.UUID;

public class Constants {
    public static final int ProtoBuf_SDK_TYPE = 4;

    public static class ProtoBufUUID {
        public static final UUID BAND_CHARACT_NOTIFY_UUID = UUID.fromString("2e8c0002-2d91-5533-3117-59380a40af8f");
        public static final UUID BAND_CHARACT_WRITE_UUID = UUID.fromString("2e8c0003-2d91-5533-3117-59380a40af8f");
        public static final UUID BAND_DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_PROTO_DFU_CHARACT_UUID = UUID.fromString("8ec90003-f315-4f60-9fb8-838830daea50");
        public static final UUID BAND_PROTO_DFU_SERVICE_UUID = UUID.fromString("0000fe59-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_SERVICE_MAIN_UUID = UUID.fromString("2E8C0001-2D91-5533-3117-59380A40AF8F");
    }
}
