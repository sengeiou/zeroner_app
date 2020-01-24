package com.iwown.my_module.utility;

import java.util.regex.Pattern;

public class TextValidator {
    private static final Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern phoneNumber = Pattern.compile("^1(3[0-9]|5[0-35-9]|7[06-8]|8[0-9])\\d{8}$");

    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    public static boolean isPhoneNumber(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return phoneNumber.matcher(email).matches();
    }
}
