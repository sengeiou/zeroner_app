package com.iwown.data_link.base;

public class BaseDataShow {
    private int data_repository = -1;

    public void setData_repository(int data_repository2) {
        this.data_repository = data_repository2;
    }

    public boolean isLocalRepository() {
        return this.data_repository == 0;
    }

    public boolean isNetRepository() {
        return this.data_repository == 1;
    }
}
