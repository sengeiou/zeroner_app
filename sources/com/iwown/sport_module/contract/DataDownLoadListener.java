package com.iwown.sport_module.contract;

public interface DataDownLoadListener {
    void downloadFinish();

    void downloadProgress(int i);

    void downloadStart();

    void downloadTimeOut();
}
