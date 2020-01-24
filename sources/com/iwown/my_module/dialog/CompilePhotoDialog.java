package com.iwown.my_module.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.iwown.my_module.R;

public class CompilePhotoDialog extends AbsCustomDialog implements OnClickListener {
    private Context mContext;
    private OnPhotoConfirmListener onConfirmListener;
    private TextView photoAlbum;
    private TextView photoCancel;
    private TextView photoGraph;

    public interface OnPhotoConfirmListener {
        void OnPhotoConfirm(int i);
    }

    public CompilePhotoDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.photograph) {
            if (this.onConfirmListener != null) {
                this.onConfirmListener.OnPhotoConfirm(0);
            }
            dismiss();
        } else if (i == R.id.photo_album) {
            if (this.onConfirmListener != null) {
                this.onConfirmListener.OnPhotoConfirm(1);
            }
            dismiss();
        } else if (i == R.id.pho_cancel) {
            dismiss();
        }
    }

    public void initView() {
        this.photoGraph = (TextView) findViewById(R.id.photograph);
        this.photoAlbum = (TextView) findViewById(R.id.photo_album);
        this.photoCancel = (TextView) findViewById(R.id.pho_cancel);
        this.photoGraph.setText(this.mContext.getString(R.string.person_center_photofrom_camera));
        this.photoAlbum.setText(this.mContext.getString(R.string.person_center_photofrom_photoalum));
    }

    public void initData() {
    }

    public void initListener() {
        this.photoGraph.setOnClickListener(this);
        this.photoAlbum.setOnClickListener(this);
        this.photoCancel.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.my_module_photos;
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 80;
    }

    public int getBackgroundDrawableResourceId() {
        return super.getBackgroundDrawableResourceId();
    }

    public boolean getCancelable() {
        return true;
    }

    public boolean getCanceledOnTouchOutside() {
        return true;
    }

    public void setOnPhotoConfirmListener(OnPhotoConfirmListener onConfirmListener2) {
        this.onConfirmListener = onConfirmListener2;
    }
}
