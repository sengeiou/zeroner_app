package me.yokeyword.fragmentation.helper.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentationMagician;
import java.util.List;
import me.yokeyword.fragmentation.ISupportFragment;

public class VisibleDelegate {
    private static final String FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE = "fragmentation_compat_replace";
    private static final String FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE = "fragmentation_invisible_when_leave";
    private boolean mFirstCreateViewCompatReplace = true;
    private boolean mFixStatePagerAdapter;
    private Fragment mFragment;
    private Handler mHandler;
    private boolean mInvisibleWhenLeave;
    private boolean mIsFirstVisible = true;
    private boolean mIsSupportVisible;
    private boolean mNeedDispatch = true;
    private Bundle mSaveInstanceState;
    private ISupportFragment mSupportF;

    public VisibleDelegate(ISupportFragment fragment) {
        this.mSupportF = fragment;
        this.mFragment = (Fragment) fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mSaveInstanceState = savedInstanceState;
            if (!this.mFixStatePagerAdapter) {
                this.mInvisibleWhenLeave = savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE);
                this.mFirstCreateViewCompatReplace = savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE);
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE, this.mInvisibleWhenLeave);
        outState.putBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE, this.mFirstCreateViewCompatReplace);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (this.mFirstCreateViewCompatReplace || this.mFragment.getTag() == null || !this.mFragment.getTag().startsWith("android:switcher:")) {
            if (this.mFirstCreateViewCompatReplace) {
                this.mFirstCreateViewCompatReplace = false;
            }
            if (!this.mInvisibleWhenLeave && !this.mFragment.isHidden()) {
                if (!this.mFragment.getUserVisibleHint() && !this.mFixStatePagerAdapter) {
                    return;
                }
                if ((this.mFragment.getParentFragment() != null && isFragmentVisible(this.mFragment.getParentFragment())) || this.mFragment.getParentFragment() == null) {
                    this.mNeedDispatch = false;
                    safeDispatchUserVisibleHint(true);
                }
            }
        }
    }

    public void onResume() {
        if (!this.mIsFirstVisible && !this.mIsSupportVisible && !this.mInvisibleWhenLeave && isFragmentVisible(this.mFragment)) {
            this.mNeedDispatch = false;
            dispatchSupportVisible(true);
        }
    }

    public void onPause() {
        if (!this.mIsSupportVisible || !isFragmentVisible(this.mFragment)) {
            this.mInvisibleWhenLeave = true;
            return;
        }
        this.mNeedDispatch = false;
        this.mInvisibleWhenLeave = false;
        dispatchSupportVisible(false);
    }

    public void onHiddenChanged(boolean hidden) {
        if (!hidden && !this.mFragment.isResumed()) {
            this.mInvisibleWhenLeave = false;
        } else if (hidden) {
            safeDispatchUserVisibleHint(false);
        } else {
            enqueueDispatchVisible();
        }
    }

    public void onDestroyView() {
        this.mIsFirstVisible = true;
        this.mFixStatePagerAdapter = false;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.mFragment.isResumed() || (this.mFragment.isDetached() && isVisibleToUser)) {
            if (!this.mIsSupportVisible && isVisibleToUser) {
                safeDispatchUserVisibleHint(true);
            } else if (this.mIsSupportVisible && !isVisibleToUser) {
                dispatchSupportVisible(false);
            }
        } else if (isVisibleToUser) {
            this.mInvisibleWhenLeave = false;
            this.mFixStatePagerAdapter = true;
        }
    }

    private void safeDispatchUserVisibleHint(boolean visible) {
        if (!this.mIsFirstVisible) {
            dispatchSupportVisible(visible);
        } else if (visible) {
            enqueueDispatchVisible();
        }
    }

    private void enqueueDispatchVisible() {
        getHandler().post(new Runnable() {
            public void run() {
                VisibleDelegate.this.dispatchSupportVisible(true);
            }
        });
    }

    /* access modifiers changed from: private */
    public void dispatchSupportVisible(boolean visible) {
        if (this.mIsSupportVisible == visible) {
            this.mNeedDispatch = true;
            return;
        }
        this.mIsSupportVisible = visible;
        if (!this.mNeedDispatch) {
            this.mNeedDispatch = true;
        } else if (!checkAddState()) {
            FragmentManager fragmentManager = this.mFragment.getChildFragmentManager();
            if (fragmentManager != null) {
                List<Fragment> childFragments = FragmentationMagician.getActiveFragments(fragmentManager);
                if (childFragments != null) {
                    for (Fragment child : childFragments) {
                        if ((child instanceof ISupportFragment) && !child.isHidden() && child.getUserVisibleHint()) {
                            ((ISupportFragment) child).getSupportDelegate().getVisibleDelegate().dispatchSupportVisible(visible);
                        }
                    }
                }
            }
        } else {
            return;
        }
        if (!visible) {
            this.mSupportF.onSupportInvisible();
        } else if (!checkAddState()) {
            this.mSupportF.onSupportVisible();
            if (this.mIsFirstVisible) {
                this.mIsFirstVisible = false;
                this.mSupportF.onLazyInitView(this.mSaveInstanceState);
            }
        }
    }

    private boolean checkAddState() {
        boolean z = false;
        if (this.mFragment.isAdded()) {
            return false;
        }
        if (!this.mIsSupportVisible) {
            z = true;
        }
        this.mIsSupportVisible = z;
        return true;
    }

    private boolean isFragmentVisible(Fragment fragment) {
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }

    public boolean isSupportVisible() {
        return this.mIsSupportVisible;
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }
}
