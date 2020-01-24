package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.ShSwitchView.OnSwitchStateChangeListener;

public class ShSwitchViewLayout extends RelativeLayout {
    private TextView label;
    private String labelText;
    private ShSwitchView switchView;

    public ShSwitchViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.my_module_view_common_check, this);
        this.label = (TextView) v.findViewById(R.id.check_text);
        this.switchView = (ShSwitchView) v.findViewById(R.id.check_switch_view);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchViewCheckbox);
        this.labelText = a.getString(R.styleable.SwitchViewCheckbox_lingyi_checkbox);
        this.label.setText(this.labelText);
        a.recycle();
    }

    public void setLaberText(String str) {
        this.label.setText(str);
    }

    public void setCheckboxCheck(boolean flag) {
        this.switchView.setOn(flag);
    }

    public void setOnSwitchStateChangeListener(OnSwitchStateChangeListener listener) {
        this.switchView.setOnSwitchStateChangeListener(listener);
    }
}
