package com.iwown.my_module.useractivity.profile.ninegrid;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class CustomImageView extends AppCompatImageView {
    private static final String TAG = "CustomImageView";
    private boolean isAttachedToWindow;
    private int resId;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    drawable.mutate().setColorFilter(-7829368, Mode.MULTIPLY);
                    break;
                }
                break;
            case 1:
            case 3:
                Drawable drawableUp = getDrawable();
                if (drawableUp != null) {
                    drawableUp.mutate().clearColorFilter();
                    break;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void onAttachedToWindow() {
        Log.i(TAG, "onAttachedToWindow");
        this.isAttachedToWindow = true;
        setImageResource(this.resId);
        super.onAttachedToWindow();
    }

    public void onDetachedFromWindow() {
        Picasso.with(getContext()).cancelRequest((ImageView) this);
        this.isAttachedToWindow = false;
        setImageBitmap(null);
        super.onDetachedFromWindow();
    }

    public void setImageResource(int resourceId) {
        Log.i(TAG, String.format("setImageResource, isAttachedToWindow:%s, resid : %d", new Object[]{String.valueOf(this.isAttachedToWindow), Integer.valueOf(resourceId)}));
        this.resId = resourceId;
        if (this.isAttachedToWindow && resourceId != 0) {
            Picasso.with(getContext()).load(resourceId).into((ImageView) this);
            Log.i(TAG, String.format("load img resource %d", new Object[]{Integer.valueOf(resourceId)}));
        }
    }
}
