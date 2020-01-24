package com.iwown.sport_module.base;

import java.lang.ref.WeakReference;

public class BasePresenterImpl2<T> {
    private WeakReference<T> viewWeakReference = null;

    public BasePresenterImpl2(T view) {
        this.viewWeakReference = new WeakReference<>(view);
    }

    public WeakReference<T> getViewWeakReference() {
        return this.viewWeakReference;
    }

    public void setViewWeakReference(Object o) {
        this.viewWeakReference = new WeakReference<>(o);
    }

    public boolean isViewNotNull() {
        return this.viewWeakReference.get() != null;
    }

    public T getView() {
        return this.viewWeakReference.get();
    }
}
