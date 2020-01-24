package com.dinuscxj.refresh;

public interface IRefreshStatus {
    void pullProgress(float f, float f2);

    void pullToRefresh();

    void refreshing();

    void releaseToRefresh();

    void reset();
}
