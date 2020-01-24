package com.tencent.bugly.beta.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.global.ResBean;
import com.tencent.bugly.beta.global.c;
import com.tencent.bugly.beta.utils.e;

/* compiled from: BUGLY */
public abstract class a extends b {
    protected Context a;
    protected View b;
    protected FrameLayout c;
    protected LinearLayout d;
    protected ImageView e;
    protected TextView f;
    protected TextView g;
    protected TextView h;
    protected LinearLayout i;
    protected ResBean j;
    protected int k;
    protected int l;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.a = getActivity();
        this.j = ResBean.a;
        if (this.l == 0) {
            this.b = new RelativeLayout(this.a);
            ((RelativeLayout) this.b).setGravity(17);
            this.b.setBackgroundColor(Color.argb(100, 0, 0, 0));
            this.c = new FrameLayout(this.a);
            this.c.setLayoutParams(new LayoutParams(-2, -2));
            this.d = new LinearLayout(this.a);
            this.d.setBackgroundColor(-1);
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            this.d.setGravity(17);
            this.d.setLayoutParams(layoutParams);
            this.d.setMinimumWidth(com.tencent.bugly.beta.global.a.a(this.a, 280.0f));
            this.d.setOrientation(1);
            if (this.k == 2) {
                int a2 = com.tencent.bugly.beta.global.a.a(this.a, 6.0f);
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{(float) a2, (float) a2, (float) a2, (float) a2, (float) a2, (float) a2, (float) a2, (float) a2}, null, null));
                shapeDrawable.getPaint().setColor(-1);
                shapeDrawable.getPaint().setStyle(Style.FILL_AND_STROKE);
                this.d.setBackgroundDrawable(shapeDrawable);
            }
            this.f = new TextView(this.a);
            this.f.setGravity(16);
            this.f.setSingleLine();
            TextView textView = this.f;
            this.j.getClass();
            textView.setTextColor(Color.parseColor("#273238"));
            this.f.setTextSize(18.0f);
            this.f.setLayoutParams(layoutParams);
            this.f.setOnClickListener(null);
            this.f.setEllipsize(TruncateAt.END);
            int a3 = com.tencent.bugly.beta.global.a.a(this.a, 16.0f);
            this.f.setPadding(a3, 0, a3, 0);
            this.f.setTypeface(null, 1);
            this.f.setHeight(com.tencent.bugly.beta.global.a.a(this.a, 42.0f));
            this.f.setTag(Beta.TAG_TITLE);
            TextView textView2 = new TextView(this.a);
            textView2.setBackgroundColor(-3355444);
            textView2.setHeight(1);
            ScrollView scrollView = new ScrollView(this.a);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams2.setMargins(0, 0, 0, com.tencent.bugly.beta.global.a.a(this.a, 52.0f));
            scrollView.setLayoutParams(layoutParams2);
            scrollView.setFillViewport(true);
            scrollView.setVerticalScrollBarEnabled(false);
            scrollView.setHorizontalScrollBarEnabled(false);
            this.i = new LinearLayout(this.a);
            this.i.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            this.i.setOrientation(1);
            this.i.setPadding(a3, com.tencent.bugly.beta.global.a.a(this.a, 10.0f), a3, 0);
            LinearLayout linearLayout = new LinearLayout(this.a);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setGravity(17);
            linearLayout.setOrientation(0);
            linearLayout.setPadding(a3 / 2, a3, a3 / 2, a3);
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, -2);
            layoutParams3.gravity = 80;
            linearLayout.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            layoutParams4.setMargins(a3 / 2, 0, a3 / 2, 0);
            this.g = new TextView(this.a);
            this.g.setSingleLine();
            this.g.setGravity(17);
            this.g.setTag(Beta.TAG_CANCEL_BUTTON);
            new RelativeLayout.LayoutParams(-2, -2);
            int a4 = com.tencent.bugly.beta.global.a.a(this.a, 30.0f);
            if (this.k == 2) {
                FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(a4, a4);
                layoutParams5.gravity = 53;
                this.g.setLayoutParams(layoutParams5);
                this.g.setTextSize((float) (((double) a4) * 0.3d));
            } else {
                this.g.setLayoutParams(layoutParams4);
                this.g.setTextSize((float) 16);
                TextView textView3 = this.g;
                this.j.getClass();
                textView3.setTextColor(Color.parseColor("#757575"));
                this.g.setPadding(com.tencent.bugly.beta.global.a.a(this.a, 10.0f), com.tencent.bugly.beta.global.a.a(this.a, 5.0f), com.tencent.bugly.beta.global.a.a(this.a, 10.0f), com.tencent.bugly.beta.global.a.a(this.a, 5.0f));
            }
            this.h = new TextView(this.a);
            this.h.setLayoutParams(layoutParams4);
            this.h.setGravity(17);
            this.h.setTextSize((float) 16);
            TextView textView4 = this.h;
            this.j.getClass();
            textView4.setTextColor(Color.parseColor("#273238"));
            this.h.setSingleLine();
            this.h.setPadding(com.tencent.bugly.beta.global.a.a(this.a, 10.0f), com.tencent.bugly.beta.global.a.a(this.a, 5.0f), com.tencent.bugly.beta.global.a.a(this.a, 10.0f), com.tencent.bugly.beta.global.a.a(this.a, 5.0f));
            this.h.setTypeface(null, 1);
            this.h.setTag(Beta.TAG_CONFIRM_BUTTON);
            int a5 = com.tencent.bugly.beta.global.a.a(this.a, 40.0f);
            scrollView.addView(this.i);
            if (this.k == 2) {
                FrameLayout frameLayout = new FrameLayout(this.a);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
                this.c.setPadding(a4 / 2, (a4 / 2) - 5, (a4 / 2) - 5, a4 / 2);
                frameLayout.addView(this.c);
                frameLayout.addView(this.g);
                ((RelativeLayout) this.b).addView(frameLayout);
            } else {
                this.b.setPadding(a5, a5, a5, a5);
                ((RelativeLayout) this.b).addView(this.c);
                linearLayout.addView(this.g);
            }
            this.d.addView(this.f);
            this.d.addView(textView2);
            this.d.addView(scrollView);
            this.c.addView(this.d);
            linearLayout.addView(this.h);
            this.c.addView(linearLayout);
            if (this.k == 2) {
                Paint paint = new Paint();
                paint.setStyle(Style.FILL);
                paint.setAntiAlias(true);
                Bitmap createBitmap = Bitmap.createBitmap(a4, a4, Config.ARGB_8888);
                int i2 = a4 / 2;
                Canvas canvas = new Canvas(createBitmap);
                paint.setColor(-3355444);
                canvas.drawCircle((float) i2, (float) i2, (float) i2, paint);
                canvas.rotate(45.0f, (float) i2, (float) i2);
                paint.setColor(-7829368);
                int a6 = com.tencent.bugly.beta.global.a.a(this.a, 0.8f);
                canvas.drawRect(((float) i2) * 0.4f, (float) (i2 - a6), ((float) i2) * 1.6f, (float) (i2 + a6), paint);
                canvas.drawRect((float) (i2 - a6), ((float) i2) * 0.4f, (float) (i2 + a6), ((float) i2) * 1.6f, paint);
                canvas.rotate(-45.0f);
                Bitmap createBitmap2 = Bitmap.createBitmap(a4, a4, Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap2);
                paint.setColor(-7829368);
                canvas2.drawCircle((float) i2, (float) i2, (float) i2, paint);
                canvas2.rotate(45.0f, (float) i2, (float) i2);
                paint.setColor(-3355444);
                canvas2.drawRect(((float) i2) * 0.4f, (float) (i2 - a6), ((float) i2) * 1.6f, (float) (i2 + a6), paint);
                canvas2.drawRect((float) (i2 - a6), ((float) i2) * 0.4f, (float) (i2 + a6), ((float) i2) * 1.6f, paint);
                canvas2.rotate(-45.0f);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
                BitmapDrawable bitmapDrawable2 = new BitmapDrawable(createBitmap2);
                this.g.setBackgroundDrawable(bitmapDrawable);
                this.g.setOnTouchListener(new c(1, bitmapDrawable2, bitmapDrawable));
            }
            this.b.setOnClickListener(null);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(300);
            this.b.startAnimation(alphaAnimation);
        } else {
            this.b = inflater.inflate(this.l, null);
            this.e = (ImageView) this.b.findViewWithTag(Beta.TAG_IMG_BANNER);
            this.f = (TextView) this.b.findViewWithTag(Beta.TAG_TITLE);
            this.g = (TextView) this.b.findViewWithTag(Beta.TAG_CANCEL_BUTTON);
            this.h = (TextView) this.b.findViewWithTag(Beta.TAG_CONFIRM_BUTTON);
        }
        this.g.setVisibility(8);
        this.h.setVisibility(8);
        this.g.setFocusable(true);
        this.h.setFocusable(true);
        this.h.requestFocus();
        return this.b;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.f = null;
        this.e = null;
        this.g = null;
        this.h = null;
        this.i = null;
    }

    /* access modifiers changed from: protected */
    public void a(String str, OnClickListener onClickListener, String str2, OnClickListener onClickListener2) {
        final String str3 = str;
        final OnClickListener onClickListener3 = onClickListener;
        final String str4 = str2;
        final OnClickListener onClickListener4 = onClickListener2;
        e.a(new Runnable() {
            public void run() {
                if (a.this.g != null && a.this.h != null) {
                    if (str3 != null) {
                        a.this.g.setVisibility(0);
                        if (a.this.k != 2) {
                            a.this.g.setText(str3);
                            if (a.this.l == 0) {
                                a.this.g.getViewTreeObserver().addOnPreDrawListener(new d(2, Integer.valueOf(a.this.k), a.this.g, Integer.valueOf(1)));
                            }
                        }
                        a.this.g.setOnClickListener(onClickListener3);
                    }
                    if (str4 != null) {
                        a.this.h.setVisibility(0);
                        a.this.h.setText(str4);
                        a.this.h.setOnClickListener(onClickListener4);
                        if (a.this.l == 0) {
                            a.this.h.getViewTreeObserver().addOnPreDrawListener(new d(2, Integer.valueOf(a.this.k), a.this.h, Integer.valueOf(1)));
                        }
                        a.this.h.requestFocus();
                    }
                }
            }
        });
    }

    public void a() {
        if (this.b == null) {
            super.a();
            return;
        }
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(200);
        e.a(new Runnable() {
            public void run() {
                if (a.this.b != null) {
                    a.this.b.startAnimation(alphaAnimation);
                }
            }
        });
        alphaAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (a.this.b != null) {
                    a.this.b.setVisibility(8);
                }
                a.super.a();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
