package com.inuker.bluetooth.library.search.response;

import com.inuker.bluetooth.library.search.SearchResult;

public interface BluetoothSearchResponse {
    void onDeviceFounded(SearchResult searchResult);

    void onSearchCanceled();

    void onSearchStarted();

    void onSearchStopped();
}
