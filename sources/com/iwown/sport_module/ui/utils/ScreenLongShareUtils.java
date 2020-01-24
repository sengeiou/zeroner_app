package com.iwown.sport_module.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.LruCache;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.util.MyScreenAdapter;
import com.socks.library.KLog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScreenLongShareUtils {
    public static String dir = (Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SHARE_SCREEN_PATH);

    public interface RecycleViewRecCallback {
        void onRecFinished(Bitmap bitmap);
    }

    public static void shareScreenView(Context context, View view) {
        Bitmap bitmap;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            ToastUtils.showShortToast((CharSequence) "Storage Exception");
            return;
        }
        String str = "shotScreen.jpg";
        if (view instanceof ScrollView) {
            bitmap = shotScrollView((ScrollView) view);
        } else if (view instanceof ListView) {
            bitmap = getWholeListViewItemsToBitmap((ListView) view);
        } else if (view instanceof RecyclerView) {
            bitmap = shotRecyclerView1((RecyclerView) view);
        } else {
            bitmap = getViewBitmap(view, 0);
        }
        if (bitmap == null) {
            ToastUtils.showShortToast((CharSequence) "screen bitmap is null");
        } else if (!FileIOUtils.saveBitmap(dir, "shotScreen.jpg", bitmap)) {
            ToastUtils.showShortToast((CharSequence) "save Bitmap error");
        } else {
            shareFile(context, dir + "shotScreen.jpg");
        }
    }

    public static void bitmapScreenView(Context context, View view) {
        Bitmap bitmap;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            ToastUtils.showShortToast((CharSequence) "Storage Exception");
            return;
        }
        String fileName = new DateUtil().getY_M_D_H_M_S() + "shotScreen.jpg";
        if (view instanceof ScrollView) {
            bitmap = shotScrollView((ScrollView) view);
        } else if (view instanceof ListView) {
            bitmap = getWholeListViewItemsToBitmap((ListView) view);
        } else if (view instanceof RecyclerView) {
            bitmap = shotRecyclerView1((RecyclerView) view);
        } else {
            bitmap = getViewBitmap1(view, -1);
        }
        if (bitmap == null) {
            ToastUtils.showShortToast((CharSequence) "screen bitmap is null");
        } else if (!FileIOUtils.saveBitmap(dir, fileName, bitmap)) {
            ToastUtils.showShortToast((CharSequence) "save Bitmap error");
        } else {
            ToastUtils.showShortToast((CharSequence) context.getString(R.string.sport_module_page_ecg_37) + dir + fileName);
            shareFile(context, dir + fileName);
        }
    }

    public static void shareScreenByMultiViews(Context context, View[] views, int[] colors) {
        Bitmap bitmap;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            ToastUtils.showShortToast((CharSequence) "Storage Exception");
            return;
        }
        if (colors.length != views.length) {
            ToastUtils.showShortToast((CharSequence) "colors is length error");
            return;
        }
        String fileName = "shotScreen.jpg";
        int allHeight = 0;
        int allWidth = 0;
        List<Bitmap> bitmaps = new ArrayList<>();
        Paint paint = new Paint();
        int iHeight = 0;
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view instanceof ScrollView) {
                bitmap = shotScrollView((ScrollView) view);
            } else if (view instanceof ListView) {
                bitmap = getWholeListViewItemsToBitmap((ListView) view);
            } else if (view instanceof RecyclerView) {
                bitmap = shotRecyclerView1((RecyclerView) view);
            } else {
                bitmap = getViewBitmap(view, colors[i]);
            }
            if (bitmap != null) {
                bitmaps.add(bitmap);
                allHeight += bitmap.getHeight();
                if (views[i].getWidth() > allWidth) {
                    allWidth = views[i].getWidth();
                }
            }
        }
        int screenHeight = DensityUtil.getScreenHeight(context);
        if (allHeight < screenHeight) {
            allHeight = screenHeight;
        }
        Bitmap bigbitmap = Bitmap.createBitmap(allWidth, allHeight, Config.ARGB_4444);
        Canvas bigcanvas = new Canvas(bigbitmap);
        for (Bitmap bitmap2 : bitmaps) {
            bigcanvas.drawBitmap(bitmap2, 0.0f, (float) iHeight, paint);
            iHeight += bitmap2.getHeight();
        }
        if (!FileIOUtils.saveBitmap(dir, fileName, bigbitmap)) {
            ToastUtils.showShortToast((CharSequence) "save Bitmap error");
        } else {
            shareFile(context, dir + fileName);
        }
    }

    public static void shareFile(Context context, String fileName) {
        File file = new File(fileName);
        Uri imageUri = null;
        if (file != null && file.exists() && file.isFile()) {
            imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", file);
        }
        if (imageUri == null) {
            ToastUtils.showShortToast((CharSequence) "Screen Pic File URI is Null");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", imageUri);
        intent.setType("image/*");
        intent.addFlags(1);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.sport_module_share_to)));
    }

    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.heart_bg);
        }
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        try {
            scrollView.getChildAt(0).draw(canvas);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            scrollView.draw(canvas);
        }
        return bitmap;
    }

    public static Bitmap getViewBitmap(View v, int bg_color) {
        if (v == null || v.getWidth() <= 0 || v.getHeight() <= 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        if (bg_color != 0) {
            canvas.drawColor(bg_color);
        }
        v.draw(canvas);
        return bitmap;
    }

    public static Bitmap getViewBitmap1(View v, int bg_color) {
        if (v == null || v.getWidth() <= 0 || v.getHeight() <= 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(8192, v.getHeight(), Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        if (bg_color != 0) {
            canvas.drawColor(bg_color);
        }
        v.draw(canvas);
        return bitmap;
    }

    public static Bitmap shotRecyclerView(RecyclerView view) {
        Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8);
            for (int i = 0; i < size; i++) {
                ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(0, 0));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }
            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                bigCanvas.drawColor(((ColorDrawable) lBackground).getColor());
            }
            for (int i2 = 0; i2 < size; i2++) {
                Bitmap bitmap = (Bitmap) bitmaCache.get(String.valueOf(i2));
                bigCanvas.drawBitmap(bitmap, 0.0f, (float) iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    public static Bitmap shotRecyclerView1(RecyclerView view) {
        MyScreenAdapter adapter = (MyScreenAdapter) view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getData().size();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8);
            if (size == 0) {
                return null;
            }
            KLog.e("licl", "size-->" + size);
            for (int i = 0; i < size; i++) {
                BaseViewHolder holder = (BaseViewHolder) adapter.createViewHolder(view, adapter.getItemViewType(adapter.getHeaderLayoutCount() + i));
                adapter.startConvert(holder, adapter.getData().get(i));
                holder.itemView.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(0, 0));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                KLog.e("licl", "item_view: " + holder.itemView.getMeasuredWidth() + "/" + holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }
            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Config.ARGB_4444);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                bigCanvas.drawColor(((ColorDrawable) lBackground).getColor());
            }
            for (int i2 = 0; i2 < size; i2++) {
                Bitmap bitmap = (Bitmap) bitmaCache.get(String.valueOf(i2));
                bigCanvas.drawBitmap(bitmap, 0.0f, (float) iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    public static void screenShotRecycleView(RecyclerView mRecyclerView, RecycleViewRecCallback callBack) {
        if (mRecyclerView != null) {
            MyScreenAdapter adapter = (MyScreenAdapter) mRecyclerView.getAdapter();
            final Paint paint = new Paint();
            LruCache lruCache = new LruCache(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8);
            final int oneScreenHeight = mRecyclerView.getMeasuredHeight();
            int shotHeight = 0;
            if (adapter != null && adapter.getData().size() > 0) {
                int headerSize = adapter.getHeaderLayoutCount();
                int dataSize = adapter.getData().size();
                int i = 0;
                while (i < headerSize + dataSize) {
                    BaseViewHolder holder = (BaseViewHolder) adapter.createViewHolder(mRecyclerView, adapter.getItemViewType(i));
                    if (i >= headerSize) {
                        adapter.startConvert(holder, adapter.getData().get(i - headerSize));
                    }
                    holder.itemView.measure(MeasureSpec.makeMeasureSpec(mRecyclerView.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(0, 0));
                    holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                    holder.itemView.setDrawingCacheEnabled(true);
                    holder.itemView.buildDrawingCache();
                    Bitmap drawingCache = holder.itemView.getDrawingCache();
                    if (drawingCache != null) {
                        lruCache.put(String.valueOf(i), drawingCache);
                    }
                    shotHeight += holder.itemView.getHeight();
                    if (shotHeight <= 12000) {
                        i++;
                    } else if (callBack != null) {
                        callBack.onRecFinished(null);
                        return;
                    } else {
                        return;
                    }
                }
                final int footHight = DensityUtil.dip2px(mRecyclerView.getContext(), 42.0f);
                int shotHeight2 = shotHeight + footHight;
                while (mRecyclerView.canScrollVertically(-1)) {
                    mRecyclerView.scrollBy(0, -oneScreenHeight);
                }
                final Bitmap bigBitmap = Bitmap.createBitmap(mRecyclerView.getMeasuredWidth(), shotHeight2, Config.ARGB_8888);
                final Canvas bigCanvas = new Canvas(bigBitmap);
                Drawable lBackground = mRecyclerView.getBackground();
                if (lBackground instanceof ColorDrawable) {
                    bigCanvas.drawColor(((ColorDrawable) lBackground).getColor());
                }
                final int[] drawOffset = {0};
                final Canvas canvas = new Canvas();
                if (shotHeight2 <= oneScreenHeight) {
                    Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Config.ARGB_8888);
                    canvas.setBitmap(bitmap);
                    mRecyclerView.draw(canvas);
                    if (callBack != null) {
                        callBack.onRecFinished(bitmap);
                        return;
                    }
                    return;
                }
                final int finalShotHeight = shotHeight2;
                final RecyclerView recyclerView = mRecyclerView;
                final RecycleViewRecCallback recycleViewRecCallback = callBack;
                mRecyclerView.postDelayed(new Runnable() {
                    public void run() {
                        if (drawOffset[0] + oneScreenHeight < finalShotHeight) {
                            Bitmap bitmap = Bitmap.createBitmap(recyclerView.getWidth(), recyclerView.getHeight(), Config.ARGB_8888);
                            canvas.setBitmap(bitmap);
                            recyclerView.draw(canvas);
                            bigCanvas.drawBitmap(bitmap, 0.0f, (float) drawOffset[0], paint);
                            int[] iArr = drawOffset;
                            iArr[0] = iArr[0] + oneScreenHeight;
                            recyclerView.scrollBy(0, oneScreenHeight);
                            try {
                                bitmap.recycle();
                            } catch (Exception ex) {
                                ThrowableExtension.printStackTrace(ex);
                            }
                            recyclerView.postDelayed(this, 10);
                            return;
                        }
                        int leftHeight = (finalShotHeight - drawOffset[0]) - footHight;
                        recyclerView.scrollBy(0, leftHeight);
                        int top = oneScreenHeight - (finalShotHeight - drawOffset[0]);
                        if (top > 0 && leftHeight > 0) {
                            Bitmap bitmap2 = Bitmap.createBitmap(recyclerView.getWidth(), recyclerView.getHeight(), Config.ARGB_8888);
                            canvas.setBitmap(bitmap2);
                            recyclerView.draw(canvas);
                            Bitmap bitmap3 = Bitmap.createBitmap(bitmap2, 0, top, bitmap2.getWidth(), leftHeight, null, false);
                            bigCanvas.drawBitmap(bitmap3, 0.0f, (float) drawOffset[0], paint);
                            try {
                                bitmap3.recycle();
                            } catch (Exception ex2) {
                                ThrowableExtension.printStackTrace(ex2);
                            }
                        }
                        if (recycleViewRecCallback != null) {
                            recycleViewRecCallback.onRecFinished(bigBitmap);
                        }
                    }
                }, 10);
            }
        }
    }

    public static Bitmap showMultiListView(ListView listview) {
        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        View childView = adapter.getView(0, null, listview);
        childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(0, 0));
        int main_count = itemscount - 1;
        int allitemsheight = 0 + childView.getMeasuredWidth();
        int itemHeight = 0;
        if (main_count > 1) {
            View childView1 = adapter.getView(1, null, listview);
            childView1.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(0, 0));
            allitemsheight += childView1.getMeasuredHeight() * main_count;
            itemHeight = childView1.getMeasuredHeight();
        }
        int screenHeight = DensityUtil.getScreenHeight(listview.getContext()) - itemHeight;
        if (allitemsheight < screenHeight) {
            allitemsheight = screenHeight;
        }
        KLog.e("h " + allitemsheight + " screenHeight " + screenHeight);
        Paint paint = new Paint();
        Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Config.RGB_565);
        Canvas bigcanvas = new Canvas(bigbitmap);
        Bitmap bitmap = Bitmap.createBitmap(listview.getWidth(), listview.getHeight(), Config.ARGB_8888);
        listview.draw(new Canvas(bitmap));
        bigcanvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        KLog.e("bigbitmap H " + bigbitmap.getHeight());
        return bigbitmap;
    }

    public static Bitmap getWholeListViewItemsToBitmap(ListView listView) {
        ListView listview = listView;
        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<>();
        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(0, 0));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }
        Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);
        Paint paint = new Paint();
        int iHeight = 0;
        for (int i2 = 0; i2 < bmps.size(); i2++) {
            Bitmap bmp = (Bitmap) bmps.get(i2);
            bigcanvas.drawBitmap(bmp, 0.0f, (float) iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
        }
        return bigbitmap;
    }

    public static void shotActivityNoStatusBar(Context context, Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        int widths = display.getWidth();
        int heights = display.getHeight();
        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeights, widths, heights - statusBarHeights);
        view.destroyDrawingCache();
        String str = "shotScreen.jpg";
        if (!FileIOUtils.saveBitmap(dir, "shotScreen.jpg", bmp)) {
            ToastUtils.showShortToast((CharSequence) "save Bitmap error");
        } else {
            shareFile(context, dir + "shotScreen.jpg");
        }
    }
}
