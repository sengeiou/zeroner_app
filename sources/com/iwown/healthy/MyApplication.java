package com.iwown.healthy;

import com.tencent.tinker.loader.app.TinkerApplication;

public class MyApplication extends TinkerApplication {
    public MyApplication() {
        super(7, "com.iwown.healthy.MyApplicationLike", "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
