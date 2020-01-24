package com.iwown.my_module.feedback.data;

import android.text.SpannableString;

public class TalkMsgEntity {
    private long chatRecordId;
    private long date;
    private int firstType = 0;
    private int megType;
    private SpannableString message;
    private boolean send = true;
    private int sqlId;

    public int getFirstType() {
        return this.firstType;
    }

    public void setFirstType(int firstType2) {
        this.firstType = firstType2;
    }

    public int getSqlId() {
        return this.sqlId;
    }

    public void setSqlId(int sqlId2) {
        this.sqlId = sqlId2;
    }

    public int getMegType() {
        return this.megType;
    }

    public void setMegType(int megType2) {
        this.megType = megType2;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date2) {
        this.date = date2;
    }

    public SpannableString getMessage() {
        return this.message;
    }

    public void setMessage(SpannableString message2) {
        this.message = message2;
    }

    public long getChatRecordId() {
        return this.chatRecordId;
    }

    public void setChatRecordId(long chatRecordId2) {
        this.chatRecordId = chatRecordId2;
    }

    public TalkMsgEntity() {
    }

    public TalkMsgEntity(long date2, SpannableString text, int megType2, int sqlId2) {
        this.date = date2;
        this.message = text;
        this.megType = megType2;
        this.sqlId = sqlId2;
    }

    public boolean isSend() {
        return this.send;
    }

    public void setSend(boolean send2) {
        this.send = send2;
    }
}
