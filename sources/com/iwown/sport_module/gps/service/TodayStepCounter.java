package com.iwown.sport_module.gps.service;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class TodayStepCounter implements SensorEventListener {
    private int firstStep;
    private Context mContext;
    private int mStep;
    private float weight;

    public TodayStepCounter(Context context) {
        this.mContext = context;
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 19) {
            int counterStep = (int) event.values[0];
            if (this.firstStep == 0 || this.firstStep > counterStep) {
                this.firstStep = counterStep;
            }
            this.mStep = counterStep - this.firstStep;
            Log.d("TodayStepCounter", "当前总步数" + counterStep + "行走步数: " + this.mStep);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public int getFirstStep() {
        return this.firstStep;
    }

    public void setFirstStep(int firstStep2) {
        this.firstStep = firstStep2;
    }

    public int getmStep() {
        return this.mStep;
    }

    public void setmStep(int mStep2) {
        this.mStep = mStep2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public float getDistance() {
        return ((float) this.mStep) * 0.07f;
    }

    public void getCaloire() {
    }

    public void clear() {
        this.mStep = 0;
        this.firstStep = 0;
    }
}
