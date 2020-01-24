package com.iwown.sport_module.Fragment.active;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.CommonAdapter;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.ViewHolder;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.ActiveActivity;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.pojo.active.GalleryData;
import com.iwown.sport_module.pojo.active.SportAllData;
import com.iwown.sport_module.presenter.ActiveTodayPresentImpl;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.WithUnitText;
import com.iwown.sport_module.view.checkbar.AChecKBarAdapter;
import com.iwown.sport_module.view.checkbar.ALinearCheckBar;
import com.iwown.sport_module.view.checkbar.ALinearCheckBar.OnCheckedChangeListener;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ActiveCaculateFragment extends Fragment {
    private static final int TYPE_CALORIA = 1;
    private static final int TYPE_STAND_UP = 2;
    private static final int TYPE_STEP = 0;
    /* access modifiers changed from: private */
    public static int caloria_max = 0;
    /* access modifiers changed from: private */
    public static int now_data_type = 0;
    /* access modifiers changed from: private */
    public static int select_num = 0;
    /* access modifiers changed from: private */
    public static int stand_up_max = 0;
    /* access modifiers changed from: private */
    public static int step_max = 0;
    private int ITEM_MAX_HEIGHT_DP = 115;
    /* access modifiers changed from: private */
    public int ITEM_MAX_HEIGHT_PX = 0;
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    private HashMap<String, List<GalleryData>> dataMonthlyMap = new HashMap<>();
    /* access modifiers changed from: private */
    public DateUtil last_request_month = new DateUtil();
    /* access modifiers changed from: private */
    public FragmentActivity mActivity;
    private List<Bean> mBeans;
    private TextView mCalTitle;
    private WithUnitText mCalValue;
    private ALinearCheckBar mCheckBar;
    private TextView mDisTitle;
    private WithUnitText mDisValue;
    private TextView mExcTitle;
    private WithUnitText mExcValue_h;
    private WithUnitText mExcValue_min;
    private Gallery mGallery;
    private List<GalleryData> mGalleryDataList = new ArrayList();
    private View mLayout;
    private ActiveTodayPresentImpl mPresent = null;
    public ScrollView mScl;
    private TextView mStandTitle;
    private WithUnitText mStandValue;
    private TextView mStepTitle;
    private WithUnitText mStepValue;
    private LinearLayout mTopPartLL;
    private CommonAdapter<GalleryData> sport_today_adapter = null;

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

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mLayout = inflater.inflate(R.layout.sport_module_active_caculate_frag, null);
        this.mActivity = getActivity();
        this.mPresent = ((ActiveActivity) this.mActivity).getActiveTodayPresent();
        initView();
        initData();
        initEvent();
        return this.mLayout;
    }

    private void initData() {
        this.ITEM_MAX_HEIGHT_PX = DensityUtil.dip2px(this.mActivity, (float) this.ITEM_MAX_HEIGHT_DP);
        this.mLayout.postDelayed(new Runnable() {
            public void run() {
                ActiveCaculateFragment.this.getDataAccordingMonth(new DateUtil(), true, 2000);
            }
        }, 500);
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        KLog.e(this.TAG, "onHiddenChanged...");
    }

    /* access modifiers changed from: private */
    public void getDataAccordingMonth(DateUtil date, boolean needNet, long delay) {
        List<GalleryData> list;
        boolean isNewList;
        int end;
        if (this.mPresent == null) {
            KLog.e(this.TAG, "mPresent==null");
            return;
        }
        String key = date.getYear() + Util.get02dStr(date.getMonth());
        if (this.dataMonthlyMap.get(key) == null) {
            list = new ArrayList<>();
            this.dataMonthlyMap.put(key, list);
            isNewList = true;
        } else {
            list = (List) this.dataMonthlyMap.get(key);
            isNewList = false;
        }
        this.last_request_month = date;
        if (new DateUtil().isSameMonth(date.getMonth(), date.getYear())) {
            end = new DateUtil().getDay();
            KLog.e(this.TAG, "当前月");
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(date.getYear(), date.getMonth() - 1, 1);
            end = cal.getActualMaximum(5);
        }
        KLog.e(this.TAG, "当前月有" + end + "天");
        DateUtil dateUtil = new DateUtil(date.getYear(), date.getMonth(), 1);
        if (isNewList) {
            for (int i = 1; i <= end; i++) {
                dateUtil.setDay(i);
                list.add(new GalleryData(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), 0, 0, 0, 0, 0, 0));
            }
            this.mGalleryDataList.addAll(0, list);
        }
        for (int i2 = 1; i2 <= end; i2++) {
            dateUtil.setDay(i2);
            SportAllData allData = this.mPresent.getAllData(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice(), needNet);
            int pos = findPosInTotalDataList(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());
            ((GalleryData) this.mGalleryDataList.get(pos)).setYear(dateUtil.getYear());
            ((GalleryData) this.mGalleryDataList.get(pos)).setMonth(dateUtil.getMonth());
            ((GalleryData) this.mGalleryDataList.get(pos)).setDay(dateUtil.getDay());
            ((GalleryData) this.mGalleryDataList.get(pos)).setGalleryDay(dateUtil.getMonth() + "/" + dateUtil.getDay());
            ((GalleryData) this.mGalleryDataList.get(pos)).setGalleryCal(allData.getCalorie());
            ((GalleryData) this.mGalleryDataList.get(pos)).setGalleryStep(allData.getSteps());
            ((GalleryData) this.mGalleryDataList.get(pos)).setGalleryStand(allData.getStand_hours());
            ((GalleryData) this.mGalleryDataList.get(pos)).setActive_time_h(allData.getActive_time() / 60);
            ((GalleryData) this.mGalleryDataList.get(pos)).setActive_time_min(allData.getActive_time() % 60);
            ((GalleryData) this.mGalleryDataList.get(pos)).setDistance(Util.doubleToFloat(1, allData.getDistance()));
        }
        findMaxes();
        this.sport_today_adapter.notifyDataSetChanged();
        setGalleryCurrentPos(list.size() == 0 ? select_num : list.size() - 1);
    }

    private int findPosInTotalDataList(int year, int month, int day) {
        DateUtil dateUtil = new DateUtil(year, month, day);
        if (this.mGalleryDataList.isEmpty()) {
            return -1;
        }
        GalleryData galleryData = (GalleryData) this.mGalleryDataList.get(0);
        return dateUtil.daysBetweenMe(new DateUtil(galleryData.getYear(), galleryData.getMonth(), galleryData.getDay()));
    }

    private void findMaxes() {
        for (GalleryData data : this.mGalleryDataList) {
            step_max = Math.max(data.getGalleryStep(), step_max);
            caloria_max = Math.max(data.getGalleryCal(), caloria_max);
            stand_up_max = Math.max(data.getGalleryStand(), stand_up_max);
        }
    }

    private void initEvent() {
        this.mCheckBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckChanged(int position, boolean isChecked) {
                Log.e(ActiveCaculateFragment.this.TAG, position + "-->" + isChecked);
                if (isChecked) {
                    ActiveCaculateFragment.now_data_type = position;
                    ActiveCaculateFragment.this.changeDataType(ActiveCaculateFragment.now_data_type);
                }
            }
        });
        this.mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view1, int position, long l) {
                ActiveCaculateFragment.select_num = position;
                ActiveCaculateFragment.this.refreshGallery(position);
                ActiveCaculateFragment.this.refreshBottomValue();
                if (position == 0) {
                    ActiveCaculateFragment.this.last_request_month.addMonth(-1);
                    KLog.e(ActiveCaculateFragment.this.TAG, "请求28数据" + ActiveCaculateFragment.this.last_request_month.getYear() + "/" + ActiveCaculateFragment.this.last_request_month.getMonth() + "/" + ActiveCaculateFragment.this.last_request_month.getDay());
                    ActiveCaculateFragment.this.getDataAccordingMonth(ActiveCaculateFragment.this.last_request_month, true, 0);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshBottomValue() {
        GalleryData galleryData = (GalleryData) this.mGalleryDataList.get(select_num);
        this.mStepValue.setNumTv(galleryData.getGalleryStep() + "");
        this.mExcValue_h.setNumTv(galleryData.getActive_time_h() + "");
        this.mExcValue_min.setNumTv(Util.get02dStr(galleryData.getActive_time_min()) + "");
        if (UserConfig.getInstance().isMertric()) {
            this.mDisValue.setNumTv(galleryData.getDistance() + "");
        } else {
            this.mDisValue.setNumTv(Util.doubleToFloat(1, Util.kmToMile((double) galleryData.getDistance())) + "");
        }
        this.mStandValue.setNumTv(galleryData.getGalleryStand() + "");
        this.mCalValue.setNumTv(galleryData.getGalleryCal() + "");
    }

    public void setGalleryCurrentPos(int position) {
        select_num = position;
        this.mGallery.setSelection(select_num);
    }

    public void changeDataType(int data_type) {
        now_data_type = data_type;
        this.sport_today_adapter.notifyDataSetChanged();
        KLog.e("licl", "changeDataType--" + data_type);
    }

    /* access modifiers changed from: private */
    public void refreshGallery(int position) {
        int firstVisiblePosition = this.mGallery.getFirstVisiblePosition();
        int lastVisiblePosition = this.mGallery.getLastVisiblePosition();
        for (int i = 0; i < this.mGallery.getChildCount(); i++) {
            View view = this.mGallery.getChildAt(i);
            if (view.getTag() instanceof ViewHolder) {
                ((ViewHolder) view.getTag()).setBackgroundColor(R.id.value_item, this.mActivity.getResources().getColor(R.color.sport_module_60_percent_white)).setTextColor(R.id.date_item_txt, this.mActivity.getResources().getColor(R.color.sport_module_60_percent_white));
            }
        }
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view2 = this.mGallery.getChildAt(position - firstVisiblePosition);
            if (view2.getTag() instanceof ViewHolder) {
                ((ViewHolder) view2.getTag()).setBackgroundColor(R.id.value_item, -1).setTextColor(R.id.date_item_txt, -1);
            }
        }
    }

    private void initView() {
        int size;
        this.mCheckBar = (ALinearCheckBar) this.mLayout.findViewById(R.id.check_bar);
        this.mBeans = new ArrayList();
        this.mBeans.add(new Bean(R.drawable.sport_module_step_check_selector, R.color.sport_module_step_check_text_selector, getString(R.string.sport_module_step)));
        this.mBeans.add(new Bean(R.drawable.sport_module_caloria_check_selector, R.color.sport_module_step_check_text_selector, getString(R.string.sport_module_calories)));
        this.mBeans.add(new Bean(R.drawable.sport_module_stand_up_check_selector, R.color.sport_module_step_check_text_selector, getString(R.string.sport_module_stand_up)));
        this.mCheckBar.setAdapter(new AChecKBarAdapter<Bean>(this.mActivity, R.layout.sport_module_active_data_type_check_item, this.mBeans, now_data_type) {
            public void bindCheckRes(View itemView, Bean bean) {
                TextView textView = (TextView) itemView.findViewById(R.id.data_type_text);
                ((ImageView) itemView.findViewById(R.id.data_type_img)).setBackground(ActiveCaculateFragment.this.mActivity.getResources().getDrawable(bean.img_selector));
                textView.setTextColor(ActiveCaculateFragment.this.mActivity.getResources().getColorStateList(R.color.sport_module_step_check_text_selector));
                textView.setText(bean.text);
            }
        });
        this.mGallery = (Gallery) this.mLayout.findViewById(R.id.gallery);
        this.mGallery.setSpacing(DensityUtil.dip2px(this.mActivity, 18.0f));
        this.sport_today_adapter = new CommonAdapter<GalleryData>(this.mActivity, this.mGalleryDataList, R.layout.sport_module_active_caculate_gallery_item) {
            public void convert(ViewHolder helper, int position, GalleryData item) {
                if (position == ActiveCaculateFragment.select_num) {
                    helper.setBackgroundColor(R.id.value_item, -1).setText(R.id.date_item_txt, item.getGalleryDay()).setTextColor(R.id.date_item_txt, -1);
                } else {
                    helper.setBackgroundColor(R.id.value_item, ActiveCaculateFragment.this.mActivity.getResources().getColor(R.color.sport_module_60_percent_white)).setText(R.id.date_item_txt, item.getGalleryDay()).setTextColor(R.id.date_item_txt, ActiveCaculateFragment.this.mActivity.getResources().getColor(R.color.sport_module_60_percent_white));
                }
                View valueView = helper.getView(R.id.value_item);
                LayoutParams params = (LayoutParams) valueView.getLayoutParams();
                switch (ActiveCaculateFragment.now_data_type) {
                    case 0:
                        params.height = (int) (((((double) item.getGalleryStep()) * 1.0d) / ((double) ActiveCaculateFragment.step_max)) * ((double) ActiveCaculateFragment.this.ITEM_MAX_HEIGHT_PX));
                        break;
                    case 1:
                        params.height = (int) (((((double) item.getGalleryCal()) * 1.0d) / ((double) ActiveCaculateFragment.caloria_max)) * ((double) ActiveCaculateFragment.this.ITEM_MAX_HEIGHT_PX));
                        break;
                    case 2:
                        params.height = (int) (((((double) item.getGalleryStand()) * 1.0d) / ((double) ActiveCaculateFragment.stand_up_max)) * ((double) ActiveCaculateFragment.this.ITEM_MAX_HEIGHT_PX));
                        break;
                }
                params.gravity = 80;
                valueView.setLayoutParams(params);
            }
        };
        this.mGallery.setAdapter(this.sport_today_adapter);
        if (this.mGalleryDataList.size() == 0) {
            size = 0;
        } else {
            size = this.mGalleryDataList.size() - 1;
        }
        setGalleryCurrentPos(size);
        this.sport_today_adapter.notifyDataSetChanged();
        changeDataType(now_data_type);
        View stepV = this.mLayout.findViewById(R.id.step_rl);
        View excV = this.mLayout.findViewById(R.id.exercise_rl);
        View disV = this.mLayout.findViewById(R.id.distance_rl);
        View calV = this.mLayout.findViewById(R.id.cal_rl);
        View standV = this.mLayout.findViewById(R.id.stand_rl);
        this.mStepTitle = (TextView) stepV.findViewById(R.id.title);
        this.mExcTitle = (TextView) excV.findViewById(R.id.title);
        this.mDisTitle = (TextView) disV.findViewById(R.id.title);
        this.mCalTitle = (TextView) calV.findViewById(R.id.title);
        this.mStandTitle = (TextView) standV.findViewById(R.id.title);
        this.mStepTitle.setText(R.string.sport_module_Steps);
        this.mExcTitle.setText(R.string.sport_module_Exercise_time);
        this.mDisTitle.setText(R.string.sport_module_Distance);
        this.mCalTitle.setText(R.string.sport_module_calories);
        this.mStandTitle.setText(R.string.sport_module_Standing_time);
        this.mStepValue = (WithUnitText) stepV.findViewById(R.id.value);
        this.mExcValue_h = (WithUnitText) excV.findViewById(R.id.value);
        this.mExcValue_min = (WithUnitText) excV.findViewById(R.id.value2);
        this.mDisValue = (WithUnitText) disV.findViewById(R.id.value);
        this.mCalValue = (WithUnitText) calV.findViewById(R.id.value);
        this.mStandValue = (WithUnitText) standV.findViewById(R.id.value);
        this.mStepValue.setUnitTv("");
        if (UserConfig.getInstance().isMertric()) {
            this.mDisValue.setUnitTv(getString(R.string.sport_module_distance_unit_km));
        } else {
            this.mDisValue.setUnitTv(getString(R.string.sport_module_distance_unit_mi));
        }
        this.mCalValue.setUnitTv(getString(R.string.sport_module_unit_cal));
        this.mStandValue.setUnitTv(getString(R.string.sport_module_unit_h));
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mStepValue.getNumTv(), this.mDisValue.getNumTv(), this.mCalValue.getNumTv(), this.mStandValue.getNumTv(), this.mExcValue_h.getNumTv(), this.mExcValue_min.getNumTv());
        this.mTopPartLL = (LinearLayout) this.mLayout.findViewById(R.id.top_part_ll);
        this.mTopPartLL.setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#186DDB"), Color.parseColor("#0E4BBD")}));
        this.mScl = (ScrollView) this.mLayout.findViewById(R.id.scl);
        this.mScl.setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
    }

    public void refreshUi(int year, int month) {
        DateUtil dateUtil = new DateUtil(year, month, 1);
        if (this.last_request_month.isSameMonth(month, year)) {
            KLog.e(this.TAG, "zhixingle" + year + "/" + month);
            getDataAccordingMonth(dateUtil, false, 0);
        }
        refreshBottomValue();
    }
}
