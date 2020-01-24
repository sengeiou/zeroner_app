package com.iwown.sport_module.base;

import java.lang.ref.WeakReference;

public class BasePresenterImpl<T, K> {
    private K myModel = null;
    private WeakReference<T> viewWeakReference = null;

    public BasePresenterImpl(T view) {
        this.viewWeakReference = new WeakReference<>(view);
    }

    public WeakReference<T> getViewWeakReference() {
        return this.viewWeakReference;
    }

    public void setViewWeakReference(Object o) {
        this.viewWeakReference = new WeakReference<>(o);
    }

    public K getMyModel() {
        return this.myModel;
    }

    public void setMyModel(K myModel2) {
        this.myModel = myModel2;
    }

    public boolean isViewNotNull() {
        return this.viewWeakReference.get() != null;
    }

    public T getView() {
        return this.viewWeakReference.get();
    }
}
