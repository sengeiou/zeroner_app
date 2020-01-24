package com.iwown.device_module.device_setting.language;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageUtil {
    public static int LANGUAGE_SUPPORT_COUNT = 19;

    public static String getLanguageString(Context mContext, int type) {
        if (BluetoothOperation.isMtk()) {
            switch (type) {
                case 0:
                    return mContext.getString(R.string.device_module_language_english);
                case 1:
                    return mContext.getString(R.string.device_module_language_chinese);
                case 2:
                    return mContext.getString(R.string.device_module_language_italian);
                case 3:
                    return mContext.getString(R.string.device_module_language_japan);
                case 4:
                    return mContext.getString(R.string.device_module_language_french);
                case 5:
                    return mContext.getString(R.string.device_module_language_german);
                case 6:
                    return mContext.getString(R.string.device_module_language_portuguese);
                case 7:
                    return mContext.getString(R.string.device_module_language_spanish);
                case 8:
                    return mContext.getString(R.string.device_module_language_russian);
                case 9:
                    return mContext.getString(R.string.device_module_language_korean);
                case 10:
                    return mContext.getString(R.string.device_module_language_arabic);
                case 11:
                    return mContext.getString(R.string.device_module_language_vietnamese);
                case 12:
                    return mContext.getString(R.string.device_module_language_Poland);
                case 13:
                    return mContext.getString(R.string.device_module_language_Romania);
                case 14:
                    return mContext.getString(R.string.device_module_language_Swedish);
                case 255:
                    return mContext.getString(R.string.device_module_language_basic);
            }
        } else {
            switch (type) {
                case 0:
                    return mContext.getString(R.string.device_module_language_english);
                case 1:
                    return mContext.getString(R.string.device_module_language_chinese);
                case 2:
                    return mContext.getString(R.string.device_module_language_italian);
                case 3:
                    return mContext.getString(R.string.device_module_language_japan);
                case 4:
                    return mContext.getString(R.string.device_module_language_french);
                case 5:
                    return mContext.getString(R.string.device_module_language_german);
                case 6:
                    return mContext.getString(R.string.device_module_language_portuguese);
                case 7:
                    return mContext.getString(R.string.device_module_language_spanish);
                case 8:
                    return mContext.getString(R.string.device_module_language_russian);
                case 9:
                    return mContext.getString(R.string.device_module_language_korean);
                case 10:
                    return mContext.getString(R.string.device_module_language_arabic);
                case 11:
                    return mContext.getString(R.string.device_module_language_vietnamese);
                case 12:
                    return mContext.getString(R.string.device_module_language_Thais);
                case 13:
                    return mContext.getString(R.string.device_module_language_Turkish);
                case 14:
                    return mContext.getString(R.string.device_module_language_Swedish);
                case 15:
                    return mContext.getString(R.string.device_module_language_Poland);
                case 16:
                    return mContext.getString(R.string.device_module_language_Romania);
                case 255:
                    return mContext.getString(R.string.device_module_language_basic);
            }
        }
        return null;
    }

    public static int getLanguageType(String model) {
        int type = DeviceUtils.localDeviceSetting().getLanguage();
        if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            if (type == 15) {
                type = 12;
            } else if (type == 16) {
                type = 13;
            }
            return type;
        }
        List<Integer> languageType = getLanguageType();
        if (languageType == null || languageType.size() == 0) {
            return 0;
        }
        for (int i = 0; i < languageType.size(); i++) {
            if (((Integer) languageType.get(i)).intValue() == type) {
                return type;
            }
        }
        return 0;
    }

    public static boolean isSupportLanguage(int language) {
        List<Integer> languageType = getLanguageType();
        if (languageType == null || languageType.size() == 0) {
            return false;
        }
        for (int i = 0; i < languageType.size(); i++) {
            if (((Integer) languageType.get(i)).intValue() == language) {
                return true;
            }
        }
        return false;
    }

    public static int getLocalLanguage() {
        int localLanguage;
        String language = ContextUtil.app.getResources().getConfiguration().locale.getLanguage();
        if (language.equals(new Locale("en").getLanguage())) {
            localLanguage = 0;
        } else if (language.equals(new Locale("zh").getLanguage())) {
            localLanguage = 1;
        } else if (language.equals(new Locale("it").getLanguage())) {
            localLanguage = 2;
        } else if (language.equals(new Locale("ja").getLanguage())) {
            localLanguage = 3;
        } else if (language.equals(new Locale("fr").getLanguage())) {
            localLanguage = 4;
        } else if (language.equals(new Locale("de").getLanguage())) {
            localLanguage = 5;
        } else if (language.equals(new Locale("pt").getLanguage())) {
            localLanguage = 6;
        } else if (language.equals(new Locale("es").getLanguage())) {
            localLanguage = 7;
        } else if (language.equals(new Locale("ru").getLanguage())) {
            localLanguage = 8;
        } else if (language.equals(new Locale("ko").getLanguage())) {
            localLanguage = 9;
        } else if (language.equals(new Locale("ar").getLanguage())) {
            localLanguage = 10;
        } else if (language.equals(new Locale("uk").getLanguage())) {
            localLanguage = 11;
        } else if (language.equals(new Locale("th").getLanguage())) {
            localLanguage = 12;
        } else if (language.equals(new Locale("tr").getLanguage())) {
            localLanguage = 13;
        } else if (language.equals(new Locale("sv").getLanguage())) {
            localLanguage = 14;
        } else if (language.equals(new Locale("pl").getLanguage())) {
            localLanguage = 15;
        } else if (language.equals(new Locale("ro").getLanguage())) {
            localLanguage = 16;
        } else {
            localLanguage = 0;
        }
        if (isSupportLanguage(localLanguage)) {
            return localLanguage;
        }
        return 0;
    }

    public static List<Integer> getLanguageType() {
        List<Integer> languages = new ArrayList<>();
        TB_DeviceSettings settings = DeviceSettingsBiz.getInstance().queryDevSettings();
        if (settings != null) {
            KLog.i(String.format("---*devsetting:%s", new Object[]{settings.getSetting()}));
            for (SettingBean setting : (List) new Gson().fromJson(settings.getSetting(), new TypeToken<List<SettingBean>>() {
            }.getType())) {
                if (setting.getType() == 7) {
                    int ls = setting.getValueInt();
                    for (int count = LANGUAGE_SUPPORT_COUNT; count >= 0; count--) {
                        if (((ls >> count) & 1) == 1) {
                            if (count == 18) {
                                languages.add(Integer.valueOf(255));
                            } else {
                                languages.add(Integer.valueOf(count));
                            }
                        }
                    }
                }
            }
        }
        return languages;
    }
}
