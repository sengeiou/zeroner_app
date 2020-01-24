package com.iwown.sport_module.ui.ecg.ai;

import android.text.TextUtils;
import android.util.Base64;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.ecg.EcgDataAiResult;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.constant.ConstantsLive.OverSeasBaseUrl;
import com.iwown.sport_module.ui.ecg.bean.EcgAiResult;
import com.iwown.sport_module.ui.ecg.bean.EcgAiResultEvent;
import com.iwown.sport_module.ui.ecg.bean.EcgResultEventBus;
import com.socks.library.KLog;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.greenrobot.eventbus.EventBus;

public class AiAPI {
    private static final String AiUrl = "https://service-g5b2j70v-1255716792.ap-shanghai.apigateway.myqcloud.com/release/ecg/analyze/v2";
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String secretId = "AKID8Hph6n31B3wq2q1EhMhEMmh59EBl3WYT3uT0";
    private static final String secretKey = "hQDM0ZXgE5JL4XICuaJAeZz9ttK9Y3euDSZ08VaQ";

    public static String sign(String secret, String timeStr) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        KLog.i("----ai sig----" + secret + "timeStr:" + timeStr);
        String signStr = "date: " + timeStr + "\nsource: AndriodApp";
        Mac mac1 = Mac.getInstance(HMAC_ALGORITHM);
        mac1.init(new SecretKeySpec(secret.getBytes("UTF-8"), mac1.getAlgorithm()));
        String sig = new String(Base64.encode(mac1.doFinal(signStr.getBytes("UTF-8")), 2));
        KLog.i("----ai sig--" + sig);
        return sig;
    }

    public static void aiEcgPost(String data, final EcgViewDataBean itemData) {
        if (data != null) {
            try {
                new OkHttpClient().newCall(new Builder().get().url(OverSeasBaseUrl.NGG_Url + "sport/ecg/aiAnalysis?rawData=" + data).build()).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                    }

                    public void onResponse(Call call, Response response) {
                        try {
                            String result = response.body().string();
                            if (!TextUtils.isEmpty(result) && response.isSuccessful()) {
                                final EcgAiResult eResult = (EcgAiResult) JsonUtils.fromJson(result, EcgAiResult.class);
                                if (eResult != null) {
                                    KLog.i("-----------" + result);
                                    if (eResult.getData() != null) {
                                        final EcgResultEventBus ecgEg = new EcgResultEventBus();
                                        ecgEg.setData(eResult.getData().getDiagLabels());
                                        String str = "[]";
                                        KLog.i("---" + JsonUtils.toJson(eResult.getData().getDiagLabels()));
                                        if (TextUtils.isEmpty(JsonUtils.toJson(eResult.getData().getDiagLabels())) || eResult.getData().getDiagLabels() == null || eResult.getData().getDiagLabels().size() == 0) {
                                            ecgEg.setStrData("[\"NO\"]");
                                        } else {
                                            ecgEg.setStrData(JsonUtils.toJson(eResult.getData().getDiagLabels()));
                                        }
                                        EcgDataAiResult er = new EcgDataAiResult();
                                        er.setUid(itemData.getUid());
                                        er.setStart_time(itemData.getDate());
                                        er.setData_from(itemData.getData_from());
                                        er.setAi_result(ecgEg.getStrData());
                                        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                                            public void onSuccess(Integer code) {
                                                if (code.intValue() == 0) {
                                                    List<String> lsData = new ArrayList<>();
                                                    try {
                                                        lsData = JsonUtils.getListJson(ecgEg.getStrData(), String.class);
                                                    } catch (Exception e) {
                                                        ThrowableExtension.printStackTrace(e);
                                                    }
                                                    ModuleRouterEcgService.getInstance().updateEcgAiResult(itemData.getUid(), itemData.getData_from(), itemData.getDate(), lsData, itemData.get_uploaded());
                                                    EventBus.getDefault().post(new EcgAiResultEvent());
                                                    EventBus.getDefault().post(ecgEg);
                                                }
                                            }

                                            public void onFail(Throwable e) {
                                                ModuleRouterEcgService.getInstance().updateEcgAiResult(itemData.getUid(), itemData.getData_from(), itemData.getDate(), eResult.getData().getDiagLabels(), 0);
                                                EventBus.getDefault().post(new EcgAiResultEvent());
                                            }
                                        }).updateEcgAiResult(er);
                                        return;
                                    }
                                    KLog.i("ecg ai 走了这里");
                                }
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                });
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }
}
