package com.iwown.device_module.device_alarm_schedule.view.Calendar.View;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.Month;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.MonthDay;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.CalendarView.NewMonthViewListener;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.CalendarView.OnDatePickListener;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.MonthView.ShouldDismissListener;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.View.WeekView2.OnDatePickerListener;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.cli.HelpFormatter;

public class ZCalenderView extends RelativeLayout implements OnClickListener {
    public static final int NUM = 4;
    OnDatePickListener calenderListener;
    /* access modifiers changed from: private */
    public boolean isAnimator;
    /* access modifiers changed from: private */
    public boolean isFirst;
    /* access modifiers changed from: private */
    public boolean isOpen;
    private View line;
    AnimatorUpdateListener mAnimatorUpdateListener;
    private ImageButton mBt;
    private Button mBtToday;
    private ArrayList<CalendarView> mCalendarViews;
    private Context mContext;
    /* access modifiers changed from: private */
    public int mCurrPosition;
    /* access modifiers changed from: private */
    public MonthCalendarDismissListener mMonthCalendarDismissListener;
    private int mMonthHeight;
    /* access modifiers changed from: private */
    public OnDateSelectListener mOnDateSelectListener;
    SimpleAnimatorListener mSimpleAnimatorListener;
    private VelocityTracker mTracker;
    /* access modifiers changed from: private */
    public TextView mTvDate;
    /* access modifiers changed from: private */
    public ViewAdapter mViewAdapter;
    /* access modifiers changed from: private */
    public ViewPager mVp;
    WeekView2 mWeek;
    private int mWeekHeight;
    private WeekView2 week;

    public interface MonthCalendarDismissListener {
        void whenMonthCalendarDismiss(View view);
    }

    public interface OnDateSelectListener {
        void onDateSelect(View view, Month month, int i);
    }

    private class ViewAdapter extends PagerAdapter {
        private ViewAdapter() {
        }

        public int getCount() {
            return Integer.MAX_VALUE;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            CalendarView calendarView = ZCalenderView.this.getCalendarView(position);
            calendarView.setTag(Integer.valueOf(position));
            calendarView.setOnDatePickListener(ZCalenderView.this.calenderListener);
            container.addView(calendarView);
            return calendarView;
        }

        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public void setMonthCalendarDismissListener(MonthCalendarDismissListener monthCalendarDismissListener) {
        this.mMonthCalendarDismissListener = monthCalendarDismissListener;
    }

    public ZCalenderView(Context context) {
        this(context, null);
    }

    public ZCalenderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZCalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrPosition = 1000;
        this.mCalendarViews = new ArrayList<>();
        this.isOpen = false;
        this.isFirst = true;
        this.calenderListener = new OnDatePickListener() {
            public void onDatePick(CalendarView view, Month month, int selectIndex) {
                if (((Integer) view.getTag()).intValue() == ZCalenderView.this.mCurrPosition) {
                    Month curMonth = ZCalenderView.this.getCalendarView(ZCalenderView.this.mVp.getCurrentItem()).getCurMonth();
                    int day = curMonth.getMonthDay(selectIndex).getmDay();
                    ZCalenderView.this.mTvDate.setText(curMonth.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + (curMonth.getMonth() + 1) + HelpFormatter.DEFAULT_OPT_PREFIX + day);
                    KLog.e("licl2", curMonth.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + (curMonth.getMonth() + 1) + HelpFormatter.DEFAULT_OPT_PREFIX + day);
                    ZCalenderView.this.mViewAdapter.notifyDataSetChanged();
                    ZCalenderView.this.refreshPreAndNext();
                    if (ZCalenderView.this.mOnDateSelectListener != null) {
                        ZCalenderView.this.mOnDateSelectListener.onDateSelect(ZCalenderView.this, month, selectIndex);
                    }
                }
            }
        };
        this.mSimpleAnimatorListener = new SimpleAnimatorListener() {
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ZCalenderView.this.isAnimator = true;
                ZCalenderView.this.mWeek.setVisibility(8);
                ZCalenderView.this.mVp.setVisibility(0);
            }

            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ZCalenderView.this.isAnimator = false;
                if (!ZCalenderView.this.isOpen) {
                    ZCalenderView.this.mWeek.setWeekView(ZCalenderView.this.getCalendarView(ZCalenderView.this.mCurrPosition).getCurMonth(), ZCalenderView.this.getCalendarView(ZCalenderView.this.mCurrPosition).getCurSelectIndex());
                    ZCalenderView.this.mWeek.setVisibility(0);
                    ZCalenderView.this.mVp.setVisibility(8);
                }
            }
        };
        this.mAnimatorUpdateListener = new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = ((Integer) animation.getAnimatedValue()).intValue();
                LayoutParams layoutParams = ZCalenderView.this.mVp.getLayoutParams();
                layoutParams.height = height;
                ZCalenderView.this.mVp.setLayoutParams(layoutParams);
            }
        };
        this.mContext = context;
        this.mTracker = VelocityTracker.obtain();
        initView(attrs);
        initData();
        initListener();
    }

    private void initView(AttributeSet attrs) {
        View inflate = View.inflate(this.mContext, R.layout.device_module_view_calendar2, this);
        this.mTvDate = (TextView) findViewById(R.id.tv_date);
        this.mBt = (ImageButton) findViewById(R.id.bt);
        this.mBtToday = (Button) findViewById(R.id.bt_today);
        this.line = findViewById(R.id.line);
        this.mVp = (ViewPager) findViewById(R.id.vp);
        this.mWeek = (WeekView2) findViewById(R.id.week);
        LayoutParams layoutParams = this.mVp.getLayoutParams();
        float screenWidth = (float) WindowsUtil.getScreenWidth(this.mContext);
        layoutParams.height = (int) ((screenWidth * 0.10714286f) + (screenWidth * 0.6f));
        this.mMonthHeight = (int) ((screenWidth * 0.10714286f) + (screenWidth * 0.6f));
        this.mVp.setLayoutParams(layoutParams);
    }

    private void initData() {
        this.mCalendarViews.add(new CalendarView(this.mContext));
        this.mCalendarViews.add(new CalendarView(this.mContext));
        this.mCalendarViews.add(new CalendarView(this.mContext));
        this.mCalendarViews.add(new CalendarView(this.mContext));
        this.mViewAdapter = new ViewAdapter();
        this.mVp.setAdapter(this.mViewAdapter);
        this.mVp.measure(-1, -1);
        this.mWeek.measure(-1, -1);
    }

    private void initListener() {
        this.mVp.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ZCalenderView.this.mCurrPosition = position;
                CalendarView calendarView = ZCalenderView.this.getCalendarView(position);
                Month curMonth = calendarView.getCurMonth();
                ZCalenderView.this.mTvDate.setText(curMonth.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + (curMonth.getMonth() + 1) + HelpFormatter.DEFAULT_OPT_PREFIX + curMonth.getMonthDay(calendarView.getCurSelectIndex()).getmDay());
                ZCalenderView.this.refreshPreAndNext();
                if (ZCalenderView.this.mOnDateSelectListener != null) {
                    ZCalenderView.this.mOnDateSelectListener.onDateSelect(ZCalenderView.this, curMonth, calendarView.getCurSelectIndex());
                }
            }
        });
        this.mVp.setCurrentItem(this.mCurrPosition);
        resetAllMonthViewListener();
        Iterator it = this.mCalendarViews.iterator();
        while (it.hasNext()) {
            ((CalendarView) it.next()).setMonthViewListener(new NewMonthViewListener() {
                public void onNewMonthView() {
                    KLog.e("licl", "新的monthview产生");
                    ZCalenderView.this.resetAllMonthViewListener();
                }
            });
        }
        this.mBt.setOnClickListener(this);
        this.mBtToday.setOnClickListener(this);
        this.mWeek.setOnDatePickerListener(new OnDatePickerListener() {
            public void onDatePicker(Month month, int selectIndex) {
                CalendarView calendarView = ZCalenderView.this.getCalendarView(ZCalenderView.this.mCurrPosition);
                MonthDay monthDay = month.getMonthDay(selectIndex);
                ZCalenderView.this.mTvDate.setText(month.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + (monthDay.getmMonth() + 1) + HelpFormatter.DEFAULT_OPT_PREFIX + monthDay.getmDay());
                KLog.e("licl", month.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + (monthDay.getmMonth() + 1) + HelpFormatter.DEFAULT_OPT_PREFIX + monthDay.getmDay());
                if (ZCalenderView.this.isFirst && ZCalenderView.this.mOnDateSelectListener != null) {
                    ZCalenderView.this.mOnDateSelectListener.onDateSelect(ZCalenderView.this, month, selectIndex);
                }
                calendarView.updateSelectedIndex(month, selectIndex);
            }
        });
        if (this.isOpen) {
            this.mWeek.setVisibility(8);
            this.mVp.setVisibility(0);
            return;
        }
        this.mWeek.setWeekView(getCalendarView(this.mCurrPosition).getCurMonth(), getCalendarView(this.mCurrPosition).getCurSelectIndex());
        this.mWeek.setVisibility(0);
        this.mVp.setVisibility(8);
    }

    public void resetAllMonthViewListener() {
        Iterator it = this.mCalendarViews.iterator();
        while (it.hasNext()) {
            ((CalendarView) it.next()).getMonthView().setDismissListener(new ShouldDismissListener() {
                public void shouldDismiss() {
                    ZCalenderView.this.setViewPagerVisible(false);
                    if (ZCalenderView.this.mMonthCalendarDismissListener != null) {
                        ZCalenderView.this.mMonthCalendarDismissListener.whenMonthCalendarDismiss(ZCalenderView.this);
                    }
                    ZCalenderView.this.setWeekViewVisible(true);
                    ZCalenderView.this.resetWeekView();
                }
            });
        }
    }

    public void onClick(View v) {
        this.isFirst = false;
        int i = v.getId();
        if (i == R.id.bt) {
            if (this.isOpen) {
                closeAnimation();
            } else {
                openAnimation();
            }
        } else if (i == R.id.bt_today) {
            CalendarView calendarView = getCalendarView(this.mCurrPosition);
            boolean rest = calendarView.restView();
            if (!this.isOpen) {
                openAnimation();
            }
            if (rest) {
                calendarView.performDayClick();
            }
        }
    }

    public Month getCurMonth() {
        return getCalendarView(this.mCurrPosition).getCurMonth();
    }

    /* access modifiers changed from: private */
    public CalendarView getCalendarView(int position) {
        return (CalendarView) this.mCalendarViews.get(position % 4);
    }

    public int getCurSelectIndex() {
        return getCalendarView(this.mCurrPosition).getCurSelectIndex();
    }

    public void updateView() {
        getCalendarView(this.mCurrPosition).updateView();
    }

    public void setOnDateSelectListener(OnDateSelectListener listener) {
        this.mOnDateSelectListener = listener;
    }

    private void closeAnimation() {
        if (!this.isAnimator) {
            this.isOpen = false;
            if (this.mMonthHeight == 0) {
                this.mMonthHeight = this.mVp.getMeasuredHeight();
            }
            if (this.mWeekHeight == 0) {
                this.mWeekHeight = this.mWeek.getMeasuredHeight();
            }
            ValueAnimator animator = ValueAnimator.ofInt(new int[]{this.mMonthHeight, this.mWeekHeight});
            animator.addUpdateListener(this.mAnimatorUpdateListener);
            animator.addListener(this.mSimpleAnimatorListener);
            animator.setDuration(500);
            animator.start();
        }
    }

    private void openAnimation() {
        if (!this.isAnimator) {
            this.isOpen = true;
            if (this.mMonthHeight == 0) {
                this.mMonthHeight = this.mVp.getMeasuredHeight();
            }
            if (this.mWeekHeight == 0) {
                this.mWeekHeight = this.mWeek.getMeasuredHeight();
            }
            ValueAnimator animator = ValueAnimator.ofInt(new int[]{this.mWeekHeight, this.mMonthHeight});
            animator.addUpdateListener(this.mAnimatorUpdateListener);
            animator.addListener(this.mSimpleAnimatorListener);
            animator.setDuration(500);
            animator.start();
        }
    }

    /* access modifiers changed from: private */
    public void refreshPreAndNext() {
        CalendarView curCalendarView = getCalendarView(this.mCurrPosition);
        CalendarView preCalendarView = getCalendarView(this.mCurrPosition - 1);
        CalendarView nextCalendarView = getCalendarView(this.mCurrPosition + 1);
        Month curMonth = curCalendarView.getCurMonth();
        int day = curMonth.getMonthDay(curCalendarView.getCurSelectIndex()).getmDay();
        if (curMonth.getMonth() + 1 != nextCalendarView.getCurMonth().getMonth()) {
            nextCalendarView.showNextMonth(curMonth, day);
        } else if (day != nextCalendarView.getCurMonth().getMonthDay(nextCalendarView.getCurSelectIndex()).getmDay()) {
            nextCalendarView.showDay(day);
        }
        if (curMonth.getMonth() - 1 != preCalendarView.getCurMonth().getMonth()) {
            preCalendarView.showPrevMonth(curMonth, day);
        } else if (day != preCalendarView.getCurMonth().getMonthDay(preCalendarView.getCurSelectIndex()).getmDay()) {
            preCalendarView.showDay(day);
        }
    }

    public void setWeekViewVisible(boolean isVisible) {
        this.mWeek.setVisibility(isVisible ? 0 : 4);
    }

    public void setViewPagerVisible(boolean isVisible) {
        this.mVp.setVisibility(isVisible ? 0 : 8);
    }

    public void changeWeekViewVisible() {
        if (this.mWeek.getVisibility() == 0) {
            this.mWeek.setVisibility(4);
        } else {
            this.mWeek.setVisibility(0);
        }
    }

    public void changeViewPagerVisible() {
        if (this.mVp.getVisibility() == 0) {
            this.mVp.setVisibility(8);
        } else {
            this.mVp.setVisibility(0);
        }
    }

    public void resetWeekView() {
        this.mWeek.setWeekView(getCalendarView(this.mCurrPosition).getCurMonth(), getCalendarView(this.mCurrPosition).getCurSelectIndex());
    }

    public boolean isViewPagerVisible() {
        if (this.mVp.getVisibility() == 0) {
            return true;
        }
        return false;
    }
}
