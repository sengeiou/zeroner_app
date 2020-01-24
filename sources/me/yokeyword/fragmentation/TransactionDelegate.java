package me.yokeyword.fragmentation;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentationMagician;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning;
import me.yokeyword.fragmentation.helper.internal.ResultRecord;
import me.yokeyword.fragmentation.helper.internal.TransactionRecord;
import me.yokeyword.fragmentation.helper.internal.TransactionRecord.SharedElement;
import me.yokeyword.fragmentation.queue.Action;
import me.yokeyword.fragmentation.queue.ActionQueue;

class TransactionDelegate {
    static final int DEFAULT_POPTO_ANIM = Integer.MAX_VALUE;
    static final String FRAGMENTATION_ARG_CONTAINER = "fragmentation_arg_container";
    static final String FRAGMENTATION_ARG_CUSTOM_ENTER_ANIM = "fragmentation_arg_custom_enter_anim";
    static final String FRAGMENTATION_ARG_CUSTOM_EXIT_ANIM = "fragmentation_arg_custom_exit_anim";
    static final String FRAGMENTATION_ARG_IS_SHARED_ELEMENT = "fragmentation_arg_is_shared_element";
    static final String FRAGMENTATION_ARG_REPLACE = "fragmentation_arg_replace";
    static final String FRAGMENTATION_ARG_RESULT_RECORD = "fragment_arg_result_record";
    static final String FRAGMENTATION_ARG_ROOT_STATUS = "fragmentation_arg_root_status";
    static final String FRAGMENTATION_STATE_SAVE_ANIMATOR = "fragmentation_state_save_animator";
    static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "fragmentation_state_save_status";
    private static final String FRAGMENTATION_STATE_SAVE_RESULT = "fragmentation_state_save_result";
    private static final String TAG = "Fragmentation";
    static final int TYPE_ADD = 0;
    static final int TYPE_ADD_RESULT = 1;
    static final int TYPE_ADD_RESULT_WITHOUT_HIDE = 3;
    static final int TYPE_ADD_WITHOUT_HIDE = 2;
    static final int TYPE_REPLACE = 10;
    static final int TYPE_REPLACE_DONT_BACK = 11;
    ActionQueue mActionQueue = new ActionQueue(this.mHandler);
    private FragmentActivity mActivity;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public ISupportActivity mSupport;

    private interface Callback {
        void call();
    }

    TransactionDelegate(ISupportActivity support) {
        this.mSupport = support;
        this.mActivity = (FragmentActivity) support;
    }

    /* access modifiers changed from: 0000 */
    public void post(final Runnable runnable) {
        this.mActionQueue.enqueue(new Action() {
            public void run() {
                runnable.run();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void loadRootTransaction(FragmentManager fm, int containerId, ISupportFragment to, boolean addToBackStack, boolean allowAnimation) {
        final int i = containerId;
        final ISupportFragment iSupportFragment = to;
        final FragmentManager fragmentManager = fm;
        final boolean z = addToBackStack;
        final boolean z2 = allowAnimation;
        enqueue(fm, new Action() {
            public void run() {
                TransactionDelegate.this.bindContainerId(i, iSupportFragment);
                String toFragmentTag = iSupportFragment.getClass().getName();
                TransactionRecord transactionRecord = iSupportFragment.getSupportDelegate().mTransactionRecord;
                if (!(transactionRecord == null || transactionRecord.tag == null)) {
                    toFragmentTag = transactionRecord.tag;
                }
                TransactionDelegate.this.start(fragmentManager, null, iSupportFragment, toFragmentTag, !z, null, z2, 10);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void loadMultipleRootTransaction(FragmentManager fm, int containerId, int showPosition, ISupportFragment... tos) {
        final FragmentManager fragmentManager = fm;
        final ISupportFragment[] iSupportFragmentArr = tos;
        final int i = containerId;
        final int i2 = showPosition;
        enqueue(fm, new Action() {
            public void run() {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                for (int i = 0; i < iSupportFragmentArr.length; i++) {
                    Fragment to = (Fragment) iSupportFragmentArr[i];
                    TransactionDelegate.this.getArguments(to).putInt(TransactionDelegate.FRAGMENTATION_ARG_ROOT_STATUS, 1);
                    TransactionDelegate.this.bindContainerId(i, iSupportFragmentArr[i]);
                    ft.add(i, to, to.getClass().getName());
                    if (i != i2) {
                        ft.hide(to);
                    }
                }
                TransactionDelegate.this.supportCommit(fragmentManager, ft);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void dispatchStartTransaction(FragmentManager fm, ISupportFragment from, ISupportFragment to, int requestCode, int launchMode, int type) {
        final FragmentManager fragmentManager = fm;
        final ISupportFragment iSupportFragment = from;
        final ISupportFragment iSupportFragment2 = to;
        final int i = requestCode;
        final int i2 = launchMode;
        final int i3 = type;
        enqueue(fm, new Action() {
            public void run() {
                TransactionDelegate.this.doDispatchStartTransaction(fragmentManager, iSupportFragment, iSupportFragment2, i, i2, i3);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void showHideFragment(final FragmentManager fm, final ISupportFragment showFragment, final ISupportFragment hideFragment) {
        enqueue(fm, new Action() {
            public void run() {
                TransactionDelegate.this.doShowHideFragment(fm, showFragment, hideFragment);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void startWithPop(FragmentManager fm, ISupportFragment from, ISupportFragment to) {
        final ISupportFragment iSupportFragment = from;
        final FragmentManager fragmentManager = fm;
        final ISupportFragment iSupportFragment2 = to;
        enqueue(fm, new Action(2) {
            public void run() {
                ISupportFragment top = TransactionDelegate.this.getTopFragmentForStart(iSupportFragment, fragmentManager);
                if (top == null) {
                    throw new NullPointerException("There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment() first!");
                }
                TransactionDelegate.this.bindContainerId(top.getSupportDelegate().mContainerId, iSupportFragment2);
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager, "popTo()");
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                top.getSupportDelegate().mLockAnim = true;
                if (!FragmentationMagician.isStateSaved(fragmentManager)) {
                    TransactionDelegate.this.mockStartWithPopAnim(SupportHelper.getTopFragment(fragmentManager), iSupportFragment2, top.getSupportDelegate().mAnimHelper.popExitAnim);
                }
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager);
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                TransactionDelegate.this.mHandler.post(new Runnable() {
                    public void run() {
                        FragmentationMagician.reorderIndices(fragmentManager);
                    }
                });
            }
        });
        dispatchStartTransaction(fm, from, to, 0, 0, 0);
    }

    /* access modifiers changed from: 0000 */
    public void remove(FragmentManager fm, Fragment fragment, boolean showPreFragment) {
        final FragmentManager fragmentManager = fm;
        final Fragment fragment2 = fragment;
        final boolean z = showPreFragment;
        enqueue(fm, new Action(1, fm) {
            public void run() {
                FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(8194).remove(fragment2);
                if (z) {
                    ISupportFragment preFragment = SupportHelper.getPreFragment(fragment2);
                    if (preFragment instanceof Fragment) {
                        ft.show((Fragment) preFragment);
                    }
                }
                TransactionDelegate.this.supportCommit(fragmentManager, ft);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void pop(final FragmentManager fm) {
        enqueue(fm, new Action(1, fm) {
            public void run() {
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fm, "pop()");
                FragmentationMagician.popBackStackAllowingStateLoss(fm);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void popQuiet(final FragmentManager fm) {
        enqueue(fm, new Action(2, fm) {
            public void run() {
                TransactionDelegate.this.mSupport.getSupportDelegate().mPopMultipleNoAnim = true;
                FragmentationMagician.popBackStackAllowingStateLoss(fm);
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fm);
                TransactionDelegate.this.mSupport.getSupportDelegate().mPopMultipleNoAnim = false;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, FragmentManager fm, int popAnim) {
        final String str = targetFragmentTag;
        final boolean z = includeTargetFragment;
        final Runnable runnable = afterPopTransactionRunnable;
        final FragmentManager fragmentManager = fm;
        final int i = popAnim;
        enqueue(fm, new Action(2) {
            public void run() {
                TransactionDelegate.this.doPopTo(str, z, runnable, fragmentManager, i);
            }
        });
        if (afterPopTransactionRunnable != null) {
            afterPopTransactionRunnable.run();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean dispatchBackPressedEvent(ISupportFragment activeFragment) {
        if (activeFragment == null || (!activeFragment.onBackPressedSupport() && !dispatchBackPressedEvent((ISupportFragment) ((Fragment) activeFragment).getParentFragment()))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void handleResultRecord(Fragment from) {
        try {
            Bundle args = from.getArguments();
            if (args != null) {
                final ResultRecord resultRecord = (ResultRecord) args.getParcelable(FRAGMENTATION_ARG_RESULT_RECORD);
                if (resultRecord != null) {
                    final ISupportFragment targetFragment = (ISupportFragment) from.getFragmentManager().getFragment(from.getArguments(), FRAGMENTATION_STATE_SAVE_RESULT);
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            targetFragment.onFragmentResult(resultRecord.requestCode, resultRecord.resultCode, resultRecord.resultBundle);
                        }
                    });
                }
            }
        } catch (IllegalStateException e) {
        }
    }

    private void enqueue(FragmentManager fm, Action action) {
        if (fm == null) {
            Log.w(TAG, "FragmentManager is null, skip the action!");
        } else {
            this.mActionQueue.enqueue(action);
        }
    }

    /* access modifiers changed from: private */
    public void doDispatchStartTransaction(FragmentManager fm, ISupportFragment from, ISupportFragment to, int requestCode, int launchMode, int type) {
        checkNotNull(to, "toFragment == null");
        if ((type == 1 || type == 3) && from != null) {
            if (!((Fragment) from).isAdded()) {
                Log.w(TAG, ((Fragment) from).getClass().getSimpleName() + " has not been attached yet! startForResult() converted to start()");
            } else {
                saveRequestCode(fm, (Fragment) from, (Fragment) to, requestCode);
            }
        }
        ISupportFragment from2 = getTopFragmentForStart(from, fm);
        int containerId = getArguments((Fragment) to).getInt(FRAGMENTATION_ARG_CONTAINER, 0);
        if (from2 == null && containerId == 0) {
            Log.e(TAG, "There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment()!");
            return;
        }
        if (from2 != null && containerId == 0) {
            bindContainerId(from2.getSupportDelegate().mContainerId, to);
        }
        String toFragmentTag = to.getClass().getName();
        boolean dontAddToBackStack = false;
        ArrayList<SharedElement> sharedElementList = null;
        TransactionRecord transactionRecord = to.getSupportDelegate().mTransactionRecord;
        if (transactionRecord != null) {
            if (transactionRecord.tag != null) {
                toFragmentTag = transactionRecord.tag;
            }
            dontAddToBackStack = transactionRecord.dontAddToBackStack;
            if (transactionRecord.sharedElementList != null) {
                sharedElementList = transactionRecord.sharedElementList;
                FragmentationMagician.reorderIndices(fm);
            }
        }
        if (!handleLaunchMode(fm, from2, to, toFragmentTag, launchMode)) {
            start(fm, from2, to, toFragmentTag, dontAddToBackStack, sharedElementList, false, type);
        }
    }

    /* access modifiers changed from: private */
    public ISupportFragment getTopFragmentForStart(ISupportFragment from, FragmentManager fm) {
        if (from == null) {
            return SupportHelper.getTopFragment(fm);
        }
        if (from.getSupportDelegate().mContainerId == 0) {
            Fragment fromF = (Fragment) from;
            if (fromF.getTag() != null && !fromF.getTag().startsWith("android:switcher:")) {
                throw new IllegalStateException("Can't find container, please call loadRootFragment() first!");
            }
        }
        return SupportHelper.getTopFragment(fm, from.getSupportDelegate().mContainerId);
    }

    /* access modifiers changed from: private */
    public void start(FragmentManager fm, ISupportFragment from, ISupportFragment to, String toFragmentTag, boolean dontAddToBackStack, ArrayList<SharedElement> sharedElementList, boolean allowRootFragmentAnim, int type) {
        int i;
        FragmentTransaction ft = fm.beginTransaction();
        boolean addMode = type == 0 || type == 1 || type == 2 || type == 3;
        Fragment fromF = (Fragment) from;
        Fragment toF = (Fragment) to;
        Bundle args = getArguments(toF);
        args.putBoolean(FRAGMENTATION_ARG_REPLACE, !addMode);
        if (sharedElementList != null) {
            args.putBoolean(FRAGMENTATION_ARG_IS_SHARED_ELEMENT, true);
            Iterator it = sharedElementList.iterator();
            while (it.hasNext()) {
                SharedElement item = (SharedElement) it.next();
                ft.addSharedElement(item.sharedElement, item.sharedName);
            }
        } else if (addMode) {
            TransactionRecord record = to.getSupportDelegate().mTransactionRecord;
            if (record == null || record.targetFragmentEnter == Integer.MIN_VALUE) {
                ft.setTransition(4097);
            } else {
                ft.setCustomAnimations(record.targetFragmentEnter, record.currentFragmentPopExit, record.currentFragmentPopEnter, record.targetFragmentExit);
                args.putInt(FRAGMENTATION_ARG_CUSTOM_ENTER_ANIM, record.targetFragmentEnter);
                args.putInt(FRAGMENTATION_ARG_CUSTOM_EXIT_ANIM, record.targetFragmentExit);
            }
        } else {
            args.putInt(FRAGMENTATION_ARG_ROOT_STATUS, 1);
        }
        if (from == null) {
            ft.replace(args.getInt(FRAGMENTATION_ARG_CONTAINER), toF, toFragmentTag);
            if (!addMode) {
                ft.setTransition(4097);
                String str = FRAGMENTATION_ARG_ROOT_STATUS;
                if (allowRootFragmentAnim) {
                    i = 2;
                } else {
                    i = 1;
                }
                args.putInt(str, i);
            }
        } else if (addMode) {
            ft.add(from.getSupportDelegate().mContainerId, toF, toFragmentTag);
            if (!(type == 2 || type == 3)) {
                ft.hide(fromF);
            }
        } else {
            ft.replace(from.getSupportDelegate().mContainerId, toF, toFragmentTag);
        }
        if (!dontAddToBackStack && type != 11) {
            ft.addToBackStack(toFragmentTag);
        }
        supportCommit(fm, ft);
    }

    /* access modifiers changed from: private */
    public void doShowHideFragment(FragmentManager fm, ISupportFragment showFragment, ISupportFragment hideFragment) {
        if (showFragment != hideFragment) {
            FragmentTransaction ft = fm.beginTransaction().show((Fragment) showFragment);
            if (hideFragment == null) {
                List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fm);
                if (fragmentList != null) {
                    for (Fragment fragment : fragmentList) {
                        if (!(fragment == null || fragment == showFragment)) {
                            ft.hide(fragment);
                        }
                    }
                }
            } else {
                ft.hide((Fragment) hideFragment);
            }
            supportCommit(fm, ft);
        }
    }

    /* access modifiers changed from: private */
    public void bindContainerId(int containerId, ISupportFragment to) {
        getArguments((Fragment) to).putInt(FRAGMENTATION_ARG_CONTAINER, containerId);
    }

    /* access modifiers changed from: private */
    public Bundle getArguments(Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle != null) {
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        fragment.setArguments(bundle2);
        return bundle2;
    }

    /* access modifiers changed from: private */
    public void supportCommit(FragmentManager fm, FragmentTransaction transaction) {
        handleAfterSaveInStateTransactionException(fm, "commit()");
        transaction.commitAllowingStateLoss();
    }

    private boolean handleLaunchMode(FragmentManager fm, ISupportFragment topFragment, final ISupportFragment to, String toFragmentTag, int launchMode) {
        if (topFragment == null) {
            return false;
        }
        final ISupportFragment stackToFragment = SupportHelper.findStackFragment(to.getClass(), toFragmentTag, fm);
        if (stackToFragment == null) {
            return false;
        }
        if (launchMode == 1) {
            if (to != topFragment && !to.getClass().getName().equals(topFragment.getClass().getName())) {
                return false;
            }
            handleNewBundle(to, stackToFragment);
            return true;
        } else if (launchMode != 2) {
            return false;
        } else {
            popTo(toFragmentTag, false, null, fm, Integer.MAX_VALUE);
            this.mHandler.post(new Runnable() {
                public void run() {
                    TransactionDelegate.this.handleNewBundle(to, stackToFragment);
                }
            });
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void handleNewBundle(ISupportFragment toFragment, ISupportFragment stackToFragment) {
        Bundle argsNewBundle = toFragment.getSupportDelegate().mNewBundle;
        Bundle args = getArguments((Fragment) toFragment);
        if (args.containsKey(FRAGMENTATION_ARG_CONTAINER)) {
            args.remove(FRAGMENTATION_ARG_CONTAINER);
        }
        if (argsNewBundle != null) {
            args.putAll(argsNewBundle);
        }
        stackToFragment.onNewBundle(args);
    }

    private void saveRequestCode(FragmentManager fm, Fragment from, Fragment to, int requestCode) {
        Bundle bundle = getArguments(to);
        ResultRecord resultRecord = new ResultRecord();
        resultRecord.requestCode = requestCode;
        bundle.putParcelable(FRAGMENTATION_ARG_RESULT_RECORD, resultRecord);
        fm.putFragment(bundle, FRAGMENTATION_STATE_SAVE_RESULT, from);
    }

    /* access modifiers changed from: private */
    public void doPopTo(final String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, FragmentManager fm, int popAnim) {
        Animation popAnimation;
        boolean z;
        handleAfterSaveInStateTransactionException(fm, "popTo()");
        Fragment targetFragment = fm.findFragmentByTag(targetFragmentTag);
        if (targetFragment == null) {
            Log.e(TAG, "Pop failure! Can't find FragmentTag:" + targetFragmentTag + " in the FragmentManager's Stack.");
            return;
        }
        int flag = 0;
        if (includeTargetFragment) {
            flag = 1;
            targetFragment = (Fragment) SupportHelper.getPreFragment(targetFragment);
        }
        ISupportFragment fromFragment = SupportHelper.getTopFragment(fm);
        if (afterPopTransactionRunnable == null && popAnim == Integer.MAX_VALUE) {
            popAnimation = fromFragment.getSupportDelegate().mAnimHelper.exitAnim;
        } else if (popAnim == Integer.MAX_VALUE) {
            popAnimation = new Animation() {
            };
            popAnimation.setDuration(fromFragment.getSupportDelegate().mAnimHelper.exitAnim.getDuration());
        } else if (popAnim == 0) {
            popAnimation = new Animation() {
            };
        } else {
            popAnimation = AnimationUtils.loadAnimation(this.mActivity, popAnim);
        }
        final int finalFlag = flag;
        final FragmentManager finalFragmentManager = fm;
        ISupportFragment iSupportFragment = (ISupportFragment) targetFragment;
        if (afterPopTransactionRunnable != null) {
            z = true;
        } else {
            z = false;
        }
        mockPopAnim(fm, fromFragment, iSupportFragment, popAnimation, z, new Callback() {
            public void call() {
                TransactionDelegate.this.popToFix(targetFragmentTag, finalFlag, finalFragmentManager);
            }
        });
    }

    /* access modifiers changed from: private */
    public void popToFix(String fragmentTag, int flag, final FragmentManager fm) {
        if (FragmentationMagician.getActiveFragments(fm) != null) {
            this.mSupport.getSupportDelegate().mPopMultipleNoAnim = true;
            FragmentationMagician.popBackStackAllowingStateLoss(fm, fragmentTag, flag);
            FragmentationMagician.executePendingTransactionsAllowingStateLoss(fm);
            this.mSupport.getSupportDelegate().mPopMultipleNoAnim = false;
            this.mHandler.post(new Runnable() {
                public void run() {
                    FragmentationMagician.reorderIndices(fm);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void mockStartWithPopAnim(ISupportFragment from, ISupportFragment to, Animation exitAnim) {
        Fragment fromF = (Fragment) from;
        final ViewGroup container = findContainerById(fromF, from.getSupportDelegate().mContainerId);
        if (container != null) {
            final View fromView = fromF.getView();
            if (fromView != null) {
                container.removeViewInLayout(fromView);
                final ViewGroup mock = addMockView(fromView, container);
                final Animation animation = exitAnim;
                to.getSupportDelegate().mEnterAnimListener = new EnterAnimListener() {
                    public void onEnterAnimStart() {
                        fromView.startAnimation(animation);
                        TransactionDelegate.this.mHandler.postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    mock.removeViewInLayout(fromView);
                                    container.removeViewInLayout(mock);
                                } catch (Exception e) {
                                }
                            }
                        }, animation.getDuration());
                    }
                };
            }
        }
    }

    private void mockPopAnim(FragmentManager fm, ISupportFragment from, ISupportFragment targetF, Animation exitAnim, boolean afterRunnable, Callback cb) {
        Fragment fromF = (Fragment) from;
        View fromView = fromF.getView();
        if (from != targetF && !FragmentationMagician.isStateSaved(fm) && fromView != null) {
            ViewGroup container = findContainerById(fromF, from.getSupportDelegate().mContainerId);
            if (container != null) {
                Fragment preF = (Fragment) SupportHelper.getPreFragment(fromF);
                ViewGroup preViewGroup = null;
                from.getSupportDelegate().mLockAnim = true;
                if (VERSION.SDK_INT < 21 && preF != targetF && preF != null && (preF.getView() instanceof ViewGroup)) {
                    preViewGroup = (ViewGroup) preF.getView();
                }
                if (preViewGroup != null) {
                    hideChildView(preViewGroup);
                    container.removeViewInLayout(fromView);
                    preViewGroup.addView(fromView);
                    if (cb != null) {
                        cb.call();
                    }
                    preViewGroup.removeViewInLayout(fromView);
                    handleMock(exitAnim, null, fromView, container, afterRunnable);
                    return;
                }
                container.removeViewInLayout(fromView);
                handleMock(exitAnim, cb, fromView, container, afterRunnable);
            }
        } else if (cb != null) {
            cb.call();
        }
    }

    private void handleMock(final Animation exitAnim, Callback cb, final View fromView, final ViewGroup container, boolean afterRunnable) {
        final ViewGroup mock = addMockView(fromView, container);
        if (cb != null) {
            cb.call();
        }
        long delay = 0;
        if (afterRunnable) {
            delay = 120;
        }
        exitAnim.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mock.setVisibility(4);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                fromView.startAnimation(exitAnim);
            }
        }, delay);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                try {
                    mock.removeViewInLayout(fromView);
                    container.removeViewInLayout(mock);
                } catch (Exception e) {
                }
            }
        }, exitAnim.getDuration() + delay);
    }

    @NonNull
    private ViewGroup addMockView(View fromView, ViewGroup container) {
        ViewGroup mock = new ViewGroup(this.mActivity) {
            /* access modifiers changed from: protected */
            public void onLayout(boolean changed, int l, int t, int r, int b) {
            }
        };
        mock.addView(fromView);
        container.addView(mock);
        return mock;
    }

    private ViewGroup findContainerById(Fragment fragment, int containerId) {
        View container;
        if (fragment.getView() == null) {
            return null;
        }
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment == null) {
            container = this.mActivity.findViewById(containerId);
        } else if (parentFragment.getView() != null) {
            container = parentFragment.getView().findViewById(containerId);
        } else {
            container = findContainerById(parentFragment, containerId);
        }
        if (container instanceof ViewGroup) {
            return (ViewGroup) container;
        }
        return null;
    }

    private void hideChildView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            viewGroup.getChildAt(i).setVisibility(8);
        }
    }

    private static <T> void checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
    }

    /* access modifiers changed from: private */
    public void handleAfterSaveInStateTransactionException(FragmentManager fm, String action) {
        if (FragmentationMagician.isStateSaved(fm)) {
            AfterSaveStateTransactionWarning e = new AfterSaveStateTransactionWarning(action);
            if (Fragmentation.getDefault().getHandler() != null) {
                Fragmentation.getDefault().getHandler().onException(e);
            }
        }
    }
}
