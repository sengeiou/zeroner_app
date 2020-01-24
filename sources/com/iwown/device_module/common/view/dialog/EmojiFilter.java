package com.iwown.device_module.common.view.dialog;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.HashSet;
import java.util.Set;

public class EmojiFilter implements InputFilter {
    public static Set<String> filterSet;
    public static Set<Scope> scopeSet;

    private class Scope {
        int end;
        int start;

        private Scope() {
        }

        public boolean equals(Object o) {
            if (o instanceof Scope) {
                Scope scope = (Scope) o;
                if (scope.start == this.start && scope.end == this.end) {
                    return true;
                }
            }
            return super.equals(o);
        }
    }

    static {
        filterSet = null;
        scopeSet = null;
        filterSet = new HashSet();
        scopeSet = new HashSet();
        addUnicodeRangeToSet(filterSet, 128513, 128591);
        addUnicodeRangeToSet(filterSet, 9986, 10160);
        addUnicodeRangeToSet(filterSet, 128640, 128704);
        addUnicodeRangeToSet(filterSet, 9410);
        addUnicodeRangeToSet(filterSet, 127344, 127569);
        addUnicodeRangeToSet(filterSet, 128512, 128566);
        addUnicodeRangeToSet(filterSet, 128641, 128709);
        addUnicodeRangeToSet(filterSet, 127757, 128359);
        addUnicodeRangeToSet(filterSet, 126980);
        addUnicodeRangeToSet(filterSet, 127183);
        addUnicodeRangeToSet(filterSet, 127744, 127757);
        addUnicodeRangeToSet(filterSet, 128507, 128511);
        addUnicodeRangeToSet(filterSet, Opcodes.DIV_FLOAT);
        addUnicodeRangeToSet(filterSet, Opcodes.DIV_DOUBLE);
        addUnicodeRangeToSet(filterSet, 35);
        addUnicodeRangeToSet(filterSet, 8252);
        addUnicodeRangeToSet(filterSet, 8265);
        addUnicodeRangeToSet(filterSet, 8419);
        addUnicodeRangeToSet(filterSet, 8482);
        addUnicodeRangeToSet(filterSet, 8505);
        addUnicodeRangeToSet(filterSet, 8596, 8601);
        addUnicodeRangeToSet(filterSet, 8617, 8618);
        addUnicodeRangeToSet(filterSet, 8986, 8987);
        addUnicodeRangeToSet(filterSet, 9193, 9196);
        addUnicodeRangeToSet(filterSet, 9200);
        addUnicodeRangeToSet(filterSet, 9203);
        addUnicodeRangeToSet(filterSet, 9642, 9643);
        addUnicodeRangeToSet(filterSet, 9723, 9726);
        addUnicodeRangeToSet(filterSet, 9728, 9982);
        addUnicodeRangeToSet(filterSet, 10548, 10549);
        addUnicodeRangeToSet(filterSet, 11013, 11015);
        addUnicodeRangeToSet(filterSet, 11035, 11036);
        addUnicodeRangeToSet(filterSet, 11088);
        addUnicodeRangeToSet(filterSet, 11093);
        addUnicodeRangeToSet(filterSet, 12336);
        addUnicodeRangeToSet(filterSet, 12349);
        addUnicodeRangeToSet(filterSet, 12951);
        addUnicodeRangeToSet(filterSet, 12953);
    }

    private static void addUnicodeRangeToSet(Set<String> set, int start, int end) {
        if (set != null && start <= end) {
            for (int i = start; i <= end; i++) {
                filterSet.add(new String(new int[]{i}, 0, 1));
            }
        }
    }

    private static void addUnicodeRangeToSet(Set<String> set, int code) {
        if (set != null) {
            filterSet.add(new String(new int[]{code}, 0, 1));
        }
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = 0; i < source.length(); i++) {
            Log.e("字符", "" + Integer.toHexString(source.charAt(i)));
        }
        Log.e("字符", "" + source.toString() + " length： " + source.toString().length() + " ；bytes length： " + source.toString().getBytes().length);
        if (!filterSet.contains(source.toString())) {
            return source;
        }
        KLog.i("不支持Emoji表情！");
        return "";
    }
}
