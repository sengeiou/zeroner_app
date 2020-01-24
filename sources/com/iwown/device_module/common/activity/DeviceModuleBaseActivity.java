package com.iwown.device_module.common.activity;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.iwown.device_module.R;
import com.iwown.device_module.common.view.TitleBar;
import com.iwown.device_module.common.view.TitleBar.ImageAction;
import com.iwown.device_module.common.view.TitleBar.TextAction;

public class DeviceModuleBaseActivity extends AppCompatActivity {
    public static String TAG = "";
    private long beginTime;
    protected LinearLayout contentLayout;
    private RelativeLayout mRootView;
    public TitleBar titleBar;

    public interface ActionOnclickListener {
        void onclick();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        super.setContentView(R.layout.device_module_activity_base);
        this.contentLayout = (LinearLayout) findViewById(R.id.common_base_content_layout);
        this.titleBar = (TitleBar) findViewById(R.id.base_title_bar);
        this.mRootView = (RelativeLayout) findViewById(R.id.rl_root);
        this.beginTime = System.currentTimeMillis();
        TAG = getClass().getSimpleName();
        if (hasKitKat() && !hasLollipop()) {
            getWindow().addFlags(67108864);
        } else if (hasLollipop()) {
            Window window = getWindow();
            window.clearFlags(201326592);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        }
        initBaseView();
    }

    public void initBaseView() {
        this.titleBar.setImmersive(true);
        this.titleBar.setBackground(ContextCompat.getDrawable(this, R.drawable.rect_change_no_corners));
        this.titleBar.setTitleColor(-1);
        this.titleBar.setSubTitleColor(-1);
        this.titleBar.setActionTextColor(-1);
    }

    public RelativeLayout getRootView() {
        return this.mRootView;
    }

    public void setTitleBarBackgroundColor(int id) {
        this.titleBar.setBackgroundColor(getResources().getColor(id));
        this.titleBar.setTitleColor(getResources().getColor(R.color.white));
        this.titleBar.setLeftTextColor(getResources().getColor(R.color.white));
    }

    public void setCenterLayoutClickListener(OnClickListener l) {
        this.titleBar.setCenterClickListener(l);
    }

    public void setLeftBtn(final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setLeftImageResource(R.mipmap.back3x);
        this.titleBar.setLeftTextColor(-1);
        this.titleBar.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setRightText(String text, final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setRightLayoutVisible(true);
        this.titleBar.addAction(new TextAction(text) {
            public void performAction(View view) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setRightImag(int drawable, final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setRightLayoutVisible(true);
        this.titleBar.addAction(new ImageAction(drawable) {
            public void performAction(View view) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void removeAllActions() {
        this.titleBar.removeAllActions();
    }

    public void setRightVisible(boolean isShow) {
        this.titleBar.setRightLayoutVisible(isShow);
    }

    public void setLeftBackTo() {
        this.titleBar.setLeftImageResource(R.mipmap.back3x);
        this.titleBar.setLeftTextColor(-1);
        this.titleBar.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                DeviceModuleBaseActivity.this.back();
            }
        });
        this.titleBar.setActionTextColor(-1);
    }

    public void setTitleText(int id) {
        this.titleBar.setTitle(id);
    }

    public void setTitleText(String text) {
        this.titleBar.setTitle((CharSequence) text);
    }

    public void setLeftTitle(String leftText) {
        this.titleBar.setLeftText((CharSequence) leftText);
    }

    public void setLeftTextColor(int color) {
        this.titleBar.setLeftTextColor(color);
    }

    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        view.setLayoutParams(new LayoutParams(-1, -1));
        this.contentLayout.addView(view);
    }

    public void setContentView(View view) {
        this.contentLayout.addView(view, new LayoutParams(-1, -1));
    }

    public void setContentView(View view, LayoutParams params) {
        this.contentLayout.addView(view, params);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public static boolean hasKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != 0 || event.getKeyCode() != 4) {
            return super.dispatchKeyEvent(event);
        }
        back();
        return true;
    }

    public void back() {
        finish();
    }

    public void setTitleBarRightDrawable(int id) {
        this.titleBar.setCenterTitleRightLayout(id);
    }

    public void setLeftVisible(boolean isVisible) {
        this.titleBar.setLeftVisible(isVisible);
    }

    public TitleBar getTitleBar() {
        return this.titleBar;
    }

    public void changeViewVisible(View view) {
        if (view.getVisibility() == 0) {
            view.setVisibility(8);
        } else {
            view.setVisibility(0);
        }
    }

    public String[] getArray(int id) {
        return getResources().getStringArray(id);
    }

    /* access modifiers changed from: protected */
    public void setTitleBackground(int color) {
        this.titleBar.setBackgroundResource(color);
    }

    private int[] getViewWidthAndHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }
}
