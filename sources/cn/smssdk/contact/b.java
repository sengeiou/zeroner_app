package cn.smssdk.contact;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import com.mob.tools.utils.R;

class b extends Shape {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(-6102899);
        RectF rectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
        int dipToPx = R.dipToPx(this.a.activity, 4);
        canvas.drawRoundRect(rectF, (float) dipToPx, (float) dipToPx, paint);
        paint.setColor(-1);
        int dipToPx2 = R.dipToPx(this.a.activity, 2);
        canvas.drawRoundRect(new RectF((float) dipToPx2, (float) dipToPx2, getWidth() - ((float) dipToPx2), getHeight() - ((float) dipToPx2)), (float) dipToPx2, (float) dipToPx2, paint);
    }
}
