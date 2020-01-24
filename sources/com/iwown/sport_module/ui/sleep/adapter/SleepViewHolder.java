package com.iwown.sport_module.ui.sleep.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.sleepview.DSleepChartView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.skin_loader.SleepSkinHandler;
import com.iwown.sport_module.ui.sleep.AntGridView;
import com.iwown.sport_module.ui.sleep.SleepErrorFeedBackActivity;
import com.iwown.sport_module.ui.sleep.data.SleepBedTimeStatusBean;
import com.iwown.sport_module.ui.weight.WeightMsgDialog;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class SleepViewHolder extends ViewHolder implements OnClickListener {
    public final View cl_main = $(R.id.cl_main);
    public final View cl_main_bottom = $(R.id.cl_main_bottom);
    private final View ctl_top = $(R.id.ctl_top);
    public final DSleepChartView dcvSleep;
    public final AntGridView gvTimes;
    public final AntGridView gv_bed_time_status = ((AntGridView) $(R.id.gv_bed_time_status));
    public final ImageView iv_data_from = ((ImageView) $(R.id.iv_data_from));
    public final ImageView iv_error_feedback = ((ImageView) $(R.id.iv_error_feedback));
    public final ImageView iv_sleep_good = ((ImageView) $(R.id.iv_sleep_good));
    public final ImageView iv_sleep_so_bad = ((ImageView) $(R.id.iv_sleep_so_bad));
    public final ImageView iv_sleep_so_good = ((ImageView) $(R.id.iv_sleep_so_good));
    public final ImageView iv_sleep_soso = ((ImageView) $(R.id.iv_sleep_soso));
    public final LinearLayout ll_sleep_quality_analysis = ((LinearLayout) $(R.id.ll_sleep_quality_analysis));
    public PopupWindow popupWindow_data_from;
    private List<SleepBedTimeStatusBean> sleepBedTimeStatusBeans;
    private SleepDataDay sleepDataToday;
    private long startTime;
    public final ScrollView sv_main = ((ScrollView) $(R.id.sv_main));
    public final TextView tb_dcv_no_data = ((TextView) $(R.id.tb_dcv_no_data));
    private int total_deep_time;
    private int total_light_time;
    public final TextView tv_date_choose = ((TextView) $(R.id.tv_date_choose));
    public TextView tv_select_sleep_status = ((TextView) $(R.id.tv_select_sleep_status));
    public final TextView tv_slee_deep_p = ((TextView) $(R.id.tv_slee_deep_p));
    public final TextView tv_sleep_light_p = ((TextView) $(R.id.tv_sleep_light_p));
    private TextView tv_sleep_quality_analysis = ((TextView) $(R.id.tv_sleep_quality_analysis));
    public final TextView tv_sleep_score = ((TextView) $(R.id.tv_sleep_score));
    public final TextView tv_sleep_wake_p = ((TextView) $(R.id.tv_sleep_wake_p));
    public TextView tv_value;
    public final View vTop = $(R.id.v_top);
    private WeightMsgDialog weightMsgDialog;

    public SleepDataDay getSleepDataToday() {
        return this.sleepDataToday;
    }

    public void setSleepDataToday(SleepDataDay sleepDataToday2) {
        this.sleepDataToday = sleepDataToday2;
    }

    public SleepViewHolder(View itemView) {
        super(itemView);
        initEvaluation();
        this.dcvSleep = (DSleepChartView) $(R.id.dcv_sleep);
        this.gvTimes = (AntGridView) $(R.id.gv_times);
        this.gvTimes.setLineShow(true);
        HandlerColor();
        this.iv_data_from.setOnClickListener(this);
        this.iv_error_feedback.setOnClickListener(this);
        this.ctl_top.setOnClickListener(this);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tv_sleep_score);
    }

    private void HandlerColor() {
        this.vTop.setBackground(SleepSkinHandler.getInstance().getSkinTopBG());
        this.dcvSleep.setColors(SleepSkinHandler.getInstance().getSleepSkin_Type_Chart_Tag_Wake_BG(), SleepSkinHandler.getInstance().getSleepSkin_Type_Chart_Tag_Light_BG(), SleepSkinHandler.getInstance().getSleepSkin_Type_Chart_Tag_Deep_BG());
    }

    private void initEvaluation() {
        this.iv_sleep_so_bad.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        this.iv_sleep_soso.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        this.iv_sleep_good.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        this.iv_sleep_so_good.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
    }

    /* access modifiers changed from: protected */
    public <T extends View> T $(@IdRes int id) {
        return this.itemView.findViewById(id);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.iv_data_from) {
            if (this.popupWindow_data_from == null) {
                this.popupWindow_data_from = new PopupWindow(view.getContext());
                this.popupWindow_data_from.setWidth(DensityUtil.dip2px(view.getContext(), 88.0f));
                this.popupWindow_data_from.setHeight(-2);
                View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.sport_module_popupwindow_data_form_top, null);
                this.tv_value = (TextView) inflate.findViewById(R.id.tv_value);
                this.popupWindow_data_from.setContentView(inflate);
                this.popupWindow_data_from.setBackgroundDrawable(new ColorDrawable(0));
                this.popupWindow_data_from.setOutsideTouchable(false);
                this.popupWindow_data_from.setFocusable(true);
            }
            this.tv_value.setText(((String) this.iv_data_from.getTag(R.id.first_id)) + "");
            this.popupWindow_data_from.showAsDropDown(view, ((-DensityUtil.dip2px(view.getContext(), 88.0f)) / 2) + (view.getWidth() / 2), 10);
        } else if (view.getId() == R.id.iv_error_feedback) {
            Intent intent = new Intent(view.getContext(), SleepErrorFeedBackActivity.class);
            intent.putExtra("sleepDataToday", this.sleepDataToday);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.ctl_top) {
            if (this.weightMsgDialog == null) {
                this.weightMsgDialog = new WeightMsgDialog(view.getContext(), true);
                this.weightMsgDialog.setTopBG(R.drawable.sleep_score_dialog_top_bg);
                this.weightMsgDialog.setShowMsg(view.getResources().getString(R.string.sport_module_sleep_score_dialog_top_msg), view.getResources().getString(R.string.sport_module_sleep_score_msg));
            }
            this.weightMsgDialog.show();
        } else {
            Object tag1 = view.getTag(R.id.lib_common_first_tag);
            if (tag1 != null) {
                setShowEvaluation(view, !((Boolean) tag1).booleanValue());
            }
        }
    }

    public void setShowEvaluation(View view, boolean b) {
        this.iv_sleep_so_bad.setImageResource(R.mipmap.sleep_so_bad00);
        this.iv_sleep_soso.setImageResource(R.mipmap.sleep_soso00);
        this.iv_sleep_good.setImageResource(R.mipmap.sleep_good00);
        this.iv_sleep_so_good.setImageResource(R.mipmap.sleep_so_good00);
        this.iv_sleep_so_bad.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        this.iv_sleep_soso.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        this.iv_sleep_good.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        this.iv_sleep_so_good.setTag(R.id.lib_common_first_tag, Boolean.valueOf(false));
        view.setTag(R.id.lib_common_first_tag, Boolean.valueOf(b));
        this.tv_select_sleep_status.setVisibility(0);
        if (!b) {
            KLog.e("------不可见");
            this.tv_select_sleep_status.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.sleep_good, 0, 0);
            this.tv_select_sleep_status.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            this.tv_select_sleep_status.setTag(R.id.first_id, Integer.valueOf(0));
            this.tv_select_sleep_status.setVisibility(4);
        } else if (view.getId() == R.id.iv_sleep_so_bad) {
            this.iv_sleep_so_bad.setImageResource(R.mipmap.sleep_so_bad0);
            this.tv_select_sleep_status.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.sleep_so_bad, 0, 0);
            this.tv_select_sleep_status.setText(this.tv_select_sleep_status.getResources().getString(R.string.sport_module_sleep_evaluation_bad));
            this.tv_select_sleep_status.setTag(R.id.first_id, Integer.valueOf(1));
        } else if (view.getId() == R.id.iv_sleep_soso) {
            this.iv_sleep_soso.setImageResource(R.mipmap.sleep_soso0);
            this.tv_select_sleep_status.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.sleep_soso, 0, 0);
            this.tv_select_sleep_status.setText(this.tv_select_sleep_status.getResources().getString(R.string.sport_module_sleep_evaluation_soso));
            this.tv_select_sleep_status.setTag(R.id.first_id, Integer.valueOf(2));
        } else if (view.getId() == R.id.iv_sleep_good) {
            this.iv_sleep_good.setImageResource(R.mipmap.sleep_good0);
            this.tv_select_sleep_status.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.sleep_good, 0, 0);
            this.tv_select_sleep_status.setText(this.tv_select_sleep_status.getResources().getString(R.string.sport_module_sleep_evaluation_good));
            this.tv_select_sleep_status.setTag(R.id.first_id, Integer.valueOf(3));
        } else if (view.getId() == R.id.iv_sleep_so_good) {
            this.iv_sleep_so_good.setImageResource(R.mipmap.sleep_so_good0);
            this.tv_select_sleep_status.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.sleep_so_good, 0, 0);
            this.tv_select_sleep_status.setText(this.tv_select_sleep_status.getResources().getString(R.string.sport_module_sleep_evaluation_excellent));
            this.tv_select_sleep_status.setTag(R.id.first_id, Integer.valueOf(4));
        }
        int db_id = ((Integer) this.tv_select_sleep_status.getTag(R.id.second_id)).intValue();
        if (db_id != 0) {
            ModuleRouteSleepService.getInstance().updateSleepFeelType(db_id, ((Integer) this.tv_select_sleep_status.getTag(R.id.first_id)).intValue());
            showSleepQuality();
        }
    }

    public void setClickEvaluation(boolean b) {
        if (!b) {
            this.iv_sleep_so_bad.setOnClickListener(null);
            this.iv_sleep_soso.setOnClickListener(null);
            this.iv_sleep_good.setOnClickListener(null);
            this.iv_sleep_so_good.setOnClickListener(null);
            return;
        }
        this.iv_sleep_so_bad.setOnClickListener(this);
        this.iv_sleep_soso.setOnClickListener(this);
        this.iv_sleep_good.setOnClickListener(this);
        this.iv_sleep_so_good.setOnClickListener(this);
    }

    public void showSleepQuality() {
        if (this.sleepBedTimeStatusBeans != null && this.startTime != 0) {
            showSleepQuality(this, this.total_deep_time, this.total_light_time, this.startTime, this.sleepBedTimeStatusBeans);
        }
    }

    public void showSleepQuality(SleepViewHolder holder, int total_deep_time2, int total_light_time2, long startTime2, List<SleepBedTimeStatusBean> sleepBedTimeStatusBeans2) {
        this.total_deep_time = total_deep_time2;
        this.total_light_time = total_light_time2;
        this.startTime = startTime2;
        this.sleepBedTimeStatusBeans = sleepBedTimeStatusBeans2;
        holder.ll_sleep_quality_analysis.removeAllViews();
        int sleep_time = (total_deep_time2 + total_light_time2) / 60;
        KLog.e("  showSleepQuality " + total_deep_time2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + total_light_time2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + sleep_time + startTime2);
        DateUtil startDateUtil = new DateUtil(startTime2, true);
        List<Integer> status = new ArrayList<>();
        int index = 0;
        for (SleepBedTimeStatusBean sleepBedTimeStatusBean : sleepBedTimeStatusBeans2) {
            if (sleepBedTimeStatusBean.selected) {
                status.add(Integer.valueOf(index));
            }
            index++;
        }
        int type = 0;
        try {
            type = ((Integer) holder.tv_select_sleep_status.getTag(R.id.first_id)).intValue();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        KLog.e("showSleepQuality " + type + "  " + sleep_time + "  " + startDateUtil.getHour());
        List<Integer> selects = new ArrayList<>();
        if (sleep_time >= 7 && startDateUtil.getHour() < 24 && startDateUtil.getHour() >= 18 && ((type >= 3 || type == 0) && status.size() == 0)) {
            addQuality(holder.ll_sleep_quality_analysis, 4, sleepBedTimeStatusBeans2);
            selects.add(Integer.valueOf(4));
        }
        if (sleep_time < 7) {
            addQuality(holder.ll_sleep_quality_analysis, 1, sleepBedTimeStatusBeans2);
            selects.add(Integer.valueOf(1));
        }
        if (startDateUtil.getHour() >= 0 && startDateUtil.getHour() < 18) {
            addQuality(holder.ll_sleep_quality_analysis, 2, sleepBedTimeStatusBeans2);
            selects.add(Integer.valueOf(2));
        }
        if (type <= 2 && type > 0) {
            addQuality(holder.ll_sleep_quality_analysis, 5, sleepBedTimeStatusBeans2);
            selects.add(Integer.valueOf(5));
        }
        if (type == 0 && (selects.contains(Integer.valueOf(1)) || selects.contains(Integer.valueOf(2)))) {
            addQuality(holder.ll_sleep_quality_analysis, 5, sleepBedTimeStatusBeans2);
            selects.add(Integer.valueOf(5));
        }
        if (type > 0 && type <= 2 && status.size() > 0) {
            addQuality(holder.ll_sleep_quality_analysis, 3, sleepBedTimeStatusBeans2);
            selects.add(Integer.valueOf(3));
        }
    }

    private void addQuality(LinearLayout ll_sleep_quality_analysis2, int type, List<SleepBedTimeStatusBean> sleepBedTimeStatusBeans2) {
        String title;
        KLog.e("addQuality " + type);
        View inflate = LayoutInflater.from(ll_sleep_quality_analysis2.getContext()).inflate(R.layout.sport_module_item_sleep_quality, null, false);
        ImageView iv_icon = (ImageView) inflate.findViewById(R.id.iv_icon);
        TextView tv_msg = (TextView) inflate.findViewById(R.id.tv_msg);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        Context context = ll_sleep_quality_analysis2.getContext();
        String msg = "";
        String str = "";
        if (type == 1) {
            msg = context.getResources().getString(R.string.sport_module_sleep_quality1);
            title = context.getResources().getString(R.string.sport_module_sleep_quality1_0);
            iv_icon.setImageResource(R.drawable.sleep_short);
        } else if (type == 2) {
            msg = context.getResources().getString(R.string.sport_module_sleep_quality2);
            title = context.getResources().getString(R.string.sport_module_sleep_quality2_0);
            iv_icon.setImageResource(R.drawable.too_late_icon);
        } else if (type == 3) {
            title = context.getResources().getString(R.string.sport_module_sleep_quality33_0);
            for (int i = 0; i < sleepBedTimeStatusBeans2.size(); i++) {
                if (((SleepBedTimeStatusBean) sleepBedTimeStatusBeans2.get(i)).selected) {
                    if (i == 0) {
                        msg = msg + context.getResources().getString(R.string.sport_module_sleep_quality30);
                    } else if (i == 1) {
                        msg = msg + context.getResources().getString(R.string.sport_module_sleep_quality31);
                    } else if (i == 2) {
                        msg = msg + context.getResources().getString(R.string.sport_module_sleep_quality32);
                    } else if (i == 3 || i == 4) {
                        if (!msg.contains(context.getResources().getString(R.string.sport_module_sleep_quality33))) {
                            msg = msg + context.getResources().getString(R.string.sport_module_sleep_quality33);
                        }
                    } else if ((i == 5 || i == 7) && !msg.contains(context.getResources().getString(R.string.sport_module_sleep_quality35))) {
                        msg = msg + context.getResources().getString(R.string.sport_module_sleep_quality35);
                    }
                }
            }
            iv_icon.setImageResource(R.drawable.poor_quality_icon);
        } else if (type == 4) {
            msg = context.getResources().getString(R.string.sport_module_sleep_quality4);
            title = context.getResources().getString(R.string.sport_module_sleep_quality4_0);
            iv_icon.setImageResource(R.drawable.good_sleep_icon);
        } else if (type == 5) {
            title = context.getResources().getString(R.string.sport_module_sleep_quality5_0);
            msg = context.getResources().getString(R.string.sport_module_sleep_quality5);
            iv_icon.setImageResource(R.drawable.ways_icon);
        } else {
            return;
        }
        if (TextUtils.isEmpty(title)) {
            this.tv_sleep_quality_analysis.setVisibility(8);
        } else {
            this.tv_sleep_quality_analysis.setVisibility(0);
        }
        tv_title.setText(title + "");
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(8);
        } else {
            tv_title.setVisibility(0);
        }
        tv_msg.setText(msg + "");
        ll_sleep_quality_analysis2.addView(inflate);
    }
}
