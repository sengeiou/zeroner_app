package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingStreamingContent implements StreamingContent {
    private final StreamingContent content;
    private final int contentLoggingLimit;
    private final Logger logger;
    private final Level loggingLevel;

    public LoggingStreamingContent(StreamingContent content2, Logger logger2, Level loggingLevel2, int contentLoggingLimit2) {
        this.content = content2;
        this.logger = logger2;
        this.loggingLevel = loggingLevel2;
        this.contentLoggingLimit = contentLoggingLimit2;
    }

    /* JADX INFO: finally extract failed */
    public void writeTo(OutputStream out) throws IOException {
        LoggingOutputStream loggableOutputStream = new LoggingOutputStream(out, this.logger, this.loggingLevel, this.contentLoggingLimit);
        try {
            this.content.writeTo(loggableOutputStream);
            loggableOutputStream.getLogStream().close();
            out.flush();
        } catch (Throwable th) {
            loggableOutputStream.getLogStream().close();
            throw th;
        }
    }
}
