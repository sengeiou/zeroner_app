package com.chad.library.adapter.base;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class BaseViewHolder extends ViewHolder {
    /* access modifiers changed from: private */
    public BaseQuickAdapter adapter;
    Object associatedObject;
    private final LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet<>();
    @Deprecated
    public View convertView;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds = new LinkedHashSet<>();
    private final HashSet<Integer> nestViews = new HashSet<>();
    private final SparseArray<View> views = new SparseArray<>();

    public HashSet<Integer> getNestViews() {
        return this.nestViews;
    }

    public BaseViewHolder(View view) {
        super(view);
        this.convertView = view;
    }

    /* access modifiers changed from: private */
    public int getClickPosition() {
        return getLayoutPosition() - this.adapter.getHeaderLayoutCount();
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    @Deprecated
    public View getConvertView() {
        return this.convertView;
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        ((TextView) getView(viewId)).setText(value);
        return this;
    }

    public BaseViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        ((TextView) getView(viewId)).setText(strId);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ((ImageView) getView(viewId)).setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        ((TextView) getView(viewId)).setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setAlpha(@IdRes int viewId, float value) {
        if (VERSION.SDK_INT >= 11) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public BaseViewHolder setVisible(@IdRes int viewId, boolean visible) {
        getView(viewId).setVisibility(visible ? 0 : 8);
        return this;
    }

    public BaseViewHolder linkify(@IdRes int viewId) {
        Linkify.addLinks((TextView) getView(viewId), 15);
        return this;
    }

    public BaseViewHolder setTypeface(@IdRes int viewId, Typeface typeface) {
        TextView view = (TextView) getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | 128);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = (TextView) getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | 128);
        }
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int viewId, int progress) {
        ((ProgressBar) getView(viewId)).setProgress(progress);
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = (ProgressBar) getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BaseViewHolder setMax(@IdRes int viewId, int max) {
        ((ProgressBar) getView(viewId)).setMax(max);
        return this;
    }

    public BaseViewHolder setRating(@IdRes int viewId, float rating) {
        ((RatingBar) getView(viewId)).setRating(rating);
        return this;
    }

    public BaseViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = (RatingBar) getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnClickListener(@IdRes int viewId, OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder addOnClickListener(@IdRes int viewId) {
        this.childClickViewIds.add(Integer.valueOf(viewId));
        View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (BaseViewHolder.this.adapter.getOnItemChildClickListener() != null) {
                        BaseViewHolder.this.adapter.getOnItemChildClickListener().onItemChildClick(BaseViewHolder.this.adapter, v, BaseViewHolder.this.getClickPosition());
                    }
                }
            });
        }
        return this;
    }

    public BaseViewHolder setNestView(@IdRes int viewId) {
        addOnClickListener(viewId);
        addOnLongClickListener(viewId);
        this.nestViews.add(Integer.valueOf(viewId));
        return this;
    }

    public BaseViewHolder addOnLongClickListener(@IdRes int viewId) {
        this.itemChildLongClickViewIds.add(Integer.valueOf(viewId));
        View view = getView(viewId);
        if (view != null) {
            if (!view.isLongClickable()) {
                view.setLongClickable(true);
            }
            view.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    return BaseViewHolder.this.adapter.getOnItemChildLongClickListener() != null && BaseViewHolder.this.adapter.getOnItemChildLongClickListener().onItemChildLongClick(BaseViewHolder.this.adapter, v, BaseViewHolder.this.getClickPosition());
                }
            });
        }
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnTouchListener(@IdRes int viewId, OnTouchListener listener) {
        getView(viewId).setOnTouchListener(listener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnLongClickListener(@IdRes int viewId, OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnItemClickListener(@IdRes int viewId, OnItemClickListener listener) {
        ((AdapterView) getView(viewId)).setOnItemClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(@IdRes int viewId, OnItemLongClickListener listener) {
        ((AdapterView) getView(viewId)).setOnItemLongClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemSelectedClickListener(@IdRes int viewId, OnItemSelectedListener listener) {
        ((AdapterView) getView(viewId)).setOnItemSelectedListener(listener);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(@IdRes int viewId, OnCheckedChangeListener listener) {
        ((CompoundButton) getView(viewId)).setOnCheckedChangeListener(listener);
        return this;
    }

    public BaseViewHolder setTag(@IdRes int viewId, Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        getView(viewId).setTag(key, tag);
        return this;
    }

    public BaseViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    public BaseViewHolder setAdapter(@IdRes int viewId, Adapter adapter2) {
        ((AdapterView) getView(viewId)).setAdapter(adapter2);
        return this;
    }

    /* access modifiers changed from: protected */
    public BaseViewHolder setAdapter(BaseQuickAdapter adapter2) {
        this.adapter = adapter2;
        return this;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = (View) this.views.get(viewId);
        if (view != null) {
            return view;
        }
        View view2 = this.itemView.findViewById(viewId);
        this.views.put(viewId, view2);
        return view2;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public void setAssociatedObject(Object associatedObject2) {
        this.associatedObject = associatedObject2;
    }
}
