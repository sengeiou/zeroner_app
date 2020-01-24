package com.iwown.lib_common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;

public class ImageUtils {
    private static ImageUtils mInstance;

    static class Contants {
        public static final int BLUR_VALUE = 20;
        public static final int CORNER_RADIUS = 20;
        public static final float THUMB_SIZE = 0.5f;

        Contants() {
        }
    }

    private ImageUtils() {
    }

    public static ImageUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageUtils();
                }
            }
        }
        return mInstance;
    }

    public void loadImage(Context context, ImageView imageView, String imgUrl, boolean isFade) {
        if (isFade) {
            Glide.with(context).load(imgUrl).crossFade().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        } else {
            Glide.with(context).load(imgUrl).into(imageView);
        }
    }

    public void loadThumbnailImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context).load(imgUrl).crossFade().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.5f).into(imageView);
    }

    public void loadOverrideImage(Context context, ImageView imageView, String imgUrl, int withSize, int heightSize) {
        Glide.with(context).load(imgUrl).crossFade().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL).override(withSize, heightSize).into(imageView);
    }

    public void loadBitmapSync(Context context, String imgUrl, SimpleTarget<Bitmap> target) {
        Glide.with(context).load(imgUrl).asBitmap().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL).into(target);
    }

    public void loadGifImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context).load(imgUrl).asGif().crossFade().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public void loadGifThumbnailImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context).load(imgUrl).asGif().crossFade().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.5f).into(imageView);
    }

    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }
}
