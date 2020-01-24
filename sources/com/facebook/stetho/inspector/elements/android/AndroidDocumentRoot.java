package com.facebook.stetho.inspector.elements.android;

import android.app.Application;
import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import com.facebook.stetho.inspector.elements.NodeType;

final class AndroidDocumentRoot extends AbstractChainedDescriptor<AndroidDocumentRoot> {
    private final Application mApplication;

    public AndroidDocumentRoot(Application application) {
        this.mApplication = (Application) Util.throwIfNull(application);
    }

    /* access modifiers changed from: protected */
    public NodeType onGetNodeType(AndroidDocumentRoot element) {
        return NodeType.DOCUMENT_NODE;
    }

    /* access modifiers changed from: protected */
    public String onGetNodeName(AndroidDocumentRoot element) {
        return "root";
    }

    /* access modifiers changed from: protected */
    public void onGetChildren(AndroidDocumentRoot element, Accumulator<Object> children) {
        children.store(this.mApplication);
    }
}
