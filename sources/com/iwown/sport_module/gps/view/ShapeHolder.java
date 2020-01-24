package com.iwown.sport_module.gps.view;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

public class ShapeHolder {
    private float alpha = 1.0f;
    private int color;
    private Paint paint;
    private ShapeDrawable shape;
    private float x = 0.0f;
    private float y = 0.0f;

    public void setPaint(Paint value) {
        this.paint = value;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void setX(float value) {
        this.x = value;
    }

    public float getX() {
        return this.x;
    }

    public void setY(float value) {
        this.y = value;
    }

    public float getY() {
        return this.y;
    }

    public void setShape(ShapeDrawable value) {
        this.shape = value;
    }

    public ShapeDrawable getShape() {
        return this.shape;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int value) {
        this.shape.getPaint().setColor(value);
        this.color = value;
    }

    public void setAlpha(float alpha2) {
        this.alpha = alpha2;
        this.shape.setAlpha((int) ((255.0f * alpha2) + 0.5f));
    }

    public float getWidth() {
        return this.shape.getShape().getWidth();
    }

    public void setWidth(float width) {
        Shape s = this.shape.getShape();
        s.resize(width, s.getHeight());
    }

    public float getHeight() {
        return this.shape.getShape().getHeight();
    }

    public void setHeight(float height) {
        Shape s = this.shape.getShape();
        s.resize(s.getWidth(), height);
    }

    public void resizeShape(float width, float height) {
        this.shape.getShape().resize(width, height);
    }

    public ShapeHolder(ShapeDrawable s) {
        this.shape = s;
    }
}
