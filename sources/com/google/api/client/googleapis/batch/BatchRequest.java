package com.google.api.client.googleapis.batch;

import com.google.api.client.http.BackOffPolicy;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.http.MultipartContent.Part;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public final class BatchRequest {
    private GenericUrl batchUrl = new GenericUrl("https://www.googleapis.com/batch");
    private final HttpRequestFactory requestFactory;
    List<RequestInfo<?, ?>> requestInfos = new ArrayList();
    private Sleeper sleeper = Sleeper.DEFAULT;

    class BatchInterceptor implements HttpExecuteInterceptor {
        private HttpExecuteInterceptor originalInterceptor;

        BatchInterceptor(HttpExecuteInterceptor originalInterceptor2) {
            this.originalInterceptor = originalInterceptor2;
        }

        public void intercept(HttpRequest batchRequest) throws IOException {
            if (this.originalInterceptor != null) {
                this.originalInterceptor.intercept(batchRequest);
            }
            for (RequestInfo<?, ?> requestInfo : BatchRequest.this.requestInfos) {
                HttpExecuteInterceptor interceptor = requestInfo.request.getInterceptor();
                if (interceptor != null) {
                    interceptor.intercept(requestInfo.request);
                }
            }
        }
    }

    static class RequestInfo<T, E> {
        final BatchCallback<T, E> callback;
        final Class<T> dataClass;
        final Class<E> errorClass;
        final HttpRequest request;

        RequestInfo(BatchCallback<T, E> callback2, Class<T> dataClass2, Class<E> errorClass2, HttpRequest request2) {
            this.callback = callback2;
            this.dataClass = dataClass2;
            this.errorClass = errorClass2;
            this.request = request2;
        }
    }

    public BatchRequest(HttpTransport transport, HttpRequestInitializer httpRequestInitializer) {
        this.requestFactory = httpRequestInitializer == null ? transport.createRequestFactory() : transport.createRequestFactory(httpRequestInitializer);
    }

    public BatchRequest setBatchUrl(GenericUrl batchUrl2) {
        this.batchUrl = batchUrl2;
        return this;
    }

    public GenericUrl getBatchUrl() {
        return this.batchUrl;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public BatchRequest setSleeper(Sleeper sleeper2) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper2);
        return this;
    }

    public <T, E> BatchRequest queue(HttpRequest httpRequest, Class<T> dataClass, Class<E> errorClass, BatchCallback<T, E> callback) throws IOException {
        Preconditions.checkNotNull(httpRequest);
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(dataClass);
        Preconditions.checkNotNull(errorClass);
        this.requestInfos.add(new RequestInfo(callback, dataClass, errorClass, httpRequest));
        return this;
    }

    public int size() {
        return this.requestInfos.size();
    }

    /* JADX INFO: finally extract failed */
    public void execute() throws IOException {
        boolean retryAllowed;
        String boundary;
        Preconditions.checkState(!this.requestInfos.isEmpty());
        HttpRequest batchRequest = this.requestFactory.buildPostRequest(this.batchUrl, null);
        BatchInterceptor batchInterceptor = new BatchInterceptor(batchRequest.getInterceptor());
        batchRequest.setInterceptor(batchInterceptor);
        int retriesRemaining = batchRequest.getNumberOfRetries();
        BackOffPolicy backOffPolicy = batchRequest.getBackOffPolicy();
        if (backOffPolicy != null) {
            backOffPolicy.reset();
        }
        do {
            retryAllowed = retriesRemaining > 0;
            MultipartContent batchContent = new MultipartContent();
            batchContent.getMediaType().setSubType("mixed");
            int contentId = 1;
            for (RequestInfo<?, ?> requestInfo : this.requestInfos) {
                int contentId2 = contentId + 1;
                batchContent.addPart(new Part(new HttpHeaders().setAcceptEncoding(null).set("Content-ID", (Object) Integer.valueOf(contentId)), new HttpRequestContent(requestInfo.request)));
                contentId = contentId2;
            }
            batchRequest.setContent(batchContent);
            HttpResponse response = batchRequest.execute();
            String str = HelpFormatter.DEFAULT_LONG_OPT_PREFIX;
            try {
                String valueOf = String.valueOf(response.getMediaType().getParameter("boundary"));
                if (valueOf.length() != 0) {
                    boundary = str.concat(valueOf);
                } else {
                    boundary = new String(str);
                }
                BatchUnparsedResponse batchResponse = new BatchUnparsedResponse(response.getContent(), boundary, this.requestInfos, retryAllowed);
                while (batchResponse.hasNext) {
                    batchResponse.parseNextResponse();
                }
                response.disconnect();
                List<RequestInfo<?, ?>> unsuccessfulRequestInfos = batchResponse.unsuccessfulRequestInfos;
                if (unsuccessfulRequestInfos.isEmpty()) {
                    break;
                }
                this.requestInfos = unsuccessfulRequestInfos;
                if (batchResponse.backOffRequired && backOffPolicy != null) {
                    long backOffTime = backOffPolicy.getNextBackOffMillis();
                    if (backOffTime != -1) {
                        try {
                            this.sleeper.sleep(backOffTime);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                retriesRemaining--;
            } catch (Throwable th) {
                response.disconnect();
                throw th;
            }
        } while (retryAllowed);
        this.requestInfos.clear();
    }
}
