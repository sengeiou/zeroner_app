package com.airbnb.lottie.model.content;

import android.support.annotation.Nullable;
import android.util.Log;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.MergePathsContent;
import com.airbnb.lottie.model.layer.BaseLayer;

public class MergePaths implements ContentModel {
    private final MergePathsMode mode;
    private final String name;

    public enum MergePathsMode {
        Merge,
        Add,
        Subtract,
        Intersect,
        ExcludeIntersections;

        public static MergePathsMode forId(int id) {
            switch (id) {
                case 1:
                    return Merge;
                case 2:
                    return Add;
                case 3:
                    return Subtract;
                case 4:
                    return Intersect;
                case 5:
                    return ExcludeIntersections;
                default:
                    return Merge;
            }
        }
    }

    public MergePaths(String name2, MergePathsMode mode2) {
        this.name = name2;
        this.mode = mode2;
    }

    public String getName() {
        return this.name;
    }

    public MergePathsMode getMode() {
        return this.mode;
    }

    @Nullable
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        if (drawable.enableMergePathsForKitKatAndAbove()) {
            return new MergePathsContent(this);
        }
        Log.w(L.TAG, "Animation contains merge paths but they are disabled.");
        return null;
    }

    public String toString() {
        return "MergePaths{mode=" + this.mode + '}';
    }
}
