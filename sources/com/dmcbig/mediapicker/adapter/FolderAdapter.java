package com.dmcbig.mediapicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.entity.Media;
import java.util.ArrayList;

public class FolderAdapter extends BaseAdapter {
    ArrayList<Folder> folders;
    int lastSelected = 0;
    private Context mContext;
    private LayoutInflater mInflater;

    class ViewHolder {
        ImageView cover;
        ImageView indicator;
        TextView name;
        TextView path;
        TextView size;

        ViewHolder(View view) {
            this.cover = (ImageView) view.findViewById(R.id.cover);
            this.name = (TextView) view.findViewById(R.id.name);
            this.path = (TextView) view.findViewById(R.id.path);
            this.size = (TextView) view.findViewById(R.id.size);
            this.indicator = (ImageView) view.findViewById(R.id.indicator);
            view.setTag(this);
        }
    }

    public FolderAdapter(ArrayList<Folder> folders2, Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.folders = folders2;
        this.mContext = context;
    }

    public int getCount() {
        return this.folders.size();
    }

    public Folder getItem(int position) {
        return (Folder) this.folders.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public void updateAdapter(ArrayList<Folder> list) {
        this.folders = list;
        notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        int i = 0;
        if (view == null) {
            view = this.mInflater.inflate(R.layout.folders_view_item, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Folder folder = getItem(position);
        if (folder.getMedias().size() > 0) {
            Glide.with(this.mContext).load(Uri.parse("file://" + ((Media) folder.getMedias().get(0)).path)).into(holder.cover);
        } else {
            holder.cover.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.default_image));
        }
        holder.name.setText(folder.name);
        holder.size.setText(folder.getMedias().size() + "" + this.mContext.getString(R.string.count_string));
        ImageView imageView = holder.indicator;
        if (this.lastSelected != position) {
            i = 4;
        }
        imageView.setVisibility(i);
        return view;
    }

    public void setSelectIndex(int i) {
        if (this.lastSelected != i) {
            this.lastSelected = i;
            notifyDataSetChanged();
        }
    }

    public ArrayList<Media> getSelectMedias() {
        return ((Folder) this.folders.get(this.lastSelected)).getMedias();
    }
}
