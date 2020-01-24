package com.iwown.my_module.utility;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtility {
    public static int getDaysInMonth(int year, int month) {
        if (month < 1 || month > 12 || year < 0) {
            return 0;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
            return 28;
        }
        return 29;
    }

    public static int getAgeByBirthday(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                return 0;
            }
            age = now.get(1) - born.get(1);
            if (now.get(6) < born.get(6)) {
                age--;
            }
        }
        return age;
    }
}
