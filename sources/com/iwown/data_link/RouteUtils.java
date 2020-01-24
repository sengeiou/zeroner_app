package com.iwown.data_link;

import android.support.v4.app.Fragment;
import com.alibaba.android.arouter.launcher.ARouter;

public class RouteUtils {
    public static final String APP_Service = "/app/service";
    public static final String Activity_Device_AddUserActivity = "/device/AddUserActivity";
    public static final String Activity_Device_DataImportActivity = "/device/DataImportActivity";
    public static final String Activity_Device_DeviceListActivity = "/device/DeviceListActivity";
    public static final String Activity_Device_FirmwareUpgradeActivity = "/device/FirmwareUpgradeActivity";
    public static final String Activity_app_MainActivity = "/app/MainActivity";
    public static final String Activity_my_app_background_Activity = "/my/AppBackgroundActivity";
    public static final String Activity_my_app_background_title = "title";
    public static final String Activity_my_app_background_url = "url";
    public static final String Activity_sport_data_download = "/sport/DataDownloadActivity";
    public static final String Actvity_Active = "/sport/activeactivity";
    public static final String Actvity_Sleep = "/sport/sleepactivity";
    public static final String Actvity_Sport_CaptureActivity = "/sport/CaptureActivity";
    public static final String Actvity_Sport_Main = "/sport/sport_mainactivty";
    public static final String Actvity_Sport_Scale_Wifi_Bind_OK = "/sport/sport_bind_ok_activty";
    public static final String Actvity_Sport_WifiInputAcitvity = "/sport/WifiInputAcitvity";
    public static final String DEVICE_Weight_Data_Not_Belong = "/device/device_weight_data_not_belong";
    public static final String Device_DeviceInfo_Service = "/device/info_service";
    public static final String Device_ECG_Service = "/device/ecg_service";
    public static final String Device_Fatigue_Service = "/device/fatigue_service";
    public static final String Device_Heart_Service = "/device/heart_service";
    public static final int Device_List = 1;
    public static final String Device_List_Key = "/app/Device_List_Key";
    public static final String Device_RRI_Service = "/device/rri_service";
    public static final String Device_Sleep_Service = "/device/sleep_service";
    public static final String Device_Sport_Service = "/device/sport_service";
    public static final String Device_Walk_Service = "/device/walk_service";
    public static final String Device_Weight_Service = "/device/weight_service";
    public static final String Fragment_Device_List = "/device/DeviceListFragment";
    public static final String SPORT_DATANETService = "/sport/data_net_service";
    public static final String Sport_module_test_MainActivity = "/sport_module/activity/test/main";
    public static final String UI_Service = "/ui/service";
    public static final String User_Service = "/user/info_service";

    public static void startSportSleepActivity() {
        ARouter.getInstance().build(Actvity_Sleep).navigation();
    }

    public static void startSportMainActivity() {
        ARouter.getInstance().build(Actvity_Sport_Main).navigation();
    }

    public static void startActiveActivity() {
        ARouter.getInstance().build(Actvity_Active).navigation();
    }

    public static Fragment findDeviceListFragment() {
        return (Fragment) ARouter.getInstance().build(Fragment_Device_List).navigation();
    }

    public static void startSportWeightDataNotBelongActivity() {
        ARouter.getInstance().build(DEVICE_Weight_Data_Not_Belong).navigation();
    }

    public static void startAPPMainActivitry() {
        ARouter.getInstance().build(Activity_app_MainActivity).navigation();
    }

    public static void startDeviceAddUserActivitry() {
        ARouter.getInstance().build(Activity_Device_AddUserActivity).navigation();
    }

    public static void startDataImportActivity() {
        ARouter.getInstance().build(Activity_Device_DataImportActivity).navigation();
    }

    public static void startDataDownloadActivity() {
        ARouter.getInstance().build(Activity_sport_data_download).navigation();
    }
}
