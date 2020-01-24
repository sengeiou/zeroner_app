package com.iwown.my_module.feedback.presenter;

import com.google.gson.Gson;
import com.iwown.my_module.BuildConfig;
import com.iwown.my_module.feedback.contract.FeedbackContract.FeedbackView;
import com.iwown.my_module.feedback.network.FeedbackService;
import com.iwown.my_module.feedback.network.response.ImageCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.socks.library.KLog;
import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedbackPresenter implements com.iwown.my_module.feedback.contract.FeedbackContract.FeedbackPresenter {
    /* access modifiers changed from: private */
    public FeedbackView feedbackView;
    private ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new LinkedBlockingDeque());
    private FeedbackService imgService;
    private Retrofit retrofit;
    private final String subPath = BuildConfig.FEEDBACK_IMG_PATH;

    public FeedbackPresenter(FeedbackView feedbackView2) {
        this.feedbackView = feedbackView2;
        initNetWork();
    }

    private void initNetWork() {
        this.retrofit = MyRetrofitClient.getImgVideoApiRetrofit();
        this.imgService = (FeedbackService) this.retrofit.create(FeedbackService.class);
    }

    /* access modifiers changed from: private */
    public void uploadFile(String path, final int type, long uid, final int listPosition) {
        KLog.e("no2855->>> 文件类型: " + type);
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            Part body = Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(BuildConfig.FEEDBACK_IMG_PATH).append(uid).append("/");
            StringBuffer stringBuffer1 = new StringBuffer();
            stringBuffer1.append(System.currentTimeMillis());
            if (type == 1) {
                stringBuffer1.append("_android.jpg");
            } else {
                stringBuffer1.append("_android.mp4");
            }
            KLog.e("no2855 上传连接: " + stringBuffer.toString());
            this.imgService.uploadImgVideo(stringBuffer.toString(), stringBuffer1.toString(), body).enqueue(new Callback<ImageCode>() {
                public void onResponse(Call<ImageCode> call, Response<ImageCode> response) {
                    if (response == null || response.body() == null) {
                        if (response == null) {
                            KLog.e("no2855->>>  video response null1");
                        } else {
                            KLog.e("no2855->>> video null1 " + response.code());
                        }
                        FeedbackPresenter.this.feedbackView.imgVideoUpFail(listPosition);
                        KLog.e("no2855->>> is null1");
                        return;
                    }
                    if (type == 1) {
                        FeedbackPresenter.this.feedbackView.imgVideoCode((ImageCode) response.body(), 2);
                    } else {
                        FeedbackPresenter.this.feedbackView.imgVideoCode((ImageCode) response.body(), 3);
                    }
                    KLog.e("no2855->>> " + new Gson().toJson(response.body()));
                }

                public void onFailure(Call<ImageCode> call, Throwable t) {
                    KLog.e("no2855->>> is faile");
                    FeedbackPresenter.this.feedbackView.imgVideoUpFail(listPosition);
                }
            });
        }
    }

    public void uploadListImgOrVideo(List<String> paths, long uid) {
        if (paths != null && paths.size() > 0) {
            for (int i = 0; i < paths.size(); i++) {
                final int finalI = i;
                final List<String> list = paths;
                final long j = uid;
                this.fixedThreadPool.execute(new Runnable() {
                    public void run() {
                        FeedbackPresenter.this.uploadFile((String) list.get(finalI), 1, j, 1);
                    }
                });
            }
        }
    }

    public void uploadImgOrVideo(String path, int type, long uid, int listPosition) {
        final String str = path;
        final int i = type;
        final long j = uid;
        final int i2 = listPosition;
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                FeedbackPresenter.this.uploadFile(str, i, j, i2);
            }
        });
    }
}
