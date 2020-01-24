package com.iwown.sport_module.ui.ecg;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.view.ecg.EcgChartView;
import com.iwown.sport_module.view.ecg.IVEcgViewAnim;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EcgViewAdapter extends Adapter<EcgViewHolder> {
    public EcgViewItemAdapter adapter;
    /* access modifiers changed from: private */
    public List<EcgViewDataBean> data = new ArrayList();
    private int mCurrentPosition;

    public class EcgViewHolder extends ViewHolder implements OnClickListener, EcgOnclickListener {
        ImageView dataFrom_ecg;
        TextView dateCenter;
        TextView ecgHeartValue;
        TextView ecgTestTime;
        IVEcgViewAnim ivEcgView;
        EcgChartView ivEcgView1;
        TextView noData;
        PopupWindow popupWindow_data_from;
        RecyclerView recyclerView;
        ImageView toAnother;

        public EcgViewHolder(View itemView) {
            super(itemView);
            this.recyclerView = (RecyclerView) itemView.findViewById(R.id.ecg_charts_items);
            this.dataFrom_ecg = (ImageView) itemView.findViewById(R.id.iv_data_from_ecg);
            this.ecgTestTime = (TextView) itemView.findViewById(R.id.ecg_time_test);
            this.noData = (TextView) itemView.findViewById(R.id.tv_no_data_ecg);
            this.ecgHeartValue = (TextView) itemView.findViewById(R.id.ecg_heart_value);
            this.toAnother = (ImageView) itemView.findViewById(R.id.to_another_activity);
            this.ivEcgView = (IVEcgViewAnim) itemView.findViewById(R.id.iv_ecg_view);
            this.ivEcgView1 = (EcgChartView) itemView.findViewById(R.id.iv_ecg_view_1);
            this.dateCenter = (TextView) itemView.findViewById(R.id.tv_date_center);
            this.dateCenter.setOnClickListener(this);
            this.toAnother.setOnClickListener(this);
            this.dataFrom_ecg.setOnClickListener(this);
            EcgViewAdapter.this.adapter = new EcgViewItemAdapter(EcgViewAdapter.this.data);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), 1, false));
            this.recyclerView.setAdapter(EcgViewAdapter.this.adapter);
            EcgViewAdapter.this.adapter.setListener(this);
            EcgViewAdapter.this.adapter.notifyDataSetChanged();
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.ecgHeartValue);
        }

        public void onEcgClick(int positions) {
            EcgViewDataBean dataBean = (EcgViewDataBean) EcgViewAdapter.this.data.get(positions);
            String ecgPoint = dataBean.getDataArray();
            if (!TextUtils.isEmpty(ecgPoint)) {
                this.noData.setVisibility(8);
                this.ivEcgView1.setDataList(JsonUtils.getListJson(ecgPoint, Integer.class));
                this.ecgTestTime.setText(new DateUtil(dataBean.getUnixTime(), true).getHHmmDate());
                this.ecgHeartValue.setText(String.valueOf(dataBean.getHeartrate()));
            } else {
                this.noData.setVisibility(0);
            }
            showDateTimeUI(this.dateCenter, dataBean);
            this.dataFrom_ecg.setTag(R.id.first_id, dataBean.getData_from() + "");
        }

        @SuppressLint({"StringFormatInvalid"})
        private void showDateTimeUI(TextView tv_date_choose, EcgViewDataBean ecgViewDataBean) {
            if (ecgViewDataBean == null) {
                return;
            }
            if (DateUtil.isSameDay(new Date(), new DateUtil(ecgViewDataBean.getUnixTime(), true).toDate())) {
                tv_date_choose.setText(String.format(tv_date_choose.getContext().getResources().getString(R.string.sport_module_sleep_sync_time), new Object[]{""}));
                return;
            }
            tv_date_choose.setText(new DateUtil(ecgViewDataBean.getUnixTime(), true).getY_M_D());
        }

        public void onClick(View view) {
            if (view.getId() == R.id.iv_data_from_ecg) {
                if (this.popupWindow_data_from == null) {
                    this.popupWindow_data_from = new PopupWindow(view.getContext());
                    this.popupWindow_data_from.setWidth(DensityUtil.dip2px(view.getContext(), 88.0f));
                    this.popupWindow_data_from.setHeight(-2);
                    View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.sport_module_popupwindow_data_form, null);
                    TextView tv_value = (TextView) inflate.findViewById(R.id.tv_value);
                    tv_value.setBackgroundResource(R.drawable.data_from_bg_red);
                    tv_value.setTextColor(Color.parseColor("#D7592F"));
                    try {
                        tv_value.setText((String) this.dataFrom_ecg.getTag(R.id.first_id));
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

    public EcgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EcgViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_module_activity_ecg, parent, false));
    }

    public void onBindViewHolder(EcgViewHolder holder, int position) {
    }

    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static int getLastPosition() {
        return 2147483646;
    }

    public void setRecycleViewData(List<EcgViewDataBean> data2) {
        this.data = data2;
    }

    public void setRecyclerViewAndData(RecyclerViewPager recyclerView, List<EcgViewDataBean> list) {
        this.mCurrentPosition = recyclerView.getCurrentPosition();
        try {
            onBindViewHolder((EcgViewHolder) recyclerView.findViewHolderForAdapterPosition(this.mCurrentPosition), this.mCurrentPosition);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
