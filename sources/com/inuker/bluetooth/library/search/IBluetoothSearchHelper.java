package com.inuker.bluetooth.library.search;

import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;

public interface IBluetoothSearchHelper {
    void startSearch(BluetoothSearchRequest bluetoothSearchRequest, BluetoothSearchResponse bluetoothSearchResponse);

    void stopSearch();
}
