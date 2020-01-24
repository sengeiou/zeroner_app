package com.facebook.stetho.inspector.helper;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.view.ViewDebug.ExportedProperty;

public class IntegerFormatter {
    private static IntegerFormatter cachedFormatter;

    private static class IntegerFormatterWithHex extends IntegerFormatter {
        private IntegerFormatterWithHex() {
            super();
        }

        @TargetApi(21)
        public String format(Integer integer, @Nullable ExportedProperty annotation) {
            if (annotation == null || !annotation.formatToHexString()) {
                return IntegerFormatter.super.format(integer, annotation);
            }
            return "0x" + Integer.toHexString(integer.intValue());
        }
    }

    public static IntegerFormatter getInstance() {
        if (cachedFormatter == null) {
            synchronized (IntegerFormatter.class) {
                if (cachedFormatter == null) {
                    if (VERSION.SDK_INT >= 21) {
                        cachedFormatter = new IntegerFormatterWithHex();
                    } else {
                        cachedFormatter = new IntegerFormatter();
                    }
                }
            }
        }
        return cachedFormatter;
    }

    private IntegerFormatter() {
    }

    public String format(Integer integer, @Nullable ExportedProperty annotation) {
        return String.valueOf(integer);
    }
}
