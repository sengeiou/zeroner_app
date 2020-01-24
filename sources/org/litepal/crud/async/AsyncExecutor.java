package org.litepal.crud.async;

public abstract class AsyncExecutor {
    private Runnable pendingTask;

    public void submit(Runnable task) {
        this.pendingTask = task;
    }

    /* access modifiers changed from: 0000 */
    public void execute() {
        if (this.pendingTask != null) {
            new Thread(this.pendingTask).start();
        }
    }
}
