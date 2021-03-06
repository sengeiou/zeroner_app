package com.facebook.stetho.inspector.elements.android;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewDebug.FlagToString;
import android.view.ViewDebug.IntToString;
import com.facebook.stetho.common.ExceptionUtil;
import com.facebook.stetho.common.LogUtil;
import com.facebook.stetho.common.ReflectionUtil;
import com.facebook.stetho.common.StringUtil;
import com.facebook.stetho.common.android.ResourcesUtil;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import com.facebook.stetho.inspector.elements.AttributeAccumulator;
import com.facebook.stetho.inspector.elements.ComputedStyleAccumulator;
import com.facebook.stetho.inspector.elements.StyleAccumulator;
import com.facebook.stetho.inspector.elements.StyleRuleNameAccumulator;
import com.facebook.stetho.inspector.helper.IntegerFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.tencent.open.SocialConstants;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class ViewDescriptor extends AbstractChainedDescriptor<View> implements HighlightableDescriptor<View> {
    private static final String ACCESSIBILITY_STYLE_RULE_NAME = "Accessibility Properties";
    private static final String ID_NAME = "id";
    private static final String NONE_MAPPING = "<no mapping>";
    private static final String NONE_VALUE = "(none)";
    private static final String VIEW_STYLE_RULE_NAME = "<this_view>";
    private static final boolean sHasSupportNodeInfo = (ReflectionUtil.tryGetClassForName("android.support.v4.view.accessibility.AccessibilityNodeInfoCompat") != null);
    private final MethodInvoker mMethodInvoker;
    @GuardedBy("this")
    @Nullable
    private volatile List<ViewCSSProperty> mViewProperties;
    @Nullable
    private Pattern mWordBoundaryPattern;

    private abstract class ViewCSSProperty {
        private final ExportedProperty mAnnotation;
        private final String mCSSName;

        public abstract Object getValue(View view) throws InvocationTargetException, IllegalAccessException;

        public ViewCSSProperty(String cssName, @Nullable ExportedProperty annotation) {
            this.mCSSName = cssName;
            this.mAnnotation = annotation;
        }

        public final String getCSSName() {
            return this.mCSSName;
        }

        @Nullable
        public final ExportedProperty getAnnotation() {
            return this.mAnnotation;
        }
    }

    private final class FieldBackedCSSProperty extends ViewCSSProperty {
        private final Field mField;

        public FieldBackedCSSProperty(Field field, String cssName, @Nullable ExportedProperty annotation) {
            super(cssName, annotation);
            this.mField = field;
            this.mField.setAccessible(true);
        }

        public Object getValue(View view) throws InvocationTargetException, IllegalAccessException {
            return this.mField.get(view);
        }
    }

    private final class MethodBackedCSSProperty extends ViewCSSProperty {
        private final Method mMethod;

        public MethodBackedCSSProperty(Method method, String cssName, @Nullable ExportedProperty annotation) {
            super(cssName, annotation);
            this.mMethod = method;
            this.mMethod.setAccessible(true);
        }

        public Object getValue(View view) throws InvocationTargetException, IllegalAccessException {
            return this.mMethod.invoke(view, new Object[0]);
        }
    }

    private Pattern getWordBoundaryPattern() {
        if (this.mWordBoundaryPattern == null) {
            this.mWordBoundaryPattern = Pattern.compile("(?<=\\p{Lower})(?=\\p{Upper})");
        }
        return this.mWordBoundaryPattern;
    }

    private List<ViewCSSProperty> getViewProperties() {
        Method[] declaredMethods;
        Field[] declaredFields;
        if (this.mViewProperties == null) {
            synchronized (this) {
                if (this.mViewProperties == null) {
                    List<ViewCSSProperty> props = new ArrayList<>();
                    for (Method method : View.class.getDeclaredMethods()) {
                        ExportedProperty annotation = (ExportedProperty) method.getAnnotation(ExportedProperty.class);
                        if (annotation != null) {
                            props.add(new MethodBackedCSSProperty(method, convertViewPropertyNameToCSSName(method.getName()), annotation));
                        }
                    }
                    for (Field field : View.class.getDeclaredFields()) {
                        ExportedProperty annotation2 = (ExportedProperty) field.getAnnotation(ExportedProperty.class);
                        if (annotation2 != null) {
                            props.add(new FieldBackedCSSProperty(field, convertViewPropertyNameToCSSName(field.getName()), annotation2));
                        }
                    }
                    Collections.sort(props, new Comparator<ViewCSSProperty>() {
                        public int compare(ViewCSSProperty lhs, ViewCSSProperty rhs) {
                            return lhs.getCSSName().compareTo(rhs.getCSSName());
                        }
                    });
                    this.mViewProperties = Collections.unmodifiableList(props);
                }
            }
        }
        return this.mViewProperties;
    }

    public ViewDescriptor() {
        this(new MethodInvoker());
    }

    public ViewDescriptor(MethodInvoker methodInvoker) {
        this.mMethodInvoker = methodInvoker;
    }

    /* access modifiers changed from: protected */
    public String onGetNodeName(View element) {
        String className = element.getClass().getName();
        return StringUtil.removePrefix(className, "android.view.", StringUtil.removePrefix(className, "android.widget."));
    }

    /* access modifiers changed from: protected */
    public void onGetAttributes(View element, AttributeAccumulator attributes) {
        String id = getIdAttribute(element);
        if (id != null) {
            attributes.store("id", id);
        }
    }

    /* access modifiers changed from: protected */
    public void onSetAttributesAsText(View element, String text) {
        for (Entry<String, String> entry : parseSetAttributesAsTextArg(text).entrySet()) {
            this.mMethodInvoker.invoke(element, "set" + capitalize((String) entry.getKey()), (String) entry.getValue());
        }
    }

    @Nullable
    private static String getIdAttribute(View element) {
        int id = element.getId();
        if (id == -1) {
            return null;
        }
        return ResourcesUtil.getIdStringQuietly(element, element.getResources(), id);
    }

    @Nullable
    public View getViewAndBoundsForHighlighting(View element, Rect bounds) {
        return element;
    }

    @Nullable
    public Object getElementToHighlightAtPosition(View element, int x, int y, Rect bounds) {
        bounds.set(0, 0, element.getWidth(), element.getHeight());
        return element;
    }

    /* access modifiers changed from: protected */
    public void onGetStyleRuleNames(View element, StyleRuleNameAccumulator accumulator) {
        accumulator.store(VIEW_STYLE_RULE_NAME, false);
        if (sHasSupportNodeInfo) {
            accumulator.store(ACCESSIBILITY_STYLE_RULE_NAME, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onGetStyles(View element, String ruleName, StyleAccumulator accumulator) {
        if (VIEW_STYLE_RULE_NAME.equals(ruleName)) {
            List<ViewCSSProperty> properties = getViewProperties();
            int size = properties.size();
            for (int i = 0; i < size; i++) {
                ViewCSSProperty property = (ViewCSSProperty) properties.get(i);
                try {
                    getStyleFromValue(element, property.getCSSName(), property.getValue(element), property.getAnnotation(), accumulator);
                } catch (Exception e) {
                    if ((e instanceof IllegalAccessException) || (e instanceof InvocationTargetException)) {
                        LogUtil.e((Throwable) e, "failed to get style property " + property.getCSSName() + " of element= " + element.toString());
                    } else {
                        throw ExceptionUtil.propagate(e);
                    }
                }
            }
        } else if (ACCESSIBILITY_STYLE_RULE_NAME.equals(ruleName) && sHasSupportNodeInfo) {
            boolean ignored = AccessibilityNodeInfoWrapper.getIgnored(element);
            getStyleFromValue(element, "ignored", Boolean.valueOf(ignored), null, accumulator);
            if (ignored) {
                getStyleFromValue(element, "ignored-reasons", AccessibilityNodeInfoWrapper.getIgnoredReasons(element), null, accumulator);
            }
            getStyleFromValue(element, "focusable", Boolean.valueOf(!ignored), null, accumulator);
            if (!ignored) {
                getStyleFromValue(element, "focusable-reasons", AccessibilityNodeInfoWrapper.getFocusableReasons(element), null, accumulator);
                getStyleFromValue(element, "focused", Boolean.valueOf(AccessibilityNodeInfoWrapper.getIsAccessibilityFocused(element)), null, accumulator);
                getStyleFromValue(element, SocialConstants.PARAM_COMMENT, AccessibilityNodeInfoWrapper.getDescription(element), null, accumulator);
                getStyleFromValue(element, "actions", AccessibilityNodeInfoWrapper.getActions(element), null, accumulator);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onGetComputedStyles(View element, ComputedStyleAccumulator styles) {
        styles.store("left", Integer.toString(element.getLeft()));
        styles.store("top", Integer.toString(element.getTop()));
        styles.store("right", Integer.toString(element.getRight()));
        styles.store("bottom", Integer.toString(element.getBottom()));
    }

    private static boolean canIntBeMappedToString(@Nullable ExportedProperty annotation) {
        return (annotation == null || annotation.mapping() == null || annotation.mapping().length <= 0) ? false : true;
    }

    private static String mapIntToStringUsingAnnotation(int value, @Nullable ExportedProperty annotation) {
        IntToString[] mapping;
        if (!canIntBeMappedToString(annotation)) {
            throw new IllegalStateException("Cannot map using this annotation");
        }
        for (IntToString map : annotation.mapping()) {
            if (map.from() == value) {
                return map.to();
            }
        }
        return NONE_MAPPING;
    }

    private static boolean canFlagsBeMappedToString(@Nullable ExportedProperty annotation) {
        return (annotation == null || annotation.flagMapping() == null || annotation.flagMapping().length <= 0) ? false : true;
    }

    private static String mapFlagsToStringUsingAnnotation(int value, @Nullable ExportedProperty annotation) {
        FlagToString[] flagMapping;
        boolean z;
        if (!canFlagsBeMappedToString(annotation)) {
            throw new IllegalStateException("Cannot map using this annotation");
        }
        StringBuilder stringBuilder = null;
        boolean atLeastOneFlag = false;
        for (FlagToString flagToString : annotation.flagMapping()) {
            boolean outputIf = flagToString.outputIf();
            if ((flagToString.mask() & value) == flagToString.equals()) {
                z = true;
            } else {
                z = false;
            }
            if (outputIf == z) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                if (atLeastOneFlag) {
                    stringBuilder.append(" | ");
                }
                stringBuilder.append(flagToString.name());
                atLeastOneFlag = true;
            }
        }
        if (atLeastOneFlag) {
            return stringBuilder.toString();
        }
        return NONE_MAPPING;
    }

    private String convertViewPropertyNameToCSSName(String getterName) {
        String[] words = getWordBoundaryPattern().split(getterName);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (!words[i].equals("get") && !words[i].equals("m")) {
                result.append(words[i].toLowerCase());
                if (i < words.length - 1) {
                    result.append('-');
                }
            }
        }
        return result.toString();
    }

    private void getStyleFromValue(View element, String name, Object value, @Nullable ExportedProperty annotation, StyleAccumulator styles) {
        boolean z = true;
        if (name.equals("id")) {
            getIdStyle(element, styles);
        } else if (value instanceof Integer) {
            getStyleFromInteger(name, (Integer) value, annotation, styles);
        } else if (value instanceof Float) {
            String valueOf = String.valueOf(value);
            if (((Float) value).floatValue() != 0.0f) {
                z = false;
            }
            styles.store(name, valueOf, z);
        } else if (value instanceof Boolean) {
            styles.store(name, String.valueOf(value), false);
        } else if (value instanceof Short) {
            String valueOf2 = String.valueOf(value);
            if (((Short) value).shortValue() != 0) {
                z = false;
            }
            styles.store(name, valueOf2, z);
        } else if (value instanceof Long) {
            String valueOf3 = String.valueOf(value);
            if (((Long) value).longValue() != 0) {
                z = false;
            }
            styles.store(name, valueOf3, z);
        } else if (value instanceof Double) {
            String valueOf4 = String.valueOf(value);
            if (((Double) value).doubleValue() != Utils.DOUBLE_EPSILON) {
                z = false;
            }
            styles.store(name, valueOf4, z);
        } else if (value instanceof Byte) {
            String valueOf5 = String.valueOf(value);
            if (((Byte) value).byteValue() != 0) {
                z = false;
            }
            styles.store(name, valueOf5, z);
        } else if (value instanceof Character) {
            String valueOf6 = String.valueOf(value);
            if (((Character) value).charValue() != 0) {
                z = false;
            }
            styles.store(name, valueOf6, z);
        } else if (value instanceof CharSequence) {
            String valueOf7 = String.valueOf(value);
            if (((CharSequence) value).length() != 0) {
                z = false;
            }
            styles.store(name, valueOf7, z);
        } else {
            getStylesFromObject(element, name, value, annotation, styles);
        }
    }

    private void getIdStyle(View element, StyleAccumulator styles) {
        String id = getIdAttribute(element);
        if (id == null) {
            styles.store("id", NONE_VALUE, false);
        } else {
            styles.store("id", id, false);
        }
    }

    private void getStyleFromInteger(String name, Integer value, @Nullable ExportedProperty annotation, StyleAccumulator styles) {
        String intValueStr = IntegerFormatter.getInstance().format(value, annotation);
        if (canIntBeMappedToString(annotation)) {
            styles.store(name, intValueStr + " (" + mapIntToStringUsingAnnotation(value.intValue(), annotation) + ")", false);
        } else if (canFlagsBeMappedToString(annotation)) {
            styles.store(name, intValueStr + " (" + mapFlagsToStringUsingAnnotation(value.intValue(), annotation) + ")", false);
        } else {
            Boolean defaultValue = Boolean.valueOf(true);
            if (value.intValue() != 0 || canFlagsBeMappedToString(annotation) || canIntBeMappedToString(annotation)) {
                defaultValue = Boolean.valueOf(false);
            }
            styles.store(name, intValueStr, defaultValue.booleanValue());
        }
    }

    private void getStylesFromObject(View view, String name, Object value, @Nullable ExportedProperty annotation, StyleAccumulator styles) {
        Field[] fields;
        String propertyName;
        if (annotation != null && annotation.deepExport() && value != null) {
            for (Field field : value.getClass().getFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    try {
                        field.setAccessible(true);
                        Object propertyValue = field.get(value);
                        String propertyName2 = field.getName();
                        char c = 65535;
                        switch (propertyName2.hashCode()) {
                            case -599904534:
                                if (propertyName2.equals("rightMargin")) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case -414179485:
                                if (propertyName2.equals("topMargin")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 1928835221:
                                if (propertyName2.equals("leftMargin")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 2064613305:
                                if (propertyName2.equals("bottomMargin")) {
                                    c = 0;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                propertyName = "margin-bottom";
                                break;
                            case 1:
                                propertyName = "margin-top";
                                break;
                            case 2:
                                propertyName = "margin-left";
                                break;
                            case 3:
                                propertyName = "margin-right";
                                break;
                            default:
                                String annotationPrefix = annotation.prefix();
                                if (annotationPrefix != null) {
                                    propertyName2 = annotationPrefix + propertyName2;
                                }
                                propertyName = convertViewPropertyNameToCSSName(propertyName2);
                                break;
                        }
                        getStyleFromValue(view, propertyName, propertyValue, (ExportedProperty) field.getAnnotation(ExportedProperty.class), styles);
                    } catch (IllegalAccessException e) {
                        LogUtil.e((Throwable) e, "failed to get property of name: \"" + name + "\" of object: " + String.valueOf(value));
                        return;
                    }
                }
            }
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0 || Character.isTitleCase(str.charAt(0))) {
            return str;
        }
        StringBuilder buffer = new StringBuilder(str);
        buffer.setCharAt(0, Character.toTitleCase(buffer.charAt(0)));
        return buffer.toString();
    }
}
