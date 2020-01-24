package com.bigkoo.pickerview.view;

import android.graphics.Typeface;
import android.view.View;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.lib.WheelView.DividerType;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.iwown.lib_common.R;
import java.util.List;

public class WheelOptions<T> {
    private List<T> N_mOptions2Items;
    private List<T> N_mOptions3Items;
    int dividerColor;
    private DividerType dividerType;
    float lineSpacingMultiplier = 2.0f;
    private boolean linkage;
    private List<T> mOptions1Items;
    /* access modifiers changed from: private */
    public List<List<T>> mOptions2Items;
    /* access modifiers changed from: private */
    public List<List<List<T>>> mOptions3Items;
    int textColorCenter;
    int textColorOut;
    private View view;
    private OnItemSelectedListener wheelListener_option1;
    /* access modifiers changed from: private */
    public OnItemSelectedListener wheelListener_option2;
    /* access modifiers changed from: private */
    public WheelView wv_option1;
    /* access modifiers changed from: private */
    public WheelView wv_option2;
    /* access modifiers changed from: private */
    public WheelView wv_option3;

    public void setItemsVisible(int itemsVisible) {
        if (itemsVisible != 0) {
            this.wv_option1.setItemsVisible(itemsVisible);
            this.wv_option2.setItemsVisible(itemsVisible);
            this.wv_option3.setItemsVisible(itemsVisible);
        }
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view2) {
        this.view = view2;
    }

    public WheelOptions(View view2, Boolean linkage2) {
        this.linkage = linkage2.booleanValue();
        this.view = view2;
        this.wv_option1 = (WheelView) view2.findViewById(R.id.options1);
        this.wv_option2 = (WheelView) view2.findViewById(R.id.options2);
        this.wv_option3 = (WheelView) view2.findViewById(R.id.options3);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items, List<List<List<T>>> options3Items) {
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        int len = 4;
        if (this.mOptions3Items == null) {
            len = 8;
        }
        if (this.mOptions2Items == null) {
            len = 12;
        }
        this.wv_option1.setAdapter(new ArrayWheelAdapter(this.mOptions1Items, len));
        this.wv_option1.setCurrentItem(0);
        if (this.mOptions2Items != null) {
            this.wv_option2.setAdapter(new ArrayWheelAdapter((List) this.mOptions2Items.get(0)));
        }
        this.wv_option2.setCurrentItem(this.wv_option1.getCurrentItem());
        if (this.mOptions3Items != null) {
            this.wv_option3.setAdapter(new ArrayWheelAdapter((List) ((List) this.mOptions3Items.get(0)).get(0)));
        }
        this.wv_option3.setCurrentItem(this.wv_option3.getCurrentItem());
        this.wv_option1.setIsOptions(true);
        this.wv_option2.setIsOptions(true);
        this.wv_option3.setIsOptions(true);
        if (this.mOptions2Items == null) {
            this.wv_option2.setVisibility(8);
        } else {
            this.wv_option2.setVisibility(0);
        }
        if (this.mOptions3Items == null) {
            this.wv_option3.setVisibility(8);
        } else {
            this.wv_option3.setVisibility(0);
        }
        this.wheelListener_option1 = new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                int opt2Select = 0;
                if (WheelOptions.this.mOptions2Items != null) {
                    opt2Select = WheelOptions.this.wv_option2.getCurrentItem();
                    if (opt2Select >= ((List) WheelOptions.this.mOptions2Items.get(index)).size() - 1) {
                        opt2Select = ((List) WheelOptions.this.mOptions2Items.get(index)).size() - 1;
                    }
                    WheelOptions.this.wv_option2.setAdapter(new ArrayWheelAdapter((List) WheelOptions.this.mOptions2Items.get(index)));
                    WheelOptions.this.wv_option2.setCurrentItem(opt2Select);
                }
                if (WheelOptions.this.mOptions3Items != null) {
                    WheelOptions.this.wheelListener_option2.onItemSelected(opt2Select);
                }
            }
        };
        this.wheelListener_option2 = new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                if (WheelOptions.this.mOptions3Items != null) {
                    int opt1Select = WheelOptions.this.wv_option1.getCurrentItem();
                    if (opt1Select >= WheelOptions.this.mOptions3Items.size() - 1) {
                        opt1Select = WheelOptions.this.mOptions3Items.size() - 1;
                    }
                    if (index >= ((List) WheelOptions.this.mOptions2Items.get(opt1Select)).size() - 1) {
                        index = ((List) WheelOptions.this.mOptions2Items.get(opt1Select)).size() - 1;
                    }
                    int opt3 = WheelOptions.this.wv_option3.getCurrentItem();
                    if (opt3 >= ((List) ((List) WheelOptions.this.mOptions3Items.get(opt1Select)).get(index)).size() - 1) {
                        opt3 = ((List) ((List) WheelOptions.this.mOptions3Items.get(opt1Select)).get(index)).size() - 1;
                    }
                    WheelOptions.this.wv_option3.setAdapter(new ArrayWheelAdapter((List) ((List) WheelOptions.this.mOptions3Items.get(WheelOptions.this.wv_option1.getCurrentItem())).get(index)));
                    WheelOptions.this.wv_option3.setCurrentItem(opt3);
                }
            }
        };
        if (options2Items != null && this.linkage) {
            this.wv_option1.setOnItemSelectedListener(this.wheelListener_option1);
        }
        if (options3Items != null && this.linkage) {
            this.wv_option2.setOnItemSelectedListener(this.wheelListener_option2);
        }
    }

    public void setNPicker(List<T> options1Items, List<T> options2Items, List<T> options3Items) {
        this.mOptions1Items = options1Items;
        this.N_mOptions2Items = options2Items;
        this.N_mOptions3Items = options3Items;
        int len = 4;
        if (this.N_mOptions3Items == null) {
            len = 8;
        }
        if (this.N_mOptions2Items == null) {
            len = 12;
        }
        this.wv_option1.setAdapter(new ArrayWheelAdapter(this.mOptions1Items, len));
        this.wv_option1.setCurrentItem(0);
        if (this.N_mOptions2Items != null) {
            this.wv_option2.setAdapter(new ArrayWheelAdapter(this.N_mOptions2Items));
        }
        this.wv_option2.setCurrentItem(this.wv_option1.getCurrentItem());
        if (this.N_mOptions3Items != null) {
            this.wv_option3.setAdapter(new ArrayWheelAdapter(this.N_mOptions3Items));
        }
        this.wv_option3.setCurrentItem(this.wv_option3.getCurrentItem());
        this.wv_option1.setIsOptions(true);
        this.wv_option2.setIsOptions(true);
        this.wv_option3.setIsOptions(true);
        if (this.N_mOptions2Items == null) {
            this.wv_option2.setVisibility(8);
        } else {
            this.wv_option2.setVisibility(0);
        }
        if (this.N_mOptions3Items == null) {
            this.wv_option3.setVisibility(8);
        } else {
            this.wv_option3.setVisibility(0);
        }
    }

    public void setTextContentSize(int textSize) {
        this.wv_option1.setTextSize((float) textSize);
        this.wv_option2.setTextSize((float) textSize);
        this.wv_option3.setTextSize((float) textSize);
    }

    private void setTextColorOut() {
        this.wv_option1.setTextColorOut(this.textColorOut);
        this.wv_option2.setTextColorOut(this.textColorOut);
        this.wv_option3.setTextColorOut(this.textColorOut);
    }

    private void setTextColorCenter() {
        this.wv_option1.setTextColorCenter(this.textColorCenter);
        this.wv_option2.setTextColorCenter(this.textColorCenter);
        this.wv_option3.setTextColorCenter(this.textColorCenter);
    }

    private void setDividerColor() {
        this.wv_option1.setDividerColor(this.dividerColor);
        this.wv_option2.setDividerColor(this.dividerColor);
        this.wv_option3.setDividerColor(this.dividerColor);
    }

    private void setDividerType() {
        this.wv_option1.setDividerType(this.dividerType);
        this.wv_option2.setDividerType(this.dividerType);
        this.wv_option3.setDividerType(this.dividerType);
    }

    private void setLineSpacingMultiplier() {
        this.wv_option1.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_option2.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_option3.setLineSpacingMultiplier(this.lineSpacingMultiplier);
    }

    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null) {
            this.wv_option1.setLabel(label1);
        }
        if (label2 != null) {
            this.wv_option2.setLabel(label2);
        }
        if (label3 != null) {
            this.wv_option3.setLabel(label3);
        }
    }

    public void setCyclic(boolean cyclic) {
        this.wv_option1.setCyclic(cyclic);
        this.wv_option2.setCyclic(cyclic);
        this.wv_option3.setCyclic(cyclic);
    }

    public void setTypeface(Typeface font) {
        this.wv_option1.setTypeface(font);
        this.wv_option2.setTypeface(font);
        this.wv_option3.setTypeface(font);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        this.wv_option1.setCyclic(cyclic1);
        this.wv_option2.setCyclic(cyclic2);
        this.wv_option3.setCyclic(cyclic3);
    }

    public int[] getCurrentItems() {
        int i = 0;
        int[] currentItems = new int[3];
        currentItems[0] = this.wv_option1.getCurrentItem();
        if (this.mOptions2Items == null || this.mOptions2Items.size() <= 0) {
            currentItems[1] = this.wv_option2.getCurrentItem();
        } else {
            currentItems[1] = this.wv_option2.getCurrentItem() > ((List) this.mOptions2Items.get(currentItems[0])).size() + -1 ? 0 : this.wv_option2.getCurrentItem();
        }
        if (this.mOptions3Items == null || this.mOptions3Items.size() <= 0) {
            currentItems[2] = this.wv_option3.getCurrentItem();
        } else {
            if (this.wv_option3.getCurrentItem() <= ((List) ((List) this.mOptions3Items.get(currentItems[0])).get(currentItems[1])).size() - 1) {
                i = this.wv_option3.getCurrentItem();
            }
            currentItems[2] = i;
        }
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (this.linkage) {
            itemSelected(option1, option2, option3);
        }
        this.wv_option1.setCurrentItem(option1);
        this.wv_option2.setCurrentItem(option2);
        this.wv_option3.setCurrentItem(option3);
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (this.mOptions2Items != null) {
            this.wv_option2.setAdapter(new ArrayWheelAdapter((List) this.mOptions2Items.get(opt1Select)));
            this.wv_option2.setCurrentItem(opt2Select);
        }
        if (this.mOptions3Items != null) {
            this.wv_option3.setAdapter(new ArrayWheelAdapter((List) ((List) this.mOptions3Items.get(opt1Select)).get(opt2Select)));
            this.wv_option3.setCurrentItem(opt3Select);
        }
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier2) {
        this.lineSpacingMultiplier = lineSpacingMultiplier2;
        setLineSpacingMultiplier();
    }

    public void setDividerColor(int dividerColor2) {
        this.dividerColor = dividerColor2;
        setDividerColor();
    }

    public void setDividerType(DividerType dividerType2) {
        this.dividerType = dividerType2;
        setDividerType();
    }

    public void setTextColorCenter(int textColorCenter2) {
        this.textColorCenter = textColorCenter2;
        setTextColorCenter();
    }

    public void setTextColorOut(int textColorOut2) {
        this.textColorOut = textColorOut2;
        setTextColorOut();
    }

    public void isCenterLabel(Boolean isCenterLabel) {
        this.wv_option1.isCenterLabel(isCenterLabel);
        this.wv_option2.isCenterLabel(isCenterLabel);
        this.wv_option3.isCenterLabel(isCenterLabel);
    }

    public void setShowLeftLine(boolean showLfetLine) {
        this.wv_option1.setShowLeftLine(showLfetLine);
    }
}
