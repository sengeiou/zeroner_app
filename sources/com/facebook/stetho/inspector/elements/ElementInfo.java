package com.facebook.stetho.inspector.elements;

import com.facebook.stetho.common.ListUtil;
import com.facebook.stetho.common.Util;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class ElementInfo {
    public final List<Object> children;
    public final Object element;
    @Nullable
    public final Object parentElement;

    ElementInfo(Object element2, @Nullable Object parentElement2, List<Object> children2) {
        this.element = Util.throwIfNull(element2);
        this.parentElement = parentElement2;
        this.children = ListUtil.copyToImmutableList(children2);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ElementInfo)) {
            return false;
        }
        ElementInfo other = (ElementInfo) o;
        if (this.element == other.element && this.parentElement == other.parentElement && ListUtil.identityEquals(this.children, other.children)) {
            return true;
        }
        return false;
    }
}
