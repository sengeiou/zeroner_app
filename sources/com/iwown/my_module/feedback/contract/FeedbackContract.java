package com.iwown.my_module.feedback.contract;

import com.iwown.my_module.feedback.network.response.ImageCode;
import java.util.List;

public class FeedbackContract {

    public interface FeedbackPresenter {
        void uploadImgOrVideo(String str, int i, long j, int i2);

        void uploadListImgOrVideo(List<String> list, long j);
    }

    public interface FeedbackView {
        void imgVideoCode(ImageCode imageCode, int i);

        void imgVideoUpFail(int i);
    }
}
