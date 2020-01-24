package com.iwown.device_module.common.view.checkbox;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.iwown.device_module.R;

public class WeekRepeatCheck extends ConstraintLayout {
    /* access modifiers changed from: private */
    public CheckBox cb1;
    SetOnCheckChange checkChange;
    private TextView tv1;

    public interface SetOnCheckChange {
        void onCheckChange(boolean z);
    }

    public SetOnCheckChange getCheckChange() {
        return this.checkChange;
    }

    public void setCheckChange(SetOnCheckChange checkChange2) {
        this.checkChange = checkChange2;
    }

    public WeekRepeatCheck(Context context) {
        super(context);
    }

    public WeekRepeatCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.device_module_week_repeat_item, this);
        this.tv1 = (TextView) view.findViewById(R.id.blood_item_text);
        this.cb1 = (CheckBox) view.findViewById(R.id.blood_item_img);
        this.cb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (WeekRepeatCheck.this.checkChange != null) {
                    WeekRepeatCheck.this.checkChange.onCheckChange(isChecked);
                }
            }
        });
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (WeekRepeatCheck.this.cb1.isChecked()) {
                    WeekRepeatCheck.this.cb1.setChecked(false);
                } else {
                    WeekRepeatCheck.this.cb1.setChecked(true);
                }
            }
        });
    }

    public WeekRepeatCheck(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLeftTitle(String text) {
        this.tv1.setText(text);
    }

    public void setChecked(boolean isCheck) {
        this.cb1.setChecked(isCheck);
    }
}
