package me.yokeyword.fragmentation.queue;

import android.os.Handler;
import java.util.LinkedList;
import java.util.Queue;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportHelper;

public class ActionQueue {
    private Handler mMainHandler;
    /* access modifiers changed from: private */
    public Queue<Action> mQueue = new LinkedList();

    public ActionQueue(Handler mainHandler) {
        this.mMainHandler = mainHandler;
    }

    public void enqueue(final Action action) {
        if (!isThrottleBACK(action)) {
            this.mMainHandler.post(new Runnable() {
                public void run() {
                    ActionQueue.this.enqueueAction(action);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void enqueueAction(Action action) {
        this.mQueue.add(action);
        if (this.mQueue.size() == 1) {
            handleAction();
        }
    }

    /* access modifiers changed from: private */
    public void handleAction() {
        if (!this.mQueue.isEmpty()) {
            Action action = (Action) this.mQueue.peek();
            action.run();
            executeNextAction(action);
        }
    }

    private void executeNextAction(Action action) {
        if (action.action == 1) {
            ISupportFragment top = SupportHelper.getTopFragment(action.fragmentManager);
            if (top != null) {
                action.duration = 60 + top.getSupportDelegate().getExitAnimDuration();
            } else {
                return;
            }
        }
        this.mMainHandler.postDelayed(new Runnable() {
            public void run() {
                ActionQueue.this.mQueue.poll();
                ActionQueue.this.handleAction();
            }
        }, action.duration);
    }

    private boolean isThrottleBACK(Action action) {
        if (action.action == 3) {
            Action head = (Action) this.mQueue.peek();
            if (head != null && head.action == 1) {
                return true;
            }
        }
        return false;
    }
}
