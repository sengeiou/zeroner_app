package com.bigkoo.pickerview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.lib.WheelView.DividerType;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelTime;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.R;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimePickerView extends BasePickerView implements OnClickListener {
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_SUBMIT = "submit";
    private int Color_Background_Title;
    private int Color_Background_Wheel;
    private int Color_Cancel;
    private int Color_Submit;
    private int Color_Title;
    private int Size_Content;
    private int Size_Submit_Cancel;
    private int Size_Title;
    private String Str_Cancel;
    private String Str_Submit;
    private String Str_Title;
    private int backgroundId;
    private Button btnCancel;
    private Button btnSubmit;
    private boolean cancelable;
    private CustomListener customListener;
    private boolean cyclic;
    private Calendar date;
    private int dividerColor;
    private DividerType dividerType;
    private Calendar endDate;
    private int endYear;
    private int gravity = 17;
    private boolean isCenterLabel;
    private boolean isDialog;
    private String label_day;
    private String label_hours;
    private String label_mins;
    private String label_month;
    private String label_seconds;
    private String label_year;
    private int layoutRes;
    private float lineSpacingMultiplier = 1.6f;
    private Calendar startDate;
    private int startYear;
    private int textColorCenter;
    private int textColorOut;
    private OnTimeSelectListener timeSelectListener;
    private TextView tvTitle;
    private boolean[] type;
    WheelTime wheelTime;

    public static class Builder {
        /* access modifiers changed from: private */
        public int Color_Background_Title;
        /* access modifiers changed from: private */
        public int Color_Background_Wheel;
        /* access modifiers changed from: private */
        public int Color_Cancel;
        /* access modifiers changed from: private */
        public int Color_Submit;
        /* access modifiers changed from: private */
        public int Color_Title;
        /* access modifiers changed from: private */
        public int Size_Content = 18;
        /* access modifiers changed from: private */
        public int Size_Submit_Cancel = 17;
        /* access modifiers changed from: private */
        public int Size_Title = 18;
        /* access modifiers changed from: private */
        public String Str_Cancel;
        /* access modifiers changed from: private */
        public String Str_Submit;
        /* access modifiers changed from: private */
        public String Str_Title;
        /* access modifiers changed from: private */
        public int backgroundId;
        /* access modifiers changed from: private */
        public boolean cancelable = true;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public CustomListener customListener;
        /* access modifiers changed from: private */
        public boolean cyclic = false;
        /* access modifiers changed from: private */
        public Calendar date;
        public ViewGroup decorView;
        /* access modifiers changed from: private */
        public int dividerColor;
        /* access modifiers changed from: private */
        public DividerType dividerType;
        /* access modifiers changed from: private */
        public Calendar endDate;
        /* access modifiers changed from: private */
        public int endYear;
        /* access modifiers changed from: private */
        public int gravity = 17;
        /* access modifiers changed from: private */
        public boolean isCenterLabel = true;
        /* access modifiers changed from: private */
        public boolean isDialog;
        /* access modifiers changed from: private */
        public String label_day;
        /* access modifiers changed from: private */
        public String label_hours;
        /* access modifiers changed from: private */
        public String label_mins;
        /* access modifiers changed from: private */
        public String label_month;
        /* access modifiers changed from: private */
        public String label_seconds;
        /* access modifiers changed from: private */
        public String label_year;
        /* access modifiers changed from: private */
        public int layoutRes = R.layout.pickerview_time;
        /* access modifiers changed from: private */
        public float lineSpacingMultiplier = 1.6f;
        /* access modifiers changed from: private */
        public Calendar startDate;
        /* access modifiers changed from: private */
        public int startYear;
        /* access modifiers changed from: private */
        public int textColorCenter;
        /* access modifiers changed from: private */
        public int textColorOut;
        /* access modifiers changed from: private */
        public OnTimeSelectListener timeSelectListener;
        /* access modifiers changed from: private */
        public boolean[] type = {true, true, true, true, true, true};

        public Builder(Context context2, OnTimeSelectListener listener) {
            this.context = context2;
            this.timeSelectListener = listener;
        }

        public Builder setType(boolean[] type2) {
            this.type = type2;
            return this;
        }

        public Builder gravity(int gravity2) {
            this.gravity = gravity2;
            return this;
        }

        public Builder setSubmitText(String Str_Submit2) {
            this.Str_Submit = Str_Submit2;
            return this;
        }

        public Builder isDialog(boolean isDialog2) {
            this.isDialog = isDialog2;
            return this;
        }

        public Builder setCancelText(String Str_Cancel2) {
            this.Str_Cancel = Str_Cancel2;
            return this;
        }

        public Builder setTitleText(String Str_Title2) {
            this.Str_Title = Str_Title2;
            return this;
        }

        public Builder setSubmitColor(int Color_Submit2) {
            this.Color_Submit = Color_Submit2;
            return this;
        }

        public Builder setCancelColor(int Color_Cancel2) {
            this.Color_Cancel = Color_Cancel2;
            return this;
        }

        public Builder setDecorView(ViewGroup decorView2) {
            this.decorView = decorView2;
            return this;
        }

        public Builder setBgColor(int Color_Background_Wheel2) {
            this.Color_Background_Wheel = Color_Background_Wheel2;
            return this;
        }

        public Builder setTitleBgColor(int Color_Background_Title2) {
            this.Color_Background_Title = Color_Background_Title2;
            return this;
        }

        public Builder setTitleColor(int Color_Title2) {
            this.Color_Title = Color_Title2;
            return this;
        }

        public Builder setSubCalSize(int Size_Submit_Cancel2) {
            this.Size_Submit_Cancel = Size_Submit_Cancel2;
            return this;
        }

        public Builder setTitleSize(int Size_Title2) {
            this.Size_Title = Size_Title2;
            return this;
        }

        public Builder setContentSize(int Size_Content2) {
            this.Size_Content = Size_Content2;
            return this;
        }

        public Builder setDate(Calendar date2) {
            this.date = date2;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener customListener2) {
            this.layoutRes = res;
            this.customListener = customListener2;
            return this;
        }

        public Builder setRange(int startYear2, int endYear2) {
            this.startYear = startYear2;
            this.endYear = endYear2;
            return this;
        }

        public Builder setRangDate(Calendar startDate2, Calendar endDate2) {
            this.startDate = startDate2;
            this.endDate = endDate2;
            return this;
        }

        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier2) {
            this.lineSpacingMultiplier = lineSpacingMultiplier2;
            return this;
        }

        public Builder setDividerColor(int dividerColor2) {
            this.dividerColor = dividerColor2;
            return this;
        }

        public Builder setDividerType(DividerType dividerType2) {
            this.dividerType = dividerType2;
            return this;
        }

        public Builder setBackgroundId(int backgroundId2) {
            this.backgroundId = backgroundId2;
            return this;
        }

        public Builder setTextColorCenter(int textColorCenter2) {
            this.textColorCenter = textColorCenter2;
            return this;
        }

        public Builder setTextColorOut(int textColorOut2) {
            this.textColorOut = textColorOut2;
            return this;
        }

        public Builder isCyclic(boolean cyclic2) {
            this.cyclic = cyclic2;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable2) {
            this.cancelable = cancelable2;
            return this;
        }

        public Builder setLabel(String label_year2, String label_month2, String label_day2, String label_hours2, String label_mins2, String label_seconds2) {
            this.label_year = label_year2;
            this.label_month = label_month2;
            this.label_day = label_day2;
            this.label_hours = label_hours2;
            this.label_mins = label_mins2;
            this.label_seconds = label_seconds2;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel2) {
            this.isCenterLabel = isCenterLabel2;
            return this;
        }

        public TimePickerView build() {
            return new TimePickerView(this);
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date, View view);
    }

    public TimePickerView(Builder builder) {
        super(builder.context);
        this.timeSelectListener = builder.timeSelectListener;
        this.gravity = builder.gravity;
        this.type = builder.type;
        this.Str_Submit = builder.Str_Submit;
        this.Str_Cancel = builder.Str_Cancel;
        this.Str_Title = builder.Str_Title;
        this.Color_Submit = builder.Color_Submit;
        this.Color_Cancel = builder.Color_Cancel;
        this.Color_Title = builder.Color_Title;
        this.Color_Background_Wheel = builder.Color_Background_Wheel;
        this.Color_Background_Title = builder.Color_Background_Title;
        this.Size_Submit_Cancel = builder.Size_Submit_Cancel;
        this.Size_Title = builder.Size_Title;
        this.Size_Content = builder.Size_Content;
        this.startYear = builder.startYear;
        this.endYear = builder.endYear;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.date = builder.date;
        this.cyclic = builder.cyclic;
        this.isCenterLabel = builder.isCenterLabel;
        this.cancelable = builder.cancelable;
        this.label_year = builder.label_year;
        this.label_month = builder.label_month;
        this.label_day = builder.label_day;
        this.label_hours = builder.label_hours;
        this.label_mins = builder.label_mins;
        this.label_seconds = builder.label_seconds;
        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.backgroundId = builder.backgroundId;
        this.decorView = builder.decorView;
        initView(builder.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable(this.cancelable);
        initViews(this.backgroundId);
        init();
        initEvents();
        if (this.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, this.contentContainer);
            this.tvTitle = (TextView) findViewById(R.id.tvTitle);
            this.btnSubmit = (Button) findViewById(R.id.btnSubmit);
            this.btnCancel = (Button) findViewById(R.id.btnCancel);
            this.btnSubmit.setTag(TAG_SUBMIT);
            this.btnCancel.setTag(TAG_CANCEL);
            this.btnSubmit.setOnClickListener(this);
            this.btnCancel.setOnClickListener(this);
            this.btnSubmit.setText(TextUtils.isEmpty(this.Str_Submit) ? context.getResources().getString(R.string.pickerview_submit) : this.Str_Submit);
            this.btnCancel.setText(TextUtils.isEmpty(this.Str_Cancel) ? context.getResources().getString(R.string.pickerview_cancel) : this.Str_Cancel);
            this.tvTitle.setText(TextUtils.isEmpty(this.Str_Title) ? "" : this.Str_Title);
            this.btnSubmit.setTextColor(this.Color_Submit == 0 ? this.pickerview_timebtn_nor : this.Color_Submit);
            this.btnCancel.setTextColor(this.Color_Cancel == 0 ? this.pickerview_timebtn_nor : this.Color_Cancel);
            this.tvTitle.setTextColor(this.Color_Title == 0 ? this.pickerview_topbar_title : this.Color_Title);
            this.btnSubmit.setTextSize((float) this.Size_Submit_Cancel);
            this.btnCancel.setTextSize((float) this.Size_Submit_Cancel);
            this.tvTitle.setTextSize((float) this.Size_Title);
            ((RelativeLayout) findViewById(R.id.rv_topbar)).setBackgroundColor(this.Color_Background_Title == 0 ? this.pickerview_bg_topbar : this.Color_Background_Title);
        } else {
            this.customListener.customLayout(LayoutInflater.from(context).inflate(this.layoutRes, this.contentContainer));
        }
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);
        timePickerView.setBackgroundColor(this.Color_Background_Wheel == 0 ? this.bgColor_default : this.Color_Background_Wheel);
        this.wheelTime = new WheelTime(timePickerView, this.type, this.gravity, this.Size_Content);
        if (!(this.startYear == 0 || this.endYear == 0 || this.startYear > this.endYear)) {
            setRange();
        }
        if (this.startDate == null || this.endDate == null) {
            if (this.startDate != null && this.endDate == null) {
                setRangDate();
            } else if (this.startDate == null && this.endDate != null) {
                setRangDate();
            }
        } else if (this.startDate.getTimeInMillis() <= this.endDate.getTimeInMillis()) {
            setRangDate();
        }
        setTime();
        this.wheelTime.setLabels(this.label_year, this.label_month, this.label_day, this.label_hours, this.label_mins, this.label_seconds);
        setOutSideCancelable(this.cancelable);
        this.wheelTime.setCyclic(this.cyclic);
        this.wheelTime.setDividerColor(this.dividerColor);
        this.wheelTime.setDividerType(this.dividerType);
        this.wheelTime.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wheelTime.setTextColorOut(this.textColorOut);
        this.wheelTime.setTextColorCenter(this.textColorCenter);
        this.wheelTime.isCenterLabel(Boolean.valueOf(this.isCenterLabel));
    }

    public void setDate(Calendar date2) {
        this.date = date2;
        setTime();
    }

    private void setRange() {
        this.wheelTime.setStartYear(this.startYear);
        this.wheelTime.setEndYear(this.endYear);
    }

    private void setRangDate() {
        this.wheelTime.setRangDate(this.startDate, this.endDate);
        if (this.startDate == null || this.endDate == null) {
            if (this.startDate != null) {
                this.date = this.startDate;
            } else if (this.endDate != null) {
                this.date = this.endDate;
            }
        } else if (this.date == null || this.date.getTimeInMillis() < this.startDate.getTimeInMillis() || this.date.getTimeInMillis() > this.endDate.getTimeInMillis()) {
            this.date = this.startDate;
        }
    }

    private void setTime() {
        int year;
        int month;
        int day;
        int hours;
        int minute;
        int seconds;
        Calendar calendar = Calendar.getInstance();
        if (this.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(1);
            month = calendar.get(2);
            day = calendar.get(5);
            hours = calendar.get(11);
            minute = calendar.get(12);
            seconds = calendar.get(13);
        } else {
            year = this.date.get(1);
            month = this.date.get(2);
            day = this.date.get(5);
            hours = this.date.get(11);
            minute = this.date.get(12);
            seconds = this.date.get(13);
        }
        this.wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }

    public void onClick(View v) {
        if (((String) v.getTag()).equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    public void returnData() {
        if (this.timeSelectListener != null) {
            try {
                this.timeSelectListener.onTimeSelect(WheelTime.dateFormat.parse(this.wheelTime.getTime()), this.clickView);
            } catch (ParseException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public boolean isDialog() {
        return this.isDialog;
    }
}
