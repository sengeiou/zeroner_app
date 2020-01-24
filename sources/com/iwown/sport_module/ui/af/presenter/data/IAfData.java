package com.iwown.sport_module.ui.af.presenter.data;

import android.util.SparseArray;
import java.util.List;

public interface IAfData {
    SparseArray<List<Integer>> getDataByHour(String str);

    List<Integer> getRealData(String str);

    void onDestroy();
}
