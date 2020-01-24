package com.iwown.my_module.healthy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.my_module.data.UserInfoEntity;
import com.socks.library.KLog;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

public class HealthyUtil {
    public static void saveWxQqToTb(String userInfo, int type, long uid) {
        if (type == 4) {
            try {
                JSONObject jb = new JSONObject(userInfo);
                saveOrUpdateUser(uid, jb.optString("nickname"), jb.optString("figureurl_qq_2"));
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
            }
        } else if (type == 3) {
            try {
                JSONObject jb2 = new JSONObject(userInfo);
                saveOrUpdateUser(uid, jb2.optString("nickname"), jb2.optString("headimgurl"));
            } catch (JSONException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        } else if (type == 1) {
            saveOrUpdateUser(uid, userInfo, "");
        }
    }

    public static void saveOrUpdateUser(long uid, String nickName, String headUrl) {
        UserInfoEntity entity = (UserInfoEntity) DataSupport.where("uid=?", uid + "").findLast(UserInfoEntity.class);
        if (entity == null) {
            UserInfoEntity entity2 = new UserInfoEntity();
            entity2.setUid(uid);
            entity2.setPortrait_url(headUrl);
            entity2.setNickname(nickName);
            entity2.save();
        } else if (TextUtils.isEmpty(entity.getNickname())) {
            entity.setUid(uid);
            if (TextUtils.isEmpty(entity.getPortrait_url())) {
                entity.setPortrait_url(headUrl);
            }
            entity.setNickname(nickName);
            entity.updateAll("uid=?", uid + "");
        }
    }

    public static void gotoChinaMarket(Context context) {
        try {
            String pake = context.getPackageName();
            String phone = Build.MANUFACTURER.toLowerCase(Locale.US);
            KLog.e("no2855手机: " + phone);
            Uri uri = Uri.parse("market://details?id=" + pake);
            String market = "com.tencent.android.qqdownloader";
            if ("xiaomi".equals(phone)) {
                market = "com.xiaomi.market";
            } else if ("huawei".equals(phone)) {
                market = "com.huawei.appmarket";
            } else if ("vivo".equals(phone)) {
                market = "com.bbk.appstore";
            } else if ("oppo".equals(phone)) {
                market = "com.oppo.market";
            } else {
                List<PackageInfo> pinfo = context.getPackageManager().getInstalledPackages(0);
                if (pinfo != null && pinfo.size() > 0) {
                    Iterator it = pinfo.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        PackageInfo packageInfo = (PackageInfo) it.next();
                        KLog.e("no2855手机循环: " + packageInfo.packageName);
                        if (!packageInfo.packageName.equals("com.tencent.android.qqdownloader")) {
                            if (!packageInfo.packageName.equals("com.qihoo.appstore")) {
                                if (!packageInfo.packageName.equals("com.baidu.appsearch")) {
                                    if (packageInfo.packageName.equals("com.wandoujia.phoenix2")) {
                                        market = "com.wandoujia.phoenix2";
                                        break;
                                    }
                                } else {
                                    market = "com.baidu.appsearch";
                                    break;
                                }
                            } else {
                                market = "com.qihoo.appstore";
                                break;
                            }
                        } else {
                            market = "com.tencent.android.qqdownloader";
                            break;
                        }
                    }
                }
            }
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addFlags(268435456);
            intent.setPackage(market);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShortToast((CharSequence) "跳转失败，请手动进去应用市场更新app");
        }
    }
}
