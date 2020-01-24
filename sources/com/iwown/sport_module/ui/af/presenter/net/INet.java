package com.iwown.sport_module.ui.af.presenter.net;

import android.util.SparseArray;
import java.util.List;

public interface INet {

    public interface INetListener {
        void onAfResult(int i);
    }

    void downloadAfData();

    void getAfResult(String str, int i, List<Integer> list);

    void getAndUpdateResult(String str, int i, List<Integer> list);

    void saveAfResult(String str, SparseArray<List<Integer>> sparseArray);

    void uploadAfData(String str);
}
