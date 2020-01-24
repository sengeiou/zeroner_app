package com.iwown.my_module.feedback.network.response;

public class QuestionData {
    private String answerCode;
    private String deviceType;
    private int id;
    private String question;
    private int sort;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question2) {
        this.question = question2;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType2) {
        this.deviceType = deviceType2;
    }

    public String getAnswerCode() {
        return this.answerCode;
    }

    public void setAnswerCode(String answerCode2) {
        this.answerCode = answerCode2;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort2) {
        this.sort = sort2;
    }
}
