package com.iwown.data_link;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.iwown.data_link.consts.ApiConst;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static String APP_NAME = IwownFit_Pro;
    public static final String DB_Name = "healthy_all_in_5.0";
    public static final int DB_Version = 21;
    public static final int FACING_BACK = 0;
    public static final int FACING_FRONT = 1;
    public static final String INSTABUG_KEY = "c2d787719fc7a1aba9e85cc07f9208e4";
    public static final String IwownFit_Pro = "IwownFit_Pro";
    public static final String Iwown_Healthy = "Iwown_Healthy";
    public static String SHARE_SCREEN_PATH = "/Zeroner/screen/";
    public static final String Zeroner_Health_Pro = "Zeroner_Health_Pro";
    public static Map<Integer, String> server_msgs = new HashMap();

    public static class LogPath {
        public static final String BLE_LOG_NOTIF = (ZERONER + Constants.APP_NAME + "/");
        public static final String BLE_LOG_PATH = (ZERONER + Constants.APP_NAME + "/blelog/");
        public static final String BLE_LOG_WRITE = (ZERONER + Constants.APP_NAME + "/");
        public static final String DATA61_PATH = (ZERONER + Constants.APP_NAME + "/blelog/61_data/");
        public static final String DATA62_PATH = (ZERONER + Constants.APP_NAME + "/blelog/62_data/");
        public static final String FILE61_PATH = (Environment.getExternalStorageDirectory() + DATA61_PATH);
        public static final String FILE62_PATH = (Environment.getExternalStorageDirectory() + DATA62_PATH);
        public static final String GPS_PATH = (ZERONER + Constants.APP_NAME + "/sport_gps/gps/");
        public static final String HR_PATH = (ZERONER + Constants.APP_NAME + "/sport_gps/hr/");
        public static final String R1_PATH = (ZERONER + Constants.APP_NAME + "/sport_gps/r1/");
        public static final String SWIM_PATH = (ZERONER + Constants.APP_NAME + "/sport_gps/swim/");
        public static final String ZERONER = "/Zeroner/";
    }

    public static class WeatherType {
        public static final int CLOUDY = 1;
        public static final int FOG = 8;
        public static final int HEAVY_RAIN = 5;
        public static final int INVALID = -1;
        public static final int LIGHT_RAIN = 3;
        public static final int OVERCAST = 2;
        public static final int Partly_Sunny = 10;
        public static final int RAIN = 4;
        public static final int SANDSTORM = 9;
        public static final int SHOWER = 6;
        public static final int SNOW = 7;
        public static final int SUNNY = 0;
        public static final int T_Storms = 11;
    }

    public static void initServerMsg(Context context) {
        server_msgs.put(Integer.valueOf(10001), context.getResources().getString(R.string.sql_error));
        server_msgs.put(Integer.valueOf(ApiConst.UserAlreadyExist), context.getResources().getString(R.string.activity_user_exist));
        server_msgs.put(Integer.valueOf(50001), context.getResources().getString(R.string.parameter_error));
        server_msgs.put(Integer.valueOf(ApiConst.NETWORK_ERROR), context.getResources().getString(R.string.message_network_error));
        server_msgs.put(Integer.valueOf(ApiConst.PasswordNotMatch), context.getResources().getString(R.string.message_login_error));
        server_msgs.put(Integer.valueOf(ApiConst.UserNotExist), context.getResources().getString(R.string.no_user_error));
        server_msgs.put(Integer.valueOf(-10), context.getResources().getString(R.string.unkown_error));
    }

    public static String getServerMsgByCode(int code) {
        String s = (String) server_msgs.get(Integer.valueOf(code));
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        return s;
    }
}
