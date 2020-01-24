package com.bigkoo.pickerview;

import android.content.Context;
import android.graphics.Typeface;
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
import com.bigkoo.pickerview.view.WheelOptions;
import com.iwown.lib_common.R;
import java.util.List;

public class OptionsPickerView<T> extends BasePickerView implements OnClickListener {
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
    private boolean cyclic1;
    private boolean cyclic2;
    private boolean cyclic3;
    private int dividerColor;
    private DividerType dividerType;
    private Typeface font;
    private boolean isCenterLabel;
    private boolean isDialog;
    private final int itemsVisible;
    private String label1;
    private String label2;
    private String label3;
    private int layoutRes;
    private float lineSpacingMultiplier = 1.6f;
    private boolean linkage;
    private int option1;
    private int option2;
    private int option3;
    private OnOptionsSelectListener optionsSelectListener;
    private RelativeLayout rv_top_bar;
    private final boolean showLfetLine;
    private int textColorCenter;
    private int textColorOut;
    private TextView tvTitle;
    WheelOptions<T> wheelOptions;

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
        public boolean cyclic1 = false;
        /* access modifiers changed from: private */
        public boolean cyclic2 = false;
        /* access modifiers changed from: private */
        public boolean cyclic3 = false;
        public ViewGroup decorView;
        /* access modifiers changed from: private */
        public int dividerColor;
        /* access modifiers changed from: private */
        public DividerType dividerType;
        /* access modifiers changed from: private */
        public Typeface font;
        /* access modifiers changed from: private */
        public boolean isCenterLabel = true;
        /* access modifiers changed from: private */
        public boolean isDialog;
        /* access modifiers changed from: private */
        public int itemsVisible;
        /* access modifiers changed from: private */
        public String label1;
        /* access modifiers changed from: private */
        public String label2;
        /* access modifiers changed from: private */
        public String label3;
        /* access modifiers changed from: private */
        public int layoutRes = R.layout.pickerview_options;
        /* access modifiers changed from: private */
        public float lineSpacingMultiplier = 1.6f;
        /* access modifiers changed from: private */
        public boolean linkage = true;
        /* access modifiers changed from: private */
        public int option1;
        /* access modifiers changed from: private */
        public int option2;
        /* access modifiers changed from: private */
        public int option3;
        /* access modifiers changed from: private */
        public OnOptionsSelectListener optionsSelectListener;
        /* access modifiers changed from: private */
        public boolean showLfetLine;
        /* access modifiers changed from: private */
        public int textColorCenter;
        /* access modifiers changed from: private */
        public int textColorOut;

        public Builder(Context context2, OnOptionsSelectListener listener) {
            this.context = context2;
            this.optionsSelectListener = listener;
        }

        public Builder setSubmitText(String Str_Cancel2) {
            this.Str_Submit = Str_Cancel2;
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

        public Builder isDialog(boolean isDialog2) {
            this.isDialog = isDialog2;
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

        public Builder setBackgroundId(int backgroundId2) {
            this.backgroundId = backgroundId2;
            return this;
        }

        public Builder setDecorView(ViewGroup decorView2) {
            this.decorView = decorView2;
            return this;
        }

        public Builder setItemsVisible(int itemsVisible2) {
            this.itemsVisible = itemsVisible2;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener listener) {
            this.layoutRes = res;
            this.customListener = listener;
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

        public Builder setContentTextSize(int Size_Content2) {
            this.Size_Content = Size_Content2;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable2) {
            this.cancelable = cancelable2;
            return this;
        }

        @Deprecated
        public Builder setLinkage(boolean linkage2) {
            this.linkage = linkage2;
            return this;
        }

        public Builder setLabels(String label12, String label22, String label32) {
            this.label1 = label12;
            this.label2 = label22;
            this.label3 = label32;
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

        public Builder setTextColorCenter(int textColorCenter2) {
            this.textColorCenter = textColorCenter2;
            return this;
        }

        public Builder setTextColorOut(int textColorOut2) {
            this.textColorOut = textColorOut2;
            return this;
        }

        public Builder setTypeface(Typeface font2) {
            this.font = font2;
            return this;
        }

        public Builder setCyclic(boolean cyclic12, boolean cyclic22, boolean cyclic32) {
            this.cyclic1 = cyclic12;
            this.cyclic2 = cyclic22;
            this.cyclic3 = cyclic32;
            return this;
        }

        public Builder setSelectOptions(int option12) {
            this.option1 = option12;
            return this;
        }

        public Builder setSelectOptions(int option12, int option22) {
            this.option1 = option12;
            this.option2 = option22;
            return this;
        }

        public Builder setSelectOptions(int option12, int option22, int option32) {
            this.option1 = option12;
            this.option2 = option22;
            this.option3 = option32;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel2) {
            this.isCenterLabel = isCenterLabel2;
            return this;
        }

        public OptionsPickerView build() {
            return new OptionsPickerView(this);
        }

        public Builder showLeftLine(boolean showLfetLine2) {
            this.showLfetLine = showLfetLine2;
            return this;
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int i, int i2, int i3, View view);
    }

    public OptionsPickerView(Builder builder) {
        super(builder.context);
        this.optionsSelectListener = builder.optionsSelectListener;
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
        this.cyclic1 = builder.cyclic1;
        this.cyclic2 = builder.cyclic2;
        this.cyclic3 = builder.cyclic3;
        this.cancelable = builder.cancelable;
        this.linkage = builder.linkage;
        this.isCenterLabel = builder.isCenterLabel;
        this.label1 = builder.label1;
        this.label2 = builder.label2;
        this.label3 = builder.label3;
        this.font = builder.font;
        this.option1 = builder.option1;
        this.option2 = builder.option2;
        this.option3 = builder.option3;
        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.backgroundId = builder.backgroundId;
        this.decorView = builder.decorView;
        this.itemsVisible = builder.itemsVisible;
        this.showLfetLine = builder.showLfetLine;
        initView(builder.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable(this.cancelable);
        initViews(this.backgroundId);
        init();
        initEvents();
        if (this.customListener == null) {
            LayoutInflater.from(context).inflate(this.layoutRes, this.contentContainer);
            this.tvTitle = (TextView) findViewById(R.id.tvTitle);
            this.rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);
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
            this.rv_top_bar.setBackgroundColor(this.Color_Background_Title == 0 ? this.pickerview_bg_topbar : this.Color_Background_Title);
            this.btnSubmit.setTextSize((float) this.Size_Submit_Cancel);
            this.btnCancel.setTextSize((float) this.Size_Submit_Cancel);
            this.tvTitle.setTextSize((float) this.Size_Title);
            this.tvTitle.setText(this.Str_Title);
        } else {
            this.customListener.customLayout(LayoutInflater.from(context).inflate(this.layoutRes, this.contentContainer));
        }
        LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionspicker);
        optionsPicker.setBackgroundColor(this.Color_Background_Wheel == 0 ? this.bgColor_default : this.Color_Background_Wheel);
        this.wheelOptions = new WheelOptions<>(optionsPicker, Boolean.valueOf(this.linkage));
        this.wheelOptions.setTextContentSize(this.Size_Content);
        this.wheelOptions.setLabels(this.label1, this.label2, this.label3);
        this.wheelOptions.setCyclic(this.cyclic1, this.cyclic2, this.cyclic3);
        this.wheelOptions.setTypeface(this.font);
        this.wheelOptions.setItemsVisible(this.itemsVisible);
        setOutSideCancelable(this.cancelable);
        if (this.tvTitle != null) {
            this.tvTitle.setText(this.Str_Title);
        }
        this.wheelOptions.setDividerColor(this.dividerColor);
        this.wheelOptions.setDividerType(this.dividerType);
        this.wheelOptions.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wheelOptions.setTextColorOut(this.textColorOut);
        this.wheelOptions.setTextColorCenter(this.textColorCenter);
        this.wheelOptions.isCenterLabel(Boolean.valueOf(this.isCenterLabel));
        this.wheelOptions.setShowLeftLine(this.showLfetLine);
    }

    public void setSelectOptions(int option12) {
        this.option1 = option12;
        SetCurrentItems();
    }

    public void setSelectOptions(int option12, int option22) {
        this.option1 = option12;
        this.option2 = option22;
        SetCurrentItems();
    }

    public void setSelectOptions(int option12, int option22, int option32) {
        this.option1 = option12;
        this.option2 = option22;
        this.option3 = option32;
        SetCurrentItems();
    }

    private void SetCurrentItems() {
        if (this.wheelOptions != null) {
            this.wheelOptions.setCurrentItems(this.option1, this.option2, this.option3);
        }
    }

    public void setPicker(List<T> optionsItems) {
        setPicker(optionsItems, null, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items) {
        setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items, List<List<List<T>>> options3Items) {
        this.wheelOptions.setPicker(options1Items, options2Items, options3Items);
        SetCurrentItems();
    }

    public void setNPicker(List<T> options1Items, List<T> options2Items, List<T> options3Items) {
        this.wheelOptions.setNPicker(options1Items, options2Items, options3Items);
        SetCurrentItems();
    }

    public void onClick(View v) {
        if (((String) v.getTag()).equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    public void returnData() {
        if (this.optionsSelectListener != null) {
            int[] optionsCurrentItems = this.wheelOptions.getCurrentItems();
            this.optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], this.clickView);
        }
    }

    public boolean isDialog() {
        return this.isDialog;
    }
}
