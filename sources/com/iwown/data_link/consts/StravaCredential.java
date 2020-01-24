package com.iwown.data_link.consts;

import com.iwown.data_link.utils.AppConfigUtil;

public class StravaCredential {
    public static int getClientId() {
        if (AppConfigUtil.isIwownFitPro()) {
            return 24527;
        }
        if (AppConfigUtil.isZeronerHealthPro()) {
            return 21191;
        }
        if (AppConfigUtil.isNanfei_TRAX_GPS()) {
            return 25428;
        }
        if (AppConfigUtil.isDrviva()) {
            return 32590;
        }
        return 21191;
    }

    public static String getClientSecret() {
        if (AppConfigUtil.isIwownFitPro()) {
            return "8292803519dd7577745cf3fcd6a75681a49dcbb3";
        }
        if (AppConfigUtil.isZeronerHealthPro()) {
            return "1e692e963afc7cd8214d3e39dbda11e127814596";
        }
        if (AppConfigUtil.isNanfei_TRAX_GPS()) {
            return "ed8a78468f44618bd807b501cb16082f3e8cb06a";
        }
        if (AppConfigUtil.isDrviva()) {
            return "d56fd40d89e0a3c6c0513bf1532f63f3012f7dca";
        }
        return "1e692e963afc7cd8214d3e39dbda11e127814596";
    }
}
