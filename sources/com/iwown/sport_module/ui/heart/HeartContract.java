package com.iwown.sport_module.ui.heart;

import android.content.Context;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.HeartStatusData.ContentBean;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.ui.base.DBasePresenter;
import com.iwown.sport_module.ui.base.DBaseView;
import java.util.Map;

public class HeartContract {

    public interface HeartPresenter1 extends DBasePresenter {
        void loadData(DateUtil dateUtil);
    }

    public interface HeartView extends DBaseView<HeartPresenter1> {
        void dismissLoading();

        Context getContext();

        void showDatas(HeartShowData heartShowData);

        void showLoading();

        void updateCalendar(Map<String, ContentBean> map);
    }
}
