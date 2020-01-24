package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class ChevronDownShapeRenderer implements IShapeRenderer {
    public void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler, float posX, float posY, Paint renderPaint) {
        float shapeHalf = dataSet.getScatterShapeSize() / 2.0f;
        renderPaint.setStyle(Style.STROKE);
        renderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
        c.drawLine(posX, posY + (2.0f * shapeHalf), posX + (2.0f * shapeHalf), posY, renderPaint);
        c.drawLine(posX, posY + (2.0f * shapeHalf), posX - (2.0f * shapeHalf), posY, renderPaint);
    }
}
