package com.iwown.lib_common.views.fatigueview2;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FatigueRecyclerView extends RelativeLayout {
    /* access modifiers changed from: private */
    public long begin_time_200;
    CallBack callBack;
    /* access modifiers changed from: private */
    public int centerPosition;
    /* access modifiers changed from: private */
    public FatigueAdapter fatigueAdapter;
    /* access modifiers changed from: private */
    public ArrayList<FatigueDataBean2> fatigueDataBean2List;
    /* access modifiers changed from: private */
    public int otherSize;
    private RecyclerView rv_charts;

    public interface CallBack {
        void onCenterPosition(FatigueDataBean2 fatigueDataBean2);

        void onStartPosition();
    }

    public FatigueRecyclerView(Context context) {
        this(context, null);
    }

    public FatigueRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FatigueRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.centerPosition = -1;
        initView(context);
    }

    public void setCallBack(CallBack callBack2) {
        this.callBack = callBack2;
    }

    @RequiresApi(api = 21)
    public FatigueRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.centerPosition = -1;
        initView(context);
    }

    private void initView(Context context) {
        this.rv_charts = (RecyclerView) inflate(context, R.layout.lib_common_view_fatigue2, this).findViewById(R.id.rv_charts);
        this.fatigueDataBean2List = new ArrayList<>();
        this.fatigueAdapter = new FatigueAdapter(this.fatigueDataBean2List);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        new LinearSnapHelper().attachToRecyclerView(this.rv_charts);
        this.rv_charts.setLayoutManager(linearLayoutManager);
        this.rv_charts.setAdapter(this.fatigueAdapter);
        this.rv_charts.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int centerPosition1;
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    int size = FatigueRecyclerView.this.fatigueDataBean2List.size();
                    int last = linearLayoutManager.findLastVisibleItemPosition();
                    int first = linearLayoutManager.findFirstVisibleItemPosition();
                    boolean isBug = (last + first) % 2 != 0;
                    int avg = isBug ? ((last + first) / 2) + 1 : (last + first) / 2;
                    if (avg < 0 || avg > size - 1) {
                        centerPosition1 = first + FatigueRecyclerView.this.otherSize;
                    } else {
                        centerPosition1 = avg;
                        if (isBug && first == 0) {
                            centerPosition1 = first + FatigueRecyclerView.this.otherSize;
                        }
                    }
                    if (FatigueRecyclerView.this.centerPosition != centerPosition1) {
                        FatigueRecyclerView.this.fatigueAdapter.setSelectDate(((FatigueDataBean2) FatigueRecyclerView.this.fatigueDataBean2List.get(centerPosition1)).tag);
                        if (FatigueRecyclerView.this.centerPosition == -1) {
                            FatigueRecyclerView.this.fatigueAdapter.notifyItemChanged((FatigueRecyclerView.this.fatigueDataBean2List.size() - 1) - FatigueRecyclerView.this.otherSize);
                        } else {
                            FatigueRecyclerView.this.fatigueAdapter.notifyItemChanged(FatigueRecyclerView.this.centerPosition);
                        }
                        FatigueRecyclerView.this.centerPosition = centerPosition1;
                        FatigueRecyclerView.this.begin_time_200 = SystemClock.currentThreadTimeMillis();
                        int index = 0;
                        Iterator it = FatigueRecyclerView.this.fatigueDataBean2List.iterator();
                        while (it.hasNext()) {
                            FatigueDataBean2 fatigueDataBean2 = (FatigueDataBean2) it.next();
                            fatigueDataBean2.hightLight = false;
                            if (index == FatigueRecyclerView.this.centerPosition) {
                                fatigueDataBean2.hightLight = true;
                            }
                            index++;
                        }
                        FatigueRecyclerView.this.fatigueAdapter.notifyItemChanged(FatigueRecyclerView.this.centerPosition);
                        FatigueRecyclerView.this.callBackPosition(FatigueRecyclerView.this.centerPosition);
                    }
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /* access modifiers changed from: private */
    public void callBackPosition(int centerPosition1) {
        if (this.callBack != null) {
            try {
                this.callBack.onCenterPosition((FatigueDataBean2) this.fatigueDataBean2List.get(centerPosition1));
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        if (centerPosition1 == this.otherSize && this.callBack != null) {
            this.callBack.onStartPosition();
        }
    }

    public void setData(List<FatigueDataBean2> dataBean2s) {
        if (dataBean2s != null && dataBean2s.size() != 0) {
            float i = ((1.0f * ((float) DensityUtil.getScreenWidth(getContext()))) / 2.0f) / ((float) DensityUtil.dip2px(getContext(), 75.0f));
            KLog.e("两边多放几个 " + i);
            this.otherSize = (int) i;
            ((FatigueDataBean2) dataBean2s.get(dataBean2s.size() - 1)).hightLight = true;
            boolean temp_callback = false;
            KLog.e(" --- " + dataBean2s.size());
            if (dataBean2s.size() == 1) {
                temp_callback = true;
            }
            for (int j = 0; j < this.otherSize; j++) {
                dataBean2s.add(new FatigueDataBean2(-1, -1, "", ""));
            }
            for (int j2 = 0; j2 < this.otherSize; j2++) {
                dataBean2s.add(0, new FatigueDataBean2(-1, -1, "", ""));
            }
            this.fatigueDataBean2List.addAll(0, dataBean2s);
            this.fatigueAdapter.notifyDataSetChanged();
            KLog.e(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((dataBean2s.size() - 1) - this.otherSize));
            this.fatigueAdapter.setSelectDate(((FatigueDataBean2) this.fatigueDataBean2List.get((dataBean2s.size() - 1) - this.otherSize)).tag);
            this.rv_charts.scrollToPosition((dataBean2s.size() - 1) - this.otherSize);
            if (temp_callback) {
                callBackPosition((dataBean2s.size() - 1) - this.otherSize);
            }
        }
    }

    public void addBeginDatas(List<FatigueDataBean2> dataBean2s) {
        Iterator<FatigueDataBean2> iterator = this.fatigueDataBean2List.iterator();
        int index = 0;
        while (iterator.hasNext() && index != 2) {
            iterator.next();
            iterator.remove();
            index++;
        }
        for (int j = 0; j < this.otherSize; j++) {
            dataBean2s.add(0, new FatigueDataBean2(-1, -1, "", ""));
        }
        ((FatigueDataBean2) dataBean2s.get(dataBean2s.size() - 1)).right_data = (FatigueDataBean2) this.fatigueDataBean2List.get(0);
        ((FatigueDataBean2) this.fatigueDataBean2List.get(0)).left_data = (FatigueDataBean2) dataBean2s.get(dataBean2s.size() - 1);
        this.fatigueDataBean2List.addAll(0, dataBean2s);
        this.fatigueAdapter.notifyDataSetChanged();
        this.rv_charts.scrollToPosition((dataBean2s.size() + this.otherSize) - 1);
    }
}
