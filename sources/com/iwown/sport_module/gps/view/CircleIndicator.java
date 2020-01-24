package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.iwown.sport_module.R;
import java.util.ArrayList;
import java.util.List;

public class CircleIndicator extends View {
    private final int DEFAULT_INDICATOR_BACKGROUND = -16776961;
    private final int DEFAULT_INDICATOR_LAYOUT_GRAVITY = Gravity.CENTER.ordinal();
    private final int DEFAULT_INDICATOR_MARGIN = 40;
    private final int DEFAULT_INDICATOR_MODE = Mode.SOLO.ordinal();
    private final int DEFAULT_INDICATOR_RADIUS = 10;
    private final int DEFAULT_INDICATOR_SELECTED_BACKGROUND = SupportMenu.CATEGORY_MASK;
    private int mCurItemPosition;
    private float mCurItemPositionOffset;
    private int mIndicatorBackground;
    private Gravity mIndicatorLayoutGravity;
    private float mIndicatorMargin;
    /* access modifiers changed from: private */
    public Mode mIndicatorMode;
    private float mIndicatorRadius;
    private int mIndicatorSelectedBackground;
    private ShapeHolder movingItem;
    private List<ShapeHolder> tabItems;
    private ViewPager viewPager;
    private int viewPagerNum;

    public enum Gravity {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum Mode {
        INSIDE,
        OUTSIDE,
        SOLO
    }

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.tabItems = new ArrayList();
        handleTypedArray(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.gpsCircleIndicator);
            this.mIndicatorRadius = (float) typedArray.getDimensionPixelSize(R.styleable.gpsCircleIndicator_gps_ci_radius, 10);
            this.mIndicatorMargin = (float) typedArray.getDimensionPixelSize(R.styleable.gpsCircleIndicator_gps_ci_margin, 40);
            this.mIndicatorBackground = typedArray.getColor(R.styleable.gpsCircleIndicator_gps_ci_background, -16776961);
            this.mIndicatorSelectedBackground = typedArray.getColor(R.styleable.gpsCircleIndicator_gps_ci_selected_background, SupportMenu.CATEGORY_MASK);
            this.mIndicatorLayoutGravity = Gravity.values()[typedArray.getInt(R.styleable.gpsCircleIndicator_gps_ci_gravity, this.DEFAULT_INDICATOR_LAYOUT_GRAVITY)];
            this.mIndicatorMode = Mode.values()[typedArray.getInt(R.styleable.gpsCircleIndicator_gps_ci_mode, this.DEFAULT_INDICATOR_MODE)];
            typedArray.recycle();
        }
    }

    public void setViewPager(int viewPagerNum2) {
        this.viewPagerNum = viewPagerNum2;
        createTabItems();
        createMovingItem();
    }

    public void setChanging(int position, float positionOffset) {
        if (this.mIndicatorMode != Mode.SOLO) {
            trigger(position, positionOffset);
        }
    }

    public void setChangeOver(int position) {
        if (this.mIndicatorMode == Mode.SOLO) {
            trigger(position, 0.0f);
        }
    }

    private void setUpListener() {
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (CircleIndicator.this.mIndicatorMode != Mode.SOLO) {
                    CircleIndicator.this.trigger(position, positionOffset);
                }
                Log.d("testViewPager", "onPageScrolled: " + positionOffset + "  ---   " + positionOffsetPixels);
            }

            public void onPageSelected(int position) {
                Log.d("testViewPager", "onPageSelected: " + position);
                if (CircleIndicator.this.mIndicatorMode == Mode.SOLO) {
                    CircleIndicator.this.trigger(position, 0.0f);
                }
            }

            public void onPageScrollStateChanged(int state) {
                Log.d("testViewPager", "onPageScrollStateChanged: " + state);
            }
        });
    }

    /* access modifiers changed from: private */
    public void trigger(int position, float positionOffset) {
        this.mCurItemPosition = position;
        this.mCurItemPositionOffset = positionOffset;
        requestLayout();
        invalidate();
    }

    private void createTabItems() {
        for (int i = 0; i < this.viewPagerNum; i++) {
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            Paint paint = drawable.getPaint();
            paint.setColor(this.mIndicatorBackground);
            paint.setAntiAlias(true);
            shapeHolder.setPaint(paint);
            this.tabItems.add(shapeHolder);
        }
    }

    private void createMovingItem() {
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{this.mIndicatorRadius, this.mIndicatorRadius, this.mIndicatorRadius, this.mIndicatorRadius, this.mIndicatorRadius, this.mIndicatorRadius, this.mIndicatorRadius, this.mIndicatorRadius}, null, null));
        this.movingItem = new ShapeHolder(drawable);
        Paint paint = drawable.getPaint();
        paint.setColor(this.mIndicatorSelectedBackground);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        switch (this.mIndicatorMode) {
            case INSIDE:
                paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_ATOP));
                break;
            case OUTSIDE:
                paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_OVER));
                break;
            case SOLO:
                paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC));
                break;
        }
        this.movingItem.setPaint(paint);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutTabItems(getWidth(), getHeight());
        layoutMovingItem(this.mCurItemPosition, this.mCurItemPositionOffset);
    }

    private void layoutTabItems(int containerWidth, int containerHeight) {
        if (this.tabItems == null) {
            throw new IllegalStateException("forget to create tabItems?");
        }
        float yCoordinate = ((float) containerHeight) * 0.5f;
        float startPosition = startDrawPosition(containerWidth);
        for (int i = 0; i < this.tabItems.size(); i++) {
            ShapeHolder item = (ShapeHolder) this.tabItems.get(i);
            item.resizeShape(this.mIndicatorRadius * 2.0f, this.mIndicatorRadius * 2.0f);
            item.setY(yCoordinate - this.mIndicatorRadius);
            item.setX(startPosition + ((this.mIndicatorMargin + (this.mIndicatorRadius * 2.0f)) * ((float) i)));
        }
    }

    private float startDrawPosition(int containerWidth) {
        if (this.mIndicatorLayoutGravity == Gravity.LEFT) {
            return 0.0f;
        }
        float tabItemsLength = (((float) this.tabItems.size()) * ((this.mIndicatorRadius * 2.0f) + this.mIndicatorMargin)) - this.mIndicatorMargin;
        if (((float) containerWidth) < tabItemsLength) {
            return 0.0f;
        }
        if (this.mIndicatorLayoutGravity == Gravity.CENTER) {
            return (((float) containerWidth) - tabItemsLength) / 2.0f;
        }
        return ((float) containerWidth) - tabItemsLength;
    }

    private void layoutMovingItem(int position, float positionOffset) {
        if (this.movingItem == null) {
            throw new IllegalStateException("forget to create movingItem?");
        } else if (this.tabItems.size() != 0) {
            ShapeHolder item = (ShapeHolder) this.tabItems.get(position);
            this.movingItem.resizeShape(item.getWidth() * 2.0f, item.getHeight());
            this.movingItem.setX((item.getX() - this.mIndicatorRadius) + ((this.mIndicatorMargin + (this.mIndicatorRadius * 2.0f)) * positionOffset));
            this.movingItem.setY(item.getY());
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
        for (ShapeHolder item : this.tabItems) {
            drawItem(canvas, item);
        }
        if (this.movingItem != null) {
            drawItem(canvas, this.movingItem);
        }
        canvas.restoreToCount(sc);
    }

    private void drawItem(Canvas canvas, ShapeHolder shapeHolder) {
        canvas.save();
        canvas.translate(shapeHolder.getX(), shapeHolder.getY());
        shapeHolder.getShape().draw(canvas);
        canvas.restore();
    }

    public void setIndicatorRadius(float mIndicatorRadius2) {
        this.mIndicatorRadius = mIndicatorRadius2;
    }

    public void setIndicatorMargin(float mIndicatorMargin2) {
        this.mIndicatorMargin = mIndicatorMargin2;
    }

    public void setIndicatorBackground(int mIndicatorBackground2) {
        this.mIndicatorBackground = mIndicatorBackground2;
    }

    public void setIndicatorSelectedBackground(int mIndicatorSelectedBackground2) {
        this.mIndicatorSelectedBackground = mIndicatorSelectedBackground2;
    }

    public void setIndicatorLayoutGravity(Gravity mIndicatorLayoutGravity2) {
        this.mIndicatorLayoutGravity = mIndicatorLayoutGravity2;
    }

    public void setIndicatorMode(Mode mIndicatorMode2) {
        this.mIndicatorMode = mIndicatorMode2;
    }
}
