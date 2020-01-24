package me.yokeyword.fragmentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentationMagician;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.List;

public class SupportHelper {
    private static final long SHOW_SPACE = 200;

    private SupportHelper() {
    }

    public static void showSoftInput(final View view) {
        if (view != null && view.getContext() != null) {
            final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
            view.requestFocus();
            view.postDelayed(new Runnable() {
                public void run() {
                    imm.showSoftInput(view, 2);
                }
            }, SHOW_SPACE);
        }
    }

    public static void hideSoftInput(View view) {
        if (view != null && view.getContext() != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showFragmentStackHierarchyView(ISupportActivity support) {
        support.getSupportDelegate().showFragmentStackHierarchyView();
    }

    public static void logFragmentStackHierarchy(ISupportActivity support, String TAG) {
        support.getSupportDelegate().logFragmentStackHierarchy(TAG);
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager) {
        return getTopFragment(fragmentManager, 0);
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager, int containerId) {
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
        if (fragmentList == null) {
            return null;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = (Fragment) fragmentList.get(i);
            if (fragment instanceof ISupportFragment) {
                ISupportFragment iFragment = (ISupportFragment) fragment;
                if (containerId == 0 || containerId == iFragment.getSupportDelegate().mContainerId) {
                    return iFragment;
                }
            }
        }
        return null;
    }

    public static ISupportFragment getPreFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager == null) {
            return null;
        }
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
        if (fragmentList == null) {
            return null;
        }
        for (int i = fragmentList.indexOf(fragment) - 1; i >= 0; i--) {
            Fragment preFragment = (Fragment) fragmentList.get(i);
            if (preFragment instanceof ISupportFragment) {
                return (ISupportFragment) preFragment;
            }
        }
        return null;
    }

    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, Class<T> fragmentClass) {
        return findStackFragment(fragmentClass, null, fragmentManager);
    }

    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, String fragmentTag) {
        return findStackFragment(null, fragmentTag, fragmentManager);
    }

    public static ISupportFragment getActiveFragment(FragmentManager fragmentManager) {
        return getActiveFragment(fragmentManager, null);
    }

    static <T extends ISupportFragment> T findStackFragment(Class<T> fragmentClass, String toFragmentTag, FragmentManager fragmentManager) {
        Fragment fragment = null;
        if (toFragmentTag == null) {
            List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
            if (fragmentList != null) {
                int i = fragmentList.size() - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    Fragment brotherFragment = (Fragment) fragmentList.get(i);
                    if ((brotherFragment instanceof ISupportFragment) && brotherFragment.getClass().getName().equals(fragmentClass.getName())) {
                        fragment = brotherFragment;
                        break;
                    }
                    i--;
                }
            } else {
                return null;
            }
        } else {
            fragment = fragmentManager.findFragmentByTag(toFragmentTag);
            if (fragment == null) {
                return null;
            }
        }
        return (ISupportFragment) fragment;
    }

    private static ISupportFragment getActiveFragment(FragmentManager fragmentManager, ISupportFragment parentFragment) {
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
        if (fragmentList == null) {
            return parentFragment;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = (Fragment) fragmentList.get(i);
            if ((fragment instanceof ISupportFragment) && fragment.isResumed() && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                return getActiveFragment(fragment.getChildFragmentManager(), (ISupportFragment) fragment);
            }
        }
        return parentFragment;
    }
}
