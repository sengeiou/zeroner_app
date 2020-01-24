package com.iwown.sport_module.gps.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.view.SwipeBackLayout;
import com.iwown.sport_module.gps.view.WallpaperDrawable;

public class SwipeBackActivity extends Activity {
    protected SwipeBackLayout layout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.sport_module_swipe_base, null);
        this.layout.attachToActivity(this);
        Drawable d = WallpaperManager.getInstance(this).getDrawable();
        if (d != null) {
            new WallpaperDrawable().setBitmap(d);
            this.layout.setBackground(d);
        }
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}
