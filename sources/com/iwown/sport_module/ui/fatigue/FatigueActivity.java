package com.iwown.sport_module.ui.fatigue;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.lib_common.views.TitleBar;
import com.iwown.lib_common.views.TitleBar.ImageAction;
import com.iwown.lib_common.views.fatigueview2.FatigueDataBean2;
import com.iwown.lib_common.views.fatigueview2.FatigueRecyclerView;
import com.iwown.lib_common.views.fatigueview2.FatigueRecyclerView.CallBack;
import com.iwown.lib_common.views.utils.StatusbarUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.fatigue.FatigueContract.FatiguePresenter1;
import com.iwown.sport_module.ui.fatigue.FatigueContract.FatigueView;
import com.iwown.sport_module.ui.skin_loader.FatigueSkinHandler;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.socks.library.KLog;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public class FatigueActivity extends AppCompatActivity implements FatigueView, OnClickListener {
    private CollapsingToolbarLayout cctlMain;
    /* access modifiers changed from: private */
    public String data_from;
    /* access modifiers changed from: private */
    public FatiguePresenter fatiguePresenter;
    private ImageView iv_data_from;
    /* access modifiers changed from: private */
    public LinearLayout ll_rlv_details;
    private LoadingDialog loadingDialog;
    private AppBarLayout mainAblAppBar;
    private Toolbar mainTbToolbar;
    private PopupWindow popupWindow_data_from;
    /* access modifiers changed from: private */
    public ConstraintLayout rlTop;
    private FatigueRecyclerView rlvChart;
    private TitleBar topTitleBar;
    /* access modifiers changed from: private */
    public TextView tvValue;
    private TextView tv_no_data;
    private TextView tv_value;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_fatigue);
        FatigueSkinHandler.getInstance().init();
        this.loadingDialog = new LoadingDialog(this);
        this.fatiguePresenter = new FatiguePresenter(this);
        initView();
    }

    private void initView() {
        StatusbarUtils.hideStatusBar(this);
        this.mainAblAppBar = (AppBarLayout) findViewById(R.id.main_abl_app_bar);
        this.cctlMain = (CollapsingToolbarLayout) findViewById(R.id.cctl_main);
        this.rlTop = (ConstraintLayout) findViewById(R.id.rl_top);
        this.ll_rlv_details = (LinearLayout) findViewById(R.id.ll_rlv_details);
        this.mainTbToolbar = (Toolbar) findViewById(R.id.main_tb_toolbar);
        this.topTitleBar = (TitleBar) findViewById(R.id.top_title_bar);
        this.tvValue = (TextView) findViewById(R.id.tv_value);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tvValue);
        this.iv_data_from = (ImageView) findViewById(R.id.iv_data_from);
        this.iv_data_from.setVisibility(8);
        this.rlvChart = (FatigueRecyclerView) findViewById(R.id.rlv_chart);
        this.tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        this.topTitleBar.addAction(new ImageAction(R.drawable.share_icon) {
            public void performAction(View view) {
                ScreenLongShareUtils.shareScreenByMultiViews(FatigueActivity.this, new View[]{FatigueActivity.this.rlTop, FatigueActivity.this.ll_rlv_details}, new int[]{FatigueActivity.this.getResources().getColor(R.color.heart_top), FatigueActivity.this.getResources().getColor(R.color.fatigue_history_bootom)});
            }
        });
        this.topTitleBar.setTitle(R.string.sport_module_fatigue_activity);
        this.topTitleBar.setLeftImageResource(R.mipmap.back3x);
        this.topTitleBar.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                FatigueActivity.this.finish();
            }
        });
        this.iv_data_from.setOnClickListener(this);
        this.iv_data_from.setVisibility(8);
        this.rlTop.setBackground(FatigueSkinHandler.getInstance().getFatigueSkin_Type_Top_Bottom_BG());
        initData();
    }

    private void initData() {
        this.rlvChart.setCallBack(new CallBack() {
            public void onCenterPosition(FatigueDataBean2 fatigueDataBean2) {
                KLog.e("onCenterPosition " + fatigueDataBean2);
                try {
                    FatigueActivity.this.tvValue.setText(fatigueDataBean2.min_value + HelpFormatter.DEFAULT_OPT_PREFIX + fatigueDataBean2.max_value);
                    FatigueActivity.this.showDetailDatas(fatigueDataBean2.json_details);
                    FatigueActivity.this.data_from = fatigueDataBean2.data_from;
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                    FatigueActivity.this.showDetailDatas(null);
                }
            }

            public void onStartPosition() {
                KLog.e("onStartPosition");
                FatigueActivity.this.fatiguePresenter.loadFatigueDatas();
            }
        });
        this.fatiguePresenter.loadFatigueDatas();
    }

    public void setPresenter(FatiguePresenter1 presenter) {
    }

    public void showDataOver() {
        ToastUtils.showShortToast((CharSequence) "Over");
    }

    public void showDatas(List<FatigueDataBean2> fatigueDatas, boolean isStart) {
        try {
            if (fatigueDatas.size() == 0) {
                this.tvValue.setVisibility(4);
                this.tv_no_data.setVisibility(0);
            } else {
                this.tvValue.setVisibility(0);
                this.tv_no_data.setVisibility(8);
            }
            if (fatigueDatas.size() == 0) {
                showDetailDatas(null);
            } else {
                showDetailDatas(((FatigueDataBean2) fatigueDatas.get(fatigueDatas.size() - 1)).json_details);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (isStart) {
            this.rlvChart.setData(fatigueDatas);
        } else {
            this.rlvChart.addBeginDatas(fatigueDatas);
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

    public Context getContext() {
        return this;
    }

    /* access modifiers changed from: private */
    public void showDetailDatas(String fatigue_json) {
        try {
            this.ll_rlv_details.removeAllViews();
            TextView textView = new TextView(this);
            textView.setText(getResources().getString(R.string.sport_module_fatigue_detial_tag));
            textView.setTextSize(1, 15.0f);
            textView.setTextColor(getResources().getColor(R.color.white));
            this.ll_rlv_details.addView(textView);
            List<FatigueData> fatigueData = sortList(JsonUtils.getListJson(fatigue_json, FatigueData.class));
            if (fatigueData != null) {
                for (int i = 0; i < fatigueData.size(); i++) {
                    View inflate = View.inflate(this, R.layout.sport_module_item_gatigue_detail, null);
                    TextView tv_time = (TextView) inflate.findViewById(R.id.tv_time);
                    TextView tv_value2 = (TextView) inflate.findViewById(R.id.tv_value);
                    FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_value2);
                    tv_time.setText(((FatigueData) fatigueData.get(i)).getMeasuretime());
                    tv_value2.setText(((FatigueData) fatigueData.get(i)).getValue() + "");
                    this.ll_rlv_details.addView(inflate);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        View tips = LayoutInflater.from(this).inflate(R.layout.sport_module_item_fatigue_tips, null, false);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(0, DensityUtil.dip2px(this, 10.0f), 0, 0);
        tips.setLayoutParams(layoutParams);
        this.ll_rlv_details.addView(tips);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.iv_data_from) {
            if (this.popupWindow_data_from == null) {
                this.popupWindow_data_from = new PopupWindow(view.getContext());
                this.popupWindow_data_from.setWidth(DensityUtil.dip2px(view.getContext(), 88.0f));
                this.popupWindow_data_from.setHeight(-2);
                View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.sport_module_popupwindow_data_form, null);
                this.tv_value = (TextView) inflate.findViewById(R.id.tv_value);
                this.popupWindow_data_from.setContentView(inflate);
                this.popupWindow_data_from.setBackgroundDrawable(new ColorDrawable(0));
                this.popupWindow_data_from.setOutsideTouchable(false);
                this.popupWindow_data_from.setFocusable(true);
            }
            this.tv_value.setText(this.data_from + "");
            this.popupWindow_data_from.showAsDropDown(view, (-DensityUtil.dip2px(view.getContext(), 88.0f)) + view.getWidth(), 10);
        }
    }

    private List<FatigueData> sortList(List<FatigueData> fatigueDataList) {
        if (fatigueDataList == null || fatigueDataList.size() == 0) {
            return null;
        }
        Collections.sort(fatigueDataList, new Comparator<FatigueData>() {
            public int compare(FatigueData index1, FatigueData index2) {
                String[] time = index1.getMeasuretime().split(":");
                String[] time2 = index2.getMeasuretime().split(":");
                int i = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
                int i2 = (Integer.parseInt(time2[0]) * 60) + Integer.parseInt(time2[1]);
                if (i > i2) {
                    return -1;
                }
                if (i != i2) {
                    return 1;
                }
                return 0;
            }
        });
        return fatigueDataList;
    }
}
