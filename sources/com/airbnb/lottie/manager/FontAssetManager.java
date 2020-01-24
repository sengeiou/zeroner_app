package com.airbnb.lottie.manager;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.airbnb.lottie.FontAssetDelegate;
import com.airbnb.lottie.L;
import com.airbnb.lottie.model.MutablePair;
import java.util.HashMap;
import java.util.Map;

public class FontAssetManager {
    private final AssetManager assetManager;
    private String defaultFontFileExtension = ".ttf";
    @Nullable
    private FontAssetDelegate delegate;
    private final Map<String, Typeface> fontFamilies = new HashMap();
    private final Map<MutablePair<String>, Typeface> fontMap = new HashMap();
    private final MutablePair<String> tempPair = new MutablePair<>();

    public FontAssetManager(Callback callback, @Nullable FontAssetDelegate delegate2) {
        this.delegate = delegate2;
        if (!(callback instanceof View)) {
            Log.w(L.TAG, "LottieDrawable must be inside of a view for images to work.");
            this.assetManager = null;
            return;
        }
        this.assetManager = ((View) callback).getContext().getAssets();
    }

    public void setDelegate(@Nullable FontAssetDelegate assetDelegate) {
        this.delegate = assetDelegate;
    }

    public void setDefaultFontFileExtension(String defaultFontFileExtension2) {
        this.defaultFontFileExtension = defaultFontFileExtension2;
    }

    public Typeface getTypeface(String fontFamily, String style) {
        this.tempPair.set(fontFamily, style);
        Typeface typeface = (Typeface) this.fontMap.get(this.tempPair);
        if (typeface != null) {
            return typeface;
        }
        Typeface typeface2 = typefaceForStyle(getFontFamily(fontFamily), style);
        this.fontMap.put(this.tempPair, typeface2);
        return typeface2;
    }

    private Typeface getFontFamily(String fontFamily) {
        Typeface defaultTypeface = (Typeface) this.fontFamilies.get(fontFamily);
        if (defaultTypeface != null) {
            return defaultTypeface;
        }
        Typeface typeface = null;
        if (this.delegate != null) {
            typeface = this.delegate.fetchFont(fontFamily);
        }
        if (this.delegate != null && typeface == null) {
            String path = this.delegate.getFontPath(fontFamily);
            if (path != null) {
                typeface = Typeface.createFromAsset(this.assetManager, path);
            }
        }
        if (typeface == null) {
            typeface = Typeface.createFromAsset(this.assetManager, "fonts/" + fontFamily + this.defaultFontFileExtension);
        }
        this.fontFamilies.put(fontFamily, typeface);
        return typeface;
    }

    private Typeface typefaceForStyle(Typeface typeface, String style) {
        int styleInt = 0;
        boolean containsItalic = style.contains("Italic");
        boolean containsBold = style.contains("Bold");
        if (containsItalic && containsBold) {
            styleInt = 3;
        } else if (containsItalic) {
            styleInt = 2;
        } else if (containsBold) {
            styleInt = 1;
        }
        return typeface.getStyle() == styleInt ? typeface : Typeface.create(typeface, styleInt);
    }
}
