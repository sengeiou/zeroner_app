package com.iwown.my_module.common;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.my_module.R;
import com.iwown.my_module.utility.ScreenUtility;
import com.iwown.my_module.utility.StatusbarHelper;
import com.iwown.my_module.widget.ErrorTipTextView;
import com.iwown.my_module.widget.ErrorTipTextView.OnDisplayEndListener;
import com.iwown.my_module.widget.TitleBar;
import com.iwown.my_module.widget.TitleBar.ImageAction;
import com.iwown.my_module.widget.TitleBar.TextAction;

public class BaseActivity extends AppCompatActivity {
    protected LinearLayout mContentHolder;
    ErrorTipTextView mErrorTip;
    protected OnDisplayEndListener mErrorTipEndListener;
    protected LoadingDialog mLoadDialog;
    public TitleBar mTitleBar;
    protected RelativeLayout mTopLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        super.setContentView(R.layout.my_module_activity_base);
        this.mContentHolder = (LinearLayout) findViewById(R.id.common_base_content_layout);
        this.mTitleBar = (TitleBar) findViewById(R.id.base_title_bar);
        this.mTopLayout = (RelativeLayout) findViewById(R.id.rl_root);
        this.mErrorTip = (ErrorTipTextView) findViewById(R.id.error_tip_common);
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
        this.mTitleBar.setImmersive(true);
        this.mTitleBar.setBackgroundResource(R.drawable.titlebar_bg);
        this.mTitleBar.setTitleColor(-1);
        this.mTitleBar.setSubTitleColor(-1);
        this.mTitleBar.setActionTextColor(-1);
        int statusBarHeight = StatusbarHelper.getStatusBarHeight();
        this.mErrorTip.setInitialHeightAndWidth(statusBarHeight + 20, ScreenUtility.getScreenWidth(this) - 120);
        this.mErrorTip.setTranslationY((float) (-statusBarHeight));
        this.mErrorTip.setViewBackground(R.drawable.dark_theme_round_corner);
        this.mErrorTip.setDisplayEndListener(new OnDisplayEndListener() {
            public void onDisplayEnd() {
                BaseActivity.this.mTopLayout.setSystemUiVisibility(0);
                if (BaseActivity.this.mErrorTipEndListener != null) {
                    BaseActivity.this.mErrorTipEndListener.onDisplayEnd();
                }
            }
        });
    }

    public void setLeftBtn(final OnClickListener clickListener) {
        this.mTitleBar.setLeftImageResource(R.mipmap.back3x);
        this.mTitleBar.setLeftTextColor(-1);
        this.mTitleBar.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                clickListener.onClick(BaseActivity.this.mTitleBar);
            }
        });
    }

    public void setRightText(String text, final OnClickListener clickListener) {
        this.mTitleBar.setRightLayoutVisible(true);
        this.mTitleBar.addAction(new TextAction(text) {
            public void performAction(View view) {
                clickListener.onClick(BaseActivity.this.mTitleBar);
            }
        });
    }

    public void setRightImag(int drawable, final OnClickListener clickListener) {
        this.mTitleBar.setRightLayoutVisible(true);
        this.mTitleBar.addAction(new ImageAction(drawable) {
            public void performAction(View view) {
                clickListener.onClick(BaseActivity.this.mTitleBar);
            }
        });
    }

    public void removeAllActions() {
        this.mTitleBar.removeAllActions();
    }

    public void setRightVisible(boolean isShow) {
        this.mTitleBar.setRightLayoutVisible(isShow);
    }

    public void setLeftBackTo() {
        this.mTitleBar.setLeftImageResource(R.mipmap.back3x);
        this.mTitleBar.setLeftTextColor(-1);
        this.mTitleBar.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                BaseActivity.this.back();
            }
        });
        this.mTitleBar.setActionTextColor(-1);
    }

    public void setTitleText(int id) {
        this.mTitleBar.setTitle(id);
    }

    public void setTitleText(String text) {
        this.mTitleBar.setTitle((CharSequence) text);
    }

    public void setLeftTitle(String leftText) {
        this.mTitleBar.setLeftText((CharSequence) leftText);
    }

    public void setLeftTextColor(int color) {
        this.mTitleBar.setLeftTextColor(color);
    }

    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        view.setLayoutParams(new LayoutParams(-1, -1));
        this.mContentHolder.addView(view);
    }

    public void setContentView(View view) {
        this.mContentHolder.addView(view, new LayoutParams(-1, -1));
    }

    public void setContentView(View view, LayoutParams params) {
        this.mContentHolder.addView(view, params);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mErrorTip.cancelAnims();
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

    public void setLeftVisible(boolean isVisible) {
        this.mTitleBar.setLeftVisible(isVisible);
    }

    public TitleBar getmTitleBar() {
        return this.mTitleBar;
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
        this.mTitleBar.setBackgroundResource(color);
    }

    private int[] getViewWidthAndHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public void raiseErrorNotice(int resId) {
        this.mTopLayout.setSystemUiVisibility(4);
        this.mErrorTip.setText(getResources().getString(resId));
        this.mErrorTip.startAnim();
    }

    public void raiseErrorNotice(String errMsg) {
        this.mErrorTip.setText(errMsg);
        this.mErrorTip.startAnim();
    }

    /* access modifiers changed from: protected */
    public void setErrorTipEndListener(OnDisplayEndListener listener) {
        this.mErrorTipEndListener = listener;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v == null || !(v instanceof EditText)) {
            return false;
        }
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0];
        int top = l[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();
        if (event.getX() <= ((float) left) || event.getX() >= ((float) right) || event.getY() <= ((float) top) || event.getY() >= ((float) bottom)) {
            return true;
        }
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(token, 2);
        }
    }

    public void showLoadingDialog() {
        if (this.mLoadDialog == null) {
            this.mLoadDialog = new LoadingDialog(this);
        }
        if (!isDestroyed()) {
            this.mLoadDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (this.mLoadDialog != null && !isDestroyed()) {
            this.mLoadDialog.dismiss();
        }
    }
}
