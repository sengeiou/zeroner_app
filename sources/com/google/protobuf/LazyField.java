package com.google.protobuf;

import java.util.Iterator;
import java.util.Map.Entry;

public class LazyField extends LazyFieldLite {
    private final MessageLite defaultInstance;

    static class LazyEntry<K> implements Entry<K, Object> {
        private Entry<K, LazyField> entry;

        private LazyEntry(Entry<K, LazyField> entry2) {
            this.entry = entry2;
        }

        public K getKey() {
            return this.entry.getKey();
        }

        public Object getValue() {
            LazyField field = (LazyField) this.entry.getValue();
            if (field == null) {
                return null;
            }
            return field.getValue();
        }

        public LazyField getField() {
            return (LazyField) this.entry.getValue();
        }

        public Object setValue(Object value) {
            if (value instanceof MessageLite) {
                return ((LazyField) this.entry.getValue()).setValue((MessageLite) value);
            }
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
    }

    static class LazyIterator<K> implements Iterator<Entry<K, Object>> {
        private Iterator<Entry<K, Object>> iterator;

        public LazyIterator(Iterator<Entry<K, Object>> iterator2) {
            this.iterator = iterator2;
        }

        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        public Entry<K, Object> next() {
            Entry<K, ?> entry = (Entry) this.iterator.next();
            if (entry.getValue() instanceof LazyField) {
                return new LazyEntry<>(entry);
            }
            return entry;
        }

        public void remove() {
            this.iterator.remove();
        }
    }

    public LazyField(MessageLite defaultInstance2, ExtensionRegistryLite extensionRegistry, ByteString bytes) {
        super(extensionRegistry, bytes);
        this.defaultInstance = defaultInstance2;
    }

    public boolean containsDefaultInstance() {
        return super.containsDefaultInstance() || this.value == this.defaultInstance;
    }

    public MessageLite getValue() {
        return getValue(this.defaultInstance);
    }

    public int hashCode() {
        return getValue().hashCode();
    }

    public boolean equals(Object obj) {
        return getValue().equals(obj);
    }

    public String toString() {
        return getValue().toString();
    }
}
