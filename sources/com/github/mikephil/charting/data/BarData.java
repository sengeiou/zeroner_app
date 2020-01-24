package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.List;

public class BarData extends BarLineScatterCandleBubbleData<IBarDataSet> {
    private float mBarWidth = 0.85f;

    public BarData() {
    }

    public BarData(IBarDataSet... dataSets) {
        super((T[]) dataSets);
    }

    public BarData(List<IBarDataSet> dataSets) {
        super(dataSets);
    }

    public void setBarWidth(float mBarWidth2) {
        this.mBarWidth = mBarWidth2;
    }

    public float getBarWidth() {
        return this.mBarWidth;
    }

    public void groupBars(float fromX, float groupSpace, float barSpace) {
        if (this.mDataSets.size() <= 1) {
            throw new RuntimeException("BarData needs to hold at least 2 BarDataSets to allow grouping.");
        }
        int maxEntryCount = ((IBarDataSet) getMaxEntryCountSet()).getEntryCount();
        float groupSpaceWidthHalf = groupSpace / 2.0f;
        float barSpaceHalf = barSpace / 2.0f;
        float barWidthHalf = this.mBarWidth / 2.0f;
        float interval = getGroupWidth(groupSpace, barSpace);
        for (int i = 0; i < maxEntryCount; i++) {
            float start = fromX;
            float fromX2 = fromX + groupSpaceWidthHalf;
            for (IBarDataSet set : this.mDataSets) {
                float fromX3 = fromX2 + barSpaceHalf + barWidthHalf;
                if (i < set.getEntryCount()) {
                    BarEntry entry = (BarEntry) set.getEntryForIndex(i);
                    if (entry != null) {
                        entry.setX(fromX3);
                    }
                }
                fromX2 = fromX3 + barWidthHalf + barSpaceHalf;
            }
            fromX = fromX2 + groupSpaceWidthHalf;
            float diff = interval - (fromX - start);
            if (diff > 0.0f || diff < 0.0f) {
                fromX += diff;
            }
        }
        notifyDataChanged();
    }

    public float getGroupWidth(float groupSpace, float barSpace) {
        return (((float) this.mDataSets.size()) * (this.mBarWidth + barSpace)) + groupSpace;
    }
}
