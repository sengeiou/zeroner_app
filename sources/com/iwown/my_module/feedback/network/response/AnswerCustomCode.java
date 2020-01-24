package com.iwown.my_module.feedback.network.response;

import com.iwown.data_link.base.RetCode;

public class AnswerCustomCode extends RetCode {
    private AnswerCustomData data;

    public class AnswerCustomData {
        AnswerData answer;
        Long chatRecordId;
        int isShowThumb;

        public AnswerCustomData() {
        }

        public int getIsShowThumb() {
            return this.isShowThumb;
        }

        public void setIsShowThumb(int isShowThumb2) {
            this.isShowThumb = isShowThumb2;
        }

        public Long getChatRecordId() {
            return this.chatRecordId;
        }

        public void setChatRecordId(Long chatRecordId2) {
            this.chatRecordId = chatRecordId2;
        }

        public AnswerData getAnswer() {
            return this.answer;
        }

        public void setAnswer(AnswerData answer2) {
            this.answer = answer2;
        }
    }

    public class AnswerData {
        String answer;
        int answerType;
        String url;

        public AnswerData() {
        }

        public String getAnswer() {
            return this.answer;
        }

        public void setAnswer(String answer2) {
            this.answer = answer2;
        }

        public int getAnswerType() {
            return this.answerType;
        }

        public void setAnswerType(int answerType2) {
            this.answerType = answerType2;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url2) {
            this.url = url2;
        }
    }

    public AnswerCustomData getData() {
        return this.data;
    }

    public void setData(AnswerCustomData data2) {
        this.data = data2;
    }
}
