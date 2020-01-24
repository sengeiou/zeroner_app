package com.iwown.sport_module.ui.weight;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.utils.BaseSportUtils;

public class WeightDetailDataBean {
    public Drawable drawable;
    public int icon;
    public double percent = Utils.DOUBLE_EPSILON;
    public String text_msg = "";
    public String title;
    public int type;
    public String unit = "";
    public float value;

    public WeightDetailDataBean(Context context, int type2, double percent2, float value2) {
        this.type = type2;
        this.title = this.title;
        this.percent = percent2;
        this.value = value2;
        if (percent2 <= ((double) 0.33333334f)) {
            this.drawable = context.getResources().getDrawable(R.drawable.sport_module_progress_bar_drawable_low);
        } else if (percent2 <= ((double) 0.6666667f)) {
            this.drawable = context.getResources().getDrawable(R.drawable.sport_module_progress_bar_drawable_nomal);
        } else {
            this.drawable = context.getResources().getDrawable(R.drawable.sport_module_progress_bar_drawable_high);
        }
        boolean mertric = UserConfig.getInstance().isMertric();
        String temp_unit = context.getResources().getString(R.string.my_module_unit_kg);
        float temp_value = value2;
        if (!mertric) {
            temp_unit = context.getResources().getString(R.string.unit_lbs);
            temp_value = BaseSportUtils.getLbsFromKG(temp_value);
        }
        float temp_value2 = (float) BaseSportUtils.getDouble1(temp_value);
        switch (type2) {
            case 1:
                this.icon = R.mipmap.weight_bmi;
                this.title = context.getResources().getString(R.string.sport_module_weight_bmi);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_bmi);
                return;
            case 2:
                this.icon = R.mipmap.weight_fat;
                this.title = context.getResources().getString(R.string.sport_module_weight_fat);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_body_fat);
                this.unit = "%";
                return;
            case 3:
                this.icon = R.mipmap.weight_muscle;
                this.title = context.getResources().getString(R.string.sport_module_weight_muscle);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_muscle);
                this.unit = temp_unit;
                this.value = temp_value2;
                return;
            case 4:
                this.icon = R.mipmap.weight_water;
                this.title = context.getResources().getString(R.string.sport_module_weight_water);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_water);
                this.unit = "%";
                return;
            case 5:
                this.icon = R.mipmap.weight_skeletonl;
                this.title = context.getResources().getString(R.string.sport_module_weight_skeletonl);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_bone);
                this.unit = temp_unit;
                this.value = temp_value2;
                return;
            case 6:
                this.icon = R.mipmap.weight_visceral;
                this.title = context.getResources().getString(R.string.sport_module_weight_visceral_fat);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_v_fat);
                return;
            case 7:
                this.icon = R.mipmap.weight_metabolism;
                this.title = context.getResources().getString(R.string.sport_module_weight_metabolism);
                this.text_msg = context.getResources().getString(R.string.sport_module_weight_msg_metabolic);
                return;
            default:
                return;
        }
    }
}
