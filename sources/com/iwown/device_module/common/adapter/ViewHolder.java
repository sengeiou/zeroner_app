package com.iwown.device_module.common.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    private View mConvertView;
    private int mPosition;
    private final SparseArray<View> mViews = new SparseArray<>();

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) this.mViews.get(viewId);
        if (view != null) {
            return view;
        }
        View view2 = this.mConvertView.findViewById(viewId);
        this.mViews.put(viewId, view2);
        return view2;
    }

    public ViewHolder setText(int viewId, String string) {
        ((TextView) getView(viewId)).setText(string);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        ((TextView) getView(viewId)).setTextColor(color);
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean isVisible) {
        getView(viewId).setVisibility(isVisible ? 0 : 8);
        return this;
    }

    public String getEditText(int viewId) {
        return ((EditText) getView(viewId)).getText().toString();
    }

    public ViewHolder setImageResource(int viewId, int drawableId) {
        ((ImageView) getView(viewId)).setImageResource(drawableId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ((ImageView) getView(viewId)).setImageBitmap(bm);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }

    public int getPosition() {
        return this.mPosition;
    }
}
