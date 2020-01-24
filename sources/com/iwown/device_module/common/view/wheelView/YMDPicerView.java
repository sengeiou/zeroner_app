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
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class YMDPicerView extends LinearLayout {
    public static int END_YEAR = 2067;
    /* access modifiers changed from: private */
    public static int ITEM_HEIGHT = 0;
    public static int START_YEAR = 2017;
    /* access modifiers changed from: private */
    public WheelViewPro day;
    /* access modifiers changed from: private */
    public int dayCurrentPosition;
    /* access modifiers changed from: private */
    public CommonAdapter<String> daysAdapter = null;
    /* access modifiers changed from: private */
    public List<String> daysList = new ArrayList();
    private TextView mCancel;
    private TextView mConfirm;
    /* access modifiers changed from: private */
    public Context mContext;
    private String[] mMinArr;
    private String[] mMonthArr;
    private OnConfirmListener mOnConfirmListener;
    private boolean mSetDayData;
    private boolean mSetMonthData;
    private boolean mSetYearData;
    private TextView mTitle;
    /* access modifiers changed from: private */
    public WheelViewPro month;
    private CommonAdapter<String> monthAdapter = null;
    /* access modifiers changed from: private */
    public int monthCurrentPosition;
    /* access modifiers changed from: private */
    public List<String> monthList = new ArrayList();
    private String[] timeArr;
    /* access modifiers changed from: private */
    public WheelViewPro year;
    private CommonAdapter<String> yearAdapter = null;
    /* access modifiers changed from: private */
    public int yearCurrentPosition;
    /* access modifiers changed from: private */
    public List<String> yearList = new ArrayList();

    public interface OnConfirmListener {
        void onConfirm(int i);
    }

    public YMDPicerView(Context context) {
        super(context);
    }

    public YMDPicerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        ITEM_HEIGHT = WindowsUtil.dip2px(this.mContext, 56.0f);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.device_module_view_ymd_picker, this);
        this.mMonthArr = this.mContext.getResources().getStringArray(R.array.device_module_months_items);
        initView(context, attrs);
        initData();
        initListener();
    }

    public void setYearCurrentPosition(int position) {
        this.mSetYearData = true;
        this.yearCurrentPosition = position;
        this.year.setSelection(this.yearCurrentPosition);
    }

    public void setMonthCurrentPosition(int position) {
        this.mSetMonthData = true;
        this.monthCurrentPosition = position;
        this.month.setSelection(this.monthCurrentPosition);
    }

    public void setDayCurrentPosition(int position) {
        this.mSetDayData = true;
        this.dayCurrentPosition = position;
        this.day.setSelection(this.dayCurrentPosition);
    }

    public void initView(Context context, AttributeSet attrs) {
        this.mCancel = (TextView) findViewById(R.id.cancel);
        this.mConfirm = (TextView) findViewById(R.id.confirm);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.year = (WheelViewPro) findViewById(R.id.year);
        this.month = (WheelViewPro) findViewById(R.id.month);
        this.day = (WheelViewPro) findViewById(R.id.day);
    }

    public void initData() {
        Calendar cal = Calendar.getInstance();
        prepareYearList();
        prepareMonthList();
        prepareDyasList(cal.get(1), cal.get(2) + 1);
        this.yearAdapter = new CommonAdapter<String>(this.mContext, this.yearList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = YMDPicerView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) YMDPicerView.this.yearList.get(position));
                if (position == YMDPicerView.this.yearCurrentPosition) {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                } else {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                }
            }
        };
        this.year.setAdapter((SpinnerAdapter) this.yearAdapter);
        this.yearAdapter.notifyDataSetChanged();
        this.monthAdapter = new CommonAdapter<String>(this.mContext, this.monthList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = YMDPicerView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) YMDPicerView.this.monthList.get(position));
                if (position == YMDPicerView.this.monthCurrentPosition) {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                } else {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                }
            }
        };
        this.month.setAdapter((SpinnerAdapter) this.monthAdapter);
        this.monthAdapter.notifyDataSetChanged();
        this.daysAdapter = new CommonAdapter<String>(this.mContext, this.daysList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = YMDPicerView.ITEM_HEIGHT;
                tv.setLayoutParams(params);
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) YMDPicerView.this.daysList.get(position));
                if (position == YMDPicerView.this.dayCurrentPosition) {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                } else {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                }
            }
        };
        this.day.setAdapter((SpinnerAdapter) this.daysAdapter);
        this.daysAdapter.notifyDataSetChanged();
    }

    private void prepareYearList() {
        for (int i = START_YEAR; i <= END_YEAR; i++) {
            this.yearList.add(String.valueOf(i));
        }
    }

    private void prepareMonthList() {
        this.monthList.clear();
        this.monthList.addAll(Arrays.asList(this.mMonthArr));
    }

    /* access modifiers changed from: private */
    public void prepareDyasList(int year2, int month2) {
        this.daysList.clear();
        for (int i = 1; i <= Utils.getDaysOfMonth(year2, month2); i++) {
            this.daysList.add(String.valueOf(i));
        }
    }

    public void setTitle(int resId) {
        this.mTitle.setText(resId);
    }

    public void initListener() {
        this.year.setScrollCycle(true);
        this.month.setScrollCycle(true);
        this.day.setScrollCycle(true);
        this.year.setOnEndFlingListener(new OnEndFlingListener() {
            public void onEndFling(TosGallery v) {
                YMDPicerView.this.yearCurrentPosition = v.getSelectedItemPosition();
            }
        });
        this.month.setOnEndFlingListener(new OnEndFlingListener() {
            public void onEndFling(TosGallery v) {
                YMDPicerView.this.monthCurrentPosition = v.getSelectedItemPosition();
            }
        });
        this.day.setOnEndFlingListener(new OnEndFlingListener() {
            public void onEndFling(TosGallery v) {
                YMDPicerView.this.dayCurrentPosition = v.getSelectedItemPosition();
            }
        });
        this.year.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if ((position > YMDPicerView.this.yearCurrentPosition && YMDPicerView.this.yearCurrentPosition != 0) || (YMDPicerView.this.yearCurrentPosition == YMDPicerView.this.yearList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    YMDPicerView.this.yearCurrentPosition = position;
                }
                if (position < YMDPicerView.this.yearCurrentPosition || (YMDPicerView.this.yearCurrentPosition == 0 && position == YMDPicerView.this.yearList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    YMDPicerView.this.yearCurrentPosition = position;
                }
                if (YMDPicerView.this.getMonthCurrentPosition() == 1) {
                    YMDPicerView.this.prepareDyasList(Integer.parseInt((String) YMDPicerView.this.yearList.get(position)), 2);
                    YMDPicerView.this.daysAdapter.notifyDataSetChanged();
                    if (YMDPicerView.this.getDayCurrentPosition() == 28 && !Utils.isLeap(Integer.parseInt((String) YMDPicerView.this.yearList.get(position)))) {
                        YMDPicerView.this.setDayCurrentPosition(27);
                    }
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.month.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if ((position > YMDPicerView.this.monthCurrentPosition && YMDPicerView.this.monthCurrentPosition != 0) || (YMDPicerView.this.monthCurrentPosition == YMDPicerView.this.monthList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    YMDPicerView.this.monthCurrentPosition = position;
                }
                if (position < YMDPicerView.this.monthCurrentPosition || (YMDPicerView.this.monthCurrentPosition == 0 && position == YMDPicerView.this.monthList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    YMDPicerView.this.monthCurrentPosition = position;
                }
                YMDPicerView.this.prepareDyasList(YMDPicerView.this.getYearCurrentPosition() + YMDPicerView.START_YEAR, position + 1);
                YMDPicerView.this.daysAdapter.notifyDataSetChanged();
                if (YMDPicerView.this.getDayCurrentPosition() > 28 && position == 1) {
                    YMDPicerView.this.setDayCurrentPosition(YMDPicerView.this.daysList.size() - 1);
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.day.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if ((position > YMDPicerView.this.dayCurrentPosition && YMDPicerView.this.dayCurrentPosition != 0) || (YMDPicerView.this.dayCurrentPosition == YMDPicerView.this.daysList.size() - 1 && position == 0)) {
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    YMDPicerView.this.dayCurrentPosition = position;
                }
                if (position < YMDPicerView.this.dayCurrentPosition || (YMDPicerView.this.dayCurrentPosition == 0 && position == YMDPicerView.this.daysList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(YMDPicerView.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    YMDPicerView.this.dayCurrentPosition = position;
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.year.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    YMDPicerView.this.year.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        this.month.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    YMDPicerView.this.month.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        this.day.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) {
                    YMDPicerView.this.day.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.mOnConfirmListener = onConfirmListener;
    }

    public int getYearCurrentPosition() {
        return this.yearCurrentPosition;
    }

    public int getDayCurrentPosition() {
        return this.dayCurrentPosition;
    }

    public int getMonthCurrentPosition() {
        return this.monthCurrentPosition;
    }
}
