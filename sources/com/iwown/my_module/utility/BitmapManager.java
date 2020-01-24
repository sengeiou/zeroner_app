package com.iwown.my_module.utility;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;
import com.iwown.my_module.utility.Constants.SERVICE_UTIL;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BitmapManager {
    private static HashMap<String, SoftReference<Bitmap>> cache = new HashMap<>();
    private static Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap());
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private Bitmap defaultBmp;
    private OnImageChanged imageChanged;

    public interface OnImageChanged {
        void onImageChanged();
    }

    public BitmapManager() {
    }

    public BitmapManager(Bitmap def) {
        this.defaultBmp = def;
    }

    public void setOnImageChanged(OnImageChanged imageChanged2) {
        this.imageChanged = imageChanged2;
    }

    public void setDefaultBmp(Bitmap bmp) {
        this.defaultBmp = bmp;
    }

    public void loadBitmap(String url, ImageView imageView) {
        loadBitmap(url, imageView, this.defaultBmp, 0, 0);
    }

    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp2) {
        loadBitmap(url, imageView, defaultBmp2, 0, 0);
    }

    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp2, int width, int height) {
        imageViews.put(imageView, url);
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            if (this.imageChanged != null) {
                this.imageChanged.onImageChanged();
                return;
            }
            return;
        }
        String prefix = "";
        if (url.indexOf("thumb_images") > 0) {
            prefix = "thumb_";
        }
        String filename = prefix + FileUtility.getFileName(url);
        if (new File(imageView.getContext().getFilesDir() + File.separator + filename).exists()) {
            imageView.setImageBitmap(ImageUtility.getBitmap(imageView.getContext(), filename));
            if (this.imageChanged != null) {
                this.imageChanged.onImageChanged();
                return;
            }
            return;
        }
        imageView.setImageBitmap(defaultBmp2);
    }

    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public Bitmap getBitmapFromCache(String url) {
        if (cache.containsKey(url)) {
            return (Bitmap) ((SoftReference) cache.get(url)).get();
        }
        return null;
    }

    public static Bitmap getImage(String urlpath) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlpath).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(SERVICE_UTIL.ELAPSED_TIME);
        if (conn.getResponseCode() == 200) {
            return BitmapFactory.decodeStream(conn.getInputStream());
        }
        return null;
    }
}
