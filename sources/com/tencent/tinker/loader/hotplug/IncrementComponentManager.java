package com.tencent.tinker.loader.hotplug;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.iwown.my_module.model.ThemeConfig;
import com.iwown.my_module.utility.Constants;
import com.tencent.open.SocialConstants;
import com.tencent.tauth.AuthActivity;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.litepal.util.Const.TableSchema;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class IncrementComponentManager {
    private static final String TAG = "Tinker.IncrementCompMgr";
    private static final int TAG_ACTIVITY = 0;
    private static final int TAG_PROVIDER = 2;
    private static final int TAG_RECEIVER = 3;
    private static final int TAG_SERVICE = 1;
    private static final AttrTranslator<ActivityInfo> sActivityInfoAttrTranslator = new AttrTranslator<ActivityInfo>() {
        /* access modifiers changed from: 0000 */
        public void onInit(Context context, int tagType, XmlPullParser parser) {
            if (tagType == 0) {
                try {
                    if (parser.getEventType() != 2 || !EnvConsts.ACTIVITY_MANAGER_SRVNAME.equals(parser.getName())) {
                        throw new IllegalStateException("unexpected xml parser state when parsing incremental component manifest.");
                    }
                } catch (XmlPullParserException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void onTranslate(Context context, int tagType, String attrName, String attrValue, ActivityInfo result) {
            if (TableSchema.COLUMN_NAME.equals(attrName)) {
                if (attrValue.charAt(0) == '.') {
                    result.name = context.getPackageName() + attrValue;
                } else {
                    result.name = attrValue;
                }
            } else if ("parentActivityName".equals(attrName)) {
                if (VERSION.SDK_INT < 16) {
                    return;
                }
                if (attrValue.charAt(0) == '.') {
                    result.parentActivityName = context.getPackageName() + attrValue;
                } else {
                    result.parentActivityName = attrValue;
                }
            } else if ("exported".equals(attrName)) {
                result.exported = "true".equalsIgnoreCase(attrValue);
            } else if ("launchMode".equals(attrName)) {
                result.launchMode = parseLaunchMode(attrValue);
            } else if (ThemeConfig.THEME_KEY.equals(attrName)) {
                result.theme = context.getResources().getIdentifier(attrValue, "style", context.getPackageName());
            } else if ("uiOptions".equals(attrName)) {
                if (VERSION.SDK_INT >= 14) {
                    result.uiOptions = Integer.decode(attrValue).intValue();
                }
            } else if ("permission".equals(attrName)) {
                result.permission = attrValue;
            } else if ("taskAffinity".equals(attrName)) {
                result.taskAffinity = attrValue;
            } else if ("multiprocess".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 1;
                } else {
                    result.flags &= -2;
                }
            } else if ("finishOnTaskLaunch".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 2;
                } else {
                    result.flags &= -3;
                }
            } else if ("clearTaskOnLaunch".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 4;
                } else {
                    result.flags &= -5;
                }
            } else if ("noHistory".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 128;
                } else {
                    result.flags &= -129;
                }
            } else if ("alwaysRetainTaskState".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 8;
                } else {
                    result.flags &= -9;
                }
            } else if ("stateNotNeeded".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 16;
                } else {
                    result.flags &= -17;
                }
            } else if ("excludeFromRecents".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 32;
                } else {
                    result.flags &= -33;
                }
            } else if ("allowTaskReparenting".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 64;
                } else {
                    result.flags &= -65;
                }
            } else if ("finishOnCloseSystemDialogs".equals(attrName)) {
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 256;
                } else {
                    result.flags &= -257;
                }
            } else if ("showOnLockScreen".equals(attrName) || "showForAllUsers".equals(attrName)) {
                if (VERSION.SDK_INT >= 23) {
                    int flag = ShareReflectUtil.getValueOfStaticIntField(ActivityInfo.class, "FLAG_SHOW_FOR_ALL_USERS", 0);
                    if ("true".equalsIgnoreCase(attrValue)) {
                        result.flags |= flag;
                    } else {
                        result.flags &= flag ^ -1;
                    }
                }
            } else if ("immersive".equals(attrName)) {
                if (VERSION.SDK_INT < 18) {
                    return;
                }
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 2048;
                } else {
                    result.flags &= -2049;
                }
            } else if ("hardwareAccelerated".equals(attrName)) {
                if (VERSION.SDK_INT < 11) {
                    return;
                }
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 512;
                } else {
                    result.flags &= -513;
                }
            } else if ("documentLaunchMode".equals(attrName)) {
                if (VERSION.SDK_INT >= 21) {
                    result.documentLaunchMode = Integer.decode(attrValue).intValue();
                }
            } else if ("maxRecents".equals(attrName)) {
                if (VERSION.SDK_INT >= 21) {
                    result.maxRecents = Integer.decode(attrValue).intValue();
                }
            } else if ("configChanges".equals(attrName)) {
                result.configChanges = Integer.decode(attrValue).intValue();
            } else if ("windowSoftInputMode".equals(attrName)) {
                result.softInputMode = Integer.decode(attrValue).intValue();
            } else if ("persistableMode".equals(attrName)) {
                if (VERSION.SDK_INT >= 21) {
                    result.persistableMode = Integer.decode(attrValue).intValue();
                }
            } else if ("allowEmbedded".equals(attrName)) {
                int flag2 = ShareReflectUtil.getValueOfStaticIntField(ActivityInfo.class, "FLAG_ALLOW_EMBEDDED", 0);
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= flag2;
                } else {
                    result.flags &= flag2 ^ -1;
                }
            } else if ("autoRemoveFromRecents".equals(attrName)) {
                if (VERSION.SDK_INT < 21) {
                    return;
                }
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 8192;
                } else {
                    result.flags &= -8193;
                }
            } else if ("relinquishTaskIdentity".equals(attrName)) {
                if (VERSION.SDK_INT < 21) {
                    return;
                }
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 4096;
                } else {
                    result.flags &= -4097;
                }
            } else if ("resumeWhilePausing".equals(attrName)) {
                if (VERSION.SDK_INT < 21) {
                    return;
                }
                if ("true".equalsIgnoreCase(attrValue)) {
                    result.flags |= 16384;
                } else {
                    result.flags &= -16385;
                }
            } else if ("screenOrientation".equals(attrName)) {
                result.screenOrientation = parseScreenOrientation(attrValue);
            } else if ("label".equals(attrName)) {
                String strOrResId = attrValue;
                int id = 0;
                try {
                    id = context.getResources().getIdentifier(strOrResId, "string", IncrementComponentManager.sPackageName);
                } catch (Throwable th) {
                }
                if (id != 0) {
                    result.labelRes = id;
                } else {
                    result.nonLocalizedLabel = strOrResId;
                }
            } else if ("icon".equals(attrName)) {
                try {
                    result.icon = context.getResources().getIdentifier(attrValue, null, IncrementComponentManager.sPackageName);
                } catch (Throwable th2) {
                }
            } else if ("banner".equals(attrName)) {
                if (VERSION.SDK_INT >= 20) {
                    try {
                        result.banner = context.getResources().getIdentifier(attrValue, null, IncrementComponentManager.sPackageName);
                    } catch (Throwable th3) {
                    }
                }
            } else if ("logo".equals(attrName)) {
                try {
                    result.logo = context.getResources().getIdentifier(attrValue, null, IncrementComponentManager.sPackageName);
                } catch (Throwable th4) {
                }
            }
        }

        private int parseLaunchMode(String attrValue) {
            if ("standard".equalsIgnoreCase(attrValue)) {
                return 0;
            }
            if ("singleTop".equalsIgnoreCase(attrValue)) {
                return 1;
            }
            if ("singleTask".equalsIgnoreCase(attrValue)) {
                return 2;
            }
            if ("singleInstance".equalsIgnoreCase(attrValue)) {
                return 3;
            }
            Log.w(IncrementComponentManager.TAG, "Unknown launchMode: " + attrValue);
            return 0;
        }

        private int parseScreenOrientation(String attrValue) {
            if ("unspecified".equalsIgnoreCase(attrValue)) {
                return -1;
            }
            if ("behind".equalsIgnoreCase(attrValue)) {
                return 3;
            }
            if ("landscape".equalsIgnoreCase(attrValue)) {
                return 0;
            }
            if ("portrait".equalsIgnoreCase(attrValue)) {
                return 1;
            }
            if ("reverseLandscape".equalsIgnoreCase(attrValue)) {
                return 8;
            }
            if ("reversePortrait".equalsIgnoreCase(attrValue)) {
                return 9;
            }
            if ("sensorLandscape".equalsIgnoreCase(attrValue)) {
                return 6;
            }
            if ("sensorPortrait".equalsIgnoreCase(attrValue)) {
                return 7;
            }
            if ("sensor".equalsIgnoreCase(attrValue)) {
                return 4;
            }
            if ("fullSensor".equalsIgnoreCase(attrValue)) {
                return 10;
            }
            if ("nosensor".equalsIgnoreCase(attrValue)) {
                return 5;
            }
            if ("user".equalsIgnoreCase(attrValue)) {
                return 2;
            }
            if (VERSION.SDK_INT >= 18 && "fullUser".equalsIgnoreCase(attrValue)) {
                return 13;
            }
            if (VERSION.SDK_INT >= 18 && "locked".equalsIgnoreCase(attrValue)) {
                return 14;
            }
            if (VERSION.SDK_INT >= 18 && "userLandscape".equalsIgnoreCase(attrValue)) {
                return 11;
            }
            if (VERSION.SDK_INT < 18 || !"userPortrait".equalsIgnoreCase(attrValue)) {
                return -1;
            }
            return 12;
        }
    };
    private static final Map<String, ActivityInfo> sClassNameToActivityInfoMap = new HashMap();
    private static final Map<String, IntentFilter> sClassNameToIntentFilterMap = new HashMap();
    private static Context sContext = null;
    private static volatile boolean sInitialized = false;
    /* access modifiers changed from: private */
    public static String sPackageName = null;

    private static abstract class AttrTranslator<T_RESULT> {
        /* access modifiers changed from: 0000 */
        public abstract void onTranslate(Context context, int i, String str, String str2, T_RESULT t_result);

        private AttrTranslator() {
        }

        /* access modifiers changed from: 0000 */
        public final void translate(Context context, int tagType, XmlPullParser parser, T_RESULT result) {
            onInit(context, tagType, parser);
            int attrCount = parser.getAttributeCount();
            for (int i = 0; i < attrCount; i++) {
                if (Constants.APPSYSTEM.equals(parser.getAttributePrefix(i))) {
                    onTranslate(context, tagType, parser.getAttributeName(i), parser.getAttributeValue(i), result);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void onInit(Context context, int tagType, XmlPullParser parser) {
        }
    }

    public static synchronized boolean init(Context context, ShareSecurityCheck checker) throws IOException {
        boolean z;
        synchronized (IncrementComponentManager.class) {
            if (!checker.getMetaContentMap().containsKey(EnvConsts.INCCOMPONENT_META_FILE)) {
                Log.i(TAG, "package has no incremental component meta, skip init.");
                z = false;
            } else {
                while (context instanceof ContextWrapper) {
                    Context baseCtx = ((ContextWrapper) context).getBaseContext();
                    if (baseCtx == null) {
                        break;
                    }
                    context = baseCtx;
                }
                sContext = context;
                sPackageName = context.getPackageName();
                StringReader sr = new StringReader((String) checker.getMetaContentMap().get(EnvConsts.INCCOMPONENT_META_FILE));
                XmlPullParser parser = null;
                try {
                    parser = Xml.newPullParser();
                    parser.setInput(sr);
                    for (int event = parser.getEventType(); event != 1; event = parser.next()) {
                        switch (event) {
                            case 2:
                                String tagName = parser.getName();
                                if (!EnvConsts.ACTIVITY_MANAGER_SRVNAME.equalsIgnoreCase(tagName)) {
                                    if (!NotificationCompat.CATEGORY_SERVICE.equalsIgnoreCase(tagName) && !SocialConstants.PARAM_RECEIVER.equalsIgnoreCase(tagName) && "provider".equalsIgnoreCase(tagName)) {
                                        break;
                                    }
                                } else {
                                    ActivityInfo aInfo = parseActivity(context, parser);
                                    sClassNameToActivityInfoMap.put(aInfo.name, aInfo);
                                    break;
                                }
                        }
                    }
                    sInitialized = true;
                    if (parser != null) {
                        try {
                            parser.setInput(null);
                        } catch (Throwable th) {
                        }
                    }
                    SharePatchFileUtil.closeQuietly(sr);
                    z = true;
                } catch (XmlPullParserException e) {
                    throw new IOException(e);
                } catch (Throwable th2) {
                    if (parser != null) {
                        try {
                            parser.setInput(null);
                        } catch (Throwable th3) {
                        }
                    }
                    SharePatchFileUtil.closeQuietly(sr);
                    throw th2;
                }
            }
        }
        return z;
    }

    private static synchronized ActivityInfo parseActivity(Context context, XmlPullParser parser) throws XmlPullParserException, IOException {
        ActivityInfo aInfo;
        synchronized (IncrementComponentManager.class) {
            aInfo = new ActivityInfo();
            ApplicationInfo appInfo = context.getApplicationInfo();
            aInfo.applicationInfo = appInfo;
            aInfo.packageName = sPackageName;
            aInfo.processName = appInfo.processName;
            aInfo.launchMode = 0;
            aInfo.permission = appInfo.permission;
            aInfo.screenOrientation = -1;
            aInfo.taskAffinity = appInfo.taskAffinity;
            if (VERSION.SDK_INT >= 11 && (appInfo.flags & 536870912) != 0) {
                aInfo.flags |= 512;
            }
            if (VERSION.SDK_INT >= 21) {
                aInfo.documentLaunchMode = 0;
            }
            if (VERSION.SDK_INT >= 14) {
                aInfo.uiOptions = appInfo.uiOptions;
            }
            sActivityInfoAttrTranslator.translate(context, 0, parser, aInfo);
            int outerDepth = parser.getDepth();
            while (true) {
                int type = parser.next();
                if (type != 1 && (type != 3 || parser.getDepth() > outerDepth)) {
                    if (!(type == 3 || type == 4)) {
                        String tagName = parser.getName();
                        if ("intent-filter".equalsIgnoreCase(tagName)) {
                            parseIntentFilter(context, aInfo.name, parser);
                        } else if ("meta-data".equalsIgnoreCase(tagName)) {
                            parseMetaData(context, aInfo, parser);
                        }
                    }
                }
            }
        }
        return aInfo;
    }

    private static synchronized void parseIntentFilter(Context context, String componentName, XmlPullParser parser) throws XmlPullParserException, IOException {
        synchronized (IncrementComponentManager.class) {
            IntentFilter intentFilter = new IntentFilter();
            String priorityStr = parser.getAttributeValue(null, "priority");
            if (!TextUtils.isEmpty(priorityStr)) {
                intentFilter.setPriority(Integer.decode(priorityStr).intValue());
            }
            String autoVerify = parser.getAttributeValue(null, "autoVerify");
            if (!TextUtils.isEmpty(autoVerify)) {
                try {
                    ShareReflectUtil.findMethod(IntentFilter.class, "setAutoVerify", (Class<?>[]) new Class[]{Boolean.TYPE}).invoke(intentFilter, new Object[]{Boolean.valueOf("true".equalsIgnoreCase(autoVerify))});
                } catch (Throwable th) {
                }
            }
            int outerDepth = parser.getDepth();
            while (true) {
                int type = parser.next();
                if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                    sClassNameToIntentFilterMap.put(componentName, intentFilter);
                } else if (!(type == 3 || type == 4)) {
                    String tagName = parser.getName();
                    if (AuthActivity.ACTION_KEY.equals(tagName)) {
                        String name = parser.getAttributeValue(null, TableSchema.COLUMN_NAME);
                        if (name != null) {
                            intentFilter.addAction(name);
                        }
                    } else if ("category".equals(tagName)) {
                        String name2 = parser.getAttributeValue(null, TableSchema.COLUMN_NAME);
                        if (name2 != null) {
                            intentFilter.addCategory(name2);
                        }
                    } else if ("data".equals(tagName)) {
                        String mimeType = parser.getAttributeValue(null, "mimeType");
                        if (mimeType != null) {
                            try {
                                intentFilter.addDataType(mimeType);
                            } catch (MalformedMimeTypeException e) {
                                XmlPullParserException xmlPullParserException = new XmlPullParserException("bad mimeType", parser, e);
                                throw xmlPullParserException;
                            }
                        }
                        String scheme = parser.getAttributeValue(null, "scheme");
                        if (scheme != null) {
                            intentFilter.addDataScheme(scheme);
                        }
                        if (VERSION.SDK_INT >= 19) {
                            String ssp = parser.getAttributeValue(null, "ssp");
                            if (ssp != null) {
                                intentFilter.addDataSchemeSpecificPart(ssp, 0);
                            }
                            String sspPrefix = parser.getAttributeValue(null, "sspPrefix");
                            if (sspPrefix != null) {
                                intentFilter.addDataSchemeSpecificPart(sspPrefix, 1);
                            }
                            String sspPattern = parser.getAttributeValue(null, "sspPattern");
                            if (sspPattern != null) {
                                intentFilter.addDataSchemeSpecificPart(sspPattern, 2);
                            }
                        }
                        String host = parser.getAttributeValue(null, "host");
                        String port = parser.getAttributeValue(null, "port");
                        if (host != null) {
                            intentFilter.addDataAuthority(host, port);
                        }
                        String path = parser.getAttributeValue(null, "path");
                        if (path != null) {
                            intentFilter.addDataPath(path, 0);
                        }
                        String pathPrefix = parser.getAttributeValue(null, "pathPrefix");
                        if (pathPrefix != null) {
                            intentFilter.addDataPath(pathPrefix, 1);
                        }
                        String pathPattern = parser.getAttributeValue(null, "pathPattern");
                        if (pathPattern != null) {
                            intentFilter.addDataPath(pathPattern, 2);
                        }
                    }
                    skipCurrentTag(parser);
                }
            }
            sClassNameToIntentFilterMap.put(componentName, intentFilter);
        }
    }

    private static synchronized void parseMetaData(Context context, ActivityInfo aInfo, XmlPullParser parser) throws XmlPullParserException, IOException {
        synchronized (IncrementComponentManager.class) {
            ClassLoader myCl = IncrementComponentManager.class.getClassLoader();
            String name = parser.getAttributeValue(null, TableSchema.COLUMN_NAME);
            String value = parser.getAttributeValue(null, "value");
            if (!TextUtils.isEmpty(name)) {
                if (aInfo.metaData == null) {
                    aInfo.metaData = new Bundle(myCl);
                }
                aInfo.metaData.putString(name, value);
            }
        }
    }

    private static void skipCurrentTag(XmlPullParser parser) throws IOException, XmlPullParserException {
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1) {
                return;
            }
            if (type == 3 && parser.getDepth() <= outerDepth) {
                return;
            }
        }
    }

    private static synchronized void ensureInitialized() {
        synchronized (IncrementComponentManager.class) {
            if (!sInitialized) {
                throw new IllegalStateException("Not initialized!!");
            }
        }
    }

    public static boolean isIncrementActivity(String className) {
        ensureInitialized();
        return className != null && sClassNameToActivityInfoMap.containsKey(className);
    }

    public static ActivityInfo queryActivityInfo(String className) {
        ensureInitialized();
        if (className != null) {
            return (ActivityInfo) sClassNameToActivityInfoMap.get(className);
        }
        return null;
    }

    public static ResolveInfo resolveIntent(Intent intent) {
        ensureInitialized();
        int maxPriority = -1;
        String bestComponentName = null;
        IntentFilter respFilter = null;
        int bestMatchRes = 0;
        ComponentName component = intent.getComponent();
        if (component != null) {
            String compName = component.getClassName();
            if (sClassNameToActivityInfoMap.containsKey(compName)) {
                bestComponentName = compName;
                maxPriority = 0;
            }
        } else {
            for (Entry<String, IntentFilter> item : sClassNameToIntentFilterMap.entrySet()) {
                String componentName = (String) item.getKey();
                IntentFilter intentFilter = (IntentFilter) item.getValue();
                int matchRes = intentFilter.match(intent.getAction(), intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), TAG);
                boolean matches = (matchRes == -3 || matchRes == -4 || matchRes == -2 || matchRes == -1) ? false : true;
                int priority = intentFilter.getPriority();
                if (matches && priority > maxPriority) {
                    maxPriority = priority;
                    bestComponentName = componentName;
                    respFilter = intentFilter;
                    bestMatchRes = matchRes;
                }
            }
        }
        if (bestComponentName == null) {
            return null;
        }
        ResolveInfo result = new ResolveInfo();
        result.activityInfo = (ActivityInfo) sClassNameToActivityInfoMap.get(bestComponentName);
        result.filter = respFilter;
        result.match = bestMatchRes;
        result.priority = maxPriority;
        result.resolvePackageName = sPackageName;
        result.icon = result.activityInfo.icon;
        result.labelRes = result.activityInfo.labelRes;
        return result;
    }

    private IncrementComponentManager() {
        throw new UnsupportedOperationException();
    }
}
