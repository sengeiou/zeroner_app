package com.iwown.sport_module.Fragment.data.mvp;

import android.content.Context;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.ui.base.DBasePresenter;
import com.iwown.sport_module.ui.base.DBaseView;
import java.util.List;

public class HomeTraningContract {

    public interface HTPresenter1 extends DBasePresenter {
        void loadData(DateUtil dateUtil, boolean z);
    }

    public interface HTView extends DBaseView<HTPresenter1> {
        Context getContext();

        void showTrandingDatas(List<MultiItemEntity> list);
    }
}
