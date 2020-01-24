package com.iwown.sport_module.gps.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.MapRunActivity;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.activity.SwimAnalyseActivity;
import com.iwown.sport_module.gps.data.GpsFootItem;
import com.iwown.sport_module.gps.data.SportDetailItem;
import com.iwown.sport_module.gps.data.SportTotalItem;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.WithUnitText;
import com.socks.library.KLog;
import java.text.DecimalFormat;
import java.util.List;

public class GpsInfoAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private WithUnitText calorie_tv;
    private String[] dayArr = null;
    private DecimalFormat decimalFormat;
    private String[] gpsTimeArr = null;
    private boolean isEnglish;
    /* access modifiers changed from: private */
    public Context mContext = null;
    /* access modifiers changed from: private */
    public int mType;
    private String[] montArr = null;
    /* access modifiers changed from: private */
    public OnLoadClickListener onLoadClickListener;
    private int sport_type;
    private TextView tv_times;

    public interface OnLoadClickListener {
        void onLoadClick();
    }

    public GpsInfoAdapter(Context context, List<MultiItemEntity> data, boolean isEnglish2, int myType) {
        super(data);
        this.isEnglish = isEnglish2;
        addItemType(0, R.layout.sport_module_layout_gps_total_info);
        addItemType(1, R.layout.sport_module_layout_gps_detail_info);
        addItemType(2, R.layout.sport_module_gps_load_more_layout);
        this.mContext = context;
        this.montArr = context.getResources().getStringArray(R.array.sport_module_months_items);
        this.dayArr = context.getResources().getStringArray(R.array.sport_module_day_with_th);
        this.gpsTimeArr = context.getResources().getStringArray(R.array.sport_module_gps_sport_time);
        this.decimalFormat = new DecimalFormat("0.0");
        this.mType = myType;
    }

    public void setOnLoadClickListener(OnLoadClickListener onLoadClickListener2) {
        this.onLoadClickListener = onLoadClickListener2;
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, MultiItemEntity item) {
        String str;
        switch (helper.getItemViewType()) {
            case 0:
                SportTotalItem gpsTotalItem = (SportTotalItem) item;
                BaseViewHolder text = helper.setText(R.id.date, (CharSequence) this.montArr[gpsTotalItem.getMonth() - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + gpsTotalItem.getYear());
                int i = R.id.activities;
                if (gpsTotalItem.getActivityCount() == 1) {
                    str = "1 " + this.mContext.getString(R.string.sport_module_activity);
                } else {
                    str = gpsTotalItem.getActivityCount() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.mContext.getString(R.string.sport_module_activitys);
                }
                text.setText(i, (CharSequence) str);
                long all_time = gpsTotalItem.getAllTime();
                if (gpsTotalItem.getData_type() != 0) {
                    helper.setText(R.id.distance, (CharSequence) String.format("%02d", new Object[]{Long.valueOf(all_time / 3600)}) + ":" + String.format("%02d", new Object[]{Long.valueOf((all_time % 3600) / 60)}) + ":" + String.format("%02d", new Object[]{Long.valueOf(all_time % 60)}));
                } else if (!this.isEnglish) {
                    helper.setText(R.id.distance, (CharSequence) this.decimalFormat.format((double) gpsTotalItem.getTotalDistanceKm()) + this.mContext.getString(R.string.sport_module_distance_unit_km));
                } else {
                    helper.setText(R.id.distance, (CharSequence) this.decimalFormat.format((double) gpsTotalItem.getTotalDistanceMile()) + this.mContext.getString(R.string.sport_module_distance_unit_mi));
                }
                if (getData().indexOf(item) == 0) {
                    View view = helper.getView(R.id.total_info_rl);
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
                    marginLayoutParams.topMargin = 0;
                    view.setLayoutParams(marginLayoutParams);
                }
                helper.getView(R.id.total_info_rl).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
                return;
            case 1:
                final SportDetailItem gpsDetailItem = (SportDetailItem) item;
                KLog.d("no2855--> gpsDetailItem.getDataType(): " + gpsDetailItem.getDataType());
                ImageView img11 = (ImageView) helper.getView(R.id.dev_type_img);
                ImageView img22 = (ImageView) helper.getView(R.id.dev_type_img2);
                if (gpsDetailItem.getDataType() == 0) {
                    img11.setImageResource(R.mipmap.phone_s3x);
                    img22.setVisibility(8);
                } else if (gpsDetailItem.getDataType() == 2) {
                    img11.setImageResource(R.mipmap.earphone_s3x);
                    img22.setVisibility(8);
                } else if (gpsDetailItem.getDataType() == 3) {
                    img22.setVisibility(0);
                    img11.setImageResource(R.mipmap.phone_s3x);
                    img22.setImageResource(R.mipmap.earphone_s3x);
                } else if (gpsDetailItem.getDataType() == 4) {
                    img11.setImageResource(R.mipmap.band_s3x);
                    img22.setVisibility(8);
                } else if (gpsDetailItem.getDataType() == 1) {
                    img11.setImageResource(R.mipmap.watch_s3x);
                    img22.setVisibility(8);
                }
                ImageView sportImg = (ImageView) helper.getView(R.id.dev_type_sport_img);
                ImageView enterImg = (ImageView) helper.getView(R.id.enter_btn);
                if (gpsDetailItem.isCanClick()) {
                    enterImg.setVisibility(0);
                } else {
                    enterImg.setVisibility(4);
                }
                if (gpsDetailItem.getSportImg() == 0) {
                    sportImg.setVisibility(4);
                } else {
                    sportImg.setVisibility(0);
                    sportImg.setImageResource(gpsDetailItem.getSportImg());
                }
                TextView time_tv = (TextView) helper.getView(R.id.date);
                time_tv.setText(gpsDetailItem.getTimeTitle());
                WithUnitText distance = (WithUnitText) helper.getView(R.id.distance_tv);
                if (gpsDetailItem.getDistanceKm() <= 0.0f) {
                    distance.setNumTv(Util.doubleToFloat(2, (double) gpsDetailItem.getCalorie()) + "");
                    distance.setUnitTv(this.mContext.getString(R.string.sport_module_calorie_unit_km));
                } else if (this.isEnglish) {
                    distance.setNumTv(gpsDetailItem.getDistanceMile() + "");
                    distance.setUnitTv(this.mContext.getString(R.string.sport_module_distance_unit_mi));
                } else if (gpsDetailItem.getSportType() == 2097) {
                    distance.setNumTv(String.valueOf(gpsDetailItem.getDistanceKm()));
                    distance.setUnitTv(this.mContext.getString(R.string.sport_swimming_distance_unit));
                } else {
                    distance.setNumTv(String.valueOf(gpsDetailItem.getDistanceKm()));
                    distance.setUnitTv(this.mContext.getString(R.string.sport_module_distance_unit_km));
                }
                FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), time_tv, distance.getNumTv(), this.tv_times);
                helper.setText(R.id.time, (CharSequence) gpsDetailItem.getDurationStr());
                View view2 = helper.getView(R.id.gps_adapter_lay);
                AnonymousClass1 r0 = new OnClickListener() {
                    public void onClick(View view) {
                        Intent intent;
                        if (gpsDetailItem.isCanClick()) {
                            if (GpsInfoAdapter.this.mType == 3) {
                                intent = new Intent(GpsInfoAdapter.this.mContext, SwimAnalyseActivity.class);
                            } else {
                                intent = new Intent(GpsInfoAdapter.this.mContext, MapRunActivity.class);
                            }
                            intent.putExtra("start_time", gpsDetailItem.getStartTime() * 1000);
                            intent.putExtra("end_time", gpsDetailItem.getEndTime() * 1000);
                            intent.putExtra("sport_type", gpsDetailItem.getSportType());
                            intent.putExtra("data_type", gpsDetailItem.getDataType());
                            intent.putExtra("data_from", gpsDetailItem.getDataFrom());
                            intent.putExtra("source_type", gpsDetailItem.getSourceType());
                            intent.putExtra("type", GpsInfoAdapter.this.mType);
                            GpsInfoAdapter.this.mContext.startActivity(intent);
                        }
                    }
                };
                view2.setOnClickListener(r0);
                helper.getView(R.id.gps_adapter_lay).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
                return;
            case 2:
                final GpsFootItem footItem = (GpsFootItem) item;
                if (footItem.getLoadType() == 0) {
                    helper.getView(R.id.gps_loading_progress).setVisibility(0);
                    helper.setText(R.id.gps_loading_text, (CharSequence) this.mContext.getString(R.string.sport_module_gps_loading));
                } else if (footItem.getLoadType() == 1) {
                    helper.getView(R.id.gps_loading_progress).setVisibility(8);
                    helper.setText(R.id.gps_loading_text, (CharSequence) this.mContext.getString(R.string.sport_module_gps_load_again));
                } else {
                    helper.getView(R.id.gps_loading_progress).setVisibility(8);
                    helper.setText(R.id.gps_loading_text, (CharSequence) this.mContext.getString(R.string.sport_module_gps_load_all));
                }
                View view3 = helper.getView(R.id.gps_loading_text);
                AnonymousClass2 r02 = new OnClickListener() {
                    public void onClick(View v) {
                        if (GpsInfoAdapter.this.onLoadClickListener != null && footItem.getLoadType() == 1) {
                            GpsInfoAdapter.this.onLoadClickListener.onLoadClick();
                        }
                    }
                };
                view3.setOnClickListener(r02);
                return;
            default:
                return;
        }
    }

    @Nullable
    public MultiItemEntity getItem(@IntRange(from = 0) int position) {
        return (MultiItemEntity) super.getItem(position);
    }

    public int getItemCount() {
        return super.getItemCount();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void loadFail() {
        if (this.mData.size() > 0) {
            ((GpsFootItem) getData().get(this.mData.size() - 1)).setLoadType(1);
            notifyItemChanged(getData().size() - 1);
        }
    }

    public void showLoadMore() {
        if (this.mData.size() > 0) {
            ((GpsFootItem) getData().get(this.mData.size() - 1)).setLoadType(0);
            notifyItemChanged(getData().size() - 1);
        }
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }
}
