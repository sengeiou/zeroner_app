package com.iwown.sport_module.Fragment.active;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.ActiveActivity;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.pojo.DevSupportAnalysisModuleInfo;
import com.iwown.sport_module.pojo.active.SportAllData;
import com.iwown.sport_module.pojo.active.SportDetailsData;
import com.iwown.sport_module.util.MyScreenAdapter;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.MyTextView;
import com.iwown.sport_module.view.WithUnitText;
import com.iwown.sport_module.view.chart.ActiveTodayChart;
import com.iwown.sport_module.view.checkbar.AChecKBarAdapter;
import com.iwown.sport_module.view.checkbar.ALinearCheckBar;
import com.iwown.sport_module.view.checkbar.ALinearCheckBar.OnCheckedChangeListener;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public class ActiveTodayFragment extends Fragment {
    private static final int TYPE_CALORIA = 1;
    private static final int TYPE_STAND_UP = 2;
    private static final int TYPE_STEP = 0;
    private String TAG = getClass().getSimpleName();
    private boolean isP1 = false;
    private ActiveTodayChart mActiveTodayChart;
    public RecyclerView mActiveTodayRcy;
    /* access modifiers changed from: private */
    public FragmentActivity mActivity;
    private List<Bean> mBeans;
    private TextView mDataFrom;
    private MyScreenAdapter<SportDetailsData, BaseViewHolder> mDetailsAdapter;
    private ImageView mDevIcon;
    private List<DevSupportAnalysisModuleInfo> mDevSupportAnalysisModuleInfos;
    private View mLayout;
    public View mRcyHead;
    /* access modifiers changed from: private */
    public SportAllData mSportAllData = new SportAllData();
    /* access modifiers changed from: private */
    public List<SportDetailsData> mSportDetailsData = new ArrayList();
    private ALinearCheckBar mSport_type_selector;
    private TextView mTestTv;
    private View mTopHeadLL;
    private WithUnitText mTotalStandUpValue;
    private MyTextView mTotalStepValue;
    private WithUnitText mTotaleCalValue;
    public int now_data_type = 0;
    /* access modifiers changed from: private */
    public long thisFragmentTime = 0;

    private class Bean {
        public int img_selector;
        public String text;
        public int text_selector;

        public Bean(int img_selector2, int text_selector2, String text2) {
            this.img_selector = img_selector2;
            this.text_selector = text_selector2;
            this.text = text2;
        }

        public Bean(int img_selector2, String text2) {
            this.img_selector = img_selector2;
            this.text = text2;
        }
    }

    public void setNow_data_type(int now_data_type2) {
        this.now_data_type = now_data_type2;
        refreshCheckBar();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mLayout = inflater.inflate(R.layout.sport_module_active_today_frag, null);
        this.mActivity = getActivity();
        KLog.e(this.TAG, "onCreateView...");
        UserConfig.getInstance().initInfoFromOtherModule();
        initView();
        initEvent();
        return this.mLayout;
    }

    private void initEvent() {
        this.mSport_type_selector.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckChanged(int position, boolean isChecked) {
                if (isChecked) {
                    ActiveTodayFragment.this.now_data_type = position;
                    ((ActiveActivity) ActiveTodayFragment.this.mActivity).getDataTypeMap().put(new DateUtil(ActiveTodayFragment.this.thisFragmentTime, false).getSyyyyMMddDate(), Integer.valueOf(ActiveTodayFragment.this.now_data_type));
                    ActiveTodayFragment.this.refreshChart();
                }
            }
        });
    }

    private void initView() {
        this.mActiveTodayRcy = (RecyclerView) this.mLayout.findViewById(R.id.active_today_rcy);
        this.mDetailsAdapter = new MyScreenAdapter<SportDetailsData, BaseViewHolder>(R.layout.sport_module_sport_details_item, this.mSportDetailsData) {
            /* access modifiers changed from: protected */
            public void convert(BaseViewHolder helper, SportDetailsData data) {
                boolean mUnit = UserConfig.getInstance().isMertric();
                int position = ActiveTodayFragment.this.mSportDetailsData.indexOf(data);
                View view = helper.getView(R.id.details_item_line);
                TextView timeTxt = (TextView) helper.getView(R.id.details_item_time);
                TextView typeTxt = (TextView) helper.getView(R.id.details_item_run);
                TextView stepUnitTxt = (TextView) helper.getView(R.id.details_item_step_unit);
                MyTextView stepTxt = (MyTextView) helper.getView(R.id.details_item_step);
                MyTextView distanceTxt = (MyTextView) helper.getView(R.id.details_item_distance);
                TextView disUnitTxt = (TextView) helper.getView(R.id.details_item_unit);
                MyTextView calrioesTxt = (MyTextView) helper.getView(R.id.details_item_calories);
                ImageView itemImg = (ImageView) helper.getView(R.id.details_item_img);
                LinearLayout notWalk = (LinearLayout) helper.getView(R.id.sport_not_walk);
                LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.details_item_enter);
                ImageView go_iv = (ImageView) helper.getView(R.id.to_run_iv);
                FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), stepTxt, distanceTxt, calrioesTxt);
                go_iv.setVisibility(8);
                ArrayList listJson = JsonTool.getListJson(ActiveTodayFragment.this.getString(R.string.dev_support_analysis), DevSupportAnalysisModuleInfo.class);
                String str = "";
                if (ActiveTodayFragment.this.mSportAllData.getData_from().lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX) != -1) {
                    KLog.e("licl", "new_str: " + ActiveTodayFragment.this.mSportAllData.getData_from().substring(0, ActiveTodayFragment.this.mSportAllData.getData_from().lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX)));
                }
                if (position == ActiveTodayFragment.this.mSportDetailsData.size() - 1) {
                    helper.setVisible(R.id.details_item_line, false);
                } else {
                    helper.setVisible(R.id.details_item_line, true);
                }
                if (data.getType() == 1 || data.getType() == 7 || data.getType() == 147 || data.getType() == 255) {
                    stepTxt.setText(data.getStep() + "");
                    stepUnitTxt.setText(this.mContext.getString(R.string.sport_module_unit_step));
                    notWalk.setVisibility(0);
                    if (!mUnit) {
                        disUnitTxt.setText(this.mContext.getString(R.string.sport_module_distance_unit_mi));
                        double mi = Util.meterToMile(data.getDistance());
                        if (mi >= 0.01d || mi <= Utils.DOUBLE_EPSILON) {
                            distanceTxt.setText(Util.doubleToString(2, mi));
                        } else {
                            distanceTxt.setText("<0.01");
                        }
                    } else {
                        disUnitTxt.setText(this.mContext.getString(R.string.sport_module_distance_unit_km));
                        distanceTxt.setText(Util.doubleToString(2, data.getDistance()));
                    }
                } else {
                    stepTxt.setText(data.getActivity() + "");
                    stepUnitTxt.setText(this.mContext.getString(R.string.sport_module_minutes));
                    notWalk.setVisibility(8);
                }
                if (data.getType() == 255) {
                    timeTxt.setVisibility(8);
                } else {
                    timeTxt.setVisibility(0);
                }
                timeTxt.setText(data.getTime());
                typeTxt.setText(data.getStrType());
                if (data.getImgType() == -1) {
                    itemImg.setVisibility(4);
                } else {
                    itemImg.setImageResource(data.getImgType());
                }
                calrioesTxt.setText(((double) Float.parseFloat(data.getStrCalories())) < 0.1d ? "<0.1" : data.getStrCalories());
            }
        };
        this.mActiveTodayRcy.setBackgroundColor(RunActivitySkin.RunActy_Base_BG);
        this.mActiveTodayRcy.setAdapter(this.mDetailsAdapter);
        this.mActiveTodayRcy.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mRcyHead = View.inflate(this.mActivity, R.layout.sport_module_active_today_rcy_head, null);
        this.mDetailsAdapter.addHeaderView(this.mRcyHead);
        this.mTestTv = (TextView) this.mRcyHead.findViewById(R.id.test_date_tv);
        this.mTopHeadLL = this.mRcyHead.findViewById(R.id.top_head_ll);
        this.mActiveTodayChart = (ActiveTodayChart) this.mRcyHead.findViewById(R.id.today_data_chart);
        this.mTotalStepValue = (MyTextView) this.mRcyHead.findViewById(R.id.total_step_value);
        this.mTotaleCalValue = (WithUnitText) this.mRcyHead.findViewById(R.id.total_cal_value);
        this.mTotalStandUpValue = (WithUnitText) this.mRcyHead.findViewById(R.id.total_stand_up_value);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mTotalStepValue, this.mTotaleCalValue.getNumTv(), this.mTotalStandUpValue.getNumTv());
        this.mDataFrom = (TextView) this.mRcyHead.findViewById(R.id.data_from_device);
        this.mDevIcon = (ImageView) this.mRcyHead.findViewById(R.id.data_from_icon);
        this.mTopHeadLL.setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#186DDB"), Color.parseColor("#0E4BBD")}));
        this.mSport_type_selector = (ALinearCheckBar) this.mRcyHead.findViewById(R.id.check_bar);
        this.mBeans = new ArrayList();
        this.mBeans.add(new Bean(R.drawable.sport_module_step_check_selector, R.color.sport_module_step_check_text_selector, getString(R.string.sport_module_step)));
        this.mBeans.add(new Bean(R.drawable.sport_module_caloria_check_selector, R.color.sport_module_step_check_text_selector, getString(R.string.sport_module_calories)));
        this.mBeans.add(new Bean(R.drawable.sport_module_stand_up_check_selector, R.color.sport_module_step_check_text_selector, getString(R.string.sport_module_stand_up)));
        this.mSport_type_selector.setAdapter(new AChecKBarAdapter<Bean>(this.mActivity, R.layout.sport_module_active_data_type_check_item, this.mBeans, this.now_data_type) {
            public void bindCheckRes(View itemView, Bean bean) {
                TextView textView = (TextView) itemView.findViewById(R.id.data_type_text);
                ((ImageView) itemView.findViewById(R.id.data_type_img)).setBackground(ActiveTodayFragment.this.mActivity.getResources().getDrawable(bean.img_selector));
                textView.setTextColor(ActiveTodayFragment.this.mActivity.getResources().getColorStateList(R.color.sport_module_step_check_text_selector));
                textView.setText(bean.text);
            }
        });
        refreshUI();
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(this.TAG, "onHiddenChanged...");
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(this.TAG, "isVisibleToUser...");
        }
    }

    public void onResume() {
        super.onResume();
        Log.e(this.TAG, "onResume...");
        setTestTv();
    }

    public SportAllData getSportAllData() {
        return this.mSportAllData;
    }

    public void setSportAllData(SportAllData sportAllData) {
        this.mSportAllData = sportAllData;
        refreshUI();
    }

    private void setTestTv() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(this.thisFragmentTime);
        if (this.mTestTv != null) {
            this.mTestTv.setText(mCalendar.get(1) + "/" + (mCalendar.get(2) + 1) + "/" + mCalendar.get(5));
        }
    }

    public void setThisFragmentTime(long time) {
        this.thisFragmentTime = time;
        setTestTv();
    }

    public long getThisFragmentTime() {
        return this.thisFragmentTime;
    }

    public void addDays(int dayOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.thisFragmentTime);
        calendar.add(5, dayOffset);
        this.thisFragmentTime = calendar.getTimeInMillis();
        setTestTv();
    }

    private void refreshUI() {
        if (this.mSport_type_selector != null) {
            setTestTv();
            refreshChart();
            refreshTotal();
            refreshDetailsRcy();
        }
    }

    private void refreshDetailsRcy() {
        this.mDataFrom.setText(this.mSportAllData.getData_from());
        this.mSportDetailsData.clear();
        this.mSportDetailsData.addAll(this.mSportAllData.getDetailsDatas());
        this.mDetailsAdapter.notifyDataSetChanged();
    }

    private void refreshTotal() {
        this.mTotalStepValue.setText(this.mSportAllData.getSteps() + "");
        this.mTotaleCalValue.setNumTv(this.mSportAllData.getCalorie() + "");
        this.mTotalStandUpValue.setNumTv(this.mSportAllData.getStand_hours() + "");
    }

    /* access modifiers changed from: private */
    public void refreshChart() {
        if (this.now_data_type == 0) {
            if (hasHourData(this.mSportAllData.getStep_value_every_h())) {
                this.mActiveTodayChart.refresh(this.mSportAllData.getStep_value_every_h(), this.mSportAllData.getStep_value_every_h(), this.now_data_type);
            } else {
                this.mActiveTodayChart.noData();
            }
        } else if (this.now_data_type == 2) {
            if (hasHourData(this.mSportAllData.getCaloreis())) {
                this.mActiveTodayChart.refresh(this.mSportAllData.getCaloreis(), this.mSportAllData.getStep_value_every_h(), this.now_data_type);
            } else {
                this.mActiveTodayChart.noData();
            }
        } else if (this.now_data_type != 1) {
        } else {
            if (hasHourData(this.mSportAllData.getCaloreis())) {
                this.mActiveTodayChart.refresh(this.mSportAllData.getCaloreis(), this.mSportAllData.getStep_value_every_h(), this.now_data_type);
            } else {
                this.mActiveTodayChart.noData();
            }
        }
    }

    private boolean hasHourData(float[] datas) {
        boolean hasData = false;
        int length = datas.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (datas[i] != 0.0f) {
                hasData = true;
                break;
            } else {
                i++;
            }
        }
        if (hasData) {
            this.mDataFrom.setVisibility(0);
            this.mDevIcon.setVisibility(0);
        } else {
            this.mDataFrom.setVisibility(8);
            this.mDevIcon.setVisibility(8);
        }
        return hasData;
    }

    private void refreshCheckBar() {
        if (this.mSport_type_selector != null) {
            this.mSport_type_selector.setCheck(this.now_data_type);
        }
    }
}
