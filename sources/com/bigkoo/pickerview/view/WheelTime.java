package com.bigkoo.pickerview.view;

import android.view.View;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.lib.WheelView.DividerType;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.lib_common.R;
import com.tencent.connect.common.Constants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public class WheelTime {
    private static final int DEFAULT_END_DAY = 31;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_START_YEAR = 1900;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /* access modifiers changed from: private */
    public int currentYear;
    int dividerColor;
    private DividerType dividerType;
    /* access modifiers changed from: private */
    public int endDay = 31;
    /* access modifiers changed from: private */
    public int endMonth = 12;
    /* access modifiers changed from: private */
    public int endYear = DEFAULT_END_YEAR;
    private int gravity;
    float lineSpacingMultiplier = 1.6f;
    /* access modifiers changed from: private */
    public int startDay = 1;
    /* access modifiers changed from: private */
    public int startMonth = 1;
    /* access modifiers changed from: private */
    public int startYear = DEFAULT_START_YEAR;
    int textColorCenter;
    int textColorOut;
    private int textSize = 18;
    private boolean[] type;
    private View view;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    /* access modifiers changed from: private */
    public WheelView wv_month;
    private WheelView wv_seconds;
    private WheelView wv_year;

    public WheelTime(View view2) {
        this.view = view2;
        this.type = new boolean[]{true, true, true, true, true, true};
        setView(view2);
    }

    public WheelTime(View view2, boolean[] type2, int gravity2, int textSize2) {
        this.view = view2;
        this.type = type2;
        this.gravity = gravity2;
        this.textSize = textSize2;
        setView(view2);
    }

    public void setPicker(int year, int month, int day) {
        setPicker(year, month, day, 0, 0, 0);
    }

    public void setPicker(int year, int month, int day, int h, int m, int s) {
        String[] months_little = {"4", Constants.VIA_SHARE_TYPE_INFO, "9", Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE};
        final List<String> list_big = Arrays.asList(new String[]{"1", "3", "5", "7", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, Constants.VIA_REPORT_TYPE_SET_AVATAR});
        final List<String> list_little = Arrays.asList(months_little);
        this.currentYear = year;
        this.wv_year = (WheelView) this.view.findViewById(R.id.year);
        this.wv_year.setAdapter(new NumericWheelAdapter(this.startYear, this.endYear));
        this.wv_year.setCurrentItem(year - this.startYear);
        this.wv_year.setGravity(this.gravity);
        this.wv_month = (WheelView) this.view.findViewById(R.id.month);
        if (this.startYear == this.endYear) {
            this.wv_month.setAdapter(new NumericWheelAdapter(this.startMonth, this.endMonth));
            this.wv_month.setCurrentItem((month + 1) - this.startMonth);
        } else if (year == this.startYear) {
            this.wv_month.setAdapter(new NumericWheelAdapter(this.startMonth, 12));
            this.wv_month.setCurrentItem((month + 1) - this.startMonth);
        } else if (year == this.endYear) {
            this.wv_month.setAdapter(new NumericWheelAdapter(1, this.endMonth));
            this.wv_month.setCurrentItem(month);
        } else {
            this.wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            this.wv_month.setCurrentItem(month);
        }
        this.wv_month.setGravity(this.gravity);
        this.wv_day = (WheelView) this.view.findViewById(R.id.day);
        if (this.startYear == this.endYear && this.startMonth == this.endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (this.endDay > 31) {
                    this.endDay = 31;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (this.endDay > 30) {
                    this.endDay = 30;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
                if (this.endDay > 28) {
                    this.endDay = 28;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else {
                if (this.endDay > 29) {
                    this.endDay = 29;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            }
            this.wv_day.setCurrentItem(day - this.startDay);
        } else if (year == this.startYear && month + 1 == this.startMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 30));
            } else if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 28));
            } else {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 29));
            }
            this.wv_day.setCurrentItem(day - this.startDay);
        } else if (year == this.endYear && month + 1 == this.endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (this.endDay > 31) {
                    this.endDay = 31;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (this.endDay > 30) {
                    this.endDay = 30;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
                if (this.endDay > 28) {
                    this.endDay = 28;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else {
                if (this.endDay > 29) {
                    this.endDay = 29;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            }
            this.wv_day.setCurrentItem(day - 1);
        } else {
            if (list_big.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 28));
            } else {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            }
            this.wv_day.setCurrentItem(day - 1);
        }
        this.wv_day.setGravity(this.gravity);
        this.wv_hours = (WheelView) this.view.findViewById(R.id.hour);
        this.wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        this.wv_hours.setCurrentItem(h);
        this.wv_hours.setGravity(this.gravity);
        this.wv_mins = (WheelView) this.view.findViewById(R.id.min);
        this.wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_mins.setCurrentItem(m);
        this.wv_mins.setGravity(this.gravity);
        this.wv_seconds = (WheelView) this.view.findViewById(R.id.second);
        this.wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_seconds.setCurrentItem(s);
        this.wv_seconds.setGravity(this.gravity);
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                int year_num = index + WheelTime.this.startYear;
                WheelTime.this.currentYear = year_num;
                int currentMonthItem = WheelTime.this.wv_month.getCurrentItem();
                if (WheelTime.this.startYear == WheelTime.this.endYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(WheelTime.this.startMonth, WheelTime.this.endMonth));
                    if (currentMonthItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + WheelTime.this.startMonth;
                    if (WheelTime.this.startMonth == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(year_num, monthNum, WheelTime.this.startDay, WheelTime.this.endDay, list_big, list_little);
                    } else if (monthNum == WheelTime.this.startMonth) {
                        WheelTime.this.setReDay(year_num, monthNum, WheelTime.this.startDay, 31, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == WheelTime.this.startYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(WheelTime.this.startMonth, 12));
                    if (currentMonthItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentMonthItem);
                    }
                    int month = currentMonthItem + WheelTime.this.startMonth;
                    if (month == WheelTime.this.startMonth) {
                        WheelTime.this.setReDay(year_num, month, WheelTime.this.startDay, 31, list_big, list_little);
                        return;
                    }
                    WheelTime.this.setReDay(year_num, month, 1, 31, list_big, list_little);
                } else if (year_num == WheelTime.this.endYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(1, WheelTime.this.endMonth));
                    if (currentMonthItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum2 = currentMonthItem + 1;
                    if (monthNum2 == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(year_num, monthNum2, 1, WheelTime.this.endDay, list_big, list_little);
                        return;
                    }
                    WheelTime.this.setReDay(year_num, monthNum2, 1, 31, list_big, list_little);
                } else {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                    WheelTime.this.setReDay(year_num, WheelTime.this.wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                }
            }
        };
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                int month_num = index + 1;
                if (WheelTime.this.startYear == WheelTime.this.endYear) {
                    int month_num2 = (WheelTime.this.startMonth + month_num) - 1;
                    if (WheelTime.this.startMonth == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, WheelTime.this.startDay, WheelTime.this.endDay, list_big, list_little);
                    } else if (WheelTime.this.startMonth == month_num2) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, WheelTime.this.startDay, 31, list_big, list_little);
                    } else if (WheelTime.this.endMonth == month_num2) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, 1, WheelTime.this.endDay, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, 1, 31, list_big, list_little);
                    }
                } else if (WheelTime.this.currentYear == WheelTime.this.startYear) {
                    int month_num3 = (WheelTime.this.startMonth + month_num) - 1;
                    if (month_num3 == WheelTime.this.startMonth) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num3, WheelTime.this.startDay, 31, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num3, 1, 31, list_big, list_little);
                    }
                } else if (WheelTime.this.currentYear != WheelTime.this.endYear) {
                    WheelTime.this.setReDay(WheelTime.this.currentYear, month_num, 1, 31, list_big, list_little);
                } else if (month_num == WheelTime.this.endMonth) {
                    WheelTime.this.setReDay(WheelTime.this.currentYear, WheelTime.this.wv_month.getCurrentItem() + 1, 1, WheelTime.this.endDay, list_big, list_little);
                } else {
                    WheelTime.this.setReDay(WheelTime.this.currentYear, WheelTime.this.wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                }
            }
        };
        this.wv_year.setOnItemSelectedListener(wheelListener_year);
        this.wv_month.setOnItemSelectedListener(wheelListener_month);
        if (this.type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        this.wv_year.setVisibility(this.type[0] ? 0 : 8);
        this.wv_month.setVisibility(this.type[1] ? 0 : 8);
        this.wv_day.setVisibility(this.type[2] ? 0 : 8);
        this.wv_hours.setVisibility(this.type[3] ? 0 : 8);
        this.wv_mins.setVisibility(this.type[4] ? 0 : 8);
        this.wv_seconds.setVisibility(this.type[5] ? 0 : 8);
        setContentTextSize();
    }

    /* access modifiers changed from: private */
    public void setReDay(int year_num, int monthNum, int startD, int endD, List<String> list_big, List<String> list_little) {
        int currentItem = this.wv_day.getCurrentItem();
        if (list_big.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
            int i = endD;
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
            int i2 = endD;
        } else if ((year_num % 4 != 0 || year_num % 100 == 0) && year_num % 400 != 0) {
            if (endD > 28) {
                endD = 28;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
            int i3 = endD;
        } else {
            if (endD > 29) {
                endD = 29;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
            int i4 = endD;
        }
        if (currentItem > this.wv_day.getAdapter().getItemsCount() - 1) {
            this.wv_day.setCurrentItem(this.wv_day.getAdapter().getItemsCount() - 1);
        }
    }

    private void setContentTextSize() {
        this.wv_day.setTextSize((float) this.textSize);
        this.wv_month.setTextSize((float) this.textSize);
        this.wv_year.setTextSize((float) this.textSize);
        this.wv_hours.setTextSize((float) this.textSize);
        this.wv_mins.setTextSize((float) this.textSize);
        this.wv_seconds.setTextSize((float) this.textSize);
    }

    private void setTextColorOut() {
        this.wv_day.setTextColorOut(this.textColorOut);
        this.wv_month.setTextColorOut(this.textColorOut);
        this.wv_year.setTextColorOut(this.textColorOut);
        this.wv_hours.setTextColorOut(this.textColorOut);
        this.wv_mins.setTextColorOut(this.textColorOut);
        this.wv_seconds.setTextColorOut(this.textColorOut);
    }

    private void setTextColorCenter() {
        this.wv_day.setTextColorCenter(this.textColorCenter);
        this.wv_month.setTextColorCenter(this.textColorCenter);
        this.wv_year.setTextColorCenter(this.textColorCenter);
        this.wv_hours.setTextColorCenter(this.textColorCenter);
        this.wv_mins.setTextColorCenter(this.textColorCenter);
        this.wv_seconds.setTextColorCenter(this.textColorCenter);
    }

    private void setDividerColor() {
        this.wv_day.setDividerColor(this.dividerColor);
        this.wv_month.setDividerColor(this.dividerColor);
        this.wv_year.setDividerColor(this.dividerColor);
        this.wv_hours.setDividerColor(this.dividerColor);
        this.wv_mins.setDividerColor(this.dividerColor);
        this.wv_seconds.setDividerColor(this.dividerColor);
    }

    private void setDividerType() {
        this.wv_day.setDividerType(this.dividerType);
        this.wv_month.setDividerType(this.dividerType);
        this.wv_year.setDividerType(this.dividerType);
        this.wv_hours.setDividerType(this.dividerType);
        this.wv_mins.setDividerType(this.dividerType);
        this.wv_seconds.setDividerType(this.dividerType);
    }

    private void setLineSpacingMultiplier() {
        this.wv_day.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_month.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_year.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_hours.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_mins.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_seconds.setLineSpacingMultiplier(this.lineSpacingMultiplier);
    }

    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        if (label_year != null) {
            this.wv_year.setLabel(label_year);
        } else {
            this.wv_year.setLabel(this.view.getContext().getString(R.string.pickerview_year));
        }
        if (label_month != null) {
            this.wv_month.setLabel(label_month);
        } else {
            this.wv_month.setLabel(this.view.getContext().getString(R.string.pickerview_month));
        }
        if (label_day != null) {
            this.wv_day.setLabel(label_day);
        } else {
            this.wv_day.setLabel(this.view.getContext().getString(R.string.pickerview_day));
        }
        if (label_hours != null) {
            this.wv_hours.setLabel(label_hours);
        } else {
            this.wv_hours.setLabel(this.view.getContext().getString(R.string.pickerview_hours));
        }
        if (label_mins != null) {
            this.wv_mins.setLabel(label_mins);
        } else {
            this.wv_mins.setLabel(this.view.getContext().getString(R.string.pickerview_minutes));
        }
        if (label_seconds != null) {
            this.wv_seconds.setLabel(label_seconds);
        } else {
            this.wv_seconds.setLabel(this.view.getContext().getString(R.string.pickerview_seconds));
        }
    }

    public void setCyclic(boolean cyclic) {
        this.wv_year.setCyclic(cyclic);
        this.wv_month.setCyclic(cyclic);
        this.wv_day.setCyclic(cyclic);
        this.wv_hours.setCyclic(cyclic);
        this.wv_mins.setCyclic(cyclic);
        this.wv_seconds.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        if (this.currentYear != this.startYear) {
            sb.append(this.wv_year.getCurrentItem() + this.startYear).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(this.wv_month.getCurrentItem() + 1).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(this.wv_day.getCurrentItem() + 1).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_mins.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
        } else if (this.wv_month.getCurrentItem() + this.startMonth == this.startMonth) {
            sb.append(this.wv_year.getCurrentItem() + this.startYear).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(this.wv_month.getCurrentItem() + this.startMonth).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(this.wv_day.getCurrentItem() + this.startDay).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_mins.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
        } else {
            sb.append(this.wv_year.getCurrentItem() + this.startYear).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(this.wv_month.getCurrentItem() + this.startMonth).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(this.wv_day.getCurrentItem() + 1).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_mins.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
        }
        return sb.toString();
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view2) {
        this.view = view2;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public void setStartYear(int startYear2) {
        this.startYear = startYear2;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public void setEndYear(int endYear2) {
        this.endYear = endYear2;
    }

    public void setRangDate(Calendar startDate, Calendar endDate) {
        if (startDate == null && endDate != null) {
            int year = endDate.get(1);
            int month = endDate.get(2) + 1;
            int day = endDate.get(5);
            if (year > this.startYear) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (year != this.startYear) {
            } else {
                if (month > this.startMonth) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                } else if (month == this.startMonth && day > this.startDay) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                }
            }
        } else if (startDate != null && endDate == null) {
            int year2 = startDate.get(1);
            int month2 = startDate.get(2) + 1;
            int day2 = startDate.get(5);
            if (year2 < this.endYear) {
                this.startMonth = month2;
                this.startDay = day2;
                this.startYear = year2;
            } else if (year2 != this.endYear) {
            } else {
                if (month2 < this.endMonth) {
                    this.startMonth = month2;
                    this.startDay = day2;
                    this.startYear = year2;
                } else if (month2 == this.endMonth && day2 < this.endDay) {
                    this.startMonth = month2;
                    this.startDay = day2;
                    this.startYear = year2;
                }
            }
        } else if (startDate != null && endDate != null) {
            this.startYear = startDate.get(1);
            this.endYear = endDate.get(1);
            this.startMonth = startDate.get(2) + 1;
            this.endMonth = endDate.get(2) + 1;
            this.startDay = startDate.get(5);
            this.endDay = endDate.get(5);
        }
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier2) {
        this.lineSpacingMultiplier = lineSpacingMultiplier2;
        setLineSpacingMultiplier();
    }

    public void setDividerColor(int dividerColor2) {
        this.dividerColor = dividerColor2;
        setDividerColor();
    }

    public void setDividerType(DividerType dividerType2) {
        this.dividerType = dividerType2;
        setDividerType();
    }

    public void setTextColorCenter(int textColorCenter2) {
        this.textColorCenter = textColorCenter2;
        setTextColorCenter();
    }

    public void setTextColorOut(int textColorOut2) {
        this.textColorOut = textColorOut2;
        setTextColorOut();
    }

    public void isCenterLabel(Boolean isCenterLabel) {
        this.wv_day.isCenterLabel(isCenterLabel);
        this.wv_month.isCenterLabel(isCenterLabel);
        this.wv_year.isCenterLabel(isCenterLabel);
        this.wv_hours.isCenterLabel(isCenterLabel);
        this.wv_mins.isCenterLabel(isCenterLabel);
        this.wv_seconds.isCenterLabel(isCenterLabel);
    }
}
