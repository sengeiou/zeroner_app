package com.iwown.my_module.utility;

import android.util.Log;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class HttpLogUtils implements Interceptor {
    private static final String TAG = "iwown_network";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private volatile Level level;
    private final Logger logger;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY,
        CUSTOM
    }

    public interface Logger {
        public static final Logger DEFAULT = new Logger() {
            public void log(String message) {
                Log.i(HttpLogUtils.TAG, message);
            }
        };

        void log(String str);
    }

    public HttpLogUtils() {
        this(Logger.DEFAULT);
    }

    public HttpLogUtils(Logger logger2) {
        this.level = Level.NONE;
        this.logger = logger2;
    }

    public HttpLogUtils setLevel(Level level2) {
        if (level2 == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level2;
        return this;
    }

    public Level getLevel() {
        return this.level;
    }

    public Response intercept(Chain chain) throws IOException {
        Level level2 = this.level;
        Request request = chain.request();
        if (level2 == Level.NONE) {
            return chain.proceed(request);
        }
        boolean logBody = level2 == Level.BODY;
        boolean logHeaders = logBody || level2 == Level.HEADERS;
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Connection connection = chain.connection();
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + (connection != null ? connection.protocol() : Protocol.HTTP_1_1);
        if (!logHeaders && hasRequestBody && level2 != Level.CUSTOM) {
            this.logger.log(requestStartMessage + " (" + requestBody.contentLength() + "-byte body)");
        }
        if (level2 == Level.CUSTOM && hasRequestBody) {
            String req = "请求方法:" + request.method() + "  请求链接:" + request.url();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            this.logger.log(req);
            this.logger.log("请求body:" + buffer.readString(charset));
        }
        if (logHeaders) {
            if (hasRequestBody) {
                if (requestBody.contentType() != null) {
                    this.logger.log("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    this.logger.log("Content-Length: " + requestBody.contentLength());
                }
            }
            Headers headers = request.headers();
            int count = headers.size();
            for (int i = 0; i < count; i++) {
                String name = headers.name(i);
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    this.logger.log(name + ": " + headers.value(i));
                }
            }
            if (!logBody || !hasRequestBody) {
                this.logger.log("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                this.logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer2 = new Buffer();
                requestBody.writeTo(buffer2);
                Charset charset2 = UTF8;
                MediaType contentType2 = requestBody.contentType();
                if (contentType2 != null) {
                    charset2 = contentType2.charset(UTF8);
                }
                this.logger.log("");
                this.logger.log(buffer2.readString(charset2));
                this.logger.log("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
            }
        }
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        if (contentLength != -1) {
            String str = contentLength + "-byte";
        } else {
            String str2 = "unknown-length";
        }
        if (level2 == Level.CUSTOM) {
            String req2 = "返回code:" + response.code() + "  返回body:" + response.message() + "  请求的url:" + response.request().url();
            BufferedSource source = responseBody.source();
            source.request(LongCompanionObject.MAX_VALUE);
            Buffer buffer3 = source.buffer();
            Charset charset3 = UTF8;
            MediaType contentType3 = responseBody.contentType();
            if (contentType3 != null) {
                try {
                    charset3 = contentType3.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }
            if (contentLength != 0) {
                this.logger.log(req2);
                this.logger.log("返回body:" + buffer3.clone().readString(charset3));
            }
        }
        if (!logHeaders) {
            return response;
        }
        Headers headers2 = response.headers();
        int count2 = headers2.size();
        for (int i2 = 0; i2 < count2; i2++) {
            this.logger.log(headers2.name(i2) + ": " + headers2.value(i2));
        }
        if (!logBody) {
            this.logger.log("<-- END HTTP");
            return response;
        } else if (bodyEncoded(response.headers())) {
            this.logger.log("<-- END HTTP (encoded body omitted)");
            return response;
        } else {
            BufferedSource source2 = responseBody.source();
            source2.request(LongCompanionObject.MAX_VALUE);
            Buffer buffer4 = source2.buffer();
            Charset charset4 = UTF8;
            MediaType contentType4 = responseBody.contentType();
            if (contentType4 != null) {
                try {
                    charset4 = contentType4.charset(UTF8);
                } catch (UnsupportedCharsetException e2) {
                    this.logger.log("");
                    this.logger.log("Couldn't decode the response body; charset is likely malformed.");
                    this.logger.log("<-- END HTTP");
                    return response;
                }
            }
            if (contentLength != 0) {
                this.logger.log("");
                this.logger.log(buffer4.clone().readString(charset4));
            }
            this.logger.log("<-- END HTTP (" + buffer4.size() + "-byte body)");
            return response;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get(HttpHeaders.CONTENT_ENCODING);
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
