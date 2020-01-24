package com.iwown.device_module.device_setting.wifi_scale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbsListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater = LayoutInflater.from(this.mContext);
    protected List<T> mList;

    public abstract View getView(int i, View view, ViewGroup viewGroup);

    public AbsListAdapter(Context context, List<T> mList2) {
        this.mContext = context;
        setList(mList2);
    }

    public AbsListAdapter(Context context, T[] mArray) {
        this.mContext = context;
        setList(mArray);
    }

    public int getCount() {
        if (this.mList != null) {
            return this.mList.size();
        }
        return 0;
    }

    public T getItem(int position) {
        if (this.mList != null) {
            return this.mList.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    private void setList(List<T> mList2) {
        if (mList2 == null) {
            mList2 = new ArrayList<>();
        }
        this.mList = mList2;
    }

    public List<T> getList() {
        return this.mList;
    }

    private void setList(T[] mArray) {
        List<T> list = new ArrayList<>();
        if (mArray != null) {
            for (T t : mArray) {
                list.add(t);
            }
        }
        setList(list);
    }

    public void changeData(List<T> mList2) {
        setList(mList2);
        notifyDataSetChanged();
    }

    public void changeData(T[] mArray) {
        setList(mArray);
        notifyDataSetChanged();
    }

    public boolean add(T t) {
        boolean result = this.mList.add(t);
        notifyDataSetChanged();
        return result;
    }

    public boolean addAll(List<T> mList2) {
        if (mList2 == null) {
            return false;
        }
        boolean addAll = this.mList.addAll(mList2);
        notifyDataSetChanged();
        return addAll;
    }

    public boolean set(List<T> mList2) {
        this.mList.clear();
        boolean result = this.mList.addAll(mList2);
        notifyDataSetChanged();
        return result;
    }

    public void insert(int position, T t) {
        this.mList.add(position, t);
        notifyDataSetChanged();
    }

    public boolean remove(T t) {
        boolean removed = this.mList.remove(t);
        notifyDataSetChanged();
        return removed;
    }

    public boolean removeAll(Collection<T> list) {
        boolean removed = this.mList.removeAll(list);
        notifyDataSetChanged();
        return removed;
    }

    public T remove(int position) {
        T t = this.mList.remove(position);
        notifyDataSetChanged();
        return t;
    }

    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void set(int position, T t) {
        this.mList.set(position, t);
        notifyDataSetChanged();
    }

    public void sort(Comparator<T> comparator) {
        Collections.sort(this.mList, comparator);
        notifyDataSetChanged();
    }
}
