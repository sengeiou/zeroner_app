package me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DefaultNoAnimator extends FragmentAnimator implements Parcelable {
    public static final Creator<DefaultNoAnimator> CREATOR = new Creator<DefaultNoAnimator>() {
        public DefaultNoAnimator createFromParcel(Parcel in) {
            return new DefaultNoAnimator(in);
        }

        public DefaultNoAnimator[] newArray(int size) {
            return new DefaultNoAnimator[size];
        }
    };

    public DefaultNoAnimator() {
        this.enter = 0;
        this.exit = 0;
        this.popEnter = 0;
        this.popExit = 0;
    }

    protected DefaultNoAnimator(Parcel in) {
        super(in);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public int describeContents() {
        return 0;
    }
}
