package com.iwown.my_module.useractivity.profile.ninegrid;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import java.util.List;

public class NineGridlayout extends ViewGroup {
    private static final String TAG = "NineGridlayout";
    private int columns;
    private int gap = 5;
    private List listData;
    private int rows;
    private int totalWidth;

    public NineGridlayout(Context context) {
        super(context);
    }

    public NineGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ScreenTools screenTools = ScreenTools.instance(getContext());
        this.totalWidth = screenTools.getScreenWidth() - screenTools.dip2px(80);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    private void layoutChildrenView() {
        int childrenCount = this.listData.size();
        Log.i(TAG, String.format("layoutChildrenView %d", new Object[]{Integer.valueOf(childrenCount)}));
        int singleWidth = (this.totalWidth - (this.gap * 2)) / 3;
        int singleHeight = singleWidth;
        LayoutParams params = getLayoutParams();
        params.height = (this.rows * singleHeight) + (this.gap * (this.rows - 1));
        setLayoutParams(params);
        for (int i = 0; i < childrenCount; i++) {
            CustomImageView childrenView = (CustomImageView) getChildAt(i);
            childrenView.setImageResource(((Image) this.listData.get(i)).getResId());
            Log.i(TAG, String.format("res %d", new Object[]{Integer.valueOf(((Image) this.listData.get(i)).getResId())}));
            int[] position = findPosition(i);
            int left = (this.gap + singleWidth) * position[1];
            int top = (this.gap + singleHeight) * position[0];
            childrenView.layout(left, top, left + singleWidth, top + singleHeight);
        }
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < this.rows; i++) {
            int j = 0;
            while (true) {
                if (j >= this.columns) {
                    break;
                } else if ((this.columns * i) + j == childNum) {
                    position[0] = i;
                    position[1] = j;
                    break;
                } else {
                    j++;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return this.gap;
    }

    public void setGap(int gap2) {
        this.gap = gap2;
    }

    public void setImagesData(List<Image> lists) {
        if (lists != null && !lists.isEmpty()) {
            generateChildrenLayout(lists.size());
            if (this.listData == null) {
                for (int i = 0; i < lists.size(); i++) {
                    addView(generateImageView(), generateDefaultLayoutParams());
                }
            } else {
                int oldViewCount = this.listData.size();
                int newViewCount = lists.size();
                if (oldViewCount > newViewCount) {
                    removeViews(newViewCount - 1, oldViewCount - newViewCount);
                } else if (oldViewCount < newViewCount) {
                    for (int i2 = 0; i2 < newViewCount - oldViewCount; i2++) {
                        addView(generateImageView(), generateDefaultLayoutParams());
                    }
                }
            }
            this.listData = lists;
            layoutChildrenView();
        }
    }

    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            this.rows = 1;
            this.columns = length;
        } else if (length <= 6) {
            this.rows = 2;
            this.columns = 3;
            if (length == 4) {
                this.columns = 2;
            }
        } else {
            this.rows = 3;
            this.columns = 3;
        }
    }

    private CustomImageView generateImageView() {
        CustomImageView iv = new CustomImageView(getContext());
        iv.setScaleType(ScaleType.CENTER_CROP);
        iv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        iv.setBackgroundColor(Color.parseColor("#f5f5f5"));
        return iv;
    }
}
