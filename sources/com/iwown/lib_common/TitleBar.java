package com.iwown.lib_common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.common.primitives.Ints;
import com.iwown.my_module.utility.Constants;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.util.LinkedList;

public class TitleBar extends ViewGroup implements OnClickListener {
    private static final int DEFAULT_ACTION_TEXT_SIZE = 15;
    private static final int DEFAULT_MAIN_TEXT_SIZE = 18;
    private static final int DEFAULT_SEARCH_TEXT_SIZE = 14;
    private static final int DEFAULT_SUB_TEXT_SIZE = 12;
    private static final int DEFAULT_TITLE_BAR_HEIGHT = 48;
    public static final int SEARCH_TEXT = 2;
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    public static final int TAB_TEXT = 3;
    public static final int TITLE_TEXT = 1;
    private Context context;
    private int flag = 1;
    private int mActionPadding;
    private int mActionTextColor;
    private LinearLayout mCenterLayout;
    private TextView mCenterText;
    private View mCustomCenterView;
    private View mDividerView;
    private int mHeight;
    private boolean mImmersive;
    private TextView mLeftText;
    private int mOutPadding;
    private LinearLayout mRightLayout;
    private int mScreenWidth;
    private EditText mSearchEditText;
    private int mStatusBarHeight;
    private TextView mSubTitleText;

    public interface Action {
        int getDrawable();

        String getText();

        void performAction(View view);
    }

    public static class ActionList extends LinkedList<Action> {
    }

    public static abstract class ImageAction implements Action {
        private int mDrawable;

        public ImageAction(int drawable) {
            this.mDrawable = drawable;
        }

        public int getDrawable() {
            return this.mDrawable;
        }

        public String getText() {
            return null;
        }
    }

    public static abstract class TextAction implements Action {
        private final String mText;

        public TextAction(String text) {
            this.mText = text;
        }

        public int getDrawable() {
            return 0;
        }

        public String getText() {
            return this.mText;
        }
    }

    public TitleBar(Context context2) {
        super(context2);
        this.context = context2;
        init(context2);
    }

    public TitleBar(Context context2, int flag2) {
        super(context2);
        this.flag = flag2;
        init(context2);
    }

    public TitleBar(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.context = context2;
        init(context2);
    }

    public TitleBar(Context context2, AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        this.context = context2;
        init(context2);
    }

    private void init(Context context2) {
        this.mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        if (this.mImmersive) {
            this.mStatusBarHeight = getStatusBarHeight();
        }
        this.mActionPadding = dip2px(5);
        this.mOutPadding = dip2px(8);
        this.mHeight = dip2px(48);
        initView(context2);
    }

    private void initView(Context context2) {
        this.mLeftText = new TextView(context2);
        this.mCenterLayout = new LinearLayout(context2);
        this.mRightLayout = new LinearLayout(context2);
        this.mDividerView = new View(context2);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        this.mLeftText.setTextSize(15.0f);
        this.mLeftText.setSingleLine();
        this.mLeftText.setGravity(16);
        this.mLeftText.setPadding(this.mOutPadding, 0, this.mOutPadding, 0);
        this.mCenterText = new TextView(context2);
        this.mSubTitleText = new TextView(context2);
        this.mCenterLayout.addView(this.mCenterText);
        this.mCenterLayout.addView(this.mSubTitleText);
        this.mCenterText.setTextSize(18.0f);
        this.mCenterText.setTypeface(Typeface.defaultFromStyle(1));
        this.mCenterText.setSingleLine();
        this.mCenterText.setGravity(17);
        this.mCenterText.setEllipsize(TruncateAt.END);
        this.mSubTitleText.setTextSize(12.0f);
        this.mSubTitleText.setSingleLine();
        this.mSubTitleText.setGravity(17);
        this.mSubTitleText.setEllipsize(TruncateAt.END);
        this.mCenterLayout.setGravity(17);
        this.mRightLayout.setPadding(this.mOutPadding, 0, this.mOutPadding, 0);
        addView(this.mLeftText, layoutParams);
        addView(this.mCenterLayout);
        addView(this.mRightLayout, layoutParams);
        addView(this.mDividerView, new LayoutParams(-1, 1));
    }

    public void setImmersive(boolean immersive) {
        this.mImmersive = immersive;
        if (this.mImmersive) {
            this.mStatusBarHeight = getStatusBarHeight();
        } else {
            this.mStatusBarHeight = 0;
        }
    }

    public void setHeight(int height) {
        this.mHeight = height;
        setMeasuredDimension(getMeasuredWidth(), this.mHeight);
    }

    public int getmHeight() {
        return this.mHeight + this.mStatusBarHeight;
    }

    public void setLeftImageResource(int resId) {
        this.mLeftText.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
    }

    public void setLeftClickListener(OnClickListener l) {
        this.mLeftText.setOnClickListener(l);
    }

    public void setLeftText(CharSequence title) {
        this.mLeftText.setText(title);
    }

    public void setLeftText(int resid) {
        this.mLeftText.setText(resid);
    }

    public void setLeftTextSize(float size) {
        this.mLeftText.setTextSize(size);
    }

    public void setLeftTextColor(int color) {
        this.mLeftText.setTextColor(color);
    }

    public void setLeftVisible(boolean visible) {
        this.mLeftText.setVisibility(visible ? 0 : 8);
    }

    public void setRightLayoutVisible(boolean visible) {
        this.mRightLayout.setVisibility(visible ? 0 : 8);
    }

    public void setRightLayoutEnable(boolean enable) {
        this.mRightLayout.setEnabled(enable);
    }

    public void setEditTextHint(String text) {
        this.mSearchEditText.setVisibility(0);
        this.mSearchEditText.setHint(text);
    }

    public void setEditTextListener(TextWatcher listener) {
        this.mSearchEditText.addTextChangedListener(listener);
    }

    public void setFocusable() {
        this.mSearchEditText.setFocusable(false);
    }

    public void setTitle(CharSequence title) {
        int index = title.toString().indexOf("\n");
        if (index > 0) {
            setTitle(title.subSequence(0, index), title.subSequence(index + 1, title.length()), 1);
            return;
        }
        int index2 = title.toString().indexOf("\t");
        if (index2 > 0) {
            setTitle(title.subSequence(0, index2), "  " + title.subSequence(index2 + 1, title.length()), 0);
            return;
        }
        this.mCenterText.setText(title);
        this.mSubTitleText.setVisibility(8);
    }

    public View getTitleView() {
        return this.mCenterText;
    }

    private void setTitle(CharSequence title, CharSequence subTitle, int orientation) {
        this.mCenterLayout.setOrientation(orientation);
        this.mCenterText.setText(title);
        this.mSubTitleText.setText(subTitle);
        this.mSubTitleText.setVisibility(0);
    }

    public void setCenterClickListener(OnClickListener l) {
        this.mCenterLayout.setOnClickListener(l);
    }

    public void setTitle(int resid) {
        setTitle((CharSequence) getResources().getString(resid));
    }

    public void setTitleColor(int resid) {
        this.mCenterText.setTextColor(resid);
    }

    public void setTitleSize(float size) {
        this.mCenterText.setTextSize(size);
    }

    public void setTitleBackground(int resid) {
        this.mCenterText.setBackgroundResource(resid);
    }

    public void setSubTitleColor(int resid) {
        this.mSubTitleText.setTextColor(resid);
    }

    public void setSubTitleSize(float size) {
        this.mSubTitleText.setTextSize(size);
    }

    public void setCustomTitle(View titleView) {
        if (titleView == null) {
            this.mCenterText.setVisibility(0);
            if (this.mCustomCenterView != null) {
                this.mCenterLayout.removeView(this.mCustomCenterView);
                return;
            }
            return;
        }
        if (this.mCustomCenterView != null) {
            this.mCenterLayout.removeView(this.mCustomCenterView);
        }
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.mCustomCenterView = titleView;
        this.mCenterLayout.addView(titleView, layoutParams);
        this.mCenterText.setVisibility(8);
    }

    public void setDivider(Drawable drawable) {
        this.mDividerView.setBackgroundDrawable(drawable);
    }

    public void setDividerColor(int color) {
        this.mDividerView.setBackgroundColor(color);
    }

    public void setDividerHeight(int dividerHeight) {
        this.mDividerView.getLayoutParams().height = dividerHeight;
    }

    public void setActionTextColor(int colorResId) {
        this.mActionTextColor = colorResId;
    }

    public void setOnTitleClickListener(OnClickListener listener) {
        this.mCenterText.setOnClickListener(listener);
    }

    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof Action) {
            ((Action) tag).performAction(view);
        }
    }

    public void addActions(ActionList actionList) {
        int actions = actionList.size();
        for (int i = 0; i < actions; i++) {
            addAction((Action) actionList.get(i));
        }
    }

    public View addAction(Action action) {
        return addAction(action, this.mRightLayout.getChildCount());
    }

    public void setCenterTitleRightLayout(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mCenterText.setCompoundDrawables(null, null, drawable, null);
    }

    public View addAction(Action action, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -1);
        View view = inflateAction(action);
        this.mRightLayout.addView(view, index, params);
        return view;
    }

    public void removeAllActions() {
        this.mRightLayout.removeAllViews();
    }

    public void removeActionAt(int index) {
        this.mRightLayout.removeViewAt(index);
    }

    public void removeAction(Action action) {
        int childCount = this.mRightLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.mRightLayout.getChildAt(i);
            if (view != null) {
                Object tag = view.getTag();
                if ((tag instanceof Action) && tag.equals(action)) {
                    this.mRightLayout.removeView(view);
                }
            }
        }
    }

    public int getActionCount() {
        return this.mRightLayout.getChildCount();
    }

    private View inflateAction(Action action) {
        View view;
        if (TextUtils.isEmpty(action.getText())) {
            ImageView img = new ImageView(getContext());
            img.setImageResource(action.getDrawable());
            view = img;
        } else {
            TextView text = new TextView(getContext());
            text.setGravity(17);
            text.setText(action.getText());
            text.setTextSize(15.0f);
            if (this.mActionTextColor != 0) {
                text.setTextColor(this.mActionTextColor);
            }
            view = text;
        }
        view.setPadding(this.mActionPadding, 0, this.mActionPadding, 0);
        view.setTag(action);
        view.setOnClickListener(this);
        return view;
    }

    public View getViewByAction(Action action) {
        return findViewWithTag(action);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        if (MeasureSpec.getMode(heightMeasureSpec) != 1073741824) {
            height = this.mHeight + this.mStatusBarHeight;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.mHeight, Ints.MAX_POWER_OF_TWO);
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec) + this.mStatusBarHeight;
        }
        measureChild(this.mLeftText, widthMeasureSpec, heightMeasureSpec);
        measureChild(this.mRightLayout, widthMeasureSpec, heightMeasureSpec);
        if (this.mLeftText.getMeasuredWidth() > this.mRightLayout.getMeasuredWidth()) {
            this.mCenterLayout.measure(MeasureSpec.makeMeasureSpec(this.mScreenWidth - (this.mLeftText.getMeasuredWidth() * 2), Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
        } else {
            this.mCenterLayout.measure(MeasureSpec.makeMeasureSpec(this.mScreenWidth - (this.mRightLayout.getMeasuredWidth() * 2), Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
        }
        measureChild(this.mDividerView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mLeftText.layout(0, this.mStatusBarHeight, this.mLeftText.getMeasuredWidth(), this.mLeftText.getMeasuredHeight() + this.mStatusBarHeight);
        this.mRightLayout.layout(this.mScreenWidth - this.mRightLayout.getMeasuredWidth(), this.mStatusBarHeight, this.mScreenWidth, this.mRightLayout.getMeasuredHeight() + this.mStatusBarHeight);
        if (this.mLeftText.getMeasuredWidth() > this.mRightLayout.getMeasuredWidth()) {
            this.mCenterLayout.layout(this.mLeftText.getMeasuredWidth(), this.mStatusBarHeight, this.mScreenWidth - this.mLeftText.getMeasuredWidth(), getMeasuredHeight());
        } else {
            this.mCenterLayout.layout(this.mRightLayout.getMeasuredWidth(), this.mStatusBarHeight, this.mScreenWidth - this.mRightLayout.getMeasuredWidth(), getMeasuredHeight());
        }
        this.mDividerView.layout(0, getMeasuredHeight() - this.mDividerView.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
    }

    public static int dip2px(int dpValue) {
        return (int) ((((float) dpValue) * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag2) {
        this.flag = flag2;
        if (this.mSearchEditText == null) {
            this.mSearchEditText = new EditText(this.context);
            this.mCenterLayout.addView(this.mSearchEditText);
            this.mSearchEditText.setTextSize(14.0f);
            this.mSearchEditText.setSingleLine();
            this.mSearchEditText.setGravity(19);
            this.mSearchEditText.setBackground(ContextCompat.getDrawable(this.context, R.drawable.search_background));
            this.mSearchEditText.setEllipsize(TruncateAt.END);
            this.mSearchEditText.setMinWidth(TinkerReport.KEY_LOADED_INTERPRET_GET_INSTRUCTION_SET_ERROR);
        }
        if (flag2 == 1) {
            this.mSearchEditText.setVisibility(8);
            this.mSearchEditText.setFocusable(false);
        } else if (flag2 == 2) {
            this.mSearchEditText.setFocusable(true);
        }
    }

    public EditText getmSearchEditText() {
        this.mSearchEditText.setFocusable(false);
        return this.mSearchEditText;
    }

    public EditText getmSearchEditText(boolean flag2) {
        this.mSearchEditText.setFocusable(flag2);
        return this.mSearchEditText;
    }

    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int resourceId = res.getIdentifier(key, "dimen", Constants.APPSYSTEM);
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
