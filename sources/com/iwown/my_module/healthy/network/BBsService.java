package com.iwown.my_module.healthy.network;

import com.iwown.data_link.base.RetCode;
import com.iwown.my_module.healthy.data.DiscuzUser;
import com.iwown.my_module.healthy.network.request.BBSAccount;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BBsService {
    @POST("userservice/discuz/bbs/account")
    Call<RetCode> postBBSAccount(@Body BBSAccount bBSAccount);

    @POST("ucenterService1/discuz/login")
    Call<RetCode> postBBSLogin(@Body BBSAccount bBSAccount);

    @POST("ucenterService1/discuz/register")
    Call<RetCode> postBBSRegister(@Body DiscuzUser discuzUser);
}
