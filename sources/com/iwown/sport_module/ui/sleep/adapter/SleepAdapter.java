package com.iwown.sport_module.ui.sleep.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepDownData2;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.lib_common.views.sleepview.DSleepChartView.SleepData;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.sleep.AntGridView;
import com.iwown.sport_module.ui.sleep.SleepDataCacheHandler;
import com.iwown.sport_module.ui.sleep.data.SleepBean;
import com.iwown.sport_module.ui.sleep.data.SleepBedTimeStatusBean;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SleepAdapter extends Adapter<SleepViewHolder> implements OnItemClickListener {
    DateClickListener dateClickListener;
    private GrideBedTimeStatusAdapter grideBedTimeStatusAdapter;
    private SleepViewHolder holder;
    private int mCurrentPosition;
    private ScrollView scroll_view;
    private ArrayList<SleepBedTimeStatusBean> sleepBedTimeStatusBeans;
    private SleepDataDay sleepDataToday;
    private GradientDrawable topBG;
    private int total_all_time;
    private int total_deep_time;
    private int total_light_time;

    public interface DateClickListener {
        void onDateClickListener();
    }

    public SleepAdapter(DateClickListener dateClickListener2) {
        this.dateClickListener = dateClickListener2;
    }

    public ScrollView getScroll_view() {
        return this.scroll_view;
    }

    public SleepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SleepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_module_item_sleep_today, parent, false));
    }

    public void onBindViewHolder(SleepViewHolder holder2, int position) {
        if (position == this.mCurrentPosition) {
            this.scroll_view = holder2.sv_main;
            initData(holder2);
        }
    }

    private void initData(SleepViewHolder holder2) {
        int type;
        SleepDataCacheHandler.init(holder2.dcvSleep.getContext());
        showDateTimeUI(holder2.tv_date_choose, this.sleepDataToday);
        holder2.cl_main_bottom.setVisibility(8);
        if (this.sleepDataToday == null || this.sleepDataToday.sleepDownData1 == null) {
            holder2.tv_select_sleep_status.setTag(R.id.second_id, Integer.valueOf(0));
            showSleepEvalution(holder2, 0);
            KLog.e("initData data null");
            holder2.dcvSleep.updateSleepDatas(null);
            updateTimeAdapter(holder2.gvTimes, 0, 0, 0, 0);
            showSleepPecent(holder2, 0, 0, 0);
            holder2.tv_sleep_score.setText("0");
            holder2.tb_dcv_no_data.setVisibility(0);
            holder2.iv_data_from.setVisibility(4);
            holder2.setClickEvaluation(false);
            return;
        }
        holder2.setSleepDataToday(this.sleepDataToday);
        holder2.cl_main_bottom.setVisibility(0);
        if (TextUtils.isEmpty(this.sleepDataToday.data_from)) {
            holder2.iv_data_from.setVisibility(4);
        } else {
            holder2.iv_data_from.setVisibility(0);
            holder2.iv_data_from.setTag(R.id.first_id, this.sleepDataToday.data_from);
        }
        holder2.setClickEvaluation(true);
        holder2.tv_select_sleep_status.setTag(R.id.second_id, Integer.valueOf(this.sleepDataToday.db_id));
        ArrayList arrayList = new ArrayList();
        long start_time = this.sleepDataToday.sleepDownData1.getStart_time() * 1000;
        this.total_deep_time = 0;
        this.total_light_time = 0;
        this.total_all_time = 0;
        if (this.sleepDataToday.sleepDownData1.getSleep_segment() == null || this.sleepDataToday.sleepDownData1.getSleep_segment().size() == 0) {
            this.total_light_time = (int) ((this.sleepDataToday.sleepDownData1.getEnd_time() - this.sleepDataToday.sleepDownData1.getStart_time()) / 60);
            arrayList.add(new SleepData(this.sleepDataToday.sleepDownData1.getStart_time() * 1000, this.sleepDataToday.sleepDownData1.getEnd_time() * 1000, 2));
        } else {
            for (SleepDownData2 sleepDownData2 : this.sleepDataToday.sleepDownData1.getSleep_segment()) {
                long startTime = start_time + ((long) (sleepDownData2.getSt() * 60 * 1000));
                long endTime = start_time + ((long) (sleepDownData2.getEt() * 60 * 1000));
                int type2 = sleepDownData2.getType();
                if (type2 == 3) {
                    type = 3;
                    this.total_deep_time = (int) (((long) this.total_deep_time) + ((endTime - startTime) / 60000));
                } else if (type2 == 4) {
                    type = 2;
                    this.total_light_time = (int) (((long) this.total_light_time) + ((endTime - startTime) / 60000));
                } else {
                    type = 1;
                }
                arrayList.add(new SleepData(startTime, endTime, type));
            }
        }
        try {
            this.total_all_time = (int) ((((SleepData) arrayList.get(arrayList.size() - 1)).endTime - ((SleepData) arrayList.get(0)).startTime) / 60000);
            if (DateUtil.isSameDay(new Date(), new Date(this.sleepDataToday.time_unix * 1000)) && UserConfig.getInstance().getToDaySleepTimeMin() != this.total_all_time) {
                ToastUtils.showShortToast((CharSequence) "total_all_time " + this.total_all_time);
                HealthDataEventBus.updateHealthSleepEvent();
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        int total_wake_time = (this.total_all_time - this.total_deep_time) - this.total_light_time;
        if (total_wake_time < 0) {
            total_wake_time = 0;
        }
        KLog.e(this.total_all_time + "  " + this.total_light_time);
        showSleepEvalution(holder2, this.sleepDataToday.sleepDownData1.getFeel_type());
        if (arrayList == null || arrayList.size() == 0) {
            holder2.tb_dcv_no_data.setVisibility(0);
        } else {
            holder2.tb_dcv_no_data.setVisibility(8);
        }
        holder2.dcvSleep.updateSleepDatas(arrayList);
        showSleepPecent(holder2, this.total_all_time, this.total_deep_time, this.total_light_time);
        updateTimeAdapter(holder2.gvTimes, this.total_all_time, this.total_deep_time, this.total_light_time, total_wake_time);
        holder2.tv_sleep_score.setText(this.sleepDataToday.sleepDownData1.getScore() + "");
        this.sleepBedTimeStatusBeans = new ArrayList<>();
        this.sleepBedTimeStatusBeans.addAll(SleepDataCacheHandler.getSleepBedTimeStatusBeans());
        try {
            Iterator it = JsonUtils.getListJson(this.sleepDataToday.sleepDownData1.getAction(), Integer.class).iterator();
            while (it.hasNext()) {
                ((SleepBedTimeStatusBean) this.sleepBedTimeStatusBeans.get(((Integer) it.next()).intValue() - 1)).selected = true;
            }
        } catch (Exception e2) {
        }
        this.grideBedTimeStatusAdapter = new GrideBedTimeStatusAdapter(this.sleepBedTimeStatusBeans, holder2.gvTimes.getContext());
        holder2.gv_bed_time_status.setAdapter(this.grideBedTimeStatusAdapter);
        holder2.gv_bed_time_status.setOnItemClickListener(this);
        holder2.showSleepQuality(holder2, this.total_deep_time, this.total_light_time, this.sleepDataToday.sleepDownData1.getStart_time(), this.sleepBedTimeStatusBeans);
        this.holder = holder2;
    }

    private void showSleepEvalution(SleepViewHolder holder2, int feel_type) {
        if (feel_type == 1) {
            holder2.setShowEvaluation(holder2.iv_sleep_so_bad, true);
        } else if (feel_type == 2) {
            holder2.setShowEvaluation(holder2.iv_sleep_soso, true);
        } else if (feel_type == 3) {
            holder2.setShowEvaluation(holder2.iv_sleep_good, true);
        } else if (feel_type == 4) {
            holder2.setShowEvaluation(holder2.iv_sleep_so_good, true);
        } else {
            holder2.setShowEvaluation(holder2.iv_sleep_so_bad, false);
            holder2.setShowEvaluation(holder2.iv_sleep_soso, false);
            holder2.setShowEvaluation(holder2.iv_sleep_good, false);
            holder2.setShowEvaluation(holder2.iv_sleep_so_good, false);
        }
    }

    private void showSleepPecent(SleepViewHolder holder2, int total_all_time2, int total_deep_time2, int total_light_time2) {
        if (total_all_time2 == 0) {
            holder2.tv_slee_deep_p.setText("0%");
            holder2.tv_sleep_light_p.setText("0%");
            holder2.tv_sleep_wake_p.setText("0%");
            return;
        }
        int deep_p = Math.round((((float) total_deep_time2) * 100.0f) / ((float) total_all_time2));
        int light_p = Math.round((((float) total_light_time2) * 100.0f) / ((float) total_all_time2));
        holder2.tv_slee_deep_p.setText(deep_p + "%");
        holder2.tv_sleep_light_p.setText(light_p + "%");
        int wake_p = (100 - deep_p) - light_p;
        if (wake_p < 0) {
            wake_p = 0;
        }
        holder2.tv_sleep_wake_p.setText(wake_p + "%");
    }

    private void showDateTimeUI(TextView tv_date_choose, SleepDataDay sleepDataToday2) {
        if (sleepDataToday2 != null && sleepDataToday2.time_unix > 0) {
            DateUtil dateUtil = new DateUtil(sleepDataToday2.time_unix, true);
            if (DateUtil.isSameDay(new Date(), new Date(dateUtil.getTimestamp()))) {
                tv_date_choose.setText(tv_date_choose.getResources().getString(R.string.sport_module_Today));
            } else {
                tv_date_choose.setText(dateUtil.getY_M_D());
            }
            tv_date_choose.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (SleepAdapter.this.dateClickListener != null) {
                        SleepAdapter.this.dateClickListener.onDateClickListener();
                    }
                }
            });
        }
    }

    private void updateTimeAdapter(AntGridView gvTimes, int total_all_time2, int total_deep_time2, int total_light_time2, int total_wake_time) {
        SleepBean sleepBean = new SleepBean(gvTimes.getContext(), 0, total_all_time2);
        SleepBean sleepBean1 = new SleepBean(gvTimes.getContext(), 1, total_deep_time2);
        SleepBean sleepBean2 = new SleepBean(gvTimes.getContext(), 2, total_light_time2);
        SleepBean sleepBean3 = new SleepBean(gvTimes.getContext(), 3, total_wake_time);
        List<SleepBean> sleepTimes = new ArrayList<>();
        sleepTimes.add(sleepBean);
        sleepTimes.add(sleepBean1);
        sleepTimes.add(sleepBean2);
        sleepTimes.add(sleepBean3);
        gvTimes.setAdapter(new GrideTimesAdapter(sleepTimes, gvTimes.getContext(), R.layout.sport_module_item_sleep_time));
    }

    public void setRecyclerViewAndData(RecyclerViewPager recyclerView, SleepDataDay sleepDataToday2) {
        this.sleepDataToday = sleepDataToday2;
        this.mCurrentPosition = recyclerView.getCurrentPosition();
        try {
            onBindViewHolder((SleepViewHolder) recyclerView.findViewHolderForAdapterPosition(this.mCurrentPosition), this.mCurrentPosition);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static int getLastPosition() {
        return 2147483646;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (this.holder != null && this.sleepDataToday != null && this.sleepDataToday.sleepDownData1 != null) {
            KLog.e("  " + i);
            ((SleepBedTimeStatusBean) this.sleepBedTimeStatusBeans.get(i)).selected = !((SleepBedTimeStatusBean) this.sleepBedTimeStatusBeans.get(i)).selected;
            this.grideBedTimeStatusAdapter.notifyDataSetChanged();
            List<Integer> actions = new ArrayList<>();
            for (int j = 0; j < this.sleepBedTimeStatusBeans.size(); j++) {
                if (((SleepBedTimeStatusBean) this.sleepBedTimeStatusBeans.get(j)).selected) {
                    actions.add(Integer.valueOf(j + 1));
                }
            }
            ModuleRouteSleepService.getInstance().updateSleepAction(this.sleepDataToday.db_id, actions);
            this.holder.showSleepQuality(this.holder, this.total_deep_time, this.total_light_time, this.sleepDataToday.sleepDownData1.getStart_time(), this.sleepBedTimeStatusBeans);
        }
    }
}
