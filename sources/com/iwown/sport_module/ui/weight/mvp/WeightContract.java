package com.iwown.sport_module.ui.weight.mvp;

import android.content.Context;
import com.iwown.data_link.weight.ScaleBodyFat;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.iwown.sport_module.ui.base.DBasePresenter;
import com.iwown.sport_module.ui.base.DBaseView;
import java.util.List;

public class WeightContract {

    public interface WPrecenter extends DBasePresenter {
        ScaleBodyFat getIndexScaleBodyFat(int i);
    }

    public interface WeightView extends DBaseView<WPrecenter> {
        void dismissLoading();

        Context getContext();

        void showLoading();

        void showRWSize(int i);

        void showUIDatas(List<WeightShowData> list, float f);
    }
}
