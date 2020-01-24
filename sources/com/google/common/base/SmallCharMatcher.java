package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.BitSet;

@GwtIncompatible("no precomputation is done in GWT")
final class SmallCharMatcher extends FastMatcher {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5d;
    static final int MAX_SIZE = 1023;
    private final boolean containsZero;
    private final long filter;
    private final char[] table;

    private SmallCharMatcher(char[] table2, long filter2, boolean containsZero2, String description) {
        super(description);
        this.table = table2;
        this.filter = filter2;
        this.containsZero = containsZero2;
    }

    static int smear(int hashCode) {
        return C2 * Integer.rotateLeft(C1 * hashCode, 15);
    }

    private boolean checkFilter(int c) {
        return 1 == ((this.filter >> c) & 1);
    }

    @VisibleForTesting
    static int chooseTableSize(int setSize) {
        if (setSize == 1) {
            return 2;
        }
        int tableSize = Integer.highestOneBit(setSize - 1) << 1;
        while (((double) tableSize) * 0.5d < ((double) setSize)) {
            tableSize <<= 1;
        }
        return tableSize;
    }

    static CharMatcher from(BitSet chars, String description) {
        int index;
        long filter2 = 0;
        int size = chars.cardinality();
        boolean containsZero2 = chars.get(0);
        char[] table2 = new char[chooseTableSize(size)];
        int mask = table2.length - 1;
        int c = chars.nextSetBit(0);
        while (c != -1) {
            filter2 |= 1 << c;
            int smear = smear(c);
            while (true) {
                index = smear & mask;
                if (table2[index] == 0) {
                    break;
                }
                smear = index + 1;
            }
            table2[index] = (char) c;
            c = chars.nextSetBit(c + 1);
        }
        return new SmallCharMatcher(table2, filter2, containsZero2, description);
    }

    public boolean matches(char c) {
        if (c == 0) {
            return this.containsZero;
        }
        if (!checkFilter(c)) {
            return false;
        }
        int mask = this.table.length - 1;
        int startingIndex = smear(c) & mask;
        int index = startingIndex;
        while (this.table[index] != 0) {
            if (this.table[index] == c) {
                return true;
            }
            index = (index + 1) & mask;
            if (index == startingIndex) {
                return false;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void setBits(BitSet table2) {
        char[] arr$;
        if (this.containsZero) {
            table2.set(0);
        }
        for (char c : this.table) {
            if (c != 0) {
                table2.set(c);
            }
        }
    }
}
