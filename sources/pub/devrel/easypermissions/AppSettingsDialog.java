package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

public class AppSettingsDialog implements Parcelable {
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final Creator<AppSettingsDialog> CREATOR = new Creator<AppSettingsDialog>() {
        public AppSettingsDialog createFromParcel(Parcel in) {
            return new AppSettingsDialog(in);
        }

        public AppSettingsDialog[] newArray(int size) {
            return new AppSettingsDialog[size];
        }
    };
    public static final int DEFAULT_SETTINGS_REQ_CODE = 16061;
    static final String EXTRA_APP_SETTINGS = "extra_app_settings";
    private Object mActivityOrFragment;
    private Context mContext;
    private final String mNegativeButtonText;
    private final String mPositiveButtonText;
    private final String mRationale;
    private final int mRequestCode;
    @StyleRes
    private final int mThemeResId;
    private final String mTitle;

    public static class Builder {
        private final Object mActivityOrFragment;
        private final Context mContext;
        private String mNegativeButtonText;
        private String mPositiveButtonText;
        private String mRationale;
        private int mRequestCode = -1;
        @StyleRes
        private int mThemeResId = -1;
        private String mTitle;

        public Builder(@NonNull Activity activity) {
            this.mActivityOrFragment = activity;
            this.mContext = activity;
        }

        public Builder(@NonNull Fragment fragment) {
            this.mActivityOrFragment = fragment;
            this.mContext = fragment.getContext();
        }

        public Builder(@NonNull android.app.Fragment fragment) {
            this.mActivityOrFragment = fragment;
            this.mContext = fragment.getActivity();
        }

        public Builder setThemeResId(@StyleRes int themeResId) {
            this.mThemeResId = themeResId;
            return this;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setTitle(@StringRes int title) {
            this.mTitle = this.mContext.getString(title);
            return this;
        }

        public Builder setRationale(String rationale) {
            this.mRationale = rationale;
            return this;
        }

        public Builder setRationale(@StringRes int rationale) {
            this.mRationale = this.mContext.getString(rationale);
            return this;
        }

        public Builder setPositiveButton(String text) {
            this.mPositiveButtonText = text;
            return this;
        }

        public Builder setPositiveButton(@StringRes int textId) {
            this.mPositiveButtonText = this.mContext.getString(textId);
            return this;
        }

        public Builder setNegativeButton(String text) {
            this.mNegativeButtonText = text;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId) {
            this.mNegativeButtonText = this.mContext.getString(textId);
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.mRequestCode = requestCode;
            return this;
        }

        public AppSettingsDialog build() {
            this.mRationale = TextUtils.isEmpty(this.mRationale) ? this.mContext.getString(R.string.rationale_ask_again) : this.mRationale;
            this.mTitle = TextUtils.isEmpty(this.mTitle) ? this.mContext.getString(R.string.title_settings_dialog) : this.mTitle;
            this.mPositiveButtonText = TextUtils.isEmpty(this.mPositiveButtonText) ? this.mContext.getString(17039370) : this.mPositiveButtonText;
            this.mNegativeButtonText = TextUtils.isEmpty(this.mNegativeButtonText) ? this.mContext.getString(17039360) : this.mNegativeButtonText;
            this.mRequestCode = this.mRequestCode > 0 ? this.mRequestCode : AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE;
            return new AppSettingsDialog(this.mActivityOrFragment, this.mThemeResId, this.mRationale, this.mTitle, this.mPositiveButtonText, this.mNegativeButtonText, this.mRequestCode);
        }
    }

    private AppSettingsDialog(Parcel in) {
        this.mThemeResId = in.readInt();
        this.mRationale = in.readString();
        this.mTitle = in.readString();
        this.mPositiveButtonText = in.readString();
        this.mNegativeButtonText = in.readString();
        this.mRequestCode = in.readInt();
    }

    private AppSettingsDialog(@NonNull Object activityOrFragment, @StyleRes int themeResId, @Nullable String rationale, @Nullable String title, @Nullable String positiveButtonText, @Nullable String negativeButtonText, int requestCode) {
        setActivityOrFragment(activityOrFragment);
        this.mThemeResId = themeResId;
        this.mRationale = rationale;
        this.mTitle = title;
        this.mPositiveButtonText = positiveButtonText;
        this.mNegativeButtonText = negativeButtonText;
        this.mRequestCode = requestCode;
    }

    static AppSettingsDialog fromIntent(Intent intent, Activity activity) {
        AppSettingsDialog dialog = (AppSettingsDialog) intent.getParcelableExtra(EXTRA_APP_SETTINGS);
        dialog.setActivityOrFragment(activity);
        return dialog;
    }

    private void setActivityOrFragment(Object activityOrFragment) {
        this.mActivityOrFragment = activityOrFragment;
        if (activityOrFragment instanceof Activity) {
            this.mContext = (Activity) activityOrFragment;
        } else if (activityOrFragment instanceof Fragment) {
            this.mContext = ((Fragment) activityOrFragment).getContext();
        } else if (activityOrFragment instanceof android.app.Fragment) {
            this.mContext = ((android.app.Fragment) activityOrFragment).getActivity();
        } else {
            throw new IllegalStateException("Unknown object: " + activityOrFragment);
        }
    }

    private void startForResult(Intent intent) {
        if (this.mActivityOrFragment instanceof Activity) {
            ((Activity) this.mActivityOrFragment).startActivityForResult(intent, this.mRequestCode);
        } else if (this.mActivityOrFragment instanceof Fragment) {
            ((Fragment) this.mActivityOrFragment).startActivityForResult(intent, this.mRequestCode);
        } else if (this.mActivityOrFragment instanceof android.app.Fragment) {
            ((android.app.Fragment) this.mActivityOrFragment).startActivityForResult(intent, this.mRequestCode);
        }
    }

    public void show() {
        startForResult(AppSettingsDialogHolderActivity.createShowDialogIntent(this.mContext, this));
    }

    /* access modifiers changed from: 0000 */
    public AlertDialog showDialog(OnClickListener positiveListener, OnClickListener negativeListener) {
        android.support.v7.app.AlertDialog.Builder builder;
        if (this.mThemeResId > 0) {
            builder = new android.support.v7.app.AlertDialog.Builder(this.mContext, this.mThemeResId);
        } else {
            builder = new android.support.v7.app.AlertDialog.Builder(this.mContext);
        }
        return builder.setCancelable(false).setTitle((CharSequence) this.mTitle).setMessage((CharSequence) this.mRationale).setPositiveButton((CharSequence) this.mPositiveButtonText, positiveListener).setNegativeButton((CharSequence) this.mNegativeButtonText, negativeListener).show();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.mThemeResId);
        dest.writeString(this.mRationale);
        dest.writeString(this.mTitle);
        dest.writeString(this.mPositiveButtonText);
        dest.writeString(this.mNegativeButtonText);
        dest.writeInt(this.mRequestCode);
    }
}
