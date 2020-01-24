package com.airbnb.lottie;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;

public class TextDelegate {
    @Nullable
    private final LottieAnimationView animationView;
    private boolean cacheText;
    @Nullable
    private final LottieDrawable drawable;
    private final Map<String, String> stringMap;

    @VisibleForTesting
    TextDelegate() {
        this.stringMap = new HashMap();
        this.cacheText = true;
        this.animationView = null;
        this.drawable = null;
    }

    public TextDelegate(LottieAnimationView animationView2) {
        this.stringMap = new HashMap();
        this.cacheText = true;
        this.animationView = animationView2;
        this.drawable = null;
    }

    public TextDelegate(LottieDrawable drawable2) {
        this.stringMap = new HashMap();
        this.cacheText = true;
        this.drawable = drawable2;
        this.animationView = null;
    }

    private String getText(String input) {
        return input;
    }

    public void setText(String input, String output) {
        this.stringMap.put(input, output);
        invalidate();
    }

    public void setCacheText(boolean cacheText2) {
        this.cacheText = cacheText2;
    }

    public void invalidateText(String input) {
        this.stringMap.remove(input);
        invalidate();
    }

    public void invalidateAllText() {
        this.stringMap.clear();
        invalidate();
    }

    public final String getTextInternal(String input) {
        if (this.cacheText && this.stringMap.containsKey(input)) {
            return (String) this.stringMap.get(input);
        }
        String text = getText(input);
        if (this.cacheText) {
            this.stringMap.put(input, text);
        }
        return text;
    }

    private void invalidate() {
        if (this.animationView != null) {
            this.animationView.invalidate();
        }
        if (this.drawable != null) {
            this.drawable.invalidateSelf();
        }
    }
}
