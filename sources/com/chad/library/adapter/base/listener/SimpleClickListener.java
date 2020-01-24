package com.chad.library.adapter.base.listener;

import android.os.Build.VERSION;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.Iterator;
import java.util.Set;

public abstract class SimpleClickListener implements OnItemTouchListener {
    public static String TAG = "SimpleClickListener";
    protected BaseQuickAdapter baseQuickAdapter;
    private GestureDetectorCompat mGestureDetector;
    /* access modifiers changed from: private */
    public boolean mIsPrepressed = false;
    /* access modifiers changed from: private */
    public boolean mIsShowPress = false;
    /* access modifiers changed from: private */
    public View mPressedView = null;
    private RecyclerView recyclerView;

    private class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        private RecyclerView recyclerView;

        public boolean onDown(MotionEvent e) {
            SimpleClickListener.this.mIsPrepressed = true;
            SimpleClickListener.this.mPressedView = this.recyclerView.findChildViewUnder(e.getX(), e.getY());
            super.onDown(e);
            return false;
        }

        public void onShowPress(MotionEvent e) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                SimpleClickListener.this.mIsShowPress = true;
            }
            super.onShowPress(e);
        }

        ItemTouchHelperGestureListener(RecyclerView recyclerView2) {
            this.recyclerView = recyclerView2;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                if (this.recyclerView.getScrollState() != 0) {
                    return false;
                }
                View pressedView = SimpleClickListener.this.mPressedView;
                BaseViewHolder vh = (BaseViewHolder) this.recyclerView.getChildViewHolder(pressedView);
                if (SimpleClickListener.this.isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    return false;
                }
                Set<Integer> childClickViewIds = vh.getChildClickViewIds();
                Set<Integer> nestViewIds = vh.getNestViews();
                if (childClickViewIds == null || childClickViewIds.size() <= 0) {
                    SimpleClickListener.this.setPressViewHotSpot(e, pressedView);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    if (childClickViewIds != null && childClickViewIds.size() > 0) {
                        for (Integer childClickViewId : childClickViewIds) {
                            View childView = pressedView.findViewById(childClickViewId.intValue());
                            if (childView != null) {
                                childView.setPressed(false);
                            }
                        }
                    }
                    SimpleClickListener.this.onItemClick(SimpleClickListener.this.baseQuickAdapter, pressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                } else {
                    for (Integer childClickViewId2 : childClickViewIds) {
                        View childView2 = pressedView.findViewById(childClickViewId2.intValue());
                        if (childView2 != null) {
                            if (!SimpleClickListener.this.inRangeOfView(childView2, e) || !childView2.isEnabled()) {
                                childView2.setPressed(false);
                            } else if (nestViewIds != null && nestViewIds.contains(childClickViewId2)) {
                                return false;
                            } else {
                                SimpleClickListener.this.setPressViewHotSpot(e, childView2);
                                childView2.setPressed(true);
                                SimpleClickListener.this.onItemChildClick(SimpleClickListener.this.baseQuickAdapter, childView2, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                                resetPressedView(childView2);
                                return true;
                            }
                        }
                    }
                    SimpleClickListener.this.setPressViewHotSpot(e, pressedView);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    for (Integer childClickViewId3 : childClickViewIds) {
                        View childView3 = pressedView.findViewById(childClickViewId3.intValue());
                        if (childView3 != null) {
                            childView3.setPressed(false);
                        }
                    }
                    SimpleClickListener.this.onItemClick(SimpleClickListener.this.baseQuickAdapter, pressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                }
                resetPressedView(pressedView);
            }
            return true;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }
                    }
                }, 50);
            }
            SimpleClickListener.this.mIsPrepressed = false;
            SimpleClickListener.this.mPressedView = null;
        }

        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (this.recyclerView.getScrollState() == 0 && SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                SimpleClickListener.this.mPressedView.performHapticFeedback(0);
                BaseViewHolder vh = (BaseViewHolder) this.recyclerView.getChildViewHolder(SimpleClickListener.this.mPressedView);
                if (!SimpleClickListener.this.isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    Set<Integer> longClickViewIds = vh.getItemChildLongClickViewIds();
                    Set<Integer> nestViewIds = vh.getNestViews();
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {
                        Iterator it = longClickViewIds.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer longClickViewId = (Integer) it.next();
                            View childView = SimpleClickListener.this.mPressedView.findViewById(longClickViewId.intValue());
                            if (SimpleClickListener.this.inRangeOfView(childView, e) && childView.isEnabled()) {
                                if (nestViewIds == null || !nestViewIds.contains(longClickViewId)) {
                                    SimpleClickListener.this.setPressViewHotSpot(e, childView);
                                    SimpleClickListener.this.onItemChildLongClick(SimpleClickListener.this.baseQuickAdapter, childView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                                    childView.setPressed(true);
                                    SimpleClickListener.this.mIsShowPress = true;
                                    isChildLongClick = true;
                                } else {
                                    isChildLongClick = true;
                                }
                            }
                        }
                    }
                    if (!isChildLongClick) {
                        SimpleClickListener.this.onItemLongClick(SimpleClickListener.this.baseQuickAdapter, SimpleClickListener.this.mPressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                        SimpleClickListener.this.setPressViewHotSpot(e, SimpleClickListener.this.mPressedView);
                        SimpleClickListener.this.mPressedView.setPressed(true);
                        if (longClickViewIds != null) {
                            for (Integer longClickViewId2 : longClickViewIds) {
                                SimpleClickListener.this.mPressedView.findViewById(longClickViewId2.intValue()).setPressed(false);
                            }
                        }
                        SimpleClickListener.this.mIsShowPress = true;
                    }
                }
            }
        }
    }

    public abstract void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public abstract void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public abstract void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public abstract void onItemLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.recyclerView == null) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
            this.mGestureDetector = new GestureDetectorCompat(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        } else if (this.recyclerView != rv) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
            this.mGestureDetector = new GestureDetectorCompat(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        }
        if (!this.mGestureDetector.onTouchEvent(e) && e.getActionMasked() == 1 && this.mIsShowPress) {
            if (this.mPressedView != null) {
                BaseViewHolder vh = (BaseViewHolder) this.recyclerView.getChildViewHolder(this.mPressedView);
                if (vh == null || !isHeaderOrFooterView(vh.getItemViewType())) {
                    this.mPressedView.setPressed(false);
                }
            }
            this.mIsShowPress = false;
            this.mIsPrepressed = false;
        }
        return false;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mGestureDetector.onTouchEvent(e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    /* access modifiers changed from: private */
    public void setPressViewHotSpot(MotionEvent e, View mPressedView2) {
        if (VERSION.SDK_INT >= 21 && mPressedView2 != null && mPressedView2.getBackground() != null) {
            mPressedView2.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView2.getY());
        }
    }

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view == null || !view.isShown()) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < ((float) x) || ev.getRawX() > ((float) (view.getWidth() + x)) || ev.getRawY() < ((float) y) || ev.getRawY() > ((float) (view.getHeight() + y))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isHeaderOrFooterPosition(int position) {
        if (this.baseQuickAdapter == null) {
            if (this.recyclerView == null) {
                return false;
            }
            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
        }
        int type = this.baseQuickAdapter.getItemViewType(position);
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }

    private boolean isHeaderOrFooterView(int type) {
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }
}
