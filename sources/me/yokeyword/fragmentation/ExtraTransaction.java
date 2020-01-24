package me.yokeyword.fragmentation;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import java.util.ArrayList;
import me.yokeyword.fragmentation.helper.internal.TransactionRecord;
import me.yokeyword.fragmentation.helper.internal.TransactionRecord.SharedElement;

public abstract class ExtraTransaction {

    public interface DontAddToBackStackTransaction {
        void add(ISupportFragment iSupportFragment);

        void replace(ISupportFragment iSupportFragment);

        void start(ISupportFragment iSupportFragment);
    }

    static final class ExtraTransactionImpl<T extends ISupportFragment> extends ExtraTransaction implements DontAddToBackStackTransaction {
        private FragmentActivity mActivity;
        private Fragment mFragment;
        private boolean mFromActivity;
        private TransactionRecord mRecord = new TransactionRecord();
        private T mSupportF;
        private TransactionDelegate mTransactionDelegate;

        ExtraTransactionImpl(FragmentActivity activity, T supportF, TransactionDelegate transactionDelegate, boolean fromActivity) {
            this.mActivity = activity;
            this.mSupportF = supportF;
            this.mFragment = (Fragment) supportF;
            this.mTransactionDelegate = transactionDelegate;
            this.mFromActivity = fromActivity;
        }

        public ExtraTransaction setTag(String tag) {
            this.mRecord.tag = tag;
            return this;
        }

        public ExtraTransaction setCustomAnimations(@AnimRes int targetFragmentEnter, @AnimRes int currentFragmentPopExit) {
            this.mRecord.targetFragmentEnter = targetFragmentEnter;
            this.mRecord.currentFragmentPopExit = currentFragmentPopExit;
            this.mRecord.currentFragmentPopEnter = 0;
            this.mRecord.targetFragmentExit = 0;
            return this;
        }

        public ExtraTransaction setCustomAnimations(@AnimRes int targetFragmentEnter, @AnimRes int currentFragmentPopExit, @AnimRes int currentFragmentPopEnter, @AnimRes int targetFragmentExit) {
            this.mRecord.targetFragmentEnter = targetFragmentEnter;
            this.mRecord.currentFragmentPopExit = currentFragmentPopExit;
            this.mRecord.currentFragmentPopEnter = currentFragmentPopEnter;
            this.mRecord.targetFragmentExit = targetFragmentExit;
            return this;
        }

        public ExtraTransaction addSharedElement(View sharedElement, String sharedName) {
            if (this.mRecord.sharedElementList == null) {
                this.mRecord.sharedElementList = new ArrayList<>();
            }
            this.mRecord.sharedElementList.add(new SharedElement(sharedElement, sharedName));
            return this;
        }

        public void loadRootFragment(int containerId, ISupportFragment toFragment) {
            loadRootFragment(containerId, toFragment, true, false);
        }

        public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnim) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.loadRootTransaction(getFragmentManager(), containerId, toFragment, addToBackStack, allowAnim);
        }

        public DontAddToBackStackTransaction dontAddToBackStack() {
            this.mRecord.dontAddToBackStack = true;
            return this;
        }

        public void remove(ISupportFragment fragment, boolean showPreFragment) {
            this.mTransactionDelegate.remove(getFragmentManager(), (Fragment) fragment, showPreFragment);
        }

        public void popTo(String targetFragmentTag, boolean includeTargetFragment) {
            popTo(targetFragmentTag, includeTargetFragment, null, Integer.MAX_VALUE);
        }

        public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
            this.mTransactionDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, getFragmentManager(), popAnim);
        }

        public void popToChild(String targetFragmentTag, boolean includeTargetFragment) {
            popToChild(targetFragmentTag, includeTargetFragment, null, Integer.MAX_VALUE);
        }

        public void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
            if (this.mFromActivity) {
                popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, popAnim);
                return;
            }
            this.mTransactionDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, this.mFragment.getChildFragmentManager(), popAnim);
        }

        public void add(ISupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, toFragment, 0, 0, 2);
        }

        public void start(ISupportFragment toFragment) {
            start(toFragment, 0);
        }

        public void startDontHideSelf(ISupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, toFragment, 0, 0, 2);
        }

        public void start(ISupportFragment toFragment, int launchMode) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, toFragment, 0, launchMode, 0);
        }

        public void startForResult(ISupportFragment toFragment, int requestCode) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, toFragment, requestCode, 0, 1);
        }

        public void startForResultDontHideSelf(ISupportFragment toFragment, int requestCode) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, toFragment, requestCode, 0, 3);
        }

        public void startWithPop(ISupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.startWithPop(getFragmentManager(), this.mSupportF, toFragment);
        }

        public void replace(ISupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, toFragment, 0, 0, 10);
        }

        private FragmentManager getFragmentManager() {
            if (this.mFragment == null) {
                return this.mActivity.getSupportFragmentManager();
            }
            return this.mFragment.getFragmentManager();
        }
    }

    @RequiresApi(22)
    public abstract ExtraTransaction addSharedElement(View view, String str);

    public abstract DontAddToBackStackTransaction dontAddToBackStack();

    public abstract void loadRootFragment(int i, ISupportFragment iSupportFragment);

    public abstract void loadRootFragment(int i, ISupportFragment iSupportFragment, boolean z, boolean z2);

    public abstract void popTo(String str, boolean z);

    public abstract void popTo(String str, boolean z, Runnable runnable, int i);

    public abstract void popToChild(String str, boolean z);

    public abstract void popToChild(String str, boolean z, Runnable runnable, int i);

    public abstract void remove(ISupportFragment iSupportFragment, boolean z);

    public abstract void replace(ISupportFragment iSupportFragment);

    public abstract ExtraTransaction setCustomAnimations(@AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2);

    public abstract ExtraTransaction setCustomAnimations(@AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4);

    public abstract ExtraTransaction setTag(String str);

    public abstract void start(ISupportFragment iSupportFragment);

    public abstract void start(ISupportFragment iSupportFragment, int i);

    public abstract void startDontHideSelf(ISupportFragment iSupportFragment);

    public abstract void startForResult(ISupportFragment iSupportFragment, int i);

    public abstract void startForResultDontHideSelf(ISupportFragment iSupportFragment, int i);

    public abstract void startWithPop(ISupportFragment iSupportFragment);
}
