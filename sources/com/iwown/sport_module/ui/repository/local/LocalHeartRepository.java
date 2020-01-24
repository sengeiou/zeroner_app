package com.iwown.sport_module.ui.repository.local;

import android.content.Context;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.sport_module.ui.repository.DataSource;

public class LocalHeartRepository implements DataSource {
    private static LocalHeartRepository instance;
    private Context context;

    private LocalHeartRepository(Context context1) {
        this.context = context1;
    }

    public static LocalHeartRepository getInsatnce(Context context2) {
        if (instance == null) {
            instance = new LocalHeartRepository(context2);
        }
        return instance;
    }

    public Context getContext() {
        return this.context;
    }

    public void getHeartByTime(HeartShowData heartShowData) {
        ModuleRouteHeartService.getInstance().getHeartByTime(heartShowData);
    }
}
