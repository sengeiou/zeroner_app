package com.peng.ppscalelibrary.BleManager.Interface;

import com.peng.ppscalelibrary.BleManager.Model.BleConnectFliterModel;
import java.util.UUID;

public interface BleConnectFliterInterface {
    void target2Write(UUID uuid, UUID uuid2, BleConnectFliterModel bleConnectFliterModel);

    void targetResponse(UUID uuid, UUID uuid2, BleConnectFliterModel bleConnectFliterModel);
}
