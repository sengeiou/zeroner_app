package com.iwown.sport_module.gps.service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.AmapLocationManger;
import com.iwown.sport_module.gps.GpsAmapService;
import com.iwown.sport_module.gps.ShowAmapMapActivity;
import com.iwown.sport_module.gps.activity.ShowMapActivity;
import com.iwown.sport_module.gps.data.GpsMsgData;
import com.iwown.sport_module.gps.data.MapActyUIEvent;
import org.greenrobot.eventbus.EventBus;

public class LocationImpl {
    public static int healthyType = 0;
    public static LocationImpl instance = null;
    private int sportType = 0;

    public static LocationImpl getInstance() {
        if (instance == null) {
            synchronized (LocationImpl.class) {
                if (instance == null) {
                    instance = new LocationImpl();
                }
            }
        }
        return instance;
    }

    public boolean isHealthy() {
        if (healthyType == 0) {
            if (AppConfigUtil.isHealthy()) {
                healthyType = 1;
            } else {
                healthyType = 2;
            }
        }
        if (healthyType == 1) {
            return true;
        }
        return false;
    }

    public void pauseLocation() {
        if (isHealthy()) {
            AmapLocationManger.getInstance().pauseLocation();
        } else {
            GoogleLocationManger.getInstance().pauseLocation();
        }
    }

    public void restartLocation() {
        if (isHealthy()) {
            AmapLocationManger.getInstance().restartLocation();
        } else {
            GoogleLocationManger.getInstance().restartLocation();
        }
    }

    public void stopLocationAndServer(Context context) {
        if (isHealthy()) {
            AmapLocationManger.getInstance().stopLocation();
            context.stopService(new Intent(context, GpsAmapService.class));
            return;
        }
        GoogleLocationManger.getInstance().stopLocation();
        context.stopService(new Intent(context, GpsGoogleNewService.class));
    }

    public void start(int sportType2, float weight) {
        this.sportType = sportType2;
        if (isHealthy()) {
            AmapLocationManger.getInstance().setSportType(sportType2);
            AmapLocationManger.getInstance().start();
            AmapLocationManger.getInstance().setWeight(weight);
            return;
        }
        GoogleLocationManger.getInstance().setSportType(sportType2);
        GoogleLocationManger.getInstance().start();
        GoogleLocationManger.getInstance().setWeight(weight);
    }

    public void finishLocation(Context context) {
        if (this.sportType == 0) {
            ModuleRouteDeviceInfoService.getInstance().controlRealTimeHR(false);
        }
        stopLocationAndServer(context);
        if (isHealthy()) {
            AmapLocationManger.getInstance().playEnd();
            if (AmapLocationManger.getInstance().isCanSave()) {
                Intent intent = new Intent(context, ShowAmapMapActivity.class);
                intent.putExtra("startTime", AmapLocationManger.getInstance().getTimeId());
                intent.putExtra("target", AmapLocationManger.getInstance().getTargetOk());
                context.startActivity(intent);
                EventBus.getDefault().post(new MapActyUIEvent(3));
                return;
            }
            finishDialog(context);
        } else if (GoogleLocationManger.getInstance().isCanSave()) {
            Intent intent2 = new Intent(context, ShowMapActivity.class);
            intent2.putExtra("startTime", GoogleLocationManger.getInstance().getTimeId());
            intent2.putExtra("target", GoogleLocationManger.getInstance().getTargetOk());
            context.startActivity(intent2);
            EventBus.getDefault().post(new MapActyUIEvent(3));
        } else {
            finishDialog(context);
        }
    }

    private void finishDialog(Context context) {
        Builder alertDialogBuilder = new Builder(context);
        alertDialogBuilder.setTitle((CharSequence) context.getString(R.string.sport_module_prompt));
        alertDialogBuilder.setPositiveButton((CharSequence) context.getString(R.string.exit), (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                EventBus.getDefault().post(new MapActyUIEvent(3));
            }
        });
        alertDialogBuilder.setNegativeButton((CharSequence) context.getString(R.string.sport_module_cancel), (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuilder.setMessage((CharSequence) context.getString(R.string.sport_module_gps_no_save) + "\n" + context.getString(R.string.device_module_dfu_confirmation_dialog_exit_message));
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void setTarget(int targetType, float target) {
        if (isHealthy()) {
            AmapLocationManger.getInstance().setTarget(targetType, target);
        } else {
            GoogleLocationManger.getInstance().setTarget(targetType, target);
        }
    }

    public void stopLocation() {
        if (isHealthy()) {
            AmapLocationManger.getInstance().stopLocation();
        } else {
            GoogleLocationManger.getInstance().stopLocation();
        }
        instance = null;
    }

    public int getSportType() {
        if (isHealthy()) {
            return AmapLocationManger.getInstance().getSportType();
        }
        return GoogleLocationManger.getInstance().getSportType();
    }

    public GpsMsgData getGpsMsgData() {
        if (isHealthy()) {
            return AmapLocationManger.getInstance().getGpsMsgData();
        }
        return GoogleLocationManger.getInstance().getGpsMsgData();
    }
}
