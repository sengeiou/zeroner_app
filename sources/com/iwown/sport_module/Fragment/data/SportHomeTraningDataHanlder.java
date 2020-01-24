package com.iwown.sport_module.Fragment.data;

import com.iwown.data_link.sport_data.HomeTrandingTodayData;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;

public class SportHomeTraningDataHanlder {
    public static List<HomeTrandingTodayData> getTodaySportDatas() {
        return ModuleRouteSportService.getInstance().getTodaySports(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), new DateUtil());
    }
}
