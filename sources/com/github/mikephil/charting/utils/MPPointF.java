package com.github.mikephil.charting.utils;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.github.mikephil.charting.utils.ObjectPool.Poolable;
import java.util.List;

public class MPPointF extends Poolable {
    public static final Creator<MPPointF> CREATOR = new Creator<MPPointF>() {
        public MPPointF createFromParcel(Parcel in) {
            MPPointF r = new MPPointF(0.0f, 0.0f);
            r.my_readFromParcel(in);
            return r;
        }

        public MPPointF[] newArray(int size) {
            return new MPPointF[size];
        }
    };
    private static ObjectPool<MPPointF> pool = ObjectPool.create(32, new MPPointF(0.0f, 0.0f));
    public float x;
    public float y;

    static {
        pool.setReplenishPercentage(0.5f);
    }

    public MPPointF() {
    }

    public MPPointF(float x2, float y2) {
        this.x = x2;
        this.y = y2;
    }

    public static MPPointF getInstance(float x2, float y2) {
        MPPointF result = (MPPointF) pool.get();
        result.x = x2;
        result.y = y2;
        return result;
    }

    public static MPPointF getInstance() {
        return (MPPointF) pool.get();
    }

    public static MPPointF getInstance(MPPointF copy) {
        MPPointF result = (MPPointF) pool.get();
        result.x = copy.x;
        result.y = copy.y;
        return result;
    }

    public static void recycleInstance(MPPointF instance) {
        pool.recycle(instance);
    }

    public static void recycleInstances(List<MPPointF> instances) {
        pool.recycle(instances);
    }

    public void my_readFromParcel(Parcel in) {
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    /* access modifiers changed from: protected */
    public Poolable instantiate() {
        return new MPPointF(0.0f, 0.0f);
    }
}
