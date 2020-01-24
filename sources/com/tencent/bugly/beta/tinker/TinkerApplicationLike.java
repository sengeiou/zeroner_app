package com.tencent.bugly.beta.tinker;

import android.app.Application;
import android.content.Intent;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/* compiled from: BUGLY */
public class TinkerApplicationLike extends DefaultApplicationLike {
    public static final String TAG = "Tinker.TinkerApplicationLike";
    private static ApplicationLike tinkerPatchApplicationLike;

    public TinkerApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
        setTinkerPatchApplicationLike(this);
    }

    private static void setTinkerPatchApplicationLike(ApplicationLike applicationLike) {
        tinkerPatchApplicationLike = applicationLike;
    }

    public static ApplicationLike getTinkerPatchApplicationLike() {
        return tinkerPatchApplicationLike;
    }
}
