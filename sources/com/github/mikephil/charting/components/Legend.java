package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class Legend extends ComponentBase {
    private List<Boolean> mCalculatedLabelBreakPoints;
    private List<FSize> mCalculatedLabelSizes;
    private List<FSize> mCalculatedLineSizes;
    private LegendDirection mDirection;
    private boolean mDrawInside;
    private LegendEntry[] mEntries;
    private LegendEntry[] mExtraEntries;
    private DashPathEffect mFormLineDashEffect;
    private float mFormLineWidth;
    private float mFormSize;
    private float mFormToTextSpace;
    private LegendHorizontalAlignment mHorizontalAlignment;
    private boolean mIsLegendCustom;
    private float mMaxSizePercent;
    public float mNeededHeight;
    public float mNeededWidth;
    private LegendOrientation mOrientation;
    private LegendForm mShape;
    private float mStackSpace;
    public float mTextHeightMax;
    public float mTextWidthMax;
    private LegendVerticalAlignment mVerticalAlignment;
    private boolean mWordWrapEnabled;
    private float mXEntrySpace;
    private float mYEntrySpace;

    public enum LegendDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public enum LegendForm {
        NONE,
        EMPTY,
        DEFAULT,
        SQUARE,
        CIRCLE,
        LINE
    }

    public enum LegendHorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum LegendOrientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum LegendVerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }

    public Legend() {
        this.mEntries = new LegendEntry[0];
        this.mIsLegendCustom = false;
        this.mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
        this.mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
        this.mOrientation = LegendOrientation.HORIZONTAL;
        this.mDrawInside = false;
        this.mDirection = LegendDirection.LEFT_TO_RIGHT;
        this.mShape = LegendForm.SQUARE;
        this.mFormSize = 8.0f;
        this.mFormLineWidth = 3.0f;
        this.mFormLineDashEffect = null;
        this.mXEntrySpace = 6.0f;
        this.mYEntrySpace = 0.0f;
        this.mFormToTextSpace = 5.0f;
        this.mStackSpace = 3.0f;
        this.mMaxSizePercent = 0.95f;
        this.mNeededWidth = 0.0f;
        this.mNeededHeight = 0.0f;
        this.mTextHeightMax = 0.0f;
        this.mTextWidthMax = 0.0f;
        this.mWordWrapEnabled = false;
        this.mCalculatedLabelSizes = new ArrayList(16);
        this.mCalculatedLabelBreakPoints = new ArrayList(16);
        this.mCalculatedLineSizes = new ArrayList(16);
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(3.0f);
    }

    public Legend(LegendEntry[] entries) {
        this();
        if (entries == null) {
            throw new IllegalArgumentException("entries array is NULL");
        }
        this.mEntries = entries;
    }

    public void setEntries(List<LegendEntry> entries) {
        this.mEntries = (LegendEntry[]) entries.toArray(new LegendEntry[entries.size()]);
    }

    public LegendEntry[] getEntries() {
        return this.mEntries;
    }

    public float getMaximumEntryWidth(Paint p) {
        float max = 0.0f;
        float maxFormSize = 0.0f;
        float formToTextSpace = Utils.convertDpToPixel(this.mFormToTextSpace);
        LegendEntry[] legendEntryArr = this.mEntries;
        int length = legendEntryArr.length;
        for (int i = 0; i < length; i++) {
            LegendEntry entry = legendEntryArr[i];
            float formSize = Utils.convertDpToPixel(Float.isNaN(entry.formSize) ? this.mFormSize : entry.formSize);
            if (formSize > maxFormSize) {
                maxFormSize = formSize;
            }
            String label = entry.label;
            if (label != null) {
                float length2 = (float) Utils.calcTextWidth(p, label);
                if (length2 > max) {
                    max = length2;
                }
            }
        }
        return max + maxFormSize + formToTextSpace;
    }

    public float getMaximumEntryHeight(Paint p) {
        float max = 0.0f;
        for (LegendEntry entry : this.mEntries) {
            String label = entry.label;
            if (label != null) {
                float length = (float) Utils.calcTextHeight(p, label);
                if (length > max) {
                    max = length;
                }
            }
        }
        return max;
    }

    public LegendEntry[] getExtraEntries() {
        return this.mExtraEntries;
    }

    public void setExtra(List<LegendEntry> entries) {
        this.mExtraEntries = (LegendEntry[]) entries.toArray(new LegendEntry[entries.size()]);
    }

    public void setExtra(LegendEntry[] entries) {
        if (entries == null) {
            entries = new LegendEntry[0];
        }
        this.mExtraEntries = entries;
    }

    public void setExtra(int[] colors, String[] labels) {
        List<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < Math.min(colors.length, labels.length); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors[i];
            entry.label = labels[i];
            if (entry.formColor == 1122868 || entry.formColor == 0) {
                entry.form = LegendForm.NONE;
            } else if (entry.formColor == 1122867) {
                entry.form = LegendForm.EMPTY;
            }
            entries.add(entry);
        }
        this.mExtraEntries = (LegendEntry[]) entries.toArray(new LegendEntry[entries.size()]);
    }

    public void setCustom(LegendEntry[] entries) {
        this.mEntries = entries;
        this.mIsLegendCustom = true;
    }

    public void setCustom(List<LegendEntry> entries) {
        this.mEntries = (LegendEntry[]) entries.toArray(new LegendEntry[entries.size()]);
        this.mIsLegendCustom = true;
    }

    public void resetCustom() {
        this.mIsLegendCustom = false;
    }

    public boolean isLegendCustom() {
        return this.mIsLegendCustom;
    }

    public LegendHorizontalAlignment getHorizontalAlignment() {
        return this.mHorizontalAlignment;
    }

    public void setHorizontalAlignment(LegendHorizontalAlignment value) {
        this.mHorizontalAlignment = value;
    }

    public LegendVerticalAlignment getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public void setVerticalAlignment(LegendVerticalAlignment value) {
        this.mVerticalAlignment = value;
    }

    public LegendOrientation getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(LegendOrientation value) {
        this.mOrientation = value;
    }

    public boolean isDrawInsideEnabled() {
        return this.mDrawInside;
    }

    public void setDrawInside(boolean value) {
        this.mDrawInside = value;
    }

    public LegendDirection getDirection() {
        return this.mDirection;
    }

    public void setDirection(LegendDirection pos) {
        this.mDirection = pos;
    }

    public LegendForm getForm() {
        return this.mShape;
    }

    public void setForm(LegendForm shape) {
        this.mShape = shape;
    }

    public void setFormSize(float size) {
        this.mFormSize = size;
    }

    public float getFormSize() {
        return this.mFormSize;
    }

    public void setFormLineWidth(float size) {
        this.mFormLineWidth = size;
    }

    public float getFormLineWidth() {
        return this.mFormLineWidth;
    }

    public void setFormLineDashEffect(DashPathEffect dashPathEffect) {
        this.mFormLineDashEffect = dashPathEffect;
    }

    public DashPathEffect getFormLineDashEffect() {
        return this.mFormLineDashEffect;
    }

    public float getXEntrySpace() {
        return this.mXEntrySpace;
    }

    public void setXEntrySpace(float space) {
        this.mXEntrySpace = space;
    }

    public float getYEntrySpace() {
        return this.mYEntrySpace;
    }

    public void setYEntrySpace(float space) {
        this.mYEntrySpace = space;
    }

    public float getFormToTextSpace() {
        return this.mFormToTextSpace;
    }

    public void setFormToTextSpace(float space) {
        this.mFormToTextSpace = space;
    }

    public float getStackSpace() {
        return this.mStackSpace;
    }

    public void setStackSpace(float space) {
        this.mStackSpace = space;
    }

    public void setWordWrapEnabled(boolean enabled) {
        this.mWordWrapEnabled = enabled;
    }

    public boolean isWordWrapEnabled() {
        return this.mWordWrapEnabled;
    }

    public float getMaxSizePercent() {
        return this.mMaxSizePercent;
    }

    public void setMaxSizePercent(float maxSize) {
        this.mMaxSizePercent = maxSize;
    }

    public List<FSize> getCalculatedLabelSizes() {
        return this.mCalculatedLabelSizes;
    }

    public List<Boolean> getCalculatedLabelBreakPoints() {
        return this.mCalculatedLabelBreakPoints;
    }

    public List<FSize> getCalculatedLineSizes() {
        return this.mCalculatedLineSizes;
    }

    public void calculateDimensions(Paint labelpaint, ViewPortHandler viewPortHandler) {
        int size;
        float formSize;
        float requiredWidth;
        float requiredSpacing;
        int i;
        float formSize2;
        float defaultFormSize = Utils.convertDpToPixel(this.mFormSize);
        float stackSpace = Utils.convertDpToPixel(this.mStackSpace);
        float formToTextSpace = Utils.convertDpToPixel(this.mFormToTextSpace);
        float xEntrySpace = Utils.convertDpToPixel(this.mXEntrySpace);
        float yEntrySpace = Utils.convertDpToPixel(this.mYEntrySpace);
        boolean wordWrapEnabled = this.mWordWrapEnabled;
        LegendEntry[] entries = this.mEntries;
        int entryCount = entries.length;
        this.mTextWidthMax = getMaximumEntryWidth(labelpaint);
        this.mTextHeightMax = getMaximumEntryHeight(labelpaint);
        switch (this.mOrientation) {
            case VERTICAL:
                float maxWidth = 0.0f;
                float maxHeight = 0.0f;
                float width = 0.0f;
                float labelLineHeight = Utils.getLineHeight(labelpaint);
                boolean wasStacked = false;
                for (int i2 = 0; i2 < entryCount; i2++) {
                    LegendEntry e = entries[i2];
                    boolean drawingForm = e.form != LegendForm.NONE;
                    if (Float.isNaN(e.formSize)) {
                        formSize2 = defaultFormSize;
                    } else {
                        formSize2 = Utils.convertDpToPixel(e.formSize);
                    }
                    String label = e.label;
                    if (!wasStacked) {
                        width = 0.0f;
                    }
                    if (drawingForm) {
                        if (wasStacked) {
                            width += stackSpace;
                        }
                        width += formSize2;
                    }
                    if (label != null) {
                        if (drawingForm && !wasStacked) {
                            width += formToTextSpace;
                        } else if (wasStacked) {
                            maxWidth = Math.max(maxWidth, width);
                            maxHeight += labelLineHeight + yEntrySpace;
                            width = 0.0f;
                            wasStacked = false;
                        }
                        width += (float) Utils.calcTextWidth(labelpaint, label);
                        if (i2 < entryCount - 1) {
                            maxHeight += labelLineHeight + yEntrySpace;
                        }
                    } else {
                        wasStacked = true;
                        width += formSize2;
                        if (i2 < entryCount - 1) {
                            width += stackSpace;
                        }
                    }
                    maxWidth = Math.max(maxWidth, width);
                }
                this.mNeededWidth = maxWidth;
                this.mNeededHeight = maxHeight;
                break;
            case HORIZONTAL:
                float labelLineHeight2 = Utils.getLineHeight(labelpaint);
                float labelLineSpacing = Utils.getLineSpacing(labelpaint) + yEntrySpace;
                float contentWidth = viewPortHandler.contentWidth() * this.mMaxSizePercent;
                float maxLineWidth = 0.0f;
                float currentLineWidth = 0.0f;
                float requiredWidth2 = 0.0f;
                int stackedStartIndex = -1;
                this.mCalculatedLabelBreakPoints.clear();
                this.mCalculatedLabelSizes.clear();
                this.mCalculatedLineSizes.clear();
                for (int i3 = 0; i3 < entryCount; i3++) {
                    LegendEntry e2 = entries[i3];
                    boolean drawingForm2 = e2.form != LegendForm.NONE;
                    if (Float.isNaN(e2.formSize)) {
                        formSize = defaultFormSize;
                    } else {
                        formSize = Utils.convertDpToPixel(e2.formSize);
                    }
                    String label2 = e2.label;
                    this.mCalculatedLabelBreakPoints.add(Boolean.valueOf(false));
                    if (stackedStartIndex == -1) {
                        requiredWidth = 0.0f;
                    } else {
                        requiredWidth = requiredWidth2 + stackSpace;
                    }
                    if (label2 != null) {
                        this.mCalculatedLabelSizes.add(Utils.calcTextSize(labelpaint, label2));
                        requiredWidth2 = requiredWidth + (drawingForm2 ? formToTextSpace + formSize : 0.0f) + ((FSize) this.mCalculatedLabelSizes.get(i3)).width;
                    } else {
                        this.mCalculatedLabelSizes.add(FSize.getInstance(0.0f, 0.0f));
                        if (!drawingForm2) {
                            formSize = 0.0f;
                        }
                        requiredWidth2 = requiredWidth + formSize;
                        if (stackedStartIndex == -1) {
                            stackedStartIndex = i3;
                        }
                    }
                    if (label2 != null || i3 == entryCount - 1) {
                        if (currentLineWidth == 0.0f) {
                            requiredSpacing = 0.0f;
                        } else {
                            requiredSpacing = xEntrySpace;
                        }
                        if (!wordWrapEnabled || currentLineWidth == 0.0f || contentWidth - currentLineWidth >= requiredSpacing + requiredWidth2) {
                            currentLineWidth += requiredSpacing + requiredWidth2;
                        } else {
                            this.mCalculatedLineSizes.add(FSize.getInstance(currentLineWidth, labelLineHeight2));
                            maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                            List<Boolean> list = this.mCalculatedLabelBreakPoints;
                            if (stackedStartIndex > -1) {
                                i = stackedStartIndex;
                            } else {
                                i = i3;
                            }
                            list.set(i, Boolean.valueOf(true));
                            currentLineWidth = requiredWidth2;
                        }
                        if (i3 == entryCount - 1) {
                            this.mCalculatedLineSizes.add(FSize.getInstance(currentLineWidth, labelLineHeight2));
                            maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                        }
                    }
                    if (label2 != null) {
                        stackedStartIndex = -1;
                    }
                }
                this.mNeededWidth = maxLineWidth;
                float size2 = labelLineHeight2 * ((float) this.mCalculatedLineSizes.size());
                if (this.mCalculatedLineSizes.size() == 0) {
                    size = 0;
                } else {
                    size = this.mCalculatedLineSizes.size() - 1;
                }
                this.mNeededHeight = (((float) size) * labelLineSpacing) + size2;
                break;
        }
        this.mNeededHeight += this.mYOffset;
        this.mNeededWidth += this.mXOffset;
    }
}
