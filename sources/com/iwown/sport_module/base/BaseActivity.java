package com.iwown.sport_module.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.iwown.lib_common.views.TitleBar;
import com.iwown.lib_common.views.TitleBar.ActionOnclickListener;
import com.iwown.lib_common.views.TitleBar.ImageAction;
import com.iwown.lib_common.views.TitleBar.TextAction;
import com.iwown.lib_common.views.utils.StatusbarUtils;
import com.iwown.sport_module.R;
import com.socks.library.KLog;

public abstract class BaseActivity extends AppCompatActivity {
    public String Tag = BaseActivity.class.getName();
    private LinearLayout ll_main1;
    protected Context mContext;
    private RelativeLayout rl_main;
    private TitleBar titleBar;
    private int topType = 1;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.Tag = getClass().getName();
        setRequestedOrientation(1);
        initBaseView();
    }

    private void initBaseView() {
        this.titleBar = new TitleBar(this);
        this.rl_main = new RelativeLayout(this);
        this.ll_main1 = new LinearLayout(this);
        this.ll_main1.setOrientation(1);
    }

    public void setTitleBarBG(int color) {
        this.titleBar.setBackgroundColor(color);
    }

    public void setTitleBarImmersive() {
        this.titleBar.setImmersive(true);
    }

    public void setCenterTitleColor(int color) {
        this.titleBar.setTitleColor(color);
    }

    public void setLeftColor(int color) {
        this.titleBar.setLeftTextColor(color);
    }

    public TitleBar getTitleBar() {
        return this.titleBar;
    }

    public void addRightImageResource(int id, Intent intent) {
        this.titleBar.addAction(new ImageAction(id) {
            public void performAction(View view) {
            }
        });
    }

    public void setLeftBtn(final ActionOnclickListener actionOnclickListener, Drawable back_src, String bac_title) {
        this.titleBar.setLeftImageResource(R.drawable.left_back_white);
        if (!TextUtils.isEmpty(bac_title)) {
            this.titleBar.setLeftText((CharSequence) bac_title);
        }
        if (back_src != null) {
            this.titleBar.setLeftImageDrable(back_src);
        }
        this.titleBar.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setLeftBackTo() {
        setLeftBtn(new ActionOnclickListener() {
            public void onclick() {
                BaseActivity.this.back();
            }
        }, getResources().getDrawable(R.mipmap.back3x), null);
    }

    /* access modifiers changed from: protected */
    public void back() {
        finish();
    }

    /* access modifiers changed from: protected */
    public int getColorRes(int colorRes) {
        return getResources().getColor(colorRes);
    }

    public void changeLeftPointer(int id) {
        this.titleBar.setLeftImageResource(id);
    }

    public void setRightText(String text, final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setRightLayoutVisible(true);
        this.titleBar.addAction(new TextAction(text) {
            public void performAction(View view) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setRightImg(int imgRes, final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setRightLayoutVisible(true);
        this.titleBar.addAction(new ImageAction(imgRes) {
            public void performAction(View view) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setRightText(String text, int colorRes, final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setRightLayoutVisible(true);
        this.titleBar.setActionTextColor(getColorRes(colorRes));
        this.titleBar.addAction(new TextAction(text) {
            public void performAction(View view) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setRightText(String text, int colorRes, int id, final ActionOnclickListener actionOnclickListener) {
        this.titleBar.setRightLayoutVisible(true);
        this.titleBar.setActionTextColor(getColorRes(colorRes));
        this.titleBar.addAction(id, new TextAction(text) {
            public void performAction(View view) {
                actionOnclickListener.onclick();
            }
        });
    }

    public void setRightVisible(boolean isShow) {
        this.titleBar.setRightLayoutVisible(isShow);
    }

    public void setRightTextById(int id, String text) {
        this.titleBar.setRightTextById(id, text);
    }

    public void setTitleTextID(int id) {
        this.titleBar.setTitle(id);
    }

    public void setTitleText(String text) {
        this.titleBar.setTitle((CharSequence) text);
    }

    public void setContentLayout(int layoutResID) {
        setContentLayout(layoutResID, 0);
    }

    public void setContentLayout(int layoutResID, int topType2) {
        this.topType = topType2;
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        view.setLayoutParams(new LayoutParams(-1, -1));
        setContentLayout(view, topType2);
    }

    public void setContentLayout(View view, int topType2) {
        if (topType2 == 1) {
            this.titleBar.setBackgroundColor(-1);
            this.titleBar.setTitleColor(R.color.white);
            this.ll_main1.removeAllViews();
            this.ll_main1.addView(this.titleBar);
            this.ll_main1.addView(view);
            setContentView((View) this.ll_main1);
            return;
        }
        KLog.e("---- " + topType2);
        StatusbarUtils.hideStatusBar(this);
        this.titleBar.setImmersive(true);
        this.titleBar.setBackgroundColor(Color.parseColor("#00000000"));
        this.titleBar.setTitleColor(R.color.white);
        this.titleBar.setActionTextColor(getResources().getColor(R.color.white));
        this.ll_main1.removeAllViews();
        this.ll_main1.addView(this.titleBar);
        this.ll_main1.addView(view);
        setContentView((View) this.ll_main1);
    }

    public void setContentLayout(View view, TitleBar titleBar2) {
        this.titleBar = titleBar2;
        StatusbarUtils.hideStatusBar(this);
        titleBar2.setImmersive(true);
        titleBar2.setBackgroundColor(Color.parseColor("#00000000"));
        titleBar2.setTitleColor(R.color.white);
        titleBar2.setActionTextColor(getResources().getColor(R.color.white));
        this.ll_main1.removeAllViews();
        this.ll_main1.addView(titleBar2);
        this.ll_main1.addView(view);
        setContentView((View) this.ll_main1);
    }

    public void setActivityBG(int color) {
        this.ll_main1.setBackgroundColor(color);
    }

    /* access modifiers changed from: protected */
    public View getRootView() {
        return this.ll_main1;
    }

    public static boolean hasKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }

    public void onBackPressed() {
        back();
    }

    public void updateBG() {
    }
}
