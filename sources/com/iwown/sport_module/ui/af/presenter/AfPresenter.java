package com.iwown.sport_module.ui.af.presenter;

import android.content.Context;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.iwown.data_link.af.ModuleRouterRRIService;
import com.iwown.sport_module.ui.af.presenter.chart.ChartImpl;
import com.iwown.sport_module.ui.af.presenter.chart.IChart;
import com.iwown.sport_module.ui.af.presenter.data.AfDataImpl;
import com.iwown.sport_module.ui.af.presenter.data.IAfData;
import com.iwown.sport_module.ui.af.presenter.net.INet;
import com.iwown.sport_module.ui.af.presenter.net.INet.INetListener;
import com.iwown.sport_module.ui.af.presenter.net.NetImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AfPresenter implements INetListener {
    private IChart chart;
    private IAfData iAfData;
    private INet iNet;
    private IAFListener listener;

    public AfPresenter(Context context, long uid, String dataFrom, ScatterChart scatterChart, IAFListener listener2) {
        this.listener = listener2;
        this.chart = new ChartImpl(context);
        this.iAfData = new AfDataImpl(uid, dataFrom);
        this.iNet = new NetImpl(this, uid, dataFrom);
        initData(scatterChart);
    }

    public AfPresenter(long uid, String dataFrom) {
        this.iAfData = new AfDataImpl(uid, dataFrom);
        this.iNet = new NetImpl(this, uid, dataFrom);
    }

    private void initData(ScatterChart scatterChart) {
        this.chart.showChat(scatterChart);
    }

    public void showData(String date) {
        refreshAfData(date);
    }

    public void updateAfResult(String date) {
        this.iNet.getAndUpdateResult(date, 0, this.iAfData.getRealData(date));
    }

    private void refreshAfData(String date) {
        List<Integer> realData = this.iAfData.getRealData(date);
        if (this.listener != null) {
            this.listener.showAfData(realData);
        }
        this.chart.setData(afDisp(realData));
    }

    private List<Entry> afDisp(List<Integer> realData) {
        if (realData.size() == 0) {
            return new ArrayList();
        }
        List<int[]> afList = new ArrayList<>();
        for (int i = 0; i < realData.size(); i += 2) {
            afList.add(new int[]{((Integer) realData.get(i)).intValue(), ((Integer) realData.get(i + 1)).intValue()});
        }
        List<Entry> entries = new ArrayList<>();
        for (int i2 = 0; i2 < afList.size(); i2++) {
            Entry entry = new Entry();
            if (((int[]) afList.get(i2))[0] <= 2000 && ((int[]) afList.get(i2))[0] >= 200 && ((int[]) afList.get(i2))[1] <= 2000 && ((int[]) afList.get(i2))[1] >= 200) {
                entry.setX((float) ((int[]) afList.get(i2))[0]);
                entry.setY((float) ((int[]) afList.get(i2 + 1))[1]);
                entries.add(entry);
            }
        }
        Collections.sort(entries, new Comparator<Entry>() {
            public int compare(Entry entry1, Entry entry2) {
                float x1 = entry1.getX();
                float x2 = entry2.getX();
                if (x1 > x2) {
                    return 1;
                }
                if (x1 == x2) {
                    return 0;
                }
                return -1;
            }
        });
        return entries;
    }

    public void onDestroy() {
        this.chart.onDestroy();
        this.iAfData.onDestroy();
    }

    public void getAfResult(String date, int hour, List<Integer> realData) {
        this.iNet.getAfResult(date, hour, realData);
    }

    public void onAfResult(int afResult) {
        if (this.listener != null) {
            this.listener.showAfResult(afResult);
        }
    }

    private String getDataFrom(long uid, String dataFrom) {
        return ModuleRouterRRIService.getInstance().getRRIDataFrom(uid, dataFrom);
    }
}
