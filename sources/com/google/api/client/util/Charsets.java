package com.google.api.client.util;

import java.nio.charset.Charset;
import org.apache.commons.codec.CharEncoding;

public final class Charsets {
    public static final Charset ISO_8859_1 = Charset.forName(CharEncoding.ISO_8859_1);
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Charsets() {
    }
}
