package com.google.api.client.json.webtoken;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.util.Collections;
import java.util.List;

public class JsonWebToken {
    private final Header header;
    private final Payload payload;

    public static class Header extends GenericJson {
        @Key("cty")
        private String contentType;
        @Key("typ")
        private String type;

        public final String getType() {
            return this.type;
        }

        public Header setType(String type2) {
            this.type = type2;
            return this;
        }

        public final String getContentType() {
            return this.contentType;
        }

        public Header setContentType(String contentType2) {
            this.contentType = contentType2;
            return this;
        }

        public Header set(String fieldName, Object value) {
            return (Header) super.set(fieldName, value);
        }

        public Header clone() {
            return (Header) super.clone();
        }
    }

    public static class Payload extends GenericJson {
        @Key("aud")
        private Object audience;
        @Key("exp")
        private Long expirationTimeSeconds;
        @Key("iat")
        private Long issuedAtTimeSeconds;
        @Key("iss")
        private String issuer;
        @Key("jti")
        private String jwtId;
        @Key("nbf")
        private Long notBeforeTimeSeconds;
        @Key("sub")
        private String subject;
        @Key("typ")
        private String type;

        public final Long getExpirationTimeSeconds() {
            return this.expirationTimeSeconds;
        }

        public Payload setExpirationTimeSeconds(Long expirationTimeSeconds2) {
            this.expirationTimeSeconds = expirationTimeSeconds2;
            return this;
        }

        public final Long getNotBeforeTimeSeconds() {
            return this.notBeforeTimeSeconds;
        }

        public Payload setNotBeforeTimeSeconds(Long notBeforeTimeSeconds2) {
            this.notBeforeTimeSeconds = notBeforeTimeSeconds2;
            return this;
        }

        public final Long getIssuedAtTimeSeconds() {
            return this.issuedAtTimeSeconds;
        }

        public Payload setIssuedAtTimeSeconds(Long issuedAtTimeSeconds2) {
            this.issuedAtTimeSeconds = issuedAtTimeSeconds2;
            return this;
        }

        public final String getIssuer() {
            return this.issuer;
        }

        public Payload setIssuer(String issuer2) {
            this.issuer = issuer2;
            return this;
        }

        public final Object getAudience() {
            return this.audience;
        }

        public final List<String> getAudienceAsList() {
            if (this.audience == null) {
                return Collections.emptyList();
            }
            if (this.audience instanceof String) {
                return Collections.singletonList((String) this.audience);
            }
            return (List) this.audience;
        }

        public Payload setAudience(Object audience2) {
            this.audience = audience2;
            return this;
        }

        public final String getJwtId() {
            return this.jwtId;
        }

        public Payload setJwtId(String jwtId2) {
            this.jwtId = jwtId2;
            return this;
        }

        public final String getType() {
            return this.type;
        }

        public Payload setType(String type2) {
            this.type = type2;
            return this;
        }

        public final String getSubject() {
            return this.subject;
        }

        public Payload setSubject(String subject2) {
            this.subject = subject2;
            return this;
        }

        public Payload set(String fieldName, Object value) {
            return (Payload) super.set(fieldName, value);
        }

        public Payload clone() {
            return (Payload) super.clone();
        }
    }

    public JsonWebToken(Header header2, Payload payload2) {
        this.header = (Header) Preconditions.checkNotNull(header2);
        this.payload = (Payload) Preconditions.checkNotNull(payload2);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("header", this.header).add("payload", this.payload).toString();
    }

    public Header getHeader() {
        return this.header;
    }

    public Payload getPayload() {
        return this.payload;
    }
}
