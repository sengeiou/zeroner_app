package com.iwown.my_module.contract;

import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.my_module.contract.IvServiceBackContract.IvServicebackPresenter;
import com.iwown.my_module.contract.IvServiceBackContract.IvServicebackView;
import com.iwown.my_module.feedback.data.TB_feedback;
import com.iwown.my_module.feedback.network.FeedbackService;
import com.iwown.my_module.feedback.network.response.IvServiceCode;
import com.iwown.my_module.feedback.network.response.IvServiceCode.IvService;
import com.iwown.my_module.network.MyRetrofitClient;
import com.socks.library.KLog;
import org.litepal.crud.DataSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IvServiceBackPresenter implements IvServicebackPresenter {
    private FeedbackService backService;
    /* access modifiers changed from: private */
    public IvServicebackView feedbackView;
    private Retrofit retrofit;

    public IvServiceBackPresenter(IvServicebackView feedbackView2) {
        this.feedbackView = feedbackView2;
        initNetWork();
    }

    private void initNetWork() {
        this.retrofit = MyRetrofitClient.getFeedbackAPIRetrofit();
        this.backService = (FeedbackService) this.retrofit.create(FeedbackService.class);
    }

    public void getServiceCallBack(long uid) {
        this.backService.getIvServiceRepo(uid).enqueue(new Callback<IvServiceCode>() {
            public void onResponse(Call<IvServiceCode> call, Response<IvServiceCode> response) {
                if (response != null && response.body() != null && ((IvServiceCode) response.body()).getRetCode() == 0) {
                    IvServiceCode code = (IvServiceCode) response.body();
                    KLog.e("no2855-->客服返回的消息: " + JsonUtils.toJson(response.body()));
                    boolean isSend = false;
                    for (IvService ivService : code.getData()) {
                        if (!DataSupport.isExist(TB_feedback.class, "uid=? and record_id=? and msg_type=1", ivService.getUid() + "", ivService.getChatReordId() + "")) {
                            TB_feedback feedback = new TB_feedback();
                            feedback.setUid(ivService.getUid());
                            feedback.setDate(System.currentTimeMillis());
                            feedback.setMsg_type(1);
                            feedback.setMessage(ivService.getContent());
                            feedback.setRecord_id(ivService.getChatReordId());
                            feedback.save();
                            isSend = true;
                        }
                    }
                    if (isSend) {
                        IvServiceBackPresenter.this.feedbackView.serviceCallBack();
                    }
                }
            }

            public void onFailure(Call<IvServiceCode> call, Throwable t) {
            }
        });
    }
}
