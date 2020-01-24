package com.iwown.sport_module.ui.skin_loader;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.SparseArray;

public class FatigueSkinHandler {
    private static final int FatigueSkin_Type_Top_Bottom_BG = 1;
    private static final int FatigueSkin_Type_Top_TitlBar = 2;
    private SparseArray<Object> cache_skins;

    private static class SleepSkinHandlerHolder {
        public static FatigueSkinHandler sleepSkinHandler = new FatigueSkinHandler();

        private SleepSkinHandlerHolder() {
        }
    }

    private FatigueSkinHandler() {
        this.cache_skins = new SparseArray<>();
    }

    public void setskinTopBG(int topBG, int bottomBG) {
        this.cache_skins.put(1, new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{topBG, bottomBG}));
        this.cache_skins.put(2, Integer.valueOf(topBG));
    }

    public static FatigueSkinHandler getInstance() {
        return SleepSkinHandlerHolder.sleepSkinHandler;
    }

    public void init() {
        this.cache_skins.clear();
        setskinTopBG(-14046971, -16418048);
    }

    public int getFatigueSkin_Type_Top_TitlBar() {
        return ((Integer) this.cache_skins.get(2)).intValue();
    }

    public Drawable getFatigueSkin_Type_Top_Bottom_BG() {
        return (Drawable) this.cache_skins.get(1);
    }
}
