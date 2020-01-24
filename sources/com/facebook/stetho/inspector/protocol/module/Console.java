package com.facebook.stetho.inspector.protocol.module;

import android.annotation.SuppressLint;
import com.facebook.stetho.inspector.console.ConsolePeerManager;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsMethod;
import com.facebook.stetho.json.annotation.JsonProperty;
import com.facebook.stetho.json.annotation.JsonValue;
import com.google.android.gms.fitness.FitnessActivities;
import org.json.JSONObject;

public class Console implements ChromeDevtoolsDomain {

    @SuppressLint({"UsingDefaultJsonDeserializer", "EmptyJsonPropertyUse"})
    public static class CallFrame {
        @JsonProperty(required = true)
        public int columnNumber;
        @JsonProperty(required = true)
        public String functionName;
        @JsonProperty(required = true)
        public int lineNumber;
        @JsonProperty(required = true)
        public String url;

        public CallFrame() {
        }

        public CallFrame(String functionName2, String url2, int lineNumber2, int columnNumber2) {
            this.functionName = functionName2;
            this.url = url2;
            this.lineNumber = lineNumber2;
            this.columnNumber = columnNumber2;
        }
    }

    @SuppressLint({"UsingDefaultJsonDeserializer", "EmptyJsonPropertyUse"})
    public static class ConsoleMessage {
        @JsonProperty(required = true)
        public MessageLevel level;
        @JsonProperty(required = true)
        public MessageSource source;
        @JsonProperty(required = true)
        public String text;
    }

    @SuppressLint({"UsingDefaultJsonDeserializer", "EmptyJsonPropertyUse"})
    public static class MessageAddedRequest {
        @JsonProperty(required = true)
        public ConsoleMessage message;
    }

    public enum MessageLevel {
        LOG("log"),
        WARNING("warning"),
        ERROR("error"),
        DEBUG("debug");
        
        private final String mProtocolValue;

        private MessageLevel(String protocolValue) {
            this.mProtocolValue = protocolValue;
        }

        @JsonValue
        public String getProtocolValue() {
            return this.mProtocolValue;
        }
    }

    public enum MessageSource {
        XML("xml"),
        JAVASCRIPT("javascript"),
        NETWORK("network"),
        CONSOLE_API("console-api"),
        STORAGE("storage"),
        APPCACHE("appcache"),
        RENDERING("rendering"),
        CSS("css"),
        SECURITY("security"),
        OTHER(FitnessActivities.OTHER);
        
        private final String mProtocolValue;

        private MessageSource(String protocolValue) {
            this.mProtocolValue = protocolValue;
        }

        @JsonValue
        public String getProtocolValue() {
            return this.mProtocolValue;
        }
    }

    @ChromeDevtoolsMethod
    public void enable(JsonRpcPeer peer, JSONObject params) {
        ConsolePeerManager.getOrCreateInstance().addPeer(peer);
    }

    @ChromeDevtoolsMethod
    public void disable(JsonRpcPeer peer, JSONObject params) {
        ConsolePeerManager.getOrCreateInstance().removePeer(peer);
    }
}
