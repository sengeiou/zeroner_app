package com.iwown.sport_module.gps;

import com.iwown.sport_module.gps.service.BaseLocationManger;

public class AmapLocationManger extends BaseLocationManger {
    public static AmapLocationManger instance = null;

    private AmapLocationManger() {
    }

    public static AmapLocationManger getInstance() {
        if (instance == null) {
            synchronized (AmapLocationManger.class) {
                if (instance == null) {
                    instance = new AmapLocationManger();
                }
            }
        }
        return instance;
    }
}
