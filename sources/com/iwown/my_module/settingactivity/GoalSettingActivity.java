package com.iwown.my_module.settingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.data.HealthGoalEntity;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.healthy.network.request.GoalSend;
import com.iwown.my_module.healthy.network.response.HealthyGoalCode;
import com.iwown.my_module.model.response.Goal;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.utility.ScreenUtility;
import com.iwown.my_module.widget.WithUnitText;
import com.tencent.bugly.BuglyStrategy.a;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import org.litepal.crud.DataSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoalSettingActivity extends BaseActivity {
    public static final String GOALVIEWSTATUS = "GoalSettingViewStatus";
    private static int MAX_STEPS = a.MAX_USERDATA_VALUE_LENGTH;
    /* access modifiers changed from: private */
    public static int MIN_STEPS = 3000;
    private static final int SUGGEST_STEP = 10000;
    private static final String TAG = "GoalSettingActivity";
    private static float mMaxWeight = 200.0f;
    /* access modifiers changed from: private */
    public static float mMinWeight = 30.0f;
    Button mBtnNext;
    TextView mCurWeightHintTv;
    /* access modifiers changed from: private */
    public HealthGoalEntity mGoal;
    private Retrofit mRetrofit;
    TextView mSportSuggestTv;
    SeekBar mStepSeekBar;
    private int mStepSeekBarInitProgress;
    private int mStepSeekBarMax;
    WithUnitText mStepsTargetTv;
    /* access modifiers changed from: private */
    public EnumMeasureUnit mUnit;
    /* access modifiers changed from: private */
    public UserInfoEntity mUserInfo;
    private UserService mUserService;
    /* access modifiers changed from: private */
    public int mViewStatus;
    SeekBar mWeightSeekBar;
    WithUnitText mWeightsTargetTv;
    /* access modifiers changed from: private */
    public int sendNum = 0;
    private int weightInitProgress;
    private int weightSeekBarMax;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_goal_setting);
        this.mStepsTargetTv = (WithUnitText) findViewById(R.id.steps_target_tv);
        this.mStepSeekBar = (SeekBar) findViewById(R.id.step_seek_bar);
        this.mWeightsTargetTv = (WithUnitText) findViewById(R.id.weights_target_tv);
        this.mWeightSeekBar = (SeekBar) findViewById(R.id.weight_seek_bar);
        this.mSportSuggestTv = (TextView) findViewById(R.id.sport_suggest_tv);
        this.mCurWeightHintTv = (TextView) findViewById(R.id.cur_weight_hint);
        this.mBtnNext = (Button) findViewById(R.id.btn_next);
        this.mUnit = GlobalUserDataFetcher.getPreferredMeasureUnit(this);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        Intent intent = getIntent();
        if (intent != null) {
            this.mViewStatus = intent.getIntExtra(GOALVIEWSTATUS, 2);
        } else {
            this.mViewStatus = 2;
        }
        initData(this.mViewStatus);
        initView(this.mViewStatus);
        initEvent();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void initEvent() {
        this.mSportSuggestTv.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GoalSettingActivity.this.mStepSeekBar.setProgress((10000 - GoalSettingActivity.MIN_STEPS) / 500);
                GoalSettingActivity.this.mStepsTargetTv.setNumTv("10000");
                GoalSettingActivity.this.mGoal.setTarget_step(GoalSettingActivity.SUGGEST_STEP);
            }
        });
        this.mStepSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GoalSettingActivity.this.mStepsTargetTv.setNumTv(((progress * 500) + GoalSettingActivity.MIN_STEPS) + "");
                GoalSettingActivity.this.mGoal.setTarget_step((progress * 500) + GoalSettingActivity.MIN_STEPS);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mWeightSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (GoalSettingActivity.this.mUnit == EnumMeasureUnit.Imperial) {
                    GoalSettingActivity.this.mWeightsTargetTv.setNumTv(String.valueOf(Math.round(GoalSettingActivity.mMinWeight + ((float) progress))));
                    GoalSettingActivity.this.mGoal.setTarget_weight(CommonUtility.lbsToKg(Math.round(GoalSettingActivity.mMinWeight + ((float) progress))));
                    return;
                }
                GoalSettingActivity.this.mWeightsTargetTv.setNumTv(CommonUtility.doubleToString(1, ((double) GoalSettingActivity.mMinWeight) + (((double) progress) * 0.5d)));
                GoalSettingActivity.this.mGoal.setTarget_weight((float) (((double) GoalSettingActivity.mMinWeight) + (((double) progress) * 0.5d)));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mSportSuggestTv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                GoalSettingActivity.this.mSportSuggestTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                GoalSettingActivity.this.mSportSuggestTv.setTranslationX((float) (((GoalSettingActivity.this.mStepSeekBar.getWidth() * 10) / 23) - (GoalSettingActivity.this.mSportSuggestTv.getWidth() / 2)));
                int sportSuggestTVWidth = GoalSettingActivity.this.mSportSuggestTv.getWidth();
                GoalSettingActivity.this.mSportSuggestTv.setTranslationX((float) (((GoalSettingActivity.this.mStepSeekBar.getThumb().getBounds().centerX() - (sportSuggestTVWidth / 2)) + ScreenUtility.dip2px(GoalSettingActivity.this, 1.0f)) - (((GoalSettingActivity.this.mGoal.getTarget_step() + ShareConstants.ERROR_LOAD_GET_INTENT_FAIL) * GoalSettingActivity.this.mStepSeekBar.getWidth()) / 27000)));
            }
        });
    }

    private void initView(int viewStatus) {
        setTitleText(getString(R.string.sport_module_sleep_quality_analysis_set_goal));
        if (viewStatus == 1) {
            this.mBtnNext.setVisibility(0);
            this.mBtnNext.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    GoalSettingActivity.this.saveGoal();
                }
            });
        } else {
            setLeftBackTo();
            setRightText(getString(R.string.iwown_save), new OnClickListener() {
                public void onClick(View view) {
                    GoalSettingActivity.this.saveGoal();
                }
            });
        }
        this.mStepSeekBarMax = (MAX_STEPS - MIN_STEPS) / 500;
        this.mStepSeekBar.setMax(this.mStepSeekBarMax);
        this.mStepsTargetTv.setUnitTv(getResources().getString(R.string.sport_module_unit_step));
        this.mSportSuggestTv.setText(getResources().getString(R.string.suggestion));
    }

    /* access modifiers changed from: 0000 */
    public void setDefaultGoal(HealthGoalEntity goal, UserInfoEntity userInfo) {
        goal.setTarget_step(SUGGEST_STEP);
        if (userInfo != null) {
            goal.setTarget_weight(userInfo.getWeight());
        } else {
            goal.setTarget_weight(65.0f);
        }
    }

    private void initData(int viewStatus) {
        long uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
        this.mUserInfo = (UserInfoEntity) DataSupport.where("uid=?", String.valueOf(uid)).findFirst(UserInfoEntity.class);
        this.mGoal = new HealthGoalEntity();
        if (this.mUserInfo != null) {
            this.mGoal.setUid(uid);
            if (viewStatus == 1) {
                setDefaultGoal(this.mGoal, this.mUserInfo);
                refreshViewByGoal();
                return;
            }
            HealthGoalEntity goal = (HealthGoalEntity) DataSupport.where("uid=?", String.valueOf(uid)).findFirst(HealthGoalEntity.class);
            if (goal == null) {
                if (this.mUserService == null) {
                    this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
                }
                AppConfigUtil.getInstance(this);
                if (AppConfigUtil.isHealthy()) {
                    getHealthyGoal(uid);
                } else {
                    this.mUserService.getGoal(uid).enqueue(new Callback<Goal>() {
                        public void onResponse(Call<Goal> call, Response<Goal> response) {
                            if (response == null || response.body() == null) {
                                GoalSettingActivity.this.raiseErrorNotice(GoalSettingActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                                return;
                            }
                            Goal goal_server = (Goal) response.body();
                            if (goal_server.getRetCode() == 0) {
                                GoalSettingActivity.this.mGoal.setTarget_step(goal_server.getTarget_step());
                                GoalSettingActivity.this.mGoal.setTarget_weight(goal_server.getTarget_weight());
                            } else {
                                GoalSettingActivity.this.setDefaultGoal(GoalSettingActivity.this.mGoal, GoalSettingActivity.this.mUserInfo);
                            }
                            GoalSettingActivity.this.refreshViewByGoal();
                        }

                        public void onFailure(Call<Goal> call, Throwable t) {
                            GoalSettingActivity.this.raiseErrorNotice(GoalSettingActivity.this.getString(R.string.network_error));
                        }
                    });
                }
            } else {
                this.mGoal = goal;
                refreshViewByGoal();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void saveGoal() {
        Call<ReturnCode> call;
        if (this.mGoal != null) {
            if (this.mUserService == null) {
                this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
            }
            Goal goal2save = new Goal();
            goal2save.setUid(this.mGoal.getUid());
            goal2save.setTarget_step(this.mGoal.getTarget_step());
            goal2save.setTarget_weight(this.mGoal.getTarget_weight());
            AppConfigUtil.getInstance(this);
            if (!AppConfigUtil.isHealthy()) {
                call = this.mUserService.updateGoal(goal2save);
            } else {
                GoalSend goalSend = new GoalSend();
                goalSend.setUid(this.mGoal.getUid());
                goalSend.setTarget_step(this.mGoal.getTarget_step());
                goalSend.setTarget_weight(this.mGoal.getTarget_weight());
                goalSend.setGoal_type(2);
                call = this.mUserService.updateHealthyGoal(goalSend);
            }
            showLoadingDialog();
            call.enqueue(new Callback<ReturnCode>() {
                public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                    GoalSettingActivity.this.hideLoadingDialog();
                    if (response == null || response.body() == null) {
                        GoalSettingActivity.this.sendNum = GoalSettingActivity.this.sendNum + 1;
                        GoalSettingActivity.this.showErrorView(GoalSettingActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                        return;
                    }
                    switch (((ReturnCode) response.body()).getRetCode()) {
                        case 0:
                            Log.w(GoalSettingActivity.TAG, String.format("save goal to server succeed, mViewStatus:%d", new Object[]{Integer.valueOf(GoalSettingActivity.this.mViewStatus)}));
                            GoalSettingActivity.this.mGoal.saveOrUpdate("uid=?", String.valueOf(GoalSettingActivity.this.mGoal.getUid()));
                            GoalSettingActivity.this.saveTBOver();
                            return;
                        case 10001:
                            GoalSettingActivity.this.sendNum = GoalSettingActivity.this.sendNum + 1;
                            GoalSettingActivity.this.showErrorView(GoalSettingActivity.this.getString(R.string.sql_error));
                            return;
                        default:
                            GoalSettingActivity.this.sendNum = GoalSettingActivity.this.sendNum + 1;
                            GoalSettingActivity.this.showErrorView(GoalSettingActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((ReturnCode) response.body()).getRetCode())}));
                            return;
                    }
                }

                public void onFailure(Call<ReturnCode> call, Throwable t) {
                    GoalSettingActivity.this.hideLoadingDialog();
                    GoalSettingActivity.this.sendNum = GoalSettingActivity.this.sendNum + 1;
                    GoalSettingActivity.this.showErrorView(GoalSettingActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void showErrorView(String msg) {
        this.sendNum++;
        if (this.mViewStatus == 1 && this.sendNum >= 3) {
            saveTBOver();
        }
        raiseErrorNotice(msg);
    }

    /* access modifiers changed from: 0000 */
    public void saveTBOver() {
        try {
            ModuleRouteDeviceInfoService.getInstance().updateTargetStep(this.mGoal.getTarget_step(), ModuleRouteUserInfoService.getInstance().getUserInfo(this).getGoalCaloria());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (this.mViewStatus == 1) {
            GlobalDataUpdater.setLoginStatus(this, 3);
            finish();
            ARouter.getInstance().build(RouteUtils.Activity_app_MainActivity).withInt(RouteUtils.Device_List_Key, 1).navigation();
            return;
        }
        finish();
    }

    /* access modifiers changed from: 0000 */
    public void refreshViewByGoal() {
        float cur_weight = this.mUserInfo.getWeight();
        try {
            Log.i(TAG, String.format("current weight:%.1f", new Object[]{Float.valueOf(cur_weight)}));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        int step = SUGGEST_STEP;
        float weight = cur_weight;
        if (this.mGoal != null) {
            step = this.mGoal.getTarget_step();
            weight = this.mGoal.getTarget_weight();
        }
        this.mStepsTargetTv.setNumTv(step + "");
        this.mStepsTargetTv.setUnitTv(getResources().getString(R.string.sport_module_unit_step));
        try {
            this.mStepSeekBarInitProgress = (step - MIN_STEPS) / 500;
        } catch (NumberFormatException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        this.mStepSeekBar.setProgress(this.mStepSeekBarInitProgress);
        float[] weight_min_max = getMaxAndMinWeight(this.mUserInfo.getWeight(), this.mUserInfo.getHeight());
        if (this.mUnit == EnumMeasureUnit.Imperial) {
            int[] weight_min_max_lbs = getMaxAndMinWeightLBS(weight_min_max);
            mMaxWeight = (float) weight_min_max_lbs[1];
            mMinWeight = (float) weight_min_max_lbs[0];
        } else {
            mMaxWeight = weight_min_max[1];
            mMinWeight = weight_min_max[0];
        }
        if (this.mUnit == EnumMeasureUnit.Imperial) {
            this.weightSeekBarMax = (int) (mMaxWeight - mMinWeight);
        } else {
            this.weightSeekBarMax = (int) ((mMaxWeight - mMinWeight) * 2.0f);
        }
        this.mWeightSeekBar.setMax(this.weightSeekBarMax);
        if (this.mUnit == EnumMeasureUnit.Imperial) {
            float lbsTargetWeight = (float) CommonUtility.kgToLbs(weight);
            this.mWeightsTargetTv.setNumTv(Math.round(lbsTargetWeight) + "");
            this.mWeightsTargetTv.setUnitTv(getResources().getString(R.string.unit_lbs));
            this.weightInitProgress = (int) (lbsTargetWeight - mMinWeight);
            float lbsCurWeight = (float) CommonUtility.kgToLbs(cur_weight);
            this.mCurWeightHintTv.setText(getResources().getString(R.string.my_module_goal_cur_weight, new Object[]{String.format("%.1flbs", new Object[]{Float.valueOf(lbsCurWeight)}), String.format("%.1f-%.1flbs", new Object[]{Float.valueOf(mMinWeight), Float.valueOf(mMaxWeight)})}));
        } else {
            this.weightInitProgress = (int) (((double) (weight - mMinWeight)) / 0.5d);
            this.mWeightsTargetTv.setNumTv(String.format("%.1f", new Object[]{Float.valueOf(weight)}));
            this.mWeightsTargetTv.setUnitTv(getResources().getString(R.string.my_module_unit_kg));
            this.mCurWeightHintTv.setText(getResources().getString(R.string.my_module_goal_cur_weight, new Object[]{String.format("%.1fkg", new Object[]{Float.valueOf(cur_weight)}), String.format("%.1f-%.1fkg", new Object[]{Float.valueOf(mMinWeight), Float.valueOf(mMaxWeight)})}));
        }
        this.mWeightSeekBar.setProgress(this.weightInitProgress);
    }

    private float[] getMaxAndMinWeight(float weight, float height) {
        double height_pow = Math.pow((double) (height / 100.0f), 2.0d);
        float minf = CommonUtility.doubleToFloat(1, 18.5d * height_pow);
        float maxf = CommonUtility.doubleToFloat(1, 24.9d * height_pow);
        float min_target_weight = 0.0f;
        float max_target_weight = 0.0f;
        if (((double) weight) > 18.5d * height_pow && (((double) weight) < 24.9d * height_pow || ((double) weight) == 24.9d * height_pow)) {
            min_target_weight = getHalfFloat(minf);
            max_target_weight = getHalfFloat(maxf);
        }
        if (((double) weight) < 18.5d * height_pow || ((double) weight) == 18.5d * height_pow) {
            min_target_weight = weight;
            max_target_weight = getHalfFloat(maxf);
        }
        if (((double) weight) > 24.9d * height_pow) {
            min_target_weight = getHalfFloat(minf);
            max_target_weight = weight;
        }
        if (weight < min_target_weight) {
            min_target_weight = weight;
        }
        if (weight > max_target_weight) {
            max_target_weight = weight;
        }
        return new float[]{min_target_weight, max_target_weight};
    }

    public int[] getMaxAndMinWeightLBS(float[] weightArr) {
        return new int[]{CommonUtility.kgToLbs(weightArr[0]), CommonUtility.kgToLbs(weightArr[1])};
    }

    private float getHalfFloat(float num) {
        if ((num * 10.0f) % 10.0f > 0.0f && (num * 10.0f) % 10.0f <= 5.0f) {
            return CommonUtility.doubleToFloat(1, ((double) ((((((int) (num * 10.0f)) / 10) * 10) + 5) / 10)) * 1.0d);
        }
        if ((num * 10.0f) % 10.0f > 5.0f) {
            return CommonUtility.doubleToFloat(1, ((double) ((((((int) (num * 10.0f)) / 10) + 1) * 10) / 10)) * 1.0d);
        }
        return num;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: 0000 */
    public void getHealthyGoal(long uid) {
        this.mUserService.getHealthyGoal(uid).enqueue(new Callback<HealthyGoalCode>() {
            public void onResponse(Call<HealthyGoalCode> call, Response<HealthyGoalCode> response) {
                if (response == null || response.body() == null) {
                    GoalSettingActivity.this.raiseErrorNotice(GoalSettingActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                if (((HealthyGoalCode) response.body()).getRetCode() == 0) {
                    GoalSend goal_server = ((HealthyGoalCode) response.body()).getData();
                    GoalSettingActivity.this.mGoal.setTarget_step(goal_server.getTarget_step());
                    GoalSettingActivity.this.mGoal.setTarget_weight(goal_server.getTarget_weight());
                    try {
                        ModuleRouteDeviceInfoService.getInstance().updateTargetStep(GoalSettingActivity.this.mGoal.getTarget_step(), ModuleRouteUserInfoService.getInstance().getUserInfo(GoalSettingActivity.this).getGoalCaloria());
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                } else {
                    GoalSettingActivity.this.setDefaultGoal(GoalSettingActivity.this.mGoal, GoalSettingActivity.this.mUserInfo);
                }
                GoalSettingActivity.this.refreshViewByGoal();
            }

            public void onFailure(Call<HealthyGoalCode> call, Throwable t) {
                GoalSettingActivity.this.raiseErrorNotice(GoalSettingActivity.this.getString(R.string.network_error));
            }
        });
    }
}
