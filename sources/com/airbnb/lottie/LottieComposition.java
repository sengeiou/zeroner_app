package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.util.JsonReader;
import android.util.Log;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.AsyncCompositionLoader;
import com.airbnb.lottie.parser.LottieCompositionParser;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class LottieComposition {
    private Rect bounds;
    private SparseArrayCompat<FontCharacter> characters;
    private float endFrame;
    private Map<String, Font> fonts;
    private float frameRate;
    private Map<String, LottieImageAsset> images;
    private LongSparseArray<Layer> layerMap;
    private List<Layer> layers;
    private final PerformanceTracker performanceTracker = new PerformanceTracker();
    private Map<String, List<Layer>> precomps;
    private float startFrame;
    private final HashSet<String> warnings = new HashSet<>();

    public static class Factory {
        private Factory() {
        }

        public static Cancellable fromAssetFileName(Context context, String fileName, OnCompositionLoadedListener listener) {
            try {
                return fromInputStream(context.getAssets().open(fileName), listener);
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to find file " + fileName, e);
            }
        }

        public static Cancellable fromRawFile(Context context, @RawRes int resId, OnCompositionLoadedListener listener) {
            return fromInputStream(context.getResources().openRawResource(resId), listener);
        }

        public static Cancellable fromInputStream(InputStream stream, OnCompositionLoadedListener listener) {
            return fromJsonReader(new JsonReader(new InputStreamReader(stream)), listener);
        }

        public static Cancellable fromJsonString(String jsonString, OnCompositionLoadedListener listener) {
            return fromJsonReader(new JsonReader(new StringReader(jsonString)), listener);
        }

        public static Cancellable fromJsonReader(JsonReader reader, OnCompositionLoadedListener listener) {
            AsyncCompositionLoader loader = new AsyncCompositionLoader(listener);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new JsonReader[]{reader});
            return loader;
        }

        @Nullable
        public static LottieComposition fromFileSync(Context context, String fileName) {
            try {
                return fromInputStreamSync(context.getAssets().open(fileName));
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to open asset " + fileName, e);
            }
        }

        @Nullable
        public static LottieComposition fromInputStreamSync(InputStream stream) {
            return fromInputStreamSync(stream, true);
        }

        @Nullable
        public static LottieComposition fromInputStreamSync(InputStream stream, boolean close) {
            try {
                LottieComposition composition = fromJsonSync(new JsonReader(new InputStreamReader(stream)));
                if (close) {
                    Utils.closeQuietly(stream);
                }
                return composition;
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to parse composition.", e);
            } catch (Throwable th) {
                if (close) {
                    Utils.closeQuietly(stream);
                }
                throw th;
            }
        }

        @Deprecated
        public static LottieComposition fromJsonSync(Resources res, JSONObject json) {
            return fromJsonSync(json.toString());
        }

        public static LottieComposition fromJsonSync(String string) {
            try {
                return fromJsonSync(new JsonReader(new StringReader(string)));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        public static LottieComposition fromJsonSync(JsonReader reader) throws IOException {
            return LottieCompositionParser.parse(reader);
        }
    }

    public void init(Rect bounds2, float startFrame2, float endFrame2, float frameRate2, List<Layer> layers2, LongSparseArray<Layer> layerMap2, Map<String, List<Layer>> precomps2, Map<String, LottieImageAsset> images2, SparseArrayCompat<FontCharacter> characters2, Map<String, Font> fonts2) {
        this.bounds = bounds2;
        this.startFrame = startFrame2;
        this.endFrame = endFrame2;
        this.frameRate = frameRate2;
        this.layers = layers2;
        this.layerMap = layerMap2;
        this.precomps = precomps2;
        this.images = images2;
        this.characters = characters2;
        this.fonts = fonts2;
    }

    @RestrictTo({Scope.LIBRARY})
    public void addWarning(String warning) {
        Log.w(L.TAG, warning);
        this.warnings.add(warning);
    }

    public ArrayList<String> getWarnings() {
        return new ArrayList<>(Arrays.asList(this.warnings.toArray(new String[this.warnings.size()])));
    }

    public void setPerformanceTrackingEnabled(boolean enabled) {
        this.performanceTracker.setEnabled(enabled);
    }

    public PerformanceTracker getPerformanceTracker() {
        return this.performanceTracker;
    }

    @RestrictTo({Scope.LIBRARY})
    public Layer layerModelForId(long id) {
        return (Layer) this.layerMap.get(id);
    }

    public Rect getBounds() {
        return this.bounds;
    }

    public float getDuration() {
        return (float) ((long) ((getDurationFrames() / this.frameRate) * 1000.0f));
    }

    @RestrictTo({Scope.LIBRARY})
    public float getStartFrame() {
        return this.startFrame;
    }

    @RestrictTo({Scope.LIBRARY})
    public float getEndFrame() {
        return this.endFrame;
    }

    public float getFrameRate() {
        return this.frameRate;
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY})
    public List<Layer> getPrecomps(String id) {
        return (List) this.precomps.get(id);
    }

    public SparseArrayCompat<FontCharacter> getCharacters() {
        return this.characters;
    }

    public Map<String, Font> getFonts() {
        return this.fonts;
    }

    public boolean hasImages() {
        return !this.images.isEmpty();
    }

    public Map<String, LottieImageAsset> getImages() {
        return this.images;
    }

    public float getDurationFrames() {
        return this.endFrame - this.startFrame;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        for (Layer layer : this.layers) {
            sb.append(layer.toString("\t"));
        }
        return sb.toString();
    }
}
