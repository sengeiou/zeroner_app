package cn.smssdk.contact;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import com.mob.tools.utils.R;

class c extends Shape {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(-6102899);
        RectF rectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
        int dipToPx = R.dipToPx(this.a.activity, 4);
        canvas.drawRoundRect(rectF, (float) dipToPx, (float) dipToPx, paint);
    }
}
