package com.google.api.client.googleapis.media;

import com.github.mikephil.charting.utils.Utils;
import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GZipEncoding;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ByteStreams;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.commons.cli.HelpFormatter;

public final class MediaHttpUploader {
    public static final String CONTENT_LENGTH_HEADER = "X-Upload-Content-Length";
    public static final String CONTENT_TYPE_HEADER = "X-Upload-Content-Type";
    public static final int DEFAULT_CHUNK_SIZE = 10485760;
    private static final int KB = 1024;
    static final int MB = 1048576;
    public static final int MINIMUM_CHUNK_SIZE = 262144;
    private Byte cachedByte;
    private int chunkSize = DEFAULT_CHUNK_SIZE;
    private InputStream contentInputStream;
    private int currentChunkLength;
    private HttpRequest currentRequest;
    private byte[] currentRequestContentBuffer;
    private boolean directUploadEnabled;
    private boolean disableGZipContent;
    private HttpHeaders initiationHeaders = new HttpHeaders();
    private String initiationRequestMethod = "POST";
    private boolean isMediaContentLengthCalculated;
    private final AbstractInputStreamContent mediaContent;
    private long mediaContentLength;
    String mediaContentLengthStr = "*";
    private HttpContent metadata;
    private MediaHttpUploaderProgressListener progressListener;
    private final HttpRequestFactory requestFactory;
    Sleeper sleeper = Sleeper.DEFAULT;
    private long totalBytesClientSent;
    private long totalBytesServerReceived;
    private final HttpTransport transport;
    private UploadState uploadState = UploadState.NOT_STARTED;

    public enum UploadState {
        NOT_STARTED,
        INITIATION_STARTED,
        INITIATION_COMPLETE,
        MEDIA_IN_PROGRESS,
        MEDIA_COMPLETE
    }

    public MediaHttpUploader(AbstractInputStreamContent mediaContent2, HttpTransport transport2, HttpRequestInitializer httpRequestInitializer) {
        this.mediaContent = (AbstractInputStreamContent) Preconditions.checkNotNull(mediaContent2);
        this.transport = (HttpTransport) Preconditions.checkNotNull(transport2);
        this.requestFactory = httpRequestInitializer == null ? transport2.createRequestFactory() : transport2.createRequestFactory(httpRequestInitializer);
    }

    public HttpResponse upload(GenericUrl initiationRequestUrl) throws IOException {
        Preconditions.checkArgument(this.uploadState == UploadState.NOT_STARTED);
        if (this.directUploadEnabled) {
            return directUpload(initiationRequestUrl);
        }
        return resumableUpload(initiationRequestUrl);
    }

    private HttpResponse directUpload(GenericUrl initiationRequestUrl) throws IOException {
        updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
        HttpContent content = this.mediaContent;
        if (this.metadata != null) {
            content = new MultipartContent().setContentParts(Arrays.asList(new HttpContent[]{this.metadata, this.mediaContent}));
            initiationRequestUrl.put("uploadType", (Object) "multipart");
        } else {
            initiationRequestUrl.put("uploadType", (Object) "media");
        }
        HttpRequest request = this.requestFactory.buildRequest(this.initiationRequestMethod, initiationRequestUrl, content);
        request.getHeaders().putAll(this.initiationHeaders);
        HttpResponse response = executeCurrentRequest(request);
        boolean responseProcessed = false;
        try {
            if (isMediaLengthKnown()) {
                this.totalBytesServerReceived = getMediaContentLength();
            }
            updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
            responseProcessed = true;
            return response;
        } finally {
            if (!responseProcessed) {
                response.disconnect();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    private HttpResponse resumableUpload(GenericUrl initiationRequestUrl) throws IOException {
        HttpResponse response;
        HttpResponse initialResponse = executeUploadInitiation(initiationRequestUrl);
        if (!initialResponse.isSuccessStatusCode()) {
            return initialResponse;
        }
        try {
            GenericUrl uploadUrl = new GenericUrl(initialResponse.getHeaders().getLocation());
            initialResponse.disconnect();
            this.contentInputStream = this.mediaContent.getInputStream();
            if (!this.contentInputStream.markSupported() && isMediaLengthKnown()) {
                this.contentInputStream = new BufferedInputStream(this.contentInputStream);
            }
            while (true) {
                this.currentRequest = this.requestFactory.buildPutRequest(uploadUrl, null);
                setContentAndHeadersOnCurrentRequest();
                MediaUploadErrorHandler mediaUploadErrorHandler = new MediaUploadErrorHandler(this, this.currentRequest);
                if (isMediaLengthKnown()) {
                    response = executeCurrentRequestWithoutGZip(this.currentRequest);
                } else {
                    response = executeCurrentRequest(this.currentRequest);
                }
                try {
                    if (response.isSuccessStatusCode()) {
                        this.totalBytesServerReceived = getMediaContentLength();
                        if (this.mediaContent.getCloseInputStream()) {
                            this.contentInputStream.close();
                        }
                        updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
                        if (1 != 0) {
                            return response;
                        }
                        response.disconnect();
                        return response;
                    } else if (response.getStatusCode() == 308) {
                        String updatedUploadUrl = response.getHeaders().getLocation();
                        if (updatedUploadUrl != null) {
                            GenericUrl genericUrl = new GenericUrl(updatedUploadUrl);
                            uploadUrl = genericUrl;
                        }
                        long newBytesServerReceived = getNextByteIndex(response.getHeaders().getRange());
                        long currentBytesServerReceived = newBytesServerReceived - this.totalBytesServerReceived;
                        Preconditions.checkState(currentBytesServerReceived >= 0 && currentBytesServerReceived <= ((long) this.currentChunkLength));
                        long copyBytes = ((long) this.currentChunkLength) - currentBytesServerReceived;
                        if (isMediaLengthKnown()) {
                            if (copyBytes > 0) {
                                this.contentInputStream.reset();
                                Preconditions.checkState(currentBytesServerReceived == this.contentInputStream.skip(currentBytesServerReceived));
                            }
                        } else if (copyBytes == 0) {
                            this.currentRequestContentBuffer = null;
                        }
                        this.totalBytesServerReceived = newBytesServerReceived;
                        updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
                        if (0 == 0) {
                            response.disconnect();
                        }
                    } else if (1 != 0) {
                        return response;
                    } else {
                        response.disconnect();
                        return response;
                    }
                } catch (Throwable th) {
                    if (0 == 0) {
                        response.disconnect();
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            initialResponse.disconnect();
            throw th2;
        }
    }

    private boolean isMediaLengthKnown() throws IOException {
        return getMediaContentLength() >= 0;
    }

    private long getMediaContentLength() throws IOException {
        if (!this.isMediaContentLengthCalculated) {
            this.mediaContentLength = this.mediaContent.getLength();
            this.isMediaContentLengthCalculated = true;
        }
        return this.mediaContentLength;
    }

    private HttpResponse executeUploadInitiation(GenericUrl initiationRequestUrl) throws IOException {
        updateStateAndNotifyListener(UploadState.INITIATION_STARTED);
        initiationRequestUrl.put("uploadType", (Object) "resumable");
        HttpRequest request = this.requestFactory.buildRequest(this.initiationRequestMethod, initiationRequestUrl, this.metadata == null ? new EmptyContent() : this.metadata);
        this.initiationHeaders.set(CONTENT_TYPE_HEADER, (Object) this.mediaContent.getType());
        if (isMediaLengthKnown()) {
            this.initiationHeaders.set(CONTENT_LENGTH_HEADER, (Object) Long.valueOf(getMediaContentLength()));
        }
        request.getHeaders().putAll(this.initiationHeaders);
        HttpResponse response = executeCurrentRequest(request);
        boolean notificationCompleted = false;
        try {
            updateStateAndNotifyListener(UploadState.INITIATION_COMPLETE);
            notificationCompleted = true;
            return response;
        } finally {
            if (!notificationCompleted) {
                response.disconnect();
            }
        }
    }

    private HttpResponse executeCurrentRequestWithoutGZip(HttpRequest request) throws IOException {
        new MethodOverride().intercept(request);
        request.setThrowExceptionOnExecuteError(false);
        return request.execute();
    }

    private HttpResponse executeCurrentRequest(HttpRequest request) throws IOException {
        if (!this.disableGZipContent && !(request.getContent() instanceof EmptyContent)) {
            request.setEncoding(new GZipEncoding());
        }
        return executeCurrentRequestWithoutGZip(request);
    }

    private void setContentAndHeadersOnCurrentRequest() throws IOException {
        int blockSize;
        int bytesAllowedToRead;
        AbstractInputStreamContent contentChunk;
        if (isMediaLengthKnown()) {
            blockSize = (int) Math.min((long) this.chunkSize, getMediaContentLength() - this.totalBytesServerReceived);
        } else {
            blockSize = this.chunkSize;
        }
        int actualBlockSize = blockSize;
        if (isMediaLengthKnown()) {
            this.contentInputStream.mark(blockSize);
            contentChunk = new InputStreamContent(this.mediaContent.getType(), ByteStreams.limit(this.contentInputStream, (long) blockSize)).setRetrySupported(true).setLength((long) blockSize).setCloseInputStream(false);
            this.mediaContentLengthStr = String.valueOf(getMediaContentLength());
        } else {
            int copyBytes = 0;
            if (this.currentRequestContentBuffer == null) {
                if (this.cachedByte == null) {
                    bytesAllowedToRead = blockSize + 1;
                } else {
                    bytesAllowedToRead = blockSize;
                }
                this.currentRequestContentBuffer = new byte[(blockSize + 1)];
                if (this.cachedByte != null) {
                    this.currentRequestContentBuffer[0] = this.cachedByte.byteValue();
                }
            } else {
                copyBytes = (int) (this.totalBytesClientSent - this.totalBytesServerReceived);
                System.arraycopy(this.currentRequestContentBuffer, this.currentChunkLength - copyBytes, this.currentRequestContentBuffer, 0, copyBytes);
                if (this.cachedByte != null) {
                    this.currentRequestContentBuffer[copyBytes] = this.cachedByte.byteValue();
                }
                bytesAllowedToRead = blockSize - copyBytes;
            }
            int actualBytesRead = ByteStreams.read(this.contentInputStream, this.currentRequestContentBuffer, (blockSize + 1) - bytesAllowedToRead, bytesAllowedToRead);
            if (actualBytesRead < bytesAllowedToRead) {
                actualBlockSize = copyBytes + Math.max(0, actualBytesRead);
                if (this.cachedByte != null) {
                    actualBlockSize++;
                    this.cachedByte = null;
                }
                if (this.mediaContentLengthStr.equals("*")) {
                    this.mediaContentLengthStr = String.valueOf(this.totalBytesServerReceived + ((long) actualBlockSize));
                }
            } else {
                this.cachedByte = Byte.valueOf(this.currentRequestContentBuffer[blockSize]);
            }
            contentChunk = new ByteArrayContent(this.mediaContent.getType(), this.currentRequestContentBuffer, 0, actualBlockSize);
            this.totalBytesClientSent = this.totalBytesServerReceived + ((long) actualBlockSize);
        }
        this.currentChunkLength = actualBlockSize;
        this.currentRequest.setContent(contentChunk);
        if (actualBlockSize == 0) {
            HttpHeaders headers = this.currentRequest.getHeaders();
            String str = "bytes */";
            String valueOf = String.valueOf(this.mediaContentLengthStr);
            headers.setContentRange(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return;
        }
        HttpHeaders headers2 = this.currentRequest.getHeaders();
        long j = this.totalBytesServerReceived;
        long j2 = (this.totalBytesServerReceived + ((long) actualBlockSize)) - 1;
        String valueOf2 = String.valueOf(String.valueOf(this.mediaContentLengthStr));
        headers2.setContentRange(new StringBuilder(valueOf2.length() + 48).append("bytes ").append(j).append(HelpFormatter.DEFAULT_OPT_PREFIX).append(j2).append("/").append(valueOf2).toString());
    }

    /* access modifiers changed from: 0000 */
    @Beta
    public void serverErrorCallback() throws IOException {
        Preconditions.checkNotNull(this.currentRequest, "The current request should not be null");
        this.currentRequest.setContent(new EmptyContent());
        HttpHeaders headers = this.currentRequest.getHeaders();
        String str = "bytes */";
        String valueOf = String.valueOf(this.mediaContentLengthStr);
        headers.setContentRange(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    private long getNextByteIndex(String rangeHeader) {
        if (rangeHeader == null) {
            return 0;
        }
        return Long.parseLong(rangeHeader.substring(rangeHeader.indexOf(45) + 1)) + 1;
    }

    public HttpContent getMetadata() {
        return this.metadata;
    }

    public MediaHttpUploader setMetadata(HttpContent metadata2) {
        this.metadata = metadata2;
        return this;
    }

    public HttpContent getMediaContent() {
        return this.mediaContent;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public MediaHttpUploader setDirectUploadEnabled(boolean directUploadEnabled2) {
        this.directUploadEnabled = directUploadEnabled2;
        return this;
    }

    public boolean isDirectUploadEnabled() {
        return this.directUploadEnabled;
    }

    public MediaHttpUploader setProgressListener(MediaHttpUploaderProgressListener progressListener2) {
        this.progressListener = progressListener2;
        return this;
    }

    public MediaHttpUploaderProgressListener getProgressListener() {
        return this.progressListener;
    }

    public MediaHttpUploader setChunkSize(int chunkSize2) {
        Preconditions.checkArgument(chunkSize2 > 0 && chunkSize2 % 262144 == 0, "chunkSize must be a positive multiple of 262144.");
        this.chunkSize = chunkSize2;
        return this;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }

    public MediaHttpUploader setDisableGZipContent(boolean disableGZipContent2) {
        this.disableGZipContent = disableGZipContent2;
        return this;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public MediaHttpUploader setSleeper(Sleeper sleeper2) {
        this.sleeper = sleeper2;
        return this;
    }

    public String getInitiationRequestMethod() {
        return this.initiationRequestMethod;
    }

    public MediaHttpUploader setInitiationRequestMethod(String initiationRequestMethod2) {
        Preconditions.checkArgument(initiationRequestMethod2.equals("POST") || initiationRequestMethod2.equals(HttpMethods.PUT) || initiationRequestMethod2.equals("PATCH"));
        this.initiationRequestMethod = initiationRequestMethod2;
        return this;
    }

    public MediaHttpUploader setInitiationHeaders(HttpHeaders initiationHeaders2) {
        this.initiationHeaders = initiationHeaders2;
        return this;
    }

    public HttpHeaders getInitiationHeaders() {
        return this.initiationHeaders;
    }

    public long getNumBytesUploaded() {
        return this.totalBytesServerReceived;
    }

    private void updateStateAndNotifyListener(UploadState uploadState2) throws IOException {
        this.uploadState = uploadState2;
        if (this.progressListener != null) {
            this.progressListener.progressChanged(this);
        }
    }

    public UploadState getUploadState() {
        return this.uploadState;
    }

    public double getProgress() throws IOException {
        Preconditions.checkArgument(isMediaLengthKnown(), "Cannot call getProgress() if the specified AbstractInputStreamContent has no content length. Use  getNumBytesUploaded() to denote progress instead.");
        return getMediaContentLength() == 0 ? Utils.DOUBLE_EPSILON : ((double) this.totalBytesServerReceived) / ((double) getMediaContentLength());
    }
}
