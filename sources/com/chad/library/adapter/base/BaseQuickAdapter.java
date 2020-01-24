package com.chad.library.adapter.base;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.animation.SlideInRightAnimation;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseQuickAdapter<T, K extends BaseViewHolder> extends Adapter<K> {
    public static final int ALPHAIN = 1;
    public static final int EMPTY_VIEW = 1365;
    public static final int FOOTER_VIEW = 819;
    public static final int HEADER_VIEW = 273;
    public static final int LOADING_VIEW = 546;
    public static final int SCALEIN = 2;
    public static final int SLIDEIN_BOTTOM = 3;
    public static final int SLIDEIN_LEFT = 4;
    public static final int SLIDEIN_RIGHT = 5;
    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
    private boolean footerViewAsFlow;
    private boolean headerViewAsFlow;
    protected Context mContext;
    private BaseAnimation mCustomAnimation;
    protected List<T> mData;
    private int mDuration;
    private FrameLayout mEmptyLayout;
    /* access modifiers changed from: private */
    public boolean mEnableLoadMoreEndClick;
    private boolean mFirstOnlyEnable;
    private boolean mFootAndEmptyEnable;
    private LinearLayout mFooterLayout;
    private boolean mHeadAndEmptyEnable;
    private LinearLayout mHeaderLayout;
    private Interpolator mInterpolator;
    private boolean mIsUseEmpty;
    private int mLastPosition;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutResId;
    private boolean mLoadMoreEnable;
    /* access modifiers changed from: private */
    public LoadMoreView mLoadMoreView;
    private boolean mLoading;
    private MultiTypeDelegate<T> mMultiTypeDelegate;
    private boolean mNextLoadEnable;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private boolean mOpenAnimationEnable;
    private int mPreLoadNumber;
    private RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public RequestLoadMoreListener mRequestLoadMoreListener;
    private BaseAnimation mSelectAnimation;
    /* access modifiers changed from: private */
    public SpanSizeLookup mSpanSizeLookup;
    private int mStartUpFetchPosition;
    private boolean mUpFetchEnable;
    private UpFetchListener mUpFetchListener;
    private boolean mUpFetching;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int i);
    }

    public interface UpFetchListener {
        void onUpFetch();
    }

    /* access modifiers changed from: protected */
    public abstract void convert(K k, T t);

    /* access modifiers changed from: protected */
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new RuntimeException("please bind recyclerView first!");
        }
    }

    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != null) {
            throw new RuntimeException("Don't bind twice");
        }
        setRecyclerView(recyclerView);
        getRecyclerView().setAdapter(this);
    }

    @Deprecated
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        openLoadMore(requestLoadMoreListener);
    }

    private void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        this.mNextLoadEnable = true;
        this.mLoadMoreEnable = true;
        this.mLoading = false;
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        openLoadMore(requestLoadMoreListener);
        if (getRecyclerView() == null) {
            setRecyclerView(recyclerView);
        }
    }

    public void disableLoadMoreIfNotFullPage() {
        checkNotNull();
        disableLoadMoreIfNotFullPage(getRecyclerView());
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        setEnableLoadMore(false);
        if (recyclerView != null) {
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null) {
                return;
            }
            if (manager instanceof LinearLayoutManager) {
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
                recyclerView.postDelayed(new Runnable() {
                    public void run() {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 != BaseQuickAdapter.this.getItemCount()) {
                            BaseQuickAdapter.this.setEnableLoadMore(true);
                        }
                    }
                }, 50);
            } else if (manager instanceof StaggeredGridLayoutManager) {
                final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
                recyclerView.postDelayed(new Runnable() {
                    public void run() {
                        int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
                        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                        if (BaseQuickAdapter.this.getTheBiggestNumber(positions) + 1 != BaseQuickAdapter.this.getItemCount()) {
                            BaseQuickAdapter.this.setEnableLoadMore(true);
                        }
                    }
                }, 50);
            }
        }
    }

    /* access modifiers changed from: private */
    public int getTheBiggestNumber(int[] numbers) {
        int tmp = -1;
        if (numbers == null || numbers.length == 0) {
            return -1;
        }
        for (int num : numbers) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }

    public void setUpFetchEnable(boolean upFetch) {
        this.mUpFetchEnable = upFetch;
    }

    public boolean isUpFetchEnable() {
        return this.mUpFetchEnable;
    }

    public void setStartUpFetchPosition(int startUpFetchPosition) {
        this.mStartUpFetchPosition = startUpFetchPosition;
    }

    private void autoUpFetch(int positions) {
        if (isUpFetchEnable() && !isUpFetching() && positions <= this.mStartUpFetchPosition && this.mUpFetchListener != null) {
            this.mUpFetchListener.onUpFetch();
        }
    }

    public boolean isUpFetching() {
        return this.mUpFetching;
    }

    public void setUpFetching(boolean upFetching) {
        this.mUpFetching = upFetching;
    }

    public void setUpFetchListener(UpFetchListener upFetchListener) {
        this.mUpFetchListener = upFetchListener;
    }

    public void setNotDoAnimationCount(int count) {
        this.mLastPosition = count;
    }

    public void setLoadMoreView(LoadMoreView loadingView) {
        this.mLoadMoreView = loadingView;
    }

    public int getLoadMoreViewCount() {
        if (this.mRequestLoadMoreListener == null || !this.mLoadMoreEnable) {
            return 0;
        }
        if ((this.mNextLoadEnable || !this.mLoadMoreView.isLoadEndMoreGone()) && this.mData.size() != 0) {
            return 1;
        }
        return 0;
    }

    public int getLoadMoreViewPosition() {
        return getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreEnd(boolean gone) {
        if (getLoadMoreViewCount() != 0) {
            this.mLoading = false;
            this.mNextLoadEnable = false;
            this.mLoadMoreView.setLoadMoreEndGone(gone);
            if (gone) {
                notifyItemRemoved(getLoadMoreViewPosition());
                return;
            }
            this.mLoadMoreView.setLoadMoreStatus(4);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void loadMoreComplete() {
        if (getLoadMoreViewCount() != 0) {
            this.mLoading = false;
            this.mNextLoadEnable = true;
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void loadMoreFail() {
        if (getLoadMoreViewCount() != 0) {
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(3);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void setEnableLoadMore(boolean enable) {
        int oldLoadMoreCount = getLoadMoreViewCount();
        this.mLoadMoreEnable = enable;
        int newLoadMoreCount = getLoadMoreViewCount();
        if (oldLoadMoreCount == 1) {
            if (newLoadMoreCount == 0) {
                notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else if (newLoadMoreCount == 1) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemInserted(getLoadMoreViewPosition());
        }
    }

    public boolean isLoadMoreEnable() {
        return this.mLoadMoreEnable;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public BaseQuickAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.mNextLoadEnable = false;
        this.mLoadMoreEnable = false;
        this.mLoading = false;
        this.mLoadMoreView = new SimpleLoadMoreView();
        this.mEnableLoadMoreEndClick = false;
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mSelectAnimation = new AlphaInAnimation();
        this.mIsUseEmpty = true;
        this.mStartUpFetchPosition = 1;
        this.mPreLoadNumber = 1;
        if (data == null) {
            data = new ArrayList<>();
        }
        this.mData = data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseQuickAdapter(@Nullable List<T> data) {
        this(0, data);
    }

    public BaseQuickAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    public void setNewData(@Nullable List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.mData = data;
        if (this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
            this.mLoadMoreEnable = true;
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(1);
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
    }

    @Deprecated
    public void add(@IntRange(from = 0) int position, @NonNull T item) {
        addData(position, item);
    }

    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        this.mData.add(position, data);
        notifyItemInserted(getHeaderLayoutCount() + position);
        compatibilityDataSizeChanged(1);
    }

    public void addData(@NonNull T data) {
        this.mData.add(data);
        notifyItemInserted(this.mData.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void remove(@IntRange(from = 0) int position) {
        this.mData.remove(position);
        int internalPosition = position + getHeaderLayoutCount();
        notifyItemRemoved(internalPosition);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(internalPosition, this.mData.size() - internalPosition);
    }

    public void setData(@IntRange(from = 0) int index, @NonNull T data) {
        this.mData.set(index, data);
        notifyItemChanged(getHeaderLayoutCount() + index);
    }

    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        this.mData.addAll(position, newData);
        notifyItemRangeInserted(getHeaderLayoutCount() + position, newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(@NonNull Collection<? extends T> newData) {
        this.mData.addAll(newData);
        notifyItemRangeInserted((this.mData.size() - newData.size()) + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    private void compatibilityDataSizeChanged(int size) {
        if ((this.mData == null ? 0 : this.mData.size()) == size) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    public List<T> getData() {
        return this.mData;
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position < this.mData.size()) {
            return this.mData.get(position);
        }
        return null;
    }

    @Deprecated
    public int getHeaderViewsCount() {
        return getHeaderLayoutCount();
    }

    @Deprecated
    public int getFooterViewsCount() {
        return getFooterLayoutCount();
    }

    public int getHeaderLayoutCount() {
        if (this.mHeaderLayout == null || this.mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public int getFooterLayoutCount() {
        if (this.mFooterLayout == null || this.mFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public int getEmptyViewCount() {
        if (this.mEmptyLayout == null || this.mEmptyLayout.getChildCount() == 0 || !this.mIsUseEmpty || this.mData.size() != 0) {
            return 0;
        }
        return 1;
    }

    public int getItemCount() {
        if (getEmptyViewCount() != 1) {
            return getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount() + getLoadMoreViewCount();
        }
        int count = 1;
        if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
            count = 1 + 1;
        }
        if (!this.mFootAndEmptyEnable || getFooterLayoutCount() == 0) {
            return count;
        }
        return count + 1;
    }

    public int getItemViewType(int position) {
        boolean header = true;
        if (getEmptyViewCount() == 1) {
            if (!this.mHeadAndEmptyEnable || getHeaderLayoutCount() == 0) {
                header = false;
            }
            switch (position) {
                case 0:
                    if (!header) {
                        return EMPTY_VIEW;
                    }
                    return HEADER_VIEW;
                case 1:
                    if (header) {
                        return EMPTY_VIEW;
                    }
                    return FOOTER_VIEW;
                case 2:
                    return FOOTER_VIEW;
                default:
                    return EMPTY_VIEW;
            }
        } else {
            int numHeaders = getHeaderLayoutCount();
            if (position < numHeaders) {
                return HEADER_VIEW;
            }
            int adjPosition = position - numHeaders;
            int adapterCount = this.mData.size();
            if (adjPosition < adapterCount) {
                return getDefItemViewType(adjPosition);
            }
            if (adjPosition - adapterCount < getFooterLayoutCount()) {
                return FOOTER_VIEW;
            }
            return LOADING_VIEW;
        }
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        if (this.mMultiTypeDelegate != null) {
            return this.mMultiTypeDelegate.getDefItemViewType(this.mData, position);
        }
        return super.getItemViewType(position);
    }

    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        K baseViewHolder;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        switch (viewType) {
            case HEADER_VIEW /*273*/:
                baseViewHolder = createBaseViewHolder(this.mHeaderLayout);
                break;
            case LOADING_VIEW /*546*/:
                baseViewHolder = getLoadingView(parent);
                break;
            case FOOTER_VIEW /*819*/:
                baseViewHolder = createBaseViewHolder(this.mFooterLayout);
                break;
            case EMPTY_VIEW /*1365*/:
                baseViewHolder = createBaseViewHolder(this.mEmptyLayout);
                break;
            default:
                baseViewHolder = onCreateDefViewHolder(parent, viewType);
                bindViewClickListener(baseViewHolder);
                break;
        }
        baseViewHolder.setAdapter(this);
        return baseViewHolder;
    }

    private K getLoadingView(ViewGroup parent) {
        K holder = createBaseViewHolder(getItemView(this.mLoadMoreView.getLayoutId(), parent));
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 3) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
                if (BaseQuickAdapter.this.mEnableLoadMoreEndClick && BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 4) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
            }
        });
        return holder;
    }

    public void notifyLoadMoreToLoading() {
        if (this.mLoadMoreView.getLoadMoreStatus() != 2) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void enableLoadMoreEndClick(boolean enable) {
        this.mEnableLoadMoreEndClick = enable;
    }

    public void onViewAttachedToWindow(K holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == 1365 || type == 273 || type == 819 || type == 546) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    /* access modifiers changed from: protected */
    public void setFullSpan(ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof LayoutParams) {
            ((LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new android.support.v7.widget.GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = BaseQuickAdapter.this.getItemViewType(position);
                    if (type == 273 && BaseQuickAdapter.this.isHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (type == 819 && BaseQuickAdapter.this.isFooterViewAsFlow()) {
                        return 1;
                    }
                    if (BaseQuickAdapter.this.mSpanSizeLookup != null) {
                        return BaseQuickAdapter.this.isFixedViewType(type) ? gridManager.getSpanCount() : BaseQuickAdapter.this.mSpanSizeLookup.getSpanSize(gridManager, position - BaseQuickAdapter.this.getHeaderLayoutCount());
                    }
                    if (BaseQuickAdapter.this.isFixedViewType(type)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }

    public void setHeaderViewAsFlow(boolean headerViewAsFlow2) {
        this.headerViewAsFlow = headerViewAsFlow2;
    }

    public boolean isHeaderViewAsFlow() {
        return this.headerViewAsFlow;
    }

    public void setFooterViewAsFlow(boolean footerViewAsFlow2) {
        this.footerViewAsFlow = footerViewAsFlow2;
    }

    public boolean isFooterViewAsFlow() {
        return this.footerViewAsFlow;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void onBindViewHolder(K holder, int positions) {
        autoUpFetch(positions);
        autoLoadMore(positions);
        switch (holder.getItemViewType()) {
            case 0:
                convert(holder, this.mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                return;
            case HEADER_VIEW /*273*/:
            case FOOTER_VIEW /*819*/:
            case EMPTY_VIEW /*1365*/:
                return;
            case LOADING_VIEW /*546*/:
                this.mLoadMoreView.convert(holder);
                return;
            default:
                convert(holder, this.mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                return;
        }
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        if (baseViewHolder != null) {
            View view = baseViewHolder.itemView;
            if (view != null) {
                if (getOnItemClickListener() != null) {
                    view.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            BaseQuickAdapter.this.getOnItemClickListener().onItemClick(BaseQuickAdapter.this, v, baseViewHolder.getLayoutPosition() - BaseQuickAdapter.this.getHeaderLayoutCount());
                        }
                    });
                }
                if (getOnItemLongClickListener() != null) {
                    view.setOnLongClickListener(new OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            return BaseQuickAdapter.this.getOnItemLongClickListener().onItemLongClick(BaseQuickAdapter.this, v, baseViewHolder.getLayoutPosition() - BaseQuickAdapter.this.getHeaderLayoutCount());
                        }
                    });
                }
            }
        }
    }

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        this.mMultiTypeDelegate = multiTypeDelegate;
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        int layoutId = this.mLayoutResId;
        if (this.mMultiTypeDelegate != null) {
            layoutId = this.mMultiTypeDelegate.getLayoutId(viewType);
        }
        return createBaseViewHolder(parent, layoutId);
    }

    /* access modifiers changed from: protected */
    public K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createBaseViewHolder(getItemView(layoutResId, parent));
    }

    /* access modifiers changed from: protected */
    public K createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && temp != null) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k = createGenericKInstance(z, view);
        return k != null ? k : new BaseViewHolder(view);
    }

    private K createGenericKInstance(Class z, View view) {
        try {
            if (!z.isMemberClass() || Modifier.isStatic(z.getModifiers())) {
                Constructor constructor = z.getDeclaredConstructor(new Class[]{View.class});
                constructor.setAccessible(true);
                return (BaseViewHolder) constructor.newInstance(new Object[]{view});
            }
            Constructor constructor2 = z.getDeclaredConstructor(new Class[]{getClass(), View.class});
            constructor2.setAccessible(true);
            return (BaseViewHolder) constructor2.newInstance(new Object[]{this, view});
        } catch (NoSuchMethodException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        } catch (IllegalAccessException e2) {
            ThrowableExtension.printStackTrace(e2);
            return null;
        } catch (InstantiationException e3) {
            ThrowableExtension.printStackTrace(e3);
            return null;
        } catch (InvocationTargetException e4) {
            ThrowableExtension.printStackTrace(e4);
            return null;
        }
    }

    private Class getInstancedGenericKClass(Class z) {
        Type[] types;
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            for (Type temp : ((ParameterizedType) type).getActualTypeArguments()) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    public LinearLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    public LinearLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, 1);
    }

    public int addHeaderView(View header, int index, int orientation) {
        if (this.mHeaderLayout == null) {
            this.mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == 1) {
                this.mHeaderLayout.setOrientation(1);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            } else {
                this.mHeaderLayout.setOrientation(0);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        this.mHeaderLayout.addView(header, index);
        if (this.mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    public int setHeaderView(View header) {
        return setHeaderView(header, 0, 1);
    }

    public int setHeaderView(View header, int index) {
        return setHeaderView(header, index, 1);
    }

    public int setHeaderView(View header, int index, int orientation) {
        if (this.mHeaderLayout == null || this.mHeaderLayout.getChildCount() <= index) {
            return addHeaderView(header, index, orientation);
        }
        this.mHeaderLayout.removeViewAt(index);
        this.mHeaderLayout.addView(header, index);
        return index;
    }

    public int addFooterView(View footer) {
        return addFooterView(footer, -1, 1);
    }

    public int addFooterView(View footer, int index) {
        return addFooterView(footer, index, 1);
    }

    public int addFooterView(View footer, int index, int orientation) {
        if (this.mFooterLayout == null) {
            this.mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == 1) {
                this.mFooterLayout.setOrientation(1);
                this.mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            } else {
                this.mFooterLayout.setOrientation(0);
                this.mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        this.mFooterLayout.addView(footer, index);
        if (this.mFooterLayout.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    public int setFooterView(View header) {
        return setFooterView(header, 0, 1);
    }

    public int setFooterView(View header, int index) {
        return setFooterView(header, index, 1);
    }

    public int setFooterView(View header, int index, int orientation) {
        if (this.mFooterLayout == null || this.mFooterLayout.getChildCount() <= index) {
            return addFooterView(header, index, orientation);
        }
        this.mFooterLayout.removeViewAt(index);
        this.mFooterLayout.addView(header, index);
        return index;
    }

    public void removeHeaderView(View header) {
        if (getHeaderLayoutCount() != 0) {
            this.mHeaderLayout.removeView(header);
            if (this.mHeaderLayout.getChildCount() == 0) {
                int position = getHeaderViewPosition();
                if (position != -1) {
                    notifyItemRemoved(position);
                }
            }
        }
    }

    public void removeFooterView(View footer) {
        if (getFooterLayoutCount() != 0) {
            this.mFooterLayout.removeView(footer);
            if (this.mFooterLayout.getChildCount() == 0) {
                int position = getFooterViewPosition();
                if (position != -1) {
                    notifyItemRemoved(position);
                }
            }
        }
    }

    public void removeAllHeaderView() {
        if (getHeaderLayoutCount() != 0) {
            this.mHeaderLayout.removeAllViews();
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    public void removeAllFooterView() {
        if (getFooterLayoutCount() != 0) {
            this.mFooterLayout.removeAllViews();
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    private int getHeaderViewPosition() {
        if (getEmptyViewCount() != 1 || this.mHeadAndEmptyEnable) {
            return 0;
        }
        return -1;
    }

    private int getFooterViewPosition() {
        if (getEmptyViewCount() != 1) {
            return getHeaderLayoutCount() + this.mData.size();
        }
        int position = 1;
        if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
            position = 1 + 1;
        }
        if (this.mFootAndEmptyEnable) {
            return position;
        }
        return -1;
    }

    public void setEmptyView(int layoutResId, ViewGroup viewGroup) {
        setEmptyView(LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false));
    }

    public void setEmptyView(int layoutResId) {
        checkNotNull();
        setEmptyView(layoutResId, getRecyclerView());
    }

    public void setEmptyView(View emptyView) {
        boolean insert = false;
        if (this.mEmptyLayout == null) {
            this.mEmptyLayout = new FrameLayout(emptyView.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -1);
            ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            this.mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        this.mEmptyLayout.removeAllViews();
        this.mEmptyLayout.addView(emptyView);
        this.mIsUseEmpty = true;
        if (insert && getEmptyViewCount() == 1) {
            int position = 0;
            if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position = 0 + 1;
            }
            notifyItemInserted(position);
        }
    }

    public void setHeaderAndEmpty(boolean isHeadAndEmpty) {
        setHeaderFooterEmpty(isHeadAndEmpty, false);
    }

    public void setHeaderFooterEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        this.mHeadAndEmptyEnable = isHeadAndEmpty;
        this.mFootAndEmptyEnable = isFootAndEmpty;
    }

    public void isUseEmpty(boolean isUseEmpty) {
        this.mIsUseEmpty = isUseEmpty;
    }

    public View getEmptyView() {
        return this.mEmptyLayout;
    }

    @Deprecated
    public void setAutoLoadMoreSize(int preLoadNumber) {
        setPreLoadNumber(preLoadNumber);
    }

    public void setPreLoadNumber(int preLoadNumber) {
        if (preLoadNumber > 1) {
            this.mPreLoadNumber = preLoadNumber;
        }
    }

    private void autoLoadMore(int position) {
        if (getLoadMoreViewCount() != 0 && position >= getItemCount() - this.mPreLoadNumber && this.mLoadMoreView.getLoadMoreStatus() == 1) {
            this.mLoadMoreView.setLoadMoreStatus(2);
            if (!this.mLoading) {
                this.mLoading = true;
                if (getRecyclerView() != null) {
                    getRecyclerView().post(new Runnable() {
                        public void run() {
                            BaseQuickAdapter.this.mRequestLoadMoreListener.onLoadMoreRequested();
                        }
                    });
                } else {
                    this.mRequestLoadMoreListener.onLoadMoreRequested();
                }
            }
        }
    }

    private void addAnimation(ViewHolder holder) {
        BaseAnimation animation;
        if (!this.mOpenAnimationEnable) {
            return;
        }
        if (!this.mFirstOnlyEnable || holder.getLayoutPosition() > this.mLastPosition) {
            if (this.mCustomAnimation != null) {
                animation = this.mCustomAnimation;
            } else {
                animation = this.mSelectAnimation;
            }
            for (Animator anim : animation.getAnimators(holder.itemView)) {
                startAnim(anim, holder.getLayoutPosition());
            }
            this.mLastPosition = holder.getLayoutPosition();
        }
    }

    /* access modifiers changed from: protected */
    public void startAnim(Animator anim, int index) {
        anim.setDuration((long) this.mDuration).start();
        anim.setInterpolator(this.mInterpolator);
    }

    /* access modifiers changed from: protected */
    public View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return this.mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public void openLoadAnimation(int animationType) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = null;
        switch (animationType) {
            case 1:
                this.mSelectAnimation = new AlphaInAnimation();
                return;
            case 2:
                this.mSelectAnimation = new ScaleInAnimation();
                return;
            case 3:
                this.mSelectAnimation = new SlideInBottomAnimation();
                return;
            case 4:
                this.mSelectAnimation = new SlideInLeftAnimation();
                return;
            case 5:
                this.mSelectAnimation = new SlideInRightAnimation();
                return;
            default:
                return;
        }
    }

    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    @Nullable
    public View getViewByPosition(int position, @IdRes int viewId) {
        checkNotNull();
        return getViewByPosition(getRecyclerView(), position, viewId);
    }

    @Nullable
    public View getViewByPosition(RecyclerView recyclerView, int position, @IdRes int viewId) {
        if (recyclerView == null) {
            return null;
        }
        BaseViewHolder viewHolder = (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
        if (viewHolder != null) {
            return viewHolder.getView(viewId);
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    private int recursiveExpand(int position, @NonNull List list) {
        int count = 0;
        int pos = (list.size() + position) - 1;
        int i = list.size() - 1;
        while (i >= 0) {
            if (list.get(i) instanceof IExpandable) {
                IExpandable item = (IExpandable) list.get(i);
                if (item.isExpanded() && hasSubItems(item)) {
                    List subList = item.getSubItems();
                    this.mData.addAll(pos + 1, subList);
                    count += recursiveExpand(pos + 1, subList);
                }
            }
            i--;
            pos--;
        }
        return count;
    }

    public int expand(@IntRange(from = 0) int position, boolean animate, boolean shouldNotify) {
        int subItemCount = 0;
        int position2 = position - getHeaderLayoutCount();
        IExpandable expandable = getExpandableItem(position2);
        if (expandable != null) {
            if (!hasSubItems(expandable)) {
                expandable.setExpanded(false);
            } else {
                subItemCount = 0;
                if (!expandable.isExpanded()) {
                    List list = expandable.getSubItems();
                    this.mData.addAll(position2 + 1, list);
                    int subItemCount2 = 0 + recursiveExpand(position2 + 1, list);
                    expandable.setExpanded(true);
                    subItemCount = subItemCount2 + list.size();
                }
                int parentPos = position2 + getHeaderLayoutCount();
                if (shouldNotify) {
                    if (animate) {
                        notifyItemChanged(parentPos);
                        notifyItemRangeInserted(parentPos + 1, subItemCount);
                    } else {
                        notifyDataSetChanged();
                    }
                }
            }
        }
        return subItemCount;
    }

    public int expand(@IntRange(from = 0) int position, boolean animate) {
        return expand(position, animate, true);
    }

    public int expand(@IntRange(from = 0) int position) {
        return expand(position, true, true);
    }

    public int expandAll(int position, boolean animate, boolean notify) {
        int position2 = position - getHeaderLayoutCount();
        T endItem = null;
        if (position2 + 1 < this.mData.size()) {
            endItem = getItem(position2 + 1);
        }
        IExpandable expandable = getExpandableItem(position2);
        if (expandable == null || !hasSubItems(expandable)) {
            return 0;
        }
        int count = expand(getHeaderLayoutCount() + position2, false, false);
        for (int i = position2 + 1; i < this.mData.size(); i++) {
            T item = getItem(i);
            if (item == endItem) {
                break;
            }
            if (isExpandable(item)) {
                count += expand(getHeaderLayoutCount() + i, false, false);
            }
        }
        if (!notify) {
            return count;
        }
        if (animate) {
            notifyItemRangeInserted(getHeaderLayoutCount() + position2 + 1, count);
            return count;
        }
        notifyDataSetChanged();
        return count;
    }

    public int expandAll(int position, boolean init) {
        return expandAll(position, true, !init);
    }

    public void expandAll() {
        for (int i = this.mData.size() - 1; i >= getHeaderLayoutCount() + 0; i--) {
            expandAll(i, false, false);
        }
    }

    private int recursiveCollapse(@IntRange(from = 0) int position) {
        T item = getItem(position);
        if (!isExpandable(item)) {
            return 0;
        }
        IExpandable expandable = (IExpandable) item;
        int subItemCount = 0;
        if (!expandable.isExpanded()) {
            return 0;
        }
        List<T> subItems = expandable.getSubItems();
        for (int i = subItems.size() - 1; i >= 0; i--) {
            T subItem = subItems.get(i);
            int pos = getItemPosition(subItem);
            if (pos >= 0) {
                if (subItem instanceof IExpandable) {
                    subItemCount += recursiveCollapse(pos);
                }
                this.mData.remove(pos);
                subItemCount++;
            }
        }
        return subItemCount;
    }

    public int collapse(@IntRange(from = 0) int position, boolean animate, boolean notify) {
        int position2 = position - getHeaderLayoutCount();
        IExpandable expandable = getExpandableItem(position2);
        if (expandable == null) {
            return 0;
        }
        int subItemCount = recursiveCollapse(position2);
        expandable.setExpanded(false);
        int parentPos = position2 + getHeaderLayoutCount();
        if (!notify) {
            return subItemCount;
        }
        if (animate) {
            notifyItemChanged(parentPos);
            notifyItemRangeRemoved(parentPos + 1, subItemCount);
            return subItemCount;
        }
        notifyDataSetChanged();
        return subItemCount;
    }

    public int collapse(@IntRange(from = 0) int position) {
        return collapse(position, true, true);
    }

    public int collapse(@IntRange(from = 0) int position, boolean animate) {
        return collapse(position, animate, true);
    }

    private int getItemPosition(T item) {
        if (item == null || this.mData == null || this.mData.isEmpty()) {
            return -1;
        }
        return this.mData.indexOf(item);
    }

    private boolean hasSubItems(IExpandable item) {
        if (item == null) {
            return false;
        }
        List list = item.getSubItems();
        if (list == null || list.size() <= 0) {
            return false;
        }
        return true;
    }

    public boolean isExpandable(T item) {
        return item != null && (item instanceof IExpandable);
    }

    private IExpandable getExpandableItem(int position) {
        T item = getItem(position);
        if (isExpandable(item)) {
            return (IExpandable) item;
        }
        return null;
    }

    public int getParentPosition(@NonNull T item) {
        int level;
        int position = getItemPosition(item);
        if (position == -1) {
            return -1;
        }
        if (item instanceof IExpandable) {
            level = ((IExpandable) item).getLevel();
        } else {
            level = Integer.MAX_VALUE;
        }
        if (level == 0) {
            return position;
        }
        if (level == -1) {
            return -1;
        }
        for (int i = position; i >= 0; i--) {
            T temp = this.mData.get(i);
            if (temp instanceof IExpandable) {
                IExpandable expandable = (IExpandable) temp;
                if (expandable.getLevel() >= 0 && expandable.getLevel() < level) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        this.mOnItemChildLongClickListener = listener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return this.mOnItemChildClickListener;
    }

    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return this.mOnItemChildLongClickListener;
    }
}
