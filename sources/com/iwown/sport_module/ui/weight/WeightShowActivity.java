package com.iwown.sport_module.ui.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.weight.ModuleRouteWeightService;
import com.iwown.data_link.weight.ScaleBodyFat;
import com.iwown.data_link.weight.WeightUser;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.views.TitleBar.ImageAction;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.iwown.lib_common.views.weightview.WeightViewScrollView;
import com.iwown.lib_common.views.weightview.WeightViewScrollView.CallBack_Select_Data;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.utils.BaseSportUtils;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.iwown.sport_module.ui.weight.WeightDataAdapter1.CallBack_Dialog;
import com.iwown.sport_module.ui.weight.mvp.WeightContract.WPrecenter;
import com.iwown.sport_module.ui.weight.mvp.WeightContract.WeightView;
import com.iwown.sport_module.ui.weight.mvp.WeightPresenter;
import com.socks.library.KLog;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WeightShowActivity extends BaseActivity implements OnClickListener, WeightView, CallBack_Select_Data {
    private int age;
    private String data_from = "";
    private List<WeightDetailDataBean> datas;
    private int dp_185;
    private int dp_275;
    private float height;
    private ScaleBodyFat indexScaleBodyFat;
    private boolean isMale;
    private ImageView iv_data_from;
    /* access modifiers changed from: private */
    public ImageView iv_user_top;
    private LinearLayout ll_users;
    private LoadingDialog loadingDialog;
    /* access modifiers changed from: private */
    public ListView lv_weight;
    private boolean mertric;
    private NumberFormat nf;
    private PopupWindow popupWindow_data_from;
    private PopupWindow popupWindow_userw;
    private Random random;
    private TextView tvUnarchived;
    private TextView tvWeightValue;
    private TextView tv_name;
    private TextView tv_no_data;
    private TextView tv_time;
    private TextView tv_value;
    private TextView tv_value_unit;
    private long uid;
    private WeightDataAdapter1 weightDataAdapter;
    /* access modifiers changed from: private */
    public WeightMsgDialog weightMsgDialog;
    private WeightPresenter weightPresenter;
    private WeightViewScrollView wvsWeight;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_weight_show);
        setTitleBarBG(getResources().getColor(R.color.weight_chart_bg_top));
        setLeftBackTo();
        setTitleTextID(R.string.sport_module_weight_activity);
        getTitleBar().addAction(new ImageAction(R.drawable.share_icon) {
            public void performAction(View view) {
                ScreenLongShareUtils.shareScreenView(WeightShowActivity.this.getContext(), WeightShowActivity.this.lv_weight);
            }
        });
        this.age = UserConfig.getInstance().getAge();
        this.isMale = UserConfig.getInstance().isMale();
        this.height = (float) UserConfig.getInstance().getHeight();
        this.weightPresenter = new WeightPresenter(this);
        if (this.weightMsgDialog == null) {
            this.weightMsgDialog = new WeightMsgDialog(this, true);
        }
        this.dp_185 = DensityUtil.dip2px(this, 185.0f);
        this.dp_275 = DensityUtil.dip2px(this, 275.0f);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        this.lv_weight = (ListView) findViewById(R.id.lv_weight);
        this.datas = new ArrayList();
        this.weightDataAdapter = new WeightDataAdapter1(this.datas, this, R.layout.sport_module_item_weight_data);
        this.weightDataAdapter.setCallBack_dialog(new CallBack_Dialog() {
            public void onResult(String text_msg, String title) {
                WeightShowActivity.this.weightMsgDialog.setShowMsg(title, text_msg);
                WeightShowActivity.this.weightMsgDialog.show();
            }
        });
        this.lv_weight.setAdapter(this.weightDataAdapter);
        initHeadView();
    }

    private void initHeadView() {
        View head_view = LayoutInflater.from(this).inflate(R.layout.sport_module_head_weight, null, false);
        this.iv_data_from = (ImageView) head_view.findViewById(R.id.iv_data_from);
        this.tv_no_data = (TextView) head_view.findViewById(R.id.tv_no_data);
        this.iv_user_top = (ImageView) head_view.findViewById(R.id.iv_user_top);
        this.tv_name = (TextView) head_view.findViewById(R.id.tv_name);
        this.tv_time = (TextView) head_view.findViewById(R.id.tv_time);
        this.tvWeightValue = (TextView) head_view.findViewById(R.id.tv_weight_value);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tvWeightValue);
        this.tv_value_unit = (TextView) head_view.findViewById(R.id.tv_value_unit);
        this.wvsWeight = (WeightViewScrollView) head_view.findViewById(R.id.wvs_weight);
        this.tvUnarchived = (TextView) head_view.findViewById(R.id.tv_unarchived);
        this.tvUnarchived.getPaint().setFlags(8);
        this.tvUnarchived.getPaint().setAntiAlias(true);
        this.lv_weight.addHeaderView(head_view);
        this.tvUnarchived.setOnClickListener(this);
        this.tv_name.setOnClickListener(this);
        this.iv_data_from.setOnClickListener(this);
        this.mertric = UserConfig.getInstance().isMertric();
        if (!this.mertric) {
            this.tv_value_unit.setText(getResources().getString(R.string.unit_lbs));
        } else {
            this.tv_value_unit.setText(getResources().getString(R.string.my_module_unit_kg));
        }
        initData();
    }

    private void initData() {
        String scaleMac = ModuleRouteWeightService.getInstance().getScaleMac(UserConfig.getInstance().getNewUID());
        initDetailWeight();
        this.weightDataAdapter.notifyDataSetChanged();
        this.nf = NumberFormat.getNumberInstance();
        this.nf.setMaximumFractionDigits(2);
        this.uid = UserConfig.getInstance().getNewUID();
        this.wvsWeight.setCallBack_select_data(this);
        loadData();
    }

    private void initDetailWeight() {
        this.indexScaleBodyFat = new ScaleBodyFat();
        WeightDetailDataBean weightDetailDataBean = new WeightDetailDataBean(this, 1, Utils.DOUBLE_EPSILON, this.indexScaleBodyFat.getBmi());
        WeightDetailDataBean weightDetailDataBean1 = new WeightDetailDataBean(this, 2, Utils.DOUBLE_EPSILON, this.indexScaleBodyFat.getBodyfat());
        WeightDetailDataBean weightDetailDataBean2 = new WeightDetailDataBean(this, 3, Utils.DOUBLE_EPSILON, this.indexScaleBodyFat.getMuscule());
        WeightDetailDataBean weightDetailDataBean3 = new WeightDetailDataBean(this, 4, Utils.DOUBLE_EPSILON, this.indexScaleBodyFat.getWater());
        WeightDetailDataBean weightDetailDataBean4 = new WeightDetailDataBean(this, 5, Utils.DOUBLE_EPSILON, this.indexScaleBodyFat.getBone_weight());
        WeightDetailDataBean weightDetailDataBean5 = new WeightDetailDataBean(this, 6, Utils.DOUBLE_EPSILON, (float) this.indexScaleBodyFat.getVisceral_fat());
        WeightDetailDataBean weightDetailDataBean6 = new WeightDetailDataBean(this, 7, Utils.DOUBLE_EPSILON, this.indexScaleBodyFat.getImpedance());
        this.datas.clear();
        this.datas.add(weightDetailDataBean);
        this.datas.add(weightDetailDataBean1);
        this.datas.add(weightDetailDataBean2);
        this.datas.add(weightDetailDataBean3);
        this.datas.add(weightDetailDataBean4);
        this.datas.add(weightDetailDataBean5);
        this.datas.add(weightDetailDataBean6);
        this.weightDataAdapter.notifyDataSetChanged();
    }

    public void loadData() {
        KLog.e(this.uid + "  " + this.height + "   " + this.age + "   " + this.isMale);
        this.weightPresenter.getWifiScaleData(this.uid);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void healthDataUpdate(HealthDataEventBus healthDataEventBus) {
        if (healthDataEventBus.type == 3) {
            loadData();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_unarchived) {
            RouteUtils.startSportWeightDataNotBelongActivity();
            return;
        }
        if (view.getId() == R.id.iv_data_from) {
            if (this.popupWindow_data_from == null) {
                PopupWindow popupWindow = new PopupWindow(this);
                this.popupWindow_data_from = popupWindow;
                this.popupWindow_data_from.setWidth(DensityUtil.dip2px(this, 88.0f));
                this.popupWindow_data_from.setHeight(-2);
                View inflate = LayoutInflater.from(this).inflate(R.layout.sport_module_popupwindow_data_form, null);
                this.tv_value = (TextView) inflate.findViewById(R.id.tv_value);
                this.tv_value.setTextColor(Color.parseColor("#1F9D04"));
                this.tv_value.setTextSize(9.5f);
                this.popupWindow_data_from.setContentView(inflate);
                this.popupWindow_data_from.setBackgroundDrawable(new ColorDrawable(0));
                this.popupWindow_data_from.setOutsideTouchable(false);
                this.popupWindow_data_from.setFocusable(true);
            }
            int width = view.getWidth();
            this.tv_value.setText(this.data_from);
            this.popupWindow_data_from.showAsDropDown(view, (-DensityUtil.dip2px(this, 88.0f)) + width, 10);
            return;
        }
        if (view.getId() == R.id.cl_user) {
            this.uid = ((Long) view.getTag(R.id.first_id)).longValue();
            this.height = ((Float) view.getTag(R.id.second_id)).floatValue();
            this.age = ((Integer) view.getTag(R.id.th_id)).intValue();
            this.isMale = ((Boolean) view.getTag(R.id.fo_id)).booleanValue();
            this.popupWindow_userw.dismiss();
            loadData();
            return;
        }
        if (view.getId() == R.id.tv_name) {
            if (this.popupWindow_userw == null) {
                PopupWindow popupWindow2 = new PopupWindow(this);
                this.popupWindow_userw = popupWindow2;
                this.popupWindow_userw.setWidth(this.dp_185);
                this.popupWindow_userw.setHeight(-2);
                this.popupWindow_userw.setContentView(LayoutInflater.from(this).inflate(R.layout.sport_module_popupwindow_user_view, null));
                this.popupWindow_userw.setBackgroundDrawable(new ColorDrawable(0));
                this.popupWindow_userw.setOutsideTouchable(false);
                this.popupWindow_userw.setFocusable(true);
                this.ll_users = (LinearLayout) this.popupWindow_userw.getContentView().findViewById(R.id.ll_users);
                PopupWindow popupWindow3 = this.popupWindow_userw;
                AnonymousClass3 r0 = new OnDismissListener() {
                    public void onDismiss() {
                        WeightShowActivity.this.iv_user_top.setVisibility(4);
                    }
                };
                popupWindow3.setOnDismissListener(r0);
            }
            List<WeightUser> weightUsers = ModuleRouteWeightService.getInstance().getWeightUsers(UserConfig.getInstance().getNewUID());
            if (weightUsers != null && weightUsers.size() != 0) {
                this.ll_users.removeAllViews();
                for (int i = 0; i < weightUsers.size(); i++) {
                    View inflate2 = View.inflate(this, R.layout.sport_module_item_weight_user, null);
                    TextView tv_name2 = (TextView) inflate2.findViewById(R.id.tv_name);
                    TextView tv_icon = (TextView) inflate2.findViewById(R.id.tv_icon);
                    View cl_user = inflate2.findViewById(R.id.cl_user);
                    View iv_tick = inflate2.findViewById(R.id.iv_tick);
                    String name = ((WeightUser) weightUsers.get(i)).getName();
                    try {
                        tv_icon.setText((name + "").substring(0, 1));
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                        KLog.e(" name " + name);
                    }
                    if (((WeightUser) weightUsers.get(i)).getUid() == this.uid) {
                        iv_tick.setVisibility(0);
                    } else {
                        iv_tick.setVisibility(4);
                    }
                    tv_name2.setText(name + "");
                    cl_user.setTag(R.id.first_id, Long.valueOf(((WeightUser) weightUsers.get(i)).getUid()));
                    cl_user.setTag(R.id.second_id, Float.valueOf(((WeightUser) weightUsers.get(i)).getHeight()));
                    cl_user.setTag(R.id.th_id, Integer.valueOf(((WeightUser) weightUsers.get(i)).getAge()));
                    cl_user.setTag(R.id.fo_id, Boolean.valueOf(((WeightUser) weightUsers.get(i)).isMale()));
                    cl_user.setOnClickListener(this);
                    this.ll_users.addView(inflate2);
                }
                int viewHeight = DensityUtil.dip2px(this, 75.0f) * 10;
                if (viewHeight > this.dp_275) {
                    this.popupWindow_userw.setHeight(this.dp_275);
                } else {
                    this.popupWindow_userw.setHeight(viewHeight);
                }
                int width2 = view.getWidth();
                KLog.e((this.iv_user_top.getHeight() + DensityUtil.dip2px(this, 5.0f)) + "iv_user_top height");
                if (VERSION.SDK_INT < 24) {
                    this.popupWindow_userw.showAsDropDown(this.tv_name);
                } else {
                    int[] location = new int[2];
                    this.tv_name.getLocationOnScreen(location);
                    this.popupWindow_userw.showAsDropDown(this.tv_name, 0, location[0], location[1] + this.tv_name.getHeight());
                }
                ImageView imageView = this.iv_user_top;
                AnonymousClass4 r02 = new Runnable() {
                    public void run() {
                        WeightShowActivity.this.iv_user_top.setVisibility(0);
                    }
                };
                imageView.postDelayed(r02, 100);
            }
        }
    }

    public void setPresenter(WPrecenter presenter) {
    }

    public Context getContext() {
        return this;
    }

    public void showUIDatas(List<WeightShowData> weightDataList, float max) {
        KLog.e("showUIDatas " + weightDataList + "  " + max);
        dismissLoading();
        initDetailWeight();
        if (weightDataList == null || weightDataList.size() == 0) {
            this.iv_data_from.setVisibility(4);
        } else {
            this.iv_data_from.setVisibility(0);
        }
        double double1 = BaseSportUtils.getDouble1(UserConfig.getInstance().getTarget_weight());
        if (!UserConfig.getInstance().isMertric()) {
            double1 = BaseSportUtils.getDouble1(BaseSportUtils.getLbsFromKG((float) double1));
        }
        this.wvsWeight.setGoal(double1, this.tv_value_unit.getText().toString().trim(), max);
        if (this.uid == UserConfig.getInstance().getNewUID()) {
            this.wvsWeight.setShowGoal(true);
        } else {
            this.wvsWeight.setShowGoal(false);
        }
        if (weightDataList == null || weightDataList.size() == 0) {
            this.tv_no_data.setVisibility(0);
        } else {
            this.tv_no_data.setVisibility(8);
        }
        this.wvsWeight.setDatas(weightDataList);
        Iterator it = ModuleRouteWeightService.getInstance().getWeightUsers(UserConfig.getInstance().getNewUID()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WeightUser weightUser = (WeightUser) it.next();
            if (weightUser.getUid() == this.uid) {
                try {
                    this.tv_name.setText((weightUser.getName() + "").substring(0, 1));
                    break;
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                    KLog.e(" name " + weightUser.getName());
                }
            }
        }
        try {
            select_data((WeightShowData) weightDataList.get(weightDataList.size() - 1));
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            this.tv_time.setText("");
            this.tvWeightValue.setText(this.nf.format((double) 0.0f) + "");
        }
    }

    public void showRWSize(int integer) {
        if (this.tvUnarchived != null) {
            this.tvUnarchived.setText(integer + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.weight_unarchived_data));
        }
    }

    public void showLoading() {
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
        this.loadingDialog.show();
    }

    public void dismissLoading() {
        if (this.loadingDialog != null) {
            this.loadingDialog.dismiss();
        }
    }

    public void select_data(WeightShowData weightData) {
        KLog.e("select_data " + weightData);
        this.indexScaleBodyFat = this.weightPresenter.getIndexScaleBodyFat(weightData.old_index);
        this.data_from = this.indexScaleBodyFat.getData_from();
        KLog.e(this.indexScaleBodyFat);
        this.datas.clear();
        showDetailsWeight(this.indexScaleBodyFat);
        this.tvWeightValue.setText(this.nf.format((double) weightData.real_weight) + "");
        this.tv_time.setText(new DateUtil(this.indexScaleBodyFat.getRecord_date(), true).getYyyyMMdd_HHmmDate());
    }

    private void showDetailsWeight(ScaleBodyFat indexScaleBodyFat2) {
        boolean isNull = false;
        if (indexScaleBodyFat2 == null) {
            indexScaleBodyFat2 = new ScaleBodyFat();
            isNull = true;
        }
        float bmi = indexScaleBodyFat2.getBmi();
        if (!isNull && bmi == 0.0f) {
            double weight = (double) indexScaleBodyFat2.getWeight();
            double height2 = UserConfig.getInstance().getHeight();
            if (UserConfig.getInstance().getNewUID() != this.uid) {
                height2 = (double) this.height;
            }
            double v = weight / Math.pow(height2 / 100.0d, 2.0d);
            bmi = (float) BaseSportUtils.getDouble1((float) v);
            KLog.e("-- " + weight + "  " + height2 + "   " + bmi + "  " + v);
        }
        WeightDetailDataBean weightDetailDataBean = new WeightDetailDataBean(getContext(), 1, (double) BaseSportUtils.getBMIFatPercent(bmi), bmi);
        WeightDetailDataBean weightDetailDataBean1 = new WeightDetailDataBean(getContext(), 2, (double) BaseSportUtils.getWeightFatPercent(indexScaleBodyFat2.getBodyfat(), this.age, this.isMale), this.indexScaleBodyFat.getBodyfat());
        WeightDetailDataBean weightDetailDataBean2 = new WeightDetailDataBean(getContext(), 3, (double) BaseSportUtils.getWeightMusclePercent(indexScaleBodyFat2.getMuscule(), this.height, this.isMale), this.indexScaleBodyFat.getMuscule());
        WeightDetailDataBean weightDetailDataBean3 = new WeightDetailDataBean(getContext(), 4, (double) BaseSportUtils.getWeightWatherPercent(indexScaleBodyFat2.getWater(), this.isMale), indexScaleBodyFat2.getWater());
        WeightDetailDataBean weightDetailDataBean4 = new WeightDetailDataBean(getContext(), 5, (double) BaseSportUtils.getWeightBonePercent(indexScaleBodyFat2.getBone_weight(), indexScaleBodyFat2.getWeight(), this.isMale), indexScaleBodyFat2.getBone_weight());
        WeightDetailDataBean weightDetailDataBean5 = new WeightDetailDataBean(getContext(), 6, (double) BaseSportUtils.getWeightVisceral_fatPercent(indexScaleBodyFat2.getVisceral_fat()), (float) indexScaleBodyFat2.getVisceral_fat());
        WeightDetailDataBean weightDetailDataBean6 = new WeightDetailDataBean(getContext(), 7, (double) BaseSportUtils.getWeightMetabolismPercent(indexScaleBodyFat2.getCalorie(), this.isMale, this.age), indexScaleBodyFat2.getCalorie());
        this.datas.clear();
        this.datas.add(weightDetailDataBean);
        this.datas.add(weightDetailDataBean1);
        this.datas.add(weightDetailDataBean2);
        this.datas.add(weightDetailDataBean3);
        this.datas.add(weightDetailDataBean4);
        this.datas.add(weightDetailDataBean5);
        this.datas.add(weightDetailDataBean6);
        this.weightDataAdapter.notifyDataSetChanged();
    }
}
