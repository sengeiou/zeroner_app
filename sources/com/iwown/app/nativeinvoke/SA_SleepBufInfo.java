package com.iwown.app.nativeinvoke;

public class SA_SleepBufInfo {
    public int completeFlag;
    public int datastatus;
    public SA_TimeInfo inSleepTime = new SA_TimeInfo();
    public SA_TimeInfo outSleepTime = new SA_TimeInfo();
    public SA_SleepDataInfo[] sleepdata = new SA_SleepDataInfo[this.total];
    public int total;
}
