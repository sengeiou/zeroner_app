package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ActivityChooserModel.ActivityChooserModelClient;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.google.common.primitives.Ints;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ActivityChooserView extends ViewGroup implements ActivityChooserModelClient {
    private static final String LOG_TAG = "ActivityChooserView";
    private final LinearLayoutCompat mActivityChooserContent;
    private final Drawable mActivityChooserContentBackground;
    final ActivityChooserViewAdapter mAdapter;
    private final Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    final FrameLayout mDefaultActivityButton;
    private final ImageView mDefaultActivityButtonImage;
    final FrameLayout mExpandActivityOverflowButton;
    private final ImageView mExpandActivityOverflowButtonImage;
    int mInitialActivityCount;
    private boolean mIsAttachedToWindow;
    boolean mIsSelectingDefaultActivity;
    private final int mListPopupMaxWidth;
    private ListPopupWindow mListPopupWindow;
    final DataSetObserver mModelDataSetObserver;
    OnDismissListener mOnDismissListener;
    private final OnGlobalLayoutListener mOnGlobalLayoutListener;
    ActionProvider mProvider;

    private class ActivityChooserViewAdapter extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
        private static final int ITEM_VIEW_TYPE_COUNT = 3;
        private static final int ITEM_VIEW_TYPE_FOOTER = 1;
        public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
        public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
        private ActivityChooserModel mDataModel;
        private boolean mHighlightDefaultActivity;
        private int mMaxActivityCount = 4;
        private boolean mShowDefaultActivity;
        private boolean mShowFooterView;

        ActivityChooserViewAdapter() {
        }

        public void setDataModel(ActivityChooserModel dataModel) {
            ActivityChooserModel oldDataModel = ActivityChooserView.this.mAdapter.getDataModel();
            if (oldDataModel != null && ActivityChooserView.this.isShown()) {
                oldDataModel.unregisterObserver(ActivityChooserView.this.mModelDataSetObserver);
            }
            this.mDataModel = dataModel;
            if (dataModel != null && ActivityChooserView.this.isShown()) {
                dataModel.registerObserver(ActivityChooserView.this.mModelDataSetObserver);
            }
            notifyDataSetChanged();
        }

        public int getItemViewType(int position) {
            if (!this.mShowFooterView || position != getCount() - 1) {
                return 0;
            }
            return 1;
        }

        public int getViewTypeCount() {
            return 3;
        }

        public int getCount() {
            int activityCount = this.mDataModel.getActivityCount();
            if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                activityCount--;
            }
            int count = Math.min(activityCount, this.mMaxActivityCount);
            if (this.mShowFooterView) {
                return count + 1;
            }
            return count;
        }

        public Object getItem(int position) {
            switch (getItemViewType(position)) {
                case 0:
                    if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                        position++;
                    }
                    return this.mDataModel.getActivity(position);
                case 1:
                    return null;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            switch (getItemViewType(position)) {
                case 0:
                    if (convertView == null || convertView.getId() != R.id.list_item) {
                        convertView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, parent, false);
                    }
                    PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
                    ResolveInfo activity = (ResolveInfo) getItem(position);
                    ((ImageView) convertView.findViewById(R.id.icon)).setImageDrawable(activity.loadIcon(packageManager));
                    ((TextView) convertView.findViewById(R.id.title)).setText(activity.loadLabel(packageManager));
                    if (!this.mShowDefaultActivity || position != 0 || !this.mHighlightDefaultActivity) {
                        convertView.setActivated(false);
                    } else {
                        convertView.setActivated(true);
                    }
                    return convertView;
                case 1:
                    if (convertView == null || convertView.getId() != 1) {
                        convertView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, parent, false);
                        convertView.setId(1);
                        ((TextView) convertView.findViewById(R.id.title)).setText(ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all));
                    }
                    return convertView;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public int measureContentWidth() {
            int oldMaxActivityCount = this.mMaxActivityCount;
            this.mMaxActivityCount = Integer.MAX_VALUE;
            int contentWidth = 0;
            View itemView = null;
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            for (int i = 0; i < count; i++) {
                itemView = getView(i, itemView, null);
                itemView.measure(widthMeasureSpec, heightMeasureSpec);
                contentWidth = Math.max(contentWidth, itemView.getMeasuredWidth());
            }
            this.mMaxActivityCount = oldMaxActivityCount;
            return contentWidth;
        }

        public void setMaxActivityCount(int maxActivityCount) {
            if (this.mMaxActivityCount != maxActivityCount) {
                this.mMaxActivityCount = maxActivityCount;
                notifyDataSetChanged();
            }
        }

        public ResolveInfo getDefaultActivity() {
            return this.mDataModel.getDefaultActivity();
        }

        public void setShowFooterView(boolean showFooterView) {
            if (this.mShowFooterView != showFooterView) {
                this.mShowFooterView = showFooterView;
                notifyDataSetChanged();
            }
        }

        public int getActivityCount() {
            return this.mDataModel.getActivityCount();
        }

        public int getHistorySize() {
            return this.mDataModel.getHistorySize();
        }

        public ActivityChooserModel getDataModel() {
            return this.mDataModel;
        }

        public void setShowDefaultActivity(boolean showDefaultActivity, boolean highlightDefaultActivity) {
            if (this.mShowDefaultActivity != showDefaultActivity || this.mHighlightDefaultActivity != highlightDefaultActivity) {
                this.mShowDefaultActivity = showDefaultActivity;
                this.mHighlightDefaultActivity = highlightDefaultActivity;
                notifyDataSetChanged();
            }
        }

        public boolean getShowDefaultActivity() {
            return this.mShowDefaultActivity;
        }
    }

    private class Callbacks implements OnItemClickListener, OnClickListener, OnLongClickListener, OnDismissListener {
        Callbacks() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (((ActivityChooserViewAdapter) parent.getAdapter()).getItemViewType(position)) {
                case 0:
                    ActivityChooserView.this.dismissPopup();
                    if (!ActivityChooserView.this.mIsSelectingDefaultActivity) {
                        if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                            position++;
                        }
                        Intent launchIntent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(position);
                        if (launchIntent != null) {
                            launchIntent.addFlags(524288);
                            ActivityChooserView.this.getContext().startActivity(launchIntent);
                            return;
                        }
                        return;
                    } else if (position > 0) {
                        ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(position);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void onClick(View view) {
            if (view == ActivityChooserView.this.mDefaultActivityButton) {
                ActivityChooserView.this.dismissPopup();
                Intent launchIntent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex(ActivityChooserView.this.mAdapter.getDefaultActivity()));
                if (launchIntent != null) {
                    launchIntent.addFlags(524288);
                    ActivityChooserView.this.getContext().startActivity(launchIntent);
                }
            } else if (view == ActivityChooserView.this.mExpandActivityOverflowButton) {
                ActivityChooserView.this.mIsSelectingDefaultActivity = false;
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public boolean onLongClick(View view) {
            if (view == ActivityChooserView.this.mDefaultActivityButton) {
                if (ActivityChooserView.this.mAdapter.getCount() > 0) {
                    ActivityChooserView.this.mIsSelectingDefaultActivity = true;
                    ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }

        public void onDismiss() {
            notifyOnDismissListener();
            if (ActivityChooserView.this.mProvider != null) {
                ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
            }
        }

        private void notifyOnDismissListener() {
            if (ActivityChooserView.this.mOnDismissListener != null) {
                ActivityChooserView.this.mOnDismissListener.onDismiss();
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class InnerLayout extends LinearLayout {
        private static final int[] TINT_ATTRS = {16842964};

        public InnerLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, TINT_ATTRS);
            setBackgroundDrawable(a.getDrawable(0));
            a.recycle();
        }
    }

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    public ActivityChooserView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActivityChooserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mModelDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.mAdapter.notifyDataSetChanged();
            }

            public void onInvalidated() {
                super.onInvalidated();
                ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
            }
        };
        this.mOnGlobalLayoutListener = new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (!ActivityChooserView.this.isShowingPopup()) {
                    return;
                }
                if (!ActivityChooserView.this.isShown()) {
                    ActivityChooserView.this.getListPopupWindow().dismiss();
                    return;
                }
                ActivityChooserView.this.getListPopupWindow().show();
                if (ActivityChooserView.this.mProvider != null) {
                    ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                }
            }
        };
        this.mInitialActivityCount = 4;
        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.ActivityChooserView, defStyle, 0);
        this.mInitialActivityCount = attributesArray.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable expandActivityOverflowButtonDrawable = attributesArray.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        attributesArray.recycle();
        LayoutInflater.from(getContext()).inflate(R.layout.abc_activity_chooser_view, this, true);
        this.mCallbacks = new Callbacks();
        this.mActivityChooserContent = (LinearLayoutCompat) findViewById(R.id.activity_chooser_view_content);
        this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
        this.mDefaultActivityButton = (FrameLayout) findViewById(R.id.default_activity_button);
        this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
        this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
        this.mDefaultActivityButtonImage = (ImageView) this.mDefaultActivityButton.findViewById(R.id.image);
        FrameLayout expandButton = (FrameLayout) findViewById(R.id.expand_activities_button);
        expandButton.setOnClickListener(this.mCallbacks);
        expandButton.setAccessibilityDelegate(new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                AccessibilityNodeInfoCompat.wrap(info).setCanOpenPopup(true);
            }
        });
        expandButton.setOnTouchListener(new ForwardingListener(expandButton) {
            public ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            /* access modifiers changed from: protected */
            public boolean onForwardingStarted() {
                ActivityChooserView.this.showPopup();
                return true;
            }

            /* access modifiers changed from: protected */
            public boolean onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = expandButton;
        this.mExpandActivityOverflowButtonImage = (ImageView) expandButton.findViewById(R.id.image);
        this.mExpandActivityOverflowButtonImage.setImageDrawable(expandActivityOverflowButtonDrawable);
        this.mAdapter = new ActivityChooserViewAdapter();
        this.mAdapter.registerDataSetObserver(new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.updateAppearance();
            }
        });
        Resources resources = context.getResources();
        this.mListPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    }

    public void setActivityChooserModel(ActivityChooserModel dataModel) {
        this.mAdapter.setDataModel(dataModel);
        if (isShowingPopup()) {
            dismissPopup();
            showPopup();
        }
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable);
    }

    public void setExpandActivityOverflowButtonContentDescription(int resourceId) {
        this.mExpandActivityOverflowButtonImage.setContentDescription(getContext().getString(resourceId));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setProvider(ActionProvider provider) {
        this.mProvider = provider;
    }

    public boolean showPopup() {
        if (isShowingPopup() || !this.mIsAttachedToWindow) {
            return false;
        }
        this.mIsSelectingDefaultActivity = false;
        showPopupUnchecked(this.mInitialActivityCount);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void showPopupUnchecked(int maxActivityCount) {
        boolean defaultActivityButtonShown;
        int maxActivityCountOffset;
        if (this.mAdapter.getDataModel() == null) {
            throw new IllegalStateException("No data model. Did you call #setDataModel?");
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            defaultActivityButtonShown = true;
        } else {
            defaultActivityButtonShown = false;
        }
        int activityCount = this.mAdapter.getActivityCount();
        if (defaultActivityButtonShown) {
            maxActivityCountOffset = 1;
        } else {
            maxActivityCountOffset = 0;
        }
        if (maxActivityCount == Integer.MAX_VALUE || activityCount <= maxActivityCount + maxActivityCountOffset) {
            this.mAdapter.setShowFooterView(false);
            this.mAdapter.setMaxActivityCount(maxActivityCount);
        } else {
            this.mAdapter.setShowFooterView(true);
            this.mAdapter.setMaxActivityCount(maxActivityCount - 1);
        }
        ListPopupWindow popupWindow = getListPopupWindow();
        if (!popupWindow.isShowing()) {
            if (this.mIsSelectingDefaultActivity || !defaultActivityButtonShown) {
                this.mAdapter.setShowDefaultActivity(true, defaultActivityButtonShown);
            } else {
                this.mAdapter.setShowDefaultActivity(false, false);
            }
            popupWindow.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
            popupWindow.show();
            if (this.mProvider != null) {
                this.mProvider.subUiVisibilityChanged(true);
            }
            popupWindow.getListView().setContentDescription(getContext().getString(R.string.abc_activitychooserview_choose_application));
            popupWindow.getListView().setSelector(new ColorDrawable(0));
        }
    }

    public boolean dismissPopup() {
        if (isShowingPopup()) {
            getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
            }
        }
        return true;
    }

    public boolean isShowingPopup() {
        return getListPopupWindow().isShowing();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel dataModel = this.mAdapter.getDataModel();
        if (dataModel != null) {
            dataModel.registerObserver(this.mModelDataSetObserver);
        }
        this.mIsAttachedToWindow = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityChooserModel dataModel = this.mAdapter.getDataModel();
        if (dataModel != null) {
            dataModel.unregisterObserver(this.mModelDataSetObserver);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (isShowingPopup()) {
            dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = this.mActivityChooserContent;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), Ints.MAX_POWER_OF_TWO);
        }
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mActivityChooserContent.layout(0, 0, right - left, bottom - top);
        if (!isShowingPopup()) {
            dismissPopup();
        }
    }

    public ActivityChooserModel getDataModel() {
        return this.mAdapter.getDataModel();
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public void setInitialActivityCount(int itemCount) {
        this.mInitialActivityCount = itemCount;
    }

    public void setDefaultActionButtonContentDescription(int resourceId) {
        this.mDefaultActionButtonContentDescription = resourceId;
    }

    /* access modifiers changed from: 0000 */
    public ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            this.mListPopupWindow = new ListPopupWindow(getContext());
            this.mListPopupWindow.setAdapter(this.mAdapter);
            this.mListPopupWindow.setAnchorView(this);
            this.mListPopupWindow.setModal(true);
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        }
        return this.mListPopupWindow;
    }

    /* access modifiers changed from: 0000 */
    public void updateAppearance() {
        if (this.mAdapter.getCount() > 0) {
            this.mExpandActivityOverflowButton.setEnabled(true);
        } else {
            this.mExpandActivityOverflowButton.setEnabled(false);
        }
        int activityCount = this.mAdapter.getActivityCount();
        int historySize = this.mAdapter.getHistorySize();
        if (activityCount == 1 || (activityCount > 1 && historySize > 0)) {
            this.mDefaultActivityButton.setVisibility(0);
            ResolveInfo activity = this.mAdapter.getDefaultActivity();
            PackageManager packageManager = getContext().getPackageManager();
            this.mDefaultActivityButtonImage.setImageDrawable(activity.loadIcon(packageManager));
            if (this.mDefaultActionButtonContentDescription != 0) {
                CharSequence label = activity.loadLabel(packageManager);
                this.mDefaultActivityButton.setContentDescription(getContext().getString(this.mDefaultActionButtonContentDescription, new Object[]{label}));
            }
        } else {
            this.mDefaultActivityButton.setVisibility(8);
        }
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
        } else {
            this.mActivityChooserContent.setBackgroundDrawable(null);
        }
    }
}
