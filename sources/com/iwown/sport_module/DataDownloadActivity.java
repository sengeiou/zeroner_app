package com.iwown.sport_module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.RouteUtils;
import com.iwown.device_module.common.view.HorizontalProgressBar;
import com.iwown.device_module.common.view.HorizontalProgressBar.ProgressListener;
import com.iwown.sport_module.contract.DataDownLoadListener;
import com.iwown.sport_module.contract.DataDownloadPresenter;

@Route(path = "/sport/DataDownloadActivity")
public class DataDownloadActivity extends AppCompatActivity implements DataDownLoadListener, ProgressListener {
    private HorizontalProgressBar mProgressBar;
    private DataDownloadPresenter presenter;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_data_down);
        this.mProgressBar = (HorizontalProgressBar) findViewById(R.id.import_progressbar);
        this.mProgressBar.setProgressListener(this);
        this.mProgressBar.initProgress();
        this.presenter = new DataDownloadPresenter(this);
        this.presenter.downloadAll();
    }

    public void downloadProgress(int progress) {
        this.mProgressBar.setProgressWithAnimation((float) progress);
    }

    public void downloadTimeOut() {
        this.presenter.onDestory();
        RouteUtils.startAPPMainActivitry();
    }

    public void downloadStart() {
        this.mProgressBar.setProgressWithAnimation(0.0f);
    }

    public void downloadFinish() {
        this.presenter.onDestory();
        RouteUtils.startAPPMainActivitry();
    }

    public void currentProgressListener(float currentProgress) {
    }
}
