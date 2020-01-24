package com.iwown.device_module.device_guide.a_interface;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GifAnimController implements IGuideAnimController {
    private int gif_res;
    private ImageView mImageView;

    public GifAnimController(ImageView imageView) {
        this.mImageView = imageView;
    }

    public void setGifRes(int gif_res2) {
        this.gif_res = gif_res2;
    }

    public void init() {
    }

    public void start() {
        Glide.with(this.mImageView.getContext()).load(Integer.valueOf(this.gif_res)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.mImageView);
    }

    public void stop() {
        Glide.with(this.mImageView.getContext()).load(Integer.valueOf(this.gif_res)).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.mImageView);
    }

    public void cancel() {
        Glide.get(this.mImageView.getContext()).clearMemory();
    }
}
