package com.iwown.my_module.useractivity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.healthy.network.request.WeightSend;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.model.response.UserInfo;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.settingactivity.GoalSettingActivity;
import com.iwown.my_module.utility.MeasureTransform;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileCategoryActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = "ProfileCategory";
    ImageView birthdayImg;
    TextView birthdayLabel;
    TextView birthdayValue;
    Button btnNext;
    ImageView genderImg;
    TextView genderLabel;
    TextView genderValue;
    ImageView heightImg;
    TextView heightLabel;
    TextView heightValue;
    String mBirthday;
    int mGender;
    float mHeight;
    private Retrofit mRetrofit;
    private EnumMeasureUnit mUnit;
    private UserService mUserService;
    float mWeight;
    ImageView weightImg;
    TextView weightLabel;
    TextView weightValue;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_newprofile_category);
        setTitleText(R.string.create_profile_title);
        this.genderImg = (ImageView) findViewById(R.id.gender_img);
        this.genderImg.setOnClickListener(this);
        this.genderLabel = (TextView) findViewById(R.id.gender_label);
        this.genderLabel.setOnClickListener(this);
        this.genderValue = (TextView) findViewById(R.id.gender_value);
        this.genderValue.setOnClickListener(this);
        this.heightImg = (ImageView) findViewById(R.id.height_img);
        this.heightImg.setOnClickListener(this);
        this.heightLabel = (TextView) findViewById(R.id.height_label);
        this.heightLabel.setOnClickListener(this);
        this.heightValue = (TextView) findViewById(R.id.height_value);
        this.heightValue.setOnClickListener(this);
        this.weightImg = (ImageView) findViewById(R.id.weight_img);
        this.weightImg.setOnClickListener(this);
        this.weightLabel = (TextView) findViewById(R.id.weight_label);
        this.weightLabel.setOnClickListener(this);
        this.weightValue = (TextView) findViewById(R.id.weight_value);
        this.weightValue.setOnClickListener(this);
        this.birthdayImg = (ImageView) findViewById(R.id.birthday_img);
        this.birthdayImg.setOnClickListener(this);
        this.birthdayLabel = (TextView) findViewById(R.id.birthday_label);
        this.birthdayLabel.setOnClickListener(this);
        this.birthdayValue = (TextView) findViewById(R.id.birthday_value);
        this.birthdayValue.setOnClickListener(this);
        this.btnNext = (Button) findViewById(R.id.btn_next);
        this.btnNext.setOnClickListener(this);
        this.mUnit = GlobalUserDataFetcher.getPreferredMeasureUnit(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.mGender = intent.getIntExtra(NewProfileGenderActivity.GENDER_VALUE, 1);
            this.mHeight = intent.getFloatExtra(NewProfileHeightActivity.HEIGHT_VALUE, 0.0f);
            this.mWeight = intent.getFloatExtra(NewProfileWeightActivity.WEIGHT_VALUE, 0.0f);
            this.mBirthday = intent.getStringExtra(NewProfileBirthdayActivity.BIRTHDAY_VALUE);
            initView();
            Log.i(TAG, String.format("in category view, height:%f,weight:%f", new Object[]{Float.valueOf(this.mHeight), Float.valueOf(this.mWeight)}));
        }
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
    }

    public void onClick(View view) {
        Call<ReturnCode> call;
        int i = view.getId();
        if (i == R.id.gender_img || i == R.id.gender_label || i == R.id.gender_value) {
            Intent intent = new Intent(this, NewProfileGenderActivity.class);
            intent.putExtra(NewProfileGenderActivity.GENDER_VALUE, this.mGender);
            intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 2);
            startActivityForResult(intent, 2);
        } else if (i == R.id.height_img || i == R.id.height_label || i == R.id.height_value) {
            Intent intent2 = new Intent(this, NewProfileHeightActivity.class);
            intent2.putExtra(UserConst.PROFILE_VIEW_STATUS, 2);
            intent2.putExtra(NewProfileHeightActivity.HEIGHT_VALUE, this.mHeight);
            startActivityForResult(intent2, 3);
        } else if (i == R.id.weight_img || i == R.id.weight_label || i == R.id.weight_value) {
            Intent intent3 = new Intent(this, NewProfileWeightActivity.class);
            intent3.putExtra(UserConst.PROFILE_VIEW_STATUS, 2);
            intent3.putExtra(NewProfileWeightActivity.WEIGHT_VALUE, this.mWeight);
            startActivityForResult(intent3, 4);
        } else if (i == R.id.birthday_img || i == R.id.birthday_label || i == R.id.birthday_value) {
            Intent intent4 = new Intent(this, NewProfileBirthdayActivity.class);
            intent4.putExtra(UserConst.PROFILE_VIEW_STATUS, 2);
            intent4.putExtra(NewProfileBirthdayActivity.BIRTHDAY_VALUE, this.mBirthday);
            startActivityForResult(intent4, 5);
        } else if (i == R.id.btn_next) {
            if (this.mUserService == null) {
                this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
            }
            long uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
            saveBaseUserInfo(uid);
            UserInfo userInfo = new UserInfo();
            userInfo.setBirthday(this.mBirthday);
            userInfo.setGender(this.mGender);
            userInfo.setHeight(this.mHeight);
            userInfo.setWeight(this.mWeight);
            userInfo.setUid(uid);
            if (AppConfigUtil.isHealthy()) {
                call = this.mUserService.postHealthyProfile(userInfo);
            } else {
                call = this.mUserService.updateUserInfo(userInfo);
            }
            showLoadingDialog();
            call.enqueue(new Callback<ReturnCode>() {
                public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                    if (AppConfigUtil.isHealthy()) {
                        ProfileCategoryActivity.this.postHealthyWeight();
                        return;
                    }
                    ProfileCategoryActivity.this.hideLoadingDialog();
                    GlobalDataUpdater.setLoginStatus(ProfileCategoryActivity.this, 2);
                    Intent intent = new Intent(ProfileCategoryActivity.this, GoalSettingActivity.class);
                    intent.setFlags(268468224);
                    intent.putExtra(GoalSettingActivity.GOALVIEWSTATUS, 1);
                    ProfileCategoryActivity.this.startActivity(intent);
                }

                public void onFailure(Call<ReturnCode> call, Throwable t) {
                    ProfileCategoryActivity.this.hideLoadingDialog();
                    ProfileCategoryActivity.this.raiseErrorNotice(ProfileCategoryActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        if (this.mGender == 1) {
            this.genderValue.setText(getResources().getString(R.string.male));
        } else {
            this.genderValue.setText(getResources().getString(R.string.female));
        }
        if (this.mUnit == EnumMeasureUnit.Imperial) {
            double lbs = MeasureTransform.kg2lb((double) this.mWeight);
            this.weightValue.setText(String.format("%d lbs", new Object[]{Integer.valueOf((int) lbs)}));
            double inches = (double) MeasureTransform.cm2Inch(this.mHeight);
            int feet = ((int) inches) / 12;
            int inch = ((int) inches) % 12;
            this.heightValue.setText(String.format("%dft%din", new Object[]{Integer.valueOf(feet), Integer.valueOf(inch)}));
        } else {
            this.heightValue.setText(String.format("%d cm", new Object[]{Integer.valueOf((int) this.mHeight)}));
            this.weightValue.setText(String.format("%.1f kg", new Object[]{Float.valueOf(this.mWeight)}));
        }
        this.birthdayValue.setText(this.mBirthday);
    }

    /* access modifiers changed from: 0000 */
    public void saveBaseUserInfo(long uid) {
        UserInfoEntity baseUserInfo = new UserInfoEntity();
        baseUserInfo.setUid(uid);
        baseUserInfo.setHeight(this.mHeight);
        baseUserInfo.setWeight(this.mWeight);
        baseUserInfo.setGender(this.mGender);
        baseUserInfo.setBirthday(this.mBirthday);
        boolean succeed = baseUserInfo.saveOrUpdate("uid=?", String.valueOf(uid));
        Log.i(TAG, String.format("save base user info: %s", new Object[]{String.valueOf(succeed)}));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:
                this.mGender = data.getIntExtra(NewProfileGenderActivity.GENDER_VALUE, 1);
                if (this.mGender == 1) {
                    this.genderValue.setText(getResources().getString(R.string.male));
                    return;
                } else {
                    this.genderValue.setText(getResources().getString(R.string.female));
                    return;
                }
            case 3:
                this.mHeight = data.getFloatExtra(NewProfileHeightActivity.HEIGHT_VALUE, 0.0f);
                if (this.mUnit == EnumMeasureUnit.Imperial) {
                    double inches = (double) MeasureTransform.cm2Inch(this.mHeight);
                    int feet = ((int) inches) / 12;
                    int inch = ((int) inches) % 12;
                    this.heightValue.setText(String.format("%dft%din", new Object[]{Integer.valueOf(feet), Integer.valueOf(inch)}));
                    return;
                }
                this.heightValue.setText(String.format("%d cm", new Object[]{Integer.valueOf((int) this.mHeight)}));
                return;
            case 4:
                this.mWeight = data.getFloatExtra(NewProfileWeightActivity.WEIGHT_VALUE, 0.0f);
                if (this.mUnit == EnumMeasureUnit.Imperial) {
                    double lbs = MeasureTransform.kg2lb((double) this.mWeight);
                    this.weightValue.setText(String.format("%d lbs", new Object[]{Integer.valueOf((int) lbs)}));
                    return;
                }
                this.weightValue.setText(String.format("%.1f kg", new Object[]{Float.valueOf(this.mWeight)}));
                return;
            case 5:
                this.mBirthday = data.getStringExtra(NewProfileBirthdayActivity.BIRTHDAY_VALUE);
                this.birthdayValue.setText(this.mBirthday);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void postHealthyWeight() {
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        WeightSend t_weight = new WeightSend();
        long uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
        t_weight.setUid(uid);
        t_weight.setWeight(this.mWeight);
        t_weight.setData_from("手动录入");
        t_weight.setRecord_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        t_weight.setHeight((int) this.mHeight);
        t_weight.setGender(this.mGender);
        List<WeightSend> weights = new ArrayList<>();
        weights.add(t_weight);
        Call<ReturnCode> call = this.mUserService.postMyWeightData(uid, weights);
        showLoadingDialog();
        call.enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                ProfileCategoryActivity.this.hideLoadingDialog();
                GlobalDataUpdater.setLoginStatus(ProfileCategoryActivity.this, 2);
                Intent intent = new Intent(ProfileCategoryActivity.this, GoalSettingActivity.class);
                intent.setFlags(268468224);
                intent.putExtra(GoalSettingActivity.GOALVIEWSTATUS, 1);
                ProfileCategoryActivity.this.startActivity(intent);
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                ProfileCategoryActivity.this.hideLoadingDialog();
                ProfileCategoryActivity.this.raiseErrorNotice(ProfileCategoryActivity.this.getString(R.string.unkown_error, new Object[]{"体重上传失败"}));
            }
        });
    }
}
