package com.iwown.device_module.common.network;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.iwown.device_module.common.network.data.resp.RetCode;
import com.iwown.device_module.common.network.data.resp.ReturnCode;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.socks.library.KLog;
import io.reactivex.observers.DisposableObserver;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.text.ParseException;
import javax.net.ssl.SSLHandshakeException;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.json.JSONException;

public abstract class CallbackWrapper<T> extends DisposableObserver<T> {
    private static final int BAD_GATEWAY = 502;
    private static final int FORBIDDEN = 403;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int UNAUTHORIZED = 401;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private boolean mIsShowToast = false;

    /* access modifiers changed from: protected */
    public abstract void onSuccess(T t) throws Exception;

    public CallbackWrapper() {
    }

    public CallbackWrapper(boolean isShowToast) {
        this.mIsShowToast = isShowToast;
    }

    /* access modifiers changed from: protected */
    public void onError1(Throwable e) {
    }

    public void onNext(T t) {
        int retCode;
        Class c = t.getClass();
        try {
            if (t instanceof ResponseBody) {
                KLog.debug("ResponseBody " + getStringByResponseBody((ResponseBody) t));
                onSuccess(t);
                return;
            }
            if (t instanceof RetCode) {
                retCode = ((RetCode) t).getRetCode();
                if (!(retCode == 0 || retCode == 60001 || retCode == 10404)) {
                    String message = ((RetCode) t).getMessage();
                    if (TextUtils.isEmpty(message)) {
                        onError(new ServerException(retCode));
                        return;
                    } else {
                        onError(new ServerException(retCode, message));
                        return;
                    }
                }
            } else if (t instanceof ReturnCode) {
                retCode = ((ReturnCode) t).getReturnCode();
                if (!(retCode == 0 || retCode == 60001 || retCode == 10404)) {
                    String message2 = ((RetCode) t).getMessage();
                    if (TextUtils.isEmpty(message2)) {
                        onError(new ServerException(retCode));
                        return;
                    } else {
                        onError(new ServerException(retCode, message2));
                        return;
                    }
                }
            } else {
                String name = "retCode";
                T t2 = t;
                retCode = ((Integer) c.getMethod("get" + (name.substring(0, 1).toUpperCase() + name.substring(1)), new Class[0]).invoke(t2, new Object[0])).intValue();
            }
            if (retCode == 0 || retCode == 60001 || retCode == 10404) {
                try {
                    onSuccess(t);
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                    onError(e);
                }
            } else {
                onError(new ServerException(retCode));
            }
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            onError(new ServerException(-5, e2.getMessage()));
        }
    }

    public void onError(Throwable e) {
        String str = "";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            httpException.code();
            String message = "网络错误 " + httpException.code();
        } else if (e instanceof ServerException) {
            String message2 = ((ServerException) e).msg;
        } else if ((e instanceof JsonParseException) || (e instanceof JSONException) || (e instanceof ParseException) || (e instanceof MalformedJsonException)) {
            String message3 = "数据解析异常 ";
        } else if ((e instanceof ConnectException) || (e instanceof SocketTimeoutException) || (e instanceof SocketException)) {
            String message4 = "连接服务器失败";
        } else if (e instanceof SSLHandshakeException) {
            String message5 = "证书验证失败";
        } else if (e instanceof NullPointerException) {
            String message6 = "数据解析异常Null";
        } else {
            String message7 = "error " + e.getMessage();
        }
        if (this.mIsShowToast) {
        }
        onError1(e);
    }

    public void onComplete() {
    }

    private String getStringByResponseBody(ResponseBody responseBody) throws IOException {
        Charset charset = UTF8;
        BufferedSource source = responseBody.source();
        source.request(LongCompanionObject.MAX_VALUE);
        Buffer buffer = source.buffer();
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        return buffer.clone().readString(charset);
    }
}
