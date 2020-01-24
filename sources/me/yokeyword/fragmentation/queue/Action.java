package me.yokeyword.fragmentation.queue;

import android.support.v4.app.FragmentManager;

public abstract class Action {
    public static final int ACTION_BACK = 3;
    public static final int ACTION_NORMAL = 0;
    public static final int ACTION_POP = 1;
    public static final int ACTION_POP_MOCK = 2;
    public static final int BUFFER_TIME = 60;
    public int action;
    public long duration;
    public FragmentManager fragmentManager;

    public abstract void run();

    public Action() {
        this.action = 0;
        this.duration = 0;
    }

    public Action(int action2) {
        this.action = 0;
        this.duration = 0;
        this.action = action2;
        if (action2 == 2) {
            this.duration = 60;
        }
    }

    public Action(int action2, FragmentManager fragmentManager2) {
        this(action2);
        this.fragmentManager = fragmentManager2;
    }
}
