package com.android.tu.loadingdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LoadingDailog extends Dialog {

    public static class Builder {
        private Context context;
        private boolean isCancelOutside = false;
        private boolean isCancelable = false;
        private boolean isShowMessage = true;
        private String message;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder setMessage(String message2) {
            this.message = message2;
            return this;
        }

        public Builder setShowMessage(boolean isShowMessage2) {
            this.isShowMessage = isShowMessage2;
            return this;
        }

        public Builder setCancelable(boolean isCancelable2) {
            this.isCancelable = isCancelable2;
            return this;
        }

        public Builder setCancelOutside(boolean isCancelOutside2) {
            this.isCancelOutside = isCancelOutside2;
            return this;
        }

        public LoadingDailog create() {
            View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_loading, null);
            LoadingDailog loadingDailog = new LoadingDailog(this.context, R.style.MyDialogStyle);
            TextView msgText = (TextView) view.findViewById(R.id.tipTextView);
            if (this.isShowMessage) {
                msgText.setText(this.message);
            } else {
                msgText.setVisibility(8);
            }
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(this.isCancelable);
            loadingDailog.setCanceledOnTouchOutside(this.isCancelOutside);
            return loadingDailog;
        }
    }

    public LoadingDailog(Context context) {
        super(context);
    }

    public LoadingDailog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
