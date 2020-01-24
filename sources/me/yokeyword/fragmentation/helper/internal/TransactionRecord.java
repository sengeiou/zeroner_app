package me.yokeyword.fragmentation.helper.internal;

import android.view.View;
import java.util.ArrayList;

public final class TransactionRecord {
    public int currentFragmentPopEnter = Integer.MIN_VALUE;
    public int currentFragmentPopExit = Integer.MIN_VALUE;
    public boolean dontAddToBackStack = false;
    public ArrayList<SharedElement> sharedElementList;
    public String tag;
    public int targetFragmentEnter = Integer.MIN_VALUE;
    public int targetFragmentExit = Integer.MIN_VALUE;

    public static class SharedElement {
        public View sharedElement;
        public String sharedName;

        public SharedElement(View sharedElement2, String sharedName2) {
            this.sharedElement = sharedElement2;
            this.sharedName = sharedName2;
        }
    }
}
