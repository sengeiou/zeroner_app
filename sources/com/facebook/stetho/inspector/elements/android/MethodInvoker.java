package com.facebook.stetho.inspector.elements.android;

import com.facebook.stetho.common.LogUtil;
import com.facebook.stetho.common.Util;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class MethodInvoker {
    private static final List<TypedMethodInvoker<?>> invokers = Arrays.asList(new TypedMethodInvoker[]{new StringMethodInvoker(), new CharSequenceMethodInvoker(), new IntegerMethodInvoker(), new FloatMethodInvoker(), new BooleanMethodInvoker()});

    private static class BooleanMethodInvoker extends TypedMethodInvoker<Boolean> {
        BooleanMethodInvoker() {
            super(Boolean.TYPE);
        }

        /* access modifiers changed from: 0000 */
        public Boolean convertArgument(String argument) {
            return Boolean.valueOf(Boolean.parseBoolean(argument));
        }
    }

    private static class CharSequenceMethodInvoker extends TypedMethodInvoker<CharSequence> {
        CharSequenceMethodInvoker() {
            super(CharSequence.class);
        }

        /* access modifiers changed from: 0000 */
        public CharSequence convertArgument(String argument) {
            return argument;
        }
    }

    private static class FloatMethodInvoker extends TypedMethodInvoker<Float> {
        FloatMethodInvoker() {
            super(Float.TYPE);
        }

        /* access modifiers changed from: 0000 */
        public Float convertArgument(String argument) {
            return Float.valueOf(Float.parseFloat(argument));
        }
    }

    private static class IntegerMethodInvoker extends TypedMethodInvoker<Integer> {
        IntegerMethodInvoker() {
            super(Integer.TYPE);
        }

        /* access modifiers changed from: 0000 */
        public Integer convertArgument(String argument) {
            return Integer.valueOf(Integer.parseInt(argument));
        }
    }

    private static class StringMethodInvoker extends TypedMethodInvoker<String> {
        StringMethodInvoker() {
            super(String.class);
        }

        /* access modifiers changed from: 0000 */
        public String convertArgument(String argument) {
            return argument;
        }
    }

    private static abstract class TypedMethodInvoker<T> {
        private final Class<T> mArgType;

        /* access modifiers changed from: 0000 */
        public abstract T convertArgument(String str);

        TypedMethodInvoker(Class<T> argType) {
            this.mArgType = argType;
        }

        /* access modifiers changed from: 0000 */
        public boolean invoke(Object receiver, String methodName, String argument) {
            try {
                receiver.getClass().getMethod(methodName, new Class[]{this.mArgType}).invoke(receiver, new Object[]{convertArgument(argument)});
                return true;
            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e2) {
                LogUtil.w("InvocationTargetException: " + e2.getMessage());
            } catch (IllegalAccessException e3) {
                LogUtil.w("IllegalAccessException: " + e3.getMessage());
            } catch (IllegalArgumentException e4) {
                LogUtil.w("IllegalArgumentException: " + e4.getMessage());
            }
            return false;
        }
    }

    public void invoke(Object receiver, String methodName, String argument) {
        Util.throwIfNull(receiver, methodName, argument);
        int size = invokers.size();
        int i = 0;
        while (i < size) {
            if (!((TypedMethodInvoker) invokers.get(i)).invoke(receiver, methodName, argument)) {
                i++;
            } else {
                return;
            }
        }
        LogUtil.w("Method with name " + methodName + " not found for any of the MethodInvoker supported argument types.");
    }
}
