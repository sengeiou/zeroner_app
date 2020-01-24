package com.iwown.my_module.feedback.network.response;

import com.iwown.data_link.base.RetCode;

public class AnswerCode extends RetCode {
    private Long chatRecordId;
    private ShowThumb data;

    public class AnswerData {
        String answer;
        String answerCode;
        int answerType;
        int id;
        String url;

        public AnswerData() {
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id2) {
            this.id = id2;
        }

        public String getAnswerCode() {
            return this.answerCode;
        }

        public void setAnswerCode(String answerCode2) {
            this.answerCode = answerCode2;
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

    public class ShowThumb {
        AnswerData answer;
        int isShowThumb;

        public ShowThumb() {
        }

        public int getIsShowThumb() {
            return this.isShowThumb;
        }

        public void setIsShowThumb(int isShowThumb2) {
            this.isShowThumb = isShowThumb2;
        }

        public AnswerData getAnswer() {
            return this.answer;
        }

        public void setAnswer(AnswerData answer2) {
            this.answer = answer2;
        }
    }

    public ShowThumb getData() {
        return this.data;
    }

    public void setData(ShowThumb data2) {
        this.data = data2;
    }

    public Long getChatRecordId() {
        return this.chatRecordId;
    }

    public void setChatRecordId(Long chatRecordId2) {
        this.chatRecordId = chatRecordId2;
    }
}
