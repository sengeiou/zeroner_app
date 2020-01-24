package com.iwown.my_module.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.my_module.R;

public class DeleteContactsDialog extends AbsCustomDialog implements OnClickListener {
    private View bottom_div_line;
    private TextView cancel;
    /* access modifiers changed from: private */
    public TextView content;
    boolean isCancel;
    Context mContext;
    private OnConfirmButtonClickListener mOnConfirmButtonClickListener;
    /* access modifiers changed from: private */
    public OnLinkClickedListener mOnLinkClickedListener;
    private TextView ok;
    private TextView title;

    public interface OnConfirmButtonClickListener {
        void onConfirmButtonClick(boolean z);
    }

    public interface OnLinkClickedListener {
        void onLinkClicked();
    }

    public void setOnConfirmButtonClickListener(OnConfirmButtonClickListener listener) {
        this.mOnConfirmButtonClickListener = listener;
    }

    public OnLinkClickedListener getOnLinkClickedListener() {
        return this.mOnLinkClickedListener;
    }

    public void setOnLinkClickedListener(OnLinkClickedListener onLinkClickedListener) {
        this.mOnLinkClickedListener = onLinkClickedListener;
    }

    public DeleteContactsDialog(Context context) {
        super(context);
    }

    public DeleteContactsDialog(Context context, boolean isCancel2) {
        super(context);
        this.isCancel = isCancel2;
        this.mContext = context;
    }

    public int getLayoutResID() {
        return R.layout.my_module_select_dialog_with_title;
    }

    public void initView() {
        this.ok = (TextView) findViewById(R.id.update_pop_ok);
        this.cancel = (TextView) findViewById(R.id.update_pop_cancel);
        this.content = (TextView) findViewById(R.id.update_message);
        this.title = (TextView) findViewById(R.id.title);
        this.bottom_div_line = findViewById(R.id.bottom_div_line);
        ClickableSpan clickableSpan = new ClickableSpan() {
            private TextPaint ds;
            private boolean isClick = false;

            public void onClick(View widget) {
                updateDrawState(this.ds);
                if (DeleteContactsDialog.this.mOnLinkClickedListener != null) {
                    DeleteContactsDialog.this.mOnLinkClickedListener.onLinkClicked();
                }
            }

            public void updateDrawState(TextPaint ds2) {
                this.ds = ds2;
                DeleteContactsDialog.this.content.setHighlightColor(DeleteContactsDialog.this.mContext.getResources().getColor(17170445));
                ds2.setColor(Color.parseColor("#0476E6"));
                ds2.setUnderlineText(true);
            }
        };
        UnderlineSpan underlineSpan = new UnderlineSpan();
        SpannableString str = new SpannableString(this.mContext.getString(R.string.privacy_tip));
        str.setSpan(clickableSpan, 0, str.length(), 33);
        str.setSpan(underlineSpan, 0, str.length(), 33);
        SpannableStringBuilder builder = new SpannableStringBuilder(this.mContext.getString(R.string.i_agree_to) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        builder.append(str);
        this.content.setText(builder);
        this.content.setMovementMethod(new LinkMovementMethod());
    }

    public void initData() {
    }

    public void initListener() {
        this.ok.setOnClickListener(this);
        this.cancel.setOnClickListener(this);
    }

    public int getWidth() {
        return -2;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 17;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.update_pop_ok) {
            dismiss();
            if (this.mOnConfirmButtonClickListener != null) {
                this.mOnConfirmButtonClickListener.onConfirmButtonClick(true);
            }
        } else if (v.getId() == R.id.update_pop_cancel) {
            dismiss();
            if (this.mOnConfirmButtonClickListener != null) {
                this.mOnConfirmButtonClickListener.onConfirmButtonClick(false);
            }
        }
    }

    public boolean getCancelable() {
        return this.isCancel;
    }

    public void setTitle(int resID) {
        this.title.setText(resID);
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setContent(int resID) {
        this.content.setText(resID);
    }

    public void setContent(String text) {
        this.content.setText(text);
    }

    public void setOk(int resID) {
        this.ok.setText(resID);
    }

    public void setCancel(int resID) {
        this.cancel.setText(resID);
    }

    private void makeVisible(boolean isVisible, View... views) {
        for (View view : views) {
            if (!isVisible) {
                view.setVisibility(8);
            } else {
                view.setVisibility(0);
            }
        }
    }

    public void changeCancelBtnVisible(boolean isVisible) {
        if (!isVisible) {
            makeVisible(false, this.cancel, this.bottom_div_line);
            return;
        }
        makeVisible(true, this.cancel, this.bottom_div_line);
    }
}
