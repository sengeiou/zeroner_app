package com.iwown.data_link.blood;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProjectAPI {
    @GET("health/bloodpressure/coverage")
    Call<bpCoverageDown> downloadBPcoverage(@Query("uid") long j, @Query("year") int i, @Query("month") int i2);

    @GET("health/bloodpressure/download/range")
    Call<BpDownData1> downloadBlood(@Query("uid") long j, @Query("start_time") String str, @Query("end_time") String str2);

    @GET("health/bloodpressure/correction/download")
    Call<bpPreDown1> downloadBloodpressure(@Query("uid") long j);

    @POST("health/bloodpressure/batchupload")
    Call<ResponseBody> uploadBlood(@Body BloodDataUpload bloodDataUpload);

    @POST("health/bloodpressure/correction/upload")
    Call<ResponseBody> uploadBloodpressure(@Body BpPreUpload bpPreUpload);
}
