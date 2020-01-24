package com.iwown.sport_module.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.google.zxing.ResultPoint;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.zxing.camera.CameraManager;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View {
    private static final long ANIMATION_DELAY = 10;
    private static final int CORNER_WIDTH = 10;
    private static final int MIDDLE_LINE_PADDING = 5;
    private static final int MIDDLE_LINE_WIDTH = 6;
    private static final int OPAQUE = 255;
    private static final int[] SCANNER_ALPHA = {0, 64, 128, Opcodes.AND_LONG_2ADDR, 255, Opcodes.AND_LONG_2ADDR, 128, 64};
    private static final int SPEEN_DISTANCE = 5;
    private static final int TEXT_PADDING_TOP = 30;
    private static final int TEXT_SIZE = 16;
    private static float density;
    private int ScreenRate;
    private int angleColor = -16711936;
    private final int frameColor;
    boolean isFirst;
    private final int laserColor;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private final int maskColor;
    private Paint paint = new Paint();
    private Collection<ResultPoint> possibleResultPoints;
    private Bitmap resultBitmap;
    private final int resultColor;
    private final int resultPointColor;
    private String s2wifi_text;
    private int scannerAlpha;
    private int slideBottom;
    private int slideTop;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = getResources();
        this.maskColor = resources.getColor(R.color.viewfinder_mask);
        this.resultColor = resources.getColor(R.color.result_view);
        this.frameColor = resources.getColor(R.color.viewfinder_frame);
        this.laserColor = resources.getColor(R.color.viewfinder_laser);
        this.resultPointColor = resources.getColor(R.color.possible_result_points);
        this.scannerAlpha = 0;
        this.possibleResultPoints = new HashSet(5);
        density = context.getResources().getDisplayMetrics().density;
        this.ScreenRate = (int) (20.0f * density);
    }

    public void setAngleColor(int color) {
        this.angleColor = color;
    }

    public void onDraw(Canvas canvas) {
        Rect frame = CameraManager.get().getFramingRect();
        if (frame != null) {
            if (!this.isFirst) {
                this.isFirst = true;
                this.slideTop = frame.top;
                this.slideBottom = frame.bottom;
            }
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
            canvas.drawRect(0.0f, 0.0f, (float) width, (float) frame.top, this.paint);
            canvas.drawRect(0.0f, (float) frame.top, (float) frame.left, (float) (frame.bottom + 1), this.paint);
            canvas.drawRect((float) (frame.right + 1), (float) frame.top, (float) width, (float) (frame.bottom + 1), this.paint);
            canvas.drawRect(0.0f, (float) (frame.bottom + 1), (float) width, (float) height, this.paint);
            if (this.resultBitmap != null) {
                this.paint.setAlpha(255);
                canvas.drawBitmap(this.resultBitmap, (float) frame.left, (float) frame.top, this.paint);
                return;
            }
            this.paint.setColor(this.angleColor);
            canvas.drawRect((float) frame.left, (float) frame.top, (float) (frame.left + this.ScreenRate), (float) (frame.top + 10), this.paint);
            canvas.drawRect((float) frame.left, (float) frame.top, (float) (frame.left + 10), (float) (frame.top + this.ScreenRate), this.paint);
            canvas.drawRect((float) (frame.right - this.ScreenRate), (float) frame.top, (float) frame.right, (float) (frame.top + 10), this.paint);
            canvas.drawRect((float) (frame.right - 10), (float) frame.top, (float) frame.right, (float) (frame.top + this.ScreenRate), this.paint);
            canvas.drawRect((float) frame.left, (float) (frame.bottom - 10), (float) (frame.left + this.ScreenRate), (float) frame.bottom, this.paint);
            canvas.drawRect((float) frame.left, (float) (frame.bottom - this.ScreenRate), (float) (frame.left + 10), (float) frame.bottom, this.paint);
            canvas.drawRect((float) (frame.right - this.ScreenRate), (float) (frame.bottom - 10), (float) frame.right, (float) frame.bottom, this.paint);
            canvas.drawRect((float) (frame.right - 10), (float) (frame.bottom - this.ScreenRate), (float) frame.right, (float) frame.bottom, this.paint);
            this.slideTop += 5;
            if (this.slideTop >= frame.bottom) {
                this.slideTop = frame.top;
            }
            canvas.drawRect((float) (frame.left + 5), (float) (this.slideTop - 3), (float) (frame.right - 5), (float) (this.slideTop + 3), this.paint);
            float base_title_top = ((float) frame.bottom) + (30.0f * density);
            this.paint.setTextSize(16.0f * density);
            if (!TextUtils.isEmpty(this.s2wifi_text)) {
                drawS2WifiText(canvas, width, base_title_top, this.paint);
            } else {
                this.paint.setAlpha(64);
                drawBaseTextTitle(canvas, (float) width, base_title_top, this.paint);
            }
            Collection<ResultPoint> currentPossible = this.possibleResultPoints;
            Collection<ResultPoint> currentLast = this.lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                this.lastPossibleResultPoints = null;
            }
            if (currentLast != null) {
            }
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    private void drawS2WifiText(Canvas canvas, int width, float base_title_top, Paint paint2) {
        float base_title_top2 = base_title_top + ((float) DensityUtil.dip2px(getContext(), 20.0f));
        paint2.setTextSize(13.0f * density);
        drawBaseTextTitle(canvas, (float) width, base_title_top2, paint2);
        paint2.setColor(Color.parseColor("#CCCCCC"));
        float base_title_top3 = paint2.getTextSize() + base_title_top2 + ((float) DensityUtil.dip2px(getContext(), 10.0f));
        String text = getResources().getString(R.string.sport_module_qrcode_s2_wifi_text);
        canvas.drawText(text, (((float) width) - paint2.measureText(text)) / 2.0f, base_title_top3, paint2);
    }

    private void drawBaseTextTitle(Canvas canvas, float width, float base_title_top, Paint paint2) {
        paint2.setColor(-1);
        paint2.setTypeface(Typeface.create("System", 1));
        String text = getResources().getString(R.string.sport_module_qrcode_scan_text);
        float textWidth = paint2.measureText(text);
        if (textWidth > ((float) (getWidth() - 10))) {
            TextPaint tp = new TextPaint();
            tp.setColor(-1);
            tp.setStyle(Style.FILL);
            tp.setTextSize(50.0f);
            StaticLayout myStaticLayout = new StaticLayout(text, tp, canvas.getWidth(), Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(10.0f, base_title_top);
            myStaticLayout.draw(canvas);
            canvas.restore();
            return;
        }
        canvas.drawText(text, (width - textWidth) / 2.0f, base_title_top, paint2);
    }

    public void drawViewfinder() {
        this.resultBitmap = null;
        invalidate();
    }

    public void drawResultBitmap(Bitmap barcode) {
        this.resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        this.possibleResultPoints.add(point);
    }

    public void setShowS2WifiText(String s2wifi_text2) {
        this.s2wifi_text = s2wifi_text2;
    }
}
