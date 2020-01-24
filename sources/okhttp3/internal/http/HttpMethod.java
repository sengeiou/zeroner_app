package okhttp3.internal.http;

import com.google.api.client.http.HttpMethods;

public final class HttpMethod {
    public static boolean invalidatesCache(String method) {
        return method.equals("POST") || method.equals("PATCH") || method.equals(HttpMethods.PUT) || method.equals(HttpMethods.DELETE) || method.equals("MOVE");
    }

    public static boolean requiresRequestBody(String method) {
        return method.equals("POST") || method.equals(HttpMethods.PUT) || method.equals("PATCH") || method.equals("PROPPATCH") || method.equals("REPORT");
    }

    public static boolean permitsRequestBody(String method) {
        return !method.equals("GET") && !method.equals(HttpMethods.HEAD);
    }

    public static boolean redirectsWithBody(String method) {
        return method.equals("PROPFIND");
    }

    public static boolean redirectsToGet(String method) {
        return !method.equals("PROPFIND");
    }

    private HttpMethod() {
    }
}
