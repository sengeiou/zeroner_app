package com.iwown.device_module.device_alarm_schedule.view.Calendar.Model;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.connect.common.Constants;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.cli.HelpFormatter;

public final class Lunar {
    private static final String[] DAY_OF_WEEK_IN_CHINESE = {"日", "一", "二", "三", "四", "五", "六"};
    private static final String[] EARTHLY_BRANCHES = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private static final String[] EVIL_SPIRIT = {"南", "东", "北", "西"};
    private static final String[] FETUS_GOD_DIRECTION = {"外东北", "外正东", "外东南", "外正南", "外西南", "外正西", "外西北", "外正北", "房内北", "房内南", "房内东"};
    private static final String[] FETUS_GOD_EARTHLY = {"碓", "厕", "炉灶", "大门", "栖", "床"};
    private static final String[] FETUS_GOD_HEAVENLY = {"门", "碓磨", "厨灶", "仓库", "房床"};
    private static final String[] FIVE_ELEMENTS = {"海中金", "炉中火", "大林木", "路旁土", "剑锋金", "山头火", "涧下水", "城头土", "白蜡金", "杨柳木", "泉中水", "屋上土", "霹雳火", "松柏木", "长流水", "砂石金", "山下火", "平地木", "壁上土", "金箔金", "灯头火", "天河水", "大驿土", "钗钏金", "桑柘木", "大溪水", "沙中土", "天上火", "石榴木", "大海水"};
    private static final String[] HEAVENLY_STEMS = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private static final String[] LUNAR_SPEC_STRING = {"初", "十", "廿", "卅", "正", "冬", "腊", "闰"};
    private static final String[] LUNAR_STRING = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final int[] LUNR_INFO = {19416, 19168, 42352, 21717, 53856, 55632, 21844, 22191, 39632, 21970, 19168, 42422, 42192, 53840, 53909, 46415, 54944, 44450, 38320, 18807, 18815, 42160, 46261, 27216, 27968, 43860, 11119, 38256, 21234, 18800, 25958, 54432, 59984, 27285, 23263, Constants.REQUEST_OLD_QZSHARE, 34531, 37615, 51415, 51551, 54432, 55462, 46431, 22176, 42420, 9695, 37584, 53938, 43344, 46423, 27808, 46416, 21333, 19887, 42416, 17779, 21183, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46752, 38310, 38335, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 23232, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 20854, 21183, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19195, 19152, 42192, 53430, 53855, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 45653, 27951, 44448, 19299, 37759, 18936, 18800, 25776, 26790, 59999, 27424, 42692, 43759, 37600, 53987, 51552, 54615, 54432, 55888, 23893, 22176, 42704, 21972, 21200, 43448, 43344, 46240, 46758, 44368, 21920, 43940, 42416, 21168, 45683, 26928, 29495, 27296, 44368, 19285, 19311, 42352, 21732, 53856, 59752, 54560, 55968, 27302, 22239, 19168, 43476, 42192, 53584, 62034, 54560};
    private static final String[] PENG_ZU_EARTHLY = {"子不问卜\n自惹祸殃", "丑不冠带\n主不还乡", "寅不祭祀\n神鬼不尝", "卯不穿井\n水泉不香", "辰不哭泣\n必主重丧", "巳不远行\n财物伏藏", "午不苫盖\n屋主更张", "未不服药\n毒气入肠", "申不安床\n鬼祟入房", "酉不宴客\n醉坐颠狂", "戌不吃犬\n作怪上床", "亥不嫁娶\n不利新郎"};
    private static final String[] PENG_ZU_HEAVENLY = {"甲不开仓\n财物耗亡", "乙不栽植\n千株不长", "丙不修灶\n必见灾殃", "丁不剃头\n头主生疮", "戊不受田\n田主不祥", "己不破券\n二比并亡", "庚不经络\n织机虚张", "辛不合酱\n主人不尝", "壬不决水\n更难提防", "癸不词讼\n理弱敌强"};
    private static final String[] SOLAR_TERM = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    private static final int[] SOLAR_TERM_INFO = {0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
    private static final String[] TWELVE_DURY = {"开", "闭", "建", "除", "满", "平", "定", "执", "破", "危", "成", "收"};
    private static final String[] ZODIAC = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private int mCyclicalDay = 0;
    private int mCyclicalMonth = 0;
    private int mCyclicalYear = 0;
    private int mDaysInLuarMonth;
    private boolean mIsLeap;
    private int mLunarDay;
    private final Holiday[] mLunarHolidays = {new Holiday(1, 1, "春节"), new Holiday(1, 15, "元宵节"), new Holiday(5, 5, "端午节"), new Holiday(7, 7, "七夕节"), new Holiday(7, 15, "中元节"), new Holiday(8, 15, "中秋节"), new Holiday(9, 9, "重阳节"), new Holiday(12, 8, "腊八节"), new Holiday(12, 23, "北方小年"), new Holiday(12, 24, "南方小年")};
    private int mLunarMonth;
    private int mLunarYear;
    private Calendar mSolar;
    private int mSolarDay;
    private final Holiday[] mSolarHolidays = {new Holiday(1, 1, "元旦节"), new Holiday(2, 14, "情人节"), new Holiday(3, 8, "妇女节"), new Holiday(3, 12, "植树节"), new Holiday(3, 15, "消费者权益日"), new Holiday(3, 21, "世界森林日"), new Holiday(4, 1, "愚人节"), new Holiday(4, 7, "世界卫生日"), new Holiday(4, 22, "世界地球日"), new Holiday(5, 1, "劳动节"), new Holiday(5, 4, "青年节"), new Holiday(5, 31, "世界无烟日"), new Holiday(6, 1, "儿童节"), new Holiday(6, 26, "禁毒日"), new Holiday(7, 1, "建党节"), new Holiday(8, 1, "建军节"), new Holiday(8, 15, "抗战胜利"), new Holiday(9, 10, "教师节"), new Holiday(9, 28, "孔子诞辰"), new Holiday(10, 1, "国庆节"), new Holiday(12, 20, "澳门回归"), new Holiday(12, 24, "平安夜"), new Holiday(12, 25, "圣诞节")};
    private int mSolarMonth;
    private int mSolarYear;
    private final Star[][] mTwentyEightStars = {new Star[]{new Star("房日兔", "吉", "东方"), new Star("心月狐", "凶", "东方"), new Star("尾火虎", "吉", "东方"), new Star("箕水豹", "吉", "东方"), new Star("角木蛟", "吉", "东方"), new Star("亢金龙", "凶", "东方"), new Star("氐土貉", "凶", "东方")}, new Star[]{new Star("虚日鼠", "凶", "北方"), new Star("危月燕", "凶", "北方"), new Star("室火猪", "吉", "北方"), new Star("壁水貐", "吉", "北方"), new Star("斗木獬", "吉", "北方"), new Star("牛金牛", "凶", "北方"), new Star("女士蝠", "凶", "北方")}, new Star[]{new Star("昴日鸡", "凶", "西方"), new Star("毕月乌", "吉", "西方"), new Star("觜火猴", "凶", "西方"), new Star("参水猿", "凶", "西方"), new Star("奎水狼", "凶", "西方"), new Star("娄金狗", "吉", "西方"), new Star("胃土雉", "吉", "西方")}, new Star[]{new Star("星日马", "凶", "南方"), new Star("张月鹿", "吉", "南方"), new Star("翼火蛇", "凶", "南方"), new Star("轸水蚓", "吉", "南方"), new Star("井木犴", "吉", "南方"), new Star("鬼金羊", "凶", "南方"), new Star("柳土獐", "凶", "南方")}};
    private GregorianCalendar mUTCCalendar;

    private class Holiday {
        private int day;
        private int month;
        private String name;

        public Holiday(int month2, int day2, String name2) {
            this.month = month2;
            this.day = day2;
            this.name = name2;
        }

        public int getDay() {
            return this.day;
        }

        public int getMonth() {
            return this.month;
        }

        public String getName() {
            return this.name;
        }
    }

    private class Star {
        private String direction;
        private String fortune;
        private String star;

        public Star(String star2, String fortune2, String direction2) {
            this.star = star2;
            this.fortune = fortune2;
            this.direction = direction2;
        }

        public String getDirection() {
            return this.direction;
        }

        public String getFortune() {
            return this.fortune;
        }

        public String getStar() {
            return this.star;
        }
    }

    public static synchronized Lunar newInstance() {
        Lunar lunar;
        synchronized (Lunar.class) {
            lunar = new Lunar();
        }
        return lunar;
    }

    public Lunar() {
    }

    public Lunar(int year, int month, int day) {
        setDate(year, month, day);
    }

    public Lunar(long millisec) {
        init(millisec);
    }

    private void init(long millisec) {
        this.mUTCCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        this.mSolar = Calendar.getInstance();
        this.mSolar.setTimeInMillis(millisec);
        this.mLunarYear = 1900;
        long offset = (millisec - new GregorianCalendar(1900, 0, 31).getTimeInMillis()) / 86400000;
        int daysInLunarYear = getLunarYearDays(this.mLunarYear);
        while (this.mLunarYear < 2100 && offset >= ((long) daysInLunarYear)) {
            offset -= (long) daysInLunarYear;
            int i = this.mLunarYear + 1;
            this.mLunarYear = i;
            daysInLunarYear = getLunarYearDays(i);
        }
        int lunarMonth = 1;
        int leapMonth = getLunarLeapMonth(this.mLunarYear);
        boolean leapDec = false;
        boolean isLeap = false;
        int daysInLunarMonth = 0;
        while (lunarMonth < 13 && offset > 0) {
            if (!isLeap || !leapDec) {
                daysInLunarMonth = getLunarMonthDays(this.mLunarYear, lunarMonth);
            } else {
                daysInLunarMonth = getLunarLeapDays(this.mLunarYear);
                leapDec = false;
            }
            if (offset < ((long) daysInLunarMonth)) {
                break;
            }
            offset -= (long) daysInLunarMonth;
            if (leapMonth != lunarMonth || isLeap) {
                lunarMonth++;
            } else {
                leapDec = true;
                isLeap = true;
            }
        }
        this.mDaysInLuarMonth = daysInLunarMonth;
        this.mLunarMonth = lunarMonth;
        this.mIsLeap = lunarMonth == leapMonth && isLeap;
        this.mLunarDay = ((int) offset) + 1;
        getCyclicalData();
    }

    private void initLunar(int lunarYear, int lunarMonth, int lunarDay, boolean isLeap) {
        int initYear = lunarYear;
        int initMonth = lunarMonth;
        long offset = (long) (lunarDay - 1);
        while (true) {
            initMonth--;
            if (initMonth <= 0) {
                break;
            }
            offset += (long) getLunarMonthDays(lunarYear, initMonth);
        }
        if (getLunarLeapMonth(lunarYear) <= lunarMonth) {
            offset += (long) getLunarLeapDays(lunarYear);
        }
        while (initYear > 1900) {
            initYear--;
            offset += (long) getLunarYearDays(initYear);
        }
        this.mLunarYear = lunarYear;
        this.mLunarMonth = lunarMonth;
        this.mLunarDay = lunarDay;
        this.mUTCCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        Calendar baseDate = new GregorianCalendar(1900, 0, 31);
        this.mSolar = Calendar.getInstance();
        this.mSolar.setTimeInMillis((86400000 * offset) + baseDate.getTimeInMillis());
        getCyclicalData();
    }

    private int getLunarLeapMonth(int lunarYear) {
        int leapMonth = LUNR_INFO[lunarYear - 1900] & 15;
        if (leapMonth == 15) {
            return 0;
        }
        return leapMonth;
    }

    private int getLunarLeapDays(int lunarYear) {
        if (getLunarLeapMonth(lunarYear) > 0) {
            return (LUNR_INFO[lunarYear + -1899] & 15) == 15 ? 30 : 29;
        }
        return 0;
    }

    private int getLunarYearDays(int lunarYear) {
        int totalDays = 348;
        for (int i = 32768; i > 8; i >>= 1) {
            totalDays += (LUNR_INFO[lunarYear + -1900] & i) != 0 ? 1 : 0;
        }
        return getLunarLeapDays(lunarYear) + totalDays;
    }

    private int getLunarMonthDays(int lunarYear, int lunarMonth) {
        return (LUNR_INFO[lunarYear + -1900] & (65536 >> lunarMonth)) != 0 ? 30 : 29;
    }

    private synchronized long getUTC(int year, int month, int day, int hour, int min, int sec) {
        long timeInMillis;
        synchronized (Lunar.class) {
            this.mUTCCalendar.clear();
            this.mUTCCalendar.set(year, month, day, hour, min, sec);
            timeInMillis = this.mUTCCalendar.getTimeInMillis();
        }
        return timeInMillis;
    }

    private synchronized int getUTCDay(Date date) {
        int i;
        synchronized (Lunar.class) {
            this.mUTCCalendar.clear();
            this.mUTCCalendar.setTimeInMillis(date.getTime());
            i = this.mUTCCalendar.get(5);
        }
        return i;
    }

    private synchronized int getUTCMonth(Date date) {
        int i;
        synchronized (Lunar.class) {
            this.mUTCCalendar.clear();
            this.mUTCCalendar.setTimeInMillis(date.getTime());
            i = this.mUTCCalendar.get(2);
        }
        return i;
    }

    private int getSolarTermDay(int solarYear, int index) {
        return getUTCDay(new Date(getUTC(1900, 0, 6, 2, 5, 0) + (31556925974L * ((long) (solarYear - 1900))) + (((long) SOLAR_TERM_INFO[index]) * 60000)));
    }

    private int getSolarTermMonth(int solarYear, int index) {
        return getUTCMonth(new Date(getUTC(1900, 0, 6, 2, 5, 0) + (31556925974L * ((long) (solarYear - 1900))) + (((long) SOLAR_TERM_INFO[index]) * 60000)));
    }

    private void getCyclicalData() {
        int cyclicalYear;
        int cyclicalMonth;
        this.mSolarYear = this.mSolar.get(1);
        this.mSolarMonth = this.mSolar.get(2);
        this.mSolarDay = this.mSolar.get(5);
        int term = getSolarTermDay(this.mSolarYear, 2);
        if (this.mSolarMonth < 1 || (this.mSolarMonth == 1 && this.mSolarDay < term)) {
            cyclicalYear = (((this.mSolarYear - 1900) + 36) - 1) % 60;
        } else {
            cyclicalYear = ((this.mSolarYear - 1900) + 36) % 60;
        }
        if (this.mSolarDay < getSolarTermDay(this.mSolarYear, this.mSolarMonth * 2)) {
            cyclicalMonth = ((((this.mSolarYear - 1900) * 12) + this.mSolarMonth) + 12) % 60;
        } else {
            cyclicalMonth = ((((this.mSolarYear - 1900) * 12) + this.mSolarMonth) + 13) % 60;
        }
        int cyclicalDay = ((int) (((getUTC(this.mSolarYear, this.mSolarMonth, this.mSolarDay, 0, 0, 0) / 86400000) + 25567) + 10)) % 60;
        this.mCyclicalYear = cyclicalYear;
        this.mCyclicalMonth = cyclicalMonth;
        this.mCyclicalDay = cyclicalDay;
    }

    private int getHeavenlyStems(int cyclicalNum) {
        return cyclicalNum % 10;
    }

    private int getEarthlyBranches(int cyclicalNum) {
        return cyclicalNum % 12;
    }

    private String getCyclical(int cyclicalNum) {
        return HEAVENLY_STEMS[getHeavenlyStems(cyclicalNum)] + EARTHLY_BRANCHES[getEarthlyBranches(cyclicalNum)];
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        init(year == 0 ? System.currentTimeMillis() : calendar.getTimeInMillis());
    }

    public void setLunarDate(int year, int month, int day, boolean isLeap) {
        initLunar(year, month, day, isLeap);
    }

    public void setTimeInMillis(long millisecond) {
        init(millisecond);
    }

    public String getZodiac() {
        return ZODIAC[(this.mLunarYear - 4) % 12];
    }

    public String getSolarTerm() {
        if (getSolarTermDay(this.mSolarYear, this.mSolarMonth * 2) == this.mSolarDay) {
            return SOLAR_TERM[this.mSolarMonth * 2];
        }
        if (getSolarTermDay(this.mSolarYear, (this.mSolarMonth * 2) + 1) == this.mSolarDay) {
            return SOLAR_TERM[(this.mSolarMonth * 2) + 1];
        }
        return null;
    }

    public int getHeavenlyAndEarthly() {
        return this.mCyclicalDay;
    }

    public String getCyclicalYear() {
        return getCyclical(this.mCyclicalYear);
    }

    public String getCyclicalMonth() {
        return getCyclical(this.mCyclicalMonth);
    }

    public String getCyclicalDay() {
        return getCyclical(this.mCyclicalDay);
    }

    public String getLunarYear() {
        return getCyclical((this.mLunarYear - 1900) + 36);
    }

    public String getLunarMonth(int lunarMonth, boolean isLeap) {
        String lunarMonthStr;
        String str;
        String lunarMonthStr2 = "";
        if (lunarMonth != 1) {
            switch (lunarMonth) {
                case 10:
                    lunarMonthStr = LUNAR_SPEC_STRING[1];
                    break;
                case 11:
                    lunarMonthStr = LUNAR_SPEC_STRING[5];
                    break;
                case 12:
                    lunarMonthStr = LUNAR_SPEC_STRING[6];
                    break;
                default:
                    lunarMonthStr = lunarMonthStr2 + LUNAR_STRING[lunarMonth % 10];
                    break;
            }
        } else {
            lunarMonthStr = LUNAR_SPEC_STRING[4];
        }
        StringBuilder sb = new StringBuilder();
        if (isLeap) {
            str = "闰";
        } else {
            str = "";
        }
        return sb.append(str).append(lunarMonthStr).toString();
    }

    public String getLunarMonth() {
        return getLunarMonth(this.mLunarMonth, this.mIsLeap);
    }

    public String getLunarDay(int lunarDay) {
        if (lunarDay < 1 || lunarDay > 30) {
            return "";
        }
        int unit = lunarDay % 10;
        String decadeStr = LUNAR_SPEC_STRING[lunarDay / 10];
        String unitStr = LUNAR_STRING[unit];
        if (lunarDay < 11) {
            decadeStr = LUNAR_SPEC_STRING[0];
        }
        if (unit == 0) {
            unitStr = LUNAR_SPEC_STRING[1];
        }
        return decadeStr + unitStr;
    }

    public String getLunarDay() {
        return getLunarDay(this.mLunarDay);
    }

    public int getLunarYearNum() {
        return this.mLunarYear;
    }

    public int getLunarMonthNum() {
        return this.mLunarMonth;
    }

    public int getLunarDayNum() {
        return this.mLunarDay;
    }

    public int getMaxDaysInLunarMonth() {
        return this.mDaysInLuarMonth;
    }

    public Calendar getCalendar() {
        return this.mSolar;
    }

    public int getSolarYear() {
        return this.mSolarYear;
    }

    public int getSolarMonth() {
        return this.mSolarMonth;
    }

    public int getSolarDay() {
        return this.mSolarDay;
    }

    public int getDayOfWeek() {
        return this.mSolar.get(7);
    }

    public int getWeekOfYear() {
        return this.mSolar.get(3);
    }

    public String getDayOfWeekInChinese() {
        return DAY_OF_WEEK_IN_CHINESE[getDayOfWeek() - 1];
    }

    public boolean isToday() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(1) == this.mSolarYear && calendar.get(2) == this.mSolarMonth && calendar.get(5) == this.mSolarDay) {
            return true;
        }
        return false;
    }

    public String[] getPengzu() {
        return new String[]{PENG_ZU_HEAVENLY[getHeavenlyStems(this.mCyclicalDay)], PENG_ZU_EARTHLY[getEarthlyBranches(this.mCyclicalDay)]};
    }

    public String getLunarHoliday() {
        Holiday[] holidayArr;
        if (this.mIsLeap) {
            return null;
        }
        if (this.mLunarMonth == 12 && this.mLunarDay == this.mDaysInLuarMonth) {
            return "除夕";
        }
        for (Holiday holiday : this.mLunarHolidays) {
            if (holiday.getMonth() == this.mLunarMonth && holiday.getDay() == this.mLunarDay) {
                return holiday.getName();
            }
        }
        return null;
    }

    public String getSolarHolidy() {
        Holiday[] holidayArr;
        for (Holiday holiday : this.mSolarHolidays) {
            if (holiday.getMonth() == this.mSolarMonth + 1 && holiday.getDay() == this.mSolarDay) {
                return holiday.getName();
            }
        }
        return null;
    }

    public String getConflictEvilSpirit() {
        int conflictHeavenlyIndex;
        int conflictEarthlyIndex;
        int heavenlyIndex = getHeavenlyStems(this.mCyclicalDay);
        int earthlyIndex = getEarthlyBranches(this.mCyclicalDay);
        if (heavenlyIndex < 6) {
            conflictHeavenlyIndex = heavenlyIndex + 4;
        } else {
            conflictHeavenlyIndex = heavenlyIndex - 6;
        }
        if (earthlyIndex < 6) {
            conflictEarthlyIndex = earthlyIndex + 6;
        } else {
            conflictEarthlyIndex = earthlyIndex - 6;
        }
        return "冲" + ZODIAC[conflictEarthlyIndex] + "(" + HEAVENLY_STEMS[conflictHeavenlyIndex] + EARTHLY_BRANCHES[conflictEarthlyIndex] + ")煞" + EVIL_SPIRIT[earthlyIndex % 4];
    }

    public String getTwentyEightStar() {
        int dayOfWeek = getDayOfWeek() - 1;
        Star star = this.mTwentyEightStars[(getWeekOfYear() - 1) % 4][dayOfWeek];
        return star.getDirection() + star.getStar() + HelpFormatter.DEFAULT_OPT_PREFIX + star.getFortune();
    }

    public int getWielding() {
        int earthlyIndexOfMonth = getEarthlyBranches(this.mCyclicalMonth);
        int earthlyIndexOfDay = getEarthlyBranches(this.mCyclicalDay);
        int monthIndex = earthlyIndexOfMonth >= 2 ? earthlyIndexOfMonth - 2 : 12 - earthlyIndexOfMonth;
        if (monthIndex == 0) {
            monthIndex = 12;
        }
        int offset = 12 - monthIndex;
        if (earthlyIndexOfDay + offset < 12) {
            return earthlyIndexOfDay + offset;
        }
        return (earthlyIndexOfDay + offset) - 12;
    }

    public String getFiveElements() {
        return FIVE_ELEMENTS[this.mCyclicalDay / 2] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + TWELVE_DURY[getWielding()] + "执位";
    }

    private int getFetusGodDirectionIndex() {
        if (this.mCyclicalDay < 2 || this.mCyclicalDay > 55) {
            return 2;
        }
        if (this.mCyclicalDay < 6) {
            return 3;
        }
        if (this.mCyclicalDay < 12) {
            return 4;
        }
        if (this.mCyclicalDay < 17) {
            return 5;
        }
        if (this.mCyclicalDay < 23) {
            return 6;
        }
        if (this.mCyclicalDay < 28) {
            return 7;
        }
        if (this.mCyclicalDay < 33) {
            return 8;
        }
        if (this.mCyclicalDay < 39) {
            return 9;
        }
        if (this.mCyclicalDay < 44) {
            return 10;
        }
        if (this.mCyclicalDay < 50) {
            return 0;
        }
        return 1;
    }

    public String getFetusGod() {
        String fetusGod;
        int heavenlyIndex = getHeavenlyStems(this.mCyclicalDay);
        int earthlyIndex = getEarthlyBranches(this.mCyclicalDay);
        String heavenlyPosition = FETUS_GOD_HEAVENLY[heavenlyIndex % 5];
        String earthlyPosition = FETUS_GOD_EARTHLY[earthlyIndex % 5];
        if (heavenlyPosition.contains(earthlyPosition)) {
            fetusGod = heavenlyPosition;
        } else if (earthlyPosition.contains(heavenlyPosition)) {
            fetusGod = earthlyPosition;
        } else {
            fetusGod = heavenlyPosition + earthlyPosition;
        }
        return ((fetusGod.length() <= 2 ? "占" : "") + fetusGod) + FETUS_GOD_DIRECTION[getFetusGodDirectionIndex()];
    }
}
