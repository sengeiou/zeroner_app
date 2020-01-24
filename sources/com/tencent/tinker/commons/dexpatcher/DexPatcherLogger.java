package com.tencent.tinker.commons.dexpatcher;

public final class DexPatcherLogger {
    private IDexPatcherLogger loggerImpl = null;

    public interface IDexPatcherLogger {
        void d(String str);

        void e(String str);

        void i(String str);

        void v(String str);

        void w(String str);
    }

    public IDexPatcherLogger getLoggerImpl() {
        return this.loggerImpl;
    }

    public void setLoggerImpl(IDexPatcherLogger dexPatcherLogger) {
        this.loggerImpl = dexPatcherLogger;
    }

    public void v(String tag, String fmt, Object... vals) {
        if (this.loggerImpl != null) {
            String fmt2 = "[V][" + tag + "] " + fmt;
            this.loggerImpl.v((vals == null || vals.length == 0) ? fmt2 : String.format(fmt2, vals));
        }
    }

    public void d(String tag, String fmt, Object... vals) {
        if (this.loggerImpl != null) {
            String fmt2 = "[D][" + tag + "] " + fmt;
            this.loggerImpl.d((vals == null || vals.length == 0) ? fmt2 : String.format(fmt2, vals));
        }
    }

    public void i(String tag, String fmt, Object... vals) {
        if (this.loggerImpl != null) {
            String fmt2 = "[I][" + tag + "] " + fmt;
            this.loggerImpl.i((vals == null || vals.length == 0) ? fmt2 : String.format(fmt2, vals));
        }
    }

    public void w(String tag, String fmt, Object... vals) {
        if (this.loggerImpl != null) {
            String fmt2 = "[W][" + tag + "] " + fmt;
            this.loggerImpl.w((vals == null || vals.length == 0) ? fmt2 : String.format(fmt2, vals));
        }
    }

    public void e(String tag, String fmt, Object... vals) {
        if (this.loggerImpl != null) {
            String fmt2 = "[E][" + tag + "] " + fmt;
            this.loggerImpl.e((vals == null || vals.length == 0) ? fmt2 : String.format(fmt2, vals));
        }
    }
}
