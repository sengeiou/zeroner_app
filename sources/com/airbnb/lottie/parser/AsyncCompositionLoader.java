package com.airbnb.lottie.parser;

import android.os.AsyncTask;
import android.util.JsonReader;
import com.airbnb.lottie.Cancellable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import java.io.IOException;

public final class AsyncCompositionLoader extends AsyncTask<JsonReader, Void, LottieComposition> implements Cancellable {
    private final OnCompositionLoadedListener loadedListener;

    public AsyncCompositionLoader(OnCompositionLoadedListener loadedListener2) {
        this.loadedListener = loadedListener2;
    }

    /* access modifiers changed from: protected */
    public LottieComposition doInBackground(JsonReader... params) {
        try {
            return Factory.fromJsonSync(params[0]);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(LottieComposition composition) {
        this.loadedListener.onCompositionLoaded(composition);
    }

    public void cancel() {
        cancel(true);
    }
}
