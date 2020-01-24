package com.iwown.sport_module.ui.repository;

import android.content.Context;
import com.iwown.sport_module.ui.repository.local.LocalHeartRepository;
import com.iwown.sport_module.ui.repository.local.LocalSleepRepository;
import com.iwown.sport_module.ui.repository.remote.RemoteHeartRepository;
import com.iwown.sport_module.ui.repository.remote.RemoteSleepRepository;

public class DataRepositoryHelper {
    public static SleepDataRepository getSleepDataRepository(Context context) {
        return SleepDataRepository.getInstance(LocalSleepRepository.getInsatnce(context.getApplicationContext()), RemoteSleepRepository.getInsatnce(context.getApplicationContext()));
    }

    public static HeartDataRepository getHeartDataRepository(Context context) {
        return HeartDataRepository.getInstance(LocalHeartRepository.getInsatnce(context.getApplicationContext()), RemoteHeartRepository.getInsatnce(context.getApplicationContext()));
    }
}
