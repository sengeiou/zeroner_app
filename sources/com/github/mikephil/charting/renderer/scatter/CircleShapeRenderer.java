package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CircleShapeRenderer implements IShapeRenderer {
    public void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler, float posX, float posY, Paint renderPaint) {
        float shapeSize = dataSet.getScatterShapeSize();
        float shapeHalf = shapeSize / 2.0f;
        float shapeHoleSizeHalf = Utils.convertDpToPixel(dataSet.getScatterShapeHoleRadius());
        float shapeStrokeSize = (shapeSize - (shapeHoleSizeHalf * 2.0f)) / 2.0f;
        float shapeStrokeSizeHalf = shapeStrokeSize / 2.0f;
        int shapeHoleColor = dataSet.getScatterShapeHoleColor();
        if (((double) shapeSize) > Utils.DOUBLE_EPSILON) {
            renderPaint.setStyle(Style.STROKE);
            renderPaint.setStrokeWidth(shapeStrokeSize);
            c.drawCircle(posX, posY, shapeHoleSizeHalf + shapeStrokeSizeHalf, renderPaint);
            if (shapeHoleColor != 1122867) {
                renderPaint.setStyle(Style.FILL);
                renderPaint.setColor(shapeHoleColor);
                c.drawCircle(posX, posY, shapeHoleSizeHalf, renderPaint);
                return;
            }
            return;
        }
        renderPaint.setStyle(Style.FILL);
        c.drawCircle(posX, posY, shapeHalf, renderPaint);
    }
}
