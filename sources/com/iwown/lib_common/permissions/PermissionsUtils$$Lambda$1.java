package com.iwown.lib_common.permissions;

import com.tbruyelle.rxpermissions2.Permission;
import io.reactivex.functions.Consumer;

final /* synthetic */ class PermissionsUtils$$Lambda$1 implements Consumer {
    static final Consumer $instance = new PermissionsUtils$$Lambda$1();

    private PermissionsUtils$$Lambda$1() {
    }

    public void accept(Object obj) {
        PermissionsUtils.lambda$handPermissionSTORAGE$1$PermissionsUtils((Permission) obj);
    }
}
