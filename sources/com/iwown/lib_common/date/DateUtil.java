package com.iwown.lib_common.date;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class DateUtil {
    public static long Hour_S_Min = 3600;
    private static final ThreadLocal<SimpleDateFormat> dFHHmm = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFHHmmss = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFMMdd = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFMMdd_HHmm = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd HH:mm");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFSyyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFyyyyMM = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFyyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    public static final String dFyyyyMMdd1 = "yyyy-MM-dd";
    public static SimpleDateFormat dFyyyyMMddF = new SimpleDateFormat("yyyy-MM-dd");
    private static final ThreadLocal<SimpleDateFormat> dFyyyyMMdd_HHmm = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dFyyyyMMdd_HHmmss = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static SimpleDateFormat dFyyyyMMddmmF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final String yyyyMMdd_HHmm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat yyyyMMdd_HHmmssF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Calendar c;

    public enum DateFormater {
        MMdd,
        MMdd_HHmm,
        yyyyMM,
        yyyyMMdd,
        yyyyMMdd_HHmm,
        yyyyMMdd_HHmmss,
        HHmm,
        HHmmss,
        yyyyMMddHHmm,
        SyyyyMMdd,
        dFyyyyMMdd,
        dFHHmm
    }

    public long getZeroTime() {
        return new DateUtil(getYear(), getMonth(), getDay()).getUnixTimestamp();
    }

    public String getZeroTimeYyyyMMdd_HHmmssDate() {
        return new DateUtil(getYear(), getMonth(), getDay()).getYyyyMMdd_HHmmssDate();
    }

    public long getZeroTime1() {
        return new DateUtil(getYear(), getMonth(), getDay()).getTimestamp();
    }

    public static long getFirstDayMonth(Date date) {
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        c2.add(2, 0);
        c2.set(5, 1);
        return c2.getTimeInMillis();
    }

    public static long getLastDayMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(2, 0);
        ca.set(5, ca.getActualMaximum(5));
        return ca.getTimeInMillis();
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(5);
    }

    public int getTodayMin() {
        return Math.round((float) ((this.c.getTimeInMillis() - getZeroTime1()) / 60000)) + 1;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        boolean isSameYear;
        boolean isSameMonth;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        if (cal1.get(1) == cal2.get(1)) {
            isSameYear = true;
        } else {
            isSameYear = false;
        }
        if (!isSameYear || cal1.get(2) != cal2.get(2)) {
            isSameMonth = false;
        } else {
            isSameMonth = true;
        }
        if (!isSameMonth || cal1.get(5) != cal2.get(5)) {
            return false;
        }
        return true;
    }

    public boolean isSameDay(long compare_time, boolean isUnix) {
        DateUtil compare_dt = new DateUtil(compare_time, isUnix);
        if (compare_dt.getYear() == getYear() && compare_dt.getMonth() == getMonth() && compare_dt.getDay() == getDay()) {
            return true;
        }
        return false;
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        boolean isSameYear;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        if (cal1.get(1) == cal2.get(1)) {
            isSameYear = true;
        } else {
            isSameYear = false;
        }
        if (!isSameYear || cal1.get(2) != cal2.get(2)) {
            return false;
        }
        return true;
    }

    public static long getPreOrNextTimeByDay(long marignSize) {
        return System.currentTimeMillis() - (86400000 * marignSize);
    }

    public static long getGMTDate(long record_date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String time = sdf.format(new Date(1000 * record_date));
            return new DateUtil(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(5, 7)), Integer.parseInt(time.substring(8, 10)), Integer.parseInt(time.substring(11, 13)), 0, 0).getUnixTimestamp();
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            DateUtil dateUtil = new DateUtil(record_date, true);
            dateUtil.setHour(0);
            dateUtil.setMinute(0);
            dateUtil.setSecond(0);
            return dateUtil.getUnixTimestamp();
        }
    }

    public static DateUtil valueOf(String sdate) {
        String MMddFmt = "[0-9]{2}-[0-9]{2}";
        String MMdd_HHmmFmt = "[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";
        String yyyyMMFmt = "[0-9]{4}-[0-9]{2}";
        String yyyyMMddFmt = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        String yyyyMMdd_HHmmFmt = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";
        String yyyyMMdd_HHmmssFmt = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
        String HHmmFmt = "[0-9]{2}:[0-9]{2}";
        String HHmmssFmt = "[0-9]{2}:[0-9]{2}:[0-9]{2}";
        Pattern compile = Pattern.compile(yyyyMMdd_HHmmssFmt);
        try {
            if (Pattern.compile(MMddFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.MMdd);
            }
            if (Pattern.compile(MMdd_HHmmFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.MMdd_HHmm);
            }
            if (Pattern.compile(yyyyMMFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.yyyyMM);
            }
            if (Pattern.compile(yyyyMMddFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.yyyyMMdd);
            }
            if (Pattern.compile(yyyyMMdd_HHmmFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.yyyyMMdd_HHmm);
            }
            if (Pattern.compile(yyyyMMdd_HHmmssFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.yyyyMMdd_HHmmss);
            }
            if (Pattern.compile(HHmmFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.HHmm);
            }
            if (Pattern.compile(HHmmssFmt).matcher(sdate).matches()) {
                return parse(sdate, DateFormater.HHmmss);
            }
            return null;
        } catch (ParseException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static DateUtil parse(String sdate, DateFormater formater) throws ParseException {
        Date date = null;
        switch (formater) {
            case MMdd:
                date = ((SimpleDateFormat) dFMMdd.get()).parse(sdate);
                break;
            case MMdd_HHmm:
                date = ((SimpleDateFormat) dFMMdd_HHmm.get()).parse(sdate);
                break;
            case yyyyMM:
                date = ((SimpleDateFormat) dFyyyyMM.get()).parse(sdate);
                break;
            case yyyyMMdd:
                date = ((SimpleDateFormat) dFyyyyMMdd.get()).parse(sdate);
                break;
            case yyyyMMdd_HHmm:
                date = ((SimpleDateFormat) dFyyyyMMdd_HHmm.get()).parse(sdate);
                break;
            case yyyyMMdd_HHmmss:
                date = ((SimpleDateFormat) dFyyyyMMdd_HHmmss.get()).parse(sdate);
                break;
            case HHmm:
                date = ((SimpleDateFormat) dFHHmm.get()).parse(sdate);
                break;
            case HHmmss:
                date = ((SimpleDateFormat) dFHHmmss.get()).parse(sdate);
                break;
        }
        return new DateUtil(date);
    }

    public DateUtil() {
        this.c = Calendar.getInstance();
    }

    public DateUtil(long timestamp, boolean isUnix) {
        this.c = Calendar.getInstance();
        if (isUnix) {
            this.c.setTimeInMillis(1000 * timestamp);
        } else {
            this.c.setTimeInMillis(timestamp);
        }
    }

    public DateUtil(Date date) {
        this.c = Calendar.getInstance();
        this.c.setTime(date);
    }

    public DateUtil(int year, int month, int day) {
        this(year, month, day, 0, 0, 0);
    }

    public DateUtil(int year, int month, int day, int hour, int minute) {
        this(year, month, day, hour, minute, 0);
    }

    public DateUtil(int year, int month, int day, int hour, int minute, int second) {
        this.c = Calendar.getInstance();
        this.c.set(1, year);
        this.c.set(2, month - 1);
        this.c.set(5, day);
        this.c.set(11, hour);
        this.c.set(12, minute);
        this.c.set(13, second);
    }

    public DateUtil(int hour, int minute) {
        this.c = Calendar.getInstance();
        this.c.set(11, hour);
        this.c.set(12, minute);
    }

    public boolean isToday() {
        DateUtil d = new DateUtil();
        return getYear() == d.getYear() && getMonth() == d.getMonth() && getDay() == d.getDay();
    }

    public boolean isSameWeek(int number) {
        return number == new DateUtil(new Date()).getWeekOfYear();
    }

    public boolean isSameMonth(int number, int year) {
        return number == getMonth() && getYear() == year;
    }

    public int daysBetweenMe(DateUtil dateUtil) {
        return (int) (Math.abs(getUnixTimestamp() - dateUtil.getUnixTimestamp()) / 86400);
    }

    public Date toDate() {
        return this.c.getTime();
    }

    public String toFormatString(DateFormater formater) {
        Date date = toDate();
        String sdate = "Unknown";
        switch (formater) {
            case MMdd:
                return ((SimpleDateFormat) dFMMdd.get()).format(date);
            case MMdd_HHmm:
                return ((SimpleDateFormat) dFMMdd_HHmm.get()).format(date);
            case yyyyMM:
                return ((SimpleDateFormat) dFyyyyMM.get()).format(date);
            case yyyyMMdd:
                return ((SimpleDateFormat) dFyyyyMMdd.get()).format(date);
            case yyyyMMdd_HHmm:
                return ((SimpleDateFormat) dFyyyyMMdd_HHmm.get()).format(date);
            case yyyyMMdd_HHmmss:
                return ((SimpleDateFormat) dFyyyyMMdd_HHmmss.get()).format(date);
            case HHmm:
                return ((SimpleDateFormat) dFHHmm.get()).format(date);
            case HHmmss:
                return ((SimpleDateFormat) dFHHmmss.get()).format(date);
            case SyyyyMMdd:
                return ((SimpleDateFormat) dFSyyyyMMdd.get()).format(date);
            case dFyyyyMMdd:
                return ((SimpleDateFormat) dFyyyyMMdd.get()).format(date);
            default:
                return sdate;
        }
    }

    public static Date String2Date(String formater, String dateString) {
        char c2 = 65535;
        try {
            switch (formater.hashCode()) {
                case -1172057030:
                    if (formater.equals("yyyy-MM-dd HH:mm")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -159776256:
                    if (formater.equals("yyyy-MM-dd")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 1333195168:
                    if (formater.equals("yyyy-MM-dd HH:mm:ss")) {
                        c2 = 0;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    return yyyyMMdd_HHmmssF.parse(dateString);
                case 1:
                    return dFyyyyMMddmmF.parse(dateString);
                case 2:
                    return dFyyyyMMddF.parse(dateString);
                default:
                    return null;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("解析 dateString 异常");
            return null;
        }
    }

    public String getMMddDate() {
        return toFormatString(DateFormater.MMdd);
    }

    public String getMMdd_HHmmDate() {
        return toFormatString(DateFormater.MMdd_HHmm);
    }

    public String getY_M_D() {
        return toFormatString(DateFormater.dFyyyyMMdd);
    }

    public String getY_M_D_H_M_S() {
        return toFormatString(DateFormater.yyyyMMdd_HHmmss);
    }

    public String getY_M_D_H_M() {
        return toFormatString(DateFormater.yyyyMMdd_HHmm);
    }

    public String getYyyyMMDate() {
        return toFormatString(DateFormater.yyyyMM);
    }

    public String getYyyyMMddDate() {
        return toFormatString(DateFormater.yyyyMMdd);
    }

    public String getYyyyMMdd_HHmmDate() {
        return toFormatString(DateFormater.yyyyMMdd_HHmm);
    }

    public String getYyyyMMdd_HHmmssDate() {
        return toFormatString(DateFormater.yyyyMMdd_HHmmss);
    }

    public String getHHmmDate() {
        return toFormatString(DateFormater.HHmm);
    }

    public String getHHmmssDate() {
        return toFormatString(DateFormater.HHmmss);
    }

    public String getSyyyyMMddDate() {
        return toFormatString(DateFormater.SyyyyMMdd);
    }

    public String getyyyyMMddDate() {
        return toFormatString(DateFormater.yyyyMMdd);
    }

    public int getYear() {
        return this.c.get(1);
    }

    public void setYear(int year) {
        this.c.set(1, year);
    }

    public int getMonth() {
        return this.c.get(2) + 1;
    }

    public void setMonth(int month) {
        this.c.set(2, month - 1);
    }

    public int getDay() {
        return this.c.get(5);
    }

    public int getDaysOfThisMonth() {
        return this.c.get(5);
    }

    public void setDay(int day) {
        this.c.set(5, day);
    }

    public void addDay(int day) {
        this.c.add(5, day);
    }

    public void addMonth(int month) {
        this.c.add(2, month);
    }

    public int getHour() {
        return this.c.get(11);
    }

    public void setHour(int hour) {
        this.c.set(11, hour);
    }

    public int getMinute() {
        return this.c.get(12);
    }

    public void setMinute(int minute) {
        this.c.set(12, minute);
    }

    public int getSecond() {
        return this.c.get(13);
    }

    public void setSecond(int second) {
        this.c.set(13, second);
    }

    public long getTimestamp() {
        return this.c.getTimeInMillis();
    }

    public void setTimestamp(long timestamp) {
        this.c.setTimeInMillis(timestamp);
    }

    public long getUnixTimestamp() {
        return this.c.getTimeInMillis() / 1000;
    }

    public void setUnixTimestamp(long unix_timestamp) {
        this.c.setTimeInMillis(1000 * unix_timestamp);
    }

    public int getDayOfWeek() {
        return this.c.get(7);
    }

    public int getWeekOfYear() {
        return this.c.get(3);
    }

    public int getWeekOfMonth() {
        return this.c.get(4);
    }

    public String getMonDate() {
        this.c.add(5, this.c.getFirstDayOfWeek() - getDayOfWeek());
        return new SimpleDateFormat("yyyyMMdd").format(this.c.getTime());
    }

    public String toString() {
        return getYyyyMMdd_HHmmssDate();
    }

    public static String getTime(long time) {
        long dTime = (System.currentTimeMillis() - time) / 86400000;
        DateUtil dateUtil = new DateUtil(time, false);
        if (dTime > 0) {
            return dateUtil.getYyyyMMddDate();
        }
        return dateUtil.getHHmmDate();
    }

    public static long getSunDayTimeFromWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.getTime().getTime() - ((long) (86400000 * (cal.get(7) - 1)));
    }

    public static Date getDateByWeekMagin(int size) {
        return new Date(((long) (86400000 * size)) + getSunDayTimeFromWeek());
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / 86400000);
    }

    public static long dateStr2Stamp(String date) {
        try {
            return Long.parseLong(String.valueOf(new SimpleDateFormat("yyyyMMdd").parse(date).getTime()));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static long dateY_M_D2Stamp(String date) {
        try {
            return Long.parseLong(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static String getMarginMin(long start, long startTime) {
        return ((start - startTime) / 60) + "";
    }

    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(1);
        int monthNow = cal.get(2) + 1;
        int dayOfMonthNow = cal.get(5);
        cal.setTime(birthday);
        int yearBirth = cal.get(1);
        int monthBirth = cal.get(2) + 1;
        int dayOfMonthBirth = cal.get(5);
        int age = yearNow - yearBirth;
        if (monthNow > monthBirth) {
            return age;
        }
        if (monthNow != monthBirth) {
            return age - 1;
        }
        if (dayOfMonthNow < dayOfMonthBirth) {
            return age - 1;
        }
        return age;
    }
}
