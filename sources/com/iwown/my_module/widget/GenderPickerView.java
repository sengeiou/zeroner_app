package com.iwown.my_module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.iwown.my_module.R;
import com.iwown.my_module.common.CommonAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenderPickerView extends LinearLayout {
    private static final String TAG = "GenderPickerView";
    private int mGender;
    private CommonAdapter<String> mGenderAdapter;
    List<String> mGenderList = new ArrayList();
    private WangWheelView mGenderWeelView;
    private View mLastViewSelected;

    public GenderPickerView(Context context) {
        super(context);
        init();
    }

    public GenderPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.my_module_view_gender_picker, this);
        this.mGenderWeelView = (WangWheelView) findViewById(R.id.picker_gender);
        initData();
    }

    public void initData() {
        prepareData();
    }

    private void prepareData() {
        this.mGenderList = Arrays.asList(getResources().getStringArray(R.array.my_module_gender_select_items));
        this.mGenderWeelView.setItems(this.mGenderList);
        if (this.mGender == 1) {
            this.mGenderWeelView.setSeletion(1);
        } else {
            this.mGenderWeelView.setSeletion(0);
        }
        this.mGenderWeelView.setVisibility(0);
    }

    public int getGender() {
        if (this.mGenderWeelView.getSeletedIndex() == 0) {
            this.mGender = 2;
        } else {
            this.mGender = 1;
        }
        return this.mGender;
    }

    public void setGender(int gender) {
        this.mGender = gender;
        if (gender == 2) {
            this.mGenderWeelView.setSeletion(0);
        } else {
            this.mGenderWeelView.setSeletion(this.mGender);
        }
    }
}
