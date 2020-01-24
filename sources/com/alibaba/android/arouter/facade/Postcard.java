package com.alibaba.android.arouter.facade;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.SparseArray;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class Postcard extends RouteMeta {
    private int enterAnim;
    private int exitAnim;
    private int flags;
    private boolean greenChannel;
    private Bundle mBundle;
    private Bundle optionsCompat;
    private IProvider provider;
    private SerializationService serializationService;
    private Object tag;
    private int timeout;
    private Uri uri;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlagInt {
    }

    public Bundle getOptionsBundle() {
        return this.optionsCompat;
    }

    public int getEnterAnim() {
        return this.enterAnim;
    }

    public int getExitAnim() {
        return this.exitAnim;
    }

    public IProvider getProvider() {
        return this.provider;
    }

    public Postcard setProvider(IProvider provider2) {
        this.provider = provider2;
        return this;
    }

    public Postcard() {
        this(null, null);
    }

    public Postcard(String path, String group) {
        this(path, group, null, null);
    }

    public Postcard(String path, String group, Uri uri2, Bundle bundle) {
        this.flags = -1;
        this.timeout = 300;
        setPath(path);
        setGroup(group);
        setUri(uri2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.mBundle = bundle;
    }

    public boolean isGreenChannel() {
        return this.greenChannel;
    }

    public Object getTag() {
        return this.tag;
    }

    public Postcard setTag(Object tag2) {
        this.tag = tag2;
        return this;
    }

    public Bundle getExtras() {
        return this.mBundle;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public Postcard setTimeout(int timeout2) {
        this.timeout = timeout2;
        return this;
    }

    public Uri getUri() {
        return this.uri;
    }

    public Postcard setUri(Uri uri2) {
        this.uri = uri2;
        return this;
    }

    public Object navigation() {
        return navigation(null);
    }

    public Object navigation(Context context) {
        return navigation(context, (NavigationCallback) null);
    }

    public Object navigation(Context context, NavigationCallback callback) {
        return ARouter.getInstance().navigation(context, this, -1, callback);
    }

    public void navigation(Activity mContext, int requestCode) {
        navigation(mContext, requestCode, null);
    }

    public void navigation(Activity mContext, int requestCode, NavigationCallback callback) {
        ARouter.getInstance().navigation(mContext, this, requestCode, callback);
    }

    public Postcard greenChannel() {
        this.greenChannel = true;
        return this;
    }

    public Postcard with(Bundle bundle) {
        if (bundle != null) {
            this.mBundle = bundle;
        }
        return this;
    }

    public Postcard withFlags(int flag) {
        this.flags = flag;
        return this;
    }

    public int getFlags() {
        return this.flags;
    }

    public Postcard withObject(@Nullable String key, @Nullable Object value) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        this.mBundle.putString(key, this.serializationService.object2Json(value));
        return this;
    }

    public Postcard withString(@Nullable String key, @Nullable String value) {
        this.mBundle.putString(key, value);
        return this;
    }

    public Postcard withBoolean(@Nullable String key, boolean value) {
        this.mBundle.putBoolean(key, value);
        return this;
    }

    public Postcard withShort(@Nullable String key, short value) {
        this.mBundle.putShort(key, value);
        return this;
    }

    public Postcard withInt(@Nullable String key, int value) {
        this.mBundle.putInt(key, value);
        return this;
    }

    public Postcard withLong(@Nullable String key, long value) {
        this.mBundle.putLong(key, value);
        return this;
    }

    public Postcard withDouble(@Nullable String key, double value) {
        this.mBundle.putDouble(key, value);
        return this;
    }

    public Postcard withByte(@Nullable String key, byte value) {
        this.mBundle.putByte(key, value);
        return this;
    }

    public Postcard withChar(@Nullable String key, char value) {
        this.mBundle.putChar(key, value);
        return this;
    }

    public Postcard withFloat(@Nullable String key, float value) {
        this.mBundle.putFloat(key, value);
        return this;
    }

    public Postcard withCharSequence(@Nullable String key, @Nullable CharSequence value) {
        this.mBundle.putCharSequence(key, value);
        return this;
    }

    public Postcard withParcelable(@Nullable String key, @Nullable Parcelable value) {
        this.mBundle.putParcelable(key, value);
        return this;
    }

    public Postcard withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        this.mBundle.putParcelableArray(key, value);
        return this;
    }

    public Postcard withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        this.mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public Postcard withSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        this.mBundle.putSparseParcelableArray(key, value);
        return this;
    }

    public Postcard withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        this.mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public Postcard withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        this.mBundle.putStringArrayList(key, value);
        return this;
    }

    public Postcard withCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        this.mBundle.putCharSequenceArrayList(key, value);
        return this;
    }

    public Postcard withSerializable(@Nullable String key, @Nullable Serializable value) {
        this.mBundle.putSerializable(key, value);
        return this;
    }

    public Postcard withByteArray(@Nullable String key, @Nullable byte[] value) {
        this.mBundle.putByteArray(key, value);
        return this;
    }

    public Postcard withShortArray(@Nullable String key, @Nullable short[] value) {
        this.mBundle.putShortArray(key, value);
        return this;
    }

    public Postcard withCharArray(@Nullable String key, @Nullable char[] value) {
        this.mBundle.putCharArray(key, value);
        return this;
    }

    public Postcard withFloatArray(@Nullable String key, @Nullable float[] value) {
        this.mBundle.putFloatArray(key, value);
        return this;
    }

    public Postcard withCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        this.mBundle.putCharSequenceArray(key, value);
        return this;
    }

    public Postcard withBundle(@Nullable String key, @Nullable Bundle value) {
        this.mBundle.putBundle(key, value);
        return this;
    }

    public Postcard withTransition(int enterAnim2, int exitAnim2) {
        this.enterAnim = enterAnim2;
        this.exitAnim = exitAnim2;
        return this;
    }

    @RequiresApi(16)
    public Postcard withOptionsCompat(ActivityOptionsCompat compat) {
        if (compat != null) {
            this.optionsCompat = compat.toBundle();
        }
        return this;
    }

    public String toString() {
        return "Postcard{uri=" + this.uri + ", tag=" + this.tag + ", mBundle=" + this.mBundle + ", flags=" + this.flags + ", timeout=" + this.timeout + ", provider=" + this.provider + ", greenChannel=" + this.greenChannel + ", optionsCompat=" + this.optionsCompat + ", enterAnim=" + this.enterAnim + ", exitAnim=" + this.exitAnim + "}\n" + super.toString();
    }
}
