package com.iwown.device_module.device_log;

public class LogUpUtils {
    public static final int TypeHistory = 2;
    public static final int TypeToday = 1;

    public static void upTodayBleLog() {
        upLoadBleLog("Zeroner/zeroner/log/", "operate", 1);
        upLoadBleLog("Zeroner/zeroner/log/", "notify", 1);
        upLoadBleLog("Zeroner/zeroner/log/", "write", 1);
    }

    public static void upHistoryBleLog() {
        upLoadBleLog("Zeroner/zeroner/log/", "operate", 2);
        upLoadBleLog("Zeroner/zeroner/log/", "notify", 2);
        upLoadBleLog("Zeroner/zeroner/log/", "write", 2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0058, code lost:
        r7 = new com.iwown.device_module.common.network.data.req.SDFileSend();
        r7.setUid(com.iwown.device_module.common.utils.ContextUtil.getLUID());
        r7.setDate(r1.getY_M_D());
        r8 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0073, code lost:
        if (r14.equalsIgnoreCase("operate") == false) goto L_0x00d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0075, code lost:
        r8 = java.lang.Integer.parseInt("20" + com.iwown.data_link.utils.AppConfigUtil.BLE_LOG_UP_APP);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x008f, code lost:
        r7.setFileType(r8);
        r7.setFilePath(r4.getAbsolutePath());
        com.iwown.device_module.common.network.NetFactory.getInstance().getClient(new com.iwown.device_module.device_log.LogUpUtils.AnonymousClass1()).upSdFile(r7);
        com.socks.library.KLog.e("手动上传日志文件:" + r4.getName());
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00dc, code lost:
        if (r14.equalsIgnoreCase("notify") == false) goto L_0x00f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00de, code lost:
        r8 = java.lang.Integer.parseInt(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE + com.iwown.data_link.utils.AppConfigUtil.BLE_LOG_UP_APP);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0100, code lost:
        if (r14.equalsIgnoreCase("write") == false) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0102, code lost:
        r8 = java.lang.Integer.parseInt(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_TROOPBAR + com.iwown.data_link.utils.AppConfigUtil.BLE_LOG_UP_APP);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void upLoadBleLog(java.lang.String r13, java.lang.String r14, int r15) {
        /*
            com.iwown.lib_common.date.DateUtil r1 = new com.iwown.lib_common.date.DateUtil
            r1.<init>()
            r9 = 2
            if (r15 != r9) goto L_0x000c
            r9 = -1
            r1.addDay(r9)
        L_0x000c:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.io.File r10 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r10 = r10.getAbsolutePath()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = "/"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r13)
            java.lang.String r0 = r9.toString()
            java.io.File r3 = new java.io.File
            r3.<init>(r0)
            java.io.File[] r6 = r3.listFiles()
            if (r6 != 0) goto L_0x0038
        L_0x0037:
            return
        L_0x0038:
            r5 = 0
            int r10 = r6.length     // Catch:{ Exception -> 0x00cf }
            r9 = 0
        L_0x003b:
            if (r9 >= r10) goto L_0x00c5
            r4 = r6[r9]     // Catch:{ Exception -> 0x00cf }
            r5 = 0
            java.lang.String r11 = r4.getName()     // Catch:{ Exception -> 0x00cf }
            java.lang.String r12 = r1.getY_M_D()     // Catch:{ Exception -> 0x00cf }
            boolean r11 = r11.contains(r12)     // Catch:{ Exception -> 0x00cf }
            if (r11 == 0) goto L_0x011e
            java.lang.String r11 = r4.getName()     // Catch:{ Exception -> 0x00cf }
            boolean r11 = r11.contains(r14)     // Catch:{ Exception -> 0x00cf }
            if (r11 == 0) goto L_0x011e
            com.iwown.device_module.common.network.data.req.SDFileSend r7 = new com.iwown.device_module.common.network.data.req.SDFileSend     // Catch:{ Exception -> 0x00cf }
            r7.<init>()     // Catch:{ Exception -> 0x00cf }
            long r10 = com.iwown.device_module.common.utils.ContextUtil.getLUID()     // Catch:{ Exception -> 0x00cf }
            r7.setUid(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r9 = r1.getY_M_D()     // Catch:{ Exception -> 0x00cf }
            r7.setDate(r9)     // Catch:{ Exception -> 0x00cf }
            r8 = -1
            java.lang.String r9 = "operate"
            boolean r9 = r14.equalsIgnoreCase(r9)     // Catch:{ Exception -> 0x00cf }
            if (r9 == 0) goto L_0x00d5
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00cf }
            r9.<init>()     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = "20"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = com.iwown.data_link.utils.AppConfigUtil.BLE_LOG_UP_APP     // Catch:{ Exception -> 0x00cf }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00cf }
            int r8 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x00cf }
        L_0x008f:
            r7.setFileType(r8)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r9 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x00cf }
            r7.setFilePath(r9)     // Catch:{ Exception -> 0x00cf }
            com.iwown.device_module.common.network.NetFactory r9 = com.iwown.device_module.common.network.NetFactory.getInstance()     // Catch:{ Exception -> 0x00cf }
            com.iwown.device_module.device_log.LogUpUtils$1 r10 = new com.iwown.device_module.device_log.LogUpUtils$1     // Catch:{ Exception -> 0x00cf }
            r10.<init>()     // Catch:{ Exception -> 0x00cf }
            com.iwown.device_module.common.network.client.DeviceClient r9 = r9.getClient(r10)     // Catch:{ Exception -> 0x00cf }
            r9.upSdFile(r7)     // Catch:{ Exception -> 0x00cf }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00cf }
            r9.<init>()     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = "手动上传日志文件:"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x00cf }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00cf }
            com.socks.library.KLog.e(r9)     // Catch:{ Exception -> 0x00cf }
            r5 = 1
        L_0x00c5:
            if (r5 != 0) goto L_0x0037
            java.lang.String r9 = "***日志文件不存在***"
            com.socks.library.KLog.e(r9)     // Catch:{ Exception -> 0x00cf }
            goto L_0x0037
        L_0x00cf:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0037
        L_0x00d5:
            java.lang.String r9 = "notify"
            boolean r9 = r14.equalsIgnoreCase(r9)     // Catch:{ Exception -> 0x00cf }
            if (r9 == 0) goto L_0x00f9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00cf }
            r9.<init>()     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = "22"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = com.iwown.data_link.utils.AppConfigUtil.BLE_LOG_UP_APP     // Catch:{ Exception -> 0x00cf }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00cf }
            int r8 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x00cf }
            goto L_0x008f
        L_0x00f9:
            java.lang.String r9 = "write"
            boolean r9 = r14.equalsIgnoreCase(r9)     // Catch:{ Exception -> 0x00cf }
            if (r9 == 0) goto L_0x008f
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00cf }
            r9.<init>()     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = "23"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r10 = com.iwown.data_link.utils.AppConfigUtil.BLE_LOG_UP_APP     // Catch:{ Exception -> 0x00cf }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00cf }
            int r8 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x00cf }
            goto L_0x008f
        L_0x011e:
            int r9 = r9 + 1
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_log.LogUpUtils.upLoadBleLog(java.lang.String, java.lang.String, int):void");
    }
}
