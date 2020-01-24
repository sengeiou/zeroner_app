package com.iwown.data_link.eventbus;

public class EpoEvent {
    public static int STATE_DOWNLOAD_FILE_FAIL = 6;
    public static int STATE_END = 1;
    public static int STATE_FAIL = 2;
    public static int STATE_INIT = 5;
    public static int STATE_LOW_BATTERY = 4;
    public static int STATE_NEED_RETRY_TIP = 7;
    public static int STATE_SENDING = 3;
    private int progress = 0;
    private int state = -1;

    public EpoEvent(int state2, int progress2) {
        this.progress = progress2;
        this.state = state2;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }
}
