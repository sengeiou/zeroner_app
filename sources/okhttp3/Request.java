package okhttp3;

import com.google.api.client.http.HttpMethods;
import com.google.common.net.HttpHeaders;
import java.net.URL;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

public final class Request {
    @Nullable
    final RequestBody body;
    private volatile CacheControl cacheControl;
    final Headers headers;
    final String method;
    final Object tag;
    final HttpUrl url;

    public static class Builder {
        RequestBody body;
        okhttp3.Headers.Builder headers;
        String method;
        Object tag;
        HttpUrl url;

        public Builder() {
            this.method = "GET";
            this.headers = new okhttp3.Headers.Builder();
        }

        Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder url(HttpUrl url2) {
            if (url2 == null) {
                throw new NullPointerException("url == null");
            }
            this.url = url2;
            return this;
        }

        public Builder url(String url2) {
            if (url2 == null) {
                throw new NullPointerException("url == null");
            }
            if (url2.regionMatches(true, 0, "ws:", 0, 3)) {
                url2 = "http:" + url2.substring(3);
            } else {
                if (url2.regionMatches(true, 0, "wss:", 0, 4)) {
                    url2 = "https:" + url2.substring(4);
                }
            }
            HttpUrl parsed = HttpUrl.parse(url2);
            if (parsed != null) {
                return url(parsed);
            }
            throw new IllegalArgumentException("unexpected url: " + url2);
        }

        public Builder url(URL url2) {
            if (url2 == null) {
                throw new NullPointerException("url == null");
            }
            HttpUrl parsed = HttpUrl.get(url2);
            if (parsed != null) {
                return url(parsed);
            }
            throw new IllegalArgumentException("unexpected url: " + url2);
        }

        public Builder header(String name, String value) {
            this.headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            this.headers.removeAll(name);
            return this;
        }

        public Builder headers(Headers headers2) {
            this.headers = headers2.newBuilder();
            return this;
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String value = cacheControl.toString();
            if (value.isEmpty()) {
                return removeHeader(HttpHeaders.CACHE_CONTROL);
            }
            return header(HttpHeaders.CACHE_CONTROL, value);
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method(HttpMethods.HEAD, null);
        }

        public Builder post(RequestBody body2) {
            return method("POST", body2);
        }

        public Builder delete(@Nullable RequestBody body2) {
            return method(HttpMethods.DELETE, body2);
        }

        public Builder delete() {
            return delete(Util.EMPTY_REQUEST);
        }

        public Builder put(RequestBody body2) {
            return method(HttpMethods.PUT, body2);
        }

        public Builder patch(RequestBody body2) {
            return method("PATCH", body2);
        }

        public Builder method(String method2, @Nullable RequestBody body2) {
            if (method2 == null) {
                throw new NullPointerException("method == null");
            } else if (method2.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
            } else if (body2 != null && !HttpMethod.permitsRequestBody(method2)) {
                throw new IllegalArgumentException("method " + method2 + " must not have a request body.");
            } else if (body2 != null || !HttpMethod.requiresRequestBody(method2)) {
                this.method = method2;
                this.body = body2;
                return this;
            } else {
                throw new IllegalArgumentException("method " + method2 + " must have a request body.");
            }
        }

        public Builder tag(Object tag2) {
            this.tag = tag2;
            return this;
        }

        public Request build() {
            if (this.url != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }
    }

    Request(Builder builder) {
        Object obj;
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        if (builder.tag != null) {
            obj = builder.tag;
        } else {
            obj = this;
        }
        this.tag = obj;
    }

    public HttpUrl url() {
        return this.url;
    }

    public String method() {
        return this.method;
    }

    public Headers headers() {
        return this.headers;
    }

    @Nullable
    public String header(String name) {
        return this.headers.get(name);
    }

    public List<String> headers(String name) {
        return this.headers.values(name);
    }

    @Nullable
    public RequestBody body() {
        return this.body;
    }

    public Object tag() {
        return this.tag;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public CacheControl cacheControl() {
        CacheControl result = this.cacheControl;
        if (result != null) {
            return result;
        }
        CacheControl result2 = CacheControl.parse(this.headers);
        this.cacheControl = result2;
        return result2;
    }

    public boolean isHttps() {
        return this.url.isHttps();
    }

    public String toString() {
        return "Request{method=" + this.method + ", url=" + this.url + ", tag=" + (this.tag != this ? this.tag : null) + '}';
    }
}
