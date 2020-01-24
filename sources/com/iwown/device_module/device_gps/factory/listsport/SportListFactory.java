package com.iwown.device_module.device_gps.factory.listsport;

public class SportListFactory {
    public SportListAllExecutor getExecutor(int type) {
        if (type == 0) {
            return new SportListGpsTag();
        }
        if (type == 1) {
            return new SportListBallTag();
        }
        if (type == 2) {
            return new SportListOtherTag();
        }
        if (type == 3) {
            return new SportListSwimTag();
        }
        return new SportListNullTag();
    }
}
