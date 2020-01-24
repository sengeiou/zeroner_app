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
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.List;

public class NewProfileHeightActivity extends BaseActivity implements OnClickListener {
    public static final int HEIGHT_RESULT_CODE = 3;
    public static final String HEIGHT_VALUE = "height_value";
    private static final String TAG = "HeightActivity";
    LinearLayout birthdayLineOff;
    LinearLayout birthdayLineOn;
    LinearLayout birthdayOff;
    LinearLayout birthdayOn;
    Button btnNext;
    boolean cnPickerInit = false;
    LinearLayout cnPickerLayout;
    boolean enPickerInit = false;
    LinearLayout enPickerLayout;
    WangWheelView heightPickerCn;
    WangWheelView heightPickerEn;
    int mGender;
    float mHeight;
    int status;
    WangWheelView unitPicker;
    LinearLayout weightLineOff;
    LinearLayout weightLineOn;
    LinearLayout weightOff;
    LinearLayout weightOn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_newprofile_height);
        setTitleText(R.string.create_profile_title);
        setLeftBackTo();
        if (GlobalUserDataFetcher.getUnitSetStatus(this) != 1) {
            GlobalDataUpdater.setMeasureUnit(this, EnumMeasureUnit.Metric);
        }
        Intent intent = getIntent();
        if (intent != null) {
            this.status = intent.getIntExtra(UserConst.PROFILE_VIEW_STATUS, 1);
        } else {
            this.status = 1;
        }
        if (this.status == 1) {
            this.mGender = intent.getIntExtra(NewProfileGenderActivity.GENDER_VALUE, 1);
            if (this.mGender == 1) {
                this.mHeight = 175.0f;
            } else {
                this.mHeight = 165.0f;
            }
        } else {
            this.mHeight = intent.getFloatExtra(HEIGHT_VALUE, 0.0f);
        }
        this.heightPickerEn = (WangWheelView) findViewById(R.id.height_picker_en);
        this.heightPickerCn = (WangWheelView) findViewById(R.id.height_picker_cn);
        this.unitPicker = (WangWheelView) findViewById(R.id.unit_picker);
        this.btnNext = (Button) findViewById(R.id.btn_next);
        this.btnNext.setOnClickListener(this);
        this.weightLineOff = (LinearLayout) findViewById(R.id.weight_line_off_img);
        this.weightLineOn = (LinearLayout) findViewById(R.id.weight_line_on_img);
        this.weightOff = (LinearLayout) findViewById(R.id.weight_off_img);
        this.weightOn = (LinearLayout) findViewById(R.id.weight_on_img);
        this.birthdayLineOff = (LinearLayout) findViewById(R.id.birthday_line_off_img);
        this.birthdayLineOn = (LinearLayout) findViewById(R.id.birthday_line_on_img);
        this.birthdayOff = (LinearLayout) findViewById(R.id.birthday_off_img);
        this.birthdayOn = (LinearLayout) findViewById(R.id.birthday_on_img);
        this.enPickerLayout = (LinearLayout) findViewById(R.id.en_layout);
        this.cnPickerLayout = (LinearLayout) findViewById(R.id.cn_layout);
        initView();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            GlobalDataUpdater.setUnitSetStatus(this, 1);
            if (this.status == 1) {
                Intent intent = new Intent(this, NewProfileWeightActivity.class);
                intent.putExtra(HEIGHT_VALUE, this.mHeight);
                intent.putExtra(NewProfileGenderActivity.GENDER_VALUE, this.mGender);
                intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
                startActivity(intent);
                return;
            }
            Intent intent2 = new Intent();
            intent2.putExtra(HEIGHT_VALUE, this.mHeight);
            setResult(3, intent2);
            finish();
        }
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        EnumMeasureUnit unit = GlobalUserDataFetcher.getPreferredMeasureUnit(this);
        if (unit == EnumMeasureUnit.Imperial) {
            initEnHeightPicker((double) this.mHeight);
        } else {
            initCnHeightPicker((double) this.mHeight);
        }
        this.unitPicker.setOffset(1);
        List<String> unitList = new ArrayList<>();
        AppConfigUtil.getInstance(this);
        if (!AppConfigUtil.isHealthy()) {
            unitList.add("ft in");
        }
        unitList.add("cm");
        this.unitPicker.setItems(unitList);
        if (unit == EnumMeasureUnit.Metric) {
            if (AppConfigUtil.isHealthy()) {
                this.unitPicker.setSeletion(0);
            } else {
                this.unitPicker.setSeletion(1);
            }
        }
        if (!AppConfigUtil.isHealthy()) {
            this.unitPicker.setOnWheelViewListener(new OnWheelViewListener() {
                public void onSelected(int selectedIndex, String item) {
                    if (selectedIndex == 1) {
                        if (NewProfileHeightActivity.this.enPickerInit) {
                            NewProfileHeightActivity.this.enPickerLayout.setVisibility(0);
                            NewProfileHeightActivity.this.cnPickerLayout.setVisibility(8);
                            NewProfileHeightActivity.this.setEnHeightPickerDisplay((double) NewProfileHeightActivity.this.mHeight);
                        } else {
                            NewProfileHeightActivity.this.initEnHeightPicker((double) NewProfileHeightActivity.this.mHeight);
                        }
                        GlobalDataUpdater.setMeasureUnit(NewProfileHeightActivity.this, EnumMeasureUnit.Imperial);
                    } else if (selectedIndex == 2) {
                        if (NewProfileHeightActivity.this.cnPickerInit) {
                            NewProfileHeightActivity.this.cnPickerLayout.setVisibility(0);
                            NewProfileHeightActivity.this.enPickerLayout.setVisibility(8);
                            NewProfileHeightActivity.this.setCnHeightPickerDisplay((double) NewProfileHeightActivity.this.mHeight);
                        } else {
                            NewProfileHeightActivity.this.initCnHeightPicker((double) NewProfileHeightActivity.this.mHeight);
                        }
                        GlobalDataUpdater.setMeasureUnit(NewProfileHeightActivity.this, EnumMeasureUnit.Metric);
                    }
                }
            });
        }
        if (this.status == 1) {
            this.weightLineOff.setVisibility(0);
            this.weightOff.setVisibility(0);
            this.birthdayLineOff.setVisibility(0);
            this.birthdayOff.setVisibility(0);
            this.weightLineOn.setVisibility(8);
            this.weightOn.setVisibility(8);
            this.birthdayLineOn.setVisibility(8);
            this.birthdayOn.setVisibility(8);
            return;
        }
        this.weightLineOn.setVisibility(0);
        this.weightOn.setVisibility(0);
        this.birthdayLineOn.setVisibility(0);
        this.birthdayOn.setVisibility(0);
        this.weightLineOff.setVisibility(8);
        this.weightOff.setVisibility(8);
        this.birthdayLineOff.setVisibility(8);
        this.birthdayOff.setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public void initEnHeightPicker(double height) {
        this.enPickerLayout.setVisibility(0);
        this.cnPickerLayout.setVisibility(8);
        List<String> heightList = new ArrayList<>();
        for (int i = 20; i < 100; i++) {
            heightList.add(String.format("%d'%d\"", new Object[]{Integer.valueOf(i / 12), Integer.valueOf(i % 12)}));
        }
        this.heightPickerEn.setItems(heightList);
        this.heightPickerEn.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                int inch = (selectedIndex + 20) - 1;
                NewProfileHeightActivity.this.mHeight = MeasureTransform.inch2Cm(inch);
            }
        });
        setEnHeightPickerDisplay(height);
        this.enPickerInit = true;
    }

    /* access modifiers changed from: 0000 */
    public void setEnHeightPickerDisplay(double height) {
        int inch = (int) MeasureTransform.cm2Inch(height);
        if (inch < 20) {
            this.heightPickerEn.setSeletion(0);
        } else if (inch > 99) {
            this.heightPickerEn.setSeletion(79);
        } else {
            this.heightPickerEn.setSeletion(inch - 20);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCnHeightPickerDisplay(double height) {
        if (height < 50.0d) {
            this.heightPickerCn.setSeletion(0);
        } else if (height > 249.0d) {
            this.heightPickerCn.setSeletion(Opcodes.SUB_FLOAT_2ADDR);
        } else {
            this.heightPickerCn.setSeletion(((int) height) - 50);
        }
    }

    /* access modifiers changed from: 0000 */
    public void initCnHeightPicker(double height) {
        this.cnPickerLayout.setVisibility(0);
        this.enPickerLayout.setVisibility(8);
        List<String> heightList = new ArrayList<>();
        for (int i = 50; i < 250; i++) {
            heightList.add(String.valueOf(i));
        }
        this.heightPickerCn.setItems(heightList);
        this.heightPickerCn.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                NewProfileHeightActivity.this.mHeight = (float) ((selectedIndex + 50) - 1);
            }
        });
        setCnHeightPickerDisplay(height);
        this.cnPickerInit = true;
    }
}
