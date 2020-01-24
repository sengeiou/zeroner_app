package com.iwown.sport_module.view.scrolltextview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher.ViewFactory;
import com.iwown.lib_common.WeakHandler;
import com.socks.library.KLog;
import java.util.ArrayList;

public class ScrollTextView extends TextSwitcher implements ViewFactory {
    private static final int FLAG_START_AUTO_SCROLL = 0;
    private static final int FLAG_STOP_AUTO_SCROLL = 1;
    private long animDuration = 0;
    /* access modifiers changed from: private */
    public boolean can_scroll = true;
    /* access modifiers changed from: private */
    public int currentId = -1;
    /* access modifiers changed from: private */
    public WeakHandler handler;
    /* access modifiers changed from: private */
    public boolean isScrolling = false;
    private Context mContext;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener = null;
    private int mPadding = 5;
    private float mTextSize = 13.0f;
    private int textColor = Color.parseColor("#999999");
    /* access modifiers changed from: private */
    public ArrayList<String> textList = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(int i);

        void onShow(int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public ScrollTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setText(float textSize, int padding, int textColor2) {
        this.mTextSize = textSize;
        this.mPadding = padding;
        this.textColor = textColor2;
    }

    private void initText(Context context) {
        this.textList = new ArrayList<>();
        this.textList.add("我是第1条信息");
        this.textList.add("我是第2条信息");
        this.textList.add("我是第3条信息");
        this.textList.add("我是第4条信息");
    }

    public void setAnimTime(long animDuration2) {
        if (this.animDuration == 0) {
            this.animDuration = animDuration2;
            setFactory(this);
            Animation in = new TranslateAnimation(0.0f, 0.0f, (float) animDuration2, 0.0f);
            in.setDuration(animDuration2);
            in.setInterpolator(new AccelerateInterpolator());
            Animation out = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-animDuration2));
            out.setDuration(animDuration2);
            out.setInterpolator(new AccelerateInterpolator());
            setInAnimation(in);
            setOutAnimation(out);
        }
    }

    public void setTextStillTime(final long time) {
        this.currentId = -1;
        if (this.handler == null) {
            this.handler = new WeakHandler(new Callback() {
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            if (ScrollTextView.this.textList.size() > 0) {
                                ScrollTextView.this.currentId = ScrollTextView.this.currentId + 1;
                                ScrollTextView.this.setText((CharSequence) ScrollTextView.this.textList.get(ScrollTextView.this.currentId % ScrollTextView.this.textList.size()));
                                ScrollTextView.this.isScrolling = true;
                            } else {
                                ScrollTextView.this.isScrolling = false;
                                ScrollTextView.this.can_scroll = false;
                            }
                            if (!ScrollTextView.this.can_scroll) {
                                ScrollTextView.this.isScrolling = false;
                                break;
                            } else {
                                ScrollTextView.this.handler.sendEmptyMessageDelayed(0, time);
                                ScrollTextView.this.mOnItemClickListener.onShow(ScrollTextView.this.currentId % ScrollTextView.this.textList.size());
                                break;
                            }
                        case 1:
                            ScrollTextView.this.can_scroll = false;
                            ScrollTextView.this.isScrolling = false;
                            break;
                    }
                    return false;
                }
            }) {
            };
        }
    }

    public void setTextList(ArrayList<String> titles) {
        this.textList.clear();
        this.textList.addAll(titles);
        this.currentId = -1;
    }

    public void startAutoScroll() {
        this.can_scroll = true;
        this.handler.removeMessages(0);
        this.handler.sendEmptyMessage(0);
    }

    public void stopAutoScroll() {
        this.can_scroll = false;
    }

    public View makeView() {
        MarqueeText t = new MarqueeText(this.mContext);
        t.setGravity(19);
        t.setPadding(this.mPadding, this.mPadding, this.mPadding, this.mPadding);
        t.setTextColor(this.textColor);
        t.setTextSize(this.mTextSize);
        t.setFocusable(true);
        t.setClickable(true);
        t.setSingleLine();
        t.setEllipsize(TruncateAt.MARQUEE);
        KLog.d("--------" + getWidth() + "-----" + getHeight() + "  -------isfinish");
        t.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ScrollTextView.this.mOnItemClickListener != null && ScrollTextView.this.textList.size() > 0 && ScrollTextView.this.currentId != -1) {
                    ScrollTextView.this.mOnItemClickListener.onItemClick(ScrollTextView.this.currentId % ScrollTextView.this.textList.size());
                }
            }
        });
        return t;
    }
}
