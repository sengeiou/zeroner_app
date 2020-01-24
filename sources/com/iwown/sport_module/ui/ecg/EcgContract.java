package com.iwown.sport_module.ui.ecg;

import android.content.Context;
import com.iwown.data_link.ecg.EcgHasDataNet.EcgHasData;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.ui.base.DBasePresenter;
import com.iwown.sport_module.ui.base.DBaseView;
import java.util.List;
import java.util.Map;

public class EcgContract {

    public interface EcgDataView extends DBaseView<EcgPresenter> {
        void dismissLoading();

        Context getContext();

        void noEcgDataByDay();

        void showDataOver();

        void showLoading();

        void updateCalendar(Map<String, EcgHasData> map);
    }

    public interface EcgPresenter extends DBasePresenter {
        void braceletToView();

        void downLoadEcgDataByDay(DateUtil dateUtil);

        void loadEcgCalendarStatus(long j);

        List<EcgViewDataBean> loadEcgDataByTime(long j);

        void loadEcgHasData(long j, int i, int i2);
    }
}
