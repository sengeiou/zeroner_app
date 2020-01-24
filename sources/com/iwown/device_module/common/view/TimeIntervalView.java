package com.iwown.device_module.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.common.adapter.CommonAdapter;
import com.iwown.device_module.common.adapter.ViewHolder;
import com.iwown.device_module.common.view.wheelView.TosAdapterView;
import com.iwown.device_module.common.view.wheelView.TosAdapterView.OnItemSelectedListener;
import com.iwown.device_module.common.view.wheelView.TosGallery;
import com.iwown.device_module.common.view.wheelView.TosGallery.OnEndFlingListener;
import com.iwown.device_module.common.view.wheelView.WheelViewPro;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeIntervalView extends LinearLayout {
    /* access modifiers changed from: private */
    public static int ITEM_HEIGHT = 0;
    private CommonAdapter<String> endAdapter;
    /* access modifiers changed from: private */
    public List<String> endTimeList = new ArrayList();
    /* access modifiers changed from: private */
    public boolean isLeftScrollEnd = true;
    /* access modifiers changed from: private */
    public boolean isRightScrollEnd = true;
    private TextView mCancel;
    private TextView mConfirm;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public WheelViewPro mEndTime;
    /* access modifiers changed from: private */
    public int mEndTimeCurrentPosition;
    private OnConfirmListener mOnConfirmListener;
    /* access modifiers changed from: private */
    public OnScrollChangeListener mOnScrollChangeListener = null;
    private boolean mSetEndData;
    private boolean mSetStartData;
    /* access modifiers changed from: private */
    public WheelViewPro mStartTime;
    /* access modifiers changed from: private */
    public int mStartTimeCurrPosition;
    private TextView mTitle;
    private int mUse_for;
    private CommonAdapter<String> startAdapter;
    /* access modifiers changed from: private */
    public List<String> startTimeList = new ArrayList();
    private String[] timeArr;
    private String[] timeArr2;

    public interface OnConfirmListener {
        void onConfirm(int i);
    }

    public interface OnScrollChangeListener {
        void onEnd(int i, int i2);

        void onScroll(int i, int i2);
    }

    public OnScrollChangeListener getOnScrollChangeListener() {
        return this.mOnScrollChangeListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public TimeIntervalView(Context context) {
        super(context);
    }

    public TimeIntervalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        ITEM_HEIGHT = WindowsUtil.dip2px(this.mContext, 56.0f);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.device_module_view_time_interval, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_TimeIntervalView);
        this.mUse_for = a.getInt(R.styleable.device_module_TimeIntervalView_device_module_use_for, 0);
        a.recycle();
        initView(context, attrs);
        initData();
        initListener();
    }

    public void setStartCurrPosition(int position) {
        this.mSetStartData = true;
        this.mStartTimeCurrPosition = position;
        this.mStartTime.setSelection(this.mStartTimeCurrPosition);
    }

    public void setEndCurrPosition(int position) {
        this.mSetEndData = true;
        this.mEndTimeCurrentPosition = position;
        this.mEndTime.setSelection(this.mEndTimeCurrentPosition);
    }

    public void initView(Context context, AttributeSet attrs) {
        this.mCancel = (TextView) findViewById(R.id.cancel);
        this.mConfirm = (TextView) findViewById(R.id.confirm);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mStartTime = (WheelViewPro) findViewById(R.id.start_time);
        this.mEndTime = (WheelViewPro) findViewById(R.id.end_time);
    }

    public void initData() {
        if (this.mUse_for == 0) {
            this.timeArr = this.mContext.getResources().getStringArray(R.array.device_module_time);
            this.startTimeList = Arrays.asList(this.timeArr);
            this.endTimeList = Arrays.asList(this.timeArr);
        } else if (this.mUse_for == 1) {
            for (int i = 40; i <= 190; i++) {
                this.startTimeList.add(String.valueOf(i));
            }
            for (int i2 = 50; i2 <= 200; i2++) {
                this.endTimeList.add(String.valueOf(i2));
            }
        } else if (this.mUse_for == 2) {
            this.timeArr = this.mContext.getResources().getStringArray(R.array.device_module_time);
            this.startTimeList = Arrays.asList(this.timeArr);
            this.timeArr2 = this.mContext.getResources().getStringArray(R.array.device_module_time_1_24);
            this.endTimeList = Arrays.asList(this.timeArr2);
        }
        this.startAdapter = new CommonAdapter<String>(this.mContext, this.startTimeList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = TimeIntervalView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) TimeIntervalView.this.startTimeList.get(position));
                if (position == TimeIntervalView.this.mStartTimeCurrPosition) {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                } else {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                }
            }
        };
        this.mStartTime.setAdapter((SpinnerAdapter) this.startAdapter);
        this.startAdapter.notifyDataSetChanged();
        this.endAdapter = new CommonAdapter<String>(this.mContext, this.endTimeList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = TimeIntervalView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) TimeIntervalView.this.endTimeList.get(position));
                if (position == TimeIntervalView.this.mEndTimeCurrentPosition) {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                } else {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                }
            }
        };
        this.mEndTime.setAdapter((SpinnerAdapter) this.endAdapter);
        this.endAdapter.notifyDataSetChanged();
    }

    public void setTitle(int resId) {
        this.mTitle.setText(resId);
    }

    public void initListener() {
        this.mStartTime.setScrollCycle(true);
        this.mEndTime.setScrollCycle(true);
        this.mStartTime.setOnEndFlingListener(new OnEndFlingListener() {
            public void onEndFling(TosGallery v) {
                TimeIntervalView.this.mStartTimeCurrPosition = v.getSelectedItemPosition();
                TimeIntervalView.this.isLeftScrollEnd = true;
                if (TimeIntervalView.this.isLeftScrollEnd && TimeIntervalView.this.isRightScrollEnd && TimeIntervalView.this.mOnScrollChangeListener != null) {
                    TimeIntervalView.this.mOnScrollChangeListener.onEnd(TimeIntervalView.this.mStartTimeCurrPosition, TimeIntervalView.this.mEndTimeCurrentPosition);
                }
            }
        });
        this.mEndTime.setOnEndFlingListener(new OnEndFlingListener() {
            public void onEndFling(TosGallery v) {
                TimeIntervalView.this.mEndTimeCurrentPosition = v.getSelectedItemPosition();
                TimeIntervalView.this.isRightScrollEnd = true;
                if (TimeIntervalView.this.isLeftScrollEnd && TimeIntervalView.this.isRightScrollEnd && TimeIntervalView.this.mOnScrollChangeListener != null) {
                    TimeIntervalView.this.mOnScrollChangeListener.onEnd(TimeIntervalView.this.mStartTimeCurrPosition, TimeIntervalView.this.mEndTimeCurrentPosition);
                }
            }
        });
        this.mStartTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if (position > TimeIntervalView.this.mStartTimeCurrPosition || (TimeIntervalView.this.mStartTimeCurrPosition == TimeIntervalView.this.startTimeList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimeIntervalView.this.mStartTimeCurrPosition = position;
                    TimeIntervalView.this.isLeftScrollEnd = false;
                    if (TimeIntervalView.this.mOnScrollChangeListener != null) {
                        TimeIntervalView.this.mOnScrollChangeListener.onScroll(TimeIntervalView.this.mStartTimeCurrPosition, TimeIntervalView.this.mEndTimeCurrentPosition);
                    }
                } else if (position < TimeIntervalView.this.mStartTimeCurrPosition || (TimeIntervalView.this.mStartTimeCurrPosition == 0 && position == TimeIntervalView.this.startTimeList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimeIntervalView.this.mStartTimeCurrPosition = position;
                    TimeIntervalView.this.isLeftScrollEnd = false;
                    if (TimeIntervalView.this.mOnScrollChangeListener != null) {
                        TimeIntervalView.this.mOnScrollChangeListener.onScroll(TimeIntervalView.this.mStartTimeCurrPosition, TimeIntervalView.this.mEndTimeCurrentPosition);
                    }
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.mEndTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if (position > TimeIntervalView.this.mEndTimeCurrentPosition || (TimeIntervalView.this.mEndTimeCurrentPosition == TimeIntervalView.this.endTimeList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimeIntervalView.this.mEndTimeCurrentPosition = position;
                    TimeIntervalView.this.isRightScrollEnd = false;
                    if (TimeIntervalView.this.mOnScrollChangeListener != null) {
                        TimeIntervalView.this.mOnScrollChangeListener.onScroll(TimeIntervalView.this.mStartTimeCurrPosition, TimeIntervalView.this.mEndTimeCurrentPosition);
                    }
                } else if (position < TimeIntervalView.this.mEndTimeCurrentPosition || (TimeIntervalView.this.mEndTimeCurrentPosition == 0 && position == TimeIntervalView.this.endTimeList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimeIntervalView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimeIntervalView.this.mEndTimeCurrentPosition = position;
                    TimeIntervalView.this.isRightScrollEnd = false;
                    if (TimeIntervalView.this.mOnScrollChangeListener != null) {
                        TimeIntervalView.this.mOnScrollChangeListener.onScroll(TimeIntervalView.this.mStartTimeCurrPosition, TimeIntervalView.this.mEndTimeCurrentPosition);
                    }
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.mStartTime.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    TimeIntervalView.this.mStartTime.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        this.mEndTime.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    TimeIntervalView.this.mEndTime.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.mOnConfirmListener = onConfirmListener;
    }

    public int getStartTimeCurrPosition() {
        return this.mStartTimeCurrPosition;
    }

    public int getEndTimeCurrentPosition() {
        return this.mEndTimeCurrentPosition;
    }
}
