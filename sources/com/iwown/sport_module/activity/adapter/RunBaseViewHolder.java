package com.iwown.sport_module.activity.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.commons.cli.HelpFormatter;

/* compiled from: RunBaseDataAdapter */
class RunBaseViewHolder extends DBaseRecyclerViewHolder<DataFragmentBean> {
    int data_size;
    DecimalFormat decimalFormat;
    private View line;
    private TextView tv_index;
    private TextView tv_pace;
    private TextView tv_speed;

    public int getData_size() {
        return this.data_size;
    }

    public void setData_size(int data_size2) {
        this.data_size = data_size2;
    }

    public RunBaseViewHolder(View itemView) {
        super(itemView);
        this.data_size = 0;
        this.decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    }

    public RunBaseViewHolder(View itemView, DBaseRecyclerViewAdapter<DataFragmentBean> dBaseRecyclerViewAdapter) {
        super(itemView, dBaseRecyclerViewAdapter);
        this.data_size = 0;
        this.decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    }

    public RunBaseViewHolder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter<DataFragmentBean> dBaseRecyclerViewAdapter) {
        super(parent, res, dBaseRecyclerViewAdapter);
        this.data_size = 0;
        this.decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        this.tv_index = (TextView) $(R.id.tv_index);
        this.tv_pace = (TextView) $(R.id.tv_pace);
        this.tv_speed = (TextView) $(R.id.tv_speed);
        this.line = $(R.id.line);
        try {
            this.tv_index.setTypeface(SportInitUtils.mDincond_bold_font);
            this.tv_pace.setTypeface(SportInitUtils.mDincond_bold_font);
            this.tv_speed.setTypeface(SportInitUtils.mDincond_bold_font);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void setData(DataFragmentBean data, int position) {
        if (data.totlaKm < 1.0f) {
            this.tv_index.setText("<" + (position + 1) + "");
        } else {
            this.tv_index.setText((position + 1) + "");
        }
        float min = 0.0f;
        String time_m = "0";
        String time_s = "00";
        float real_km = data.totlaKm;
        try {
            min = data.time_s / 60.0f;
            real_km = Util.doubleToFloat(2, (double) data.totlaKm);
            KLog.e("min " + min + "  r_k " + real_km);
            String real_str = this.decimalFormat.format((double) (min / real_km));
            KLog.e("str " + real_str + "  " + real_km);
            String[] splits = real_str.split("\\.");
            time_m = splits[0];
            time_s = ((Integer.parseInt(splits[1]) * 60) / 100) + "";
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        KLog.e(data.totlaKm + "  ");
        float speed_m = (real_km / min) * 60.0f;
        if (data.totlaKm != 0.0f) {
            this.tv_pace.setText(time_m + "'" + time_s + "''");
            this.tv_speed.setText(this.decimalFormat.format((double) speed_m));
        } else {
            this.tv_pace.setText(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
            this.tv_speed.setText(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        }
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tv_index, this.tv_pace, this.tv_speed);
        if (position == this.data_size - 1) {
            this.line.setVisibility(8);
        } else {
            this.line.setVisibility(0);
        }
    }
}
