package com.iwown.device_module.common;

import android.content.IntentFilter;
import java.util.UUID;

public class BaseActionUtils {
    public static final String BLE_ARRIVED_DATA = "com.iwown.app.BLE_ARRIVED_DATA";
    public static final String BLE_BLUETOOTH_ADDRESS = "com.iwown.app.BLE_BLUETOOTH_ADDRESS";
    public static final String BLE_CHARACTER_UUID = "com.iwown.app.CHARACTER_UUID";
    public static final String BLE_COMMON_SEND = "com.iwown.app.BLE_COMMON_SEND";
    public static final String BLE_CONNECT_STATUE = "com.iwown.app.BLE_CONNECT_STAUE";
    public static final String BLE_DATA_TYPE = "com.iwown.app.BLE_DATA_TYPE";
    public static final String BLE_NO_CALLBACK = "com.iwown.app.BLE_NO_CALLBACK";
    public static final String BLE_PRE_CONNECT = "com.iwown.app.BLE_PRE_CONEECT";
    public static final String BLE_SCAN_RESULT_DEVICE = "com.iwown.app.BLE_SCAN_RESULT_DEVICE";
    public static final String BLE_SDK_TYPE = "com.iwown.app.BLE_SDK_TYPE";
    public static final String BLE_SERVICE_UUID = "com.iwown.app.BLE_SERVICE_UUID";
    public static final String NotificationMonitor = "com.iwown.device_module.device_message_push.NotificationMonitor";
    public static final String ON_BLUETOOTH_ERROR = "com.iwown.app.ON_BLUETOOTH_ERROR";
    public static final String ON_BLUETOOTH_INIT = "com.iwown.app.ON_BLUETOOTH_INIT";
    public static final String ON_CHARACTERISTIC_CHANGE = "com.iwown.app.ON_CHARACTERISTIC_CHANGE";
    public static final String ON_COMMON_SEND = "com.iwown.app.ON_COMMON_SEND";
    public static final String ON_CONNECT_STATUE = "com.iwown.app.ON_CONNECT_STATUE";
    public static final String ON_DATA_ARRIVED = "com.iwown.app.ON_DATA_ARRIVED";
    public static final String ON_DISCOVER_CHARACTER = "com.iwown.app.ON_DISCOVER_CHARACTER";
    public static final String ON_DISCOVER_SERVICE = "com.iwown.app.ON_DISCOVER_SERVICE";
    public static final String ON_SCAN_RESULT = "com.iwown.app.ON_SCAN_RESULT";
    public static final String PROTOBUF_CYC_CHECK = "com.iwown.device_module.PROTOBUF_CYC_CHECK_CODE";
    public static final String PROTOBUF_MTU_INFO = "com.iwown.device_module.PROTOBUF_MTU_INFO";
    public static final UUID UPDATE_SERVICE_MAIN_ = UUID.fromString("00001530-0000-1000-8000-00805f9b34fb");
    public static final UUID UPDATE_SERVICE_MAIN_DFU = UUID.fromString("0000fe59-0000-1000-8000-00805f9b34fb");

    public static class BleAction {
        public static final String Bluetooth_Device_Address = "com.iwown.Bluetooth_Device_Address";
        public static final String Bluetooth_Device_Address_Current_Device = "com.iwown.Bluetooth_Device_Address_Current_Device";
        public static final String Bluetooth_Device_Alias_Current_Device = "com.iwown.Bluetooth_Device_Alias_Current_Device";
        public static final String Bluetooth_Device_Name = "com.iwown.Bluetooth_Device_Name";
        public static final String Bluetooth_Device_Name_Current_Device = "com.iwown.Bluetooth_Device_Name_Current_Device";
        public static final String Bluetooth_Last_Device_Address = "com.iwown.Bluetooth_Last_Device_Address";
        public static final String Bluetooth_Protocol_Type = "com.iwown.Bluetooth_Protocol_Type";
        public static final String Bluetooth_Sync_Heart_Beat_Time = "com.iwown.Bluetooth_Sync_Heart_Beat_Time";
    }

    public static class DEV_PLATFORM {
        public static final int DIALOG = 3;
        public static final int MTK = 4;
        public static final int NORDIC = 2;
        public static final int TI = 1;
    }

    public static class FilePath {
        public static final String ECG_Data_Path = "/Zeroner/ecg/";
        public static final String Mtk_Ble_61_Data_Log_Dir = "/Zeroner/iwownfit_5_0/blelog/61_data/";
        public static final String Mtk_Ble_61_Sleep_Dir = "/Zeroner/iwownfit_5_0/sleep/";
        public static final String Mtk_Ble_62_Data_Log_Dir = "/Zeroner/iwownfit_5_0/blelog/62_data/";
        public static final String Mtk_Ble_68_Data_Dir = "/Zeroner/iwownfit_5_0/68_data/";
        public static final String ProtoBuf_Ble_80_Sleep_Dir = "/Zeroner/iwownfit_5_0/protobuf/sleep/";
        public static final String ProtoBuf_Ble_90_DOWNLOAD_Dir = "/Zeroner/iwownfit_5_0/protobuf/download_file/";
    }

    public static class FirmwareAction {
        public static final String ACTION_SELFIE_DATA = "com.kunekt.healthy.ACTION_SELFIE_DATA";
        public static final String Firmware_Battery = "com.iwown.Firmware_Battery";
        public static final String Firmware_Call_Message_Push = "com.iwown.call_message_push";
        public static final String Firmware_Can_Support_08 = "com.iwown.Firmware_Can_Support_08";
        public static final String Firmware_Command_To_Device = "com.iwown.Firmware_Command_To_Device";
        public static final String Firmware_Curr_0x29_Data = "com.iwown.Firmware_Curr_0x29_Data";
        public static final String Firmware_Device_Pref_Upload_Time = "com.iwown.Firmware_Device_Pref_Upload_Time";
        public static final String Firmware_Factory_Version_Info = "com.iwown.Firmware_Factory_Version_Info";
        public static final String Firmware_Factory_Version_Time = "com.iwown.Firmware_Factory_Version_Time";
        public static final String Firmware_Information = "com.iwown.Firmware_Information";
        public static final String Firmware_Last_Epo_Time = "com.iwown.Firmware_Last_Epo_Time";
        public static final String Firmware_Last_Sync_Setting_Time = "com.iwown.Firmware_Last_Sync_Setting_Time";
        public static final String Firmware_Last_agps_Time = "com.iwown.Firmware_Last_agps_Time";
        public static final String Firmware_Long_Sit = "com.iwown.Firmware_Long_Sit";
        public static final String Firmware_Message_Auto_Heart_Guidance = "com.iwown.Firmware_Message_Auto_Heart_Guidance";
        public static final String Firmware_Message_Auto_Heart_Rate = "com.iwown.Firmware_Message_Auto_Heart_Rate";
        public static final String Firmware_Message_Device_Setting_Other = "com.iwown.Firmware_Message_Device_Setting_Other";
        public static final String Firmware_Message_Push_Switch_Statue = "com.iwown.Firmware_Message_Push_Switch_Statue";
        public static final String Firmware_New_Protocol = "com.iwown.Firmware_New_Protocol";
        public static final String Firmware_Notification_Reconnect_Time = "com.iwown.Firmware_Notification_Reconnect_Time";
        public static final String Firmware_Scan_Refresh_Time = "com.iwown.Firmware_Scan_Refresh_Time";
        public static final String Firmware_Settings_Default = "com.iwown.Firmware_Settings_Default";
        public static final String Firmware_Sms_Message_Push = "com.iwown.Firmware_Sms_Message_Push";
        public static final String Firmware_Support_Sport = "com.iwown.Firmware_Support_Sport";
        public static final String Firmware_Support_Sports_Status = "com.iwown.Firmware_Support_Sports_Status";
        public static final String Firmware_Support_Sports_Status_UnChecked = "com.iwown.Firmware_Support_Sports_Status_UnChecked";
        public static final String Firmware_Temperature_Int = "com.iwown.Firmware_Temperature_Int";
        public static final String Firmware_Update_Device_Mac = "com.iwown.Firmware_Update_Device_Mac";
        public static final String Firmware_Update_Server_Resp = "com.iwown.Firmware_Update_Server_Resp";
        public static final String Firmware_Vibration_Mode = "com.iwown.Firmware_Vibration_Mode";
        public static final String Firmware_Weather_Int = "com.iwown.Firmware_Weather_Int";
        public static final String Firmware_Weather_Update = "com.iwown.Firmware_Weather_Update";
        public static final String Firmware_class_model_country = "com.iwown.class_model_country";
        public static final String Firmware_pm25 = "com.iwown.pm25";
        public static final String Firmware_post_version = "com.iwown.Firmware_post_version";
        public static final String UNBind_Device_Button = "com.iwown.unBind_Device_Button";
    }

    public static class HEART_REMIND_TYPE {
        public static final int AEROBIC = 2;
        public static final int ANAEROBIC = 3;
        public static final int FAT_BURNNING = 1;
        public static final int NO_SELECTED = -1;
        public static final int WARM_UP = 0;
    }

    public static class KeyCodeAction {
        public static final String Action_Phone_Mute = "com.iwown.Action_Phone_Mute";
        public static final String Action_Phone_Ring = "com.iwown.Action_Phone_Ring";
        public static final String Action_Phone_Statue_Out = "com.iwown.Action_Phone_Statue_Out";
        public static final String Action_Seleie_Data = "com.iwown.Action_Seleie_Data";
        public static final String Action_Voice_Start = "com.iwown.Action_voice_start";
    }

    public class LanguageType {
        public static final int ARABIC = 10;
        public static final int ARABIC_p1 = 10;
        public static final int BASIC = 255;
        public static final int CHINESE = 1;
        public static final int CHINESE_p1 = 1;
        public static final int ENGLISH = 0;
        public static final int ENGLISH_p1 = 0;
        public static final int FRENCH = 4;
        public static final int FRENCH_p1 = 4;
        public static final int GERMAN = 5;
        public static final int GERMAN_p1 = 5;
        public static final int ITALIAN = 2;
        public static final int ITALIAN_p1 = 2;
        public static final int JAPAN = 3;
        public static final int JAPAN_p1 = 3;
        public static final int KOREAN = 9;
        public static final int KOREAN_p1 = 9;
        public static final int PORTUGUESE = 6;
        public static final int PORTUGUESE_p1 = 6;
        public static final int Poland = 15;
        public static final int Poland_p1 = 12;
        public static final int RUSSIAN = 8;
        public static final int RUSSIAN_p1 = 8;
        public static final int Romania = 16;
        public static final int Romania_p1 = 13;
        public static final int SPANISH = 7;
        public static final int SPANISH_p1 = 7;
        public static final int Sweden = 14;
        public static final int Sweden_p1 = 14;
        public static final int Thai = 12;
        public static final int Turkey = 13;
        public static final int VIETNAMESE = 11;
        public static final int VIETNAMESE_p1 = 11;

        public LanguageType() {
        }
    }

    public class LogKeyStr {
        public static final String BLE_LOG_KEY = "operate";
        public static final String NOTIFY_LOG_KEY = "notify";
        public static final String WRITE_LOG_KEY = "write";

        public LogKeyStr() {
        }
    }

    public class PushAppPackName {
        public static final String FACEBOOK = "com.facebook.orca";
        public static final String GMAIL = "com.google.android.gm";
        public static final String KAKAOTALK = "com.kakao.talk";
        public static final String LINE = "jp.naver.line.android";
        public static final String Push_Default = "com.iwown.MsgPushActivity.Push_Default";
        public static final String QQ = "com.tencent.mobileqq";
        public static final String SINA = "com.sina.weibo";
        public static final String SKYPE1 = "com.skype.rover";
        public static final String SKYPE2 = "com.skype.raider";
        public static final String TWITTER = "com.twitter.android";
        public static final String WECHAT = "com.tencent.mm";
        public static final String WHATSAPP = "com.whatsapp";

        public PushAppPackName() {
        }
    }

    public static class SDK {
        public static final int Sdk_Type_Iv = 1;
        public static final int Sdk_Type_Mtk = 2;
        public static final int Sdk_Type_ProtoBuf = 4;
        public static final int Sdk_Type_Zg = 3;
    }

    public static class SETTING_INDEXS {
        public static final int ADD_SPORT = 17;
        public static final int AF_24 = 41;
        public static final int ALARM = 15;
        public static final int AUTO_HEART_RATE = 11;
        public static final int AUTO_HEART_SWITCH_TIME = 26;
        public static final int AUTO_SLEEP = 4;
        public static final int All_Of_Them = 9999;
        public static final int BACK_COLOR = 6;
        public static final int BACK_LIGHT_TIME = 5;
        public static final int BLOOD_PRESSURE = 38;
        public static final int CAMERA = 18;
        public static final int Call_PUSH_SWITCH = 25;
        public static final int DATE_FORMAT = 9;
        public static final int DISCONNECT_TIP = 8;
        public static final int Device_Guide = 36;
        public static final int Double_Touch_Switch = 32;
        public static final int FW_UPGRADE = 20;
        public static final int GESTURE = 1;
        public static final int HEART_GUIDE = 13;
        public static final int HOUR_24 = 3;
        public static final int HRV_P1 = 28;
        public static final int LANGUAGE = 7;
        public static final int LED = 0;
        public static final int Message_Push_Switch = 30;
        public static final int NO_DISTURB = 39;
        public static final int SCHEDULE = 16;
        public static final int SEDENTARY = 19;
        public static final int SHOULD_READ_0X18 = 22;
        public static final int SMART_TRACK = 24;
        public static final int SUPPORT_HEART = 21;
        public static final int SUPPORT_SOPORT_COUNT = 23;
        public static final int Standard_Heart = 34;
        public static final int UNIT_MEASUREMENT = 2;
        public static final int VIBRATION = 12;
        public static final int WEARING_MANNER = 27;
        public static final int WEATHER = 14;
        public static final int WRIST_TURNING = 10;
        public static final int Wearable_Recognize = 33;
        public static final int Welcome_Page = 35;
    }

    public static class SharedPreferencesAction {
        public static final String APP_SDK_UPDATE_Content = "APP_SDK_UPDATE_Content";
        public static final String APP_SDK_UPDATE_Time = "APP_SDK_UPDATE_Time";
        public static final String APP_SDK_UPDATE_Types = "APP_SDK_UPDATE_Types";
        public static final String EARPHONE = "EARPHONE";
        public static final String PROTOBUF = "PROTOBUF";
        public static final String User_Sdk_type = "User_Sdk_type";
    }

    public static class UserAction {
        public static final String Bluetooth_Check_Firmware_Update_Time = "com.iwown.Bluetooth_Check_Firmware_Update_Time";
        public static final String HEALTHY_LANGUAGE = "com.iwown_healthy_language";
        public static final String I6S7_BRACELET_KEY = "com.iwown.I6S7_BRACELET_KEY";
        public static final String User_Firmware_Tag = "com.iwown.User_Firmware_Tag";
        public static final String User_Log_History_Time = "com.iwown.User_Log_History_Time";
        public static final String User_Page_Select = "com.iwown.User_Page_Select";
        public static final String User_Phone_App_Background = "com.iwown.User_Phone_App_Background";
        public static final String User_Request_Remote_Setting_Time = "com.iwown.User_Request_Remote_Setting_Time";
        public static final String User_Step_Calorie = "com.iwown.User_Step_Calorie";
        public static final String User_Step_Target = "com.iwown.User_Step_Target";
        public static final String User_Target_Steps = "com.iwown.User_Target_Steps";
        public static final String User_Uid = "com.iwown.User_Uid";
        public static final String User_Weight = "com.iwown.User_Weight";
    }

    public static class WifiScaleAction {
        public static final String Wifi_Scale_Setting_Action = "com.kunekt.healthy.Wifi_Scale_Setting_Action";
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ON_SCAN_RESULT);
        intentFilter.addAction(ON_DATA_ARRIVED);
        intentFilter.addAction(ON_BLUETOOTH_INIT);
        intentFilter.addAction(ON_CONNECT_STATUE);
        intentFilter.addAction(ON_DISCOVER_SERVICE);
        intentFilter.addAction(ON_DISCOVER_CHARACTER);
        intentFilter.addAction(ON_COMMON_SEND);
        intentFilter.addAction(ON_CHARACTERISTIC_CHANGE);
        intentFilter.addAction(ON_BLUETOOTH_ERROR);
        intentFilter.addAction(BLE_DATA_TYPE);
        intentFilter.addAction(BLE_ARRIVED_DATA);
        intentFilter.addAction(BLE_SCAN_RESULT_DEVICE);
        intentFilter.addAction(BLE_CONNECT_STATUE);
        intentFilter.addAction(BLE_SERVICE_UUID);
        intentFilter.addAction(BLE_CHARACTER_UUID);
        intentFilter.addAction(BLE_COMMON_SEND);
        intentFilter.addAction(BLE_SDK_TYPE);
        intentFilter.addAction(BLE_BLUETOOTH_ADDRESS);
        intentFilter.addAction(BLE_PRE_CONNECT);
        intentFilter.addAction(BLE_NO_CALLBACK);
        return intentFilter;
    }
}
