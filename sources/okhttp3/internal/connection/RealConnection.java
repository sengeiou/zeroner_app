package okhttp3.internal.connection;

import io.reactivex.annotations.SchedulerSupport;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http1.Http1Codec;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Codec;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Connection.Builder;
import okhttp3.internal.http2.Http2Connection.Listener;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket.Streams;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public final class RealConnection extends Listener implements Connection {
    private static final int MAX_TUNNEL_ATTEMPTS = 21;
    private static final String NPE_THROW_WITH_NULL = "throw with null exception";
    public int allocationLimit = 1;
    public final List<Reference<StreamAllocation>> allocations = new ArrayList();
    private final ConnectionPool connectionPool;
    private Handshake handshake;
    private Http2Connection http2Connection;
    public long idleAtNanos = LongCompanionObject.MAX_VALUE;
    public boolean noNewStreams;
    private Protocol protocol;
    private Socket rawSocket;
    private final Route route;
    private BufferedSink sink;
    private Socket socket;
    private BufferedSource source;
    public int successCount;

    public RealConnection(ConnectionPool connectionPool2, Route route2) {
        this.connectionPool = connectionPool2;
        this.route = route2;
    }

    public static RealConnection testConnection(ConnectionPool connectionPool2, Route route2, Socket socket2, long idleAtNanos2) {
        RealConnection result = new RealConnection(connectionPool2, route2);
        result.socket = socket2;
        result.idleAtNanos = idleAtNanos2;
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0094, code lost:
        if (r14.rawSocket == null) goto L_0x0096;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(int r15, int r16, int r17, int r18, boolean r19, okhttp3.Call r20, okhttp3.EventListener r21) {
        /*
            r14 = this;
            okhttp3.Protocol r3 = r14.protocol
            if (r3 == 0) goto L_0x000d
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "already connected"
            r3.<init>(r4)
            throw r3
        L_0x000d:
            r13 = 0
            okhttp3.Route r3 = r14.route
            okhttp3.Address r3 = r3.address()
            java.util.List r10 = r3.connectionSpecs()
            okhttp3.internal.connection.ConnectionSpecSelector r9 = new okhttp3.internal.connection.ConnectionSpecSelector
            r9.<init>(r10)
            okhttp3.Route r3 = r14.route
            okhttp3.Address r3 = r3.address()
            javax.net.ssl.SSLSocketFactory r3 = r3.sslSocketFactory()
            if (r3 != 0) goto L_0x007d
            okhttp3.ConnectionSpec r3 = okhttp3.ConnectionSpec.CLEARTEXT
            boolean r3 = r10.contains(r3)
            if (r3 != 0) goto L_0x003f
            okhttp3.internal.connection.RouteException r3 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r4 = new java.net.UnknownServiceException
            java.lang.String r5 = "CLEARTEXT communication not enabled for client"
            r4.<init>(r5)
            r3.<init>(r4)
            throw r3
        L_0x003f:
            okhttp3.Route r3 = r14.route
            okhttp3.Address r3 = r3.address()
            okhttp3.HttpUrl r3 = r3.url()
            java.lang.String r12 = r3.host()
            okhttp3.internal.platform.Platform r3 = okhttp3.internal.platform.Platform.get()
            boolean r3 = r3.isCleartextTrafficPermitted(r12)
            if (r3 != 0) goto L_0x007d
            okhttp3.internal.connection.RouteException r3 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r4 = new java.net.UnknownServiceException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "CLEARTEXT communication to "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r12)
            java.lang.String r6 = " not permitted by network security policy"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            r3.<init>(r4)
            throw r3
        L_0x007d:
            okhttp3.Route r3 = r14.route     // Catch:{ IOException -> 0x00d8 }
            boolean r3 = r3.requiresTunnel()     // Catch:{ IOException -> 0x00d8 }
            if (r3 == 0) goto L_0x00b0
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r7 = r20
            r8 = r21
            r3.connectTunnel(r4, r5, r6, r7, r8)     // Catch:{ IOException -> 0x00d8 }
            java.net.Socket r3 = r14.rawSocket     // Catch:{ IOException -> 0x00d8 }
            if (r3 != 0) goto L_0x00b9
        L_0x0096:
            okhttp3.Route r3 = r14.route
            boolean r3 = r3.requiresTunnel()
            if (r3 == 0) goto L_0x0120
            java.net.Socket r3 = r14.rawSocket
            if (r3 != 0) goto L_0x0120
            java.net.ProtocolException r11 = new java.net.ProtocolException
            java.lang.String r3 = "Too many tunnel connections attempted: 21"
            r11.<init>(r3)
            okhttp3.internal.connection.RouteException r3 = new okhttp3.internal.connection.RouteException
            r3.<init>(r11)
            throw r3
        L_0x00b0:
            r0 = r16
            r1 = r20
            r2 = r21
            r14.connectSocket(r15, r0, r1, r2)     // Catch:{ IOException -> 0x00d8 }
        L_0x00b9:
            r0 = r18
            r1 = r20
            r2 = r21
            r14.establishProtocol(r9, r0, r1, r2)     // Catch:{ IOException -> 0x00d8 }
            okhttp3.Route r3 = r14.route     // Catch:{ IOException -> 0x00d8 }
            java.net.InetSocketAddress r3 = r3.socketAddress()     // Catch:{ IOException -> 0x00d8 }
            okhttp3.Route r4 = r14.route     // Catch:{ IOException -> 0x00d8 }
            java.net.Proxy r4 = r4.proxy()     // Catch:{ IOException -> 0x00d8 }
            okhttp3.Protocol r5 = r14.protocol     // Catch:{ IOException -> 0x00d8 }
            r0 = r21
            r1 = r20
            r0.connectEnd(r1, r3, r4, r5)     // Catch:{ IOException -> 0x00d8 }
            goto L_0x0096
        L_0x00d8:
            r8 = move-exception
            java.net.Socket r3 = r14.socket
            okhttp3.internal.Util.closeQuietly(r3)
            java.net.Socket r3 = r14.rawSocket
            okhttp3.internal.Util.closeQuietly(r3)
            r3 = 0
            r14.socket = r3
            r3 = 0
            r14.rawSocket = r3
            r3 = 0
            r14.source = r3
            r3 = 0
            r14.sink = r3
            r3 = 0
            r14.handshake = r3
            r3 = 0
            r14.protocol = r3
            r3 = 0
            r14.http2Connection = r3
            okhttp3.Route r3 = r14.route
            java.net.InetSocketAddress r5 = r3.socketAddress()
            okhttp3.Route r3 = r14.route
            java.net.Proxy r6 = r3.proxy()
            r7 = 0
            r3 = r21
            r4 = r20
            r3.connectFailed(r4, r5, r6, r7, r8)
            if (r13 != 0) goto L_0x011c
            okhttp3.internal.connection.RouteException r13 = new okhttp3.internal.connection.RouteException
            r13.<init>(r8)
        L_0x0113:
            if (r19 == 0) goto L_0x011b
            boolean r3 = r9.connectionFailed(r8)
            if (r3 != 0) goto L_0x007d
        L_0x011b:
            throw r13
        L_0x011c:
            r13.addConnectException(r8)
            goto L_0x0113
        L_0x0120:
            okhttp3.internal.http2.Http2Connection r3 = r14.http2Connection
            if (r3 == 0) goto L_0x0130
            okhttp3.ConnectionPool r4 = r14.connectionPool
            monitor-enter(r4)
            okhttp3.internal.http2.Http2Connection r3 = r14.http2Connection     // Catch:{ all -> 0x0131 }
            int r3 = r3.maxConcurrentStreams()     // Catch:{ all -> 0x0131 }
            r14.allocationLimit = r3     // Catch:{ all -> 0x0131 }
            monitor-exit(r4)     // Catch:{ all -> 0x0131 }
        L_0x0130:
            return
        L_0x0131:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0131 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealConnection.connect(int, int, int, int, boolean, okhttp3.Call, okhttp3.EventListener):void");
    }

    private void connectTunnel(int connectTimeout, int readTimeout, int writeTimeout, Call call, EventListener eventListener) throws IOException {
        Request tunnelRequest = createTunnelRequest();
        HttpUrl url = tunnelRequest.url();
        int i = 0;
        while (i < 21) {
            connectSocket(connectTimeout, readTimeout, call, eventListener);
            tunnelRequest = createTunnel(readTimeout, writeTimeout, tunnelRequest, url);
            if (tunnelRequest != null) {
                Util.closeQuietly(this.rawSocket);
                this.rawSocket = null;
                this.sink = null;
                this.source = null;
                eventListener.connectEnd(call, this.route.socketAddress(), this.route.proxy(), null);
                i++;
            } else {
                return;
            }
        }
    }

    private void connectSocket(int connectTimeout, int readTimeout, Call call, EventListener eventListener) throws IOException {
        Socket socket2;
        Proxy proxy = this.route.proxy();
        Address address = this.route.address();
        if (proxy.type() == Type.DIRECT || proxy.type() == Type.HTTP) {
            socket2 = address.socketFactory().createSocket();
        } else {
            socket2 = new Socket(proxy);
        }
        this.rawSocket = socket2;
        eventListener.connectStart(call, this.route.socketAddress(), proxy);
        this.rawSocket.setSoTimeout(readTimeout);
        try {
            Platform.get().connectSocket(this.rawSocket, this.route.socketAddress(), connectTimeout);
            try {
                this.source = Okio.buffer(Okio.source(this.rawSocket));
                this.sink = Okio.buffer(Okio.sink(this.rawSocket));
            } catch (NullPointerException npe) {
                if (NPE_THROW_WITH_NULL.equals(npe.getMessage())) {
                    throw new IOException(npe);
                }
            }
        } catch (ConnectException e) {
            ConnectException ce = new ConnectException("Failed to connect to " + this.route.socketAddress());
            ce.initCause(e);
            throw ce;
        }
    }

    private void establishProtocol(ConnectionSpecSelector connectionSpecSelector, int pingIntervalMillis, Call call, EventListener eventListener) throws IOException {
        if (this.route.address().sslSocketFactory() == null) {
            this.protocol = Protocol.HTTP_1_1;
            this.socket = this.rawSocket;
            return;
        }
        eventListener.secureConnectStart(call);
        connectTls(connectionSpecSelector);
        eventListener.secureConnectEnd(call, this.handshake);
        if (this.protocol == Protocol.HTTP_2) {
            this.socket.setSoTimeout(0);
            this.http2Connection = new Builder(true).socket(this.socket, this.route.address().url().host(), this.source, this.sink).listener(this).pingIntervalMillis(pingIntervalMillis).build();
            this.http2Connection.start();
        }
    }

    private void connectTls(ConnectionSpecSelector connectionSpecSelector) throws IOException {
        String maybeProtocol;
        Protocol protocol2;
        Address address = this.route.address();
        SSLSocket sslSocket = null;
        try {
            sslSocket = (SSLSocket) address.sslSocketFactory().createSocket(this.rawSocket, address.url().host(), address.url().port(), true);
            ConnectionSpec connectionSpec = connectionSpecSelector.configureSecureSocket(sslSocket);
            if (connectionSpec.supportsTlsExtensions()) {
                Platform.get().configureTlsExtensions(sslSocket, address.url().host(), address.protocols());
            }
            sslSocket.startHandshake();
            SSLSession sslSocketSession = sslSocket.getSession();
            if (!isValid(sslSocketSession)) {
                throw new IOException("a valid ssl session was not established");
            }
            Handshake unverifiedHandshake = Handshake.get(sslSocketSession);
            if (!address.hostnameVerifier().verify(address.url().host(), sslSocketSession)) {
                X509Certificate cert = (X509Certificate) unverifiedHandshake.peerCertificates().get(0);
                throw new SSLPeerUnverifiedException("Hostname " + address.url().host() + " not verified:\n    certificate: " + CertificatePinner.pin(cert) + "\n    DN: " + cert.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(cert));
            }
            address.certificatePinner().check(address.url().host(), unverifiedHandshake.peerCertificates());
            if (connectionSpec.supportsTlsExtensions()) {
                maybeProtocol = Platform.get().getSelectedProtocol(sslSocket);
            } else {
                maybeProtocol = null;
            }
            this.socket = sslSocket;
            this.source = Okio.buffer(Okio.source(this.socket));
            this.sink = Okio.buffer(Okio.sink(this.socket));
            this.handshake = unverifiedHandshake;
            if (maybeProtocol != null) {
                protocol2 = Protocol.get(maybeProtocol);
            } else {
                protocol2 = Protocol.HTTP_1_1;
            }
            this.protocol = protocol2;
            if (sslSocket != null) {
                Platform.get().afterHandshake(sslSocket);
            }
            if (1 == 0) {
                Util.closeQuietly((Socket) sslSocket);
            }
        } catch (AssertionError e) {
            if (Util.isAndroidGetsocknameError(e)) {
                throw new IOException(e);
            }
            throw e;
        } catch (Throwable th) {
            if (sslSocket != null) {
                Platform.get().afterHandshake(sslSocket);
            }
            if (0 == 0) {
                Util.closeQuietly((Socket) sslSocket);
            }
            throw th;
        }
    }

    private boolean isValid(SSLSession sslSocketSession) {
        return !"NONE".equals(sslSocketSession.getProtocol()) && !"SSL_NULL_WITH_NULL_NULL".equals(sslSocketSession.getCipherSuite());
    }

    private Request createTunnel(int readTimeout, int writeTimeout, Request tunnelRequest, HttpUrl url) throws IOException {
        Response response;
        String requestLine = "CONNECT " + Util.hostHeader(url, true) + " HTTP/1.1";
        do {
            Http1Codec tunnelConnection = new Http1Codec(null, null, this.source, this.sink);
            this.source.timeout().timeout((long) readTimeout, TimeUnit.MILLISECONDS);
            this.sink.timeout().timeout((long) writeTimeout, TimeUnit.MILLISECONDS);
            tunnelConnection.writeRequest(tunnelRequest.headers(), requestLine);
            tunnelConnection.finishRequest();
            response = tunnelConnection.readResponseHeaders(false).request(tunnelRequest).build();
            long contentLength = HttpHeaders.contentLength(response);
            if (contentLength == -1) {
                contentLength = 0;
            }
            Source body = tunnelConnection.newFixedLengthSource(contentLength);
            Util.skipAll(body, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            body.close();
            switch (response.code()) {
                case 200:
                    if (this.source.buffer().exhausted() && this.sink.buffer().exhausted()) {
                        return null;
                    }
                    throw new IOException("TLS tunnel buffered too many bytes!");
                case 407:
                    tunnelRequest = this.route.address().proxyAuthenticator().authenticate(this.route, response);
                    if (tunnelRequest != null) {
                        break;
                    } else {
                        throw new IOException("Failed to authenticate with proxy");
                    }
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + response.code());
            }
        } while (!"close".equalsIgnoreCase(response.header(com.google.common.net.HttpHeaders.CONNECTION)));
        return tunnelRequest;
    }

    private Request createTunnelRequest() {
        return new Request.Builder().url(this.route.address().url()).header(com.google.common.net.HttpHeaders.HOST, Util.hostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header(com.google.common.net.HttpHeaders.USER_AGENT, Version.userAgent()).build();
    }

    public boolean isEligible(Address address, @Nullable Route route2) {
        if (this.allocations.size() >= this.allocationLimit || this.noNewStreams || !Internal.instance.equalsNonHost(this.route.address(), address)) {
            return false;
        }
        if (address.url().host().equals(route().address().url().host())) {
            return true;
        }
        if (this.http2Connection == null || route2 == null || route2.proxy().type() != Type.DIRECT || this.route.proxy().type() != Type.DIRECT || !this.route.socketAddress().equals(route2.socketAddress()) || route2.address().hostnameVerifier() != OkHostnameVerifier.INSTANCE || !supportsUrl(address.url())) {
            return false;
        }
        try {
            address.certificatePinner().check(address.url().host(), handshake().peerCertificates());
            return true;
        } catch (SSLPeerUnverifiedException e) {
            return false;
        }
    }

    public boolean supportsUrl(HttpUrl url) {
        if (url.port() != this.route.address().url().port()) {
            return false;
        }
        if (url.host().equals(this.route.address().url().host())) {
            return true;
        }
        return this.handshake != null && OkHostnameVerifier.INSTANCE.verify(url.host(), (X509Certificate) this.handshake.peerCertificates().get(0));
    }

    public HttpCodec newCodec(OkHttpClient client, Chain chain, StreamAllocation streamAllocation) throws SocketException {
        if (this.http2Connection != null) {
            return new Http2Codec(client, chain, streamAllocation, this.http2Connection);
        }
        this.socket.setSoTimeout(chain.readTimeoutMillis());
        this.source.timeout().timeout((long) chain.readTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.sink.timeout().timeout((long) chain.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        return new Http1Codec(client, streamAllocation, this.source, this.sink);
    }

    public Streams newWebSocketStreams(StreamAllocation streamAllocation) {
        final StreamAllocation streamAllocation2 = streamAllocation;
        return new Streams(true, this.source, this.sink) {
            public void close() throws IOException {
                streamAllocation2.streamFinished(true, streamAllocation2.codec(), -1, null);
            }
        };
    }

    public Route route() {
        return this.route;
    }

    public void cancel() {
        Util.closeQuietly(this.rawSocket);
    }

    public Socket socket() {
        return this.socket;
    }

    public boolean isHealthy(boolean doExtensiveChecks) {
        int readTimeout;
        if (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) {
            return false;
        }
        if (this.http2Connection != null) {
            if (this.http2Connection.isShutdown()) {
                return false;
            }
            return true;
        } else if (!doExtensiveChecks) {
            return true;
        } else {
            try {
                readTimeout = this.socket.getSoTimeout();
                this.socket.setSoTimeout(1);
                if (this.source.exhausted()) {
                    this.socket.setSoTimeout(readTimeout);
                    return false;
                }
                this.socket.setSoTimeout(readTimeout);
                return true;
            } catch (SocketTimeoutException e) {
                return true;
            } catch (IOException e2) {
                return false;
            } catch (Throwable th) {
                this.socket.setSoTimeout(readTimeout);
                throw th;
            }
        }
    }

    public void onStream(Http2Stream stream) throws IOException {
        stream.close(ErrorCode.REFUSED_STREAM);
    }

    public void onSettings(Http2Connection connection) {
        synchronized (this.connectionPool) {
            this.allocationLimit = connection.maxConcurrentStreams();
        }
    }

    public Handshake handshake() {
        return this.handshake;
    }

    public boolean isMultiplexed() {
        return this.http2Connection != null;
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public String toString() {
        Object obj;
        StringBuilder append = new StringBuilder().append("Connection{").append(this.route.address().url().host()).append(":").append(this.route.address().url().port()).append(", proxy=").append(this.route.proxy()).append(" hostAddress=").append(this.route.socketAddress()).append(" cipherSuite=");
        if (this.handshake != null) {
            obj = this.handshake.cipherSuite();
        } else {
            obj = SchedulerSupport.NONE;
        }
        return append.append(obj).append(" protocol=").append(this.protocol).append('}').toString();
    }
}
