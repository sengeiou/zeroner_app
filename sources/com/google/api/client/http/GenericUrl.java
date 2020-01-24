package com.google.api.client.http;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.escape.CharEscapers;
import com.google.api.client.util.escape.Escaper;
import com.google.api.client.util.escape.PercentEscaper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.text.Typography;

public class GenericUrl extends GenericData {
    private static final Escaper URI_FRAGMENT_ESCAPER = new PercentEscaper("=&-_.!~*'()@:$,;/?:", false);
    private String fragment;
    private String host;
    private List<String> pathParts;
    private int port;
    private String scheme;
    private String userInfo;

    public GenericUrl() {
        this.port = -1;
    }

    public GenericUrl(String encodedUrl) {
        this(parseURL(encodedUrl));
    }

    public GenericUrl(URI uri) {
        this(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getRawPath(), uri.getRawFragment(), uri.getRawQuery(), uri.getRawUserInfo());
    }

    public GenericUrl(URL url) {
        this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef(), url.getQuery(), url.getUserInfo());
    }

    private GenericUrl(String scheme2, String host2, int port2, String path, String fragment2, String query, String userInfo2) {
        String str = null;
        this.port = -1;
        this.scheme = scheme2.toLowerCase();
        this.host = host2;
        this.port = port2;
        this.pathParts = toPathParts(path);
        this.fragment = fragment2 != null ? CharEscapers.decodeUri(fragment2) : null;
        if (query != null) {
            UrlEncodedParser.parse(query, (Object) this);
        }
        if (userInfo2 != null) {
            str = CharEscapers.decodeUri(userInfo2);
        }
        this.userInfo = str;
    }

    public int hashCode() {
        return build().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof GenericUrl)) {
            return false;
        }
        return build().equals(((GenericUrl) obj).toString());
    }

    public String toString() {
        return build();
    }

    public GenericUrl clone() {
        GenericUrl result = (GenericUrl) super.clone();
        if (this.pathParts != null) {
            result.pathParts = new ArrayList(this.pathParts);
        }
        return result;
    }

    public GenericUrl set(String fieldName, Object value) {
        return (GenericUrl) super.set(fieldName, value);
    }

    public final String getScheme() {
        return this.scheme;
    }

    public final void setScheme(String scheme2) {
        this.scheme = (String) Preconditions.checkNotNull(scheme2);
    }

    public String getHost() {
        return this.host;
    }

    public final void setHost(String host2) {
        this.host = (String) Preconditions.checkNotNull(host2);
    }

    public final String getUserInfo() {
        return this.userInfo;
    }

    public final void setUserInfo(String userInfo2) {
        this.userInfo = userInfo2;
    }

    public int getPort() {
        return this.port;
    }

    public final void setPort(int port2) {
        Preconditions.checkArgument(port2 >= -1, "expected port >= -1");
        this.port = port2;
    }

    public List<String> getPathParts() {
        return this.pathParts;
    }

    public void setPathParts(List<String> pathParts2) {
        this.pathParts = pathParts2;
    }

    public String getFragment() {
        return this.fragment;
    }

    public final void setFragment(String fragment2) {
        this.fragment = fragment2;
    }

    public final String build() {
        String valueOf = String.valueOf(buildAuthority());
        String valueOf2 = String.valueOf(buildRelativeUrl());
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final String buildAuthority() {
        StringBuilder buf = new StringBuilder();
        buf.append((String) Preconditions.checkNotNull(this.scheme));
        buf.append("://");
        if (this.userInfo != null) {
            buf.append(CharEscapers.escapeUriUserInfo(this.userInfo)).append('@');
        }
        buf.append((String) Preconditions.checkNotNull(this.host));
        int port2 = this.port;
        if (port2 != -1) {
            buf.append(':').append(port2);
        }
        return buf.toString();
    }

    public final String buildRelativeUrl() {
        StringBuilder buf = new StringBuilder();
        if (this.pathParts != null) {
            appendRawPathFromParts(buf);
        }
        addQueryParams(entrySet(), buf);
        String fragment2 = this.fragment;
        if (fragment2 != null) {
            buf.append('#').append(URI_FRAGMENT_ESCAPER.escape(fragment2));
        }
        return buf.toString();
    }

    public final URI toURI() {
        return toURI(build());
    }

    public final URL toURL() {
        return parseURL(build());
    }

    public final URL toURL(String relativeUrl) {
        try {
            return new URL(toURL(), relativeUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Object getFirst(String name) {
        Object value = get(name);
        if (!(value instanceof Collection)) {
            return value;
        }
        Iterator<Object> iterator = ((Collection) value).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public Collection<Object> getAll(String name) {
        Object value = get(name);
        if (value == null) {
            return Collections.emptySet();
        }
        if (value instanceof Collection) {
            return Collections.unmodifiableCollection((Collection) value);
        }
        return Collections.singleton(value);
    }

    public String getRawPath() {
        if (this.pathParts == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        appendRawPathFromParts(buf);
        return buf.toString();
    }

    public void setRawPath(String encodedPath) {
        this.pathParts = toPathParts(encodedPath);
    }

    public void appendRawPath(String encodedPath) {
        if (encodedPath != null && encodedPath.length() != 0) {
            List<String> appendedPathParts = toPathParts(encodedPath);
            if (this.pathParts == null || this.pathParts.isEmpty()) {
                this.pathParts = appendedPathParts;
                return;
            }
            int size = this.pathParts.size();
            List<String> list = this.pathParts;
            int i = size - 1;
            String valueOf = String.valueOf((String) this.pathParts.get(size - 1));
            String valueOf2 = String.valueOf((String) appendedPathParts.get(0));
            list.set(i, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            this.pathParts.addAll(appendedPathParts.subList(1, appendedPathParts.size()));
        }
    }

    public static List<String> toPathParts(String encodedPath) {
        String sub;
        if (encodedPath == null || encodedPath.length() == 0) {
            return null;
        }
        List<String> result = new ArrayList<>();
        int cur = 0;
        boolean notDone = true;
        while (notDone) {
            int slash = encodedPath.indexOf(47, cur);
            notDone = slash != -1;
            if (notDone) {
                sub = encodedPath.substring(cur, slash);
            } else {
                sub = encodedPath.substring(cur);
            }
            result.add(CharEscapers.decodeUri(sub));
            cur = slash + 1;
        }
        return result;
    }

    private void appendRawPathFromParts(StringBuilder buf) {
        int size = this.pathParts.size();
        for (int i = 0; i < size; i++) {
            String pathPart = (String) this.pathParts.get(i);
            if (i != 0) {
                buf.append('/');
            }
            if (pathPart.length() != 0) {
                buf.append(CharEscapers.escapeUriPath(pathPart));
            }
        }
    }

    static void addQueryParams(Set<Entry<String, Object>> entrySet, StringBuilder buf) {
        boolean first = true;
        for (Entry<String, Object> nameValueEntry : entrySet) {
            Object value = nameValueEntry.getValue();
            if (value != null) {
                String name = CharEscapers.escapeUriQuery((String) nameValueEntry.getKey());
                if (value instanceof Collection) {
                    for (Object repeatedValue : (Collection) value) {
                        first = appendParam(first, buf, name, repeatedValue);
                    }
                } else {
                    first = appendParam(first, buf, name, value);
                }
            }
        }
    }

    private static boolean appendParam(boolean first, StringBuilder buf, String name, Object value) {
        if (first) {
            first = false;
            buf.append('?');
        } else {
            buf.append(Typography.amp);
        }
        buf.append(name);
        String stringValue = CharEscapers.escapeUriQuery(value.toString());
        if (stringValue.length() != 0) {
            buf.append('=').append(stringValue);
        }
        return first;
    }

    private static URI toURI(String encodedUrl) {
        try {
            return new URI(encodedUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static URL parseURL(String encodedUrl) {
        try {
            return new URL(encodedUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
