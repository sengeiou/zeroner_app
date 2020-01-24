package com.iwown.my_module.healthy;

import android.content.Context;
import com.google.android.gms.fitness.data.Field;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class OldVersionSleepDataQQUtils {
    private static String QQ_Step = "qq_step";

    public static void uploadStepQQ(Context context) {
        KLog.e("no2855uploadStepQQ 1");
        V3_walk tb_step = ModuleRouteWalkService.getInstance().getWalk(new HealthySharedUtil(context).getUid(), new DateUtil().getSyyyyMMddDate(), UserConfig.getInstance().getDevice());
        if (tb_step == null) {
            KLog.e("no2855QQ上传步数tb_step is null : 0");
            return;
        }
        int steps = tb_step.getStep();
        if (steps == 0) {
            KLog.e("no2855QQ上传步数 : 0");
            return;
        }
        HealthySharedUtil sharedUtil = new HealthySharedUtil(context);
        KLog.e("no2855uploadStepQQ 3   " + steps + " openid: " + sharedUtil.getQQOpenId() + " token: " + sharedUtil.getQQToken());
        new OkHttpClient().newBuilder().build().newCall(new Builder().post(new FormBody.Builder().add(Constants.PARAM_ACCESS_TOKEN, sharedUtil.getQQToken()).add("oauth_consumer_key", "1104800774").add("openid", sharedUtil.getQQOpenId()).add(Constants.PARAM_PLATFORM_ID, Constants.SOURCE_QZONE).add("time", (System.currentTimeMillis() / 1000) + "").add("distance", (tb_step.getDistance() * 1000.0f) + "").add("steps", tb_step.getStep() + "").add(Field.NUTRIENT_CALORIES, tb_step.getCalorie() + "").add("duration", ((tb_step.getStep() % 1000) * 60) + "").build()).url("https://openmobile.qq.com/v3/health/report_steps").build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                L.file("QQ同步失败failure", 3);
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (response == null || response.body() == null) {
                    KLog.e("no2855QQ同步返回数据:失败: ");
                    L.file("QQ同步返回数据:失败 ", 3);
                    return;
                }
                String msg = response.body().string();
                KLog.e("no2855QQ同步返回数据:  code: " + response.code() + msg);
                L.file("QQ同步返回数据: " + msg, 3);
            }
        });
    }
}
