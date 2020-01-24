package com.iwown.sport_module.contract;

import com.iwown.sport_module.pojo.active.SportAllData;

public interface ActiveTodayContract {

    public interface ActiveTodayModel {
        SportAllData getAllData(long j, int i, int i2, int i3, String str, boolean z);
    }

    public interface ActiveTodayPresenter {
        SportAllData getAllData(long j, int i, int i2, int i3, String str, boolean z);
    }

    public interface ActivityTodayView {
        void netReqLoading(boolean z);

        void refreshDFrgUI(int i, int i2);

        void refreshUI(SportAllData sportAllData);
    }
}
