package com.iwown.sport_module.ui.af.presenter;

import android.util.SparseArray;
import java.util.List;

public interface IAFListener {
    void showAfData(List<Integer> list);

    void showAfDataByHour(SparseArray<List<Integer>> sparseArray);

    void showAfResult(int i);
}
