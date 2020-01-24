package com.inuker.bluetooth.library.search;

import android.os.Bundle;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;

public class BluetoothSearchManager {
    public static void search(SearchRequest request, final BleGeneralResponse response) {
        BluetoothSearchHelper.getInstance().startSearch(new BluetoothSearchRequest(request), new BluetoothSearchResponse() {
            public void onSearchStarted() {
                response.onResponse(1, null);
            }

            public void onDeviceFounded(SearchResult device) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.EXTRA_SEARCH_RESULT, device);
                response.onResponse(4, bundle);
            }

            public void onSearchStopped() {
                response.onResponse(2, null);
            }

            public void onSearchCanceled() {
                response.onResponse(3, null);
            }
        });
    }

    public static void stopSearch() {
        BluetoothSearchHelper.getInstance().stopSearch();
    }
}
