package com.facebook.stetho.inspector.elements.android;

import android.app.Activity;
import android.app.Application;
import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import com.facebook.stetho.inspector.elements.NodeType;
import com.facebook.stetho.inspector.elements.android.ActivityTracker.Listener;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

final class ApplicationDescriptor extends AbstractChainedDescriptor<Application> {
    /* access modifiers changed from: private */
    public final ActivityTracker mActivityTracker = ActivityTracker.get();
    private final Map<Application, ElementContext> mElementToContextMap = Collections.synchronizedMap(new IdentityHashMap());

    private class ElementContext {
        private Application mElement;
        private final Listener mListener = new Listener() {
            public void onActivityAdded(Activity activity) {
            }

            public void onActivityRemoved(Activity activity) {
            }
        };

        public ElementContext() {
        }

        public void hook(Application element) {
            this.mElement = element;
            ApplicationDescriptor.this.mActivityTracker.registerListener(this.mListener);
        }

        public void unhook() {
            ApplicationDescriptor.this.mActivityTracker.unregisterListener(this.mListener);
            this.mElement = null;
        }

        public List<WeakReference<Activity>> getActivitiesList() {
            return ApplicationDescriptor.this.mActivityTracker.getActivitiesView();
        }
    }

    ApplicationDescriptor() {
    }

    private ElementContext getContext(Application element) {
        return (ElementContext) this.mElementToContextMap.get(element);
    }

    /* access modifiers changed from: protected */
    public void onHook(Application element) {
        ElementContext context = new ElementContext();
        context.hook(element);
        this.mElementToContextMap.put(element, context);
    }

    /* access modifiers changed from: protected */
    public void onUnhook(Application element) {
        ((ElementContext) this.mElementToContextMap.remove(element)).unhook();
    }

    /* access modifiers changed from: protected */
    public NodeType onGetNodeType(Application element) {
        return NodeType.ELEMENT_NODE;
    }

    /* access modifiers changed from: protected */
    public void onGetChildren(Application element, Accumulator<Object> children) {
        List<WeakReference<Activity>> activities = getContext(element).getActivitiesList();
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = (Activity) ((WeakReference) activities.get(i)).get();
            if (activity != null) {
                children.store(activity);
            }
        }
    }
}
