package com.iwown.my_module.healthy.bbs;

import android.content.Context;
import android.content.Intent;

public class BroadcastUtils {
    public static void sendFinishActivityBroadcast(Context context) {
        context.sendBroadcast(new Intent(BBSActivity.RECEIVER_ACTION_FINISH_A));
    }
}
