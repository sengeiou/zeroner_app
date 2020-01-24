package com.iwown.device_module.device_add_sport.recyledapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.device_module.R;

/* compiled from: RecycleAdapter */
class AddSportViewHolder extends ViewHolder {
    ImageView img_item_pic;
    TextView tv_itemName;

    public AddSportViewHolder(View itemView) {
        super(itemView);
        this.tv_itemName = (TextView) itemView.findViewById(R.id.sport_name);
        this.img_item_pic = (ImageView) itemView.findViewById(R.id.sport_pic);
    }
}
