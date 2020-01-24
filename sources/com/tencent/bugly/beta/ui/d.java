package com.tencent.bugly.beta.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.beta.global.a;
import com.tencent.bugly.beta.global.c;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.proguard.an;

/* compiled from: BUGLY */
public class d implements OnPreDrawListener {
    final int a;
    final Object[] b;
    long c;
    StringBuilder d = new StringBuilder().append("loading");

    public d(int i, Object... objArr) {
        this.a = i;
        this.b = objArr;
    }

    public boolean onPreDraw() {
        int i;
        int i2;
        try {
            switch (this.a) {
                case 1:
                    h hVar = (h) this.b[0];
                    TextView textView = (TextView) this.b[1];
                    Bitmap bitmap = (Bitmap) this.b[2];
                    int intValue = ((Integer) this.b[3]).intValue();
                    int measuredWidth = textView.getMeasuredWidth();
                    int i3 = (int) (0.42857142857142855d * ((double) measuredWidth));
                    textView.setHeight(i3);
                    if (hVar.u == null) {
                        if (intValue == 2) {
                            hVar.u = a.a(bitmap, measuredWidth, i3, (float) a.a(e.E.s, 6.0f));
                        } else {
                            hVar.u = a.a(bitmap, measuredWidth, i3, (float) a.a(e.E.s, 0.0f));
                        }
                        if (hVar.u != null) {
                            textView.setText("");
                            textView.setBackgroundDrawable(hVar.u);
                            textView.getViewTreeObserver().removeOnPreDrawListener(this);
                            return true;
                        }
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.c > 300) {
                        this.c = currentTimeMillis;
                        if (this.d.length() > 9) {
                            this.d = new StringBuilder().append("loading");
                        } else {
                            this.d.append(Consts.DOT);
                        }
                        textView.setText(this.d.toString());
                    }
                    return true;
                case 2:
                    if (((Integer) this.b[2]).intValue() > 0) {
                        int intValue2 = ((Integer) this.b[0]).intValue();
                        TextView textView2 = (TextView) this.b[1];
                        int measuredWidth2 = textView2.getMeasuredWidth();
                        int measuredHeight = textView2.getMeasuredHeight();
                        int i4 = (int) (((float) (e.E.B.widthPixels * e.E.B.heightPixels)) * 0.4f);
                        if (!(measuredWidth2 == 0 || measuredHeight == 0 || measuredWidth2 * measuredHeight > i4)) {
                            this.b[2] = Integer.valueOf(0);
                            Paint paint = new Paint();
                            paint.setStyle(Style.FILL);
                            paint.setAntiAlias(true);
                            if (intValue2 == 2) {
                                i2 = 8;
                                i = 7;
                            } else {
                                i = 0;
                                i2 = 0;
                            }
                            paint.setColor(-3355444);
                            Bitmap createBitmap = Bitmap.createBitmap(measuredWidth2, measuredHeight, Config.ARGB_8888);
                            Canvas canvas = new Canvas(createBitmap);
                            RectF rectF = new RectF(0.0f, 0.0f, (float) measuredWidth2, (float) measuredHeight);
                            canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
                            paint.setColor(-1);
                            canvas.drawRoundRect(new RectF(2.0f, 2.0f, ((float) measuredWidth2) - 2.0f, ((float) measuredHeight) - 2.0f), (float) i, (float) i, paint);
                            paint.setColor(-3355444);
                            Bitmap createBitmap2 = Bitmap.createBitmap(measuredWidth2, measuredHeight, Config.ARGB_8888);
                            new Canvas(createBitmap2).drawRoundRect(rectF, (float) i2, (float) i2, paint);
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
                            BitmapDrawable bitmapDrawable2 = new BitmapDrawable(createBitmap2);
                            textView2.setBackgroundDrawable(bitmapDrawable);
                            textView2.setOnTouchListener(new c(1, bitmapDrawable2, bitmapDrawable));
                        }
                    }
                    return true;
                case 3:
                    ViewGroup viewGroup = (ViewGroup) this.b[0];
                    if (viewGroup.getMeasuredHeight() > a.a((Context) this.b[1], 158.0f)) {
                        LayoutParams layoutParams = viewGroup.getLayoutParams();
                        layoutParams.height = a.a((Context) this.b[1], 200.0f);
                        viewGroup.setLayoutParams(layoutParams);
                    }
                    return true;
            }
        } catch (Exception e) {
            if (!an.b(e)) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        return false;
    }
}
