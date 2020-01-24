package com.iwown.sport_module.ui.heart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.HeartShowData.SportHeart;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.heartview.DHeartChartView;
import com.iwown.lib_common.views.heartview.DlineDataBean;
import com.iwown.lib_common.views.heartview.HeartColumnView;
import com.iwown.lib_common.views.heartview.HeartColumnView.HeartTypeValueData;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.iwown.sport_module.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.cli.HelpFormatter;

public class HeartAdapter extends Adapter<SleepViewHolder> {
    DateClickListener dateClickListener;
    private HeartShowData heartShowData;
    private int mCurrentPosition;
    private ScrollView sv_main;

    public interface DateClickListener {
        void onDateClickListener();
    }

    public static class SleepViewHolder extends ViewHolder implements OnClickListener {
        /* access modifiers changed from: private */
        public DHeartChartView dcv_heart = ((DHeartChartView) $(R.id.dcv_heart));
        /* access modifiers changed from: private */
        public HeartColumnView hcvChart = ((HeartColumnView) $(R.id.hcv_chart));
        /* access modifiers changed from: private */
        public ImageView ivDataFrom = ((ImageView) $(R.id.iv_data_from));
        /* access modifiers changed from: private */
        public LinearLayout llHeartDetail = ((LinearLayout) $(R.id.ll_heart_detail));
        private PopupWindow popupWindow_data_from;
        private ConstraintLayout rlTop = ((ConstraintLayout) $(R.id.rl_top));
        /* access modifiers changed from: private */
        public final ScrollView sv_main = ((ScrollView) $(R.id.sv_main));
        private TextView tvAvg = ((TextView) $(R.id.tv_avg));
        /* access modifiers changed from: private */
        public TextView tvAvgValue = ((TextView) $(R.id.tv_avg_value));
        /* access modifiers changed from: private */
        public TextView tvDateCenter = ((TextView) $(R.id.tv_date_center));
        private TextView tvHighest = ((TextView) $(R.id.tv_highest));
        /* access modifiers changed from: private */
        public TextView tvHighestValue = ((TextView) $(R.id.tv_highest_value));
        private TextView tvLowest = ((TextView) $(R.id.tv_lowest));
        /* access modifiers changed from: private */
        public TextView tvLowestValue = ((TextView) $(R.id.tv_lowest_value));
        /* access modifiers changed from: private */
        public TextView tv_no_data = ((TextView) $(R.id.tv_no_data));
        private TextView tv_value;

        public SleepViewHolder(View itemView) {
            super(itemView);
            this.ivDataFrom.setOnClickListener(this);
            this.tvDateCenter.setOnClickListener(this);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tvAvgValue, this.tvLowestValue, this.tvHighestValue);
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
                    View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.sport_module_popupwindow_data_form, null);
                    this.tv_value = (TextView) inflate.findViewById(R.id.tv_value);
                    this.tv_value.setBackgroundResource(R.drawable.data_from_bg_red);
                    this.tv_value.setTextColor(Color.parseColor("#D7592F"));
                    try {
                        this.tv_value.setText((String) this.ivDataFrom.getTag(R.id.first_id));
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    this.popupWindow_data_from.setContentView(inflate);
                    this.popupWindow_data_from.setBackgroundDrawable(new ColorDrawable(0));
                    this.popupWindow_data_from.setOutsideTouchable(false);
                    this.popupWindow_data_from.setFocusable(true);
                }
                this.popupWindow_data_from.showAsDropDown(view, (-DensityUtil.dip2px(view.getContext(), 88.0f)) + view.getWidth(), 10);
            }
        }
    }

    public HeartAdapter(DateClickListener dateClickListener2) {
        this.dateClickListener = dateClickListener2;
    }

    public SleepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SleepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_module_fragment_heart, parent, false));
    }

    public void onBindViewHolder(SleepViewHolder holder, int position) {
        if (position == this.mCurrentPosition) {
            this.sv_main = holder.sv_main;
            initData(holder);
        }
    }

    private void initData(SleepViewHolder holder) {
        int avg;
        List<DlineDataBean> datas = new ArrayList<>();
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        long unixTimestamp = dateUtil1.getUnixTimestamp();
        int highest = 0;
        int lowest = Integer.MAX_VALUE;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < this.heartShowData.detail_data.size(); i++) {
            Integer integer = (Integer) this.heartShowData.detail_data.get(i);
            datas.add(new DlineDataBean(((long) (i * ServiceErrorCode.YOU_AND_ME_IS_FRIEND)) + unixTimestamp, integer.intValue()));
            if (integer.intValue() > highest && integer.intValue() <= 200) {
                highest = integer.intValue();
            }
            if (integer.intValue() < lowest && integer.intValue() >= 35) {
                lowest = integer.intValue();
            }
            if (integer.intValue() != 0 && integer.intValue() >= 35 && integer.intValue() <= 200) {
                sum += integer.intValue();
                count++;
            }
        }
        if (lowest == Integer.MAX_VALUE) {
            lowest = 0;
        }
        if (count == 0) {
            avg = 0;
        } else {
            avg = sum / count;
        }
        holder.tvAvgValue.setText(String.valueOf(avg));
        holder.tvLowestValue.setText(String.valueOf(lowest));
        holder.tvHighestValue.setText(String.valueOf(highest));
        holder.ivDataFrom.setTag(R.id.first_id, this.heartShowData.data_from + "");
        showDateTimeUI(holder.tvDateCenter, this.heartShowData);
        if (this.heartShowData.detail_data == null || this.heartShowData.detail_data.size() == 0) {
            holder.ivDataFrom.setVisibility(4);
            holder.tv_no_data.setVisibility(0);
        } else {
            holder.tv_no_data.setVisibility(8);
            holder.ivDataFrom.setVisibility(0);
        }
        holder.dcv_heart.setDatas(datas, this.heartShowData.y_titles);
        showSportDetails(holder, holder.dcv_heart.getContext(), this.heartShowData);
    }

    private int getTypeByLimit(int value, int[] values) {
        for (int i = 1; i < values.length; i++) {
            if (value <= values[i]) {
                return i;
            }
        }
        return -1;
    }

    private void showSportDetails(SleepViewHolder holder, Context context, HeartShowData heartShowData2) {
        Map<Integer, SportHeart> sportHeartMap = heartShowData2.sportHeartMap;
        Map<Integer, Integer> maps = new LinkedHashMap<>();
        int[] values = new int[7];
        int i = 0;
        while (true) {
            if (i >= 7) {
                break;
            } else if (i == 6) {
                values[i] = ((SportHeart) sportHeartMap.get(Integer.valueOf(i))).range_end;
                maps.put(Integer.valueOf(7), Integer.valueOf(0));
                break;
            } else {
                SportHeart sportHeart = (SportHeart) sportHeartMap.get(Integer.valueOf(i + 1));
                if (sportHeart != null) {
                    KLog.e((i + 1) + "  " + sportHeart);
                    values[i] = sportHeart.range_start;
                    maps.put(Integer.valueOf(i + 1), Integer.valueOf(sportHeart.activity));
                }
                i++;
            }
        }
        KLog.e(Arrays.toString(values));
        KLog.e(maps);
        String[] sleep_tags = context.getResources().getStringArray(R.array.sport_module_sleep_tags);
        List<HeartTypeValueData> heartTypeValueDataList = new ArrayList<>();
        for (Integer key : maps.keySet()) {
            heartTypeValueDataList.add(new HeartTypeValueData(((Integer) maps.get(key)).intValue(), key.intValue()));
        }
        showDetailDatas(holder, heartTypeValueDataList, sleep_tags, values);
    }

    public void showDetailDatas(SleepViewHolder holder, List<HeartTypeValueData> heartTypeValueDataList, String[] sleep_tags, int[] values) {
        holder.hcvChart.setLimit_values(values);
        holder.hcvChart.setDatas(heartTypeValueDataList);
        int[] limit_values_color = holder.hcvChart.getLimit_values_color();
        holder.llHeartDetail.removeAllViews();
        int[] limit_values = holder.hcvChart.getLimit_values();
        for (int i = 0; i < sleep_tags.length; i++) {
            View inflate = LayoutInflater.from(holder.llHeartDetail.getContext()).inflate(R.layout.sport_module_item_heart_detial, null, false);
            View viewById = inflate.findViewById(R.id.v_line);
            TextView tv_type_name = (TextView) inflate.findViewById(R.id.tv_type_name);
            TextView tv_type_range_value = (TextView) inflate.findViewById(R.id.tv_type_range_value);
            TextView tv_value_min = (TextView) inflate.findViewById(R.id.tv_value_min);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_value_min);
            viewById.setBackgroundColor(limit_values_color[i]);
            tv_value_min.setText(((HeartTypeValueData) heartTypeValueDataList.get(i)).value + "");
            if (i + 1 <= limit_values.length - 1) {
                int limit_value = limit_values[i];
                if (i > 0) {
                    limit_value++;
                }
                tv_type_range_value.setText(limit_value + HelpFormatter.DEFAULT_OPT_PREFIX + limit_values[i + 1] + "bpm");
            }
            tv_type_name.setText(sleep_tags[i] + "");
            holder.llHeartDetail.addView(inflate);
        }
    }

    @SuppressLint({"StringFormatInvalid"})
    private void showDateTimeUI(TextView tv_date_choose, HeartShowData sleepDataToday) {
        KLog.e(" s " + sleepDataToday);
        if (sleepDataToday != null && sleepDataToday.dateUtil != null) {
            if (DateUtil.isSameDay(new Date(), new Date(sleepDataToday.dateUtil.getTimestamp()))) {
                tv_date_choose.setText(String.format(tv_date_choose.getContext().getResources().getString(R.string.sport_module_sleep_sync_time), new Object[]{""}));
            } else {
                tv_date_choose.setText(sleepDataToday.dateUtil.getY_M_D());
            }
            tv_date_choose.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (HeartAdapter.this.dateClickListener != null) {
                        HeartAdapter.this.dateClickListener.onDateClickListener();
                    }
                }
            });
        }
    }

    public void setRecyclerViewAndData(RecyclerViewPager recyclerView, HeartShowData heartShowData2) {
        this.heartShowData = heartShowData2;
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

    public View getScroll_view() {
        return this.sv_main;
    }
}
