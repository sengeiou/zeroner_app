package me.yokeyword.fragmentation.debug;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.lib_common.R;
import java.util.List;

public class DebugHierarchyViewContainer extends ScrollView {
    /* access modifiers changed from: private */
    public Context mContext;
    private int mItemHeight;
    private LinearLayout mLinearLayout;
    private int mPadding;
    private LinearLayout mTitleLayout;

    public DebugHierarchyViewContainer(Context context) {
        super(context);
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        HorizontalScrollView hScrollView = new HorizontalScrollView(context);
        this.mLinearLayout = new LinearLayout(context);
        this.mLinearLayout.setOrientation(1);
        hScrollView.addView(this.mLinearLayout);
        addView(hScrollView);
        this.mItemHeight = dip2px(50.0f);
        this.mPadding = dip2px(16.0f);
    }

    private int dip2px(float dp) {
        return (int) ((dp * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void bindFragmentRecords(List<DebugFragmentRecord> fragmentRecords) {
        this.mLinearLayout.removeAllViews();
        this.mLinearLayout.addView(getTitleLayout());
        if (fragmentRecords != null) {
            setView(fragmentRecords, 0, null);
        }
    }

    @NonNull
    private LinearLayout getTitleLayout() {
        if (this.mTitleLayout != null) {
            return this.mTitleLayout;
        }
        this.mTitleLayout = new LinearLayout(this.mContext);
        this.mTitleLayout.setPadding(dip2px(24.0f), dip2px(24.0f), 0, dip2px(8.0f));
        this.mTitleLayout.setOrientation(0);
        this.mTitleLayout.setLayoutParams(new LayoutParams(-1, -2));
        TextView title = new TextView(this.mContext);
        title.setText("栈视图(Stack)");
        title.setTextSize(20.0f);
        title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2, -2);
        p.gravity = 16;
        title.setLayoutParams(p);
        this.mTitleLayout.addView(title);
        ImageView img = new ImageView(this.mContext);
        img.setImageResource(R.drawable.fragmentation_help);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.leftMargin = dip2px(16.0f);
        params.gravity = 16;
        img.setLayoutParams(params);
        this.mTitleLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(DebugHierarchyViewContainer.this.mContext, "* means not in backBack.", 0).show();
            }
        });
        this.mTitleLayout.addView(img);
        return this.mTitleLayout;
    }

    private void setView(List<DebugFragmentRecord> fragmentRecordList, int hierarchy, TextView tvItem) {
        for (int i = fragmentRecordList.size() - 1; i >= 0; i--) {
            DebugFragmentRecord child = (DebugFragmentRecord) fragmentRecordList.get(i);
            int tempHierarchy = hierarchy;
            final TextView childTvItem = getTextView(child, tempHierarchy);
            childTvItem.setTag(R.id.hierarchy, Integer.valueOf(tempHierarchy));
            final List<DebugFragmentRecord> childFragmentRecord = child.childFragmentRecord;
            if (childFragmentRecord == null || childFragmentRecord.size() <= 0) {
                childTvItem.setPadding(childTvItem.getPaddingLeft() + this.mPadding, 0, this.mPadding, 0);
            } else {
                int tempHierarchy2 = tempHierarchy + 1;
                childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_ic_right, 0, 0, 0);
                final int finalChilHierarchy = tempHierarchy2;
                childTvItem.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        boolean z;
                        if (v.getTag(R.id.isexpand) != null) {
                            boolean isExpand = ((Boolean) v.getTag(R.id.isexpand)).booleanValue();
                            if (isExpand) {
                                childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_ic_right, 0, 0, 0);
                                DebugHierarchyViewContainer.this.removeView(finalChilHierarchy);
                            } else {
                                DebugHierarchyViewContainer.this.handleExpandView(childFragmentRecord, finalChilHierarchy, childTvItem);
                            }
                            int i = R.id.isexpand;
                            if (!isExpand) {
                                z = true;
                            } else {
                                z = false;
                            }
                            v.setTag(i, Boolean.valueOf(z));
                            return;
                        }
                        childTvItem.setTag(R.id.isexpand, Boolean.valueOf(true));
                        DebugHierarchyViewContainer.this.handleExpandView(childFragmentRecord, finalChilHierarchy, childTvItem);
                    }
                });
            }
            if (tvItem == null) {
                this.mLinearLayout.addView(childTvItem);
            } else {
                this.mLinearLayout.addView(childTvItem, this.mLinearLayout.indexOfChild(tvItem) + 1);
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleExpandView(List<DebugFragmentRecord> childFragmentRecord, int finalChilHierarchy, TextView childTvItem) {
        setView(childFragmentRecord, finalChilHierarchy, childTvItem);
        childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_ic_expandable, 0, 0, 0);
    }

    /* access modifiers changed from: private */
    public void removeView(int hierarchy) {
        for (int i = this.mLinearLayout.getChildCount() - 1; i >= 0; i--) {
            View view = this.mLinearLayout.getChildAt(i);
            if (view.getTag(R.id.hierarchy) != null && ((Integer) view.getTag(R.id.hierarchy)).intValue() >= hierarchy) {
                this.mLinearLayout.removeView(view);
            }
        }
    }

    private TextView getTextView(DebugFragmentRecord fragmentRecord, int hierarchy) {
        TextView tvItem = new TextView(this.mContext);
        tvItem.setLayoutParams(new LayoutParams(-1, this.mItemHeight));
        if (hierarchy == 0) {
            tvItem.setTextColor(Color.parseColor("#333333"));
            tvItem.setTextSize(16.0f);
        }
        tvItem.setGravity(16);
        tvItem.setPadding((int) (((double) this.mPadding) + (((double) (this.mPadding * hierarchy)) * 1.5d)), 0, this.mPadding, 0);
        tvItem.setCompoundDrawablePadding(this.mPadding / 2);
        TypedArray a = this.mContext.obtainStyledAttributes(new int[]{16843534});
        tvItem.setBackgroundDrawable(a.getDrawable(0));
        a.recycle();
        tvItem.setText(fragmentRecord.fragmentName);
        return tvItem;
    }
}
