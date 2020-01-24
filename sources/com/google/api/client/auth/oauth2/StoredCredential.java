package com.google.api.client.auth.oauth2;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Objects;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Beta
public final class StoredCredential implements Serializable {
    public static final String DEFAULT_DATA_STORE_ID = StoredCredential.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private final Lock lock = new ReentrantLock();
    private String refreshToken;

    public StoredCredential() {
    }

    public StoredCredential(Credential credential) {
        setAccessToken(credential.getAccessToken());
        setRefreshToken(credential.getRefreshToken());
        setExpirationTimeMilliseconds(credential.getExpirationTimeMilliseconds());
    }

    public String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setAccessToken(String accessToken2) {
        this.lock.lock();
        try {
            this.accessToken = accessToken2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setExpirationTimeMilliseconds(Long expirationTimeMilliseconds2) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = expirationTimeMilliseconds2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredCredential setRefreshToken(String refreshToken2) {
        this.lock.lock();
        try {
            this.refreshToken = refreshToken2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public String toString() {
        return Objects.toStringHelper(StoredCredential.class).add("accessToken", getAccessToken()).add("refreshToken", getRefreshToken()).add("expirationTimeMilliseconds", getExpirationTimeMilliseconds()).toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StoredCredential)) {
            return false;
        }
        StoredCredential o = (StoredCredential) other;
        if (!Objects.equal(getAccessToken(), o.getAccessToken()) || !Objects.equal(getRefreshToken(), o.getRefreshToken()) || !Objects.equal(getExpirationTimeMilliseconds(), o.getExpirationTimeMilliseconds())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{getAccessToken(), getRefreshToken(), getExpirationTimeMilliseconds()});
    }

    public static DataStore<StoredCredential> getDefaultDataStore(DataStoreFactory dataStoreFactory) throws IOException {
        return dataStoreFactory.getDataStore(DEFAULT_DATA_STORE_ID);
    }
}
