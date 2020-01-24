package com.iwown.my_module.useractivity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.utility.MeasureTransform;
import com.iwown.my_module.widget.WangWheelView;
import com.iwown.my_module.widget.WangWheelView.OnWheelViewListener;
import java.util.ArrayList;
import java.util.List;

public class NewProfileWeightActivity extends BaseActivity implements OnClickListener {
    public static final int WEIGHT_RESULT_CODE = 4;
    public static final String WEIGHT_VALUE = "weight_value";
    LinearLayout birthdayLineOff;
    LinearLayout birthdayLineOn;
    LinearLayout birthdayOff;
    LinearLayout birthdayOn;
    boolean cnPickerInit = false;
    boolean enPickerInit = false;
    WangWheelView factionPicker;
    LinearLayout fractionPickerLayout;
    WangWheelView intPickerCn;
    WangWheelView intPickerEn;
    LinearLayout intPickerLayoutCn;
    LinearLayout intPickerLayoutEn;
    /* access modifiers changed from: private */
    public boolean isHealthy = false;
    Button mBtnNext;
    int mGender;
    float mHeight;
    float mWeight;
    int status;
    WangWheelView unitPicker;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_newprofile_weight);
        setTitleText(R.string.create_profile_title);
        setLeftBackTo();
        this.isHealthy = AppConfigUtil.isHealthy();
        this.intPickerCn = (WangWheelView) findViewById(R.id.weight_integer_picker_cn);
        this.intPickerEn = (WangWheelView) findViewById(R.id.weight_integer_picker_en);
        this.factionPicker = (WangWheelView) findViewById(R.id.weight_fraction_picker);
        this.unitPicker = (WangWheelView) findViewById(R.id.unit_picker);
        this.mBtnNext = (Button) findViewById(R.id.btn_next);
        this.mBtnNext.setOnClickListener(this);
        this.birthdayLineOff = (LinearLayout) findViewById(R.id.birthday_line_off_img);
        this.birthdayLineOn = (LinearLayout) findViewById(R.id.birthday_line_on_img);
        this.birthdayOff = (LinearLayout) findViewById(R.id.birthday_off_img);
        this.birthdayOn = (LinearLayout) findViewById(R.id.birthday_on_img);
        this.intPickerLayoutCn = (LinearLayout) findViewById(R.id.weight_integer_layout_cn);
        this.fractionPickerLayout = (LinearLayout) findViewById(R.id.weight_fraction_layout);
        this.intPickerLayoutEn = (LinearLayout) findViewById(R.id.weight_integer_layout_en);
        Intent intent = getIntent();
        if (intent != null) {
            this.status = intent.getIntExtra(UserConst.PROFILE_VIEW_STATUS, 1);
        } else {
            this.status = 1;
        }
        if (this.status == 1) {
            this.mGender = intent.getIntExtra(NewProfileGenderActivity.GENDER_VALUE, 1);
            this.mHeight = intent.getFloatExtra(NewProfileHeightActivity.HEIGHT_VALUE, 0.0f);
            if (this.mGender == 1) {
                this.mWeight = 70.0f;
            } else {
                this.mWeight = 50.0f;
            }
        } else {
            this.mWeight = intent.getFloatExtra(WEIGHT_VALUE, 0.0f);
        }
        initView();
    }

    public void onClick(View view) {
        if (view.getId() != R.id.btn_next) {
            return;
        }
        if (this.status == 1) {
            Intent intent = new Intent(this, NewProfileBirthdayActivity.class);
            intent.putExtra(WEIGHT_VALUE, this.mWeight);
            intent.putExtra(NewProfileHeightActivity.HEIGHT_VALUE, this.mHeight);
            intent.putExtra(NewProfileGenderActivity.GENDER_VALUE, this.mGender);
            intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.putExtra(WEIGHT_VALUE, this.mWeight);
        setResult(4, intent2);
        finish();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        EnumMeasureUnit unit = GlobalUserDataFetcher.getPreferredMeasureUnit(this);
        this.factionPicker.setOffset(1);
        List<String> fractionList = new ArrayList<>();
        for (int f = 0; f < 10; f++) {
            fractionList.add(String.format(".%d", new Object[]{Integer.valueOf(f)}));
        }
        this.factionPicker.setItems(fractionList);
        this.factionPicker.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                if (NewProfileWeightActivity.this.isHealthy) {
                    NewProfileWeightActivity.this.mWeight = ((float) (NewProfileWeightActivity.this.intPickerCn.getSeletedIndex() + 5)) + (((float) (selectedIndex - 1)) * 0.1f);
                } else if (NewProfileWeightActivity.this.unitPicker.getSeletedIndex() == 0) {
                    float lbs = ((float) (NewProfileWeightActivity.this.intPickerEn.getSeletedIndex() + 10)) + (((float) (selectedIndex - 1)) * 0.1f);
                    NewProfileWeightActivity.this.mWeight = MeasureTransform.lbs2Kg(lbs);
                } else {
                    NewProfileWeightActivity.this.mWeight = ((float) (NewProfileWeightActivity.this.intPickerCn.getSeletedIndex() + 5)) + (((float) (selectedIndex - 1)) * 0.1f);
                }
            }
        });
        if (unit == EnumMeasureUnit.Imperial) {
            initEnWeightPicker((double) this.mWeight);
        } else {
            initCnWeightPicker((double) this.mWeight);
        }
        if (this.isHealthy) {
            this.unitPicker.setOffset(0);
        } else {
            this.unitPicker.setOffset(1);
        }
        List<String> unitList = new ArrayList<>();
        if (!this.isHealthy) {
            unitList.add("lbs");
        }
        unitList.add("kg");
        this.unitPicker.setItems(unitList);
        if (!this.isHealthy) {
            this.unitPicker.setOnWheelViewListener(new OnWheelViewListener() {
                public void onSelected(int selectedIndex, String item) {
                    if (selectedIndex == 1) {
                        if (NewProfileWeightActivity.this.enPickerInit) {
                            NewProfileWeightActivity.this.intPickerLayoutEn.setVisibility(0);
                            NewProfileWeightActivity.this.intPickerLayoutCn.setVisibility(8);
                            NewProfileWeightActivity.this.setEnPickerDisplay((double) NewProfileWeightActivity.this.mWeight);
                        } else {
                            NewProfileWeightActivity.this.initEnWeightPicker((double) NewProfileWeightActivity.this.mWeight);
                        }
                        GlobalDataUpdater.setMeasureUnit(NewProfileWeightActivity.this, EnumMeasureUnit.Imperial);
                        return;
                    }
                    if (NewProfileWeightActivity.this.cnPickerInit) {
                        NewProfileWeightActivity.this.intPickerLayoutEn.setVisibility(8);
                        NewProfileWeightActivity.this.intPickerLayoutCn.setVisibility(0);
                        NewProfileWeightActivity.this.setCnPickerDisplay((double) NewProfileWeightActivity.this.mWeight);
                    } else {
                        NewProfileWeightActivity.this.initCnWeightPicker((double) NewProfileWeightActivity.this.mWeight);
                    }
                    GlobalDataUpdater.setMeasureUnit(NewProfileWeightActivity.this, EnumMeasureUnit.Metric);
                }
            });
        }
        if (unit == EnumMeasureUnit.Imperial) {
            this.unitPicker.setSeletion(0);
        } else {
            this.unitPicker.setSeletion(1);
        }
        if (this.status == 1) {
            this.birthdayLineOff.setVisibility(0);
            this.birthdayOff.setVisibility(0);
            this.birthdayLineOn.setVisibility(8);
            this.birthdayOn.setVisibility(8);
            return;
        }
        this.birthdayLineOn.setVisibility(0);
        this.birthdayOn.setVisibility(0);
        this.birthdayLineOff.setVisibility(8);
        this.birthdayOff.setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public void initEnWeightPicker(double weight) {
        this.intPickerLayoutEn.setVisibility(0);
        this.intPickerLayoutCn.setVisibility(8);
        this.intPickerEn.setOffset(1);
        List<String> intList = new ArrayList<>();
        for (int i = 10; i < 1100; i++) {
            intList.add(String.valueOf(i));
        }
        this.intPickerEn.setItems(intList);
        this.intPickerEn.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                float lbs = ((float) ((selectedIndex - 1) + 10)) + (0.1f * ((float) NewProfileWeightActivity.this.factionPicker.getSeletedIndex()));
                NewProfileWeightActivity.this.mWeight = MeasureTransform.lbs2Kg(lbs);
            }
        });
        setEnPickerDisplay(weight);
        this.enPickerInit = true;
    }

    /* access modifiers changed from: 0000 */
    public void initCnWeightPicker(double weight) {
        this.intPickerLayoutEn.setVisibility(8);
        this.intPickerLayoutCn.setVisibility(0);
        this.intPickerCn.setOffset(1);
        List<String> intList = new ArrayList<>();
        for (int i = 5; i < 500; i++) {
            intList.add(String.valueOf(i));
        }
        this.intPickerCn.setItems(intList);
        this.intPickerCn.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                NewProfileWeightActivity.this.mWeight = ((float) ((selectedIndex - 1) + 5)) + (0.1f * ((float) NewProfileWeightActivity.this.factionPicker.getSeletedIndex()));
            }
        });
        setCnPickerDisplay(weight);
        this.cnPickerInit = true;
    }

    /* access modifiers changed from: 0000 */
    public void setEnPickerDisplay(double weight) {
        double lb = MeasureTransform.kg2lb(weight);
        int intPart = (int) lb;
        int fraction = (int) ((lb - ((double) intPart)) / 0.1d);
        if (intPart > 1099) {
            this.intPickerEn.setSeletion(1089);
            this.factionPicker.setSeletion(0);
        } else if (intPart < 10) {
            this.intPickerEn.setSeletion(0);
            this.factionPicker.setSeletion(0);
        } else {
            this.intPickerEn.setSeletion(intPart - 10);
            this.factionPicker.setSeletion(fraction);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCnPickerDisplay(double weight) {
        int intPart = (int) weight;
        int fraction = (int) ((weight - ((double) intPart)) / 0.1d);
        if (intPart > 499) {
            this.intPickerCn.setSeletion(494);
            this.factionPicker.setSeletion(0);
        } else if (intPart < 5) {
            this.intPickerCn.setSeletion(0);
            this.factionPicker.setSeletion(0);
        } else {
            this.intPickerCn.setSeletion(intPart - 5);
            this.factionPicker.setSeletion(fraction);
        }
    }
}
