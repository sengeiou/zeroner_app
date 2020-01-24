package com.dmcbig.mediapicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.utils.FileUtils;
import com.dmcbig.mediapicker.utils.ScreenUtils;
import java.util.ArrayList;

public class MediaGridAdapter extends Adapter<MyViewHolder> {
    Context context;
    FileUtils fileUtils = new FileUtils();
    /* access modifiers changed from: private */
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    long maxSelect;
    long maxSize;
    ArrayList<Media> medias;
    ArrayList<Media> selectMedias = new ArrayList<>();

    public class MyViewHolder extends ViewHolder {
        public ImageView check_image;
        public RelativeLayout gif_info;
        public View mask_view;
        public ImageView media_image;
        public TextView textView_size;
        public RelativeLayout video_info;

        public MyViewHolder(View view) {
            super(view);
            this.media_image = (ImageView) view.findViewById(R.id.media_image);
            this.check_image = (ImageView) view.findViewById(R.id.check_image);
            this.mask_view = view.findViewById(R.id.mask_view);
            this.video_info = (RelativeLayout) view.findViewById(R.id.video_info);
            this.gif_info = (RelativeLayout) view.findViewById(R.id.gif_info);
            this.textView_size = (TextView) view.findViewById(R.id.textView_size);
            this.itemView.setLayoutParams(new LayoutParams(-1, MediaGridAdapter.this.getItemWidth()));
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Media media, ArrayList<Media> arrayList);
    }

    public MediaGridAdapter(ArrayList<Media> list, Context context2, ArrayList<Media> select, int max, long maxSize2) {
        if (select != null) {
            this.selectMedias = select;
        }
        this.maxSelect = (long) max;
        this.maxSize = maxSize2;
        this.medias = list;
        this.context = context2;
    }

    /* access modifiers changed from: 0000 */
    public int getItemWidth() {
        return (ScreenUtils.getScreenWidth(this.context) / PickerConfig.GridSpanCount) - PickerConfig.GridSpanCount;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_view_item, viewGroup, false));
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        int i = 0;
        final Media media = (Media) this.medias.get(position);
        Glide.with(this.context).load(Uri.parse("file://" + media.path)).into(holder.media_image);
        if (media.mediaType == 3) {
            holder.gif_info.setVisibility(4);
            holder.video_info.setVisibility(0);
            holder.textView_size.setText(this.fileUtils.getSizeByUnit((double) media.size));
        } else {
            holder.video_info.setVisibility(4);
            holder.gif_info.setVisibility(".gif".equalsIgnoreCase(media.extension) ? 0 : 4);
        }
        int isSelect = isSelect(media);
        View view = holder.mask_view;
        if (isSelect < 0) {
            i = 4;
        }
        view.setVisibility(i);
        holder.check_image.setImageDrawable(isSelect >= 0 ? ContextCompat.getDrawable(this.context, R.drawable.btn_selected) : ContextCompat.getDrawable(this.context, R.drawable.btn_unselected));
        holder.media_image.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i = 0;
                int isSelect = MediaGridAdapter.this.isSelect(media);
                if (((long) MediaGridAdapter.this.selectMedias.size()) < MediaGridAdapter.this.maxSelect || isSelect >= 0) {
                    Log.e("no2855", "no2855--> 大小比较: " + media.size + " - " + MediaGridAdapter.this.maxSize);
                    if (media.size > MediaGridAdapter.this.maxSize) {
                        Toast.makeText(MediaGridAdapter.this.context, MediaGridAdapter.this.context.getString(R.string.msg_size_limit) + FileUtils.fileSize(MediaGridAdapter.this.maxSize), 1).show();
                        return;
                    }
                    View view = holder.mask_view;
                    if (isSelect >= 0) {
                        i = 4;
                    }
                    view.setVisibility(i);
                    holder.check_image.setImageDrawable(isSelect >= 0 ? ContextCompat.getDrawable(MediaGridAdapter.this.context, R.drawable.btn_unselected) : ContextCompat.getDrawable(MediaGridAdapter.this.context, R.drawable.btn_selected));
                    MediaGridAdapter.this.setSelectMedias(media);
                    MediaGridAdapter.this.mOnItemClickListener.onItemClick(v, media, MediaGridAdapter.this.selectMedias);
                    return;
                }
                Toast.makeText(MediaGridAdapter.this.context, MediaGridAdapter.this.context.getString(R.string.msg_amount_limit), 0).show();
            }
        });
    }

    public void setSelectMedias(Media media) {
        int index = isSelect(media);
        if (index == -1) {
            this.selectMedias.add(media);
        } else {
            this.selectMedias.remove(index);
        }
    }

    public int isSelect(Media media) {
        int is = -1;
        if (this.selectMedias.size() <= 0) {
            return -1;
        }
        int i = 0;
        while (true) {
            if (i >= this.selectMedias.size()) {
                break;
            } else if (((Media) this.selectMedias.get(i)).path.equals(media.path)) {
                is = i;
                break;
            } else {
                i++;
            }
        }
        return is;
    }

    public void updateSelectAdapter(ArrayList<Media> select) {
        if (select != null) {
            this.selectMedias = select;
        }
        notifyDataSetChanged();
    }

    public void updateAdapter(ArrayList<Media> list) {
        this.medias = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ArrayList<Media> getSelectMedias() {
        return this.selectMedias;
    }

    public int getItemCount() {
        return this.medias.size();
    }
}
