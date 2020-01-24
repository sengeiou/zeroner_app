package com.iwown.my_module.feedback.network.response;

import com.iwown.data_link.base.RetCode;

public class UserCode extends RetCode {
    private UserData data;

    public class UserData {
        String answer;
        Long chatRecordId;
        int isShowThumb;

        public UserData() {
        }

        public int getIsShowThumb() {
            return this.isShowThumb;
        }

        public void setIsShowThumb(int isShowThumb2) {
            this.isShowThumb = isShowThumb2;
        }

        public String getAnswer() {
            return this.answer;
        }

        public void setAnswer(String answer2) {
            this.answer = answer2;
        }

        public Long getChatRecordId() {
            return this.chatRecordId;
        }

        public void setChatRecordId(Long chatRecordId2) {
            this.chatRecordId = chatRecordId2;
        }
    }

    public UserData getData() {
        return this.data;
    }

    public void setData(UserData data2) {
        this.data = data2;
    }
}
