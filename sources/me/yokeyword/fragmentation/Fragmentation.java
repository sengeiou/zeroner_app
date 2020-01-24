package me.yokeyword.fragmentation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class Fragmentation {
    public static final int BUBBLE = 2;
    static volatile Fragmentation INSTANCE = null;
    public static final int NONE = 0;
    public static final int SHAKE = 1;

    /* renamed from: debug reason: collision with root package name */
    private boolean f411debug;
    private ExceptionHandler handler;
    private int mode = 2;

    @Retention(RetentionPolicy.SOURCE)
    @interface StackViewMode {
    }

    public static class FragmentationBuilder {
        /* access modifiers changed from: private */

        /* renamed from: debug reason: collision with root package name */
        public boolean f412debug;
        /* access modifiers changed from: private */
        public ExceptionHandler handler;
        /* access modifiers changed from: private */
        public int mode;

        public FragmentationBuilder debug(boolean debug2) {
            this.f412debug = debug2;
            return this;
        }

        public FragmentationBuilder stackViewMode(int mode2) {
            this.mode = mode2;
            return this;
        }

        public FragmentationBuilder handleException(ExceptionHandler handler2) {
            this.handler = handler2;
            return this;
        }

        public Fragmentation install() {
            Fragmentation fragmentation;
            synchronized (Fragmentation.class) {
                if (Fragmentation.INSTANCE != null) {
                    throw new RuntimeException("Default instance already exists. It may be only set once before it's used the first time to ensure consistent behavior.");
                }
                Fragmentation.INSTANCE = new Fragmentation(this);
                fragmentation = Fragmentation.INSTANCE;
            }
            return fragmentation;
        }
    }

    public static Fragmentation getDefault() {
        if (INSTANCE == null) {
            synchronized (Fragmentation.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Fragmentation(new FragmentationBuilder());
                }
            }
        }
        return INSTANCE;
    }

    Fragmentation(FragmentationBuilder builder) {
        this.f411debug = builder.f412debug;
        if (this.f411debug) {
            this.mode = builder.mode;
        } else {
            this.mode = 0;
        }
        this.handler = builder.handler;
    }

    public boolean isDebug() {
        return this.f411debug;
    }

    public void setDebug(boolean debug2) {
        this.f411debug = debug2;
    }

    public ExceptionHandler getHandler() {
        return this.handler;
    }

    public void setHandler(ExceptionHandler handler2) {
        this.handler = handler2;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode2) {
        this.mode = mode2;
    }

    public static FragmentationBuilder builder() {
        return new FragmentationBuilder();
    }
}
