package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.sport_module.R;
import com.iwown.sport_module.view.MyTextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class GpsGoalView extends RelativeLayout {
    private ImageView addTxt;
    private DecimalFormat decimalFormat;
    private TextView goalTxt;
    private float mNumber;
    private String numStr = "";
    /* access modifiers changed from: private */
    public OnGpsGoalListener onGpsGoalListener;
    private ImageView subtractTxt;
    private MyTextView txtNum;
    private TextView txtTip;
    private TextView txtUnit;
    /* access modifiers changed from: private */
    public int type;

    public interface OnGpsGoalListener {
        void setTargetNum(float f);

        void showInputDialog(int i);
    }

    public GpsGoalView(Context context) {
        super(context);
        initView(context);
    }

    public GpsGoalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GpsGoalView(Context context, int type2) {
        super(context);
        this.type = type2;
        initView(context);
    }

    public GpsGoalView(Context context, int type2, OnGpsGoalListener onGpsGoalListener2) {
        super(context);
        this.type = type2;
        this.onGpsGoalListener = onGpsGoalListener2;
        initView(context);
    }

    /* access modifiers changed from: protected */
    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_gps_model_other, this);
        this.subtractTxt = (ImageView) findViewById(R.id.model_goal_subtract);
        this.addTxt = (ImageView) findViewById(R.id.model_goal_add);
        this.txtNum = (MyTextView) findViewById(R.id.model_goal_num);
        this.txtUnit = (TextView) findViewById(R.id.model_goal_unit);
        this.txtTip = (TextView) findViewById(R.id.model_goal_tip);
        this.goalTxt = (TextView) findViewById(R.id.model_goal_txt);
        switch (this.type) {
            case 1:
                this.goalTxt.setText(R.string.sport_module_Distance);
                this.txtNum.setText("5.0");
                if (UserConfig.getInstance().isMertric()) {
                    this.txtUnit.setText(R.string.sport_module_distance_unit_km);
                } else {
                    this.txtUnit.setText(R.string.sport_module_distance_unit_mi);
                }
                this.numStr = "5.0";
                this.mNumber = 5.0f;
                this.txtTip.setText(R.string.sport_module_distance_goaland_tip);
                break;
            case 2:
                this.goalTxt.setText(R.string.sport_module_gps_duration);
                this.txtNum.setText("00:30");
                this.numStr = "0030";
                this.mNumber = 30.0f;
                this.txtUnit.setVisibility(4);
                this.txtTip.setText(R.string.sport_module_duration_goaland_tip);
                break;
            case 3:
                this.goalTxt.setText(R.string.sport_module_calories);
                this.txtNum.setText("300");
                this.numStr = "300";
                this.mNumber = 300.0f;
                this.txtUnit.setText(R.string.sport_module_unit_cal);
                this.txtTip.setText(R.string.sport_module_calories_goaland_tip);
                break;
        }
        this.subtractTxt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GpsGoalView.this.addSubTxt(false);
            }
        });
        this.addTxt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GpsGoalView.this.addSubTxt(true);
            }
        });
        this.txtNum.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (GpsGoalView.this.onGpsGoalListener != null) {
                    GpsGoalView.this.onGpsGoalListener.showInputDialog(GpsGoalView.this.type);
                }
            }
        });
        this.decimalFormat = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
    }

    /* access modifiers changed from: private */
    public void addSubTxt(boolean isAdd) {
        if (this.type == 1) {
            this.mNumber = (float) (isAdd ? ((double) this.mNumber) + 0.5d : ((double) this.mNumber) - 0.5d);
            this.mNumber = ((double) this.mNumber) < 0.5d ? 0.5f : this.mNumber;
            this.mNumber = this.mNumber > 500.0f ? 500.0f : this.mNumber;
            this.numStr = this.decimalFormat.format((double) this.mNumber);
            this.txtNum.setText(this.numStr);
        } else if (this.type == 2) {
            this.mNumber = isAdd ? this.mNumber + 5.0f : this.mNumber - 5.0f;
            this.mNumber = this.mNumber < 5.0f ? 5.0f : this.mNumber;
            this.mNumber = this.mNumber > 2880.0f ? 2880.0f : this.mNumber;
            num2Str();
            strTo2Txt(0, false);
        } else if (this.type == 3) {
            this.mNumber = isAdd ? this.mNumber + 50.0f : this.mNumber - 50.0f;
            this.mNumber = this.mNumber < 50.0f ? 50.0f : this.mNumber;
            this.mNumber = this.mNumber > 5000.0f ? 5000.0f : this.mNumber;
            this.numStr = String.valueOf((int) this.mNumber);
            this.txtNum.setText(this.numStr);
        }
        if (this.onGpsGoalListener != null) {
            this.onGpsGoalListener.setTargetNum(this.mNumber);
        }
    }

    private void num2Str() {
        String n1 = String.format("%02d", new Object[]{Integer.valueOf(((int) this.mNumber) / 60)});
        this.numStr = n1 + String.format("%02d", new Object[]{Integer.valueOf(((int) this.mNumber) % 60)});
    }

    public void setTxtNum(int number) {
        Log.d("testGoal", number + "");
        if ("".equals(this.numStr) && number == -1) {
            return;
        }
        if (this.numStr.contains(Consts.DOT) && number == -1) {
            return;
        }
        if (number != -1 || this.type != 3) {
            if (number <= 0 || this.numStr.length() < 7) {
                String mStr = number < 0 ? Consts.DOT : String.valueOf(number);
                if (this.type == 2) {
                    if (number != -1) {
                        strTo2Txt(number, true);
                    }
                } else if ("".equals(this.numStr) && number < 0) {
                } else {
                    if (number == -2) {
                        this.numStr = this.numStr.substring(0, this.numStr.length() - 1);
                        this.txtNum.setText(this.numStr);
                        return;
                    }
                    this.numStr += mStr;
                    this.txtNum.setText(this.numStr);
                }
            }
        }
    }

    private void strTo2Txt(int number, boolean isSuan) {
        int md;
        Log.d("testGoal", "number11 is " + number + "");
        if (isSuan) {
            int md2 = Integer.parseInt(this.numStr);
            if (number >= 0) {
                md = (md2 * 10) + number;
                if (md >= 10000) {
                    return;
                }
            } else {
                md = md2 / 10;
            }
            this.numStr = String.format("%04d", new Object[]{Integer.valueOf(md)});
        }
        String s1 = this.numStr.substring(0, 2);
        this.txtNum.setText(s1 + ":" + this.numStr.substring(2, this.numStr.length()));
    }

    public void setStrToFloat() {
        try {
            if (this.type == 1) {
                if (TextUtils.isEmpty(this.numStr)) {
                    this.numStr = "5";
                }
                this.mNumber = Float.parseFloat(this.numStr);
                if (this.mNumber >= 500.0f) {
                    this.mNumber = 500.0f;
                    this.numStr = "500";
                } else if (((double) this.mNumber) < 0.5d) {
                    this.mNumber = 0.5f;
                    this.numStr = "0.5";
                } else {
                    this.numStr = this.decimalFormat.format((double) this.mNumber);
                }
                this.txtNum.setText(this.numStr);
            } else if (this.type == 3) {
                if (TextUtils.isEmpty(this.numStr)) {
                    this.mNumber = 300.0f;
                } else {
                    this.mNumber = (float) Integer.parseInt(this.numStr);
                    if (this.mNumber < 50.0f) {
                        this.mNumber = 50.0f;
                    } else if (this.mNumber > 5000.0f) {
                        this.mNumber = 5000.0f;
                    }
                }
                this.numStr = String.valueOf((int) this.mNumber);
                this.txtNum.setText(this.numStr);
            } else if (this.type == 2) {
                String st1 = this.numStr.substring(0, 2);
                String st2 = this.numStr.substring(2, 4);
                int in1 = Integer.parseInt(st1);
                int in2 = Integer.parseInt(st2);
                if (in2 > 60) {
                    in1++;
                }
                if (in1 >= 48) {
                    this.numStr = "4800";
                    this.mNumber = 2880.0f;
                    strTo2Txt(0, false);
                } else {
                    String now2 = String.format("%02d", new Object[]{Integer.valueOf(in2 % 60)});
                    String now1 = String.format("%02d", new Object[]{Integer.valueOf(in1)});
                    this.mNumber = (float) ((in1 * 60) + (in2 % 60));
                    this.numStr = now1 + now2;
                    strTo2Txt(0, false);
                    Log.d("testGoal", " mNumber is " + this.mNumber + " -- " + this.numStr);
                }
            }
            if (this.onGpsGoalListener != null) {
                this.onGpsGoalListener.setTargetNum(this.mNumber);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public float getmNumber() {
        return this.mNumber;
    }

    public void setOnGpsGoalListener(OnGpsGoalListener onGpsGoalListener2) {
        this.onGpsGoalListener = onGpsGoalListener2;
    }
}
