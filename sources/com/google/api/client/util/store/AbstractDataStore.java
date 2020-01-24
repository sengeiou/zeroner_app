package com.google.api.client.util.store;

import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractDataStore<V extends Serializable> implements DataStore<V> {
    private final DataStoreFactory dataStoreFactory;
    private final String id;

    protected AbstractDataStore(DataStoreFactory dataStoreFactory2, String id2) {
        this.dataStoreFactory = (DataStoreFactory) Preconditions.checkNotNull(dataStoreFactory2);
        this.id = (String) Preconditions.checkNotNull(id2);
    }

    public DataStoreFactory getDataStoreFactory() {
        return this.dataStoreFactory;
    }

    public final String getId() {
        return this.id;
    }

    public boolean containsKey(String key) throws IOException {
        return get(key) != null;
    }

    public boolean containsValue(V value) throws IOException {
        return values().contains(value);
    }

    public boolean isEmpty() throws IOException {
        return size() == 0;
    }

    public int size() throws IOException {
        return keySet().size();
    }
}
