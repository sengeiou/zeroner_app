package com.iwown.my_module.healthy.network;

import com.iwown.data_link.base.RetCode;
import com.iwown.my_module.healthy.network.request.UploadSprotSteps;
import com.iwown.my_module.healthy.network.request.WxBindDeviceSend;
import com.iwown.my_module.healthy.network.request.WxBindSend;
import com.iwown.my_module.healthy.network.response.WxBindReturnCode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BindService {
    @POST("deviceservice/device/registerwechat")
    Call<RetCode> postBindWx(@Body WxBindSend wxBindSend);

    @POST("deviceservice/device/authorize")
    Call<WxBindReturnCode> postBindWxDevice(@Body WxBindDeviceSend wxBindDeviceSend);

    @POST("deviceservice/device/uploadstep")
    Call<RetCode> postBindWxStep(@Body UploadSprotSteps uploadSprotSteps);
}
