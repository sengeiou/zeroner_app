package me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.iwown.lib_common.R;

public class DefaultHorizontalAnimator extends FragmentAnimator implements Parcelable {
    public static final Creator<DefaultHorizontalAnimator> CREATOR = new Creator<DefaultHorizontalAnimator>() {
        public DefaultHorizontalAnimator createFromParcel(Parcel in) {
            return new DefaultHorizontalAnimator(in);
        }

        public DefaultHorizontalAnimator[] newArray(int size) {
            return new DefaultHorizontalAnimator[size];
        }
    };

    public DefaultHorizontalAnimator() {
        this.enter = R.anim.h_fragment_enter;
        this.exit = R.anim.h_fragment_exit;
        this.popEnter = R.anim.h_fragment_pop_enter;
        this.popExit = R.anim.h_fragment_pop_exit;
    }

    protected DefaultHorizontalAnimator(Parcel in) {
        super(in);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public int describeContents() {
        return 0;
    }
}
