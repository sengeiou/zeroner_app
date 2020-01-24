package com.github.mikephil.charting.components;

import android.graphics.Paint.Align;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class Description extends ComponentBase {
    private MPPointF mPosition;
    private Align mTextAlign;
    private String text;

    public Description() {
        this.text = "Description Label";
        this.mTextAlign = Align.RIGHT;
        this.mTextSize = Utils.convertDpToPixel(8.0f);
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public String getText() {
        return this.text;
    }

    public void setPosition(float x, float y) {
        if (this.mPosition == null) {
            this.mPosition = MPPointF.getInstance(x, y);
            return;
        }
        this.mPosition.x = x;
        this.mPosition.y = y;
    }

    public MPPointF getPosition() {
        return this.mPosition;
    }

    public void setTextAlign(Align align) {
        this.mTextAlign = align;
    }

    public Align getTextAlign() {
        return this.mTextAlign;
    }
}
