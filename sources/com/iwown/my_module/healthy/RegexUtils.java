package com.iwown.my_module.healthy;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RegexUtils {
    private static List chinaMobile1 = Arrays.asList(new String[]{"135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "182", "187", "188"});
    private static List chinaMobile2 = Arrays.asList(new String[]{"1340", "1341", "1342", "1343", "1344", "1345", "1346", "1347", "1348"});
    private static List chinaUnicom = Arrays.asList(new String[]{"130", "131", "132", "145", "155", "156", "186", "185"});
    public static final String telRegex = "[1][3578]\\d{9}";

    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        return mobiles.matches(telRegex);
    }

    public static boolean isEixtExChar(String str) {
        if (Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]").matcher(str).find()) {
            return true;
        }
        return false;
    }

    public static boolean isCode(String code) {
        if (TextUtils.isEmpty(code) || code.length() != 4) {
            return false;
        }
        try {
            Integer.parseInt(code);
            return true;
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    public static boolean isPWD(String pwd) {
        String regex = "^[a-zA-Z0-9]{6,18}$";
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        return pwd.matches(regex);
    }

    public static String phoneNoHide(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String cardIdHide(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }

    public static boolean checkVehicleNo(String vehicleNo) {
        return Pattern.compile("^[一-龥]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$").matcher(vehicleNo).find();
    }

    public static int String_length(String value) {
        int valueLength = 0;
        String chinese = "[一-龥]";
        for (int i = 0; i < value.length(); i++) {
            if (value.substring(i, i + 1).matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength++;
            }
        }
        return valueLength;
    }

    public static boolean checkIdCard(String idCard) {
        return Pattern.matches("[1-9]\\d{13,16}[a-zA-Z0-9]{1}", idCard);
    }

    public static boolean checkMobile(String mobile) {
        return Pattern.matches("(\\+\\d+)?1[3458]\\d{9}$", mobile);
    }

    public static boolean checkPhone(String phone) {
        return Pattern.matches("(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$", phone);
    }

    public static boolean checkEmail(String email) {
        return Pattern.matches("\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?", email);
    }

    public static boolean checkDigit(String digit) {
        return Pattern.matches("\\-?[1-9]\\d+", digit);
    }

    public static boolean checkDecimals(String decimals) {
        return Pattern.matches("\\-?[1-9]\\d+(\\.\\d+)?", decimals);
    }

    public static boolean checkBlankSpace(String blankSpace) {
        return Pattern.matches("\\s+", blankSpace);
    }

    public static boolean checkChinese(String chinese) {
        return Pattern.matches("^[一-龥]+$", chinese);
    }

    public static boolean checkBirthday(String birthday) {
        return Pattern.matches("[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}", birthday);
    }

    public static boolean checkURL(String url) {
        return Pattern.matches("(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?", url);
    }

    public static boolean checkPostcode(String postcode) {
        return Pattern.matches("[1-9]\\d{5}", postcode);
    }

    public static boolean checkIpAddress(String ipAddress) {
        return Pattern.matches("[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))", ipAddress);
    }

    public static String getMobileTypeYYS(String mobile) {
        try {
            Long.parseLong(mobile);
            boolean bolChinaUnicom = chinaUnicom.contains(mobile.substring(0, 3));
            boolean bolChinaMobile1 = chinaMobile1.contains(mobile.substring(0, 3));
            boolean bolChinaMobile2 = chinaMobile2.contains(mobile.substring(0, 4));
            if (bolChinaUnicom) {
                return "10010";
            }
            if (bolChinaMobile1 || bolChinaMobile2) {
                return "10086";
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
