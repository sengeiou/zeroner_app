package com.google.android.cameraview;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

public class AspectRatio implements Comparable<AspectRatio>, Parcelable {
    public static final Creator<AspectRatio> CREATOR = new Creator<AspectRatio>() {
        public AspectRatio createFromParcel(Parcel source) {
            return AspectRatio.of(source.readInt(), source.readInt());
        }

        public AspectRatio[] newArray(int size) {
            return new AspectRatio[size];
        }
    };
    private static final SparseArrayCompat<SparseArrayCompat<AspectRatio>> sCache = new SparseArrayCompat<>(16);
    private final int mX;
    private final int mY;

    public static AspectRatio of(int x, int y) {
        int gcd = gcd(x, y);
        int x2 = x / gcd;
        int y2 = y / gcd;
        SparseArrayCompat<AspectRatio> arrayX = (SparseArrayCompat) sCache.get(x2);
        if (arrayX == null) {
            AspectRatio ratio = new AspectRatio(x2, y2);
            SparseArrayCompat<AspectRatio> arrayX2 = new SparseArrayCompat<>();
            arrayX2.put(y2, ratio);
            sCache.put(x2, arrayX2);
            return ratio;
        }
        AspectRatio ratio2 = (AspectRatio) arrayX.get(y2);
        if (ratio2 == null) {
            ratio2 = new AspectRatio(x2, y2);
            arrayX.put(y2, ratio2);
        }
        return ratio2;
    }

    public static AspectRatio parse(String s) {
        int position = s.indexOf(58);
        if (position == -1) {
            throw new IllegalArgumentException("Malformed aspect ratio: " + s);
        }
        try {
            return of(Integer.parseInt(s.substring(0, position)), Integer.parseInt(s.substring(position + 1)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Malformed aspect ratio: " + s, e);
        }
    }

    private AspectRatio(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public boolean matches(Size size) {
        int gcd = gcd(size.getWidth(), size.getHeight());
        return this.mX == size.getWidth() / gcd && this.mY == size.getHeight() / gcd;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof AspectRatio)) {
            return false;
        }
        AspectRatio ratio = (AspectRatio) o;
        if (!(this.mX == ratio.mX && this.mY == ratio.mY)) {
            z = false;
        }
        return z;
    }

    public String toString() {
        return this.mX + ":" + this.mY;
    }

    public float toFloat() {
        return ((float) this.mX) / ((float) this.mY);
    }

    public int hashCode() {
        return this.mY ^ ((this.mX << 16) | (this.mX >>> 16));
    }

    public int compareTo(@NonNull AspectRatio another) {
        if (equals(another)) {
            return 0;
        }
        if (toFloat() - another.toFloat() > 0.0f) {
            return 1;
        }
        return -1;
    }

    public AspectRatio inverse() {
        return of(this.mY, this.mX);
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mX);
        dest.writeInt(this.mY);
    }
}
