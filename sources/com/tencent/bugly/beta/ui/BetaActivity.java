package com.tencent.bugly.beta.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.global.b;

/* compiled from: BUGLY */
public class BetaActivity extends FragmentActivity {
    public Runnable onDestroyRunnable = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(1);
            if (Beta.dialogFullScreen) {
                getWindow().setFlags(1024, 1024);
            }
            View findViewById = getWindow().getDecorView().findViewById(16908290);
            if (findViewById != null) {
                findViewById.setOnClickListener(new b(1, this, findViewById));
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        int intExtra = getIntent().getIntExtra("frag", -1);
        b bVar = (b) g.a.get(Integer.valueOf(intExtra));
        if (bVar != null) {
            getSupportFragmentManager().beginTransaction().add(16908290, (Fragment) bVar).commit();
            g.a.remove(Integer.valueOf(intExtra));
            return;
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.onDestroyRunnable != null) {
            this.onDestroyRunnable.run();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z;
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(16908290);
        try {
            if (findFragmentById instanceof b) {
                z = ((b) findFragmentById).a(keyCode, event);
            } else {
                z = false;
            }
        } catch (Exception e) {
            z = false;
        }
        if (!z) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }
}
