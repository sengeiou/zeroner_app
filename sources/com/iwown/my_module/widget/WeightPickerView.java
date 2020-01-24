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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeightPickerView extends LinearLayout implements OnEndFlingListener {
    private static final String TAG = "WeightPickerView";
    private TextView healthyUnit;
    private CommonAdapter<String> mFractionAdapter;
    List<String> mFractionList = new ArrayList();
    /* access modifiers changed from: private */
    public WheelViewPro mFractionPicker;
    private CommonAdapter<String> mImperialValueAdapter;
    List<String> mImperialValueList = new ArrayList();
    /* access modifiers changed from: private */
    public WheelViewPro mImperialValuePicker;
    private LinearLayout mLayoutImperial;
    private LinearLayout mLayoutMetric;
    private CommonAdapter<String> mMetricValueAdapter;
    List<String> mMetricValueList = new ArrayList();
    /* access modifiers changed from: private */
    public WheelViewPro mMetricValuePicker;
    private CommonAdapter<String> mUnitAdapter;
    private WangWheelView mUnitPicker;
    private float mWeight;
    List<String> mWeightUnitList = new ArrayList();
    private int maxWeightImperial = 1100;
    private int maxWeightMetric = 499;
    private int minWeightImperial = 10;
    private int minWeightMetric = 5;
    private int unitPosition;
    /* access modifiers changed from: private */
    public EnumMeasureUnit unitType;

    public int getUnitPosition() {
        return this.unitPosition;
    }

    public void setUnitPosition(int unitPosition2) {
        this.unitPosition = unitPosition2;
    }

    public WeightPickerView(Context context) {
        super(context);
        init();
    }

    public WeightPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.my_module_view_weight_picker, this);
        this.mLayoutImperial = (LinearLayout) findViewById(R.id.imperial_content);
        this.mLayoutMetric = (LinearLayout) findViewById(R.id.metric_content);
        this.mImperialValuePicker = (WheelViewPro) findViewById(R.id.picker_value_imperial);
        this.mMetricValuePicker = (WheelViewPro) findViewById(R.id.picker_value_metric);
        this.mFractionPicker = (WheelViewPro) findViewById(R.id.picker_fraction);
        this.mUnitPicker = (WangWheelView) findViewById(R.id.picker_unit);
        this.healthyUnit = (TextView) findViewById(R.id.healthy_we_unit);
        setEventProp(this.mImperialValuePicker);
        setEventProp(this.mMetricValuePicker);
        setEventProp(this.mFractionPicker);
        AppConfigUtil.getInstance(getContext());
        if (AppConfigUtil.isHealthy()) {
            this.unitType = EnumMeasureUnit.Metric;
            this.healthyUnit.setVisibility(0);
            this.mUnitPicker.setVisibility(8);
        } else {
            this.unitType = GlobalUserDataFetcher.getPreferredMeasureUnit(getContext());
            Log.i(TAG, String.format("---------------unittype in init:%s", new Object[]{this.unitType.toString()}));
            this.healthyUnit.setVisibility(8);
            this.mUnitPicker.setVisibility(0);
        }
        initData();
    }

    private void setEventProp(WheelViewPro wheelView) {
        wheelView.setOnEndFlingListener(this);
        wheelView.setScrollCycle(true);
        wheelView.setUnselectedAlpha(0.3f);
        wheelView.setOnItemSelectedListener(new WheelViewItemSelectedListener(getContext()));
    }

    public void initData() {
        AppConfigUtil.getInstance(getContext());
        if (AppConfigUtil.isHealthy()) {
            this.mWeightUnitList = Arrays.asList(getResources().getStringArray(R.array.my_module_weight_healthy_unit));
        } else {
            this.mWeightUnitList = Arrays.asList(getResources().getStringArray(R.array.my_module_weight_select_unit));
        }
        this.mUnitPicker.setItems(this.mWeightUnitList);
        this.mUnitPicker.setSeletion(0);
        this.mFractionList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            this.mFractionList.add(String.format(".%d", new Object[]{Integer.valueOf(i)}));
        }
        this.mFractionAdapter = new CommonAdapter<String>(getContext(), this.mFractionList, R.layout.my_module_wheelview_item_layout) {
            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) WeightPickerView.this.mFractionList.get(position));
                if (position == WeightPickerView.this.mFractionPicker.getSelectedItemPosition()) {
                    tv.setTextColor(WeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_text_color));
                } else {
                    tv.setTextColor(WeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_unselected_color));
                }
            }
        };
        this.mFractionPicker.setAdapter((SpinnerAdapter) this.mFractionAdapter);
        this.mUnitPicker.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                int selectedIndex2 = selectedIndex - 1;
                WeightPickerView.this.setUnitPosition(selectedIndex2);
                if (selectedIndex2 != WeightPickerView.this.unitType.ordinal()) {
                    WeightPickerView.this.unitType = EnumMeasureUnit.values()[selectedIndex2];
                    WeightPickerView.this.setValuePickerByWeight();
                }
            }
        });
        this.mMetricValueList = new ArrayList();
        for (int i2 = this.minWeightMetric; i2 <= this.maxWeightMetric; i2++) {
            this.mMetricValueList.add(String.valueOf(i2));
        }
        this.mImperialValueList = new ArrayList();
        for (int i3 = this.minWeightImperial; i3 <= this.maxWeightImperial; i3++) {
            this.mImperialValueList.add(String.valueOf(i3));
        }
        this.mMetricValueAdapter = new CommonAdapter<String>(getContext(), this.mMetricValueList, R.layout.my_module_wheelview_item_layout) {
            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) WeightPickerView.this.mMetricValueList.get(position));
                if (position == WeightPickerView.this.mMetricValuePicker.getSelectedItemPosition()) {
                    tv.setTextColor(WeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_text_color));
                } else {
                    tv.setTextColor(WeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_unselected_color));
                }
            }
        };
        this.mMetricValuePicker.setAdapter((SpinnerAdapter) this.mMetricValueAdapter);
        this.mImperialValueAdapter = new CommonAdapter<String>(getContext(), this.mImperialValueList, R.layout.my_module_wheelview_item_layout) {
            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) WeightPickerView.this.mImperialValueList.get(position));
                if (position == WeightPickerView.this.mImperialValuePicker.getSelectedItemPosition()) {
                    tv.setTextColor(WeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_text_color));
                } else {
                    tv.setTextColor(WeightPickerView.this.getContext().getResources().getColor(R.color.dark_theme_unselected_color));
                }
            }
        };
        this.mImperialValuePicker.setAdapter((SpinnerAdapter) this.mImperialValueAdapter);
        if (this.unitType == EnumMeasureUnit.Imperial) {
            this.mLayoutImperial.setVisibility(0);
            this.mLayoutMetric.setVisibility(8);
            this.mUnitPicker.setSeletion(0);
            return;
        }
        this.mLayoutImperial.setVisibility(8);
        this.mLayoutMetric.setVisibility(0);
        this.mUnitPicker.setSeletion(1);
    }

    public void onEndFling(TosGallery v) {
        int position = v.getSelectedItemPosition();
        if (position < 0) {
            position = 0;
        }
        int i = v.getId();
        if (i != R.id.picker_unit) {
            if (i == R.id.picker_fraction) {
                if (this.unitType == EnumMeasureUnit.Imperial) {
                    float lbs = ((float) (this.mImperialValuePicker.getSelectedItemPosition() + this.minWeightImperial)) + (((float) position) * 0.1f);
                    Log.i(TAG, String.format("lbs out: %f", new Object[]{Float.valueOf(lbs)}));
                    this.mWeight = MeasureTransform.lbs2Kg(lbs);
                    Log.i(TAG, String.format("kg out: %f", new Object[]{Float.valueOf(this.mWeight)}));
                    return;
                }
                this.mWeight = ((float) (this.mMetricValuePicker.getSelectedItemPosition() + this.minWeightMetric)) + (((float) position) * 0.1f);
            } else if (i == R.id.picker_value_imperial) {
                this.mWeight = MeasureTransform.lbs2Kg(((float) (this.minWeightImperial + position)) + (((float) this.mFractionPicker.getSelectedItemPosition()) * 0.1f));
            } else if (i == R.id.picker_value_metric) {
                this.mWeight = ((float) (this.minWeightMetric + position)) + (((float) this.mFractionPicker.getSelectedItemPosition()) * 0.1f);
            }
        }
    }

    public float getWeight() {
        return this.mWeight;
    }

    public void setWeight(float weight) {
        this.mWeight = weight;
        setValuePickerByWeight();
    }

    /* access modifiers changed from: 0000 */
    public void setValuePickerByWeight() {
        if (this.unitType == EnumMeasureUnit.Imperial) {
            this.mLayoutImperial.setVisibility(0);
            this.mLayoutMetric.setVisibility(8);
            this.mUnitPicker.setSeletion(0);
            setUnitPosition(0);
            float lbs = MeasureTransform.kg2Lbs(this.mWeight);
            int intPart = (int) lbs;
            int fraction = Math.round((lbs - ((float) intPart)) * 10.0f);
            if (fraction > 9 || fraction < 0) {
                this.mFractionPicker.setSelection(0);
            } else {
                this.mFractionPicker.setSelection(fraction);
            }
            if (intPart < this.minWeightImperial) {
                this.mImperialValuePicker.setSelection(0);
            } else if (intPart > this.maxWeightImperial) {
                this.mImperialValuePicker.setSelection(this.maxWeightImperial - this.minWeightImperial);
            } else {
                this.mImperialValuePicker.setSelection(intPart - this.minWeightImperial);
            }
        } else {
            this.mLayoutImperial.setVisibility(8);
            this.mLayoutMetric.setVisibility(0);
            this.mUnitPicker.setSeletion(1);
            setUnitPosition(1);
            int intPart2 = (int) this.mWeight;
            int fraction2 = Math.round((this.mWeight - ((float) intPart2)) * 10.0f);
            if (fraction2 > 9 || fraction2 < 0) {
                this.mFractionPicker.setSelection(0);
            } else {
                this.mFractionPicker.setSelection(fraction2);
            }
            if (intPart2 < this.minWeightMetric) {
                this.mMetricValuePicker.setSelection(0);
            } else if (intPart2 > this.maxWeightMetric) {
                this.mMetricValuePicker.setSelection(this.maxWeightMetric - this.minWeightMetric);
            } else {
                this.mMetricValuePicker.setSelection(intPart2 - this.minWeightMetric);
            }
        }
    }
}
