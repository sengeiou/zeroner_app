package com.iwown.sport_module.gps.contract;

import com.iwown.data_link.data.CopySportAll;
import com.iwown.sport_module.gps.data.SportDetailItem;
import java.util.List;

public class SportDetailContract {

    public interface SportDetailPresenter {
        void getDetailGpsLocal(long j, int i, int i2);

        void getDetailGpsServer(long j, long j2, int i, int i2, int i3);

        void getTotalGps(long j, int i, int i2);
    }

    public interface SportDetailView {
        void loadAllSportFail();

        void loadAllSportSuccess(CopySportAll copySportAll);

        void loadPageDataFail();

        void loadPageDataSuccess(List<SportDetailItem> list);

        void loadPageServiceDataSuccess(int i, List<SportDetailItem> list);

        void loadServiceAllSportSuccess(CopySportAll copySportAll);
    }
}
