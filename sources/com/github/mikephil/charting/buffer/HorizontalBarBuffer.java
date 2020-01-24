package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer extends BarBuffer {
    public HorizontalBarBuffer(int size, int dataSetCount, boolean containsStacks) {
        super(size, dataSetCount, containsStacks);
    }

    public void feed(IBarDataSet data) {
        float right;
        float left;
        float y;
        float yStart;
        float right2;
        float left2;
        float size = ((float) data.getEntryCount()) * this.phaseX;
        float barWidthHalf = this.mBarWidth / 2.0f;
        for (int i = 0; ((float) i) < size; i++) {
            BarEntry e = (BarEntry) data.getEntryForIndex(i);
            if (e != null) {
                float x = e.getX();
                float y2 = e.getY();
                float[] vals = e.getYVals();
                if (!this.mContainsStacks || vals == null) {
                    float bottom = x - barWidthHalf;
                    float top = x + barWidthHalf;
                    if (this.mInverted) {
                        left = y2 >= 0.0f ? y2 : 0.0f;
                        right = y2 <= 0.0f ? y2 : 0.0f;
                    } else {
                        right = y2 >= 0.0f ? y2 : 0.0f;
                        left = y2 <= 0.0f ? y2 : 0.0f;
                    }
                    if (right > 0.0f) {
                        right *= this.phaseY;
                    } else {
                        left *= this.phaseY;
                    }
                    addBar(left, top, right, bottom);
                } else {
                    float posY = 0.0f;
                    float negY = -e.getNegativeSum();
                    for (float value : vals) {
                        if (value >= 0.0f) {
                            y = posY;
                            yStart = posY + value;
                            posY = yStart;
                        } else {
                            y = negY;
                            yStart = negY + Math.abs(value);
                            negY += Math.abs(value);
                        }
                        float bottom2 = x - barWidthHalf;
                        float top2 = x + barWidthHalf;
                        if (this.mInverted) {
                            if (y >= yStart) {
                                left2 = y;
                            } else {
                                left2 = yStart;
                            }
                            if (y <= yStart) {
                                right2 = y;
                            } else {
                                right2 = yStart;
                            }
                        } else {
                            if (y >= yStart) {
                                right2 = y;
                            } else {
                                right2 = yStart;
                            }
                            if (y <= yStart) {
                                left2 = y;
                            } else {
                                left2 = yStart;
                            }
                        }
                        addBar(left2 * this.phaseY, top2, right2 * this.phaseY, bottom2);
                    }
                }
            }
        }
        reset();
    }
}
