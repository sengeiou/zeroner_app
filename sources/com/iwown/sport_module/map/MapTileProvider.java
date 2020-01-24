package com.iwown.sport_module.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import java.io.ByteArrayOutputStream;

public class MapTileProvider implements TileProvider {
    private static final int TILE_SIZE_DP = 256;
    private final Bitmap mBorderTile = Bitmap.createBitmap((int) (this.mScaleFactor * 256.0f), (int) (this.mScaleFactor * 256.0f), Config.ARGB_8888);
    private final float mScaleFactor;

    public MapTileProvider(Context context) {
        this.mScaleFactor = context.getResources().getDisplayMetrics().density * 0.6f;
        new Paint(1).setStyle(Style.STROKE);
        new Canvas(this.mBorderTile);
    }

    public Tile getTile(int x, int y, int zoom) {
        Bitmap coordTile = drawTileCoords(x, y, zoom);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        coordTile.compress(CompressFormat.PNG, 0, stream);
        return new Tile((int) (this.mScaleFactor * 256.0f), (int) (this.mScaleFactor * 256.0f), stream.toByteArray());
    }

    private synchronized Bitmap drawTileCoords(int x, int y, int zoom) {
        Bitmap copy;
        copy = this.mBorderTile.copy(Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copy);
        String str = "(" + x + ", " + y + ")";
        String str2 = "zoom = " + zoom;
        Paint mTextPaint = new Paint(1);
        mTextPaint.setStyle(Style.FILL);
        mTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        mTextPaint.setAlpha(80);
        mTextPaint.setTextSize(18.0f * this.mScaleFactor);
        canvas.drawRect(0.0f, 0.0f, this.mScaleFactor * 256.0f, this.mScaleFactor * 256.0f, mTextPaint);
        return copy;
    }
}
