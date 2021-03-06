package com.facebook.stetho.inspector;

import com.facebook.stetho.common.LogRedirector;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.jsonrpc.PendingRequest;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcRequest;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcResponse;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.json.ObjectMapper;
import com.facebook.stetho.websocket.SimpleEndpoint;
import com.facebook.stetho.websocket.SimpleSession;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ChromeDevtoolsServer implements SimpleEndpoint {
    public static final String PATH = "/inspector";
    private static final String TAG = "ChromeDevtoolsServer";
    private final MethodDispatcher mMethodDispatcher;
    private final ObjectMapper mObjectMapper = new ObjectMapper();
    private final Map<SimpleSession, JsonRpcPeer> mPeers = Collections.synchronizedMap(new HashMap());

    public ChromeDevtoolsServer(Iterable<ChromeDevtoolsDomain> domainModules) {
        this.mMethodDispatcher = new MethodDispatcher(this.mObjectMapper, domainModules);
    }

    public void onOpen(SimpleSession session) {
        LogRedirector.d(TAG, "onOpen");
        this.mPeers.put(session, new JsonRpcPeer(this.mObjectMapper, session));
    }

    public void onClose(SimpleSession session, int code, String reasonPhrase) {
        LogRedirector.d(TAG, "onClose: reason=" + code + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + reasonPhrase);
        JsonRpcPeer peer = (JsonRpcPeer) this.mPeers.remove(session);
        if (peer != null) {
            peer.invokeDisconnectReceivers();
        }
    }

    public void onMessage(SimpleSession session, byte[] message, int messageLen) {
        LogRedirector.d(TAG, "Ignoring binary message of length " + messageLen);
    }

    public void onMessage(SimpleSession session, String message) {
        if (LogRedirector.isLoggable(TAG, 2)) {
            LogRedirector.v(TAG, "onMessage: message=" + message);
        }
        try {
            JsonRpcPeer peer = (JsonRpcPeer) this.mPeers.get(session);
            Util.throwIfNull(peer);
            handleRemoteMessage(peer, message);
        } catch (IOException e) {
            if (LogRedirector.isLoggable(TAG, 2)) {
                LogRedirector.v(TAG, "Unexpected I/O exception processing message: " + e);
            }
            closeSafely(session, 1011, e.getClass().getSimpleName());
        } catch (MessageHandlingException e2) {
            LogRedirector.i(TAG, "Message could not be processed by implementation: " + e2);
            closeSafely(session, 1011, e2.getClass().getSimpleName());
        } catch (JSONException e3) {
            LogRedirector.v(TAG, "Unexpected JSON exception processing message", e3);
            closeSafely(session, 1011, e3.getClass().getSimpleName());
        }
    }

    private void closeSafely(SimpleSession session, int code, String reasonPhrase) {
        session.close(code, reasonPhrase);
    }

    private void handleRemoteMessage(JsonRpcPeer peer, String message) throws IOException, MessageHandlingException, JSONException {
        JSONObject messageNode = new JSONObject(message);
        if (messageNode.has("method")) {
            handleRemoteRequest(peer, messageNode);
        } else if (messageNode.has("result")) {
            handleRemoteResponse(peer, messageNode);
        } else {
            throw new MessageHandlingException("Improper JSON-RPC message: " + message);
        }
    }

    private void handleRemoteRequest(JsonRpcPeer peer, JSONObject requestNode) throws MessageHandlingException {
        String responseString;
        JsonRpcRequest request = (JsonRpcRequest) this.mObjectMapper.convertValue(requestNode, JsonRpcRequest.class);
        JSONObject result = null;
        JSONObject error = null;
        try {
            result = this.mMethodDispatcher.dispatch(peer, request.method, request.params);
        } catch (JsonRpcException e) {
            logDispatchException(e);
            error = (JSONObject) this.mObjectMapper.convertValue(e.getErrorMessage(), JSONObject.class);
        }
        if (request.id != null) {
            JsonRpcResponse response = new JsonRpcResponse();
            response.id = request.id.longValue();
            response.result = result;
            response.error = error;
            try {
                responseString = ((JSONObject) this.mObjectMapper.convertValue(response, JSONObject.class)).toString();
            } catch (OutOfMemoryError e2) {
                response.result = null;
                response.error = (JSONObject) this.mObjectMapper.convertValue(e2.getMessage(), JSONObject.class);
                responseString = ((JSONObject) this.mObjectMapper.convertValue(response, JSONObject.class)).toString();
            }
            peer.getWebSocket().sendText(responseString);
        }
    }

    private static void logDispatchException(JsonRpcException e) {
        switch (e.getErrorMessage().code) {
            case METHOD_NOT_FOUND:
                LogRedirector.d(TAG, "Method not implemented: " + e.getErrorMessage().message);
                return;
            default:
                LogRedirector.w(TAG, "Error processing remote message", e);
                return;
        }
    }

    private void handleRemoteResponse(JsonRpcPeer peer, JSONObject responseNode) throws MismatchedResponseException {
        JsonRpcResponse response = (JsonRpcResponse) this.mObjectMapper.convertValue(responseNode, JsonRpcResponse.class);
        PendingRequest pendingRequest = peer.getAndRemovePendingRequest(response.id);
        if (pendingRequest == null) {
            throw new MismatchedResponseException(response.id);
        } else if (pendingRequest.callback != null) {
            pendingRequest.callback.onResponse(peer, response);
        }
    }

    public void onError(SimpleSession session, Throwable ex) {
        LogRedirector.e(TAG, "onError: ex=" + ex.toString());
    }
}
