package com.iwown.sport_module.view.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HistoryCalendar extends View {
    private int ALL_ROW_NUM = 6;
    private int PER_WEEK_NUM = 7;
    private int SPANCE_DAYANDLUNAR = 1;
    private int SPANCE_HASSCHEDULE = 8;
    private String TAG = "CalendarMonthView";
    private String UID;
    private int clientHeight;
    private int curMonth;
    CurMonthDayData curMonthDayData = new CurMonthDayData();
    private int curYear;
    private int dayCellSize;
    private boolean isShowLunar = true;
    private Map<Integer, Integer> level_circle_colors;
    private int level_circle_r = 3;
    private int level_circle_r_margin = 2;
    private Paint mCircleLevePaint;
    private int mClientBackgroundColorDeep;
    private int mClientBackgroundColorLight;
    private Paint mClientDayBackgroundPaint;
    private Context mContext;
    private Paint mDayCellPaint;
    private float mDensity;
    private int mDisableDayCellColor;
    private Calendar mDisplayCalendar;
    private int mEnableDayCellColor;
    private int mSelectedCircleColor;
    private Paint mSelectedCirclePaint;
    private int mSelectedDayCellColor;
    private Paint mWeekBackPaint;
    private Paint mWeekTitlePaint;
    private int myDay;
    private int myMonth;
    private int myYear;
    private OnCalendarDayClick onCalendarDayClick;
    private int paddingLeft;
    private int paddingTop;
    private int perColWeight;
    private int perRowHeight;
    private int selectedDay;
    private int selectedDayCellRadius;
    private boolean showLeveTag;
    private Map<String, ShowLeveTag> showLeveTagList = new HashMap();
    private Paint todayCirclePaint;
    private int todayDay;
    private int todayMonth;
    private int todayYear;
    private int viewHeight;
    private int viewWidth;
    private int weekTitleColor;
    private int weekTitleHeight;
    private int weekTitleSize;

    private class CurMonthDayData {
        public int endX;
        public int endY;
        public int startX;
        public int startY;

        private CurMonthDayData() {
            this.startY = 0;
        }
    }

    public static class ShowLeveTag {
        public int color;
        public long unix_time;

        public ShowLeveTag() {
        }

        public ShowLeveTag(int color2, long unix_time2) {
            this.color = color2;
            this.unix_time = unix_time2;
        }
    }

    public void setShowLeveTag(boolean showLeveTag2) {
        this.showLeveTag = showLeveTag2;
    }

    public void updateLevelTags(Map<String, ShowLeveTag> showLeveTagList2) {
        this.showLeveTagList = showLeveTagList2;
        invalidate();
    }

    public void putData2LeveTagList(String key, int color, long unix_time) {
        this.showLeveTagList.put(key, new ShowLeveTag(color, unix_time));
    }

    public HistoryCalendar(Context context) {
        super(context);
    }

    public HistoryCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HistoryCalendar(Context context, Calendar calendar, int selectedDay2) {
        super(context);
    }

    public HistoryCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        if (this.mContext.getString(R.string.sport_module_calendar_is_show_lunar).equals("农历")) {
            this.isShowLunar = true;
        } else {
            this.isShowLunar = false;
        }
        this.mDensity = new DisplayMetrics().density;
        Calendar todayCalendar = Calendar.getInstance();
        this.todayYear = todayCalendar.get(1);
        this.todayMonth = todayCalendar.get(2) + 1;
        this.todayDay = todayCalendar.get(5);
        this.myYear = this.todayYear;
        this.myMonth = this.todayMonth;
        this.myDay = this.todayDay;
        this.mDisplayCalendar = (Calendar) todayCalendar.clone();
        this.selectedDay = this.todayDay;
        this.curYear = this.todayYear;
        this.curMonth = this.todayMonth;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.sport_module_CalendarMonthView, 0, 0);
        Resources resources = context.getResources();
        this.weekTitleHeight = typedArray.getDimensionPixelOffset(R.styleable.sport_module_CalendarMonthView_sport_module_weekTitleHeight, resources.getDimensionPixelOffset(R.dimen.sport_module_dip_size_30));
        this.selectedDayCellRadius = typedArray.getDimensionPixelSize(R.styleable.sport_module_CalendarMonthView_sport_module_selectedDayCellRadius, resources.getDimensionPixelOffset(R.dimen.sport_module_dip_size_15));
        this.weekTitleSize = typedArray.getDimensionPixelSize(R.styleable.sport_module_CalendarMonthView_sport_module_textSizeWeekTitle, resources.getDimensionPixelSize(R.dimen.sport_module_dip_size_14));
        this.dayCellSize = typedArray.getDimensionPixelSize(R.styleable.sport_module_CalendarMonthView_sport_module_textSizeDayCell, resources.getDimensionPixelSize(R.dimen.sport_module_dip_size_16));
        this.weekTitleColor = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorWeekTitle, resources.getColor(R.color.sport_module_4A4B4D));
        this.mClientBackgroundColorDeep = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorClientBackgroundDeep, resources.getColor(R.color.sport_module_calendar_clientbackground_deep));
        this.mClientBackgroundColorLight = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorClientBackgroundLight, resources.getColor(R.color.sport_module_calendar_clientbackground_light));
        this.mDisableDayCellColor = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorDisableDayCell, resources.getColor(R.color.sport_module_DDDDDD));
        this.mEnableDayCellColor = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorEnableDayCell, resources.getColor(R.color.sport_module_4A4B4D));
        this.mSelectedDayCellColor = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorSelectedDayCell, resources.getColor(R.color.sport_module_calendar_selected_daycell));
        this.mSelectedCircleColor = typedArray.getColor(R.styleable.sport_module_CalendarMonthView_sport_module_colorSelectedCircleBackground, resources.getColor(R.color.sport_module_0475E4));
        initPaint();
        this.paddingLeft = DensityUtil.dip2px(getContext(), 5.0f);
        this.paddingTop = DensityUtil.dip2px(getContext(), 4.0f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.viewHeight = getMeasuredHeight() - (this.paddingTop * 2);
        this.clientHeight = this.viewHeight - this.weekTitleHeight;
        this.perRowHeight = this.clientHeight / this.ALL_ROW_NUM;
        this.viewWidth = getMeasuredWidth() - (this.paddingLeft * 2);
        this.perColWeight = this.viewWidth / this.PER_WEEK_NUM;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawWeekTitle(canvas);
        drawClientDay(canvas);
    }

    private void initPaint() {
        this.mWeekTitlePaint = new Paint();
        this.mWeekTitlePaint.setAntiAlias(true);
        this.mWeekTitlePaint.setTextSize((float) this.weekTitleSize);
        this.mWeekTitlePaint.setColor(this.weekTitleColor);
        this.mWeekTitlePaint.setStyle(Style.FILL);
        this.mWeekTitlePaint.setTextAlign(Align.CENTER);
        this.mWeekBackPaint = new Paint();
        this.mWeekBackPaint.setColor(-16248796);
        this.mWeekBackPaint.setAntiAlias(true);
        this.mWeekBackPaint.setTextAlign(Align.CENTER);
        this.mWeekBackPaint.setStyle(Style.FILL);
        this.mClientDayBackgroundPaint = new Paint();
        this.mClientDayBackgroundPaint.setFakeBoldText(true);
        this.mClientDayBackgroundPaint.setAntiAlias(true);
        this.mClientDayBackgroundPaint.setTextAlign(Align.CENTER);
        this.mClientDayBackgroundPaint.setStyle(Style.FILL);
        this.mDayCellPaint = new Paint();
        this.mDayCellPaint.setAntiAlias(true);
        this.mDayCellPaint.setTextSize((float) this.dayCellSize);
        this.mDayCellPaint.setStyle(Style.FILL);
        this.mDayCellPaint.setTextAlign(Align.CENTER);
        this.mDayCellPaint.setFakeBoldText(false);
        this.mSelectedCirclePaint = new Paint();
        this.mSelectedCirclePaint.setFakeBoldText(true);
        this.mSelectedCirclePaint.setAntiAlias(true);
        this.mSelectedCirclePaint.setColor(this.mSelectedCircleColor);
        this.mSelectedCirclePaint.setTextAlign(Align.CENTER);
        this.mSelectedCirclePaint.setStyle(Style.FILL);
        this.level_circle_r = DensityUtil.dip2px(getContext(), 2.0f);
        this.level_circle_r_margin = DensityUtil.dip2px(getContext(), 3.0f);
        this.mCircleLevePaint = new Paint();
        this.mCircleLevePaint.setAntiAlias(true);
        this.mCircleLevePaint.setColor(this.mSelectedCircleColor);
        this.mCircleLevePaint.setStyle(Style.FILL);
        this.todayCirclePaint = new Paint();
        this.todayCirclePaint.setAntiAlias(true);
        this.todayCirclePaint.setColor(this.mSelectedCircleColor);
        this.todayCirclePaint.setStrokeWidth((float) DensityUtil.dip2px(getContext(), 1.0f));
        this.todayCirclePaint.setStyle(Style.STROKE);
    }

    private void drawWeekTitle(Canvas canvas) {
        String[] strWeek = this.mContext.getResources().getStringArray(R.array.sport_module_CalendarView_WeekLable);
        int yOffset = this.weekTitleHeight - (this.weekTitleSize / 2);
        int xOffsetHalf = this.viewWidth / (this.PER_WEEK_NUM * 2);
        canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) this.weekTitleHeight, this.mWeekBackPaint);
        for (int i = 0; i < this.PER_WEEK_NUM; i++) {
            canvas.drawText(strWeek[i], (float) (this.paddingLeft + (((i * 2) + 1) * xOffsetHalf)), (float) yOffset, this.mWeekTitlePaint);
        }
    }

    private void drawClientDay(Canvas canvas) {
        drawClientDayCells(canvas);
    }

    private int getCurDayStartIdxOfWeek(int week) {
        return week - 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x014a, code lost:
        r23 = r28.curYear;
        r24 = r28.todayYear;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawClientDayCells(android.graphics.Canvas r29) {
        /*
            r28 = this;
            r0 = r28
            java.util.Calendar r0 = r0.mDisplayCalendar
            r23 = r0
            java.lang.Object r12 = r23.clone()
            java.util.Calendar r12 = (java.util.Calendar) r12
            r23 = 5
            r24 = 1
            r0 = r23
            r1 = r24
            r12.set(r0, r1)
            r23 = 7
            r0 = r23
            int r23 = r12.get(r0)
            r0 = r28
            r1 = r23
            int r7 = r0.getCurDayStartIdxOfWeek(r1)
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            r0 = r23
            r0.startX = r7
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            r24 = 0
            r0 = r24
            r1 = r23
            r1.startY = r0
            r0 = r28
            int r0 = r0.weekTitleHeight
            r23 = r0
            r0 = r28
            int r0 = r0.perRowHeight
            r24 = r0
            r0 = r24
            double r0 = (double) r0
            r24 = r0
            r26 = 4613937818241073152(0x4008000000000000, double:3.0)
            double r24 = r24 / r26
            r26 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r24 = r24 * r26
            r0 = r24
            int r0 = (int) r0
            r24 = r0
            int r22 = r23 + r24
            r0 = r28
            int r0 = r0.viewWidth
            r23 = r0
            r0 = r28
            int r0 = r0.PER_WEEK_NUM
            r24 = r0
            int r24 = r24 * 2
            int r23 = r23 / r24
            r0 = r28
            int r0 = r0.paddingLeft
            r24 = r0
            int r20 = r23 + r24
            r21 = 0
            if (r7 <= 0) goto L_0x00f4
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mDisableDayCellColor
            r24 = r0
            r23.setColor(r24)
            r0 = r28
            java.util.Calendar r0 = r0.mDisplayCalendar
            r23 = r0
            java.lang.Object r17 = r23.clone()
            java.util.Calendar r17 = (java.util.Calendar) r17
            r23 = 2
            r24 = -1
            r0 = r17
            r1 = r23
            r2 = r24
            r0.add(r1, r2)
            r23 = 5
            r0 = r17
            r1 = r23
            int r13 = r0.getActualMaximum(r1)
            int r23 = r13 - r7
            int r19 = r23 + 1
            r14 = r19
        L_0x00b3:
            if (r14 > r13) goto L_0x00f4
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r14)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
            r0 = r28
            int r0 = r0.perColWeight
            r23 = r0
            int r20 = r20 + r23
            int r14 = r14 + 1
            goto L_0x00b3
        L_0x00f4:
            r23 = 1
            r0 = r23
            int r23 = r12.get(r0)
            r0 = r23
            r1 = r28
            r1.curYear = r0
            r23 = 2
            r0 = r23
            int r23 = r12.get(r0)
            int r23 = r23 + 1
            r0 = r23
            r1 = r28
            r1.curMonth = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mEnableDayCellColor
            r24 = r0
            r23.setColor(r24)
            r8 = 1
            r23 = 5
            r0 = r23
            int r6 = r12.getActualMaximum(r0)
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            r24 = 0
            r0 = r24
            r1 = r23
            r1.endY = r0
            r0 = r28
            int r0 = r0.curMonth
            r23 = r0
            r0 = r28
            int r0 = r0.todayMonth
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x023b
            r0 = r28
            int r0 = r0.curYear
            r23 = r0
            r0 = r28
            int r0 = r0.todayYear
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x023b
            r15 = 1
        L_0x015d:
            r11 = r8
        L_0x015e:
            if (r11 > r6) goto L_0x0487
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mEnableDayCellColor
            r24 = r0
            r23.setColor(r24)
            r0 = r28
            int r0 = r0.selectedDay
            r23 = r0
            r0 = r23
            if (r0 != r11) goto L_0x02fb
            if (r15 == 0) goto L_0x023e
            r0 = r28
            int r0 = r0.todayDay
            r23 = r0
            r0 = r23
            if (r11 <= r0) goto L_0x023e
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mDisableDayCellColor
            r24 = r0
            r23.setColor(r24)
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r11)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
        L_0x01c8:
            r0 = r28
            boolean r0 = r0.showLeveTag
            r23 = r0
            if (r23 == 0) goto L_0x0208
            r0 = r28
            java.util.Map<java.lang.String, com.iwown.sport_module.view.calendar.HistoryCalendar$ShowLeveTag> r0 = r0.showLeveTagList
            r23 = r0
            if (r23 == 0) goto L_0x0208
            com.iwown.lib_common.date.DateUtil r9 = new com.iwown.lib_common.date.DateUtil
            r0 = r28
            int r0 = r0.curYear
            r23 = r0
            r0 = r28
            int r0 = r0.curMonth
            r24 = r0
            r0 = r23
            r1 = r24
            r9.<init>(r0, r1, r11)
            com.iwown.lib_common.date.DateUtil r10 = new com.iwown.lib_common.date.DateUtil
            long r24 = java.lang.System.currentTimeMillis()
            r23 = 0
            r0 = r24
            r2 = r23
            r10.<init>(r0, r2)
            long r24 = r9.getTimestamp()
            long r26 = r10.getTimestamp()
            int r23 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
            if (r23 <= 0) goto L_0x0418
        L_0x0208:
            int r7 = r7 + 1
            r0 = r28
            int r0 = r0.PER_WEEK_NUM
            r23 = r0
            r0 = r23
            if (r7 != r0) goto L_0x047d
            r7 = 0
            r0 = r28
            int r0 = r0.viewWidth
            r23 = r0
            r0 = r28
            int r0 = r0.PER_WEEK_NUM
            r24 = r0
            int r24 = r24 * 2
            int r23 = r23 / r24
            r0 = r28
            int r0 = r0.paddingLeft
            r24 = r0
            int r20 = r23 + r24
            int r21 = r21 + 1
            r0 = r28
            int r0 = r0.perRowHeight
            r23 = r0
            int r22 = r22 + r23
        L_0x0237:
            int r11 = r11 + 1
            goto L_0x015e
        L_0x023b:
            r15 = 0
            goto L_0x015d
        L_0x023e:
            r0 = r28
            int r0 = r0.curYear
            r23 = r0
            r0 = r28
            int r0 = r0.myYear
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x02eb
            r0 = r28
            int r0 = r0.curMonth
            r23 = r0
            r0 = r28
            int r0 = r0.myMonth
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x02eb
            r0 = r28
            int r0 = r0.selectedDay
            r23 = r0
            r0 = r28
            int r0 = r0.myDay
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x02eb
            r0 = r20
            float r0 = (float) r0
            r23 = r0
            r0 = r28
            int r0 = r0.dayCellSize
            r24 = r0
            int r24 = r24 / 3
            int r24 = r22 - r24
            r0 = r24
            float r0 = (float) r0
            r24 = r0
            r0 = r28
            int r0 = r0.selectedDayCellRadius
            r25 = r0
            r0 = r25
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mSelectedCirclePaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawCircle(r1, r2, r3, r4)
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mSelectedDayCellColor
            r24 = r0
            r23.setColor(r24)
        L_0x02b5:
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r11)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
            goto L_0x01c8
        L_0x02eb:
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mEnableDayCellColor
            r24 = r0
            r23.setColor(r24)
            goto L_0x02b5
        L_0x02fb:
            r0 = r28
            int r0 = r0.todayDay
            r23 = r0
            r0 = r23
            if (r11 != r0) goto L_0x0391
            r0 = r28
            int r0 = r0.curMonth
            r23 = r0
            r0 = r28
            int r0 = r0.todayMonth
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0391
            r0 = r28
            int r0 = r0.curYear
            r23 = r0
            r0 = r28
            int r0 = r0.todayYear
            r24 = r0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0391
            r0 = r20
            float r0 = (float) r0
            r23 = r0
            r0 = r28
            int r0 = r0.dayCellSize
            r24 = r0
            int r24 = r24 / 3
            int r24 = r22 - r24
            r0 = r24
            float r0 = (float) r0
            r24 = r0
            r0 = r28
            int r0 = r0.selectedDayCellRadius
            r25 = r0
            r0 = r25
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.todayCirclePaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawCircle(r1, r2, r3, r4)
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r11)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
            goto L_0x01c8
        L_0x0391:
            if (r15 == 0) goto L_0x03e2
            r0 = r28
            int r0 = r0.todayDay
            r23 = r0
            r0 = r23
            if (r11 <= r0) goto L_0x03e2
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mDisableDayCellColor
            r24 = r0
            r23.setColor(r24)
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r11)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
            goto L_0x01c8
        L_0x03e2:
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r11)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
            goto L_0x01c8
        L_0x0418:
            r0 = r28
            java.util.Map<java.lang.String, com.iwown.sport_module.view.calendar.HistoryCalendar$ShowLeveTag> r0 = r0.showLeveTagList
            r23 = r0
            java.lang.String r24 = r9.getY_M_D()
            java.lang.Object r18 = r23.get(r24)
            com.iwown.sport_module.view.calendar.HistoryCalendar$ShowLeveTag r18 = (com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag) r18
            if (r18 == 0) goto L_0x0208
            r0 = r28
            android.graphics.Paint r0 = r0.mCircleLevePaint
            r23 = r0
            r0 = r18
            int r0 = r0.color
            r24 = r0
            r23.setColor(r24)
            r0 = r20
            float r0 = (float) r0
            r23 = r0
            r0 = r28
            int r0 = r0.dayCellSize
            r24 = r0
            int r24 = r24 / 3
            int r24 = r22 - r24
            r0 = r28
            int r0 = r0.selectedDayCellRadius
            r25 = r0
            int r24 = r24 + r25
            r0 = r28
            int r0 = r0.level_circle_r_margin
            r25 = r0
            int r24 = r24 + r25
            r0 = r24
            float r0 = (float) r0
            r24 = r0
            r0 = r28
            int r0 = r0.level_circle_r
            r25 = r0
            r0 = r25
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mCircleLevePaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawCircle(r1, r2, r3, r4)
            goto L_0x0208
        L_0x047d:
            r0 = r28
            int r0 = r0.perColWeight
            r23 = r0
            int r20 = r20 + r23
            goto L_0x0237
        L_0x0487:
            if (r7 != 0) goto L_0x0540
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            int r24 = r21 + -1
            r0 = r24
            r1 = r23
            r1.endY = r0
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            r0 = r28
            int r0 = r0.PER_WEEK_NUM
            r24 = r0
            int r24 = r24 + -1
            r0 = r24
            r1 = r23
            r1.endX = r0
        L_0x04ab:
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r23 = r0
            r0 = r28
            int r0 = r0.mDisableDayCellColor
            r24 = r0
            r23.setColor(r24)
            java.lang.Object r16 = r12.clone()
            java.util.Calendar r16 = (java.util.Calendar) r16
            r23 = 2
            r24 = 1
            r0 = r16
            r1 = r23
            r2 = r24
            r0.add(r1, r2)
            r11 = 1
        L_0x04ce:
            r0 = r28
            int r0 = r0.ALL_ROW_NUM
            r23 = r0
            r0 = r21
            r1 = r23
            if (r0 >= r1) goto L_0x0562
            java.lang.String r23 = "%d"
            r24 = 1
            r0 = r24
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r24 = r0
            r25 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r11)
            r24[r25] = r26
            java.lang.String r23 = java.lang.String.format(r23, r24)
            r0 = r20
            float r0 = (float) r0
            r24 = r0
            r0 = r22
            float r0 = (float) r0
            r25 = r0
            r0 = r28
            android.graphics.Paint r0 = r0.mDayCellPaint
            r26 = r0
            r0 = r29
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r0.drawText(r1, r2, r3, r4)
            int r11 = r11 + 1
            int r7 = r7 + 1
            r0 = r28
            int r0 = r0.PER_WEEK_NUM
            r23 = r0
            r0 = r23
            if (r7 != r0) goto L_0x0558
            r7 = 0
            r0 = r28
            int r0 = r0.viewWidth
            r23 = r0
            r0 = r28
            int r0 = r0.PER_WEEK_NUM
            r24 = r0
            int r24 = r24 * 2
            int r23 = r23 / r24
            r0 = r28
            int r0 = r0.paddingLeft
            r24 = r0
            int r20 = r23 + r24
            int r21 = r21 + 1
            r0 = r28
            int r0 = r0.perRowHeight
            r23 = r0
            int r22 = r22 + r23
            goto L_0x04ce
        L_0x0540:
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            r0 = r21
            r1 = r23
            r1.endY = r0
            r0 = r28
            com.iwown.sport_module.view.calendar.HistoryCalendar$CurMonthDayData r0 = r0.curMonthDayData
            r23 = r0
            r0 = r23
            r0.endX = r7
            goto L_0x04ab
        L_0x0558:
            r0 = r28
            int r0 = r0.perColWeight
            r23 = r0
            int r20 = r20 + r23
            goto L_0x04ce
        L_0x0562:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.sport_module.view.calendar.HistoryCalendar.drawClientDayCells(android.graphics.Canvas):void");
    }

    public void setOnCalendarDayClick(OnCalendarDayClick click) {
        this.onCalendarDayClick = click;
    }

    public void updateCalendarCard(int year, int month, int selectedDay2) {
        this.mDisplayCalendar.set(1, year);
        this.mDisplayCalendar.set(2, month - 1);
        this.mDisplayCalendar.set(5, 1);
        this.selectedDay = selectedDay2;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1) {
            CalendarDayClickData calendarDayClickData = getDayFromLocation(event.getX(), event.getY());
            if (!(calendarDayClickData == null || this.onCalendarDayClick == null)) {
                this.myYear = calendarDayClickData.getYear();
                this.myMonth = calendarDayClickData.getMonth();
                this.myDay = calendarDayClickData.getDay();
                this.onCalendarDayClick.onDayClick(calendarDayClickData);
            }
        }
        return true;
    }

    public CalendarDayClickData getDayFromLocation(float x, float y) {
        if (x < 0.0f || x > ((float) this.viewWidth)) {
            return null;
        }
        int selectedNewY = ((int) (y - ((float) this.weekTitleHeight))) / this.perRowHeight;
        int selectedNewX = (int) ((((float) this.PER_WEEK_NUM) * x) / ((float) this.viewWidth));
        if (selectedNewY > this.curMonthDayData.startY && selectedNewY < this.curMonthDayData.endY) {
            int day = (((this.PER_WEEK_NUM * selectedNewY) + selectedNewX) - this.curMonthDayData.startX) + 1;
            if (this.curYear > this.todayYear) {
                return null;
            }
            if (this.curYear == this.todayYear && this.curMonth > this.todayMonth) {
                return null;
            }
            if (this.curMonth == this.todayMonth && this.curYear == this.todayYear && day > this.todayDay) {
                return null;
            }
            this.selectedDay = day;
            invalidate();
            return new CalendarDayClickData(selectedNewX, selectedNewY, this.curYear, this.curMonth, day, selectedNewX);
        } else if (selectedNewY == this.curMonthDayData.startY) {
            if (selectedNewX < this.curMonthDayData.startX) {
                return null;
            }
            int day2 = (((this.PER_WEEK_NUM * selectedNewY) + selectedNewX) - this.curMonthDayData.startX) + 1;
            if (this.curYear > this.todayYear) {
                return null;
            }
            if (this.curYear == this.todayYear && this.curMonth > this.todayMonth) {
                return null;
            }
            if (this.curMonth == this.todayMonth && this.curYear == this.todayYear && day2 > this.todayDay) {
                return null;
            }
            this.selectedDay = day2;
            invalidate();
            return new CalendarDayClickData(selectedNewX, selectedNewY, this.curYear, this.curMonth, day2, selectedNewX);
        } else if (selectedNewY != this.curMonthDayData.endY || selectedNewX > this.curMonthDayData.endX) {
            return null;
        } else {
            int day3 = (((this.PER_WEEK_NUM * selectedNewY) + selectedNewX) - this.curMonthDayData.startX) + 1;
            if (this.curYear > this.todayYear) {
                return null;
            }
            if (this.curYear == this.todayYear && this.curMonth > this.todayMonth) {
                return null;
            }
            if (this.curMonth == this.todayMonth && this.curYear == this.todayYear && day3 > this.todayDay) {
                return null;
            }
            this.selectedDay = day3;
            invalidate();
            return new CalendarDayClickData(selectedNewX, selectedNewY, this.curYear, this.curMonth, day3, selectedNewX);
        }
    }

    public void update(int year, int month, int selectedDay2) {
        this.mDisplayCalendar.set(1, year);
        this.mDisplayCalendar.set(2, month - 1);
        this.selectedDay = selectedDay2;
        this.myYear = year;
        this.myMonth = month;
        this.myDay = selectedDay2;
        invalidate();
    }

    public void update() {
        invalidate();
    }

    public void setRoundColor(int color) {
        this.mSelectedCircleColor = color;
        this.mSelectedCirclePaint.setColor(this.mSelectedCircleColor);
        this.todayCirclePaint.setColor(this.mSelectedCircleColor);
    }
}
