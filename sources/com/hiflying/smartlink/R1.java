package com.hiflying.smartlink;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.lang.reflect.Field;

public class R1 {
    private static Context context;

    public static void initContext(Context _context) {
        context = _context;
    }

    public static int anim(String name) {
        return getIdentifier(context, name, "anim");
    }

    public static int animator(String name) {
        return getIdentifier(context, name, "animator");
    }

    public static int array(String name) {
        return getIdentifier(context, name, "array");
    }

    public static int attr(String name) {
        return getIdentifier(context, name, "attr");
    }

    public static int color(String name) {
        return getIdentifier(context, name, "color");
    }

    public static int dimen(String name) {
        return getIdentifier(context, name, "dimen");
    }

    public static int drawable(String name) {
        return getIdentifier(context, name, "drawable");
    }

    public static int id(String name) {
        return getIdentifier(context, name, "id");
    }

    public static int integer(String name) {
        return getIdentifier(context, name, "integer");
    }

    public static int layout(String name) {
        return getIdentifier(context, name, "layout");
    }

    public static int raw(String name) {
        return getIdentifier(context, name, ShareConstants.DEXMODE_RAW);
    }

    public static int string(String name) {
        return getIdentifier(context, name, "string");
    }

    public static int style(String name) {
        return getIdentifier(context, name, "style");
    }

    public static int[] styleable(String name) {
        return (int[]) getFieldFromStyleable(context, name);
    }

    public static <T> T styleable(String name, Class<T> cls) {
        return getFieldFromStyleable(context, name);
    }

    private static int getIdentifier(Context context2, String name, String type) {
        if (context2 == null) {
            new NullPointerException("Must call initContext(Context _context), recommend application context");
        }
        int resource = context2.getResources().getIdentifier(name, type, context2.getPackageName());
        if (resource != 0) {
            return resource;
        }
        throw new NotFoundException(String.format("Resource for id R.%s.%s not found!", new Object[]{type, name}));
    }

    public static final <T> T getFieldFromStyleable(Context context2, String name) {
        try {
            Field field = Class.forName(context2.getPackageName() + ".R$styleable").getField(name);
            if (field != null) {
                return field.get(null);
            }
            return null;
        } catch (Throwable t) {
            ThrowableExtension.printStackTrace(t);
            return null;
        }
    }
}
