package com.iwown.my_module.useractivity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.iwown.data_link.consts.UserConst;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.widget.WangWheelView;
import java.util.ArrayList;
import java.util.List;

public class NewProfileGenderActivity extends BaseActivity implements OnClickListener {
    public static final int GENDER_RESULT_CODE = 2;
    public static final String GENDER_VALUE = "gender_value";
    private static final String TAG = "GenderActivity";
    LinearLayout birthdayLineOff;
    LinearLayout birthdayLineOn;
    LinearLayout birthdayOff;
    LinearLayout birthdayOn;
    Button btnNext;
    WangWheelView genderPicker;
    LinearLayout heightLineOff;
    LinearLayout heightLineOn;
    LinearLayout heightOff;
    LinearLayout heightOn;
    int mGender;
    int status;
    LinearLayout weightLineOff;
    LinearLayout weightLineOn;
    LinearLayout weightOff;
    LinearLayout weightOn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_newprofile_gender);
        Intent intent = getIntent();
        if (intent != null) {
            this.status = intent.getIntExtra(UserConst.PROFILE_VIEW_STATUS, 1);
        } else {
            this.status = 1;
        }
        setTitleText(R.string.create_profile_title);
        if (this.status == 2) {
            setLeftBackTo();
            this.mGender = intent.getIntExtra(GENDER_VALUE, 0);
        } else {
            this.mGender = 1;
        }
        this.genderPicker = (WangWheelView) findViewById(R.id.gender_picker);
        this.btnNext = (Button) findViewById(R.id.btn_next);
        this.btnNext.setOnClickListener(this);
        this.heightLineOff = (LinearLayout) findViewById(R.id.height_line_off_img);
        this.heightLineOn = (LinearLayout) findViewById(R.id.height_line_on_img);
        this.heightOff = (LinearLayout) findViewById(R.id.height_off_img);
        this.heightOn = (LinearLayout) findViewById(R.id.height_on_img);
        this.weightLineOff = (LinearLayout) findViewById(R.id.weight_line_off_img);
        this.weightLineOn = (LinearLayout) findViewById(R.id.weight_line_on_img);
        this.weightOff = (LinearLayout) findViewById(R.id.weight_off_img);
        this.weightOn = (LinearLayout) findViewById(R.id.weight_on_img);
        this.birthdayLineOff = (LinearLayout) findViewById(R.id.birthday_line_off_img);
        this.birthdayLineOn = (LinearLayout) findViewById(R.id.birthday_line_on_img);
        this.birthdayOff = (LinearLayout) findViewById(R.id.birthday_off_img);
        this.birthdayOn = (LinearLayout) findViewById(R.id.birthday_on_img);
        initView();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            this.mGender = this.genderPicker.getSeletedIndex() + 1;
            if (this.status == 1) {
                Intent intent = new Intent(this, NewProfileHeightActivity.class);
                intent.putExtra(GENDER_VALUE, this.mGender);
                intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
                startActivity(intent);
                return;
            }
            Intent intent2 = new Intent();
            intent2.putExtra(GENDER_VALUE, this.mGender);
            setResult(2, intent2);
            finish();
        }
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.genderPicker.setOffset(1);
        List<String> genderList = new ArrayList<>();
        genderList.add(getString(R.string.male));
        genderList.add(getString(R.string.female));
        this.genderPicker.setItems(genderList);
        this.genderPicker.setSeletion(this.mGender - 1);
        if (this.status == 1) {
            this.heightLineOff.setVisibility(0);
            this.heightOff.setVisibility(0);
            this.weightLineOff.setVisibility(0);
            this.weightOff.setVisibility(0);
            this.birthdayLineOff.setVisibility(0);
            this.birthdayOff.setVisibility(0);
            this.heightLineOn.setVisibility(8);
            this.heightOn.setVisibility(8);
            this.weightLineOn.setVisibility(8);
            this.weightOn.setVisibility(8);
            this.birthdayLineOn.setVisibility(8);
            this.birthdayOn.setVisibility(8);
            return;
        }
        this.heightLineOn.setVisibility(0);
        this.heightOn.setVisibility(0);
        this.weightLineOn.setVisibility(0);
        this.weightOn.setVisibility(0);
        this.birthdayLineOn.setVisibility(0);
        this.birthdayOn.setVisibility(0);
        this.heightLineOff.setVisibility(8);
        this.heightOff.setVisibility(8);
        this.weightLineOff.setVisibility(8);
        this.weightOff.setVisibility(8);
        this.birthdayLineOff.setVisibility(8);
        this.birthdayOff.setVisibility(8);
    }
}
