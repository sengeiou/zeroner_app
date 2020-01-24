package com.iwown.sport_module.presenter;

import com.iwown.sport_module.activity.ActiveActivity;
import com.iwown.sport_module.base.BasePresenterImpl;
import com.iwown.sport_module.contract.ActiveTodayContract.ActiveTodayPresenter;
import com.iwown.sport_module.model.ActiveTodayModelImpl;
import com.iwown.sport_module.model.ActiveTodayModelImpl.LoadCallback;
import com.iwown.sport_module.pojo.active.SportAllData;
import com.iwown.sport_module.pojo.active.SportDetailsData;

public class ActiveTodayPresentImpl extends BasePresenterImpl<ActiveActivity, ActiveTodayModelImpl> implements LoadCallback<SportAllData>, ActiveTodayPresenter {
    public ActiveTodayPresentImpl(ActiveActivity view) {
        super(view);
        setMyModel(new ActiveTodayModelImpl());
        ((ActiveTodayModelImpl) getMyModel()).setMyCallback(this);
    }

    public void onSuccess(SportAllData allData) {
        if (isViewNotNull()) {
            ((ActiveActivity) getView()).refreshUI(allData);
            ((ActiveActivity) getView()).netReqLoading(false);
        }
    }

    public void onFail(Throwable e, int year, int month, int day, SportAllData allData) {
        if (isViewNotNull()) {
            ((ActiveActivity) getView()).refreshUI(allData);
            ((ActiveActivity) getView()).netReqLoading(false);
        }
    }

    public void startProcess() {
        if (isViewNotNull()) {
            ((ActiveActivity) getView()).netReqLoading(true);
        }
    }

    public void month28DataArrive(int year, int month) {
        if (isViewNotNull()) {
            ((ActiveActivity) getView()).refreshDFrgUI(year, month);
        }
    }

    public SportAllData getAllData(long uid, int year, int month, int day, String dataFrom, boolean needNet) {
        SportAllData allData = ((ActiveTodayModelImpl) getMyModel()).getAllData(uid, year, month, day, dataFrom, needNet);
        for (SportDetailsData detailsData : allData.getDetailsDatas()) {
            if (detailsData.getStr_res() == -1) {
                detailsData.setStrType("");
            } else {
                detailsData.setStrType(((ActiveActivity) getView()).getApplication().getString(detailsData.getStr_res()));
            }
        }
        return allData;
    }
}
