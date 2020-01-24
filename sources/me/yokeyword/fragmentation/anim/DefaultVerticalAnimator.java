package me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.iwown.lib_common.R;

public class DefaultVerticalAnimator extends FragmentAnimator implements Parcelable {
    public static final Creator<DefaultVerticalAnimator> CREATOR = new Creator<DefaultVerticalAnimator>() {
        public DefaultVerticalAnimator createFromParcel(Parcel in) {
            return new DefaultVerticalAnimator(in);
        }

        public DefaultVerticalAnimator[] newArray(int size) {
            return new DefaultVerticalAnimator[size];
        }
    };

    public DefaultVerticalAnimator() {
        this.enter = R.anim.v_fragment_enter;
        this.exit = R.anim.v_fragment_exit;
        this.popEnter = R.anim.v_fragment_pop_enter;
        this.popExit = R.anim.v_fragment_pop_exit;
    }

    protected DefaultVerticalAnimator(Parcel in) {
        super(in);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public int describeContents() {
        return 0;
    }
}
