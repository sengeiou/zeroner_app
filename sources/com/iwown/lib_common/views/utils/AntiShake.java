package com.iwown.lib_common.views.utils;

import java.util.ArrayList;
import java.util.List;

public class AntiShake {
    private List<OneClickUtil> utils = new ArrayList();

    public boolean check(Object o) {
        String flag;
        if (o == null) {
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            flag = String.valueOf(o.hashCode());
        }
        for (OneClickUtil util : this.utils) {
            if (util.getMethodName().equals(flag)) {
                return util.check();
            }
        }
        OneClickUtil clickUtil = new OneClickUtil(flag);
        this.utils.add(clickUtil);
        return clickUtil.check();
    }

    public boolean check() {
        return check(null);
    }
}
