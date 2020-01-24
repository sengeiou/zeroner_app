package com.alibaba.android.arouter.core;

import android.content.Context;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.enums.TypeKind;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IInterceptorGroup;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.alibaba.android.arouter.facade.template.IRouteRoot;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.ClassUtils;
import com.alibaba.android.arouter.utils.Consts;
import com.alibaba.android.arouter.utils.PackageUtils;
import com.alibaba.android.arouter.utils.TextUtils;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class LogisticsCenter {
    static ThreadPoolExecutor executor;
    private static Context mContext;
    private static boolean registerByPlugin;

    private static void loadRouterMap() {
        registerByPlugin = false;
    }

    private static void register(String className) {
        if (!TextUtils.isEmpty(className)) {
            try {
                Object obj = Class.forName(className).getConstructor(new Class[0]).newInstance(new Object[0]);
                if (obj instanceof IRouteRoot) {
                    registerRouteRoot((IRouteRoot) obj);
                } else if (obj instanceof IProviderGroup) {
                    registerProvider((IProviderGroup) obj);
                } else if (obj instanceof IInterceptorGroup) {
                    registerInterceptor((IInterceptorGroup) obj);
                } else {
                    ARouter.logger.info("ARouter::", "register failed, class name: " + className + " should implements one of IRouteRoot/IProviderGroup/IInterceptorGroup.");
                }
            } catch (Exception e) {
                ARouter.logger.error("ARouter::", "register class error:" + className);
            }
        }
    }

    private static void registerRouteRoot(IRouteRoot routeRoot) {
        markRegisteredByPlugin();
        if (routeRoot != null) {
            routeRoot.loadInto(Warehouse.groupsIndex);
        }
    }

    private static void registerInterceptor(IInterceptorGroup interceptorGroup) {
        markRegisteredByPlugin();
        if (interceptorGroup != null) {
            interceptorGroup.loadInto(Warehouse.interceptorsIndex);
        }
    }

    private static void registerProvider(IProviderGroup providerGroup) {
        markRegisteredByPlugin();
        if (providerGroup != null) {
            providerGroup.loadInto(Warehouse.providersIndex);
        }
    }

    private static void markRegisteredByPlugin() {
        if (!registerByPlugin) {
            registerByPlugin = true;
        }
    }

    public static synchronized void init(Context context, ThreadPoolExecutor tpe) throws HandlerException {
        Set<String> routerMap;
        synchronized (LogisticsCenter.class) {
            mContext = context;
            executor = tpe;
            try {
                long startInit = System.currentTimeMillis();
                loadRouterMap();
                if (registerByPlugin) {
                    ARouter.logger.info("ARouter::", "Load router map by arouter-auto-register plugin.");
                } else {
                    if (ARouter.debuggable() || PackageUtils.isNewVersion(context)) {
                        ARouter.logger.info("ARouter::", "Run with debug mode or new install, rebuild router map.");
                        routerMap = ClassUtils.getFileNameByPackageName(mContext, Consts.ROUTE_ROOT_PAKCAGE);
                        if (!routerMap.isEmpty()) {
                            context.getSharedPreferences(Consts.AROUTER_SP_CACHE_KEY, 0).edit().putStringSet(Consts.AROUTER_SP_KEY_MAP, routerMap).apply();
                        }
                        PackageUtils.updateVersion(context);
                    } else {
                        ARouter.logger.info("ARouter::", "Load router map from cache.");
                        routerMap = new HashSet<>(context.getSharedPreferences(Consts.AROUTER_SP_CACHE_KEY, 0).getStringSet(Consts.AROUTER_SP_KEY_MAP, new HashSet()));
                    }
                    ARouter.logger.info("ARouter::", "Find router map finished, map size = " + routerMap.size() + ", cost " + (System.currentTimeMillis() - startInit) + " ms.");
                    startInit = System.currentTimeMillis();
                    for (String className : routerMap) {
                        if (className.startsWith("com.alibaba.android.arouter.routes.ARouter$$Root")) {
                            ((IRouteRoot) Class.forName(className).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(Warehouse.groupsIndex);
                        } else if (className.startsWith("com.alibaba.android.arouter.routes.ARouter$$Interceptors")) {
                            ((IInterceptorGroup) Class.forName(className).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(Warehouse.interceptorsIndex);
                        } else if (className.startsWith("com.alibaba.android.arouter.routes.ARouter$$Providers")) {
                            ((IProviderGroup) Class.forName(className).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(Warehouse.providersIndex);
                        }
                    }
                }
                ARouter.logger.info("ARouter::", "Load root element finished, cost " + (System.currentTimeMillis() - startInit) + " ms.");
                if (Warehouse.groupsIndex.size() == 0) {
                    ARouter.logger.error("ARouter::", "No mapping files were found, check your configuration please!");
                }
                if (ARouter.debuggable()) {
                    ARouter.logger.debug("ARouter::", String.format(Locale.getDefault(), "LogisticsCenter has already been loaded, GroupIndex[%d], InterceptorIndex[%d], ProviderIndex[%d]", new Object[]{Integer.valueOf(Warehouse.groupsIndex.size()), Integer.valueOf(Warehouse.interceptorsIndex.size()), Integer.valueOf(Warehouse.providersIndex.size())}));
                }
            } catch (Exception e) {
                throw new HandlerException("ARouter::ARouter init logistics center exception! [" + e.getMessage() + "]");
            }
        }
    }

    public static Postcard buildProvider(String serviceName) {
        RouteMeta meta = (RouteMeta) Warehouse.providersIndex.get(serviceName);
        if (meta == null) {
            return null;
        }
        return new Postcard(meta.getPath(), meta.getGroup());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ee, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0113, code lost:
        throw new com.alibaba.android.arouter.exception.HandlerException("ARouter::Fatal exception when loading group meta. [" + r2.getMessage() + "]");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01ea, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0208, code lost:
        throw new com.alibaba.android.arouter.exception.HandlerException("Init provider failed! " + r2.getMessage());
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:16:0x0061, B:47:0x01c6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void completion(com.alibaba.android.arouter.facade.Postcard r21) {
        /*
            java.lang.Class<com.alibaba.android.arouter.core.LogisticsCenter> r16 = com.alibaba.android.arouter.core.LogisticsCenter.class
            monitor-enter(r16)
            if (r21 != 0) goto L_0x0011
            com.alibaba.android.arouter.exception.NoRouteFoundException r13 = new com.alibaba.android.arouter.exception.NoRouteFoundException     // Catch:{ all -> 0x000e }
            java.lang.String r14 = "ARouter::No postcard!"
            r13.<init>(r14)     // Catch:{ all -> 0x000e }
            throw r13     // Catch:{ all -> 0x000e }
        L_0x000e:
            r13 = move-exception
            monitor-exit(r16)
            throw r13
        L_0x0011:
            java.util.Map<java.lang.String, com.alibaba.android.arouter.facade.model.RouteMeta> r13 = com.alibaba.android.arouter.core.Warehouse.routes     // Catch:{ all -> 0x000e }
            java.lang.String r14 = r21.getPath()     // Catch:{ all -> 0x000e }
            java.lang.Object r12 = r13.get(r14)     // Catch:{ all -> 0x000e }
            com.alibaba.android.arouter.facade.model.RouteMeta r12 = (com.alibaba.android.arouter.facade.model.RouteMeta) r12     // Catch:{ all -> 0x000e }
            if (r12 != 0) goto L_0x0114
            java.util.Map<java.lang.String, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.IRouteGroup>> r13 = com.alibaba.android.arouter.core.Warehouse.groupsIndex     // Catch:{ all -> 0x000e }
            java.lang.String r14 = r21.getGroup()     // Catch:{ all -> 0x000e }
            java.lang.Object r3 = r13.get(r14)     // Catch:{ all -> 0x000e }
            java.lang.Class r3 = (java.lang.Class) r3     // Catch:{ all -> 0x000e }
            if (r3 != 0) goto L_0x0061
            com.alibaba.android.arouter.exception.NoRouteFoundException r13 = new com.alibaba.android.arouter.exception.NoRouteFoundException     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x000e }
            r14.<init>()     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "ARouter::There is no route match the path ["
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = r21.getPath()     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "], in group ["
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = r21.getGroup()     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "]"
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x000e }
            r13.<init>(r14)     // Catch:{ all -> 0x000e }
            throw r13     // Catch:{ all -> 0x000e }
        L_0x0061:
            boolean r13 = com.alibaba.android.arouter.launcher.ARouter.debuggable()     // Catch:{ Exception -> 0x00ee }
            if (r13 == 0) goto L_0x0096
            com.alibaba.android.arouter.facade.template.ILogger r13 = com.alibaba.android.arouter.launcher.ARouter.logger     // Catch:{ Exception -> 0x00ee }
            java.lang.String r14 = "ARouter::"
            java.util.Locale r15 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x00ee }
            java.lang.String r17 = "The group [%s] starts loading, trigger by [%s]"
            r18 = 2
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x00ee }
            r18 = r0
            r19 = 0
            java.lang.String r20 = r21.getGroup()     // Catch:{ Exception -> 0x00ee }
            r18[r19] = r20     // Catch:{ Exception -> 0x00ee }
            r19 = 1
            java.lang.String r20 = r21.getPath()     // Catch:{ Exception -> 0x00ee }
            r18[r19] = r20     // Catch:{ Exception -> 0x00ee }
            r0 = r17
            r1 = r18
            java.lang.String r15 = java.lang.String.format(r15, r0, r1)     // Catch:{ Exception -> 0x00ee }
            r13.debug(r14, r15)     // Catch:{ Exception -> 0x00ee }
        L_0x0096:
            r13 = 0
            java.lang.Class[] r13 = new java.lang.Class[r13]     // Catch:{ Exception -> 0x00ee }
            java.lang.reflect.Constructor r13 = r3.getConstructor(r13)     // Catch:{ Exception -> 0x00ee }
            r14 = 0
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x00ee }
            java.lang.Object r4 = r13.newInstance(r14)     // Catch:{ Exception -> 0x00ee }
            com.alibaba.android.arouter.facade.template.IRouteGroup r4 = (com.alibaba.android.arouter.facade.template.IRouteGroup) r4     // Catch:{ Exception -> 0x00ee }
            java.util.Map<java.lang.String, com.alibaba.android.arouter.facade.model.RouteMeta> r13 = com.alibaba.android.arouter.core.Warehouse.routes     // Catch:{ Exception -> 0x00ee }
            r4.loadInto(r13)     // Catch:{ Exception -> 0x00ee }
            java.util.Map<java.lang.String, java.lang.Class<? extends com.alibaba.android.arouter.facade.template.IRouteGroup>> r13 = com.alibaba.android.arouter.core.Warehouse.groupsIndex     // Catch:{ Exception -> 0x00ee }
            java.lang.String r14 = r21.getGroup()     // Catch:{ Exception -> 0x00ee }
            r13.remove(r14)     // Catch:{ Exception -> 0x00ee }
            boolean r13 = com.alibaba.android.arouter.launcher.ARouter.debuggable()     // Catch:{ Exception -> 0x00ee }
            if (r13 == 0) goto L_0x00e9
            com.alibaba.android.arouter.facade.template.ILogger r13 = com.alibaba.android.arouter.launcher.ARouter.logger     // Catch:{ Exception -> 0x00ee }
            java.lang.String r14 = "ARouter::"
            java.util.Locale r15 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x00ee }
            java.lang.String r17 = "The group [%s] has already been loaded, trigger by [%s]"
            r18 = 2
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x00ee }
            r18 = r0
            r19 = 0
            java.lang.String r20 = r21.getGroup()     // Catch:{ Exception -> 0x00ee }
            r18[r19] = r20     // Catch:{ Exception -> 0x00ee }
            r19 = 1
            java.lang.String r20 = r21.getPath()     // Catch:{ Exception -> 0x00ee }
            r18[r19] = r20     // Catch:{ Exception -> 0x00ee }
            r0 = r17
            r1 = r18
            java.lang.String r15 = java.lang.String.format(r15, r0, r1)     // Catch:{ Exception -> 0x00ee }
            r13.debug(r14, r15)     // Catch:{ Exception -> 0x00ee }
        L_0x00e9:
            completion(r21)     // Catch:{ all -> 0x000e }
        L_0x00ec:
            monitor-exit(r16)
            return
        L_0x00ee:
            r2 = move-exception
            com.alibaba.android.arouter.exception.HandlerException r13 = new com.alibaba.android.arouter.exception.HandlerException     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x000e }
            r14.<init>()     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "ARouter::Fatal exception when loading group meta. ["
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = r2.getMessage()     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "]"
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x000e }
            r13.<init>(r14)     // Catch:{ all -> 0x000e }
            throw r13     // Catch:{ all -> 0x000e }
        L_0x0114:
            java.lang.Class r13 = r12.getDestination()     // Catch:{ all -> 0x000e }
            r0 = r21
            r0.setDestination(r13)     // Catch:{ all -> 0x000e }
            com.alibaba.android.arouter.facade.enums.RouteType r13 = r12.getType()     // Catch:{ all -> 0x000e }
            r0 = r21
            r0.setType(r13)     // Catch:{ all -> 0x000e }
            int r13 = r12.getPriority()     // Catch:{ all -> 0x000e }
            r0 = r21
            r0.setPriority(r13)     // Catch:{ all -> 0x000e }
            int r13 = r12.getExtra()     // Catch:{ all -> 0x000e }
            r0 = r21
            r0.setExtra(r13)     // Catch:{ all -> 0x000e }
            android.net.Uri r10 = r21.getUri()     // Catch:{ all -> 0x000e }
            if (r10 == 0) goto L_0x01a6
            java.util.Map r11 = com.alibaba.android.arouter.utils.TextUtils.splitQueryParameters(r10)     // Catch:{ all -> 0x000e }
            java.util.Map r7 = r12.getParamsType()     // Catch:{ all -> 0x000e }
            boolean r13 = com.alibaba.android.arouter.utils.MapUtils.isNotEmpty(r7)     // Catch:{ all -> 0x000e }
            if (r13 == 0) goto L_0x019a
            java.util.Set r13 = r7.entrySet()     // Catch:{ all -> 0x000e }
            java.util.Iterator r17 = r13.iterator()     // Catch:{ all -> 0x000e }
        L_0x0154:
            boolean r13 = r17.hasNext()     // Catch:{ all -> 0x000e }
            if (r13 == 0) goto L_0x017c
            java.lang.Object r6 = r17.next()     // Catch:{ all -> 0x000e }
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ all -> 0x000e }
            java.lang.Object r13 = r6.getValue()     // Catch:{ all -> 0x000e }
            java.lang.Integer r13 = (java.lang.Integer) r13     // Catch:{ all -> 0x000e }
            java.lang.Object r14 = r6.getKey()     // Catch:{ all -> 0x000e }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ all -> 0x000e }
            java.lang.Object r15 = r6.getKey()     // Catch:{ all -> 0x000e }
            java.lang.Object r15 = r11.get(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ all -> 0x000e }
            r0 = r21
            setValue(r0, r13, r14, r15)     // Catch:{ all -> 0x000e }
            goto L_0x0154
        L_0x017c:
            android.os.Bundle r14 = r21.getExtras()     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "wmHzgD4lOj5o4241"
            java.util.Set r13 = r7.keySet()     // Catch:{ all -> 0x000e }
            r17 = 0
            r0 = r17
            java.lang.String[] r0 = new java.lang.String[r0]     // Catch:{ all -> 0x000e }
            r17 = r0
            r0 = r17
            java.lang.Object[] r13 = r13.toArray(r0)     // Catch:{ all -> 0x000e }
            java.lang.String[] r13 = (java.lang.String[]) r13     // Catch:{ all -> 0x000e }
            r14.putStringArray(r15, r13)     // Catch:{ all -> 0x000e }
        L_0x019a:
            java.lang.String r13 = "NTeRQWvye18AkPd6G"
            java.lang.String r14 = r10.toString()     // Catch:{ all -> 0x000e }
            r0 = r21
            r0.withString(r13, r14)     // Catch:{ all -> 0x000e }
        L_0x01a6:
            int[] r13 = com.alibaba.android.arouter.core.LogisticsCenter.AnonymousClass1.$SwitchMap$com$alibaba$android$arouter$facade$enums$RouteType     // Catch:{ all -> 0x000e }
            com.alibaba.android.arouter.facade.enums.RouteType r14 = r12.getType()     // Catch:{ all -> 0x000e }
            int r14 = r14.ordinal()     // Catch:{ all -> 0x000e }
            r13 = r13[r14]     // Catch:{ all -> 0x000e }
            switch(r13) {
                case 1: goto L_0x01b7;
                case 2: goto L_0x0209;
                default: goto L_0x01b5;
            }     // Catch:{ all -> 0x000e }
        L_0x01b5:
            goto L_0x00ec
        L_0x01b7:
            java.lang.Class r9 = r12.getDestination()     // Catch:{ all -> 0x000e }
            java.util.Map<java.lang.Class, com.alibaba.android.arouter.facade.template.IProvider> r13 = com.alibaba.android.arouter.core.Warehouse.providers     // Catch:{ all -> 0x000e }
            java.lang.Object r5 = r13.get(r9)     // Catch:{ all -> 0x000e }
            com.alibaba.android.arouter.facade.template.IProvider r5 = (com.alibaba.android.arouter.facade.template.IProvider) r5     // Catch:{ all -> 0x000e }
            if (r5 != 0) goto L_0x01e0
            r13 = 0
            java.lang.Class[] r13 = new java.lang.Class[r13]     // Catch:{ Exception -> 0x01ea }
            java.lang.reflect.Constructor r13 = r9.getConstructor(r13)     // Catch:{ Exception -> 0x01ea }
            r14 = 0
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x01ea }
            java.lang.Object r8 = r13.newInstance(r14)     // Catch:{ Exception -> 0x01ea }
            com.alibaba.android.arouter.facade.template.IProvider r8 = (com.alibaba.android.arouter.facade.template.IProvider) r8     // Catch:{ Exception -> 0x01ea }
            android.content.Context r13 = mContext     // Catch:{ Exception -> 0x01ea }
            r8.init(r13)     // Catch:{ Exception -> 0x01ea }
            java.util.Map<java.lang.Class, com.alibaba.android.arouter.facade.template.IProvider> r13 = com.alibaba.android.arouter.core.Warehouse.providers     // Catch:{ Exception -> 0x01ea }
            r13.put(r9, r8)     // Catch:{ Exception -> 0x01ea }
            r5 = r8
        L_0x01e0:
            r0 = r21
            r0.setProvider(r5)     // Catch:{ all -> 0x000e }
            r21.greenChannel()     // Catch:{ all -> 0x000e }
            goto L_0x00ec
        L_0x01ea:
            r2 = move-exception
            com.alibaba.android.arouter.exception.HandlerException r13 = new com.alibaba.android.arouter.exception.HandlerException     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x000e }
            r14.<init>()     // Catch:{ all -> 0x000e }
            java.lang.String r15 = "Init provider failed! "
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r15 = r2.getMessage()     // Catch:{ all -> 0x000e }
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ all -> 0x000e }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x000e }
            r13.<init>(r14)     // Catch:{ all -> 0x000e }
            throw r13     // Catch:{ all -> 0x000e }
        L_0x0209:
            r21.greenChannel()     // Catch:{ all -> 0x000e }
            goto L_0x00ec
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.android.arouter.core.LogisticsCenter.completion(com.alibaba.android.arouter.facade.Postcard):void");
    }

    private static void setValue(Postcard postcard, Integer typeDef, String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            if (typeDef != null) {
                try {
                    if (typeDef.intValue() == TypeKind.BOOLEAN.ordinal()) {
                        postcard.withBoolean(key, Boolean.parseBoolean(value));
                    } else if (typeDef.intValue() == TypeKind.BYTE.ordinal()) {
                        postcard.withByte(key, Byte.valueOf(value).byteValue());
                    } else if (typeDef.intValue() == TypeKind.SHORT.ordinal()) {
                        postcard.withShort(key, Short.valueOf(value).shortValue());
                    } else if (typeDef.intValue() == TypeKind.INT.ordinal()) {
                        postcard.withInt(key, Integer.valueOf(value).intValue());
                    } else if (typeDef.intValue() == TypeKind.LONG.ordinal()) {
                        postcard.withLong(key, Long.valueOf(value).longValue());
                    } else if (typeDef.intValue() == TypeKind.FLOAT.ordinal()) {
                        postcard.withFloat(key, Float.valueOf(value).floatValue());
                    } else if (typeDef.intValue() == TypeKind.DOUBLE.ordinal()) {
                        postcard.withDouble(key, Double.valueOf(value).doubleValue());
                    } else if (typeDef.intValue() == TypeKind.STRING.ordinal()) {
                        postcard.withString(key, value);
                    } else if (typeDef.intValue() == TypeKind.PARCELABLE.ordinal()) {
                    } else {
                        if (typeDef.intValue() == TypeKind.OBJECT.ordinal()) {
                            postcard.withString(key, value);
                        } else {
                            postcard.withString(key, value);
                        }
                    }
                } catch (Throwable ex) {
                    ARouter.logger.warning("ARouter::", "LogisticsCenter setValue failed! " + ex.getMessage());
                }
            } else {
                postcard.withString(key, value);
            }
        }
    }

    public static void suspend() {
        Warehouse.clear();
    }
}
