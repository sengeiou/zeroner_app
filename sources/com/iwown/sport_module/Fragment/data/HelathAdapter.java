package com.iwown.sport_module.Fragment.data;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.af.ModuleRouterRRIService;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.sport_data.Bp_data_sport;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.sql.TB_af_result;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.toast.Utils;
import com.iwown.lib_common.views.heartview.DHeartChartView;
import com.iwown.lib_common.views.heartview.DlineDataBean;
import com.iwown.lib_common.views.sleepview.DSleepChartView;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.iwown.lib_common.views.weightview.mini_weight.WeightViewMiniLayout;
import com.iwown.sport_module.R;
import com.iwown.sport_module.pojo.data.BaseTraningItem;
import com.iwown.sport_module.pojo.data.TodayAfItem;
import com.iwown.sport_module.pojo.data.TodayBloodItem;
import com.iwown.sport_module.pojo.data.TodayEcgItem;
import com.iwown.sport_module.pojo.data.TodayFatigueItem;
import com.iwown.sport_module.pojo.data.TodayHeartItem;
import com.iwown.sport_module.pojo.data.TodaySleepItem;
import com.iwown.sport_module.pojo.data.TodayWeightItem;
import com.socks.library.KLog;
import java.util.Date;
import java.util.List;
import org.litepal.crud.DataSupport;

public class HelathAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private final int dp_180;

    public HelathAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_Weight, R.layout.sport_module_home_item_weight);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_Sleep, R.layout.sport_module_home_item_sleep);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_Heart, R.layout.sport_module_home_item_heart);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_Fatigue, R.layout.sport_module_home_item_fatigue);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_ECG, R.layout.sport_module_home_item_ecg);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_Blood, R.layout.sport_module_home_item_blood);
        addItemType(BaseTraningItem.UI_TYPE_TODAY_AF, R.layout.sport_module_home_item_af);
        this.dp_180 = DensityUtil.dip2px(context, 180.0f);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, MultiItemEntity item) {
        if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Weight) {
            updateWeightData(helper, (TodayWeightItem) item);
        } else if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Sleep) {
            updateSleepData(helper, (TodaySleepItem) item);
        } else if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Heart) {
            updateHeartData(helper, (TodayHeartItem) item);
        } else if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Fatigue) {
            updateFatigueData(helper, (TodayFatigueItem) item);
        } else if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_ECG) {
            updateEcgData(helper, (TodayEcgItem) item);
        } else if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Blood) {
            updateBloodData(helper, (TodayBloodItem) item);
        } else if (item.getItemType() == BaseTraningItem.UI_TYPE_TODAY_AF) {
            updateAfData(helper, (TodayAfItem) item);
        }
    }

    private void updateAfData(BaseViewHolder helper, TodayAfItem item) {
        TextView tv_af_status = (TextView) helper.getView(R.id.tv_af_status);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        TextView tv_af_title = (TextView) helper.getView(R.id.tv_af_title);
        TextView tv_no_data = (TextView) helper.getView(R.id.tv_no_data);
        DateUtil dateUtil = new DateUtil();
        TB_af_result tb_af_result = (TB_af_result) DataSupport.where("uid=? and data_From=? and record_date=?", UserConfig.getInstance().getNewUID() + "", ModuleRouterRRIService.getInstance().getRRIHasDataFrom(UserConfig.getInstance().getNewUID(), dateUtil.getSyyyyMMddDate(), UserConfig.getInstance().getDevice()), dateUtil.getSyyyyMMddDate()).findFirst(TB_af_result.class);
        if (tb_af_result == null) {
            tv_af_status.setVisibility(8);
            tv_af_title.setVisibility(8);
            tv_time.setVisibility(8);
            tv_no_data.setVisibility(0);
            return;
        }
        tv_af_status.setVisibility(0);
        tv_af_title.setVisibility(0);
        tv_time.setVisibility(0);
        tv_no_data.setVisibility(8);
        int af_ai_result = tb_af_result.getAf_ai_result();
        if (af_ai_result == 1) {
            tv_af_status.setText(R.string.af_result_by_calc_1);
        } else if (af_ai_result == 2) {
            tv_af_status.setText(R.string.af_result_by_calc_2);
        } else {
            tv_af_status.setText(R.string.af_result_by_calc_3);
        }
        tv_time.setText(new DateUtil(tb_af_result.getTime(), true).getHHmmDate());
    }

    private void updateBloodData(BaseViewHolder helper, TodayBloodItem item) {
        ConstraintLayout noBlood = (ConstraintLayout) helper.getView(R.id.cl_no_data);
        ConstraintLayout yesBlood = (ConstraintLayout) helper.getView(R.id.cl_show_blood);
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        long staBtime = dateUtil1.getZeroTime();
        List<Bp_data_sport> aa = ModuleRouteSportService.getInstance().getAllDataBlood(UserConfig.getInstance().getNewUID(), dateUtil1.getUnixTimestamp(), dateUtil1.getUnixTimestamp() + 86400);
        TextView card_bp = (TextView) helper.getView(R.id.tv_value);
        TextView tv_sync_time = (TextView) helper.getView(R.id.tv_time);
        TextView tv__bloodgrade = (TextView) helper.getView(R.id.tv__bloodgrade);
        long lastTime = 0;
        int lastSbp = 0;
        int lastDbp = 0;
        if (aa == null || aa.size() <= 0) {
            KLog.e("--l808  无数据--");
            yesBlood.setVisibility(8);
            noBlood.setVisibility(0);
            return;
        }
        for (int i = 0; i < aa.size(); i++) {
            if (((Bp_data_sport) aa.get(i)).getBpTime() > staBtime) {
                lastTime = ((Bp_data_sport) aa.get(i)).getBpTime();
                lastDbp = ((Bp_data_sport) aa.get(i)).getDbp();
                lastSbp = ((Bp_data_sport) aa.get(i)).getSbp();
                staBtime = lastTime;
            }
        }
        if (lastDbp <= 0 || lastSbp <= 0 || lastTime <= 0) {
            yesBlood.setVisibility(8);
            noBlood.setVisibility(0);
            return;
        }
        yesBlood.setVisibility(0);
        noBlood.setVisibility(8);
        card_bp.setText(lastSbp + "/" + lastDbp);
        tv_sync_time.setText(DataTimeUtils.getHM(1000 * lastTime));
        tv__bloodgrade.setText(JudgeBlood(lastSbp, lastDbp));
    }

    public String JudgeBlood(int sbp, int dbp) {
        if (sbp < 90 || dbp < 60) {
            return Utils.getString(R.string.blood_grade_low);
        }
        if (sbp > 89 && sbp < 120 && dbp > 59 && dbp < 80) {
            return Utils.getString(R.string.blood_grade_normal);
        }
        if (sbp >= 180 || dbp >= 110) {
            return Utils.getString(R.string.blood_grade_Severelyhigh);
        }
        if ((sbp > 159 && sbp < 180) || (dbp > 99 && dbp < 110)) {
            return Utils.getString(R.string.blood_grade_Moderatelyhigh);
        }
        if ((sbp > 139 && sbp < 160) || (dbp > 89 && dbp < 100)) {
            return Utils.getString(R.string.blood_grade_Mildlyhigh);
        }
        if ((sbp <= 119 || sbp >= 140) && (dbp <= 79 || dbp >= 90)) {
            return Utils.getString(R.string.blood_grade_Severelyhigh);
        }
        return Utils.getString(R.string.blood_grade_Normalhigh_value);
    }

    private void updateEcgData(BaseViewHolder helper, TodayEcgItem item) {
        if (item.getEcgData().getCount() > 0) {
            helper.setText(R.id.ecg_home_msg, (CharSequence) this.mContext.getString(R.string.sport_module_page_ecg));
        } else {
            helper.setText(R.id.ecg_home_msg, (CharSequence) this.mContext.getString(R.string.sport_module_page_ecg_1));
        }
    }

    private void updateWeightData(BaseViewHolder helper, TodayWeightItem item) {
        String unit;
        KLog.e(" updateWeightData mertric " + item.mertric);
        showWeightDataUI(helper, false);
        WeightViewMiniLayout weightViewMiniLayout = (WeightViewMiniLayout) helper.getView(R.id.wvml_weight_cahrt);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        List<WeightShowData> weightDatas1 = item.getWeightDatas1();
        if (weightDatas1 != null && weightDatas1.size() != 0) {
            showWeightDataUI(helper, true);
            TextView tv_hour = (TextView) helper.getView(R.id.tv_value);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour);
            helper.setText(R.id.tv_value, (CharSequence) ((WeightShowData) weightDatas1.get(weightDatas1.size() - 1)).real_weight + "");
            TextView tv_value_unit = (TextView) helper.getView(R.id.tv_value_unit);
            String str = "";
            if (!item.mertric) {
                unit = tv_value_unit.getResources().getString(R.string.unit_lbs);
            } else {
                unit = tv_value_unit.getResources().getString(R.string.my_module_unit_kg);
            }
            tv_value_unit.setText(unit);
            weightViewMiniLayout.setGoal(item.getGoal_weight(), item.max, unit);
            weightViewMiniLayout.setDatas(item.getWeightDatas1());
            DateUtil dateUtil = new DateUtil(item.last_unix_time, true);
            if (DateUtil.isSameDay(new Date(), dateUtil.toDate())) {
                tv_time.setText(dateUtil.getHHmmDate() + "");
            } else {
                tv_time.setText(dateUtil.getY_M_D() + "");
            }
        }
    }

    private void showWeightDataUI(BaseViewHolder helper, boolean b) {
        boolean z = true;
        if (b) {
            int i = R.id.tv_no_data;
            if (1 != 0) {
                z = false;
            }
            helper.setVisible(i, z);
            helper.setVisible(R.id.tv_value, true);
            helper.setVisible(R.id.tv_value_unit, true);
            helper.setVisible(R.id.tv_time, true);
            helper.setVisible(R.id.wvml_weight_cahrt, true);
            return;
        }
        int i2 = R.id.tv_no_data;
        if (0 != 0) {
            z = false;
        }
        helper.setVisible(i2, z);
        helper.setVisible(R.id.tv_value, false);
        helper.setVisible(R.id.tv_value_unit, false);
        helper.setVisible(R.id.tv_time, false);
        helper.setVisible(R.id.wvml_weight_cahrt, false);
    }

    private void updateFatigueData(BaseViewHolder helper, TodayFatigueItem item) {
        showFaitgueDataUI(helper, false);
        if (item.getFatigue() != null) {
            showFaitgueDataUI(helper, true);
            TextView tv_hour = (TextView) helper.getView(R.id.tv_value);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour);
            FatigueData fatigue = item.getFatigue();
            TextView tv_value = (TextView) helper.getView(R.id.tv_value);
            TextView tv_sync_time = (TextView) helper.getView(R.id.tv_sync_time);
            if (fatigue != null) {
                tv_value.setText(fatigue.getValue() + "");
                if (DateUtil.isSameDay(new Date(), new Date(item.time))) {
                    tv_sync_time.setText("" + fatigue.getMeasuretime());
                } else {
                    tv_sync_time.setText("" + new DateUtil(item.time, false).getY_M_D());
                }
            } else {
                tv_value.setText("0");
                tv_sync_time.setText("");
            }
        }
    }

    private void showFaitgueDataUI(BaseViewHolder helper, boolean b) {
        helper.setVisible(R.id.cl_show, b);
        helper.setVisible(R.id.cl_no_data, !b);
    }

    private void updateHeartData(BaseViewHolder helper, TodayHeartItem item) {
        DHeartChartView dHeartChartView = (DHeartChartView) helper.getView(R.id.dhcv_chart);
        showHeartDataUI(helper, false);
        List<DlineDataBean> datas = item.getDatas();
        if (datas != null && datas.size() != 0) {
            int value = 0;
            long time = 0;
            int i = datas.size() - 1;
            while (true) {
                if (i >= 0) {
                    if (((DlineDataBean) datas.get(i)).value >= 35 && ((DlineDataBean) datas.get(i)).value <= 200) {
                        value = ((DlineDataBean) datas.get(i)).value;
                        time = ((DlineDataBean) datas.get(i)).time;
                        break;
                    }
                    i--;
                } else {
                    break;
                }
            }
            if (value != 0) {
                TextView tv_hour = (TextView) helper.getView(R.id.tv_value);
                FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour);
                helper.setText(R.id.tv_value, (CharSequence) value + "");
                helper.setText(R.id.tv_time, (CharSequence) new DateUtil(time, true).getHHmmDate() + "");
                showHeartDataUI(helper, true);
                dHeartChartView.setY_size(3, 1.0f);
                dHeartChartView.setShowYText(false);
                dHeartChartView.setDatas(item.getDatas(), item.y_titles);
            }
        }
    }

    private void showHeartDataUI(BaseViewHolder helper, boolean b) {
        if (b) {
            helper.setVisible(R.id.tv_no_data, false);
            helper.setVisible(R.id.dhcv_chart, true);
            helper.setVisible(R.id.tv_value, true);
            helper.setVisible(R.id.tv_value_unit, true);
            helper.setVisible(R.id.tv_time, true);
            return;
        }
        helper.setVisible(R.id.tv_no_data, true);
        helper.setVisible(R.id.dhcv_chart, false);
        helper.setVisible(R.id.tv_value, false);
        helper.setVisible(R.id.tv_value_unit, false);
        helper.setVisible(R.id.tv_time, false);
    }

    private void updateSleepData(BaseViewHolder helper, TodaySleepItem item) {
        DSleepChartView dSleepChartView = (DSleepChartView) helper.getView(R.id.slcv_sleep);
        showSleepDataUI(helper, false);
        if (item.getLists().size() != 0 && item.getTotal_sleep_time() != 0) {
            showSleepDataUI(helper, true);
            int hour = item.getTotal_sleep_time() / 60;
            int min = item.getTotal_sleep_time() % 60;
            TextView tv_hour = (TextView) helper.getView(R.id.tv_hour);
            TextView tv_min = (TextView) helper.getView(R.id.tv_min);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour, tv_min);
            helper.setText(R.id.tv_hour, (CharSequence) hour + "");
            helper.setText(R.id.tv_min, (CharSequence) min + "");
            dSleepChartView.updateSleepDatas(item.getLists());
        }
    }

    private void showSleepDataUI(BaseViewHolder helper, boolean b) {
        if (b) {
            helper.setVisible(R.id.tv_no_data, false);
            helper.setVisible(R.id.tv_hour, true);
            helper.setVisible(R.id.tv_hour_unit, true);
            helper.setVisible(R.id.tv_min, true);
            helper.setVisible(R.id.tv_min_unit, true);
            helper.setVisible(R.id.slcv_sleep, true);
            return;
        }
        helper.setVisible(R.id.tv_no_data, true);
        helper.setVisible(R.id.tv_hour, false);
        helper.setVisible(R.id.tv_hour_unit, false);
        helper.setVisible(R.id.tv_min, false);
        helper.setVisible(R.id.tv_min_unit, false);
        helper.setVisible(R.id.slcv_sleep, false);
    }
}
