package com.iwown.device_module.common.view.wheelView;

import android.content.Context;
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
import com.iwown.device_module.common.view.wheelView.TosAdapterView.OnItemSelectedListener;
import com.iwown.device_module.common.view.wheelView.TosGallery.OnEndFlingListener;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import java.util.ArrayList;
import java.util.List;

public class TimePointView extends LinearLayout {
    /* access modifiers changed from: private */
    public static int ITEM_HEIGHT = 0;
    private CommonAdapter<String> endAdapter;
    /* access modifiers changed from: private */
    public List<String> endTimeList = new ArrayList();
    View line;
    private TextView mCancel;
    private TextView mConfirm;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public WheelViewPro mEndTime;
    /* access modifiers changed from: private */
    public int mEndTimeCurrentPosition;
    private String[] mMinArr;
    private OnConfirmListener mOnConfirmListener;
    private boolean mSetEndData;
    private boolean mSetStartData;
    /* access modifiers changed from: private */
    public WheelViewPro mStartTime;
    /* access modifiers changed from: private */
    public int mStartTimeCurrPosition;
    private TextView mTitle;
    private CommonAdapter<String> startAdapter;
    /* access modifiers changed from: private */
    public List<String> startTimeList = new ArrayList();
    private String[] timeArr;

    public interface OnConfirmListener {
        void onConfirm(int i);
    }

    public TimePointView(Context context) {
        super(context);
    }

    public TimePointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        ITEM_HEIGHT = WindowsUtil.dip2px(this.mContext, 56.0f);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.device_module_view_time_point, this);
        boolean show = context.obtainStyledAttributes(attrs, R.styleable.device_module_time_picker_line).getBoolean(R.styleable.device_module_time_picker_line_device_module_show, false);
        initView(context, attrs);
        initData();
        initListener();
        if (show) {
            this.line.setVisibility(0);
        } else {
            this.line.setVisibility(8);
        }
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
        this.line = findViewById(R.id.line_view);
    }

    public void initData() {
        this.mMinArr = this.mContext.getApplicationContext().getResources().getStringArray(R.array.device_module_min_has_zero);
        for (int i = 0; i <= 23; i++) {
            this.startTimeList.add(String.valueOf(i));
        }
        for (String s : this.mMinArr) {
            this.endTimeList.add(s);
        }
        this.startAdapter = new CommonAdapter<String>(this.mContext, this.startTimeList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = TimePointView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) TimePointView.this.startTimeList.get(position));
                if (position == TimePointView.this.mStartTimeCurrPosition) {
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
                params.height = TimePointView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) TimePointView.this.endTimeList.get(position));
                if (position == TimePointView.this.mEndTimeCurrentPosition) {
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
                TimePointView.this.mStartTimeCurrPosition = v.getSelectedItemPosition();
            }
        });
        this.mEndTime.setOnEndFlingListener(new OnEndFlingListener() {
            public void onEndFling(TosGallery v) {
                TimePointView.this.mEndTimeCurrentPosition = v.getSelectedItemPosition();
            }
        });
        this.mStartTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if ((position > TimePointView.this.mStartTimeCurrPosition && TimePointView.this.mStartTimeCurrPosition != 0) || (TimePointView.this.mStartTimeCurrPosition == TimePointView.this.startTimeList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimePointView.this.mStartTimeCurrPosition = position;
                } else if (position < TimePointView.this.mStartTimeCurrPosition || (TimePointView.this.mStartTimeCurrPosition == 0 && position == TimePointView.this.startTimeList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimePointView.this.mStartTimeCurrPosition = position;
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.mEndTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if ((position > TimePointView.this.mEndTimeCurrentPosition && TimePointView.this.mEndTimeCurrentPosition != 0) || (TimePointView.this.mEndTimeCurrentPosition == TimePointView.this.endTimeList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimePointView.this.mEndTimeCurrentPosition = position;
                } else if (position < TimePointView.this.mEndTimeCurrentPosition || (TimePointView.this.mEndTimeCurrentPosition == 0 && position == TimePointView.this.endTimeList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(TimePointView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    TimePointView.this.mEndTimeCurrentPosition = position;
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.mStartTime.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    TimePointView.this.mStartTime.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        this.mEndTime.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    TimePointView.this.mEndTime.getParent().requestDisallowInterceptTouchEvent(true);
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
