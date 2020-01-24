package com.iwown.sport_module.Fragment.data;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.SwimAnalyseActivity;
import com.iwown.sport_module.gps.activity.GpsTargetActivity;
import com.iwown.sport_module.pojo.data.BaseTraningItem;
import com.iwown.sport_module.pojo.data.HistoryRideTraningItem;
import com.iwown.sport_module.pojo.data.TodayTraningItem;
import com.iwown.sport_module.view.HRMonitorDialog;
import com.iwown.sport_module.view.HRMonitorDialog.OnConfirmButtonClickListener;
import java.text.DecimalFormat;
import java.util.List;

public class TraningAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> implements OnClickListener {
    DecimalFormat df = new DecimalFormat("00");
    private HRMonitorDialog mHrMonitorDialog;

    public TraningAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        addItemType(BaseTraningItem.UI_TYPE_HISTORY_RIDE_Layout, R.layout.sport_module_traning_ride_info);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, MultiItemEntity item) {
        updateRideData(helper, (HistoryRideTraningItem) item);
    }

    private void updateRideData(BaseViewHolder helper, HistoryRideTraningItem item) {
        TextView tv_title = (TextView) helper.getView(R.id.tv_title);
        ImageView sport_icon = (ImageView) helper.getView(R.id.sport_icon);
        sport_icon.setImageResource(R.drawable.r1_other_icon);
        ConstraintLayout cl_main = (ConstraintLayout) helper.getView(R.id.cl_main);
        TextView tv_distance_info_title = (TextView) helper.getView(R.id.tv_distance_info_title);
        TextView tv_hour = (TextView) helper.getView(R.id.tv_hour);
        TextView tv_times = (TextView) helper.getView(R.id.tv_hours);
        TextView tv_times_title = (TextView) helper.getView(R.id.tv_times_info_title);
        ImageView iv_right_icon1 = (ImageView) helper.getView(R.id.iv_right_icon);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour, tv_times);
        if (item.getBgImgResources() != 0) {
            sport_icon.setImageResource(item.getTitleImgResources());
            cl_main.setBackgroundResource(item.getBgImgResources());
            tv_title.setText(this.mContext.getString(item.getTitleMsgResources()));
            tv_title.setTextColor(this.mContext.getResources().getColor(item.getTitleColor()));
            if (item.getUnitResources() != 0) {
                tv_distance_info_title.setText(this.mContext.getString(item.getUnitResources()));
            }
            tv_times.setText(item.getCount() + "");
        }
        if (item.isShowGoImg()) {
            iv_right_icon1.setVisibility(0);
            iv_right_icon1.setImageResource(item.getGoImgResources());
            tv_times_title.setVisibility(8);
            tv_times.setVisibility(8);
        } else {
            iv_right_icon1.setVisibility(8);
            tv_times_title.setVisibility(0);
            tv_times.setVisibility(0);
        }
        if (item.getShowType() == 0) {
            float show_distance = item.getDistance();
            if (!UserConfig.getInstance().isMertric()) {
                show_distance = 0.621f * item.getDistance();
            }
            tv_hour.setText(String.format("%.1f", new Object[]{Float.valueOf(show_distance)}));
        } else {
            tv_hour.setText(item.getTime() + "");
        }
        iv_right_icon1.setTag(R.id.first_id, Integer.valueOf(item.getSport_type()));
        iv_right_icon1.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.top_item || view.getId() == R.id.bottom_item) {
            TodayTraningItem todayTraningItem = (TodayTraningItem) view.getTag(R.id.first_id);
            Intent intent = new Intent(view.getContext(), SwimAnalyseActivity.class);
            intent.putExtra("start_time", todayTraningItem.getStartTime());
            intent.putExtra("end_time", todayTraningItem.getEndTime());
            intent.putExtra("dev_type", todayTraningItem.getDev_type());
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.iv_right_icon) {
            final int sport_type = ((Integer) view.getTag(R.id.first_id)).intValue();
            if (this.mHrMonitorDialog == null) {
                this.mHrMonitorDialog = new HRMonitorDialog(this.mContext, false);
                this.mHrMonitorDialog.setOnConfirmButtonClickListener(new OnConfirmButtonClickListener() {
                    public void onConfirmButtonClick(boolean isConfirm) {
                        Intent intent = new Intent(TraningAdapter.this.mContext, GpsTargetActivity.class);
                        intent.putExtra("sport_type", sport_type);
                        intent.putExtra("show_hr", isConfirm);
                        TraningAdapter.this.mContext.startActivity(intent);
                    }
                });
            }
            if (!ModuleRouteDeviceInfoService.getInstance().isWristConnected() || ModuleRouteDeviceInfoService.getInstance().isMtk()) {
            }
            Intent intent2 = new Intent(view.getContext(), GpsTargetActivity.class);
            intent2.putExtra("sport_type", sport_type);
            intent2.putExtra("show_hr", false);
            view.getContext().startActivity(intent2);
        }
    }
}
