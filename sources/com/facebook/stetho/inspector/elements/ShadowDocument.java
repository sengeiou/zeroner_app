package com.facebook.stetho.inspector.elements;

import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.common.ListUtil;
import com.facebook.stetho.common.Util;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

public final class ShadowDocument implements DocumentView {
    /* access modifiers changed from: private */
    public final IdentityHashMap<Object, ElementInfo> mElementToInfoMap = new IdentityHashMap<>();
    /* access modifiers changed from: private */
    public boolean mIsUpdating;
    /* access modifiers changed from: private */
    public final Object mRootElement;

    public final class Update implements DocumentView {
        private final Map<Object, ElementInfo> mElementToInfoChangesMap;
        private final Set<Object> mRootElementChangesSet;

        public Update(Map<Object, ElementInfo> elementToInfoChangesMap, Set<Object> rootElementChangesSet) {
            this.mElementToInfoChangesMap = elementToInfoChangesMap;
            this.mRootElementChangesSet = rootElementChangesSet;
        }

        public boolean isEmpty() {
            return this.mElementToInfoChangesMap.isEmpty();
        }

        public boolean isElementChanged(Object element) {
            return this.mElementToInfoChangesMap.containsKey(element);
        }

        public Object getRootElement() {
            return ShadowDocument.this.getRootElement();
        }

        public ElementInfo getElementInfo(Object element) {
            ElementInfo elementInfo = (ElementInfo) this.mElementToInfoChangesMap.get(element);
            return elementInfo != null ? elementInfo : (ElementInfo) ShadowDocument.this.mElementToInfoMap.get(element);
        }

        public void getChangedElements(Accumulator<Object> accumulator) {
            for (Object element : this.mElementToInfoChangesMap.keySet()) {
                accumulator.store(element);
            }
        }

        public void getGarbageElements(Accumulator<Object> accumulator) {
            Object expectedParent;
            Queue<Object> queue = new ArrayDeque<>();
            for (Object element : this.mRootElementChangesSet) {
                ElementInfo newElementInfo = getElementInfo(element);
                if (element != ShadowDocument.this.mRootElement && newElementInfo.parentElement == null) {
                    queue.add(element);
                    queue.add(element);
                }
            }
            while (!queue.isEmpty()) {
                Object element2 = queue.remove();
                Object expectedParent0 = queue.remove();
                if (element2 == expectedParent0) {
                    expectedParent = null;
                } else {
                    expectedParent = expectedParent0;
                }
                if (getElementInfo(element2).parentElement == expectedParent) {
                    accumulator.store(element2);
                    ElementInfo oldElementInfo = ShadowDocument.this.getElementInfo(element2);
                    if (oldElementInfo != null) {
                        int N = oldElementInfo.children.size();
                        for (int i = 0; i < N; i++) {
                            queue.add(oldElementInfo.children.get(i));
                            queue.add(element2);
                        }
                    }
                }
            }
        }

        public void abandon() {
            if (!ShadowDocument.this.mIsUpdating) {
                throw new IllegalStateException();
            }
            ShadowDocument.this.mIsUpdating = false;
        }

        public void commit() {
            if (!ShadowDocument.this.mIsUpdating) {
                throw new IllegalStateException();
            }
            ShadowDocument.this.mElementToInfoMap.putAll(this.mElementToInfoChangesMap);
            for (Object element : this.mRootElementChangesSet) {
                removeGarbageSubTree(ShadowDocument.this.mElementToInfoMap, element);
            }
            ShadowDocument.this.mIsUpdating = false;
        }

        private void removeGarbageSubTree(Map<Object, ElementInfo> elementToInfoMap, Object element) {
            ElementInfo elementInfo = (ElementInfo) elementToInfoMap.get(element);
            if (elementInfo.parentElement == null || !elementToInfoMap.containsKey(elementInfo.parentElement)) {
                elementToInfoMap.remove(element);
                int N = elementInfo.children.size();
                for (int i = 0; i < N; i++) {
                    removeGarbageSubTree(elementToInfoMap, elementInfo.children.get(i));
                }
            }
        }

        private void validateTree(Map<Object, ElementInfo> elementToInfoMap) {
            HashSet<Object> rootElements = new HashSet<>();
            for (Entry<Object, ElementInfo> entry : elementToInfoMap.entrySet()) {
                Object element = entry.getKey();
                ElementInfo elementInfo = (ElementInfo) entry.getValue();
                if (element != elementInfo.element) {
                    throw new IllegalStateException("element != elementInfo.element");
                }
                int i = 0;
                int N = elementInfo.children.size();
                while (i < N) {
                    ElementInfo childElementInfo = (ElementInfo) elementToInfoMap.get(elementInfo.children.get(i));
                    if (childElementInfo == null) {
                        throw new IllegalStateException(String.format("elementInfo.get(elementInfo.children.get(%s)) == null", new Object[]{Integer.valueOf(i)}));
                    } else if (childElementInfo.parentElement != element) {
                        throw new IllegalStateException("childElementInfo.parentElement != element");
                    } else {
                        i++;
                    }
                }
                if (elementInfo.parentElement == null) {
                    rootElements.add(element);
                } else {
                    ElementInfo parentElementInfo = (ElementInfo) elementToInfoMap.get(elementInfo.parentElement);
                    if (parentElementInfo == null) {
                        throw new IllegalStateException("elementToInfoMap.get(elementInfo.parentElementInfo) == NULL");
                    } else if (elementInfo.parentElement != parentElementInfo.element) {
                        throw new IllegalStateException("elementInfo.parentElementInfo != parentElementInfo.parent");
                    } else if (!parentElementInfo.children.contains(element)) {
                        throw new IllegalStateException("parentElementInfo.children.contains(element) == FALSE");
                    }
                }
            }
            if (rootElements.size() != 1) {
                throw new IllegalStateException("elementToInfoMap is a forest, not a tree. rootElements.size() != 1");
            }
        }
    }

    public final class UpdateBuilder {
        private HashSet<Object> mCachedNotNewChildrenSet;
        private final Map<Object, ElementInfo> mElementToInfoChangesMap = new LinkedHashMap();
        private final HashSet<Object> mRootElementChanges = new HashSet<>();

        public UpdateBuilder() {
        }

        public void setElementChildren(Object element, List<Object> children) {
            ElementInfo newElementInfo;
            ElementInfo changesElementInfo = (ElementInfo) this.mElementToInfoChangesMap.get(element);
            if (changesElementInfo == null || !ListUtil.identityEquals(children, changesElementInfo.children)) {
                ElementInfo oldElementInfo = (ElementInfo) ShadowDocument.this.mElementToInfoMap.get(element);
                if (changesElementInfo != null || oldElementInfo == null || !ListUtil.identityEquals(children, oldElementInfo.children)) {
                    if (changesElementInfo == null || oldElementInfo == null || oldElementInfo.parentElement != changesElementInfo.parentElement || !ListUtil.identityEquals(children, oldElementInfo.children)) {
                        Object parentElement = changesElementInfo != null ? changesElementInfo.parentElement : oldElementInfo != null ? oldElementInfo.parentElement : null;
                        newElementInfo = new ElementInfo(element, parentElement, children);
                        this.mElementToInfoChangesMap.put(element, newElementInfo);
                    } else {
                        newElementInfo = (ElementInfo) ShadowDocument.this.mElementToInfoMap.get(element);
                        this.mElementToInfoChangesMap.remove(element);
                    }
                    HashSet<Object> notNewChildrenSet = acquireNotNewChildrenHashSet();
                    if (!(oldElementInfo == null || oldElementInfo.children == newElementInfo.children)) {
                        int N = oldElementInfo.children.size();
                        for (int i = 0; i < N; i++) {
                            notNewChildrenSet.add(oldElementInfo.children.get(i));
                        }
                    }
                    if (!(changesElementInfo == null || changesElementInfo.children == newElementInfo.children)) {
                        int N2 = changesElementInfo.children.size();
                        for (int i2 = 0; i2 < N2; i2++) {
                            notNewChildrenSet.add(changesElementInfo.children.get(i2));
                        }
                    }
                    int N3 = newElementInfo.children.size();
                    for (int i3 = 0; i3 < N3; i3++) {
                        Object childElement = newElementInfo.children.get(i3);
                        setElementParent(childElement, element);
                        notNewChildrenSet.remove(childElement);
                    }
                    Iterator it = notNewChildrenSet.iterator();
                    while (it.hasNext()) {
                        Object childElement2 = it.next();
                        ElementInfo childChangesElementInfo = (ElementInfo) this.mElementToInfoChangesMap.get(childElement2);
                        if (childChangesElementInfo == null || childChangesElementInfo.parentElement == element) {
                            ElementInfo oldChangesElementInfo = (ElementInfo) ShadowDocument.this.mElementToInfoMap.get(childElement2);
                            if (oldChangesElementInfo != null && oldChangesElementInfo.parentElement == element) {
                                setElementParent(childElement2, null);
                            }
                        }
                    }
                    releaseNotNewChildrenHashSet(notNewChildrenSet);
                }
            }
        }

        private void setElementParent(Object element, Object parentElement) {
            ElementInfo changesElementInfo = (ElementInfo) this.mElementToInfoChangesMap.get(element);
            if (changesElementInfo == null || parentElement != changesElementInfo.parentElement) {
                ElementInfo oldElementInfo = (ElementInfo) ShadowDocument.this.mElementToInfoMap.get(element);
                if (changesElementInfo != null || oldElementInfo == null || parentElement != oldElementInfo.parentElement) {
                    if (changesElementInfo == null || oldElementInfo == null || parentElement != oldElementInfo.parentElement || !ListUtil.identityEquals(oldElementInfo.children, changesElementInfo.children)) {
                        List<Object> children = changesElementInfo != null ? changesElementInfo.children : oldElementInfo != null ? oldElementInfo.children : Collections.emptyList();
                        this.mElementToInfoChangesMap.put(element, new ElementInfo(element, parentElement, children));
                        if (parentElement == null) {
                            this.mRootElementChanges.add(element);
                        } else {
                            this.mRootElementChanges.remove(element);
                        }
                    } else {
                        this.mElementToInfoChangesMap.remove(element);
                        if (parentElement == null) {
                            this.mRootElementChanges.remove(element);
                        }
                    }
                }
            }
        }

        public Update build() {
            return new Update(this.mElementToInfoChangesMap, this.mRootElementChanges);
        }

        private HashSet<Object> acquireNotNewChildrenHashSet() {
            HashSet<Object> notNewChildrenHashSet = this.mCachedNotNewChildrenSet;
            if (notNewChildrenHashSet == null) {
                notNewChildrenHashSet = new HashSet<>();
            }
            this.mCachedNotNewChildrenSet = null;
            return notNewChildrenHashSet;
        }

        private void releaseNotNewChildrenHashSet(HashSet<Object> notNewChildrenHashSet) {
            notNewChildrenHashSet.clear();
            if (this.mCachedNotNewChildrenSet == null) {
                this.mCachedNotNewChildrenSet = notNewChildrenHashSet;
            }
        }
    }

    public ShadowDocument(Object rootElement) {
        this.mRootElement = Util.throwIfNull(rootElement);
    }

    public Object getRootElement() {
        return this.mRootElement;
    }

    public ElementInfo getElementInfo(Object element) {
        return (ElementInfo) this.mElementToInfoMap.get(element);
    }

    public UpdateBuilder beginUpdate() {
        if (this.mIsUpdating) {
            throw new IllegalStateException();
        }
        this.mIsUpdating = true;
        return new UpdateBuilder();
    }
}
