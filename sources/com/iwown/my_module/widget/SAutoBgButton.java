package com.iwown.my_module.widget;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import com.iwown.my_module.R;
import com.iwown.my_module.utility.CommonUtility;

public class SAutoBgButton extends AppCompatButton {

    protected class SAutoBgButtonBackgroundDrawable extends LayerDrawable {
        protected int _disabledAlpha = 100;
        protected int _fullAlpha = 255;
        protected ColorFilter _pressedFilter = new LightingColorFilter(-3355444, 1);

        public SAutoBgButtonBackgroundDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        /* access modifiers changed from: protected */
        public boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;
            for (int state : states) {
                if (state == 16842910) {
                    enabled = true;
                } else if (state == 16842919) {
                    pressed = true;
                }
            }
            mutate();
            if (enabled && pressed) {
                setColorFilter(this._pressedFilter);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(this._disabledAlpha);
            } else {
                setColorFilter(null);
                setAlpha(this._fullAlpha);
            }
            invalidateSelf();
            return super.onStateChange(states);
        }

        public boolean isStateful() {
            return true;
        }
    }

    public SAutoBgButton(Context context) {
        super(context);
    }

    public SAutoBgButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SAutoBgButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setBackgroundDrawable(Drawable d) {
        RoundImageDrawable drawable2 = new RoundImageDrawable();
        drawable2.setColor(getContext().getResources().getColor(R.color.dark_theme_button_background_color));
        drawable2.setStyle(Style.FILL);
        drawable2.setRadioX((float) CommonUtility.dip2px(getContext(), 5.0f));
        drawable2.setRadioY((float) CommonUtility.dip2px(getContext(), 5.0f));
        super.setBackgroundDrawable(drawable2);
    }
}
