package com.facebook.stetho.inspector.elements.android;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.common.Predicate;
import com.facebook.stetho.common.ThreadBound;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.elements.Descriptor;
import com.facebook.stetho.inspector.elements.DescriptorMap;
import com.facebook.stetho.inspector.elements.DescriptorProvider;
import com.facebook.stetho.inspector.elements.DocumentProvider;
import com.facebook.stetho.inspector.elements.DocumentProviderListener;
import com.facebook.stetho.inspector.elements.NodeDescriptor;
import com.facebook.stetho.inspector.elements.ObjectDescriptor;
import com.facebook.stetho.inspector.helper.ThreadBoundProxy;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

final class AndroidDocumentProvider extends ThreadBoundProxy implements DocumentProvider, AndroidDescriptorHost {
    private static final int INSPECT_HOVER_COLOR = 1077952767;
    private static final int INSPECT_OVERLAY_COLOR = 1090519039;
    private static final long REPORT_CHANGED_INTERVAL_MS = 1000;
    /* access modifiers changed from: private */
    public final Application mApplication;
    private final DescriptorMap mDescriptorMap;
    private final AndroidDocumentRoot mDocumentRoot;
    /* access modifiers changed from: private */
    public final ViewHighlighter mHighlighter;
    /* access modifiers changed from: private */
    public final Rect mHighlightingBoundsRect = new Rect();
    /* access modifiers changed from: private */
    public final Rect mHitRect = new Rect();
    private final InspectModeHandler mInspectModeHandler;
    /* access modifiers changed from: private */
    public boolean mIsReportChangesTimerPosted = false;
    /* access modifiers changed from: private */
    @Nullable
    public DocumentProviderListener mListener;
    private final Runnable mReportChangesTimer = new Runnable() {
        public void run() {
            AndroidDocumentProvider.this.mIsReportChangesTimerPosted = false;
            if (AndroidDocumentProvider.this.mListener != null) {
                AndroidDocumentProvider.this.mListener.onPossiblyChanged();
                AndroidDocumentProvider.this.mIsReportChangesTimerPosted = true;
                AndroidDocumentProvider.this.postDelayed(this, AndroidDocumentProvider.REPORT_CHANGED_INTERVAL_MS);
            }
        }
    };

    private final class InspectModeHandler {
        /* access modifiers changed from: private */
        public List<View> mOverlays;
        private final Predicate<View> mViewSelector;

        private final class OverlayView extends DocumentHiddenView {
            public OverlayView(Context context) {
                super(context);
            }

            /* access modifiers changed from: protected */
            public void onDraw(Canvas canvas) {
                canvas.drawColor(AndroidDocumentProvider.INSPECT_OVERLAY_COLOR);
                super.onDraw(canvas);
            }

            public boolean onTouchEvent(MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Object parent = getParent();
                while (true) {
                    HighlightableDescriptor descriptor = AndroidDocumentProvider.this.getHighlightableDescriptor(parent);
                    if (descriptor != null) {
                        AndroidDocumentProvider.this.mHitRect.setEmpty();
                        Object element = descriptor.getElementToHighlightAtPosition(parent, x, y, AndroidDocumentProvider.this.mHitRect);
                        x -= AndroidDocumentProvider.this.mHitRect.left;
                        y -= AndroidDocumentProvider.this.mHitRect.top;
                        if (element == parent) {
                            break;
                        }
                        parent = element;
                    } else {
                        break;
                    }
                }
                if (parent != null) {
                    HighlightableDescriptor descriptor2 = AndroidDocumentProvider.this.getHighlightableDescriptor(parent);
                    if (descriptor2 != null) {
                        View viewToHighlight = descriptor2.getViewAndBoundsForHighlighting(parent, AndroidDocumentProvider.this.mHighlightingBoundsRect);
                        if (!(event.getAction() == 3 || viewToHighlight == null)) {
                            AndroidDocumentProvider.this.mHighlighter.setHighlightedView(viewToHighlight, AndroidDocumentProvider.this.mHighlightingBoundsRect, AndroidDocumentProvider.INSPECT_HOVER_COLOR);
                            if (event.getAction() == 1 && AndroidDocumentProvider.this.mListener != null) {
                                AndroidDocumentProvider.this.mListener.onInspectRequested(parent);
                            }
                        }
                    }
                }
                return true;
            }
        }

        private InspectModeHandler() {
            this.mViewSelector = new Predicate<View>() {
                public boolean apply(View view) {
                    return !(view instanceof DocumentHiddenView);
                }
            };
        }

        public void enable() {
            AndroidDocumentProvider.this.verifyThreadAccess();
            if (this.mOverlays != null) {
                disable();
            }
            this.mOverlays = new ArrayList();
            AndroidDocumentProvider.this.getWindows(new Accumulator<Window>() {
                public void store(Window object) {
                    if (object.peekDecorView() instanceof ViewGroup) {
                        ViewGroup decorView = (ViewGroup) object.peekDecorView();
                        OverlayView overlayView = new OverlayView(AndroidDocumentProvider.this.mApplication);
                        LayoutParams layoutParams = new LayoutParams();
                        layoutParams.width = -1;
                        layoutParams.height = -1;
                        decorView.addView(overlayView, layoutParams);
                        decorView.bringChildToFront(overlayView);
                        InspectModeHandler.this.mOverlays.add(overlayView);
                    }
                }
            });
        }

        public void disable() {
            AndroidDocumentProvider.this.verifyThreadAccess();
            if (this.mOverlays != null) {
                for (int i = 0; i < this.mOverlays.size(); i++) {
                    View overlayView = (View) this.mOverlays.get(i);
                    ((ViewGroup) overlayView.getParent()).removeView(overlayView);
                }
                this.mOverlays = null;
            }
        }
    }

    public AndroidDocumentProvider(Application application, List<DescriptorProvider> descriptorProviders, ThreadBound enforcer) {
        super(enforcer);
        this.mApplication = (Application) Util.throwIfNull(application);
        this.mDocumentRoot = new AndroidDocumentRoot(application);
        this.mDescriptorMap = new DescriptorMap().beginInit().registerDescriptor(Activity.class, (Descriptor) new ActivityDescriptor()).registerDescriptor(AndroidDocumentRoot.class, (Descriptor) this.mDocumentRoot).registerDescriptor(Application.class, (Descriptor) new ApplicationDescriptor()).registerDescriptor(Dialog.class, (Descriptor) new DialogDescriptor()).registerDescriptor(Object.class, (Descriptor) new ObjectDescriptor()).registerDescriptor(TextView.class, (Descriptor) new TextViewDescriptor()).registerDescriptor(View.class, (Descriptor) new ViewDescriptor()).registerDescriptor(ViewGroup.class, (Descriptor) new ViewGroupDescriptor()).registerDescriptor(Window.class, (Descriptor) new WindowDescriptor());
        DialogFragmentDescriptor.register(this.mDescriptorMap);
        FragmentDescriptor.register(this.mDescriptorMap);
        int size = descriptorProviders.size();
        for (int i = 0; i < size; i++) {
            ((DescriptorProvider) descriptorProviders.get(i)).registerDescriptor(this.mDescriptorMap);
        }
        this.mDescriptorMap.setHost(this).endInit();
        this.mHighlighter = ViewHighlighter.newInstance();
        this.mInspectModeHandler = new InspectModeHandler();
    }

    public void dispose() {
        verifyThreadAccess();
        this.mHighlighter.clearHighlight();
        this.mInspectModeHandler.disable();
        removeCallbacks(this.mReportChangesTimer);
        this.mIsReportChangesTimerPosted = false;
        this.mListener = null;
    }

    public void setListener(DocumentProviderListener listener) {
        verifyThreadAccess();
        this.mListener = listener;
        if (this.mListener == null && this.mIsReportChangesTimerPosted) {
            this.mIsReportChangesTimerPosted = false;
            removeCallbacks(this.mReportChangesTimer);
        } else if (this.mListener != null && !this.mIsReportChangesTimerPosted) {
            this.mIsReportChangesTimerPosted = true;
            postDelayed(this.mReportChangesTimer, REPORT_CHANGED_INTERVAL_MS);
        }
    }

    public Object getRootElement() {
        verifyThreadAccess();
        return this.mDocumentRoot;
    }

    public NodeDescriptor getNodeDescriptor(Object element) {
        verifyThreadAccess();
        return getDescriptor(element);
    }

    public void highlightElement(Object element, int color) {
        verifyThreadAccess();
        HighlightableDescriptor descriptor = getHighlightableDescriptor(element);
        if (descriptor == null) {
            this.mHighlighter.clearHighlight();
            return;
        }
        this.mHighlightingBoundsRect.setEmpty();
        View highlightingView = descriptor.getViewAndBoundsForHighlighting(element, this.mHighlightingBoundsRect);
        if (highlightingView == null) {
            this.mHighlighter.clearHighlight();
        } else {
            this.mHighlighter.setHighlightedView(highlightingView, this.mHighlightingBoundsRect, color);
        }
    }

    public void hideHighlight() {
        verifyThreadAccess();
        this.mHighlighter.clearHighlight();
    }

    public void setInspectModeEnabled(boolean enabled) {
        verifyThreadAccess();
        if (enabled) {
            this.mInspectModeHandler.enable();
        } else {
            this.mInspectModeHandler.disable();
        }
    }

    public void setAttributesAsText(Object element, String text) {
        verifyThreadAccess();
        Descriptor descriptor = this.mDescriptorMap.get(element.getClass());
        if (descriptor != null) {
            descriptor.setAttributesAsText(element, text);
        }
    }

    public Descriptor getDescriptor(Object element) {
        if (element == null) {
            return null;
        }
        return this.mDescriptorMap.get(element.getClass());
    }

    public void onAttributeModified(Object element, String name, String value) {
        if (this.mListener != null) {
            this.mListener.onAttributeModified(element, name, value);
        }
    }

    public void onAttributeRemoved(Object element, String name) {
        if (this.mListener != null) {
            this.mListener.onAttributeRemoved(element, name);
        }
    }

    @Nullable
    public HighlightableDescriptor getHighlightableDescriptor(@Nullable Object element) {
        if (element == null) {
            return null;
        }
        HighlightableDescriptor highlightableDescriptor = null;
        Class<?> theClass = element.getClass();
        Descriptor lastDescriptor = null;
        while (highlightableDescriptor == null && theClass != null) {
            Descriptor descriptor = this.mDescriptorMap.get(theClass);
            if (descriptor == null) {
                return null;
            }
            if (descriptor != lastDescriptor && (descriptor instanceof HighlightableDescriptor)) {
                highlightableDescriptor = (HighlightableDescriptor) descriptor;
            }
            lastDescriptor = descriptor;
            theClass = theClass.getSuperclass();
        }
        return highlightableDescriptor;
    }

    /* access modifiers changed from: private */
    public void getWindows(final Accumulator<Window> accumulator) {
        Descriptor appDescriptor = getDescriptor(this.mApplication);
        if (appDescriptor != null) {
            appDescriptor.getChildren(this.mApplication, new Accumulator<Object>() {
                public void store(Object element) {
                    if (element instanceof Window) {
                        accumulator.store((Window) element);
                        return;
                    }
                    Descriptor elementDescriptor = AndroidDocumentProvider.this.getDescriptor(element);
                    if (elementDescriptor != null) {
                        elementDescriptor.getChildren(element, this);
                    }
                }
            });
        }
    }
}
