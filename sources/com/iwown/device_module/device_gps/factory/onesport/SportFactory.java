package com.iwown.device_module.device_gps.factory.onesport;

public class SportFactory {
    public SportAllExecutor getExecutor(int type) {
        if (type == 0) {
            return new SportGpsTag();
        }
        if (type == 1) {
            return new SportBallTag();
        }
        if (type == 2) {
            return new SportOtherTag();
        }
        if (type == 3) {
            return new SportSwimTag();
        }
        return new SportNullTag();
    }
}
