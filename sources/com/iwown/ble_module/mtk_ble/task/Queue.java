package com.iwown.ble_module.mtk_ble.task;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.LinkedList;
import java.util.List;

public class Queue<E> {
    private final Object mObject = new Object();
    private final LinkedList<E> mQueue = new LinkedList<>();

    public void addHead(E object) {
        synchronized (this.mQueue) {
            this.mQueue.add(0, object);
        }
        synchronized (this.mObject) {
            this.mObject.notifyAll();
        }
    }

    public void addFirst(E object) {
        synchronized (this.mQueue) {
            this.mQueue.addFirst(object);
        }
        synchronized (this.mObject) {
            this.mObject.notifyAll();
        }
    }

    public void addTail(E object) {
        synchronized (this.mQueue) {
            this.mQueue.add(object);
        }
        synchronized (this.mObject) {
            this.mObject.notifyAll();
        }
    }

    public void addAllHead(List<E> list) {
        synchronized (this.mQueue) {
            this.mQueue.addAll(0, list);
        }
        synchronized (this.mObject) {
            this.mObject.notifyAll();
        }
    }

    public void addAllSecond(List<E> list) {
        synchronized (this.mQueue) {
            this.mQueue.addAll(1, list);
        }
        synchronized (this.mObject) {
            this.mObject.notifyAll();
        }
    }

    public void addAllTail(List<E> list) {
        synchronized (this.mQueue) {
            this.mQueue.addAll(list);
        }
        synchronized (this.mObject) {
            this.mObject.notifyAll();
        }
    }

    public E get() {
        E e = null;
        if (this.mQueue.size() == 0) {
            try {
                synchronized (this.mObject) {
                    this.mObject.wait();
                }
            } catch (InterruptedException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        synchronized (this.mQueue) {
            if (this.mQueue.size() > 0) {
                e = this.mQueue.removeFirst();
            }
        }
        return e;
    }

    public E getNew() {
        E e = null;
        if (this.mQueue.size() == 0) {
            try {
                synchronized (this.mObject) {
                    this.mObject.wait();
                }
            } catch (InterruptedException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        synchronized (this.mQueue) {
            if (this.mQueue.size() > 0) {
                e = this.mQueue.getFirst();
            }
        }
        return e;
    }

    public LinkedList<E> getAllTaskNow() {
        return this.mQueue;
    }

    public E getNewNotWait() {
        E e;
        synchronized (this.mQueue) {
            if (this.mQueue.size() > 0) {
                e = this.mQueue.getFirst();
            } else {
                e = null;
            }
        }
        return e;
    }

    public E getLast() {
        E e = null;
        if (this.mQueue.size() == 0) {
            try {
                synchronized (this.mObject) {
                    this.mObject.wait();
                }
            } catch (InterruptedException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        synchronized (this.mQueue) {
            if (this.mQueue.size() > 0) {
                e = this.mQueue.getLast();
            }
        }
        return e;
    }

    public void remove() {
        if (this.mQueue.size() != 0) {
            synchronized (this.mQueue) {
                if (this.mQueue.size() > 0) {
                    this.mQueue.removeFirst();
                }
            }
        }
    }

    public void remove(E object) {
        if (this.mQueue.size() != 0) {
            synchronized (this.mQueue) {
                if (this.mQueue.size() > 0) {
                    this.mQueue.remove(object);
                }
            }
        }
    }

    public boolean contains(E object) {
        if (this.mQueue.size() != 0) {
            synchronized (this.mQueue) {
                if (this.mQueue.size() > 0) {
                    boolean contains = this.mQueue.contains(object);
                    return contains;
                }
            }
        }
        return false;
    }

    public void clear() {
        synchronized (this.mQueue) {
            this.mQueue.clear();
        }
    }

    public int size() {
        return this.mQueue.size();
    }
}
