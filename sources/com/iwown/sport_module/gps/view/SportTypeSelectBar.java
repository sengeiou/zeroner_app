package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.iwown.sport_module.R;
import java.util.ArrayList;
import java.util.List;

public class SportTypeSelectBar extends LinearLayout {
    private Context context;
    Handler handler = new Handler(Looper.getMainLooper());
    public boolean isAnimating = false;
    /* access modifiers changed from: private */
    public OnClickListener listener;
    public AnimationSet mAnimationSet = null;
    private int mItem_layout_res;
    private int mTypeNum;
    List<View> views = new ArrayList();

    public interface OnClickListener {
        void onClick(int i);
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    public void setAnimating(boolean animating) {
        this.isAnimating = animating;
    }

    public AnimationSet getAnimationSet() {
        return this.mAnimationSet;
    }

    public void setAnimationSet(AnimationSet animationSet) {
        this.mAnimationSet = animationSet;
    }

    public SportTypeSelectBar(Context context2) {
        super(context2);
    }

    public SportTypeSelectBar(Context context2, @Nullable AttributeSet attrs) {
        super(context2, attrs);
        init(context2, attrs);
    }

    private void init(Context context2, AttributeSet attrs) {
        this.context = context2;
        setOrientation(0);
        TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.sport_type_select_bar);
        this.mTypeNum = a.getInt(R.styleable.sport_type_select_bar_type_num, 4);
        this.mItem_layout_res = a.getResourceId(R.styleable.sport_type_select_bar_item_layout, -1);
        a.recycle();
    }

    public void addAllTabs() {
        for (int i = 0; i < this.mTypeNum; i++) {
            View view = View.inflate(this.context, this.mItem_layout_res, null);
            addView(view);
            this.views.add(view);
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.width = 0;
            params.weight = 1.0f;
            params.height = -2;
            view.setLayoutParams(params);
        }
    }

    public void setViewScaleFromCenter(int itemIndex, final float scale) {
        if (itemIndex >= 0 && itemIndex < this.views.size()) {
            final View view = (View) this.views.get(itemIndex);
            view.post(new Runnable() {
                public void run() {
                    view.setPivotX((float) (view.getWidth() / 2));
                    view.setPivotY((float) (view.getHeight() / 2));
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            });
        }
    }

    public void setViewAlpha(int itemIndex, float alpha) {
        if (itemIndex >= 0 && itemIndex < this.views.size()) {
            ((View) this.views.get(itemIndex)).setAlpha(alpha);
        }
    }

    public void setImgRes(int[] imgResId, int itemImgResId) {
        if (imgResId != null) {
            int set_num = Math.min(imgResId.length, this.views.size());
            for (int i = 0; i < set_num; i++) {
                final ImageView img = (ImageView) ((View) this.views.get(i)).findViewById(itemImgResId);
                img.setImageResource(imgResId[i]);
                img.setTag(Integer.valueOf(i));
                img.setOnClickListener(new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        if (SportTypeSelectBar.this.listener != null) {
                            SportTypeSelectBar.this.listener.onClick(((Integer) img.getTag()).intValue());
                        }
                    }
                });
            }
        }
    }

    public void setOnItemclickListener(OnClickListener listener2) {
        this.listener = listener2;
    }
}
