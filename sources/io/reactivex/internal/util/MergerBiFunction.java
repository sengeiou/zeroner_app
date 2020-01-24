package io.reactivex.internal.util;

import io.reactivex.functions.BiFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class MergerBiFunction<T> implements BiFunction<List<T>, List<T>, List<T>> {
    final Comparator<? super T> comparator;

    public MergerBiFunction(Comparator<? super T> comparator2) {
        this.comparator = comparator2;
    }

    public List<T> apply(List<T> a, List<T> b) throws Exception {
        Object obj;
        Object obj2;
        int n = a.size() + b.size();
        if (n == 0) {
            return new ArrayList();
        }
        List<T> both = new ArrayList<>(n);
        Iterator<T> at = a.iterator();
        Iterator<T> bt = b.iterator();
        if (at.hasNext()) {
            obj = at.next();
        } else {
            obj = null;
        }
        if (bt.hasNext()) {
            obj2 = bt.next();
        } else {
            obj2 = null;
        }
        while (obj != null && obj2 != null) {
            if (this.comparator.compare(obj, obj2) < 0) {
                both.add(obj);
                if (at.hasNext()) {
                    obj = at.next();
                } else {
                    obj = null;
                }
            } else {
                both.add(obj2);
                obj2 = bt.hasNext() ? bt.next() : null;
            }
        }
        if (obj != null) {
            both.add(obj);
            while (at.hasNext()) {
                both.add(at.next());
            }
            return both;
        }
        both.add(obj2);
        while (bt.hasNext()) {
            both.add(bt.next());
        }
        return both;
    }
}
