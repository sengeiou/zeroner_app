package com.iwown.device_module.device_add_sport.activity;

import com.iwown.ble_module.iwown.bean.SportType;
import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.device_add_sport.bean.AddSport;
import java.util.List;

public class AddSupportSportsContract {

    public interface AddSportPresenter extends BasePresenter {
        List<AddSport> defaultIcon(int i);

        void sendSupportSportCommand(List<AddSport> list);

        SportType supportSports();

        List<AddSport> unCheckSupportSports(SportType sportType);
    }

    public interface addSportView extends BaseView<AddSportPresenter> {
    }
}
