package com.iwown.my_module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.CommonAdapter;
import com.iwown.my_module.common.ViewHolder;
import com.iwown.my_module.protocol.WheelViewItemSelectedListener;
import com.iwown.my_module.utility.MeasureTransform;
import com.iwown.my_module.widget.TosGallery.OnEndFlingListener;
import com.iwown.my_module.widget.WangWheelView.OnWheelViewListener;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeightPickerView extends LinearLayout implements OnEndFlingListener {
    private static final String TAG = "HeightDialogView";
    private TextView healthyUnit;
    /* access modifiers changed from: private */
    public float mHeight;
    private CommonAdapter<String> mImperialValueAdapter;
    /* access modifiers changed from: private */
    public List<String> mImperialValueList;
    /* access modifiers changed from: private */
    public LinearLayout mLayoutImperial;
    /* access modifiers changed from: private */
    public LinearLayout mLayoutMetric;
    private CommonAdapter<String> mMetricValueAdapter;
    /* access modifiers changed from: private */
    public List<String> mMetricValueList;
    private WangWheelView mPickerUnit;
    /* access modifiers changed from: private */
    public WheelViewPro mPickerValueImperial;
    /* access modifiers changed from: private */
    public WheelViewPro mPickerValueMetric;
    private List<String> mUnitList;
    /* access modifiers changed from: private */
    public int maxHeightImperial = 107;
    /* access modifiers changed from: private */
    public int maxHeightMetric = 249;
    /* access modifiers changed from: private */
    public int minHeightImperial = 13;
    /* access modifiers changed from: private */
    public int minHeightMetric = 51;
    private int unitPosition;
    /* access modifiers changed from: private */
    public EnumMeasureUnit unitType;

    public EnumMeasureUnit getUnitType() {
        return this.unitType;
    }

    public void setUnitType(EnumMeasureUnit unitType2) {
        this.unitType = unitType2;
    }

    public HeightPickerView(Context context) {
        super(context);
        init();
    }

    public HeightPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int getUnitPosition() {
        return this.unitPosition;
    }

    public void setUnitPosition(int unitPosition2) {
        this.unitPosition = unitPosition2;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.my_module_view_height_picker, this);
        this.mLayoutImperial = (LinearLayout) findViewById(R.id.imperial_content);
        this.mLayoutMetric = (LinearLayout) findViewById(R.id.metric_content);
        this.mPickerUnit = (WangWheelView) findViewById(R.id.picker_unit);
        this.mPickerValueImperial = (WheelViewPro) findViewById(R.id.picker_value_imperial);
        this.mPickerValueMetric = (WheelViewPro) findViewById(R.id.picker_value_metric);
        this.healthyUnit = (TextView) findViewById(R.id.healthy_hi_unit);
        setEventProp(this.mPickerValueMetric);
        setEventProp(this.mPickerValueImperial);
        AppConfigUtil.getInstance(getContext());
        if (AppConfigUtil.isHealthy()) {
            this.unitType = EnumMeasureUnit.Metric;
            this.healthyUnit.setVisibility(0);
            this.mPickerUnit.setVisibility(8);
            KLog.i("============" + this.unitType);
        } else {
            this.unitType = GlobalUserDataFetcher.getPreferredMeasureUnit(getContext());
            Log.i(TAG, String.format("---------------unittype in init:%s", new Object[]{this.unitType.toString()}));
            this.healthyUnit.setVisibility(8);
            this.mPickerUnit.setVisibility(0);
        }
        initData();
        this.mPickerUnit.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                int selectedIndex2 = selectedIndex - 1;
                HeightPickerView.this.setUnitPosition(selectedIndex2);
                if (selectedIndex2 != HeightPickerView.this.unitType.ordinal()) {
                    HeightPickerView.this.unitType = EnumMeasureUnit.values()[selectedIndex2];
                    if (HeightPickerView.this.unitType == EnumMeasureUnit.Imperial) {
                        HeightPickerView.this.mLayoutImperial.setVisibility(0);
                        HeightPickerView.this.mLayoutMetric.setVisibility(8);
                        int inch = (int) MeasureTransform.cm2Inch(HeightPickerView.this.mHeight);
                        if (inch < HeightPickerView.this.minHeightImperial) {
                            HeightPickerView.this.mPickerValueImperial.setSelection(0);
                        } else if (inch > HeightPickerView.this.maxHeightImperial) {
                            HeightPickerView.this.mPickerValueImperial.setSelection(HeightPickerView.this.maxHeightImperial - HeightPickerView.this.minHeightImperial);
                        } else {
                            HeightPickerView.this.mPickerValueImperial.setSelection(inch - HeightPickerView.this.minHeightImperial);
                        }
                    } else {
                        HeightPickerView.this.mLayoutImperial.setVisibility(8);
                        HeightPickerView.this.mLayoutMetric.setVisibility(0);
                        int height = (int) HeightPickerView.this.mHeight;
                        if (height < HeightPickerView.this.minHeightMetric) {
                            HeightPickerView.this.mPickerValueMetric.setSelection(0);
                        } else if (height > HeightPickerView.this.maxHeightMetric) {
                            HeightPickerView.this.mPickerValueMetric.setSelection(HeightPickerView.this.maxHeightMetric - HeightPickerView.this.minHeightMetric);
                        } else {
                            HeightPickerView.this.mPickerValueMetric.setSelection(height - HeightPickerView.this.minHeightMetric);
                        }
                    }
                }
            }
        });
    }

    private void setEventProp(WheelViewPro wheelView) {
        wheelView.setOnEndFlingListener(this);
        wheelView.setScrollCycle(true);
        wheelView.setUnselectedAlpha(0.2f);
        wheelView.setOnItemSelectedListener(new WheelViewItemSelectedListener(getContext()));
    }

    public void initData() {
        this.mUnitList = Arrays.asList(getResources().getStringArray(R.array.my_module_height_select_unit));
        this.mPickerUnit.setItems(this.mUnitList);
        this.mPickerUnit.setSeletion(0);
        this.mImperialValueList = new ArrayList();
        for (int i = this.minHeightImperial; i <= this.maxHeightImperial; i++) {
            int feet = i / 12;
            int inch = i % 12;
            this.mImperialValueList.add(String.format("%d'%d''", new Object[]{Integer.valueOf(feet), Integer.valueOf(inch)}));
        }
        this.mMetricValueList = new ArrayList();
        for (int i2 = this.minHeightMetric; i2 <= this.maxHeightMetric; i2++) {
            this.mMetricValueList.add(String.valueOf(i2));
        }
        this.mMetricValueAdapter = new CommonAdapter<String>(getContext(), this.mMetricValueList, R.layout.my_module_wheelview_item_layout) {
            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) HeightPickerView.this.mMetricValueList.get(position));
                if (position == HeightPickerView.this.mPickerValueMetric.getSelectedItemPosition()) {
                    tv.setTextColor(HeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_text_color));
                } else {
                    tv.setTextColor(HeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_unselected_color));
                }
            }
        };
        this.mPickerValueMetric.setAdapter((SpinnerAdapter) this.mMetricValueAdapter);
        this.mPickerValueMetric.setSelection(0);
        this.mImperialValueAdapter = new CommonAdapter<String>(getContext(), this.mImperialValueList, R.layout.my_module_wheelview_item_layout) {
            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) HeightPickerView.this.mImperialValueList.get(position));
                if (position == HeightPickerView.this.mPickerValueImperial.getSelectedItemPosition()) {
                    tv.setTextColor(HeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_text_color));
                } else {
                    tv.setTextColor(HeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_unselected_color));
                }
            }
        };
        this.mPickerValueImperial.setAdapter((SpinnerAdapter) this.mImperialValueAdapter);
        this.mPickerValueImperial.setSelection(0);
        if (this.unitType == EnumMeasureUnit.Imperial) {
            this.mLayoutImperial.setVisibility(0);
            this.mLayoutMetric.setVisibility(8);
            return;
        }
        this.mLayoutImperial.setVisibility(8);
        this.mLayoutMetric.setVisibility(0);
    }

    public void onEndFling(TosGallery v) {
        int position = v.getSelectedItemPosition();
        Log.i(TAG, String.format("onEndFling position:%d", new Object[]{Integer.valueOf(position)}));
        if (position < 0) {
            position = 0;
        }
        int i = v.getId();
        if (i == R.id.picker_unit) {
            if (position >= EnumMeasureUnit.values().length) {
                position = 0;
            }
            if (position != this.unitType.ordinal()) {
                this.unitType = EnumMeasureUnit.values()[position];
                if (this.unitType == EnumMeasureUnit.Imperial) {
                    this.mLayoutImperial.setVisibility(0);
                    this.mLayoutMetric.setVisibility(8);
                    int inch = (int) MeasureTransform.cm2Inch(this.mHeight);
                    if (inch < this.minHeightImperial) {
                        this.mPickerValueImperial.setSelection(0);
                    } else if (inch > this.maxHeightImperial) {
                        this.mPickerValueImperial.setSelection(this.maxHeightImperial - this.minHeightImperial);
                    } else {
                        this.mPickerValueImperial.setSelection(inch - this.minHeightImperial);
                    }
                } else {
                    this.mLayoutImperial.setVisibility(8);
                    this.mLayoutMetric.setVisibility(0);
                    int height = (int) this.mHeight;
                    if (height < this.minHeightMetric) {
                        this.mPickerValueMetric.setSelection(0);
                    } else if (height > this.maxHeightMetric) {
                        this.mPickerValueMetric.setSelection(this.maxHeightMetric - this.minHeightMetric);
                    } else {
                        this.mPickerValueMetric.setSelection(height - this.minHeightMetric);
                    }
                }
            }
        } else if (i == R.id.picker_value_imperial) {
            this.mHeight = MeasureTransform.inch2Cm(this.minHeightImperial + position);
        } else if (i == R.id.picker_value_metric) {
            this.mHeight = (float) (this.minHeightMetric + position);
        }
    }

    public float getCurrHeight() {
        return this.mHeight;
    }

    public void setCurrHeight(float curHeight) {
        Log.i(TAG, String.format("--------------set height : %f, unittype:%s", new Object[]{Float.valueOf(curHeight), this.unitType.toString()}));
        this.mHeight = curHeight;
        if (this.unitType == EnumMeasureUnit.Imperial) {
            this.mLayoutImperial.setVisibility(0);
            this.mLayoutMetric.setVisibility(8);
            int inch = (int) MeasureTransform.cm2Inch(this.mHeight);
            if (inch < this.minHeightImperial) {
                this.mPickerValueImperial.setSelection(0);
            } else if (inch > this.maxHeightImperial) {
                this.mPickerValueImperial.setSelection(this.maxHeightImperial - this.minHeightImperial);
            } else {
                this.mPickerValueImperial.setSelection(inch - this.minHeightImperial);
            }
            this.mPickerUnit.setSeletion(0);
            setUnitPosition(0);
            return;
        }
        this.mLayoutImperial.setVisibility(8);
        this.mLayoutMetric.setVisibility(0);
        int height = (int) this.mHeight;
        if (height < this.minHeightMetric) {
            this.mPickerValueMetric.setSelection(0);
        } else if (height > this.maxHeightMetric) {
            this.mPickerValueMetric.setSelection(this.maxHeightMetric - this.minHeightMetric);
        } else {
            this.mPickerValueMetric.setSelection(height - this.minHeightMetric);
        }
        this.mPickerUnit.setSeletion(1);
        setUnitPosition(1);
    }
}
