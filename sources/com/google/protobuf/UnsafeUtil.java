package com.google.protobuf;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class UnsafeUtil {
    private static final long BOOLEAN_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(boolean[].class));
    private static final long BOOLEAN_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(boolean[].class));
    private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());
    /* access modifiers changed from: private */
    public static final long BYTE_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(byte[].class));
    private static final long DOUBLE_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(double[].class));
    private static final long DOUBLE_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(double[].class));
    private static final long FLOAT_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(float[].class));
    private static final long FLOAT_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(float[].class));
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
    private static final long INT_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(int[].class));
    private static final long INT_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(int[].class));
    private static final long LONG_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(long[].class));
    private static final long LONG_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(long[].class));
    private static final MemoryAccessor MEMORY_ACCESSOR = getMemoryAccessor();
    private static final long OBJECT_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(Object[].class));
    private static final long OBJECT_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(Object[].class));
    private static final long STRING_VALUE_OFFSET = fieldOffset(stringValueField());
    private static final Unsafe UNSAFE = getUnsafe();
    private static final Logger logger = Logger.getLogger(UnsafeUtil.class.getName());

    private static final class JvmMemoryAccessor extends MemoryAccessor {
        JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        public byte getByte(long address) {
            return this.unsafe.getByte(address);
        }

        public void putByte(long address, byte value) {
            this.unsafe.putByte(address, value);
        }

        public int getInt(long address) {
            return this.unsafe.getInt(address);
        }

        public void putInt(long address, int value) {
            this.unsafe.putInt(address, value);
        }

        public long getLong(long address) {
            return this.unsafe.getLong(address);
        }

        public void putLong(long address, long value) {
            this.unsafe.putLong(address, value);
        }

        public byte getByte(Object target, long offset) {
            return this.unsafe.getByte(target, offset);
        }

        public void putByte(Object target, long offset, byte value) {
            this.unsafe.putByte(target, offset, value);
        }

        public boolean getBoolean(Object target, long offset) {
            return this.unsafe.getBoolean(target, offset);
        }

        public void putBoolean(Object target, long offset, boolean value) {
            this.unsafe.putBoolean(target, offset, value);
        }

        public float getFloat(Object target, long offset) {
            return this.unsafe.getFloat(target, offset);
        }

        public void putFloat(Object target, long offset, float value) {
            this.unsafe.putFloat(target, offset, value);
        }

        public double getDouble(Object target, long offset) {
            return this.unsafe.getDouble(target, offset);
        }

        public void putDouble(Object target, long offset, double value) {
            this.unsafe.putDouble(target, offset, value);
        }

        public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
            this.unsafe.copyMemory(null, srcOffset, target, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + targetIndex, length);
        }

        public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
            this.unsafe.copyMemory(src, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + srcIndex, null, targetOffset, length);
        }

        public Object getStaticObject(Field field) {
            return getObject(this.unsafe.staticFieldBase(field), this.unsafe.staticFieldOffset(field));
        }
    }

    private static abstract class MemoryAccessor {
        Unsafe unsafe;

        public abstract void copyMemory(long j, byte[] bArr, long j2, long j3);

        public abstract void copyMemory(byte[] bArr, long j, long j2, long j3);

        public abstract boolean getBoolean(Object obj, long j);

        public abstract byte getByte(long j);

        public abstract byte getByte(Object obj, long j);

        public abstract double getDouble(Object obj, long j);

        public abstract float getFloat(Object obj, long j);

        public abstract int getInt(long j);

        public abstract long getLong(long j);

        public abstract Object getStaticObject(Field field);

        public abstract void putBoolean(Object obj, long j, boolean z);

        public abstract void putByte(long j, byte b);

        public abstract void putByte(Object obj, long j, byte b);

        public abstract void putDouble(Object obj, long j, double d);

        public abstract void putFloat(Object obj, long j, float f);

        public abstract void putInt(long j, int i);

        public abstract void putLong(long j, long j2);

        MemoryAccessor(Unsafe unsafe2) {
            this.unsafe = unsafe2;
        }

        public final long objectFieldOffset(Field field) {
            return this.unsafe.objectFieldOffset(field);
        }

        public final int getInt(Object target, long offset) {
            return this.unsafe.getInt(target, offset);
        }

        public final void putInt(Object target, long offset, int value) {
            this.unsafe.putInt(target, offset, value);
        }

        public final long getLong(Object target, long offset) {
            return this.unsafe.getLong(target, offset);
        }

        public final void putLong(Object target, long offset, long value) {
            this.unsafe.putLong(target, offset, value);
        }

        public final Object getObject(Object target, long offset) {
            return this.unsafe.getObject(target, offset);
        }

        public final void putObject(Object target, long offset, Object value) {
            this.unsafe.putObject(target, offset, value);
        }

        public final int arrayBaseOffset(Class<?> clazz) {
            return this.unsafe.arrayBaseOffset(clazz);
        }

        public final int arrayIndexScale(Class<?> clazz) {
            return this.unsafe.arrayIndexScale(clazz);
        }
    }

    private UnsafeUtil() {
    }

    static boolean hasUnsafeArrayOperations() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }

    static boolean hasUnsafeByteBufferOperations() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static long objectFieldOffset(Field field) {
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    private static int arrayBaseOffset(Class<?> clazz) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayBaseOffset(clazz);
        }
        return -1;
    }

    private static int arrayIndexScale(Class<?> clazz) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayIndexScale(clazz);
        }
        return -1;
    }

    static byte getByte(Object target, long offset) {
        return MEMORY_ACCESSOR.getByte(target, offset);
    }

    static void putByte(Object target, long offset, byte value) {
        MEMORY_ACCESSOR.putByte(target, offset, value);
    }

    static int getInt(Object target, long offset) {
        return MEMORY_ACCESSOR.getInt(target, offset);
    }

    static void putInt(Object target, long offset, int value) {
        MEMORY_ACCESSOR.putInt(target, offset, value);
    }

    static long getLong(Object target, long offset) {
        return MEMORY_ACCESSOR.getLong(target, offset);
    }

    static void putLong(Object target, long offset, long value) {
        MEMORY_ACCESSOR.putLong(target, offset, value);
    }

    static boolean getBoolean(Object target, long offset) {
        return MEMORY_ACCESSOR.getBoolean(target, offset);
    }

    static void putBoolean(Object target, long offset, boolean value) {
        MEMORY_ACCESSOR.putBoolean(target, offset, value);
    }

    static float getFloat(Object target, long offset) {
        return MEMORY_ACCESSOR.getFloat(target, offset);
    }

    static void putFloat(Object target, long offset, float value) {
        MEMORY_ACCESSOR.putFloat(target, offset, value);
    }

    static double getDouble(Object target, long offset) {
        return MEMORY_ACCESSOR.getDouble(target, offset);
    }

    static void putDouble(Object target, long offset, double value) {
        MEMORY_ACCESSOR.putDouble(target, offset, value);
    }

    static Object getObject(Object target, long offset) {
        return MEMORY_ACCESSOR.getObject(target, offset);
    }

    static void putObject(Object target, long offset, Object value) {
        MEMORY_ACCESSOR.putObject(target, offset, value);
    }

    static byte getByte(byte[] target, long index) {
        return MEMORY_ACCESSOR.getByte(target, BYTE_ARRAY_BASE_OFFSET + index);
    }

    static void putByte(byte[] target, long index, byte value) {
        MEMORY_ACCESSOR.putByte(target, BYTE_ARRAY_BASE_OFFSET + index, value);
    }

    static int getInt(int[] target, long index) {
        return MEMORY_ACCESSOR.getInt(target, INT_ARRAY_BASE_OFFSET + (INT_ARRAY_INDEX_SCALE * index));
    }

    static void putInt(int[] target, long index, int value) {
        MEMORY_ACCESSOR.putInt(target, INT_ARRAY_BASE_OFFSET + (INT_ARRAY_INDEX_SCALE * index), value);
    }

    static long getLong(long[] target, long index) {
        return MEMORY_ACCESSOR.getLong(target, LONG_ARRAY_BASE_OFFSET + (LONG_ARRAY_INDEX_SCALE * index));
    }

    static void putLong(long[] target, long index, long value) {
        MEMORY_ACCESSOR.putLong(target, LONG_ARRAY_BASE_OFFSET + (LONG_ARRAY_INDEX_SCALE * index), value);
    }

    static boolean getBoolean(boolean[] target, long index) {
        return MEMORY_ACCESSOR.getBoolean(target, BOOLEAN_ARRAY_BASE_OFFSET + (BOOLEAN_ARRAY_INDEX_SCALE * index));
    }

    static void putBoolean(boolean[] target, long index, boolean value) {
        MEMORY_ACCESSOR.putBoolean(target, BOOLEAN_ARRAY_BASE_OFFSET + (BOOLEAN_ARRAY_INDEX_SCALE * index), value);
    }

    static float getFloat(float[] target, long index) {
        return MEMORY_ACCESSOR.getFloat(target, FLOAT_ARRAY_BASE_OFFSET + (FLOAT_ARRAY_INDEX_SCALE * index));
    }

    static void putFloat(float[] target, long index, float value) {
        MEMORY_ACCESSOR.putFloat(target, FLOAT_ARRAY_BASE_OFFSET + (FLOAT_ARRAY_INDEX_SCALE * index), value);
    }

    static double getDouble(double[] target, long index) {
        return MEMORY_ACCESSOR.getDouble(target, DOUBLE_ARRAY_BASE_OFFSET + (DOUBLE_ARRAY_INDEX_SCALE * index));
    }

    static void putDouble(double[] target, long index, double value) {
        MEMORY_ACCESSOR.putDouble(target, DOUBLE_ARRAY_BASE_OFFSET + (DOUBLE_ARRAY_INDEX_SCALE * index), value);
    }

    static Object getObject(Object[] target, long index) {
        return MEMORY_ACCESSOR.getObject(target, OBJECT_ARRAY_BASE_OFFSET + (OBJECT_ARRAY_INDEX_SCALE * index));
    }

    static void putObject(Object[] target, long index, Object value) {
        MEMORY_ACCESSOR.putObject(target, OBJECT_ARRAY_BASE_OFFSET + (OBJECT_ARRAY_INDEX_SCALE * index), value);
    }

    static void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
        MEMORY_ACCESSOR.copyMemory(src, srcIndex, targetOffset, length);
    }

    static void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
        MEMORY_ACCESSOR.copyMemory(srcOffset, target, targetIndex, length);
    }

    static void copyMemory(byte[] src, long srcIndex, byte[] target, long targetIndex, long length) {
        System.arraycopy(src, (int) srcIndex, target, (int) targetIndex, (int) length);
    }

    static byte getByte(long address) {
        return MEMORY_ACCESSOR.getByte(address);
    }

    static void putByte(long address, byte value) {
        MEMORY_ACCESSOR.putByte(address, value);
    }

    static int getInt(long address) {
        return MEMORY_ACCESSOR.getInt(address);
    }

    static void putInt(long address, int value) {
        MEMORY_ACCESSOR.putInt(address, value);
    }

    static long getLong(long address) {
        return MEMORY_ACCESSOR.getLong(address);
    }

    static void putLong(long address, long value) {
        MEMORY_ACCESSOR.putLong(address, value);
    }

    static long addressOffset(ByteBuffer buffer) {
        return MEMORY_ACCESSOR.getLong(buffer, BUFFER_ADDRESS_OFFSET);
    }

    static String moveToString(char[] chars) {
        if (STRING_VALUE_OFFSET == -1) {
            return new String(chars);
        }
        try {
            String str = (String) UNSAFE.allocateInstance(String.class);
            putObject((Object) str, STRING_VALUE_OFFSET, (Object) chars);
            return str;
        } catch (InstantiationException e) {
            return new String(chars);
        }
    }

    static Object getStaticObject(Field field) {
        return MEMORY_ACCESSOR.getStaticObject(field);
    }

    static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Field[] declaredFields;
                    Class<Unsafe> cls = Unsafe.class;
                    for (Field f : cls.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get(null);
                        if (cls.isInstance(x)) {
                            return (Unsafe) cls.cast(x);
                        }
                    }
                    return null;
                }
            });
        } catch (Throwable th) {
            return unsafe;
        }
    }

    private static MemoryAccessor getMemoryAccessor() {
        if (UNSAFE == null) {
            return null;
        }
        return new JvmMemoryAccessor(UNSAFE);
    }

    private static boolean supportsUnsafeArrayOperations() {
        if (UNSAFE == null) {
            return false;
        }
        try {
            Class<?> clazz = UNSAFE.getClass();
            clazz.getMethod("objectFieldOffset", new Class[]{Field.class});
            clazz.getMethod("arrayBaseOffset", new Class[]{Class.class});
            clazz.getMethod("arrayIndexScale", new Class[]{Class.class});
            clazz.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            clazz.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            clazz.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            clazz.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            clazz.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            clazz.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            clazz.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable e) {
            logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + e);
            return false;
        }
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        if (UNSAFE == null) {
            return false;
        }
        try {
            Class<?> clazz = UNSAFE.getClass();
            clazz.getMethod("objectFieldOffset", new Class[]{Field.class});
            clazz.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (bufferAddressField() == null) {
                return false;
            }
            clazz.getMethod("getByte", new Class[]{Long.TYPE});
            clazz.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            clazz.getMethod("getInt", new Class[]{Long.TYPE});
            clazz.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            clazz.getMethod("getLong", new Class[]{Long.TYPE});
            clazz.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            clazz.getMethod("copyMemory", new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            clazz.getMethod("copyMemory", new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable e) {
            logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + e);
            return false;
        }
    }

    private static Field bufferAddressField() {
        Field field = field(Buffer.class, "address");
        if (field == null || field.getType() != Long.TYPE) {
            return null;
        }
        return field;
    }

    private static Field stringValueField() {
        Field field = field(String.class, "value");
        if (field == null || field.getType() != char[].class) {
            return null;
        }
        return field;
    }

    private static long fieldOffset(Field field) {
        if (field == null || MEMORY_ACCESSOR == null) {
            return -1;
        }
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    private static Field field(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (Throwable th) {
            return null;
        }
    }
}
