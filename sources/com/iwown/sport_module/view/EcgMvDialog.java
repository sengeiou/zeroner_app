package com.iwown.sport_module.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.lib_common.dialog.NewAbsCustomDialog;
import com.iwown.sport_module.R;

public class EcgMvDialog extends NewAbsCustomDialog implements OnClickListener {
    private TextView cancel;
    private TextView content;
    private ImageView img_10;
    private ImageView img_20;
    private ImageView img_2_5;
    private ImageView img_40;
    private ImageView img_5;
    boolean isCancel;
    Context mContext;
    private OnLinkClickedListener mOnLinkClickedListener;
    private TextView mv_10;
    private TextView mv_20;
    private TextView mv_2_5;
    private TextView mv_40;
    private TextView mv_5;
    private OnItemClickListener onItemClickListener;
    private int position;
    private TextView title;

    public interface OnItemClickListener {
        void item(int i);
    }

    public interface OnLinkClickedListener {
        void onLinkClicked();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        this.position = position2;
    }

    public OnLinkClickedListener getOnLinkClickedListener() {
        return this.mOnLinkClickedListener;
    }

    public EcgMvDialog(Context context) {
        super(context);
    }

    public EcgMvDialog(Context context, boolean isCancel2) {
        super(context);
        this.isCancel = isCancel2;
        this.mContext = context;
        setOutCancel(isCancel2);
    }

    public int getLayoutResID() {
        return R.layout.sport_module_ecg_speed_with_title;
    }

    public void initView() {
        this.mv_2_5 = (TextView) findViewById(R.id.mv_2_5_text1);
        this.mv_5 = (TextView) findViewById(R.id.mv_5_text1);
        this.mv_10 = (TextView) findViewById(R.id.mv_10_text1);
        this.mv_20 = (TextView) findViewById(R.id.mv_20_text1);
        this.mv_40 = (TextView) findViewById(R.id.mv_40_text1);
        this.img_2_5 = (ImageView) findViewById(R.id.img_2_5);
        this.img_5 = (ImageView) findViewById(R.id.img_5);
        this.img_10 = (ImageView) findViewById(R.id.img_10);
        this.img_20 = (ImageView) findViewById(R.id.img_20);
        this.img_40 = (ImageView) findViewById(R.id.img_40);
        if (this.position == 0) {
            this.mv_2_5.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_2_5.setVisibility(0);
        } else if (this.position == 1) {
            this.mv_5.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_5.setVisibility(0);
        } else if (this.position == 2) {
            this.mv_10.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_10.setVisibility(0);
        } else if (this.position == 3) {
            this.mv_20.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_20.setVisibility(0);
        } else if (this.position == 4) {
            this.mv_40.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_40.setVisibility(0);
        }
    }

    public void initData() {
    }

    public void initListener() {
        this.mv_2_5.setOnClickListener(this);
        this.mv_5.setOnClickListener(this);
        this.mv_10.setOnClickListener(this);
        this.mv_20.setOnClickListener(this);
        this.mv_40.setOnClickListener(this);
    }

    public int getWidth() {
        return -2;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 17;
    }

    public void onClick(View v) {
        int id = v.getId();
        this.mv_2_5.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_80_percent_black));
        this.mv_5.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_80_percent_black));
        this.mv_10.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_80_percent_black));
        this.mv_20.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_80_percent_black));
        this.mv_40.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_80_percent_black));
        this.img_2_5.setVisibility(4);
        this.img_5.setVisibility(4);
        this.img_10.setVisibility(4);
        this.img_20.setVisibility(4);
        this.img_40.setVisibility(4);
        if (id == R.id.mv_2_5_text1) {
            this.mv_2_5.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_2_5.setVisibility(0);
            if (this.onItemClickListener != null) {
                this.onItemClickListener.item(0);
            }
        } else if (id == R.id.mv_5_text1) {
            this.mv_5.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_5.setVisibility(0);
            if (this.onItemClickListener != null) {
                this.onItemClickListener.item(1);
            }
        } else if (id == R.id.mv_10_text1) {
            this.mv_10.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_10.setVisibility(0);
            if (this.onItemClickListener != null) {
                this.onItemClickListener.item(2);
            }
        } else if (id == R.id.mv_20_text1) {
            this.mv_20.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_20.setVisibility(0);
            if (this.onItemClickListener != null) {
                this.onItemClickListener.item(3);
            }
        } else if (id == R.id.mv_40_text1) {
            this.mv_40.setTextColor(ContextCompat.getColor(this.mContext, R.color.sport_module_D75930));
            this.img_40.setVisibility(0);
            if (this.onItemClickListener != null) {
                this.onItemClickListener.item(4);
            }
        }
        dismiss();
    }

    public boolean isCancel() {
        return this.isCancel;
    }
}
