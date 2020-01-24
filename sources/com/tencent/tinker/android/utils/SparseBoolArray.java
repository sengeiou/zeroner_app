package com.tencent.tinker.android.utils;

public class SparseBoolArray implements Cloneable {
    private static final boolean[] EMPTY_BOOL_ARRAY = new boolean[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private int[] mKeys;
    private int mSize;
    private boolean[] mValues;

    public static class KeyNotFoundException extends Exception {
        public KeyNotFoundException() {
        }

        public KeyNotFoundException(String msg) {
            super(msg);
        }
    }

    public SparseBoolArray() {
        this(10);
    }

    public SparseBoolArray(int initialCapacity) {
        if (initialCapacity == 0) {
            this.mKeys = EMPTY_INT_ARRAY;
            this.mValues = EMPTY_BOOL_ARRAY;
        } else {
            this.mKeys = new int[initialCapacity];
            this.mValues = new boolean[initialCapacity];
        }
        this.mSize = 0;
    }

    private static int growSize(int currentSize) {
        if (currentSize <= 4) {
            return 8;
        }
        return (currentSize >> 1) + currentSize;
    }

    public SparseBoolArray clone() {
        SparseBoolArray clone = null;
        try {
            clone = (SparseBoolArray) super.clone();
            clone.mKeys = (int[]) this.mKeys.clone();
            clone.mValues = (boolean[]) this.mValues.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            return clone;
        }
    }

    public boolean get(int key) throws KeyNotFoundException {
        int i = binarySearch(this.mKeys, this.mSize, key);
        if (i >= 0) {
            return this.mValues[i];
        }
        throw new KeyNotFoundException("" + key);
    }

    public void delete(int key) {
        int i = binarySearch(this.mKeys, this.mSize, key);
        if (i >= 0) {
            removeAt(i);
        }
    }

    public void removeAt(int index) {
        System.arraycopy(this.mKeys, index + 1, this.mKeys, index, this.mSize - (index + 1));
        System.arraycopy(this.mValues, index + 1, this.mValues, index, this.mSize - (index + 1));
        this.mSize--;
    }

    public void put(int key, boolean value) {
        int i = binarySearch(this.mKeys, this.mSize, key);
        if (i >= 0) {
            this.mValues[i] = value;
            return;
        }
        int i2 = i ^ -1;
        this.mKeys = insertElementIntoIntArray(this.mKeys, this.mSize, i2, key);
        this.mValues = insertElementIntoBoolArray(this.mValues, this.mSize, i2, value);
        this.mSize++;
    }

    public int size() {
        return this.mSize;
    }

    public int keyAt(int index) {
        return this.mKeys[index];
    }

    public boolean valueAt(int index) {
        return this.mValues[index];
    }

    public int indexOfKey(int key) {
        return binarySearch(this.mKeys, this.mSize, key);
    }

    public boolean containsKey(int key) {
        return indexOfKey(key) >= 0;
    }

    public int indexOfValue(boolean value) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int key, boolean value) {
        if (this.mSize == 0 || key > this.mKeys[this.mSize - 1]) {
            this.mKeys = appendElementIntoIntArray(this.mKeys, this.mSize, key);
            this.mValues = appendElementIntoBoolArray(this.mValues, this.mSize, value);
            this.mSize++;
            return;
        }
        put(key, value);
    }

    private int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int midVal = array[mid];
            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal <= value) {
                return mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo ^ -1;
    }

    private int[] appendElementIntoIntArray(int[] array, int currentSize, int element) {
        if (currentSize > array.length) {
            throw new IllegalArgumentException("Bad currentSize, originalSize: " + array.length + " currentSize: " + currentSize);
        }
        if (currentSize + 1 > array.length) {
            int[] newArray = new int[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    private boolean[] appendElementIntoBoolArray(boolean[] array, int currentSize, boolean element) {
        if (currentSize > array.length) {
            throw new IllegalArgumentException("Bad currentSize, originalSize: " + array.length + " currentSize: " + currentSize);
        }
        if (currentSize + 1 > array.length) {
            boolean[] newArray = new boolean[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    private int[] insertElementIntoIntArray(int[] array, int currentSize, int index, int element) {
        if (currentSize > array.length) {
            throw new IllegalArgumentException("Bad currentSize, originalSize: " + array.length + " currentSize: " + currentSize);
        } else if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        } else {
            int[] newArray = new int[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = element;
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            return newArray;
        }
    }

    private boolean[] insertElementIntoBoolArray(boolean[] array, int currentSize, int index, boolean element) {
        if (currentSize > array.length) {
            throw new IllegalArgumentException("Bad currentSize, originalSize: " + array.length + " currentSize: " + currentSize);
        } else if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        } else {
            boolean[] newArray = new boolean[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = element;
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            return newArray;
        }
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 28);
        buffer.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(keyAt(i));
            buffer.append('=');
            buffer.append(valueAt(i));
        }
        buffer.append('}');
        return buffer.toString();
    }
}
