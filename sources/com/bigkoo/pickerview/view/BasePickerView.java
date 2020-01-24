package com.bigkoo.pickerview.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout.LayoutParams;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.utils.PickerViewAnimateUtil;
import com.iwown.lib_common.R;

public class BasePickerView {
    protected int bgColor_default = -1;
    private boolean cancelable;
    protected View clickView;
    protected ViewGroup contentContainer;
    private Context context;
    public ViewGroup decorView;
    private ViewGroup dialogView;
    /* access modifiers changed from: private */
    public boolean dismissing;
    private int gravity = 80;
    private Animation inAnim;
    private boolean isAnim = true;
    /* access modifiers changed from: private */
    public boolean isShowing;
    private Dialog mDialog;
    private final OnTouchListener onCancelableTouchListener = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 0) {
                BasePickerView.this.dismiss();
            }
            return false;
        }
    };
    /* access modifiers changed from: private */
    public OnDismissListener onDismissListener;
    private OnKeyListener onKeyBackListener = new OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode != 4 || event.getAction() != 0 || !BasePickerView.this.isShowing()) {
                return false;
            }
            BasePickerView.this.dismiss();
            return true;
        }
    };
    private Animation outAnim;
    private final LayoutParams params = new LayoutParams(-1, -2, 80);
    protected int pickerview_bg_topbar = -657931;
    protected int pickerview_timebtn_nor = -16417281;
    protected int pickerview_timebtn_pre = -4007179;
    protected int pickerview_topbar_title = ViewCompat.MEASURED_STATE_MASK;
    /* access modifiers changed from: private */
    public ViewGroup rootView;

    public BasePickerView(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void initViews(int backgroudId) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        if (isDialog()) {
            this.dialogView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, null, false);
            this.dialogView.setBackgroundColor(0);
            this.contentContainer = (ViewGroup) this.dialogView.findViewById(R.id.content_container);
            this.params.leftMargin = 30;
            this.params.rightMargin = 30;
            this.contentContainer.setLayoutParams(this.params);
            createDialog();
            this.dialogView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BasePickerView.this.dismiss();
                }
            });
        } else {
            if (this.decorView == null) {
                this.decorView = (ViewGroup) ((Activity) this.context).getWindow().getDecorView().findViewById(16908290);
            }
            this.rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, this.decorView, false);
            this.rootView.setLayoutParams(new LayoutParams(-1, -1));
            if (backgroudId != 0) {
                this.rootView.setBackgroundColor(backgroudId);
            }
            this.contentContainer = (ViewGroup) this.rootView.findViewById(R.id.content_container);
            this.contentContainer.setLayoutParams(this.params);
        }
        setKeyBackCancelable(true);
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.inAnim = getInAnimation();
        this.outAnim = getOutAnimation();
    }

    /* access modifiers changed from: protected */
    public void initEvents() {
    }

    public void show(View v, boolean isAnim2) {
        this.clickView = v;
        this.isAnim = isAnim2;
        show();
    }

    public void show(boolean isAnim2) {
        this.isAnim = isAnim2;
        show();
    }

    public void show(View v) {
        this.clickView = v;
        show();
    }

    public void show() {
        if (isDialog()) {
            showDialog();
        } else if (!isShowing()) {
            this.isShowing = true;
            onAttached(this.rootView);
            this.rootView.requestFocus();
        }
    }

    private void onAttached(View view) {
        this.decorView.addView(view);
        if (this.isAnim) {
            this.contentContainer.startAnimation(this.inAnim);
        }
    }

    public boolean isShowing() {
        if (isDialog()) {
            return false;
        }
        if (this.rootView.getParent() != null || this.isShowing) {
            return true;
        }
        return false;
    }

    public void dismiss() {
        if (isDialog()) {
            dismissDialog();
        } else if (!this.dismissing) {
            if (this.isAnim) {
                this.outAnim.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        BasePickerView.this.dismissImmediately();
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                this.contentContainer.startAnimation(this.outAnim);
            } else {
                dismissImmediately();
            }
            this.dismissing = true;
        }
    }

    public void dismissImmediately() {
        this.decorView.post(new Runnable() {
            public void run() {
                BasePickerView.this.decorView.removeView(BasePickerView.this.rootView);
                BasePickerView.this.isShowing = false;
                BasePickerView.this.dismissing = false;
                if (BasePickerView.this.onDismissListener != null) {
                    BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                }
            }
        });
    }

    public Animation getInAnimation() {
        return AnimationUtils.loadAnimation(this.context, PickerViewAnimateUtil.getAnimationResource(this.gravity, true));
    }

    public Animation getOutAnimation() {
        return AnimationUtils.loadAnimation(this.context, PickerViewAnimateUtil.getAnimationResource(this.gravity, false));
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener2) {
        this.onDismissListener = onDismissListener2;
        return this;
    }

    public void setKeyBackCancelable(boolean isCancelable) {
        ViewGroup View;
        if (isDialog()) {
            View = this.dialogView;
        } else {
            View = this.rootView;
        }
        View.setFocusable(isCancelable);
        View.setFocusableInTouchMode(isCancelable);
        if (isCancelable) {
            View.setOnKeyListener(this.onKeyBackListener);
        } else {
            View.setOnKeyListener(null);
        }
    }

    /* access modifiers changed from: protected */
    public BasePickerView setOutSideCancelable(boolean isCancelable) {
        if (this.rootView != null) {
            View view = this.rootView.findViewById(R.id.outmost_container);
            if (isCancelable) {
                view.setOnTouchListener(this.onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }
        return this;
    }

    public void setDialogOutSideCancelable(boolean cancelable2) {
        this.cancelable = cancelable2;
    }

    public View findViewById(int id) {
        return this.contentContainer.findViewById(id);
    }

    public void createDialog() {
        if (this.dialogView != null) {
            this.mDialog = new Dialog(this.context, R.style.custom_dialog2);
            this.mDialog.setCancelable(this.cancelable);
            this.mDialog.setContentView(this.dialogView);
            this.mDialog.getWindow().setWindowAnimations(R.style.pickerview_dialogAnim);
            this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    if (BasePickerView.this.onDismissListener != null) {
                        BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                    }
                }
            });
        }
    }

    public void showDialog() {
        if (this.mDialog != null) {
            this.mDialog.show();
        }
    }

    public void dismissDialog() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    public boolean isDialog() {
        return false;
    }
}
