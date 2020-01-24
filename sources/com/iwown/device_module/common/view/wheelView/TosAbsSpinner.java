package com.iwown.device_module.common.view.wheelView;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import com.iwown.device_module.R;

public abstract class TosAbsSpinner extends TosAdapterView<SpinnerAdapter> {
    SpinnerAdapter mAdapter;
    boolean mBlockLayoutRequests;
    private DataSetObserver mDataSetObserver;
    int mHeightMeasureSpec;
    Interpolator mInterpolator;
    RecycleBin mRecycler;
    View mSelectedView;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    Rect mSpinnerPadding;
    private Rect mTouchFrame;
    int mWidthMeasureSpec;

    class RecycleBin {
        private SparseArray<View> mScrapHeap = new SparseArray<>();

        RecycleBin() {
        }

        public void put(int position, View v) {
            this.mScrapHeap.put(position, v);
        }

        /* access modifiers changed from: 0000 */
        public View get(int position) {
            View result = (View) this.mScrapHeap.get(position);
            if (result != null) {
                this.mScrapHeap.delete(position);
            }
            return result;
        }

        /* access modifiers changed from: 0000 */
        public View peek(int position) {
            return (View) this.mScrapHeap.get(position);
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            SparseArray<View> scrapHeap = this.mScrapHeap;
            int count = scrapHeap.size();
            for (int i = 0; i < count; i++) {
                View view = (View) scrapHeap.valueAt(i);
                if (view != null) {
                    TosAbsSpinner.this.removeDetachedView(view, true);
                }
            }
            scrapHeap.clear();
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int position;
        long selectedId;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selectedId = in.readLong();
            this.position = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.selectedId);
            out.writeInt(this.position);
        }

        public String toString() {
            return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " position=" + this.position + "}";
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void layout(int i, boolean z);

    /* access modifiers changed from: protected */
    public DataSetObserver getDataSetObserver() {
        return this.mDataSetObserver;
    }

    public TosAbsSpinner(Context context) {
        super(context);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new Rect();
        this.mSelectedView = null;
        this.mRecycler = new RecycleBin();
        initAbsSpinner();
    }

    public TosAbsSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TosAbsSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new Rect();
        this.mSelectedView = null;
        this.mRecycler = new RecycleBin();
        initAbsSpinner();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_AbsSpinner, defStyle, 0);
        CharSequence[] entries = a.getTextArray(R.styleable.device_module_AbsSpinner_device_module_entries);
        if (entries != null) {
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, 17367048, entries);
            adapter.setDropDownViewResource(17367049);
            setAdapter((SpinnerAdapter) adapter);
        }
        a.recycle();
    }

    private void initAbsSpinner() {
        setFocusable(true);
        setWillNotDraw(false);
    }

    public void setAdapter(SpinnerAdapter adapter) {
        int position = -1;
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            resetList();
        }
        this.mAdapter = adapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            if (this.mItemCount > 0) {
                position = 0;
            }
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            checkFocus();
            resetList();
            checkSelectionChanged();
        }
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void resetList() {
        this.mDataChanged = false;
        this.mNeedSync = false;
        removeAllViewsInLayout();
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        this.mSpinnerPadding.left = getPaddingLeft() > this.mSelectionLeftPadding ? getPaddingLeft() : this.mSelectionLeftPadding;
        this.mSpinnerPadding.top = getPaddingTop() > this.mSelectionTopPadding ? getPaddingTop() : this.mSelectionTopPadding;
        this.mSpinnerPadding.right = getPaddingRight() > this.mSelectionRightPadding ? getPaddingRight() : this.mSelectionRightPadding;
        this.mSpinnerPadding.bottom = getPaddingBottom() > this.mSelectionBottomPadding ? getPaddingBottom() : this.mSelectionBottomPadding;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        int preferredHeight = 0;
        int preferredWidth = 0;
        boolean needsMeasuring = true;
        int selectedPosition = getSelectedItemPosition();
        if (selectedPosition >= 0 && this.mAdapter != null) {
            View view = this.mRecycler.get(selectedPosition);
            if (view == null) {
                view = this.mAdapter.getView(selectedPosition, null, this);
            }
            if (view != null) {
                this.mRecycler.put(selectedPosition, view);
            }
            if (view != null) {
                if (view.getLayoutParams() == null) {
                    this.mBlockLayoutRequests = true;
                    view.setLayoutParams(generateDefaultLayoutParams());
                    this.mBlockLayoutRequests = false;
                }
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
                preferredHeight = getChildHeight(view) + this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
                preferredWidth = getChildWidth(view) + this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                needsMeasuring = false;
            }
        }
        if (needsMeasuring) {
            preferredHeight = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
            if (widthMode == 0) {
                preferredWidth = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
            }
        }
        setMeasuredDimension(resolveSize(Math.max(preferredWidth, getSuggestedMinimumWidth()), widthMeasureSpec), resolveSize(Math.max(preferredHeight, getSuggestedMinimumHeight()), heightMeasureSpec));
        this.mHeightMeasureSpec = heightMeasureSpec;
        this.mWidthMeasureSpec = widthMeasureSpec;
    }

    /* access modifiers changed from: 0000 */
    public int getChildHeight(View child) {
        return child.getMeasuredHeight();
    }

    /* access modifiers changed from: 0000 */
    public int getChildWidth(View child) {
        return child.getMeasuredWidth();
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    /* access modifiers changed from: 0000 */
    public void recycleAllViews() {
        int childCount = getChildCount();
        RecycleBin recycleBin = this.mRecycler;
        for (int i = 0; i < childCount; i++) {
            recycleBin.put(this.mFirstPosition + i, getChildAt(i));
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleDataChanged() {
        super.handleDataChanged();
    }

    public void setSelection(int position, boolean animate) {
        setSelectionInt(position, animate && this.mFirstPosition <= position && position <= (this.mFirstPosition + getChildCount()) + -1);
        checkSelectionChanged();
    }

    public void setSelection(int position) {
        setNextSelectedPositionInt(position);
        requestLayout();
        invalidate();
        checkSelectionChanged();
    }

    /* access modifiers changed from: 0000 */
    public void setSelectionInt(int position, boolean animate) {
        if (position != this.mOldSelectedPosition) {
            this.mBlockLayoutRequests = true;
            int delta = position - this.mSelectedPosition;
            setNextSelectedPositionInt(position);
            layout(delta, animate);
            this.mBlockLayoutRequests = false;
        }
    }

    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    public SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getCount() {
        return this.mItemCount;
    }

    public int pointToPosition(int x, int y) {
        Rect frame = this.mTouchFrame;
        if (frame == null) {
            this.mTouchFrame = new Rect();
            frame = this.mTouchFrame;
        }
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.selectedId = getSelectedItemId();
        if (ss.selectedId >= 0) {
            ss.position = getSelectedItemPosition();
        } else {
            ss.position = -1;
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.selectedId >= 0) {
            this.mDataChanged = true;
            this.mNeedSync = true;
            this.mSyncRowId = ss.selectedId;
            this.mSyncPosition = ss.position;
            this.mSyncMode = 0;
            requestLayout();
        }
    }
}
