package coms.mediatek.ctrl.epo;

import android.util.Log;
import com.google.common.net.HttpHeaders;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class a {
    private static HttpURLConnection a = null;

    public static InputStream a(String str) {
        Exception e;
        InputStream inputStream;
        IOException e2;
        MalformedURLException e3;
        InputStream inputStream2 = null;
        try {
            a = (HttpURLConnection) new URL(str).openConnection();
            a.setConnectTimeout(10000);
            a.setDoInput(true);
            a.setRequestMethod("GET");
            Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] conn.connect begin -- " + str);
            a.connect();
            Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] conn.connect end");
            Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] conn.getInputStream begin");
            InputStream inputStream3 = a.getInputStream();
            try {
                inputStream = new BufferedInputStream(inputStream3);
            } catch (MalformedURLException e4) {
                MalformedURLException malformedURLException = e4;
                inputStream = inputStream3;
                e3 = malformedURLException;
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] MalformedURLException : " + e3.getMessage());
                return inputStream;
            } catch (IOException e5) {
                IOException iOException = e5;
                inputStream = inputStream3;
                e2 = iOException;
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] IOException : " + e2.getMessage());
                return inputStream;
            } catch (Exception e6) {
                Exception exc = e6;
                inputStream = inputStream3;
                e = exc;
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] Exception : " + e.getMessage());
                return inputStream;
            }
            try {
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] conn.getInputStream end");
            } catch (MalformedURLException e7) {
                e3 = e7;
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] MalformedURLException : " + e3.getMessage());
                return inputStream;
            } catch (IOException e8) {
                e2 = e8;
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] IOException : " + e2.getMessage());
                return inputStream;
            } catch (Exception e9) {
                e = e9;
                Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] Exception : " + e.getMessage());
                return inputStream;
            }
        } catch (MalformedURLException e10) {
            MalformedURLException malformedURLException2 = e10;
            inputStream = inputStream2;
            e3 = malformedURLException2;
        } catch (IOException e11) {
            IOException iOException2 = e11;
            inputStream = inputStream2;
            e2 = iOException2;
            Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] IOException : " + e2.getMessage());
            return inputStream;
        } catch (Exception e12) {
            Exception exc2 = e12;
            inputStream = inputStream2;
            e = exc2;
            Log.d("[AppStore]HttpHelper", "[getInputStreamFromURL] Exception : " + e.getMessage());
            return inputStream;
        }
        return inputStream;
    }

    public static String a() {
        String str = "";
        if (a != null) {
            str = a.getHeaderField(HttpHeaders.ETAG).replaceAll("^\"|\"$", "");
        } else {
            Log.d("[AppStore]HttpHelper", "mConn is null");
        }
        Log.d("[AppStore]HttpHelper", "md5=" + str);
        return str;
    }
}
