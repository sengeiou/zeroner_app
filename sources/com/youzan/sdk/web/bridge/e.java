package com.youzan.sdk.web.bridge;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Parser */
public final class e {

    /* renamed from: ˊ reason: contains not printable characters */
    private String f384;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f385;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f386;

    /* renamed from: ˏ reason: contains not printable characters */
    private boolean f387 = false;

    public e(String meta) {
        m157(meta);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public boolean m158() {
        return this.f387;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private void m155(boolean isValid) {
        this.f387 = isValid;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public String m159() {
        return this.f385;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private void m154(String data) {
        this.f385 = data;
    }

    /* renamed from: ˎ reason: contains not printable characters */
    public String m160() {
        return this.f386;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private void m156(String method) {
        this.f386 = method;
    }

    /* renamed from: ˎ reason: contains not printable characters */
    private void m157(String obj) {
        this.f384 = obj;
        if (obj != null) {
            try {
                JSONObject meta = new JSONObject(obj);
                JSONArray argsTypes = meta.getJSONArray("types");
                JSONArray argsData = meta.getJSONArray("args");
                m156(meta.getString("method"));
                String currType = null;
                if (argsTypes.length() > 0) {
                    currType = argsTypes.getString(0);
                }
                if ("string".equalsIgnoreCase(currType) && argsData.length() > 0) {
                    m154(argsData.optString(0));
                }
                m155(true);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public String toString() {
        return this.f384;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        e that = (e) o;
        if (this.f384 != null) {
            if (this.f384.equals(that.f384)) {
                return true;
            }
        } else if (that.f384 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.f384.hashCode();
    }
}
