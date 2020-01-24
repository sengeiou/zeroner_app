package com.iwown.sport_module.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class IntentSendUtils {
    public static void sendUploadSleep(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.UPLOAD_Sleep_Action));
    }

    public static void sendUploadHeart(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.UPLOAD_Heart_Action));
    }

    public static void sendUploadEcg(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.UPLOAD_Ecg_Sport_Action));
    }

    public static void sendUploadAllData(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.UPLOAD_All_Action));
    }

    public static void sendDownloadSleep(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.Down_Sleep_Action_Today));
    }

    public static void sendDownloadHeart(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.Down_Heart_Action_Today));
    }

    public static void sendDownloadEcg(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.Down_Ecg_Action_Today));
    }

    public static void sendDownloadWeight(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.Down_Weight_Action_Today));
    }

    public static void sendDownloadFatigue(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.Down_Fatigue_Action_Today));
    }

    public static void sendDownloadBlood(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataNetService.Down_Blood_Action));
    }
}
