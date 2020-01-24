package com.iwown.sport_module.view.run;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.view.MyTextView;
import com.socks.library.KLog;

public class HeartF151Layout extends LinearLayout {
    private MyTextView heartArea0;
    private TextView heartArea0Txt;
    private MyTextView heartArea1;
    private TextView heartArea1Txt;
    private MyTextView heartArea2;
    private TextView heartArea2Txt;
    private MyTextView heartArea3;
    private TextView heartArea3Txt;
    private MyTextView heartArea4;
    private TextView heartArea4Txt;
    private MyTextView heartArea5;
    private TextView heartArea5Txt;

    public HeartF151Layout(Context context) {
        super(context);
        initView(context);
    }

    public HeartF151Layout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_heart_f1_activity, this);
        this.heartArea0 = (MyTextView) findViewById(R.id.heart_area0);
        this.heartArea0Txt = (TextView) findViewById(R.id.heart_area0_txt);
        this.heartArea1 = (MyTextView) findViewById(R.id.heart_area1);
        this.heartArea1Txt = (TextView) findViewById(R.id.heart_area1_txt);
        this.heartArea2 = (MyTextView) findViewById(R.id.heart_area2);
        this.heartArea2Txt = (TextView) findViewById(R.id.heart_area2_txt);
        this.heartArea3 = (MyTextView) findViewById(R.id.heart_area3);
        this.heartArea3Txt = (TextView) findViewById(R.id.heart_area3_txt);
        this.heartArea4 = (MyTextView) findViewById(R.id.heart_area4);
        this.heartArea4Txt = (TextView) findViewById(R.id.heart_area4_txt);
        this.heartArea5 = (MyTextView) findViewById(R.id.heart_area5);
        this.heartArea5Txt = (TextView) findViewById(R.id.heart_area5_txt);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.heartArea0, this.heartArea1, this.heartArea2, this.heartArea3, this.heartArea4, this.heartArea5);
    }

    public void setAreaData(int maxHeart) {
        KLog.e("no2855--11》maxHeart is " + maxHeart);
        this.heartArea0Txt.setText(getContext().getString(R.string.sport_module_heart_area_bpm, new Object[]{Integer.valueOf(35), Integer.valueOf((maxHeart * 5) / 10)}));
        this.heartArea1Txt.setText(getContext().getString(R.string.sport_module_heart_area_bpm, new Object[]{Integer.valueOf(((maxHeart * 5) / 10) + 1), Integer.valueOf((maxHeart * 6) / 10)}));
        this.heartArea2Txt.setText(getContext().getString(R.string.sport_module_heart_area_bpm, new Object[]{Integer.valueOf(((maxHeart * 6) / 10) + 1), Integer.valueOf((maxHeart * 7) / 10)}));
        this.heartArea3Txt.setText(getContext().getString(R.string.sport_module_heart_area_bpm, new Object[]{Integer.valueOf(((maxHeart * 7) / 10) + 1), Integer.valueOf((maxHeart * 8) / 10)}));
        this.heartArea4Txt.setText(getContext().getString(R.string.sport_module_heart_area_bpm, new Object[]{Integer.valueOf(((maxHeart * 8) / 10) + 1), Integer.valueOf((maxHeart * 9) / 10)}));
        this.heartArea5Txt.setText(getContext().getString(R.string.sport_module_heart_area_bpm, new Object[]{Integer.valueOf(((maxHeart * 9) / 10) + 1), Integer.valueOf(maxHeart)}));
    }

    public void setHeartData(int[] data) {
        if (data == null || data.length < 6) {
            this.heartArea0.setText("0");
            this.heartArea1.setText("0");
            this.heartArea2.setText("0");
            this.heartArea3.setText("0");
            this.heartArea4.setText("0");
            this.heartArea5.setText("0");
            return;
        }
        this.heartArea0.setText(String.valueOf(data[0]));
        this.heartArea1.setText(String.valueOf(data[1]));
        this.heartArea2.setText(String.valueOf(data[2]));
        this.heartArea3.setText(String.valueOf(data[3]));
        this.heartArea4.setText(String.valueOf(data[4]));
        this.heartArea5.setText(String.valueOf(data[5]));
    }
}
