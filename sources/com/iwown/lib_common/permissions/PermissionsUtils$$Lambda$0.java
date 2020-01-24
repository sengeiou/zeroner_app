package com.iwown.lib_common.permissions;

import com.tbruyelle.rxpermissions2.Permission;
import io.reactivex.functions.Consumer;

final /* synthetic */ class PermissionsUtils$$Lambda$0 implements Consumer {
    static final Consumer $instance = new PermissionsUtils$$Lambda$0();

    private PermissionsUtils$$Lambda$0() {
    }

    public void accept(Object obj) {
        PermissionsUtils.lambda$handPermission$0$PermissionsUtils((Permission) obj);
    }
}
