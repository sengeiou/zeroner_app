package com.iwown.my_module.utility;

import android.os.Environment;
import com.iwown.data_link.BaseNetUrl;
import java.util.UUID;

public class Constants {
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACTION_ALERM_DATA = "com.kunekt.healthy.ACTION_ALERM_DATA";
    public static final String ACTION_BAND_CHANGED_PROFILE = "com.chamobile.iweight.ACTION_BAND_CHANGED_PROFILE";
    public static final String ACTION_BAND_CONNECTED = "com.chamobile.iweight.ACTION_BAND_CONNECTED";
    public static final String ACTION_BAND_DATE_READ = "com.chamobile.iweight.ACTION_BAND_DATE_READ";
    public static final String ACTION_BAND_DATE_UPDATED = "com.chamobile.iweight.ACTION_BAND_DATE_UPDATED";
    public static final String ACTION_BAND_DISCONNECTED = "com.chamobile.iweight.ACTION_BAND_DISCONNECTED";
    public static final String ACTION_BAND_LIVE = "com.chamobile.iweight.ACTION_BAND_LIVE";
    public static final String ACTION_BAND_PAIR = "com.chamobile.iweight.ACTION_BAND_PAIR";
    public static final String ACTION_BAND_READING = "com.chamobile.iweight.ACTION_BAND_READING";
    public static final String ACTION_BAND_SERVICES_DISCOVERED = "com.chamobile.iweight.ACTION_BAND_SERVICES_DISCOVERED";
    public static final String ACTION_BAND_UNBIND = "com.chamobile.iweight.ACTION_BAND_UNBIND";
    public static final String ACTION_BLUETOOTH_RESTART = "com.kunekt.healthy.ACTION_BLUETOOTH_RESTART";
    public static final String ACTION_CLOCK_CLOCKID = "com.kunket.ACTION_CLOCK_CLOCKID";
    public static final String ACTION_CONNECT_TIMEOUT = "com.kunekt.healthy.ACTION_CONNECT_TIMEOUT";
    public static final String ACTION_DIALY_SPORT_DATA = "com.kunekt.healthy.ACTION_DIALY_SPORT_DATA";
    public static final String ACTION_GUIDE_LAST_LAYOUT = "com.kunekt.healthy.ACTION_GUIDE_LAST_LAYOUT";
    public static final String ACTION_MAINACTIVITY_FINISH = "com.kunket.ACTION_MAINACTIVITY_FINISH";
    public static final String ACTION_MIN_SPORT_DATA = "com.kunekt.healthy.ACTION_MIN_SPORT_DATA";
    public static final String ACTION_MORNING_CLOCK_TIME = "com.kunket.ACTION_MORNING_CLOCK_TIME";
    public static final String ACTION_NIGHT_CLOCK_TIME = "com.kunket.ACTION_NIGHT_CLOCK_TIME";
    public static final String ACTION_PERSONCENTER_USER_NICKNAME = "com.kunket.ACTION_PERSONCENTER_USER_NICKNAME";
    public static final String ACTION_PERSONCENTER_USER_PHOTO = "com.kunket.ACTION_PERSONCENTER_USER_PHOTO";
    public static final String ACTION_PERSONCENTER_USER_PHOTO_DOWNLOAD = "com.kunket.ACTION_PERSONCENTER_USER_PHOTO_DOWNLOAD";
    public static final String ACTION_PERSON_GRADE = "com.kunket.ACTION_PERSON_GRADE";
    public static final String ACTION_PHONE_ANSWER_RING = "com.kunekt.healthy.ACTION_PHONE_ANSWER_RING";
    public static final String ACTION_PHONE_NORINGING = "com.kunekt.healthy.ACTION_PHONE_NORINGING";
    public static final String ACTION_PHONE_STATUE_OUT = "com.kunekt.healthy.ACTION_PHONE_STATUE_OUT";
    public static final String ACTION_SEDENTARY_DATA = "com.kunekt.healthy.ACTION_SEDENTARY_DATA";
    public static final String ACTION_SELFIE_DATA = "com.kunekt.healthy.ACTION_SELFIE_DATA";
    public static final String ACTION_SERVICE_INSERT = "com.kunekt.healthy.ACTION_SERVICE_INSERT";
    public static final String ACTION_SERVICE_PHONE_STEPZERONER = "com.kunekt.healthy.ACTION_SERVICE_PHONE_STEPZERONER";
    public static final String ACTION_SETTING_TARGET_STEP = "com.kunket.ACTION_SETTING_TARGET_STEP";
    public static final String ACTION_SETTING_TARGET_WEIGHT = "com.kunket.ACTION_SETTING_TARGET_WEIGHT";
    public static final String ACTION_SHOW_MENU = "com.kunket.ACTION_SHOW_MENU";
    public static final String ACTION_SOFTWARE_UPDATE = "com.kunekt.healthy.ACTION_SOFTWARE_UPDATE";
    public static final String ACTION_TARGET_STEPS = "com.kunket.ACTION_TARGET_STEPS";
    public static final String ACTION_TIME_SERVICE = "com.kunket.zero_health.version_v3.ACTION_TIME_SERVICE";
    public static final String ACTION_UNREGISTER_SPORTFRAGMENT = "com.kunket.ACTION_UNREGISTER_SPORTFRAGMENT";
    public static final String ACTION_UNREGISTER_WEIGHTFRAGMENT = "com.kunket.ACTION_UNREGISTER_WEIGHTFRAGMENT";
    public static final String ACTION_VOICE_START = "com.kunekt.healthy.ACTION_VOICE_START";
    public static final String ACTION_WRISTBAND_CONNECT = "com.kunket.ACTION_WRISTBAND_CONNECT";
    public static final String ACTION_WRISTBAND_DISCONNECT = "com.kunket.ACTION_WRISTBAND_DISCONNECT";
    public static final String ACTION_WRISTBAND_REPEAT_CONNECT = "com.kunekt.healthy.ACTION_WRISTBAND_REPEAT_CONNECT";
    public static final String ACTION_WRITEDATA2SD = "com.kunekt.healthy.ACTION_WRITEDATA2SD";
    public static final String ADDRESS_FRIENDS_SEARCH = "getProfile#phoneLike";
    public static final String AD_TIME = "com.kunekt.healthy.ad_time";
    public static final String AD_URL = (NEW_API + "wawaservice/advertise/startpage");
    public static final String AGE = "com.kunekt.healthy.age";
    public static final String APPNAME = "zeroner";
    public static final String APPSYSTEM = "android";
    public static final String APP_VERSION = "com.healthy.iwownfit_pro_pro.APP_VERSION";
    public static final String APP_VERSION_UPDATE = "com.kunekt.healthy.APP_VERSION_UPDATE";
    public static final String AUTO_HR = "com.healthy.iwownfit_pro_pro.AUTO_HR";
    public static final String AUTO_SLEEP_BUTTON = "com.kunekt.healthy.AUTO_SLEEP_BUTTON";
    public static final String BACK_LIGHTEND_TIME = "com.kunekt.healthy.BACK_LIGHTEND_TIME";
    public static final String BACK_LIGHTSTAR_TTIME = "com.kunekt.healthy.BACK_LIGHTSTAR_TTIME";
    public static final String BACK_LIGHT_TTIME = "com.kunekt.healthy.BACK_LIGHT_TTIME";
    public static final UUID BAND_CHARACTERISTIC_ALARM = UUID.fromString("f000ff01-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_BATTERY = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_DAILY = UUID.fromString("f000ff07-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_DATE = UUID.fromString("f000ff05-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_INFO = UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_LED = UUID.fromString("f000ff04-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_NEW_INDICATE = UUID.fromString("0000ff23-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_NEW_NOTIFY = UUID.fromString("0000ff22-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_NEW_WRITE = UUID.fromString("0000ff21-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_PAIR = UUID.fromString("f000ff06-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_PHONE_ALERT = UUID.fromString("f000ff11-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_POWER_SAVING = UUID.fromString("f000ff09-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_SCALE_DATA = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_SCALE_SERVICE_MAIN = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_SCALE_SERVICE_MAIN_2 = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_SCALE_SETTING = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_SCALE_SETTING_2 = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_CHARACTERISTIC_SEDENTARY = UUID.fromString("f000ff08-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_SPORT = UUID.fromString("f000ff03-0451-4000-b000-000000000000");
    public static final UUID BAND_CHARACTERISTIC_USER = UUID.fromString("f000ff02-0451-4000-b000-000000000000");
    public static final UUID BAND_DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_SERVICE_BATTERY = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_SERVICE_INFO = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_SERVICE_MAIN = UUID.fromString("f000ff00-0451-4000-b000-000000000000");
    public static final UUID BAND_SERVICE_MAIN_NEW = UUID.fromString("0000ff20-0000-1000-8000-00805f9b34fb");
    public static final UUID BAND_SERVICE_PHONE_ALERT = UUID.fromString("f000ff10-0451-4000-b000-000000000000");
    public static final String BBS_URL = "http://bbs.iwownfit.com";
    public static final String BINDING_EMAIL = "binding_email";
    public static final String BINDING_PHONE = "binding_phone";
    public static final String BIND_DEVICE_OR_NOT = "com.kunekt.healthy.BIND_DEVICE_OR_NOT";
    public static String BLE_61_UP = BaseNetUrl.Log_P1_Upload_API_AMAZON_DEV;
    public static final String BODY_TYPE = "body_type";
    public static final int BOOL_FALSE = 0;
    public static final int BOOL_TRUE = 1;
    public static final UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final String CLOCK_MORNING_HOUR = "clock_morning_hour";
    public static final String CLOCK_MORNING_MIN = "clock_morning_min";
    public static final String CLOCK_MORNING_WAKEUP_TIME = "com.kunekt.healthy.CLOCK_MORNING_TIME";
    public static final String CLOCK_NIGHTSLEEP_TIME = "com.kunekt.healthy.CLOCK_NIGHTSLEEP_TIME";
    public static final String CLOCK_NIGHT_HOUR = "clock_night_hour";
    public static final String CLOCK_NIGHT_MIN = "clock_night_min";
    public static final String CLOCK_OPEN_CLOSE = "clock_open_close";
    public static final String CLOCK_REPEAT_STR_WEEK = "clock_repeat_str_week";
    public static final String CLOCK_REPEAT_WEEK = "clock_repeat_week";
    public static final String CLOCK_SHAKE_MODE = "com.kunekt.healthy.CLOCK_SHAKE_MODE";
    public static final String CLOCK_SHAKE_NUM = "com.kunekt.healthy.CLOCK_SHAKE_NUM";
    public static final int CMD_GRP_CONFIG = 1;
    public static final int CMD_GRP_DATALOG = 2;
    public static final int CMD_GRP_DEVICE = 0;
    public static final int CMD_GRP_MSG = 3;
    public static final int CMD_ID_CONFIG_GET_AC = 5;
    public static final int CMD_ID_CONFIG_GET_BLE = 3;
    public static final int CMD_ID_CONFIG_GET_HW_OPTION = 9;
    public static final int CMD_ID_CONFIG_GET_NMA = 7;
    public static final int CMD_ID_CONFIG_GET_TIME = 1;
    public static final int CMD_ID_CONFIG_SET_AC = 4;
    public static final int CMD_ID_CONFIG_SET_BLE = 2;
    public static final int CMD_ID_CONFIG_SET_HW_OPTION = 8;
    public static final int CMD_ID_CONFIG_SET_NMA = 6;
    public static final int CMD_ID_CONFIG_SET_TIME = 0;
    public static final int CMD_ID_CONFIG_SPORT_TYPE = 10;
    public static final int CMD_ID_CONFIG_SPORT_TYPE_GOLE = 11;
    public static final int CMD_ID_CONFIG_SPORT_TYPE_READ_GOLE = 12;
    public static final int CMD_ID_DATALOG_CLEAR_ALL = 2;
    public static final int CMD_ID_DATALOG_GET_BODY_PARAM = 1;
    public static final int CMD_ID_DATALOG_GET_CUR_DAY_DATA = 7;
    public static final int CMD_ID_DATALOG_SET_BODY_PARAM = 0;
    public static final int CMD_ID_DATALOG_START_GET_DAY_DATA = 3;
    public static final int CMD_ID_DATALOG_START_GET_MINUTE_DATA = 5;
    public static final int CMD_ID_DATALOG_STOP_GET_DAY_DATA = 4;
    public static final int CMD_ID_DATALOG_STOP_GET_MINUTE_DATA = 6;
    public static final int CMD_ID_DEVICE_GET_BATTERY = 1;
    public static final int CMD_ID_DEVICE_GET_INFORMATION = 0;
    public static final int CMD_ID_DEVICE_RESE = 2;
    public static final int CMD_ID_DEVICE_UPDATE = 3;
    public static final int CMD_ID_MSG_DOWNLOAD = 1;
    public static final int CMD_ID_MSG_MULTI_DOWNLOAD_CONTINUE = 3;
    public static final int CMD_ID_MSG_MULTI_DOWNLOAD_END = 4;
    public static final int CMD_ID_MSG_MULTI_DOWNLOAD_START = 2;
    public static final int CMD_ID_MSG_UPLOAD = 0;
    public static final int CMD_ID_PHONE_ALERT = 1;
    public static final int CMD_ID_PHONE_PRESSKEY = 0;
    public static final int CMD_PHONE_MSG = 4;
    public static String COMMON_URL = "http://54.67.86.82:80/iwown/iwown.iwown";
    public static final String COMMON_URL_BASE = "http://114.215.151.68:9999/web/test.iwown";
    public static final String COMMON_URL_FILE = "http://114.215.151.68:8080/iwown/iwown.iwown";
    public static final String CONDITION = "com.kunekt.healthy.condition";
    public static final String CONNECT_OK_FLAG = "connect_ok_flag";
    public static final String CONNECT_WHEN_TO_FOREGROUND = "com.healthy.iwownfit_pro_pro.CONNECT_WHEN_TO_FOREGROUND";
    public static final String COUNTRY = "com.healthy.healthy.country";
    public static final String DAILY_SPORT = "daily_sport";
    public static final String DATE_FORMAT_TYPE = "com.kunekt.healthy.DATE_FORMAT_TYPE";
    public static final String DEEP_SLEEP_TIME = "deep_sleep_time";
    public static final String DERVICE_ADDRESS = "dervice_address";
    public static final String DERVICE_NAME = "dervice_name";
    public static final boolean DEVELOPER_MODE = false;
    public static final String DEVICES_POWER = "com.kunekt.healthy.DEVICES_POWER";
    public static final String DEVICE_MODEL = "com.healthy.healthy.device_model";
    public static final String DIALY_CALOIES = "dialy_caloies";
    public static final String DIALY_DISTANCE = "dialy_distance";
    public static final String DIALY_STEPS = "dialy_steps";
    public static final String DIALY_TIME = "com.kunekt.healthy.DIALY_TIME";
    public static final String FACEBOOK_URL = "https://www.facebook.com/iwownfit";
    public static final String FAHRENHEIT = "com.healthy.healthy.fahrenheit";
    public static final String FIMWARE_UPGRADE_TIME_ERROR = "com.healthy.iwownfit_pro_pro.FIMWARE_UPGRADE_SERVICE_ERROR";
    public static final String FIND_PASSWORD_BY_PHONE = "FIND_PASSWORD_BY_PHONE";
    public static final String FIRSTWEIGHT = "firstweight";
    public static final String FIRST_LOGIN_FLAG = "firstLogin";
    public static final String FM_INFOMATION = "com.kunekt.healthy.FM_INFOMATION";
    public static final String FM_VERSION_INFOMATION = "com.kunekt.healthy.FM_VERSION_INFOMATION";
    public static final String FONT_LIB_IWOWN = "com.kunekt.FONT_LIB_IWOWN";
    public static final String FWUPDATEREQUEST = "fwupdaterequest";
    public static final String FWUPDATEREQUEST_BOOTLOADER = "fwupdaterequest_bootloader";
    public static final String GET_IPS_URL = "http://54.183.62.103:80/ip.json";
    public static String GET_MODE_LIST1 = (NEW_API + "/deviceservice/cloudevice/class1modelist");
    public static String GET_MODE_LIST2 = (NEW_API + "/deviceservice/cloudevice/class2modelist");
    public static final String GROUP_USERNAME = "item_groups";
    public static final String GS_BACKLIGHT_ET = "com.healthy.iwownfit_pro_pro.GS_BACKLIGHT_ET";
    public static final String GS_BACKLIGHT_ST = "com.healthy.iwownfit_pro_pro.GS_BACKLIGHT_ST";
    public static final String GUIDE = "com.healthy.healthy.guide";
    public static final String GUIDE_URL = "https://api4.iwown.com/guide/bracelet/bracelet.html";
    public static final String HAS_GUIDE_DEV_SET_ACT = "com.healthy.iwownfit_pro_pro.HAS_GUIDE_DEV_SET_ACT";
    public static final String HAS_GUIDE_DEV_SET_FRG = "com.healthy.iwownfit_pro_pro.HAS_GUIDE_DEV_SET_FRG";
    public static final String HAS_UPDATE_TB_SLEEP_DOWNLOAD_DATA = "com.kunekt.healthy.HAS_UPDATE_TB_SLEEP_DOWNLOAD_DATA";
    public static final String HAVE_OPEN_HEART_GUIDE = "com.healthy.iwownfit_pro_pro.HAVE_OPEN_HEART_GUIDE";
    public static final String HAVE_UP_GOAL = "com.healthy.iwownfit_pro_pro.HAVE_UP_GOAL";
    public static final String HAVE_UP_LOG_TODAY = "com.healthy.iwownfit_pro_pro.HAVE_UP_LOG_TODAY";
    public static final String HAVE_UP_NOTIFY_TODAY = "com.healthy.iwownfit_pro_pro.HAVE_UP_NOTIFY_TODAY";
    public static final String HAVE_UP_WRITE_TODAY = "com.healthy.iwownfit_pro_pro.HAVE_UP_WRITE_TODAY";
    public static final String HEART_GUIDE_END = "com.healthy.iwownfit_pro_pro.HEART_GUIDE_END";
    public static final String HEART_GUIDE_SHAKE_MODE = "com.kunekt.healthy.HEART_GUIDE_SHAKE_MODE";
    public static final String HEART_GUIDE_SHAKE_NUM = "com.kunekt.healthy.HEART_GUIDE_SHAKE_NUM";
    public static final String HEART_GUIDE_START = "com.healthy.iwownfit_pro_pro.HEART_GUIDE_START";
    public static final String HEART_GUIDE_TYPE = "com.healthy.iwownfit_pro_pro.HEART_GUIDE_TYPE";
    public static final String HEIGHT = "com.kunekt.healthy.height";
    public static final String HX_ADD_FRIENDS_SUCCESS = "userFriend#addFriendSuccess";
    public static final String HX_DEL_FRIENDS = "userFriend#delFriend";
    public static final String HX_FRIENDS_APPLY = "userFriend#addFriend";
    public static final String HX_FRIENDS_LIST = "userFriend#friendList";
    public static final String HX_FRIENDS_SEARCH = "getProfile#profileList";
    public static final String HX_PASSWORD = "com.kunekt.healthy.HX_PASSWORD";
    public static final String INTENT_ACTION_UPDATE_DATA = "intent_action_update_data";
    public static final String INTENT_ACTION_UPDATE_DATA_EXTRA_ALTITUDE = "intent_action_update_data_extra_altitude";
    public static final String INTENT_ACTION_UPDATE_DATA_EXTRA_LATITUDE = "intent_action_update_data_extra_latitude";
    public static final String INTENT_ACTION_UPDATE_DATA_EXTRA_LONGITUDE = "intent_action_update_data_extra_longitude";
    public static final String ISBOY = "com.kunekt.healthy.isboy";
    public static final String IS_CHOOSE_LANGUAGE = "com.healthy.healthy.CHOOSE_LANGUAGE";
    public static final String IS_DISCONNECTTIP = "com.kunekt.healthy.IS_DISCONNECTTIP";
    public static final String IS_ENGLISH = "com.kunekt.healthy.IS_ENGLISH";
    public static final String IS_ENGLISHUNIT = "com.kunekt.healthy.IS_ENGLISHUNIT";
    public static final String IS_GESTURE_OPEN = "com.kunekt.healthy.IS_GESTURE_OPEN";
    public static final String IS_HOURSUNIT = "com.kunekt.healthy.IS_HOURSUNIT";
    public static final String IS_INVERSECOLOR = "com.kunekt.healthy.IS_INVERSECOLOR";
    public static final String IS_LIGTH_OPEN = "com.kunekt.healthy.IS_LIGTH_OPEN";
    public static final String IS_QQ_OPEN = "com.kunekt.healthy.IS_QQ_OPEN";
    public static final String IS_REQUEST = "com.kunekt.healthy.IS_REQUEST";
    public static final String IS_SHAKE_BUG = "com.healthy.healthy.IS_SHAKE_BUG";
    public static final String IS_SUPPORT_08 = "is_support_08";
    public static final String IS_UPGRADING = "is_upgrading";
    public static final String IS_WEIXING_OPEN = "com.kunekt.healthy.IS_WEIXING_OPEN";
    public static final String IWOWN_BBS = "http://bbs.iwown.com";
    public static final String JAVASCRIPT_TO_WEBVIEW = "com.kunekt.healthy.JAVASCRIPT_TO_WEBVIEW";
    public static final String LANGUAGE = "com.healthy.healthy.language";
    public static final int LANGUAGE_SUPPORT_COUNT = 19;
    public static final String LANGUAGE_TYPE = "com.kunekt.healthy.LANGUAGE_TYPE";
    public static final String LAST_SYNCHRONIZED_TIME = "com.kunekt.healthy.LAST_SYNCHRONIZED_TIME";
    public static final String LATITUDE = "com.kunekt.healthy.latitude";
    public static final String LOCALITY = "com.kunekt.healthy.locality";
    public static final String LOGIN_PASSWORD = "login_password";
    public static final String LOGIN_USERNAME = "login_username";
    public static String LOG_UPLOAD_API = BaseNetUrl.Log_P1_Upload_API_AMAZON_PROD;
    public static final String LONGITUDE = "com.kunekt.healthy.longitude";
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_FACEBOOK_ACTION = "com.kunekt.MESSAGE_FACEBOOK_ACTION";
    public static final String MESSAGE_FACEBOOK_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_FACEBOOK_INFOMESSAGE_ACTION";
    public static final String MESSAGE_FACEBOOK_OPEN_ACTION = "com.kunekt.MESSAGE_FACEBOOK_OPEN_ACTION";
    public static final String MESSAGE_GMAIL_ACTION = "com.kunekt.MESSAGE_GMAIL_ACTION";
    public static final String MESSAGE_GMAIL_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_GMAIL_INFOMESSAGE_ACTION";
    public static final String MESSAGE_GMAIL_OPEN_ACTION = "com.kunekt.MESSAGE_GMAIL_OPEN_ACTION";
    public static final String MESSAGE_KAKAOTALK_ACTION = "com.kunekt.MESSAGE_KAKAOTALK_ACTION";
    public static final String MESSAGE_KAKAOTALK_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_KAKAOTALK_INFOMESSAGE_ACTION";
    public static final String MESSAGE_KAKAOTALK_OPEN_ACTION = "com.kunekt.MESSAGE_KAKAOTALK_OPEN_ACTION";
    public static final String MESSAGE_LINE_ACTION = "com.kunekt.MESSAGE_LINE_ACTION";
    public static final String MESSAGE_LINE_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_LINE_INFOMESSAGE_ACTION";
    public static final String MESSAGE_LINE_OPEN_ACTION = "com.kunekt.MESSAGE_LINE_OPEN_ACTION";
    public static final String MESSAGE_PUSH_AGAIN_ERROR = "com.kunekt.healthy.MESSAGE_PUSH_AGAIN_ERROR";
    public static final String MESSAGE_PUSH_AGAIN_ERROR_QQ = "com.kunekt.healthy.MESSAGE_PUSH_AGAIN_ERROR_QQ";
    public static final String MESSAGE_PUSH_AGAIN_ERROR_SMS = "com.kunekt.healthy.MESSAGE_PUSH_AGAIN_ERROR_SMS";
    public static final String MESSAGE_PUSH_AGAIN_ERROR_WEICHAT = "com.kunekt.healthy.MESSAGE_PUSH_AGAIN_ERROR_WEICHAT";
    public static final String MESSAGE_SKYPE_ACTION = "com.kunekt.MESSAGE_SKYPE_ACTION";
    public static final String MESSAGE_SKYPE_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_SKYPE_INFOMESSAGE_ACTION";
    public static final String MESSAGE_SKYPE_OPEN_ACTION = "com.kunekt.MESSAGE_SKYPE_OPEN_ACTION";
    public static final String MESSAGE_TWITTER_ACTION = "com.kunekt.MESSAGE_TWITTER_ACTION";
    public static final String MESSAGE_TWITTER_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_TWITTER_INFOMESSAGE_ACTION";
    public static final String MESSAGE_TWITTER_OPEN_ACTION = "com.kunekt.MESSAGE_TWITTER_OPEN_ACTION";
    public static final String MESSAGE_WHATSAPP_ACTION = "com.kunekt.MESSAGE_WHATSAPP_ACTION";
    public static final String MESSAGE_WHATSAPP_INFOMESSAGE_ACTION = "com.kunekt.MESSAGE_WHATSAPP_INFOMESSAGE_ACTION";
    public static final String MESSAGE_WHATSAPP_OPEN_ACTION = "com.kunekt.MESSAGE_WHATSAPP_OPEN_ACTION";
    public static final String MODEL_TYPE = "com.kunekt.healthy.model_type";
    public static final String NEWUID = "com.kunekt.healthy.NEWUID";
    public static final int NEW_61_SLEEP_DOWN = 38;
    public static String NEW_API = BaseNetUrl.Base_API_AMAZON_PROD;
    public static final int NEW_CHECK_APP = 34;
    public static final int NEW_DEVICE_SETTINGS_DOWN = 37;
    public static final int NEW_DOWNLOAD_FILE = 40;
    public static final int NEW_FIRMWARE_BASE = 30;
    public static final int NEW_FIRMWARE_USER_DOWN = 32;
    public static final int NEW_FIRMWARE_USER_UP = 31;
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final int NEW_GET_61_FILE_URL = 41;
    public static final int NEW_GET_62_FILE_URL = 39;
    public static final int NEW_GOAL_DOWN = 36;
    public static final int NEW_GOAL_UP = 35;
    public static final int NEW_HEART_DOWN = 14;
    public static final int NEW_HEART_HOUR_DOWN = 16;
    public static final int NEW_HEART_HOUR_UP = 15;
    public static final int NEW_HEART_UP = 13;
    public static final String NEW_MAP_KEY = "body";
    public static final int NEW_SD_FILE_UP = 33;
    public static final int NEW_SLEEP_DOWN = 12;
    public static final int NEW_SLEEP_UP = 11;
    public static final int NEW_SPORT_DOWN = 20;
    public static final int NEW_SPORT_UP = 19;
    public static final int NEW_USER_CHECK_EMAIL = 2;
    public static final int NEW_USER_DOWN_PHOTO = 9;
    public static final int NEW_USER_DOWN_USER = 6;
    public static final int NEW_USER_LOGIN = 1;
    public static final int NEW_USER_PASS_CHANGE = 7;
    public static final int NEW_USER_REGISTER = 0;
    public static final int NEW_USER_SEND_EMAIL = 3;
    public static final int NEW_USER_SEND_PHONE = 4;
    public static final int NEW_USER_UP_PHOTO = 8;
    public static final int NEW_USER_UP_USER = 5;
    public static final int NEW_WALKING_DOWN = 22;
    public static final int NEW_WALKING_UP = 21;
    public static final int NEW_WEIGHT_DOWN = 18;
    public static final int NEW_WEIGHT_UP = 17;
    public static final String OLD_MODEL = "old_model";
    public static final String OUT_BLE_SEARCHANDCNT_ACT = "com.healthy.iwownfit_pro_pro.OUT_BLE_SEARCHANDCNT_ACT";
    public static final String PERSON_GRADE_CALORIEFORUPDATE = "com.kunekt.healthy.PERSON_GRADE_CALORIEFORUPDATE";
    public static final String PERSON_GRADE_GRADE = "com.kunekt.healthy.PERSON_GRADE_GRADE";
    public static final String PERSON_GRADE_LEVEL = "com.kunekt.healthy.PERSON_GRADE_LEVEL";
    public static final String PERSON_GRADE_UPDATERATIO = "com.kunekt.healthy.PERSON_GRADE_UPDATERATIO";
    public static final String PERSON_SEDENTARY_TIME = "com.kunekt.healthy.PERSON_SEDENTARY_TIME";
    public static final String PERSON_USER_GENDER = "com.kunekt.healthy.PERSON_USER_GENDER";
    public static final String PERSON_USER_PHOTO = "com.kunekt.healthy.PERSON_USER_PHOTO";
    public static final String PERSON_USER_PHOTO_BITMAP = "com.kunekt.healthy.PERSON_USER_PHOTO_BITMAP";
    public static final String PHOEN_GSENSOR = "com.kunekt.healthy.PHOEN_GSENSOR";
    public static final String PHONE_MODEL_LOGO = "PHONE_MODEL_LOGO";
    public static final String PHONE_MODEL_URL = "com.kunekt.healthy.PHONE_MODEL_URL";
    public static final String PHONE_SHAKE_MODE = "com.kunekt.healthy.PHONE_SHAKE_MODE";
    public static final String PHONE_SHAKE_NUM = "com.kunekt.healthy.PHONE_SHAKE_NUM";
    public static final String PHONE_STEP_MODEL = "com.kunekt.healthy.PHONE_STEP_MODEL_IWOWN";
    public static String PHONTO_URL = "http://54.67.86.82:80/iwown/";
    public static final String POWERSAVING_OPEN_OR_CLOSE = "powersaving_open_or_close";
    public static final int PREFIX_NOTIFY = 65314;
    public static final int PREFIX_WRITE = 65313;
    public static final String PRODUCT_URL = "http://api6.iwown.com/zhp/products-";
    public static final String PROGRESSBAR_STATUE = "progressbar_statue";
    public static final String QQ_INFORMATION_ALERT = "com.kunekt.healthy.QQ_INFORMATION_ALERT";
    public static final String QQ_LOGIN_ISQQLOGIN = "com.kunekt.healthy.QQ_LOGIN_ISQQLOGIN";
    public static final String QQ_LOGIN_OPENID = "com.kunekt.healthy.QQ_LOGIN_OPENID";
    public static final String QQ_LOGIN_OTHER = "com.kunekt.healthy.QQ_LOGIN_OTHER";
    public static final String QQ_LOGIN_TOKEN = "com.kunekt.healthy.QQ_LOGIN_TOKEN";
    public static final String QQ_SLEEP_URL = "https://openmobile.qq.com/v3/health/report_sleep";
    public static final String QQ_URL = "https://openmobile.qq.com/v3/health/report_steps";
    public static final String READ_DATE_TIME = "READ_DATE_TIME";
    public static final int RECORD_CALENDAR = 2;
    public static final int RECORD_HEART_RATE = 4;
    public static final int RECORD_SLEEP = 1;
    public static final int RECORD_SPORT = 0;
    public static final int RECORD_WEIGHT = 3;
    public static final String REG_NICKNAME = "com.kunekt.healthy.REG_NICKNAME";
    public static final String RING_MODEL = "com.kunekt.healthy.RING_MODEL";
    public static final String SCHEDULE_SHAKE_MODE = "com.kunekt.healthy.SCHEDULE_SHAKE_MODE";
    public static final String SCHEDULE_SHAKE_NUM = "com.kunekt.healthy.SCHEDULE_SHAKE_NUM";
    public static final String SEARCHING_DEVICES = "searching_devices";
    public static final String SEDENTARY_END_HOUR = "sedentary_end_hour";
    public static final String SEDENTARY_LUNCH_BREAK = "com.kunekt.healthy.SEDENTARY_LUNCH_BREAK";
    public static final String SEDENTARY_OPEN_OR_CLOSE = "sedentary_open_or_close";
    public static final String SEDENTARY_SHAKE_MODE = "com.kunekt.healthy.SEDENTARY_SHAKE_MODE";
    public static final String SEDENTARY_SHAKE_NUM = "com.kunekt.healthy.SEDENTARY_SHAKE_NUM";
    public static final String SEDENTARY_START_HOUR = "sedentary_start_hour";
    public static final String SEDENTARY_WEEK_INT = "sedentary_week_int";
    public static final String SEDENTARY_WEEK_STRING = "sedentary_week_string";
    public static final String SELECTED_SPORT_TYPES = "com.kunekt.healthy.lingyi.v3.SELECTED_SPORT_TYPES";
    public static final String SESSION_ID = "session_id";
    public static final String SLEEPDATE = "com.kunekt.healthy.sleepDate";
    public static final String SLEEPRATING = "com.kunekt.healthy.sleepRating";
    public static final String SLEEPSTRING = "com.kunekt.healthy.sleepString";
    public static final String SLEEP_DEVICE = "sleep_device";
    public static final String SLEEP_TIME = "com.healthy.healthy.sleep_time";
    public static final String SMS_ALERT = "com.kunekt.healthy.SMS_ALERT";
    public static final String SMS_APK_NAME = "com.kunekt.healthy.SMS_APK_NAME";
    public static final String SMS_APK_PACKAGE_NAME = "com.kunekt.healthy.SMS_APK_PACKAGE_NAME";
    public static final String SMS_SHAKE_MODE = "com.kunekt.healthy.SMS_SHAKE_MODE";
    public static final String SMS_SHAKE_NUM = "com.kunekt.healthy.SMS_SHAKE_NUM";
    public static final String SPALISH_ACTIVITY = "com.kunekt.healthy.SPALISH_ACTIVITY";
    public static final String SPALISH_CONNECT_DEVICES = "com.kunekt.healthy.SPALISH_CONNECT_DEVICES";
    public static final String SPALISH_CONNECT_DEVICES_SPORTNADHEALTH = "com.kunekt.healthy.SPALISH_CONNECT_DEVICES_SPORTNADHEALTH";
    public static final String SPORTDATE = "com.kunekt.healthy.sportDate";
    public static final String SPORTPURPOSE = "com.kunekt.healthy.sportpurpose";
    public static final String SPORTRATING = "com.kunekt.healthy.sportRating";
    public static final String SPORTSTRING = "com.kunekt.healthy.sportString";
    public static final String SPORT_FLUSH_VIEW = "com.kunekt.healthy.SPORT_FLUSH_VIEW";
    public static final String SPORT_TYPE_ACTION = "com.kunekt.healthy.SPORT_TYPE_ACTION";
    public static final String STEP_MOLDER_SWITCH = "com.kunekt.healthy.STEP_MOLDER_SWITCH";
    public static final String STEP_MOLDER_SWITCH_WRISTBAND = "com.kunekt.healthy.STEP_MOLDER_SWITCH_WRISTBAND";
    public static final String SYNC_TIMESTEP = "sync_timestep";
    public static final String SYSTEM_FONT_LIB_IWOWN = "com.kunekt.SYSTEM_FONT_LIB_IWOWN";
    public static final String TARGERWEIGHT = "com.kunekt.healthy.targetWeight";
    public static final String TARGET_STEP_SET = "target_step_set";
    public static final String TARGET_WEIGHT_2 = "com.kunekt.healthy.lingyi.v3.TARGET_WEIGHT_2";
    public static final String TEMPERATURE = "com.kunekt.healthy.temperature";
    public static final String TEMPERATURE_TYPE = "com.kunekt.healthy.TEMPERATURE_TYPE";
    public static final String TEMPORARY_CALORY = "temporary_calory";
    public static final String TEMPORARY_DISTANCE = "temporary_distance";
    public static final String TEMPORARY_STEP = "temporary_step";
    public static final String TOTALCAL = "com.kunekt.healthy.totalCal";
    public static final String TOTAL_SLEEP_TIME = "deep_sleep_time";
    public static final String UID = "com.kunekt.healthy.UID";
    public static final UUID UPDATE_SERVICE_MAIN_ = UUID.fromString("00001530-0000-1000-8000-00805f9b34fb");
    public static final String UPGRADE_DOWNLOAD = (NEW_API + "deviceservice/device/downloadUpgrade");
    public static final String UPLOAD_APP_NEW_VERSION = "com.kunekt.healthy.UPLOAD_APP_NEW_VERSION";
    public static final String UPLOAD_APP_UPLOAD_DETIAL = "com.kunekt.healthy.UPLOAD_APP_UPLOAD_DETIAL";
    public static final String UPLOAD_APP_URL = "com.kunekt.healthy.UPLOAD_APP_URL";
    public static final String UPLOAD_APP_VISON = "com.kunekt.healthy.UPLOAD_APP_VISON";
    public static final int UPLOAD_NG = 0;
    public static final int UPLOAD_OK = 1;
    public static final String UP_USER_INFO_SUCCESS = "UP_USER_INFO_SUCCESS";
    public static final String USER_BINDING_EMAIL_OR_PHONE = "bindingemailorphone";
    public static final String USER_DOWNLOAD_DATA = "dlPedoData";
    public static final String USER_DOWNLOAD_SLEEP_DATA = "user_download_sleep_data";
    public static final String USER_DOWNLOAD_SPORTHISTORYDATA = "downloadsporthistorydata";
    public static final String USER_DOWNLOAD_WEIGHTDATA = "downloadweightdata";
    public static final String USER_FIND_PASSWORD = "findpassword";
    public static final String USER_FORGET_PASSWORD = "getpwd";
    public static final String USER_GRADE = "getgrade";
    public static final String USER_IFNO_GET_ACTION = "getProfile";
    public static final String USER_INFO = "user_info";
    public static final String USER_ISFIRST = "user_isfirst";
    public static final String USER_LOGIN_ACTION = "login";
    public static final String USER_LOGIN_OTHER_ACTION = "user_login_other_action";
    public static final String USER_MAP_LOCATION = "upPos";
    public static final String USER_MESSAGE_EDIT = "editinfo";
    public static final String USER_PERSON_IFNO_PASSWORD = "edtProfile";
    public static final String USER_PERSON_USERPHOTO = "edtProfileUserphoto";
    public static final String USER_REGISTER_ACTION = "register";
    public static final String USER_RESET_NEWPASSWORD = "newpassword";
    public static final String USER_SUGGEST_SEND = "upFeedback";
    public static final String USER_SYNC_DATA = "udPedoData";
    public static final String USER_UPLOAD_HEARTRATE_DATA = "uploadheartratedata";
    public static final String USER_UPLOAD_SLEEPDATA = "uploadSleepData";
    public static final String USER_UPLOAD_SPORTHISTORYDATA = "uploadsporthistorydata";
    public static final String USER_UPLOAD_TRACKDATA = "uploadTrackData";
    public static final String USER_UPLOAD_WEIGHTDATA = "uploadweightdata";
    public static final String USER_VERSION_DETECTION = "appUpdate";
    public static final String USER_WEIGHT_DOWNLOAD = "dlScaleData";
    public static final String USER_WEIGHT_UPLOAD = "udScaleData";
    public static final String WALK_PLAN_COMPELETE = "walk_plan_compelete";
    public static final String WALK_PLAN_COMPELETE_DATE = "walk_plan_compelete_date";
    public static String WEATHER_API = BaseNetUrl.Base_API_AMAZON_PROD;
    public static final String WEATHER_TIME = "com.kunekt.healthy.weather_time";
    public static final String WEATHER_URL = (WEATHER_API + "weatherservice/getweatherandroid");
    public static final String WECHAT_LOGIN_OTHER = "com.kunekt.healthy.WECHAT_LOGIN_OTHER";
    public static final String WEICHAT_URL_USERINOF = "https://api.weixin.qq.com/sns/userinfo";
    public static final String WEICHAT_URL_token = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String WEIGHT = "com.kunekt.healthy.weight";
    public static final String WEIGHT_DERVICE_ADDRESS = "weight_dervice_address";
    public static final String WEIGHT_DERVICE_NAME = "weight_dervice_name";
    public static final String WEIGHT_PROGRESS_NUMGER = "weight_progress_number";
    public static final String WEIGHT_PROGRESS_TEMP = "weight_progress_number";
    public static final String WEIGHT_SCALE_DATE = "weight_scale_date";
    public static final String WEIGHT_SCALE_DATE_TEMP = "weight_suscale_date_temp";
    public static final String WEIGHT_SCALE_TIME = "weight_scale_time";
    public static final String WEIGHT_SCALE_TIME_TEMP = "weight_scale_time_temp";
    public static final String WEIGHT_TARGET_PROGRESS = "weight_target_progress";
    public static final String WEIGHT_TARGET_SCALE = "weight_target_scale";
    public static final String WEIXING_INFORMATION_ALERT = "com.kunekt.healthy.WEIXING_INFORMATION_ALERT";
    public static final String WORK_TYPE = "work_type";
    public static final String WX_BIND_INTENT = "WX_BIND_INTENT";
    public static final String WX_BIND_STEPS_TOSERVER = "WX_BIND_STEPS_TOSERVER";
    public static final String ZERO_FRIEND_ID = "zero_friend_id";
    public static final String ZERO_FRIEND_NAME = "zero_friend_name";
    public static final String ZERO_GREETINGS = "zero_greetings";
    public static final String ZERO_OPEN_OR_CLOSE = "zero_open_or_close";
    public static int second = 0;

    public class Activitys {
        public static final int AddClockActivity = 1;
        public static final int SendentaryAcitvity2 = 0;

        public Activitys() {
        }
    }

    public class BroadCastAction {
        public static final String FINISH_ACTIVITY_BY_BROADCASTRECEIVER = "com.kunekt.healthy.FINISH_ACTIVITY_BY_BROADCASTRECEIVER";
        public static final String HUANXING_MESSAGE_BROADCASTACTION = "com.kunekt.healthy.HUANXING_MESSAGE_BROADCASTACTION";
        public static final String HUANXING_MESSAGE_CHART_SERVICE_ERROR = "com.kunekt.healthy.HUANXING_MESSAGE_CHART_SERVICE_ERROR";
        public static final String HUANXING_MESSAGE_LOGIN_OTHER = "com.kunekt.healthy.HUANXING_MESSAGE_LOGIN_OTHER";
        public static final String HUANXING_MESSAGE_NETWORKOK = "com.kunekt.healthy.HUANXING_MESSAGE_NETWORKOK";
        public static final String HUANXING_MESSAGE_NETWORK_ERROR = "com.kunekt.healthy.HUANXING_MESSAGE_NETWORK_ERROR";
        public static final String SQLITE_INSERT_ACTION = "sqlite_insert_action";

        public BroadCastAction() {
        }
    }

    public static class DEV_PLATFORM {
        public static final int DIALOG = 3;
        public static final int MTK = 4;
        public static final int NORDIC = 2;
        public static final int TI = 1;
    }

    public static class Debug {
        public static boolean DEBUG = true;
        public static boolean flag = true;
    }

    public class ErrorCode {
        public static final int ERROR_CODE_DB_ERROR = -1;
        public static final int ERROR_CODE_FILE_ERROR = -2;
        public static final int ERROR_CODE_NETWORK_ERROR = -3;
        public static final int ERROR_CODE_PARSE_ERROR = -4;
        public static final int ERROR_CODE_UNKOWN_ERROR = -5;
        public static final int EXIST_ALREADY = 2;
        public static final int OPERATION_CANCELLED = -999;
        public static final int SUCCESS = 0;

        public ErrorCode() {
        }
    }

    public static class HEART_REMIND_TYPE {
        public static final int AEROBIC = 2;
        public static final int ANAEROBIC = 3;
        public static final int FAT_BURNNING = 1;
        public static final int NO_SELECTED = -1;
        public static final int WARM_UP = 0;
    }

    public class LogKeyStr {
        public static final String BLE_LOG_KEY = "bleup";
        public static final String NOTIF_LOG_KEY = "notify";
        public static final String WRITE_LOG_KEY = "write";

        public LogKeyStr() {
        }
    }

    public static class LogPath {
        public static final String BLE_LOG_NOTIF = "/Zeroner/iwownfit_3/";
        public static final String BLE_LOG_PATH = "/Zeroner/iwownfit_3/blelog/";
        public static final String BLE_LOG_WRITE = "/Zeroner/iwownfit_3/";
        public static final String DATA61_PATH = "/Zeroner/iwownfit_5_0/blelog/61_data/";
        public static final String DATA62_PATH = "/Zeroner/iwownfit_5_0/blelog/62_data/";
        public static final String FILE61_PATH = (Environment.getExternalStorageDirectory() + "/Zeroner/iwownfit_5_0/blelog/61_data/");
        public static final String FILE62_PATH = (Environment.getExternalStorageDirectory() + "/Zeroner/iwownfit_5_0/blelog/62_data/");
    }

    public class OPENID {
        public static final String QQ_OPENID = "1104800774";
        public static final String WECHAT_SERECT = "5d90948461cdcfc53f120b672bab9590";
        public static final String WEICHAT_OPENID = "wx695ef7ad14cc332e";

        public OPENID() {
        }
    }

    public class PushAppPackName {
        public static final String FACEBOOK = "com.facebook.orca";
        public static final String GMAIL = "com.google.android.gm";
        public static final String KAKAOTALK = "com.kakao.talk";
        public static final String LINE = "jp.naver.line.android";
        public static final String QQ = "com.tencent.mobileqq";
        public static final String SKYPE1 = "com.skype.rover";
        public static final String SKYPE2 = "com.skype.raider";
        public static final String TWITTER = "com.twitter.android";
        public static final String WECHAT = "com.tencent.mm";
        public static final String WHATSAPP = "com.whatsapp";

        public PushAppPackName() {
        }
    }

    public class SERVICE_UTIL {
        public static final int BROADCAST_ELAPSED_TIME_DELAY = 120000;
        public static final int ELAPSED_TIME = 15000;
        public static final int ELAPSED_TIME_DELAY = 120000;
        public static final String POI_SERVICE = "com.kunekt.healthy.timeservice";
        public static final String POI_SERVICE_ACTION = "com.kunket.healthy.version_v3.ACTION_TIME_SERVICE";
        public static final int RETRIVE_SERVICE_COUNT = 50;
        public static final String WORKER_SERVICE = "com.kunekt.healthy.WorkService";

        public SERVICE_UTIL() {
        }
    }

    public static class SETTING_INDEXS {
        public static final int ADD_SPORT = 17;
        public static final int ALARM = 15;
        public static final int AUTO_HEART_RATE = 11;
        public static final int AUTO_SLEEP = 4;
        public static final int BACK_COLOR = 6;
        public static final int BACK_LIGHT_TIME = 5;
        public static final int CAMERA = 18;
        public static final int DATE_FORMAT = 9;
        public static final int DISCONNECT_TIP = 8;
        public static final int FW_UPGRADE = 20;
        public static final int GESTURE = 1;
        public static final int HEART_GUIDE = 13;
        public static final int HOUR_24 = 3;
        public static final int LANGUAGE = 7;
        public static final int LED = 0;
        public static final int SCHEDULE = 16;
        public static final int SEDENTARY = 19;
        public static final int SHOULD_READ_0X18 = 22;
        public static final int SUPPORT_HEART = 21;
        public static final int UNIT_MEASUREMENT = 2;
        public static final int VIBRATION = 12;
        public static final int WEATHER = 14;
        public static final int WRIST_TURNING = 10;
    }

    public class ServiceErrorCode {
        public static final int COLUN_IS_ERROR = 302;
        public static final int COLUN_IS_NULL = 301;
        public static final int EMAIL_IS_NOT_EXISTS = 500;
        public static final int FRIEND_IS_FULL = 401;
        public static final int NO_JSON_DATA = 203;
        public static final int PASSWORD_ERROR = 2012;
        public static final int REQUEST_ERROR = 6000;
        public static final int SERVICE_ERROR = -10001;
        public static final int UNKNOW_ERROR = 5000;
        public static final int UPLOAD_FILE_SO_BIG = 4000;
        public static final int USERNAME_IS_EXISTS = 2004;
        public static final int USERNAME_OR_PASSWORD_ERROR = 102;
        public static final int USER_IS_NOT_EXISTS = 2001;
        public static final int YOUR_FRIEND_IS_FULL = 402;
        public static final int YOU_AND_ME_IS_FRIEND = 600;

        public ServiceErrorCode() {
        }
    }

    public class ServiceErrorID {
        public static final int EMAIL_IS_NULL = 201;
        public static final int EMAIL_REPEATED = 204;
        public static final int NO_JSON_DATA = 203;
        public static final int PASSWORDERROR = 3;
        public static final int PASSWORDNULL = 102;
        public static final int SESSION_ERRORID = 101;

        public ServiceErrorID() {
        }
    }

    public class Url {
        public static final String IWOWN_BBS = "http://bbs.iwown.com/";
        public static final String IWOWN_PHONE_MODEL = "http://114.215.151.68/health/new_health/help/fitphone.html";
        public static final String IWOWN_PHONE_PROGREM = "http://114.215.151.68/health/new_health/help/Q&A.html";

        public Url() {
        }
    }

    public class V3_SharePreferences {
        public static final String ADS_URL_CHANGE = "com.kunekt.healthy.ADS_URL_CHANGE";
        public static final String AVATAR_LAST_MODIFI_TIME = "com.kunekt.healthy.AVATAR_LAST_MODIFI_TIME";
        public static final String Bluetooth_Type = "com.kunekt.healthy.iwownfit_pro.Bluetooth_Type";
        public static final String COMMON_URL = "com.kunekt.healthy.COMMON_URL";
        public static final String DATA_0X28_INDEX = "com.kunekt.healthy.DATA_0X28_INDEX";
        public static final String DATE_SOFT_PUSH = "com.kunekt.healthy.DATE_SOFT_PUSH";
        public static final String DEVICE_MAC = "com.kunekt.healthy.DEVICE_MAC";
        public static final String DEV_APP_LOG = "com.kunekt.healthy.DEV_APP_LOG";
        public static final String DEV_BLE_LOG = "com.kunekt.healthy.DEV_BLE_LOG";
        public static final String EPO_FAIL_TIMES = "com.kunekt.healthy.iwownfit_pro.EPO_FAIL_TIMES";
        public static final String FM_INFO_CAN_CLEAR_WHEN_UNBIND = "com.kunekt.healthy.FM_INFO_CAN_CLEAR_WHEN_UNBIND";
        public static final String FM_UPDATE_MAC = "com.kunekt.healthy.FM_UPDATE_MAC";
        public static final String Guide_MyProfile = "com.kunekt.healthy.Guide_MyProfile";
        public static final String HEARTRATE_PARAMS_JSON = "com.kunekt.healthy.HEARTRATE_PARAMS_JSON";
        public static final String Height_Weight_Unit = "com.kunekt.healthy.HeightWeightUnit";
        public static final String IS_NEW_PORTOCAL = "com.kunekt.healthy.iwownfit_pro.IS_NEW_PORTOCAL";
        public static final String IS_SAME_DEVICE = "com.kunekt.healthy.IS_SAME_DEVICE";
        public static final String IS_UPDATE = "com.kunekt.healthy.IS_UPDATE";
        public static final String LAST_EPO_SUECESS_TIME = "com.kunekt.healthy.iwownfit_pro.LAST_EPO_SUECESS_TIME";
        public static final String LAST_FW_VERSION = "com.kunekt.healthy.LAST_FW_VERSION";
        public static final String LAST_GET_SETTING_TIME = "com.kunekt.healthy.LAST_GET_SETTING_TIME";
        public static final String LAST_MAC = "com.kunekt.healthy.LAST_MAC";
        public static final String LOGIN_NAME = "com.kunekt.healthy.LOGIN_NAME";
        public static final String Login_Success = "com.kunekt.healthy.Login_Success";
        public static final String PHONTO_URL = "com.kunekt.healthy.PHONTO_URL";
        public static final String PULLVIEW_LAST_TIMESTAMP = "com.kunekt.healthy.lingyi.v3.PULLVIEW_LAST_TIMESTAMP";
        public static final String TAGET_WEIGHT = "com.kunekt.healthy.TAGET_WEIGHT";
        public static final String TICKER_URL = "com.kunekt.healthy.TICKER_URL";
        public static final String USER_EXIST = "com.kunekt.healthy.USER_EXIST";
        public static final String WX_BIND_OR_LOGIN = "com.kunekt.healthy.WX_BIND_OR_LOGIN";
        public static final String WX_LOGIN_ACTION = "com.kunekt.healthy.WX_LOGIN_ACTION";
        public static final String WX_OPEND_ID = "com.kunekt.healthy.WX_OPEND_ID";

        public V3_SharePreferences() {
        }
    }

    public class ZERONER_DEV_TYPE {
        public static final int BRACELET = 0;
        public static final int P1_WATCH = 1;

        public ZERONER_DEV_TYPE() {
        }
    }
}
