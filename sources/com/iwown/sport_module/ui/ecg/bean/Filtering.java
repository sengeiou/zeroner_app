package com.iwown.sport_module.ui.ecg.bean;

import com.tencent.tinker.loader.shareutil.ShareConstants;

public class Filtering {
    public static int ECG_BLANK_SIZE = 30;
    public static int ECG_FILTER_CACHE_SIZE = 64;
    public static int ECG_FILTER_FIR_SIZE = 30;
    public static int ECG_FILTER_FIR_TH = 250;
    public static int ECG_FILTER_HZ = 250;
    public int[] blankTemp = new int[ECG_BLANK_SIZE];
    public int blankTotal;
    public int firCount;
    public int firCount2;
    public int[] firTemp = new int[ECG_FILTER_FIR_SIZE];
    public int[] firTemp2 = new int[ECG_FILTER_FIR_SIZE];
    public int lastDif;
    public int maxNum;
    public int[] medianTemp = new int[ECG_FILTER_CACHE_SIZE];
    public int minDif;
    public int[] src = new int[ECG_FILTER_CACHE_SIZE];
    public int sumDif;
    public int total;

    public void init() {
        this.total = 0;
        this.blankTotal = 0;
        for (int i = 0; i < ECG_FILTER_CACHE_SIZE; i++) {
            this.src[i] = 0;
            this.medianTemp[i] = 0;
        }
        for (int i2 = 0; i2 < ECG_FILTER_FIR_SIZE; i2++) {
            this.firTemp[i2] = 0;
            this.firTemp2[i2] = 0;
        }
        for (int i3 = 0; i3 < ECG_BLANK_SIZE; i3++) {
            this.blankTemp[i3] = 0;
        }
    }

    /* access modifiers changed from: 0000 */
    public int ecgSave(int data, int[] temp, int total2) {
        for (int i = 0; i < total2 - 1; i++) {
            temp[i] = temp[i + 1];
        }
        temp[total2 - 1] = data;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int ecgFir(int data, int[] temp) {
        for (int i = 0; i < ECG_FILTER_FIR_SIZE - 1; i++) {
            temp[i] = temp[i + 1];
        }
        temp[ECG_FILTER_FIR_SIZE - 1] = data;
        return ((((((((temp[0] + (temp[1] * 2)) + (temp[2] * 3)) + (temp[3] * 3)) + (temp[4] * 15)) + (temp[5] * 3)) + (temp[6] * 3)) + (temp[7] * 2)) + temp[8]) / 33;
    }

    /* access modifiers changed from: 0000 */
    public int ecgFir2(int data, int[] temp) {
        int th = ECG_FILTER_FIR_TH;
        int sum = data;
        for (int i = 0; i < ECG_FILTER_FIR_SIZE - 1; i++) {
            temp[i] = temp[i + 1];
            sum += temp[i];
        }
        temp[ECG_FILTER_FIR_SIZE - 1] = data;
        int sum2 = ((((((((temp[0] + (temp[1] * 3)) + (temp[2] * 3)) + (temp[3] * 3)) + (temp[4] * 3)) + (temp[5] * 3)) + (temp[6] * 3)) + (temp[7] * 3)) + temp[8]) / 23;
        int difSum = 0;
        for (int i2 = 4; i2 < 7; i2++) {
            difSum += Math.abs(temp[i2] - temp[i2 + 5]);
        }
        int difSum2 = difSum / 3;
        this.firCount++;
        if (this.firCount < ECG_FILTER_HZ && this.lastDif / 2 > ECG_FILTER_FIR_TH) {
            th = (this.lastDif * 2) / 3;
        }
        if (difSum2 > th) {
            if (Math.abs(temp[4]) > this.maxNum) {
                this.maxNum = Math.abs(temp[4]);
                sum2 = temp[4];
            }
            this.firCount = 0;
            if (this.firCount2 > 0) {
                this.lastDif = Math.max(difSum2, this.lastDif);
            } else {
                this.lastDif = difSum2;
            }
            this.firCount2++;
        } else {
            this.maxNum = 0;
            this.firCount2 = 0;
        }
        return sum2;
    }

    /* access modifiers changed from: 0000 */
    public int ecgMedianFiltering(int data, int[] temp, int total2, int length) {
        if (total2 <= 0 || length < 0 || length < total2) {
            return data;
        }
        int num = 0;
        int sum = 0;
        for (int i = 0; i < total2; i++) {
            num++;
            sum += temp[(length - i) - 1];
        }
        if (num > 0) {
            return sum / num;
        }
        return data;
    }

    public int filteringMain(int data, boolean flag) {
        int ret;
        int ret2 = data;
        ecgSave(ret2, this.medianTemp, ECG_FILTER_CACHE_SIZE);
        int nowtotal = this.total;
        if (nowtotal > ECG_FILTER_CACHE_SIZE) {
            nowtotal = ECG_FILTER_CACHE_SIZE;
        }
        int medianNum = ecgMedianFiltering(ret2, this.medianTemp, nowtotal, ECG_FILTER_CACHE_SIZE);
        this.total++;
        if (this.total > ECG_FILTER_FIR_SIZE) {
            ret = this.medianTemp[ECG_FILTER_CACHE_SIZE - (nowtotal / 2)] - medianNum;
        } else {
            ret = 0;
        }
        if (flag) {
            ret = ecgFir2(ecgFir(ret, this.firTemp), this.firTemp2);
        }
        if (this.total > 300000 || this.total < 0) {
            this.total = ECG_FILTER_CACHE_SIZE * 2;
        }
        return ret;
    }

    public int ecgGetBlankData(int data, int blank) {
        int ret = ShareConstants.ERROR_LOAD_GET_INTENT_FAIL;
        if (blank * 2 > ECG_BLANK_SIZE && blank < 3) {
            return ShareConstants.ERROR_LOAD_GET_INTENT_FAIL;
        }
        ecgSave(data, this.blankTemp, blank * 2);
        if (this.blankTotal < blank * 2) {
            this.blankTotal++;
        } else {
            int maxNum2 = 0;
            int i = 0;
            while (i < blank) {
                if (Math.abs(this.blankTemp[i]) > maxNum2) {
                    maxNum2 = Math.abs(this.blankTemp[i]);
                    ret = this.blankTemp[i];
                }
                i++;
            }
            this.blankTotal -= 5;
            if (maxNum2 > this.blankTemp[i]) {
                i++;
                this.blankTotal--;
            }
            if (maxNum2 > this.blankTemp[i]) {
                int i2 = i + 1;
                this.blankTotal--;
            }
        }
        return ret;
    }
}
