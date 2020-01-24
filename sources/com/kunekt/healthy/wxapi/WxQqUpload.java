package com.kunekt.healthy.wxapi;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.fitness.data.Field;
import com.google.gson.Gson;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepDownData2;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.data.WxStep;
import com.iwown.my_module.healthy.network.BindFactory;
import com.iwown.my_module.healthy.network.request.UploadSprotSteps;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WxQqUpload {
    public static void uploadStepWX(Context mContext, boolean mustUpload, boolean isSendEvent) {
        HealthySharedUtil sharedUtil = new HealthySharedUtil(mContext);
        if (sharedUtil.getWxOpenId() == null) {
            KLog.d("no2855,微信 openid null");
            L.file("微信 openid null", 3);
            return;
        }
        L.file("微信上传步数开始", 3);
        DateUtil dateUtil = new DateUtil();
        long uid = sharedUtil.getUid();
        final String record_date = dateUtil.getSyyyyMMddDate();
        V3_walk tb_step = ModuleRouteWalkService.getInstance().getWalk(uid, record_date, ModuleRouteDeviceInfoService.getInstance().getDeviceInfo().mac);
        if (tb_step == null) {
            KLog.e("no2855微信上传步数 : 0 tb_step null");
            L.file("微信上传步数 : 0", 3);
            sendEvent(-1, isSendEvent, "微信上传步数 为0: null");
            return;
        }
        final int steps = tb_step.getStep();
        if (steps == 0) {
            sendEvent(-1, isSendEvent, "微信上传步数 为0: ");
            L.file("微信上传步数 : 0", 3);
            KLog.e("no2855微信上传步数 : 0");
            return;
        }
        UploadSprotSteps request = new UploadSprotSteps();
        request.setOpenid(sharedUtil.getWxOpenId());
        request.setStep(steps);
        request.setRecord_date(record_date);
        WxStep wxStep = (WxStep) new Gson().fromJson(sharedUtil.getWxStepMsg(), WxStep.class);
        if (mustUpload || wxStep == null || !TextUtils.equals(record_date, wxStep.getDate()) || wxStep.getStep() < steps) {
            final boolean z = isSendEvent;
            new BindFactory().getBindService().postBindWxStep(request).enqueue(new Callback<RetCode>() {
                public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                    if (response == null || response.body() == null) {
                        L.file("no2855微信上传步数 : 失败" + (response != null ? response.errorBody().toString() : " null"), 3);
                        WxQqUpload.sendEvent(-1, z, "微信上传步数 失败 :  " + (response != null ? response.errorBody().toString() : " null"));
                    } else if (((RetCode) response.body()).getRetCode() == 0) {
                        WxStep wxStep1 = new WxStep();
                        wxStep1.setDate(record_date);
                        wxStep1.setStep(steps);
                        L.file("no2855微信上传步数 : 成功" + steps, 3);
                        KLog.e("no2855微信上传步数 : 成功" + steps);
                        WxQqUpload.sendEvent(2, z, "微信上传步数 成功");
                    } else {
                        L.file("微信上传步数 : 失败" + ((RetCode) response.body()).getRetCode(), 3);
                        WxQqUpload.sendEvent(-1, z, "微信上传步数 失败: " + ((RetCode) response.body()).getRetCode());
                    }
                }

                public void onFailure(Call<RetCode> call, Throwable t) {
                    L.file("微信上传步数 : 失败     " + t.toString(), 3);
                    call.cancel();
                    WxQqUpload.sendEvent(-1, z, "微信上传步数 失败 :  " + t.toString());
                }
            });
            return;
        }
        KLog.e("no2855 微信上传步数没有变化");
    }

    /* access modifiers changed from: private */
    public static void sendEvent(int type, boolean isSend, String msg) {
        if (isSend) {
            EventBus.getDefault().post(new WxQqEvent(type, msg));
        }
    }

    public static void uploadStepQQ(Context context, boolean mustUpload, boolean isSendEvent) {
        HealthySharedUtil sharedUtil = new HealthySharedUtil(context);
        String token = sharedUtil.getQQToken();
        String openId = sharedUtil.getQQOpenId();
        if (token == null || openId == null) {
            KLog.d("no2855,QQ openid null");
            L.file("QQ openid null", 3);
            return;
        }
        V3_walk tb_step = ModuleRouteWalkService.getInstance().getWalk(sharedUtil.getUid(), new DateUtil().getSyyyyMMddDate(), ModuleRouteDeviceInfoService.getInstance().getDeviceInfo().dev_name);
        if (tb_step == null) {
            KLog.e("no2855QQ上传步数tb_step is null : 0");
            sendEvent(-1, isSendEvent, "需要同步的步数为 :0  ");
            return;
        }
        int steps = tb_step.getStep();
        if (steps == 0) {
            sendEvent(-1, isSendEvent, "需要同步的步数为 :0  ");
            KLog.e("no2855QQ上传步数 : 0");
            return;
        }
        KLog.e("no2855uploadStepQQ 3   " + steps);
        okhttp3.Call newCall = new OkHttpClient().newBuilder().build().newCall(new Builder().post(new FormBody.Builder().add(Constants.PARAM_ACCESS_TOKEN, token).add("oauth_consumer_key", "1104800774").add("openid", openId).add(Constants.PARAM_PLATFORM_ID, Constants.SOURCE_QZONE).add("time", (System.currentTimeMillis() / 1000) + "").add("distance", tb_step.getDistance() + "").add("steps", tb_step.getStep() + "").add(Field.NUTRIENT_CALORIES, tb_step.getCalorie() + "").add("duration", ((tb_step.getStep() % 1000) * 60) + "").build()).url("https://openmobile.qq.com/v3/health/report_steps").build());
        final boolean z = isSendEvent;
        AnonymousClass2 r0 = new okhttp3.Callback() {
            public void onFailure(okhttp3.Call call, IOException e) {
                WxQqUpload.sendEvent(-1, z, "QQ运动同步失败failure");
                L.file("QQ同步失败failure", 3);
            }

            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response == null || response.body() == null) {
                    WxQqUpload.sendEvent(-1, z, "QQ运动同步失败");
                    KLog.e("no2855QQ同步返回数据:失败: ");
                    L.file("QQ同步返回数据:失败 ", 3);
                    return;
                }
                String msg = response.body().string();
                KLog.e("no2855QQ同步返回数据:  code: " + response.code() + "内容: " + msg);
                WxQqUpload.sendEvent(1, z, "QQ运动同步成功");
                L.file("QQ同步返回数据: " + msg, 3);
            }
        };
        newCall.enqueue(r0);
    }

    public static void responseSleepTime(Context context) {
        HealthySharedUtil healthySharedUtil = new HealthySharedUtil(context);
        String token = healthySharedUtil.getQQToken();
        String openId = healthySharedUtil.getQQOpenId();
        if (token == null || openId == null) {
            KLog.d("no2855,QQ sleep openid null");
            L.file("QQ sleep openid null", 3);
            return;
        }
        String dataFrom = ModuleRouteDeviceInfoService.getInstance().getDeviceInfo().dev_name;
        HealthySharedUtil healthySharedUtil2 = new HealthySharedUtil(context);
        long uid = healthySharedUtil2.getUid();
        SleepDataDay sleepDataToday = new SleepDataDay();
        sleepDataToday.uid = uid;
        sleepDataToday.data_from = dataFrom;
        sleepDataToday.time_unix = System.currentTimeMillis() / 1000;
        ModuleRouteSleepService.getInstance().getDaySleep(sleepDataToday);
        if (sleepDataToday.sleepDownData1 == null || sleepDataToday.sleepDownData1.getEnd_time() - sleepDataToday.sleepDownData1.getStart_time() <= 0) {
            KLog.d("no2855,QQ sleep is null");
            L.file("QQ sleep is null", 3);
            return;
        }
        KLog.d("no2855,QQ sleep startTime is " + sleepDataToday.sleepDownData1.getStart_time());
        int start_time = (int) sleepDataToday.sleepDownData1.getStart_time();
        int end_time = (int) sleepDataToday.sleepDownData1.getEnd_time();
        int total_time = ((int) (sleepDataToday.sleepDownData1.getEnd_time() - sleepDataToday.sleepDownData1.getStart_time())) / 60;
        int light_time = (int) sleepDataToday.sleepDownData1.getLight_time();
        int deep_time = (int) sleepDataToday.sleepDownData1.getDeep_time();
        int awake = (total_time - light_time) - deep_time;
        int awake_time = awake > 0 ? awake : 0;
        List<SleepDownData2> lists = sleepDataToday.sleepDownData1.getSleep_segment();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (SleepDownData2 sleepDownData2 : lists) {
            int time = (sleepDownData2.getSt() * 60) + start_time;
            int type = 1;
            if (sleepDownData2.getType() == 3) {
                type = 3;
            } else if (sleepDownData2.getType() == 4) {
                type = 2;
            }
            if (i == lists.size() - 1) {
                stringBuilder.append("[" + time + "," + type + "]");
            } else {
                stringBuilder.append("[" + time + "," + type + "],");
            }
            i++;
        }
        KLog.e("今天上传睡眠 QQ start end total " + start_time + "  " + end_time + "  " + total_time + "  浅睡: " + light_time + " 深睡：" + deep_time + " 清醒: " + awake_time);
        KLog.e("今天上传睡眠 QQ " + stringBuilder);
        new HashMap();
        new OkHttpClient().newBuilder().build().newCall(new Builder().post(new FormBody.Builder().add(Constants.PARAM_ACCESS_TOKEN, token).add("oauth_consumer_key", "1104800774").add("openid", openId).add(Constants.PARAM_PLATFORM_ID, Constants.SOURCE_QZONE).add("start_time", start_time + "").add("end_time", end_time + "").add("total_time", total_time + "").add("deep_sleep", deep_time + "").add("light_sleep", light_time + "").add("awake_time", awake_time + "").add("detail", stringBuilder.toString()).build()).url("https://openmobile.qq.com/v3/health/report_sleep").build()).enqueue(new okhttp3.Callback() {
            public void onFailure(okhttp3.Call call, IOException e) {
                L.file("QQ睡眠同步失败failure", 3);
            }

            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response == null || response.body() == null) {
                    KLog.e("no2855QQ睡眠返回数据:失败: ");
                    L.file("QQ睡眠同步返回数据:失败 ", 3);
                    return;
                }
                String msg = response.body().string();
                KLog.e("no2855QQ睡眠返回数据:  code: " + response.code() + "内容: " + msg);
                L.file("QQ睡眠同步返回数据: " + msg, 3);
            }
        });
    }
}
