package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class AclRule extends GenericJson {
    @Key
    private String etag;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private String role;
    @Key
    private Scope scope;

    public static final class Scope extends GenericJson {
        @Key
        private String type;
        @Key
        private String value;

        public String getType() {
            return this.type;
        }

        public Scope setType(String str) {
            this.type = str;
            return this;
        }

        public String getValue() {
            return this.value;
        }

        public Scope setValue(String str) {
            this.value = str;
            return this;
        }

        public Scope set(String str, Object obj) {
            return (Scope) super.set(str, obj);
        }

        public Scope clone() {
            return (Scope) super.clone();
        }
    }

    public String getEtag() {
        return this.etag;
    }

    public AclRule setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public AclRule setId(String str) {
        this.id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public AclRule setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getRole() {
        return this.role;
    }

    public AclRule setRole(String str) {
        this.role = str;
        return this;
    }

    public Scope getScope() {
        return this.scope;
    }

    public AclRule setScope(Scope scope2) {
        this.scope = scope2;
        return this;
    }

    public AclRule set(String str, Object obj) {
        return (AclRule) super.set(str, obj);
    }

    public AclRule clone() {
        return (AclRule) super.clone();
    }
}
