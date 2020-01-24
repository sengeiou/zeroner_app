package com.iwown.my_module.protocol;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.TosAdapterView;
import com.iwown.my_module.widget.TosAdapterView.OnItemSelectedListener;

public class WheelViewItemSelectedListener implements OnItemSelectedListener {
    private Context mContext;

    public WheelViewItemSelectedListener(Context context) {
        this.mContext = context;
    }

    public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
        if (parent.getChildAt(position - 1) != null) {
            TextView tv3 = (TextView) parent.getChildAt(position - 1).findViewById(R.id.wheelview_item);
            if (tv3 != null) {
                tv3.setTextColor(this.mContext.getResources().getColor(R.color.dark_theme_unselected_color));
            }
        }
        if (parent.getChildAt(position + 1) != null) {
            TextView tv32 = (TextView) parent.getChildAt(position + 1).findViewById(R.id.wheelview_item);
            if (tv32 != null) {
                tv32.setTextColor(this.mContext.getResources().getColor(R.color.dark_theme_unselected_color));
            }
        }
        if (view != null) {
            TextView tv = (TextView) view.findViewById(R.id.wheelview_item);
            if (tv != null) {
                tv.setTextColor(this.mContext.getResources().getColor(R.color.dark_theme_text_color));
            }
        }
    }

    public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
    }
}
