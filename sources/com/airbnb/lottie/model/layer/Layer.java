package com.airbnb.lottie.model.layer;

import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;
import java.util.Locale;

public class Layer {
    private final LottieComposition composition;
    private final List<Keyframe<Float>> inOutKeyframes;
    private final long layerId;
    private final String layerName;
    private final LayerType layerType;
    private final List<Mask> masks;
    private final MatteType matteType;
    private final long parentId;
    private final int preCompHeight;
    private final int preCompWidth;
    @Nullable
    private final String refId;
    private final List<ContentModel> shapes;
    private final int solidColor;
    private final int solidHeight;
    private final int solidWidth;
    private final float startFrame;
    @Nullable
    private final AnimatableTextFrame text;
    @Nullable
    private final AnimatableTextProperties textProperties;
    @Nullable
    private final AnimatableFloatValue timeRemapping;
    private final float timeStretch;
    private final AnimatableTransform transform;

    public enum LayerType {
        PreComp,
        Solid,
        Image,
        Null,
        Shape,
        Text,
        Unknown
    }

    public enum MatteType {
        None,
        Add,
        Invert,
        Unknown
    }

    public Layer(List<ContentModel> shapes2, LottieComposition composition2, String layerName2, long layerId2, LayerType layerType2, long parentId2, @Nullable String refId2, List<Mask> masks2, AnimatableTransform transform2, int solidWidth2, int solidHeight2, int solidColor2, float timeStretch2, float startFrame2, int preCompWidth2, int preCompHeight2, @Nullable AnimatableTextFrame text2, @Nullable AnimatableTextProperties textProperties2, List<Keyframe<Float>> inOutKeyframes2, MatteType matteType2, @Nullable AnimatableFloatValue timeRemapping2) {
        this.shapes = shapes2;
        this.composition = composition2;
        this.layerName = layerName2;
        this.layerId = layerId2;
        this.layerType = layerType2;
        this.parentId = parentId2;
        this.refId = refId2;
        this.masks = masks2;
        this.transform = transform2;
        this.solidWidth = solidWidth2;
        this.solidHeight = solidHeight2;
        this.solidColor = solidColor2;
        this.timeStretch = timeStretch2;
        this.startFrame = startFrame2;
        this.preCompWidth = preCompWidth2;
        this.preCompHeight = preCompHeight2;
        this.text = text2;
        this.textProperties = textProperties2;
        this.inOutKeyframes = inOutKeyframes2;
        this.matteType = matteType2;
        this.timeRemapping = timeRemapping2;
    }

    /* access modifiers changed from: 0000 */
    public LottieComposition getComposition() {
        return this.composition;
    }

    /* access modifiers changed from: 0000 */
    public float getTimeStretch() {
        return this.timeStretch;
    }

    /* access modifiers changed from: 0000 */
    public float getStartProgress() {
        return this.startFrame / this.composition.getDurationFrames();
    }

    /* access modifiers changed from: 0000 */
    public List<Keyframe<Float>> getInOutKeyframes() {
        return this.inOutKeyframes;
    }

    public long getId() {
        return this.layerId;
    }

    /* access modifiers changed from: 0000 */
    public String getName() {
        return this.layerName;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public String getRefId() {
        return this.refId;
    }

    /* access modifiers changed from: 0000 */
    public int getPreCompWidth() {
        return this.preCompWidth;
    }

    /* access modifiers changed from: 0000 */
    public int getPreCompHeight() {
        return this.preCompHeight;
    }

    /* access modifiers changed from: 0000 */
    public List<Mask> getMasks() {
        return this.masks;
    }

    public LayerType getLayerType() {
        return this.layerType;
    }

    /* access modifiers changed from: 0000 */
    public MatteType getMatteType() {
        return this.matteType;
    }

    /* access modifiers changed from: 0000 */
    public long getParentId() {
        return this.parentId;
    }

    /* access modifiers changed from: 0000 */
    public List<ContentModel> getShapes() {
        return this.shapes;
    }

    /* access modifiers changed from: 0000 */
    public AnimatableTransform getTransform() {
        return this.transform;
    }

    /* access modifiers changed from: 0000 */
    public int getSolidColor() {
        return this.solidColor;
    }

    /* access modifiers changed from: 0000 */
    public int getSolidHeight() {
        return this.solidHeight;
    }

    /* access modifiers changed from: 0000 */
    public int getSolidWidth() {
        return this.solidWidth;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public AnimatableTextFrame getText() {
        return this.text;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public AnimatableTextProperties getTextProperties() {
        return this.textProperties;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public AnimatableFloatValue getTimeRemapping() {
        return this.timeRemapping;
    }

    public String toString() {
        return toString("");
    }

    public String toString(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(getName()).append("\n");
        Layer parent = this.composition.layerModelForId(getParentId());
        if (parent != null) {
            sb.append("\t\tParents: ").append(parent.getName());
            Layer parent2 = this.composition.layerModelForId(parent.getParentId());
            while (parent2 != null) {
                sb.append("->").append(parent2.getName());
                parent2 = this.composition.layerModelForId(parent2.getParentId());
            }
            sb.append(prefix).append("\n");
        }
        if (!getMasks().isEmpty()) {
            sb.append(prefix).append("\tMasks: ").append(getMasks().size()).append("\n");
        }
        if (!(getSolidWidth() == 0 || getSolidHeight() == 0)) {
            sb.append(prefix).append("\tBackground: ").append(String.format(Locale.US, "%dx%d %X\n", new Object[]{Integer.valueOf(getSolidWidth()), Integer.valueOf(getSolidHeight()), Integer.valueOf(getSolidColor())}));
        }
        if (!this.shapes.isEmpty()) {
            sb.append(prefix).append("\tShapes:\n");
            for (Object shape : this.shapes) {
                sb.append(prefix).append("\t\t").append(shape).append("\n");
            }
        }
        return sb.toString();
    }
}
