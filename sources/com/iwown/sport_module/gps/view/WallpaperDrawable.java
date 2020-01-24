package com.iwown.sport_module.gps.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class WallpaperDrawable extends Drawable {
    Bitmap mBitmap;
    int mIntrinsicHeight;
    int mIntrinsicWidth;

    public void setBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        this.mBitmap = bitmap;
        if (this.mBitmap != null) {
            this.mIntrinsicWidth = this.mBitmap.getWidth();
            this.mIntrinsicHeight = this.mBitmap.getHeight();
        }
    }

    public void draw(Canvas canvas) {
        if (this.mBitmap != null) {
            int width = canvas.getWidth();
            canvas.drawBitmap(this.mBitmap, (float) ((width - this.mIntrinsicWidth) / 2), (float) ((canvas.getHeight() - this.mIntrinsicHeight) / 2), null);
        }
    }

    public int getOpacity() {
        return -1;
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }
}
