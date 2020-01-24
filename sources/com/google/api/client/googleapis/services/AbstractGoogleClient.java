package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class AbstractGoogleClient {
    static final Logger logger = Logger.getLogger(AbstractGoogleClient.class.getName());
    private final String applicationName;
    private final String batchPath;
    private final GoogleClientRequestInitializer googleClientRequestInitializer;
    private final ObjectParser objectParser;
    private final HttpRequestFactory requestFactory;
    private final String rootUrl;
    private final String servicePath;
    private final boolean suppressPatternChecks;
    private final boolean suppressRequiredParameterChecks;

    public static abstract class Builder {
        String applicationName;
        String batchPath;
        GoogleClientRequestInitializer googleClientRequestInitializer;
        HttpRequestInitializer httpRequestInitializer;
        final ObjectParser objectParser;
        String rootUrl;
        String servicePath;
        boolean suppressPatternChecks;
        boolean suppressRequiredParameterChecks;
        final HttpTransport transport;

        public abstract AbstractGoogleClient build();

        protected Builder(HttpTransport transport2, String rootUrl2, String servicePath2, ObjectParser objectParser2, HttpRequestInitializer httpRequestInitializer2) {
            this.transport = (HttpTransport) Preconditions.checkNotNull(transport2);
            this.objectParser = objectParser2;
            setRootUrl(rootUrl2);
            setServicePath(servicePath2);
            this.httpRequestInitializer = httpRequestInitializer2;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public ObjectParser getObjectParser() {
            return this.objectParser;
        }

        public final String getRootUrl() {
            return this.rootUrl;
        }

        public Builder setRootUrl(String rootUrl2) {
            this.rootUrl = AbstractGoogleClient.normalizeRootUrl(rootUrl2);
            return this;
        }

        public final String getServicePath() {
            return this.servicePath;
        }

        public Builder setServicePath(String servicePath2) {
            this.servicePath = AbstractGoogleClient.normalizeServicePath(servicePath2);
            return this;
        }

        public Builder setBatchPath(String batchPath2) {
            this.batchPath = batchPath2;
            return this;
        }

        public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
            return this.googleClientRequestInitializer;
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer2) {
            this.googleClientRequestInitializer = googleClientRequestInitializer2;
            return this;
        }

        public final HttpRequestInitializer getHttpRequestInitializer() {
            return this.httpRequestInitializer;
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer2) {
            this.httpRequestInitializer = httpRequestInitializer2;
            return this;
        }

        public final String getApplicationName() {
            return this.applicationName;
        }

        public Builder setApplicationName(String applicationName2) {
            this.applicationName = applicationName2;
            return this;
        }

        public final boolean getSuppressPatternChecks() {
            return this.suppressPatternChecks;
        }

        public Builder setSuppressPatternChecks(boolean suppressPatternChecks2) {
            this.suppressPatternChecks = suppressPatternChecks2;
            return this;
        }

        public final boolean getSuppressRequiredParameterChecks() {
            return this.suppressRequiredParameterChecks;
        }

        public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks2) {
            this.suppressRequiredParameterChecks = suppressRequiredParameterChecks2;
            return this;
        }

        public Builder setSuppressAllChecks(boolean suppressAllChecks) {
            return setSuppressPatternChecks(true).setSuppressRequiredParameterChecks(true);
        }
    }

    protected AbstractGoogleClient(Builder builder) {
        this.googleClientRequestInitializer = builder.googleClientRequestInitializer;
        this.rootUrl = normalizeRootUrl(builder.rootUrl);
        this.servicePath = normalizeServicePath(builder.servicePath);
        this.batchPath = builder.batchPath;
        if (Strings.isNullOrEmpty(builder.applicationName)) {
            logger.warning("Application name is not set. Call Builder#setApplicationName.");
        }
        this.applicationName = builder.applicationName;
        this.requestFactory = builder.httpRequestInitializer == null ? builder.transport.createRequestFactory() : builder.transport.createRequestFactory(builder.httpRequestInitializer);
        this.objectParser = builder.objectParser;
        this.suppressPatternChecks = builder.suppressPatternChecks;
        this.suppressRequiredParameterChecks = builder.suppressRequiredParameterChecks;
    }

    public final String getRootUrl() {
        return this.rootUrl;
    }

    public final String getServicePath() {
        return this.servicePath;
    }

    public final String getBaseUrl() {
        String valueOf = String.valueOf(this.rootUrl);
        String valueOf2 = String.valueOf(this.servicePath);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final String getApplicationName() {
        return this.applicationName;
    }

    public final HttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
        return this.googleClientRequestInitializer;
    }

    public ObjectParser getObjectParser() {
        return this.objectParser;
    }

    /* access modifiers changed from: protected */
    public void initialize(AbstractGoogleClientRequest<?> httpClientRequest) throws IOException {
        if (getGoogleClientRequestInitializer() != null) {
            getGoogleClientRequestInitializer().initialize(httpClientRequest);
        }
    }

    public final BatchRequest batch() {
        return batch(null);
    }

    public final BatchRequest batch(HttpRequestInitializer httpRequestInitializer) {
        BatchRequest batch = new BatchRequest(getRequestFactory().getTransport(), httpRequestInitializer);
        String valueOf = String.valueOf(getRootUrl());
        String valueOf2 = String.valueOf(this.batchPath);
        batch.setBatchUrl(new GenericUrl(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
        return batch;
    }

    public final boolean getSuppressPatternChecks() {
        return this.suppressPatternChecks;
    }

    public final boolean getSuppressRequiredParameterChecks() {
        return this.suppressRequiredParameterChecks;
    }

    static String normalizeRootUrl(String rootUrl2) {
        Preconditions.checkNotNull(rootUrl2, "root URL cannot be null.");
        if (!rootUrl2.endsWith("/")) {
            return String.valueOf(rootUrl2).concat("/");
        }
        return rootUrl2;
    }

    static String normalizeServicePath(String servicePath2) {
        Preconditions.checkNotNull(servicePath2, "service path cannot be null");
        if (servicePath2.length() == 1) {
            Preconditions.checkArgument("/".equals(servicePath2), "service path must equal \"/\" if it is of length 1.");
            return "";
        } else if (servicePath2.length() <= 0) {
            return servicePath2;
        } else {
            if (!servicePath2.endsWith("/")) {
                servicePath2 = String.valueOf(servicePath2).concat("/");
            }
            if (servicePath2.startsWith("/")) {
                return servicePath2.substring(1);
            }
            return servicePath2;
        }
    }
}
