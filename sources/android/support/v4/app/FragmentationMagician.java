package android.support.v4.app;

import android.util.SparseArray;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentationMagician {
    private static boolean sSupportLessThan25dot4;

    static {
        sSupportLessThan25dot4 = false;
        for (Field field : FragmentManagerImpl.class.getDeclaredFields()) {
            if (field.getName().equals("mAvailIndices")) {
                sSupportLessThan25dot4 = true;
                return;
            }
        }
    }

    public static boolean isExecutingActions(FragmentManager fragmentManager) {
        boolean z = false;
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return z;
        }
        try {
            return ((FragmentManagerImpl) fragmentManager).mExecutingActions;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }

    public static void reorderIndices(FragmentManager fragmentManager) {
        if (sSupportLessThan25dot4 && (fragmentManager instanceof FragmentManagerImpl)) {
            try {
                Object object = getValue((FragmentManagerImpl) fragmentManager, "mAvailIndices");
                if (object != null) {
                    ArrayList arrayList = (ArrayList) object;
                    if (arrayList.size() > 1) {
                        Collections.sort(arrayList, Collections.reverseOrder());
                    }
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public static boolean isStateSaved(FragmentManager fragmentManager) {
        boolean z = false;
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return z;
        }
        try {
            return ((FragmentManagerImpl) fragmentManager).mStateSaved;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }

    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.popBackStack();
            }
        });
    }

    public static void popBackStackImmediateAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.popBackStackImmediate();
            }
        });
    }

    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager, final String name, final int flags) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.popBackStack(name, flags);
            }
        });
    }

    public static void executePendingTransactionsAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.executePendingTransactions();
            }
        });
    }

    public static List<Fragment> getActiveFragments(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return Collections.EMPTY_LIST;
        }
        if (sSupportLessThan25dot4) {
            return fragmentManager.getFragments();
        }
        try {
            return getActiveList(((FragmentManagerImpl) fragmentManager).mActive);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return fragmentManager.getFragments();
        }
    }

    private static List<Fragment> getActiveList(SparseArray<Fragment> active) {
        if (active == null) {
            return Collections.EMPTY_LIST;
        }
        int count = active.size();
        ArrayList<Fragment> fragments = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            fragments.add(active.valueAt(i));
        }
        return fragments;
    }

    private static Object getValue(Object object, String fieldName) throws Exception {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    private static void hookStateSaved(FragmentManager fragmentManager, Runnable runnable) {
        if (fragmentManager instanceof FragmentManagerImpl) {
            FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
            if (isStateSaved(fragmentManager)) {
                fragmentManagerImpl.mStateSaved = false;
                runnable.run();
                fragmentManagerImpl.mStateSaved = true;
                return;
            }
            runnable.run();
        }
    }
}
