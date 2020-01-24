package com.iwown.lib_common.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.iwown.lib_common.network.utils.BaseUtils;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public final class NetworkUtils {
    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_IWLAN = 18;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;

    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    private NetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void openWirelessSettings() {
        if (VERSION.SDK_INT > 10) {
            BaseUtils.getContext().startActivity(new Intent("android.settings.WIRELESS_SETTINGS").setFlags(268435456));
        } else {
            BaseUtils.getContext().startActivity(new Intent("android.settings.SETTINGS").setFlags(268435456));
        }
    }

    private static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) BaseUtils.getContext().getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseUtils.getContext().getSystemService("connectivity");
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean getDataEnabled() {
        try {
            TelephonyManager tm = (TelephonyManager) BaseUtils.getContext().getSystemService(UserConst.PHONE);
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled", new Class[0]);
            if (getMobileDataEnabledMethod != null) {
                return ((Boolean) getMobileDataEnabledMethod.invoke(tm, new Object[0])).booleanValue();
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return false;
    }

    public static void setDataEnabled(boolean enabled) {
        try {
            TelephonyManager tm = (TelephonyManager) BaseUtils.getContext().getSystemService(UserConst.PHONE);
            Method setMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("setDataEnabled", new Class[]{Boolean.TYPE});
            if (setMobileDataEnabledMethod != null) {
                setMobileDataEnabledMethod.invoke(tm, new Object[]{Boolean.valueOf(enabled)});
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static boolean is4G() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.getSubtype() == 13;
    }

    public static boolean getWifiEnabled() {
        return ((WifiManager) BaseUtils.getContext().getSystemService("wifi")).isWifiEnabled();
    }

    public static void setWifiEnabled(boolean enabled) {
        WifiManager wifiManager = (WifiManager) BaseUtils.getContext().getSystemService("wifi");
        if (enabled) {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        } else if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public static boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) BaseUtils.getContext().getSystemService("connectivity");
        if (cm == null || cm.getActiveNetworkInfo() == null || cm.getActiveNetworkInfo().getType() != 1) {
            return false;
        }
        return true;
    }

    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) BaseUtils.getContext().getSystemService(UserConst.PHONE);
        if (tm != null) {
            return tm.getNetworkOperatorName();
        }
        return null;
    }

    public static NetworkType getNetworkType() {
        NetworkType netType = NetworkType.NETWORK_NO;
        NetworkInfo info = getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return netType;
        }
        if (info.getType() == 1) {
            return NetworkType.NETWORK_WIFI;
        }
        if (info.getType() != 0) {
            return NetworkType.NETWORK_UNKNOWN;
        }
        switch (info.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkType.NETWORK_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetworkType.NETWORK_3G;
            case 13:
            case 18:
                return NetworkType.NETWORK_4G;
            default:
                String subtypeName = info.getSubtypeName();
                if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                    return NetworkType.NETWORK_3G;
                }
                return NetworkType.NETWORK_UNKNOWN;
        }
    }

    public static String getIPAddress(boolean useIPv4) {
        boolean isIPv4;
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                if (ni.isUp()) {
                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) addresses.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String hostAddress = inetAddress.getHostAddress();
                            if (hostAddress.indexOf(58) < 0) {
                                isIPv4 = true;
                            } else {
                                isIPv4 = false;
                            }
                            if (useIPv4) {
                                if (isIPv4) {
                                    return hostAddress;
                                }
                            } else if (!isIPv4) {
                                int index = hostAddress.indexOf(37);
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                    continue;
                }
            }
        } catch (SocketException e) {
            ThrowableExtension.printStackTrace(e);
        }
        return null;
    }

    public static String getDomainAddress(final String domain) {
        try {
            return (String) Executors.newCachedThreadPool().submit(new Callable<String>() {
                public String call() throws Exception {
                    try {
                        return InetAddress.getByName(domain).getHostAddress();
                    } catch (UnknownHostException e) {
                        ThrowableExtension.printStackTrace(e);
                        return null;
                    }
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo state : networkInfo) {
                    if (state.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
