package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Beta
public final class StoredChannel implements Serializable {
    public static final String DEFAULT_DATA_STORE_ID = StoredChannel.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private String clientToken;
    private Long expiration;
    private final String id;
    private final Lock lock;
    private final UnparsedNotificationCallback notificationCallback;
    private String topicId;

    public StoredChannel(UnparsedNotificationCallback notificationCallback2) {
        this(notificationCallback2, NotificationUtils.randomUuidString());
    }

    public StoredChannel(UnparsedNotificationCallback notificationCallback2, String id2) {
        this.lock = new ReentrantLock();
        this.notificationCallback = (UnparsedNotificationCallback) Preconditions.checkNotNull(notificationCallback2);
        this.id = (String) Preconditions.checkNotNull(id2);
    }

    public StoredChannel store(DataStoreFactory dataStoreFactory) throws IOException {
        return store(getDefaultDataStore(dataStoreFactory));
    }

    public StoredChannel store(DataStore<StoredChannel> dataStore) throws IOException {
        this.lock.lock();
        try {
            dataStore.set(getId(), this);
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public UnparsedNotificationCallback getNotificationCallback() {
        this.lock.lock();
        try {
            return this.notificationCallback;
        } finally {
            this.lock.unlock();
        }
    }

    public String getClientToken() {
        this.lock.lock();
        try {
            return this.clientToken;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredChannel setClientToken(String clientToken2) {
        this.lock.lock();
        try {
            this.clientToken = clientToken2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public Long getExpiration() {
        this.lock.lock();
        try {
            return this.expiration;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredChannel setExpiration(Long expiration2) {
        this.lock.lock();
        try {
            this.expiration = expiration2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public String getId() {
        this.lock.lock();
        try {
            return this.id;
        } finally {
            this.lock.unlock();
        }
    }

    public String getTopicId() {
        this.lock.lock();
        try {
            return this.topicId;
        } finally {
            this.lock.unlock();
        }
    }

    public StoredChannel setTopicId(String topicId2) {
        this.lock.lock();
        try {
            this.topicId = topicId2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public String toString() {
        return Objects.toStringHelper(StoredChannel.class).add("notificationCallback", getNotificationCallback()).add("clientToken", getClientToken()).add("expiration", getExpiration()).add("id", getId()).add("topicId", getTopicId()).toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StoredChannel)) {
            return false;
        }
        return getId().equals(((StoredChannel) other).getId());
    }

    public int hashCode() {
        return getId().hashCode();
    }

    public static DataStore<StoredChannel> getDefaultDataStore(DataStoreFactory dataStoreFactory) throws IOException {
        return dataStoreFactory.getDataStore(DEFAULT_DATA_STORE_ID);
    }
}
