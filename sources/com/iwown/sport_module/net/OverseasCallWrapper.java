package com.iwown.sport_module.net;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.iwown.data_link.base.NggReturnCode;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.sport_data.ReturnCode;
import com.iwown.sport_module.net.exception.ServerException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.socks.library.KLog;
import io.reactivex.observers.DisposableObserver;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.text.ParseException;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.json.JSONException;

public abstract class OverseasCallWrapper<T> extends DisposableObserver<T> {
    private static final int BAD_GATEWAY = 502;
    private static final int FORBIDDEN = 403;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int UNAUTHORIZED = 401;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    /* access modifiers changed from: protected */
    public abstract void onSuccess(T t) throws Exception;

    public void onNext(T t) {
        Class cls = t.getClass();
        if (t == null) {
            onError(new ServerException(-2));
            return;
        }
        try {
            if (t instanceof ResponseBody) {
                KLog.i("ResponseBody " + getStringByResponseBody((ResponseBody) t));
                onSuccess(t);
            } else if (t instanceof RetCode) {
                int retCode = ((RetCode) t).getRetCode();
                if (retCode == 0 || retCode == 10404) {
                    onSuccess(t);
                } else {
                    onError(new ServerException(retCode));
                }
            } else if (t instanceof NggReturnCode) {
                int retCode2 = ((NggReturnCode) t).getReturnCode();
                if (retCode2 == 0 || retCode2 == 10404) {
                    onSuccess(t);
                } else {
                    onError(new ServerException(retCode2));
                }
            } else if (t instanceof ReturnCode) {
                int retCode3 = ((ReturnCode) t).getReturnCode();
                if (retCode3 == 0 || retCode3 == 10404) {
                    onSuccess(t);
                } else {
                    onError(new ServerException(retCode3));
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            onError(e);
        }
    }

    public void onError(Throwable e) {
        String message = "";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            httpException.code();
            message = "NetWork Error " + httpException.code();
        } else if ((e instanceof JsonParseException) || (e instanceof JSONException) || (e instanceof ParseException) || (e instanceof MalformedJsonException)) {
            message = "DataParseException ";
        } else if (!(e instanceof ConnectException) && !(e instanceof SocketException) && !(e instanceof SocketTimeoutException) && !(e instanceof NullPointerException) && (e instanceof ServerException)) {
            message = ((ServerException) e).message();
        }
        if (!TextUtils.isEmpty(message)) {
        }
        KLog.e(message + "  " + e);
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
