package com.iwown.sport_module.gps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class GpsAmapService extends Service {
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
