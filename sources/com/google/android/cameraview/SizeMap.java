package com.google.android.cameraview;

import android.support.v4.util.ArrayMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class SizeMap {
    private final ArrayMap<AspectRatio, SortedSet<Size>> mRatios = new ArrayMap<>();

    SizeMap() {
    }

    public boolean add(Size size) {
        for (AspectRatio ratio : this.mRatios.keySet()) {
            if (ratio.matches(size)) {
                SortedSet<Size> sizes = (SortedSet) this.mRatios.get(ratio);
                if (sizes.contains(size)) {
                    return false;
                }
                sizes.add(size);
                return true;
            }
        }
        SortedSet<Size> sizes2 = new TreeSet<>();
        sizes2.add(size);
        this.mRatios.put(AspectRatio.of(size.getWidth(), size.getHeight()), sizes2);
        return true;
    }

    public void remove(AspectRatio ratio) {
        this.mRatios.remove(ratio);
    }

    /* access modifiers changed from: 0000 */
    public Set<AspectRatio> ratios() {
        return this.mRatios.keySet();
    }

    /* access modifiers changed from: 0000 */
    public SortedSet<Size> sizes(AspectRatio ratio) {
        return (SortedSet) this.mRatios.get(ratio);
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        this.mRatios.clear();
    }

    /* access modifiers changed from: 0000 */
    public boolean isEmpty() {
        return this.mRatios.isEmpty();
    }
}
