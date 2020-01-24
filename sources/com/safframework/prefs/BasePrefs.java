package com.safframework.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.codec.binary.Base64;

public class BasePrefs {
    private Editor editor;
    private SharedPreferences prefs;

    protected BasePrefs(Context context, String prefsName) {
        this.prefs = context.getSharedPreferences(prefsName, 0);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.prefs.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return this.prefs.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return this.prefs.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return this.prefs.getLong(key, defValue);
    }

    public String getString(String key, String defValue) {
        return this.prefs.getString(key, defValue);
    }

    public Object getObject(String key) {
        try {
            String stringBase64 = this.prefs.getString(key, "");
            if (TextUtils.isEmpty(stringBase64)) {
                return null;
            }
            return new ObjectInputStream(new ByteArrayInputStream(Base64.decodeBase64(stringBase64.getBytes()))).readObject();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public void putBoolean(String key, boolean v) {
        ensureEditorAvailability();
        this.editor.putBoolean(key, v);
        save();
    }

    public void putFloat(String key, float v) {
        ensureEditorAvailability();
        this.editor.putFloat(key, v);
        save();
    }

    public void putInt(String key, int v) {
        ensureEditorAvailability();
        this.editor.putInt(key, v);
        save();
    }

    public void putLong(String key, long v) {
        ensureEditorAvailability();
        this.editor.putLong(key, v);
        save();
    }

    public void putString(String key, String v) {
        ensureEditorAvailability();
        this.editor.putString(key, v);
        save();
    }

    public void putObject(String key, Object obj) {
        ensureEditorAvailability();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new ObjectOutputStream(baos).writeObject(obj);
            this.editor.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
            save();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void save() {
        if (this.editor != null) {
            this.editor.apply();
        }
    }

    private void ensureEditorAvailability() {
        if (this.editor == null) {
            this.editor = this.prefs.edit();
        }
    }

    public void remove(String key) {
        ensureEditorAvailability();
        this.editor.remove(key);
        save();
    }

    public void clear() {
        ensureEditorAvailability();
        this.editor.clear();
        save();
    }
}
