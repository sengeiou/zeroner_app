package com.iwown.sport_module.ui.blood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.iwown.sport_module.R;
import com.socks.library.KLog;
import java.util.List;

public class BloodAdapter extends ArrayAdapter<BloodBeanData> {
    public BloodAdapter(Context context, int resource, List<BloodBeanData> items) {
        super(context, resource, items);
    }

    @SuppressLint({"ResourceAsColor"})
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.sport_module_active_blood_list, null);
        }
        BloodBeanData bloodToday = (BloodBeanData) getItem(position);
        if (bloodToday != null) {
            View backgrounbp = v.findViewById(R.id.sport_module_bplist_view);
            TextView tv_blood_num = (TextView) v.findViewById(R.id.tv_blood_num);
            TextView tv_blood_grade = (TextView) v.findViewById(R.id.tv_blood_grade);
            ((TextView) v.findViewById(R.id.tv_blood_time)).setText(bloodToday.getBP_time());
            tv_blood_num.setText(bloodToday.getBP_num());
            tv_blood_grade.setText(bloodToday.getBP_grade());
            KLog.e("1111    --------" + bloodToday.getBP_grade().toString());
            if (bloodToday.getBP_grade().toString() == getContext().getString(R.string.blood_grade_low)) {
                backgrounbp.setBackgroundColor(Color.parseColor("#5ACCEF"));
            } else if (bloodToday.getBP_grade().toString() == getContext().getString(R.string.blood_grade_normal)) {
                backgrounbp.setBackgroundColor(Color.parseColor("#6DDEA0"));
            } else if (bloodToday.getBP_grade().toString() == getContext().getString(R.string.blood_grade_Normalhigh_value)) {
                backgrounbp.setBackgroundColor(Color.parseColor("#93E361"));
            } else if (bloodToday.getBP_grade().toString() == getContext().getString(R.string.blood_grade_Mildlyhigh)) {
                backgrounbp.setBackgroundColor(Color.parseColor("#D7CF42"));
            } else if (bloodToday.getBP_grade().toString() == getContext().getString(R.string.blood_grade_Moderatelyhigh)) {
                backgrounbp.setBackgroundColor(Color.parseColor("#F3912E"));
            } else if (bloodToday.getBP_grade().toString() == getContext().getString(R.string.blood_grade_Severelyhigh)) {
                backgrounbp.setBackgroundColor(Color.parseColor("#EE3327"));
            } else {
                backgrounbp.setBackgroundColor(Color.parseColor("#EE3327"));
            }
        }
        return v;
    }

    public int getCount() {
        return super.getCount();
    }
}
