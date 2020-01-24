package com.iwown.sport_module.ui.fatigue;

import android.content.Context;
import com.iwown.lib_common.views.fatigueview2.FatigueDataBean2;
import com.iwown.sport_module.ui.base.DBasePresenter;
import com.iwown.sport_module.ui.base.DBaseView;
import java.util.List;

public class FatigueContract {

    public interface FatiguePresenter1 extends DBasePresenter {
        void loadFatigueDatas();
    }

    public interface FatigueView extends DBaseView<FatiguePresenter1> {
        void dismissLoading();

        Context getContext();

        void showDataOver();

        void showDatas(List<FatigueDataBean2> list, boolean z);

        void showLoading();
    }
}
