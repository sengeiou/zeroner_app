package com.facebook.stetho.inspector.jsonrpc.protocol;

import android.annotation.SuppressLint;
import com.facebook.stetho.json.annotation.JsonProperty;
import javax.annotation.Nullable;
import org.json.JSONObject;

@SuppressLint({"UsingDefaultJsonDeserializer", "EmptyJsonPropertyUse"})
public class JsonRpcEvent {
    @JsonProperty(required = true)
    public String method;
    @JsonProperty
    public JSONObject params;

    public JsonRpcEvent() {
    }

    public JsonRpcEvent(String method2, @Nullable JSONObject params2) {
        this.method = method2;
        this.params = params2;
    }
}
