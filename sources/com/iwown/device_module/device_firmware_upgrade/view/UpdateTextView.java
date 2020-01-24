package com.iwown.device_module.device_firmware_upgrade.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.alibaba.android.arouter.utils.Consts;

public class UpdateTextView extends TextView {
    private static final String TAG = "UpdateTextView";
    private String mLastText;
    /* access modifiers changed from: private */
    public String mText;
    private TimeRunnable mTimeRunable;

    private class TimeRunnable implements Runnable {
        int index;

        private TimeRunnable() {
            this.index = 0;
        }

        /* access modifiers changed from: private */
        public void start() {
            UpdateTextView.this.removeCallbacks(this);
            UpdateTextView.this.postDelayed(this, 500);
        }

        /* access modifiers changed from: private */
        public void stop() {
            UpdateTextView.this.removeCallbacks(this);
        }

        public void run() {
            if (this.index == 0) {
                UpdateTextView.this.setText(UpdateTextView.this.mText);
            } else if (this.index == 1) {
                UpdateTextView.this.setText(UpdateTextView.this.mText + Consts.DOT);
            } else if (this.index == 2) {
                UpdateTextView.this.setText(UpdateTextView.this.mText + "..");
            } else {
                UpdateTextView.this.setText(UpdateTextView.this.mText + "...");
            }
            this.index++;
            this.index %= 4;
            UpdateTextView.this.postDelayed(this, 500);
        }
    }

    public UpdateTextView(Context context) {
        super(context);
    }

    public UpdateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UpdateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUpdateText(int resId) {
        setUpdateText(getContext().getString(resId));
    }

    public void setUpdateText(String text) {
        if (!TextUtils.equals(this.mLastText, text)) {
            this.mLastText = text;
            stop();
            if (TextUtils.isEmpty(text)) {
                setText(text);
            } else if (text.length() <= 4) {
                setText(text);
            } else if (text.charAt(text.length() - 1) == '.' && text.charAt(text.length() - 2) == '.' && text.charAt(text.length() - 3) == '.') {
                this.mText = text.substring(0, text.length() - 3);
                start();
            }
        }
    }

    public void start() {
        if (this.mTimeRunable == null) {
            this.mTimeRunable = new TimeRunnable();
        }
        this.mTimeRunable.start();
    }

    public void stop() {
        if (this.mTimeRunable != null) {
            this.mTimeRunable.stop();
        }
    }
}
